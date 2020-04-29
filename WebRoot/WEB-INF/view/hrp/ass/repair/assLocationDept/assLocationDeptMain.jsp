<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
   <jsp:include page="${path}/resource.jsp">
        <jsp:param value="hr,dialog,grid,select,tree" name="plugins" />
    </jsp:include>
    <script type="text/javascript">
    var grid, tree;
    $(function () {
        loadTree();
        loadGrid();
        loadDict();
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
    
      function loadDict() {
                // loc_code = $("#loc_code").etSelect({
                //     url: '',
                //     defaultValue: "none"
                // });

                // loc_name = $("#loc_name").etSelect({
                //     url: '',
                //     defaultValue: "none"
                // });

              /*   super_code = $("#super_code").etSelect({
                    url: '../../querySuperLocationSelect.do?isCheck=false',
                    defaultValue: "none"
                }); */

              /*   is_last = $("#is_last").etSelect({
                    options: [{
                        id: 1,
                        text: '是'
                    }, {
                        id: 0,
                        text: '否'
                    }],
                    defaultValue: "none"
                }); */
                is_stop = $("#is_stop").etSelect({
                    options: [{
                        id: 1,
                        text: '是'
                    }, {
                        id: 0,
                        text: '否'
                    }],
                    defaultValue: "none"
                });
            }
    
    function loadGrid() {
        var gridObj = {
            editable: false,
            checkbox: true,
            height: '100%',
            inWindowHeight: true,
            addRowByKey: true //  快捷键控制添加行
        };
        gridObj.numberCell = {
            title: '#'
        };
        gridObj.columns = [
        	{
                display: "部门编码",
                align: "left",
                width: 120,
                name: "dept_code" ,
           		render: function (ui) { // 修改页打开
                    return '<a data-item=' + ui.rowIndx + ' class="td-a">' + ui.cellData +'</a>'
                } 

            },
        	{
                display: "部门名称",
                align: "left",
                width: 120,
                name: "dept_name"/* ,
           		render: function (ui) { // 修改页打开
                    return '<a data-item=' + ui.rowIndx + ' class="td-a">' + ui.cellData +'</a>'
                } */

            },
        	{
                display: "位置编码",
                align: "left",
                width: 120,
                name: "loc_code"/* ,
           		render: function (ui) { // 修改页打开
                    return '<a data-item=' + ui.rowIndx + ' class="td-a">' + ui.cellData +'</a>'
                } */

            },
            {
                display: '位置名称',
                align: 'left',
                name: 'loc_name',
                width: 120
            },
            {
                display: "上级编码",
                align: "left",
                width: 120,
                name: "super_code"
            },
            {
                display: "是否末级",
                align: "left",
                width: 120,
                name: "is_last",
                render: function (ui) { // 修改页打开
                	return  ui.cellData==0?'否':'是';
                }
            },
            {
                display: "拼音码",
                align: "left",
                width: 120,
                name: "spell_code"
            },
            {
                display: "五笔码",
                align: "left",
                width: 120,
                name: "wbx_code"
            },
            {
                display: "是否停用",
                align: "left",
                width: 120,
                name: "is_stop",
                render: function (ui) { // 修改页打开
                	return  ui.cellData==0?'否':'是';
                }
            },
            {
                display: "全称",
                align: "left",
                width: 120,
                name: "loc_name_all"
            }
        ];
      /*   gridObj.dataModel = { // 数据加载的有关属性
             url: '',
            recIndx: 'loc_code' 
        }; */
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
                    listeners: [{
                        click: addAssLocationTree
                    }]
                }, {
                    type: "button",
                    label: '删除',
                    id:'delete',
                    icon: 'delete',
                    listeners: [{
                        click: deleteData
                    }]
                }/* , {
                    type: "button",
                    label: '保存',
                    id:'save',
                    icon: 'save',
                    listeners: [{
                        click: save
                    }]
                }, {
                    type: "button",
                    label: '生成',
                    icon: 'add',
                    id:'create',
                    listeners: [{
                        click: create
                    }]
                }, {
                    type: "button",
                    label: '打印',
                    icon: 'print',
                    listeners: [{
                        click: print
                    }]
                }, {
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
                    id:'import',
                    listeners: [{
                        click: importData
                    }]
                } */
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
	/* function update(data, index, value){
	    	 $.etDialog.open({
	             url: 'assLocationUpdatePage.do?isCheck=false',
	             height: 400,
	             width: 400,
	             title: '修改位置',
	             btn: ['确定', '取消'],
	             btn1: function (index, el) {
	                 var iframeWindow = window[el.find('iframe').get(0).name];
	                 iframeWindow.saveData();
	             }
	         });
	} */
	
	function update(data, index, value) {
        var parm = [
        	'dept_id='+data.dept_id+","+data.loc_code
        ]
        console.log(parm)
        $.etDialog.open({
            url: 'assLocationDeptUpdatePage.do?isCheck=false&'+parm,
            height: 400,
            width: 400,
            title: '修改位置',
            btn: ["确定", "取消"],
            btn1: function (index, el) {
                var frameWindow = window[el.find('iframe')[0].name];
                frameWindow.saveData();
            },
            btn2: function (index) {
                $.etDialog.close(index); // 关闭弹窗
                return false;
            }
        });

    }

    function loadTree() {
        tree = $("#mainTree").etTree({
            async: {
                enable: true,
                url: 'queryAssLocationDeptTree.do?isCheck=false'
            } ,
            callback: {
                onClick: function (event, treeId, treeNode) {
                    var dept_id = treeNode.DEPT_ID.split(",")[0];
                    search();
                }
            }, 
         /*    addSuffix: function () {
                var treeNodes = tree.transformToArray(tree.getNodes());
                return {
                    nodes: treeNodes,
                    rules: [
                        { rule: {is_innr: 1}, text: '内置', color: 'red' },
                        { rule: {is_cite: 1}, text: '外引', color: 'red' }
                    ]
                }
            } */

        })
    }
    function search() {
        var selected = tree.getSelectedNodes()[0];
        var sId;
        if(selected) {
            sId = selected.DEPT_ID.split(",")[0];
           /*  if(selected.is_innr || selected.is_cite) {
                grid.setDisabledTB('add');
                grid.setDisabledTB('create');
                grid.setDisabledTB('delete');
                grid.setDisabledTB('save');
                grid.setDisabledTB('import');                                                                                
            } else {
            	grid.setEnabledTB('add');
                grid.setEnabledTB('create');
                grid.setEnabledTB('delete');
                grid.setEnabledTB('save');
                grid.setEnabledTB('import');
            } */
        }
        //根据表字段进行添加查询条件
        var parms = [];
        parms.push({
            name: 'dept_id',
            value: sId
        });
        parms.push({
            name: 'loc_code',
            value: $('#loc_code').val()
        });
        parms.push({
            name: 'loc_name',
            value: $('#loc_name').val()
        });
        parms.push({
            name: 'is_stop',
            value: $('#is_stop').val()
        });
        //加载查询条件
        grid.loadData(parms, 'queryAssLocationDept.do?isCheck=false');
    }
    
    function addAssLocationTree() {
    	  var selected = tree.getSelectedNodes()[0];
          if (!selected) {
              $.etDialog.error('请选择部门');
          } else {
    	 $.etDialog.open({
             url: 'assLocationDeptAddPage.do?isCheck=false&dept_id='+selected.DEPT_ID,
             height: 400,
             width: 400,
             title: '添加位置',
             btn: ['确定', '取消'],
             btn1: function (index, el) {
                 var iframeWindow = window[el.find('iframe').get(0).name];
                 iframeWindow.saveData();
             }
         });
          }
    }

    function create() {

    }

    
    function deleteData() {
   	  
        var data = grid.selectGet();
        if (data.length == 0) {
            $.etDialog.error('请选择行');
        } else {
            var ParamVo = [];
           
            $(data).each(function () {
                var rowdata = this.rowData;
                ParamVo.push(
               		rowdata.loc_code+"@"+rowdata.dept_id.split(",")[0]
                		);
            });
            console.log(JSON.stringify(ParamVo));
            $.etDialog.confirm('确定删除?', function () {
                ajaxPostData({
                    url: "deleteAssLocationDept.do?isCheck=false",
                    data: {ParamVo : ParamVo.toString()},
                    success: function (res) {
                        if (res.state == "true") {
                            search();
                            tree.reAsyncChildNodes(null, 'refresh');
                        }
                    }
                })
            });
        }
    }
    
    </script>
</title>
</head>
<body>
	<div class="container">
        <div class="left border-right">
            <div class="button-group">
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
                    <td class="label">位置编码：</td>
                    <td class="ipt">
                        <input id="loc_code" type="text" style="width:180px;"/>
                    </td>
                    <td class="label">位置名称：</td>
                    <td class="ipt">
                        <input id="loc_name" type="text" style="width:180px;"/>
                    </td>
                    <td class="label">是否停用：</td>
                    <td class="ipt">
                        <select id="is_stop" name="is_stop" style="width:180px;"></select>
                    </td>
                     
                </tr>
            </table>

            <div id="maingrid"></div>
        </div>
   </div>
</body>
</html>