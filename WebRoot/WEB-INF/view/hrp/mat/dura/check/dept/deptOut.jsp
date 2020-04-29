<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow: hidden;"  xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
    <script src="<%=path%>/lib/stringbuffer.js"></script>
    <script src="<%=path%>/lib/hrp/mat/mat.js" type="text/javascript"></script>
	<script type="text/javascript">
	var grid;
	var gridManager = null;
	var state = "${matDuraDeptMain.state}";
	var paraMoney = '${p04005}';
	var paraPrice = '${p04006}';
	
	$(function (){
		$("#layout1").ligerLayout({ topHeight: 90, centerWidth: 888 });
		loadDict();
		loadHead(null);	
		loadHotkeys();
		queryDetail();
	});  
     
	function loadDict(){
		//字典下拉框
    	$("#dept_code").ligerComboBox({width: 160, disabled: true, cancelable: false}); 
    	if('${matDuraDeptMain.dept_id}'){
			liger.get("dept_code").setValue('${matDuraDeptMain.dept_id},${matDuraDpetCheck.dept_no}');
			liger.get("dept_code").setText('${matDuraDeptMain.dept_code} ${matDuraDeptMain.dept_name}');
    	}
    	
    	$("#duar_no").ligerTextBox({width: 160, disabled: true }); 
		$("#make_date").ligerTextBox({width: 160});

		$("#print").ligerButton({click: printDate, width: 90});
		$("#printSet").ligerButton({click: printSet, width: 100});
		$("#close").ligerButton({click: this_close, width: 90});
	} 

 	function queryDetail(){
 		grid.options.parms=[];
 		grid.options.newPage=1;
         //根据表字段进行添加查询条件
 		grid.options.parms.push({
 			name: 'dura_id', 
 			value: '${matDuraDeptMain.dura_id}'
 		});
 		grid.options.parms.push({
 			name: 'dept_id', 
 			value: '${matDuraDeptMain.dept_id}'
 		});

     	//加载查询条件
     	grid.loadData(grid.where);
 	}

    function loadHead(){
		grid = $("#maingrid").ligerGrid({
			columns: [ { 
				display: '交易编码', name: 'bid_code', align: 'left', width: 100
			}, { 
				display: '材料编码', name: 'inv_code', align: 'left', width: 110, 
				totalSummary: {
					align: 'right', 
					render: function (suminf, column, cell) {
						return '<div>合计：</div>';
					}
				}
			}, { 
				display: '材料名称', name: 'inv_id', textField: 'inv_name', align: 'left', width: 240 
			}, { 
				display: '规格型号', name: 'inv_model', align: 'left', width: 180
			}, { 
				display: '计量单位', name: 'unit_name', align: 'left', width: 60
			}, { 
				display: '单价', name: 'price', align: 'right', width: 90, 
				render: function(rowdata, rowindex, value) {
					return value == null ? "" : formatNumber(value, paraPrice, 0);
				}
			}, { 
				display: '出库数量', name: 'amount', align: 'right', width: 80, 
				totalSummary: {
					align: 'right', 
					render: function (suminf, column, cell) {
						return '<div>' + formatNumber(suminf.sum ==null ? 0: suminf.sum, 2, 1)+ '</div>';
					}
				}
			}, { 
				display: '金额', name: 'amount_money', align: 'right', width: 80, 
				render : function(rowdata, rowindex, value) {
	           		rowdata.amount_money = value == null ? "" : formatNumber(value, paraMoney, 0);
	 				return value == null ? "" : formatNumber(value, paraMoney, 1);
	 			},
				totalSummary: {
					align: 'right', 
					render: function (suminf, column, cell) {
						return '<div>' + formatNumber(suminf.sum ==null ? 0: suminf.sum, 2, 1)+ '</div>';
					}
				}
			}, { 
				display: '生产厂商', name: 'fac_name', align: 'left', width: 180
			}, { 
				display: '备注', name: 'note', align: 'left', width: 240
			} ], 
			usePager: false, width: '100%', height: '100%', enabledEdit: false, fixedCellHeight: true, heightDiff: -20, 
			dataAction: 'server', dataType: 'server', url: 'queryMatDuraDeptDetailByCode.do?isCheck=false', delayLoad: true, //初始化明细数据
			selectRowButtonOnly: true, checkbox: true, rownumbers: true, isScroll: true, 
			toolbar: { items: [ 
			                    /* { text: '审核（<u>O</u>）', id: 'audit', click: audit, icon: 'audit', disabled: state == 1 ? false : true }, 
			                    { line: true }, 
			                    { text: '消审（<u>U</u>）', id: 'unAudit', click: unAudit, icon: 'unaudit', disabled: state == 2 ? false : true }, 
			                    { line: true }, */
			                    { text: '出库确认（<u>I</u>）', id: 'confirm', click: confirm, icon: 'account', disabled: state == 1 ? false : true },
								{ line:true }
				]}
		});
    	gridManager = $("#maingrid").ligerGetGridManager();
	}
    
    //键盘事件
	function loadHotkeys() {
		/* hotkeys('O', audit);
		hotkeys('U', unAudit); */
		hotkeys('I', confirm);
	}
    
	/* //审核
    function audit(){
    	if(state > 1){
			$.ligerDialog.error("审核失败！单据不是新建状态");
			return false;
		}
    	var ParamVo = [];
    	ParamVo.push($("#group_id").val() + "@" + $("#hos_id").val() + "@" + $("#copy_code").val() + "@" +$("#dura_id").val());

		$.ligerDialog.confirm('确定审核?', function(yes) {
			if (yes) {
				ajaxJsonObjectByUrl("auditMatDuraDeptMain.do", {ParamVo: ParamVo.toString()}, function(responseData) {
					if (responseData.state == "true") {
						state = 2;
						change_button();
				    	loadHead();
				    	grid.reRender();
						parentFrameUse().query();
					}
				});
			}
		});
	}
    
	//消审
    function unAudit(){
		if(state != 2){
			$.ligerDialog.error("消审失败！单据不是审核状态");
			return false;
		}
		
    	var ParamVo = [];
		
    	ParamVo.push($("#group_id").val() + "@" + $("#hos_id").val() + "@" + $("#copy_code").val() + "@" +$("#dura_id").val());
		
		$.ligerDialog.confirm('确定消审?', function(yes) {
			if (yes) {
				ajaxJsonObjectByUrl("unAuditMatDuraDeptMain.do", {ParamVo: ParamVo.toString()}, function(responseData) {
					if (responseData.state == "true") {
						state = 1;
						change_button();
				    	loadHead();
				    	grid.reRender();
						parentFrameUse().query();
					}
				});
			}
		});
	} */
	//确认
    function confirm(){ 
		if(state != 1){
			$.ligerDialog.error("入库确认失败！单据不是审核状态");
			return false;
		}
    	var ParamVo = [];
		
    	ParamVo.push($("#group_id").val() + "@" + $("#hos_id").val() + "@" + $("#copy_code").val() + "@" +$("#dura_id").val());
		
		$.ligerDialog.confirm('确定入库确认吗?', function(yes) {
			if (yes) {
				ajaxJsonObjectByUrl("confirmMatDuraDeptMain.do", {ParamVo: ParamVo.toString()}, function(responseData) {
					if (responseData.state == "true") {
						state = 3;
						change_button();
				    	loadHead();
				    	grid.reRender();
		            	parentFrameUse().query();
					}
				});
			}
		});
	}
	
	//关闭当前页面
	function this_close(){
		frameElement.dialog.close();
	}
	
	//打印
	function printDate(){
		var useId=0;//统一打印
		if('${p04020}'==1){
			//按用户打印
			useId='${sessionScope.user_id }';
		}else if('${p04020}'==2){
			//按科室打印
			 if(liger.get("dept_code").getValue()==""){
				$.ligerDialog.error('当前打印模式是按科室打印，请选择科室！');
				return;
			}
			useId=liger.get("dept_code").getValue().split(",")[0];
		}
		var para={
				dura_id: $("#dura_id").val(), 
				template_code: '04015', 
				use_id: useId, 
				p_num: 0
		};
		printTemplate("hrp/mat/dura/check/dept/queryDataByPrintTemlate.do", para);
	}
	
		//打印设置
		function printSet(){
			
			var useId=0;//统一打印
			if('${p04020}'==1){
				//按用户打印
				useId='${sessionScope.user_id }';
			}else if('${p04020}'==2){
				//按科室打印
				if(liger.get("dept_code").getValue()==""){
					$.ligerDialog.error('当前打印模式是按科室打印，请选择科室！');
					return;
				} 
				
				useId=liger.get("dept_code").getValue().split(",")[0];
			}
			parent.parent.$.ligerDialog.open({url: 'hrp/mat/dura/check/dept/printSetPage.do?template_code=041309&use_id='+useId, 
				data: {}, height: $(parent).height(), width: $(parent).width(), title: '打印模板设置', modal: true, showToggle: false, showMax: false, showMin: false, isResize: true, 
			});
		}
	
    </script>
  	</head>
	<body >
	<div id="pageloading" class="l-loading" style="display: none"></div>
		<input name="hos_id"  type="hidden" id="hos_id" value="${matDuraDeptMain.hos_id}" />
		<input name="group_id"  type="hidden" id="group_id" value="${matDuraDeptMain.group_id}" />
		<input name="copy_code"  type="hidden" id="copy_code" value="${matDuraDeptMain.copy_code}" />
		<input name="dura_id"  type="hidden" id="dura_id" value="${matDuraDeptMain.dura_id}" />
		<div id="layout1">
			<div position="top">
				<form name="form1" method="post"  id="form1" >
					<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
						<tr>
							<td align="right" class="l-table-edit-td" >
								<span style="color: red">*</span>单据号：
							</td>
							<td align="left" class="l-table-edit-td">
								<input name="duar_no" type="text" id="duar_no" disabled="disabled" ltype="text" value="${matDuraDeptMain.dura_no}"/>
							</td>
				            
							<td align="right" class="l-table-edit-td" >
								<span style="color: red">*</span>编制日期：
							</td>
							<td align="left" class="l-table-edit-td">
								<input name="make_date" type="text" id="make_date" ltype="text"  validate="{required: true}"  value="${matDuraDeptMain.make_date}" class="Wdate" onFocus="WdatePicker({isShowClear: true, readOnly: false, dateFmt: 'yyyy-MM-dd'})"/>
							</td>
	
							<td align="right" class="l-table-edit-td" >
								<span style="color: red">*</span>科室：
							</td>
							<td align="left" class="l-table-edit-td">
								<input name="dept_code" type="text" id="dept_code" ltype="text" validate="{required: true}" />
							</td>
				        </tr>  
						<tr>
							<td align="right" class="l-table-edit-td" >
								摘      要：
							</td>
							<td align="left" class="l-table-edit-td" colspan="3">
								<textarea class="l-textarea" name="brief" id="brief" rows="3" style="width: 380px;">${matDuraDeptMain.brief}</textarea>
							</td>
						</tr>
					</table>
				</form>
			</div>
			<div position="center">
				<div id="maingrid"></div>
				<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%"  style="margin-top: 5px;">
					<tr>	
						<td align="center" class="l-table-edit-td">
							<button id ="print" accessKey="P"><b>打印（<u>P</u>）</b></button>
							&nbsp;&nbsp; 
							<button id ="printSet" accessKey="M"><b>打印模板（<u>M</u>）</b></button>
							&nbsp;&nbsp;
							<button id ="close" accessKey="C"><b>关闭（<u>C</u>）</b></button>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</body>
</html>
