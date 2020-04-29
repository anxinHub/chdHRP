<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;" xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>

<script type="text/javascript">

	var grid; 
	var gridManager = null;
	var userUpdateStr;
	var show_goal ;
	
	
	
	//页面初始化
	$(function() {
		//autocomplete("#dept_id", "../queryDeptDict.do?isCheck=false", "id","text", true, true);
		
		loadDict();//加载字典
		loadHead(null);//加载数据
		toolbar();//工具栏
		loadHotkeys();//快捷键
		
		$("#show_goal").change(function () {
			show_goal = $("#show_goal").ligerCheckBox().getValue();
			loadHead(null);//加载数据
			query(); 
		});
		
	});
	
	
	
	function loadDict(){
		
		autocomplete("#goal_code","../quertPrmGoalDict.do?isCheck=false","id","text",true,true,"",true,"","400");
		$("#show_goal").ligerCheckBox().setValue(true);
		
		$("#acct_yearm").ligerTextBox({ width:160 });
   	 	autodate("#acct_yearm","yyyymm");
	}
	
	
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		
		
		var acct_yearm = $('#acct_yearm').val();
		if(acct_yearm == ''){
			$.ligerDialog.warn('核算年月不能为空');
			return ; 
		}
		
		var goal_code = liger.get('goal_code').getValue();
		if(goal_code == ''){
			$.ligerDialog.warn('目标名称不能为空');
			return ; 
		}
		
		var acc_year = acct_yearm.substring(0,4);
		var acc_month = acct_yearm.substring(4,6);
		
		
		grid.options.parms.push({name : 'acc_year',value : acc_year});
		grid.options.parms.push({name : 'acc_month',value : acc_month});
		grid.options.parms.push({name : 'goal_code',value : goal_code});
		
		
		//加载查询条件
		grid.loadData(grid.where);
	}
	 
	
	//加载数据
	function loadHead() {
		
		show_goal = $("#show_goal").ligerCheckBox().getValue();
		
		if(show_goal){

			grid = $("#maingrid").ligerGrid({
				columns :[
					{display : '项目及权重',align : 'left',frozen:true,
						columns :[
							{display : '数据提供部门',align : 'left',frozen:true,
								columns :[
									{display : '科室',name : 'dept_name',align : 'left',frozen:true,width:160}
								]
							}
						]
					},
				    
					{display : '综合服务满意度(25分)',align : 'left',
						columns :[
							{display : '服务中心',align : 'left',
								columns :[
									{display : '积分值',name : 'kpi_001',align : 'left',width:80},
									{display : '灯',name : 'led_001',align : 'left',width:40,
										render : function(rowdata, rowindex, value,col) {
											if(!rowdata[col.name]){
												return '';
											}
												
											return "<img style='margin-top: 7px;width:20px;' src='<%=path%>/"+rowdata[col.name]+"' border='0' width ='50px' />";
										}
									},
									{display : '质量奖得分1',name : 'score_001',align : 'left',width:80},
									{display : '目标值',name : 'goal_001',align : 'left',width:80}
								]
							}
						]
					},
					
					
					/* {display : '医疗投诉(5分)',align : 'left',width:280,
						columns :[
							{display : '服务中心',align : 'left',width:280,
								columns :[
									{display : '投诉事件有错',name : '',align : 'left',width:80},
									{display : '投诉事件无错',name : '',align : 'left',width:80},
									{display : '灯',name : '',align : 'left',width:40},
									{display : '质量奖得分2',name : '',align : 'left',width:80},
								]
							}
						]
					}, */
					
					
					{display : '月质控检查合格率(10分)',align : 'left',
						columns :[
							{display : '医技质控小组',align : 'left',
								columns :[
									{display : '合格率',name : 'kpi_003',align : 'left',width:80},
									{display : '灯',name : 'led_003',align : 'left',width:40,
										render : function(rowdata, rowindex, value,col) {
											if(!rowdata[col.name]){
												return '';
											}
												
											return "<img style='margin-top: 7px;width:20px;' src='<%=path%>/"+rowdata[col.name]+"' border='0' width ='50px' />";
										}
									},
									{display : '质量奖得分3',name : 'score_003',align : 'left',width:80},
									{display : '目标值',name : 'goal_003',align : 'left',width:80}
								]
							}
						]
					},
					
					
					{display : '检查及时性/当日检查率(30分)',align : 'left',
						columns :[
							{display : '医务处',align : 'left',
								columns :[
									{display : '及时性',name : 'kpi_004',align : 'left',width:80},
									{display : '灯',name : 'led_004',align : 'left',width:40,
										render : function(rowdata, rowindex, value,col) {
											if(!rowdata[col.name]){
												return '';
											}
												
											return "<img style='margin-top: 7px;width:20px;' src='<%=path%>/"+rowdata[col.name]+"' border='0' width ='50px' />";
										}
									},
									{display : '质量奖得分4',name : 'score_004',align : 'left',width:80},
									{display : '目标值',name : 'goal_004',align : 'left',width:80},
								]
							}
						]
					},
					
					
					{display : '设备使用记录合格率(10分)',align : 'left',
						columns :[
							{display : '医技质控小组',align : 'left',
								columns :[
									{display : '合格率',name : 'kpi_005',align : 'left',width:80},
									{display : '灯',name : 'led_005',align : 'left',width:40,
										render : function(rowdata, rowindex, value,col) {
											if(!rowdata[col.name]){
												return '';
											}
												
											return "<img style='margin-top: 7px;width:20px;' src='<%=path%>/"+rowdata[col.name]+"' border='0' width ='50px' />";
										}
									},
									{display : '质量奖得分5',name : 'score_005',align : 'left',width:80},
									{display : '目标值',name : 'goal_005',align : 'left',width:80}
								]
							}
						]
					},
					
					
					{display : '危急值管理(10分)',align : 'left',
						columns :[
							{display : '医技质控小组',align : 'left',
								columns :[
									{display : '不合格例次',name : 'kpi_006',align : 'left',width:80},
									{display : '灯',name : 'led_006',align : 'left',width:40,
										render : function(rowdata, rowindex, value,col) {
											if(!rowdata[col.name]){
												return '';
											}
												
											return "<img style='margin-top: 7px;width:20px;' src='<%=path%>/"+rowdata[col.name]+"' border='0' width ='50px' />";
										}
									},
									{display : '质量奖得分6',name : 'score_006',align : 'left',width:80},
									{display : '目标值',name : 'goal_006',align : 'left',width:80}
								]
							}
						]
					},
					
					
					{display : '改善项目(10分)',align : 'left',
						columns :[
							{display : '医技质控小组',align : 'left',
								columns :[
									{display : '项目',name : 'kpi_007',align : 'left',width:80},
									{display : '灯',name : 'led_007',align : 'left',width:40,
										render : function(rowdata, rowindex, value,col) {
											if(!rowdata[col.name]){
												return '';
											}
												
											return "<img style='margin-top: 7px;width:20px;' src='<%=path%>/"+rowdata[col.name]+"' border='0' width ='50px' />";
										}
									},
									{display : '质量奖得分7',name : 'score_007',align : 'left',width:80},
									{display : '目标值',name : 'goal_007',align : 'left',width:80}
								]
							}
						]
					},
					
					
					{display : '超扣项目',align : 'left',
						columns :[
							{display : '核心制度执行情况(10分)',align : 'left',
								columns :[
									{display : '医务处',align : 'left',
										columns :[
											{display : '未执行情况',name : 'kpi_009',align : 'left',width:80},
											{display : '灯',name : 'score_009',align : 'left',width:40,
												render : function(rowdata, rowindex, value,col) {
													if(!rowdata[col.name]){
														return '';
													}
														
													return "<img style='margin-top: 7px;width:20px;' src='<%=path%>/"+rowdata[col.name]+"' border='0' width ='50px' />";
												}
											},
											{display : '质量奖扣分1',name : 'led_009',align : 'left',width:80},
											{display : '目标值',name : 'goal_009',align : 'left',width:80}
										]
									}
								]
							},
							
							
							{display : '推诿事件(10分)',align : 'left',
								columns :[
									{display : '事件情况',name : 'kpi_010',align : 'left',width:80},
									{display : '灯',name : 'led_010',align : 'left',width:40,
										render : function(rowdata, rowindex, value,col) {
											if(!rowdata[col.name]){
												return '';
											}
												
											return "<img style='margin-top: 7px;width:20px;' src='<%=path%>/"+rowdata[col.name]+"' border='0' width ='50px' />";
										}
									},
									{display : '质量奖扣分2',name : 'score_010',align : 'left',width:80},
									{display : '目标值',name : 'goal_010',align : 'left',width:80}
								]
							},
							
							
							{display : '强制不良事件漏报例次(10分)',align : 'left',
								columns :[
									{display : '漏报事件',name : 'kpi_011',align : 'left',width:80},
									{display : '灯',name : 'led_011',align : 'left',width:40,
										render : function(rowdata, rowindex, value,col) {
											if(!rowdata[col.name]){
												return '';
											}
												
											return "<img style='margin-top: 7px;width:20px;' src='<%=path%>/"+rowdata[col.name]+"' border='0' width ='50px' />";
										}
									},
									{display : '质量奖扣分3',name : 'score_011',align : 'left',width:80},
									{display : '目标值',name : 'goal_011',align : 'left',width:80}
								]
							},
							
							
							{display : '院内感染(10分)',align : 'left',
								columns :[
									{display : '院感科',align : 'left',
										columns :[
											{display : '群发性院感事件',name : 'kpi_012',align : 'left',width:90},
											{display : '灯',name : 'led_012',align : 'left',width:40,
												render : function(rowdata, rowindex, value,col) {
													if(!rowdata[col.name]){
														return '';
													}
														
													return "<img style='margin-top: 7px;width:20px;' src='<%=path%>/"+rowdata[col.name]+"' border='0' width ='50px' />";
												}
											},
											{display : '质量奖扣分4',name : 'score_012',align : 'left',width:80},
											{display : '目标值',name : 'goal_012',align : 'left',width:80}
										]
									}
								]
							},
							
							
							/* {display : '医院会议参会情况(10分)',align : 'left',width:280,
								columns :[
									{display : '院办',align : 'left',width:280,
										columns :[
											{display : '请假次数(有替代)',name : '',align : 'left',width:100},
											{display : '请假次数(无替代)',name : '',align : 'left',width:100},
											{display : '灯',name : '',align : 'left',width:40},
											{display : '质量奖扣分5',name : '',align : 'left',width:80}
										]
									}
								]
							}, */
							
							
							{display : '质量超扣(10分)',align : 'left',
								columns :[
									{display : '护理部',align : 'left',
										columns :[
											{display : '过期物品',name : 'kpi_014',align : 'left',width:80},
											{display : '灯',name : 'led_014',align : 'left',width:40,
												render : function(rowdata, rowindex, value,col) {
													if(!rowdata[col.name]){
														return '';
													}
														
													return "<img style='margin-top: 7px;width:20px;' src='<%=path%>/"+rowdata[col.name]+"' border='0' width ='50px' />";
												}
											},
											{display : '质量奖扣分6',name : 'score_014',align : 'left',width:80},
											{display : '目标值',name : 'goal_014',align : 'left',width:80}
										]
									}
								]
							},
							
							
							{display : '医疗纠纷/事故(10-50分)',align : 'left',
								columns :[
									{display : '医疗纠纷/事故',name : 'kpi_015',align : 'left',width:80},
									{display : '灯',name : 'led_015',align : 'left',width:40,
										render : function(rowdata, rowindex, value,col) {
											if(!rowdata[col.name]){
												return '';
											}
												
											return "<img style='margin-top: 7px;width:20px;' src='<%=path%>/"+rowdata[col.name]+"' border='0' width ='50px' />";
										}
									},
									{display : '质量奖扣分6',name : 'score_015',align : 'left',width:80},
									{display : '目标值',name : 'goal_015',align : 'left',width:80}
								]
							}
						]
					},
					
					
					
					{display : '总分(100分)',align : 'left',
						columns :[
							{display : '得分',name : 'sum_score',align : 'left',width:80}
						]
					}/* ,
					
					
					{display : '得分率',name : '',align : 'left',width:80} */
				],
				dataAction : 'server',dataType : 'server',usePager : true,url:"queryreport_003.do?isCheck=false",
				width : '100%',height : '100%',rownumbers : true,selectRowButtonOnly : true,
				isAddRow:false,delayLoad:true,
				alternatingRow:false
			});
		}else{
			
			grid = $("#maingrid").ligerGrid({
				columns :[
					{display : '项目及权重',align : 'left',frozen:true,
						columns :[
							{display : '数据提供部门',align : 'left',frozen:true,
								columns :[
									{display : '科室',name : 'dept_name',align : 'left',frozen:true,width:160}
								]
							}
						]
					},
				    
					{display : '综合服务满意度(25分)',align : 'left',
						columns :[
							{display : '服务中心',align : 'left',
								columns :[
									{display : '积分值',name : 'kpi_001',align : 'left',width:80},
									{display : '灯',name : 'led_001',align : 'left',width:40,
										render : function(rowdata, rowindex, value,col) {
											if(!rowdata[col.name]){
												return '';
											}
												
											return "<img style='margin-top: 7px;width:20px;' src='<%=path%>/"+rowdata[col.name]+"' border='0' width ='50px' />";
										}
									},
									{display : '质量奖得分1',name : 'score_001',align : 'left',width:80}
								]
							}
						]
					},
					
					
					/* {display : '医疗投诉(5分)',align : 'left',width:280,
						columns :[
							{display : '服务中心',align : 'left',width:280,
								columns :[
									{display : '投诉事件有错',name : '',align : 'left',width:80},
									{display : '投诉事件无错',name : '',align : 'left',width:80},
									{display : '灯',name : '',align : 'left',width:40},
									{display : '质量奖得分2',name : '',align : 'left',width:80},
								]
							}
						]
					}, */
					
					
					{display : '月质控检查合格率(10分)',align : 'left',
						columns :[
							{display : '医技质控小组',align : 'left',
								columns :[
									{display : '合格率',name : 'kpi_003',align : 'left',width:80},
									{display : '灯',name : 'led_003',align : 'left',width:40,
										render : function(rowdata, rowindex, value,col) {
											if(!rowdata[col.name]){
												return '';
											}
												
											return "<img style='margin-top: 7px;width:20px;' src='<%=path%>/"+rowdata[col.name]+"' border='0' width ='50px' />";
										}
									},
									{display : '质量奖得分3',name : 'score_003',align : 'left',width:80}
								]
							}
						]
					},
					
					
					{display : '检查及时性/当日检查率(30分)',align : 'left',
						columns :[
							{display : '医务处',align : 'left',
								columns :[
									{display : '及时性',name : 'kpi_004',align : 'left',width:80},
									{display : '灯',name : 'led_004',align : 'left',width:40,
										render : function(rowdata, rowindex, value,col) {
											if(!rowdata[col.name]){
												return '';
											}
												
											return "<img style='margin-top: 7px;width:20px;' src='<%=path%>/"+rowdata[col.name]+"' border='0' width ='50px' />";
										}
									},
									{display : '质量奖得分4',name : 'score_004',align : 'left',width:80}
								]
							}
						]
					},
					
					
					{display : '设备使用记录合格率(10分)',align : 'left',
						columns :[
							{display : '医技质控小组',align : 'left',
								columns :[
									{display : '合格率',name : 'kpi_005',align : 'left',width:80},
									{display : '灯',name : 'led_005',align : 'left',width:40,
										render : function(rowdata, rowindex, value,col) {
											if(!rowdata[col.name]){
												return '';
											}
												
											return "<img style='margin-top: 7px;width:20px;' src='<%=path%>/"+rowdata[col.name]+"' border='0' width ='50px' />";
										}
									},
									{display : '质量奖得分5',name : 'score_005',align : 'left',width:80}
								]
							}
						]
					},
					
					
					{display : '危急值管理(10分)',align : 'left',
						columns :[
							{display : '医技质控小组',align : 'left',
								columns :[
									{display : '不合格例次',name : 'kpi_006',align : 'left',width:80},
									{display : '灯',name : 'led_006',align : 'left',width:40,
										render : function(rowdata, rowindex, value,col) {
											if(!rowdata[col.name]){
												return '';
											}
												
											return "<img style='margin-top: 7px;width:20px;' src='<%=path%>/"+rowdata[col.name]+"' border='0' width ='50px' />";
										}
									},
									{display : '质量奖得分6',name : 'score_006',align : 'left',width:80}
								]
							}
						]
					},
					
					
					{display : '改善项目(10分)',align : 'left',
						columns :[
							{display : '医技质控小组',align : 'left',
								columns :[
									{display : '项目',name : 'kpi_007',align : 'left',width:80},
									{display : '灯',name : 'led_007',align : 'left',width:40,
										render : function(rowdata, rowindex, value,col) {
											if(!rowdata[col.name]){
												return '';
											}
												
											return "<img style='margin-top: 7px;width:20px;' src='<%=path%>/"+rowdata[col.name]+"' border='0' width ='50px' />";
										}
									},
									{display : '质量奖得分7',name : 'score_007',align : 'left',width:80}
								]
							}
						]
					},
					
					
					{display : '超扣项目',align : 'left',
						columns :[
							{display : '核心制度执行情况(10分)',align : 'left',
								columns :[
									{display : '医务处',align : 'left',
										columns :[
											{display : '未执行情况',name : 'kpi_009',align : 'left',width:80},
											{display : '灯',name : 'score_009',align : 'left',width:40,
												render : function(rowdata, rowindex, value,col) {
													if(!rowdata[col.name]){
														return '';
													}
														
													return "<img style='margin-top: 7px;width:20px;' src='<%=path%>/"+rowdata[col.name]+"' border='0' width ='50px' />";
												}
											},
											{display : '质量奖扣分1',name : 'led_009',align : 'left',width:80}
										]
									}
								]
							},
							
							
							{display : '推诿事件(10分)',align : 'left',
								columns :[
									{display : '事件情况',name : 'kpi_010',align : 'left',width:80},
									{display : '灯',name : 'led_010',align : 'left',width:40,
										render : function(rowdata, rowindex, value,col) {
											if(!rowdata[col.name]){
												return '';
											}
												
											return "<img style='margin-top: 7px;width:20px;' src='<%=path%>/"+rowdata[col.name]+"' border='0' width ='50px' />";
										}
									},
									{display : '质量奖扣分2',name : 'score_010',align : 'left',width:80}
								]
							},
							
							
							{display : '强制不良事件漏报例次(10分)',align : 'left',
								columns :[
									{display : '漏报事件',name : 'kpi_011',align : 'left',width:80},
									{display : '灯',name : 'led_011',align : 'left',width:40,
										render : function(rowdata, rowindex, value,col) {
											if(!rowdata[col.name]){
												return '';
											}
												
											return "<img style='margin-top: 7px;width:20px;' src='<%=path%>/"+rowdata[col.name]+"' border='0' width ='50px' />";
										}
									},
									{display : '质量奖扣分3',name : 'score_011',align : 'left',width:80}
								]
							},
							
							
							{display : '院内感染(10分)',align : 'left',
								columns :[
									{display : '院感科',align : 'left',
										columns :[
											{display : '群发性院感事件',name : 'kpi_012',align : 'left',width:90},
											{display : '灯',name : 'led_012',align : 'left',width:40,
												render : function(rowdata, rowindex, value,col) {
													if(!rowdata[col.name]){
														return '';
													}
														
													return "<img style='margin-top: 7px;width:20px;' src='<%=path%>/"+rowdata[col.name]+"' border='0' width ='50px' />";
												}
											},
											{display : '质量奖扣分4',name : 'score_012',align : 'left',width:80}
										]
									}
								]
							},
							
							
							/* {display : '医院会议参会情况(10分)',align : 'left',width:280,
								columns :[
									{display : '院办',align : 'left',width:280,
										columns :[
											{display : '请假次数(有替代)',name : '',align : 'left',width:100},
											{display : '请假次数(无替代)',name : '',align : 'left',width:100},
											{display : '灯',name : '',align : 'left',width:40},
											{display : '质量奖扣分5',name : '',align : 'left',width:80}
										]
									}
								]
							}, */
							
							
							{display : '质量超扣(10分)',align : 'left',
								columns :[
									{display : '护理部',align : 'left',
										columns :[
											{display : '过期物品',name : 'kpi_014',align : 'left',width:80},
											{display : '灯',name : 'led_014',align : 'left',width:40,
												render : function(rowdata, rowindex, value,col) {
													if(!rowdata[col.name]){
														return '';
													}
														
													return "<img style='margin-top: 7px;width:20px;' src='<%=path%>/"+rowdata[col.name]+"' border='0' width ='50px' />";
												}
											},
											{display : '质量奖扣分6',name : 'score_014',align : 'left',width:80}
										]
									}
								]
							},
							
							
							{display : '医疗纠纷/事故(10-50分)',align : 'left',
								columns :[
									{display : '医疗纠纷/事故',name : 'kpi_015',align : 'left',width:80},
									{display : '灯',name : 'led_015',align : 'left',width:40,
										render : function(rowdata, rowindex, value,col) {
											if(!rowdata[col.name]){
												return '';
											}
												
											return "<img style='margin-top: 7px;width:20px;' src='<%=path%>/"+rowdata[col.name]+"' border='0' width ='50px' />";
										}
									},
									{display : '质量奖扣分6',name : 'score_015',align : 'left',width:80}
								]
							}
						]
					},
					
					
					
					{display : '总分(100分)',align : 'left',
						columns :[
							{display : '得分',name : 'sum_score',align : 'left',width:80}
						]
					}/* ,
					
					
					{display : '得分率',name : '',align : 'left',width:80} */
				],
				dataAction : 'server',dataType : 'server',usePager : true,url:"queryreport_003.do?isCheck=false",
				width : '100%',height : '100%',rownumbers : true,selectRowButtonOnly : true,
				isAddRow:false,delayLoad:true,
				alternatingRow:false
			});
		}

		gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	
	
	//工具栏
	function toolbar(){
		var obj = [];
			
		obj.push({ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search'});
       	obj.push({ line:true});
       	
       	$("#toptool").ligerToolBar({ items: obj});
	}
	

	//键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
	}
	

</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">核算年月：</td>
			<td align="left" class="l-table-edit-td"><input class="Wdate" name="acct_yearm" type="text" id="acct_yearm" ltype="text"
				validate="{required:true,maxlength:20}" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyyMM'})" /></td>
			<td align="left" ></td>
			
			<td align="right" class="l-table-edit-td" style="padding-left: 20px; ">目标名称：</td>
			<td align="left" class="l-table-edit-td">
				<input name="goal_code" type="text" id="goal_code" ltype="text" />
			</td>
			<td align="left" ></td> 
			
			<td align="left" class="l-table-edit-td" style="padding-left: 20px; ">
				<input name="show_goal" type="checkbox" id="show_goal" ltype="text"  />目标值
            </td>
            <td align="left"></td>
		</tr>
		
	</table>
	<div id="toptool"></div>
	<div id="maingrid"></div>
</body>
</html>
