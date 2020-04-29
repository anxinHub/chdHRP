<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">

     var kpi_code;
     var ratio;
     var goal_value;
     var grade_meth_code;
     var method_code;
	 var emp_led;
	 var emp_full;
    
    $(function ()
    {
        loadDict();
        
        $('input:checkbox').ligerCheckBox()

    });
	
    function loadDict(){
    	
    	autocomplete("#hos_id","../quertSysHosInfoDict.do?isCheck=false","id","text",true,true,"",false,'${hos_id}');
     
    	loadComboBox({id:"#goal_code",url:"../quertPrmGoalDict.do?isCheck=false&prem_data=true",value:"id",text:"text",autocomplete:true,hightLight:true,initValue:true,selectBoxWidth:'auto',maxWidth:'260',defaultSelect:true,async:false});
		
		autocomplete("#emp_no","../queryHosEmpDict.do?isCheck=false","id","text",true,true,"",true);
		
		$("#hos_id").ligerTextBox({
			width : 160
		});

		$("#goal_code").ligerTextBox({
			width : 330
		});
		
		$("#check_hos_id").ligerTextBox({
			width : 160
		});
		
		$("#acc_year").ligerTextBox({
			width : 80
		});

		$("#acc_month").ligerTextBox({
			width : 80
		});
		
		 kpi_code =  $("#kpi_code").ligerCheckBox({ disabled: false });
		 ratio =  $("#ratio").ligerCheckBox({ disabled: false });
		 goal_value =  $("#goal_value").ligerCheckBox({ disabled: false });
		 grade_meth_code =  $("#grade_meth_code").ligerCheckBox({ disabled: false });
		 method_code =  $("#method_code").ligerCheckBox({ disabled: false });
		 emp_led =  $("#emp_led").ligerCheckBox({ disabled: false });
		 emp_full = $("#emp_full").ligerCheckBox({ disabled: false });
		autodate("#acc_year", "yyyy");

		autodate("#acc_month", "mm");
	}      
    
     function saveEmpIntroduce(){
    	 
    	 if(liger.get("hos_id").getValue() == ""){
     		$.ligerDialog.warn('请选择单位');
  			return;
     	}
      	
      	if(liger.get("goal_code").getValue() == ""){
     		$.ligerDialog.warn('请选择目标');
  			return;
     	}
      	
     	if(liger.get("emp_no").getValue() == "" || liger.get("emp_no").getValue() == "."){
     		$.ligerDialog.warn('请选择考核单元');
  			return;
     	}
    	 
    	 
		var formPara = {
                
				kpiAcc_month:"${acc_month}",
				kpiEmp_no:"${emp_no}",
				kpiEmp_id:"${emp_id}",
				kpiGoal_code:'${goal_code}',
				kpiHos_id:'${hos_id}',
				kpiAcc_year:'${acc_year}',
				acc_year : $("#acc_year").val(),
				acc_month : $("#acc_month").val(),
				goal_code : liger.get("goal_code").getValue(),
				hos_id : liger.get("hos_id").getValue(),
				emp_no : liger.get("emp_no").getValue().split(".")[1],
				emp_id : liger.get("emp_no").getValue().split(".")[0],
				kpi_code : kpi_code.getValue(),
				ratio : ratio.getValue(),
				goal_value : goal_value.getValue(),
				grade_meth_code : grade_meth_code.getValue(),
				method_code : method_code.getValue(),
				emp_led:emp_led.getValue(),
				emp_full:emp_full.getValue()

			};
		ajaxJsonObjectByUrl("saveIntroduceEmpScheme.do", formPara, function(
				responseData) {
			parent.query();
		});


    }
    
    </script>
<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

    <table cellpadding="0" cellspacing="0" class="l-table-edit" id = "table1">
	 	  <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:10px;">单位信息：</td>
            <td align="left" class="l-table-edit-td" colspan="7"><input name="hos_id" type="text" id="hos_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td" style="padding-left: 20px;">目标名称：</td>
			<td align="left" class="l-table-edit-td" colspan="6"><input name="goal_code" type="text" id="goal_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
           </tr>
           <tr>
            <td align="right" class="l-table-edit-td" style="padding-left: 20px;">考核单元：</td>
			<td align="left" class="l-table-edit-td"  colspan="7"><input name="emp_no" type="text" id="emp_no" ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">考核年度：</td>
			<td align="left" class="l-table-edit-td" ><input name="acc_year" type="text" id="acc_year" ltype="text" validate="{required:true,maxlength:20}" class="Wdate"
					onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy'})" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">考核期间：</td>
			<td align="left" class="l-table-edit-td"  ><input name="acc_month" type="text" id="acc_month" ltype="text" validate="{required:true,maxlength:20}" class="Wdate"
					onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'MM'})" /></td>
			<td align="left"></td>
           </tr>
           
    </table>
    
    <table cellpadding="0" cellspacing="0" style="margin-top: 30px;margin-left: 50px;" class="l-table-edit" id = "table2">
    
    <tr>
	            <td align="right" class="l-table-edit-td" ><input type="checkbox" name="kpi_code" id="kpi_code" checked="checked"/></td>
	            <td align="left" >指标体系</td>
	            <td align="left" class="l-table-edit-td"  style="padding-left:10px;"></td>
	            <td align="left" class="l-table-edit-td"  style="padding-left:10px;"></td>
	            <td align="right" class="l-table-edit-td"><input type="checkbox" name="chbox" id = "ratio" checked="checked"></td>
	            <td align="left" >权重</td>
	            <td align="left" class="l-table-edit-td"  style="padding-left:10px;"></td>
	            <td align="left" class="l-table-edit-td"  style="padding-left:10px;"></td>
	            <td align="right" class="l-table-edit-td"><input type="checkbox" name="chbox" id = "goal_value" checked="checked"/></td>
	            <td align="left" >目标值</td>
	            <td align="left" class="l-table-edit-td"  style="padding-left:10px;"></td>
	            <td align="left" class="l-table-edit-td"  style="padding-left:10px;"></td>
	            <td align="right" class="l-table-edit-td"><input type="checkbox" name="chbox" id= "grade_meth_code" checked="checked"/></td>
	            <td align="left" >评分标准</td>
	            <td align="left" class="l-table-edit-td"  style="padding-left:10px;"></td>
	            <td align="left" class="l-table-edit-td"  style="padding-left:10px;"></td>
	            <td align="right" class="l-table-edit-td"><input type="checkbox" name="chbox" id = "method_code" checked="checked"/></td>
	            <td align="left" >取值方法</td>
	            <td align="left" class="l-table-edit-td"  style="padding-left:10px;"></td>
	            <td align="left" class="l-table-edit-td"  style="padding-left:10px;"></td>
	            <td align="right" class="l-table-edit-td"><input type="checkbox" name="chbox" id = "emp_led" checked="checked"/></td>
	            <td align="left" >指示灯</td>
	            <td align="left" ></td>
	            <td align="left" class="l-table-edit-td"  style="padding-left:10px;"></td>
	            <td align="left" class="l-table-edit-td"  style="padding-left:10px;"></td>
	            <td align="right" class="l-table-edit-td"><input type="checkbox" name="chbox" id = "emp_full" checked="checked"/></td>
	            <td align="left" >满分标准</td>
	            <td align="left" ></td>
            </tr>
    </table>
</body>
</html>
