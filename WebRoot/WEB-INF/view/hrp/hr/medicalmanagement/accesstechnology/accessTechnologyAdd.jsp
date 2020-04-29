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
	var application_date, surgery_name;
	var leftGrid, RightGrid;
	// var formValidate;

	// var initValidate = function () {
	//     formValidate = $.etValidate({
	//         items: [
	//             { el: $("#"), required: true },
	//         ]
	//     });
	// };
	var initForm = function() {
		app_date = $("#app_date").etDatepicker();
		emp_id = $("#emp_id").etSelect({
			url : "../../queryEmpSelect.do?isCheck=false",
			defaultValue : "none",
		});
		app_type = $("#app_type").etSelect({
			options : [ {
				id : 01,
				text : '初次申请'
			}, {
				id : 02,
				text : '重新申请'
			} ],
			defaultValue : "none",

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
			if ($("#oper_name").val() == "") {
				$.etDialog.error('手术名称不能为空');
				return;
			}
			if ($("#app_type").val() == "") {
				$.etDialog.error('申请类别不能为空');
				return;
			}
			var formPara = {
				app_no : $("#app_no").val(),
				app_date : $("#app_date").val(),
				emp_id : $("#emp_id").val(),
				oper_name : $("#oper_name").val(),
				app_type : $("#app_type").val(),
				note : $("#note").val()
			};


			var isPass =rightgrid.validateTest({
				required : {
					ref_oper_name : true,
				}
			});
			if (!isPass) {
				return;
			}
			var gridAllData = rightgrid.getAllData();
			if (gridAllData != null) {
				gridAllData.forEach(function(item) {
					item.app_no = $("#app_no").val();
					item.emp_id = $("#emp_id").val();
					item.oper_name = $("#oper_name").val();
				})
				
			}
			
			
			
			var gridLeftAllData = leftgrid.getAllData();
			if ( gridLeftAllData != null) {
				gridLeftAllData.forEach(function(item) {
					item.app_no = $("#app_no").val();
					item.emp_id = $("#emp_id").val();
					item.oper_name = $("#oper_name").val();
				})
			}
		
			ajaxPostData({
				url : 'addAccessTechnology.do',
				data : {
					paramVo : JSON.stringify(gridAllData),
				    paramLeftVo : JSON.stringify(gridLeftAllData),
				app_no : $("#app_no").val(),
				app_date : $("#app_date").val(),
				emp_id : $("#emp_id").val(),
				oper_name : $("#oper_name").val(),
				app_type : $("#app_type").val(),
				note : $("#note").val()
				},
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

		
		//提交
		$("#submit").click(function() {
			if ($("#app_no").val() == "") {
				$.etDialog.error('申请单号不能为空');
				return;
			}
			if ($("#app_date").val() == "") {
				$.etDialog.error('申请日期不能为空');
				return;
			}
			if ($("#emp_id").val() == "") {
				$.etDialog.error('申请人不能为空');
				return;
			}
			if ($("#oper_name").val() == "") {
				$.etDialog.error('手术名称不能为空');
				return;
			}
			if ($("#app_type").val() == "") {
				$.etDialog.error('申请类别不能为空');
				return;
			}
			var formPara = {
					app_no : $("#app_no").val(),
					app_date : $("#app_date").val(),
					emp_id : $("#emp_id").val(),
					oper_name : $("#oper_name").val(),
					app_type : $("#app_type").val(),
					note : $("#note").val()
				};
	/* 		ajaxPostData({
				url : 'confirmHrTechRec.do?isCheck=false',
				data : formPara,
				success : function() {
					query();
				}
			}) */
		
				$.etDialog.confirm('确定提交?', function() {
					ajaxPostData({
						url : 'confirmHrAccessTechnologyAdd.do',
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
					app_no : $("#app_no").val(),
					app_date : $("#app_date").val(),
					emp_id : $("#emp_id").val(),
					oper_name : $("#oper_name").val(),
					app_type : $("#app_type").val(),
					note : $("#note").val()
				};
			
				$.etDialog.confirm('确定撤回?', function() {
					ajaxPostData({
						url : 'reConfirmHrHrAccessTechnologyAdd.do',
						data : formPara,
						success : function() {
							query();
						}
					})
				});
		

		});
		$("#colse").click(function() {

         	var curIndex = parent.$.etDialog.getFrameIndex(window.name);
           	parent.$.etDialog.close(curIndex);
          
		})
		//    技术准入开展情况删除
		$("#leftRemove").click(function() {

			var selectData = leftgrid.selectGet();
			if (selectData.length === 0) {
				$.etDialog.error('请选择行');
				return;
			}
			var param = [];
			selectData.forEach(function(item) {
				param.push({
					case_no : item.rowData.case_no,
					patient_name : item.rowData.patient_name,
					app_no : $("#app_no").val(),
					emp_id : $("#emp_id").val(),
					oper_name : $("#oper_name").val()
				});
			})
			ajaxPostData({
				url : 'deleteHrTechExec.do',
				data : {
					paramVo : JSON.stringify(param)
				},
				success : function() {
					leftgrid.deleteRows(selectData);
				}
			})

		});
		//  技术准入开展情况保存
		$("#leftSave").click(function() {

			var isPass = leftgrid.validateTest({
				required : {
					case_no : true,
					patient_name : true,

				}
			});
			if (!isPass) {
				return;
			}
			var gridAllData = leftgrid.getAllData();
			gridAllData.forEach(function(item) {
				item.app_no = $("#app_no").val();
				item.emp_id = $("#emp_id").val();
				item.oper_name = $("#oper_name").val();
			})
			if (!gridAllData || gridAllData.length === 0) {
				return;
			}

			ajaxPostData({
				url : 'addTechExec.do',
				data : {
					paramVo : JSON.stringify(gridAllData),
					app_no : $("#app_no").val(),
					emp_id : $("#emp_id").val(),
					oper_name : $("#oper_name").val()
				},
				success : function() {
					//leftgrid.deleteRows();
				}
			})
		});
		// 技术准入开展情况添加行
		$("#leftAdd").click(function() {
			leftgrid.addRow();
		});
		//技术准入相关联手术删除
		$("#rightRemove").click(function() {


			var selectData =rightgrid.selectGet();
			if (selectData.length === 0) {
				$.etDialog.error('请选择行');
				return;
			}
			var param = [];
			selectData.forEach(function(item) {
				param.push({
					ref_oper_name : item.rowData.ref_oper_name,
					app_no : $("#app_no").val(),
					emp_id : $("#emp_id").val(),
					oper_name : $("#oper_name").val()
				});
			})
			ajaxPostData({
				url : 'deleteHrTechRef.do',
				data : {
					paramVo : JSON.stringify(param)
				},
				success : function() {
					rightgrid.deleteRows(selectData);
				}
			})

		});
		// 技术准入相关联手术保存
		$("#rightSave").click(function() {

			var isPass =rightgrid.validateTest({
				required : {
					ref_oper_name : true,
				}
			});
			if (!isPass) {
				return;
			}
			var gridAllData = rightgrid.getAllData();
			gridAllData.forEach(function(item) {
				item.app_no = $("#app_no").val();
				item.emp_id = $("#emp_id").val();
				item.oper_name = $("#oper_name").val();
			})
			if (!gridAllData || gridAllData.length === 0) {
				return;
			}

			ajaxPostData({
				url : 'addTechRef.do',
				data : {
					paramVo : JSON.stringify(gridAllData),
					app_no : $("#app_no").val(),
					emp_id : $("#emp_id").val(),
					oper_name : $("#oper_name").val()
				},
				success : function() {
					//leftgrid.deleteRows();
				}
			})
		});
		//技术准入相关联手术添加行
		$("#rightAdd").click(function() {
			rightgrid.addRow();
		});
	};

	var initLeftGrid = function() {
		var columns = [ {
			display : '病案号',
			name : 'case_no',
			width : 160

		}, {
			display : '患者姓名',
			name : 'patient_name',
			width : 160

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
			// url: '.do?isCheck=false'
			},
			editable : true,
			columns : columns,
		};
		leftgrid = $("#leftGrid").etGrid(paramObj);
	};
	var initRightGrid = function() {
		var columns = [ {
			display : '关联手术名称',
			name : 'ref_oper_name',
			width : 300
		} ];
		var paramObj = {
			height : '100%',
			inWindowHeight : true,
			checkbox : true,
			selectionModel : {
				type : 'row'
			},
			showBottom : false,
			dataModel : {
			// url: '.do?isCheck=false'
			},
			editable : true,
			columns : columns,
		};
		rightgrid = $("#rightGrid").etGrid(paramObj);
	};

	$(function() {
		// initValidate();
		initForm();
		initLeftGrid();
		initRightGrid();
	})
</script>
</head>

<body>
	<div class="main">
		<table class="table-layout" style="width: 100%;">
			<tr>
				<td class="label">申请单号：</td>
				<td class="ipt"><input id="app_no" type="text" disabled
					value="${app_no }" /></td>
				<td class="label">申请日期：</td>
				<td class="ipt"><input id="app_date" type="text" /></td>
				<td class="label">职工名称：</td>
				<td class="ipt"><select id="emp_id" style="width: 180px;"></select></td>
			</tr>
			<tr>
				<td class="label">手术名称：</td>
				<td class="ipt"><input id="oper_name" type="text" /></td>
				<td class="label">申请类别：</td>
				<td class="ipt"><select id="app_type" style="width: 180px;"></select>
				</td>
				<td class="label">备注：</td>
				<td class="ipt"><input id="note" type="text" /></td>
			</tr>
		</table>
	</div>
	<div class="button-group">
		<button id="save">保存</button>
		<button id="submit">提交</button>
		<button id="cancel">撤回</button>
		<button id="colse">关闭</button>
	</div>
	<div class="flex-wrap">
		<div class="flex-item-1 single-block">
			<div class="flex-wrap">
				<div class="button-group align-left small">
					<button id="leftRemove">删除</button>
					<!-- <button id="leftSave">保存</button> -->
					<button id="leftAdd">添加</button>
				</div>
				<span class="flex-item-1 align-right" style="line-height: 30px;">>>开展情况</span>
			</div>
			<div id="leftGrid"></div>
		</div>
		<div class="flex-item-1 single-block">
			<div class="flex-wrap">
				<div class="button-group align-left small">
					<button id="rightRemove">删除</button>
					<!-- <button id="rightSave">保存</button> -->
					<button id="rightAdd">添加</button>
				</div>
				<span class="flex-item-1 align-right" style="line-height: 30px;">>>关联手术</span>
			</div>
			<div id="rightGrid"></div>
		</div>
	</div>
</body>

</html>