<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String path = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<jsp:include page="${path}/resource.jsp">
	<jsp:param value="hr,grid,dialog,select,datepicker" name="plugins" />
</jsp:include>
<script type="text/javascript">
	var grid, make_date, change_date, salary_change_type, dept_id, emp_id, state;
	$(function(){
		initSelect();
		initGrid();
		query();
	});
	
	var initSelect = function(){
		make_date = $("#make_date").etDatepicker({
			range : true,
 			onChange: query
		});
		
		change_date = $("#change_date").etDatepicker({
			range : true,
 			onChange: query
		});
		
		state = $("#state").etSelect({
			options: [
				{ id: 0, text: "新建"},
				{ id: 1, text: "审核"}
			],
			defaultValue: 'none',
 			onChange: query
		});
		
		change_type = $("#change_type").etSelect({
			url: 'querySalaryChangeTypeOption.do?isCheck=false',
			defaultValue: 'none',
 			onChange: query			
		});
		
		emp_id = $("#emp_id").etSelect({
			url: 'querySalaryManageEmpOption.do?isCheck=false',
			defaultValue: 'none',
 			onChange: query
		});
	}
	
	var initGrid = function(){
		var toolbar = {
			items: [
				{ type: 'button', label: '查询', listeners: [{click: query}], icon: 'search' },
				{ type: 'button', label: '添加', listeners: [{click: add}], icon: 'add' },
				{ type: 'button', label: '删除', listeners: [{click: remove}], icon: 'delete' },
				{ type: 'button', label: '审核', listeners: [{click: submit}], icon: 'submit' },
				{ type: 'button', label: '销审', listeners: [{click: cancel}], icon: 'cancel' }
			]
		};
		
		var columns = [
   			{ display: '变动类型', name: 'TYPE_NAME', width: 120 },
			{ display: '变动编号', name: 'CHANGE_CODE', width: 120,
            	render : function(row){
                	return '<a href="javascript:update(\''+row.rowData.CHANGE_CODE+'\','+row.rowData.STATE+')">'+row.rowData.CHANGE_CODE+'</a>';
                }	
			},
			{ display: '员工', name: 'EMP_NAME', width: 330 },
   			{ display: '变动说明', name: 'REMARK', width: 120 },
			{ display: '生效时间', name: 'EFFECTIVE_DATE', width: 120 },
   			{ display: '制单人', name: 'MAKER', width: 120},
			{ display: '制单日期', name: 'MAKE_DATE', width: 120 },
			{ display: '审核人', name: 'CHECKER', width: 120,
            	render : function(row){
                	if(row.rowData.CHECKER != null && row.rowData.CHECKER != ''){
                        return row.rowData.MAKER;
                    }else{
                        return '(未审核)';
                    }
                }	
			},
			{ display: '审核日期', name: 'CHECK_DATE', width: 120,
            	render : function(row){
                	if(row.rowData.CHECK_DATE != null && row.rowData.CHECK_DATE != ''){
                        return row.rowData.CHECK_DATE;
                    }else{
                        return '(未审核)';
                    }
                }	
			},
			{ display: '状态', name: 'STATE', width: 120,
            	render : function(row){
                	if(row.rowData.STATE == 0){
                        return '新建';
                    }else if(row.rowData.STATE == 1){
                        return '已审核';
                    }
                }		
			}
		];
		
		var paramObj = {
			height: '100%',
			inWindowHeight: true,
			checkbox: true,
			rowClick: function (event, ui) {},
			columns: columns,
			toolbar: toolbar
		};
		
		grid = $("#mainGrid").etGrid(paramObj);
	}
	var query = function(){
		
		params = [
	                { name: 'make_date_state', value: make_date.getValue()[0] },
	                { name: 'make_date_end', value: make_date.getValue()[1] },
	                { name: 'change_date_state', value: change_date.getValue()[0] },
	                { name: 'change_date_end', value: change_date.getValue()[1] },
	                { name: 'state', value: state.getValue() },
	                { name: 'change_type', value: change_type.getValue() },
	                { name: 'emp_id', value: $("#emp_id").text() }
	            ];
		
    	grid.loadData(params,'querySalaryChangeManage.do');
	}
	var add = function(){
        parent.$.etDialog.open({
            url: 'hrp/hr/salarymanagement/salaryChange/salaryChangeManageAddPage.do?isCheck=false',
            width: $(window).width(),
            height: $(window).height(),
            frameName :window.name,
            title: '薪资变动管理添加'
        });
	}
	var update = function(id,state){
		if(state == 1){
			$.etDialog.error("已被审核的数据无法修改!");
			return;
		}
        parent.$.etDialog.open({
            url: 'hrp/hr/salarymanagement/salaryChange/salaryChangeManageUpdatePage.do?isCheck=false&change_code='+id,
            width: $(window).width(),
            height: $(window).height(),
            frameName :window.name,
            title: '薪资变动管理修改'
        });
	}
	var remove = function(){
		var selectData = grid.selectGet();
		if(selectData != null && selectData.length > 0){
			var arrid = [];
			$.each(selectData,function(){
				arrid.push(this.rowData.CHANGE_CODE)
				
			})
			$.etDialog.confirm('确定删除?', function () {
		     	ajaxPostData({
		            url: 'deleteSalaryChangeManage.do',
		            data:{"arrid":JSON.stringify(arrid)},
		            success: function (responseData) {
		            	query();
		            },
		        })
			});
		}else{
			$.etDialog.error("请选择要删除的数据!");
		}
	}
	var submit = function(){
		
		var selectData = grid.selectGet();
		if(selectData != null && selectData.length > 0){
			var arrid = [];
			$.each(selectData,function(){
				if(this.rowData.STATE == 0){
					arrid.push(this.rowData.CHANGE_CODE)
				}
			})
			if(arrid.length == 0){
				$.etDialog.error("数据已经审核!");
				return false;
			}
	     	ajaxPostData({
	            url: 'updateSalaryChangeManageSubmit.do',
	            data:{"arrid":JSON.stringify(arrid)},
	            success: function (responseData) {
	            	query();
	            },
	        })
		}else{
			$.etDialog.error("请选择要审核的数据!");
		}
		
	}
	var cancel = function(){
		
		var selectData = grid.selectGet();
		if(selectData != null && selectData.length > 0){
			var arrid = [];
			$.each(selectData,function(){
				if(this.rowData.STATE == 1){
					arrid.push(this.rowData.CHANGE_CODE)
				}
			})
			if(arrid.length == 0){
				$.etDialog.error("数据未审核!");
				return false;
			}
	     	ajaxPostData({
	            url: 'updateSalaryChangeManageCancel.do',
	            data:{"arrid":JSON.stringify(arrid)},
	            success: function (responseData) {
	            	query();
	            },
	        })
		}else{
			$.etDialog.error("请选择要销审的数据!");
		}
		
	}

</script>
<body>
	<div class="main">
		<table class="table-layout">
			<tr>
				<td class="label">制单日期：</td>
				<td class="ipt">
                	<input type="text" id="make_date" style="width:180px;"/>
                </td>
				<td class="label">变动日期：</td>
				<td class="ipt">
                	<input type="text" id="change_date" style="width:180px;"/>
                </td>
				<td class="label">状态：</td>
				<td class="ipt">
                	<select id="state" style="width:180px;"></select>
                </td>
			</tr>
			<tr>
				<td class="label">变动类型：</td>
				<td class="ipt">
                	<input type="text" id="change_type" style="width:180px;"/>
                </td>

				<td class="label">职工：</td>
				<td class="ipt">
                	<select id="emp_id" style="width:180px;"></select>
                </td>
			</tr>
		</table>
	</div>
	<div id="mainGrid"></div>
</body>
</html>