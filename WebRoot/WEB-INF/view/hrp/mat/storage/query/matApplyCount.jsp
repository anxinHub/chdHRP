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
        loadDict();//加载下拉框
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
		singleSel();
    });
    //查询
    function  query(){
		grid.options.parms=[];
		grid.options.newPage=1; 
        //根据表字段进行添加查询条件
        grid.options.parms.push({name : 'begin_date',value : $("#begin_date").val()});
		grid.options.parms.push({name : 'end_date',value : $("#end_date").val()});
		grid.options.parms.push({name : 'set_id',value : liger.get("set_code").getValue() == null ? "" : liger.get("set_code").getValue()});
		grid.options.parms.push({name : 'store_id',value : liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[0]});
		grid.options.parms.push({name : 'by_emp',value : $("input[type='radio']:checked").val()==1 ?1 :0});
		grid.options.parms.push({name : 'dept_id',value : liger.get("dept_code").getValue() == null ? "" : liger.get("dept_code").getValue().split(",")[0]});
		grid.options.parms.push({name : 'app_emp',value : liger.get("app_emp").getValue() == null ? "" : liger.get("app_emp").getValue()});
		grid.options.parms.push({name : 'mat_type_id',value : liger.get("mat_type_code").getValue() == null ? "" : liger.get("mat_type_code").getValue().split(",")[0]});
		grid.options.parms.push({name : 'inv_code',value : $("#inv_code").val()});
		grid.options.parms.push({name : 'inv_model',value : $("#inv_model").val()});
		grid.options.parms.push({
			name:'bus_type_code', 
			value : liger.get("bus_type_code").getValue() == "" ?  "" :"("+liger.get("bus_type_code").getValue().replace(/;/g,',')+")"
		});
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [{
					display: '虚仓名称', name: 'set_name', align: 'left', minWidth: '140',id:'02'
				}, {
					display: '仓库名称', name: 'store_name', align: 'left', minWidth: '140',id:'03'
		 		}, {
					display: '科室编码', name: 'dept_code', align: 'left', minWidth: '140'
				}, { 
		 			display: '科室名称', name: 'dept_name', align: 'left', minWidth: '150'
		 		}, { 
		 			display: '申请人', name: 'emp_name', align: 'left', minWidth: '80',id:'01'
		 		}, { 
		 			display: '物资类别编码', name: 'mat_type_code', align: 'left', minWidth: '120'
		 		}, { 
		 			display: '物资类别名称', name: 'mat_type_name', align: 'left', minWidth: '150'
		 		}, { 
		 			display: '材料编码', name: 'inv_code', align: 'left', minWidth: '80'
		 		},  { 
		 			display: '材料名称', name: 'inv_name', align: 'left', minWidth: '80'
		 		},  { 
		 			display: '规格型号', name: 'inv_model', align: 'left', minWidth: '80'
		 		},  { 
		 			display: '单价', name: 'price', align: 'left', minWidth: '80',
		 			render : function(rowdata, rowindex, value) {
		 				if(rowdata.price == null || rowdata.price == '' || rowdata.price == undefined){
		 					return "";
		 				}
						return formatNumber(rowdata.price ==null ? 0 : rowdata.price, '${p04006 }', 1);
					},formatter:"###,##0.00"
		 		},  { 
		 			display: '数量', name: 'amount', align: 'left', minWidth: '80'
		 		},  { 
		 			display: '金额', name: 'amount_money', align: 'right', minWidth: '100',
		 			render : function(rowdata, rowindex, value) {
						return formatNumber(rowdata.amount_money ==null ? 0 : rowdata.amount_money, '${p04005 }', 1);
					},formatter:"###,##0.00"
		 		}],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatApplyCount.do',
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
    
    //键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('P', print);
	}
    
	
 
	//打印
	function print(){
		singleSel();
    	if(grid.getData().length==0){
			$.ligerDialog.error("请先查询数据！");
			return;
		}
    	
    	var heads={
        		"isAuto":true,//系统默认，页眉显示页码
        		"rows": [
    	          {"cell":0,"value":"统计年月："},
    	          {"cell":1,"value":""+liger.get("begin_date").getValue()+"至"+liger.get("end_date").getValue()}	,
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
          		title: "科室申领统计报表",//标题
          		columns: JSON.stringify(grid.getPrintColumns()),//表头
          		class_name: "com.chd.hrp.mat.service.storage.query.MatInDetailService",
       			method_name: "queryMatApplyCountPrint",
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
    	$("#begin_date").ligerTextBox({width:110});
        autodate("#begin_date", "yyyy-mm-dd", "month_first");
        $("#end_date").ligerTextBox({width:110});
        autodate("#end_date", "yyyy-mm-dd", "month_last");
        
		//字典下拉框
		//autocomplete("#mat_type_code", "../../queryMatTypeDict.do?isCheck=false", "id", "text", true, true, {is_last : 1},false,'',240);
		autocomplete("#mat_type_code", "../../queryMatTypeDictDate.do?isCheck=false", "id", "text", true, true, {is_last : 1,read_or_write : 1},false,'',240);
		//autocompleteAsync("#dept_code", "../../queryHosDeptDictByPerm.do?isCheck=false", "id", "text", true, true, {is_last : 1}, true,true,240);
		autocompleteAsync("#dept_code", "../../queryMatDeptDictDate.do?isCheck=false", "id", "text", true, true, {is_last : 1,read_or_write : 1}, true,'',240);
		autocomplete("#app_emp", "../../queryMatEmp.do?isCheck=false", "id", "text", true, true,'',false,'',240);
		//autocomplete("#store_code", "../../queryMatStoreByWrite.do?isCheck=false", "id", "text", true, true, '',true,'',240);
		//autocomplete("#store_code", "../../queryMatStoreByRead.do?isCheck=false", "id", "text", true, true, '',true,'',240);
		autocomplete("#store_code", "../../queryMatStoreDictDate.do?isCheck=false", "id", "text", true, true, {read_or_write : 1},true,'',240);
		autocomplete("#set_code", "../../queryMatVirStore.do?isCheck=false", "id", "text", true, true, '',false,'',240);
	//业务类型xia
autocompleteAsyncMulti("#bus_type_code", "../../queryMatBusType.do?isCheck=false", "id", "text", true, true,{codes : '3, 21, 9,11,13,23,49,50,28,30'},true,true,240);
	
	$("#set_code").ligerTextBox({width:240});
		$("#mat_type_code").ligerTextBox({width:240});
		$("#inv_code").ligerTextBox({width:240});
        $("#inv_model").ligerTextBox({width:240});
        
        
        //var by_emp = $("#by_emp").ligerCheckBox();
        //by_emp.setValue(true);
	}  
    
    function singleSel(){
   	 	if($("input[type='radio']:checked").val() == '1'){
   	 		$("#app_emp").ligerComboBox({disabled:false,cancelable: false});
   			grid.toggleCol('01', true);
   	 	}else{
   	 		liger.get("app_emp").setValue("");
			liger.get("app_emp").setText("");
   	 		$("#app_emp").ligerComboBox({disabled:true,cancelable: false});
   			grid.toggleCol('01', false);
   	 	}
    }
	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar" ></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" >
        <tr>
        	<td align="right" class="l-table-edit-td"  width="10%">
            	确认日期：
            </td>
            <td align="left" class="l-table-edit-td"  width="20%">
				<table>
					<tr>
						<td>
							<input class="Wdate" name="begin_date" id="begin_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
						<td align="right" class="l-table-edit-td"  > 至 </td>
						<td>
							<input class="Wdate" name="end_date" id="end_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
            		</tr>
				</table>
	        </td>
            
            <td align="right" class="l-table-edit-td" width="10%">虚&nbsp;&nbsp;仓：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="set_code" type="text" id="set_code" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
            
            <td align="right" class="l-table-edit-td" width="10%">仓&nbsp;&nbsp;库：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="store_code" type="text" id="store_code" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
        </tr> 
        
        <tr>
        	<td align="right" class="l-table-edit-td"  width="10%"></td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input id="by_emp" name="planType" type="radio" value="1" checked="checked" onclick="singleSel()" />按个人
            	<input id="by_dept" name="planType" type="radio" value="2" onclick="singleSel()" />按科室
            </td>
        	
	        <td align="right" class="l-table-edit-td"  width="10%">申请科室：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="dept_code" type="text" id="dept_code" ltype="text" validate="{required:false}" />
            </td>
            
	        <td align="right" class="l-table-edit-td"  width="10%">申请人：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="app_emp" type="text" id="app_emp" ltype="text" validate="{required:false}" />
            </td>
            
        </tr>
        <tr>
        	<td align="right" class="l-table-edit-td"  width="10%">物资类别：</td>
			<td align="left" class="l-table-edit-td" width="20%">
            	<input name="mat_type_code" type="text" id="mat_type_code" ltype="text" validate="{required:false}" />
            </td>
	        
        	<td align="right" class="l-table-edit-td" width="10%">材料信息：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="inv_code" type="text" id="inv_code" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
            
            <td align="right" class="l-table-edit-td" width="10%">
				规格型号：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="inv_model" type="text" id="inv_model" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
            
        </tr>
        <tr>
        <td align="right" class="l-table-edit-td"  width="10%">
            	业务类型：
        	</td>
        	
        	<td align="left" class="l-table-edit-td"  width="20%">
        		<input name="bus_type_code" type="text" id="bus_type_code" ltype="text"/>
        	</td>
        </tr>
        
       
    </table>
	<div id="maingrid"></div>
</body>
</html>
