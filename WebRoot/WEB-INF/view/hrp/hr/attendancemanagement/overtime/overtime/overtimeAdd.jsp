<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<jsp:include page="${path}/resource.jsp">
	<jsp:param value="hr,dialog,select,datepicker,upload,validate,grid,time"
		name="plugins" />
</jsp:include>
<script>
	var lTree, rTree, grid, attend_code, begin_date, end_date, photo_file, formValidate,bdept_code,cdept_code,emp_code,overtime_date;
	var emp_id="";
	var user_hide_data = {};
	function initDict() {
		formValidate = $.etValidate({
			items : [ {
				el : $("#bdept_code"),
				required : true
			}, {
				el : $("#emp_code"),
				required : true
			}, {
				el : $("#cdept_code"),
				required : true
			}, {
				el : $("#attend_code"),
				required : true
			}, {
				el : $("#overtime_date"),
				required : true
			}, {
				el : $("#days"),
				required : true		
			/*}, {
				el : $("#hours"),
				required : true	
			}, {
				el : $("#begin_date"),
				required : true
			}, {
				el : $("#end_date"),
				required : true*/
			}, ]
		});
		
		overtime_date = $("#overtime_date").etDatepicker({
			defaultDate : true
		});
		attend_code = $("#attend_code").etSelect({
			url : "../../queryOvertimeKind.do?isCheck=false",
			defaultValue : "none",
		});
		
		emp_code = $("#emp_code").etSelect({
			url : "../../queryEmpSelectAttend.do?isCheck=false",
			defaultValue : "none",
			onChange : function(value) {
				bdept_code.reload({
					url : '../../queryHosDeptByEmpSelect.do?isCheck=false',
					para : {
						empId : value
					}
				})
			},
		});
		
		
		if(emp_id == ""){
			emp_id =  $("#emp_code").val();
		}
		
		bdept_code = $("#bdept_code").etSelect({
			url : "../../queryHosDeptByEmpSelect.do?isCheck=false&empId="+(emp_id ? emp_id : 0),
			defaultValue : true
		}); 

		cdept_code = $("#cdept_code").etSelect({
			url : "../../queryHosDeptSelect.do?isCheck=false",
			defaultValue : "none"
		});
		
		begin_date = laydate.render({
			elem: '#begin_date',
			type: 'time',
			format: 'HH:mm'
		});
		end_date = laydate.render({
			elem: '#end_date',
			type: 'time',
			format: 'HH:mm'
		});
		
		// 照片上传
		photo_file = $("#photo").etUpload();

		$("#save").click(function() {
			if (!formValidate.test()) {
				return;
			}
			
			//核算天数不能大于2
			if(parseFloat($("#days").val()) >= 2){
				$.etDialog.warn('加班天数需小于2');
				return;
			}
			//核算天数不能小于0
			if(parseFloat($("#days").val()) <= 0){
				$.etDialog.warn('加班天数不能小于0');
				return;
			}
			if($("#begin_date").val() && $("#end_date").val() 
					&& parseFloat($("#begin_date").val().replace(":", "")) > parseFloat($("#end_date").val().replace(":", ""))){
				$.etDialog.warn('加班开始点数不能大于加班结束点数!');
				return;
			}

			ajaxPostData({
				url : 'addOvertime.do',
				data : {
					emp_id : emp_code.getValue(),
					bdept_id : bdept_code.getValue().split('@')[1],
					cdept_id : cdept_code.getValue().split('@')[1],
					attend_code : attend_code.getValue(),
					overtime_date : overtime_date.getValue(), 
					days : $("#days").val(),
					hours : $("#hours").val(),
					begin_date : $("#begin_date").val(),
					end_date : $("#end_date").val(),
					note : $("#note").val(),
				/*  attend_xj_add:$("#attend_xj_add").val(), */
				},
			    success: function (responseData) {
					var curIndex = parent.$.etDialog.getFrameIndex(window.name);
		                            
					var parentFrameName = parent.$.etDialog.parentFrameName;
					var parentWindow = parent.window[parentFrameName];
					parentWindow.query(); 
					parent.$.etDialog.close(curIndex);
				},
			})
		})
		$("#close").click(function() {
			var curIndex = parent.$.etDialog.getFrameIndex(window.name);
			parent.$.etDialog.close(curIndex);
		})
	}

	function changeDate(){
		if (!emp_code.getValue()) {
			$.etDialog.warn('请选择职工名称!');
			return;
		};
		if(!begin_date.getValue() || !end_date.getValue()){
			return;
		}
		
		var beginDate = begin_date.getValue().split("-");
		//获取时分
		var beginhours = begin_date.getValue("HH:mm")
		var endDate = end_date.getValue().split("-");
		var endinhours = end_date.getValue("HH:mm");
		var sRDate = new Date(beginDate[0], beginDate[1], beginDate[2]);
		var eRDate = new Date(endDate[0], endDate[1], endDate[2]);
		//如果是同一天
		if (eRDate - sRDate == 0) {
			//时分格式化
			var s = '';
			var hour = beginhours.split(':')[0];
			var min = beginhours.split(':')[1];
			s = Number(hour * 3600) + Number(min * 60);
			var es = '';
			var hour = endinhours.split(':')[0];
			var min = endinhours.split(':')[1];
			es = Number(hour * 3600) + Number(min * 60);
			var endday = 0;
			//转换成小时
			var enhours = parseInt((es - s) / (60 * 60));
			//大于4小时小于8小时算半天 大于8小时算一天
			if (enhours >= 4 && enhours < 8) {
				endday += 0.5;
			} else if (enhours >= 8) {
				endday += 1;
			}
			var days = endday + parseInt((eRDate - sRDate) / (24 * 60 * 60 * 1000));
			//var hours=days*24
			//var hours=parseInt((eRDate-sRDate)/(60*60*1000));

			$("#hours").val(enhours);
			$("#days").val(days);
		}//不是同一天 
		else if (eRDate - sRDate > 0) {
			var endday1 = 0;
			//先计算第一天加班数
			//时分格式化
			var s1 = '';
			var hour = beginhours.split(':')[0];
			var min = beginhours.split(':')[1];
			s1 = Number(hour * 3600) + Number(min * 60);
			var es1 = '86400';
			var enhours1 = parseInt((es1 - s1) / (60 * 60));
			//大于4小时小于8小时算半天 大于8小时算一天
			if (enhours1 >= 4 && enhours1 < 8) {
				endday1 += 0.5;
			} else if (enhours1 >= 8) {
				endday1 += 1;
			}
			//var days1 =endday1;
			//计算最后一天加班小时
			var es2 = '';
			var hour2 = endinhours.split(':')[0];
			var min2 = endinhours.split(':')[1];
			es2 = Number(hour * 3600) + Number(min * 60);
			//转换成小时
			var enhours2 = parseInt((es2) / (60 * 60));
			//大于4小时小于8小时算半天 大于8小时算一天
			if (enhours2 >= 4 && enhours2 < 8) {
				endday1 += 0.5;
			} else if (enhours2 >= 8) {
				endday1 += 1;
			}

			var days1 = parseInt((eRDate - sRDate) / (24 * 60 * 60 * 1000)) - 1;
			//计算中间加班小时数
			var hours = days1 * 24;
			$("#hours").val(enhours1 + enhours2 + hours);
			$("#days").val(endday1 + days1);
		}
		/* 	ajaxPostData({
		        url: 'queryAccInit.do?isCheck=false',
		        data: {
		        	emp_id :emp_id.getValue(),
		        },
		  		success:function(data){
		  			
		  			if(days<data.attend_accdays){
		  				$("#days").val(days);
		  			}else{
		  				$("#days").val(data.attend_accdays);
		  			}
		  		}
			}) */
	}

	$(function() {
		initDict();
	})
</script>
</head>

<body>
	<div class="flex-wrap">
		<table class="flex-item-1 table-layout">
			<tr>
				<td class="label">加班编号：</td>
				<td class="ipt"><input value="自动生成" type="text" disabled /></td>
				
				<td class="label"><font size="2" color="red">*</font>职工：</td>
				<td class="ipt">
					<select id="emp_code" style="width: 180px;"></select>
				</td>
			</tr>
			<tr>
				<td class="label">编制科室：</td>
				<td class="ipt"><select id="bdept_code" style="width: 180px;" disabled></select>
				</td>
				
				<td class="label"><font size="2" color="red">*</font>出勤科室：</td>
				<td class="ipt"><select id="cdept_code" style="width: 180px;"></select>
				</td>
			</tr>
			<tr>
				<td class="label"><font size="2" color="red">*</font>加班类型：</td>
				<td class="ipt"><select id="attend_code" type="text" style="width: 180px"></select></td>
				
				<td class="label"><font size="2" color="red">*</font>加班日期：</td>
				<td class="ipt"><input id="overtime_date" type="text" /></td>
			</tr>
			<tr>
				<td class="label"><font size="2" color="red">*</font>核算天数：</td>
				<td class="ipt"><input id="days" type="number" placeholder="不能大于2"  /></td>
				
				<td class="label">加班小时数：</td>
				<td class="ipt"><input id="hours" type="number" /></td>
			</tr>
			<tr>
				<td class="label">加班开始点数：</td>
				<td class="ipt">
					<input id="begin_date"  type="text" placeholder="HH:mm" style="width: 180px;"/>
				</td>
				
				<td class="label">加班结束点数：</td>
				<td class="ipt">
					<input id="end_date"  type="text" placeholder="HH:mm" style="width: 180px;"/>
				</td>
			</tr>
			<tr>
				<td class="label">加班原因：</td>
				<td class="ipt" colSpan="3"><textarea name="note" id="note" cols="20"
						rows="30" style="width: 800px; height: 115px"></textarea></td>
			</tr>
		</table>
		<div class="upload-photo-form">
			<div id="photo"></div>
			<span>照片</span>
		</div>

	</div>
	<div class="button-group">
		<button id="save">保存</button>
		<button id="close">关闭</button>
	</div>
</body>

</html>