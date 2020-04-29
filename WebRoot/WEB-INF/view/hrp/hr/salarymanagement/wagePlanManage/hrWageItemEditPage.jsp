<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String path = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<jsp:include page="${path}/resource.jsp">
	<jsp:param value="hr,dialog,select,validate" name="plugins" />
</jsp:include>
<script type="text/javascript">
	// 下拉选
	var item_nature, data_type, is_sum, is_stop, item_type;
	var planCode = "${plan_code}";
	var saveOrUpdateUrl = "addWageItem.do"
	$(function(){
		initSelect();
		initForm();
		initValidate();
	});
	
	var initValidate = function () {
        formValidate = $.etValidate({
        	config: {},
        	items: [
				{ el: $("#item_code"), required: true },
				{ el: $("#item_name"), required: true },
				{ el: $("#item_nature"), required: true },
				{ el: $("#data_type"), required: true },
				{ el: $("#is_sum"), required: true },
				{ el: $("#is_stop"), required: true },
				{ el: $("#item_type"), required: true }
            ]
        });
    };
	
	var initSelect = function(){
		// 工资项性质
		item_nature = $("#item_nature").etSelect({
			showClear: false,
			url : '../../../acc/queryAccWageItemNature.do?isCheck=false',
			defaultValue: "${hrWageItem.item_nature}" || "none"
		});
		
		// 工资项类型
		item_type = $("#item_type").etSelect({
			showClear: false,
			url:'accWageItemTypeSelect.do?isCheck=false',
			defaultValue: "${hrWageItem.item_type}" || "none"
		});
		
		// 数值类型
		data_type = $("#data_type").etSelect({
			showClear: false,
		    options: [
		        { id: '0', text: '数值' },
		        { id: '1', text: '文本' }
		    ],
		    defaultValue: "${hrWageItem.data_type}" || "0"
		});
		
		// 是否参与合计
		is_sum = $("#is_sum").etSelect({
			showClear: false,
		    options: [
		        { id: '1', text: '是' },
		        { id: '0', text: '否' }
		    ],
		    defaultValue: "${hrWageItem.is_sum}" || "1"
		});
		
		// 是否停用
		is_stop = $("#is_stop").etSelect({
			showClear: false,
		    options: [
		        { id: '0', text: '否' },
		        { id: '1', text: '是' }
		    ],
		    defaultValue: "${hrWageItem.is_stop}" || "0"
		});
	}
	
	var initForm = function(){
		// 编辑更新时
		if("${hrWageItem.item_code}"){
			$("#item_code").prop("disabled", "disabled");
			$("#column_item").parent().prev().show();
			$("#column_item").parent().show();
			saveOrUpdateUrl = "updateHrWageItem.do?isCheck=false";
		}
		
		// 确定（保存）
		$("#save").click(function(){
			if(!formValidate.test()){
				return;
			}
			
			ajaxPostData({
				url: saveOrUpdateUrl,
				data: {
					plan_code : planCode,
					item_code : $("#item_code").val(),
					item_name : $("#item_name").val(),
					item_type : $("#item_type").val(),
					item_nature : item_nature.getValue(),
					data_type : data_type.getValue(),
					is_sum : is_sum.getValue(),
					is_stop : is_stop.getValue(),
					note : $("#note").val(),
				},
				delayCallback:true,
				success: function(res){
					if(res.state){
						var parentFrameName = parent.$.etDialog.parentFrameName;
						var parentWindow = parent.window[parentFrameName];
						parentWindow.queryRightGrid();
						
						var curIndex = parent.$.etDialog.getFrameIndex(window.name);
			        	parent.$.etDialog.close(curIndex);
					}
				}
			});
		});
		
		$("#close").click(function(){
			var curIndex = parent.$.etDialog.getFrameIndex(window.name);
	        parent.$.etDialog.close(curIndex);
		});
	}
	
</script>
<body style="overflow:hidden;">
	<div class="main flex-wrap">
		<table class="flex-item-1 table-layout">
			<tr>
				<td class="label">工资项编码<span style="color:red;">*</span>：</td>
				<td class="ipt">
					<input type="text" name="item_code" id="item_code" value="${hrWageItem.item_code}" style="width:180px;"/>
				</td>
				<td class="label">工资项名称<span style="color:red;">*</span>：</td>
				<td class="ipt">
					<input type="text" name="item_name" id="item_name" value="${hrWageItem.item_name}" style="width:180px;"/>
				</td>
			</tr>
			<tr>
				<td class="label">工资项类型<span style="color:red;">*</span>：</td>
				<td class="ipt">
					<input type="text" name="item_type" id="item_type" style="width:180px;"/>
				</td>
				<td class="label">工资项性质<span style="color:red;">*</span>：</td>
				<td class="ipt">
					<input type="text" name="item_nature" id="item_nature" style="width:180px;"/>
				</td>
			</tr>
			<tr>
				<td class="label">数据类型<span style="color:red;">*</span>：</td>
				<td class="ipt">
					<select name="data_type" id="data_type" style="width:180px;"></select>
				</td>
				<td class="label">是否参与合计<span style="color:red;">*</span>：</td>
				<td class="ipt">
					<select name="is_sum" id="is_sum" style="width:180px;"></select>
				</td>
			</tr>
			<tr>
				<td class="label">备注：</td>
				<td class="ipt">
					<input type="text" name="note" id="note" value="${hrWageItem.note}" style="width:180px;"/>
				</td>
				<td class="label">是否停用<span style="color:red;">*</span>：</td>
				<td class="ipt">
					<select name="is_stop" id="is_stop" style="width:180px;"></select>
				</td>
			</tr>
			<tr>
				<td class="label">排序号<span style="color:red;">*</span>：</td>
				<td class="ipt">
					<input type="text" name="sort_code" id="sort_code" placeholder="系统生成" value="${hrWageItem.sort_code}" disabled="disabled" style="width:180px;"/>
				</td>
				<td class="label" style="display: none">工资项目字段：</td>
				<td class="ipt" style="display: none">
					<input type="text" name="column_item" id="column_item" value="${hrWageItem.column_item}" disabled="disabled" style="width:180px;"/>
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