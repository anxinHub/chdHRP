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
					          
					url : "readDeptMonthMedInBudgFiles.do?isCheck=false",    
					
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
				grid = $("#maingrid").ligerGrid({
				columns: [ 
				{ display: '预算年度', name: 'year', align: 'left',width:80,editor:{type:'text'}},
				{ display: '预算科室编码', name: 'dept_code', align: 'left',width:120,editor:{type:'text'}},
				{ display: '预算科目编码', name: 'subj_code', align: 'left',width:150,editor:{type:'text'}},
				{ display: '01月', name: 'm01', align: 'right',width:120,editor:{type:'float'}},
				{ display: '02月', name: 'm02', align: 'right',width:120,editor:{type:'float'}},
				{ display: '03月', name: 'm03', align: 'right',width:120,editor:{type:'float'}},
				{ display: '04月', name: 'm04', align: 'right',width:120,editor:{type:'float'}},
				{ display: '05月', name: 'm05', align: 'right',width:120,editor:{type:'float'}},
				{ display: '06月', name: 'm06', align: 'right',width:120,editor:{type:'float'}},
				{ display: '07月', name: 'm07', align: 'right',width:120,editor:{type:'float'}},
				{ display: '08月', name: 'm08', align: 'right',width:120,editor:{type:'float'}},
				{ display: '09月', name: 'm09', align: 'right',width:120,editor:{type:'float'}},
				{ display: '10月', name: 'm10', align: 'right',width:120,editor:{type:'float'}},
				{ display: '11月', name: 'm11', align: 'right',width:120,editor:{type:'float'}},
				{ display: '12月', name: 'm12', align: 'right',width:120,editor:{type:'float'}},
				{ display: '错误说明', name: 'error_type', align: 'left',width: '35%',
				render:function(rowdata, rowindex, value){
				return "<span style='color:red'>"+rowdata.error_type+"</span>";
				}
				}
				],  
				width : '99%',
				height: '370',
				enabledEdit: true,
				usePager:false,
				rownumbers:true,
				selectRowButtonOnly:true,
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
