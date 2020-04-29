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
	var application_date, surgery_name, compere;
	var leftGrid;
	var initForm = function() {
		app_date = $("#rc_date").etDatepicker();
		compere = $("#compere").etSelect({
			url : "../../queryEmpSelect.do?isCheck=false",
			//defaultValue : "${compere}"
		});
		
		//主表保存
		$("#save").click(function() {
			var param = [];
			if ($("#rc_no").val() == "") {
				$.etDialog.error('记录单号不能为空');
				return;
			}
			if ($("#team_name").val() == "") {
				$.etDialog.error('团队名称不能为空');
				return;
			}
			if ($("#recorder").val() == "") {
				$.etDialog.error('记录人不能为空');
				return;
			}
			if ($("#compere").val() == "") {
				$.etDialog.error('主持人不能为空');
				return;
			}
			if ($("#rc_date").val() == "") {
				$.etDialog.error('时间不能为空');
				return;
			}
			if ($("#site").val() == "") {
				$.etDialog.error('地点不能为空');
				return;
			}
			if ($("#title").val() == "") {
				$.etDialog.error('讨论主题不能为空');
				return;
			}
			var gridAllData = leftgrid.getAllData();
        
            if(gridAllData!=null){
  		      gridAllData.forEach(function (item) {
  		          	if(item.dept_code!=null || item.dept_code!=undefined ){
  		          		dept_id =item.dept_code.split('@')[1],
  		          	emp_id =item.emp_id
  		          		param.push({dept_id,emp_id})
  		          	}
  		          })
  		      }
			var formPara = {
				rc_no : $("#rc_no").val(),
				team_name : $("#team_name").val(),
				recorder : $("#recorder").val(),
				compere : $("#compere").val(),
				rc_date : $("#rc_date").val(),
				site : $("#site").val(),
				title : $("#title").val(),
				content : $("#content").val(),
				para : JSON.stringify(param)
			};

			ajaxPostData({
				url : 'updateMDTTeamMeeting.do?isCheck=false',
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
		$("#submit").click(function() {
			if ($("#rc_no").val() == "") {
				$.etDialog.error('记录单号不能为空');
				return;
			}
			if ($("#team_name").val() == "") {
				$.etDialog.error('团队名称不能为空');
				return;
			}
			if ($("#recorder").val() == "") {
				$.etDialog.error('记录人不能为空');
				return;
			}
			if ($("#compere").val() == "") {
				$.etDialog.error('主持人不能为空');
				return;
			}
			if ($("#rc_date").val() == "") {
				$.etDialog.error('时间不能为空');
				return;
			}
			if ($("#site").val() == "") {
				$.etDialog.error('地点不能为空');
				return;
			}
			if ($("#title").val() == "") {
				$.etDialog.error('讨论主题不能为空');
				return;
			}
			var formPara = {
					rc_no : $("#rc_no").val(),
					team_name : $("#team_name").val(),
					recorder : $("#recorder").val(),
					compere : $("#compere").val(),
					rc_date : $("#rc_date").val(),
					site : $("#site").val(),
					title : $("#title").val(),
					content : $("#content").val()
				};
				$.etDialog.confirm('确定提交?', function() {
					ajaxPostData({
						url : 'confirmHrMDTTeamMeetingAdd.do?isCheck=false',
						data : formPara,
						success : function() {
							query();
						}
					})
				});
		});
		//撤回
		$("#cancel").click(function() {
			var formPara = {
					rc_no : $("#rc_no").val(),
					team_name : $("#team_name").val(),
					recorder : $("#recorder").val(),
					compere : $("#compere").val(),
					rc_date : $("#rc_date").val(),
					site : $("#site").val(),
					title : $("#title").val(),
					content : $("#content").val()
				};
			
				$.etDialog.confirm('确定撤回?', function() {
					ajaxPostData({
						url : 'reConfirmHrMDTTeamMeetingAdd.do?isCheck=false',
						data : formPara,
						success : function() {
							query();
						}
					})
				});
		});
		
		//参会人员添加行
		$("#leftAdd").click(function() {
			leftgrid.addRow();
		});
		
		 var option = compere.getItem(0);
		// compere.clearItem();
		 compere.removeOption("${emp_id}");
		
		 compere.addOptions({id: "${emp_id}", text:"${emp_name}"})
		compere.setValue("${emp_id}");

	};

	var initLeftGrid = function() {
		var columns = [ {
			display : '科室',
			name : 'dept_name',
			width : 160,
          	editor: {
                type: 'select',
                keyField: 'dept_code',
                url:'../../queryHosDeptSelect.do?isCheck=false',
                change: function (value) {
                	leftgrid.getColumns()[2].editor.url ='../../queryEmpSelect.do?isCheck=false&dept_code='+value.dept_code;
                }
            }
		},{
			display : '参会人员',
			name : 'emp_name',
			width : 160,
          	editor: {
                type: 'select',
                keyField: 'emp_id',
                url:'../../queryEmpSelect.do?isCheck=false',
            } 
		}, ];
		var paramObj = {
			height : '100%',
			inWindowHeight : true,
			checkbox : true,
			selectionModel : {
				type : 'row'
			},
			showBottom : false,
			dataModel : {
				url: 'queryMeetDetail.do?isCheck=false',
		        postData:{
					rc_no : $("#rc_no").val(),
					team_name : $("#team_name").val(),
					recorder : $("#recorder").val(),
					compere : $("#compere").val(),
					rc_date : $("#rc_date").val(),
					title : $("#title").val(),
		           },
			},
			editable : true,
			columns : columns,
		};
		leftgrid = $("#leftGrid").etGrid(paramObj);
	};

	$(function() {
		initForm();
		initLeftGrid();
	})
</script>
</head>

<body>
	<div class="main">
		<table class="table-layout" style="width: 100%;">
			<tr>
				<td class="label">记录单号：</td>
				<td class="ipt"><input  id="rc_no" type="text" disabled
					value="${rc_no }" /></td>
				<td class="label">团队名称：</td>
				<td class="ipt"><input disabled id="team_name" type="text" value="${team_name }"/></td>
				<td class="label">记录人：</td>
				<td class="ipt"><input disabled id="recorder" type="text" value="${recorder }"></td>
			</tr>
			<tr>
				<td class="label">主&nbsp持&nbsp&nbsp人：</td>
				<td class="ipt"><select disabled id="compere" style="width: 180px;"></select></td>
				<td class="label">时&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp间：</td>
				<td class="ipt"><input disabled id="rc_date" type="text" value="${rc_date }"/></td>
				<td class="label">地&nbsp&nbsp&nbsp点：</td>
				<td class="ipt"><input id="site" type="text" value="${site }"/></td>
			</tr>
			<tr>
				<td class="label">讨论主题：</td>
				<td class="ipt"><textarea disabled id="title" rows="" cols="25">${title }</textarea></td>
			</tr>
		</table>
	</div>
	<div class="button-group">
		<button id="save">保存</button>
		<button id="submit">提交</button>
		<button id="cancel">撤回</button>
	</div>
	<div class="flex-wrap">
		<div class="flex-item-1 single-block">
			<div class="flex-wrap">
				<div class="button-group align-left small">
					<button id="leftAdd">添加</button>
				</div>
				<span class="flex-item-1 align-right" style="line-height: 30px;">>>参会人员</span>
			</div>
			<div id="leftGrid"></div>
		</div>
		<div class="flex-item-1 single-block">
			<div class="flex-wrap">
				<div>
					<span style="line-height: 30px;float: right;">>>讨论内容</span><br/>
					<textarea id="content" cols="135" rows="30" style="resize:none;">${content }</textarea>
				</div>
			</div>
		</div>
	</div>
</body>

</html>