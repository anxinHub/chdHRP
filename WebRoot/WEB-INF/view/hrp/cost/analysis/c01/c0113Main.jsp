<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
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
		loadDict();
    	
    	loadHead(null);	//加载数据
    
    });
    //查询
    function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	  grid.options.parms.push({name:'begin_date',value:$("#begin_date").val()}); 
    	  grid.options.parms.push({name:'end_date',value:$("#end_date").val()});
    	//加载查询条件
    	grid.loadData(grid.where);
     }
   
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '项目', name: 'item_name', align: 'left',width:'20%'},
                     { display: '行次', name: 'item_code', align: 'center',width:'10%'},
                     { display: '医疗收入', name: '', align: 'center',width:'20%',
                    	 columns:[
                    	          {display:'本期',name:'',align:'right',columns:[
				                    	          {display:'1',name:'t_1',align:'right',formatter:'###,##0.00',
					                    	 			render : function(rowdata, rowindex,
																value) {
													 	return formatNumber(rowdata.t_1,2,1);
													}}
				                     	         ]
                      				},
						 			{display:'累计',name:'',align:'right',columns:[
				                    	          {display:'2',name:'t_2',align:'right',formatter:'###,##0.00',
				                         			 render : function(rowdata, rowindex,
				     										value) {
				     									 return formatNumber(rowdata.t_2,2,1);
				     									}}
				                     	         ]
                      				}
                    	         ]
					 },
					 { display: '财政基本补助收入', name: '', align: 'center',width:'20%',
                    	 columns:[
                    	          {display:'本期',name:'',align:'right',columns:[
				                    	          {display:'3',name:'t_3',align:'right',formatter:'###,##0.00',
					                    	 			render : function(rowdata, rowindex,
																value) {
													 	return formatNumber(rowdata.t_3,2,1);
													}}
				                     	         ]
                      				},
						 			{display:'累计',name:'',align:'right',columns:[
				                    	          {display:'4',name:'t_4',align:'right',formatter:'###,##0.00',
				                         			 render : function(rowdata, rowindex,
				     										value) {
				     									 return formatNumber(rowdata.t_4,2,1);
				     									}}
				                     	         ]
                      				}
                    	         ]
					 },
					 { display: '成本', name: '', align: 'center',width:'20%',
                    	 columns:[
                    	          {display:'本期',name:'',align:'right',columns:[
				                    	          {display:'5',name:'t_5',align:'right',formatter:'###,##0.00',
					                    	 			render : function(rowdata, rowindex,
																value) {
													 	return formatNumber(rowdata.t_5,2,1);
													}}
				                     	         ]
                      				},
						 			{display:'累计',name:'',align:'right',columns:[
				                    	          {display:'6',name:'t_6',align:'right',formatter:'###,##0.00',
				                         			 render : function(rowdata, rowindex,
				     										value) {
				     									 return formatNumber(rowdata.t_6,2,1);
				     									}}
				                     	         ]
                      				}
                    	         ]
					 },
					 { display: '收支结余', name: '', align: 'center',width:'20%',
                    	 columns:[
                    	          {display:'本期',name:'',align:'right',columns:[
				                    	          {display:'7',name:'t_7',align:'right',formatter:'###,##0.00',
					                    	 			render : function(rowdata, rowindex,
																value) {
													 	return formatNumber(rowdata.t_7,2,1);
													}}
				                     	         ]
                      				},
						 			{display:'累计',name:'',align:'right',columns:[
				                    	          {display:'8',name:'t_8',align:'right',formatter:'###,##0.00',
				                         			 render : function(rowdata, rowindex,
				     										value) {
				     									 return formatNumber(rowdata.t_8,2,1);
				     									}}
				                     	         ]
                      				}
                    	         ]
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAnalysisC0113.do',
                     width: '100%', height: '100%', checkbox: false,rownumbers:true,delayLoad:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
                     	/* { text: '生成', id:'create', click: createData,icon:'back' },
                     	{ line:true }, */
                     	{ text: '打印', id:'print', click: print,icon:'print' },
                     	{ line:true }
    				]}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function createData(){
 	   
    	$.ligerDialog.open({url: 'costAnalysisC0113AddPage.do?isCheck=false', height: 350,width: 500, title:'生成',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveCostIncomeDetail(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
    }
    function printData(){
 	   
    	$("#resultPrint > table > tbody").empty();
 		//有数据直接打印
 		if($("#resultPrint > table > tbody").html()!=""){
 			lodopPrinterTable("resultPrint","开始打印","医疗收益总表(包含财政收入)",true);
 			return;
 		}
 		//重新查询数据，避免分页导致打印数据不全
 		var manager = $.ligerDialog.waitting('系统正在准备打印数据,请稍候...');
 		
 		var printPara={
 				
 			usePager:false,
     		
 			begin_date:$("#begin_date").val(),
 			
 			end_date:$("#end_date").val(),

             
          };
 		ajaxJsonObjectByUrl("queryAnalysisC0113.do",printPara,function (responseData){
 			$.each(responseData.Rows,function(idx,item){ 
 				 var trHtml="<tr>";
                  trHtml+="<td>"+item.dept_code+"</td>"; 
                  trHtml+="<td>"+item.dept_name+"</td>"; 
                  trHtml+="<td>"+item.t_1+"</td>"; 
                  trHtml+="<td>"+item.t_2+"</td>";
                  trHtml+="<td>"+item.t_3+"</td>";
                  trHtml+="<td>"+item.t_4+"</td>";
                  trHtml+="<td>"+item.t_5+"</td>";
                  trHtml+="<td>"+item.t_6+"</td>";
 				 trHtml+="</tr>";
 				 $("#resultPrint > table > tbody").append(trHtml);
 			});
 			manager.close();
 			lodopPrinterTable("resultPrint","开始打印","医疗收益总表(包含财政收入)",true);
 	    },true,manager);
 		return;
    }
    
     function loadDict(){
    	 
         $("#begin_date").ligerTextBox({width:90});
         $("#end_date").ligerTextBox({width:90});
      	  
   	     autodate("#begin_date","yyyyMM");
   	     
   	     autodate("#end_date","yyyyMM");
 	}   
     
     //打印
//      function print(){
//   	   var exportPara = {
//  				usePager: false,
//  				begin_date: $("#begin_date").val(),
//  				end_date: $("#end_date").val()
//  			};
  		  
//   	   $.ajax({
//   		   url:"queryAnalysisC0113.do",
//   		   type:"post",
//   		   data:exportPara,
//   		   dataType:"JSON",
//   		   success:function(res){
  			  
//   			   var data={
//   					   headers:[
//   								{ x: 0, y: 0, rowSpan: 3, colSpan: 1, displayName: "项目", name: "item_name",size:110 },
//   								{ x: 1, y: 0, rowSpan: 3, colSpan: 1, displayName: "行次", name: " ",size:40 },
//   								{ x: 2, y: 0, rowSpan: 1, colSpan: 2, displayName: "医疗收入" },
//   								{ x: 2, y: 1, rowSpan: 1, colSpan: 1, displayName: "本期" },
//   								{ x: 3, y: 1, rowSpan: 1, colSpan: 1, displayName: "累计" },
//   								{ x: 2, y: 2, rowSpan: 1, colSpan: 1, displayName: "1", name: "t_1", formatter: "#,##0.00",size:100 },
//   								{ x: 3, y: 2, rowSpan: 1, colSpan: 1, displayName: "2", name: "t_2", formatter: "#,##0.00",size:110 },
//   								{ x: 4, y: 0, rowSpan: 1, colSpan: 2, displayName: "财政基本补助收入" },
//   								{ x: 4, y: 1, rowSpan: 1, colSpan: 1, displayName: "本期" },
//   								{ x: 5, y: 1, rowSpan: 1, colSpan: 1, displayName: "累计" },
//   								{ x: 4, y: 2, rowSpan: 1, colSpan: 1, displayName: "3", name: "t_3", formatter: "#,##0.00",size:100 },
//   								{ x: 5, y: 2, rowSpan: 1, colSpan: 1, displayName: "4", name: "t_4", formatter: "#,##0.00",size:110 },
//   								{ x: 6, y: 0, rowSpan: 1, colSpan: 2, displayName: "成本" },
//   								{ x: 6, y: 1, rowSpan: 1, colSpan: 1, displayName: "本期" },
//   								{ x: 7, y: 1, rowSpan: 1, colSpan: 1, displayName: "累计" },
//   								{ x: 6, y: 2, rowSpan: 1, colSpan: 1, displayName: "5", name: "t_5", formatter: "#,##0.00",size:100 },
//   								{ x: 7, y: 2, rowSpan: 1, colSpan: 1, displayName: "6", name: "t_6", formatter: "#,##0.00",size:110 },
//   								{ x: 8, y: 0, rowSpan: 1, colSpan: 2, displayName: "收支结余" },
//   								{ x: 8, y: 1, rowSpan: 1, colSpan: 1, displayName: "本期" },
//   								{ x: 9, y: 1, rowSpan: 1, colSpan: 1, displayName: "累计" },
//   								{ x: 8, y: 2, rowSpan: 1, colSpan: 1, displayName: "7", name: "t_7", formatter: "#,##0.00",size:100 },
//   								{ x: 9, y: 2, rowSpan: 1, colSpan: 1, displayName: "8", name: "t_8", formatter: "#,##0.00",size:110 },
  								
  								
//   					    ],
//   					    rows: res.Rows
//   			   }
  			  
//   			   viewPrint(data, "医疗收益总表(包含财政收入)");
//   		   },
//   		   error: function (res) {
//  					console.error(res);
//  				}
//   	   });
//      };
       function print(){
    	
    	if(grid.getData().length==0){
    		
			$.ligerDialog.error("请先查询数据！");
			
			return;
		}
    	
    	var heads={
 	    		//"isAuto":true,//系统默认，页眉显示页码
 	    		"rows": [
 		          {"cell":0,"value":"统计日期："+$("#begin_date").val()+"至"+$("#end_date").val(),"colSpan":"5"}
 	    	]};
 	       var printPara={
 	      		title: "医疗收益总表（包含财政收入）",//标题
 	      		columns: JSON.stringify(grid.getPrintColumns()),//表头
 	      		class_name: "com.chd.hrp.cost.service.analysis.c01.C01Service",
 	   			method_name: "queryC0113Print",
 	   			bean_name: "c01Service",
 	   		    heads: JSON.stringify(heads)//表头需要打印的查询条件,可以为空
 	   			
 	       	};
 	      //执行方法的查询条件
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
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">期间：</td>
            <td align="left" class="l-table-edit-td" ><input name="begin_date" type="text" id="begin_date" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
            <td align="right" class="l-table-edit-td" style="padding-left:5px;">至：</td>
            <td align="left" class="l-table-edit-td"><input name="end_date" type="text" id="end_date" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
            <td align="left"></td>
          
        </tr> 
    </table>

	<div id="maingrid"></div>
	<div id="resultPrint" style="display:none">
		<table width="100%">
			   	<thead>
			   	     <tr>
			   	     <th width="100" rowspan="3">项目</th>
				     <th width="100" rowspan="3">行次</th>
				     <th width="100" colspan="2" align="center">医疗收入</th>
				     <th width="100" colspan="2" align="center">财政基本补助收入</th>
				     <th width="100" colspan="2" align="center">成本</th>
				     <th width="100" colspan="2" align="center">收支结余</th>
			   	     </tr>
			   	     <tr>
			   	     <th width="100">本期</th>
				     <th width="100">累计</th>
				     <th width="100">本期</th>
				     <th width="100">累计</th>
				     <th width="100">本期</th>
				     <th width="100">累计</th>
				     <th width="100">本期</th>
				     <th width="100">累计</th>
			   	     </tr>
			   	     <tr>
			   	     <th width="100">1</th>
				     <th width="100">2</th>
				     <th width="100">3</th>
				     <th width="100">4</th>
				     <th width="100">5</th>
				     <th width="100">6</th>
				     <th width="100">7</th>
				     <th width="100">8</th>
			   	    </tr>
			   	</thead>
			   	<tbody></tbody>
	   	</table>
   	</div>
</body>
</html>
