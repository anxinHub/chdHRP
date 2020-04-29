<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="hr,dialog,select,grid,datepicker" name="plugins" />
	</jsp:include>
    <script>
        var dept_code, diagnose, is_same;
        var grid;
        var initFrom = function () {

            dept_code = $("#state").etSelect({
            	options: [
      		            { id: 1, text: '启用' },
      		            { id: 2, text: '停用' },
      		        ],
                defaultValue: "none",
                onChange: query
            });
        };
        var query = function () {
            params = [
            	{ name: 'stan_name', value: $('#stan_name').val() },
            	{ name: 'state', value: dept_code.getValue() },
            ];
            grid.loadData(params,'queryStandard.do');
        };
        var add = function () {
            parent.$.etDialog.open({
                url: 'hrp/hr/salarymanagement/standard/standardAddPage.do?isCheck=false',
                width: $(window).width(),
                height: $(window).height(),
                frameName :window.name,
                title: '薪资标准添加'
            });
        };

		var update = function (id) {
            parent.$.etDialog.open({
                url: 'hrp/hr/salarymanagement/standard/standardUpdatePage.do?isCheck=false&id='+id,
                width: $(window).width(),
                height: $(window).height(),
                frameName :window.name,
                title: '薪资标准修改'
            });
		}

		var maintain = function (id) {
            parent.$.etDialog.open({
                url: 'hrp/hr/salarymanagement/standard/standardMaintainPage.do?isCheck=false&id='+id,
                width: $(window).width(),
                height: $(window).height(),
                frameName :window.name,
                title: '薪资标准维护'
            });
		}
        
        var remove = function () {
            var selectData = grid.selectGet();
            if (selectData.length === 0) {
                $.etDialog.error('请选择行');
                return;
            }
            var param = [];
            selectData.forEach(function (item) {
                param.push(
                		item.rowData.STAN_ID
                );
            })
			$.etDialog.confirm('确定删除?', function () {
	            ajaxPostData({
	               url: 'deleteStandard.do',
	                data: { 'arrid': JSON.stringify(param) },
	                success: function () {
	                    grid.deleteRows(selectData);
	                }
	            })
			});
        };

		var copy = function () {

			var arr = grid.selectGet();

			if(arr.length == 0){
				$.etDialog.error("请选择你要复制的数据(单选)!");
				return false;
			}else if(arr.length  > 1){
				$.etDialog.error("请选择单条数据!");
				return false;
			}else if(arr[0].rowData.STATE == 1){
				$.etDialog.error("存在已启用的薪资标准表编码或名称!");
				return false;
			}
            ajaxPostData({
	               url: 'addCopyStandard.do',
	                data: { 'id': arr[0].rowData.STAN_ID },
	                success: function (responsejson) {
	                    query();
	                }
	        })
		}
        
        var initGrid = function () {
            var yearEditor = getRecentYearForSelect();
            var columns = [
                { display: '标准编码', name: 'STAN_CODE', width:100,
                	render : function(row){
                    	return "<a href='javascript:update("+row.rowData.STAN_ID+")'>"+row.rowData.STAN_CODE+"</a>";
                    }
                },
                { display: '标准表名称', name: 'STAN_NAME', width: 100 },
                { display: '人员限定条件', name: 'COND', width: 200 },
                { display: '启动日期', name: 'START_DATE', width: 120 },
                { display: '状态', name: 'STATE', width: 120,
                	render : function(row){
                    	if(row.rowData.STATE == 1){
                            return "启用";
                        }else if(row.rowData.STATE == 2){
                            return "停用";
                        }
                    }
                },
                { display: '维护', name: 'STAN_ID', width: 120,
                	render : function(row){
                    	return "<a href='javascript:maintain("+row.rowData.STAN_ID+")'>维护</a>";
                    }
                },
            ];
            var paramObj = {
                height: '100%',
                inWindowHeight: true,
                checkbox: true,
                editable: false,
                rowDblClick: function (event, ui) {
                	update(ui.rowData.STAN_ID)
                },
               /*  dataModel: {
                   url: ''
                }, */
                columns: columns,
                toolbar: {
                    items: [
                        { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                        { type: 'button', label: '添加', listeners: [{ click: add }], icon: 'add' },
                        { type: 'button', label: '删除', listeners: [{ click: remove }], icon: 'delete' },
                        { type: 'button', label: '复制', listeners: [{ click: copy }], icon: 'print' }
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
    <div class="main">
        <table class="table-layout">
            <tr>
               <td class="label">薪资标准表：</td>
                <td class="ipt">
                    <input id="stan_name" type="text" />
                </td>
                <td class="label">状态：</td>
                <td class="ipt">
                    <select id="state" style="width:180px;"></select>
                </td>
            </tr>
        </table>
    </div>
    <div id="mainGrid"></div>
</body>

</html>