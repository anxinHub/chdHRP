<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc.jsp" />
	<script type="text/javascript" charset="utf-8" src="<%=path%>/lib/build/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=path%>/lib/build/ueditor.all.min.js"> </script>
    <script type="text/javascript" charset="utf-8" src="<%=path%>/lib/build/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript">
	var dataFormat;
	var formula_stack_value = [];
	var num = 0;
	$(function() {
		
		ue = UE.getEditor('formula_method_chs',{ 'enterTag' : '<br/>',autoHeightEnabled: false,autoFloatEnabled: false, toolbars: []});
		
		ue.ready(function() {ue.setContent("${formula_method_chs}")});
		
		loadDict();

		loadForm();

		loadTree();
		
		$("#target_code").change(function(){
    		target_code = liger.get("target_code").getValue();
     		tree.unbind();
    		loadTree(target_code);
    		
    	})
    	;  

	});

	function saveHpmFormula() {
		
		var formula_method_chs = UE.getEditor('formula_method_chs').getPlainTxt();
		
		if(formula_method_chs == ''){
			$.ligerDialog.warn('计算公式不能为空 ');
			return ;
		}
		
		
		var formPara = {

				formula_code : $("#formula_code").val(),

				formula_name : $("#formula_name").val(),

				formula_method_chs :  formula_method_chs,

				formula_stack : $("#formula_stack").val(),

				is_stop : $("#is_stop").val(),
				
				formula_stack_value :JSON.stringify(formula_stack_value)

			};

			ajaxJsonObjectByUrl("updateHpmFormula.do", formPara, function(
					responseData) {

				if (responseData.state == "true") { 
					
					parentFrameUse().query();
				}
			});
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

	function loadDict() {

		$("#layout1").ligerLayout({
			leftWidth : 200,
			topHeight : 30,
			
		});

		$("#formula_code").ligerTextBox({
			width : 130
		});

		$("#formula_name").ligerTextBox({
			width : 200
		});

		$("#is_stop").ligerComboBox({
			width : 80
		});
		autocomplete("#target_code","../queryTarget.do?isCheck=false","id","text",true,true,"", "","", 140);
	}

	function onSelect(note) {
		
		if (note.data.pid != 0) {
			num = num + 1;
			var name_chs = UE.getEditor('formula_method_chs');

			var name_eng = $("#formula_method_eng");

			var formula_stack = $("#formula_stack");
			
			ue.setContent(UE.getEditor('formula_method_chs').getContentTxt() + "{" +note.data.text + "}");
			
			name_eng.val(name_eng.val() + "{"+note.data.id+"}");
			
			formula_stack_value.push({target_code:note.data.id,stack_seq_no:num});
			
			if (formula_stack.val() != '') {
				formula_stack.val(formula_stack.val() + ";" + note.data.id);
			} else {
				formula_stack.val(note.data.id);
			}
		}
	}

	function button0() {

		var name_chs = UE.getEditor('formula_method_chs');

		var name_eng = $("#formula_method_eng");

		ue.setContent(UE.getEditor('formula_method_chs').getContentTxt() + 0);

		name_eng.val(name_eng.val() + 0);

	}

	function buttonCE() {

		var name_chs = UE.getEditor('formula_method_chs');

		var name_eng = $("#formula_method_eng");

		var formula_stack = $("#formula_stack");

		var name_chs_val = UE.getEditor('formula_method_chs').getContentTxt();

		var name_eng_val = $("#formula_method_eng").val();

		var formula_stack_val = $("#formula_stack").val();

		var indexof_var = "0|1|2|3|4|5|6|7|8|9|+|-|*|/|(|)|%|.";

		if (name_chs_val.length > 0) {

			var last_chs_var = name_chs_val.charAt(name_chs_val.length - 1);

			if (indexof_var.indexOf(last_chs_var) > 0) {

				ue.setContent(name_chs_val.substring(0, name_chs_val.length - 1));

				name_eng
						.val(name_eng_val.substring(0, name_eng_val.length - 1));

			} else {//计算最后一个位置 如果不是计算字符

				var count = -1;

				var indexof_var_split = indexof_var.split("|");

				for (var i = 0; i < indexof_var_split.length; i++) {

					var count1 = name_chs_val.lastIndexOf(indexof_var_split[i]);
					//alert("name_chs_val="+name_chs_val+" count1="+count1+" split[i]="+indexof_var_split[i]);
					if (count1 > 0 && count1 > count) {

						count = count1;

					}

				}

				ue.setContent(name_chs_val.substring(0, count));

				name_eng.val(name_eng_val.substring(0, count));

				var formula_stack_lastIndexOf = formula_stack_val
						.lastIndexOf(";");

				if (formula_stack_lastIndexOf > 0) {

					formula_stack.val(formula_stack_val.substring(0,
							formula_stack_lastIndexOf));

				} else {

					formula_stack.val('');

				}

			}

		}

	}
	function buttonC() {

		var name_chs = UE.getEditor('formula_method_chs');

		var name_eng = $("#formula_method_eng");

		var formula_stack = $("#formula_stack");

		ue.setContent("");

		name_eng.val('');

		formula_stack.val('');

	}
	function buttonJia() {

		var name_chs = UE.getEditor('formula_method_chs');

		var name_eng = $("#formula_method_eng");

		ue.setContent(UE.getEditor('formula_method_chs').getContentTxt() + '+');

		name_eng.val(name_eng.val() + '+');

	}
	function buttonZKH() {

		var name_chs = UE.getEditor('formula_method_chs');

		var name_eng = $("#formula_method_eng");

		ue.setContent(UE.getEditor('formula_method_chs').getContentTxt() + '(');

		name_eng.val(name_eng.val() + '(');

	}
	function button7() {

		var name_chs = UE.getEditor('formula_method_chs');

		var name_eng = $("#formula_method_eng");

		ue.setContent(UE.getEditor('formula_method_chs').getContentTxt() + 7);

		name_eng.val(name_eng.val() + 7);

	}
	function button8() {

		var name_chs = UE.getEditor('formula_method_chs');

		var name_eng = $("#formula_method_eng");

		ue.setContent(UE.getEditor('formula_method_chs').getContentTxt() + 8);

		name_eng.val(name_eng.val() + 8);

	}
	function button9() {

		var name_chs = UE.getEditor('formula_method_chs');

		var name_eng = $("#formula_method_eng");

		ue.setContent(UE.getEditor('formula_method_chs').getContentTxt() + 9);

		name_eng.val(name_eng.val() + 9);

	}
	function buttonCH() {

		var name_chs = UE.getEditor('formula_method_chs');

		var name_eng = $("#formula_method_eng");

		ue.setContent(UE.getEditor('formula_method_chs').getContentTxt() + '/');

		name_eng.val(name_eng.val() + '/');

	}
	function buttonYKH() {

		var name_chs = UE.getEditor('formula_method_chs');

		var name_eng = $("#formula_method_eng");

		ue.setContent(UE.getEditor('formula_method_chs').getContentTxt() + ')');

		name_eng.val(name_eng.val() + ')');

	}
	function button4() {

		var name_chs = UE.getEditor('formula_method_chs');

		var name_eng = $("#formula_method_eng");

		ue.setContent(UE.getEditor('formula_method_chs').getContentTxt() + 4);

		name_eng.val(name_eng.val() + 4);

	}
	function button5() {

		var name_chs = UE.getEditor('formula_method_chs');

		var name_eng = $("#formula_method_eng");

		ue.setContent(UE.getEditor('formula_method_chs').getContentTxt() + 5);

		name_eng.val(name_eng.val() + 5);

	}
	function button6() {

		var name_chs = UE.getEditor('formula_method_chs');

		var name_eng = $("#formula_method_eng");

		ue.setContent(UE.getEditor('formula_method_chs').getContentTxt() + 6);

		name_eng.val(name_eng.val() + 6);

	}
	function buttonCheng() {

		var name_chs = UE.getEditor('formula_method_chs');

		var name_eng = $("#formula_method_eng");

		ue.setContent(UE.getEditor('formula_method_chs').getContentTxt() + '*');

		name_eng.val(name_eng.val() + '*');

	}
	function buttonBFH() {

		var name_chs = UE.getEditor('formula_method_chs');

		var name_eng = $("#formula_method_eng");

		ue.setContent(UE.getEditor('formula_method_chs').getContentTxt() + '%');

		name_eng.val(name_eng.val() + '%');

	}
	function button1() {

		var name_chs = UE.getEditor('formula_method_chs');

		var name_eng = $("#formula_method_eng");

		ue.setContent(UE.getEditor('formula_method_chs').getContentTxt() + 1);

		name_eng.val(name_eng.val() + 1);

	}
	function button2() {

		var name_chs = UE.getEditor('formula_method_chs');

		var name_eng = $("#formula_method_eng");

		ue.setContent(UE.getEditor('formula_method_chs').getContentTxt() + 2);

		name_eng.val(name_eng.val() + 2);

	}
	function button3() {

		var name_chs = UE.getEditor('formula_method_chs');

		var name_eng = $("#formula_method_eng");

		ue.setContent(UE.getEditor('formula_method_chs').getContentTxt() + 3);

		name_eng.val(name_eng.val() + 3);

	}
	function buttonJH() {

		var name_chs = UE.getEditor('formula_method_chs');

		var name_eng = $("#formula_method_eng");

		ue.setContent(UE.getEditor('formula_method_chs').getContentTxt() + '-');

		name_eng.val(name_eng.val() + '-');

	}
	function buttonDian() {

		var name_chs = UE.getEditor('formula_method_chs');

		var name_eng = $("#formula_method_eng");

		ue.setContent(UE.getEditor('formula_method_chs').getContentTxt() + '.');

		name_eng.val(name_eng.val() + '.');

	}
	
	function buttonDDY() {

		var name_chs = UE.getEditor('formula_method_chs');

		var name_eng = $("#formula_method_eng");

		ue.setContent(UE.getEditor('formula_method_chs').getContentTxt() + '==');

		name_eng.val(name_eng.val() + '==');

	}
	
	function buttonBDY() {

		var name_chs = UE.getEditor('formula_method_chs');

		var name_eng = $("#formula_method_eng");

		ue.setContent(UE.getEditor('formula_method_chs').getContentTxt() + '!=');

		name_eng.val(name_eng.val() + '!=');

	}
	
	function buttonDY() {

		var name_chs = UE.getEditor('formula_method_chs');

		var name_eng = $("#formula_method_eng");

		ue.setContent(UE.getEditor('formula_method_chs').getContentTxt() + '>');

		name_eng.val(name_eng.val() + '>');

	}
	
	function buttonXY() {

		var name_chs = UE.getEditor('formula_method_chs');

		var name_eng = $("#formula_method_eng");

		ue.setContent(UE.getEditor('formula_method_chs').getContentTxt() + '<');

		name_eng.val(name_eng.val() + '<');

	}
	
function buttonDYDY(){
		
		var name_chs = UE.getEditor('formula_method_chs');

		var name_eng = $("#formula_method_eng");

		ue.setContent(UE.getEditor('formula_method_chs').getContentTxt() + '>=');

		name_eng.val(name_eng.val() + '>=');
	}
	
	function buttonXYDY(){
		
		var name_chs = UE.getEditor('formula_method_chs');

		var name_eng = $("#formula_method_eng");

		ue.setContent(UE.getEditor('formula_method_chs').getContentTxt() + '<=');

		name_eng.val(name_eng.val() + '<=');
	}
	
	function buttonYFH(){
		
		var name_chs = UE.getEditor('formula_method_chs');

		var name_eng = $("#formula_method_eng");

		ue.setContent(UE.getEditor('formula_method_chs').getContentTxt() + '&&');

		name_eng.val(name_eng.val() + '&&');
	}
	
	function buttonHFH(){
	
		var name_chs = UE.getEditor('formula_method_chs');
	
		var name_eng = $("#formula_method_eng");
	
		ue.setContent(UE.getEditor('formula_method_chs').getContentTxt() + '||');
	
		name_eng.val(name_eng.val() + '||');
	}
	
	function buttonIF(){
		
		var name_chs = UE.getEditor('formula_method_chs');
	
		var name_eng = $("#formula_method_eng");
	
		ue.setContent(UE.getEditor('formula_method_chs').getContentTxt() + 'if(<span style="color: red">条件</span>){<br/><span style="color: red">&nbsp;&nbsp;内容体</span><br/>}');
	
		name_eng.val(name_eng.val() + 'if(){}');
	}
	
	function buttonIF(){
		
		var name_chs = UE.getEditor('formula_method_chs');
	
		var name_eng = $("#formula_method_eng");
	
		ue.setContent(UE.getEditor('formula_method_chs').getContentTxt() + 'if(<span style="color: red">条件</span>){<span style="color: red">&nbsp;&nbsp;内容体</span>}');
	
		name_eng.val(name_eng.val() + 'if(){}');
	}
	
	function buttonELSEIF(){
	
		var name_chs = UE.getEditor('formula_method_chs');
	
		var name_eng = $("#formula_method_eng");
	
		ue.setContent(UE.getEditor('formula_method_chs').getContentTxt() + ' else if(<span style="color: red">条件</span>){<span style="color: red">&nbsp;&nbsp;内容体</span>}');
	
		name_eng.val(name_eng.val() + 'else if(){}');
	}
	
	function buttonELSE(){
		
		var name_chs = UE.getEditor('formula_method_chs');
	
		var name_eng = $("#formula_method_eng");
	
		ue.setContent(UE.getEditor('formula_method_chs').getContentTxt() + ' else{<span style="color: red">&nbsp;&nbsp;内容体</span>}');
	
		name_eng.val(name_eng.val() + 'else {}');
	}
	
	function buttonSYF(){
		
		var name_chs = UE.getEditor('formula_method_chs');
	
		var name_eng = $("#formula_method_eng");
	
		ue.setContent(UE.getEditor('formula_method_chs').getContentTxt() + '条件 ？ "真值" : "假值" ;');
	
		name_eng.val(name_eng.val() + '条件 ？ "真值" : "假值"');
	}

	function loadTree(dataParam) {
		var param = {};
		if (dataParam) {
		    param = {
		    "target_code": dataParam
		}};
		ajaxJsonObjectByUrl("../hpmtarget/queryTargetTree.do?isCheck=false",param,

				function(responseData) {

					if (responseData != null) {

						tree = $("#tree1").ligerTree({

							data : responseData.Rows,

							checkbox : false,

							idFieldName : 'id',

							parentIDFieldName : 'pid',

							onSelect : onSelect,

							isExpand : 3,
							nodeWidth : 400

						});

						treeManager = $("#tree1").ligerGetTreeManager();

						treeManager.collapseAll();
					}

				});
	}
</script>
<style>
		.target_code {
   			 border-bottom: 1px solid #bed5f3;
  		  margin-bottom: 10px;
  		  padding: 10px 5px;
  		  
		}
        .target_code label {
            display: inline-block;
            width: 32px;
        }
        .target_code > div {
            display: inline-block;
        }
        
    </style>

</head>

<body style="padding: 10px; margin: 0;">
	<textarea id="val_content" style="display:none;" >${formula_method_chs}</textarea>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post" id="form1">
		<div id="layout1"
			style="width: 100%; margin: 40px; height: 400px; margin: 0; padding: 0;">
			<div position="top">
				<table cellpadding="0" cellspacing="0" class="l-table-edit">
					<tr>
						<td align="right" class="l-table-edit-td" style="padding-left: 20px;">公式编码：</td>
						<td align="left" class="l-table-edit-td"><input name="formula_code" type="text" value="${formula_code}" id="formula_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
						<td align="left"></td>
						<td align="right" class="l-table-edit-td" style="padding-left: 20px;">公式名称：</td>
						<td align="left" class="l-table-edit-td">
							<input name="formula_name" type="text" id="formula_name" ltype="text" validate="{required:true,maxlength:200}" value="${formula_name}" />
							<input name="formula_method_eng" type="hidden" id="formula_method_eng" ltype="text" validate="{required:true,maxlength:2000}" value="${formula_method_eng}" />
							<input name="formula_stack" type="hidden" id="formula_stack" ltype="text" validate="{required:true,maxlength:2000}" value="${formula_stack}" /></td>
						<td align="right" class="l-table-edit-td" style="padding-left: 20px;">是否停用：</td>
						<td align="left" class="l-table-edit-td"><select name="is_stop" id="is_stop">
								<option value="0">否</option>
								<option value="1">是</option>
						</select></td>
						<td align="left"></td>
					</tr>
				</table>


			</div>
			<div position="left" title="指标" style="width: 200px; height: 100%; overflow: auto;">
					<div class="target_code">
				<label >指标名称</label>
				
				<input name="target_code" type="text" id="target_code" ltype="text" validate="{required:true,maxlength:20}" />
				</div>
				<ul id="tree1"></ul>
			</div>
			<div position="center" title="计算公式（中文）" >
				 <table cellpadding="0" cellspacing="0" class="l-table-edit" style="width:100%; bottom: 143px;">
					<tr>
						<td align="left" class="l-table-edit-td">
							<textarea class="liger-textarea" id="formula_method_chs" name="formula_method_chs" style="width:100%;height:300px;" validate="{required:true}"></textarea>
						 </td>
					<tr />
				</table>
				<div class="bottom" style="position: absolute;width: 100%;bottom: 0;">
	
					<table align="left">
						<tr>
							<td align="left" style="padding: 0px 0px 10px 10px;" class="l-table-edit-td"></td>
							<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"></td>
							<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"></td>
							<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"></td>
							<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"></td>
						</tr>
						<tr>
							<td align="left" style="padding: 0px 0px 10px 10px;" class="l-table-edit-td"><input class="liger-button" type="button" value="0" onClick="button0();"></td>
							<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"><input class="liger-button" type="button" value="CE" onClick="buttonCE();"></td>
							<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"><input class="liger-button" type="button" value="C" onClick="buttonC();"></td>
							<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"><input class="liger-button" type="button" value="+" onClick="buttonJia();"></td>
							<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"><input class="liger-button" type="button" value="(" onClick="buttonZKH();"></td>
							<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"><input class="liger-button" type="button" value="==" onClick="buttonDDY();"></td>
							<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"><input class="liger-button" type="button" value=">=" onClick="buttonDYDY();"></td>
							<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"><input class="liger-button" type="button" value="if" onClick="buttonIF();"></td>
						</tr>
						<tr>
							<td align="left" style="padding: 0px 0px 10px 10px;" class="l-table-edit-td"><input class="liger-button" type="button" value="7" onClick="button7();"></td>
							<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"><input class="liger-button" type="button" value="8" onClick="button8();"></td>
							<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"><input class="liger-button" type="button" value="9" onClick="button9();"></td>
							<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"><input class="liger-button" type="button" value="/" onClick="buttonCH();"></td>
							<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"><input class="liger-button" type="button" value=")" onClick="buttonYKH();">
							<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"><input class="liger-button" type="button" value="!=" onClick="buttonBDY();">
							<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"><input class="liger-button" type="button" value="<=" onClick="buttonXYDY();"></td>
							<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"><input class="liger-button" type="button" value="else if" onClick="buttonELSEIF();"></td>
						</tr>
						<tr>
							<td align="left" style="padding: 0px 0px 10px 10px;" class="l-table-edit-td"><input class="liger-button" type="button" value="4" onClick="button4();"></td>
							<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"><input class="liger-button" type="button" value="5" onClick="button5();"></td>
							<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"><input class="liger-button" type="button" value="6" onClick="button6();"></td>
							<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"><input class="liger-button" type="button" value="*" onClick="buttonCheng();"></td>
							<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"><input class="liger-button" type="button" value="%" onClick="buttonBFH();"></td>
							<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"><input class="liger-button" type="button" value=">" onClick="buttonDY();"></td>
							<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"><input class="liger-button" type="button" value="&&" onClick="buttonYFH();"></td>
							<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"><input class="liger-button" type="button" value="else" onClick="buttonELSE();"></td>
						</tr>
						<tr>
							<td align="left" style="padding: 0px 0px 10px 10px;" class="l-table-edit-td"><input class="liger-button" type="button" value="1" onClick="button1();"></td>
							<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"><input class="liger-button" type="button" value="2" onClick="button2();"></td>
							<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"><input class="liger-button" type="button" value="3" onClick="button3();"></td>
							<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"><input class="liger-button" type="button" value="-" onClick="buttonJH();"></td>
							<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"><input class="liger-button" type="button" value="." onClick="buttonDian();"></td>
							<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"><input class="liger-button" type="button" value="<" onClick="buttonXY();"></td>
							<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"><input class="liger-button" type="button" value="||" onClick="buttonHFH();"></td>
							<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"><input class="liger-button" type="button" value="?:" onClick="buttonSYF();"></td>
						</tr>
					</table>
				</div>
			</div>
		</div>

	</form>

</body>
</html>
