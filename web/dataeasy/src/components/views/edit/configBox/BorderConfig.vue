<template>
  <div>
    <!--  边框样式  -->
    <div class="config-box">
      <div class="title">边框样式选择</div>
      <el-select v-model="getCurrentElement.data.borderId" placeholder="请选择样式" class="select-box-style">
        <el-option label="古典-棕" value="1"></el-option>
        <el-option label="古典-白" value="2"></el-option>
        <el-option label="古典-黑" value="3"></el-option>
        <el-option label="科技" value="4"></el-option>
        <el-option label="科技-蓝" value="5"></el-option>
        <el-option label="科技-紫" value="6"></el-option>
        <el-option label="传统-红" value="7"></el-option>
        <el-option label="欧式-金" value="8"></el-option>
      </el-select>
    </div>
    <!--  边框透明度  -->
    <div class="config-box">
      <div class="title">透明度</div>
      <el-input  v-model="getCurrentElement.data.opacity" class="opacity-input"><template slot="append">Max:1</template><template slot="prepend">Min:0</template></el-input>
      <el-slider v-model="getCurrentElement.data.opacity" class="slider" :max="1" :min="0" :step="0.01"></el-slider>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return{

    }
  },

  computed: {
    //获取当前活跃的控件
    getCurrentElement: function () {
      console.log(this.$parent.$parent.getCurrentElement)
      return this.$parent.$parent.getCurrentElement
    },
  },

  methods: {

  }
}
</script>

<style scoped>
.config-box {
  border-top: 1px solid rgba(0, 0, 0, 0.06);
  margin: 0;
  padding: 14px 20px;
}

.title {
  font-weight: bold;
  font-size: 0.86rem;
  margin-bottom: 12px;
}

.select-box-style {
  width: 245px;
}

.opacity-input /deep/ input {
  text-align: center;
}

.opacity-input /deep/ div {
  text-align: center;
  width: 40px;
}

.slider {
  width: 235px;
}
</style>