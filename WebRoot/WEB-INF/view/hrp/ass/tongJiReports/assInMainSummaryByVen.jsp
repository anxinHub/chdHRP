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
    	grid.options.parms=[];
    	grid.options.newPage=1;
        //根据表字段进行添加查询条件
        grid.options.parms.push({name:'ass_year',value:$("#ass_year").val()}); 
		grid.options.parms.push({name:'ass_month',value:$("#ass_month").val()});
		grid.options.parms.push({name:'ven_id',value:liger.get("ven_id").getValue().split("@")[0]}); 
    	//加载查询条件
    	grid.loadData(grid.where);
    }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
        	   { display: '年度', name: 'ass_year', align: 'left',width:160
        			 
			   },
			   { display: '月份', name: 'ass_month', align: 'left',width:160
					 
			   },
               { display: '供应商', name: 'ven_name', align: 'left',
	 
				 },
			   { display: '总金额', name: 'in_money', align: 'right',width:220,totalSummary:{render: function (suminf, column, cell)
                   {
                   return '<div>' + formatNumber(suminf.sum,'${ass_05005 }',1) + '</div>';
               },
               align: 'right'},
					render : function(rowdata, rowindex,
						value) {
					 return formatNumber(rowdata.in_money,'${ass_05005 }',1);
				 },formatter:'###,##0.00'
			   },
              ],
            dataAction: 'server',dataType: 'server',usePager:true,url:'../tongJiReports/queryAssInMainSummaryByVen.do',
            width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad :true,
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
    	var date=$("#ass_year").val()+"年"+$("#ass_month").val()+"月";
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
 				title: "资产入库按供应商汇总",//标题
 				columns: JSON.stringify(grid.getPrintColumns()),//表头
 				class_name: "com.chd.hrp.ass.service.tongJiReports.AssInMainSummaryByVenService",
 				method_name: "queryAssInMainSummaryByVenPrint",
 				bean_name: "assInMainSummaryByVenService" ,
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
   		autocomplete("#ven_id","../queryHosSupDict.do?isCheck=false","id","text",true,true,param,true);
   		$("#ass_year,#ass_month").ligerTextBox({width:160});
    }  
</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
 
         <tr>
         	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">统计年度：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_year" type="text" id="ass_year" ltype="text" validate="{required:true,maxlength:20}" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy'})"/></td>
        	<td align="left"></td>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">统计月份：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_month" type="text" id="ass_month" ltype="text" validate="{required:true,maxlength:20}" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'MM'})"/></td>
        	<td align="left"></td>
         	<td align="right" class="l-table-edit-td" style="padding-left: 20px;">供应商：</td>
			<td align="left" class="l-table-edit-td" ><input name="ven_id" type="text" id="ven_id" /></td>
         </tr>
    </table>
	<div id="maingrid"></div>
</body>
</html>
