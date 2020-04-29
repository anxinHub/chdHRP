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
        
        $("#emp_kind_code").ligerTextBox({ disabled: true});
        
        $("#bonus_item_code").ligerTextBox({ disabled: true});
        
    });  
     
    function save(){
        var formPara={
				
				
        		 id:"${id}",	
        		 acc_year:$("#year_month").val().substring(0,4),
                 
                 acc_month:$("#year_month").val().substring(4,6),
        emp_kind_code:liger.get("emp_kind_code").getValue(),
    	bonus_item_code:liger.get("bonus_item_code").getValue(),
        cost_item_id:liger.get("cost_item_id").getValue().split(".")[0],
        cost_item_no:liger.get("cost_item_id").getValue().split(".")[1]
        };
        ajaxJsonObjectByUrl("updateCostBonusCostRela.do",formPara,function(responseData){
            
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
   
    function saveCostBonusCostRela(){
        if($("form").valid()){
            save();
        }
    }
    function loadDict(){
        //字典下拉框
    	autocomplete("#bonus_item_code","../queryBonusItemArrt.do?isCheck=false","id","text",true,true);
        
    	autocomplete("#emp_kind_code","../queryEmpTypeArrt.do?isCheck=false","id","text",true,true);
    	
    	autocomplete("#cost_item_id","../queryItemDictNoLast.do?isCheck=false","id","text",true,true,'', false, '${cost_item_id}.${cost_item_no}');
    	
    	liger.get("bonus_item_code").setValue('${bonus_item_code}');	
    	
		liger.get("bonus_item_code").setText('${bonus_item_name}');
		
		liger.get("emp_kind_code").setValue('${emp_kind_code}');	
		
		liger.get("emp_kind_code").setText('${emp_kind_name}');

     }   
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">统计年月：</td>
                <td align="left" class="l-table-edit-td"><input disabled="disabled" name="year_month" type="text" id="year_month" ltype="text"  value="${year_month}" validate="{required:true,maxlength:20}" class="Wdate"  onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">职工分类：</td>
                <td align="left" class="l-table-edit-td"><input disabled="disabled" name="emp_kind_code" type="text" id="emp_kind_code" ltype="text"  value="${emp_kind_code}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">奖金项编码：</td>
                <td align="left" class="l-table-edit-td"><input disabled="disabled" name="bonus_item_code" type="text" id="bonus_item_code" ltype="text"  value="${bonus_item_code}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
             <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">成本项目：</td>
                <td align="left" class="l-table-edit-td"><input name="cost_item_id" type="text" id="cost_item_id" ltype="text"  value="${cost_item_id}" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr> 
        </table>
    </form>
    </body>
</html>
