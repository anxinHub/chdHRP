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
        <jsp:param value="select,datepicker,dialog,etValidate" name="plugins" />
    </jsp:include>
    <script type="text/javascript">
     var dataFormat;
     var index_code ;
     var year_input, dept_input, index_code_select, form_validate;
     $(function (){
    	 init();
		 registerValidate();
     });  
     
     function init() {
    	 year_input = $("#year_input").etDatepicker({
             view: "years",
             minView: "years",
             dateFormat: "yyyy",
             clearButton: false,
             onChange: function () {
                 setTimeout(function () {
                 	initColumns();
                 }, 10);
             },
             defaultDate: true
         });

	
	    dept_input = $("#dept_input").etSelect({
	        url: "../../../queryDept.do?isCheck=false&is_last=1",
	        defaultValue: "none"
	    });
	    
	    index_code_select = $("#index_code_select").etSelect({
	        url: "../../../queryBudgIndexDict.do?isCheck=false",
	        defaultValue: "none"
	    });
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
	    });
	}
	
	function registerValidate() {
	    form_validate = $.etValidate({
	        items: [
	            { el: $("#year_input"), required: true },
	            { el: $("#dept_input"), required: true },
	            { el: $("#index_code_select"), required: true },
	            { el: $("#execute_input"), required: true }
	        ]
	    });
	}

    function  save(){
        var formPara={
           year: year_input.getValue(),
            
           dept_id: dept_input.getValue(),
            
           index_code: index_code_select.getValue(),
            
           execute_value:$("#execute_input").val(),
            
           remark:$("#remark_input").val()
        };
        
        ajaxPostData({
            url: "addBudgWorkDeptYearExecute.do",
            data: formPara,
            success: function (res) {
                if (res.state == "true") {
                   // year_input.clear();
                   // dept_input.clearItem();
                   // index_code_select.clearItem();
                    $("#execute_input").val('');
                    $("#remark_input").val('');
                    parent.query();
                }
            },
        });
    }
   
    function saveBudgWorkDeptYearExecute(){
        if(form_validate.test()){
            save();
        }
    }
    </script>
  </head>
  
  <body style="overflow:hidden">
    <div class="main" style="height:500px">
        <table class="table-layout">
            <tr>
                <td class="label no-empty">预算年度：</td>
                <td class="ipt">
                    <input type="text" id="year_input" />
                </td>
                <td class="label no-empty">指标编码：</td>
                <td class="ipt">
                    <select name="" id="index_code_select" style="width:180px;"></select>
                </td>
            </tr>
            <tr>
                <td class="label no-empty">科室：</td>
                <td class="ipt">
                    <input type="text" id="dept_input" style="width:180px;"/>
                </td>
                <td class="label no-empty">执行数据：</td>
                <td class="ipt">
                    <input type="text" id="execute_input" />
                </td>
            </tr>
            <tr>
                <td class="label">说明：</td>
                <td class="ipt" colspan="3">
                    <input type="text" id="remark_input" style="width:487px;" />
                </td>
            </tr>
        </table>
    </div>
</body>
</html>
