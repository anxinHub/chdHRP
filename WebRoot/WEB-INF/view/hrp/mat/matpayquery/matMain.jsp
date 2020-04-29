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
    var query_type;
	var isUseAffiStore = '${p04044 }' == 1 ? true : false;
 
	var renderFunc = {

		amount_money : function(value) {//金额
			return formatNumber(value,
					'${p04005 }', 1);
		},

		ra_state : function(value) {
			if (value > 0) {
				return "是";
			} else {
				return "否";
			}
		},
		
	};

	$(function() {
		loadDict()//加载下拉框
		query_type = $("#query_type input:radio:checked").val();
		//加载数据
		loadHead(null);	
		loadHotkeys();
		//showDetail();
		//query();
	});
	//查询
	function query() {

		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		
		if(!$("#begin_confirm_date").val()){
			$.ligerDialog.warn('开始日期不能为空！ ');
			return;
		}
		grid.options.parms.push({
			name : 'state',
			value : liger.get("state").getValue() == null ? "" : liger.get(
					"state").getValue()
		});
		grid.options.parms.push({
			name : 'begin_confirm_date',
			value : $("#begin_confirm_date").val()
		});
		grid.options.parms.push({
			name : 'end_confirm_date',
			value : $("#end_confirm_date").val()
		});
		grid.options.parms.push({
			name : 'store_id',
			value : liger.get("store_code").getValue() == null ? "" : liger
					.get("store_code").getValue().split(",")[0]
		});
		grid.options.parms.push({
			name : 'sup_id',
			value : liger.get("sup_code").getValue() == null ? "" : liger.get(
					"sup_code").getValue().split(",")[0]
		});
		grid.options.parms.push({
			name : 'pay_code',
			value : liger.get("pay_code").getValue() 
		});
		grid.options.parms.push({
			name : 'in_no',
			value : $("#in_no").val()
		});
		grid.options.parms.push({
			name : 'invoice_no',
			value : $("#invoice_no").val()
		});
		grid.options.parms.push({
			name : 'not_invoice',
			value : $("#not_invoice").prop("checked") ? 1 : 0
		});

		grid.options.parms.push({
			name : 'brief',
			value : $("#brief").val()
		});

		var reg = new RegExp("^\\d+(\\.\\d+)?$");
		var begin_amount_money = $.trim($("#begin_amount_money").val());
		var end_amount_money = $.trim($("#end_amount_money").val());

		if (begin_amount_money == '' && end_amount_money != '') {
			$.ligerDialog.warn('金额开始范围不能为空 ')
			return;
		}

		if (begin_amount_money != '' && end_amount_money == '') {
			$.ligerDialog.warn('金额结束范围不能为空 ')
			return;
		}

		if (begin_amount_money != '' && end_amount_money != '') {
			if (!reg.test(begin_amount_money)) {
				$.ligerDialog.warn('金额开始范围格式错误 ');
				return;
			}

			if (!reg.test(end_amount_money)) {
				$.ligerDialog.warn('金额结束范围格式错误 ');
				return;
			}
		}

		//2017-06-05 台州 增加金额范围查询条件
		grid.options.parms.push({
			name : 'begin_amount_money',
			value : begin_amount_money
		});
		grid.options.parms.push({
			name : 'end_amount_money',
			value : end_amount_money
		});
		grid.options.parms.push({
			name : 'query_type',
			value : query_type
		});
		
		if (query_type == 1) {
			// 	grid.options.parms.push({name:'inv_code',value:liger.get("inv_code").getText().split(" ")[0]});
			grid.options.parms.push({
				name : 'inv_code',
				value : $("#inv_code").val()
			});
			grid.options.parms.push({
				name : 'batch_no',
				value : $("#batch_no").val()
			});
		}
		//加载查询条件
		grid.loadData(grid.where);
	}

	function loadHead() {
		if (query_type == "1") {
			grid = $("#maingrid")
					.ligerGrid(
							{
								columns : [
										{
											display : '入库单号',
											name : 'in_no',
											align : 'left',
											width : '150',
											render : function(rowdata,
													rowindex, value) {
												if (value == '合计') {
													return value;
												}
												return '<a href=javascript:update_open("'
														+ rowdata.group_id
														+ ','
														+ rowdata.hos_id
														+ ','
														+ rowdata.copy_code
														+ ','
														+ rowdata.in_id
														+ '")>'
														+ rowdata.in_no
														+ '</a>';

											}
										},{
											display : '供应商',
											name : 'sup_name',
											align : 'left',
											width : '200'
										}, 
										{
											display : '材料编码',
											name : 'inv_code',
											align : 'left',
											width : '120'
										},
										{
											display : '材料名称',
											name : 'inv_name',
											align : 'left',
											width : '120'
										},
										{
											display : '计量单位',
											name : 'unit_name',
											align : 'left',
											width : '60'
										},
										{
											display : '规格型号',
											name : 'inv_model',
											align : 'left',
											width : '120'
										},
										{
											display : '单价',
											name : 'price',
											align : 'right',
											width : '80',
											render : function(rowdata,
													rowindex, value) {
												return formatNumber(
														value,
														'${p04006 }',
														1);
											}
										},
										{
											display : '数量',
											name : 'amount',
											align : 'right',
											width : '80'
										},
										{
											display : '金额',
											name : 'amount_money',
											align : 'right',
											width : '100',
											render : function(rowdata,
													rowindex, value) {
												return formatNumber(
														rowdata.amount_money == null ? 0
																: rowdata.amount_money,
														'${p04005 }',
														1);
											}
										},{
											display : '发票金额',name : 'bill_money',align : 'right',width : '100',
											render : function(rowdata,rowindex, value) {
												return formatNumber(
														rowdata.bill_money == null ? 0: rowdata.bill_money,
														'${p04005 }',1);
											}
										},{
											display : '已付金额',name : 'pay_money',align : 'right',width : '100',
											render : function(rowdata,rowindex, value) {
												return formatNumber(
														rowdata.pay_money == null ? 0: rowdata.pay_money,
														'${p04005 }',1);
											}
										},
										{
											display : '批号',
											name : 'batch_no',
											align : 'left',
											width : '80'
										}, {
											display : '条形码',
											name : 'bar_code',
											align : 'left',
											width : '80'
										}, {
											display : '发票号',
											name : 'bill_no',
											align : 'left',
											width : '80'
										}, {
											display : '发票备注',
											name : 'bill_note',
											align : 'left',
											width : '120'
										}, {
											display : '仓库',
											name : 'store_name',
											align : 'left',
											width : '150'
										},{
											display : '状态',
											name : 'state_name',
											align : 'left',
											width : '60'
										}],
								dataAction : 'server',
								dataType : 'server',
								usePager : true,
								url : 'queryMatStorageIn.do?isCheck=true',
								width : '100%',
								height : '100%',
								checkbox : false,
								rownumbers : true,
								delayLoad : true,//初始化不加载，默认false
								checkBoxDisplay : isCheckDisplay,
								selectRowButtonOnly : true,//heightDiff: -10,
								onsuccess : function() {

								},
								toolbar : {
									items : [ {
										text : '查询(<u>Q</u>)',id : 'search',click : query,icon : 'search'
									}, {
										line : true
									}, {
										text : '打印',id : 'print',click : print,icon : 'print'
									}, {
										line : true
									} ]
								},
								onDblClickRow : function(rowdata, rowindex,
										value) {
									if (rowdata.in_id == null) {
										$.ligerDialog.warn('请选择数据 ');
										return;
									}
									update_open(rowdata.group_id + ","
											+ rowdata.hos_id + ","
											+ rowdata.copy_code + ","
											+ rowdata.in_id + ","
											+ rowdata.in_no);
								}
							});
		}else if (query_type == "2"){
			grid = $("#maingrid")
			.ligerGrid(
					{
						columns : [
								{
									display : '供应商',
									name : 'sup_name',
									align : 'left',
									width : '200'
								}, {
									display : '期初金额',
									name : 'begin_money',
									align : 'right',
									width : '100',
									render : function(rowdata,
											rowindex, value) {
										return formatNumber(
												rowdata.amount_money == null ? 0: rowdata.amount_money,
												'${p04005 }',1);
									}
								},{
									display : '入库金额',
									name : 'amount_money',
									align : 'right',
									width : '100',
									render : function(rowdata,
											rowindex, value) {
										return formatNumber(
												rowdata.amount_money == null ? 0: rowdata.amount_money,
												'${p04005 }',1);
									}
								},{
									display : '发票金额',name : 'bill_money',align : 'right',width : '100',
									render : function(rowdata,rowindex, value) {
										return formatNumber(
												rowdata.bill_money == null ? 0: rowdata.bill_money,
												'${p04005 }',1);
									}
								},{
									display : '已付金额',name : 'pay_money',align : 'right',width : '100',
									render : function(rowdata,rowindex, value) {
										return formatNumber(
												rowdata.pay_money == null ? 0: rowdata.pay_money,
												'${p04005 }',1);
									}
								},{
									display : '应付金额',name : 'un_money',align : 'right',width : '100',
									render : function(rowdata,rowindex, value) {
										return formatNumber(
												rowdata.un_money == null ? 0: rowdata.un_money,
												'${p04005 }',1);
									}
								}],
						dataAction : 'server',
						dataType : 'server',
						usePager : true,
						url : 'queryMatStorageIn.do?isCheck=true',
						width : '100%',
						height : '100%',
						checkbox : true,
						rownumbers : true,
						delayLoad : true,//初始化不加载，默认false
						checkBoxDisplay : isCheckDisplay,
						selectRowButtonOnly : true,//heightDiff: -10,
						onsuccess : function() {

						},
						toolbar : {
							items : [ {
								text : '查询(<u>Q</u>)',id : 'search',click : query,icon : 'search'
							}, {
								line : true
							}, {
								text : '打印',id : 'print',click : print,icon : 'print'
							}, {
								line : true
							} ]
						},
						
					});
		} else{
			grid = $("#maingrid")
					.ligerGrid(
							{
								columns : [
										{
											display : '入库单号',
											name : 'in_no',
											align : 'left',
											width : '150',
											render : function(rowdata,
													rowindex, value) {
												if (value == '合计') {
													return value;
												} else {
													return '<a href=javascript:update_open("'
															+ rowdata.group_id
															+ ','
															+ rowdata.hos_id
															+ ','
															+ rowdata.copy_code
															+ ','
															+ rowdata.in_id
															+ '")>'
															+ rowdata.in_no
															+ '</a>';
												}
											}
										},{
											display : '供应商',
											name : 'sup_name',
											align : 'left',
											width : '200'
										},{
											display : '支付方式',
											name : 'pay_name',
											align : 'left',
											width : '80'
										}, 
										{
											display : '入库金额',
											name : 'amount_money',
											align : 'right',
											width : '100',
											render : function(rowdata,
													rowindex, value) {
												return formatNumber(
														rowdata.amount_money == null ? 0
																: rowdata.amount_money,
														'${p04005 }',
														1);
											}
										},{
											display : '发票金额',name : 'bill_money',align : 'right',width : '100',
											render : function(rowdata,rowindex, value) {
												return formatNumber(
														rowdata.bill_money == null ? 0: rowdata.bill_money,
														'${p04005 }',1);
											}
										},{
											display : '已付金额',name : 'pay_money',align : 'right',width : '100',
											render : function(rowdata,rowindex, value) {
												return formatNumber(
														rowdata.pay_money == null ? 0: rowdata.pay_money,
														'${p04005 }',1);
											}
										},{
											display : '应付金额',name : 'un_money',align : 'right',width : '100',
											render : function(rowdata,rowindex, value) {
												return formatNumber(
														rowdata.un_money == null ? 0: rowdata.un_money,
														'${p04005 }',1);
											}
										},{
											display : '未付金额',name : 'de_money',align : 'right',width : '100',
											render : function(rowdata,rowindex, value) {
												return formatNumber(
														rowdata.de_money == null ? 0: rowdata.de_money,
														'${p04005 }',1);
											}
										},
										{
											display : '状态',
											name : 'state_name',
											align : 'left',
											width : '60'
										},
										{
											display : '发票号',
											name : 'bill_no',
											align : 'left',
											width : '80'
										}, {
											display : '发票备注',
											name : 'bill_note',
											align : 'left',
											width : '120'
										},{
											display : '采购员',
											name : 'stocker_name',
											align : 'left',
											width : '60'
										},{
											display : '制单人',
											name : 'maker_name',
											align : 'left',
											width : '60'
										},{
											display : '确认人',
											name : 'confirmer_name',
											align : 'left',
											width : '60'
										}, {
											display : '入库日期',
											name : 'confirm_date',
											align : 'left',
											width : '90',
											formatter:'yyyy-MM-dd'
										}, {
											display : '仓库',
											name : 'store_name',
											align : 'left',
											width : '150'
										}],
								dataAction : 'server',
								dataType : 'server',
								usePager : true,
								url : 'queryMatStorageIn.do?isCheck=true',
								width : '100%',
								height : '100%',
								checkbox : true,
								rownumbers : true,
								delayLoad : true,//初始化不加载，默认false
								checkBoxDisplay : isCheckDisplay,
								selectRowButtonOnly : true,//heightDiff: -10,
								onsuccess : function() {

									//is_addRow();
								},
								toolbar : {
									items : [ {
										text : '查询(<u>Q</u>)',id : 'search',click : query,icon : 'search'
									}, {
										line : true
									}, {
										text : '打印',id : 'print',click : print,icon : 'print'
									}, {
										line : true
									} ]
								},
								onDblClickRow : function(rowdata, rowindex,
										value) {
									if (rowdata.in_id == null) {
										$.ligerDialog.warn('请选择数据 ');
										return;
									}
									update_open(rowdata.group_id + ","
											+ rowdata.hos_id + ","
											+ rowdata.copy_code + ","
											+ rowdata.in_id + ","
											+ rowdata.in_no);
								}
							});
		}

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	//是否显示复选框
	function isCheckDisplay(rowdata) {
		if (rowdata.in_id == null)
			return false;
		return true;
	}

	//键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('P', print);
	}

 	//打开修改页面
	function update_open(obj) {
		var vo = obj.split(",");
		var paras = "group_id=" + vo[0] + "&" + "hos_id=" + vo[1] + "&"
				+ "copy_code=" + vo[2] + "&" + "in_id=" + vo[3] + "&"
				+ "in_no=" + vo[4];
		parent.$.ligerDialog.open({
			title : '查看入库单',
			height : $(window).height(),
			width : $(window).width(),
			url : 'hrp/mat/storage/in/updatePage.do?isCheck=false&'
					+ paras.toString(),
			modal : true,
			showToggle : false,
			showMax : true,
			showMin : true,
			isResize : true,
			parentframename : window.name, //用于parent弹出层调用本页面的方法或变量
		});
	} 
 
	//打印
	function print(){
    	
    	if(grid.getData().length==0){
    		
			$.ligerDialog.error("请先查询数据！");
			
			return;
		}
   		
		var dates = getCurrentDate();
    	
    	var cur_date = dates.split(";")[2];

		//表头
	  	var heads = {
	  		rows: [
					{"cell":0,"value":"单位: ${sessionScope.hos_name}","colSpan":3,"br":true},
					{"cell":0,"value":"统计日期: " + $("#begin_confirm_date").val() +" 年  "+ $("#end_confirm_date").val(),"colSpan":3,"br":true}
	   		]
	  	};
		//表尾
		var foots = {
			rows: [
					{"cell":0,"value":"主管:","colSpan":3} ,
					{"cell":3,"value":"复核人:","colSpan":3},
					//{"cell":1, "from":"right","align":"right","value":"制单人： ${sessionScope.user_name}","colSpan":2},
	   		]
		}; 
  	  
		var printPara={
			title: "供应商付款表",//标题
			columns: JSON.stringify(grid.getPrintColumns()),//表头
			class_name: "com.chd.hrp.mat.service.matpayquery.MatMainService",
			method_name: "printData",
			bean_name: "matMainService", 
			heads: JSON.stringify(heads), //表头需要打印的查询条件,可以为空
			foots: JSON.stringify(foots),//表尾需要打印的查询条件,可以为空 
		};
		
		//执行方法的查询条件
		$.each(grid.options.parms,function(i,obj){
			printPara[obj.name]=obj.value;
		});
			
		officeGridPrint(printPara);
    }

	function loadDict() {
		//字典下拉框
		autoCompleteByData("#state", matInMain_state.Rows, "id", "text", true,
				true, "", false, false, 222);
		autocomplete("#pay_code", "../queryMatPayType.do?isCheck=false", "id", "text", true, true, '', false, '', 222);

		autocomplete("#store_code", "../queryMatStoreDictDate.do?isCheck=false",
				"id", "text", true, true, isUseAffiStore ? {read_or_write:1} : {
					is_com : 0,
					read_or_write:1
				}, false, false, 222);
		
		autocomplete("#sup_code", "../queryHosSupDict.do?isCheck=false",
				"id", "text", true, true, "", false, false, 242, false, 242);

		autodate("#begin_confirm_date", "yyyy-mm-dd", "month_first");
		autodate("#end_confirm_date", "yyyy-mm-dd", "month_last");
		$("#begin_confirm_date").ligerTextBox({
			width : 110
		});
		$("#end_confirm_date").ligerTextBox({
			width : 110
		});
		$("#in_no").ligerTextBox({
			width : 222
		});
		$("#inv_code").ligerTextBox({
			width : 242
		}); 
		$("#invoice_no").ligerTextBox({
			width : 222
		});
		$("#batch_no").ligerTextBox({
			width : 222
		});
		$("#brief").ligerTextBox({
			width : 222
		});

		$("#begin_amount_money").ligerTextBox({
			width : 110
		});
		$("#end_amount_money").ligerTextBox({
			width : 110
		});

	}

	function showDetail() {
		query_type = $("#query_type input:radio:checked").val();
		
		if (query_type == 0) {
			//liger.get("inv_code").clear();
			//$(".demo").attr("style","display:none");
			$("#batch_no").val();
		}/* else{
					$(".demo").attr("style","display:table-cell");
				} */
		loadHead();
		query();
	}
</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>

	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" >
        <tr>
        </tr> 
        <tr>
        	<td align="right" class="l-table-edit-td"  width="10%">
            	入库日期：
            </td>
            <td align="left" class="l-table-edit-td"  width="20%">
				<table>
					<tr>
						<td>
							<input class="Wdate" name="begin_confirm_date" id="begin_confirm_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
						<td align="right" class="l-table-edit-td"  >
							至
						</td>
						<td>
							<input class="Wdate" name="end_confirm_date" id="end_confirm_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
            		</tr>
				</table>
	        </td>
	        <td align="right" class="l-table-edit-td"  width="10%">
				仓库：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="store_code" type="text" id="store_code" ltype="text" validate="{required:false}" />
            </td>
			<td align="right" class="l-table-edit-td"  width="10%">
				状态：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="state" type="text" id="state" ltype="text" validate="{required:false}" />
            </td>
        </tr> 
        <tr>
			<td align="right" class="l-table-edit-td"  width="10%">
				供应商：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="sup_code" type="text" id="sup_code" ltype="text" validate="{required:false}" />
            </td>
            
	        <td align="right" class="l-table-edit-td" width="10%">
				单据号：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="in_no" type="text" id="in_no" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
            
			<td align="right" class="l-table-edit-td" width="10%">发票号：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="invoice_no" type="text" id="invoice_no" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  width="10%">
            	金额范围：
            </td>
            <td align="left" class="l-table-edit-td"  width="20%">
				<table>
					<tr>
						<td>
							<input name="begin_amount_money" type="text" id="begin_amount_money" ltype="text" />
						</td>
						<td align="right" class="l-table-edit-td"  >
							至
						</td>
						<td>
							<input name="end_amount_money" type="text" id="end_amount_money" ltype="text" />
						</td>
            		</tr>
				</table>
	        </td>
	       
            <td align="right" class="l-table-edit-td" >摘要：</td>
            <td align="left" class="l-table-edit-td" >
            	<input name="brief" type="text" id="brief" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
            
        	<td align="right" class="l-table-edit-td  demo" width="10%">批号：</td>
            <td align="left" class="l-table-edit-td  demo" width="20%">
            	<input name="batch_no" type="text" id="batch_no" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
        </tr>
        <tr>
            <td align="right" class="l-table-edit-td" width="10%">材料信息：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="inv_code" type="text" id="inv_code" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
            
            <td align="right" class="l-table-edit-td" >
            	查询类别：
			</td>
			<td id="query_type" align="left" class="l-table-edit-td">
				<input name="query_type" type="radio" value="0" onclick="showDetail();"/>&nbsp;&nbsp;按入库单据
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input name="query_type" type="radio" checked="checked" value="1" onclick="showDetail();"/>&nbsp;&nbsp;按入库明细
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input name="query_type" type="radio" value="2" onclick="showDetail();"/>&nbsp;&nbsp;按供应商
			</td>
          	<td align="right" class="l-table-edit-td"  width="10%">
				付款方式：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="pay_code" type="text" id="pay_code" ltype="text" validate="{required:false}" />
            </td>
        </tr>
    </table>
	<div id="maingrid"></div>
</body>
</html>
