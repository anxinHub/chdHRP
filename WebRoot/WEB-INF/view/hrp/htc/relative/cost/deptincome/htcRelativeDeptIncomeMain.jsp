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
			name : 'year_month',
			value : $("#year_month").val()
		});
		grid.options.parms.push({
			name : 'appl_dept_id', 
			value : liger.get("appl_dept").getValue().split(".")[0]
		});
		grid.options.parms.push({ 
			name : 'exec_dept_id',
			value : liger.get("exec_dept").getValue().split(".")[0]
		});
		grid.options.parms.push({
			name : 'charge_kind_id',
			value : liger.get("charge_kind_id").getValue()
		});
		grid.options.parms.push({
			name : 'charge_item_id',
			value : liger.get("charge_item_id").getValue()
		});

		//加载查询条件
		grid.loadData(grid.where);
	}
	

	function loadHead() {
		grid = $("#maingrid").ligerGrid(
				{
					columns : [
							{
								display : '年月',
								name : 'acct_month',
								align : 'left',
								render : function(rowdata, rowindex, value) {
									if (rowdata.acc_year+rowdata.acc_month != "合计"){
										return "<a href='#' onclick=\"openUpdate('"
										+ rowdata.group_id + "|"  
										+ rowdata.hos_id + "|"
										+ rowdata.copy_code + "|"
										+ rowdata.income_detail_id+"');\" >" +rowdata.acc_year+ rowdata.acc_month
										+ "</a>";
									 }else {
										return rowdata.acc_year+ rowdata.acc_month
								  }
								}
							}, {
								display : '收费类别名称',
								name : 'charge_kind_name',
								align : 'left'
							}, {
								display : '收费项目名称',
								name : 'charge_item_name',
								align : 'left'
							}, {
								display : '开单科室名称',
								name : 'appl_dept_name',
								align : 'left'
							}, {
								display : '执行科室名称',
								name : 'exec_dept_name',
								align : 'left'
							}, {
								display : '数量',
								name : 'num',
								align : 'left',
								render: function(rowdata, rowindex, value) {
									return  formatNumber(rowdata.num, 2, 1);
								}
							}, {
								display : '金额',
								name : 'money',
								align : 'left',
								render: function(rowdata, rowindex, value) {
									return  formatNumber(rowdata.money, 2, 1);
								}
							},{
								display : '操作时间',
								name : 'create_date',
								align : 'left'
							} ],
					dataAction : 'server',
					dataType : 'server',
					usePager : true,
					delayLoad :true ,
					url : 'queryHtcRelativeDeptIncome.do',
					width : '100%',
					height : '100%',
					checkbox : true,
					rownumbers : true,
					checkBoxDisplay : f_checkBoxDisplay,
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
							text : '添加',
							id : 'add',
							click : add_open,
							icon : 'add'
						}, {
							line : true
						}, {
							text : '删除',
							id : 'delete',
							click : remove,
							icon : 'delete'
						},{
							text : '导入',
							id : 'imp',
							click : imp,
							icon : 'up'
						}]
					}
				});

		gridManager = $("#maingrid").ligerGetGridManager();
		
	}

	  function f_checkBoxDisplay(rowdata){
	        
	     	 if (rowdata.acc_year+rowdata.acc_month == "合计")
	  			    return false;

	  }
		
	function add_open(){
			$.ligerDialog.open({
				url : 'htcRelativeDeptIncomeAddPage.do?isCheck=false',
				height : 380,
				width : 500,
				title : '添加',
				modal : true,
				showToggle : false,
				showMax : false,
				showMin : true,
				isResize : true,
				buttons : [ {
					text : '确定',
					onclick : function(item, dialog) {
						dialog.frame.saveDeptIncome();
					},
					cls : 'l-dialog-btn-highlight'
				}, {
					text : '取消',
					onclick : function(item, dialog) {
						dialog.close();
					}
				} ]
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
							ParamVo.push(this.group_id + "&"
									+ this.hos_id + "&"
									+ this.copy_code + "&"
									+ this.income_detail_id);//实际代码中temp替换主键
	
						});
				$.ligerDialog.confirm('确定删除?', function(yes) {
					if (yes) {
						ajaxJsonObjectByUrl("deleteHtcRelativeDeptIncome.do", {
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

	function imp(){
		
		var para={
			    "column": [
			          { "name": "year_month",
			            "display": "年月",
			            "width": "200",
			            "require":true
			        },{
			            "name": "appl_dept_code",
			            "display": "开单科室编码",
			            "width": "200",
			            "require":true
			        },{
			            "name": "appl_dept_name",
			            "display": "开单科室名称",
			            "width": "200",
			            "require":true
			        },{
			            "name": "exec_dept_code",
			            "display": "执行科室编码",
			            "width": "200",
			            "require":true
			        },{
			            "name": "exec_dept_name",
			            "display": "执行科室名称",
			            "width": "200",
			            "require":true
			        },{ "name": "charge_kind_code",
			            "display": "收费类别编码",
			            "width": "200",
			            "require":true
			        },{
			            "name": "charge_kind_name",
			            "display": "收费类别名称",
			            "width": "200",
			            "require":true
			        },{
			            "name": "charge_item_code",
			            "display": "收费项目编码",
			            "width": "200",
			            "require":true
			        },{
			            "name": "charge_item_name",
			            "display": "收费项目名称",
			            "width": "200",
			            "require":true
			        },{
			            "name": "num",
			            "display": "数量",
			            "width": "200",
			            "require":true
			        },{
			            "name": "money",
			            "display": "金额",
			            "width": "200",
			            "require":true
			        },{
			            "name": "busi_data_source_code",
			            "display": "数据来源编码",
			            "width": "200",
			            "require":true
			        },{
			            "name": "busi_data_source_name",
			            "display": "数据来源名称",
			            "width": "200",
			            "require":true
			        }
			    ]
			};
		
			importSpreadView("hrp/htc/relative/cost/deptincome/impHtcRelativeDeptIncome.do?isCheck=false",para); 
		}
	function openUpdate(obj) {

		//实际代码中&temp替换主键
		var vo = obj.split("|");
		//rowdata.people_type_code+","+rowdata.wage_item_code+","+rowdata.cost_item_code
		var parm =  "group_id=" + vo[0] + "&" + "hos_id=" + vo[1] + "&" + "copy_code=" + vo[2]
				+ "&" + "income_detail_id=" + vo[3]

		$.ligerDialog.open({
			url : 'htcRelativeDeptIncomeUpdatePage.do?isCheck=false&' + parm,
			data : {},
			height : 350,
			width : 500,
			title : '修改',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : false,
			isResize : true,
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.saveDeptIncome();
				},
				cls : 'l-dialog-btn-highlight'
			}, {
				text : '取消',
				onclick : function(item, dialog) {
					dialog.close();
				}
			} ]
		});

	}
	function loadDict() {
		
		 autodate("#year_month","YYYYmm");
		 $("#year_month").ligerTextBox({width:160});

		     var appl_param = {
		    		   /* 开单科室为临床科室  */
	         		type_code:"('01')"
	         };
	         var exec_param = {
	        		 /* 执行科室为临床科室和医技科室  */
	         		type_code:"('01','02')"
	         };
	  
         autocomplete("#appl_dept","../../../info/base/queryHtcDeptDict.do?isCheck=false","id","text",true,true,appl_param);
		 autocomplete("#exec_dept","../../../info/base/queryHtcDeptDict.do?isCheck=false","id","text",true,true,exec_param); 
		 autocomplete("#charge_kind_id","../../../info/base/queryHtcChargeKindArrt.do?isCheck=false","id","text",true,true);
		 autocomplete("#charge_item_id","../../../info/base/queryHtcChargeItemArrt.do?isCheck=false","id","text",true,true); 

			 
			
	}

   </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">

		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">年月：</td>
			<td align="left" class="l-table-edit-td"><input class="Wdate"
				name="year_month" type="text" id="year_month" ltype="text"
				onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyyMM'})" style="width:160px;"/></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">开单科室：</td>
			<td align="left" class="l-table-edit-td">
			<input name="appl_dept" type="text" id="appl_dept" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">执行科室：</td>
			<td align="left" class="l-table-edit-td">
			<input name="exec_dept" type="text" id="exec_dept" /></td>
			<td align="left"></td>
		</tr>

		<tr>
		    <td align="right" class="l-table-edit-td" style="padding-left: 20px;">收费类别 ：</td>
			<td align="left" class="l-table-edit-td">
			  <input name="charge_kind_id" type="text" id="charge_kind_id" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">收费项目：</td>
			<td align="left" class="l-table-edit-td">
			  <input name="charge_item_id" type="text" id="charge_item_id" /></td>
			<td align="left"></td>
		</tr>
	</table>
	<div id="maingrid"></div>
</body>
</html>
