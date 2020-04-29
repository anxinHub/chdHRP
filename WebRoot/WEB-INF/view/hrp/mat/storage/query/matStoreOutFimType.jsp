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
		$("#is_showStore").bind("change",function(){
			
			query();
		});
    });
    //查询
    function  query(){
    	
		grid.options.parms=[];
		grid.options.newPage=1;
		/* if(liger.get("store_code").getValue().split(",")[0]!=null || liger.get("set_code").getValue() !=null){
			$.ligerDialog.error('x！');
			
		} */
        //根据表字段进行添加查询条件
       // $("#store_type_td").find(":radio:checked").val()
         grid.options.parms.push({name : 'begin_confirm_date',value : $("#begin_confirm_date").val()});
		 grid.options.parms.push({name : 'end_confirm_date',value : $("#end_confirm_date").val()});
         grid.options.parms.push({name : 'store_id',value : liger.get("store_code").getValue().split(",")[0]}); 
         grid.options.parms.push({name : 'set_id',value : liger.get("set_code").getValue()});
         grid.options.parms.push({name : 'is_showStore',value : $("#is_showStore").is(":checked") ? "1":""}); 
         grid.options.parms.push({name : 'is_tran',value :  $("#is_tran").is(":checked") ? "1":"0"});
         
         if(liger.get("ex_store_id").getValue().length>0){
 			
 			var ex_store_ids=liger.get("ex_store_id").getValue().split(";");
 			var ex_store_id="";
 			for(var code of ex_store_ids){
 				var codes=code.split(",");
 				ex_store_id+=ex_store_id.length>0?","+codes[0] : codes[0];
 			}
 			if(ex_store_id.length>0){
 				grid.options.parms.push({name : 'ex_store_id',value : ex_store_id});
 			}
 		}
         
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({

			columns: [ { 

		 			display: '仓库名称', name: 'dept_name', align: 'left'
		 		},{ 
		 			display: '计价化材', name: 'radiaiton_three_money', align: 'right' ,
		 			render : function(rowdata, rowindex, value) {
						return formatNumber(value ==null ? 0 : value, '${p04005 }', 1);
					} 
		 		},{ 
		 			display: '非计价化材', name: 'radiaiton_four_money', align: 'right' ,
		 			render : function(rowdata, rowindex, value) {
						return formatNumber(value ==null ? 0 : value, '${p04005 }', 1);
					}
		 		},{ 
		 			display: '计价卫材', name: 'radiaiton_nine_money', align: 'right' ,
		 			render : function(rowdata, rowindex, value) {
						return formatNumber(value ==null ? 0 : value, '${p04005 }', 1);
					}
		 		},{ 
		 			display: '非计价卫材', name: 'radiaiton_ten_money', align: 'right' ,
		 			render : function(rowdata, rowindex, value) {
						return formatNumber(value ==null ? 0 : value, '${p04005 }', 1);
					}
		 		},{ 
		 			display: '低值易耗品', name: 'radiaiton_eleven_money', align: 'right' ,
		 			render : function(rowdata, rowindex, value) {
						return formatNumber(value ==null ? 0 : value, '${p04005 }', 1);
					}
		 		},{ 
		 			display: '计价放材', name: 'radiaiton_one_money', align: 'right' ,
		 			render : function(rowdata, rowindex, value) {
						return formatNumber(value ==null ? 0 : value, '${p04005 }', 1);
					}
		 		},{ 
		 			display: '非计价放材', name: 'radiaiton_two_money', align: 'right' ,
		 			render : function(rowdata, rowindex, value) {
						return formatNumber(value ==null ? 0 : value, '${p04005 }', 1);
					}
		 		}, { 
		 			display: '计价植材', name: 'radiaiton_five_money', align: 'right' ,
		 			render : function(rowdata, rowindex, value) {
						return formatNumber(value ==null ? 0 : value, '${p04005 }', 1);
					} 
		 		},  { 
		 			display: '非计价植材', name: 'radiaiton_six_money', align: 'right' ,
		 			render : function(rowdata, rowindex, value) {
						return formatNumber(value ==null ? 0 : value, '${p04005 }', 1);
					} 
		 		},  { 
		 			display: '计价介材', name: 'radiaiton_seven_money', align: 'right' ,
		 			render : function(rowdata, rowindex, value) {
						return formatNumber(value ==null ? 0 : value, '${p04005 }', 1);
					} 
		 		},  { 
		 			display: '非计价介材', name: 'radiaiton_eight_money', align: 'right' ,
		 			render : function(rowdata, rowindex, value) {
						return formatNumber(value ==null ? 0 : value, '${p04005 }', 1);
					} 
		 		}, { 
		 			display: '总计', name: 'amount_money', align: 'right',
		 			render : function(rowdata, rowindex, value) {
						return formatNumber(value ==null ? 0 : value, '${p04005 }', 1);
					} 
		 		}],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatStoreOutFimType.do',
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
 		head=head+"<tr><td>查询期间："+$("#year").val()+"年"+$("#month").val()+"日"+"</td></tr>";
 		head=head+"</table>";
 		grid.options.lodop.head=head; 
 		grid.options.lodop.fn=renderFunc;
 		grid.options.lodop.title="业务明细查询";
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
    	var storeName="";
    	if(liger.get("set_code").getText()==''){
    		if(liger.get("store_code").getText()==''){
    			storeName="空";
    		}else{
	    		storeName=liger.get("store_code").getText().split(" ")[1]
    		}
    	}else{
    		storeName=liger.get("set_code").getText().split(" ")[1]
    	}
    	
    	var heads={
        		"isAuto":true,//系统默认，页眉显示页码
        		"rows": [
				  {"cell":0,"value":"单位："},
				  {"cell":1,"value":"${sessionScope.hos_name}","colSpan":2},
    	          {"cell":0,"value":"统计年月：","br":"true"},
    	          {"cell":1,"value":""+$("#begin_confirm_date").val()+"至"+$("#end_confirm_date").val(),"colSpan":"2"},
    	          {"cell":11,"value":"仓库：","align":"right"},
    	          {"cell":12,"value":storeName}
    	        ]};  
    	//表尾 
    	var time = new Date(); //获得当前时间
		var year = time.getFullYear();//获得年、月、日
		var month = time.getMonth()+1;
		var day = time.getDate(); 
		var formDate = year+"年"+month+"月"+day+"日";
		var foots = {
			rows: [
				
				
			]
		}; 
    	var printPara={
          		title: "科室领用耗材分类汇总表",//标题
          		columns: JSON.stringify(grid.getPrintColumns()),//表头
          		class_name: "com.chd.hrp.mat.service.storage.query.MatDeptOutReportService",
       			method_name: "queryMatStoreOutFimTypePrint",
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
		autocomplete("#store_code", "../../queryMatStoreDictDate.do?isCheck=false", "id", "text", true, true,{read_or_write:1});	
		autocomplete("#set_code", "../../queryMatVirStore.do?isCheck=false", "id", "text", true, true);
		autocomplete("#is_showStore", "../../queryMatVirStore.do?isCheck=false", "id", "text", true, true);
		autocompleteAsyncMulti("#ex_store_id", "../../queryMatStoreDictDate.do?isCheck=false", "id", "text", true, true, {read_or_write : 1}, false,'',160);
		
        $("#begin_confirm_date").ligerTextBox({width:100});
        $("#end_confirm_date").ligerTextBox({width:100});
        autodate("#begin_confirm_date", "yyyy-mm-dd", "month_first");
        autodate("#end_confirm_date", "yyyy-mm-dd", "month_last");

	}  
	
	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
	 
		<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
			<tr>
				<td align="right" class="l-table-edit-td"   >统计日期：</td>
				<td align="left" class="l-table-edit-td" >
					<table>
						<tr>
							<td><input class="Wdate" name="begin_confirm_date" id="begin_confirm_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
							<td>至</td>
							<td><input class="Wdate" name="end_confirm_date" id="end_confirm_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
						</tr>
					</table>
				</td>
				<td align="right" class="l-table-edit-td">虚仓名称：</td>
            	<td align="left" class="l-table-edit-td"> 
            		<input name="set_code" type="text" id="set_code" ltype="text" validate="{required:false}" />
  				</td> 
	      	    <td align="right" class="l-table-edit-td" > 仓库： </td>
           	    <td align="left" class="l-table-edit-td">
            		<input name="store_code" type="text" id="store_code" ltype="text" validate="{required:false}" />
                </td> 
           </tr>
           <tr>
           	    <td align="right" class="l-table-edit-td">调拨排除仓库：</td>
				<td align="left" class="l-table-edit-td">
					<input name="ex_store_id" type="text" id="ex_store_id" ltype="text" required="true" validate="{required:true}" />
				</td>
                <td align="right" class="l-table-edit-td" > </td>
                <td align="left" class="l-table-edit-td" >
		        	<input name="is_tran" type="checkbox" id="is_tran" ltype="text" checked="checked"/>是否包含调拨
	        	</td>
				<!-- <td align="right" class="l-table-edit-td" >仓 &nbsp;&nbsp;库：</td>
				<td align="left" class="l-table-edit-td"  ><input name="store_code" type="text" id="store_code" ltype="text" validate="{required:false}" /></td>
				 -->
		    </tr>
			 
		</table> 
	 
	<div id="maingrid"></div>
</body>
</html>
