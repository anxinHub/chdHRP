<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
 <jsp:include page="${path}/resource.jsp">
            <jsp:param value="select, datepicker, checkbox, grid, tab, tree, hr, dialog, form, validate, upload, pageOffice, jquery_print" name="plugins" />
        </jsp:include>
<script type="text/javascript">
	var dataFormat;
	var first_length=0;
	 var dialog = frameElement.dialog; //调用页面的dialog对象(ligerui对象)
	 var dialogData = dialog.get('data');//获取data参数
	$(function() {
		var from=getFromField({ui:'et',group_id:'${group_id}',hos_id:'${hos_id}',formTable:['HR_EMP_CONTRACT']})
        

   	 $('.form_content').html(''); //初始化前清空
   	 $('.form_content').height(450);
     $(data).each(function () {
         var rowdata = this;
         Object.keys(rowdata).forEach(function(key){

       		 if(rowdata[key+"_name"]){
       			 arr.push({name: key,value: rowdata[key],text:rowdata[key+"_name"]}) 
       			 
       		 }else{
       		 arr.push({name: key,value: rowdata[key]})}
     })
    

	}); 
	 for (var i=0;i<arr.length;i++){
  		
  		var fieldItems;
  		
  		var name=arr[i].name;
  		
  		for(var j=0;j<option.length;j++){
  			
  			var optionName=option[j].id;
  			
  			if(name==optionName){
  			if(option[j].type=='select'){
  				/*  if(optionName=='dept_id'){
  					option[j].OPTIONS.defaultOption={id: data.dept_id+"@"+data.dept_no, text:arr[i].text };
  				}  */
  				//option[j].OPTIONS.defaultValue=arr[i].value;
   				option[j].OPTIONS.defaultOption={id:arr[i].value,text:arr[i].text};

  			}else{
  				option[j].value=arr[i].value;
  			}	
  			}
  		}
  		
  	}
   	 form = $('.form_content').etForm(from);
	      
                         form.initWidget();
                         validate = form.initValidate();
	});
	 
	function save() {

		
	/* 	   var formPara={
				     contract_id:dialogData.param[0].contract_id,
 			     emp_id: liger.get("emp_id").getValue(),
	                 dept_id : liger.get("dept_id").getValue().split('@')[0],
	                 dept_no : liger.get("dept_id").getValue().split('@')[1] ,
	                 form_code: liger.get("form_code").getValue(),
	                 contract_years: $("#contract_years").val(),
	                 contract_code: $("#contract_code").val(),
	                 contract_type : liger.get("contract_type").getValue(),
	                 status: liger.get("status").getValue(),
	                 start_date:$("#start_date").val(),
	                 trial_date:$("#trial_date").val(),
	                 note:$("#note").val(),
	                 change_type:'续签',
	            	 tab_code: 'HR_EMP_CONTRACT'
         };
 	   ajaxJsonObjectByUrl("updateContract.do", formPara, function(
					responseData) {
 		   if(responseData.state=="true"){
 				
                 parent.query();
                 dialog.close();
             }
			}); */
 	   

	     var formData = form.getFormData();
	     formData.append('tab_code', 'HR_EMP_CONTRACT');
	     formData.append('state', '02');
	     ajaxPostFormData({
           url: "updateContract.do",
           data: formData,
           dataType: 'json',
           success: function (data) {
               if (data.state == 'true') {
                   parent.query();
                   dialog.close();
               } else {
                   $.etDialog.error(data.error)
               }
           }
       });
	}

	
	function saveData() {

			save();
		
	}
	
		
	
</script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	  <div id="base_space" class='base_space'>
                  <!--   <div class='file_content'>
                        <div id="base_file"></div>
                    </div> -->
                    <div class='form_content' style="width:100%;overflow-x:auto"></div>
                    
                </div>
               

</body>
</html>
