<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%String path = request.getContextPath();%>
        <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
        <html>

        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>storeTabStrucMain</title>
            <jsp:include page="${path}/resource.jsp">
                <jsp:param value="hr,dialog,grid,select,tree,datepicker,pageOffice" name="plugins" />
            </jsp:include>
            <script>
                var grid, tree,store_type_code;
                $(function () {
                    loadTree();
                    loadGrid();

                    $('#maingrid').on('click', '.td-a', function () {
                        var index = $(this).attr('data-item') * 1;
                        var data = grid.getRowData(index);
                        var value = $(this).text();
                        update(data, index, value);
                    });
                    
                    
                    $("#search_input").keyup(function (e) {
                        var _this = this;
                        searchTree({
                            tree: tree,
                            value: this.value,
                            callback: function (node) {
                                $(_this).focus(); //回去焦点
                            }
                        });
                    });
                    
                })

                function loadGrid() {
                    var gridObj = {
                        editable: true,
                        checkbox: true,
                        height: '100%',
                        /* usePager: false, */
                        inWindowHeight: true,
                        addRowByKey: true, //  快捷键控制添加行
                        dataModel: {
                              sorting: "remote"//后台排序
                             
                        },
                    
                    };
                    gridObj.numberCell = {
                        title: '#'
                    };
                    gridObj.columns = [{
                        display: "数据表编码",
                        align: "left",
                        width: 200,
                        name: "tab_code",
                        //editable: true ,
                        editor: {
                            type: 'grid',
                            resizable: true,
                            columns: [{
                                display: '数据表编码',
                                align: 'left',
                                width: 200,
                                name: 'tab_code',
                                editable: true 
                            }, {
                                display: '数据表名称',
                                align: 'left',
                                width: 150,
                                name: 'tab_name'
                            },
                            {
                                display: '备注',
                                align: 'left',
                                width: 120,
                                name: 'note' 
                            }],
                            dataModel: {
                                url: '../sysstruc/queryHrHosTabStruc.do?isCheck=false',
                                recIndx: 'tab_code'
                            },
                            width: 500,
                            height: 200
                        }
                    },
                    {
                        display: '数据表名称',
                        align: 'left',
                        name: 'tab_name',
                        width: 150,
                        editable: false
                    },
                    {
                        display: "数据表说明",
                        align: "left",
                        width: 120,
                        name: "note",
                        editable: false
                    }
                    ];

                    gridObj.toolbar = {
                        items: [{
                            type: "button",
                            label: '查询',
                            icon: 'search',
                            id: 'query',
                            listeners: [{
                                click: query
                            }]
                        },
                        {
                            type: "button",
                            label: '添加行',
                            icon: 'add',
                            listeners: [{
                                click: add
                            }]
                        }, {
                            type: "button",
                            label: '删除',
                            icon: 'delete',
                            listeners: [{
                                click: deleteData
                            }]
                        }, {
                            type: "button",
                            label: '保存',
                            icon: 'save',
                            listeners: [{
                                click: save
                            }]
                       /*  }, {
                            type: "button",
                            label: '生成',
                            icon: 'add',
                            listeners: [{
                                click: create
                            }] */
                        }, {
                            type: "button",
                            label: '打印',
                            icon: 'print',
                            listeners: [{
                                click: print
                            }]
                        },{
                            type: "button",
                            label: '批量添加',
                            icon: 'add',
                            listeners: [{
                                click: batch_add
                            }]
                        }/* , {
                            type: "button",
                            label: '导出',
                            icon: 'export',
                            listeners: [{
                                click: exportData
                            }]
                        } , {
                            type: "button",
                            label: '导入',
                            icon: 'import',
                            listeners: [{
                                click: importData
                            }]
                        }*/
                        ]
                    };
                    grid = $("#maingrid").etGrid(gridObj);
                }

                /**
                 * 加载左侧树形
                 */
                function loadTree() {
                    tree = $("#mainTree").etTree({
                        async: {
                            enable: true,
                            url: 'queryStoreTabStrucTree.do?isCheck=false'
                        },
                        callback: {
                            // 初始默认选择第一条
                            onAsyncSuccess: function (event, treeId, treeNode, msg) {
                                var selectedNode = selectTreeDefault(tree);

                                query(selectedNode.id);
                            },
                            onClick: function (evt, idname, selectData) {
                                var value = selectData.name;
                                var selectId = selectData.id;
                                query(selectId);
                                $('#subjectCode').text(value);
                            }
                        }

                    })
                }


                function query() {
                    var selectId,
                        selected = tree.getSelectedNodes()[0];
                    if (selected) {
                        selectId = selected.id;
                    }
                    var parms = [{
                        name: 'store_type_code',
                        value: selectId
                    },

                    {
                        name: 'tab_code',
                        value: $("#tab_code").val()
                    },
                    ];

                    grid.loadData(parms, 'queryHrStoreTabStruc.do');

                }

                function add() {
                    var data = {};
                    grid.addRow(data)
                }

                /* function create() {

                } */

                function update() {
                    /*    $.etDialog.open({
                           url: 'storeTabStrucUpdatePage.do?isCheck=false',
                           height: 400,
                           width: 800,
                           title: '档案库数据配置修改页',
                           btn: ["确认", "取消"],
                           btn1: function (index, el) {
                               var frameWindow = window[el.find('iframe')[0].name];
                               frameWindow.saveData();
                           },
                           btn2: function (index) {
                               $.etDialog.close(index); // 关闭弹窗
                               return false;
                           }
                       }) */
                }

                function deleteData() {
                    var data = grid.selectGet();
                    var store_type_code = tree.getSelectedNodes()[0].id;
                    if (data.length == 0) {
                        $.etDialog.error('请选择行');
                    } else {
                        var ParamVo = [];
                        var nullRow=[];
                        $(data).each(function () {
                            var rowdata = this.rowData;
                            if(rowdata.store_type_code){
                            	ParamVo.push(rowdata);
                            }else{
                				nullRow.push(this);
                			}
                            
                        });
                        if(nullRow.length>0){
           				 grid.deleteRows(nullRow);
           			}
                        if(ParamVo.length>0){
                        $.etDialog.confirm('确定删除?', function () {
                            ajaxPostData({
                                url: "deleteHrStoreTabStruc.do",
                                data: {
                                    paramVo: JSON.stringify(ParamVo)
                                },
                                success: function (res) {
                                    if (res.state == "true") {
                                        query(store_type_code);
                                        //tree.reAsyncChildNodes(null, 'refresh');
                                    }
                                }
                            })
                        });
                        }
                    }
                }

                function save() {
                	/* var allData = grid.getAllData(); // 获取表格所有数据
                	var treeSelectedId = tree.getSelectedNodes();  // 获取tree 中选中节点数据
                	var saveParam = {
                      
                    }; */


                    var tab_code;
                    if (tree.getSelectedNodes().length) {
                        store_type_code = tree.getSelectedNodes()[0].id;

                        var allData = grid.getAllData();//获取所有数据
                        if(allData==null){
                        	$.etDialog.error("无数据保存!");
                        	return;
                        }else{
                        ajaxPostData({
                            url: 'addHrStoreTabStruc.do',
                            data: {
                                'store_type_code': store_type_code,
                                'allData': JSON.stringify(allData)
                            },
                            delayCallback: true,
                            success: function (data) {
                                query(store_type_code);
                            }
                        })
                        }
                    } else {
                        $.etDialog.error('请选择树节点');
                    }

                }

                function print() {
                	if(grid.getAllData()==null){
                		$.etDialog.error("请先查询数据！");
            			return;
            		}
                	//alert(tree.getSelectedNodes()[0].id);
                	var heads={
                    		 /* "isAuto":true,//系统默认，页眉显示页码
                    		"rows": [
                	          {"cell":0,"value":"表名："+tree.getSelectedNodes()[0].name},
                    		]  */}; 
                	var printPara={
                      		title: tree.getSelectedNodes()[0].name+" 档案库打印",//标题
                      		columns: JSON.stringify(grid.getPrintColumns()),//表头
                      		class_name: "com.chd.hrp.hr.service.sysstruc.HrStoreTabStrucService",
                   			method_name: "queryHrStoreTabStrucByPrint",
                   			bean_name: "hrStoreTabStrucService",
                   			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
                   			foots: '',//表尾需要打印的查询条件,可以为空 
                       	};
                     	$.each(grid.getUrlParms(),function(i,obj){
                   			printPara[obj.name]=obj.value;
                    	}); 
                     	console.log(printPara);
                    	officeGridPrint(printPara);
                }

                function exportData() {
                	exportGrid(grid);
                }

                function importData() {
                	var selectedNode = tree.getSelectedNodes()[0];
                	store_type_code = selectedNode ? selectedNode.id : '';
                    if (store_type_code == null || store_type_code == '' || !store_type_code) {
                        $.etDialog.error('请选择树节点');
                    }else{
                    	var para = {"column" : [ 
        	                		{
        	                			"name" : "tab_code","display" : "数据表编码","width" : "200","require" : true
        	                		},{
        	                			"name" : "tab_name","display" : "数据表名称","width" : "200","require" : true
        	                		},{
        	                			"name" : "note","display" : "备注","width" : "100","require" : false
        	                		} ]
                    	        };
               	       		importSpreadView("/hrp/hr/sysstruc/importExcel.do?isCheck=false&store_type_code="+store_type_code, para, query);
                	}
                }
                
                function batch_add(){
                	
                	if (tree.getSelectedNodes().length) {
                		parent.$.etDialog.open({
                            url: 'hrp/hr/sysstruc/storeTabBatchAddPage.do?isCheck=false',
                            width: 570,
                            height: 450,
                            title: '批量添加',
                            frameName: window.name,
                            btn: ['确定', '取消'],
                            btn1: function (index, el) {
                                var iframeWindow = parent.window[el.find('iframe')[0].name]
                                iframeWindow.save()
                            }
                        });
                    } else {
                        $.etDialog.error('请选择树节点');
                    }
                }
                
            </script>
        </head>

        <body>
            <div class="container">
                <div class="left border-right">
                    <div class="search-form">
                        <label>快速定位</label>
                        <input type="text" class="text-input" id="search_input">
                    </div>
                    <div id="mainTree"></div>
                    <div class="container-bar"></div>
                </div>
                <div class="center">
                    <table class="table-layout">
                        <tr>
                            <td class="label">数据表名称：</td>
                            <td class="ipt">
                                <input id="tab_code" type="text" />
                            </td>
                            <td class="label">档案库：</td>
                            <td class="ipt">
                                <button id="subjectCode">在职人员档案库</button>
                            </td>
                        </tr>
                    </table>

                    <div id="maingrid"></div>
                </div>
            </div>
        </body>

        </html>