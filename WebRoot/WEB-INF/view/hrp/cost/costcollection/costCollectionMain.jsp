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
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
         
    	grid.options.parms.push({name:'year_month',value:$("#year_month").val()}); 
    	grid.options.parms.push({name:'e_year_month',value:$("#e_year_month").val()});  
        
        grid.options.parms.push({
			name : 'dept_code',
			value : liger.get("dept_code").getValue().split(".")[2]
		});
        grid.options.parms.push({
			name : 'cost_item_code',
			value : liger.get("cost_item_code").getValue().split(".")[2]
		});
		
		grid.options.parms.push({name:'source_id',value:liger.get("source_id").getValue()}); 
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '统计年月', name: 'year_month', align: 'left',render : function(rowdata, rowindex,value) {
                    	 
                    		return rowdata.acc_year+rowdata.acc_month;
                    		
            			}
					 },
                     { display: '科室编码', name: 'dept_code', align: 'left'
					 },
                     { display: '科室名称', name: 'dept_name', align: 'left',
						 render:function(rowdata,rowindex,value){
							 return formatSpace(rowdata.dept_name, rowdata.dept_level-1, null);
						 }
					 },
                     { display: '成本项目编码', name: 'cost_item_code', align: 'left'
					 },
                     { display: '成本项目名称', name: 'cost_item_name', align: 'left',
						 render:function(rowdata,rowindex,value){
							 return formatSpace(rowdata.cost_item_name, rowdata.item_grade-1, null);
						 }
					 },
					 { display: '资金来源编码', name: 'source_code', align: 'left'
					 },
                     { display: '资金来源名称', name: 'source_name', align: 'left'
					 }, 
                     { display: '金额', name: 'dir_amount', align: 'right',
							render : function(rowdata, rowindex,
									value) {
								 return formatNumber(rowdata.dir_amount,2,1);
							}
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryCostCollection.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
                    	{ text: '归集', id:'CostCollection', click: addCostCollection,icon:'back' },
                     	{ line:true },
//     	                { text: '导出Excel', id:'export', click: exportExcel,icon:'pager' },
// 		                { line:true },
		                { text: '打印', id:'print', click: print,icon:'print' },
		                { line:true },
    				]},
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

   
    function loadDict(){
    	
    	$("#year_month").ligerTextBox({ width:160 });
	     $("#e_year_month").ligerTextBox({ width:160});
	 		 
			 autodate("#year_month","yyyyMM");
			 autodate("#e_year_month","yyyyMM");
            //字典下拉框
    	autocomplete("#dept_code","../queryDeptDictCode.do?isCheck=false","id","text",true,true);
    	autocomplete("#cost_item_code","../queryItemDictNo.do?isCheck=false","id","text",true,true); 
    	autocomplete("#source_id","../querySourceArrt.do?isCheck=false","id","text",true,true); 
         }  
    
    //归集 
    function addCostCollection(){
    	
    	if($("#year_month").val()==''){
    		
    		$.ligerDialog.error("统计年月不能为空");
    		
    		return false
    	}
    	
    	 var formPara={
    	            
    	          year_month : $("#year_month").val()

    	         };
        $.ligerDialog.confirm('是否归集?', function (yes){
        	           if(yes){
        	               ajaxJsonObjectByUrl("addCostCollection.do",formPara,function(responseData){      	               
        	                   if(responseData.state=="true"){
            	                   query();
        	                   }
        	               });
        	               }
            	});
    }
    
	 function print(){
	    	
	    	if(grid.getData().length==0){
	    		
				$.ligerDialog.error("请先查询数据！");
				
				return;
			}
	    	
	    	var heads={
	 	    		//"isAuto":true,//系统默认，页眉显示页码
	 	    		"rows": [
	 		          {"cell":0,"value":"统计日期："+$("#b_year_month").val()+"至"+$("#e_year_month").val(),"colSpan":"5"}
	 	    	]};
	 	       var printPara={
	 	      		title: "成本归集",//标题
	 	      		columns: JSON.stringify(grid.getPrintColumns()),//表头
	 	      		class_name: "com.chd.hrp.cost.service.CostCollectionService",
	 	   			method_name: "queryCostCollectionPrint",
	 	   			bean_name: "costCollectionService",
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
           <td align="right" class="l-table-edit-td"  style="padding-left:20px;">统计年月：</td>
           <td align="left" class="l-table-edit-td"><input name="year_month" type="text" id="year_month" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
           <td align="right" class="l-table-edit-td"  style="padding-left:20px;">至&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
           <td align="left" class="l-table-edit-td"><input name="e_year_month" type="text" id="e_year_month" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
           <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室编码：</td>
           <td align="left" class="l-table-edit-td"><input name="dept_code" type="text" id="dept_code" /></td>
        </tr> 
        <tr>
          <td align="right" class="l-table-edit-td"  style="padding-left:20px;">成本项目：</td>
          <td align="left" class="l-table-edit-td"><input name="cost_item_code" type="text" id="cost_item_code" /></td>
          <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资金来源：</td>
          <td align="left" class="l-table-edit-td"><input name="source_id" type="text" id="source_id" /></td>
        </tr>
    </table>
	<div id="maingrid"></div>
</body>
</html>
