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
         loadDict()//加载下拉框
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
     
     function  save(){
    	
    	
        var formPara={
            
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
 		ajaxJsonObjectByUrl("addMedLocationDict.do",formPara,function(responseData){   
            if(responseData.state=="true"){
				
				 $("input[name='location_code']").val('');
				 $("input[name='location_name']").val('');
				 $("input[name='grid_no']").val('');
				 $("input[name='location_type_id']").val('');
				 
				 $("input[name='store_id']").val('');
				 $("input[name='location_nature']").val('');
				 $("input[name='ctrl_type']").val('');
				 $("input[name='picker']").val('');
				 
				 $("input[name='is_stop']").val('');
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
   
    function saveMedLocationDict(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
        //字典下拉框
    	$("#location_type_id").ligerComboBox({
           	url: '../../../../queryMedLocationType.do?isCheck=false',
           	valueField: 'id',
            textField: 'text', 
            selectBoxWidth: 160,
           	autocomplete: true,
           	initValue : 0,
           	width: 160
  		  }); 
    	$("#store_id").ligerComboBox({
           	url: '../../../../queryMedStore.do?isCheck=false',
           	valueField: 'id',
            textField: 'text', 
            selectBoxWidth: 200,
           	autocomplete: true,
           	initValue : 0,
           	width: 160
  		  });
    	
    	$("#picker").ligerComboBox({
           	url: '../../../../queryMedEmp.do?isCheck=false',
           	valueField: 'id',
            textField: 'text', 
            selectBoxWidth: 160,
           	autocomplete: true,
           	initValue : 0,
           	width: 160
  		  });
    	
    	$("#is_stop").ligerComboBox({
           	url: '../../../../queryMedYearOrNo.do?isCheck=false',
           	valueField: 'id',
            textField: 'text', 
            selectBoxWidth: 160,
           	autocomplete: true,
           	initValue : 0,
           	width: 160,
           	onSuccess : function (data){
    		this.setValue(0);
    		 }
  		  });
     } 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
		
        <tr></tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">货位编码：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="location_code" type="text" id="location_code" ltype="text" required="true" validate="{required:true,maxlength:20}" />
            </td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">货位名称：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="location_name" type="text" id="location_name" ltype="text" required="true" validate="{required:true,maxlength:20}" />
            </td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">排位编号：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="grid_no" type="text" id="grid_no" ltype="text" required="true" validate="{required:true,maxlength:20}" />
            </td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">货位分类：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="location_type_id" type="text" id="location_type_id" ltype="text" required="true" validate="{required:true,maxlength:20}" />
            </td>
            <td align="left"></td>
        </tr> 
        <tr>
           
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">货位性质：</td>
            <td align="left" class="l-table-edit-td">
            	<select id="location_nature" name="location_nature" >
	            		<option value="0">0 固定货位</option>
	            		<option value="1">1 自由货位</option>
	            </select>
            	
            </td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td" style="padding-left: 20px;">所属库房：</td>
			<td align="left" class="l-table-edit-td">
				<input name="store_id" type="text" id="store_id" ltype="text" required="true" />
			</td>
			<td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">控制方式：</td>
            <td align="left" class="l-table-edit-td">
            	<select id="ctrl_type" name="ctrl_type" >
	            		<option value="0">0 不控制</option>
	            		<option value="1">1 提示控制</option>
	            		<option value="2">2 强制控制</option>
	            </select>
            	
            </td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">拣货员：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="picker" type="text" id="picker" ltype="text" required="true" validate="{required:true,maxlength:20}" />
            </td>
            <td align="left"></td>
        </tr>
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否停用：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="is_stop" type="text" id="is_stop" ltype="text"  validate="{required:true,maxlength:20}" />
            </td>
            <td align="left"></td>
            <td></td>
            <td></td>
            <td></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">备注：</td>
            <td align="left" class="l-table-edit-td" colspan="5">
            	<textarea name="note" id="note" rows="2" cols="5" style="width:420px;" ></textarea>
            </td>
            
        </tr> 
    </table>
    </form>
   
    </body>
</html>
