<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
    $(function ()
    {
		loadDict();
    	loadHead(null);	//加载数据
    	$("#year_month1").ligerTextBox({ width:50 });
    	$("#year_month2").ligerTextBox({ width:50 });
    	$("#date_type").ligerTextBox({ width:160 });
    	$("#income_level").ligerTextBox({ width:160 });
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
        
            
        	if($("#date_type").val()==""||$("#year_month1").val()==""||$("#year_month2").val()==""){
        		
        		$.ligerDialog.error('期间不能为空!');
        		
        		return ;
        		
        	}
		grid.options.parms.push({name:'year_month1',value:$("#year_month1").val()}); 
		grid.options.parms.push({name:'year_month2',value:$("#year_month2").val()}); 
		grid.options.parms.push({name:'date_type',value:$("#date_type").val()}); 
		grid.options.parms.push({name:'kind_code',value:liger.get("kind_code").getValue()}); 
		grid.options.parms.push({name:'natur_code',value:liger.get("natur_code").getValue()}); 
		grid.options.parms.push({name:'dept_level',value:liger.get("dept_level").getValue()});
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	var columnses = ""
                     +"{ display: '科室编码', name: 'DEPT_CODE', align: 'left'},"
					 +"{ display: '科室名称', name: 'DEPT_NAME', align: 'left'}"
		    +",";
    	
    	ajaxJsonObjectByUrl("loadIncomeStructureTitle.do?isCheck=false",null,function (responseData){
    		$.each(responseData.Rows,function(idx,item){ 
    			columnses = columnses + "{display : '"+item.income_item_name+"', align: 'center',"
				+"columns:["
	                     +"{ display: '金额', name: 'PRICE_"+item.income_item_id+"', align: 'left'},"
						 +"{ display: '比例', name: 'ratio_"+item.income_item_id+"', align: 'left',"
						 +"render : function(rowdata, rowindex,value) {"
							 +"return formatNumber((rowdata.PRICE_"+item.income_item_id+" / rowdata.PRICE_TOTAL) * 100) + '%';"
							 +"}"
	                     +"}"
			 +"]},";
			});
    		grid = $("#maingrid").ligerGrid({
    	           columns: eval("["+columnses+"]"),
    	                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryBillingRevenueStructureAnalysis.do',
    	                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
    	                     selectRowButtonOnly:true,//heightDiff: -10,
    	                     toolbar: { items: [
    	                     	{ text: '查询', id:'search', click: query,icon:'search' },
    	                     	{ line:true },
    	    	                //{ text: '导出Excel', id:'export', click: exportExcel,icon:'pager' },
    			                //{ line:true },
    			                { text: '打印', id:'print', click: printDate,icon:'print' },
    			                { line:true }
    	    				]}
    	                   });
    	        gridManager = $("#maingrid").ligerGetGridManager();
    	});
    	
    }


    function loadDict(){
    	$("form").ligerForm();
    	var param = {
            	kind_code1:'01',
            	kind_code2:'02'
        };
    	autocomplete("#kind_code","../../../sys/queryDeptKindDict.do?isCheck=false","id","text",true,true,param);
    	var param1 = {
    			natur_code1:'03',
    			natur_code2:'05'
    	};
    	autocomplete("#natur_code","../../../acc/queryDeptNatur.do?isCheck=false","id","text",true,true,param1);
    	
    	
    	autocomplete("#dept_level","../../queryDeptLevel.do?isCheck=false","id","text",true,true); 
         }  
    
  //打印数据
	 function printDate(){
		//有数据直接打印
		if($("#resultPrint > table > tbody").html()!=""){
			lodopPrinterTable("resultPrint","开始打印","列表",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备打印数据,请稍候...');

		
		var printPara={
				
			usePager:false
            
         };
		ajaxJsonObjectByUrl("queryBillingRevenueStructureAnalysis.do",printPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
					 trHtml+="<td>"+item.DEPT_CODE+"</td>"; 
					 trHtml+="<td>"+item.DEPT_NAME+"</td>"; 
					 trHtml+="<td>"+item.PRICE_1+"</td>"; 
					 var num1=formatNumber((item.PRICE_1/ item.PRICE_TOTAL) * 100) + '%'
					 trHtml+="<td>"+num1+"</td>"; 
					 trHtml+="<td>"+item.PRICE_2+"</td>"; 
					 var num2=formatNumber((item.PRICE_2/ item.PRICE_TOTAL) * 100) + '%'
					 trHtml+="<td>"+num2+"</td>"; 
					 trHtml+="<td>"+item.PRICE_3+"</td>"; 
					 var num3=formatNumber((item.PRICE_3/ item.PRICE_TOTAL) * 100) + '%'
					 trHtml+="<td>"+num3+"</td>"; 
					 trHtml+="<td>"+item.PRICE_4+"</td>"; 
					 var num4=formatNumber((item.PRICE_4/ item.PRICE_TOTAL) * 100) + '%'
					 trHtml+="<td>"+num4+"</td>"; 
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			//alert($("#resultPrint").html())
			lodopPrinterTable("resultPrint","开始打印","列表",true);
	    },true,manager);
		return;
	 }
	 
	 function dateType(){
		 var date_type = $("#date_type").val();
		 if(date_type == 0){
			     $('#year_month1').unbind('focus');
				 $('#year_month1').bind('focus',function(){
				 	WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy'});
				 });
				 $('#year_month2').unbind('focus');
				 $('#year_month2').bind('focus',function(){
				 	WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy'});
				 });
		 }else{
				 $('#year_month1').unbind('focus');
				 $('#year_month1').bind('focus',function(){
					 WdatePicker({isShowClear:true,readOnly:false,dateFmt:'MM'});
				 });
				 $('#year_month2').unbind('focus');
				 $('#year_month2').bind('focus',function(){
					 WdatePicker({isShowClear:true,readOnly:false,dateFmt:'MM'});
				 });
		 }
	 }
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
<form name="form1" method="post"  id="form1" >
	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">期间类型：</td>
            <td align="left" class="l-table-edit-td">
            	<select id="date_type" name="data_type" onChange="dateType();">
            		<option ></option>
            		<option value="0">年</option>
            		<option value="1">月</option>
            	</select>
            </td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">期间：</td>
            <td align="left" class="l-table-edit-td"><input name="year_month1" type="text" id="year_month1" class="Wdate" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">至：</td>
            <td align="left" class="l-table-edit-td"><input name="year_month2" type="text" id="year_month2" class="Wdate" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室类型：</td>
            <td align="left" class="l-table-edit-td"><input name="kind_code" type="text" id="kind_code" /></td>
         </tr> 
         <tr>
         	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室性质：</td>
            <td align="left" class="l-table-edit-td"><input name="natur_code" type="text" id="natur_code" /></td>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;" >科室级次：</td>
            <td align="left" class="l-table-edit-td" colspan="3"><input name="dept_level" type="text" id="dept_level" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">收入级次：</td>
            <td align="left" class="l-table-edit-td">
            	<select id="income_level" name="income_level">
            		<option value="1">1级</option>
            		<option value="2">2级</option>
            		<option value="3">3级</option>
            	</select>
            </td>
         </tr>
    </table>
</form>
	<div id="maingrid"></div>
	<div id="resultPrint" style="display:none">
	   	<table width="100%">
			   	<thead>
				<tr>
					<th rowspan="2" width="200">科室编码</th>
					<th rowspan="2" width="200">科室名称</th>
					<th colspan="2" width="200">科室编码</th>
					<th colspan="2" width="200">科室编码</th>
					<th colspan="2" width="200">科室编码</th>
					<th colspan="2" width="200">科室编码</th>
				   	</tr>
				   	<tr>
				   	<th width="200">金额</th>
				   	<th width="200">比例</th>
				   	<th width="200">金额</th>
				   	<th width="200">比例</th>
				   	<th width="200">金额</th>
				   	<th width="200">比例</th>
				   	<th width="200">金额</th>
				   	<th width="200">比例</th>
				   	</tr>
			   	</thead>
			   	<tbody></tbody>
	   	</table>
   	</div>
</body>
</html>
