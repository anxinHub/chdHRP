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
		                                 /*     { text: '导入', menu: menu1 },
		                                     { text: '生成', menu: menu2 }, */
		                                     {
													text : '打印（<u>P</u>）',
													id : 'print',
													click : printDate,
													icon : 'print'
												}
		                                     
		                                 ]
		});

	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
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
						display : '记录单号',
						name : 'record_no',
						align : 'left',
						width : 120,
						render : function(rowdata, rowindex,
								value) {
							if(rowdata.note == "合计"){
								return '';
							}
							return "<a href=javascript:openUpdate('"+rowdata.group_id + "|" + rowdata.hos_id
							+ "|" + rowdata.copy_code + "|"
							+ rowdata.record_no  +"')>"+rowdata.record_no+"</a>";
						}, frozen: true
					}, {
						display : '摘要',
						name : 'note',
						align : 'left',
						width : 160,
						frozen: true,
						render: function (rowdata, rowindex,
								value) {
								var note = "";
								if (rowdata.note == null) {
									note = "追溯盘亏申请";
								} else {
									note = rowdata.note;
								}


								if (rowdata.is_import == 1) {
									return "<a href=javascript:openView('"
										+ rowdata.group_id
										+ "|"
										+ rowdata.hos_id
										+ "|"
										+ rowdata.copy_code
										+ "|"
										+ rowdata.record_no
										+ "|"
										+ rowdata.state
										+ "')>"
										+ note
										+ "</a>";
								} else {
									return rowdata.note;
								}
							}

					},{
						display : '供应商',
						name : 'ven_id',
						textField:'sup_name',
						align : 'left',
						width : 200
					}, {
						display : '制单人',
						name : 'create_emp',
						textField:'create_name',
						align : 'left',
						width : 100
					}, {
						display : '制单日期',
						name : 'create_date',
						align : 'left',
						width : 120
					}, {display : '审核人',
						name : 'audit_emp',
						textField:'audit_name',
						align : 'left',
						width : 100
					}, {
						display : '确认日期',
						name : 'record_date',
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
					url : 'queryAssRemouldRHouse.do',
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
								+ rowdata.record_no);
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
								+ this.copy_code + "@" + this.record_no);
					});
			$.ligerDialog.confirm('变动确认?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("updateConfirmChangeHouse.do", {
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
			title: '资产改造记录',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/ass/assremould/assremouldrhouse/assRemouldRHouseAddPage.do?isCheck=false&',
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
	}
	//查看盘亏申请
	function openView(obj){
		var vo = obj.split("|");
		var parm = "group_id=" + vo[0] + "&" + "hos_id=" + vo[1] + "&"
				+ " copy_code=" + vo[2] + "&" + "record_no=" + vo[3];

		parent.$.ligerDialog.open({
			title: '改造申请查看',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/ass/assremould/assremouldrhouse/assViewSpecialPage.do?isCheck=false&'+parm,
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
								+ this.copy_code + "@" + this.record_no  );
					});
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteAssRemouldARHouse.do?isCheck=false", {
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
				+ " copy_code=" + vo[2] + "&" + "record_no=" + vo[3];

		parent.$.ligerDialog.open({
			title: '原值变动修改',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/ass/assremould/assremouldrhouse/assRemouldRHouseUpdatePage.do?isCheck=false&'+parm,
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
			cancelable:true
		}); 
		autodate("#create_date_begin","YYYY-mm-dd","month_first");

		autodate("#create_date_end","YYYY-mm-dd","month_last");
		
	}
	//键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);

		hotkeys('A', add_open);
		hotkeys('D', remove);


		hotkeys('P', printDate);

	}
         function printDate(){
	    	
	    	if(grid.getData().length==0){
	    		
				$.ligerDialog.error("请先查询数据！");
				
				return;
			}
	    	
	    	var selPara={};
	    	
	    	$.each(grid.options.parms,function(i,obj){
	    		
	    		selPara[obj.name]=obj.value;
	    		
	    	});
	   		
			var dates = getCurrentDate();
	    	
	    	var cur_date = dates.split(";")[2];
	    	//跨所有列:计算列数
	    	var colspan_num = grid.getColumns(1).length-1;
	   		
	    	var printPara={
	       			title:'资产入库',
	       			head:[
	    				{"cell":0,"value":"单位: ${hos_name}","colspan":colspan_num,"br":true}
	       			],
	       			foot:[
	    				{"cell":0,"value":"主管:","colspan":2,"br":false} ,
						{"cell":2,"value":"复核人:","colspan":colspan_num-2,"br":true},
						{"cell":0,"value":"制单人： ${user_name}","colspan":2,"br":false},
						{"cell":2,"value":"打印日期: " + cur_date,"colspan":colspan_num-2,"br":true}
	       			],
	       			columns:grid.getColumns(1),
	       			headCount:2,//列头行数
	       			autoFile:true,
	       			type:3
	       	};
	   		ajaxJsonObjectByUrl("queryAssRemouldRHouse.do?isCheck=false", selPara, function (responseData) {
	   			printGridView(responseData,printPara);
			});

	   		
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
            	<input name="state" type="text" id="state"/>
			</td>
		</tr>

	</table>
	<div id="topmenu" style="background:#FFFFFF; color:#FFFFFF; border:1px solid #A4D3EE" ></div>
	<div id="maingrid"></div>
</body>
</html>
