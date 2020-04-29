<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
   <jsp:include page="${path}/inc.jsp"/>
    <script type="text/javascript">
    var dataFormat;
    $(function (){
        loadDict();
        loadForm();
       
        $("#location_code").ligerTextBox({ width : 160 });
 		$("#location_name").ligerTextBox({width : 160});
 		$("#grid_no").ligerTextBox({width : 160});
 		$("#location_type_id").ligerTextBox({width : 160});
 		
 		$("#location_nature").ligerTextBox({width : 160});
 		$("#store_id").ligerTextBox({width : 160});
 		$("#ctrl_type").ligerTextBox({width : 160});
 		$("#picker").ligerTextBox({width : 160});
 		
 		$("#is_stop").ligerTextBox({width : 160	});
 		
 		
    });  
     
    function save(){
    	//alert("location_type_id:"+liger.get("location_type_id").getValue().split(",")[0]);	
    	//alert("store_id:"+liger.get("store_id").getValue().split(",")[0]);	
    	//alert("picker:"+liger.get("picker").getValue().split(",")[0]);	
        var formPara={
        	
        		
	        location_id:$("#location_id").val(),
	        location_code : $("#location_code").val(),
	        location_name : $("#location_name").val(),   
	        grid_no : $("#grid_no").val(),
	        location_type_id : liger.get("location_type_id").getValue().split(",")[0],
	        
	        location_nature : $("#location_nature").val(),
	        store_id : liger.get("store_id").getValue().split(",")[0],
	        location_store_id : liger.get("store_id").getValue().split(",")[0],
	        ctrl_type : $("#ctrl_type").val(),  
	        picker : liger.get("picker").getValue().split(",")[0],
	         
	        is_stop : liger.get("is_stop").getValue(),   
	        note : $("#note").val()
        };
        
		ajaxJsonObjectByUrl("updateMatLocationDict.do",formPara,function(responseData){
            
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
   
    function saveMatLocationDict(){
        if($("form").valid()){
            save();
        }
    }
    function loadDict(){
    	
  		
    	//字典下拉框
    	autocomplete("#picker","../../../../queryMatEmp.do?isCheck=false","id","text",true,true);  
        liger.get("picker").setValue("${picker}");
        liger.get("picker").setText("${picker_name}");
        
        //alert(liger.get("picker").getText());
        
    	autocomplete("#location_type_id","../../../../queryMatLocationType.do?isCheck=false","id","text",true,true);
    	liger.get("location_type_id").setValue("${location_type_id}");
        liger.get("location_type_id").setText("${location_type_code}");
        
        autocomplete("#store_id","../../../../queryMatStore.do?isCheck=false","id","text",true,true,'','','','','',200);
        liger.get("store_id").setValue("${store_id}");
        liger.get("store_id").setText("${store_name}");
        
        
    
    	autocomplete("#is_stop","../../../../queryMatYearOrNo.do?isCheck=false","id","text",true,true);
        liger.get("is_stop").setValue("${is_stop}");
    	if('${is_stop}'==0){
    		liger.get("is_stop").setText("否");
    	}else{
    		liger.get("is_stop").setText("是");
    	}
    	
    	
    	if('${ctrl_type}'==0){
    		$("#ctrl_type").val(0);
    		
    	}else if('${ctrl_type}'==1){
    		$("#ctrl_type").val(1);
           
    	}else{
    		$("#ctrl_type").val(2);
           
    	}
    	if('${location_nature}' == '0'){
    		$("#location_nature").val(0);
    		
    	}
    	if('${location_nature}' == '1'){
    		$("#location_nature").val(1);
    		
    	}
    	
        
     }   
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
		
        <tr style="display:none">
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">货位ID：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="location_id" type="text" id="location_id" ltype="text" readOnly value="${location_id}" validate="{required:true,maxlength:20}" />
            </td>
            <td align="left"></td>
        </tr> 
        <tr>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">货位编码：</td>
            <td align="left" class="l-table-edit-td">
            	<input style="width:160px;" name="location_code" type="text" id="location_code" ltype="text"  value="${location_code}" validate="{required:true,maxlength:20}" />
            </td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">货位名称：</td>
            <td align="left" class="l-table-edit-td">
            	<input style="width:160px;" name="location_name" type="text" id="location_name" ltype="text"  value="${location_name}" validate="{required:true,maxlength:20}" />
            </td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">排位编号：</td>
            <td align="left" class="l-table-edit-td">
            	<input style="width:160px;" name="grid_no" type="text" id="grid_no" ltype="text" value="${grid_no}"   validate="{required:true,maxlength:20}" />
            </td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">货位分类：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="location_type_id" type="text" id="location_type_id" ltype="text"  validate="{required:true,maxlength:20}" />
            </td>
            <td align="left"></td>
            
        </tr> 
        <tr>
            
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">货位性质：</td>
            <td align="left" class="l-table-edit-td">
            	<select id="location_nature" name="location_nature" style="width:160px;">
	            		<option value="0">0 固定货位</option>
	            		<option value="1">1 自由货位</option>
	            </select>
            	
            </td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">所属库房：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="store_id" type="text" id="store_id" ltype="text" validate="{required:true,maxlength:20}" />
            </td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">控制方式：</td>
            <td align="left" class="l-table-edit-td">
            
            	<select id="ctrl_type" name="ctrl_type" style="width:160px;">
	            		<option value="0">0 不控制</option>
	            		<option value="1">1 提示控制</option>
	            		<option value="2">2 强制控制</option>
	            </select>
            	
            </td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">拣货员：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="picker" type="text" id="picker" ltype="text"   validate="{required:true,maxlength:20}" />
            </td>
            <td align="left"></td>
            
        </tr> 
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否停用：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="is_stop" type="text"  id="is_stop" ltype="text"  validate="{required:true,maxlength:20}" />
            </td>
            <td align="left"></td>
            <td></td>
            <td></td>
            <td></td>
        </tr>
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">备注：</td>
            <td align="left" class="l-table-edit-td" colspan="5">
            	<textarea name="note" id="note" rows="2" cols="5" style="width:420px;" >${note}</textarea>
            </td>
            
        </tr> 
			
        </table>
    </form>
    </body>
</html>
