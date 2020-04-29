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
			name:'plan_code',
			value:liger.get("plan_code").getValue()
		}); 
		grid.options.parms.push({
			name:'proj_dept_no',
			value:liger.get("proj_dept_code").getValue().split(".")[1]
		}); 
		grid.options.parms.push({
			name:'proj_dept_id',
			value:liger.get("proj_dept_code").getValue().split(".")[0]
		}); 

		grid.options.parms.push({
			name:'title_code',
			value:liger.get("title_code").getValue()
		}); 

		//加载查询条件
		grid.loadData(grid.where);
	}

	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '核算科室编码',
				name : 'proj_dept_code',
				align : 'left'
			}, {
				display : '核算科室名称',
				name : 'proj_dept_name',
				align : 'left'
			}, {
				display : '职称',
				name : 'title_name',
				align : 'left'
			},{
				display : '成本项目',
				name : 'cost_item_name',
				align : 'left'
			}, {
				display : '平均每分钟工资',
				name : 'cost_avg',
				align : 'right',
				render : function(rowdata, rowindex, value) {
					return formatNumber(rowdata.cost_avg, 2, 1);
				}
			} ],
			dataAction : 'server',
			dataType : 'server',
			usePager : true,
			url : 'queryHtcTitleCostEve.do',
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
					text : '删除',
					id : 'delete',
					click : remove,
					icon : 'delete'
				}, {
					line : true
				}, {
					text : '平均工资测算',
					id : 'averageWageReckon',
					click : averageWageReckon,
					icon : 'config'
				}, {
					line : true
				}]
			}
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	function remove(){
		var data = gridManager.getCheckedRows();
	   	  if (data.length == 0) {
				$.ligerDialog.error('请选择行');
				return false;
			}
	      	var ParamVo = [];
			$(data).each(function() {
				        ParamVo.push(this.group_id + "@"
								   + this.hos_id + "@"
								   + this.copy_code + "@"
								   + this.acc_year + "@"
								   + this.plan_code + "@"
								   + this.proj_dept_no + "@"
								   + this.proj_dept_id + "@"
								   + this.title_code + "@"
								   + this.cost_item_no + "@"
								   + this.cost_item_id);//实际代码中temp替换主键
					     });
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteHtcTitleCostEve.do", {
						ParamVo : ParamVo.toString()
					}, function(responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			});
    }
	function averageWageReckon() {
		
		var acc_year = $("#acc_year").val();
		var plan_code=liger.get("plan_code").getValue();
		if(acc_year == null || acc_year == ""){
			$.ligerDialog.error('请选择年度!')
	       return false;
		}

		if(plan_code == null || plan_code == ""){
			$.ligerDialog.error('请选择方案!')
		   return false;
		}
		
		var formPara = {
				acc_year:$("#acc_year").val(),
	        	plan_code:liger.get("plan_code").getValue()
		};
		ajaxJsonObjectByUrl("averageHtcWageReckon.do?isCheck=false", formPara, function(responseData) {
			if (responseData.state == "true") {
				query();
			}
		});
	}

	function loadDict() {

		 autocomplete("#plan_code","../../../info/base/queryHtcPlan.do?isCheck=false", "id", "text",true, true)
			
		 autocomplete("#proj_dept_code","../../../info/base/queryHtcProjDeptDict.do?isCheck=false", "id", "text",true, true)
			
		 autocomplete("#title_code","../../../info/base/queryHtcPeopleTitleDict.do?isCheck=false", "id", "text",true, true)

		  
		$("#acc_year").ligerTextBox({width:160});
		autodate("#acc_year", "YYYY");
		
	}

	
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">年度：</td>
			<td align="left" class="l-table-edit-td"><input class="Wdate" name="acc_year" type="text" id="acc_year" ltype="text"
				onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy'})"  style="width: 160px;" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">核算方案：</td>
			<td align="left" class="l-table-edit-td"><input name="plan_code" type="text" id="plan_code" ltype="text"/></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">核算科室：</td>
			<td align="left" class="l-table-edit-td"><input name="proj_dept_code" type="text" id="proj_dept_code" ltype="text" /></td>
			<td align="left"></td>
		</tr>
		<tr>
		  	<td align="right" class="l-table-edit-td" style="padding-left: 20px;">职称：</td>
			<td align="left" class="l-table-edit-td"><input name="title_code" type="text" id="title_code" ltype="text"  /></td>
			<td align="left"></td>
		</tr>
	</table>
	<div id="maingrid"></div>
</body>
</html>
