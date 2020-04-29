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
   <script src="<%=path %>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
   <script src="<%=path %>/lib/jquery-validation/jquery.validate.min.js" type="text/javascript"></script>
   <script src="<%=path %>/lib/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
   <script src="<%=path %>/lib/jquery-validation/messages_cn.js" type="text/javascript"></script>
   <script src="<%=path %>/lib/hrp.js" type="text/javascript"></script>
   <script src="<%=path%>/lib/json2.js"></script>
    <script type="text/javascript">
     var dataFormat;
     $(function (){
         loadDict()//加载下拉框
         
         loadForm();
         $.post("showRules.do?isCheck=false",null,function(responseData){
    		 $("#font2").text(responseData);
         });
     });  
     
     function  save(){
        var formPara={
           payment_item_code:$("#payment_item_code").val(),
            
           payment_item_name:$("#payment_item_name").val(),
            
           payment_item_nature:liger.get("payment_item_nature").getValue(),
            
           is_manage:$("#is_manage").val(),
            
           control_way:liger.get("control_way").getValue(),
           
           is_stop:$("#is_stop").val()/* ,
           
           acc_subj_manage:liger.get("acc_subj_manage").getValue(),
            
           acc_subj_medical:liger.get("acc_subj_medical").getValue(),
            
           acc_subj_education:liger.get("acc_subj_education").getValue(),
            
           acc_subj_scientific:liger.get("acc_subj_scientific").getValue(),
            
           acc_subj_financial:liger.get("acc_subj_financial").getValue() */
            
         };
        
        ajaxJsonObjectByUrl("addBudgPaymentItem.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
                parent.query();
                $("#payment_item_code").val("");
                $("#payment_item_name").val("");
                liger.get("payment_item_nature").setValue(0);
                liger.get("control_way").setValue(0);
                liger.get("is_manage").setValue(0);
                liger.get("is_stop").setValue(0);
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
   
    function saveBudgPaymentItem(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){

		$("#item_name_all").ligerTextBox({width : 178,disabled:true,cancelable: false});
		$("#payment_item_code").ligerTextBox({width : 444});
		$("#payment_item_name").ligerTextBox({width : 160});
    	$("#is_manage").ligerComboBox({
			width : 160
		});
    	$("#is_stop").ligerComboBox({
			width : 160
		});
    	/* $("#acc_subj_manage").ligerComboBox({
	      	url: '../../../querySubjDict.do?isCheck=false',
	      	valueField: 'id',
	       	textField: 'text', 
	       	selectBoxWidth: 444,
	      	autocomplete: true,
	      	width: 444
		 });
    	$("#acc_subj_medical").ligerComboBox({
	      	url: '../../../querySubjDict.do?isCheck=false',
	      	valueField: 'id',
	       	textField: 'text', 
	       	selectBoxWidth: 444,
	      	autocomplete: true,
	      	width: 444
		 });
    	$("#acc_subj_education").ligerComboBox({
	      	url: '../../../querySubjDict.do?isCheck=false',
	      	valueField: 'id',
	       	textField: 'text', 
	       	selectBoxWidth: 444,
	      	autocomplete: true,
	      	width: 444
		 });
    	$("#acc_subj_scientific").ligerComboBox({
	      	url: '../../../querySubjDict.do?isCheck=false',
	      	valueField: 'id',
	       	textField: 'text', 
	       	selectBoxWidth: 444,
	      	autocomplete: true,
	      	width: 444
		 });
    	$("#acc_subj_financial").ligerComboBox({
	      	url: '../../../querySubjDict.do?isCheck=false',
	      	valueField: 'id',
	       	textField: 'text', 
	       	selectBoxWidth: 444,
	      	autocomplete: true,
	      	width: 444
		 }); */
    	
    	$("#payment_item_nature").ligerComboBox({
	      	url: '../../../queryBudgSysDict.do?isCheck=false&f_code=PAYMENT_ITEM_NATURE',
	      	valueField: 'id',
	       	textField: 'text', 
	       	selectBoxWidth: 160,
	      	autocomplete: true,
	      	width: 160
		 });
    	
    	$("#control_way").ligerComboBox({
	      	url: '../../../queryBudgSysDict.do?isCheck=false&f_code=CONTROL_WAY',
	      	valueField: 'id',
	       	textField: 'text', 
	       	selectBoxWidth: 160,
	      	autocomplete: true,
	      	width: 160
		 });
    	
    	
     } 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <font id="font1">编码规则：<font id="font2" color="red"></font></font><hr/>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">项目编码：</td>
            <td align="left" class="l-table-edit-td" colspan="4"><input name="payment_item_code" type="text" id="payment_item_code" validate="{required:true}"/></td>
            <td align="left"></td>
        </tr> 
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">项目名称：</td>
            <td align="left" class="l-table-edit-td" ><input name="payment_item_name"  type="text" id="payment_item_name" validate="{required:true}" validate="{required:true}"/></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">项目全称：</td>
            <td align="left" class="l-table-edit-td" ><input name="item_name_all" value="系统生成" disabled="disabled" type="text" id="item_name_all"  validate="{required:true}"/></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">支出项目性质：</td>
            <td align="left" class="l-table-edit-td"><input name="payment_item_nature" type="text" id="payment_item_nature"  validate="{required:true}"/></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否管理费：</td>
            <td align="left" class="l-table-edit-td">
            	<select name="is_manage" id="is_manage" >
	            	<option value="0">否</option>
	            	<option value="1">是</option>
	            </select>
            </td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">控制方式：</td>
            <td align="left" class="l-table-edit-td"><input name="control_way" type="text" id="control_way"  validate="{required:true}"/></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否停用：</td>
            <td align="left" class="l-table-edit-td">
	            <select name="is_stop" id="is_stop" >
	            	<option value="0">否</option>
	            	<option value="1">是</option>
	            </select>
            </td>
            <td align="left"></td>
        </tr> 
        <!-- <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">管理支出对应会计科目：</td>
            <td align="left" class="l-table-edit-td" colspan="4"><input name="acc_subj_manage" type="text" id="acc_subj_manage"  /></td>
            <td align="left"></td>
        </tr>    
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">医疗支出对应会计科目：</td>
            <td align="left" class="l-table-edit-td" colspan="4"><input name="acc_subj_medical" type="text" id="acc_subj_medical"  /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">教学支出对应会计科目：</td>
            <td align="left" class="l-table-edit-td" colspan="4"><input name="acc_subj_education" type="text" id="acc_subj_education"  /></td>
            <td align="left"></td>
        </tr>   
        <tr> 
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科研支出对应会计科目：</td>
            <td align="left" class="l-table-edit-td" colspan="4"><input name="acc_subj_scientific" type="text" id="acc_subj_scientific"  /></td>
            <td align="left"></td>
        </tr>
        <tr>    
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">财政支出对应会计科目：</td>
            <td align="left" class="l-table-edit-td" colspan="4"><input name="acc_subj_financial" type="text" id="acc_subj_financial"  /></td>
            <td align="left"></td>
        </tr>  -->
    </table>
    </form>
   
    </body>
</html>
