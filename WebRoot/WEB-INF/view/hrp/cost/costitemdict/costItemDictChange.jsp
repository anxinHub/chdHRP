<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
<link href="<%=path%>/lib/htc/style/select/chosen.css" rel="stylesheet">
<script src="<%=path%>/lib/htc/style/select/chosen.jquery.js" type="text/javascript"></script>
<script type="text/javascript">
	
    var dataFormat;
    
    $(function (){
    	
        loadDict();
        
        loadForm();
        
        update(0);
        
    });  
     
    function save(){
    	
        if($("#codeTable").is(':hidden') && $("#nameTable").is(':show')){//名称变更
        	var note2 = $("#note2").val();	
        	if(note2 == ""){
        		note2 = " ";
        	}
           if($("#new_cost_item_name").val()=="") {
        	   alert("新名称不能为空!");  
 	          return;
        	   }
           if($("#note2").val()=="") {
        	   alert("变更原因不能为空!");  
 	          return;
        	   }
        	
        	var formPara2 = {
        			cost_item_no:'',
        			cost_item_id:'${cost_item_id}',
        			cost_type_id:liger.get("cost_type1").getValue().split(".")[0],
        	        cost_type_no:liger.get("cost_type1").getValue().split(".")[1],
        	        cost_item_code:'${cost_item_code}',
        	        cost_item_name:$("#new_cost_item_name").val(),
        	        supp_item_code:'${supp_item_code}',
        	        cost_type_id:'${cost_type_id}',
        	        nature_id:'${nature_id}',
        	        busi_data_source:'${busi_data_source}',
        	        para_type_code:'${para_type_code}',
        	        item_grade:'${item_grade}',
                    is_stop:0,
                    is_last:'${is_last}',
                    note:$("#note2").val(),
                    dict_type:"1"
            };
        	var cost_item_name=$("#new_cost_item_name").val();
        	
        	ajaxJsonObjectByUrl("../costitemdictno/addCostItemDictNo.do?isCheck=false",formPara2,function (responseData){
				if(responseData.state=="true"){
					
					parent.location.reload(); 
					
					parent.parent.query($("#new_cost_item_name").val());
	            	parent.parent.loadTree();
        		}
     		});
        }else{//编码变更
        	var note1 = $("#note1").val();	
        	if(note1 == ""){
        		note1 = " ";
        	}
        	
        	if($("#new_cost_item_code").val()=="") {
         	   alert("新编码不能为空!");  
  	          return;
         	   }
            if($("#note1").val()=="") {
         	   alert("变更原因不能为空!");  
  	          return;
         	   }
        	
        	var formPara1 = { 
        			cost_item_no:'',
        			cost_item_id:'${cost_item_id}',
        			cost_type_id:liger.get("cost_type1").getValue().split(".")[0],
        	        cost_type_no:liger.get("cost_type1").getValue().split(".")[1],
        	        cost_item_code:$("#new_cost_item_code").val(),
        	        cost_item_name:'${cost_item_name}',
        	        supp_item_code:'${supp_item_code}',
        	        cost_type_id:'${cost_type_id}',
        	        nature_id:'${nature_id}',
        	        busi_data_source:'${busi_data_source}',
        	        para_type_code:'${para_type_code}',
        	        item_grade:'${item_grade}',
    		        is_stop:0,
    		        is_last:'${is_last}',
    		        note:$("#note1").val(),
    		        dict_type:"0"
            };

        	var cost_item_code = $("#new_cost_item_code").val();
        	ajaxJsonObjectByUrl("../costitemdictno/addCostItemDictNo.do?isCheck=false",formPara1,function (responseData){

        		if(responseData.state=="true"){
        			//2016/10/26 lxj
					//刷新父页面
        			parent.location.reload(); 
        			parent.parent.query($("#new_cost_item_code").val());
	            	parent.parent.loadTree();
        		}
     		});
        }
       
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
   
    function saveGroup(){
        if($("form").valid()){
            save();
        }
    }
    function loadDict(){
    	autocomplete("#cost_type1","../queryDeptTypeDictNo.do?isCheck=false","id","text",true,true);
		liger.get("cost_type1").setValue('${cost_type_id}.${cost_type_no}');
	    liger.get("cost_type1").setText('${cost_type_name}');
	    
     }   
    
    function update(state){
    	if(state == 0){
    		$("#codeTable").show();
    		$("#nameTable").hide();
    	}else{
    		$("#codeTable").hide();
    		$("#nameTable").show();
    	}
    }
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <div align="center" >
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">
                	<div class="l-button" style="width: 60px;  margin-right: 40px;margin-left: 18px;" ligeruiid="Button1000" onclick="update(0);">
   					<span>编码变更</span></div>
                </td>
                <td align="left" class="l-table-edit-td">
                	<div class="l-button" style="width: 60px; " ligeruiid="Button1001" onclick="update(1);">
   					<span>名称变更</span></div>
                </td>
                <td align="left"></td>
            </tr> 
        </table>
   </div>
   <div align="center" >
   <form name="form" method="post"  id="form" >
        <table cellpadding="0" cellspacing="0" id="codeTable" class="l-table-edit" style="display: inline;">
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">旧编码：</td>
                <td align="left" class="l-table-edit-td"><input name="cost_item_code" type="text" disabled="disabled" id="cost_item_code" ltype="text"  value="${cost_item_code}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
             <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">新编码：</td>
                <td align="left" class="l-table-edit-td"><input name="new_cost_item_code" type="text" id="new_cost_item_code" ltype="text"  validate="{required:true,maxlength:20}"/></td>
                <td align="left"></td>
            </tr>
             <tr>
             	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">成本分类：</td>
                <td align="left" class="l-table-edit-td"><input name="cost_type1" type="text" id="cost_type1" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td> 
            </tr>    
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;" validate="{required:true,maxlength:20}">变更原因：</td>
                <td align="left" class="l-table-edit-td"><textarea rows="3" cols="30" id="note1" name="note1"></textarea></td>
                <td align="left"></td>
            </tr> 
        </table>
        
        
        <table cellpadding="0" cellspacing="0" id="nameTable" class="l-table-edit" style="display: none;">
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">旧名称：</td>
                <td align="left" class="l-table-edit-td"><input name="cost_item_name" disabled="disabled" type="text" id="cost_item_name" ltype="text"  value="${cost_item_name}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
             <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">新名称：</td>
                <td align="left" class="l-table-edit-td"><input name="new_cost_item_name"  type="text" id="new_cost_item_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
           <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">变更原因：</td>
                <td align="left" class="l-table-edit-td"><textarea rows="3" cols="30" id="note2" name="note2" validate="{required:true,maxlength:20}"></textarea></td>
                <td align="left"></td>
            </tr> 
        </table>
    </form>
    </div>
    </body>
</html>
