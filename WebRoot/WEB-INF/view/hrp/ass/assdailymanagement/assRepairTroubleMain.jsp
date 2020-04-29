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
<!-- 日常管理中维修故障统计报表 -->
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
       
        $("#trouble_level").ligerTextBox({width:160});
        
       
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1; 
 
        //根据表字段进行添加查询条件  
    	   
    	//grid.options.parms.push({name:'trouble_level',value:liger.get("trouble_level").getValue()}); 
    	  
    	//加载查询条件
    	grid.loadData(grid.where);
    	
		$("#resultPrint > table > tbody").empty();
		
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [  
                     
                     { display: '故障级别', name: 'troublelevel', align: 'left' 
					 		
					 		},
                    
                     { display: '故障统计', name: 'total', align: 'left' 
					 		}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAssRepairTrouble.do',
                      width:'100%',height: '100%', checkbox: true,rownumbers:true,
                     isScroll  : true,
                     selectRowButtonOnly:true,
                     toolbar: { items: [
                     					{ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' },
                     					{ line:true },
                     					{ text: '打印（<u>P</u>）', id:'print', click: printDate,icon:'print' },
						                { line:true },
// 						                { text: '导出Excel（<u>E</u>）', id:'export', click: exportExcel,icon:'pager' },
				    				]},
    			 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
     
    
    //键盘事件
	  function loadHotkeys() {

		hotkeys('Q', query);

		hotkeys('E', exportExcel);

		hotkeys('P', printDate);

	 }
//打印数据
// 	 function printDate(){
// 		//有数据直接打印
// 		if($("#resultPrint > table > tbody").html()!=""){
// 			lodopPrinterTable("resultPrint","开始打印","维修故障统计报表",true);
// 			return;
// 		}
		
// 		//重新查询数据，避免分页导致打印数据不全
// 		var manager = $.ligerDialog.waitting('系统正在准备打印数据,请稍候...');

// 		var printPara={
				
// 			usePager:false,
          
//          trouble_level:liger.get("trouble_level").getValue() 
         
//        };
		
// 		ajaxJsonObjectByUrl("queryAssRepairTrouble.do",printPara,function (responseData){
			
// 			$.each(responseData.Rows,function(idx,item){ 
				
// 				 var trHtml="<tr>";
				 
// 					 if (item.trouble_level ==  0) {
						 
// 						 trHtml+="<td>一级故障</td>";
						 
// 					 }else if (item.trouble_level ==  1) {
						 
// 						 trHtml+="<td>二级故障</td>";
						 
// 					 }else if (item.trouble_level ==  2) {
						 
// 						 trHtml+="<td>三级故障</td>";
						 
// 					 }else if (item.trouble_level ==  3) {
						 
// 						 trHtml+="<td>四级故障</td>";
						 
// 					 }
					 
// 					 trHtml+="<td>"+item.trouble_num+"</td>"; 
					 
// 				 trHtml+="</tr>";
				
// 				$("#resultPrint > table > tbody").append(trHtml);
				
// 			});
// 			manager.close();
			
// 			//alert($("#resultPrint").html())
			
// 			lodopPrinterTable("resultPrint","开始打印","维修故障统计报表",true);
			
// 	    },true,manager);
		
// 		return;
		
// 	 }
	  function printDate(){
	    	
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
	       			title:'维修故障统计',
	       			head:[
	    				{"cell":0,"value":"单位: ${hos_name}","colspan":colspan_num,"br":true}
	       			],
	       			foot:[
	    				{"cell":0,"value":"主管:","colspan":2,"br":false} ,
						{"cell":2,"value":"复核人:","colspan":colspan_num-2,"br":true},
						{"cell":0,"value":"制单人： ${user_name}","colspan":2,"br":false},
						{"cell":2,"value":"打印日期: " + cur_date,"colspan":colspan_num-2,"br":true}
	       			],
	       			columns:grid.getColumns(1),
	       			headCount:2,//列头行数
	       			autoFile:true,
	       			type:3
	       	};
	   		ajaxJsonObjectByUrl("queryAssRepairTrouble.do?isCheck=false", selPara, function (responseData) {
	   			printGridView(responseData,printPara);
			});

	   		
	    }
	 
	 //导出数据
	 function exportExcel(){
		//有数据直接导出
		
		if($("#resultPrint > table > tbody").html()!=""){
			
			lodopExportExcel("resultPrint","导出Excel","051201 维修故障统计报表.xls",true);
			
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');

		var exportPara={
				
			usePager:false,
			
			trouble_level:liger.get("trouble_level").getValue() 
         
       };
		
		ajaxJsonObjectByUrl("queryAssRepairTrouble.do",exportPara,function (responseData){
			
			$.each(responseData.Rows,function(idx,item){ 
				
				 var trHtml="<tr>";
					  
					 if (item.trouble_level ==  0) {
						 
						 trHtml+="<td>一级故障</td>";
						 
					 }else if (item.trouble_level ==  1) {
						 
						 trHtml+="<td>二级故障</td>";
						 
					 }else if (item.trouble_level ==  2) {
						 
						 trHtml+="<td>三级故障</td>";
						 
					 }else if (item.trouble_level ==  3) {
						 
						 trHtml+="<td>四级故障</td>";
					 }
					 
					 trHtml+="<td>"+item.trouble_num+"</td>"; 
					 
				 trHtml+="</tr>";
				  
				 $("#resultPrint > table > tbody").append(trHtml);
				 
			});
			
			manager.close();
			
			lodopExportExcel("resultPrint","导出Excel","051201 维修故障统计报表.xls",true);
			
	    },true,manager);
		
		return;
		
	 }	
	 
    function loadDict(){
            //字典下拉框
       
    	//故障级别
    	$("#trouble_level").ligerTextBox({
			width : 160
		});
     
         }  
    	  
    </script>

</head>

<body style="overflow: auto;" >
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
	
	<!-- <div class="l-layout-top" style="left: 0px; width: 100%; top: 0px; height: 200px;">
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
        </tr> 
        
          
        <tr>
           <td align="right" class="l-table-edit-td"  style="padding-left:20px;">故障级别：</td>
            <td align="left" class="l-table-edit-td"> 
            	<select id="trouble_level" name="trouble_level"> 
            			<option value=""></option>
                		<option value="0">一级故障</option>
                		<option value="1">二级故障</option>
                		<option value="2">三级故障</option>
                		<option value="3">四级故障</option>
                	</select>
            </td> 
        </tr>
    </table>
    </div> -->
    
	<div id="maingrid"  ></div>
	</div>
	<div id="resultPrint" style="display:none">
	   	<table width="100%">
			<thead>
		 
			<tr>
                 
                <th width="200">故障级别</th>	
                <th width="200">故障统计</th>	
                 
			</tr>
			   	</thead>
			   	<tbody></tbody>
	   	</table>
   	</div>
</body>
</html>
