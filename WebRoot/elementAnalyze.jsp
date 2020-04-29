<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	//out.print(path);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>添加修改页面</title>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css"
	rel="stylesheet" />
<link href="<%=path%>/lib/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" />
<link href="<%=path%>/main.css" rel="stylesheet" />
<link href="<%=path%>/lib/hrp/flow/css/foot.css" rel="stylesheet" />
<script src="<%=path%>/lib/jquery/jquery-1.9.1.min.js"></script>
<script src="<%=path%>/lib/hrp.js"></script>
<script src="<%=path%>/lib/ligerUI/js/ligerui.all.js"></script>
<script src="<%=path%>/lib/jquery.cookie.js"></script>
<script src="<%=path%>/lib/json2.js"></script>
<script src="<%=path%>/indexdata.js"></script>
<script src="<%=path%>/lib/hrp/flow/js/foot.js"></script>
<script src="<%=path%>/lib/et_components/et_plugins/etDialog.min.js"></script>
<style>
</style>
<script type="text/javascript">
var mainform 
$(function(){
	
/* 	$("#report_save_button").ligerButton({
		text: '保存',
		click: function () {
			var result = null;
			var dataArr = [];
			var dataObject ={}
		//dataArr.push({name:'report_ruler_input' ,value : $("#report_ruler_input").val()})
			//编码
			dataObject.FAC_CODE = $("#report_code_input").val();
			//名称
			dataObject.FAC_NAME = $("#report_name_input").val();
			//指标单位
			dataObject.ZB_UNIT = $("#report_unit_input").val();
			
			 $.ajax({
			      url: '/CHD-HRP/hrp/acc/accanalyze/elementAnalyze/addElementAnalyze.do?isCheck=false',
			      data: dataObject,
			      type: "post",
			      dataType: "json",
			      async: false,
			      success: function (data) {
			    	  result = data; 
			      },
			      error: function x(s) {
			        result = s.response
			      }
			})
			//var value = $.ligerui.get("elementInfo").getValue(); 
		}
	}); */
	
/* 	$("#report_close_button").ligerButton({
		text: '关闭',
		click: function () {
			var value = $.ligerui.get("elementInfo").getValue();
			alert(value);
		}
	});  */
	
	var columns = [
		{ header: 'ID', name: 'id', width: 80 },
		{ header: '名字', name: 'name', width: 170 },
		{ header: '描述', name: 'desc', width: 170 }
	];

	
	//取数公式设置按钮 
	$("#report_formulaSet_input").ligerButton({
		text: '设置',
		click: function () {
			var value = $.ligerui.get("elementInfo").getValue();
			alert(value);
		}
	});
	
	//状态下拉框 
	$("#report_status_input").ligerComboBox({
		url: '../../data/net/ComboBoxData.ashx',
		valueField : 'id',
		textField: 'name',
		columns: columns,
		selectBoxWidth: 400,
		autocomplete: true,
		width: 400
	});
	
})
</script>
</head>

<body>

	
<form id="acc_element_sf">
	<table cellpadding="0" align="center" cellspacing="0" class="l-table-edit" >
         <tr>
            <td align="right" class="l-table-edit-td" style="padding-left:20px;">
            	编码规则：
            </td>
            <td align="left" class="l-table-edit-td">
            	<input id="target_ruler_input" name="paper_num" class="l-table-edit-td" type="text" ltype="text"  />
            </td>
        </tr>
        
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">
        		<font color="red">*</font>因素指标编码：
        	</td>
            <td align="left" class="l-table-edit-td">
            	<input id="target_code_input" name="qf_date" style="width: 158;height: 27" placeholder=" 必填"  class="Wdate" ltype="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd',maxDate:dq_date.value})"  />
            </td>
        </tr>
        
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">
        		<font color="red">*</font>因素指标名称：
        	</td>
            <td align="left" class="l-table-edit-td">
            	<input id="target_name_input" name="rate_code" class="l-table-edit-td" placeholder="必填"  ltype="text" />
            </td>
        </tr>
        
        <tr>
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;">
				指标单位：
			</td>
            <td align="left" class="l-table-edit-td">
            	<input id="target_unit_input" name="bank_name" class="l-table-edit-td" ltype="text"  />
            </td>
        </tr>
        <tr>
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;">
				取数公式
			</td>
            <td align="left" class="l-table-edit-td">
            	<input id="report_formula_input" name="bank_name" class="l-table-edit-td" ltype="text"  />
            </td>
            <td >
            	<input id="report_formulaSet_input" name="bank_name"  value="设置"  type="button"/>
            </td>
        </tr>
        
        <tr>
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;">
				备注
			</td>
            <td align="left" class="l-table-edit-td">
            	<input id="report_note_input" name="bank_name" class="l-table-edit-td" style="width:444"  ltype="text"  />
            </td>
        </tr>
        
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">
        		状态：
        	</td>
            <td align="left" class="l-table-edit-td">
            	<!-- <input id="report_status_input" name="rate_code" class="l-table-edit-td" ltype="text" /> -->
            	<select class="l-table-edit-td"  id="report_status_select" style="width:180px;">
						<option value="1">启用</option>
						<option value="2">停用</option>
				</select>
            </td>
        </tr>
    </table>
</form>
</body>

</html>