<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% 
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
		var grid;
		var fileName;
		var dialog = frameElement.dialog;
		$(function(){
		loadHead(null);
		function plupload(){  
			$("#uploader").pluploadQueue({    
				       
					runtimes : 'flash,html5,gears,browserplus,silverlight,html4', 
					          
					url : "readMatInvFiles.do?isCheck=false",    
					
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
									//alert(JSON.stringify(res));
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
				{ display: '物资材料编码', name: 'inv_code', align: 'left',width:100,editor:{type:'text'}},
				{ display: '物资材料名称', name: 'inv_name', align: 'left',width:100,editor:{type:'text'}},
				{ display: '别名', name: 'alias', align: 'left',width:100,editor:{type:'text'}},
				{ display: '物资类别编码', name: 'mat_type_code', align: 'left',width:100,editor:{type:'text'}},
				{ display: '物资类别名称', name: 'mat_type_name', align: 'left',width:100,editor:{type:'text'}},
				{ display: '规格型号', name: 'inv_model', align: 'left',width:100,editor:{type:'text'}},
				{ display: '计量单位名称', name: 'unit_name', align: 'left',width:100,editor:{type:'text'}},
				{ display: '生产厂商编码', name: 'fac_code', align: 'left',width:100,editor:{type:'text'}},
				{ display: '生产厂商名称', name: 'fac_name', align: 'left',width:100,editor:{type:'text'}},
				{ display: '摊销方式', name: 'amortize_type', align: 'left',width:100,editor:{type:'text'}},
				{ display: '计价方法', name: 'price_type', align: 'left',width:100,editor:{type:'text'}},
				{ display: '计划价', name: 'plan_price', align: 'left',width:100,editor:{type:'text'}},
				{ display: '加价率', name: 'price_rate', align: 'left',width:100,editor:{type:'text'}},
				{ display: '零售价', name: 'sell_price', align: 'left',width:100,editor:{type:'text'}},
				{ display: '供应商编码', name: 'sup_code', align: 'left',width:100,editor:{type:'text'}},
				{ display: '供应商名称', name: 'sup_name', align: 'left',width:100,editor:{type:'text'}},
				{ display: '是否是唯一供应商', name: 'is_single_ven', align: 'left',width:100,editor:{type:'text'}},
				{ display: '是否批次管理', name: 'is_batch', align: 'left',width:100,editor:{type:'text'}},
				{ display: '是否条码管理', name: 'is_bar', align: 'left',width:100,editor:{type:'text'}},
				{ display: '是否个体条码管理', name: 'is_per_bar', align: 'left',width:100,editor:{type:'text'}},
				{ display: '是否证件管理', name: 'is_cert', align: 'left',width:100,editor:{type:'text'}},
				{ display: '是否保质期管理', name: 'is_quality', align: 'left',width:100,editor:{type:'text'}},
				{ display: '是否灭菌材料', name: 'is_disinfect', align: 'left',width:100,editor:{type:'text'}},
				{ display: '是否专购', name: 'is_com', align: 'left',width:100,editor:{type:'text'}},
				{ display: '是否为耐用品', name: 'is_dura', align: 'left',width:100,editor:{type:'text'}},
				{ display: '是否高值', name: 'is_highvalue', align: 'left',width:100,editor:{type:'text'}},
				{ display: '是否收费', name: 'is_charge', align: 'left',width:100,editor:{type:'text'}},
				{ display: '是否作二级库管理', name: 'is_sec_whg', align: 'left',width:100,editor:{type:'text'}},
				{ display: '是否自制品', name: 'is_shel_make', align: 'left',width:100,editor:{type:'text'}},
				{ display: '启用日期', name: 'sdate', align: 'left',width:100,editor:{type:'text'}},
				{ display: '停用日期', name: 'edate', align: 'left',width:100,editor:{type:'text'}},
				{ display: '品种条码', name: 'bar_code_new', align: 'left',width:100,editor:{type:'text'}},
				{ display: 'ABC分类', name: 'abc_type', align: 'left',width:100,editor:{type:'text'}},
				{ display: '单位重量', name: 'per_weight', align: 'left',width:100,editor:{type:'text'}},
				{ display: '单位体积', name: 'per_volum', align: 'left',width:100,editor:{type:'text'}},
				{ display: '品牌', name: 'brand_name', align: 'left',width:100,editor:{type:'text'}},
				{ display: '代理商', name: 'agent_name', align: 'left',width:100,editor:{type:'text'}},
				{ display: '材料结构', name: 'inv_structure', align: 'left',width:100,editor:{type:'text'}},
				{ display: '材料用途', name: 'inv_usage', align: 'left',width:100,editor:{type:'text'}},
				{ display: '是否在用', name: 'use_state', align: 'left',width:100,editor:{type:'text'}},
				{ display: '备注', name: 'note', align: 'left',width:100,editor:{type:'text'}},
				{ display: '是否中标', name: 'is_bid', align: 'left',width:100,editor:{type:'text'}},
				{ display: '中标日期', name: 'bid_date', align: 'left',width:100,editor:{type:'text'}},
				{ display: '项目中标编码', name: 'bid_code', align: 'left',width:100,editor:{type:'text'}},
				{ display: '是否介入', name: 'is_involved', align: 'left',width:100,editor:{type:'text'}},
				{ display: '是否植入', name: 'is_implant', align: 'left',width:100,editor:{type:'text'}},
				{ display: '储运条件', name: 'stora_tran_cond', align: 'left',width:100,editor:{type:'text'}},
				{ display: '是否零库存管理', name: 'is_zero_store', align: 'left',width:100,editor:{type:'text'}},
				{ display: '计划来源', name: 'source_plan', align: 'left',width:100,editor:{type:'text'}},
				{ display: '是否个体码', name: 'is_inv_bar', align: 'left',width:100,editor:{type:'text'}},
				{ display: '品种码', name: 'is_inv_bar_no', align: 'left',width:100,editor:{type:'text'}},
				{ display: '错误说明', name: 'error_type', align: 'left',width: '35%',
				render:function(rowdata, rowindex, value){
				return "<span style='color:red'>"+rowdata.error_type+"</span>";
				}
				}
				],  
				width : '100%',
				height: '100%',
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
		ajaxJsonObjectByUrl("addBatchMatInv.do?isCheck=false",{ParamVo : JSON.stringify(data)},function (responseData){
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
