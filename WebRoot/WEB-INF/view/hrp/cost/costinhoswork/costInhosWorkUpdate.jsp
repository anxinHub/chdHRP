<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
	    
    $(function (){
    	
        loadDict();
        
        loadForm();
        
        $("#year_month").ligerTextBox({ disabled: true});
        
 		$("#dept_id").ligerTextBox({ disabled: true});
        
        $("#patient_type_code").ligerTextBox({ disabled: true});
        
    });  
     
    function save(){
    	var use_rate=$("#bed_use_rate").val();
    	
	var dept_dict = liger.get("dept_id").getValue();
    	
        var formPara={

        		acc_year:$("#year_month").val().substring(0,4),
                
                acc_month:$("#year_month").val().substring(4,6),
        
        dept_id:dept_dict.split(".")[0],
        
        dept_no:dept_dict.split(".")[1],
        
        patient_type_code:liger.get("patient_type_code").getValue(),
        
        in_hos_num:$("#in_hos_num").val(),
        out_hos_num:$("#out_hos_num").val(),
        set_bed_num:$("#set_bed_num").val(),
        open_bed_num:$("#open_bed_num").val(),
        bed_use_rate:use_rate.split("%")[0],
        bed_use_day_num:$("#bed_use_day_num").val(),
        bed_out_day_num:$("#bed_out_day_num").val()
        };
        ajaxJsonObjectByUrl("updateCostInhosWork.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
                parent.query();
            }
        });
    }
     
    function loadForm(){
    
    $.metadata.setType("attr", "validate");
     var v = $("form").validate({
         errorPlacement: function (lable, element)
         {
             if (element.hasClass("l-textarea"))
             {
                 element.ligerTip({ content: lable.html(), target: element[0] }); 
             }
             else if (element.hasClass("l-text-field"))
             {
                 element.parent().ligerTip({ content: lable.html(), target: element[0] });
             }
             else
             {
                 lable.appendTo(element.parents("td:first").next("td"));
             }
         },
         success: function (lable)
         {
             lable.ligerHideTip();
             lable.remove();
         },
         submitHandler: function ()
         {
             $("form .l-text,.l-textarea").ligerHideTip();
         }
     });
     $("form").ligerForm();
    }       
   
    function saveCostInhosWork(){
        if($("form").valid()){
            save();
        }
    }
    function loadDict(){
    	var param = {
   			  type_code:"('01')",
   			  natur_code:"('02')"
            };
        //字典下拉框
		autocomplete("#dept_id","../queryDeptDictNoLast.do?isCheck=false","id","text",true,true,param);
        
    	autocomplete("#patient_type_code","../queryCostPatientTypeDict.do?isCheck=false","id","text",true,true);
    	
    	$("#bed_use_rate").ligerTextBox({disabled: true});
    	
    	$("#bed_out_day_num").ligerTextBox({disabled: true});
    	
    	liger.get("dept_id").setValue('${dept_id}.${dept_no}');
    	
	    liger.get("dept_id").setText('${dept_name}');
	    
		liger.get("patient_type_code").setValue("${patient_type_code}");	
		
		liger.get("patient_type_code").setText("${patient_name}");

		//$("#bed_use_rate").val(formatNumber('${bed_use_rate}', 2, 1));
		
        bedUseRateCalculation();
        bedUseOutCalculation();
     }   
    function bedUseRateCalculation() {
    	if($("#open_bed_num").val()==0){
   		 $("#bed_use_rate").val(0);
   	 }else{
   		 var value = division($("#bed_use_day_num").val(),$("#open_bed_num").val(),2);
   	     $("#bed_use_rate").val(formatNumber(value, 2, 1));
   	 }
    }
    function bedUseOutCalculation() {
    	if($("#bed_use_day_num").val() <= $("#open_bed_num").val()){
   		 $("#bed_out_day_num").val(0);
   	 }else{
   		 var value = $("#bed_use_day_num").val()-$("#open_bed_num").val();
   	     $("#bed_out_day_num").val(value);
   	 }
    }
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">统计年月：</td>
                <td align="left" class="l-table-edit-td"><input disabled="disabled" name="year_month" type="text" id="year_month"  disabled="disabled"  ltype="text"  value="${year_month}" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室：</td>
                <td align="left" class="l-table-edit-td"><input disabled="disabled" name="dept_id" type="text" id="dept_id" ltype="text"  value="${dept_id}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">患者类别：</td>
                <td align="left" class="l-table-edit-td"><input disabled="disabled" name="patient_type_code" type="text" id="patient_type_code" ltype="text"  value="${patient_type_code}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">入院人次：</td>
                <td align="left" class="l-table-edit-td"><input name="in_hos_num" type="text" id="in_hos_num" ltype="text"  value="${in_hos_num}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">出院人次：</td>
                <td align="left" class="l-table-edit-td"><input name="out_hos_num" type="text" id="out_hos_num" ltype="text"  value="${out_hos_num}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">编制床位数：</td>
                <td align="left" class="l-table-edit-td"><input name="set_bed_num" type="text" id="set_bed_num" ltype="text"  value="${set_bed_num}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">实际开放床位数：</td>
                <td align="left" class="l-table-edit-td"><input name="open_bed_num" type="text" id="open_bed_num" ltype="text"  value="${open_bed_num}" onBlur="bedUseRateCalculation();bedUseOutCalculation()" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
             <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">实际占用床日数：</td>
                <td align="left" class="l-table-edit-td"><input name="bed_use_day_num" type="text" id="bed_use_day_num" ltype="text"  value="${bed_use_day_num}" onBlur="bedUseRateCalculation();bedUseOutCalculation()" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
             <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">超出床日数：</td>
                <td align="left" class="l-table-edit-td"><input name="bed_out_day_num" type="text" id="bed_out_day_num" ltype="text"  onBlur="bedUseOutCalculation()"   validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">床位使用率：</td>
                <td align="left" class="l-table-edit-td"><input name="bed_use_rate" type="text" id="bed_use_rate" ltype="text"  onBlur="bedUseRateCalculation()" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
           
        </table>
    </form>
    </body>
</html>
