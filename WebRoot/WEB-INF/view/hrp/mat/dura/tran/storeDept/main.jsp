<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"  xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script src="<%=path%>/lib/hrp/mat/mat.js" type="text/javascript"></script>
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
	var paraMoney = '${p04005}';
	var paraPrice = '${p04006}';
	
    $(function ()
    {
        loadDict();//加载下拉框
    	//加载数据
    	loadHead(null);	
        
		loadHotkeys();
		query();
    });
    //查询
    function  query(){
    	
    	/* if(!liger.get("store_code").getValue()){
    		$.ligerDialog.warn("请选择库房！");
    		return false;
    	} */
    	
		grid.options.parms=[];
    	grid.options.newPage=1;
        //根据表字段进行添加查询条件
		grid.options.parms.push({name: 'begin_dura_date', value: $("#begin_dura_date").val()}); 
		grid.options.parms.push({name: 'end_dura_date', value: $("#end_dura_date").val()}); 
		
		grid.options.parms.push({name: 'store_id', value: liger.get("store_code").getValue().split(",")[0]}); 
		grid.options.parms.push({name: 'state', value: liger.get("state").getValue()}); 
		
		grid.options.parms.push({name: 'bus_type_code', value: liger.get("bus_type_code").getValue()}); 
		grid.options.parms.push({name: 'dept_id', value: liger.get("dept_code").getValue().split(",")[0]}); 
		
		grid.options.parms.push({name: 'begin_confirm_date', value: $("#begin_confirm_date").val()}); 
		grid.options.parms.push({name: 'end_confirm_date', value: $("#end_confirm_date").val()}); 

		grid.options.parms.push({name : 'brief', value : $("#brief").val()});
		grid.options.parms.push({name: 'dura_no', value: $("#dura_no").val()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }

	function loadHead(){
		grid = $("#maingrid").ligerGrid({
			columns: [ { 
				display: '单据号', name: 'dura_no', width : 130,  align: 'left',
				render : function(rowdata, rowindex, value) {
					if(value == '合计'){
						return value;
					}
					return '<a href=javascript:openUpdate("' 
							+ rowdata.group_id
							+ ',' + rowdata.hos_id 
							+ ',' + rowdata.copy_code
							+ ',' + rowdata.dura_id
							+ ',' + rowdata.store_id
							+ '")>'+rowdata.dura_no+'</a>';
				}		 
			}, { 
				display: '业务类型', name: 'bus_type_name', width : 100, align: 'left'
			}, { 
				display: '摘要', name: 'brief', width : 200, align: 'left'
			}, { 
				display: '编制日期', name: 'make_date', width : 90, align: 'left'
			}, { 
				display: '移出库房', name: 'store_name', width : 120, align: 'left'
			}, { 
				display: '移入科室', name: 'dept_name', width : 120, align: 'left'
			}, { 
				display: '金额', name: 'sum_money', width : 90, align: 'right',
				render : function(rowdata, rowindex, value) {
					return value == null ? "" : formatNumber(value, paraMoney, 1);
				}
			}, { 
				display: '制单人', name: 'maker_name', width : 80, align: 'left'
			}, { 
				display: '移出确认人', name: 'checker_name', width : 80, align: 'left'
			}, { 
				display: '移出日期', name: 'check_date', width : 90, align: 'left'
			}, { 
				display: '移入确认人', name: 'confirmer_name', width : 80, align: 'left'
			}, { 
				display: '移入日期', name: 'confirm_date', width : 90, align: 'left'
			}, { 
				display: '状态', name: 'state', width : 100, align: 'left', render: fieldTypeRender
			} ],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatDuraTranStoreDept.do',
			width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad:true,
			checkBoxDisplay:isCheckDisplay,
			selectRowButtonOnly:true,//heightDiff: -10,
			onsuccess:function(){
			
			},
			toolbar: { items: [ { 
				/* text: '查询（<u>Q</u>）', id: 'search', click: query, icon: 'search' 
			}, { 
				line:true 
			}, {  */
				text: '添加（<u>A</u>）', id: 'add', click: add_open, icon: 'add' 
			}, { 
				line:true 
			}, { 
				text: '移出确认（<u>O</u>）', id: 'audit', click: audit, icon: 'audit' 
			}, { 
				line:true 
			}, { 
				text: '取消移出确认（<u>U</u>）', id: 'unAudit', click: unAudit, icon: 'unaudit' 
			}, { 
				line:true 
			}, { 
				text: '移入确认（<u>I</u>）', id: 'confirm', click: confirm, icon: 'account' 
			}, { 
				line:true 
			/* }, { 
				text: '冲账（<u>C</u>）', id: 'offset', click: offset, icon: 'offset' 
			}, { 
				line:true  */
			}, { 
				text: '删除（<u>D</u>）', id: 'delete', click: remove, icon: 'delete' 
			}, { 
				line:true 
			}, { 
				text: '批量打印（<u>P</u>）', id: 'print', click: print, icon: 'print' 
			}] },
			onDblClickRow : function (rowdata, rowindex, value){
				if(rowdata.dura_id == null){
					$.ligerDialog.warn('请选择数据 ');
					return ; 
				}
				openUpdate(
					rowdata.group_id   + "," + 
					rowdata.hos_id  + "," + 
					rowdata.copy_code   + "," + 
					rowdata.dura_id    + "," + 
					rowdata.store_id 
				);
			} 
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    //键盘事件
	function loadHotkeys() {
		//hotkeys('Q', query);
		hotkeys('A', add_open);
		hotkeys('D', remove);
		hotkeys('O', audit);
		hotkeys('U', unAudit);
		hotkeys('I', confirm);
		hotkeys('C', offset);
		hotkeys('H', btn_saveColumn);
		hotkeys('P', print);
	}
	
    //保存列
    function btn_saveColumn(){
		
		var path = window.location.pathname+"/maingrid";
		var url = '../../../../sys/addBatchSysTableStyle.do?isCheck=false';
		saveColHeader({
			grid: grid,
			path: path,
			url: url,
			callback: function(data){
				$.ligerDialog.success("保存成功");
			}
		});
	  
		return false;
	}
    
    //字段类型渲染器
    function fieldTypeRender(r, i, value){
        for (var i = 0, l = matDuraTran_state.Rows.length; i < l; i++){
            var o = matDuraTran_state.Rows[i];
            if (o.id == value) return o.text;
        }
        if(value == null){
        	return "";
        }
        return "未确认";
    }
    
    //是否显示复选框
    function isCheckDisplay(rowdata){
       	if(rowdata.dura_id == null) return false;
         return true;
    }
    
    function add_open(){
    	
    	parent.$.ligerDialog.open({
			title: '(库-科)添加',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/mat/dura/tran/storeDept/addPage.do?isCheck=false',
			modal: true, showToggle: false, showMax: true, showMin: true, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});   
	}
    	
    function remove(){

		var data = gridManager.getCheckedRows();
		
		if (data.length == 0) {
			$.ligerDialog.error('请选择要删除的数据');
		} else {
			var ParamVo = [];
			var nos = "";
			$(data).each(
					function() {
						if(this.state > 1){nos = nos + this.dura_no + ",";}
						ParamVo.push(
								this.group_id + "@" + 
								this.hos_id + "@"+ 
								this.copy_code + "@" +
								this.dura_id 
								)
			});
			 if(nos != ""){
 				$.ligerDialog.error("删除失败！"+nos+"单据不是未确认状态");
 				return;
 			}
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteMatDuraTranStoreDept.do", {ParamVo : ParamVo.toString()}, function(responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			});
		}
	}
    
    function audit(){

		var data = gridManager.getCheckedRows();
		
		if (data.length == 0) {
			$.ligerDialog.error('请选择要移出确认的数据');
		} else {
			var ParamVo = [];
			var dura_nos = "";
			$(data).each(
					function() {
						if(this.state != 1){
							dura_nos = dura_nos + this.dura_no + ",";
						}
						ParamVo.push(
								this.group_id + "@"+ 
								this.hos_id + "@" + 
								this.copy_code + "@" +
								this.dura_id
								)
			});
			if(dura_nos != ""){
				$.ligerDialog.warn("移出确认失败！"+dura_nos+"单据不是未确认状态");
				return;
			}
			$.ligerDialog.confirm('确定移出确认?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("auditMatDuraTranStoreDept.do", {ParamVo : ParamVo.toString()}, function(responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			});
		}
	}
    
    function unAudit(){

		var data = gridManager.getCheckedRows();
		
		if (data.length == 0) {
			$.ligerDialog.error('请选择要取消移出确认的数据');
		} else {
			var ParamVo = [];
			var dura_nos = "";
			$(data).each(
					function() {
						if(this.state != 2){
							dura_nos = dura_nos + this.dura_no + ",";
						}
						ParamVo.push(
								this.group_id + "@"+ 
								this.hos_id + "@" + 
								this.copy_code + "@" +
								this.dura_id
								)
			});
			if(dura_nos != ""){
				$.ligerDialog.warn("取消移出确认失败！"+dura_nos+"单据不是移出确认状态");
				return;
			}
			$.ligerDialog.confirm('确定取消移出确认?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("unAuditMatDuraTranStoreDept.do", {ParamVo : ParamVo.toString()}, function(responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			});
		}
	}
    
    function confirm(){

		var data = gridManager.getCheckedRows();
		
		if (data.length == 0) {
			$.ligerDialog.error('请选择要移入确认的数据');
		} else {
			var ParamVo = [];
			var dura_nos = "";
			var checker="";
			$(data).each(
					function() {
						if(this.checker=='${user_id}'){
							checker=checker+this.dura_no+",";
						}
						if(this.state != 2){
							dura_nos = dura_nos + this.dura_no + ",";
						}
						ParamVo.push(
								this.group_id + "@"+ 
								this.hos_id + "@" + 
								this.copy_code + "@" +
								this.dura_id
								);
						
			});
			
			if(checker!= ""){
				$.ligerDialog.warn("移入确认失败！"+checker+"单据移入人与移出人一样");
				return;
			} 
			
			if(dura_nos != ""){
				$.ligerDialog.warn("移入确认失败！"+dura_nos+"单据不是移出确认状态");
				return;
			}
			$.ligerDialog.confirm('确定移入确认?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("confirmMatDuraTranStoreDept.do", {ParamVo : ParamVo.toString()}, function(responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			});
		}
	}
    
    function offset(){

		var data = gridManager.getCheckedRows();
		
		if (data.length == 0) {
			$.ligerDialog.error('请选择要冲账的数据');
		} else {
			var ParamVo = [];
			var dura_nos = "";
			$(data).each(function() {
				if(this.state != 3){
					dura_nos = dura_nos + this.dura_no + ",";
				}
				ParamVo.push(
					this.group_id + "@"+ 
					this.hos_id + "@" + 
					this.copy_code + "@" +
					this.dura_id
				)
			});
			if(dura_nos != ""){
				$.ligerDialog.warn("冲账失败！"+dura_nos+"单据不是移入确认状态");
				return;
			}
			$.ligerDialog.confirm('确定冲账?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("offsetMatDuraTranStoreDept.do", {ParamVo : ParamVo.toString()}, function(responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			});
		}
	}

	function openUpdate(obj) {

		var voStr = obj.split(",");
		var paras = 
			"group_id=" + voStr[0].toString() + "&" 
			+ "hos_id=" + voStr[1].toString() + "&" 
			+ "copy_code=" + voStr[2].toString() + "&" 
			+ "dura_id=" + voStr[3].toString() + "&" 
			+ "store_id=" + voStr[4].toString();
		
		parent.$.ligerDialog.open({
			title: '(库-科)修改',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/mat/dura/tran/storeDept/updatePage.do?isCheck=false&' + paras.toString(),
			modal: true, showToggle: false, showMax: true, showMin: true, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});   
	}
	
	function loadDict() {
		//字典下拉框
		autocompleteAsync("#store_code", "../../../queryMatStore.do?isCheck=false", "id", "text", true, true, "", true);
		autocomplete("#dept_code", "../../../queryHosDeptDictByPerm.do?isCheck=false", "id", "text", true, true, {is_last : 1});
		autocomplete("#bus_type_code", "../../../queryMatBusType.do?isCheck=false", "id", "text", true, true, {codes : '37,38'});
		//autocomplete("#bus_type_code1", "../../../queryMatBusType.do?isCheck=false", "id", "text", true, true, {codes : '37,38'});
		
		autoCompleteByData("#state", matDuraTran_state.Rows, "id", "text",true, true);
		
		//格式化日期框和文本框
		$("#begin_dura_date").ligerTextBox({width : 100});
        autodate("#begin_dura_date", "yyyy-mm-dd", "month_first");
		$("#end_dura_date").ligerTextBox({width : 100});
        autodate("#end_dura_date", "yyyy-mm-dd", "month_last");
		
		$("#begin_confirm_date").ligerTextBox({width : 100});
		$("#end_confirm_date").ligerTextBox({width : 100});
		
		$("#dura_no").ligerTextBox({width : 160});
		$("#brief").ligerTextBox({width : 238});
		
		//格式化按钮
		$("#query").ligerButton({ click: query, width: 90 });
	}
	
	//打印模板设置
    function printSet(){
	  
    	var useId=0;//统一打印
		if('${p04020}'==1){
			//按用户打印
			useId='${sessionScope.user_id }';
		}
    	parent.$.ligerDialog.open({url : 'hrp/mat/dura/tran/storeDept/printSetPage.do?template_code=041307&use_id='+useId,
    		data:{}, height: $(parent).height(),width: $(parent).width(), title:'打印模板设置',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true
    		});
    }

	//打印
    function print(){
    	 var useId=0;//统一打印
 		if('${p04020}'==1){
 			//按用户打印
 			useId='${sessionScope.user_id }';
 		}

 		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
			
			var dura_id ="" ;
			var dura_nos = "";
			$(data).each(function (){		
				if(this.state != 3){
					dura_nos = dura_nos + this.dura_no + "<br>";
				}
				dura_id  += this.dura_id+","
			});
			
 			if(dura_nos != ""){
 				$.ligerDialog.error("打印失败！<br>以下单据不是移入确认状态：<br>"+in_nos);
				return;
			} 
			
 			var para={
 					paraId : dura_id.substring(0,dura_id.length-1) , 
 					template_code:'041307',
 					class_name:"com.chd.hrp.mat.serviceImpl.dura.tran.MatDuraTranStoreDeptServiceImpl",
 					method_name:"queryDataByPrintTemlate",
 					isPreview:true,//预览窗口，传绝对路径
 					use_id:useId,
 					p_num:1
 			}; 
 			officeFormPrint(para);
		}
    }
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
		<tr>
			<td align="right" class="l-table-edit-td">
				制单日期：
			</td>
			<td align="left" class="l-table-edit-td">
				<table cellpadding="0" cellspacing="0" class="l-table-edit" >
					<tr>
			            <td align="left"><input name="begin_dura_date" type="text" id="begin_dura_date" ltype="text"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
			            <td align="left" class="l-table-edit-td">至：</td>
			            <td align="left" class="l-table-edit-td"><input name="end_dura_date" type="text" id="end_dura_date" ltype="text"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
					</tr>
				</table>
			</td>

			<td align="right" class="l-table-edit-td">
				<!-- <span style="color: red">*</span> -->库房：
			</td>
			<td align="left" class="l-table-edit-td">
				<input name="store_code" type="text" id="store_code" ltype="text" />
			</td>
			
			<td align="right" class="l-table-edit-td">
				状   态：
			</td>
			<td align="left" class="l-table-edit-td" >
				<input name="state" type="text" id="state" ltype="text" />
			</td>
		</tr> 
		<tr>
			<td align="right" class="l-table-edit-td" >
				移库日期：
			</td>
			<td align="left" class="l-table-edit-td">
				<table cellpadding="0" cellspacing="0" class="l-table-edit" >
					<tr>
			            <td align="left"><input name="begin_confirm_date" type="text" id="begin_confirm_date" ltype="text"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
			            <td align="left" class="l-table-edit-td">至：</td>
			            <td align="left" class="l-table-edit-td"><input name="end_confirm_date" type="text" id="end_confirm_date" ltype="text"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
					</tr>
				</table>
			</td>
			
			<td align="right" class="l-table-edit-td">
				业务类型：
			</td>
			<td align="left" class="l-table-edit-td" >
				<input name="bus_type_code" type="text" id="bus_type_code" ltype="text" />
			</td>
			            
			<td align="right" class="l-table-edit-td" >
				领用科室：
			</td>
			<td align="left" class="l-table-edit-td">
				<input name="dept_code" type="text" id="dept_code" ltype="text" />
			</td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td">
				摘要：
			</td>
			<td align="left" class="l-table-edit-td">
				<input name="brief" type="text" id="brief" ltype="text" validate="{required:false,maxlength:100}" />
			</td>
			
			<td align="right" class="l-table-edit-td" >
				单据号：
			</td>
			<td align="left" class="l-table-edit-td" >
				<input name="dura_no" type="text" id="dura_no" ltype="text" />
			</td>
			
			<!-- <td align="right" class="l-table-edit-td">
				是否流转生成：
			</td>
			<td align="left" class="l-table-edit-td" >
				<input name="bus_type_code1" type="text" id="bus_type_code1" ltype="text" />
			</td> -->
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" colspan="6">
				<button id="query" type="button" accessKey="Q"><b>查询（<u>Q</u>）</b></button> &nbsp;&nbsp;&nbsp;&nbsp;
			</td>
		</tr>
    </table>

	<div id="maingrid"></div>
</body>
</html>
