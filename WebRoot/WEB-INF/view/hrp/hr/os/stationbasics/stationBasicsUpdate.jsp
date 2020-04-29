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
	$(function() {
		var from=getFromField({ui:'et',group_id:'${group_id}',hos_id:'${hos_id}',formTable:['HR_STATION_BASICS']})


	   	 $('.form_content').html(''); //初始化前清空
	   	 $('.form_content').height(650);
	  	var arr = new Array();
		var option=from.fieldItems;
	     $(${data}).each(function () {
	         var rowdata = this;
	         Object.keys(rowdata).forEach(function(key){
	       		 arr.push({name: key,value: rowdata[key]})
	       		 }
	     )
	    

		}); 
		 for (var i=0;i<arr.length;i++){
	  		
	  		var fieldItems;
	  		
	  		var name=arr[i].name;
	  		
	  		for(var j=0;j<option.length;j++){
	  			
	  			var optionName=option[j].id;
	  			
	  			if(name==optionName){
	  			if(option[j].type=='select'){
	   				option[j].OPTIONS.defaultValue=arr[i].value;;

	  			}else{
	  				option[j].value=arr[i].value;
	  			}	
	  			}
	  		}
	  		
	  	}
	   	 form = $('.form_content').etForm(from);
	   	$("#station_code").attr('disabled', true);//职工编码不能修改
	                         form.initWidget();
	                         validate = form.initValidate();
		
	});

	function save() {

 	   /* var formPara={
 			   station_code: $("#station_code").val(),
	                 station_name : $("#station_name").val(),
	                 technical_type: liger.get("technical_type").getValue(),
	                 station_type : liger.get("station_type").getValue(),
	                 station_nature: liger.get("station_nature").getValue(),
	                  is_stop:liger.get("is_stop").getValue(),
	                  note:$("#note").val(),
	            	tab_code:'HR_STATION_BASICS'
         };
 	   ajaxJsonObjectByUrl("updateStationBasics.do", formPara, function(
					responseData) {
		   if(responseData.state=="true"){
				
                parent.query();
            }
			}); */
			   if (!validate.test()) {return;}
 	  var formData = form.getFormData();
	     formData.append('tab_code', 'HR_STATION_BASICS');
	     ajaxPostFormData({
       url: "updateStationBasics.do",
       data: formData,
       dataType: 'json',
       success: function (data) {
           if (data.state == 'true') {
        	   parentFrameUse().query();
               dialog.close();
           } else {
               $.etDialog.error(data.error)
           }
       }
	     })
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
