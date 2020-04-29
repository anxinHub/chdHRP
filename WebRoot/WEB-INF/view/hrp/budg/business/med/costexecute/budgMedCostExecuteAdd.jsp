<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String path = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/static_resource.jsp">
    <jsp:param value="select,datepicker,etValidate,dialog" name="plugins" />
</jsp:include>
<script type="text/javascript">
    var year, month, subj_code, dept_id;
    var formValidate;

    $(function() {
        loadDict();
        
        formValidate = $.etValidate({
            config: {},
            items: [
                { el: $("#year"), required: true },
                { el: $("#month"), required: true },
                { el: $("#subj_code"), required: true },
                { el: $("#dept_id"), required: true },
                { el: $("#amount"), type: 'number' },
            ]
        });
    });

    function save() {
        var formPara = {
            year: year.getValue(),
            month: month.getValue(),
            dept_id: dept_id.getValue().split(".")[0],
            subj_code: subj_code.getValue().split(".")[1],
            amount: $("#amount").val(),
            remark: $("#remark").val()
        };

        ajaxPostData({
            url: "addBudgMedCostExecute.do",
            data: formPara,
            success: function(responseData) {
                year.clear();
                month.clear();
                dept_id.clearItem();
                subj_code.clearItem();
                $("#amount").val("");
                $("#remark").val("");

                parent.query();
            }
        });
    }


    function saveBudgMedCostExecute() {
        if (formValidate.test()) {
            save();
        }
    }
    function getData(url, callback) {
		$.ajax({
			url: url,
			dataType: "JSON",
			type: "post",
			success: function (res) {
				if (typeof callback === "function") {
					callback(res);
				}
			}
		});
	};
    function loadDict() {
    	getData("../../../queryBudgYear.do?isCheck=false", function (data) {
    		year = $("#year").etDatepicker({
				defaultDate: data[0].text,
				view: "years",
				minView: "years",
				dateFormat: "yyyy",
				minDate: data[data.length - 1].text,
				maxDate: data[0].text,
				todayButton: false,
				onSelect: function (value) {
					reloadSubjName(value);
					setTimeout(function () {
						query();
					}, 10);
				}
			});
    		reloadSubjName(data[0].text);
		});
        month = $("#month").etDatepicker({
            view: "months",
            minView: "months",
            showNav: false,
            dateFormat: "mm"
        });
        subj_code = $("#subj_code").etSelect({
            defaultValue: "none"
        });
        function reloadSubjName(year) {
        	subj_code.reload({
                url: "../../../queryBudgSubj.do?isCheck=false",
                para: {
                    subj_type: "05",
                    budg_year: year
                }
            });
        }
        dept_id = $("#dept_id").etSelect({
            url: "../../../queryDept.do?isCheck=false",
            defaultValue: "none"
        });
    }
</script>
</head>

<body>
    <div class="mian">
        <table class="table-layout">
            <tr>
                <td class="label no-empty">年度：</td>
                <td class="ipt">
                    <input id="year" type="text" />
                </td>
            </tr>
            <tr>
                <td class="label no-empty">月份：</td>
                <td class="ipt">
                    <input id="month" type="text" />
                </td>
            </tr>
            <tr>
                <td class="label no-empty">部门：</td>
                <td class="ipt">
                    <select id="dept_id" style="width: 180px;"></select>
                </td>
            </tr>
            <tr>
                <td class="label no-empty">科目：</td>
                <td class="ipt">
                    <select id="subj_code" style="width: 180px;"></select>
                </td>
            </tr>
            <tr>
                <td class="label">金额：</td>
                <td class="ipt">
                    <input id="amount" type="text" />
                </td>
            </tr>
            <tr>
                <td class="label">说明：</td>
                <td class="ipt">
                    <input id="remark" type="text" />
                </td>
                
            </tr>
        </table>
    </div>
</body>

</html>