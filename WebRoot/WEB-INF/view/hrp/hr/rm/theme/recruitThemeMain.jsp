<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />   
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script src="<%=path%>/lib/json2.js"></script>
<script type="text/javascript">
	var dataFormat;
	var grid; 
	var gridManager = null; 
	var cardgrid;
	var cardgridManager = null;
	var userUpdateStr;
	var editor;
	$(function() {
		$("#layout1").ligerLayout({
			bottomHeight  : '270',
			heightDiff: 5
		});
		
		loadHead(null);
// 		loadHotkeys();
	});

	function this_close() {
		frameElement.dialog.close();
	}

	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
// 		grid.options.parms.push({
// 			name : 'ass_in_no',
// 			value : $("#ass_in_no").val() == "" ? "0" : $("#ass_in_no").val()
// 		});
		grid.loadData(grid.where);
	}
	function queryCard(theme_id) {
		cardgrid.options.parms = [];
		cardgrid.options.newPage = 1;
		cardgrid.options.parms.push({
			name : 'theme_id',
			value : theme_id
		});	
		cardgrid.loadData(cardgrid.where);
	}
	

	function loadHead() {
			grid = $("#maingrid").ligerGrid({
						columns : [{
										display : '招聘主题',
										name : 'theme_name',
										align : 'left',
										textField : 'theme_name',
										width : '160',
										render : function(rowdata, rowindex,
												value) {
											if(rowdata.theme_state == '01'){
												return "<a href=javascript:openThemeUpdate('"+rowdata.group_id   + "|" + rowdata.hos_id   + "|" + rowdata.theme_id+"')>"+rowdata.theme_name+"</a>";
											}else{
												return rowdata.theme_name;
											}
												
										}
									},
									{
										display : '开始日期',
										name : 's_time',
										align : 'left',
										textField : 'start_time',
										width : '130'
									},
									{
										display : '结束日期',
										name : 'e_time',
										textField : 'end_time',
										align : 'left',
										width : '100'
									},
									{
										display : '联系人',
										name : 'user_name',
										textField:'username',
										align : 'left',
										width : '100'
									},
									{
										display : '联系电话',
										name : 'theme_phone',
										align : 'left',
										width : '100'
									},
									{
										display : '电子邮件',
										name : 'theme_email',
										align : 'right',
										width : '150'

									},
									{
										display : '地址',
										name : 'theme_address',
										align : 'right',
										width : '100'
									},
									{
										display : '描述',
										name : 'describe',
										align : 'left',
										width : '100'
									},
									{
										display : '招聘状态',
										name : 'theme_state',
										align : 'left',
										width : '100',
										render : function(rowdata, rowindex,
												value) {
											if(rowdata.theme_state == '1'){
												return "未发布";
											}
											if(rowdata.theme_state == '2'){
												return '发布中'
											}
											if(rowdata.theme_state == '3'){
												return '招聘结束'
											}
										}
									}],
							dataAction : 'server',
							dataType : 'server',
							url:'queryRecruitTheme.do?tab_code=HR_RECRUIT_THEME&&group_id=100&&hos_id=102',
							usePager : true,
							width : '100%',
							height : '50%',
							checkbox : true,
							enabledEdit : false,
							alternatingRow : true,
							isScroll : true,
							rownumbers : true,
							selectRowButtonOnly : true,//heightDiff: -10,
							toolbar : {
								items : [ {
									text : '新增',
									id : 'addTheme',
									click : addTheme,
									icon : 'add'
								},{
									line : true
								},{
									text : '删除',
									id : 'deleteRecruitTheme',
									click : deleteTheme,
									icon : 'delete'
								}]
							},
							onDblClickRow  : function(data,rowid,rowdata)  {
								var theme_id = data.theme_id;
								console.log(theme_id);
								queryCard(theme_id);
							}
						});

		gridManager = $("#maingrid").ligerGetGridManager();
		
		cardgrid = $("#cardgrid").ligerGrid({
				columns : [{
								display : '招聘岗位',
								name : 'station_name',
								align : 'left',
								width:100
							},
							{
								display : '招聘科室',
								name : 'dept_id_name',
								align : 'left',
								width:100
							},
							{
								display : '招聘主题',
								name : 'theme_name',
								align : 'left',
								width: 160
							},
							{
								display : '招聘人数',
								name : 'demand_num',
								align : 'left',
								width:100
							},
							{
								display : '专业要求',
								name : 'demand_major_name',
								align : 'left',
								width:100
							},
							{
								display : '学历',
								name : 'demand_edu_name',
								align : 'left',
								width:100
							},
							{
								display : '招聘英语级别',
								name : 'demand_cet_name',
								align : 'left',
								width:100
							},
							{
								display : '性别',
								name : 'demand_sex_name',
								align : 'left',
								width:100
							},
							{
								display : '任职资格',
								name : 'demand_qualify',
								align : 'left',
								width:100
							},
							{
								display : '任职要求',
								name : 'demand_require',
								align : 'left',
								width:100
							},
							{
								display : '审批标志',
								name : 'demand_state',
								align : 'left',
								width:100,
								render : function(rowdata, rowindex,
										value) {
									if(rowdata.demand_state == "01"){
										return "审核中";
									}
									if(rowdata.demand_state == "02"){
										return '通过'
									}
									if(rowdata.demand_state == "03"){
										return '拒绝'
									}
									if(rowdata.demand_state == "04"){
										return '删除'
									}
									if(rowdata.demand_state == "05"){
										return '存档'
									}
								}
							},
							{
								display : '备注',
								name : 'demand_note',
								width:100,
								align : 'left'
							}
							 ],
					dataAction : 'server',
					dataType : 'server',
					url:'queryDemandByTheme.do?isCheck=false&tab_code=HR_RECRUIT_DEMAND',
					usePager : true,
					width : '100%',
					height : '97%',
					checkbox : true,
					isScroll : true,
					rownumbers : true,
					delayLoad : true,//初始化明细数据
					selectRowButtonOnly : true,//heightDiff: -10,
					toolbar : {
						items : [ {
							text : '删除',
							id : 'delete',
							click : deleteDemand,
							icon : 'delete'
						}]
					}
				});
		cardgridManager = $("#cardgrid").ligerGetGridManager();
		
	}

	
	function addTheme(){
		$.ligerDialog.open({
			title: '新增',
			height: 350,
			width: 700,
			url: 'recruitThemeAddPage.do?isCheck=false',
			modal: false, showToggle: false, showMax: false, showMin: false, isResize: true
		});
		
	}

	
	function deleteDemand(){
		var data = cardgridManager.getCheckedRows();
		var theme_id;
		var err ="";
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var paramVo = [];
			$(data).each(function() {
				        theme_id = this.theme_id;
						if(this.demand_state == '01'){
							if (isnull(this.group_id)) {
								cardgridManager.deleteSelectedRow();
							} else {
								paramVo.push({
	        						demand_id: this.demand_id,
	        						group_id: this.group_id,
	        						hos_id: this.hos_id,
	        						demand_state: '04'
	        					});
							}
						}else{
							if(err == ""){
								err = this.row_id;
							}else{
								err = err+ "、"+this.row_id;
							}
						}
					});
			if (err != "") {
				$.ligerDialog.warn("第["+err+"]行招聘需求不是审核中状态，删除失败！");
				return;
			}
			$.ligerDialog.confirm(
							'确定删除?',
							function(yes) {
								if (yes) {
									ajaxJsonObjectByUrl(
											"updateDemandState01Batch.do?isCheck=false&tab_code=HR_RECRUIT_DEMAND",
											{
												ParamVo : JSON.stringify(paramVo)
											},
											function(responseData) {
												if (responseData.state == "true") {
													queryCard(theme_id);
												}
											});
								}
							});
		}
		
	}
	
	var rowindex_id = "";
	var column_name = "";
	
	
	function deleteTheme() {
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var Param = [];
			var err='';
			$(data).each(
					function() {
						if(this.theme_state == '01'){
							if (isnull(this.group_id)) {
								gridManager.deleteSelectedRow();
							} else {
								var rowdata = this;
								rowdata.tab_code = 'HR_RECRUIT_THEME';
								Param.push(rowdata);
							}
						}
						else{
							if(err == ""){
								err = this.row_id;
							}else{
								err += "、"+this.row_id;
							}
						}
					});
			if (err != "") {
				$.ligerDialog.warn("第["+err+"]行招聘主题不是未发布状态，删除失败！");
				return;
			}
			$.ligerDialog.confirm(
							'确定删除?',
							function(yes) {
								if (yes) {
									ajaxJsonObjectByUrl(
											"deleteRecruitTheme.do?isCheck=false",
											{
												paramVo :JSON.stringify(Param)
											},
											function(responseData) {
												if (responseData.state == "true") {
													query();
												}
											});
								}
							});
		}
	}
	  function openThemeUpdate(obj){
	    	
			var vo = obj.split("|");
			
			var parm = "&group_id="+vo[0]+"&hos_id="+vo[1]+"&theme_id="+vo[2]+"&tab_code=HR_RECRUIT_THEME";
			
	    	$.ligerDialog.open({
				title: '修改',
				height: 350,
				width: 700,
				url: 'recruitThemeUpdatePage.do?isCheck=false'+ parm,
				modal: false, showToggle: false, showMax: false, showMin: false, isResize: true,
// 				parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
			}); 
	    }

	//键盘事件
// 	function loadHotkeys() {

// 		hotkeys('Q', query);
// 		hotkeys('A', save);
// 		hotkeys('D', remove);

// 	}
</script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="layout1">
		<div id="layout2" position="center" title="招聘主题" >
				<div id="maingrid"></div>
		</div>
		<div id="layout3" position="bottom" title="招聘岗位">
				<div id="cardgrid"></div>
		</div>
	</div>

</body>
</html>
