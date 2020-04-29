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
	var grid, wage_plan, dept_id, emp_id,wage_item;
	$(function(){
		initSelect();
		initGrid();
		refreshGridHead(wage_plan.getValue);
		query()	
	});
	
	var initSelect = function(){
		wage_plan = $("#wage_plan").etSelect({
			showClear: false,
			url: '../wagePlanManage/wagePlanSelect.do?isCheck=false',
            onChange: function(id){
            	refreshGridHead(id);
            	query()	;
            	//$("#itemDiv").load();
            
            	reloadSelect(id);
            	
            } , 
            onInit:function(id){
              	reloadSelect(id);}
		});
		wage_item=$("#wage_item").etSelect({
			showClear: false,
		      defaultValue: 'none',
			//url: 'wageItemSelect.do?isCheck=false&wage_plan='+wage_plan.getValue(),
           
		});
		dept_id = $("#dept_id").etSelect({
			url: '../../queryHosDeptSelect.do?isCheck=false',
            defaultValue: 'none',
            onChange: query

		});
		emp_id = $("#emp_id").etSelect({
			url: '../../queryPerson.do?isCheck=false',
            defaultValue: 'none',
            onChange: query

		});
	}
	  // 加载下拉框 child_select
    function reloadSelect(id) {
    	wage_item.clearOptions();
    	wage_item.reload({
            url: 'wageItemSelect.do?isCheck=false&wage_plan='+id,
        });
    	
    }
	var columns_1 = [
		{ display: '部门名称', name: 'dept_name', width: 120, editable: false },
		{ display: '员工编号', name: 'emp_code', width: 120, editable: false},
		{ display: '姓名', name: 'emp_name', width: 120, 
			editor: {
				type: 'grid',
				columns: [
					{ display: '职工工号', name: 'emp_code', width: 120,align:"left" },
					{ display: '职工名称', name: 'emp_name', width: 120,align:"left" },
					{ display: '部门名称', name: 'dept_name', width: 120,align:"left" },
					{ display: '职工ID', name: 'emp_id', hidden: true }
				],
				width: '700px',
				height: '205px',
				dataModel: {
					url: '',
				}
			}
		}
	];
	var columns_2 = [
		{ display: '合计', name: 'wage_count', width: 120, align: "center", editable: false },
		{ display: '备注', name: 'note', width: 120 }      
	];
	var columns = columns_1.concat(columns_2);
	var initGrid = function(){
		var toolbar = {
			items: [
				{ type: 'button', label: '查询', listeners: [{click: query}], icon: 'search' },
				{ type: 'button', label: '生成', listeners: [{click: generate}], icon: 'settings' },
				{ type: 'button', label: '保存', listeners: [{click: save}], icon: 'save' },
				{ type: 'button', label: '计算', listeners: [{click: collect}], icon: 'counter' },
				{ type: 'button', label: '批量修改', listeners: [{click: updateBatch}], icon: 'add2' },
				{ type: 'button', label: '添加', listeners: [{click: add}], icon: 'add' },
				{ type: 'button', label: '删除', listeners: [{click: remove}], icon: 'delete' },
				{ type: 'button', label: '导入', listeners: [{click: importData}], icon: 'import' }
			]
		};
		
		var paramObj = {
			height: '100%',
			inWindowHeight: true,
			rowClick: function (event, ui) {},
			editable: true,
			columns: columns,
			checkbox: true,
			toolbar: toolbar
		};
		grid = $("#mainGrid").etGrid(paramObj);
	}
	
	// 刷新表头
	function refreshGridHead(plan_code){
		ajaxPostData({url:'queryWageCheckComputeHead.do?isCheck=false', data:{plan_code:plan_code}, success:function (res) {// 获取数据
			columns = columns_1.concat(res.columns, columns_2);
			columns[2].editor.type = 'grid';
			grid.option('columns', columns);
			grid.option({
				 summary: {
					 keyWordCol: 'dept_name',
					 totalColumns: res.totalColumns
				 }
			});
			
			grid.getColumns()[3].editor.dataModel.url = 'queryEmpByWagePlanEmpKind.do?isCheck=false&plan_code=' + wage_plan.getValue();
			grid.refreshView();

        }}); 
	}
	
	// 查询
	var query = function(){
		params = [
			{ name: 'plan_code', value: wage_plan.getValue() }, 
			{ name: 'dept_id', value: dept_id.getValue() },
			{ name: 'emp_id', value: emp_id.getValue() }
		];
		grid.loadData(params,'queryWageCheckComputeGrid.do?isCheck=false');
	}
	
	// 生成
	var generate = function(){
	/* 	alert("生成 TODO"); */
// 		params = [
// 			{ name: 'plan_code', value: wage_plan.getValue() }
// 		];
// 		grid.loadData(params,'generateEmpWage.do');


                    if (wage_plan.getValue() == "") {
                        $.etDialog.error('请选择方案进行生成操作！');
                        return;
                    }
                 
                 
               	 $.etDialog.confirm('是否生成工资表?', function () {
            		 ajaxPostData({
            			 url:"generateEmpWage.do",
            			 data: {
                         	'wage_plan' : wage_plan.getValue()
                         },
 	                    success: function (responseData) {
 	                    	if (responseData.state == "true") {
                                query();
                            }
 	                    }
            			 
            		 })
            		 });
                  /*   $.ligerDialog.confirm('是否生成本月工资表?', function(yes) {
                        if (yes) {
                            ajaxJsonObjectByUrl("initAccWagePay.do", 
                            	{ ParamVo: ParamVo.toString()}, 
                            	function(responseData) {
                                if (responseData.state == "true") {
                                    query();
                                }
                            });
                        }
                    }); */
	}
	
	// 保存
	var save = function(){
		var selectData = grid.selectGet();
		var paramVo = [];
		if (selectData.length == 0) {
			$.etDialog.error('请选择行');
			return;
		} else {
			$(selectData).each(function (index) {
				paramVo.push(this.rowData);
			});
		}
		ajaxPostData({
			url: "saveEmpHrWage.do",
			data: {
				plan_code: wage_plan.getValue(),
				paramVo: JSON.stringify(paramVo)
			},
			success: function(res){
				query();
			}
		});
	}
	
	// 计算
	var collect = function(){
		 if (wage_plan.getValue() == "") {
             $.etDialog.error('请选择薪资方案进行计算');
             return;
         }
			
         var formPara = { plan_code: wage_plan.getValue() } ;
         formPara=JSON.stringify(formPara)
        	 $.etDialog.confirm('是否计算工资数据?', function () {
        		 ajaxPostData({
        		 url:"collectEmpHrWage.do",
        		  data: { wage_code: wage_plan.getValue()},
                 success: function (responseData) {
                     if (responseData.is_ok == "0") {
                         $.etDialog.success(responseData.msg_text);
                         //query();
                     } else if (responseData.is_ok == "-1") {
                         $.etDialog.error(responseData.msg_text);
                         return;
                     } else if (responseData.is_ok == "100") {
                    	 $.etDialog.error(responseData.msg_text);
                         return;
                     }
                 }
        		 })
         });
	}
	
	// 批量修改
	var updateBatch = function(){
		
		$.etDialog.open({
			title: '批量修改',
			type: 1, 
			content: $("#itemDiv"), 
			width: 300, 
			height: 300,
			btn: ['确定', '取消'],
			btn1: function (index, el) {
				
				if(!wage_item.getValue()){
					$.etDialog.warn('请选择需要修改的工资项目');
					return;
				}
				ajaxPostData({
					url : 'updateItem.do',
					data : {
						wage_item: wage_item.getValue(), 
						plan_code: wage_plan.getValue(),
						item_after: $("#item_after").val()
					},
				    success: function (res) {
						$.etDialog.close(index); // 关闭弹窗
					},
				})
			}
		});
	}
	
	// 添加
	var add = function(){
		grid.addRow();
	}
	
	// 删除
	var remove = function(){
		var selectData = grid.selectGet();
		var paramVo = [];
		if (selectData.length == 0) {
			$.etDialog.error('请选择行');
			return;
		} else {
			$(selectData).each(function (index) {
				paramVo.push(this.rowData);
			});
		}
		ajaxPostData({
			url: "deleteEmpHrWage.do",
			data: {
				plan_code: wage_plan.getValue(),
				paramVo: JSON.stringify(paramVo)
			},
			success: function(res){
				query();
			}
		});
	}
	
	// 导入
	var importData = function(){
		//alert("导入 TODO");
		var column =[
		             { 'display': '部门名称', 'name': 'dept_name', "width" : "200", "require" : true },
		     		{ 'display': '员工编号', 'name': 'emp_code', "width" : "200", "require" : true }
		             ];
            	var a = null;
            	  $.post('queryWageCheckComputeHead.do?isCheck=false&plan_code='+wage_plan.getValue(), function (data) {
            		 
            		  $(data.columns).each(function ( i,v) {
           			   a = {
               				   "display": v.display,
               				   "name":v.name,
               				   "width":"200",
               				  
               		   };
            		  
            		   column.push(a);
                 }); 
                }, 'json');  
            	  var para = {"column": column}
             	 importSpreadView("/hrp/hr/salarymanagement/empWage/importData.do&plan_code="+wage_plan.getValue(), para); 

	}
	
</script>
<body>
	<div class="main">
		<table class="table-layout">
			<tr>
				<td class="label">薪资方案<span style="color:red;">*</span>：</td>
				<td class="ipt">
                	<select id="wage_plan" style="width:180px;"></select>
                </td>
               <!--  <td class="label">工资项目<font size="2" color="red">*</font>：</td>
				<td class="ipt">
					<select id="wage_item" style="width:180px"/></select>
				</td> -->
				<td class="label">部门：</td>
				<td class="ipt">
                	<select id="dept_id" style="width:180px;"></select>
                </td>
				<td class="label">职工：</td>
				<td class="ipt">
                	<select id="emp_id" style="width:180px;"></select>
                </td>
			</tr>
		</table>
	</div>
	<div id="mainGrid"></div>
	<div id="itemDiv" style="display: none">
		<table class="table-layout" id="itemTab">
			<tr>
				<td class="label">工资项目<font size="2" color="red">*</font>：</td>
				<td class="ipt">
					<select id="wage_item" style="width:180px"/></select>
				</td>
			</tr>
			<tr>
			 <td class="label">修改后：</td>
	                <td class="ipt">
	                    <input id="item_after"     type="text" />
	                </td>
			</tr>
		</table>
	</div>
</body>
</html>