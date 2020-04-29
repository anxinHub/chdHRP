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
	var userUpdateStr;
	
	var store_id = '';
	var store_no  = '';
	var store_name  = '';
	var flag = 0;
	$(function() {
		
		loadDict();//加载下拉框
		//加载数据
		loadHead(null);
		
		$("#store_code").bind("change",function(){
			if(liger.get("store_code").getValue()==''){
				flag = 1;
				$.ligerDialog.error("请选择仓库！");
				return;
			}else{
				flag = 0;
			}
			
		});
		
		
	
	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		
		grid.options.parms.push({name : 'store_id',value : liger.get("store_code").getValue().split(",")[0]});
		grid.options.parms.push({name : 'store_no',value : liger.get("store_code").getValue().split(",")[1]});
		
		//加载查询条件
		grid.loadData(grid.where);
		
	}
	
	function loadHead() {
		
	}
	
	function saveMatDepartRequriedPlanStore(){
		if(flag == 1){
			parent.isFlag = 1;
		}else{
			parent.isFlag = 0;
			parent.store_id = liger.get("store_code").getValue().split(",")[0];
			parent.store_no = liger.get("store_code").getValue().split(",")[1];
			parent.store_name = liger.get("store_code").getText();
		}

	}
	
	function loadDict() {
		autocomplete("#store_code", "../../../queryMatStoreByRead.do?isCheck=false", "id", "text", true, true,{is_purchase : '1'},true,'','260');//仓库
		$("#store_code").ligerTextBox({width : 260});
	}

</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td></td>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<td></td>
			<td></td>
			<td></td>
		</tr>
		
		<tr>
			
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><font color="red">*</font>响应库房：</td>
			<td align="left" class="l-table-edit-td">
				<input name="store_code" type="text" requried="true" id="store_code" ltype="text"  />
			</td>
			<td align="left"></td>
		</tr>
		
		
		
	</table>

	<div id="maingrid"></div>
	
</body>
</html>
