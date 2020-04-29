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
         var acc_year=$("#acc_year").val();
         grid.options.parms.push({name:'acc_year',value:acc_year.substring(0,4)}); 
    	  grid.options.parms.push({name:'acc_month',value:acc_year.substring(4,6)}); 
    	//加载查询条件
    	grid.loadData(grid.where);
     }
   
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '成本项目', name: 'item_name', align: 'left'},
                    /*  { display: '行次', name: '', align: 'center',width:'5%'}, */
                     { display: '本期', name: 't_1', align: 'right',width:'8%',
                    	 render : function(rowdata, rowindex,
									value) {
						 	return formatNumber(rowdata.t_1,2,1);
						}},
                     { display: '与上年同期比较', name: '', align: 'center',
                    	 columns:[
                    	          {display:'上年同期',name:'',align:'right',columns:[
				                    	          {display:'2',name:'t_2',align:'right',
					                    	 			render : function(rowdata, rowindex,
																value) {
													 	return formatNumber(rowdata.t_2,2,1);
													}}
				                     	         ]
                      				},
						 			{display:'差异',name:'',align:'right',columns:[
				                    	          {display:'3',name:'t_3',align:'right',
				                         			 render : function(rowdata, rowindex,
				     										value) {
				     									 return formatNumber(rowdata.t_3,2,1);
				     									}}
				                     	         ]
                      				},
                      				{display:'差异率',name:'',align:'right',
                      					columns:[
					                    	          {display:'4',name:'t_4',align:'right',
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
				                    	          {display:'5',name:'t_5',align:'right',
					                    	 			render : function(rowdata, rowindex,
																value) {
													 	return formatNumber(rowdata.t_5,2,1);
													}}
				                     	         ]
                      				},
						 			{display:'差异',name:'',align:'right',columns:[
				                    	          {display:'6',name:'t_6',align:'right',
				                         			 render : function(rowdata, rowindex,
				     										value) {
				     									 return formatNumber(rowdata.t_6,2,1);
				     									}}
				                     	         ]
                      				},
                      				{display:'差异率',name:'',align:'right',
                      					columns:[
					                    	          {display:'7',name:'t_7',align:'right',
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
				                    	          {display:'8',name:'t_8',align:'right',
					                    	 			render : function(rowdata, rowindex,
																value) {
													 	return formatNumber(rowdata.t_8,2,1);
													}}
				                     	         ]
                      				},
						 			{display:'差异',name:'',align:'right',columns:[
				                    	          {display:'9',name:'t_9',align:'right',
				                         			 render : function(rowdata, rowindex,
				     										value) {
				     									 return formatNumber(rowdata.t_9,2,1);
				     									}}
				                     	         ]
                      				},
                      				{display:'差异率',name:'',align:'right',
                      					columns:[
					                    	          {display:'10',name:'t_10',align:'right',
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
				                    	          {display:'11',name:'t_11',align:'right',
					                    	 			render : function(rowdata, rowindex,
																value) {
													 	return formatNumber(rowdata.t_11,2,1);
													}}
				                     	         ]
                      				},
						 			{display:'差异',name:'',align:'right',columns:[
				                    	          {display:'12',name:'t_12',align:'right',
				                         			 render : function(rowdata, rowindex,
				     										value) {
				     									 return formatNumber(rowdata.t_12,2,1);
				     									}}
				                     	         ]
                      				},
                      				{display:'差异率',name:'',align:'right',
                      					columns:[
					                    	          {display:'13',name:'t_13',align:'right',
					                         			 render : function(rowdata, rowindex,
					     										value) {
					     									 return formatNumber(rowdata.t_13,2,1);
					     									}}
					                     	         ]
                      				}
                    	         ]
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAnalysisC0511.do',
                     width: '100%', height: '100%', checkbox: false,rownumbers:true,delayLoad:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
                     	{ text: '生成', id:'create', click: createData,icon:'back' },
                     	{ line:true },
                     	{ text: '打印', id:'print', click: printData,icon:'print' },
                     	{ line:true }
    				]}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function createData(){
    	$.ligerDialog.open({url: 'costAnalysisC0511AddPage.do?isCheck=false', height: 300,width: 500, title:'生成',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveCostIncomeDetail(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });  
    }
    function printData(){
 	   
    }
    
     function loadDict(){
     
     	$("#acc_year").ligerTextBox({width:120});

   	  autodate("#acc_year","yyyyMM");
   	  
   	 
 	}  
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">统计年月：</td>
            <td align="left" class="l-table-edit-td"><input name="acc_year" type="text" id="acc_year" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
            <td align="left"></td>
        </tr> 
    </table>

	<div id="maingrid"></div>
</body>
</html>
