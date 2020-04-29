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
     var year_input,month_input,dept_id_select,subj_code_select;
     var etValidate;
     
     $(function (){
        loadDict()//加载下拉框
        etValidate = $.etValidate({
            config: {

            },
            items: [
                { el: $("#year_input"), required: true },
                { el: $("#month_input"), required: true },
                { el: $("#dept_id_select"), required: true },
                { el: $("#subj_code_select"), required: true },
                { el: $("#amount"), required: true}
            ]
        })
     });  
     
     function  save(){
        var formPara={
    		 year: year_input.getValue(),
             
    		 month:month_input.getValue(),
    		 
             dept_id: dept_id_select.getValue(),
              
             subj_code: subj_code_select.getValue(),
              
             amount:$("#amount").val(),
              
             remark:$("#remark").val()
        };
        ajaxPostData({
            url: "updateBudgMedInExecute.do",
            data: formPara,
            success: function(responseData) {
                if (responseData.state == "true") {
               	 	parent.query();
               	 	close_page();
                }
            }
        });
    }
    
    //关闭当前页面
    function close_page() {
        var curIndex = parent.$.etDialog.getFrameIndex(window.name);
        parent.$.etDialog.close(curIndex);
    }
     
    function saveBudgMedInExecute(){
    	if(etValidate.test()){
            save();
        }
    }
    function loadDict(){
    	year_input = $("#year_input").etDatepicker({
            view: "years",
            minView: "years",
            dateFormat: "yyyy",
            clearButton: false,
            onChange: function () {
                setTimeout(function () {
                	query();
                }, 10);
            },
            defaultDate: "${year}"
        });

        month_input = $("#month_input").etDatepicker({
           view: "months",
           defaultDate: "${month}",
           minView: "months",
           showNav: false,
           dateFormat: "mm",
           todayButton: false,
	    });
        
        dept_id_select = $("#dept_id_select").etSelect({
			defaultValue:"${dept_id}",
			url:"../../queryBudgDeptDictAll.do?isCheck=false&is_last=1",
	    });
        
        subj_code_select = $("#subj_code_select").etSelect({
			defaultValue: "${subj_code}",
			url:"../../queryBudgSubj.do?isCheck=false&subj_type=04&budg_year="+"${year}",
	    });
	    function reloadSubjName(value){
			subj_code_select.reload({
			
			})
	    }
    } 
    </script>
  </head>
  
<body>
	<div class="main">
   		<table class="table-layout" >
			<tr>
	            <td class="label no-empty">预算年度：</td>
	            <td class="ipt">
	                <input id="year_input" name="year_input" disabled="disabled" type="text"/>
	            </td>
	            <td class="label">月份：</td>
	            <td class="ipt">
	                <input type="text"  disabled="disabled" id="month_input" />
	            </td>
	        </tr>
	        <tr>
	            <td class="label no-empty">部门：</td>
	            <td class="ipt">
	                <select id="dept_id_select"  disabled="disabled" style="width: 180px;"></select>
	            </td>
	            <td class="label no-empty">科目编码：</td>
	            <td class="ipt">
	                <select id="subj_code_select"  disabled="disabled" style="width: 180px;"></select>
	            </td>
	        </tr>
	        <tr>
	            <td class="label no-empty">金额：</td>
	            <td class="ipt">
	                <input id="amount" value="${amount}" name="amount" type="text"/>
	            </td>
	            <td class="label">说明：</td>
	            <td class="ipt">
	                <input name="remark" value="${remark}" type="text" id="remark" style="width: 200px;" />
	            </td>
	        </tr>
	    </table>
    </div>
</body>
</html>
