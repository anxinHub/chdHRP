<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String path = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/static_resource.jsp">
    <jsp:param value="select,datepicker,grid,dialog,etValidate" name="plugins" />
</jsp:include>
<script src="<%=path%>/lib/hrp/budg/budg.js" type="text/javascript"></script>
<script type="text/javascript">
    var budg_year, subj_name_select, dept_name_select;
    var formValidate;

    $(function() {
        loadDict();
        
        formValidate = $.etValidate({
            config: {},
            items: [
                { el: $("#budg_year"), required: true },
                { el: $("#subj_code"), required: true },
                { el: $("#dept_id"), required: true },
                { el: $("#budg_value"),required: true }
            ]
        });
    });
function checkNum(obj) {
    	
    	if (obj == undefined || obj == "") {
    		return true;
    	}else{
    		
    		var reg = /^[0-9]+(.[0-9]{2})?$/;
        	if (!reg.test(obj)) {
        	$.etDialog.error("请输入数字");
        	return false;
        	}else{
        		return true;
        	}
    	}
    	
    }
    
    function save() {

    	var v_base_value=$("#base_value").val();
    	var v_grow_rate=$("#grow_rate").val();
    	var v_budg_value=$("#budg_value").val();
    	
    	if(!checkNum(v_base_value))
    		return false;
    		
    	if(!checkNum(v_grow_rate))
    		return false;
    	if(!checkNum(v_budg_value))
    		return false;
    		
    	
    	
        var formPara = {
            budg_year: budg_year.getValue(),
            dept_id: dept_name_select.getValue(),
            subj_code: subj_name_select.getValue(),
            base_value: v_base_value,
            grow_rate: v_grow_rate,
            budg_value: v_budg_value,
            remark: $("#remark").val()
        };

        ajaxPostData({
            url: "addBudgMedMonthCost.do",
            data: formPara,
            success: function(responseData) {
                dept_id.clearItem();
                subj_code.clearItem();
                $("#budg_value").val("");
                $("#remark").val("");

                parent.query();
            }
        });
    }


    function saveBudgMedMonth() {
        if (formValidate.test()) {
            save();
        }
    }
	//ajax获取数据
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
		})
	}
    function loadDict() {
    	getData("../../../queryBudgYearTen.do?isCheck=false", function (data) {
			budg_year = $("#budg_year").etDatepicker({
				defaultDate: data[4].text,
				view: "years",
				minView: "years",
				dateFormat: "yyyy",
				minDate: data[0].text,
				maxDate: data[9].text,
				todayButton: false,
				onSelect: function (value) {
					reloadSubjName(value);
				}
			});
			reloadSubjName(data[4].text);
		});
      
       subj_name_select = $("#subj_code").etSelect({
            defaultValue: "none",
        });
        
        function reloadSubjName(year) {
            subj_name_select.reload({
                url: "../../../queryBudgSubj.do?isCheck=false",
                para: {
                	is_last: 1,
                    subj_type: "05",
                    budg_year: year
                }
            });
        }
        
        dept_name_select = $("#dept_id").etSelect({
            url: "../../../queryBudgDeptDict.do?isCheck=false",
            defaultValue: "none",
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
                    <input id="budg_year" type="text" />
                </td>
            </tr>
           
            <tr>
                <td class="label no-empty">科目：</td>
                <td class="ipt">
                    <select id="subj_code" style="width: 180px;"></select>
                </td>
            </tr>
            
            <tr>
                <td class="label no-empty">部门：</td>
                <td class="ipt">
                    <select id="dept_id" style="width: 180px;"></select>
                </td>
            </tr>
            <tr>
                <td class="label">基础值：</td>
                <td class="ipt">
                    <input id="base_value" type="text" />
                </td>
            </tr>
             <tr>
                <td class="label">增长率：</td>
                <td class="ipt">
                    <input id="grow_rate" type="text" />
                </td>
            </tr>
             <tr>
                <td class="label">预算金额：</td>
                <td class="ipt">
                    <input id="budg_value" type="text" />
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