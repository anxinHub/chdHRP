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
    
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
		loadHotkeys();
    });
    //查询
    function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
		var begin_date = $("#begin_date").val();
        var end_date  = $("#end_date").val();
        
        if(begin_date == ''){
        	$.ligerDialog.warn('开始期间不能为空 ');
        	return ;
        }
        
        if(end_date == ''){
        	$.ligerDialog.warn('结束期间不能为空 ');
        	return ;
        }
        //根据表字段进行添加查询条件
		grid.options.parms.push({name : 'begin_date',value : begin_date});
		grid.options.parms.push({name : 'end_date',value : end_date});
		grid.options.parms.push({name : 'in_no',value:$("#in_no").val()});//发票号
		grid.options.parms.push({name : 'sup_id',value : liger.get("sup_code").getValue() == null ? "" : liger.get("sup_code").getValue().split(",")[0]});
		
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [
				{ 
					display: '', name: 'in_id', align: 'left', width: '100',hide:true
				},{
					display: '发票号', name: 'in_no', align: 'left', width: '150',
					render : function(rowdata,rowindex, value) {
						return '<a href=javascript:openDetail("'
						+ rowdata.group_id + ','
						+ rowdata.hos_id + ','
						+ rowdata.copy_code + ','
						+ rowdata.in_id + '")>'
						+ rowdata.in_no + '</a>';
					}
				}, { 
		 			display: '金额', name: 'money', align: 'left', width: '100'
		 		}, { 
		 			display: '供应商名称', name: 'sup_name', align: 'left', width: '300'
		 		}, { 
		 			display: '入库日期', name: 'in_date', align: 'left',width:'100'
		 		}],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatInSupBillSum.do',
			width: '100%', height: '100%',rownumbers:true,
			delayLoad: true,//初始化不加载，默认false
			selectRowButtonOnly:true,//heightDiff: -10,
			toolbar: { items: [
				{ text: '查询（<u>Q</u>）', id:'search', click: query, icon:'search' },
				{ line:true },
				{ text:'打印', id:'print', click: print,icon:'print'},
				{ line:true}

			]}
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function openDetail(obj){
    	var vo = obj.split(",");
		var paras = "group_id=" + vo[0] + "&" + "hos_id=" + vo[1] + "&"
				+ "copy_code=" + vo[2] + "&" + "in_id=" + vo[3] + "&"
				+ "in_no=" + vo[4];
		parent.$.ligerDialog.open({
			title : '入库单查看',
			height : $(window).height(),
			width : $(window).width(),
			url : 'hrp/mat/storage/query/matInvCheckDetailByIdPage.do?isCheck=false&'+ paras.toString(),
			modal : true,
			showToggle : false,
			showMax : true,
			showMin : false,
			isResize : true,
			parentframename : window.name, //用于parent弹出层调用本页面的方法或变量
		});
    }
    
    //打印回调方法
    function lodopPrint(){
    	var head="<table class='head' width='100%'><tr><td>单位：${sessionScope.hos_name}</td></tr>";
 		head=head+"<tr><td>入库日期："+$("#begin_date").val() +" 至  "+ $("#end_date").val()+"</td></tr>";
 		head=head+"</table>";
 		grid.options.lodop.head=head; 
 		grid.options.lodop.fn=renderFunc;
 		grid.options.lodop.title="供应商入库发票汇总表";
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
    	
    	var heads={
        		"isAuto":true,//系统默认，页眉显示页码
        		"rows": [
    	          {"cell":0,"value":"统计年月："},
    	          {"cell":1,"value":""+$("#begin_date").val()+"至"+$("#end_date").val()}
        	]}; 
    	//表尾
		var foots = {
			rows: [
				{"cell":0,"value":"制表日期:"} ,
				{"cell":1,"value":date} ,
				{"cell":0,"value":'分管院领导：',"br":"true"} ,
				{"cell":3,"value":"部门主管:"},
				{"cell":11,"value":"会计:"}
			]
		}; 
    	var printPara={
          		title: "供应商入库发票汇总表",//标题
          		columns: JSON.stringify(grid.getPrintColumns()),//表头
          		class_name: "com.chd.hrp.mat.service.storage.query.MatInSupBillSumService",
       			method_name: "queryMatInSupBillSumPrint",
       			bean_name: "matInSupBillSumService",
       			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
       			foots: JSON.stringify(foots),//表尾需要打印的查询条件,可以为空 
           	};
        	$.each(grid.options.parms,function(i,obj){
       			printPara[obj.name]=obj.value;
        	});
       		
        	officeGridPrint(printPara);

   		
    }
   
    function loadDict(){
    	$("#begin_date").ligerTextBox({width:90});
        autodate("#begin_date", "yyyy-mm-dd", "month_first");
        $("#end_date").ligerTextBox({width:90});
        autodate("#end_date", "yyyy-mm-dd", "month_last");
        $("#in_no").ligerTextBox({width:200});
		//字典下拉框
		autocomplete("#sup_code", "../../queryHosSupDict.do?isCheck=false", "id", "text", true, true, '',false);
		$("#sup_code").ligerTextBox({width:200});
	}  
	
	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar" ></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
		<tr>
        	<td align="right" class="l-table-edit-td"  width="10%">
            	入库日期：
            </td>
            <td align="left" class="l-table-edit-td"  width="20%">
				<table>
					<tr>
						<td>
							<input class="Wdate" name="begin_date" id="begin_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
						<td align="right" class="l-table-edit-td"  > 至 </td>
						<td>
							<input class="Wdate" name="end_date" id="end_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
            		</tr>
				</table>
	        </td>
	        <td align="right" class="l-table-edit-td"  style="padding-left:20px;">发票号：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="in_no" type="text" id="in_no" ltype="text" type="text" />
            </td>
	        <td align="right" class="l-table-edit-td"  width="10%">供应商：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="sup_code" type="text" id="sup_code" ltype="text" validate="{required:false}" />
            </td>
        </tr> 
	</table>
	<div id="maingrid"></div>
</body>
</html>
