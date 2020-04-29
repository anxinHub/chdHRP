# etCheck API #

[TOC]

```javascript
// 使用举例
// --- HTML --- //
<input type="checkbox" id="checkbox">
<label>测试checkbox</label>

<input type="radio" id="radio">
<label>测试radio</label>

// --- javascript --- //

var checkboxTest = $("#checkbox").etCheck({
	checked: true,
	ifToggled: function (checked) {
		console.log(checked)
	}
});
checkboxTest.setUncheck(function () {
	console.log("方法调用方式")
});

var radioTest = $("#radio").etCheck({
	disabled: true,
	ifChanged: function (status, checked, disabled) {
		console.log(status, checked, disabled)
	}
})
radioTest.setEnable();
```

---

Tips

> 函数构建后返回对象
> `$el`: checkbox的jQuery节点
> `status`: 当前状态checked、unchecked、disabled
> `checked`: 是否勾选
> `disabled`: 是否禁用
> 根据上述例子可以这样调用`checkboxTest.$el` `checkboxTest.checked` `checkboxTest.status` ...

---

### 基本配置 ###

#### `checked: false` ####

- Type: Boolean
- 初始check或不check。默认不check

#### `disabled: false` ####

- Type: Boolean
- 初始disabled或不disabled。默认不disabled

### 事件 ###

#### `ifCreated()` ####

- 被创建时，回调函数

#### `ifChecked()` ####

- 被check时，回调函数

#### `ifUnchecked()` ####

- 被uncheck时，回调函数

#### `ifDisabled()` ####

- 被disabled时，回调函数

#### `ifEnabled()` ####

- 被enabled时，回调函数

#### `ifClicked()` ####

- 被点击时，包括点击label内容。回调函数

#### `ifChanged(status, checked, disabled)` ####

- 被改变状态时，回调函数
- status, String。当前状态，"checked" / "unchecked" / "disabled"
- checked为Boolean，是否勾选
- disabled为Boolean，是否禁用

#### `ifToggled(checked)` ####

- 被checked,unchecked时的切换，回调函数
- checked为Boolean，是否勾选

### 方法 ###

> 以下方法都接受对应的回调函数作为参数。选填

#### `setCheck(fn)` ####

- 设置checked

#### `setUncheck(fn)` ####

- 设置unchecked

#### `setToggle(fn)` ####

- 设置checked或unchecked切换

#### `setDisable(fn)` ####

- 设置禁用

#### `setEnable(fn)` ####

- 设置启用

#### `setUpdate(fn)` ####

- 设置更新

