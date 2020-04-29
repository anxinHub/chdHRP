<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>hrTableTypeMain</title>
<jsp:include page="${path}/resource.jsp">
	<jsp:param value="select,dialog,grid" name="plugins" />
</jsp:include>
<script>
	var type_tab_code, grid,file;
	$(function() {
		loadDict();
		loadGrid();
		$('#maingrid').on('click', '.td-a', function() { // 给a标签设置事件代理
			var index = $(this).attr('data-item') * 1;
			var data = grid.getRowData(index);
			var value = $(this).text();
			update(data, index, value);
		})
	})

	function loadDict() {
		/* type_tab_code = $("#type_tab_code").etSelect({
		    url: '',
		    defaultValue: "none"
		}); */
	}

	function loadGrid() {
		var gridObj = {
			editable : true,
			checkbox : true,
			height : '100%',
			addRowByKey : true, //  快捷键控制添加行
			resizable : true
		};
		gridObj.columns = [
				{
					display : "类别代码",
					width : 120,
					name : "type_tab_code",
					editable : false,
					render : function(ui) { // 修改页打开
						return '<a data-item=' + ui.rowIndx + ' class="td-a">'+ ui.cellData + '</a>'
					}
				}, {
					display : '类别名称',
					name : 'type_tab_name',
					width : 120
				}, {
					display : '排序',
					name : 'table_sort',
					width : 120
				}, {
					display : "备注",
					width : 480,
					name : "table_note",
					editor : {
						type : 'textbox'
					}
				} ];
		gridObj.dataModel = { // 数据加载的有关属性
			url : 'queryHrTableType.do',
			recIndx : 'class_code'
		};
		gridObj.toolbar = {
			items : [ {
				type : "button",
				label : '查询',
				icon : 'search',
				id : 'search',
				listeners : [ {
					click : search
				} ]
			}, {
				type : "button",
				label : '添加',
				icon : 'plus',
				id : 'add',
				listeners : [ {
					click : add
				} ]
			}, {
                type: "button",
                label: '删除',
                icon: 'delete',
                listeners: [{
                    click: deleteData
                }]
			}/* , {
                type: "button",
                label: '导入模板',
                icon: 'download',
                listeners: [{
                    click: importTemplate
                }] 
            }, {
                type: "button",
                label: '导入',
                icon: 'import',
                listeners: [{
                    click: importExcel
                }]
            }, {
                type: "button",
                label: '导出',
                icon: 'export',
                listeners: [{
                    click: exportExcel
                }]
            } */ 
            
            ]
		};
		grid = $("#maingrid").etGrid(gridObj);
	}

	function add() {
		$.etDialog.open({
			url : 'hrTableTypeAddPage.do?isCheck=false',
			height : 300,
			width : 700,
			title : '数据表分类添加页',
			btn : [ '确定', '取消' ],
			btn1 : function(index, el) {
				var iframeWindow = window[el.find('iframe').get(0).name];
				iframeWindow.saveData();
			}
		});
	}

	function update(data, index, value) {
		var parm = 'type_tab_code='+value;
		$.etDialog.open({
			url : 'hrTableTypeUpdatePage.do?isCheck=false&' + parm,
			height : 320,
			width : 650,
			title : '数据表分类修改页',
			btn : [ "确定", "取消" ],
			btn1 : function(index, el) {
				var frameWindow = window[el.find('iframe')[0].name];
				frameWindow.saveData();
			},
			btn2 : function(index) {
				$.etDialog.close(index); // 关闭弹窗
				return false;
			}
		})
	}
	
	function deleteData() {
		var data = grid.selectGet();
        if (data.length == 0) {
            $.etDialog.error('请选择行');
        } else {
        	var ParamVo = [];
            $(data).each(function () {
                var rowdata = this.rowData;
                ParamVo.push(rowdata);
            });
            $.etDialog.confirm('确定删除?', function () {
                ajaxPostData({
                    url: "deleteHrTableType.do",
                    data: {paramVo:JSON.stringify(ParamVo)},
                    success: function (res) {
                        if (res.state == "true") {
                            search();
                        }
                    }
                })
            });
        }
    }

	function search() {
		var param = [{name:'type_tab_code',value:$('#type_tab_code').val()}];
		grid.loadData(param);
	}
	
	/* function importTemplate(){
		location.href = "importTemplate.do?isCheck=false";	
	} */
	
	function importExcel(){
		//$("form[name=fileForm]").submit();
		var para = {
			"column" : [ 
				{
        			"name" : "type_tab_code","display" : "类别代码","width" : "200","require" : true
        		},{
        			"name" : "type_tab_name","display" : "类别名称","width" : "200","require" : false
        		},{
        			"name" : "table_sort","display" : "排序","width" : "200","require" : false
        		},{
        			"name" : "table_note","display" : "备注","width" : "100","require" : false
			} ]

		};
		importSpreadView("/hrp/hr/sc/hrTableType/importExcel.do?isCheck=false", para);
	}

	function exportExcel() {
		exportGrid(grid);
	}
</script>
</head>

<body>
	<div class="main">
		<table class="table-layout">
			<tr>
				<td class="label">类别编码：</td>
				<td class="ipt">
					<input type="text" name="type_tab_code"	id="type_tab_code" style="width: 180px;" />
				</td>
			</tr>
		</table>
	</div>
	<div id="maingrid"></div>
</body>

</html>