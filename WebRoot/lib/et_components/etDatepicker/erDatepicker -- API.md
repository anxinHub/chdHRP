# etDatepicker API #


[TOC]

### 基本配置 ###

#### `classes: null` ####

- 类型: Sting

> 添加class

#### `inline: false` ####

- type: Boolean

> 一直显示日期控件

#### `view: "days"` ####

- type: String
- days/months/years

> 开始视图时显示日期类型。

#### `minView: "days"` ####

- type: String
- days/months/years

#### `showNav: true` ####

- type: Boolean
- 显示隐藏nav
- 可用于，当只选择月份时，`view:'months',minView: 'months',showNav: false`

> 最小显示日期类型。配合view，dateFormat使用

#### `dateFormat: "yyyy-mm-dd"` ####

- type: String
- @毫秒为单位、dd数字日、DD星期、mm数字月份、MM中文月份、yyyy年份、yy两位年份、yyyy1十年的第一年、yyyy2十年的最后一年

> 日期格式化

#### `minDate: null` ####

- type: String
- "yyyy-mm-dd"
- `first_dd`月初1号、`last_dd`月末最后一天
- "yyyy-mm-last_dd"表示当年当月的最后一天

> 限制最小可选择日期

#### `maxDate: null` ####

- type: String
- 同minDate

> 限制最大可选择日期

#### `defaultDate:  null` ####

- type: Boolean/String/ Array
- 默认false。为true时，默认显示当天日期。
- 为string时，可以自定义默认显示日期。"yyyy-mm-dd"。`first_dd`1号。`last_dd`每月最后一天。"yyyy-mm-first_dd"表示当年当月的1号
- 为Array时，可输入同上格式的日期数组。
  - 数组length为2时，且`range`参数为true则显示的是`arr[0]`到`arr[1]`的日期。  
  - 当`multipleDates`为true是，则表示多选日期，数组内的日期将依次放入input中
  - 注意，`range`参数和`multipleDates`参数不建议同时为true。

> 初始默认显示日期 

#### `todayButton: true` ####

- type: Boolean

> 显示今日按钮

#### `clearButton: true` ####

- type: Boolean

> 清楚按钮

#### `autoClose: true` ####

- type: Boolean

> 选择完后自动关闭

#### `multipleDates: false` ####

- type: Boolean

> 为true是多选日期。为数字为日期选择个数限制

#### `range: false` ####

- type: Boolean

> 范围选择日期

#### `multipleDatesSeparator: ","` ####

- type:  String

> 日期之间分割符。已经默认，`rang: true`时，值为' 至 '。`multipleDates: true`时，值为', '

#### `toggleSelected: false` ####

- type: Boolean

> false时点击同天日期可以选择同一天，true时点击同一天取消选择

#### `keyboardNav: true` ####

- type: Boolean

> 开启热键

#### `position: "bottom left"` ####

- type: String

> 定位位置

#### `offset: 12` ####

- type: Number

> 定位偏移

#### `timepicker: false` ####

- type: Boolean

> 可以选择小时，分钟

#### `timeFormat: 'hh:ii aa'` ####

- type: String

> 小时分钟格式化，默认24小时制。aa加上为12小时制

#### `maxHours: null, minHours: null` ####

- type: Number

> 最大最小的小时值

#### `maxMinutes: null, minMinutes: null` ####

- type: Number

> 最大最小的分钟值

### 事件 ###

#### `onChanged(formattedDate, date, inst)` ####

> 选择日期时回调
> 同onSelect(),参数除了第一个，都相同。

- `formattedDate`格式化日期或数组
- `date`日期对象或日期对象数组
- `inst`datepicker实例

#### `onShow(inst，animationCompleted)` ####

> 日历显示时回调

- `inst`datepicker实例
- `animationCompleted` datepicker的显示动画指示器。如果`false`表示刚刚开始显示，如果`true`已经显示完成

#### `onHide(inst，animationCompleted)` ####

> 日历隐藏时回调

- `inst`datepicker实例
- `animationCompleted` datepicker的隐藏动画指示器。如果`false`表示刚刚开始隐藏，如果`true`已经隐藏完成

#### `onChangeMonth(month, year)` ####

> 月份更改时的回调

#### `onChangeYear(year)` ####

> 年份更改时回调

#### `onChangeDecade(decade)` ####

> 十年改变时回调

#### `onChangeView(view)` ####

> datepicker视图更改时回调

#### `onRenderCell(date, cellType)` ####

> datepicker渲染日期时的回调
> 回调必须返回
> ```javascript
> {
>     html: "", // 自定义单元格的内容
>     classes: '', // 额外的class
>     disabled:  false //  true/false。true时禁用对应的日期
> }
> ```

- `date`当前日期
- `cellType`: 当前单元格类型

### 方法 ###

#### `show()` ####

> 显示datepicker

#### `hide()` ####

> 关闭datepicker

#### `destroy()` ####

> 销毁

#### `next()` ####

> 根据当前视图，渲染下个月/下个年/下个十年

#### `prev()` ####

> 根据当前视图，渲染上个月/上个年/上个十年

#### `setValue(date)` ####

> 设置日期

- `date`可以为日期字符串或者数组

#### `getValue()` ####

> 获取当前被选日期
> 返回。

#### `clear()` ####

> 清楚选择日期

#### `update(filed[, value])` ####

> 更新datepicker的option。调用后重新渲染
> 可以单个修改值，也可以是个object

#### `view` ####

> 可以直接修改当前视图
> `datapicker.view = 'months'`

#### `$el` ####

> `datapicker.$el`获取datepicker的dom节点
