//全局变量
var tree;
/* 左侧树形菜单 */
function loadTree(){
	tree = $("#tree").css({"height": $(window).height()-60}).ligerTree({
		url: "queryMatEvaTargetTypeTree.do?isCheck=false", 
		parentIcon: null,
		childIcon: null,
		checkbox : false,
		idFieldName : 'target_type_code',
		parentIDFieldName : 'super_code',
		textFieldName : 'target_type_text',
		onSelect: onSelect,
		isExpand: true,
		nodeWidth: 400,
		isExpand: 2
	});
	//$("#tree").css({"width":"100%"});
	//tree.collapseAll(); //全部收起
}

//选中节点事件
function onSelect(node){
	if(node.data){
		query_target();
	}
}

//添加类别
function add_type(){
	var node = tree.getSelected();
	if(!node){
		$.ligerDialog.warn("请选择指标分类");
		return false;
	}
	$.ligerDialog.open({
		title: '指标分类',
		url: 'matEvaTargetTypePage.do?isCheck=false', 
		data: {matEvaTargetType: node.data, is_update: 0}, 
		height: 300, width: 500, 
		modal: true, showToggle: false, 
		showMax: false, showMin: true, isResize: true
	});
}

//修改类别
function edit_type(){
	var node = tree.getSelected();
	if(!node){
		$.ligerDialog.warn("请选择指标分类");
		return false;
	}
	$.ligerDialog.open({
		title: '指标分类',
		height: 300, width: 500, 
		url: 'matEvaTargetTypePage.do?isCheck=false', 
		data: {matEvaTargetType: node.data, is_update: 1}, 
		modal: true, showToggle: false, 
		showMax: false, showMin: true, isResize: true
	});
}

//删除类别
function remove_type(){
	var node = tree.getSelected();
	if(!node){
		$.ligerDialog.warn("请选择指标分类");
		return false;
	}
	if(node.data.is_last == 0){
		$.ligerDialog.warn("请选择末级指标分类");
		return false;
	}
	var param = {
			target_type_code: node.data.target_type_code, 
			super_code: node.data.super_code
	}
	$.ligerDialog.confirm('确定删除?', function (yes){
		if(yes){
			ajaxJsonObjectByUrl("deleteMatEvaTargetType.do?isCheck=false", param, function (responseData){
				if(responseData.state=="true"){
					tree.reload();
				}
			});
		}
	})
}