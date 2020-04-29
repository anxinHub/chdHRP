<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String path = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<jsp:include page="${path}/resource.jsp">
	<jsp:param value="hr,dialog,select,validate,tree" name="plugins" />
</jsp:include>
<link href="<%=path %>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="<%=path %>/lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<script src="<%=path %>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script type="text/javascript">
	var plan_code, item_code, kind_code, stan_id,tree1;
	var saveOrUpdateUrl = "addHrWageItemCal.do";
	$(function(){
		initSelect();
		initValidate();
		loadDict();
		initClick();
		initForm();
		
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
	});

	function loadDict(){
		$("#layout1").ligerLayout({
			leftWidth: 200,
			centerBottomHeight:112,
			height:380,
			allowLeftCollapse:false
		});
	}
	function loadTree(id){
/* 		var formPara={
			plan_code : plan_code.getValue()
		};
		// 计算公式左侧工资项树
		ajaxJsonObjectByUrl("queryHrWageItemCalTree.do?isCheck=false", formPara,
			function(responseData) {
				if (responseData != null) {
					tree = $("#tree1").ligerTree({
						data : responseData.Rows,
						checkbox : false,
						idFieldName : 'id',
						parentIDFieldName : 'pId',
						textFieldName:'name',
						onClick: onSelect,
						isExpand: 3,
						nodeWidth:296
					});
					treeManager = $("#tree1").ligerGetTreeManager();
					treeManager.collapseAll();
				}
			}
		); */
		
		// 树
        var dept_url = 'queryHrWageItemCalTree.do?isCheck=false&plan_code='+id;
        tree1 = $("#tree1").etTree({
            async: {
                enable: true,
                url: dept_url,
            },
            callback: {
                onClick: function (e, id, node) {
                	onSelect(node);
                }
            }
        });
	}
	
	function onSelect(note,target){

		if(note.pId==1){
			buttonWageData(note);
		}
		if(note.pId==0){
			$("#formula_method_chs").insertContent("{"+note.name+"}");
			//$("#formula_method_eng").insertContent("{"+note.data.column_item+"}");	
		}
			
	};	
	
	function buttonWageData(obj){
		$.ligerDialog.open({
			url : 'hrWageTaxSetPage.do?isCheck=false&ele_code='+obj.id,
			data:{}, 
			height: 340,
			width: 755, 
			title:'',
			modal:true,
			showToggle:false,
			showMax:false,
			showMin: false,
			isResize:true,
			buttons: [
				{text: '确定', onclick:
					function (item, dialog) {
	    				var cal_name =dialog.frame.saveAccItemCal();
						if(cal_name !=""){
							$("#formula_method_chs").insertContent("{"+obj.name+""+cal_name.split("|")[0]+"}");
							//$("#formula_method_eng").insertContent(obj.name+""+cal_name.split("|")[1]+"|");
							setTimeout(function (){
								dialog.close();  
							},300)
						}
    				}, cls:'l-dialog-btn-highlight'
    			}, 
				{ text: '取消', onclick:
					function (item, dialog) {
						dialog.close();
					}
    			}
			] 
		});
	}
	
	var initValidate = function () {
        formValidate = $.etValidate({
        	config: {},
        	items: [
				{ el: $("#plan_code"), required: true },
				{ el: $("#item_code"), required: true },
				{ el: $("#kind_code"), required: true }
            ]
        });
    };
	
	var initSelect = function(){
		// 薪资方案
		plan_code = $("#plan_code").etSelect({
			showClear: false,
			url : '../wagePlanManage/wagePlanSelect.do?isCheck=false',
			
			onInit: function(id){
				loadTree(id);
			},
			onChange: function(id){
				//$("#tree1").unbind();
				//$("#tree1").ligerTree().clear();
				//$("#tree1").ligerTree().remove();
 				loadTree(id);
				item_code.clearItem();
				
				item_code.reload({
		            url: '../wagePlanManage/wageItemSelect.do?isCheck=false&plan_code=' + id
		        });
			}
		});
		// 工资项名称
		item_code = $("#item_code").etSelect({
			showClear: false,
			url: '../wagePlanManage/wageItemSelect.do?isCheck=false&plan_code=' + plan_code.getValue(),
			defaultValue : "${hrWageItemCal.item_code}"
		});
		
		// 职工类别
		kind_code = $("#kind_code").etSelect({
			showClear: false,
			url: 'empKindSelect.do?isCheck=false',
		});
		// 薪资标准表
		stan_id = $("#stan_id").etSelect({
			showClear: false,
			url : '../wagePlanManage/selectHrWageStan.do?isCheck=false',
			defaultValue: "none"
		});
	}
	
	var initClick = function(){
		// 取消
		$("#close").click(function(){
			var curIndex = parent.$.etDialog.getFrameIndex(window.name);
	        parent.$.etDialog.close(curIndex);
		});
		
		// 确定（保存）
		$("#save").click(function(){
			if(!formValidate.test()){
				return;
			}
			var itemCal = $("input[name='item_cal']:checked").val();
			<%-- 录入 --%>
			if(1 == itemCal){
				$("#formula_method_chs").val("");
				stan_id.clearItem();
			<%-- 计算公式 --%>
			}else if(2 == itemCal){
				stan_id.clearItem();
			<%-- 薪资标准 --%>
			}else if(3 == itemCal){
				$("#formula_method_chs").val("");
			}
			
			ajaxPostData({
				url: saveOrUpdateUrl,
				data: {
					cal_id : $("#cal_id").val(),
					plan_code : plan_code.getValue(),
					item_code : item_code.getValue(),
					kind_code : kind_code.getValue(),
					item_cal : itemCal,
					cal_name : $("#formula_method_chs").val(),
					stan_id : stan_id.getValue()
				},
				delayCallback:true,
				success: function(res){
					if(res.state){
						var parentFrameName = parent.$.etDialog.parentFrameName;
						var parentWindow = parent.window[parentFrameName];
						parentWindow.query();
						
						var curIndex = parent.$.etDialog.getFrameIndex(window.name);
			        	parent.$.etDialog.close(curIndex);
					}
				}
			});
		});
		
		$("input[name='item_cal']").click(function(){
			<%-- 录入 --%>
			if(1 == this.value){
				$("#layout1").hide();
				$("#layout2").hide();
			<%-- 计算公式 --%>
			}else if(2 == this.value){
				$("#layout2").hide();
				$("#layout1").show();
			<%-- 薪资标准 --%>
			}else if(3 == this.value){
				$("#layout1").hide();
				$("#layout2").show();
			}
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
	
	var initForm = function(){
		if("${hrWageItemCal.cal_id}"){
			saveOrUpdateUrl = "updateHrWageItemCal.do?isCheck=false";
			plan_code.setValue("${hrWageItemCal.plan_code}");
			kind_code.setValue("${hrWageItemCal.kind_code}" || "全部");
			$("#item_cal_" + "${hrWageItemCal.item_cal}").prop("checked", true);
			if("${hrWageItemCal.item_cal}" == 2){
				$("#layout1").show();
				$("#formula_method_chs").val("${hrWageItemCal.cal_name}");
			}else if("${hrWageItemCal.item_cal}" == 3){
				$("#layout2").show();
				stan_id.setValue("${hrWageItemCal.stan_id}");
			}
		}
	}
</script>
<body style="overflow:hidden;">
	<div class="main flex-wrap">
		<input type="hidden" id="cal_id" name="cal_id" value="${hrWageItemCal.cal_id}">
		<table class="flex-item-1 table-layout">
			<tr>
				<td class="label">薪资方案<span style="color:red;">*</span>：</td>
				<td class="ipt">
					<input type="text" name="plan_code" id="plan_code" value="" style="width:180px;"/>
				</td>
				<td class="label">工资项名称<span style="color:red;">*</span>：</td>
				<td class="ipt">
					<input type="text" name="item_code" id="item_code" value="" style="width:180px;"/>
				</td>
			</tr>
			<tr>
				<td class="label">职工类别<span style="color:red;">*</span>：</td>
				<td class="ipt">
					<input type="text" name="kind_code" id="kind_code" value="" style="width:180px;"/>
				</td>
			</tr>
			<tr>
				<td class="label">取值方法<span style="color:red;">*</span>：</td>
				<td class="ipt" colspan="3">
					<input type="radio" name="item_cal" id="item_cal_1" value="1" checked="checked"/>
					<label>录入</label>
					<input type="radio" name="item_cal" id="item_cal_2" value="2"/>
					<label>计算公式</label>
					<input type="radio" name="item_cal" id="item_cal_3" value="3"/>
					<label>薪资标准</label>
				</td>
			</tr>
		</table>
	</div>
	
	<div id="layout1" style="width:716px;margin:0 auto; padding:0;display: none;">
		<div position="left" title="工资项" style="height: 314px;overflow:auto;">
			<ul id="tree1"></ul>
		</div>
		<div position="center" title="计算公式">
			<table cellpadding="0" cellspacing="0" class="l-table-edit" >
				<tr>
					<td align="left" class="l-table-edit-td">
            			<textarea class="liger-textarea" id="formula_method_chs" name="formula_method_chs" style="height:230px;width:500px;font-size:16px;resize: none;" validate="{required:true}" ></textarea>
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
	
	<div id="layout2" style="display: none;">
		<table class="flex-item-1 table-layout" style="margin: 0 auto;">
			<tr>
				<td class="label">薪资标准表：</td>
				<td class="ipt">
					<input type="text" name="stan_id" id="stan_id" style="width:180px;"/>
				</td>
			</tr>
		</table>
	</div>
	
	<div class="button-group">
        <button id="save">确定</button>
        <button id="close">取消</button>
    </div>
</body>
</html>