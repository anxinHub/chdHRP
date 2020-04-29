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
	var grid;
	var gridManager = null;
	var userUpdateStr;
	var menu;
	var actionNodeID;
	var tree_code;
	$(function() {
		$("#layout1").ligerLayout({
			leftWidth : 235
		});
		loadDict();
		loadHead(null); //加载数据
		loadTree();
		$("#cost_item_code").ligerTextBox({
			width : 160
		});
		$("#cost_item_name").ligerTextBox({
			width : 160
		});
		menu = $.ligerMenu({
			top : 100,
			left : 100,
			width : 120,
			items : [ {
				text : '增加',
				click : itemclick,
				id : 'add',
				icon : 'add'
			}, {
				text : '修改',
				click : update,
				icon : 'edit'
			}, {
				line : true
			}, {
				text : '删除',
				id : 'delete',
				icon : 'delete',
				click : delete_tree
			} ]
		});
	});
	function delete_tree(item) {
		var vo = actionNodeID.split("|");
		var ParamVo = [];
		ParamVo.push(vo[0] + "@" + vo[1]
				+ "@" + vo[2] + "@"
				+ vo[3]+ "@"+ vo[4])
		$.ligerDialog.confirm('确定删除?', function(yes) {
			if (yes) {
				ajaxJsonObjectByUrl("deleteCostItemDict.do", {
					ParamVo : ParamVo.toString()
				}, function(responseData) {
					if (responseData.state == "true") {
						query();
						loadTree() ;
					}
				});
			}
		});
	}
	function update(item) {
		var obj=actionNodeID;
		var vo = obj.split("|");
		var parm = "group_id=" + vo[0] + "&" + "hos_id=" + vo[1] + "&"
				+ "copy_code=" + vo[2] + "&" + "cost_type_id=" + vo[3] + "&"
				+ "cost_item_id=" + vo[4] + "&" + "cost_item_no=" + vo[5]
		$.ligerDialog.open({
			url : 'costItemDictUpdatePage.do?isCheck=false&' + parm,
			data : {},
			height : 450,
			width : 950,
			title : '修改',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : false,
			isResize : true,
			buttons : [ {
				text : '成本项目变更',
				onclick : function(item, dialog) {
					dialog.frame.itemDictChange();
				},
				cls : 'l-dialog-btn-highlight'
			}, {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.saveCostItemDict();
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
	/* 设置树形菜单 */
	function onSelect(note) {
		tree_code=note.data.code;
		query(note.data.code);
	}
	function loadTree() {
		ajaxJsonObjectByUrl("queryCostItemDictByTree.do?isCheck=false", {},
				function(responseData) {
					if (responseData != null) {
						tree = $("#tree").height($(window).height()-60).ligerTree(
								{
									data : responseData.Rows,
									parentIcon : null,
									childIcon : null,
									checkbox : false,
									idFieldName : 'code',
									parentIDFieldName : 'pcode',
									textFieldName : 'text',
									onSelect : onSelect,
									nodeWidth : 250,
									slide : false,
									isExpand: true,
									onContextmenu : function(node, e) {
										actionNodeID = "" + node.data.group_id
												+ "|" + node.data.hos_id + "|"
												+ node.data.copy_code + "|"
												+ node.data.cost_type_id+ "|"
												+ node.data.cost_item_id+ "|"
												+ node.data.cost_item_no;
										menu.show({
											top : e.pageY,
											left : e.pageX
										});
										return false;
									}
								});
						treeManager = $("#tree").ligerGetTreeManager();
						//treeManager.collapseAll(); //全部收起
					}
				});
	}
	//是否存在指定变量 
	function isExitsVariable(variableName) {
		try {
			if (typeof (variableName) == "object") {
				return false;
			} else if (typeof (variableName) == "undefined") {
				return false;
			} else {
				return true;
			}
		} catch (e) {
		}
		return false;
	}
	//查询
	function query(code) {
		grid.options.parms = [];
		grid.options.newPage = 1;
		if (isExitsVariable(code)) {
			grid.options.parms.push({
				name : 'cost_item_code',
				value : code
			});
		} else {
			grid.options.parms.push({
				name : 'cost_item_code',
				value : $("#cost_item_code").val()
			});
		}
		//根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'cost_item_code',
			value : $("#cost_item_code").val()
		});
		/* grid.options.parms.push({name:'cost_item_name',value:$("#cost_item_name").val()});  */
		grid.options.parms.push({
			name : 'supp_item_code',
			value : liger.get("supp_item_code").getValue()
		});
		grid.options.parms.push({
			name : 'nature_id',
			value : liger.get("nature_id").getValue()
		});
		grid.options.parms.push({
			name : 'busi_data_source',
			value : liger.get("busi_data_source").getValue()
		});
		grid.options.parms.push({
			name : 'item_grade',
			value : $("#item_grade").val()
		});
		grid.options.parms.push({
			name : 'is_last',
			value : $("#is_last").val()
		});
		//加载查询条件
		grid.loadData(grid.where);
	}

	function loadHead() {
		grid = $("#maingrid").ligerGrid(
				{
					columns : [
							{
								display : '成本项目编码',
								name : 'cost_item_code',
								align : 'left',
								render : function(rowdata, rowindex, value) {
									return "<a href=javascript:openUpdate('"
											+ rowdata.group_id + "|"
											+ rowdata.hos_id + "|"
											+ rowdata.copy_code + "|"
											+ rowdata.cost_type_id + "|"
											+ rowdata.cost_item_id + "|"
											+ rowdata.cost_item_no + "')>"
											+ rowdata.cost_item_code + "</a>";
								}
							}, {
								display : '成本项目名称',
								name : 'cost_item_name',
								align : 'left',
								render : function(rowdata, rowindex,
											value) {
								        return formatSpace(rowdata.cost_item_name,rowdata.item_grade-1)
									}
							}, {
								display : '成本项目分类',
								name : 'cost_type_name',
								align : 'left'
							}, {
								display : '上级项目',
								name : 'supp_item_name',
								align : 'left',
								render : function(rowdata, rowindex, value) {
									if (rowdata.supp_item_name == 0) {
										return "";
									} else {
										return rowdata.supp_item_name;
									}
								}
							}, {
								display : '成本习性',
								name : 'nature_name',
								align : 'left'
							}, 
							 {
								display : '分摊类型',
								name : 'para_type_name',
								align : 'left'
							}, {
								display : '成本项目来源',
								name : 'busi_data_source_name',
								align : 'left'
							}, {
								display : '层级',
								name : 'item_grade',
								align : 'left'
							}, {
								display : '末级标志',
								name : 'is_last',
								align : 'left',reg:'0=否,1=是',
								render : function(rowdata, rowindex, value) {
									if (rowdata.is_last == 0) {
										return "否";
									} else {
										return "是"
									}
								}
							}, {
								display : '停用标志',
								name : 'is_stop',
								align : 'left',reg:'0=否,1=是',
								render : function(rowdata, rowindex, value) {
									if (rowdata.is_stop == 0) {
										return "否";
									} else {
										return "是"
									}
								}
							} ],
					dataAction : 'server',
					dataType : 'server',
					usePager : true,
					url : 'queryCostItemDict.do',
					width : '100%',
					height : '100%',
					checkbox : true,
					rownumbers : true,
					selectRowButtonOnly : true,//heightDiff: -10,
					toolbar : {
						items : [ {
							text : '查询',
							id : 'search',
							click : query,
							icon : 'search'
						}, {
							line : true
						}, {
							text : '添加',
							id : 'add',
							click : itemclick,
							icon : 'add'
						}, {
							line : true
						}, {
							text : '批量更新',
							id : 'editBatch',
							click : costItemBatch,
							icon : 'edit'
						}, {
							text : '删除',
							id : 'delete',
							click : itemclick,
							icon : 'delete'
						}, {
							line : true
						}, 
// 						{
// 							text : '导出Excel',
// 							id : 'export',
// 							click : exportExcel,
// 							icon : 'pager'
// 						}, {
// 							line : true
// 						},
						{
							text : '打印',
							id : 'print',
							click : print,
							icon : 'print'
						}, {
							line : true
						}, {
							text : '下载导入模板',
							id : 'downTemplate',
							click : itemclick,
							icon : 'down'
						}, {
							line : true
						}, {
							text : '导入',
							id : 'import',
							click : itemclick,
							icon : 'up'
						} ]
					},
					onDblClickRow : function(rowdata, rowindex, value) {
						openUpdate(rowdata.group_id + "|" + rowdata.hos_id
								+ "|" + rowdata.copy_code + "|"
								+ rowdata.cost_type_id + "|"
								+ rowdata.cost_item_id + "|"
								+ rowdata.cost_item_no);
					}
				});

		gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	function costItemBatch(){
	$.ligerDialog.open({
		url : 'costItemDictBatchPage.do?isCheck=false',
		height : 400,
		width : 500,
		title : '根据成本分类批量更新 性质 或者 项目来源 或者 分摊类别 ',
		modal : true,
		showToggle : false,
		showMax : false,
		showMin : true,
		isResize : true,
		buttons : [ {
			text : '确定',
			onclick : function(item, dialog) {
				dialog.frame.saveCostItemDict();
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
	function itemclick(item) {
		if (item.id) {
			switch (item.id) {
			case "add":
				$.ligerDialog.open({
					url : 'costItemDictAddPage.do?isCheck=false',
					height : 400,
					width : 500,
					title : '<h2>编码规则：<font color="red">' + "${rules_view}"
							+ '</font></h2>',
					modal : true,
					showToggle : false,
					showMax : false,
					showMin : true,
					isResize : true,
					buttons : [ {
						text : '确定',
						onclick : function(item, dialog) {
							dialog.frame.saveCostItemDict();
						},
						cls : 'l-dialog-btn-highlight'
					}, {
						text : '取消',
						onclick : function(item, dialog) {
							dialog.close();
						}
					} ]
				});
				return;
			case "modify":
				return;
			case "delete":
				var data = gridManager.getCheckedRows();
				if (data.length == 0) {
					$.ligerDialog.error('请选择行');
				} else {
					var ParamVo = [];
					$(data).each(
							function() {
								ParamVo.push(
								//表的主键
								this.group_id + "@" + this.hos_id + "@"
										+ this.copy_code + "@"
										+ this.cost_type_id + "@"
										+ this.cost_item_id)
							});
					$.ligerDialog.confirm('确定删除?', function(yes) {
						if (yes) {
							$.post("deleteCostItemDict.do", {
								ParamVo : ParamVo.toString()
							}, function(responseData) {
								if (responseData.state == "true") {
									query();
									loadTree() ;
								}else{
									$.ligerDialog.warn(responseData.error);
								}
							},"json");
						}
					});
				}
				return;
			case "import":
				$.ligerDialog.open({
					url : 'costItemDictImportPage.do?isCheck=false',
					height : 500,
					width : 800,
					title : '导入',
					modal : true,
					showToggle : false,
					showMax : false,
					showMin : true,
					isResize : true
				});
			case "export":
				return;
			case "downTemplate":
				location.href = "downTemplate.do?isCheck=false";
				return;
			case "Excel":
			case "Word":
			case "PDF":
			case "TXT":
			case "XML":
				$.ligerDialog.waitting('导出中，请稍候...');
				setTimeout(function() {
					$.ligerDialog.closeWaitting();
					if (item.id == "Excel")
						$.ligerDialog.success('导出成功');
					else
						$.ligerDialog.error('导出失败');
				}, 1000);
				return;
			}
		}

	}
	function openUpdate(obj) {

		var vo = obj.split("|");
		var parm = "group_id=" + vo[0] + "&" + "hos_id=" + vo[1] + "&"
				+ "copy_code=" + vo[2] + "&" + "cost_type_id=" + vo[3] + "&"
				+ "cost_item_id=" + vo[4] + "&" + "cost_item_no=" + vo[5]+"&tree_code="+tree_code;
		$.ligerDialog.open({
			url : 'costItemDictUpdatePage.do?isCheck=false&' + parm,
			data : {},
			height : 450,
			width : 950,
			title : '修改',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : false,
			isResize : true,
			buttons : [ {
				text : '成本项目变更',
				onclick : function(item, dialog) {
					dialog.frame.itemDictChange();
				},
				cls : 'l-dialog-btn-highlight'
			}, {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.saveCostItemDict();
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
	function loadDict() {
		//字典下拉框
		$("#cost_item_code").ligerTextBox({});
		$("#cost_item_name").ligerTextBox({});
		$("#item_grade").ligerComboBox({});
		$("#is_last").ligerComboBox({});

		autocomplete("#nature_id", "../queryDeptNature.do?isCheck=false", "id",
				"text", true, true);
		autocomplete("#supp_item_code", "../queryItemDict.do?isCheck=false",
				"id", "text", true, true);
		//成本项目来源
		autocomplete("#busi_data_source",
				"../queryDataSource.do?isCheck=false", "id", "text", true, true,{busi_data_source_type:2});
	}

	//打印数据
	function printDate() {

		$("#resultPrint > table > tbody").empty();
		//有数据直接打印
		if ($("#resultPrint > table > tbody").html() != "") {
			lodopPrinterTable("resultPrint", "成本项目开始打印", "成本项目列表", true);
			return;
		}

		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备打印数据,请稍候...');

		var printPara = {
			usePager : false,

			cost_item_code : $("#cost_item_code").val(),
			/*  cost_item_name:$("#cost_item_name").val(), */
			supp_item_code : liger.get("supp_item_code").getValue(),
			nature_id : liger.get("nature_id").getValue(),
			busi_data_source : liger.get("busi_data_source").getValue()

		};
		ajaxJsonObjectByUrl("queryCostItemDict.do", printPara, function(
				responseData) {
			$.each(responseData.Rows, function(idx, item) {
				var trHtml = "<tr>";
				trHtml += "<td>" + item.cost_item_code + "</td>";
				trHtml += "<td>" + item.cost_item_name + "</td>";
				trHtml += "<td>" + item.cost_type_name + "</td>";
				if (item.supp_item_name == 0) {
					trHtml += "<td></td>";
				} else {
					trHtml += "<td>" + item.supp_item_name + "</td>";
				}
				trHtml += "<td>" + item.nature_name + "</td>";
				trHtml += "<td>" + item.busi_data_source_name + "</td>";
				trHtml += "<td>" + item.item_grade + "</td>";

				if (item.is_last == 0) {
					trHtml += "<td>否</td>";
				} else {
					trHtml += "<td>是</td>";
				}
				if (item.is_stop == 0) {
					trHtml += "<td>否</td>";
				} else {
					trHtml += "<td>是</td>";
				}
				if (item.is_pub == 0) {
					trHtml += "<td>否</td>";
				} else {
					trHtml += "<td>是</td>";
				}
				trHtml += "</tr>";
				$("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			//alert($("#resultPrint").html())
			lodopPrinterTable("resultPrint", "成本项目开始打印", "成本项目列表", true);
		}, true, manager);
		return;
	}

	
	     function print(){
	    	
	    	if(grid.getData().length==0){
	    		
				$.ligerDialog.error("请先查询数据！");
				
				return;
			}
	    	var heads={
	 	    		//"isAuto":true,//系统默认，页眉显示页码
	 	    		"rows": [
	 		          {"cell":0,"value":"单位："+$("sessionScope.hos_name").val(),"colSpan":"5"}
	 	    	]};
	 	       var printPara={
	 	      		title: "成本项目",//标题
	 	      		columns: JSON.stringify(grid.getPrintColumns()),//表头
	 	      		class_name: "com.chd.hrp.cost.service.CostItemDictService",
	 	   			method_name: "queryCostItemDictPrint",
	 	   			bean_name: "costItemDictService",
	 	   		    heads: JSON.stringify(heads)//表头需要打印的查询条件,可以为空
	 	   			
	 	       	};
	 	      //执行方法的查询条件
	 		  $.each(grid.options.parms,function(i,obj){
	 			printPara[obj.name]=obj.value;
	  	      });
	 		
	  	     officeGridPrint(printPara);
	   		
	    }
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<div id="layout1">
		<div position="left" title="成本项目">
			<h2>
				<font id="font1">编码规则：<font id="font2" color="red">"${rules_view}"</font></font>
			</h2>
			<div class="tree">
				<ul class="ztree" id="tree"></ul>
			</div>
		</div>
		<div position="center">
			<table cellpadding="0" cellspacing="0" class="l-table-edit">

				<tr>
					<td align="right" class="l-table-edit-td"
						style="padding-left: 20px;">成本项目：</td>
					<td align="left" class="l-table-edit-td"><input
						name="cost_item_code" type="text" id="cost_item_code" /></td>
					<td align="left"></td>
					<!--             <td align="right" class="l-table-edit-td"  style="padding-left:20px;">成本项目名称：</td>
            <td align="left" class="l-table-edit-td"><input name="cost_item_name" type="text" id="cost_item_name" /></td>
            <td align="left"></td> -->
					<td align="right" class="l-table-edit-td"
						style="padding-left: 20px;">上级项目：</td>
					<td align="left" class="l-table-edit-td"><input
						name="supp_item_code" type="text" id="supp_item_code" /></td>
					<td align="left"></td>
					<td align="right" class="l-table-edit-td"
						style="padding-left: 20px;">成本习性：</td>
					<td align="left" class="l-table-edit-td"><input
						name="nature_id" type="text" id="nature_id" /></td>
					<td align="left"></td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td"
						style="padding-left: 20px;">成本项目来源：</td>
					<td align="left" class="l-table-edit-td"><input
						name="busi_data_source" type="text" id="busi_data_source" /></td>
					<td align="left"></td>
					<td align="right" class="l-table-edit-td"
						style="padding-left: 20px;" style="display: none">级次：</td>
					<td align="left" class="l-table-edit-td">
					<select id="item_grade"
						name="item_grade" style="width: 135px;">
							<option value=""> </option>
							<option value="1">一级</option>
							<option value="2">二级</option>
							<option value="3">三级</option>
							<option value="4">四级</option>
							<option value="5">五级</option>
					</select></td>
					<td align="left"></td>
				<td align="right" class="l-table-edit-td"
						style="padding-left: 20px;">是否末级：</td>
					<td align="left" class="l-table-edit-td">
					<select id="is_last"
						name="is_last" style="width: 135px;">
							<option value="">全部</option>
							<option value="0">否</option>
							<option value="1">是</option>
					</select></td>
				</tr>
				</tr>
			</table>
			<div id="maingrid"></div>
		</div>
	</div>
</body>
</html>
