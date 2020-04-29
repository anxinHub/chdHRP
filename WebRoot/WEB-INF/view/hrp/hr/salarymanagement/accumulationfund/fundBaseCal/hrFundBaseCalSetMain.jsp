<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String path = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="${path}/resource.jsp">
	<jsp:param value="hr,dialog,grid,select" name="plugins" />
</jsp:include>
<script type="text/javascript">
	var grid, kind_code, emp_kind_code, cal_name;
	$(function(){
		initSelect();
		initGrid();
		initBtn();
		
		query();
	});
	
	var initBtn = function(){
		// 添加
		$("#add").click(function(){
			ajaxPostData({
				url : "addHrFundBaseCal.do",
				data : {
					kind_code : emp_kind_code.getValue(),
					cal_name : $("#cal_name").val(),
					note : $("#note").val()
				},
				success : function(res) {
					query();
					
					emp_kind_code.reload({
						url : '../../wageItemCal/empKindSelect.do?isCheck=false'
			        });
					$(".edit_item").val("");
				}
			});
		});
		// 保存
		$("#save").click(function(){
			ajaxPostData({
				url : "updateHrFundBaseCal.do",
				data : {
					group_id : $("#group_id").val(),
					hos_id : $("#hos_id").val(),
					cal_id : $("#cal_id").val(),
					kind_code : emp_kind_code.getValue(),
					cal_name : $("#cal_name").val(),
					note : $("#note").val()
				},
				success : function(res) {
					query();
				}
			});
		});
		// 公式设置
		$("#calSet").click(function(){
			cal_name = $("#cal_name").val();
			parent.$.etDialog.open({
	            url: 'hrp/hr/salarymanagement/accumulationfund/fundBaseCal/fundBaseCalEditPage.do?isCheck=false',
	            width: 800,
	            height: 500,
	            frameName :window.name,
	            title: '公式设置'
	        });
		});
	}
	
	var initSelect = function(){
		// 职工分类（带全部选项）
		kind_code = $("#kind_code").etSelect({
			url : '../../wageItemCal/empKindSelect.do?isCheck=false',
			defaultValue: 'none',
			onChange: query
		});
		
		// 职工分类（带全部选项）
		emp_kind_code = $("#emp_kind_code").etSelect({
			showClear: false,
			url : '../../wageItemCal/empKindSelect.do?isCheck=false'
		});
	}
	
	var initGrid = function(){
		var toolbar = {
			items: [
				{ type: 'button', label: '查询', listeners: [{click: query}], icon: 'search' },
				{ type: 'button', label: '删除', listeners: [{click: remove}], icon: 'delete' }
			]
		};
		var columns = [
			{ display: '职工分类', name: 'kind_name', width: 140 },
			{ display: '缴费基数', name: 'pay_base', width: 120, 
				render : function(ui){
					return '<a class="editCal" row-index="' + ui.rowIndx + '">' + ui.cellData + '</a>';
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
			}
		};
		
		grid = $("#grid").etGrid(paramObj);
		
		$("#grid").on('click', '.editCal', function () {
            var rowIndex = $(this).attr('row-index');
            var currentRowData = grid.getDataInPage()[rowIndex];
            editCal(currentRowData);
        })
	}
	
	// 编辑公式
	function editCal(rowData) {
		// 右侧填充值
		$("#cal_id").val(rowData.cal_id);
		$("#group_id").val(rowData.group_id);
		$("#hos_id").val(rowData.hos_id);
		$("#note").val(rowData.note);
		$("#cal_name").val(rowData.cal_name);
		if(!rowData.kind_code){
			kind_code.setValue("全部");
		}
	}
	
	// 查询
	var query = function(){
		params = [
			{ name: 'kind_code', value: kind_code.getValue() }
		];
		grid.loadData(params,'queryHrFundBaseCal.do?isCheck=false');
	}
	
	// 删除
	var remove = function(){
		var selectData = grid.selectGet();
		var paramVo = [];
		
		if (selectData.length == 0) {
			$.etDialog.warn('请选择行');
			return;
		}else{
			$(selectData).each(function (index) {
				paramVo.push(this.rowData);
			});
		}
		ajaxPostData({
			url: "deleteHrFundBaseCal.do",
			data: {
				paramVo: JSON.stringify(paramVo)
			},
			success: function(res){
				query();
			}
		});
	}

	// 公式赋值
	function setCalName(val){
		$("#cal_name").val(val);
	}
</script>
</head>
<body style="padding: 0px; overflow: hidden;">
	<div class="container">
		<div class="right border-right" style="max-width: 460px; min-width: 460px;">
			<table class="table-layout">
				<tr>
					<td class="label leftText">职工分类：</td>
					<td class="ipt">
						<select id="kind_code" style="width:180px;">
					</td>
				</tr>
			</table>
			<div id="grid"></div>
		</div>
		<div class="center">
			<input type="hidden" name="cal_id" id="cal_id" class="edit_item"/>
			<input type="hidden" name="group_id" id="group_id" class="edit_item"/>
			<input type="hidden" name="hos_id" id="hos_id" class="edit_item"/>
			<table class="table-layout">
				<tr>
					<td class="label leftText"><span style="color:red;">*</span>职工分类：</td>
					<td class="ipt">
						<input id="emp_kind_code" type="text" style="width:180px;">
					</td>
				</tr>
				<tr>
					<td class="label leftText">缴费基数说明：</td>
					<td class="ipt" colspan="3">
						<input id="note" type="text" style="width:518px;" class="edit_item">
					</td>
				</tr>
				<tr>
					<td class="label leftText" style="vertical-align: top;">缴费基数公式：</td>
					<td class="ipt" colspan="3">
						<textarea class="edit_item" id="cal_name" style="margin: 0px; width: 518px; height: 362px; resize:none;" disabled="disabled"></textarea>
					</td>
				</tr>
			</table>
			<div class="button-group">
		        <button id="add">添加</button>
		        <button id="calSet">公式设置</button>
		        <button id="save">保存</button>
		    </div>
		</div>
	</div>
</body>
</html>