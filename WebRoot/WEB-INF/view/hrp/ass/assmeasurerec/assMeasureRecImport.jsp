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
					          
					url : "readAssMeasureRecFiles.do?isCheck=false",    
					
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
				{ display: '计量记录ID', name: 'rec_id', align: 'left',editor:{type:'text'}},
				{ display: '计量记录编号', name: 'seq_no', align: 'left',editor:{type:'text'}},
				{ display: '计划ID', name: 'plan_id', align: 'left',editor:{type:'text'}},
				{ display: '计量证书号', name: 'cert_no', align: 'left',editor:{type:'text'}},
				{ display: '资产性质', name: 'ass_nature', align: 'left',editor:{type:'text'}},
				{ display: '外部计量员', name: 'outer_measure_engr', align: 'left',editor:{type:'text'}},
				{ display: '内部计量员', name: 'inner_measure_emp', align: 'left',editor:{type:'text'}},
				{ display: '计划计量日期', name: 'plan_measure_date', align: 'left',editor:{type:'text'}},
				{ display: '实际计量日期', name: 'fact_measure_date', align: 'left',editor:{type:'text'}},
				{ display: '预计下次日期', name: 'pre_next_date', align: 'left',editor:{type:'text'}},
				{ display: '计量工时', name: 'measure_hours', align: 'left',editor:{type:'text'}},
				{ display: '其他费用', name: 'other_money', align: 'left',editor:{type:'text'}},
				{ display: '计量费用', name: 'measure_money', align: 'left',editor:{type:'text'}},
				{ display: '计量说明', name: 'measure_memo', align: 'left',editor:{type:'text'}},
				{ display: '检测方式', name: 'measure_kind', align: 'left',editor:{type:'text'}},
				{ display: '内部计量部门ID', name: 'inner_measure_dept_id', align: 'left',editor:{type:'text'}},
				{ display: '内部计量部门NO', name: 'inner_measure_dept_no', align: 'left',editor:{type:'text'}},
				{ display: '外部计量单位', name: 'outer_measure_org', align: 'left',editor:{type:'text'}},
				{ display: '制单人', name: 'create_emp', align: 'left',editor:{type:'text'}},
				{ display: '制单时间', name: 'create_date', align: 'left',editor:{type:'text'}},
				{ display: '审核人', name: 'audit_emp', align: 'left',editor:{type:'text'}},
				{ display: '审核时间', name: 'audit_date', align: 'left',editor:{type:'text'}},
				{ display: '经办人', name: 'deal_emp', align: 'left',editor:{type:'text'}},
				{ display: '计量类别', name: 'measure_type', align: 'left',editor:{type:'text'}},
				{ display: '状态', name: 'state', align: 'left',editor:{type:'text'}},
				{ display: '计量结果', name: 'measure_result', align: 'left',editor:{type:'text'}},
				{ display: '处理意见', name: 'measure_idea', align: 'left',editor:{type:'text'}}
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
		ajaxJsonObjectByUrl("addBatchAssMeasureRec.do?isCheck=false",{ParamVo : JSON.stringify(data)},function (responseData){
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
