<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String path = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="${path}/resource.jsp">
	<jsp:param value="hr,dialog,select,validate" name="plugins" />
</jsp:include>
<link href="<%=path %>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="<%=path %>/lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<script src="<%=path %>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script type="text/javascript">
	var parentFrame = parent.$.etDialog.parentFrameName;
	var parentWindow = parent.window[parentFrame];
	$(function(){
		loadDict();
		initBtnClick();
		// 在光标位置输入字符串
		(function($) {
			$.fn.extend({
				insertContent : function(myValue, t) {
					var $t = $(this)[0];
					if (document.selection) { // ie
						this.focus();
						var sel = document.selection.createRange();
						sel.text = myValue;
						this.focus();
						sel.moveStart('character', -l);
						var wee = sel.text.length;
	    	    		if (arguments.length == 2) {
							var l = $t.value.length;
							sel.moveEnd("character", wee + t);
							t <= 0 ? sel.moveStart("character", wee - 2 * t - myValue.length) : sel.moveStart( "character", wee - t - myValue.length);
							sel.select();
						}
					} else if ($t.selectionStart || $t.selectionStart == '0') {
						var startPos = $t.selectionStart;
						var endPos = $t.selectionEnd;
						var scrollTop = $t.scrollTop;
						$t.value = $t.value.substring(0, startPos) + myValue + $t.value.substring(endPos, $t.value.length);
						this.focus();
						$t.selectionStart = startPos + myValue.length;
						$t.selectionEnd = startPos + myValue.length;
						$t.scrollTop = scrollTop;
						if (arguments.length == 2) {
							$t.setSelectionRange(startPos - t, $t.selectionEnd + t);
							this.focus();
						}
					} else {
						this.value += myValue;
						this.focus();
					}
				}
			})
		})(jQuery);
		
		$("#formula_method_chs").val(parentWindow.cal_name);
	});
	
	// 加载公式编辑框
	function loadDict(){
		$("#layout1").ligerLayout({
			leftWidth: 200,
			centerBottomHeight:110,
			height:400,
			allowLeftCollapse:false
		});
		
		var formPara={};
		ajaxJsonObjectByUrl("queryFundBaseSetFunTree.do?isCheck=false", formPara,
			function(responseData) {
				if (responseData != null) {
					tree = $("#tree1").ligerTree({
						data : responseData.Rows,
						checkbox : false,
						idFieldName : 'id',
						parentIDFieldName : 'pId',
						textFieldName:'name',
						onSelect: onSelect,
						isExpand: 3,
						nodeWidth:296
					});
					treeManager = $("#tree1").ligerGetTreeManager();
					treeManager.collapseAll();
				}
			}
		);
	}
	
	// 选择树节点
	function onSelect(note) {
		if (note.data.pId == 1) {
			buttonWageData(note.data);
		}
// 		if (note.data.pId == 0) {
// 			$("#formula_method_chs").insertContent("{" + note.data.name + "}");
// 			//$("#formula_method_eng").insertContent("{"+note.data.column_item+"}");
// 		}
	}
	
	// 确认函数
	function buttonWageData(obj) {
		$.ligerDialog.open({
			url : '../../wageItemCal/hrWageTaxSetPage.do?isCheck=false&ele_code=' + obj.id,
			data : {},
			height : 340,
			width : 755,
			title : '',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : false,
			isResize : true,
			buttons : [
				{
					text : '确定',
					onclick : function(item, dialog) {
						var cal_name = dialog.frame.saveAccItemCal();
						if (cal_name != "") {
							$("#formula_method_chs").insertContent("{" + obj.name + "" + cal_name.split("|")[0] + "}");
							//$("#formula_method_eng").insertContent(obj.name + "" + cal_name.split("|")[1] + "|");
							setTimeout(function() {
								dialog.close();
							}, 300)
						}
					},
					cls : 'l-dialog-btn-highlight'
				}, 
				{
					text : '取消',
					onclick : function(item, dialog) {
						dialog.close();
					}
				} 
			]
		});
	}
	
	// 初始化按钮
	function initBtnClick(){
		$("#save").click(function(){
			parentWindow.setCalName($("#formula_method_chs").val());
			var curIndex = parent.$.etDialog.getFrameIndex(window.name);
			parent.$.etDialog.close(curIndex);
		});
		$("#close").click(function(){
			var curIndex = parent.$.etDialog.getFrameIndex(window.name);
			parent.$.etDialog.close(curIndex);
		});
	}
	
	// 符号单击函数
	// +
	function buttonJia() {
		$("#formula_method_chs").insertContent('+');
// 		$("#formula_method_eng").insertContent('+');
	}
	
	// -
	function buttonJH() {
		$("#formula_method_chs").insertContent('-');
// 		$("#formula_method_eng").insertContent('-');
	}
	
	// *
	function buttonCheng() {
		$("#formula_method_chs").insertContent('*');
// 		$("#formula_method_eng").insertContent('*');
	}
	
	// /
	function buttonCH() {
		$("#formula_method_chs").insertContent('/');
// 		$("#formula_method_eng").insertContent('/');
	}
	
	// (
	function buttonZKH() {
		$("#formula_method_chs").insertContent('(');
// 		$("#formula_method_eng").insertContent('(');
	}
	
	// )
	function buttonYKH() {
		$("#formula_method_chs").insertContent(')');
// 		$("#formula_method_eng").insertContent(')');
	}
	
	// =
	function buttonDH() {
		$("#formula_method_chs").insertContent('=');
// 		$("#formula_method_eng").insertContent('=');
	}
	
	// >
	function buttonDYH() {
		$("#formula_method_chs").insertContent('>');
// 		$("#formula_method_eng").insertContent('>');
	}
	
	// <
	function buttonXYH() {
		$("#formula_method_chs").insertContent('<');
// 		$("#formula_method_eng").insertContent('<');
	}
	
	// 或者
	function buttonHZ() {
		$("#formula_method_chs").insertContent('或者');
// 		$("#formula_method_eng").insertContent('or');
	}
	
	// 并且
	function buttonBQ() {
		$("#formula_method_chs").insertContent('并且');
// 		$("#formula_method_eng").insertContent('and');
	}
	
	// 如果...否则
	function buttonRG() {
		$("#formula_method_chs").insertContent('如果...则...否则...如果完');
// 		$("#formula_method_eng").insertContent('case when ...then ... else ... end');
	}
	
	// 取整
	function buttonQZ() {
		$("#formula_method_chs").insertContent('取整');
// 		$("#formula_method_eng").insertContent('wageLastMonthDays');
	}
	
</script>
</head>
<body>
	<div id="layout1" style="width:716px;margin:0 auto;; padding:0;">
		<div position="left" title="工资项目" style="height: 374px;overflow:auto;">
			<ul id="tree1"></ul>
		</div>
		<div position="center" title="计算公式（中文）">
			<table cellpadding="0" cellspacing="0" class="l-table-edit" >
				<tr>
					<td align="left" class="l-table-edit-td">
            			<textarea class="liger-textarea" id="formula_method_chs" name="formula_method_chs" style="height:252px;width:500px;font-size:16px;resize: none;" validate="{required:true}" ></textarea>
						<input name="formula_method_eng" style="display: none;" type="text" id="formula_method_eng" ltype="text" validate="{required:true,maxlength:2000}" />
					</td>
				</tr>
			</table>
		</div>  
		<div position="centerbottom">
			<table align="center" >
				<tr>
					<td align="left" style="padding:0px 0px 10px 10px;" class="l-table-edit-td"></td>
					<td align="left" style="padding:0px 0px 10px 40px;" class="l-table-edit-td"></td>
					<td align="left" style="padding:0px 0px 10px 40px;" class="l-table-edit-td"></td>
					<td align="left" style="padding:0px 0px 10px 40px;" class="l-table-edit-td"></td>
					<td align="left" style="padding:0px 0px 10px 40px;" class="l-table-edit-td"></td>
				</tr>
				<tr>
	           		<td align="left" style="padding:0px 0px 10px 10px;" class="l-table-edit-td">
	           			<input class="liger-button" id="jia" type="button" value="+" onClick="buttonJia();">
	           		</td>
	           		<td align="left" style="padding:0px 0px 10px 30px;" class="l-table-edit-td">
	           			<input class="liger-button" id="jian" type="button" value="-" onClick="buttonJH();">
	           		</td>
	          		<td align="left" style="padding:0px 0px 10px 30px;" class="l-table-edit-td">
	          			<input class="liger-button" id="cheng" type="button" value="*" onClick="buttonCheng();">
	          		</td>
	          		<td align="left" style="padding:0px 0px 10px 30px;" class="l-table-edit-td">
	          			<input class="liger-button" id="chu" type="button" value="/" onClick="buttonCH();">
	          		</td>
	          		<td align="left" style="padding:0px 0px 10px 30px;" class="l-table-edit-td">
	          			<input class="liger-button" id="zkh" type="button" value="("  onClick="buttonZKH();">
	          		</td>
	          	</tr>
				<tr>
					<td align="left" style="padding:0px 0px 10px 10px;" class="l-table-edit-td">
						<input class="liger-button" id="ykh" type="button" value=")" onClick="buttonYKH();">
					</td>
					<td align="left" style="padding:0px 0px 10px 30px;" class="l-table-edit-td">
						<input class="liger-button" id="dh" type="button" value="=" onClick="buttonDH();">
					</td>
					<td align="left" style="padding:0px 0px 10px 30px;" class="l-table-edit-td">
						<input class="liger-button" id="dyh" type="button" value=">" onClick="buttonDYH();">
					</td>
					<td align="left" style="padding:0px 0px 10px 30px;" class="l-table-edit-td">
						<input class="liger-button" id="xyh" type="button" value="< " onClick="buttonXYH();">
					</td>
					<td align="left" style="padding:0px 0px 10px 30px;" class="l-table-edit-td">
						<input class="liger-button" id="hz" type="button" value="或者"  onClick="buttonHZ();">
					</td>
				</tr>
	          	<tr>
	           		<td align="left" style="padding:0px 0px 10px 10px;" class="l-table-edit-td">
	           			<input class="liger-button" id="bq" type="button" value="并且" onClick="buttonBQ();">
	           		</td>
	           		<td align="left" style="padding:0px 0px 10px 30px;" class="l-table-edit-td">
	           			<input class="liger-button" id="rg" type="button" value="如果...否则" onClick="buttonRG();">
	           		</td>
	           		<td align="left" style="padding:0px 0px 10px 30px;" class="l-table-edit-td">
	          			<input class="liger-button" id="qz" type="button" value="取整"  onClick="buttonQZ();">
	          		</td>
	          	</tr>
			</table>
		</div> 
	</div>
	<div class="button-group">
        <button id="save">确定</button>
        <button id="close">取消</button>
    </div>
</body>
</html>