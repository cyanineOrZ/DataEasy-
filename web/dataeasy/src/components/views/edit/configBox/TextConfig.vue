<template>
  <div>
    <!--  文本控件内容设置  -->
    <div class="config-box">
      <div class="title">文本内容</div>
      <el-input v-model="getCurrentElement.data.text" placeholder="请输入文本内容"></el-input>
    </div>
    <!--  文本控件字体大小设置  -->
    <div class="config-box">
      <div class="title">字体大小</div>
      <el-input v-model.number="getCurrentElement.data.fontSize"></el-input>
    </div>
    <!--  文本控件字体设置  -->
    <div class="config-box">
      <div class="title">字体加粗</div>
      <el-switch
          active-text="加粗"
          inactive-text="普通"
          active-value="bold"
          inactive-value="normal"
          active-color="#13ce66"
          v-model="getCurrentElement.data.bold"
          style="margin-bottom: 10px"
      ></el-switch>

      <div class="title">字体斜体</div>
      <el-switch
          active-text="斜体"
          inactive-text="普通"
          active-value="italic"
          inactive-value="normal"
          active-color="#13ce66"
          v-model="getCurrentElement.data.italic"
      ></el-switch>
    </div>
    <!--  文本控件字体设置  -->
    <div class="config-box">
      <div class="title">字体设置</div>
      <div class="title" style="font-size: 14px;">英文字体设置</div>
      <el-select v-model="getCurrentElement.data.fontFamilyEnglish" class="select-box-style">
        <el-option label="Arial" value="Arial"><span :style="{fontFamily: 'Arial'}">Arial</span></el-option>
        <el-option label="Comic Sans MS" value="Comic Sans MS"><span :style="{fontFamily: 'Comic Sans MS'}">Comic Sans MS</span></el-option>
        <el-option label="Courier New" value="Courier New"><span :style="{fontFamily: 'Courier New'}">Courier New</span></el-option>
        <el-option label="Georgia" value="Georgia"><span :style="{fontFamily: 'Georgia'}">Georgia</span></el-option>
        <el-option label="Hei" value="Hei"><span :style="{fontFamily: 'Hei'}">Hei</span></el-option>
        <el-option label="Monaco" value="Monaco"><span :style="{fontFamily: 'Monaco'}">Monaco</span></el-option>
        <el-option label="simhei" value="simhei"><span :style="{fontFamily: 'simhei'}">simhei</span></el-option>
        <el-option label="Tahoma" value="Tahoma"><span :style="{fontFamily: 'Tahoma'}">Tahoma</span></el-option>
        <el-option label="Time News Roman" value="Time News Roman"><span :style="{fontFamily: 'Time News Roman'}">Time News Roman</span></el-option>
        <el-option label="Trebuchet MS" value="Trebuchet MS"><span :style="{fontFamily: 'Trebuchet'}">Trebuchet</span></el-option>
        <el-option label="Verdana" value="Verdana"><span :style="{fontFamily: 'Verdana'}">Verdana</span></el-option>
      </el-select>
      <div class="title" :style="{fontSize: '12px', fontWeight: 200, fontFamily: getCurrentElement.data.fontFamilyEnglish, marginLeft: '10px'}">Sample Text</div>
      <div class="title" style="font-size: 14px; margin-top: 10px">中文字体设置</div>
      <el-select v-model="getCurrentElement.data.fontFamilyChinese" class="select-box-style">
        <el-option label="宋体" value="SimSun"><span :style="{fontFamily: 'SimSun'}">宋体</span></el-option>
        <el-option label="黑体" value="SimHei"><span :style="{fontFamily: 'SimHei'}">黑体</span></el-option>
        <el-option label="微软雅黑" value="Microsoft YaHei"><span :style="{fontFamily: 'Microsoft YaHei'}">微软雅黑</span></el-option>
        <el-option label="微软正黑体" value="Microsoft JhengHei"><span :style="{fontFamily: 'Microsoft JhengHei'}">微软正黑体</span></el-option>
        <el-option label="新宋体" value="NSimSun"><span :style="{fontFamily: 'NSimSun'}">新宋体</span></el-option>
        <el-option label="细明体" value="MingLiU"><span :style="{fontFamily: 'MingLiU'}">细明体</span></el-option>
        <el-option label="标楷体" value="DFKai-SB"><span :style="{fontFamily: 'DFKai-SB'}">标楷体</span></el-option>
        <el-option label="仿宋" value="FangSong"><span :style="{fontFamily: 'FangSong'}">仿宋</span></el-option>
        <el-option label="楷体" value="KaiTi"><span :style="{fontFamily: 'KaiTi'}">楷体</span></el-option>
        <el-option label="华文仿宋" value="STFangsong MS"><span :style="{fontFamily: 'STFangsong'}">华文仿宋</span></el-option>
        <el-option label="华文黑体" value="STHeiti"><span :style="{fontFamily: 'STHeiti'}">华文黑体</span></el-option>
      </el-select>
      <div class="title" :style="{fontSize: '12px', fontWeight: 200, fontFamily: getCurrentElement.data.fontFamilyChinese, marginLeft: '10px'}">所选字体示例</div>
    </div>
    <!--  文本控件字体颜色设置  -->
    <div class="config-box">
      <div class="title">字体颜色</div>
      <el-color-picker v-model="getCurrentElement.data.color" style="margin-bottom: 10px"></el-color-picker>
      <el-input :disabled="true" v-model="getCurrentElement.data.color"><template slot="prepend">十六进制颜色</template></el-input>
    </div>
    <!--  文本控件字体位置设置  -->
    <div class="config-box">
      <div class="title">字体位置</div>
      <el-select v-model="getCurrentElement.data.textAlign" class="select-box-style">
        <el-option label="left(左对齐)" value="left"></el-option>
        <el-option label="center(居中对齐)" value="center"></el-option>
        <el-option label="right(右对齐)" value="right"></el-option>
      </el-select>
    </div>
    <!--  文本控件文本描边设置  -->
    <div class="config-box">
      <div class="title">文本描边设置</div>
      <!--  文本描边开关  -->
      <div class="text-switch-box">
        <div class="title">{{testStrokeSetting}}</div>
        <el-switch v-model="getCurrentElement.data.textStroke" class="switch" active-color="#13ce66"></el-switch>
      </div>
      <!--  文本描边具体设置  -->
      <transition name="fade">
        <div v-show="getCurrentElement.data.textStroke">
          <div class="title">文本描边大小</div>
          <el-input v-model.number="getCurrentElement.data.textStrokeSize"><template slot="append">px</template></el-input>

          <div class="title" style="margin: 10px 0">文本描边颜色</div>
          <el-color-picker v-model="getCurrentElement.data.textStrokeColor" style="margin-bottom: 10px"></el-color-picker>
          <el-input :disabled="true" v-model="getCurrentElement.data.textStrokeColor"><template slot="prepend">十六进制数</template></el-input>
        </div>
      </transition>
    </div>
    <!--  文本控件文本阴影设置  -->
    <div class="config-box">
      <div class="title">文本阴影设置</div>
      <!--  文本阴影开关  -->
      <div class="text-switch-box">
        <div class="title">{{textShadowSetting}}</div>
        <el-switch v-model="getCurrentElement.data.textShadow" class="switch" active-color="#13ce66"></el-switch>
      </div>
      <!--  文本阴影具体设置  -->
      <transition name="fade">
        <div v-show="getCurrentElement.data.textShadow">
          <div class="title">文本控件阴影X轴偏移设置</div>
          <el-input v-model="getCurrentElement.data.textShadowX" class="shadow-Input"><template slot="append">px</template></el-input>

          <div class="title">文本控件阴影Y轴偏移设置</div>
          <el-input v-model="getCurrentElement.data.textShadowY" class="shadow-Input"><template slot="append">px</template></el-input>

          <div class="title">文本控件阴影散射设置</div>
          <el-input v-model="getCurrentElement.data.textShadowBlur" class="shadow-Input"><template slot="append">px</template></el-input>

          <div class="title">文本控件阴影颜色设置</div>
          <el-color-picker v-model="getCurrentElement.data.textShadowColor" style="margin-bottom: 10px"></el-color-picker>
          <el-input :disabled="true" v-model="getCurrentElement.data.textShadowColor"><template slot="prepend">十六进制数</template></el-input>
        </div>
      </transition>
    </div>

  </div>
</template>

<script>
export default {
  data() {
    return {

    }
  },

  computed: {
    //获取当前活跃的控件
    getCurrentElement: function () {
      console.log(this.$parent.$parent.getCurrentElement)
      return this.$parent.$parent.getCurrentElement
    },

    //字体描边是否开启文本
    testStrokeSetting() {
      return this.getCurrentElement.data.textStroke === true? "开启": "关闭"
    },

    //字体阴影是否开启文本
    textShadowSetting() {
      return this.getCurrentElement.data.textShadow === true? "开启": "关闭"
    }
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
/*控件设置标题*/
.title {
  font-weight: bold;
  font-size: 0.86rem;
  margin-bottom: 12px;
}

/*字体设置选择框*/
.select-box-style {
  width: 245px;
  margin-bottom: 10px;
}

/*文本描边，文本阴影开关文本和开关的容器样式*/
.text-switch-box {
  display: flex;
  justify-self: flex-start;
  margin-bottom: 10px;
}
/*开关样式*/
.switch {
  margin-left: 10px;
}

/*文本阴影各项输入框样式*/
.shadow-Input {
  margin-bottom: 10px;
}

/*动画效果*/
.fade-enter-active, .fade-leave-active{
  transition: opacity 0.5s;
}
.fade-enter, .fade-leave-to {
  opacity: 0;
}
</style>