<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="${path}/resource.jsp">
	<jsp:param value="validate,select,datepicker,grid,ligerUI,dialog" name="plugins" />
</jsp:include>
<script>

var isValiData;

//必填项 控制
var initValidate = function (){
	isValiData = $.etValidate({
		items: [
	        { el: $("#table_code"), required: true },
	        { el: $("#table_name"), required: true },
	        { el: $("#prefixe"), required: true },
	        { el: $("#is_year"), required: true },
	        { el: $("#is_month"), required: true },
	        { el: $("#is_day"), required: true },
	        { el: $("#seq_no"), required: true }
        ]
	});
}

// 是/否   类型下拉数据
var loadDict = function (){
	$('#is_year').etSelect({
		options:[
			{ id: '1', text: '是'}, { id: '0', text: '否'}
		]
	});
	$('#is_month').etSelect({
		options:[
			{ id: '1', text: '是'}, { id: '0', text: '否'}
		]	
	});
	$('#is_day').etSelect({
		options:[
			{ id: '1', text: '是'}, { id: '0', text: '否'}
		]
	});
}

// 实现新增
var addHrNoRule = function (){
	// 首先判断，必填项是否 有为空
	if(!isValiData.test()){
		return;
	}
	var paramVo=[];
	/* paramVo.push({ name: 'table_code', value: $("#table_code").val() });
	paramVo.push({ name: 'table_name', value: $("#table_name").val() });
	paramVo.push({ name: 'prefixe', value: $("#prefixe").val() });
	paramVo.push({ name: 'is_year', value: $("#is_year").val() });
	paramVo.push({ name: 'is_month', value: $("#is_month").val() });
	paramVo.push({ name: 'is_day', value: $("#is_day").val() });
	paramVo.push({ name: 'seq_no', value: $("#seq_no").val() }); */
	
	paramVo.push( "table_code=" + $("#table_code").val() + "," +
			      "table_name=" + $("#table_name").val() + "," +
			      "prefixe=" + $("#prefixe").val() + "," +
			      "is_year=" + $("#is_year").val() + "," +
			      "is_month=" + $("#is_month").val() + "," +
			      "is_day=" + $("#is_day").val() + "," +
			      "seq_no=" + $("#seq_no").val()
	);
	ajaxPostData({
		url: 'addHrNoRule.do?isCheck=false',
		data: { paramVo: paramVo.toString() },
		success: function (responsedata){
			if(responsedata.state == "true"){
				$.etDialog.success('新增成功!');
				// 获取父界面 window 对象
				var curIndex = parent.$.etDialog.getFrameIndex(window.name);
                parent.$.etDialog.close(curIndex);
                parent.query(); // 执行父页面的 query() 查询
			}
		}
	}); 
}

$(function(){
	loadDict();
	initValidate();
});

</script>
</head>
<body style="padding: 10px; overflow: hidden;">
	<div class="addrule" style="padding-left:5px;">
		<table class="table-layout" >
			<tr>
				<td class="l-table-edit-td" align="left" >
					<span style="color:red">*</span>单据规则编码：
				</td>
				<td class="l-table-edit-td">
					<input type="text" id="table_code" name="table_code" >
				</td>	
				
				<td class="l-table-edit-td" align="left">
					<span style="color:red">*</span>单据名称：</td>
				<td class="l-table-edit-td">
					<input type="text" id="table_name" name="table_name" >
				</td>		
			</tr>
			
			<tr>
				<td class="l-table-edit-td" align="left">
					<span style="color:red">*</span>前缀：
					</td>
				<td class="l-table-edit-td">
					<input type="text" id="prefixe" >
				</td>	
				
				<td class="l-table-edit-td" align="left">
					<span style="color:red">*</span>是否包含年：
				</td>
				<td class="l-table-edit-td">
					<select id="is_year" name="is_year" style="width: 180px"></select>
					<!-- <select id="is_stop" name="is_stop">
                		<option value="0">否</option>
                		<option value="1">是</option>
                	</select> -->
				</td>		
			</tr>
			
			<tr>
				<td class="l-table-edit-td" align="left">
					<span style="color:red">*</span>是否包含月：
				</td>
				<td class="l-table-edit-td">
					<select id="is_month" name="is_month" style="width: 180px"></select>
				</td>	
				
				<td class="l-table-edit-td" align="left">
					<span style="color:red">*</span>是否包含日：
				</td>
				<td class="l-table-edit-td">
					<select id="is_day" name="is_day" style="width: 180px"></select>
				</td>		
			</tr>
			<tr>
				<td class="l-table-edit-td" align="left">
					<span style="color:red">*</span>单据号位数：
				</td>
				<td class="l-table-edit-td">
					<input id="seq_no" name="seq_no" type="text">
				</td>	
			</tr>
		</table>
	</div>
</body>
</html>