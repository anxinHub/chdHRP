<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
	var grid;
	var gridManager = null;
	var userUpdateStr;
	$(function() {
		loadDict()//加载下拉框
		//加载数据
		loadHead(null);
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
		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '期间类型',
				name : 'period_type_name',
				align : 'left',
				frozen : true,
				minWidth : 60
			}, {
				display : '核算年度',
				name : 'acc_year',
				align : 'left',
				frozen : true,
				minWidth : 60
			}, {
				display : '核算期间',
				name : 'period_name',
				align : 'left',
				frozen : true,
				minWidth : 60
			}, {
				display : '方案名称',
				name : 'scheme_name',
				align : 'left',
				frozen : true,
				minWidth : 120
			}, {
				display : '病种编码',
				name : 'drgs_code',
				align : 'left',
				frozen : true,
				minWidth : 60
			}, {
				display : '病种名称',
				name : 'drgs_name',
				align : 'left',
				frozen : true,
				minWidth : 60
			}, {
				display : '准入规则',
				name : 'recipe_group_name',
				align : 'left',
				minWidth : 80
			}, {
				display : '项目病例数',
				name : 'item_mr_count',
				align : 'left',
				minWidth : 80
			}, {
				display : '病种病例数',
				name : 'drgs_mr_count',
				align : 'left',
				minWidth : 80
			}, {
				display : '医嘱时效',
				name : 'recipe_type_name',
				align : 'left',
				minWidth : 80
			}, {
				display : '诊疗项目编码',
				name : 'charge_item_code',
				align : 'left',
				minWidth : 120
			}, {
				display : '诊疗项目名称',
				name : 'charge_item_name',
				align : 'left',
				minWidth : 150
			},{
				display : '数量',
				name : 'amount',
				align : 'left',
				minWidth : 80,
				render : function(rowdata, rowindex, value) {
					return formatNumber(rowdata.amount, 2, 1);
				}
			}, {
				display : '单价',
				name : 'price',
				align : 'left',
				minWidth : 80,
				render : function(rowdata, rowindex, value) {
					return formatNumber(rowdata.price, 2, 1);
				}
			}, {
				display : '金额',
				name : 'income_money',
				align : 'left',
				minWidth : 80,
				render : function(rowdata, rowindex, value) {
					return formatNumber(rowdata.income_money, 2, 1);
				}
			}, {
				display : '病人CLP时程',
				name : 'clp_step_name',
				align : 'left',
				minWidth : 80
			}, {
				display : '直接百分比',
				name : 'prim_percent',
				align : 'left',
				minWidth : 80,
				render : function(rowdata, rowindex, value) {
					return formatNumber(rowdata.prim_percent * 100, 2, 1) + '%';
				}
			}, {
				display : '累计百分比',
				name : 'grand_percent',
				align : 'left',
				minWidth : 80,
				render : function(rowdata, rowindex, value) {
					return formatNumber(rowdata.grand_percent * 100, 2, 1) + '%';
				}
			}, {
				display : '准入标识',
				name : 'recipe_p_group',
				align : 'left',
				minWidth : 80,
				render : function(rowdata, rowindex, value) {
					if (rowdata.recipe_p_group == 0) {
						return '未准入'
					} else if (rowdata.recipe_p_group == 1) {
						return '已准入'
					}
				}
			} ],
			dataAction : 'server',
			dataType : 'server',
			usePager : true,
			url : 'queryHtcgMedicalAdvicePgroup.do',
			width : '100%',
			height : '100%',
			checkbox : true,
			rownumbers : true,
			delayLoad : true,
			selectRowButtonOnly : true,//heightDiff: -10,
			toolbar : {
				items : [ {
					text : '查询',
					id : 'search',
					click : query,
					icon : 'search'
				}, {
					line : true
				}, {
					text : '生成',
					id : 'init',
					click : init,
					icon : 'add'
				}, {
					line : true
				}, {
					text : '计算',
					id : 'calculate',
					click : calculate,
					icon : 'add'
				}, {
					line : true
				}, {
					text : '准入',
					id : 'admittance',
					click : admittance,
					icon : 'add'
				}, {
					line : true
				}, {
					text : '删除',
					id : 'delete',
					click : remove,
					icon : 'delete'
				}, {
					line : true
				}]
			}
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	function init() {
		var scheme_code = liger.get("scheme_code").getValue();
		var acc_year = $("#acc_year").val();
		if (scheme_code == '') {
			$.ligerDialog.error('请选择方案');
			return;
		}
		if (acc_year == '') {
			$.ligerDialog.error('请选择核算年度');
			return;
		}
		var formPara = {
			scheme_code : scheme_code,
			acc_year : acc_year
		};
		ajaxJsonObjectByUrl("initHtcgMedicalAdvicePgroup.do?isCheck=false",
				formPara, function(WorkponseData) {
					if (WorkponseData.state == "true") {
						query();
					}
				});
	}

	function calculate() {
		var scheme_code = liger.get("scheme_code").getValue();
		var acc_year = $("#acc_year").val();

		if (scheme_code == '') {
			$.ligerDialog.error('请选择方案');
			return;
		}
		if (acc_year == '') {
			$.ligerDialog.error('请选择核算年度');
			return;
		}
		var formPara = {
			scheme_code : scheme_code,
			acc_year : acc_year
		};
		ajaxJsonObjectByUrl("calculateHtcgMedicalAdvicePgroup.do?isCheck=false",
				formPara, function(WorkponseData) {
					if (WorkponseData.state == "true") {
						query();
					}
				});
	}

	/* 准入 */
	function admittance() {
		
		var scheme_code = liger.get("scheme_code").getValue();
		var acc_year = $("#acc_year").val();

		if (scheme_code == '') {
			$.ligerDialog.error('请选择方案');
			return;
		}
		if (acc_year == '') {
			$.ligerDialog.error('请选择核算年度');
			return;
		}
		var formPara = {
			scheme_code : scheme_code,
			acc_year : acc_year
		};
		ajaxJsonObjectByUrl("admittanceHtcgMedicalAdvicePgroup.do?isCheck=false",
				formPara, function(WorkponseData) {
					if (WorkponseData.state == "true") {
						query();
					}
				});

	}

	function remove(){
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var ParamVo = [];
			$(data).each(
					function() {
						ParamVo.push(
								  this.group_id + "@"
								+ this.hos_id + "@"
								+ this.copy_code + "@"
								+ this.period_type_code + "@"
								+ this.acc_year + "@"
								+ this.acc_month + "@"
								+ this.scheme_code + "@"
								+ this.drgs_code + "@"
								+ this.charge_item_code + "@"
								+ this.price + "@"
								+ this.recipe_type_code + "@"
								+ this.clp_p_step);//实际代码中temp替换主键
					});
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteHtcgMedicalAdvicePgroup.do",
							{
								ParamVo : ParamVo.toString()
							}, function(responseData) {
								if (responseData.state == "true") {
									query();
								}
							});
				}
			});
		}
	}
	
	function chargePeriodType() {
		var formPara = {
				period_type_code : liger.get("period_type_code").getValue()
			};
		autocomplete("#period_code","../../base/queryHtcgPeriodDict.do?isCheck=false","id","text",true,true,formPara);
	}
	function loadDict() {
		//字典下拉框
		autocomplete("#scheme_code","../../base/queryHtcgSchemeDict.do?isCheck=false", "id", "text", true,true);
		autocomplete("#period_type_code","../../base/queryHtcgPeriodTypeDict.do?isCheck=false","id","text",true,true);
		$("#acc_year").ligerTextBox({
			width : 160
		});
		$("#advice_date").ligerTextBox({
			width : 160
		});
		$("#scheme_name").ligerTextBox({
			width : 160
		});
		$("#drgs_name").ligerTextBox({
			width : 160
		});
		$("#charge_item_name").ligerTextBox({
			width : 160
		});
	}
</script>
</head>
<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
          <tr>
	        <td align="right" class="l-table-edit-td"  style="padding-left:20px;">方案名称：</td>
            <td align="left" class="l-table-edit-td"><input name="scheme_code" style="width:160px;" type="text" id="scheme_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">核算年度：</td>
            <td align="left" class="l-table-edit-td"><input  class="Wdate" style="width:160px;" name="acc_year" type="text" id="acc_year" ltype="text" validate="{required:true,maxlength:20}" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy'})"/></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">期间类型：</td>
            <td align="left" class="l-table-edit-td"><input name="period_type_code" type="text" id="period_type_code" ltype="text" validate="{required:true,maxlength:20}" onchange="chargePeriodType()"/></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">核算期间：</td>
            <td align="left" class="l-table-edit-td"><input name="period_code" type="text" id="period_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr>
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">病种名称：</td>
            <td align="left" class="l-table-edit-td"><input name="drgs_name" style="width:160px;" type="text" id="drgs_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">诊疗项目：</td>
            <td align="left" class="l-table-edit-td"><input name="charge_item_name" style="width:160px;" type="text" id="charge_item_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr>
	</table>
	<div id="maingrid"></div>
</body>
</html>
