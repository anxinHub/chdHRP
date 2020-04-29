<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
	String path = request.getContextPath();
	%>
        <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
        <html>

        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>hrTabStrucMain</title>
            <jsp:include page="${path}/resource.jsp">
                <jsp:param value="hr,dialog,grid,select,tree,datepicker,pageOffice" name="plugins" />
            </jsp:include>
            <script>
                var tree, grid, tab_code;
                $(function () {
                    loadTree();
                    loadGrid();

                    $('#addTabStrucBtn').on('click', function () {
                        addTabStruc();
                    })

                    $('#modTabStrucBtn').on('click', function () {
                        updateTabStruc();
                    })

                    $('#delTabStrucBtn').on('click', function () {
                        deleteTabStruc();
                    })

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
                        inWindowHeight: true,
                        freezeCols: 2,
                        addRowByKey: true //  快捷键控制添加行
                    };
                    gridObj.columns = [{
                            display: "数据列编码",
                            align: "left",
                            width: 120,
                            name: "col_code",
                            editable : function(col){
                            	if(col.rowData){
                            		if(col.rowData.is_innr==1){
                            			return false;
                            		}
                            		return true;
                            	}else{
                            		return true;
                            	}
                            }
                        },
                        {
                            display: '数据列名称',
                            align: 'left',
                            name: 'col_name',
                            width: 120,
                            editable : function(col){
                            	if(col.rowData){
                            		if(col.rowData.is_innr==1){
                            			return false;
                            		}
                            		return true;
                            	}else{
                            		return true;
                            	}
                            }
                        },
                        {
                            display: "类型",
                            align: "left",
                            width: 120,
                            name: "data_type_name",
                            editor: {
                                type: 'select', // 下拉框编辑框
                                keyField: 'data_type',
                                url: '../../queryHrColDataType.do?isCheck=false'
                            },
                            editable : function(col){
                            	if(col.rowData){
                            		if(col.rowData.is_innr==1){
                            			return false;
                            		}
                            		return true;
                            	}else{
                            		return true;
                            	}
                            }
                        },
                        {
                            display: "长度",
                            align: "right",
                            dataType: "integer",
                            width: 120,
                            name: "filed_length",
                            editable : function(col){
                            	if(col.rowData){
                            		if(col.rowData.is_innr==1){
                            			return false;
                            		}
                            		return true;
                            	}else{
                            		return true;
                            	}
                            }
                        },
                        {
                            display: "小数位",
                            align: "right",
                            dataType: "integer",
                            width: 120,
                            name: "prec",
                            editable : function(col){
                            	if(col.rowData){
                            		if(col.rowData.is_innr==1){
                            			return false;
                            		}
                            		return true;
                            	}else{
                            		return true;
                            	}
                            }
                        },
                        {
                            display: "是否内置",
                            align: "left",
                            width: 120,
                            name: "is_innr_text",
                            editable: false,
                            editor: {
                                type: 'select', // 下拉框编辑框
                                keyField: 'is_innr',
                                source: [{
                                        id: '1',
                                        label: "是"
                                    },
                                    {
                                        id: '0',
                                        label: "否"
                                    }
                                ]
                            },
                        },
                        { 	display: "是否唯一",
                            align: "left",
                            width: 120,
                            name: "is_unique_text",
                            editor: {
                                type: 'select', // 下拉框编辑框
                                keyField: 'is_unique',
                                source: [{
                                        id: '1',
                                        label: "是"
                                    },
                                    {
                                        id: '0',
                                        label: "否"
                                    }
                                ]
                            },
                            editable : function(col){
                            	if(col.rowData){
                            		if(col.rowData.is_innr==1){
                            			return false;
                            		}
                            		return true;
                            	}else{
                            		return true;
                            	}
                            }
                        },
                        {
                            display: "相关代码",
                            align: "left",
                            width: 120,
                            name: "field_tab_name",
                            editor: {
                                type: 'grid',
                                keyField: 'field_tab_code',
                                resizable: true,
                                columns: [{
                                    display: '代码表编码',
                                    align: 'left',
                                    width: 120,
                                    name: 'field_tab_code'
                                }, {
                                    display: '代码表名称',
                                    align: 'left',
                                    width: 120,
                                    name: 'field_tab_name'
                                }, {
                                    display: '代码表分类',
                                    align: 'left',
                                    width: 120,
                                    name: 'type_filed_name'
                                }, {
                                    display: '备注',
                                    align: 'left',
                                    width: 120,
                                    name: 'note'
                                }],
                                usePager : false,
                                dataModel: {
                                    url: '../../queryHrFiiedTabStruc.do?isCheck=false',
                                },
                                width: 500,
                                height: 200
                            }
                        },
                        {
                            display: "是否主键",
                            align: "left",
                            width: 120,
                            name: "is_pk_text",
                            editor: {
                                type: 'select', // 下拉框编辑框
                                keyField: 'is_pk',
                                source: [{
                                        id: '1',
                                        label: "是"
                                    },
                                    {
                                        id: '0',
                                        label: "否"
                                    }
                                ]
                            },
                            editable : function(col){
                            	if(col.rowData){
                            		if(col.rowData.is_innr==1){
                            			return false;
                            		}
                            		return true;
                            	}else{
                            		return true;
                            	}
                            }
                        }/* , {
                            display: "是否必填",
                            align: "left",
                            width: 120,
                            name: "is_m_text",
                            editor: {
                                type: 'select', // 下拉框编辑框
                                keyField: 'is_m',
                                source: [{
                                        id: '1',
                                        label: "是"
                                    },
                                    {
                                        id: '0',
                                        label: "否"
                                    }
                                ],
                            }
                        } */, {
                            display: "排序",
                            align: "right",
                            width: 60,
                            dataType: "integer",
                            name: "sort"
                        }, {
                            display: "备注",
                            align: "left",
                            width: 120,
                            name: "note"
                        }
                    ];
                    gridObj.dataModel = { // 数据加载的有关属性
                        url: 'queryHrColStruc.do',
                        recIndx: 'tab_code'
                    };
                    gridObj.toolbar = {
                        items: [{
                                type: "button",
                                label: '查询',
                                icon: 'search',
                                id: 'search',
                                listeners: [{
                                    click: search
                                }]
                            },
                            {
                                type: "button",
                                label: '添加行',
                                icon: 'add',
                                id: 'add',
                                disabled:true,
                                listeners: [{
                                    click: add
                                }]
                            }, {
                                type: "button",
                                label: '删除',
                                disabled:true,
                                icon: 'delete',
                                id: 'delete',
                                listeners: [{
                                    click: deleteData
                                }]
                            }, {
                                type: "button",
                                label: '保存',
                                disabled:true,
                                icon: 'save',
                                id: 'save',
                                listeners: [{
                                    click: save
                                }]
                            }, {
                                type: "button",
                                label: '打印',
                                icon: 'print',
                                listeners: [{
                                    click: print
                                }]
                            }/* , {
                                type: "button",
                                label: '导出',
                                icon: 'export',
                                listeners: [{
                                    click: exportData
                                }]
                            } */, {
                                type: "button",
                                label: '导入',
                                icon: 'import',
                                listeners: [{
                                    click: importData
                                }]
                            }
                        ]
                    };
                    grid = $("#maingrid").etGrid(gridObj);

                    /* $('#maingrid').on('click', '.td-a', function () {
                        var index = $(this).attr('data-item') * 1;
                        var data = grid.getRowData(index);
                        var value = $(this).text();
                        update(data, index, value);
                    }) */
                }

                function loadTree() {
                    tree = $("#mainTree").etTree({
                        async: {
                            enable: true,
                            url: 'queryHrTabStrucTree.do?isCheck=false'
                        },
                        callback: {
                            onClick: function () {
                                search();
                            }
                        },
                        addSuffix: function () {
                            var treeNodes = tree.transformToArray(tree.getNodes());
                            return {
                                nodes: treeNodes,
                                rules: [
                                    { rule: {is_innr: 1}, text: '内置', color: 'red' }
                                ]
                            }
                        }

                    })
                }

                function search() {
                    var selectedNode = tree.getSelectedNodes()[0];
                    tab_code = selectedNode ? selectedNode.id : '';
                    
                    if(selectedNode){
                    
                    if(selectedNode.level){
	                    grid.setEnabledTB('add');
	                	grid.setEnabledTB('save');
	                	grid.setEnabledTB('delete');
	                  
                    }	
                    }else{
                    	grid.setDisabledTB('add');
	                	grid.setDisabledTB('save');
	                	grid.setDisabledTB('delete');
                    }
                    var param = [{
                        name: 'tab_code',
                        value: tab_code
                    },
                    {
                        name: 'col_name',
                        value: $('#col_name').val()
                    }
                ];
                grid.loadData(param);
                }

                function addTabStruc() {
                    $.etDialog.open({
                        url: 'hrTabStrucAddPage.do?isCheck=false',
                        height: 400,
                        width: 700,
                        title: '数据表构建添加页',
                        btn: ['确定', '取消'],
                        btn1: function (index, el) {
                            var iframeWindow = window[el.find('iframe').get(0).name];
                            iframeWindow.saveData();
                        }
                    });
                }

                function updateTabStruc() {
                    //获取树当前选择项
                    if (tree.getSelectedNodes().length) {
                    	if(tree.getSelectedNodes()[0].pId){
	                        var parm = 'tab_code=' + tree.getSelectedNodes()[0].id;
	                        $.etDialog.open({
	                            url: 'hrTabStrucUpdatePage.do?isCheck=false&' + parm,
	                            height: 400,
	                            width: 600,
	                            title: '数据表构建修改页',
	                            btn: ["确定", "取消"],
	                            btn1: function (index, el) {
	                                var frameWindow = window[el.find('iframe')[0].name];
	                                frameWindow.saveData();
	                            },
	                            btn2: function (index) {
	                                $.etDialog.close(index); // 关闭弹窗
	                                return false;
	                            }
	                        })
                    	}else{
                            $.etDialog.error('请选择数据表');
                    	}
                    } else {
                        $.etDialog.error('请选择树节点');
                    }
                }

                function deleteTabStruc() {
                    //获取树当前选择项
                    if (tree.getSelectedNodes().length) {
                    	//console.info(tree.getSelectedNodes()[0].pId);
                    	if(tree.getSelectedNodes()[0].pId==null){
                    		$.etDialog.error('请选择末级节点');
                    		return;
                    	}
                        $.etDialog.confirm('确定删除?', function () {
                            ajaxPostData({
                                url: "deleteHrTabStruc.do",
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
                    }
                }

                //添加行（子集）
                function add() {
                    var obj = {is_innr_text:'否'};
                    grid.addRow(obj);
                }


                //保存子集
                function save() {
                	/* var isPass = grid.validateTest({
                		required: {
                			
                		},
                		type: {
                			
                		}
                	})
                	if (!isPass) {
                		return;
                	} */
                    if (tab_code != null && tab_code != '') {
                        var addData = grid.getAdded(); //添加数据
                        var modData = grid.getUpdated(); //修改数据'
                        var allData = grid.getAllData(); //修改数据'
                        var addDataVo = [];
                        var modDataVo = [];

                        var flag = true; //用来判断
                        var flag02 = true; //用来判断
                        var flag03 = true; //用来判断
                        var flag04 = true; //用来判断
                        var reg = /(^_([a-zA-Z0-9]_?)*$)|(^[a-zA-Z](_?[a-zA-Z0-9])*_?$)/;
                        var str = "";
                        
                        $(modData).each(function () {
                        	if (!(flag02=reg.test(this.rowData.col_code))){
                        		str+="第"+(this.rowData._rowIndx+1)+"行、";
                        	}
                        	if (this.rowData.sort=="" || this.rowData.sort== null){
                        		flag03 = false;
                        		str+="第"+(this.rowData._rowIndx+1)+"行、";
                        	}
                        	if (this.rowData.field_tab_name=="" || this.rowData.field_tab_name== null){
                        		this.rowData.field_tab_code=""
                        		
                        	}
                            modDataVo.push(this.rowData);
                        });
                        
                        $(addData).each(function () {
                        	if (!(flag=reg.test(this.rowData.col_code))){
                        		str+="第"+(this.rowData._rowIndx+1)+"行、";
                        	}
                        	if (this.rowData.sort=="" || this.rowData.sort== null){
                        		flag04 = false;
                        		str+="第"+(this.rowData._rowIndx+1)+"行、";
                        	}
                            addDataVo.push(this.rowData);
                        });
                        
                        if(!flag || !flag02){
                        	$.etDialog.error(str.substring(0,str.length-1)+"数据列编码非法，请重新命名。");
                        	return;
                        }
                        if(!flag03 || !flag04){
                        	$.etDialog.error(str.substring(0,str.length-1)+"排序列不能为空。");
                        	return;
                        }
                        if(allData==null){
                        	$.etDialog.error("请选择需要保存的数据!");
                        	return;
                        }else{
                        ajaxPostData({
                            url: 'saveHrColStruc.do',
                            data: {
                                'tab_code': tab_code,
                                'addData': JSON.stringify(addDataVo),
                                'modData': JSON.stringify(modDataVo)
                            },
                            delayCallback: true,
                            success: function (data) {
                                search();
                            }
                        })
                        }
                    } else {
                        $.etDialog.error('请选择树节点');
                    }
                }

                //删除子集
                function deleteData() {
                    var data = grid.selectGet();
                    if (tab_code == null || tab_code == '' || !tab_code) {
                        $.etDialog.error('请选择树节点');
                    } else if (data.length == 0) {
                        $.etDialog.error('请选择行');
                    } else {
                        var ParamVo = [],FdRows = [];  // 后台要删数据变量与不走后台要删数据变量
                        $(data).each(function () {
                            var rowdata = this.rowData;
                            if (rowdata.col_code) {
                                ParamVo.push({'group_id':rowdata.group_id,'hos_id':rowdata.hos_id,'tab_code':rowdata.tab_code,'col_code':rowdata.col_code,'is_innr':rowdata.is_innr});
                            } else {
                                FdRows.push(this);
                            }

                        });
                        if(FdRows.length > 0) {   // 直接在前台所删的数据
                            grid.deleteRows(FdRows)
                        }

                        if (ParamVo.length > 0) {  // 通过后台所删的数据
                            $.etDialog.confirm('确定删除?', function () {
                                ajaxPostData({
                                    url: "deleteHrColStruc.do",
                                    data: {
                                        paramVo: JSON.stringify(ParamVo),
                                        tab_code: tab_code
                                    },
                                    success: function (res) {
                                        if (res.state == "true") {
                                            search();
                                            //tree.reAsyncChildNodes(null, 'refresh');
                                        }
                                    }
                                })
                            });
                        }
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
                      		title: tree.getSelectedNodes()[0].name+" 数据表打印",//标题
                      		columns: JSON.stringify(grid.getPrintColumns()),//表头
                      		class_name: "com.chd.hrp.hr.service.sysstruc.HrColStrucService",
                   			method_name: "queryHrColStrucByPrint",
                   			bean_name: "hrColStrucService",
                   			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
                   			foots: '',//表尾需要打印的查询条件,可以为空 
                       	};
                     	$.each(grid.getUrlParms(),function(i,obj){
                   			printPara[obj.name]=obj.value;
                    	}); 
                     	//console.log(printPara);
                    	officeGridPrint(printPara);
                }

                function exportData() {
                	exportGrid(grid);
                }

                function importData() {
                	var selectedNode = tree.getSelectedNodes()[0];
                    tab_code = selectedNode ? selectedNode.id : '';
                    if (tab_code == null || tab_code == '' || !tab_code) {
                        $.etDialog.error('请选择树节点');
                    }else{
                    	var para = {"column" : [ 
        	                		{
        	                			"name" : "col_code","display" : "数据列编码","width" : "200","require" : true
        	                		},{
        	                			"name" : "col_name","display" : "数据列名称","width" : "200","require" : true
        	                		},{
        	                			"name" : "data_type_name","display" : "数据类型","width" : "100","require" : true
        	                		},{
        	                			"name" : "length","display" : "长度","width" : "100","require" : false
        	                		},{
        	                			"name" : "prec","display" : "小数位数","width" : "90","require" : false
        	                		},{
        	                			"name" : "is_pk","display" : "是否主键","width" : "80","require" : false
        	                		},{
        	                			"name" : "is_m","display" : "是否必填","width" : "80","require" : false
        	                		},{
        	                			"name" : "is_innr","display" : "是否内置","width" : "80","require" : false
        	                		},{
        	                			"name" : "field_tab_code","display" : "代码表","width" : "80","require" : false
        	                		},{
        	                			"name" : "sort","display" : "排序号","width" : "200","require" : false
        	                		},{
        	                			"name" : "note","display" : "备注","width" : "200","require" : false
        	                		} ]
                    	        };
               	       		importSpreadView("/hrp/hr/sysstruc/hrtabstruc/importExcel.do?isCheck=false&tab_code="+tab_code, para);
                    	
                    }
                    
                	
                		
                	/* location.href="/CHD-HRP/print/importForHr.jsp" */
                	/* var para = {
       					 "column" : [{
       						"name" : "",
       						"display" : "",
       						"width" : "200",
       						"require" : true
       					}] 
       				
       				};
       				importSpreadView("",para); */
                }
            </script>
        </head>

        <body>
            <div class="container">
                <div class="left border-right">
                    <div class="button-group">
                        <button id="addTabStrucBtn">增加</button>
                        <button id="modTabStrucBtn">修改</button>
                        <button id="delTabStrucBtn">删除</button>
                    </div>
                    <div class="search-form">
                        <label>快速定位</label>
                        <input type="text" id="search_input" class="text-input">
                    </div>
                    <div id="mainTree"></div>
                    <div class="container-bar"></div>
                </div>
                <div class="center">
                    <table class="table-layout">
                        <tr>
                            <td class="label">数据列名称：</td>
                            <td class="ipt">
                                <input id="col_name" type="text" />
                            </td>
                        </tr>
                    </table>

                    <div id="maingrid"></div>
                </div>
            </div>
        </body>

        </html>