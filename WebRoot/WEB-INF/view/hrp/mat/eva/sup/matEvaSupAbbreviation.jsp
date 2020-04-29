<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"> 
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
	<script src="<%=path%>/lib/hrp/mat/mat.js"	type="text/javascript"></script> 
	<script src="<%=path%>/lib/Lodop/barcode.js"	type="text/javascript"></script>
	<script src="<%=path%>/lib/Lodop/LodopFuncs.js"	type="text/javascript"></script>
    <script type="text/javascript">
	var grid;   
	var gridManager;
	var eva_code = "${eva_code}";
	var target_code = "${target_code}";
	var get_score = "${get_score}";
	
	$(function (){
		loadDict();//加载下拉框 
		loadHead();
	});
	
	function loadDict(){
    	//字典下拉框 
    } 
	
	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [
				{ display : '扣分项代码', name : 'deduct_code', width : 100, align : 'left' },
				{ display : '扣分项名称', name : 'deduct_id', textField: 'deduct_name', width : 100, align : 'left',
					editor : {
						type : 'select',
						valueField : 'deduct_id',
						textField : 'deduct_name',
						url : 'queryMatEvaTargetDeduct.do?isCheck=false&target_code=' + target_code,
						keySupport : true,
						autocomplete : true
					}	
				}, 
				{ display : '扣分项描述', name : 'deduct_desc', width: 100, align : 'left' }, 
				{ display : '上限值', name : 'high_point', width: 100, align : 'left', hide:true },
				{ display : '扣分数', name : 'deduct_score', width: 100, align : 'right', editor: { type:"number"}}
			],
			dataAction:'server', dataType:'server', usePager:false, url:'queryMatEvaSupTargetDeduct.do?isCheck=false&eva_code=' + eva_code + '&target_code=' + target_code, 
			width:'100%', height:'93%', checkbox:true,
			enabledEdit:(${state == 1} ? true : false), alternatingRow:true, isAddRow:true,
			isScroll : true, rownumbers : true, delayLoad : false,//初始化明细数据
			selectRowButtonOnly : true,//heightDiff: -10,isAddRow:false,
			onBeforeEdit: f_onBeforeEdit, onAfterEdit: f_onAfterEdit, 
			toolbar : {
				items : [ 
					{ text : '保存', id : 'save', click : save, icon : 'save', disabled: (${state == 1} ? false : true) },
					{ line:true },
					{ text : '删除', id : 'delete', click : remove, icon : 'delete', disabled: (${state == 1} ? false : true) }
				]
			}
		});
		gridManager = $("#maingrid").ligerGetGridManager(); 
	}
	
	function validateGrid() {
		var datas = gridManager.getData();
		
		var targetMap = new HashMap();
		var msg = "";
		$.each(datas, function (i, v) {
			if (targetMap.get(v.deduct_code)== null || targetMap.get(v.deduct_code) == 'undefined' || targetMap.get(v.deduct_code) == "") {
				targetMap.put(v.deduct_code, v);
			} else {
				msg += v.deduct_code;
			}
		});
		if(msg != ""){
			$.ligerDialog.warn('存在相同扣分项！');
			return false;
		}
		
		return true;
	}
	
	function save(){
		grid.endEdit();
		var datas = gridManager.getData();
		
		var formPara={
	 		eva_code : eva_code,
	 		target_code : target_code,
	 		get_score : get_score,
	 		detailData : JSON.stringify(datas)
		};

		if(validateGrid()){
			ajaxJsonObjectByUrl("addMatEvaTargetDudect.do", formPara, function(responseData){
	 	    	if(responseData.state=="true"){
	 	    		parentFrameUse().refresh(get_score);
	 	    		/*$.ligerDialog.confirm('保存成功！<br>是否关闭当前页？', 
	 	   				function (yes) { 
	 	    				if(yes){
	 	 	    				frameElement.dialog.close();//关闭当前页签
	 	    				}	
	 	    			}
	 	    		)*/
	 	        }
	 	    });
		}	
	}
	
	function is_addRow() {
		setTimeout(function () { //当数据为空时 默认新增一行
			grid.addRow();
		}, 100);
	}
	
	function remove(){
		
		var data = gridManager.getCheckedRows();
		
		if (data.length == 0) {
			$.ligerDialog.warn('请选择行');
			return ;
		} else {
			
			$.ligerDialog.confirm('确定删除选中行？', 
				function (yes) { 
					grid.deleteSelectedRow();
				}
			);
		}
	}
	
	//键盘事件
	function loadHotkeys() {
	}
	
	var deductScore = "";
	// 保存编辑前扣分值
	function f_onBeforeEdit(rowData){
		deductScore = rowData.record.deduct_score;
	}
	
	// 回充数据
	function f_onAfterEdit(rowData){
		if(rowData.record.deduct_id == "" || rowData.record.deduct_id == undefined){
			$.ligerDialog.warn('请选择扣分项！');
			return ;
		}
		grid.updateCell('deduct_code', rowData.record.deduct_id.split("-")[0], rowData.record);
		grid.updateCell('deduct_desc', rowData.record.deduct_id.split("-")[1], rowData.record);
		grid.updateCell('high_point', rowData.record.deduct_id.split("-")[2], rowData.record);
		
		if(rowData.record.deduct_id.split("-")[2] != "" 
				&& rowData.record.deduct_score > rowData.record.deduct_id.split("-")[2]){
			$.ligerDialog.warn("扣分数已超出上限值！<br>上限值：<font color='red'>"+rowData.record.deduct_id.split("-")[2]+"</font>");
			grid.updateCell('deduct_score', rowData.oldvalue, rowData.record);
		}else if(rowData.record.deduct_score != deductScore){
			get_score = parseFloat(get_score) - parseFloat(rowData.record.deduct_score == "" ? 0 : rowData.record.deduct_score)
				+ parseFloat(rowData.oldvalue == "" ? 0 : rowData.oldvalue);
		}
		deductScore = "";
	}
	
</script>
  
</head>
  
<body onload="is_addRow()"> 
	<div id="pageloading" class="l-loading" style="display: none"></div>
    <div style="width: 100%; height: 100%;">
		<div id="maingrid"></div>
	</div>
</body>
</html>
