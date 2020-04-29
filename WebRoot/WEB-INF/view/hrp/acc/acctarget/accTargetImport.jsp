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
		
		$(function(){
			
		loadHead(null);
		
		function plupload(){  
			
			$("#uploader").pluploadQueue({    
				       
					runtimes : 'flash,html5,gears,browserplus,silverlight,html4', 
					          
					url : "readAccTargetFiles.do?isCheck=false",    
					
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
			
			grid = $("#maingrid").ligerGrid({
		           columns: [ 
		                     { display: '基本指标编码', name: 'target_code', align: 'left'
							 },
							 { display: '基本指标名称', name: 'target_name', align: 'left'
							 },
							 { display: '单位编码', name: 'unit_code', align: 'left'
							 },
		                     { display: '单位名称', name: 'unit_name', align: 'left'
							 },
		                     { display: '是否停用', name: 'is_stop', align: 'left'
							 },
		                     { display: '备注', name: 'note', align: 'left'
							 },
		                     { display: '错误信息', name: 'error_type', align: 'left'
							 }
		                     ],
		                     dataAction: 'server',dataType: 'server',usePager:true,
		                     width: '100%', height: '100%',rownumbers:true,
		                     selectRowButtonOnly:true//heightDiff: -10,
		                     
		                   });

		        gridManager = $("#maingrid").ligerGetGridManager();
		 	
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
