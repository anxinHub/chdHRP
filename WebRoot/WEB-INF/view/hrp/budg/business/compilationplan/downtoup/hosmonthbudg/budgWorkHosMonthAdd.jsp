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
    <script src="<%=path%>/lib/hrp/budg/budg.js"	type="text/javascript"></script>
    <script type="text/javascript">
     var dataFormat;
     var budg_year ;
     $(function (){
         loadDict()//加载下拉框
         loadForm();
         
         $("#year").change(function(){
        	 
        	 budg_year = liger.get("year").getValue();
   			
   			 liger.get("index_code").setValue();
  			
  			 liger.get("index_code").setText();
         	 
         	 //预算指标下拉框
             autocomplete("#index_code","../../../../qureyBudgIndexFromPlan.do?isCheck=false&budg_level=02&budg_year="+budg_year,"id","text",true,true,"",true);
          
         })
        	
         
     });  
     
     function  save(){
        var formPara={
            
           year:liger.get("year").getValue(),
            
           index_code:liger.get("index_code").getValue(),
           
           month:liger.get("month").getValue(),
            
           budg_value:$("#budg_value").val(),
            
           remark:$("#remark").val(),
            
           grow_rate:$("#grow_rate").val(),
            
           count_value:"",
           
           resolve_rate:"",
           
           last_month_carried:"",
            
           carried_next_month:""
            
         };
        
        ajaxJsonObjectByUrl("addBudgWorkHosMonthDown.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
				 $("input[name='year']").val('');
				 $("input[name='index_code']").val('');
				 $("input[name='month']").val('');
				 $("input[name='budg_value']").val('');
				 $("input[name='remark']").val('');
				 $("input[name='grow_rate']").val('');
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
   
    function saveBudgWorkHosMonth(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
            //字典下拉框
		autocomplete("#year","../../../../queryBudgYear.do?isCheck=false","id","text",true,true,"",true);
        
		//初始化 预算指标下拉框 （预算指标下拉框 与 预算年度下拉框 联动）
   		$("#index_code").ligerComboBox({});
            
        
        autoCompleteByData("#month", monthData.Rows, "id", "text", true, true,'',false,'',160);
        
   		$("#year").ligerTextBox({width:160});
    		
        $("#index_code").ligerTextBox({width:160});
         
        $("#month").ligerTextBox({width:160});
        
        $("#budg_value").ligerTextBox({width:160});
		
        $("#grow_rate").ligerTextBox({width:160});
         
        $("#remark").ligerTextBox({width:160});
     } 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
		
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">预算年度<span style="color:red">*</span>:</td>
            <td align="left" class="l-table-edit-td"><input name="year" type="text" id="year" ltype="text" validate="{required:true,maxlength:4}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">指标编码<span style="color:red">*</span>:</td>
            <td align="left" class="l-table-edit-td"><input name="index_code" type="text" id="index_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">月份<span style="color:red">*</span>:</td>
            <td align="left" class="l-table-edit-td"><input name="month" type="text" id="month" ltype="text" validate="{required:true,maxlength:4}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">预算值<span style="color:red">*</span>:</td>
            <td align="left" class="l-table-edit-td"><input name="budg_value" type="text" id="budg_value" ltype="text" validate="{required:true,number:true,maxlength:18}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
       		<td align="right" class="l-table-edit-td"  style="padding-left:20px;">增长比例:</td>
            <td align="left" class="l-table-edit-td"><input name="grow_rate" type="text" id="grow_rate" ltype="text" validate="{number:true,maxlength:18}" /></td>
            <td align="left"></td>
           	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">说明:</td>
            <td align="left" class="l-table-edit-td"><input name="remark" type="text" id="remark" ltype="text" validate="{maxlength:40}" /></td>
            <td align="left"></td>
        </tr> 
    </table>
    </form>
   
    </body>
</html>
