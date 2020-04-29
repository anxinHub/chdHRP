<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script src="<%=path%>/lib/map.js"></script>

<script type="text/javascript">
	var grid;
	var gridManager;
	var gridRowData;
	var detailDiv;
	var detailGrid;
	var state = ${matSafeChange.state};
	
	$(function (){ 
		$("#layout").ligerLayout({ 
			topHeight: 95, 
			bottomHeight: 50, 
			//不允许调整大小
			allowTopResize: false, 
			allowBottomResize: false, 
		});
		loadDict();
		loadButton();
		loadHead();
		loadHotkeys();

		$("#store_code").bind("change", function () {
			grid.columns[1].editor.grid.url = 'queryMatInvBySafeChange.do?isCheck=false&store_id=' + liger.get("store_code").getValue().split(",")[0];
			grid.reRender();
		});
		
		queryDetail();
	}); 
	
	function queryDetail(){
		grid.options.parms=[];
		grid.options.newPage=1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({name: 'change_id', value: $("#change_id").val()});
		//加载查询条件
		grid.loadData(grid.where);
	}
	
	function loadHead(){
		grid = $("#maingrid").ligerGrid({
			columns: [ { 
				display: "材料编码", name: "inv_code", width: 140, align: 'left', 
			}, {
				display: '材料名称(E)', name: 'inv_id', textField: 'inv_name', width: 180, align: 'left', 
				editor: {
					type: 'select',
					valueField: 'inv_id',
					textField: 'inv_name',
					selectBoxWidth: '80%',
					selectBoxHeight: 240,
					grid: {
						columns: [{
							display: '材料编码', name: 'inv_code', width: 100, align: 'left'
						}, {
							display: '材料名称', name: 'inv_name', width: 240, align: 'left'
						}, {
							display : '别名', name : 'alias', width : 120, align : 'left', 
						}, {
							display: '物资类别', name: 'mat_type_name', width: 100, align: 'left'
						}, {
							display: '规格型号', name: 'inv_model', width: 180, align: 'left'
						}, {
							display: '计量单位', name: 'unit_name', width: 80, align: 'left'
						}, {
							display: '生产厂商', name: 'fac_name', width: 100, align: 'left'
						} ],
						switchPageSizeApplyComboBox: false,
						onSelectRow: function (data) {
							var e = window.event;
							if (e && e.which == 1) {
								f_onSelectRow_detail(data);
							}
						},
						gid: "invGrid", 
						url: 'queryMatInvBySafeChange.do?isCheck=false&store_id='+ liger.get("store_code").getValue().split(",")[0],
						pageSize: 100,
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
					delayLoad: true, keySupport: true, autocomplete: true,// rownumbers : true,
					onSuccess: function (data, grid) {
						this.parent("tr").next(".l-grid-row").find("td:first").focus();
					},
					ontextBoxKeyEnter: function (data) {
						f_onSelectRow_detail(data.rowdata);
					}
				},
				render: function (rowdata, rowindex, value) {
					return rowdata.inv_name;
				}
			}, {
				display: '规格型号', name: 'inv_model', width: 120, align: 'left'
			}, {
				display: '计量单位', name: 'unit_name', width: 80, align: 'left'
			}, {
				display: '生产厂商', name: 'fac_name', width: 140, align: 'left'
			}, {
				display: '周期单位(E)', name: 'period', textField: 'period_name', width: 80, align: 'left', 
				editor: {
					type: 'select',
					valueField: 'period',
					textField: 'period_name',
					data: [{"period_name": "年", "period": 1}, {"period_name": "季", "period": 2}, {"period_name": "月", "period": 3}, {"period_name": "天", "period": 4}],
					keySupport: true,
					autocomplete: true,
				}, 
			}, { 
				display: '安全周期数(E)', name: 'period_num', align: 'right', width:'100',  
				editor: { type : 'number' },
				render: function (rowdata, rowindex, value) {
					return formatNumber(value ==null ? 0 : value, 2, 1);
				}
			}, {
				display: '最低库存(E)', name: 'low_limit', align: 'right',width:'100',  
				editor: { type : 'number' },
				render: function (rowdata, rowindex, value) {
					return formatNumber(value ==null ? 0 : value, 2, 1);
				}
			}, { 
				display: '安全库存(E)', name: 'secu_limit', align: 'right',width:'100',  
				editor: { type : 'number' },
				render: function (rowdata, rowindex, value) {
					return formatNumber(value ==null ? 0 : value, 2, 1);
				}
			}, { 
				display: '最高库存(E)', name: 'high_limit', align: 'right',width:'100',  
				editor: { type : 'number' },
				render: function (rowdata, rowindex, value) {
					return formatNumber(value ==null ? 0 : value, 2, 1);
				}
			}, 
			/**{ 
				display: '临近预警量(E)', name: 'warn_amount', align: 'right',width:'100',  
				editor: { type : 'number' },
				render: function (rowdata, rowindex, value) {
					return formatNumber(value ==null ? 0 : value, 2, 1);
				}
			}, { 
				display: '临近预警包装量(E)', name: 'pack_amount', align: 'right',width:'100',  
				editor: { type : 'number' },
				render: function (rowdata, rowindex, value) {
					return formatNumber(value ==null ? 0 : value, 2, 1);
				}
			}, */
			{ 
				display: '配送周期(E)', name: 'ps_period', align: 'right',width:'100',  
				editor: { type : 'number' },
				render: function (rowdata, rowindex, value) {
					return formatNumber(value ==null ? 0 : value, 2, 1);
				}
			}, { 
				display: '采购周期(E)', name: 'cg_period', align: 'right',width:'100',  
				editor: { type : 'number' },
				render: function (rowdata, rowindex, value) {
					return formatNumber(value ==null ? 0 : value, 2, 1);
				}
			}, { 
				display: '最小采购批量(E)', name: 'min_pur', align: 'right',width:'100',  
				editor: { type : 'number' },
				render: function (rowdata, rowindex, value) {
					return formatNumber(value ==null ? 0 : value, 2, 1);
				}
			}, { 
				display: '日消耗量周期(E)', name: 'rxhl_period', align: 'right',width:'100',  
				editor: { type : 'number' },
				render: function (rowdata, rowindex, value) {
					return formatNumber(value ==null ? 0 : value, 2, 1);
				}  
			} ],
			usePager: false, width: '100%', height: '100%',
			url: "queryMatSafeChangeDetailById.do?isCheck=false",
			checkbox: true, rownumbers: true, frozen: false,
			enabledEdit: state > 1 ? false : true, isAddRow: true, inWindow: false, 
			enabledSort: false, delayLoad: true,//初始化不加载，默认false
			onBeforeEdit: f_onBeforeEdit, 
			selectRowButtonOnly: true, heightDiff: 30,
			//onSuccess: function(v_data, v_grid){v_grid.addRow()},
			toolbar: { id: "gridToolBar", items: [ { 
				text: '删除（<u>D</u>）', id: 'delete', click: remove, icon: 'delete', disabled: state == 1 ? false : true 
			}, { 
				line:true 
			}, { 
				text: '保存（<u>S</u>）', id: 'save', click: save, icon: 'save', disabled: state == 1 ? false : true 
			}, { 
				line:true 
			}, { 
				text: '审核（<u>Z</u>）', id: 'audit', click: audit, icon: 'bluebook', disabled: state == 1 ? false : true 
			}, { 
				line:true 
			}, { 
				text: '消审（<u>U</u>）', id: 'unaudit', click: unAudit, icon: 'bookpen', disabled: state == 2 ? false : true 
			}, { 
				line:true 
			}, { 
				text: '确认（<u>C</u>）', id: 'confirm', click: confirm, icon: 'account', disabled: state == 2 ? false : true 
			} ] }
		});
		
		gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	var rowindex_id = "";
	var column_name = "";
	function f_onBeforeEdit(e) {
		rowindex_id = e.rowindex;
		column_name = e.column.name;
	}
	
	//选中回充数据
	function f_onSelectRow_detail(data) {
		//回充数据 
		if (column_name == "inv_id") {
			grid.updateRow(rowindex_id, {
				inv_code: data.inv_code,
				inv_model: data.inv_model == null ? "" : data.inv_model,
				unit_name: data.unit_name == null ? "" : data.unit_name,
				fac_name : data.fac_name == null ? "" : data.fac_name,
				period: data.period == null ? "" : data.period,
				period_name: data.period_name == null ? "" : data.period_name,
				period_num: data.period_num == null ? 0 : data.period_num,
				high_limit: data.high_limit == null ? 0 : data.high_limit,
				secu_limit: data.secu_limit == null ? 0 : data.secu_limit,
				low_limit: data.low_limit == null ? 0 : data.low_limit,
				ps_period: data.ps_period == null ? 0 : data.ps_period,
				cg_period: data.cg_period == null ? 0 : data.cg_period,
				min_pur: data.min_pur == null ? 0 : data.min_pur,
				rxhl_period: data.rxhl_period == null ? 0 : data.rxhl_period,
			});
			setTimeout(function (){
				grid.endEditToNext();
			},300)
		}
		//手动移除grid IE下只能移除......   隐藏不生效
		// $('.l-box-select-lookup').remove();
		return true;
	}
	
	//删除
	function remove(){
		//移除表格选中行
    	gridManager.deleteSelectedRow();
	}
	
	//保存
	function  save(){
		var store_code = liger.get("store_code").getValue();
		if(!store_code){
			$.ligerDialog.warn('请选择库房！');
			return false;
		}
		
		var data = grid.getData();
		if (data.length == 0){
			$.ligerDialog.error('请添加材料后再保存');
			return false;
		}
		
		var invs = {};
		var str = "";
		$.each(data, function(i, v){
			if(invs[v.inv_id]){
				str += "第" + (i+1) + "行材料【" + v.inv_code + " " + v.inv_name + "】与第" + invs[v.inv_id] + "行重复<br/>";
			}
			invs[v.inv_id] = i + 1;
		});
		
		if(str.length > 0){
			$.ligerDialog.error(str);
			return false;
		}
		
		var formPara={
			change_id: $("#change_id").val(),
			change_no: $("#change_no").val(),
			store_id: store_code.split(",")[0],
			brief: $("#brief").val(),
			allData: JSON.stringify(data)
		};

		ajaxJsonObjectByUrl("saveMatSafeChange.do?isCheck=false", formPara, function(res){
			if(res.state == "true"){
				queryDetail();
				parentFrameUse().query();
			}
		});
	}
	
	//关闭页面
	function this_close(){
		frameElement.dialog.close();
	}
	
	// 审核
	function audit(){
		if (state != 1){
			$.ligerDialog.error('审核失败！单据不是未审核状态,不能审核');
			return;
		}
		
		$.ligerDialog.confirm('确定审核?', function (yes){
			if(yes){
				ajaxJsonObjectByUrl("auditMatSafeChange.do", {ids : $("#change_id").val()}, function (res){
					if(res.state=="true"){
						state = 2;
						grid.set("enabledEdit", "false");
						grid.reRender();
						liger.get("gridToolBar").setDisabled("delete");
						liger.get("gridToolBar").setDisabled("save");
						//liger.get("save").setEnabled();
						liger.get("gridToolBar").setDisabled("audit");
						liger.get("gridToolBar").setEnabled("unaudit");
						liger.get("gridToolBar").setEnabled("confirm");
						if(parentFrameUse().gridRowIdByOpen){
							parentFrameUse().grid.updateRow(parentFrameUse().gridRowIdByOpen, {state: 2, state_name: '已审核', checker_name: res.checker_name, check_date: res.check_date});
						}else{
							parentFrameUse().query();
						}
					}
				});
			}
		}); 
	}
	
	//消审
	function unAudit(){
		if (state != 2){
			$.ligerDialog.error('消审失败！单据不是已审核状态,不能消审');
			return;
		}
		
		$.ligerDialog.confirm('确定消审?', function (yes){
			if(yes){
				ajaxJsonObjectByUrl("unAuditMatSafeChange.do", {ids : $("#change_id").val()}, function (res){
					if(res.state=="true"){
						state = 1;
						grid.set("enabledEdit", "true");
						grid.reRender();
						liger.get("gridToolBar").setEnabled("delete");
						liger.get("gridToolBar").setEnabled("save");
						//liger.get("save").setEnabled();
						liger.get("gridToolBar").setEnabled("audit");
						liger.get("gridToolBar").setDisabled("unaudit");
						liger.get("gridToolBar").setDisabled("confirm");
						if(parentFrameUse().gridRowIdByOpen){
							parentFrameUse().grid.updateRow(parentFrameUse().gridRowIdByOpen, {state: 1, state_name: '未审核', checker_name: res.checker_name, check_date: res.check_date});
						}else{
							parentFrameUse().query();
						}
					}
				});
			}
		}); 
	}
	
	//确认
	function confirm(){
		if (state != 2){
			$.ligerDialog.error('确认失败！单据不是已审核状态,不能确认');
			return;
		}
		
		$.ligerDialog.confirm('确定确认?', function (yes){
			if(yes){
				ajaxJsonObjectByUrl("confirmMatSafeChange.do", {ids : $("#change_id").val()}, function (res){
					if(res.state=="true"){
						state = 3;
						grid.set("enabledEdit", "false");
						grid.reRender();
						liger.get("gridToolBar").setDisabled("delete");
						liger.get("gridToolBar").setDisabled("save");
						//liger.get("save").setEnabled();
						liger.get("gridToolBar").setDisabled("audit");
						liger.get("gridToolBar").setDisabled("unaudit");
						liger.get("gridToolBar").setDisabled("confirm");
						if(parentFrameUse().gridRowIdByOpen){
							parentFrameUse().grid.updateRow(parentFrameUse().gridRowIdByOpen, {state: 3, state_name: '已确认', confirmer_name: res.confirmer_name, confirm_date: res.confirm_date});
						}else{
							parentFrameUse().query();
						}
					}
				});
			}
		}); 
	}
	
	//加载快捷键
	function loadHotkeys() {
		hotkeys('S', save);
		hotkeys('D', remove);
		hotkeys('Z', audit);
		hotkeys('U', unAudit);
	}
	
	//字典下拉框
	function loadDict(){
		//库房
		autocompleteAsync("#store_code", "../../../queryMatStoreDictDate.do?isCheck=false", "id", "text", true, true, {is_write:1}, false, '', 240);
		if("${matSafeChange.store_id}"){
			liger.get("store_code").setValue("${matSafeChange.store_id},${matSafeChange.store_no}");
			liger.get("store_code").setText("${matSafeChange.store_code} ${matSafeChange.store_name}");
		}
		
		$("#change_no").ligerTextBox({width: 160, disabled: true});
	}
	
	//加载按钮
	function loadButton(){
        $("#print").ligerButton({ click: printDate, width: 90 });
        $("#printSet").ligerButton({ click: printSet, width: 100 });
        $("#close").ligerButton({ click: this_close, width: 90 });
	}

	//打印设置
	function printSet(){
		var useId=0;//统一打印
		if('${p04027 }'==1){
			//按用户打印
			useId='${user_id }';
		}else if('${p04048 }'==2){
			//按仓库打印
			if(liger.get("store_code").getValue()==""){
				$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
				return;
			}
			useId=liger.get("store_code").getValue().split(",")[0];
		}
		
		officeFormTemplate({template_code: "04099", use_id: useId});
	}
	
	//打印
	function printDate(){
		var useId = 0;//统一打印
		if('${p04027 }' == 1){
			//按用户打印
			useId = '${user_id }';
		}else if('${p04048 }'==2){
			//按仓库打印
			if(liger.get("store_code").getValue()==""){
				$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
				return;
			}
			useId=liger.get("store_code").getValue().split(",")[0];
		}
		
		var para={
				template_code: '04099',
				class_name: "com.chd.hrp.mat.serviceImpl.info.basic.MatSafeChangeService",
				bean_name: "matSafeChangeService",
				method_name: "queryMatSafeChangeByPrintTemlate",
				//isSetPrint:flag,//是否套打，默认非套打
				isPreview: true,//是否预览，默认直接打印
				change_id: $("#change_id").val(),
				use_id: useId,
				p_num: 0
		};
		
		officeFormPrint(para);
	}
</script>
</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	
	<div id="layout">
		<div position="top" >
			<table cellpadding="0" cellspacing="0" class="l-table-edit"  width="100%">
				<tr>
					<td align="right" class="l-table-edit-td"  style="padding-left:20px;">
						调整单号：
					</td>
					<td align="left" class="l-table-edit-td">
						<input type="hidden" id="change_id" value="${matSafeChange.change_id}"/>
						<input type="text" id="change_no" disabled="disabled" value="${matSafeChange.change_no}"/>
					</td>
					
					<td align="right" class="l-table-edit-td" >仓库：</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" id="store_code" />
					</td>
				</tr> 
				<tr>
					<td align="right" class="l-table-edit-td"  style="padding-left:20px;">
						摘要：
					</td>
					<td align="left" class="l-table-edit-td" colspan="3">
						<textarea id="brief" rows="3" style="width: 380px;">${matSafeChange.brief}</textarea>
					</td>
				</tr>
			</table>
		</div>
		<div position="center" >
			<div id="maingrid"></div>
		</div>
		<div position="bottom" >
			<div align="center" style="margin-top: 10px;"><!-- 
				<button id="print" accessKey="P"><b>打印（<u>P</u>）</b></button>
				&nbsp;&nbsp;
				<button id="printSet" accessKey="M"><b>打印模板（<u>M</u>）</b></button>
				&nbsp;&nbsp; -->
				<button id="close" accessKey="C"><b>关闭（<u>C</u>）</b></button>
			</div>
		</div>
	</div>
</body>
</html>
