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
        grid.options.parms.push({name : 'sup_id',value : liger.get("sup_id").getValue().split(",")[0]}); 
		grid.options.parms.push({name : 'startDate',value : $("#startDate").val()});
		grid.options.parms.push({name : 'endDate',value : $("#endDate").val()});
		grid.options.parms.push({name : 'store_id',value : liger.get("store_code").getValue().split(",")[0]}); 
		grid.options.parms.push({name : 'store_no',value : liger.get("store_code").getValue().split(",")[1]}); 
		grid.options.parms.push({name : 'mat_type_code',value : liger.get("mat_type_code").getText().split(" ")[0]});
		grid.options.parms.push({name : 'inv_id',value : liger.get("inv_code").getValue().split(",")[0]}); 
		grid.options.parms.push({name : 'inv_no',value : liger.get("inv_code").getValue().split(",")[1]});
		grid.options.parms.push({name : 'batch_no',value : $("#batch_no").val()});
		grid.options.parms.push({name : 'bus_type_code',value : liger.get("bus_type_code").getValue()});
		grid.options.parms.push({name : 'cert_code',value : $("#cert_code").val()});
		grid.options.parms.push({name : 'fac_id',value : liger.get("fac_id").getValue().split(",")[0]}); 
		grid.options.parms.push({name : 'inv_model',value : $("#inv_model").val()});  
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [{ 
		 			display: '材料编码', name: 'inv_code', align: 'left', width: 90
		 		},{ 
		 			display: '材料名称', name: 'inv_name', align: 'left', width: 180
		 		}, { 
		 			display: '规格型号', name: 'inv_model', align: 'left',width: 120,
		 		},  { 
		 			display: '计量单位', name: 'unit_name', align: 'left', width: 60
		 		}, { 
		 			display: '供应商', name: 'sup_name', align: 'left', width: 150
		 		}, { 
		 			display: '生产商', name: 'fac_name', align: 'left', width: 150
		 		}, { 
		 			display: '单价', name: 'price', align: 'right', width: 90,
		 			render : function(rowdata, rowindex, value) {
		 				var zhi=0;
		 				if(rowdata.in_amount ==null ||  rowdata.in_amount == 0 ||rowdata.in_amount_money ==null){
		 					zhi=formatNumber(0, '${p04006 }', 1);
		 				}else{
		 					zhi=rowdata.in_amount_money/rowdata.in_amount;
		 					zhi=formatNumber(zhi, '${p04006 }', 1);
		 				}
						return zhi;
					}
		 		},  { 
		 			display: '入库数量', name: 'in_amount', align: 'left', width: 80,
		 			render : function(rowdata, rowindex, value) {
						return value ==null ? "" : "<a href=javascript:queryInDetail('"+
								    "inv_id="+rowdata.inv_id+"&sup_id="+(rowdata.sup_id?rowdata.sup_id:"")+"&fac_id="+(rowdata.fac_id?rowdata.fac_id:"")
								+"')>"+formatNumber(value, 2, 1)+"</a>";
					}
		 		},  { 
		 			display: '入库金额', name: 'in_amount_money', align: 'right', width: 120,
		 			render : function(rowdata, rowindex, value) {
						return value ==null ? "" : formatNumber(value, '${p04005 }', 1);
					} 
		 		},  { 
		 			display: '出库数量', name: 'out_amount', align: 'left', width: 80,
		 			render : function(rowdata, rowindex, value) {
		 				return value ==null ? "" : "<a href=javascript:queryOutDetail('"+
		 						"inv_id="+rowdata.inv_id+"&sup_id="+(rowdata.sup_id?rowdata.sup_id:"")+"&fac_id="+(rowdata.fac_id?rowdata.fac_id:"")
							+"')>"+formatNumber(value, 2, 1)+"</a>";
		 			}
		 		},  { 
		 			display: '出库金额', name: 'out_amount_money', align: 'right', width: 120,
		 			render : function(rowdata, rowindex, value) {
						return value ==null ? "" : formatNumber(value, '${p04005 }', 1);
					} 
		 		}],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatInOutDetail.do',
			width: '100%', height: '100%',rownumbers:true,
			delayLoad: true,//初始化不加载，默认false
			selectRowButtonOnly:true,//heightDiff: -10,
			toolbar: { items: [
			       			{ text: '查询（<u>Q</u>）', id:'search', click: query, icon:'search' },
							{ line:true },
			       			{ text: '打印（<u>P</u>）', id:'print', click: print, icon:'print' },
							
			]}
			
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    //查询入库材料数量明细
     function queryInDetail(para){
    	
    	var paras=para+"&bus_type_code="+liger.get("bus_type_code").getValue()
    	          +"&store_id="+liger.get("store_code").getValue().split(",")[0]
    	          +"&store_no="+(liger.get("store_code").getValue().split(",")[1]?liger.get("store_code").getValue().split(",")[1]:"")
    	          +"&mat_type_code="+liger.get("mat_type_code").getText().split(" ")[0]
    	          +"&startDate="+$("#startDate").val()
    	          +"&endDate="+$("#endDate").val()
    	          +"&batch_no="+$("#batch_no").val()
    	
		parent.$.ligerDialog.open({
			title:'材料入库详情',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/mat/storage/in/invInDetailPage.do?isCheck=false&' + paras.toString(),
			modal: true, showToggle: false, showMax: true, showMin: true, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});	 
    }
   //查询入库材料数量明细
    function queryOutDetail(para){		
    	var paras=para
		        +"&bus_type_code="+liger.get("bus_type_code").getValue()
		        +"&store_id="+liger.get("store_code").getValue().split(",")[0]
		        +"&store_no="+(liger.get("store_code").getValue().split(",")[1]?liger.get("store_code").getValue().split(",")[1]:"")
		        +"&mat_type_code="+liger.get("mat_type_code").getText().split(" ")[0]
		        +"&startDate="+$("#startDate").val()
		        +"&endDate="+$("#endDate").val()
		        +"&batch_no="+$("#batch_no").val()
		
		parent.$.ligerDialog.open({
			title:'材料出库详情',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/mat/storage/out/outlibrary/invOutDetailPage.do?isCheck=false&' + paras.toString(),
			modal: true, showToggle: false, showMax: true, showMin: true, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
        });
    }
   
   
   //页面打印
   
   function print(){
	   var time = new Date(); //获得当前时间
	   var year = time.getFullYear();//获得年、月、日
	   var month = time.getMonth()+1;
	   var day = time.getDate();  
	   var date = year+"年"+month+"月"+day;	
	   
	   if(grid.getData().length==0){
   		
			$.ligerDialog.error("请先查询数据！");
			
			return;
		}
   	
   	
	   	var heads={
	   			
	       		"isAuto":true,//系统默认，页眉显示页码
	       		"rows": [
	   	          {"cell":0,"value":"统计年月："},
	   	          {"cell":1,"value":""+liger.get("startDate").getValue()+"至"+liger.get("endDate").getValue()}	,
	   	          {"cell":3,"value":"仓库："},
	   	          {"cell":4,"value":""+liger.get("store_code").getText()==''?'空':liger.get("store_code").getText().split(" ")[1]+""}
	       	]}; 
	   	//表尾
			var foots = {
				rows: [
					{"cell":0,"value":"制单日期:"} ,
					{"cell":1,"value":date} ,
				]
			}; 
	   	var printPara={
	         		title: "入出库查询表",//标题
	         		columns: JSON.stringify(grid.getPrintColumns()),//表头
	         		class_name: "com.chd.hrp.mat.service.storage.query.MatInDetailService",
	      			method_name: "queryMatInOutDetailPrint",
	      			bean_name: "matInDetailService",
	      			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
	      			foots: JSON.stringify(foots),//表尾需要打印的查询条件,可以为空 
	          	};
	       	$.each(grid.options.parms,function(i,obj){
	      			printPara[obj.name]=obj.value;
	       	});
	      		
	       	officeGridPrint(printPara);
   }
    
    
    
  //键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
	}
 
	
   
    function loadDict(){
		//字典下拉框
		autocompleteAsync("#bus_type_code", "../../queryMatBusType.do?isCheck=false", "id", "text", true, true, {sel_flag:'in',read_or_write : 1}, false,'',160);
		autocomplete("#sup_id", "../../queryHosSupDict.do?isCheck=false", "id", "text", true, true,"",false,'',280);
		autocomplete("#fac_id", "../../queryHosFacDict.do?isCheck=false", "id", "text", true, true,"",false,'',160);
		autocomplete("#store_code", "../../queryMatStoreDictDate.do?isCheck=false", "id", "text", true, true,{read_or_write : 1},true);
		autocomplete("#mat_type_code", "../../queryMatTypeDictDate.do?isCheck=false", "id", "text", true, true, {read_or_write : 1},false);
		autocomplete("#inv_code", "../../queryMatInv.do?isCheck=false", "id", "text", true, true,'',false,'',280);
		 $("#inv_code").ligerTextBox({width:280});
        $("#startDate").ligerTextBox({width:93});
        autodate("#startDate", "yyyy-MM-dd", "month_first");
        $("#endDate").ligerTextBox({width:93});
        autodate("#endDate", "yyyy-MM-dd", "month_last");
        $("#batch_no").ligerTextBox({width:200});
        $("#inv_model").ligerTextBox({width:160});
        
	}  
	
	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
	 
		<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
			<tr>
				<td align="right" class="l-table-edit-td"  >查询期间：</td>
				<td align="left" class="l-table-edit-td"  >
					<table>
						<tr>
							<td><input class="Wdate" name="startDate" id="startDate" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
							<td>至</td>
							<td><input class="Wdate" name="endDate" id="endDate" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
						</tr>
					</table>
				</td>
				<td align="right" class="l-table-edit-td" >仓 &nbsp;&nbsp;库：</td>
				<td align="left" class="l-table-edit-td"  ><input name="store_code" type="text" id="store_code" ltype="text" validate="{required:false}" /></td>
				<td align="right" class="l-table-edit-td" >物资类别：</td>
				<td align="left" class="l-table-edit-td"  ><input name="mat_type_code" type="text" id="mat_type_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"  >材料信息：</td>
				<td align="left" class="l-table-edit-td"  ><input name="inv_code" type="text" id="inv_code" ltype="text" validate="{required:false,maxlength:100}" /></td>
				<td align="right" class="l-table-edit-td">批&nbsp;&nbsp;号：</td>
				<td align="left" class="l-table-edit-td"><input name="batch_no" type="text" id="batch_no" ltype="text" required="true" validate="{required:true}" /></td>
				<td align="right" class="l-table-edit-td">材料名称:</td>
				<td align="left" class="l-table-edit-td"><input name="inv_model" type="text" id="inv_model" ltype="text"  required="true"  validate="{required:true}" /></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td">供应商：</td>
				<td align="left" class="l-table-edit-td"><input name="sup_id" type="text" id="sup_id" ltype="text" validate="{required:false}" /></td>
				<td align="right" class="l-table-edit-td">业务类型：</td>
				<td align="left" class="l-table-edit-td"><input name="bus_type_code" type="text" id="bus_type_code" ltype="text" required="true" validate="{required:true}" /></td>
				<td align="right" class="l-table-edit-td">生成厂商：</td>
				<td align="left" class="l-table-edit-td"><input name="fac_id" type="text" id="fac_id" ltype="text" validate="{required:false}" /></td>
			</tr>
		</table>
	 
	 
	<div id="maingrid"></div>
</body>
</html>
