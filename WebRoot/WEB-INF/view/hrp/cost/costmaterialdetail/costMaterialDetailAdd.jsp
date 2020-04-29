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
    	 
    	 var dept_code = liger.get("dept_code").getValue();
   	  
         var formPara={

            dept_code:dept_code.split(".")[2],
             
            acc_year:$("#year_month").val().substring(0,4),
        	
        	acc_month:$("#year_month").val().substring(4,6),
            
            mate_type_code:liger.get("mate_type_code").getValue(),
            
            source_id:liger.get("source_id").getValue(),
            
            sum_money:$("#sum_money").val()
            
         };
        
        ajaxJsonObjectByUrl("addCostMaterialDetail.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
				 $("input[name='year_month']").val('');
				 $("input[name='mate_type_code']").val('');
				 $("input[name='sum_money']").val('');
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
   
    function saveCostMaterialDetail(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
    	
    	  var dept_para = {type_code : "('01','02','03','04')",
    			  is_last:'1'};
    	  
    	
    	autocomplete("#dept_code","../queryDeptDictCodeLast.do?isCheck=false","id","text",true,true,dept_para);
    	autocomplete("#mate_type_code","../queryMateTypeArrt.do?isCheck=false","id","text",true,true);
    	autocomplete("#source_id","../querySourceArrt.do?isCheck=false","id","text",true,true);
    	  
     } 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >

            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">统计年月：</td>
                <td align="left" class="l-table-edit-td"><input name="year_month" type="text" id="year_month" ltype="text" validate="{required:true}"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室编码：</td>
                <td align="left" class="l-table-edit-td"><input name="dept_code" type="text" id="dept_code" ltype="text" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">材料分类编码：</td>
                <td align="left" class="l-table-edit-td"><input name="mate_type_code" type="text" id="mate_type_code" ltype="text"  validate="{required:true}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资金来源：</td>
                <td align="left" class="l-table-edit-td"><input name="source_id" type="text" id="source_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">费用：</td>
                <td align="left" class="l-table-edit-td"><input name="sum_money" type="text" id="sum_money" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
             
        </table>
    </form>
   
    </body>
</html>
