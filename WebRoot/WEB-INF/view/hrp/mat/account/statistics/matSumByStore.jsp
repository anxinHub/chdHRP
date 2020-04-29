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
	var time = new Date(); //获得当前时间
	var year = time.getFullYear();//获得年、月、日
	var month = time.getMonth()+1;
	var day = time.getDate(); 
	var date = year+"年"+month+"月"+day+"日";
    var grid;
    var gridManager = null;
    var userUpdateStr;
    var renderFunc = {
			 
    		begin_money:function(value){//期初金额
				return formatNumber(value, '${p04005}', 1);
			},
			in_money:function(value){//本期收入
   			 return formatNumber(value, '${p04005}', 1); 
   		 
			},
			out_money:function(value){//本期发出
				return formatNumber(value, '${p04005}', 1);
			},
			end_money:function(value){//期末余额
				return formatNumber(value, '${p04005}', 1);
			},
			
		 
	};
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
		 //loadHotkeys();
		// query();
    });
    //查询
    function  query(){
    	$("#year_month_span").text(liger.get("year").getValue()+"年"+liger.get("month").getValue()+"月");
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
			name : 'type_code',
			value : liger.get("type_code").getValue() == null ? "" : liger.get("type_code").getValue()
		});

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [
			    /* {
					display: '仓库名称', name: 'store_id', textField: 'store_name', align: 'left', minWidth: '150',
				} */
				{
					display: '仓库名称', name: 'store_name', align: 'left', minWidth: '150',
				}
				, { 
		 			display: '期初余额', name: 'begin_money', align: 'right', minWidth: '100',formatter:"###,##0.00",
		 			render : function(rowdata, rowindex, value) {
						return value ==null ? "" : formatNumber(value, '${p04005}', 1);
					}
		 		}, { 
		 			display: '本期收入', name: 'in_money', align: 'right', minWidth: '100',formatter:"###,##0.00",
		 			render : function(rowdata, rowindex, value) {
						return value ==null ? "" : formatNumber(value, '${p04005}', 1);
					}
		 		}, { 
		 			display: '本期发出', name: 'out_money', align: 'right', minWidth: '100',formatter:"###,##0.00",
		 			render : function(rowdata, rowindex, value) {
						return value ==null ? "" : formatNumber(value, '${p04005}', 1);
					}
		 		}, { 
		 			display: '期末余额', name: 'end_money', align: 'right', minWidth: '100',formatter:"###,##0.00",
		 			render : function(rowdata, rowindex, value) {
						return value ==null ? "" : formatNumber(value, '${p04005}', 1);
					}
		 		}],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatAccountStatisticsSumByStore.do',
			width: '100%', height: '100%', checkbox: false,rownumbers:true,
			delayLoad: true,//初始化不加载，默认false
			selectRowButtonOnly:true//heightDiff: -10,
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
		autocomplete("#year", "../../queryMatYear.do?isCheck=false", "id", "text", true, true, "", false, year, "60");
        autocomplete("#month", "../../queryMatMonth.do?isCheck=false", "id", "text", true, true, "", false, month, "50");
        autocomplete("#type_code", "../../queryMatStoreType.do?isCheck=false", "id", "text", true, true);
        
        //格式化按钮
		$("#query").ligerButton({click: query, width:70});
		$("#print").ligerButton({click: print, width:70});
	}
    
	 //打印回调方法
    function lodopPrint(){
    	var head="<table class='head' width='100%'><tr><td>单位：${hos_name}</td></tr>";
    	head=head+"<tr><td>统计年月："+liger.get("year").getValue()+"年"+liger.get("month").getValue()+"月"+"</td></tr>";
 		head=head+"</table>";
 		grid.options.lodop.head=head; 
 		grid.options.lodop.fn=renderFunc;
 		grid.options.lodop.title=liger.get("year").getValue()+"年"+liger.get("month").getValue()+"月"+"收发结存汇总表(仓库)";
    }
	 
  	//打印
	function print(){
    	
    	if(grid.getData().length==0){
    		
			$.ligerDialog.error("请先查询数据！");
			
			return;
		}
    	if(liger.get("type_code").getValue()== ""){ 
    		var heads={
            		"isAuto":true,//系统默认，页眉显示页码
            		"rows": [
        	          {"cell":0,"value":"统计年月："},
        	          {"cell":1,"value":date}//  {"cell":1,"value":""+$("#year").val()+"年"+$("#month").val()},
        	          ]
    		}
    	
    	}else{
    		
    		var heads={
            		"isAuto":true,//系统默认，页眉显示页码
            		"rows": [
        	          {"cell":0,"value":"统计年月："},
        	          {"cell":1,"value":liger.get("year").getValue()+"年"+liger.get("month").getValue()+"月"} ,//  {"cell":1,"value":""+$("#year").val()+"年"+$("#month").val()},
        	          /* {"cell":3,"value":"仓库："},
        	          {"cell":4,"value":""+liger.get("type_code").getText()==''?' ':liger.get("type_code").getText().split(" ")[1]+""} */
            	]}; 
        	}
    	//表尾
		var foots = {
			rows: [
				{"cell":0,"value":"打印日期:"} ,
				{"cell":1,"value":date} ,
				{"cell":3,"value":"打印人:"} ,
				{"cell":4,"value":"${user_name}"} ,
			]
		}; 
    	var printPara={
          		title: "收发结存汇总表（仓库）",//标题
          		columns: JSON.stringify(grid.getPrintColumns()),//表头
          		class_name: "com.chd.hrp.mat.service.account.statistics.MatAccountStatisticsSumByStoreService",
       			method_name: "queryMatAccountStatisticsSumByStorePrint",
       			bean_name: "matAccountStatisticsSumByStoreService",
       			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
       			foots: JSON.stringify(foots),//表尾需要打印的查询条件,可以为空 
           	};
        	$.each(grid.options.parms,function(i,obj){
       			printPara[obj.name]=obj.value;
        	});
       		
        	officeGridPrint(printPara);
   		
   		
    }
	
	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" >	</div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
        <tr>
            <td align="right" class="l-table-edit-td"  width="100px">
	            统计年月：
			</td>
			<td align="left" class="l-table-edit-td"  width="200px">
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
						<td align="right" class="l-table-edit-td"  >
							月
						</td>
	            	</tr>
				</table>
			</td>
			
			<td align="right" class="l-table-edit-td">仓库分类:</td>
			<td align="left" class="l-table-edit-td">
				<input name="type_code" type="text" id="type_code" ltype="text" />
			</td>
			<td align="right" class="l-table-edit-td">
				<button id ="query" accessKey="Q"><b>查询（<u>Q</u>）</b></button>
				&nbsp;&nbsp;
				<button id ="print" accessKey="P"><b>打印（<u>P</u>）</b></button>
				&nbsp;&nbsp;
			</td>
        </tr> 
    </table>
    <div align="center">
    	<h2>
	    	<span id="year_month_span"></span>
	    	收发结存汇总表（仓库）
    	</h2>
    </div>
	<div id="maingrid"></div>
</body>
</html>
