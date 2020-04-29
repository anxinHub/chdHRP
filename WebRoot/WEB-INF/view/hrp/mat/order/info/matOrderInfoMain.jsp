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
<script src="<%=path%>/lib/hrp/mat/mat.js"	type="text/javascript"></script>
<script type="text/javascript">
	var grid;
	var gridManager = null;
	var userUpdateStr;
	
	$(function() {
		
		loadDict()//加载下拉框
		//加载数据
		loadHead(null);
		loadHotkeys();
		
	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		
    	grid.options.parms.push({name : 'begin_date',value : $("#begin_date").val()}); 
    	grid.options.parms.push({name : 'end_date',value : $("#end_date").val() }); 
    	grid.options.parms.push({name : 'order_code',value : $("#order_code").val()}); 
    	grid.options.parms.push({name : 'sup_id',value : liger.get("sup_code").getValue().split(',')[0]});
    	grid.options.parms.push({name : 'sup_no',value : liger.get("sup_code").getValue().split(',')[1]});
    	
    	grid.options.parms.push({name : 'brif',value : $("#brif").val()});    	
    	grid.options.parms.push({name : 'dept_id',value : liger.get("dept_code").getValue().split(',')[0]}); 
    	grid.options.parms.push({name : 'dept_no',value : liger.get("dept_code").getValue().split(',')[1]});
    	grid.options.parms.push({name : 'stocker',value : liger.get("stocker").getValue().split(',')[0]});
    	
    	grid.options.parms.push({name : 'pur_hos_id',value : liger.get("pur_hos_id").getValue()});
    	grid.options.parms.push({name : 'pay_hos_id',value : liger.get("pay_hos_id").getValue()});
    	grid.options.parms.push({name : 'take_hos_id',value : liger.get("take_hos_id").getValue()});
    	
    	grid.options.parms.push({name : 'state',value : liger.get("state").getValue()});
    	grid.options.parms.push({name : 'inv_code',value : $("#inv_code").val()});
    	grid.options.parms.push({name : 'mat_type_id',value : liger.get("mat_type").getValue().split(',')[0]});
    	grid.options.parms.push({name : 'mat_type_no',value : liger.get("mat_type").getValue().split(',')[1]});
    		
		//加载查询条件
		grid.loadData(grid.where);
		
	}
	

	function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [
			    {display: '订单编号', name: 'order_code', align: 'left', width: '120',
			    	totalSummary: {
	                    type: 'sum',
	                    render: function (suminf, column, cell) {
	                        return '<div>合计</div>';
	                    }
	                }		
			    }, 
				{display: '日期', name: 'order_date', align: 'left',width:120}, 
				{display: '供应商', name: 'sup_name', align: 'left',width:200}, 
				{display: '采购单位', name: 'pur_hos_name', align: 'left',width:120}, 
				{display: '收货单位', name: 'take_hos_name', align: 'left',width:120},
				{display: '付款单位', name: 'pay_hos_name', align: 'left',width:120}, 
				{display: '科室', name: 'dept_name', align: 'left',width:120}, 
				{display: '采购员', name: 'stocker_name', align: 'left',width:120}, 
				{display: '材料名称', name: 'inv_name', align: 'left',width:'150'}, 
				{display: '单位', name: 'unit_name', align: 'left',width:120}, 
				{display: '数量', name: 'amount', align: 'right',width:120,
					 render:function(rowdata){
						  return formatNumber(rowdata.amount ==null ? 0 : rowdata.amount,2,1);
		             }
				}, 
				{display: '金额', name: 'amount_money', align: 'right',width:120,
					 render:function(rowdata){
						  return formatNumber(rowdata.amount_money ==null ? 0 : rowdata.amount_money,'${p04005 }',1);
		             },totalSummary: {
	                      type: 'sum',
	                      render: function (suminf, column, cell) {
	                         return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum,'${p04005 }',1)+ '</div>';
	                      }
	                 }	
				}
			],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatOrderInfo.do?isCheck=true',
			width: '100%', height: '100%', checkbox: false, rownumbers:true,
			delayLoad: true,//初始化不加载，默认false
			selectRowButtonOnly:true,//heightDiff: -10,
			toolbar: { items: [
				{ text: '查询（<u>Q</u>）', id:'search', click: query, icon:'search' },
				{ line:true } , 	
				{ text: '打印（<u>P</u>）', id:'print', click: print, icon:'print' },
				{ line:true } 
			]}
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
	//键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('P', print);
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
   			title:'订单信息查询',
   			head:[
				{"cell":0,"value":"单位: ${sessionScope.hos_name}","colspan":colspan_num,"br":true},
				{"cell":0,"value":"统计日期: " + $("#begin_date").val() +"至"+ $("#end_date").val(),"colspan":colspan_num,"br":true}
   			],
   			foot:[
				{"cell":0,"value":"主管:","colspan":3,"br":false} ,
				{"cell":3,"value":"复核人:","colspan":colspan_num-5,"br":false},
				{"cell":colspan_num-2,"value":"制单人： ${sessionScope.user_name}","colspan":2,"br":true},
				{"cell":0,"value":"打印日期: " + cur_date,"colspan":colspan_num,"br":true}
   			],
   			columns:grid.getColumns(1),
   			headCount:1,//列头行数
   			autoFile:true,
   			type:3
   		};
   		ajaxJsonObjectByUrl("queryMatOrderInfo.do?isCheck=false", selPara, function (responseData) {
   			printGridView(responseData,printPara);
		});

   		
    }
	
	function loadDict() {
		//返回当前年,当前月,当前日期,当前月第一天,当前月最后一天,上个月,上月第一天，上月最后一天
		var date = getCurrentDate();
        var aa = date.split(';');
       
		$("#begin_date").val(aa[3]);
   		$("#end_date").val(aa[4]);
   	
		
		//供应商
		autocomplete("#sup_code","../../queryHosSupDict.do?isCheck=false","id","text",true,true,'',false,'',250);
		//采购部门
		autocomplete("#dept_code","../../queryPurDept.do?isCheck=false","id","text",true,true);
		//采购员
		autocomplete("#stocker","../../queryMatPurStockEmp.do?isCheck=false","id","text",true,true);
		
		//采购单位
		autocomplete("#pur_hos_id","../../../sys/queryHosInfoDictPerm.do?isCheck=false","id","text",true,true,'',false,'',250);
		//收货单位
		autocomplete("#take_hos_id","../../../sys/queryHosInfoDictPerm.do?isCheck=false","id","text",true,true);
		//付款单位
		autocomplete("#pay_hos_id","../../../sys/queryHosInfoDictPerm.do?isCheck=false","id","text",true,true);
		//状态
		autoCompleteByData("#state", matOrderMain_state.Rows, "id", "text", true, true); 
		//物资类别
		autocomplete("#mat_type","../../queryMatTypeDictDate.do?isCheck=false","id","text",true,true,{read_or_write:'1'});
		
		
   		$("#begin_date").ligerTextBox({width:100});
		$("#end_date").ligerTextBox({width:100});
		$("#order_code").ligerTextBox({width:160});
		$("#sup_code").ligerTextBox({width:250});
		
		$("#brif").ligerTextBox({width:250});
		$("#dept_code").ligerTextBox({width:160});
		$("#stocker").ligerTextBox({width:160});
		
        $("#pur_hos_id").ligerTextBox({width:250});
        $("#take_hos_id").ligerTextBox({width:160});
        $("#pay_hos_id").ligerTextBox({width:160});
        
        $("#state").ligerTextBox({width:160});
        $("#inv_code").ligerTextBox({width:160});
        $("#mat_type").ligerTextBox({width:160});

	}
	
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" border="0" width="100%">

		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">订单日期：</td>
			<td align="left" class="l-table-edit-td" style="width: 100px;">
				<input class="Wdate" name="begin_date" id="begin_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
			</td>
			<td align="right" class="l-table-edit-td"  style="width: 10px;">至：</td>
			<td align="left" class="l-table-edit-td" style="width: 100px;">
				<input class="Wdate" name="end_date" id="end_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
			</td>
			

			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">订单编号：</td>
			<td align="left" class="l-table-edit-td">
				<input name="order_code" type="text" requried="false" id="order_code" />
			</td>
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">状态：</td>
			<td align="left" class="l-table-edit-td" >
				<input name="state" type="text" requried="false" id="state" />
			</td>
			

		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">摘要：</td>
			<td align="left" class="l-table-edit-td" colspan="3">
				<input name="brif" type="text" requried="false" id="brif" />
			</td>
			

			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">采购部门：</td>
			<td align="left" class="l-table-edit-td">
				<input name="dept_code" type="text" requried="false" id="dept_code" />
			</td>
		
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">采购员：</td>
			<td align="left" class="l-table-edit-td">
				<input name="stocker" type="text" requried="false" id="stocker" />
			</td>
			
			
		</tr>
		<tr>
			
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">采购单位：</td>
			<td align="left" class="l-table-edit-td" colspan="3">
				<input name="pur_hos_id" type="text" requried="false" id="pur_hos_id" />
			</td>
			
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">收货单位：</td>
			<td align="left" class="l-table-edit-td" >
				<input name="take_hos_id" type="text" requried="false" id="take_hos_id" />
			</td>
			
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">付款单位：</td>
			<td align="left" class="l-table-edit-td" >
				<input name="pay_hos_id" type="text" requried="false" id="pay_hos_id" />
			</td>
		</tr>
		<tr>
			
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">供应商：</td>
			<td align="left" class="l-table-edit-td" colspan="3" >
				<input name="sup_code" type="text" requried="false" id="sup_code" />
			</td>
			
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">材料信息：</td>
			<td align="left" class="l-table-edit-td" >
				<input name="inv_code" type="text" requried="false" id="inv_code" />
			</td>
			
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">物资类别：</td>
			<td align="left" class="l-table-edit-td">
				<input name="mat_type" type="text" requried="false" id="mat_type" />
			</td>
		</tr>
		
	</table>

	<div id="maingrid"></div>
	
</body>
</html>
