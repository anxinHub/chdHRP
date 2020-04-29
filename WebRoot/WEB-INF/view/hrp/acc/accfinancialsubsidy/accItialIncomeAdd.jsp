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
            
           fun_code:"",
           
           eco_code:"",
           
           credit:$("#credit").val()
            
         };
        
        ajaxJsonObjectByUrl("addAccItialIncome.do?isCheck=false",formPara,function(responseData){
            
             if(responseData.state=="true"){
				
                parent.query();
            } 
        });
    }
     
 function loadForm(){
    
    $.metadata.setType("attr", "validate");
     var v = $("#form1").validate({
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
    			subj_type_code:'04'
    		};
            //字典下拉框
    	autocomplete("#subj_code","../querySubj.do?isCheck=false","id","text",true,true,data);
            
    	/* autocomplete("#fun_code","../queryFunType.do?isCheck=false","id","text",true,true);
    	
    	autocomplete("#eco_code","../queryEcoType.do?isCheck=false","id","text",true,true); */
     } 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >

            <tr>
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
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">摘要：</td>
                <td align="left" class="l-table-edit-td"><input name="summary" type="text" id="summary" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">会计科目：</td>
                <td align="left" class="l-table-edit-td"><input name="subj_code" type="text" id="subj_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <!-- <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">功能分类：</td>
                <td align="left" class="l-table-edit-td"><input name="fun_code" type="text" id="fun_code" ltype="text" validate="{maxlength:20}" /></td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">经济分类：</td>
                <td align="left" class="l-table-edit-td"><input name="eco_code" type="text" id="eco_code" ltype="text" validate="{maxlength:20}" /></td>
                <td align="left"></td>
            </tr> -->
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">收入金额：</td>
                <td align="right" class="l-table-edit-td"><input style="text-align:right" name="credit" type="text" id="credit" ltype="text"  validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr>

        </table>
    </form>
   
    </body>
</html>
