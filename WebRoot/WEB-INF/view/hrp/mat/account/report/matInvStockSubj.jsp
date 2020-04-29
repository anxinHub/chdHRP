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
<script src="<%=path%>/lib/stringbuffer.js"></script>
<script type="text/javascript">
	var time = new Date(); //获得当前时间
	var year = time.getFullYear();//获得年、月、日
	var month = time.getMonth()+1;
	var day = time.getDate(); 
	var date = year+"年"+month+"月"+day +"日";
    var grid;
    var gridManager = null;
	var renderFunc = {
			begin_money:function(value){//本期增加
				return formatNumber(value ==null ? 0 : value, '${p04005}', 1);
			},
			bal_money:function(value){//进销误差
				return formatNumber(value ==null ? 0 : value, '${p04005}', 1);
			},
			end_money:function(value){//期末结存
				return formatNumber(value ==null ? 0 : value, '${p04005}', 1);
			},
			add_sum_money:function(value){//合计金额
				return formatNumber(value ==null ? 0 : value, '${p04005}', 1);
			},
			reduct_sum_money:function(value){//合计金额
				return formatNumber(value ==null ? 0 : value, '${p04005}', 1);
			}
			  
	};   
    $(function ()
    {
        loadDict()//加载下拉框
       
    	//加载数据
    	loadHead(null);	
		 loadHotkeys();
		 //query();
		$("#store_type_td").find(":radio").click(function(){
			var store_type = $("#store_type_td").find(":radio:checked").val();
			if(store_type == 1){
				$("#store_label").text("虚仓");
				autocompleteAsync("#store_code", "../../queryMatVirStore.do?isCheck=false", "id", "text", true, true, "", true);
			}else{
				$("#store_label").text("仓库");
/* 				autocompleteAsync("#store_code", "../../queryMatStoreByRead.do?isCheck=false", "id", "text", true, true, "", true); */
				autocompleteAsync("#store_code", "../../queryMatStoreDictDate.do?isCheck=false", "id", "text", true, true, {read_or_write:1}, true);
			}
		});
    });
    
    //查询
    function  query(){
    	
		grid.options.parms=[];
		grid.options.newPage=1;
		 
        //根据表字段进行添加查询条件
        if(!liger.get("begin_year").getValue()){
        	$.ligerDialog.warn("请选择开始年份！");
        	return false;
        }
        if(!liger.get("begin_month").getValue()){
        	$.ligerDialog.warn("请选择开始月份！");
        	return false;
        }
        if(!liger.get("end_year").getValue()){
        	$.ligerDialog.warn("请选择结束年份！");
        	return false;
        }
        if(!liger.get("end_month").getValue()){
        	$.ligerDialog.warn("请选择结束月份！");
        	return false;
        }
        if(!liger.get("store_code").getValue()){
        	$.ligerDialog.warn("请选择仓库！");
        	return false;
        }
//         if(!liger.get("type_level").getValue()){
//         	$.ligerDialog.warn("请选择级次！");
//         	return false;
//         } 
        
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
			value : liger.get("end_month").getValue() == null ? "" : liger.get("end_month").getValue()
		}); 
		grid.options.parms.push({
			name : 'store_type',
			value : $("#store_type_td").find(":radio:checked").val()
		}); 
		grid.options.parms.push({
			name : 'store_id',
			value : liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[0]
		}); 
// 		grid.options.parms.push({
// 			name : 'type_level',
// 			value : liger.get("type_level").getValue() == null ? "" : liger.get("type_level").getValue()
// 		}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	
    	//增加字段
		var add_columns = new StringBuffer();
    	add_columns.append("[ ");
		//减少字段
		var reduct_columns = new StringBuffer();
		reduct_columns.append("[ ");
		//获取本期增加、减少查询字段
    	ajaxJsonObjectByUrl("queryMatAccountReportInvStockSubjColumns.do?isCheck=false", "", function (responseData){
    		if(responseData.Rows.length > 0){
    			//存在本期增加减少字段
    			var render = "render : function(rowdata, rowindex, value) { return formatNumber(value ==null ? 0 : value, " + '${p04005}' + ", 1);}";
    			$.each(responseData.Rows, function(v_index, v_data){ 
    				if(v_data.direction_flag == 1){//本期增加
    					add_columns.append("{display: '"+v_data.show_name+"', name: '"+v_data.show_code+"', align: 'right',formatter:'###,##0.00', minWidth: 100, "+render+"}, ")	
    				}else if(v_data.direction_flag == 2){//本期减少
    					reduct_columns.append("{display: '"+v_data.show_name+"', name: '"+v_data.show_code+"', align: 'right',formatter:'###,##0.00', minWidth: 100, "+render+"}, ")	
    				}
    				
    				renderFunc[v_data.show_code] = function(value){
    					return formatNumber(value ==null ? 0 : value, '${p04005}', 1);
    				}
    			});
    			add_columns.append("{display: '合计金额', name: 'add_sum_money', align: 'right', minWidth: 100,formatter:'###,##0.00', "+render+"} ]");
    			reduct_columns.append("{display: '合计金额', name: 'reduct_sum_money', align: 'right', minWidth: 100,formatter:'###,##0.00', "+render+"} ]");
			}
    	}, false);
        

		var columns = [{
// 				display: '物资分类编码', name: 'mat_type_code', align: 'left', minWidth: '100', frozen: true
// 			}, { 
// 	 			display: '物资分类名称', name: 'mat_type_name', align: 'left', minWidth: '150', frozen: true
// 	 		}, { 
	 			display: '会计科目编码', name: 'subj_code', align: 'left', minWidth: '150', frozen: true
	 		}, { 
	 			display: '会计科目名称', name: 'subj_name', align: 'left', minWidth: '150', frozen: true
	 		}, { 
	 			display: '期初金额', name: 'begin_money', align: 'right', minWidth: '100', frozen: true, 
	 			render : function(rowdata, rowindex, value) {
					return formatNumber(value ==null ? 0 : value, '${p04005}', 1);
				},formatter:"###,##0.00"
	 		}, { 
	 			display: '本期增加',
	 			columns: eval(add_columns.toString()),
	 			formatter:'###,##0.00'
	 		}, { 
	 			display: '本期减少', 
	 			columns: eval(reduct_columns.toString()),
	 			formatter:'###,##0.00'
	 		}, { 
	 			display: '进销误差', name: 'bal_money', align: 'right', minWidth: '100',
	 			render : function(rowdata, rowindex, value) {
					return formatNumber(value ==null ? 0 : value, '${p04005}', 1);
				},formatter:'###,##0.00'
	 		}, { 
	 			display: '期末结存', name: 'end_money', align: 'right', minWidth: '100',
	 			render : function(rowdata, rowindex, value) {
					return formatNumber(value ==null ? 0 : value, '${p04005}', 1);
				},formatter:"###,##0.00"
	 		} ];

		
    	grid = $("#maingrid").ligerGrid({
    		columns : columns, 
			dataAction: 'server', dataType: 'server', usePager: false, url: 'queryMatAccountReportInvStockSubj.do', 
			width: '100%', height: '100%', rownumbers: true, //checkbox: true, 
			delayLoad: true, //初始化不加载，默认false
			selectRowButtonOnly:true, //heightDiff: -10, 
			//alternatingRow: false, //不知道什么意思，有待测试
			tree: { columnId: 'mat_type_code' }, 
			toolbar: { items: [
				{ text: '查询（<u>Q</u>）', id:'search', click: query, icon:'search' },
				{ line:true },
				{ text: '打印（<u>P</u>）', id:'print', click: print, icon:'print' }
			]},
			lodop:{
				
	    		//title:"材料库存汇总表",
	    		 /* fn:{
	    			begin_money:function(value){//期初金额
	    				if(value == 0){return "";}
	           			else{return formatNumber(value ==null ? 0 : value, '${p04005}', 1);}
	    			},
	    			bal_money:function(value){//进销误差
	    				if(value == 0){return "";}
	          			else{return formatNumber(value ==null ? 0 : value, '${p04005}', 1);}
	   				},
	   				end_money:function(value){//期末结存
		   				 if(value==0){return "";}
						 else{return formatNumber(value ==null ? 0 : value, '${p04005}', 1);}
	  				},
	  				add_sum_money:function(value){//合计金额
		   				 if(value==0){return "";}
						 else{return formatNumber(value ==null ? 0 : value, '${p04005}', 1);}
	  				},
	  				reduct_sum_money:function(value){//合计金额
		   				 if(value==0){return "";}
						 else{return formatNumber(value ==null ? 0 : value, '${p04005}', 1);}
	  				}
	    		}  */
		    }
		});

        gridManager = $("#maingrid").ligerGetGridManager();
		
    } 
   
    //键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
		hotkeys('P', print);
	}
    
	//批量打印
	function print(){
    	
    	if(grid.getData().length==0){
    		
			$.ligerDialog.error("请先查询数据！");
			
			return;
		}
    	
    	var heads={
        		"isAuto":true,//系统默认，页眉显示页码
        		"rows": [
    	          {"cell":0,"value":"统计年月："},
    	          {"cell":1,"value":""+liger.get("begin_year").getText()+"-"+liger.get("begin_month").getText()+"至"+liger.get("end_year").getText()+"-"+liger.get("end_month").getText(),"colSpan":"2"},
    	          {"cell":3,"value":""+$("#store_type_td").find(":radio:checked").val()==1?'虚仓':'仓库'+"："},
    	          {"cell":4,"value":""+liger.get("store_code").getText()}
        	]};
    	//表尾
		var foots = {
			rows: [
			    {"cell":0,"value":"制表日期:"} ,
				{"cell":1,"value":date} ,
				{"cell":2,"value":"制表人:"} ,
				{"cell":3,"value":"${user_name}"} ,
				{"cell":0,"value":'分管院领导：',"br":"true"} ,
				{"cell":3,"value":"部门主管:"},
				{"cell":11,"value":"会计:"}
			]
		}; 
    	var printPara={
      		title: "材料库存汇总表",//标题
      		columns: JSON.stringify(grid.getPrintColumns()),//表头
      		class_name: "com.chd.hrp.mat.service.account.report.MatAccountReportInvStockSubjService",
   			method_name: "collectMatAccountReportInvStockSubjPrint",
   			bean_name: "matAccountReportInvStockSubjService",
   			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
   			foots: JSON.stringify(foots),//表尾需要打印的查询条件,可以为空 

       	};
    	$.each(grid.options.parms,function(i,obj){
   			printPara[obj.name]=obj.value;
    	});
   		
    	officeGridPrint(printPara);
    	/* var selPara={};
    	
    	$.each(grid.options.parms,function(i,obj){
    		
    		selPara[obj.name]=obj.value;
    		
    	});
   		
		var dates = getCurrentDate();
    	
    	var cur_date = dates.split(";")[2];
    	//跨所有列:计算列数
    	var colspan_num = grid.getColumns(1).length-1;
    	
   		var printPara={
   			title:'材料库存汇总表',
   			head:[
				{"cell":0,"value":"单位: ${hos_name}","colspan":colspan_num,"br":true},
				{"cell":0,"value":"统计日期: " + $("#begin_year").val() +"年"+$("#begin_month").val() +" 至  "+ $("#end_year").val() +"年"+$("#end_month").val(),"colspan":colspan_num,"br":true}
   			],
   			foot:[
				{"cell":0,"value":"主管:","colspan":3,"br":false} ,
				{"cell":3,"value":"复核人:","colspan":colspan_num-5,"br":false},
				{"cell":colspan_num-2,"value":"制单人： ${user_name}","colspan":2,"br":true},
				{"cell":0,"value":"打印日期: " + cur_date,"colspan":colspan_num,"br":true}
   			],
   			columns:grid.getColumns(1),
   			headCount:2,//列头行数
   			autoFile:true,
   			type:3
   		}; */
   		/* ajaxJsonObjectByUrl("queryMatAccountReportInvStock.do?isCheck=false", selPara, function (responseData) {
   			printGridView(responseData,printPara);
		});  */
      
    }
   
    function loadDict(){//字典下拉框
		//返回当前年,当前月,
		var date = getCurrentDate();
        var aa = date.split(';');
        year = aa[0];
        month = aa[1];
		//返回当前年,当前月,当前日期,当前月第一天,当前月最后一天,上个月,上月第一天，上月最后一天
		autocompleteAsync("#begin_year", "../../queryMatYear.do?isCheck=false", "id", "text", true, true, "", false, year, "60");
        autocompleteAsync("#begin_month", "../../queryMatMonth.do?isCheck=false", "id", "text", true, true, "", false, month, "50");
		autocompleteAsync("#end_year", "../../queryMatYear.do?isCheck=false", "id", "text", true, true, "", false, year, "60");
        autocompleteAsync("#end_month", "../../queryMatMonth.do?isCheck=false", "id", "text", true, true, "", false, month, "50");
		//第一次加载显示虚仓
        autocompleteAsync("#store_code", "../../queryMatVirStore.do?isCheck=false", "id", "text", true, true, "", true);
		//autocompleteAsync("#store_code", "../../queryMatStore.do?isCheck=false", "id", "text", true, true, "", true);
		//autocompleteAsync("#type_level", "../../queryMatTypeLevel.do?isCheck=false", "id", "text", true, true, "", true);
	//	autoCompleteByData("#show_last", yes_or_no.Rows, "id", "text", true, true, "", true);
		autocomplete("#is_charge", "../../queryMatYearOrNo.do?isCheck=false", "id", "text", true, true);
	}  
	
	</script>
</head>

<body style="padding: 0px; ">
	<div id="pageloading" class="l-loading" style="display: none"></div>

    <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" >
		<tr>
			<td align="right" class="l-table-edit-td"  width="100px">
	            统计年月：
			</td>
			<td align="left" class="l-table-edit-td"  width="200px">
				<table >
					<tr>
						<td align="left" class="l-table-edit-td">
							<input name="begin_year" id="begin_year" type="text" required="true" validate="{required:true}" />
						</td>
						<td align="right" class="l-table-edit-td"  >
							年
						</td>
						<td align="left" class="l-table-edit-td">
							<input name="begin_month" id="begin_month" type="text" required="true" validate="{required:true}" />
						</td>
						<td align="right" class="l-table-edit-td"  >
							至：
						</td>
						<td align="left" class="l-table-edit-td">
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
			<td align="right" class="l-table-edit-td"  >
				类型：
			</td>
			<td id="store_type_td" align="left" class="l-table-edit-td"  >
				<input name="store_type" type="radio" checked="checked" value="1" />按虚仓
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input name="store_type" type="radio" value="0" />按仓库
			</td>
			<td align="right" class="l-table-edit-td"  >
				<span id="store_label">虚仓</span>：
			</td>
			<td align="left" class="l-table-edit-td" >
				<input name="store_code" type="text" id="store_code" ltype="text" validate="{required:false,maxlength:100}" />
			</td>
		</tr>
		<tr>
			<!-- <td align="right" class="l-table-edit-td" >
				显示级次：
			</td>
			<td align="left" class="l-table-edit-td">
				<input name="type_level" type="text" id="type_level" ltype="text" validate="{required:false,maxlength:100}" />
			</td> -->
			 <td align="right" class="l-table-edit-td"  width="10%">
				是否收费：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="is_charge" type="text" id="is_charge" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
		</tr> 
	</table>
	<div style="width: 100%; height: 100%; ">
		<div id="maingrid"></div>
	</div>
</body>
</html>
