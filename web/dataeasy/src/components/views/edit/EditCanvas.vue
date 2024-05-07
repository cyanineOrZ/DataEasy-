<template>
  <!--  -->
  <div class="screen" v-if="this.$parent.chartData" :style="{scale: getScale}">

    <!--  画板区域  通过按住空格，可以拖动画布（只能在画板区域按住才能控制）  -->
    <div class="edit-view" tabindex="0"
         @keydown.space.prevent="handleSpaceDown($event)"
         @keyup.space.prevent="handleSpaceUp"
         ref="edit-view">

      <div class="screen-box-draggable-tips">按住空格即可拖动画布</div>
      <!--  画布区域 x为控件生成的初始横轴位置，y为控件初始纵轴位置，坐标轴中心为父容器的左上角 注意这里是整个画布的区域 -->
      <vue-draggable-resizable
        :x="getChartData.rightresever"
        :y="getChartData.top"
        :w="getChartData.width"
        :h="getChartData.height"
        class-name="screen-box"
        class-name-draggable="screen-box-draggable"
        :parent="true"
        :grid="[1, 1]"
        :draggable="screenDraggable"
        :resizable="true"
        @resizing="handleChartResize(arguments)"
        @dragging="handleChartDrag(arguments)">
        <!--  真实画布区域（上方为配置项）  -->
        <div class="canvas" :style="getScreenStyle" @click.self="handleActivated(-1)" ref="contentData">


          <!--  控件生成 非预览状态可以拖动控件和改变控件大小  -->
          <vue-drag-resize
            v-for="(item, index) in getChartData.elements"
            :key="item.elementid"
            :isActive="item.active"
            :w="item.width"
            :h="item.height"
            :x="item.rightresever"
            :y="item.top"
            :minh="0"
            :minw="0"
            :parent-limitation="true"
            :parent-w="getChartData.width"
            :parent-h="getChartData.height"
            :preventActiveBehavior="true"
            dragCancel="dragCancel"
            contentClass="contentClass"
            @activated="handleActivated(index)"
            @resizing="handleElementResize(item, arguments[0], index)"
            @dragging="handelElementDrag(item, arguments[0], index)"
            >
            <!--  生成文本控件 -->
            <div class="filler" @click="handleActivated(index)" v-if="item.type === 'text'" :style="{width: '100%', height: '100%', backgroundColor: item.backgroundcolor}">
              <div
                  class="filler-text-container"
                  :style="{
                      color: item.data.color,
                      fontFamily: `${item.data.fontFamilyEnglish}, ${item.data.fontFamilyChinese}`,
                      fontSize: item.data.fontSize + 'px',
                      lineHeight: item.height + 'px',
                      fontWeight: item.data.bold === 'bold' ? 'bold' : 'normal',
                      fontStyle: item.data.italic === 'italic' ? 'italic' : 'normal',
                      textAlign: item.data.textAlign,
                      textStroke: item.data.textStroke ? item.data.textStrokeSize + 'px ' + item.data.textStrokeColor : '0',
                      textShadow: item.data.textShadow ? item.data.textShadowX + 'px ' + item.data.textShadowY + 'px ' + item.data.textShadowBlur + 'px ' + item.data.textShadowColor : 'none',
                  }"
                  v-text="item.data.text">
              </div>
            </div>

            <!--  生成边框控件 -->
            <div class="filler" @click="handleActivated(index)" v-if="item.type === 'border'" :style="{width: '100%', height: '100%', backgroundColor: item.backgroundcolor}">
              <div
                  class="filler-border-container"
                  :class="'filler-border-container-border' + item.data.borderId"
                  :style="{
                    opacity: item.data.opacity
                  }"></div>
            </div>

            <!--  生成图片控件 -->
            <div class="filler" @click="handleActivated(index)" v-if="item.type === 'image'" :style="{width: '100%', height: '100%'}">
              <div
                  class="filler-image-container"
                  :style="{
                                backgroundImage: `url(${item.data.backgroundImage})`,
                                backgroundRepeat: 'no-repeat',
                                backgroundSize: item.data.backgroundImageSize,
                                backgroundPositionX:
                                item.data.backgroundPositionX ? item.data.backgroundPositionX + 'px' : '0px',
                                backgroundPositionY:
                                item.data.backgroundPositionY ? item.data.backgroundPositionY + 'px' : '0px',
                                opacity: item.data.opacity,
                             }"
                  :ref="'image' + index"
              >
              </div>
            </div>

            <!--  生成图表控件 -->
            <div class="filler" @click="handleActivated(index)" v-if="item.type === 'chart'" :style="{width: '100%', height: '100%'}">
              <!--  生成折线图 -->
              <ve-line v-if="item.data.settings.type === 'line'"
                       :ref="item.data.settings.type + index"
                       class="filler-chart-container"
                       :backgroundColor="item.backgroundcolor"
                       :width="item.width + 'px'"
                       :height="item.height + 'px'"
                       :settings="item.data.settings"
                       :extend="item.data.extend"
                       :data="item.data.generated">
              </ve-line>
              <!--  生成柱状图 -->
              <ve-histogram v-if="item.data.settings.type === 'histogram'"
                            class="filler-chart-container"
                            :width="item.width + 'px'"
                            :height="item.height + 'px'"
                            :settings="item.data.settings"
                            :backgroundColor="item.backgroundcolor"
                            :extend="item.data.extend"
                            :data="item.data.generated">
              </ve-histogram>
              <!--  生成条形图 -->
              <ve-bar v-if="item.data.settings.type ==='bar'"
                      class="filler-chart-container"
                      :width="item.width + 'px'"
                      :height="item.height + 'px'"
                      :settings="item.data.settings"
                      :backgroundColor="item.backgroundcolor"
                      :extend="item.data.extend"
                      :data="item.data.generated">
              </ve-bar>
              <!--  生成饼图 -->
              <ve-pie v-if="item.data.settings.type ==='pie'"
                      class="filler-chart-container"
                      :width="item.width + 'px'"
                      :height="item.height + 'px'"
                      :settings="item.data.settings"
                      :backgroundColor="item.backgroundcolor"
                      :extend="item.data.extend"
                      :data="item.data.generated">
              </ve-pie>
              <!--  生成漏斗图 -->
              <ve-funnel v-if="item.data.settings.type === 'funnel'"
                         class="filler-chart-container"
                         :width="item.width + 'px'"
                         :height="item.height + 'px'"
                         :backgroundColor="item.backgroundcolor"
                         :extend="item.data.extend"
                         :settings="item.data.settings"
                         :data="item.data.generated">
              </ve-funnel>
              <!--  生成散点图 -->
              <ve-scatter v-if="item.data.settings.type === 'scatter'"
                          class="filler-chart-container"
                          :width="item.width + 'px'"
                          :height="item.height + 'px'"
                          :backgroundColor="item.backgroundcolor"
                          :extend="item.data.extend"
                          :settings="item.data.settings"
                          :data="item.data.generated">
              </ve-scatter>
              <!--  生成瀑布图 -->
              <ve-waterfall v-if="item.data.settings.type === 'waterfall'"
                            class="filler-chart-container"
                            :width="item.width + 'px'"
                            :height="item.height + 'px'"
                            :backgroundColor="item.backgroundcolor"
                            :extend="item.data.extend"
                            :settings="item.data.settings"
                            :data="item.data.generated">
              </ve-waterfall>
              <!--  生成K线图 -->
              <ve-candle v-if="item.data.settings.type === 'candle'"
                         class="filler-chart-container"
                         :width="item.width + 'px'"
                         :height="item.height + 'px'"
                         :backgroundColor="item.backgroundcolor"
                         :extend="item.data.extend"
                         :settings="item.data.settings"
                         :data="item.data.generated">
              </ve-candle>
            </div>

          </vue-drag-resize>

        </div>
      </vue-draggable-resizable>
    </div>

  </div>
</template>

<script>
export default {
  name: "EditCanvas",

  data() {
    return {
      currentElementWidth: this.$parent.currentElementIndex,
      screenDraggable: false,
    }
  },

  computed: {
    //调取EditIndex内的ChartData（可视化大屏全部数据）， 并且当ChartDat变化时，可以同时改变
    getChartData: function () {
      return this.$parent.chartData
    },

    //返回当前元素
    getCurrentElement: function () {
      return this.$parent.getCurrentElement
    },


    //返回画布的背景样式，如果画布没有图片数据，则只返回画布的颜色，如果存在背景图片，则返回背景图片和图片样式
    getScreenStyle: function () {
      if (this.getChartData.backgroundsetting === "背景颜色") {
        return {
          backgroundColor: this.getChartData.backgroundcolor
        }
      }
      else if (this.getChartData.backgroundsetting === "背景图片"){
        if (this.getChartData.backgroundimage === "") {
          return {
            backgroundImage: null,
            backgroundSize: this.getChartData.backgroundimagesize
          }
        }
        else {
          return {
            backgroundImage: `url(${this.getChartData.backgroundimage})`,
            backgroundSize: this.getChartData.backgroundimagesize
          }
        }
      }
      else {
        return null
      }
    },

    getContentData: function () {
      return this.$refs.contentData
    },

    getScale: function () {
      console.log(this.$parent.scale)
      return this.$parent.scale
    }
  },

  mounted() {
  },

  methods: {
    //在画板区域按下空格，控制画布是否可以移动
    handleSpaceDown: function (event) {
      //解决多次触发问题
      if (event.repeat) {
        return;
      }
      this.screenDraggable = true
    },
    //不按空格，或者抬起空格，画布的无法移动
    handleSpaceUp: function () {
      this.screenDraggable = false
    },

    //监控当前活跃的元素，并传入到父组件index中
    handleActivated: function (index) {
      this.$parent.setActiveComponentByIndex(index)
    },

    //监听画布的位置和大小的移动(重新设置了大小，并且拖动才会触发)
    handleChartResize: function (arg) {
      //agr[0]: left, arg[1]: top, arg[2]: width, arg[3]: height
      this.getChartData.rightresever = arg[0]
      this.getChartData.top = arg[1]
      this.getChartData.width = arg[2]
      this.getChartData.height = arg[3]
    },
    //监听画布的位置移动(仅被拖动)
    handleChartDrag: function (arg) {
      //arg[0]: left, arg[1]: top
      this.getChartData.rightresever = arg[0]
      this.getChartData.top = arg[1]
    },

    //监听控件大小的改变和位移
    handleElementResize: function (element, arg, index) {
      //arg[0]改变后的left, top, width, height
      this.handleActivated(index)
      let ResizeElement = element
      if(this.$parent.currentElementIndex >= 0) {
        ResizeElement.rightresever = arg.left
        ResizeElement.top = arg.top
        ResizeElement.width = arg.width
        ResizeElement.height = arg.height
      }
    },
    // 监听控件位置的改变（只有位置）
    handelElementDrag: function (element, arg, index) {
      this.handleActivated(index)
      let ResizeElement = element
      if(this.$parent.currentElementIndex >= 0) {
        ResizeElement.top = arg.top
        ResizeElement.rightresever = arg.left
      }
    },
  }
}
</script>

<style>
.screen {
  position: relative;
  width: 100%;
  height: 100%;
  overflow: hidden;
}

.edit-view {
  /*画板区域，将整个画布覆盖*/
  position: relative;
  width: 100%;
  height: 100%;
  overflow: hidden;
  outline: 0;
  background: #F8F8F8;
}

.screen-box-draggable {
  /*画布被拖动时的样式 鼠标样式改变*/
  border: 2px dashed grey;
  cursor: grab;
}

.screen-box-draggable-tips {
  position: absolute;
  top: 5px;
  right: 0;
  color: grey;
  padding: 0 20px 0 0;
  opacity: 0.6;
  user-select:none;
}

.canvas {
  position: relative;
  width: 100%;
  height: 100%;
  overflow: hidden;
  border: 1px solid grey;
  background-position: center center;
  background-repeat: no-repeat;
}

.filler {
  position: relative;
}

.filler-chart-container {
  width: 100%;
  position: relative;
}


.filler-text-container {
  /*文本控件统一样式： 文本换行不会打破单词书写 文本无法选中*/
  width: 100%;
  height: 100%;
  user-select:none;
  overflow: hidden;
}

.filler-image-container {
  width: 100%;
  height: 100%;
  background-position: center center;
}

.filler-border-container {
  /*边框控件统一样式：最小高度和宽度为100px*/
  box-sizing: border-box;
  min-height: 100px;
  min-width: 100px;
  width: 100%;
  height: 100%;
}


.filler-border-container-border1 {
  /*边框样式id为1*/
  border: 50px solid transparent;
  border-image: url('../../../assets/img/borders/1古典-棕.png') 50;
}

.filler-border-container-border2 {
  /*边框样式id为2*/
  border: 50px solid transparent;
  border-image: url('../../../assets/img/borders/2古典-白.png') 50;
}

.filler-border-container-border3 {
  /*边框样式id为3*/
  border: 50px solid transparent;
  border-image: url('../../../assets/img/borders/3古典-黑.png') 50;
}


.filler-border-container-border4 {
  /*边框样式id为3*/
  border: 50px solid transparent;
  border-image: url('../../../assets/img/borders/4科技.png') 50;
}

.filler-border-container-border5 {
  /*边框样式id为3*/
  border: 50px solid transparent;
  border-image: url('../../../assets/img/borders/5科技-蓝.png') 50;
}

.filler-border-container-border6 {
  /*边框样式id为3*/
  border: 50px solid transparent;
  border-image: url('../../../assets/img/borders/6科技-紫.png') 50;
}

.filler-border-container-border7 {
  /*边框样式id为3*/
  border: 50px solid transparent;
  border-image: url('../../../assets/img/borders/7传统-红.png') 50;
}

.filler-border-container-border8 {
  /*边框样式id为8*/
  border: 50px solid transparent;
  border-image: url('../../../assets/img/borders/8欧式-金.png') 50;
}

/*.filler-border-container-border5 {*/
/*  !*边框样式id为3*!*/
/*  border: 50px solid transparent;*/
/*  border-image: url('../../../assets/img/borders/5科技-蓝.png') 50;*/
/*}*/

/*.filler-border-container-border5 {*/
/*  !*边框样式id为3*!*/
/*  border: 50px solid transparent;*/
/*  border-image: url('../../../assets/img/borders/5科技-蓝.png') 50;*/
/*}*/

</style>