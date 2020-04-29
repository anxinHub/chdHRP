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
                    {display:'医院名称',name:'hos_name',align:'left'},
					{display:'门诊人次',name:'t_1',align:'right'},
					{ display: '单位收入', name: 't_2', align: 'right',formatter:'###,##0.00',
            			 render : function(rowdata, rowindex,
									value) {
								 return formatNumber(rowdata.t_2,2,1);
								}
					 },
					 { display: '单位变动成本', name: 't_3', align: 'right',formatter:'###,##0.00',
             			 render : function(rowdata, rowindex,
									value) {
								 return formatNumber(rowdata.t_3,2,1);
								}
					 },
					 { display: '单位收益', name: 't_4', align: 'right',formatter:'###,##0.00',
             			 render : function(rowdata, rowindex,
									value) {
								 return formatNumber(rowdata.t_4,2,1);
								}
					 },
					 { display: '医疗收入', name: 't_5', align: 'right',formatter:'###,##0.00',
             			 render : function(rowdata, rowindex,
									value) {
								 return formatNumber(rowdata.t_5,2,1);
								}
					 },
					 { display: '固定成本', name: 't_6', align: 'right',formatter:'###,##0.00',
             			 render : function(rowdata, rowindex,
									value) {
								 return formatNumber(rowdata.t_6,2,1);
								}
					 },
					 { display: '变动成本', name: 't_7', align: 'right',formatter:'###,##0.00',
             			 render : function(rowdata, rowindex,
									value) {
								 return formatNumber(rowdata.t_7,2,1);
								}
					 },
					 { display: '保本诊次', name: 't_8', align: 'right',formatter:'###,##0.00',
             			 render : function(rowdata, rowindex,
									value) {
								 return formatNumber(rowdata.t_8,2,1);
								}
					 },
					 { display: '保本收入', name: 't_9', align: 'right',formatter:'###,##0.00',
             			 render : function(rowdata, rowindex,
									value) {
								 return formatNumber(rowdata.t_9,2,1);
								}
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAnalysisC0906.do',
                     width: '100%', height: '100%', checkbox: false,rownumbers:true,delayLoad:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
                     	/* { text: '生成', id:'add', click: itemclick,icon:'add' },
                     	{ line:true }, */
                     	{ text: '打印', id:'print', click: print,icon:'print' },
                     	{ line:true }
    				]}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function itemclick(item){ 
        if(item.id)
        {
            switch (item.id)
            {
                case "add":
                	
              		$.ligerDialog.open({url: 'costAnalysisC0906AddPage.do?isCheck=false', height: 350,width: 500, title:'生成',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveCostIncomeDetail(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
              		return;
            }   
        }
        
    }
    function printData(){
    	 $("#resultPrint > table > tbody").empty();
  		//有数据直接打印
  		if($("#resultPrint > table > tbody").html()!=""){
  			lodopPrinterTable("resultPrint","开始打印","医技盈亏平衡分析",true);
  			return;
  		}
  		//重新查询数据，避免分页导致打印数据不全
  		var manager = $.ligerDialog.waitting('系统正在准备打印数据,请稍候...');
  		
  		var printPara={
  				
  			usePager:false,
      		
  			acc_year:$("#acc_year").val(),
  				
  			begin_date:$("#begin_date").val(),
  			
  			end_date:$("#end_date").val()
              
           };
  		ajaxJsonObjectByUrl("queryAnalysisC0906.do",printPara,function (responseData){
  			$.each(responseData.Rows,function(idx,item){ 
  				 var trHtml="<tr>";
                   trHtml+="<td>"+item.hos_name+"</td>"; 
                   trHtml+="<td>"+item.t_1+"</td>"; 
                   trHtml+="<td>"+item.t_2+"</td>";
                   trHtml+="<td>"+item.t_3+"</td>";
                   trHtml+="<td>"+item.t_4+"</td>";
                   trHtml+="<td>"+item.t_5+"</td>";
                   trHtml+="<td>"+item.t_6+"</td>";
                   trHtml+="<td>"+item.t_7+"</td>";
                   trHtml+="<td>"+item.t_8+"</td>";
                   trHtml+="<td>"+item.t_9+"</td>";
  				 trHtml+="</tr>";
  				 $("#resultPrint > table > tbody").append(trHtml);
  			});
  			manager.close();
  			//alert($("#resultPrint").html())
  			lodopPrinterTable("resultPrint","开始打印","医技盈亏平衡分析",true);
  	    },true,manager);
  		return;
    }
    
     function loadDict(){
      	$("#begin_date").ligerTextBox({width:120});
      	$("#end_date").ligerTextBox({width:120});

     	  
     	    autodate("#begin_date","yyyyMM");
     	    
     	    autodate("#end_date","yyyyMM");
 	}    
     
//      function  print(){
//      	//设置表头
//     	 var exportPara = {
//   				usePager: false,
//   				begin_date: $("#begin_date").val(),
//   				end_date: $("#end_date").val()
//   			};
   		  
//    	   $.ajax({
//    		   url:"queryAnalysisC0906.do",
//    		   type:"post",
//    		   data:exportPara,
//    		   dataType:"JSON",
//    		   success:function(res){
   			  
//    			   var data={
//    					   headers:[
//    								{ x: 0, y: 0, rowSpan: 1, colSpan: 1, displayName: "医院名称", name: "hos_name",size:150 },
//    								{ x: 1, y: 0, rowSpan: 1, colSpan: 1, displayName: "门诊人次",name: "t_1", formatter: "#,##0.00",size:110 },
//    								{ x: 2, y: 0, rowSpan: 1, colSpan: 1, displayName: "单位收入", name: "t_2", formatter: "#,##0.00",size:110 },
//    								{ x: 3, y: 0, rowSpan: 1, colSpan: 1, displayName: "单位变动成本", name: "t_3", formatter: "#,##0.00",size:110 },
//    								{ x: 4, y: 0, rowSpan: 1, colSpan: 1, displayName: "单位收益", name: "t_4", formatter: "#,##0.00",size:110 },
//    								{ x: 5, y: 0, rowSpan: 1, colSpan: 1, displayName: "医疗收入", name: "t_5", formatter: "#,##0.00",size:110 },
//    								{ x: 6, y: 0, rowSpan: 1, colSpan: 1, displayName: "固定成本", name: "t_6", formatter: "#,##0.00",size:110 },
//    								{ x: 7, y: 0, rowSpan: 1, colSpan: 1, displayName: "变动成本",name: "t_7", formatter: "#,##0.00",size:110},  
//    								{ x: 8, y: 0, rowSpan: 1, colSpan: 1, displayName: "保本诊次", name: "t_8", formatter: "#,##0.00",size:110 },
//    								{ x: 8, y: 0, rowSpan: 1, colSpan: 1, displayName: "保本收入", name: "t_9", formatter: "#,##0.00",size:110 },
//    					    ],
//    					    rows: res.Rows
//    			   }
   			  
//    			   viewPrint(data, "医技盈亏平衡分析");
//    		   },
//    		   error: function (res) {
//  					console.error(res);
//  				}
//    	   });
//       }
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
 	      		title: "医技盈亏平衡分析(集团)",//标题
 	      		columns: JSON.stringify(grid.getPrintColumns()),//表头
 	      		class_name: "com.chd.hrp.cost.service.analysis.c09.C09Service",
 	   			method_name: "queryC0906Print",
 	   			bean_name: "c09Service",
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
			   	     <th width="200">医院名称</th>
				     <th width="200">门诊人次</th>
				     <th width="200">单位收入</th>
				     <th width="200">单位变动成本</th>
				     <th width="200">医疗收益</th>
				     <th width="200">医疗收入</th>
				     <th width="200">固定成本</th>
				     <th width="200">变动成本</th>
				     <th width="200">保本诊次</th>
				     <th width="200">保本收入</th>
			   	    </tr>
			   	</thead>
			   	<tbody></tbody>
	   	</table>
   	</div>
</body>
</html>
