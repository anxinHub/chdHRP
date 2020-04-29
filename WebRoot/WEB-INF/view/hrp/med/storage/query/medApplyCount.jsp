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
		if($("input[type='radio']:checked").val() == '1'){
			if(liger.get("app_emp").getValue() == ''){
				$.ligerDialog.warn('按个人查询申请人不能为空');
				return ; 
			}
		}
		
		grid.options.parms.push({name : 'app_emp',value : liger.get("app_emp").getValue() == null ? "" : liger.get("app_emp").getValue()});
		grid.options.parms.push({name : 'med_type_id',value : liger.get("med_type_code").getValue() == null ? "" : liger.get("med_type_code").getValue().split(",")[0]});
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
		 			display: '药品类别编码', name: 'med_type_code', align: 'left', minWidth: '120'
		 		}, { 
		 			display: '药品类别名称', name: 'med_type_name', align: 'left', minWidth: '150'
		 		}, { 
		 			display: '药品编码', name: 'inv_code', align: 'left', minWidth: '80'
		 		},  { 
		 			display: '药品名称', name: 'inv_name', align: 'left', minWidth: '80'
		 		},  { 
		 			display: '规格型号', name: 'inv_model', align: 'left', minWidth: '80'
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
		 		}],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMedApplyCount.do',
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
    	
    	var selPara={};
    	$.each(grid.options.parms,function(i,obj){
    		selPara[obj.name]=obj.value;
    	});
   		
		var dates = getCurrentDate();
    	var cur_date = dates.split(";")[2];
    	//跨所有列:计算列数
    	var colspan_num = grid.getColumns(1).length-1;
   		var printPara={
   			title:'科室申领统计查询',
   			head:[
				{"cell":0,"value":"单位: ${sessionScope.hos_name}","colspan":colspan_num,"br":true},
				{"cell":0,"value":"统计日期: " + $("#begin_date").val() +" 至  "+ $("#end_date").val(),"colspan":colspan_num,"br":true}
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
   		ajaxJsonObjectByUrl("queryMedApplyCount.do?isCheck=false", selPara, function (responseData) {
   			printGridView(responseData,printPara);
		});

   		
    }
   
    function loadDict(){
    	$("#begin_date").ligerTextBox({width:110});
        autodate("#begin_date", "yyyy-mm-dd", "month_first");
        $("#end_date").ligerTextBox({width:110});
        autodate("#end_date", "yyyy-mm-dd", "month_last");
        
		//字典下拉框
		//autocomplete("#med_type_code", "../../queryMedTypeDict.do?isCheck=false", "id", "text", true, true, {is_last : 1},false,'',240);
		autocomplete("#med_type_code", "../../queryMedTypeDictDate.do?isCheck=false", "id", "text", true, true, {is_last : 1,read_or_write : 1},false,'',240);
		//autocompleteAsync("#dept_code", "../../queryHosDeptDictByPerm.do?isCheck=false", "id", "text", true, true, {is_last : 1}, true,true,240);
		autocompleteAsync("#dept_code", "../../queryMedDeptDictDate.do?isCheck=false", "id", "text", true, true, {is_last : 1,read_or_write : 1}, true,'',240);
		autocomplete("#app_emp", "../../queryMedEmp.do?isCheck=false", "id", "text", true, true,'',false,'',240);
		//autocomplete("#store_code", "../../queryMedStoreByWrite.do?isCheck=false", "id", "text", true, true, '',true,'',240);
		//autocomplete("#store_code", "../../queryMedStoreByRead.do?isCheck=false", "id", "text", true, true, '',true,'',240);
		autocomplete("#store_code", "../../queryMedStoreDictDate.do?isCheck=false", "id", "text", true, true, {read_or_write : 1},true,'',240);
		autocomplete("#set_code", "../../queryMedVirStore.do?isCheck=false", "id", "text", true, true, '',false,'',240);
	//业务类型xia
autocompleteAsyncMulti("#bus_type_code", "../../queryMedBusType.do?isCheck=false", "id", "text", true, true,{codes : '3, 21, 9,11,13,23,49,50,28,30'},true,true,240);
	
	$("#set_code").ligerTextBox({width:240});
		$("#med_type_code").ligerTextBox({width:240});
		$("#inv_code").ligerTextBox({width:240});
        $("#inv_model").ligerTextBox({width:240});
        
        
        //var by_emp = $("#by_emp").ligerCheckBox();
        //by_emp.setValue(true);
	}  
    
    function singleSel(){
   	 	if($("input[type='radio']:checked").val() == '1'){
   	 		$("#app_emp").ligerComboBox({disabled:false/* ,cancelable: false */});
   			grid.toggleCol('01', true);
   	 	}else{
   	 		liger.get("app_emp").setValue("");
			liger.get("app_emp").setText("");
   	 		$("#app_emp").ligerComboBox({disabled:true/* ,cancelable: false */});
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
        	<td align="right" class="l-table-edit-td"  width="10%">药品类别：</td>
			<td align="left" class="l-table-edit-td" width="20%">
            	<input name="med_type_code" type="text" id="med_type_code" ltype="text" validate="{required:false}" />
            </td>
	        
        	<td align="right" class="l-table-edit-td" width="10%">药品信息：</td>
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
