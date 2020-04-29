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
					          
					url : "readBudgAssDictFiles.do?isCheck=false",    
					
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
				{ display: '资产编码', name: 'ass_code', align: 'left',editor:{type:'text'}},
				{ display: '资产名称', name: 'ass_name', align: 'left',editor:{type:'text'}},
				{ display: '资产分类编码', name: 'ass_type_code', align: 'left',editor:{type:'text'}},
				{ display: '财务分类编码', name: 'acc_type_code', align: 'left',editor:{type:'text'}},
				{ display: '单位', name: 'ass_unit', align: 'left',editor:{type:'text'}},
				{ display: '是否计量', name: 'is_measure', align: 'left',editor:{type:'text'}},
				{ display: '是否折旧', name: 'is_depre', align: 'left',editor:{type:'text'}},
				{ display: '折旧方法编码', name: 'ass_depre_code', align: 'left',editor:{type:'text'}},
				{ display: '折旧年限', name: 'depre_years', align: 'left',editor:{type:'text'}},
				{ display: '是否停用', name: 'is_stop', align: 'left',editor:{type:'text'}},
				{ display: '规格', name: 'ass_spec', align: 'left',editor:{type:'text'}},
				{ display: '型号', name: 'ass_model', align: 'left',editor:{type:'text'}},
				{ display: '生产厂商编码', name: 'fac_code', align: 'left',editor:{type:'text'}},
			 	{ display: '生产厂商名称', name: 'fac_name', align: 'left',editor:{type:'text'}}, 
				{ display: '主要供应商编码', name: 'ven_code', align: 'left',editor:{type:'text'}},
			 	{ display: '主要供应商名称', name: 'ven_name', align: 'left',editor:{type:'text'}}, 
				{ display: '资产用途', name: 'usage_code', align: 'left',editor:{type:'text'}},
				{ display: '国标码', name: 'gb_code', align: 'left',editor:{type:'text'}},
				{ display: '错误说明', name: 'error_type', align: 'left',width: '10%',
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
		ajaxJsonObjectByUrl("addBatchAssDict.do?isCheck=false",{ParamVo : JSON.stringify(data)},function (responseData){
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
