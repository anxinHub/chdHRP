<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />   
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
	var grid;
	var detailGrid;
	var gridManager = null;
	var userUpdateStr;
	var gridRowIndex;
	var gridRowData;
	var detailGridAllChecked;//0:全不选,1:全选,2:根据条件判断 
	var accept_id;
	var acceptObj = {};
	$(function() {
		
		loadDict();//加载下拉框
		loadHead(null);
		loadHotkeys();
		loadDetailHead(null);
		query();
		
		
	});
	
	//初始化全局变量
    function initPara(){
        accept_id = "";
    	acceptObj = {};
		detailGrid.deleteAllRows();    
    }
	
	function changeDetailGrid(obj){
    	
    	accept_id = obj;
    	
    	queryDetail();
    }

	function save() {
		gridManager.endEdit();
		if ($("#create_date").val() == "") {
			$.ligerDialog.error('制单日期不能为空');
			return;
		}
		if (liger.get("store_id").getValue() == "") {
			$.ligerDialog.error('仓库不能为空');
			return;
		}
		if (liger.get("ven_id").getValue() == "") {
			$.ligerDialog.error('供应商不能为空');
			return;
		}
		if (liger.get("purc_emp").getValue() == "") {
			$.ligerDialog.error('采购员不能为空');
			return;
		}
		if (liger.get("ass_naturs").getValue() == "") {
			$.ligerDialog.error('资产性质不能为空');
			return;
		}
		var data = detailGrid.getData();
		var num = 0;
		var nat='';
		var ass_naturs=liger.get("ass_naturs").getValue();
		var accept_id = data[0].accept_id;
		for (var i = 0; i < data.length; i++) {

			if (data[i].ass_code) {
				num++;
			}
			if(ass_naturs != data[i].ass_naturs){
				nat+='第'+(i+1) + '、'
			}
		}
		if (!num) {
			$.ligerDialog.error('明细数据不能为空');
			return false;
		}
		if(nat.length>0){
			$.ligerDialog.error(nat+"行资产性质与上面不一致！");
			return;
		}
		var formPara = {
			ass_in_no : $("#ass_in_no").val() == "" ? '0' : $("#ass_in_no").val(),
			store_id : liger.get("store_id").getValue().split("@")[0],
			store_no : liger.get("store_id").getValue().split("@")[1],
			store_code : liger.get("store_id").getText().split(" ")[0],
			dept_id : liger.get("dept_id").getValue().split("@")[0],
			dept_no : liger.get("dept_id").getValue().split("@")[1],
			ven_id : liger.get("ven_id").getValue().split("@")[0],
			ven_no : liger.get("ven_id").getValue().split("@")[1],
			proj_id : liger.get("proj_id").getValue().split("@")[0],
			proj_no : liger.get("proj_id").getValue().split("@")[1],
			purc_emp : liger.get("purc_emp").getValue(),
			invoice_no:$("#invoice_no").val(),
			invoice_date:$("#invoice_date").val(),
			bus_type_code : '01',
			note : $("#note").val(),
			create_date : $("#create_date").val(),
			ass_naturs:liger.get("ass_naturs").getValue(),
			accept_id : accept_id,
			ParamVo : JSON.stringify(data)
		};
		if (validateGrid()) {
			ajaxJsonObjectByUrl("saveAssAcceptInMain.do?isCheck=false", formPara, function(
					responseData) {
				if (responseData.state == "true") {
					$("#ass_in_no").val(responseData.ass_in_no);
					$("#in_money").val(responseData.in_money)
					query();
					parentFrameUse().query();
				}
			});
		}
	}
	function this_close() {
		frameElement.dialog.close();
	}

	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		grid.options.parms.push({
			name : 'in_state',
			value : liger.get("in_state").getValue()
		});
		grid.options.parms.push({
			name : 'ass_naturs',
			value : liger.get("ass_naturs").getValue()
		});
		
		grid.loadData(grid.where);
		initPara();
		
	}
	//查询
    function  queryDetail(){
    	detailGrid.options.parms=[];
    	detailGrid.options.newPage=1;
        //根据表字段进行添加查询条件
 		detailGrid.options.parms.push({
 			name : 'accept_id',
 			value : accept_id
 		});
        
    	//加载查询条件
    	detailGrid.loadData(detailGrid.where);
	}


	function loadHead() {
		grid = $("#maingrid").ligerGrid(
				{
					columns : [
							{
								display : '验收单号',
								name : 'accept_no',
								align : 'left',
								width : 100,frozen : true,
							}, {
								display : '摘要',
								name : 'accept_desc',
								align : 'left',
								width : 100,
								frozen : true,
							}, {
								display : '验收日期',
								name : 'accept_date',
								align : 'left',
								width : 90,
								frozen : true,
							}, {
								display : '是否入库',
								name : 'in_state',
								align : 'left',
								width : 100,
								render : function(rowdata, rowindex, value) {
									if (rowdata.in_state == 0) {
										return "未入库";
									}else if(rowdata.in_state == 1){
										return "已入库";
									}
									return "无状态";
								}
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
								display : '资产性质',
								name : 'naturs_name',
								align : 'left',
								width : 90
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

							}, {
								display : '验收id',
								name : 'accept_id',
								align : 'left',
								width : 10,hide:true
							}],
					dataAction : 'server',
					dataType : 'server',
					usePager : true,
					url : 'queryAssAcceptMain.do?isCheck=false&state=1',
					width : '100%',
					height : '60%',
					checkbox : true,
					rownumbers : false,inWindow: false, isScroll:true, 
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
						} , {
							text : '关闭',
							id : 'close',
							click : this_close,
							icon : 'candle'
						}]
					},
					onClickRow : function (rowdata, rowindex, value){
						if(accept_id != rowdata.accept_id){
							gridRowIndex = rowindex;
							gridRowData = rowdata;
							detailGridAllChecked = 2;
							changeDetailGrid(rowdata.accept_id);
						}
					},
					onCheckRow : function(checked, rowdata, rowindex, rowobj){
						if(checked){//选中
							acceptObj[accept_id] = {};
							if(JSON.stringify(detailGrid.records) != "{}"){
								for (var rowid in detailGrid.records){
									if(accept_id == detailGrid.records[rowid].accept_id){
					                   	detailGrid.select(detailGrid.records[rowid]);
									}
								}
							}
						}else{//取消选中
							delete acceptObj[accept_id];
							if(JSON.stringify(detailGrid.records) != "{}"){
								for (var rowid in detailGrid.records){
									if(accept_id == detailGrid.records[rowid].accept_id){
										detailGrid.unselect(detailGrid.records[rowid]); 
									}
								}
							}
						}
						//明细grid是否全选(1)或全不选(0)
						detailGridAllChecked = checked ? 1 : 0;
					}, 
					onCheckAllRow : function(checked,element){
						if(checked){
							var data = gridManager.getData();
							$(data).each(function(){
								acceptObj[this.accept_id] = {};
							})
						}
					}
				});

		gridManager = $("#maingrid").ligerGetGridManager();
		
		
	}
	
	function loadDetailHead(){
		detailGrid = $("#detailgrid")
		.ligerGrid(
				{
					columns : [
							{
								display : '资产编码',
								name : 'ass_code',
								align : 'left',
								width : '90'
							},
							{
								display : '资产名称',
								name : 'ass_id',
								align : 'left',
								textField : 'ass_name',
								render : function(rowdata, rowindex,
										value) {
									return rowdata.ass_name;
								},
								width : '130',
			                    totalSummary:
			                    {
			                        render: function (suminf, column, cell)
			                        {
			                            return '<div>合计</div>';
			                        },
			                        align: 'center'
			                    }

							},
							{
								display : '规格',
								name : 'ass_spec',
								align : 'left',
								width : '100',
								editor : {
									type : 'text'
								},
								render : function(rowdata, rowindex,
										value) {
									if(rowdata.ass_spec == null || rowdata.ass_spec == ""){
										rowdata.ass_spec = "*";
									}
									return  rowdata.ass_spec; 
								}
							},
							{
								display : '计量单位',
								name : 'unit_code',
								textField:'unit_name',
								editor : {
									type : 'select',
									valueField : 'id',
									textField : 'text',
									url : '../queryHosUnitDict.do?isCheck=false',
									keySupport : true,
									autocomplete : true,
									onSuccess : function(data) {
										if (initvalue != undefined
												&& initvalue != "") {
											this.setValue(initvalue);
											initvalue = "";
										}
									}
								},
								align : 'left',
								width : '50'
							},
							{
								display : '入库数量',
								name : 'in_amount',
								/* editor : {
									type : 'int'
								}, */
								align : 'left',
								width : '100'
							},
							{
								display : '单价',
								name : 'price',
								/* editor : {
									type : 'numberbox',
									precision: '${ass_04006}'
								}, */
								align : 'right',
								render : function(item) {
									return formatNumber(
											item.price, '${ass_05006}', 1);
								},
								width : '100'

							},
							{
								display : '金额',
								name : 'total_price',
								align : 'right',
								totalSummary : {
									render: function (suminf, column, cell)
			                        {
										$("#in_money").val(suminf.sum);
			                            return '<div>' + formatNumber(suminf.sum,'${ass_05005}',1) + '</div>';
			                        }
								},
								render : function(item) {
									item.total_price = formatNumber(
											item.price
													* item.in_amount,
													'${ass_05005}', 1);
									return formatNumber(
											item.total_price, '${ass_05005}', 1);
								},
								width : '100'
							},
							{
								display : '型号',
								name : 'ass_model',
								align : 'left',
								width : '100',
								editor : {
									type : 'text'
								},
								render : function(rowdata, rowindex,
										value) {
									if(rowdata.ass_model == null || rowdata.ass_model == ""){
										rowdata.ass_model = "*";
									}
									return  rowdata.ass_model;
								}
							},
							{
								display : '品牌',
								name : 'ass_brand',
								align : 'left',
								editor : {
									type : 'text'
								},
								width : '100'
							},
							{
								display : '生产厂家',
								name : 'fac_id',
								textField : 'fac_name',
								editor : {
									type : 'select',
									valueField : 'id',
									textField : 'text',
									url : '../queryHosFacDictNo.do?isCheck=false',
									keySupport : true,
									autocomplete : true/* ,
									onSuccess : function(data) {
										if (initvalue != undefined
												&& initvalue != "") {
											this.setValue(initvalue);
											initvalue = "";
										}
									} */
								},
								align : 'left',
								width : '190'
							},
							{
								display : '资产用途',
								name : 'ass_purpose',
								textField:'ass_purpose_name',
								 editor : {
									type : 'select',
									valueField : 'id',
									textField : 'text',
									url : '../queryAssUsageDict.do?isCheck=false',
									keySupport : true,
									autocomplete : true,
									onSuccess : function(data) {
										if (initvalue != undefined
												&& initvalue != "") {
											this.setValue(initvalue);
											initvalue = "";
										}
									} 
								},
								align : 'left',
								width : '60'
							},
							{
								display : '注册证号',
								name : 'reg_no',
								align : 'left',
								editor : {
									type : 'text'
								},
								width : '100'
							}, {
								display : '备注',
								name : 'note',
								editor : {
									type : 'text'
								},
								align : 'left',
								width : '100'
							} ],
					dataAction : 'server',
					dataType : 'server',
					url:'queryAssAcceptDetailIn.do?isCheck=false',
					usePager : false,
					width : '100%',
					height : '50%',
					checkbox : true,
					enabledEdit : true,
					alternatingRow : true,
					onBeforeEdit : f_onBeforeEdit,
					onBeforeSubmitEdit : f_onBeforeSubmitEdit,
					onAfterEdit : f_onAfterEdit,
					isScroll : true,
					rownumbers : true,
					delayLoad : true,//初始化明细数据
					selectRowButtonOnly : true,//heightDiff: -10,
					isChecked: detail_isChecked,
					toolbar : {
						items : [ {
							text : '保存入库',
							id : 'save',
							click : save,
							icon : 'save'
						}, {
							line : true
						}, {
							text : '删除',
							id : 'delete',
							click : remove,
							icon : 'delete'
						}]
					},
					onSelectRow : function(rowdata, rowindex, rowobj){
						var purDetailObj; 
						if(acceptObj[accept_id]){
							purDetailObj = acceptObj[accept_id];
						}else{
							purDetailObj = {};
						}
						purDetailObj[rowdata.accept_detail_id] = rowdata.accept_detail_id;
						acceptObj[accept_id] = purDetailObj;
						if(!grid.isSelected(gridRowData)){
							grid.select(gridRowIndex);
						}
					}, 
					onUnSelectRow : function(rowdata, rowindex, rowobj){
						if(acceptObj[accept_id]){
							var purDetailObj = acceptObj[accept_id];
							delete purDetailObj[rowdata.accept_detail_id];
							if(JSON.stringify(purDetailObj) == "{}"){
								if(grid.isSelected(gridRowData)){
									grid.unselect(gridRowIndex);
								}
							}else{
								acceptObj[accept_id] = purDetailObj;
							}
						}
					}
				});

    }
	
	function detail_isChecked(rowdata){
    	if(detailGridAllChecked == 0){
    		return false;
    	}else if(detailGridAllChecked == 1){
    		return true;
    	}else if(detailGridAllChecked == 2){
	    	if(acceptObj[accept_id]){
		    	var purDetailObj = acceptObj[accept_id];
		    	if(purDetailObj[rowdata.accept_detail_id]){
		    		return true;
		    	}
	    	}
    	}
    	return false;
    }
	
	function openCardUpdate(obj) {
		var vo = obj.split("|");
		var parm = "group_id=" + vo[0] + "&" + "hos_id=" + vo[1] + "&"
				+ " copy_code=" + vo[2] + "&" + "ass_card_no=" + vo[3] + "&"
				+ "ass_nature=03";
		parent.$.ligerDialog.open({
			title: '资产卡片维护',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/ass/asscard/assInCardUpdatePage.do?isCheck=false&'+parm,
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,slide:false,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
	}
	
	
	var rowindex_id = "";
	var column_name = "";
	function f_onBeforeEdit(e) {
		rowindex_id = e.rowindex;
		clicked = 0;
		column_name = e.column.name;
	}
	//选中回充数据
	function f_onSelectRow_detail(data, rowindex, rowobj) {

		selectData = "";
		selectData = data;
		//alert(JSON.stringify(data)); 
		if (column_name == "ass_id") {
			if (selectData != "" || selectData != null) {
				//回充数据 
				//grid.updateCell('apply_emp', 100, e.record);
				grid.updateRow(rowindex_id, {
					ass_code : data.ass_code,
					ass_name : data.ass_name,
					ass_model : data.ass_model,
					ass_spec : data.ass_spec,
					fac_id : data.fac_id,
					fac_name : data.fac_name,
					unit_code:data.ass_unit,
					unit_name:data.ass_unit_name,
					ass_purpose:data.usage_code, 
					ass_purpose_name:data.usage_name,
					ass_brand:data.ass_brand,
					price:data.price,
					reg_no:data.reg_no
				});

			}
		}
		return true;
	}
	function f_onSelectRow(data, rowindex, rowobj) {
		return true;
	}
	// 编辑单元格提交编辑状态之前作判断限制
	function f_onBeforeSubmitEdit(e) {
		return true;
	}
	// 跳转到下一个单元格之前事件
	function f_onAfterEdit(e) {
		$("#in_money").val( e.record.price
				* e.record.in_amount);
		gridManager.updateCell('total_price', e.record.price
				* e.record.in_amount, e.record);
		grid.updateTotalSummary();
	}

	
	function validateGrid() {
		var data = detailGrid.getData();
		var msg = "";
		var targetMap = new HashMap();
		var msgMap = new HashMap();
		var price=0;
		//删除空行

		$.each(data, function(i, v) {
			if (!isnull(v.ass_code)) {
				var key = v.ass_code +"|"+v.ass_spec +"|"+v.ass_model;
				var value = "第" + (i + 1) + "行";

				if (isnull(v.in_amount)) {
					msg += "[入库数量]、";
				}
				//if (isnull(v.price)) {
					//msg += "[单价]";
				//}
				//if (isnull(v.unit_code)) {  
				//str += "第"+(i+1)+"[计量单位]、";  
				//is_flag = false;
				//} 
				if (msg != "") {
					msgMap.put(value + msg + "不能为空或不能为零！\n\r", "");
				}
				if (isnull(targetMap.get(key))) {
					targetMap.put(key, value);
				} else {
					msg = targetMap.get(key) + "与" + value + "重复！\n\r",
					value;
					msgMap.put(targetMap.get(key) + "与" + value + "重复！\n\r",
							value);
				}
			}
		});
		var price=0;
		for(var i=0;i<data.length;i++){
            if(data[i].price!=undefined){
               price+=data[i].price;

             }
        }
        if(price<0){
           $.ligerDialog.warn("入库单价不能为负数");
            return false;
         }
		for(var i=0;i<data.length;i++){
			if(data[i].price!=undefined){
				price+=data[i].price;
				
			}
			
		}
		if(price<0){
			$.ligerDialog.warn("入库单价不能为负数");
			return false;
			
		}
		if (msg != "") {
			$.ligerDialog.warn(msgMap.keySet());
			return false;
		}
		if (msg != "") {
			$.ligerDialog.warn(msgMap.keySet());
			return false;
		}
		return true;
	}
	function remove() {

		var data = detailGrid.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var ParamVo = [];
			var i = 0;
			$(data).each(function() {
				if (isnull(this.group_id)) {
					detailGrid.deleteSelectedRow();
				} else {
					ParamVo.push(this.group_id + "@" + this.hos_id
									+ "@" + this.copy_code + "@"
									+ this.ass_in_no + "@"
									+ this.ass_id.split("@")[0] + "@"
									+ this.ass_no+"@"
									+ this.ass_spec+"@" 
									+ this.ass_model);
				}
				i++;
			});
			if (ParamVo == "") {
				return;
			}
			$.ligerDialog.confirm('确定删除?',function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteAssInDetailGeneral.do",
							{ParamVo : ParamVo.toString()},
							function(responseData) {
								if (responseData.state == "true") {
									$("#in_money").val(responseData.in_money);
									query();
									queryCard("0","0@0","","");
								}
							});
				}
			});
		}
	}


	//键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
		hotkeys('A', save);
		hotkeys('D', remove);

	}
	function is_addRow() {
		setTimeout(function() { //当数据为空时 默认新增一行
			grid.addRow();
		}, 1000);
	} 
	function loadDict() {
		//字典下拉框
		autocomplete("#store_id", "../queryHosStoreDict.do?naturs_code=03&isCheck=false",
				"id", "text", true, true, null, false, null, "300");
		
		autocomplete("#purc_emp",
				"../queryMatStockEmp.do?isCheck=false&", "id",
				"text", true, true, null, null);
		
		$("#store_id").change(function(){
			
			var store_id = liger.get("store_id").getValue().split("@")[0];

			if (store_id == null || store_id == "") {
				store_id = "";
			}
			
			autocomplete("#purc_emp",
					"../queryMatStockEmp.do?isCheck=false&store_id="+store_id, "id",
					"text", true, true, null, true);
		});
		
		 $("#invoice_no").ligerTextBox({width:160});
		 
		 $("#invoice_date").ligerTextBox({width:160});
		 
		//autodate("#invoice_date");
		
		autocomplete("#ven_id", "../queryHosSupDictNo.do?isCheck=false", "id",
				"text", true, true, null, false, null, "400");
		ajaxJsonObjectByUrl("../queryDeptDictInitValue.do?isCheck=false",{},function(data){
			autocomplete("#dept_id", "../queryDeptDict.do?isCheck=false", "id",
					"text", true, true, null, null);
			/* if(data != null && data != ""){
				liger.get("dept_id").setValue(data[0].id);
				liger.get("dept_id").setText(data[0].text);
			} */
		},false);
		//autocomplete("#ass_purpose", "../../queryAssUsageDict.do?isCheck=false",
				//"id", "text", true, true, null, true);
		autocomplete("#proj_id", "../queryAssProjDict.do?isCheck=false","id", "text",true,true,null,false,null,"400");
		autodate("#create_date");
		autocomplete("#ass_naturs", "../queryAssNaturs.do?isCheck=false", "id",
				"text", true, true, null, true, null, "160");
		$('#in_state').ligerComboBox({
			data:[{id:0,text:'未入库'},{id:1,text:'已入库'}],
			valueField: 'id',
            textField: 'text',
			cancelable:true
		});
		
		$("#ass_in_no").ligerTextBox({width : 160});
		$("#store_id").ligerTextBox({width : 160});
			
		$("#ven_id").ligerTextBox({width : 160});
			
		$("#proj_id").ligerTextBox({width : 160});
			
		$("#purc_emp").ligerTextBox({width : 160});
			
		$("#in_money").ligerTextBox({width : 160});
			
		$("#create_date").ligerTextBox({width : 160});
			
		$("#ass_in_no").ligerComboBox({disabled : true,cancelable : false});
		$("#in_money").val(0);
		$("#in_money").ligerComboBox({disabled : true,cancelable : false});
		
	}
</script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>入库单号：</td>
			<td align="left" class="l-table-edit-td"><input name="ass_in_no"
				type="text" id="ass_in_no" disabled="disabled" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>制单日期：</td>
			<td align="left" class="l-table-edit-td"><input
				name="create_date" type="text" id="create_date" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>采&nbsp;&nbsp;购&nbsp;&nbsp;员：</td>
			<td align="left" class="l-table-edit-td"><input name="purc_emp"
				type="text" id="purc_emp" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">项&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;目：</td>
			<td align="left" class="l-table-edit-td"><input
				name="proj_id" type="text" id="proj_id" /></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>仓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;库：</td>
			<td align="left" class="l-table-edit-td"><input name="store_id"
				type="text" id="store_id" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>供&nbsp;&nbsp;应&nbsp;&nbsp;商：</td>
			<td align="left" class="l-table-edit-td"><input name="ven_id"
				type="text" id="ven_id" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;">发票号：</td>
            <td align="left" class="l-table-edit-td"><input name="invoice_no" type="text" id="invoice_no" ltype="text"  /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;">发票日期：</td>
            <td align="left" class="l-table-edit-td"><input name="invoice_date" type="text" id="invoice_date" ltype="text" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="left"></td>
		</tr>
		<tr>
			
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">领用科室：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_id"
				type="text" id="dept_id" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">入库金额：</td>
			<td align="left" class="l-table-edit-td"><input name="in_money"
				type="text" id="in_money" /></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">摘&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;要：</td>
			<td align="left" class="l-table-edit-td" colspan="9"><textarea 
					 cols="70" id="note" name="note" style="border-color: #aecaf0;">由验收单生成</textarea></td>
			<td align="left"></td>
		</tr>
		<tr>
		<td colspan=11><hr width="100%" color="red" ></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td"
						style="padding-left: 20px;"><b><font color="red">*</font></b>资产性质：</td>
			<td align="left" class="l-table-edit-td"><input
				name="ass_naturs" type="text" id="ass_naturs" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td"
						style="padding-left: 20px;">入库状态：</td>
			<td align="left" class="l-table-edit-td"><input
				name="in_state" type="text" id="in_state" /></td>
			<td align="left"></td>
		</tr>
		
	</table>
	<div id="maingrid"></div>
	<div id="detailgrid"></div>
	
</body>
</html>
