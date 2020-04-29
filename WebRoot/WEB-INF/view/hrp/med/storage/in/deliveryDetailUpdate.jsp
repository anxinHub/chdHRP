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
<script src="<%=path%>/lib/hrp/med/med.js" type="text/javascript"></script>
<script src="<%=path%>/lib/stringbuffer.js"></script>
<script type="text/javascript">
	var grid;
	var gridManager = null;
	var userUpdateStr;
	$(function() {
		loadDict()//加载下拉框
		//加载数据
		loadHead(null);
		loadHotkeys();
		query();
	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'delivery_id',
			value : '${delivery_id}'
		});
		grid.options.parms.push({
			name : 'delivery_no',
			value : '${delivery_no}'
		});

		grid.options.parms.push({
			name : 'inv_id',
			value : '${inv_id}'
		});

		//加载查询条件
		grid.loadData(grid.where);
	}

	function loadHead() {
		grid = $("#maingrid")
				.ligerGrid(
						{
							columns : [
									{
										display : '药品变更号',
										name : 'inv_no',
										align : 'left',
										hide : true
									},
									{
										display : '订单ID',
										name : 'delivery_id',
										align : 'left',
										hide : true
									},
									{
										display : '订单号',
										name : 'delivery_no',
										align : 'left',
										width : '120',
// 										render : function(rowdata, rowindex,
// 												value) {
// 											return '<a href=javascript:update_open("'
// 													+ rowdata.group_id
// 													+ ','
// 													+ rowdata.hos_id
// 													+ ','
// 													+ rowdata.copy_code
// 													+ ','
// 													+ rowdata.order_id
// 													+ '")>'
// 													+ rowdata.order_code
// 													+ '</a>';
// 										}
									},
									{
										display : '药品编码',
										name : 'inv_code',
										align : 'left',
										width : '120'
									},
									{
										display : '药品名称',
										name : 'inv_name',
										align : 'left',
										width : '220'
									},
									{
										display : '规格型号',
										name : 'inv_model',
										minWidth : 120,
										align : 'left'
									},
									{
										display : '计量单位',
										name : 'unit_name',
										minWidth : 80,
										align : 'left'
									},
									{
										display : '送货单数量',
										name : 'delivery_amount',
										minWidth : 80,
										type : 'float',
										align : 'left',
										totalSummary : {
											align : 'left',
											render : function(suminf, column,
													cell) {
												return '<div>'
														+ formatNumber(
																suminf.sum == null ? 0
																		: suminf.sum,
																0, 0)
														+ '</div>';
											}
										}
									},
									{
										display : '已入库数量(E)',
										name : 'already_amount',
										minWidth : 100,
										type : 'float',
										align : 'left',
										totalSummary : {
											align : 'left',
											render : function(suminf, column,
													cell) {
												return '<div>'
														+ formatNumber(
																suminf.sum == null ? 0
																		: suminf.sum,
																0, 0)
														+ '</div>';
											}
										}
									},
									{
										display : '入库数量(E)',
										name : 'amount',
										minWidth : 100,
										type : 'float',
										align : 'left',
										editor : {
											type : 'float',
										},
										totalSummary : {
											align : 'left',
											render : function(suminf, column,
													cell) {
												return '<div>'
														+ formatNumber(
																suminf.sum == null ? 0
																		: suminf.sum,
																0, 0)
														+ '</div>';
											}
										}
									},
									{
										display : '单价(E)',
										name : 'price',
										minWidth : 80,
										align : 'right',
										render : function(rowdata, rowindex,
												value) {
											rowdata.price = value == null ? ""
													: formatNumber(
															value,
															'${sessionScope.med_para_map["08006"] }',
															0);
											return value == null ? ""
													: formatNumber(
															value,
															'${sessionScope.med_para_map["08006"] }',
															1);
										}
									},
									{
										display : '金额(E)',
										name : 'amount_money',
										minWidth : 100,
										align : 'right',
										render : function(rowdata, rowindex,
												value) {
											rowdata.amount_money = value == null ? ""
													: formatNumber(
															value,
															'${sessionScope.med_para_map["08005"] }',
															0);
											return value == null ? ""
													: formatNumber(
															value,
															'${sessionScope.med_para_map["08005"] }',
															1);
										},
										totalSummary : {
											align : 'right',
											render : function(suminf, column,
													cell) {
												return '<div>'
														+ formatNumber(
																suminf.sum == null ? 0
																		: suminf.sum,
																'${sessionScope.med_para_map["08005"] }',
																1) + '</div>';
											}
										}

									}
									/*, {
										display : '生产批号(E)',
										name : 'batch_no',
										minWidth : 80,
										align : 'left',
										editor : {
											type : 'text',
										}
									}, {
										display : '有效日期(E)',
										name : 'inva_date',
										type : 'date',
										align : 'left',
										format : 'yyyy-MM-dd',
										minWidth : 100,
										editor : {
											type : 'date',
										},
									}, {
										display : '灭菌日期(E)',
										name : 'disinfect_date',
										type : 'date',
										align : 'left',
										format : 'yyyy-MM-dd',
										minWidth : 100,
										editor : {
											type : 'date',
										},
									}, {
										display : '条形码(E)',
										name : 'sn',
										minWidth : 80,
										align : 'left',
										editor : {
											type : 'text',
										}
									}, {
										display : '生产厂商',
										name : 'fac_name',
										width : 180,
										align : 'left'
									}, {
										display : '备注(E)',
										name : 'note',
										width : 80,
										align : 'left',
										editor : {
											type : 'text',
										}
									}*/ ],
							dataAction : 'server',
							dataType : 'server',
							usePager : true,
							url : '../../sup/supdeliverydetail/queryDetailWithDelivery.do?isCheck=false',
							width : '100%',
							height : '90%',
							checkbox : true,
							rownumbers : true,
							isAddRow: false,
							enabledEdit : true,
							delayLoad : true,//初始化不加载，默认false
							selectRowButtonOnly : true,//heightDiff: -10,
							//groupColumnName : 'inv_name',
							//groupColumnDisplay : '药品名称         ',
							
						});

		gridManager = $("#maingrid").ligerGetGridManager();
		grid.toggleCol("inv_no", false);
	}

	//键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
		hotkeys('A', add);
		hotkeys('C', this_close);
	}

	//修改
	function update_open(obj) {
		var vo = obj.split(",");
		var paras = "group_id=" + vo[0] + "&" + "hos_id=" + vo[1] + "&"
				+ "copy_code=" + vo[2] + "&" + "delivery_id=" + vo[3];
		$.ligerDialog.open({
			title : '订单修改',
			height : 500,
			width : 1000,
			url : '../../sup/suporder/supOrderUpdatePage.do?isCheck=false&'
					+ paras.toString(),
			modal : true,
			showToggle : false,
			showMax : true,
			showMin : false,
			isResize : true,
			top : 1
		});
	}
	function validateGrid() {
		//明细
		var rowm = "";
		var msg = "";
		var priceMsg = "";
		var rows = 0;
		var store_inv = ""; //用于判断是否属于该仓库
		var sup_inv = ""; //用于判断唯一供应商
		var data = gridManager.getData();
		//alert(JSON.stringify(data));
		//判断grid 中的数据是否重复或者为空
		var targetMap = new HashMap();
		$
				.each(
						data,
						function(i, v) {
							rowm = "";
							if (v.inv_id) {
								
								if (!v.amount || v.amount>v.not_ship_amount || v.amount>v.delivery_amount) {
									rowm += "[数量]、";
								}
								
								
								if (rowm != "") {
									rowm = "第"
											+ (i + 1)
											+ "行"
											+ rowm
													.substring(0,
															rowm.length - 1)
											+ "不能为空、不能大于未入库数量" + "<br>";
								}
								
								msg += rowm;
							}
						});

		if (msg != "") {
			$.ligerDialog.warn(msg);
			return false;
		}
		return true;
	}

	function save() {
		var data = gridManager.getCheckedRows();
		
		if (data.length == 0) {
			$.ligerDialog.warn('请选择行');
			return false;
		}  else {
			if (validateGrid()) {
			var formPara = {
				in_id : '${in_id}',
				in_no : '${in_no}',
			    inv_id : '${inv_id}',
				detailData : JSON.stringify(data)
			};
			$.ligerDialog.confirm('确定更新入库单?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("updateDeliveryGenerate.do?isCheck=false",
							formPara, function(responseData) {
						      parentFrameUse().queryDetail();
								this_close();
							});
				}
			});
		}
		}
	}

	function this_close() {
		frameElement.dialog.close();
	}

	function loadDict() {

		//格式化按钮
		$("#query").ligerButton({
			click : query,
			width : 90
		});
		//格式化按钮
		$("#save").ligerButton({
			click : save,
			width : 90
		});
		$("#close").ligerButton({
			click : this_close,
			width : 90
		});
	}
</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div style="width: 100%; height: 100%;">
		<div id="maingrid"></div>
		<table cellpadding="0" cellspacing="0" class="l-table-edit"
			width="100%" style="margin-top: 5px;">
			<tr>
				<td align="center" class="l-table-edit-td">
					
					<button id="save" accessKey="B">
						<b>确定（<u>B</u>）
						</b>
					</button> &nbsp;&nbsp;
					<button id="close" accessKey="C">
						<b>关闭（<u>C</u>）
						</b>
					</button>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>
