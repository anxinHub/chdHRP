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
					          
					url : "readAssEqiuserecordqueueFiles.do?isCheck=false",    
					
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
				{ display: 'URQ_RowID', name: 'urq_rowid', align: 'left',editor:{type:'text'}},
				{ display: '仪器来源编码（仪器来源类型：3.设备效益分析）COST_BUSI_SOUREC_DICT', name: 'busi_data_source_code', align: 'left',editor:{type:'text'}},
				{ display: '外部系统设备ID', name: 'urq_exdevicecode', align: 'left',editor:{type:'text'}},
				{ display: '外部系统项目ID', name: 'urq_exitemcode', align: 'left',editor:{type:'text'}},
				{ display: 'urqExitemdcode', name: 'urq_exitemdcode', align: 'left',editor:{type:'text'}},
				{ display: '外部系统业务ID', name: 'urq_exbusscode', align: 'left',editor:{type:'text'}},
				{ display: '外部系统科室ID', name: 'urq_exuseloccode', align: 'left',editor:{type:'text'}},
				{ display: '使用日期', name: 'urq_usedate', align: 'left',editor:{type:'text'}},
				{ display: '工作量', name: 'urq_workloadnum', align: 'left',editor:{type:'text'}},
				{ display: '单价', name: 'urq_price', align: 'left',editor:{type:'text'}},
				{ display: '总金额', name: 'urq_totalfee', align: 'left',editor:{type:'text'}},
				{ display: '为空表示按标准配备数量或者没有单独收费， 不为空表示特殊的单独收费项数量', name: 'urq_alonepaynum', align: 'left',editor:{type:'text'}},
				{ display: '使用患者ID', name: 'urq_patientid', align: 'left',editor:{type:'text'}},
				{ display: '记录患者姓名', name: 'urq_patientname', align: 'left',editor:{type:'text'}},
				{ display: '记录患者性别', name: 'urq_patientsex', align: 'left',editor:{type:'text'}},
				{ display: '记录患者年龄', name: 'urq_patientage', align: 'left',editor:{type:'text'}},
				{ display: '开始使用日期', name: 'urq_startdate', align: 'left',editor:{type:'text'}},
				{ display: '开始使用时间', name: 'urq_starttime', align: 'left',editor:{type:'text'}},
				{ display: '结束使用日期', name: 'urq_enddate', align: 'left',editor:{type:'text'}},
				{ display: '结束使用时间', name: 'urq_endtime', align: 'left',editor:{type:'text'}},
				{ display: '操作员', name: 'urq_operator', align: 'left',editor:{type:'text'}},
				{ display: '其他信息', name: 'urq_otherinfo', align: 'left',editor:{type:'text'}},
				{ display: '取消标志', name: 'urq_cancelflag', align: 'left',editor:{type:'text'}},
				{ display: '备注', name: 'urq_remark', align: 'left',editor:{type:'text'}},
				{ display: '记录医嘱OrderID', name: 'urq_doctororderid', align: 'left',editor:{type:'text'}},
				{ display: '用做阳性标志', name: 'urq_positiveflag', align: 'left',editor:{type:'text'}},
				{ display: '标本号/检查号', name: 'urq_sampleno', align: 'left',editor:{type:'text'}},
				{ display: '曝光次数', name: 'urq_exposurenum', align: 'left',editor:{type:'text'}},
				{ display: '新增日期', name: 'urq_adddate', align: 'left',editor:{type:'text'}},
				{ display: '新增时间', name: 'urq_addtime', align: 'left',editor:{type:'text'}},
				{ display: '完成日期', name: 'urq_finishdate', align: 'left',editor:{type:'text'}},
				{ display: '完成时间', name: 'urq_finishtime', align: 'left',editor:{type:'text'}},
				{ display: '最后执行日期', name: 'urq_lastexecutedate', align: 'left',editor:{type:'text'}},
				{ display: '最后执行时间', name: 'urq_lastexecutetime', align: 'left',editor:{type:'text'}},
				{ display: '执行次数', name: 'urq_executetimes', align: 'left',editor:{type:'text'}},
				{ display: '状态', name: 'urq_status', align: 'left',editor:{type:'text'}}
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
		ajaxJsonObjectByUrl("addBatchAssEqiuserecordqueue.do?isCheck=false",{ParamVo : JSON.stringify(data)},function (responseData){
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
