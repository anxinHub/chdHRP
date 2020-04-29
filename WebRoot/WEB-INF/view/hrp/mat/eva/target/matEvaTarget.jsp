<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"  xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">

	var grid;
	var parentData = frameElement.dialog.options.data;
	var matEvaTarget = parentData.matEvaTarget;
	var is_update = parentData.is_update;
	var is_true = true;
	
	$(function(){
		//tab页签
		$("#navtab1").ligerTab({contextmenu: false, onAfterSelectTabItem: function(id){
			if(id == "scaleDiv"){
				if(is_true && is_update == 1){
					loadGrid();
					query_scale();
					is_true = false;
				}else{
					if(grid){
						grid.reRender();
					}
				}
			}
		}});
		//渲染表单样式
		loadDict();
		//修改页面渲染数据
		if(matEvaTarget){
			$("#eva_type_span").text(matEvaTarget.target_type_code + " " + matEvaTarget.target_type_name)
			$("#target_type_code").val(matEvaTarget.target_type_code);
			$('#target_code').val(matEvaTarget.target_code);
			liger.get("target_code").setDisabled();
			$('#target_name').val(matEvaTarget.target_name);
			liger.get("eva_method").setValue(matEvaTarget.eva_method);
			liger.get("target_kind").setValue(matEvaTarget.target_kind);
			liger.get("target_attr").setValue(matEvaTarget.target_attr);
			$('#target_explain').val(matEvaTarget.target_explain);
			$('#eva_content').val(matEvaTarget.eva_content);
			$('#eva_principle').val(matEvaTarget.eva_principle);
			$('#sort_code').val(matEvaTarget.sort_code);
			liger.get("sort_code").setEnabled();
			if(matEvaTarget.is_stop == "1"){
				$('#is_stop1').prop("checked", "checked");
			}
			$("#saveScale1").hide();
		}else{
			$("#target_type_code").val(parentData.target_type_code);
			$("#eva_type_span").text(parentData.target_type_code + " " + parentData.target_type_name)
		}
	})
	
	//渲染表单样式
	function loadDict(){
		$("#target_code").ligerTextBox({width:200});
		$("#target_name").ligerTextBox({width:200});
		$("#sort_code").ligerTextBox({width:200, disabled: true, digits: true});
		
		autoCompleteByData("#eva_method", [{id: 1, text: "标度"}, {id: 2, text: "打分"}], "id", "text", true, true, "", true, "", "200");
		autoCompleteByData("#target_kind", [{id: 1, text: "主观"}, {id: 2, text: "客观"}], "id", "text", true, true, "", true, "", "200");
		autoCompleteByData("#target_attr", [{id: 1, text: "定性"}, {id: 2, text: "定量"}], "id", "text", true, true, "", true, "", "200");
	}
	
	//保存指标
	function saveScale(flag){
		if(!$("#target_code").val()) {
			$.ligerDialog.warn("指标编码不能为空！");
			return false;
		}
		if(!$("#target_name").val()) {
			$.ligerDialog.warn("指标名称不能为空！");
			return false;
		} 
		if(!liger.get("eva_method").getValue()) {
			$.ligerDialog.warn("评价方式不能为空！");
			return false;
		}
		
		var para = {
			target_type_code: $("#target_type_code").val(),
			target_code: $("#target_code").val(),
			target_name: $("#target_name").val(),
			eva_method: liger.get("eva_method").getValue(),
			target_kind: liger.get("target_kind").getValue(),
			target_attr: liger.get("target_attr").getValue(),
			target_explain: $("#target_explain").val(),
			eva_content: $("#eva_content").val(),
			eva_principle: $("#eva_principle").val(),
			sort_code: $("#sort_code").val(),
			is_stop: $('.is_stop:checked').val(), 
			is_update: is_update
		};
		
		ajaxJsonObjectByUrl("saveMatEvaTarget.do?isCheck=false", para, function (res) {
			if (res.state == "true") {
				// 刷新父页面的表格
				parentFrameUse().query_target(); 
				if(flag == 1){
					//清空表单以便再次添加
					$('#target_code').val("");
					$('#target_name').val("");
					$('#target_explain').val("");
					$('#eva_content').val("");
					$('#eva_principle').val("");
				}else{
					//修改页面标志为修改页面
					is_update = 1;
					liger.get("target_code").setDisabled();
					$("#sort_code").val(res.sort_code);
					liger.get("sort_code").setEnabled();
				}
			}
		}, false);
	}
	
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
			dataAction: 'server', dataType: 'server', usePager: false, width: '100%', height: '100%', 
			url:'queryMatEvaTargetScaleList.do?isCheck=false', delayLoad: true, 
			checkbox: true, rownumbers: true, selectRowButtonOnly: false, heightDiff: 30, 
			isAddRow: false, enabledEdit: true, onBeforeEdit: f_onBeforeEdit, 
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
	
	//指标标度查询
	function query_scale(){

		grid.options.parms=[];
		grid.options.newPage=1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({name: 'target_code', value: $("#target_code").val()});
		//加载查询条件
		grid.loadData(grid.where);
	}
	
	function f_onBeforeEdit(e){
		if (e.column.name == "scale_code" && e.record._code) {
			return false;
		}
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
		$.ligerDialog.confirm('此操作会覆盖原有的指标标度是否确定?', function (yes){
			if(yes){
				ajaxJsonObjectByUrl("addMatEvaTargetScaleByBZ.do?isCheck=false",{target_code: $("#target_code").val()},function (responseData){
					if(responseData.state=="true"){
						query_scale();
					}
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
			target_code: $("#target_code").val(),
			allData: JSON.stringify(data)
		};

		ajaxJsonObjectByUrl("saveMatEvaTargetScale.do?isCheck=false", paras, function(responseData){
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
	
	<div id="navtab1" style="border:1px solid #A3C0E8; ">
		<div tabid="targetDiv" title="指标维护" lselected="true" >
			<h3 style="margin-left: 20px;">指标类别：<span id="eva_type_span"></span></h3>
			<table class="table-layout" style="margin-top: 20px; width="100%">
				<tr>
					<td align="right" class="l-table-edit-td" style="padding-left: 30px;" width="20%">
						<span style="color: red">*</span>指标编码：
					</td> 
					<td align="left" class="l-table-edit-td" width="30%">
						<input type="hidden" id="target_type_code" />
						<input type="text" id="target_code" validate="{required:true}" />
					</td>

					<td align="right" class="l-table-edit-td" style="padding-left: 30px;" width="20%">
						<span style="color: red">*</span>指标名称：
					</td> 
					<td align="left" class="l-table-edit-td" width="30%">
						<input type="text" id="target_name" validate="{required:true}" />
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td">
						<span style="color: red">*</span>评价方式：
					</td> 
					<td align="left" class="l-table-edit-td">
						<input type="text" id="eva_method" validate="{required:true}" />
					</td>

					<td align="right" class="l-table-edit-td">
						指标类型：
					</td> 
					<td align="left" class="l-table-edit-td">
						<input type="text" id="target_kind" />
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td">
						指标属性：
					</td> 
					<td align="left" class="l-table-edit-td">
						<input type="text" id="target_attr" />
					</td>

					<td align="right" class="l-table-edit-td">
						排序号：
					</td> 
					<td align="left" class="l-table-edit-td">
						<input type="text" id="sort_code" />
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td">
						指标解释：
					</td> 
					<td align="left" class="l-table-edit-td">
						<textarea id="target_explain" rows="3" style="width: 200px;"></textarea>
					</td>

					<td align="right" class="l-table-edit-td">
						考核内容：
					</td> 
					<td align="left" class="l-table-edit-td">
						<textarea id="eva_content" rows="3" style="width: 200px;"></textarea>
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td">
						评分原则：
					</td> 
					<td align="left" class="l-table-edit-td">
						<textarea id="eva_principle" rows="3" style="width: 200px;"></textarea>
					</td>
				</tr>
				<tr> 
					<td align="right" class="l-table-edit-td">
						<span style="color: red">*</span>是否停用：
					</td>
					<td align="left" class="l-table-edit-td">
						<input type="radio" name="is_stop" class="is_stop" id="is_stop1" value="1" />是 
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" name="is_stop" class="is_stop" id="is_stop0" value="0" checked/>否
					</td>
				</tr>
			</table>
			<div align="center" style="padding-top: 30px;">
				<button class="l-button l-button-test" id="saveScale1" onclick="saveScale(1);" style="width: 100px;">保存&继续</button>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<button class="l-button l-button-test" onclick="saveScale(0);">保存</button>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<button class="l-button l-button-test" onclick="thisClose();">关闭</button>
			</div>
		</div>
		<div tabid="scaleDiv" title="指标标度">
			<div id="grid"></div>
		</div>
	</div>
</body>
</html>