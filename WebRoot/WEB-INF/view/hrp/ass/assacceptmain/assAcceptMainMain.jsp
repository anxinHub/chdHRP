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
		//加载数据
		loadHead(null);
		loadHotkeys();
		$("#contract_id").ligerTextBox({
			width : 160
		});
	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'contract_id',
			value : liger.get("contract_id").getValue().split("@")[0]
		});
		grid.options.parms.push({
			name : 'ven_id',
			value : liger.get("ven_id").getValue().split("@")[0]
		});
		grid.options.parms.push({
			name : 'ven_no',
			value : liger.get("ven_id").getValue().split("@")[1]
		});
		grid.options.parms.push({
			name : 'dept_id',
			value : liger.get("dept_id").getValue().split("@")[0]
		});
		grid.options.parms.push({
			name : 'dept_no',
			value : liger.get("dept_id").getValue().split("@")[1]
		});
		grid.options.parms.push({
			name : 'accept_date1',
			value : $("#accept_date_begin").val()
		});
		grid.options.parms.push({
			name : 'accept_date2',
			value : $("#accept_date_end").val()
		});
		grid.options.parms.push({
			name : 'state',
			value : liger.get("state").getValue()
		});

		//加载查询条件
		grid.loadData(grid.where);
	}
	function loadHead() {
		grid = $("#maingrid").ligerGrid(
				{
					columns : [
							{
								display : '验收单号',
								name : 'accept_no',
								align : 'left',
								width : 100,
								frozen : true,
								render : function(rowdata, rowindex, value) {

									return "<a href=javascript:openUpdate('"
											+ rowdata.group_id + "|"
											+ rowdata.hos_id + "|"
											+ rowdata.copy_code + "|"
											+ rowdata.accept_id + "')>"
											+ rowdata.accept_no + "</a>";
								}
							}, {
								display : '摘要',
								name : 'accept_desc',
								align : 'left',
								width : 150,
								frozen : true,
							}, {
								display : '验收日期',
								name : 'accept_date',
								align : 'left',
								width : 90,
								frozen : true,
							}, {
								display : '摘要',
								name : 'summary',
								align : 'left',
								width : 100
							}, {
								display : '合同',
								name : 'contract_name',
								align : 'left',
								width : 80
							}, {
								display : '供应商',
								name : 'ven_name',
								align : 'left',
								width : 200
							}, {
								display : '设备来源',
								name : 'device_name',
								align : 'left',
								width : 100
							}, {
								display : '申购类别',
								name : 'buy_name',
								align : 'left',
								width : 100
							}, {
								display : '资金来源',
								name : 'source_name',
								align : 'left',
								width : 100
							}, {
								display : '验收科室',
								name : 'dept_name',
								align : 'left',
								width : 100
							}, {
								display : '验收人',
								name : 'accept_emp_name',
								align : 'left',
								width : 100
							}, {
								display : '制单人',
								name : 'create_emp_name',
								align : 'left',
								width : 100
							}, {
								display : '制单日期',
								name : 'create_date',
								align : 'left',
								width : 100
							}, {
								display : '审核人',
								name : 'audit_emp_name',
								align : 'left',
								width : 100
							}, {
								display : '审核日期',
								name : 'audit_date',
								align : 'left',
								width : 100
							}, {
								display : '状态',
								name : 'state',
								align : 'left',
								width : 80,
								render : function(rowdata, rowindex, value) {
									if (rowdata.state == 0) {
										return "新建";
									}
									return "审核";
								}

							} ],
					dataAction : 'server',
					dataType : 'server',
					usePager : true,
					url : 'queryAssAcceptMain.do',
					width : '100%',
					height : '100%',
					checkbox : true,
					rownumbers : true,
					delayLoad : true,
					selectRowButtonOnly : true,//heightDiff: -10,
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
						},{
							line : true
						}, {
							text : '生成入库单',
							id : 'add',
							click : generate,
							icon : 'add'
						}, {
							line : true
						/* }, {
							text : '按合同生成单据（<u>H</u>）',
							id : 'initContract',
							click : initContract,
							icon : 'refresh'
						}, {
							line : true
						}, {
							text : '按安装生成单据（<u>Z</u>）',
							id : 'initInstall',
							click : initInstall,
							icon : 'refresh'
						}, {
							line : true */
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
						} ]
					},
					onDblClickRow : function(rowdata, rowindex, value) {
						openUpdate(rowdata.group_id + "|" + rowdata.hos_id
								+ "|" + rowdata.copy_code + "|"
								+ rowdata.accept_id);
					}
				});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	function add_open() {

		parent.$.ligerDialog
				.open({
					url : 'hrp/ass/assacceptmain/assAcceptMainAddPage.do?isCheck=false',
					height : $(window).height(),
					width : $(window).width(),
					modal : true,
					showToggle : false,
					showMax : true,
					showMin : false,
					isResize : true,
					parentframename : window.name,
					title : '资产验收添加'
				});

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
								+ this.copy_code + "@" + this.accept_id);

					});
			$.ligerDialog.confirm('确定审核?', function(yes) {

				if (yes) {
					ajaxJsonObjectByUrl("updateToExamine.do?isCheck=false", {
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

	function initContract() {
		parent.$.ligerDialog
				.open({
					url : 'hrp/ass/assacceptmain/assAcceptMainInitPage.do?isCheck=false',
					height : $(window).height(),
					width : $(window).width(),
					modal : true,
					showToggle : false,
					showMax : true,
					showMin : false,
					isResize : true,
					parentframename : window.name,
					title : '按合同生成'
				});

	}
	function initInstall() {
		parent.$.ligerDialog
				.open({
					url : 'hrp/ass/assacceptmain/assAcceptMainInitInstallPage.do?isCheck=false',
					height : $(window).height(),
					width : $(window).width(),
					modal : true,
					showToggle : false,
					showMax : true,
					showMin : false,
					isResize : true,
					parentframename : window.name,
					title : '按安装生成'
				});
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
								+ this.copy_code + "@" + this.accept_id);

					});
			$.ligerDialog.confirm('确定销审?', function(yes) {

				if (yes) {
					ajaxJsonObjectByUrl("updateNotToExamine.do?isCheck=false",
							{
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
	function remove() {

		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var ParamVo = [];
			$(data).each(
					function() {
						ParamVo.push(this.group_id + "@" + this.hos_id + "@"
								+ this.copy_code + "@" + this.accept_id + "@" + this.accept_no);
					});
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteAssAcceptMain.do?isCheck=false",
							{
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

	function generate(){
		/* var data = gridManager.getCheckedRows();
		if (data.length == 0 || data.length > 1) {
			$.ligerDialog.error('请选择一行');
		} else {
		} */
		parent.$.ligerDialog
		.open({
			url : 'hrp/ass/assacceptmain/assAcceptInMainPage.do?isCheck=false',
			height : $(window).height(),
			width : $(window).width(),
			modal : true,
			showToggle : false,
			showMax : true,
			showMin : false,
			isResize : true,
			parentframename : window.name,
			title : '生成入库单'
		});
		
	}
	
	function openUpdate(obj) {

		var vo = obj.split("|");
		if("null"==vo[3]){
			return false;
			
		}
		var param = "group_id=" + vo[0] + "&" + "hos_id=" + vo[1] + "&"
				+ " copy_code=" + vo[2] + "&" + "accept_id=" + vo[3];
		parent.$.ligerDialog
				.open({
					url : 'hrp/ass/assacceptmain/assAcceptMainUpdatePage.do?isCheck=false&'
							+ param,
					height : $(window).height(),
					width : $(window).width(),
					modal : true,
					showToggle : false,
					showMax : true,
					showMin : false,
					isResize : true,
					parentframename : window.name,
					title : '资产验收修改'
				});

	}
	function loadDict() {

		var param = {
			query_key : ''
		};

		autocompleteAsync("#contract_id",
				"../queryContractMain.do?isCheck=false", "id", "text", true,
				true, param, true,null,"300");

		autocompleteAsync("#ven_id", "../queryHosSupDict.do?isCheck=false",
				"id", "text", true, true, param, true, null, "160");

		autocompleteAsync("#dept_id", "../queryDeptDict.do?isCheck=false",
				"id", "text", true, true, param, true);

		autocompleteAsync("#audit_emp",
				"../../../hrp/sys/queryUserDict.do?isCheck=false", "id",
				"text", true, true, param, true);

		autocompleteAsync("#accept_emp",
				"../../../hrp/sys/queryUserDict.do?isCheck=false", "id",
				"text", true, true, param, true);

		autocompleteAsync("#create_emp",
				"../../../hrp/sys/queryUserDict.do?isCheck=false", "id",
				"text", true, true, param, true);

		autodate("#accept_date_begin","YYYY-mm-dd","month_first");

		autodate("#accept_date_end","YYYY-mm-dd","month_last");

		$("#accept_date_begin").ligerTextBox({
			width : 110
		});

		$("#accept_date_end").ligerTextBox({
			width : 110
		});

		/* $("#state").ligerComboBox({
			width : 228
		}); */
		$('#state').ligerComboBox({
			data:[{id:0,text:'新建'},{id:1,text:'审核'}],
			valueField: 'id',
            textField: 'text',
			cancelable:true
		});

	}
	//键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);

		hotkeys('A', add);
		hotkeys('D', remove);

		hotkeys('S', toExamine);

		hotkeys('X', notToExamine);

		hotkeys('H', initContract);

		hotkeys('Z', initInstall);

		hotkeys('P', printDate);

	}
	 //打印回调方法
    function lodopPrint(){
    	var head="<table class='head' width='100%'><tr><td>单位：${hos_name}</td></tr>";
 		head=head+"<tr><td>制单日期："+$("#create_date_start").val() +" 至  "+ $("#create_date_end").val()+"</td></tr>";
 		head=head+"</table>";
 		grid.options.lodop.head=head; 
 		grid.options.lodop.fn=renderFunc;
 		grid.options.lodop.title="资产验证";
    }

	//模板设置
    function printSet(){
		  
		  var useId=0;//统一打印
			if('${ass_05013}'==1){
				//按用户打印
				useId='${user_id }';
			}
			
	//POBrowser.openWindow('../../../../PageOffice/printFormSet.do?isCheck=false&template_code=01001&userid=${user_id}', 'fullscreen=yes');
	officeFormTemplate({template_code:"05013",use_id : useId});

    }

    //打印
	function printDate() {
		 var useId=0;//统一打印
	 		if('${ass_05013}'==1){
	 			//按用户打印
	 			useId='${user_id }';
	 		}
	 		var data = gridManager.getCheckedRows();
			if (data.length == 0){
				$.ligerDialog.error('请选择行');
			}else{
				
				var accept_id ="" ;
				var accept_nos = "";
				$(data).each(function (){		
					accept_id  += "'"+this.accept_id+"',"
						
				});
			}
	    	var para={
	    		
	       
	    			template_code:'05013',
	    			class_name:"com.chd.hrp.ass.serviceImpl.accept.AssAcceptMainServiceImpl",
	    			method_name:"queryAssAcceptDY",
					
	    			paraId :accept_id.substring(0,accept_id.length-1),
	    			isPrintCount:false,//更新打印次数
	    			isPreview:true,//预览窗口，传绝对路径
	    			use_id:useId,
	    			p_num:1
	    	};
	    	ajaxJsonObjectByUrl("queryAcceptMainState.do?isCheck=false",{paraId :accept_id.substring(0,accept_id.length-1),state:1},function(responseData){
				if (responseData.state == "true") {
					officeFormPrint(para);
				}
			});
    }
	
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" border="0" width="100%">

		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">验收日期：</td>
			<td align="left" class="l-table-edit-td" width="5%"><input
				name="accept_date_begin" type="text" id="accept_date_begin"
				class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="left" width="2%">至：</td>
			<td align="left"><input name="accept_date_end" type="text"
				id="accept_date_end" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<!--             onFocus="WdatePicker({minDate:'#F{$dp.$D(\'accept_date_begin\');}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td> -->

			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">验收科室：</td>
			<td align="left" class="l-table-edit-td" width="5%"><input name="dept_id"
				type="text" id="dept_id" /></td>
			<td align="right" class="l-table-edit-td" width="20%">合同：</td>
			<td align="left" class="l-table-edit-td"><input
				name="contract_id" type="text" id="contract_id" /></td>

		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态：</td>
			<td align="left" class="l-table-edit-td" colspan="3">
			<!-- <select
				id="state" name="state">
					<option value="">全部</option>
					<option value="0">新建</option>
					<option value="1">审核</option>
			</select> -->
			<input  name="state" type="text" id="state"/>
			</td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">供&nbsp;&nbsp;应&nbsp;&nbsp;商：</td>
			<td align="left" class="l-table-edit-td" colspan="3"><input
				name="ven_id" type="text" id="ven_id" /></td>
		</tr>
	</table>

	<div id="maingrid"></div>
</body>
</html>
