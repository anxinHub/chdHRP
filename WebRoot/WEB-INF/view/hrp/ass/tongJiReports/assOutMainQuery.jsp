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
<!-- 资产出库汇总 -->
<script type="text/javascript">

    var grid;
    var gridManager = null;
    
    $(function (){
        loadDict();//加载下拉框
        loadHotkeys();
    	loadHead(null);	//加载数据
    	
    });
    
    //查询
    function query(){
    	
   		grid.options.parms=[];
   		grid.options.newPage=1;
   		
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'dept_id',value:liger.get("dept_id").getValue().split("@")[0]});
    	grid.options.parms.push({name:'ass_type_id',value:liger.get("ass_type_id").getValue()}); 
    	grid.options.parms.push({name:'ass_name',value:liger.get("ass_name").getValue()});
    	grid.options.parms.push({name:'ass_spec',value:liger.get("ass_spec").getValue()});
    	grid.options.parms.push({name:'out_no',value:$('#out_no').val()});
    	grid.options.parms.push({name:'out_date_beg',value:$('#out_date_beg').val()});
    	grid.options.parms.push({name:'out_date_end',value:$('#out_date_end').val()});
    	grid.options.parms.push({name : 'store_id',value : liger.get("store_id").getValue().split("@")[0]});
    	grid.options.parms.push({name : 'store_no',value : liger.get("store_id").getValue().split("@")[1]});
    	grid.options.parms.push({name : 'ven_id',value : liger.get("ven_id").getValue().split("@")[0]});
    	//加载查询条件
    	grid.loadData(grid.where);
    	loadHead();	
		$("#resultPrint > table > tbody").empty();
		
	}
    
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [{ 
					display: '仓库名称', name: 'store_name', align: 'left', minWidth: '90'
				},{ 
		 			display: '资产类别', name: 'ass_type_name', align: 'left', minWidth: '90'
		 		},{ 
		 			display: '资产编码', name: 'ass_code', align: 'left', minWidth: '80'},
		 		{ 
		 			display: '资产名称', name: 'ass_name', align: 'left', minWidth: '80'
		 		},{ 
		 			display: '规格型号', name: 'ass_spec', align: 'left', minWidth: '80'
		 		},{ 
		 			display: '计量单位', name: 'unit_name', align: 'left', minWidth: '80'
		 		},{ 
		 			display: '出库数量', name: 'ass_amount', align: 'left', minWidth: '80'
		 		},{ 
		 			display: '单价', name: 'price', align: 'left', minWidth: '80',
		 			render : function(rowdata, rowindex, value) {
		 				if(rowdata.price == null ){
		 					return "";
		 				}
						return formatNumber(rowdata.price, '${ass_05005 }', 1);
					},formatter:"###,##0.00"
		 		},{ 
		 			display: '金额', name: 'sum_price', align: 'right', minWidth: '100',
		 			render : function(rowdata, rowindex, value) {
						return formatNumber(rowdata.sum_price ==null ? 0 : rowdata.sum_price, '${ass_05005 }', 1);
					},formatter:"###,##0.00"
		 		},{ 
		 			display: '生产厂商', name: 'fac_name', align: 'left', minWidth: '130'
		 		},{ 
		 			display: '供应商名称', name: 'ven_name', align: 'left', minWidth: '80'
		 		},{ 
		 			display: '领料科室', name: 'dept_name', align: 'left', minWidth: '80'
		 		}],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryAssOutMainQuery.do?isCheck=false',
			width: '100%', height: '100%',rownumbers:true,
			delayLoad: true,//初始化不加载，默认false
			selectRowButtonOnly:true,//heightDiff: -10,
			toolbar: { items: [
				{ text: '查询（<u>Q</u>）', id:'search', click: query, icon:'search' },
				{ line:true },
				{ text: '打印', id:'print', click:printDate, icon:'print'},
				{ line:true }
			]}
		});
	

    gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    //字典下拉框
    function loadDict(){
    	var param = {query_key:''};
    	autocomplete("#dept_id","../queryDeptDict.do?isCheck=false","id","text",true,true,null,null,null,"180");
    	
    	autodate("#out_date_beg","YYYY-mm-dd","month_first");

		autodate("#out_date_end","YYYY-mm-dd","month_last");
    	$("#out_date_beg,#out_date_end").ligerTextBox({width : 80});
    	
    	$("#ass_name,#out_no,#ass_spec").ligerTextBox({width:180});
    	autocomplete("#ass_type_id","../queryAssTypeDictIsLast.do?isCheck=false","id","text",true,true,null,null,null,"180");

    	autocomplete("#ven_id", "../queryHosSupDictNo.do?isCheck=false","id", "text",true,true,null,false,null,"180");
    	autocomplete("#store_id", "../queryHosStoreDict.do?isCheck=false","id", "text",true,true,param,true,null,"180");
    } 
    //键盘事件
	function loadHotkeys() {
	}
    
	//打印数据
 	function printDate(){
 		if(grid.getData().length==0){
   			$.ligerDialog.error("请先查询数据！");
   			return;
   		}
 		
 		var time=new Date();
    	var date = $('#out_date_beg').val()+"至"+$('#out_date_end').val();
    	
    	var heads={
        		"isAuto":true,//系统默认，页眉显示页码
        		"rows": [
        		          {"cell":0,"value":"报表日期:"},
         				  {"cell":1,"value":date} ,
		    	          
		    	        
    	          
        	]}; 
    	//表尾
    	var foots = {   
    			rows: [
    				{"cell":0,"value":"制表人:"},
    				{"cell":1,"value":"${sessionScope.user_name}"},
    			]
    		}; 
 		var printPara={
 				title: "资产出库汇总月报表",//标题
 				columns: JSON.stringify(grid.getPrintColumns()),//表头
 				class_name: "com.chd.hrp.ass.service.tongJiReports.AssOutSummaryService",
 				method_name: "queryOutSituationPrint",
 				bean_name: "assOutSummaryService" ,
 				heads: JSON.stringify(heads), //表头需要打印的查询条件,可以为空
 				foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 
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
	
	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" >
		<tr>
			<td align="right" class="l-table-edit-td"  width="10%">出库日期：</td>
			
			<td align="left" class="l-table-edit-td"  width="20%">
				<table>
					<tr>
						<td align="left" class="l-table-edit-td">
							<input class="Wdate" name="out_date_beg" id="out_date_beg" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						<td align="right" class="l-table-edit-td"  >
							至：
						</td>
						<td align="left" class="l-table-edit-td">
							<input class="Wdate" name="out_date_end" id="out_date_end" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
            		</tr>
				</table>
			</td>
			<td align="right" class="l-table-edit-td"  width="10%">仓库：</td>
			<td align="left" class="l-table-edit-td" width="20%">
            	<input name="store_id" type="text" id="store_id" ltype="text" validate="{required:true}" />
            </td>
			
			<td align="right" class="l-table-edit-td"  width="10%">
				资产类别：
			</td>
			<td align="left" class="l-table-edit-td" width="20%">
            	<input name="ass_type_id" type="text" id="ass_type_id" ltype="text" validate="{required:true,maxlength:20}" />
            </td>
		</tr>
		
		<tr>
			<td align="right" class="l-table-edit-td"  width="10%">&nbsp;科 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;室：</td>
			<td align="left" class="l-table-edit-td" width="20%">
            	<input name="dept_id" type="text" id="dept_id" ltype="text" required="true" validate="{required:true,maxlength:100}" />
            </td>
			<td align="right" class="l-table-edit-td"  width="10%">
				资产名称：
			</td>
			<td align="left" class="l-table-edit-td" width="20%">
            	<input name="ass_name" type="text" id="ass_name" ltype="text"  />
            </td>
            <td align="right" class="l-table-edit-td"  width="10%">出库单号：</td>
			<td align="left" class="l-table-edit-td" width="20%">
            	<input name="out_no" type="text" id="out_no" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
		</tr>
		
		<tr>
			<td align="right" class="l-table-edit-td" width="10%">供应商：</td>
			<td align="left" class="l-table-edit-td" width="20%"><input name="ven_id" type="text" id="ven_id" ltype="text" validate="{required:false}" /></td>
			<td align="right" class="l-table-edit-td"  width="10%">规格型号：</td>
			<td align="left" class="l-table-edit-td" width="20%">
            	<input name="ass_spec" type="text" id="ass_spec" ltype="text"  required="true"  validate="{required:true}" />
            </td> 
		</tr>
	</table>
		
	</div>
	<div id="maingrid"></div>
</body>
</html>
