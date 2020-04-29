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
		//加载数据
		loadHead(null);
		loadHotkeys();
		showDetail();
		show_detail = $("#show_detail").is(":checked") ? 1 : 0;
		$("#audit_date_beg").ligerTextBox({
			width : 90
		});
		$("#audit_date_end").ligerTextBox({
			width : 90
		});
		$("#create_date_beg").ligerTextBox({
			width : 90
		});
		$("#create_date_end").ligerTextBox({
			width : 90
		});
		$("#state").ligerComboBox({
			width : 160
		});
		$("#out_store_id").ligerTextBox({
			width : 160
		});
		$("#in_store_id").ligerComboBox({
			width : 160
		});
		$("#in_hos_id").ligerComboBox({
			width : 160
		});

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
												text : '出库确认（<u>B</u>）',
												id : 'card',
												click : initCard,
												icon : 'right'
										     },{
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
			name : 'in_store_id',
			value : liger.get("in_store_id").getValue().split("@")[0]
		});
		grid.options.parms.push({
			name : 'in_store_no',
			value : liger.get("in_store_id").getValue().split("@")[1]
		});
		grid.options.parms.push({
			name : 'out_store_id',
			value : liger.get("out_store_id").getValue().split("@")[0]
		});
		grid.options.parms.push({
			name : 'out_store_no',
			value : liger.get("out_store_id").getValue().split("@")[1]
		});
		grid.options.parms.push({
			name : 'bus_type_code',
			value : liger.get("bus_type_code").getValue()
		});
		grid.options.parms.push({
			name : 'in_hos_id',
			value : liger.get("in_hos_id").getValue()
		});
		grid.options.parms.push({
			name : 'in_group_id',
			value : liger.get("in_group_id").getValue()
		});
		grid.options.parms.push({
			name : 'create_date_beg',
			value : $("#create_date_beg").val()
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
			name : 'audit_date_beg',
			value : $("#audit_date_beg").val()
		});
		grid.options.parms.push({
			name : 'audit_date_end',
			value : $("#audit_date_end").val()
		});
		//加载查询条件
		grid.loadData(grid.where);
	}
	

	function loadHead() {
		if(show_detail == "1"){
			grid = $("#maingrid").ligerGrid(
				{
					columns : [  {
						display : '调拨单号',
						name : 'sell_out_no',
						align : 'left',
						width : 120,
						render : function(rowdata, rowindex,
								value) {
							if(rowdata.note == "合计"){
								return '';
							}
							return "<a href=javascript:openUpdate('"+rowdata.group_id + "|" + rowdata.hos_id
							+ "|" + rowdata.copy_code + "|"
							+ rowdata.sell_out_no  +"')>"+rowdata.sell_out_no+"</a>";
						}, frozen: true
					}, {
						display : '摘要',
						name : 'note',
						align : 'left',
						width : 150,
						frozen: true
					} , {
						display : '调出仓库',
						name : 'out_store_name',
						align : 'left',
						width : 200, frozen: true
					}, {
						display : '调入集团',
						name : 'in_group_name',
						align : 'left',
						width : 140
					}, {
						display : '调入单位',
						name : 'in_hos_name',
						align : 'left',
						width : 140
					} , {
						display : '调入仓库',
						name : 'in_store_name',
						align : 'left',
						width : 200
					} , {
						display : '业务类型',
						name : 'bus_type_name',
						align : 'left',
						width : 200
					},{
						display : '卡片编码',
						name : 'ass_card_no',
						align : 'left',
						width : '150'
					}, {
						display : '资产编码',
						name : 'ass_code',
						align : 'left',
						width : '150'
					}, {
						display : '资产名称',
						name : 'ass_name',
						align : 'left',
						width : '150'
					}, {
						display : '资产原值',
						name : 'price',
						align : 'right',
						width : 80,
						render: function(item)
			            {
			                    return formatNumber(item.price,'${ass_05006}',1);
			            }
					}, {
						display : '累计折旧',
						name : 'add_depre',
						align : 'right',
						width : 80,
						render: function(item)
			            {
			                    return formatNumber(item.add_depre,'${ass_05005}',1);
			            }
					}, {
						display : '资产净值',
						name : 'cur_money',
						align : 'right',
						width : 80,
						render: function(item)
			            {
			                    return formatNumber(item.cur_money,'${ass_05006}',1);
			            }
					}, {
						display : '预留残值',
						name : 'fore_money',
						align : 'right',
						width : 80,
						render: function(item)
			            {
			                    return formatNumber(item.fore_money,'${ass_05006}',1);
			            }
					}, {
						display : '调拨价格',
						name : 'sell_price',
						align : 'right',
						width : 80,
						render: function(item)
			            {
			                    return formatNumber(item.sell_price,'${ass_05006}',1);
			            }
					}, {
						display : '应缴税费',
						name : 'dispose_tax',
						align : 'right',
						width : 80,
						render: function(item)
			            {
			                    return formatNumber(item.dispose_tax,'${ass_05005}',1);
			            }
					}, {
						display : '计税收入',
						name : 'dispose_income',
						align : 'right',
						width : 80,
						render: function(item)
			            {
			                    return formatNumber(item.dispose_income,'${ass_05005}',1);
			            }
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
						name : 'audit_emp_name',
						align : 'left',
						width : 100
					}, {
						display : '入库确认日期',
						name : 'audit_date',
						align : 'left',
						width : 100
					}, {
						display : '状态',
						name : 'state_name',
						align : 'left',
						width : 100
					}],
					dataAction : 'server',
					dataType : 'server',
					usePager : true,
					url : 'queryAssSellOutSpecial.do?isCheck=false&show_detail=1',
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
								+ rowdata.sell_out_no);
					}
				});
		}else{
			grid = $("#maingrid").ligerGrid(
					{
						columns : [  {
							display : '调拨单号',
							name : 'sell_out_no',
							align : 'left',
							width : 120,
							render : function(rowdata, rowindex,
									value) {
								if(rowdata.note == "合计"){
									return '';
								}
								return "<a href=javascript:openUpdate('"+rowdata.group_id + "|" + rowdata.hos_id
								+ "|" + rowdata.copy_code + "|"
								+ rowdata.sell_out_no  +"')>"+rowdata.sell_out_no+"</a>";
							}, frozen: true
						}, {
							display : '摘要',
							name : 'note',
							align : 'left',
							width : 150,
							frozen: true
						} , {
							display : '调出仓库',
							name : 'out_store_name',
							align : 'left',
							width : 200, frozen: true
						}, {
							display : '调入集团',
							name : 'in_group_name',
							align : 'left',
							width : 140
						}, {
							display : '调入单位',
							name : 'in_hos_name',
							align : 'left',
							width : 140
						} , {
							display : '调入仓库',
							name : 'in_store_name',
							align : 'left',
							width : 200
						} , {
							display : '业务类型',
							name : 'bus_type_name',
							align : 'left',
							width : 200
						}, {
							display : '资产原值',
							name : 'price',
							align : 'right',
							width : 80,
							render: function(item)
				            {
				                    return formatNumber(item.price,'${ass_05006}',1);
				            }
						}, {
							display : '累计折旧',
							name : 'add_depre',
							align : 'right',
							width : 80,
							render: function(item)
				            {
				                    return formatNumber(item.add_depre,'${ass_05005}',1);
				            }
						}, {
							display : '资产净值',
							name : 'cur_money',
							align : 'right',
							width : 80,
							render: function(item)
				            {
				                    return formatNumber(item.cur_money,'${ass_05006}',1);
				            }
						}, {
							display : '预留残值',
							name : 'fore_money',
							align : 'right',
							width : 80,
							render: function(item)
				            {
				                    return formatNumber(item.fore_money,'${ass_05006}',1);
				            }
						}, {
							display : '调拨价格',
							name : 'sell_price',
							align : 'right',
							width : 80,
							render: function(item)
				            {
				                    return formatNumber(item.sell_price,'${ass_05006}',1);
				            }
						}, {
							display : '应缴税费',
							name : 'dispose_tax',
							align : 'right',
							width : 80,
							render: function(item)
				            {
				                    return formatNumber(item.dispose_tax,'${ass_05005}',1);
				            }
						}, {
							display : '计税收入',
							name : 'dispose_income',
							align : 'right',
							width : 80,
							render: function(item)
				            {
				                    return formatNumber(item.dispose_income,'${ass_05005}',1);
				            }
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
							name : 'audit_emp_name',
							align : 'left',
							width : 100
						}, {
							display : '入库确认日期',
							name : 'audit_date',
							align : 'left',
							width : 100
						}, {
							display : '状态',
							name : 'state_name',
							align : 'left',
							width : 100
						}],
						dataAction : 'server',
						dataType : 'server',
						usePager : true,
						url : 'queryAssSellOutSpecial.do?isCheck=false&show_detail=0',
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
									+ rowdata.sell_out_no);
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
	
	//出库确认
	function initCard(){
		var ParamVo = [];
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			$(data).each(
					function() {
						ParamVo.push(this.group_id + "@" + this.hos_id + "@"
								+ this.copy_code + "@" + this.sell_out_no);
					});
			$.ligerDialog.confirm('确认出库?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("updateConfirmSellOutSpecial.do", {
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
			title: '资产调拨出库添加',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/ass/assspecial/asssellout/assSellOutSpecialAddPage.do?isCheck=false&',
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
								+ this.copy_code + "@" + this.sell_out_no  );
					});
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteAssSellOutSpecial.do", {
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
				+ " copy_code=" + vo[2] + "&" + "sell_out_no=" + vo[3];

		parent.$.ligerDialog.open({
			title: '资产调拨出库修改',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/ass/assspecial/asssellout/assSellOutSpecialUpdatePage.do?isCheck=false&'+parm,
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
	}
	function loadDict() {
		
		autocomplete("#out_store_id", "../../queryHosStoreDict.do?naturs_code=02&isCheck=false","id", "text",true,true,null,false,null,"300");
    	
		autocomplete("#bus_type_code", "../../queryAssBusType.do?isCheck=false","id", "text",true,true,{is_menu:4},false,null,"160");
    	
		autocomplete("#in_group_id", "../../queryGroupDict.do?isCheck=false","id", "text",true,true,null,false,null,"160");
		
		$("#in_group_id").change(function(){
			autocomplete("#in_hos_id", "../../queryHosInfoDict.do?isCheck=false","id", "text",true,true,{group_id:liger.get("in_group_id").getValue()},false,null,"160");
			$("#in_hos_id").change(function(){
				autocomplete("#in_store_id", "../../queryHosStoreDict.do?naturs_code=02&isCheck=false","id", "text",true,true,{group_id:liger.get("in_group_id").getValue(),hos_id:liger.get("in_hos_id").getValue()},false,null,"160");
			});
		});
		$('#state').ligerComboBox({
			data:[{id:0,text:'新建'},{id:1,text:'审核'},{id:2,text:'确认'}],
			valueField: 'id',
            textField: 'text',
			cancelable:true,
			width:160
		});
		autodate("#create_date_beg","YYYY-mm-dd","month_first");

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
		if('${ass_05019}'==1){
			//按用户打印
			useId='${user_id }';
		}else if('${ass_05019}'==2){
			//按仓库打印
			if(liger.get("out_store_id").getValue()==""){
				$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
				return;
			}
			useId=liger.get("out_store_id").getValue().split("@")[0];
		}
    	
		officeFormTemplate({template_code:"0501902",use_id : useId})
    }
  
  //打印 最新版
   function printDate(){
   	
		var useId=0;//统一打印
		if('${ass_05019}'==1){
			//按用户打印
			useId='${user_id }';
		}else if('${ass_05019}'==2){
			//按仓库打印
			if(liger.get("out_store_id").getValue()==""){
				$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
				return;
			}
			useId=liger.get("out_store_id").getValue().split("@")[0];
		}
	
		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
			
			var sell_out_no ="" ;
			
			$(data).each(function (){	
				
				sell_out_no  += "'"+this.sell_out_no+"',"
					
			});
			
			 var para={
					 
					template_code:'0501902',
					class_name:"com.chd.hrp.ass.serviceImpl.sell.out.AssSellOutSpecialServiceImpl", 
					method_name:"queryAssSellOutSpecialByPrintTemlatePrint",
					isSetPrint:false,//是否套打，默认非套打
					isPreview:true,//是否预览，默认直接打印
	    			paraId :sell_out_no.substring(0,sell_out_no.length-1) ,
	    			isPrintCount:false,//更新打印次数
	    			use_id:useId,
	    			p_num:1
	    			//isSetPrint:flag
	    	}; 
			ajaxJsonObjectByUrl("queryAssSellOutSpecialState.do?isCheck=false",{paraId:sell_out_no.substring(0,sell_out_no.length-1),state:2},function(responseData){
				if (responseData.state == "true") {
					officeFormPrint(para);
				}
			});
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
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" border="0" id="table1" width="100%">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">制单日期：</td>
			<td align="left" width="5%"><input
				name="create_date_beg" type="text" id="create_date_beg"
				  class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="left" width="2%">至：</td>
			<td align="left"><input name="create_date_end" type="text"
				id="create_date_end" 
				 class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">调出仓库：</td>
			<td align="left" class="l-table-edit-td"><input name="out_store_id"
				type="text" id="out_store_id" 
				 /></td>
			<td align="right"  class="l-table-edit-td" style="padding-left: 20px;">业务类型：</td>
			<td align="left"  class="l-table-edit-td"><input name="bus_type_code"
				type="text" id="bus_type_code" 
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
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">确认日期：</td>
			<td align="left" ><input name="audit_date_beg"
				type="text" id="audit_date_beg" 
				 class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
				<td align="left">至：</td>
			<td align="left"><input name="audit_date_end" type="text"
				id="audit_date_end" 
				 class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="right"  class="l-table-edit-td" style="padding-left: 20px;">调入集团：</td>
			<td align="left"  class="l-table-edit-td"><input name="in_group_id"
				type="text" id="in_group_id" 
				 /></td>	
			<td align="right"  class="l-table-edit-td" style="padding-left: 20px;">调入单位：</td>
			<td align="left"  class="l-table-edit-td"><input name="in_hos_id"
				type="text" id="in_hos_id" 
				 /></td>
			<td align="right"  class="l-table-edit-td" style="padding-left: 20px;">调入仓库：</td>
			<td align="left"  class="l-table-edit-td"><input name="in_store_id"
				type="text" id="in_store_id" 
				 /></td>
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
