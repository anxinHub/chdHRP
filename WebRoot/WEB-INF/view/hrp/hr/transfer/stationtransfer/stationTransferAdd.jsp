<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
        <jsp:param value="hr,tree,datepicker,select,grid,upload,dialog" name="plugins" />
    </jsp:include>
    <script>
        var move_name, staff_name, dept, staff_type, photo_file;
        var duty, postion, post, post_level, salary_scale;
        var tree, grid;
        var initFrom = function () {
            // 调动日期
            move_name = $("#move_name").etDatepicker();
            // 职工名称
            staff_name = $("#staff_name").etSelect({
                url: "../../queryPerson.do?isCheck=false",
                defaultValue: "none",
                onChange: function (value) {
                    if (value) {
                        ajaxPostData({
                            url: '../../queryPersonnel.do?isCheck=false&emp_id=' + value,
                            data: {},
                            success: function (res) {
                                if (photo_file) {
                                    photo_file.setValue(res.photo)
                                }
                            }
                        })
                    } else {
                        photo_file.setValue('')
                    }
                }
                /*  defaultValue: "none",
                 onChange: function (value) {
                     emp_id.reload({
                         url: '../../queryPersonnel.do?isCheck=false',
                         para: {
                             emp_id: value,
                         },
                     })
                 } */
            });
            // 部门
            dept = $("#dept").etSelect({
                url: "../../queryHosDeptSelect.do?isCheck=false",
                defaultValue: "none"
            });
            // 职工类别
            staff_type = $("#staff_type").etSelect({
                url: "../../queryEmpType.do?isCheck=false",
                defaultValue: "none"
            });
            // 照片上传
            photo_file = $("#upload_photo").etUpload();

            // 调整后
            // 职务
            duty = $("#duty").etSelect({
                url: "../../queryDuty.do?isCheck=false",
                defaultValue: "none"
            });
            // 职位
            postion = $("#postion").etSelect({
                url: "../../queryDuty.do?isCheck=false",
                defaultValue: "none"
            });
            // 岗位
            post = $("#post").etSelect({
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

            $("#saveForm").click(function () {

            });
            $("#close").click(function () {
                var curIndex = parent.$.etDialog.getFrameIndex(window.name);
                parent.$.etDialog.close(curIndex);
            });
        };
        var initTree = function () {
            tree = $("#mainTree").etTree({
                async: {
                    enable: true,
                    url: 'http://118.178.184.131:9090/tree'
                }
            })
        };

        var saveGrid = function () { };
        var remove = function () {
            var selectData = grid.selectGet();
            var param = [];
            selectData.forEach(function (item) {
                param.push({
                    kind_code: item.rowData.kind_code
                });
            })

            ajaxPostdata({
                url: '.do?isCheck=false',
                data: { paramVo: JSON.stringify(param) },
                success: function () {
                    query();
                }
            })
        };
        var putInPost = function () { };
        var putInWage = function () { };
        var putInExecel = function () { };
        var putOut = function () { };
        var print = function () { };
        var initGrid = function () {
            var columns = [
                { display: '工资套', name: 'a', width: 120 },
                { display: '工资项编码', name: 'b', width: 120 },
                { display: '工资项名称', name: 'c', width: 120 },
                { display: '调整前工资额', name: 'd', width: 120 },
                { display: '工资额', name: 'e', width: 120 },
                { display: '备注', name: 'f', width: 140 }
            ];
            var paramObj = {
                height: '100%',
                inWindowHeight: true,
                checkbox: true,
                title: '核定薪资',
                showTitle: true,
                dataModel: {
                    url: 'http://118.178.184.131:9090/static_column/grid'
                },
                columns: columns,
                toolbar: {
                    items: [
                        { type: 'button', label: '保存', listeners: [{ click: saveGrid }], icon: 'save' },
                        { type: 'button', label: '删除', listeners: [{ click: remove }], icon: 'delete' },
                        { type: 'button', label: '导入岗位薪资', listeners: [{ click: putInPost }], icon: 'import' },
                        { type: 'button', label: '导入工资标准', listeners: [{ click: putInWage }], icon: 'import' },
                        { type: 'button', label: 'EXECL导入', listeners: [{ click: putInExecel }], icon: 'import' },
                        { type: 'button', label: '导出', listeners: [{ click: putOut }], icon: 'export' },
                        { type: 'button', label: '打印', listeners: [{ click: print }], icon: 'print' }
                    ]
                }
            };
            grid = $("#mainGrid").etGrid(paramObj);
        };

        $(function () {
            initFrom();
            initTree();
            initGrid();
        })
    </script>
</head>

<body style="overflow-x:hidden;">
    <div class="container">
        <div class="left border-right">
            <div id="mainTree"></div>
        </div>
        <div class="center">
            <div class="flex-wrap">
                <table class="flex-item-1 table-layout">
                    <tr>
                        <td class="label">调动编号：</td>
                        <td class="ipt">
                            <input id="move_code" type="text" />
                        </td>
                        <td class="label">调动日期：</td>
                        <td class="ipt">
                            <input id="move_name" type="text" />
                        </td>
                        <td class="label">职工名称：</td>
                        <td class="ipt">
                            <select id="staff_name" style="width:180px;"></select>
                        </td>
                    </tr>
                    <tr>
                        <td class="label">部门：</td>
                        <td class="ipt">
                            <select id="dept" style="width:180px;"></select>
                        </td>
                        <td class="label">职工类别：</td>
                        <td class="ipt">
                            <select id="staff_type" style="width:180px;"></select>
                        </td>
                    </tr>
                    <tr>
                        <td class="label">调动原因：</td>
                        <td class="ipt" colspan="3">
                            <textarea id="move_reason" cols="30" rows="10"></textarea>
                        </td>
                    </tr>
                </table>
                <div class="upload-photo-form">
                    <div id="upload_photo"></div>
                    <span>照片</span>
                </div>
            </div>
            <div class="flex-wrap">
                <div class="flex-item-1">
                    <div class="title">
                        <<调整前>></div>
                    <table class="single-block table-layout">
                        <tr>
                            <td class="label">职务：</td>
                            <td class="ipt">
                                <input id="prev_duty" type="text" disabled value="${prev_duty}" />
                            </td>
                            <td class="label">职位：</td>
                            <td class="ipt">
                                <input id="prev_postion" type="text" disabled value="${prev_postion}" />
                            </td>
                        </tr>
                        <tr>
                            <td class="label">岗位：</td>
                            <td class="ipt">
                                <input id="prev_post" type="text" disabled value="${prev_post}" />
                            </td>
                            <td class="label">岗级：</td>
                            <td class="ipt">
                                <input id="prev_post_level" type="text" disabled value="${prev_post_level}" />
                            </td>
                        </tr>
                        <tr>
                            <td class="label">薪级：</td>
                            <td class="ipt">
                                <input id="prev_salary_scale" type="text" disabled value="${prev_salary_scale}" />
                            </td>
                        </tr>
                    </table>
                </div>
                <div class="flex-item-1">
                    <div class="title">
                        <<调整后>></div>
                    <table class="single-block table-layout">
                        <tr>
                            <td class="label">职务：</td>
                            <td class="ipt">
                                <select id="duty" style="width: 180px;"></select>
                            </td>
                            <td class="label">职位：</td>
                            <td class="ipt">
                                <select id="postion" style="width: 180px;"></select>
                            </td>
                        </tr>
                        <tr>
                            <td class="label">岗位：</td>
                            <td class="ipt">
                                <select id="post" style="width: 180px;"></select>
                            </td>
                            <td class="label">岗级：</td>
                            <td class="ipt">
                                <select id="post_level" style="width: 180px;"></select>
                            </td>
                        </tr>
                        <tr>
                            <td class="label">薪级：</td>
                            <td class="ipt">
                                <select id="salary_scale" style="width: 180px;"></select>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
            <div class="button-group">
                <button id="saveForm">保存</button>
                <button id="close">关闭</button>
            </div>
            <div id="mainGrid"></div>
        </div>
    </div>
</body>

</html>