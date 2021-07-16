import vue from 'vue'
import Vuex from 'vuex'

vue.use(Vuex)

const store = new Vuex.Store({
  state: {
    LOADING: false,
    userInfo: {}
  },
  mutations: {
    showLoading (state) {
      state.LOADING = true
    },
    hideLoading (state) {
      state.LOADING = false
    },
    setUser (state, user) {
      state.userInfo = user
    }
  }
})
export default store
