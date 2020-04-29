<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<jsp:include page="${path}/inc.jsp" />
<script type="text/javascript">
	var grid;

	var gridManager = null;

	var userUpdateStr;
	
	var childData;

	$(function() {
		loadDict();

		loadHead(null); //加载数据

		$("#topmenu").ligerMenuBar({
			items : [ {
				text : '批量设置',
				id : 'set',
				click : itemclick
			},{
				text : '批量取消',
				id : 'unset',
				click : itemclick
			},{
				text : '打印',
				menu : menu_print
			}, {
				text : '导出',
				id : 'export',
				click : itemclick
			} ]
		});

	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'vouch_date',
			value : $("#vouch_date").val()s
		});
		//加载查询条件
		grid.loadData(grid.where);
	}
	//获取查询条件的数值
	function f_getWhere() {
		if (!grid)
			return null;
		var clause = function(rowdata, rowindex) {
			if ($("#vouch_date").val() != "") {
				return rowdata.vouch_date.indexOf($("#vouch_date").val()) > -1;
			}
		};
		return clause;
	}

	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '对方科目编码',
				name : 'subj_code',
				align : 'left'
			}, {
				display : '对方科目名称',
				name : 'subj_name',
				align : 'left'
			}, {
				display : '现金项目',
				name : 'cash_item_name',
				align : 'left',
				render:function(rowdata,index,value){
					if(childData != null){
						return this.value = childData;
					}
					return rowdata.cash_item_name
				}
			}, {
				display : '操作',
				align : 'left',
				render:function(rowdata,index,value){
					return '<input  type="button" id="btn_set" ltype="text" value="设置"  style="width: 160px;" onclick="set_subj('+rowdata.subj_code+')" />';
				}
			} ],
			dataAction : 'server',
			dataType : 'server',
			usePager : true,
			url : 'queryCashFlowSubj.do',
			width : '100%',
			height : '100%',
			checkbox : true,
			rownumbers : true,
			selectRowButtonOnly : true,//heightDiff: -10,
			toolbar : {
				items : [ {
					text : '查询',
					id : 'search',
					click : query,
					icon : 'search'
				}, {
					line : true
				} ]
			}
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'subj_type_code',
			value : $("#subj_type_code").val()
		});
		grid.options.parms.push({
			name : 'subj_type_name',
			value : $("#subj_type_name").val()
		});

		//加载查询条件
		grid.loadData(grid.where);
	}
	function set_subj(obj){
		$.ligerDialog.open({url: 'queryAccVouchCashFlowSetSubj.do?isCheck=false&subj_code_other='+obj, height: 420,width: 650,title:'设置',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveTemplate();query(); dialog.close();},cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
		return;
	}
	function itemclick(item) {
		if (item.id) {
			switch (item.id) {
			case "set":
				var data = gridManager.getCheckedRows();
				if (data.length == 0) {
					$.ligerDialog.error('请选择行');
				} else {
					var ParamVo = [];
					$(data).each(function() {
						ParamVo.push(
						//表的主键
						this.subj_code)
					});
					$.ligerDialog.open({url: 'queryBatchAccVouchCashFlowSetSubj.do?isCheck=false&ParamVo='+ParamVo,height: 420,width: 650,title:'设置',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveTemplate();query(); dialog.close();},cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
				}
				return;
			case "unset":
				var data = gridManager.getCheckedRows();
				if (data.length == 0) {
					$.ligerDialog.error('请选择行');
				} else {
					var ParamVo = [];
					$(data).each(function() {
						ParamVo.push(
						//表的主键
								this.subj_code)
					});
					$.ligerDialog.confirm('确定取消?', function(yes) {
						if (yes) {
							ajaxJsonObjectByUrl("AccVouchBatchUnset.do", {
								ParamVo : ParamVo
							}, function(responseData) {
								if (responseData.state == "true") {
									query();
								}
							});
						}
					});
				}
				return;
			case "Excel":
			case "Word":
			case "PDF":
			case "TXT":
			case "XML":
				$.ligerDialog.waitting('导出中，请稍候...');
				setTimeout(function() {
					$.ligerDialog.closeWaitting();
					if (item.id == "Excel")
						$.ligerDialog.success('导出成功');
					else
						$.ligerDialog.error('导出失败');
				}, 1000);
				return;
			}
		}

	}
	function loadDict() {
		//字典下拉框
		autocomplete("#vouch_type_code", "../queryVouchType.do?isCheck=false",
				"id", "text", true, true);
		autocomplete("#state", "../queryAccVouchState.do?isCheck=false", "id",
				"text", true, true);
	}

	/**
	 * 打印 打印 预览 设置
	 */
	var menu_print = {
		width : 120,
		items : [ {
			text : '打印',
			id : 'print',
			click : itemclick
		}, {
			text : '预览',
			id : 'view',
			click : itemclick
		}, {
			text : '设置',
			id : 'set',
			click : itemclick
		} ]
	};
	
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="topmenu"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">现金科目：</td>
			<td align="left" class="l-table-edit-td"><input 
				name="subj_code" type="text" id="subj_code" ltype="text"
				style="width: 160px;" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">金额方向：</td>
			<td align="left" class="l-table-edit-td"><select name="money_dire" id="money_dire" >
				<option value="0">借</option>
				<option value="1">贷</option>
				</select></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">对方科目：</td>
			<td align="left" class="l-table-edit-td"><input 
				name="subj_id" type="text" id="subj_id" ltype="text"
				style="width: 160px;" /></td>
			<td align="left"></td>
		</tr>
	</table>

	<div id="maingrid"></div>

</body>
</html>
