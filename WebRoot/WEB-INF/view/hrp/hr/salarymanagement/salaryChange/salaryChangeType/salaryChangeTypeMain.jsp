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
	var grid, is_stop;
	
	var initSelect = function(){
		is_stop = $("#is_stop").etSelect({
			//checkboxMode: true,
			options: [
				{ id: 0, text: "否"},
				{ id: 1, text: "是"}
			],
		        defaultValue: "none"
		});
	}
	
	var initGrid = function(){
		var toolbar = {
			items: [
				{ type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
				{ type: 'button', label: '添加', listeners: [{click: add}], icon: 'add' },
				{ type: 'button', label: '删除', listeners: [{click: remove}], icon: 'delete' }
			]
		};
		
		var columns = [
   			{ display: '薪资变动类型编码', name: 'TYPE_CODE', width: 120,
            	render : function(row){
                 	return '<a href="javascript:update(\''+row.rowData.TYPE_CODE+'\')">'+row.rowData.TYPE_CODE+'</a>';
                }	
   			},
			{ display: '薪资变动类型名称', name: 'TYPE_NAME', width: 120 },
			{ display: '备注', name: 'NOTE', width: 120,
            	render : function(row){
                	if(row.rowData.NOTE == null || row.rowData.NOTE == ""){
                        return "(空)";
                    }else{
                        return row.rowData.NOTE;
                    }
                }	
			},
			{ display: '是否停用', name: 'IS_STOP', width: 120,
            	render : function(row){
                	if(row.rowData.IS_STOP == 0){
                        return "否";
                    }else if(row.rowData.IS_STOP == 1){
                        return "是";
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
	
	var add = function(){
        parent.$.etDialog.open({
            url: 'hrp/hr/salarymanagement/salaryChange/salaryChangeTypeAddPage.do?isCheck=false',
            width: $(window).width(),
            height: $(window).height(),
            frameName :window.name,
            title: '薪资变动类型添加'
        });;
	}
	
	var update = function(id){
        parent.$.etDialog.open({
            url: 'hrp/hr/salarymanagement/salaryChange/salaryChangeTypeUpdatePage.do?isCheck=false&type_code='+id,
            width: $(window).width(),
            height: $(window).height(),
            frameName :window.name,
            title: '薪资变动类型修改'
        });;
	}
	
	var query = function(){
        params = [
              	{ name: 'key', value: $('#key').val() },
              	{ name: 'is_stop', value: is_stop.getValue() },
              ];
              grid.loadData(params,'querySalaryChangeType.do');
	}
	
	var remove = function(){
		var selectData = grid.selectGet();
        if (selectData.length === 0) {
            $.etDialog.error('请选择行');
            return;
        }
        var param = "";
        selectData.forEach(function (item) {
            param += ",'"+item.rowData.TYPE_CODE+"'";
        })
		$.etDialog.confirm('确定删除?', function () {
            ajaxPostData({
               url: 'deleteSalaryChangeType.do',
                data: { 'arrid': param.substr(1) },
                success: function () {
                    grid.deleteRows(selectData);
                }
            })
		});
	}
	
	$(function(){
		initSelect();
		initGrid();
		query();
	});
</script>
<body>
	<div class="main">
		<table class="table-layout">
			<tr>
				<td class="label">变动类型：</td>
				<td class="ipt">
                	<input type="text" id="key" style="width:180px;"/>
                </td>
				<td class="label">是否停用：</td>
				<td class="ipt">
                	<select id="is_stop" style="width:180px;"></select>
                </td>
			</tr>
		</table>
	</div>
	<div id="mainGrid"></div>
</body>
</html>