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
	var isAdd = "${isAdd}";
	var eva_code = "${evaInfo.eva_code}";
	var state = "${evaInfo.state}";
	var order_ids = "${order_ids}";
	var isEdit = 0;
	var true_get_score = "${evaInfo.get_score}";
	
	$(function (){
		
		if(${full_score == null}){
			$.ligerDialog.confirm('系统当前无评价方案，请先维护！', function(yes) {
				frameElement.dialog.close();//关闭当前页签
			});
		}
		
		loadDict();//加载下拉框 
		loadHead();
		
	});
	
	function save(){
		grid.endEdit();
		var datas = gridManager.getData();
			
		for (let index = datas.length - 1; index >= 0 ; index--) {
			if(datas[index].scale_point != null && datas[index].get_score.search("-") == -1){
				datas.splice(index, 1);
			}
		}
			
	 	var formPara={
	     	sup_id : $("#sup_id").val(),
	 		sup_no : $("#sup_no").val(),
	 		full_score : $("#full_score").val(),
	 		get_score : $("#get_score").val() == "" ? 0 : $("#get_score").val(),
	 		eva_code : eva_code,
	 		eva_content : $("#eva_content").val(),
	 		order_ids : order_ids,
	 		detailData : JSON.stringify(datas)
		};

	 	ajaxJsonObjectByUrl("addOrUpdateMatEvaSup.do", formPara, function(responseData){
	 	    if(responseData.state=="true"){
	 	       	eva_code = responseData.eva_code;
	 	       	if(isAdd == 1){
	 	 	      	isAdd = 0;
	 	 	       	state = 1;
	 	       	}
	 	       	if(order_ids == null || order_ids == ""){
	 	 	       	parentFrameUse().queryEvaSupMain();
	 	       	}
	 	       	isEdit = 0;	// 保存成功后 设为 没有编辑过标识
	 	       	
	 	       	order_ids = ""; //保存成功后 设置为空 防止二次更新插入相同数据【违反唯一约束】
	 	       	
	 	       	true_get_score = $("#get_score").val();
	 	    }
		}); 
	}
	
	function loadDict(){
    	//字典下拉框 
		$("#sup_name").ligerTextBox({ width : 160, disabled:true });
		$("#full_score").ligerTextBox({ width : 160, disabled:true });
		$("#get_score").ligerTextBox({ width : 160, disabled:true });
    } 
	
	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [
				{ display : '指标编码', name : 'target_code', width : '10%', align : 'left' },
				{ display : '指标名称', name : 'target_name', width : '10%', align : 'left' }, 
				{ display : '考核内容', name : 'eva_content', width: '20%', align : 'left' }, 
				{ display : '权重', name : 'weight', width: '10%', align : 'left' }, 
				{ display : '标度权重', name : 'scale_point', width: '10%', align : 'left', hide:true }, 
				{ display : '标准分值', name : 'full_score', width: '10%', align : 'left', formatter: '###,##0.00',
					render: function(rowdata, rowindex, value){return formatNumber(value,2,1)}		
				}, 
				{ display : '评价', name: 'get_score', width : '10%', align : 'right',
					editor: { type:"text"}
				},
				{ display : '扣分情况', name : 'deduct_text', width: '20%', align : 'left' }, 
				{ display : '扣分', name : '', width: '5%', align : 'center',
					render : function(rowdata, rowindex, value) {
						if(rowdata.is_show > 0 ){
							return '<a href=javascript:openUpdate("' + rowdata.target_code + '")>扣分</a>';
						}	
					}
				}
			],
			dataAction:'server', dataType:'server', usePager:false, 
			url:isAdd == 1 ? 'queryMatEvaSupTargetForAdd.do?isCheck=false' : 'queryMatEvaSupTarget.do?isCheck=false&eva_code=' + eva_code, 
			width:'100%', height:'80%', checkbox:false, enabledEdit:(state == 1||isAdd == 1) ? true : false, 
			onBeforeEdit: f_onBeforeEdit, onAfterEdit: f_onAfterEdit, 
			alternatingRow:true, isAddRow:false,
			isScroll : true, rownumbers : true, delayLoad : false,//初始化明细数据
			selectRowButtonOnly : true,//heightDiff: -10,isAddRow:false,
			toolbar : {
				items : [ 
					{ text : '保存', id : 'add', click : save, icon : 'add', disabled: (state == 1||isAdd == 1) ? false : true }
				]
			}
		});
		gridManager = $("#maingrid").ligerGetGridManager(); 
	}
	
	function f_onBeforeEdit(rowData){
		if(rowData.record.eva_method == 1){
			rowData.column.textField = 'scale_name';
			rowData.column.editor.type = "select";
			rowData.column.editor.valueField = "id";
			rowData.column.editor.textField = "text";
			rowData.column.editor.url = "queryMatEvaTargetScale.do?isCheck=false&target_code=" + rowData.record.target_code;
		}else{
			rowData.column.editor.type = "text";
			rowData.column.textField = '';
			rowData.column.formatter = '###,##0.00';
		}
	}
	
	var getScore = "${evaInfo.get_score}" == "" ? 0 : "${evaInfo.get_score}";
	
	function f_onAfterEdit(rowData){
		
		isEdit = 1;
		if(rowData.record.eva_method == 1){
			if(rowData.record.scale_point != null && rowData.oldvalue.toString().search("-") == -1){
				getScore = parseFloat(getScore) + parseFloat(rowData.record.get_score.split("-")[0] * rowData.record.full_score)
					- parseFloat(rowData.record.scale_point * rowData.record.full_score);
			}else{
				getScore = parseFloat(getScore) + parseFloat(rowData.record.get_score.split("-")[0] * rowData.record.full_score)
					- parseFloat(rowData.oldvalue.toString().split("-")[0] * rowData.record.full_score);
			}
			
			$("#get_score").val(formatNumber(getScore, 2, 0));
		}else{
			
			if(rowData.record.full_score < rowData.record.get_score){
				$.ligerDialog.warn("评价分值不能大于标准分值！");
				grid.updateCell('get_score', rowData.oldvalue, rowData.record);
				return false;
			}else{
				getScore = parseFloat(getScore) + parseFloat(rowData.record.get_score == "" ? 0 : rowData.record.get_score)
					- parseFloat(rowData.oldvalue == "" ? 0 : rowData.oldvalue);
				$("#get_score").val(formatNumber(getScore, 2, 0));
			}
		}
	}
	
	function openUpdate(obj){
		
		if(isAdd == 1){
			$.ligerDialog.warn("请先保存当前页面再进行扣分！");
			return;
		}
		
		// 编辑过评价后保存一下再进行扣分
		if(isEdit == 1){
			$.ligerDialog.confirm('是否保存对[指标评价]的修改?', function(yes) {
				if (yes) {
					save();
				}else{
					isEdit = 0;
					open();
				}
			});
		}else{
			open();
		}
		
		function open(){
			var paras = "eva_code=" + eva_code + 
				  "&" + "state=" + state +
			  	  "&" + "target_code=" + obj + 
			  	  "&" + "get_score=" + true_get_score;
			
			parent.$.ligerDialog.open({
				title : '扣分页面',
				height : $(window).height(),
				width : $(window).width(),
				url : 'hrp/mat/eva/sup/matEvaSupAbbreviationPage.do?isCheck=false&' + paras.toString(),
				modal : true,
				showToggle : false,
				showMax : true,
				showMin : true,
				isResize : true,
				parentframename : window.name, //用于parent弹出层调用本页面的方法或变量
			});
		}	
	}

	//键盘事件
	function loadHotkeys() {
		
	}
	
	function refresh(obj){
		$("#get_score").val(obj);
		getScore = obj;
		grid.options.url = 'queryMatEvaSupTarget.do?isCheck=false&eva_code=' + eva_code;
		grid.reload();
		if(order_ids == null){
	       	parentFrameUse().queryEvaSupMain();
    	}
	}
	
</script>
  
</head>
  
<body> 
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post"  id="form1" >
		<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
			<tr>
	            <td style="display: none;">
	            	<input name="eva_code" type="text" id="eva_code" value="${evaInfo.eva_code}" ltype="text" />
	            	<input name="state" type="text" id="state" value="${evaInfo.state}" ltype="text" />
	            	<input name="sup_id" type="text" id="sup_id" value="${sup_id}" ltype="text" />
	            	<input name="sup_no" type="text" id="sup_no" value="${sup_no}" ltype="text" />
	            </td>
			</tr>
	        <tr>
	            <td align="right" class="l-table-edit-td" >
	            	供应商：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="sup_name" type="text" id="sup_name" value="${sup_name}" ltype="text" disabled="disabled" required="true" validate="{required:true}" />
	            </td>
	            <td align="right" class="l-table-edit-td" >
	            	满分值：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="full_score" type="text" id="full_score" value="${full_score}" ltype="text" disabled="disabled" required="true" validate="{required:true}" />
	            </td>
	            <td align="right" class="l-table-edit-td"  >
	            	总得分：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="get_score" type="text" id="get_score" value="${evaInfo.get_score}" ltype="text" required="true" validate="{required:true}" />
	            </td>
	        </tr> 
	    </table>
    </form>
    <div style="width: 100%; height: 100%;">
		<div id="maingrid"></div>
		<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%"  style="margin-top: 5px;">
			<tr>	
				<td align="right" class="l-table-edit-td">总体评价：</td>
				<td align="left" class="l-table-edit-td">
					<textarea name="eva_content" type="text" style="width:800px;height:80px" id="eva_content" >${evaInfo.eva_content}</textarea>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>
