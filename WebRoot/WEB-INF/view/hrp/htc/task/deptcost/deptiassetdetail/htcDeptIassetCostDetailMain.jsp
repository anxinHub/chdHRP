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
    
    $(function ()
    {
	    loadDict()//加载下拉框
		loadHead(null);

	});
	//查询
	function query() {

		grid.options.parms = [];

		grid.options.newPage = 1;

		grid.options.parms.push({
			name : 'start_year_month',
			value : $("#start_year_month").val()
		});

		grid.options.parms.push({
			name : 'end_year_month',
			value : $("#end_year_month").val()
		});

	 	grid.options.parms.push({
			name : 'dept_no',
			value : liger.get("dept_code").getValue().split(".")[1]
		});
		grid.options.parms.push({
			name : 'dept_id',
			value : liger.get("dept_code").getValue().split(".")[0]
		});
		grid.options.parms.push({
			name : 'asset_type_code',
			value : liger.get("asset_type_code").getValue()
		});
		grid.options.parms.push({
			name : 'asset_code',
			value : liger.get("asset_code").getValue()
		});
		grid.options.parms.push({
			name : 'source_id',
			value : liger.get("source_code").getValue()
		});


		//加载查询条件
		grid.loadData(grid.where);
	}

	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '核算年月',
				align : 'left',
				render : function(rowdata, rowindex, value) {
					return rowdata.acc_year + rowdata.acc_month;
				}
			},{
				display : '科室编码',
				name : 'dept_code',
				align : 'left'
			}, {
				display : '科室ID',
				name : 'dept_id',
				align : 'left',
				hide : true,
				width : 1
			}, {
				display : '科室名称',
				name : 'dept_name',
				align : 'left'
			}, {
				display : '成本项目编码',
				name : 'cost_item_code',
				align : 'left'
			}, {
				display : '成本项目名称',
				name : 'cost_item_name',
				align : 'left'
			}, {
				display : '卡片号',
				name : 'asset_code',
				align : 'left'
			}, {
				display : '资产名称',
				name : 'asset_name',
				align : 'left'
			}, {
				display : '折旧额',
				name : 'depre_amount',
				align : 'right',
				render : function(rowdata, rowindex, value) {
					return formatNumber(rowdata.depre_amount, 2, 1);
				}
			}, {
				display : '资金来源',
				name : 'source',
				align : 'left',
				hide : true,
				width : 1
			}, {
				display : '资金来源',
				name : 'source_name',
				align : 'left'
			} ],

			dataAction : 'server',
			dataType : 'server',
			usePager : true,
			url : 'queryHtcDeptIassetCostDetail.do',
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
				},
				{
					text : '数据核对',
					id : 'check',
					click : check,
					icon : 'settings'
				}, {
					line : true
				}]
			}
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	function check(){
				$.ligerDialog.open({
					url : 'htcDeptIassetCostDetailCheckPage.do?isCheck=false',
					height : 450,
					width : 790,
					title : '数据核对',
					modal : true,
					showToggle : false,
					showMax : false,
					showMin : true,
					isResize : true
				});
		   }

	function loadDict(){
		
		autocomplete("#dept_code","../../../info/base/queryHtcDeptDict.do?isCheck=false", "id","text", true, true);

		autocomplete("#asset_type_code","../../../info/base/queryHtcIassetTypeDict.do.do?isCheck=false","id", "text", true, true);

		autocomplete("#asset_code","../../../info/base/queryHtcIassetDict.do?isCheck=false","id","text", true, true);

		autocomplete("#source_code","../../../info/base/queryHtcSourceDict.do?isCheck=false","id","text",true,true);

		autodate("#start_year_month", "YYYYmm");
		
		autodate("#end_year_month", "YYYYmm");
		
		$("#start_year_month").ligerTextBox({
			width : 70
		});
		$("#end_year_month").ligerTextBox({
			width : 70
		});
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">期间：</td>
			<td>
				<table style="font-size: 12px;">
					<tr>
						<td align="left" class="l-table-edit-td"><input class="Wdate"
							name="start_year_month" type="text" id="start_year_month"
							style="width: 70px;" ltype="text"
							onFocus="WdatePicker({startDate: '%y%M',isShowClear:false,readOnly:true,dateFmt:'yyyyMM'})" />
						</td>
						<td align="right"><span>至&nbsp;</span></td>
						<td><input class="Wdate" name="end_year_month" type="text"
							id="end_year_month" style="width: 70px;" ltype="text"
							onFocus="WdatePicker({startDate: '%y%M',isShowClear:false,readOnly:true,dateFmt:'yyyyMM'})" />
						</td>
					</tr>
				</table>
			</td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室编码：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_code"
				type="text" id="dept_code" ltype="text" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">资产分类：</td>
			<td align="left" class="l-table-edit-td"><input
				name="asset_type_code" type="text" id="asset_type_code" ltype="text" /></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">资产：</td>
			<td align="left" class="l-table-edit-td"><input
				name="asset_code" type="text" id="asset_code" ltype="text"/></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">资金来源：</td>
			<td align="left" class="l-table-edit-td"><input name="source_code"
				type="text" id="source_code" ltype="text"  /></td>
			<td align="left"></td>
		</tr>
	</table>
	<div id="maingrid"></div>
</body>
</html>
