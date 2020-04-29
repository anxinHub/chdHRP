# grid API #

[TOC]

### 基本配置 ###

> 约定
> 填写的为默认属性。

#### `columns:null`  列模块 ####


##### `align: "left"` #####

> 对齐

"left"/"right"/"center"

##### `cls: null` #####

> 设置class

##### `colModel:null` #####

> 多表头

##### `copy: null` #####

> 阻止列被复制到剪贴板并被导出到Excel

#####  `name: colIndx`列名 #####

> 列名

##### `dataType: string`数据类型 #####

> 类型

##### `editable: true`是否编辑 #####

> Boolean、Function

##### `editModel: null`编辑设置 #####

> 将会覆盖全局的editModel

##### `editor: null`编辑 #####

> 定制编辑器

```javascript
editor: {
	type: "textbox", // textbox，textarea，contenteditable，select，checkbox，callback function，html string或null
	cls: "",
	style: "",
	change:function () {

	}
}
```
eg:
```javascript
editor: {
	 type: 'select',  //编辑框为下拉框时
	 autoFocus:true,   //  为true时 下拉框默认选择第一个
	 disabled:false,
	 source:[],   //  静态数据接口  也可以是回调函数返回值
	 url:   ,      //  动态数据接口
	 open: function() {

	 },
	 select: function() {

	 },
	 change: function() {

	 },
	 close: function() {

	 }
 }
```
```javascript
editor: {
	 type: 'date',      // 日期控件时
     onSelect: function () {
	 },
	 beforeShow: function (input, inst) {
		
	 },
	 onClose: function () {
	
	 }
 }
```
```javascript
editor: {
	 type: 'grid',      // 表格控件时   参数跟etgrid参数一样
     dataModel:{
		 url:......,
	 },
	 ......             
 }
```

##### `filter: null`过滤 #####

> object

```javascript
filter: {
	type: "", // textbox, textarea, select, checkbox,
	attr: "",
	condition: "", //contain, notcontain, begin, end, equal, notequal, empty, notempty, less, great.
	value: 
}
```

##### `hidden: false` #####

> 隐藏

##### `width` 、`maxWidth`、`minWidth`  #####

##### `render(ui)`渲染 #####

> 返回单元格的数据

##### `resizable: true` #####

> 重新调整列

##### `isSort: true`排序 #####

> 是否启用排序

##### `display: null`显示文字 #####

> 列头显示文字

##### `type: null` #####

> `checkBoxSelection`、`detail`

##### `validation: null` 验证 #####

```javascript
validation: [
	{
		type: '', //minLen，maxLen，gt，gte，lt，lte，regexp，nonEmpty或callback函数
	   //eg: type:function(ui) {
	   //	var value = ui.value;
	   //	if (...) {
//			ui.msg = value + "没找到";
//			$('.pq-editor-focus').css  ('border-color','#f00');
	//		return false;
	   //	}	
	   //} 
		value: '',
		msg: '', // 提示内容
	}
]
```

#### `columnTemplate` ####

> 将会被深入克隆应用所有列中

#### `dataModel: null` 数据模块  ####

##### `beforeSend(jqXHR, setings)` #####

> 回调函数，发送请求前修改jqXHR，返回false将取消请求

##### `contentType: undefined` #####

> 将会覆盖默认请求头

##### `data: null` #####

> 本地直接传递的数据

##### `dataType: "TEXT"` #####

> "TEXT"/"JSON"/"XML"

##### `error(jqXHR, textStatus, errorThrown)` #####

> 错误回调函数

##### `getData(response, textStatus, jqXHR)` #####

> grid与服务端的中介器
> 需要返回包含data，curPage和totalRecords的对象

##### `getUrl()` #####

> 返回请求相关url，数据。指定后，忽略dataModel.url

##### `location: "remote"` #####

> 数据加载方式，local，remote

##### `method: "PSOT"` #####

> "PSOT","GET"

##### `postData: null` #####

> 传回后台数据

##### `postDataOnce:null` #####

> 传回后台数据，仅一次

##### `recIndex: null` 跟踪 #####

> 主键标识符  获取表格添加、删除、修改数据时需要它 值为colname 列名中的任意一个即可

##### `sortDir: "up"` #####

> 与`sortIndx`一起用，可以使
> `"up"`,`"down"`,或数组`["up",...]`

##### `sortIndx: null` #####

> 使用数组时，和sortDir对应，可以多列排序

##### `sort: "local"` #####

> "local"/"remote"

##### `url: null`请求路径 #####

> 请求路径

#### `detailModel: obj` 详细视图 ####

> 包含详细视图信息，对于表格嵌套很有用

```javascript
detailModel: {
	cache: true,  // 缓存细节视图。行的折叠和展开会导致刷新细节数据。false时查看
	init: fn(rowData), // 回调函数，接受rowData作为参数，必须将详细视图作为jq对象返回
	collapseIcon: "ui-icon-triangle-1-e",图标
	expandIcon: "ui-icon-triangle-1-se"图标
}
```

#### `dragColumns:obj` ####

```
dragColumns: {
	enable: true, // 默认启用
	acceptIcon：'ui-icon-check', // 默认图标
	rejectIcon：'ui-icon-closethick'，
	topIcon：'ui-icon-circle-arrow-s'，
	bottomIcon：'ui-icon-circle- arrow-n'
}
```

#### `draggable: false` ####

> 是够可拖动grid

#### `editableL: true`编辑 ####

> 是否可编辑，是函数时接收{rowData: rowData, rowIndx: rowIndx}
> Boolean、Function

#### `editModel: obj`编辑设置 ####

```
editModel: {
	clicksToEdit: 1, // 1,2,点击，双击进入编辑
	saveKey: "", // 除了Tab外，可以自定义ascll码 ，默认值'$.ui.keyCode.ENTER' enter键
	filterKeys: true, // 防止dataType定义的整数或浮点数有非数字
	keyUpDown: true, // 上下键导航编辑单元格 (若为true,则有可能影响单元格里的下拉框键盘控制 ,建议为false)
	onBlur: "validate"， // 使编辑器保持编辑状态
	allowInvalid: false, // 为true时不拒绝无效值
	invalidClass: "ui-tstate-error" // 验证失败是添加class
}
```

#### `editor: {type: "text", cls: "", style: "", select: false}`编辑类型 ####

> editor会被各个列`{ column: editor }`中被覆盖

#### `filterModel: obj` ####

```javascript
filterModel: {
	on: true, // 过滤器打开关闭属性
	mode: "AND", // 过滤模式"AND"/"OR"
	header: false, // 为true时，过滤器控件显示在列头中
	type: null // "local","remote"
}
```

#### `flexHeight: false`、`flexWidth: false` ####

> 高度调整。为true时，grid随内容自动撑开高度

#### `inWindowHeight: false` ####

> 默认值为 false

> 高度调整。 为true时， grid的height值为百分比时 以窗口window高度计算 


#### `freezeCols: 0` 、`freezeRows: 0` 冻结行列 ####

> 固定列.参数为需要固定的列数

#### `groupModel: null` 分组模块  ####

> 定义每页展示数据数

#####  `collapsed: [false, ...]` #####

> 定义组是否展开折叠、

##### `dataIndx: null` #####

> 数据的键名，，必须填写。不然不能分组。Array

##### `dir: ['up',...]` #####

> 向上或下的阵列

##### `icon:[...]` #####

##### `title: ["{0} - {1} items"]...` #####

#### `height: 400` `width: 500` ####

#### `historyModel: obj` ####

```javascript
historyModel: {
	on: true, // 开关历史
	allowInvalid: true, // 为true是接受无效单元格值
	checkEditable: true, // 为true时单元格行检查可编辑性
	checkEditableAdd: false // 撤销、重做期间添加行，检查单元格可编辑性
}
```

#### `hoverMode: 'row'` ####

> 提供单元格和行的mouseenter和mouseleave事件

#### `hwrap: true` ####

>  单元格文本是否溢出或换行

#### `minWidth: 50` ####

> 单元格最小宽度

#### `numberCell: obj` 序列 ####

```javascript
numberCell: {
	width: 50,
	title； "",
	reziable: false,
	minWidth: 50,
	show: true
}
```

#### `checkbox: true` 复选框 ####

> 复选框

#### `PageModel: obj` 分页模块 ####

##### `type: local` 分页模式  #####

> 分页方式 'local'(前台分页)，'remote'(后台分页)

##### `rPP: 10` #####

> 每页分页数

##### `rPPOptions: [10,20,50,100]` #####

> 分页器数量选择

##### `totalPages: 0` #####

> 分页器中的数据总值  

#### `pasteModel:obj` 粘贴模块  ####

#### `resizable: true` ####

> 调整大小

#### `roundCorners: true` ####

> 圆角

#### `scrollModel: obj` 滚动模块 ####

```javascript
scrollModel: {
	horizontal: true, // 滚动条是否可见
	pace: 'fast',  // 滚动速度
	autoFit: false, // 会更改列宽，当适合表格才不滚动
	lastColumn: 'auto', // 
	theme: false
}
```

#### `selectionModel: obj` 选择模块 ####

```javascript
selectionModel: {
	type: "cell", // "row"、"cell"、null
	mode: "range", // "single"or null  // single  时 复选框只能单选
	all: null, 
	cbAll: null, // 
	cbHeader: null // 
}
```

#### `showButton: true` ####

> 展示底部，和分页工具显示在底部

#### `showHeader: true` ####

> 确定列头，顶部工具等的展示

#### `showTop: true` ####

> 网格顶部列头上方

#### `showToolbar: true` ####

> 工具栏

#### `sortable: true` ####

> 使用排序

#### `stripeRows: true`####

> 奇偶行样式

#### `title: null` ####

> 标题

#### `trackModel: {on: true, dirtyClass: "pq-cell-dirty"}`操作跟踪模块 ####

> 需要使用`dataModel.recIndx`来跟踪工作

#### `toolbar: obj` 工具栏模块 ####

```javascript
toolbar: {
	cls: "",
	items: [
		{
			type: 'button',
			label: 'Add', // 按钮文字
			icon: '...',
			listeners:[{ // 事件监听
				click: clickFn
			}]
		}
	]
}
```
>	常用icon图标所对应的字符串 

|      | icon |
| :---- | :------: |
| 添加 | plus/add |
| 添加(加粗) | plusthick/add2 |
| 添加(圆圈背景) | circle-plus/add3 |
| 关闭 | close |
| 关闭(加粗) | closethick/close2 |
| 关闭(圆圈背景) | circle-close/close3 |
| 删除 | minus/delete |
| 删除 | minusthick/delete2 |
| 删除(圆圈背景) | circle-minus/delete3 |
| 返回/撤销 | arrowreturn-1-w/back |
| 返回(加粗) | arrowreturnthick-1-w/back2 |
| 清空 | trash/clean |
| 取消/禁用 | cancel |
| 查询 | search |
| 设置 | gear/settings |
| 确认/保存 | check/audit |
| 计算 | calculator/counter |
| 确认(圆圈背景) | circle-check/audit2 |
| 保存 | disk/save |
| 导出 | arrowstop-1-n/export |
| 导出(加粗) | arrowthickstop-1-n/export2 |
| 文档 | document |
| 下载/导入 | arrowstop-1-s/import |
| 下载(加粗) | arrowthickstop-1-s/import2 |
| 打印 | print |
| 复制 | copy |
| 刷新 | refresh |
| 刷新 | arrowrefresh-1-w/refresh2 |
| 帮助 | help |


#### `wrap: true` 换行 ####

> 文本换行，溢出隐藏，变为...

#### `summary:obj` 固定摘要行 ####
   
> 像合计行一样悬浮在表格底部  (前台计算合计、平均值等) 
```javascript 
		summary:{         //  摘要行集合
			totalColumns:['revenues','profits'],
			keyWordCol:'rank',   //关键字所在列的列名
			averageColumns:['revenues','profits'],
			maxColumns:['revenues','profits'],
			minColumns:['revenues','profits']
		}
```
#### `summaryRowIndx:[index1, index2,...]`  (后台渲染摘要行数据) ####
> 从后台返回的数据中通过给出的索引取出合计的数据 单独渲染到表格底部  
>  值为数据中摘要数据的索引 

#### `addRowByKey:true` ####
>   是否键盘控制增加行

### 事件 ###

#### `beforeSort(event, ui)` ####

> 排序前事件，返回false取消排序

#### `beforeTableView(event, ui)` ####

> 在表格数据即将呈现之前触发，只能用于**更新**，不能插入或删除

#### `beforeValidate(event, ui)` ####

> 表格数据更改之前，验证事件之前。
> 引用所有ui参数，修改参数会影响表格后续数据处理

#### `cellBeforeSave(event, ui)` ####

> 单元格保存之前触发。返回false取消数据保存

#### `cellClick(event, ui)` 单元格点击 ####

> 单元格点击事件

#### `cellDblClick(evnet, ui` 单元格双击 ####

> 单元格双击

#### `cellKeyDown(event, ui)` ####

> 选中单元格时按键触发。多选，在最后单元格接受输入。返回false可以防止grid默认处理

#### `cellRightClick(event, ui)` 单元格右击 ####

> 单元格右键事件

#### `cellSave(event, ui)` ####

> 单元格本地保存后触发。适用于更细其他单元格中的计算或依赖数据

#### `cellSelect(event, ui)` 单元格选择 ####

> 选择单元格时触发

#### `cellUnselect(event, ui)` 单元格取选 ####

> 单元格取消选择时触发

#### `change(event, ui)` 数据改变 ####

> 编辑完后数据变更触发。可以调用或粘贴行/单元格。撤销/重做/或提交/回滚来添加/更新/删除行
> 多处一起变更时触发一次。

#### `columnResize(event, ui)` ####

> 调整列大小后触发

#### `create(event, ui)` 表格创建 ####

> grid创建后触发。
> 本地使用。远程使用load事件

#### `load(event, ui)` 数据加载 ####

> 加载数据后触发

#### `editorBegin(event, ui)` 编辑前 ####

> 创建编辑器时触发

#### `editorBlur(event, ui)` 编辑失焦 ####

> 编辑器失焦时触发

#### `editorEnd(event, ui)` 编辑后 ####

> 销毁时触发 (单元格编辑完事件)

#### `editorFocus(event, ui)` 编辑聚焦 ####

> 聚焦时触发

#### `editorKeyDown(event, ui)` ####

> 定义键时触发。返回false防止编辑器默认行为

#### `editorKeyPress(event, ui)` ####

> 按住时

#### `editorKeyUp(event, ui)` ####

> 按键释放时

#### `headerCellClick(event, ui)`列头点击 ####

> 列头单击触发

#### `history(event, ui)`历史 ####

> 当触发`type = add`、 `canUndo = true | false`、 `canRedo = true | false`、 `type = undo | redo`、 `type = reset | resetUndo`时触发

#### `refresh(event, ui)`刷新 ####

> 刷新时触发

#### `refreshRow(event, ui)`刷新行 ####

> refreshRow()方法执行,行刷新时触发

#### `render(event, ui)`重渲染 ####

> DOM结构创建之后，完全初始化前触发。适用于添加工具栏等

#### `rowClick(event, ui)`行点击 ####

> 行点击后触发，在`cellClick`事件之后

#### `rowDblClick(event, ui)`行双击 ####

> 行双击，在`cellDblClick`事件后触发

#### `rowRightClick(event, ui)`行右击 ####

> 右键单击触发

#### `rowSelect(event, ui)`行选择 ####

> 行选择时触发

#### `rowUnSelect(event, ui)`行取选 ####

> 行取消选择触发

#### `selectChange(event, ui)`改变选择 ####

> 操作更改选择，或通过调用选择方法触发

#### `sort(event, ui)`	 ####

> 排序后触发

#### `changeRow(event, obj)`换行 ####

> 换行事件 (该事件只在键盘回车换行时执行)
> 参数: 鼠标属性 、 包含prevR与nowR属性的对象
> 返回值为 false时 阻止跳转


### 方法 ###

> 约定
> 加粗为必填

#### `setEvent(eventname, callback)`设置事件 ####

> 绑定自定义事件

- **eventname**
	Type: String
	事件名
	click、mousedown...
- **callback**
	Type: Function
	回调函数

#### `addRowClass(rowIndx, cls)` ####

> 行添加class

- **rowIndx**
	Type: number
	行索引
- **cls**
	Type: String
	判断的class，空格分割可添加多个

#### `addCellClass(rowIndx, dataIndx, cls)` ####

> 给单元格添加class

- **rowIndx**
	Type: number
	行索引
- **dataIndx**
	Type: Number/String
	键名或数组索引
- **cls**
	Type: String
	判断的class
#### `addRow(obj)` ####

> 末尾添加数据行
> 不填数据则加空行

- obj
	Type:  object
	行数据对象。

#### `insertRow(rowIndx, obj)`插入行 ####

> 指定行，插入数据行
> 不填则在前面添加空行

- rowIndx
	Type: number
	行号
- obj
	Type: object
	行数据对象

#### `collapse()` ####

> 折叠grid ！
	
#### `commit(type, rows)` ####

> 限制 添加提交删除操作 ！
> 不填则限制所有操作

- type
	Type:  String
	限制操作类型
	add、update、delete
- rows
	Type: array
	行对象数组，通常服务端获取

#### `createTable($cont, data)` ####

> 生成类似表结构，同步表。底部创建冻结列，合计等，很有用

- **$cont**
	Type:  Jquery obj
	容器
	e.g. `$(<div></div>)`
	
- **data**
	Type: array
	与表数据对应的 键值对对象 数组
	
#### `deleteRow(rowIndx)` 删除行 ####

> 从本地视图中删除一行

- **rowIndx**
	Type: number
	行索引
	
#### `destroy()` 销毁 ####

> 销毁真个gird

#### `disable()` 禁用 ####

> 禁用grid

#### `enable()` 启用 ####

> 启用grid
	
#### `editCell(rowIndxPage, dataIndx, isInAll)` ####

> 选定单元格开始编辑，聚焦

- **rowIndxPage**
	Type:  Number
	当前页行索引
	
- **dataIndx**
	Type:  string/number
	单元格的name或列索引
	
- isInAll
	Type:  boolean
	如果isInAll为true时，rowIndxPage变所有数据的行索引
	
#### `editFirstCellInRow(rowIndx)` ####

> 可编辑模式下 选定行第一个可编辑单元格开始编辑

- **rowIndx**
	Type: number

#### `expand()` ####

> 展开表格 ！

#### `exportExcel(url, sheetName)` ####

> 导出xml的excel格式文件 ！

- **url**
	Type: string
	导出路径
	
- **sheetName**
	Type: string
	文件名

#### `exportCsv(url)` ####

> 导出csv格式

- **url**
	Type: string
	导出路径

#### `filter(oper, data)` ####

> 过滤数据

- **oper**
	Type: string
	'replace'替换原有过滤/'add'添加条件，dataIndx相同则替换条件
	'replace' / 'add'
	
- **data**
	Type:  Array
	过滤数组，[键入{ dataIndx: 列索引或键名， condition: 过滤条件，value: 过滤的值，value2: 第二值 }]

#### `getCell(rowIndx, dataIndx, isInAll)` 获取单元格 ####

> 获取单元格
> 返回

- **rowIndx**
	Type: number
	当前页索引
	
- **dataIndx**
	Type: number/string
	单元格name，或列索引
	
- isInAll
	Type:  boolean
	true，rowIndx变成所有数据的行索引

#### `getCellsByClass(cls)` ####

> 根据class获取单元格
> 返回

- **cls**
	Type:  string
	单元格class名
	
#### `getCellIndices($td)` 获取单元格索引 ####

> 返回单元格的索引
> 返回

- **$td**
	Type:  jq obj
	单元格jquery对象
	
#### `getChanges(format)` ####

> 获取 未提交的，添加，更新，删除的行。(执行此方法时必须开启swipeModel{on:true},与obj.dataModel.recIndx)
> 返回

- **format**
	Type: String
	不填，返回引用的rowData，填"byVal"，克隆再返回
	'byVal'
	
#### `getColIndx(dataInx)` ####

> 根据键名获取列索引
> 返回

- **dataInx**
	Type: string
	列名
	
#### `getColumn(dataIndx)` ####

> 获取列信息
> 返回

- **dataIndx**
	Type: string
	列

#### `getColumns()` ####

> 获取columns
> 返回

#### `getData(colNameArr)` 获取指定数据 ####

> 根据列名数组，返回行对象数组
> 返回

- **colNameArr**
	Type: array
	列名数组
	
#### `getEditCell()` ####

> 返回当前编辑单元格
> 返回

#### `getEditCellData()` ####

> 返回当前编辑单元格关联的数据
> 返回

#### `getInstance()` ####

> 返回表格实例
> 返回

#### `getRowNode(rowIndxPage)` ####

> 获取行节点
> 返回

- **rowIndxPage**
	Type: number
	当前页行索引
	
#### `getRowData(rowindx, isInAll)` 获取行数据 ####

> 获取行数据
> 返回

- **rowindx**
	Type:  Number
	当前行索引
	
- **isInAll**
	Type: Boolean
	如果true是所有数据的索引
	
#### `getRowIndx($tr)` 获取行索引 ####

> 返回行索引
> 返回

- **$tr**
	Type:  jq obj
	行的jquery对象
	
#### `getRowsByClass(cls)` ####

> 返回行数据对象数组
> 返回

- **cls**
	Type:  String
	行class
	
#### `goToPage(pageNum)` ####

> 翻页，跳转指定页面

- **pageNum**
	Type: Number
	指定页数

#### `hasRowClass(rowIndx, cls)` ####

> 判断行有没有对应class
> 返回

- **rowIndx**
	Type: number
	行索引
	
- **cls**
	Type: String
	判断的class
	
#### `hasCellClass(rowIndx, dataIndx, cls)` ####

> 判断单元格有没有class
> 返回

- **rowIndx**
	Type: number
	行索引
	
- **dataIndx**
	Type: Number/String
	键名或数组索引
	
#### `hideLoading()` ####

> 隐藏加载图表

#### `history(method)` ####

> 操作历史记录
> method是canUndo或canRedo时返回

- **method**
	Type: String
	undo：反向添加更新删除, 
	redo：重复添加更新删除先前反转的操作, 
	canUndo：返回布尔值，是否可以执行进一步的撤销操作, 
	canRedo：返回布尔值，是否可以重复执行操作,
	 reset：清楚历史记录，不进行任何操作

#### `isDirty(rowData)` ####

> 判断提交后数据是否有变化
> 返回

- rowData
	Type: Srtng/number
	不填查所有。或填入行索引或行数据逐行查询

#### `isEditableCell(rowIndx, dataIndx)` ####

> 判断是否可编辑
> 返回

- **rowIndx**
	Type: Number
	行索引
	
- **dataIndx**
	Type: Number/String
	数据数组索引，或键名
	
#### `isEditableRow(rowIndx)` ####

> 判断行是否可编辑

- **rowIndx**
	Type: Number
	行索引
	
#### `isRowValid(rowIndx, allowInvalid)` ####

> 检查行是否对column.validations []有效
> 返回

- **rowIndx**
	Type: number/obj/arry
	行信息
	
- **allowInvalid**
	Type: Boolean
	为true时允许无效，返回无效集合。
	false，不允许无效，跳到第一个无效单元格，返回第一个单元格无效信息
	
#### `isRowArrayValid(data, allowInvalid)` ####

> 检查行集合是否对column.validations []有效
> 返回

- **data**
	Type: arry
	行信息集合
	
- **allowInvalid**
	Type: Boolean
	
#### `isCellValid(rowIndx, dataIndx, allowInvalid)` ####

> 检查单元格是否对column.validations []有效

- **rowIndx**
	Type: number
	行索引
	
- **dataIndx**
	Type: number、string
	列name或数据索引

- **allowInvalid**
	Type: Boolean
	
#### `quitEditMode()` ####

> 忽略编辑单元格未保存的更改，并退出编辑模式

#### `option(optionName, value)`配置选项 ####

> 返回表格的设置选项
> 返回。

- **optionName**
	Type: string/object
	不填：值返回当前grid设置对象，
	object：{optionName: value},增加设置
	
- **value**
	Type: 
	上个参数为string时，不填：返回对应设置的值。填：修改对应设置
	
#### `refresh()`刷新 ####

> 刷新，更改dataModel，或更新记录时很有用

#### `refreshCell(rowIndx, dataIndx, isInAll)`刷新单元格 ####

> 刷新单元格

- **rowIndx**
	Type: Number
	当前页面索引
	
- **dataIndx**
	Type: Number，String
	数据索引，或键名

- **isInAll**
	Type: Boolean
	
	
#### `refreshColumn(dataIndx)`刷新列 ####

> 刷新列

- **dataIndx**
	Type: Number/string
	数据索引，或键名

#### `refreshDataAndView()`刷新视图和数据 ####

> 刷新数据，也会重新加载后台数据。更改dataModal属性，或添加，删除更新记录后很有用。避免循环
	
#### `refreshHeader()`刷新列头 ####

> 刷新列标题

#### `refreshRow(rowIndx, isInAll)`刷新行 ####

> 刷新行

- **rowIndx**
	Type: Number
	当前页行索引
	
- **isInAll**
	Type: boolean
	
#### `refreshView()`刷新视图 ####

> 刷新视图。更改dataModel或添加，删除，更新记录后很有用
	
#### `removeRowClass(rowIndx, cls)` ####

> 移除行class

- **rowIndx**
	Type: number
	行索引
	
- **cls**
	Type: String
	删除的class，可以空格分割来删除多个class
	
#### `removeCellClass(rowIndx, dataIndx, cls)` ####

> 

- **rowIndx**
	Type: number
	行索引
	
- **dataIndx**
	Type: Number/String
	键名或数组索引
	
- **cls**
	Type: String
	删除的class，可以空格分割来删除多个class
	
	
#### `rollback(type)`回滚 ####

> 打开跟踪后，可以撤销添加，编辑，删除操作

- **type**
	Type: string
	撤销类型
	"add", "update", "dalete"
	
#### `rowCollapse(rowIndx, isInAll)` ####

> 折叠对应视图

- **rowIndx**
	Type: Number
	当前页行索引
	
- **isInAll**
	Type: Boolean
	填入并true时，所有数据行索引
	
#### `rowExpand(rowIndx, isInAll)` ####

> 展开对应视图

- **rowIndx**
	Type: Number
	当前页行索引
	
- **isInAll**
	Type: Boolean
	填入并true时，所有数据行索引
	
#### `rowInvalidate(rowIndx, isInAll)` ####

> 从视图和缓存中删除该行的详细视图 ，网络错误导致视图无法加载可能有用！

- **rowIndx**
	Type: Number
	当前页行索引
	
- **isInAll**
	Type: Boolean
	填入并true时，所有数据行索引
	
#### `saveEditCell()` ####

> 保存当前编辑单元格
> 返回操作结果

#### `scrollColumn(dataIndx)` ####

> 水平滚动视图

- **dataIndx**
	Type: Number/string
	列名name或数据数组索引
	
#### `scrollRow(rowIndxPage)` ####

> 垂直滚动视图

- **rowIndxPage**
	Type: Number
	当前页行索引
	
#### `selectRemove(rowIndx)` ####

> 从选择集中移除指定行数据

#### `selectRemoveAll()` ####

> 删除所有选择数据

#### `selectAdd(rowIndx)`添加选择 ####

> 从选择集中添加指定行数据

- **rowIndx**
	Type: number
	行索引

#### `selectGet(type)`获取选择行 ####

> 获取选择数据集  
> type 默认值为 row
> 有返回值

- type
  Type: string
  "row"、 "cell"。

#### `selectGetChecked()` ####

> 获取复选框勾选时的选择数据 
> 返回

#### `setSelection(rowIndx, isInAll)` ####

> 根据参数选择行、单元格

- **rowIndx**
	Type:Number
	当前页行索引

- **isInAll**
 Type: boolean
 true时，所有数据行索引。

#### `showLoading()` ####

> 显示loading。异步操作时很有用

#### `undo()`撤销 ####

> 撤销添加，更新，删除操作。可以被多次调用 ！
 
#### `updateRow(rowIndx, rowdata)`更新行 ####

> 更新行数据

- **rowIndx**
	Type:number
	行索引

- **rowdata**
 Type: object
 行数据
 
#### `widget()` ####

> 返回包含表格的jquery对象
> 返回

--------

#### `loadData(parms, url)` 重加载 ####

> 重新加载数据

- **parms**
	Type:obj
	ajax 参数

- url
 Type: string
 ajax url
 
#### `getUrlParms()` ####

> 获取远程请求时的字段

> 返回parms对象
  
#### `getAllData()` 获取所有数据 ####

> 获取所有页面数据
> 返回
  
#### `getDataInPage()` ####

> 获取当前页数据
> 返回

#### `deleteRows(arr)` 删除多行 ####

> 批量删除行数据
- **arr**
	Type:array
	行数据集合 eg: [{rowIndx:...,}]

#### `deleteSelectedRow()` 删除选中行 ####

> 删除选中行

#### `toggleCol(columnname, isShow)` ####

> 显示隐藏列

- **columnname**
	Type:string
	列名
	
- **isShow**
 Type: Boolean
 是否显示
  
#### `setDisabledTB(itemid)` ####

> 设置toobar 按钮置灰
- **itemid**
	Type:string
	button item中 item.id值

#### `getUpdated()` ####

> 获取修改数据

#### `getAdded()` ####

> 获取添加数据

#### `getDeleted()` ####

> 获取删除数据

#### `getTotalSummary(columns)`获取合计数据 ####

- type: 'string' / 'array'
- 列名或列名数组
- 返回
- 获取合计行的对应数据

#### `getSummaryDatas()` 获取摘要行数据 ####

- 所有的摘要行数据

#### `getPrintColumns()` 获取摘要行数据 ####

- 获取表格打印格式的表头列

#### `getColsByLevel(columnLevel)` 获取摘要行数据 ####
- type: 'number'
- 列层级 
- 根据列层次获取列集合