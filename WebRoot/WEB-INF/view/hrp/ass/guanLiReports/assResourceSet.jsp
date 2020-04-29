<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<!--资产来源查询页面  -->
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
    	 $("#ven_id").ligerTextBox({width:160});
         $("#state").ligerComboBox({width:160});
         $("#dept_id").ligerComboBox({width:160});
         $("#ass_id").ligerComboBox({width:160});
         $("#year_month_beg").ligerTextBox({width:80});
         $("#year_month_end").ligerTextBox({width:80});
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
          grid.options.parms.push({name:'ass_type_code',value:liger.get("ass_type_id").getText().split(" ")[0]});
          grid.options.parms.push({name:'ass_id',value:liger.get("ass_id").getValue().split("@")[0]}); 
          grid.options.parms.push({name:'ass_no',value:liger.get("ass_id").getValue().split("@")[1]}); 
          grid.options.parms.push({name:'ven_id',value:liger.get("ven_id").getValue().split("@")[0]}); 
          grid.options.parms.push({name:'ven_no',value:liger.get("ven_id").getValue().split("@")[1]}); 
          grid.options.parms.push({name:'dept_id',value:liger.get("dept_id").getValue().split("@")[0]}); 
          grid.options.parms.push({name:'dept_no',value:liger.get("dept_id").getValue().split("@")[1]}); 
          grid.options.parms.push({name:'use_state',value:liger.get("use_state").getValue()}); 
    	  grid.options.parms.push({name:'ass_nature',value:liger.get("ass_nature").getValue()}); 
    	  grid.options.parms.push({name:'source_id',value:liger.get("source_code").getValue()}); 
    	  grid.options.parms.push({name:'ass_card_no',value:$("#ass_card_no").val()});  
    	  grid.options.parms.push({name:'year_month_beg',value:$("#year_month_beg").val()});  
    	  grid.options.parms.push({name:'year_month_end',value:$("#year_month_end").val()});  
    	//加载查询条件
    	grid.loadData(grid.where);
     }

  
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                   
	                     { display: '资产卡片号', name: 'ass_card_no', align: 'left',
				 			 
					 	 },
 						 { display: '状态', name: 'state_name', align: 'center',
				 			 
					     },
						 { display: '入库时间', name: 'in_date', align: 'center',
				 			 
					     },
					 	 { display: '资产名称', name: 'ass_name', align: 'left',
				 			 
					 	 },
					 	 { display: '资产分类', name: 'ass_type_name', align: 'left',
				 			 
					 	 },
	                     { display: '资金来源', name: 'source_name', align: 'left',
				 			 
					     },
						 { display: '科室', name: 'dept_name', align: 'left',
				 			 
					     },
						 { display: '供应商', name: 'ven_name', align: 'left',
				 			 
					     },
	                     { display: '卡片原值', name: 'price', align: 'right',
					    	 render : function(rowdata, rowindex,
										value) {
									 return formatNumber(rowdata.price,'${ass_05006 }',1);
								},formatter:'###,##0.00'
				 			 
					 	 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'../guanLiReports/queryAssResourceSet.do?isCheck=false',
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
	    	var date=$("#year_month_beg").val()+"至"+$("#year_month_end").val();
	    	var heads={
	        		"isAuto":true,//系统默认，页眉显示页码
	        		"rows": [
	    	          {"cell":0,"value":"报表日期:"},
	  				  {"cell":1,"value":date} ,
	  				  {"cell":2,"value":"资产性质:"},
	  				  {"cell":3,"value":liger.get("ass_nature").getText().split(" ")[1]} ,
	        	]}; 
	    	//表尾
	    	var foots = {
	    			rows: [
	    				{"cell":7,"value":"制表人:"},
	    				{"cell":8,"value":"${sessionScope.user_name}"},
	    			]
	    		}; 
	 		var printPara={
	 				title: "资金来源月报表",//标题
	 				columns: JSON.stringify(grid.getPrintColumns()),//表头
	 				class_name: "com.chd.hrp.ass.service.guanLiReports.AssResourceSetService",
	 				method_name: "queryAssResourceSetPrint",
	 				bean_name: "assResourceSetService" ,
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
			lodopExportExcel("resultPrint","导出Excel","资产来源报表.xls",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');

		var exportPara={
			usePager:false,
           
         };
		ajaxJsonObjectByUrl("../guanLiReports/queryAssResourceSet.do?isCheck=false&ass_nature="+liger.get("ass_nature").getValue(),exportPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
					 trHtml+="<td>"+item.ass_card_no+"</td>"; 
					 trHtml+="<td>"+item.source_code+"</td>"; 
					 trHtml+="<td>"+item.source_name+"</td>"; 
					 trHtml+="<td>"+item.price+"</td>"; 
					 trHtml+="<td>"+item.depre_money+"</td>"; 
					 trHtml+="<td>"+item.cur_money+"</td>"; 
					 trHtml+="<td>"+item.fore_money+"</td>"; 
				 trHtml+="</tr>"; 
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			lodopExportExcel("resultPrint","导出Excel","资产来源报表.xls",true);
	    },true,manager);
		return;
	 }	
	 
	    function loadDict(){
	    	
			var param = {
	            	query_key:''
	        };
			
            //字典下拉框 
            
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
     
			$("#ass_nature").ligerTextBox({
	    		
	    		width : 160
	    		
	    	});
	 
			$("#ass_card_no").ligerTextBox({
				
				width : 160
				
			});
			autodate("#year_month_beg","YYYY-mm-dd","month_first");

			autodate("#year_month_end","YYYY-mm-dd","month_last");
			
	    	$("#year_month_beg,#year_month_end").ligerTextBox({width : 80});
			autocomplete("#source_code","../querySourceDict.do?isCheck=false","id","text",true,true,param,true);
			autocomplete("#ass_type_id","../queryAssTypeDict.do?isCheck=false","id","text",true,true,null,null,null,"180");
			autocomplete("#dept_id","../queryDeptDict.do?isCheck=false","id","text",true,true,null,null,null,"180");
			autocomplete("#use_state","../queryAssCardUseStateDict.do?isCheck=false","id","text",true,true,null,null,null,"180");
			autocomplete("#ven_id", "../queryHosSupDict.do?isCheck=false","id", "text",true,true,param,true,null,"350");
			autocomplete("#ass_id", "../queryAssNoDict.do?isCheck=false", "id","text", true, false, null,null,null,"230");


        }  
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
     <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
	     <tr>
	     		<td align="right" class="l-table-edit-td">入库年月：</td>
	     		<td >
	     			<table>
	     				<tr>
	     					<td>
	     						<input name="year_month_beg" type="text" id="year_month_beg" ltype="text" validate="{required:true,maxlength:20}" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
	     					</td>
	     					<td>至:</td>
	     					<td><input name="year_month_end" type="text" id="year_month_end" ltype="text" validate="{required:true,maxlength:20}" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
	     				</tr>
	     			</table>
	     		</td>
	            
	        	<td align="right" class="l-table-edit-td"  style="width:120px;">资产性质：</td>
				<td align="left" class="l-table-edit-td">
					<input name="ass_nature" type="text" id="ass_nature" />
				</td>
				<td align="right" class="l-table-edit-td">资产名称：</td>
				<td align="left" class="l-table-edit-td">
					<input name="ass_id" type="text" id="ass_id" />
				</td>
	     </tr>
        <tr >
				<td align="right" class="l-table-edit-td">资金来源：</td>
				<td align="left" class="l-table-edit-td">
					<input name="source_code" type="text" id="source_code" /></td>
				<td align="right" class="l-table-edit-td">资产卡片号：</td>
				<td align="left" class="l-table-edit-td">
					<input name="ass_card_no" type="text" id="ass_card_no" />
				</td>

				<td align="right" class="l-table-edit-td" >资产分类：</td>
				<td align="left" class="l-table-edit-td">
					<input name="ass_type_id" type="text" id="ass_type_id" />
				</td>
        </tr> 
        <tr >
				<td align="right" class="l-table-edit-td" >供应商：</td>
				<td align="left" class="l-table-edit-td"><input
					name="ven_id" type="text" id="ven_id" /></td>
				<td align="right" class="l-table-edit-td" >部门：</td>
				<td align="left" class="l-table-edit-td"><input
					name="dept_id" type="text" id="dept_id" /></td>
				<td align="right" class="l-table-edit-td" >状态：</td>
				<td align="left" class="l-table-edit-td"><input
					name="use_state" type="text" id="use_state" /></td>
        </tr> 
       
    </table>

	<div id="maingrid"></div>
</body>
</html>
