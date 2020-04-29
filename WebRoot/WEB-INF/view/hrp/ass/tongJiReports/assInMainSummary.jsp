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
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    $(function ()
    {
        loadDict();//加载下拉框
    	//加载数据
    	loadHead(null);	
    });
    //查询
    function  query(){
    	if(isnull($("#acc_year_month_begin").val()) || isnull($("#acc_year_month_end").val())){
   			$.ligerDialog.error("请选择起始会计期间！");
   			return;
   		}
    	grid.options.parms=[];
    	grid.options.newPage=1;
        //根据表字段进行添加查询条件
		grid.options.parms.push({name:'year_month_begin',value:$("#acc_year_month_begin").val()}); 
		grid.options.parms.push({name:'year_month_end',value:$("#acc_year_month_end").val()}); 
		grid.options.parms.push({name:'store_id',value:liger.get("store_id").getValue().split("@")[0]}); 
		grid.options.parms.push({name:'ven_id',value:liger.get("ven_id").getValue().split("@")[0]});
		grid.options.parms.push({name:'bus_type',value:liger.get("bus_type").getValue()});
		
    	//加载查询条件
    	grid.loadData(grid.where);
     }

     function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
			   { display: '库房', name: 'store_name', align: 'left',
					 
				    },
			   { display: '供应商', name: 'ven_name', align: 'left',
					 
				 	},
			   { display: '总金额', name: 'in_money', align: 'right',/* totalSummary:{render: function (suminf, column, cell)
                   {
                   return '<div>' + formatNumber(suminf.sum,'${ass_05005 }',1) + '</div>';
               },
               align: 'right'}, */
						render : function(rowdata, rowindex,
							value) {
						 return formatNumber(rowdata.in_money,'${ass_05005 }',1);
						},formatter:'###,##0.00'
				    },

           ],
           dataAction: 'server',dataType: 'server',usePager:true,url:'../tongJiReports/queryAssInMainBySummary.do',
           width: '100%', height: '100%', checkbox: false,rownumbers:true,delayLoad :true,
           selectRowButtonOnly:true,
           toolbar: { items: [
               	{ text: '查询（<u>E</u>）', id:'search', click: query,icon:'search' },
               	{ line:true },
               	{ text: '打印（<u>P</u>）', id:'print', click: printDate,icon:'print' }
		   ]},
         });

        gridManager = $("#maingrid").ligerGetGridManager();
     }
     
   //打印数据
 	function printDate(){
 		if(grid.getData().length==0){
   			$.ligerDialog.error("请先查询数据！");
   			return;
   		}
 		
 		var time=new Date();
 		var date=$("#acc_year_month_begin").val()+"至"+$("#acc_year_month_end").val();
    	var heads={
        		"isAuto":true,//系统默认，页眉显示页码
        		"rows": [
    	          {"cell":0,"value":"报表日期:"},
  				  {"cell":1,"value":date} ,
  				  {"cell":2,"value":$("#bus_type").val()} ,
    	          
        	]}; 
    	//表尾
    	var foots = {
    			rows: [
    				{"cell":0,"value":"制表人:"},
    				{"cell":1,"value":"${sessionScope.user_name}"},
    			]
    		}; 
 		var printPara={
 				title: "资产入库汇总月报表",//标题
 				columns: JSON.stringify(grid.getPrintColumns()),//表头
 				class_name: "com.chd.hrp.ass.service.tongJiReports.AssInMainSummaryService",
 				method_name: "queryAssInMainSummaryPrint",
 				bean_name: "assInMainSummaryService" ,
 				heads: JSON.stringify(heads), //表头需要打印的查询条件,可以为空
 				foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 
 				};
 		
 		$.each(grid.options.parms,function(i,obj){
 				printPara[obj.name]=obj.value;
 		});
 		
 		officeGridPrint(printPara);
 	   		
 	}
	
      function loadDict(){
   	
		var param = {
	           	query_key:''
	    };
        //字典下拉框
          
	   	autocomplete("#store_id","../queryHosStoreDict.do?isCheck=false", "id","text", true, true,param,true);
	   	
	   	autocomplete("#ven_id","../queryHosSupDict.do?isCheck=false","id","text",true,true,param,true);
   	  
	    $("#acc_year_month_begin").ligerTextBox({width:70});
        $("#acc_year_month_end").ligerTextBox({width:70});
        
      //默认年
        if(${ass_year_month}){
			$("#acc_year_month_begin").val('${ass_year_month }')
        }else{
        	autodate("#acc_year_month_begin","YYYYMM"); 
        }
        if(${ass_year_month}){
			$("#acc_year_month_end").val('${ass_year_month }')
        }else{
        	autodate("#acc_year_month_end","YYYYMM"); 
        }
        $('#bus_type').ligerComboBox({
			data:[{id:0,text:'采购入库'},{id:1,text:'采购退货'}],
			valueField: 'id',
            textField: 'text',
			cancelable:true,
			width : 180
		});
      }  
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
 
          <tr>
	        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">统计年月：</td>
	           <td align="left" class="l-table-edit-td"><input name="acc_year_month_begin" type="text" id="acc_year_month_begin" ltype="text" validate="{required:true,maxlength:20}" class="Wdate" 
            onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'acc_year_month_end\');}',isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
        	 <td align="left" width="2%">&nbsp;至：</td>
			<td align="left"><input name="acc_year_month_end" type="text" id="acc_year_month_end" class="Wdate"
				onFocus="WdatePicker({minDate:'#F{$dp.$D(\'acc_year_month_begin\');}',isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})" onchange="changeYearMonth()" /></td>
           <td align="left"></td>
	        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">库房：</td>
	            <td align="left" class="l-table-edit-td"><input name="store_id" type="text" id="store_id"  /></td>
	            <td align="right" class="l-table-edit-td" style="padding-left: 20px;">供应商：</td>
			    <td align="left" class="l-table-edit-td" ><input name="ven_id" type="text" id="ven_id" /></td>
			    
			    <td align="right" class="l-table-edit-td" style="padding-left: 20px;">业务类型：</td>
			    <td align="left" class="l-table-edit-td" ><input name="bus_type" type="text" id="bus_type" /></td>
           </tr>
    </table>
	<div id="maingrid"></div>
</body>
</html>
