<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
	var grid;
	var gridManager = null;
	var userUpdateStr;
	var selectData = "";
	var clicked = 0;
	var editor;
	$(function() {
		loadDict()//加载下拉框
		//加载数据
		loadHead(null);
		loadHotkeys();

		$("#ins_no").ligerTextBox({
			disabled : true,
			cancelable : false,
			width : 160
		});
		$("#pact_code").ligerTextBox({
			width : 160
		});
		$("#ass_year").ligerTextBox({
			width : 160
		});
		$("#ass_month").ligerTextBox({
			width : 160
		});
		$("#ins_date").ligerTextBox({
			width : 160
		});
		$("#ven_id").ligerTextBox({
			width : 160
		});
		$("#ins_money").ligerTextBox({
			disabled : true,
			cancelable : false,
			width : 160
		});
		$("#ven_id").change(function(){
			
			var ven_id = liger.get("ven_id").getValue().split("@")[0];
			var ven_no = liger.get("ven_id").getValue().split("@")[1];

			if (ven_no == null || ven_id == null || ven_id == ""
					|| ven_no == "") {
				ven_no = "";
				ven_id = "";
			}
			
			editor.grid.url = '../queryAssNoDictTable.do?isCheck=false&ven_id='+ven_id+'&ven_no='+ven_no;
		});
	});
	//查询

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
					url : '../queryAssNoDictTable.do?isCheck=false&ven_id='+ven_id+'&ven_no='+ven_no,
					pageSize : 30
				},
				keySupport : true,
				alwayShowInDown : true,
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
										width : 100,
										name : 'ass_code',
										align : 'left'
									},
									{
										display : '资产名称',
										name : 'ass_id',
										align : 'left',
										width : 100,
										textField : 'ass_name',
										editor : editor,
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
										display : '型号',
										name : 'ass_model',
										editor : {
											type : 'text'
										},
										align : 'left',
										width : 100
									},
									{
										display : '规格',
										name : 'ass_spec',
										editor : {
											type : 'text'
										},
										align : 'left',
										width : 100
									},
									{
										display : '品牌',
										name : 'ass_brand',
										editor : {
											type : 'text'
										},
										align : 'left',
										width : 100
									},
									{
										display : '生产厂家',
										name : 'fac_id',
										textField : 'fac_name',
										editor : {
											type : 'select',
											valueField : 'id',
											textField : 'text',
											url : '../queryHosFacDict.do?isCheck=false',
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
										width : '260'
									},
									{
										display : '安装数量',
										name : 'ins_amount',
										type : 'int',
										editor : {
											type : 'int'
										},
										align : 'left',
										width : 100
									},
									{
										display : '单价',
										name : 'ins_price',
										editor : {
											type : 'numberbox',
										},
										align : 'right',
										width : 100,
										render : function(item) {
											return formatNumber(item.ins_price,
													'${ass_05006}', 1);
										}
									},
									{
										display : '安装费用',
										name : 'ins_money',
										align : 'right',
										width : 100,
										totalSummary : {
											render: function (suminf, column, cell)
					                        {
												$("#ins_money").val(suminf.sum);
					                            return '<div>' + formatNumber(suminf.sum,'${ass_05005}',1) + '</div>';
					                        }
										},
										editor : {
											type : 'numberbox',
										},
										render : function(item) {
											item.ins_money = formatNumber(
													item.ins_price
															* item.ins_amount
													);
											return formatNumber(item.ins_money,
													'${ass_05005}', 1);
										}
									}, {
										display : '安装单位',
										name : 'ins_company',
										editor : {
											type : 'text'
										},
										align : 'left',
										width : 150
									},

									{
										display : '联系电话',
										name : 'ins_tele',
										editor : {
											type : 'text'
										},
										align : 'left',
										width : 100
									}, {
										display : '主要安装工程师',
										name : 'ins_engr',
										editor : {
											type : 'text'
										},
										align : 'left',
										width : 100
									}, {
										display : '安装说明',
										name : 'ins_desc',
										editor : {
											type : 'text'
										},
										align : 'left',
										width : 140
									} ],
							dataAction : 'server',
							dataType : 'server',
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
							checkbox : true,
							rownumbers : true,
							delayLoad : true,
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
									click : itemclick,
									icon : 'delete'
								}, {
									line : true
								}, {
									text : '关闭',
									id : 'close',
									click : this_close,
									icon : 'candle'
								}
								]
							}
						});

		gridManager = $("#maingrid").ligerGetGridManager();
	}
	function this_close() {
		frameElement.dialog.close();
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
		if (column_name == "ass_id") {
			if (selectData != "" || selectData != null) {
				//回充数据 
				//grid.updateCell('pact_code', 100, e.record);
				grid.updateRow(rowindex_id, {
					ass_code : data.ass_code,
					ass_name : data.ass_name,
					ass_model : data.ass_model,
					ass_spec : data.ass_spec,
					ass_brand : data.ass_brand,
					fac_id : data.fac_id,
					fac_name : data.fac_name
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
		grid.updateCell('ins_company', liger.get("ven_id").getText().split(" ")[1], e.record);
		grid.updateTotalSummary();
		return true;
	}
	//保存主表
	function save() {
		gridManager.endEdit();
		if ($("#ass_year").val() == "") {
			$.ligerDialog.error('统计年度不能为空');
			return;
		}
		if ($("#ass_month").val() == "") {
			$.ligerDialog.error('统计月份不能为空');
			return;
		}
		if ($("#ins_date").val() == "") {
			$.ligerDialog.error('安装日期不能为空');
			return;
		}
		if (liger.get("dept_id").getValue() == "") {
			$.ligerDialog.error('安装科室不能为空');
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

			ins_id : $("#ins_id").val() == "" ? 0 : $("#ins_id").val(),

			ins_no : $("#ins_no").val(),

			ass_year : $("#ass_year").val(),

			ass_month : $("#ass_month").val(),

			ins_date : $("#ins_date").val(),

			pact_code : liger.get("pact_code").getValue().split("@")[0],

			ven_id : liger.get("ven_id").getValue().split("@")[0],

			ven_no : liger.get("ven_id").getValue().split("@")[1],

			dept_id : liger.get("dept_id").getValue().split("@")[0],

			dept_no : liger.get("dept_id").getValue().split("@")[1],

			accept_desc : $("#accept_desc").val(),

			ParamVo : JSON.stringify(data)

		};
		if (validateGrid()) {
			ajaxJsonObjectByUrl("addAssInsMain.do", formPara, function(responseData) {
				if (responseData.state == "true") {
					parentFrameUse().query();
					$.ligerDialog.confirm('添加成功 。是否继续添加单据?', function(yes) {
						if (yes) {
							$("#ins_no").val("");
							autodate("#ass_year", "YYYY");
							autodate("#ass_month", "MM");
							autodate("#ins_date");
							$("#accept_desc").val("");
							liger.get("pact_code").setValue("");
							liger.get("pact_code").setText("");
							liger.get("ven_id").setValue("");
							liger.get("ven_id").setText("");
							liger.get("dept_id").setValue("");
							liger.get("dept_id").setText("");
							grid.loadData();
							is_addRow();
						} else {
							this_close();
						}
					});
				}
			});
		}
	}
	function validateGrid() {
		var data = gridManager.getData();
		var msg = "";
		var targetMap = new HashMap();
		var msgMap = new HashMap();
		//删除空行
		$.each(data, function(i, v) {
			if (!isnull(v.ass_code)) {
				var key = v.ass_code + "|" + v.ass_model + "|" + v.ass_spec
						+ "|" + v.fac_id;
				var value = "第" + (i + 1) + "行";
				//if (isnull(v.ass_spec)) {
					//msg += "[规格]、";
				//}
				//if (isnull(v.ass_model)) {
					//msg += "[型号]、";
				//}
				/* if (v.fac_id == '@' || isnull(v.fac_id)) {
					msg += "[生产厂家]、";
				} */
				if (isnull(v.ins_price)) {
					msg += "[单价]、";
				}
				if (isnull(v.ins_amount)) {
					msg += "[安装数量]、";
				}
				//if (isnull(v.ins_money)) {
					//msg += "[安装费用]、";
				//}
				/* if (isnull(v.ins_engr)) {
					msg += "[主要安装工程师]";
				} */
				if (msg != "") {
					msgMap.put(value + msg + "不能为空或不能为零！\n\r", "");
				}
				if (isnull(targetMap.get(key))) {
					targetMap.put(key, value);
				} else {
					msgMap.put(targetMap.get(key) + "与" + value + "重复！\n\r",
							value);
				}

			}
		});
		var price=0;
		for(var i=0;i<data.length;i++){
            if(data[i].ins_price!=undefined){
               price+=data[i].ins_price;

             }
           }
           if(price<0){
              $.ligerDialog.warn("单价不能为负数");
               return false;
             }
           var ins_price=0;
   		for(var i=0;i<data.length;i++){
               if(data[i].ins_money!=undefined){
            	   ins_price+=data[i].ins_money;

                }
              }
              if(ins_price<0){
                 $.ligerDialog.warn("安装费用不能为负数");
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
	function itemclick(item) {
		if (item.id) {
			switch (item.id) {
			case "delete":
				gridManager.deleteSelectedRow();
				return;
			}
		}
	}

	function loadDict() {
		//字典下拉框
		autocomplete("#audit_emp",
				"../../../hrp/sys/queryUserDict.do?isCheck=false", "id",
				"text", true, true);
		
		autocomplete("#dept_id", "../queryDeptDict.do?isCheck=false", "id",
				"text", true, true, null, null);
		//获取当前用户科室默认值
		ajaxJsonObjectByUrl("../queryDeptDictInitValue.do?isCheck=false",{},function(data){
			var id = data == null || data == "" ? null : data[0].id;
			var text = data == null || data == "" ? null : data[0].text;
			liger.get("dept_id").setValue(id);
			liger.get("dept_id").setText(text);
		},false)

		autocomplete("#create_emp",
				"../../../hrp/sys/queryUserDict.do?isCheck=false", "id",
				"text", true, true);
		autocomplete("#ven_id", "../queryHosSupDict.do?isCheck=false", "id",
				"text", true, true, null, false, null, "400");
		autocomplete("#pact_code", "../queryContractMain.do?isCheck=false",
				"id", "text", true, true, null, false, null, "400");
		autodate("#ass_year", "YYYY");
		autodate("#ass_month", "MM");
		autodate("#ins_date");
	}

	//键盘事件
	function loadHotkeys() {

		hotkeys('A', save);

		hotkeys('B', itemclick, [ {
			id : 'save'
		} ]);

	}

	function is_addRow() {
		setTimeout(function() { //当数据为空时 默认新增一行
			grid.addRow();
		}, 1000);
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;" onload="is_addRow()">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<input type="hidden" id="ins_id" name="ins_id" />
	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>安装单号：</td>
			<td align="left" class="l-table-edit-td"><input name="ins_no"
				type="text" id="ins_no" disabled="disabled" ltype="text"
				validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>购置年度：</td>
			<td align="left" class="l-table-edit-td"><input name="ass_year"
				type="text" id="ass_year" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy'})" /></td>
			<td align="left"></td>

			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>购置月份：</td>
			<td align="left" class="l-table-edit-td"><input name="ass_month"
				type="text" id="ass_month" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'MM'})" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>安装日期：</td>
			<td align="left" class="l-table-edit-td"><input name="ins_date"
				type="text" id="ins_date" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="left"></td>

		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">合&nbsp;&nbsp;同&nbsp;&nbsp;号：</td>
			<td align="left" class="l-table-edit-td"><input
				name="pact_code" type="text" id="pact_code" ltype="text"
				validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">供&nbsp;&nbsp;应&nbsp;&nbsp;商：</td>
			<td align="left" class="l-table-edit-td"><input name="ven_id"
				type="text" id="ven_id" ltype="text"
				validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>安装科室：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_id"
				type="text" id="dept_id" ltype="text"
				validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">安装费用：</td>
			<td align="left" class="l-table-edit-td"><input
				name="ins_money" type="text" id="ins_money" disabled="disabled" /></td>
			<td align="left"></td>	
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">摘&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;要：</td>
			<td align="left" class="l-table-edit-td" colspan="6"><textarea
					name="accept_desc" id="accept_desc" rows="3" cols="100"  style="border-color: #aecaf0;"></textarea>
			</td>
			<td align="left"></td>
		</tr>

	</table>

	<div id="maingrid"></div>
</body>
</html>
