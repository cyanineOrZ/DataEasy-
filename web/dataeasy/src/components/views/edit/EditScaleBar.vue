<template>
  <div class="panel">
    <!-- 控制缩放,通过点击按钮来控制 -->
    <div class="control-bar">
      <i class="btn el-icon-minus" @click.prevent="zoomOut"></i>
      <div class="scale-mount">{{Math.floor(scale * 100)}}%</div>
      <i class="btn el-icon-plus" @click.prevent="zoomIn"></i>
    </div>
  </div>
</template>

<script>
export default {
  name: "EditScaleBar",

  data() {
    return{
      scale: 1.0
    }
  },

  watch: {
    scale: {
      handler(newValue, oldValue) {
        if (newValue !== oldValue) {
          this.$emit("updateScale", this.scale)
        }
      }
    }
  },

  methods: {
    //缩小画布(每次缩小10%)
    zoomOut: function () {
      if (this.scale > 0.1) {
        this.scale = parseFloat((this.scale - 0.1).toFixed(1))
      }
    },

    //放大画布(每次放大10%)
    zoomIn: function () {
      if (this.scale < 1.2) {
        this.scale = parseFloat((this.scale + 0.1).toFixed(1))
      }
    }
  },
}
</script>

<style scoped>
.panel {
  width: 130px;
  border-radius: 8px;
  display: flex;
  align-items: center;
  background: #ffffffe9;
  border: 1px solid #cccccc;
}

.control-bar {
  width: 100%;
  display: flex;
  align-items: center;
  text-align: center;
  height: 32px;
  color: #666666;
  padding: 0 6px;
}

.btn {
  width: 24px;
  height: 24px;
  line-height: 24px;
  border-radius: 14px;
  font-size: 12px;
  transition: all 0.3s ease;
}
.btn:hover {
  cursor: pointer;
  background-color: #dddddd;
  color: #333333;
}

.scale-mount {
  flex: 1;
  font-size: 14px;
  user-select:none;
}
</style>