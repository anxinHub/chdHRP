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
	var paraMoney = '${p04005}';
	var paraPrice = '${p04006}';
	
    var renderFunc = {

			price: function(value){//单价
				return formatNumber(value, paraPrice, 1); 
			},
			begin_amount: function(value){//期初数量
				return formatNumber(value, 2, 1); 
			},
			begin_money: function(value){//期初金额
				return formatNumber(value, paraMoney, 1);
			}, 
			in_amount: function(value){//移入数量
	   			 return formatNumber(value, 2, 1); 
			},
			in_money: function(value){//移入金额
				return formatNumber(value, paraMoney, 1);
			}, 
			out_amount: function(value){//移出数量
		   		return formatNumber(value, 2, 1); 
			},
			out_money: function(value){//移出金额
				return formatNumber(value, paraMoney, 1);
			}, 
			end_amount: function(value){//期末数量
   				return formatNumber(value, 2, 1); 
			},
			end_money: function(value){//期末金额
				return formatNumber(value, paraMoney, 1);
			} 
	};
    
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
		// query();
    });
    //查询
    function  query(){
    	$("#year_month_span").text(liger.get("year").getValue()+"年"+
    			liger.get("month").getValue()+"月");
    	
		grid.options.parms=[];
		grid.options.newPage=1;
        //根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'year',
			value : liger.get("year").getValue() == null ? "" : liger.get("year").getValue()
		});
		grid.options.parms.push({
			name : 'month',
			value : liger.get("month").getValue() == null ? "" : liger.get("month").getValue()
		}); 
		grid.options.parms.push({
			name : 'store_id',
			value : liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[0]
		}); 
		grid.options.parms.push({
			name : 'mat_type_id',
			value : liger.get("mat_type_code").getValue() == null ? "" : liger.get("mat_type_code").getValue()
		}); 
		grid.options.parms.push({
			name : 'inv_code',
			value : $("#inv_code").val()
		}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [{
					display: '库房编码', name: 'store_code', align: 'left', width: '80',
				}, { 
					display: '库房名称', name: 'store_name', align: 'left', width: '100',
				}, { 
					display: '材料编码', name: 'inv_code', align: 'left', width: '100',
				}, { 
					display: '材料名称', name: 'inv_name', align: 'left', width: '180',
				}, { 
					display: '规格型号', name: 'inv_model', align: 'left', width: '140',
				}, { 
					display: '计量单位', name: 'unit_name', align: 'left', width: '70',
				}, { 
					/* display: '条形码', name: 'bar_code', align: 'left', width: '90',
				}, {  */
					display: '单价', name: 'price', align: 'left', width: '80',
		 			render : function(rowdata, rowindex, value) {
						return value ==null ? "" : formatNumber(value, paraPrice, 1);
					}
				}, { 
		 			display: '期初数量', name: 'begin_amount', align: 'right', width: '80',
		 			render : function(rowdata, rowindex, value) {
						return value ==null ? "" : formatNumber(value, 2, 1);
					}
		 		}, { 
		 			display: '期初金额', name: 'begin_money', align: 'right', width: '100',
		 			render : function(rowdata, rowindex, value) {
						return value ==null ? "" : formatNumber(value, paraMoney, 1);
					}
		 		}, {
		 			display: '移入数量', name: 'in_amount', align: 'right', width: '80',
		 			render : function(rowdata, rowindex, value) {
						return value ==null ? "" : formatNumber(value, 2, 1);
					}
		 		}, { 
		 			display: '移入金额', name: 'in_money', align: 'right', width: '100',
		 			render : function(rowdata, rowindex, value) {
						return value ==null ? "" : formatNumber(value, paraMoney, 1);
					}
		 		}, {
		 			display: '移出数量', name: 'out_amount', align: 'right', width: '80',
		 			render : function(rowdata, rowindex, value) {
						return value ==null ? "" : formatNumber(value, 2, 1);
					}
		 		}, { 
		 			display: '移出金额', name: 'out_money', align: 'right', width: '100',
		 			render : function(rowdata, rowindex, value) {
						return value ==null ? "" : formatNumber(value, paraMoney, 1);
					}
		 		}, {
		 			display: '结存数量', name: 'end_amount', align: 'right', width: '80',
		 			render : function(rowdata, rowindex, value) {
						return value ==null ? "" : formatNumber(value, 2, 1);
					}
		 		}, { 
		 			display: '结存金额', name: 'end_money', align: 'right', width: '100',
		 			render : function(rowdata, rowindex, value) {
						return value ==null ? "" : formatNumber(value, paraMoney, 1);
					}
		 		}],
			dataAction: 'server', dataType: 'server', usePager: true, url: 'queryMatDuraQueryStoreStock.do',
			width: '100%', height: '97%', checkbox: false, rownumbers: true,
			delayLoad: true,//初始化不加载，默认false
			selectRowButtonOnly: true//heightDiff: -10,
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
   
    function loadDict(){
		//字典下拉框
    	//返回当前年,当前月,
		var date = getCurrentDate();
        var aa = date.split(';');
        year = aa[0];
        month = aa[1];
		//返回当前年,当前月,当前日期,当前月第一天,当前月最后一天,上个月,上月第一天，上月最后一天
		autocompleteAsync("#year", "../../queryMatYear.do?isCheck=false", "id", "text", true, true, "", false, year, "60");
        autocompleteAsync("#month", "../../queryMatMonth.do?isCheck=false", "id", "text", true, true, "", false, month, "50");
        autocompleteAsync("#store_code", "../../queryMatStore.do?isCheck=false", "id", "text", true, true, "", true);
        autocomplete("#mat_type_code", "../../queryMatType.do?isCheck=false", "id", "text", true, true);
 		
		$("#inv_code").ligerTextBox({width:220});
        //格式化按钮
		$("#query").ligerButton({click: query, width:70});
		$("#print").ligerButton({click: print, width:70});
	}
    
	 //打印回调方法
    function lodopPrint(){
    	var head="<table class='head' width='100%'><tr><td>单位：${sessionScope.hos_name}</td></tr>";
    	head=head+"<tr><td>统计年月："+liger.get("year").getText()+liger.get("month").getText()+"</td></tr>";
 		head=head+"</table>";
 		grid.options.lodop.head=head; 
 		grid.options.lodop.fn=renderFunc;
 		grid.options.lodop.title=liger.get("year").getText()+liger.get("month").getText()+"耐用品库房库存查询";
    }
	 
  	//打印
	function print(){
		if (grid.getData().length == 0) {

			$.ligerDialog.error("请先查询数据！");

			return;
		}

		var heads = {
			"isAuto" : true,//系统默认，页眉显示页码
			"rows" : [
					{"cell" : 0,"value" : "统计年月："},
					{"cell" : 1,"value" : ""+ liger.get("year").getValue()+ "年"+ liger.get("month").getValue()+"月"},
					{"cell":3,"value":"仓库："},
		  	        {"cell":4,"value":""+liger.get("store_code").getText()==''?' ':liger.get("store_code").getText().split(" ")[1]+""}
					]
		};
		var printPara = {
			title : "库房领用明细表",//标题
			columns : JSON.stringify(grid.getPrintColumns()),//表头
			class_name : "com.chd.hrp.mat.service.dura.query.MatDuraQueryService",
			method_name : "queryMatDuraQueryStoreStockPrint",
			bean_name : "matDuraQueryService",
			heads : JSON.stringify(heads),//表头需要打印的查询条件,可以为空

		};
		$.each(grid.options.parms, function(i, obj) {
			printPara[obj.name] = obj.value;
		});

		officeGridPrint(printPara);

    	/* if(grid.getData().length==0){
    		
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
   			title:'耐用品库房库存查询',
   			head:[
				{"cell":0,"value":"单位: ${sessionScope.hos_name}","colspan":colspan_num,"br":true},
				{"cell":0,"value":
					"统计日期: " + $("#year").val()+ "年"+ $("#month").val()+ "月" +
					" 至  "+ $("#end_year").val() +"年"+ $("#end_month").val() + "月","colspan":colspan_num,"br":true}
   			],
   			foot:[
				{"cell":0,"value":"主管:","br":false} ,
				{"cell":1,"value":"复核人:","br":false},
				{"cell":2,"value":"制单人： ${sessionScope.user_name}","br":true},
				{"cell":0,"value":"打印日期: " + cur_date,"colspan":colspan_num,"br":true}
   			],
   			columns:grid.getColumns(1),
   			headCount:1,//列头行数
   			autoFile:true,
   			type:3
   		};
   		ajaxJsonObjectByUrl("queryMatDuraQueryStoreStock.do?isCheck=false", selPara, function (responseData) {
   			printGridView(responseData,printPara);
		}); */

   		
    }
	
	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" >	</div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" >
        <tr>
            <td align="right" class="l-table-edit-td"  width="10%">
	            统计年月：
			</td>
			<td align="left" class="l-table-edit-td"  width="20%">
				<table >
					<tr>
						<td align="left">
							<input name="year" id="year" type="text" required="true" validate="{required:true}" />
						</td>
						<td align="right" class="l-table-edit-td"  >
							年
						</td>
						<td align="left" class="l-table-edit-td">
							<input name="month" id="month" type="text" required="true" validate="{required:true}" />
						</td>
	            	</tr>
				</table>
			</td>
            <td align="right" class="l-table-edit-td" >
	            仓库名称：
			</td>
			<td align="left" class="l-table-edit-td" >
				<input name="store_code" id="store_code" type="text" required="true" validate="{required:true}" />
			</td>
            <td align="right" class="l-table-edit-td"  >
	            物资类别：
			</td>
			<td align="left" class="l-table-edit-td"  >
				<input name="mat_type_code" id="mat_type_code" type="text" required="true" validate="{required:true}" />
			</td>
		</tr>
		<tr>
            <td align="right" class="l-table-edit-td"  >
	            材料名称：
			</td>
			<td align="left" class="l-table-edit-td"  >
				<input name="inv_code" id="inv_code" type="text" required="true" validate="{required:true}" />
			</td>
			<td colspan="4" align="right" class="l-table-edit-td">
				<button id ="query" accessKey="Q"><b>查询（<u>Q</u>）</b></button>
				&nbsp;&nbsp;
				<button id ="print" accessKey="P"><b>打印（<u>P</u>）</b></button>
				&nbsp;&nbsp;
			</td>
        </tr> 
    </table>
    <div align="center">
    	<h2>
	    	<span id="year_month_span"></span>耐用品库房库存查询
    	</h2>
    </div>
	<div id="maingrid"></div>
</body>
</html>
