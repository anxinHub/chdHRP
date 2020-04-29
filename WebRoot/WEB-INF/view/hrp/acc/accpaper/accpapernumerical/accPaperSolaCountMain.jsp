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
        grid.options.parms.push({name:'type_code',value:liger.get("type_code").getValue()}); 
        grid.options.parms.push({name:'begin_year_month',value:$("#begin_year_month").val()}); 
        grid.options.parms.push({name:'end_year_month',value:$("#end_year_month").val()}); 
    	//加载查询条件
    	grid.loadData(grid.where);
     }
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '票据类型编码', name: 'type_code', align: 'left'},
   	  				 { display: '票据类型名称', name: 'type_name', align: 'left',width:160},
                     { display: '票据号码前缀', name: 'paper_prefix', align: 'left'},
                     { display: '票据号码', name: 'paper_num', align: 'left'},
                     { display: '状态', name: 'state1', align: 'left',
   					     render : function(rowdata, rowindex,value) {
   					    	if(rowdata.state1=="1"){
                   			 return "购置中";
                   		 }else if(rowdata.state1=="2"){
                   			 return "领用生效";
                   		 }else if(rowdata.state1=="3"){
                   			 return "作废";
                   		 }else{
                   			 return "已核销";
                   		 }
      	  				  }
                     },
                     { display: '购置人', name: 'user_name', align: 'left'},
                     { display: '购置日期', name: 'opt_date', align: 'left'},
                     { display: '领用人', name: 'out_name', align: 'left'},
                     { display: '领用日期', name: 'out_date1', align: 'left'},
                     { display: '核销人', name: 'check_name', align: 'left'},
                     { display: '核销日期', name: 'check_date', align: 'left'},
                     { display: '凭证号', name: 'vouch_no', align: 'left',
                   	      render:function(rowdata){
               		        if(rowdata.vouch_id != null){
               			   
               			      return "<a href=javascript:openSuperVouch('"+rowdata.vouch_id+"')><div>"+rowdata.vouch_type_short+"-"+rowdata.vouch_no+"</div></a>";
               		     
               		      }{
               			   return rowdata.check_vouch_no
               		     }
               	      }
                     },
                     { display: '备注', name: 'note', align: 'left'},
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAccPaperSolaCount.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad:true,
                     selectRowButtonOnly:true,minColumnWidth:200,columnWidth:'10%'//heightDiff: -10,
                         ,lodop:{
           	         		title:"单张票据统计表",
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
    
	function openSuperVouch(vouch_id){
		
		parent.openFullDialog('hrp/acc/accvouch/superVouch/superVouchMainPage.do?vouch_id='+vouch_id,'会计凭证',0,0,true,true);
	
	}
	   //打印回调方法
    function lodopPrint(){
   
    	var head="<table class='head' width='100%'><tr><td>日期："+$("#begin_year_month").val()+"至"+$("#end_year_month").val()+"</td>";
 		grid.options.lodop.head=head;
    }
    function print(){
    	
    	if(grid.getData().length==0){
 			$.ligerDialog.error("请先查询数据！");
 			return;
 		}
    	var heads={
	      		  //"isAuto": true/false 默认true，页眉右上角默认显示页码
	      		  "rows": [
							{"cell":0,"value":"日期："+$("#begin_year_month").val()+"至"+$("#end_year_month").val(),"colSpan":"5"},
							//{"cell":3,"value":"系统名称："+liger.get("mod_code").getText(),"from":"right","align":"right","colSpan":"4"}  
	      		  ]
	      	};
	   		
		var printPara={
			rowCount:1,
			title:'单张管理票据统计表',
			columns: JSON.stringify(grid.getPrintColumns()),//表头
			class_name: "com.chd.hrp.acc.service.paper.AccPaperJournalinglService",
			method_name: "queryAccPaperSolaCountPrint",
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
        			title:'单张管理票据统计表',
        			type:3,
        			columns:grid.getColumns(1),
        			autoFile:true
        		};
        		
        		ajaxJsonObjectByUrl("queryAccPaperSolaCount.do?isCheck=false", selPara, function(responseData) {
        			printGridView(responseData,printPara);
         	}); */
    	
    }
    function loadDict(){
            //字典下拉框
            
         autocomplete("#type_code","../../queryAccPaperType.do?isCheck=false","id","text",true,true);
    	 
        $(':button').ligerButton({width:80});
            
    	$("#begin_year_month").ligerTextBox({width:160});
    	
    	$("#end_year_month").ligerTextBox({width:160});
    	
    	autodate("#begin_year_month","yyyy-MM-dd");
    	
    	autodate("#end_year_month","yyyy-MM-dd");
    	
         }   
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit">
         <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">票据类型：</td>
            <td align="left" class="l-table-edit-td"><input name="type_code" type="text" id="type_code" ltype="text" validate="{required:true}" /></td>
            <td align="left"></td>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">日期：</td>
            <td align="left" class="l-table-edit-td"><input name="begin_year_month" class="Wdate" id="begin_year_month" ltype="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"  /></td>
            <td align="center">至</td>
            <td align="left" class="l-table-edit-td"><input name="end_year_month" class="Wdate" id="end_year_month" ltype="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"  /></td>
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
