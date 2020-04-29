<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<jsp:include page="${path}/inc.jsp" />
<script type="text/javascript">
	var grid;
	var gridManager = null;
	
	$(function() {
		
		loadHead(null); //加载数据
		loadDict();
	});
	//科目Grid
	function loadHead() {
		 
		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '会计年度', name : 'acc_year', align : 'left', width : 90, 
				render : function(rowdata, rowindex,value) { 
					return '<a href=javascript:updateOpen('+ rowdata.target_id +')>'+rowdata.acc_year+'</a>';
				}
			}, {
				display : '直接支付',  
				columns:[ {
					display : '预算指标数', name : 'dircet_target', align : 'right', width : 120, 
					render : function(rowdata, rowindex,value) { 
						return formatNumber(value == null ? 0 : value, 2, 1);
					}
				}, {
					display : '实际发生额', name : 'dircet_money', align : 'right', width : 120, 
					render : function(rowdata, rowindex,value) { 
						return formatNumber(value == null ? 0 : value, 2, 1);
					}
				} ]
			}, {
				display : '授权支付',  
				columns:[ {
					display : '预算指标数', name : 'grant_target', align : 'right', width : 120, 
					render : function(rowdata, rowindex,value) { 
						return formatNumber(value == null ? 0 : value, 2, 1);
					}
				}, {
					display : '实际发生额', name : 'grant_money', align : 'right', width : 120, 
					render : function(rowdata, rowindex,value) { 
						return formatNumber(value == null ? 0 : value, 2, 1);
					}
				} ]
			}, {
				display : '状态', name : 'state', align : 'left', width : 90
			} ],
			dataAction : 'server', dataType : 'server', width : '100%', height : '100%', 
			usePager : false, url : 'queryAccBudgTarget.do?isCheck=true', isSingleCheck : true,
			checkbox : true, rownumbers : false, delayLoad : true, selectRowButtonOnly : false,//heightDiff: -10,
			toolbar : {
				items : [ {
					text : '查询', id : 'query', click : query, icon : 'search'
				}, {
					line : true
				}, {
					text : '添加', id : 'add', click : addOpen, icon : 'add'
				}, {
					line : true
				}, {
					text : '删除', id : 'delete', click : remove, icon : 'delete'
				}, {
					line : true
				}, {
					text : '获取度年实际发生额', id : 'getMoney', click : getMoney, icon : 'down'
				}, {
					line : true
				}, {
					text : '审核', id : 'audit', click : audit, icon : 'audit'
				}, {
					line : true
				}, {
					text : '消审', id : 'unAudit', click : unAudit, icon : 'unaudit' 
				} ]
			},  
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	function loadDict() {
		autocomplete("#acc_year", "../../../queryAccYear.do?isCheck=false", "id", "text", true, true);
	}

	function query() {
		 
		var subj_code_value = "";
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'acc_year',
			value : liger.get("acc_year").getValue()
		});
		
		//加载查询条件
		grid.loadData(grid.where);
	}
	
	function addOpen(){
		$.ligerDialog.open({ 
			title: '添加', 
			width: 400, 
			height: 240, 
			url: 'accBudgTargetAddPage.do?isCheck=false', 
			modal: true, showToggle: false, showMax: false, showMin: false, isResize: true,
		}); 
	}
	
	function updateOpen(target_id){
		$.ligerDialog.open({ 
			title: '修改', 
			width: 400, 
			height: 300, 
			url: 'accBudgTargetUpdatePage.do?isCheck=false&target_id='+target_id, 
			modal: true, showToggle: false, showMax: false, showMin: false, isResize: true,
		}); 
	}
	
	function getMoney(){
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var target_id = "";
			$(data).each(function() {
				target_id += this.target_id;
			});
			$.ligerDialog.confirm('确定获取度年实际发生额?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("updateAccBudgTargetForMoney.do?isCheck=true", {
						target_id : target_id
					}, function(responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			});
		}
	}
	
	function remove(){
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var target_id = "";
			$(data).each(function() {
				target_id += this.target_id;
			});
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteAccBudgTarget.do?isCheck=true", {
						target_id : target_id
					}, function(responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			});
		}
	}
	
	function audit(){
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var target_id = "";
			$(data).each(function() {
				target_id += this.target_id;
			});
			$.ligerDialog.confirm('确定审核?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("auditAccBudgTarget.do?isCheck=true", {
						target_id : target_id, state : 1
					}, function(responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			});
		}
	}
	
	function unAudit(){
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var target_id = "";
			$(data).each(function() {
				target_id += this.target_id;
			});
			$.ligerDialog.confirm('确定消审?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("auditAccBudgTarget.do?isCheck=true", {
						target_id : target_id, state : 0
					}, function(responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			});
		}
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div class="l-clear"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td">年度：</td>
			<td align="left" class="l-table-edit-td">
				<input name="acc_year" type="text" id="acc_year" ltype="text" validate="{required:false}" />
			</td>
		</tr>
	</table>
	<div id="maingrid"></div>
</body>
</html>
