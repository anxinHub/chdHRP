<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<script src="<%=path%>/lib/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js" type="text/javascript"></script>
<script src="<%=path%>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/core/base.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path%>/lib/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src='<%=path%>/lib/hrp/acc/superReport/ktLayer.js'  type="text/javascript"></script>
<script src="<%=path%>/lib/layer-v2.3/layer/layer.js" type="text/javascript"></script>
<script src="<%=path%>/lib/jquery-validation/jquery.validate.min.js" type="text/javascript"></script>
<script src="<%=path%>/lib/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
<script src="<%=path%>/lib/jquery-validation/messages_cn.js" type="text/javascript"></script>
<script type="text/javascript">
	var grid, subjGrid;
	var gridManager = null;
	var year_month = '${acc_year_month}';
	var userUpdateStr;

	$(function() {
		//布局
		$("#layout1").ligerLayout({ leftWidth: 265});
		$("#navtab1").ligerTab({contextmenu: false});
		loadSubjGrid();
		$("#navtab1").hide();
		loadForm();
		loadDict();
		loadHead(null); //加载数据
		query();
	});
	
	//查询模板信息
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'acc_year',
			value : liger.get("acc_year_month").getValue().split(".")[0]
		});
		grid.options.parms.push({
			name : 'acc_month',
			value : liger.get("acc_year_month").getValue().split(".")[1]
		});
		grid.options.parms.push({
			name : 'template_type_code',
			value : "Z009"
		});
		//加载查询条件
		grid.loadData(grid.where);
	}
	
	//加载模板主表信息
	function queryMain(rowdata) {
		rowdata = JSON.parse(rowdata);
		$("#template_id").val(rowdata.template_id);
		$("#template_type_code").val(rowdata.template_type_code);
		$("#debit1").show();
		switch (rowdata.template_type_code){
			case "Z00901":
				$("#debit_text1").html("借：　　　　　本期盈余：");
				$("#credit_text1").html("贷：　　　　　盈余分配：");
				$("#debit2").hide();
				$("#credit2").hide();
				$("#budg").show();
				$("#debit3").show();
				$("#credit3").show();
				$("#debit4").show();
				$("#credit4").hide();
				$("#debit5").show();
				$("#debit_text3").html("借：非财政拨款医疗结转：");
				$("#debit_text4").html("借：　　　　　经营结余：");
				$("#debit_text5").html("借：　　　　　其他结余：");
				$("#credit_text3").html("贷：非财政拨款结余分配：");
				$("#rate_th").hide();
				$("#rate_td").hide();
				$("#cw_btn_tr").show();
				$("#ys_btn_tr").show();
				var paras ={
					subj_type_code : "08",
					is_last : "1", 
					kind_code: "02"
				};
				autocompleteAsync("#debit_subj_code3", "../../../querySubjAll.do?isCheck=false", "id", "text", true, true, paras, false, "", "360", "", subjWidth);
				autocompleteAsync("#debit_subj_code4", "../../../querySubjAll.do?isCheck=false", "id", "text", true, true, paras, false, "", "360", "", subjWidth);
				autocompleteAsync("#debit_subj_code5", "../../../querySubjAll.do?isCheck=false", "id", "text", true, true, paras, false, "", "360", "", subjWidth);
				autocompleteAsync("#credit_subj_code3", "../../../querySubjAll.do?isCheck=false", "id", "text", true, true, paras, false, "", "360", "", subjWidth);
				$("#navtab1").hide();
				break;
			case "Z00902":
				$("#debit_text1").html("借：　　　　　盈余分配：");
				$("#credit_text1").html("贷：　　　提取专用基金：");
				$("#debit2").hide();
				$("#credit2").hide();
				$("#budg").show();
				$("#debit3").show();
				$("#credit3").show();
				$("#debit4").hide();
				$("#credit4").hide();
				$("#debit5").hide();
				$("#debit_text3").html("借：非财政拨款结余分配：");
				$("#credit_text3").html("贷：　　　提取专用结余：");
				$("#rate_th").show();
				$("#rate_td").show();
				$("#cw_btn_tr").hide();
				$("#ys_btn_tr").hide();
				$("#rate").ligerTextBox({width:90, disabled: false });
				var paras ={
					subj_type_code : "08",
					is_last : "1", 
					kind_code: "02"
				};
				autocompleteAsync("#debit_subj_code3", "../../../querySubjAll.do?isCheck=false", "id", "text", true, true, paras, false, "", "360", "", subjWidth);
				autocompleteAsync("#credit_subj_code3", "../../../querySubjAll.do?isCheck=false", "id", "text", true, true, paras, false, "", "360", "", subjWidth);
				$("#navtab1").hide();
				break;
			case "Z00903":
				$("#debit_text1").html("借：　　　提取专用基金：");
				$("#credit_text1").html("贷：　　　　　专用基金：");
				$("#debit2").hide();
				$("#credit2").hide();
				$("#budg").show();
				$("#debit3").show();
				$("#credit3").show();
				$("#debit4").hide();
				$("#credit4").hide();
				$("#debit5").hide();
				$("#debit_text3").html("借：　　　提取专用结余：");
				$("#credit_text3").html("贷：　　　　　专用结余：");
				$("#rate_th").hide();
				$("#rate_td").hide();
				$("#cw_btn_tr").hide();
				$("#ys_btn_tr").hide();
				var paras ={
					subj_type_code : "08",
					is_last : "1", 
					kind_code: "02"
				};
				autocompleteAsync("#debit_subj_code3", "../../../querySubjAll.do?isCheck=false", "id", "text", true, true, paras, false, "", "360", "", subjWidth);
				autocompleteAsync("#credit_subj_code3", "../../../querySubjAll.do?isCheck=false", "id", "text", true, true, paras, false, "", "360", "", subjWidth);
				$("#navtab1").hide();
				break;
			case "Z00904":
				$("#debit_text1").html("借：　　　　　盈余分配：");
				$("#credit_text1").html("贷：　　　　　累计盈余：");
				$("#debit2").hide();
				$("#credit2").hide();
				$("#budg").show();
				$("#debit3").show();
				$("#credit3").show();
				$("#debit4").hide();
				$("#credit4").hide();
				$("#debit5").hide();
				$("#debit_text3").html("借：非财政拨款结余分配：");
				$("#credit_text3").html("贷：　　　　　累计结余：");
				$("#rate_th").hide();
				$("#rate_td").hide();
				$("#cw_btn_tr").show();
				$("#ys_btn_tr").show();
				var paras ={
					subj_type_code : "08",
					is_last : "1", 
					kind_code: "02"
				};
				autocompleteAsync("#debit_subj_code3", "../../../querySubjAll.do?isCheck=false", "id", "text", true, true, paras, false, "", "360", "", subjWidth);
				autocompleteAsync("#credit_subj_code3", "../../../querySubjAll.do?isCheck=false", "id", "text", true, true, paras, false, "", "360", "", subjWidth);
				$("#navtab1").hide();
				break;
			case "Z00905":
				$("#debit_text1").html("借：　　无偿调拨净资产：");
				$("#credit_text1").html("贷：　　　　　累计盈余：");
				$("#debit2").hide();
				$("#credit2").hide();
				$("#budg").hide();
				$("#debit3").hide();
				$("#credit3").hide();
				$("#debit4").hide();
				$("#credit4").hide();
				$("#debit5").hide();
				$("#rate_th").hide();
				$("#rate_td").hide();
				$("#cw_btn_tr").hide();
				$("#ys_btn_tr").hide();
				$("#navtab1").hide();
				break;
			case "Z00906":
				$("#debit_text1").html("借：　以前年度盈余调整：");
				$("#credit_text1").html("贷：　　　　　累计盈余：");
				$("#debit2").hide();
				$("#credit2").hide();
				$("#budg").hide();
				$("#debit3").hide();
				$("#credit3").hide();
				$("#debit4").hide();
				$("#credit4").hide();
				$("#debit5").hide();
				$("#rate_th").hide();
				$("#rate_td").hide();
				$("#cw_btn_tr").hide();
				$("#ys_btn_tr").hide();
				$("#navtab1").hide();
				break;
			case "Z00907":
				//$("#debit_text1").html("借：　　　新旧转换盈余：");
				$("#credit_text1").html("贷：　　　　　累计盈余：");
				$("#debit1").hide();
				$("#debit2").hide();
				$("#credit2").hide();
				$("#budg").hide();
				$("#debit3").hide();
				$("#credit3").hide();
				$("#debit4").hide();
				$("#credit4").hide();
				$("#debit5").hide();
				$("#rate_th").hide();
				$("#rate_td").hide();
				$("#cw_btn_tr").hide();
				$("#ys_btn_tr").hide();
				$("#subjDiv").attr("title", "借：新旧转换盈余科目");
				$("#navtab1").show();
				querySubjGrid();
				break;
		}
		$("#template_name").val(rowdata.template_name);
		if(rowdata.vouch_type_code){
			liger.get("vouch_type_code").setValue(rowdata.vouch_type_code);
			liger.get("vouch_type_code").setText(rowdata.vouch_type_name);
		}
		$("#summary").val(rowdata.summary);
		if(rowdata.rate != null){
			$("#rate").val(rowdata.rate);
		}
		if(rowdata.debit_subj_code1){
			liger.get("debit_subj_code1").setValue(rowdata.debit_subj_code1);
			liger.get("debit_subj_code1").setText(rowdata.debit_subj_code1+" "+rowdata.debit_subj_name1);
		}else if(liger.get("debit_subj_code1")){
			liger.get("debit_subj_code1").setValue("");
			liger.get("debit_subj_code1").setText("");
		}
		if(rowdata.debit_subj_code2){
			liger.get("debit_subj_code2").setValue(rowdata.debit_subj_code2);
			liger.get("debit_subj_code2").setText(rowdata.debit_subj_code2+" "+rowdata.debit_subj_name2);
		}else if(liger.get("debit_subj_code2")){
			liger.get("debit_subj_code2").setValue("");
			liger.get("debit_subj_code2").setText("");
		}
		if(rowdata.debit_subj_code3){
			liger.get("debit_subj_code3").setValue(rowdata.debit_subj_code3);
			liger.get("debit_subj_code3").setText(rowdata.debit_subj_code3+" "+rowdata.debit_subj_name3);
		}else if(liger.get("debit_subj_code3")){
			liger.get("debit_subj_code3").setValue("");
			liger.get("debit_subj_code3").setText("");
		}
		if(rowdata.debit_subj_code4){
			liger.get("debit_subj_code4").setValue(rowdata.debit_subj_code4);
			liger.get("debit_subj_code4").setText(rowdata.debit_subj_code4+" "+rowdata.debit_subj_name4);
		}else if(liger.get("debit_subj_code4")){
			liger.get("debit_subj_code4").setValue("");
			liger.get("debit_subj_code4").setText("");
		}
		if(rowdata.debit_subj_code5){
			liger.get("debit_subj_code5").setValue(rowdata.debit_subj_code5);
			liger.get("debit_subj_code5").setText(rowdata.debit_subj_code5+" "+rowdata.debit_subj_name5);
		}else if(liger.get("debit_subj_code5")){
			liger.get("debit_subj_code5").setValue("");
			liger.get("debit_subj_code5").setText("");
		}
		if(rowdata.credit_subj_code1){
			liger.get("credit_subj_code1").setValue(rowdata.credit_subj_code1);
			liger.get("credit_subj_code1").setText(rowdata.credit_subj_code1+" "+rowdata.credit_subj_name1);
		}else if(liger.get("credit_subj_code1")){
			liger.get("credit_subj_code1").setValue("");
			liger.get("credit_subj_code1").setText("");
		}
		if(rowdata.credit_subj_code2){
			liger.get("credit_subj_code2").setValue(rowdata.credit_subj_code2);
			liger.get("credit_subj_code2").setText(rowdata.credit_subj_code2+" "+rowdata.credit_subj_name2);
		}else if(liger.get("credit_subj_code2")){
			liger.get("credit_subj_code2").setValue("");
			liger.get("credit_subj_code2").setText("");
		}
		if(rowdata.credit_subj_code3){
			liger.get("credit_subj_code3").setValue(rowdata.credit_subj_code3);
			liger.get("credit_subj_code3").setText(rowdata.credit_subj_code3+" "+rowdata.credit_subj_name3);
		}else if(liger.get("credit_subj_code3")){
			liger.get("credit_subj_code3").setValue("");
			liger.get("credit_subj_code3").setText("");
		}
		if(rowdata.credit_subj_code4){
			liger.get("credit_subj_code4").setValue(rowdata.credit_subj_code4);
			liger.get("credit_subj_code4").setText(rowdata.credit_subj_code4+" "+rowdata.credit_subj_name4);
		}else if(liger.get("credit_subj_code4")){
			liger.get("credit_subj_code4").setValue("");
			liger.get("credit_subj_code4").setText("");
		}
	}
	
	function loadForm(){
	    $.metadata.setType("attr", "validate");
	     var v = $("form").validate({
	         errorPlacement: function (lable, element)
	         {
	             if (element.hasClass("l-textarea"))
	             {
	                 element.ligerTip({ content: lable.html(), target: element[0] }); 
	             }
	             else if (element.hasClass("l-text-field"))
	             {
	                 element.parent().ligerTip({ content: lable.html(), target: element[0] });
	             }
	             else
	             {
	                 lable.appendTo(element.parents("td:first").next("td"));
	             }
	         },
	         success: function (lable)
	         {
	             lable.ligerHideTip();
	             lable.remove();
	         },
	         submitHandler: function ()
	         {
	             $("form .l-text,.l-textarea").ligerHideTip();
	         }
	     });
		//$("form").ligerForm();
	}  
	//模板Grid
	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '凭证模板', name : 'template_name', align : 'left', width: '140', 
				render : function(rowdata, rowindex, value) { 
					return '<a href=javascript:queryMain(\''+ JSON.stringify(rowdata) +'\')>'+rowdata.template_name+'</a>';
				}
			}, {
				display : '凭证类型', name : 'vouch_type_name', align : 'left', width: '60'
			}],
			dataAction : 'server', dataType : 'server', usePager : false, width : '100%', height : $(window).height()-1,
			url : 'queryAccBalanceAllocation.do',  checkbox : true,  isSingleCheck: true,  
			rownumbers : true, delayLoad: true, selectRowButtonOnly : true,//heightDiff: -10,
			/* toolbar : {
				items : [ {
					text : '模板添加', id : 'add', click : itemclick, icon : 'add'
				}, {
					line : true
				}, {
					text : '模板删除', id : 'delete', click : itemclick, icon : 'delete'
				}]
			}, */ 
			onDblClickRow : function(rowdata, rowindex, value) {
				queryMain(rowdata);
			}
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	function itemclick(item) {
		if (item.id) {
			switch (item.id) {
			case "add":
				$("#template_id").val("");
				$("#template_name").val("");
				$("#summary").val("");
				//grid.deleteAllRows();
				return;
			case "delete":
				var data = gridManager.getCheckedRows();
				if (data.length == 0) {
					$.ligerDialog.warn('请选择行');
				} else {
					var ParamVo = [];
					$(data).each(function() { 
						ParamVo.push(
						//表的主键
						this.template_id)
					});
					$.ligerDialog.confirm('确定删除?', function(yes) {
						if (yes) {
							ajaxJsonObjectByUrl("deleteAccBalanceAllocation.do", {
								ParamVo :  ParamVo.toString()
							}, function(responseData) {
								if (responseData.state == "true") {
									query();
								}
							});
						}
					});
				}
				return;
			}
		}
	}	
	
	//字典下拉框
	function loadDict() {
		$("#acc_year_month").ligerComboBox({
	      	url: '../../../queryYearMonth.do?isCheck=false',
	      	valueField: 'id',
	       	textField: 'text', 
	       	selectBoxWidth: 90,
	      	autocomplete: true,
	      	width: 90,
	      	onChangeValue: function(value){
	      		if(grid){
		      		query();
	      		}
	      	}
		});
		liger.get("acc_year_month").setValue(year_month.substring(0,4)+"."+year_month.substring(4,6).toString());
		liger.get("acc_year_month").setText(year_month.substring(0,4)+"."+year_month.substring(4,6).toString());
		
		var paras ={
				subj_type_code : "03",
				is_last : "1", 
				kind_code: "01"
			};
		autocomplete("#debit_subj_code1", "../../../querySubjAll.do?isCheck=false", "id", "text", true, true, paras, false, "", "360", "", subjWidth);
		autocomplete("#credit_subj_code1", "../../../querySubjAll.do?isCheck=false", "id", "text", true, true, paras, false, "", "360", "", subjWidth);

		autocomplete("#debit_subj_code2", "../../../querySubjAll.do?isCheck=false", "id", "text", true, true, paras, false, "", "360", "", subjWidth);
		autocomplete("#credit_subj_code2", "../../../querySubjAll.do?isCheck=false", "id", "text", true, true, paras, false, "", "360", "", subjWidth);
		
		paras ={
				subj_type_code : "08",
				is_last : "1", 
				kind_code: "01"
			};
		autocomplete("#debit_subj_code3", "../../../querySubjAll.do?isCheck=false", "id", "text", true, true, paras, false, "", "360", "", subjWidth);
		autocomplete("#debit_subj_code4", "../../../querySubjAll.do?isCheck=false", "id", "text", true, true, paras, false, "", "360", "", subjWidth);
		autocomplete("#debit_subj_code5", "../../../querySubjAll.do?isCheck=false", "id", "text", true, true, paras, false, "", "360", "", subjWidth);
		autocomplete("#credit_subj_code3", "../../../querySubjAll.do?isCheck=false", "id", "text", true, true, paras, false, "", "360", "", subjWidth);
		
		autocomplete("#vouch_type_code", "../../../queryVouchType.do?isCheck=false", "id", "text", true, true, "", true);
		
		$("#template_name").ligerTextBox({width:180,disabled: false });
		$("#vouch_type_code").ligerTextBox({width:85,disabled: false });
		$("#summary").ligerTextBox({width:180,disabled: false });
		$("#rate").ligerTextBox({width:85,number:true,disabled: false });
		
		//格式化按钮
		$("#btn_add").ligerButton({click: createVouch, width: 90});
		$("#btn_vouch").ligerButton({click: showVhouchList, width: 90});
		$("#btn_save").ligerButton({click: saveTemplate, width: 90});
		$("#btn_cw_add").ligerButton({click: createCwVouch, width: 120});
		$("#btn_ys_add").ligerButton({click: createYsVouch, width: 120});
	}

	//保存模板
	function saveTemplate(){
		if($("form").valid()){ 

			var template_type_code = $("#template_type_code").val();
			
			var formPara ={
				template_id : $("#template_id").val(),
				template_name : $("#template_name").val(),
				template_type_code: $("#template_type_code").val(),
				vouch_type_code : liger.get("vouch_type_code").getValue(),
				summary : $("#summary").val(),
				rate : template_type_code == "Z00902" ? $("#rate").val() : 0,
				debit_subj_code1 : liger.get("debit_subj_code1").getValue(),
				debit_subj_code2 : liger.get("debit_subj_code2") ? liger.get("debit_subj_code2").getValue() : "",
				debit_subj_code3 : liger.get("debit_subj_code3") ? liger.get("debit_subj_code3").getValue() : "",
				debit_subj_code4 : liger.get("debit_subj_code4") ? liger.get("debit_subj_code4").getValue() : "",
				debit_subj_code5 : liger.get("debit_subj_code5") ? liger.get("debit_subj_code5").getValue() : "",
				credit_subj_code1 : liger.get("credit_subj_code1").getValue(),
				credit_subj_code2 : liger.get("credit_subj_code2") ? liger.get("credit_subj_code2").getValue() : "",
				credit_subj_code3 : liger.get("credit_subj_code3") ? liger.get("credit_subj_code3").getValue() : "",
				credit_subj_code4 : liger.get("credit_subj_code4") ? liger.get("credit_subj_code4").getValue() : ""
			};
			
			if(template_type_code == "Z00907"){
				var data = subjGrid.getData();
				if(data.length == 0){
					$.ligerDialog.error('请选择转出科目！');
					return false ;
				}
				formPara.detailData = JSON.stringify(data);
			}
			ajaxJsonObjectByUrl("saveAccBalanceAllocation.do",formPara,function (responseData){
        		if(responseData.state=="true"){
        			query();
        		}
        	});
		} 
	}
	
	//查询模板明细信息
	function querySubjGrid() {
		subjGrid.options.parms = [];
		subjGrid.options.newPage = 1;
		//根据表字段进行添加查询条件
		subjGrid.options.parms.push({
			name : 'template_id',
			value : $("#template_id").val()
		});
		//加载查询条件
		subjGrid.loadData(subjGrid.where);
	}
	
	//科目Grid
	function loadSubjGrid() {
		//subjGrid = $("#subjgrid").css({"margin-left" : 40}).ligerGrid({
		subjGrid = $("#subjgrid").ligerGrid({
			columns : [ {
				display : '科目编码', name : 'subj_code', align : 'left', width: "120"
			}, {
				display : '科目名称', name : 'subj_name_all', align : 'left'
			}],
			dataAction : 'server', dataType : 'server', width : '100%', height : '100%',
			usePager : false, url : '../../queryAccTermendTemplateDetail.do?isCheck=false',
			checkbox : true, rownumbers : false, delayLoad: true, selectRowButtonOnly : true,//heightDiff: -10,
			toolbar : {
				items : [ {
					text : '科目设置', id : 'add', click : setSubj, icon : 'add'
				}, {
					line : true
				}, {
					text : '科目删除', id : 'delete', click : removeSubj, icon : 'delete'
				} ] 
			},
		});
	}
		
	//设置科目   通过设置科目 点击确定来进行保存主表和明细表
	var subjList;
	function setSubj(){
		if ($("#template_name").val() == "" ){  
			$.ligerDialog.error('模板名称不能为空！');
			return false ;
		}
		 
		if ($("#summary").val() == "" ){
			$.ligerDialog.error('摘要不能为空！');
			return false ;
		}

		parent.$.ligerDialog.open({ 
			title: '转出科目设置', 
			width: $(window).width() - 20,  
			height: $(window).height() - 50, 
			url: 'hrp/acc/termend/accTermendTemplateSubjPage.do?isCheck=false&subj_type_code=03', 
			modal: true, showToggle: false, showMax: true, showMin: true, isResize: true,
			buttons: [
				{ text: '确定', onclick: writeSubj, cls:'l-dialog-btn-highlight' },
				{ text: '取消', onclick: closeDialog }
			]
		}); 
	}
	//写入选择的科目  进行保存操作
	function writeSubj(item, dialog){ 
		var rows = dialog.frame.getSelectRows();
        if (!rows){
            alert('请选择行!');
            return;
        }  
		//写入数据 
    	subjGrid.addRows(rows); 
		//保存数据 
    	saveTemplate();
        dialog.close();
    }	
	//关闭选择科目窗口
    function closeDialog(item, dialog){ 
        dialog.close();
    }

	//科目删除
	function removeSubj(){
		subjGrid.deleteSelectedRow();
	}
	
	//生成财务凭证
	function createCwVouch(){
		createVouch("01");
	}
	
	//生成预算凭证
	function createYsVouch(){
		createVouch("02");
	}
	
	//生成凭证
	function createVouch(create_kind){
		var rows = [];
		if(create_kind){
			rows.push({
				template_id: $("#template_id").val(), 
				vouch_type_code: liger.get("vouch_type_code").getValue()
			})
		}else{
			rows = gridManager.getCheckedRows();
			if (rows.length == 0) {
				$.ligerDialog.warn('请选择行');
	            return;
	        } 
			create_kind = "";
		}
		//如选多个模板则循环生成凭证
		$.ligerDialog.confirm('确定生成凭证?', function (yes){
	        if(yes){
	        	var para;
	        	for (var i = 0; i < rows.length; i++){
		        	template_ids = rows[i].template_id + ",";
		           
		        	para = {
		    			acc_year : liger.get("acc_year_month").getValue().split(".")[0],
		    			acc_month : liger.get("acc_year_month").getValue().split(".")[1],
		        		vouch_type_code : rows[i].vouch_type_code,
		        		create_kind : create_kind, 
		        		template_id : rows[i].template_id
		        	}
			
	        		var loadIndex = layer.load(1);
	        		ajaxJsonObjectBylayer("addAccBalanceAllocationVouch.do",para,function(responseData){
	        			layer.close(loadIndex); 
	        			var paraVouch={
	        				url:'hrp/acc/accvouch/superVouch/superVouchMainPage.do?isCheck=false&openType=autoVouch',
	        				title:'会计凭证',
	        				width:0,
	        				height:0,
	        				isShowMin:true,
	        				isModal:true,
	        				data:{auto_id: responseData.vouch_id, busi_log_table:'ACC_BUSI_LOG_ZZ', busi_type_code:'Z009',busi_no:para.template_id}
	        			};
	        			parent.openDialog(paraVouch);
	          		},layer,loadIndex);
	        	}
        	} 
        });
	}
	//凭证维护
	function showVhouchList(){
		parent.$.ligerDialog.open({ 
			title: '凭证维护', 
			width: $(window).width() - 20, 
			height: $(window).height() - 50, 
			url: 'hrp/acc/termend/accTermendTemplateVouchPage.do?isCheck=false&template_type_code=Z009&acc_year='+liger.get("acc_year_month").getValue().split(".")[0]+'&acc_month='+liger.get("acc_year_month").getValue().split(".")[1],
			model: true, showMax: false, showToggle: false, showMin: false, isResize: true
		}); 
	}
	
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="topmenu"></div>
	<div id="layout1" style="width:100%;margin:0; padding:0;">
		<div id="maingrid" position="left" title="模板列表"></div>
		<div position="center" title="模板信息">
			<table cellpadding="0" cellspacing="0" class="l-table-edit">
				<tr>
					<td align="right" class="l-table-edit-td" style="padding-left: 20px;">会计期间：</td>
					<td align="left" class="l-table-edit-td"><input name="acc_year_month"
						type="text" id="acc_year_month" ltype="text"
						validate="{required:true}" /></td>
					<td align="left"></td>
					<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
						<input  type="button" id="btn_add" accessKey="T"  value="结余分配(T)" />
						&nbsp;&nbsp;
						<input type="button" id="btn_vouch" accessKey="Z"  value="凭证维护(Z)"/>
						&nbsp;&nbsp;
						<input type="button" id="btn_save" accessKey="S"  value="模板保存(S)"/>
					</td>
				</tr>
			</table>
			<hr style="border:1px solid #A3C0E8; "/>
			<form name="form1" method="post"  id="form1" >
				<table cellpadding="0" cellspacing="0" class="l-table-edit" style="margin-left: 5px;">
					<tr>
						<td align="right" class="l-table-edit-td" >
							<span style="color:red">*</span>模板名称：
						</td>
						<td align="left" class="l-table-edit-td" >
							<input name="template_id" type="hidden" id="template_id" ltype="text" validate="{required:false}" />
							<input name="template_type_code" type="hidden" id="template_type_code" ltype="text"   validate="{required:false}" />
							<input disabled="disabled" name="template_name" type="text" id="template_name" ltype="text" required="true" validate="{required:true}" />
						</td>
						
						<td align="right" class="l-table-edit-td" style="padding-left: 100px;">
							<span style="color:red">*</span>凭证类型：
						</td>
						<td align="left" class="l-table-edit-td" >
							<input name="vouch_type_code" type="text" id="vouch_type_code" ltype="text" required="true" validate="{required:true}" />
						</td>
					</tr>
					<tr>
						<td align="right" class="l-table-edit-td" >
							<span style="color:red">*</span>摘　　要：
						</td>
						<td align="left" class="l-table-edit-td" >
							<input name="summary" type="text" id="summary" ltype="text" required="true" validate="{required:true}" />
						</td>
						
						<td id="rate_th"  align="right" class="l-table-edit-td" style="padding-left: 100px;">
							<span style="color:red">*</span>提取比例：
						</td>
						<td id="rate_td" align="left" class="l-table-edit-td" >
							<table>
								<tr>
									<td>
										<input name="rate" type="text" id="rate" ltype="text" validate="{required:true}" />
									</td>
									<td>%</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
				
				<fieldset style="border: 1px solid #7d7474; width: 540px; margin-left: 5px; margin-top: 5px; padding: 5px 0;"> 
					<legend style="margin-left: 70px; padding: 0 5px;">财务会计</legend>
					<table cellpadding="0" cellspacing="0" class="l-table-edit" >
						<tr id="debit1">
							<td align="right" class="l-table-edit-td">
								<span style="color:red">*</span><span id="debit_text1">借：　　　　　本期盈余：</span>
							</td>
							<td align="left" colspan="3" class="l-table-edit-td" >
								<input type="text" id="debit_subj_code1" />
							</td>
						</tr>
						<tr id="debit2" style="display: none;">
							<td align="right" class="l-table-edit-td">
								<span style="color:red">*</span><span id="debit_text2"></span>
							</td>
							<td align="left" class="l-table-edit-td">
								<input type="text" id="debit_subj_code2" />
							</td>
						</tr>
						<tr>
							<td align="right" class="l-table-edit-td" >
								<span style="color:red">*</span><span id="credit_text1">贷：　　　　　盈余分配：</span>
							</td>
							<td align="left" colspan="3" class="l-table-edit-td" >
								<input type="text" id="credit_subj_code1" />
							</td>
							<tr id="credit2" style="display: none;">
								<td align="right" class="l-table-edit-td">
									<span style="color:red">*</span><span id="credit_text2"></span>
								</td>
								<td align="left" class="l-table-edit-td">
									<input type="text" id="credit_subj_code2" />
								</td>
							</tr>
						</tr>
						<tr id="cw_btn_tr" style="display: none">
							<td align="center" colspan="4" class="l-table-edit-td" >
								<input type="button" id="btn_cw_add" value="生成财务结余凭证"/>
							</td>
						</tr>
					</table>
				</fieldset>
				
				<fieldset id="budg" style="border: 1px solid #7d7474; width: 540px; margin-left: 5px; margin-top: 5px; padding: 5px 0;"> 
					<legend style="margin-left: 70px; padding: 0 5px;">预算会计</legend>
					<table cellpadding="0" cellspacing="0" class="l-table-edit" >
						<tr id="debit3">
							<td align="right" class="l-table-edit-td" >
								<span style="color:red">*</span><span id="debit_text3">借：非财政拨款医疗结转：</span>
							</td>
							<td align="left" colspan="3" class="l-table-edit-td" >
								<input type="text" id="debit_subj_code3" />
							</td>
						</tr>
						<tr id="debit4">
							<td align="right" class="l-table-edit-td">
								<span style="color:red">*</span><span id="debit_text4">借：　　　　　经营结余：</span>
							</td>
							<td align="left" class="l-table-edit-td">
								<input type="text" id="debit_subj_code4" />
							</td>
						</tr>
						<tr id="debit5">
							<td align="right" class="l-table-edit-td">
								<span style="color:red">*</span><span id="debit_text5">借：　　　　　其他结余：</span>
							</td>
							<td align="left" class="l-table-edit-td">
								<input type="text" id="debit_subj_code5" />
							</td>
						</tr>
						<tr id="credit3">
							<td align="right" class="l-table-edit-td" >
								<span style="color:red">*</span><span id="credit_text3">贷：非财政拨款结余分配：</span>
							</td>
							<td align="left" colspan="3" class="l-table-edit-td" >
								<input type="text" id="credit_subj_code3" />
							</td>
						</tr>
						<tr id="credit4" style="display: none;">
							<td align="right" class="l-table-edit-td">
								<span style="color:red">*</span><span id="credit_text4"></span>
							</td>
							<td align="left" class="l-table-edit-td">
								<input type="text" id="credit_subj_code4" />
							</td>
						</tr>
						<tr id="ys_btn_tr" style="display: none">
							<td align="center" colspan="4" class="l-table-edit-td" >
								<input type="button" id="btn_ys_add" value="生成预算结余凭证"/>
							</td>
						</tr>
					</table>
				</fieldset>
			</form>
			
			<div id="navtab1" style="border:1px solid #A3C0E8; margin-top: 10px;">
				<div id="subjDiv" tabid="tb1" title="转出科目" lselected="true" >
					<div id="subjgrid" style="margin-top: 5px;"></div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
