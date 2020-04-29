<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="dialog,datepicker,grid,validate,select" name="plugins" />
	</jsp:include>
    <script>
        var edu_date, teacher;
        var grid;
        var formValidate;

        var initValidate = function () {
            formValidate = $.etValidate({
                items: [
                    { el: $("#edu_date"), required: true },
                    { el: $("#class_name"), required: true },
                    { el: $("#teacher"), required: true },
                    { el: $("#hours"), required: true },
                    { el: $("#place"), required: true }
                ]
            });
        };
        var initFrom = function () {
            edu_date = $("#edu_date").etDatepicker();
            teacher = $("#teacher").etSelect({
                url: "../queryEmpSelect.do?isCheck=false",
                defaultValue: "none",
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
            
            var hours = $("#hours").val();
            
            if(isNaN(hours) == true){
            	$.etDialog.error('培训学时只能是数字');
            	return ; 	
            }
            
            ajaxPostData({
                 url: 'addInserviceEducation.do',
                data: {
                    edu_date: edu_date.getValue(),
                    class_name: $("#class_name").val(),
                    teacher: teacher.getValue(),
                    hours: $("#hours").val(),
                    place: $("#place").val(),

                    Param: JSON.stringify(grid.getAllData()),
                },
                success: function () {
                    var parentFrameName = parent.$.etDialog.getFrameName('index');
                    var parentWindow = parent.window[parentFrameName];
                    parentWindow.query();
                    close();
                },
                delayCallback: true
            })
        };
        
        var add = function () {
            parent.$.etDialog.open({
                url: 'hrp/hr/nursing/addInserviceEducationPersonnelPage.do?isCheck=false',
                isMax: true,
                frameNameObj: { 'add': window.name },
                title: '添加人员'
            });
        };
        var remove = function () {
            var selectData = grid.selectGet();
            if (selectData.length === 0) {
                $.etDialog.error('请选择行');
                return;
            }
            grid.deleteRows(selectData);
        };
       /*  var putin = function () {};
        var putout = function () {}; */
        
        var initGrid = function () {
            var columns = [
                { display: '职工工号', name: 'emp_id', width: 120 },
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
                columns: columns,
                toolbar: {
                    items: [
                        { type: 'button', label: '保存', listeners: [{ click: save }], icon: 'save' },
                        { type: 'button', label: '添加', listeners: [{ click: add }], icon: 'add' },
                        { type: 'button', label: '删除', listeners: [{ click: remove }], icon: 'delete' },
                        /* { type: 'button', label: '导入', listeners: [{ click: putin }], icon: 'import' },
                        { type: 'button', label: '导出', listeners: [{ click: putout }], icon: 'export' }, */
                        { type: 'button', label: '关闭', listeners: [{ click: close }], icon: 'close' },
                    ]
                }
            };
            grid = $("#mainGrid").etGrid(paramObj);
        };

        $(function () {
            initValidate();
            initFrom();
            initGrid();
        })
    </script>
</head>

<body>
    <div class="main">
        <table class="table-layout">
            <tr>
                <td class="label"><font size="2" color="red">*</font>培训日期：</td>
                <td class="ipt">
                    <input id="edu_date" type="text" />
                </td>
                <td class="label"><font size="2" color="red">*</font>培训课程：</td>
                <td class="ipt">
                    <input id="class_name" type="text" />
                </td>
                <td class="label"><font size="2" color="red">*</font>授课人：</td>
                <td class="ipt">
                    <select id="teacher" style="width:180px;"></select>
                </td>
            </tr>
            <tr>
                <td class="label"><font size="2" color="red">*</font>培训学时：</td>
                <td class="ipt">
                    <input id="hours" type="text" />
                </td>
                <td class="label"><font size="2" color="red">*</font>培训地点：</td>
                <td class="ipt">
                    <input id="place" type="text" />
                </td>
            </tr>
        </table>
    </div>
    <div id="mainGrid"></div>
</body>

</html>