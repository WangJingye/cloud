<template>
  <div class="ant-modal-root">
    <div class="ant-modal-mask" style=""></div>
    <div role="dialog" class="ant-modal-wrap">
      <div role="document" class="ant-modal" style="width: 20rem;top: calc(50vh - 2rem)">
        <div class="ant-modal-content">
          <div class="ant-modal-body" style="padding: 1.5rem">
            <i aria-label="icon: loading" class="anticon anticon-loading" style="color:#1890ff;font-size: 1.2rem">
              <svg viewBox="0 0 1024 1024" data-icon="loading" width="1em" height="1em" fill="currentColor"
                   aria-hidden="true" focusable="false" class="anticon-spin">
                <path
                  d="M988 548c-19.9 0-36-16.1-36-36 0-59.4-11.6-117-34.6-171.3a440.45 440.45 0 0 0-94.3-139.9 437.71 437.71 0 0 0-139.9-94.3C629 83.6 571.4 72 512 72c-19.9 0-36-16.1-36-36s16.1-36 36-36c69.1 0 136.2 13.5 199.3 40.3C772.3 66 827 103 874 150c47 47 83.9 101.8 109.7 162.7 26.7 63.1 40.2 130.2 40.2 199.3.1 19.9-16 36-35.9 36z"></path>
              </svg>
            </i><span style="color:red;margin-left: 0.5rem;font-size: 1rem">{{content}}</span>
          </div>
        </div>
        <div tabindex="0" aria-hidden="true" style="width: 0; height: 0; overflow: hidden;"></div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'loading',
  data () {
    return {
      content: '数据加载中，请稍等...'
    }
  }
}
</script>

<style scoped>
  .ant-modal-body {
    text-align: center;
  }
</style>
