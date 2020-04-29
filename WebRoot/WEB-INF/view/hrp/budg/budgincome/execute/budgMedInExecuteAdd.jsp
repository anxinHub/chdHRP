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
                { el: $("#amount"), required: true }
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
            url: "addBudgMedInExecute.do",
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
      	month_input.clear();
      	dept_id_select.clearItem();
      	subj_code_select.clearItem();
        $("#amount").val('');
        $("#remark").val('');
    }
     
    function saveBudgMedInExecute(){
    	if(etValidate.test()){
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
    function loadDict(){
    	getData("../../queryBudgYear.do?isCheck=false", function (data) {
    		year_input = $("#year_input").etDatepicker({
				defaultDate: data[0].text,
				view: "years",
				minView: "years",
				dateFormat: "yyyy",
				/* minDate: data[data.length - 1].text,
				maxDate: data[0].text, */
				todayButton: false,
				onSelect: function (value) {
					reloadSubjName(value);
				}
			});
			reloadSubjName(data[0].text);
		});

        month_input = $("#month_input").etDatepicker({
           view: "months",
           minView: "months",
           showNav: false,
           dateFormat: "mm",
           todayButton: false,
	    });
        
        dept_id_select = $("#dept_id_select").etSelect({
			defaultValue:"none",
			url:"../../queryBudgDeptDictAll.do?isCheck=false&is_last=1",
	    });
        
        subj_code_select = $("#subj_code_select").etSelect({
			defaultValue: "none",
	    });
	    function reloadSubjName(value){
			subj_code_select.reload({
				url:"../../queryBudgSubj.do?isCheck=false",
				para:{
					subj_type:'04',
					budg_year:value
				}
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
	                <input id="year_input" name="year_input" type="text"/>
	            </td>
	            <td class="label">月份：</td>
	            <td class="ipt">
	                <input type="text" id="month_input" />
	            </td>
	        </tr>
	        <tr>
	            <td class="label no-empty">部门：</td>
	            <td class="ipt">
	                <select id="dept_id_select" style="width: 180px;"></select>
	            </td>
	            <td class="label no-empty">科目编码：</td>
	            <td class="ipt">
	                <select id="subj_code_select" style="width: 180px;"></select>
	            </td>
	        </tr>
	        <tr>
	            <td class="label no-empty">金额：</td>
	            <td class="ipt">
	                <input id="amount" name="amount" type="text"/>
	            </td>
	            <td class="label">说明：</td>
	            <td class="ipt">
	                <input name="remark" type="text" id="remark" style="width: 200px;" />
	            </td>
	        </tr>
	    </table>
    </div>
</body>
</html>
