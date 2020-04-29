<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script src="<%=path%>/lib/hrp/budg/budg.js"	type="text/javascript"></script>
<script type="text/javascript">
    $(function (){
       loadDict();//加载下拉框
       loadForm(); 
    });  
     
     function  save(){
        var formPara={
           insurance_code:$("#insurance_code").val(),
           insurance_name:$("#insurance_name").val(),
           insurance_nature : liger.get("insurance_nature").getValue() ,
           is_stop : liger.get("is_stop").getValue() 
        };
        
        ajaxJsonObjectByUrl("updateBudgYBType.do?isCheck=true",formPara,function(responseData){
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
  	    // $("form").ligerForm();
  	 }       
     
      function updateBudgYBType(){
          if($("form").valid()){
              save();
          }
     }
  
    function loadDict(){
      	//字典下拉框    
      	//医保类型性质
      	autocomplete("#insurance_nature","../../../queryBudgYBTypeNature.do?isCheck=false","id","text",true,true);
      	
		liger.get("insurance_nature").setValue('${budgYBTypeMap.insurance_nature}');
        liger.get("insurance_nature").setText('${budgYBTypeMap.insurance_nature_name}');
         
    	autoCompleteByData("#is_stop", yes_or_no.Rows, "id", "text", true, true, "",false, '${budgYBTypeMap.is_stop}');
    	$("#insurance_code").ligerTextBox({width:160,disabled:true});
    	$("#insurance_name").ligerTextBox({width:160});
    	$("#insurance_nature").ligerTextBox({width:160});
        $("#is_stop").ligerTextBox({width:160});
        
    	$("#save").ligerButton({click: updateBudgYBType, width:90});
        
		$("#close").ligerButton({click: this_close, width:90});
    } 
    
    //关闭
    function this_close(){
    	frameElement.dialog.close();
    }
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red">*</font><b>医保类型编码:</b></td>
                <td align="left" class="l-table-edit-td">
                	<input name="insurance_code" type="text" id="insurance_code" ltype="text" value="${budgYBTypeMap.insurance_code}"  disabled="true" validate="{required:true,maxlength:20}" />
                </td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red">*</font><b>医保类型名称:</td>
                <td align="left" class="l-table-edit-td">
                	<input name="insurance_name" type="text" id="insurance_name" ltype="text" value="${budgYBTypeMap.insurance_name}" validate="{required:true,maxlength:20}" />
                </td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red">*</font><b>医保类型性质:</td>
                <td align="left" class="l-table-edit-td">
                	<input name="insurance_nature" type="text" id="insurance_nature" ltype="text" validate="{required:true,maxlength:20}" />
                </td>
                <td align="left"></td>
            </tr>  
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red">*</font><b>是否停用:</b></td>
                <td align="left" class="l-table-edit-td">
					<input name="is_stop" type="text" id="is_stop" ltype="text" validate="{required:true,maxlength:20}"/>
                </td>
                <td align="left"></td>
            </tr> 
        </table>
    </form>
   	<div style="width: 100%; height: 100%;">
		<div id="maingrid"></div>
		<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%"  style="margin-top: 5px;">
			<tr>	
				<td align="center" class="l-table-edit-td" >
					<button id ="save" accessKey="B"><b>保存（<u>B</u>）</b></button>
					&nbsp;&nbsp;
					<button id ="close" accessKey="C"><b>关闭（<u>C</u>）</b></button>
				</td>
			</tr>
		</table>
	</div>
    </body>
</html>
