<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<style type="text/css">
		html {
			overflow: auto !important;
		}
  		.divedline {
  			margin: 20px 0;
  		}
  	</style>
<script type="text/javascript">
	var grid;
	var gridManager = null;
	var userUpdateStr;
	var selectData = "";
	var clicked = 0;
	var ForT = [{ id: 0, text: '否' }, { id: 1, text: '是'}];
	var ForS = [{ id: 0, text: '新建' }, { id: 1, text: '审核'}];
	var ForR = [{ id: 0, text: '未完成' }, { id: 1, text: '已完成'}];
	$(function() {
		loadDict()//加载下拉框
		loadHead(null);
		if ('${state}' !=0) { 
          	toobarmanage = gridManager.toolbarManager; 
          	toobarmanage.setDisabled('saveDetail');
          	toobarmanage.setDisabled('save');
          	toobarmanage.setDisabled('delete');
          	toobarmanage.setDisabled('create');
  		}
		query(); 
	});
	//查询
	
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		grid.options.parms.push({ name : 'ins_id', value : $("#ins_id").val() });
		grid.loadData(grid.where);
	}
	 

	function loadHead() {
		grid = $("#maingrid")
				.ligerGrid(
						{
							columns : 
							
									[{
										display : '资产卡片号',
										name : 'ass_card_no',
										width: 150,
										align : 'left',
										textField : 'ass_card_no',
										editor : {
											type : 'select',
											valueField : 'ass_card_no',
											textField : 'ass_card_no',
											selectBoxWidth : 500,
											selectBoxHeight : 240,
											grid : {
												columns : [{
													 display : '资产卡片号',
													 name : 'ass_card_no',
													 align : 'left'
														},  {
													display : '编码',
													name : 'ass_code',
													align : 'left'
												}, {
													display : '名称',
													name : 'ass_name',
													align : 'left'
												}, {
													display : '分类名称',
													name : 'ass_type_name',
													align : 'left'
												}, {
													display : '是否停用',
													name : 'is_stop',
													align : 'left'
												} ],
												switchPageSizeApplyComboBox : false,
												onSelectRow : f_onSelectRow_detail,
												url : '../assmaintainplan/choseAssCardNo.do?isCheck=false&ass_nature='+liger.get("ass_nature").getValue(),
												pageSize : 30
											},
											alwayShowInDown : true,
											keySupport : true,
											autocomplete : true,
											onSuccess : function() {
												this.parent("tr").next(
														".l-grid-row").find(
														"td:first").focus();
											}
										},
										render : function(rowdata, rowindex,
												value) {
											return rowdata.ass_card_no;
										}

									},
									{ display: '资产编码', name: 'ass_code', width: 100,align: 'left', },
			                        { display: '资产名称', name: 'ass_name',width: 100, align: 'left', },
			                        { display: '型号', name: 'ass_mondl',width: 100, align: 'left',  },
			                        { display: '规格', name: 'ass_spec',width: 100, align: 'left', },
			                        { display: '品牌', name: 'ass_brand', width: 100,align: 'left', },
			 					    { display: '生产厂家', name: 'fac_name',width: 100, align: 'left', },
									{
										display : '状态',
										name : 'state',
										width: 100,
			 					 		editor : {
											type : 'select',
											valueField : 'id',
											textField : 'text',
											data : ForS,
											keySupport : true,
											autocomplete : true,
											onSuccess : function(data) {
												if (initvalue != undefined
														&& initvalue != "") {
													this.setValue(initvalue);
													initvalue = "";
												}
											}
										},
					                    render: function (item)
					                    {
					                        if (parseInt(item.state) == 0) {
					                        	return '新建';
					                        }else if(parseInt(item.state) == 1){
					                        	return '审核';
					                        }else{
					                        	return "";
					                        }
					                    },
										align : 'left'
									},
									{
										display : '是否报修',
										name : 'is_rep',
										width: 100,
			 					 		editor : {
											type : 'select',
											valueField : 'id',
											textField : 'text',
											data : ForT,
											keySupport : true,
											autocomplete : true,
											onSuccess : function(data) {
												if (initvalue != undefined
														&& initvalue != "") {
													this.setValue(initvalue);
													initvalue = "";
												}
											}
										},
					                    render: function (item)
					                    {
					                        if (parseInt(item.is_rep) == 1) {
					                        	return '是';
					                        }else if(parseInt(item.is_rep) == 0){
					                        	return '否';
					                        }else{
					                        	return "";
					                        }
					                    },
										align : 'left'
									},
									
									{
										display : '是否保养',
										name : 'is_main',
										width: 100,
			 					 		editor : {
											type : 'select',
											valueField : 'id',
											textField : 'text',
											data : ForT,
											keySupport : true,
											autocomplete : true,
											onSuccess : function(data) {
												if (initvalue != undefined
														&& initvalue != "") {
													this.setValue(initvalue);
													initvalue = "";
												}
											}
										},
					                    render: function (item)
					                    {
					                        if (parseInt(item.is_main) == 1) {
					                        	return '是';
					                        }else if(parseInt(item.is_main) == 0){
					                        	return '否';
					                        }else{
					                        	return "";
					                        }
					                    },
										align : 'left'
									},
									{
										display : '巡检结果',
										name : 'is_result',
										width: 100,
			 					 		editor : {
											type : 'select',
											valueField : 'id',
											textField : 'text',
											data : ForR,
											keySupport : true,
											autocomplete : true,
											onSuccess : function(data) {
												if (initvalue != undefined
														&& initvalue != "") {
													this.setValue(initvalue);
													initvalue = "";
												}
											}
										},
					                    render: function (item)
					                    {
					                        if (parseInt(item.is_result) == 1) {
					                        	return '已完成';
					                        }else if(parseInt(item.is_result) == 0){
					                        	return '未完成';
					                        }else{
					                        	return "";
					                        }
					                    },
					                    align : 'left'
									},
									{
										display : '备注',
										name : 'note',
										width: 100,
										editor : {
											type : 'text'
										},
										align : 'left'
									}

										 ],
							dataAction : 'server',
							dataType : 'server',
							usePager : true,
							url : '../assinspectiondetail/queryAssInspectionDetail.do?isCheck=false&ass_nature='+liger.get("ass_nature").getValue(),
							width : '100%',
							height : '100%',
							checkbox : true,
							enabledEdit : '${state}'!=0?false:true,
							alternatingRow : true,
							onBeforeEdit : f_onBeforeEdit,
							onBeforeSubmitEdit : f_onBeforeSubmitEdit,
							onAfterEdit : f_onAfterEdit,
							isScroll : true,
							checkbox : true,
							rownumbers : true,
							delayLoad :true,
							//notCellEditByColName:"ass_card_no", //资产卡片是主键不能进行修改
							selectRowButtonOnly : true,//heightDiff: -10,
							toolbar : {
								items : [ 
								          

									{
																text : '关闭',
																id : 'close',
																click : this_close,
																icon : 'candle'
															} 
								]
							}
						});

		gridManager = $("#maingrid").ligerGetGridManager();
	}
	var rowindex_id = "";
	var column_name="";
	function f_onBeforeEdit(e) {
		rowindex_id = e.rowindex;
		clicked = 0;
		column_name=e.column.name;
	}
	//选中回充数据
	function f_onSelectRow_detail(data, rowindex, rowobj) {
		selectData = "";
		selectData = data;
		if(column_name == "ass_card_no"){
 			if (selectData != "" || selectData != null) {
 				grid.updateRow(rowindex_id, {
 					ass_card_no : data.ass_card_no,
 					ass_code : data.ass_code,
 					ass_name : data.ass_name,
 					fac_id : data.fac_id,
 					fac_no : data.fac_no,
 					fac_code : data.fac_code,
 					fac_name : data.fac_name,
 					ass_mondl : data.ass_mondl,
 					ass_spec : data.ass_spec
 				});
 			}
 		} 
		return true;
	}
	function f_onSelectRow(data, rowindex, rowobj) {
		return true;
	}
	// 编辑单元格提交编辑状态之前作判断限制
	function f_onBeforeSubmitEdit(e) {
		return true;
	}
	// 跳转到下一个单元格之前事件
	function f_onAfterEdit(e) {
		return true;
	}
	
	

	function this_close() {
frameElement.dialog.close();
}
	
	function loadDict() {
		//字典下拉框
		
		autocomplete("#dept_id", "../queryDeptDict.do?isCheck=false", "id", "text", true, true,'',false,'${dept_id}@${dept_no}');
		
		liger.get("dept_id").setValue("${dept_id}@${dept_no}");
    	
    	liger.get("dept_id").setText("${dept_code} ${dept_name}");
		 
		autocomplete("#ass_nature", "../queryAssNaturs.do?isCheck=false", "id","text", true, true,"",false,"${ass_nature}");
        
    	liger.get("ass_nature").setValue("${ass_nature}");
    	
    	liger.get("ass_nature").setText("${nature_name}");
    	
    	$("#ins_no").ligerTextBox({disabled:true,cancelable: false,width:160});
    	
    	$("#ins_no").ligerTextBox({ width : 160 });
    	
		$("#ins_name").ligerTextBox({ width : 160 });
		
		$("#ass_year").ligerTextBox({ width : 160 });
		
		$("#ass_month").ligerTextBox({ width : 160 });
		
		$("#ass_nature").ligerTextBox({ width : 160 });
		
		$("#dept_id").ligerTextBox({ width : 160 });  
		
	}
 
	function is_addRow() {
		setTimeout(function() { //当数据为空时 默认新增一行
			grid.addRow();
		}, 1000);

	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;" onload="is_addRow()">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<input type="hidden" id="ins_id" name="ins_id" value="${ins_id }"/>
	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr> 
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>巡检编号：</td>
            <td align="left" class="l-table-edit-td"><input name="ins_no" type="text" id="ins_no" disabled="disabled" value="${ins_no}"  /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>巡检名称：</td>
            <td align="left" class="l-table-edit-td"><input name="ins_name" type="text" id="ins_name" value="${ins_name}"  /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>巡检科室：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" value="${dept_id}"   /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>统计年度：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_year" type="text" id="ass_year" value="${ass_year}" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy'})" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>统计月份：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_month" type="text" id="ass_month" value="${ass_month}" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'MM'})" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>资产性质：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_nature" type="text" id="ass_nature" value="${ass_nature}"   /></td>
            <td align="left"></td>
        </tr> 
       <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">备注：</td>
            <td align="left" class="l-table-edit-td" colspan="4">
            	<textarea rows="3" cols="70"  name="note"  id="note"  >${note }</textarea>
             </td>
             <td align="left"></td>
        </tr> 
	</table>

	<div id="maingrid"></div>
 
</body>
</html>
