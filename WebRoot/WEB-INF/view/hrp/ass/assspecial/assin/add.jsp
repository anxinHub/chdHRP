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

		$("#ass_in_no").ligerTextBox({
			width : 160
		});
		$("#store_id").ligerTextBox({
			width : 160
		});
		$("#ven_id").ligerTextBox({
			width : 160
		});
		$("#proj_id").ligerTextBox({
			width : 160
		});
		$("#purc_emp").ligerTextBox({
			width : 160
		});
		//$("#ass_purpose").ligerTextBox({
			//width : 160
		//});
		$("#in_money").ligerTextBox({
			width : 160
		});
		$("#create_date").ligerTextBox({
			width : 160
		});

		$("#ass_in_no").ligerComboBox({
			disabled : true,
			cancelable : false
		});
		$("#in_money").val(0);
		$("#in_money").ligerComboBox({
			disabled : true,
			cancelable : false
		});
		
		$("#ven_id").change(function(){
			
			var ven_id = liger.get("ven_id").getValue().split("@")[0];
			var ven_no = liger.get("ven_id").getValue().split("@")[1];

			if (ven_no == null || ven_id == null || ven_id == ""
					|| ven_no == "") {
				ven_no = "";
				ven_id = "";
			}
			
			editor.grid.url = '../../queryAssNoDictTable.do?isCheck=false&ass_naturs=02&ven_id='+ven_id+'&ven_no='+ven_no;
		});
	});

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
			pact_code : liger.get("pact_code").getValue(),
			purc_emp : liger.get("purc_emp").getValue(),
			invoice_no:$("#invoice_no").val(), 
			invoice_date:$("#invoice_date").val(),
			bus_type_code : '01',
			note : $("#note").val(),
			//ass_purpose : liger.get("ass_purpose").getValue(),
			create_date : $("#create_date").val(),
			ParamVo : JSON.stringify(data)
		};
		
		if (validateGrid()) {
			ajaxJsonObjectByUrl("saveAssInMainSpecial.do", formPara, function(
					responseData) {
				if (responseData.state == "true") {
					alert
					$("#ass_in_no").val(responseData.ass_in_no);
					$("#in_money").val(responseData.in_money)
					query();
					queryCard("0","0@0","","");
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
			name : 'ass_in_no',
			value : $("#ass_in_no").val() == "" ? "0" : $("#ass_in_no").val()
		});
		grid.loadData(grid.where);
		is_addRow();
	}
	function queryCard(ass_in_no,ass_id,ass_spec,ass_model) {
		cardgrid.options.parms = [];
		cardgrid.options.newPage = 1;
		cardgrid.options.parms.push({
			name : 'ass_in_no',
			value : ass_in_no
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
			name : 'ass_spec',
			value : ass_spec
		});
		cardgrid.options.parms.push({
			name : 'ass_model',
			value : ass_model
		});
		
		cardgrid.loadData(cardgrid.where);
	}
	
	

	function loadHead() {
		
		var ven_id = liger.get("ven_id").getValue().split("@")[0];
		var ven_no = liger.get("ven_id").getValue().split("@")[1];

		if (ven_no == null || ven_id == null || ven_id == ""
				|| ven_no == "") {
			ven_no = "";
			ven_id = "";
		}
		
		editor = {
				type : 'select',
				valueField : 'ass_id_no',
				textField : 'ass_name',
				selectBoxWidth : 800,
				selectBoxHeight : 240,
				grid : {
					columns : [ {
						display : '资产编码',
						name : 'ass_code',
						align : 'left',
						width : 90
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
						align : 'left',
						width : 50
					},{
						display : '资产用途',
						name : 'usage_name',
						align : 'left',
						width : 60
					},{
						display : '单价',
						name : 'price',
						align : 'left'
					},{
						display : '注册证号',
						name : 'reg_no',
						align : 'left'
					},{
						display : '资产ID',
						name : 'ass_id',
						hide : true
					} ],
					switchPageSizeApplyComboBox : false,
					onSelectRow : f_onSelectRow_detail,
					url : '../../queryAssNoDictTable.do?isCheck=false&ass_naturs=02&ven_id='+ven_id+'&ven_no='+ven_no,
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
			};
		
		
		grid = $("#maingrid")
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
										editor : editor,
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
									/* 	render:function(){
											console.log(arguments);
										}, */
										 editor : {
											type : 'select',
											valueField : 'id',
											textField : 'text',
											url : '../../queryHosUnitDict.do?isCheck=false',
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
										editor : {
											type : 'int'
										},
										align : 'left',
										width : '100'
									},
									{
										display : '单价',
										name : 'price',
										editor : {
											type : 'numberbox',
											precision: '${ass_04006}'
										},
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
											url : '../../queryHosFacDictNo.do?isCheck=false',
											keySupport : true,
											autocomplete : true/*  ,
											onSuccess : function(data) {
												if (value != undefined
														&& initvalue != "") {
													this.setValue(value);
													value = "";
												}
											}  */
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
											url : '../../queryAssUsageDict.do?isCheck=false',
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
							url:'queryAssInDetailSpecial.do',
							usePager : false,
							width : '100%',
							height : '97%',
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
									text : '添加字典',
									id : 'addDict',
									click : addDict,
									icon : 'add'
								}, {
									line : true
								},{
									text : '入库确认',
									id : 'card',
									click : initCard,
									icon : 'right'
							     }, {
										line : true
									}, {
										text : '打印模板设置',
										id : 'printSet',
										click : printSet,
										icon : 'setting'
									},{
										line : true
									},
								  	{
										text : '批量打印',
										id : 'print',
										click : printDate,
										icon : 'print'
									},{
										line : true
								 },{
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
								}]
							},
							onCheckRow : function(checked, data, rowid, rowdata) {
								var ass_id = data.ass_id;
								if(ass_id == null || ass_id == ""){
									ass_id = "0@0"
								}
								var ass_in_no = data.ass_in_no;
								if(ass_in_no == null || ass_in_no == ""){
									ass_in_no = "0"
								}
								var ass_spec = data.ass_spec;
								var ass_model = data.ass_model;
								
								queryCard(ass_in_no,ass_id,ass_spec,ass_model);
							}
						});

		gridManager = $("#maingrid").ligerGetGridManager();
		is_addRow();
		
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
					url:'queryAssInCardSpecial.do',
					usePager : false,
					width : '100%',
					height : '97%',
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
						}
						/*, {
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
	
	
	//入库确认
	function initCard(){
		if ($("#ass_in_no").val() == "") {
			$.ligerDialog.error('没有保存的单据不能确认');
			return;
		}
		var ParamVo = [];
		ParamVo.push('${group_id}' + "@" + '${hos_id}' + "@"
				+ '${copy_code}' + "@" + $("#ass_in_no").val());
		
		$.ligerDialog.confirm('确认入库?', function(yes) {
			if (yes) {
				ajaxJsonObjectByUrl("updateConfirmInMainSpecial.do", {
					ParamVo : ParamVo.toString()
				}, function(responseData) {
					if (responseData.state == "true") {
						query();
						parentFrameUse().query();
					}
				});
			}
		});	
		
	}
	
	//打印设置 最新版 
	function printSet(){
		if ($("#ass_in_no").val() == "") {
			$.ligerDialog.error('没有保存的单据不能设置');
			return;
		}
		var useId=0;//统一打印
		if('${ass_05014}'==1){
			//按用户打印
			useId='${user_id }';
		}else if('${ass_05014}'==2){
			//按仓库打印
			if(liger.get("store_id").getValue()==""){
				$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
				return;
			}
			useId=liger.get("store_id").getValue().split("@")[0];
		}
		
		
		officeFormTemplate({template_code:"05014",use_id : useId})
	}
	
	//打印 最新版
	function printDate(){
		if ($("#ass_in_no").val() == "") {
			$.ligerDialog.error('没有保存的单据不能打印');
			return;
		}
		var useId=0;//统一打印
		if('${ass_05014}'==1){
			//按用户打印
			useId='${user_id }';
		}else if('${ass_05014}'==2){
			//按仓库打印
			if(liger.get("store_id").getValue()==""){
				$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
				return;
			}
			useId=liger.get("store_id").getValue().split("@")[0];
		}
		
		var ass_in_nos = $("#ass_in_no").val();
		
		var para={
				template_code:'05014',
				class_name:"com.chd.hrp.ass.serviceImpl.in.AssInMainSpecialServiceImpl", 
				method_name:"queryAssInSpecialByPrintTemlatePrint",
				isSetPrint:false,//是否套打，默认非套打
				isPreview:false,//是否预览，默认直接打印
				ass_in_no:$("#ass_in_no").val(),
				p_num:0,
				use_id:useId
			
		};
		
		ajaxJsonObjectByUrl("queryAssInSpecialState.do?isCheck=false",{ass_in_no:$("#ass_in_no").val(),state:2},function(responseData){
			if (responseData.state == "true") {
				officeFormPrint(para);
				$.post("updateIsPrint.do?isCheck=false",{paramVo:ass_in_nos},function(data){
					
				},"json");
			}
		});
	}
	
	
	function addDict(){
		parent.$.ligerDialog.open({
			title: '资产添加',
			height : 400,
			width : 1000,
			url: 'hrp/ass/assdict/assDictAddPage.do?isCheck=false&ass_naturs=02&ass_type_code=""&ass_type_name=""&ass_type_id=""&manage_depre_amount=',
			modal: false, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		}); 
	}
	
	function openCardUpdate(obj) {
		var vo = obj.split("|");
		var parm = "group_id=" + vo[0] + "&" + "hos_id=" + vo[1] + "&"
				+ " copy_code=" + vo[2] + "&" + "ass_card_no=" + vo[3] + "&"
				+ "ass_nature=02";
		parent.$.ligerDialog.open({
			title: '资产卡片维护',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/ass/asscard/assInCardUpdatePage.do?isCheck=false&'+parm,
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,slide:false,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
	}
	function buildCards(){
		
		var data = gridManager.getData();
		var ass_in_no = $("#ass_in_no").val();
		var price = $("#in_money").val();
		var proj_id = liger.get("proj_id").getValue().split("@")[0];
		var proj_no = liger.get("proj_id").getValue().split("@")[1];
		if (proj_id == null || proj_no == null || proj_no == ""
			|| proj_id == "") {
				proj_no = "";
				proj_id = "";
		}
		var ass_ids = [];
		var ass_nos = [];
		var ass_specs = [];
		var ass_models = [];
		var flag = true;
		
		if(ass_in_no == null || ass_in_no == ""){
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
				ass_specs.push("'"+data[i].ass_spec+"'");
				ass_models.push("'"+data[i].ass_model+"'");
			}
			
		}
		if(flag){
			$.ligerDialog.confirm('生成卡片是否设置资金来源?',function(yes) {
				if(yes){
					$.ligerDialog.open({ 
						url : 'assInMainSpecialSourcePage.do?isCheck=false&ass_in_no='+ass_in_no,
						data:{}, 
						height: 350,
						width: 600, 
						title:'资金来源设置&nbsp;<font color="red">原值：'+price+'</font>',
						modal:true,
						showToggle:false,
						showMax:false,
						showMin: false,
						isResize:true,
						buttons: [ 
							{ text: '确定', onclick: function (item, dialog) { 
								var data = dialog.frame.validateGrid();
								if(data){
									ajaxJsonObjectByUrl(
											"initAssInBatchCardSpecial.do?isCheck=false",
											{
												ass_in_no:ass_in_no,
												ass_ids:ass_ids.toString(),
												ass_nos:ass_nos.toString(),
												ass_specs:ass_specs.toString(),
												ass_models:ass_models.toString(),
												is_source:'1',
												total_price:price,
												store_code : liger.get("store_id").getText().split(" ")[0], 
												source_data:JSON.stringify(data)
											},
											function(responseData) {
												if (responseData.state == "true") {
													dialog.close();
													queryCard("0","0@0","","");
												}
									});
								}
							},cls:'l-dialog-btn-highlight' },
							{ text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] 
					});
				}else{
					ajaxJsonObjectByUrl(
							"initAssInBatchCardSpecial.do?isCheck=false",
							{
								ass_in_no:ass_in_no,
								ass_ids:ass_ids.toString(),
								ass_nos:ass_nos.toString(),
								ass_specs:ass_specs.toString(),
								ass_models:ass_models.toString(),
								store_code : liger.get("store_id").getText().split(" ")[0], 
								is_source:'0'
							},
							function(responseData) {
								if (responseData.state == "true") {
									queryCard("0","0@0","","");
								}
					});
				}
			});
		}
	}
	
	
	function buildCard(){
		var data = gridManager.getCheckedRows();
		if (data.length == 0 || data.length > 1) {
			$.ligerDialog.warn('选择单个资产操作');
			return false;
		} else {
			var ass_id = "0@0";
			var ass_in_no = "0";
			var price = 0; 
			var ass_spec = "";
			var ass_model = "";
			var flag = true;
			$.each(data, function() {
				if (isnull(this.group_id)) {
					$.ligerDialog.warn('所选资产没有保存');
					flag = false;
				}
				ass_id = this.ass_id;
				ass_in_no = this.ass_in_no;
				price = this.price;
				ass_spec = this.ass_spec;
				ass_model = this.ass_model;
			});
			var proj_id = liger.get("proj_id").getValue().split("@")[0];
			var proj_no = liger.get("proj_id").getValue().split("@")[1];
			if (proj_id == null || proj_no == null || proj_no == ""
				|| proj_id == "") {
					proj_no = "";
					proj_id = "";
			}
			
			if(flag){
				$.ligerDialog.confirm('生成卡片是否设置资金来源?',function(yes) {
					if(yes){
						$.ligerDialog.open({ 
							url : 'assInMainSpecialSourcePage.do?isCheck=false&price='+price+'&ass_in_no='+ass_in_no,
							data:{}, 
							height: 350,
							width: 600, 
							title:'资金来源设置&nbsp;<font color="red">原值：'+price+'</font>',
							modal:true,
							showToggle:false,
							showMax:false,
							showMin: false,
							isResize:true,
							buttons: [ 
								{ text: '确定', onclick: function (item, dialog) { 
									var data = dialog.frame.validateGrid();
									if(data){
										ajaxJsonObjectByUrl(
												"initAssInCardSpecial.do",
												{
													ass_in_no:ass_in_no,
													ass_id:ass_id.split("@")[0],
													ass_no:ass_id.split("@")[1],
													is_source:'1',
													source_data:JSON.stringify(data),
													ass_spec:ass_spec,
													store_code : liger.get("store_id").getText().split(" ")[0], 
													ass_model:ass_model
												},
												function(responseData) {
													if (responseData.state == "true") {
														dialog.close();
														queryCard(ass_in_no,ass_id,ass_spec,ass_model);
													}
										});
									}
								},cls:'l-dialog-btn-highlight' },
								{ text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] 
						});
					}else{
						ajaxJsonObjectByUrl(
								"initAssInCardSpecial.do",
								{
									ass_in_no:ass_in_no,
									ass_id:ass_id.split("@")[0],
									ass_no:ass_id.split("@")[1],
									is_source:'0',
									ass_spec:ass_spec,
									store_code : liger.get("store_id").getText().split(" ")[0], 
									ass_model:ass_model
								},
								function(responseData) {
									if (responseData.state == "true") {
										queryCard(ass_in_no,ass_id,ass_spec,ass_model);
									}
						});
					}
				});	
			}
			
		}
	}
	
	function removeCard(){
		
		
		var data = gridManager.getCheckedRows();
		if (data.length == 0 || data.length > 1) {
			$.ligerDialog.warn('选择单个资产操作');
			return false;
		} else {
			var ass_id = "0@0";
			var ass_in_no = "0";
			var flag = true;
			var ass_spec;
			var ass_model;
			$.each(data, function() {
				if (isnull(this.group_id)) {
					$.ligerDialog.warn('所选资产没有保存');
					flag = false;
				}
				ass_id = this.ass_id;
				ass_in_no = this.ass_in_no;
				ass_spec = this.ass_spec;
				ass_model = this.ass_model;
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
													+ this.ass_card_no + "@" + ass_in_no);
							});
					$.ligerDialog
							.confirm(
									'确定删除?',
									function(yes) {
										if (yes) {
											ajaxJsonObjectByUrl(
													"deleteAssInCardSpecial.do",
													{
														ParamVo : ParamVo.toString()
													},
													function(responseData) {
														if (responseData.state == "true") {
															queryCard(ass_in_no,ass_id,ass_spec,ass_model);
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
					unit_code:data.ass_unit, 
					unit_name:data.ass_unit_name,
					ass_brand:data.ass_brand,
					ass_purpose:data.usage_code, 
					ass_purpose_name:data.usage_name,
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
		var data = gridManager.getData();
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
									.push(this.group_id + "@" + this.hos_id
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
			$.ligerDialog
					.confirm(
							'确定删除?',
							function(yes) {
								if (yes) {
									ajaxJsonObjectByUrl(
											"deleteAssInDetailSpecial.do",
											{
												ParamVo : ParamVo.toString()
											},
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
		autocomplete("#store_id", "../../queryHosStoreDict.do?naturs_code=02&isCheck=false",
				"id", "text", true, true, null, false, null, "300");
		
		autocomplete("#purc_emp",
				"../../queryMatStockEmp.do?isCheck=false&", "id",
				"text", true, true, null, null);
		
		$("#store_id").change(function(){
			
			var store_id = liger.get("store_id").getValue().split("@")[0];

			if (store_id == null || store_id == "") {
				store_id = "";
			}
			liger.get("purc_emp").setValue("");
			liger.get("purc_emp").setText("");
			autocomplete("#purc_emp",
					"../../queryMatStockEmp.do?isCheck=false&store_id="+store_id, "id",
					"text", true, true, null, true);
		});
		
		 $("#invoice_no").ligerTextBox({width:160});

		
		autocomplete("#ven_id", "../../queryHosSupDictNo.do?isCheck=false", "id",
				"text", true, true, null, false, null, "400");
		ajaxJsonObjectByUrl("../../queryDeptDictInitValue.do?isCheck=false",{},function(data){
			autocomplete("#dept_id", "../../queryDeptDict.do?isCheck=false", "id",
					"text", true, true, null, null);
			/* if(data != null && data != ""){
				liger.get("dept_id").setValue(data[0].id);
				liger.get("dept_id").setText(data[0].text);
			} */
		},false);
		//autocomplete("#ass_purpose", "../../queryAssUsageDict.do?isCheck=false",
				//"id", "text", true, true, null, true);
		autocomplete("#proj_id", "../../queryAssProjDict.do?isCheck=false","id", "text",true,true,null,false,null,"400");
		autodate("#create_date");
		
		$("#invoice_date").ligerTextBox({width:160});
		
		//autodate("#invoice_date");
		
		autocomplete("#pact_code", "../../queryPactMainFkht.do?isCheck=false","id", "text",true,true,null,false,null,"160");
		
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
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>采&nbsp;&nbsp;购&nbsp;&nbsp;员：</td>
			<td align="left" class="l-table-edit-td"><input name="purc_emp"
				type="text" id="purc_emp" /></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">领用科室：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_id"
				type="text" id="dept_id" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;">发票号：</td>
            <td align="left" class="l-table-edit-td"><input name="invoice_no" type="text" id="invoice_no" ltype="text"  /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;">发票日期：</td>
            <td align="left" class="l-table-edit-td"><input name="invoice_date" type="text" id="invoice_date" ltype="text" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"  /></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">合同：</td>
			<td align="left" class="l-table-edit-td"><input name="pact_code"
				type="text" id="pact_code" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">入库金额：</td>
			<td align="left" class="l-table-edit-td"><input name="in_money"
				type="text" id="in_money" /></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">摘&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;要：</td>
			<td align="left" class="l-table-edit-td" colspan="9"><textarea
					rows="3" cols="70" id="note" name="note" style="border-color: #aecaf0;"></textarea></td>
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
