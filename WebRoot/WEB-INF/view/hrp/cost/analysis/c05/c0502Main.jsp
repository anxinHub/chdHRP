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
    /*      grid.options.parms.push({name:'acc_year',value:$("#acc_year").val()}); 
    	  grid.options.parms.push({name:'acc_month',value:$("#acc_month").val()});  */
    	  grid.options.parms.push({name:'year_month_begin',value:$("#year_month_begin").val()}); 
    	/*   grid.options.parms.push({name:'year_month_end',value:$("#year_month_end").val()}); */
    	//加载查询条件
    	grid.loadData(grid.where);
     }
   
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '项目编码', name: 'item_code', align: 'left',width:'10%'},
                     { display: '项目名称', name: 'item_name', align: 'left',width:'10%'},
                    /*  { display: '行次', name: '', align: 'center',width:'5%'}, */
                     { display: '本期', name: 't_1', align: 'right',width:'8%',formatter:'###,##0.00',
                    	 render : function(rowdata, rowindex,
									value) {
						 	return formatNumber(rowdata.t_1,2,1);
						}},
                     { display: '与上年同期比较', name: '', align: 'center',
                    	 columns:[
                    	          {display:'上年同期',name:'',align:'right',columns:[
				                    	          {display:'2',name:'t_2',align:'right',formatter:'###,##0.00',
					                    	 			render : function(rowdata, rowindex,
																value) {
													 	return formatNumber(rowdata.t_2,2,1);
													}}
				                     	         ]
                      				},
						 			{display:'差异',name:'',align:'right',columns:[
				                    	          {display:'3',name:'t_3',align:'right',formatter:'###,##0.00',
				                         			 render : function(rowdata, rowindex,
				     										value) {
				     									 return formatNumber(rowdata.t_3,2,1);
				     									}}
				                     	         ]
                      				},
                      				{display:'差异率',name:'',align:'right',
                      					columns:[
					                    	          {display:'4',name:'t_4',align:'right',formatter:'###,##0.00',
					                         			 render : function(rowdata, rowindex,
					     										value) {
					     									 return formatNumber(rowdata.t_4,2,1);
					     									}}
					                     	         ]
                      				}
                    	         ]
					 },
					 { display: '与上期比较', name: '', align: 'center',
                    	 columns:[
                    	          {display:'上期',name:'',align:'right',columns:[
				                    	          {display:'5',name:'t_5',align:'right',formatter:'###,##0.00',
					                    	 			render : function(rowdata, rowindex,
																value) {
													 	return formatNumber(rowdata.t_5,2,1);
													}}
				                     	         ]
                      				},
						 			{display:'差异',name:'',align:'right',columns:[
				                    	          {display:'6',name:'t_6',align:'right',formatter:'###,##0.00',
				                         			 render : function(rowdata, rowindex,
				     										value) {
				     									 return formatNumber(rowdata.t_6,2,1);
				     									}}
				                     	         ]
                      				},
                      				{display:'差异率',name:'',align:'right',
                      					columns:[
					                    	          {display:'7',name:'t_7',align:'right',formatter:'###,##0.00',
					                         			 render : function(rowdata, rowindex,
					     										value) {
					     									 return formatNumber(rowdata.t_7,2,1);
					     									}}
					                     	         ]
                      				}
                    	         ]
					 },
					 { display: '与预算比较', name: '', align: 'center',
                    	 columns:[
                    	          {display:'预算',name:'',align:'right',columns:[
				                    	          {display:'8',name:'t_8',align:'right',formatter:'###,##0.00',
					                    	 			render : function(rowdata, rowindex,
																value) {
													 	return formatNumber(rowdata.t_8,2,1);
													}}
				                     	         ]
                      				},
						 			{display:'差异',name:'',align:'right',columns:[
				                    	          {display:'9',name:'t_9',align:'right',formatter:'###,##0.00',
				                         			 render : function(rowdata, rowindex,
				     										value) {
				     									 return formatNumber(rowdata.t_9,2,1);
				     									}}
				                     	         ]
                      				},
                      				{display:'差异率',name:'',align:'right',
                      					columns:[
					                    	          {display:'10',name:'t_10',align:'right',formatter:'###,##0.00',
					                         			 render : function(rowdata, rowindex,
					     										value) {
					     									 return formatNumber(rowdata.t_10,2,1);
					     									}}
					                     	         ]
                      				}
                    	         ]
					 },
					 { display: '与平均比较', name: '', align: 'center',
                    	 columns:[
                    	          {display:'平均',name:'',align:'right',columns:[
				                    	          {display:'11',name:'t_11',align:'right',formatter:'###,##0.00',
					                    	 			render : function(rowdata, rowindex,
																value) {
													 	return formatNumber(rowdata.t_11,2,1);
													}}
				                     	         ]
                      				},
						 			{display:'差异',name:'',align:'right',columns:[
				                    	          {display:'12',name:'t_12',align:'right',formatter:'###,##0.00',
				                         			 render : function(rowdata, rowindex,
				     										value) {
				     									 return formatNumber(rowdata.t_12,2,1);
				     									}}
				                     	         ]
                      				},
                      				{display:'差异率',name:'',align:'right',
                      					columns:[
					                    	          {display:'13',name:'t_13',align:'right',formatter:'###,##0.00',
					                         			 render : function(rowdata, rowindex,
					     										value) {
					     									 return formatNumber(rowdata.t_13,2,1);
					     									}}
					                     	         ]
                      				}
                    	         ]
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAnalysisC0502.do',
                     width: '100%', height: '100%', checkbox: false,rownumbers:true,delayLoad:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
                     	/* { text: '生成', id:'create', click: createData,icon:'back' },
                     	{ line:true }, */
                     	{ text: '打印', id:'print', click: printData,icon:'print' },
                     	{ line:true } 
    				]}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function createData(){
  	   
    	$.ligerDialog.open({url: 'costAnalysisC0502AddPage.do?isCheck=false', height: 300,width: 500, title:'生成',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveCostIncomeDetail(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
    }
//     function printData(){
 	   
//     	var printPara = {
// 			/* 	usePager: true,
// 				acc_year: $("#acc_year").val(),
// 				acc_month: $("#acc_month").val() */
// 				usePager:false,	   		
// 				year_month_begin: $("#year_month_begin").val(),
// 				year_month_end: $("#year_month_end").val()
// 			};
// 		$.ajax({
// 			url: "queryAnalysisC0502.do?isCheck=false",
// 			type: "post",
// 			dataType: "JSON",
// 			data: printPara,
// 			success: function (res) {
// 				var data = {
						
// 					headers: [
// 						{ x: 0, y: 0, rowSpan: 3, colSpan: 1, displayName: "项目编码", name: "item_code",size:80 },
// 						{ x: 1, y: 0, rowSpan: 3, colSpan: 1, displayName: "项目名称", name: "item_name",size:220 },
// 						{ x: 2, y: 0, rowSpan: 3, colSpan: 1, displayName: "本期",name: 't_1',formatter: "#,##0.00",size:100 },
						
// 						{ x: 3, y: 0, rowSpan: 1, colSpan: 3, displayName: "与上年同期比较"},
// 						{ x: 3, y: 1, rowSpan: 1, colSpan: 1, displayName: "上年同期"},
// 						{ x: 4, y: 1, rowSpan: 1, colSpan: 1, displayName: "差异"},
// 						{ x: 5, y: 1, rowSpan: 1, colSpan: 1, displayName: "差异率"},
// 						{ x: 3, y: 2, rowSpan: 1, colSpan: 1, displayName: "2", name: "t_2",formatter: "#,##0.00",size:85},
// 						{ x: 4, y: 2, rowSpan: 1, colSpan: 1, displayName: "3", name: "t_3",formatter: "#,##0.00",size:85},
// 						{ x: 5, y: 2, rowSpan: 1, colSpan: 1, displayName: "4", name: "t_4",formatter: "#,##0.00",size:50},
						
						
// 						{ x: 6, y: 0, rowSpan: 1, colSpan: 3, displayName: "与上期比较"},
// 						{ x: 6, y: 1, rowSpan: 1, colSpan: 1, displayName: "上期"},
// 						{ x: 7, y: 1, rowSpan: 1, colSpan: 1, displayName: "差异"},
// 						{ x: 8, y: 1, rowSpan: 1, colSpan: 1, displayName: "差异率"},
// 						{ x: 6, y: 2, rowSpan: 1, colSpan: 1, displayName: "5", name: "t_5",formatter: "#,##0.00",size:85},
// 						{ x: 7, y: 2, rowSpan: 1, colSpan: 1, displayName: "6", name: "t_6",formatter: "#,##0.00",size:85},
// 						{ x: 8, y: 2, rowSpan: 1, colSpan: 1, displayName: "7", name: "t_7",formatter: "#,##0.00",size:50},
						
						
// 						{ x: 9, y: 0, rowSpan: 1, colSpan: 3, displayName: "与预算比较"},
// 						{ x: 9, y: 1, rowSpan: 1, colSpan: 1, displayName: "预算"},
// 						{ x: 10, y: 1, rowSpan: 1, colSpan: 1, displayName: "差异"},
// 						{ x: 11, y: 1, rowSpan: 1, colSpan: 1, displayName: "差异率"},
// 						{ x: 9, y: 2, rowSpan: 1, colSpan: 1, displayName: "8", name: "t_8",formatter: "#,##0.00",size:85},
// 						{ x: 10, y: 2, rowSpan: 1, colSpan: 1, displayName: "9", name: "t_9",formatter: "#,##0.00",size:85},
// 						{ x: 11, y: 2, rowSpan: 1, colSpan: 1, displayName: "10", name: "t_10",formatter: "#,##0.00",size:50},
						
// 						{ x: 12, y: 0, rowSpan: 1, colSpan: 3, displayName: "与平均比较"},
// 						{ x: 12, y: 1, rowSpan: 1, colSpan: 1, displayName: "平均"},
// 						{ x: 13, y: 1, rowSpan: 1, colSpan: 1, displayName: "差异"},
// 						{ x: 14, y: 1, rowSpan: 1, colSpan: 1, displayName: "差异率"},
// 						{ x: 12, y: 2, rowSpan: 1, colSpan: 1, displayName: "11", name: "t_11",formatter: "#,##0.00",size:85},
// 						{ x: 13, y: 2, rowSpan: 1, colSpan: 1, displayName: "12", name: "t_12",formatter: "#,##0.00",size:85},
// 						{ x: 14, y: 2, rowSpan: 1, colSpan: 1, displayName: "13", name: "t_13",formatter: "#,##0.00",size:50}
// 					],
// 					rows: res.Rows
// 				}

// 				viewPrint(data, "医疗成本比较分析表");
// 			},
// 			error: function (res) {
// 				console.error(res);
// 			}
// 		})
//     }

       function printData(){
    	
    	if(grid.getData().length==0){
    		
			$.ligerDialog.error("请先查询数据！");
			
			return;
		}
    	
    	var heads={
 	    		//"isAuto":true,//系统默认，页眉显示页码
 	    		"rows": [
 		          {"cell":0,"value":"统计日期："+$("#year_month_begin").val()+"至"+$("#year_month_end").val(),"colSpan":"5"}
 	    	]};
 	       var printPara={
 	      		title: "医疗成本比较分析表(医院)",//标题
 	      		columns: JSON.stringify(grid.getPrintColumns()),//表头
 	      		class_name: "com.chd.hrp.cost.service.analysis.c05.C05Service",
 	   			method_name: "queryC0502Print",
 	   			bean_name: "c05Service",
 	   		    heads: JSON.stringify(heads)//表头需要打印的查询条件,可以为空
 	   			
 	       	};
 	      //执行方法的查询条件
 		  $.each(grid.options.parms,function(i,obj){
 			printPara[obj.name]=obj.value;
  	      });
 		
  	     officeGridPrint(printPara);

   		
    }
    
     function loadDict(){
     	/* 
    	 $("#acc_year").ligerTextBox({ width:120 });
    	  $("#acc_month").ligerTextBox({ width:90 });
      	

    	  autodate("#acc_year","yyyy");
    	  
    	  autodate("#acc_month","MM"); */
    	 $("#year_month_begin").ligerTextBox({ width:120 });
        /* 	$("#year_month_end").ligerTextBox({ width:120 }); */
        	
       	    autodate("#year_month_begin","yyyyMM");
          	 
       	   /*  autodate("#year_month_end","yyyyMM"); */

 	}     
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
           <!--  <td align="right" class="l-table-edit-td"  style="padding-left:20px;">核算年：</td>
            <td align="left" class="l-table-edit-td"><input name="acc_year" type="text" id="acc_year" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy'})"/></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">核算月：</td>
            <td align="left" class="l-table-edit-td" ><input name="acc_month" type="text" id="acc_month" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'MM'})"/></td>
         -->
         <td align="right" class="l-table-edit-td"  style="padding-left:20px;">期间：</td>
            <td align="left" class="l-table-edit-td"><input class="Wdate" name="year_month_begin" type="text" id="year_month_begin" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})" ltype="text"/></td>
           <!--  <td align="left" class="l-table-edit-td">至：</td>
            <td align="left" class="l-table-edit-td"><input class="Wdate" name="year_month_end" type="text" id="year_month_end" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})" ltype="text"/></td>
         -->
        </tr> 
    </table>

	<div id="maingrid"></div>
	<div id="resultPrint" style="display:none">
	   	<table width="100%">
			   	<thead>
				<tr>
					<th width="200">成本项目编码</th>
					<th width="200">成本项目名称</th>
				   	</tr>
			   	</thead>
			   	<tbody></tbody>
	   	</table>
   	</div>
</body>
</html>
