<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc.jsp"/>
<script type="text/javascript">
	
    var dataFormat;
    
    $(function (){
    	
        loadDict();
        
        loadForm();
        
       
    });  
     
    function save(){
    	
        var dialog = frameElement.dialog; //调用页面的dialog对象(ligerui对象)
        
        var data=dialog!=null?dialog.get("data").data:"";
        
        
        var ParamVo =[];
        $(data).each(function (){
        	
			if(this.tell_id != "" &&this.tell_id !=null){
				
				ParamVo.push(
						//表的主键
						this.tell_id   +"@"+ 
						$("#con_date").val()
					)
			}
        });
        ajaxJsonObjectByUrl("updateAccBankByAccountState.do?isCheck=false",{ParamVo : ParamVo},function(responseData){
            if(responseData.state=="true"){
                parent.query();
              /*   parent.$.ligerDialog.close();
                parent.$(".l-dialog,.l-window-mask").remove(); */
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
   
    function accBankByAccountCon(){
            save();
        
    }
    function loadDict(){
        //字典下拉框
    	
     }   
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" style="margin-top: 70px;margin-left: 50px">
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">确认日期：</td>
                <td align="left" class="l-table-edit-td"><input class="Wdate" name="con_date" type="text" id="con_date" ltype="text" validate="{required:true,maxlength:20}" 
                onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd'})" value="${con_date}"/></td>
                <td align="left"></td>
            </tr> 
        </table>
    </form>
    </body>
</html>
