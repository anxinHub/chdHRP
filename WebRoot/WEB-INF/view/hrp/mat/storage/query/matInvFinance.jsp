
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
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
		 loadHotkeys();
		 query();
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
		grid.options.parms.push({name : 'set_id',value : liger.get("set_code").getValue()});

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [{ 
		 			display: '物资分类名称', name: 'fim_type_name', align: 'left'
		 		}, { 
		 			display: '期初金额', name: 'begin_money', align: 'right',
		 			render : function(rowdata, rowindex, value) {
						return value ==null ? "" : formatNumber(value,'${p04005 }', 1);
					},
					formatter:'###,##0.00' 
		 		}, 
		 		{ display: '本期增加',
		 		 columns : [{
		 				display: '入库', name: 'in_money', align: 'right',
		 				render : function(rowdata, rowindex, value) {
							return value ==null ? "" : formatNumber(value,'${p04005 }', 1);
						},
						formatter:'###,##0.00'
			 		},
			 		{
		 				display: '盘盈', name: 'py_money', align: 'right',
			 			render : function(rowdata, rowindex, value) {
							return value ==null ? "" : formatNumber(value,'${p04005 }', 1);
						},
						formatter:'###,##0.00'
			 		},
			 		{  
			 			display: '合计金额', name: 'in_amountMoney', align: 'right',
			 			render : function(rowdata, rowindex, value) {
							return value ==null ? "" : formatNumber(value, '${p04005 }', 1);
						},
						formatter:'###,##0.00'
			 		}]
		 		}, 
		 		{ display: '本期减少',
			 		 columns : [{
			 				display: '出库', name: 'out_money', align: 'right',
			 				render : function(rowdata, rowindex, value) {
								return value ==null ? "" : formatNumber(value,'${p04005 }', 1);
							},
							formatter:'###,##0.00'
				 		}, 
				 		{
			 				display: '盘亏', name: 'pk_money', align: 'right',
				 			render : function(rowdata, rowindex, value) {
								return value ==null ? "" : formatNumber(value,'${p04005 }', 1);
							},
							formatter:'###,##0.00'
				 		},
				 		{ 
				 			display: '报损', name: 'bs_money', align: 'right',
				 			render : function(rowdata, rowindex, value) {
								return value ==null ? "" : formatNumber(value, '${p04005 }', 1);
							},
							formatter:'###,##0.00'
				 		},
				 		{ 
				 			display: '合计金额', name: 'out_amountMoney', align: 'right',
				 			render : function(rowdata, rowindex, value) {
								return value ==null ? "" : formatNumber(value, '${p04005 }', 1);
							},
							formatter:'###,##0.00'
				 		}]
			 		}, { 
			 			display: '期末结存', name: 'end_amountMoney', align: 'right',
			 			render : function(rowdata, rowindex, value) {
							return value ==null ? "" : formatNumber(value, '${p04005 }', 1);
						},
						formatter:'###,##0.00'
			 		}],
						dataAction: 'server',dataType: 'server',usePager:false,url:'queryMatFinance.do',
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
    	head=head+"<tr><td>统计年月："+liger.get("begin_year").getText()+liger.get("begin_month").getText()+"至"+liger.get("end_year").getText()+liger.get("end_month").getText()+"</td></tr>";
 		head=head+"</table>";
 		grid.options.lodop.head=head; 
 		grid.options.lodop.fn=renderFunc;
 		grid.options.lodop.title="财务进销存";
    }
    

	//打印
	function print(){
    	
    	if(grid.getData().length==0){
			$.ligerDialog.error("请先查询数据！");
			return;
		}
    	var heads={
        		"isAuto":true,//系统默认，页眉显示页码
        		"rows": [
				  {"cell":0,"value":"单位："},
				  {"cell":1,"value":"${sessionScope.hos_name}","colSpan":2},
    	          {"cell":0,"value":"统计年月：","br":"true"},
    	          {"cell":1,"value":""+$("#begin_year").val()+"年"+$("#begin_month").val()+"至"+$("#end_year").val()+"年"+$("#end_month").val(),"colSpan":2},
    	          {"cell" : 6, "colSpan" : 2},
    	          {"cell":7,"value":"虚仓名称：","align" : "right"},
    	          {"cell":9,"value":""+liger.get("set_code").getText()==''?'空':liger.get("set_code").getText().split(" ")[1]+""}
        	       	
    	    ]}; 
    	//表尾
    	var time = new Date(); //获得当前时间
		var year = time.getFullYear();//获得年、月、日
		var month = time.getMonth()+1;
		var day = time.getDate(); 
		var formDate = year+"年"+month+"月"+day+"日";
		var foots = {
			rows: [
				{"cell":0,"value":"制单人:"} ,
				{"cell":1,"value":""} 
				
			]
		}; 
    	var printPara={
          		title: "财务进销存",//标题
          		columns: JSON.stringify(grid.getPrintColumns()),//表头
          		class_name: "com.chd.hrp.mat.service.storage.query.MatDeptOutReportService",
       			method_name: "queryMatFinancePrint",
       			bean_name: "matDeptOutReportService",
       			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
       			foots: JSON.stringify(foots),//表尾需要打印的查询条件,可以为空 
       			
           	};
        	$.each(grid.options.parms,function(i,obj){
       			printPara[obj.name]=obj.value;
        	});
       		
        	officeGridPrint(printPara);

   		
    }
    function loadDict(){
		//字典下拉框
		var date = getCurrentDate();
        var aa = date.split(';');
        year = aa[0];
        month = aa[1];
		autocomplete("#set_code", "../../queryMatVirStore.do?isCheck=false", "id", "text", true, true);
		
		autocompleteAsync("#begin_year", "../../queryMatYear.do?isCheck=false", "id", "text", true, true, "", false, year, "60");
        autocompleteAsync("#begin_month", "../../queryMatMonth.do?isCheck=false", "id", "text", true, true, "", false, month, "50");
		autocompleteAsync("#end_year", "../../queryMatYear.do?isCheck=false", "id", "text", true, true, "", false, year, "60");
		autocompleteAsync("#end_month", "../../queryMatMonth.do?isCheck=false", "id", "text", true, true, "", false, month, "50");
    
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
     
			<td align="right" class="l-table-edit-td"  width="10%">
				虚仓名称：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="set_code" type="text" id="set_code" ltype="text"/>
            </td>
        </tr> 
    </table>
	<div id="maingrid"></div>
</body>
</html>
