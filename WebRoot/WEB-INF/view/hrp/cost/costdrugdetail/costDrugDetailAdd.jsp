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
          
          var cost_item = liger.get("cost_item_id").getValue();
     	 
         var formPara={
        		 
        	acc_year:$("#year_month").val().substring(0,4),
        	
        	acc_month:$("#year_month").val().substring(4,6),
             
            dept_id:dept_dict.split(".")[0],
             
            dept_no:dept_dict.split(".")[1],
             
            cost_item_id:cost_item.split(".")[0],
             
            cost_item_no:cost_item.split(".")[1],
            
           amount:$("#amount").val()
            
           //source_id:liger.get("source").getValue()
            
         };
        
        ajaxJsonObjectByUrl("addCostDrugDetail.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
				 $("input[name='year_month']").val('');
				 $("input[name='dept_id']").val('');
				 $("input[name='dept_no']").val('');
				 $("input[name='cost_item_id']").val('');
				 $("input[name='cost_item_no']").val('');
				 $("input[name='amount']").val('');
				// $("input[name='source']").val('');
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
   
    function saveCostDrugDetail(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
    	
    	 var dept_para = {type_code : "('01','02','03','04')",
    			          is_last:'1'
    	 };
            //字典下拉框
    	autocomplete("#dept_id","../queryDeptDictNoLast.do?isCheck=false","id","text",true,true,dept_para);
    	autocomplete("#cost_item_id","../queryItemDictNoLast.do?isCheck=false","id","text",true,true); 
    	//autocomplete("#source","../querySourceArrt.do?isCheck=false","id","text",true,true); 
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
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">成本项目ID：</td>
                <td align="left" class="l-table-edit-td"><input name="cost_item_id" type="text" id="cost_item_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">金额：</td>
                <td align="left" class="l-table-edit-td"><input name="amount" type="text" id="amount" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
<!--             <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资金来源：</td>
                <td align="left" class="l-table-edit-td"><input name="source" type="text" id="source" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr>  -->

        </table>
    </form>
   
    </body>
</html>
