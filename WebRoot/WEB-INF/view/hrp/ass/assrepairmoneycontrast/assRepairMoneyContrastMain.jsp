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
		 loadHotkeys(); 

        
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1; 
 
        //根据表字段进行添加查询条件  
    	  
    	  grid.options.parms.push({name:'fixed_dept_id',value:liger.get("fixed_dept_id").getValue().split("@")[0]}); 
    	  
    	  grid.options.parms.push({name:'repair_date1',value:$("#repair_date1").val()}); 
    	  grid.options.parms.push({name:'repair_date2',value:$("#repair_date2").val()}); 
    	  
    	  grid.options.parms.push({name:'ass_card_no',value:$("#ass_card_no").val()}); 
    	  
    	//加载查询条件
    	grid.loadData(grid.where);
    	
		$("#resultPrint > table > tbody").empty();
     }


    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [  
                     { display: '维修记录号', name: 'repairrecno', align: 'left',
					 		},
                     { display: '维修部门', name: 'dept_name', align: 'left'
					 		},  
                     { display: '资产卡片号', name: 'ass_card_no', align: 'left'
					 		},
                     { display: '维修工程师', name: 'repair_name', align: 'left'
					 		},
                     { display: '维修工时', name: 'repair_hours', align: 'left'
					 		},
                     { display: '维修费用', name: 'repair_money', align: 'right',
					 			
					 			render : function(rowdata, rowindex,
										value) {
									 return formatNumber(rowdata.repair_money,'${ass_05005}',1);
								}
					 		},
                     { display: '其他费用', name: 'other_money', align: 'right',
					 			render : function(rowdata, rowindex,
										value) {
									 return formatNumber(rowdata.other_money,'${ass_05005}',1);
								}
                     }
					 		
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAssRepairMoneyContrastMain.do?isCheck=false',
                      width:'100%',height: '100%', checkbox: true,rownumbers:true,
                     isScroll  : true,delayLoad :true,
                     selectRowButtonOnly:true,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
                     	{ text: '打印', id:'print', click: printDate,icon:'print' },
		                { line:true },
// 				    	                { text: '导出Excel', id:'export', click: exportExcel,icon:'pager' },
// 						                { line:true },
						                
						                
				    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code   + "|" + 
								rowdata.repair_rec_id 
							);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
   
   
    //键盘事件
	  function loadHotkeys() {

	 }
//打印数据
// 	 function printDate(){
// 		//有数据直接打印
// 		if($("#resultPrint > table > tbody").html()!=""){
// 			lodopPrinterTable("resultPrint","开始打印","资产维修记录",true);
// 			return;
// 		}
		
// 		//重新查询数据，避免分页导致打印数据不全
// 		var manager = $.ligerDialog.waitting('系统正在准备打印数据,请稍候...');

// 		var printPara={
				
// 			usePager:false,
// 	        fixed_dept_id : liger.get("fixed_dept_id").getValue().split("@")[0],
// 	        repair_date1 : $("#repair_date1").val(),
// 	        repair_date2 : $("#repair_date2").val(),
// 	    	ass_card_no : $("#ass_card_no").val()  
         
//        };
		
// 		ajaxJsonObjectByUrl("queryAssRepairMoneyContrastMain.do",printPara,function (responseData){
			
// 			$.each(responseData.Rows,function(idx,item){ 
				
// 				 var trHtml="<tr>";
					 
// 					 trHtml+="<td>"+item.repair_rec_no+"</td>"; 
					 
// 					 trHtml+="<td>"+item.fixed_dept_code+"</td>"; 
					 
// 					 trHtml+="<td>"+item.fixed_dept_name+"</td>";  
					 
// 					 trHtml+="<td>"+item.ass_card_no+"</td>"; 
					 
// 					 trHtml+="<td>"+item.repair_engr+"</td>"; 
					 
// 					 trHtml+="<td>"+item.repair_hours+"</td>"; 
					 
// 					 trHtml+="<td>"+item.repair_money+"</td>"; 
					 
// 					 trHtml+="<td>"+item.other_money+"</td>";  
					 
// 				 trHtml+="</tr>";
				  
// 				$("#resultPrint > table > tbody").append(trHtml);
				
// 			});
// 			manager.close();
			
// 			//alert($("#resultPrint").html())
			
// 			lodopPrinterTable("resultPrint","开始打印","资产维修记录",true);
			
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
	       			title:'维修费用对比统计',
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
	   		ajaxJsonObjectByUrl("queryAssRepairMoneyContrastMain.do?isCheck=false", selPara, function (responseData) {
	   			printGridView(responseData,printPara);
			});

	   		
	    }
	 
	 //导出数据
	 function exportExcel(){
		//有数据直接导出
		
		if($("#resultPrint > table > tbody").html()!=""){
			
			lodopExportExcel("resultPrint","导出Excel","051201 资产维修记录.xls",true);
			
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');

		var exportPara={
				
			usePager:false,
	        fixed_dept_id : liger.get("fixed_dept_id").getValue().split("@")[0],
	        repair_date1 : $("#repair_date1").val(),
	        repair_date2 : $("#repair_date2").val(),
	    	ass_card_no : $("#ass_card_no").val()  
         
       };
		
		ajaxJsonObjectByUrl("queryAssRepairMoneyContrastMain.do",exportPara,function (responseData){
			
			$.each(responseData.Rows,function(idx,item){ 
				
				 var trHtml="<tr>";
				 
					 trHtml+="<td>"+item.repair_rec_no+"</td>"; 
					 
					 trHtml+="<td>"+item.fixed_dept_code+"</td>"; 
					 
					 trHtml+="<td>"+item.fixed_dept_name+"</td>";  
					 
					 trHtml+="<td>"+item.ass_card_no+"</td>"; 
					 
					 trHtml+="<td>"+item.repair_engr+"</td>"; 
					 
					 trHtml+="<td>"+item.repair_hours+"</td>"; 
					 
					 trHtml+="<td>"+item.repair_money+"</td>"; 
					 
					 trHtml+="<td>"+item.other_money+"</td>";  
					 
				 trHtml+="</tr>";
				 
				 $("#resultPrint > table > tbody").append(trHtml);
				 
			});
			
			manager.close();
			
			lodopExportExcel("resultPrint","导出Excel","051201 资产维修记录.xls",true);
			
	    },true,manager);
		
		return;
		
	 }	
	 
    function loadDict(){
    	
		var param = {
            	query_key:''
        };
    	
            //字典下拉框
    	//维修部门
    	autocomplete("#fixed_dept_id", "../queryDeptDict.do?isCheck=false", "id","text", true, true,param,true);
    	
        $("#repair_date1").ligerTextBox({width:160});
        
        $("#repair_date2").ligerTextBox({width:160});
       
        $("#fixed_dept_id").ligerTextBox({width:160});
        
        $("#ass_card_no").ligerTextBox({width:160});
        
    }
    	  
    </script>

</head>

<body style="overflow: auto;" >
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
	
	<div class="l-layout-top" style="left: 0px; width: 100%; top: 0px; ">
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
        </tr> 
     
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产卡片号：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="ass_card_no" type="text" id="ass_card_no" ltype="text" validate="{required:true,maxlength:20}" />
            </td> 
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">维修部门：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="fixed_dept_id" type="text" id="fixed_dept_id" ltype="text" validate="{required:true,maxlength:20}" />
            </td> 
            <td align="left"></td>
           <td align="right" class="l-table-edit-td"  style="padding-left:20px;">维修日期：</td>
            <td align="left" class="l-table-edit-td"><input name="repair_date1" type="text" id="repair_date1" ltype="text" validate="{required:true,maxlength:20}" class="Wdate" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'repair_date2\')}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
            <td align="center" class="l-table-edit-td">至：</td>
            <td align="left" class="l-table-edit-td"><input name="repair_date2" type="text" id="repair_date2" ltype="text" validate="{required:true,maxlength:20}" class="Wdate" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'repair_date1\')}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
            <td align="left"></td>
        </tr>
      
    </table>
    </div>
    	<div id="resultPrint" style="display:none">
	   	<table width="100%">
			<thead>
		 
			<tr> 
                <th width="200">维修记录号</th>	
                <th width="200">维修部门编码</th>	
                <th width="200">维修部门名称</th>	 
                <th width="200">资产卡片号</th>	 
                <th width="200">维修工程师</th>	
                <th width="200">维修工时</th>	 
                <th width="200">维修费用</th>	
                <th width="200">其他费用</th>	 
			</tr>
			   	</thead>
			   	<tbody></tbody>
	   	</table>
   	</div>
    <div class="l-layout-bottom" style="left: 0px; width: 100%; top: 40px; height: 100%;">
	<div id="maingrid"  ></div>
	</div>

</body>
</html>
