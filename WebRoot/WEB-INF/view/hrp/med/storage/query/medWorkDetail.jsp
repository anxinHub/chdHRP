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
        grid.options.parms.push({name : 'dept_id',value : liger.get("dept_id").getValue().split(",")[0]}); 
		grid.options.parms.push({name : 'startDate',value : $("#startDate").val()});
		grid.options.parms.push({name : 'endDate',value : $("#endDate").val()});
		grid.options.parms.push({name : 'store_id',value : liger.get("store_code").getValue().split(",")[0]}); 
		grid.options.parms.push({name : 'store_no',value : liger.get("store_code").getValue().split(",")[1]}); 
// 		grid.options.parms.push({name : 'med_type_id',value : liger.get("med_type_code").getValue().split(",")[0]}); 
// 		grid.options.parms.push({name : 'med_type_no',value : liger.get("med_type_code").getValue().split(",")[1]});
		grid.options.parms.push({name : 'med_type_code',value : liger.get("med_type_code").getText().split(" ")[0]});
		grid.options.parms.push({name : 'inv_id',value : liger.get("inv_code").getValue().split(",")[0]}); 
		grid.options.parms.push({name : 'inv_no',value : liger.get("inv_code").getValue().split(",")[1]});
		grid.options.parms.push({name : 'batch_no',value : $("#batch_no").val()});
		grid.options.parms.push({name : 'is_charge',value : liger.get("is_charge").getValue()}); 
		grid.options.parms.push({name : 'bus_type_code',value : liger.get("bus_type_code").getValue()});
		grid.options.parms.push({name : 'cert_code',value : $("#cert_code").val()});
		grid.options.parms.push({
			name : 'inv_model',//规格型号
			value : $("#inv_model").val()
		});  
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [{ 
		 			display: '交易编码', name: 'bid_code', align: 'left', width: 110
		 		}, { 
		 			display: '药品编码', name: 'inv_code', align: 'left', width: 110
		 		},{ 
		 			display: '药品名称', name: 'inv_name', align: 'left', width: 240
		 		}, { 
		 			display: '规格型号', name: 'inv_model', align: 'left',width: 120,
		 		},  { 
		 			display: '计量单位', name: 'unit_name', align: 'left', width: 60
		 		}, { 
		 			display: '单价', name: 'price', align: 'right', width: 90,
		 			render : function(rowdata, rowindex, value) {
						return value ==null ? "" : formatNumber(value, '${p08005 }', 1);
					}
		 		},  { 
		 			display: '数量', name: 'amount', align: 'left', width: 90,
		 			render : function(rowdata, rowindex, value) {
						return value ==null ? "" : formatNumber(value, 2, 1);
					}
		 		},  { 
		 			display: '金额', name: 'amount_money', align: 'right', width: 120,
		 			render : function(rowdata, rowindex, value) {
						return value ==null ? "" : formatNumber(value, '${p08006 }', 1);
					} 
		 		}, { 
		 			display: '确认日期', name: 'confirm_date', align: 'left', width: 90
		 		}, { 
		 			display: '科室/库房', name: 'dept_name', align: 'left', width: 150
		 		},  { 
		 			display: '业务类型', name: 'bus_type_name', align: 'left', width: 80
		 		}, { 
		 			display: '供应商', name: 'sup_name', align: 'left', width: 150
		 		}, { 
		 			display: '生产厂家', name: 'fac_name', align: 'left', width: 150
		 		}, { 
		 			display: '单据号', name: 'in_no', align: 'left', width: 130,
		 			 render : function(rowdata, rowindex, value) {
		 				 if(rowdata.in_no==null){
		 					 return '';
		 				 }else{
		 					 if(rowdata.bus_type_code==2){
		 						return '<a href=javascript:open_update("' 
								+ rowdata.group_id
								+ ',' + rowdata.hos_id 
								+ ',' + rowdata.copy_code
								+ ',' + rowdata.in_id
								+ '")>'+rowdata.in_no+'</a>';
		 					 }else
		 						 if(rowdata.bus_type_code==3){
		 						return '<a href=javascript:openUpdate("' 
								+ rowdata.group_id
								+ ',' + rowdata.hos_id 
								+ ',' + rowdata.copy_code
								+ ',' + rowdata.in_id
								+ '")>'+rowdata.in_no+'</a>';
		 					 }
		 					
		 				 }
							
						}
		 		},  { 
		 			display: '批号', name: 'batch_no', align: 'left', width: 110
		 		}, { 
		 			display: '注册证号', name: 'cert_code', align: 'left', width: 150
		 		}, { 
		 			display: '有效期', name: 'inva_date', align: 'left', width: 90
		 		}],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMedStorageQueryWorkDetail.do',
			width: '100%', height: '100%',rownumbers:true,
			delayLoad: true,//初始化不加载，默认false
			selectRowButtonOnly:true,//heightDiff: -10,
			toolbar: { items: [
			       			{ text: '查询（<u>Q</u>）', id:'search', click: query, icon:'search' },
							{ line:true },
							{ text: '打印', id:'print', click: print, icon:'print' },
			   				{ line:true }
			]},
			onDblClickRow : function (rowdata, rowindex, value)
			{
				if(rowdata.bus_type_code==2){
					update_open(
							rowdata.group_id   + "," + 
							rowdata.hos_id   + "," + 
							rowdata.copy_code   + "," + 
							rowdata.in_id   
						);
				}else if(rowdata.bus_type_code==3){
					openUpdate(
							rowdata.group_id   + "," + 
							rowdata.hos_id   + "," + 
							rowdata.copy_code   + "," + 
							rowdata.in_id   
						);
				}
				
			}  
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
     function openUpdate(obj){
    	
    	var voStr = obj.split(",");
		var paras = 
			"group_id=" + voStr[0].toString() + "&" 
			+ "hos_id=" + voStr[1].toString() + "&" 
			+ "copy_code=" + voStr[2].toString() + "&" 
			+ "out_id=" + voStr[3].toString();
		
		parent.$.ligerDialog.open({
			title:'出库单修改',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/med/storage/out/outlibrary/medOutMainUpdatePage.do?isCheck=false&' + paras.toString(),
			modal: true, showToggle: false, showMax: true, showMin: true, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});	
    }
    //入库单修改
    function update_open(obj){		
    
		var vo = obj.split(",");
		var paras = 
			"group_id="+vo[0] +"&"+ 
			"hos_id="+vo[1] +"&"+ 
			"copy_code="+vo[2] +"&"+ 
			"in_id="+vo[3];
		parent.$.ligerDialog.open({
			title: '入库单修改',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/med/storage/in/updatePage.do?isCheck=false&' + paras.toString(),
			modal: true, showToggle: false, showMax: true, showMin: true, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});   
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
    	
    	var selPara={};
    	
    	$.each(grid.options.parms,function(i,obj){
    		
    		selPara[obj.name]=obj.value;
    		
    	});
   		
		var dates = getCurrentDate();
    	
    	var cur_date = dates.split(";")[2];
    	//跨所有列:计算列数
    	var colspan_num = grid.getColumns(1).length-1;
    	
   		var printPara={
   			title:'业务明细查询',
   			head:[
				{"cell":0,"value":"单位: ${sessionScope.hos_name}","colspan":colspan_num,"br":true},
				{"cell":0,"value":"统计日期: " + $("#year").val() +" 年  "+ $("#month").val(),"colspan":colspan_num,"br":true}
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
   		ajaxJsonObjectByUrl("queryMedStorageQueryStockDetail.do?isCheck=false", selPara, function (responseData) {
   			printGridView(responseData,printPara);
		});
    }
   
    function loadDict(){
		//字典下拉框
		autocompleteAsync("#bus_type_code", "../../queryMedBusType.do?isCheck=false", "id", "text", true, true, "", false,false,160);
		autocomplete("#sup_id", "../../queryHosSupDict.do?isCheck=false", "id", "text", true, true,"",false,'',280);
		//autocomplete("#dept_id", "../../queryMedDept.do?isCheck=false", "id", "text", true, true,{is_last  : 1},false,'',200);
		autocomplete("#dept_id", "../../queryMedDeptDictDate.do?isCheck=false", "id", "text", true, true,{is_last  : 1,read_or_write : 1},false,'',200);
		
		//autocomplete("#store_code", "../../queryMedStoreByRead.do?isCheck=false", "id", "text", true, true,"",true);
		autocomplete("#store_code", "../../queryMedStoreDictDate.do?isCheck=false", "id", "text", true, true,{read_or_write : 1},true);
		//autocomplete("#med_type_code", "../../queryMedTypeDictCode.do?isCheck=false", "id", "text", true, true, "",false);
		autocomplete("#med_type_code", "../../queryMedTypeDictDate.do?isCheck=false", "id", "text", true, true, {read_or_write : 1},false);
		autocomplete("#is_charge", "../../queryMedYearOrNo.do?isCheck=false", "id", "text", true, true);
		autocomplete("#inv_code", "../../queryMedInv.do?isCheck=false", "id", "text", true, true,'',false,'',280);
		 $("#inv_code").ligerTextBox({width:280});
        $("#startDate").ligerTextBox({width:100});
        autodate("#startDate", "yyyy-MM-dd", "yyyy-MM-dd");
        $("#endDate").ligerTextBox({width:100});
        autodate("#endDate", "yyyy-MM-dd", "yyyy-MM-dd");
        $("#batch_no").ligerTextBox({width:200});
        $("#inv_model").ligerTextBox({width:160});
        $("#cert_code").ligerTextBox({width:160});
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
				<td align="right" class="l-table-edit-td" >药品类别：</td>
				<td align="left" class="l-table-edit-td"  ><input name="med_type_code" type="text" id="med_type_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
				<td align="right" class="l-table-edit-td"  >药品信息：</td>
				<td align="left" class="l-table-edit-td"  ><input name="inv_code" type="text" id="inv_code" ltype="text" validate="{required:false,maxlength:100}" /></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td">批&nbsp;&nbsp;号：</td>
				<td align="left" class="l-table-edit-td"><input name="batch_no" type="text" id="batch_no" ltype="text" required="true" validate="{required:true}" /></td>
				<td align="right" class="l-table-edit-td">药品名称:</td>
				<td align="left" class="l-table-edit-td"><input name="inv_model" type="text" id="inv_model" ltype="text"  required="true"  validate="{required:true}" /></td>
				<td align="right" class="l-table-edit-td">是否收费：</td>
				<td align="left" class="l-table-edit-td"><input name="is_charge" type="text" id="is_charge" ltype="text" validate="{required:true,maxlength:20}" /></td>
				<td align="right" class="l-table-edit-td">供应商：</td>
				<td align="left" class="l-table-edit-td"><input name="sup_id" type="text" id="sup_id" ltype="text" validate="{required:false}" /></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td">科室：</td>
				<td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:false}" /></td>
				<td align="right" class="l-table-edit-td">业务类型：</td>
				<td align="left" class="l-table-edit-td"><input name="bus_type_code" type="text" id="bus_type_code" ltype="text" required="true" validate="{required:true}" /></td>
			</tr>
		</table>
	 
<!-- 	<div class="search-block clearfix">
		<div class="cell w1">
			<div>查询期间：</div>
			<div>
				<input class="Wdate" name="startDate" id="startDate" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
				至
				<input class="Wdate" name="endDate" id="endDate" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
			</div>
		</div>
	    <div class="cell w1">
			<div>仓 &nbsp;&nbsp;库：</div>
			<div>
				<input name="store_code" type="text" id="store_code" ltype="text" validate="{required:false}" />
			</div>
		</div>
		<div class="cell w1">
			<div>药品类别：</div>
			<div>
				<input name="med_type_code" type="text" id="med_type_code" ltype="text" validate="{required:true,maxlength:20}" />
			</div>
		</div>
		 <div class="cell w1">
			<div>药品信息：</div>
			<div>
				<input name="inv_code" type="text" id="inv_code" ltype="text" validate="{required:false,maxlength:100}" />
			</div>
		</div>
		<div class="cell w1">
			<div>批&nbsp;&nbsp;号：</div>
			<div>
				<input name="batch_no" type="text" id="batch_no" ltype="text" required="true" validate="{required:true}" />
			</div>
		</div>
		<div class="cell w1">
		<div>药品名称:</div>
		<div>
			<input name="inv_model" type="text" id="inv_model" ltype="text"  required="true"  validate="{required:true}" />
		</div>
		
		</div >
		<div class="cell w1">
			<div>是否收费：</div>
			<div>
				<input name="is_charge" type="text" id="is_charge" ltype="text" validate="{required:true,maxlength:20}" />
			</div>
		</div>

		<div class="cell w1">
			<div>供应商：</div>
			<div>
				<input name="sup_id" type="text" id="sup_id" ltype="text" validate="{required:false}" />
			</div>
		</div>
		<div class="cell w1">
			<div>科室：</div>
			<div>
				<input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:false}" />
			</div>
		</div>
		<div class="cell w1">
			<div>注册证号：</div>
			<div>
				<input name="cert_code" type="text" id="cert_code" ltype="text" required="true" validate="{required:true}" />
			</div>
		</div>
		<div class="cell w1">
			<div>业务类型：</div>
			<div>
				<input name="bus_type_code" type="text" id="bus_type_code" ltype="text" required="true" validate="{required:true}" />
			</div>
		</div>
	</div> -->
	 
	<div id="maingrid"></div>
</body>
</html>
