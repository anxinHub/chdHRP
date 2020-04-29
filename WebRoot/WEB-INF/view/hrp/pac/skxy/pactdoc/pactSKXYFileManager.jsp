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
        var grid;
        var query = function () {
            params = [
                { name: 'type_name', value: $("#type_name").val() },
                { name: 'is_stop', value: $("#is_stop").val() },
            ];
            grid.loadData(params);
        };
        
        var initGrid = function () {
            var columns = [
            	 { display: '归档项目编码', name: 'type_code', align: 'center',width: '30%',editable: false},
                 { display: '归档项目名称', name: 'type_name', align: 'center' ,width: '40%',editable: false},
                 { display: '备注', name: 'note', align: 'center', width: '22%',editable: false}
            ];
            var paramObj = {
            	editable: true,
            	height: '100%',
            	width:'98%',
            	checkbox: true,
            	 usePager: false,
                dataModel: {
                    url: 'queryPactFileType.do?isCheck=false&pact_code=${pact_code}',
                },
                columns: columns,
                toolbar: {
                    items: [
                        { type: 'button', label: '保存', listeners: [{ click: save }], icon: 'search' },
                        /*{ type: 'button', label: '添加', listeners: [{ click: save }], icon: 'save' },
                        { type: 'button', label: '删除', listeners: [{ click: remove }], icon: 'del' } */
                        
                    ]
                }
            };
            grid = $("#mainGrid").etGrid(paramObj);
        };

        function save(){
        	var data = grid.selectGet();
            if (data.length == 0) {
                $.etDialog.error('请选择行');
            } else {
                var param = [];
                $(data).each(function () {
                    var rowdata = this.rowData;
                    rowdata.group_id = ${group_id};
                    rowdata.hos_id = ${hos_id};
                    rowdata.copy_code = '${copy_code}';
                    rowdata.pact_code = '${pact_code}';
                    rowdata.file_type = rowdata.type_code;
                    rowdata.sort_id=rowdata.type_code;
                    rowdata.note = "";
                    var parentFrameName = parent.$.etDialog.parentFrameName;
                    var parentWindow = parent.window[parentFrameName];
                    var data = parentWindow.grid.getAllData();
                    if (data) {
	                    for (var i = 0; i < data.length; i++) {
							if (data[i].type_code == rowdata.type_code) {
								return;
							}
						}
					}
                    
                    parentWindow.grid.addRow(rowdata);
	              	parentWindow.onAfterShowData(); 
                });
              	var curIndex = parent.$.etDialog.getFrameIndex(window.name);
             	parent.$.etDialog.close(curIndex); 
            }
        }
       
        $(function () {
            initGrid();
        })
    </script>
</head>

<body>
    <div id="mainGrid"></div>
</body>

</html>

