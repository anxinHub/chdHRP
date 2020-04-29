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
		app_date = $("#app_date").etDatepicker();
		beg_date = $("#beg_date").etDatepicker();
		hostel = $("#hostel").etSelect({
            options: [
                { id: 0, text: '否' },
                { id: 1, text: '是' }
            ],
            defaultValue: "none",
        });
	      emp_id = $("#emp_id").etSelect({
  			url : "../../queryEmpSelect.do?isCheck=false",
  			defaultValue : "none",
  		   onChange: function (value) {
               ajaxPostData({
                   url: 'queryHosEmp.do?isCheck=false',
                   data: {
                	   emp_id: value
                   },
                   success: function (res) {
                       user_hide_data = {
                           profession: res.profession || '',
                           emp_id: res.emp_id || '',
                           sex_code: res.sex_code || '',
                           degree_code: res.degree_code || '',
                           age: res.age || '',
                           hostime: res.hostime || '',
                           english_level: res.english_level || '',
                           workage: res.workage || '',
                           graduation_school: res.graduation_school || '',
                          
                       };
                       $("#profession").val(res.profession || '');
                       $("#sex_code").val(res.sex_code || '');
                       $("#age").val(res.age || '');
                       $("#hostime").val(res.hostime || '');
                       $("#workage").val(res.workage || '');
                       $("#graduation_school").val(res.graduation_school || '');
                       $("#political_code").val(res.political_code || '');
                       $("#english_level").val(res.english_level || '');
                   
                   },
               })
               }
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
				url : 'addFurstdApplication.do',
				data : formPara,
			    success: function (responseData) {
                  	$.etDialog.success(
							'添加成功',
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

		//关闭
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
				<td class="label">申请单号：</td>
				<td class="ipt"><input id="bill_no" type="text" disabled 
					value="系统自动生成" /></td>
				<td class="label"><font size="2" color="red">*</font>姓名：</td>
				<td class="ipt"><select id="emp_id" style="width: 180px;"></select></td>
				<td class="label"><font size="2" color="red">*</font>申请日期：</td>
				<td class="ipt"><input id="app_date" type="text" /></td>
			</tr>
			<tr>
				<td class="label">从事专业：</td>
				<td class="ipt"><input id="profession" type="text" disabled /></td>
				<td class="label">性别：</td>
				<td class="ipt"><input id="sex_code" type="text" disabled/>
				</td>
				<td class="label">年龄：</td>
				<td class="ipt"><input id="age" type="text" disabled/></td>
			</tr>
			
			<tr>
				<td class="label">进入医院时间：</td>
				<td class="ipt"><input id="hostime" type="text" disabled/></td>
				<td class="label">工龄：</td>
				<td class="ipt"><input id="workage" type="text" disabled/>
				</td>
				<td class="label">政治面貌：</td>
				<td class="ipt"><input id="political_code" type="text" disabled/></td>
			</tr>
				<tr>
				<td class="label">毕业学校：</td>
				<td class="ipt"><input id="graduation_school" type="text" disabled/></td>
				<td class="label">英语等级：</td>
				<td class="ipt"><input id="english_level" type="text" disabled/>
				</td>
			</tr>
				<tr>
				<td class="label">进修开始时间：</td>
				<td class="ipt"><input id="beg_date" type="text" /></td>
				<td class="label">进修时长：</td>
				<td class="ipt"><input id="duration" type="text" />
				</td>
				<td class="label">亚专科定位：</td>
				<td class="ipt"><input id="sec_profession" type="text" /></td>
			</tr>
				<tr>
				<td class="label">进修医院：</td>
				<td class="ipt"><input id="furstd_hos" type="text" /></td>
				<td class="label">是否提供住宿：</td>
				<td class="ipt"><select id="hostel" style="width: 180px;" ></select>
				</td>
				<td class="label">住宿费用：</td>
				<td class="ipt"><input id="hostel_charge" type="text" /></td>
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
			<td class="ipt"><textarea id="goal" rows="40" cols="55"></textarea></td>
			</tr>
			<tr>
			<td class="label" >进修后拟开展项目：</td>
			</tr>
				<tr>
				<td class="label">一月计划：</td>
				<td class="ipt"><input id="plan1" type="text" /></td>
				<td class="label">三月计划：</td>
				<td class="ipt"><input id="plan3" type="text" />
				</td>
				<td class="label">六月计划：</td>
				<td class="ipt"><input id="plan6" type="text" /></td>
			</tr>
		</table>
	</div>
	<div class="button-group">
		<button id="save">保存</button>
		<button id="colse">关闭</button>
	</div>
	
</body>

</html>