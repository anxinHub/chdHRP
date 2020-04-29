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
<script type="text/javascript">
	var grid;
	var gridManager = null;
	var userUpdateStr;
	$(function() {
		loadDict();//加载下拉框
		//加载数据
		loadHead(null);
		loadHotkeys();
		
		/************************************/
		$("#invoice_no").ligerTextBox({
			 width:160
		});
		$("#invoice_date_begin").ligerTextBox({
			width : 90
		});
		$("#invoice_date_end").ligerTextBox({
			width : 90
		});
		/************************************/
		$("#change_date_beg").ligerTextBox({
			width : 90
		});
		$("#change_date_end").ligerTextBox({
			width : 90
		});
		$("#create_date_begin").ligerTextBox({
			width : 90
		});
		$("#create_date_end").ligerTextBox({
			width : 90
		});
		$("#state").ligerComboBox({
			width : 160
		});
		$("#ven_id").ligerTextBox({
			width : 160
		});
		$("#create_emp").ligerTextBox({
			width : 160
		});
		var menu1 = { width: 120, items:
	           [
		           //{ text: '导入合同单', click: itemclick,id:'ImportContract',icon:'import' },
		           //{ text: '导入招标单', click: itemclick,id:'importBid',icon:'import' }
	           ]
	       };
	 
	       var menu2 = { width: 120, items:
	           [
	             //{ text: '生成退货单', click: itemclick,id:'initBack',icon:'initial' },
	             //{ text: '生成发票单', click: itemclick,id:'initBill',icon:'initial' },
	             //{ text: '生成科室领用单', click: itemclick,id:'initTranster',icon:'initial' }
	           ]
	       };

		$("#topmenu").ligerToolBar({ items: [
						{
							text : '查询（<u>E</u>）',
							id : 'search',
							click : query,
							icon : 'search'
						}, {
							text : '添加（<u>A</u>）',
							id : 'add',
							click : add_open,
							icon : 'add'
						},  {
							text : '删除（<u>D</u>）',
							id : 'delete',
							click : remove,
							icon : 'delete'
						}, 
						 /** {
							text : '审核（<u>S</u>）',
							id : 'toExamine',
							click : toExamine,
							icon : 'ok'
						},{
							text : '销审（<u>X</u>）',
							id : 'notToExamine',
							click : notToExamine,
							icon : 'bcancle'
						},*/
						 {
						text : '变动确认（<u>B</u>）',
						id : 'confirm',
						click : confirm,
						icon : 'right'
					    },
                          /*  { text: '导入', menu: menu1 },
                           { text: '生成', menu: menu2 }, */
   						 { text: '模板设置', id:'printSet', click: printSet, icon:'print' },
   						 { line:true } ,
   						 { text: '批量打印', id:'print', click: print, icon:'print' }
                            
                     ]
		});

	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		/*******************************************************/
		
		grid.options.parms.push({
			name:'invoice_no',
			value:liger.get("invoice_no").getValue()
		}); 
		grid.options.parms.push({
			name : 'invoice_date_beg',
			value : $("#invoice_date_begin").val()
		});
		grid.options.parms.push({
			name:'invoice_date_end',
			value:$("#invoice_date_end").val()
		}); 
		
		/******************************************************/
		grid.options.parms.push({
			name : 'create_emp',
			value : liger.get("create_emp").getValue()
		});
		grid.options.parms.push({
			name : 'ven_id',
			value : liger.get("ven_id").getValue().split("@")[0]
		});
		grid.options.parms.push({
			name : 'ven_no',
			value : liger.get("ven_id").getValue().split("@")[1]
		});
		grid.options.parms.push({
			name : 'create_date_beg',
			value : $("#create_date_begin").val()
		});
		grid.options.parms.push({
			name : 'create_date_end',
			value : $("#create_date_end").val()
		});
		grid.options.parms.push({
			name : 'state',
			value : liger.get("state").getValue()
		});
		grid.options.parms.push({
			name : 'change_date_beg',
			value : $("#change_date_beg").val()
		});
		grid.options.parms.push({
			name : 'change_date_end',
			value : $("#change_date_end").val()
		});
		//加载查询条件
		grid.loadData(grid.where);
	}
	

	function loadHead() {
		grid = $("#maingrid").ligerGrid(
				{
					columns : [  {
						display : '变更单号',
						name : 'change_no',
						align : 'left',
						width : 120,
						render : function(rowdata, rowindex,
								value) {
							if(rowdata.note == "合计"){
								return '';
							}
							return "<a href=javascript:openUpdate('"+rowdata.group_id + "|" + rowdata.hos_id
							+ "|" + rowdata.copy_code + "|"
							+ rowdata.change_no  +"')>"+rowdata.change_no+"</a>";
						}, frozen: true
					}, {
						display : '摘要',
						name : 'note',
						align : 'left',
						width : 160,
						frozen: true
					},
					
					
					{
						display : '发票号',
						name : 'invoice_no',
						align : 'left',
						width : 160,
						frozen: true
					},{
						display : '发票日期',
						name : 'invoice_date',
						align : 'left',
						width : 160,
						frozen: true
					},
					
					{
						display : '供应商',
						name : 'ven_name',
						align : 'left',
						width : 240
					}, {
						display : '制单人',
						name : 'create_emp_name',
						align : 'left',
						width : 120
					}, {
						display : '制单日期',
						name : 'create_date',
						align : 'left',
						width : 120
					}, {
						display : '确认人',
						name : 'audit_emp_name',
						align : 'left',
						width : 120
					}, {
						display : '确认日期',
						name : 'change_date',
						align : 'left',
						width : 120
					}, {
						display : '状态',
						name : 'state_name',
						align : 'left',
						width : 100
					}],
					dataAction : 'server',
					dataType : 'server',
					usePager : true,
					url : 'queryAssChangeSpecial.do',
					width : '100%',
					height : '100%',
					checkbox : true,
					rownumbers : true,
					delayLoad :true,
					checkBoxDisplay : isCheckDisplay,
					selectRowButtonOnly : true,//heightDiff: -10,
					onDblClickRow : function(rowdata, rowindex, value) {
						openUpdate(rowdata.group_id + "|" + rowdata.hos_id
								+ "|" + rowdata.copy_code + "|"
								+ rowdata.change_no);
					}
				});
		gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	function isCheckDisplay(rowdata) {
		if (rowdata.note == "合计")
			return false;
		return true;
	}
	
	function toExamine(){
		
	}
	
	function notToExamine(){
		
	}
	
	function itemclick(item){
		 if(item.id)
	        {
	            switch (item.id)
	            {
	                case "importBid":
	                	return;
	                case "ImportContract":
	               	 	return;
	                case "initBack":
	                	return;
	                case "initBill":
	                	return;
	                case "initTranster":
	                	return;
	            }
	        }    
	}
	
	
	//入库确认
	function confirm(){
		var ParamVo = [];
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			$(data).each(
					function() {
						ParamVo.push(this.group_id + "@" + this.hos_id + "@"
								+ this.copy_code + "@" + this.change_no);
					});
			$.ligerDialog.confirm('变动确认?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("updateConfirmChangeSpecial.do", {
						ParamVo : ParamVo.toString()
					}, function(responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			});	
		}
	}

	function add_open() {
		
		parent.$.ligerDialog.open({
			title: '原值变动添加',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/ass/assspecial/asschange/assChangeSpecialAddPage.do?isCheck=false&',
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
	}

	function remove() {

		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var ParamVo = [];
			$(data).each(
					function() {
						ParamVo.push(this.group_id + "@" + this.hos_id + "@"
								+ this.copy_code + "@" + this.change_no  );
					});
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteAssChangeSpecial.do", {
						ParamVo : ParamVo.toString()
					}, function(responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			});
		}
	}

	function openUpdate(obj) {

		var vo = obj.split("|");
		if("null"==vo[3]){
			return false;
			
		}
		var parm = "group_id=" + vo[0] + "&" + "hos_id=" + vo[1] + "&"
				+ " copy_code=" + vo[2] + "&" + "change_no=" + vo[3];

		parent.$.ligerDialog.open({
			title: '原值变动修改',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/ass/assspecial/asschange/assChangeSpecialUpdatePage.do?isCheck=false&'+parm,
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
	}
	function loadDict() {
		var param = {query_key:''};
		
		autocomplete("#ven_id", "../../queryHosSupDict.do?isCheck=false","id", "text",true,true,param,true,null,"300");
		
		autocomplete("#create_emp", "../../queryUserDict.do?isCheck=false","id", "text",true,true,param,true,null,"160");
		$('#state').ligerComboBox({
			data:[{id:0,text:'新建'},{id:1,text:'审核'},{id:2,text:'确认'}],
			valueField: 'id',
            textField: 'text',
			cancelable:true,
			width:160
		});
		autodate("#create_date_begin","YYYY-mm-dd","month_first");

		autodate("#create_date_end","YYYY-mm-dd","month_last");
		
		/****************************************************/
		
		 $("#invoice_no").ligerTextBox({width:160});
		
		 /***************************************************/
	}
	//键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);

		hotkeys('A', add_open);
		hotkeys('D', remove);


		hotkeys('P', print);

	}
	//打印模板设置
    function printSet(){
	  
    	var useId=0;//统一打印
		if('${ass_05021}'==1){
			//按用户打印
			useId='${user_id }';
		}else if('${ass_05021}'==2){
			//按仓库打印
			if(liger.get("store_id").getValue()==""){
				$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
				return;
			}
			useId=liger.get("store_id").getValue().split(",")[0];
		}
    	
		officeFormTemplate({template_code:"05021",use_id : useId});
    }


    //打印
    function print(){
    	var useId=0;//统一打印
 		if('${ass_05021}'==1){
 			//按用户打印
 			useId='${user_id }';
 		}else if('${ass_05021}'==2){
 			//按仓库打印
 			if(liger.get("store_id").getValue()==""){
 				$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
 				return;
 			}
 			useId=liger.get("store_id").getValue().split(",")[0];
 		}

 		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
		
			var change_no ="" ;
			$(data).each(function (){
				
				change_no  += "'"+this.change_no+"',"
					
			});
			
			 var para={
	    			paraId :change_no.substring(0,change_no.length-1) ,
	    			class_name:"com.chd.hrp.ass.serviceImpl.change.AssChangeSpecialServiceImpl",
	    			method_name:"assChangeSpecialByPrintTemlate",
	    			template_code:'05021',
	    			isPrintCount:false,//更新打印次数
	    			isPreview:true,//预览窗口，传绝对路径
	    			use_id:useId,
	    			p_num:1
	    			//isSetPrint:flag
	    	 }; 
		 
           	ajaxJsonObjectByUrl("queryState.do?isCheck=false",{paraId :change_no.substring(0,change_no.length-1) },function (responseData){
           		if(responseData.state=="true"){
           		   officeFormPrint(para);
           		}
           	});
	   }
    }
	
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" border="0" id="table1" width="100%">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">制单日期：</td>
			<td align="left" width="5%"><input
				name="create_date_begin" type="text" id="create_date_begin"
				  class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="left" width="2%">至：</td>
			<td align="left"><input name="create_date_end" type="text"
				id="create_date_end" 
				 class="Wdate"
				onFocus="WdatePicker({minDate:'#F{$dp.$D(\'create_date_begin\');}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">制单人：</td>
			<td align="left" class="l-table-edit-td"><input name="create_emp"
				type="text" id="create_emp" 
				 /></td>
				 <td align="right"  class="l-table-edit-td" style="padding-left: 20px;">供应商：</td>
			<td align="left"  class="l-table-edit-td"><input name="ven_id"
				type="text" id="ven_id" 
				 /></td>		 
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">确认日期：</td>
			<td align="left" ><input name="change_date_beg"
				type="text" id="change_date_beg" 
				 class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
				<td align="left">至：</td>
			<td align="left"><input name="change_date_end" type="text"
				id="change_date_end" 
				 class="Wdate"
				onFocus="WdatePicker({minDate:'#F{$dp.$D(\'change_date_beg\');}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态：</td>
			<td align="left" class="l-table-edit-td">
				<!-- <select id="state" name="state">
            		<option value="">全部</option>
            		<option value="0">新建</option>
            		<option value="1">审核</option>
            		<option value="2">确认</option>
            	</select> -->
            	<input  name="state" type="text" id="state"/>
			</td>
		</tr>
		<tr>
		 <td align="right" class="l-table-edit-td" style="padding-left: 20px;">发票日期：</td>
			<td align="left" width="5%"><input
				name="invoice_date_begin" type="text" id="invoice_date_begin"
				  class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="left" width="2%">至：</td>
			<td align="left"><input name="invoice_date_end" type="text"
				id="invoice_date_end" 
				 class="Wdate"
				onFocus="WdatePicker({minDate:'#F{$dp.$D(\'invoice_date_begin\');}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="right"  class="l-table-edit-td" style="padding-left: 20px;">发&nbsp;&nbsp;&nbsp;票&nbsp;&nbsp;&nbsp;&nbsp;号：</td>
			 <td align="left"  class="l-table-edit-td" colspan = "3"><input name="invoice_no" type="text" id="invoice_no" /></td>
		</tr>
	</table>
	<div id="topmenu" style="background:#FFFFFF; color:#FFFFFF; border:1px solid #A4D3EE" ></div>
	<div id="maingrid"></div>
</body>
</html>
