<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>storeConditionMain</title>
        <jsp:include page="${path}/resource.jsp">
            <jsp:param value="hr,dialog,grid,select,tree,datepicker,pageOffice" name="plugins" />
        </jsp:include>
        <script>
            var grid, tree;
            $(function () {
                loadGrid();
                loadTree();

                $('#addTree').click(function () {
                    $.etDialog.open({
                        url: 'hrFiiedTabStrucAddPage.do?isCheck=false',
                        height: 400,
                        width: 700,
                        title: '代码表添加页',
                        btn: ['确定', '取消'],
                        btn1: function (index, el) {
                            var iframeWindow = window[el.find('iframe').get(0).name];
                            iframeWindow.saveData();
                        }
                    });
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
                    showBottom:false,
                    inWindowHeight: true,
                    cellSelect: function (event, ui) {
    					if (ui.dataIndx === 'field_col_name') {
    						var type = ui.rowData.field_col_name_type;
    						if (type === 'select') {
    							type = 'grid';
    							ui.column.editor.dynamic = type;
    							ui.column.editor.type = 'textbox';
    						}else{
    							type = 'textbox';
    							ui.column.editor.dynamic = true;
    							ui.column.editor.type = type;
    						} 
    					}
    				},
                    addRowByKey: true //  快捷键控制添加行
                };
                gridObj.numberCell = {
                    title: '#'
                };
                gridObj.columns = [{
                        display: '左括号',
                        align: 'left',
                        width: 120,
                        name: 'l_bracket'
                    },{
                        display: '数据表名',
                        align: 'left',
                        name: 'tab_name',
                        width: 120,
                        editor: {
                            type: 'select',
                            keyField: 'tab_code',
                            url: 'queryHrHosConditionTabStruc.do?isCheck=false',
                        }
                    },{
                        display: '系统结构列名',
                        align: 'left',
                        name: 'col_name',
                        width: 120,
                        relyOn: [{
                            field: 'tab_code',
                            key: 'tab_code'
                        }],
                        editor: {
                            type: 'select',
                            keyField: 'col_code',
                            change: function (rowwData, cellData) {
    							grid.updateRow(cellData.rowIndx, {
    								field_col_code: '',
    								field_col_name: '',
    								field_col_name_type: cellData.selected.type
    							});
    						}
                        }
                    },  {
                        display: '条件',
                        align: "left",
                        width: 120,
                        name:"con_sign_note",
                        editor: {
                            type: 'select',
                            keyField: 'con_sign_code',
                            url:'queryHrHosConSign.do?isCheck=false'
                        }
                    },
                    {
                        display: '数据项值',
                        align: "left",
                        width: 120,
                        name: "field_col_name",
                        relyOn: [{
                            field: 'tab_code',
                            key: 'tab_code'
                        }, {
                            field: 'col_code',
                            key: 'col_code'
                        }],
                        editor: {
                        	dynamic: true,
                        	type: 'grid',
                            columns: [{
                                display: '代码项编码',
                                align: 'left',
                                width: 120,
                                name: 'field_col_code'
                            }, {
                                display: '代码项名称',
                                align: 'left',
                                width: 120,
                                name: 'field_col_name'
                            }],
                            dataModel: {
                            	url: '../queryHrFiiedDataDicByTabCol.do?isCheck=false'
                            },
                            width: 500,
                            height: 200
                        },
                        render : function(rowdata, rowIndx,value) {
                        	if(rowdata.data[rowdata.rowIndx]._status!='add')
							{if(rowdata.data[rowdata.rowIndx].col_value != null &&rowdata.data[rowdata.rowIndx].field_col_name ==null ){
								rowdata.data[rowdata.rowIndx].field_col_name=rowdata.data[rowdata.rowIndx].col_value
								return rowdata.data[rowdata.rowIndx].field_col_name;
							}else{
								return rowdata.data[rowdata.rowIndx].field_col_name;
							}
							}
						}
                    },{
                        display: "右括号",
                        align: "left",
                        width: 120,
                        name: "r_bracket",
                    }, {
                        display: "连接符",
                        align: "left",
                        width: 120,
                        name: "join_sign_note",
                        editor: {
                            type: 'select',
                            keyField: 'join_sign_code',
                            url:'queryHrHosJoinSign.do?isCheck=false'
                        }
                    }
                ];
                gridObj.dataModel = { // 数据加载的有关属性
                    recIndx: 'l_bracket'
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
                            label: '添加',
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
                        /* }, {
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
                        }/*, {
                            type: "button",
                            label: '导出',
                            icon: 'export',
                            listeners: [{
                                click: exportData
                            }]
                         }, {
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

                $('#maingrid').on('click', '.td-a', function () {
                    var index = $(this).attr('data-item') * 1;
                    var data = grid.getRowData(index);
                    var value = $(this).text();
                    update(data, index, value);
                })
            }

            function loadTree() {
                tree = $("#mainTree").etTree({
                    async: {
                        enable: true,
                        url: 'queryStoreConditionTree.do?isCheck=false'
                    },
                    callback: {
                        onAsyncSuccess: function () {
                            var firstNode = selectTreeDefault(tree);
                            grid.getColumns()[3].editor.url = 'queryHrTabColStruc.do?isCheck=false&store_type_code=' + firstNode.id;
                            $('#subjectCode').text(firstNode.name);
                            search();
                        },
                        onClick: function (evt, idname, item) {
                        	grid.getColumns()[3].editor.url = 'queryHrTabColStruc.do?isCheck=false&store_type_code=' + item.id;
                        	$('#subjectCode').text(item.name)
                            search();
                        }
                    }
                })
            }

            function search() {
                var selects = tree.getSelectedNodes();
                var sId;
                if(selects.length) {
                	sId = selects[0].id;
                }
                var parms = [];
                //根据表字段进行添加查询条件
                 parms.push({
                     name: 'store_type_code',
                     value: sId
                 });
                 parms.push( {
                 	name:"tab_code",
                 	value: $("#tab_code").val()
                 });
               
                //加载查询条件
                grid.loadData(parms, 'queryStoreCondition.do?isCheck=false');

            }

            function add() {
                var data = {};
                grid.addRow(data)
            }

            /* function create() {

            } */

            function deleteData() {
                var data = grid.selectGet();
                var selectedNode = tree.getSelectedNodes();
                
                if (data.length == 0) {
                    $.etDialog.error('请选择行');
                    return;
                }
                if (selectedNode.length == 0) {
                    $.etDialog.error('请选择树');
                    return;
                }
                var ParamVo = [];
                var nullRow=[];
          /*       $(data).each(function () {
                    var rowdata = this.rowData;
                    ParamVo.push(rowdata);
                }); */
                
                data.forEach(function(item) {
                	 var rowdata = item.rowData;
        			if(rowdata.tab_name && rowdata.col_name){
        				ParamVo.push(rowdata);
        			}else{
        				nullRow.push(item);
        			}
        		});
        			if(nullRow.length>0){
        				 grid.deleteRows(nullRow);
        			}
        			if(ParamVo.length>0){
        		$.etDialog.confirm('确定删除?', function (index, el) {
        			 
        				 ajaxPostData({
        						url : 'deleteHrStoreCondition.do',
        						data : {
                                    paramVo: JSON.stringify(ParamVo),
                                    store_type_code: selectedNode[0].id
        						},
        						success : function(res) {
        							   if (res.state == "true") {
        	                            	grid.refresh();
        	                                search();
        	                         // tree.reAsyncChildNodes(null, 'refresh');
        	                            }
        						}
        				});
        			 });
        		}

            /*     $.etDialog.confirm('确定删除?', function () {
                    ajaxPostData({
                        url: "deleteHrStoreCondition.do", 
                        data: {
                            paramVo: JSON.stringify(ParamVo),
                            store_type_code: selectedNode[0].id
                        },
                        success: function (res) {
                            if (res.state == "true") {
                            	grid.refresh();
                                search();
                         // tree.reAsyncChildNodes(null, 'refresh');
                            }
                        }
                    })
                }); */
                
            }

            function save() {
            	var isPass = grid.validateTest({
            		required: {
            			tab_name :true,
            			col_name :true,
            			con_sign_note :true,
            			field_col_name :true,
            		}
            	})
            	if (!isPass) {
            		return;
            	}

            	var tab_code;
            	if (tree.getSelectedNodes().length) {
            		var store_type_code = tree.getSelectedNodes()[0].id;
            		
            		var Added = grid.getAllData();
                    var Updated = grid.getUpdated();

                    <%--if (Updated.length === 0 && Added.length === 0) {
                        return;
                    }--%>
                    
                    var all = grid.getAllData();
                    var lastOne = all[all.length-Added.length-1];
                    var length = 0;
                    
                    var addDataVo = [];
                    var updateDataVo = [];
                    
                    var errorMsg = '';//错误信息提示
                    
                    $(all).each(function (item, index) {
                    	
                    	if( this.field_col_code==null && this.col_value!=null){
                    		this.field_col_code=this.col_value
                    	}
                    	if(this.field_col_code==""){
                    		this.field_col_code=this.field_col_name
                    	}
                    	length = length+1;
                    	var obj = this;
                    	
                    	if(obj.tab_code == ''){
                    		return ;
                    	}
                    	
                    	
                        addDataVo.push(this.rowData);
                    });
                    all = all.map(function (item,index ){
                    	/* if(item.line_no==undefined){
                    		item.line_no = index + 1;
                    	}
                    	 */
                    	item.col_value =item.id;
	            		return item;
	            	});
                    $(Updated).each(function () {
                    	
                    	var obj = this.rowData;
                    	
                    	if(obj.tab_code == ''){
                    		return ;
                    	}
                    	
                    	if(this.rowData.field_col_code==null){
                    		this.rowData.field_col_code=this.rowData.field_col_name
                    	}
                    	addDataVo.push(this.rowData);
                    });
                    /* var  l_bracket=0;
                    var  r_bracket=0;
                    $(all).each(function( index,item){
                    	var ind=	index+1;
                    	if(index<all.length-1){
                    		if(item.join_sign_code ==null || item.join_sign_code == ""){
                       			errorMsg += '第' + (ind ) + '连接符不能为空<br/>';
                       		}	
                    	} 
                    	if(index ==all.length-1){
                    		if(item.join_sign_code !=undefined){
                       			errorMsg += '第' + (ind) + '行不能存在连接符<br/>';
                       		}	
                    	}
                     
                    		if(item.l_bracket !=null && item.l_bracket != ""){
                    			l_bracket=l_bracket+1;
                       			
                       		} 	
                    		if(item.r_bracket !=null && item.r_bracket != ""){
                    			r_bracket=r_bracket+1;
                       		}
                    		if(l_bracket>r_bracket){
                    			errorMsg += '缺失右括号<br/>';
                    		}
                    		if(l_bracket<r_bracket){
                    			errorMsg += '缺失左括号<br/>';
                    		}
                    	 
                    });
                    
                    if(errorMsg != ''){
                    	 $.etDialog.error(errorMsg);
                         return;
                    }*/
                    
/*                     addDataVo = addDataVo.map(function (item, index){
   	            		item.line_no = index + 1;
   	            		item.col_value = item.id;
   	            		return item;
   	            	});
                    
                    updateDataVo = updateDataVo.map(function (item, index){
   	            		item.line_no = index + 1;
   	            		item.col_value = item.id;
   	            		return item;
   	            	}); */
                   	

                    //console.info(addDataVo);
                    //console.info(updateDataVo);
	            	ajaxPostData({
	                    url: 'addHrStoreCondition.do',
	                    data: {
	                        'store_type_code': store_type_code, 
	                        'Updated': JSON.stringify(updateDataVo),
	                        'Added': JSON.stringify(all)
	                    },
	                    delayCallback: true,
	                    success: function (data) {
	                    	// search(store_type_code);
	                    	//解决保存后删除全部数据的问题,必须查询加载出条件行号和档案库分类编码
	                    	search();
	                    }
	                })
            	} else {
                    $.etDialog.error('请选择树节点');
                }

            }
            
			//打印
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
                  		class_name: "com.chd.hrp.hr.service.sysstruc.HrStoreConditionService",
               			method_name: "queryStoreConditionByPrint",
               			bean_name: "hrStoreConditionService",
               			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
               			foots: '',//表尾需要打印的查询条件,可以为空 
                   	};
                 	$.each(grid.getUrlParms(),function(i,obj){
               			printPara[obj.name]=obj.value;
                	}); 
                 	console.log(printPara);
                	officeGridPrint(printPara);
            }
			//导出
            function exportData() {
            	exportGrid(grid);
            }
			//导入
            function importData() {
            	var selectedNode = tree.getSelectedNodes()[0];
            	store_type_code = selectedNode ? selectedNode.id : '';
                if (store_type_code == null || store_type_code == '' || !store_type_code) {
                    $.etDialog.error('请选择树节点');
                }else{
                	var para = {"column" : [ 
    	                		{
    	                			"name" : "l_bracket","display" : "左括号","width" : "100","require" : true
    	                		},{
    	                			"name" : "tab_code","display" : "数据表名","width" : "150","require" : true
    	                		},{
    	                			"name" : "col_code","display" : "系列结构列名","width" : "150","require" : false
    	                		},{
    	                			"name" : "con_sign_code","display" : "条件","width" : "100","require" : false
    	                		},{
    	                			"name" : "col_value","display" : "数据项值","width" : "150","require" : false
    	                		},{
    	                			"name" : "r_bracket","display" : "右括号","width" : "100","require" : false
    	                		},{
    	                			"name" : "join_sign_code","display" : "连接符","width" : "100","require" : false
    	                		} ]
                	        };
           	       		importSpreadView("/hrp/hr/sysstruc/importExcelCondition.do?isCheck=false&store_type_code="+store_type_code, para, search);
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