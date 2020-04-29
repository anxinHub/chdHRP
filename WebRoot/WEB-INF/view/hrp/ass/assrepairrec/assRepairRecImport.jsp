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
					          
					url : "readAssRepairRecFiles.do?isCheck=false",    
					
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
				{ display: '维修记录ID', name: 'repair_rec_id', align: 'left',editor:{type:'text'}},
				{ display: '统计年度', name: 'ass_year', align: 'left',editor:{type:'text'}},
				{ display: '统计月份', name: 'ass_month', align: 'left',editor:{type:'text'}},
				{ display: '维修记录号', name: 'repair_rec_no', align: 'left',editor:{type:'text'}},
				{ display: '维修部门ID', name: 'fixed_dept_id', align: 'left',editor:{type:'text'}},
				{ display: '维修部门NO', name: 'fixed_dept_no', align: 'left',editor:{type:'text'}},
				{ display: '资产性质', name: 'ass_nature', align: 'left',editor:{type:'text'}},
				{ display: '资产卡片号', name: 'ass_card_no', align: 'left',editor:{type:'text'}},
				{ display: '申请单号', name: 'apply_no', align: 'left',editor:{type:'text'}},
				{ display: '是否内部维修', name: 'is_inner', align: 'left',editor:{type:'text'}},
				{ display: '故障级别', name: 'trouble_level', align: 'left',editor:{type:'text'}},
				{ display: '维修级别', name: 'repair_level', align: 'left',editor:{type:'text'}},
				{ display: '维修日期', name: 'repair_date', align: 'left',editor:{type:'text'}},
				{ display: '取件日期', name: 'receive_date', align: 'left',editor:{type:'text'}},
				{ display: '维修工程师', name: 'repair_engr', align: 'left',editor:{type:'text'}},
				{ display: '维修工时', name: 'repair_hours', align: 'left',editor:{type:'text'}},
				{ display: '是否合同', name: 'is_contract', align: 'left',editor:{type:'text'}},
				{ display: '合同号', name: 'contract_no', align: 'left',editor:{type:'text'}},
				{ display: '维修费用', name: 'repair_money', align: 'left',editor:{type:'text'}},
				{ display: '其他费用', name: 'other_money', align: 'left',editor:{type:'text'}},
				{ display: '状态', name: 'state', align: 'left',editor:{type:'text'}},
				{ display: '制单人', name: 'create_emp', align: 'left',editor:{type:'text'}},
				{ display: '制单日期', name: 'create_date', align: 'left',editor:{type:'text'}},
				{ display: '审核人', name: 'audit_emp', align: 'left',editor:{type:'text'}},
				{ display: '检修说明', name: 'repair_desc', align: 'left',editor:{type:'text'}}
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
		ajaxJsonObjectByUrl("addBatchAssRepairRec.do?isCheck=false",{ParamVo : JSON.stringify(data)},function (responseData){
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
