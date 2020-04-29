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
<script src="<%=path%>/lib/hrp/mat/mat.js"	type="text/javascript"></script>
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    var isEdit = false;
    var paraPrice = ${p04006};
    var paraMoney = ${p04005};
    var isFrist = true;
    
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
		loadHotkeys();
		query();
    });
    //查询
    function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
        //根据表字段进行添加查询条件
        if(!liger.get("dept_code").getValue()){
        	$.ligerDialog.warn("请选择科室！");
        	return false;
        }
		grid.options.parms.push({
			name : 'dept_id',
			value : liger.get("dept_code").getValue() == null ? "" : liger.get("dept_code").getValue().split(",")[0]
		});
		grid.options.parms.push({
			name : 'begin_date',
			value : $("#begin_date").val()
		});
		grid.options.parms.push({
			name : 'end_date',
			value : $("#end_date").val()
		}); 
		grid.options.parms.push({
			name : 'inv_code', 
			value : $("#inv_code").val()
		});
		grid.options.parms.push({
			name : 'state',
			value : liger.get("check_state").getValue() == null ? "" : liger.get("check_state").getValue()
		}); 
    	//加载查询条件
    	grid.loadData(grid.where);
    	//重置变量
    	isFrist = true;
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [{
					display: 'reg_id', name: 'reg_id', width: 20, align: 'left', isAllowHide: false, hide: true
				}, {
		 			display: '统计日期(E)', name: 'make_date', align: 'left', type: 'date', width: 130, format: 'yyyy-MM-dd', 
		 			editor: {
						type: 'date', showSelectBox: false
					}
		 		}, { 
		 			display: '科室', name: 'dept_name', align: 'left', width: '120', hide: true
		 		}, { 
					display: 'inv_no', name: 'inv_no', width: 20, align: 'left', isAllowHide: false, hide: true
				}, {
					display: '材料编码', name: 'inv_code', width: 110, align: 'left',
				}, {
					display: '材料名称(E)', name: 'inv_id', textField: 'inv_name', width: 200, align: 'left',
					editor: {
						type: 'select',
						valueField: 'inv_id',
						textField: 'inv_name',
						selectBoxWidth: '80%',
						selectBoxHeight: 240,
						grid: {
							columns: [{
								display: '交易编码', name: 'bid_code', width: 100, align: 'left', hide: true
							},{
								display: '材料编码', name: 'inv_code', width: 100, align: 'left'
							}, {
								display: '材料名称', name: 'inv_name', width: 240, align: 'left'
							}, {
								display: '物资类别', name: 'mat_type_name', width: 100, align: 'left'
							}, {
								display: '规格型号', name: 'inv_model', width: 180, align: 'left'
							}, {
								display: '计量单位', name: 'unit_name', width: 80, align: 'left'
							}, {
								display: '包装单位', name: 'pack_name', width: 80, align: 'left'
							}, {
								display: '转换量', name: 'num_exchange', width: 80, align: 'left'
							}, {
								display: '生产厂商', name: 'fac_name', width: 100, align: 'left'
							}, {
								display: '计划单价', name: 'plan_price', width: 90, align: 'right',
								render: function (rowdata, rowindex, value) {
									return formatNumber(value, paraPrice, 1);
								}
							}, {
								display: '是否条码', name: 'is_bar', width: 80, align: 'left',
								render: function (rowdata, rowindex, value) {
									return rowdata.is_bar == 1 ? '是' : '否';
								}
							}, {
								display: '货位', name: 'location_name', width: 100, align: 'left'
							}, {
								display: '零售价', name: 'sell_price', width: 90, align: 'right',
								render: function (rowdata, rowindex, value) {
									return formatNumber(value, paraPrice, 1);
								}
							}],
							switchPageSizeApplyComboBox: false,
							onSelectRow: function (data) {
								var e = window.event;
								if (e && e.which == 1) {
									f_onSelectRow_detail(data);
								}
							},
							url: '../../../queryMatDuraInvList.do?isCheck=false', 
							pageSize: 500,
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
						delayLoad: false, keySupport: true, autocomplete: true,// rownumbers : true,
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
					display: '规格型号', name: 'inv_model', width: 180, align: 'left'
				}, {
					display: '计量单位', name: 'unit_name', width: 60, align: 'left'
				}, {
					display: '生产厂商', name: 'fac_name', width: 180, align: 'left'
				},{
					display: '单价(E)', name: 'price', width: 80, align: 'right',
					editor: {
						type: 'numberbox',
						precision: paraPrice
					},
					render: function (rowdata, rowindex, value) {
						//rowdata.price = value == null ? "" : formatNumber(value, paraPrice, 0);
						return value == null ? "" : formatNumber(value, paraPrice, 1);
					}
				}, {
					display: '条形码(E)', name: 'bar_code', width: 110, type: 'text', align: 'left',
					editor: {
						type: 'text',
					}
				}, {
					display: '数量(E)', name: 'amount', width: 80, type: 'float', align: 'left',
					editor: {
						type: 'float',
					}
				}, {
					display: '金额(E)', name: 'amount_money', width: 80, align: 'right',
					editor: {
						type: 'numberbox',
						precision: paraMoney
					},
					render: function (rowdata, rowindex, value) {
						//rowdata.amount_money = value == null ? "" : formatNumber(value, paraMoney, 0);
						return value == null ? "" : formatNumber(value, paraMoney, 1);
					},
				},
				{
					display: '单据状态', name: 'state', width: 80, align: 'left',hide:true
				},
				{
					display: '状态', name: 'state_name', width: 80, align: 'left',
					/* render: function (rowdata, rowindex, value) {
						if(value == '1'){
							return "未审核";
						}else if(value == '2'){
							return "已审核";
						}else{
							return "";
						}
					}, */
				},{
		 			display: '备注(E)', name: 'note', align: 'left', type: 'text', width: 180,
		 			editor: {
						type: 'text',
					}
		 		}
		 	],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatDuraInitDept.do',
			width: '100%', height: '100%', checkbox: true,rownumbers:true, enabledEdit: isEdit, 
			onBeforeEdit: f_onBeforeEdit, onBeforeSubmitEdit: f_onBeforeSubmitEdit, 
			onChangeRow: f_onChangeRow, onAfterEdit: f_onAfterEdit,
			checkBoxDisplay: isCheckDisplay,
			delayLoad: true,//初始化不加载，默认false
			selectRowButtonOnly:true,//heightDiff: -10,
			toolbar: { items: [
				{ text: '编辑（<u>E</u>）', id:'edit', click: changeEdit, icon: 'edit' },
				{ line:true },
				{ text: '删除（<u>D</u>）', id:'delete', click: remove, icon: 'delete' },
				{ line:true }, 
				{ text: '审核（<u>S</u>）', id:'audit', click: audit, icon: 'audit' },
				{ line:true }, 
				{ text: '消审（<u>U</u>）', id:'unAudit', click: unAudit, icon: 'unaudit' },
				{ line:true },
				/* { text: '导入（<u>I</u>）', id:'imp', click: imp, icon: 'up' },
				{ line:true },
				{ text: '下载导入模板（<u>M</u>）', id:'downTemplate', click: downTemplate, icon: 'down' },
				{ line:true }, */
			]}, 
			onSuccess : function(){
				
			} 
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    var rowindex_id, column_name;
    
    function f_onBeforeEdit(e) {
		rowindex_id = e.rowindex;
		column_name = e.column.name;
		
		//合计行不可编辑
		if(grid.getRow(rowindex_id).make_date && grid.getRow(rowindex_id).make_date == "合计"){

			return false;
		}
	}
    
	//选中回充数据
	function f_onSelectRow_detail(data, rowindex, rowobj) {
		selectData = "";
		selectData = data;
		//回充数据 
		if (selectData != "" || selectData != null) {
			if (column_name == "inv_id") {
				grid.updateRow(rowindex_id, {
					inv_no: data.inv_no,
					inv_code: data.inv_code,
					inv_model: data.inv_model == null ? "" : data.inv_model,
					unit_name: data.unit_name == null ? "" : data.unit_name,
					is_bar: data.is_bar == null ? 0 : data.is_bar,
					price: data.plan_price == null ? 0: data.plan_price,
				    fac_name : data.fac_name == null ? "" : data.fac_name,
				});
			}
		}
		//手动移除grid IE下只能移除......   隐藏不生效
		// $('.l-box-select-lookup').remove();
		return true;
	}
	// 编辑单元格提交编辑状态之前作判断限制
	function f_onBeforeSubmitEdit(e) {
		//alert(JSON.stringify(e));
		if (e.column.name == "make_date" && !e.value){
			return false;
		}
		if (e.column.name == "inv_id" && !e.value){
			return false;
		}
		if (e.column.name == "price" && !e.value){
			return false;
		}
		if (e.column.name == "amount" && !e.value){
			return false;
		}
		if (e.column.name == "amount_money" && (!e.value || e.value == 0)){
			return false;
		}
		return true;
	}
	// 跳转到下一个单元格之前事件
	function f_onAfterEdit(e) {
		if (e.value != "") {
			if (e.column.name == "amount") {
				//自动计算金额
				if (e.record.price != undefined && e.record.price != "") {
					grid.updateCell('amount_money', e.value * e.record.price, e.rowindex);
				}
			} else if (e.column.name == "price") {
				//自动计算金额
				if (e.record.amount != undefined && e.record.amount != "") {
					grid.updateCell('amount_money', e.record.amount ? e.value * e.record.amount : 0, e.rowindex);
				}
			} else if (e.column.name == "amount_money") {
				//自动计算数量
				if (e.record.price != undefined && e.record.price != "") {
					grid.updateCell('amount', e.value / e.record.price, e.rowindex);
				}
			}
		}
		return true;
	}
	//换行事件
	function f_onChangeRow(e){
		if(liger.get("dept_code").getValue()){
			if(!e.record.make_date){
				return false;
			}
			if(!e.record.inv_id){
				return false;
			}
			if(e.record.price == '' || e.record.price == 'undefined'){
				return false;
			}
			if(!e.record.amount){
				return false;
			}
			if(e.record.amount_money == '' || e.record.amount_money == 'undefined'){
				return false;
			}
			e.record.dept_id = liger.get("dept_code").getValue().split(",")[0];
			e.record.dept_no = liger.get("dept_code").getValue().split(",")[1];
			ajaxJsonObjectByUrl("saveMatDuraInitDept.do", e.record, function(responseData) {
				//alert(JSON.stringify(responseData));
				if (responseData.state == "true") {
					//回写联合主键用于继续操作当前行数据
					if(!e.record.reg_id){
						grid.updateRow(e.rowindex, {
							group_id: responseData.group_id,
							hos_id: responseData.hos_id,
							copy_code: responseData.copy_code,
							reg_id: responseData.reg_id,
							state: 1, //新增时状态为1未审核 
						});
					}
				}else{
					//改变当前行背景色
				}
			});
		}else{
			$.ligerDialog.warn("请选择科室！");
		}
	}
    
	//合计行只读
    function isCheckDisplay(rowdata){
       	if(rowdata.make_date == "合计") return false;
         return true;
    }
    
    //键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('D', remove);
		hotkeys('S', audit);
		hotkeys('U', unAudit);
		hotkeys('I',imp);
	}
    
    function changeEdit(){
    	isEdit = isEdit ? false : true;
    	grid.set("enabledEdit", isEdit);
    	if(!grid.rows.length || isFrist){
    		grid.addRow();
    		isFrist = false;
    	}
    }
    	
    //删除
    function remove(){
		var data = gridManager.getCheckedRows();
		var dept_id = liger.get("dept_code").getValue().split(",")[0];
		if (data.length == 0){
        	$.ligerDialog.warn('请选择行');
        	return;
		}else{
			var ParamVo =[];
			var rows = [];
			var is_delete = false;
			var inv_names = "";
			$(data).each(function (){	
				if(this.reg_id){
					if(this.state != 1){
						inv_names = inv_names + this.inv_name + "<br>";
					}
					ParamVo.push(
						this.group_id   +"@"+ 
						this.hos_id   +"@"+ 
						this.copy_code   +"@"+ 
						dept_id   +"@"+ 
						this.reg_id 
					) 
					is_delete = true;
				}
				rows.push(this);
			});

			if(inv_names != ""){
				$.ligerDialog.error("删除失败！<br>以下材料已审核状态：<br>"+inv_names);
				return;
			}
			
			$.ligerDialog.confirm('确定删除?', function (yes){
				if(yes){
					if(is_delete){
						ajaxJsonObjectByUrl("deleteMatDuraInitDept.do",{ParamVo : ParamVo.toString()},function (responseData){
							if(responseData.state=="true"){
								grid.deleteRange(rows);
							}
						});
					}else{
						grid.deleteRange(rows);
					}
				}
			});  
		}
	}
	
    //审核
	function audit(){
		var data = gridManager.getCheckedRows();
		var dept_id = liger.get("dept_code").getValue().split(",")[0];
		if (data.length == 0){
			$.ligerDialog.warn('请选择行');
		}else{
			var ParamVo =[];
			var inv_names = "";
			var inv_names_rows ="";
			$(data).each(function (){	
				if(this.reg_id){
					if(this.state != 1){
						inv_names = inv_names + this.inv_name + ",";
					}
					ParamVo.push(
						this.group_id   +"@"+ 
						this.hos_id   +"@"+ 
						this.copy_code   +"@"+ 
						dept_id   +"@"+ 
						this.reg_id 
					) 
				}else{
					inv_names_rows ="选择的记录中存在没有保存的数据";
				}
			});
			if(inv_names_rows!= ""){
				$.ligerDialog.error("审核失败！<br>选择的记录中存在没有保存的数据");
				return;
			}
			if(inv_names != ""){
				$.ligerDialog.error("审核失败！<br>以下材料不是未审核状态：<br>"+inv_names);
				return;
			}
			$.ligerDialog.confirm('确定审核?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("auditMatDuraInitDept.do",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.state=="true"){
							query();
						}
					});
				}
			}); 
		}
	}

    //消审
	function unAudit(){
		var data = gridManager.getCheckedRows();
		var dept_id = liger.get("dept_code").getValue().split(",")[0];
		if (data.length == 0){
			$.ligerDialog.warn('请选择行');
		}else{
			var ParamVo =[];
			var inv_names = "";
			$(data).each(function (){		
				if(this.reg_id){
					if(this.state != 2){
						inv_names = inv_names + this.inv_name + ",";
					}
					ParamVo.push(
						this.group_id   +"@"+ 
						this.hos_id   +"@"+ 
						this.copy_code   +"@"+ 
						dept_id   +"@"+ 
						this.reg_id 
					) 
				}
			});
			if(inv_names != ""){
				$.ligerDialog.error("消审失败！<br>以下材料不是审核状态：<br>"+inv_names);
				return;
			}
			$.ligerDialog.confirm('确定消审?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("unAuditMatDuraInitDept.do",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.state=="true"){
							query();
						}
					});
				}
			}); 
		}
	}
    
    //导入
    function imp(){
		var para = {
				url : 'hrp/mat/dura/init/dept/importPage.do?isCheck=false',
				title : '导入耐用品科室期初',
				width : 0,
				height : 0,
				isShowMin : false,
				isModal : true,
				data : {
					grid : grid
				}
			};
			parent.openDialog(para);
    }

    //下载打印模板
    function downTemplate(){
    	location.href = "downTemplate.do?isCheck=false";
    }
   
    function loadDict(){
		//字典下拉框
		autocompleteAsync("#dept_code", "../../../queryHosDeptDictByPerm.do?isCheck=false", "id", "text", true, true, {is_last : 1}, true, false, "200");
		liger.get("dept_code").set("onSelected",function(){
    		query();
    	});
		autoCompleteByData("#check_state", matDuraReg_state.Rows, "id", "text", true, true);
		
        $("#begin_date").ligerTextBox({width:100});
        $("#end_date").ligerTextBox({width:100});
        $("#inv_code").ligerTextBox({width:238});
        autodate("#begin_date", "yyyy-mm-dd", "month_first");
        autodate("#end_date", "yyyy-mm-dd", "month_last");
        $("#query").ligerButton({ click: query, width: 90 });
        $("#imp").ligerButton({ click: imp, width: 90 });
	}  
	
	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" >
        <tr>
            <td align="right" class="l-table-edit-td" >
            	统计日期：
            </td>
            <td align="left" class="l-table-edit-td">
				<table>
					<tr>
						<td align="left" >
							<input class="Wdate" name="begin_date" id="begin_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
						<td align="right" class="l-table-edit-td"  >
							至：
						</td>
						<td align="left" class="l-table-edit-td">
							<input class="Wdate" name="end_date" id="end_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
            		</tr>
				</table>
	        </td>
        	<td align="right" class="l-table-edit-td">
            	<span style="color: red">*</span>科室：
            </td>
            <td align="left" class="l-table-edit-td">
				<input name="dept_code" type="text" id="dept_code" ltype="text" validate="{required:false}" />
	        </td>
			<td align="right" class="l-table-edit-td" >
				状态：
			</td>
            <td align="left" class="l-table-edit-td">
            	<input name="check_state" type="text" id="check_state" ltype="text" validate="{required:false}" />
            </td>
        </tr> 
        <tr>
			<td align="right" class="l-table-edit-td" >
				材料信息：
			</td>
			<td align="left" class="l-table-edit-td">
				<input name="inv_code" type="text" id="inv_code" ltype="text" validate="{required:false}" />
			</td>
        	<td align="right" class="l-table-edit-td" colspan="4">
        		<button id="query" type="button" accessKey="Q"><b>查询（<u>Q</u>）</b></button> 
        		&nbsp;&nbsp;&nbsp;&nbsp;
        		<button id="imp" type="button" accessKey="I"><b>导入（<u>I</u>）</b></button> 
        		&nbsp;&nbsp;&nbsp;&nbsp;
        	</td>
        </tr> 
    </table>
	<div id="maingrid"></div>
</body>
</html>
