<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<%=path %>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<script src="<%=path %>/lib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script>
<script src="<%=path %>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path %>/lib/jquery-validation/jquery.validate.min.js" type="text/javascript"></script>
<script src="<%=path %>/lib/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
<script src="<%=path %>/lib/jquery-validation/messages_cn.js" type="text/javascript"></script>
<script src="<%=path %>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>
<script type="text/javascript">
     var is_auto=${sessionScope.hos_rules['HOS_FAC'].is_auto };
     var dataFormat;
     var is_mat_manager;
     var is_med_manager;
     var is_ass_manager;
     
     $(function (){
        loadDict();//加载下拉框
        loadForm();
        if(is_auto=="1"){
        	manager=$("#fac_code").ligerTextBox({disabled: true});
        	$("#fac_code").ligerGetTextBoxManager().setValue('自动生成');
        	manager.setDisabled();
        }
        
        is_mat_manager = $("#is_mat").ligerCheckBox();is_mat_manager.setValue(false);
        is_med_manager = $("#is_med").ligerCheckBox();is_med_manager.setValue(false);
        is_ass_manager = $("#is_ass").ligerCheckBox();is_ass_manager.setValue(false);
        is_sup_manager = $("#is_sup").ligerCheckBox();is_sup_manager.setValue(false);
        
     });  
     
     function  save(){
    	 var fac_type =  liger.get("type_code").getValue();
    	 if(fac_type == null || fac_type == '' || fac_type == undefined ){
    		 $.ligerDialog.error("生产厂商类别不能为空");
    		 return ; 
    	 }
    	mat=$("#is_mat").is(":checked") ? 1 : 0;
    	med=$("#is_med").is(":checked") ? 1 : 0;
    	ass=$("#is_ass").is(":checked") ? 1 : 0;
    	sup=$("#is_sup").is(":checked") ? 1 : 0 ;		    
    	if(mat=='0' && med=='0' && ass=='0' && sup=='0'){
    		 $.ligerDialog.error("系统模块不能为空");
    		 return ; 
    	}
 		if(is_auto=="0"){
    		 if($("#fac_code").val()==""){
        		 $.ligerDialog.error("供应商编码不能为空");
        		 return false;
        	 }else{
        		 var formPara={
        		 	fac_id:'',
        		    fac_code:$("#fac_code").val(),
        		    type_code:liger.get("type_code").getValue(),
        		    fac_name:$("#fac_name").val(), 
        		    fac_sort:$("#fac_sort").val(),
        		    /*   is_stop:$("#is_stop").val(),  */
        		    note:$("#note").val(),
        		    is_auto:is_auto,
        		    is_mat:$("#is_mat").is(":checked") ? 1 : 0,
        		    is_med:$("#is_med").is(":checked") ? 1 : 0,
        		    is_ass:$("#is_ass").is(":checked") ? 1 : 0,
                    is_sup:$("#is_sup").is(":checked") ? 1 : 0 
        		};
        		        
        		ajaxJsonObjectByUrl("assaddFac.do",formPara,function(responseData){
        			if(responseData.state=="true"){
        				$("input[name='fac_code']").val('');
        				$("input[name='type_code']").val('');
        				$("input[name='fac_name']").val('');
        				//$("input[name='fac_sort']").val('');
        				/*  	 $("input[name='is_stop']").val('');  */
        				 $("#note").val('');
        		         parent.query();
        		     }
        		});
        	 }
    	 }else{
    		 var formPara={
    		 	fac_id:'',
    		    fac_code:$("#fac_code").val(),
    		    type_code:liger.get("type_code").getValue(),
    		    fac_name:$("#fac_name").val(),
    		    fac_sort:$("#fac_sort").val(),
    		    /*   is_stop:$("#is_stop").val(),  */
    		    note:$("#note").val(),
    		    is_auto : is_auto,
    		    is_mat : $("#is_mat").is(":checked") ? 1 : 0,
            	is_med : $("#is_med").is(":checked") ? 1 : 0,
            	is_ass:$("#is_ass").is(":checked") ? 1 : 0 ,
                 is_sup:$("#is_sup").is(":checked") ? 1 : 0 
    		};
    		        
    		ajaxJsonObjectByUrl("assaddFac.do",formPara,function(responseData){
    			if(responseData.state=="true"){
    				$("input[name='fac_code']").val('');
    				$("input[name='type_code']").val('');
    				$("input[name='fac_name']").val('');
    				//$("input[name='fac_sort']").val('');
    				/* 	 $("input[name='is_stop']").val('');  */
    				$("#note").val('');
    		        parent.query();
    		     }
    		 });
    	 }
    	 
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
   
    function saveFac(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
            //字典下拉框
    	autocomplete("#type_code","../queryFacTypeDict.do?isCheck=false","id","text",true,true,'',true);
    	$("#fac_name").ligerTextBox({width:435});
    	$("#fac_code").ligerTextBox({width:180});
    	$("#fac_sort").ligerTextBox({width:180});
    	$("#type_code").ligerTextBox({width:180});
    	
    	$("#fac_sort").ligerTextBox({width:180,disabled:true});
     } 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" border="0">

            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>生产厂商编码<font color="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td"><input name="fac_code" type="text" id="fac_code"  ltype="text"  /></td>
                <td align="left"></td>
                
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>排序号:</b></td>
                <td align="left" class="l-table-edit-td" colspan="2"><input name="fac_sort" type="text" id="fac_sort" value="系统生成" disabled="disabled" ltype="text" validate="{required:true,digits:true , maxlength:20}" /></td>
               
            </tr> 
            
            <tr>
            	<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>生产厂商名称<font color="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td" colspan="5"><input name="fac_name" type="text" id="fac_name" ltype="text" validate="{required:true}"/></td>
                
                
            </tr>
            <tr>
            	
                <td align="left" class="l-table-edit-td"  style="padding-left:20px;"><b>生产厂商类别<font color="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td"><input name="type_code" type="text" id="type_code" ltype="text" validate="{maxlength:20}" /></td>
                <td align="left"></td>
                
               <td align="right" class="l-table-edit-td" colspan="2">
                	<input name="is_ass"  id ="is_ass" type="checkbox"  checked="checked"/>固定资产&nbsp;&nbsp;
                	<!-- <input name="is_mat"  id ="is_mat" type="hidden" /> -->
                <!-- 	<input name="is_med"  id ="is_med" type="hidden" />
                	<input name="is_sup"  id ="is_sup" type="hidden" /> -->
                </td>
               
            </tr>
            
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>备注:</b></td>
                <td align="left" class="l-table-edit-td" colspan="5">
                	<textarea rows="3" cols="70" id="note" name="note" ltype="text" validate="{maxlength:20}"></textarea>
                </td>
                
            </tr> 

        </table>
    </form>
   
    </body>
</html>
