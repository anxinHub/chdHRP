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
    var renderFunc = {
			price:function(value){//单价
				return formatNumber(value==null?0:value, '${p04006 }', 1);
			},
			money:function(value){//金额
				return formatNumber(value, '${p04005 }', 1);
			},
			amount:function(value){//数量
				return formatNumber(value==null?0:value,2,1);
			}
	}; 
    
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
		loadHotkeys();
		batch_no_Column();
		bar_code_Column();
    });
    //查询
    function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
		/* 
		if(liger.get("store_code").getValue() == null || liger.get("store_code").getValue() == undefined || liger.get("store_code").getValue() == ""){
			
			$.ligerDialog.error('仓库为必填项');
			return ; 
		} */
        //根据表字段进行添加查询条件
		grid.options.parms.push({name : 'store_id',value : liger.get("store_code").getValue().split(",")[0]}); 
		grid.options.parms.push({name : 'mat_type_id',value : liger.get("mat_type_code").getValue().split(",")[0]}); 
		grid.options.parms.push({name : 'inv_info',value : $("#inv_info").val()});
		grid.options.parms.push({name : 'batch_no',value : $("#batch_no").val()});
		grid.options.parms.push({name : 'bar_code',value : $("#bar_code").val()});
		grid.options.parms.push({name : 'type_level',value : $("#type_level").val()});
		grid.options.parms.push({name : 'is_charge',value : liger.get("is_charge").getValue()}); 
		grid.options.parms.push({name : 'show_batch',value : $("#show_batch").is(":checked") ? 1 : ''}); 
		grid.options.parms.push({name : 'show_bar',value : $("#show_bar").is(":checked") ? 1 : ''}); 
		grid.options.parms.push({name : 'only_bar',value : $("#only_bar").is(":checked") ? 1 : 0}); 
		grid.options.parms.push({name : 'zeroStore',value : $("#zeroStore").prop("checked")? '' : 1});
		grid.options.parms.push({name : 'is_com',value : $("#is_com").is(":checked") ? 1 : ''});
		grid.options.parms.push({name : 'sup_id',value : liger.get("sup_code").getValue().split(",")[0]}); 
		grid.options.parms.push({
			name : 'inv_model',//规格型号
			value : $("#inv_model").val()
		});
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [ { 
		 			display: '库房', name: 'store_name', align: 'left',frozen:true,width: '100'
		 		}, { 
		 			display: '材料编码', name: 'inv_code', align: 'left',frozen:true,width: '100'
		 		}, { 
		 			display: '材料名称', name: 'inv_name', align: 'left', width: '200',frozen:true
		 		}, /* { 
		 			display: '物资类别编码', name: 'mat_type_code', align: 'left', width: '140'
		 		}, { 
		 			display: '物资类别名称', name: 'mat_type_name', align: 'left', width: '140'
		 		},  */{ 
		 			display: '规格型号', name: 'inv_model', align: 'left', width: '150',frozen:true
		 		}, { 
		 			display: '生产厂商', name: 'fac_name', align: 'left', width: '200'
		 		}, { 
		 			display: '计量单位', name: 'unit_name', align: 'left', width: '60'
		 		},  { 
		 			display: '批号', name: 'batch_no', align: 'left', width: '120'
		 		}, { 
		 			display: '条形码', name: 'bar_code', align: 'left', width: '120'
		 		}, { 
		 			display: '供应商', name: 'sup_name', align: 'left', width: '200'
		 		}, { 
		 			display: '单价', name: 'price', align: 'right', width: '90',
		 			 render : function(rowdata, rowindex, value) {
							return formatNumber(rowdata.price ==null ? 0 : rowdata.price, '${p04006 }', 1);
						},formatter:'###,##0.00'
		 		}, { 
		 			display: '结存数量', name: 'amount', align: 'right', width: '100',
		 			 render : function(rowdata, rowindex, value) {
							return '<div style="font-weight: bold; font-size: 14px;">'+formatNumber(rowdata.amount ==null ? 0 : rowdata.amount, 2, 1)+'</div>';
					}
		 		}, { 
		 			display: '结存金额', name: 'money', align: 'right', width: '100',
		 			 render : function(rowdata, rowindex, value) {
						return formatNumber(rowdata.money ==null ? 0 : rowdata.money, '${p04005 }', 1);
					},formatter:'###,##0.00'
		 		}],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatStorageQueryInvBarRouting.do',
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
 		head=head+"<tr><td>仓库："+liger.get("store_code").getText().split(" ")[1]+"</td></tr>";
 		head=head+"</table>";
 		grid.options.lodop.head=head; 
 		grid.options.lodop.fn=renderFunc;
 		grid.options.lodop.title="条码查询";
    }
    
    //键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
	}
 
	//打印
	function print(){
    	var store_name = null;
    	if(grid.getData().length==0){
    		
			$.ligerDialog.error("请先查询数据！");
			
			return;
		}
    	if(liger.get("store_code").getValue()){
    		store_name=liger.get("store_code").getText().split(" ")[1];
    	}else{
    		store_name="空"
    	}
    	var heads={
        		"isAuto":true,//系统默认，页眉显示页码
        		"rows": [
    	          {"cell":0,"value":"仓库："},
    	          {"cell":1,"value":""+store_name}
        	]}; 
    	//表尾
    	var time=new Date();
    	var date=time.getFullYear()+"年"+(time.getMonth()+1)+"月"+time.getDate()+"日";
    	var foots = {
    			rows: [
    				{"cell":0,"value":"制表日期:"} ,
    				{"cell":1,"value":date} ,
    			]
    		}; 
    	var printPara={
          		title: "材料条码查询表",//标题
          		columns: JSON.stringify(grid.getPrintColumns()),//表头
          		class_name: "com.chd.hrp.mat.service.storage.query.MatInDetailService",
       			method_name: "queryMatStorageQueryInvBarRoutingPrint",
       			bean_name: "matInDetailService",
       			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
       			foots:JSON.stringify(foots),//表尾打印数据,可以为空

           	};
        	$.each(grid.options.parms,function(i,obj){
       			printPara[obj.name]=obj.value;
        	});
       		
        	officeGridPrint(printPara);
   		
    }
	
	function batch_no_Column(){
		
		var batch_no = $("#show_batch").is(":checked") ? 1 : 0;
		if(batch_no == 1){	
			
			 grid.toggleCol('batch_no', true)
		}else {
			 grid.toggleCol('batch_no', false)
		}
		query();
	}
	
	function bar_code_Column(){
		
		var bar_code = $("#show_bar").is(":checked") ? 1 : 0;
		if(bar_code == 1){	
			
			 grid.toggleCol('bar_code', true)
		}else {
			 grid.toggleCol('bar_code', false)
		}
		query();
	}
	function change_where(){
		
		 query();
	}
    function loadDict(){
		//字典下拉框
		
/* 		autocompleteAsync("#store_code", "../../queryMatStore.do?isCheck=false", "id", "text", true, true,"",true);
		autocomplete("#mat_type_code", "../../queryMatTypeDict.do?isCheck=false", "id", "text", true, true, "",false); */
		autocompleteAsync("#store_code", "../../queryMatStoreDictDate.do?isCheck=false", "id", "text", true, true,{is_write:1},true);
		autocomplete("#mat_type_code", "../../queryMatTypeDictDate.do?isCheck=false", "id", "text", true, true, {read_or_write:1},false);
		autocomplete("#is_charge", "../../queryMatYearOrNo.do?isCheck=false", "id", "text", true, true);
		autocomplete("#sup_code", "../../queryHosSup.do?isCheck=false", "id", "text", true, true,'',false,'',280);
		//autocomplete("#inv_code", "../../queryMatInv.do?isCheck=false", "id", "text", true, true);
        $("#batch_no").ligerTextBox({width:160});
        $("#bar_code").ligerTextBox({width:160});
        $("#inv_info").ligerTextBox({width:160});
        $("#inv_model").ligerTextBox({width:160});
        $("#sup_code").ligerTextBox({width:160});
	}  
	
	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
		<tr>
			<td align="right" class="l-table-edit-td" >
				仓库：
			</td>
			<td align="left" class="l-table-edit-td" >
				<input name="store_code" type="text" id="store_code" ltype="text" validate="{required:false}" />
			</td>
			
			<td align="right" class="l-table-edit-td" >
				物资类别：
			</td>
			<td align="left" class="l-table-edit-td" >
				<input name="mat_type_code" type="text" id="mat_type_code" ltype="text" validate="{required:true,maxlength:20}" />
			</td>
			
			<td align="right" class="l-table-edit-td" >
				材料信息：
			</td>
			<td align="left" class="l-table-edit-td" >
				<input name="inv_info" type="text" id="inv_info" ltype="text" validate="{required:false,maxlength:100}" />
			</td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" >
				批号：
			</td>
			<td align="left" class="l-table-edit-td" >
				<input name="batch_no" type="text" id="batch_no" ltype="text" required="true" validate="{required:true}" />
			</td>
			
			<td align="right" class="l-table-edit-td" >
				条形码：
			</td>
			<td align="left" class="l-table-edit-td" >
				<input name="bar_code" type="text" id="bar_code" ltype="text" required="true" validate="{required:true}" />
			</td>
			
			<td align="right" class="l-table-edit-td" >
				供应商信息：
			</td>
			<td align="left" class="l-table-edit-td" >
				<input name="sup_code" type="text" id="sup_code" ltype="text" validate="{required:true,maxlength:20}" />
			</td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" >规格型号:</td>
			 <td align="left" class="l-table-edit-td" >
				<input name="inv_model" type="text" id="inv_model" ltype="text"    validate="{required:true}" />
			</td>
			
			<td align="right" class="l-table-edit-td" >
				是否收费：
			</td>
			<td align="left" class="l-table-edit-td" >
				<input name="is_charge" type="text" id="is_charge" ltype="text" validate="{required:true,maxlength:20}" />
			</td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" >
				查询：
			</td>
			<td align="left" class="l-table-edit-td" colspan="5">
				<input name="show_batch" type="checkbox" id="show_batch" ltype="text" checked="checked" onclick="batch_no_Column()"/> 按批查询 
				&nbsp;&nbsp; 
				<input name="show_bar" type="checkbox" id="show_bar" ltype="text" checked="checked" onclick="bar_code_Column()"/> 按条码查询 
				&nbsp;&nbsp; 
				<input name="only_bar" type="checkbox" id="only_bar" ltype="text" checked="checked" onclick="change_where()"/>只显示条码材料 
				&nbsp;&nbsp; 
				<input name="zeroStore" type="checkbox" id="zeroStore" ltype="text"  onclick="change_where()"/> 显示零库存 
				&nbsp;&nbsp; 
				<input name="is_com" type="checkbox" id="is_com" checked="checked" ltype="text" onclick="change_where()"/>包含代销 
				&nbsp;&nbsp; 
			</td>
		</tr>
	</table>
	<div id="maingrid"></div>
</body>
</html>
