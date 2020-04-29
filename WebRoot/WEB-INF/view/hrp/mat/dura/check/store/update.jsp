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
	<link href='<%=path%>/lib/SpreadJS/css/gcspread.sheets.9.40.20161.0.css' rel='stylesheet' type='text/css'/>
	<script src='<%=path%>/lib/SpreadJS/scripts/gcspread.sheets.all.9.40.20161.0.min.js' type='text/javascript'></script>
	<script src='<%=path%>/lib/SpreadJS/scripts/pluggable/gcspread.sheets.print.9.40.20161.0.min.js' type='text/javascript'></script>
	<script type="text/javascript">
	var grid;
	var gridManager = null;
	var state = "${matDuraStoreCheck.state}";
	var paraMoney = '${p04005}';
	var paraPrice = '${p04006}';
	$(function (){
		$("#layout1").ligerLayout({ topHeight: 90, centerWidth: 888 });
		loadDict();
		//loadForm();
		loadHead(null);	
		loadHotkeys();
		queryDetail();
		change_button();

        //库房监听事件：动态改变材料下拉列表
		$("#store_code").bind("change", function(){
			grid.columns[4].editor.grid.url = '../../../queryMatDuraStoreInvBarList.do?isCheck=false&store_id=' 
					+ liger.get("store_code").getValue().split(",")[0];
		})
	});  
     
	function  save(){
		//结束编辑
		grid.endEdit();
		
		if(!liger.get("make_date").getValue()){
			$.ligerDialog.warn('编制日期不能为空！');
			return false;
		}
		if(!liger.get("store_code").getValue()){
			$.ligerDialog.warn('库房不能为空！');
			return false;
		}
    	if($("#brief").val() && $("#brief").val().length > 100){
    		$.ligerDialog.warn('摘要长度不能大于100！');
			return false;
    	}
    	 
		var dura_detail_data = gridManager.rows;
		var targetMap = new HashMap();
		var msg = new StringBuffer();
		var rows = 0;
		if(dura_detail_data.length > 0){
  			$.each(dura_detail_data, function(i, v){ 
	  	      	if(v.inv_id){
	  	      		var value="第"+(i+1)+"行";
	  	      		if(!v.amount && v < 0){
						msg.append(value+"数量为零或空 请输入\n");
					}
	  	      		
	  	      		var key=v.inv_id + "|" + v.price +"|" + v.bar_code;
	  				if(!targetMap.get(key)){
	  					targetMap.put(key , value);
	  				}else{
	  					msg.append(targetMap.get(key)+"与"+value+"材料编码、单价、条形码不能重复" + "\n\r");
	  				}
	  				rows += 1;
	  	      	}
 			}); 
  		}
		
		if(msg.toString()  != ""){
 			$.ligerDialog.warn(msg.toString(), '', '', {width: 450});
 			return false;
 		}

 		if(rows == 0){
 			$.ligerDialog.warn('请选择材料');
 			return false;
 		}
 		
        var formPara={
        		hos_id: $("#hos_id").val(), 
        		group_id: $("#group_id").val(), 
        		copy_code: $("#copy_code").val(), 
        		check_id: $("#check_id").val(), 
        		check_no: $("#check_no").val(), 
 				make_date: $("#make_date").val(), 
 				store_code: liger.get("store_code").getValue().split(",")[0], 
 				store_no: liger.get("store_code").getValue().split(",")[1], 
 				brief: $("#brief").val(), 
 				detailData: JSON.stringify(dura_detail_data)
         };
        ajaxJsonObjectByUrl("updateMatDuraCheckStore.do", formPara, function(responseData){
            if(responseData.state=="true"){
            	queryDetail();
            	parentFrameUse().query();
            }
        });
    }
     
	function loadForm(){
		$.metadata.setType("attr", "validate");
		var v = $("form").validate({
			errorPlacement: function (lable, element){
				if (element.hasClass("l-textarea")){
					element.ligerTip({ content: lable.html(), target: element[0] }); 
				}else if (element.hasClass("l-text-field")){
					element.parent().ligerTip({ content: lable.html(), target: element[0] });
				}else{
					lable.appendTo(element.parents("td: first").next("td"));
				}
			}, 
			success: function (lable){
				lable.ligerHideTip();
				lable.remove();
			}, 
			submitHandler: function (){
				$("form .l-text, .l-textarea").ligerHideTip();
			}
		});
		//$("form").ligerForm();
	}    
   
	function loadDict(){
        //字典下拉框
        
		//字典下拉框
		//autocompleteAsync("#store_code", "../../../queryMatStore.do?isCheck=false", "id", "text", true, true);
    	$("#store_code").ligerComboBox({width: 160, disabled: true, cancelable: false}); 
    	
    	if('${matDuraStoreCheck.store_id}'){
			liger.get("store_code").setValue('${matDuraStoreCheck.store_id},${matDuraStoreCheck.store_no}');
			liger.get("store_code").setText('${matDuraStoreCheck.store_code} ${matDuraStoreCheck.store_name}');
    	}
    	
    	$("#check_no").ligerTextBox({width: 160, disabled: true }); 
        
    	//$("#brief").ligerTextBox({width: 240}); 
    	
		$("#make_date").ligerTextBox({width: 160});

		$("#save").ligerButton({click: save, width: 90});
		$("#print").ligerButton({click: printDate, width: 90});
		$("#printSet").ligerButton({click: printSet, width: 100});
		$("#close").ligerButton({click: this_close, width: 90});
	} 

 	function queryDetail(){
 		grid.options.parms=[];
 		grid.options.newPage=1;
         //根据表字段进行添加查询条件
 		grid.options.parms.push({
 			name: 'check_id', 
 			value: '${matDuraStoreCheck.check_id}'
 		});
 		grid.options.parms.push({
 			name: 'store_id', 
 			value: '${matDuraStoreCheck.store_code}'.split(",")[0]
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
				display: '材料名称(E)', name: 'inv_id', textField: 'inv_name', align: 'left', width: 240, 
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
				display: '条形码(E)', name: 'bar_code', align: 'left', width: 120
			}, { 
				display: '账面数量', name: 'cur_amount', align: 'left', width: 80, 
				totalSummary: {
					align: 'left', 
					render: function (suminf, column, cell) {
						return '<div>' + formatNumber(suminf.sum ==null ? 0: suminf.sum, 2, 1)+ '</div>';
					}
				}
			}, { 
				display: '盘点数量(E)', name: 'chk_amount', align: 'left', width: 80, editor: {type: 'float'}, 
				totalSummary: {
					align: 'left', 
					render: function (suminf, column, cell) {
						return '<div>' + formatNumber(suminf.sum ==null ? 0: suminf.sum, 2, 1)+ '</div>';
					}
				}
			}, { 
				display: '盈亏数量', name: 'amount', align: 'left', width: 80,  
				totalSummary: {
					align: 'left', 
					render: function (suminf, column, cell) {
						return '<div>' + formatNumber(suminf.sum ==null ? 0: suminf.sum, 2, 1)+ '</div>';
					}
				}
			}, { 
				display: '盈亏金额', name: 'amount_money', align: 'right', width: 90, 
				render: function(rowdata, rowindex, value) {
					rowdata.amount_money = value == null ? "" : formatNumber(value, paraMoney, 0);
					return value == null ? "" : formatNumber(value, paraMoney, 1);
				}, 
				totalSummary: {
					align: 'right', 
					render: function (suminf, column, cell) {
						return '<div>' + formatNumber(suminf.sum ==null ? 0: suminf.sum, paraMoney, 1)+ '</div>';
					}
				}
			}, { 
				display: '生产厂商', name: 'fac_name', align: 'left', width: 180
			}, { 
				display: '备注(E)', name: 'note', align: 'left', width: 240, editor: {type: 'text'}
			} ], 
			usePager: false, width: '100%', height: '100%', enabledEdit: state == 1 ? true : false, fixedCellHeight: true, heightDiff: -20, 
			dataAction: 'server', dataType: 'server', url: 'queryMatDuraCheckStoreDetailByCode.do?isCheck=false', delayLoad: true, //初始化明细数据
			onBeforeEdit: f_onBeforeEdit, onAfterEdit: f_onAfterEdit, isAddRow:false,
			selectRowButtonOnly: true, checkbox: true, rownumbers: true, isScroll: true,
			toolbar: { items: [ { 
				text: '选择材料（<u>X</u>）', id: 'add', click: choice_inv, icon: 'add',  disabled: state == 1 ? false : true   
			}, { 
				line: true 
			}, { 
				text: '删除（<u>D</u>）', id: 'delete', click: deleteRow, icon: 'delete', disabled: state == 1 ? false : true 
			}, { 
				line: true 
			}, { 
				text: '审核（<u>O</u>）', id: 'audit', click: audit, icon: 'audit', disabled: state == 1 ? false : true 
			}, { 
				line: true 
			}, { 
				text: '消审（<u>U</u>）', id: 'unAudit', click: unAudit, icon: 'unaudit', disabled: state == 2 ? false : true 
			}, { 
				line: true 
			}, { 
				text: '确认（<u>I</u>）', id: 'confirm', click: confirm, icon: 'account', disabled: state == 2 ? false : true 
			}] }, 
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
 	 //选择材料
    function choice_inv(){
    	if(liger.get("store_code").getValue() == null){
			$.ligerDialog.warn('请选择库房');
			return ;
		}
		
		var store_id = liger.get("store_code").getValue();
		var store_text = liger.get("store_code").getText();
		
		parent.$.ligerDialog.open({
			url: "hrp/mat/dura/check/store/choiceInvPage.do?isCheck=false&store_id="+store_id+"&store_text="+store_text, 
			height : $(window).height(),
			width : $(window).width(),
			title: '选择材料', 
			modal: true, 
			showToggle: false, 
			showMax: true, 
			showMin: true, 
			isResize: true, 
			parentframename : window.name //用于parent弹出层调用本页面的方法或变量
		});
    }
    
    //键盘事件
	function loadHotkeys() {
		hotkeys('D', deleteRow);
		hotkeys('O', audit);
		hotkeys('U', unAudit);
		hotkeys('I', confirm);
	}
    
    var rowindex_id = "";
	var column_name = "";
	
	function f_onBeforeEdit(e) {
		rowindex_id = e.rowindex;
		column_name = e.column.name;		
	}
	
	//选中回充数据
	function f_onSelectRow_detail(data, rowindex, rowobj) {
		selectData = "";
		selectData = data;
		//alert(JSON.stringify(data)); 
		//回充数据 
		if (selectData != "" || selectData != null) {
			if (column_name == "inv_id") {
				grid.updateRow(rowindex_id, {
					bid_code: data.bid_code, 
					inv_no: data.inv_no, 
					inv_code: data.inv_code, 
					inv_name: data.inv_name, 
					inv_model: data.inv_model == null ? "" : data.inv_model, 
					unit_name: data.unit_name == null ? "" : data.unit_name, 
					bar_code: data.bar_code == null ? "" : data.bar_code, 
					cur_amount: data.cur_amount == null ? "" : data.cur_amount, 
					imme_amount: data.imme_amount == null ? "" : data.imme_amount, 
					price: data.price == null ? "" : data.price, 
					fac_name: data.fac_name, 
				});
			}
		}
		return true;
	}
	
    function f_onAfterEdit(e){
	    if("chk_amount" == e.column.name){
    		gridManager.updateCell('amount', (e.record.chk_amount -e.record.cur_amount), e.record); 
    		gridManager.updateCell('amount_money', (e.record.chk_amount -e.record.cur_amount)*e.record.price, e.record); 
    	}
    	if("amount" == column_name){
    		grid.updateCell('amount_money', e.record.amount*e.record.price, e.record); 
    	}
    	
    	//更新合计行
		grid.updateTotalSummary();
    }
    
	//移除行
	function deleteRow(){ 
		
		gridManager.deleteSelectedRow();
    }
	
	//审核
    function audit(){
    	if(state > 1){
			$.ligerDialog.error("审核失败！单据不是新建状态");
			return false;
		}
    	var ParamVo = [];
    	ParamVo.push($("#group_id").val() + "@" + $("#hos_id").val() + "@" + $("#copy_code").val() + "@" +$("#check_id").val());

		$.ligerDialog.confirm('确定审核?', function(yes) {
			if (yes) {
				ajaxJsonObjectByUrl("auditMatDuraCheckStore.do", {ParamVo: ParamVo.toString()}, function(responseData) {
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
		
    	ParamVo.push($("#group_id").val() + "@" + $("#hos_id").val() + "@" + $("#copy_code").val() + "@" +$("#check_id").val());
		
		$.ligerDialog.confirm('确定消审?', function(yes) {
			if (yes) {
				ajaxJsonObjectByUrl("unAuditMatDuraCheckStore.do", {ParamVo: ParamVo.toString()}, function(responseData) {
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
	}
    
	//确认
    function confirm(){
		if(state != 2){
			$.ligerDialog.error("确认失败！单据不是审核状态");
			return false;
		}
    	var ParamVo = [];
		
    	ParamVo.push($("#group_id").val() + "@" + $("#hos_id").val() + "@" + $("#copy_code").val() + "@" +$("#check_id").val());
		
		$.ligerDialog.confirm('确定确认?', function(yes) {
			if (yes) {
				ajaxJsonObjectByUrl("confirmMatDuraCheckStore.do", {ParamVo: ParamVo.toString()}, function(responseData) {
					if (responseData.state == "true") {
						state = 3;
						change_button();
				    	//loadHead();
				    	//grid.reRender();
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
				check_id : $("#check_id").val(), 
				template_code:'041313',
				class_name:"com.chd.hrp.mat.serviceImpl.dura.check.MatDuraCheckStoreServiceImpl",
				method_name:"queryDataByPrintTemlate",
				isPreview:true,//预览窗口，传绝对路径
				use_id:useId,
				p_num:0
			};
		officeFormPrint(para);
	}
	
	//打印设置
	function printSet(){
		
		var useId=0;//统一打印
		
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
		officeFormTemplate({template_code:"041313",use_id:useId});
	}
	
	function change_button(){
		if(state == 1){
			$("#save").ligerButton({click: save, width: 90, disabled: false});
		}else{
			$("#save").ligerButton({click: save, width: 90, disabled: true});
		}
		
		if(state == 3){
      		$("#print").ligerButton({click: printDate, width: 90, disabled: false});
      	}else{
      		$("#print").ligerButton({click: printDate, width: 90, disabled: true});
      	}
	}
	
	function addParts(addData){
		
		$.each(addData, function(i, v) { 
			
			var rowData={
				amount: v.amount,
				amount_money: v.amount_money,
				bar_code: v.bar_code,
				bid_code: v.bid_code,
				chk_amount: v.chk_amount,
				copy_code: v.copy_code,
				cur_amount: v.cur_amount,
				fac_code: v.fac_code,
				fac_id: v.fac_id,
				fac_name: v.fac_name,
				group_id: v.group_id,
				hos_id: v.hos_id,
				inv_code: v.inv_code,
				inv_id: v.inv_id,
				inv_no: v.inv_no,
				inv_model: v.inv_model,
				inv_name: v.inv_name,
				price: v.price,
				unit_code: v.unit_code,
				unit_name: v.unit_name,
			};
			
			grid.addRow(rowData);
	  	});
	}
    </script>
  	</head>
  
	<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<input name="hos_id"  type="hidden" id="hos_id" value="${matDuraStoreCheck.hos_id}" />
	<input name="group_id"  type="hidden" id="group_id" value="${matDuraStoreCheck.group_id}" />
	<input name="copy_code"  type="hidden" id="copy_code" value="${matDuraStoreCheck.copy_code}" />
	<input name="check_id"  type="hidden" id="check_id" value="${matDuraStoreCheck.check_id}" />
	<div id="layout1">
		<div position="top">
			<form name="form1" method="post"  id="form1" >
				<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
					<tr>
						<td align="right" class="l-table-edit-td" >
							<span style="color: red">*</span>单据号：
						</td>
						<td align="left" class="l-table-edit-td">
							<input name="check_no" type="text" id="check_no" disabled="disabled" ltype="text" value="${matDuraStoreCheck.check_no}"/>
						</td>
			            
						<td align="right" class="l-table-edit-td" >
							<span style="color: red">*</span>编制日期：
						</td>
						<td align="left" class="l-table-edit-td">
							<input name="make_date" type="text" id="make_date" ltype="text"  validate="{required: true}"  value="${matDuraStoreCheck.make_date}" class="Wdate" onFocus="WdatePicker({isShowClear: true, readOnly: false, dateFmt: 'yyyy-MM-dd'})"/>
						</td>

						<td align="right" class="l-table-edit-td" >
							<span style="color: red">*</span>库房：
						</td>
						<td align="left" class="l-table-edit-td">
							<input name="store_code" type="text" id="store_code" ltype="text" validate="{required: true}" />
						</td>
			        </tr>  
					<tr>
						<td align="right" class="l-table-edit-td" >
							摘      要：
						</td>
						<td align="left" class="l-table-edit-td" colspan="3">
							<textarea class="l-textarea" name="brief" id="brief" rows="3" style="width: 380px;">${matDuraStoreCheck.brief}</textarea>
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
						<button id ="save" accessKey="S"><b>保存（<u>S</u>）</b></button>
						&nbsp;&nbsp; 
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
