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
		grid.options.parms.push({
			name : 'drug_type_code',
			value : $("#drug_type_code").val()
		});
		grid.options.parms.push({
			name : 'drug_code',
			value : $("#drug_code").val()
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
				frozen : true,
				minWidth : 80
			}, {
				display : '核算年度',
				name : 'acc_year',
				align : 'left',
				frozen : true,
				minWidth : 80
			}, {
				display : '核算期间',
				name : 'period_name',
				align : 'left',
				frozen : true,
				minWidth : 80
			}, {
				display : '药品类别',
				name : 'drug_type_name',
				align : 'left',
				frozen : true,
				minWidth : 80
			}, {
				display : '药品编码',
				name : 'drug_code',
				align : 'left',
				frozen : true,
				minWidth : 120
			}, {
				display : '药品名称',
				name : 'drug_name',
				align : 'left',
				frozen : true,
				minWidth : 160
			}, {
				display : '规格',
				name : 'mode_code',
				align : 'left',
				minWidth : 80
			}, {
				display : '计量单位',
				name : 'unit_code',
				align : 'left',
				minWidth : 80
			}, {
				display : '生产厂家',
				name : 'factory_name',
				align : 'left',
				minWidth : 80
			}, {
				display : '药品收入',
				name : 'income_money',
				align : 'left',
				minWidth : 80,
				render : function(rowdata, value) {
					return formatNumber(rowdata.income_money, 2, 1);
				}
			}, {
				display : '直接成本',
				name : 'cost_money',
				align : 'left',
				minWidth : 80,
				render : function(rowdata, value) {
					return formatNumber(rowdata.cost_money, 2, 1);
				}
			}, {
				display : '管理成本',
				name : 'admin_cost_money',
				align : 'left',
				minWidth : 80,
				render : function(rowdata, value) {
					return formatNumber(rowdata.admin_cost_money, 2, 1);
				}
			}, {
				display : '总成本',
				name : '',
				align : 'left',
				minWidth : 80,
				render : function(rowdata, value) {
					return formatNumber(rowdata.cost_money + rowdata.admin_cost_money, 2, 1);
				}
			}, {
				display : '药品收益',
				name : '',
				align : 'left',
				minWidth : 80,
				render : function(rowdata, value) {
					return formatNumber(rowdata.income_money-(rowdata.cost_money + rowdata.admin_cost_money), 2, 1);

				}
			} ],
			dataAction : 'server',
			dataType : 'server',
			usePager : true,
			url : 'queryHtcgDrugAdminResult.do',
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
				} ]
			}
		});

		gridManager = $("#maingrid").ligerGetGridManager();
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
		$("#drug_type_code").ligerTextBox({
			width : 160
		});
		$("#drug_code").ligerTextBox({
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
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">核算年度：</td>
            <td align="left" class="l-table-edit-td"><input  class="Wdate" name="acc_year" type="text" id="acc_year" ltype="text" validate="{required:true,maxlength:20}" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy'})"/></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">期间类型：</td>
            <td align="left" class="l-table-edit-td"><input name="period_type_code" type="text" id="period_type_code" ltype="text" validate="{required:true,maxlength:20}" onchange="chargePeriodType()"/></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">核算期间：</td>
            <td align="left" class="l-table-edit-td"><input name="period_code" type="text" id="period_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">药品类别：</td>
            <td align="left" class="l-table-edit-td"><input name="drug_type_code" type="text" id="drug_type_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">药品编码：</td>
            <td align="left" class="l-table-edit-td"><input name="drug_code" type="text" id="drug_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
	</table>
	<div id="maingrid"></div>
</body>
</html>
