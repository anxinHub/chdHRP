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
	var dataFormat;
	var grid;
	var gridManager = null;
	var cardgrid;
	var cardgridManager = null;
	var userUpdateStr;
	var editor;
	$(function() {
		$("#layout1").ligerLayout({
			rightWidth : '400',
			//heightDiff: -8,
			//每展开左边树即刷新一次表格，以免结构混乱
			onRightToggle : function() {
				grid._onResize();
			},
			//每调整左边树宽度大小即刷新一次表格，以免结构混乱
			onEndResize : function(a, b) {
				grid._onResize();
			}
		});
		
		loadDict();//加载下拉框
		loadHead(null);
		loadHotkeys();

		if ('${state}' != 0) {//置黑不能点击
			toobarmanage = gridManager.toolbarManager;
			toobarmanage.setDisabled('saveDetail');
			toobarmanage.setDisabled('save');
			toobarmanage.setDisabled('delete');
			toobarmanage.setDisabled('buildCards');
			toobarmanageItem =cardgridManager.toolbarManager;
			toobarmanageItem.setDisabled('buildCard');
			toobarmanageItem.setDisabled('buildCards');
			toobarmanageItem.setDisabled('delete');
	}
	
		$("#allot_in_no").ligerTextBox({
			width : 160,
			disabled : true,
			cancelable : false
		});
		$("#in_store_id").ligerTextBox({
			width : 160
		});
		$("#out_store_id").ligerComboBox({
			width : 160
		});
		$("#out_hos_id").ligerComboBox({
			width : 160
		});
		$("#create_date").ligerTextBox({
			width : 160
		});
		$("#price").ligerTextBox({
			disabled : true,
			cancelable : false,
			width : 160
		});
		$("#add_depre").ligerTextBox({
			disabled : true,
			cancelable : false,
			width : 160
		});
		$("#cur_money").ligerTextBox({
			disabled : true,
			cancelable : false,
			width : 160
		});
		$("#fore_money").ligerTextBox({
			disabled : true,
			cancelable : false,
			width : 160
		});
		query();
	});

	function save() {
		gridManager.endEdit();
		if ($("#create_date").val() == "") {
			$.ligerDialog.error('制单日期不能为空');
			return;
		}
		if (liger.get("in_store_id").getValue() == "") {
			$.ligerDialog.error('调入仓库不能为空');
			return;
		}
		if (liger.get("out_group_id").getValue() == "") {
			$.ligerDialog.error('调出集团不能为空');
			return;
		}
		if (liger.get("out_hos_id").getValue() == "") {
			$.ligerDialog.error('调出医院不能为空');
			return;
		}
		if (liger.get("out_store_id").getValue() == "") {
			$.ligerDialog.error('调出仓库不能为空');
			return;
		}
		if (liger.get("out_store_id").getValue() == liger.get("in_store_id")
				.getValue()) {
			$.ligerDialog.error('调出仓库和调入仓库不能相同');
			return;
		}
		var data = gridManager.getData();
		var num = 0;
		for (var i = 0; i < data.length; i++) {

			if (data[i].ass_code) {
				num++;
			}
		}
		if (!num) {
			$.ligerDialog.error('明细数据不能为空');
			return false;
		}
		var formPara = {
			allot_in_no : $("#allot_in_no").val() == "" ? '0' : $("#allot_in_no").val(),
			in_store_id : liger.get("in_store_id").getValue().split("@")[0],
			in_store_no : liger.get("in_store_id").getValue().split("@")[1],
			out_group_id : liger.get("out_group_id").getValue(),
			out_hos_id : liger.get("out_hos_id").getValue(),
			out_store_id : liger.get("out_store_id").getValue().split("@")[0],
			out_store_no : liger.get("out_store_id").getValue().split("@")[1],
			note : $("#note").val(),
			create_date : $("#create_date").val(),
			ParamVo : JSON.stringify(data)
		};
		if (validateGrid()) {
			ajaxJsonObjectByUrl("saveAssAllotInGeneral.do", formPara, function(
					responseData) {
				if (responseData.state == "true") {
					$("#allot_in_no").val(responseData.allot_in_no);
					$("#price").val(responseData.price);
					$("#add_depre").val(responseData.add_depre);
					$("#cur_money").val(responseData.cur_money);
					$("#fore_money").val(responseData.fore_money);
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
			name : 'allot_in_no',
			value : $("#allot_in_no").val() == "" ? "0" : $("#allot_in_no").val()
		});
		grid.loadData(grid.where);
		is_addRow();
	}
	function queryCard(allot_in_no,ass_id,ass_ori_card_no) {
		cardgrid.options.parms = [];
		cardgrid.options.newPage = 1;
		cardgrid.options.parms.push({
			name : 'ass_in_no',
			value : allot_in_no
		});
		cardgrid.options.parms.push({
			name : 'ass_id',
			value : ass_id.split("@")[0]
		});
		cardgrid.options.parms.push({
			name : 'ass_no',
			value : ass_id.split("@")[1]
		});
		cardgrid.options.parms.push({
			name : 'ass_ori_card_no',
			value : ass_ori_card_no
		});
		cardgrid.loadData(cardgrid.where);
	}
	
	

	function loadHead() {
		grid = $("#maingrid")
				.ligerGrid(
						{
							columns : [
									{
										display : '资产编码',
										name : 'ass_code',
										align : 'left',
										width : '130'
									},
									{
										display : '资产名称',
										name : 'ass_id',
										align : 'left',
										textField : 'ass_name',
										editor : {
											type : 'select',
											valueField : 'ass_id_no',
											textField : 'ass_name',
											selectBoxWidth : 800,
											selectBoxHeight : 240,
											grid : {
												columns : [ {
													display : '资产编码',
													name : 'ass_code',
													align : 'left'
												}, {
													display : '资产名称',
													name : 'ass_name',
													align : 'left'
												}, {
													display : '资产分类',
													name : 'ass_type_name',
													align : 'left'
												}, {
													display : '规格',
													name : 'ass_spec',
													align : 'left'
												}, {
													display : '型号',
													name : 'ass_model',
													align : 'left'
												}, {
													display : '品牌',
													name : 'ass_brand',
													align : 'left'
												},{
													display : '计量单位',
													name : 'ass_unit_name',
													align : 'left'
												},{
													display : '资产ID',
													name : 'ass_id',
													hide : true
												} ],
												switchPageSizeApplyComboBox : false,
												onSelectRow : f_onSelectRow_detail,
												url : '../../queryAssNoDictTable.do?isCheck=false&ass_naturs=03',
												pageSize : 30
											},
											keySupport : true,
											alwayShowInDown : false,
											keySupport : true,
											autocomplete : true,
											onSuccess : function() {
												this.parent("tr").next(
														".l-grid-row").find(
														"td:first").focus();
											}
										},
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
										display : '原始卡片号',
										name : 'ass_ori_card_no',
										align : 'left',
										width : '120',
										editor : {
											type : 'text'
										}
									},
									{
										display : '规格',
										name : 'ass_spec',
										align : 'left',
										width : '100',
										editor : {
											type : 'text'
										}
									},
									{
										display : '型号',
										name : 'ass_model',
										align : 'left',
										width : '100',
										editor : {
											type : 'text'
										}
									},
									{
										display : '品牌',
										name : 'ass_brand',
										align : 'left',
										width : '100',
										editor : {
											type : 'text'
										}
									},
									{
										display : '生产厂家',
										name : 'fac_id',
										textField : 'fac_name',
										editor : {
											type : 'select',
											valueField : 'id',
											textField : 'text',
											url : '../../queryHosFacDict.do?isCheck=false',
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
										width : '190'
									},
									{
										display : '资产原值',
										name : 'price',
										editor : {
											type : 'numberbox'
										},
										align : 'right',
										render : function(item) {
											return formatNumber(
													item.price, '${ass_05005}', 1);
										},
										width : '100'

									},
									{
										display : '累计折旧',
										name : 'add_depre',
										editor : {
											type : 'float',
											precision : 2
										},
										align : 'right',
										render : function(item) {
											return formatNumber(
													item.add_depre, '${ass_05005}', 1);
										},
										width : '100'
									},
									{
										display : '累计折旧月份',
										name : 'add_depre_month',
										editor : {
											type : 'int'
										},
										align : 'left',
										width : '100'
									},
									{
										display : '资产净值',
										name : 'cur_money',
										align : 'right',
										render : function(item) {
											return formatNumber(
													item.cur_money, '${ass_05006}', 1);
										},
										width : '100'
									},
									{
										display : '预留残值',
										name : 'fore_money',
										editor : {
											type : 'float',
											precision : 2
										},
										align : 'right',
										render : function(item) {
											return formatNumber(
													item.fore_money, '${ass_05006}', 1);
										},
										width : '100'
									}, {
										display : '备注',
										name : 'note',
										editor : {
											type : 'text'
										},
										align : 'left',
										width : '150'
									} ],
							dataAction : 'server',
							dataType : 'server',
							url:'queryAssAllotInDetailGeneral.do',
							usePager : false,
							width : '100%',
							height : '100%',
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
							toolbar : {
								items : [ {
									text : '保存',
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
								}, {
									line : true
								}, {
									text : '关闭',
									id : 'close',
									click : this_close,
									icon : 'candle'
								},{
						    		text : '打印模板设置',
						    		id : 'printSet',
						    		click : printSet,
						    		icon : 'settings'
						     },{
						    		text : '批量打印',
						    		id : 'print',
						    		click : printDate,
						    		icon : 'print'
						    	}]
							},
							onCheckRow : function(checked, data, rowid, rowdata) {
								var ass_id = data.ass_id;
								if(ass_id == null || ass_id == ""){
									ass_id = "0@0"
								}
								var allot_in_no = data.allot_in_no;
								if(allot_in_no == null || allot_in_no == ""){
									allot_in_no = "0"
								}
								var ass_ori_card_no = data.ass_ori_card_no;
								if(ass_ori_card_no == null || ass_ori_card_no == ""){
									ass_ori_card_no = "0"
								}
								queryCard(allot_in_no,ass_id,ass_ori_card_no);
							}
						});

		gridManager = $("#maingrid").ligerGetGridManager();
		
		cardgrid = $("#cardgrid")
		.ligerGrid(
				{
					columns : [
							{
								display : '卡片编码',
								name : 'ass_card_no',
								align : 'left',
								render : function(rowdata, rowindex,
										value) {
									return "<a href=javascript:openCardUpdate('"+rowdata.group_id + "|" + rowdata.hos_id
									+ "|" + rowdata.copy_code + "|"
									+ rowdata.ass_card_no  +"')>"+rowdata.ass_card_no+"</a>";
								}
							},
							{
								display : '状态',
								name : 'use_state_name',
								align : 'left'
							}
							 ],
					dataAction : 'server',
					dataType : 'server',
					url:'queryAssAllotInCardGeneral.do',
					usePager : false,
					width : '100%',
					height : '100%',
					checkbox : true,
					isScroll : true,
					rownumbers : true,
					delayLoad : true,//初始化明细数据
					selectRowButtonOnly : true,//heightDiff: -10,
					toolbar : {
						items : [ {
							text : '生成',
							id : 'buildCard',
							click : buildCard,
							icon : 'extend'
						},{
							line : true
						}, {
							text : '批量生成',
							id : 'buildCards',
							click : buildCards,
							icon : 'extend'
						}, {
							line : true
						}, {
							text : '删除',
							id : 'delete',
							click : removeCard,
							icon : 'delete'
						}/*, {
							line : true
						}, {
							text : '条码打印',
							id : 'printBarcode',
							click : printBarcode,
							icon : 'print'
						}*/]
					}
				});
		cardgridManager = $("#cardgrid").ligerGetGridManager();
		
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
	function buildCard(){
		
		if ($("#create_date").val() == "") {
			$.ligerDialog.error('制单日期不能为空');
			return;
		}
		if (liger.get("in_store_id").getValue() == "") {
			$.ligerDialog.error('调入仓库不能为空');
			return;
		}
		if (liger.get("out_group_id").getValue() == "") {
			$.ligerDialog.error('调出集团不能为空');
			return;
		}
		if (liger.get("out_hos_id").getValue() == "") {
			$.ligerDialog.error('调出医院不能为空');
			return;
		}
		if (liger.get("out_store_id").getValue() == "") {
			$.ligerDialog.error('调出仓库不能为空');
			return;
		}
		
		var data = gridManager.getCheckedRows();
		if (data.length == 0 || data.length > 1) {
			$.ligerDialog.warn('选择单个资产操作');
			return false;
		} else {
			var ass_id = "0@0";
			var allot_in_no = "0";
			var ass_ori_card_no;
			var flag = true;
			$.each(data, function() {
				if (isnull(this.group_id)) {
					$.ligerDialog.warn('所选资产没有保存');
					flag = false;
				}
				ass_id = this.ass_id;
				allot_in_no = this.allot_in_no;
				ass_ori_card_no = this.ass_ori_card_no;
			});
			if(flag){
				
				ajaxJsonObjectByUrl(
						"initAssAllotInCardGeneral.do",
						{
							allot_in_no:allot_in_no,
							ass_id:ass_id.split("@")[0],
							ass_no:ass_id.split("@")[1],
							ass_ori_card_no : ass_ori_card_no,
							is_import:'${is_import}',
							out_group_id:liger.get("out_group_id").getValue(),
							out_hos_id:liger.get("out_hos_id").getValue(),
							in_store_id:liger.get("in_store_id").getValue().split("@")[0],
							in_store_no:liger.get("in_store_id").getValue().split("@")[1]
						},
						function(responseData) {
							if (responseData.state == "true") {
								queryCard(allot_in_no,ass_id);
							}
						});
				
				
				
			}
			
		}
	}
	
	function buildCards(){
		$.ligerDialog.confirm('确定要把所有资产生成卡片?',function(yes) {
			if(yes){
				var data = gridManager.getData();
				var allot_in_no = $("#allot_in_no").val();
				var ass_ids = [];
				var ass_nos = [];
				var ass_ori_card_nos = [];
				var flag = true;
				
				if(allot_in_no == null || allot_in_no == ""){
					$.ligerDialog.warn('单据没有保存');
					flag = false;
				}
				
				for (var i = 0; i < data.length;i++){
					if(!isnull(data[i].ass_id)){
						if (isnull(data[i].group_id)) {
							$.ligerDialog.warn('资产没有保存');
							flag = false;
						}
						ass_ids.push(data[i].ass_id.split("@")[0]);
						ass_nos.push(data[i].ass_id.split("@")[1]);
						ass_ori_card_nos.push("'"+data[i].ass_ori_card_no+"'");
					}
				}
				if(flag){
					ajaxJsonObjectByUrl(
							"initAssAllotInBatchCardGeneral.do?isCheck=false",
							{
								allot_in_no:allot_in_no,
								is_import:'${is_import}',
								out_group_id:liger.get("out_group_id").getValue(),
								out_hos_id:liger.get("out_hos_id").getValue(),
								in_store_id:liger.get("in_store_id").getValue().split("@")[0],
								in_store_no:liger.get("in_store_id").getValue().split("@")[1],
								ass_ids:ass_ids.toString(),
								ass_nos:ass_nos.toString(),
								ass_ori_card_nos:ass_ori_card_nos.toString()
							},
							function(responseData) {
								if (responseData.state == "true") {
									queryCard("0","0@0");
								}
							});
				}
			}
		});
				
	}
	
	function removeCard(){
		
		
		var data = gridManager.getCheckedRows();
		if (data.length == 0 || data.length > 1) {
			$.ligerDialog.warn('选择单个资产操作');
			return false;
		} else {
			var ass_id = "0@0";
			var allot_in_no = "0";
			var flag = true;
			$.each(data, function() {
				if (isnull(this.group_id)) {
					$.ligerDialog.warn('所选资产没有保存');
					flag = false;
				}
				ass_id = this.ass_id;
				allot_in_no = this.allot_in_no;
			});
			
			if (flag) {
				var data = cardgridManager.getCheckedRows();
				if (data.length == 0) {
					$.ligerDialog.error('请选择行');
				} else {
					var ParamVo = [];
					$(data).each(
							function() {
									ParamVo
											.push(this.group_id + "@" + this.hos_id
													+ "@" + this.copy_code + "@"
													+ this.ass_card_no + "@" + allot_in_no);
							});
					$.ligerDialog
							.confirm(
									'确定删除?',
									function(yes) {
										if (yes) {
											ajaxJsonObjectByUrl(
													"deleteAssAllotInCardGeneral.do",
													{
														ParamVo : ParamVo.toString()
													},
													function(responseData) {
														if (responseData.state == "true") {
															queryCard(allot_in_no,ass_id);
														}
													});
										}
									});
				}
			}
		}
		
	}
	
	function printBarcode(){
		
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
					ass_brand:data.ass_brand
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
		var price = e.record.price == null || e.record.price == ""?0:e.record.price;
		var add_depre = e.record.add_depre == null || e.record.add_depre == ""?0:e.record.add_depre;
		var fore_money = e.record.fore_money == null || e.record.fore_money == ""?0:e.record.fore_money;
		gridManager.updateCell('cur_money', price
				- add_depre - fore_money, e.record);
		grid.updateTotalSummary();
	}
	function initCard(){
		
	}
	
	function validateGrid() {
		var data = gridManager.getData();
		var msg = "";
		var targetMap = new HashMap();
		var msgMap = new HashMap();
		//删除空行

		$.each(data, function(i, v) {
			if (!isnull(v.ass_code)) {
				var key = v.ass_code + "|" +v.ass_ori_card_no;
				var value = "第" + (i + 1) + "行";

				if (v.ass_ori_card_no == '@' || isnull(v.ass_ori_card_no)) {
					msg += "[原始卡片号]、";
				}
				if (isnull(v.price) ||  v.price < 0) {
					msg += "[原值]";
				}
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

		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var ParamVo = [];
			var i = 0;
			$(data).each(
					function() {
						if (isnull(this.group_id)) {
							gridManager.deleteSelectedRow();
						} else {
							ParamVo
							.push(this.group_id + "@" 
									+ this.hos_id+ "@" 
									+ this.copy_code + "@"
									+ this.allot_in_no + "@"
									+ this.ass_id.split("@")[0] + "@"
									+ this.ass_no +"@"
									+ this.ass_ori_card_no);
						}
						i++;
					});
			if (ParamVo == "") {
				return;
			}
			$.ligerDialog
					.confirm(
							'确定删除?',
							function(yes) {
								if (yes) {
									ajaxJsonObjectByUrl(
											"deleteAssAllotInDetailGeneral.do",
											{
												ParamVo : ParamVo.toString()
											},
											function(responseData) {
												if (responseData.state == "true") {
													$("#price").val(responseData.price);
													$("#add_depre").val(responseData.add_depre);
													$("#cur_money").val(responseData.cur_money);
													$("#fore_money").val(responseData.fore_money);
													query();
													queryCard("0","0@0");
												}
											});
								}
							});
		}
	}

	//打印设置 最新版 
	function printSet(){
		
		var useId=0;//统一打印
		if('${ass_05028}'==1){
			//按用户打印
			useId='${user_id }';
		}else if('${ass_05028}'==2){
			//按仓库打印
			if(liger.get("out_store_id").getValue()==""){
				$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
				return;
			}
			useId=liger.get("out_store_id").getValue().split("@")[0];
		}
		
		
		officeFormTemplate({template_code:"0502801",use_id : useId})
	}
	
	//打印 最新版
	function printDate(){
		var useId=0;//统一打印
		if('${ass_05028}'==1){
			//按用户打印
			useId='${user_id }';
		}else if('${ass_05028}'==2){
			//按仓库打印
			if(liger.get("out_store_id").getValue()==""){
				$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
				return;
			}
			useId=liger.get("out_store_id").getValue().split("@")[0];
		}
		
		var para={
				template_code:'0502801',
				class_name:"com.chd.hrp.ass.serviceImpl.allot.in.AssAllotInGeneralServiceImpl", 
				method_name:"queryAssAllotInGeneralByPrintTemlatePrint",
				isSetPrint:false,//是否套打，默认非套打
				isPreview:false,//是否预览，默认直接打印
				allot_in_no:$("#allot_in_no").val(),
				p_num:0,
				use_id:useId
			
		};
		
		ajaxJsonObjectByUrl("queryAssAllotInGeneralState.do?isCheck=false",{allot_in_no:$("#allot_in_no").val(),state:2},function(responseData){
			if (responseData.state == "true") {
				officeFormPrint(para);
			}
		});
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
		autocomplete("#in_store_id", "../../queryHosStoreDict.do?naturs_code=03&isCheck=false","id", "text",true,true,null,false,null,"300");
		liger.get("in_store_id").setValue("${in_store_id}@${in_store_no}");
		liger.get("in_store_id").setText("${in_store_code} ${in_store_name}");
		autocomplete("#out_group_id", "../../queryGroupDict.do?isCheck=false","id", "text",true,true,null,false,null,"160");
		liger.get("out_group_id").setValue("${out_group_id}");
		liger.get("out_group_id").setText("${out_group_name}");
		autocomplete("#out_hos_id", "../../null.do?isCheck=false","id", "text",true,true,null,false,null,"160");
		liger.get("out_hos_id").setValue("${out_hos_id}");
		liger.get("out_hos_id").setText("${out_hos_name}");
		autocomplete("#out_store_id", "../../null.do?isCheck=false","id", "text",true,true,null,false,null,"160");
		liger.get("out_store_id").setValue("${out_store_id}@${out_store_no}");
		liger.get("out_store_id").setText("${out_store_code} ${out_store_name}");
		$("#out_group_id").change(function(){
			autocomplete("#out_hos_id", "../../queryHosInfoDict.do?isCheck=false","id", "text",true,true,{group_id:liger.get("out_group_id").getValue()},false,null,"160");
			$("#out_hos_id").change(function(){
				autocomplete("#out_store_id", "../../queryHosStoreDict.do?naturs_code=03&isCheck=false","id", "text",true,true,{group_id:liger.get("out_group_id").getValue(),hos_id:liger.get("out_hos_id").getValue()},false,null,"160");
				liger.get("out_store_id").setValue("${out_store_id}@${out_store_no}");
				liger.get("out_store_id").setText("${out_store_code} ${out_store_name}");
			});
		});
		
	}
</script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>调剂单号：</td>
			<td align="left" class="l-table-edit-td"><input name="allot_in_no"
				type="text" id="allot_in_no" disabled="disabled" value="${allot_in_no }" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>制单日期：</td>
			<td align="left" class="l-table-edit-td"><input
				name="create_date" type="text" id="create_date" class="Wdate" value="${create_date }"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>调入仓库：</td>
			<td align="left" class="l-table-edit-td"><input name="in_store_id"
				type="text" id="in_store_id" /></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right"  class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>调出集团：</td>
			<td align="left"  class="l-table-edit-td"><input name="out_group_id"
				type="text" id="out_group_id" 
				 /></td>
			<td align="left"></td>	 	
			<td align="right"  class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>调出单位：</td>
			<td align="left"  class="l-table-edit-td"><input name="out_hos_id"
				type="text" id="out_hos_id" 
				 /></td>
			<td align="left"></td>	 
			<td align="right"  class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>调出仓库：</td>
			<td align="left"  class="l-table-edit-td"><input name="out_store_id"
				type="text" id="out_store_id" 
				 /></td>
			<td align="left"></td>	 
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">资产原值：</td>
			<td align="left" class="l-table-edit-td"><input name="price" value="${price }"
				type="text" id="price" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">累计折旧：</td>
			<td align="left" class="l-table-edit-td"><input name="add_depre" value="${add_depre }"
				type="text" id="add_depre" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">资产净值：</td>
			<td align="left" class="l-table-edit-td"><input name="cur_money" value="${cur_money }"
				type="text" id="cur_money" /></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">预留残值：</td>
			<td align="left" class="l-table-edit-td"><input name="fore_money" value="${fore_money }"
				type="text" id="fore_money" /></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">摘&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;要：</td>
			<td align="left" class="l-table-edit-td" colspan="9"><textarea
					rows="3" cols="70" id="note" name="note">${note }</textarea></td>
			<td align="left"></td>

		</tr>
	</table>
	<div id="layout1">
		<div position="center" title="资产列表">
			<div>
				<div id="maingrid"></div>
			</div>
		</div>
		<div position="right" title="卡片列表">
			<div>
				<div id="cardgrid"></div>
			</div>
		</div>
	</div>

</body>
</html>
