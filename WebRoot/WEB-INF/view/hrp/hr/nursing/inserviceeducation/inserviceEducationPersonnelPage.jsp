<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="dialog,select,grid,hr,tree" name="plugins" />
	</jsp:include>
    <script>
        var dept_name, duty, title, dict_level;
        var tree, grid;
        var initFrom = function () {
          
            duty_code = $("#duty_code").etSelect({
                url: "../queryDuty.do?isCheck=false",
                defaultValue: "none",
                onChange: query,
            });
            title_code = $("#title_code").etSelect({
                url: "../queryHrTitle.do?isCheck=false",
                defaultValue: "none",
                onChange: query,
            });
            level_code = $("#level_code").etSelect({
                url: "../queryDicLevel.do?isCheck=false",
                defaultValue: "none",
                onChange: query,
            });
        };
        var initTree = function () {
            tree = $("#mainTree").etTree({
                async: {
                    enable: true,
                   url: '../queryDeptTree.do?isCheck=false'
                },
                callback: {
                    onClick: function (event, treId, treeNode) {
                    	//console.log(tree.getNodes());
                        query(treeNode.id);
                    },
                    onAsyncSuccess: function (event, treeId, treeNode, msg) {
                       // console.log(treeNode, msg)
                    }
                }
            })
        };
        var query = function (treeId) {
        	var params = [];
            var selectedNode = tree.getSelectedNodes();
            if(selectedNode.length>0 && selectedNode[0].id!="0"){
            	params.push( { name: 'dept_code', value: selectedNode[0].id });
             }
            params.push( { name: 'emp_code', value: $("#emp_code").val() });
            params.push( { name: 'emp_name', value: $("#emp_name").val() });
            params.push( { name: 'duty_code', value: duty_code.getValue() });
            params.push( { name: 'title_code', value: title_code.getValue() });
            params.push( { name: 'level_code', value: level_code.getValue() });
            
            grid.loadData(params,'../queryEmpDuty.do?isCheck=false');
        };
        var close = function () {
            var curIndex = parent.$.etDialog.getFrameIndex(window.name);
            parent.$.etDialog.close(curIndex);
        };
        var introduce = function () {
            var selectedData = grid.selectGet();

            if (selectedData.length === 0) {
                $.etDialog.error('请选择行');
                return;
            }

            var parentFrameName = parent.$.etDialog.getFrameName('add');
            var parentWindow = parent.window[parentFrameName];
            var parentGridData = parentWindow.grid.getAllData();

            // 过滤参数， 过滤重复数据
            var introduceData = [];
            selectedData.forEach(function (item) {
                if (parentGridData) {
                    var isRepeat = parentGridData.some(function (element) {
                        return element.emp_id == item.rowData.emp_id;
                    })
                    if (!isRepeat) {
                        introduceData.push(item.rowData);
                    }
                } else {
                    introduceData.push(item.rowData);
                }
            });
            parentWindow.grid.addRows(introduceData);
            $.etDialog.success('添加成功，保存后生效');
            //close();
        };
        
        var initGrid = function () {
            var columns = [
				{ display: '职工ID', name: 'emp_id', width: 120,hidden: true },
                { display: '职工工号', name: 'emp_code', width: 120 },
                { display: '职工姓名', name: 'emp_name', width: 120 },
                { display: '科室名称', name: 'dept_name', width: 120 },
                { display: '职务', name: 'duty_name', width: 120 },
                { display: '职称', name: 'title_name', width: 120 },
                { display: '护理等级', name: 'level_name', width: 120 }
            ];
            var paramObj = {
                height: '100%',
                inWindowHeight: true,
                checkbox: true,
                rowDblClick: function (event, ui) {
                    var rowData = ui.rowData;
                    openUpdate(rowData);
                },
                pageModel: {type: 'remote',rPP:200,rPPOptions: [50,100,200,500]},
                columns: columns,
                toolbar: {
                    items: [
                        { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                        { type: 'button', label: '引入', listeners: [{ click: introduce }], icon: 'add' },
                        { type: 'button', label: '关闭', listeners: [{ click: close }], icon: 'close' },
                    ]
                }
            };
            grid = $("#mainGrid").etGrid(paramObj);
        };

        $(function () {
            initFrom();
            initTree();
            initGrid();

            // 给输入框绑定搜索树事件
            $(".text-input").on('keyup', function () {
                var $self = $(this)
                searchTree({
                    tree: tree,
                    value: $self.val(),
                    callback: function () {
                        $self.focus();
                    }
                })
            })
        })
    </script>
</head>

<body>
    <div class="container">
        <div class="left border-right">
            <div class="search-form">
                <label>快速定位</label>
                <input class="text-input" type="text">
            </div>
            <div id="mainTree"></div>
        </div>
        <div class="center">
            <table class="table-layout">
                <tr>
                    <td class="label">职工工号：</td>
                    <td class="ipt">
                        <input id="emp_code" type="text" />
                    </td>
                    <td class="label">职工姓名：</td>
                    <td class="ipt">
                        <input id="emp_name" type="text" />
                    </td>
                </tr>
                <tr>
                    <td class="label">职务：</td>
                    <td class="ipt">
                        <select id="duty_code" style="width:180px;"></select>
                    </td>
                    <td class="label">职称：</td>
                    <td class="ipt">
                        <select id="title_code" style="width:180px;"></select>
                    </td>
                    <td class="label">护理等级：</td>
                    <td class="ipt">
                        <select id="level_code" style="width:180px;"></select>
                    </td>
                </tr>
            </table>
            <div id="mainGrid"></div>
        </div>
    </div>
</body>

</html>