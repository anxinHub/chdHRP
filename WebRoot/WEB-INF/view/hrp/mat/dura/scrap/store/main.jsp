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
		grid.options.parms=[];
    	grid.options.newPage=1;
        //根据表字段进行添加查询条件
		grid.options.parms.push({name: 'begin_make_date', value: $("#begin_make_date").val()}); 
		grid.options.parms.push({name: 'end_make_date', value: $("#end_make_date").val()}); 
		
		grid.options.parms.push({name: 'store_id', value: liger.get("store_code").getValue().split(",")[0]}); 
		
		grid.options.parms.push({name: 'begin_confirm_date', value: $("#begin_confirm_date").val()}); 
		grid.options.parms.push({name: 'end_confirm_date', value: $("#end_confirm_date").val()}); 

		grid.options.parms.push({name: 'scrap_no', value: $("#scrap_no").val()}); 
		grid.options.parms.push({name: 'state', value: liger.get("state").getValue()}); 
		grid.options.parms.push({name : 'brief', value : $("#brief").val()});

    	//加载查询条件
    	grid.loadData(grid.where);
     }

	function loadHead(){
		grid = $("#maingrid").ligerGrid({
			columns: [ { 
				display: '单据号', name: 'scrap_no', width : 130,  align: 'left',
				render : function(rowdata, rowindex, value) {
					if(value == '合计'){
						return value;
					}
					return '<a href=javascript:openUpdate("' 
							+ rowdata.group_id
							+ ',' + rowdata.hos_id 
							+ ',' + rowdata.copy_code
							+ ',' + rowdata.scrap_id
							+ ',' + rowdata.store_id
							+ '")>'+rowdata.scrap_no+'</a>';
				}		 
			}, { 
				display: '摘要', name: 'brief', width : 200, align: 'left'
			}, { 
				display: '编制日期', name: 'make_date', width : 90, align: 'left'
			}, { 
				display: '库房', name: 'store_name', width : 120, align: 'left'
			}, { 
				display: '金额', name: 'sum_money', width : 90, align: 'right',
				render : function(rowdata, rowindex, value) {
					return value == null ? "" : formatNumber(value, paraMoney, 1);
				}
			}, { 
				display: '制单人', name: 'maker_name', width : 80, align: 'left'
			}, { 
				display: '审核人', name: 'checker_name', width : 80, align: 'left'
			}, { 
				display: '审核日期', name: 'check_date', width : 90, align: 'left'
			}, { 
				display: '确认人', name: 'confirmer_name', width : 80, align: 'left'
			}, { 
				display: '确认日期', name: 'confirm_date', width : 90, align: 'left'
			}, { 
				display: '状态', name: 'state', width : 100, align: 'left', render: fieldTypeRender
			} ],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatDuraScrapStore.do',
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
				text: '审核（<u>O</u>）', id: 'audit', click: audit, icon: 'audit' 
			}, { 
				line:true 
			}, { 
				text: '消审（<u>U</u>）', id: 'unAudit', click: unAudit, icon: 'unaudit' 
			}, { 
				line:true 
			}, { 
				text: '确认（<u>I</u>）', id: 'confirm', click: confirm, icon: 'account' 
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
				if(rowdata.scrap_id == null){
					$.ligerDialog.warn('请选择数据 ');
					return ; 
				}
				openUpdate(
					rowdata.group_id   + "," + 
					rowdata.hos_id  + "," + 
					rowdata.copy_code   + "," + 
					rowdata.scrap_id    + "," + 
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
		hotkeys('P', print);
	}
    
    //字段类型渲染器
    function fieldTypeRender(r, i, value){
        for (var i = 0, l = matDuraTran_state.Rows.length; i < l; i++){
            var o = matDuraTran_state.Rows[i];
            if(value == 3)
            	return "移出库"
            if (o.id == value) return o.text;
        }
        if(value == null){
        	return "";
        }
        return "未确认";
    }
    
    //是否显示复选框
    function isCheckDisplay(rowdata){
       	if(rowdata.scrap_id == null) return false;
         return true;
    }
    
    function add_open(){
    	
    	parent.$.ligerDialog.open({
			title: '库房报废添加',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/mat/dura/scrap/store/addPage.do?isCheck=false',
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
			$(data).each(function() {
				if(this.state > 1){nos = nos + this.scrap_no + ",";}
				ParamVo.push(
						this.group_id + "@" + 
						this.hos_id + "@"+ 
						this.copy_code + "@" +
						this.scrap_id 
				)
			});
			 if(nos != ""){
 				$.ligerDialog.error("删除失败！"+nos+"单据不是未审核状态");
 				return;
 			}
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteMatDuraScrapStore.do", {ParamVo : ParamVo.toString()}, function(responseData) {
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
			$.ligerDialog.error('请选择要审核的数据');
		} else {
			var ParamVo = [];
			var scrap_nos = "";
			$(data).each(function() {
				if(this.state != 1){
					scrap_nos = scrap_nos + this.scrap_no + ",";
				}
				ParamVo.push(
						this.group_id + "@"+ 
						this.hos_id + "@" + 
						this.copy_code + "@" +
						this.scrap_id
				)
			});
			if(scrap_nos != ""){
				$.ligerDialog.warn("审核失败！"+scrap_nos+"单据不是新建状态");
				return;
			}
			$.ligerDialog.confirm('确定审核?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("auditMatDuraScrapStore.do", {ParamVo : ParamVo.toString()}, function(responseData) {
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
			$.ligerDialog.error('请选择要消审的数据');
		} else {
			var ParamVo = [];
			var scrap_nos = "";
			$(data).each(function() {
				if(this.state != 2){
					scrap_nos = scrap_nos + this.scrap_no + ",";
				}
				ParamVo.push(
						this.group_id + "@"+ 
						this.hos_id + "@" + 
						this.copy_code + "@" +
						this.scrap_id
				)
			});
			if(scrap_nos != ""){
				$.ligerDialog.warn("消审失败！"+scrap_nos+"单据不是审核状态");
				return;
			}
			$.ligerDialog.confirm('确定消审?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("unAuditMatDuraScrapStore.do", {ParamVo : ParamVo.toString()}, function(responseData) {
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
			$.ligerDialog.error('请选择要确认的数据');
		} else {
			var ParamVo = [];
			var scrap_nos = "";
			var checker="";
			$(data).each(function() {
				if(this.state != 2){
					scrap_nos = scrap_nos + this.scrap_no + ",";
				}
				ParamVo.push(
						this.group_id + "@"+ 
						this.hos_id + "@" + 
						this.copy_code + "@" +
						this.scrap_id
				);
			});
			
			if(scrap_nos != ""){
				$.ligerDialog.warn("确认失败！"+scrap_nos+"单据不是审核状态");
				return;
			}
			$.ligerDialog.confirm('确定确认?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("confirmMatDuraScrapStore.do", {ParamVo : ParamVo.toString()}, function(responseData) {
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
			var scrap_nos = "";
			$(data).each(function() {
				if(this.state != 3){
					scrap_nos = scrap_nos + this.scrap_no + ",";
				}
				ParamVo.push(
					this.group_id + "@"+ 
					this.hos_id + "@" + 
					this.copy_code + "@" +
					this.scrap_id
				)
			});
			if(scrap_nos != ""){
				$.ligerDialog.warn("冲账失败！"+scrap_nos+"单据不是确认状态");
				return;
			}
			$.ligerDialog.confirm('确定冲账?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("offsetMatDuraScrapStore.do", {ParamVo : ParamVo.toString()}, function(responseData) {
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
			+ "scrap_id=" + voStr[3].toString() + "&" 
			+ "store_id=" + voStr[4].toString();
		
		parent.$.ligerDialog.open({
			title: '库房报废修改',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/mat/dura/scrap/store/updatePage.do?isCheck=false&' + paras.toString(),
			modal: true, showToggle: false, showMax: true, showMin: true, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});   
	}
	
	function loadDict() {
		//字典下拉框
		autocomplete("#store_code", "../../../queryMatStore.do?isCheck=false", "id", "text", true, true);
		
		autoCompleteByData("#state", matDuraScrap_state.Rows, "id", "text",true, true);
		
		//格式化日期框和文本框
		$("#begin_make_date").ligerTextBox({width : 100});
        autodate("#begin_make_date", "yyyy-mm-dd", "month_first");
		$("#end_make_date").ligerTextBox({width : 100});
        autodate("#end_make_date", "yyyy-mm-dd", "month_last");
		
		$("#begin_confirm_date").ligerTextBox({width : 100});
		$("#end_confirm_date").ligerTextBox({width : 100});
		
		$("#scrap_no").ligerTextBox({width : 160});
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
    	parent.$.ligerDialog.open({url : 'hrp/mat/dura/scrap/store/printSetPage.do?template_code=041323&use_id='+useId,
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
			
			var scrap_id ="" ;
			var scrap_nos = "";
			$(data).each(function (){		
				if(this.state != 3){
					scrap_nos = scrap_nos + this.scrap_no + "<br>";
				}
				scrap_id  += this.scrap_id+","
			});
			
 			if(scrap_nos != ""){
 				$.ligerDialog.error("打印失败！<br>以下单据不是移入确认状态：<br>"+scrap_no);
				return;
			} 
			
 			var para={
 					paraId : scrap_id.substring(0,scrap_id.length-1) ,  
 					template_code:'041323',
 					class_name:"com.chd.hrp.mat.serviceImpl.dura.scrap.MatDuraScrapStoreServiceImpl",
 					method_name:"queryDataByPrintTemlate",
 					isPreview:true,//预览窗口，传绝对路径
 					use_id:useId,
 					p_num:1 
 			};
 			officeFormPrint(para);
 			
			/* var para={
	    			paraId :scrap_id.substring(0,scrap_id.length-1) ,
	    			
	    			template_code:'04015',
	    			isPrintCount:false,//更新打印次数
	    			isPreview:true,//预览窗口，传绝对路径
	    			use_id:useId,
	    			p_num:1
	    			//isSetPrint:flag
	    	};  
		 	
			//alert(JSON.stringify(para));
	    	printTemplate("hrp/mat/dura/scrap/store/queryDataByPrintTemlate.do?isCheck=false",para); */
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
			            <td align="left"><input name="begin_make_date" type="text" id="begin_make_date" ltype="text"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
			            <td align="left" class="l-table-edit-td">至：</td>
			            <td align="left" class="l-table-edit-td"><input name="end_make_date" type="text" id="end_make_date" ltype="text"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
					</tr>
				</table>
			</td>

			<td align="right" class="l-table-edit-td">
				库房：
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
				确认日期：
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
			
			<td align="right" class="l-table-edit-td" >
				单据号：
			</td>
			<td align="left" class="l-table-edit-td" >
				<input name="scrap_no" type="text" id="scrap_no" ltype="text" />
			</td>
			
			<td align="right" class="l-table-edit-td" >
				资金标注：
			</td>
			<td align="left" class="l-table-edit-td" >
				<input name="a" type="text" id="a" ltype="text" />
			</td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td">
				摘要：
			</td>
			<td align="left" class="l-table-edit-td">
				<input name="brief" type="text" id="brief" ltype="text" validate="{required:false,maxlength:100}" />
			</td>
			<td align="right" class="l-table-edit-td" colspan="4">
				<button id="query" type="button" accessKey="Q"><b>查询（<u>Q</u>）</b></button> &nbsp;&nbsp;&nbsp;&nbsp;
			</td>
		</tr>
    </table>

	<div id="maingrid"></div>
</body>
</html>
