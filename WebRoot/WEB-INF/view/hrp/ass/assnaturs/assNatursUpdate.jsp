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
<script type="text/javascript">
var grid;

var gridManager= null;
	$(function() {
		$("#naturs_code").ligerTextBox({
			disabled : true
		});
		$("#naturs_name").ligerTextBox({
			disabled : true
		});
		loadHead(null); //加载数据
		
	});
	function loadHead() {
		grid=$("#maingrid").ligerGrid({
			columns : [ {
				display : '仓库编码',
				name : 'store_id',
				align : 'left',
				width : 120
			}, {
				display : '仓库名称',
				name : 'text',
				align : 'left',
				width : 150
			} ],
			dataAction : 'server',
			dataType : 'server',
			usePager : true,
			url : 'queryHosStoreDict.do?isCheck=false&'+"naturs_code="+${naturs_code},
			width : '350px',
			height : '96%',
			checkbox : true,
			rownumbers : true,
			isChecked : initCheck,
			isSingleCheck : false,
			scroll : true,
			frozen : true,
			alternatingRow : false,
			scrollToPage : true,
			scrollToAppend : true,
			selectRowButtonOnly : true,
		
		/* 	 onCheckRow : function(checked, data, rowid, rowdata) {
				queryNature(data.pay_code, "" + data.pay_name + "")
			}  */
		});
		//grid.set('url','queryHosStoreDictByNaturs.do?isCheck=false&'+"naturs_code="+${naturs_code})
		gridManager = $("#maingrid").ligerGetGridManager();

		
	}
	function initCheck(e) {
		if (e.naturs_code!=null ) {
			return true;
		} else {
			return false;
		}
	}
	function save() {
		var data = gridManager.getCheckedRows();
	/* 	var formPara = {
			naturs_code : $("#id").val(),
			naturs_name : $("#naturs_name").val(),
		};  */
		ajaxJsonObjectByUrl("updateAssNatursStore.do?isCheck=false",{
			dataItem : JSON.stringify(data), naturs_code : $("#naturs_code").val(),
		}, function(
				responseData) {

			if (responseData.state == "true") {
			
			}
		})
	}

	function saveAssTypeDict() {
		if ($("form").valid()) {
			save();
		}
	}
</script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post" id="form1">
		<table cellpadding="0" cellspacing="0" class="l-table-edit">
		
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">性质编码<span style="color:red">*</span>：</td>
				<td align="left" class="l-table-edit-td"><input
					name="naturs_code" type="text" disabled="disabled" id="naturs_code" ltype="text"
					value="${naturs_code}" validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">性质名称<span style="color:red">*</span>：</td>
				<td align="left" class="l-table-edit-td"><input
					name="naturs_name" type="text"  disabled="disabled"id="naturs_name" ltype="text"
					value="${naturs_name}" validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<!-- <tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">维护仓库：</td>
				<td align="left" class="l-table-edit-td"><input
					name="store_id" type="text" id="store_id" ltype="text"
					 /></td>
				<td align="left"></td>
			</tr> -->
			
			
			
		</table>
			<div id="maingrid"><td >维护仓库</td></div>
		</form>
</body>
</html>
