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
  				{ el: $("#emp_id"), required: true },
  				{ el: $("#transfer_date"), required: true },
  			/* 	{ el: $("#adjust_reason"), required: true }, */
              ]
          });
      };
	function save() {
	   	if (!formValidate.test()) {
			return;
		};
		   var formPara={
				  
	                  aft_dept_id: aft_dept_id.getValue().split('@')[0],
	                  aft_dept_no: aft_dept_id.getValue().split('@')[1],
	                  emp_id: emp_id.getValue(),
	                  transfer_date : $("#transfer_date").val(),
	                 transfer_reason:$("#transfer_reason").val(),
	                 state:'0',
	            	tab_code: 'HR_DEPT_TRANSFER',
	            	bef_dept_id: user_hide_data.dept_id,
                     bef_dept_no: user_hide_data.dept_no
            };
    	   ajaxJsonObjectByUrl("addDeptTransfer.do", formPara, function(
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
		transfer_date = $("#transfer_date").etDatepicker();
		   //字典下拉框
		   emp_id = $("#emp_id").etSelect({
		      	url: '../../baseSelect.do?isCheck=false&&field_tab_code=HOS_EMP',
		      	valueField: 'id',
		       	textField: 'text', 
		       	selectBoxWidth: 160,
		      	autocomplete: true,
		      	width: 160,
		      	onChange: function (value) {
		      		$.post("queryEmpInfo.do?isCheck=false", {
						'tab_code' : 'HOS_EMP',
						'emp_id':value,
						 'rjt':'json'
					
					},function(responseData){
		       		 $.each(responseData.Rows,function(i,v){
						
							$("#emp_code").val(v.EMP_CODE);
				   			$("#title").val(v.TITLE);
				   			$("#hostime").val(v.HOSTIME);
				   			$("#bef_dept_id").val(v.DEPT_ID_NAME);
				   			$("#permit").val(v.PERMIT);
				   			$("#technique").val(v.TECHNIQUE);
				   			$("#age").val(v.AGE);
				   			$("#kind_code").val(v.KIND_CODE_NAME);
				   			$("#interpersonal").val(v.INTERPERSONAL);
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
           })

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
					name="emp_id" type="text" id="emp_id" type="text" style="width:180px;"
					/></td>
				<td align="left"></td>
			<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">工号：</td>
				<td align="left" class="l-table-edit-td"><input
					name="emp_code" type="text" id="emp_code" type="text"  disabled/></td>
				<td align="left"></td>
			
			</tr>
			
			 <tr>
			 <td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">职位：</td>
				<td align="left" class="l-table-edit-td"><input
					name="title" type="text" id="title" type="text" disabled/></td>
				<td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">入司时间：</td>
                <td align="left" class="l-table-edit-td"><input name="hostime" type="text"   id="hostime"   disabled/></td>
                <td align="left"></td>
                </tr>
             <!--       <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">原用人部门：</td>
                <td align="left" class="l-table-edit-td"><input name="bef_dept_id" type="text"  id="bef_dept_id" ltype="text"  /></td>
                <td align="left"></td>
                   <td align="right" class="l-table-edit-td"  style="padding-left:20px;">更动后部门：</td>
                <td align="left" class="l-table-edit-td"><input name="aft_dept_id" type="text"  id="aft_dept_id" ltype="text"  /></td>
                <td align="left"></td>
                </tr> -->
			 <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">生效时间：</td>
                <td align="left" class="l-table-edit-td"><input name="transfer_date" type="text"   id="transfer_date" type="text" /></td>
                <td align="left"></td>
                </tr>
             <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">更动原因：</td>
                 <td class="ipt" class="l-table-edit-td" colspan="4">
                            <textarea rows="2" cols="81" id="transfer_reason" name="transfer_reason"></textarea>
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
                                <td class="label">
                                    部门：
                                </td>
                                <td class="ipt">
                                    <input type="text" id="bef_dept_id" disabled>
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
                                    <select name="" id="aft_dept_id" style="width:180px;"></select>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>

</body>
</html>
