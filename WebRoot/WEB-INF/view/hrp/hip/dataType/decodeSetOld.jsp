<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<jsp:include page="${path}/resource.jsp">
	<jsp:param value="dialog,grid,select" name="plugins" />
</jsp:include>
<script src="<%=path%>/lib/ligerUI/js/core/base.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/plugins/ligerLayout.js" type="text/javascript"></script>
<script type="text/javascript">
	var $grid, $source_col, $target_table, $target_col, $decode_type, $rela_col, $create_type;
	var parentFrameName = parent.$.etDialog.parentFrameName; // 拿取window名
	var parentWindow = parent.window[parentFrameName]; // 当前页拿取window对象
	var hipDataType = parentWindow.hipDataType;

	var decodeTypeOptions = [{
		id: "1", text: "对应转换"
	}, {
		id: "2", text: "HRP生成"
	}];
	
	$(function ()
	{
		initLayout();
		loadDict()//加载下拉框
		//加载数据
		loadHead(null);	
		query();
		
		//默认隐藏
		$("#relaTr").hide();
		$("#createTr").hide();
		
		//绑定按钮
		$("#addGrid").click(function() {
			addGrid();
		})
		$("#this_save").click(function() {
			this_save();
		})
		$("#this_close").click(function() {
			this_close();
		})
	});
	
	function initLayout(){
		var rightWidth = $(window).width() - 290;
		$("#layout").ligerLayout({ 
			//topHeight: 100, 
			leftWidth: 280, 
			InWindow: true, 
			height: '100%', 
			//centerWidth: '5', 
			rightWidth: rightWidth, 
			bottomHeight: 50, 
		});
	}
	
	//查询
	function  query(){
		params = [ {
			name: "type_id", value: hipDataType.type_id
		}, {
			name: "table_name", value: hipDataType.to_table
		} ]
		
		$grid.loadData(params, "queryHipDataDecode.do?isCheck=false");
	}
	
	function loadHead(){
		/* 列头 */
		var columns = [ {
			display: 'ID', name: 'decode_id', align: 'left', hidden: true, editable: false, 
		}, {
		    display: '需转换字段', name: 'source_col', align: 'left', width: 120, editable: false, 
		    render: function (ui) { // 修改页打开
				return '<a data-item=' + ui.rowIndx + ' class="read_decode">' + ui.cellData + '</a>'
			}
		}, {
			display: '目标表', name: 'target_table', align: 'left', width: 120, editable: false, 
		}, {
			display: '转换后字段', name: 'target_col', align: 'left', width: 120, editable: false, 
		}, {
			display: '转换', name: 'decode_type', align: 'left', hidden: true, editable: false, 
		}, {
			display: '转换方式', name: 'decode_type_name', align: 'left', width: 90, editable: false, 
		}, {
			display: '对应字段', name: 'rela_col', align: 'left', width: 120, editable: false, 
		}, {
			display: '生成方式', name: 'create_type', align: 'left', width: 160, editable: false, 
		}, {
			display: '表达式', name: 'el', align: 'left', width: 240, editable: false, 
		} ]; 
		
		/* 头部按钮 */
	    var toolbar = {
			items: [ { 
				type: "button", label: '删除行', icon: 'delete', listeners: [{ click: removeRow }] 
			} ]
		};
		
		/* 表格基础参数 */
		var paramObj = {	
			width: 'auto',
			height: '90%', 
			usePager: false, 
			editable: true,
			inWindowHeight: true,
			toolbar: toolbar,
			checkbox: true,
			columns: columns, 
		};
		
		$grid = $("#maingrid").etGrid(paramObj);

		$("#maingrid").on('click', '.read_decode', function () {
			var rowIndex = $(this).attr('data-item');
			var currentRowData = $grid.getDataInPage()[rowIndex];
			read_decode(rowIndex, currentRowData);
		})
	}
	
	function read_decode(rowId, obj){
		$("#row_id").val(rowId);
		$("#decode_id").val(obj.decode_id);
		$source_col.setValue(obj.source_col);
		$target_table.setValue(obj.target_table);
		$target_col.setValue(obj.target_col);
		$decode_type.setValue(obj.decode_type);
		$rela_col.setValue(obj.rela_col);
		$create_type.setValue(obj.create_type);
		$("#el").val(obj.el);
	}
	
	function removeRow(){
    	$grid.deleteSelectedRows();
	}
	
	function loadDict(){
		$("#target_table").val(hipDataType.to_table);
		//解析sql得列表
    	$source_col = $("#source_col").etSelect({
			url : "querySourceColListByType.do?isCheck=false&type_id="+hipDataType.type_id,
			defaultValue : "none",
		});
    	$target_table = $("#target_table").etSelect({
			url : "queryHrpDictTable.do?isCheck=false",
			defaultValue : "none",
			searchField: ['text'], 
			backEndSearch: true, 
			onChange: function(value){
				//清除相关控件的选中项
				$target_col.clearItem();
				$rela_col.clearItem();
				$create_type.clearItem();
				
				//重新加载相关控件数据
				$target_col.reload({
				    url: 'queryHrpDictTableCol.do?isCheck=false',
				    type: 'post',
				    para: {table_name: value}
				});
				$rela_col.reload({
				    url: 'queryHrpDictTableCol.do?isCheck=false',
				    type: 'post',
				    para: {table_name: value}
				});
				var createTypeOptions = [{
					id: "UUID", text: "UUID"
				}, {
					id: value+"_SEQ", text: "序列："+value+"_SEQ"
				}];
				$create_type.clearOptions();
				$create_type.addOptions(createTypeOptions);
		    }
    	});
    	$target_col = $("#target_col").etSelect({
			url : "queryHrpDictTableCol.do?isCheck=false",
			defaultValue : "none",
		});
    	$decode_type = $("#decode_type").etSelect({
    	    options: decodeTypeOptions,
			defaultValue : "none",
			onChange: function(value){
				changeDecode(value);
		    }
		});
    	$rela_col = $("#rela_col").etSelect({
			url : "queryHrpDictTableCol.do?isCheck=false",
			defaultValue : "none",
		});
    	$create_type = $("#create_type").etSelect({
    	    //options: createTypeOptions,
			defaultValue : "none",
		});
	}
	
	function changeDecode(dtype){
		if(dtype && dtype == 1){
			$("#relaTr").show();
			$("#createTr").hide();
		}else if(dtype && dtype == 2){
			$("#relaTr").hide();
			$("#createTr").show();
		}else{
			$("#relaTr").hide();
			$("#createTr").hide();
		}
		$rela_col.clearItem();
		$create_type.clearItem();
	}
	
	var row_data = {};
	var row_id = null;
	function addGrid(){
		if(!$source_col.getValue()){
			$.etDialog.warn("【需转换字段】不能为空！");
            return false;
		}
		if(!$target_col.getValue()){
			$.etDialog.warn("【转换后字段】不能为空！");
            return false;
		}
		if(!$decode_type.getValue()){
			$.etDialog.warn("【转换方式】不能为空！");
            return false;
		}
		if($decode_type.getValue() == 1 && !$rela_col.getValue()){
			$.etDialog.warn("转换方式为【对应转换】时【对应字段】不能为空！");
            return false;
		}
		if($decode_type.getValue() == 2 && !$create_type.getValue()){
			$.etDialog.warn("转换方式为【HRP生成】时【生成方式】不能为空！");
            return false;
		}
		
		row_data.decode_id = $("#decode_id").val();
		row_data.source_col = $source_col.getValue();
		row_data.target_table = $("#target_table").val();
		row_data.target_col = $target_col.getValue();
		row_data.decode_type = $decode_type.getValue();
		row_data.decode_type_name = $decode_type.getText();
		row_data.rela_col = $rela_col.getValue();
		row_data.create_type = $create_type.getValue();
		row_data.el = $("#el").val();
		
		//获取Grid数据
 		var data = $grid.getAllData();
		var map = {};
		$.each(data, function(index, v){
			map[v.source_col] = "row"+v._rowIndx;
		});
		
		row_id = $("#row_id").val();
		console.log($grid.getRowData(row_id))
		if(row_id){
			if(!map[row_data.source_col]){
				$grid.addRow(row_data);
			}else{
				if(map[row_data.source_col] != "row"+row_id){
					$.etDialog.warn("【需转换列】同一个字段只能设置一次！");
		            return false;
				}
				
				$grid.updateRow(row_id, row_data);
			}
			$("#row_id").val("");
		}else{
			if(map[row_data.source_col]){
				$.etDialog.warn("【需转换列】同一个字段只能设置一次！");
	            return false;
			}
			$grid.addRow(row_data);
		}
		row_data = {};
		
		console.log($grid.getAllData());
		//清除左侧信息以方便再次选择
		$source_col.clearItem();
		$target_col.clearItem();
		$decode_type.clearItem();
		$rela_col.clearItem();
		$create_type.clearItem();
	}
	
	function this_save(){
 		var data = $grid.getAllData();
 		
 		if(!data || data.length == 0){
            $.etDialog.warn("请添加转换信息");
            return false;
		}
		/*
 		var msg = "";
		$.each(data,function(index, v){
			if(!v.subj_code || !v.sort_code){
				msg += "财务会计第"+(index+1)+"行科目、排序号不能为空<br/>";
			}
		});
		
		if(msg){
			$.etDialog.warn(msg);
            return false;
		}
		*/
		ajaxPostData({
			url : 'addHipDataDecode.do?isCheck=false',
			data : {
				type_id: hipDataType.type_id, 
				allData: JSON.stringify(data)
			},
		    success: function (responseData) {
				
			},
		})
	}
	
	function this_close(){
		//frameElement.dialog.close();
		var curIndex = parent.$.etDialog.getFrameIndex(window.name);
		parent.$.etDialog.close(curIndex);
	}
	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="layout" style="z-index: 2">
		<div  position="left" title="设置" style="left: 0px; top: 0px;  height: 100%;">
			<table class="table-layout">
				<tr>
					<td><input id="row_id" type="hidden"/></td>
					<td><input id="decode_id" type="hidden"/></td>
				</tr>
				<tr>
					<td class="label"><font size="2" color="red">*</font>需转换字段：</td>
					<td class="ipt">
						<select id="source_col" style="width: 180px;"></select>
					</td>
				</tr>
				<tr>
					<td class="label"><font size="2" color="red">*</font>目标表：</td>
					<td class="ipt">
						<!-- <input id="target_table" type="text" disabled="disabled"/> -->
						<select id="target_table" style="width: 180px;"></select>
					</td>
				</tr>
				<tr>
					<td class="label"><font size="2" color="red">*</font>转换后字段：</td>
					<td class="ipt">
						<select id="target_col" style="width: 180px;"></select>
					</td>
				</tr>
				<tr>
					<td class="label"><font size="2" color="red">*</font>转换方式：</td>
					<td class="ipt">
						<select id="decode_type" style="width: 180px;" ></select>
					</td>
				</tr>
				<tr id="relaTr">
					<td class="label"><font size="2" color="red">*</font>对应字段：</td>
					<td class="ipt">
						<select id="rela_col" style="width: 180px;"></select>
					</td>
				</tr>
				<tr id="createTr">
					<td class="label"><font size="2" color="red">*</font>生成方式：</td>
					<td class="ipt">
						<select id="create_type" style="width: 180px;"></select>
					</td>
				</tr>
				<tr>
					<td class="label">表达式：</td>
					<td class="ipt">
						<textarea id="el" cols="20" rows="30" style="width: 180px; height: 120px"></textarea>
					</td>
				</tr>
				<tr>
					<td class="label"></td>
					<td class="ipt button-group">
						<button id="addGrid">添加至列表</button>
						<!-- <div class="button-group" >
							<button id="addGrid">添加至列表</button>
						</div> -->
					</td>
				</tr>
			</table>
		</div>
		<div position="center" title="列表"  style="left:width:100%; height: 100%;">
			<div id="maingrid"></div>
		</div>
		<div position="bottom" >
			
			<div position="bottom" class="button-group" >
				<button id="this_save">保存</button>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<button id="this_close">关闭</button>
			</div>
		</div>
	</div>
</body>
</html>