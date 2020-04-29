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
	var userUpdateStr;
	var editor;
	$(function() {
		
		loadDict();//加载下拉框
		loadHead(null);

		$("#allot_out_no").ligerTextBox({
			width : 160,
			disabled : true,
			cancelable : false
		});
		$("#in_group_id").ligerComboBox({
			width : 160,
			disabled : true,
			cancelable : false
		});
		
		$("#in_store_id").ligerComboBox({
			width : 160,
			disabled : true,
			cancelable : false
		});
		$("#out_store_id").ligerComboBox({
			width : 160,
			disabled : true,
			cancelable : false
		});
		$("#in_hos_id").ligerComboBox({
			width : 160,
			disabled : true,
			cancelable : false
		});
		$("#create_date").ligerTextBox({
			width : 160,
			disabled : true,
			cancelable : false
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
	
	function this_close() {
		frameElement.dialog.close();
	}

	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		grid.options.parms.push({
			name : 'allot_out_no',
			value : $("#allot_out_no").val() == "" ? "0" : $("#allot_out_no").val()
		});
		grid.loadData(grid.where);
	}
	

	function loadHead() {
		
		grid = $("#maingrid")
				.ligerGrid(
						{
							columns : [
								{
									display : '卡片编码',
									name : 'ass_card_no',
									align : 'left',
									width : '180',
									render : function(rowdata, rowindex,
											value) {
										if (rowdata.ass_card_no == null
												|| rowdata.ass_card_no == "") {
											return "";
										}
										return "<a href=javascript:openCardUpdate('"
												+ rowdata.group_id
												+ "|"
												+ rowdata.hos_id
												+ "|"
												+ rowdata.copy_code
												+ "|"
												+ rowdata.ass_card_no
												+ "')>"
												+ rowdata.ass_card_no
												+ "</a>";
									}
								}, {
									display : '资产编码',
									name : 'ass_code',
									align : 'left',
									width : '150'
								}, {
									display : '资产名称',
									name : 'ass_name',
									align : 'left',
									width : '150'
								}, {
									display : '资产原值',
									name : 'price',
									align : 'left',
									width : '120',
									editor : {
										type : 'numberbox'
									},
									render : function(item) {
										return formatNumber(
												item.fore_money, '${ass_05006}', 1);
									}
								}, {
									display : '本期折旧',
									name : 'now_depre',
									align : 'left',
									width : '120',
									render : function(item) {
										return formatNumber(
												item.fore_money, '${ass_05005}', 1);
									}
								}, {
									display : '本期分摊',
									name : 'now_manage_depre',
									align : 'left',
									width : '120',
									render : function(item) {
										return formatNumber(
												item.fore_money, '${ass_05005}', 1);
									}
								}, {
									display : '累计折旧',
									name : 'add_depre',
									align : 'left',
									width : '120',
									render : function(item) {
										return formatNumber(
												item.fore_money, '${ass_05005}', 1);
									}
								},
								{
									display : '累计折旧月份',
									name : 'add_depre_month',
									align : 'left',
									width : '120'
								},
								{
									display : '累计分摊',
									name : 'manage_depre_money',
									align : 'left',
									width : '120',
									render : function(item) {
										return formatNumber(
												item.fore_money, '${ass_05005}', 1);
									}
								}, {
									display : '资产净值',
									name : 'cur_money',
									align : 'left',
									width : '120',
									render : function(item) {
										return formatNumber(
												item.fore_money, '${ass_05006}', 1);
									}
								}, {
									display : '预留残值',
									name : 'fore_money',
									align : 'left',
									width : '120',
									render : function(item) {
										return formatNumber(
												item.fore_money, '${ass_05006}', 1);
									}
								}, {
									display : '备注',
									name : 'note',
									editor : {
										type : 'text'
									},
									align : 'left',
									width : '180'
								} ],
							dataAction : 'server',
							dataType : 'server',
							url:'queryAllotOutDetByAllotInInassets.do',
							usePager : false,
							width : '100%',
							height : '100%',
							checkbox : false,
							enabledEdit : false,
							alternatingRow : true,
							isScroll : true,
							rownumbers : true,
							delayLoad : true,//初始化明细数据
							selectRowButtonOnly : true,//heightDiff: -10,
							toolbar : {
								items : [  {
									text : '关闭',
									id : 'close',
									click : this_close,
									icon : 'candle'
								}]
							}
						});

		gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	function openCardUpdate(obj) {
		var vo = obj.split("|");
		var parm = "group_id=" + vo[0] + "&" + "hos_id=" + vo[1] + "&"
				+ " copy_code=" + vo[2] + "&" + "ass_card_no=" + vo[3] + "&"
				+ "ass_nature=05";
		parent.parent.$.ligerDialog.open({
			title: '资产卡片维护',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/ass/asscard/assInCardUpdatePage.do?isCheck=false&'+parm,
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,slide:false,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
	}
	
	function printBarcode(){
		
	}
	
	function loadDict() {
		//字典下拉框
		autocomplete("#out_store_id", "../../queryHosStoreDict.do?naturs_code=05&isCheck=false","id", "text",true,true,null,false,null,"300");
		liger.get("out_store_id").setValue("${out_store_id}@${out_store_no}");
		liger.get("out_store_id").setText("${out_store_code} ${out_store_name}");
		
		
		autocomplete("#in_group_id", "../../queryGroupDict.do?isCheck=false","id", "text",true,true,null,false,null,"160");
		liger.get("in_group_id").setValue("${in_group_id}");
		liger.get("in_group_id").setText("${in_group_name}");
		
		
		autocomplete("#in_hos_id", "../../null.do?isCheck=false","id", "text",true,true,null,false,null,"160");
		liger.get("in_hos_id").setValue("${in_hos_id}");
		liger.get("in_hos_id").setText("${in_hos_name}");
		
		
		autocomplete("#in_store_id", "../../null.do?isCheck=false","id", "text",true,true,null,false,null,"160");
		liger.get("in_store_id").setValue("${in_store_id}@${in_store_no}");
		liger.get("in_store_id").setText("${in_store_code} ${in_store_name}");
		
		
		$("#in_group_id").change(function(){
			autocomplete("#in_hos_id", "../../queryHosInfoDict.do?isCheck=false","id", "text",true,true,{group_id:liger.get("in_group_id").getValue()},false,null,"160");
			$("#in_hos_id").change(function(){
				autocomplete("#in_store_id", "../../queryHosStoreDict.do?naturs_code=05&isCheck=false","id", "text",true,true,{group_id:liger.get("in_group_id").getValue(),hos_id:liger.get("in_hos_id").getValue()},false,null,"160");
				liger.get("in_store_id").setValue("${in_store_id}@${in_store_no}");
				liger.get("in_store_id").setText("${in_store_code} ${in_store_name}");
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
			<td align="left" class="l-table-edit-td"><input name="allot_out_no" value="${allot_out_no }"
				type="text" id="allot_out_no" disabled="disabled" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>制单日期：</td>
			<td align="left" class="l-table-edit-td"><input
				name="create_date" type="text" id="create_date" class="Wdate" value="${create_date }"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>调出仓库：</td>
			<td align="left" class="l-table-edit-td"><input name="out_store_id"
				type="text" id="out_store_id" /></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right"  class="l-table-edit-td" style="padding-left: 20px;">调入集团：</td>
			<td align="left"  class="l-table-edit-td"><input name="in_group_id"
				type="text" id="in_group_id" 
				 /></td>
			<td align="left"></td>	 	
			<td align="right"  class="l-table-edit-td" style="padding-left: 20px;">调入单位：</td>
			<td align="left"  class="l-table-edit-td"><input name="in_hos_id"
				type="text" id="in_hos_id" 
				 /></td>
			<td align="left"></td>	 
			<td align="right"  class="l-table-edit-td" style="padding-left: 20px;">调入仓库：</td>
			<td align="left"  class="l-table-edit-td"><input name="in_store_id"
				type="text" id="in_store_id" 
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
					rows="3" cols="70" id="note" name="note" disabled="disabled">${note }</textarea></td>
			<td align="left"></td>

		</tr>
	</table>
	<div id="maingrid"></div>

</body>
</html>
