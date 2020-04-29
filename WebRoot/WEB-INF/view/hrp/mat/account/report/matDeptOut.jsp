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
				return formatNumber(value, '${p04006}', 1);
			},
			amount_money:function(value){//金额
				return formatNumber(value, '${p04005}', 1);
			},
			amount:function(value){//数量
				return formatNumber(value, '${p04005}', 1);
			}
	}; 
    
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
		 loadHotkeys();
		 query();
    });
    //查询
    function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
        //根据表字段进行添加查询条件
        
        var  begin_date = $("#begin_date").val();
        var  end_date = $("#end_date").val();
        
        if( begin_date == '' ){
        	$.ligerDialog.warn('开始期间不能为空 ');
        	return ; 
        }
        
        if( end_date == '' ){
        	$.ligerDialog.warn('结束期间不能为空 ');
        	return ; 
        }
        
		grid.options.parms.push({
			name : 'begin_date',
			value : begin_date
		});
		grid.options.parms.push({
			name : 'end_date',
			value : end_date
		}); 
		grid.options.parms.push({
			name : 'store_id',
			value : liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[0]
		}); 
		grid.options.parms.push({
			name : 'bus_type_code',
			value : liger.get("bus_type_code").getValue() == null ? "" : "("+liger.get("bus_type_code").getValue().replace(/;/g,',')+")"
		}); 
		
	/* 	grid.options.parms.push({
			name : 'mat_type_id',
			value : liger.get("mat_type_code").getValue() == null ? "" : liger.get("mat_type_code").getValue()
		}); */
		grid.options.parms.push({name : 'mat_type_code',value : liger.get("mat_type_code").getText().split(" ")[0]});
		grid.options.parms.push({
			name : 'set_id',
			value : liger.get("set_code").getValue() == null ? "" : liger.get("set_code").getValue()
		}); 
		
		grid.options.parms.push({
			name : 'inv_id',
			value : liger.get("inv_code").getValue() == null ? "" : liger.get("inv_code").getValue().split(",")[0]
		}); 
		
		grid.options.parms.push({
			name : 'is_charge',
			value : liger.get("is_charge").getValue() == null ? "" : liger.get("is_charge").getValue()
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
				display: '虚仓名称', name: 'set_name', align: 'left', minWidth: '150', frozen: true
				}, { 
		 			display: '仓库名称', name: 'store_name', align: 'left', minWidth: '100', frozen: true
		 		},{
					display: '物资类别', name: 'mat_type_name', textField: 'mat_type_name', align: 'left', minWidth: '150', frozen: true
				}, { 
		 			display: '材料编码', name: 'inv_code', align: 'left', minWidth: '100', frozen: true
		 		}, { 
		 			display: '材料名称', name: 'inv_name', align: 'left', minWidth: '150', frozen: true
		 		}, { 
		 			display: '规格型号', name: 'inv_model', align: 'left', minWidth: '80'
		 		}, { 
		 			display: '计量单位', name: 'unit_name', align: 'left', minWidth: '80'
		 		}, { 
		 			display: '数量', name: 'amount', align: 'left', minWidth: '100',formatter:'0.00'
		 		}, { 
		 			display: '单价', name: 'price', align: 'right', minWidth: '100',formatter:"###,##0.00",
		 			render : function(rowdata, rowindex, value) {
						return value ==null ? "" : formatNumber(value, '${p04006}', 1);
					}
		 		}, { 
		 			display: '金额', name: 'amount_money', align: 'right', minWidth: '100',formatter:"###,##0.00",
		 			render : function(rowdata, rowindex, value) {
						return value ==null ? "" : formatNumber(value, '${p04005}', 1);
					}
		 		}],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatAccountReportDeptOut.do',
			width: '100%', height: '98%',rownumbers:true,
			delayLoad: true,//初始化不加载，默认false
			selectRowButtonOnly:true,//heightDiff: -10,
			//alternatingRow: false, //不知道什么意思，有待测试
			tree: { columnId: 'mat_type_code' }, 
			toolbar: { items: [ 
				{ text: '查询（<u>Q</u>）', id:'search', click: query, icon:'search' },
				{ line:true },
				{ text: '打印', id:'print', click: print, icon:'print' },
				{ line:true }
			]}
		});
		console.log(324567,grid);
        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    //打印回调方法
    function lodopPrint(){
    	var head="<table class='head' width='100%'><tr><td>单位：${hos_name}</td></tr>";
 		head=head+"<tr><td>确认日期："+$("#begin_date").val()+"至"+$("#end_date").val()+"</td></tr>";
 		head=head+"</table>";
 		grid.options.lodop.head=head; 
 		grid.options.lodop.fn=renderFunc;
 		grid.options.lodop.title="出库明细汇总表";
    }
    
    //键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
		hotkeys('P', print);
	}
    
    function loadDict(){
		//字典下拉框
/* 		autocomplete("#store_code", "../../queryMatStoreByRead.do?isCheck=false", "id", "text", true, true);
		autocomplete("#mat_type_code", "../../queryMatType.do?isCheck=false", "id", "text", true, true,'',false,'',280); */
		autocomplete("#store_code", "../../queryMatStoreDictDate.do?isCheck=false", "id", "text", true, true,{read_or_write:1});
		autocomplete("#mat_type_code", "../../queryMatTypeDictDate.do?isCheck=false", "id", "text", true, true,{read_or_write:1},false,'',280);
		autocomplete("#set_code", "../../queryMatVirStore.do?isCheck=false", "id", "text", true, true);
		autocomplete("#is_charge", "../../queryMatYearOrNo.do?isCheck=false", "id", "text", true, true);
	//	autocomplete("#inv_code", "../../queryMatInvDict.do?isCheck=false", "id", "text", true, true);
		autocompleteAsyncMulti("#bus_type_code", "../../queryMatBusType.do?isCheck=false", "id", "text", true, true, {codes : '3, 21, 9,11,13,23,49,50'}, true);
        $("#begin_date").ligerTextBox({width:100});
        autodate("#begin_date", "yyyy-mm-dd", "month_first");
        $("#end_date").ligerTextBox({width:100});
        autodate("#end_date", "yyyy-mm-dd", "month_last");
        $("#inv_model").ligerTextBox({width:240});
        $("#inv_code").ligerTextBox({width:160});
        $("#mat_type_code").ligerTextBox({width:240});
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
    	          {"cell":1,"value":""+$("#begin_date").val()+"至"+$("#end_date").val()},
    	          {"cell":3,"value":"仓库："},
    	          {"cell":4,"value":""+liger.get("store_code").getText()==''?'空':liger.get("store_code").getText().split(" ")[1]+""},
    	          {"cell":6,"value":"虚仓："},
    	          {"cell":7,"value":""+liger.get("set_code").getText()==''?'空':liger.get("set_code").getText().split(" ")[1]+""}
        	]}; 
    	//表尾
		var foots = {
			rows: [
				{"cell":0,"value":"制表日期:"} ,
				{"cell":1,"value":date} ,
			]
		}; 
    	var printPara={
          		title: "出库明细汇总表(材料)",//标题
          		columns: JSON.stringify(grid.getPrintColumns()),//表头
          		class_name: "com.chd.hrp.mat.service.account.report.MatAccountReportDeptOutService",
       			method_name: "queryMatAccountReportDeptOutPrint",
       			bean_name: "matAccountReportDeptOutService",
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
    <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" >
        <tr>
            <td align="right" class="l-table-edit-td"  width="10%">
            	<span style="color: red; size: 2;">*</span>确认日期：
            </td>
            <td align="left" class="l-table-edit-td"  width="20%">
				<table>
					<tr>
						<td align="left" class="l-table-edit-td">
							<input class="Wdate" name="begin_date" id="begin_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
						<td align="right" class="l-table-edit-td"  >
							至：
						</td>
						<td align="left" class="l-table-edit-td">
							<input class="Wdate" name="end_date" id="end_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
            		</tr>
				</table>
	        </td>
	        
	        <td align="right" class="l-table-edit-td"  width="10%">
				虚仓名称：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="set_code" type="text" id="set_code" ltype="text" />
            </td>
            
			<td align="right" class="l-table-edit-td"  width="10%">
				仓库：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="store_code" type="text" id="store_code" ltype="text" required="true" validate="{required:false}" />
            </td>
			
        </tr> 
        <tr>
        	<td align="right" class="l-table-edit-td"  width="10%">
				物资类别：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="mat_type_code" type="text" id="mat_type_code" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
            
            <td align="right" class="l-table-edit-td"  width="10%">
				材料信息：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="inv_code" type="text" id="inv_code" ltype="text" />
            </td>
            
            <td align="right" class="l-table-edit-td"  width="10%">
				是否收费：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="is_charge" type="text" id="is_charge" ltype="text" />
            </td>
        </tr>
        <tr>
       		<td align="right" class="l-table-edit-td"  width="10%">
				规格型号:
			</td>
		  	<td align="left" class="l-table-edit-td" width="20%">
				<input name="inv_model" type="text" id="inv_model" ltype="text"    validate="{required:true}" />
		  	</td>
		  	
			<td align="right" class="l-table-edit-td"  width="10%">
				业务类型：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="bus_type_code" type="text" id="bus_type_code" ltype="text" required="true" validate="{required:true}" />
            </td>
     </tr>
		
    </table>
	<div id="maingrid"></div>
</body>
</html>
