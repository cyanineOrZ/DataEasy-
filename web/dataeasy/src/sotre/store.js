import Vuex from 'vuex'
import Vue from 'vue'

Vue.use(Vuex)


const store = new Vuex.Store({
    state() {
      return {
          avatarChange: false
      }
    },

    mutations: {

    },

    modules: {
    }
})

export default store