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
	var sup_id = "${sup_id}";
	var sup_name = "${sup_name}";
	
	$(function() {
		
		loadDict();//加载下拉框
		loadHead();
		query();
	});
	//查询
	function query() {
		grid.options.parms=[];
		grid.options.newPage=1;
		
		grid.options.parms.push({name:'sup_id',value:sup_id}); 
		grid.options.parms.push({name:'is_stop',value:liger.get("is_stop").getValue()}); 

		//加载查询条件
		grid.loadData(grid.where);
	}
	
	function loadHead(){
		
		grid = $("#maingrid").ligerGrid({
    		columns: [
				{display: '证件ID', name: 'cert_id', align: 'left', width: '20%', hide:true},
    			{display: '证件编号', name: 'cert_code', align: 'left', width: '20%',
					render: function(rowdata, index, value){
						return "<a href=javascript:update_open('"+rowdata.cert_id+"')>"+value+"</a>"
					}
    			},
    			{display: '证件类型', name: 'cert_type_name', align: 'left', width: '20%'},
    			{display: '时间', name: 'cert_date', align: 'center', width: '20%' },
    			{display: '是否停用', name: 'is_stop', align: 'center', width: '10%',
    				render: function (rowdata, rowindex, value) {
						if(value == 1){
							return "是";
						}else{
							return "否";
						}
					}	
    			}, 
    			{display: '认证状态', name: 'authent_state', align: 'center', width: '10%', type: 'float',
					render: function (rowdata, rowindex, value) {
						if(value == 2){
							return "已认证";
						}else if(value == 1){
							return "未认证";
						}else if(value == 0){
							return "缺失";
						}else{
							return;
						}
					}
    			},
    			{display: '效期状态', name: 'end_date', align: 'left', width: '10%',
    				render: function (rowdata, rowindex, value) {
    					if(rowdata.is_long == 1){
    						return "未过期";
    					}else if(new Date()<= getDate(value)){
							return "未过期";
						}else{
							return "<font color='red'>已过期</font>";
						}
					}	
    			},
    			{display: '审核状态', name: 'check_state', align: 'left', width: '10%',
    				render: function (rowdata, rowindex, value) {
						if(value == 3){
							return "未通过";
						}else if(value == 2){
							return "已审核";
						}else if(value == 1){
							return "未审核";
						}else if(value == 0){
							return "未提交";
						}else{
							return;
						}
					}	
    			},
    		],
    		dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatSupCertInfo.do?isCheck=false',
    		width: '100%', height: '100%', checkbox: true, rownumbers:true,
    		enabledEdit: true, delayLoad : true,//初始化不加载，默认false
    		selectRowButtonOnly:true,//heightDiff: -10,
    		toolbar: { items: [
    			{ text: '查询', id:'search', click: query, icon:'search' },
    			{ line: true },
    			{ text: '添加', id:'search', click: f_onAdd, icon:'add' },
    			{ line: true },
    			{ text: '删除', id:'search', click: f_onDel, icon:'delete' },
    			{ line: true },
    			{ text: '认证', id:'search', click: f_authent, icon:'cashier' },
    			{ line: true },
    			{ text: '取消认证', id:'search', click: f_unAuthent, icon:'uncashier' },
    			{ line: true },
    			{ text: '审核', id:'search', click: f_check, icon:'blabel' },
    			{ line: true },
    			{ text: '消审', id:'search', click: f_unCheck, icon:'bcancle' }
    		]}
    	});
		gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	function loadDict() {
		
		$("#sup_name").ligerTextBox({ width : 160, disabled:true });		
		$("#sup_name").val(sup_name);
		
		$('#is_stop').ligerComboBox({
				data:[{id:1,text:'是'},{id:0,text:'否'}],
				valueField: 'id',
	            textField: 'text',
				cancelable:true,
				width:160
		});
	}
	
	function f_onAdd(){
		
		var paras = "sup_id=" + sup_id+ "&sup_name=" + sup_name;
		
		parent.$.ligerDialog.open({
			title : '添加供应商证件',
			height : $(window).height() - 50,
			width :  $(window).width() - 800,
			url : 'hrp/mat/cert/sup/matSupCertAddPage.do?isCheck=false&'
					+ paras.toString(),
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
			var ParamVo = [];
			var cert_codes = "";
			var cert_ids = "";
			$.each(data, function (index, element) {
				if(element.check_state == 2){
					cert_codes += this.cert_code + ",";
				}else{
					cert_ids += this.cert_id + ",";
				}
	        })
			if (cert_codes != "") {
				$.ligerDialog.warn("[" + cert_codes.substring(0, cert_codes.length-1) +"]单据已审核<br>");
				return;
			}
			$.ligerDialog.confirm('是否确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteMatSupCert.do", {
						cert_ids : cert_ids.substring(0, cert_ids.length-1)
					}, function(responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			});
		}
	}
	
	function f_authent(){
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.warn('请选择行');
		} else {
			var ParamVo = [];
			var cert_codes = "";
			var cert_ids = "";
			$.each(data, function (index, element) {
				if(element.authent_state == 2){
					cert_codes += this.cert_code + ",";
				}else{
					cert_ids += this.cert_id + ",";
				}
	        })
			if (cert_codes != "") {
				$.ligerDialog.warn("[" + cert_codes.substring(0, cert_codes.length-1) +"]证件已认证<br>");
				return;
			}
			
			ajaxJsonObjectByUrl("authentMatCertSup.do", {
				cert_ids : cert_ids.substring(0, cert_ids.length-1)
			}, function(responseData) {
				if (responseData.state == "true") {
					query();
				}
			});
		}
	}
	
	function f_unAuthent(){
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.warn('请选择行');
		} else {
			var ParamVo = [];
			var cert_codes = "";
			var cert_ids = "";
			$.each(data, function (index, element) {
				if(element.authent_state == 1){
					cert_codes += this.cert_code + ",";
				}else{
					cert_ids += this.cert_id + ",";
				}
	        })
			if (cert_codes != "") {
				$.ligerDialog.warn("[" + cert_codes.substring(0, cert_codes.length-1) +"]证件未认证<br>");
				return;
			}
			
			ajaxJsonObjectByUrl("unAuthentMatCertSup.do", {
				cert_ids : cert_ids.substring(0, cert_ids.length-1)
			}, function(responseData) {
				if (responseData.state == "true") {
					query();
				}
			});
		}
	}
	
	function f_check(){
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.warn('请选择行');
		} else {
			var ParamVo = [];
			var cert_codes = "";
			var cert_ids = "";
			$.each(data, function (index, element) {
				if(element.check_state == 2){
					cert_codes += this.cert_code + ",";
				}else{
					cert_ids += this.cert_id + ",";
				}
	        })
			if (cert_codes != "") {
				$.ligerDialog.warn("[" + cert_codes.substring(0, cert_codes.length-1) +"]证件已审核<br>");
				return;
			}
			
			ajaxJsonObjectByUrl("checkMatCertSup.do", {
				cert_ids : cert_ids.substring(0, cert_ids.length-1)
			}, function(responseData) {
				if (responseData.state == "true") {
					query();
				}
			});
		}
	}
	
	function f_unCheck(){
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.warn('请选择行');
		} else {
			var ParamVo = [];
			var cert_codes = "";
			var cert_ids = "";
			$.each(data, function (index, element) {
				if(element.check_state == 1){
					cert_codes += this.cert_code + ",";
				}else{
					cert_ids += this.cert_id + ",";
				}
	        })
			if (cert_codes != "") {
				$.ligerDialog.warn("[" + cert_codes.substring(0, cert_codes.length-1) +"]证件未审核<br>");
				return;
			}
			
			ajaxJsonObjectByUrl("unCheckMatCertSup.do", {
				cert_ids : cert_ids.substring(0, cert_ids.length-1)
			}, function(responseData) {
				if (responseData.state == "true") {
					query();
				}
			});
		}
	}
	
	function update_open(obj) {
		var paras = "cert_id=" + obj ;
		
		parent.$.ligerDialog.open({
			title : '修改供应商证件',
			height : $(window).height() - 50,
			width :  $(window).width() - 800,
			url : 'hrp/mat/cert/sup/matSupCertUpdatePage.do?isCheck=false&'
					+ paras.toString(),
			modal:true,showToggle:false,showMax:false,
			isResize:true, showMin: true,showMax: false, //开启最大化最小化按钮
			parentframename : window.name, //用于parent弹出层调用本页面的方法或变量
		});
	}
	
	function getDate(strDate){
		var date = eval('new Date(' + strDate.replace(/\d+(?=-[^-]+$)/, 
			function (a) { return parseInt(a, 10) - 1; }).match(/\d+/g) + ')');
		return date;
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	
	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" border="0" width="100%">
		<tr>
			<td align="right" class="l-table-edit-td" >供应商：</td>
            <td align="left" class="l-table-edit-td"><input name="sup_name" type="text" id="sup_name" ltype="text" validate="{required:true}" /></td>
           
			<td align="right" class="l-table-edit-td" >是否停用：</td>
            <td align="left" class="l-table-edit-td"><input name="is_stop" type="text" id="is_stop"  /></td>
		</tr>
		
	</table>
	<div id="maingrid"></div>
</body>
</html>
