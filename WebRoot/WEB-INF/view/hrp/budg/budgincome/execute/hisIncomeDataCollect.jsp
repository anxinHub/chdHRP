<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/static_resource.jsp">
	<jsp:param value="select,etValidate,datepicker" name="plugins" />
</jsp:include>

<script type="text/javascript">
var year_month_input , is_detail_select, etValidate;
     $(function (){
     	
        loadDict();//加载下拉框
        etValidate = $.etValidate({
            config: {

            },
            items: [
                { el: $("#year_month"), required: true },
                { el: $("#is_detail"), required: true },
            ]
        })
     });  
     
     function  saveHisExecuteData(){
    	 if(!$("#year_month").val()){
    		 $.ligerDialog.warn('请选择采集年月');
    		 return false ;
    	 }
    	 
    	 if(!$("#is_detail").val()){
    		 $.ligerDialog.warn('是否细分诊疗组不能为空'); 
    		 return false ;
    	 }	 
		var formPara={
				acc_year: year_month_input.getValue().split("-")[0],
               acc_month: year_month_input.getValue().split("-")[1], 
               flag :  is_detail_select.getValue(),
               ds_code: 'his' //这个字段是用来确定数据源的不能去掉
        	};
		ajaxPostData({
            url: "saveHisExecuteData.do?isCheck=false",
            data: formPara,
            success: function(responseData) {
                if (responseData.state == "true") {
                	year_month_input.clear();
               		parent.query();
                }
            }
        });
    }
   
    function saveCostDeptPeople(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
    	year_month_input = $("#year_month").etDatepicker({
    		view: "months",
    		minView: "months",
            dateFormat: "yyyy-mm",
            todayButton: false,
 	    });
    	is_detail_select = $("#is_detail").etSelect({
    		options:[{id:'0',text:'否'},{id:'1',text:'是'}]
	    });
     } 
    </script>
  
  </head>
  
   <body>
   <div class="main">
   		<table class="table-layout" >
			<tr>
            	<td class="label no-empty" style="width:120px">采&nbsp&nbsp&nbsp集&nbsp&nbsp&nbsp年&nbsp&nbsp&nbsp月：</td>
	            <td class="ipt">
	                <input id="year_month" style="width: 160px;"></input>
	            </td>
            </tr>
            <tr>
            	<td class="label no-empty" style="width:120px">是否细分诊疗组：</td>
	            <td class="ipt">
	                <select id="is_detail" style="width: 160px;"></select>
	            </td>
            </tr>
        </table>
    </form>
   
    </body>
</html>
