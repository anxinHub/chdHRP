//全局变量
var mGrid;

//加载表格
function loadMGrid(){
	mGrid = $("#maingrid").ligerGrid({
		columns: [{
			display: '指标编号', name: 'target_code', align: 'left', width: 120, 
			render: function(rowdata, index, value){
				return "<a href=javascript:edit_target('"+index+"')>"+value+"</a>"
			}
		},{
			display: '指标名称', name: 'target_name', align: 'left', width: 140
		},{
			display: '指标分类', name: 'target_type_name', align: 'left', width: 140
		},{
			display: '评价方式', name: 'eva_method_name', align: 'left', width: 70
		},{
			display: '指标类型', name: 'target_kind_name', align: 'left', width: 70
		},{
			display: '指标属性', name: 'target_attr_name', align: 'left', width: 70
		},{
			display: '是否停用', name: 'is_stop_name', align: 'left', width: 70
		}],
		dataAction: 'server', dataType: 'server', usePager: false, width: '100%', height: '100%', 
		url:'queryMatEvaTargetList.do?isCheck=false', delayLoad: true, 
		checkbox: true, rownumbers: false, selectRowButtonOnly: true, heightDiff: 30, 
		onClickRow: clickMGridRow, 
		toolbar: { items: [{
			text: '查询', id: 'query_target', icon: 'search', click: query_target
		},{ line:true },{
			text: '新增', id: 'add_target', icon: 'add', click: add_target
		},{ line:true },{
			text: '删除', id:'remove_target', icon: 'delete', click: remove_target
		},{ line:true },{ 
			text: '导入', id: 'imp_target', icon: 'down', click: imp_target 
		},{ line:true },{ 
			text: '批量设置标度', id: 'setTargetScale', icon: 'edit', click: setTargetScale 
		}] }
	});
}

//表格单击事件
var target_code = "", target_name = "";
function clickMGridRow(rowdata, rowindex, value){
	target_code = rowdata.target_code;
	target_name = rowdata.target_name;
	if(is_float == 1){
		query_deduct();
	}
}

//查询
function  query_target(){
	var node = tree.getSelected();
	if(!node){
		$.ligerDialog.error('请选择指标类别');
		return false;
	}
	mGrid.options.parms=[];
	mGrid.options.newPage=1;
	//根据表字段进行添加查询条件
	mGrid.options.parms.push({name: 'target_type_code', value: node.data.target_type_code});
	mGrid.options.parms.push({name: 'target_name', value: $("#target_name").val()});
	mGrid.options.parms.push({name: 'is_stop', value: liger.get("is_stop").getValue()});
	//加载查询条件
	mGrid.loadData(mGrid.where);
	//刷新扣分项
	if(is_float == 1){
		target_code = "";
		target_name = "";
		query_deduct();
	}
}

//添加
function add_target(){
	var node = tree.getSelected();
	if(!node || node.data.is_last != "1"){
		$.ligerDialog.error('请选择末级指标类别');
		return false;
	}
	parent.$.ligerDialog.open({ 
		title: '指标维护',
		url : 'hrp/mat/eva/target/matEvaTargetPage.do?isCheck=false',
		height: $(window).height(),
		width: 900,
		data: {target_type_code: node.data.target_type_code, target_type_name: node.data.target_type_name, is_update: 0}, 
		modal: true, showToggle: false, showMax: false,
		isResize: true, showMin: true, showMax: false, 
		parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
	}); 
}

//修改
function edit_target(rowindex){
	var data = mGrid.getRow(rowindex);
	parent.$.ligerDialog.open({ 
		title: '指标维护',
		url : 'hrp/mat/eva/target/matEvaTargetPage.do?isCheck=false',
		height: $(window).height(),
		width: 900,
		data: {matEvaTarget: data, rowindex: rowindex, is_update: 1}, 
		modal: true, showToggle: false, showMax: false,
		isResize: true, showMin: true, showMax: false, 
		parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
	}); 
}

//删除
function remove_target(){

	var data = mGrid.getCheckedRows();
	if (data.length == 0){
		$.ligerDialog.error('请选择指标');
		return false;
	}
	
	var codes = "";
	$.each(data, function (){
		codes += this.target_code+",";
	})
	
	$.ligerDialog.confirm('确定删除?', function (yes){
		if(yes){
			ajaxJsonObjectByUrl("deleteMatEvaTarget.do?isCheck=false",{codes: codes.substr(0, codes.length - 1)},function (responseData){
				if(responseData.state=="true"){
			    	mGrid.deleteSelectedRow();
				}
			});
		}
	}); 
}

//导入
function imp_target(){
	var para = {
		url : 'hrp/mat/eva/target/matEvaTargetImportPage.do?isCheck=false',
		title : '评价指标导入',
		width : 0,
		height : 0,
		isShowMin : false,
		isModal : true,
		data : {
			grid : mGrid
		}
	};
	parent.openDialog(para);
}

//批量设置标度
function setTargetScale(){
	var data = mGrid.getCheckedRows();
	if (data.length == 0){
		$.ligerDialog.error('请选择指标');
		return false;
	}
	
	var codes = "";
	$.each(data, function (){
		codes += this.target_code+",";
	})
	parent.$.ligerDialog.open({ 
		title: '指标维护',
		url : 'hrp/mat/eva/target/matEvaTargetScaleBatchPage.do?isCheck=false',
		height: $(window).height() - 50,
		width: $(window).width() - 100,
		data: {codes: codes.substr(0, codes.length - 1)}, 
		modal: true, showToggle: false, showMax: false,
		isResize: true, showMin: true, showMax: false, 
		parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
	}); 
}