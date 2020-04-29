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
        //根据表字段进行添加查询条件
        
		grid.options.parms.push({name : 'begin_confirm_date',value : $("#begin_confirm_date").val()});
		grid.options.parms.push({name : 'end_confirm_date',value :$("#end_confirm_date").val()});
		grid.options.parms.push({name : 'begin_tran_date',value : $("#begin_tran_date").val()});
		grid.options.parms.push({name : 'end_tran_date',value : $("#end_tran_date").val()});
		grid.options.parms.push({name : 'inv_msg',value : $("#inv_msg").val()});
	    grid.options.parms.push({name : 'out_store_id',value : liger.get("out_store_code").getValue().split(",")[0]}); 
		grid.options.parms.push({name : 'out_store_no',value : liger.get("out_store_code").getValue().split(",")[1]}); 
	    grid.options.parms.push({name : 'in_store_id',value : liger.get("in_store_code").getValue().split(",")[0]}); 
		grid.options.parms.push({name : 'in_store_no',value : liger.get("in_store_code").getValue().split(",")[1]}); 
		grid.options.parms.push({name : 'mat_type_id',value : liger.get("mat_type_code").getText().split(" ")[0]}); 
		grid.options.parms.push({name : 'mat_type_code',value : liger.get("mat_type_code").getText().split(" ")[0]});
		grid.options.parms.push({name : 'inv_model',value : $("#inv_model").val()});
		grid.options.parms.push({name : 'state',value : $("#state").prop("checked") ? "1":"0"});
		grid.options.parms.push({name : 'is_charge',value : liger.get("is_charge").getValue()}); 
		
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	if($("#is_detail").is(':checked')==false){
	    	grid = $("#maingrid").ligerGrid({
	    		columns: [{ 
			 			display: '调出仓库', name: 'out_store_name', align: 'left', Width: '80'
			 		}, { 
			 			display: '调入仓库', name: 'in_store_name', align: 'left', Width: '80'
			 		}, { 
			 			display: '物资类别', name: 'mat_type_name', align: 'left', Width: '80'
			 		}, { 
			 			display: '材料编码', name: 'inv_code', align: 'left', Width: '80'
			 		}, { 
			 			display: '材料名称', name: 'inv_name', align: 'left', Width: '80'
			 		}, { 
			 			display: '规格型号', name: 'inv_model', align: 'left', Width: '80'
			 		}, { 
			 			display: '计量单位', name: 'unit_name', align: 'left', width: '60'
			 		}, { 
			 			display: '数量', name: 'amount', align: 'right', Width: '80'
			 			
			 		},  { 
			 			display: '金额', name: 'amount_money', align: 'right',Width: '100',
			 			render : function(rowdata, rowindex, value) {
							return formatNumber(rowdata.amount_money ==null ? 0 : rowdata.amount_money, '${p04005 }', 1);
						},formatter:"###,##0.00"
			 		}, { 
			 			display: '生产厂商', name: 'fac_name', align: 'left', Width: '80'
			 		}
			 		],
			 		dataAction: 'server',dataType: 'server',usePager:true,
			 		url:'queryMatAccountReportTranCollection.do?isCheck=false',
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
	    }else{
	    	grid = $("#maingrid").ligerGrid({
		    	columns: [{ 
		 			display: '调出仓库', name: 'out_store_name', align: 'left', Width: '80'
		 		}, { 
		 			display: '调入仓库', name: 'in_store_name', align: 'left', Width: '80'
		 		}, { 
		 			display: '调拨单号', name: 'tran_no', align: 'left', Width: '80',
		 			render : function(rowdata, rowindex, value) {
                		if(!value){
                			return '';
                		}
  						return '<a href=javascript:openUpdate("' 
  							+ rowdata.group_id
  							+ ',' + rowdata.hos_id 
  							+ ',' + rowdata.copy_code
  							+ ',' + rowdata.tran_id
  							+ ',' + rowdata.out_store_id
  							+ ',' + rowdata.in_store_id
  							+ '")>'+rowdata.tran_no+'</a>';
  					}		
		 		}, { 
		 			display: '物资类别', name: 'mat_type_name', align: 'left', Width: '80'
		 		}, { 
		 			display: '材料编码', name: 'inv_code', align: 'left', Width: '80'
		 		}, { 
		 			display: '材料名称', name: 'inv_name', align: 'left', Width: '80'
		 		}, { 
		 			display: '规格型号', name: 'inv_model', align: 'left', Width: '80'
		 		}, { 
		 			display: '计量单位', name: 'unit_name', align: 'left', width: '60'
		 		}, { 
		 			display: '数量', name: 'amount', align: 'right', Width: '80'
		 			
		 		},  { 
		 			display: '金额', name: 'amount_money', align: 'right',Width: '100',
		 			render : function(rowdata, rowindex, value) {
						return formatNumber(rowdata.amount_money ==null ? 0 : rowdata.amount_money, '${p04005 }', 1);
					},formatter:"###,##0.00"
		 		}, { 
		 			display: '生产厂商', name: 'fac_name', align: 'left', Width: '80'
		 		}
		 		],
		 		dataAction:'server',dataType:'server',usePager:true,
		 		url:'queryMatAccountReportTranDetail.do?isCheck=false',
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
	    }
        gridManager = $("#maingrid").ligerGetGridManager();
        query();
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
    	          {"cell":1,"value":""+liger.get("begin_confirm_date").getValue()+"至"+liger.get("end_confirm_date").getValue()}	,
        	]}; 
    	//表尾
		var foots = {
			rows: [
				{"cell":0,"value":"制表日期:"} ,
				{"cell":1,"value":date} ,
			]
		}; 
    	
		if($("#is_detail").is(':checked')==false){
			var printPara={
	         		title: "调拨查询汇总表",//标题
	         		columns: JSON.stringify(grid.getPrintColumns()),//表头
	         		class_name: "com.chd.hrp.mat.service.storage.query.MatInDetailService",
	      			method_name: "queryMatAccountReportTranCollectionOrDetailPrint",
	      			bean_name: "matInDetailService",
	      			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
	      			foots: JSON.stringify(foots),//表尾需要打印的查询条件,可以为空 
	          	};
		}else{
			var printPara={
	         		title: "调拨查询明细表",//标题
	         		columns: JSON.stringify(grid.getPrintColumns()),//表头
	         		class_name: "com.chd.hrp.mat.service.storage.query.MatInDetailService",
	      			method_name: "queryMatAccountReportTranCollectionOrDetailPrint",
	      			bean_name: "matInDetailService",
	      			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
	      			foots: JSON.stringify(foots),//表尾需要打印的查询条件,可以为空 
	      			show_detail:"1"
	          	};
		}
    	
   		
       	$.each(grid.options.parms,function(i,obj){
      			printPara[obj.name]=obj.value;
       	});
      		
       	officeGridPrint(printPara);
   		
    }
	
	function openUpdate(obj) {

		var voStr = obj.split(",");
		var paras = 
			"group_id=" + voStr[0].toString() + "&" 
			+ "hos_id=" + voStr[1].toString() + "&" 
			+ "copy_code=" + voStr[2].toString() + "&" 
			+ "tran_id=" + voStr[3].toString() + "&" 
			+ "store_id=" + voStr[4].toString()+ "&" 
			+ "in_store_id=" + voStr[5].toString();;
		
		parent.$.ligerDialog.open({
			title: '调拨单修改',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/mat/storage/tran/matTranMainPageForShow.do?isCheck=false&' + paras.toString(),
			modal: true, showToggle: false, showMax: true, showMin: true, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});   
	}
   
    function loadDict(){
		//字典下拉框
		autocomplete("#out_store_code", "../../queryMatStoreDictDate.do?isCheck=false", "id", "text", true, true,{read_or_write : 1},false);
		autocomplete("#in_store_code", "../../queryMatStoreDictDate.do?isCheck=false", "id", "text", true, true,{read_or_write : 1},false);
		autocomplete("#mat_type_code", "../../queryMatTypeDictDate.do?isCheck=false", "id", "text", true, true,{read_or_write : 1},false,'',280);
		autocomplete("#sup_code", "../../queryHosSupDict.do?isCheck=false", "id", "text", true, true);
		autocomplete("#is_charge", "../../queryMatYearOrNo.do?isCheck=false", "id", "text", true, true);
		
		$("#inv_code").ligerTextBox({width:280});
		
		$("#begin_confirm_date").ligerTextBox({width:100});
        $("#end_confirm_date").ligerTextBox({width:100});
        
        autodate("#begin_confirm_date", "yyyy-mm-dd", "month_first");
        autodate("#end_confirm_date", "yyyy-mm-dd", "month_last");
        
        $("#begin_tran_date").ligerTextBox({width:100});
        $("#end_tran_date").ligerTextBox({width:100});
        
        $("#inv_model").ligerTextBox({width:247});
        $("#inv_msg").ligerTextBox({width:280});
	}  
	function is_detail(){
		if (grid) {
			grid.unbind();
		}
		loadHead();
		
	}
	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
		<tr>
			<td align="right" class="l-table-edit-td"  width="10%">
            	调拨日期：
        	</td>
        	
        	<td align="left" class="l-table-edit-td"  width="20%">
				<table>
					<tr>
						<td align="left" class="l-table-edit-td">
							<input class="Wdate" name="begin_confirm_date" id="begin_confirm_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>						</td>
						<td align="right" class="l-table-edit-td"  >
							至：
						</td>
						<td align="left" class="l-table-edit-td">
							<input class="Wdate" name="end_confirm_date" id="end_confirm_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
            		</tr>
				</table>
	        </td>
	        
	        <td align="right" class="l-table-edit-td"  width="10%">
            	调出仓库：
        	</td>
        	
        	<td align="left" class="l-table-edit-td"  width="20%">
        		<input name="out_store_code" type="text" id=out_store_code ltype="text" validate="{required:false}" />
        	</td>
        	
        	<td align="right" class="l-table-edit-td"  width="10%">
            	物资类别：
        	</td>
        	
        	<td align="left" class="l-table-edit-td"  width="20%">
        		<input name="mat_type_code" type="text" id="mat_type_code" ltype="text" validate="{required:true,maxlength:20}" />
        	</td>
		</tr>
		
		<tr>
			<td align="right" class="l-table-edit-td"  width="10%">
            	制单日期：
        	</td>
        	
        	<td align="left" class="l-table-edit-td"  width="20%">
				<table>
					<tr>
						<td align="left" class="l-table-edit-td">
							<input class="Wdate" name="begin_tran_date" id="begin_tran_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
						<td align="right" class="l-table-edit-td"  >
							至：
						</td>
						<td align="left" class="l-table-edit-td">
							<input class="Wdate" name="end_tran_date" id="end_tran_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
            		</tr>
				</table>
	        </td>
	        <td align="right" class="l-table-edit-td"  width="10%">
            	调入仓库：
        	</td>
        	
        	<td align="left" class="l-table-edit-td"  width="20%">
        		<input name="in_store_code" type="text" id=in_store_code ltype="text" validate="{required:false}" />
        	</td>
        	<td align="right" class="l-table-edit-td"  width="10%">
            	材料信息：
        	</td>
        	
        	<td align="left" class="l-table-edit-td"  width="20%">
        		<input name="inv_msg" type="text" id="inv_msg" ltype="text"/>
        	</td>
		</tr>	
		
		<tr>
			<td align="right" class="l-table-edit-td"  width="10%">
            	规格型号:
        	</td>
        	<td align="left" class="l-table-edit-td"  width="20%">
        		<input name="inv_model" type="text" id="inv_model" ltype="text"  />
        	</td>
        	
        	<td align="right" class="l-table-edit-td"  width="10%">
				是否收费：
			</td>
			<td align="left" class="l-table-edit-td" width="20%">
            	<input name="is_charge" type="text" id="is_charge" ltype="text" />
            </td>
        	
        	<td align="right" class="l-table-edit-td"  width="10%"></td>
        	<td align="left" class="l-table-edit-td"  width="20%">
        		<input name="is_detail" type="checkbox" id="is_detail" ltype="text" onchange="is_detail()" />显示明细
        		<input name="state" type="checkbox" id="state" ltype="text"/>包含未确认单据
        	</td>
		</tr>	
	</table>
	<div id="maingrid"></div>
</body>
</html>
