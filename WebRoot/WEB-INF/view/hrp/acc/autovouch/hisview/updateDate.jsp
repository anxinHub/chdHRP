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
	var grid;
	var gridManager = null;
	var userUpdateStr;
	var his_log_code='${accHis.his_log_code}';
	$(function() {
		
		loadDict()//加载下拉框
		//加载数据
		loadHead(null);
		loadHotkeys();
		query();
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

	//键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
		
		hotkeys('C', this_close);
	}
	


	function save() {
		var formPara = {
				his_log_code : '${his_log_code}',
				begin_date : $("#begin_date").val(),
				end_date : $("#end_date").val()
		};
		$.ligerDialog.confirm('确定保存?', function(yes) {
			if (yes) {
				ajaxJsonObjectByUrl("updateDateAccAutoHisView.do?isCheck=false",
						formPara, function(responseData) {
							this_close();
						});
			}
		});
		
	}
	
	function saveAccAutoHisDate(){
		save();
	}
	
	function this_close() {
		frameElement.dialog.close();
	}

	function loadDict() {
		autodate("#begin_date", "yyyy-mm-dd", "month_first");
		autodate("#end_date", "yyyy-mm-dd", "month_last");
		$("#begin_date").ligerTextBox({width : 100});
		$("#end_date").ligerTextBox({width : 100});
		//格式化按钮
		$("#save").ligerButton({
			click : save,
			width : 90
		});
		$("#close").ligerButton({
			click : this_close,
			width : 90
		}); 
	}
</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div style="width: 100%; height: 100%;">
		<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
			<tr>
			<td align="right" class="l-table-edit-td" width="10%" style="margin-left: 22;">取数时间：</td>
			<td align="left" class="l-table-edit-td"  width="20%">
				<table>
					<tr>
						<td>
							<input class="Wdate" name="begin_date" id="begin_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
						<td align="right" class="l-table-edit-td"  >
							至：
						</td>
						<td align="left" class="l-table-edit-td">
							<input class="Wdate" name="end_date" id="end_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
            		</tr>
				</table>
	        </td>
	        </tr>
		</table>
		<div id="maingrid"></div>
		
	</div>
</body>
</html>
