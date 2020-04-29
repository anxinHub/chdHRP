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
					          
					url : "readAssEquserecordFiles.do?isCheck=false",    
					
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
				{ display: 'ID', name: 'rowid', align: 'left',editor:{type:'text'}},
				{ display: '01：机组:02：设备', name: 'ur_fromtype', align: 'left',editor:{type:'text'}},
				{ display: 'urEqGroup', name: 'ur_eq_group', align: 'left',editor:{type:'text'}},
				{ display: '使用日期', name: 'use_date', align: 'left',editor:{type:'text'}},
				{ display: '开始使用时间', name: 'start_time', align: 'left',editor:{type:'text'}},
				{ display: '结束使用日期', name: 'end_date', align: 'left',editor:{type:'text'}},
				{ display: '结束使用时间', name: 'end_time', align: 'left',editor:{type:'text'}},
				{ display: '工作量', name: 'work_load_num', align: 'left',editor:{type:'text'}},
				{ display: 'unitCode', name: 'unit_code', align: 'left',editor:{type:'text'}},
				{ display: 'deptCode', name: 'dept_code', align: 'left',editor:{type:'text'}},
				{ display: '使用患者', name: 'patient_id', align: 'left',editor:{type:'text'}},
				{ display: '性别', name: 'patient_sex', align: 'left',editor:{type:'text'}},
				{ display: '年龄', name: 'patient_age', align: 'left',editor:{type:'text'}},
				{ display: '姓名', name: 'patient_name', align: 'left',editor:{type:'text'}},
				{ display: '单价', name: 'price', align: 'left',editor:{type:'text'}},
				{ display: '收取费用', name: 'total_fee', align: 'left',editor:{type:'text'}},
				{ display: '为空表示按标准配备数量或者没有单独收费， 不为空表示特殊的单独收费项数量', name: 'alone_pay_num', align: 'left',editor:{type:'text'}},
				{ display: '年', name: 'year', align: 'left',editor:{type:'text'}},
				{ display: '月', name: 'month', align: 'left',editor:{type:'text'}},
				{ display: '用自增ID', name: 'charge_kind_id', align: 'left',editor:{type:'text'}},
				{ display: 'chargeItemId', name: 'charge_item_id', align: 'left',editor:{type:'text'}},
				{ display: '仪器来源编码（仪器来源类型：3.设备效益分析）COST_BUSI_SOUREC_DICT', name: 'busi_data_source_code', align: 'left',editor:{type:'text'}},
				{ display: '扩展ID', name: 'ex_id', align: 'left',editor:{type:'text'}},
				{ display: '手工录入标志', name: 'is_input_flag', align: 'left',editor:{type:'text'}},
				{ display: '状态（0:新增 1:提交 2:审核 3:作废）', name: 'status', align: 'left',editor:{type:'text'}},
				{ display: '有效标记', name: 'status', align: 'left',editor:{type:'text'}},
				{ display: '备注', name: 'remark', align: 'left',editor:{type:'text'}},
				{ display: '记录医嘱OrderID', name: 'doctor_order_id', align: 'left',editor:{type:'text'}},
				{ display: '记录操作员（编码_名字）', name: 'operator', align: 'left',editor:{type:'text'}},
				{ display: '阳性标志', name: 'positive_flag', align: 'left',editor:{type:'text'}},
				{ display: '用于标本号/医技号', name: 'sample_no', align: 'left',editor:{type:'text'}},
				{ display: '用于曝光次数', name: 'exposure_num', align: 'left',editor:{type:'text'}},
				{ display: '开始日期', name: 'start_date', align: 'left',editor:{type:'text'}},
				{ display: '其他信息', name: 'other_info', align: 'left',editor:{type:'text'}},
				{ display: '新增人（SYS_USER）', name: 'add_user', align: 'left',editor:{type:'text'}},
				{ display: '新增日期', name: 'add_date', align: 'left',editor:{type:'text'}},
				{ display: '新增时间', name: 'add_time', align: 'left',editor:{type:'text'}},
				{ display: '更新人（SYS_USER）', name: 'update_user', align: 'left',editor:{type:'text'}},
				{ display: '更新日期', name: 'update_date', align: 'left',editor:{type:'text'}},
				{ display: '更新时间', name: 'update_time', align: 'left',editor:{type:'text'}},
				{ display: '取消日期', name: 'cancel_date', align: 'left',editor:{type:'text'}},
				{ display: '取消时间', name: 'cancel_time', align: 'left',editor:{type:'text'}},
				{ display: '取消人（SYS_USER）', name: 'cancel_user', align: 'left',editor:{type:'text'}},
				{ display: '提交日期', name: 'submit_date', align: 'left',editor:{type:'text'}},
				{ display: '提交时间', name: 'submit_time', align: 'left',editor:{type:'text'}},
				{ display: '提交人（SYS_USER）', name: 'submit_user', align: 'left',editor:{type:'text'}},
				{ display: '审核日期', name: 'audit_date', align: 'left',editor:{type:'text'}},
				{ display: '审核时间', name: 'audit_time', align: 'left',editor:{type:'text'}},
				{ display: '审核人（SYS_USER）', name: 'audit_user', align: 'left',editor:{type:'text'}}
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
		ajaxJsonObjectByUrl("addBatchAssEquserecord.do?isCheck=false",{ParamVo : JSON.stringify(data)},function (responseData){
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
