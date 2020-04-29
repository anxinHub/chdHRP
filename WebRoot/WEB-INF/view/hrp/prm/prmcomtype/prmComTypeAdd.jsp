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
     
     var para = "";
     
     var fc=0;
     
     var dialog = frameElement.dialog;
      
     
     $(function (){

    	 loadDefine();
    	 
    	 loadValue();
    	 
     });
     
    function loadValue(){
    	
 		var dialogData = dialog.get('data').name;//获取data参数
 	 
 		var iw = $("#iw");
    	 
    	 if(dialogData == ""){
    		 
    		 return false;
    		 
    	 }else{
    		 
    		 var dataSplit = dialogData.split("#");
    		 
    		 for ( i=0; i<dataSplit.length ;i++ ){
    		        
    		        var data = dataSplit[i].split(";"); 
    		        
    		        fc++;
    		        
    		        if(data[0] == "text"){
 
    		        	$(iw).append('<tr class="l-table-edit-tr">'+
    		        			'<td align="right" class="l-table-edit-td">'+
    		            		'<input type="button" class="liger-button" id="del" onClick="delTr(this);" value="删除"/>'+
    		            		'</td>'+
    		        			'<td align="right" class="l-table-edit-td">'+ fc +'.部件编码：</td>'+
    		            		'<td align="right" class="l-table-edit-td">'+
    		            		'<input name="com_type_code'+ fc +'" type="text" value="'+data[2]+'" id="com_type_code'+ fc +'" ltype="text" validate="{required:true,maxlength:20}" />'+
    		            		'</td>'+
    		     				'</td><td align="right" class="l-table-edit-td">部件名称：</td>'+
    		    				'<td align="right" class="l-table-edit-td">'+
    		    				'<input name="com_type_name'+ fc +'" value="'+data[1]+'" type="text" id="com_type_name'+ fc +'" validate="{required:true,maxlength:20}" />'+
    		    				'<input type="hidden" name="com_type_nature'+ fc +'" id="com_type_nature'+ fc +'" value="text"></td>'+		    				
    		    				'<td class="l-table-edit-td"></td><td class="l-table-edit-td"></td>'+
    		    				'</tr>');
    		        	
    		        	loadTextStyle(fc);
    		        	
    		        }else if(data[0] == "input"){
    		        	 
    		        	
    		        	$(iw).append('<tr class="l-table-edit-tr">'+
    		        			'<td align="right" class="l-table-edit-td">'+
    		            		'<input type="button" class="liger-button" id="del" onClick="delTr(this);" value="删除"/>'+
    		            		'</td>'+
    		        			'<td align="right" class="l-table-edit-td">'+ fc +'.部件编码：</td>'+
    		            		'<td align="right" class="l-table-edit-td">'+
    		            		'<input name="com_type_code'+ fc +'" type="text" value="'+data[2]+'"  id="com_type_code'+ fc +'" ltype="text" validate="{required:true,maxlength:20}" />'+
    		            		'</td>'+
			     				'<td align="right" class="l-table-edit-td">部件名称：</td>'+
			    				'<td align="right" class="l-table-edit-td">'+
			    				'<input name="com_type_name'+ fc +'" value="'+data[1]+'" type="text" id="com_type_name'+ fc +'" ltype="text" validate="{required:true,maxlength:20}" />'+
			    				'<input type="hidden" name="com_type_nature'+ fc +'" id="com_type_nature'+ fc +'" value="input"></td>'+
			    				'<td align="left" class="l-table-edit-td">下拉框默认值：</td>'+
			    				'<td align="left" class="l-table-edit-td">'+
			    				'<input class="para_code'+ fc +'" name="para_code'+ fc +'" type="text" id="para_code'+ fc +'" ltype="text" />'+
			    				'</td>'+
			    				'</tr>');  

    		        	loadTextStyle(fc);
    		        	
    		        	loadDict(fc);

    		        	liger.get("para_code"+ fc).setValue(data[3]);
    		        	
    		    		liger.get("para_code"+ fc).setText(data[4]);
    		        	
    		        }else if(data[0] == "date"){
    		        	
    		        	$(iw).append('<tr class="l-table-edit-tr">'+
    		        			'<td align="right" class="l-table-edit-td">'+
    		            		'<input type="button" class="liger-button" id="del" onClick="delTr(this);" value="删除"/>'+
    		            		'</td>'+
    		        			'<td align="right" class="l-table-edit-td">'+ fc +'.部件编码：</td>'+
    		            		'<td align="right" class="l-table-edit-td">'+
    		            		'<input name="com_type_code'+ fc +'" type="text" value="'+data[2]+'" id="com_type_code'+ fc +'" ltype="text" validate="{required:true,maxlength:20}" />'+
    		            		'</td>'+
			     				'<td align="right" class="l-table-edit-td">部件名称：</td>'+
			    				'<td align="right" class="l-table-edit-td">'+
			    				'<input name="com_type_name'+ fc +'" value="'+data[1]+'" type="text" id="com_type_name'+ fc +'" ltype="text" validate="{required:true,maxlength:20}" />'+
			    				'<input type="hidden" name="com_type_nature'+ fc +'" id="com_type_nature'+ fc +'" value="date"></td>'+		    				
			    				//'<td align="left" class="l-table-edit-td">日期框默认值：</td>'+
			    				//'<td align="left" class="l-table-edit-td">'+
			    				//'<input name="para_code'+ fc +'" type="text" id="para_code'+ fc +'" ltype="text" />'+
								//'</td>'+
			    				'</tr>'); 
    		        	
    		        	loadTextStyle(fc);
    		        	
    		        	//loadDate(fc);
    		        	
						//liger.get("para_code"+ fc).setValue(data[3]);
    		        	
    		    		//liger.get("para_code"+ fc).setText(data[4]);

    		        	
    		        } 
    		 }  
    		 
    	 }

    }
     
	function loadDefine() {
    	 
		 var mi = 9;
		 var iw = $("#iw");
		 var amt = $("#amt");
		 var amsb = $("#amsb");
		 var amdfb = $("#amdfb");
		 var x = iw.length;

		 $(amt).click(function (e){
		         if(x <= mi){ 
		        	 
		             fc++;

		             $(iw).append('<tr class="l-table-edit-tr">'+
		            		'<td align="right" class="l-table-edit-td">'+
		            		'<input type="button" class="liger-button" id="del" onClick="delTr(this);" value="删除"/>'+
		            		'</td>'+
		            		'<td align="right" class="l-table-edit-td">'+ fc +'.部件编码：</td>'+
		            		'<td align="right" class="l-table-edit-td">'+
		            		'<input name="com_type_code'+ fc +'" type="text" id="com_type_code'+ fc +'"ltype="text" validate="{required:true,maxlength:20}" />'+
		            		'</td>'+
		     				'</td><td align="right" class="l-table-edit-td">部件名称：</td>'+
		    				'<td align="right" class="l-table-edit-td">'+
		    				'<input name="com_type_name'+ fc +'" type="text" id="com_type_name'+ fc +'" validate="{required:true,maxlength:20}" />'+
		    				'<input type="hidden" name="com_type_nature'+ fc +'" id="com_type_nature'+ fc +'" value="text"></td>'+
		    				'<td class="l-table-edit-td"></td><td class="l-table-edit-td"></td>'+
		    				'</tr>');
		             
		             x++;
		             
		             loadTextStyle(fc);

		         }  
		 	return false;  
		 }); 
		 $(amsb).click(function (e){
		         if(x <= mi){ 
		        	 
		             fc++;
	
		             $(iw).append('<tr class="l-table-edit-tr">'+
		            		 	'<td align="right" class="l-table-edit-td">'+
		            		 	'<input type="button" class="liger-button" id="del" onClick="delTr(this);" value="删除"/>'+
		            		 	'</td>'+
		            		 	'<td align="right" class="l-table-edit-td">'+ fc +'.部件编码：</td>'+
		            		 	'<td align="right" class="l-table-edit-td">'+
		            		 	'<input name="com_type_code'+ fc +'" type="text" id="com_type_code'+ fc +'"ltype="text" validate="{required:true,maxlength:20}" />'+
		            		 	'</td>'+
			     				'<td align="right" class="l-table-edit-td">部件名称：</td>'+
			    				'<td align="right" class="l-table-edit-td">'+
			    				'<input name="com_type_name'+ fc +'" type="text" id="com_type_name'+ fc +'" ltype="text" validate="{required:true,maxlength:20}" />'+
			    				'<input type="hidden" name="com_type_nature'+ fc +'" id="com_type_nature'+ fc +'" value="input"></td>'+
			    				'<td align="left" class="l-table-edit-td">下拉框默认值：</td>'+
			    				'<td align="left" class="l-table-edit-td">'+
			    				'<input name="para_code'+ fc +'" type="text" id="para_code'+ fc +'" ltype="text" />'+
			    				'</td>'+
			    				'</tr>');  
		             
		             x++;
		             
		             loadTextStyle(fc);
		             
		             loadDict(fc);
		         }  
		 	return false;  
		 });
		 $(amdfb).click(function (e){
		         if(x <= mi){ 
		        	 
		             fc++;

		             $(iw).append('<tr class="l-table-edit-tr">'+
		            		 	'<td align="right" class="l-table-edit-td">'+
		            		 	'<input type="button" class="liger-button" id="del" onClick="delTr(this);" value="删除"/>'+
		            		 	'</td>'+
		            		 	'<td align="right" class="l-table-edit-td">'+ fc +'.部件编码：</td>'+
		            		 	'<td align="right" class="l-table-edit-td">'+
		            		 	'<input name="com_type_code'+ fc +'" type="text" id="com_type_code'+ fc +'"ltype="text" validate="{required:true,maxlength:20}" />'+
		            		 	'</td>'+
			     				'<td align="right" class="l-table-edit-td">部件名称：</td>'+
			    				'<td align="right" class="l-table-edit-td">'+
			    				'<input name="com_type_name'+ fc +'" type="text" id="com_type_name'+ fc +'" ltype="text" validate="{required:true,maxlength:20}" />'+
			    				'<input type="hidden" name="com_type_nature'+ fc +'" id="com_type_nature'+ fc +'" value="date"></td>'+
			    				//'<td align="left" class="l-table-edit-td">日期框默认值：</td>'+
			    				//'<td align="left" class="l-table-edit-td">'+
			    				//'<input name="para_code'+ fc +'" type="text" id="para_code'+ fc +'" ltype="text" />'+
			    				//'</select></td>'+
			    				'</tr>'); 

		             x++;
		             
		             loadTextStyle(fc);
		             
		             //loadDate(fc);

		         }  
		 	return false;  
		 });

 	 }
	
	function delTr(delbtn){

		$(delbtn).parents("tr").remove();
		
		fc--;
		
	}
    
	function loadTextStyle(fc){
		
		$("#com_type_name"+fc).ligerTextBox({width:100 });
		
		$("#com_type_code"+fc).ligerTextBox({width:100 });
		
	}
	
	//function loadComboxStyle(fc){
		
	//	$("#date_default_value"+fc).ligerComboBox({width:160 });
		
	//}
	
	//function loadDate(fc) {

	//	autoCompleteByData("#para_code"+fc,para_date.Rows,"para_code", "para_name", true, true, para);
			
	//}
	
    function loadDict(fc) {

    	 autocomplete("#para_code"+fc,"../queryPrmFunParaMethod.do?isCheck=false","id", "text", true, true, para);
 			
 	}
     
     function  save(){
    	 
    	 var where_name="";
    	 
    	 var para_value="";
    	 
    	 for(var i =1 ;i<=fc; i++){

    		 var com_type_nature=$("#com_type_nature"+i).val();
    		 
    		 if(com_type_nature == 'text'){
    			 
    			 var com_type_name = $("#com_type_name"+i).val();
    			 
    			 var com_type_code = $("#com_type_code"+i).val();
    			 
				if(com_type_code == ""){
    				 
    				 $.ligerDialog.error('请输入条件'+i+'部件代码');
    				 
    				 return false;
    				 
    			 }
    			 
    			 if(com_type_name == ""){
    				 
    				 $.ligerDialog.error('请输入条件'+i+'部件名称');
    				 
    				 return false;
    				 
    			 }

    			 where_name = where_name  + com_type_name + ",";
    			 
    			 para_value = para_value  + "text;" + com_type_name + ";" +com_type_code+ "#";
    			 
    			 
    		 }
    		 
    		 if(com_type_nature == 'input'){
    			 
 				 var com_type_name = $("#com_type_name"+i).val();
    			 
    			 var com_type_code = $("#com_type_code"+i).val();
    			 
    			 var para_code = liger.get("para_code"+i).getValue();
    			 
    			 
    			 
    			 var para_name = liger.get("para_code"+i).getText();
    			 
				if(com_type_code == ""){
    				 
    				 $.ligerDialog.error('请输入条件'+i+'部件代码');
    				 
    				 return false;
    				 
    			 }
    			 
				 if(com_type_name == ""){
    				 
    				 $.ligerDialog.error('请输入条件'+i+'部件名称');
    				 
    				 return false;
    				 
    			 }
				 if(para_code == ""){
    				 
    				 $.ligerDialog.error('请选择条件'+i+'的下拉框内容');
    				 
    				 return false;
    				 
    			 }
    			 
    			 where_name = where_name  + com_type_name+ ",";
    			 
    			 para_value = para_value + "input;" + com_type_name + ";" +com_type_code + ";" + para_code+";" +para_name+ "#" ;
    			 
    		 }
    		 
			if(com_type_nature == 'date'){
    			 
 				var com_type_name = $("#com_type_name"+i).val();
    			 
    			var com_type_code = $("#com_type_code"+i).val();
    			 
				if(com_type_code == ""){
    				 
    				 $.ligerDialog.error('请输入条件'+i+'部件代码');
    				 
    				 return false;
    				 
    			 }
    			 
    			 if(com_type_name == ""){
    				 
    				 $.ligerDialog.error('请输入条件'+i+'部件名称');
    				 
    				 return false;
    				 
    			 }

    			 //where_name = where_name + $("#com_type_name"+i).val() + ",";
    			 
    			 //para_value = para_value + "date;" + com_type_name + ";" +com_type_code + ";" + para_code+ ";" +para_name+ "#" ;
    			 
				where_name = where_name  + com_type_name + ",";
    			 
				para_value = para_value  + "date;" + com_type_name + ";" +com_type_code+ "#";
    			 
    		 }
    		 
    	 }
    	 
    	 //年,科室,月
    	 //alert(where_name.substring(0, where_name.length-1));
    	 //text;年;year#input;科室;dept;001;科室编码#date;月;month
    	 //alert(para_value.substring(0, para_value.length-1));
    	 
    	 
    	 
    	 parent.$("#para_name").val(where_name.substring(0, where_name.length-1));
    	 
    	 parent.$("#para_value").val(para_value.substring(0, para_value.length-1));
    	 
    	 
    	 
    	 dialog.close();
        
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
     //$("form").ligerForm();
 }       
   
    function savePrmComType(){ 
        //if($("form").valid()){
            save(); 
        //}
   }
    </script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	
	<form name="form1" method="post" id="form1">
		<table cellpadding="0" cellspacing="0" class="l-table-edit">
		
			<tr>
				 <td align="left" class="l-table-edit-td">
					<input type="button" class="liger-button" id="amt" style="width:120px;" value="添加文本框"/>
				</td>
				<td align="left" class="l-table-edit-td">
					<input type="button" class="liger-button" id="amsb" style="width:120px;" value="添加下拉框"/>
				</td>
				<td align="left" class="l-table-edit-td">
					<input type="button" class="liger-button" id="amdfb" style="width:120px;" value="添加日期框"/>
				</td>
				<!-- <td align="left" class="l-table-edit-td">
					<input type="button" class="liger-button" id="del" onClick="delTr();" value="删除"/>
				</td> -->
			</tr>
		</table>	
		<table cellpadding="0" cellspacing="0" class="l-table-edit"  id="iw">

					
		</table>
	</form>

</body>
</html>