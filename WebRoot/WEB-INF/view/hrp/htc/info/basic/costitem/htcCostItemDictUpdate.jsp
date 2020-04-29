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
<script type="text/javascript">
	var dataFormat;
	var grid;
	var gridManager;
	$(function() {

		loadDict();
		loadForm();
		loadHead(null);
	});
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		//加载查询条件
		grid.loadData(grid.where);
	}
	function loadHead() {
		
		grid = $("#maingrid").ligerGrid(
						{
							columns : [ {
								display : '成本项目编码',
								name : 'cost_item_code',
								align : 'left'
							}, {
								display : '成本项目名称',
								name : 'cost_item_name',
								align : 'left'
							}, {
								display : '操作人',
								name : 'user_code',
								align : 'left'
							}, {
								display : '操作时间',
								name : 'create_date',
								align : 'left'
							}, {
								display : '变更原因',
								name : 'note',
								align : 'left'
							} ],
							dataAction : 'server',
							dataType : 'server',
							usePager : true,
							url : '../costitemno/queryHtcCostItemDictNo.do?isCheck=false&cost_item_id=${cost_item_id}',
							width : '100%',
							height : '100%',
							checkbox : false,
							rownumbers : false,
							selectRowButtonOnly : true
						});

		gridManager = $("#maingrid").ligerGetGridManager();
	}
	function save() {
		var formPara = {

			cost_type_id : liger.get("cost_type").getValue(),
			cost_item_id : '${cost_item_id}',
			cost_item_code : $("#cost_item_code").val(),
			cost_item_name : $("#cost_item_name").val(),
			nature_id : liger.get("nature_id").getValue(),
			para_type_code : liger.get("para_type_code").getValue(),
			busi_data_source : liger.get("busi_data_source").getValue(),
			is_last : liger.get("is_last").getValue(),
			is_stop : liger.get("is_stop").getValue()

		};
		ajaxJsonObjectByUrl("updateHtcCostItemDict.do", formPara, function(responseData) {
			if (responseData.state == "true") {
				parent.query('${tree_code}');
				parent.loadTree();
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
		$("form").ligerForm();
	}

	function saveCostItemDict() {
		if ($("form").valid()) {
			save();
		}
	}
	function loadDict() {
		//字典下拉框
		
		 //字典下拉框
    	 autocomplete("#nature_id","../../base/queryHtcDeptNature.do?isCheck=false","id","text",true,true);
    	 liger.get("nature_id").setValue("${nature_id}");
		 liger.get("nature_id").setText("${nature_name}");
		 autocomplete("#cost_type","../../base/queryHtcDeptTypeDictNo.do?isCheck=false","id","text",true,true);
		 liger.get("cost_type").setValue("${cost_type_id}");
		 liger.get("cost_type").setText("${cost_type_name}");
		 //成本项目来源
		 autocomplete("#busi_data_source","../../base/queryHtcDataSource.do?isCheck=false","id","text",true,true,{busi_data_source_type:2});/* 1：收入数据来源 2.成本数据来源(必填) */
		 liger.get("busi_data_source").setValue("${busi_data_source}");
		 liger.get("busi_data_source").setText("${busi_data_source_name}");
		 //成本项目分摊类型配置
		 autocomplete("#para_type_code","../../base/queryHtcParaType.do?isCheck=false","id","text",true,true,"",true,"${para_type_code}");
		 
		 autocomplete("#is_last", "../../base/queryHtcYearOrNo.do?isCheck=false", "id", "text", true, true,"",true,"${is_last}");
		 autocomplete("#is_stop", "../../base/queryHtcYearOrNo.do?isCheck=false", "id", "text", true, true,"",true,"${is_stop}");
		 
		
		$("#cost_type").ligerTextBox({disabled:true});
		
		
	}
	
	function itemDictChange() {

		var param = "&cost_type_id=${cost_type_id}&cost_type_no=${cost_type_no}&cost_item_id=${cost_item_id}&supp_item_code=${supp_item_code}&cost_type_id=${cost_type_id}&nature_id=${nature_id}&busi_data_source=${busi_data_source}&item_grade=${item_grade}";
		$.ligerDialog.open({
			url : 'htcCostItemDictChangePage.do?isCheck=false' + param,
			data : {},
			height : 300,
			width : 400,
			title : '修改',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : false,
			isResize : true,
			buttons : [ {
				text : '保存',
				onclick : function(item, dialog) {
					dialog.frame.save();
					parent.query();
				},
				cls : 'l-dialog-btn-highlight'
			}, {
				text : '取消',
				onclick : function(item, dialog) {
					dialog.close();
				}
			} ]
		});
	}
</script>

</head>
<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post" id="form1">
		<table cellpadding="0" cellspacing="0" class="l-table-edit">
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">成本项目编码：</td>
				<td align="left" class="l-table-edit-td"><input
					name="cost_item_code" type="text" id="cost_item_code" ltype="text"
					disabled="disabled" value="${cost_item_code}"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">成本项目名称：</td>
				<td align="left" class="l-table-edit-td"><input
					name="cost_item_name" type="text" id="cost_item_name" ltype="text"
					disabled="disabled" value="${cost_item_name}"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">成本分类：</td>
				<td align="left" class="l-table-edit-td"><input
					name="cost_type" type="text" id="cost_type" ltype="text"
					disabled="disabled" validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">成本习性：</td>
				<td align="left" class="l-table-edit-td"><input
					name="nature_id" type="text" id="nature_id" ltype="text"
				     validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">分摊类型：</td>
				<td align="left" class="l-table-edit-td"><input
					name="para_type_code" type="text" id="para_type_code" ltype="text"
				    validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">成本项目来源：</td>
				<td align="left" class="l-table-edit-td"><input
					name="busi_data_source" type="text" id="busi_data_source"
					ltype="text"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">末级标志：</td>
				<td align="left" class="l-table-edit-td">
					<input name="is_last" type="text" id="is_last" ltype="text" validate="{required:true}"/>
				</td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">停用标志：</td>
				<td align="left" class="l-table-edit-td">
					<input name="is_stop" type="text" id="is_stop" ltype="text" validate="{required:true}"/>
				</td>
				<td align="left"></td>
			</tr>
		</table>
	</form>
	<div id="maingrid"></div>
</body>
</html>
