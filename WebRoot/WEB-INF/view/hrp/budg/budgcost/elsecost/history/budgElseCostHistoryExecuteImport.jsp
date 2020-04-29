<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">
		var grid;
		var fileName;
		var dialog = frameElement.dialog;
		$(function(){
		loadHead(null);
		function plupload(){  
			
			$("#uploader").pluploadQueue({    
				       
				runtimes : 'flash,html5,gears,browserplus,silverlight,html4', 
				          
				url : "readBudgElseCostHistoryExecuteFiles.do?isCheck=false",    
				
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
			{ display: '年度', name: 'year', align: 'left',editor:{type:'text'}},
			{ display: '月份', name: 'month', align: 'left',editor:{type:'text'}},
			{ display: '科目编码', name: 'subj_code', align: 'left',editor:{type:'text'}},
			{ display: '科目名称', name: 'subj_name', align: 'left',editor:{type:'text'}},
			{ display: '金额', name: 'amount', align: 'left',editor:{type:'text'}},
			{ display: '说明', name: 'remark', align: 'left',editor:{type:'text'}}
			,{ display: '错误说明', name: 'error_type', align: 'left',width: '35%',
			render:function(rowdata, rowindex, value){
			return "<span style='color:red'>"+rowdata.error_type+"</span>";
			}
			}
			],  
			width : '99%',
			height: '100%',
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
