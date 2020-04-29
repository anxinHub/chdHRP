<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
	var dataFormat;
	$(function() { 
		
		loadForm();
		loadHead();
	});

	
	function loadHead() {
		grid = $("#maingrid").ligerGrid(
				{
					columns: [

								{ display: '申请单号', name: 'apply_no', align: 'left',
									 
									 render : function(rowdata, rowindex,value) {
										 
											return "<a href=javascript:openUpdate('"+rowdata.group_id   + "|" + 
																				rowdata.hos_id   + "|" + 
																				rowdata.copy_code   + "|" + 
																				rowdata.apply_id  +"')>"+
													                                 rowdata.apply_no+"</a>";
														}
										},
								{ display: '申请人', name: 'apply_emp_name', align: 'left'
										},
								{ display: '申请日期', name: 'apply_date', align: 'left'
										},
								{ display: '状态', name: 'state', align: 'left',
											render : function(rowdata, rowindex,value) {
												if(rowdata.state == 1){
													
													return "审核";
													
												}else if(rowdata.state == 0){
													
													return "新建";
													
												}
											}
										},
								{ display: '维修部门', name: 'repair_dept_name', align: 'left'
										},
								{ display: '紧急程度', name: 'sharp_degree', align: 'left',
											render : function(rowdata, rowindex,value) {
												
												if(rowdata.sharp_degree == 1){
													
													return "中";
													
												}else if(rowdata.sharp_degree == 0){
													
													return "高";
													
												}else if(rowdata.sharp_degree == 2){
													
													return "低";
													
												}
								
											}
										},
								{ display: '备注', name: 'note', align: 'left'
										}
									 	     
								 
								 ],
								 dataAction: 'server',dataType: 'server',usePager:true,url:'../assrepairapply/queryAssRepairApply.do?state=1',
	                       width: '700', height: '400', checkbox: true,rownumbers:true,
	              
				});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	function applyNoAssRepairRec() {
		
		var data = gridManager.getCheckedRows();
		
		//alert(JSON.stringify(data))
	 	   
        if (data.length == 0){
     	   
        	$.ligerDialog.error('请选择行');
        	
        }else{
     	   
            var ParamVo =[];
            
            $(data).each(function (){		
         	   
 				ParamVo.push(
 						
 					this.group_id   +"@"+
 				
 					this.hos_id   +"@"+ 
 				
 					this.copy_code   +"@"+ 
 				
 					this.apply_no     +"@"+ 
 				
 					this.repair_dept_id   +"@"+ 
 				
 					this.repair_dept_no   +"@"+ 
 				
 					this.ass_year   +"@"+ 
 				
 					this.ass_month   +"@"+ 
 				
 					this.ass_nature   +"@"+ 
 				
 					this.apply_id   
 					
 				
 				); });
            $.ligerDialog.confirm('确定生成?', function (yes){
            	if(yes){
                	ajaxJsonObjectByUrl("applyNoAssRepairRec.do",{ParamVo : ParamVo.toString()},function (responseData){
                		if(responseData.state=="true"){
                			parent.query();
                		}
                	});
            	}
            });
     	   
        }
		 /*
		ajaxJsonObjectByUrl("applyNoAssRepairRec.do", formPara, function(responseData) {

			if (responseData.state == "true") { 
				parent.query();
			}
		});
		 */
	}

	function loadForm() {

		$.metadata.setType("attr", "validate");
		var v = $("form").validate({
			errorPlacement : function(lable, element) {
				if (element.hasClass("l-textarea")) {
					element.ligerTip({
						content : lable.html(),
						target : element[0]
					});
				} else if (element.hasClass("l-text-field")) {
					element.parent().ligerTip({
						content : lable.html(),
						target : element[0]
					});
				} else {
					lable.appendTo(element.parents("td:first").next("td"));
				}
			},
			success : function(lable) {
				lable.ligerHideTip();
				lable.remove();
			},
			submitHandler : function() {
				$("form .l-text,.l-textarea").ligerHideTip();
			}
		});
		$("form").ligerForm();
	}
 
	 
</script>

</head>

<body>
	 <div id="maingrid"></div>
</body>
</html>
