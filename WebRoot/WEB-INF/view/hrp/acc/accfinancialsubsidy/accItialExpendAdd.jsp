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
<script src="<%=path%>/lib/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script type="text/javascript">
	
     var dataFormat;
     
     $(function (){
     	
        loadDict();//加载下拉框
        
        loadForm();
        
     });  
     
     function  save(){
    	 
        var formPara={
        		
           sub_id:'',
            
           occur_date:$("#occur_date").val(),
           
           vouch_no:$("#vouch_no").val(),
            
           summary:$("#summary").val(),
            
           subj_code:liger.get("subj_code").getValue(),
            
           fun_code:liger.get("fun_code").getValue(),
           
           eco_code:liger.get("eco_code").getValue(),
           
           debit:$("#debit").val()
            
         };
        
        ajaxJsonObjectByUrl("addAccItialExpend.do?isCheck=false",formPara,function(responseData){
            
             if(responseData.state=="true"){
				
                parent.query();
                $("#occur_date").val("");
                $("#vouch_no").val(""),
                $("#summary").val(""),
                liger.get("subj_code").setValue(),
                liger.get("fun_code").setValue(),
                liger.get("eco_code").setValue(),
                $("#debit").val("")
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
   
    function saveAccCashItem(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
    	var data={
    			subj_type_code:'05',
    			is_last:1
    		};
            //字典下拉框 
    	autocomplete("#subj_code","../querySubj.do?isCheck=false","id","text",true,true,data,false,'','300');
            
    	autocomplete("#fun_code","../queryFunType.do?isCheck=false","id","text",true,true);
    	
    	autocomplete("#eco_code","../queryEcoType.do?isCheck=false","id","text",true,true);
     } 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1"  >
        <table cellpadding="0" cellspacing="0"  align="center"  class="l-table-edit" >

            <tr >
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">发生日期：</td>
                <td align="left" class="l-table-edit-td"><input class="Wdate" name="occur_date" type="text" id="occur_date" ltype="text" validate="{maxlength:20}" 
                onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd'})"/></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">凭证编号：</td>
                <td align="left" class="l-table-edit-td"><input name="vouch_no" type="text" id="vouch_no" ltype="text" validate="{maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">摘&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;要：</td>
                <td align="left" class="l-table-edit-td"><input name="summary" type="text" id="summary" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;目：</td>
                <td align="left" class="l-table-edit-td"><input name="subj_code" type="text" id="subj_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">功能分类：</td>
                <td align="left" class="l-table-edit-td"><input name="fun_code" type="text" id="fun_code" ltype="text" validate="{maxlength:20}" /></td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">经济分类：</td>
                <td align="left" class="l-table-edit-td"><input name="eco_code" type="text" id="eco_code" ltype="text" validate="{maxlength:20}" /></td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">支出金额：</td>
                <td align="left" class="l-table-edit-td"><input name="debit" type="text" id="debit" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr>

        </table>
    </form>
   
    </body>
</html>
