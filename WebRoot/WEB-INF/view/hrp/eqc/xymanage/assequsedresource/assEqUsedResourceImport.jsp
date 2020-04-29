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
					          
					url : "readAssEqusedresourceFiles.do?isCheck=false",    
					
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
				{ display: '年', name: 'year', align: 'left',editor:{type:'text'}},
				{ display: '月', name: 'month', align: 'left',editor:{type:'text'}},
				{ display: '01：机组:02：设备', name: 'ur_fromtype', align: 'left',editor:{type:'text'}},
				{ display: 'urEqGroup', name: 'ur_eq_group', align: 'left',editor:{type:'text'}},
				{ display: 'deptCode', name: 'dept_code', align: 'left',editor:{type:'text'}},
				{ display: '代码', name: 'oresource_code', align: 'left',editor:{type:'text'}},
				{ display: '单价', name: 'price', align: 'left',editor:{type:'text'}},
				{ display: 'unitCode', name: 'unit_code', align: 'left',editor:{type:'text'}},
				{ display: '用量', name: 'quantity', align: 'left',editor:{type:'text'}},
				{ display: '金额', name: 'amount', align: 'left',editor:{type:'text'}},
				{ display: '备注', name: 'remark', align: 'left',editor:{type:'text'}},
				{ display: '手工录入标志', name: 'is_input_flag', align: 'left',editor:{type:'text'}},
				{ display: '销售金额', name: 'sale_amount', align: 'left',editor:{type:'text'}},
				{ display: '新增日期', name: 'add_date', align: 'left',editor:{type:'text'}},
				{ display: '新增时间', name: 'add_time', align: 'left',editor:{type:'text'}},
				{ display: '新增人', name: 'add_user', align: 'left',editor:{type:'text'}},
				{ display: '更新日期', name: 'update_date', align: 'left',editor:{type:'text'}},
				{ display: '更新时间', name: 'update_time', align: 'left',editor:{type:'text'}},
				{ display: '更新人', name: 'update_user', align: 'left',editor:{type:'text'}},
				{ display: '状态（0:新增 1:提交 2:审核 3:作废）', name: 'status', align: 'left',editor:{type:'text'}},
				{ display: '提交日期', name: 'submit_date', align: 'left',editor:{type:'text'}},
				{ display: '提交时间', name: 'submit_time', align: 'left',editor:{type:'text'}},
				{ display: '提交人', name: 'submit_user', align: 'left',editor:{type:'text'}},
				{ display: '审核日期', name: 'audit_date', align: 'left',editor:{type:'text'}},
				{ display: '审核时间', name: 'audit_time', align: 'left',editor:{type:'text'}},
				{ display: '审核人', name: 'audit_user', align: 'left',editor:{type:'text'}},
				{ display: '取消日期', name: 'cancel_date', align: 'left',editor:{type:'text'}},
				{ display: '取消时间', name: 'cancel_time', align: 'left',editor:{type:'text'}},
				{ display: '取消人', name: 'cancel_user', align: 'left',editor:{type:'text'}}
				,{ display: '错误说明', name: 'error_type', align: 'left',width: '35%',
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
				toolbar: { items: [
				{text : '保存',
				id : 'save',
				click : itemclick,
				icon : 'add',
				}, 
				{line : true},
				{text : '删除',
				id : 'delete',
				click : itemclick,
				icon : 'delete'},
				{line : true}
				
				]}
				});
				gridManager = $("#maingrid").ligerGetGridManager();
		}
		function itemclick(item){ 
		if(item.id)
		{
		switch (item.id)
		{
		case "save":
		var data = gridManager.getData();
		ajaxJsonObjectByUrl("addBatchAssEqusedresource.do?isCheck=false",{ParamVo : JSON.stringify(data)},function (responseData){
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
