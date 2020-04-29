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
<style>
	element.style {
		width:300px;
	}

</style>
<script type="text/javascript">
	
     var dataFormat;
     var ptypeRows = {
    			Rows : [
 			{
 				"id" : "5",
 				"text" : "周" 
 			}, 
    			{
    				"id" : "4",
    				"text" : "月" 
    			}, 
    			{
    				"id" : "3",
    				"text" : "天" 
    			}],
    			Total : 3
    	 };
     $(function (){
    	 
    	 loadDict()
        
         loadForm();
    	 
    	 loadCss();
        
     });  
     
     function loadCss(){
 		
 		$("#job_code").ligerTextBox({
 			width : 400
 		});
 		
 		$("#name").ligerTextBox({
 			width : 400
 		});

 		$("#type_id").ligerTextBox({
 			width : 400
 		});
 		
 		$("#period").ligerTextBox({
 			width : 340
 		});
 		$("#ptype").ligerTextBox({
 			width : 50
 		});
 	}
     
     function save(){
    	 if($("form").valid()){
    		 if($('#cron').val()!='')
    		 	saveDataJob();
    		 else
    			 $.ligerDialog.error("任务执行规则必填!");
         }
     }
     function  saveDataJob(){
         var formPara={
        		
        		name:$("#name").val(),
        		
        		job_code:$("#job_code").val(),
        		
        		type_id:liger.get("type_id").getValue(),
        		
        		period:$("#period").val(),
        		
        		ptype:liger.get("ptype").getValue(),
        		
        		cron:$("#cron").val(),
        		        		
        		note:$("#note").val()
            
            
         };
        
        ajaxJsonObjectByUrl("add.do?isCheck=false",formPara,function(responseData){
        	if(responseData.state=="true"){     
        		 $("input[name='job_code']").val('');
				 $("input[name='name']").val('');
				 $("input[name='type_id']").val('');
				 $("input[name='period']").val('');
				 $('#cron').val('');
				 $('#note').val('');
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
 
 function loadDict(){
     //字典下拉框
     autocompleteObj({
    	 id: '#type_id',
    	 urlStr: 'queryDataType.do?isCheck=false&is_stop=0',
    	 valueField: 'ID',
    	 textField: 'TEXT',
    	 autocomplete: true,
    	 initWidth: '400',
    	 autocompletelocal: true,
    	 pageSize: 99999,
    	 selectEvent: function(){
    		 var txt=liger.get("type_id").getText();
    		 if(txt!=""){
    			 $("#job_code").val(txt.split(" ")[0]);
    			 $("#name").val(txt.split(" ")[1]);
    		 }
    	 }    	 
     });
     autoCompleteByData("#ptype", ptypeRows.Rows, "id", "text", false, true, true,true,null,50);
  } 
 
 function setCron(){
	var ptype=liger.get("ptype").getValue();
	var cron=$('#cron').val();	
 	parent.$.ligerDialog.open({ url : 'hrp/hip/dataJob/setCron.do?isCheck=false&ptype='+ptype+'&cron='+cron, height: 450,width: 600, title:'规则设置',
 			modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
 			buttons: [
 			           { text: '确定', onclick: function (item, dialog) { 
 			        	   var s=dialog.frame.getCron();
 			        	   $("#cron").val(s);
 			        	   dialog.close();
 			           
 			           },cls:'l-dialog-btn-highlight' },
 			           { text: '取消', onclick: function (item, dialog) { dialog.close(); },cls:'l-dialog-btn-highlight'  } ] });

 	
	}
    
  
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" border=0>
        	 <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">业务:</td>
                <td align="left" class="l-table-edit-td" colspan=2>
                	<input name="type_id" type="text" id="type_id" ltype="text" maxlength="50" validate="{required:true}" />
                </td>
             	<td></td>
             </tr> 
			 <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">任务编码:</td>
                <td align="left" class="l-table-edit-td" colspan=2>
                	<input name="job_code" type="text" id="job_code" ltype="text" maxlength="20" validate="{required:true}" />
                </td>
                <td></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">任务名称:</td>
                <td align="left" class="l-table-edit-td" colspan=2>
                	<input name="name" type="text" id="name" ltype="text" maxlength="20" validate="{required:true}" />
                </td>
                <td></td>
            </tr> 
             <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;" >业务时间:</td>
                <td align="left" class="l-table-edit-td">
                	<input name="period" type="text" id="period" ltype="text" maxlength="4" placeholder='例如：取昨天数据值为1，单位选择天' validate="{required:true}" />
                </td>
                <td align="left">
                	<input name="ptype" type="text" id="ptype" ltype="text" maxlength="50" validate="{required:true}" />
                </td>
                <td></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">执行规则:</td>
                <td align="left" class="l-table-edit-td" colspan=2>
                	<textarea name="cron" id="cron" validate="{required:true}" readonly disabled
                			style=" border: 1px solid #aecaf0;height: 50px;line-height: 15px;width: 400px;" validate="{required:true}"></textarea>
                </td>
                <td align="left">
                	<input  type="button" class="l-dialog-btn-highlight" style="width:50px;height:25px" value=" 设置" onclick="setCron();"/>
                </td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">备注:</td>
                <td align="left" class="l-table-edit-td" colspan=3>
                	<textarea name="note" id="note" ltype="textarea" maxlength="100"
                			style=" border: 1px solid #aecaf0;height: 100px;line-height: 20px;width: 400px;background: #fff;"></textarea>
                </td>
                <td align="left"></td>
            </tr>
            
        </table>
    </form>
   
    </body>
</html>
       
