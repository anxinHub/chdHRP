<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <!-- jsp:include page="${path}/inc.jsp"/-->
    <jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
	<script src="<%=path%>/lib/hrp/med/med.js"	type="text/javascript"></script>
    <script type="text/javascript">
     var grid;
     var gridManager;
     
     $(function (){
		loadDict();//加载下拉框
		initBus();
        //loadForm();
		loadHead();
		queryDetail();
     });  
     
	function queryDetail(){
		grid.options.parms=[];
		grid.options.newPage=1;
        //根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'in_id',
			value : '${medInMain.in_id}'
		});

    	//加载查询条件
    	grid.loadData(grid.where);
	}
     
	function loadForm(){
    
	$.metadata.setType("attr", "validate");
     var v = $("form").validate({
         errorPlacement: function (lable, element)
         {
             if (element.hasClass("l-textarea"))
             {
                 element.ligerTip({ content: lable.html(), target: element[0] }); 
             }
             else if (element.hasClass("l-text-field"))
             {
                 element.parent().ligerTip({ content: lable.html(), target: element[0] });
             }
             else
             {
                 lable.appendTo(element.parents("td:first").next("td"));
             }
         },
         success: function (lable)
         {
             lable.ligerHideTip();
             lable.remove();
         },
         submitHandler: function ()
         {
             $("form .l-text,.l-textarea").ligerHideTip();
         }
     });
     //$("form").ligerForm(); 
 }       
   
	function loadDict(){
    	//字典下拉框
    	autocompleteAsync("#bus_type_code", "../../queryMedBusType.do?isCheck=false", "id", "text", true, true, {codes : '14'}, false, "${medInMain.bus_type_code}");
		autocompleteAsync("#store_code", "../../queryMedStore.do?isCheck=false", "id", "text", true, true);//, "", false, "${medInMain.store_id},${medInMain.store_no}");
		liger.get("store_code").setValue("${medInMain.store_id},${medInMain.store_no}");
		liger.get("store_code").setText("${medInMain.store_code} ${medInMain.store_name}");
		
		autocomplete("#stocker", "../../queryMedStockEmp.do?isCheck=false", "id", "text", true, true, "", false, "${medInMain.stocker}");
		liger.get("stocker").setValue("${medInMain.stocker}}");
		liger.get("stocker").setText("${medInMain.stocker_code} ${medInMain.stocker_name}");
		
		autocompleteAsync("#sup_code", "../../queryHosSupDict.do?isCheck=false", "id", "text", true, true, "", false, false, 160, false, 240);
		liger.get("sup_code").setValue("${medInMain.sup_id},${medInMain.sup_no}");
		liger.get("sup_code").setText("${medInMain.sup_code} ${medInMain.sup_name}");
		
		autocomplete("#stock_type_code", "../../queryMedStockType.do?isCheck=false", "id", "text", true, true, "", false, "${medInMain.stock_type_code}");
		
		autocomplete("#app_dept", "../../queryHosDeptByPerm.do?isCheck=false", "id", "text", true, true, {is_last : 1}, false, "${medInMain.app_dept}");
		liger.get("app_dept").setValue("${medInMain.app_dept}");
		liger.get("app_dept").setText("${medInMain.app_dept_code} ${medInMain.app_dept_name}");
		
		autocomplete("#protocol_code", "../../queryMedProtocolMain.do?isCheck=false", "id", "text", true, true, "", false, false, 160, false, 240);
		liger.get("protocol_code").setValue("${medInMain.protocol_id},${medInMain.protocol_code}");
		liger.get("protocol_code").setText("${medInMain.protocol_code} ${medInMain.protocol_name}");
		
		autocomplete("#proj_code", "../../queryMedProj.do?isCheck=false", "id", "text", true, true, "", false, false, 160, false, 240);
		liger.get("proj_code").setValue("${medInMain.proj_id}");
		liger.get("proj_code").setText("${medInMain.proj_code} ${medInMain.proj_name}");
		//格式化文本框
        $("#in_no").ligerTextBox({width:160, disabled:true});
        $("#in_date").ligerTextBox({width:160});
        $("#brief").ligerTextBox({width:320});
        //格式化按钮
		$("#print").ligerButton({click: printDate, width:90});
		$("#close").ligerButton({click: this_close, width:90});
    } 
    
    function initBus(){
    	if("${medInMain.bus_type_code}" != 2){
    		$("#stocker_span").css("display", "none");
			$("#stocker").ligerComboBox({width:160,disabled:true,cancelable: false});

    		$("#stock_type_code_span").css("display", "none");
			$("#stock_type_code").ligerComboBox({width:160,disabled:true,cancelable: false});

    		$("#sup_code_span").css("display", "none");
			$("#sup_code").ligerComboBox({width:160,disabled:true,cancelable: false});
		}else{
    		$("#stocker_span").css("display", "inline");
			$("#stocker").ligerComboBox({width:160,disabled:false});

    		$("#stock_type_code_span").css("display", "inline");
			$("#stock_type_code").ligerComboBox({width:160,disabled:false});

    		$("#sup_code_span").css("display", "inline");
			$("#sup_code").ligerComboBox({width:160,disabled:false});
		}
    }
	
	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [{
				display : '明细ID', name : 'in_detail_id', minWidth : 100, align : 'left'
			}, {
				display : '药品编码', name : 'inv_code', minWidth : 100, align : 'left',
				/* totalSummary: {
                    align : 'right',
                    render: function (suminf, column, cell) {
                    	return '<div>合计：</div>';
                    }
                } */
			}, {
				display : '药品变更号', name : 'inv_no', align : 'left'
			}, {
				display : '药品名称(E)', name : 'inv_id', textField : 'inv_name', minWidth : 180, align : 'left',
			}, {
				display : '规格型号', name : 'inv_model', minWidth : 80, align : 'left'
			}, {
				display : '计量单位', name : 'unit_name', minWidth : 80, align : 'left'
			}, {
				display : '数量(E)', name : 'amount', minWidth : 80, type : 'float', align : 'left',
				editor : {
					type : 'float',
				}
			}, {
				display : '单价(E)', name : 'price', minWidth : 80, align : 'right', 
				editor : {
					type : 'numberbox',
					precision : '${p08006 }'
				},
				render : function(rowdata, rowindex, value) {
					rowdata.price = value == null ? "" : formatNumber(value, '${p08006 }', 0);
					return value == null ? "" : formatNumber(value, '${p08006 }', 1);
				}
			}, {
				display : '金额', name : 'amount_money', minWidth : 80, align : 'right',
				render : function(rowdata, rowindex, value) {
					rowdata.amount_money = value == null ? "" : formatNumber(value, '${p08005 }', 0);
					return value == null ? "" : formatNumber(value, '${p08005 }', 1);
				},
				/* totalSummary: {
					align : 'right',
                    render: function (suminf, column, cell) {
                        return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, '${p08005 }', 1)+ '</div>';
                    }
                } */
			}, {
				display : '包装单位(E)', name : 'pack_code', textField : 'pack_name', minWidth : 80, align : 'left',
				editor : {
					type : 'select',
					valueField : 'id',
					textField : 'text',
					url : '../../queryMedHosPackage.do?isCheck=false',
					keySupport : true,
					autocomplete : true,
				},
			}, {
				display : '转换量(E)', name : 'num_exchange', minWidth : 80, type : 'float', align : 'left',
				editor : {
					type : 'float',
				}
			}, {
				display : '包装件数(E)', name : 'num', minWidth : 80, type : 'float', align : 'left',
				editor : {
					type : 'float',
				},
			}, {
				display : '包装单价', name : 'pack_price', minWidth : 80, align : 'right',
				render : function(rowdata, rowindex, value) {
					rowdata.pack_price = value == null ? "" : formatNumber(value, '${p08006 }', 0);
					return value == null ? "" : formatNumber(value, '${p08006 }', 1);
				}
			}, {
				display : '生产批号(E)', name : 'batch_no', minWidth : 80, align : 'left',
				editor : {
					type : 'text',
				}
			}, {
				display : '批次', name : 'batch_sn', minWidth : 80, align : 'left',
			}, {
				display : '有效日期(E)', name : 'inva_date', type: 'date', align : 'left', format: 'yyyy-MM-dd', minWidth : 100,
				editor : {
					type : 'date',
				},
			}, {
				display : '灭菌日期(E)', name : 'disinfect_date', type: 'date', align : 'left', format: 'yyyy-MM-dd', minWidth : 100,
				editor : {
					type : 'date',
				},
			}, {
				display : '条形码(E)', name : 'sn', minWidth : 80, align : 'left',
				editor : {
					type : 'text',
				}
			}, {
				display : '是否个体码', name : 'is_per_bar', minWidth : 80, align : 'left',
				render : function(rowdata, rowindex, value) {
					if(value == 0){
						return "否";
					}else if(value == 1){
						return "是";
					}else{
						rowdata.is_per_bar = 0;
						return "否";
					}
				}
			}, {
				display : '院内码', name : 'bar_code', minWidth : 80, align : 'left',
			}, {
				display : '批发单价(E)', name : 'sale_price', minWidth : 80, align : 'right', 
				editor : {
					type : 'numberbox',
					precision : '${p08006 }'
				},
				render : function(rowdata, rowindex, value) {
					rowdata.sale_price = value == null ? "" : formatNumber(value, '${p08006 }', 0);
					return value == null ? "" : formatNumber(value, '${p08006 }', 1);
				}
			}, {
				display : '批发金额', name : 'sale_money', minWidth : 80, align : 'right',
				render : function(rowdata, rowindex, value) {
					rowdata.sale_money = value == null ? "" : formatNumber(value, '${p08005 }', 0);
					return value == null ? "" : formatNumber(value, '${p08005 }', 1);
				},
				/* totalSummary: {
					align : 'right',
                    render: function (suminf, column, cell) {
                        return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, '${p08005 }'', 1)+ '</div>';
                    }
                } */
			}, {
				display : '零售单价(E)', name : 'sell_price', minWidth : 80, align : 'right', 
				editor : {
					type : 'numberbox',
					precision : '${p08006 }'
				},
				render : function(rowdata, rowindex, value) {
					rowdata.sell_price = value == null ? "" : formatNumber(value, '${p08006 }', 0);
					return value == null ? "" : formatNumber(value, '${p08006 }', 1);
				}
			}, {
				display : '零售金额', name : 'sell_money', minWidth : 80, align : 'right',
				render : function(rowdata, rowindex, value) {
					rowdata.sell_money = value == null ? "" : formatNumber(value, '${p08005 }', 0);
					return value == null ? "" : formatNumber(value, '${p08005 }', 1);
				},
				/* totalSummary: {
					align : 'right',
                    render: function (suminf, column, cell) {
                        return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, '${p08005 }', 1)+ '</div>';
                    }
                } */
			}, {
				display : '货位名称(E)', name : 'location_id', textField : 'location_name', minWidth : 80, align : 'left',
				editor : {
					type : 'select',
					valueField : 'id',
					textField : 'text',
					url : '../../queryMedLocationDict.do?isCheck=false',
					keySupport : true,
					autocomplete : true,
				}
			}, {
				display : '备注(E)', name : 'note', minWidth : 80, align : 'left',
				editor : {
					type : 'text',
				}
			} ],
			dataAction : 'server', dataType : 'server', usePager : false, url : '../in/queryMedStorageInDetail.do?isCheck=false', 
			width : '100%', height : '93%', checkbox : true, enabledEdit : false, alternatingRow : true, 
			isScroll : true, rownumbers : true, delayLoad : true,//初始化明细数据
			selectRowButtonOnly : true,//heightDiff: -10,
		});
		gridManager = $("#maingrid").ligerGetGridManager();
		
		grid.toggleCol("in_detail_id", false);
		grid.toggleCol("inv_no", false);
	}

	//打印
	function printDate(){
		
	}
	
	//关闭当前页面
	function this_close(){
		frameElement.dialog.close();
	}
    </script>
  
</head>
  
<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post"  id="form1" >
		<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
			<tr>
	            <td style="display: none;">
	            	<input name="group_id" type="text" id="group_id" value="${medInMain.group_id}" ltype="text" />
	            	<input name="hos_id" type="text" id="hos_id" value="${medInMain.hos_id}" ltype="text" />
	            	<input name="copy_code" type="text" id="copy_code" value="${medInMain.copy_code}" ltype="text" />
	            	<input name="in_id" type="text" id="in_id" value="${medInMain.in_id}" ltype="text" />
	            	<input name="money_sum" type="text" id="money_sum" value="${medInMain.money_sum}" ltype="text" />
	            </td>
			</tr>
	        <tr>
	            <td align="right" class="l-table-edit-td" >
	            	<span style="color:red">*</span>入库单号：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="in_no" type="text" id="in_no" value="${medInMain.in_no}" ltype="text" disabled="disabled" required="true" validate="{required:true}" />
	            </td>
	            <td align="right" class="l-table-edit-td" >
	            	<span style="color:red">*</span>业务类型：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="bus_type_code" type="text" id="bus_type_code" ltype="text" required="true" validate="{required:true}" />
	            </td>
	            <td align="right" class="l-table-edit-td"  >
	            	<span style="color:red">*</span>仓库：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="store_code" type="text" id="store_code" ltype="text" required="true" validate="{required:true}" />
	            </td>
	        </tr> 
	        <tr>
	            <td align="right" class="l-table-edit-td" >
	            	<span style="color:red">*</span>制单日期：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input class="Wdate" name="in_date" id="in_date" value="${medInMain.in_date}" type="text" required="true" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" validate="{required:true}" />
	            </td>
	            <td align="right" class="l-table-edit-td"  >
	            	<span id="stocker_span" style="color:red">*</span>采购员：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="stocker" type="text" id="stocker" ltype="text" required="true" validate="{required:true}" />
	            </td>
	            <td align="right" class="l-table-edit-td"  >
	            	<span id="stock_type_code_span" style="color:red">*</span>采购类型：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="stock_type_code" type="text" id="stock_type_code" ltype="text" validate="{required:false}" />
	            </td>
	        </tr> 
			<tr>
	            <td align="right" class="l-table-edit-td"  >
	            	<span id="sup_code_span" style="color:red">*</span>供应商：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="sup_code" type="text" id="sup_code" ltype="text" required="true" validate="{required:true}" />
	            </td>
	            <td align="right" class="l-table-edit-td"  >
	            	申请科室：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="app_dept" type="text" id="app_dept" ltype="text" validate="{required:false}" />
	            </td>
	            <td align="right" class="l-table-edit-td" >
					协议编号：
	            </td>
	            <td align="left" class="l-table-edit-td" >
	            	<input name="protocol_code" type="text" id="protocol_code" ltype="text" validate="{required:false,maxlength:20}" />
	            </td>
			</tr>
	        <tr>
	            <td align="right" class="l-table-edit-td" >
					项&nbsp;&nbsp;&nbsp;&nbsp;目：
	            </td>
	            <td align="left" class="l-table-edit-td" >
	            	<input name="proj_code" type="text" id="proj_code" ltype="text" validate="{required:false,maxlength:20}" />
	            </td>
	            <td align="right" class="l-table-edit-td" >
					摘&nbsp;&nbsp;&nbsp;&nbsp;要：
	            </td>
	            <td align="left" class="l-table-edit-td" colspan="5">
	            	<input name="brief" type="text" id="brief" ltype="text" value="${medInMain.brief}" validate="{required:false,maxlength:50}" />
	            </td>
			</tr>
	    </table>
    </form>
    <div style="width: 100%; height: 100%;">
		<div id="maingrid"></div>
		<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%"  style="margin-top: 5px;">
			<tr>	
				<td align="center" class="l-table-edit-td" width="32%" >
					制单人：${medInMain.maker_name}
				</td>
				<td align="center" class="l-table-edit-td" width="32%" >
					审核人：${medInMain.checker_name}
				</td>
				<td align="center" class="l-table-edit-td" width="32%" >
					确认人：${medInMain.confirmer_name}
				</td>
			</tr>
			<tr>	
				<td align="center" class="l-table-edit-td" colspan="3">
					<!-- <button id ="print" accessKey="P"><b>打印（<u>P</u>）</b></button>
					&nbsp;&nbsp; -->
					<button id ="close" accessKey="C"><b>关闭（<u>C</u>）</b></button>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>
