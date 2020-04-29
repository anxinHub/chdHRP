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
		//加载查询条件
		grid.loadData(grid.where);
	}

	function loadHead() {
		grid = $("#maingrid").ligerGrid(
				{
					columns : [
							{
								display : '核算科室编码',
								name : 'proj_dept_code',
								align : 'left',
							    render : function(rowdata, rowindex, value) {
									return "<a href='#' onclick=\"openUpdate('"
									+ rowdata.group_id + "|"
									+ rowdata.hos_id + "|"
									+ rowdata.copy_code +"|"
									+ rowdata.acc_year + "|"
									+ rowdata.plan_code + "|"
									+ rowdata.proj_dept_no + "|"
									+ rowdata.proj_dept_id + "|"
									+ rowdata.people_code + "|"
									+ "');\" >"
									+ rowdata.proj_dept_code + "</a>";
						        }
							}, {
								display : '核算科室名称',
								name : 'proj_dept_name',
								align : 'left'
							}, {
								display : '年度',
								name : 'acc_year',
								align : 'left'
							}, {
								display : '方案名称',
								name : 'plan_name',
								align : 'left'
							}, {
								display : '人员编码',
								name : 'people_code',
								align : 'left'
							}, {
								display : '人员名称',
								name : 'people_name',
								align : 'left'
							}, {
								display : '职称',
								name : 'title_name',
								align : 'left'
							}, {
								display : '总工时(分钟)',
								name : 'time_sum',
								align : 'left'
							} ],
					dataAction : 'server',
					dataType : 'server',
					usePager : true,
					url : 'queryHtcPeopleTime.do',
					width : '100%',
					height : '100%',
					delayLoad : true,
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
						}
						]
					}
				});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	function add_open(){
		$.ligerDialog.open({
			url : 'htcPeopleTimeAddPage.do?isCheck=false',
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
					dialog.frame.savePeopleTime();
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
							ParamVo.push(this.group_id + "@"
									   + this.hos_id + "@"
									   + this.copy_code + "@"
									   + this.acc_year + "@"
									   + this.plan_code + "@"
									   + this.proj_dept_no + "@"
									   + this.proj_dept_id + "@"
									   + this.people_code);//实际代码中temp替换主键
						});
				$.ligerDialog.confirm('确定删除?', function(yes) {
					if (yes) {
						ajaxJsonObjectByUrl("deleteHtcPeopleTime.do", {
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
	
	function openUpdate(obj) {
		var vo = obj.split("|");
		var parm = "&" + "group_id="  + vo[0] 
				 + "&" + "hos_id="    + vo[1]
				 + "&" + "copy_code=" + vo[2] 
				 + "&" + "acc_year="  + vo[3]
				 + "&" + "plan_code=" + vo[4]
		         + "&" + "proj_dept_no=" + vo[5]
		         + "&" + "proj_dept_id=" + vo[6]
		         + "&" + "people_code=" + vo[7]
		//实际代码中&temp替换主键
		$.ligerDialog.open({
					url : 'htcPeopleTimeUpdatePage.do?isCheck=false'+parm,
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
							dialog.frame.savePeopleTime();
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

         autocomplete("#plan_code","../../../info/base/queryHtcPlan.do?isCheck=false", "id", "text",true, true)
		
		autocomplete("#proj_dept_code","../../../info/base/queryHtcProjDeptDict.do?isCheck=false", "id", "text",true, true)
		
		
		 autodate("#acc_year","YYYY");
		 $("#acc_year").ligerTextBox({width:160});


	}
	
	
</script>
</head>
<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">年度：</td>
			<td align="left" class="l-table-edit-td"><input class="Wdate"
				type="text" name="acc_year" id="acc_year"
				onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy'})" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">方案：</td>
			<td align="left" class="l-table-edit-td"><input type="text"
				name="plan_code" id="plan_code" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">核算科室：</td>
			<td align="left" class="l-table-edit-td"><input type="text"
				id="proj_dept_code" name="proj_dept_code" /></td>
			<td align="left"></td>
		</tr>
	</table>
	<div id="maingrid"></div>
</body>
</html>
