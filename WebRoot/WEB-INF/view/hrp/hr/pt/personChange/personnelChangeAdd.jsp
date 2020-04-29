<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/resource.jsp">
<jsp:param value="select, datepicker, hr, dialog, upload,validate" name="plugins" />
</jsp:include>
<style>
.title {
    padding: 5px;
}
</style>
<script type="text/javascript">
	var dataFormat;
	var first_length=0;
	 var dialog = frameElement.dialog; //调用页面的dialog对象(ligerui对象)
	    var user_hide_data = {};
	$(function() {
		loadDict()//加载下拉框
		 initValidate();
	});
	  var initValidate = function () {
          formValidate = $.etValidate({
          	config: {},
          	items: [
  				{ el: $("#chang_emp_name"), required: true },
  				{ el: $("#chang_effect_time"), required: true },
              ]
          });
      };
	function save() {
		if (!formValidate.test()) {
            return;
        }
		   var formPara={
				 emp_id: $("#emp_select").val(),
				 chang_emp_unit : $("#chang_emp_unit").val(),
				 chang_entry_time : $("#chang_entry_time").val(),
				 chang_effect_time : $("#chang_effect_time").val(),

                 bef_dept_id: user_hide_data.dept_id,
                 bef_dept_no: user_hide_data.dept_no,
                 aft_dept_id: aft_dept_id.getValue().split('@')[0],
                 aft_dept_no: aft_dept_id.getValue().split('@')[1],
                
                 bef_station_code: $("#bef_station_code").val(),
                 aft_station_code: $("#aft_station_code").val(),
                 bef_level_code: $("#bef_level_code").val(),
                 aft_level_code: $("#aft_level_code").val(),
                 bef_salary: $("#bef_salary").val(),
                 aft_salary: $("#aft_salary").val(),
                 bef_salary_float: $("#bef_salary_float").val(),
                 aft_salary_float: $("#aft_salary_float").val(),
               	 bef_year_salary: $("#bef_year_salary").val(),
           		 aft_year_salary: $("#aft_year_salary").val(),
      			 chang_reason : $("#chang_reason").val(),
      			 chang_emp_state:'0',
            	 tab_code: 'HR_PERSONNEL_CHANGE',
            };
    	   ajaxJsonObjectByUrl("addPersonnelChange.do", formPara, function(
					responseData) {
    		   if(responseData.state=="true"){
    				
                    parent.query();
                    dialog.close();
                }
			});
	}

	
	function saveData() {
		
			save();
		
	}
	function loadDict() {
		   chang_entry_time = $("#chang_entry_time").etDatepicker();
		   chang_effect_time = $("#chang_effect_time").etDatepicker();
		   //字典下拉框
		   chang_emp_name = $("#chang_emp_name").etSelect({
		      	url: '../../baseSelect.do?isCheck=false&&field_tab_code=HOS_EMP',
		      	valueField: 'id',
		       	textField: 'text', 
		       	selectBoxWidth: 160,
		      	autocomplete: true,
		      	width: 160,
		      	onChange: function (value) {
		      		$("#emp_select").val(value);
		      		$.post("queryChangeEmpInfo.do?isCheck=false", {
						'tab_code' : 'HOS_EMP',
						'emp_select':value,
						 'rjt':'json'
					
					},function(responseData){
		       		 $.each(responseData.Rows,function(i,v){
						
// 							$("#emp_select").val(v.EMP_CODE);
				   			$("#chang_emp_unit").val(v.ORI_HOS);
				   			$("#chang_entry_time").val(v.HOSTIME);
				   			$("#bef_station_code").val(v.BEF_STATION_CODE);
				   			$("#bef_dept_id").val(v.DEPT_ID);
				   			$("#bef_level_code").val(v.BEF_LEVEL_CODE);
				   			$("#bef_salary").val(v.BEF_SALARY);
				   			$("#bef_year_salary").val(v.BEF_YEAR_SALARY);
				   			$("#bef_salary_float").val(v.BEF_SALARY_FLOAT);
				   		 user_hide_data = {
                               
                                 dept_id: v.DEPT_ID || '',
                                 dept_no: v.DEPT_NO || ''
                             };
					 })
		       		})
		      	}
			 });
		   aft_dept_id = $("#aft_dept_id").etSelect({
               url: "../../baseSelect.do?isCheck=false&&field_tab_code=SYS_DEPT",
               defaultValue: 'none'
           });
		   aft_station_code = $("#aft_station_code").etSelect({
               url: "../../baseSelect.do?isCheck=false&&field_tab_code=DIC_STATION_MANAGE",
               defaultValue: 'none'
           });
		   aft_level_code = $("#aft_level_code").etSelect({
               url: "../../baseSelect.do?isCheck=false&&field_tab_code=DIC_TITLE_LEVEL",
               defaultValue: 'none'
           });

	}
	
</script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	  <div class="flex-wrap">
		<table class="table-layout flex-item-1">
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">员工姓名<span style="color:red">*</span>：</td>
				<td align="left" class="l-table-edit-td"><input
					name="chang_emp_name" type="text" id="chang_emp_name" type="text" style="width:180px;"
					/></td>
				<td align="left"></td>
			<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">工号：</td>
				<td align="left" class="l-table-edit-td"><input
					name="emp_select" type="text" id="emp_select" type="text"  disabled/></td>
				<td align="left"></td>
			
			</tr>
			
			 <tr>
			 <td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">单位：</td>
				<td align="left" class="l-table-edit-td"><input
					name="chang_emp_unit" type="text" id="chang_emp_unit" type="text" disabled/></td>
				<td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">入司时间：</td>
                <td align="left" class="l-table-edit-td"><input name="chang_entry_time" type="text" id="chang_entry_time"   disabled/></td>
                <td align="left"></td>
                </tr>
			 <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">生效时间：</td>
                <td align="left" class="l-table-edit-td"><input name="chang_effect_time" type="text"  id="chang_effect_time" type="text" /></td>
                <td align="left"></td>
                </tr>
             <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">更动原因：</td>
                 <td align="left" class="l-table-edit-td" colspan="4">
                            <textarea rows="2" cols="81" id="chang_reason" name="chang_reason"></textarea>
                        </td>
                <td align="left"></td>
            </tr> 

		</table>
		   </div>
		       <div class="flex-wrap" style="height:130px;">
                <div class="flex-item-1">
                    <div class="title">
                        <<调整前>>
                    </div>
                    <div class="single-block">
                        <table class="table-layout ">
                            <tr>
                                <td class="label">    部门：</td>
                                <td class="ipt">
                                    <input type="text" id="bef_dept_id" disabled>
                                </td>
                            </tr>
                            <tr>
                                <td class="label">    职位：</td>
                                <td class="ipt">
                                    <input type="text" id="bef_station_code" disabled>
                                </td>
                            </tr>
                            <tr>
                                <td class="label">    级别：</td>
                                <td class="ipt">
                                    <input type="text" id="bef_level_code" disabled>
                                </td>
                            </tr>
                            <tr>
                                <td class="label">    薪资：</td>
                                <td class="ipt">
                                    <input type="text" id="bef_salary" disabled>
                                </td>
                            </tr>
                            <tr>
                                <td class="label">    薪资幅度：</td>
                                <td class="ipt">
                                    <input type="text" id="bef_salary_float" disabled>
                                </td>
                            </tr>
                            <tr>
                                <td class="label">    年薪：</td>
                                <td class="ipt">
                                    <input type="text" id="bef_year_salary" disabled>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
                <div class="flex-item-1">
                    <div class="title">
                        <<调整后>>
                    </div>
                    <div class="single-block">
                        <table class="table-layout ">
                            <tr>
                                <td class="label">
                                    <font size="2" color="red">*</font>部门：
                                </td>
                                <td class="ipt">
                                    <select name="aft_dept_id" id="aft_dept_id" style="width:180px;"></select>
                                </td>
                            </tr>
                             <tr>
                                <td class="label">    职位：</td>
                                <td class="ipt">
                                    <input type="text" id="aft_station_code" style="width:180px;">
                                </td>
                            </tr>
                            <tr>
                                <td class="label">    级别：</td>
                                <td class="ipt">
                                    <input type="text" id="aft_level_code" style="width:180px;">
                                </td>
                            </tr>
                            <tr>
                                <td class="label">    薪资：</td>
                                <td class="ipt">
                                    <input type="text" id="aft_salary">
                                </td>
                            </tr>
                            <tr>
                                <td class="label">    薪资幅度：</td>
                                <td class="ipt">
                                    <input type="text" id="aft_salary_float">
                                </td>
                            </tr>
                            <tr>
                                <td class="label">    年薪：</td>
                                <td class="ipt">
                                    <input type="text" id="aft_year_salary">
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>

</body>
</html>
