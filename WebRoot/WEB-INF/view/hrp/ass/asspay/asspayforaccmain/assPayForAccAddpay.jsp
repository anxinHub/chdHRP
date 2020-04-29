<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%
	String path = request.getContextPath();
%> 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
	<style>
		div.work-space {
			width: 100%;
			height: 100%;
			padding-top: 50px;
		}

		div.work-space table {
			font-size: 12px;
		}

		div.work-space table tr {
			height: 30px;
		}

		div.work-space table .label {
			width: 20%;
			text-align: right;
			padding-right: 5px;
		}

		div.work-space table .ipt {
			text-align: left;
			padding-left: 5px;
			font-size: 12px;
		}

		.date_input_txt {
			border: 1px solid #aecaf0;
			width: 180px;
			height: 26px;
			outline: none;
			box-sizing: border-box;
			padding-left: 5px;
			-webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.1);
			box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.1);
			border-radius: 1px;
			cursor: pointer;
		}
	</style>
	<script>
	
	$(function(){
		loadDict();
	});
	
	function loadDict() {
		autocomplete("#pay_code", "../../queryAccPayType.do?isCheck=false", "id",
			"text", true, true, null, false, null, "150");
	}
	
	function f_select()
    {
        return liger.get("pay_code").getValue();
    }


	</script>
</head>

<body>
	
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div class="work-space">
		<table cellpadding="0" border="0" cellspacing="0" align="center">
			<tr>
				<td class="label">支付方式：</td>
				<td class="ipt">
					<input id="pay_code" style="width:210px"/>
				</td>
			</tr>
		</table>
	</div>

	
</body>

</html>