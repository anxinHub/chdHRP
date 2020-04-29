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
    var grid;
    var gridManager = null;
    var userUpdateStr;
    
    $(function (){
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);
    });
    
    //查询
    function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
		
		if($("#begin_year").val() == ''){
			$.ligerDialog.warn('预算年度不允许为空!');
			return ;
		}
		
		grid.options.parms.push({name : 'begin_year',value : $("#begin_year").val()});
		grid.options.parms.push({name : 'begin_month',value : $("#begin_month").val()});
		grid.options.parms.push({name : 'mat_type_id',value : liger.get("mat_type_id").getValue() == '' ? '' : liger.get("mat_type_id").getValue().split(",")[0]});
		grid.options.parms.push({name : 'mat_type_code',value : liger.get("mat_type_id").getText() == '' ? '' : liger.get("mat_type_id").getText().split(" ")[0]});

    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [
				{display : '预算年度', name : "year", width : '20%', align : 'left'},
				{display : '月份', name : "month", width : '20%', align : 'left'},
				{display : '物资分类', name : "mat_type_name", width : '20%', align : 'left'},
				{display : '采购预算', name : "pur_budg", width : '20%', align : 'right', 
					render : function(rowdata, rowindex, value) {
						 return formatNumber(rowdata.pur_budg,'2',1);
					},
					formatter:'###,##0.00' 
				},
				{display : '预算执行值(采购入库)', name : "amount_money", width : '20%', align : 'right', 
					render : function(rowdata, rowindex, value) {
						 return formatNumber(rowdata.amount_money,'2',1);
					},
					formatter:'###,##0.00' }
			],
			dataAction: 'server', dataType: 'server', usePager:false,
			url:'queryReducedBudg.do', width: '100%', height: '100%',
			rownumbers:true,
			delayLoad: true,//初始化不加载，默认false  
			selectRowButtonOnly:true,
			toolbar: { items: [
	  			 { text: '查询（<u>Q</u>）', id:'search', click: query, icon:'search' },
				 { line:true },
			     { text: '打印', id:'print', click: print, icon:'print' },
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
    
	function loadDict(){
		//字典下拉框
		month = month < 10 ? '0' + month : month;
		autocompleteAsync("#begin_year", "../../queryMatYear.do?isCheck=false", "id", "text", true, true, "", false, year, "160");
        autocompleteAsync("#begin_month", "../../queryMatMonth.do?isCheck=false", "id", "text", true, true, "", false, month, "160");
		autocompleteAsync("#mat_type_id", "../../queryMatTypeDictDate.do?isCheck=false", "id", "text", true, true);
	}  
	
  	//打印
	function print(){
    	if(grid.getData().length==0){
			$.ligerDialog.error("请先查询数据！");
			return;
		}
    	
    	var heads={ }; 
    	//表尾
		var foots = { }; 
    	var printPara={
          	title: ""+$("#begin_year").val()+""+$("#begin_month").val()+"预算降本报表",//标题
          	columns: JSON.stringify(grid.getPrintColumns()),//表头
          	class_name: "com.chd.hrp.mat.serviceImpl.account.report.MatAccountReportReducedBudgServiceImpl",
       		method_name: "queryReducedBudgPrint",
       		bean_name: "matAccountReportReducedBudgService",
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

<body style="padding: 0px; overflow: hidden;" >
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" >
        <tr>
            <td align="right" class="l-table-edit-td" > <font color="red">*</font>年度： </td>
            <td align="left" class="l-table-edit-td">
				<input name="begin_year" id="begin_year" type="text" required="true" validate="{required:true}" />
			</td>
			<td align="right" class="l-table-edit-td" > 月份： </td>
			<td align="left" class="l-table-edit-td">
				<input name="begin_month" id="begin_month" type="text" required="true" validate="{required:true}" />
			</td>
            <td align="right" class="l-table-edit-td"  width="10%">物资类别：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="mat_type_id" type="text" id="mat_type_id" ltype="text"/>
            </td>
        </tr>
    </table>
    
	<div id="maingrid"></div>
</body>
</html>
