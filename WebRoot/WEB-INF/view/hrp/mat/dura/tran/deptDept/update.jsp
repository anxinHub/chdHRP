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
	var state = "${matDuraDeptDept.state}";
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

        //科室监听事件：动态改变材料下拉列表
		$("#dept_code").bind("change", function(){
			grid.columns[4].editor.grid.url = '../../../queryMatDuraDeptInvBarList.do?isCheck=false&dept_id=' 
					+ liger.get("dept_code").getValue().split(",")[0];
		})
	});  
     
	function  save(){
		//结束编辑
		grid.endEdit();
		
		if(!liger.get("make_date").getValue()){
			$.ligerDialog.warn('编制日期不能为空！');
			return false;
		}
		if(!liger.get("dept_code").getValue()){
			$.ligerDialog.warn('移出科室不能为空！');
			return false;
		}
		if(!liger.get("in_dept_code").getValue()){
			$.ligerDialog.warn('移入科室不能为空！');
			return false;
		}
		if(liger.get("dept_code").getValue() == liger.get("in_dept_code").getValue()){
			$.ligerDialog.warn('院内移拨移出、移入科室不能一致！');
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
	  	      		
	  	      		var key=v.inv_id + "|" + v.price /* +"|" + v.bar_code */;
	  				if(!targetMap.get(key)){
	  					targetMap.put(key , value);
	  				}else{
	  					/* msg.append(targetMap.get(key)+"与"+value+"材料编码、单价不能重复" + "\n\r"); */
	  					msg.append(targetMap.get(key)+"与"+value+"材料编码、单价、条形码不能重复" + "\n\r");
	  				}
	  				if(v.amount > v.imme_amount){
	  					msg.append(value+"[数量]不能大于[即时库存];\n");
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
        		dura_id: $("#dura_id").val(), 
        		dura_no: $("#dura_no").val(), 
 				make_date: $("#make_date").val(), 
 				dept_code: liger.get("dept_code").getValue().split(",")[0], 
 				dept_no: liger.get("dept_code").getValue().split(",")[1], 
 				in_dept_id: liger.get("in_dept_code").getValue().split(",")[0], 
 				in_dept_no: liger.get("in_dept_code").getValue().split(",")[1], 
 				brief: $("#brief").val(), 
 				detailData: JSON.stringify(dura_detail_data)
         };
        ajaxJsonObjectByUrl("updateMatDuraTranDeptDept.do", formPara, function(responseData){
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
		//autocompleteAsync("#dept_code", "../../../queryMatdept.do?isCheck=false", "id", "text", true, true);
    	$("#dept_code").ligerComboBox({width: 160, disabled: true, cancelable: false}); 
    	if('${matDuraDeptDept.dept_id}'){
			liger.get("dept_code").setValue('${matDuraDeptDept.dept_id},${matDuraDeptDept.dept_no}');
			liger.get("dept_code").setText('${matDuraDeptDept.dept_code} ${matDuraDeptDept.dept_name}');
    	}
    	
		autocompleteAsync("#in_dept_code", "../../../queryHosDeptDictByPerm.do?isCheck=false", "id", "text", true, true, {is_last : 1});
		if("${matDuraDeptDept.in_dept_id}"){
			liger.get("in_dept_code").setValue('${matDuraDeptDept.in_dept_id},${matDuraDeptDept.in_dept_no}');
			liger.get("in_dept_code").setText('${matDuraDeptDept.in_dept_code} ${matDuraDeptDept.in_dept_name}');
		}
		
    	$("#dura_no").ligerTextBox({width: 160, disabled: true }); 
        
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
 			name: 'dura_id', 
 			value: '${matDuraDeptDept.dura_id}'
 		});
 		grid.options.parms.push({
 			name: 'dept_id', 
 			value: '${matDuraDeptDept.dept_code}'.split(",")[0]
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
				editor: {
					type: 'select', 
					valueField: 'inv_id', 
					textField: 'inv_name', 
					selectBoxWidth: "80%", 
					selectBoxHeight: 240, 
					keySupport: true, 
					autocomplete: true, 
					highLight: true, 
					grid: {
						columns: [ {
							display: '交易编码', name: 'bid_code', width: 100, align: 'left'
						}, {
							display: '材料编码', name: 'inv_code', width: 110, align: 'left'
						}, {
							display: '材料名称', name: 'inv_name', width: 240, align: 'left'
						}, {
							display: '规格型号', name: 'inv_model', width: 100, align: 'left'
						}, {
							display: '计量单位', name: 'unit_name', width: 60, align: 'left'
						}, {
							display: '单价', name: 'price', width: 80, align: 'right'
						}, {
							display: '条码', name: 'bar_code', width: 120, align: 'left' 
						}, {
							display: '库存', name: 'cur_amount', width: 80, align: 'left'
						}, {
							display: '即时库存', name: 'imme_amount', width: 80, align: 'left'
						}, {
							display: '物资类别', name: 'mat_type_name', width: 120, align: 'left'
						}, {
							display: '生产厂商', name: 'fac_name', width: 180, align: 'left'
						} ], 
						switchPageSizeApplyComboBox: false, 
						onSelectRow: function (data) {
							var e = window.event;
							if (e && e.which == 1) {
								f_onSelectRow_detail(data);
							}
						},  
						url: '../../../queryMatDuraDeptInvBarList.do?isCheck=false&dept_id=' 
								+ liger.get("dept_code").getValue().split(",")[0], 
						pageSize: 100, //每页条数
						onSuccess: function (data, g) { //加载完成时默认选中
							if (grid.editor.editParm) {
								var editor = grid.editor.editParm.record;
								var item = data.Rows.map(function (v, i) {
									return v.inv_name;
								});
								var index = item.indexOf(editor.inv_name) == -1 ? 0 : item.indexOf(editor.inv_name);
								//加载完执行
								setTimeout(function () {
									g.select(data.Rows[index]);
								}, 80);
							}
						}
					}, 
					onSuccess: function() {
						this.parent("tr").next(".l-grid-row").find("td: first").focus();
					}, 
					ontextBoxKeyEnter: function (data) {
						f_onSelectRow_detail(data.rowdata);
					}
				}
			}, { 
				display: '规格型号', name: 'inv_model', align: 'left', width: 180
			}, { 
				display: '计量单位', name: 'unit_name', align: 'left', width: 60
			}, { 
				display: '即时库存', name: 'imme_amount', align: 'left', width: 80
			}, { 
				display: '数量(E)', name: 'amount', align: 'right', width: 80, editor: {type: 'float'}, 
				totalSummary: {
					align: 'left', 
					render: function (suminf, column, cell) {
						return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, 2, 1)+ '</div>';
					}
				}
			}, { 
				display: '单价', name: 'price', align: 'right', width: 80, 
				render: function(rowdata, rowindex, value) {
					return value == null ? "" : formatNumber(value, paraPrice, 0);
				}
			}, { 
				display: '金额', name: 'amount_money', align: 'right', width: 80, 
				render: function(rowdata, rowindex, value) {
					rowdata.amount_money = value == null ? "" : formatNumber(value, paraMoney, 0);
					return value == null ? "" : formatNumber(value, paraMoney, 1);
				}, 
				totalSummary: {
					align: 'right', 
					render: function (suminf, column, cell) {
						return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, paraMoney, 1)+ '</div>';
					}
				}
			}, { 
				display: '条形码', name: 'bar_code', align: 'left', width: 120 
			}, { 
				display: '生产厂商', name: 'fac_name', align: 'left', width: 180
			}, { 
				display: '备注(E)', name: 'note', align: 'left', width: 180, editor: {type: 'text'} 
			} ], 
			usePager: false, width: '100%', height: '100%', enabledEdit: state == 1 ? true : false, fixedCellHeight: true, heightDiff: -20, 
			dataAction: 'server', dataType: 'server', url: 'queryMatDuraTranDeptDeptDetailByCode.do?isCheck=false&dept_id=${matDuraDeptDept.dept_id}', delayLoad: true, //初始化明细数据
			onBeforeEdit: f_onBeforeEdit, onBeforeSubmitEdit: f_onBeforeSubmitEdit, onAfterEdit: f_onAfterEdit, 
			selectRowButtonOnly: true, checkbox: true, rownumbers: true, isScroll: true, 
			onsuccess: function(){
				//is_addRow();
			}, 
			toolbar: { items: [ { 
				text: '删除（<u>D</u>）', id: 'delete', click: deleteRow, icon: 'delete', disabled: state == 1 ? false : true 
			}, { 
				line: true 
			}, { 
				text: '移出确认（<u>O</u>）', id: 'audit', click: audit, icon: 'audit', disabled: state == 1 ? false : true 
			}, { 
				line: true 
			}, { 
				text: '取消移出确认（<u>U</u>）', id: 'unAudit', click: unAudit, icon: 'unaudit', disabled: state == 2 ? false : true 
			}, { 
				line: true 
			}, { 
				text: '移入确认（<u>I</u>）', id: 'confirm', click: confirm, icon: 'account', disabled: state == 2 ? false : true 
			}] }, 
		});

		gridManager = $("#maingrid").ligerGetGridManager();
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
					/* bar_code: data.bar_code == null ? "" : data.bar_code,  */
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
    	if("amount" == column_name){
    		grid.updateCell('amount_money', e.record.amount*e.record.price, e.record); 
    	}
    	
    	//更新合计行
		grid.updateTotalSummary();
    }
    
	// 编辑单元格提交编辑状态之前作判断限制
	function f_onBeforeSubmitEdit(e) {
		/*
		if (e.column.name == "inv_id" && e.value == ""){
			//$.ligerDialog.warn('请选择材料！');
			//grid.setCellEditing(e.record, e.column, true);
			return false;
		}
		if (e.column.name == "amount" && e.value == 0){
			//$.ligerDialog.warn('数量不能为0！');
			//grid.setCellEditing(e.record, e.column, true);
			return false;
		}
		*/
		return true;
	}
    
	//移除行
	function deleteRow(){ 
		
		gridManager.deleteSelectedRow();
    }
	
	//移出确认
    function audit(){
    	if(state > 1){
			$.ligerDialog.error("移出确认失败！单据不是未移出确认状态");
			return false;
		}
    	var ParamVo = [];
    	ParamVo.push($("#group_id").val() + "@" + $("#hos_id").val() + "@" + $("#copy_code").val() + "@" +$("#dura_id").val());

		$.ligerDialog.confirm('确定移出确认?', function(yes) {
			if (yes) {
				ajaxJsonObjectByUrl("auditMatDuraTranDeptDept.do", {ParamVo: ParamVo.toString()}, function(responseData) {
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
    
	//取消移出确认
    function unAudit(){
		if(state != 2){
			$.ligerDialog.error("取消移出确认失败！单据不是移出确认状态");
			return false;
		}
		
    	var ParamVo = [];
		
    	ParamVo.push($("#group_id").val() + "@" + $("#hos_id").val() + "@" + $("#copy_code").val() + "@" +$("#dura_id").val());
		
		$.ligerDialog.confirm('确定取消移出确认?', function(yes) {
			if (yes) {
				ajaxJsonObjectByUrl("unAuditMatDuraTranDeptDept.do", {ParamVo: ParamVo.toString()}, function(responseData) {
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
    
	//移入确认
    function confirm(){
		if(state != 2){
			$.ligerDialog.error("移入确认失败！单据不是移出确认状态");
			return false;
		}
    	var ParamVo = [];
		
    	ParamVo.push($("#group_id").val() + "@" + $("#hos_id").val() + "@" + $("#copy_code").val() + "@" +$("#dura_id").val());
		
		$.ligerDialog.confirm('确定移入确认?', function(yes) {
			if (yes) {
				ajaxJsonObjectByUrl("confirmMatDuraTranDeptDept.do", {ParamVo: ParamVo.toString()}, function(responseData) {
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

	//当单据未审核 默认新增一行
	function is_addRow(){
		if(state > 1){
			return;
		}
		setTimeout(function () {
				grid.addRow();
			}, 500);
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
				$.ligerDialog.error('当前打印模式是按移出科室打印，请选择科室！');
				return;
			}
			useId=liger.get("dept_code").getValue().split(",")[0];
		}
		var para={
			dura_id: $("#dura_id").val(),  
			template_code:'041311',
			class_name:"com.chd.hrp.mat.serviceImpl.dura.tran.MatDuraTranDeptDeptServiceImpl",
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
				$.ligerDialog.error('当前打印模式是按移出科室打印，请选择科室！');
				return;
			} 
			
			useId=liger.get("dept_code").getValue().split(",")[0];
		}
		officeFormTemplate({template_code:"041311",use_id:useId});
		/* parent.parent.$.ligerDialog.open({url: 'hrp/mat/dura/tran/deptDept/printSetPage.do?template_code=041311&use_id='+useId, 
			data: {}, height: $(parent).height(), width: $(parent).width(), title: '打印模板设置', modal: true, showToggle: false, showMax: false, showMin: false, isResize: true, 
		}); */
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
    </script>

  	</head>
  
	<body onload="is_addRow();">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<input name="hos_id"  type="hidden" id="hos_id" value="${matDuraDeptDept.hos_id}" />
	<input name="group_id"  type="hidden" id="group_id" value="${matDuraDeptDept.group_id}" />
	<input name="copy_code"  type="hidden" id="copy_code" value="${matDuraDeptDept.copy_code}" />
	<input name="dura_id"  type="hidden" id="dura_id" value="${matDuraDeptDept.dura_id}" />
	<div id="layout1">
		<div position="top">
			<form name="form1" method="post"  id="form1" >
				<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
					<tr>
						<td align="right" class="l-table-edit-td" >
							<span style="color: red">*</span>移拨单号：
						</td>
						<td align="left" class="l-table-edit-td">
							<input name="dura_no" type="text" id="dura_no" disabled="disabled" ltype="text" value="${matDuraDeptDept.dura_no}"/>
						</td>
			            
						<td align="right" class="l-table-edit-td" >
							<span style="color: red">*</span>编制日期：
						</td>
						<td align="left" class="l-table-edit-td">
							<input name="make_date" type="text" id="make_date" ltype="text"  validate="{required: true}"  value="${matDuraDeptDept.make_date}" class="Wdate" onFocus="WdatePicker({isShowClear: true, readOnly: false, dateFmt: 'yyyy-MM-dd'})"/>
						</td>

						<td align="right" class="l-table-edit-td" >
							<span style="color: red">*</span>移出科室：
						</td>
						<td align="left" class="l-table-edit-td">
							<input name="dept_code" type="text" id="dept_code" ltype="text" validate="{required: true}" />
						</td>
			        </tr>  
					<tr>
						<td align="right" class="l-table-edit-td" >
							<span style="color: red">*</span>移入科室：
						</td>
						<td align="left" class="l-table-edit-td">
							<input name="in_dept_code" type="text" id="in_dept_code" ltype="text"  validate="{required: true}" />
						</td>

						<td align="right" class="l-table-edit-td" >
							摘      要：
						</td>
						<td align="left" class="l-table-edit-td" colspan="3">
			            	<textarea class="l-textarea" name="brief" id="brief" rows="3" style="width: 380px;">${matDuraDeptDept.brief}</textarea>
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
