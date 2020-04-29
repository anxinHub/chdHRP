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
		loadDict();//加载下拉框-
		//加载数据
		loadHead(null);
		loadHotkeys();
		query();
		showDetail();
		 show_detail = $("#show_detail").is(":checked") ? 1 : 0;
		$("#bill_type").ligerComboBox({
			width : 160
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
			name : 'store_no',
			value : liger.get("store_id").getValue().split("@")[1]
		});
		grid.options.parms.push({
			name:'ass_name',
			value:liger.get("ass_name").getValue()
		});
		grid.options.parms.push({
			name:'out_no',
			value:liger.get("out_no").getValue()
		});
		grid.options.parms.push({
			name : 'dept_id',
			value : liger.get("dept_id").getValue().split("@")[0]
		});
		grid.options.parms.push({
			name : 'dept_no',
			value : liger.get("dept_id").getValue().split("@")[1]
		});
		grid.options.parms.push({
			name : 'bill_type',
			value : liger.get("bill_type").getValue()
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
			name : 'audit_date_beg',
			value : $("#audit_date_beg").val()
		});
		grid.options.parms.push({
			name : 'audit_date_end',
			value : $("#audit_date_end").val()
		});
		grid.options.parms.push({
			name : 'state',
			value : liger.get("state").getValue()
		});
		//加载查询条件
		grid.loadData(grid.where);
	}

	function loadHead() {
		if(show_detail == "1"){
		grid = $("#maingrid").ligerGrid(
				{
					columns : [
							{
								display : '移库单号',
								name : 'out_no',
								align : 'left',
								width : 120,
								render : function(rowdata, rowindex, value) {
									if(rowdata.note == "合计"){
										return '';
									}
									return "<a href=javascript:openUpdate('"
											+ rowdata.group_id + "|"
											+ rowdata.hos_id + "|"
											+ rowdata.copy_code + "|"
											+ rowdata.out_no + "')>"
											+ rowdata.out_no + "</a>";
								}
							}, {
								display : '摘要',
								name : 'note',
								align : 'left',
								width : 100,
								render : function(rowdata, rowindex,
										value) {
									var note = "";
									if(rowdata.note == null){
										note = "引入采购入库";
									}else{
										note = rowdata.note;
									}
									if (rowdata.is_import  == 1) {
										return "<a href=javascript:openViewAssIn('"
										+ rowdata.group_id
										+ "|"
										+ rowdata.hos_id
										+ "|"
										+ rowdata.copy_code
										+ "|"
										+ rowdata.out_no
										+ "')>"
										+ note
										+ "</a>";
									} else{
										return rowdata.note;
									}
								}
							}, {
								display : '单据类型',
								name : 'bill_type_name',
								align : 'left',
								width : 100
							}, {
								display : '科室',
								name : 'dept_name',
								align : 'left',
						        width : 100
							}, {
								display : '仓库',
								name : 'store_name',
								align : 'left',
						        width : 100
							},{
								display : '卡片编码',
								name : 'ass_card_no',
								align : 'left',
						        width : 100
							}, 
							 {
								display : '资产编码',
								name : 'ass_code',
								align : 'left',
						        width : 100
									},
							 {
								display : '资产名称',
								name : 'ass_name',
								align : 'left',
						        width : 100
									},
							 {
								display : '资产规格',
								name : 'ass_spec',
								align : 'left',
						        width : 100
									},
							 {
								display : '资产型号',
								name : 'ass_mondl',
								align : 'left',
						        width : 100
									}, 
							 {
								display : '资产品牌',
								name : 'ass_brand',
								align : 'left',
						        width : 100
									},
							 {
								display : '数量',
								name : 'ass_amount',
								align : 'left',
						        width : 100
									},
							 {
								display : '金额',
								name : 'price',
								align : 'left',
						        width : 100,
								render: function(item)
					            {
				                    return formatNumber(item.price,'${ass_05005}',1);
					            }
							},  {
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
								display : '确认日期',
								name : 'audit_date',
								align : 'left',
						        width : 100
							}, {
								display : '状态',
								name : 'state_name',
								align : 'left',
						        width : 100
							} ],
					dataAction : 'server',
					dataType : 'server',
					usePager : true,
					url : 'queryAssOutSpecial.do?isCheck=false&show_detail=1',
					width : '100%',
					height : '100%',
					checkbox : true,
					rownumbers : true,
					delayLoad : true,
					selectRowButtonOnly : true,//heightDiff: -10,
					toolbar : {
						items : [ {
							text : '查询（<u>E</u>）',
							id : 'search',
							click : query,
							icon : 'search'
						}, {
							line : true
						}, {
							text : '添加（<u>A</u>）',
							id : 'add',
							click : add_open,
							icon : 'add'
						}, {
							line : true
						}, {
							text : '删除（<u>D</u>）',
							id : 'delete',
							click : remove,
							icon : 'delete'
						},{
							line : true
						},{
							text : '冲账',
							id : 'offset',
							click : offset,
							icon : 'offset'
						}, {
							line : true
						}, {
							text : '移库确认（<u>Q</u>）',
							id : 'updateConfirm',
							click : updateConfirm,
							icon : 'ok'
						}, {
							line : true
						},
						{ text: '模板设置', id:'printSet', click: printSet, icon:'print' },
						{ line:true } ,
						{ text: '批量打印', id:'print', click: print, icon:'print' }
					   ]
					},
					onDblClickRow : function(rowdata, rowindex, value) {
						openUpdate(rowdata.group_id + "|" + rowdata.hos_id
								+ "|" + rowdata.copy_code + "|"
								+ rowdata.out_no);
					}
				});
		}else{
			grid = $("#maingrid").ligerGrid(
					{
						columns : [
								{
									display : '移库单号',
									name : 'out_no',
									align : 'left',
									render : function(rowdata, rowindex, value) {
										return "<a href=javascript:openUpdate('"
												+ rowdata.group_id + "|"
												+ rowdata.hos_id + "|"
												+ rowdata.copy_code + "|"
												+ rowdata.out_no + "')>"
												+ rowdata.out_no + "</a>";
									}
								}, {
									display : '摘要',
									name : 'note',
									align : 'left',
									render : function(rowdata, rowindex,
											value) {
										var note = "";
										if(rowdata.note == null){
											note = "引入采购入库";
										}else{
											note = rowdata.note;
										}
										if (rowdata.is_import  == 1) {
											return "<a href=javascript:openViewAssIn('"
											+ rowdata.group_id
											+ "|"
											+ rowdata.hos_id
											+ "|"
											+ rowdata.copy_code
											+ "|"
											+ rowdata.out_no
											+ "')>"
											+ note
											+ "</a>";
										} else{
											return rowdata.note;
										}
									}
								}, {
									display : '单据类型',
									name : 'bill_type_name',
									align : 'left'
								}, {
									display : '科室',
									name : 'dept_name',
									align : 'left'
								}, {
									display : '仓库',
									name : 'store_name',
									align : 'left'
								}, {
									display : '制单人',
									name : 'create_emp_name',
									align : 'left'
								}, {
									display : '制单日期',
									name : 'create_date',
									align : 'left'
								}, {
									display : '确认人',
									name : 'audit_emp_name',
									align : 'left'
								}, {
									display : '确认日期',
									name : 'audit_date',
									align : 'left'
								}, {
									display : '状态',
									name : 'state_name',
									align : 'left'
								} ],
						dataAction : 'server',
						dataType : 'server',
						usePager : true,
						url : 'queryAssOutSpecial.do?isCheck=false&show_detail=0',
						width : '100%',
						height : '100%',
						checkbox : true,
						rownumbers : true,
						delayLoad : true,
						selectRowButtonOnly : true,//heightDiff: -10,
						toolbar : {
							items : [ {
								text : '查询（<u>E</u>）',
								id : 'search',
								click : query,
								icon : 'search'
							}, {
								line : true
							}, {
								text : '添加（<u>A</u>）',
								id : 'add',
								click : add_open,
								icon : 'add'
							}, {
								line : true
							}, {
								text : '删除（<u>D</u>）',
								id : 'delete',
								click : remove,
								icon : 'delete'
							}, {
								line : true
							},{
								text : '冲账',
								id : 'offset',
								click : offset,
								icon : 'offset'
							}, {
								line : true
							}, {
								text : '移库确认（<u>Q</u>）',
								id : 'updateConfirm',
								click : updateConfirm,
								icon : 'ok'
							}, {
								line : true
							},
							{ text: '模板设置', id:'printSet', click: printSet, icon:'print' },
							{ line:true } ,
							{ text: '批量打印', id:'print', click: print, icon:'print' }
						   ]
						},
						onDblClickRow : function(rowdata, rowindex, value) {
							openUpdate(rowdata.group_id + "|" + rowdata.hos_id
									+ "|" + rowdata.copy_code + "|"
									+ rowdata.out_no);
						}
					});
		}
		gridManager = $("#maingrid").ligerGetGridManager();
	}

	function add_open() {

		parent.$.ligerDialog
				.open({
					title : '科室移库添加',
					height : $(window).height(),
					width : $(window).width(),
					url : 'hrp/ass/assspecial/asstran/out/assOutSpecialAddPage.do?isCheck=false&',
					modal : true,
					showToggle : false,
					showMax : true,
					showMin : true,
					isResize : true,
					parentframename : window.name, //用于parent弹出层调用本页面的方法或变量
				});

	}
	function updateConfirm() {
		var ParamVo = [];
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			$(data).each(
					function() {
						ParamVo.push(this.group_id + "@" + this.hos_id + "@"
								+ this.copy_code + "@" + this.out_no);

					});
			$.ligerDialog.confirm('确定移库?', function(yes) {

				if (yes) {
					ajaxJsonObjectByUrl("updateConfirmOutSpecial.do", {
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
			var nos_bill = "";
			var ParamVo = [];
			$(data).each(	
				function() {
					if(this.state !=2){
						nos = nos + this.out_no + ",";
					}
					
					if(this.bill_type != '01'){
						nos_bill = nos_bill + this.out_no + ",";
					}
					
					ParamVo.push(
							this.group_id   +"@"+ 
							this.hos_id   +"@"+ 
							this.copy_code   +"@"+ 
							this.out_no
						) 
				});

			if (nos != "") {
				$.ligerDialog.error("冲账失败！" + nos + "单据不是移库确认状态");
				flag = false;
				return;
			}
		
			if (nos_bill != "") {
				$.ligerDialog.error("冲账失败！" + nos_bill + "单据业务类型不允许冲账");
				flag = false;
				return;
			}
			
			
			$.ligerDialog.confirm('确定要冲销吗?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("offsetOutSpecial.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.state=="true"){
							query();
						}
					});
				}
			});
			
			
			
		}
		
	}
	
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
								+ this.copy_code + "@" + this.out_no);
					});
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteAssOutSpecial.do", {
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
				+ " copy_code=" + vo[2] + "&" + "out_no=" + vo[3];

		parent.$.ligerDialog
				.open({
					title : '科室移库修改',
					height : $(window).height(),
					width : $(window).width(),
					url : 'hrp/ass/assspecial/asstran/out/assOutSpecialUpdatePage.do?isCheck=false&'
							+ parm,
					modal : true,
					showToggle : false,
					showMax : true,
					showMin : false,
					isResize : true,
					parentframename : window.name, //用于parent弹出层调用本页面的方法或变量
				});

	}
	function loadDict() {

		var param = {
			query_key : ''
		};

		autocomplete("#dept_id", "../../../queryDeptDict.do?isCheck=false",
				"id", "text", true, true, param, true);

		autocomplete("#store_id",
				"../../../queryHosStoreDict.do?naturs_code=02&isCheck=false", "id", "text",
				true, true, param, true);

		autocomplete("#create_emp",
				"../../../../../hrp/sys/queryUserDict.do?isCheck=false", "id",
				"text", false, false, false, false);

		$("#create_date_beg").ligerTextBox({
			width : 90
		});

		$("#audit_date_beg").ligerTextBox({
			width : 90
		});

		$("#create_date_end").ligerTextBox({
			width : 90
		});

		$("#audit_date_end").ligerTextBox({
			width : 90
		});

		/* $("#state").ligerComboBox({
			width : 160
		}); */
		$('#state').ligerComboBox({
			data:[{id:0,text:'新建'},{id:2,text:'移库确认'}],
			valueField: 'id',
            textField: 'text',
			cancelable:true,
			width:160
		});
		
		$("#out_no").ligerTextBox({width:160});
		
		$("#ass_name").ligerTextBox({width:230});
		
		$('#bill_type').ligerComboBox({
			data:[{id:'01',text:'科室领用'},{id:'04',text:'科室退库'}],
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

		hotkeys('A', add);
		hotkeys('D', remove);

		hotkeys('Q', updateConfirm);

		hotkeys('P', print);

	}

	//打印模板设置
    function printSet(){
	  
    	var useId=0;//统一打印
		if('${ass_05088}'==1){
			//按用户打印
			useId='${user_id }';
		}else if('${ass_05088}'==2){
			//按仓库打印
			if(liger.get("store_id").getValue()==""){
				$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
				return;
			}
			useId=liger.get("store_id").getValue().split("@")[0];
		}
    	
		officeFormTemplate({template_code:"0508803",use_id : useId});
    }


    //打印
    function print(){
    	var useId=0;//统一打印
 		if('${ass_05088}'==1){
 			//按用户打印
 			useId='${user_id }';
 		}else if('${ass_05088}'==2){
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
		
			var out_no ="" ;
			$(data).each(function (){
				
				out_no  += "'"+this.out_no+"',"
					
			});
			
			 var para={
	    			paraId :out_no.substring(0,out_no.length-1) ,
	    			class_name:"com.chd.hrp.ass.serviceImpl.tran.out.AssOutSpecialServiceImpl",
	    			method_name:"assOutSpecialByPrintTemlate",
	    			template_code:'0508803',
	    			isPrintCount:false,//更新打印次数
	    			isPreview:true,//预览窗口，传绝对路径
	    			use_id:useId,
	    			p_num:1
	    			//isSetPrint:flag
	    	 }; 
		 
           	ajaxJsonObjectByUrl("queryState.do?isCheck=false",{paraId :out_no.substring(0,out_no.length-1) },function (responseData){
           		if(responseData.state=="true"){
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
  
  function openViewAssIn(obj){
	  var vo = obj.split("|");
		var parm = "group_id=" + vo[0] + "&" + "hos_id=" + vo[1] + "&"
				+ " copy_code=" + vo[2] + "&" + "out_no=" + vo[3];

		parent.$.ligerDialog.open({
			title: '采购入库查看',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/ass/assspecial/asstran/out/assViewInMainPage.do?isCheck=false&'+parm,
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
  }
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit"
		width="100%">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">制单日期：</td>
			<td align="left" class="l-table-edit-td" width="5%"><input
				name="create_date_beg" type="text" id="create_date_beg"
				class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="left" width="2%">至：</td>
			<td align="left" class="l-table-edit-td"><input
				name="create_date_end" type="text" id="create_date_end"
				class="Wdate"
				onFocus="WdatePicker({minDate:'#F{$dp.$D(\'create_date_beg\');}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">状态：</td>
			<td align="left" class="l-table-edit-td">
			 <!-- <select id="state"
				name="state">
					<option value="">全部</option>
					<option value="0">新建</option>
					<option value="2">移库确认</option>
			</select>  -->
			<input  name="state" type="text" id="state"/>
			</td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_id"
				type="text" id="dept_id" /></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">审核日期：</td>
			<td align="left" class="l-table-edit-td"><input
				name="audit_date_beg" type="text" id="audit_date_beg" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="left">至：</td>
			<td align="left" class="l-table-edit-td"><input
				name="audit_date_end" type="text" id="audit_date_end" class="Wdate"
				onFocus="WdatePicker({minDate:'#F{$dp.$D(\'audit_date_beg\');}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">单据类型：</td>
			<td align="left" class="l-table-edit-td">
				<!--  <select name="bill_type" id="bill_type">
					<option value="01">科室领用</option>
					<option value="04">科室退库</option>
				</select>  -->
				<input  name="bill_type" type="text" id="bill_type"/> 
			</td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">仓库：</td>
			<td align="left" class="l-table-edit-td"><input name="store_id"
				type="text" id="store_id" /></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right"  class="l-table-edit-td" style="padding-left: 20px;">资产名称：</td>
			 <td align="left"  class="l-table-edit-td" colspan = "3"><input name="ass_name" type="text" id="ass_name" /></td>
			 <td align="right"  class="l-table-edit-td" style="padding-left: 20px;">出库单号：</td>
			 <td align="left"  class="l-table-edit-td" colspan = "3"><input name="out_no" type="text" id="out_no" /></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" width="10%">
			</td>
			<td align="left" class="l-table-edit-td" >
            	<input name="show_detail" type="checkbox" id="show_detail" onclick="showDetail();"/>&nbsp;&nbsp;显示明细
             </td>
		</tr>
	</table>

	<div id="maingrid"></div>
</body>
</html>
