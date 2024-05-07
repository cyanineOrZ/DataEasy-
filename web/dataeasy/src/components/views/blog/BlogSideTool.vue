<template>
  <div>
    <div class="personBlog-tool-side-module-contain" style="color: grey; font-size: 14px">侧边工具栏</div>
    <!--   时间与日历    -->
    <div class="personBlog-tool-side-module-contain">
      <!--   时间组件    -->
      <div class="personBlog-tool-side-module-contain-title">当前时间:</div>
      <div style="margin-bottom: 8px;margin-left: 8px">{{dayjsTime}}</div>
      <!--   日历组件    -->
      <div class="personBlog-tool-side-calendar">
        <el-calendar style="border-radius: 8px" class="el-calendar"></el-calendar>
      </div>
    </div>
       天气插件
    <div class="personBlog-tool-side-module-contain">
      <div class="personBlog-tool-side-module-contain-title">今日天气:</div>
      <div id="he-plugin-standard"></div>
    </div>
  </div>
</template>

<script>
const dayjs = require("dayjs")
const utc = require("dayjs/plugin/utc")
const timezone = require("dayjs/plugin/timezone")
dayjs.extend(utc)
dayjs.extend(timezone)
dayjs.tz.setDefault("Asia/Shanghai")

export default {
  data() {
    return {
      timeNow: dayjs(),
    }
  },

  computed: {
    dayjsTime: function () {
      return this.timeNow.format(('HH:mm:ss'))
    },
  },

  mounted() {
    //和风天气
    window.WIDGET = {
      "CONFIG": {
        "layout": "2",
        "width": "230",
        "height": "250",
        "background": "1",
        "dataColor": "FFFFFF",
        "borderRadius": "8",
        "key": "1d9bce87f63e47289d579f666f18c1e6"
      }
    }
    {
      let script = document.createElement("script");
      script.type = "text/javascript";
      script.src = "https://widget.qweather.net/standard/static/js/he-standard-common.js?v=2.0";
      document.getElementsByTagName("head")[0].appendChild(script)
    }
    //实时时间
    setInterval(() => {
      this.timeNow = dayjs()
    }, 1000)
  }
}
</script>

<style scoped>
/*博客内容侧边栏工具组件容器*/
.personBlog-tool-side-module-contain {
  padding: 10px 0;
  border-bottom: 1px solid grey;
}

.personBlog-tool-side-module-contain-title {
  color: grey;
  font-size: 14px;
  margin-bottom: 4px;
  margin-top: 4px;
  margin-left: 4px;
}

/*日历组件样式*/
.el-calendar /deep/ .el-button-group {
  width: 120px;
  display: flex;
  flex-wrap: wrap;
}
.el-calendar /deep/ .el-button-group button {
  padding: 8px 8px;
  flex: 1;
}
.el-calendar /deep/ .el-calendar__body{
  /*修改elementui日历的样式*/
  padding: 8px 8px;
}
.el-calendar /deep/ .el-calendar__body table thead th {
  font-size: 10px;
  padding: 0 0 4px;
}
.el-calendar /deep/ .el-calendar__body table tbody tr {
  font-size: 10px;
}
.el-calendar /deep/ .el-calendar__body table tbody tr td div{
  text-align: center;
  height: 100%;
}
</style>