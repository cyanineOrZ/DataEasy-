<template>
  <div class="toolbar">
    <div class="toolbox" :class="{'toolbox-unactive': isActive}">
      <div class="toolbox-list">
        <!--   图标列表  活跃时获取该按钮的对应的key，并展示对应侧边框   -->
        <div class="toolbox-list-btn"
             v-for="item in btnList"
             :key="item.name"
             @click="showPanel(item.key)"
        >{{item.name}}
        </div>
      </div>

      <!--   需要展示的图表   -->
      <transition name="fade"><div class="collapse-panel" v-show="isActive"><EditSidePanel :panelKey="panelKey"></EditSidePanel></div></transition>
    </div>
  </div>
</template>

<script>
import EditSidePanel from './EditSidePanel.vue';

export default {

  name: "EditToolBar",

  components: {
    EditSidePanel,
  },
  data() {
    return {
      //默认为空
      panelKey: 'text',
      //按钮列表
      btnList: [
        {key: 'chart', name: '图表',},
        {key: 'text', name: '文字',},
        {key: 'picture', name: '图片',},
        {key: 'tools', name: '边框',},
      ],
      isActive: false,
    };
  },
  computed: {

  },
  methods: {
    //展示响应模块
    showPanel(key) {
      //当重复点击时，会开启或关闭边选项组
      if (this.panelKey === key) {
        this.isActive = !this.isActive
      }
      else {
        this.isActive = true
        this.panelKey = key
      }
    },
  },
};
</script>

<style scoped>
.toolbox {
  height: 560px;
  display: flex;
  flex-direction: column;
  border-radius: 8px;
  background: #ffffffe9;
  border: 1px solid grey;
}

.toolbox-unactive{
  border-right: none;
  border-bottom-right-radius: 0;
  border-top-right-radius: 0;
}


.toolbar {
  position: relative;
  height: 75%;
}



.toolbox-list {
  flex: 1;
}

.toolbox-list-btn {
  display: inline-block;
  width: 40px;
  height: 40px;
  line-height: 40px;
  margin: 5px;
  text-align: center;
  border-radius: 8px;
  transition: all 0.3s ease;
  color: #999;
}
.toolbox-list-btn:hover {
  cursor: pointer;
  background-color: rgba(64, 160, 255, 0.1);
  color: #666666;
}
.toolbox-list-btn:active {
  background-color: rgba(64, 160, 255, 0.1);
  color: #409EFF;
}

.collapse-panel {
  position: absolute;
  top: 0;
  bottom: 0;
  left: 50px;
  width: 250px;
  z-index: 100;
}

.fade-enter-active, .fade-leave-active{
  transition: opacity 0.5s;
}
.fade-enter, .fade-leave-to {
  opacity: 0;
}

</style>