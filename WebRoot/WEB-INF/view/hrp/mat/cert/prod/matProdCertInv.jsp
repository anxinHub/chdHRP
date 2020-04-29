<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"  xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">

	var parentData = frameElement.dialog.options.data;
	
	$(function(){
		loadTab();
		loadGrid();
		queryCertInv();
	})
	
	function loadTab(){
		$("#cert_id").val(parentData.cert_id);
		$("#cert_code").ligerTextBox({width: 180, disabled: true, value: parentData.cert_code});
		$("#fac_id").val(parentData.fac_id);
		$("#fac_text").ligerTextBox({width: 200, disabled: true, value: parentData.fac_text});
		$("#prod_name").ligerTextBox({width: 180, disabled: true, value: parentData.prod_name});
	}
	
	//加载表格
	function loadGrid(){
		grid = $("#grid").ligerGrid({
			columns: [{
				display: '材料编码', name: 'inv_id', textField: "inv_code", align: 'left', width: 140
			},{
				display: '材料名称(E)', name: 'inv_name', align: 'left', width: 180, 
				editor: {
					type: 'select',
					valueField: 'inv_name',
					textField: 'inv_name',
					selectBoxWidth: '80%',
					selectBoxHeight: 180,
					grid: {
						columns: [{
							display: '材料编码', name: 'inv_code', align: 'left', width: 140
						},{
							display: '材料名称', name: 'inv_name', align: 'left', width: 180, 
						},{
							display: '规格型号', name: 'inv_model', align: 'left', width: 160
						},{
							display: '单位', name: 'unit_name', align: 'left', width: 90
						},{
							display: '单价', name: 'plan_price', align: 'left', width: 100
						},{
							display: '在用状态', name: 'use_state', align: 'left', width: 70
						}],
						switchPageSizeApplyComboBox: false,
						onSelectRow: function (data) {
							var e = window.event;
							if (e && e.which == 1) {
								f_onSelectRow_detail(data);
							}
						},
						gid: "invGrid", 
						url: 'queryMatInvList.do?isCheck=false',
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
			},{
				display: '规格型号', name: 'inv_model', align: 'left', width: 160
			},{
				display: '单位', name: 'unit_name', align: 'left', width: 90
			},{
				display: '单价', name: 'plan_price', align: 'left', width: 100
			},{
				display: '在用状态', name: 'use_state', align: 'left', width: 70
			}],
			dataAction: 'server', dataType: 'server', usePager: false, width: '100%', height: '100%', 
			url:'queryMatProdCertInvList.do?isCheck=false', delayLoad: true, 
			checkbox: true, rownumbers: true, selectRowButtonOnly: false, heightDiff: 30, 
			isAddRow: false, enabledEdit: true, onBeforeEdit: f_onBeforeEdit, 
			toolbar: { items: [{
				text: '选择材料', id: 'chooseInv', icon: 'extend', click: chooseInv
			},{ line:true },{
				text: '删除', id:'removeInv', icon: 'delete', click: removeInv
			},{ line:true },{
				text: '添加行', id:'addInvRow', icon: 'add', click: addInvRow
			},{ line:true },{
				text: '保存', id:'saveInv', icon: 'save', click: saveInv
			},{ line:true },{
				text: '关闭', id:'close', icon: 'close', click: thisClose 
			}] }
		});
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
		if (column_name == "inv_name") {
			grid.updateRow(rowindex_id, {
				inv_id: data.inv_id,
				inv_code: data.inv_code,
				inv_name: data.inv_name,
				inv_model: data.inv_model == null ? "" : data.inv_model,
				unit_name: data.unit_name == null ? "" : data.unit_name,
				plan_price: data.plan_price == null ? "" : data.plan_price,
				use_state: data.use_state == null ? 0 : data.use_state
			});
			/* setTimeout(function (){
				grid.endEditToNext();
			},300) */
		}
		//手动移除grid IE下只能移除......   隐藏不生效
		// $('.l-box-select-lookup').remove();
		return true;
	}
	
	//查询关联材料
	function queryCertInv(){
		grid.options.parms=[];
		grid.options.newPage=1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({name: 'cert_id', value: $("#cert_id").val()});
		//加载查询条件
		grid.loadData(grid.where);
	}
	
	//选择材料
	function chooseInv(){
		parent.$.ligerDialog.open({ 
			title: '选择材料',
			url : 'hrp/mat/cert/prod/matInvListPage.do?isCheck=false',
			height: $(window).height()-50,
			width: $(window).width()-100,
			data: {cert_id: $("#cert_id").val()}, 
			modal: true, showToggle: false, showMax: false,
			isResize: true, showMin: true, showMax: false, 
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		}); 
	}
	
	//删除材料
	function removeInv(){
    	grid.deleteSelectedRow();
	}
	
	//保存
	function saveInv(){
		var data = grid.getData();
		if(data.length == 0){
			$.ligerDialog.error('请选择材料！');
			return false;
		}
		
		var inv_count = 0;
		$.each(data, function(index, item){
			if(item.inv_id){
				inv_count ++;
			}
		});
		
		if(inv_count == 0){
			$.ligerDialog.error('请选择材料！');
			return false;
		}
		
		var paras = {
			cert_id: $("#cert_id").val(), 
			cert_code: $("#cert_code").val(), 
			fac_id: $("#fac_id").val(), 
			allData: JSON.stringify(data)
		};

		ajaxJsonObjectByUrl("saveMatProdCertInv.do?isCheck=false", paras, function(res){
			if(res.state == "true"){
				queryCertInv();
			}
		});
	}
	
	//添加行
	function addInvRow(){
		grid.addRow();
	}
	
	//关闭页面
	function thisClose(){
 		frameElement.dialog.close();
	}
</script>
</head>
<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<table class="table-layout"  width="100%">
		<tr>
			<td align="right" class="l-table-edit-td">
				注册证编号：
			</td> 
			<td align="left" class="l-table-edit-td">
				<input type="hidden" id="cert_id" />
				<input type="text" id="cert_code" />
			</td>
			<td align="right" class="l-table-edit-td">
				生产厂商：
			</td> 
			<td align="left" class="l-table-edit-td">
				<input type="hidden" id="fac_id" />
				<input type="text" id="fac_text" />
			</td>
			<td align="right" class="l-table-edit-td">
				产品名称：
			</td> 
			<td align="left" class="l-table-edit-td">
				<input type="text" id="prod_name" />
			</td>
		</tr>
	</table>
	<div id="grid"></div>
</body>
</html>