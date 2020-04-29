<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String path = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<jsp:include page="${path}/resource.jsp">
	<jsp:param value="hr,grid,dialog,select" name="plugins" />
</jsp:include>
<script type="text/javascript">
	var grid, plan_code, item_code, kind_code;
	$(function(){
		initSelect();
		initGrid();
		query();
	});
	
	var initSelect = function(){
		// 薪资方案
		plan_code = $("#plan_code").etSelect({
			showClear: false,
		    defaultValue: 'none',
			url: '../wagePlanManage/wagePlanSelect.do?isCheck=false',
            onChange: function(id){
            	var itemCode = item_code.getValue();
            	if(itemCode){
            		item_code.clearItem();
            	}else{
            		query();
            	}
            	item_code.reload({
                    url: '../wagePlanManage/wageItemSelect.do?isCheck=false&plan_code='+id
                });
            }
		});
		// 工资项目
		item_code = $("#item_code").etSelect({
			url: '../wagePlanManage/wageItemSelect.do?isCheck=false&plan_code='+plan_code.getValue(),
            defaultValue: 'none',
            onChange: query
		});
		// 职工分类
		kind_code = $("#kind_code").etSelect({
			url: '../../queryHrEmpKindSelect.do?isCheck=false',
            defaultValue: 'none',
            onChange: query
		});
	}
	
	var initGrid = function(){
		var toolbar = {
			items: [
				{ type: 'button', label: '查询', listeners: [{click: query}], icon: 'search' },
				{ type: 'button', label: '添加', listeners: [{click: add}], icon: 'add' },
				{ type: 'button', label: '删除', listeners: [{click: remove}], icon: 'delete' }
			]
		};
		var columns = [
			{ display: '工资项编码', name: 'item_code', width: 120,
				render : function(ui){
					return '<a class="openUpdate" row-index="' + ui.rowIndx + '">' + ui.cellData + '</a>';
				}
			},
			{ display: '工资项名称', name: 'item_name', width: 120 },
			{ display: '职工类别', name: 'kind_name', width: 120 },
			{ display: '取值方法', name: 'item_cal_cn', width: 120 },
			{ display: '薪资标准表', name: 'stan_name', width: 120 }/* ,
			{ display: '备注', name: 'note', width: 120 } */
		];
		
		var paramObj = {
			height: '100%',
			inWindowHeight: true,
			rowClick: function (event, ui) {},
			editable: true,
			columns: columns,
			checkbox: true,
			toolbar: toolbar,
		};
		
		grid = $("#mainGrid").etGrid(paramObj);
		$("#mainGrid").on('click', '.openUpdate', function () {
            var rowIndex = $(this).attr('row-index');
            var currentRowData = grid.getDataInPage()[rowIndex];
            openUpdate(currentRowData);
        })
	}
	
	// 查询
	var query = function(){
		params = [
			{ name: 'plan_code', value: plan_code.getValue() }, 
			{ name: 'item_code', value: item_code.getValue() },
			{ name: 'kind_code', value: kind_code.getValue() }
		];
		grid.loadData(params,'queryHrWageItemCal.do?isCheck=false');
	}
	
	// 添加
	var add = function(){
		parent.$.etDialog.open({
            url: 'hrp/hr/salarymanagement/wageItemCal/hrWageItemCalAddPage.do?isCheck=false',
            width: 1000,
            height: 650,
            frameName :window.name,
            title: '工资项取值方法添加'
        });
	}
	
	// 打开编辑页面
	var openUpdate = function(rowData){
		parent.$.etDialog.open({
            url: 'hrp/hr/salarymanagement/wageItemCal/hrWageItemCalUpdatePage.do?isCheck=false&cal_id=' + rowData.cal_id,
            width: 1000,
            height: 650,
            frameName :window.name,
            title: '工资项取值方修改'
        });
	}
	
	// 删除
	var remove = function(){
		var paramVo = [];
		var selectData = grid.selectGet();
		if(selectData.length == 0){
			$.etDialog.error('请选择行');
			return;
		}else{
			$(selectData).each(function(){
				paramVo.push(this.rowData);
			});
		}
		
		$.etDialog.confirm('确定要删除?', function(){
			ajaxPostData({
				url : 'deleteHrWageItemCal.do',
				data : {
					paramVo : JSON.stringify(paramVo)
				},
				success : function(res){
					query();
				}
			});
		});
	}
	
</script>
<body>
	<div class="main">
		<table class="table-layout">
			<tr>
				<td class="label">薪资方案<span style="color:red;">*</span>：</td>
				<td class="ipt">
                	<select id="plan_code" style="width:180px;"></select>
                </td>
				<td class="label">工资项目：</td>
				<td class="ipt">
                	<select id="item_code" style="width:180px;"></select>
                </td>
				<td class="label">职工分类：</td>
				<td class="ipt">
                	<select id="kind_code" style="width:180px;"></select>
                </td>
			</tr>
		</table>
	</div>
	<div id="mainGrid"></div>
</body>
</html>