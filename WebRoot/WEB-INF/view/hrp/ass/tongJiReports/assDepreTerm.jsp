<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<!--资产折旧年限页面  -->

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
     
    	  grid.options.parms.push({name:'ass_nature',value:liger.get("ass_nature").getValue()}); 
    	  grid.options.parms.push({name:'ass_card_no',value:$("#ass_card_no").val()});
    	  
		 
    	//加载查询条件
    	grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                   
	                     { display: '资产卡片号', name: 'ass_card_no', align: 'left',
				 			 
					     },
	                     { display: '资产编码', name: 'ass_code', align: 'left',
				 			 
					 	 },
 						 { display: '资产名称', name: 'ass_name', align: 'left',
				 			 
					 	 },
	                     { display: '规格', name: 'ass_spec', align: 'left',
				 			 
					 	 },
		                 { display: '型号', name: 'ass_mondl', align: 'left',
					 			 
						 },	
	                     //{ display: '资产性质', name: 'ass_nature', align: 'left',
				 			 
					     //},
	                     { display: '品牌', name: 'ass_brand', align: 'left'
				 			 
					 	 },
 						 { display: '卡片原值', name: 'price', align: 'right',totalSummary:{render: function (suminf, column, cell)
 			                   {
 			                   return '<div>' + formatNumber(suminf.sum,'${ass_05005 }',1) + '</div>';
 			               },
 			               align: 'right'},
					 		render : function(rowdata, rowindex,
									value) {
								 return formatNumber(rowdata.price,'${ass_05006 }',1);
							},formatter:'###,##0.00'
					 	 },
					 	{ display: '折旧年限', name: 'acc_depre_amount', align: 'left',
				 			 
					 	 },
					 	 
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'../tongJiReports/queryAssDepreTerm.do?ass_nature='+liger.get("ass_nature").getValue(),
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad :true,
                     selectRowButtonOnly:true,
                     toolbar: { items: [
				                     	{ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' },
				                     	{ line:true },
				                     	{ text: '打印（<u>P</u>）', id:'print', click: printDate,icon:'print' },
						                { line:true },
// 				    	                { text: '导出Excel（<u>E</u>）', id:'export', click: exportExcel,icon:'pager' },
// 						                { line:true },
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
	 	function printDate(){
	 		if(grid.getData().length==0){
	   			$.ligerDialog.error("请先查询数据！");
	   			return;
	   		}
	 		
	 		var time=new Date();
	    	var date=time.getFullYear()+"年"+(time.getMonth()+1)+"月"+time.getDate()+"日";
	    	var heads={
	        		"isAuto":true,//系统默认，页眉显示页码
	        		"rows": [
	    	          {"cell":0,"value":"资产性质："},
	    	          {"cell":1,"value":liger.get("ass_nature").getText().split(" ")[1]},
	    	          {"cell":6,"value":"制表日期:"},
	  				  {"cell":7,"value":date} ,
	    	          
	        	]}; 
	    	//表尾
	    	var foots = {
	    			rows: [
	    				{"cell":6,"value":"制表人:"},
	    				{"cell":7,"value":"${sessionScope.user_name}"},
	    			]
	    		}; 
	 		var printPara={
	 				title: "资产折旧年限",//标题
	 				columns: JSON.stringify(grid.getPrintColumns()),//表头
	 				class_name: "com.chd.hrp.ass.service.tongJiReports.AssDepreExpireService",
	 				method_name: "queryAssDepreTermPrint",
	 				bean_name: "assDepreExpireService" ,
	 				heads: JSON.stringify(heads), //表头需要打印的查询条件,可以为空
	 				foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 
	 				};
	 		
	 		$.each(grid.options.parms,function(i,obj){
	 				printPara[obj.name]=obj.value;
	 		});
	 		
	 		officeGridPrint(printPara);
	 	   		
	 	}
	 
	 //导出数据
	 function exportExcel(){
		//有数据直接导出
		if($("#resultPrint > table > tbody").html()!=""){
			lodopExportExcel("resultPrint","导出Excel","资产折旧年限查询.xls",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');

		var exportPara={
			usePager:false,
			ass_nature : liger.get("ass_nature").getValue(),
	  	    ass_card_no : $("#ass_card_no").val()
         };
		ajaxJsonObjectByUrl("queryAssDepreTerm.do?ass_nature="+ ger.get("ass_nature").getValue(),exportPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
				 trHtml+="<td>"+item.ass_card_no+"</td>"; 
				 trHtml+="<td>"+item.ass_code+"</td>"; 
				 trHtml+="<td>"+item.ass_name+"</td>"; 
				 trHtml+="<td>"+item.ass_spec+"</td>"; 
				 trHtml+="<td>"+item.ass_mondl+"</td>"; 
				 trHtml+="<td>"+item.ass_amount+"</td>"; 
				 trHtml+="<td>"+item.price+"</td>"; 
				 trHtml+="<td>"+item.acc_depre_amount+"</td>"; 
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			lodopExportExcel("resultPrint","导出Excel","资产折旧年限查询.xls",true);
	    },true,manager);
		return;
	 }	
	 
	    function loadDict(){
            //字典下拉框 
            
              $("#ass_nature").ligerComboBox({
          	url: '../queryAssNaturs.do?isCheck=false',
          	valueField: 'id',
           	textField: 'text', 
           	selectBoxWidth: '160',
          	autocomplete: true,
          	width: '160',
          	onSelected :function(id,text){ 
          		loadHead();
          		
          	}
 		  });
     
		$("#ass_nature").ligerTextBox({
    		
    		width : 160
    		
    	});
		
        $("#ass_card_no").ligerTextBox({width:160});
        
         }  
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
     <table cellpadding="0" cellspacing="0" class="l-table-edit" >
     	<tr>
     		<td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产性质：</td> 
            <td align="left" class="l-table-edit-td">
            	<input name="ass_nature" type="text" id="ass_nature"/>
            </td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产卡片号：</td> 
            <td align="left" class="l-table-edit-td">
            	<input name="ass_card_no" type="text" id="ass_card_no"/>
            </td>
            <td align="left"></td>
     	</tr>
       
    </table>

	<div id="maingrid"></div>
</body>
</html>
