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
<script src="<%=path%>/lib/hrp/budg/budg.js"	type="text/javascript"></script>
<link rel="stylesheet" href="<%=path %>/lib/Z-tree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="<%=path %>/lib/Z-tree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=path %>/lib/Z-tree/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="<%=path %>/lib/Z-tree/js/jquery.ztree.exedit-3.5.js"></script>
<script type="text/javascript">
	var dataFormat;
	
	var element_type ;
	
	$(function() {

		loadDict();

		loadForm();
		
		element_type = liger.get("element_type").getValue();
		
		loadTree(element_type);
		//$("#tree1").css("height", 320);
		
		$("#element_type").change(function(){
			element_type = liger.get("element_type").getValue();
			
			treeManager.unbind(); 
			treeManager.clear();
			loadTree(element_type);
			
		})

	});
	
	
	function save() {
		var formPara = {

			formula_id : $("#formula_id").val(),

			formula_name : $("#formula_name").val(),

			formula_ca : $("#formula_ca").val(),

			formula_en : $("#formula_en").val(),

			formula_stack : $("#formula_stack").val()

			//is_stop : liger.get("is_stop").getValue()

		};

		ajaxJsonObjectByUrl("addBudgFormula.do?isCheck=false", formPara,
				function(responseData) {

					if (responseData.state == "true") {
						$("input[name='formula_id']").val('');
						$("input[name='formula_name']").val('');
						$("input[name='formula_ca']").val('');
						$("input[name='formula_en']").val('');
						$("input[name='formula_stack']").val('');
						if(parentFrameUse()){
							parentFrameUse().query();
						}else{
							parent.query();
						}
						
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

	function saveBudgFormula() {
		if ($("form").valid()) {
			save();
		}
	}
	function loadDict() {
		

		$("#layout1").ligerLayout({	leftWidth : 200,topHeight : 50,	centerBottomHeight : 175});
		
		autocompleteAsync("#element_type", "../../queryElementType.do?isCheck=false","id", "text", true, true,'',true,'',190);
		
		//autoCompleteByData("#is_stop", yes_or_no.Rows, "id", "text", true, true,'',true,'',80);
		
		$("#formula_id").ligerTextBox({width : 130});

		$("#formula_name").ligerTextBox({width : 200});
		
		$("#element_type").ligerTextBox({width : 190});
		
		//$("#is_stop").ligerTextBox({width : 80});

	}

	function setFormula(note) {
		
		if (note.data.pId != 0 && note.data.pId != '01' && note.data.pId != '02' 
				&& note.data.pId != '03' && note.data.pId != '04'){
			var name_chs = $("#formula_ca").val();

			var name_eng = $("#formula_en").val();

			var formula_stack = $("#formula_stack").val();
			
			if(name_chs){
				$("#formula_ca").val(name_chs + note.data.name);
				
			}else{
				$("#formula_ca").val(note.data.name);
			}
			
			if(name_eng){
				$("#formula_en").val(name_eng + note.data.id+"@"+element_type);
			}else{
				$("#formula_en").val(note.data.id+"@"+element_type);
			}
			
			if (formula_stack) {

				$("#formula_stack").val(formula_stack + ";" + note.data.id+"@"+element_type);
			} else {

				$("#formula_stack").val(note.data.id + "@"+element_type);
			}
		}else{
			$.ligerDialog.error("该项不可作为计算项,请选择其子项");
		}
		
	}

	function button0() {

		var name_chs = $("#formula_ca").val();

		var name_eng = $("#formula_en").val();

		$("#formula_ca").val(name_chs + 0);

		$("#formula_en").val(name_eng + 0);

	}

	function buttonCE() {

		var name_chs = $("#formula_ca");

		var name_eng = $("#formula_en");

		var formula_stack = $("#formula_stack");

		var name_chs_val = $("#formula_ca").val();

		var name_eng_val = $("#formula_en").val();

		var formula_stack_val = $("#formula_stack").val();

		var indexof_var = "0|1|2|3|4|5|6|7|8|9|+|-|*|/|(|)|%|.";

		if (name_chs_val.length > 0) {

			var last_chs_var = name_chs_val.charAt(name_chs_val.length - 1);

			if (indexof_var.indexOf(last_chs_var) > 0) {

				name_chs.val(name_chs_val.substring(0, name_chs_val.length - 1));

				name_eng.val(name_eng_val.substring(0, name_eng_val.length - 1));

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

				name_chs.val(name_chs_val.substring(0, count));

				name_eng.val(name_eng_val.substring(0, count));

				var formula_stack_lastIndexOf = formula_stack_val.lastIndexOf(";");

				if (formula_stack_lastIndexOf > 0) {

					formula_stack.val(formula_stack_val.substring(0,formula_stack_lastIndexOf));

				} else {

					formula_stack.val('');

				}

			}

		}

	}
	function buttonC() {

		$("#formula_ca").val('');

		$("#formula_en").val('');

		$("#formula_stack").val('');

	}
	function buttonJia() {

		var name_chs = $("#formula_ca").val();

		var name_eng = $("#formula_en").val();

		$("#formula_ca").val(name_chs + '+');

		$("#formula_en").val(name_eng + '+');

	}
	function buttonZKH() {

		var name_chs = $("#formula_ca").val();

		var name_eng = $("#formula_en").val();

		$("#formula_ca").val(name_chs + '(');

		$("#formula_en").val(name_eng + '(');

	}
	function button7() {

		var name_chs = $("#formula_ca").val();

		var name_eng = $("#formula_en").val();

		$("#formula_ca").val(name_chs + 7);

		$("#formula_en").val(name_eng + 7);

	}
	function button8() {

		var name_chs = $("#formula_ca").val();

		var name_eng = $("#formula_en").val();

		$("#formula_ca").val(name_chs + 8);

		$("#formula_en").val(name_eng + 8);

	}
	function button9() {

		var name_chs = $("#formula_ca").val();

		var name_eng = $("#formula_en").val();

		$("#formula_ca").val(name_chs + 9);

		$("#formula_en").val(name_eng + 9);

	}
	function buttonCH() {

		var name_chs = $("#formula_ca").val();

		var name_eng = $("#formula_en").val();

		$("#formula_ca").val(name_chs + '/');

		$("#formula_en").val(name_eng + '/');

	}
	function buttonYKH() {

		var name_chs = $("#formula_ca").val();

		var name_eng = $("#formula_en").val();

		$("#formula_ca").val(name_chs + ')');

		$("#formula_en").val(name_eng + ')');

	}
	function button4() {

		var name_chs = $("#formula_ca").val();

		var name_eng = $("#formula_en").val();

		$("#formula_ca").val(name_chs + 4);

		$("#formula_en").val(name_eng + 4);

	}
	function button5() {

		var name_chs = $("#formula_ca").val();

		var name_eng = $("#formula_en").val();

		$("#formula_ca").val(name_chs + 5);

		$("#formula_en").val(name_eng + 5);

	}
	function button6() {

		var name_chs = $("#formula_ca").val();

		var name_eng = $("#formula_en").val();

		$("#formula_ca").val(name_chs + 6);

		$("#formula_en").val(name_eng + 6);

	}
	function buttonCheng() {

		var name_chs = $("#formula_ca").val();

		var name_eng = $("#formula_en").val();

		$("#formula_ca").val(name_chs + '*');

		$("#formula_en").val(name_eng + '*');

	}
	function buttonBFH() {

		var name_chs = $("#formula_ca").val();

		var name_eng = $("#formula_en").val();

		$("#formula_ca").val(name_chs + '%');

		$("#formula_en").val(name_eng+ '%');

	}
	function button1() {

		var name_chs = $("#formula_ca").val();

		var name_eng = $("#formula_en").val();

		$("#formula_ca").val(name_chs + 1);

		$("#formula_en").val(name_eng + 1);

	}
	function button2() {

		var name_chs = $("#formula_ca").val();

		var name_eng = $("#formula_en").val();

		$("#formula_ca").val(name_chs + 2);

		$("#formula_en").val(name_eng + 2);

	}
	function button3() {

		var name_chs = $("#formula_ca").val();

		var name_eng = $("#formula_en").val();

		$("#formula_ca").val(name_chs + 3);

		$("#formula_en").val(name_eng + 3);

	}
	function buttonJH() {

		var name_chs = $("#formula_ca").val();

		var name_eng = $("#formula_en").val();

		$("#formula_ca").val(name_chs + '-');

		$("#formula_en").val(name_eng + '-');

	}
	function buttonDian() {

		var name_chs = $("#formula_ca").val();

		var name_eng = $("#formula_en").val();

		$("#formula_ca").val(name_chs + '.');

		$("#formula_en").val(name_eng + '.');

	}

	function loadTree(data) {
		
		if(!element_type){
			$.ligerDialog.error("下拉框必选");
			return false ;
		}
		
		ajaxJsonObjectByUrl("queryElementTree.do?isCheck=false&element_type="+data,
				{},

				function(responseData) {

					if (responseData != null) {

						tree = $("#tree1").ligerTree({

							data : responseData.Rows,

							checkbox : false,

							idFieldName : 'id',

							parentIDFieldName : 'pId',
							
							textFieldName:'name',
							
							onSelect : setFormula,

							isExpand : 3,
							nodeWidth : 200

						});

						treeManager = $("#tree1").ligerGetTreeManager();

						treeManager.collapseAll();
					}

				});
	} 
</script>

</head>

<body style="padding: 10px; margin: 0;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post" id="form1">
		<div id="layout1"
			style="width: 100%;  height: 400px; margin: 0; padding: 0;">
			<div position="top">
				<table cellpadding="0" cellspacing="0" class="l-table-edit">
					<tr>
						<td align="right" class="l-table-edit-td" style="padding-left: 20px;">公式编码：</td>
						<td align="left" class="l-table-edit-td">
							<input name="formula_id" type="text" id="formula_id" ltype="text"  validate="{required:true,maxlength:20}" /></td>
						<td align="left"></td>
						<td align="right" class="l-table-edit-td" style="padding-left: 20px;">公式名称：</td>
						<td align="left" class="l-table-edit-td">
							<input name="formula_name" type="text" id="formula_name" ltype="text" validate="{required:true,maxlength:200}" /> 
							<input name="formula_en" type="hidden" id="formula_en" ltype="text" validate="{required:true,maxlength:2000}" /> 
							<input name="formula_stack" type="hidden" id="formula_stack" ltype="text" validate="{required:true,maxlength:2000}" />
						</td>
						<!-- <td align="right" class="l-table-edit-td" style="padding-left: 20px;">是否停用：</td>
						<td align="left" class="l-table-edit-td">
							<input name="is_stop" type="text" id="is_stop" ltype="text" validate="{required:true,maxlength:4}" /> 
						<td align="left"></td> -->
					</tr>
				</table>


			</div>
			<div position="left" style="overflow:auto;height:373px;">
				
					<table cellpadding="0" cellspacing="0" class="l-table-edit">
						<tr>
							<td align="left" class="l-table-edit-td"><input name="element_type" type="text" id="element_type" ltype="text" /></td>
						</tr>
					</table>
				
				
				<div id="tree1" style="width: 200px; ">
				</div>
			</div>
			<div position="center" title="计算公式（中文）">
				<table cellpadding="0" cellspacing="0" class="l-table-edit">
					<tr>
						<td align="left" class="l-table-edit-td">
							<textarea rows="9" class="liger-textarea" id="formula_ca" disabled name="formula_ca" style="width: 915px; font-size: 16px;" validate="{required:true}"></textarea></td>
					<tr />
				</table>
			</div>
			<div position="centerbottom">

				<table align="center">
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
					</tr>
					<tr>
						<td align="left" style="padding: 0px 0px 10px 10px;" class="l-table-edit-td"><input class="liger-button" type="button" value="7" onClick="button7();"></td>
						<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"><input class="liger-button" type="button" value="8" onClick="button8();"></td>
						<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"><input class="liger-button" type="button" value="9" onClick="button9();"></td>
						<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"><input class="liger-button" type="button" value="/" onClick="buttonCH();"></td>
						<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td">
							<input class="liger-button" type="button" value=")" onClick="buttonYKH();">
						</td>
					</tr>
					<tr>
						<td align="left" style="padding: 0px 0px 10px 10px;" class="l-table-edit-td"><input class="liger-button" type="button" value="4" onClick="button4();"></td>
						<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"><input class="liger-button" type="button" value="5" onClick="button5();"></td>
						<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"><input class="liger-button" type="button" value="6" onClick="button6();"></td>
						<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"><input class="liger-button" type="button" value="*" onClick="buttonCheng();"></td>
						<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"><input class="liger-button" type="button" value="%" onClick="buttonBFH();"></td>
					</tr>
					<tr>
						<td align="left" style="padding: 0px 0px 10px 10px;" class="l-table-edit-td"><input class="liger-button" type="button" value="1" onClick="button1();"></td>
						<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"><input class="liger-button" type="button" value="2" onClick="button2();"></td>
						<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"><input class="liger-button" type="button" value="3" onClick="button3();"></td>
						<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"><input class="liger-button" type="button" value="-" onClick="buttonJH();"></td>
						<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"><input class="liger-button" type="button" value="." onClick="buttonDian();"></td>
					</tr>
				</table>
			</div>
		</div>

	</form>

</body>
</html>
