<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
	var dataFormat;
	var grid_chargework;
	var grid_title;
	var gridManager_chargework = null;
	var gridManager_title = null;
	var parm = {};
	$(function() {
		loadDict()//加载下拉框
		loadHead(null);
	});
	//查询
	function query() {
		queryChargeWork();
	}
	function queryChargeWork() {
		grid_chargework.options.parms = [];
		grid_chargework.options.newPage = 1;
		var acc_year = $("#acc_year").val();
		var plan_code = liger.get("plan_code").getValue();
		var proj_dept_no = liger.get("proj_dept_code").getValue().split(".")[1]
		var proj_dept_id = liger.get("proj_dept_code").getValue().split(".")[0]
		if(acc_year == null || acc_year == ""){
			$.ligerDialog.error('请选择年度!')
	       return false;
		}

		if(plan_code == null || plan_code == ""){
			$.ligerDialog.error('请选择方案!')
		   return false;
		}
		if(proj_dept_no == null || proj_dept_no == ""){
			$.ligerDialog.error('请选择科室!')
			   return false;
		}
		if(proj_dept_id == null || proj_dept_id == ""){
			$.ligerDialog.error('请选择科室!')
			  return false;
		}
		//根据表字段进行添加查询条件
		grid_chargework.options.parms.push({
			name : 'acc_year',
			value : $("#acc_year").val()
		});
		grid_chargework.options.parms.push({
			name : 'plan_code',
			value : liger.get("plan_code").getValue()
		});
		grid_chargework.options.parms.push({
			name:'proj_dept_no',
			value:liger.get("proj_dept_code").getValue().split(".")[1]
		}); 
		grid_chargework.options.parms.push({
			name:'proj_dept_id',
			value:liger.get("proj_dept_code").getValue().split(".")[0]
		}); 
		//加载查询条件
		grid_chargework.loadData(grid_chargework.where);

	}
	function queryTitle(parm) {
		grid_title.options.parms = [];
		grid_title.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid_title.options.parms.push({
			name : 'acc_year',
			value : parm.acc_year
		});
		grid_title.options.parms.push({
			name:'plan_code',
			value:parm.plan_code
		}); 
		grid_title.options.parms.push({
			name:'proj_dept_no',
			value:parm.proj_dept_no
		}); 
		grid_title.options.parms.push({
			name:'proj_dept_id',
			value:parm.proj_dept_id
		});
		grid_title.options.parms.push({
			name:'charge_item_id',
			value:parm.charge_item_id
		});
		grid_title.options.parms.push({
			name:'work_code',
			value:parm.work_code
		});
		//加载查询条件
		grid_title.loadData(grid_title.where);
	}

	function loadHead() {
		loadHeadCharge();
		loadHeadTitle();
	}

	function loadHeadCharge(){
		grid_chargework = $("#maingrid_chargework").ligerGrid({
			columns : [{
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
				align : 'left'
			}, {
				display : '作业编码',
				name : 'work_code',
				align : 'left'
			}, {
				display : '作业名称',
				name : 'work_name',
				align : 'left'
			}, {
				display : '系数',
				name : 'ratio',
				align : 'left'
			}],
			dataAction : 'server',
			dataType : 'server',
			usePager : true,
			url : 'queryHtcDeptChargeWorkDetailWork.do?isCheck=false',
			width : '100%',
			height : '100%',
			//rownumbers : true,
			delayLoad : true,
			checkbox : 'checkbox',
			onDblClickRow : onDblClickRowCharge
		});
		gridManager_chargework = $("#maingrid_chargework").ligerGetGridManager();
	}

	function onDblClickRowCharge(rowdata, rowid, rowobj){
		parm = {
				group_id :rowdata.group_id,
		        hos_id :rowdata.hos_id,
		        copy_code :rowdata.copy_code,
		        acc_year :rowdata.acc_year,
		        plan_code :rowdata.plan_code,
		        plan_name :rowdata.plan_name,
		        proj_dept_no :rowdata.proj_dept_no,
		        proj_dept_id :rowdata.proj_dept_id,
		        proj_dept_code :rowdata.proj_dept_code,
		        proj_dept_name :rowdata.proj_dept_name,
		        charge_item_id :rowdata.charge_item_id,
		        charge_item_code :rowdata.charge_item_code,
		        charge_item_name :rowdata.charge_item_name,
		        work_code :rowdata.work_code,
		        work_name :rowdata.work_name,
		}
		queryTitle(parm);
	}
	function loadHeadTitle(){
		grid_title = $("#maingrid_title").ligerGrid({
			columns : [ {
				display : '职称编码',
				name : 'title_code',
				align : 'left'
			}, {
				display : '职称名称',
				name : 'title_name',
				align : 'left'
			}, {
				display : '操作人数',
				name : 'person_num',
				align : 'left',
				editor : {
					type : 'text'
				}
			}, {
				display : '操作时间',
				name : 'oper_time',
				align : 'left',
				editor : {
					type : 'text'
				}
			} ],
			dataAction : 'server',
			dataType : 'server',
			usePager : false,
			url : 'queryHtcDeptChargeWorkDetailTitle.do?isCheck=false',
			width : '100%',
			height : '100%',
			checkbox : 'checkbox',
			delayLoad : true,
			//rownumbers : true,
			selectRowButtonOnly : true,
			enabledEdit : true,
			onAfterEdit:f_onAfterEdit
		});
		gridManager_title = $("#maingrid_title").ligerGetGridManager();
	}
	

  function f_onAfterEdit(rowdata, rowid, rowobj){
    	var formPara = {
   			group_id :parm.group_id,
	        hos_id :parm.hos_id,
	        copy_code :parm.copy_code,
   			acc_year :parm.acc_year,
           	plan_code :parm.plan_code,
           	proj_dept_no :parm.proj_dept_no,
           	proj_dept_id :parm.proj_dept_id,
   			charge_item_id : parm.charge_item_id,
   			work_code:parm.work_code,
   			title_code:rowdata.record.title_code,
   			person_num:rowdata.record.person_num,
   			oper_time:rowdata.record.oper_time,
    	};
	  ajaxJsonObjectByUrl("saveHtcDeptChargeWorkDetail.do?isCheck=false", formPara, function(responseData) {
			if (responseData.state == "true") {
				queryTitle(parm);
			}
		});
     }

	function loadDict() {

		 $("#layout").ligerLayout({
			 leftWidth: '50%',
			 rightWidth: '50%',
			 allowLeftCollapse:false,
			 allowRightCollapse:false
         });
		
	    autocomplete("#plan_code","../../../info/base/queryHtcPlan.do?isCheck=false", "id", "text",true, true)
		
		autocomplete("#proj_dept_code","../../../info/base/queryHtcProjDeptDict.do?isCheck=false", "id", "text",true, true)
		  
		$("#acc_year").ligerTextBox({width:160});
		
		autodate("#acc_year", "YYYY");

		$(':button').ligerButton({width:80});
		 
	}
</script>

</head>

<body style="padding: 6px; overflow: hidden; width: 100%; height: 100%;">
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">年度：</td>
			<td align="left" class="l-table-edit-td"><input class="Wdate" name="acc_year" type="text" id="acc_year" ltype="text" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy'})" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">方案：</td>
			<td align="left" class="l-table-edit-td"><input name="plan_code" type="text" id="plan_code" ltype="text"/></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">核算科室：</td>
			<td align="left" class="l-table-edit-td"><input name="proj_dept_code" type="text" id="proj_dept_code" ltype="text" /></td>
			<td align="left"></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td"></td>
			<td align="left" class="l-table-edit-td"><input  type="button" value="查询" onclick="query();"/></td>
			<td align="left"></td>
		</tr>
	</table>
	<div id="layout">
           <div position="left"  title="收费项目作业信息    双击行选中" id="maingrid_chargework"></div>
           <div position="right" title="职称" id="maingrid_title"></div>  
    </div> 
</body>
</html>
