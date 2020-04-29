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
	
	
	function loadDict(){
		autocomplete("#goal_code","../quertPrmGoalDict.do?isCheck=false","id","text",true,true,"",true,"","400");
		$("#show_goal").ligerCheckBox().setValue(true);
		
		$("#acct_yearm").ligerTextBox({ width:160 });
   	 	autodate("#acct_yearm","yyyymm");
	}
	
	
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
		
		if(show_goal){//目标值选中
			grid = $("#maingrid").ligerGrid({
				columns :[
					{display : '项目及权重',align : 'left',frozen:true,
						columns :[
							{display : '数据提供部门',align : 'left',frozen:true,
								columns :[
									{display : '科室',name : 'dept_name',align : 'left',frozen:true,width:200}
								]
							}
						]
					},
					
					
					{display : '综合服务满意度(25分)',align : 'left',
						columns :[
							{display : '服务中心',align : 'left',
								columns :[
									{display : '积分值',name : '',align : 'left',width:80},
									{display : '灯',name : '',align : 'left',width:40},
									{display : '质量奖得分1',name : '',align : 'left',width:80},
									{display : '目标值',name : '',align : 'left',width:80}
								]
							}
						]
					},
					
					
					/* {display : '投诉(5分)',align : 'left',
						columns :[
							{display : '服务中心',align : 'left',
								columns :[
									{display : '投诉事件有错',name : '',align : 'left',width:80},
									{display : '投诉事件无错',name : '',align : 'left',width:80},
									{display : '灯',name : '',align : 'left',width:40},
									{display : '目标值',name : '',align : 'left',width:80}
								]
							}
						]
					}, */
					
					
					{display : '日常工作完成情况',align : 'left',
						columns :[
							{display : '院务会测评工作数量得分(25分)',align : 'left',
								columns :[
									{display : '院办',align : 'left',
										columns :[
											{display : '数量得分',name : '',align : 'left',width:80},
											{display : '灯',name : '',align : 'left',width:40},
											{display : '质量奖得分3',name : '',align : 'left',width:80},
											{display : '目标值',name : '',align : 'left',width:80}
										]
									}
								]
							},
							
							
							{display : '院务会测评工作质量得分(25分)',align : 'left',
								columns :[
									{display : '院办',align : 'left',
										columns :[
											{display : '质量得分',name : '',align : 'left',width:80},
											{display : '灯',name : '',align : 'left',width:40},
											{display : '质量奖得分4',name : '',align : 'left',width:80},
											{display : '目标值',name : '',align : 'left',width:80}
										]
									}
								]
							}
						]
					},
					
					
					
					{display : '督办工作完成情况',align : 'left',
						columns :[
							{display : '完成及时性(5分)',align : 'left',
								columns :[
									{display : '院办',align : 'left',
										columns :[
											{display : '完成及时性',name : '',align : 'left',width:80},
											{display : '灯',name : '',align : 'left',width:40},
											{display : '质量奖得分5',name : '',align : 'left',width:80},
											{display : '目标值',name : '',align : 'left',width:80}
										]
									}
								]
							},
							
							{display : '完成质量(5分)',align : 'left',
								columns :[
									{display : '院办',align : 'left',
										columns :[
											{display : '完成质量',name : '',align : 'left',width:80},
											{display : '灯',name : '',align : 'left',width:40},
											{display : '质量奖得分6',name : '',align : 'left',width:80},
											{display : '目标值',name : '',align : 'left',width:80}
										]
									}
								]
							}
						]
					}, 
					
					
					
					{display : '个性化指标完成情况(20分)',align : 'left',
						columns :[
							{display : '院办',align : 'left',
								columns :[
									{display : '完成情况',name : '',align : 'left',width:80},
									{display : '灯',name : '',align : 'left',width:40},
									{display : '质量奖得分7',name : '',align : 'left',width:80},
									{display : '目标值',name : '',align : 'left',width:80}
								]
							}
						]
					},
					
					
					
					{display : '改善项目(5分)',align : 'left',
						columns :[
							{display : '行政后勤质控小组',align : 'left',
								columns :[
									{display : '项目',name : '',align : 'left',width:80},
									{display : '灯',name : '',align : 'left',width:40},
									{display : '质量奖得分8',name : '',align : 'left',width:80},
									{display : '目标值',name : '',align : 'left',width:80}
								]
							}
						]
					},
					
					
					
					{display : '超扣项目',align : 'left',
						columns :[
							{display : '推诿事件(10分)',align : 'left',
								columns :[
									{display : '医务处',align : 'left',
										columns :[
											{display : '推诿事件',name : '',align : 'left',width:80},
											{display : '灯',name : '',align : 'left',width:40},
											{display : '质量奖扣分1',name : '',align : 'left',width:80},
											{display : '目标值',name : '',align : 'left',width:80}
										]
									}
								]
							},
							
							{display : '强制不良事件漏报(10分)',align : 'left',
								columns :[
									{display : '医务处',align : 'left',
										columns :[
											{display : '事件',name : '',align : 'left',width:80},
											{display : '灯',name : '',align : 'left',width:40},
											{display : '质量奖扣分2',name : '',align : 'left',width:80},
											{display : '目标值',name : '',align : 'left',width:80}
										]
									}
								]
							},
							
							{display : '医院感染(10分)',align : 'left',
								columns :[
									{display : '院感科',align : 'left',
										columns :[
											{display : '群发性院感事件',name : '',align : 'left',width:90},
											{display : '灯',name : '',align : 'left',width:40},
											{display : '质量奖扣分3',name : '',align : 'left',width:80},
											{display : '目标值',name : '',align : 'left',width:80}
										]
									}
								]
							}/* ,
							
							{display : '医院会议参会情况(10分)',align : 'left',width:320,
								columns :[
									{display : '院办',align : 'left',width:320,
										columns :[
											{display : '请假次数(有替代)',name : '',align : 'left',width:100},
											{display : '请假次数(无替代)',name : '',align : 'left',width:100},
											{display : '灯',name : '',align : 'left',width:40},
											{display : '质量奖扣分5',name : '',align : 'left',width:80}
										]
									}
								]
							} */
						]
					},
					
					
					
					
					{display : '总分(115分)',align : 'left',
						columns :[
							{display : '得分',name : '',align : 'left',width:80}
						]
					}/* ,
					
					
					
					{display : '得分率',name : '',align : 'left',width:80} */
					
				],
				dataAction : 'server',dataType : 'server',usePager : true,url:"queryreport_004.do?isCheck=false",
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
									{display : '科室',name : 'dept_name',align : 'left',frozen:true,width:200}
								]
							}
						]
					},
					
					
					{display : '综合服务满意度(25分)',align : 'left',
						columns :[
							{display : '服务中心',align : 'left',
								columns :[
									{display : '积分值',name : '',align : 'left',width:80},
									{display : '灯',name : '',align : 'left',width:40},
									{display : '质量奖得分1',name : '',align : 'left',width:80}
								]
							}
						]
					},
					
					
					/* {display : '投诉(5分)',align : 'left',
						columns :[
							{display : '服务中心',align : 'left',
								columns :[
									{display : '投诉事件有错',name : '',align : 'left',width:80},
									{display : '投诉事件无错',name : '',align : 'left',width:80},
									{display : '灯',name : '',align : 'left',width:40},
									{display : '目标值',name : '',align : 'left',width:80}
								]
							}
						]
					}, */
					
					
					{display : '日常工作完成情况',align : 'left',
						columns :[
							{display : '院务会测评工作数量得分(25分)',align : 'left',
								columns :[
									{display : '院办',align : 'left',
										columns :[
											{display : '数量得分',name : '',align : 'left',width:80},
											{display : '灯',name : '',align : 'left',width:40},
											{display : '质量奖得分3',name : '',align : 'left',width:80}
										]
									}
								]
							},
							
							
							{display : '院务会测评工作质量得分(25分)',align : 'left',
								columns :[
									{display : '院办',align : 'left',
										columns :[
											{display : '质量得分',name : '',align : 'left',width:80},
											{display : '灯',name : '',align : 'left',width:40},
											{display : '质量奖得分4',name : '',align : 'left',width:80}
										]
									}
								]
							}
						]
					},
					
					
					
					{display : '督办工作完成情况',align : 'left',
						columns :[
							{display : '完成及时性(5分)',align : 'left',
								columns :[
									{display : '院办',align : 'left',
										columns :[
											{display : '完成及时性',name : '',align : 'left',width:80},
											{display : '灯',name : '',align : 'left',width:40},
											{display : '质量奖得分5',name : '',align : 'left',width:80}
										]
									}
								]
							},
							
							{display : '完成质量(5分)',align : 'left',
								columns :[
									{display : '院办',align : 'left',
										columns :[
											{display : '完成质量',name : '',align : 'left',width:80},
											{display : '灯',name : '',align : 'left',width:40},
											{display : '质量奖得分6',name : '',align : 'left',width:80}
										]
									}
								]
							}
						]
					}, 
					
					
					
					{display : '个性化指标完成情况(20分)',align : 'left',
						columns :[
							{display : '院办',align : 'left',
								columns :[
									{display : '完成情况',name : '',align : 'left',width:80},
									{display : '灯',name : '',align : 'left',width:40},
									{display : '质量奖得分7',name : '',align : 'left',width:80}
								]
							}
						]
					},
					
					
					
					{display : '改善项目(5分)',align : 'left',
						columns :[
							{display : '行政后勤质控小组',align : 'left',
								columns :[
									{display : '项目',name : '',align : 'left',width:80},
									{display : '灯',name : '',align : 'left',width:40},
									{display : '质量奖得分8',name : '',align : 'left',width:80}
								]
							}
						]
					},
					
					
					
					{display : '超扣项目',align : 'left',
						columns :[
							{display : '推诿事件(10分)',align : 'left',
								columns :[
									{display : '医务处',align : 'left',
										columns :[
											{display : '推诿事件',name : '',align : 'left',width:80},
											{display : '灯',name : '',align : 'left',width:40},
											{display : '质量奖扣分1',name : '',align : 'left',width:80}
										]
									}
								]
							},
							
							{display : '强制不良事件漏报(10分)',align : 'left',
								columns :[
									{display : '医务处',align : 'left',
										columns :[
											{display : '事件',name : '',align : 'left',width:80},
											{display : '灯',name : '',align : 'left',width:40},
											{display : '质量奖扣分2',name : '',align : 'left',width:80}
										]
									}
								]
							},
							
							{display : '医院感染(10分)',align : 'left',
								columns :[
									{display : '院感科',align : 'left',
										columns :[
											{display : '群发性院感事件',name : '',align : 'left',width:90},
											{display : '灯',name : '',align : 'left',width:40},
											{display : '质量奖扣分3',name : '',align : 'left',width:80}
										]
									}
								]
							}/* ,
							
							{display : '医院会议参会情况(10分)',align : 'left',width:320,
								columns :[
									{display : '院办',align : 'left',width:320,
										columns :[
											{display : '请假次数(有替代)',name : '',align : 'left',width:100},
											{display : '请假次数(无替代)',name : '',align : 'left',width:100},
											{display : '灯',name : '',align : 'left',width:40},
											{display : '质量奖扣分5',name : '',align : 'left',width:80}
										]
									}
								]
							} */
						]
					},
					
					
					
					
					{display : '总分(115分)',align : 'left',
						columns :[
							{display : '得分',name : '',align : 'left',width:80}
						]
					}/* ,
					
					
					
					{display : '得分率',name : '',align : 'left',width:80} */
					
				],
				dataAction : 'server',dataType : 'server',usePager : true,url:"queryreport_004.do?isCheck=false",
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
			
			<!-- <td align="right" class="l-table-edit-td" style="padding-left: 20px; ">科室名称：</td>
			<td align="left" class="l-table-edit-td">
				<input name="dept_id" type="text" id="dept_id" ltype="text" />
			</td>
			<td align="left" ></td> -->
			
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
