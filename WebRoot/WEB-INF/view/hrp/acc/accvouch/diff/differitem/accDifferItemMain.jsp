<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<script src="<%=path%>/lib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script>
<script src="<%=path%>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>
<script src="<%=path%>/lib/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<link rel="stylesheet" href="<%=path%>/lib/Z-tree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="<%=path%>/lib/Z-tree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=path%>/lib/Z-tree/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="<%=path%>/lib/Z-tree/js/jquery.ztree.exedit-3.5.js"></script>
<script type="text/javascript">
	var grid,tree;
	var gridManager = null;

	$(function() {
		loadTree();
		loadHead(null); //加载数据
	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({ name : 'diff_type_code', value : tree.getSelectedNodes()[0].id});
		//加载查询条件
		grid.loadData(grid.where);
	}

	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [
				{display : '项目编码',name : 'diff_item_code',align : 'left',width : 160,
						render : function(rowdata, rowindex, value) {
								return "<a href=javascript:openUpdate('"
											+ rowdata.diff_item_code + "')>"
											+ rowdata.diff_item_code + "</a>";
								}
					}, 
					{display : '项目名称',name : 'diff_item_name',align : 'left',width:430}, 
					{display : '差异方向',name : 'diff_dire',align : 'left',width : 80,
						render : function(rowdata, rowindex, value) {
							if (rowdata.diff_dire == 0) {
								return "借";
							} else {
								return "贷"
							}
						}
					},
					{display : '是否默认',name : 'is_default',align : 'left',width : 80,
						render : function(rowdata, rowindex, value) {
							if (rowdata.is_default == 1) {
								return "是";
							} else {
								return "否"
							}
						}
					},
					{display : '是否停用',name : 'is_stop',align : 'left',width : 100,
							render : function(rowdata, rowindex, value) {
								if (rowdata.is_stop == 0) {
									return "启用";
								} else {
									return "停用"
								}
							}
					} ],
					dataAction : 'server',dataType : 'server',usePager : false,
					url : 'queryAccDifferItem.do?isCheck=false',width : '100%',delayLoad : true,
					height : '100%',checkbox : true,rownumbers : true,selectRowButtonOnly : true,heightDiff: 28,
					toolbar : {
						items : [ 
							{text : '添加',id : 'add',click : itemclick,icon : 'add'}, 
							{line : true}, 
							{text : '删除',id : 'delete',click : itemclick,icon : 'delete'} 
						]
					},
					onDblClickRow : function(rowdata, rowindex, value) {
						openUpdate(rowdata.diff_item_code);
					}
				});

		gridManager = $("#maingrid").ligerGetGridManager();
		
		
		$("#toptoolbar").ligerToolBar({ items: [
            {text: '添加', id:'add', icon:'add', click: itemclick1},
            { line:true },
            { text: '删除',id:'delete',icon:'delete', click: itemclick1 },
            { line:true },
            { text: '修改',id:'update',icon:'edit', click: itemclick1 },
            { line:true },
            { text: '包含停用<input type="checkbox" id="is_have" />', id:'is_have',icon:'' }
        ]
        });
		
		$("#is_have").change(function() {
			loadTree();
		});
	}

	function itemclick(item) {
		if (item.id) {
			switch (item.id) {
			case "add":
				var node = tree.getSelectedNodes();
				if (node.length == 0) {
					$.ligerDialog.error('请先选择差异标注类别');
					return;
				}
				$.ligerDialog.open({
					url : 'accDifferItemAddPage.do?isCheck=false&type_code=' + node[0].id,
					height : 350,
					width : 500,
					title : '添加',
					modal : true,
					showToggle : false,
					showMax : false,
					showMin : true,
					isResize : true,
					buttons : [ {
						text : '确定',
						onclick : function(item, dialog) {
							dialog.frame.saveAccDifferItem();
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
			case "delete":
				var data = gridManager.getCheckedRows();
				if (data.length == 0) {
					$.ligerDialog.error('请选择行');
				} else {
					var ParamVo = [];
					$(data).each(function() {
						ParamVo.push(this.diff_item_code)
					});
					$.ligerDialog.confirm('确定删除?', function(yes) {
						if (yes) {
							ajaxJsonObjectByUrl("deleteAccDifferItem.do?isCheck=false", {
								mapVo : ParamVo
							}, function(responseData) {
								if (responseData.state == "true") {
										query();
								}
							});
						}
					});
				}
				return;
			}
		}
	}
	
	function openUpdate(diff_item_code) {
		$.ligerDialog.open({
			url : 'accDifferItemEditPage.do?isCheck=false&diff_item_code=' + diff_item_code,
			height : 350,
			width : 500,
			title : '修改',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : false,
			isResize : true,
			buttons : [ 
				{text : '确定',onclick : function(item, dialog) {
					dialog.frame.saveAccDifferItem();
				},cls : 'l-dialog-btn-highlight'}, 
				{text : '取消',onclick : function(item, dialog) {dialog.close();}} 
			]
		});

	}

	var is_stop=0;
	var setting = {      
	   		data: {simpleData: {enable: true}},
	   		treeNode:{open:true},
	   		callback:{onClick:groupDetail}
	}; 
	function loadTree(){
		 if($("#is_have").is(':checked')){
			 is_stop='';
        }else{
        	is_stop=0;
        }
       	$.post('queryAccDifferType.do?isCheck=false&is_stop='+is_stop ,null,function (responseData){
       	       tree=$.fn.zTree.init($("#tree"), setting, responseData.Rows);
       	       
       	},"json");
     }
	
	function groupDetail(){
		query();
	}
		
		
		function itemclick1(item){
			if (item.id) {
				switch (item.id) {
				case "add":
					$.ligerDialog.open({
						url : 'accDifferTypeAddPage.do?isCheck=false',
						height : 350,
						width : 500,
						title : '添加',
						modal : true,
						showToggle : false,
						showMax : false,
						showMin : true,
						isResize : true,
						buttons : [ {
							text : '确定',
							onclick : function(item, dialog) {
								dialog.frame.saveAccDifferType();
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
				case "update":
					var node = tree.getSelectedNodes();
					if (node.length == 0) {
						$.ligerDialog.error('请先选择差异标注类别');
						return;
					}
					$.ligerDialog.open({
						url : 'accDifferTypeEditPage.do?isCheck=false&diff_type_code='+node[0].id,
						height : 350,
						width : 500,
						title : '添加',
						modal : true,
						showToggle : false,
						showMax : false,
						showMin : true,
						isResize : true,
						buttons : [ {
							text : '确定',
							onclick : function(item, dialog) {
								dialog.frame.saveAccDifferType();
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
				case "delete":
					var node = tree.getSelectedNodes();
					if (node.length == 0) {
						$.ligerDialog.error('请先选择差异标注类别');
						return;
					}
					var data = gridManager.getData();
					if(data.length != 0){
						$.ligerDialog.error('该类别包含关联数据，请清空后重试！');
						return;
					}
					$.ligerDialog.confirm('确定删除?', function(yes) {
						if (yes) {
							ajaxJsonObjectByUrl("deleteAccDifferType.do?isCheck=false", {
								diff_type_code : node[0].id
							}, function(responseData) {
								if (responseData.state == "true") {
										loadTree();
								}
							});
						}
					});
				return;
				}
			}
		}
</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<div class="l-layout" id="layout1" style="height: 100%;" ligeruiid="layout1">

		<div class="l-layout-left" style="left: 0px; top: 0px; width: 260px; height: 100%;">
			<div class="l-layout-header">差异标注类别</div>
			<div class="l-layout-content" position="left">
				<div class="ztree" style="float: left">
					<ul id="tree"></ul>
				</div>
			</div>
		</div>
		<div class="l-layout-center" style="left: 260px; top: 0px; width: 920px; height: 100%;">
			<div class="l-layout-header">差异标注项目</div>
			<div title="" class="l-layout-content" style="height: 100%;" position="center">
				<div id="maingrid"></div>
			</div>
		</div>
	</div>

</body>
</html>
