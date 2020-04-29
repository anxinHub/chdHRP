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
<script src="<%=path%>/lib/hrp/budg/budg.js" type="text/javascript"></script>
<script type="text/javascript">
	var grid;
	var gridManager = null;
	var budg_year;
	var dialog = frameElement.dialog;
	$(function() {
		loadDict();//加载下拉框
		loadHead(null);
		query();
		loadForm();
		loadHotkeys();
		if ('${state}' != 0) {//置黑不能点击
			toobarmanage = gridManager.toolbarManager;
			
			toobarmanage.setDisabled('save');
			toobarmanage.setDisabled('delete');
			toobarmanage.setDisabled('importSave');
		
		}

		
	});
	
	 function changeCreateDate() {

		editor.grid.url = '../../../queryAssCardTable.do?isCheck=false&ass_nature=02&use_state=1,2,3,4,5,6&in_date='
				+ $("#create_date").val();
	} 

	function save() {
		gridManager.endEdit();
		if(!$("form").valid()){
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
			dis_r_no : $("#dis_r_no").val() == "" ? '0' : $(
					"#dis_r_no").val(),
			dispose_type : liger.get("dispose_type").getValue(),
			/* store_id : liger.get("store_id").getValue().split("@")[0],
			store_no : liger.get("store_id").getValue().split("@")[1],
			ven_id : liger.get("ven_id").getValue().split("@")[0],
			ven_no : liger.get("ven_id").getValue().split("@")[1], */
			note : $("#note").val(),
			create_date : $("#create_date").val(),
			ParamVo : JSON.stringify(data)
		};
		if (validateGrid()) {
			ajaxJsonObjectByUrl("saveAssDisposalRecordSpecial.do", formPara,
					function(responseData) {
						if (responseData.state == "true") {
							$("#dis_r_no").val(responseData.dis_r_no);
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
			name : 'dis_r_no',
			value : $("#dis_r_no").val() == "" ? "0" : $("#dis_r_no")
					.val()
		});
		grid.loadData(grid.where);
		is_addRow();
	}
	
	function cardSelect(){
		var data = gridManager.getData();
		var ass_card_nos = [];
		$.each(data, function(i, v) {
			if (!isnull(v.ass_card_no)) {
				ass_card_nos.push("'"+v.ass_card_no+"'");
			}
		});
		
		var str = "select * from ASS_DISPOSAL_R_DETAIL_SPECIAL p "+
		  " left join ASS_DISPOSAL_R_SPECIAL pp "+
		  "  on pp.group_id = p.group_id "+
		  " and pp.hos_id = p.hos_id "+
		  " and pp.copy_code = p.copy_code "+
		  " and pp.dis_r_no = p.dis_r_no "+
		  " where p.group_id = a.group_id "+
		  " and p.hos_id = a.hos_id "+
		  " and p.ass_card_no = a.ass_card_no "+
		  " and pp.state = 0 ";
		
		editor.grid.url = '../../../queryAssCardTable.do?isCheck=false&ass_nature=02&use_state=1,2,3,4,5,6&in_date='
			+ $("#create_date").val()+'&ass_card_not_exists='+ass_card_nos.toString()+"&sql="+str;

	}

	function loadHead() {
		
		/* var store_id = liger.get("store_id").getValue().split("@")[0];
		var store_no = liger.get("store_id").getValue().split("@")[1];

		if (store_no == null || store_id == null || store_id == ""
				|| store_no == "") {
			store_no = "";
			store_id = "";
		}
		
		var ven_id = liger.get("ven_id").getValue().split("@")[0];
		var ven_no = liger.get("ven_id").getValue().split("@")[1];

		if (ven_no == null || ven_id == null || ven_id == ""
				|| ven_no == "") {
			ven_no = "";
			ven_id = "";
		} */
		
		editor = {
			type : 'select',
			valueField : 'ass_card_no',
			textField : 'ass_card_no',
			selectBoxWidth : 1000,
			selectBoxHeight : 240,
			grid : {
				columns : [ {
					display : '卡片编码',
					name : 'ass_card_no',
					align : 'left'
				}, {
					display : '原始卡片号',
					name : 'ass_ori_card_no',
					align : 'left'
				},{
					display : '资产编码',
					name : 'ass_code',
					align : 'left'
				}, {
					display : '资产名称',
					name : 'ass_name',
					align : 'left'
				}, {
					display : '资产规格',
					name : 'ass_spec',
					align : 'left'
				}, {
					display : '资产型号',
					name : 'ass_model',
					align : 'left'
				}, {
					display : '资产品牌',
					name : 'ass_brand',
					align : 'left'
				}, {
					display : '计量单位',
					name : 'ass_unit_name',
					align : 'left',
					width : 50
				}, {
					display : '生产厂商',
					name : 'fac_name',
					align : 'left'
				}, {
					display : '供应商',
					name : 'ven_name',
					align : 'left'
				}, {
					display : '部门',
					name : 'dept_name',
					align : 'left'
				}, {
					display : '仓库',
					name : 'store_name',
					align : 'left'
				},{
					display : '入库时间',
					name : 'in_date',
					align : 'left'
				},{
					display : '资产原值',
					name : 'price',
					align : 'left'
				}, {
					display : '累计折旧',
					name : 'depre_money',
					align : 'left'
				}, {
					display : '累计分摊',
					name : 'now_manage_depre',
					align : 'left'
				},{
					display : '资产原值',
					name : 'price',
					align : 'left',
					render : function(rowdata, rowindex,
							value) {
						 return formatNumber(
									rowdata.price == null ? 0
											: rowdata.price,
									'${ass_05006}',
									1);
					}
					
				}, {
					display : '累计折旧',
					name : 'depre_money',
					align : 'left',
					render : function(rowdata, rowindex,
							value) {
						 return formatNumber(
									rowdata.depre_money == null ? 0
											: rowdata.depre_money,
									'${ass_05005}',
									1);
					}
					
				}, {
					display : '累计分摊',
					name : 'manage_depre_money',
					align : 'left',
					render : function(rowdata, rowindex,
							value) {
						 return formatNumber(
									rowdata.manage_depre_money == null ? 0
											: rowdata.manage_depre_money,
									'${ass_05005}',
									1);
					}
					
				}, {
					display : '资产净值',
					name : 'cur_money',
					align : 'left',
					render : function(rowdata, rowindex,
							value) {
						 return formatNumber(
									rowdata.cur_money == null ? 0
											: rowdata.cur_money,
									'${ass_05005}',
									1);
					}
					
				}, {
					display : '预留残值',
					name : 'fore_money',
					align : 'left',
					render : function(rowdata, rowindex,
							value) {
						 return formatNumber(
									rowdata.fore_money == null ? 0
											: rowdata.fore_money,
									'${ass_05005}',
									1);
					}
					
				}  ,
				 {
					display : '累计折旧月份',
					name : 'add_depre_month',
					align : 'left',
					width : 80
				} ,
				 {
					display : '累计分摊月份',
					name : 'add_manage_month',
					align : 'left',
					width : 80
				}],
				switchPageSizeRecordComboBox : false,
				onSelectRow : f_onSelectRow_detail,
				url : '../../../queryAssCardTable.do?isCheck=false&ass_nature=02&use_state=1,2,3,4,5,6&in_date='
					+ $("#create_date").val()/* +'&ven_id='+ven_id+'&ven_no='+ven_no+'&store_id='+store_id+'&store_no='+store_no */,
				pageSize : 30
			},
			alwayShowInDown : false,
			keySupport : true,
			autocomplete : true,
			onSuccess : function() {
				this.parent("tr").next(".l-grid-row").find("td:first").focus();
			},
			onBeforeOpen: cardSelect
		};

		grid = $("#maingrid")
				.ligerGrid(
						{
							columns : [
									{
										display : '卡片编码',
										name : 'ass_card_no',
										align : 'left',
										textField : 'ass_card_no',
										valueField : 'ass_card_no',
										editor : editor,
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
										display : '规格',
										name : 'ass_spec',
										align : 'left',
										width : '150'
									}, {
										display : '型号',
										name : 'ass_model',
										align : 'left',
										width : '150'
									}, {
										display : '科室',
										name : 'dept_name',
										align : 'left',
										width : '150'
									}, {
										display : '仓库',
										name : 'store_name',
										align : 'left',
										width : '150'
									}, {
										display : '资产原值',
										name : 'price',
										align : 'left',
										width : '120',
										render : function(rowdata, rowindex,value) {
											return formatNumber(
											rowdata.price == null ? 0: rowdata.price,'${ass_05006}',1);
											}
									}, {
										display : '本期折旧',
										name : 'now_depre',
										align : 'left',
										width : '120',
										render : function(rowdata, rowindex,value) {
											return formatNumber(
											rowdata.now_depre == null ? 0: rowdata.now_depre,'${ass_05005}',1);
											}
									}, {
										display : '本期分摊',
										name : 'now_manage_depre',
										align : 'left',
										width : '120',
										render : function(rowdata, rowindex,value) {
											return formatNumber(
											rowdata.now_manage_depre == null ? 0: rowdata.now_manage_depre,'${ass_05005}',1);
											}
									}, {
										display : '累计折旧',
										name : 'add_depre',
										align : 'left',
										width : '120',
										render : function(rowdata, rowindex,value) {
											return formatNumber(
											rowdata.add_depre == null ? 0: rowdata.add_depre,'${ass_05005}',1);
											}
									}, 
									{
										display : '累计分摊',
										name : 'manage_depre_money',
										align : 'left',
										width : '120',
										render : function(rowdata, rowindex,value) {
											return formatNumber(
											rowdata.manage_depre_money == null ? 0: rowdata.manage_depre_money,'${ass_05005}',1);
											}
									}, {
										display : '资产净值',
										name : 'cur_money',
										align : 'left',
										width : '120',
										render : function(rowdata, rowindex,value) {
											return formatNumber(
											rowdata.cur_money == null ? 0: rowdata.cur_money,'${ass_05006}',1);
											}
									}, {
										display : '预留残值',
										name : 'fore_money',
										align : 'left',
										width : '120',
										render : function(rowdata, rowindex,value) {
											return formatNumber(
											rowdata.fore_money == null ? 0: rowdata.fore_money,'${ass_05006}',1);
											}
									}, {
										display : '处置费用',
										name : 'dispose_cost',
										align : 'right',
										editor : {
											type : 'float'
										},
										width : '80',
										render : function(rowdata, rowindex,value) {
											return formatNumber(
											rowdata.dispose_cost == null ? 0: rowdata.dispose_cost,'${ass_05005}',1);
											}
									}, {
										display : '处置收入',
										name : 'dispose_income',
										align : 'right',
										editor : {
											type : 'float'
										},
										width : '80',
										render : function(rowdata, rowindex,value) {
											return formatNumber(
											rowdata.dispose_income == null ? 0: rowdata.dispose_income,'${ass_05005}',1);
											}
									}, {
										display : '应缴税费',
										name : 'dispose_tax',
										align : 'right',
										editor : {
											type : 'float'
										},
										width : '80',
										render : function(rowdata, rowindex,value) {
											return formatNumber(
											rowdata.dispose_tax == null ? 0: rowdata.dispose_tax,'${ass_05005}',1);
											}
									},  {
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
							url : 'queryAssDisposalRecordDetailSpecial.do?isCheck=false',
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
									text : '删除',
									id : 'delete',
									click : remove,
									icon : 'delete'
								},{
									line : true
								},{
									text :'批量保存',
									id : 'importSave',
									click : importSave,
									up : 'up'
								}, {
									line : true
								}, {
									text : '关闭',
									id : 'close',
									click : this_close,
									icon : 'candle'
								},{
									line : true
								},
								{ text: '模板设置', id:'printSet', click: printSet, icon:'print' },
								{ line:true } ,
								{ text: '打印', id:'print', click: print, icon:'print' }]
							}
						});

		gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	function importSave(){
		if ($("#create_date").val() == "") {
			$.ligerDialog.error('制单日期不能为空');
			return;
		}
		if (liger.get("dispose_type").getValue() == "") {
			$.ligerDialog.error('处置类型不能为空');
			return;
		}
		
		var param = 
				"dispose_type="+liger.get("dispose_type").getValue()  +"&"
				+"dispose_type_name="+liger.get("dispose_type").getText() +"&"
				+"create_date="+$("#create_date").val()+"&"
				+"note="+$("#note").val();
		$.ligerDialog.open({
			title: '批量增加',
			height: $(window).height(),
			width: $(window).width(),
			url: 'assImportSpecialPage.do?isCheck=false&'+param,
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
	}
	
	function openCardUpdate(obj) {
		var vo = obj.split("|");
		var parm = "group_id=" + vo[0] + "&" + "hos_id=" + vo[1] + "&"
				+ " copy_code=" + vo[2] + "&" + "ass_card_no=" + vo[3] + "&"
				+ "ass_nature=02";
		parent.$.ligerDialog.open({
			title : '资产卡片维护',
			height : $(window).height(),
			width : $(window).width(),
			url : 'hrp/ass/asscard/assInCardUpdatePage.do?isCheck=false&'
					+ parm,
			modal : true,
			showToggle : false,
			showMax : true,
			showMin : false,
			isResize : true,
			slide : false,
			parentframename : window.name, //用于parent弹出层调用本页面的方法或变量
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
		if (column_name == "ass_card_no") {
			if (selectData != "" || selectData != null) {
				//回充数据 
				//grid.updateCell('record_emp', 100, e.record);
				grid.updateRow(rowindex_id, {
					ass_code : data.ass_code,
					ass_name : data.ass_name,
					price : data.price,
					add_depre : data.depre_money,
					manage_depre_money : data.manage_depre_money,
					cur_money : data.cur_money,
					fore_money : data.fore_money,
					add_depre_month : data.add_depre_month,
					ass_spec : data.ass_spec,
					ass_model : data.ass_model,
					dept_name : data.dept_name,
					store_name : data.store_name
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
		
		grid.updateTotalSummary();
	}
	function initCard() {

	}

	function validateGrid() {
		var data = gridManager.getData();
		var msg = "";
		var targetMap = new HashMap();
		var msgMap = new HashMap();
		//删除空行

		$.each(data, function(i, v) {
			if (!isnull(v.ass_card_no)) {
				var key = v.ass_card_no;
				var value = "第" + (i + 1) + "行";
				if (msg != "") {
					msgMap.put(value + msg + "不能为空或不能为零！\n\r", "");
				}
				if (isnull(targetMap.get(key))) {
					targetMap.put(key, value);
				} else {
					msg = targetMap.get(key) + "与" + value + "重复！\n\r", value;
					msgMap.put(targetMap.get(key) + "与" + value + "重复！\n\r",
							value);
				}
			}
		});
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
							ParamVo.push(this.group_id + "@" + this.hos_id
									+ "@" + this.copy_code + "@"
									+ this.dis_r_no + "@" + this.ass_card_no);
						}
						i++;
					});
			if (ParamVo == "") {
				return;
			}
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteAssDisposalRecordDetailSpecial.do", {
						ParamVo : ParamVo.toString()
					}, function(responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			});
		}
	}
	
	function loadForm() {

		$.metadata.setType("attr", "validate");
		var v = $("form").validate({
			errorPlacement : function(lable, element) {
				if (element.hasClass("l-textarea")) {
					element.ligerTip({
						content : lable.html(),
						target : element[0]
					});
				} else if (element.hasClass("l-text-field")) {
					element.parent().ligerTip({
						content : lable.html(),
						target : element[0]
					});
				} else {
					lable.appendTo(element.parents("td:first").next("td"));
				}
			},
			success : function(lable) {
				lable.ligerHideTip();
				lable.remove();
			},
			submitHandler : function() {
				$("form .l-text,.l-textarea").ligerHideTip();
			}
		});
		//$("form").ligerForm();
	}
	
	//打印模板设置
    function printSet(){
	  
    	var useId=0;//统一打印
		if('${ass_05023}'==1){
			//按用户打印
			useId='${user_id }';
		}else if('${ass_05023}'==2){
			//按仓库打印
			if(liger.get("store_id").getValue()==""){
				$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
				return;
			}
			useId=liger.get("store_id").getValue().split(",")[0];
		}
    	
		officeFormTemplate({template_code:"05023",use_id : useId});
    }

	
	//打印
    function print(){
    	var useId=0;//统一打印
 		if('${ass_05023}'==1){
 			//按用户打印
 			useId='${user_id }';
 		}else if('${ass_05023}'==2){
 			//按仓库打印
 			if(liger.get("store_id").getValue()==""){
 				$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
 				return;
 			}
 			useId=liger.get("store_id").getValue().split(",")[0];
 		}
 		
	    var para={
	    	dis_r_no:$("#dis_r_no").val(),
	    	class_name:"com.chd.hrp.ass.serviceImpl.assdisposal.special.AssDisposalRecordSpecialServiceImpl",
			method_name:"assDisposalRecordSpecialByPrintTemlate",
			template_code:'05023',
   			isPrintCount:false,//更新打印次数
   			isPreview:false,//预览窗口，传绝对路径
   			use_id:useId,
   			p_num:0
   			//isSetPrint:flag
   	    }; 
	 
       	ajaxJsonObjectByUrl("queryState.do?isCheck=false",{dis_r_no:$("#dis_r_no").val()},function (responseData){
       		if(responseData.state=="true"){
       		   officeFormPrint(para);
       		}
       	});
    }
	
	
	//键盘事件
	function loadHotkeys() {

		hotkeys('A', save);
		hotkeys('D', remove);

	}
	function is_addRow() {
		setTimeout(function() { //当数据为空时 默认新增一行
			grid.addRow();
		}, 1000);
	}
	function loadDict() {
		//处置类型
		autocomplete("#dispose_type", "../../../queryAssDisposeTypeDict.do?isCheck=false&dispose_type_codes=41,51,31,32", "id",
				"text", true, true, "", false, "${dispose_type}", 140);
		
		/* autocomplete("#store_id", "../../../queryHosStoreDict.do?naturs_code=02&isCheck=false",
				"id", "text", true, true, null, true, "${store_id}", "140");
		
		autocomplete("#ven_id", "../../../queryHosSupDict.do?isCheck=false", "id",
				"text", true, true, null, true, "${ven_id}", "140"); */
		
		//记录单号
		$("#dis_r_no").ligerTextBox({disabled:true,cancelable: false,width : 140});
		$("#create_date").ligerTextBox({width : 120});
		//autodate("#create_date");
	}
</script>

</head>

<body onload="is_addRow()">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post" id="form1">
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 50px;"><b><font color="red">*</font></b>记录单号：</td>
			<td align="left" class="l-table-edit-td">
				<input name="dis_r_no" disabled="disabled" type="text" id="dis_r_no" value="${dis_r_no }"/>
			</td>
			<td align="left"></td>
			
			<td align="right" class="l-table-edit-td" style="padding-left: 50px;"><b><font color="red">*</font></b>处置类型：</td>
			<td align="left" class="l-table-edit-td">
				<input name="dispose_type" type="text" id="dispose_type"  ltype="text" validate="{required:true}"/>
			</td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 50px;"><b><font
					color="red">*</font></b>制单日期：</td>
			<td align="left" class="l-table-edit-td"><input
				name="create_date" type="text" id="create_date" class="Wdate" value="${create_date }"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd',onpicked:changeCreateDate})" /></td>
			<!-- <td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">仓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;库：</td>
			<td align="left" class="l-table-edit-td"><input name="store_id"
				type="text" id="store_id" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">供&nbsp;&nbsp;应&nbsp;&nbsp;商：</td>
			<td align="left" class="l-table-edit-td"><input name="ven_id"
				type="text" id="ven_id" /></td>
			<td align="left"></td>
		</tr>
		<tr>	 -->
			<td align="right" class="l-table-edit-td" style="padding-left: 50px;">备注：</td>
			<td align="left" class="l-table-edit-td" colspan="9"><textarea
					rows="2" cols="50" id="note" name="note">${note}</textarea></td>
			<td align="left"></td>
			<td align="left"></td>
		</tr>
	</table>
	</form>
	<div id="maingrid"></div>
	
</body>
</html>
