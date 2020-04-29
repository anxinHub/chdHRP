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
    	$("#year_month1").ligerTextBox({ width:70 });
    	$("#year_month2").ligerTextBox({ width:70 });
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
		grid.options.parms.push({name:'acc_year1',value:$("#year_month1").val().substring(0,4)}); 
		grid.options.parms.push({name:'acc_month1',value:$("#year_month1").val().substring(4,7)}); 
		grid.options.parms.push({name:'acc_year2',value:$("#year_month2").val().substring(0,4)}); 
		grid.options.parms.push({name:'acc_month2',value:$("#year_month2").val().substring(4,7)});
		grid.options.parms.push({name:'kind_code',value:liger.get("kind_code").getValue()}); 
		grid.options.parms.push({name:'natur_code',value:liger.get("natur_code").getValue()}); 
		grid.options.parms.push({name:'dept_level',value:liger.get("dept_level").getValue()});
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '科室编码', name: 'dept_code', align: 'left'
					 },
					 { display: '科室名称', name: 'dept_name', align: 'left'
					 },
                     { display: '科室收入', name: 'dept_income', align: 'left'
					 },
                     { display: '科室成本', name: 'dept_cost', align: 'left'
					 },
                     { display: '科室收益', name: 'dept_profit', align: 'left'
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryBillingRevenueBalance.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad:true,
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
	  
	  
		 $("#resultPrint > table > tbody").empty();
		//有数据直接打印
		if($("#resultPrint > table > tbody").html()!=""){
			lodopPrinterTable("resultPrint","开始打印","列表",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备打印数据,请稍候...');
		
		var printPara={
				
			usePager:false,
			
			acc_year1:$("#year_month1").val().substring(0,4),
	           	
	        acc_month1:$("#year_month1").val().substring(4,7),
	        
	        acc_year2:$("#year_month2").val().substring(0,4),
           	
	        acc_month2:$("#year_month2").val().substring(4,7)
            
         };
		ajaxJsonObjectByUrl("queryBillingRevenueBalance.do",printPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
					 trHtml+="<td>"+item.acc_year+item.acc_month+"</td>"; 
					 trHtml+="<td>"+item.dept_code+"</td>"; 
	                 trHtml+="<td>"+item.dept_name+"</td>"; 
	                 trHtml+="<td>"+item.dept_income+"</td>"; 
	                 trHtml+="<td>"+item.dept_cost+"</td>"; 
	                 trHtml+="<td>"+item.dept_profit+"</td>";
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			lodopPrinterTable("resultPrint","开始打印","列表",true);
	    },true,manager);
		return;
	 }
	 
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
<form name="form1" method="post"  id="form1" >
	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;" >期间：</td>
            <td align="left" class="l-table-edit-td" ><input name="year_month1" type="text" id="year_month1" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
            <td align="left">至：</td>
            <td align="left" ><input name="year_month2" type="text" id="year_month2" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室类型：</td>
            <td align="left" class="l-table-edit-td"><input name="kind_code" type="text" id="kind_code" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室性质：</td>
            <td align="left" class="l-table-edit-td"><input name="natur_code" type="text" id="natur_code" /></td>
         </tr> 
        <tr>
        <!-- 
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">分摊级别：</td>
            <td align="left" class="l-table-edit-td">
            	<select id="shareLevel" name="shareLevel">
            		<option>一级分摊</option>
            		<option>二级分摊</option>
            		<option>三级分摊</option>
            		<option>直接成本</option>
            	</select>
            </td>
             -->
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;" >成本级次：</td>
            <td align="left" class="l-table-edit-td" colspan="3">
            	<select id="costLevel" name="costLevel">
            		<option value="0">医疗成本</option>
            		<option value="1">医疗全成本</option>
            		<option value="2">医院全成本</option>
            	</select>
            </td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;" >科室级次：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_level" type="text" id="dept_level" /></td>
        </tr> 
    </table>
</form>
	<div id="maingrid"></div>
	<div id="resultPrint" style="display:none">
	   	<table width="100%">
			   	<thead>
				<tr>
					<th width="200">年月</th>
					<th width="200">科室编码</th>
					<th width="200">科室名称</th>
					<th width="200">科室收入</th>
					<th width="200">科室成本</th>
					<th width="200">科室收益</th>
				   	</tr>
			   	</thead>
			   	<tbody></tbody>
	   	</table>
   	</div>
</body>
</html>
