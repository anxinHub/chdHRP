<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<!--资产逾龄役龄分析  -->
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
        /* 
			if($("#year_month").val() == ""){
    			
            	$.ligerDialog.error('请选择统计年月');
            	
            	return;
            	
            }  */
     
    	  grid.options.parms.push({name:'ass_naturs',value:liger.get("ass_nature").getValue()}); 
    	  grid.options.parms.push({name:'year_month',value:$("#year_month").val()});
    	  grid.options.parms.push({name:'bus_type',value:liger.get("bus_type_code").getValue()}); 
    	  grid.options.parms.push({name:'dept_id',value:liger.get("dept_id").getValue().split("@")[0]}); 
    	  grid.options.parms.push({name:'store_id',value:liger.get("store_id").getValue().split("@")[0]}); 
    	//加载查询条件
    	grid.loadData(grid.where);
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
					 { display: '生产厂家', name: 'fac_name', align: 'left',
			 			 
					 },	
					 { display: '仓库', name: 'store_name', align: 'left'
						 
				 	 },
           
                     { display: '科室', name: 'dept_name', align: 'left'
				 		 
				 	 },
				 	{ display: '使用年限', name: 'acc_depre_amount', align: 'left'
						 
				 	 },
          
                    { display: '已使用月', name: 'add_depre_month', align: 'left'
				 		 
				 	 },
				 	{ display: '状态', name: 'state', align: 'left',
				 		render : function(rowdata, rowindex,value) {
							 
				 			if (rowdata.add_depre_month/12 > rowdata.acc_depre_amount){
				 				return "逾龄";
				 			}
				 			if (rowdata.add_depre_month/12 <= rowdata.acc_depre_amount){
				 				
				 				return "役龄";
				 			}
						}
				 	 },
          
              ],
              dataAction: 'server',dataType: 'server',usePager:true,url:'queryAssAgeAnalyze.do?isCheck=false',
             width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad :true,
                     selectRowButtonOnly:true,
                     toolbar: { items: [
				                     	{ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' },
				                     	{ line:true },
						                { text: '打印（<u>P</u>）', id:'print', click: printDate,icon:'print' },
						                { line:true },
// 						                { text: '导出Excel（<u>E</u>）', id:'export', click: exportExcel,icon:'pager' },
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
	    	var date=$("#year_month").val();
	    	var heads={
	        		"isAuto":true,//系统默认，页眉显示页码
	        		"rows": [
	    	          {"cell":0,"value":"资产性质："},
	    	          {"cell":1,"value":liger.get("ass_nature").getText().split(" ")[1]},
	    	          {"cell":9,"value":"报表日期:"},
	  				  {"cell":10,"value":date} ,
	    	          
	        	]}; 
	    	//表尾
	    	var foots = {
	    			rows: [
	    				{"cell":9,"value":"制表人:"},
	    				{"cell":10,"value":"${sessionScope.user_name}"},
	    			]
	    		}; 
	 		var printPara={
	 				title: "资产入库汇总",//标题
	 				columns: JSON.stringify(grid.getPrintColumns()),//表头
	 				class_name: "com.chd.hrp.ass.service.tongJiReports.AssAgeAnalyzeService",
	 				method_name: "queryAssAgeAnalyePrint",
	 				bean_name: "assAgeAnalyzeService" ,
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
			lodopExportExcel("resultPrint","导出Excel","资产逾龄役龄分析.xls",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');

		var exportPara={
			usePager:false,
	        ass_nature : liger.get("ass_nature").getValue(),
  	  		year_month : $("#year_month").val(),
  	  		ass_dis_no : $("#ass_dis_no").val(),
  	  		bus_type_code : liger.get("bus_type_code").getValue(),
  	  		dept_id : liger.get("dept_id").getValue().split("@")[0],
  	  		store_id : liger.get("store_id").getValue().split("@")[0] 
         };
		ajaxJsonObjectByUrl("../tongJiReports/queryAssAgeAnalyze.do?ass_nature="+liger.get("ass_nature").getValue()+"&year_month="+$("#year_month").val(),exportPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
					 trHtml+="<td>"+item.ass_card_no+"</td>"; 
					 trHtml+="<td>"+item.ass_code+"</td>"; 
					 trHtml+="<td>"+item.ass_name+"</td>"; 
					 trHtml+="<td>"+item.ass_spec+"</td>"; 
					 trHtml+="<td>"+item.ass_mondl+"</td>"; 
					 trHtml+="<td>"+item.fac_name+"</td>"; 
					 trHtml+="<td>"+item.store_name+"</td>";
					 trHtml+="<td>"+item.dept_name+"</td>"; 
					 trHtml+="<td>"+item.acc_depre_amount+"</td>"; 
					 trHtml+="<td>"+item.add_depre_month+"</td>";
					 if (item.add_depre_month > item.acc_depre_amount){
						 
						 trHtml+="<td>逾龄</td>";
					 }else  if (item.add_depre_month <= item.acc_depre_amount){
						 
						 trHtml+="<td>役龄</td>";
					 }
				 trHtml+="</tr>"; 
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			lodopExportExcel("resultPrint","导出Excel","资产逾龄役龄分析.xls",true);
	    },true,manager);
		return;
	 }	
	 
	    function loadDict(){
	    	
			var param = {
	            	query_key:''
	        };
			
            //字典下拉框 
         autocomplete("#dept_id", "../queryDeptDict.do?isCheck=false", "id", "text", true, true,param,true);
		
		autocomplete("#store_id", "../queryHosStoreDict.do?isCheck=false","id", "text",true,true,param,true);
		
		autocomplete("#bus_type_code", "../queryAssBusType.do?isCheck=false","id", "text",true,true,param,true);
		
		//autocomplete("#ass_nature", "../queryAssNaturs.do?isCheck=false","id", "text",true,true); 
		
		 $("#ass_nature").ligerComboBox({
          	url: '../queryAssNaturs.do?isCheck=false',
          	valueField: 'id',
           	textField: 'text', 
           	selectBoxWidth: '160',
          	autocomplete: true,
          	width: '160',
          	onSelected :function(id,text){ 
          		query();
          		
          	}
 		  });
		 
        $("#year_month").ligerTextBox({width:160});
        
        $("#ass_dis_no").ligerTextBox({width:160});
         
        
         }  
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
     <table cellpadding="0" cellspacing="0" class="l-table-edit" >
		<tr>
		   	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">统计年月：</td>
            <td align="left" class="l-table-edit-td"><input name="year_month" type="text" id="year_month" ltype="text" validate="{required:true,maxlength:20}" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
        	<td align="left"></td>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产性质：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_nature" type="text" id="ass_nature"  /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">业务类型：</td>
            <td align="left" class="l-table-edit-td"><input name="bus_type_code" type="text" id="bus_type_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;">仓库：</td>
            <td align="left" class="l-table-edit-td"><input name="store_id" type="text" id="store_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
		</tr> 
       
    </table>

	<div id="maingrid"></div>
</body>
</html>
