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
        //根据表字段进行添加查询条件
        grid.options.parms.push({name : 'sup_id',value : liger.get("sup_id").getValue().split(",")[0]}); 
        grid.options.parms.push({name : 'dept_id',value : liger.get("dept_id").getValue().split(",")[0]}); 
		grid.options.parms.push({name : 'startDate',value : $("#startDate").val()});
		grid.options.parms.push({name : 'endDate',value : $("#endDate").val()});
		grid.options.parms.push({name : 'store_id',value : liger.get("store_code").getValue().split(",")[0]}); 
		grid.options.parms.push({name : 'store_no',value : liger.get("store_code").getValue().split(",")[1]}); 
// 		grid.options.parms.push({name : 'mat_type_id',value : liger.get("mat_type_code").getValue().split(",")[0]}); 
// 		grid.options.parms.push({name : 'mat_type_no',value : liger.get("mat_type_code").getValue().split(",")[1]});
		grid.options.parms.push({name : 'mat_type_code',value : liger.get("mat_type_code").getText().split(" ")[0]});
		grid.options.parms.push({name : 'in_out',value : liger.get("in_out").getValue() == null ? "" : liger.get("in_out").getValue()});
		grid.options.parms.push({name : 'inv_id',value : liger.get("inv_code").getValue().split(",")[0]}); 
		grid.options.parms.push({name : 'inv_no',value : liger.get("inv_code").getValue().split(",")[1]});
		grid.options.parms.push({name : 'batch_no',value : $("#batch_no").val()});
		grid.options.parms.push({name : 'is_charge',value : liger.get("is_charge").getValue()}); 
	/* 	grid.options.parms.push({name : 'bus_type_code',value : liger.get("bus_type_code").getValue()}); */
		grid.options.parms.push({name : 'set_id',value : liger.get("set_code").getValue() == null ? "" : liger.get("set_code").getValue()});
	if(liger.get("bus_type_code").getValue().length>0){
			
			var bus_type_codes=liger.get("bus_type_code").getValue().split(";");
			var bus_type_code="";
			for(var code of bus_type_codes){
				/*
				if(code==='15'){
					grid.options.parms.push({name : 'tran_code',value : '15'});
				}else{
					bus_type_code+=bus_type_code.length>0?","+code:code;
				}
				*/
				bus_type_code+=bus_type_code.length>0?","+code:code;
			}
			if(bus_type_code.length>0){
				grid.options.parms.push({name : 'bus_type_code',value : bus_type_code});
			}
		}
		grid.options.parms.push({name : 'cert_code',value : $("#cert_code").val()});
		grid.options.parms.push({name : 'fac_id',value : liger.get("fac_id").getValue().split(",")[0]}); 
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
		 			display: '材料编码', name: 'inv_code', align: 'left', width: 110
		 		},{ 
		 			display: '材料名称', name: 'inv_name', align: 'left', width: 240
		 		}, { 
		 			display: '规格型号', name: 'inv_model', align: 'left',width: 120,
		 		},  { 
		 			display: '计量单位', name: 'unit_name', align: 'left', width: 60
		 		}, { 
		 			display: '单价', name: 'price', align: 'right', width: 90,
		 			render : function(rowdata, rowindex, value) {
						return value ==null ? "" : formatNumber(value, '${p04006 }', 1);
					}
		 		},  { 
		 			display: '数量', name: 'amount', align: 'left', width: 90,
		 			render : function(rowdata, rowindex, value) {
						return value ==null ? "" : formatNumber(value, 2, 1);
					}
		 		},  { 
		 			display: '金额', name: 'amount_money', align: 'right', width: 120,
		 			render : function(rowdata, rowindex, value) {
						return value ==null ? "" : formatNumber(value, '${p04005 }', 1);
					} 
		 		}, { 
		 			display: '确认日期', name: 'confirm_date', align: 'left', width: 90, formatter: "yyyy-MM-dd"
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
		 					 }else
		 						 if(rowdata.bus_type_code==21){
				 						return '<a href=javascript:openUpdate("' 
										+ rowdata.group_id
										+ ',' + rowdata.hos_id 
										+ ',' + rowdata.copy_code
										+ ',' + rowdata.in_id
										+ '")>'+rowdata.in_no+'</a>';
				 					 }else
				 						 if(rowdata.bus_type_code==15){
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
		 			display: '有效期', name: 'inva_date', align: 'left', width: 90, formatter: "yyyy-MM-dd"
		 		}],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatStorageQueryWorkDetail.do',
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
			url: 'hrp/mat/storage/out/outlibrary/matOutMainUpdatePage.do?isCheck=false&' + paras.toString(),
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
			url: 'hrp/mat/storage/in/updatePage.do?isCheck=false&' + paras.toString(),
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
   		
		var dates = getCurrentDate();
    	
    	var cur_date = dates.split(";")[2];

		//表头
	  	var heads = {
	  		rows: [
					{"cell":0,"value":"单位: ${sessionScope.hos_name}","colSpan":"3"},
					{"cell":2, "from":"right","align":"right","value":"库房: "+ liger.get("store_code").getText(),"colSpan":"3"},
					{"cell":0,"value":"统计日期: " + $("#startDate").val() +"至"+ $("#endDate").val(),"colSpan":"3","br":true}
	   		]
	  	};
		//表尾
		var foots = {
			rows: [
					{"cell":0,"value":"主管:","colSpan":"3"} ,
					{"cell":3,"value":"复核人:","colSpan":"3"},
					{"cell":1, "from":"right","align":"right","value":"制单人： ${sessionScope.user_name}","colSpan":"3"},
					{"cell":0,"value":"制单日期:","br":true} ,
					{"cell":1,"value":date} ,
	   		]
		}; 
  	  
		var printPara={
			title: "业务明细查询",//标题
			columns: JSON.stringify(grid.getPrintColumns()),//表头
			class_name: "com.chd.hrp.mat.service.storage.query.MatInDetailService",
			method_name: "queryMatStorageQueryWorkDetailPrint",
			bean_name: "matInDetailService", 
			heads: JSON.stringify(heads), //表头需要打印的查询条件,可以为空
			foots: JSON.stringify(foots),//表尾需要打印的查询条件,可以为空 
		};
		
		//执行方法的查询条件
		$.each(grid.options.parms,function(i,obj){
			printPara[obj.name]=obj.value;
		});
			
		officeGridPrint(printPara);
    }
   
    function loadDict(){ 
		//字典下拉框
	//	autocompleteAsyncMulti("#bus_type_code", "../../queryMatBusType.do?isCheck=false", "id", "text", true, true, '', false,'',160);
		
		autocompleteAsyncMulti("#bus_type_code", "../../queryMatBusType.do?isCheck=false", "id", "text", true, true,{read_or_write : 1},false,false,'180');
		
		autocomplete("#sup_id", "../../queryHosSupDict.do?isCheck=false", "id", "text", true, true,"",false,'',280);
		autocomplete("#fac_id", "../../queryHosFacDict.do?isCheck=false", "id", "text", true, true,"",false,'',160);
		//autocomplete("#dept_id", "../../queryMatDept.do?isCheck=false", "id", "text", true, true,{is_last  : 1},false,'',200);
		autocomplete("#dept_id", "../../queryMatDeptDictDate.do?isCheck=false", "id", "text", true, true,{is_last  : 1,read_or_write : 1},false,'',200);
		autoCompleteByData("#in_out", matIN_Out.Rows, "id", "text", true, true, "", false, false, '220');
		//autocomplete("#store_code", "../../queryMatStoreByRead.do?isCheck=false", "id", "text", true, true,"",true);
		autocomplete("#store_code", "../../queryMatStoreDictDate.do?isCheck=false", "id", "text", true, true,{read_or_write : 1},true);
		//autocomplete("#mat_type_code", "../../queryMatTypeDictCode.do?isCheck=false", "id", "text", true, true, "",false);
		autocomplete("#mat_type_code", "../../queryMatTypeDictDate.do?isCheck=false", "id", "text", true, true, {read_or_write : 1},false);
		autocomplete("#is_charge", "../../queryMatYearOrNo.do?isCheck=false", "id", "text", true, true);
		autocomplete("#inv_code", "../../queryMatInv.do?isCheck=false", "id", "text", true, true,'',false,'',280);
		autocomplete("#set_code", "../../queryMatVirStore.do?isCheck=false", "id", "text", true, true, '',false,'',240);
		$("#inv_code").ligerTextBox({width:280}); 
        $("#startDate").ligerTextBox({width:93});
        autodate("#startDate", "yyyy-MM-dd", "month_first");
        $("#endDate").ligerTextBox({width:93});
        autodate("#endDate", "yyyy-MM-dd", "month_last");
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
				<td align="right" class="l-table-edit-td" >物资类别：</td>
				<td align="left" class="l-table-edit-td"  ><input name="mat_type_code" type="text" id="mat_type_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
				<td align="right" class="l-table-edit-td"  >材料信息：</td>
				<td align="left" class="l-table-edit-td"  ><input name="inv_code" type="text" id="inv_code" ltype="text" validate="{required:false,maxlength:100}" /></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td">批&nbsp;&nbsp;号：</td>
				<td align="left" class="l-table-edit-td"><input name="batch_no" type="text" id="batch_no" ltype="text" required="true" validate="{required:true}" /></td>
				<td align="right" class="l-table-edit-td">材料名称:</td>
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
				<td align="right" class="l-table-edit-td">生成厂商：</td>
				<td align="left" class="l-table-edit-td"><input name="fac_id" type="text" id="fac_id" ltype="text" validate="{required:false}" /></td>
			    <td align="right" class="l-table-edit-td" width="10%">虚&nbsp;&nbsp;仓：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="set_code" type="text" id="set_code" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
			</tr>
			<tr>
			<td align="right" class="l-table-edit-td">入出库查询：</td>
				<td align="left" class="l-table-edit-td"><input name="in_out" type="text" id="in_out" ltype="text" required="true" validate="{required:true}" /></td>
				
			
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
			<div>物资类别：</div>
			<div>
				<input name="mat_type_code" type="text" id="mat_type_code" ltype="text" validate="{required:true,maxlength:20}" />
			</div>
		</div>
		 <div class="cell w1">
			<div>材料信息：</div>
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
		<div>材料名称:</div>
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
