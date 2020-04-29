# etDialog API #

[TOC]

> 用例

** 普通操作 **

```javascript
$.etDialog.success('操作成功');

$.etDialog.confirm('确定删除？',
	function (index, el) {
		console.log('已删除')
	},
	function (index, el) {
		console.log('取消删除了')
	}
)
```

** 当前页通过父级 全屏打开 子页面 **

```javascript
parent.$.etDialog.open({
	url: '...',
	isMax: true,
	btn: ['确定'， '取消'],
	title: '科室职工工资总表',
	frameName: window.name,
	btn1: function (index, el) {
		var iframeWindow = parent.window[el.find('iframe').get(0).name];
		iframeWindow.save();
	}
})

/* 子级页面的save方法， 当要保存并且关闭时 */
function save () {
	ajax ... {
		// ...
		success: function () {
			// 保存成功后的回调
			var curIndex = parent.$.etDialog.getFrameIndex(window.name);
			parent.$.etDialog.close(curIndex);
		}
	}
}
/* 子级页面调取页面的query方法 */
function getParentQuery () {
	var parentFrameName = parent.$.etDialog.parentFrameName; // 拿取window名
	var parentWindow = parent.window[parentFrameName]; // 当前页拿取window对象
	parentWindow.query(); // 执行当前页query方法
}
```

** 打开普通的子页面 **

```javascript
$.etDialog.open({
	url: '...',
	btn: ['确定'， '取消'],
	title: '科室职工工资总表',
	btn1: function (index, el) {
		var iframeWindow = window[el.find('iframe').get(0).name];
		iframeWindow.save();
	}
})

/* 子级页面的save方法， 当要保存并且关闭时 */
function save () {
	ajax ... {
		// ...
		success: function () {
			// 保存成功后的回调
			var curIndex = parent.$.etDialog.getFrameIndex(window.name);
			parent.$.etDialog.close(curIndex);
		}
	}
}

/* 子级页面调取页面的query方法 */
function getParentQuery () {
	parent.query(); // 执行当前页query方法
}
```

> 小坑：通过父级弹出的iframe层，和当前弹出的iframe层，用法区别是，
> 父级的在`etDialog`,`window`等前面要加上`parent.`。没加就会报错。
> 子页面在任何情况下操作父页面，都要加上`parent`

### 基本参数 ###

#### `title: '提示'` - 标题 ####

- Type: String
- 标题。填写为false不显示标题栏

#### `content: ''` - 内容 ####

- Type: String/DOM/Array
- 可以传入要显示的内容

#### `url: ''` - iframe路径 ####

- Type: String
- 当加入url参数，并填入路径时，则开启iframe。

#### `width:'auto' / height:'auto' / maxWidth: 360` - 高宽 ####

- Type: String/Number
- 宽高，默认自适应

#### `offset` - 位置 ####

- Type: String/Array
- 默认垂直水平居中
- `offset: '100px'` top距离
- `offset: ['100px', '50px']`,top距离，left距离
- `offset: 't'`即top置顶，`'t' 'b' 'l' 'lt' 'lb' 'rt' 'rb'`英文首字母，字面理解

#### `btn: '确认'` - 按钮 ####

- Type: String/Array
- 默认确认按钮，可以自定义多个按钮
- `btn: ['入库确认', '打印', '取消']`

> TIPs
> btn 对应的点击事件 ，为`btn1:fun, btn2: fn, btn3: fn`，类推。
> 参数1,`index`是打开层的唯一索引
> 参数2，`el`是打开层的jq对象，可以find iframe
> 
> 第二个按钮开始点击都会自动关闭，第一个要自己写关闭方法`erDialog.close(index)`
> 
> 如果想要**执行子iframe的事件**，可以通过`window[el.find('iframe')[0].name]`获取到对应的页面的window对象，来获取页面上的全局变量或函数。
> 如果开始是`parent.etDialog.open...`，通过父页面打开，那么获取window对象也要加`parent` 。`parent.window[el.find('iframe')[0].name]`

```javascript
etDialog.open({
	url: '...',
	btn: ['入库确认', '取消'],
	btn1: function (index, el) {
		var childFrame = window[el.find('iframe')[0].name]; // 获取window对象
		
		childFrame.save(); // 执行ziifram的save事件

		etDialog.close(index); // 关闭弹窗
	}
})
```

#### `btnAlign: 'right'` 按钮排列 ####

- Type: String
- 按钮排列方向，`'right'  'left'  'center'`

#### `shade: 0.3` - 遮罩 ####

- Type: STring/Array
- 遮罩层的样式设置。默认`shade: [0.3, '#000']`
- `shade: [0.8, '#393D49']`
- 设为0时，无遮罩

#### `shadeClose: false` ####

- Type: Boolean
- 是否点击遮罩关闭

#### `time: 0` - 自动关闭 ####

- Type: Number
- 延时关闭层。默认不关闭。
- `time: 5000` 五秒后关闭

#### `anim: 0` - 动画 ####

- Type: Number
- 设置为-1时取消动画

#### `isOutAnim: true` - 关闭动画 ####

- Type: Booelan
- 关闭动画

#### `maxmin: false` - 最大小化 ####

- Type: Boolean
- 最大化最小化按钮是否显示

#### `fixed: true` - 固定 ####

- Type: Boolean
- 鼠标滚动时，层是否固定在可视区

#### `resize: false` - 拉伸 ####

- Type: Boolean
- 是否允许拉伸
- `resizing: fn(el)`拉伸监听事件

#### `scrollbar: true` - 滚动条 ####

- Type: Boolean
- 是否允许浏览器出现滚动条

#### `zIndex: 19891914` - 层级 ####

- Type: Number
- 层叠顺序

#### `moveOut: false` - 移动 ####

- Type: Boolean
- 是否允许拖到窗外
- `moveEnd: fn(el)拖动结束的回调函数`

### 事件 ###

> 注：参数中，el表示层的jq对象，index表示当前层的唯一索引，用于调用方法时填入参数

#### `success: null` - 弹出后 ####

- 参数`el, index`
- 层创建完毕时回调

#### `yes: null` - 确定 ####

- 参数`index, el`
- 确定按钮回调

#### `cancel: null` - 取消 ####

- 参数`index, el`
- 右上角关闭回调

#### `end: null` - 销毁 ####

- 无参数
- 层销毁回调

#### `full: null/nin:null/restore:null` ####

- 参数`el`
- 最大化最小化还原回调

#### `frameName: window.name` - 设置名 ####

- 当要在当前页通过父级打开全屏子页面时，加入的参数，
- 以便子页面调取当前页面的参数。

### 方法 ###

> 所有方法会自动向左补齐
> 如`layer.alert(content, options, yes)`,
> 可以只填`layer.alert(content, yes)`
> 如要要传入options参数，则必要放在回调参数之前，即第二个参数


#### `config(options)` - 全局设置 ####

- 初始化全局配置

#### `ready(callback)` - 就绪 ####

- 初始化就绪。页面一个开就可以执行弹层


#### `open(options)` - iframe层 ####

- 原始核心方法，可以用来打开核心层 

#### `alert(content, options, yes)` - 弹框 ####

- alert弹出框

#### `success(content, options, yes)` - 弹框 ####

#### `error(content, options, yes)` - 弹框 ####

#### `warn(content, options, yes)` - 弹框 ####

#### `confirm(content, options, yes, cancel)` - 询问 ####

- 

#### `msg(content, options, end)` - 提示 ####

- 提示框，可以设置time控制。默认显示3000ms


#### `load(options)` - loading ####

- 加载层。不会自动关闭。一般手动在ajax回调中关闭

---

> 通用方法

#### `close(index)` - 关闭 ####

- 关闭特定层，index为对应层的返回值
- `var loadIndex = etDialog.load()`这样获取index

#### `closeAll(type)` - 关闭全部 ####

- 可以关闭全部，也可以根据类型关闭
- `'dialog'` 信息框
- `'page'` 页面层
- `'iframe'`iframe层
- `'loading'` loading层
- `'tip'`tip层

#### `title(title, index)` - 重置标题 ####

- 改变对应框的标题

#### `setTop(el)` - 多层置顶 ####

-  置顶当前层

#### `full()` `min()` `restore()` ####

- 手动执行最大最小


---

> iframe层专用的方法

#### `getChildFrame(selector, index)` ####

- 获取iframe页的dom

```javascript
success:function (el, index) {
	var childFrameBody = etDialog.getChildFrame('body', index);
}
```

#### `getFrameIndex(windowName)` ####

- 获取特定iframe层的索引

```javascript 
var theIndex = parent.etDialog.getFrameIndex(window.name);
parent.layer.close(theIndex)
```

#### `iframeAuto(index)`  ####

- 会让iframe重新自适应

#### `iframeSrc(index, url)` ####

- 重置url

#### `parentFrameName` ####

- 并不是方法，只是一个etDialog的属性。
- 在当前页面通过父页面打开子页面时，可以在子页面获取当前页面的window name
- 必须在当前页面上open里面设置参数,`frameName: window.name`

```javascript
var parentFrameName = parent.$.etDialog.parentFrameName; // 拿取window名
var parentWindow = parent.window[parentFrameName]; // 当前页拿取window对象
parentWindow.queryNew(); // 执行当前页query方法
```
