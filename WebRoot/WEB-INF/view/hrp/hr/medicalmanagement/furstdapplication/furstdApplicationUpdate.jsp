<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="${path}/resource.jsp">
	<jsp:param value="hr,select,validate,grid,upload,datepicker,dialog"
		name="plugins" />
</jsp:include>
<script>
	 var user_hide_data = {};


	// var initValidate = function () {
	//     formValidate = $.etValidate({
	//         items: [
	//             { el: $("#"), required: true },
	//         ]
	//     });
	// };
	var initForm = function() {
		if (' ${is_commit}' != 0) {//置黑不能点击
			/* toobarmanage = gridManager.toolbarManager;
			toobarmanage.setDisabled('save'); */
			document.getElementById("save").disabled = true;
		}
		app_date = $("#app_date").etDatepicker();
		beg_date = $("#beg_date").etDatepicker();
		hostel = $("#hostel").etSelect({
            options: [
                { id: 0, text: '否' },
                { id: 1, text: '是' }
            ],
            defaultValue: "${hostel}",
        });
	
		//主表保存
		$("#save").click(function() {
		    
		
			if ($("#app_date").val() == "") {
				$.etDialog.error('申请日期不能为空');
				return;
			}
			if ($("#emp_id").val() == "") {
				$.etDialog.error('申请人不能为空');
				return;
			}
			var formPara = {
				app_no: $("#app_no").val(),
				app_date : $("#app_date").val(),
				emp_id : $("#emp_id").val(),
				profession : $("#profession").val(),
				sex_code : $("#sex_code").val(),
				age : $("#age").val(),
				hostime : $("#hostime").val(),
				workage : $("#workage").val(),
				political_code : $("#political_code").val(),
				graduation_school : $("#graduation_school").val(),
				english_level : $("#english_level").val(),
				beg_date : $("#beg_date").val(),
				duration : $("#duration").val(),
				sec_profession : $("#sec_profession").val(),
				furstd_hos : $("#furstd_hos").val(),
				hostel : $("#hostel").val(),
				hostel_charge : $("#hostel_charge").val(),
				goal : $("#goal").val(),
				plan1 : $("#plan1").val(),
				plan3 : $("#plan3").val(),
				plan6 : $("#plan6").val(),
			};

			ajaxPostData({
				url : 'updateFurstdApplication.do',
				data : formPara,
			    success: function (responseData) {
                  	$.etDialog.success(
							'修改成功',
							 function (index, el) {
								  var curIndex = parent.$.etDialog.getFrameIndex(window.name);
		                            
		                            var parentFrameName = parent.$.etDialog.parentFrameName;
		                            var parentWindow = parent.window[parentFrameName];
		                            parentWindow.query(); 
		                            parent.$.etDialog.close(curIndex);
							    }
							)
                  },
			})
		});

		//提交
		$("#colse").click(function() { 
			var curIndex = parent.$.etDialog.getFrameIndex(window.name);
			parent.$.etDialog.close(curIndex);
			});
		

		
	}
	
	$(function() {
		// initValidate();
		initForm();
		
	})
</script>
</head>

<body>
	<div class="main">
		<table class="table-layout" style="width: 100%;">
			<tr>
			<input  id="emp_id" type="text" value="${ emp_id}" hidden/>
			<input  id="app_no" type="text" value="${ app_no}" hidden/>
		
				<td class="label">申请单号：</td>
				
				<td class="ipt"><input id="app_no" type="text" disabled 
					value="${ app_no}" /></td>
				<td class="label">姓名：</td>
				<td class="ipt"><input id="emp_name" type="text" value="${ emp_name}" disabled/></td>
				<td class="label">申请日期：</td>
				<td class="ipt"><input id="app_date" type="text" value="${ app_date}" disabled/></td>
			</tr>
			<tr>
				<td class="label">从事专业：</td>
				<td class="ipt"><input id="profession" type="text" disabled value="${ profession}" /></td>
				<td class="label">性别：</td>
				<td class="ipt"><input id="sex_code" type="text" disabled value="${ sex_code}"/>
				</td>
				<td class="label">年龄：</td>
				<td class="ipt"><input id="age" type="text" disabled value="${ age}"/></td>
			</tr>
			
			<tr>
				<td class="label">进入医院时间：</td>
				<td class="ipt"><input id="hostime" type="text" disabled value="${ hostime}"/></td>
				<td class="label">工龄：</td>
				<td class="ipt"><input id="workage" type="text" disabled value="${ workage}"/>
				</td>
				<td class="label">政治面貌：</td>
				<td class="ipt"><input id="political_code" type="text" disabled value="${ political_code}"/></td>
			</tr>
				<tr>
				<td class="label">毕业学校：</td>
				<td class="ipt"><input id="graduation_school" type="text" disabled value="${ graduation_school}"/></td>
				<td class="label">英语等级：</td>
				<td class="ipt"><input id="english_level" type="text" disabled value="${ english_level}"/>
				</td>
			</tr>
				<tr>
				<td class="label">进修开始时间：</td>
				<td class="ipt"><input id="beg_date" type="text" value="${ beg_date}" /></td>
				<td class="label">进修时长：</td>
				<td class="ipt"><input id="duration" type="text" value="${ duration}"/>
				</td>
				<td class="label">亚专科定位：</td>
				<td class="ipt"><input id="sec_profession" type="text" value="${ sec_profession}"/></td>
			</tr>
				<tr>
				<td class="label">进修医院：</td>
				<td class="ipt"><input id="furstd_hos" type="text" value="${ furstd_hos}"/></td>
				<td class="label">是否提供住宿：</td>
				<td class="ipt"><select id="hostel" style="width: 180px;" ></select>
				</td>
				<td class="label">住宿费用：</td>
				<td class="ipt"><input id="hostel_charge" type="text" value="${ hostel_charge}"/></td>
			</tr>
		<!-- 		<tr>
				<td class="label">进修开始时间：</td>
				<td class="ipt"><input id="beg_date" type="text" /></td>
				<td class="label">进修时长：</td>
				<td class="ipt"><input id="duration" type="text" />
				</td>
				<td class="label">亚专科定位：</td>
				<td class="ipt"><input id="sec_profession" type="text" /></td>
			</tr> -->
			<tr>
			<td class="label">申请原因：</td>
			<td class="ipt"><textarea id="goal" rows="40" cols="55" >${goal}</textarea></td>
			</tr>
			<tr>
			<td class="label" >进修后拟开展项目：</td>
			</tr>
				<tr>
				<td class="label">一月计划：</td>
				<td class="ipt"><input id="plan1" type="text" value="${ plan1}"/></td>
				<td class="label">三月计划：</td>
				<td class="ipt"><input id="plan3" type="text" value="${ plan3}"/>
				</td>
				<td class="label">六月计划：</td>
				<td class="ipt"><input id="plan6" type="text" value="${ plan6}"/></td>
			</tr>
		</table>
	</div>
	<div class="button-group">
		<button id="save">保存</button>
		<button id="colse">关闭</button>
	</div>
	
</body>

</html>