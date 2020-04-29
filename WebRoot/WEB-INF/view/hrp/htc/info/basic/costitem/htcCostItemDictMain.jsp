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
	});
	
	/* 设置树形菜单 */
	function onSelect(note) {
		tree_code=note.data.code;
		query(note.data.code);
	}
	function loadTree() {
		ajaxJsonObjectByUrl("queryHtcCostItemDictByTree.do?isCheck=false", {},
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
						treeManager.collapseAll(); //全部收起
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
			value :	liger.get("item_grade").getValue()
		});
		grid.options.parms.push({
			name : 'is_last',
			value : liger.get("is_last").getValue()
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
								align : 'left',
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
								align : 'left',
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
					url : 'queryHtcCostItemDict.do',
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
							click : add_open,
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
							click : remove,
							icon : 'delete'
						}, {
							line : true
						} ]
					},
					onDblClickRow : function(rowdata, rowindex, value) {
						openUpdate(rowdata.group_id + "|" 
								+ rowdata.hos_id + "|" 
								+ rowdata.copy_code + "|"
								+ rowdata.cost_type_id + "|"
								+ rowdata.cost_item_id + "|"
								+ rowdata.cost_item_no);
					}
				});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	function add_open(){
			$.ligerDialog.open({
				url : 'htcCostItemDictAddPage.do?isCheck=false',
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
		}
	
	function remove(){
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
						$.post("deleteHtcCostItemDict.do", {
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
		}
	
		function costItemBatch(){
			$.ligerDialog.open({
				url : 'htcCostItemDictBatchPage.do?isCheck=false',
				height : 400,
				width : 500,
				title : '根据成本分类批量更新 性质 或者 项目来源 或者 分摊类别',
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

	function openUpdate(obj) {
		var vo = obj.split("|");
		var parm = "group_id=" + vo[0] + "&" + "hos_id=" + vo[1] + "&"
				+ "copy_code=" + vo[2] + "&" + "cost_type_id=" + vo[3] + "&"
				+ "cost_item_id=" + vo[4] + "&" + "cost_item_no=" + vo[5];
		$.ligerDialog.open({
			url : 'htcCostItemDictUpdatePage.do?isCheck=false&' + parm,
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
		
		autocomplete("#supp_item_code","../../base/queryHtcItemSuppDict.do?isCheck=false", "id", "text", true, true);
		//字典下拉框
		autocomplete("#nature_id","../../base/queryHtcDeptNature.do?isCheck=false", "id", "text", true, true);

		autocomplete("#busi_data_source","../../base/queryHtcDataSource.do?isCheck=false", "id", "text", true, true,{busi_data_source_type:2});/* 1：收入数据来源 2.成本数据来源(必填) */
		
		autocomplete("#item_grade","../../base/queryHtcItemGrade.do?isCheck=false", "id", "text", true, true);
		
		autocomplete("#is_last","../../base/queryHtcYearOrNo.do?isCheck=false", "id", "text", true, true);

		$("#cost_item_code").ligerTextBox({width:160});

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
	 	      		class_name: "com.chd.hrp.htc.serviceImpl.basis.HtcCostItemDictService",
	 	   			method_name: "queryHtcCostItemDictPrint",
	 	   			bean_name: "htcCostItemDictService",
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
			<h2><font id="font1">编码规则：<font id="font2" color="red">"${rules_view}"</font></font></h2>
			<div class="tree"><ul class="ztree" id="tree"></ul></div>
		</div>
		<div position="center">
			<table cellpadding="0" cellspacing="0" class="l-table-edit">
				<tr>
					<td align="right" class="l-table-edit-td"
						style="padding-left: 20px;">成本项目：</td>
					<td align="left" class="l-table-edit-td"><input
						name="cost_item_code" type="text" id="cost_item_code" /></td>
					<td align="left"></td>
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
						<input name="item_grade" type="text" id="item_grade" ltype="text"/>
					</td>
					<td align="left"></td>
				<td align="right" class="l-table-edit-td"
						style="padding-left: 20px;">是否末级：</td>
					<td align="left" class="l-table-edit-td">
						<input name="is_last" type="text" id="is_last" ltype="text"/>
					</td>
				</tr>
				</tr>


			</table>

			<div id="maingrid"></div>
		</div>
	</div>
</body>
</html>
