<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String path = request.getContextPath(); %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<script src="<%=path%>/lib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script>
<script src="<%=path%>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>
<script src="<%=path%>/lib/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<link rel="stylesheet" href="<%=path%>/lib/Z-tree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="<%=path%>/lib/Z-tree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=path%>/lib/Z-tree/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="<%=path%>/lib/Z-tree/js/jquery.ztree.exedit-3.5.js"></script>
<script type="text/javascript">
	var leftgrid;
	var leftGridManager = null;
	var rightgrid;

	var rightGridManager = null;

	var userUpdateStr;

	var item_url = "";

	var emp_url = "";

	var saveData = '${scheme_id}';

	var scheme_id;

	$(function() {
		if('${page_type}' == 'people'){
			//$(".l-layout-left").attr("style","display:none")
			$("#layout1").ligerLayout({
				 leftWidth : 0,
				minLeftWidth : 0,
				isLeftCollapse: true,
				allowLeftResize : false
			}); 
			$(".l-layout-left").remove();
			$(".l-layout-collapse-left").remove();
			$(".l-layout-center").css("left","0");
			$(".l-layout-center").css("width","100%");
			
		}else{
			 $("#layout1").ligerLayout({
				 leftWidth : 600,
				minLeftWidth : 230,
				allowLeftResize : false
			}); 
			
		}

		loadDict();

		$("#wage_code").ligerTextBox({ width : 160, disabled : true });
		$("#scheme_name").ligerTextBox({ width : 160 });
		$("#scheme_code").ligerTextBox({ width : 160 });
		$("#emp_code").ligerTextBox({ width : 160 });
		$("#dept_code").ligerTextBox({ width : 160 });
		$("#item_code").ligerTextBox({ width : 160 });

		if ('${scheme_code}' != "") {
			item_url = "queryAccWageSchemeItem.do?isCheck=false&scheme_id=" 
					 + saveData + "&wage_code=" + '${wage_code}' + "&acc_year=" + '${acc_year}';
			emp_url = "queryAccWageSchemeEmpKind.do?isCheck=false&scheme_id=" 
					+ saveData + "&scheme_code=" + $("#scheme_code").val() + "&wage_code=" + '${wage_code}';
			<%--
			emp_url = "queryAccWageSKind.do?isCheck=false&scheme_id=" 
					+ saveData + "&scheme_code=" + $("#scheme_code").val() + "&wage_code=" + '${wage_code}';
			--%>
		}

		loadHead();

		if('${page_type}' == 'people'){
			//$(".l-layout-left").attr("style","display:none")
			//$(".l-layout-left").remove();
			//$(".l-layout-center").css("width","100%");
			// $("#layout1").ligerLayout({ isLeftCollapse: true }); 
			/* $("#layout1").ligerLayout({
				 leftWidth : 0,
				 rightWidth:'100%',
				minLeftWidth : 0,
				allowLeftResize : false
			}); */
		}
	});
	
	<%--
	// 查询工资方案与职工分类关系
	function queryEmpKind(){
		if (saveData != "") {
			leftgrid.set("url", "queryAccWageSKind.do?isCheck=false&scheme_id=" 
					+ saveData + "&scheme_code=" + $("#scheme_code").val() + "&wage_code=" + '${wage_code}');
			leftgrid.loadData(leftgrid.where);
		}else{
			emp_url="";
		}
	}
	--%>
	
	// 查询 工资方案与职工关联关系
	function querySEmp(){
		if("${scheme_id}" == ""){
			emp_url = "";
		}else{
			leftgrid.options.parms=[];
			leftgrid.options.newPage=1;
			leftgrid.options.parms.push({name: "emp_code", value: liger.get("emp_code").getText().split(" ")[0]});
			
			emp_url = "queryAccWageSchemeEmpKind.do?isCheck=false&scheme_id=" 
				    + saveData + "&scheme_code=" + $("#scheme_code").val() + "&wage_code=" + '${wage_code}';
			leftgrid.set("url", emp_url);
		}
	}
	
	function queryItem(){
		
		if (saveData != "") {

				rightgrid.set("url", "queryAccWageSchemeItem.do?isCheck=false&scheme_id="+ saveData + "&wage_code=" + '${wage_code}' + "&acc_year="
					+ '${acc_year}'+"&item_code="+$("#item_code").val());

				rightgrid.loadData(rightgrid.where);
				
		}else{
			
			item_url="";
		}
		
	}

	// 加载grid
	function loadHead() {
		leftgrid = $("#leftgrid").ligerGrid({
			columns : [
				{ display: '职工编码', name: 'emp_code', align: 'left' , width:'30%'},
				{ display: '职工姓名', name: 'emp_name', align: 'left' , width:'50%'},
				{ name: "emp_id", hide: 'emp_id', width: '1%'}
			],
			dataAction : 'server',
			dataType : 'server',
			usePager : true,
			url : emp_url,
			width : '100%',
			height : '100%',
			checkbox : true,
			rownumbers : true,
			selectRowButtonOnly : true,
			enabledEdit : true,//heightDiff: -10,
			toolbar : {
				items : [
					{ text : '选择职工', id : 'search', click : choose_emp, icon : 'search' }, 
					{ line : true }, 
					{ text : '删除', id : 'delete', click : emp_remove, icon : 'delete' },
					{ line : true }, 
					{ text : '按工资套生成', id : 'againCreateToEmp', click : againCreateToEmp, icon : '' },
					{ line : true }
					<%--
					{ text : '选择职工分类', id : 'search', click : choose_empKind, icon : 'search' }, 
					{ line : true }, 
					{ text : '删除', id : 'delete', click : empKind_remove, icon : 'delete' },
					{ line : true }, 
					{ text : '保存', id : 'save', click : empKind_save, icon : 'save' },
					{ line : true }
					--%>
				]
			}
		});
		leftGridManager = $("#leftgrid").ligerGetGridManager();

		rightgrid = $("#rightgrid").ligerGrid({
			columns : [
				{ display : '工资项编码', name : 'item_code', align : 'left' , width:'30%' }, 
				{ display : '工资项名称', name : 'item_name', align : 'left' , width:'50%' }, 
				{ name : 'column_item', hide : 'column_item', width : '1%' } 
			],
			dataAction : 'server',
			dataType : 'server',
			usePager : false,
			url : item_url,
			width : '100%',
			height : '100%',
			rownumbers : true,
			checkbox : true,
			selectRowButtonOnly : true,//heightDiff: -10,
			toolbar : {
				items : [
					{ text : '选择工资项（<u>Q</u>）', id : 'search', click : choose_item, icon : 'search' }, 
					{ line : true }, 
					{ text : '删除（<u>D</u>）', id : 'delete', click : item_remove, icon : 'delete' }, 
					{ line : true }, 
					{ text : '按工资套重新生成', id : 'againCreateToItem', click : againCreateToItem, icon : '' } 
				]
			}
		});
		rightGridManager = $("#rightgrid").ligerGetGridManager();
	}
	
	// 选择职工
	function choose_emp(){
		if("${scheme_id}" == ""){
			$.ligerDialog.warn('请先保存方案！');
			return;
		}
		
		$.ligerDialog.open({
			url : 'chooseEmpMainPage.do?isCheck=false&wage_code=' + '${wage_code}' 
					+ '&scheme_id=' + "${scheme_id}"+ "&scheme_type_code=" + '${scheme_type_code}' + "&acc_year=" + '${acc_year}' + "&is_gzt=" + '${is_gzt}',
			height : 600,
			width : 800,
			modal : true,
			isResize : true,
			buttons : [{
				text : '取消',
				onclick : function(item, dialog) {
					dialog.close();
				}
			}]
		});
	}
	
	// 删除方案关联的职工 
	function emp_remove(){
		var emp_data = leftgrid.getCheckedRows();
		if(emp_data.length == 0){
			$.ligerDialog.warn('请选择行');
			return;
		}
		var ParamVo = [];
		$(emp_data).each(function() {
			ParamVo.push(
				this.emp_id + "@" + '${scheme_id}'
			)
		});

		$.ligerDialog.confirm('确定删除?', function(yes) {
			if (yes) {
				ajaxJsonObjectByUrl("deleteAccWageSchemeKind.do?isCheck=false",
					{ ParamVo : ParamVo.toString() }, 
					function(responseData) {
						if (responseData.state == "true") {
							leftgrid.reload();
						}
					}
				);
			}
		});
	}
	
	//按工资套重新生成员工
	function againCreateToEmp() {
		var formData = {
			wage_code: "${wage_code}",
			scheme_id: saveData,
			is_gzt: '${is_gzt}'
		};
		
		ajaxJsonObjectByUrl("addBatchAccWageSchemeEmpKind.do?isCheck=false", formData, 
			function(responseData) {
				querySEmp();
			}
		);
	}
	
	//按工资套从新生成工资项
	function againCreateToItem() {
		if(saveData == "" ){
			$.ligerDialog.warn('请先维护工资方案！');
			return;
		}
		
		var formData="wage_code="+'${wage_code}'
		+"&scheme_id="+saveData;
		
		ajaxJsonObjectByUrl("addBatchAccWageSchemeItemCode.do?isCheck=false&"+formData, {},
				function(responseData) {
					queryItem();
				});
	}
	

	function choose_item() {

		if (saveData == 0 && '${scheme_id}' == "") {

			$.ligerDialog.warn('请先保存方案！');

			return;
		}

		if ('${scheme_id}' != "") {

			saveData = '${scheme_id}';
		}

		$.ligerDialog
				.open({
					url : 'chooseItemMainPage.do?isCheck=false&wage_code='
							+ '${wage_code}' + '&scheme_id=' + saveData
							+ "&acc_year=" + '${acc_year}',
					height : 496,
					width : 720,
					modal : true,
					isResize : true,
					buttons : [ /* { text: '确定', onclick: function (item, dialog) { item_imp(item, dialog); },cls:'l-dialog-btn-highlight' }, */{
						text : '取消',
						onclick : function(item, dialog) {
							dialog.close();
						}
					} ]
				});

	}


	function item_remove() {
		var emp_data = rightgrid.getCheckedRows();
		if(emp_data.length == 0){
			$.ligerDialog.warn('请选择行');
			return;
		}

		var ParamVo = [];

		$(emp_data).each(function() {

			ParamVo.push(

			this.column_item + "@" + '${scheme_id}'

			)

		});

		$.ligerDialog.confirm('确定删除?', function(yes) {
			if (yes) {
				ajaxJsonObjectByUrl("deleteAccWageSchemeItem.do?isCheck=false",
						{
							ParamVo : ParamVo.toString()
						}, function(responseData) {
							if (responseData.state == "true") {
								rightgrid.reload();
							}
						});
			}
		});

	}

	// 保存方案
	function btn_add() {
		if($("#scheme_code").val()==""||$("#scheme_name").val()==""){
			$.ligerDialog.warn("方案编码与方案名称不能为空！");
			return;
		}
		var isGzt = 0;// 是否工资条
		if(liger.get("is_gzt")){
			isGzt = liger.get("is_gzt").getValue();
		}
		var param = {
			scheme_id : '${scheme_id}',
			scheme_code : $("#scheme_code").val(),
			scheme_name : $("#scheme_name").val(),
			wage_code : '${wage_code}',
			scheme_type_code : '${scheme_type_code}',
			is_gzt: isGzt
		};
		ajaxJsonObjectByUrl("setAccWageSchemeRela.do", param, 
			function(responseData) {
				if (responseData.error != undefined
						&& responseData.error != "") {
					$.ligerDialog.error(responseData.error);
					return;
				}
				if (responseData.state == "true") {
					saveData = responseData.scheme_id;
					item_url = "queryAccWageSchemeItem.do?isCheck=false&scheme_id="
							+ saveData
							+ "&wage_code="
							+ '${wage_code}'
							+ "&acc_year=" + '${acc_year}';
					emp_url = "queryAccWageSchemeEmpKind.do?isCheck=false&scheme_id=" 
							+ saveData + "&scheme_code=" + $("#scheme_code").val() + "&wage_code=" + '${wage_code}';
					<%--
					emp_url = "queryAccWageSKind.do?isCheck=false&scheme_id=" 
							+ saveData + "&scheme_code=" + $("#scheme_code").val() + "&wage_code=" + '${wage_code}';
					--%>
					parentFrameUse().btn_query();
				}
			}, 
			"json"
		);
	}

	function loadDict() {

		/* //加载工资套
		autocomplete("#wage_code","../queryAccWage.do?isCheck=false","id","text",true,true);
		//加载方案列表
		autocomplete("#scheme_name","../queryAccWageScheme.do?isCheck=false","id","text",true,true);
		 */

		if ('${scheme_code }' != "") {
			$("#scheme_code").ligerTextBox({ disabled : true });
			$("#scheme_code").attr("disabled", "disabled");
		}
		 
		autocomplete("#emp_code","../queryEmpDict.do?isCheck=false&is_stop=0","id","text",true,true);
		
		if('${scheme_type_code}' == '04'){
			$(".edit-is_gzt").show();
			$("#is_gzt").ligerComboBox({  
                data: [
                    { text: '是', id: '1' },
                    { text: '否', id: '0' }
                ],
				width: 80
            }); 
			liger.get("is_gzt").setValue('${is_gzt}');
		}
	}
	
	// 关闭
	function btn_colse(){
		frameElement.dialog.close();
	}
	
	<%--
	// 选择职工分类
	function choose_empKind() {
		if (saveData == "" && '${scheme_id}' == "") {
			$.ligerDialog.warn('请先保存方案！');
			return;
		}

		$.ligerDialog.open({
			url : 'chooseEmpKindMainPage.do?isCheck=false&wage_code=' + '${wage_code}' + '&scheme_id=' + saveData,
			height : 400,
			width : 500,
			modal : true,
			isResize : true,
			buttons : [
			    {
					text: '确定', 
					onclick: function (item, dialog) {
						var rows = dialog.frame.f_select();
				    	if(!rows){
				    		$.ligerDialog.warn('请选择行');
				    		return;
				    	}
				    	for(var i = 0; i < rows.length; i++){
				    		leftgrid.addRow(rows[i]);
				    	}
				    	dialog.close();
					}
				},
				{ 
					text : '取消', 
					onclick : function(item, dialog) {
						dialog.close();
					}
				} 
			]
		});
	}
	
	// 删除职工分类与方案关系
	function empKind_remove() {
		var data = leftGridManager.getCheckedRows();
		var paramVo = [];
		if(data.length == 0){
			$.ligerDialog.warn('请选择行');
			return;
		}else{
			$(data).each(function(){
				paramVo.push(this);
			});
			
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl(
						"deleteAccWageSKind.do?isCheck=false",
						{ paramVo : JSON.stringify(paramVo) }, 
						function(responseData) {
							if (responseData.state == "true") {
								leftgrid.reload();
							}
						}
					);
				}
			});
		}
	}
	
	// 保存职工分类与方案关系
	function empKind_save(){
		var data = leftGridManager.getData();
    	var paramVo = [];
        if (data.length == 0){
			$.ligerDialog.warn('请选择行');
			return;
        }else{
   			var flag = false;
        	$(data).each(function (){
        		for(var i = 0; i < data.length; i++){
        			flag = false;
        			for(var j = i + 1; j < data.length; j++){
        				if(data[i].kind_code == data[j].kind_code){
        					$.ligerDialog.warn('职工分类编码不能有重复数据');
        					flag = true;
        					return false;
        				}
        			}
        			if(flag){ return false; }
        		}
        		if(!flag){ paramVo.push(this); }
        	});
        	
        	if(flag){ return; }
        }
    	var formPara = {
			paramVo : JSON.stringify(paramVo),
			scheme_id : '${scheme_id}',
			scheme_code : $("#scheme_code").val(),
			wage_code : '${wage_code}'
		};
    	
    	ajaxJsonObjectByUrl("../accwagepay/saveAccWageSKind.do?isCheck=false", formPara, function(responseData){
    		queryEmpKind();
		});
	}
	--%>
</script>
<style type="text/css">
.middle input {
	display: block;
	width: 40px;
	margin-top: 30px;
}
</style>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div class="l-layout" id="layout1" style="height: 100%;">

		<div position="top">
			<table cellpadding="0" cellspacing="0" class="l-table-edit"
				style="margin-top: 10px">
				<tr>
					<td align="right" class="l-table-edit-td"
						style="padding-left: 20px;">工资套名称：</td>
					<td align="left" class="l-table-edit-td" colspan="2"><input
						name="wage_code" type="text" id="wage_code" value="${wage_name }"
						ltype="text" disabled="disabled" /></td>
					<td align="right" class="l-table-edit-td"
						style="padding-left: 20px;">方案编码：</td>
					<td align="left" class="l-table-edit-td" colspan="2"><input
						name="scheme_code" type="text" id="scheme_code"
						value="${scheme_code }" ltype="text"
						validate="{required:true,maxlength:18}" /></td>
					<td align="right" class="l-table-edit-td"
						style="padding-left: 20px;">方案名称：</td>
					<td align="left" class="l-table-edit-td" colspan="2"><input
						name="scheme_name" type="text" id="scheme_name"
						value="${scheme_name }" ltype="text"
						validate="{required:true,maxlength:18}" /></td>
						
					<td align="right" class="l-table-edit-td edit-is_gzt" style="padding-left: 20px;display: none;">是否工资条：</td>
					<td align="left" class="l-table-edit-td edit-is_gzt" colspan="2" style="display: none;">
						<input name="is_gzt" type="text" id="is_gzt"
							   ltype="text" validate="{required:true,maxlength:18}" />
					</td>
						
					<!-- <td align="left"><input class="l-button" type="button" style="width: 80px;margin-left: 20px" id="query" value="查询(Q)" onclick="btn_query();" /></td> -->
					<td align="left"><input class="l-button" type="button"
						style="width: 80px; margin-left: 20px" id="query" value="保存方案(A)"
						onclick="btn_add();" /></td>
					<td align="left"><input class="l-button" type="button"
						style="width: 80px; margin-left: 20px" id="colse" value="关闭"
						onclick="btn_colse();" /></td>
				</tr>
			</table>
		</div>
		<div position="left" title="  ">
		 	<table cellpadding="0" cellspacing="0" class="l-table-edit">
				<tr>
					<td align="right" class="l-table-edit-td" style="padding-left: 20px;">职工:</td>
					<td align="left" class="l-table-edit-td" colspan="2">
						<input name="emp_code" id="emp_code">
					</td>

					<td align="left" class="l-table-edit-td" colspan="2">
						<input class="l-button" type="button" style="width: 80px; margin-left: 20px" value="查询" onclick="querySEmp()">
					</td>
				</tr>
			</table>
			<div id="leftgrid"></div>
		</div>

		<div position="center" title="  ">
			<table cellpadding="0" cellspacing="0" class="l-table-edit">
				<tr>
					<td align="right" class="l-table-edit-td"
						style="padding-left: 20px;">工资项:</td>
					<td align="left" class="l-table-edit-td" colspan="2"><input
						name="item_code" id="item_code"></td>

					<td align="left" class="l-table-edit-td" colspan="2"><input
						class="l-button" type="button"
						style="width: 80px; margin-left: 20px" value="查询"
						onclick="queryItem()"></td>
				</tr>
			</table>
			<div id="rightgrid"></div>
		</div>
	</div>

</body>
</html>
