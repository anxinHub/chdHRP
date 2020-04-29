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
<style type="text/css"> 
  		.input-right-warp {
		    text-align: right;
		    line-height: 28px;
		    padding-bottom: 6px;
		}
		.input-right-warp>div {
		    display: inline-block;
		    margin-right: 10px;
		    margin-bottom: -6px;
		}
		
</style> 
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

		//加载查询条件
		grid.loadData(grid.where);
	}

	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '期间类型',
				name : 'period_type_name',
				align : 'left',
				frozen:true,
				minWidth:80
			}, {
				display : '核算年度',
				name : 'acc_year',
				align : 'left',
				frozen:true,
				minWidth:80
			}, {
				display : '核算期间',
				name : 'period_name',
				align : 'left',
				frozen:true,
				minWidth:80
			}, {
				display : '药品类别',
				name : 'drug_type_name',
				align : 'left',
				frozen:true,
				minWidth:80
			}, {
				display : '药品编码',
				name : 'drug_code',
				align : 'left',
				frozen:true,
				minWidth:120
			}, {
				display : '药品名称',
				name : 'drug_name',
				align : 'left',
				frozen:true,
				minWidth:180
			}, {
				display : '规格',
				name : 'mode_code',
				align : 'left',
				minWidth:80
			}, {
				display : '计量单位',
				name : 'unit_code',
				align : 'left',
				minWidth:80
			}, {
				display : '生产厂家',
				name : 'factory_name',
				align : 'left',
				minWidth:120
			}, {
				display : '数量',
				name : 'amount',
				align : 'left',
				minWidth:80,
				render : function(rowdata, rowindex, value) {
	  					return formatNumber(rowdata.amount, 2, 1);
	  				}
			}, {
				display : '销售单价',
				name : 'price',
				align : 'left',
				minWidth:80,
				render : function(rowdata, rowindex, value) {
  					return formatNumber(rowdata.price, 2, 1);
  				}
			}, {
				display : '药品收入',
				name : 'income_money',
				align : 'left',
				minWidth:80,
				render : function(rowdata, rowindex, value) {
  					return formatNumber(rowdata.income_money, 2, 1);
  				}
			}, {
				display : '管理成本',
				name : 'admin_cost_money',
				align : 'left',
				minWidth:80,
				render : function(rowdata, rowindex, value) {
  					return formatNumber(rowdata.admin_cost_money, 2, 1);
  				}
			}, {
				display : '备注',
				name : 'note',
				align : 'left',
				minWidth:80
			} ],
			dataAction : 'server',
			dataType : 'server',
			usePager : true,
			url : 'queryHtcgDrugAdminCost.do',
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
				}, {
					text : '工作量分摊',
					id : 'workload',
					click:calculate_workload,
					icon : 'add'
				}, {
					line : true
				}, {
					text : '收支配比分摊',
					id : 'income',
					click:calculate_income,
					icon : 'add'
				}, {
					line : true
				}]
			}
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

function init(){
		var acc_year=$("#acc_year").val();
    	var scheme_code=liger.get("scheme_code").getValue();
    	if(scheme_code==''){
    		$.ligerDialog.error('请选择方案!');
    		return ;
    	}
    	if(acc_year==''){
    		$.ligerDialog.error('请选择核算年度!');
    		return ;
    	}
    	var formPara={
            acc_year:acc_year,
            scheme_code:scheme_code
          };
		ajaxJsonObjectByUrl("initHtcgDrugAdminCost.do?isCheck=false",formPara, function(WorkponseData) {
			if (WorkponseData.state == "true") {
				query();
			}
		});
	}
		
	 function collectDeptDrugAdminCost(){
		    var acc_year=$("#acc_year").val();
	    	var scheme_code=liger.get("scheme_code").getValue();
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
			ajaxJsonObjectByUrl("collectHtcgDeptDrugAdminCost.do?isCheck=false",
					formPara, function(WorkponseData) {
						if (WorkponseData.state == "true") {
							
						}
					});
		}

		
	//工作量分摊
	function calculate_workload() {
    	var scheme_code=liger.get("scheme_code").getValue();
		var acc_year=$("#acc_year").val();
    	var dept_code=liger.get("dept_code").getValue();
    	if(scheme_code==''){
    		$.ligerDialog.error('请选择方案!');
    		return ;
    	}
    	if(acc_year==''){
    		$.ligerDialog.error('请选择核算年度!');
    		return ;
    	}
    	if(dept_code==''){
    		$.ligerDialog.error('请选择科室!');
    		return ;
    	}
    	var formPara={
            acc_year:acc_year,
            scheme_code:scheme_code,
            dept_no:dept_code.split(".")[0],
            dept_id:dept_code.split(".")[1],
          };
		ajaxJsonObjectByUrl("workloadHtcgDrugAdminCost.do?isCheck=false",formPara, function(WorkponseData) {
			if (WorkponseData.state == "true") {
				query();
			}
		});
	}
	//收支配比
	function calculate_income() {
		var acc_year=$("#acc_year").val();
    	var scheme_code=liger.get("scheme_code").getValue();
    	var dept_code=liger.get("dept_code").getValue();
    	if(acc_year==''){
    		$.ligerDialog.error('请选择核算年度!');
    		return ;
    	}
    	if(scheme_code==''){
    		$.ligerDialog.error('请选择方案!');
    		return ;
    	}
    	if(dept_code==''){
    		$.ligerDialog.error('请选择科室!');
    		return ;
    	}
    	var formPara={
            acc_year:acc_year,
            scheme_code:scheme_code,
            dept_no:dept_code.split(".")[0],
            dept_id:dept_code.split(".")[1],
          };
		ajaxJsonObjectByUrl("revenueHtcgDrugAdminCost.do?isCheck=false",
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
						ParamVo.push(this.group_id + "@"
								+ this.hos_id + "@"
								+ this.copy_code + "@"
								+ this.period_type_code + "@"
								+ this.acc_year + "@"
								+ this.acc_month + "@"
								+ this.drug_code);//实际代码中temp替换主键
					});
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteHtcgDrugAdminCost.do", {
						ParamVo :  ParamVo.toString()
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
		$("#dept_code").ligerComboBox({
			url : "../../base/queryDeptDrugAdminCostDict.do?isCheck=false",
			valueField : "id",
			textField : "text",
			setTextBySource:true,
			keySupport:true,
			autocomplete:true,
			onSelected : function (value,text) {
				var dept_no = value.split(".")[0]
				var dept_id = value.split(".")[1]
				var formPara = {dept_no:dept_no,dept_id:dept_id}
				ajaxJsonObjectByUrl("queryHtcgDeptDrugAdminCost.do?isCheck=false",
						formPara, function(WorkponseData) {
							 $("#admin_cost_money").val(WorkponseData.admin_cost_money)
						});
				
	       }
		});
		 $("#acc_year").ligerTextBox({width:160});
    	 $("#drug_code").ligerTextBox({width:160});
    	 $("#mode_code").ligerTextBox({width:160});
    	 $("#markup_percent").ligerTextBox({width:160});
    	 $(':button').ligerButton({ width: 80 });
    	 $("#admin_cost_money").ligerTextBox({width:100,disabled:true});
    	 $("#admin_cost_money").attr("disabled",true)
    	 $("#dept_code").ligerTextBox({width:160});
    	 
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
            <td align="right" class="l-table-edit-td" style="padding-left: 20px;">核算年度：</td>
			<td align="left" class="l-table-edit-td"><input class="Wdate"
				name="acc_year" type="text" id="acc_year" ltype="text"
				onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy'})" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">期间类型：</td>
			<td align="left" class="l-table-edit-td"><input
				name="period_type_code" type="text" id="period_type_code" 
				onchange="chargePeriodType()" /></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">核算期间：</td>
			<td align="left" class="l-table-edit-td"><input
				name="period_code" type="text" id="period_code" ltype="text" /></td>
			<td align="left"></td>
		</tr>
	</table>
	<div>
	   <hr style="height:1px;border:none;border-top:1px  double #c1dbfa;" /> 
	</div>
	  <div class ="input-right-warp"> 
	                <label>科室名称：</label>
		            <input name="dept_code" type="text" id="dept_code" ltype="text" />
		            <label>管理成本：</label>
	                <input name="admin_cost_money" type="text" id="admin_cost_money" ltype="text" />
	                <input type="button" value="成本采集" onclick="collectDeptDrugAdminCost();" />
	</div>
	<div id="maingrid"></div>
</body>
</html>
