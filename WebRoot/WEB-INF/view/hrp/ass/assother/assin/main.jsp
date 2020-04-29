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
	var show_detail ;
	$(function() {
		loadDict();//加载下拉框
		//加载数据-
		loadHead(null);
		loadHotkeys();
		query();
		//showDetail();
		//show_detail = $("#show_detail").is(":checked") ? 1 : 0;
		$("#in_date1").ligerTextBox({
			width : 90
		});
		$("#in_date2").ligerTextBox({
			width : 90
		});
		$("#invoice_no").ligerTextBox({
			 width:160
		});
		$("#ass_name").ligerTextBox({
			 width:225
		});
		$("#ass_in_no").ligerTextBox({
			 width:160
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
		$("#store_id").ligerTextBox({
			width : 160
		});
		$("#proj_id").ligerTextBox({
			width : 160
		});
		$("#ven_id").ligerTextBox({
			width : 160
		});
		var menu1 = { width: 120, items:
	           [
		           //{ text: '导入合同单', click: itemclick,id:'ImportContract',icon:'import' },
		          // { text: '导入招标单', click: itemclick,id:'importBid',icon:'import' }
	           ]
	       };
	 
	       var menu2 = { width: 120, items:
	           [
	             //{ text: '生成退货单', click: itemclick,id:'initBack',icon:'initial' },
	            // { text: '生成发票单', click: itemclick,id:'initBill',icon:'initial' },
	            // { text: '生成科室领用单', click: itemclick,id:'initTranster',icon:'initial' }
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
											},{
												text : '冲账',
												id : 'offset',
												click : offset,
												icon : 'offset'
											},{ 
												text:'生成发票单',
												id:'invoice',
												click: generate_bill,
												icon : 'add'
											},{
												text : '入库确认（<u>B</u>）',
												id : 'card',
												click : initCard,
												icon : 'right'
										     },
		                                 /*     { text: '导入', menu: menu1 },
		                                     { text: '生成', menu: menu2 }, */
		                                     {
		                                    		text : '打印模板设置（<u>M</u>）',
		                                    		id : 'printSet',
		                                    		click : printSet,
		                                    		icon : 'settings'
		                                    },{
		                                    		text : '批量打印（<u>P</u>）',
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
			name : 'store_id',
			value : liger.get("store_id").getValue().split("@")[0]
		});
		grid.options.parms.push({
			name : 'ven_id',
			value : liger.get("ven_id").getValue().split("@")[0]
		});
		grid.options.parms.push({
			name : 'store_no',
			value : liger.get("store_id").getValue().split("@")[1]
		});
		grid.options.parms.push({
			name : 'ven_no',
			value : liger.get("ven_id").getValue().split("@")[1]
		});
		grid.options.parms.push({
			name : 'proj_id',
			value : liger.get("proj_id").getValue().split("@")[0]
		});
		grid.options.parms.push({
			name:'invoice_no',
			value:liger.get("invoice_no").getValue()
		}); 
		grid.options.parms.push({
			name:'ass_name',
			value:liger.get("ass_name").getValue()
		});
		grid.options.parms.push({
			name:'ass_in_no',
			value:liger.get("ass_in_no").getValue()
		});
		grid.options.parms.push({
			name : 'proj_no',
			value : liger.get("proj_id").getValue().split("@")[1]
		});
		grid.options.parms.push({
			name : 'purc_emp',
			value : liger.get("purc_emp").getValue()
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
			name : 'is_print',
			value : liger.get("is_print").getValue()
		});
		grid.options.parms.push({
			name : 'in_date_beg',
			value : $("#in_date1").val()
		});
		grid.options.parms.push({
			name : 'in_date_end',
			value : $("#in_date2").val()
		});
		grid.options.parms.push({
			name : 'accepter',
			value : $("#accepter").val()
		});
		//加载查询条件
		grid.loadData(grid.where);
	}
	

	function loadHead() {
		if(show_detail == "1"){
			grid = $("#maingrid").ligerGrid(
				{
					columns : [  {
						display : '入库单号',
						name : 'ass_in_no',
						align : 'left',
						width : 120,
						render : function(rowdata, rowindex,
								value) {
							if(rowdata.note == "合计"){
								return '';
							}
							return "<a href=javascript:openUpdate('"+rowdata.group_id + "|" + rowdata.hos_id
							+ "|" + rowdata.copy_code + "|"
							+ rowdata.ass_in_no  +"')>"+rowdata.ass_in_no+"</a>";
						}, frozen: true
					},{ display: '发票号',
						name: 'invoice_no', 
						align: 'left',
						width : 150,
						frozen: true
			 		},{ display: '发票日期',
						name: 'invoice_date', 
						align: 'left',
						width : 150,
						frozen: true
			 		}, {
						display : '摘要',
						name : 'note',
						align : 'left',
						width : 150,
						frozen: true
					} ,{
						display : '资产编码',
						name : 'ass_code',
						align : 'left',
						width : 80	
					}, {
						display : '资产名称',
						name : 'ass_name',
						align : 'left',
						width : 190
					},{
						display : '规格',
						name : 'ass_spec',
						align : 'left',
						width : 80
					},{
						display : '型号',
						name : 'ass_model',
						align : 'left',
						width : 80
					},{
						display : '品牌',
						name : 'ass_brand',
						align : 'left',
						width : 80
					},{
						display : '生产厂家',
						name : 'fac_id',
						textField : 'fac_name',
						align : 'left',
						width : '190'
					},{
						display : '计量单位',
						name : 'unit_code',
						textField:'unit_name',
						align : 'left',
						width : '100'
					},{
						display : '入库数量',
						name : 'in_amount',
						align : 'left',
						width : '100'
					},{
						display : '单价',
						name : 'price',
						align : 'left',
						width : '100'
					},{
						display : '领用科室',
						name : 'dept_name',
						align : 'left',
						width : 110
					},{
						display : '仓库',
						name : 'store_name',
						align : 'left',
						width : 140, frozen: true
					}, {
						display : '供应商',
						name : 'ven_name',
						align : 'left',
						width : 260
					}, {
						display : '项目',
						name : 'proj_name',
						align : 'left',
						width : 260
					}, {
						display : '入库金额',
						name : 'in_money',
						align : 'right',
						width : 80,
						render: function(item)
			            {
			                    return formatNumber(item.in_money,'${ass_05005}',1);
			            }
					}, {
						display : '采购员',
						name : 'purc_emp_name',
						align : 'left',
						width : 110
					}, {
						display : '制单人',
						name : 'create_emp_name',
						align : 'left',
						width : 100
					}, {
						display : '制单日期',
						name : 'create_date',
						align : 'left',
						width : 100
					}, {
						display : '确认人',
						name : 'confirm_emp_name',
						align : 'left',
						width : 100
					}, {
						display : '入库确认日期',
						name : 'in_date',
						align : 'left',
						width : 100
					}, {
						display : '状态',
						name : 'state_name',
						align : 'left',
						width : 100
					},{
						display : '用途',
						name : 'ass_purpose_name',
						align : 'left',
						width : 100
					}],
					dataAction : 'server',
					dataType : 'server',
					usePager : true,
					url : 'queryAssInMainOther.do?isCheck=false&show_detail=1',
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
								+ rowdata.ass_in_no);
					}
				});
		}else{
			grid = $("#maingrid").ligerGrid(
					{
						columns : [  {
							display : '入库单号',
							name : 'ass_in_no',
							align : 'left',
							width : 120,
							render : function(rowdata, rowindex,
									value) {
								if(rowdata.note == "合计"){
									return '';
								}
								return "<a href=javascript:openUpdate('"+rowdata.group_id + "|" + rowdata.hos_id
								+ "|" + rowdata.copy_code + "|"
								+ rowdata.ass_in_no  +"')>"+rowdata.ass_in_no+"</a>";
							}, frozen: true
						},{ display: '发票号',
							name: 'invoice_no', 
							align: 'left',
							width : 150,
							frozen: true
				 		}, {
							display : '摘要',
							name : 'note',
							align : 'left',
							width : 150,
							frozen: true
						} , {
							display : '仓库',
							name : 'store_name',
							align : 'left',
							width : 140, frozen: true
						}, {
							display : '供应商',
							name : 'ven_name',
							align : 'left',
							width : 260
						}, {
							display : '项目',
							name : 'proj_name',
							align : 'left',
							width : 260
						}, {
							display : '入库金额',
							name : 'in_money',
							align : 'right',
							width : 80,
							render: function(item)
				            {
				                    return formatNumber(item.in_money,'${ass_05005}',1);
				            }
						}, {
							display : '采购员',
							name : 'purc_emp_name',
							align : 'left',
							width : 110
						}, {
							display : '制单人',
							name : 'create_emp_name',
							align : 'left',
							width : 100
						}, {
							display : '制单日期',
							name : 'create_date',
							align : 'left',
							width : 100
						}, {
							display : '确认人',
							name : 'confirm_emp_name',
							align : 'left',
							width : 100
						}, {
							display : '入库确认日期',
							name : 'in_date',
							align : 'left',
							width : 100
						}, {
							display : '状态',
							name : 'state_name',
							align : 'left',
							width : 100
						},{
							display : '用途',
							name : 'ass_purpose_name',
							align : 'left',
							width : 100
						}],
						dataAction : 'server',
						dataType : 'server',
						usePager : true,
						url : 'queryAssInMainOther.do?isCheck=false&show_detail=0',
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
									+ rowdata.ass_in_no);
						}
					});
		}
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
	
function offset(){
		
		var data = gridManager.getCheckedRows();
    	if (data.length == 0){
			$.ligerDialog.error('请选择要冲销的单据！');
			return ;
		} else {
			if(data.length > 1){
				$.ligerDialog.error('只能单张冲账,请重新选择！');
				return ;
			} else {
			
			var nos = "";
			var ParamVo = [];
			$(data).each(	
				function() {
					if(this.state !=2){
						nos = nos + this.ass_in_no + ",";
					}
					
					ParamVo.push(
							this.group_id   +"@"+ 
							this.hos_id   +"@"+ 
							this.copy_code   +"@"+ 
							this.ass_in_no
						) 
				});

			if (nos != "") {
				$.ligerDialog.error("冲账失败！" + nos + "单据不是入库确认状态");
				flag = false;
				return;
			}
			
			$.ligerDialog.confirm('确定要冲销吗?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("offsetInOther.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.state=="true"){
							query();
						}
					});
				}
			});
			
			
			
		}
		
	}
	
	}
	
	//入库确认
	function initCard(){
		var ParamVo = [];
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			$(data).each(
					function() {
						ParamVo.push(this.group_id + "@" + this.hos_id + "@"
								+ this.copy_code + "@" + this.ass_in_no);
					});
			$.ligerDialog.confirm('确认入库?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("updateConfirmInMainOther.do", {
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
			title: '资产入库添加',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/ass/assother/assin/assInMainOtherAddPage.do?isCheck=false&',
			modal: true, showToggle: false, showMax: true, showMin: true, isResize: true,
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
								+ this.copy_code + "@" + this.ass_in_no  );
					});
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteAssInMainOther.do", {
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
		if("null"==vo[3] || "undefined"==vo[3]){
			return false;
			
		}
		var parm = "group_id=" + vo[0] + "&" + "hos_id=" + vo[1] + "&"
				+ " copy_code=" + vo[2] + "&" + "ass_in_no=" + vo[3];

		parent.$.ligerDialog.open({
			title: '资产入库修改',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/ass/assother/assin/assInMainOtherUpdatePage.do?isCheck=false&'+parm,
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
	}
	function loadDict() {
		var param = {query_key:''};
		
		autocomplete("#store_id", "../../queryHosStoreDict.do?naturs_code=04&isCheck=false","id", "text",true,true,param,true,null,"300");
    	
		$("#invoice_no").ligerTextBox({width:160});
		$("#ass_name,#ass_in_no").ligerTextBox({width:160});
		
		autocomplete("#purc_emp",
				"../../queryMatStockEmp.do?isCheck=false&", "id",
				"text", true, true, null, null);
		
		$("#store_id").change(function(){
			
			var store_id = liger.get("store_id").getValue().split("@")[0];

			if (store_id == null || store_id == "") {
				store_id = "";
			}
			autocomplete("#purc_emp",
					"../../queryMatStockEmp.do?isCheck=false&store_id="+store_id, "id",
					"text", true, true, null, null);
		});
    	
		autocomplete("#ven_id", "../../queryHosSupDictNo.do?isCheck=false","id", "text",true,true,param,true,null,"400");
		
		autocomplete("#proj_id", "../../queryAssProjDict.do?isCheck=false","id", "text",true,true,param,true,null,"400");
		$('#state').ligerComboBox({
			data:[{id:0,text:'新建'},{id:1,text:'审核'},{id:2,text:'确认'}],
			valueField: 'id',
            textField: 'text',
			cancelable:true
		});
		
		$('#is_print').ligerComboBox({
			data:[{id:0,text:'否'},{id:1,text:'是'}],
			valueField: 'id',
            textField: 'text',
			cancelable:true,
			width:160
		});
		
		autodate("#create_date_begin","YYYY-mm-dd","month_first");

		autodate("#create_date_end","YYYY-mm-dd","month_last");
		
	}
	//键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);

		hotkeys('A', add_open);
		hotkeys('D', remove);

		hotkeys('M', printSet);
		hotkeys('P', printDate);

	}
	//打印模板设置 最新版
    function printSet(){
	  
    	var useId=0;//统一打印
		if('${ass_05034}'==1){
			//按用户打印
			useId='${user_id }';
		}else if('${ass_05034}'==2){
			//按仓库打印
			if(liger.get("store_id").getValue()==""){
				$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
				return;
			}
			useId=liger.get("store_id").getValue().split("@")[0];
		}
    	
		officeFormTemplate({template_code:"05034",use_id : useId})
    }
  
	//打印 最新版
    function printDate(){
    	
    	 var useId=0;//统一打印
 		if('${ass_05034}'==1){
 			//按用户打印
 			useId='${user_id }';
 		}else if('${ass_05034}'==2){
 			//按仓库打印
 			if(liger.get("store_id").getValue()==""){
 				$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
 				return;
 			}
 			useId=liger.get("store_id").getValue().split("@")[0];
 		}

 		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
			
			var ass_in_no ="" ;
			
			var ass_in_nos = "";
			
			$(data).each(function (){	
				
				ass_in_no  += "'"+this.ass_in_no+"',"
				
				ass_in_nos += ""+this.ass_in_no + ","
					
			});
			
			 var para={
					 
					template_code:'05034',
					class_name:"com.chd.hrp.ass.serviceImpl.in.AssInMainOtherServiceImpl", 
					method_name:"queryAssInOtherByPrintTemlatePrint",
					isSetPrint:false,//是否套打，默认非套打
					isPreview:true,//是否预览，默认直接打印
	    			paraId :ass_in_no.substring(0,ass_in_no.length-1) ,
	    			isPrintCount:false,//更新打印次数
	    			use_id:useId,
	    			p_num:1
	    			//isSetPrint:flag
	    	}; 
			//ajaxJsonObjectByUrl("queryAssInOtherState.do?isCheck=false",{paraId:ass_in_no.substring(0,ass_in_no.length-1),state:2},function(responseData){
				//if (responseData.state == "true") {
					officeFormPrint(para);
					$.post("updateIsPrint.do?isCheck=false",{paramVo:ass_in_nos},function(data){
						
					},"json");
				//}
			//});
			
			
		}
    }
	
  //是否显示明细
    function showDetail() {
		show_detail = $("#show_detail").is(":checked") ? 1 : 0;
		$("#batch_no").val();
		if (grid) {
			//由于一个对象多次绑定相同的事件，需要进行解绑在绑定
			grid.unbind(); 
		}
		loadHead();
		//query();
	}
  
function generate_bill(){
    	
    	var gridData = grid.getSelectedRows();
		
		var params = [];
		$.each(gridData,function(index,item){
			params.push(item.ass_in_no);
		}); 
		
		if(isnull(params)){
			$.ligerDialog.success("清选择维护发票的单据");
			return;
		}
		
		ajaxJsonObjectByUrl("assInGenerateBill.do?isCheck=false",
				{
				 'params':params.toString()
				 },
				 function(data){
					
		},"json"); 
		
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
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">仓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;库：</td>
			<td align="left" class="l-table-edit-td"><input name="store_id"
				type="text" id="store_id" 
				 /></td>
			<td align="right"  class="l-table-edit-td" style="padding-left: 20px;">供&nbsp;&nbsp;应&nbsp;&nbsp;商：</td>
			<td align="left"  class="l-table-edit-td"><input name="ven_id"
				type="text" id="ven_id" 
				 /></td>	
				 <td align="right"  class="l-table-edit-td" style="padding-left: 20px;">项&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;目：</td>
			<td align="left"  class="l-table-edit-td"><input name="proj_id"
				type="text" id="proj_id" 
				 /></td>		 
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">确认日期：</td>
			<td align="left" ><input name="in_date1"
				type="text" id="in_date1" 
				 class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
				<td align="left">至：</td>
			<td align="left"><input name="in_date2" type="text"
				id="in_date2" 
				 class="Wdate"
				onFocus="WdatePicker({minDate:'#F{$dp.$D(\'in_date1\');}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">采&nbsp;&nbsp;购&nbsp;&nbsp;员：</td>
			<td align="left" class="l-table-edit-td"><input name="purc_emp"
				type="text" id="purc_emp" 
				 /></td>
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
			<td align="right"  class="l-table-edit-td" style="padding-left: 20px;">发&nbsp;&nbsp;&nbsp;票&nbsp;&nbsp;&nbsp;&nbsp;号：</td>
			 <td align="left"  class="l-table-edit-td" colspan = "3"><input name="invoice_no" type="text" id="invoice_no" /></td>	
		</tr>
		<tr>
			<td align="right"  class="l-table-edit-td" style="padding-left: 20px;">资产名称：</td>
			 <td align="left"   colspan = "3" ><input name="ass_name" type="text" id="ass_name" /></td>	
			 <td align="right"  class="l-table-edit-td" style="padding-left: 20px;">入库单号：</td>
			 <td align="left"  class="l-table-edit-td" ><input name="ass_in_no" type="text" id="ass_in_no" /></td>
			 <td align="right"  class="l-table-edit-td" style="padding-left: 20px;">是否打印：</td>
			 <td align="left"  class="l-table-edit-td" ><input name="is_print" type="text" id="is_print" /></td>	
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" width="10%">
			</td>
			<td align="left" class="l-table-edit-td" >
            	<input name="show_detail" type="checkbox" id="show_detail" onclick="showDetail();"/>&nbsp;&nbsp;显示明细
             </td>
		</tr>
	</table>
	<div id="topmenu" style="background:#FFFFFF; color:#FFFFFF; border:1px solid #A4D3EE" ></div>
	<div id="maingrid"></div>
</body>
</html>
