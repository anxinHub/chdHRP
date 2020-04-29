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
        
        $("#column_item").ligerTextBox({ disabled: true});
        
        $("#item_code").ligerTextBox({ disabled: true});
        
    });  
     
    function save(){
    	
        var formPara={
				
		group_id:'${group_id}',
		
		hos_id:'${hos_id}',
		
		copy_code:'${copy_code}',
		
        acc_year:'${acc_year}',
        
        item_id:'${item_id}',
        
        wage_code:liger.get("wage_code").getValue(),
		
        item_code:$("#item_code").val(),
         
        item_name:$("#item_name").val(),
         
        item_type:liger.get("item_type").getValue(),
         
        item_cal:liger.get("item_cal").getValue(),
         
        item_nature:liger.get("item_nature").getValue(),
         
        is_jc:$("#is_jc").val(),
         
        is_sum:$("#is_sum").val(),
        
        sort_code:$("#sort_code").val(),
         
        is_stop:$("#is_stop").val(),
        
        note:$("#note").val(),
        
        column_item:'${column_item }'
        };

        ajaxJsonObjectByUrl("updateAccWageItems.do",formPara,function(responseData){
            
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
   
    function saveAccWageItems(){
        if($("form").valid()){
            save();
        }
    }
    function loadDict(){
        //字典下拉框
    	autoCompleteByData("#item_type",item_type_dict.Rows,"item_type_code","item_type_name",true,true);
    	for(var item_type in item_type_dict.Rows){
    		if(item_type_dict.Rows[item_type].item_type_code == '${item_type}'){
    			liger.get("item_type").setValue(item_type_dict.Rows[item_type].item_type_code);
    	        liger.get("item_type").setText(item_type_dict.Rows[item_type].item_type_name);
    		}
    	}
        
    	autoCompleteByData("#item_nature",item_nature_dict.Rows,"item_nature_code","item_nature_name",true,true);
    	for(var item_nature in item_nature_dict.Rows){
    		if(item_nature_dict.Rows[item_nature].item_nature_code == '${item_nature}'){
    			liger.get("item_nature").setValue(item_nature_dict.Rows[item_nature].item_nature_code);
    	        liger.get("item_nature").setText(item_nature_dict.Rows[item_nature].item_nature_name);
    		}
    	}
    	
    	autoCompleteByData("#item_cal",item_cal_dict.Rows,"item_cal_code","item_cal_name",true,true);
    	for(var item_cal in item_cal_dict.Rows){
    		if(item_cal_dict.Rows[item_cal].item_cal_code == '${item_cal}'){
    			liger.get("item_cal").setValue(item_cal_dict.Rows[item_cal].item_cal_code);
    	        liger.get("item_cal").setText(item_cal_dict.Rows[item_cal].item_cal_name);
    		}
    	}
    	
    	autocomplete("#wage_code","../queryAccWage.do?isCheck=false","id","text",true,true);
    	
    	$("#is_jc").val('${is_jc}');
    	
    	$("#is_sum").val('${is_sum}');
    	
    	$("#is_stop").val('${is_stop}');
    	
    	liger.get("wage_code").setValue("${wage_code}");
    	
        liger.get("wage_code").setText("${wage_name}");
     }   
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >

            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">工资项编码：</td>
                <td align="left" class="l-table-edit-td"><input name="item_code" type="text" id="item_code" disabled="disabled" ltype="text"  value="${item_code}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">工资项名称：</td>
                <td align="left" class="l-table-edit-td"><input name="item_name" type="text" id="item_name" ltype="text"  value="${item_name}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">工资套：</td>
                <td align="left" class="l-table-edit-td"><input name="wage_code" type="text" id="wage_code" ltype="text"  value="${wage_code}"  validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">工资项类型：</td>
                <td align="left" class="l-table-edit-td"><input name="item_type" type="text" id="item_type" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">计算方式：</td>
                <td align="left" class="l-table-edit-td"><input name="item_cal" type="text" id="item_cal" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">工资项性质：</td>
                <td align="left" class="l-table-edit-td"><input name="item_nature" type="text" id="item_nature" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否继承上月：</td>
                <td align="left" class="l-table-edit-td">
                	<select id="is_jc" name="is_jc" style="width: 135px;">
			                		<option value="0">否</option>
			                		<option value="1">是</option>
                	</select>
                </td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否参与合计：</td>
                <td align="left" class="l-table-edit-td">
                	<select id="is_sum" name="is_sum" style="width: 135px;">
			                		<option value="0">否</option>
			                		<option value="1">是</option>
                	</select>
                </td>
                <td align="left"></td>
            </tr> 
            
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">状态：</td>
                <td align="left" class="l-table-edit-td">
                	<select id="is_stop" name="is_stop" style="width: 135px;">
			                		<option value="0">启用</option>
			                		<option value="1">停用</option>
                	</select>
                </td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">排序号：</td>
                <td align="left" class="l-table-edit-td" ><input name="sort_code" type="text" id="sort_code" ltype="text" value="${sort_code }" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
             <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">工资项目字段：</td>
                <td align="left" class="l-table-edit-td"><input name="column_item" type="text" id="column_item" ltype="text"  value="${column_item }" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
           <!--  </tr> 
            <tr> -->
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">备注：</td>
                <td align="left" class="l-table-edit-td" ><input name="note" type="text" id="note" ltype="text" value="${note }" maxlength="50"/></td>
                <td align="left"></td>
            </tr> 

        </table>
    </form>
    </body>
</html>
