<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>  
<meta http-equiv="Content-Type" content="text/html;">
<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<jsp:include page="${path}/resource.jsp">
	<jsp:param value="hr,dialog,select,datepicker,upload,validate,grid,time"
		name="plugins" />
</jsp:include>
<script src="<%=path%>/lib/ligerUI/js/core/base.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/plugins/ligerLayout.js" type="text/javascript"></script>
<script>
	var accGrid, budgGrid, vouch_type_code, formValidate;
	var dire_source = [{"label": "借方", "id": 0}, {"label": "贷方", "id": 1}];
	var parentFrameName = parent.$.etDialog.parentFrameName; // 拿取window名
	var parentWindow = parent.window[parentFrameName]; // 当前页拿取window对象
	var is_update = parentWindow.is_update;
	var accBudgTp = parentWindow.accBudgTp;
	
	//验证初始化
	function initValidate() {
		formValidate = $.etValidate({
			items : [ {
				el : $("#tp_code"),
				required : true
			}, {
				el : $("#tp_name"),
				required : true
			}, {
				el : $("#sort_code"),
				required : true
			}, ]
		});
	}
	
	//字段初始化
	function initDict() {
		vouch_type_code = $("#vouch_type_code").etSelect({
			url : "../../queryVouchType.do?isCheck=false",
			defaultValue : "none",
		});
	}
	
	//字符自增 
	function incChar(char0){
		if(!char0){
			return "A";
		}
		
		char0 = char0.toUpperCase(); //转大写
		var char1 = 0, char2 = 0, newChar = "";
		
		char1 = char0.substr(0, 2).charCodeAt();
		
		if(char0.length > 1){
			char2 = char0.substr(1, 2).charCodeAt();
			if(char2 == 90){
				char2 = 65;
				char1 += 1;
			}else{
				char2 += 1;
			}
			newChar = String.fromCharCode(char1) + String.fromCharCode(char2);
		}else{
			if(char1 == 90){
				newChar = "AA";
			}else{
				newChar = String.fromCharCode(char1 + 1);
			}
		}
		
		return newChar;
	}
	
	//财务会计查询
	function queryAcc(){
		params = [ {
			name: 'tp_code', value: accBudgTp.tp_code 
		}, {
			name: 'kind_code', value: '01' 
		} ]
		
		accGrid.loadData(params, "queryDetailByCode.do?isCheck=false");
	}
	//财务会计Grid
	function initAccGrid() {
		/* 列头 */
		var columns = [ {
			display: '财务会计科目', name: 'subj_name', width: 240, isSort: false, 
			editor: {
				type: 'select',
			    autoFocus : true,   //  为true时 下拉框默认选择第一个
			    disabled : false,
				keyField : 'subj_code',
				url : 'querySubjSelect.do?isCheck=false&kind_code=01', 
			}
		}, {
			display: '摘要', name: 'summary', width: 160, isSort: false, 
			editor: {
				type: 'text'
			}
		}, {
			display: '方向', name: 'dire_name', width: 60, isSort: false, 
			editor: {
				type: 'select',
			    autoFocus : true,   //  为true时 下拉框默认选择第一个
			    disabled : false,
				keyField : 'dire',
			    source: dire_source,
			}
		}, { 
			display: '编号', name: 'sort_code', width: 60, isSort: false, editable: false, 
		}, { 
			display: '科目类型', name: 'kind_code', width: 60, hidden: true, editable: false, 
		} ]; 
		/* 头部按钮 */
	    var toolbar = {
			items: [ { 
				type: "button", label: '新增行', icon: 'add', listeners: [{ click: addAccRow }] 
			}, { 
				type: "button", label: '删除行', icon: 'delete', listeners: [{ click: removeAccRow }] 
			} ]
		};
		
		/* 表格基础参数 */
		var paramObj = {	
			width: 'auto',
			height: '90%',
			usePager: false, 
			editable: true,
			inWindowHeight: true, 
			toolbar: toolbar, 
			checkbox: true, 
			columns: columns, 
			showButton: false 
		};
		
		accGrid = $("#accGrid").etGrid(paramObj);
	}
	
	var accSortCode = "", acc_first = true;
	function addAccRow(){
		if(acc_first && accGrid.getAllData() && accGrid.getAllData().length > 0){
			accSortCode = accGrid.getAllData()[accGrid.getAllData().length - 1].sort_code;
			acc_first = false;
		}
		accSortCode = incChar(accSortCode);
		accGrid.addRow({kind_code: '01', sort_code: accSortCode, dire: 0, dire_name: '借方'});
	}
	function removeAccRow(){
		accGrid.deleteSelectedRows();
		acc_first = true;
	}
	
	//预算会计查询
	function queryBudg(){
		params = [ {
			name: 'tp_code', value: accBudgTp.tp_code 
		}, {
			name: 'kind_code', value: '02' 
		} ]
		
		budgGrid.loadData(params, "queryDetailByCode.do?isCheck=false");
	}
	//预算会计Grid
	function initBudgGrid() {
		/* 列头 */
		var columns = [ {
			display: '预算会计科目', name: 'subj_name', width: 240, isSort: false, 
			editor: {
				type: 'select',
			    autoFocus : true,   //  为true时 下拉框默认选择第一个
			    disabled : false,
				keyField : 'subj_code',
				url : 'querySubjSelect.do?isCheck=false&kind_code=02', 
			}
		}, {
			display: '摘要', name: 'summary', width: 160, isSort: false, 
			editor: {
				type: 'text'
			}
		}, {
			display: '方向', name: 'dire_name', width: 60, isSort: false, 
			editor: {
				type: 'select',
			    autoFocus : true,   //  为true时 下拉框默认选择第一个
			    disabled : false,
				keyField : 'dire',
			    source: dire_source,
			}
		}, {
			display: '取值公式', name: 'cal', width: 120, isSort: false, 
			editor: {
				type: 'text'
			}
		}, {
			display: '编号', name: 'sort_code', width: 60, hidden: true, isSort: false, editable: false, 
		}, {
			display: '科目类型', name: 'kind_code', width: 60, hidden: true, editable: false, 
		} ]; 
		/* 头部按钮 */
	    var toolbar = {
			items: [ { 
				type: "button", label: '新增行', icon: 'add', listeners: [{ click: addBudgRow }] 
			}, { 
				type: "button", label: '删除行', icon: 'delete', listeners: [{ click: removeBudgRow }] 
			} ]
		};
		
		/* 表格基础参数 */
		var paramObj = {	
			width: 'auto',
			height: '90%', 
			usePager: false, 
			editable: true,
			inWindowHeight: true,
			toolbar: toolbar,
			checkbox: true,
			columns: columns, 
		};
		
		budgGrid = $("#budgGrid").etGrid(paramObj);
	}
	
	var budgSortCode = "", budg_first = true;
	function addBudgRow(){
		if(budg_first && budgGrid.getAllData() && budgGrid.getAllData().length > 0){
			budgSortCode = budgGrid.getAllData()[budgGrid.getAllData().length - 1].sort_code;
			budg_first = false;
		}
		budgSortCode = incChar(budgSortCode);
		budgGrid.addRow({kind_code: '02', sort_code: budgSortCode, dire: 0, dire_name: '借方'});
	}
	function removeBudgRow(){
		budgGrid.deleteSelectedRows();
		budg_first = true;
	}
	
	var accSortCodes = {};
	var errorMsg = "";
	//校验公式是否合法
	function checkCal(row, cal) {
		var flag = true;
	    try{
			var ret=cal.replace(/[a-zA-Z]+/g, function () {
				//调用方法时内部会产生 this 和 arguments
				var v_char = arguments[0].toUpperCase();
				if(!accSortCodes[v_char]){
					errorMsg += "预算会计第" + row + "行取值公式中编号" + v_char + "不存在<br/>";
					flag = false;
				}
				return 1;
			});
	    	eval("cal="+ret);
	    	
	    	return flag;
	    }catch(err){
	    	errorMsg += "预算会计第" + row + "行取值公式不合法<br/>";
	    	return false;
	    }
	}
	
	//保存
	function save() {
		if (!formValidate.test()) {
			return false;
		}
		
		//组装明细
		var allData = [];
		var accData = accGrid.getAllData();
		var budgData = budgGrid.getAllData();
		
		if(!accData || !budgData || accData.length == 0 || budgData.length == 0){
            $.etDialog.warn("请添加分录信息");
            return false;
		}
		
		var msg = "";
		$.each(accData,function(index, v){
			if(!v.subj_code || !v.sort_code){
				msg += "财务会计第"+(index+1)+"行科目、排序号不能为空<br/>";
			}else{
				allData.push(v);
				accSortCodes[v.sort_code] = 1;
			}
		});
		$.each(budgData,function(index, v){
			if(!v.subj_code || !v.sort_code || !v.cal){
				msg += "预算会计第"+(index+1)+"行科目、排序号、公式不能为空<br/>";
			}else{
				//转换大写
				v.cal = v.cal.toUpperCase();
				//校验公式
				errorMsg = "";
				if(!checkCal(index+1, v.cal)){
					msg += errorMsg;
				}
				allData.push(v);
			}
		});
		
		if(msg){
			$.etDialog.warn(msg);
            return false;
		}
		
		ajaxPostData({
			url : 'saveAccBudgTp.do',
			data : {
				tp_code: $("#tp_code").val(), 
				tp_name: $("#tp_name").val(), 
				vouch_type_code : vouch_type_code.getValue(),
				sort_code: $("#sort_code").val(),
				note : $("#note").val(), 
				is_update: is_update, 
				allData: JSON.stringify(allData)
			},
		    success: function (responseData) {
	                            
				parentWindow.query(); 
				is_update = 1;
				$("#tp_code").attr("disabled", "disabled");
				if(responseData.sort_code){
					$("#sort_code").removeAttr("disabled");
					$("#sort_code").attr("type", "number");
					$("#sort_code").val(responseData.sort_code);
				}
			},
		})
	}
	
	function this_close(){
		var curIndex = parent.$.etDialog.getFrameIndex(window.name);
		parent.$.etDialog.close(curIndex);
	}
	
	function loadData(){
		$("#tp_code").attr("disabled", "disabled");
		$("#tp_code").val(accBudgTp.tp_code);
		$("#tp_name").val(accBudgTp.tp_name);
		vouch_type_code.setValue(accBudgTp.vouch_type_code);
		$("#sort_code").removeAttr("disabled");
		$("#sort_code").attr("type", "number");
		$("#sort_code").val(accBudgTp.sort_code);
		$("#note").val(accBudgTp.note);
		
		queryAcc();
		queryBudg();
	}
	
	function initLayout(){
		var leftWidth = $(window).width()/2 - 10;
		$("#layout").ligerLayout({ 
			topHeight: 100, 
			leftWidth: leftWidth, 
			InWindow: true, 
			height: '100%', 
			//centerWidth: '5', 
			rightWidth: leftWidth, 
			bottomHeight: 50, 
		});
	}
	
	$(function() {
		initLayout();
		initDict();
		initValidate();
		initAccGrid();
		initBudgGrid();
		
		if(is_update == 1){
			loadData();
		}

		$("#save").click(function() {
			save();
		})
		
		$("#close").click(function() {
			this_close();
		})
	})
</script>
</head>

<body style="overflow-x: hidden">
	<div id="layout" style="z-index: 2">
		<div position="top" class="flex-wrap">
			<table class="table-layout">
				<tr>
					<td class="label"><font size="2" color="red">*</font>模板编码：</td>
					<td class="ipt">
						<input id="tp_code" type="text" />
					</td>
					
					<td class="label"><font size="2" color="red">*</font>模板名称：</td>
					<td class="ipt">
						<input id="tp_name" type="text" />
					</td>
					
					<td class="label"><font size="2" color="red">*</font>排序号：</td>
					<td class="ipt">
						<input id="sort_code" type="text" value="自动生成" disabled="disabled"/>
					</td>
					<td></td>
				</tr> 
				<tr>
					<td class="label">备注：</td>
					<td class="ipt" colspan="5">
						<textarea id="note" cols="20" rows="30" style="width: 520px; height: 60px"></textarea>
					</td>
					
					<td class="label" style="display: none">凭证类型：</td>
					<td class="ipt" style="display: none">
						<select id="vouch_type_code" style="width: 180px;"></select>
					</td>
					<td></td>
				</tr>
			</table>
		</div>
		<div position="left" title="财务会计" >
			<div id="accGrid"></div>
		</div>
		<!-- <div position="center"></div> -->
		<div position="right" title="预算会计（计算公式为财务会计编号的加减乘除运算，如：A+B）" > 
			<div id="budgGrid"></div>
		</div>
		<div position="bottom" class="button-group" >
			<button id="save">保存</button>
			<button id="close">关闭</button>
		</div>
	</div>
</body>

</html>