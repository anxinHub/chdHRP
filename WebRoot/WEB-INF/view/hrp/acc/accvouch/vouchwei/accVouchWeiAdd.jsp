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
     	
        loadDict();//加载下拉框
        
        loadForm();
        
     });  
     
     function  save(){
    	 
    	 var wei_name = $("#wei_name").val();
    	 if(wei_name == ''){
    		 $.ligerDialog.warn('分册名称不能为空');
   			 return;
    	 }
    	 
    	 var vouch_type_code = liger.get("vouch_type_code").getValue();
    	 if(vouch_type_code == ''){
    		 $.ligerDialog.warn('凭证类型不能为空');
   			 return;
    	 }
    	 
    	 var begin_vouch_no =  $("#vouch_no_begin").val();
    	 if(begin_vouch_no == ''){
    		 $.ligerDialog.warn('起始凭证号不能为空');
   			 return;
    	 }
    	 
    	 
    	 var end_vouch_no = $("#vouch_no_end").val();
    	 if(end_vouch_no == ''){
    		 $.ligerDialog.warn('结束凭证号不能为空');
   			 return;
    	 }
    	 
   		 if(end_vouch_no < parseInt(begin_vouch_no)){
   			 $.ligerDialog.warn('结束凭证号必须大于或等于起始凭证号');
   			 return;
   		 }
    	 
        var formPara={
        		
           wei_name:wei_name,
           vouch_type_code:vouch_type_code,
           vouch_no_begin:begin_vouch_no,
           vouch_no_end:end_vouch_no,
           acc_year:$("#acc_year").val().substring(0,4).toString(),
           acc_month:$("#acc_year").val().substring(4,6).toString(),
           note:$("#note").val()
         };
        
        ajaxJsonObjectByUrl("addAccVouchWei.do?isCheck=false",formPara,function(responseData){
            
            if(responseData.state=="true"){
            	$("input[name='wei_name']").val('');
				$("input[name='vouch_no_begin']").val('');
				$("input[name='vouch_no_end']").val('');
				$("input[name='note']").val('');
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
   
    function saveAccVouchWei(){
       // if($("form").valid()){
            save();
        //}
   }
    function loadDict(){
    	
    	$("#acc_year").ligerTextBox({width:180});
    	
    	$("#acc_month").ligerTextBox({width:180});
    	
    	$("#wei_name").ligerTextBox({width:180});
    	
    	$("#vouch_no_begin").ligerTextBox({width:180});
    	
    	$("#vouch_no_end").ligerTextBox({width:180});
    	
    	$("#note").ligerTextBox({width:180});
            //字典下拉框
    	autocomplete("#vouch_type_code", "../../queryVouchType.do?isCheck=false","id", "text", true, true);
    	
        var year_Month = '${acc_year_month}';

   	    liger.get("acc_year").setValue(year_Month);
	
    } 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
            <tr>
            <td align="right" class="l-table-edit-td" style="padding-left: 20px;"><font color="red" size="2">*</font>年月：</td>
			<td align="left" class="l-table-edit-td"><input
				name="acc_year" type="text" id="acc_year" class="Wdate"
				onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyyMM'})" /></td>
			<td align="left"></td>
			</tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>分册名称：</td>
                <td align="left" class="l-table-edit-td"><input name="wei_name" type="text" id="wei_name" ltype="text" validate="{maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>凭证类型：</td>
                <td align="left" class="l-table-edit-td"><input name="vouch_type_code" type="text" id="vouch_type_code" ltype="text" validate="{maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>起始凭证号：</td>
                <td align="left" class="l-table-edit-td"><input name="vouch_no_begin" type="text" id="vouch_no_begin" ltype="text" validate="{maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>结束凭证号：</td>
                <td align="left" class="l-table-edit-td"><input name="vouch_no_end" type="text" id="vouch_no_end" ltype="text" validate="{maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">备注：</td>
                <td align="left" class="l-table-edit-td"><input name="note" type="text" id="note" ltype="text" validate="{maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 

        </table>
    </form>
   
    </body>
</html>
