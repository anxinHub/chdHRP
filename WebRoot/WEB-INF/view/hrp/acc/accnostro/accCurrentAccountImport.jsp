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
					          
					url : "readAccVouchCheckRelaFiles.do?isCheck=false&subj_code="+'${subj_code}'+"&group_id="+'${group_id}'+"&hos_id="+'${hos_id}'+"&copy_code="+'${copy_code}'+"&subj_dire="+'${subj_dire}',
					
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
		                     { display: '科目编码', name: 'subj_code', align: 'left'
							 },
							 { display: '科目名称', name: 'subj_name', align: 'left'
							 },
							 { display: '方向', name: 'subj_dire', align: 'left',
		                    	 render:function(rowdata,index,value){
		                    		 
		                    		 if(rowdata.subj_dire==0){
		                    			 return "借";
		                    		 }
		                    		 return "贷";
		                    	 }
							 },
							 { display: '币种', name: 'cur_code', align: 'left'
							 },
		                     { display: '年初余额', name: 'bal_os', align: 'left'
							 },
		                     { display: '累计借方', name: 'sum_od', align: 'left'
							 },
		                     { display: '累计贷方', name: 'sum_oc', align: 'left'
							 },
		                     { display: '期初余额', name: 'end_os', align: 'left'
							 },
		                     { display: '错误类型', name: 'error_type', align: 'right'
							 }
		                     ],
		                     dataAction: 'server',dataType: 'server',usePager:true,
		                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
		                     selectRowButtonOnly:true//heightDiff: -10,
		                     
		                   });

		        gridManager = $("#maingrid").ligerGetGridManager();
		 	
		}
	function itemclick(item){ 
		if(item.id){
		switch (item.id){
			case "save":
				var data = gridManager.getData();
				ajaxJsonObjectByUrl("addBatchAssBidMain.do?isCheck=false",{ParamVo : JSON.stringify(data)},function (responseData){
					if(responseData.state=="true"){
							$("#uploader").css("display","block");
							$("#maingrid").css("display","none");
			
							igerDialog.confirm('导入成功！是否关闭当前页面', function (yes) {
								if(yes==true){
								dialog.close();
								}else{
								document.location.reload();
								}
							});
					}else if(responseData.state=="err"){
					$("#uploader").css("display","block");
					$("#maingrid").css("display","none");
					plupload();
					document.location.reload();
					}else{
					grid.loadData(responseData);
					}
				});
				return;
			case "delete":  
				var data = gridManager.getCheckedRows();
				if (data.length == 0){
					$.ligerDialog.error('请选择行');
				}else{
					var data = gridManager.deleteSelectedRow();
				}
					return;
				}   
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
