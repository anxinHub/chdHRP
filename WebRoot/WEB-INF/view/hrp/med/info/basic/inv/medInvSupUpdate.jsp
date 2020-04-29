<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:auto;">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
	<script src="<%=path%>/lib/hrp/med/med.js"	type="text/javascript"></script>
    <script type="text/javascript">
    var dataFormat;
    $(function (){
    	$("#inv_code").attr("disabled", true);
    	$("#inv_name").attr("disabled", true);
        loadHead(null);//加载供应商数据
		querySup();
    });
	
	function querySup(){
		grid.options.parms=[];
		grid.options.newPage=1;
        //根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'group_id',
			value : '${group_id}'
		});
		grid.options.parms.push({
			name : 'hos_id',
			value : '${hos_id}'
		});
		grid.options.parms.push({
			name : 'copy_code',
			value : '${copy_code}'
		});
		grid.options.parms.push({
			name : 'inv_id',
			value : '${inv_id}'
		});
       
		
		
		
    	//加载查询条件
		setTimeout("grid.loadData(grid.where); ",500);
	}
    
	function loadHead(){
		grid = $("#maingrid").ligerGrid({
			columns: [ { 
				display: '供应商编码', name: 'sup_code', align: 'left'
			}, { 
				display: '供应商名称',
				name: 'sup_id',
				align: 'left',
				textField : 'sup_name',
				editor :{
					type : 'select',
					valueField : 'sup_id',
					textField : 'sup_name',
					selectBoxWidth : 300,
					selectBoxHeight : 200,
					grid : {
						columns : [ {
							display : '供应商编码', name : 'sup_code', align : 'left'
						}, {
							display : '供应商名称', name : 'sup_name', align : 'left'
						/*}, {
							display : '供应商证件信息', name : 'sup_cert', align : 'left'
						}, {
							display : '联系人', name : 'contact', align : 'left'
						}, {
							display : '电话', name : 'phone', align : 'left'*/
						} ],
						switchPageSizeApplyComboBox : false,
						onSelectRow : f_onSelectRow_detail,
						url : 'queryMedInvSupList.do?isCheck=false',
						//delayLoad:true,
						usePager: true,
						pageSize : 5
					},
					keySupport : true,
					autocomplete : true,
					onSuccess : function() {
						this.parent("tr").next(".l-grid-row").find("td:first").focus();
					}
				}
			/*}, { 
				display: '供应商证件信息', name: 'sup_cert', align: 'left'
			}, { 
				display: '联系人', name: 'contact', align: 'left'
			}, { 
				display: '电话', name: 'phone', align: 'left',type: 'int'*/
			},
			{
				display : '是否默认', name : 'is_default', align : 'center',
				render : function(rowdata, rowindex,value) {
					
					if(value == 1){
						return "<input name='is_default"+rowindex+"' checked='checked' type='checkbox' id='is_default"+rowindex+"' ltype='text'"
							+" style='margin-top:5px;' />";
					}
					return "<input name='is_default"+rowindex+"' type='checkbox' id='is_default"+rowindex+"' ltype='text'"
						+" style='margin-top:5px;' />";
				}
			}],
			dataAction: 'server',dataType: 'server',usePager:false,
			url: "queryMedInvSup.do?isCheck=false",
			width: '98%', height: '98%', checkbox: true,rownumbers:true,
			enabledEdit : true,alternatingRow : true,onBeforeEdit : f_onBeforeEdit,
			onBeforeSubmitEdit : f_onBeforeSubmitEdit,onAfterEdit : f_onAfterEdit,
			//isScroll : false,
			selectRowButtonOnly:true,//heightDiff: -10,
			toolbar: { items: [
				{ text: '删除', id:'delete', click: deleteRow,icon:'delete' },{ line:true },
				{ text: '添加行', id:'add', click: addCenterRow, icon:'add' },{ line:true }
				//,{ text: '保存', id:'save', click: saveSup, icon:'save' },{ line:true }
			]}
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	          
	}		
	      
	var rowindex_id = "";
	var column_name="";
	function f_onBeforeEdit(e) {
		rowindex_id = e.rowindex;
		clicked = 0;
		column_name=e.column.name;
	}

	//选中回充数据
	function f_onSelectRow_detail(data, rowindex, rowobj) {
		selectData = "";
		selectData = data;
		if (column_name == "sup_id") {
			if (selectData != "" || selectData != null) {
				//回充数据 
				grid.updateRow(rowindex_id, {
					sup_code : data.sup_code,
	 				sup_name : data.sup_name/*, 
	 				sup_cert : data.sup_cert, 
	 				contact : data.contact, 
	 				phone : data.phone */
	 			});
			}
	 	}
	 	return true;
	 }
	 		
	function f_onSelectRow(data, rowindex, rowobj) {
	 	return true;
	}
	 		
	// 编辑单元格提交编辑状态之前作判断限制
	function f_onBeforeSubmitEdit(e) {
	 	return true;
	 }
	 
	// 跳转到下一个单元格之前事件
	function f_onAfterEdit(e) {
	 			
		return true;
	}
	 		
	//保存数据
	function saveSup() {
		
		grid.endEdit();
		var param = '';
		var count = 0 ;
		var rows = 0;
		var msg="";
		var targetMap = new HashMap();
		var d = gridManager.getData();
		
		//if(JSON.stringify(d) != '[{}]'){
		if(d.length != 0){	
			$.each(d,function(rowindex,item){
				
 				if(typeof(item.sup_id) != "undefined" && item.sup_id != null && item.sup_id != ''){
 					var sup_id = item.sup_id;
					var is_default = $("#is_default"+rowindex).is(":checked")? 1 : 0;
					
					if(is_default == 1){
						count++;
					}
					
					param+=sup_id+","+is_default+"@";
					var key = item.sup_id;
					var value="第"+(rowindex+1)+"行";
					
		 			if(targetMap.get(key)== null || targetMap.get(key) == 'undefined' || targetMap.get(key) == ""){
		 				targetMap.put(key ,value);
		 			}else{
		 				msg += value + targetMap.get(key) +'供应商重复.<br> '
		 			}
		 			
		 			rows +=1;
 				}
			});
		}
		
		if(rows != 0){
			if(count != 1){
				$.ligerDialog.warn('是否默认必选且只能选中一个');
				return ; 
			}
		}
		var formPara = {
	        group_id : $("#group_id").val(),
	        hos_id : $("#hos_id").val(),
	        copy_code : $("#copy_code").val(),
			inv_id : $("#inv_id").val(),
			supData : param
	 	};
	 	ajaxJsonObjectByUrl("addMedInvSup.do?isCheck=false", formPara,function(responseData) {
			if (responseData.state == "true") {
				querySup();
	 		}
		});
	}
	
	//自动添加行
	function is_addRow() {
		setTimeout(function() { //当数据为空时 默认新增一行
			grid.addRow();
		}, 100);
	}
	//手动添加行
	function addCenterRow() {
		grid.addRow();
	}
	
	//删除选中行
	function deleteRow(){
		gridManager.deleteSelectedRow();
	}
	
	//键盘事件
	function loadHotkeys() {
		hotkeys('Esc', this_close);
	 }
	
	//关闭当前页面
	function this_close(){
		frameElement.dialog.close();
	} 
	</script>
	</head>
  
	<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post"  id="form1" >
		<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" style="padding: 10px">
	        <tr>
	        	<td align="left" class="l-table-edit-td" >
	        		<input type="hidden" name="group_id" id="group_id" value="${group_id}">
	        		<input type="hidden" name="hos_id" id="hos_id" value="${hos_id}">
	        		<input type="hidden" name="copy_code" id="copy_code" value="${copy_code}">
	        		<input type="hidden" name="inv_id" id="inv_id" value="${inv_id}">
	        	</td>
	            <td align="right" class="l-table-edit-td" >
	            	药品编码：
	            </td>
	            <td align="left" class="l-table-edit-td" >
	            	<input name="inv_code" type="text" id="inv_code" ltype="text" value="${inv_code}" validate="{required:true}" />
	            </td>
	            <td align="right" class="l-table-edit-td" >
	            	药品名称：
	            </td>
	            <td align="left" class="l-table-edit-td" >
	            	<input name="inv_name" type="text" id="inv_name" ltype="text" value="${inv_name}" validate="{required:true}" />
	            </td>
	        </tr>
	        <tr>
	        	<td colspan="5" class="l-table-edit-td" >
	        		<div id="maingrid"></div>
	        	</td>
	        </tr>
	    </table>
    </form>
    </body>
</html>
