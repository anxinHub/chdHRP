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
	<jsp:param value="dialog,hr.select,grid,datepicker,upload" name="plugins" />
</jsp:include>
<script>
	var the_date, dept_code, emp_id;
	var tree, grid;
	var initFrom = function() {
		year_month = $("#year_month").etDatepicker({
			onChange : query,
		});

		dept_code = $("#dept_code").etSelect({
			url : "../../queryHosDeptSelect.do?isCheck=false",
			defaultValue : "none",
			onChange : query
		});
		emp_id = $("#emp_id").etSelect({
			url : "../../queryEmpSelect.do?isCheck=false",
			defaultValue : "none",
			onChange : query
		});
	};
	var query = function() {
		var params = [ {
			name : 'year_month',
			value : year_month.getValue('yyyymm')
		}, {
			name : 'dept_id',
			value : dept_code.getValue().split('@')[1]
		}, {
			name : 'emp_id',
			value : emp_id.getValue()
		} ];
		grid.loadData(params);
	};

	var save = function() {
		var ParamVo = [];
		   var isPass = grid.validateTest({
               required: {
            	   dept_name: true,
            	   year_month: true,
            	   emp_name: true
               }
           });
           if (!isPass) {
               return;
           }
        var data = grid.selectGet();
             if (data.length == 0) {
                 $.etDialog.error('请选择行');
                 return;
             } else {
                   $(data).each(function () {
                       var rowdata = this.rowData;
                       if(rowdata.dept_code!=null){
                      	 rowdata.dept_id = rowdata.dept_code.split('@')[1]
                      }
                       ParamVo.push(rowdata);
                   }) 
                   }
           //验证重复数据
       	if (!grid.checkRepeat(
       			grid.selectGet(),
       			['year_month','dept_name','emp_name']
       	)		
       	) {
              return;
          }
   
		ajaxPostData({
			url : 'addTechDocWorkload.do',
			data : {
				paramVo : JSON.stringify(ParamVo)
			},
			success : function() {
				query()
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
				dept_id : item.rowData.dept_id,
				emp_id : item.rowData.emp_id,
				year_month : item.rowData.year_month
			});
		})
$.etDialog.confirm('确定删除?', function () {
		ajaxPostData({
			url : 'deleteTechDocWorkload.do',
			data : {
				paramVo : JSON.stringify(param)
			},
			success : function() {
				grid.deleteRows(selectData);
			}
		})
});
	};
	 //导入数据
    function putin(){
		//$("form[name=fileForm]").submit();
		var para = {
			"column" : [ {
				"name" : "dept_id",
				"display" : "科室ID",
				"width" : "200",
				"require" : true
			},{
				"name" : "emp_id",
				"display" : "职工ID",
				"width" : "200",
				"require" : true
			},{
				"name" : "year_month",
				"display" : "年月",
				"width" : "200",
				"require" : true
			},{
				"name" : "rep_mz",
				"display" : "报告人次(门诊)",
				"width" : "200"
			},{
				"name" : "rep_zy",
				"display" : "报告人次(住院)",
				"width" : "200"
			},{
				"name" : "rep_tj",
				"display" : "报告人次(体检)",
				"width" : "200"
			},{
				"name" : "rep_sum",
				"display" : "报告人次(合计)",
				"width" : "200"
			},{
				"name" : "aut_mz",
				"display" : "审核人次(门诊)",
				"width" : "200"
			},{"name" : "aut_zy",
				"display" : "审核人次(住院)",
				"width" : "200"
			},{"name" : "aut_tj",
				"display" : "审核人次(体检)",
				"width" : "200"
			},{"name" : "aut_sum",
				"display" : "审核人次(合计)",
				"width" : "200"
			},{"name" : "tech_money",
				"display" : "金额",
				"width" : "200"
			},{
				"name" : "note",
				"display" : "备注",
				"width" : "200"
			} ]

		};
		importSpreadView("/hrp/hr/privilegemanagement/workload/import.do?isCheck=false", para);
	}
	
	var putout = function() {
		exportGrid(grid);
	};

	var initGrid = function() {
		var yearEditor = getRecentYearForSelect();
		var columns = [
				{
					display : '年月',
					name : 'year_month',
					width : 120,
					editor : {
						type : 'date',
						dateFormat : 'yymm',
					},
                    editable : function(col){
                       	if(col.rowData){
                    		if(col.rowData.hos_id!=null){
                    			return false;
                    		}
                    		return true;
                    	}else{
                    		return true;
                    	}
                    }
				},
				{
					display : '科室',
					name : 'dept_name',
					width : 120,
					editor : {
						type : 'select',
						keyField : 'dept_code',
						url : '../../queryHosDeptSelect.do?isCheck=false',
						change : function(value) {
							//console.log(value);
							grid.getColumns()[3].editor.url = '../../queryEmpSelect.do?isCheck=false&dept_code='
									+ value.dept_code;
						}

					},
                    editable : function(col){
                       	if(col.rowData){
                    		if(col.rowData.hos_id!=null){
                    			return false;
                    		}
                    		return true;
                    	}else{
                    		return true;
                    	}
                    }
				}, {
					display : '姓名',
					name : 'emp_name',
					width : 120,
					editor : {
						type : 'select',
						keyField : 'emp_id',
						url : '../../queryEmpSelect.do?isCheck=false',
					},
                    editable : function(col){
                       	if(col.rowData){
                    		if(col.rowData.hos_id!=null){
                    			return false;
                    		}
                    		return true;
                    	}else{
                    		return true;
                    	}
                    }
				}, {
					display : '报告人次',/*  name: 'man_patient_num', */
					columns : [ {
						display : '报告人次(门诊)',
						name : 'rep_mz',
						width : 120
					}, {
						display : '报告人次(住院)',
						name : 'rep_zy',
						width : 120
					}, {
						display : '报告人次(体检)',
						name : 'rep_tj',
						width : 120
					}, {
						display : '报告人次(合计)',
						name : 'rep_sum',
						width : 120
					}, ]
				}, {
					display : '审核人次',
					columns : [ {
						display : '审核人次(门诊)',
						name : 'aut_mz',
						width : 120
					}, {
						display : '审核人次(住院)',
						name : 'aut_zy',
						width : 120
					}, {
						display : '审核人次(体检)',
						name : 'aut_tj',
						width : 120
					}, {
						display : '审核人次(合计)',
						name : 'aut_sum',
						width : 120
					}, ]
				}, {
					display : '金额',
					name : 'tech_money',
					width : 120
				}, {
					display : '备注',
					name : 'note',
					width : 120
				},

		];
		var paramObj = {
			height : '100%',
			inWindowHeight : true,
			checkbox : true,
			editable : true,
			dataModel : {
				url : 'queryTechDocWorkload.do'
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
				}, {
					type : 'button',
					label : '保存',
					listeners : [ {
						click : save
					} ],
					icon : 'save'
				}, {
					type : 'button',
					label : '添加',
					listeners : [ {
						click : add
					} ],
					icon : 'add'
				}, {
					type : 'button',
					label : '删除',
					listeners : [ {
						click : remove
					} ],
					icon : 'delete'
				}, {
					type : 'button',
					label : '导入',
					listeners : [ {
						click : putin
					} ],
					icon : 'import'
				},/*  {
					type : 'button',
					label : '导出',
					listeners : [ {
						click : putout
					} ],
					icon : 'export'
				}, */ ]
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
				<td class="label">日期：</td>
				<td class="ipt"><input id="year_month" type="text" /></td>
				<td class="label">科室名称：</td>
				<td class="ipt"><select id="dept_code" style="width: 180px;"></select>
				</td>
				<td class="label">职工名称：</td>
				<td class="ipt"><select id="emp_id" style="width: 180px;"></select>
				</td>
			</tr>
		</table>
	</div>
	<div id="mainGrid"></div>
</body>

</html>