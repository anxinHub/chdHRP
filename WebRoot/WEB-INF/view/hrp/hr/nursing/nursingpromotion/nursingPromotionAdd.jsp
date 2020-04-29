<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="dialog, select, datepicker, validate, grid" name="plugins" />
	</jsp:include>
    <script>
        var year, dept_code, user_code, apply_level_code, apply_date;
        var user_hide_data = {};
        var formValidate;

        var initValidate = function () {
            formValidate = $.etValidate({
                items: [
                    { el: $("#year"), required: true },
                    { el: $("#dept_code"), required: true },
                    { el: $("#user_code"), required: true },
                    { el: $("#apply_level_code"), required: true },
                    { el: $("#apply_date"), required: true }
                ]
            });
        };
        var close = function () {
            var curIndex = parent.$.etDialog.getFrameIndex(window.name);
            parent.$.etDialog.close(curIndex);
        };
        var save = function () {
            if (!formValidate.test()) {
                return;
            }
            var applyDate = moment(apply_date.getValue());
            var nowDate = moment();
            if(applyDate > nowDate){
            	$.etDialog.error("申请日期不能大于当天");
            	return;
            }
            var data = {
                // 表单 填写的
                year:"${year}",
                dept_code: dept_code.getValue(),
                user_code: user_code.getValue(),
                apply_level_code: apply_level_code.getValue(),
                apply_date: apply_date.getValue(),
                // 表单 带出来的
                emp_id: user_hide_data.emp_id,
                degree_code: user_hide_data.degree_code,
                graduation_date: user_hide_data.graduation_date,
                birthday: user_hide_data.birthday,
                cur_get_date: user_hide_data.cur_get_date,
                cur_level_code: user_hide_data.cur_level_code,
                cur_title_code: user_hide_data.cur_title_code,
                cur_title_date: user_hide_data.cur_title_date,
                field_col_code: user_hide_data.field_col_code,
                max_degree_code: user_hide_data.max_degree_code,
                max_graduation_date: user_hide_data.max_graduation_date,
                worktime: user_hide_data.worktime,
                // 表格
                Param: JSON.stringify(grid.getAllData()),
            };
            ajaxPostData({
               url: 'addNursingPromotion.do',
                data: data,
                success: function () {
                    var parentFrameName = parent.$.etDialog.parentFrameName;
                    var parentWindow = parent.window[parentFrameName];
                    parentWindow.query();
                    close();
                },
                delayCallback: true
            })
        };
        var print = function () {};
        

        var initForm = function () {
          /*   year = $("#year").etDatepicker({
                view: "years",
                minView: "years",
                dateFormat: "yyyy",
                // minDate: data[data.length - 1].text,
                // maxDate: data[0].text,
            }); */
            dept_code = $("#dept_code").etSelect({
                url: "../queryHosDeptSelect.do?isCheck=false",
                defaultValue: "none",
                onChange: function (value) {
                    user_code.reload({
                        url: '../queryEmpSelect.do?isCheck=false',
                        para: {
                            dept_code: value,
                        },
                    })
                }
            });

            user_code = $("#user_code").etSelect({
                url: "../queryEmpSelect.do?isCheck=false",
                defaultValue: "none",
                onChange: function (value) {
                    ajaxPostData({
                        url: 'queryHosEmpDetail.do?isCheck=false',
                        data: {
                            user_code: value
                        },
                        success: function (res) {
                            user_hide_data = {
                                emp_name: res.emp_name || '',
                                emp_id: res.emp_id || '',
                                degree_name: res.degree_name || '',
                                degree_code: res.degree_code || '',
                                graduation_date: res.graduation_date || '',
                                birthday: res.birthday || '',
                                cur_get_date: res.cur_get_date || '',
                                cur_level_code: res.cur_level_code || '',
                                cur_level_name: res.cur_level_name || '',
                                cur_title_code: res.cur_title_code || '',
                                cur_title_date: res.cur_title_date || '',
                                cur_title_name: res.cur_title_name || '',
                                field_col_code: res.field_col_code || '',
                                max_degree_code: res.max_degree_code || '',
                                max_degree_name: res.max_degree_name || '',
                                max_graduation_date: res.max_graduation_date || '',
                                worktime: res.worktime || '',
                            };
                            $("#emp_name").val(res.emp_name || '');
                            $("#degree_name").val(res.degree_name || '');
                            $("#graduation_date").val(res.graduation_date || '');
                            $("#birthday").val(res.birthday || '');
                            $("#max_degree_name").val(res.max_degree_name || '');
                            $("#max_graduation_date").val(res.max_graduation_date || '');
                            $("#cur_level_name").val(res.cur_level_name || '');
                            $("#cur_get_date").val(res.cur_get_date || '');
                            $("#cur_title_name").val(res.cur_title_name || '');
                            $("#cur_title_date").val(res.cur_title_date || '');
                            $("#worktime").val(res.worktime || '');
                        },
                    })
                    query();
                }
            });
            apply_level_code = $("#apply_level_code").etSelect({
                url: "queryLevel.do?isCheck=false",
                defaultValue: "none"
            });
            apply_date = $("#apply_date").etDatepicker();

            $("#save").on('click', save);
            $("#print").on('click', print);
            $("#close").on('click', close);
        };
        var query = function () {
            params = [
                { name: 'year', value: year.getValue() },
                { name: 'dept_code', value: dept_code.getValue() },
                { name: 'user_code', value: user_code.getValue() },
            ];
            // url: '.do?isCheck=false'
            grid.loadData(params, 'queryAttend.do?isCheck=false');
        };
        var initGrid = function () {
            var columns = [
                { display: '工作时间', name: 'work_year', width: 120 },
                { display: '病假时间（天）', name: 'sick', width: 120 },
                { display: '产假时间（天）', name: 'maternity', width: 120 },
                { display: '事假时间（天）', name: 'casual', width: 120 }
            ];
            var paramObj = {
                height: '100%',
                inWindowHeight: true,
                checkbox: true,
                title: '>>近三年资料：',
                showTitle: true,
                columns: columns,
            };
            grid = $("#mainGrid").etGrid(paramObj);
        };
        $(function () {
            initValidate();
            initForm();
            initGrid();
        })

        
    </script>
</head>

<body>
    <div class="main">
        <table class="table-layout" style="width: 100%;">
            <tr>
                <td class="label"><font size="2" color="red">*</font>年度：</td>
                <td class="ipt">
                    <input id="year" type="text"  value="${year}" disabled/>
                </td>
                <td class="label"><font size="2" color="red">*</font>科室：</td>
                <td class="ipt">
                    <select id="dept_code" style="width:180px;"></select>
                </td>
                <td class="label"><font size="2" color="red">*</font>职工：</td>
                <td class="ipt">
                    <select id="user_code" style="width:180px;"></select>
                </td>
            </tr>
            <tr>
                <td class="label">姓名：</td>
                <td class="ipt">
                    <input id="emp_name" type="text" disabled />
                </td>
                <td class="label">毕业学历：</td>
                <td class="ipt">
                    <input id="degree_name" type="text" disabled />
                </td>
                <td class="label">毕业时间：</td>
                <td class="ipt">
                    <input id="graduation_date" type="text" disabled />
                </td>
            </tr>
            <tr>
                <td class="label">出生年月：</td>
                <td class="ipt">
                    <input id="birthday" type="text" disabled />
                </td>
                <td class="label">最高学历：</td>
                <td class="ipt">
                    <input id="max_degree_name"  type="text" disabled />
                </td>
                <td class="label">毕业时间：</td>
                <td class="ipt">
                    <input id="max_graduation_date" type="text" disabled />
                </td>
            </tr>
            <tr>
                <td class="label">现有阶别：</td>
                <td class="ipt">
                    <input id="cur_level_name" type="text" disabled />
                </td>
                <td class="label">进阶时间：</td>
                <td class="ipt">
                    <input id="cur_get_date" type="text" disabled />
                </td>
            </tr>
            <tr>
                <td class="label">现有技术积称：</td>
                <td class="ipt">
                    <input id="cur_title_name" type="text" disabled />
                </td>
                <td class="label">职能晋升时间：</td>
                <td class="ipt">
                    <input id="cur_title_date" type="text" disabled />
                </td>
                <td class="label">参加工作时间：</td>
                <td class="ipt">
                    <input id="worktime" type="text" disabled />
                </td>
            </tr>
            <tr>
                <td class="label no-empty">晋升阶别：</td>
                <td class="ipt">
                    <select id="apply_level_code" style="width:180px;"></select>
                </td>
                <td class="label no-empty">申请时间：</td>
                <td class="ipt">
                    <input id="apply_date" type="text">
                </td>
            </tr>
        </table>
    </div>
    <div class="button-group">
        <button id="save">保存</button>
        <!-- <button id="print">打印</button> -->
        <button id="close">关闭</button>
    </div>
    <div id="mainGrid"></div>
</body>

</html>