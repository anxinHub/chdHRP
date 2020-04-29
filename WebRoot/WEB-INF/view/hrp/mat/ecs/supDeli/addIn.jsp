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
<script src="<%=path%>/lib/hrp/mat/mat.js"	type="text/javascript"></script>
<script type="text/javascript">
    
    var dialog = frameElement.dialog; //调用页面的dialog对象(ligerui对象)
    var gridRowData = dialog != null ? dialog.get("data").gridRowData : null;
    var data = dialog != null ? dialog.get("data").data : null;
    $(function () {
    	//渲染节点
		loadMainDict();
		
		//赋默认值
        liger.get("sup_code").setValue(gridRowData.sup_id + "," + gridRowData.sup_no);
		liger.get("sup_code").setText(gridRowData.sup_code + " " + gridRowData.sup_name);
		$("#bill_no").val(gridRowData.bill_no);
		$("#bill_date").val(gridRowData.bill_date);
		$("#delivery_code").val(gridRowData.delivery_code);
		$("#brief").val(gridRowData.note);
    });
    
    function loadMainDict(){

		//字典下拉框
		autocompleteAsync("#bus_type_code", "../../../mat/queryMatBusType.do?isCheck=false", "id", "text", true, true, { sel_flag: 'in',is_write:1 }, true);
		autocompleteAsync("#store_code", "../../../mat/queryMatStoreDictDate.do?isCheck=false", "id", "text", true, true, {is_com : 0,is_write:'1'}, true);
		//即墨需求   2017/04/06  根据仓库有多个采购员   gaopei
		var store_id = liger.get("store_code").getValue().split(",")[0];  
		autocomplete("#stocker", "../../../mat/queryMatStockEmpByStore.do?isCheck=false", "id", "text", true, true,{store_id:store_id}, true);

		$("#sup_code").ligerComboBox({width:200,disabled:true,cancelable: false});
		autocomplete("#stock_type_code", "../../../mat/queryMatStockType.do?isCheck=false", "id", "text", true, true, '', true);
		autocomplete("#app_dept", "../../../mat/queryMatDeptDictDate.do?isCheck=false", "id", "text", true, true, { is_last: '1',is_write:'1' }, false, false, 280);
		
		autocompleteAsync("#protocol_code", "../../../mat/queryMatProtocolMain.do?isCheck=false", "id", "text", true, true, "", false, false, 160, false, 240);
		autocomplete("#proj_code", "../../../mat/queryMatProj.do?isCheck=false", "id", "text", true, true, "", false, false, 200, false, 240);
		//格式化文本框
		
		$("#in_no").ligerTextBox({ width: 200, disabled: true });
		$("#in_date").ligerTextBox({ width: 200 });
		autodate("#in_date");//默认当前日期
		$("#brief").ligerTextBox({ width: 320 });
		$("#bill_date").ligerTextBox({ width: 200 });
		$("#bill_no").ligerTextBox({ width: 160 });
		
		$("#delivery_code").ligerTextBox({width:160});
		var manager = liger.get("store_code").getValue().split(",")[4];
		if(manager){
			autocomplete("#examiner", "../../../mat/queryMatStockEmp.do?isCheck=false", "id", "text", true, true,{manager : manager}, true);
		}else{
			autocomplete("#examiner", "../../../mat/queryMatStockEmp.do?isCheck=false", "id", "text", true, true,{manager : manager}, false);
			liger.get("examiner").setValue("");
			liger.get("examiner").setText("");
		}
		$("#examiner").ligerTextBox({width:160});
		$("#app_dept").ligerTextBox({ width: 160 });
		
		//按钮
		$("#save").ligerButton({ click: addInByDelivery, width: 90 });
		$("#close").ligerButton({ click: this_close, width: 90 });
    }

	function addInByDelivery() {
		
		if (validateMain()) {
			var formPara = {
				in_no: $("#in_no").val(),
				bus_type_code: liger.get("bus_type_code").getValue(),
				store_id: liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[0],
				store_no: liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[1],
				in_date: $("#in_date").val(),
				stocker: liger.get("stocker").getValue() == null ? "" : liger.get("stocker").getValue(),
				sup_id: liger.get("sup_code").getValue() == null ? "" : liger.get("sup_code").getValue().split(",")[0],
				sup_no: liger.get("sup_code").getValue() == null ? "" : liger.get("sup_code").getValue().split(",")[1],
				stock_type_code: liger.get("stock_type_code").getValue() == null ? "" : liger.get("stock_type_code").getValue(),
				app_dept: liger.get("app_dept").getValue() == null ? "" : liger.get("app_dept").getValue().split(",")[0],
				protocol_code: liger.get("protocol_code").getValue() == null ? "" : liger.get("protocol_code").getValue().split(",")[0],
				proj_id: liger.get("proj_code").getValue() == null ? "" : liger.get("proj_code").getValue(),
				brief: $("#brief").val(),
				delivery_code: $("#delivery_code").val(),
				examiner: liger.get("examiner").getValue() == null ? "" : liger.get("examiner").getValue(),
				come_from : 3,
				bill_no: $("#bill_no").val(),
				bill_date: $("#bill_date").val(),
				detailData: JSON.stringify(data)
			};
			$.ligerDialog.confirm('确定生成入库单?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("addMatInByDelivery.do", formPara, function(responseData) {
						parentFrameUse().query();
						parentFrameUse().ligerMsg();
						this_close();
					});
				}
			});
		}
 	} 

	function validateMain() {
		//主表
		if (!liger.get("bus_type_code").getValue()) {
			$.ligerDialog.warn("业务类型不能为空");
			return false;
		}
		if (!liger.get("store_code").getValue()) {
			$.ligerDialog.warn("仓库不能为空");
			return false;
		}
		if (!$("#in_date").val()) {
			$.ligerDialog.warn("制单日期不能为空");
			return false;
		}
		if (liger.get("bus_type_code").getValue() && liger.get("bus_type_code").getValue() == 2) {
			if (!liger.get("stocker").getValue()) {
				$.ligerDialog.warn("采购员不能为空");
				return false;
			}
			if (!liger.get("stock_type_code").getValue()) {
				$.ligerDialog.warn("采购类型不能为空");
				return false;
			}
			if (!liger.get("sup_code").getValue()) {
				$.ligerDialog.warn("供应商不能为空");
				return false;
			}
		}
		
		return true;
	}
	
	function this_close() {
		frameElement.dialog.close();
	}
	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
	<div id="inMainDiv" style="width: 100%">
		<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
			<tr>
				<td align="right" class="l-table-edit-td">
					<span style="color:red">*</span>入库单号：
				</td>
				<td align="left" class="l-table-edit-td">
					<input name="in_no" type="text" id="in_no" value="自动生成" ltype="text" disabled="disabled" required="true" validate="{required:true}"
					/>
				</td>
				<td align="right" class="l-table-edit-td">
					<span style="color:red">*</span>业务类型：
				</td>
				<td align="left" class="l-table-edit-td">
					<input name="bus_type_code" type="text" id="bus_type_code" ltype="text" required="true" validate="{required:true}" />
				</td>
				<td align="right" class="l-table-edit-td">
					<span style="color:red">*</span>仓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;库：
				</td>
				<td align="left" class="l-table-edit-td">
					<input name="store_code" type="text" id="store_code" ltype="text" required="true" validate="{required:true}" />
				</td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td">
					<span style="color:red">*</span>制单日期：
				</td>
				<td align="left" class="l-table-edit-td">
					<input class="Wdate" name="in_date" id="in_date" type="text" required="true" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" validate="{required:true}" />
				</td>
				<td align="right" class="l-table-edit-td">
					<span id="stocker_span" style="color:red">*</span>采&nbsp;&nbsp;购&nbsp;&nbsp;员：
				</td>
				<td align="left" class="l-table-edit-td">
					<input name="stocker" type="text" id="stocker" ltype="text" required="true" validate="{required:true}" />
				</td>
				<td align="right" class="l-table-edit-td">
					<span id="stock_type_code_span" style="color:red">*</span>采购类型：
				</td>
				<td align="left" class="l-table-edit-td">
					<input name="stock_type_code" type="text" id="stock_type_code" ltype="text" validate="{required:true}" />
				</td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td">
					<span id="sup_code_span" style="color:red">*</span>供&nbsp;&nbsp;应&nbsp;&nbsp;商：
				</td>
				<td align="left" class="l-table-edit-td">
					<input name="sup_code" type="text" id="sup_code" ltype="text" required="true" validate="{required:true}" />
				</td>
				<td align="right" class="l-table-edit-td">
					申请科室：
				</td>
				<td align="left" class="l-table-edit-td">
					<input name="app_dept" type="text" id="app_dept" ltype="text" validate="{required:true}" />
				</td>
				<td align="right" class="l-table-edit-td">
					协议编号：
				</td>
				<td align="left" class="l-table-edit-td">
					<input name="protocol_code" type="text" id="protocol_code" ltype="text" validate="{required:false}" />
				</td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td">
					项&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;目：
				</td>
				<td align="left" class="l-table-edit-td">
					<input name="proj_code" type="text" id="proj_code" ltype="text" validate="{required:false,maxlength:20}" />
				</td>
				
				<td align="right" class="l-table-edit-td" >验&nbsp;&nbsp;收&nbsp;&nbsp;员：</td>
				<td align="left" class="l-table-edit-td" >
					<input name="examiner" type="text" id="examiner" ltype="text" validate="{required:false,maxlength:20}" />
				</td>
				
				<td align="right" class="l-table-edit-td" >货&nbsp;&nbsp;单&nbsp;&nbsp;号：</td>
				<td align="left" class="l-table-edit-td"  >
					<input name="delivery_code"  type="text" id="delivery_code" ltype="text" validate="{required:false,maxlength:20}" />
				</td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td">发票日期：</td>
				<td align="left" class="l-table-edit-td">
					<input class="Wdate" name="bill_date" id="bill_date" type="text"  onFocus="WdatePicker({isShowClear:true,dateFmt:'yyyy-MM-dd'})"/>
				</td>
				<td align="right" class="l-table-edit-td">发&nbsp;&nbsp;票&nbsp;&nbsp;号：</td>
				<td align="left" class="l-table-edit-td">
					<input name="bill_no" type="text" id="bill_no"  ltype="text" required="true"  />
				</td>
				<td align="right" class="l-table-edit-td">
					摘&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;要：
				</td> 
					<td align="left" class="l-table-edit-td" >
					<input name="brief" type="text" id="brief" ltype="text" validate="{required:false,maxlength:100}" />
				</td>						
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td align="center" class="l-table-edit-td" colspan="6">
					<button id="save" accessKey="B"><b>保存（<u>B</u>）</b></button> 
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<button id="close" accessKey="C"><b>关闭（<u>C</u>）</b></button>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>
