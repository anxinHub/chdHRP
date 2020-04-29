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
    		amount:function(value){//数量
				return formatNumber(value, 2, 1);
			},
			price:function(value){//单价
				return formatNumber(value, '${p04006}', 1);
			},
			amount_money:function(value){//金额
				return formatNumber(value, '${p04005}', 1);
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
    	var begin_out_date = $("#begin_out_date").val();
    	if(begin_out_date == ''){
    		$.ligerDialog.warn('开始期间不能为空');
    		return;
    	}
    	var end_out_date = $("#end_out_date").val();
    	if(end_out_date == ''){
    		$.ligerDialog.warn('结束期间不能为空');
    		return ; 
    	}
    	
    	/* if(begin_out_date.substring(0,4) != end_out_date.substring(0,4)){
    		$.ligerDialog.warn('不支持跨年查询');
    		return;
    	} */
    	if(begin_out_date> end_out_date){
    		$.ligerDialog.warn('开始期间不能大于结束期间');
    		return ; 
    	}
     	
		grid.options.parms=[];
		grid.options.newPage=1;
        //根据表字段进行添加查询条件
		grid.options.parms.push({name : 'begin_out_date',value : $("#begin_out_date").val()});
		grid.options.parms.push({name : 'end_out_date',value : $("#end_out_date").val()});
		grid.options.parms.push({name : 'store_id',value : liger.get("store_code").getValue().split(",")[0]});
		var is_com = $("#is_com input:radio:checked").val();
		grid.options.parms.push({name : 'is_com', value : is_com}); 
		grid.options.parms.push({name : 'sup_id',value : liger.get("sup_code").getValue().split(",")[0]}); 
		grid.options.parms.push({name : 'inv_code', value : $("#inv_code").val()});
		grid.options.parms.push({name : 'bed_no', value : $("#bed_no").val()});
		var inv_sup = $("#inv_sup input:radio:checked").val();
		grid.options.parms.push({name : 'inv_sup', value : inv_sup}); 
		
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [{ 
		 		display: '供应商', name: 'sup_name', align: 'left', width: 180, 
		 		render : function(rowdata, rowindex, value) {
		 			if(value == "合计" || rowdata.store_name == "供应商合计"){
		 				return "<span style='font-size: 14px;font-weight: bold;'>"+value+"</span>"
		 			}else{
		 				return value;
		 			}
				}
		 	}, { 
		 		display: '库房', name: 'store_name', align: 'left', width: 100, 
		 		render : function(rowdata, rowindex, value) {
		 			if(value == "供应商合计"){
		 				return "<span style='font-size: 14px;font-weight: bold;'>"+value+"</span>"
		 			}else{
		 				return value;
		 			}
				}
		 	}, { 
		 		display: '材料编码', name: 'inv_code', align: 'left', width: 100
		 	},  { 
		 		display: '材料名称', name: 'inv_name', align: 'left', width: 180, 
		 		render : function(rowdata, rowindex, value) {
		 			if(value == "材料小计"){
		 				return "<span style='font-size: 14px;font-weight: bold;'>"+value+"</span>"
		 			}else{
		 				return value;
		 			}
				}
		 	}, { 
		 		display: '规格型号', name: 'inv_model', align: 'left', width: 180
		 	}, {
		 		display: '计量单位', name: 'unit_name', align: 'left', width: 70
		 	}, {
		 		display: '生产厂商', name: 'fac_name', align: 'left', width: 200
		 	}, {
		 		display: '出库日期', name: 'out_date', align: 'left', width: 90
		 	}, {
				display: '单价', name: 'price', align: 'left', width: 90,
		 		render : function(rowdata, rowindex, value) {
					return value == null ? "" : formatNumber(value,'${p04006}', 1);
				}
		 	}, { 
		 		display: '数量', name: 'amount', align: 'left', width: 80,
		 		render : function(rowdata, rowindex, value) {
		 			if(rowdata.sup_name == "合计" || rowdata.store_name == "供应商合计" || rowdata.inv_name == "材料小计" ){
		 				return "<span style='font-size: 14px;font-weight: bold;'>"+(value == null ? "" : formatNumber(value, 2, 1))+"</span>"
		 			}
					return value == null ? "" : formatNumber(value, 2, 1);
				}
		 	}, {
			 	display: '金额', name: 'amount_money', align: 'right', width: 100,
				render : function(rowdata, rowindex, value) {
		 			if(rowdata.sup_name == "合计" || rowdata.store_name == "供应商合计" || rowdata.inv_name == "材料小计" ){
		 				return "<span style='font-size: 14px;font-weight: bold;'>"+(value == null ? "" : formatNumber(value, 2, 1))+"</span>"
		 			}
					return value == null ? "" : formatNumber(value, '${p04005}', 1);
				},formatter:"###,##0.00"
		 	}, {
		 		display: '住院号码', name: 'bed_no', align: 'left', width: 90
		 	} ],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatAccountBalanceChargeInvCollectByHospital.do',
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
    	var head="<table class='head' width='100%'><tr><td>单位：${hos_name}</td></tr>";
 		head=head+"<tr><td>查询期间："+$("#begin_out_date").val()+"至"+$("#end_out_date").val()+"</td></tr>";
 		head=head+"</table>";
 		grid.options.lodop.head=head; 
 		grid.options.lodop.fn=renderFunc;
 		grid.options.lodop.title= liger.get("store_code").getText() + "计费材料月结表";
    }
    
    
    //键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
	}
 
    function loadDict(){
		//字典下拉框
//		autocompleteAsync("#store_code", "../../queryMatStore.do?isCheck=false", "id", "text", true, true, "", true);
		autocompleteAsync("#store_code", "../../queryMatStoreDictDate.do?isCheck=false", "id", "text", true, true,{is_write : 1}, true);
		autocomplete("#sup_code", "../../queryHosSupDict.do?isCheck=false", "id", "text", true, true, "", false, false, '222');
        $("#begin_out_date").ligerTextBox({width:100});
        autodate("#begin_out_date", "yyyy-MM-dd", "month_first");
        $("#end_out_date").ligerTextBox({width:100});
        autodate("#end_out_date", "yyyy-MM-dd", "month_last");
        $("#bed_no").ligerTextBox({width:160});
        $("#inv_code").ligerTextBox({width:222});
	}
    
  	//打印
	function print(){
    	
    	if(grid.getData().length==0){
    		
			$.ligerDialog.warn("请先查询数据！");
			
			return;
		}
    	
    	var heads={
        		"isAuto":true,//系统默认，页眉显示页码
        		"rows": [
    	          {"cell":0,"value":"统计年月："},
    	          {"cell":1,"value":""+$("#begin_out_date").val()+"至"+$("#end_out_date").val()},
    	          {"cell":3,"value":"仓库："},
    	          {"cell":4,"value":""+liger.get("store_code").getText()==''?'空':liger.get("store_code").getText().split(" ")[1]+""}
        	]}; 
    	var foots = {
    			rows: [
    				{"cell":0,"value":"制单日期:"} ,
    				{"cell":1,"value":date} ,
    			]
    		}; 
    	var printPara={
          		title: "计费材料月结表",//标题
          		columns: JSON.stringify(grid.getPrintColumns()),//表头
          		class_name: "com.chd.hrp.mat.service.account.balance.MatAccountBalanceChargeInvService",
       			method_name: "queryMatAccountBalanceChargeInvCollectByHospitalPrint",
       			bean_name: "matAccountBalanceChargeInvService",
       			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
       			foots: JSON.stringify(foots),//表尾需要打印的查询条件,可以为空
       			
           	};
        	$.each(grid.options.parms,function(i,obj){
       			printPara[obj.name]=obj.value;
        	});
       		
        	officeGridPrint(printPara);

   		
    }
	
	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
	<div class="search-block clearfix">
	
	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" style="padding: 10px" border="0">
		<tr>
			<td align="right" class="l-table-edit-td">查询期间： </td>
			<td align="left" class="l-table-edit-td" >
				<table>
					<tr>
						<td>
							<input class="Wdate" name="begin_out_date" id="begin_out_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
						<td align="right" class="l-table-edit-td"  > 至 </td>
						<td>
							<input class="Wdate" name="end_out_date" id="end_out_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
            		</tr>
				</table>
	        </td>
	        <td align="right" class="l-table-edit-td" > 仓库： </td>
            <td align="left" class="l-table-edit-td" >
            	<input name="store_code" type="text" id="store_code" ltype="text" validate="{required:false}" />
            </td>
            
        	<td align="right" class="l-table-edit-td" >
				查询类别：
			</td>
            <td id="is_com" align="left" class="l-table-edit-td">
				<input type="radio" name="is_com" value="0" checked="checked" />常备
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    			<input type="radio" name="is_com" value="1" />代销
			</td>
		</tr> 
		<tr>
	        <td align="right" class="l-table-edit-td" > 供应商： </td>
            <td align="left" class="l-table-edit-td" >
            	<input name="sup_code" type="text" id="sup_code" ltype="text" validate="{required:false}" />
            </td>
            
	        <td align="right" class="l-table-edit-td" > 住院号码： </td>
            <td align="left" class="l-table-edit-td" >
            	<input name="bed_no" type="text" id="bed_no" ltype="text" validate="{required:false}" />
            </td>

	        <td align="right" class="l-table-edit-td" > 材料信息： </td>
            <td align="left" class="l-table-edit-td" >
            	<input name="inv_code" type="text" id="inv_code" ltype="text" validate="{required:false}" />
            </td>
		</tr>
		<tr>
        	<td align="right" class="l-table-edit-td" >
				供应商类别：
			</td>
            <td id="inv_sup" align="left" class="l-table-edit-td">
				<input type="radio" name="inv_sup" value="0" />入库供应商
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    			<input type="radio" name="inv_sup" value="1" checked="checked" />材料供应商
			</td>
		</tr> 
	</table>
	
	</div>
	<div id="maingrid"></div>
</body>
</html>
