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
	var gridManager;
	$(function() { 
		loadDict();
		loadForm();
		$("#ass_code").ligerTextBox({
			width : 200
		});
		$("#common_name").ligerTextBox({
			width : 200
		});
		$("#ass_name").ligerTextBox({
			width : 200
		});
		$("#ass_type_id").ligerTextBox({
			width : 200
		});
		$("#ass_unit").ligerTextBox({
			width : 200
		});
		$("#is_measure").ligerTextBox({
			width : 200
		});
		$("#is_depre").ligerTextBox({
			width : 200
		});
		$("#ass_depre_code").ligerTextBox({
			width : 200
		});
		$("#depre_years").ligerTextBox({
			width : 200
		});
		$("#is_stop").ligerTextBox({
			width : 200
		});
		$("#ass_spec").ligerTextBox({
			width : 200
		});
		$("#ass_model").ligerTextBox({
			width : 200
		});
		$("#fac_id").ligerTextBox({
			width : 200
		});
		$("#usage_code").ligerTextBox({
			width : 200
		});
		$("#ven_id").ligerTextBox({
			width : 200
		});
		$("#gb_code").ligerTextBox({
			width : 200
		});
		$("#is_ins").ligerTextBox({
			width : 200
		});
		$("#is_accept").ligerTextBox({
			width : 200
		});
		$("#is_check").ligerTextBox({
			width : 200
		});
		$("#ass_brand").ligerTextBox({
			width : 200
		});
		$("#is_bar").ligerTextBox({
			width : 200
		});
		$("#is_fae").ligerTextBox({
			width : 200
		});
		$("#bar_type").ligerTextBox({
			width : 200
		});
		$("#is_manage_depre").ligerTextBox({
			width : 200
		});
		$("#manage_depr_method").ligerTextBox({
			width : 200
		});
		$("#manage_depre_amount").ligerTextBox({
			width : 200
		});
		
		$("#reg_no").ligerTextBox({
			width : 200
		});
		
		$("#type_code").ligerTextBox({
			width : 200
		});
		
		$("#price").ligerTextBox({
			width : 200,
			number : true
		});
		
		$("#accordion_div").ligerPortal({
			draggable : true,
			height : 200,
		});
		loadHead(null);
		query();
		var navtab =  $("#navtab1").ligerTab({
       	 onBeforeSelectTabItem:function(tabId){
       		 if(tabId == "acceptItem"){
       			 $("#acceptItemGrid").show();
       			 $("#checkItemGrid").hide();
       			 $("#assNoDictGrid").hide();
       		 }else if(tabId == "checkItem"){
       			 $("#checkItemGrid").show();
       			 $("#acceptItemGrid").hide();
       			 $("#assNoDictGrid").hide();
       		 }else if(tabId == "assNoDict"){
       			 $("#assNoDictGrid").show();
       			 $("#checkItemGrid").hide();
      			 $("#acceptItemGrid").hide();
       		 }
      		} 
   		});
		if('${is_accept}' == 0){
			$("#acceptItemGrid").hide();
			liger.get("navtab1").hideTabItem("acceptItem");
		}
		
		if('${is_check}' == 0){
			$("#checkItemGrid").hide();
       		liger.get("navtab1").hideTabItem("checkItem");
		}
       
       $("#is_accept").change(function(){
       	if(liger.get("is_accept").getValue() == 0){
   			$("#acceptItemGrid").hide();
   			liger.get("navtab1").hideTabItem("acceptItem");
   			liger.get("navtab1").selectTabItem("checkItem");
   		}else{
   			$("#acceptItemGrid").show();
   			liger.get("navtab1").showTabItem("acceptItem");
   			liger.get("navtab1").selectTabItem("acceptItem");
   		}
       	isSelected();
       });
       
       $("#is_check").change(function(){
       	if(liger.get("is_check").getValue() == 0){
       		$("#checkItemGrid").hide();
       		liger.get("navtab1").hideTabItem("checkItem");
       		liger.get("navtab1").selectTabItem("acceptItem");
   		}else{
   			$("#checkItemGrid").show();
   			liger.get("navtab1").showTabItem("checkItem");
   			liger.get("navtab1").selectTabItem("checkItem");
   		}
       	isSelected();
       });
       
       $('#is_depre').change(function(){
        	if(liger.get("is_depre").getValue() == 1){
        		liger.get("ass_depre_code").setValue("01");
        		liger.get("ass_depre_code").setText("平均年限法");
        		$("#ass_depre_code").ligerTextBox({disabled : true})
    		}else{
    			liger.get("ass_depre_code").setValue(null);
        		liger.get("ass_depre_code").setText(null);
    			$("#ass_depre_code").ligerTextBox({disabled : false})
    		}
        });
       
       $('#ass_type_id').change(function(){
       	$.each(liger.get("ass_type_id").data,function(i,v){
       		if(this.id == liger.get("ass_type_id").getValue()){
       			$("#depre_years").val(this.manage_depre_amount);
       		}
       	});
       });
        
        $('#is_manage_depre').change(function(){
        	if(liger.get("is_manage_depre").getValue() == 1){
        		liger.get("manage_depr_method").setValue("01");
        		liger.get("manage_depr_method").setText("平均年限法");
        		$("#manage_depr_method").ligerTextBox({disabled : true})
    		}else{
    			$("#manage_depr_method").ligerTextBox({disabled : false})
    		}
        });
        $('#is_measure').change(function(){
        	if(liger.get("is_measure").getValue() == 1){
        		$("#showis_s_measure1").show();
        		$("#showis_s_measure2").show();
    		}else{
    			$("#showis_s_measure1").hide();
        		$("#showis_s_measure2").hide();
    		}
        });
	});
	
	function isSelected(){
		if(liger.get("is_check").getValue() == 0 && liger.get("is_accept").getValue() == 0){
			$("#checkItemGrid").hide();
			$("#acceptItemGrid").hide();
    		liger.get("navtab1").hideTabItem("checkItem");
			liger.get("navtab1").hideTabItem("acceptItem");
			liger.get("navtab1").selectTabItem("assNoDict");
		}
	}

	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		grid.options.parms.push({
			name : 'ass_id',
			value : "${ass_id}"
		});
		//根据表字段进行添加查询条件
		//加载查询条件
		grid.loadData(grid.where);
	}

	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [ 
				{ display: '资产编码', name: 'ass_code', align: 'left',width: '80'
				 		},
                { display: '资产名称', name: 'ass_name', align: 'left',width: '160'
				 		},
                { display: '资产分类', name: 'ass_type_name', align: 'left',width: '140'
				 		},
                { display: '单位', name: 'ass_unit_name', align: 'left',width: '90'
				 		},
                { display: '是否计量', name: 'is_measure', align: 'left',
				 			render : function(rowdata, rowindex,
									value) {
								if(rowdata.is_measure == 0){
									return "否";
								}else{
									return "是";
								}
							},width: '80'
				 			
				 		},
                { display: '是否安装', name: 'is_ins', align: 'left',
				 			render : function(rowdata, rowindex,
									value) {
								if(rowdata.is_ins == 0){
									return "否";
								}else{
									return "是";
								}
							},width: '80'
				 			
				 		},
				 { display: '是否验收', name: 'is_accept', align: 'left',
				 			render : function(rowdata, rowindex,
									value) {
								if(rowdata.is_accept == 0){
									return "否";
								}else{
									return "是";
								}
							},width: '80'
				 			
				 		},
				 { display: '是否巡检', name: 'is_check', align: 'left',
				 			render : function(rowdata, rowindex,
									value) {
								if(rowdata.is_check == 0){
									return "否";
								}else{
									return "是";
								}
							},width: '80'
				 			
				 		},		
                { display: '是否折旧', name: 'is_depre', align: 'left',
				 			render : function(rowdata, rowindex,
									value) {
								if(rowdata.is_depre == 0){
									return "否";
								}else{
									return "是";
								}
							},width: '80'
				 		},
				 { display: '是否条码管理', name: 'is_bar', align: 'left',
				 			render : function(rowdata, rowindex,
									value) {
								if(rowdata.is_bar == 0){
									return "否";
								}else{
									return "是";
								}
							},width: '80'
				 		},		
				 { display: '是否分摊', name: 'is_manage_depre', align: 'left',
				 			render : function(rowdata, rowindex,
									value) {
								if(rowdata.is_manage_depre == 0){
									return "否";
								}else{
									return "是";
								}
							},width: '80'
				 		},		
                { display: '折旧方法', name: 'ass_depre_name', align: 'left',width: '200'
				 		},
                { display: '折旧年限', name: 'depre_years', align: 'left',width: '100'
				 		},
				 { display: '分摊方法', name: 'manage_depr_method_name', align: 'left',width: '200'
				 		},
                { display: '分摊年限', name: 'manage_depre_amount', align: 'left',width: '100'
				 		},		
                { display: '是否停用', name: 'is_stop', align: 'left',
				 			render : function(rowdata, rowindex,
									value) {
								if(rowdata.is_stop == 0){
									return "否";
								}else{
									return "是";
								}
							},width: '80'
				 		},
                { display: '规格', name: 'ass_spec', align: 'left',width: '100'
				 		},
                { display: '型号', name: 'ass_model', align: 'left',width: '100'
				 		},
				 { display: '品牌', name: 'ass_brand', align: 'left',width: '100'
				 		},
                { display: '生产厂商', name: 'fac_code', align: 'left',width: '260'
				 		},
                { display: '主要供应商', name: 'ven_code', align: 'left',width: '280'
				 		},
                { display: '资产用途', name: 'usage_name', align: 'left',width: '100'
				 		},
				 { display: '条码类型', name: 'bar_type', align: 'left',width: '100'
				 		},			
                { display: '国标码', name: 'gb_code', align: 'left',width: '100'
				 		}

			],
			dataAction : 'server',
			dataType : 'server',
			usePager : false,
			url : '../assnodict/queryAssNoDict.do?isCheck=false',
			width : '100%',
			height : '75%',
			checkbox : false,
			rownumbers : false,
			delayLoad : true,//delayLoad:true,初始化是否不加载的属性
			selectRowButtonOnly : true
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	function save() {
		
		gridManager.endEdit();
		var acceptItemData = "";
		var checkItemData = "";
		
		if(liger.get("is_accept").getValue() == 1){
			acceptItemData = document.getElementById("acceptFrame").contentWindow.getData();
			if(isnull(acceptItemData)){
				var f = confirm("验收项目没有维护 是否继续保存?");
				if(!f){	return;	}
			}
		}
		
		if(liger.get("is_check").getValue() == 1){
			checkItemData = document.getElementById("checkFrame").contentWindow.getData();
			if(isnull(checkItemData)){
				var f = confirm("巡检项目没有维护 是否继续保存?");
				if(!f){	return; }
			}
		}
		
		if(liger.get("is_bar").getValue() == 1){
			if(isnull($("#bar_type").val())){
				parent.$.ligerDialog.warn('条码类型不能为空');
				return;
			}
		}
		
		if(liger.get("is_measure").getValue() == 1){
			if(isnull($("#ass_unit").val())){
				parent.$.ligerDialog.warn('计量单位不能为空');
				return;
			}
		}
		
		if(liger.get("is_depre").getValue() == 1){
			if(isnull($("#ass_depre_code").val())){
				parent.$.ligerDialog.warn('折旧方法不能为空');
				return;
			}
			if(isnull($("#depre_years").val())){
				parent.$.ligerDialog.warn('折旧年限不能为空');
				return;
			}
		}
		
		if(liger.get("is_manage_depre").getValue() == 1){
			if(isnull($("#manage_depr_method").val())){
				parent.$.ligerDialog.warn('分摊方法不能为空');
				return;
			}
			if(isnull($("#manage_depre_amount").val())){
				parent.$.ligerDialog.warn('分摊年限不能为空');
				return;
			}
		}

		var formPara = {
			ass_id : "${ass_id}",

			ass_code : $("#ass_code").val(),
			
			ass_name : $("#ass_name").val(),
			
			ass_type_id : liger.get("ass_type_id").getValue(),

			ass_unit : liger.get("ass_unit").getValue(),

			is_measure : liger.get("is_measure").getValue(),
			
			measure_type : liger.get("measure_type").getValue(),
			
			is_s_measure : liger.get("is_s_measure").getValue(),
			
			measure_king_code : liger.get("measure_king_code").getValue(),

			is_depre : liger.get("is_depre").getValue(),

			ass_depre_code : liger.get("ass_depre_code").getValue(),

			depre_years : $("#depre_years").val(),

			is_stop : liger.get("is_stop").getValue(),

			ass_spec : $("#ass_spec").val(),

			ass_model : $("#ass_model").val(),

			fac_id : liger.get("fac_id").getValue().split("@")[0],

			fac_no : liger.get("fac_id").getValue().split("@")[1],

			ven_id : liger.get("ven_id").getValue().split("@")[0],

			ven_no : liger.get("ven_id").getValue().split("@")[1],

			usage_code : liger.get("usage_code").getValue(),

			gb_code : liger.get("gb_code").getValue(),
			
			history : liger.get("history").getValue(),

			is_ins : liger.get("is_ins").getValue(),

			is_accept : liger.get("is_accept").getValue(),

			is_check : liger.get("is_check").getValue(),
			
			ass_brand : $("#ass_brand").val(),
			
			is_bar : liger.get("is_bar").getValue(),
			
			bar_type : liger.get("bar_type").getValue(),
			
			is_manage_depre : liger.get("is_manage_depre").getValue(),
			
			manage_depr_method : liger.get("manage_depr_method").getValue(),
			
			manage_depre_amount :  $("#manage_depre_amount").val(),
			
			reg_no :  liger.get("reg_no").getValue(),
			
			type_code : liger.get("type_code").getValue(),
			
			price :  $("#price").val(),

			acceptItemData : JSON.stringify(acceptItemData),
			
			checkItemData : JSON.stringify(checkItemData),
			
			is_fae : liger.get("is_fae").getValue(),
			common_name : $("#common_name").val()

		};
		$.post("updateAssDict.do", formPara,
				function(responseData) {
					 if (responseData.state == "true") {
						parent.$.ligerDialog.success("保存成功");
						parentFrameUse().query();
						/* document.getElementById("acceptFrame").contentWindow.query();
						document.getElementById("checkFrame").contentWindow.query();
						parentFrameUse().query();
						query(); */
					}else{
						parent.$.ligerDialog.warn(responseData.msg);
					} 
				},"json");
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
		$("form").ligerForm();
	}

	function saveAssDict() {
		if ($("form").valid()) {
			save();
		}
	}
	function loadDict() {
		//字典下拉框

		//字典下拉框
		autocomplete("#ass_type_id",
				"../queryAssTypeDictIsLast.do?isCheck=false", "id", "text",
				true, true, "", false, null, "260");
		liger.get("ass_type_id").setValue("${ass_type_id}");
		liger.get("ass_type_id").setText("${ass_type_code} ${ass_type_name}");

		autocomplete("#fac_id", "../queryHosFacDictNo.do?isCheck=false", "id",
				"text", true, true, "", false, null, "400");
		liger.get("fac_id").setValue("${fac_id}@${fac_no}");
		liger.get("fac_id").setText("${fac_code}");

		autocomplete("#reg_no", "../queryCertNo.do?isCheck=false", "id",
				"text", true, true, null, null, null, "200");
		
		//liger.get("reg_no").setValue("${reg_no}");
		liger.get("reg_no").setText("${reg_no}");
		
		
		autocomplete("#ven_id", "../queryHosSupDict.do?isCheck=false", "id",
				"text", true, true, "", false, null, "400");
		liger.get("ven_id").setValue("${ven_id}@${ven_no}");
		liger.get("ven_id").setText("${ven_code}");

		autocomplete("#ass_depre_code",
				"../queryAssDepreMethodDict.do?isCheck=false", "id", "text",
				true, true, "", false, "${ass_depre_code}", "280");

		autocomplete("#usage_code", "../queryAssUsageDict.do?isCheck=false",
				"id", "text", true, true, "", false, null, "200");

		autocomplete("#ass_unit", "../queryHosUnitDict.do?isCheck=false", "id",
				"text", true, true, "", false, null, "200");
		
		autocomplete("#manage_depr_method",
				"../queryAssDepreMethodDict.do?isCheck=false", "id", "text",
				true, true, null, null, "${manage_depr_method}", "280");
		
		autocomplete("#gb_code",
				"../queryAssGBDict.do?isCheck=false", "id", "text",
				true, true,null, false, null, "300");
		
		autocomplete("#measure_king_code", "../queryAssMeasureKingDict.do?isCheck=false", "id",
				"text", true, true, null, null, null, "200");
		
		autocomplete("#type_code", "../queryAssTypeSixEight.do?isCheck=false", "id",
				"text", true, true, null, null, null, "200");
		
		liger.get("measure_king_code").setValue("${measure_king_code}");
		liger.get("measure_king_code").setText("${measure_king_name}");
		
		liger.get("gb_code").setValue("${gb_code}");
		liger.get("gb_code").setText("${gb_name}");
		
		liger.get("ass_unit").setValue("${ass_unit}");
		liger.get("ass_unit").setText("${ass_unit_name}");
		
		liger.get("usage_code").setValue("${usage_code}");
		liger.get("usage_code").setText("${usage_name}");

		liger.get("type_code").setValue("${type_code}");
		liger.get("type_code").setText("${type_name}");
		
		//是否计量	
		$('#is_measure').ligerComboBox({
				data:[{id:1,text:'是'},{id:0,text:'否'}],
				valueField: 'id',
	            textField: 'text',
				cancelable:true,
				width : 180
		});
		liger.get("is_measure").setValue("${is_measure}");
		if("${is_measure}" == 1){
			$("#showis_s_measure1").show();
    		$("#showis_s_measure2").show();
		}else{
			$("#showis_s_measure1").hide();
    		$("#showis_s_measure2").hide();
		}
		
		//是否折旧	
		 $('#is_depre').ligerComboBox({
				data:[{id:1,text:'是'},{id:0,text:'否'}],
				valueField: 'id',
	            textField: 'text',
				cancelable:true,
				width : 180
		});
		 liger.get("is_depre").setValue("${is_depre}");
		//是否停用	
		/*  $('#is_stop').ligerComboBox({
				data:[{id:1,text:'是'},{id:0,text:'否'}],
				valueField: 'id',
	            textField: 'text',
				cancelable:true,
				width : 180
		});*/
		$("#is_stop").ligerComboBox({
			width : 180
		});
		 liger.get("is_stop").setValue("${is_stop}");
		//是否安装	
		 $('#is_ins').ligerComboBox({
				data:[{id:1,text:'是'},{id:0,text:'否'}],
				valueField: 'id',
	            textField: 'text',
				cancelable:true,
				width : 180
		});
		 liger.get("is_ins").setValue("${is_ins}");
		//是否验收	
		$('#is_accept').ligerComboBox({
				data:[{id:1,text:'是'},{id:0,text:'否'}],
				valueField: 'id',
	            textField: 'text',
				cancelable:true,
				width : 180
		});
		 liger.get("is_accept").setValue("${is_accept}");
		//是否巡检	
		 $('#is_check').ligerComboBox({
				data:[{id:1,text:'是'},{id:0,text:'否'}],
				valueField: 'id',
	            textField: 'text',
				cancelable:true,
				width : 180
		});
		 liger.get("is_check").setValue("${is_check}");
		 $('#is_manage_depre').ligerComboBox({
				data:[{id:1,text:'是'},{id:0,text:'否'}],
				valueField: 'id',
	            textField: 'text',
				cancelable:true,
				width : 180
		});
		 liger.get("is_manage_depre").setValue("${is_manage_depre}");
		
		 $('#is_bar').ligerComboBox({
				data:[{id:1,text:'是'},{id:0,text:'否'}],
				valueField: 'id',
	            textField: 'text',
				cancelable:true,
				width : 180
		});
		 liger.get("is_bar").setValue("${is_bar}");
		 
		
		 $('#is_fae').ligerComboBox({
				data:[{id:1,text:'是'},{id:0,text:'否'}],
				valueField: 'id',
	            textField: 'text',
				cancelable:true,
				width : 180
		});
		 liger.get("is_fae").setValue("${is_fae}");
		 
		$('#bar_type').ligerComboBox({
			data:[{id:1,text:'一维条码'},{id:2,text:'二维条码'}],
			valueField: 'id',
            textField: 'text',
			cancelable:true,
			width : 180
		});
		liger.get("bar_type").setValue("${bar_type}");
		$("#ass_code").ligerTextBox({

			disabled : true
		});
		
		if(liger.get("is_depre").getValue() == 1){
    		liger.get("ass_depre_code").setValue("01");
    		liger.get("ass_depre_code").setText("平均年限法");
    		$("#ass_depre_code").ligerTextBox({disabled : true})
		}else{
			$("#ass_depre_code").ligerTextBox({disabled : false})
		}
		
		if(liger.get("is_manage_depre").getValue() == 1){
    		liger.get("manage_depr_method").setValue("01");
    		liger.get("manage_depr_method").setText("平均年限法");
    		$("#manage_depr_method").ligerTextBox({disabled : true})
		}else{
			$("#manage_depr_method").ligerTextBox({disabled : false})
		}
		
		if(${isDisabled}){
			$("#is_stop").ligerTextBox({disabled : true});
		}

		
		 $('#measure_type').ligerComboBox({
			    data:[{id:0,text:'A类'},{id:1,text:'B类'},{id:2,text:'C类'}],
				valueField: 'id',
	            textField: 'text',
				cancelable:true,
				width : 200,
				value: 1,
		});
		 liger.get("measure_type").setValue("${measure_type}");
		 $('#is_s_measure').ligerComboBox({
				data:[{id:1,text:'是'},{id:0,text:'否'}],
				valueField: 'id',
	            textField: 'text',
				cancelable:true,
				width : 200,
				value: 0,
		})
		liger.get("is_s_measure").setValue("${is_s_measure}");
	}
	function this_close() {
		frameElement.dialog.close();
	}
</script>

</head>

<body style="overflow-y: hidden;overflow-x: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post" id="form1" style="display:blok">
		<table cellpadding="0" cellspacing="0" class="l-table-edit"
			width="100%">
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">资产编码<span style="color: red">*</span>：
				</td>
				<td align="left" class="l-table-edit-td"><input name="ass_code"
					type="text" disabled="disabled" value="${ass_code }" id="ass_code"
					ltype="text" validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">资产名称<span style="color: red">*</span>：
				</td>
				<td align="left" class="l-table-edit-td"><input name="ass_name"
					type="text" id="ass_name" value="${ass_name }" ltype="text"
					validate="{required:true,maxlength:100}" /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">资产分类<span style="color: red">*</span>：
				</td>
				<td align="left" class="l-table-edit-td"><input
					name="ass_type_id" type="text" id="ass_type_id" ltype="text"
					validate="{required:true,maxlength:100}" /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">规格：</td>
				<td align="left" class="l-table-edit-td"><input name="ass_spec"
					type="text" value="${ass_spec}" id="ass_spec" ltype="text"
					validate="{maxlength:100}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">型号：</td>
				<td align="left" class="l-table-edit-td"><input
					name="ass_model" type="text" value="${ass_model}" id="ass_model"
					ltype="text" validate="{maxlength:100}" /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">品牌：</td>
				<td align="left" class="l-table-edit-td"><input
					name="ass_brand" type="text" id="ass_brand" ltype="text"
					validate="{maxlength:100}" value="${ass_brand}" /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">计量单位<span style="color: red">*</span>：</td>
				<td align="left" class="l-table-edit-td"><input name="ass_unit"
					type="text" value="${unit_name}" id="ass_unit" ltype="text"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">是否安装<span style="color: red">*</span>：
				</td>
				<td align="left" class="l-table-edit-td">
					<!-- <select id="is_ins"	name="is_ins">
						<option value="1">是</option>
						<option value="0">否</option>
					</select> -->
					<input name="is_ins" type="text" id="is_ins"/>
				</td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">是否验收<span style="color: red">*</span>：
				</td>
				<td align="left" class="l-table-edit-td">
				<!-- <select id="is_accept" name="is_accept">
						<option value="1">是</option>
						<option value="0">否</option>
				</select> -->
				<input name="is_accept" type="text" id="is_accept"/>
				</td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">是否巡检<span style="color: red">*</span>：
				</td>
				<td align="left" class="l-table-edit-td">
				<!-- <select id="is_check"
					name="is_check">
						<option value="1">是</option>
						<option value="0">否</option>
				</select> -->
				<input name="is_check" type="text" id="is_check"/>
				</td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">是否条码管理<span style="color: red">*</span>：
				</td>
				<td align="left" class="l-table-edit-td">
				<!-- <select id="is_bar"
					name="is_bar" >
						<option value="1">是</option>
						<option value="0">否</option>
				</select> -->
				<input name="is_bar" type="text" id="is_bar"/>
				</td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">条码类型<span style="color: red">*</span>：
				</td>
				<td align="left" class="l-table-edit-td">
				<!-- <select id="bar_type"
					name="bar_type" >
						<option value="1">一维条码</option>
						<option value="2">二维条码</option>
				</select> -->
				<input name="bar_type" type="text" id="bar_type"/>
				</td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">是否计量<span style="color: red">*</span>：
				</td>
				<td align="left" class="l-table-edit-td">
				<!-- <select
					id="is_measure" name="is_measure">
						<option value="1">是</option>
						<option value="0">否</option>

				</select> --> 
				<input name="is_measure" type="text" id="is_measure"/>
				</td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">国标码：</td>
				<td align="left" class="l-table-edit-td"><input name="gb_code"
					type="text" value="${gb_code}" id="gb_code" ltype="text"
					 /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">资产用途：</td>
				<td align="left" class="l-table-edit-td"><input
					name="usage_code" type="text" id="usage_code" ltype="text"
					validate="{maxlength:20}" /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">生产厂商：</td>
				<td align="left" class="l-table-edit-td"><input name="fac_id"
					type="text" id="fac_id" ltype="text"  /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">是否折旧<span style="color: red">*</span>：
				</td>
				<td align="left" class="l-table-edit-td">
				<!-- <select id="is_depre"
					name="is_depre">
						<option value="0">否</option>
						<option value="1">是</option>
				</select> -->
				<input name="is_depre" type="text" id="is_depre"/>
				</td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">折旧方法：</td>
				<td align="left" class="l-table-edit-td"><input
					name="ass_depre_code" type="text" id="ass_depre_code" ltype="text"
					 /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">折旧年限：</td>
				<td align="left" class="l-table-edit-td"><input
					name="depre_years" type="text" value="${depre_years}"
					id="depre_years" ltype="text"  onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">主要供应商：</td>
				<td align="left" class="l-table-edit-td"><input name="ven_id"
					type="text" id="ven_id" ltype="text"  /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">是否分摊<span style="color: red">*</span>：
				</td>
				<td align="left" class="l-table-edit-td">
				<!-- <select id="is_manage_depre"
					name="is_manage_depre">
						<option value="0">否</option>
						<option value="1">是</option>
						
				</select> -->
				<input name="is_manage_depre" type="text" id="is_manage_depre"/>
				</td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">分摊方法：</td>
				<td align="left" class="l-table-edit-td"><input
					name="manage_depr_method" type="text" id="manage_depr_method" ltype="text"
					 /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">分摊年限：</td>
				<td align="left" class="l-table-edit-td"><input
					name="manage_depre_amount" type="text" id="manage_depre_amount" ltype="text"  value="${manage_depre_amount}"  onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">是否停用<span style="color: red">*</span>：
				</td>
				<td align="left" class="l-table-edit-td">
				 <select id="is_stop"
					name="is_stop">
						<option value="0">否</option>
						<option value="1">是</option>
				</select> 
				<!-- <input name="is_stop" type="text" id="is_stop"/> -->
				</td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">注册证号：
				</td>
				<td align="left" class="l-table-edit-td">
				<input name="reg_no" type="text" id="reg_no" value="${reg_no }"/>
				</td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">单价：</td>
				<td align="left" class="l-table-edit-td"><input
					name="price" type="text" id="price" value="${price}"  /></td>
				<td align="left"></td>
				
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">计量分类：</td>
				<td align="left" class="l-table-edit-td"><input
					name="measure_type" type="text" id="measure_type" /></td>
				<td align="left"></td>	
				
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">计量类别：</td>
				<td align="left" class="l-table-edit-td"><input
					name="measure_king_code" type="text" id="measure_king_code" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;" >是否急救类设备：</td>
				<td align="left" class="l-table-edit-td"  ><input
					name="is_fae" type="text" id="is_fae" /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;" >常用名称：</td>
				<td align="left" class="l-table-edit-td"  ><input
					name="common_name" type="text" id="common_name" value="${common_name }" /></td>	
				<td align="left"></td>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;" >68分类：</td>
				<td align="left" class="l-table-edit-td" ><input
					name="type_code" type="text" id="type_code" /></td>		
				<td align="left"></td>		
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;" id="showis_s_measure1">是否强检：</td>
				<td align="left" class="l-table-edit-td"  id="showis_s_measure2"><input
					name="is_s_measure" type="text" id="is_s_measure" /></td>
				<td align="left"></td>	
			</tr>
			<tr>
				<td align="left" class="l-table-edit-td" style="padding-left: 20px;"><input
					name="history" id="history" type="checkbox" />是否保留历史记录</td>
				<td align="left"></td>
			</tr>
		</table>
		<table align="center" cellpadding="0" cellspacing="0"
			class="l-table-edit">
			<tr align="center">
				<td align="center" class="l-table-edit-td"
					style="padding-left: 20px;"><input
					class="l-button l-button-test" style="float: right;" type="button"
					value="保存" onclick="saveAssDict();" /></td>

				<td align="center" class="l-table-edit-td"
					style="padding-left: 20px;"><input
					class="l-button l-button-test" style="float: right;" type="button"
					value="关闭" onclick="this_close();" /></td>
			</tr>
		</table>
	</form>
	
	<div id="navtab1" style="width: 100%;"  class="liger-tab">
	 	<div id="acceptItem" title="验收项目" style="height: 0;" lselected="true"  tabid="acceptItem">
	 	</div>
	 	<div id="checkItem"  title="巡检项目" style="height: 0;"  tabid="checkItem">
	 	</div>
	 	<div id="assNoDict" title="变更记录"  style="height:0;" tabid="assNoDict">
	 	</div>
     </div>
	
	<div id="acceptItemGrid" style="height: 100%;">
               <iframe frameborder="0" id="acceptFrame"  src="assDictAcceptItemAffiPage.do?isCheck=false&ass_id=${ass_id}" width="100%" height="64%"></iframe> 
     </div>
     <div id="checkItemGrid" style="height: 100%;">
               <iframe frameborder="0" id="checkFrame"  src="assDictCheckItemAffiPage.do?isCheck=false&ass_id=${ass_id}" width="100%" height="64%"></iframe> 
     </div>
     <div id="assNoDictGrid" style="height: 100%;">
		<div id="maingrid"></div>
	 </div>
	
</body>
</html>
