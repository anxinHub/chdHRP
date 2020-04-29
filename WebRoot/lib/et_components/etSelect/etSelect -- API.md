# etSelect API #

[TOC]

### 基本配置 ###

#### `options: []` - 静态选项 ####

- 类型: Array
- 静态选择项目数组
- `[ { id: 1, text: '是' }, ... ]`


#### `items: []` - 初始选择 ####

- 类型:  Array
- 初始选择值的数组
- 

#### `delimiter: ','` - 分隔符 ####

- 类型: String
- 用于分隔项目的字符串
- 
#### `create: false` - 创建 ####

- 类型: boolean/function
- 是否能创建选项
- 允许用户创建不在初始选项列表中的新项目

#### `createOnBlur: false` ####

- 类型: Boolean
- 是否当用户退出该字段时，创建并选择新选项
- 配合`create:true`使用

#### `createFilter: null` ####

- 类型: RegExp | string | function
- 接受过滤文本并返回是否允许
- 配合`create:true`使用

#### `highlight: true` - 高亮 ####

- 类型: Boolean
- 在下拉菜单中匹配突出显示

#### `persist: true`  ####

- 类型: Booelan
- 如果为false，用户创建的项目一旦被取消选择就不会显示为可用选项

#### `openOnFocus: true` - 聚焦下拉 ####

- 类型: Boolean
- 当控件接受到焦点时，立即显示下拉列表

#### `maxOptions: 1000` - 最大项目数 ####

- 类型: int
- 下拉列表一次性呈现的最大项目数

#### `maxItems：1` - 可选择数 ####

- 类型: int
- 用户可选择的最大项目数
- `1`时单选，`null`时不限

#### `hideSelected:false` - 选择后不显 ####

- 类型: Boolean
- 如果为true，选择项目完后，下拉列表不显示对应项目
- 
#### `closeAfterSelect:false` - 选择后关闭 ####

- 类型: Boolean
- 如果为true，则在进行选择后下拉菜单将关闭。

#### `allowEmptyOption: false` 允许空值 ####

- 类型: Boolean
- 如果为true，将处理`""`值

#### `scrollDuration: 60` - 动画时间 ####

- 类型: int
- 向上向下时动画的持续时间
- 
#### `loadThrottle: 300` - 查询节流 ####

- 类型: int
- 服务端请求等待毫秒数，用于模糊查询

#### `placeholder: undefiend` - 占位符 ####

- 类型: String
- 占位符

#### `preload: false` - 预加载 ####

- 类型: boolean/string
- 预加载

#### `dropdownParent:null` - 附着位置 ####

- 类型: String
- 下拉列表附加的位置。

#### `addPrecedence: false` ####

- 类型:  Boolean
- 为true时`'添加...'`选项时下拉列表中的默认选择

#### `selectOnTab: false` ####

- 类型: Boolean
- 如果为true，则选项卡键将选择当前选择的项目

#### `diacritics: true` ####

- 类型: Boolean
- 启用或禁用国际字符支持。

#### `valueField: 'id'` -  项目value属性名 ####

- 类型: String
- 将id作为项目value名

#### `labelField: 'text'` - 项目呈现属性名 ####

- 类型: Sring
- 将text作为项目呈现的属性名

#### `searchField: ['text']` - 搜索属性名 ####

- 类型: Array
- 搜索是分析的属性名称

---

> 自增属性 ↓

#### `defaultValue: ''` - 默认值 ####

- 类型: String
- 同items。

#### `url: ''` - 请求路径 ####

- 类型: String
- 请求路径。默认POST请求

#### `showClear: true` - 关闭按钮 ####

- 类型: Boolean
- 是否显示关闭按钮

#### `checkboxMode: false` - 下拉复选框 ####

- 类型: Boolean
- 将开启复选框模式，默认禁用关闭按钮，增加全部选择框，勾选全选，取消全不选。

### 事件 ###

#### `load(query, callback)` - 加载 ####

- 参数: query， callback
- 使用当前查询字符串和一个回调函数调用结果加载时的结果

#### `score(search)` - ####

- 参数: search

#### `onInitialize()` - 初始化 ####

- 完全初始化后调用

#### `onFocus()` - 聚焦 ####

- 当控件获得聚焦 时调用。

#### `onBlur()` - 失焦 ####

- 当控件失去焦点时调用。

#### `onChange(value)` - 更改选择 ####

- 参数: value
- 当控件的值更改时调用。

#### `onItemAdd(value, $item)` - 选择 ####

- 参数: value，$item
- 选择项目时调用。

#### `onItemRemove(value)` - 取消选择 ####

- 参数: value
- 取消选择项目时调用。

#### `onClear()` - 清除 ####

- 当通过clear()法手动清除控件时调用。点击x按钮时也会调用

#### `onDelete(values)` - 删除选择 ####

- 当用户尝试删除当前选择时调用。

#### `onOptionAdd(value, data)` - 添加项目 ####

- 当将新选项添加​​到可用选项列表时调用。

#### `onOptionRemove(value)` - 移除项目 ####

- 从可用选项中删除选项时调用。

#### `onDropdownOpen($dropdown)` - 打开下拉 ####

- 当下拉列表打开时调用。

#### `onDropdownClose($dropdown)` - 关闭下拉 ####

- 下拉关闭时调用。

#### `onType(str)` - 键入过滤 ####

- 当用户键入过滤选项时调用。

#### `onLoad(data)` - 新选项加载 ####

- 参数: data
- 当新选项加载并添加到控件（通过load选项或loadAPI方法）时调用。

---

### 方法 ###


#### 常用 ####

#### `setValue(value, silent)`- 设置值 ####

- 参数：value，silent
- 将所选项目重置为给定值。

#### `getValue()` - 获取值 ####

- 返回控件的值

#### `clearItem()` - 清除值 ####

- 同clear，清除所选值


#### 选项操作 ####

#### `addOption(data)` - 添加选项  ####

- 参数： data: Object/Array
- 添加可用选项或选项数组。
- 注意：这不刷新选项列表下拉列表（使用refreshOptions（））

#### `updateOption(value, data)` - 更新选项  ####

- 参数： value , data
- 更新可供选择的选项。如果在所选项目或选项下拉列表中可见，则会自动重新呈现。

#### `removeOption(value)` - 删除选项  ####

- 参数： value
- 	删除由给定值标识的选项。

#### `clearOptions()` - 删除所有选项  ####

- 从控件中删除所有选项。

#### `getOption(value)` - 获取选项 ####

- 参数： value
- 检索由给定值标识的选项的jQuery元素。

#### `getAdjacentOption(value, direction)` -  ####

- 参数: value, direction
- 检索相对于当前突出显示的选项的上一个或下一个选项的jQuery元素。direction“next” 的参数应为1，“previous” 的参数应为-1。

#### `refreshOptions(triggerDropdown)` - 刷新选项  ####

- 参数：triggerDropdown
- 刷新自动完成下拉菜单中显示的可用选项列表。

#### 项目操作 ####

> 注
> value指对应项目的value
> silent，如果为true，那么原来的输入就不会改变事件

#### `clear(silent)` - 清除所有项目  ####

- 参数： silent： Booelan，为true，原来的输入就不会改变事件
- 清除所有选定的项目

#### `getItem(value)` - 获取指定项目  ####

- 参数：value
- 返回与给定值匹配的项目的jQuery元素。 

#### `removeItem(value, silent)` - 删除指定项目 ####

- 参数： value， silent
- 删除与提供的值匹配的所选项目

#### `createItem(value, [triggerDropdown], [callback])` -  ####

- 参数： value，triggerDropdown，callback
- 在create为true时使用

#### `refreshItems()` - 刷新项目 ####

- 	重新显示所选项目列表。

#### Dropdown ####

#### `open()` - 打开下拉  ####

- 显示包含可用选项的自动完成下拉列表。

#### `close()` - 关闭下拉  ####

- 关闭自动完成下拉菜单。

#### `positionDropdown()` - 调整位置 ####

- 计算并应用下拉菜单的适当位置。

#### 其他 ####

#### `destroy()` - 销毁 ####

- 破坏控件并取消绑定事件侦听器，使其可以被垃圾回收。

#### `load(fn)` - 加载  ####

- 参数：fn：回调函数
- 通过调用提供的函数来加载选项

#### `focus()` - 聚焦 ####

- 使聚焦

#### `blur()` - 失焦 ####

- 使失焦

#### `disable()` - 禁用 ####

- 禁用

#### `enable()` - 启用 ####

- 启用

#### `isFull()` - 是否选更多  ####

- 返回用户是否可以选择更多的项目。
- 返回

#### `clearCache(template)` - 清除缓存 ####

- 清除渲染缓存

#### `updatePlaceholder()` - 更新占位符 ####

- 当`settings.placeholder`值更改时，会显示新的占位符。

#### `setTextboxValue(str)` - 设置输入字段  ####

- 设置输入字段的值。
