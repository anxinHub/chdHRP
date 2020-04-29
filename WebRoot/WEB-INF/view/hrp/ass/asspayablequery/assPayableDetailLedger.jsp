<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;" xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
	var grid;
	var gridManager = null;
	var userUpdateStr;

	$(function() {
		loadDict();

		loadHead(null); //加载数据
		$("#dept_id").ligerTextBox({width:160});
        $("#in_date_beg").ligerTextBox({width:90});
        $("#in_date_end").ligerTextBox({width:90});
        $("#ven_id").ligerTextBox({width:160});
        $("#store_id").ligerComboBox({width:160});
        $("#state").ligerComboBox({width:160});
        $("#ass_card_no").ligerTextBox({width:160});
        $("#run_date_beg").ligerTextBox({width:90});
        $("#run_date_end").ligerTextBox({width:90});
        autodate("#in_date_beg","YYYY-mm-dd","month_first");

		autodate("#in_date_end","YYYY-mm-dd","month_last");
		
	
	});
	//查询
	function query() {//根据表字段进行添加查询条件

		  grid.options.parms = [];
		  grid.options.newPage = 1;
		  grid.options.parms.push({name:'dept_id',value:liger.get("dept_id").getValue().split("@")[0]}); 
	  	  grid.options.parms.push({name:'dept_no',value:liger.get("dept_id").getValue().split("@")[1]}); 
	  	  grid.options.parms.push({name:'ven_id',value:liger.get("ven_id").getValue().split("@")[0]}); 
	  	  grid.options.parms.push({name:'ven_no',value:liger.get("ven_id").getValue().split("@")[1]});
	  	  grid.options.parms.push({name:'store_id',value:liger.get("store_id").getValue().split("@")[0]}); 
	  	  grid.options.parms.push({name:'store_no',value:liger.get("store_id").getValue().split("@")[1]});
	  	  grid.options.parms.push({name:'ass_id',value:liger.get("ass_id").getValue().split("@")[0]}); 
	  	  grid.options.parms.push({name:'ass_no',value:liger.get("ass_id").getValue().split("@")[1]});
	  	  grid.options.parms.push({name:'in_date_beg',value:$("#in_date_beg").val()}); 
	  	  grid.options.parms.push({name:'in_date_end',value:$("#in_date_end").val()});
	  	  grid.options.parms.push({name:'run_date_beg',value:$("#run_date_beg").val()}); 
	  	  grid.options.parms.push({name:'run_date_end',value:$("#run_date_end").val()}); 
	  	  grid.options.parms.push({name:'state',value:liger.get("state").getValue()}); 
	  	  grid.options.parms.push({name:'ass_type_id',value:liger.get("ass_type_id").getValue()}); 
	  	  grid.options.parms.push({name:'ass_card_no',value:$("#ass_card_no").val()}); 
		  grid.loadData(grid.where);//加载查询条件
	}

	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '供应商',
				name : 'ven_name',
				align : 'left'
			}, {
				display : '应付',
				name : 'price',
				align : 'right',
				render : function(rowdata, rowindex, value) {
					if (typeof (value) == 'undefined') {
						return "";
					} else {
						return formatNumber(rowdata.price, '${ass_05005}', 1);
					}
				}
			}, {
				display : '发票',
				align : 'center',
				formatter : '###,##0.00',
				columns : [ {
					display : '已开票',
					name : 'nc_d',
					align : 'right',
					formatter : '###,##0.00',
					render : function(rowdata, rowindex, value) {
						if (typeof (value) == 'undefined') {
							return "";
						} else {
							return formatNumber(rowdata.nc_d, '${ass_05005}', 1);
						}
					}
				}, {
					display : '未开票',
					name : 'nc_c',
					align : 'right',
					formatter : '###,##0.00',
					render : function(rowdata, rowindex, value) {
						if (typeof (value) == 'undefined') {
							return "";
						} else {
							return formatNumber(rowdata.nc_c, '${ass_05005}', 1);
						}
					}
				},{
					display : '开票',
					name : 'nc_e',
					align : 'right',
					formatter : '###,##0.00',
					render : function(rowdata, rowindex, value) {
						if (typeof (value) == 'undefined') {
							return "";
						} else {
							return formatNumber(rowdata.nc_e, '${ass_05005}', 1);
						}
					}
				}, {
					display : '退票',
					name : 'nc_f',
					align : 'right',
					formatter : '###,##0.00',
					render : function(rowdata, rowindex, value) {
						if (typeof (value) == 'undefined') {
							return "";
						} else {
							return formatNumber(rowdata.nc_f, '${ass_05005}', 1);
						}
					}
				}
				]
			},

			{
				display : '付款',
				align : 'center',
				columns : [ {
					display : '已付',
					name : 'qc_d',
					align : 'right',
					formatter : '###,##0.00',
					render : function(rowdata, rowindex, value) {
						if (typeof (value) == 'undefined') {
							return "";
						} else {
							return formatNumber(rowdata.qc_d, '${ass_05005}', 1);
						}
					}
				}, {
					display : '未付',
					name : 'qc_c',
					align : 'right',
					formatter : '###,##0.00',
					render : function(rowdata, rowindex, value) {
						if (typeof (value) == 'undefined') {
							return "";
						} else {
							return formatNumber(rowdata.qc_c, '${ass_05005}', 1);
						}
					}
				},{
					display : '付款',
					name : 'qc_e',
					align : 'right',
					formatter : '###,##0.00',
					render : function(rowdata, rowindex, value) {
						if (typeof (value) == 'undefined') {
							return "";
						} else {
							return formatNumber(rowdata.qc_e, '${ass_05005}', 1);
						}
					}
				}, {
					display : '退款',
					name : 'qc_f',
					align : 'right',
					formatter : '###,##0.00',
					render : function(rowdata, rowindex, value) {
						if (typeof (value) == 'undefined') {
							return "";
						} else {
							return formatNumber(rowdata.qc_f, '${ass_05005}', 1);
						}
					}
				} ]
			}

			],
			dataAction : 'server',
			dataType : 'server',
			usePager : true,
			url : 'queryPayVenSearch.do',
			width : '100%',
			height : '100%',
			checkbox : false,
			rownumbers : true,
			delayLoad : true,
			selectRowButtonOnly : true,
			toolbar : {
				items : [ {
					text : '查询',
					id : 'search',
					click : queryBtn,
					icon : 'search'
				} ]
			}
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	function queryBtn() {//查询
		query();
	}

	function loadDict() {//字典下拉框
		autocomplete("#dept_id", "../queryDeptDict.do?isCheck=false", "id", "text", true, true, null, false);
		
		autocomplete("#ven_id", "../queryHosSupDict.do?isCheck=false","id", "text",true,true,null,false,null,"350");
		
		autocomplete("#store_id", "../queryHosStoreDict.do?isCheck=false","id", "text",true,true,null,false,null,"350");
		
		autocomplete("#state", "../queryAssCardUseStateDict.do?isCheck=false","id", "text",true,true,null,false,null);
            
		autocomplete("#ass_id", "../queryAssNoDict.do?isCheck=false", "id","text", true, false, null,null,null,"230");
				
    	autocomplete("#ass_type_id","../queryAssTypeDictIsLast.do?isCheck=false","id","text",true,true,null,null,null,"160");
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">

	<div id="pageloading" class="l-loading" style="display: none"></div>

	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">入库日期：</td>
            <td align="left" class="l-table-edit-td" width="5%"><input name="in_date_beg" type="text" id="in_date_beg"  class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
            <td align="left" width="2%">至：</td>
            <td align="left" class="l-table-edit-td"><input name="in_date_end" type="text" id="in_date_end"  class="Wdate"
				onFocus="WdatePicker({minDate:'#F{$dp.$D(\'in_date_beg\');}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">供应商：</td>
            <td align="left" class="l-table-edit-td"><input name="ven_id" type="text" id="ven_id"   /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id"   /></td>
            <td align="left"></td>
        </tr> 
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">投入使用日期：</td>
            <td align="left" class="l-table-edit-td"><input name="run_date_beg" type="text" id="run_date_beg"  class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"  /></td>
            <td align="left">至：</td>
            <td align="left" class="l-table-edit-td"><input name="run_date_end" type="text" id="run_date_end"  class="Wdate"
				onFocus="WdatePicker({minDate:'#F{$dp.$D(\'run_date_beg\');}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">状态：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="state" type="text" id="state"   />
            </td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">仓库：</td>
            <td align="left" class="l-table-edit-td"><input name="store_id" type="text" id="store_id"   /></td>
            <td align="left"></td>
        </tr> 
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产名称：</td>
            <td align="left" class="l-table-edit-td" colspan="3"><input name="ass_id" type="text" id="ass_id"  /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">卡片编码：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="ass_card_no" type="text" id="ass_card_no"   />
            </td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产分类：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_type_id" type="text" id="ass_type_id"   /></td>
            <td align="left"></td>
        </tr>
	</table>

	<div id="maingrid"></div>


</body>
</html>
