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
    var renderFunc = {
			price:function(value){//单价
				return formatNumber(value, '${p04006 }', 1);
			},
			amount_money:function(value){//金额
				return formatNumber(value, '${p04005 }', 1);
			},
			amount:function(value){//数量 
				return formatNumber(value ==null ? 0 : value);
			}
	}; 
    
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
		loadHotkeys();
		$("#store_type_td").find(":radio").click(function(){
			var store_type = $("#store_type_td").find(":radio:checked").val();
			if(store_type == 1){
				$("#store_label").text("虚仓");
				autocompleteAsync("#store_code", "../../queryMatVirStore.do?isCheck=false", "id", "text", true, true, "", true);
			}else{
				$("#store_label").text("仓库");
				autocompleteAsync("#store_code", "../../queryMatStoreDictDate.do?isCheck=false", "id", "text", true, true, {read_or_write:1}, true);
			}
		});
    });
    //查询
    function  query(){
   		grid.options.parms=[];
		grid.options.newPage=1;
        //根据表字段进行添加查询条件
		grid.options.parms.push({name : 'begin_confirm_date',value : $("#begin_confirm_date").val()});
		grid.options.parms.push({name : 'end_confirm_date',value : $("#end_confirm_date").val()});
		//grid.options.parms.push({name : 'store_id',value : liger.get("store_code").getValue().split(",")[0]}); 
		grid.options.parms.push({name : 'store_no',value : liger.get("store_code").getValue().split(",")[1]}); 
		grid.options.parms.push({name : 'mat_type_id',value : liger.get("mat_type_code").getValue().split(",")[0]}); 
		grid.options.parms.push({name : 'mat_type_no',value : liger.get("mat_type_code").getValue().split(",")[1]});
		grid.options.parms.push({name : 'begin_in_date',value : $("#begin_in_date").val()});
		grid.options.parms.push({name : 'end_in_date',value : $("#end_in_date").val()});
		grid.options.parms.push({name : 'sup_id',value : liger.get("sup_code").getValue().split(",")[0]}); 
		grid.options.parms.push({name : 'sup_no',value : liger.get("sup_code").getValue().split(",")[1]});
		grid.options.parms.push({name : 'inv_id',value : liger.get("inv_code").getValue().split(",")[0]}); 
		grid.options.parms.push({name : 'inv_no',value : liger.get("inv_code").getValue().split(",")[1]});
		grid.options.parms.push({name : 'is_charge',value : liger.get("is_charge").getValue()}); 
		grid.options.parms.push({
			name : 'store_type',
			value : $("#store_type_td").find(":radio:checked").val()
		}); 
		grid.options.parms.push({
			name : 'store_id',
			value : liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[0]
		}); 
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
		 			display: '交易编码', name: 'bid_code', align: 'left', minWidth: '150'
		 		},{ 
		 			display: '存储编码', name: 'memory_encoding', align: 'left', minWidth: '150'
		 		},{ 
		 			display: '供应商编码', name: 'sup_code', align: 'left', minWidth: '150'
		 		}, { 
		 			display: '供应商名称', name: 'sup_name', align: 'left', minWidth: '80'
		 		}, { 
		 			display: '生产厂商编码', name: 'fac_code', align: 'left', minWidth: '80'
		 		}, { 
		 			display: '生产厂商名称', name: 'fac_name', align: 'left', minWidth: '80'
		 		},  { 
		 			display: '入库日期', name: 'confirm_date', align: 'left', minWidth: '80'
		 		},  { 
		 			display: '材料编码', name: 'inv_code', align: 'left', minWidth: '80'
		 		},  { 
		 			display: '材料名称', name: 'inv_name', align: 'left', minWidth: '80'
		 		},  { 
		 			display: '规格型号', name: 'inv_model', align: 'left', minWidth: '80'
		 		},  { 
		 			display: '计量单位', name: 'unit_name', align: 'left', minWidth: '80'
		 		},  { 
		 			display: '进价', name: 'price', align: 'left', minWidth: '80',
		 			render : function(rowdata, rowindex, value) {
						return formatNumber(rowdata.price ==null ? 0 : rowdata.price, '${p04005 }', 1);
					},formatter:"###,##0.00"
		 		},  { 
		 			display: '数量', name: 'amount', align: 'left', minWidth: '80'
		 		},  { 
		 			display: '金额', name: 'amount_money', align: 'right', minWidth: '100',
		 			render : function(rowdata, rowindex, value) {
						return formatNumber(rowdata.amount_money ==null ? 0 : rowdata.amount_money, '${p04005 }', 1);
					},formatter:"###,##0.00"
		 		},{ 
		 			display: '材料分类', name: 'mat_type_name', align: 'left', minWidth: '90'
		 		}],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatStorageQueryMatInSupDetail.do',
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
 		head=head+"<tr><td>制单日期："+ $("#begin_in_date").val() +" 至  "+ $("#end_in_date").val()+"</td></tr>";
 		head=head+"</table>";
 		grid.options.lodop.head=head; 
 		grid.options.lodop.fn=renderFunc;
 		grid.options.lodop.title="供应商采购明细查询";
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
    	          {"cell":0,"value":"入库日期："},
    	          {"cell":1,"value":""+liger.get("begin_confirm_date").getValue()+"至"+liger.get("end_confirm_date").getValue()},
    	          {"cell":3,"value":""+$("#store_type_td").find(":radio:checked").val()==1?'虚仓':'仓库'+"："},
    	          {"cell":4,"value":""+liger.get("store_code").getText()==''?' ':liger.get("store_code").getText().split(" ")[1]+""}
        	]}; 
    	//表尾
		var foots = {
			rows: [
				{"cell":0,"value":"制单日期:"} ,
				{"cell":1,"value":date} ,
			]
		}; 
    	var printPara={
          		title: "供应商采购明细查询表",//标题
          		columns: JSON.stringify(grid.getPrintColumns()),//表头
          		class_name: "com.chd.hrp.mat.service.storage.query.MatInDetailService",
       			method_name: "queryMatStorageQueryMatInSupDetailPrint",
       			bean_name: "matInDetailService",
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
		//第一次加载显示虚仓
        autocompleteAsync("#store_code", "../../queryMatVirStore.do?isCheck=false", "id", "text", true, true, "", true);
		//autocomplete("#store_code", "../../queryMatStoreByRead.do?isCheck=false", "id", "text", true, true,"",true);
		//autocomplete("#store_code", "../../queryMatStoreDictDate.do?isCheck=false", "id", "text", true, true,{read_or_write : 1},true);
		//autocomplete("#mat_type_code", "../../queryMatTypeDict.do?isCheck=false", "id", "text", true, true, {is_last : 1},false,'',280);
		autocomplete("#mat_type_code", "../../queryMatTypeDictDate.do?isCheck=false", "id", "text", true, true, {is_last : 1,read_or_write : 1},false,'',280);
		autoCompleteByData("#state", matInMain_state.Rows, "id", "text", true, true);
		autocompleteAsync("#bus_type_code", "../../queryMatBusType.do?isCheck=false", "id", "text", true, true, {sel_flag : 'in'}, true);
		autocomplete("#sup_code", "../../queryHosSupDict.do?isCheck=false", "id", "text", true, true);
	/* 	autocomplete("#inv_code", "../../queryMatInv.do?isCheck=false", "id", "text", true, true,'',false,'',280); */
		autocomplete("#is_charge", "../../queryMatYearOrNo.do?isCheck=false", "id", "text", true, true);
		$("#mat_type_code").ligerTextBox({width:280});
		$("#inv_code").ligerTextBox({width:280});
		
        $("#begin_confirm_date").ligerTextBox({width:110});
        //autodate("#begin_confirm_date", "yyyy-mm-dd");
        $("#end_confirm_date").ligerTextBox({width:110});
        //autodate("#end_confirm_date", "yyyy-mm-dd");
        $("#begin_in_date").ligerTextBox({width:110});
        autodate("#begin_confirm_date", "yyyy-mm-dd", "month_first");
        $("#end_in_date").ligerTextBox({width:110});
        autodate("#end_confirm_date", "yyyy-mm-dd", "month_last");
        $("#inv_model").ligerTextBox({width:240});
	}  
	
	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
	<div class="search-block clearfix">
	<table>
		<tr>
			<td align="right" class="l-table-edit-td">制单日期： </td>
			<td align="left" class="l-table-edit-td"  width="20%">
				<table>
					<tr>
						<td>
							<input class="Wdate" name="begin_in_date" id="begin_in_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
						<td align="right" class="l-table-edit-td"  > 至 </td>
						<td>
							<input class="Wdate" name="end_in_date" id="end_in_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
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
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="store_code" type="text" id="store_code" ltype="text" validate="{required:false}" />
            </td>
            
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td">入库日期： </td>
			<td align="left" class="l-table-edit-td"  width="20%">
				<table>
					<tr>
						<td>
							<input class="Wdate" name="begin_confirm_date" id="begin_confirm_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
						<td align="right" class="l-table-edit-td"  > 至 </td>
						<td>
							<input class="Wdate" name="end_confirm_date" id="end_confirm_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
            		</tr>
				</table>
	        </td>
	         <td align="right" class="l-table-edit-td"  width="10%">供应商： </td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="sup_code" type="text" id="sup_code" ltype="text" validate="{required:false}" />
            </td>
           <td align="right" class="l-table-edit-td"  width="10%"> 物资类别： </td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="mat_type_code" type="text" id="mat_type_code" ltype="text" validate="{required:true,maxlength:20}" />
            </td>
            
		</tr>
		<tr>
			
            <td align="right" class="l-table-edit-td"  width="10%"> 规格型号:</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="inv_model" type="text" id="inv_model" ltype="text"  required="true"  validate="{required:true}" />
            </td>
            <td align="right" class="l-table-edit-td"  width="10%">是否收费： </td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="is_charge" type="text" id="is_charge" ltype="text" validate="{required:true,maxlength:20}" />
            </td>
             <td align="right" class="l-table-edit-td"  width="10%"> 材料信息： </td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="inv_code" type="text" id="inv_code" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
		</tr>
	</table>
		<!-- <div class="cell w1">
			<div> 制单日期：</div>
			<div>
				<input class="Wdate" name="begin_in_date" id="begin_in_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
				至
				<input class="Wdate" name="end_in_date" id="end_in_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
			</div>
		</div>
	    <div class="cell w1">
			<div>仓&nbsp;&nbsp;库：</div>
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
			<div>入库日期：</div>
			<div>
				<input class="Wdate" name="begin_confirm_date" id="begin_confirm_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
				至
				<input class="Wdate" name="end_confirm_date" id="end_confirm_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
			</div>
		</div>
		
		<div class="cell w1">
			<div>供应商：</div>
			<div>
				<input name="sup_code" type="text" id="sup_code" ltype="text" validate="{required:false}" />
			</div>
		</div>
		<div class="cell w1">
			<div>材料信息：</div>
			<div>
				<input name="inv_code" type="text" id="inv_code" ltype="text" validate="{required:false,maxlength:100}" />
			</div>
		</div>
		<div class="cell w1">
			<div>是否收费：</div>
			<div>
				<input name="is_charge" type="text" id="is_charge" ltype="text" validate="{required:true,maxlength:20}" />

			</div>
		</div>
		<div class="cell w1">
		<div>规格型号:</div>
		<div>
			<input name="inv_model" type="text" id="inv_model" ltype="text"  required="true"  validate="{required:true}" />
		</div>
		
		</div > -->
	</div>
	<div id="maingrid"></div>
</body>
</html>
