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
			price:function(value){//单价
				return formatNumber(value, '${p08005 }', 1);
			},
			amount_money:function(value){//金额
				return formatNumber(value, '${p08005 }', 1);
			},
			amount:function(value){//数量
				return formatNumber(value==null?0:value);
			}
	}; 
    
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
		grid.options.parms.push({name : 'end_confirm_date',value : $("#end_confirm_date").val()});
		grid.options.parms.push({name : 'store_id',value : liger.get("store_code").getValue().split(",")[0]}); 
		grid.options.parms.push({name : 'store_no',value : liger.get("store_code").getValue().split(",")[1]}); 
		//grid.options.parms.push({name : 'med_type_id',value : liger.get("med_type_code").getValue().split(",")[0]}); 
		//grid.options.parms.push({name : 'med_type_no',value : liger.get("med_type_code").getValue().split(",")[1]});
		grid.options.parms.push({name : 'med_type_code',value : liger.get("med_type_code").getText().split(" ")[0]});
		grid.options.parms.push({name : 'begin_in_date',value : $("#begin_in_date").val()});
		grid.options.parms.push({name : 'end_in_date',value : $("#end_in_date").val()});
		grid.options.parms.push({name : 'bill_no',value : $("#bill_no").val()});
		grid.options.parms.push({name : 'sup_id',value : liger.get("sup_code").getValue().split(",")[0]}); 
		grid.options.parms.push({name : 'sup_no',value : liger.get("sup_code").getValue().split(",")[1]});
	 	grid.options.parms.push({name : 'inv_id',value : liger.get("inv_code").getValue().split(",")[0]}); 
		grid.options.parms.push({name : 'inv_no',value : liger.get("inv_code").getValue().split(",")[1]});
		grid.options.parms.push({name : 'in_no',value : $("#in_no").val()});
		grid.options.parms.push({name : 'state',value : liger.get("state").getValue()});
		grid.options.parms.push({name : 'cert_code',value : liger.get("cert_code").getValue()});
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
					display: '入库单号', name: 'in_no', align: 'left', minWidth: '140',
					render : function(rowdata, rowindex, value) {
						if(value == '合计'){
							return value;
						}else{
							return '<a href=javascript:in_open("' 
								+ rowdata.group_id 
								+ ',' + rowdata.hos_id 
								+ ',' + rowdata.copy_code 
								+ ',' + rowdata.in_id
								+ '")>'+rowdata.in_no+'</a>';
						}
					}
				}, { 
		 			display: '入库日期', name: 'confirm_date', align: 'left', minWidth: '150'
		 		}, { 
		 			display: '业务类型', name: 'bus_type_name', align: 'left', minWidth: '80'
		 		}, { 
		 			display: '发票号', name: 'bill_no', align: 'left', minWidth: '120'
		 		}, { 
		 			display: '供应商编码', name: 'sup_code', align: 'left', minWidth: '150'
		 		}, { 
		 			display: '供应商名称', name: 'sup_name', align: 'left', minWidth: '80'
		 		}, { 
		 			display: '状态', name: 'field_desc', align: 'left', minWidth: '80'
		 		},  { 
		 			display: '交易编码', name: 'bid_code', align: 'left', minWidth: '80'
		 		},  { 
		 			display: '药品名称', name: 'inv_name', align: 'left', minWidth: '80'
		 		},  { 
		 			display: '药品编码', name: 'inv_code', align: 'left', minWidth: '80'
		 		},  { 
		 			display: '规格型号', name: 'inv_model', align: 'left', minWidth: '80'
		 		},  { 
		 			display: '计量单位', name: 'unit_name', align: 'left', minWidth: '80'
		 		},  { 
		 			display: '单价', name: 'price', align: 'left', minWidth: '80',
		 			render : function(rowdata, rowindex, value) {
		 				if(rowdata.price == null || rowdata.price == '' || rowdata.price == undefined){
		 					return "";
		 				}
						return formatNumber(rowdata.price ==null ? 0 : rowdata.price, '${p08005 }', 1);
					}
		 		},  { 
		 			display: '数量', name: 'amount', align: 'left', minWidth: '80'
		 		},  { 
		 			display: '金额', name: 'amount_money', align: 'right', minWidth: '100',
		 			render : function(rowdata, rowindex, value) {
						return formatNumber(rowdata.amount_money ==null ? 0 : rowdata.amount_money, '${p08005 }', 1);
					}
		 		}, { 
		 			display: '采购部门', name: 'stocker_dept_name', align: 'left', minWidth: '90'
		 		}, { 
		 			display: '采购员', name: 'stocker_name', align: 'left', minWidth: '80'
		 		}, { 
		 			display: '药品分类', name: 'med_type_name', align: 'left', minWidth: '90'
		 		}, { 
		 			display: '注册证号', name: 'cert_code', align: 'left', minWidth: '130'
		 		}, { 
		 			display: '有效期', name: 'inva_date', align: 'left', minWidth: '90'
		 		}],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMedStorageQueryInDetail.do',
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
 		grid.options.lodop.title="入库明细查询";
    }
    
    //键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
		hotkeys('P', print);
	}
    
	//打开修改页面
	function in_open(obj){		
		var vo = obj.split(",");
		var paras = 
			"group_id="+vo[0] +"&"+ 
			"hos_id="+vo[1] +"&"+ 
			"copy_code="+vo[2] +"&"+ 
			"in_id="+vo[3] +"&"+ 
			"in_no="+vo[4];
		parent.$.ligerDialog.open({
			title: '入库单修改',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/med/storage/in/updatePage.do?isCheck=false&' + paras.toString(),
			modal: true, showToggle: false, showMax: true, showMin: true, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});   
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
   			title:'入库明细查询',
   			head:[
				{"cell":0,"value":"单位: ${sessionScope.hos_name}","colspan":colspan_num,"br":true},
				{"cell":0,"value":"统计日期: " + $("#begin_in_date").val() +" 至  "+ $("#end_in_date").val(),"colspan":colspan_num,"br":true}
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
   		ajaxJsonObjectByUrl("queryMedStorageQueryInDetail.do?isCheck=false", selPara, function (responseData) {
   			printGridView(responseData,printPara);
		});

   		
    }
   
    function loadDict(){
		//字典下拉框
		//autocomplete("#store_code", "../../queryMedStoreByRead.do?isCheck=false", "id", "text", true, true,"",true);
		autocomplete("#store_code", "../../queryMedStoreDictDate.do?isCheck=false", "id", "text", true, true,{read_or_write : 1},true);
		//autocomplete("#med_type_code", "../../queryMedTypeDict.do?isCheck=false", "id", "text", true, true, {is_last : 1},false,'',280);
		autocomplete("#med_type_code", "../../queryMedTypeDictDate.do?isCheck=false", "id", "text", true, true, {is_last : 1,read_or_write : 1},false,'',280);
		autoCompleteByData("#state", medInMain_state.Rows, "id", "text", true, true);
		autocompleteAsync("#bus_type_code", "../../queryMedBusType.do?isCheck=false", "id", "text", true, true, {sel_flag : 'in'}, true);
		autocomplete("#sup_code", "../../queryHosSupDict.do?isCheck=false", "id", "text", true, true,'',false,'',280);
	/* 	autocomplete("#inv_code", "../../queryMedInv.do?isCheck=false", "id", "text", true, true,'',false,'',280); */
		
		$("#med_type_code").ligerTextBox({width:280});
		$("#sup_code").ligerTextBox({width:280});
		$("#inv_code").ligerTextBox({width:280});
		
        $("#begin_confirm_date").ligerTextBox({width:110});
        //autodate("#begin_confirm_date", "yyyy-mm-dd", "month_first");
        $("#end_confirm_date").ligerTextBox({width:110});
        //autodate("#end_confirm_date", "yyyy-mm-dd", "month_last");
        $("#begin_in_date").ligerTextBox({width:110});
        autodate("#begin_confirm_date", "yyyy-mm-dd", "month_first");
        $("#end_in_date").ligerTextBox({width:110});
        autodate("#end_confirm_date", "yyyy-mm-dd", "month_last");
        
        $("#bill_no").ligerTextBox({width:160});
        $("#in_no").ligerTextBox({width:240});
        $("#inv_model").ligerTextBox({width:240});
        $("#cert_code").ligerTextBox({width:160});
	}  
	
	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" >
        <tr>
        	<td align="right" class="l-table-edit-td"  width="10%">
            	制单日期：
            </td>
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
	        
	        <td align="right" class="l-table-edit-td"  width="10%">
				仓库：
			</td>
			<td align="left" class="l-table-edit-td" width="20%">
            	<input name="store_code" type="text" id="store_code" ltype="text" validate="{required:false}" />
            </td>
            
            <td align="right" class="l-table-edit-td"  width="10%">
				药品类别：
			</td>
			<td align="left" class="l-table-edit-td" width="20%">
            	<input name="med_type_code" type="text" id="med_type_code" ltype="text" validate="{required:false}" />
            </td>
        </tr> 
        <tr>
        	<td align="right" class="l-table-edit-td"  width="10%">
            	入库日期：
            </td>
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
	        
	        <td align="right" class="l-table-edit-td" width="10%">发票号：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="bill_no" type="text" id="bill_no" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
            
	        <td align="right" class="l-table-edit-td"  width="10%">
				供应商：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="sup_code" type="text" id="sup_code" ltype="text" validate="{required:false}" />
            </td>
            
        </tr>
        <tr>
        	<td align="right" class="l-table-edit-td" width="10%">
				入库单号：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="in_no" type="text" id="in_no" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
            
            <td align="right" class="l-table-edit-td"  width="10%">状态：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="state" type="text" id="state" ltype="text" validate="{required:false}" />
            </td>
            
            <td align="right" class="l-table-edit-td" width="10%">药品信息：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="inv_code" type="text" id="inv_code" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
            
        </tr>
        <tr>
        	<td align="right" class="l-table-edit-td" width="10%">
				规格型号：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="inv_model" type="text" id="inv_model" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
            <td align="right" class="l-table-edit-td" width="10%">
				注册证号：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="cert_code" type="text" id="cert_code" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
        </tr>
    </table>
	<!-- <div class="search-block clearfix">
		<div class="cell w1">
			<div> 制单日期：</div>
			<div>
				<input class="Wdate" name="begin_in_date" id="begin_in_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
				至
				<input class="Wdate" name="end_in_date" id="end_in_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
			</div>
		</div>
	    <div class="cell w1">
			<div>&nbsp;仓 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;库：</div>
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
			<div>入库日期：</div>
			<div>
				<input class="Wdate" name="begin_confirm_date" id="begin_confirm_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
				至
				<input class="Wdate" name="end_confirm_date" id="end_confirm_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
			</div>
		</div>
	     
		<div class="cell w1">
			<div>&nbsp;发&nbsp;票&nbsp;号：</div>
			<div>
				<input name="bill_no" type="text" id="bill_no" ltype="text" required="true" validate="{required:true,maxlength:100}" />
			</div>
		</div>
		<div class="cell w1">
			<div> &nbsp;供&nbsp;应&nbsp;商：</div>
			<div>
				<input name="sup_code" type="text" id="sup_code" ltype="text" validate="{required:false}" />
			</div>
		</div>
		
		<div class="cell w1">
			<div>入库单号：</div>
			<div>
				<input name="in_no" type="text" id="in_no" ltype="text" validate="{required:false,maxlength:100}" />
			</div>
		</div>
		<div class="cell w1">
			<div>&nbsp;&nbsp;状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态：</div>
			<div>
				<input name="state" type="text" id="state" ltype="text" validate="{required:false}" />
			</div>
		</div>
		<div class="cell w1">
			<div>药品信息：</div>
			<div>
				<input name="inv_code" type="text" id="inv_code" ltype="text" validate="{required:false,maxlength:100}" />
			</div>
		</div>
		<div class="cell w1">
		<div>规格型号:</div>
		<div>
			<input name="inv_model" type="text" id="inv_model" ltype="text"    validate="{required:true}" />
		</div>
		
		</div >
	</div> -->
	<div id="maingrid"></div>
</body>
</html>
