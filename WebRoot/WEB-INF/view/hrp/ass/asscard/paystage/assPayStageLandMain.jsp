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
	var ass_card_no = '${ass_card_no}';
	
	$(function() {
		loadDict()//加载下拉框
		//加载数据
		loadHead(null);
		query();
	});

	function setAssCardNo(no) {
		ass_card_no = no;
		query();
	}
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'ass_nature',
			value : '${ass_nature}'
		});
		grid.options.parms.push({
			name : 'ass_card_no',
			value : ass_card_no
		});
		//加载查询条件
		grid.loadData(grid.where);
		is_addRow();
	}

	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '付款期号',
				name : 'stage_no',
				align : 'center',
				render : function(rowdata, rowindex,
						value) {
					if(rowdata.stage_no == null || rowdata.stage_no == ""){
						rowdata.stage_no = rowindex + 1;
						return rowindex + 1;
					}else{
						return rowdata.stage_no;
					}
				},width:110
				
			}, {
				display : '摘要',
				name : 'stage_name',
				align : 'left',
				editor : {
					type : 'text'
				},
				totalSummary : {
					render : function(suminf, column,
							cell) {
						return '<div>合计</div>';
					},
					align : 'center'
				},width:160
			}, {
				display : '付款条件',
				name : 'pay_term_id',
				textField : 'pay_term_name',
				align : 'left',
				editor : {
					type : 'select',
					valueField : 'id',
					textField : 'text',
					url:'../queryMatPayTerm.do?isCheck=false',
					keySupport : true,
					autocomplete : true
				},width:120
			}, {
				display : '预计付款时间',
				name : 'pay_plan_date',
				align : 'left',
				type : 'date',
				format : 'yyyy-MM-dd',
				editor : {
					type : 'date'
				},width:120
			}, {
				display : '付款比例',
				name : 'pay_plan_percent',
				align : 'left',
				editor : {
					type : 'float',
					precision : 2
				},width:120
			}, {
				display : '付款金额',
				name : 'pay_plan_money',
				align : 'right',
				editor : {
					type : 'float',
					precision : 2
				},
				render : function(item) {
					return formatNumber(
							item.pay_plan_money, '${ass_05006}', 1);
				},
				totalSummary : {
					render : function(suminf, column,
							cell) {
						return '<div>'
								+ formatNumber(
										suminf.sum, '${ass_05006}',
										1) + '</div>';
					}
				},width:120
			}, {
				display : '已付金额',
				name : 'pay_money',
				align : 'right',
				editor : {
					type : 'float',
					precision : 2
				},
				render : function(item) {
					return formatNumber(
							item.pay_money, '${ass_05006}', 1);
				},
				totalSummary : {
					render : function(suminf, column,
							cell) {
						return '<div>'
								+ formatNumber(
										suminf.sum, '${ass_05006}',
										1) + '</div>';
					}
				},width:120
			}, {
				display : '供应商',
				name : 'ven_id',
				textField : 'ven_name',
				align : 'left',width:200,
				render : function(item) {
					if(item.ven_id == null){
						item.ven_id = parent.liger.get("VEN_ID").getValue();
						item.ven_name = parent.liger.get("VEN_ID").getText();
						return item.ven_name;
					}else{
						return item.ven_name;
					}
				}
			}, {
				display : '未付金额',
				name : 'unpay_money',
				align : 'right',
				render : function(item) {
					return formatNumber(
							item.unpay_money, 2, 1);
				},
				totalSummary : {
					render : function(suminf, column,
							cell) {
						return '<div>'
								+ formatNumber(
										suminf.sum, 2,
										1) + '</div>';
					}
				},width:120
			}, {
				display : '备注',
				name : 'note',
				align : 'left',
				editor : {
					type : 'text'
				},width:180
			}],
			dataAction : 'server',
			dataType : 'server',
			usePager : true,
			url : 'queryAssPayStage.do',
			width : '100%',
			height : '90%',
			checkbox: true,
			rownumbers:true,
			delayLoad : true,
			enabledEdit : true,
            selectRowButtonOnly:true,//heightDiff: -10,,
            scroll: true,
            frozen:true,
            alternatingRow: false,
            onAfterEdit : f_onAfterEdit,
            onBeforeEdit : f_onBeforeEdit,
            scrollToPage: true,
            scrollToAppend: true,
			toolbar : {
				items : [ {
					text : '查询',
					id : 'search',
					click : query,
					icon : 'search'
				}, {
					line : true
				}, {
					text : '保存',
					id : 'save',
					click : save,
					icon : 'add'
				}, {
					line : true
				}, {
					text : '删除',
					id : 'delete',
					click : remove,
					icon : 'delete'
				} ]
			}
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	function f_onAfterEdit(e) {
		var pay_money = e.record.pay_money == null || e.record.pay_money == '' ? 0:e.record.pay_money;
		var pay_plan_money = e.record.pay_plan_money;
		var value = pay_plan_money - pay_money;
		grid.updateCell('unpay_money', value, e.record);
		grid.updateTotalSummary();
		return true;
	}
    var rowindex_id = "";
	var column_name = "";
	function f_onBeforeEdit(e) {
		rowindex_id = e.rowindex;
		clicked = 0;
		column_name = e.column.name;
	}
	
	function validateGrid() {
		var data = gridManager.getData();
		var msg = "";
		var targetMap = new HashMap();
		var msgMap = new HashMap();
		//删除空行
		var pay_plan_percent;
		$.each(data, function(i, v) {
			if (!isnull(v.pay_term_id)) {
				var key = v.stage_no;
				var value = "第" + (i + 1) + "行";

				if(v.pay_money > v.pay_plan_money){
					msg = "[已付金额不能大于未付金额]";
				}
				pay_plan_percent = pay_plan_percent + v.pay_plan_percent;
				
				
				if (msg != "") {
					msgMap.put(value + msg + "不能为空或不能为零！\n\r", "");
				}
				if (isnull(targetMap.get(key))) {
					targetMap.put(key, value);
				} else {
					msg = targetMap.get(key) + "与" + value + "重复！\n\r",
					value;
					msgMap.put(targetMap.get(key) + "与" + value + "重复！\n\r",
							value);
				}
			}
		});

		if (msg != "") {
			parent.$.ligerDialog.warn(msgMap.keySet());
			return false;
		}
		if (data.length == 0) {
			parent.$.ligerDialog.warn("无数据保存");
			return false;
		}
		if(pay_plan_percent > 1){
			parent.$.ligerDialog.warn("付款比例总数错误");
			return false;
		}
		
		return true;
	}

	function save() {
		gridManager.endEdit();
		var data = grid.getData();
    	if(ass_card_no == '0'){
    		parent.$.ligerDialog.warn("请先保存卡片");
    		return;
    	}
    	
		var formPara = {
				ass_nature:'${ass_nature}',
			ass_card_no : ass_card_no,

			ParamVo : JSON.stringify(data)

		};
		if (validateGrid()) {
			ajaxJsonObjectByUrl("saveAssPayStage.do", formPara, function(
					responseData) {
				if (responseData.state == "true") {
					query();
				}
			}, false);
		}
	}

	function remove() {

		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			parent.$.ligerDialog.error('请选择行');
		} else {
			var ParamVo = [];
			$(data).each(
					function() {
						ParamVo.push(this.group_id + "@" + this.hos_id + "@"
								+ this.copy_code + "@" + this.ass_card_no + "@" + this.stage_no)
					});
			parent.$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteAssPayStage.do", {
						ParamVo : ParamVo.toString(),
						ass_nature:'${ass_nature}'
					}, function(responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			});
		}
	}
	function loadDict() {
		//字典下拉框

	}
	//键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);

		hotkeys('A', add);
		hotkeys('D', remove);

	}
	 function is_addRow() {
			setTimeout(function() { //当数据为空时 默认新增一行
				grid.addRow();
			}, 1000);
		}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>

	<div id="maingrid"></div>

</body>
</html>
