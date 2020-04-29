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
		grid.options.parms.push({
			name : 'scheme_code',
			value : liger.get("scheme_code").getValue()
		});
		grid.options.parms.push({
			name : 'acc_year',
			value : $("#acc_year").val()
		});
		grid.options.parms.push({
			name : 'period_type_code',
			value : liger.get("period_type_code").getValue()
		});
		grid.options.parms.push({
			name : 'period_code',
			value : liger.get("period_code").getValue()
		});
		//加载查询条件
		grid.loadData(grid.where);
	}

	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '期间类型',
				name : 'period_type_name',
				align : 'left',
				minWidth : 50
			}, {
				display : '核算年度',
				name : 'acc_year',
				align : 'left',
				minWidth : 50
			}, {
				display : '核算期间',
				name : 'period_name',
				align : 'left',
				minWidth : 50
			}, {
				display : '方案名称',
				name : 'scheme_name',
				align : 'left',
				minWidth : 150
			}, {
				display : '病种名称',
				name : 'drgs_name',
				align : 'left',
				minWidth : 150
			}, {
				display : '医嘱类别',
				name : 'recipe_type_name',
				align : 'left',
				minWidth : 100
			}, {
				display : '医嘱时程',
				name : 'clp_p_step',
				align : 'left',
				minWidth : 100
			}, {
				display : '项目编码',
				name : 'item_code',
				align : 'left',
				minWidth : 200
			}, {
				display : '项目名称',
				name : 'item_name',
				align : 'left',
				minWidth : 200
			}, {
				display : '数量',
				name : 'amount',
				align : 'left',
				minWidth : 100,
				render : function(rowdata, rowindex, value) {
					return formatNumber(rowdata.amount, 2, 1);
				}
			}, {
				display : '单价',
				name : 'price',
				align : 'left',
				minWidth : 100,
				render : function(rowdata, rowindex, value) {
					return formatNumber(rowdata.price, 2, 1);
				}
			}, {
				display : '收入',
				name : 'income_money',
				align : 'left',
				minWidth : 100,
				render : function(rowdata, rowindex, value) {
					return formatNumber(rowdata.income_money, 2, 1);
				}
			}, {
				display : '成本单价',
				name : 'cost_price',
				align : 'left',
				minWidth : 100,
				render : function(rowdata, rowindex, value) {
					return formatNumber(rowdata.cost_price, 2, 1);
				}
			}, {
				display : '成本',
				name : 'cost_money',
				align : 'left',
				minWidth : 100,
				render : function(rowdata, rowindex, value) {
					return formatNumber(rowdata.cost_money, 2, 1);
				}
			}, {
				display : '收益',
				name : '',
				align : 'left',
				minWidth : 100,
				render : function(rowdata, rowindex, value) {
					return formatNumber(rowdata.income_money- rowdata.cost_money, 2, 1);
				}
			} ],
			dataAction : 'server',
			dataType : 'server',
			usePager : true,
			url : 'queryHtcgHosPdrgsCost.do',
			width : '100%',
			height : '100%',
			checkbox : true,
			rownumbers : true,
			selectRowButtonOnly : true,//heightDiff: -10,
			delayLoad : true,
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
			$.ligerDialog.error('请选择方案!');
			return;
		}

		if (acc_year == '') {
			$.ligerDialog.error('请选择核算年度!');
			return;
		}

		var formPara = {
			acc_year : acc_year,
			scheme_code : scheme_code
		};

		ajaxJsonObjectByUrl("initHtcgHosPdrgsCost.do?isCheck=false", formPara,
				function(WorkponseData) {
					if (WorkponseData.state == "true") {
						query();
					}
				});
	}
	
	function remove() {

		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var ParamVo = [];
			$(data).each(
					function() {
						ParamVo.push(this.group_id + "@"
								+ this.hos_id + "@"
								+ this.copy_code + "@"
								+ this.period_type_code + "@"
								+ this.acc_year + "@"
								+ this.acc_month + "@"
								+ this.scheme_code + "@"
								+ this.drgs_code + "@"
								+ this.clp_p_step + "@"
								+ this.item_code + "@"
								+ this.charge_nature_code + "@"
								+ this.price + "@"
								+ this.recipe_type_code);//实际代码中temp替换主键
					});
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteHtcgHosPdrgsCost.do", {
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
		autocomplete("#scheme_code","../../base/queryHtcgSchemeDict.do?isCheck=false", "id", "text", true,true);
		autocomplete("#period_type_code","../../base/queryHtcgPeriodTypeDict.do?isCheck=false","id","text",true,true);
		$("#acc_year").ligerTextBox({
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
            <td align="left" class="l-table-edit-td"><input name="scheme_code" type="text" id="scheme_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">核算年度：</td>
            <td align="left" class="l-table-edit-td"><input class="Wdate"  name="acc_year" type="text" id="acc_year" ltype="text" validate="{required:true,maxlength:20}" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy'})"/></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">期间类型：</td>
            <td align="left" class="l-table-edit-td"><input name="period_type_code" type="text" id="period_type_code" ltype="text" validate="{required:true,maxlength:20}"  onchange="chargePeriodType()"/></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">核算期间：</td>
            <td align="left" class="l-table-edit-td"><input name="period_code" type="text" id="period_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
	</table>
	<div id="maingrid"></div>
</body>
</html>
