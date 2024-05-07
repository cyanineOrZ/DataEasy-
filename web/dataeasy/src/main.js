import Vue from 'vue'
import App from './App.vue'

import router from './router/router'
import store from './sotre/store'

import httpServe from './axios/axios'
Vue.prototype.$axios = httpServe

import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

import VueDragResize from 'vue-drag-resize';
import VueDraggableResizable from 'vue-draggable-resizable'
import 'vue-draggable-resizable/dist/VueDraggableResizable.css'
import draggable from 'vuedraggable'

import VeChart from "@v-charts2/chart";

Vue.config.productionTip = false



Vue.use(ElementUI);
Vue.use(VeChart)
Vue.component('vue-draggable-resizable', VueDraggableResizable);
Vue.component('vue-drag-resize', VueDragResize);
Vue.component('vue-draggable', draggable);


Vue.config.silent = true;
Vue.config.errorHandler = function (error, vm, info) {
  // 检查错误类型是否为 'TypeError'，以及错误信息中是否包含 'offsetWidth'
  if (error instanceof TypeError && error.message.includes('offsetWidth')) {
    console.error(1)
    // 如果是以上条件，返回 true 表示已处理该错误
    return true;
  }
  else if (error instanceof DOMException && error.message.includes('removeChild')) {
    return true;
  }

  // 对于其他类型的错误，继续使用默认的错误处理行为
  // 此处你可以选择记录到日志，通知用户等
  console.error(error, vm, info)
};
window.onerror = function(message, source, lineno, colno, error) {
  // 检查错误类型是否为 'TypeError'，以及错误信息是否包含 'offsetWidth'
  if (error instanceof TypeError && message.includes('offsetWidth')) {
    // 如果是以上条件，忽略该错误，不进行警告
    return true; // 返回 true 表示已处理该错误
  }
  return false;
};

//统一修改el-dialog的宽度
ElementUI.Dialog.props.width.default = '550px'

new Vue({

  beforeCreate() {
    // 事件总线
    Vue.prototype.$EventBus = new Vue({});
    },

  router,
  store,
  render: h => h(App),

}).$mount('#app')
