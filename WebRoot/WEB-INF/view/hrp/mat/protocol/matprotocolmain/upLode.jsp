<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- <html xmlns="http://www.w3.org/1999/xhtml"> -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc.jsp"/>
<script type="text/javascript">
		var data = ${data};
		var fileName;
		var dialog = frameElement.dialog;
		$(function(){
		function plupload(){  
			
			$("#uploader").pluploadQueue({    
				       
					runtimes : 'flash,html5,gears,browserplus,silverlight,html4', 
					          
					url : "upLodeFile.do?isCheck=false&protocol_id=${protocol_id}&file_id=${file_id}",    
					
					chunk_size : '20mb',           
					
					filters : {                
						
					mime_types: [  
					
					{title : "* files", extensions : "*"}
					
					]  
					},  
					flash_swf_url : '<%=path%>/lib/plupload-2.1.3/js/Moxie.swf',       
					          
					silverlight_xap_url : '<%=path%>/lib/plupload-2.1.3/js/Moxie.xap',     
					
					init: {
						
						FileUploaded: function(uploader,file,responseObject) {
							
							var res=responseObject.response;

							var json = eval('(' + res + ')') ;
							 if(json.state == "true"){
								$.ligerDialog.success('上传成功！');
								
								plupload();
							}else{
								$.ligerDialog.success('上传成功,请保存文档数据！');
								
								parent.fileManager.updateCell("file_path",json.file_path,data);
								
								plupload();
							}
								
							plupload();
							
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
