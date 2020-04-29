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
<script src="<%=path%>/lib/hrp/med/med.js"	type="text/javascript"></script>
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    var renderFunc = {
			 
    		end_money:function(value){//金额
				return formatNumber(value, '${p08005 }', 1);
			},
			end_price:function(value){//单价
   			 return formatNumber(value, '${p08006 }', 1); 
   		 
			},
			end_amount:function(value){//数量
				return formatNumber(value, 2, 1);
			},
			
		 
	};
    
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
		loadHotkeys();
		query();
		 
		$('#show_zero').bind("change",function(){
			query();
		});
    });
    //查询
    function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
        //根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'b_year_month',
			value : $("#begin_year").val()+$("#begin_month").val()
		});
		grid.options.parms.push({
			name : 'e_year_month',
			value : $("#end_year").val()+$("#end_month").val()
		});
		grid.options.parms.push({
			name : 'store_id',
			value : liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[0]
		}); 
		grid.options.parms.push({
			name : 'location_id',
			value : liger.get("location_code").getValue() == null ? "" : liger.get("location_code").getValue()
		}); 
		grid.options.parms.push({
			name : 'type_level',
			value : liger.get("type_level").getText() == null ? "" : liger.get("type_level").getText()
		}); 
	/* 	grid.options.parms.push({
			name : 'med_type_id',
			value : liger.get("med_type_code").getValue() == null ? "" : liger.get("med_type_code").getValue().split(",")[0]
		});  */
		grid.options.parms.push({name : 'med_type_code',value : liger.get("med_type_code").getText().split(" ")[0]});
		grid.options.parms.push({
			name : 'inv_code',
			value : $("#inv_code").val()
		});
		grid.options.parms.push({
			name : 'begin_price',
			value : liger.get("begin_price").getValue() == null ? "" : liger.get("begin_price").getValue()
		}); 
		grid.options.parms.push({
			name : 'end_price',
			value : liger.get("end_price").getValue() == null ? "" : liger.get("end_price").getValue()
		}); 
		grid.options.parms.push({
			name : 'show_zero',
			value : $("#show_zero").prop("checked") ? 1 : 0
		}); 
		grid.options.parms.push({
			name : 'inv_model',//规格型号
			value : $("#inv_model").val()
		});
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [{
					display: '药品编码', name: 'inv_code', align: 'left', width: '100', frozen: true
				}, { 
		 			display: '储存编码', name: 'memory_encoding', align: 'left', width: '100', frozen: true
		 		}, { 
		 			display: '药品名称', name: 'inv_name', align: 'left', width: '150', frozen: true
		 		}, { 
		 			display: '规格型号', name: 'inv_model', align: 'left', width: '80'
		 		}, { 
		 			display: '计量单位', name: 'unit_name', align: 'left', width: '60'
		 		}, { 
		 			display: '期初余额', 
		 			columns : [{
		 				display: '数量', name: 'begin_amount', align: 'left', width: '80', 
			 			render : function(rowdata, rowindex, value) {
							return value ==null ? "" : formatNumber(value, 2, 1);
						}
			 		}, { 
			 			display: '单价', name: 'begin_price', align: 'right', width: '100',formatter:"###,##0.00",
			 			render : function(rowdata, rowindex, value) {
							return value ==null ? "" : formatNumber(value, '${p08006 }', 1);
						}
			 		}, { 
			 			display: '金额', name: 'begin_money', align: 'right', width: '100',formatter:"###,##0.00",
			 			render : function(rowdata, rowindex, value) {
							return value ==null ? "" : formatNumber(value, '${p08005 }', 1);
						}
		 			}]
		 		}, { 
		 			display: '本期收入', 
		 			columns : [{
		 				display: '数量', name: 'in_amount', align: 'left', width: '80', 
			 			render : function(rowdata, rowindex, value) {
							return value ==null ? "" : formatNumber(value, 2, 1);
						}
			 		}, { 
			 			display: '单价', name: 'in_price', align: 'right', width: '100',formatter:"###,##0.00",
			 			render : function(rowdata, rowindex, value) {
							return value ==null ? "" : formatNumber(value, '${p08006 }', 1);
						}
			 		}, { 
			 			display: '金额', name: 'in_money', align: 'right', width: '100',formatter:"###,##0.00",
			 			render : function(rowdata, rowindex, value) {
							return value ==null ? "" : formatNumber(value, '${p08005 }', 1);
						}
		 			}]
		 		}, { 
		 			display: '本期发出', 
		 			columns : [{
		 				display: '数量', name: 'out_amount', align: 'left', width: '80', 
			 			render : function(rowdata, rowindex, value) {
							return value ==null ? "" : formatNumber(value, 2, 1);
						}
			 		}, { 
			 			display: '单价', name: 'out_price', align: 'right', width: '100',formatter:"###,##0.00",
			 			render : function(rowdata, rowindex, value) {
							return value ==null ? "" : formatNumber(value, '${p08006 }', 1);
						}
			 		}, { 
			 			display: '金额', name: 'out_money', align: 'right', width: '100',formatter:"###,##0.00",
			 			render : function(rowdata, rowindex, value) {
							return value ==null ? "" : formatNumber(value, '${p08005 }', 1);
						}
		 			}]
		 		}, { 
		 			display: '期末余额', 
		 			columns : [{
		 				display: '数量', name: 'end_amount', align: 'left', width: '80', 
			 			render : function(rowdata, rowindex, value) {
							return value ==null ? "" : formatNumber(value, 2, 1);
						}
			 		}, { 
			 			display: '单价', name: 'end_price', align: 'right', width: '100',formatter:"###,##0.00",
			 			render : function(rowdata, rowindex, value) {
							return value ==null ? "" : formatNumber(value, '${p08006 }', 1);
						}
			 		}, { 
			 			display: '金额', name: 'end_money', align: 'right', width: '100',formatter:"###,##0.00",
			 			render : function(rowdata, rowindex, value) {
							return value ==null ? "" : formatNumber(value, '${p08005 }', 1);
						}
		 			}]
		 		}],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMedAccountBalanceInvHold.do',
			width: '100%', height: '98%', checkbox: false,rownumbers:true,
			delayLoad: true,//初始化不加载，默认false
			selectRowButtonOnly:true,//heightDiff: -10,
			toolbar: { items: [
				{ text: '查询（<u>Q</u>）', id:'search', click: query, icon:'search' },
				{ line:true },
				{ text: '打印', id:'print', click: print, icon:'print' },
				{ line:true }
			]}
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    //改变列
    function changeColumn(){
    	var colFlag = $("#is_only_end").is(":checked") ? 1 : 0;
    	if(colFlag == 0){
    		var columns = [{
				display: '药品编码', name: 'inv_code', align: 'left', width: '100', frozen: true
			}, { 
	 			display: '储存编码', name: 'memory_encoding', align: 'left', width: '100', frozen: true
	 		}, { 
	 			display: '药品名称', name: 'inv_name', align: 'left', width: '150', frozen: true
	 		}, { 
	 			display: '规格型号', name: 'inv_model', align: 'left', width: '80'
	 		}, { 
	 			display: '计量单位', name: 'unit_name', align: 'left', width: '60'
	 		}, { 
	 			display: '期初余额', 
	 			columns : [{
	 				display: '数量', name: 'begin_amount', align: 'left', width: '80', 
		 			render : function(rowdata, rowindex, value) {
						return value ==null ? "" : formatNumber(value, 2, 1);
					}
		 		}, { 
		 			display: '单价', name: 'begin_price', align: 'right', width: '100',formatter:"###,##0.00",
		 			render : function(rowdata, rowindex, value) {
						return value ==null ? "" : formatNumber(value, '${p08006 }', 1);
					}
		 		}, { 
		 			display: '金额', name: 'begin_money', align: 'right', width: '100',formatter:"###,##0.00",
		 			render : function(rowdata, rowindex, value) {
						return value ==null ? "" : formatNumber(value, '${p08005 }', 1);
					}
	 			}]
	 		}, { 
	 			display: '本期收入', 
	 			columns : [{
	 				display: '数量', name: 'in_amount', align: 'left', width: '80', 
		 			render : function(rowdata, rowindex, value) {
						return value ==null ? "" : formatNumber(value, 2, 1);
					}
		 		}, { 
		 			display: '单价', name: 'in_price', align: 'right', width: '100',formatter:"###,##0.00",
		 			render : function(rowdata, rowindex, value) {
						return value ==null ? "" : formatNumber(value, '${p08006 }', 1);
					}
		 		}, { 
		 			display: '金额', name: 'in_money', align: 'right', width: '100',formatter:"###,##0.00",
		 			render : function(rowdata, rowindex, value) {
						return value ==null ? "" : formatNumber(value, '${p08005 }', 1);
					}
	 			}]
	 		}, { 
	 			display: '本期发出', 
	 			columns : [{
	 				display: '数量', name: 'out_amount', align: 'left', width: '80', 
		 			render : function(rowdata, rowindex, value) {
						return value ==null ? "" : formatNumber(value, 2, 1);
					}
		 		}, { 
		 			display: '单价', name: 'out_price', align: 'right', width: '100',formatter:"###,##0.00",
		 			render : function(rowdata, rowindex, value) {
						return value ==null ? "" : formatNumber(value, '${p08006 }', 1);
					}
		 		}, { 
		 			display: '金额', name: 'out_money', align: 'right', width: '100',formatter:"###,##0.00",
		 			render : function(rowdata, rowindex, value) {
						return value ==null ? "" : formatNumber(value, '${p08005 }', 1);
					}
	 			}]
	 		}, { 
	 			display: '期末余额', 
	 			columns : [{
	 				display: '数量', name: 'end_amount', align: 'left', width: '80', 
		 			render : function(rowdata, rowindex, value) {
						return value ==null ? "" : formatNumber(value, 2, 1);
					}
		 		}, { 
		 			display: '单价', name: 'end_price', align: 'right', width: '100',formatter:"###,##0.00",
		 			render : function(rowdata, rowindex, value) {
						return value ==null ? "" : formatNumber(value, '${p08006 }', 1);
					}
		 		}, { 
		 			display: '金额', name: 'end_money', align: 'right', width: '100',formatter:"###,##0.00",
		 			render : function(rowdata, rowindex, value) {
						return value ==null ? "" : formatNumber(value, '${p08005 }', 1);
					}
	 			}]
	 		}];
    		colFlag = 1;
    	}else{
    		var columns = [{
				display: '药品编码', name: 'inv_code', align: 'left', width: '100', frozen: true
			}, { 
	 			display: '储存编码', name: 'memory_encoding', align: 'left', width: '100', frozen: true
	 		}, { 
	 			display: '药品名称', name: 'inv_name', align: 'left', width: '150', frozen: true
	 		}, { 
	 			display: '规格型号', name: 'inv_model', align: 'left', width: '80'
	 		}, { 
	 			display: '计量单位', name: 'unit_name', align: 'left', width: '60'
	 		}, { 
	 			display: '期末余额', 
	 			columns : [{
	 				display: '数量', name: 'end_amount', align: 'left', width: '80', type: 'float', 
		 			render : function(rowdata, rowindex, value) {
						return value ==null ? "" : formatNumber(value, 2, 1);
					}
		 		}, { 
		 			display: '单价', name: 'end_price', align: 'right', width: '100',formatter:"###,##0.00",
		 			render : function(rowdata, rowindex, value) {
						return value ==null ? "" : formatNumber(value, '${p08006 }', 1);
					}
		 		}, { 
		 			display: '金额', name: 'end_money', align: 'right', width: '100',formatter:"###,##0.00",
		 			render : function(rowdata, rowindex, value) {
						return value ==null ? "" : formatNumber(value, '${p08005 }', 1);
					}
	 			}]
	 		}];
    		colFlag = 0;
    	}

        grid.set('columns', columns); 
        grid.reRender();
    }
    
    
	 //打印回调方法
    function lodopPrint(){
    	var head="<table class='head' width='100%'><tr><td>单位：${sessionScope.hos_name}</td></tr>";
    	head=head+"<tr><td>统计年月："+liger.get("begin_year").getText()+liger.get("begin_month").getText()+"至"+liger.get("end_year").getText()+liger.get("end_month").getText()+"</td></tr>";
 		head=head+"</table>";
 		grid.options.lodop.head=head; 
 		grid.options.lodop.fn=renderFunc;
 		grid.options.lodop.title="药品收发结存表";
    }
	 
    //键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
		hotkeys('P', print);
	}
    
    function loadDict(){
		//字典下拉框
    	//返回当前年,当前月,
		var date = getCurrentDate();
        var aa = date.split(';');
        year = aa[0];
        month = aa[1];
		//返回当前年,当前月,当前日期,当前月第一天,当前月最后一天,上个月,上月第一天，上月最后一天
		autocompleteAsync("#begin_year", "../../queryMedYear.do?isCheck=false", "id", "text", true, true, "", false, year, "60");
        autocompleteAsync("#begin_month", "../../queryMedMonth.do?isCheck=false", "id", "text", true, true, "", false, month, "50");
		autocompleteAsync("#end_year", "../../queryMedYear.do?isCheck=false", "id", "text", true, true, "", false, year, "60");
		autocompleteAsync("#end_month", "../../queryMedMonth.do?isCheck=false", "id", "text", true, true, "", false, month, "50");
		autocompleteAsync("#store_code", "../../queryMedStore.do?isCheck=false", "id", "text", true, true, "", true);
		autocomplete("#location_code", "../../queryMedLocationDict.do?isCheck=false", "id", "text", true, true);
		autocomplete("#med_type_code", "../../queryMedTypeDict.do?isCheck=false", "id", "text", true, true);
		autocomplete("#type_level", "../../queryMedTypeLevel.do?isCheck=false", "id", "text", true, true);
		

        $("#inv_code").ligerTextBox({width:160});
        $("#begin_price").ligerTextBox({width:80});
        $("#end_price").ligerTextBox({width:80});
        $("#show_zero").attr("checked", true);
        $("#is_only_end").attr("checked", false);
        $("#inv_model").ligerTextBox({width:160});
        $("#type_level").ligerTextBox({width:210});
	}
    
  	//打印
	function print(){
    	
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
       			title:'药品收发结存表',
       			head:[
    				{"cell":0,"value":"单位: ${sessionScope.hos_name}","colspan":colspan_num,"br":true},
    				{"cell":0,"value":
    					"统计日期: " + $("#begin_year").val()+ "年"+ $("#begin_month").val()+ "月" +
    					" 至  "+ $("#end_year").val() +"年"+ $("#end_month").val() + "月",
    				"colspan":colspan_num,"br":true}
       			],
       			foot:[
    				{"cell":0,"value":"主管:","colspan":2,"br":false} ,
					{"cell":2,"value":"复核人:","colspan":colspan_num-2,"br":true},
					{"cell":0,"value":"制单人： ${sessionScope.user_name}","colspan":2,"br":false},
					{"cell":2,"value":"打印日期: " + cur_date,"colspan":colspan_num-2,"br":true}
       			],
       			columns:grid.getColumns(1),
       			headCount:2,//列头行数
       			autoFile:true,
       			type:3
       	};
   		ajaxJsonObjectByUrl("queryMedAccountBalanceInvHold.do?isCheck=false", selPara, function (responseData) {
   			printGridView(responseData,printPara);
		});

   		
    }
	
	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" >
        <tr>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  width="100px">
	            统计年月：
			</td>
			<td align="left" class="l-table-edit-td"  width="200px">
				<table >
					<tr>
						<td align="right" class="l-table-edit-td">
							<input name="begin_year" id="begin_year" type="text" required="true" validate="{required:true}" />
						</td>
						<td align="right" class="l-table-edit-td"  >
							年
						</td>
						<td align="right" class="l-table-edit-td">
							<input name="begin_month" id="begin_month" type="text" required="true" validate="{required:true}" />
						</td>
						<td align="right" class="l-table-edit-td"  >
							至：
						</td>
						<td align="right" class="l-table-edit-td">
							<input name="end_year" id="end_year" type="text" required="true" validate="{required:true}" />
						</td>
						<td align="right" class="l-table-edit-td"  >
							年
						</td>
						<td align="right" class="l-table-edit-td">
							<input name="end_month" id="end_month" type="text" required="true" validate="{required:true}" />
						</td>
	            	</tr>
				</table>
			</td>
			<td align="right" class="l-table-edit-td"  >
				仓库：
			</td>
			<td align="left" class="l-table-edit-td" >
				<input name="store_code" type="text" id="store_code" ltype="text" validate="{required:true,maxlength:100}" />
			</td>
			<td align="right" class="l-table-edit-td"  >
				货位：
			</td>
			<td align="left" class="l-table-edit-td" >
				<input name="location_code" type="text" id="location_code" ltype="text" validate="{required:false,maxlength:100}" />
			</td>
        </tr> 
        <tr>
			<td align="right" class="l-table-edit-td"  >
				药品级次：
			</td>
			<td align="left" class="l-table-edit-td" >
				<input name="type_level" type="text" id="type_level" ltype="text" validate="{required:false,maxlength:100}" />
			</td>
			<td align="right" class="l-table-edit-td"  >
				药品类别：
			</td>
			<td align="left" class="l-table-edit-td" >
				<input name="med_type_code" type="text" id="med_type_code" ltype="text" validate="{required:false,maxlength:100}" />
			</td>
			<td align="right" class="l-table-edit-td" >
				药品信息：
			</td>
           	 <td align="left" class="l-table-edit-td" >
			<input name="inv_model" type="text" id="inv_model" ltype="text"    validate="{required:true}" />
		</td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" >
				药品单价：
			</td>
            <td align="left" class="l-table-edit-td">
            	<table >
					<tr>
						<td align="right" class="l-table-edit-td">
							<input name="begin_price" id="begin_price" type="text" required="true" validate="{required:false}" />
						</td>
						<td align="right" class="l-table-edit-td"  >
							元 至
						</td>
						<td align="right" class="l-table-edit-td">
							<input name="end_price" id="end_price" type="text" required="true" validate="{required:false}" />
						</td>
						<td align="right" class="l-table-edit-td" >
							元
						</td>
	            	</tr>
				</table>
            </td>
            <td></td>
            <td align="left" class="l-table-edit-td">不显示零库存<input name="show_zero" type="checkbox" id="show_zero" ltype="text" /> 
                               只显示余额<input name="is_only_end" type="checkbox" id="is_only_end" onclick="changeColumn()" ltype="text" />
            </td>
            
            
	<!-- <td align="right" class="l-table-edit-td" >规格型号:</td>
		 <td align="left" class="l-table-edit-td" >
			<input name="inv_model" type="text" id="inv_model" ltype="text"    validate="{required:true}" />
		</td> -->
		
		
        </tr> 
    </table>
	<div id="maingrid"></div>
</body>
</html>
