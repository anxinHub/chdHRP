//全局变量
var dGrid;
var is_float = 0;

//加载浮动层
function loadLayer(){
	
	ktLayerObj = $("#floatDiv").ktLayer({
		// 参数配置
		direction: "up",
		BtnbgImg: {open:'/CHD-HRP/lib/hrp/acc/superReport/open.png',close:'/CHD-HRP/lib/hrp/acc/superReport/close.png'},
		speed: "100",
		bgColor: "#ffffff",//背景颜色
		closeHeight: 0,//关闭状态高度
		Descript: ["展开扣分项","收起扣分项"],//展开收起描述
		zIndex: 2,
		open: function(){
			if(is_float == 0){
				loadDGrid();
				query_deduct();
				is_float = 1;
			}
		}
	});
}

//加载表格
function loadDGrid(){
	dGrid = $("#detailgrid").ligerGrid({
		columns: [{
			display: '扣分项代码', name: 'deduct_code', align: 'left', width: 120, 
			render: function(rowdata, index, value){
				return "<a href=javascript:edit_deduct('"+index+"')>"+value+"</a>"
			}
		},{
			display: '扣分项名称', name: 'deduct_name', align: 'left', width: 140
		},{
			display: '扣分项描述', name: 'deduct_desc', align: 'left', width: 180
		},{
			display: '扣分上限值', name: 'high_point', align: 'left', width: 90
		},{
			display: '是否停用', name: 'is_stop_name', align: 'left', width: 70
		}],
		dataAction: 'server', dataType: 'server', usePager: false, width: '100%', height: '50%', 
		url:'queryMatEvaTargetDeductList.do?isCheck=false', delayLoad: true, 
		checkbox: true, rownumbers: false, selectRowButtonOnly: true, heightDiff: -30, 
		toolbar: { items: [{
			text: '新增', id: 'add_deduct', icon: 'add', click: add_deduct
		},{ line:true },{
			text: '删除', id:'remove_deduct', icon: 'delete', click: remove_deduct
		}] }
	});
}

//查询
function  query_deduct(){
	dGrid.options.parms = [];
	dGrid.options.newPage = 1;
	//根据表字段进行添加查询条件
	dGrid.options.parms.push({name: 'target_code', value: target_code});
	//加载查询条件
	dGrid.loadData(dGrid.where);
}

//添加
function add_deduct(){
	if(!target_code){
		$.ligerDialog.error('请选择指标！');
		return false;
	}
	parent.$.ligerDialog.open({ 
		title: '指标扣分项维护',
		url : 'hrp/mat/eva/target/matEvaTargetDeductPage.do?isCheck=false',
		height: 400,
		width: 400,
		data: {target_code: target_code, target_name: target_name, is_update: 0}, 
		modal: true, showToggle: false, showMax: false,
		isResize: true, showMin: true, showMax: false, 
		parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
	}); 
}

//修改
function edit_deduct(rowindex){
	var data = dGrid.getRow(rowindex);
	parent.$.ligerDialog.open({ 
		title: '指标扣分项维护',
		url : 'hrp/mat/eva/target/matEvaTargetDeductPage.do?isCheck=false',
		height: 400,
		width: 400,
		data: {matEvaTargetDeduct: data, rowindex: rowindex, is_update: 1, target_code: target_code, target_name: target_name}, 
		modal: true, showToggle: false, showMax: false,
		isResize: true, showMin: true, showMax: false, 
		parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
	}); 
}

//删除
function remove_deduct(){
	if(!target_code){
		$.ligerDialog.error('请选择指标！');
		return false;
	}
	
	var data = dGrid.getCheckedRows();
	if (data.length == 0){
		$.ligerDialog.error('请选择指标扣分项');
		return false;
	}
	
	var codes = "";
	$.each(data, function (){
		codes += this.deduct_code+",";
	})
	
	$.ligerDialog.confirm('确定删除?', function (yes){
		if(yes){
			ajaxJsonObjectByUrl("deleteMatEvaTargetDeduct.do?isCheck=false",{target_code: target_code, codes: codes.substr(0, codes.length - 1)},function (responseData){
				if(responseData.state=="true"){
			    	dGrid.deleteSelectedRow();
				}
			});
		}
	}); 
}

