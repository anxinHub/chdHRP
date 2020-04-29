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
		grid.options.parms.push({name : 'year_month',value : $("#year_month").val()});
		//加载查询条件
		grid.loadData(grid.where);
	}


	function loadHead() {
		grid = $("#maingrid").ligerGrid(
				{
					columns : [
							{
								display : '年月',
								name : 'year_month',
								align : 'left',
								render : function(rowdata, rowindex, value) {
								return rowdata.acc_year + rowdata.acc_month
							  }
							},
							{
								display : '成本科室编码',
								name : 'dept_code',
								align : 'left'
							},
							{
								display : '成本科室名称',
								name : 'dept_name',
								align : 'left'
							},
							{
								display : '总成本',
								name : 'tot_amount',
								align : 'right',
								render : function(rowdata, rowindex, value) {
									if(rowdata.acc_year + rowdata.acc_month !="合计"){
										return rowdata.tot_amount=='0.00' ? '0.00':"<a href='#' onclick=\"openSelect('"
										    + rowdata.group_id + "|" 
											+ rowdata.hos_id + "|"
											+ rowdata.copy_code + "|"
											+ rowdata.acc_year + "|"
											+ rowdata.acc_month + "|"
											+ rowdata.dept_id + "|"
											+ rowdata.dept_name + "|"
											+ "1"+"');\" >" + formatNumber(rowdata.tot_amount, 2, 1)
											+ "</a>";
						        	  }else {
						        		  return formatNumber(rowdata.tot_amount, 2, 1)
								      }
								}
							},
							{
								display : '直接成本',
								name : 'prime_amount',
								align : 'right',
								render : function(rowdata, rowindex, value) {
									if(rowdata.acc_year + rowdata.acc_month !="合计"){
										return rowdata.prime_amount=='0.00' ? '0.00':"<a href='#' onclick=\"openSelect('"
										    + rowdata.group_id + "|" 
											+ rowdata.hos_id + "|"
											+ rowdata.copy_code + "|"
											+ rowdata.acc_year + "|"
											+ rowdata.acc_month + "|"
											+ rowdata.dept_id + "|"
											+ rowdata.dept_name + "|"
											+ "2"+"');\" >" + formatNumber(rowdata.prime_amount, 2, 1)
											+ "</a>";
						        	  }else {
						        		  return formatNumber(rowdata.prime_amount, 2, 1)
								      }
								}
							},
							{
								display : '公用分摊成本',
								name : 'pub_amount',
								align : 'right',
								render : function(rowdata, rowindex, value) {
									if(rowdata.acc_year + rowdata.acc_month !="合计"){
										return rowdata.pub_amount=='0.00' ? '0.00':"<a href='#' onclick=\"openSelect('"
										    + rowdata.group_id + "|" 
											+ rowdata.hos_id + "|"
											+ rowdata.copy_code + "|"
											+ rowdata.acc_year + "|"
											+ rowdata.acc_month + "|"
											+ rowdata.dept_id + "|"
											+ rowdata.dept_name + "|"
											+ "3"+"');\" >" + formatNumber(rowdata.pub_amount, 2, 1)
											+ "</a>";
						        	  }else {
						        		  return formatNumber(rowdata.pub_amount, 2, 1)
								    }
								}
							},
							{
								display : '管理分摊成本',
								name : 'man_amount',
								align : 'right',
								render : function(rowdata, rowindex, value) {
									if(rowdata.acc_year + rowdata.acc_month !="合计"){
										return rowdata.man_amount=='0.00' ? '0.00':"<a href='#' onclick=\"openSelect('"
										    + rowdata.group_id + "|" 
											+ rowdata.hos_id + "|"
											+ rowdata.copy_code + "|"
											+ rowdata.acc_year + "|"
											+ rowdata.acc_month + "|"
											+ rowdata.dept_id + "|"
											+ rowdata.dept_name + "|"
											+ "4"+"');\" >" + formatNumber(rowdata.man_amount, 2, 1)
											+ "</a>";
						        	  }else {
						        		  return formatNumber(rowdata.man_amount, 2, 1)
								  }
								}
							},
							{
								display : '医辅分摊成本',
								name : 'ass_amount',
								align : 'right',
								render : function(rowdata, rowindex, value) {
									if(rowdata.acc_year + rowdata.acc_month !="合计"){
										return rowdata.ass_amount=='0.00' ? '0.00':"<a href='#' onclick=\"openSelect('"
										    + rowdata.group_id + "|" 
											+ rowdata.hos_id + "|"
											+ rowdata.copy_code + "|"
											+ rowdata.acc_year + "|"
											+ rowdata.acc_month + "|"
											+ rowdata.dept_id + "|"
											+ rowdata.dept_name + "|"
											+ "5"+"');\" >" + formatNumber(rowdata.ass_amount, 2, 1)
											+ "</a>";
						        	  }else {
						        		  return formatNumber(rowdata.ass_amount, 2, 1)
								  }
								}
							} ],
					dataAction : 'server',
					dataType : 'server',
					usePager : true,
					delayLoad :true ,
					url : 'queryHtcTaskDeptCost.do',
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
						}, {
							line : true
						}, {
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
    	   if(rowdata.acc_year + rowdata.acc_month =="合计"){
                   return false
        	   }
        }

	function add_open(){
			$.ligerDialog.open({
				url : 'htcTaskDeptCostAddPage.do?isCheck=false',
				height : 350,
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
						dialog.frame.saveDeptCost();
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
			$(data).each(function() {
				ParamVo.push(
						        this.group_id
						+ "&" + this.hos_id
					    + "&" + this.copy_code
					    + "&" + this.acc_year
					    + "&" + this.acc_month
					    + "&" + this.dept_id);//实际代码中temp替换主键
			});
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteHtcTaskDeptCost.do", {
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
	
	
	function openSelect(obj) {
		var vo = obj.split("|");
		var parm = "group_id=" + vo[0] + "&" +"hos_id =" + vo[1] + "&" + "copy_code=" + vo[2]
		     +"&"+ "acc_year=" + vo[3] + "&" +"acc_month=" + vo[4] +"&"+ "dept_id=" +  vo[5]  
			 + "&" +"dept_name=" + encodeURI(vo[6]) + "&" +"cost_type=" + vo[7]
		//实际代码中&temp替换主键
		$.ligerDialog.open({
			url : 'htcTaskDeptCostSelectPage.do?isCheck=false&' + parm,
			data : {},
			height : 470,
			width : 1000,
			title : '查询',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : false,
			isResize : true,
			buttons : [ {
				text : '关闭',
				onclick : function(item, dialog) {
					dialog.close();
				}
			} ]
		});

	}

	 function imp(){
			var para={
				    "column": [
				        {
				            "name": "year_month",
				            "display": "年月",
				            "width": "200",
				            "require":true
				        },{
				            "name": "dept_code",
				            "display": "成本科室编码",
				            "width": "200",
				            "require":true
				        },{
				            "name": "dept_name",
				            "display": "成本科室名称",
				            "width": "200",
				            "require":true
				        },{
				            "name": "cost_item_code",
				            "display": "成本项目编码",
				            "width": "200",
				            "require":true
				        },{
				            "name": "cost_item_name",
				            "display": "成本项目名称",
				            "width": "200",
				            "require":true
				        },{
				            "name": "source_code",
				            "display": "资金来源编码",
				            "width": "200",
				            "require":true
				        },{
				            "name": "source_name",
				            "display": "资金来源名称",
				            "width": "200",
				            "require":true
				        },{
				            "name": "tot_amount",
				            "display": "总成本",
				            "width": "200"
				        },{
				            "name": "prime_amount",
				            "display": "直接成本",
				            "width": "200"
				        },{
				            "name": "pub_amount",
				            "display": "公用分摊成本",
				            "width": "200"
				        },{
				            "name": "man_amount",
				            "display": "管理分摊成本",
				            "width": "200"
				        },{
				            "name": "ass_amount",
				            "display": "医辅分摊成本",
				            "width": "200"
				        },
				    ]
				};
				importSpreadView("hrp/htc/relative/cost/deptcost/impHtcTaskDeptCost.do?isCheck=false",para); 
		}
	function loadDict() {
		//字典下拉框
		 $("#year_month").ligerTextBox({width:160});	
		 autodate("#year_month","YYYYmm");
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
				onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyyMM'})"  style="width:160px;"/></td>
			<td align="left"></td>
		</tr>
		
	</table>

	<div id="maingrid"></div>
</body>
</html>
