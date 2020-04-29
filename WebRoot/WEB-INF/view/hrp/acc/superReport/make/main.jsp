<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	

	<jsp:include page="${path}/static_resource.jsp">
		 <jsp:param value="select,datepicker,select,ligerUI,checkbox,tree" name="plugins"/>
	</jsp:include>

	<style>
		.table-layout {
			font-size: 13px;
			width:100%
		}


	</style>
	<script type="text/javascript">
		//spread=document.getElementById('spreadFrame').contentWindow.GcSpread.Sheets.designer.wrapper.spread;
		var tree;
		var setting;
		var report_type_select, report_attr_select, is_perm_check, is_stop_check, is_write_check;
		$(function () {
			$("#layout1").ligerLayout({ leftWidth: 210, allowLeftResize: true });
			setting = {
				data: { simpleData: { enable: true }, key: { title: "title" } },
				treeNode: { open: true },
				callback: {
					onClick: function () {
						selectTreeNode()
					}
				}
			};
			$("#treeDiv").css("height", $(window).height() - 28);
			loadTree();
			loadCenterTool();
			init();
		});


		function loadTree(nodeSel) {
			var para = {
				mod_code: $("#mod_code").val()
			};
			ajaxJsonObjectByUrl("../querySuperReportByMod.do?isCheck=false", para, function (responseData) {

				tree = $.fn.zTree.init($("#tree"), setting, responseData.Rows);
				if (nodeSel != undefined) {
					//添加、保存完操作
					var node = tree.getNodeByParam("id", $("#report_code_input").val());
					tree.selectNode(node, true);
					// myClick();
					selectTreeNode();
				}

			});
		}

		var $tool;
		function loadCenterTool() {
			$tool = $("#centertoolbar").ligerToolBar({
				items: [
					{ text: '添加', id: 'add', icon: 'add', click: report_add },
					{ line: true },
					{ text: '删除', id: 'delete', icon: 'delete', click: myDel },
					{ line: true },
					{ text: '保存', id: 'save', icon: 'save', click: report_save },
					{ line: true },
					{ text: '报表定义', id: 'initwage', icon: 'view', click: openReport }
				]
			});
		}

		//删除
		function myDel() {
			var node = tree.getSelectedNodes()[0];
			if (node == null || node.pId == null || node.id == null) {
				return;
			}

			if (node.is_sys == 1) {
				$.ligerDialog.error("系统报表不能删除！");
				return;
			}
			$.ligerDialog.confirm('确定要删除【' + node.name + '】吗？', function (yes) {
				if (yes) {
					var para = {
						report_code: node.id,
						mod_code: $("#mod_code").val()
					};
					ajaxJsonObjectByUrl("../deleteSuperReportByCode.do", para, function (responseData) {
						if (responseData.state == "true") {
							loadTree();
							report_add();
						}
					});
				}
			});
		}

		var spreadJSTop = 195;
		var headerHeight = 175;
		function hidenSpreadMenu(item) {

			var $spreadFrame = $(window.frames["spreadFrame"].document);
			var $content = $spreadFrame.find(".content .fill-spread-content");
			if (item.text == "隐藏工具栏") {

				headerHeight = $spreadFrame.find(".header").css("height");
				spreadJSTop = $content.css("top");
				$spreadFrame.find(".header").css("height", 0);
				$content.css({ top: 2 });
				//$tool.set("items",[{text: "显示工具栏", id: "up", icon: "down", click: hidenSpreadMenu}]);
				item.text = "显示工具栏";
				item.icon = "down";

			} else {

				headerHeight = $spreadFrame.find(".header").css("height", headerHeight);
				$content.css({ top: spreadJSTop });
				item.text = "隐藏工具栏";
				item.icon = "up";

			}
			$content.parent().css({ height: $content.height() });
			spreadNS.designer.wrapper.spread.refresh();
			$tool._render();

		}


		function openReport() {
			var node = tree.getSelectedNodes()[0];
			if (node == null || node.pId == null || node.id == null) {
				$.ligerDialog.error("请选择报表！");
				return;
			}

			parent.$.ligerDialog.open({
				url: 'hrp/acc/superReport/make/reportPage.do?isCheck=false&mod_code=' + $("#mod_code_input").val() + '&report_code=' + $("#report_code_input").val(),
				data: {}, height: $(parent).height(), width: $(parent).width(), title: $("#report_name_input").val(), modal: true, showToggle: false, showMax: false, showMin: false, isResize: false
			});

		}


		// xjx 重构
		//加载页面控件
		function init() {
			report_type_select = $("#report_type_select").etSelect({defaultValue: 1,showClear: false});
			if("${mod_code}" && "${mod_code}" == "01"){
				$(".report_attr_tr").show();
			}
			report_attr_select = $("#report_attr_select").etSelect({defaultValue: 2,showClear: false});
			is_perm_check = $("#is_perm_check").etCheck();
			is_stop_check = $("#is_stop_check").etCheck();
			is_write_check = $("#is_write_check").etCheck();
		}

		//选择树
		function selectTreeNode() {
			var node = tree.getSelectedNodes()[0];
			if (node == null || node.pId == null) {
				return;
			}

			//报表属性赋值
			$("#report_code_input").val(node.id).attr("disabled", true);
			$("#report_name_input").val(node.name);
			$("#report_group_input").val(node.report_group);
			report_type_select.setValue(node.report_type);
			report_attr_select.setValue(node.report_attr);
			$("#report_note_input").val(node.report_note == "null" ? "" : node.report_note);
			$("#sort_code_input").val(node.sort_code).attr("disabled", false);


			if (node.is_perm == 1) {
				is_perm_check.setCheck();
			} else {
				is_perm_check.setUncheck();
			}

			if (node.is_stop == 1) {
				is_stop_check.setCheck();
			} else {
				is_stop_check.setUncheck();
			}

			if (node.is_write == 1) {
				is_write_check.setCheck();
			} else {
				is_write_check.setUncheck();
			}
		}

		//报表添加
		function report_add() {
			var node = tree.getSelectedNodes()[0];
			var report_group = "";
			if (node != null) {
				if (node.report_group != undefined) report_group = node.report_group;
				else report_group = node.name;
				tree.cancelSelectedNode(node);
			}

			$("#report_code_input").val("").attr("disabled", false);
			$("#report_name_input").val("");
			$("#report_note_input").val("");
			$("#report_group_input").val(report_group);
			$("#sort_code_input").val("系统生成").attr("disabled", true);
			is_perm_check.setUncheck();
			is_stop_check.setUncheck();
			is_write_check.setUncheck();
		}

		//报表保存
		function report_save() {
			var report_code = $("#report_code_input").val();
			var report_name = $("#report_name_input").val()
			var sort_code = $("#sort_code_input").val();
			var report_group = $("#report_group_input").val();
			var report_node = $("#report_note_input").val();
			var report_type = report_type_select.val();
			var report_attr = report_attr_select.val();
			var report_note = $("#report_note_input").val();
			if (report_code == "") {
				$.ligerDialog.error("报表编码不能为空！");
				return false;
			}
			if (report_name == "") {
				$.ligerDialog.error("报表名称不能为空！");
				return false;
			}
			if (sort_code == "") {
				$.ligerDialog.error("排序号不能为空！");
				return false;
			}
			if (report_type == "") {
				$.ligerDialog.error("报表类型不能为空！");
				return false;
			}

			var r1 = /^[0-9]*[1-9][0-9]*$/;//正整数
			var r2 = /^-[0-9]*[1-9][0-9]*$/;//负整数
			if (sort_code != "系统生成" && !r1.test(sort_code) && !r2.test(sort_code)) {
				$.ligerDialog.error("排序号只能输入整数！");
				return false;
			}


			if (report_group == "") {
				$("#report_group_input").val("系统默认");
			}

			var para = {
				report_code: report_code,
				report_name: report_name,
				report_group: report_group,
				report_note: report_note,
				report_type: report_type,
				report_attr: report_attr, 
				sort_code: sort_code,
				mod_code: $("#mod_code").val(),
				is_perm: $("#is_perm_check").prop("checked") ? 1 : 0,
				is_stop: $("#is_stop_check").prop("checked") ? 1 : 0,
				is_write: $("#is_write_check").prop("checked") ? 1 : 0,
				is_sys: 0
			};

			ajaxJsonObjectByUrl("../saveSuperReport.do", para, function (responseData) {
				if (responseData.state == "true") {
					loadTree($("#report_code_input").val());
				}
			}, false);
		}
	</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<input id="mod_code" type="text" value="${mod_code}" style="display:none" />
	<div class="l-layout" id="layout1">
		<div position="left" title="报表列表">
			<div style="overflow:auto;" id="treeDiv">
				<ul class="ztree" id="tree"></ul>
			</div>
		</div>

		<div position="center" id="centerReport">
			<div id="centertoolbar"></div>
			<div class="main" style="margin-left:20px;">
				<table class="table-layout">
					<tr>
						<td class="label no-empty">报表编码：</td>
						<td class="ipt">
							<input type="text" id="report_code_input">
						</td>
						<td class="label no-empty">报表名称：</td>
						<td class="ipt">
							<input type="text" id="report_name_input">
						</td>
						<td></td>
					</tr>
					<tr>
						<td class="label no-empty">报表类型：</td>
						<td class="ipt">
							<select name="" id="report_type_select" style="width:180px;">
								<option value="1">普通报表</option>
								<option value="2">月报</option>
								<option value="3">季报</option>
								<option value="4">半年报</option>
								<option value="5">年报</option>
							</select>
						</td>
						<td class="label">报表分类：</td>
						<td class="ipt">
							<input type="text" id="report_group_input">
						</td>
						<td></td>
					</tr>
					<tr>
						<td class="label no-empty report_attr_tr" style="display: none;">报表属性：</td>
						<td class="ipt report_attr_tr" style="display: none;">
							<select name="" id="report_attr_select" style="width:180px;">
								<option value="2">政府会计制度（新）</option>
								<option value="1">医院财务制度（旧）</option>
							</select>
						</td>
						<td class="label no-empty">排序号：</td>
						<td class="ipt">
							<input id="sort_code_input" type="text" value="系统生成" disabled>
						</td>
						<td></td>
					</tr>
					<tr>
						<td class="label"></td>
						<td colspan="3">
							<input type="checkbox" id="is_perm_check">
							<label for="is_perm_check">需要授权</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="checkbox" id="is_write_check">
							<label for="is_write_check">允许编辑</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="checkbox" id="is_stop_check">
							<label for="is_stop_check">停用</label>
						</td>
						<td></td>
					</tr>
					<tr>
						<td class="label">报表说明：</td>
						<td colspan="3" class="ipt">
							<textarea name="" id="report_note_input" style="width:500px"></textarea>
						</td>
						<td></td>
					</tr>
				</table>
			</div>
		</div>



	</div>



</body>

</html>