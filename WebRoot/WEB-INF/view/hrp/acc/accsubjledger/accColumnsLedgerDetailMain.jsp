<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;" xmlns="http://www.w3.org/1999/xhtml">
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script src="<%=path%>/lib/et_components/et_plugins/etDatepicker.min.js"></script>	

<script type="text/javascript">
	var grid,acc_year_month1,acc_year_month2; 

	var gridManager = null;

	var userUpdateStr;

	var columns1 = "";

	var columns2 = "";

	$(function() {
		loadDict();
		loadHead(); //加载数据
		//queryColumns();//查询  用户设置的借方分析、贷方分析显示的列内容
		//hideOrShowColumns() ;////  根据 用户设置的借方分析、贷方分析显示的列内容 显示或者隐藏列
	});
	//查询
	function query() {
		var columns = [];
		grid.options.parms = [];

		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		var year_month1 = acc_year_month1.getValue();
		var year_month2 = acc_year_month2.getValue();
		if (year_month1 == "" || year_month2 == "") {
			$.ligerDialog.error('会计期间为必填项，不能为空！');
			return;
		}
		
		var subj_name = liger.get("subj_name").getValue();
		if (subj_name == "") {
			$.ligerDialog.error('科目为必填项，不能为空！');
			return;
		}
		
		var plan_code = liger.get("plan_code").getValue();
		if (plan_code == "") {
			$.ligerDialog.error('多栏方案为必填项，不能为空！');
			return;
		}
		
		grid.options.parms.push({
			name : 'subj_name',
			value : subj_name
		});
		grid.options.parms.push({
			name : 'acc_year',
			value : year_month1.split(".")[0]
		});

		grid.options.parms.push({
			name : 'between_acc_month',
			value : year_month1.split(".")[1]
		});

		grid.options.parms.push({
			name : 'end_acc_month',
			value : year_month2.split(".")[1]
		});

		grid.options.parms.push({
			name : 'plan_code',
			value : plan_code.split(" ")[0]
		});

		var is_state = 1;
		if ($("#is_state").attr("checked") == true) {
			is_state = 99;
		}

		grid.options.parms.push({
			name : 'state',
			value : is_state
		});
		
		columns1 = "";
		columns2 = "";
		//根据方案编码查询借贷分析科目
		$.ajax({

					type : "POST",
					url : "../queryAccSubjByPlan.do?isCheck=false&acc_year="
							+ acc_year_month1.getValue().split(".")[0],
					data : {'plan_code' : liger.get("plan_code").getValue().split(" ")[0]},
					dataType : "json",
					async : false,
					success : function(data) {
						if (data.Rows.length > 0) {

							$.each(data.Rows,
											function(i, v) {

												if (v.subj_dire == 0) {

													columns1 += "{display: '"
															+ v.SUBJ_NAME
															+ "', name: 'j"
															+ v.subj_code
															+ "', align: 'right',minWidth:100,formatter:'###,##0.00',"
															+ "render : function(rowdata, rowindex, value) {"
															+ "	return formatNumber(rowdata.j"+v.subj_code+", 2, 1); "
															+ "}  },";

												} else {

													columns2 += "{display: '"
															+ v.SUBJ_NAME
															+ "', name: 'd"
															+ v.subj_code
															+ "', align: 'right',minWidth:100,formatter:'###,##0.00',"
															+ "render : function(rowdata, rowindex, value) {"
															+ "	return formatNumber(rowdata.d"+v.subj_code+", 2, 1); "
															+ "}  },";
												}

											});

							if (columns1.length > 0) {

								columns1 = columns1.substr(0,
										columns1.length - 1);
							}

							if (columns2.length > 0) {

								columns2 = columns2.substr(0,
										columns2.length - 1);
							}
						}

					}
				});

		columns.push({
			display : '期间',
			align : 'center',
			columns : [ {
				display : '年',
				name : 'acc_year',
				align : 'left',
				width : 50
			}, {
				display : '月',
				name : 'acc_month',
				align : 'left',
				width : 40
			}, {
				display : '日',
				name : 'acc_date',
				align : 'left',
				width : 40
			} ]
		}, {
			display : '凭证号',
			name : 'vouch_no',
			align : 'left',
			minWidth : 100,
			render : function(rowdata, rowindex, value) {
				 if(rowdata.vouch_id !=null){
					 return "<a href=javascript:openSuperVouch('"+rowdata.vouch_id+"')><div>"+rowdata.vouch_no+"</div></a>"; 
				 }
			}
		}, {
			display : '摘要',
			name : 'summary',
			align : 'left',
			minWidth : 120
		}, {
			display : '借方',
			name : 'debit',
			align : 'right',
			formatter : '###,##0.00',
			minWidth : 100,
			render : function(rowdata, rowindex, value) {
				return formatNumber(rowdata.debit, 2, 1);
			}
		}, {
			display : '贷方',
			name : 'credit',
			align : 'right',
			formatter : '###,##0.00',
			minWidth : 100,
			render : function(rowdata, rowindex, value) {
				return formatNumber(rowdata.credit, 2, 1);
			}
		}, {
			display : '方向',
			name : 'subj_dire',
			align : 'left',
			maxWidth : 60

		}, {
			display : '余额',
			name : 'end_os',
			align : 'right',
			formatter : '###,##0.00',
			minWidth : 100,
			render : function(rowdata, rowindex, value) {
				return formatNumber(rowdata.end_os, 2, 1);
			}
		});

		if (columns1 != "") {
			columns.push({
				display : '借方分析',
				id : "debitColumns",
				align : 'right',
				columns : eval("[" + columns1 + "]"),
			})
		}
		if (columns2 != "") {
			columns.push({
				display : '贷方分析',
				id : "creditColumns",
				align : 'right',
				columns : eval("[" + columns2 + "]"),
			})
		}
		grid.set('columns', columns);

		//加载查询条件
		grid.loadData(grid.where);
	}
	
	function openSuperVouch(vouch_id){
		parent.openFullDialog('hrp/acc/accvouch/superVouch/superVouchMainPage.do?vouch_id='+vouch_id,'会计凭证',0,0,true,true);
	}
	
	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '期间',
				align : 'center',
				columns : [ {
					display : '年',
					name : 'acc_year',
					align : 'left',
					width : 50
				}, {
					display : '月',
					name : 'acc_month',
					align : 'left',
					width : 40
				}, {
					display : '日',
					name : 'acc_date',
					align : 'left',
					width : 40
				} ]
			}, {
				display : '凭证号',
				name : 'vouch_no',
				align : 'left',
				minWidth : 100,
				render : function(rowdata, rowindex, value) {
					 if(rowdata.vouch_id !=null){
						 return "<a href=javascript:openSuperVouch('"+rowdata.vouch_id+"')><div>"+rowdata.vouch_no+"</div></a>"; 
					 }
				}
			}, {
				display : '摘要',
				name : 'summary',
				align : 'left',
				minWidth : 120
			}, {
				display : '借方',
				name : 'debit',
				align : 'right',
				formatter : '###,##0.00',
				minWidth : 100,
				render : function(rowdata, rowindex, value) {
					return formatNumber(rowdata.debit, 2, 1);
				}
			}, {
				display : '贷方',
				name : 'credit',
				align : 'right',
				formatter : '###,##0.00',
				minWidth : 100,
				render : function(rowdata, rowindex, value) {
					return formatNumber(rowdata.credit, 2, 1);
				}
			}, {
				display : '方向',
				name : 'subj_dire',
				align : 'left',
				maxWidth : 60

			}, {
				display : '余额',
				name : 'end_os',
				align : 'right',
				formatter : '###,##0.00',
				minWidth : 100,
				render : function(rowdata, rowindex, value) {
					return formatNumber(rowdata.end_os, 2, 1);
				}
			} ],
			dataAction : 'server',
			dataType : 'server',
			usePager : false,
			pageSizeOptions:[100, 200, 500], pageSize: 100,
			url : 'queryAccColumnsLedgerDetail.do?isCheck=false',
			width : '100%',
			height : '100%',
			checkbox : false,
			rownumbers : true,
			delayLoad : true,
			isScroll : true,
			scroll : true,
			selectRowButtonOnly : true,//heightDiff: -10,
			delayLoad : true,
			toolbar : {
				items : [ {
					text : '查询',
					id : 'search',
					click : query,
					icon : 'search'
				}, {
					line : true
				}, {
					text : '打印',
					id : 'print',
					click : printDate,
					icon : 'print'
				}, {
					line : true
				}, {
					text : '多栏设置',
					id : 'columnsSet',
					click : columnsSet,
					icon : 'columnsSet'
				}, {
					line : true
				} ]
			}
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	//查询  用户设置的借方分析、贷方分析显示的列内容
	function queryColumns() {
		ajaxJsonObjectByUrl("queryColumns.do?isCheck=false", {}, function(
				responseData) {
			$(responseData.columns1).each(
					function() {
						columns1 += "{display:" + this.subj_name + ",name:"+ this.subj_name + ",align: 'left', }"
					})

			$(responseData.columns2).each(
					function() {
						columns2 += "{display:" + this.subj_name + ",name:"+ this.subj_name + ",align: 'left',}"
					})
		})
	}
	// 打开多栏设置页面  设置完成后返回  借方分析、贷方分析显示的列内容
	function columnsSet() {

		parent.$.ligerDialog
				.open({
					url : 'hrp/acc/accsubjledger/accSetColumnsLedgerMainPage.do?isCheck=false',
					data : {},
					height : $(parent.window).height(),
					width : $(parent.window).width(),
					title : '多栏设置',
					modal : true,
					showToggle : false,

					showMin : false,
					isResize : true,
					parentframename : window.name,
					buttons : [ 
						{text : '新建',onclick : function(item, dialog) {dialog.frame.create();},cls : 'l-dialog-btn-highlight'},
						{text : '保存',onclick : function(item, dialog) {dialog.frame.saveAccColumn();},cls : 'l-dialog-btn-highlight'}, 
						{text : '关闭',onclick : function(item, dialog) {dialog.close();}
					} ]
				});

	}
	//显示、隐藏列（根据用户设置显示、隐藏 借方分析、贷方分析列）
	function hideOrShowColumns() {
		if (columns1 == '') {
			grid.toggleCol('debitColumns', false);
		} else {
			grid.toggleCol('debitColumns', true);
		}

		if (columns2 == '') {
			grid.toggleCol('creditColumns', false);
		} else {
			grid.toggleCol('creditColumns', true);
		}
	}

	function loadDict() {
		//字典下拉框
		autocompleteObj({
			id : "#plan_code",
			urlStr : "../queryAccMultiPlan.do?isCheck=false",
			valueField : "id",
			textField : "text",
			autocomplete : true,
			highLight : true,
			parmsStr : null,
			defaultSelect : true,
			initvalue : null,
			initWidth : "180",
			initHeight : null,
			boxwidth : null,
			alwayShowInDown : null
		});

		autocomplete("#cur_code", "../queryCur.do?isCheck=false", "id", "text",true, true, '', true,false,'180');
		//autocomplete("#subj_name", "../querySubj.do?isCheck=false", "id","text", true, true, '', true,false,'180');
		autocompleteObj({
			id:  '#subj_name', 
			urlStr: 	'../querySubj.do?isCheck=false', 
			valueField:  'id', 
			textField:    'text', 
			autocomplete: true, 
			highLight: true, 
			defaultSelect:  true, 
			initWidth: 180, 
			boxwidth: subjWidth, 
			textBoxKeySpace: function(value){
				showSubjTree({
					ligerId: "subj_name", 
					acc_year: acc_year_month1.getValue().split(".")[0], 
					windowName: window.name
				});
			}
		});

		//会计期间
		acc_year_month1 = $("#acc_year_month1").etDatepicker({
			range: false,
			view: "months",
			minView: "months",
			dateFormat: "yyyy.mm",
			defaultDate: ['${yearMonth}']
		});
		acc_year_month2 = $("#acc_year_month2").etDatepicker({
			range: false,
			view: "months",
			minView: "months",
			dateFormat: "yyyy.mm",
			defaultDate: ['${yearMonth}']
		});
     	
		$("#subj_name").ligerTextBox({width : 180});
		$("#cur_code").ligerTextBox({width : 180});

	}
	function printDate() {
		var year_month1 = acc_year_month1.getValue();
		var year_month2 = acc_year_month2.getValue();
		if (grid.getData().length == 0) {
			$.ligerDialog.error("请先查询数据！");
			return;
		}

		var heads = {
			//"isAuto": true/false 默认true，页眉右上角默认显示页码
			"rows" : [
					{
						"cell" : 0,
						"value" : "会计期间：" + year_month1 + "至" + year_month2,
						"colSpan" : "4"
					},
					{
						"cell" : 5,
						"value" : "科目名称：" + liger.get("subj_name").getText().split(" ")[1],
						"colSpan" : "4"
					},
					{
						"cell" : 9,
						"value" : "多栏方案：" + liger.get("plan_code").getText().split(" ")[1],
						"colSpan" : "2"
					} ]
		};

		var printPara = {
			title : "多栏明细账",//标题
			columns : JSON.stringify(grid.getPrintColumns()),//表头
			class_name : "com.chd.hrp.acc.service.books.subjaccount.AccSubjLedgerService",
			method_name : "queryAccColumnsLedgerDetailPrint",
			bean_name : "accSubjLedgerService",
			heads : JSON.stringify(heads)
		//表头需要打印的查询条件,可以为空
		/* foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 */
		};

		//执行方法的查询条件
		$.each(grid.options.parms, function(i, obj) {
			printPara[obj.name] = obj.value;
		});

		officeGridPrint(printPara);
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<div id="toptoolbar1"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><font
				size="2" color="red">*</font>会计期间：</td>
			<td><table><tr>
				<td align="left" class="l-table-edit-td">
					<input class="Wdate" name="acc_year_month1" type="text" 
						id="acc_year_month1" ltype="text" style="width: 90px;" />
				</td>
				<td align="left" class="l-table-edit-td">至</td>
				<td align="left" class="l-table-edit-td">
					<input class="Wdate" name="acc_year_month2" type="text" 
						id="acc_year_month2" ltype="text" style="width: 90px;" />
				</td>
				<td align="left"></td>
			</tr></table></td>	
			<td align="left" class="l-table-edit-td" style="padding-left: 10px;"><font
				size="2" color="red">*</font>科目名称:</td>
			<td align="left" class="l-table-edit-td"><input name="subj_name"
				type="text" id="subj_name" ltype="text" /></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td"><font size="2"
				color="red">*</font>多栏方案：</td>
			<td align="left" class="l-table-edit-td"><input name="plan_code"
				type="text" id="plan_code" ltype="text" /></td>
			<td align="right" class="l-table-edit-td">币种：</td>
			<td align="left" class="l-table-edit-td" colspan="2"><input
				name="cur_code" type="text" id="cur_code" ltype="text" /></td>
			<td align="left" class="l-table-edit-td"><input name="is_state"
				type="checkbox" id="is_state" ltype="text" checked="checked" />包含未记账</td>
		</tr>
	</table>
	<div id="maingrid"></div>
</body>
</html>
