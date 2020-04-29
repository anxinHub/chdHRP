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
        
      //人员数量 发生改变时  重新计算 科室总人数
 		$("#doctor_num").change(function(){
 			$("#alldept_num").val( parseInt($("#doctor_num").val()) + parseInt($("#nurse_num").val()) 
 						+ parseInt($("#technician_num").val()) + parseInt($("#pharmacist_num").val())
 						+ parseInt($("#manager_num").val()) + parseInt($("#supportor_num").val())
 						+ parseInt($("#floater_num").val())+ parseInt($("#out_employ_num").val())
 						+ parseInt($("#clearner_num").val())) ;
 			
 		})
 		$("#nurse_num").change(function(){
 			$("#alldept_num").val( parseInt($("#doctor_num").val()) + parseInt($("#nurse_num").val()) 
 						+ parseInt($("#technician_num").val()) + parseInt($("#pharmacist_num").val())
 						+ parseInt($("#manager_num").val()) + parseInt($("#supportor_num").val())
 						+ parseInt($("#floater_num").val())+ parseInt($("#out_employ_num").val())
 						+ parseInt($("#clearner_num").val())) ;
 			
 		})
 		$("#technician_num").change(function(){
 			$("#alldept_num").val( parseInt($("#doctor_num").val()) + parseInt($("#nurse_num").val()) 
 						+ parseInt($("#technician_num").val()) + parseInt($("#pharmacist_num").val())
 						+ parseInt($("#manager_num").val()) + parseInt($("#supportor_num").val())
 						+ parseInt($("#floater_num").val())+ parseInt($("#out_employ_num").val())
 						+ parseInt($("#clearner_num").val())) ;
 			
 		})
 		$("#pharmacist_num").change(function(){
 			$("#alldept_num").val( parseInt($("#doctor_num").val()) + parseInt($("#nurse_num").val()) 
 						+ parseInt($("#technician_num").val()) + parseInt($("#pharmacist_num").val())
 						+ parseInt($("#manager_num").val()) + parseInt($("#supportor_num").val())
 						+ parseInt($("#floater_num").val())+ parseInt($("#out_employ_num").val())
 						+ parseInt($("#clearner_num").val())) ;
 			
 		})
 		$("#manager_num").change(function(){
 			$("#alldept_num").val( parseInt($("#doctor_num").val()) + parseInt($("#nurse_num").val()) 
 						+ parseInt($("#technician_num").val()) + parseInt($("#pharmacist_num").val())
 						+ parseInt($("#manager_num").val()) + parseInt($("#supportor_num").val())
 						+ parseInt($("#floater_num").val())+ parseInt($("#out_employ_num").val())
 						+ parseInt($("#clearner_num").val())) ;
 			
 		})
 		
 		$("#supportor_num").change(function(){
 			$("#alldept_num").val( parseInt($("#doctor_num").val()) + parseInt($("#nurse_num").val()) 
 						+ parseInt($("#technician_num").val()) + parseInt($("#pharmacist_num").val())
 						+ parseInt($("#manager_num").val()) + parseInt($("#supportor_num").val())
 						+ parseInt($("#floater_num").val())+ parseInt($("#out_employ_num").val())
 						+ parseInt($("#clearner_num").val())) ;
 			
 		})
 		
 		$("#floater_num").change(function(){
 			$("#alldept_num").val( parseInt($("#doctor_num").val()) + parseInt($("#nurse_num").val()) 
 						+ parseInt($("#technician_num").val()) + parseInt($("#pharmacist_num").val())
 						+ parseInt($("#manager_num").val()) + parseInt($("#supportor_num").val())
 						+ parseInt($("#floater_num").val())+ parseInt($("#out_employ_num").val())
 						+ parseInt($("#clearner_num").val())) ;
 			
 		})
 		
 		$("#out_employ_num").change(function(){
 			$("#alldept_num").val( parseInt($("#doctor_num").val()) + parseInt($("#nurse_num").val()) 
 						+ parseInt($("#technician_num").val()) + parseInt($("#pharmacist_num").val())
 						+ parseInt($("#manager_num").val()) + parseInt($("#supportor_num").val())
 						+ parseInt($("#floater_num").val())+ parseInt($("#out_employ_num").val())
 						+ parseInt($("#clearner_num").val())) ;
 			
 		})
 		$("#clearner_num").change(function(){
 			$("#alldept_num").val( parseInt($("#doctor_num").val()) + parseInt($("#nurse_num").val()) 
 						+ parseInt($("#technician_num").val()) + parseInt($("#pharmacist_num").val())
 						+ parseInt($("#manager_num").val()) + parseInt($("#supportor_num").val())
 						+ parseInt($("#floater_num").val())+ parseInt($("#out_employ_num").val())
 						+ parseInt($("#clearner_num").val())) ;
 			
 		})
     });  
     
     function  save(){
    	 
    	 var dept_dict = liger.get("dept_id").getValue();
   	  
         var formPara={

 				acc_year:$("#year_month").val().substring(0,4),
                 
                 acc_month:$("#year_month").val().substring(4,6),
             
 			dept_id:dept_dict.split(".")[0],
            
            dept_no:dept_dict.split(".")[1],
            
           doctor_num:$("#doctor_num").val(),
            
           nurse_num:$("#nurse_num").val(),
            
           technician_num:$("#technician_num").val(),
            
           pharmacist_num:$("#pharmacist_num").val(),
            
           manager_num:$("#manager_num").val(),
            
           supportor_num:$("#supportor_num").val(),
            
           floater_num:$("#floater_num").val(),
            
           out_employ_num:$("#out_employ_num").val(),
            
           clearner_num:$("#clearner_num").val(),
           
           alldept_num:$("#alldept_num").val()
            
         };
        
        ajaxJsonObjectByUrl("addCostDeptPeople.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
            	$("input[name='year_month']").val('');
				 $("input[name='dept_id']").val('');
				 $("input[name='dept_no']").val('');
				 /*
				 	2016/11/3 lxj
				 	保存成功之后重新赋默认值
				 */
				 $("input[name='doctor_num']").val('0');
				 $("input[name='nurse_num']").val('0');
				 $("input[name='technician_num']").val('0');
				 $("input[name='pharmacist_num']").val('0');
				 $("input[name='manager_num']").val('0');
				 $("input[name='supportor_num']").val('0');
				 $("input[name='floater_num']").val('0');
				 $("input[name='out_employ_num']").val('0');
				 $("input[name='clearner_num']").val('0');
				 $("input[name='alldept_num']").val('0');
				 
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
   
    function saveCostDeptPeople(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
            //字典下拉框
    	autocomplete("#dept_id","../queryDeptDictNoLast.do?isCheck=false","id","text",true,true);
            
    	 $("#alldept_num").ligerTextBox({ disabled: true});
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
                <td align="right" class="l-table-edit-td"  style="padding-left:200px;">科室：</td>
                <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">医生数量：</td>
                <td align="left" class="l-table-edit-td"><input name="doctor_num" type="text" id="doctor_num" value="0" ltype="text" validate="{required:true,digits:true,maxlength:20}" /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">护士数量：</td>
                <td align="left" class="l-table-edit-td"><input name="nurse_num" type="text" id="nurse_num" value="0" ltype="text" validate="{required:true,digits:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">技师数量：</td>
                <td align="left" class="l-table-edit-td"><input name="technician_num" type="text" id="technician_num" value="0" ltype="text" validate="{required:true,digits:true,maxlength:20}" /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">药师数量：</td>
                <td align="left" class="l-table-edit-td"><input name="pharmacist_num" type="text" id="pharmacist_num" value="0" ltype="text" validate="{required:true,digits:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">管理人员数量：</td>
                <td align="left" class="l-table-edit-td"><input name="manager_num" type="text" id="manager_num" value="0" ltype="text" validate="{required:true,digits:true,maxlength:20}" /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">后勤人员数量：</td>
                <td align="left" class="l-table-edit-td"><input name="supportor_num" type="text" id="supportor_num" value="0" ltype="text" validate="{required:true,digits:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">临时工数量：</td>
                <td align="left" class="l-table-edit-td"><input name="floater_num" type="text" id="floater_num" value="0" ltype="text" validate="{required:true,digits:true,maxlength:20}" /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">外聘人员数量：</td>
                <td align="left" class="l-table-edit-td"><input name="out_employ_num" type="text" id="out_employ_num" value="0" ltype="text" validate="{required:true,digits:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">保洁人员数量：</td>
                <td align="left" class="l-table-edit-td"><input name="clearner_num" type="text" id="clearner_num" value="0" ltype="text" validate="{required:true,digits:true,maxlength:20}" /></td>
                <td align="left"></td>
                
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室总数量：</td>
                <td align="left" class="l-table-edit-td"><input name="alldept_num" type="text" id="alldept_num" value="0" ltype="text" disabled="disabled" validate="{required:true,digits:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 

        </table>
    </form>
   
    </body>
</html>
