<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;"> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<%=path %>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
  <jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script src="<%=path %>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path %>/lib/jquery-validation/jquery.validate.min.js" type="text/javascript"></script>
<script src="<%=path %>/lib/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
<script src="<%=path %>/lib/jquery-validation/messages_cn.js" type="text/javascript"></script>
<script src="<%=path %>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>
<script type="text/javascript">


var paper_way_type =  [{ text: '单张管理', id: '1' },{ text: '多张管理', id: '2' }]
var paper_use_type =  [{ text: '一次领用', id: '1' },{ text: '二次领用', id: '2' }]
var state =  [{ text: '否', id: '0' },{ text: '是', id: '1' }]
     var dataFormat;
     
     $(function (){
     	
        loadDict();//加载下拉框
        
        loadForm();
        
     });  
     
     function  save(){
    	 
    	 if(!/^[0-9]*$/.test($("#paper_clen").val())){
    		
    			$.ligerDialog.error('票据号长度不是数字类型');
    			
    			return false;
    	 }
    	 
         if(!/^[0-9]*$/.test($("#paper_zlen").val())){
    		
        		$.ligerDialog.error('每本张数不是数字类型');
        		
        		return false;
    	 }
    	
        var formPara={ 
        		
           type_code:$("#type_code").val(),
            
           type_name:$("#type_name").val(), 
           
           paper_prefix:$("#paper_prefix").val(),
           
           paper_clen:$("#paper_clen").val(),
           
           paper_zlen:$("#paper_zlen").val(),
           
           paper_way_type:liger.get("paper_way_type").getValue(),
        	   
           paper_use_type:liger.get("paper_use_type").getValue(),
        		   
           is_sd:liger.get("is_sd").getValue(),
        			   
           is_auto:liger.get("is_auto").getValue(),
            
           is_stop:liger.get("is_stop").getValue(),
           
           is_wb:liger.get("is_wb").getValue(),
           
           note:$("#note").val(),
         };
        ajaxJsonObjectByUrl("updateAccPaperType.do",formPara,function(responseData){
            
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
   
    function saveAccPayType(){
        if($("form").valid()){
            save();
        }
   }
     function loadDict(){
    	 
    	 $("#type_code").ligerTextBox({width:200});
    	 liger.get('type_code').setDisabled(true);
 
         $("#paper_way_type").ligerComboBox({  
             data: paper_way_type
         }); 
         liger.get("paper_way_type").setValue("${paper_way_type}");
 		 liger.get("paper_way_type").setText("${paper_way_type_name}");
         $("#paper_use_type").ligerComboBox({  
             data: paper_use_type
         }); 
         liger.get("paper_use_type").setValue("${paper_use_type}");
 		 liger.get("paper_use_type").setText("${paper_use_type_name}");
         $("#is_sd").ligerComboBox({  
             data: state
         });
         liger.get("is_sd").setValue("${is_sd}");
 		 liger.get("is_sd").setText("${is_sd_name}");
         $("#is_auto").ligerComboBox({  
             data: state
         });
         liger.get("is_auto").setValue("${is_auto}");
 		 liger.get("is_auto").setText("${is_auto_name}");
         $("#is_stop").ligerComboBox({  
             data: state,
             initValue:0
         }); 
         liger.get("is_stop").setValue("${is_stop}");
 		 liger.get("is_stop").setText("${is_stop_name}");
         $("#is_wb").ligerComboBox({  
             data: state,
             initValue:0
         }); 
         liger.get("is_wb").setValue("${is_wb}");
 		 liger.get("is_wb").setText("${is_wb_name}");
         
           
     }  
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >

            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red">*</font>类型编码：</td>
                <td align="left" class="l-table-edit-td"><input name="type_code" type="text" id="type_code" ltype="text" value="${type_code}" disabled="disabled" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red">*</font>类型编码：</td>
                <td align="left" class="l-table-edit-td"><input name="type_name" type="text" id="type_name" ltype="text" value="${type_name}" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">前缀：</td>
                <td align="left" class="l-table-edit-td"><input name="paper_prefix" type="text" id="paper_prefix" ltype="text" value="${paper_prefix}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red">*</font>票据号长度：</td>
                <td align="left" class="l-table-edit-td"><input name="paper_clen" type="text" id="paper_clen" ltype="text" value="${paper_clen}" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red">*</font>每本张数：</td>
                <td align="left" class="l-table-edit-td"><input name="paper_zlen" type="text" id="paper_zlen" ltype="text" value="${paper_zlen}" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red">*</font>管理方式：</td>
                <td align="left" class="l-table-edit-td"><input name="paper_way_type" type="text" id="paper_way_type" ltype="text" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr> 
              <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red">*</font>领用方式：</td>
                <td align="left" class="l-table-edit-td"><input name="paper_use_type" type="text" id="paper_use_type" ltype="text" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr> 
             <tr style="display:none">
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red">*</font>手动核销：</td>
                <td align="left" class="l-table-edit-td"><input name="is_sd" type="text" id="is_sd" ltype="text" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr> 
              <tr style="display:none">
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red">*</font>自动核销：</td>
                <td align="left" class="l-table-edit-td"><input name="is_auto" type="text" id="is_auto" ltype="text" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr> 
              <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red">*</font>是否停用：</td>
                <td align="left" class="l-table-edit-td"><input name="is_stop" type="text" id="is_stop" ltype="text" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr> 
              <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red">*</font>是否银行票据：</td>
                <td align="left" class="l-table-edit-td"><input name="is_wb" type="text" id="is_wb" ltype="text" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr> 
             <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">备注：</td>
                <td align="left" class="l-table-edit-td"><input name="note" type="text" id="note"  value="${note}"  ltype="text"/></td>
                <td align="left"></td>
            </tr> 
        </table>
    </form>
   
    </body>
</html>
