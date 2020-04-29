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
	 var dept_led;
	 var dept_full;
    
    $(function ()
    {
        loadDict();
        
        $('input:checkbox').ligerCheckBox();
        
        $("#listbox1").ligerListBox({
        	url : '../queryPrmDeptDict.do?isCheck=false&pageSize=1000',
			valueField : 'id',
			textField : 'text',
            isShowCheckBox: true,
            isMultiSelect: true,
            height: 230,
            width : 625
        });

    });
   
    function loadDict(){
    	
    	autocomplete("#hos_id","../quertSysHosInfoDict.do?isCheck=false","id","text",true,true,"",false,'${hos_id}',200);
     
		loadComboBox({id:"#goal_code",url:"../quertPrmGoalDict.do?isCheck=false&prem_data=true",value:"id",text:"text",autocomplete:true,hightLight:true,initValue:true,selectBoxWidth:'300',maxWidth:'300',defaultSelect:true,async:false});
		
		//autocomplete("#dept_no","../queryPrmDeptDict.do?isCheck=false","id","text",true,true,"",true);
		//loadComboBox({id:"#dept_no",url:"../queryPrmDeptDict.do?isCheck=false",value:"id",text:"text",autocomplete:true,hightLight:true,initValue:true,selectBoxWidth:'220',maxWidth:'260',defaultSelect:true,async:false});
		//loadComboBox({id:"#dept_noM",url:"../queryPrmDeptDict.do?isCheck=false",value:"id",text:"text",autocomplete:true,hightLight:true,initValue:true,selectBoxWidth:'220',maxWidth:'260',defaultSelect:false,async:false});
		autocomplete("#dept_no","../queryPrmDeptDict.do?isCheck=false","id","text",true,true,"",true,"",200,"",200);
		//autocomplete("#dept_noM","../queryPrmDeptDict.do?isCheck=false","id","text",true,true,"",true,"",200,"",200);
		
		
		//liger.get("dept_noM").setValue('${dept_id},${dept_no}');
		//liger.get("dept_noM").setText('${dept_text}');
		
		
		$("#hos_id").ligerTextBox({width : 200});
		$("#goal_code").ligerTextBox({width : 300});
		$("#check_hos_id").ligerTextBox({width : 160});
		$("#year_month").ligerTextBox({width : 205});
		//$("#acc_month").ligerTextBox({width : 80});
		$("#goal_acct_year_from").ligerTextBox({width : 80});
		$("#goal_acct_year_to").ligerTextBox({width : 80});
		autodate("#year_month", "yyyymm");
		autodate("#goal_acct_year_from", "yyyymm");
		autodate("#goal_acct_year_to", "yyyymm");
		
		kpi_code =  $("#kpi_code").ligerCheckBox({ disabled: false });
		ratio =  $("#ratio").ligerCheckBox({ disabled: false });
		goal_value =  $("#goal_value").ligerCheckBox({ disabled: false });
		grade_meth_code =  $("#grade_meth_code").ligerCheckBox({ disabled: false });
		method_code =  $("#method_code").ligerCheckBox({ disabled: false });
		dept_led =  $("#dept_led").ligerCheckBox({ disabled: false });
		dept_full = $("#dept_full").ligerCheckBox({ disabled: false });
		
	}      
    
     function saveDeptIntroduce(){
    	 
    	 if(liger.get("hos_id").getValue() == ""){
    		$.ligerDialog.warn('请选择单位');
 			return;
    	}
     	
     	if(liger.get("goal_code").getValue() == ""){
    		$.ligerDialog.warn('请选择目标');
 			return;
    	}
     	
    	if(liger.get("dept_no").getValue() == "" || liger.get("dept_no").getValue() == "."){
    		$.ligerDialog.warn('请选择源考核单元');
 			return;
    	}
    	 
		var formPara = {
				
				//kpiAcc_month:"${acc_month}",
				//kpiDept_no:"${dept_no}",
				//kpiDept_id:"${dept_id}",
				//kpiAcc_year:'${acc_year}',
				goal_code : liger.get("goal_code").getValue(),
				hos_id    : liger.get("hos_id").getValue(),
				dept_id   : liger.get("dept_no").getValue().split(".")[0],
				dept_no   : liger.get("dept_no").getValue().split(".")[1],
				acc_year  : $("#year_month").val().substring(0,4),
				acc_month : $("#year_month").val().substring(4,6),
				
				kpiGoal_code:'${goal_code}',
				kpiHos_id:'${hos_id}',
				kpiDept : liger.get("listbox1").getValue(),
				goal_acct_year_from:$("#goal_acct_year_from").val(),
				goal_acct_year_to:$("#goal_acct_year_to").val(),
				
				kpi_code : kpi_code.getValue(),
				ratio : ratio.getValue(),
				goal_value : goal_value.getValue(),
				grade_meth_code : grade_meth_code.getValue(),
				method_code : method_code.getValue(),
				dept_led:dept_led.getValue(),
				dept_full:dept_full.getValue()

			};
		ajaxJsonObjectByUrl("saveIntroduceDeptScheme.do", formPara, function(responseData) {
			parent.query();
		});


    }
    
    </script>
	<style type="text/css">
        .middle input {
            display: block;width:30px; margin:2px;
        }
    </style>
    
<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

    <table cellpadding="0" cellspacing="0" class="l-table-edit" id = "table1">
	 	  <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">单 位 信 息：</td>
            <td align="left" class="l-table-edit-td" colspan="7"><input name="hos_id" type="text" id="hos_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td" style="padding-left: 20px;">目标名称：</td>
			<td align="left" class="l-table-edit-td" colspan="6"><input name="goal_code" type="text" id="goal_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
           </tr>
    </table>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" id = "table2">
           <tr>
            <td align="right" class="l-table-edit-td" style="padding-left: 20px;">源考核单元：</td>
			<td align="left" class="l-table-edit-td"  colspan="7"><input name="dept_no" type="text" id="dept_no" ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">考核期间：</td>
			<td align="left" class="l-table-edit-td" ><input name="year_month" type="text" id="year_month" ltype="text" validate="{required:true,maxlength:20}" class="Wdate"
					onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyyMM'})" /></td>
			<td align="left"></td>
			
           </tr>
           
    </table>
    <div style="width: 87%;padding: 10px;margin: 10px 13px;border: 1px #aecaf0 solid;">
    	<table cellpadding="0" cellspacing="0" class="l-table-edit" id = "table3">
	    	<tr>
	    		<td align="right" class="l-table-edit-td" style="padding-left: 2px;">目标考核单元：</td>
	    		<td align="right" class="l-table-edit-td" style="padding-left: 216px;">考核期间：</td>
				<td align="left" class="l-table-edit-td" >
					<input name="goal_acct_year_from" type="text" id="goal_acct_year_from" ltype="text" validate="{required:true,maxlength:20}" class="Wdate"
						onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyyMM'})" /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td">到：</td>
				<td align="left" class="l-table-edit-td"  >
					<input name="goal_acct_year_to" type="text" id="goal_acct_year_to" ltype="text" validate="{required:true,maxlength:20}" class="Wdate"
						onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyyMM'})"/></td>
				<td align="left"></td>
	    	</tr>
	    </table>
	    <table cellpadding="0" cellspacing="0" class="l-table-edit" id = "table4">
	         
	           
	            <!--  td align="right" class="l-table-edit-td" style="padding-left: 20px;">目标考核单元：</td>
				<td align="left" class="l-table-edit-td"><input name="dept_noM" type="text" id="dept_noM" ltype="text" validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
	           </tr>-->
	     <tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 2px;">
				<div id="listbox1"></div>
			</td>
	     </tr> 
	    </table>
    </div>
    
    

    <table cellpadding="0" cellspacing="0" style="margin-top: 10px;margin-left: 20px;" class="l-table-edit" id = "table4">
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
	            <td align="right" class="l-table-edit-td"><input type="checkbox" name="chbox" id = "dept_led" checked="checked"/></td>
	            <td align="left" >指示灯</td>
	            <td align="left" ></td>
	            <td align="left" class="l-table-edit-td"  style="padding-left:10px;"></td>
	            <td align="left" class="l-table-edit-td"  style="padding-left:10px;"></td>
	            <td align="right" class="l-table-edit-td"><input type="checkbox" name="chbox" id = "dept_full" checked="checked"/></td>
	            <td align="left" >满分标准</td>
	            <td align="left" ></td>
            </tr>
    </table>
</body>
</html>
