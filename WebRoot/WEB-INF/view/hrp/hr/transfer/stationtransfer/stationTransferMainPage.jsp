<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="datepicker,grid,select,dialog" name="plugins" />
	</jsp:include>
    <script>
        var move_date, dept, staff, member_type, duty, post, postion, post_level, salary_scale, status, operator, auditor;
        var grid;
        var initFrom = function () {
            // 调动日期
            move_date = $("#move_date").etDatepicker({
                range: true
            });
            // 部门
            dept = $("#dept").etSelect({
            	  url: "../../queryHosDeptSelect.do?isCheck=false",
                defaultValue: "none"
            });
            // 职工
            staff = $("#staff").etSelect({
            	   url: "../../queryEmp.do?isCheck=false",
                defaultValue: "none"
            });
            // 人员类别
            member_type = $("#member_type").etSelect({
            	 url: "../../queryEmpType.do?isCheck=false",
                defaultValue: "none"
            });
            // 职务
            duty = $("#duty").etSelect({
            	   url: "../../queryDuty.do?isCheck=false",
                defaultValue: "none"
            });
            // 职位
            post = $("#post").etSelect({
                url: "http://118.178.184.131:9090/select",
                defaultValue: "none"
            });
            // 岗位
            postion = $("#postion").etSelect({
            	 url: "../../queryStation.do?isCheck=false",
                defaultValue: "none"
            });
            // 岗级
            post_level = $("#post_level").etSelect({
                url: "http://118.178.184.131:9090/select",
                defaultValue: "none"
            });
            // 薪级
            salary_scale = $("#salary_scale").etSelect({
                url: "http://118.178.184.131:9090/select",
                defaultValue: "none"
            });
            // 状态
            status = $("#status").etSelect({
                url: "http://118.178.184.131:9090/select",
                defaultValue: "none"
            });
            // 操作人
            operator = $("#operator").etSelect({
         	   url: "queryEmp.do?isCheck=false",
                defaultValue: "none"
            });
            // 审核人
            auditor = $("#auditor").etSelect({
         	   url: "queryEmp.do?isCheck=false",
                defaultValue: "none"
            });
        };
        var query = function () {
            params = [
                { name: '', value: '' }
            ];
            grid.loadData(params);
        };
        var add = function () {
            parent.$.etDialog.open({
                url: 'hrp/hr/transfer/stationtransfer/addHrStationTransferPage.do?isCheck=false',
                isMax: true,
                title: '添加'
            });
        };
        var remove = function () {
            var selectData = grid.selectGet();
            if (selectData.length === 0) {
                $.etDialog.error('请选择行');
                return;
            }
            var param = [];
            selectData.forEach(function (item) {
                param.push({
                    kind_code: item.rowData.kind_code
                });
            })

            ajaxPostData({
                // url: '.do?isCheck=false',
                url: 'http://118.178.184.131:9090/delete?id=1',
                data: { paramVo: JSON.stringify(param) },
                success: function () {
                    grid.deleteRows(selectData);
                }
            })
        };
        var audit = function () {};
        var unAudit = function () {};
        var print = function () {};
        var openUpdate = function (rowData) {
            parent.$.etDialog.open({
                url: 'hrp/hr/transfer/stationtransfer/updateHrStationTransferPage.do?isCheck=false',
                title: '修改',
                isMax: true
            })
        };
        var initGrid = function () {
            var columns = [
                { display: '调动号', name: 'number1', width: 120,
                    render: function (ui) {
                        var updateHtml =
                            '<a class="openUpdate" row-index="' + ui.rowIndx + '">' +
                                ui.cellData +
                            '</a>'
                        return updateHtml;
                    }
                },
                { display: '调动日期', name: 'date1', width: 120 },
                { display: '职工编码', name: 'c', width: 120 },
                { display: '职工名称', name: 'name1', width: 120 },
                { display: '部门', name: 'e', width: 120 },
                { display: '人员类别', name: 'f', width: 120 },
                { display: '调整前',
                    columns: [
                        { display: '原职务', name: 'g', width: 120 },
                        { display: '原职位', name: 'h', width: 120 },
                        { display: '原岗位', name: 'i', width: 120 },
                        { display: '原岗级', name: 'j', width: 120 },
                        { display: '原薪级', name: 'k', width: 120 }
                    ]
                },
                { display: '调整后',
                    columns: [
                        { display: '职务', name: 'l', width: 120 },
                        { display: '职位', name: 'm', width: 120 },
                        { display: '岗位', name: 'n', width: 120 },
                        { display: '岗级', name: 'o', width: 120 },
                        { display: '薪级', name: 'p', width: 120 }
                    ]
                },
                { display: '状态', name: 'q', width: 120 },
                { display: '操作人', name: 'b', width: 120 },
                { display: '操作时间', name: 'date2', width: 120 },
                { display: '审核人', name: 'd', width: 120 },
                { display: '审核时间', name: 'date3', width: 120 }
            ];
            var paramObj = {
                height: '100%',
                inWindowHeight: true,           
                checkbox: true,
                rowDblClick: function (event, ui) {
                    var rowData = ui.rowData;
                    openUpdate(rowData);
                },
                dataModel: {
                    url: 'http://118.178.184.131:9090/static_column/grid'
                },
                columns: columns,
                toolbar: {
                    items: [
                        { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                        { type: 'button', label: '添加', listeners: [{ click: add }], icon: 'add' },
                        { type: 'button', label: '删除', listeners: [{ click: remove }], icon: 'delete' },
                        { type: 'button', label: '审核', listeners: [{ click: audit }], icon: 'audit' },
                        { type: 'button', label: '销审', listeners: [{ click: unAudit }], icon: 'cancel' },
                        { type: 'button', label: '打印', listeners: [{ click: print }], icon: 'print' }
                    ]
                }
            };
            grid = $("#mainGrid").etGrid(paramObj);

            $("#mainGrid").on('click', '.openUpdate', function () {
                var rowIndex = $(this).attr('row-index');
                var currentRowData = grid.getAllData()[rowIndex];
                openUpdate(currentRowData);
            })
        };

        $(function () {
            initFrom();
            initGrid();
        })
    </script>
</head>

<body>
    <table class="table-layout">
        <tr>
            <td class="label">调动日期：</td>
            <td class="ipt">
                <input id="move_date" type="text" />
            </td>

            <td class="label">调动号：</td>
            <td class="ipt">
                <input id="move_num" type="text" />
            </td>

            <td class="label">部门：</td>
            <td class="ipt">
                <select id="dept" style="width:180px;"></select>
            </td>

            <td class="label">职工：</td>
            <td class="ipt">
                <select id="staff" style="width:180px;"></select>
            </td>
        </tr>
        <tr>
            <td class="label">人员类别：</td>
            <td class="ipt">
                <select id="member_type" style="width:180px;"></select>
            </td>

            <td class="label">职务：</td>
            <td class="ipt">
                <select id="duty" style="width:180px;"></select>
            </td>

            <td class="label">职位：</td>
            <td class="ipt">
                <select id="post" style="width:180px;"></select>
            </td>

            <td class="label">岗位：</td>
            <td class="ipt">
                <select id="postion" style="width:180px;"></select>
            </td>
        </tr>
        <tr>
            <td class="label">岗级：</td>
            <td class="ipt">
                <select id="post_level" style="width:180px;"></select>
            </td>
            
            <td class="label">薪级：</td>
            <td class="ipt">
                <select id="salary_scale" style="width:180px;"></select>
            </td>

            <td class="label">状态：</td>
            <td class="ipt">
                <select id="status" style="width:180px;"></select>
            </td>

            <td class="label">操作人：</td>
            <td class="ipt">
                <select id="operator" style="width:180px;"></select>
            </td>
        </tr>
        <tr>
            <td class="label">审核人：</td>
            <td class="ipt">
                <select id="auditor" style="width:180px;"></select>
            </td>
        </tr>
    </table>
    <div id="mainGrid"></div>
</body>

</html>
