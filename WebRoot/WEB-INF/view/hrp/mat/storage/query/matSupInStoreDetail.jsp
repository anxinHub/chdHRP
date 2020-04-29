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
	jQuery.fn.rowspan = function (colname, tableObj) {
		var colIdx;
		for (var i = 0, n = tableObj.columns.length; i < n; i++) {
			if (tableObj.columns[i]["columnname"] == colname) {
	    		colIdx = i - 1 < 1 ? 0 : i - 1;
	    		break;
	    	}
	   	}
	   	return this.each(function () {
	    	var that;
	        $('tr', this).each(function (row) {
	        	$('td:eq(' + colIdx + ')', this).filter(':visible').each(function (col) {
	            	if (that != null && $(this).html() == $(that).html()) {
	                	rowspan = $(that).attr("rowSpan");
	                	if (rowspan == undefined) {
	                    	$(that).attr("rowSpan", 1);
	                   		rowspan = $(that).attr("rowSpan");
	                	}
	                 	rowspan = Number(rowspan) + 1;
	                 	$(that).attr("rowSpan", rowspan);
	                 	$(this).hide();
	            	} else {
	                	 that = this;
	       			}
	     		});
	      	});
	 	});
	}
    
    var grid;
    var gridManager = null;
    var userUpdateStr;
    
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
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
		grid.options.parms.push({name : 'mat_type_id',value : liger.get("mat_type_code").getValue().split(",")[0]}); 
		grid.options.parms.push({name : 'mat_type_code',value : liger.get("mat_type_code").getText().split(" ")[0]});
		grid.options.parms.push({name : 'sup_id',value : liger.get("sup_code").getValue().split(",")[0]}); 
		grid.options.parms.push({name : 'sup_no',value : liger.get("sup_code").getValue().split(",")[1]});
    	grid.options.parms.push({name : 'is_charge',value : liger.get("is_charge").getValue()}); 
    	grid.options.parms.push({name : 'set_id',value : liger.get("set_code").getValue()});

    	//加载查询条件
    	grid.loadData(grid.where);
	}

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [
				{display: '供应商编码', name: 'sup_code', align: 'left', minWidth: '10%'},
				{display: '供应商名称', name: 'sup_name', align: 'left', minWidth: '20%'},       
				{display: '材料编码', name: 'inv_code', align: 'left', minWidth: '10%'},
				{display: '材料名称', name: 'inv_name', align: 'left', minWidth: '10%'},
				{display: '规格', name: 'inv_model', align: 'left', minWidth: '10%'},
				{display: '单位', name: 'unit_name', align: 'left', minWidth: '10%'},
				{display: '数量', name: 'amount', align: 'left', minWidth: '10%'},
				{display: '进价', name: 'price', align: 'right', minWidth: '10%'},
				{display: '进价金额', name: 'amount_money', align: 'right', minWidth: '10%'}
			],
			dataAction: 'server',dataType: 'server',usePager:false,url:'queryMatSupInStoreDetail.do',
			width: '100%', height: '100%',rownumbers:true,
			delayLoad: true,//初始化不加载，默认false
			selectRowButtonOnly:true,//heightDiff: -10,
			onAfterShowData: function (s) {
				setTimeout(function () {
					$('#maingrid .l-grid-body-table tbody').rowspan('sup_code', grid);
					$('#maingrid .l-grid-body-table tbody').rowspan('sup_name', grid)
				}, 0)
			},
			toolbar: { items: [
				{ text: '查询', id:'search', click: query, icon:'search' },
				{ line: true },
				{ text: '打印', id:'print', click: printData, icon:'print' },
			]}
		});
        gridManager = $("#maingrid").ligerGetGridManager();
    }
   
    function loadDict(){
		//字典下拉框
		autocomplete("#store_code", "../../queryMatStoreDictDate.do?isCheck=false", "id", "text", true, true,{read_or_write:1});
		autocomplete("#mat_type_code", "../../queryMatTypeDictDate.do?isCheck=false", "id", "text", true, true,{read_or_write:1},false,'',220);
		autocomplete("#sup_code", "../../queryHosSupDict.do?isCheck=false", "id", "text", true, true);
		autocomplete("#is_charge", "../../queryMatYearOrNo.do?isCheck=false", "id", "text", true, true);
		autocomplete("#set_code", "../../queryMatVirStore.do?isCheck=false", "id", "text", true, true);
		$("#begin_confirm_date").ligerTextBox({width:100});
        $("#end_confirm_date").ligerTextBox({width:100});
        autodate("#begin_confirm_date", "yyyy-mm-dd", "month_first");
        autodate("#end_confirm_date", "yyyy-mm-dd", "month_last");
	}  
    
  	//打印
	function printData(){
    	
    	if(grid.getData().length==0){
			$.ligerDialog.error("请先查询数据！");
			return;
		}
    	
    	var printPara={
        	title: "供应商入库明细汇总表",//标题
       		columns: JSON.stringify(grid.getPrintColumns()),//表头
        	class_name: "com.chd.hrp.mat.serviceImpl.storage.query.MatSupInStoreServiceImpl",
       		method_name: "queryMatSupInStoreDetailPrint",
       		bean_name: "matSupInStoreService"
    	};
      	$.each(grid.options.parms,function(i,obj){
       		printPara[obj.name]=obj.value;
      	});
       		
     	officeGridPrint(printPara);
    }

</script>
</head>

<body style="padding: 0px; overflow: hidden;"  >
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
	<div class="search-block clearfix">
		 <table>
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
	        <td align="right" class="l-table-edit-td" width="10%">虚仓名称：</td>
            <td align="left" class="l-table-edit-td" width="20%"> 
            	<input name="set_code" type="text" id="set_code" ltype="text" validate="{required:false}" />
  			</td> 
	        <td align="right" class="l-table-edit-td"  width="10%"> 仓库： </td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="store_code" type="text" id="store_code" ltype="text" validate="{required:false}" />
            </td>
		</tr>
		<tr>
	      	<td align="right" class="l-table-edit-td"  width="10%"> 物资类别： </td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="mat_type_code" type="text" id="mat_type_code" ltype="text"   validate="{required:true,maxlength:20}" />
            </td>
        	<td align="right" class="l-table-edit-td"  width="10%">是否收费： </td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="is_charge" type="text" id="is_charge" ltype="text" validate="{required:true,maxlength:20}" />
            </td>
            <td align="right" class="l-table-edit-td" width="10%">供&nbsp;应&nbsp;商： </td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="sup_code" type="text" id="sup_code" ltype="text" validate="{required:false}" />
			</td>
		</tr> 
	</table>
	</div>
	<div id="maingrid"></div>
</body>
</html>
