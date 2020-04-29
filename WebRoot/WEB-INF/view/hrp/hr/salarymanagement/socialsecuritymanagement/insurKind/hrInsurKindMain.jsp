<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String path = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="${path}/resource.jsp">
	<jsp:param value="hr,dialog,grid,select" name="plugins" />
</jsp:include>
<script>
	var grid;
	$(function() {
		//initFrom();
		initGrid();
		query();
	})
	
// 	var initFrom = function() {
		
// 	}

	var initGrid = function() {
		var columns = [
			{ display : '险种编码', name : 'insure_code', width : 80,
				editable : function (ui){
					if(ui.rowData && ui.rowData.group_id){
						return false;
					}else{
						return true;
					}
				}
			},
			{ display : '险种名称', name : 'insure_name', width : 240 },
			{ display : '是否内置', name : 'is_innr_cn', width : 120,
				editor : {
					type : 'select',
				    keyField : 'is_innr',
					source : [
						{ label: '否', id : '0' },
						{ label: '是', id : '1' }
					]
				}
			},
			{ display : '是否停用', name : 'is_stop_cn', width : 120,
				editor : {
					type : 'select',
				    keyField : 'is_stop',
					source : [
						{ label: '否', id : '0' },
						{ label: '是', id : '1' }
					]
				}
			},
			{ display : '备注', name : 'note', width : 180 }
		];
		var paramObj = {
			height : '100%',
			inWindowHeight : true,
			checkbox : true,
			editable : true,
			columns : columns,
			toolbar : {
				items : [
					{ type : 'button', label : '查询', listeners : [ { click : query } ], icon : 'search' },
					{ type : 'button', label : '保存', listeners : [ { click : save } ], icon : 'save' },
					{ type : 'button', label : '添加', listeners : [ { click : add } ], icon : 'add' },
					{ type : 'button', label : '删除', listeners : [ { click : remove } ], icon : 'delete' },
				]
			}
		};
		grid = $("#mainGrid").etGrid(paramObj);
	};

	// 主查询
	var query = function() {
		var param = [
 			{ name: "insure_name", value: $("#insure_name").val() }
 		];
		grid.loadData(param, "queryInsurKind.do?isCheck=false");
	}

	// 添加
	var add = function() {
		grid.addRow();
	}
	
	// 保存
	var save = function(){
		var selectData = grid.selectGet();
		var paramVo = [];
		
		if (selectData.length == 0) {
			$.etDialog.warn('请选择行');
			return;
		} else {
			// 验证必填
			var isPass = grid.validateTest({
	    		required: {
	    			insure_code :true
	    		}
	    	});
	    	if (!isPass) {
	    		return;
	    	}
			// 验证重复数据
	    	if (!grid.checkRepeat(selectData, ['insure_code'])){
				return;
			}
	    	if (!grid.checkRepeat(selectData, ['insure_name'])){
				return;
			}
			
			$(selectData).each(function (index) {
				paramVo.push(this.rowData);
			});
		}
		
		ajaxPostData({
			url: "saveInsureKind.do",
			data: {
				paramVo: JSON.stringify(paramVo)
			},
			success: function(res){
				query();
			}
		});
	}

	// 删除
	var remove = function() {
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
		
		   $.etDialog.confirm('确定要删除?', function () {
               ajaxPostData({
                   url: 'deleteInsureKind.do',
                   data: {
                	   paramVo: JSON.stringify(paramVo)
                   },
                   success: function () {
                   	 query();
                   }
               })
           });
	/* 	ajaxPostData({
			url: "deleteInsureKind.do",
			data: {
				paramVo: JSON.stringify(paramVo)
			},
			success: function(res){
				query();
			}
		}); */
	}
</script>
</head>
<body>
	<div class="main">
		<table class="table-layout">
			<tr>
				<td class="label">社保险种：</td>
				<td class="ipt"><input id="insure_name" type="text" /></td>
			</tr>
		</table>
	</div>
	<div id="mainGrid"></div>
</body>
</html>