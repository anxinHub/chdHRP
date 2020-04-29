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
	var leftGrid, rightGrid;
	var wage_plan;
	var planCode;
	$(function() {
		initSelect();
		initLeftGrid();
		initRightGrid();
		initClick();
		queryLeftGrid();
	});
	
	// 初始化下拉选
	var initSelect = function(){
		wage_plan = $("#wage_plan").etSelect({
			showClear: false,
			url:'wagePlanSelect.do?isCheck=false'
		});
	}
	
	// 初始化页面单击事件
	var initClick = function(){
		$("#state").click(function(){
			queryLeftGrid();
		});
	}
	
	// 薪资方案表格
	var initLeftGrid = function(){
		var toolbar = {
			items: [
				{ type: 'button', label: '查询', listeners: [{click: queryLeftGrid}], icon: 'search' },
				{ type: 'button', label: '添加', listeners: [{click: addPlan}], icon: 'add' },
				{ type: 'button', label: '删除', listeners: [{click: deletePlan}], icon: 'delete' }
			]
		};
		var columns = [
			{ display: '方案编号', name: 'plan_code', width: 140,
				render : function(ui){
					var reHtml = ui.cellData;
					if(ui.rowData.state == 0){
						reHtml = '<span style="color:red;">' + ui.cellData + '</span>';
					}
					
					reHtml = '<a class="openUpdatePlan" row-index="' + ui.rowIndx + '">' + reHtml + '</a>';
					return reHtml;
				}
			},
			{ display: '方案名称', name: 'plan_name', width: 140,
				render : function(ui){
					if(ui.rowData.state == 0){
						return '<span style="color:red;">' + ui.cellData + '</span>';
					}
				}
			}
		];
		
		var paramObj = {
			height: '100%',
			inWindowHeight: true,
			checkbox: true,
			columns: columns,
			toolbar: toolbar,
			rowClick: function (event, ui) {
				planCode = ui.rowData.plan_code;
				$("#plan_code").val(ui.rowData.plan_name);
				queryRightGrid();
			}
		};
		
		leftGrid = $("#leftGrid").etGrid(paramObj);
		
        $("#leftGrid").on('click', '.openUpdatePlan', function () {
            var rowIndex = $(this).attr('row-index');
            var currentRowData = leftGrid.getDataInPage()[rowIndex];
            openUpdatePlan(currentRowData);
        })
	}
	
	var openUpdatePlan = function(rowData){
		parent.$.etDialog.open({
            url: 'hrp/hr/salarymanagement/wagePlanManage/wagePlanUpdatePage.do?isCheck=false&plan_code=' + rowData.plan_code,
            width: 800,
            height: 500,
            frameName :window.name,
            title: '薪资方案修改'
        });
	}
	
	// 薪资方案添加
	var addPlan = function(){
		parent.$.etDialog.open({
            url: 'hrp/hr/salarymanagement/wagePlanManage/wagePlanAddPage.do?isCheck=false',
            width: 800,
            height: 500,
            frameName :window.name,
            title: '薪资方案添加'
        });
	}
	
	// 薪资方案删除
	var deletePlan = function(){
		var paramVo = [];
		var selectData = leftGrid.selectGet();
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
				url : 'deleteWagePlan.do',
				data : {
					paramVo : JSON.stringify(paramVo)
				},
				success : function(res){
					queryLeftGrid();
				}
			});
		});
	}
	
	// 薪资方案表格查询
	var queryLeftGrid = function(){
		var param = [
			{ name: "plan_name", value: $("#plan_name").val() },
			{ name: "state", value: $("#state:checked").val() }
		];
		
		leftGrid.loadData(param, "queryWagePlan.do");
	}
	
	// 工资项表格
	var initRightGrid = function(){
		var toolbar = {
			items: [
				{ type: 'button', label: '查询', listeners: [{click: queryRightGrid}], icon: 'search' },
				{ type: 'button', label: '添加', listeners: [{click: addItem}], icon: 'add' },
				{ type: 'button', label: '删除', listeners: [{click: deleteItem}], icon: 'delete' },
				{ type: 'button', label: '复制', listeners: [{click: copyPlan}], icon: 'copy' },
				{ type: 'button', label: '导入', listeners: [{click: importData}], icon: 'import' },
				{ type: 'button', label: '取值方法设置', listeners: [{click: getVelueSet}], icon: 'copy' }
			]
		};
		
		var columns = [
			{ display: '工资项编号', name: 'item_code', width: 80,
				render : function(ui){
					return '<a class="openUpdateItem" row-index="' + ui.rowIndx + '">' + ui.cellData + '</a>';
				}
			},
			{ display: '工资项名称', name: 'item_name', width: 120 },
			{ display: '工资类型', name: 'type_name', width: 120 },
			{ display: '工资项性质', name: 'nature_name', width: 120 },
			{ display: '数据类型', name: 'data_type_cn', width: 80 },
			{ display: '是否参与合计', name: 'is_sum_cn', width: 80 },
			{ display: '是否停用', name: 'is_stop_cn', width: 60 },
			{ display: '备注', name: 'note', width: 140 },
		];
		
		var paramObj = {
			height: '100%',
			inWindowHeight: true,
			checkbox: true,
			rowClick: function (event, ui) {},
			columns: columns,
			toolbar: toolbar
		};
		
		rightGrid = $("#rightGrid").etGrid(paramObj);
		
		$("#rightGrid").on('click', '.openUpdateItem', function () {
            var rowIndex = $(this).attr('row-index');
            var currentRowData = rightGrid.getDataInPage()[rowIndex];
            openUpdateItem(currentRowData);
        })
	}
	
	var openUpdateItem = function(rowData){
		parent.$.etDialog.open({
            url: 'hrp/hr/salarymanagement/wagePlanManage/wageItemUpdatePage.do?isCheck=false&item_code=' + rowData.item_code + '&plan_code=' + rowData.plan_code,
            width: 650,
            height: 400,
            frameName :window.name,
            title: '工资项修改'
        });
	}
	
	// 工资项查询
	var queryRightGrid = function(){
		if(!planCode){
			$.etDialog.warn('请选择一个薪资方案');
			return;
		}
		var param = [
 			{ name: "plan_code", value: planCode },
 			{ name: "item_name", value: $("#item_name").val() }
 		];
 		
 		rightGrid.loadData(param, "queryWageItem.do?isCheck=false");
	}
	
	// 工资项增加
	var addItem = function(){
		if(!planCode){
			$.etDialog.warn('请选择一个薪资方案');
			return;
		}
		parent.$.etDialog.open({
            url: 'hrp/hr/salarymanagement/wagePlanManage/wageItemAddPage.do?isCheck=false&plan_code=' + planCode,
            width: 650,
            height: 400,
            frameName :window.name,
            title: '工资项添加'
        });
	}
	
	// 工资项删除
	var deleteItem = function(){
		var paramVo = [];
		var selectData = rightGrid.selectGet();
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
				url : 'deleteWageItem.do',
				data : {
					paramVo : JSON.stringify(paramVo)
				},
				success : function(res){
					queryRightGrid();
				}
			});
		});
	}
	
	// 复制（选择薪资方案复制）
	var copyPlan = function(){
		if(!planCode){
			$.etDialog.warn('请选择一个薪资方案');
			return;
		}
		
		$.etDialog.open({
			title: '选择薪资方案',
			type: 1,
			content: $("#itemDiv"),
			width: 300,
			height: 300,
			btn: ['确定', '取消'],
			btn1: function (index, el) {
				ajaxPostData({
					url : 'copyWagePlan.do',
					data : {
						plan_code: wage_plan.getValue(),
						target_plan_code: planCode
					},
					delayCallback:true,
					success : function(res){
						queryRightGrid();
						$.etDialog.close(index);
					}
				});
		    }
		});
	}
	
	// 导入
	var importData = function(){
		if(!planCode){
			$.etDialog.warn('请选择一个薪资方案');
			return;
		}
		var para = {
			"plan_code" : planCode,
   			"column" : [
				{ "name" : "item_code", "display" : "工资项编号", "width" : "200", "require" : true },
				{ "name" : "item_name", "display" : "工资项名称", "width" : "200", "require" : true },
				{ "name" : "item_type", "display" : "工资项类型", "width" : "200", "require" : true},
				{ "name" : "item_nature", "display" : "工资项性质", "width" : "200", "require" : true},
				{ "name" : "data_type", "display" : "数据类型", "width" : "200", "require" : true},
				{ "name" : "is_sum", "display" : "是否参与合计", "width" : "200", "require" : true},
				{ "name" : "is_stop", "display" : "是否停用", "width" : "200", "require" : true},
				{ "name" : "note", "display" : "备注", "width" : "200" }
   			]
   		};
   		importSpreadView("/hrp/hr/salarymanagement/wagePlanManage/importHrWageItemData.do?isCheck=false", para, queryRightGrid);
	}
	
	// 取值方法设置（页面跳转）
	var getVelueSet = function(){
		var url = 'hrp/hr/salarymanagement/wageItemCal/hrWageItemCalMainPage.do?isCheck=false';
		var tabid = parent.$("#accordion1").find("ul li[url$='"+url+"']").attr("tabid");
		
        if (!tabid) {
            tabid = new Date().getTime();
            parent.$("li[url$='"+url+"']").attr("tabid", tabid);
        }
        parent.f_addTab(tabid, "工资项取值方法", url);
	}
</script>
<body>
	<div class="container">
		<div class="right border-right" style="max-width: 366px; min-width: 366px;">
			<table class="table-layout">
				<tr>
					<td class="label leftText">薪资方案：</td>
					<td class="ipt">
						<input id="plan_name" type="text">
					</td>
					<td class="ipt">
						<input type="checkbox" id="state" name="state" value="1" checked="checked"/>
						<label>只显示启用</label>
					</td>
				</tr>
			</table>
			<div id="leftGrid"></div>
		</div>
		<div class="center">
			<table class="table-layout">
				<tr>
					<td class="label">工资项：</td>
					<td class="ipt"><input id="item_name" type="text"></td>
					<td class="label">方案：</td>
					<td class="ipt"><input id="plan_code" type="text" disabled="disabled"></td>
				</tr>
			</table>
			<div id="rightGrid"></div>
		</div>
	</div>
	
	<div id="itemDiv" style="display: none">
		<table class="table-layout">
			<tr>
				<td class="label">薪资方案：</td>
				<td class="ipt">
					<select id="wage_plan" type="text" style="width:160px"/>
				</td>
			</tr>
		</table>
	</div>
	
</body>
</html>