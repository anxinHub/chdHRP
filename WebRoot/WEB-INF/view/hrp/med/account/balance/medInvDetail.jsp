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
    		in_amount:function(value){//入库数量
				return formatNumber(value, 2, 1);
			},
			in_price:function(value){//入库单价
				return formatNumber(value, '${p08006 }', 1);
			},
			in_amount_money:function(value){//入库金额
				return formatNumber(value, '${p08005 }', 1);
			},
			
			out_amount:function(value){//出库数量
				return formatNumber(value, 2, 1);
			},
			out_price:function(value){//出库单价
				return formatNumber(value, '${p08006 }', 1);
			},
			out_amount_money:function(value){//出库金额
				return formatNumber(value, '${p08005 }', 1);
			},
			 
			hold_amount:function(value){//结存数量
				return formatNumber(value, 2, 1);
			},
			hold_price:function(value){//结存单价
				return formatNumber(value,'${p08006 }', 1);
			},
			hold_amount_money:function(value){//结存金额
				return formatNumber(value, '${p08005 }', 1);
			}
	}; 
    
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
		loadHotkeys();
    });
    //查询
    function  query(){
    	var begin_date = $("#begin_date").val();
    	if(begin_date == ''){
    		$.ligerDialog.error('开始期间不能为空');
    		return;
    	}
    	var end_date = $("#end_date").val();
    	if(end_date == ''){
    		$.ligerDialog.error('结束期间不能为空');
    		return ; 
    	}
    	
    	if(begin_date.substring(0,4) != end_date.substring(0,4)){
    		$.ligerDialog.error('不支持跨年查询');
    		return;
    	}
    	if(begin_date> end_date){
    		$.ligerDialog.error('开始期间不能大于结束期间');
    		return ; 
    	}
    	
     	if(liger.get("store_code").getValue().split(",")[0] == ''){
    		$.ligerDialog.error('仓库不能为空');
    		return ; 
    	} 
    	
    /* 	if(liger.get("inv_code").getValue().split(",")[0] == ''){
    		$.ligerDialog.error('药品信息不能为空');
    		return ; 
    	} */
		grid.options.parms=[];
		grid.options.newPage=1;
        //根据表字段进行添加查询条件
		grid.options.parms.push({name : 'begin_date',value : $("#begin_date").val()});
		grid.options.parms.push({name : 'end_date',value : $("#end_date").val()});
		grid.options.parms.push({name : 'store_id',value : liger.get("store_code").getValue().split(",")[0]}); 
		grid.options.parms.push({name : 'store_no',value : liger.get("store_code").getValue().split(",")[1]}); 
		grid.options.parms.push({name : 'med_type_id',value : liger.get("med_type_code").getValue().split(",")[0]}); 
		grid.options.parms.push({name : 'med_type_no',value : liger.get("med_type_code").getValue().split(",")[1]});
		grid.options.parms.push({name : 'inv_id',value : $("#inv_code").val()});
	
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
		 			display: '日期', name: 'confirm_date', align: 'left'
		 		}, { 
		 			display: '单据号', name: 'out_in_no', align: 'left'
		 		}, { 
		 			display: '单据类型', name: 'bills_name', align: 'left'
		 		},  { 
		 			display: '业务类型', name: 'bus_type_name', align: 'left'
		 		}, { 
		 			display: '摘要', name: 'brief', align: 'left'
		 		},
		 		{ display: '入库',
		 		 columns : [{
		 				display: '数量', name: 'in_amount', align: 'left',
			 			render : function(rowdata, rowindex, value) {
							return value ==null ? "" : formatNumber(value, 2, 1);
						}
			 		},
			 		{
		 				display: '单价', name: 'in_price', align: 'left',
			 			render : function(rowdata, rowindex, value) {
							return value ==null ? "" : formatNumber(value,'${p08006 }', 1);
						}
			 		},
			 		{ 
			 			display: '金额', name: 'in_amount_money', align: 'right',
			 			render : function(rowdata, rowindex, value) {
							return value ==null ? "" : formatNumber(value, '${p08005 }', 1);
						}
			 		}]
		 		}, 
		 		{ display: '出库',
			 		 columns : [{
			 				display: '数量', name: 'out_amount', align: 'left',
				 			render : function(rowdata, rowindex, value) {
								return value ==null ? "" : formatNumber(value, 2, 1);
							}
				 		}, 
				 		{
			 				display: '单价', name: 'out_price', align: 'left',
				 			render : function(rowdata, rowindex, value) {
								return value ==null ? "" : formatNumber(value,'${p08006 }', 1);
							}
				 		},
				 		{ 
				 			display: '金额', name: 'out_amount_money', align: 'right',
				 			render : function(rowdata, rowindex, value) {
								return value ==null ? "" : formatNumber(value, '${p08005 }', 1);
							}
				 		}]
			 		}, { display: '结存',
				 		 columns : [{
				 				display: '数量', name: 'hold_amount', align: 'left',
					 			render : function(rowdata, rowindex, value) {
									return value ==null ? "" : formatNumber(value, 2, 1);
								}
					 		}, 
					 		{
				 				display: '单价', name: 'hold_price', align: 'left',
					 			render : function(rowdata, rowindex, value) {
									return value ==null ? "" : formatNumber(value,'${p08006 }', 1);
								}
					 		},
					 		{ 
					 			display: '金额', name: 'hold_amount_money', align: 'right',
					 			render : function(rowdata, rowindex, value) {
									return value ==null ? "" : formatNumber(value, '${p08005 }', 1);
								}
					 		}]
				 		}],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMedAccountBalanceInvDetail.do',
			width: '100%', height: '100%',rownumbers:true,
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
    
    //打印回调方法
    function lodopPrint(){
    	var head="<table class='head' width='100%'><tr><td>单位：${sessionScope.hos_name}</td></tr>";
 		head=head+"<tr><td>查询期间："+$("#begin_date").val()+"至"+$("#end_date").val()+"</td></tr>";
 		head=head+"</table>";
 		grid.options.lodop.head=head; 
 		grid.options.lodop.fn=renderFunc;
 		grid.options.lodop.title="药品明细表";
    }
    
    
    //键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
	}
 
    function loadDict(){
		//字典下拉框
		autocomplete("#store_code", "../../queryMedStore.do?isCheck=false", "id", "text", true, true,"",true);
		autocomplete("#med_type_code", "../../queryMedTypeDict.do?isCheck=false", "id", "text", true, true, {is_last : 1},false);
	//	autocomplete("#inv_code", "../../queryMedInv.do?isCheck=false", "id", "text", true, true,'',false,'',220);
        $("#begin_date").ligerTextBox({width:100});
        autodate("#begin_date", "yyyy-MM", "yyyy-MM");
        $("#end_date").ligerTextBox({width:100});
        autodate("#end_date", "yyyy-MM", "yyyy-MM");
        $("#inv_model").ligerTextBox({width:160 });
      $("#inv_code").ligerTextBox({width:220});
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
   		
   		var printPara={
   			headCount:2,
   			title:'药品明细表',
   			type:3,
   			columns:grid.getColumns(1)
   			};
   		ajaxJsonObjectByUrl("queryMedAccountBalanceInvDetail.do?isCheck=false", selPara, function (responseData) {
   			printGridView(responseData,printPara);
		});

   		
    }
	
	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
	<div class="search-block clearfix">
	
	<table>
		<tr>
			<td align="right" class="l-table-edit-td">查询期间： </td>
			<td align="left" class="l-table-edit-td"  width="20%">
				<table>
					<tr>
						<td>
							<input class="Wdate" name="begin_date" id="begin_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM'})"/>
						</td>
						<td align="right" class="l-table-edit-td"  > 至 </td>
						<td>
							<input class="Wdate" name="end_date" id="end_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM'})"/>
						</td>
            		</tr>
				</table>
	        </td>
	        <td align="right" class="l-table-edit-td"  width="10%"> 仓库： </td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="store_code" type="text" id="store_code" ltype="text" validate="{required:false}" />
            </td>
            <td align="right" class="l-table-edit-td" width="10%"> 药品类别：</td>
            <td align="left" class="l-table-edit-td" width="20%"> 
            	<input name="med_type_code" type="text" id="med_type_code" ltype="text" validate="{required:true,maxlength:20}" />
  			</td> 
		</tr> 
		<tr>
			 <td align="right" class="l-table-edit-td" width="10%">药品信息：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="inv_code" type="text" id="inv_code" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
            <td align="right" class="l-table-edit-td" width="10%">
				规格型号：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="inv_model" type="text" id="inv_model" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
		</tr> 
	</table>
		
	</div>
	<div id="maingrid"></div>
</body>
</html>
