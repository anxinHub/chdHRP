<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"  xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">

	var parentData = frameElement.dialog.options.data;
	
	$(function(){
		loadGrid();
	})
	
	//加载指标标度表格
	function loadGrid(){
		grid = $("#grid").ligerGrid({
			columns: [ {
				display: '标度代号', name: 'scale_code', align: 'left', width: 100, 
				editor: {
					type: 'text'
				}, 
				render: function(rowdata, index, value){
					if(rowdata._code){
						return "<a href=javascript:edit_scale('"+value+"','"+index+"')>"+value+"</a>"
					}else{
						return value;
					}
				}
			}, {
				display: '标度名称', name: 'scale_name', align: 'left', width: 120, 
				editor: {
					type: 'text'
				}
			}, {
				display: '标度内容', name: 'scale_content', align: 'left', width: 200, 
				editor: {
					type: 'text'
				}
			}, {
				display: '得分比例', name: 'scale_point', align: 'left', width: 80, 
				editor: {
					type: 'number',
					precision: 2
				}
			}, {
				display: '上限值', name: 'high_point', align: 'left', width: 80, 
				editor: {
					type: 'number',
					precision: 2
				}
			}, {
				display: '下限值', name: 'low_point', align: 'left', width: 80, 
				editor: {
					type: 'number',
					precision: 2
				}
			}, {
				display: '排序号', name: 'sort_code', align: 'left', width: 80, 
				editor: {
					type: 'digits',
					precision: 0
				}
			}, {
				display: '是否停用', name: 'is_stop', textField: 'is_stop_name', align: 'left', width: 70, 
				editor : {
					type : 'select',
					valueField : 'is_stop',
					textField : 'is_stop_name',
					data: [{is_stop: 0, is_stop_name: "否"}, {is_stop: 1, is_stop_name: "是"}],
					keySupport : true,
					autocomplete : true,
				}
			} ],
			dataAction: 'local', dataType: 'local', usePager: false, 
			width: '100%', height: '100%', delayLoad: true, data: {}, 
			checkbox: true, rownumbers: true, selectRowButtonOnly: false, heightDiff: 30, 
			enabledEdit: true, onBeforeEdit: f_onBeforeEdit, 
			toolbar: { items: [{
				text: '添加行', id: 'add_row', icon: 'add', click: add_scale
			},{ line:true },{
				text: '删除', id: 'remove_scale', icon: 'delete', click: remove_scale
			},{ line:true },{
				text: '引用标准标度', id: 'imp_scale', icon: 'up', click: imp_scale
			},{ line:true },{
				text: '保存', id: 'save_scale', icon: 'save', click: save_scale
			},{ line:true },{
				text: '取消', id: 'close_scale', icon: 'close', click: thisClose
			}] }
		});
	}
	
	function f_onBeforeEdit(e){
		return true;
	}
	
	//新增行
	function add_scale(){
		grid.addRow();
	}
	
	//指标标度修改
	function edit_scale(scale_code, rowindex){
		var data = grid.getRow(rowindex);
		$.ligerDialog.open({ 
			title: '指标标度维护',
			url : 'matEvaTargetScalePage.do?isCheck=false',
			height: 450,
			width: 450,
			data: {matEvaScale: data, rowindex: rowindex}, 
			modal: true, showToggle: false, showMax: false,
			isResize: true, showMin: true, showMax: false, 
		}); 
	}

	//指标标度删除
	function remove_scale(){
		var data = grid.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择指标标度');
			return false;
		}
		grid.deleteSelectedRow();
	}
	
	//引入标准标度
	function imp_scale(){
		$.ligerDialog.confirm('此操作会覆盖原有的指标是否确定?', function (yes){
			if(yes){
				ajaxJsonObjectByUrl("queryMatEvaScaleList.do?isCheck=false", {is_stop: 0}, function (resData){
					grid.loadData(resData);
				});
			}
		});
	}
	
	//保存指标标度
	function save_scale(){
		var data = grid.getData();
		if (data.length == 0){
			$.ligerDialog.error('请添加指标标度后再保存');
			return false;
		}
		var error_msg = "";
		var row_count = 0;
		var existsObj = {};
		$.each(data, function(index, item){
			if(!item.scale_code){
				//空行进入下次循环
				return true;
			}
			if(existsObj[item.scale_code]){
				error_msg += '第'+(index+1)+'行与第'+existsObj[item.scale_code]+'行【标度代号】重复<br>';
			}
			if(!item.scale_name){
				error_msg += '第'+(index+1)+'行【标度名称】不能为空<br>';
			}
			if(existsObj[item.scale_name]){
				error_msg += '第'+(index+1)+'行与第'+existsObj[item.scale_name]+'行【标度名称】重复<br>';
			}
			if(!item.scale_point || item.scale_point > 1){
				error_msg += '第'+(index+1)+'行【得分比例】不能为空且不能大于1<br>';
			}
			if(item.high_point > item.scale_point){
				error_msg += '第'+(index+1)+'行【上限值】不能大于【得分比例】<br>';
			}
			if(item.is_stop === null || item.is_stop === '' || item.is_stop === undefined){
				error_msg += '第'+(index+1)+'行【是否停用】不能为空<br>';
			}
			row_count ++;
			//记录代号和名称用于判断重复
			existsObj[item.scale_code] = index+1;
			existsObj[item.scale_name] = index+1;
		})
		if(row_count == 0){
			$.ligerDialog.error("请添加指标标度！");
			return false;
		}
		
		if(error_msg.length > 0){
			$.ligerDialog.error(error_msg);
			return false;
		}
		
		var paras={
			codes: parentData.codes, 
			allData: JSON.stringify(data)
		};

			ajaxJsonObjectByUrl("saveMatEvaTargetScaleBatch.do?isCheck=false", paras, function(responseData){
				if(responseData.state == "true"){
					query_scale();
				}
			});
	}
	
	//关闭页面
	function thisClose(){
 		frameElement.dialog.close();
	} 
</script>
</head>
<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="grid"></div>
</body>
</html>