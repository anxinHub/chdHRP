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
<script type="text/javascript">
var dialog = frameElement.dialog; //调用页面的dialog对象(ligerui对象)
var state_name = dialog.get("data").state_name;
var year_month;

	$(function() {
		$("#msg").text("2、上级节点："+state_name+"。");
    	loadDict();
	});
	
	
	function loadDict(){
		
		year_month = $("#year_month").etDatepicker({
			range: false,
			view: "months",
			minView: "months",
			dateFormat: "yyyy-mm",
			defaultDate: ['${acc_year}-${acc_month}']
		});
	}

	function save(flag) {
		
    	if($("#year_month").val() == null || $("#year_month").val() == ""){
			$.ligerDialog.error('请选择会计期间!');
			return;
		}
		
		var para={
			flag: flag,
			year_month: $("#year_month").val()
        };
        
		var loadIndex = layer.load(1);
		ajaxJsonObjectBylayer("auditAll.do",para,function (responseData){
			if(responseData.state){
				parent.query();
			}
		},layer,loadIndex);
		
	}


</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div style="width: 100%; height: 100%;">
			
		<table align="center" cellpadding="0" cellspacing="0" class="l-table-edit" style="font-size:15px">
		 	<tr>
	        <td align="right" class="l-table-edit-td" style="padding-left: 10px;">会计期间：</td>
			<td align="left" >
				<input class="Wdate" name="year_month" type="text" id="year_month" ltype="text" style="width: 98px;" />
			</td>
	        </tr>
		</table>
		<br/>
		<div style="padding-left: 90px;font-size:15px;color:red">说明：<br/>1、只能审核或取消他人的凭证。<br/>
		<span id="msg" ></span>
		</div>
	</div>
</body>
</html>
