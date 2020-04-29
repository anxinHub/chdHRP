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
	var grid;
	var gridManager = null;
	var userUpdateStr;

	$(function() {
		loadDict()//加载下拉框
	
		//$("#apply_date2").ligerTextBox({width : 110});
		
		
	});
	//查询
	function query(obj) {
		
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件

		grid.options.parms.push({
			name : 'apply_year',
			value : $("#apply_year").val()
		});
		grid.options.parms.push({
			name : 'apply_month1',
			value : $("#apply_month1").val()
		});
		grid.options.parms.push({
			name : 'apply_month2',
			value : $("#apply_month2").val()
		});
		grid.options.parms.push({
			name : 'dept_id',
			value : liger.get("dept_id").getValue()
		});

		grid.options.parms.push({
			name : 'apply_date1',
			value : $("#apply_date1").val()
		});
		grid.options.parms.push({
			name : 'apply_date2',
			value : $("#apply_date2").val()
		});
		
		//加载查询条件
		grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
	}


	function save() {
		
		 if($("#audit_rem").val() == ""){
	 			$.ligerDialog.error('审批意见不能为空');
	 			return;	
	 		}
	        var formPara={
	           //check_id:$("#plan_id").val() =="" ?0:$("#plan_id").val(),
	           audit_rem:$("#audit_rem").val(),
	           paramVo:'${paramVo}'
	         };
	        
	        ajaxJsonObjectByUrl("assPlanDeptApprove.do?isCheck=false",formPara,function(responseData){
	            
	            if(responseData.state=="true"){
	                parent.query();
	            }
	        });
	
	}
	
	 function savePlanDeptApprove(){
        // if($("form").valid()){
             save();
         //}
    }
	
	function loadDict() {
		//字典下拉框
	
	}

</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">

		<tr>
		</tr>

		<tr>
<!--             <td align="right" class="l-table-edit-td"  style="padding-left:20px;">摘要：</td> -->
            <td align="left" class="l-table-edit-td" colspan="1"> 
            	<textarea rows="6" cols="70" name="audit_rem" id="audit_rem" ></textarea>
            </td>
<!--             <td align="left"></td> -->
        </tr>

	</table>
	<div id="maingrid"></div>
	<div id="resultPrint" style="display: none">
		<table width="100%">
			<thead>

				<tr>
				    <th width="200">申请ID</th>
					<th width="200">申请单号</th>
					<th width="200">购置年度</th>
					<th width="200">购置月份</th>
					<th width="200">申请科室ID</th>
					<th width="200">申请科室NO</th>
					<th width="200">申请人</th>
					<th width="200">申请日期</th>
					<th width="200">申请金额</th>
					<th width="200">摘要</th>
					<th width="200">制单人</th>
					<th width="200">制单日期</th>
					<th width="200">审核人</th>
					<th width="200">审核日期</th>
					<th width="200">是否追加申请</th>
					<th width="200">状态</th>
					<th width="200">备注</th>

				</tr>
			</thead>
			<tbody></tbody>
		</table>
	</div>
</body>
</html>
