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
					          
					url : "readAccLederRelaFiles.do?isCheck=false&subj_id="+'${subj_id}'+"&group_id="+'${group_id}'+"&hos_id="+'${hos_id}'+"&copy_code="+'${copy_code}'+"&acc_year="+'${acc_year}',    
					
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
			
			var columns = "";
	    	
		 	 $.post("getSubjItemTitle.do?isCheck=false",{'subj_id':'${subj_id}'},function(data){
		 			
		 		 
	 			if(data[0].check_type_name1 != null && data[0].check_type_name1 != ""){
	 				
	 				columns = columns + "{ display: '"+data[0].check_type_name1+"编码', name: 'check1_code',align: 'center'"
						
	 					+"},";
	 				columns = columns + "{ display: '"+data[0].check_type_name1+"名称', name: 'check1_name',align: 'center'"
					
 					+"},";
	 			}
	 			
	 			if(data[0].check_type_name2 != null && data[0].check_type_name2 != ""){
	 				
	 				columns = columns + "{ display: '"+data[0].check_type_name2+"编码', name: 'check2_code', align: 'center'"
					
						+"},";
	 				columns = columns + "{ display: '"+data[0].check_type_name2+"名称', name: 'check2_name', align: 'center'"
					
					+"},";
	 			}
	 			
	 			if(data[0].check_type_name3 != null && data[0].check_type_name3 != ""){
	 				
	 				columns = columns + "{ display: '"+data[0].check_type_name3+"编码', name: 'check3_code', align: 'center'"
					
						+"},";
	 				columns = columns + "{ display: '"+data[0].check_type_name3+"名称', name: 'check3_name', align: 'center'"
					
					+"},";
	 			}
	 			
	 			if(data[0].check_type_name4 != null && data[0].check_type_name4 != ""){
	 			
	 				columns = columns + "{ display: '"+data[0].check_type_name4+"编码', name: 'check4_code',align: 'center'"
					
						+"},";
	 				columns = columns + "{ display: '"+data[0].check_type_name4+"名称', name: 'check4_name',align: 'center'"
					
					+"},";
	 			}
		 			
		 			columns = columns + "{ display: '年初余额', name: 'bal_os', align: 'right'},"
	                
		 			+"{ display: '累计借方', name: 'sum_od', align: 'right'},"
	                
		 			+"{ display: '累计贷方', name: 'sum_oc', align: 'right'},"
		 			
		 			+"{ display: '期初余额', name: 'end_os', align: 'right'},"
	                 
		 			+"{ display: '错误类型', name: 'error_type', align: 'right'}";
	                 
		 			grid = $("#maingrid").ligerGrid({
		 				
		 	           columns: eval("["+columns+"]"),
		 	           
		 	                     dataAction: 'server',dataType: 'server',usePager:false,
		 	                     
		 	                     width: '100%', height: '100%', checkbox: true,rownumbers:false,
		 	                     
		 	                     selectRowButtonOnly:true
		 	                  
		 			});
		 			
		 			gridManager = $("#maingrid").ligerGetGridManager();
		 	
		 	 },"json"); 
		 	
		}
		function itemclick(item){ 
		if(item.id)
		{
		switch (item.id)
		{
		case "save":
		var data = gridManager.getData();
		ajaxJsonObjectByUrl("addBatchAssBidMain.do?isCheck=false",{ParamVo : JSON.stringify(data)},function (responseData){
		if(responseData.state=="true"){
		$("#uploader").css("display","block");
		$("#maingrid").css("display","none");
		parent.query();
		$.ligerDialog.confirm('导入成功！是否关闭当前页面', function (yes) {
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
