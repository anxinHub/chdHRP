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
	var dept_code, title_code, duty_code, level_code;
	var grid;
	var initFrom = function() {
		apply_date = $("#apply_date").etDatepicker({
			range : true,
			onChange : query
		});

		emp_id = $("#emp_id").etSelect({
			url : "../queryEmpSelect.do?isCheck=false",
			defaultValue : "none",
			onChange : query
		});
	};
	var query = function() {
		var params = [  { name: 'apply_date', value: apply_date.getValue()[0] || '' },
		                { name: 'end_date', value: apply_date.getValue()[1] || '' }, {
			name : 'emp_id',
			value : emp_id.getValue()
		}, ];
		grid.loadData(params,'queryHrAuthorityView.do');
	};

	var add = function() {
		grid.addRow();
	};

	var print = function() {
		if(grid.getAllData()==null){
    		$.etDialog.error("请先查询数据！");
			return;
		}
    	var heads={
        		 /* "isAuto":true,//系统默认，页眉显示页码
        		"rows": [
    	          {"cell":0,"value":"表名："+tree.getSelectedNodes()[0].name},
        		]  */}; 
    	var printPara={
            title: " 权限查看打印",//标题
            columns: JSON.stringify(grid.getPrintColumns()),//表头
            class_name: "com.chd.hrp.hr.service.medicalmanagement.HrAuthorityViewService",
            method_name: "queryAuthorityViewByPrint",
            bean_name: "hrAuthorityViewService",
            heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
            foots: '',//表尾需要打印的查询条件,可以为空 
        };
        $.each(grid.getUrlParms(),function(i,obj){
            printPara[obj.name]=obj.value;
        }); 
        //console.log(printPara);
        officeGridPrint(printPara);
		
	};
	var putout = function() {
	};
	var initGrid = function() {
		var yearEditor = getRecentYearForSelect();
		var columns = [
				{
					display : '职工工号',
					name : 'emp_code',
					width : 120
				},
				{
					display : '职工名称',
					name : 'emp_name',
					width : 120,
					editable : false,
				},
				{
					display : '处方权',
					name : 'prescription',
					width : 120,
					editable : false,
					render : function(ui) {
						if (!ui.cellData) {
							ui.rowData.prescription = '0';
						}
						var isChecked = ui.cellData == 1 ? 'checked' : '';
						var rowIndex = ui.rowIndx;
						var checkBoxHtml = '<input class="isWriteInGrid" rowIndex="' + rowIndex + '" type="checkbox" ' + isChecked + ' />';
						return checkBoxHtml;
					}

				},
				{
					display : '限制药品明细',
					name : 'drugPermDetail',
					width : 120,
					editable : false,
					render : function(ui) {
						var updateHtml = '<a class="openUpdate" row-index="' + ui.rowIndx + '">查看  </a>'
						return updateHtml;
					}
				},
				{
					display : '毒麻精处方权',
					name : 'venom',
					editable : true,
					width : 120,
					editable : false,
					render : function(ui) {
						if (!ui.cellData) {
							ui.rowData.venom = '0';
						}
						var isChecked = ui.cellData == 1 ? 'checked' : '';
						var rowIndex = ui.rowIndx;
						var checkBoxHtml = '<input class="isWriteInGrid" rowIndex="' + rowIndex + '" type="checkbox" ' + isChecked + ' />';
						return checkBoxHtml;
					}
				},
				{
					display : '特殊药品权限',
					name : 'special',
					editable : true,
					width : 120,
					editable : false,
					render : function(ui) {
						if (!ui.cellData) {
							ui.rowData.special = '0';
						}
						var isChecked = ui.cellData == 1 ? 'checked' : '';
						var rowIndex = ui.rowIndx;
						var checkBoxHtml = '<input class="isWriteInGrid" rowIndex="' + rowIndex + '" type="checkbox" ' + isChecked + ' />';
						return checkBoxHtml;
					}
				},
				{
					display : '抗菌药物处方权',
					columns : [
							{
								display : '普通级',
								name : 'generalLevel',
								width : 120,
								editable : false,
								render : function(ui) {
									if (!ui.cellData) {
										ui.rowData.generalLevel = '0';
									}
									var isChecked = ui.cellData == 1 ? 'checked'
											: '';
									var rowIndex = ui.rowIndx;
									var checkBoxHtml = '<input class="isWriteInGrid" rowIndex="' + rowIndex + '" type="radio" ' + isChecked + ' />';
									return checkBoxHtml;
								}
							},
							{
								display : '限制级',
								name : 'limitLevel',
								width : 120,
								editable : false,
								render : function(ui) {
									if (!ui.cellData) {
										ui.rowData.limitLevel = '0';
									}
									var isChecked = ui.cellData == 1 ? 'checked'
											: '';
									var rowIndex = ui.rowIndx;
									var checkBoxHtml = '<input class="isWriteInGrid" rowIndex="' + rowIndex + '" type="radio" ' + isChecked + ' />';
									return checkBoxHtml;
								}
							},
							{
								display : '特殊级',
								name : 'specialLevel',
								width : 120,
								editable : false,
								render : function(ui) {
									if (!ui.cellData) {
										ui.rowData.specialLevel = '0';
									}
									var isChecked = ui.cellData == 1 ? 'checked'
											: '';
									var rowIndex = ui.rowIndx;
									var checkBoxHtml = '<input class="isWriteInGrid" rowIndex="' + rowIndex + '" type="radio" ' + isChecked + ' />';
									return checkBoxHtml;
								}
							},

					]
				}, {
					display : '备注',
					name : 'note',
					editable : false,
					width : 120
				}, ];
		var paramObj = {
			height : '100%',
			inWindowHeight : true,
			checkbox : true,
			editable : true,
			dataModel : {
				//url : ''
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
				}, 
	              {
					type : 'button',
					label : '打印',
					listeners : [ {
						click : print
					} ],
					icon : 'print'
				} ]
			}
		};
		grid = $("#mainGrid").etGrid(paramObj);

		$("#mainGrid")
				.on(
						'click',
						'.openUpdate',
						function() {
							var rowIndex = $(this).attr('row-index');
							var currentRowData = grid.getAllData()[rowIndex];
							if (!currentRowData.emp_id) {
								$.etDialog.error('请选择员工');
							} else {
								parent.$.etDialog.open({
											url : "hrp/hr/privilegemanagement/hrAuthorityViewDetailPage.do?isCheck=false&emp_id="
													+ currentRowData.emp_id
													+ '&emp_code='
													+ currentRowData.emp_code
													+ '&view=' + 1,
											isMax : true,
										})
							}
						})
	};

	$(function() {
		initFrom();
		initGrid();
		query();
	})
</script>
</head>

<body>
	<div class="main">
		<table class="table-layout">
			<tr>
				<td class="label">日期范围：</td>
				<td class="ipt"><input id="apply_date" type="text" /></td>
				<td class="label">职工姓名：</td>
				<td class="ipt"><select id="emp_id" style="width: 180px;"></select>
				</td>

			</tr>
		</table>
	</div>
	<div id="mainGrid"></div>
</body>

</html>