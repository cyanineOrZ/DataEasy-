<template>
  <div>
    <!--  删除控件区域  -->
    <div class="config-box">
      <el-button type="danger" @click="removeComponent">删除控件</el-button>
    </div>

    <!--  控件名称设置区域  -->
    <div class="config-box">
      <div class="title">控件名称</div>
      <el-input v-model="getCurrentElement.name"></el-input>
    </div>

    <!--  控件尺寸位置设置区域  -->
    <div class="config-box">
      <div class="title">控件尺寸</div>
      <el-input class="size-position-input" :min="0" v-model="getCurrentElement.width"><template slot="prepend">Width</template></el-input>
      <el-input class="size-position-input" :min="0" v-model="getCurrentElement.height"><template slot="prepend">Height</template></el-input>

      <div class="title">控件位置</div>
      <el-input class="size-position-input" :min="0" :disabled="true" v-model="getCurrentElement.rightresever"><template slot="prepend">X</template></el-input>
      <el-input class="size-position-input" :min="0" :disabled="true" v-model="getCurrentElement.top"><template slot="prepend">Y</template></el-input>
    </div>

    <!--  控件背景设置区域 非图片控件可设置 -->
    <div class="config-box" v-show="getCurrentElement.type !== 'image'">
      <div class="title">控件背景颜色</div>
      <el-color-picker v-model="getCurrentElement.backgroundcolor" style="margin-bottom: 10px"></el-color-picker>
      <el-input        v-model="getCurrentElement.backgroundcolor" @blur="updateColorPicker" ref="colorInput"><template slot="prepend">十六进制颜色</template></el-input>
      <div class="title" style="margin-top: 10px">透明背景</div>
      <el-switch v-model="transparentBackground" active-color="#13ce66" inactive-color="grey"></el-switch>
    </div>

    <!--  控件JSON格式查看区域  -->
    <div class="config-box">
      <div class="title">控件json-setting(只读)</div>
      <pre class="code-box" v-html="formatElementJSON" style="user-select: text"></pre>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return{
      transparentBackground: false,
      // meColor: this.$refs.backgroundColorInput.value
    }
  },

  watch: {
    //透明背景
    transparentBackground: {
      handler() {
        if (this.transparentBackground) {
          this.getCurrentElement.backgroundcolor = 'transparent'
        }
        else {
          this.getCurrentElement.backgroundcolor = "#FFFFFF"
        }
      }
    },
  },

  computed: {
    //获取当前活跃的控件
    getCurrentElement: function () {
      return this.$parent.$parent.getCurrentElement
    },

    formatElementJSON() {
      let currentElementIndex = this.$parent.$parent.currentElementIndex
      if (currentElementIndex < 0) {
        return null;
      }
      else {
        return this.$parent.$parent.chartData.elements[currentElementIndex]
      }
    },

  },

  methods: {
    updateColorPicker: function () {
      if(/^#([\dA-Fa-f]{6}|[\dA-Fa-f]{3})$/.test(this.$refs.colorInput.value)) {
        this.getCurrentElement.backgroundColor = '#' + this.colorInput
      }
      else {
        this.colorInput = ''
      }
    },

    removeComponent: function () {
      this.$parent.removeComponent()
    }
  }
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
  margin-bottom: 12px;
}

/*控件大小尺寸的输入框*/
.size-position-input {
  margin-bottom: 10px;
}

/*控件大小尺寸的插槽提示文本*/
.size-position-input /deep/ .el-input-group__prepend {
  text-align: center;
  width: 60px;
}

/*控件JSON内容的容器*/
.code-box {
  border: 1px solid #eeeeee;
  border-radius: 4px;
  padding: 6px;
  overflow: scroll;
}
</style>