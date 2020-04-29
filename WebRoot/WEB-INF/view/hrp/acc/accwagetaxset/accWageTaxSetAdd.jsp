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
	
     var dataFormat;
     
     var i=0;
     
     var data;
     
     $(function (){
     	
        loadDict();//加载下拉框
        
        loadForm();
        
		liger.get("wage_code").setValue('${wage_code}');
		
        liger.get("wage_code").setText('${wage_name}');
        
        liger.get("wage_item_code").setValue('${wage_item_code}');
        
        liger.get("wage_item_code").setText('${wage_item_name}');
        
        $("#wage_code").ligerTextBox({ disabled: true});
        
        $("#wage_item_code").ligerTextBox({ disabled: true});
        
        $("#acc_year").ligerTextBox({ disabled: true});
        
     });  
     
     function  save(){
    	 
    	 var money = $("#money").val();
    	 
    	 var rate = $("#rate").val();
    	 
    	 if(money=="" && rate == "" ||  money!="" && rate != ""){
    		 
    		 $.ligerDialog.error('调整金额或调整比例请填写一项');
    		 
    		 return;
    		 
    	 }
    	 
        var formPara={
        		
            wage_code:liger.get("wage_code").getValue(),
            
            wage_item_code:liger.get("wage_item_code").getValue().split(".")[0],
        	
            item:liger.get("wage_item_code").getValue().split(".")[1],
            
            acc_year:liger.get("acc_year").getValue().substr(0,4),
            
            acc_month:liger.get("acc_year").getValue().substr(5,6),
            
            flag:$('input[name="flag"]:checked').val(),
           
           note:$("#note").val(),
           
           money:$("#money").val(),
            
           rate:$("#rate").val() 
            
         };
        
        $.ligerDialog.confirm('确认对你查出的所有人进行工资变动吗？', function (yes){
        	
        	if(yes){
        		ajaxJsonObjectByUrl("addAccWagePayModi.do",formPara,function(responseData){
                    
                    if(responseData.state=="true"){
        				
                        parent.query();
                    }
                });
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
   
    function saveAccPara(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
            //字典下拉框
    	 autocomplete("#wage_item_code","../queryAccWageItem.do?isCheck=false&is_stop=0","id","text",true,true);
 	
    	 autocomplete("#wage_code","../queryAccWage.do?isCheck=false","id","text",true,true);
            
    	 $("#acc_year").ligerComboBox({
	          	url: '../queryYearMonth.do?isCheck=false',
	          	valueField: 'id',
	           	textField: 'text', 
	           	selectBoxWidth: 160,
	          	autocomplete: true,
	          	width: 160,
	          	onSuccess:function(data){
	          		for(var i in data){
	          			if(data[i].id.split(".")[2] == 0){
	          				liger.get("acc_year").setValue(data[i].id.split(".")[0]+"."+data[i].id.split(".")[1]);
	          				liger.get("acc_year").setText(data[i].text);
	          			}
	          		}
	          	}
	 		  });
            
     } 
    
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit"  id="table_id">
			<tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">工资套：</td>
                <td align="left" class="l-table-edit-td"><input name="wage_code" type="text" id="wage_code" ltype="text" validate="{required:true,maxlength:20}"  disabled="disabled"/></td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">期间：</td>
                <td align="left" class="l-table-edit-td"><input name="acc_year" type="text" id="acc_year" ltype="text" validate="{required:true,maxlength:20}"  disabled="disabled"/></td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">工资项目：</td>
                <td align="left" class="l-table-edit-td"><input name="wage_item_code" type="text" id="wage_item_code" ltype="text" validate="{required:true,maxlength:20}"  disabled="disabled"/></td>
                <td align="left"></td>
            </tr> 
            
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">调整方向：</td>
                <td align="left" class="l-table-edit-td"><input name="flag" type="radio" value="1" checked="checked"/>调增<input name="flag" type="radio" value="2" />调减</td>
                <td align="left"></td>
            </tr>
            
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">调整说明：</td>
                <td align="left" class="l-table-edit-td"><input name="note" type="text" id="note" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr>
            
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">调整金额：</td>
                <td align="left" class="l-table-edit-td"><input name="money" type="text" id="money" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr>
            <tr>
            <td colspan="3" style="padding-left: 40px">或者</td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">调整比例：</td>
                <td align="left" class="l-table-edit-td"><input name="rate" type="text" id="rate" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr>
        </table>
    </form>
   
    </body>
</html>
