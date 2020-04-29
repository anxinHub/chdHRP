<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>  
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script src="<%=path%>/lib/hrp/mat/mat.js"	type="text/javascript"></script>
<script type="text/javascript">
	var grid; 
	var gridManager = null;
	
	$(function() {
		
		loadDict();//加载下拉框
		loadHead();
		query();
	});
	//查询
	function query() {
		grid.options.parms=[];
		grid.options.newPage=1;
		
		grid.options.parms.push({name:'warn_type_code',value:$("#warn_type_code").val()}); 
		grid.options.parms.push({name:'is_stop',value:liger.get("is_stop").getValue()}); 

		//加载查询条件
		grid.loadData(grid.where);
	}
	
	function loadHead(){
		
		grid = $("#maingrid").ligerGrid({
    		columns: [
    			{display: '预警类型编号', name: 'warn_type_code', align: 'left', width: '15%',
					render: function(rowdata, index, value){
						return "<a href=javascript:update_open('"+rowdata.warn_type_code+"')>"+value+"</a>"
					}
    			},
    			{display: '预警类型名称', name: 'warn_type_name', align: 'left', width: '15%'},
    			{display: '提醒方式', name: 'warn_way', align: 'center', width: '10%',
    				render: function (rowdata, rowindex, value) {
						if(value == 1){
							return "过期";
						}else if(value == 2){
							return "提前";
						}else if(value == 3){
							return "到期";
						}else if(value == 4){
							return "未到期";
						}else if(value == 5){
							return "缺失";
						}else{
							return ;
						}
					}		
    			},
    			{display: '天数', name: 'days', align: 'center', width: '10%' },
    			{display: '提醒间隔', name: 'space', align: 'center', width: '10%',
    				render: function (rowdata, rowindex, value) {
						if(value == 1){
							return "每天";
						}else if(value == 2){
							return "每周";
						}else if(value == 3){
							return "每月";
						}else{
							return ;
						}
					}		
    			},
    			{display: '是否待办提醒', name: 'is_warn', align: 'center', width: '10%',
    				render: function (rowdata, rowindex, value) {
						if(value == 1){
							return "是";
						}else{
							return "否";
						}
					}	
    			}, 
    			{display: '是否停用', name: 'is_stop', align: 'center', width: '10%',
					render: function (rowdata, rowindex, value) {
						if(value == 1){
							return "是";
						}else{
							return "否";
						}
					}
    			},
    			{display: '系统内置', name: 'is_sys', align: 'center', width: '10%',
    				render: function (rowdata, rowindex, value) {
    					if(value == 1){
							return "是";
						}else{
							return "否";
						}
					}	
    			},
    			{display: '提醒图标', name: 'icon_url', align: 'center', width: '10%',
    				render: function (rowdata, rowindex, value) {
    					if(value == null){
    						return ;
    					}
    					return "<img src = '../../../../dhc/images/warnType/"+value+"'>";
					}		
    			}
    		],
    		dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatWarnType.do?isCheck=false',
    		width: '100%', height: '100%', checkbox: true, rownumbers:true,
    		enabledEdit: true, delayLoad : true,//初始化不加载，默认false
    		selectRowButtonOnly:true,//heightDiff: -10,
    		checkBoxDisplay : f_isCheckDisplay,
    		toolbar: { items: [
    			{ text: '查询', id:'search', click: query, icon:'search' },
    			{ line: true },
    			{ text: '添加', id:'add', click: f_onAdd, icon:'add' },
    			{ line: true },
    			{ text: '删除', id:'delete', click: f_onDel, icon:'delete' },
    			{ line: true },
    			{ text: '启用', id:'start', click: f_onStart, icon:'flowstart' },
    			{ line: true },
    			{ text: '停用', id:'stop', click: f_onStop, icon:'flowstop' },
    		]}
    	});
		gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	function f_isCheckDisplay(rowdata) {
		if (rowdata.is_sys == 1)
			return false;
		return true;
	}
	
	function loadDict() {
		
		$("#warn_type_code").ligerTextBox({ width : 160});	
		
		$('#is_stop').ligerComboBox({
				data:[{id:1,text:'是'},{id:0,text:'否'}],
				valueField: 'id',
	            textField: 'text',
				cancelable:true,
				width:160
		});
	}
	
	function f_onAdd(){
		
		parent.$.ligerDialog.open({
			title : '添加预警类型',
			height : $(window).height() - 150,
			width :  $(window).width() - 400,
			url : 'hrp/mat/cert/warn/matWarnTypeAddPage.do?isCheck=false',
			modal:true,
			showToggle:false,
			isResize:true, 
			showMin: true,
			showMax: false, //开启最大化最小化按钮
			parentframename : window.name, //用于parent弹出层调用本页面的方法或变量
		});
	}
	
	function f_onDel(){
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.warn('请选择行');
		} else {
			var warn_type_codes = "";
			$.each(data, function (index, element) {
				warn_type_codes += this.warn_type_code + ",";
	        });
			
			$.ligerDialog.confirm('是否确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteMatWarnType.do", {
						warn_type_codes : warn_type_codes.substring(0, warn_type_codes.length-1)
					}, function(responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			});
		}
	}
	
	function f_onStart(){
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.warn('请选择行');
		} else {
			var err_codes = "";
			var warn_type_codes = "";
			$.each(data, function (index, element) {
				if(element.is_stop == 0){
					err_codes += this.warn_type_code + ",";
				}else{
					warn_type_codes += this.warn_type_code + ",";
				}
	        })
			if (err_codes != "") {
				$.ligerDialog.warn("[" + err_codes.substring(0, err_codes.length-1) +"]已启用<br>");
				return;
			}
			
			ajaxJsonObjectByUrl("updateMatWarnTypeToStart.do", {
				warn_type_codes : warn_type_codes.substring(0, warn_type_codes.length-1)
			}, function(responseData) {
				if (responseData.state == "true") {
					query();
				}
			});
		}
	}
	
	function f_onStop(){
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.warn('请选择行');
		} else {
			var err_codes = "";
			var warn_type_codes = "";
			$.each(data, function (index, element) {
				if(element.is_stop == 1){
					err_codes += this.warn_type_code + ",";
				}else{
					warn_type_codes += this.warn_type_code + ",";
				}
	        })
			if (err_codes != "") {
				$.ligerDialog.warn("[" + err_codes.substring(0, err_codes.length-1) +"]已停用<br>");
				return;
			}
			
			ajaxJsonObjectByUrl("updateMatWarnTypeToStop.do", {
				warn_type_codes : warn_type_codes.substring(0, warn_type_codes.length-1)
			}, function(responseData) {
				if (responseData.state == "true") {
					query();
				}
			});
		}
	}
	
	function update_open(obj) {
		var paras = "warn_type_code=" + obj ;
		
		parent.$.ligerDialog.open({
			title : '修改预警类型',
			height : $(window).height() - 150,
			width :  $(window).width() - 400,
			url : 'hrp/mat/cert/warn/matWarnTypeUpdatePage.do?isCheck=false&'
					+ paras.toString(),
			modal:true,showToggle:false,showMax:false,
			isResize:true, showMin: true,showMax: false, //开启最大化最小化按钮
			parentframename : window.name, //用于parent弹出层调用本页面的方法或变量
		});
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	
	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" border="0" width="100%">
		<tr>
			<td align="right" class="l-table-edit-td" >预警类型：</td>
            <td align="left" class="l-table-edit-td"><input name="warn_type_code" type="text" id="warn_type_code" ltype="text" validate="{required:true}" /></td>
           
			<td align="right" class="l-table-edit-td" >是否停用：</td>
            <td align="left" class="l-table-edit-td"><input name="is_stop" type="text" id="is_stop"  /></td>
		</tr>
		
	</table>
	<div id="maingrid"></div>
</body>
</html>
