<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;"> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<%=path %>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
  <jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script src="<%=path %>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path %>/lib/jquery-validation/jquery.validate.min.js" type="text/javascript"></script>
<script src="<%=path %>/lib/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
<script src="<%=path %>/lib/jquery-validation/messages_cn.js" type="text/javascript"></script>
<script src="<%=path %>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>

<script type="text/javascript">

	function yanzheng(){
		if(pan1()&pan2()&pan3()&pan4()){
			return false;
		}
		return true;
	}

	function pan1(){

		if($("#rate_code").val() == null || $("#rate_code").val() == ""){
			$("#pan1").html("<font color='red'>必填!</font")
			return false;
		}
		return true;
	}

	function pan2(){
		if($("#rate_name").val() == null || $("#rate_name").val() == ""){
			$("#pan2").html("<font color='red'>必填!</font>")
			return false;
		}
		return true;
	}
	
	function pan3(){
		if($("#rate").val() == null || $("#rate").val() == ""){
			$("#pan3").html("<font color='red'>必填!</font>")
			return false;
		}
		return true;
	}
	
	function pan4(){
		if($("#is_stop").val() == null || $("#is_stop").val() == ""){
			$("#pan4").html("<font color='red'>必填!</font>")
			return false;
		}
		return true;
	}
	
</script>

</head>
<body>
	<form id="exchangeAddForm">
		<table cellpadding="0" cellspacing="0" class="l-table-edit" >

            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color='red'>*</font>汇率编码:</td>
                <td align="left" class="l-table-edit-td"><input class="l-table-edit-td" value="${RATE_CODE}" id="rate_code" ${code == 1 ? 'disabled':'' } name="rate_code" type="text"><span id="pan1"></span></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color='red'>*</font>汇率名称:</td>
                <td align="left" class="l-table-edit-td"><input class="l-table-edit-td" value="${RATE_NAME}" id="rate_name" type="text" name="rate_name"><span id="pan2"></span></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color='red'>*</font>汇率:</td>
                <td align="left" class="l-table-edit-td"><input class="l-table-edit-td" value="${RATE}" type="number" id="rate" name="rate"><span id="pan3"></span></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否停用:</td>
                <td align="left" class="l-table-edit-td">
                	<select id="is_stop" class="l-table-edit-td" name="is_stop" style="width: 158px;">
					<option value="2" ${IS_STOP == 2 ? 'selected':''}>否</option>
					<option value="1" ${IS_STOP == 1 ? 'selected':''}>是</option>
					</select><span id="pan4"></span></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">备注:</td>
                <td align="left" class="l-table-edit-td"><input class="l-table-edit-td" value="${NOTE == 'null' ? '':NOTE }" type="text" name="note" validate="{required:true}"></td>
            </tr>
        </table>
	</form>
</body>
</html>