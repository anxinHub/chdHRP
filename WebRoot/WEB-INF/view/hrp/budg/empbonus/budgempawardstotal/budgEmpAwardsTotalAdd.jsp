<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
    <%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html style="overflow:hidden;">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <jsp:include page="${path}/static_resource.jsp">
        <jsp:param value="select,datepicker,etValidate,dialog,grid" name="plugins" />
    </jsp:include>

    <script type="text/javascript">
        var dataFormat,
            year,
            dept_id,
            emp_id,
            awards_item_code,
            validate,
            emp_type_code;
        $(function () {
            loadDict() //加载下拉框
            loadForm()
        });

        function save() {
            var formPara = {

                year: year.getValue().split(".")[0],

                month: year.getValue().split(".")[1],

                dept_id: dept_id.getValue().split(" ")[0],

                emp_id: emp_id.getValue().split(".")[0],

                emp_type_code: emp_type_code.getValue(),

                awards_item_code: awards_item_code.getValue(),

                amount: $("#amount").val()

            };
            ajaxPostData({
                url: "addBudgEmpAwardsTotal.do",
                data: formPara,
                success: function (res) {
                    if (res.state == "true") {
                        year.setValue('');
                        dept_id.setValue('');
                        emp_id.setValue('');
                        emp_type_code.setValue('');
                        awards_item_code.setValue('');
                        $("input[name='amount']").val('');
                        parent.query();
                    }
                }
            })
        }

        function loadForm(){
            validate = $.etValidate({
               config: {
                   // tipTime: 1000
               },
               items: [
                   {
                       el: $("#year"),
                       required: true
                   },
                   {
                       el: $("#dept_id"),
                       required: true
                   },
                   {
                       el: $("#emp_id"),
                       required: true
                   },
                   {
                       el: $("#emp_type_code"),
                       required: true,
                   },
                   {
                       el: $("#awards_item_code"),
                       required: true,
                   },
                   {
                       el: $("#amount"),
                       required: true,
                       test: /\d/
                   }
               ]
           })
        }

        function saveBudgEmpAwardsTotal() {
            if(validate.test()) {
                save();
            }
        }

        function loadDict() {
            //字典下拉框
           year = $("#year").etSelect({
                url: '../../../acc/queryYearMonth.do?isCheck=false',
                defaultValue: "none"
            });
           dept_id = $("#dept_id").etSelect({
                url: '../../queryDept.do?isCheck=false',
                defaultValue: "none"
            });
           emp_id = $("#emp_id").etSelect({
                url: '../../queryEmpDict.do?isCheck=false',
                defaultValue: "none"
            });
           awards_item_code = $("#awards_item_code").etSelect({
                url: '../../queryBudgAwardsItem.do?isCheck=false',
                defaultValue: "none"
            });
            
           emp_type_code = $("#emp_type_code").etSelect({
                url: '../../queryEmpType.do?isCheck=false',
                defaultValue: "none"
            });
        }
    </script>
</head>

<body>
    <div id="pageloading" class="l-loading" style="display: none"></div>
        <div class="main">
            <table class="table-layout">
                <tr>
                    <td class="label no-empty">统计年月：</td>
                    <td class="ipt">
                        <select name="year" id="year" style="width:180px;" validate="{required:true,maxlength:20}"></select>
                    </td>
                    <td class="label no-empty">科室名称：</td>
                    <td class="ipt">
                        <select name="dept_id" id="dept_id" style="width:180px;" validate="{required:true,maxlength:20}"></select>
                    </td>
                </tr>
                <tr>
                    <td class="label no-empty">职工：</td>
                    <td class="ipt">
                        <select name="emp_id" id="emp_id" style="width:180px;" validate="{required:true,maxlength:20}"></select>
                    </td>
                    <td class="label no-empty">职工类别：</td>
                    <td class="ipt">
                        <select name="emp_type_code" id="emp_type_code" style="width:180px;" validate="{required:true,maxlength:20}"></select>
                    </td>
                </tr>
                <tr>
                    <td class="label no-empty">奖金项目：</td>
                    <td class="ipt">
                        <select name="awards_item_code" id="awards_item_code" style="width:180px;" validate="{required:true,maxlength:20}"></select>
                    </td>
                    <td class="label no-empty">金额：</td>
                    <td class="ipt">
                        <input name="amount" type="text" id="amount" validate="{required:true,maxlength:20}"/>
                    </td>
                </tr>
            </table>
		</div>
</body>

</html>