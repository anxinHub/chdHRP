<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
            <jsp:include page="${path}/resource.jsp">
                <jsp:param value="hr,dialog,grid,select,tree,datepicker,pageOffice" name="plugins" />
            </jsp:include>
		<script type="text/javascript">
		var grid, tree,store_type_code,selectId;
		
		$(function () {
			
            loadGrid();
			query();
			
        })
		
		function loadGrid() {
            var gridObj = {
                editable: true,
                checkbox: true,
                height: '100%',
                inWindowHeight: true,
                usePager: false,
                addRowByKey: true //  快捷键控制添加行
            };
            gridObj.numberCell = {
                title: '#'
            };
            gridObj.columns = [{
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
            grid = $("#maingrid").etGrid(gridObj);
        }
		
        function query() {
        	
        	var curIndex = parent.$.etDialog.getFrameIndex(window.name);
        	var parentFrameName = parent.$.etDialog.parentFrameName;
        	var parentWindow = parent.window[parentFrameName];
			tree = parentWindow.tree;
        	
            var selected = tree.getSelectedNodes()[0];
            if (selected) {
                selectId = selected.id;
            }
            
            var parms = [{
                name: 'store_type_code',
                value: selectId
            },{
                name: 'tab_code',
                value: $("#tab_code").val()
            },
            ];
            
            grid.loadData(parms, '../sysstruc/queryHrHosTabStruc.do?isCheck=false');

        }
        
        function save(){
        	var data = grid.selectGet();
        	var arrid = [];
        	$.each(data,function(){
				arrid.push(this.rowData.tab_code);
        	})
        	if(arrid.length == 0){
            	var curIndex = parent.$.etDialog.getFrameIndex(window.name);
            	parent.$.etDialog.close(curIndex);
            	return false;
        	}
                            ajaxPostData({
                                url: "addStoreTabBatch.do",
                                data: {
                                    "arrid": JSON.stringify(arrid),
                                    "store_type_code":selectId
                                },
                                success: function (res) {
                                    if (res.state == "true") {
                                    	var parentFrameName = parent.$.etDialog.parentFrameName;
                                    	var parentWindow = parent.window[parentFrameName];
                                    	parentWindow.query();
                                    	var curIndex = parent.$.etDialog.getFrameIndex(window.name);
                                    	parent.$.etDialog.close(curIndex);
                                    }
                                }
                            })
        }
        
		</script>
</head>
<body>
            <div class="center">
                    <table class="table-layout">
                        <tr>
                            <td class="label">数据表名称：</td>
                            <td class="ipt">
                                <input id="tab_code" type="text" />
                            </td>
                            <td class="label"><button onclick="query();">查询</button></td>
                        </tr>
                    </table>

                    <div id="maingrid"></div>
             </div>
</body>
</html>