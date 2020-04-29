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
	var date = year+"年"+month+"月"+day;
    var grid;
    var gridManager = null;
    var userUpdateStr;
    var renderFunc = {
			 
			in_money:function(value){//本期收入
   			 return formatNumber(value, '${p04005}', 1); 
   		 
			},
			out_money:function(value){//本期发出
				return formatNumber(value, '${p04005}', 1);
			} 
			
		 
	};
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
		// query();
		$("#mat_type_code").bind("change",function(){
	    	if(liger.get("mat_type_code").getValue()){
	    		var para = {
	    				mat_type_id : liger.get("mat_type_code").getValue()
	    		}
	    		liger.get("inv_code").set('parms', para);
	    		liger.get("inv_code").reload();
	    	}
		})
    });
    //查询
    function  query(){
    	$("#year_month_span").text(liger.get("begin_year").getValue()+"年"+
    			liger.get("begin_month").getValue()+"月至"+
    			liger.get("end_year").getValue()+"年"+
    			liger.get("end_month").getValue()+"月");
    	
		grid.options.parms=[];
		grid.options.newPage=1;
		if(liger.get("store_code").getValue() == ''){
			$.ligerDialog.error('仓库名称不能为空');
			return ; 
		}
        //根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'begin_year',
			value : liger.get("begin_year").getValue() == null ? "" : liger.get("begin_year").getValue()
		});
		grid.options.parms.push({
			name : 'begin_month',
			value : liger.get("begin_month").getValue() == null ? "" : liger.get("begin_month").getValue()
		}); 
		grid.options.parms.push({
			name : 'end_year',
			value : liger.get("end_year").getValue() == null ? "" : liger.get("end_year").getValue()
		});
		grid.options.parms.push({
			name : 'end_month',
			value : liger.get("begin_month").getValue() == null ? "" : liger.get("begin_month").getValue()
		}); 
		grid.options.parms.push({
			name : 'store_id',
			value : liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[0]
		}); 
	/* 	grid.options.parms.push({
			name : 'mat_type_id',
			value : liger.get("mat_type_code").getValue() == null ? "" : liger.get("mat_type_code").getValue()
		}); */ 
		grid.options.parms.push({name : 'mat_type_code',value : liger.get("mat_type_code").getText().split(" ")[0]});
		grid.options.parms.push({
			name : 'inv_id',
			value : liger.get("inv_code").getValue() == null ? "" : liger.get("inv_code").getValue()
		}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [{
					display: '业务类型', name: 'bus_type_name', align: 'left', minWidth: '150',
				}, { 
		 			display: '本期收入', name: 'in_money', align: 'right', minWidth: '100',
		 			render : function(rowdata, rowindex, value) {
						return value ==null ? "" : formatNumber(value, '${p04005}', 1);
					},formatter:'###,##0.00'
		 		}, { 
		 			display: '本期发出', name: 'out_money', align: 'right', minWidth: '100',
		 			render : function(rowdata, rowindex, value) {
						return value ==null ? "" : formatNumber(value, '${p04005}', 1);
					},formatter:'###,##0.00'
		 		}],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatAccountStatisticsSumByBus.do',
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
		autocomplete("#begin_year", "../../queryMatYear.do?isCheck=false", "id", "text", true, true, "", false, year, "60");
        autocomplete("#begin_month", "../../queryMatMonth.do?isCheck=false", "id", "text", true, true, "", false, month, "50");
		autocomplete("#end_year", "../../queryMatYear.do?isCheck=false", "id", "text", true, true, "", false, year, "60");
        autocomplete("#end_month", "../../queryMatMonth.do?isCheck=false", "id", "text", true, true, "", false, month, "50");
//        autocomplete("#store_code", "../../queryMatStore.do?isCheck=false", "id", "text", true, true, "", true);
        autocomplete("#store_code", "../../queryMatStoreDictDate.do?isCheck=false", "id", "text", true, true, {read_or_write : 1}, true);
//        autocomplete("#mat_type_code", "../../queryMatType.do?isCheck=false", "id", "text", true, true);
        autocomplete("#mat_type_code", "../../queryMatTypeDictDate.do?isCheck=false", "id", "text", true, true,{read_or_write : 1});
       // autocomplete("#inv_code", "../../queryMatInv.do?isCheck=false", "id", "text", true, true, "", false, false, "", "", "250");
       $("#inv_code").ligerTextBox({width:220});
        //格式化按钮
		$("#query").ligerButton({click: query, width:70});
		$("#print").ligerButton({click: print, width:70});
	}
    
	 //打印回调方法
    function lodopPrint(){
    	var head="<table class='head' width='100%'><tr><td>单位：${hos_name}</td></tr>";
    	head=head+"<tr><td>统计年月："+liger.get("begin_year").getText()+liger.get("begin_month").getText()+"至"+liger.get("end_year").getText()+liger.get("end_month").getText()+"</td></tr>";
 		head=head+"</table>";
 		grid.options.lodop.head=head; 
 		grid.options.lodop.fn=renderFunc;
 		grid.options.lodop.title=liger.get("begin_year").getText()+liger.get("begin_month").getText()+"至"+liger.get("end_year").getText()+liger.get("end_month").getText()+"收发结存汇总表(业务类型)";
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
    	          {"cell":0,"value":"统计年月："},
    	          {"cell":1,"value":""+$("#begin_year").val()+"年"+$("#begin_month").val()+"至"+$("#end_year").val()+"年"+$("#end_month").val()},
    	          {"cell":3,"value":"仓库："},
    	          {"cell":4,"value":""+liger.get("store_code").getText()==''?' ':liger.get("store_code").getText().split(" ")[1]+""}
        	]}; 
    	//表尾
		var foots = {
			rows: [
				{"cell":0,"value":"制单日期:"} ,
				{"cell":1,"value":date} ,
			]
		}; 
    	var printPara={
          		title: ""+liger.get("store_code").getText().split(" ")[1]+"收发结存汇总表",//标题
          		columns: JSON.stringify(grid.getPrintColumns()),//表头
          		class_name: "com.chd.hrp.mat.service.account.statistics.MatAccountStatisticsSumByBusService",
       			method_name: "queryMatAccountStatisticsSumByBusPrint",
       			bean_name: "matAccountStatisticsSumByBusService",
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
    <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" >
        <tr>
            <td align="right" class="l-table-edit-td"  width="10%">
	            统计年月：
			</td>
			<td align="left" class="l-table-edit-td"  width="20%">
				<table >
					<tr>
						<td align="left">
							<input name="begin_year" id="begin_year" type="text" required="true" validate="{required:true}" />
						</td>
						<td align="right" class="l-table-edit-td"  >
							年
						</td>
						<td align="left" class="l-table-edit-td">
							<input name="begin_month" id="begin_month" type="text" required="true" validate="{required:true}" />
						</td>
						<td align="right" class="l-table-edit-td"  >
							至
						</td>
						<td align="left">
							<input name="end_year" id="end_year" type="text" required="true" validate="{required:true}" />
						</td>
						<td align="right" class="l-table-edit-td"  >
							年
						</td>
						<td align="left" class="l-table-edit-td">
							<input name="end_month" id="end_month" type="text" required="true" validate="{required:true}" />
						</td>
	            	</tr>
				</table>
			</td>
            <td align="right" class="l-table-edit-td" >
            <font color="red" size="2">*</font>
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
	    	<span id="year_month_span"></span>收发结存汇总表（业务类型）
    	</h2>
    </div>
	<div id="maingrid"></div>
</body>
</html>
