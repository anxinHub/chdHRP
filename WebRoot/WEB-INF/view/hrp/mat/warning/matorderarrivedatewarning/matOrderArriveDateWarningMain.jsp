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
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    var state ;
    $(function ()
    {
        loadDict();//加载下拉框
    	//加载数据
    	loadHead(null);	
		loadHotkeys();
		
		$("#near").change(function(){
			if($("#near").prop("checked") == true){
				state = 1 ;
				$('#last').prop('checked',false) ;
				$('#past').prop('checked',false) ;
			}else{
				state = '' ;
			}
			query();
		});
		
		$("#last").change(function(){
			if($("#last").prop("checked") == true){
				state = 2 ;
				$('#near').prop('checked',false) ;
				$('#past').prop('checked',false) ;
			}else{
				state = '' ;
			}
			query();
		});
		
		$("#past").change(function(){
			if($("#past").prop("checked") == true){
				state = 3 ;
				$('#near').prop('checked',false) ;
				$('#last').prop('checked',false) ;
			}else{
				state = '';
			}
			query();
		});
		
    });
    //查询
    function  query(){
    	grid.options.parms=[];
		grid.options.newPage=1;
    	//根据表字段进行添加查询条件
    	grid.options.parms.push({name:'begin_date',value:$("#begin_date").val()});
		grid.options.parms.push({name:'end_date',value:$("#end_date").val()});
		grid.options.parms.push({name:'query_date',value:$("#query_date").val()});
	    grid.options.parms.push({name:'sup_id',value:liger.get("sup_id").getValue().split(",")[0]});
		//grid.options.parms.push({name:'order_code',value:$("#order_code").val()});
		grid.options.parms.push({name:'state',value:state}); 
		//加载查询条件
		grid.loadData(grid.where);
		
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '供应商编码', name: 'sup_code', align: 'left',width:100},
					 { display: '供应商名称', name: 'sup_name', align: 'left',width:200},
					 { display: '订单量', name: 'order_num', align: 'right',width:80,
						 render : function(rowdata, rowindex,value) {
							 if(value!=0){
								 return '<a href=javascript:openAmountUrl("'+ 
									rowdata.sup_id+ ','+
									rowdata.sup_no+  ','+
									1+  ','+
									rowdata.sup_code+ ','+
									rowdata.sup_name+  ','+
									'")>'+ formatNumber(value, 2, 0) + '</a>'; 
							 }	
						 }	 
					 },
					 { display: '已处理订单量', name: 'deal_count', align: 'right',width:80,
						 render : function(rowdata, rowindex,value) {
							 if(value!=0){
								 return '<a href=javascript:openAmountUrl("'+ 
									rowdata.sup_id+ ','+
									rowdata.sup_no+  ','+
									2+  ','+
									rowdata.sup_code+ ','+
									rowdata.sup_name+  ','+
									'")>'+ formatNumber(value, 2, 0) + '</a>'; 
							 }	
						 }
					 },
					 { display: '逾期订单量', name: 'over_tmp', align: 'right',width:80,
						 render : function(rowdata, rowindex,value) {
							 if(value!=0){
								 return '<a href=javascript:openAmountUrl("'+ 
									rowdata.sup_id+ ','+
									rowdata.sup_no+  ','+
									3+  ','+
									rowdata.sup_code+ ','+
									rowdata.sup_name+  ','+
									'")>'+ formatNumber(value, 2, 0) + '</a>'; 
							 }	
						 }	 	 
					 },
					 { display: '未到期订单量', name: 'wdue_tmp', align: 'right',width:80,
						 render : function(rowdata, rowindex,value) {
							 if(value!=0){
								 return '<a href=javascript:openAmountUrl("'+ 
									rowdata.sup_id+ ','+
									rowdata.sup_no+  ','+
									4+  ','+
									rowdata.sup_code+ ','+
									rowdata.sup_name+  ','+
									'")>'+ formatNumber(value, 2, 0) + '</a>'; 
							 }	
						 }	 
					 }],
					dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatOrderArriveDateWarning.do?isCheck=false',
					width: '100%', height: '100%', checkbox: true, rownumbers:true,
					delayLoad : true,//初始化不加载，默认false
					selectRowButtonOnly:true,//heightDiff: -10,
                   	pageSize:200,
                     	toolbar: { items: [
								{ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' },
								{ line:true },
								{ text: '打印（<u>P</u>）', id:'print', click: print,icon:'print' },
								{ line:true }
    					]},
            });
        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function openAmountUrl(obj){
    	var vo = obj.split(",");
		var parm = "sup_id="+vo[0]+"&sup_no="+vo[1]+"&state="+vo[2]+"&sup_code="+vo[3]+"&sup_name="+vo[4]
			+"&begin_date="+$("#begin_date").val()+"&end_date="+$("#end_date").val()+"&query_date="+$("#query_date").val(); 
		parent.$.ligerDialog.open({ 
			title: '订单情况',
			height: $(window).height(),
			width: $(window).width(),
			url : 'hrp/mat/warning/matorderarrivedatewarning/matOrderInfoPage.do?isCheck=false&' + parm, 
			modal:true,showToggle:false,showMax:true ,showMin: true,isResize:true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
    }
    
    function loadDict(){
       	//字典下拉框
       	autocomplete("#sup_id", "../../queryHosSupDict.do?isCheck=false","id", "text", true, true, "", false, false, 300, false, 300);
		//$("#order_code").ligerTextBox({width:200});
		$("#query_date").ligerTextBox({width:120});
		autodate("#query_date");//默认当前日期
		autodate("#begin_date", "yyyy-mm-dd", "month_first");
		autodate("#end_date", "yyyy-mm-dd", "month_last");
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
	   			title:'订单到期预警',
	   			head:[
					{"cell":0,"value":"单位: ${sessionScope.hos_name}","colspan":colspan_num,"br":true},
					/* {"cell":0,"value":"统计日期: " + $("#query_date").val() ,"colspan":colspan_num,"br":true} */
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
	   		ajaxJsonObjectByUrl("queryMatOrderArriveDateWarning.do?isCheck=false", selPara, function (responseData) {
	   			printGridView(responseData,printPara);
			});

	   		
	    }
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" border="0">
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;" width="10%">订单日期：</td>
            <td align="left" class="l-table-edit-td"  width="20%">
				<table>
					<tr>
						<td>
							<input class="Wdate" name="begin_date" id="begin_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
						<td align="right" class="l-table-edit-td"  >
							至
						</td>
						<td>
							<input class="Wdate" name="end_date" id="end_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
            		</tr>
				</table>
	        </td>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;" width="10%">供应商：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="sup_id" type="text" id="sup_id" ltype="text" validate="{required:true,maxlength:20}" />
            </td>
            
            <!-- <td align="right" class="l-table-edit-td"  style="padding-left:20px;" width="10%">订单编号：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="order_code" type="text" id="order_code" ltype="text" type="text" />
            </td> -->
           
        </tr>
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;" width="10%">查询日期：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input class="Wdate" name="query_date" type="text" id="query_date" ltype="text" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" />
            </td>
        	
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;" width="10%">查询内容：</td>
            <td align="left" class="l-table-edit-td" colspan="3" width="20%">
	            <input id="near" type="checkbox" ltype="text" />临近
	            <input id="last" type="checkbox" ltype="text" />到期
	            <input id="past" type="checkbox" ltype="text" />过期
	        </td>
        </tr> 
    </table>
	<div id="maingrid"></div>
</body>
</html>
