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
		$("#out_hos_id").ligerComboBox({
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
												text : '入库确认（<u>B</u>）',
												id : 'card',
												click : initCard,
												icon : 'right'
										     },
										     { text: '模板设置', id:'printSetDetail', click: printSetDetail, icon:'settings' },
		 			        				 { text: '批量打印（<u>P</u>）', id:'printDetail', click: printDetail, icon:'print' }

		                                     
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
			name : 'out_hos_id',
			value : liger.get("out_hos_id").getValue()
		});
		grid.options.parms.push({
			name : 'out_group_id',
			value : liger.get("out_group_id").getValue()
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
						display : '调剂单号',
						name : 'allot_in_no',
						align : 'left',
						width : 120,
						render : function(rowdata, rowindex,
								value) {
							if(rowdata.note == "合计"){
								return '';
							}
							return "<a href=javascript:openUpdate('"+rowdata.group_id + "|" + rowdata.hos_id
							+ "|" + rowdata.copy_code + "|"
							+ rowdata.allot_in_no  +"|"+ rowdata.is_import
							+"')>"+rowdata.allot_in_no+"</a>";
						}, frozen: true
					}, {
						display : '摘要',
						name : 'note',
						align : 'left',
						width : 150,
						frozen: true,
						render : function(rowdata, rowindex,
								value) {
							var note = "";
							if(rowdata.note == null){
								note = "引入调剂出库";
							}else{
								note = rowdata.note;
							}
							if (rowdata.is_import  == 1) {
								return "<a href=javascript:openViewAllotOut('"
								+ rowdata.group_id
								+ "|"
								+ rowdata.hos_id
								+ "|"
								+ rowdata.copy_code
								+ "|"
								+ rowdata.allot_in_no
								+ "|"
								+ rowdata.out_group_id
								+ "|"
								+ rowdata.out_hos_id
								+"')>"
								+ note
								+ "</a>";
							} else{
								return rowdata.note;
							}
						}
					}, {
						display : '调出集团',
						name : 'out_group_name',
						align : 'left',
						width : 140
					}, {
						display : '调出单位',
						name : 'out_hos_name',
						align : 'left',
						width : 140
					}, {
						display : '资产编码',
						name : 'ass_code',
						align : 'left',
						width : 140
					} ,{
						display : '资产名称',
						name : 'ass_name',
						align : 'left',
						width : 140
					},{
						display : '原始卡片号',
						name : 'ass_ori_card_no',
						align : 'left',
						width : 140
					},{
						display : '资产原值',
						name : 'price',
						align : 'right',
						width : 80,
						editor : {
							type : 'numberbox'
						},
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
					url : 'queryAssAllotInHouse.do?isCheck=false&show_detail=1',
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
								+ rowdata.allot_in_no + "|" + rowdata.is_import);
					}
				});
		}else{
			grid = $("#maingrid").ligerGrid(
					{
						columns : [  {
							display : '调剂单号',
							name : 'allot_in_no',
							align : 'left',
							width : 120,
							render : function(rowdata, rowindex,
									value) {
								if(rowdata.note == "合计"){
									return '';
								}
								return "<a href=javascript:openUpdate('"+rowdata.group_id + "|" + rowdata.hos_id
								+ "|" + rowdata.copy_code + "|"
								+ rowdata.allot_in_no  +"|"+ rowdata.is_import
								+"')>"+rowdata.allot_in_no+"</a>";
							}, frozen: true
						}, {
							display : '摘要',
							name : 'note',
							align : 'left',
							width : 150,
							frozen: true,
							render : function(rowdata, rowindex,
									value) {
								var note = "";
								if(rowdata.note == null){
									note = "引入调剂出库";
								}else{
									note = rowdata.note;
								}
								if (rowdata.is_import  == 1) {
									return "<a href=javascript:openViewAllotOut('"
									+ rowdata.group_id
									+ "|"
									+ rowdata.hos_id
									+ "|"
									+ rowdata.copy_code
									+ "|"
									+ rowdata.allot_in_no
									+ "|"
									+ rowdata.out_group_id
									+ "|"
									+ rowdata.out_hos_id
									+"')>"
									+ note
									+ "</a>";
								} else{
									return rowdata.note;
								}
							}
						}, {
							display : '调出集团',
							name : 'out_group_name',
							align : 'left',
							width : 140
						}, {
							display : '调出单位',
							name : 'out_hos_name',
							align : 'left',
							width : 140
						}, {
							display : '资产原值',
							name : 'price',
							align : 'right',
							width : 80,
							editor : {
								type : 'numberbox'
							},
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
						url : 'queryAssAllotInHouse.do?isCheck=false&show_detail=0',
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
									+ rowdata.allot_in_no + "|" + rowdata.is_import);
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
	
	function openViewAllotOut(obj){
		var vo = obj.split("|");
		var parm = "group_id=" + vo[0] + "&" + "hos_id=" + vo[1] + "&"
				+ " copy_code=" + vo[2] + "&" + "allot_in_no=" + vo[3] + "&"
				+ "out_group_id=" + vo[4] + "&" + "out_hos_id="+vo[5] ;

		parent.$.ligerDialog.open({
			title: '资产调剂出库查看',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/ass/asshouse/assallotin/assViewAllotOutHousePage.do?isCheck=false&'+parm,
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
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
								+ this.copy_code + "@" + this.allot_in_no);
					});
			$.ligerDialog.confirm('确认入库?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("updateConfirmAllotInMainHouse.do", {
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
			title: '资产调剂入库添加',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/ass/asshouse/assallotin/assAllotInHouseAddPage.do?isCheck=false&',
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
								+ this.copy_code + "@" + this.allot_in_no  );
					});
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteAssAllotInHouse.do", {
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
				+ " copy_code=" + vo[2] + "&" + "allot_in_no=" + vo[3] + "&"
				+ "is_import=" + vo[4];

		parent.$.ligerDialog.open({
			title: '资产调剂入库修改',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/ass/asshouse/assallotin/assAllotInHouseUpdatePage.do?isCheck=false&'+parm,
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
	}
	function loadDict() {
		
    	
		autocomplete("#create_emp", "../../../../hrp/sys/queryUserDict.do?isCheck=false", "id", "text", false, false, false, false);
    	
		autocomplete("#out_group_id", "../../queryGroupDict.do?isCheck=false","id", "text",true,true,null,false,null,"160");
		
		$("#out_group_id").change(function(){
			autocomplete("#out_hos_id", "../../queryHosInfoDict.do?isCheck=false","id", "text",true,true,{group_id:liger.get("out_group_id").getValue()},false,null,"160");
		});
		$('#state').ligerComboBox({
			data:[{id:0,text:'新建'},{id:1,text:'审核'},{id:2,text:'确认'}],
			valueField: 'id',
            textField: 'text',
			cancelable:true
		});
		autodate("#create_date_beg","YYYY-mm-dd","month_first");

		autodate("#create_date_end","YYYY-mm-dd","month_last");
		
	}
	//键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);

		hotkeys('A', add_open);
		hotkeys('D', remove);

		hotkeys('P', printDetail);

	}
	//打印模板设置
	function printSetDetail(){
		  
		var useId=0;//统一打印
		if('${ass_05058}'==1){
			//按用户打印
			useId='${user_id }';
		} 
		officeFormTemplate({template_code:"0505801",use_id:useId});
	}
	
	//打印
	function printDetail(){
		var useId=0;//统一打印
		if('${ass_05058}'==1){
			//按用户打印
			useId='${user_id }';
		}
		//if($("#create_date_b").val())
		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
			var allot_in_no ="" ;
			$(data).each(function (){		
				allot_in_no  += "'"+this.allot_in_no+"',"
			});
			var para={
				class_name:"com.chd.hrp.ass.serviceImpl.allot.in.AssAllotInHouseServiceImpl",
				method_name:"printAssAllotInHouseData",
				allot_in_no: allot_in_no.substring(0,allot_in_no.length-1) ,
				template_code:'0505801',
				isPrintCount:false,//更新打印次数
				isPreview:true,//预览窗口，传绝对路径
				use_id:useId,
			}; 
			//printTemplate("hrp/mat/purchase/make/queryMatMakeByDetailPrintTemlate.do?isCheck=false",para);
			ajaxJsonObjectByUrl("queryAssAllotInHouseStates.do?isCheck=false",{allot_in_no: allot_in_no.substring(0,allot_in_no.length-1),state:2},function(responseData){
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
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">制单人：</td>
			<td align="left" class="l-table-edit-td"><input name="create_emp"
				type="text" id="create_emp" 
				 /></td>	 
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
			<td align="right"  class="l-table-edit-td" style="padding-left: 20px;">调出集团：</td>
			<td align="left"  class="l-table-edit-td"><input name="out_group_id"
				type="text" id="out_group_id" 
				 /></td>	
			<td align="right"  class="l-table-edit-td" style="padding-left: 20px;">调出单位：</td>
			<td align="left"  class="l-table-edit-td"><input name="out_hos_id"
				type="text" id="out_hos_id" 
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
