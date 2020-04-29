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
     	
        loadDict();//加载下拉框
        
        loadForm();
        
     });  
     
     function  save(){
    	 
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
            
           bed_use_rate:$("#bed_use_rate").val(),
            
           bed_use_day_num:$("#bed_use_day_num").val(),
           bed_out_day_num:$("#bed_out_day_num").val()
            
         };
        
        ajaxJsonObjectByUrl("addCostInhosWork.do",formPara,function(responseData){
            
            if(responseData.state=="true"){

            	$("input[name='year_month']").val('');
				 $("input[name='dept_id']").val('');
				 $("input[name='dept_no']").val('');
				 $("input[name='patient_type_code']").val('');
				 /*
				 	2016/11/3 lxj 
				 	保存完之后重新赋默认值
				 */
				 $("input[name='in_hos_num']").val('0');
				 $("input[name='out_hos_num']").val('0');
				 $("input[name='set_bed_num']").val('0');
				 $("input[name='open_bed_num']").val('0');
				 $("input[name='bed_use_rate']").val('1');
				 $("input[name='bed_use_day_num']").val('0');
				 $("input[name='bed_out_day_num']").val('0');
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
 function bedUseOutCalculation() {
 	if($("#bed_use_day_num").val() <= $("#open_bed_num").val()){
		 $("#bed_out_day_num").val(0);
	 }else{
		 var value = $("#bed_use_day_num").val()-$("#open_bed_num").val();
	     $("#bed_out_day_num").val(value);
	 }
 }
 function bedUseRateCalculation() {
	 if($("#open_bed_num").val()==0){
		 $("#bed_use_rate").val(0);
	 }else{
		 var value = division($("#bed_use_day_num").val(),$("#open_bed_num").val(),2);
	     $("#bed_use_rate").val(formatNumber(value, 2, 1));
	 }
	
 }
 
    function saveCostInhosWork(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
            //字典下拉框
             var param = {
     			  type_code:"('01')",
     			  natur_code:"('02')"
              };
    	autocomplete("#dept_id","../queryDeptDictNoLast.do?isCheck=false","id","text",true,true,param);
    	autocomplete("#patient_type_code","../queryCostPatientTypeDict.do?isCheck=false","id","text",true,true);
    	$("#bed_use_rate").ligerTextBox({disabled: true});
    	$("#bed_out_day_num").ligerTextBox({disabled: true});
     } 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >

            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">统计年月：</td>
                <td align="left" class="l-table-edit-td"><input name="year_month" type="text" id="year_month" ltype="text" validate="{required:true,maxlength:20}" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室：</td>
                <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">患者类别：</td>
                <td align="left" class="l-table-edit-td"><input name="patient_type_code" type="text" id="patient_type_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">入院人次：</td>
                <td align="left" class="l-table-edit-td"><input name="in_hos_num" type="text" id="in_hos_num" ltype="text" value="0" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">出院人次：</td>
                <td align="left" class="l-table-edit-td"><input name="out_hos_num" type="text" id="out_hos_num" ltype="text" value="0" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">编制床位数：</td>
                <td align="left" class="l-table-edit-td"><input name="set_bed_num"  type="text" id="set_bed_num"  ltype="text" value="0" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">实际开放床位数：</td>
                <td align="left" class="l-table-edit-td"><input name="open_bed_num" type="text" id="open_bed_num" value="0" onBlur="bedUseRateCalculation();bedUseOutCalculation()"  ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">实际占用床日数：</td>
                <td align="left" class="l-table-edit-td"><input name="bed_use_day_num" type="text" id="bed_use_day_num" value="0"  onBlur="bedUseRateCalculation();bedUseOutCalculation()" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">超出床日数：</td>
                <td align="left" class="l-table-edit-td"><input name="bed_out_day_num" type="text" id="bed_out_day_num" value="0"    ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
			<tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">床位使用率：</td>
                <td align="left" class="l-table-edit-td"><input name="bed_use_rate" type="text" id="bed_use_rate" value="1" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
        </table>
    </form>
   
    </body>
</html>
