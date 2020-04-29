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
    		begin_money:function(value){//期初库存金额
    			 return formatNumber(value, '${p08005 }', 1); 
    		 
			},
			in_money:function(value){//本期增加金额
				 return formatNumber(value, '${p08005 }', 1); 
			},
			out_money:function(value){//本期减少金额
				 return formatNumber(value, '${p08005 }', 1); 
			},
			
			remove_zero_error:function(value){//进销误差
				 return formatNumber(value, '${p08005 }', 1); 
			},
			end_money:function(value){//期末结存金额
				 return formatNumber(value, '${p08005 }', 1); 
			} 
	}; 
    
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
		 //loadHotkeys();
		 query();
    });
    //查询
    function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
		
		var begin_date = $("#begin_date").val();
		var end_date = $("#end_date").val();
			
		if(begin_date == ''){
			$.ligerDialog.error('开始之间不能为空');
			return ;
		}
		
		if(end_date == ''){
			$.ligerDialog.error('结束期间不能为空');
			return ; 
		}
		if(begin_date > end_date){
			$.ligerDialog.error('开始期间不能大于结束期间');
			return;
		}
		
        //根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'begin_date',
			value : $("#begin_date").val()
		});
		grid.options.parms.push({
			name : 'end_date',
			value : $("#end_date").val()
		}); 
		
		grid.options.parms.push({
			name : 'store_id',
			value : liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[0]
		});
		
		grid.options.parms.push({
			name : 'med_type_id',
			value : liger.get("med_type_code").getValue() == null ? "" : liger.get("med_type_code").getValue().split(",")[0]
		}); 
		
		grid.options.parms.push({
			name : 'is_charge',
			value : liger.get("is_charge").getValue() == null ? "" : liger.get("is_charge").getValue()
		}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [{ 
		 			display: '药品类别编码', name: 'med_type_code', align: 'left', width: '150'
		 		}, { 
		 			display: '药品类别名称', name: 'med_type_name', align: 'left', width: '150'
		 		}, { 
		 			display: '期初库存金额', name: 'begin_money', align: 'right', width: '150',formatter:"###,##0.00",
		 			render : function(rowdata, rowindex, value) {
						return formatNumber(rowdata.begin_money ==null ? 0 : rowdata.begin_money, '${p08005 }', 1);
					}
		 		}, { 
		 			display: '本期增加金额', name: 'in_money', align: 'right', width: '150',formatter:"###,##0.00",
		 			render : function(rowdata, rowindex, value) {
						return formatNumber(rowdata.in_money ==null ? 0 : rowdata.in_money, '${p08005 }', 1);
					}
		 		}, { 
		 			display: '本期减少金额', name: 'out_money', align: 'right', width: '150',formatter:"###,##0.00",
		 			render : function(rowdata, rowindex, value) {
						return formatNumber(rowdata.out_money ==null ? 0 : rowdata.out_money, '${p08005 }', 1);
					}
		 		}, { 
		 			display: '进销误差', name: 'remove_zero_error', align: 'right', width: '150',formatter:"###,##0.00",
		 			render : function(rowdata, rowindex, value) {
						return formatNumber(rowdata.remove_zero_error ==null ? 0 : rowdata.remove_zero_error, '${p08005 }', 1);
					}
		 		}, { 
		 			display: '期末结存金额', name: 'end_money', align: 'right', width: '150',formatter:"###,##0.00",
		 			render : function(rowdata, rowindex, value) {
						return formatNumber(rowdata.end_money ==null ? 0 : rowdata.end_money, '${p08005 }', 1);
					}
		 		}],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMedAccountBalanceStockInvBalance.do',
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
 		grid.options.lodop.title="库存药品收发账表";
    }
    
   /*  //键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
		hotkeys('A', add);
		hotkeys('C', copy_no);
		hotkeys('O', offset);
		hotkeys('D', remove);
		hotkeys('S', audit);
		hotkeys('U', unAudit);
		hotkeys('F', confirm);
		hotkeys('P', print);
	}
     */
   
    function loadDict(){
		//字典下拉框
		autocomplete("#store_code", "../../queryMedStoreByRead.do?isCheck=false", "id", "text", true, true);
		autocomplete("#med_type_code", "../../queryMedTypeDict.do?isCheck=false", "id", "text", true, true, {is_last : 1},false,'',240);
		autocomplete("#is_charge", "../../queryMedYearOrNo.do?isCheck=false", "id", "text", true, true);
        $("#begin_date").ligerTextBox({width:100});
        autodate("#begin_date", "yyyy-MM");
        $("#end_date").ligerTextBox({width:100});
        autodate("#end_date", "yyyy-MM");
        $("#med_type_code").ligerTextBox({width:240});
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
   			title:'库存药品收发账表',
   			head:[
				{"cell":0,"value":"单位: ${sessionScope.hos_name}","colspan":colspan_num,"br":true},
				{"cell":0,"value":"统计日期: " + $("#begin_date").val() +" 至  "+ $("#end_date").val(),"colspan":colspan_num,"br":true}
   			],
   			foot:[
				{"cell":0,"value":"主管:","colspan":2,"br":false} ,
				{"cell":2,"value":"复核人:","colspan":3,"br":false},
				{"cell":colspan_num-2,"value":"制单人： ${sessionScope.user_name}","colspan":2,"br":true},
				{"cell":0,"value":"打印日期: " + cur_date,"colspan":colspan_num,"br":true}
   			],
   			columns:grid.getColumns(1),
   			headCount:1,//列头行数
   			autoFile:true,
   			type:3
   		};
   		ajaxJsonObjectByUrl("queryMedAccountBalanceStockInvBalance.do?isCheck=false", selPara, function (responseData) {
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
            <td align="right" class="l-table-edit-td"  width="10%">
            	<font color="red" size="2">*</font>查询期间：
            </td>
            <td align="left" class="l-table-edit-td"  width="20%">
				<table>
					<tr>
						<td align="left" class="l-table-edit-td">
							<input class="Wdate" name="begin_date" id="begin_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM'})"/>
						</td>
						<td align="right" class="l-table-edit-td"  >
							至：
						</td>
						<td align="left" class="l-table-edit-td">
							<input class="Wdate" name="end_date" id="end_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM'})"/>
						</td>
            		</tr>
				</table>
	        </td>
			<td align="right" class="l-table-edit-td"  width="10%">
				仓库：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="store_code" type="text" id="store_code" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
			<td align="right" class="l-table-edit-td"  width="10%">
				是否收费：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="is_charge" type="text" id="is_charge" ltype="text" />
            </td>
        </tr>
        	<td align="right" class="l-table-edit-td"  width="10%">
				药品类别：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="med_type_code" type="text" id="med_type_code" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
        <tr>
        	
        </tr> 
    </table>
	<div id="maingrid"></div>
</body>
</html>
