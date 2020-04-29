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
		var from=getFromField({ui:'et',group_id:'${group_id}',hos_id:'${hos_id}',formTable:['HR_STATION_MANAGE']})
        

	   	 $('.form_content').html(''); //初始化前清空
	   	 $('.form_content').height(650);
	  
	   	 form = $('.form_content').etForm(from);
		      
	                         form.initWidget();
	                         validate = form.initValidate();
		
	});

	function save() {
		
		   if (!validate.test()) {return;}
    	    var formData = form.getFormData();
    	    if(formData.get('dept_id')!=null){
              
                	 var dept_id=formData.get('dept_id').split('@')[0];
                     var dept_no=formData.get('dept_id').split('@')[1];
                     formData.delete('dept_id');
                     formData.append('dept_id', dept_id);
                     formData.append('dept_no',dept_no);
                
                }
   	     formData.append('tab_code', 'HR_STATION_MANAGE');
   	     ajaxPostFormData({
             url: "addStationManage.do",
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
