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
     var year_input,index_code_select,dept_id_select;
     var etValidate;
     $(function (){
         loadDict()//加载下拉框
         etValidate = $.etValidate({
             config: {

             },
             items: [
                 { el: $("#year_input"), required: true },
                 { el: $("#index_code_select"), required: true },
                 { el: $("#dept_id_select"), required: true },
                 { el: $("#execute_value"), required: true }
             ]
         })
     });  
     function  save(){
        var formPara={
           year: year_input.getValue(),
           index_code: index_code_select.getValue(),
           dept_id: dept_id_select.getValue(),
           execute_value:$("#execute_value").val(),
           remark:$("#remark").val()
        };
        ajaxPostData({
            url: "addWorkDeptYearExecute.do",
            data: formPara,
            success: function(responseData) {
               if (responseData.state == "true") {
	               resetForm();
	               parent.query();
               }
            }
        });
     }
   	 
     //重置表单
     function resetForm() {
     	year_input.clear();
     	index_code_select.clearItem();
     	dept_id_select.clearItem();
        $("#execute_value").val('');
        $("#remark").val('');
     }
     function checkNum(obj) {
  		if (obj == undefined || obj == "") {
  			return true;
  		}else{
  			var reg = /^(\d+)(.?[0-9]{0,2})?$/
  	    	if (!reg.test(obj)) {
  	    		$.etDialog.error("请输入数字(最多两位小数)");
  	    		return false;
  	    	}else{
  	    		return true;
  	    	}
  		}
 	 }
     function saveBudgWorkDeptYearExecute(){
    	var v_execute_value =$("#execute_value").val();
 		if(!checkNum(v_execute_value))
 			return false;
        if(etValidate.test()){
            save();
        }
     }
     
     function loadDict(){
    	 ajaxPostData({
             url: '../../../queryBudgYear.do?isCheck=false',
             success: function (data) {
             	year_input = $("#year_input").etDatepicker({
                     defaultDate: data[0].text,
                     view: "years",
                     minView: "years",
                     dateFormat: "yyyy",
                     //minDate: data[data.length - 1].text,
                     //maxDate: data[0].text,
                     todayButton: false,
                 });
             }
         })
         
         index_code_select = $("#index_code_select").etSelect({
 			defaultValue:"none",
 			url:"../../../queryBudgIndexDict.do?isCheck=false",
 		 });
    	 
    	 dept_id_select = $("#dept_id_select").etSelect({
  			defaultValue:"none",
  			url:"../../../queryDept.do?isCheck=false",
  		 });
     } 
    </script>
</head>
  
<body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <div class="main">
   		<table class="table-layout" >
		<tr>
            <td class="label no-empty">预算年度：</td>
            <td class="ipt">
                <input id="year_input" name="year_input" type="text"/>
            </td>
            <td class="label no-empty">指标编码：</td>
            <td class="ipt">
                <select id="index_code_select" style="width: 180px;"></select>
            </td>
        </tr>
        <tr>
            <td class="label no-empty">科室名称：</td>
            <td class="ipt">
                <select id="dept_id_select" style="width: 180px;"></select>
            </td>
            <td class="label no-empty">指标值：</td>
            <td class="ipt">
                <input id="execute_value" name="execute_value" type="text"/>
            </td>
        </tr>
        <tr>
            <td class="label">说明：</td>
            <td class="ipt">
                <input name="remark" type="text" id="remark" style="width: 300px;"/>
            </td>
        </tr>
    </table>
  </div>
</body>
</html>
