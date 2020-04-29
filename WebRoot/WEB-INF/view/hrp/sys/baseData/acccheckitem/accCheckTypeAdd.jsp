<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
     	
        loadDict();//加载下拉框
        
        loadForm();
        
     });  
     
     function  save(){
        var formPara={
           check_type_id:'',
            
           check_table:$("#check_table").val(),
            
           check_type_code:$("#check_type_code").val(),
           check_type_name:$("#check_type_name").val(),
            
           sort_code:$("#sort_code").val(),
            
           column_id:'CHECK_ITEM_ID', 
           column_code:'CHECK_ITEM_CODE',
           column_name:'CHECK_ITEM_NAME',
           is_change:0,
           is_stop:$("#is_stop").val(),
           is_sys:0,
           z_code:liger.get("z_code").getValue()
            
         };
        
        ajaxJsonObjectByUrl("addAccCheckType.do?isCheck=false",formPara,function(responseData){
            
            if(responseData.state=="true"){
				$("input[name='check_type_name']").val('');	
				$("input[name='check_type_code']").val('');	
                parent.loadTree();
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
   
    function saveAccCheckType(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
    	autocompleteObj({
    		id:"#z_code",
    		urlStr:"../queryAccBusiZCheck.do?isCheck=false",
    		valueField:"id",
    		textField:"text",
    		autocomplete:true,
    		highLight:true,
    		parmsStr:null,
    		defaultSelect:null,
    		initvalue:null,
    		initWidth:"180",initHeight:null,boxwidth:null,alwayShowInDown:null,selectEvent:null
    	});
           
     } 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >

            <tr style="display:none">
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">核算项表：</td>
                <td align="left" class="l-table-edit-td"><input name="check_table" type="text" id="check_table" disabled="disabled" value="ACC_CHECK_ITEM" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">核算类编码<font color="red">*</font>:</td>
                <td align="left" class="l-table-edit-td"><input name="check_type_code" type="text" id="check_type_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">核算类名称<font color="red">*</font>:</td>
                <td align="left" class="l-table-edit-td"><input name="check_type_name" type="text" id="check_type_name" ltype="text" validate="{required:true,maxlength:50}" /></td>
                <td align="left"></td>
            </tr>
             <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">核算分类：</td>
                <td align="left" class="l-table-edit-td"><input name="sort_code" type="text" id="sort_code" ltype="text" disabled="disabled"  value="系统生成" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr>  
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">排序号：</td>
                <td align="left" class="l-table-edit-td"><input name="sort_code" type="text" id="sort_code" ltype="text" disabled="disabled"  value="系统生成" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否停用：</td>
                <td align="left" class="l-table-edit-td">
              		   <select id="is_stop" name="is_stop" style="width: 135px;">
			                		<option value="0">启用</option>
			                		<option value="1">停用</option>
                	</select>
                </td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">对应字典表：</td>
                <td align="left" class="l-table-edit-td">
              		<input name="z_code"  type="text"  id="z_code"  />
                </td>
                <td align="left">
               		 <div style="color:#00F;">
					       生成凭证与对应的字典表同步数据
					 </div>
                </td>
            </tr>  
        </table>
    </form>
   
    </body>
</html>
