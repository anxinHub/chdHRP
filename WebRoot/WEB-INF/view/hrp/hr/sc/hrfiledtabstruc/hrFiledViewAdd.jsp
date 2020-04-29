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
	/* text-align: center; */
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

.middle input {
    display: block;width:30px; margin:2px;
}

.l-dialog-table .l-panel .l-toolbar{
	width: auto !important;
}

table td{
	width: 70px;
	height:20px;
	padding: 0px 0px 0px 15px;
} 

</style>
<script type="text/javascript">
	var tree, actionNodeID='${tab_code}',dataTableGrid, conditionGrid, groupGrid, sortGrid, toptoolbar, design_code;
	var filedData={
			Rows:[{"codeItem":"field_col_code","colCode":null,"colName":null},
			      {"codeItem":"field_col_name","colCode":null,"colName":null},
			      {"codeItem":"super_col_name","colCode":null,"colName":null},
			      {"codeItem":"is_last_text","colCode":null,"colName":null},
			      {"codeItem":"is_innr_text","colCode":null,"colName":null},
			      {"codeItem":"is_stop_text","colCode":null,"colName":null},
			      {"codeItem":"note"}],Total:7
			};
	
	$(function() {
  	
		//布局
		$("#layout1").ligerLayout({
			leftWidth : 220,
			minLeftWidth : 220,
			allowLeftResize : false
		});
		
		toptoolbar = $("#toptoolbar").ligerToolBar(
			{ items: [
				{text: '保存', id:"saveData", click: function (item){saveData()}, icon:'save'}, { line:true },
				{text: '添加行', id:"add", click: function (item){add()}, icon:'add'}, { line:true },
				{text: '删除', id:"del", click: function (item){del()}, icon:'delete'}, { line:true },
// 				{text: '生成', id:"genSql", click: function (item){genSql()}, icon:'add'}, { line:true },
				{text: '代码表：${field_tab_code}'},{text: '数据表：'+actionNodeID,id:'tabCode'},
			]}
		);
		
		loadFiledData();
		loadTree();
		loadGrid();

		$("#search_input").keyup(function(e) {
			var key = $("#search_input").val();
			tree.options.url = 'queryDBTableTree.do?isCheck=false&key='+key+'&design_code='+design_code;
			tree.reload();
		});
		
		
		
	});

	
	function loadFiledData(){
		ajaxPostData({
            url: 'queryFiledColsByCode.do?isCheck=false&field_tab_code=${field_tab_code}',
            delayCallback: true,
            success: function (data) {
            	if(data != null){
            		grid = $("#data_table_grid").ligerGetGridManager();
                	grid.loadData(data) ;
            	}
            	
            }
        });
	}
	
	function loadTree() {
		tree = $("#mainTree").ligerTree(
				{url: 'queryDBTableTree.do?isCheck=false&tab_code=${tab_code}', 
            	 ajaxType: 'post' ,
            	 idFieldName :'tab_code',
            	 textFieldName: 'tab_name',
                 parentIDFieldName :'pid', 
                 nodeWidth:'100%',
                 single : true,
                 checkbox : false,
            	 autoCheckboxEven: false,
            	 data:'',
            	 onClick: function(node){
            		if(node.data.pid){
            			actionNodeID = node.data.tab_code;
            			toptoolbar.options.items.find(function(i){return i.id=="tabCode"}).text =  '数据表：'+actionNodeID;
            			toptoolbar._render();
            			//             			$("#tabCode").text('数据表：'+node.data.tab_code); 
            			var gridManager = $("#data_table_grid").ligerGetGridManager();
                			if(${not empty tab_code}){
                				if(actionNodeID != "${tab_code}"){
                					grid.loadData(filedData) ;
//                 					gridManager.reload();
									$("#related_sql").val('');
									$("#query_sql").val('');
                    			}else{
//                     				loadFiledData();
                    				Location.reload();
                    			}
                			}else{
                				grid.loadData(filedData) ;
                			}
            		}
            	}
            });
	}
	function updateRow() {
        manager = $("#data_table_grid").ligerGetGridManager();
        var rowArr = [];
        for (var i in manager.rows) {   
        	rowArr[i] = manager.rows[i];
        }
        manager.deleteRange(rowArr);
        
    }
	function loadGrid() {
		dataTableGrid = $("#data_table_grid").ligerGrid({
			columns: [
				{display: "代码项", name: "codeItem", align: "center", width: 300, textField: "text",
					editor: { type: 'select', data: optionData.codeItem },
				 	render: function(rowdata, rowindex, value) {
	               		if(value == 'field_col_code'){
	               			return "代码项编码";
	               		}
	               		if(value == 'field_col_name'){
	               			return "代码项名称";
	               		}
	               		if(value == 'super_col_name'){
	               			return "上级代码";
	               		}
	               		if(value == 'is_last_text'){
	               			return "是否末级";
	               		}
	               		if(value == 'is_innr_text'){
	               			return "是否内置";
	               		}
	               		if(value == 'is_stop_text'){
	               			return "是否停用";
	               		}
	               		if(value == 'note'){
	               			return "备注";
	               		}
					}
				,
				},
	            {display: '数据项编码', name: 'colCode', align: 'center', width: 300, textField: "colCode", editor: { type: 'text'}, 
	            },
				{display: '数据项名称', name: 'colName', align: 'center', width: 300
				}
			],
			data: null, width: '100%', height: '300', rownumbers: true, delayLoad: false,rowDraggable:true,  
			enabledEdit: true, selectRowButtonOnly: true, isAddRow: false, usePager: false, isSingleCheck: true,
// 			url:'queryFiledColsByCode.do?isCheck=false&field_tab_code=${field_tab_code}',
			onBeforeEdit: function (e) {
				var tables = [actionNodeID];
				if(e.column.name == 'colCode'){
					//func参数是用来回显函数与参数信息
					var parm = {'tab_codes': tables};
			    	e.column.editor = {
			    			type: 'popup', parms: parm, valueField: 'col_code', textField: 'col_name', 
		            		grid: {
	                        	url: "../hrtabledesign/queryHrTableColByCodes.do?isCheck=false", parm: parm, columns: [
	                        		{ display: '数据列编码', name: 'col_code', width: 300}, 
	                        		{ display: '数据列名称', name: 'col_name', width: 300},
	                        	],
	                        	checkbox: false, usePager: false, enabledEdit: true 
	                    	},
	                    	onSelected: f_onSelected,
						}
				}
			},
		});
		
		function f_onSelected(e){
			if (!e.data || !e.data.length) return;
			var lastEditRow = dataTableGrid.lastEditRow;
	       
	        dataTableGrid.updateRow(lastEditRow, {
	       	 colCode: e.value,
	       	 colName: e.text
	        });
		}	
		
		 $("#related_grid").ligerPanel({
			                  title: '外部引用SQL',
			                  width: 510,
			                  height : 200
		});
		 $("#query_grid").ligerPanel({
             title: '查询SQL',
             width: 510,
             height : 200
         });
	}

	
	function add(){
		if(actionNodeID == "" || actionNodeID == null){
			$.ligerDialog.error('请选择树节点');
		}else{
			var gridManager = $("#data_table_grid").ligerGetGridManager();
			gridManager.addRow();
		}
	}
	function del(){
		var gridManager = $("#data_table_grid").ligerGetGridManager();
		gridManager.deleteSelectedRow2();
	}
	
	function genSql(){
		saveData();
	}
	//查询
	function search() {
		dataTableGrid.options.parms = [];
		dataTableGrid.options.newPage = 1;
		//根据表字段进行添加查询条件
		
		//加载查询条件
		dataTableGrid.loadData(dataTableGrid.where);
	}
	//保存代码表字段信息
	function saveData() {
		var dataTableGridManager = $("#data_table_grid").ligerGetGridManager();
		dataTableGridManager.endEdit();
		if (actionNodeID != null && actionNodeID != "" && typeof(actionNodeID) != "undefine") {
			var allData = dataTableGridManager.getData();
	        var parm = [];
			$.each(allData,function(index,item){
				parm.push({
					codeItem : item.codeItem,
					colCode  : item.colCode,
					colName  : item.colName,
				});
			});
			ajaxPostData({
				url: 'updateHrFiledTabStruc.do?isCheck=false',
				data: {
					'field_tab_code' : '${field_tab_code}',
					'table_code' : actionNodeID,
                	'allData' : JSON.stringify(parm),
                	'related_sql':$("#related_sql").val(),
                	'query_sql':$("#query_sql").val()
					
				},
				delayCallback: true,
				success: function(data) {
					location.reload();
				}
			})
		} else {
			$.ligerDialog.error('请选择树节点');
		}
	}
    
</script>
</head>
<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div class="l-layout" id="layout1" style="height: 100%;">
		<div position="left" title="数据表">
			<div class="search-form">
				<label>快速定位</label> 
				<input type="text" id="search_input" class="text-input"> 
			</div>
			<div style="width:100%;height:calc(100% - 60px);overflow:auto;">
				<ul id="mainTree"></ul>
			</div>
			<div class="container-bar"></div>
		</div>
		<div position="center" title="" style="overflow: auto">
			<div id="toptoolbar"></div>
			<div style="padding:4px;float:left;width:99%;">
		    	<div id="data_table_grid"></div>  
		  	</div>
		  	<div style="margin-left:20px">
           		<span><font color="blue">参数说明：[group_id]：当前集团ID 、 [hos_id]：当前医院ID 、 [copy_code]：当前账套 、 [user_id]：当前登录用户ID。</font></span><br/>
           </div>
			<table  cellspacing="5" cellpadding="2">
	            <tr><td align="left" class="l-table-edit-td">外部引用SQL：</td> <td align="left" class="l-table-edit-td">查询SQL：</td></tr>
	            <tr>
	                <td class="l-table-edit-td">
	                    <textarea name="related_sql" id="related_sql" style="width:455px;height:150px;border:1px solid #99bbe8;">${related_sql}</textarea>
	                </td>
	                <td class="l-table-edit-td">
	                    <textarea name="query_sql" id="query_sql" style="width:455px;height:150px;border:1px solid #99bbe8;" >${query_sql}</textarea>
	                </td>
	            </tr>       
            </table>
		</div>
		
	</div>
</body>
</html>
