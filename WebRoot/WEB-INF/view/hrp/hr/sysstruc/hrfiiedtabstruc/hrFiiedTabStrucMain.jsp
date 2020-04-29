<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
    <jsp:include page="${path}/resource.jsp">
        <jsp:param value="hr,dialog,grid,select,tree,pageOffice" name="plugins" />
    </jsp:include>
    <script>
        var grid, tree, field_tab_code;
        $(function () {
            loadTree();
            loadGrid();

            $('#maingrid').on('click', '.td-a', function () {
                var index = $(this).attr('data-item') * 1;
                var data = grid.getRowData(index);
                var value = $(this).text();
                update(data, index, value);
            })

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
            $('#updateTree').click(function () {
                var selected = tree.getSelectedNodes()[0];
                
                if(selected.level == 0){
                	$.etDialog.error('请选择末级');
                	return ; 
                }
                
                var selectId = selected.id;
                
                
                $.etDialog.open({
                    url: 'hrFiiedTabStrucUpdatePage.do?isCheck=false&field_tab_code=' +
                        selectId,
                    height: 400,
                    width: 700,
                    title: '代码表修改页',
                    btn: ['确定', '取消'],
                    btn1: function (index, el) {
                        var iframeWindow = window[el.find('iframe').get(0).name];
                        iframeWindow.saveData();
                    }
                });
            });
            $('#deleteTree').click(function () {
                var selected = tree.getSelectedNodes()[0];
                if(selected.level == 0){
	                $.etDialog.warn("请选择代码表！");
	                return false;
                }
                if(selected.is_innr==1){
                	 $.etDialog.warn("内置数据不允许删除！");
 	                return false;
                }
                var ParamVo = {
                    'field_tab_code': selected.id
                };
                
                $.etDialog.confirm('确定删除?', function () {
                    ajaxPostData({
                        url: "hrFiiedTabStrucDelete.do?isCheck=false",
                        data: ParamVo,
                        success: function (res) {
                            if (res.state == "true") {
                            	tree.reAsyncChildNodes(null, 'refresh');
                                search();
                            }
                        }
                    })
                });
            });
            $('#subjectCode').click(function () {
            	
            	if(tree.getSelectedNodes()[0].pId == null){
	            	return ; 
            	}
            	
                $.etDialog.open({
                    url: 'hrFiiedTabsubjectMainPage.do?isCheck=false&field_tab_code='+tree.getSelectedNodes()[0].id,
                    height: 400,
                    width: 800,
                    title: '学科分类查询页',
                    btn: ["取消"]
                })
            });

            $("#searchTree").keyup(function (e) { // 树快速定位
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
                addRowByKey: true //  快捷键控制添加行
            };
            gridObj.numberCell = {
                title: '#'
            };
            gridObj.columns = [{
                    display: "代码项编码",
                    align: "left",
                    width: 120,
                    name: "field_col_code",
                    editable : function(col){
                    	if(col.rowData){
                    		if(col.rowData.is_innr==1){
                    			return false;
                    		}
                    		return true;
                    	}else{
                    		return false;
                    	}
                    }

                },
                {
                    display: '代码项名称',
                    align: 'left',
                    name: 'field_col_name',
                    width: 120
                },
                {
                    display: "上级代码项",
                    align: "left",
                    width: 120,
                    name: "super_col_name",
                    editor: {
                    	type: 'select',
                    	keyField: 'super_col_code',
                    	url: ''
                    }
                },
                {
                    display: "是否末级",
                    align: "left",
                    width: 120,
                    name: "is_last_text",
                    is_default: true,
                    editor: {
                        type: 'select', // 下拉框编辑框
                        keyField: 'is_last',
                        source: [{
                                id: '1',
                                label: "是"
                            },
                            {
                            	id: '0',
                                label: "否"
                            }
                        ]
                    }
                },
                {
                    display: "是否内置",
                    align: "left",
                    width: 120,
                    name: "is_innr_text",
                    is_default: true,
                    editor: {
                        type: 'select', // 下拉框编辑框
                        keyField: 'is_innr',
                        source: [{
                                id: '0',
                                label: "否"
                            },
                            {
                                id: '1',
                                label: "是"
                            }
                        ]
                    }
                },
                {
                    display: "是否停用",
                    align: "left",
                    width: 120,
                    name: "is_stop_text",
                    is_default: true,
                    editor: {
                        type: 'select', // 下拉框编辑框
                        keyField: 'is_stop',
                        source: [{
                                id: '0',
                                label: "否"
                            },
                            {
                                id: '1',
                                label: "是"
                            }
                        ]
                    }
                },
                {
                    display: "备注",
                    align: "left",
                    width: 120,
                    name: "note"
                }
            ];
            gridObj.dataModel = { // 数据加载的有关属性
                url: '',
                recIndx: 'field_tab_code'
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
                        id:'add',
                        disabled:true,
                        listeners: [{
                            click: add
                        }]
                    }, {
                        type: "button",
                        label: '删除',
                        id:'delete',
                        icon: 'delete',
                        disabled:true,
                        listeners: [{
                            click: deleteData
                        }]
                    }, {
                        type: "button",
                        label: '保存',
                        id:'save',
                        icon: 'save',
                        disabled:true,
                        listeners: [{
                            click: save
                        }]
                   },  /* {
                        type: "button",
                        label: '生成',
                        icon: 'add',
                        id:'create',
                        listeners: [{
                            click: create
                        }] 
                    }, */{
                        type: "button",
                        label: '设置外部引用',
                        icon: 'add',
                        id:'setCite',
                        disabled:true,
                        listeners: [{
                            click: setCite
                        }]
                    }, {
                        type: "button",
                        label: '打印',
                        icon: 'print',
                        listeners: [{
                            click: print
                        }]
                    }, /* {
                        type: "button",
                        label: '导出',
                        icon: 'export',
                        listeners: [{
                            click: exportData
                        }]
                    },  */{
                        type: "button",
                        label: '导入',
                        icon: 'import',
                        id:'import',
                        listeners: [{
                            click: importData
                        }]
                    }
                ]
            };
            grid = $("#maingrid").etGrid(gridObj);
        }

        function loadTree() {
            tree = $("#mainTree").etTree({
                async: {
                    enable: true,
                    url: 'queryHrFiiedTabStrucTree.do?isCheck=false'
                },
                callback: {
                	onAsyncSuccess: function () {
                        var firstNode = selectTreeDefault(tree);
                        grid.getColumns()[3].editor.url = '../../queryHrFiiedDataSelect.do?isCheck=false&store_type_code=' + firstNode.id;
                        $('#subjectCode').text(firstNode.name);
                        search();
                    },
                    onClick: function (evt, idname, item) {
                    	grid.getColumns()[3].editor.url = '../../queryHrFiiedDataSelect.do?isCheck=false&store_type_code=' + item.id;
                    	$('#subjectCode').text(item.name)
                        search();
                    }
                	/* onClick: function (event, treeId, treeNode) {
                    	field_tab_code = treeNode.id;
                    	grid.getColumn('super_col_name').editor.url = '../../queryHrFiiedDataSelect.do?isCheck=false&field_tab_code='+field_tab_code;
                        var is_innr = treeNode.is_innr;
                        search();
                    } */
                },
                addSuffix: function () {
                    var treeNodes = tree.transformToArray(tree.getNodes());
                    return {
                        nodes: treeNodes,
                        rules: [
                            { rule: {is_innr: 1}, text: '内置', color: 'red' },
                            { rule: {is_cite: 1}, text: '引用', color: 'red' }
                        ]
                    }
                }

            })
        }

        function search() {
            var selected = tree.getSelectedNodes()[0];
            var sId;
            if(selected) {
            	if(!selected.level){
            		grid.setDisabledTB('add');
                    grid.setDisabledTB('delete');
                    grid.setDisabledTB('save');
                    grid.setDisabledTB('import');    
                    grid.setDisabledTB('setCite');
                }else {
	                sId = selected.id;
	                if(selected.is_cite) {
	                    grid.setDisabledTB('add');
	                    //grid.setEnabledTB('create');
	                  // grid.getColumn('field_col_code').editable(false);
	                    grid.setDisabledTB('delete');
	                    grid.setDisabledTB('save');
	                    grid.setDisabledTB('import');    
	                    grid.setEnabledTB('setCite');
	                } else {
	                	grid.setEnabledTB('add');
	                    //grid.setEnabledTB('create');
	                    grid.setEnabledTB('delete');
	                    grid.setEnabledTB('save');
	                    grid.setEnabledTB('import');
	                    grid.setDisabledTB('setCite'); 
	                }
                }
            }
            //根据表字段进行添加查询条件
            var parms = [];
            parms.push({
                name: 'field_tab_code',
                value: sId
            });
            parms.push({
                name: 'field_col_code',
                value: $('#field_col_code').val()
            });
            //加载查询条件
            grid.loadData(parms, 'queryHrFiiedData.do?isCheck=false');
        }

        function add() {
            var data = {};
            grid.addRowByDefault(data)
        }

        function create() {

        }

        function update() {


        }
        
        // 设置外部引用
        function setCite(){
        	var selected = tree.getSelectedNodes()[0];
            var sId = selected.id;
            var sText = selected.name;
        	
        	parent.$.etDialog.open({
                title: '设置外部引用',
                url: 'hrp/hr/sysstruc/hrfiiedtabstruc/hrFiiedViewAddPage.do?isCheck=false&field_tab_code='+sId+'&field_tab_name='+sText,
                isMax: true,
                frameName: window.name,
                btn: ['确定', '取消'],
                btn1: function (index, el) {
                    var iframeWindow = parent.window[el.find('iframe').get(0).name];
                    iframeWindow.saveData();
                }
            });
        }

        function deleteData() {
          	var errorMsg = '';//错误提示信息
            var ParamVo = [];
       	    var selected = tree.getSelectedNodes()[0];
            var sId = selected.id;
            var data = grid.selectGet();
            if (data.length == 0) {
                $.etDialog.error('请选择行');
            } else {
            	
            	

                var ParamVo = [],FdRows = [];  // 后台要删数据变量与不走后台要删数据变量
                $(data).each(function () {
                    var rowdata = this.rowData;
                    if (rowdata.field_col_code) {
                        ParamVo.push({'field_col_code':rowdata.field_col_code,'field_tab_code':selected.SORT});
                    } else {
                        FdRows.push(this);
                    }

                });
                if(FdRows.length > 0) {   // 直接在前台所删的数据
                    grid.deleteRows(FdRows)
                }

              
             
                if(errorMsg != ''){
                	 $.etDialog.error(errorMsg);
                     return;
                     }
                if (ParamVo.length > 0) {  
                $.etDialog.confirm('确定删除?', function () {
                    ajaxPostData({
                        url: "deleteHrFiiedData.do?isCheck=false",
                        data: {
                            ParamVo: JSON.stringify(ParamVo)
                        },
                        success: function (res) {
                            if (res.state == "true") {
                                search();
                                /* tree.reAsyncChildNodes(null, 'refresh'); */
                            }
                        }
                    })
                });
                }
            }
        }

        function save() {
        	var isPass = grid.validateTest({
        		required: {
        			field_col_code:true,
        			field_col_name:true,
        			is_last_text:true,
        			is_innr_text:true,
        			is_stop_text:true
        		},
        		type: {
        			//is_stop:'number'
        		}
        	})
        	//console.log(isPass)
        	if (!isPass) {
        		return;
        	}
        	
        	var selected = tree.getSelectedNodes()[0];
            var sId;
        	if(selected){
        		sId = selected.id
        		var allData = grid.getAllData();
	        	//var addData = grid.getAdded(); //添加数据
	            //var modData = grid.getUpdated(); //修改数据'
	            //var addDataVo = [];
	            //var modDataVo = [];
	
	            //$(addData).each(function () {
	            //	this.rowData.field_tab_code = sId;
	            //    addDataVo.push(this.rowData);
	            //});
	            //$(modData).each(function () {
	            //	this.rowData.field_tab_code = sId;
	            //    modDataVo.push(this.rowData);
	            //});
	            $(allData).each(function () {
	            	this.field_tab_code = sId;
	            });
	            if (allData==null) {
	                $.etDialog.error('请选择行');
	            } else {
	            ajaxPostData({
	                url: 'saveHrFiiedData.do?isCheck=false',
	                data: {
	                	'field_tab_code' : sId,
	                	'allData' : JSON.stringify(allData)
	                    //'addData': JSON.stringify(addDataVo),
	                    //'modData': JSON.stringify(modDataVo)
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
        	
        	 
        	/*  var data = grid.selectGet();
             if (data.length == 0) {
                 $.etDialog.error('请选择行');
             } else {
                 var ParamVo = [];
                 $(data).each(function () {
                     var rowdata = this.rowData;
                   
                     ParamVo.push(rowdata);
                 });
                 console.log(JSON.stringify(ParamVo));
                 return;
                 ajaxPostData({
                     url: "saveHrFiiedData.do?isCheck=false",
                     data: {
                         ParamVo: JSON.stringify(ParamVo)
                     },
                     success: function (res) {
                         if (res.state == "true") {
                             search();
                         }
                     }
                 })
             } */

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
              		title: tree.getSelectedNodes()[0].name+" 代码表打印",//标题
              		columns: JSON.stringify(grid.getPrintColumns()),//表头
              		class_name: "com.chd.hrp.hr.service.sysstruc.HrFiiedTabStrucService",
           			method_name: "queryHrFiiedDataByPrint",
           			bean_name: "hrFiiedTabStrucService",
           			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
           			foots: '',//表尾需要打印的查询条件,可以为空 
               	};
             	$.each(grid.getUrlParms(),function(i,obj){
           			printPara[obj.name]=obj.value;
            	}); 
            	officeGridPrint(printPara);
        }

        function exportData() {
        	exportGrid(grid);
        }
        //导入数据
        function importData(){
    		//$("form[name=fileForm]").submit();
    		var selected = tree.getSelectedNodes()[0];
    		
    		if(selected){
    		var field_tab_code = selected.id;
    		var para = {
    			"column" : [ {
    				"name" : "field_col_code",
    				"display" : "代码项编码",
    				"width" : "200",
    				"require" : true
    			},{
    				"name" : "field_col_name",
    				"display" : "代码项名称",
    				"width" : "200",
    				"require" : true
    			},{
    				"name" : "super_col_name",
    				"display" : "上级代码项",
    				"width" : "200",
    				/* "require" : true */
    			},{
    				"name" : "is_last_text",
    				"display" : "是否末级",
    				"width" : "200",
    				"require" : true
    			},{
    				"name" : "is_innr_text",
    				"display" : "是否内置",
    				"width" : "200",
    				"require" : true
    			},{
    				"name" : "is_stop_text",
    				"display" : "是否停用",
    				"width" : "200",
    				"require" : true
    			},{
    				"name" : "note",
    				"display" : "备注",
    				"width" : "200"
    			} ]

    		};
    		importSpreadView("/hrp/hr/sysstruc/hrfiiedtabstruc/importDateHFTS.do?isCheck=false&&field_tab_code="+field_tab_code, para, search);
    		}else{
    			$.etDialog.error('请选择树节点');
    		}
    	}
    </script>
</head>

<body>
    <div class="container">
        <div class="left border-right">
            <div class="button-group">
                <button id="addTree">增加</button>
                <button id="updateTree">修改</button>
                <button id="deleteTree">删除</button>
            </div>
            <div class="search-form">
                <label>快速定位</label>
                <input type="text" id="searchTree" class="text-input">
            </div>
            <div id="mainTree"></div>
            <div class="container-bar"></div>
        </div>
        <div class="center">
            <table class="table-layout">
                <tr>
                    <td class="label">代码项编码：</td>
                    <td class="ipt">
                        <input id="field_col_code" type="text" />
                    </td>
                    <td class="label">代码表名称：</td>
                    <td class="ipt" style="white-space:nowrap">
                        <button id="subjectCode">学科分类与代码</button>
                    </td>
                </tr>
            </table>

            <div id="maingrid"></div>
        </div>
    </div>
</body>

</html>