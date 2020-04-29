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
<script src="<%=path%>/lib/stringbuffer.js"></script>
<script type="text/javascript">
	var grid;
	var gridManager;
	
	$(function (){ 
		$("#layout").ligerLayout({ 
			topHeight: 90, 
			//不允许调整大小
			allowTopResize: false, 
		});
		loadDict();
		loadHead(null);
		loadHotkeys();
		
		$("#store_code").bind("change", function () {
			grid.columns[3].editor.grid.url = 'queryMatInvBySafeChange.do?isCheck=false&store_id=' + liger.get("store_code").getValue().split(",")[0];
			grid.reRender();
		});
	}); 
	
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
						}],
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
			},*/ 
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
			checkbox: true, rownumbers: true, 
			enabledEdit: true, isAddRow: true, inWindow: false, 
			enabledSort: false, delayLoad: true,//初始化不加载，默认false
			onBeforeEdit: f_onBeforeEdit, 
			selectRowButtonOnly: true, heightDiff: 30,  
			toolbar: { items: [ { 
				text: '删除（<u>D</u>）', id: 'delete', click: remove, icon: 'delete' 
			}, { 
				line:true 
			}, {
				text: '保存（<u>S</u>）', id: 'save', click: save, icon: 'save' 
			}, { 
				line:true 
			}, {
				text: '关闭（<u>C</u>）', id: 'close', click: this_close, icon: 'close' 
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
			change_no: $("#change_no").val(),
			store_id: store_code.split(",")[0],
			brief: $("#brief").val(),
			allData: JSON.stringify(data)
		};

		ajaxJsonObjectByUrl("saveMatSafeChange.do?isCheck=false", formPara, function(res){
			if(res.state == "true"){
				parentFrameUse().query();
				$.ligerDialog.confirm('是否继续添加调整单？', function (yes){
					if(yes){
						$("#change_no").val("");
						$("#brief").val("");
						grid.deleteAllRows();
					}else{
						parentFrameUse().openUpdate(res.change_id, 1);
						this_close();
					}
				});
			}
		});
	}
	
	//关闭页面
	function this_close(){
		frameElement.dialog.close();
	}
	
	//加载快捷键
	function loadHotkeys() {
		hotkeys('S', save);
		hotkeys('D', remove);
		hotkeys('C', this_close);
	}
	
	//字典下拉框
	function loadDict(){
		//库房
		autocompleteAsync("#store_code", "../../../queryMatStoreDictDate.do?isCheck=false", "id", "text", true, true, {is_write:1}, true, '', 240);
		
		$("#change_no").ligerTextBox({width: 160, disabled: true});
	}

	function is_addRow() {
		setTimeout(function () { //当数据为空时 默认新增一行
			grid.addRow();
		}, 100);
	}
</script>
</head>

<body onload="is_addRow()">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	
	<div id="layout">
		<div position="top" >
			<table cellpadding="0" cellspacing="0" class="l-table-edit"  width="100%">
				<tr>
					<td align="right" class="l-table-edit-td"  style="padding-left:20px;">
						调整单号：
					</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" id="change_no" disabled="disabled" value="系统生成"/>
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
						<textarea id="brief" rows="3" style="width: 380px;"></textarea>
					</td>
				</tr>
			</table>
		</div>
		<div position="center" >
			<div id="maingrid" ></div>
		</div>
	</div>
</body>
</html>
