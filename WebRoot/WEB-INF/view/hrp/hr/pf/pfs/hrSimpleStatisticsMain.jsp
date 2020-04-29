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
<script src="<%=path%>/lib/et_components/et_plugins/etDialog.min.js"></script>
<script src="<%=path%>/lib/et_assets/common.js"></script>
<script src="<%=path%>/lib/et_assets/hr/common.js"></script>
<script src="<%=path%>/lib/hrp/hr/hr.js"></script>

<style type="text/css">
.l-layout-content{
	height: 100%;
}
/* 搜索框 */
.search-form {
	padding: 5px;
	box-sizing: border-box;
	background: #e0ecff;
	border-bottom: 1px solid #a3c0e8;
}
/* 文本input */
.text-input {
	box-sizing: border-box;
	width: 180px;
	height: 26px;
	padding: 1px 1px 1px 5px;
	border: 1px solid #aecaf0;
	border-radius: 1px;
	outline: none;
	width: 140px;
	-webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .1);
	box-shadow: inset 0 1px 1px rgba(0, 0, 0, .1);
}
/*.left-buttons ul{
	display: flex;
    flex-direction: row;
    justify-content: center;
} */

</style>
<script type="text/javascript">
	var grid, tree, design_code, toptoolbar, searchParam, design_query_col;
	var gridTables = [], gridTableNames = [];
	$(function() {
		
		//genGridHeader({group_id: 19, hos_id: 23, tables: ['HOS_EMP', 'HR_EMP_DEGREE'], design: '0101'});
  	
		//布局
		$("#layout").ligerLayout({leftWidth: 230, minLeftWidth: 230, allowLeftResize: false});
		
		toptoolbar = $("#toptoolbar").ligerToolBar(
			{ items: [
				{text: '查询', id:"save", click: function (item){search()}, icon:'search'}, { line:true },
				{text: '条件查询', click: function (item){searchCondition()}, icon:'search'}, { line:true },
				{text: '打印', click: function (item){print()}, icon:'print'}, { line:true },
				/* {text: '模板设置', click: function (item){printSet()}, icon:'settings'}, { line:true },
				{text: '模板打印', click: function (item){templatePrint()}, icon:'print'}, { line:true } */
			]}
		);
		
		loadTree();
		initGrid();
		
		$("#search_input").keyup(function(e) {
			var key = $("#search_input").val();
			tree.options.url = 'queryTableDesignTree.do?isCheck=false&rjt=tree&key='+key;
			tree.reload();
		});
		
	});
	
	//设置分组
	function setGroupColumn(param){
		if(param){
			$.each(param, function(i, v){
				if(v.name == 'groupColumn'){
					grid.set('groupColumnName', v.value.split(',')[0]);
		            grid.set('groupColumnDisplay', v.value.split(',')[1]);
		            grid.reRender();
		            loadGridHeader();
		            return false;
				}
			}) 
		}
	}
	
	function initGrid(){
		grid = $("#maingrid").ligerGrid({
			dataType: 'server', 
			dataAction: 'server',
			url: ".do",
            columns : [],
            width: '100%', height: '100%', checkbox: true, rownumbers: true,
            usePager: true, delayLoad: true, enabledEdit: false
        });
	}
	
	function loadGridHeader(){
		gridTables = [];
		gridTableNames = [];
		if(!design_query_col || !JSON.parse(design_query_col).tableData){
			return;
		}
		$.each(JSON.parse(design_query_col).tableData, function(i, v){
            gridTables.push(v.tab_code);
            gridTableNames.push(v.tab_name);
        });
        var columns = getGridColumns({group_id: ${group_id}, hos_id: ${hos_id}, gridTables: gridTables, design: design_code, ui: 'liger'});
        //console.log(columns);
        grid.set('columns', columns);
	}
	
	function loadGridData(param) {//远程加载主集表格 main_grid
		setGroupColumn(param);//设置分组
	
        grid.options.parms = param && Array.isArray(param) ? param : [];
        grid.options.newPage = 1;
        grid.options.parms.push({
            name : 'design_code',
            value : design_code
        });
        
        /* grid.options.parms.push({
            name : 'search_param',
            value : JSON.stringify(param)
        }); */
        
        grid.options.url = design_code + '?isCheck=false';
        grid.loadData(grid.where);
        
        //var data = $.extend({design_code: design_code}, param);
        
        searchParam=param;
        
    }
	
	function searchCondition() {
        if (tree.getSelected()) {
            $.etDialog.open({
                title: '查询',
                height: 500,
                width: 800,
                frameName: window.name,
                btn: ["查询", "取消"],
                btn1: function (index, el) {
                    var iframeWindow = window[el.find('iframe')[0].name];
                    iframeWindow.query(); //子页函数
                    $.etDialog.close(index);
                },
                btn2: function (index) {
                    $.etDialog.close(index); // 关闭弹窗
                    parent.$.etDialog.close(index);
                    return false;
                },
                url: "hrSimpleStatisticsSearchAddPage.do?isCheck=false"
            });
        } else {
            $.etDialog.error('请选择树节点');
        }
    }

	//查询
	function search() {
		loadGridData();
	}
	
	function loadTree() {
		
		tree = $("#mainTree").ligerTree(
				{url: 'queryTableDesignTree.do?isCheck=false&rjt=tree', 
            	 ajaxType: 'post' ,
            	 idFieldName:'id',
            	 textFieldName: 'text',
                 parentIDFieldName:'pid', 
                 nodeWidth:'100%',
            	 checkbox: false,
            	 onClick: function(node, target){
            		if(!node.data.pid || node.data.pid == '0'){
            			return;
            		}
            		design_code = node.data.id;
            		design_query_col = node.data.design_query_col;
            		
            		loadGridHeader();
            		loadGridData();
            	}
            });
	}
	
	//添加查询设计器
	function addTabDesign() {
		parent.$.etDialog.open({
            title: '简单统计',
            url: "hrp/hr/sc/hrtabledesign/hrSimpleStatisticsSetForm.do?isCheck=false",
            isMax: true,
            frameName: window.name
        });
	}

	//修改查询设计器
	function updateTabDesign() {
		//获取树当前选择项
		if (tree.getSelected()) {
			var param = 'design_code=' + tree.getSelected().data.id;
			//var param = 'design_code=queryS02';
			parent.$.etDialog.open({
	            title: '简单统计',
	            url: "hrp/hr/sc/hrtabledesign/hrSimpleStatisticsSetForm.do?isCheck=false&"+param,
	            isMax: true,
	            frameName: window.name
	        });
		} else {
			$.ligerDialog.error('请选择树节点');
		}
	}

	//删除查询设计器
	function deleteTabDesign() {
		//获取树当前选择项
		if (tree.getSelected() || tree.getChecked().length > 0) {
			$.ligerDialog.confirm('确定删除?', function(e) {
				if(e){
					var design_code = "";
					if(tree.getChecked() && tree.getChecked().length > 0){
						$.each(tree.getChecked(), function(i, n){
							design_code += n.data.id + ',';
						})
					}else{
						design_code = tree.getSelected().data.id;
					}
					ajaxPostData({
						url: "../../sc/hrtabledesign/deleteHrStatisticDesign.do?isCheck=false",
						data: {
							design_code: design_code
						},
						success: function(res) {
							if (res.state == "true") {
								tree.reload();
							}
						}
					})
				}
			}); 
		} else {
			$.ligerDialog.error('请选择树节点');
		}
	}
	
	function print() {
        if(grid.getData()==null){
            $.etDialog.error("请先查询数据！");
            return;
        }
        var params = {};
        if(searchParam && Array.isArray(searchParam)){
        	$.each(searchParam, function(i, v){
                params[v.name] = v.value;
            });
        }
        
        var heads={};
        var printPara= $.extend({
                title: tree.getSelected().name,//标题
                columns: JSON.stringify(grid.getPrintColumns()),//表头
                class_name: "com.chd.hrp.hr.service.QueryService",
                method_name: "queryBaseInfoPtint",
                bean_name: "queryService",
                heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
                foots: '',//表尾需要打印的查询条件,可以为空 
                design_code: design_code
            }, params);
            
            officeGridPrint(printPara);
    }
	
	//打印模板设置
	function printSet(){
		//use_id (0统一打印，1按用户打印，2按仓库打印，3按供应商打印)
		officeFormTemplate({template_code: "06001",use_id: 0});
	}
	
	//模板打印
    function templatePrint(){
    	var params = {
    			template_code: '06001', //必填
    			use_id: 0, //默认0，(0统一打印，1按用户打印，2按仓库打印，3按供应商打印)
    			design_code: design_code, //必填 查询设计器编码
    			id_column: 'EMP_ID', //默认'EMP_ID'
    	};
    	
    	//查询条件
        if(searchParam && Array.isArray(searchParam)){
            $.each(searchParam, function(i, v){
                params[v.name] = v.value;
            });
        }
        hrTemplatePrint(params);
    }

	//导入数据
	function importData() {
		$.ligerDialog.open({url: 'importPage.do?isCheck=false', height: 500,width: 800, title:'导入',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true });
	}
	
	//导出数据
	function exportData() {
		//console.log(tree.getChecked())
		if(tree.getChecked()){
			var ids = '';
			$.each(tree.getChecked(), function(index, item){
				if(item.data.pid){
					ids += item.data.id + ','
				}
			});
			location.href = "export.do?isCheck=false&ids="+ids;
		}
		
	}
</script>
</head>
<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div class="l-layout" id="layout" style="height: 100%;">
		<div position="left" title="设计器">
			<div id="form1" class="liger-form left-buttons">
				<div class="buttons">
					<input data-text="增加" data-click="addTabDesign" /> 
					<input data-text="修改" data-click="updateTabDesign" /> 
					<input data-text="删除" data-click="deleteTabDesign" />
					<!-- <input data-text="导入" data-click="importData" /> 
                    <input data-text="导出" data-click="exportData" /> -->
				</div>
			</div>
			<div class="search-form">
				<label>快速定位</label> 
				<input type="text" id="search_input" class="text-input"> 
			</div>
			<div style="width:100%;height:calc(100% - 120px);overflow:auto;">
				<ul id="mainTree"></ul>
			</div>
			<div class="container-bar"></div>
		</div>
		<div position="center" title="">
			<div id="toptoolbar"></div>
			<div id="maingrid"></div> 
		</div>
	</div>
</body>
</html>
