<template>
  <ul v-if="totalPage > 1" class="ant-pagination ant-table-pagination">
    <li title="Previous Page"
        @click="goPage(page - 1)"
        :aria-disabled="page===startPage ? 'true':'false'"
        :class="page===startPage ?'ant-pagination-disabled ant-pagination-prev':'ant-pagination-prev'"><a
      class="ant-pagination-item-link"><i aria-label="icon: left" class="anticon anticon-left">
      <svg viewBox="64 64 896 896" data-icon="left" width="1em" height="1em" fill="currentColor" aria-hidden="true"
           focusable="false" class="">
        <path
          d="M724 218.3V141c0-6.7-7.7-10.4-12.9-6.3L260.3 486.8a31.86 31.86 0 0 0 0 50.3l450.8 352.1c5.3 4.1 12.9.4 12.9-6.3v-77.3c0-4.9-2.3-9.6-6.1-12.6l-360-281 360-281.1c3.8-3 6.1-7.7 6.1-12.6z"></path>
      </svg>
    </i></a>
    </li>
    <template v-for="it of endPage - startPage + 1">
      <li :key="it + startPage - 1" :title="it + startPage - 1"
          @click="goPage(it + startPage - 1)"
          :class="it+startPage-1 === page ? 'ant-pagination-item ant-pagination-item-active' : 'ant-pagination-item'">
        <a>{{ it + startPage - 1 }}</a>
      </li>
    </template>
    <li title="Next Page"
        :aria-disabled="page === endPage ? 'true':'false'"
        @click="goPage(page + 1)"
        :class="page === endPage ? 'ant-pagination-disabled ant-pagination-next':'ant-pagination-next'"><a
      class="ant-pagination-item-link"><i aria-label="icon: right" class="anticon anticon-right">
      <svg viewBox="64 64 896 896" data-icon="right" width="1em" height="1em" fill="currentColor" aria-hidden="true"
           focusable="false" class="">
        <path
          d="M765.7 486.8L314.9 134.7A7.97 7.97 0 0 0 302 141v77.3c0 4.9 2.3 9.6 6.1 12.6l360 281.1-360 281.1c-3.9 3-6.1 7.7-6.1 12.6V883c0 6.7 7.7 10.4 12.9 6.3l450.8-352.1a31.96 31.96 0 0 0 0-50.4z"></path>
      </svg>
    </i></a>
    </li>
  </ul>
</template>

<script>
export default {
  name: 'pagination',
  props: ['totalPage', 'page', 'pageSize', 'searchForm', 'searchFunction'],
  data () {
    return {
      list: [],
      startPage: 0,
      endPage: 0
    }
  },
  created () {
    let pageCount = 8
    let leftCount = Math.floor(pageCount / 2)
    let rightCount = leftCount
    // 偶数时
    if (pageCount % 2 === 0) {
      leftCount = rightCount - 1
    }
    let startPage
    let endPage
    if (this.totalPage <= pageCount) {
      startPage = 1
      endPage = this.totalPage
    } else if (this.page + rightCount >= this.totalPage) {
      startPage = this.totalPage - pageCount + 1
      endPage = this.totalPage
    } else if (this.page - leftCount <= 0) {
      startPage = 1
      endPage = pageCount
    } else {
      startPage = this.page - leftCount
      endPage = this.page + rightCount
    }
    this.startPage = startPage
    this.endPage = endPage
  },
  methods: {
    goPage: function (page) {
      if (page <= 0) {
        page = 1
      }
      if (page > this.endPage) {
        page = this.endPage
      }
      this.searchForm.page = page
      this.searchFunction(this.searchForm)
    }
  }
}
</script>
