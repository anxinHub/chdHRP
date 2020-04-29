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
<script type="text/javascript">
	var grid;
	var gridManager = null;
	var userUpdateStr;
	$(function() {
		loadDict();//加载下拉框

		loadHotkeys();

		//加载数据
		loadHead(null);

	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件 
		grid.options.parms.push({
			name : 'plan_no',
			value : $("#plan_no").val()
		});

		grid.options.parms.push({
			name : 'plan_year1',
			value : $("#plan_year1").val()
		});

		grid.options.parms.push({
			name : 'plan_year2',
			value : $("#plan_year2").val()
		});

		grid.options.parms.push({
			name : 'dept_id',
			value : liger.get("dept_id").getValue().split("@")[0]
		});

		grid.options.parms.push({
			name : 'check_emp',
			value : liger.get("check_emp").getValue()
		});

		grid.options.parms.push({
			name : 'check_date1',
			value : $("#check_date1").val()
		});

		grid.options.parms.push({
			name : 'check_date2',
			value : $("#check_date2").val()
		});
		grid.options.parms.push({
			name : 'create_date1',
			value : $("#create_date1").val()
		});
		grid.options.parms.push({
			name : 'create_date2',
			value : $("#create_date2").val()
		});
		grid.options.parms.push({
			name : 'is_add',
			value : liger.get("is_add").getValue()
		});

		grid.options.parms.push({
			name : 'state',
			value : liger.get("state").getValue()
		});

		grid.options.parms.push({
			name : 'buy_code',
			value : liger.get("buy_code").getValue()
		});

		//加载查询条件
		grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
	}

	function loadHead() {
		grid = $("#maingrid")
				.ligerGrid(
						{
							columns : [
									{
										display : '计划号',
										name : 'plan_no',
										align : 'left',
										width : '110',
										frozen : true,
										render : function(rowdata, rowindex,
												value) {
											if (rowdata.note == "合计") {
												return '';
											}

											if (rowdata.is_add == 1) {
												return "<a href=javascript:openUpdate('"
														+ rowdata.group_id
														+ "|"
														+ rowdata.hos_id
														+ "|"
														+ rowdata.copy_code
														+ "|"
														+ rowdata.plan_id
														+ "')>"
														+ rowdata.plan_no
														+ "&nbsp;&nbsp;<font color='red'><b>追</b></font></a>";
											} else {
												return "<a href=javascript:openUpdate('"
														+ rowdata.group_id
														+ "|"
														+ rowdata.hos_id
														+ "|"
														+ rowdata.copy_code
														+ "|"
														+ rowdata.plan_id
														+ "')>"
														+ rowdata.plan_no
														+ "</a>";
											}
										}
									},
									{
										display : '摘要',
										name : 'note',
										align : 'left',
										width : '150',
										frozen : true,
										render : function(rowdata, rowindex,
												value) {
											var note = "";
											if(rowdata.note == null){
												note = "追溯购置申请";
											}else{
												note = rowdata.note;
											}
											
											if (rowdata.is_import == 1) {
												return "<a href=javascript:openViewApply('"
												+ rowdata.group_id
												+ "|"
												+ rowdata.hos_id
												+ "|"
												+ rowdata.copy_code
												+ "|"
												+ rowdata.plan_id
												+ "|"
												+ rowdata.state
												+ "')>"
												+ note
												+ "</a>";
											} else if(rowdata.is_import == 2){
												
												if(rowdata.note == null){
													note = "追溯医院购置计划";
												}else{
													note = rowdata.note;
												}
												return "<a href=javascript:openViewGroupPlan('"
												+ rowdata.group_id
												+ "|"
												+ rowdata.hos_id
												+ "|"
												+ rowdata.copy_code
												+ "|"
												+ rowdata.plan_id
												+ "|"
												+ rowdata.state
												+ "')>"
												+ note
												+ "</a>";
											}else{
												return rowdata.note;
											}
										}
										
									},
									{
										display : '购置年度',
										name : 'plan_year',
										align : 'left',
										width : '90',
										frozen : true
									},
									{
										display : '编制科室名称',
										name : 'dept_no',
										align : 'left',
										width : '150',
										render : function(rowdata, rowindex,
												value) {
											return rowdata.dept_name;
										}
									},
									{
										display : '计划金额',
										name : 'plan_money',
										align : 'right',
										width : '110',
										render : function(rowdata, rowindex,
												value) {
											return formatNumber(
													rowdata.plan_money == null ? 0
															: rowdata.plan_money,
													'${ass_05005}',
													1);
										}
									},
									{
										display : '制单人',
										name : 'create_emp',
										align : 'left',
										width : '110',
										render : function(rowdata, rowindex,
												value) {
											return rowdata.create_emp_name;
										}
									},
									{
										display : '制单日期',
										name : 'create_date',
										align : 'left',
										width : '100'
									},
									{
										display : '审核人',
										name : 'check_emp',
										align : 'left',
										width : '110',
										render : function(rowdata, rowindex,
												value) {
											return rowdata.check_emp_name;
										}
									},
									{
										display : '审核日期',
										name : 'check_date',
										align : 'left',
										width : '100'
									},
									{
										display : '是否追加计划',
										name : 'is_add',
										align : 'left',
										width : '90',
										render : function(rowdata, rowindex,
												value) {
											if (rowdata.is_add == 0) {
												return "否";
											} else if (rowdata.is_add == 1) {
												return "是";
											}
										}
									},
									{
										display : '状态',
										name : 'state',
										align : 'left',
										width : '90',
										render : function(rowdata, rowindex,
												value) {
											if (rowdata.state == 0) {
												return "新建";
											} else if (rowdata.state == 1) {
												return "审核";
											}
										}
									},
									{
										display : '采购方式',
										name : 'buy_code',
										align : 'left',
										width : '100',
										render : function(rowdata, rowindex,
												value) {
											if (rowdata.buy_code == 0) {
												return "自主采购";
											} else if (rowdata.buy_code == 1) {
												return "集中采购";
											}

										}
									} ],
							dataAction : 'server',
							dataType : 'server',
							usePager : true,
							url : 'queryAssPlanDeptGroup.do',
							width : '100%',
							height : '100%',
							checkbox : true,
							rownumbers : true,
							delayLoad : true,
							selectRowButtonOnly : true,//heightDiff: -10,
							checkBoxDisplay : isCheckDisplay,
							toolbar : {
								items : [ {
									text : '查询（<u>E</u>）',
									id : 'search',
									click : query,
									icon : 'search'
								}, {
									line : true
								}, {
									text : '添加（<u>A</u>）',
									id : 'add',
									click : add_open,
									icon : 'add'
								}, {
									line : true
								}, {
									text : '删除（<u>D</u>）',
									id : 'delete',
									click : remove,
									icon : 'delete'
								}, {
									line : true
								}, {
									text : '审核（<u>S</u>）',
									id : 'toExamine',
									click : toExamine,
									icon : 'ok'
								}, {
									line : true
								}, {
									text : '销审（<u>X</u>）',
									id : 'notToExamine',
									click : notToExamine,
									icon : 'right'
								}, {
									line : true
								}, {
									text : '引入购置申请（<u>U</u>）',
									id : 'importApply',
									click : importApplys,
									icon : 'refresh'
								}, {
									line : true
								}, {
									text : '引入医院计划（<u>O</u>）',
									id : 'importHosPlanDept',
									click : importHosPlanDept,
									icon : 'refresh'
								}, {
									line : true
								}, {
									text : '批量打印（<u>P</u>）',
									id : 'print',
									click : printDate,
									icon : 'print'
								},{
									line : true
								},{
									text :'模板设置',
									id   :'printSet',
									click:printSet,
									icon : 'print'
								}

								]
							},
							onDblClickRow : function(rowdata, rowindex, value) {//双击行事件
								if(rowdata.note == '合计'){
									return;
								}else{
									openUpdate(rowdata.group_id + "|"
											+ rowdata.hos_id + "|"
											+ rowdata.copy_code + "|"
											+ rowdata.plan_id + "|" + rowdata.state);
								}

							}
						});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	function isCheckDisplay(rowdata) {
		if (rowdata.note == "合计")
			return false;
		return true;
	}
	
	function openViewApply(obj){
		var vo = obj.split("|");

		var parm = "group_id=" + vo[0] + "&" + "hos_id=" + vo[1] + "&"
				+ " copy_code=" + vo[2] + "&" + "plan_id=" + vo[3];

		parent.$.ligerDialog.open({
			title : '购置申请追溯',
			height : $(window).height(),
			width : $(window).width(),
			url : 'hrp/ass/assplandeptgroup/assViewApplyGroupPage.do?isCheck=false&'
					+ parm,
			modal : true,
			showToggle : false,
			showMax : true,
			showMin : false,
			isResize : true,
			parentframename : window.name, //用于parent弹出层调用本页面的方法或变量
		});
	}
	
	function openViewGroupPlan(obj){
		var vo = obj.split("|");

		var parm = "group_id=" + vo[0] + "&" + "hos_id=" + vo[1] + "&"
				+ " copy_code=" + vo[2] + "&" + "plan_id=" + vo[3];

		parent.$.ligerDialog.open({
			title : '医院购置计划追溯',
			height : $(window).height(),
			width : $(window).width(),
			url : 'hrp/ass/assplandeptgroup/assViewPlanGroupPage.do?isCheck=false&'
					+ parm,
			modal : true,
			showToggle : false,
			showMax : true,
			showMin : false,
			isResize : true,
			parentframename : window.name, //用于parent弹出层调用本页面的方法或变量
		});
	}
	

	function importHosPlanDept() {

		parent.$.ligerDialog
				.open({
					title : '引入医院计划单',
					height : $(window).height(),
					width : $(window).width(),
					url : 'hrp/ass/assplandeptgroup/assImportHosPlanDeptGroupPage.do?isCheck=false&',
					modal : true,
					showToggle : false,
					showMax : true,
					showMin : false,
					isResize : true,
					parentframename : window.name, //用于parent弹出层调用本页面的方法或变量
				});

	}
	function add_open() {
		parent.$.ligerDialog
				.open({
					title : '购置计划添加',
					height : $(window).height(),
					width : $(window).width(),
					url : 'hrp/ass/assplandeptgroup/assPlanDeptGroupAddPage.do?isCheck=false&',
					modal : true,
					showToggle : false,
					showMax : true,
					showMin : false,
					isResize : true,
					parentframename : window.name, //用于parent弹出层调用本页面的方法或变量
				});
	}

	function remove() {

		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var ParamVo = [];
			$(data).each(
					function() {
						ParamVo.push(this.group_id + "@" + this.hos_id + "@"
								+ this.copy_code + "@" + this.plan_id)
					});
			$.ligerDialog.confirm('确定删除?', function(yes) {

				if (yes) {
					ajaxJsonObjectByUrl("deleteAssPlanDeptGroup.do", {
						ParamVo : ParamVo.toString()
					}, function(responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			});
		}
	}

	//引入购置申请单
	function importApplys() {
		parent.$.ligerDialog
				.open({
					title : '引入购置申请单',
					height : $(window).height(),
					width : $(window).width(),
					url : 'hrp/ass/assplandeptgroup/assPlanDeptGroupImportApplyPage.do?isCheck=false&',
					modal : true,
					showToggle : false,
					showMax : true,
					showMin : false,
					isResize : true,
					parentframename : window.name, //用于parent弹出层调用本页面的方法或变量
				});
	}

	function downTemplate() {

		location.href = "downTemplate.do?isCheck=false";
	}

	function openUpdate(obj) {

		var vo = obj.split("|");
		if("null"==vo[3]){
			return false;
			
		}
		var parm = "group_id=" + vo[0] + "&" + "hos_id=" + vo[1] + "&"
				+ " copy_code=" + vo[2] + "&" + "plan_id=" + vo[3]

		parent.$.ligerDialog
				.open({
					title : '购置计划修改',
					height : $(window).height(),
					width : $(window).width(),
					url : 'hrp/ass/assplandeptgroup/assPlanDeptGroupUpdatePage.do?isCheck=false&'
							+ parm,
					modal : true,
					showToggle : false,
					showMax : true,
					showMin : false,
					isResize : true,
					parentframename : window.name, //用于parent弹出层调用本页面的方法或变量
				});

	}
	function loadDict() {

		var param = {
			query_key : ''
		};

		//字典下拉框
		//默认年
		autodate("#plan_year", "YYYY");

		autodate("#plan_year1", "YYYY");

		autodate("#plan_year2", "YYYY"); 
		//科室编码
		autocomplete("#dept_id", "../queryDeptDict.do?isCheck=false", "id",
				"text", true, true, param, true);
		//审核人
		autocomplete("#check_emp",
				"../../../hrp/sys/queryUserDict.do?isCheck=false", "id",
				"text", true, true, param, true);
		$("#plan_no").ligerTextBox({
			width : 160
		});

		$("#plan_year1").ligerTextBox({
			width : 94
		});

		$("#plan_year2").ligerTextBox({
			width : 94
		});

		$("#check_date1").ligerTextBox({
			width : 94
		});

		$("#check_date2").ligerTextBox({
			width : 94
		});

		$("#create_date1").ligerTextBox({
			width : 94
		});

		$("#create_date2").ligerTextBox({
			width : 94
		});

		//是否追加计划
		/* $("#is_add").ligerComboBox({
			width : 160
		}); */
		$('#is_add').ligerComboBox({
			data:[{id:0,text:'否'},{id:1,text:'是'}],
			valueField: 'id',
            textField: 'text',
			cancelable:true,
			width:160
		});
		//状态
		/* $("#state").ligerComboBox({
			width : 160
		}); */
		$('#state').ligerComboBox({
			data:[{id:0,text:'新建'},{id:1,text:'审核'}],
			valueField: 'id',
            textField: 'text',
			cancelable:true,
			width:160
		});
		//采购方式
		/* $("#buy_code").ligerComboBox({
			width : 160
		}); */
		$('#buy_code').ligerComboBox({
			data:[{id:0,text:'自主采购'},{id:1,text:'集中采购'}],
			valueField: 'id',
            textField: 'text',
			cancelable:true,
			width:160
		});
	}
	//键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);

		hotkeys('A', add_open);
		hotkeys('D', remove);

		hotkeys('S', toExamine);

		hotkeys('X', notToExamine);

		hotkeys('P', printDate);

		hotkeys('U', importApplys);

		hotkeys('I', approves);

		hotkeys('O', importHosPlanDept);

	}

	function approves() {
		var ParamVo = [];
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var row = gridManager.getSelectedRow();

			if (row.state != 1) {

				$.ligerDialog.error('该数据没有审核则不能进行审批');

				return false;
			} else {
				$(data).each(
						function() {
							ParamVo
									.push(this.group_id + "@" + this.hos_id
											+ "@" + this.copy_code + "@"
											+ this.plan_id);
						});
				$.ligerDialog
						.open({
							url : 'assPlanDeptGroupApprovePage.do?isCheck=false&ParamVo='
									+ ParamVo.toString(),
							data : {},
							height : 300,
							width : 500,
							title : '审批意见',
							modal : true,
							showToggle : false,
							showMax : false,
							showMin : false,
							isResize : true,
							buttons : [ {
								text : '确定',
								onclick : function(item, dialog) {
									dialog.frame.savePlanDeptApprove();
								},
								cls : 'l-dialog-btn-highlight'
							}, {
								text : '取销',
								onclick : function(item, dialog) {
									dialog.close();
								}
							} ]
						});
			}
		}
	}

	function toExamine() {
		var ParamVo = [];
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			$(data).each(
					function() {
						ParamVo.push(this.group_id + "@" + this.hos_id + "@"
								+ this.copy_code + "@" + this.plan_id);

					});
			$.ligerDialog.confirm('确定审核?', function(yes) {

				if (yes) {
					ajaxJsonObjectByUrl(
							"updateToExamineGroup.do?isCheck=false", {
								ParamVo : ParamVo.toString()
							}, function(responseData) {
								if (responseData.state == "true") {
									query();
								}
							});
				}
			})
		}
	}
	function notToExamine() {
		var ParamVo = [];
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			$(data).each(
					function() {
						ParamVo.push(this.group_id + "@" + this.hos_id + "@"
								+ this.copy_code + "@" + this.plan_id);
					});
			$.ligerDialog.confirm('确定销核?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl(
							"updateNotToExamineGroup.do?isCheck=false", {
								ParamVo : ParamVo.toString()
							}, function(responseData) {
								if (responseData.state == "true") {
									query();
								}
							});
				}
			});
		}
	}
	 //打印回调方法
    function lodopPrint(){
    	var head="<table class='head' width='100%'><tr><td>单位：${hos_name}</td></tr>";
 		head=head+"<tr><td>制单日期："+$("#create_date_start").val() +" 至  "+ $("#create_date_end").val()+"</td></tr>";
 		head=head+"</table>";
 		grid.options.lodop.head=head; 
 		grid.options.lodop.fn=renderFunc;
 		grid.options.lodop.title="置购计划";
    }
	
    function printSet(){
		  
		  var useId=0;//统一打印
			if('${ass_05009}'==1){
				//按用户打印
				useId='${user_id }';
			}
	//POBrowser.openWindow('../../../../PageOffice/printFormSet.do?isCheck=false&template_code=01001&userid=${user_id}', 'fullscreen=yes');
	officeFormTemplate({template_code:"05009",use_id:useId});

    }
    //打印
	function printDate() {
		 var useId=0;//统一打印
	 		if('${ass_05009}'==1){
	 			//按用户打印
	 			useId='${user_id }';
	 		}
	 		var data = gridManager.getCheckedRows();
			if (data.length == 0){
				$.ligerDialog.error('请选择行');
			}else{
				
				var plan_id ="" ;
				var plan_nos = "";
				$(data).each(function (){		
					
					plan_id  += "'"+this.plan_id+"',"
						
				});
			
	    	var para={
	    		
	       
	    			template_code:'05009',
	    			class_name:"com.chd.hrp.ass.serviceImpl.plan.AssPlanGroupHosServiceImpl",
	    			method_name:"queryAssPlanGroupDY",
					
	    			paraId :plan_id.substring(0,plan_id.length-1),
	    			isPrintCount:false,//更新打印次数
	    			isPreview:true,//预览窗口，传绝对路径
	    			use_id:useId,
	    			p_num:1
	    	};
	    	ajaxJsonObjectByUrl("queryPlanGroupDeptState.do?isCheck=false",{paraId :plan_id.substring(0,plan_id.length-1),state:1},function(responseData){
				if (responseData.state == "true") {
					officeFormPrint(para);
				}
			});
    }
    }
	
	
/* 
	function printDate() {

		if (grid.getData().length == 0) {

			$.ligerDialog.error("请先查询数据！");

			return;
		}

		var selPara = {};

		$.each(grid.options.parms, function(i, obj) {

			selPara[obj.name] = obj.value;

		});

		var dates = getCurrentDate();

		var cur_date = dates.split(";")[2];
		//跨所有列:计算列数
		var colspan_num = grid.getColumns(1).length - 1;

		var printPara = {
			title : '集团购置计划',
			head : [ {
				"cell" : 0,
				"value" : "单位: ${hos_name}",
				"colspan" : colspan_num,
				"br" : true
			} ],
			foot : [ {
				"cell" : 0,
				"value" : "主管:",
				"colspan" : 2,
				"br" : false
			}, {
				"cell" : 2,
				"value" : "复核人:",
				"colspan" : colspan_num - 2,
				"br" : true
			}, {
				"cell" : 0,
				"value" : "制单人： ${user_name}",
				"colspan" : 2,
				"br" : false
			}, {
				"cell" : 2,
				"value" : "打印日期: " + cur_date,
				"colspan" : colspan_num - 2,
				"br" : true
			} ],
			columns : grid.getColumns(1),
			headCount : 2,//列头行数
			autoFile : true,
			type : 3
		};
		ajaxJsonObjectByUrl("queryAssPlanDeptGroup.do?isCheck=false", selPara,
				function(responseData) {
					printGridView(responseData, printPara);
				});

	} */
</script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit"
		width="100%">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">购置年度：</td>
			<td align="left" class="l-table-edit-td" width="5%"><input
				name="plan_year1" type="text" id="plan_year1" ltype="text"
				validate="{required:true,maxlength:20}" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy'})" /></td>
			<td align="center" class="l-table-edit-td" width="2%">至：</td>
			<td align="left" class="l-table-edit-td"><input
				name="plan_year2" type="text" id="plan_year2" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy'})" /></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 80px;"
				width="60">计&nbsp;&nbsp;划&nbsp;&nbsp;号：</td>
			<td align="left" class="l-table-edit-td"><input name="plan_no"
				type="text" id="plan_no" ltype="text"
				validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 80px;">编制科室：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_id"
				type="text" id="dept_id" ltype="text"
				validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">制单日期：</td>
			<td align="left" class="l-table-edit-td" width="5%"><input
				name="create_date1" type="text" id="create_date1" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="center" class="l-table-edit-td">至：</td>
			<td align="left" class="l-table-edit-td"><input
				name="create_date2" type="text" id="create_date2" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 80px;">采购方式：</td>
			<td align="left" class="l-table-edit-td">
			<!-- <select id="buy_code"
				name="buy_code">
					<option value="">全部</option>
					<option value="0">自主采购</option>
					<option value="1">集中采购</option>
			</select> -->
			<input  name="buy_code" type="text" id="buy_code"/>
			</td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 80px;">审&nbsp;&nbsp;核&nbsp;&nbsp;人：</td>
			<td align="left" class="l-table-edit-td"><input name="check_emp"
				type="text" id="check_emp" ltype="text"
				validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
		</tr>

		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">审核日期：</td>
			<td align="left" class="l-table-edit-td"><input
				name="check_date1" type="text" id="check_date1" ltype="text"
				validate="{required:true,maxlength:20}" class="Wdate"
				onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'check_date2\')}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="center" class="l-table-edit-td">至：</td>
			<td align="left" class="l-table-edit-td"><input
				name="check_date2" type="text" id="check_date2" ltype="text"
				validate="{required:true,maxlength:20}" class="Wdate"
				onFocus="WdatePicker({minDate:'#F{$dp.$D(\'check_date1\')}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 80px;">状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态：</td>
			<td align="left" class="l-table-edit-td">
			<!-- <select id="state"
				name="state">
					<option value="">全部</option>
					<option value="0">新建</option>
					<option value="1">审核</option>
			</select> -->
			<input  name="state" type="text" id="state"/>
			</td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 80px;">追加计划：</td>
			<td align="left" class="l-table-edit-td">
			<!-- <select id="is_add"
				name="is_add">
					<option value="">全部</option>
					<option value="0">否</option>
					<option value="1">是</option>
			</select> -->
			<input  name="is_add" type="text" id="is_add"/>
			</td>
			<td align="left"></td>
		</tr>

	</table>

	<div id="maingrid"></div>
</body>
</html>
