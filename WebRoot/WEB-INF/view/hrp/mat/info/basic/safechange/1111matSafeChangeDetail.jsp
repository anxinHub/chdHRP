<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>  
<meta http-equiv="Content-Type" content="text/html;">
<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<jsp:include page="${path}/resource.jsp">
	<jsp:param value="hr,dialog,select,datepicker,upload,validate,grid,time"
		name="plugins" />
</jsp:include>
<script src="<%=path%>/lib/ligerUI/js/core/base.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/plugins/ligerLayout.js" type="text/javascript"></script>
<script>
	var grid, store_code, formValidate;
	var period_source = [{"label": "年", "id": 1}, {"label": "季", "id": 2}, {"label": "月", "id": 3}, {"label": "天", "id": 4}];
	var parentFrameName = parent.$.etDialog.parentFrameName; // 拿取window名
	var parentWindow = parent.window[parentFrameName]; // 当前页拿取window对象
	var matSafeChange = parentWindow.matSafeChange;
	
	//验证初始化
	function initValidate() {
		formValidate = $.etValidate({
			items : [ {
				el : $("#store_code"),
				required : true
			}, ]
		});
	}
	
	//字段初始化
	function initDict() {
		store_code = $("#store_code").etSelect({
			url : "../../../queryMatStore.do?isCheck=false",
			defaultValue : true,
			onSelect: function(value){
				grid.columns[1].editor.dataModel.url = "queryMatInvBySafeChange.do?isCheck=false&store_id="+value.split(",")[0];
				grid.refreshView();
			}
		});
	}
	
	//查询
	function query(){
		params = [ {
			name: 'change_id', value: matSafeChange.change_id 
		} ]
		
		grid.loadData(params, "queryMatSafeChangeDetailById.do?isCheck=false");
	}
	//Grid
	function initGrid() {
		/* 列头 */
		var columns = [ {
			display: "材料编码", name: "inv_code", width: 140, isSort: false, 
		}, {
			display: "材料名称", name: "inv_name", width: 200, isSort: false, 
			editor: {
				type: "grid", 
				resizable: true,
			    width: 800,
			    height: 300,
				columns: [ {
					display: "材料编码", name: "inv_code", width: 140, 
				}, {
					display: "材料名称", name: "inv_name", width: 200,
				}, {
					display: "规格型号", name: "inv_model", width: 180,
				}, {
					display: "计量单位", name: "unit_name", width: 100,
				}, {
					display: "生产厂商", name: "fac_name", width: 180,
				} ],
			    dataModel: {
			        url: 'queryMatInvBySafeChange.do?isCheck=false',
			    },
			}
		}, {
			display: '周期单位(E)', name: 'period_name', width: 80, isSort: false, 
			editor: {
				type: 'select',
			    autoFocus : true,   //  为true时 下拉框默认选择第一个
			    disabled : false,
				keyField : 'period',
			    source: period_source,
			}
		}, { 
			display: '安全周期数(E)', name: 'period_num', align: 'right', width:'100', isSort: false, 
			editor: { type : 'number' },
			render: function (ui) {
				return formatNumber(value ==null ? 0 : ui.cellData, 2, 1);
			}
		}, {
			display: '最低库存(E)', name: 'low_limit', align: 'right',width:'100', isSort: false, 
			editor: { type : 'number' },
			render: function (ui) {
				return formatNumber(value ==null ? 0 : ui.cellData, 2, 1);
			}
		}, { 
			display: '安全库存(E)', name: 'secu_limit', align: 'right',width:'100', isSort: false, 
			editor: { type : 'number' },
			render: function (ui) {
				return formatNumber(value ==null ? 0 : ui.cellData, 2, 1);
			}
		}, { 
			display: '最高库存(E)', name: 'high_limit', align: 'right',width:'100', isSort: false, 
			editor: { type : 'number' },
			render: function (ui) {
				return formatNumber(value ==null ? 0 : ui.cellData, 2, 1);
			}
		}, { 
			display: '临近预警量(E)', name: 'warn_amount', align: 'right',width:'100', isSort: false, 
			editor: { type : 'number' },
			render: function (ui) {
				return formatNumber(value ==null ? 0 : ui.cellData, 2, 1);
			}
		}, { 
			display: '临近预警包装量(E)', name: 'pack_amount', align: 'right',width:'100', isSort: false, 
			editor: { type : 'number' },
			render: function (ui) {
				return formatNumber(value ==null ? 0 : ui.cellData, 2, 1);
			}
		}, { 
			display: '配送周期(E)', name: 'ps_period', align: 'right',width:'100', isSort: false, 
			editor: { type : 'number' },
			render: function (ui) {
				return formatNumber(value ==null ? 0 : ui.cellData, 2, 1);
			}
		}, { 
			display: '采购周期(E)', name: 'cg_period', align: 'right',width:'100', isSort: false, 
			editor: { type : 'number' },
			render: function (ui) {
				return formatNumber(value ==null ? 0 : ui.cellData, 2, 1);
			}
		}, { 
			display: '最小采购批量(E)', name: 'min_pur', align: 'right',width:'100', isSort: false, 
			editor: { type : 'number' },
			render: function (ui) {
				return formatNumber(value ==null ? 0 : ui.cellData, 2, 1);
			}
		}, { 
			display: '日消耗量周期(E)', name: 'rxhl_period', align: 'right',width:'100', isSort: false, 
			editor: { type : 'number' },
			render: function (ui) {
				return formatNumber(value ==null ? 0 : ui.cellData, 2, 1);
			}
		} ]; 
		/* 头部按钮 */
	    var toolbar = {
			items: [ { 
				type: "button", label: '新增行', icon: 'add', listeners: [{ click: addRow }] 
			}, { 
				type: "button", label: '审核', icon: 'audit', listeners: [{ click: audit }] 
			}, { 
				type: "button", label: '消审', icon: 'cancel', listeners: [{ click: unAudit }] 
			}, { 
				type: "button", label: '删除行', icon: 'delete', listeners: [{ click: removeRow }] 
			} ]
		};
		
		/* 表格基础参数 */
		var paramObj = {	
			width: 'auto',
			height: '90%', 
			usePager: false, 
			editable: true,
			inWindowHeight: true,
			toolbar: toolbar,
			checkbox: true,
			addRowByKey: true,
			columns: columns, 
		};
		
		grid = $("#grid").etGrid(paramObj);
	}
	//新增行
	function addRow(){
		grid.addRow();
	}
	//删除行
	function removeRow(){
		grid.deleteSelectedRows();
	}
	
	// 审核
	function audit(){
		if (state != 0){
			$.ligerDialog.error('审核失败！单据不是未审核状态,不能审核');
			return;
		}
		
		$.ligerDialog.confirm('确定审核?', function (yes){
			if(yes){
				ajaxJsonObjectByUrl("auditMatSafeChange.do", {ids : $("#change_id").val()}, function (res){
					if(res.state=="true"){
						state = 1;
						liger.get("gridToolBar").setDisabled("add");
						liger.get("gridToolBar").setDisabled("delete");
						liger.get("gridToolBar").setDisabled("save");
						liger.get("gridToolBar").setDisabled("audit");
						liger.get("gridToolBar").setEnabled("unaudit");
						//liger.get("save").setDisabled();
						if(parentWindow.gridRowIdByOpen){
							parentWindow.grid.updateRow(parentWindow.gridRowIdByOpen, {state: 1, state_name: '已审核'});
						}else{
							parentWindow.query();
						}
					}
				});
			}
		}); 
	}
	
	//消审
	function unAudit(){
		if (state != 1){
			$.ligerDialog.error('消审失败！单据不是已审核状态,不能消审');
			return;
		}
		
		$.ligerDialog.confirm('确定消审?', function (yes){
			if(yes){
				ajaxJsonObjectByUrl("unAuditMatSafeChange.do", {ids : $("#change_id").val()}, function (res){
					if(res.state=="true"){
						state = 0;
						liger.get("gridToolBar").setEnabled("add");
						liger.get("gridToolBar").setEnabled("delete");
						liger.get("gridToolBar").setEnabled("save");
						liger.get("gridToolBar").setEnabled("audit");
						liger.get("gridToolBar").setDisabled("unaudit");
						//liger.get("save").setEnabled();
						if(parentWindow.gridRowIdByOpen){
							parentWindow.grid.updateRow(parentWindow.gridRowIdByOpen, {state: 0, state_name: '未审核'});
						}else{
							parentWindow.query();
						}
					}
				});
			}
		}); 
	}
	
	//保存
	function save() {
		if (!formValidate.test()) {
			return false;
		}
		
		//组装明细
		var allData = grid.getAllData();
		
		if(!allData || allData.length == 0){
            $.etDialog.warn("请添加明细信息");
            return false;
		}
		
		var msg = "";
		$.each(allData,function(index, v){
			if(!v.subj_code || !v.sort_code || !v.cal){
				msg += "预算会计第"+(index+1)+"行科目、排序号、公式不能为空<br/>";
			}
		});
		
		if(msg){
			$.etDialog.warn(msg);
            return false;
		}
		
		ajaxPostData({
			url : 'saveMatSafeChange.do',
			data : {
				change_id: $("#change_id").val(), 
				change_no: $("#change_no").val(), 
				store_id : store_code.getValue(),
				brief : $("#brief").val(), 
				allData: JSON.stringify(allData)
			},
		    success: function (res) {
		    	
				parentWindow.query(); 
				if(res.change_no){
					$("#change_no").val(res.change_no);
				}
			},
		})
	}
	
	function this_close(){
		var curIndex = parent.$.etDialog.getFrameIndex(window.name);
		parent.$.etDialog.close(curIndex);
	}
	
	function loadData(){
		$("#change_id").val(matSafeChange.change_id);
		$("#change_no").val(matSafeChange.change_no);
		store_code.setValue(matSafeChange.store_id);
		store_code.setText(matSafeChange.store_code + " " + matSafeChange.store_name);
		$("#brief").val(matSafeChange.brief);
		
		query();
	}
	
	function initLayout(){
		$("#layout").ligerLayout({ 
			topHeight: 120, 
			InWindow: true, 
			height: '100%', 
			bottomHeight: 50, 
		});
	}
	
	$(function() {
		//initLayout();
		initDict();
		initValidate();
		initGrid();
		
		if(matSafeChange.change_id){
			loadData();
		}

		$("#save").click(function() {
			save();
		})
		
		$("#close").click(function() {
			this_close();
		})
	})
</script>
</head>

<body style="overflow-x: hidden">
	<div id="layout" style="z-index: 2">
		<div position="top" class="flex-wrap">
			<table class="table-layout">
				<tr>
					<td class="label"><font size="2" color="red">*</font>调整单号：</td>
					<td class="ipt">
						<input id="change_id" type="hidden" />
						<input id="change_no" type="text" value="系统生成" disabled="disabled"/>
					</td>
					
					<td class="label">仓库：</td>
					<td class="ipt">
						<select id="store_code" style="width: 180px;"></select>
					</td>
				</tr> 
				<tr>
					<td class="label">备注：</td>
					<td class="ipt" colspan="3">
						<textarea id="brief" cols="20" rows="30" style="width: 520px; height: 60px"></textarea>
					</td>
				</tr>
			</table>
		</div>
		<div position="center" style="height: auto;">
			<div id="grid"></div>
		</div>
		<div position="bottom" class="button-group" style="height: 60px;">
			<button id="save">保存</button>
			<button id="close">关闭</button>
		</div>
	</div>
</body>

</html>