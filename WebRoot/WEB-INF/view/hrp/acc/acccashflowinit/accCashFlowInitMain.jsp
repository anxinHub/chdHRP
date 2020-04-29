<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
    <script src="<%=path%>/lib/map.js"></script>
    <script type="text/javascript">
    
	var grid;
	var gridManager = null;
	var userUpdateStr;
	
	
	//页面初始化
	$(function() {
		loadDict();//加载查询条件
		loadHead(null); //加载grid
	});
	
	
	//查询
	function query() {
		if(liger.get('year_month').getValue() == ""){
			$.ligerDialog.warn('会计期间不能为空！');
			return;
		}
		
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({name : 'acc_year',value : liger.get('year_month').getValue()});
		grid.options.parms.push({name : 'subj_code',value : liger.get("subj_code").getValue()});
		grid.options.parms.push({name : 'cash_item_id',value : liger.get("cash_item_id").getValue()});
		//加载查询条件
		grid.loadData(grid.where);
	}
	
	//加载grid
	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [
				{display : '年度',name : 'acc_year',width:120,align : 'left'},
				
			    {display : '科目名称',name : 'subj_code',align : 'left',textField: 'subj_name',editor : {
				    	type : 'select',valueField: 'subj_code',textField: 'subj_name',
						url : 'queryAccSubj.do?isCheck=false',
						keySupport:true,autocomplete: true,delayLoad: false,
					    triggerToLoad : false, //是否在点击下拉按钮时加载
					    async:false
					}
			    },
			    
			    {display : '现金流量项目',name : 'cash_item_id',	align : 'left',textField: 'cash_item_name',editor : {
						type : 'select',valueField: 'id',textField: 'text',
						url : '../queryCashItemSelect.do?isCheck=false',keySupport:true,autocomplete: true,
				      	delayLoad: false,triggerToLoad : false, //是否在点击下拉按钮时加载
				      	async:false
					}
				},
				
				{display : '金额',name : 'cash_money',align : 'right',editor : {type : 'float'},formatter:'###,##0.00',
					render : function(rowdata, rowindex, value) {
	  					return formatNumber(rowdata.cash_money, 2, 1);
	  				}
				},
				
				{display : '备注',name : 'summary',align : 'right',width:180,editor : {type : 'text'}}
			],
			dataAction : 'server',dataType : 'server',usePager : true,enabledEdit : true,isAddRow:false,
			url : 'queryAccCashFlowInit.do',width : '100%',height : '100%',
			checkbox : true,rownumbers : true, delayLoad:true,frozen : false ,
			selectRowButtonOnly : false ,heightDiff:0,
			onBeforeEdit : prePara ,//编辑前选中该行数据
			onToFirst : updateDateExist,//翻页之前判断当前页是否有变更数据 （若有变更数据，提示其保存）
			onToPrev  : updateDateExist,//翻页之前判断当前页是否有变更数据 （若有变更数据，提示其保存）
			onToNext  : updateDateExist,//翻页之前判断当前页是否有变更数据 （若有变更数据，提示其保存）
			onToLast  : updateDateExist,//翻页之前判断当前页是否有变更数据（若有变更数据，提示其保存）
			//onReload : reloadOrNot ,//刷新页面之前判断当前页是否有变更数据，并提示用户相应信息
			toolbar: { 
				items: [
					{ text: '查询', id:'search', click: query, icon:'search' },
					{ line:true },
    				{ text: '添加', id:'add', click: add_open, icon:'add' },
    	            { line:true },
    	            { text: '保存', id:'save', click: updateFlowInit, icon:'save' },
    	            { line:true },
    	            { text: '删除', id:'delete', click: remove,icon:'delete' },
    	            { line:true },
    	            { text: '导入模板', id:'down', click: loadDown,icon:'down' },
    	            { line:true },
    	            { text: '导入', id:'edit', click: impFlowInit,icon:'edit'},
    	            { line:true },
    	            { text: '打印', id:'print', click: print,icon:'print'}
    	        ]
			}
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	
	//打印
	function print(){
		if(grid.getData().length==0){
    		$.ligerDialog.warn("请先查询数据！");
    		return;
    	}
		
	 	var heads={
	      	//"isAuto": true/false 默认true，页眉右上角默认显示页码
	    	"rows": [
				{"cell":0,"value":"会计期间："+$("#year_month").val(),"colSpan":"5"},
	      	]
		};
	   		
	 	
		var printPara={
			rowCount:1,
			title:'现金流量初始帐',
			columns: JSON.stringify(grid.getPrintColumns()),//表头
			class_name: "com.chd.hrp.acc.service.AccCashFlowInitService",
			method_name: "queryAccCashFlowInitPrint",
			bean_name: "accCashFlowInitService",
			heads: JSON.stringify(heads)//表头需要打印的查询条件,可以为空
		};
	
		//执行方法的查询条件
		$.each(grid.options.parms,function(i,obj){
			printPara[obj.name]=obj.value;
		});
		
		officeGridPrint(printPara); 
		
	}
	
	//编辑前选中该行数据
	function prePara(e){
		if(!gridManager.isSelected(e)){
			gridManager.select(e.record.__id);
   	 	}
	}
	
	
	//翻页之前判断当前页是否有变更数据（若有变更数据，提示其保存）
	function updateDateExist(){
		var data = grid.changedCells;
        if (!isObjEmpty(data)){
        	$.ligerDialog.warn('数据有变更【<span style="color:red">不支持跨页保存数据,请先保存变更数据(若不想保存变更数据,请刷新页面后再操作)</span>】');
        	return false;
		}
        
        function isObjEmpty(obj){      //新注册的方法判断对象是否为空对象
        	for(var i in obj ){
        		return false;
			}
        	return true;
        }
	}
	
	/* //刷新页面之前判断当前页是否有变更数据，并提示用户相应信息
	function reloadOrNot(){
		var data = grid.getUpdated();
        if (data.length != 0){
	        $.ligerDialog.confirm('数据有变更,如若刷新页面数据将恢复到变更之前的状态，您确定刷新吗?', function (yes){
	        	return false ;
	            if(yes){
	            	gridManager.reload();
	            }
	        })
        }
	} */
	
	
	
	//添加
	function add_open(){
		if($("#year_month").val()==""){
			$.ligerDialog.warn('会计期间不能为空！');
			return;
		}
		var curentDate = new Date();

		if(parseInt(curentDate.getFullYear()) < parseInt($("#year_month").val())){
			$.ligerDialog.warn('会计期间不能为大于当前会计年度！');
			return;
		}

		$.ligerDialog.open({
			url: 'accCashFlowInitAddPage.do?isCheck=false&acc_year='+$("#year_month").val(),
			height: 310,width: 500, title:'现金流量初始帐添加',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
			parentframename:window.name,
			buttons: [ 
				{ text: '确定', onclick: 
					function (item, dialog) { 
						dialog.frame.saveAccCashFlowInit(); 
					},cls:'l-dialog-btn-highlight' 
				}, 
				{ text: '取消', onclick: 
					function (item, dialog) { 
						dialog.close(); 
					} 
				} 
			] 
		});
	}
	
	
	//保存
	function updateFlowInit(){
		
		var data = grid.getSelectedRows();
		//var data = grid.getUpdated() ;
        if (data.length == 0){
        	$.ligerDialog.warn('请选择行.');
//         	$.ligerDialog.warn('数据没变更,不需要保存');
        	return ;
        }
		
        var ParamVo =[];
		$.each(data,function(index,content){
			ParamVo.push(//表的主键
				this.group_id   +"@"+
				this.hos_id   +"@"+
				this.copy_code+"@"+
				this.acc_year   +"@"+
				this.subj_code   +"@"+
				this.subj_name   +"@"+
				this.cash_item_id  +"@"+
				this.cash_item_name   +"@"+
				this.summary  +"@"+
				this.cash_money  +"@"+
				this.old_subj_code +"@"+
				this.old_cash_item_id
			);
		});
		
     	ajaxJsonObjectByUrl("updateAccCashFlowInit.do",{ParamVo : ParamVo.toString()},function (responseData){
      		if(responseData.state=="true"){
      			query();
      		}
      	});
	}
	
	
	//删除
	function remove(){
		var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.warn('请选择行');
        	return ; 
        }
        var ParamVo =[];
        $(data).each(function (){
        	//表的主键
			ParamVo.push(
				this.group_id   +"@"+
				this.hos_id   +"@"+
				this.copy_code+"@"+
				this.acc_year   +"@"+
				this.subj_code  +"@"+
				this.cash_item_id   +"@"+
				this.subj_name
			);
        });
		
        $.ligerDialog.confirm('确定删除?', function (yes){
           	if(yes){
	           	ajaxJsonObjectByUrl("deleteAccCashFlowInit.do",{ParamVo : ParamVo.toString()},function (responseData){
	           		if(responseData.state=="true"){
	           			query();
	           		}
	           	});
           	}
		});
	}
	
	
	//导入模板
	function loadDown(){
		location.href = "downTemplate.do?isCheck=false";
    	return;
	}
	
	
	//导入
	function impFlowInit(){
		$.ligerDialog.open({url: 'accCashFlowInitImportPage.do?isCheck=false', height: 500,width:900,
			title:'现金流量初始帐导入',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true });
	}
	
	
	//字典下拉框
	function loadDict() {
		
		autocomplete("#subj_code","../querySubj.do?isCheck=false","id","text",true,true);
		autocomplete("#cash_item_id","../queryCashItemSelect.do?isCheck=false","id","text",true,true,'',false,'',240);
		
		//默认选中当前会计年度
		autocomplete("#year_month","../queryAccYear.do?isCheck=false","id","text",true,true,'',false,"${acc_year}",80);
	}
	
	
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">会计期间：</td>
			<td align="left" class="l-table-edit-td"><input  name="year_month" type="text" id="year_month" ltype="text" style="width: 160px;" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科目：</td>
			<td align="left" class="l-table-edit-td"><input  name="subj_code" type="text" id="subj_code" ltype="text" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">现金流量项目：</td>
			<td align="left" class="l-table-edit-td"><input  name="cash_item_id" type="text" id="cash_item_id" ltype="text"/></td>
			<td align="left"></td>
		</tr>
	</table>
	<div id="maingrid"></div>

</body>
</html>
