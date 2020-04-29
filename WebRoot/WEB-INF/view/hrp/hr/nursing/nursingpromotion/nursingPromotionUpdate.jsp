<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="hr,dialog, select, datepicker, validate, grid" name="plugins" />
	</jsp:include>
    <script>
        var apply_level_code, apply_date;
        var formValidate;

        var initValidate = function () {
            formValidate = $.etValidate({
                items: [
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
            var data = {
                // 表单 填写的
                year: '${year}',
                dept_no: '${dept_no}',
                emp_id: '${emp_id}',
                apply_level_code: apply_level_code.getValue(),
                apply_date: apply_date.getValue(),
                // 表单 带出来的
                emp_id: '${emp_id}',
                degree_code: '${degree_code}',
               //graduation_date: '${graduation_date}',
                birthday: '${birthday}',
                cur_get_date: '${cur_get_date}',
                cur_level_code: '${cur_level_code}',
                cur_title_code: '${cur_title_code}',
                cur_title_date: '${cur_title_date}',
                field_col_code: '${field_col_code}',
                max_degree_code: '${max_degree_code}',
                max_graduation_date: '${max_graduation_date}',
                worktime: '${worktime}',
                // 表格
                Param: JSON.stringify(grid.getAllData()),
            };
            ajaxPostData({
               url: 'updateNursingPromotion.do',
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
        // 提交
        var submit = function () {
        	var ParamVo = [];
        	var data={
        			  // 表单 填写的
                    year: '${year}',
                    dept_no: '${dept_no}',
                    emp_id: '${emp_id}',
                    state:'${state}',
                    apply_level_code: apply_level_code.getValue(),
                    apply_date: apply_date.getValue(),
                    // 表单 带出来的
                    emp_id: '${emp_id}',
                    degree_code: '${degree_code}',
                   //graduation_date: '${graduation_date}',
                    birthday: '${birthday}',
                    cur_get_date: '${cur_get_date}',
                    cur_level_code: '${cur_level_code}',
                    cur_title_code: '${cur_title_code}',
                    cur_title_date: '${cur_title_date}',
                    field_col_code: '${field_col_code}',
                    max_degree_code: '${max_degree_code}',
                    max_graduation_date: '${max_graduation_date}',
                    worktime: '${worktime}',
        	};
        	 ParamVo.push(data)
            ajaxPostData({
                url: 'confirmNursingPromotion.do',
                data: {
                    paramVo: JSON.stringify(ParamVo)
                },
                success: function () {
                	  var parentFrameName = parent.$.etDialog.parentFrameName;
                      var parentWindow = parent.window[parentFrameName];
                      parentWindow.query();
                      close();
                },
                delayCallback: true
            })
        };
        /* var print = function () {}; */
        

        var initForm = function () {
            apply_level_code = $("#apply_level_code").etSelect({
                url: "queryLevel.do?isCheck=false",
                defaultValue: "${apply_level_code}"
            });
            apply_date = $("#apply_date").etDatepicker({
                defaultDate: '${apply_date}'
            });
            
            <%-- 状态为新建晋升级别、申请时间可选，否则不可选 --%>
            var state = ${state};
            if(state){
            	$("#apply_date").attr("disabled", "disabled");
            	apply_level_code.disabled();
            	$("#save").hide();
            	$("#submit").hide();
            }else{
            	$("#save").on('click', save);
            	$("#submit").on('click', submit);
            }
            /* $("#print").on('click', print); */
            $("#close").on('click', close);
        };
        
        var query = function () {
            params = [
                { name: 'year', value: '${year}' },
                { name: 'dept_no', value: '${dept_no}' },
                { name: 'emp_id', value: '${emp_id}' },
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
            query();
        })

        
    </script>
</head>

<body>
    <div class="main">
        <table class="table-layout" style="width: 100%;">
            <tr>
                <td class="label"><font size="2" color="red">*</font>年度：</td>
                <td class="ipt">
                    <input id="year" type="text" disabled value="${year}" />
                </td>
                <td class="label"><font size="2" color="red">*</font>科室：</td>
                <td class="ipt">
                    <input id="dept_no" type="text" disabled value="${dept_name}" />
                </td>
                <td class="label"><font size="2" color="red">*</font>职工：</td>
                <td class="ipt">
                    <input id="emp_id" type="text" disabled value="${emp_name}" />
                </td>
            </tr>
            <tr>
                <td class="label">姓名：</td>
                <td class="ipt">
                    <input id="emp_name" type="text" disabled value="${emp_name}"/>
                </td>
                <td class="label">毕业学历：</td>
                <td class="ipt">
                    <input id="degree_name" type="text" disabled value="${degree_name}"/>
                </td>
                <td class="label">毕业时间：</td>
                <td class="ipt">
                    <input id="graduation_date" type="text" disabled value="${graduation_date}"/>
                </td>
            </tr>
            <tr>
                <td class="label">出生年月：</td>
                <td class="ipt">
                    <input id="birthday" type="text" disabled value="${birthday}"/>
                </td>
                <td class="label">最高学历：</td>
                <td class="ipt">
                    <input id="max_degree_name"  type="text" disabled value="${max_degree_name}"/>
                </td>
                <td class="label">毕业时间：</td>
                <td class="ipt">
                    <input id="max_graduation_date" type="text" disabled value="${max_graduation_date}"/>
                </td>
            </tr>
            <tr>
                <td class="label">现有阶别：</td>
                <td class="ipt">
                    <input id="cur_level_name" type="text" disabled value="${cur_level_name}"/>
                </td>
                <td class="label">进阶时间：</td>
                <td class="ipt">
                    <input id="cur_get_date" type="text" disabled value="${cur_get_date}"/>
                </td>
            </tr>
            <tr>
                <td class="label">现有技术积称：</td>
                <td class="ipt">
                    <input id="cur_title_name" type="text" disabled value="${cur_title_name}"/>
                </td>
                <td class="label">职能晋升时间：</td>
                <td class="ipt">
                    <input id="cur_title_date" type="text" disabled value="${cur_title_date}"/>
                </td>
                <td class="label">参加工作时间：</td>
                <td class="ipt">
                    <input id="worktime" type="text" disabled value="${worktime}"/>
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
        <button id="submit">提交</button>
        <!-- <button id="print">打印</button> -->
        <button id="close">关闭</button>
    </div>
    <div id="mainGrid"></div>
</body>

</html>