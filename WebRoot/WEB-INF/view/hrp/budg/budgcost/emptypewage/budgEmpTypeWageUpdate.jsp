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
        
     });  
     
     function  save(){
        var formPara={
        		
           year:$("#year").val().substr(0,4),
            
           month:$("#year").val().substr(4,2),
            
           dept_id:'${dept_id}',
            
           emp_id:'${emp_id}',
            
           emp_type_code:'${emp_type_code}',
            
           wage_item_code:'${wage_item_code}',
            
           amount:$("#amount").val()
            
         };
        
        ajaxJsonObjectByUrl("updateBudgEmpWageTotal.do?isCheck=false",formPara,function(responseData){
            
           /*  if(responseData.state=="true"){
				 $("input[name='year']").val('');
				 $("input[name='month']").val('');
				 $("input[name='dept_id']").val('');
				 $("input[name='emp_id']").val('');
				 $("input[name='emp_type_code']").val('');
				 $("input[name='wage_item_code']").val('');
				 $("input[name='amount']").val(''); */
                parent.query();
          //  }
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
   
    function saveBudgEmpWageTotal(){
        if($("form").valid()){
            save();
        }
    }
    
    function loadDict(){
        //字典下拉框
    	/* /* //预算年度下拉框
        autocompleteAsync("#year","../../queryBudgYear.do?isCheck=false","id","text",true,true,"",true);
        year = liger.get("year").getValue();
       	//预算月份下拉框
        autocomplete("#month","../../queryBudgMonth.do?isCheck=false","id","text",true,true,"",false,'','177px');
      
        //预算科室下拉框
        autocomplete("#dept_id","../../queryBudgHosDept.do?isCheck=false","id","text",true,true,'',false,'${dept_id}','177px');
    	
        //预算职工类别下拉框
        autocomplete("#emp_type_code","../../queryBudgEmpType.do?isCheck=false","id","text",true,true,'',false,'${emp_type_code}','177px')//,'','160px');
    	
        //预算职工名称下拉框
        autocomplete("#emp_id","../../queryBudgEmpName.do?isCheck=false","id","text",true,true,'',false,'${emp_id}','177px');
        
        //预算工资项下拉框
        autocomplete("#wage_item_code","../../queryBudgWageItem.do?isCheck=false","id","text",true,true,'',false,'${wage_item_code}','177px');
    	
       	 */
   		$("#year").ligerTextBox({width:160,disabled:true});
        $("#dept_id").ligerTextBox({width:160,disabled:true});
        $("#emp_id").ligerTextBox({width:160,disabled:true});
        $("#emp_type_code").ligerTextBox({width:180,disabled:true});   
        $("#wage_item_code").ligerTextBox({width:180,disabled:true});   
        $("#amount").ligerTextBox({width:160,number: true,precision:2});
        
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
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">统计年月：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="year" type="text" id="year" class="Wdate" value="${yearMonth}" disabled="disabled" ltype="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"
             validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室名称：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" value="${dept_name}" disabled="disabled" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">职工名称：</td>
            <td align="left" class="l-table-edit-td"><input name="emp_id" type="text" id="emp_id" ltype="text" value="${emp_name}" disabled="disabled" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">职称类别：</td>
            <td align="left" class="l-table-edit-td"><input name="emp_type_code" type="text" id="emp_type_code" value="${type_name}" disabled="disabled" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">工资项目：</td>
            <td align="left" class="l-table-edit-td"><input name="wage_item_code" type="text" id="wage_item_code" value="${wage_item_name}" disabled="disabled" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">金额：</td>
            <td align="left" class="l-table-edit-td"><input name="amount" type="text" id="amount" ltype="text" value="${amount}"  validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
    </table>
    </form>
   
    </body>
</html>
