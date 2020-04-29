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
			 
    		in_money:function(value){//汇总金额
				return formatNumber(value, '${p08005 }', 1);
			},
			
			back_money:function(value){//汇总金额
				return formatNumber(value, '${p08005 }', 1);
			},
			
			sum_money:function(value){//汇总金额
				return formatNumber(value, '${p08005 }', 1);
			}
		 
	};
    
    
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
		loadHotkeys();
		$("#set_code").bind("change",function(){
	    	if(liger.get("set_code").getValue()){
	    		liger.get("store_code").setValue("");
				liger.get("store_code").setText("");
	   	 		$("#store_code").ligerComboBox({disabled:true});
	   	 		grid.toggleCol('02', true);
	    	}else{
	    		$("#store_code").ligerComboBox({disabled:false});
	    		grid.toggleCol('02', false);
	    	}
	    	
		});
		$("#store_code").bind("change",function(){
	    	if(liger.get("store_code").getValue()){
	    		liger.get("set_code").setValue("");
				liger.get("set_code").setText("");
	   	 		$("#set_code").ligerComboBox({disabled:true});
	   	 		grid.toggleCol('03', true);
	    	}else{
	    		$("#set_code").ligerComboBox({disabled:false});
	    		grid.toggleCol('03', false);
	    	}
	    	
		});
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
		grid.options.parms.push({name : 'set_id',value : liger.get("set_code").getValue() == null ? "" : liger.get("set_code").getValue()});
		grid.options.parms.push({name : 'store_id',value : liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[0]});
		grid.options.parms.push({name : 'sup_id',value : liger.get("sup_code").getValue() == null ? "" : liger.get("sup_code").getValue().split(",")[0]});
		
		var in_bus_type = "";
		if(liger.get("in_bus_type").getValue() == null ||liger.get("in_bus_type").getValue() == 0){
			in_bus_type="(2,27)";
		}else{
			in_bus_type="("+liger.get("in_bus_type").getValue().replace(/;/g,',')+")";
		}
		var back_bus_type="";
		if(liger.get("back_bus_type").getValue() == null ||liger.get("back_bus_type").getValue() == 0){
			back_bus_type="(12,29)";
		}else{
			back_bus_type="("+liger.get("back_bus_type").getValue().replace(/;/g,',')+")";
		}
		
		grid.options.parms.push({
			name : 'in_bus_type',
			value : in_bus_type
		}); 
		grid.options.parms.push({
			name : 'back_bus_type',
			value : back_bus_type
		}); 
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [
			    {
					display: '虚仓名称', name: 'set_name', align: 'left', width: '150',id:'02'
				}, {
					display: '仓库名称', name: 'store_name', align: 'left', width: '150',id:'03'
			    }, { 
		 			display: '供应商编码', name: 'sup_code', align: 'left', width: '100'
		 		}, { 
		 			display: '供应商名称', name: 'sup_name', align: 'left', width: '300'
		 		}, { 
		 			display: '入库金额', name: 'in_money', align: 'right', width: '150',
		 			render : function(rowdata, rowindex, value) {
						return formatNumber(rowdata.in_money ==null ? 0 : rowdata.in_money, '${p08005 }', 1);
					}
		 		}, { 
		 			display: '退货金额', name: 'back_money', align: 'right',  width: '150',
		 			render : function(rowdata, rowindex, value) {
						return formatNumber(rowdata.back_money ==null ? 0 : rowdata.back_money, '${p08005 }', 1);
					}
		 		}, { 
		 			display: '合计', name: 'sum_money', align: 'right', width: '150',
		 			render : function(rowdata, rowindex, value) {
						return formatNumber(rowdata.sum_money ==null ? 0 : rowdata.sum_money, '${p08005 }', 1);
					}
		 			
		 		}],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMedInSupBusTypeSum.do',
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
    
    //打印回调方法
    function lodopPrint(){
    	var head="<table class='head' width='100%'><tr><td>单位：${sessionScope.hos_name}</td></tr>";
 		head=head+"<tr><td>入库日期："+$("#begin_date").val() +" 至  "+ $("#end_date").val()+"</td></tr>";
 		head=head+"</table>";
 		grid.options.lodop.head=head; 
 		grid.options.lodop.fn=renderFunc;
 		grid.options.lodop.title="供应商入库汇总表";
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
   			title:'供应商入库汇总表',
   			head:[
				{"cell":0,"value":"单位: ${sessionScope.hos_name}","colspan":colspan_num,"br":true},
				{"cell":0,"value":"统计日期: " + $("#begin_date").val() +" 至  "+ $("#end_date").val(),"colspan":colspan_num,"br":true}
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
   		ajaxJsonObjectByUrl("queryMedInSupBusTypeSum.do?isCheck=false", selPara, function (responseData) {
   			printGridView(responseData,printPara);
		});

   		
    }
   
    function loadDict(){
    	$("#begin_date").ligerTextBox({width:90});
        autodate("#begin_date", "yyyy-mm-dd", "month_first");
        $("#end_date").ligerTextBox({width:90});
        autodate("#end_date", "yyyy-mm-dd", "month_last");
        
		//字典下拉框
		//autocomplete("#store_code", "../../queryMedStoreByWrite.do?isCheck=false", "id", "text", true, true, '',true);
		autocomplete("#store_code", "../../queryMedStoreByRead.do?isCheck=false", "id", "text", true, true, '',true);
		autocomplete("#set_code", "../../queryMedVirStore.do?isCheck=false", "id", "text", true, true, '',false);
		autocomplete("#sup_code", "../../queryHosSupDict.do?isCheck=false", "id", "text", true, true, '',false);
		autocompleteAsyncMulti("#in_bus_type", "../../queryMedBusType.do?isCheck=false", "id", "text", true, true, {codes : '2,27'}, false);
		autocompleteAsyncMulti("#back_bus_type", "../../queryMedBusType.do?isCheck=false", "id", "text", true, true, {codes : '12,29'}, false);
		$("#store_code").ligerTextBox({width:180});
		$("#set_code").ligerTextBox({width:180});
		$("#sup_code").ligerTextBox({width:200});
		$("#in_bus_type").ligerTextBox({width:180});
		$("#back_bus_type").ligerTextBox({width:180});
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
            
            <td align="right" class="l-table-edit-td" width="10%">虚&nbsp;&nbsp;仓：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="set_code" type="text" id="set_code" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
            
            <td align="right" class="l-table-edit-td" width="10%">仓&nbsp;&nbsp;库：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="store_code" type="text" id="store_code" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
        </tr> 
        
        <tr>
        	<td align="right" class="l-table-edit-td"  width="10%">供应商：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="sup_code" type="text" id="sup_code" ltype="text" validate="{required:false}" />
            </td>
        	
	        <td align="right" class="l-table-edit-td"  width="10%">入库业务类型：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="in_bus_type" type="text" id="in_bus_type" ltype="text" validate="{required:false}" />
            </td>
            
	        <td align="right" class="l-table-edit-td"  width="10%">退货业务类型：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="back_bus_type" type="text" id="back_bus_type" ltype="text" validate="{required:false}" />
            </td>
            
        </tr>
        
	</table>
	<div id="maingrid"></div>
</body>
</html>
