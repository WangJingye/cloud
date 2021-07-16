<template>
  <div class="clearfix">
    <a-upload
      :action="action"
      list-type="picture-card"
      :file-list="fileList"
      @preview="handlePreview"
      @change="handleChange"
      :before-upload="beforeUpload">
      <div v-if=" fileList.length < ( max ? max : 1 )">
        <a-icon type="plus"/>
        <div class="ant-upload-text">
          Upload
        </div>
      </div>
    </a-upload>
    <a-modal :visible="previewVisible" :footer="null" @cancel="handleCancel">
      <img alt="example" style="width: 100%" :src="previewImage"/>
    </a-modal>
  </div>
</template>
<script>

export default {
  props: [
    'imageUrl', 'max'
  ],
  data () {
    return {
      action: process.env.REQUEST_HOST + 'system/public/upload',
      loading: false,
      fileList: [],
      previewVisible: false,
      previewImage: ''
    }
  },
  model: {
    prop: 'imageUrl', // 绑定的值，通过父组件传递
    event: 'update' //
  },
  watch: {
    imageUrl (val) {
      let imgList = []
      if (val !== undefined && val !== null && val.length !== 0) {
        imgList = val.split(',')
      }
      for (let i in imgList) {
        imgList[i] = {
          uid: (-i).toString(),
          name: imgList[i],
          status: 'done',
          url: process.env.IMAGE_HOST + imgList[i]
        }
      }
      this.fileList = imgList
    }
  },
  methods: {
    async handlePreview (file) {
      this.previewImage = file.url
      this.previewVisible = true
    },
    handleCancel () {
      this.previewVisible = false
    },
    handleChange (info) {
      if (!info.file.hasOwnProperty('status')) {
        return
      }
      if (info.file.status === 'uploading') {
        this.fileList = info.fileList
        return
      }
      let files = []
      if (info.file.status === 'removed') {
        files = info.fileList
      }
      if (info.file.status === 'done') {
        if (info.file.response.code !== 200) {
          this.$message.error(info.file.response.message)
        }
        files = info.fileList
      }
      let imgs = []
      let newFiles = []
      for (let i in files) {
        if (files[i].response.code === 0) {
          continue
        }
        let url = files[i].url || files[i].response.data.url
        if (url === null || url.length === 0) {
          continue
        }
        url = url.replace(process.env.IMAGE_HOST, '')
        imgs.push(url)
        newFiles.push(files[i])
      }
      if (this.imageUrl === imgs.join(',')) {
        this.fileList = newFiles
      } else {
        this.$emit('update', imgs.join(','))
      }
    },
    beforeUpload (file) {
      return true
      // const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/png'
      // if (!isJpgOrPng) {
      //   this.$message.error('文件类型有误')
      //   return false
      // }
      // const isLt2M = file.size / 1024 / 1024 < 2
      // if (!isLt2M) {
      //   this.$message.error('文件大小限制为2M')
      //   return false
      // }
      // return isJpgOrPng && isLt2M
    }
  }
}
</script>
<style>
  .avatar-uploader > .ant-upload {
    width: 128px;
    height: 128px;
  }

  .ant-upload-select-picture-card i {
    font-size: 32px;
    color: #999;
  }

  .ant-upload-select-picture-card .ant-upload-text {
    margin-top: 8px;
    color: #666;
  }
</style>
