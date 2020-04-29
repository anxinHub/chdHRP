<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<link rel="stylesheet"
	href="<%=path%>/lib/Z-tree/css/zTreeStyle/zTreeStyle.css"
	type="text/css">
<script type="text/javascript"
	src="<%=path%>/lib/Z-tree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript"
	src="<%=path%>/lib/Z-tree/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript"
	src="<%=path%>/lib/Z-tree/js/jquery.ztree.exedit-3.5.js"></script>
<script src="<%=path%>/lib/et_components/et_plugins/etDatepicker.min.js"></script>	
<style type="text/css">
.ztree * {
	font-size: 14px;
	line-height: 20px
}

.ztree li {
	line-height: 20px
}

.mode {
	border: 1px solid #aecaf0;
	width: 346px;
	height: 70px;
	margin: 10px;
}

.check {
	border: 1px solid #aecaf0;
	margin: 10px;
	font-size: 12;
}

#checkCon table{
	font-size: 12;
}

.mode legend {
	padding: 5px;
	margin-left: 5px;
	font-size: 13px;
}

.mode div {
	text-align: center;
}
</style>
</head>
<script type="text/javascript">
	var tree, treeList;
	var accBookSch;
	var year_month ,acc_year_month1,acc_year_month2;;
	var paraDatil;
	var check_type1, check_type2, check_type3, check_type4, type_count;
	var is_check = "${is_check}";
	var page = "${page}";
	var is_dTree = 0;
	var itemPara = {
		//初始化加载queryCheckItemByTypeFy 核算项下拉框传参为0， 由于不传参时会报错，为无效类型
		check_type_id : 0
	};
	$(function() {
		loadDict();
		loadTree();
		loadTreeList();
		$("#layout1").ligerLayout({leftWidth : '160', height: $(window).height() - 3, });
    	$("#tree").css("height", $(window).height()-80);

		if (is_check == 0) {
			$("#check").hide();
		}

		$("#detail").click(function() {
			$("#generalCon").show();
			$("#subjectCon").hide();
			$("#checkCon").hide();
		});

		$("#file").click(function() {
			$("#subjectCon").show();
			if(is_dTree == 0){
				$("#subjectCon").ligerLayout({height: $(window).height() - 30, bottomHeight : 60});
				is_dTree = 1;
			}
			$("#generalCon").hide();
			$("#checkCon").hide();
		});
		$("#check").click(function() {
			$("#subjectCon").hide();
			$("#generalCon").hide();
			$("#checkCon").show();
		});
	});
	var setting = {
		data : {
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "pId",
				rootPId : 0
			},
			keep : {
				leaf : true
			},
			key : {
				children : "nodes"
			}
		},
		edit : {
			enable : true,
			removeTitle : '删除',
			renameTitle : '修改',
			showRemoveBtn : setShowBtn,
			showRenameBtn : setShowBtn

		},
		treeNode : {
			open : true
		},
		callback : {
			beforeRemove : function(event, treeId, node) {
				if (!treeId.isParent && treeId.pId != 0) {
					var state = confirm("确认删除方案 " + treeId.name + " 吗？");
					if (state) {
						ajaxJsonObjectByUrl("deleteAccBookSch.do?isCheck=false&sch_id=" + treeId.id, {}, function(res) {
							if (res.state == "true") {
								loadTree();
								return true;
							} else {
								return false;
							}
						});
					}else{
						return false;
					}
				}
			},
			beforeEditName : function(event, treeId, node) {
				var bookSch = {
					sch_id : treeId.id,
					che_name : treeId.name,
					che_group : treeId.che_group,
					che_type : treeId.che_type
				};
				newBuild(bookSch);
				return false;
			},
			onNodeCreated : function(event, treeId, node) {
				//console.log(node)
				var treeObj = $.fn.zTree.getZTreeObj("tree");
				var treeNodes = treeObj.getNodes();

				treeObj.expandNode(node, true, false, false);

				if (node.nodes && node.level === 0 && node.nodes.length === 0) {
					treeObj.hideNode(node);
				}
			},
			onClick : function(event, treeId, node) {
				if (!node.isParent && node.pId != 0) {
					//初始化右侧元素
					initRightLayout();
					$("#sch_id").val(node.id);
					$("#sch_name").val(node.name);
					var params = {
						sch_id : node.id
					};
					ajaxJsonObjectByUrl("queryAccBookSchDetailById.do?isCheck=false", params, function(res) {
						if (res.state == "true") {
							$("#con_id").val(res.con_id);
							if(res.che_hos_id){
								liger.get("che_hos_id").setValue(res.che_hos_id);
							}
							if(res.che_copy_code){
								liger.get("che_copy_code").setValue(res.che_copy_code);
							}
							if(res.cur_code){
								liger.get("cur_code").setValue(res.cur_code);
							}
							if(res.subj_level_begin){
								liger.get("subj_level_begin").setValue(res.subj_level_begin);
							}
							if(res.subj_level_end){
								liger.get("subj_level_end").setValue(res.subj_level_end);
							}
							if (res.is_last == 1) {
								liger.get("is_last").setValue(true);
								//$("#is_last").prop("checked", "checked");
							} else {
								liger.get("is_last").setValue(false);
								//$("#is_last").prop("checked", false);
							}
							if (res.is_nacc == 1) {
								liger.get("is_nacc").setValue(true);
								//$("#is_nacc").attr("checked", "checked");
							} else {
								liger.get("is_nacc").setValue(false);
								//$("#is_nacc").attr("checked", false);
							}
							if (res.is_bqwfs == 1) {
								liger.get("is_bqwfs").setValue(true);
								//$("#is_bqwfs").prop("checked", "checked");
							} else {
								liger.get("is_bqwfs").setValue(false);
								//$("#is_bqwfs").prop("checked", false);
							}
							if (res.is_fw == 1) {
								liger.get("is_fw").setValue(true);
								//$("#is_fw").prop("checked", "checked");
								$("#munSearch").show();
								liger.get("subj_code_begin").setValue(res.subj_code_begin);
								liger.get("subj_code_begin").setText(res.subj_code_begin + " " + res.subj_name_begin);
								liger.get("subj_code_end").setValue(res.subj_code_end);
								liger.get("subj_code_end").setText(res.subj_code_end + " " + res.subj_name_end);
							} else {
								liger.get("is_fw").setValue(false);
								//$("#is_fw").prop("checked", false);
								$("#munSearch").hide();
								loadModeTreeData(res.subj_code);
							}

							if(is_check != 0){
								setTimeout(function() {
									if (res.check_item_type1) {
										liger.get("check_item_type1").setValue(res.check_item_type1);
										liger.get("check_item_code1_b").setValue(res.check_item_code1_b);
										liger.get("check_item_code1_b").setText(res.check_item_name1_b);
										liger.get("check_item_code1_e").setValue(res.check_item_code1_e);
										liger.get("check_item_code1_e").setText(res.check_item_name1_e);
									}
	
									if (res.check_item_type2) {
										liger.get("check_item_type2").setValue(res.check_item_type2);
										liger.get("check_item_code2_b").setValue(res.check_item_code2_b);
										liger.get("check_item_code2_b").setText(res.check_item_name2_b);
										liger.get("check_item_code2_e").setValue(res.check_item_code2_e);
										liger.get("check_item_code2_e").setText(res.check_item_name2_e);
									}
	
									if (res.check_item_type3) {
										liger.get("check_item_type3").setValue(res.check_item_type3);
										liger.get("check_item_code3_b").setValue(res.check_item_code3_b);
										liger.get("check_item_code3_b").setText(res.check_item_name3_b);
										liger.get("check_item_code3_e").setValue(res.check_item_code3_e);
										liger.get("check_item_code3_e").setText(res.check_item_name3_e);
									}
	
									if (res.check_item_type4) {
										liger.get("check_item_type4").setValue(res.check_item_type4);
										liger.get("check_item_code4_b").setValue(res.check_item_code4_b);
										liger.get("check_item_code4_b").setText(res.check_item_name4_b);
										liger.get("check_item_code4_e").setValue(res.check_item_code4_e);
										liger.get("check_item_code4_e").setText(res.check_item_name4_e);
									}
								}, 2000)
							}
						}
					});
				}
			},
		},
		view : {
			dblClickExpand : false
		}
	};

	//是否显示按钮
	function setShowBtn(treeId, treeNode) {
		if(treeNode.id == "1" || treeNode.id == "2"){
			return false;
		}
		return !treeNode.isParent;
	}

	//初始化右侧元素
	function initRightLayout() {
		$("#generalCon").show();
		$("#subjectCon").hide();
		$("#checkCon").hide();
		checkAllSubj(false);
		liger.get("subj_code_begin").clear();
		liger.get("subj_code_end").clear();
	}

	var setting1 = {
		data : {
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "pId",
				rootPId : 0
			},
			keep : {
				leaf : true
			},
			key : {
				children : "nodes"
			}
		},
		treeNode : {
			open : true
		},
		check : {
			enable : true,
			chkStyle : "checkbox", //复选框
			chkboxType : {
				"Y" : "ps",
				"N" : "ps"
			}
		},
		callback : {
			onNodeCreated : function(event, treeId, node) {
				var treeObj = $.fn.zTree.getZTreeObj("treeList");
				var treeNodes = treeObj.getNodes();

				if (node.nodes && node.level === 0 && node.nodes.length === 0) {
					treeObj.hideNode(node);
				}
			},
		},
		view : {
			dblClickExpand : false
		}
	}

	//选中or取消所有
	function checkAllSubj(flag) {
		var treeObj = $.fn.zTree.getZTreeObj("treeList");
		treeObj.checkAllNodes(flag);
		if (!flag) {
			//如果是取消选中，则收起所有节点
			treeObj.expandAll(flag);
		}
	}

	//加载分析方式树
	function loadModeTreeData(subj_code) {
		//如果有值就让树中内容选中
		if (subj_code && subj_code.length > 0) {
			var treeObj = $.fn.zTree.getZTreeObj("treeList");
			$.each(subj_code, function(index, item) {
				var treeNode = treeObj.getNodesByParam("id", item.subj_code, null);
				treeObj.checkNode(treeNode[0], true, true);
			})
		}
	}

	//加载左侧树
	function loadTree() {
		ajaxJsonObjectByUrl("queryAccBookSchListForTree.do?isCheck=false", {is_check: is_check, page: page},
			function(responseData) {
				//console.log(JSON.stringify(responseData))  
				tree = $.fn.zTree.init($("#tree"), setting, responseData.Rows);
			});
	};
	//加载科目条件树
	function loadTreeList() {
		ajaxJsonObjectByUrl("queryAccSubjListForTree.do?isCheck=false", "",
			function(responseData) {
				treeList = $.fn.zTree.init($("#treeList"), setting1, responseData.Rows);
			});
	};

	//页面初始化
	function loadDict() {
		//单位
		var count = 0;
		$("#che_hos_id").ligerComboBox({
			url : "../querySysHosAll.do?isCheck=false",
			valueField : "id",
			textField : "text",
			selectBoxWidth : '260',
			selectBoxHeight : '260',
			setTextBySource : true,
			width : '260',
			autocomplete : true,
			highLight : true,
			keySupport : true,
			async : true,
			alwayShowInDown : true,
			isShowCheckBox : true,
			isMultiSelect : false,
			/* onSuccess: function (data) {
				if (count == 0) {this.setValue(data[0].id);}
				count++;
			}  */
		});

		/* $("#che_hos_id").bind('change',function(){
			liger.get("che_copy_code").clear();
			if(liger.get("che_hos_id").getValue()){
				liger.get("che_copy_code").set("parms", {"hos_id": liger.get("che_hos_id").getValue()});
				liger.get("che_copy_code").reload();
			}else{
				liger.get("che_copy_code").set("parms", {"hos_id":""});
				liger.get("che_copy_code").reload();
			}
		}); */
		//账套
		var count1 = 0;
		$("#che_copy_code").ligerComboBox({
			url : "../querySysHosCopyAll.do?isCheck=false",
			valueField : "id",
			textField : "text",
			//parms: {"che_hos_id": liger.get("che_hos_id").getValue()}, 
			selectBoxWidth : '260',
			selectBoxHeight : '260',
			setTextBySource : true,
			width : '260',
			autocomplete : true,
			highLight : true,
			keySupport : true,
			async : true,
			alwayShowInDown : true,
			isShowCheckBox : true,
			isMultiSelect : true,
			onSuccess: function (data) {
				if (count1 == 0) {this.setValue(data[0].id);}
				count1++;
			} 
		});
		//币种
		var count2 = 0;
		$("#cur_code").ligerComboBox({
			url : "../queryCur.do?isCheck=false",
			valueField : "id",
			textField : "text",
			selectBoxWidth : '260',
			selectBoxHeight : '260',
			setTextBySource : true,
			width : '260',
			autocomplete : true,
			highLight : true,
			keySupport : true,
			async : true,
			alwayShowInDown : true,
			parms : {
				pageSize : 100
			},
			onSuccess: function (data) {
				if (count2 == 0) {this.setValue(data[0].id);}
				count2++;
			} 
		});

		//会计期间
		/* year_month = $("#year_month").etDatepicker({
			range : true,
			view : "months",
			minView : "months",
			dateFormat : "yyyy.mm",
			defaultDate : [ '${yearMonth}', '${yearMonth}' ],
			onShow : function(picker) {
				picker.update({
					minDate : new Date(parent.sessionJson["min_date"]),
					maxDate : new Date(parent.sessionJson["max_date"]),
				})
			},
		}); */
		acc_year_month1 = $("#acc_year_month1").etDatepicker({
			range: false,
			view: "months",
			minView: "months",
			dateFormat: "yyyy.mm",
			defaultDate: ['${yearMonth}']
		});
		acc_year_month2 = $("#acc_year_month2").etDatepicker({
			range: false,
			view: "months",
			minView: "months",
			dateFormat: "yyyy.mm",
			defaultDate: ['${yearMonth}']
		});

		//级次
		$("#subj_level_begin").ligerComboBox({
			url : "../querySubjLevel.do?isCheck=false",
			valueField : "id",
			textField : "text",
			selectBoxWidth : '60',
			selectBoxHeight : '140',
			setTextBySource : true,
			width : '60',
			autocomplete : true,
			highLight : true,
			keySupport : true,
			async : true,
			alwayShowInDown : true,
		});

		$("#subj_level_end").ligerComboBox({
			url : "../querySubjLevel.do?isCheck=false",
			valueField : "id",
			textField : "text",
			selectBoxWidth : '60',
			selectBoxHeight : '140',
			setTextBySource : true,
			width : '60',
			autocomplete : true,
			highLight : true,
			keySupport : true,
			async : true,
			alwayShowInDown : true,
		});
		$("#is_last").ligerCheckBox();
		$("#is_nacc").ligerCheckBox();
		$("#is_bqwfs").ligerCheckBox();
		$("#is_fw").ligerCheckBox();
		$("#subj_code_begin").ligerComboBox({
			url : "../querySubj.do?isCheck=false",
			valueField : "id",
			textField : "text",
			selectBoxWidth : '160',
			selectBoxHeight : '150',
			setTextBySource : true,
			width : '160',
			autocomplete : true,
			highLight : true,
			keySupport : true,
			async : true,
			alwayShowInTop : true
		});
		$("#subj_code_end").ligerComboBox({
			url : "../querySubj.do?isCheck=false",
			valueField : "id",
			textField : "text",
			selectBoxWidth : '160',
			selectBoxHeight : '150',
			setTextBySource : true,
			width : '160',
			autocomplete : true,
			highLight : true,
			keySupport : true,
			async : true,
			alwayShowInTop : true
		});
		
		if(is_check != 0){
			autocompleteObj({
				id : '#check_item_type1',
				urlStr : "../queryCheckTypeBySubjCode.do?isCheck=false",
				valueField : 'id',
				textField : 'text',
				autocomplete : true,
				highLight : true,
				defaultSelect : false,
				selectEvent : function(value) {
	
					var param = {
						check_type_id : value
					};
	
					liger.get("check_item_code1_b").clear();
					liger.get("check_item_code1_b").set("parms", param);
					liger.get("check_item_code1_b").reload();
					liger.get("check_item_code1_e").clear();
					liger.get("check_item_code1_e").set("parms", param);
					liger.get("check_item_code1_e").reload();
				}
			});
			autocompleteObj({
				id : '#check_item_code1_b',
				urlStr : "../queryCheckItemByTypeFy.do?isCheck=false",
				valueField : 'id',
				textField : 'text',
				autocomplete : true,
				highLight : true,
				defaultSelect : false,
				boxwidth: subjWidth, 
				selectEvent : function(value) {
					liger.get("check_item_code1_e").clear();
					liger.get("check_item_code1_e").setValue(value);
					liger.get("check_item_code1_e").setText(liger.get("check_item_code1_b").getText());
				}
			});
			autocomplete("#check_item_code1_e",
					"../queryCheckItemByTypeFy.do?isCheck=false", "id", "text",
					true, true, "", false, false, "", "", subjWidth);
	
			autocompleteObj({
				id : '#check_item_type2',
				urlStr : "../queryCheckTypeBySubjCode.do?isCheck=false",
				valueField : 'id',
				textField : 'text',
				autocomplete : true,
				highLight : true,
				parmsStr : paraDatil,
				defaultSelect : false,
				selectEvent : function(value) {
	
					var param = {
						check_type_id : value
					};
					liger.get("check_item_code2_b").clear();
					liger.get("check_item_code2_b").set("parms", param);
					liger.get("check_item_code2_b").reload();
					liger.get("check_item_code2_e").clear();
					liger.get("check_item_code2_e").set("parms", param);
					liger.get("check_item_code2_e").reload();
				}
			});
			autocompleteObj({
				id : '#check_item_code2_b',
				urlStr : "../queryCheckItemByTypeFy.do?isCheck=false",
				valueField : 'id',
				textField : 'text',
				autocomplete : true,
				highLight : true,
				defaultSelect : false,
				boxwidth: subjWidth, 
				selectEvent : function(value) {
					liger.get("check_item_code2_e").clear();
					liger.get("check_item_code2_e").setValue(value);
					liger.get("check_item_code2_e").setText(liger.get("check_item_code2_b").getText());
				}
			});
			autocomplete("#check_item_code2_e",
					"../queryCheckItemByTypeFy.do?isCheck=false", "id", "text",
					true, true, "", false, false, "", "", subjWidth);
	
			autocompleteObj({
				id : '#check_item_type3',
				urlStr : "../queryCheckTypeBySubjCode.do?isCheck=false",
				valueField : 'id',
				textField : 'text',
				autocomplete : true,
				highLight : true,
				parmsStr : paraDatil,
				defaultSelect : false,
				selectEvent : function(value) {
	
					var param = {
						check_type_id : value
					};
					liger.get("check_item_code3_b").clear();
					liger.get("check_item_code3_b").set("parms", param);
					liger.get("check_item_code3_b").reload();
					liger.get("check_item_code3_e").clear();
					liger.get("check_item_code3_e").set("parms", param);
					liger.get("check_item_code3_e").reload();
				}
			});
			autocompleteObj({
				id : '#check_item_code3_b',
				urlStr : "../queryCheckItemByTypeFy.do?isCheck=false",
				valueField : 'id',
				textField : 'text',
				autocomplete : true,
				highLight : true,
				defaultSelect : false,
				boxwidth: subjWidth, 
				selectEvent : function(value) {
					liger.get("check_item_code3_e").clear();
					liger.get("check_item_code3_e").setValue(value);
					liger.get("check_item_code3_e").setText(liger.get("check_item_code3_b").getText());
				}
			});
			autocomplete("#check_item_code3_e",
					"../queryCheckItemByTypeFy.do?isCheck=false", "id", "text",
					true, true, "", false, false, "", "", subjWidth);
	
			autocompleteObj({
				id : '#check_item_type4',
				urlStr : "../queryCheckTypeBySubjCode.do?isCheck=false",
				valueField : 'id',
				textField : 'text',
				autocomplete : true,
				highLight : true,
				parmsStr : paraDatil,
				defaultSelect : false,
				selectEvent : function(value) {
	
					var param = {
						check_type_id : value
					};
					liger.get("check_item_code4_b").clear();
					liger.get("check_item_code4_b").set("parms", param);
					liger.get("check_item_code4_b").reload();
					liger.get("check_item_code4_e").clear();
					liger.get("check_item_code4_e").set("parms", param);
					liger.get("check_item_code4_e").reload();
				}
			});
			autocompleteObj({
				id : '#check_item_code4_b',
				urlStr : "../queryCheckItemByTypeFy.do?isCheck=false",
				valueField : 'id',
				textField : 'text',
				autocomplete : true,
				highLight : true,
				defaultSelect : false,
				boxwidth: subjWidth, 
				selectEvent : function(value) {
					liger.get("check_item_code4_e").clear();
					liger.get("check_item_code4_e").setValue(value);
					liger.get("check_item_code4_e").setText(liger.get("check_item_code4_b").getText());
				}
			});
			autocomplete("#check_item_code4_e",
					"../queryCheckItemByTypeFy.do?isCheck=false", "id", "text",
					true, true, "", false, false, "", "", subjWidth);
		
		}

	};
	//新建
	function newBuild(obj) {
		accBookSch = obj;
		parent.$.ligerDialog.open({
			url : 'hrp/acc/accbooksch/accBookSchAddPage.do?isCheck=false&is_check='+is_check+'&page='+page,
			data : {},
			height : 300,
			width : 350,
			title : '新建方案',
			modal : true,
			showToggle : false,
			showMin : false,
			isResize : true,
			parentframename : window.name,
		});
	};
	//保存
	function saveSch(obj) {

		if (obj == 0) {
			var sch_id = $("#sch_id").val();
			if (!sch_id) {
				$.ligerDialog.warn('请选择方案（如左侧无方案可选，请新建方案）！');
				return;
			}
			var con_id = $("#con_id").val();
			var che_hos_id = 0;//liger.get("che_hos_id").getValue();
			var che_copy_code = liger.get("che_copy_code").getValue();
			var cur_code = liger.get("cur_code").getValue();
			var subj_level_begin = liger.get("subj_level_begin").getValue();
			var subj_level_end = liger.get("subj_level_end").getValue();
			var is_last = $("#is_last").prop("checked") ? 1 : 0;
			var is_nacc = $("#is_nacc").prop("checked") ? 1 : 0;
			var is_bqwfs = $("#is_bqwfs").prop("checked") ? 1 : 0;
			var is_fw = $("#is_fw").prop("checked") ? 1 : 0;
			/* if(che_hos_id==""){
				$.ligerDialog.error('单位为必填项，不能为空！');
				return;
			} */
			if (che_copy_code == "") {
				$.ligerDialog.error('账套为必填项，不能为空！');
				return;
			}
			if (cur_code == "") {
				$.ligerDialog.error('币种为必填项，不能为空！');
				return;
			}
			//拼装科目
			var subj_code = [];
			var subj_code_begin = "";
			var subj_code_end = "";
			if (is_fw == 1) {
				subj_code_begin = liger.get("subj_code_begin").getValue();
				subj_code_end = liger.get("subj_code_end").getValue();
				if (subj_code_begin && subj_code_end) {
					if (subj_code_end < subj_code_begin) {
						$.ligerDialog.error('按范围查询时开始科目不能大于结束科目');
						return;
					}
				} else {
					$.ligerDialog.error('按范围查询时科目范围不能为空');
					return;
				}
			} else {
				//解析科目树形
				var data = treeList.getCheckedNodes(true);
				if (data.length == 0) {
					$.ligerDialog.error('请选择需要查询的科目');
					return;
				}
				$.each(data, function(index, value) {
					var param = {};
					if (!value.isParent || value.pId != 0) {
						if (value.pId != 0) {
							subj_code.push({
								"subj_code" : value.id
							});
						}
					}
				});
			}

			if (is_check != 0) {
				check_type1 = liger.get("check_item_type1").getValue();
				if (check_type1 == "") {
					$.ligerDialog.error('核算类1为必填项');
					return;
				}
				check_type2 = liger.get("check_item_type2") == null ? "" : liger
						.get("check_item_type2").getValue();
				if (type_count >= 2 && check_type2 == "") {
					$.ligerDialog.error('核算类2为必填项');
					return;
				}
				check_type3 = liger.get("check_item_type3") == null ? "" : liger
						.get("check_item_type3").getValue();
				if (type_count >= 3 && check_type3 == "") {
					$.ligerDialog.error('核算类3为必填项');
					return;
				}
				check_type4 = liger.get("check_item_type4") == null ? "" : liger
						.get("check_item_type4").getValue();
				if (type_count >= 4 && check_type4 == "") {
					$.ligerDialog.error('核算类4为必填项');
					return;
				}
	
				var check_item1_b = liger.get("check_item_code1_b").getValue();
				var check_item1_e = liger.get("check_item_code1_e").getValue();
	
				var check_item2_b = liger.get("check_item_code2_b") == null ? ""
						: liger.get("check_item_code2_b").getValue();
				var check_item2_e = liger.get("check_item_code2_b") == null ? ""
						: liger.get("check_item_code2_e").getValue();
	
				var check_item3_b = liger.get("check_item_code3_b") == null ? ""
						: liger.get("check_item_code3_b").getValue();
				var check_item3_e = liger.get("check_item_code3_b") == null ? ""
						: liger.get("check_item_code3_e").getValue();
	
				var check_item4_b = liger.get("check_item_code4_b") == null ? ""
						: liger.get("check_item_code4_b").getValue();
				var check_item4_e = liger.get("check_item_code4_b") == null ? ""
						: liger.get("check_item_code4_e").getValue();
			}

			var para = {
				sch_id : sch_id,
				con_id : con_id,
				che_hos_id : che_hos_id,
				che_copy_code : che_copy_code,
				cur_code : cur_code,
				subj_level_begin : subj_level_begin,
				subj_level_end : subj_level_end,
				is_last : is_last, 
				is_nacc : is_nacc, 
				is_bqwfs : is_bqwfs, 
				is_fw : is_fw, 
				is_check : is_check, 
				page: page, 
				subj_code_begin : subj_code_begin,
				subj_code_end : subj_code_end,
				subj_code : JSON.stringify(subj_code),
				check_type1 : check_type1,
				check_item1_b : check_item1_b,
				check_item1_e : check_item1_e,
				check_type2 : check_type2,
				check_item2_b : check_item2_b,
				check_item2_e : check_item2_e,
				check_type3 : check_type3,
				check_item3_b : check_item3_b,
				check_item3_e : check_item3_e,
				check_type4 : check_type4,
				check_item4_b : check_item4_b,
				check_item4_e : check_item4_e
			};
			ajaxJsonObjectByUrl("saveAccBookSchDetail.do?isCheck=false", para,
					function(res) {
						if (res.state == "true") {
							$("#con_id").val(res.con_id);
							//parentFrameUse().liger.get("sch_id").reload();
						}
					}, false);
		}else if (obj == 1) {
			//已选择方案可以直接查询
			/* if($("#sch_id").val()){
				
				parentFrameUse().acc_year_month1.setValue(year_month.getValue());
				parentFrameUse().query();
				parentFrameUse().liger.get("sch_id").setValue($("#sch_id").val());
				parentFrameUse().liger.get("sch_id").setText($("#sch_id").val($("#sch_name")));
				thisClose();
			}else{ */
				//没选择方案则添加默认方案再查询
				var sch_id = 1;
				var che_hos_id = 0;//liger.get("che_hos_id").getValue();
				var che_copy_code = liger.get("che_copy_code").getValue();
				var cur_code = liger.get("cur_code").getValue();
				var subj_level_begin = liger.get("subj_level_begin").getValue();
				var subj_level_end = liger.get("subj_level_end").getValue();
				var is_last = $("#is_last").prop("checked") ? 1 : 0;
				var is_nacc = $("#is_nacc").prop("checked") ? 1 : 0;
				var is_bqwfs = $("#is_bqwfs").prop("checked") ? 1 : 0;
				var is_fw = $("#is_fw").prop("checked") ? 1 : 0;
	
				/* if(che_hos_id==""){
					$.ligerDialog.error('单位为必填项，不能为空！');
					return;
				} */
				if (che_copy_code == "") {
					$.ligerDialog.error('账套为必填项，不能为空！');
					return;
				}
				if (cur_code == "") {
					$.ligerDialog.error('币种为必填项，不能为空！');
					return;
				}
				//拼装科目
				var subj_code = [];
				var subj_code_begin = "";
				var subj_code_end = "";
				if (is_fw == 1) {
					subj_code_begin = liger.get("subj_code_begin").getValue();
					subj_code_end = liger.get("subj_code_end").getValue();
					if (subj_code_begin && subj_code_end) {
						if (subj_code_end < subj_code_begin) {
							$.ligerDialog.error('按范围查询时开始科目不能大于结束科目');
							return;
						}
					} else {
						$.ligerDialog.error('按范围查询时科目范围不能为空');
						return;
					}
				} else {
					//解析科目树形
					var data = treeList.getCheckedNodes(true);
					if (data.length == 0) {
						$.ligerDialog.error('请选择需要查询的科目');
						return;
					}
					$.each(data, function(index, value) {
						var param = {};
						if (!value.isParent || value.pId != 0) {
							if (value.pId != 0) {
								subj_code.push({
									"subj_code" : value.id
								});
							}
						}
					});
				}
	
				if(is_check != 0){
					check_type1 = liger.get("check_item_type1").getValue();
					if (check_type1 == "") {
						$.ligerDialog.error('核算类1为必填项');
						return;
					}
					check_type2 = liger.get("check_item_type2") == null ? "" : liger
							.get("check_item_type2").getValue();
					if (type_count >= 2 && check_type2 == "") {
						$.ligerDialog.error('核算类2为必填项');
						return;
					}
					check_type3 = liger.get("check_item_type3") == null ? "" : liger
							.get("check_item_type3").getValue();
					if (type_count >= 3 && check_type3 == "") {
						$.ligerDialog.error('核算类3为必填项');
						return;
					}
					check_type4 = liger.get("check_item_type4") == null ? "" : liger
							.get("check_item_type4").getValue();
					if (type_count >= 4 && check_type4 == "") {
						$.ligerDialog.error('核算类4为必填项');
						return;
					}
		
					var check_item1_b = liger.get("check_item_code1_b").getValue();
					var check_item1_e = liger.get("check_item_code1_e").getValue();
		
					var check_item2_b = liger.get("check_item_code2_b") == null ? ""
							: liger.get("check_item_code2_b").getValue();
					var check_item2_e = liger.get("check_item_code2_b") == null ? ""
							: liger.get("check_item_code2_e").getValue();
		
					var check_item3_b = liger.get("check_item_code3_b") == null ? ""
							: liger.get("check_item_code3_b").getValue();
					var check_item3_e = liger.get("check_item_code3_b") == null ? ""
							: liger.get("check_item_code3_e").getValue();
		
					var check_item4_b = liger.get("check_item_code4_b") == null ? ""
							: liger.get("check_item_code4_b").getValue();
					var check_item4_e = liger.get("check_item_code4_b") == null ? ""
							: liger.get("check_item_code4_e").getValue();
				}
				var para = {
					sch_id : sch_id,
					che_hos_id : che_hos_id,
					che_copy_code : che_copy_code,
					cur_code : cur_code,
					subj_level_begin : subj_level_begin,
					subj_level_end : subj_level_end,
					is_last : is_last,
					is_nacc : is_nacc,
					is_bqwfs : is_bqwfs,
					is_fw : is_fw,
					is_check : is_check, 
					page: page, 
					subj_code_begin : subj_code_begin,
					subj_code_end : subj_code_end,
					subj_code : JSON.stringify(subj_code),
					check_type1 : check_type1,
					check_item1_b : check_item1_b,
					check_item1_e : check_item1_e,
					check_type2 : check_type2,
					check_item2_b : check_item2_b,
					check_item2_e : check_item2_e,
					check_type3 : check_type3,
					check_item3_b : check_item3_b,
					check_item3_e : check_item3_e,
					check_type4 : check_type4,
					check_item4_b : check_item4_b,
					check_item4_e : check_item4_e
				};
	
				ajaxJsonObjectByUrl("saveAccBookSchDetail.do?isCheck=false", para, function(res) {
					if (res.state == "true") {
						//con_id.val(res.con_id);
						if (obj) {
							parentFrameUse().liger.get("sch_id").setValue("1");
							parentFrameUse().liger.get("sch_id").setText("");
	
							parentFrameUse().acc_year_month1.setValue(acc_year_month1.getValue());
							parentFrameUse().acc_year_month2.setValue(acc_year_month2.getValue());
	
							parentFrameUse().query();
	
							//删除默认的方案
							setTimeout(function() {ajaxJsonObjectByUrl("deleteAccBookSch.do?isCheck=false&sch_id=1");}, 2000);
	
							parentFrameUse().liger.get("sch_id").setValue("");
							thisClose();
						}
					}
				}, false);
			/* } */
		}
	}

	//按范围查询
	function onBetween() {

		if ($("#is_fw").prop("checked") == true) {
			$("#munSearch").show();
		} else {
			$("#munSearch").hide();
		}
	}

	function thisClose() {
		// 保存成功后的回调
		/* etDialog写法 */
		/* var curIndex = parent.$.etDialog.getFrameIndex(window.name);
		parent.$.etDialog.close(curIndex); */
		/* ligerDialog写法 */
		frameElement.dialog.close();
	}
	
	//增加第二行
	function addSecond(paraDatil){
		autocompleteObj({
			id:  '#check_item_type2',                   
			urlStr: 	"../queryCheckTypeBySubjCode.do?isCheck=false",							
			valueField:  'id',            
			textField:    'text' ,            
			autocomplete: true,			
			highLight: true,
			parmsStr: paraDatil,
			defaultSelect:  false,
			initWidth:220,
			selectEvent: function(value){
				 
				var param = {
					check_type_id:  value
	     		}; 
				liger.get("check_item_code2_b").clear();
				liger.get("check_item_code2_b").set("parms", param);
				liger.get("check_item_code2_b").reload();
				liger.get("check_item_code2_e").clear();
				liger.get("check_item_code2_e").set("parms", param);
				liger.get("check_item_code2_e").reload();
		    }
		});	
		
		autocomplete("#check_item_code2_b","../queryCheckItemByTypeFy.do?isCheck=false","id","text",true,true);
		autocomplete("#check_item_code2_e","../queryCheckItemByTypeFy.do?isCheck=false","id","text",true,true);
	};
	
	//增加第三行
	function addThird(paraDatil){
		autocompleteObj({
			id:  '#check_item_type3',                   
			urlStr: 	"../queryCheckTypeBySubjCode.do?isCheck=false",							
			valueField:  'id',            
			textField:    'text' ,            
			autocomplete: true,			
			highLight: true,
			parmsStr: paraDatil,
			defaultSelect:  false,
			initWidth:220,
			selectEvent: function(value){
				 
				var param = {
					check_type_id:  value
	     		}; 
				liger.get("check_item_code3_b").clear();
				liger.get("check_item_code3_b").set("parms", param);
				liger.get("check_item_code3_b").reload();
				liger.get("check_item_code3_e").clear();
				liger.get("check_item_code3_e").set("parms", param);
				liger.get("check_item_code3_e").reload();
		    }
		});
		
		autocomplete("#check_item_code3_b","../queryCheckItemByTypeFy.do?isCheck=false","id","text",true,true);
		autocomplete("#check_item_code3_e","../queryCheckItemByTypeFy.do?isCheck=false","id","text",true,true);
	};
	
	//增加第四行
	function addFourth(paraDatil){
		autocompleteObj({
			id:  '#check_item_type4',                   
			urlStr: 	"../queryCheckTypeBySubjCode.do?isCheck=false",							
			valueField:  'id',            
			textField:    'text' ,            
			autocomplete: true,			
			highLight: true,
			parmsStr: paraDatil,
			defaultSelect: false,
			initWidth:220,
			selectEvent: function(value){
				 
				var param = {
					check_type_id:  value
	     		}; 
				liger.get("check_item_code4_b").clear();
				liger.get("check_item_code4_b").set("parms", param);
				liger.get("check_item_code4_b").reload();
				liger.get("check_item_code4_e").clear();
				liger.get("check_item_code4_e").set("parms", param);
				liger.get("check_item_code4_e").reload();
		    }
		});
		
		autocomplete("#check_item_code4_b","../queryCheckItemByTypeFy.do?isCheck=false","id","text",true,true);
		autocomplete("#check_item_code4_e","../queryCheckItemByTypeFy.do?isCheck=false","id","text",true,true);
	};
	
	function addLine1(){
		$('#attend2').show();
		$('#add1').hide();
		//addSecond(paraDatil);
		type_count = 2;
	};
	function addLine2(){
		$('#attend3').show();
		$('#add2').hide();
		$('#delete1').hide();
		//addThird(paraDatil);
		type_count = 3;
	};
	function addLine3(){
		$('#attend4').show();
		$('#add3').hide();
		$('#delete2').hide();
		//addFourth(paraDatil);
		type_count = 4;
	};
	function deleteLine1(){
		
		liger.get("check_item_type2").clear();
		
		liger.get("check_item_code2_b").clear();
		
		liger.get("check_item_code2_e").clear();
		
		$('#attend2').hide();
		$('#add1').show();
		type_count = 1;
	};
	function deleteLine2(){
		liger.get("check_item_type3").clear();
		liger.get("check_item_code3_b").clear();
		liger.get("check_item_code3_e").clear();
		
		$('#attend3').hide();
		$('#add2').show();
		$('#delete1').show();
		type_count = 2;
	};
	function deleteLine3(){
		liger.get("check_item_type4").clear();
		liger.get("check_item_code4_b").clear();
		liger.get("check_item_code4_e").clear();
		
		$('#attend4').hide();
		$('#add3').show();
		$('#delete2').show();
		type_count = 3;
	};
</script>

<body>
	<input type="hidden" value="${listBoxData}" id="boxList" name="boxList" />
	<div id="layout1" style="width:100%;margin:0; padding:0;">
		<div position="left" title="方案列表">
			<div class="l-layout-header">
				<div class="l-layout-header-inner">
					<button class="l-button l-button-test" onclick="newBuild(null);">新建</button>
				</div>
			</div>
			<div class="l-layout-content">
				<div style="overflow: auto;" id="treeDiv">
					<ul class="ztree" id="tree"></ul>
				</div>
			</div>
		</div>

		<div position="center" >
			<div class="l-layout-header">
				<div class="l-layout-header-inner" id="layoutHeader">
					<input class="liger-button" name="detail" id="detail" type="button" width="100" value="一般条件" /> 
					<input class="liger-button" name="file" id="file" type="button" width="100" value="科目条件" />
					<input class="liger-button" name="check" id="check" type="button" width="100" value="辅助核算条件" />
				</div>
			</div>
			<div position="center" title="" class="l-layout-content" 
				style="height: inherit;" id="layoutCenter">
				<div id="generalCon" style="padding: 0 60px">
					<fieldset id="debit" class="mode">
						<legend>页面条件</legend>
						<div>
							<span><font size="2" color="red">*</font>会计期间：</span>
						    	<tr>
									<td align="left" class="l-table-edit-td">
									<input class="Wdate" name="acc_year_month1" type="text" 
										id="acc_year_month1" ltype="text" style="width: 90px;" />
									</td>
									<td align="left" class="l-table-edit-td">至</td>
									<td align="left" class="l-table-edit-td">
									<input class="Wdate" name="acc_year_month2" type="text" 
										id="acc_year_month2" ltype="text" style="width: 90px;" />
									</td>
									<td align="left"></td>
								</tr>
						</div>
					</fieldset>
					<input id="sch_id" type="hidden" /> 
					<input id="sch_name" type="hidden" />
					<table cellpadding="0" cellspacing="0" class="l-table-edit">
						<tr style="display: none;">
							<td align="right" class="l-table-edit-td no-empty"><font
								size="2" color="red">*</font>单位：</td>
							<td align="left" class="l-table-edit-td"><input
								name="che_hos_id" type="text" id="che_hos_id"
								style="width: 200px" /></td>
						</tr>
						<tr>
							<td align="right" class="l-table-edit-td no-empty"><font
								size="2" color="red">*</font>账套：</td>
							<td align="left" class="l-table-edit-td"><input
								name="che_copy_code" type="text" id="che_copy_code" ltype="text" />
							</td>
						</tr>
						<tr>
							<td align="right" class="l-table-edit-td"><font size="2"
								color="red">*</font>币种：</td>
							<td align="left" class="l-table-edit-td"><input
								name="cur_code" type="text" id="cur_code" ltype="text" /></td>
						</tr>
						<tr>
							<td align="right" class="l-table-edit-td">科目级次：</td>
							<td align="left" class="l-table-edit-td">
								<table>
									<tr>
										<td><input name="subj_level_begin" type="text"
											id="subj_level_begin" /></td>
										<td align="right" class="l-table-edit-td"
											style="font-size: 12px">至：</td>
										<td><input name="subj_level_end" type="text"
											id="subj_level_end" /></td>
										<td align="left" class="l-table-edit-td"
											style="font-size: 12px"><input
											style="vertical-align: middle" name="is_last" type="checkbox"
											id="is_last" ltype="text" value="0" />追加末级</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td align="right" class="l-table-edit-td"></td>
							<td align="left" class="l-table-edit-td"><input
								style="vertical-align: middle" name="checkbox" type="checkbox"
								id="is_nacc" ltype="text" value="0" checked="checked" />包含未记账
								&nbsp;&nbsp;&nbsp;&nbsp; <input style="vertical-align: middle"
								name="checkbox" type="checkbox" id="is_bqwfs" ltype="text"
								value="1" />显示本期无发生</td>
						</tr>
					</table>
				</div>

				<div id="subjectCon" style="display: none">
					<div position="center" title="科目列表" style="overflow: auto;">
						<ul class="ztree" id="treeList"></ul>
					</div>
					<div position="bottom"> 
						<input type="checkbox" id="is_fw" name="is_fw" onchange="onBetween();" />按范围查询
						
						<table id="munSearch" style="display: none" cellpadding="0" cellspacing="0" class="l-table-edit">
							<tr>
								<td align="right" class="l-table-edit-td">
									科目范围：
								</td>
								<td align="left" class="l-table-edit-td">
									<input name="subj_code_begin" type="hidden" id="subj_code_begin" ltype="text" />
								</td>
								<td align="right">&nbsp;至：</td>
								<td align="left" class="l-table-edit-td">
									<input name="subj_code_end" type="hidden" id="subj_code_end" ltype="text" />
								</td>
							</tr>
						</table>
					</div>
				</div>
				<div id="checkCon" style="display: none">
					<fieldset id="attend1" class="check">
						<legend>辅助核算1</legend>
						<div>
							<table>
								<tr>
									<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
										<font size="2" color="red">*</font>核算类：
									</td>
									<td align="left" class="l-table-edit-td">
										<input type="hidden" id="check_item_type1" name="check_item_type1" />
									</td>
								</tr>
								<tr>
									<td align="right" class="l-table-edit-td">核算项：</td>
									<td align="left" class="l-table-edit-td">
										<table>
											<tr>
												<td>
													<input type="hidden" id="check_item_code1_b" name="check_item_code1_b" />
												</td>
												<td align="left" class="l-table-edit-td">&nbsp;至：</td>
												<td>
													<input type="hidden" id="check_item_code1_e" name="check_item_code1_e" />
												</td>
												<!--  td align="right" class="l-table-edit-td"><input class="l-button l-button-test"  id="add1" type="button" value="增加" onclick="addLine1()"/></td>-->
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</div>
					</fieldset>
					<fieldset id="attend2" class="check">
						<legend>辅助核算2</legend>
						<div>
							<table>
								<tr>
									<td align="right" class="l-table-edit-td"
										style="padding-left: 20px;"><font size="2" color="red">*</font>核算类：</td>
									<td align="left" class="l-table-edit-td"><input
										type="hidden" id="check_item_type2" name="check_item_type2" />
		
									</td>
								</tr>
								<tr>
									<td align="right" class="l-table-edit-td">核算项：</td>
									<td align="left" class="l-table-edit-td" colspan="3">
										<table>
											<tr>
												<td>
													<input type="hidden" id="check_item_code2_b" name="check_item_code2_b" />
												</td>
												<td align="left" class="l-table-edit-td">&nbsp;至：</td>
												<td>
													<input type="hidden" id="check_item_code2_e" name="check_item_code2_e" />
												</td>
												<!--  <td align="right" class="l-table-edit-td"><input class="l-button l-button-test"  id="add2" type="button" value="增加" onclick="addLine2()"/></td>
												<td align="right" class="l-table-edit-td"><input class="l-button l-button-test"  id="delete1" type="button" value="移除" onclick="deleteLine1()"/></td>-->
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</div>
					</fieldset>
					<fieldset id="attend3" class="check">
						<legend>辅助核算3</legend>
						<div>
							<table>
								<tr>
									<td align="right" class="l-table-edit-td"
										style="padding-left: 20px;"><font size="2" color="red">*</font>核算类：</td>
									<td align="left" class="l-table-edit-td"><input
										type="hidden" id="check_item_type3" name="check_item_type3" />
		
									</td>
								</tr>
								<tr>
									<td align="right" class="l-table-edit-td">核算项：</td>
									<td align="left" class="l-table-edit-td" colspan="3">
										<table>
											<tr>
												<td>
													<input type="hidden" id="check_item_code3_b" name="check_item_code3_b" />
												</td>
												<td align="left" class="l-table-edit-td">&nbsp;至：</td>
												<td>
													<input type="hidden" id="check_item_code3_e" name="check_item_code3_e" />
												</td>
												<!--  <td align="right" class="l-table-edit-td"><input class="l-button l-button-test"  id="add3" type="button" value="增加" onclick="addLine3()"/></td>
												<td align="right" class="l-table-edit-td"><input class="l-button l-button-test"  id="delete2" type="button" value="移除" onclick="deleteLine2()"/></td>-->
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</div>
					</fieldset>
					<fieldset id="attend4" class="check">
						<legend>辅助核算4</legend>
						<div>
							<table>
								<tr>
									<td align="right" class="l-table-edit-td"
										style="padding-left: 20px;"><font size="2" color="red">*</font>核算类：</td>
									<td align="left" class="l-table-edit-td"><input
										type="hidden" id="check_item_type4" name="check_item_type4" />
		
									</td>
								</tr>
								<tr>
									<td align="right" class="l-table-edit-td">核算项：</td>
									<td align="left" class="l-table-edit-td" colspan="3">
										<table>
											<tr>
												<td>
													<input type="hidden" id="check_item_code4_b" name="check_item_code4_b" />
												</td>
												<td align="left" class="l-table-edit-td">&nbsp;至：</td>
												<td>
													<input type="hidden" id="check_item_code4_e" name="check_item_code4_e" />
												</td>
												<!--  <td align="right" class="l-table-edit-td"><input class="l-button l-button-test"  id="delete3" type="button" value="移除" onclick="deleteLine3()"/></td>-->
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</div>
					</fieldset>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
