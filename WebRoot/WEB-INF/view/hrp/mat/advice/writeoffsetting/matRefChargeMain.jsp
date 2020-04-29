<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
   <jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
    <script type="text/javascript">
    var grid;
	var gridManager = null;
    var dataFormat;
    var ForT = [{ id: 0, text: '否' }, { id: 1, text: '是'}];
    $(function (){
        loadDict();
        loadHead(null);
		$(':button').ligerButton({width:80});
    });  
    
    
    function validateGrid() {
		var data = gridManager.getData();
		var len = data.length;
		var msg = "";
		var msgMap = new HashMap();
		$.each(data, function(i, v) {
			if (isnull(v.inv_id)) {
				gridManager.deleteRow(i);
				len--;
				return;
			}
			if (isnull(v.inv_name)) {
				msg += "[材料名称]、";
			}
			if (isnull(v.charge_item_id)) {
				msg += "[收费项目]、";
			}
			if (msg != "") {
				msgMap.put(msg+"不能为空或不能为零！\n\r", "");
			}
		});
		if (msg != "") {
			$.ligerDialog.warn(msgMap.keySet());
			return false;
		}
		if (len == 0) {
			$.ligerDialog.warn("无数据保存");
			return false;
		}
		return true;
	} 
     
    function save(){
		var data = gridManager.getData();
        var formPara={
        ParamVo : JSON.stringify(data)
        };
        if(validateGrid()){
        	ajaxJsonObjectByUrl("addMatRefCharge.do?isCheck=false",formPara,function(responseData){
	            
 	           if(responseData.state=="true"){
 	            query();
 				is_addRow();
 	           }
 	       });
        }
    }
     
  //查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		grid.options.parms.push({
			name : 'inv_id',
			value : liger.get("inv_id").getValue()
		});
		grid.options.parms.push({
			name : 'charge_item_id',
			value : liger.get("charge_item_id").getValue()
		});
 		grid.loadData(grid.where);
	}

	function loadHead() {
		grid = $("#maingrid")
				.ligerGrid(
						{
							columns : [
									{
										display : '材料编码',
										name : 'inv_code',
										align : 'left',
										width: '100'
									},
									{
										display : '材料名称', name : 'inv_id', textField : 'inv_name', minWidth : 180, align : 'left',
										editor : {
											type : 'select',
											valueField : 'inv_id',
											textField : 'inv_name',
											selectBoxWidth : 500,
											selectBoxHeight : 240,
											grid : {
												columns : [ {
													display : '材料编码', name : 'inv_code', minWidth : 100, align : 'left'
												}, {
													display : '材料名称', name : 'inv_name', minWidth : 120, align : 'left'
												}, {
													display : '物资类别', name : 'mat_type_name', minWidth : 100, align : 'left'
												}, {
													display : '规格型号', name : 'inv_model', minWidth : 80, align : 'left'
												}, {
													display : '计量单位', name : 'unit_name', minWidth : 80, align : 'left'
												}, {
													display : '包装单位', name : 'pack_name', minWidth : 80, align : 'left'
												}, {
													display : '转换量', name : 'num_exchange', minWidth : 80, align : 'left'
												}, {
													display : '生产厂商', name : 'fac_name', minWidth : 100, align : 'left'
												}, {
													display : '计划单价', name : 'plan_price', minWidth : 80, align : 'left',
													render : function(rowdata, rowindex, value) {
														return formatNumber(value, '4', 1);
													}
												}, {
													display : '是否条码', name : 'is_bar', minWidth : 80, align : 'left',
													render : function(rowdata, rowindex, value) {
														return rowdata.is_bar == 1 ? '是' : '否';
													}
												}, {
													display : '货位', name : 'location_name', minWidth : 100, align : 'left'
												}, {
													display : '零售价', name : 'sell_price', minWidth : 80, align : 'left',
													render : function(rowdata, rowindex, value) {
														return formatNumber(value, '4', 1);
													}
												} ],
												switchPageSizeApplyComboBox : false,
												onSelectRow : f_onSelectRow_detail,
												url : '../../queryMatInvListDept.do?isCheck=false&mat_ref_chrage_exists=0',
												pageSize : 10,
												onSuccess:function(data,g){
													var editor = grid.editor.editParm.record;
													var item = data.Rows.map(function(v,i){
														return v.inv_name;
													});
													var index = editor.inv_name?item.indexOf(editor.inv_name):0;
													//加载完执行
													setTimeout(function(){
														g.select(data.Rows[index]);
													},80);
												}
											},
											delayLoad : false, keySupport : true, autocomplete : true,// rownumbers : true,
											onSuccess : function(data,grid) {
												this.parent("tr").next(".l-grid-row").find("td:first").focus();
											}
										},
										render : function(rowdata, rowindex, value) {
											return rowdata.inv_name;
										}
									},
									{
										display : '规格型号',
										name : 'inv_model',
										align : 'left',
										width: '100'
									},
									{
										display : '计量单位',
										name : 'unit_code',
										align : 'left',
										width: '100'
									},{
										display : '单价',
										name : 'plan_price',
										align : 'right',
										render: function(item)
								            {
								                    return formatNumber(item.plan_price,2,1);
								            } ,
											width: '100'
									}, {
										display : '是否代销',
										name : 'is_com',
										align : 'left',
										width: '100',
										render: function(item)
							            {
												if(item.is_com == '1'){
													return "是";
												}else{
													return "否";
												}
							            }
									} ,
									{
										display : '收费项目',
										name : 'charge_item_id',
										textField : 'charge_item_name',
										editor : {
											type : 'select',
											valueField : 'id',
											textField : 'text',
											url : '../../../cost/queryChargeItemArrt.do?isCheck=false',
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
										align : 'left',
										width: '140'
									}],
							dataAction : 'server',
							dataType : 'server',
							usePager : true,
							url : 'queryMatRefCharge.do',
							width : '100%',
							height : '100%',
							checkbox : true,
							enabledEdit : true,
							alternatingRow : true,
							onBeforeEdit : f_onBeforeEdit,
							onBeforeSubmitEdit : f_onBeforeSubmitEdit,
							onAfterEdit : f_onAfterEdit,
							isScroll : true,
							rownumbers : true,
							selectRowButtonOnly : true,//heightDiff: -10,
							toolbar : {
								items : [ {
									text : '保存',
									id : 'save',
									click : save,
									icon : 'save'
								}, {
									line : true
								},{
									text : '添加一行',
									id : 'add',
									click : is_addRow,
									icon : 'add'
								}, {
									line : true
								}, {
									text : '删除对应关系',
									id : 'delete',
									click : remove,
									icon : 'delete'
								} ]
							}
						});

		gridManager = $("#maingrid").ligerGetGridManager();
	}
	var rowindex_id = "";
	var column_name = "";
	function f_onBeforeEdit(e) {
		rowindex_id = e.rowindex;
		clicked = 0;
		column_name = e.column.name;
	}
	//选中回充数据
	function f_onSelectRow_detail(data, rowindex, rowobj) {

		selectData = "";
		selectData = data;
		//alert(JSON.stringify(data)); 
		if (column_name == "inv_id") {
			if (selectData != "" || selectData != null) {
				//回充数据 
				//grid.updateCell('apply_emp', 100, e.record);
				grid.updateRow(rowindex_id, {
					inv_id : data.inv_id,
					inv_code : data.inv_code,
					inv_name : data.inv_name,
					inv_model : data.inv_model,
					plan_price : data.plan_price,
					unit_code:data.unit_name,
					is_com : data.is_com
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
	function remove() {
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var ParamVo = [];
			var i = 0;
			$(data).each(
					function() {
						if(isnull(this.group_id)){
							gridManager.deleteRow(i);
						}else{
							ParamVo.push(this.group_id + "@" + this.hos_id + "@"
								+ this.copy_code + "@" + this.inv_id + "@"
								+ this.charge_item_id);
						}
						i++;
					});
			if(ParamVo == ""){
				query();
				return;
			}
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteMatRefCharge.do", {
						ParamVo : ParamVo.toString()
					}, function(responseData) {
						if (responseData.state == "true") {
							query();
							is_addRow();
						}
					});
				}
			});
		}
	}

	function is_addRow() {
		setTimeout(function() { //当数据为空时 默认新增一行
			grid.addRow();
		}, 1000);
	}
	function loadDict() {
		autocomplete("#inv_id",
				"../../queryMatInv.do?isCheck=false","id",
			    "text",true,true,null,false);
		autocomplete("#charge_item_id",
				"../../../cost/queryChargeItemArrt.do?isCheck=false","id",
			    "text",true,true,null,false);
		
	}
    </script>
  
  </head>
  
   <body onload="is_addRow()">
   <div id="pageloading" class="l-loading" style="display: none"></div>
    <input type="hidden" id="ass_in_id" name="ass_in_id"/>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;">材料信息：</td>
            <td align="left" class="l-table-edit-td"><input name="inv_id" type="text"  id="inv_id"   /></td>
            <td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">收费项目信息：</td>
			<td align="left" class="l-table-edit-td"><input
				name="charge_item_id" type="text" id="charge_item_id" 
				 /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"></td>
			<td align="left" class="l-table-edit-td"><button  onclick="query();"><b>查询</b></button></td>
			<td align="left"></td>
		</tr>
	</table>
    <div id="maingrid"></div>
    </body>
</html>
