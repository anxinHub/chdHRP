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
	<jsp:param value="dialog,hr.select,grid,datepicker,pageOffice " name="plugins" />
</jsp:include>
<script>
	var seq_no, med_id, duty_code, level_code;
	var grid;
	var state = "${state}";
	var initFrom = function() {
	/* 	if (' ${view}' != 0) {//置黑不能点击
		
			document.getElementById("save").disabled = true;
		} */
	
		
	}
	
	var query = function() {
		var params = [ {}, ];
		grid.loadData(params);
	};
	var save = function() {
		if(state!=0){
			$.etDialog.error('不是新建状态不能修改');
			return;
		}
		var isPass = grid.validateTest({
			required : {
				seq_no : true,
				med_name : true,
			}
		});
		if (!isPass) {
			return;
		}
		var gridAllData = grid.getAllData();
		if (!gridAllData || gridAllData.length === 0) {
			return;
		}
		var param = [];
		gridAllData.forEach(function(item){
			param.push({ 
				emp_id: $("#emp_id").val() ,
				perm_type: $("#perm_type").val(),
				seq_no : item.seq_no,
				med_id:item.med_id, 
				});
			
			
		})
		ajaxPostData({
			url : 'addHrDrugPermDetail.do',
			data : {
				paramVo : JSON.stringify(param)
			},
			success : function() {
				query();
			}
		})
	};
	var add = function() {
		grid.addRow();
	};
	var remove = function() {
		var selectData = grid.selectGet();
		if (selectData.length === 0) {
			$.etDialog.error('请选择行');
			return;
		}

		var param = [];
		selectData.forEach(function(item) {
			param.push({
				seq_no : item.rowData.seq_no,
			   emp_code: $("#emp_code").val() ,
		       perm_type: $("#perm_type").val() ,
			});
		})

		ajaxPostData({
			url : 'deleteHrDrugPermDetail.do',
			data : {
				paramVo : JSON.stringify(param)
			},
			success : function() {
				grid.deleteRows(selectData);
			}
		})
	};
	var putin = function() {
	};
	var putout = function() {
	};
	var initGrid = function() {
		var columns = [ 
		   {
			display : '序号',
			name : 'seq_no',
			editable : false,
			width : 120,
			  render: function (ui) {
				    ui.rowData.seq_no = ui.rowIndx+1
                  return     ui.rowData.seq_no;
              }
		},
		 {
			display : '药品名称',
			name : 'inv_name',
			width : 120
		
		},];
		var paramObj = {
			height : '100%',
			inWindowHeight : true,
			checkbox : true,
			editable : true,
			dataModel : {
				url : 'queryHrDrugPermDetail.do?isCheck=false',
				 postData:{
					 emp_id : $("#emp_id").val(),
					 perm_type : $("#perm_type").val(),
				    },

			},
			columns : columns,
			toolbar : {
				items : [ {
					type : 'button',
					label : '查询',
					listeners : [ {
						click : query
					} ],
					icon : 'search'
				}]
			}
		};
		grid = $("#mainGrid").etGrid(paramObj);

	};

	$(function() {
		initFrom();
		initGrid();
	})
</script>
</head>

<body>
	<div class="main">
		<table class="table-layout">
			<tr>
				<input id="perm_type" type="text" value="${perm_type}" style="display:none"/>
				<input id="emp_id" type="text" value="${emp_id}" style="display:none"/>
				<td class="label">职工工号：</td>
				<td class="ipt"><input id="emp_code" type="text" value="${emp_code}" disabled="disabled"/></td>
				<td class="label">职工姓名：</td>
				<td class="ipt"><input id="emp_name" type="text" value="${emp_name}" disabled="disabled"/></select>
				</td>
				<td class="label">科室名称：</td>
				<td class="ipt"><input id="dept_name" type="text" value="${dept_name}" disabled="disabled"/></select>
				</td>
			</tr>
		</table>
	</div>
	<div id="mainGrid"></div>
</body>

</html>