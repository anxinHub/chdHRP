<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<!-- 保养提醒 页面 -->
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    var state_name;
    $(function ()
    { 
    	loadDict();//加载下拉框
    	//加载数据
    	loadHead(null);
    	$("#near").change(function(){
			if($("#near").prop("checked") == true){
				state_name=1;
				$('#last').prop('checked',false) ;
				$('#past').prop('checked',false) ;
			}else{
				state_name = '' ;
			}
			query();
		})
		$("#last").change(function(){
			if($("#last").prop("checked") == true){
				state_name=2;
				$('#near').prop('checked',false) ;
				$('#past').prop('checked',false) ;
			}else{
				state_name = '' ;
			}
			query();
		})
		$("#past").change(function(){
			if($("#past").prop("checked") == true){
				state_name=3;
				$('#near').prop('checked',false) ;
				$('#last').prop('checked',false) ;
			}else{
				state_name = '' ;
			}
			query();
		}) 
		
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1; 
    		grid.options.parms.push({name:'ass_nature',value:liger.get("ass_nature").getValue()}); 
    		grid.options.parms.push({name:'plan_name',value:$("#plan_name").val()}); 
    		grid.options.parms.push({name:'queryDate',value:$("#queryDate").val()});
    		grid.options.parms.push({name:'state_name',value:state_name}); 
    		//根据表字段进行添加查询条件  
    	  /* grid.options.parms.push({name:'state',value:liger.get("state").getValue()});   */
    	//加载查询条件
    	grid.loadData(grid.where);
     }
    

    function loadHead(){
    	
    	grid = $("#maingrid").ligerGrid({
    		
           columns: [  
                     
                     { display: '计划编号', name: 'plan_no', align: 'left',width:'100',
					 		},
                     { display: '计划名称', name: 'plan_name', align: 'left',width:'100'
					 		},
			 		{ display: '状态', name: 'state_name', align: 'center',width:60,
			 		  	render:function(rowdata,index,value){
			 				if(value =='过期'){
			 					return '<span style="color:red;font-weight:bold;">过期</span>';
			 				}else if(value == '到期'){
			 					return '<span style="color:#FF5500;font-weight:bold;">到期</span>';
			 				}else if(value =='临近'){
			 					return '<span style="color:blue;font-weight:bold;">临近</span>';
			 				}
			 			}  
			 		},
                     { display: '统计年度', name: 'ass_year', align: 'left',width:'100'
					 		},
                     { display: '统计月份', name: 'ass_month', align: 'left',width:'100'
					 		},
                     { display: '资产性质', name: 'ass_nature', align: 'left',width:'100',
					 			
								render : function(rowdata, rowindex, value) {
					 				
									return rowdata.naturs_name;
									
									}
					 		},
                     { display: '保养等级', name: 'maintain_degree', align: 'left',width:'100',
					 			
								render : function(rowdata, rowindex, value) {
					 				
									if (rowdata.maintain_degree == 0) {
										
											return "一级保养";
											
										} else if (rowdata.maintain_degree == 1) {
											
											return "二级保养";
											
										}else if (rowdata.maintain_degree == 2) {
											
											return "三级保养";
											
										}else if (rowdata.maintain_degree == 3) {
											
											return "四级保养";
											
										}
									}
					 		},
                     { display: '周期单位', name: 'sharp_name', align: 'left',width:'100'
					 		},
                     { display: '保养周期', name: 'plan_cycle', align: 'left',width:'100'
					 		},
                     { display: '计划执行人', name: 'plan_exec_emp', align: 'left',width:'100',
					 			
								render : function(rowdata, rowindex, value) {
					 				
									return rowdata.plan_exec_emp_name;
									
									}
					 		},
                     { display: '保养说明', name: 'maintain_desc', align: 'left',width:'100'
					 		},
                     { display: '制单人', name: 'create_emp', align: 'left',width:'100',
					 			
								render : function(rowdata, rowindex, value) {
					 				
									return rowdata.create_emp_name;
									
									}
					 		},
                     { display: '制单日期', name: 'create_date', align: 'left',width:'100'
					 		},
                     { display: '审核人', name: 'audit_emp', align: 'left',width:'100',
					 			
								render : function(rowdata, rowindex, value) {
					 				
									return rowdata.audit_emp_name;
									
									}
					 			
					 		},
                     { display: '审核日期', name: 'audit_date', align: 'left',width:'100'
					 		},
					 		/* { display: '状态', name: 'state_name', align: 'left',width:'100',
					 			
								 render : function(rowdata, rowindex, value) {
					 				
									if (rowdata.state == 0) {
										
											return "新建";
											
										} else if (rowdata.state == 1) {
											
											return "审核";
											
										} else if (rowdata.state == 2) {
											
											return "终止";
											
										}
									} 
					 		},*/
                     { display: '上次保养日期', name: 'last_exec_date', align: 'left',width:'100'
					 		},
                     { display: '下次保养日期', name: 'next_exec_date', align: 'left',width:'100'
					 		},
                     { display: '计划开始日期', name: 'plan_start_date', align: 'left',width:'100'
					 		},
                     { display: '计划结束日期', name: 'plan_end_date', align: 'left',width:'100'
					 		},
			 		 
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAssMaintainRemind.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,
                     toolbar: { items: [
                                     	{ text: '查询', id:'search', click: query,icon:'search' },
                				    				]} 
    				 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function loadDict(){
    	var param = {
            	query_key:''
        };
    	autodate("#queryDate",'yyyy-MM-dd');
    	$("#plan_name").ligerTextBox({width:160});
    	autocomplete("#ass_nature", "../queryAssNaturs.do?isCheck=false", "id","text", true, true,param,true);
    	$("#queryDate").ligerTextBox({width:160});
    }
   
	 
	 
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
<table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
        	
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">查询日期：</td>
            <td align="left" class="l-table-edit-td">
            	<input class="Wdate" name="queryDate" type="text" id="queryDate" ltype="text" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" />
            </td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">计划名称：</td>
            <td align="left" class="l-table-edit-td">
           		<input name="plan_name" type="text" id="plan_name"  />
            </td>
              <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产性质：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="ass_nature" type="text" id="ass_nature"  />
            </td> 
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">查询内容：</td>
            <td align="left" class="l-table-edit-td" colspan="2">
	            <input id="near" type="checkbox" ltype="text" />临近
	            <input id="last" type="checkbox" ltype="text" />到期
	            <input id="past" type="checkbox" ltype="text" />过期
	            </td>
    </table>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	 
	<div id="maingrid"></div>
 
</body>
</html>
