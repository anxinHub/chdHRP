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
			name:'cost_type_id',
			value:liger.get("cost_type_code").getValue().split(".")[0]
		}); 

		//加载查询条件
		grid.loadData(grid.where);
	}
	
	//获取查询条件的数值
	function loadHead() {
		grid = $("#maingrid").ligerGrid(
				{
					columns : [ {
						display : '年份',
						name : 'acc_year',
						align : 'left'
					}, {
						display : '方案编码',
						name : 'plan_code',
						align : 'left'
					}, {
						display : '方案名称',
						name : 'plan_name',
						align : 'left'
					}, {
						display : '核算科室名称',
						name : 'proj_dept_name',
						align : 'left'
					}, {
						display : '成本分类名称',
						name : 'cost_type_name',
						align : 'left'
					}, {
						display : '直接成本资源动因',
						name : 'dir_res_name',
						align : 'left'
					}, {
						display : '公用成本资源动因',
						name : 'pub_res_name',
						align : 'left'
					}, {
						display : '管理成本资源动因',
						name : 'man_res_name',
						align : 'left'
					}, {
						display : '医辅成本资源动因',
						name : 'ass_res_name',
						align : 'left'
					} ],
					dataAction : 'server',
					dataType : 'server',
					usePager : true,
					url : 'queryHtcResCauseSet.do',
					width : '100%',
					height : '100%',
					delayLoad:true,
					checkbox : true,
					rownumbers : true,
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
							text : '初始化数据',
							id : 'init',
							click : init,
							icon : 'add'
						}, {
							line : true
						},{
							text : '批量编辑',
							id : 'modify',
							click : openBatchUpdate,
							icon : 'modify'
						}, {
							line : true
						}, {
							text : '删除',
							id : 'delete',
							click : remove,
							icon : 'delete'
						}, {
							line : true
						} ]
					}
				});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	function init(){
	   var formPara={
	        	acc_year:$("#acc_year").val(),
	        };
		ajaxJsonObjectByUrl("initHtcResCauseSet.do?isCheck=false",formPara,function(
				responseData) {
			if (responseData.state == "true") {
				query();
			}
		});
	}

	function openBatchUpdate() {
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var ParamVo = [];
			$(data).each(function(){
					ParamVo.push(
							    this.group_id + '@'
							  + this.hos_id + '@'
							  + this.copy_code + '@'
							  + this.acc_year + '@'
							  + this.plan_code + '@'
							  + this.proj_dept_no + '@'
							  + this.proj_dept_id + '@'
							  + this.cost_type_id);//实际代码中temp替换主键
					});
			$.ligerDialog.open({
				url : 'htcResCauseSetBatchUpdatePage.do?isCheck=false&ParamVo='+ ParamVo.toString(),
				data : {},
				height : 300,
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
						dialog.frame.saveResCauseSet();
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
	}

	function remove(){
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.warn('请选择行');
		} else {
			var ParamVo = [];
			$(data).each(
					function() {
						ParamVo.push(this.group_id + '@'
								  + this.hos_id + '@'
								  + this.copy_code + '@'
								  + this.acc_year + '@'
								  + this.plan_code + '@'
								  + this.proj_dept_no + '@'
								  + this.proj_dept_id + '@'
								  + this.cost_type_id);//实际代码中temp替换主键
					});
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteHtcResCauseSet.do", {
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
	
	function loadDict() {
		
        autocomplete("#plan_code","../../../info/base/queryHtcPlan.do?isCheck=false", "id", "text",true, true)
		
		autocomplete("#proj_dept_code","../../../info/base/queryHtcProjDeptDict.do?isCheck=false", "id", "text",true, true)
		
		autocomplete("#cost_type_code","../../../info/base/queryHtcDeptTypeDictNo.do?isCheck=false", "id", "text",true, true)
		  
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
			<td align="left" class="l-table-edit-td"><input class="Wdate" name="acc_year" type="text" id="acc_year" ltype="text"  onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy'})"  style="width:160px;"/></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">方案：</td>
			<td align="left" class="l-table-edit-td"><input name="plan_code" type="text" id="plan_code" ltype="text" /></td>
			<td align="left"></td>
		    <td align="right" class="l-table-edit-td" style="padding-left: 20px;">核算科室：</td>
			<td align="left" class="l-table-edit-td"><input name="proj_dept_code" type="text" id="proj_dept_code" ltype="text" /></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">成本分类：</td>
			<td align="left" class="l-table-edit-td"><input name="cost_type_code" type="text" id="cost_type_code" ltype="text" /></td>
			<td align="left"></td>
		</tr>
	</table>
	<div id="maingrid"></div>
</body>
</html>
