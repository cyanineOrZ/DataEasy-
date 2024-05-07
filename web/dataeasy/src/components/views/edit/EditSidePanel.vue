<template>
  <div class="panel">
    <!-- 当panelKey不为空时，展示  -->
    <div class="title">
      <span>{{componentList[panelKey].name}}列表</span>
    </div>

    <!-- 控件列表 通过panKey来判断  -->
    <div class="component-list">
      <div class="component-list-item" v-for="item in componentList[panelKey].children" :key="item.name"
          @click="handleAddComponent(item)">
        <!--  控件图片     -->
        <div class="component-list-img-wrapper"><img class="component-list-img-wrapper-img" :src="item.img"></div>
        <!--  控件名称     -->
        <div class="component-list-img-wrapper-img-name">{{item.name}}</div>
      </div>
    </div>
    <span style="font-size: 12px; color: grey; opacity: 0.7; padding-left: 20px">再次点击相同控件图标即可关闭该窗口</span>
  </div>
</template>

<script>
export default {
  name: "EditSidePanel",

  components: {

  },

  props: ['panelKey'],

  data() {
    return {
      drag: false,
      //控件列表
      componentList: {
        chart: {
          name: "图表",
          children: [
            {
              id: "line",
              name: "折线图",
              img: require("@/assets/img/charts/line.png")
            },
            {
              id: "histogram",
              name: "柱状图",
              img: require("@/assets/img/charts/histogram.png")
            },
            {
              id: "bar",
              name: "条形图",
              img: require("@/assets/img/charts/bar.png")
            },
            {
              id: "pie",
              name: "饼图",
              img: require("@/assets/img/charts/pie.png")
            },
            {
              id: "funnel",
              name: "漏斗图",
              img: require("@/assets/img/charts/funnel.png")
            },
            {
              id: "scatter",
              name: "散点图",
              img: require("@/assets/img/charts/scatter.png")
            },
            {
              id: "waterfall",
              name: "瀑布图",
              img: require("@/assets/img/charts/waterfall.png")
            },
            {
              id: "candle",
              name: "K线图",
              img: require("@/assets/img/charts/candle.png")
            },
          ]
        },
        text: {
          name: "文本",
          children: [
            {
              id: "text",
              name: "文本",
              img: require("@/assets/img/charts/text.png")
            }
          ]
        },
        picture: {
          name: "图片",
          children: [
            {
              id: "image",
              name: "图片",
              img: require("@/assets/img/charts/image.png")
            }
          ]
        },
        tools: {
          name: "边框",
          children: [
            {
              id: "border",
              name: "边框",
              img: require("@/assets/img/charts/border.png")
            }
          ]
        }
      },
      chartid: parseInt(this.$parent.$parent.chartid)
    };
  },

  computed: {

  },

  methods: {
    //监听控件的增加
    handleAddComponent(item) {
      //假自增id
      let elementid;
      if (this.$parent.$parent.chartData.elements.length === 0) {
        elementid = 0
      }
      else {
        //获取最后一位的id，并进行+1
        elementid = this.$parent.$parent.chartData.elements[this.$parent.$parent.chartData.elements.length - 1].elementid + 1
      }
      //控件默认详细属性
      let initData = {}
      //文本组件的初始默认值
      if (item.id === "text") {
        initData = {
          type: "text",
          active: false,
          data: {
            text: "请输入文字",
            fontFamilyEnglish: "Arial",
            fontFamilyChinese: "Microsoft YaHei",
            fontSize: 16,
            bold: "normal",
            italic: "normal",
            color: "#000000",
            textAlign: "center",
            textStroke: false,
            textStrokeSize: 0,
            textStrokeColor: "#000000",
            textShadow: false,
            textShadowX: 0,
            textShadowY: 0,
            textShadowBlur: 0,
            textShadowColor: "#000000",
          }
        }
      }
      //图表组件的初始默认值
      else if(item.id === "image") {
        initData = {
          type: "image",
          active: false,
          data: {
            backgroundImage: "",
            backgroundImageSize: "cover",
            backgroundPositionX: 0,
            backgroundPositionY: 0,
            opacity: 1,
          }
        }
      }
      //边框组件的初始默认值
      else if (item.id === "border") {
        initData = {
          type: "border",
          active: false,
          data: {
            opacity: 1,
            borderId: "1"
          }
        }
      }
      //K线图
      else if (item.id === "candle") {
        initData = {
          type: 'chart',
          active: false,
          data: {
            settings: {
              type: "candle",
              dataType: 'connect',
              // 图源数据的id
              connectid: 0,
            },
            extend: {
              //图表定位
              grid: {
                top: '0px',
                left: '10px',
                right: '10px',
                bottom: '10px'
              },
              //图例基本设置
              legend: {
                show: false,
                left: 0,
                top: 0,
                orient: 'vertical'
              },
              //数据标签
              label: false
            },
            generated: {
              columns: ['日期', '开盘', '关盘', '最低点', '最高点', '交易量'],
              rows: [
                {日期: '2004-01-05', 开盘: 10411.85, 关盘: 10544.07, 最低点: 10411.85, 最高点: 10575.92, 交易量: 221290000},
                {日期: '2004-01-06', 开盘: 10543.85, 关盘: 10538.66, 最低点: 10454.37, 最高点: 10584.07, 交易量: 191460000},
                {日期: '2004-01-07', 开盘: 10535.46, 关盘: 10529.03, 最低点: 10432.12, 最高点: 10587.55, 交易量: 225490000},
                {日期: '2004-01-08', 开盘: 10530.07, 关盘: 10592.44, 最低点: 10480.59, 最高点: 10651.99, 交易量: 237770000},
                {日期: '2004-01-09', 开盘: 10589.25, 关盘: 10458.89, 最低点: 10420.52, 最高点: 10603.48, 交易量: 223250000},
                {日期: '2004-01-12', 开盘: 10461.55, 关盘: 10485.18, 最低点: 10389.85, 最高点: 10543.03, 交易量: 197960000},
                {日期: '2004-01-13', 开盘: 10485.18, 关盘: 10427.18, 最低点: 10341.19, 最高点: 10539.25, 交易量: 197310000},
              ]
            }
          },
        }
      }
      //其他图表控件初始默认值
      else {
        let chartType = item.id
        initData = {
          type: "chart",
          active: false,
          data: {
            settings: {
              type: chartType,
              area: false,
              showLine: [""],
              radius: 100,
              offsetY: 100,
              roseType: false,
              ascending: false,
              dataType: "connect",
              connectid: 0,
            },
            extend: {
              color: ['#023047', '#126883', '#279ebc', '#90c9e6', '#fc9e7f'],
              //图标定位
              grid: {
                top: 0,
                left: '10px',
                right: '10px',
                bottom: 0
              },
              //图例基本设置
              legend: {
                show: false,
                left: 0,
                top: 0,
                orient: 'vertical'
              },
            },
            generated: {
              columns: ['日期', "示例数据"],
              rows: [
                {日期: '1月1日', 示例数据: 1523},
                {日期: '1月2日', 示例数据: 1223},
                {日期: '1月3日', 示例数据: 2123},
                {日期: '1月4日', 示例数据: 4123},
                {日期: '1月5日', 示例数据: 3123},
                {日期: '1月6日', 示例数据: 7123},
              ]
            },
          }
        }
      }

      //控件默认基础属性
      let newComponent = {
        chartid: this.chartid,
        elementid: elementid,
        type: initData.type,
        active: initData.active,
        name: "新建控件" + this.$parent.$parent.chartData.elements.length,
        width: 100,
        height: 100,
        top: 10,
        left: 10,
        backgroundcolor: "#ffffff",
        data: initData.data
      }
      this.$parent.$parent.addComponent(newComponent)
    }
  }
}
</script>

<style scoped>

.panel {
  height: 560px;
  width: 250px;
  display: flex;
  position: relative;
  flex-direction: column;
  background: #ffffffe9;
  box-shadow: 4px 0 4px #00000005;
  border-top: 1px solid grey;
  border-right: 1px solid grey;
  border-bottom: 1px solid grey;
  border-top-right-radius: 8px;
  border-bottom-right-radius: 8px;
}
.panel::before {
  content: "";
  position: absolute;
  height: 90%;
  width: 1px;
  background: grey;
  opacity: 0.4;
  top: 5%;
  left: -1px;
}

.title {
  color: #999999;
  padding: 10px 16px 16px;
}

.component-list {
  flex: 1;
  width: 250px;
  padding: 0 10px 0 16px;

}

.component-list-item {
  display: inline-block;
  width: 100px;
  background: rgba(64, 160, 255, 0.06);
  border: 1px solid rgba(64, 160, 255, 0.1);
  margin-right: 10px;
  margin-bottom: 12px;
  opacity: 0.6;
  transition: opacity, background 0.3s ease;
  text-align: center;
  padding: 5px 0;
}

.component-list-item:hover {
  cursor: pointer;
  opacity: 0.8;
  background: rgba(64, 160, 255, 0.1);
  border: 1px solid #409eff;
}

.component-list-img-wrapper {
  display: flex;
  width: 100%;
  height: 80px;
  align-items: center;
  justify-content: center;
}

.component-list-img-wrapper-img {
  height: 54px;
}

.component-list-img-wrapper-img-name {
  height: 20px;
  line-height: 20px;
  font-size: 13px;
  color: #777777;
}

</style>
