<template>
  <div>
    <!--  图表图例设置  -->
    <div class="config-box">
      <div class="title">图表图例设置</div>
      <el-switch
          active-text="开启图例"
          inactive-text="关闭图例"
          v-model="getCurrentElement.data.extend.legend.show">
      </el-switch>
      <!--  图标通用设定  -->
      <transition name="fade">
        <div v-show="getCurrentElement.data.extend.legend.show">
          <div class="title" style="margin-top: 10px">图例位置设定</div>
          <el-input v-model.number="getCurrentElement.data.extend.legend.left" class="chart-input"><template slot="prepend">X</template></el-input>
          <el-input v-model.number="getCurrentElement.data.extend.legend.top"  class="chart-input"><template slot="prepend">Y</template></el-input>
          <div class="title">图例样式设定</div>
          <el-select v-model="getCurrentElement.data.extend.legend.orient" class="chart-select-box">
            <el-option label="垂直" value="vertical"></el-option>
            <el-option label="水平" value="horizontal"></el-option>
          </el-select>
        </div>
      </transition>
    </div>
    <!--  图标颜色设置  -->
    <div class="config-box" v-show="getCurrentElement.data.settings.type !== 'candle'">
      <div class="title">图表颜色设置</div>
      <el-select class="chart-select-box" v-model="userColor" ref="colorSelect">
        <el-option v-for="(item, index) in colorList" :key="index" @click.native="changeColor" :label="colorLabel[index]" :value="JSON.stringify(item)"></el-option>
      </el-select>
      <div class="title" style="margin-top: 10px">颜色预览</div>
      <div class="color-box">
        <div v-for="(item, index) in JSON.parse(userColor)" :key="index" :style="{backgroundColor: item}" class="color-div"></div>
      </div>
    </div>
    <!--  折线图设置区域  -->
    <div class="config-box" v-show="getCurrentElement.data.settings.type === 'line'">
      <div class="title">折线图阴影开关</div>
      <el-switch
          active-text="开启投影"
          inactive-text="关闭投影"
          v-model="getCurrentElement.data.settings.area"></el-switch>
    </div>
    <!--  饼图设置区域  -->
    <div class="config-box" v-show="getCurrentElement.data.settings.type === 'pie'">
      <div class="title">饼图设置</div>
      <div class="title">饼图半径设置</div>
      <el-input v-model="getCurrentElement.data.settings.radius" class="chart-input"></el-input>
      <div class="title">饼图中心点Y轴设置</div>
      <el-input v-model="getCurrentElement.data.settings.offsetY" class="chart-input"></el-input>
      <div class="title">玫瑰图设定</div>
      <el-switch
          active-text="开启玫瑰图"
          inactive-text="关闭"
          v-model="getCurrentElement.data.settings.roseType"></el-switch>
    </div>
    <!--  漏斗图设置区域  -->
    <div class="config-box" v-show="getCurrentElement.data.settings.type === 'funnel'">
      <div class="title">漏斗图反转开关</div>
      <el-switch
          active-text="金字塔图"
          inactive-text="漏斗图"
          v-model="getCurrentElement.data.settings.ascending"></el-switch>
    </div>
    <!--  图标数据设定  -->
    <div class="config-box">
      <div class="title">图表数据配置</div>
      <el-select v-model="getCurrentElement.data.settings.dataType" class="chart-select-box">
        <el-option label="我的数据源" value="connect"></el-option>
        <el-option label="静态JSON" value="raw"></el-option>
      </el-select>

      <!--   数据源选择   -->
      <div v-show="getCurrentElement.data.settings.dataType === 'connect'">
        <div class="title" style="margin-top: 10px">选择数据源</div>
        <!--   选择数据源为我的数据源时出现   -->
        <el-select placeholder="请选择" v-model="connectId" class="chart-select-box" ref="selectDatID">
          <el-option v-show="getCurrentElement.data.settings.type === 'candle'" v-for="item in candleDataList" :key="item.dataid" :value="item.dataid" :label="item.dataname"></el-option>
          <el-option v-show="getCurrentElement.data.settings.type !== 'candle'" v-for="item in chartDataList" :key="item.dataid" :value="item.dataid" :label="item.dataname"></el-option>
        </el-select>
        <div class="title" style="font-size: 12px; font-weight: 400; margin-top: 10px; color: #cd4432">请注意，选择数据后请点击确认选择，数据才会更替</div>
        <div class="title" style="font-size: 12px; font-weight: 400; margin-top: 10px; color: #cd4432">数据更替后如果不保存将不会替换原有数据</div>
        <el-button @click="selectData" type="primary" style="width: 110px;margin-right: 10px">确认选择</el-button>
      </div>
      <!-- JSON编辑器-->
      <div style="margin-top: 10px" v-show="getCurrentElement.data.settings.dataType === 'raw'">
        <div class="title">JSON数据编辑</div>
        <div v-show="true" class="JSON-contain">
          <vue-json-editor
              :modes="['text']"
              mode="text"
              v-model="JSONData"
          ></vue-json-editor>
        </div>
        <div class="title" style="font-size: 12px; font-weight: 400; margin-top: 10px; color: #cd4432">数据更替后如果不保存将不会替换原有数据</div>
        <div style="font-size: 12px; font-weight: 400; margin-top: 10px;color: red; margin-bottom: 10px">数据被替换后将无法复原</div>
        <el-button @click="saveJSONData" type="primary">SAVE</el-button>
      </div>
      <!--   数据预览   -->
      <div v-if="currentData" style="margin-top: 10px">
        <div class="title">数据预览</div>
        <div class="data-preview-contain">
          <el-table :data="currentData.exdata" border>
            <el-table-column v-for="(item, index) in currentData.columns" :key="index" :prop="item" :label="item" :width="'120px'"></el-table-column>
          </el-table>
        </div>
      </div>

    </div>
  </div>
</template>
<script>
import vueJsonEditor from 'vue-json-editor'
export default {
  data() {
    return{
      chartDefault: {
        candleDefault: {
          dataid: 0,
          dataname: "默认数据",
          dimension: "日期",
          metrics: ['开盘', '关盘', '最低点', '最高点', '交易量'],
          columns: ['日期', '开盘', '关盘', '最低点', '最高点', '交易量'],
          exdata: [
            {日期: '2004-01-05', 开盘: 10411.85, 关盘: 10544.07, 最低点: 10411.85, 最高点: 10575.92, 交易量: 221290000},
            {日期: '2004-01-06', 开盘: 10543.85, 关盘: 10538.66, 最低点: 10454.37, 最高点: 10584.07, 交易量: 191460000},
            {日期: '2004-01-07', 开盘: 10535.46, 关盘: 10529.03, 最低点: 10432.12, 最高点: 10587.55, 交易量: 225490000},
            {日期: '2004-01-08', 开盘: 10530.07, 关盘: 10592.44, 最低点: 10480.59, 最高点: 10651.99, 交易量: 237770000},
            {日期: '2004-01-09', 开盘: 10589.25, 关盘: 10458.89, 最低点: 10420.52, 最高点: 10603.48, 交易量: 223250000},
            {日期: '2004-01-12', 开盘: 10461.55, 关盘: 10485.18, 最低点: 10389.85, 最高点: 10543.03, 交易量: 197960000},
            {日期: '2004-01-13', 开盘: 10485.18, 关盘: 10427.18, 最低点: 10341.19, 最高点: 10539.25, 交易量: 197310000},
          ],
          //K线图默认数据
        },
        //其他图表默认数据
        chartDefault: {
          dataid: 0,
          dataname: "默认数据",
          dimension: '日期',
          metrics: ["示例数据"],
          columns: ['日期', "示例数据"],
          exdata: [
            {日期: '1月1日', 示例数据: 1523},
            {日期: '1月2日', 示例数据: 1223},
            {日期: '1月3日', 示例数据: 2123},
            {日期: '1月4日', 示例数据: 4123},
            {日期: '1月5日', 示例数据: 3123},
            {日期: '1月6日', 示例数据: 7123},
          ]
        }
      },
      connectId: 0,

      candleDataList: [],
      chartDataList: [],

      currentData: null,

      userColor: null,

      colorLabel: ["经典配色1", "经典配色2", "冷淡",
          "永恒", "热情", "干练", "青春", "绿草", "科技"
      ],

      colorList: [
        ["#023047","#126883","#279ebc","#90c9e6","#fc9e7f"],
        ["#f66f69","#feb3ae","#fff4f2","#1597a5","#0e606b"],
        ["#333c42","#316658","#5ea69c","#c2cfa2","#a4799e"],
        ["#352660","#553b94","#9872ca","#f6e7ed","#5f203d"],
        ["#611e1e","#cd4432","#ef9163","#ecc29b","#f3d9be"],
        ["#211a3e","#453370","#a597b6","#fef3e8","#d06c9d"],
        ["#76a2b9","#bfd9e5","#d64f38","#f8f2ec"],
        ["#658873","#b9c78d","#e7e3e4","#d2bfa5"],
        ["#6fa0ac","#b5d3d9","#5d887b","#fceee2"],
      ],

      JSONData: {},
    }
  },

  components: {
    vueJsonEditor
  },

  computed: {
    //获取当前活跃的控件
    getCurrentElement: function () {
      this.getColor()
      this.getJSONData()
      return this.$parent.$parent.getCurrentElement
    },

  },

  mounted() {
    this.addDefault()
    // this.addColorOption()
  },

  methods: {
    //给用户数据添加默认值
    addDefault: function () {
      let candleDataList = JSON.parse(JSON.stringify(this.$parent.$parent.userData))
      let chartDataList = JSON.parse(JSON.stringify(this.$parent.$parent.userData))
      candleDataList.unshift(this.chartDefault.candleDefault)
      chartDataList.unshift(this.chartDefault.chartDefault)
      this.candleDataList = candleDataList
      this.chartDataList = chartDataList
    },

    getColor: function () {
      if (this.$parent.$parent.getCurrentElement.type === 'chart') {
        this.userColor = JSON.stringify(this.$parent.$parent.getCurrentElement.data.extend.color)
      }
    },

    getJSONData: function () {
      if (this.$parent.$parent.getCurrentElement.type === 'chart') {
        this.JSONData = this.$parent.$parent.getCurrentElement.data.generated
      }
    },

    //改变图表颜色
    changeColor: function () {
      this.getCurrentElement.data.extend.color = JSON.parse(this.$refs.colorSelect.value)
    },

    //通过connectID获取选择的data对象
    selectData: function () {
      let selectData
      if (this.getCurrentElement.data.settings.type === 'candle') {
        selectData = this.candleDataList.find((item) => {
          return item.dataid === this.$refs.selectDatID.value
        })
      }
      else {
        selectData = this.chartDataList.find((item) => {
          return item.dataid === this.$refs.selectDatID.value
        })
      }
      this.currentData = selectData

      //数据列指定
      this.getCurrentElement.data.generated.rows = this.currentData.exdata
      this.getCurrentElement.data.generated.columns = this.currentData.columns
    },

    saveJSONData: function () {
      console.log(this.JSONData)
      console.log(JSON.parse(JSON.stringify(this.JSONData)))
      this.getCurrentElement.data.generated = JSON.parse(JSON.stringify(this.JSONData))
    },
  },
}
</script>

<style scoped>
/*控件各部分设置容器*/
.config-box {
  border-top: 1px solid rgba(0, 0, 0, 0.06);
  margin: 0;
  padding: 14px 20px;
}
/*控件设置标题*/
.title {
  font-weight: bold;
  font-size: 0.86rem;
  margin-bottom: 10px;
}

.chart-input {
  width: 245px;
  margin-bottom: 10px;
}
.chart-input /deep/ span span {
  text-align: center;
  width: 50px;
}

.color-box {
  display: flex;
  flex-direction: row;
  justify-content: space-around;
  width: 245px;
}

.color-div {
  flex: 1;
  height: 30px;
  margin-right: 10px;
}

.chart-select-box {
  width: 245px;
}

.data-preview-contain {
  width: 245px;
  border: 1px solid #DCDFE6;
  border-radius: 4px;
}

.JSON-contain {
  width: 260px;
  margin-top: 10px;
  margin-left: -5px;
}

.JSON-contain /deep/ > div > div > div > div > textarea {
  min-height: 300px;
}
</style>