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
					          
					url : "readBudgAssCardFiles.do?isCheck=false",    
					
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
					{ display: '卡片编号', name: 'ass_card_no', align: 'left',width:100},
					{ display: '资产编码', name: 'ass_code', align: 'left',width:100},
					{ display: '资产名称', name: 'ass_name', align: 'left',width:120},
					{ display: '管理部门编码', name: 'dept_code', align: 'left',width:100},
					{ display: '管理部门名称', name: 'dept_name', align: 'left',width:120},
					{ display: '使用状态', name: 'use_state', align: 'left',width:80},
					{ display: '在用状态', name: 'is_dept', align: 'left',width:80},
					{ display: '仓库编码', name: 'store_code', align: 'left',width:100},
					{ display: '仓库名称', name: 'store_name', align: 'left',width:120},
					{ display: '是否投放', name: 'is_throw', align: 'left',width:80},
					{ display: '入库日期', name: 'in_date', align: 'left',width:80},
					{ display: '备注', name: 'note', align: 'left',width:100},
					{ display: '是否折旧', name: 'is_depr', align: 'left',width:80},
					{ display: '折旧方法', name: 'depr_method', align: 'left',width:100},
					{ display: '计提年数', name: 'acc_depre_amount', align: 'left',width:80},
					{ display: '资产原值', name: 'price', align: 'left',width:100,
						render:function(rowdata,rowindex,value){
							return formatNumber(value,2,1)
						}
					},
					{ display: '累计折旧', name: 'depre_money', align: 'left',width:100,
						render:function(rowdata,rowindex,value){
							return formatNumber(value,2,1)
						}
					},
					{ display: '资产净值', name: 'cur_money', align: 'left',width:100,
						render:function(rowdata,rowindex,value){
							return formatNumber(value,2,1)
						}
					},
					{ display: '预留残值', name: 'fore_money', align: 'left',width:100,
						render:function(rowdata,rowindex,value){
							return formatNumber(value,2,1)
						}
					},
					{ display: '累计折旧月份', name: 'add_depre_month', align: 'left',width:100},
					{ display: '上次折旧年', name: 'last_depr_year', align: 'left',width:80},
					{ display: '上次折旧月', name: 'last_depr_month', align: 'left',width:80},
					{ display: '错误说明', name: 'error_type', align: 'left',width: '35%',
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
