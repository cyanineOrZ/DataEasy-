<template>
  <div style="position:relative;">
    <!--  切换全屏状态按钮  -->
    <div class="enter-fullscreen" @click="toggleFullscreen(document)" :class="{'enter-fullscreen-fullscreen': isFullScreen}">切换为{{documentStatus}}</div>
    <div class="screen" :style="{scale: getScale}">
      <div class="canvas" :style="chartStyle" ref="contentData">
        <!--控件预览模式的基本容器-->
        <!--控件生成-->
        <div v-for="(item, index) in this.getChartData.elements"
             :key="item.elementid"
             class="component"
             :style="{
             width: item.width + 'px',
             height: item.height + 'px',
             top: item.top + 'px',
             left: item.rightresever + 'px',
             zindex: index,
           }"
        >
          <!--文本控件-->
          <div v-if="item.type === 'text'"   class="filler" :style="{width: '100%', height: '100%', backgroundColor: item.backgroundcolor}">
            <div class="filler-text-container"
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
                 v-text="item.data.text"
            >
            </div>
          </div>
          <!--边框控件-->
          <div v-if="item.type === 'border'" class="filler" :style="{width: '100%', height: '100%', backgroundColor: item.backgroundcolor}">
            <div class="filler-border-container"
                 :class="'filler-border-container-border' + item.data.borderId"
                 :style="{
                    opacity: item.data.opacity
                  }"
            ></div>
          </div>
          <!--图片控件-->
          <div v-if="item.type === 'image'" class="filler"  :style="{width: '100%', height: '100%'}">
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
                :ref="'image' + index"></div>
          </div>
          <!--控件生成-->
          <div v-if="item.type === 'chart'" class="filler"  :style="{width: '100%', height: '100%'}">
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
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "PreView",

  data() {
    return {
      document: document.documentElement,
      isFullScreen: false,
      documentStatus: '全屏',
      screenShot: false
    }
  },

  mounted() {
    console.log(this.$router)
    this.$parent.preview = true
    this.isFullScreen = false
  },

  beforeDestroy() {
    this.isFullScreen = false
    window.removeEventListener('beforeunload', () => {
    });
  },

  computed: {
    getChartData: function () {
      return this.$parent.chartData
    },

    getScale: function () {
      return this.$parent.scale
    },

    //根据画布的背景设置来确定背景的样式
    chartStyle: function () {
      if (this.getChartData.backgroundsetting === "背景颜色") {
        return {
          width: this.getChartData.width + 'px',
          height: this.getChartData.height + 'px',
          top: this.getChartData.top + 'px',
          left: this.getChartData.rightresever + 'px',
          backgroundColor: this.getChartData.backgroundcolor,
        }
      }
      else {
        return {
          width: this.getChartData.width + 'px',
          height: this.getChartData.height + 'px',
          top: this.getChartData.top + 'px',
          left: this.getChartData.rightresever + 'px',
          backgroundImage: `url(${this.getChartData.backgroundimage})`,
          backgroundSize: this.getChartData.backgroundimagesize
        }
      }
    },

    getContentData: function () {
      return this.$refs.contentData
    }

  },

  methods: {
    //切换全屏和窗口
    toggleFullscreen: function (documentElement) {
      //适配不同浏览器
      let element = document.fullscreenElement || document.mozFullScreenElement || document.webkitFullscreenElement
      if (element != null) {
        //关闭全屏
        if (document.exitFullscreen) {
          document.exitFullscreen()
        }else if (document.mozFullScreenElement) {
          document.mozFullScreenElement()
        }else if (document.webkitFullscreenElement) {
          document.webkitFullscreenElement()
        }else if (documentElement.msExitFullscreen) {
          documentElement.msExitFullscreen()
        }
      }
      else {
        //开启全屏
        if (documentElement.requestFullscreen) {
          documentElement.requestFullscreen()
        } else if (documentElement.mozRequestFullScreen) {
          documentElement.mozRequestFullScreen()
        } else if (documentElement.msRequestFullscreen) {
          documentElement.msRequestFullscreen()
        } else if (documentElement.webkitRequestFullscreen) {
          documentElement.webkitRequestFullScreen()
        }
      }
      //切换文本
      this.isFullScreen = !this.isFullScreen
      if (this.isFullScreen) {
        this.documentStatus = '窗口'
        this.$parent.isFullScreen = true
      }
      else {
        this.documentStatus = '全屏'
        this.$parent.isFullScreen = false
      }
    },


  },
}
</script>

<style>
.screen {
  position: relative;
  width: 100%;
  height: 100%;
  overflow: visible;
}

/*进入全屏按钮*/
.enter-fullscreen {
  position: absolute;
  width: calc(100% + 370px);
  left: -185px;
  height: 60px;
  z-index: 999;
  color: #fff;
  font-size: 24px;
  line-height: 60px;
  text-align: center;
  background: grey;
  opacity: 0;
  transition: opacity 0.3s ease;
  user-select: none;
}

.enter-fullscreen-fullscreen {
  position: absolute;
  top: -100px;
}

.enter-fullscreen:hover {
  cursor: pointer;
  opacity: 1;
}

.canvas {
  position: relative;
  border: none;
}

.component {
  position: absolute;
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
</style>