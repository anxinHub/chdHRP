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
<title></title>
<script type="text/javascript">
	var grid1;

	var gridManager1 = null;

	var grid2;

	var gridManager2 = null;

	var userUpdateStr;

	$(function() {
		loadDict();

		loadHead(null); //加载数据

		$("#panel1-1").ligerPanel({
			title : '支付方式'
		});
		$("#panel1-2").ligerPanel({
			title : '资金来源'
		});
		$(':button').ligerButton({
			width : 80
		});
	});
	//查询
	function query1() {
		grid1.options.parms = [];
		grid1.options.newPage = 1;
		//加载查询条件
		grid1.options.parms.push({
			name : 'pay_code',
			value : liger.get("pay_code").getValue()
		});
		grid1.loadData(grid1.where);
	}

	function query2() {
		grid2.options.parms = [];
		grid2.options.newPage = 1;

		if ($("#pay").val() == "" || $("#pay").val() == null) {
			$.ligerDialog.error('请选择支付方式');
			return;
		}

		grid2.options.parms.push({
			name : 'pay_code',
			value : $("#pay").val()
		});
		//加载查询条件
		grid2.loadData(grid2.where);
	}

	function querySource(pay_code, pay_name) {
		$("#pay").val(pay_code);
		$("#pay_code_info").val(pay_name);
		query2();
	}

	function loadHead() {
		grid1 = $("#maingrid1").ligerGrid({
			columns : [ {
				display : '支付方式编码',
				name : 'pay_code',
				align : 'left',
				width : 120
			}, {
				display : '支付方式名称',
				name : 'pay_name',
				align : 'left',
				width : 150
			} ],
			dataAction : 'server',
			dataType : 'server',
			usePager : true,
			url : 'queryAssPaySourceByPayType.do',
			width : '350px',
			height : '96%',
			checkbox : true,
			rownumbers : true,
			isSingleCheck : true,
			scroll : true,
			frozen : true,
			alternatingRow : false,
			scrollToPage : true,
			scrollToAppend : true,
			selectRowButtonOnly : true,
			onCheckRow : function(checked, data, rowid, rowdata) {
				querySource(data.pay_code, "" + data.pay_name + "")
			}
		});

		gridManager1 = $("#maingrid1").ligerGetGridManager();

		grid2 = $("#maingrid2").ligerGrid({
			columns : [ {
				display : '资金来源编码',
				name : 'source_code',
				align : 'left'
			}, {
				display : '资金来源名称',
				name : 'source_name',
				align : 'left'
			} ],
			dataAction : 'server',
			dataType : 'server',
			usePager : true,
			url : 'queryAssPaySourceBySource.do',
			width : '100%',
			height : '96%',
			checkbox : true,
			rownumbers : true,
			delayLoad : true,
			scroll : true,
			frozen : true,
			alternatingRow : false,
			scrollToPage : true,
			scrollToAppend : true,
			selectRowButtonOnly : true
		});

		gridManager2 = $("#maingrid2").ligerGetGridManager();
	}

	function del() {
		if ($("#pay").val() == "" || $("#pay").val() == null) {
			$.ligerDialog.error('请选择支付方式');
			return;
		}
		var ParamVo = [];
		var data = gridManager2.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			$(data).each(
					function() {
						ParamVo.push(this.group_id + "@" + this.hos_id + "@"
								+ this.copy_code + "@" + $("#pay").val() + "@"
								+ this.source_id);

					});
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteAssPaySourceMain.do", {
						ParamVo : ParamVo.toString()
					}, function(responseData) {
						if (responseData.state == "true") {
							query2();
						}
					});
				}
			});
		}
	}
	
	function settingSource(){
		if ($("#pay").val() == "" || $("#pay").val() == null) {
			$.ligerDialog.error('请选择支付方式');
			return;
		}
    	parent.$.ligerDialog.open({
			title: '设置资金来源',
			height: 600,
			width: 1000,
			url: 'hrp/ass/asspaysource/assPaySourceSettingPage.do?isCheck=false&pay_code='+$("#pay").val(),
			modal: true, showToggle: false, showMax: false, showMin: true, isResize: true, top : 1,parentframename: window.name
		}); 
	}

	function loadDict() {
		//字典下拉框
		$("#pay_code_info").ligerTextBox({
			width : 160,
			disabled : true,
			cancelable : false
		});
		autocomplete("#pay_code", "../queryAccPayType.do?isCheck=false", "id",
				"text", true, true);
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<input type="hidden" id="pay" name="pay" />
	<div style="width: 100%;">
		<div id="panel1-1" style="float: left;" class="l-panel"
			ligeruiid="panel1-1">
			<table cellpadding="0" cellspacing="0" class="l-table-edit" style="">
				<tr>
					<td align="right" class="l-table-edit-td"
						style="padding-left: 20px;">支付方式：</td>
					<td align="left" class="l-table-edit-td" style=""><input
						name="pay_code" type="text" id="pay_code" /></td>
					<td align="left"></td>
					<td align="right" class="l-table-edit-td" style=""></td>
					<td align="left" class="l-table-edit-td"
						style="padding-left: 20px;"><button onclick="query1();">
							<b>查询（<u>Q</u>）
							</b>
						</button></td>
					<td align="left"></td>
				</tr>
			</table>
			<div id="maingrid1"></div>
		</div>
		<div id="panel1-2"
			style="float: left; width: 600px; margin-left: 20px;" class="l-panel"
			ligeruiid="panel1-2">
			<table cellpadding="0" cellspacing="0" class="l-table-edit"
				style="margin-top: 0px;">
				<tr>
					<td align="right" class="l-table-edit-td"
						style="padding-left: 20px;">支付方式：</td>
					<td align="left" class="l-table-edit-td"><input
						disabled="disabled" name="pay_code_info" type="text"
						id="pay_code_info" /></td>
					<td align="left"></td>
					<td align="left" class="l-table-edit-td" style="padding-left:20px;"><button  onclick="settingSource();"><b>设置资金来源</b></button></td>
					<td align="left" class="l-table-edit-td"
						style="padding-left: 20px;"><button onclick="del();">
							<b>删除</b>
						</button></td>
					<td align="left"></td>
				</tr>
			</table>

			<div id="maingrid2"></div>
		</div>
	</div>

</body>
</html>
