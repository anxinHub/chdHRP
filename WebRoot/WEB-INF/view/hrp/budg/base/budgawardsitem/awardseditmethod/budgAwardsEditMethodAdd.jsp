<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html style="overflow:hidden;">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
    <script type="text/javascript">
     var dataFormat;
     $(function (){
         loadDict()//加载下拉框
        loadForm();
		
         $("#edit_method").change(function(){
        	 
        	 var edit_method = liger.get("edit_method").getValue();
        	 
        	 //与编制方法下拉框联动查询   取值方法下拉框 
        	 autocomplete("#get_way","../../../queryBudgGetWay.do?isCheck=false&edit_method="+edit_method,"id","text",true,true,'',false,'',180);
        	 
         });
         
         $("#get_way").change(function(){
          	if(liger.get("get_way").getValue() !='02' && liger.get("get_way").getValue() !='03' ){
          		$("#fun_id").ligerTextBox({disabled:true,cancelable: false});
          		$("#formula_id").ligerTextBox({disabled:true,cancelable: false});
          		liger.get("fun_id").setValue("");
          		liger.get("formula_id").setValue("");
          	}
          	if(liger.get("get_way").getValue()=='02'){
          		$("#fun_id").ligerTextBox({disabled:false,cancelable: true});
          		$("#formula_id").ligerTextBox({disabled:true,cancelable: false});
          	}
          	if(liger.get("get_way").getValue()=='03'){
          		$("#formula_id").ligerTextBox({disabled:false,cancelable: true});
          		$("#fun_id").ligerTextBox({disabled:true,cancelable: false});
          	}
         })
        
     });  
     
     function  save(){
        var formPara={
        		
           awards_item_code:liger.get("awards_item_code").getValue(),
            
           edit_method:liger.get("edit_method").getValue(),
            
           get_way:liger.get("get_way").getValue(),
            
           formula_id:liger.get("formula_id").getValue(),
            
           fun_id:liger.get("fun_id").getValue()
            
         };
        
        ajaxJsonObjectByUrl("addBudgAwardsEditMethod.do?isCheck=false",formPara,function(responseData){
            
            if(responseData.state=="true"){
				 $("input[name='awards_item_code']").val('');
				 $("input[name='edit_method']").val('');
				 $("input[name='get_way']").val('');
				 $("input[name='formula_id']").val('');
				 $("input[name='fun_id']").val('');
				 $("#formula_id").ligerTextBox({disabled:true});
			     $("#fun_id").ligerTextBox({disabled:true});
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
	    //$("form").ligerForm();
	}       
   
    function saveBudgAwardsEditMethod(){
        if($("form").valid()){
            save();
        }
   }
   function loadDict(){
       //字典下拉框
       
       //奖金项目下拉框
	   autocomplete("#awards_item_code","../../../queryBudgAwardsItem.do?isCheck=false","id","text",true,true,'',false,'',180); 
       
	   //编制方法下拉框
	   	autocomplete("#edit_method","../../../queryBudgEditMethod.do?isCheck=false","id","text",true,true,'',false,'',180);
       
	   //初始化 取值方法下拉框 （编制方法下拉框 与 取值方法下拉框 联动）
	   	$("#get_way").ligerComboBox({});
	   //取值方法下拉框
	   //	autocomplete("#get_way","../../../queryBudgGetWay.do?isCheck=false","id","text",true,true);
	       
	   	//计算公式下拉框
	   	autocomplete("#formula_id","../../../queryBudgFormula.do?isCheck=false","id","text",true,true,'',false,'',180);
	   	  
	   	//取值函数下拉框
	   	autocomplete("#fun_id","../../../queryBudgFun.do?isCheck=false","id","text",true,true,'',false,'',180);
       
	   	$("#awards_item_code").ligerTextBox({width:180});
    	$("#edit_method").ligerTextBox({width:180});
    	$("#get_way").ligerTextBox({width:180});
	   	$("#formula_id").ligerTextBox({width:180,disabled:true});
    	$("#fun_id").ligerTextBox({width:180,disabled:true});
     	
    } 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
		
        <tr>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>奖金项目<font color="red">*</font>:</b></td>
            <td align="left" class="l-table-edit-td"><input name="awards_item_code" type="text" id="awards_item_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">编制方法：</td>
            <td align="left" class="l-table-edit-td"><input name="edit_method" type="text" id="edit_method" ltype="text" validate="{maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">取值方法：</td>
            <td align="left" class="l-table-edit-td"><input name="get_way" type="text" id="get_way" ltype="text" validate="{maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">计算公式：</td>
            <td align="left" class="l-table-edit-td"><input name="formula_id" type="text" id="formula_id" ltype="text" validate="{maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">取值函数：</td>
            <td align="left" class="l-table-edit-td"><input name="fun_id" type="text" id="fun_id" ltype="text" validate="{maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
    </table>
    </form>
   
    </body>
</html>
