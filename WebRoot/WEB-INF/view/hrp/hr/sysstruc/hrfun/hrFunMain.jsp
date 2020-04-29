<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
        <jsp:param value="hr,tree,grid,select,dialog" name="plugins" />
    </jsp:include>
    <script>
			var tree, grid, tab_code;
			$(function () {
			    loadTree();
			    loadGrid();
			    
			    $('#maingrid').on('click', '.td-a', function () {
	                var index = $(this).attr('data-item') * 1;
	                var data = grid.getRowData(index);
	                var value = $(this).text();
	                update(data, index, value);
	            })
			});
			
			function loadTree() {
				tree = $("#mainTree").etTree({
	                async: {
	                    enable: true,
	                    url: '../hrcaltrans/queryHrFunTypeTree.do?isCheck=false'
	                },
	                callback: {
	                    onClick: function () {
	                    	search('tree');
	                    }
	                }
	            }) 
           }
			
			var search = function (queryFor) {
	            if (queryFor === 'tree') {
	                var selectedNode = tree.getSelectedNodes()[0];
	                var type_code = selectedNode ? selectedNode.id : '';
	                params = [{
	                        name: 'fun_code',
	                        value: type_code
	                    }
	                ]
	            } else {
	                params = [{
                        name: 'fun_code',
                        value: null
                    }
	                          ]
	            }
	            grid.loadData(params);
	        };
	        
			function loadGrid() {
                    var gridObj = { editable: false, checkbox: true, height: '100%',
                        inWindowHeight: true, freezeCols: 2, addRowByKey: true,  showBottom:false,//  快捷键控制添加行
                    };
                    gridObj.columns = [{
                            display: "函数编码", align: "left",width: 200,name: "fun_code",
                            render: function (ui) { // 修改页打开
                                return '<a data-item=' + ui.rowIndx + ' class="td-a">' + ui.cellData +
                                    '</a>'
                            }
                        },{
                            display: '函数名称',align: 'left',name: 'fun_name',width: 150
                        },{
                            display: "取值函数(中文)",align: "left",width: 400,name: "fun_method_chs",
                        }
                    ];
                    gridObj.dataModel = { // 数据加载的有关属性
                        url: 'hrFunSetquery.do',
                        recIndx: 'fun_code'
                    };
                    gridObj.toolbar = {
                        items: [{
                                type: "button",label: '查询',icon: 'search',id: 'search',listeners: [{ 
                                	click: search
                                }]
                            },{
                                type: "button",label: '添加',icon: 'add',id: 'add',listeners: [{
                                    click: addFun
                                }]
                            }, {
                                type: "button",label: '删除',icon: 'delete',id: 'delete',listeners: [{
                                    click: deleteFun
                                }]
                            }, {
                                type: "button",label: '重新加载存储过程', icon: 'initProc',id: 'initProc',listeners: [{
                                    click: initProc
                                }]
                            }
                        ]
                    };
                    grid = $("#maingrid").etGrid(gridObj);
            }
			
			//添加
			function addFun() {
                parent.$.etDialog.open({
                    url: 'hrp/hr/sysstruc/hrcaltrans/hrFunAddPage.do?isCheck=false',
                    height: $(parent.window).height(),
	                width: $(parent.window).width(),
                    title: '添加',
                    btn: ['确定', '取消'],
                    btn1: function (index, el) {
                        var iframeWindow = parent.window[el.find('iframe').get(0).name];
                        iframeWindow.save();
                    }
                });
            }
			//更新
			function update(data, index, value) {
				parent.$.etDialog.open({
	                url: 'hrp/hr/sysstruc/hrcaltrans/hrFunUpdatePage.do?isCheck=false&fun_code=' + value,
	                height: $(parent.window).height(),
	                width: $(parent.window).width(),
	                title: '修改',
	                btn: ["确定", "取消"],
	                btn1: function (index, el) {
	                    var frameWindow = parent.window[el.find('iframe') [0].name];
	                    frameWindow.save();
	                }
	            });
            }
			
			//删除
            function deleteFun() {
            	var data = grid.selectGet();
                if (data.length == 0) {
                    $.etDialog.error('请选择行');
                } else {
                    var param = [];
                    $(data).each(function () {
                        var rowdata = this.rowData;
                        param.push(rowdata);
                    });
                    $.etDialog.confirm('确定删除?', function () {
                        console.log(JSON.stringify(param))
                        ajaxPostData({
                            url: "hrFunSetdelete.do",
                            data: {
                            	param: JSON.stringify(param)
                            },
                            success: function (res) {
                                if (res.state == "true") {
                                    search();
                                }
                            }
                        })
                    });
                }
                
                 /* if (tree.getSelectedNodes().length) { 
                    $.etDialog.confirm('确定删除?', function () {
                        ajaxPostData({
                            url: "hrFunSetdelete.do",
                            data: {
                                tab_code: tree.getSelectedNodes()[0].id
                            },
                            success: function (res) {
                                if (res.state == "true") {
                                    loadTree();
                                    search();
                                }
                            }
                        })
                    });
                 } else {
                    $.etDialog.error('请选择树节点');
                }  */
            }
           
		    function initProc(){
		    	$.etDialog.confirm('确定要重新加载存储过程?', function () {
		    		ajaxPostData({
			            url: "inithrFunProc.do?isCheck=false",
			            success: function (res) {
			                if (res.state == "true") {
			                    search();
			                }
			            }
			        })
		    	})
		    	
		    }
	</script>
</head>

<body>
	<div class="container">
    	<div class="left border-right">
        	<div class="search-form">
            	<label>函数分类</label>
            </div>
            <div id="mainTree"></div>
            <div class="container-bar"></div>
        </div>
        <div class="center">
          	<div id="maingrid"></div>
        </div>
     </div>
</body>

</html>
