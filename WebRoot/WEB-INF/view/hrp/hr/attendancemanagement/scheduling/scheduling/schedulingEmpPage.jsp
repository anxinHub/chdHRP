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
        var dept_name, duty, title, dict_level,attend_areacode,yh_code;
        var tree, grid;
        var parentFrameName = parent.$.etDialog.getFrameName('add');
        var parentWindow = parent.window[parentFrameName];
        
        var initFrom = function () {
       
        	 attend_areacode = $("#attend_areacode").etSelect({
                 url: "queryAttendAreacode.do?isCheck=false",
                 defaultValue: "${attend_areacode}",
                 onInit: function (value) {
                	 initTree(value);
                 },
             	onChange : function(value) {
             		initTree(value);
     			}
             });
        	
        	
            duty_code = $("#duty_code").etSelect({
                url: "../../queryDuty.do?isCheck=false",
                defaultValue: "none",
                onChange: query,
            });
            title_code = $("#title_code").etSelect({
                url: "../../queryHrTitle.do?isCheck=false",
                defaultValue: "none",
                onChange: query,
            });
            level_code = $("#level_code").etSelect({
                url: "../../queryDicLevel.do?isCheck=false",
                defaultValue: "none",
                onChange: query,
            });
            
            //医护属性下拉加载
            yh_code = $("#yh_code").etSelect({
                url:"../../queryAttendFieldOption.do?isCheck=false",
                defaultValue: "none",
                onInit: function (value) {
    			},
    			onChange: function (value) {
    			}
            });

            
        };
        var initTree = function (value) {
            tree = $("#mainTree").etTree({
                async: {
                   enable: true,
                   url: 'queryDeptTreeByArea.do?isCheck=false&attend_areacode='+value,
                },
                callback: {
                    onClick: function (event, treId, treeNode) {
                        query(treeNode.id);
                    },
                    onAsyncSuccess: function (event, treeId, treeNode, msg) {
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
            params.push( { name: 'yh_code', value: yh_code.getValue() });
            params.push( { name: 'attend_areacode', value: attend_areacode.getValue() });
            
            grid.loadData(params,'queryEmpByArea.do?isCheck=false');
        };
        var close = function () {
            var curIndex = parent.$.etDialog.getFrameIndex(window.name);
            parent.$.etDialog.close(curIndex);
        };
        var introduce = function () {
        	var params = [];
            var selectedData = grid.selectGet();

            if (selectedData.length === 0) {
                $.etDialog.error('请选择行');
                return;
            }
            
            var parentGridData = parentWindow.leftGrid.getAllData();
            selectedData.forEach(function (item,index){
            	var isRepeat=false;
             	if (parentGridData) {
            		isRepeat = parentGridData.some(function (element) {
                        return element.emp_id == item.rowData.emp_id;
                    })
                } 
            	
            	if (!isRepeat) {
            		parentWindow.leftGrid.addRow({
                		emp_id : item.rowData.emp_id,
                		emp_name : item.rowData.emp_name,
                		dept_id : item.rowData.dept_id,
                		dept_name : item.rowData.dept_name
                	});
            		
                }
            });
           
            $.etDialog.success('添加成功，保存后生效');
            parentWindow.changeLeftGridColumns();
          //  close();
        };
        
        var initGrid = function () {
            var columns = [
                { display: '职工ID', name: 'emp_id', width: 120,hidden: true },
                { display: '职工工号', name: 'emp_code', width: 120 },
                { display: '职工姓名', name: 'emp_name', width: 120 },
                { display: '科室名称', name: 'dept_name', width: 120 },
                { display: '职务', name: 'duty_name', width: 120 },
                { display: '职称', name: 'title_name', width: 120 },
                { display: '护理等级', name: 'level_name', width: 120 },
                { display: '医护属性', name: 'yh_name', width: 120 }
            ];
            var paramObj = {
                height: '100%',
                inWindowHeight: true,
                checkbox: true,
                rowDblClick: function (event, ui) {
                    var rowData = ui.rowData;
                    openUpdate(rowData);
                },
                pageModel: {type: 'remote',rPP:100},
                columns: columns,
                toolbar: {
                    items: [
                        { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                        { type: 'button', label: '添加', listeners: [{ click: introduce }], icon: 'add' },
                        { type: 'button', label: '关闭', listeners: [{ click: close }], icon: 'close' },
                    ]
                }
            };
            grid = $("#mainGrid").etGrid(paramObj);
        };

        $(function () {
            initFrom();
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
           		<label>区域名称：</label>
		        <select id="attend_areacode" style="width: 140px;"></select>
            </div>
            <div class="search-form">
                <label>快速定位：</label>
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
                     <td class="label">职务：</td>
                    <td class="ipt">
                        <select id="duty_code" style="width:180px;"></select>
                    </td>
                </tr>
                <tr>
                    <td class="label">职称：</td>
                    <td class="ipt">
                        <select id="title_code" style="width:180px;"></select>
                    </td>
                    <td class="label">护理等级：</td>
                    <td class="ipt">
                        <select id="level_code" style="width:180px;"></select>
                    </td>
					<td class="label ">医护属性：</td>
					<td class="ipt"><select id="yh_code" style="width: 180px;"></select></td>
                </tr>
            </table>
            <div id="mainGrid"></div>
        </div>
    </div>
</body>

</html>