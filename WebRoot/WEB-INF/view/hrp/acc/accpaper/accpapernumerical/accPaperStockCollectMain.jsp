<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
	<script>
	
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
        grid.options.parms.push({name:'end_date',value:$("#end_date").val()}); 
    	grid.options.parms.push({name:'type_code',value:liger.get("type_code").getValue()}); 
    	//加载查询条件
    	grid.loadData(grid.where);
     }
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '票据类型编码', name: 'type_code', align: 'left'},
   	  				 { display: '票据类型名称', name: 'type_name', align: 'left',width:160},
                     { display: '购入', name: 'count_temp_pid', align: 'right',
                     	render : function(rowdata, rowindex,value) {
                     			 return "<a href=javascript:openQueryDetail('"+rowdata.type_code+"|"+$("#end_date").val()+"|"+1+"')><div>"+rowdata.count_temp_pid+"</div></a>";	
	  				   }
   	  					 },
                     { display: '领用', name: 'use_temp_pid', align: 'right',
   	                     	render : function(rowdata, rowindex,value) {
   	                     			 return "<a href=javascript:openQueryDetail('"+rowdata.type_code+"|"+$("#end_date").val()+"|"+2+"')><div>"+rowdata.use_temp_pid+"</div></a>";	
   	                     		
   		  				   }
   	  				 },
                     { display: '核销', name: 'check_temp_pid', align: 'right',
                     	render : function(rowdata, rowindex,value) {
                     			 return "<a href=javascript:openQueryDetail('"+rowdata.type_code+"|"+$("#end_date").val()+"|"+4+"')><div>"+rowdata.check_temp_pid+"</div></a>";	
                     		
	  				   }
   	  				 },
                     { display: '作废', name: 'inva_temp_pid', align: 'right',
                     	render : function(rowdata, rowindex,value) {
                     			 return "<a href=javascript:openQueryDetail('"+rowdata.type_code+"|"+$("#end_date").val()+"|"+3+"')><div>"+rowdata.inva_temp_pid+"</div></a>";	
                     		
	  				   }
   	  				 },
                     { display: '领用结存', name: 'use_balance', align: 'right'},
                     { display: '购入结存', name: 'count_balance', align: 'right'},
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAccPaperStockCollect.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad:true,
                     selectRowButtonOnly:true//heightDiff: -10,
                     ,lodop:{
     	         		title:"票据库存汇总表",
     	      			fn:{
     	          			debit:function(value){//借方
     	          				if(value == 0){return "";}
     	                 			else{return formatNumber(value, 2, 1);}
     	          			},
     	          			credit:function(value){//贷方
     	          				if(value == 0){return "";}
     	                			else{return formatNumber(value, 2, 1);}
     	         				},
     	         				end_os:function(value){//余额
     	      	   				 if(value==0){return "Q";}
     	      					 else{return formatNumber(value, 2, 1);}
     	        				}
     	          		}
     	      		}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    //打印回调方法
    function lodopPrint(){
   
    	var head="<table class='head' width='100%'><tr><td>截止日期："+$("#end_date").val()+"</td>";
 		grid.options.lodop.head=head;
    }
    function openQueryDetail(obj){
    	var vo = obj.split("|");
    	var mapVo =
			"type_code="+ vo[0] +
		 	"&end_date=" + vo[1]+
		 	"&state=" + vo[2]
    	 $.ligerDialog.open({ url : 'accPaperStockCollectDetailPage.do?'+ mapVo ,data:{}, height: 538,width: 1154, title:'',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true});
    	
       }
     function print(){
    	 
    	 if(grid.getData().length==0){
 			$.ligerDialog.error("请先查询数据！");
 			return;
 		}
    	 var heads={
	      		  //"isAuto": true/false 默认true，页眉右上角默认显示页码
	      		  "rows": [
							{"cell":0,"value":"截止日期："+$("#end_date").val(),"colSpan":"5"},
							//{"cell":3,"value":"系统名称："+liger.get("mod_code").getText(),"from":"right","align":"right","colSpan":"4"}  
	      		  ]
	      	};
	   		
		var printPara={
			rowCount:1,
			title:'票据库存汇总表',
			columns: JSON.stringify(grid.getPrintColumns()),//表头
			class_name: "com.chd.hrp.acc.service.paper.AccPaperJournalinglService",
			method_name: "queryAccPaperStockCollectPrint",
			bean_name: "accPaperJournalinglService",
			heads: JSON.stringify(heads)//表头需要打印的查询条件,可以为空
			};
	
		//执行方法的查询条件
		$.each(grid.options.parms,function(i,obj){
			printPara[obj.name]=obj.value;
	});
		
	officeGridPrint(printPara); 
	
            	
         	/* var selPara={};
         	$.each(grid.options.parms,function(i,obj){
         		selPara[obj.name]=obj.value;
         	});
      
        		//console.log(grid)
        		var printPara={
        			headCount:2,
        			title:'票据库存汇总表',
        			type:3,
        			columns:grid.getColumns(1),
        			autoFile:true
        		};
        		
        		ajaxJsonObjectByUrl("queryAccPaperStockCollect.do?isCheck=false", selPara, function(responseData) {
        			printGridView(responseData,printPara);
         	}); */

     }
    function loadDict(){
            //字典下拉框
            
         autocomplete("#type_code","../../queryAccPaperType.do?isCheck=false","id","text",true,true);
    	 
        $(':button').ligerButton({width:80});
            
    	$("#end_date").ligerTextBox({width:160});
    	
    	autodate("#end_date","yyyy-MM-dd");
    	
         }   
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
         <tr>
             <td align="right" class="l-table-edit-td"  style="padding-left:20px;">票据类型：</td>
             <td align="left" class="l-table-edit-td"><input name="type_code" type="text" id="type_code" ltype="text" validate="{required:true}" /></td>
             <td align="left"></td>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">截止日期：</td>
            <td align="left" class="l-table-edit-td"><input name="end_date" class="Wdate" id="end_date" ltype="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"  /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"></td>
            <td align="left" class="l-table-edit-td">  <input  type="button" value=" 查询" onclick="query();"/></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"></td>
            <td align="left" class="l-table-edit-td">  <input  type="button" value=" 打印" onclick="print();"/></td>
            <td align="left"></td>
        </tr> 
    </table>
	<div id="maingrid"></div>

</body>
</html>
