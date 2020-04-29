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

        var formPara={

           busi_data_source_type:liger.get("busi_data_source_type").getValue(),
        			
           busi_data_source_code:$("#busi_data_source_code").val(),
                
           busi_data_source_name:$("#busi_data_source_name").val(),
 
         };
        ajaxJsonObjectByUrl("updateCostBusiSourecDict.do",formPara,function(responseData){
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
   
		    function saveCostBusiSourecDict(){
		        if($("form").valid()){
		            save();
		        }
		   }
		    function loadDict(){
		            //字典下拉框
		    	$("#busi_data_source_type").ligerComboBox({  
		            data: [
		                { text: '收入数据来源', id: '1' },
		                { text: '成本数据来源', id: '2' },
		            ],value: "${busi_data_source_type}",
		        });  

		    	$("#busi_data_source_type").ligerTextBox({ disabled: true });
		    	$("#busi_data_source_code").ligerTextBox({ disabled: true });
		        
		     } 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >

           <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">数据来源类型：</td>
                <td align="left" class="l-table-edit-td"><input name="busi_data_source_type" type="text" id="busi_data_source_type" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">数据来源编码：</td>
                <td align="left" class="l-table-edit-td"><input name="busi_data_source_code" type="text" id="busi_data_source_code"  value="${busi_data_source_code}" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">数据来源名称：</td>
                <td align="left" class="l-table-edit-td"><input name="busi_data_source_name" type="text" id="busi_data_source_name"  value="${busi_data_source_name}"  ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
        </table>
    </form>
   
    </body>
</html>
