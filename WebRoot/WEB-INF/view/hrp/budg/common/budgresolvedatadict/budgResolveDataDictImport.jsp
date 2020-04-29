<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/static_resource.jsp">
<jsp:param value="grid,dialog,plupload" name="plugins" />
</jsp:include>
<script type="text/javascript">
		var grid;
		var fileName;
		var budg_level ='${budg_level}';
		var dialog = frameElement.dialog;
		$(function(){
		loadHead(null);
		function plupload(){  
			
			$("#uploader").pluploadQueue({    
				       
					runtimes : 'flash,html5,gears,browserplus,silverlight,html4', 
					          
					url : "readBudgResolveDataDictFiles.do?isCheck=false&budg_level="+budg_level,    
					
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
									
									if(json.state == "false"){
										
										$("#uploader").css("display","none");
										
										$("#maingrid").css("display","block");
										
										grid.addRows(json.Rows);
										
									}else{
											
										$("#uploader").css("display","block");
										
										$("#maingrid").css("display","none");
										
										plupload();
										
										var parentFrameName = parent.$.etDialog.getFrameName('key');
								        var parentWindow = parent.window[parentFrameName];
								        
								        parentWindow.grid.addRows(json.Rows);
										
										var curIndex = parent.$.etDialog.getFrameIndex(window.name);
								        parent.$.etDialog.close(curIndex);
									
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
			if(budg_level == '02'){
	    		 grid = $("#maingrid").etGrid({
	            	 columns: [ 
	    				{ display: '月份', name: 'month', align: 'left',width:'20%', 
	    					 
	    					 },
	    			    { display: '数值', name: 'value', align: 'right',width:'30%',
	    						 render:function(ui){
	    							 if(ui.rowData.value){
	    								 return formatNumber(ui.rowData.value,2,1);
	    							 }
	    						 }
	    				},
						{ display: '错误说明', name: 'error_type', align: 'left',width: '48%',
							render:function(ui){
								if(ui.rowData.error_type){
									return "<span style='color:red'>"+ui.rowData.error_type+"</span>";
								}
								
							}
						}
	                     ],  	
	                     dataModel:{
	                    	 method:'POST',
	                    	 location:'remote',
	                    	 url:'',
	                    	 recIndx: 'month' //必填 且该列不能为空  
	                    },
	                    usePager:true,width: '100%',inWindowHeight:true, height: '100%',checkbox: true,
	             });
	    	 }else if(budg_level == '03'){
	    		 grid = $("#maingrid").etGrid({
	            	 columns: [ 
	    				{ display: '科室编码', name: 'dept_code', align: 'left',width:'15%', 
	    					 },
	    				{ display: '科室名称', name: 'dept_name', align: 'left',width:'20%', 
	    					 },
	    			    { display: '数值', name: 'value', align: 'right',width:'20%',
	    						 render:function(ui){
	    							 if(ui.rowData.value){
	    								 return formatNumber(ui.rowData.value,2,1);
	    							 }
	    						 }
	    				},
						{ display: '错误说明', name: 'error_type', align: 'left',width: '42%',
							render:function(ui){
								if(ui.rowData.error_type){
									return "<span style='color:red'>"+ui.rowData.error_type+"</span>";
								}
								
							}
						}
	                     ],  	
	                     dataModel:{
	                    	 method:'POST',
	                    	 location:'remote',
	                    	 url:'',
	                    	 recIndx: 'dept_code' //必填 且该列不能为空  
	                    },
	                    usePager:true,width: '100%',inWindowHeight:true, height: '100%',checkbox: true,
	             });
	    	 }else if(budg_level == '04'){
	    		 grid = $("#maingrid").etGrid({
	            	 columns: [ 
	    				{ display: '月份', name: 'month', align: 'left',width:'10%',},
	    				{ display: '科室', name: 'dept_name', align: 'left',width:'25%',},
	    				{ display: '数值', name: 'value', align: 'right',width:'20%',
	    						 render:function(ui){
	    							 if(ui.rowData.value){
	    								 return formatNumber(ui.rowData.value,2,1);
	    							 }
	    						 }
	    				},
						{ display: '错误说明', name: 'error_type', align: 'left',width: '42%',
							render:function(ui){
								if(ui.rowData.error_type){
									return "<span style='color:red'>"+ui.rowData.error_type+"</span>";
								}
								
							}
						}
	                     ],  	
	                     dataModel:{
	                    	 method:'POST',
	                    	 location:'remote',
	                    	 url:'',
	                    	 recIndx: 'month' //必填 且该列不能为空  
	                    },
	                    usePager:true,width: '100%',inWindowHeight:true, height: '100%',checkbox: true,
	             });
	    	 }
		}
		
</script>
</head>

<body>
<div id="pageloading" class="l-loading" style="display: none"></div>
<div style="width:775px;margin:0px auto;">  
<div id="uploader"></div>  
</div>
<div id="maingrid" style="display: none ; padding-top: 20px" ></div>  
</body>
</html>
