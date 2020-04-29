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
		var begin_date = $("#begin_date").val();
        var end_date  = $("#end_date").val();
        
        if(begin_date == ''){
        	$.ligerDialog.warn('开始期间不能为空 ');
        	return ;
        }
        
        if(end_date == ''){
        	$.ligerDialog.warn('结束期间不能为空 ');
        	return ;
        }
        //根据表字段进行添加查询条件
		grid.options.parms.push({name : 'begin_date',value : begin_date});
		grid.options.parms.push({name : 'end_date',value : end_date});
		grid.options.parms.push({
			name : 'inv_id',
			value : $("#inv_id").val()});
		grid.options.parms.push({
			name : 'med_type_id',
			value : liger.get("med_type_code").getValue().split(",")[0]});
		grid.options.parms.push({
			name : 'store_id',
			value :  liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[0]}); 
		grid.options.parms.push({
			name : 'in_no',
			value : $("in_no").val()});
		grid.options.parms.push({
			name : 'inv_model',
			value : $("#inv_model").val()});
		grid.options.parms.push({
			name : 'sup_name',
			value : $("#sup_name").val()});
		
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [
			    {
					display: '日期', name: 'confirm_date', align: 'left', width: '75'
				}, {
					display: '单号', name: 'in_no', align: 'left', width: '150'
			    }, {
					display: '药品名称', name: 'inv_name', align: 'left', width: '150'
			    }, { 
		 			display: '规格', name: 'inv_model', align: 'left', width: '50'
		 		}, { 
		 			display: '单位', name: 'unit_name', align: 'left', width: '50'
		 		}, { 
		 			display: '数量', name: 'amount', align: 'right', width: '50',
		 		}, { 
		 			display: '供应商', name: 'sup_name', align: 'right',  width: '150',
		 		}, { 
		 			display: '生产厂商', name: 'fac_name', align: 'right', width: '150',
		 			
		 		},{ 
		 			display: '注册证号', name: 'cert_code', align: 'left', width: '100'
		 		},{ 
		 			display: '批号', name: 'batch_no', align: 'left', width: '100'
		 		}, { 
		 			display: '有效期', name: 'inva_date', align: 'left', width: '75'
		 		}, { 
		 			display: '出厂检验合格单', name: 'has_fac_insp_cert', align: 'left', width: '100'
		 		}, { 
		 			display: '包装情况', name: 'is_cov_good', align: 'left', width: '75'
		 		}, { 
		 			display: '外观质量', name: 'is_appe_comp', align: 'left', width: '75'
		 		}, { 
		 			display: '验收结果', name: 'check_result', align: 'left', width: '75'
		 		}, { 
		 			display: '验收人', name: 'user_name', align: 'left', width: '100'
		 		}, { 
		 			display: '备注', name: 'note', align: 'left', width: '100'
		 		}, { 
		 			display: '药品类别', name: 'med_type_name', align: 'left', width: '100'
		 		} ],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMedInvCheckDetail.do',
			width: '100%', height: '100%',rownumbers:true,
			delayLoad: true,//初始化不加载，默认false
			selectRowButtonOnly:true,//heightDiff: -10,
			toolbar: { items: [
				{ text: '查询（<u>Q</u>）', id:'search', click: query, icon:'search' },
				{ line:true },
				{ text:'打印', id:'print', click: print,icon:'print'},
				{ line:true}

			]}
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    //打印回调方法
    function lodopPrint(){
    	var head="<table class='head' width='100%'><tr><td>单位：${sessionScope.hos_name}</td></tr>";
 		head=head+"<tr><td>入库日期："+$("#begin_date").val() +" 至  "+ $("#end_date").val()+"</td></tr>";
 		head=head+"</table>";
 		grid.options.lodop.head=head; 
 		grid.options.lodop.fn=renderFunc;
 		grid.options.lodop.title="药品验收查询表";
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
   			title:'药品验收查询表',
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
   		ajaxJsonObjectByUrl("queryMedInvCheckDetail.do?isCheck=false", selPara, function (responseData) {
   			printGridView(responseData,printPara);
		});

   		
    }
   
    function loadDict(){
    	$("#begin_date").ligerTextBox({width:90});
        autodate("#begin_date", "yyyy-mm-dd", "month_first");
        $("#end_date").ligerTextBox({width:90});
        autodate("#end_date", "yyyy-mm-dd", "month_last");
		//字典下拉框
/* 		autocomplete("#store_code", "../../queryMedStoreByRead.do?isCheck=false", "id", "text", true, true,"",false);
		autocomplete("#med_type_code", "../../queryMedTypeByRead.do?isCheck=false", "id", "text", true, true, '',false); */
		autocomplete("#store_code", "../../queryMedStoreDictDate.do?isCheck=false", "id", "text", true, true,{read_or_write:1},false);
		autocomplete("#med_type_code", "../../queryMedTypeDictDate.do?isCheck=false", "id", "text", true, true,{read_or_write:1},false);
	}  
	
	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar" ></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
		<tr>
        	<td align="right" class="l-table-edit-td"  width="10%">
            	入库日期：
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
            
            <td align="right" class="l-table-edit-td" width="10%">仓&nbsp;&nbsp;库：</td>
            <td align="left" class="l-table-edit-td" width="20%">
           		<input name="store_code" type="text" id="store_code" ltype="text" validate="{required:false}" />
            </td>
            <td align="right" class="l-table-edit-td" width="10%">药品类别：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="med_type_code" type="text" id="med_type_code" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
        </tr> 
        
        <tr>
        	<td align="right" class="l-table-edit-td" width="10%">供应商：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="sup_name" type="text" id="sup_name" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
        	<td align="right" class="l-table-edit-td"  width="10%">入库单号：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="in_no" type="text" id="in_no" ltype="text" validate="{required:false}" />
            </td>
            <td align="right" class="l-table-edit-td"  width="10%">药品名称：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="inv_id" type="text" id="inv_id" ltype="text" validate="{required:false}" />
            </td>
        </tr>
        <tr>
        	
	        <td align="right" class="l-table-edit-td" width="10%">规&nbsp;&nbsp;格：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="inv_model" type="text" id="inv_model" ltype="text" validate="{required:false}" />
            </td>
        </tr>
        
	</table>
	<div id="maingrid"></div>
</body>
</html>
