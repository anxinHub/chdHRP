<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script src="<%=path%>/lib/stringbuffer.js"></script>

<script type="text/javascript">
	var grid;
	var gridManager = null;
	var userUpdateStr;
	
	$(function() {
		loadDict();//加载下拉框
		//加载数据
		loadHead(null);
		
	//alert('${detailList}')
	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		//加载查询条件
		grid.loadData(grid.where);
		
	}
	
	function loadHead() {
		
	}
	
	function saveMatDeptCollectSaveAndSub(){
		//alert($("#make_date").val());
		parent.$("#make_date").val($("#make_date").val());
		parent.save();
		this_close();
	}
	function loadDict() {
		//返回当前年,当前月,当前日期,当前月第一天,当前月最后一天,上个月,上月第一天，上月最后一天
		var date = getCurrentDate();
        var aa = date.split(';');
     
		$("#make_date").val(aa[2]);
		$("#make_date").ligerTextBox({width:160});
	}
	
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" border="0">
		<tr></tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">编制日期：</td>
			<td align="left" class="l-table-edit-td">
				<input class="Wdate" name="make_date" type="text"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"
				id="make_date" />
			</td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" colspan="3" style="padding-left: 10px;">
				<font color="red">需求汇总后，科室需求将被执行，确定汇总提交吗？</font>
			</td>
			
		</tr>
	</table>

	<div id="maingrid"></div>
	
</body>
</html>
