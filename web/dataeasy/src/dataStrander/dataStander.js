/**
 * chartData: {
 *   userid: Number  //所属用户id
 *   chartid： Number  //画布id(唯一标识)
 *   title: String  //画布标题
 *   width: number  //宽度(只需要数字)
 *   height: number  //高度(只需要数字)
 *   backgroundSetting: '背景颜色' | '背景图片'  //设置背景的分类
 *   backgroundColor: '0xNumber'  //背景颜色(十六进制文本)
 *   backgroundImage: url  //背景图片
 *   backgroundImageSize: 'contain' | 'cover' | '100% 100%'  //背景图片的样式，分别是覆盖， 平铺，拉伸
 *
 *   // 用于记录画布中的控件，一共有四种
 *   element: [
 *     {
 *        id: Number  //控件标识符
 *        active: true | false  //控件是否处于活跃状态(只要布尔值,默认为false)
 *        width: number  //宽度(只要数字)
 *        height: number  //高度(只要数字)
 *        top： number  //向Y轴位移多少px(只要数字)
 *        left: number  //向X轴位移多少px(只要数字)
 *
 *        //以下设置只有当控件不为image时，有用
 *        backgroundSetting: '背景颜色' | '背景图片'  //设置背景的分类
 *        backgroundColor: '0xNumber'  //背景颜色(十六进制文本)
 *
 *        //控件的基本数据
 *        data: {
 *          type: 'chart' | 'text' | 'image' | 'border'  //控件类型， 一共有四种（图表，文本，图片，边框）
 *
 *          //图表控件具体设置
 *          //部分设置
 *          settings: {
 *              type: 'line' 折线图 | ‘histogram’ 柱状图 | ‘bar’ 条形图
 *                    'pie' 饼图    | 'funnel' 漏斗图    | 'scatter'  散点图
 *                    'waterfall' 瀑布图  | 'candle' K线图  | 'wordcloud' 词云图
 *          },
 *          //
 *          extend: {
 *
 *          },
 *          //图表数据
 *          generated: {
 *              rows: [],
 *              columns: [{}, {}, {}]
 *          }
 *
 *          //文本控件具体数据
 *          datacon: {
 *             text: String  //文本内容
 *             fontFamilyEnglish: String  //英文字体
 *             fontFamilyChinese: String  //中文字体
 *             fontSize: Number  //字体大小,单位px(只要数字)
 *             lineHeight: Number  //行高，保持字体垂直居中
 *             bold: 'bold' | 'normal'  //字体加粗，字体不加粗
 *             italic: 'italic' | 'normal'  //斜体字体， 字体正常样式
 *             color: '0xNumber'  //字体颜色(十六进制文本)
 *             textAlign: 'left' | 'center' | 'right'  //文本水平放置方式，左对齐，居中对齐和右对齐(默认为左对齐)
 *             textStroke: true | false  //是否启用字体描边
 *             textStrokeSize: Number | 0  //文本描边， 默认为0(不显示)
 *             textStrokeColor: '0xNumber'  //文本描边颜色(十六进制文本， 默认为黑色)
 *             textShadow: true | false  //是否启用文本阴影
 *             textShadowX: Number | 0 //文本阴影，向X轴移动的距离(只要数字)
 *             textShadowY: Number | 0//文本阴影，向Y轴移动的距离(只要数字)
 *             textShadowBlur: Number | 0 //文本阴影， 阴影的散射范围
 *             textShadowColor: '0xNumber'  //文本阴影，阴影的颜色(十六进制文本)
 *          }
 *
 *          //图片控件具体数据
 *          datacon: {
 *             backgroundImage: url  //背景图片
 *             backgroundImageSize: 'cover' | 'contain' | '100% 100%'  //背景图片的样式，分别是覆盖，平铺，拉伸
 *             backgroundPositionX: Number | 0  //图片在容器中的X轴位置(容器中心为中心点)(px为单位)
 *             backgroundPositionY: Number | 0  //图片在容器中的Y轴位置(px为单位)
 *             opacity: Number  //透明度(范围[0-1])
 *          }
 *
 *          //边框空间具体数据
 *          datacon: {
 *              opacity: Number  //透明度(范围[0-1])
 *              borderId: '1' | '2' | '3'  //等
 *          }
 *        }
 *     },
 *   ]
 * }
 * */