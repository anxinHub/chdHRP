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
	var grid;
	
	var fileName;
	
	var dialog = frameElement.dialog;
	
    var para ="";
    
    var sumPara = "";
    
    var wherePara="";
	
	$(function(){
		
		loadHead(null);
		
		function plupload(){  
			
			$("#uploader").pluploadQueue({   
				
		       	runtimes : 'flash,html5,gears,browserplus,silverlight,html4',    
		       	
		       	url : "readAccMedStoreRef.do?isCheck=false",   
		       			
		       	chunk_size : '2mb',   
		       	
		       	filters : {    
           
		           mime_types: [  
		                        
		               {title : "excel files", extensions : "xls,xlsx"}
		               
		           ]  
		       	},  
		       	
		       	flash_swf_url : '<%=path%>/lib/plupload-2.1.3/js/Moxie.swf',  
		       	
			    silverlight_xap_url : '<%=path%>/lib/plupload-2.1.3/js/Moxie.xap',   
			    
		   	  	init: {
		   	  		
			   		FileUploaded: function(uploader,file,responseObject) {
			   			
				   		var res=responseObject.response;
				   		
				   		var json = eval('(' + res + ')');
				   		
						   		 if(json.Rows!= null&&json.Rows!= ""){
						   			 
						   			$("#uploader").css("display","none");
						   			
								   	$("#maingrid").css("display","block");
								   	
								    grid.loadData(json);
								    
						   		}else{
						   			
						   			$.ligerDialog.success('导入成功！');
						   			
						   			$("#uploader").css("display","block");
						   			
						   		   	$("#maingrid").css("display","none");
						   		   	
						   	        plupload();
						   	        
						   	     	parent.query();
						   	     	
						   		}
				   		  }
				   	}
		   	});
		}
		
		plupload();
		
	});
	function getJsonObjLength(jsonObj) {
		
	    var Length = 0;
	    
	    for (var item in jsonObj) {
	    	
	      Length++;
	      
	    }
	    
	    return Length;
	    
	  }
	
	function loadHead() {
		grid = $("#maingrid").ligerGrid(
						{
					columns : [
								 { display: 'HIS库房编码', name: 'his_store_code', align: 'left'},
								 { display: 'HIS库房名称', name: 'his_store_name', align: 'left'},
								 { display: 'HRP库房编码', name: 'hrp_store_code', align: 'left'},
								 { display: 'HRP库房名称', name: 'hrp_store_name', align: 'left'}
	              		],
					width : '99%',
					height: '370',
					usePager:false,
					rownumbers:true,
					selectRowButtonOnly:true
					
						});
		gridManager = $("#maingrid").ligerGetGridManager();
	}
	
    </script>
</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
    <div style="width:1100px;margin:0px auto;">  
        <div id="uploader"></div>  
	</div>
	<div id="maingrid" style="display: none ; padding-top: 20px" ></div>  
</body>
</html>
