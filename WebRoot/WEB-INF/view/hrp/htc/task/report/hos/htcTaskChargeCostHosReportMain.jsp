<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
    <%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
    <script type="text/javascript">
  	var grid;
    var gridManager = null;
    $(function(){
     	loadDict() ;
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
			name : 'plan_code',
			value : liger.get("plan_code").getValue()
		});
		 grid.options.parms.push({
			name : 'charge_item_id',
			value : liger.get("charge_item_code").getValue()
		}); 
		
		//加载查询条件
		grid.loadData(grid.where);
	}
	
    function loadHead() {
		grid = $("#maingrid").ligerGrid(
				{
					columns : [
							{
								display : '收费项目编码',
								name : 'charge_item_code',
								align : 'left'
							}, {
								display : '收费项目名称',
								name : 'charge_item_name',
								align : 'left'
							}, {
								display : '单价',
								name : 'price',
								align : 'left',
								render: function(rowdata, rowindex, value) {
									return  formatNumber(rowdata.price, 2, 1);
								}

							}, {
								display : '单位成本',
								name : 'cost_eve',
								align : 'left',
								render: function(rowdata, rowindex, value) {
									return  formatNumber(rowdata.cost_eve, 2, 1);
								}
							}, {
								display : '活劳动成本',
								name : 'people_cost_eve',
								align : 'left',
								render: function(rowdata, rowindex, value) {
									return  formatNumber(rowdata.people_cost_eve, 2, 1);
								}
							}, {
								display : '物化劳动成本-材料',
								name : 'mate_cost_eve',
								align : 'left',
								render: function(rowdata, rowindex, value) {
									return  formatNumber(rowdata.mate_cost_eve, 2, 1);
								},
								width : 110
							}, {
								display : '物化劳动成本-折旧',
								name : 'asset_cost_eve',
								align : 'left',
								render: function(rowdata, rowindex, value) {
									return  formatNumber(rowdata.asset_cost_eve, 2, 1);
								},
								width : 110
							}, {
								display : '责任风险成本',
								name : 'zr_cost_eve',
								align : 'left',
								render: function(rowdata, rowindex, value) {
									return  formatNumber(rowdata.zr_cost_eve, 2, 1);
								}
							}, {
								display : '其他成本',
								name : 'qt_cost_eve',
								align : 'left',
								render: function(rowdata, rowindex, value) {
									return  formatNumber(rowdata.qt_cost_eve, 2, 1);
								}
							}, {
								display : '保本点',
								name : 'bbd',
								align : 'left',
								render: function(rowdata, rowindex, value) {
									return  formatNumber(rowdata.bbd, 2, 1);
								}
							}],
					dataAction : 'server',
					dataType : 'server',
					usePager : true,
					url : 'queryHtcTaskChargeCostHosReport.do',
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
						},{ line:true } ]
					}
				});

		gridManager = $("#maingrid").ligerGetGridManager();
	}
    
   
	function loadDict() {

		autocomplete("#plan_code","../../../info/base/queryHtcPlan.do?isCheck=false", "id", "text",true, true);
		autocomplete("#charge_item_code","../../../info/base/queryHtcChargeItemArrt.do?isCheck=false","id","text",true,true); 
		autodate("#acc_year", "yyyy");
		$("#acc_year").ligerTextBox({width:100});
			
	}
    </script>
</head>
<body>
<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar" ></div>
   <table>
          <tr>
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;">年度：</td>
            <td align="left" class="l-table-edit-td"><input  class="Wdate" name="acc_year" type="text" id="acc_year" ltype="text"  style="width:160px;" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy'})" onchange="changeAcctYear()" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">方案：</td>
            <td align="left" class="l-table-edit-td"><input  name="plan_code" type="text" id="plan_code" ltype="text" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">收费项目：</td>
	        <td align="left" class="l-table-edit-td"><input  name="charge_item_code" type="text" id="charge_item_code" ltype="text" /></td>
	        <td align="left"></td>
         </tr>
    </table>
<div id="maingrid"></div>
</body>
</html>