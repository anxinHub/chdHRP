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
       
        	
        	
        /* 	item_code = $("#item_code").etSelect({
                url: '../budgsysset/budgsysset/queryBudgModSelect.do?isCheck=false&plan_code='+'${plan_code}',
                defaultValue: "none",
                onChange: query,
            }); */

            
        };
   
        var query = function (treeId) {
        
            var params = [];
         
            params.push( { name: 'item_code', value: $("#item_code").val()});
            params.push( { name: 'plan_code', value:'${plan_code}'});
            
            grid.loadData(params,'../budgsysset/budgsysset/queryItemCode.do?isCheck=false');
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
            
         /*    var parentGridData = parentWindow.child_grid.getAllData();
            selectedData.forEach(function (item,index){
            	var isRepeat=false;
             	if (parentGridData) {
            		isRepeat = parentGridData.some(function (element) {
                        return element.item_code == item.rowData.item_code;
                    })
                } 
            	
            	if (!isRepeat) {
            		parentWindow.child_grid.addRow({
            			item_code : item.rowData.item_code,
            			item_name : item.rowData.item_name
                	});
            		
                }
            }); */
            var param = [];
            var code;
            selectedData.forEach(function (item) {
           	 if(item.rowData.state == 1){//验证行数据已经提交
           		 code = true;
   				 return;
           	 }
                param.push({
               	 budg_year : '${budg_year}',
               	 plan_code : '${plan_code}',
               	 mod_code : '${mod_code}',
               	 link_code : '${link_code}',
               	 item_code : item.rowData.item_code,
               	 item_name : item.rowData.item_name,
               	 cont_l : item.rowData.cont_l,
               	 cont_p : item.rowData.cont_p,
               	 cont_w : item.rowData.cont_w,
               
                });
            });
          
            
            ajaxPostData({
                url: 'budgsysset/updateDetailBudgSysSetControl.do',
                data: { paramVo: JSON.stringify(param) },
                success: function (responseData) {
            		var parentFrameName = parent.$.etDialog.parentFrameName;
        			
        			var parentWindow = parent.window[parentFrameName];
        			parentWindow.query();
               },
           })
          
        };
        
        var initGrid = function () {
            var columns = [
                           { display: '预算项编码', name: 'item_code', width: 120,editable:false}, 
                           { display: '预算项名称', name: 'item_name', width: 120,editable:false}
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

          query();
        })
    </script>
</head>

<body>
    <div class="container">
       
        <div class="center">
            <table class="table-layout">
                <tr>
                   
                    <td class="label">预算项名称：</td>
                    <td class="ipt">
                    <input name="item_code" type="text" id="item_code" />
                    </td>
                  
                </tr>
               
            </table>
            <div id="mainGrid"></div>
        </div>
    </div>
</body>

</html>