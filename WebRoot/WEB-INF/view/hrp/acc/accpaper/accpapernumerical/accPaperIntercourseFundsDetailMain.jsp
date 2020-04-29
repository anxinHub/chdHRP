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
    		
    		var acc_year=$("#acc_year").val();
    		
    		var acc_month=$("#acc_month").val();

    		if(acc_year.substring(0,4)!= acc_month.substring(0,4)){
    			
    			$.ligerDialog.error("不支持跨年查询！");
    			
    			return;
    		}
    		
        //根据表字段进行添加查询条件
        grid.options.parms.push({name:'acc_year',value:acc_year}); 
        
        grid.options.parms.push({name:'acc_month',value:acc_month}); 
        
		grid.options.parms.push({name:'begin_no',value:$("#begin_no").val()}); 
        
        grid.options.parms.push({name:'end_no',value:$("#end_no").val()}); 
        
        grid.options.parms.push({name:'type_code',value:liger.get("type_code").getValue()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
    	
    	loadHead(null);
     }
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: $("#acc_year").val().substring(0,4),align: 'center',
                      columns:[{
                        	 display: '月', name: 'acc_month', align: 'left',width:'80'
                         },{
                        	 display: '日', name: 'acc_day', align: 'left',width:'80'
                         }]
                     },
                     { display: '领用/交回', align: 'center',
                    	 columns:[
                    	          {
                             		display: '开始号码', name: 'use_paper_num', align: 'left'
                         		  },{
                         			display: '结束号码', name: 'purchase_paper_num', align: 'left'
                             	 }]	
   	  				 },
                     { display: '核销', align: 'center',
                    	 columns:[{
                             display: '票据号码', name: 'check_paper_num', align: 'left'
                           },{
                               display: '会计凭证号码', name: 'check_vouch_no', align: 'left',
                        	      render:function(rowdata){
                         		        if(rowdata.vouch_id != null){
                         			   
                         			      return "<a href=javascript:openSuperVouch('"+rowdata.vouch_id+"')><div>"+rowdata.vouch_type_short+"-"+rowdata.vouch_no+"</div></a>";
                         		     
                         		      }{
                         			   return rowdata.check_vouch_no
                         		     }
                         	      }
                             }]	
   	  					  },
                     { display: '备注', name: 'money', align: 'left',
   	  					   render:function(rowdata){
   	  					    if(rowdata.vouch_id != null){
   	  					    	  if(rowdata.money == 0){
   	  					    		return "废";
   	  					    	  }else{
   	  					    		  
   	  					    		return formatNumber(rowdata.money,2,1);
   	  					    	  }
               		          }else{
               		        	 if(rowdata.check_money == 0){
    	  					    		return "废";
    	  					    	  }else{
    	  					    		return formatNumber(rowdata.check_money,2,1);
    	  					    	  }
               		           }
   	  					   }
                     },
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAccPaperIntercourseFundsDetail.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad:true,
                     selectRowButtonOnly:true//heightDiff: -10,
                     ,lodop:{
      	         		title:"往来款收据核销明细账",
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
    
    function loadDict(){
    	
    	$(':button').ligerButton({width:80});
    	
    	$("#acc_year").ligerTextBox({width:80});
    	
    	autodate("#acc_year","yyyyMMdd");
    	
    	$("#acc_month").ligerTextBox({width:80});
    	
    	autodate("#acc_month","yyyyMMdd");
    	
    	$("#begin_no").ligerTextBox({width:100});
    	
    	$("#end_no").ligerTextBox({width:100});
    	
    	autocomplete("#type_code","../../queryAccPaperType.do?isCheck=false","id","text",true,true);
    }   
    
	function openSuperVouch(vouch_id){
		
		parent.openFullDialog('hrp/acc/accvouch/superVouch/superVouchMainPage.do?vouch_id='+vouch_id,'会计凭证',0,0,true,true);
	
	}

	   //打印回调方法
    function lodopPrint(){
   
    	var head="<table class='head' width='100%'><tr><td>日期："+$("#acc_year").val()+"至"+$("#acc_month").val()+"</td>";
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
							{"cell":0,"value":"会计年度："+$("#acc_year").val()+"至"+$("#acc_month").val(),"colSpan":"5"},
							//{"cell":3,"value":"系统名称："+liger.get("mod_code").getText(),"from":"right","align":"right","colSpan":"4"}  
	      		  ]
	      	};
	   		
		var printPara={
			rowCount:1,
			title:'往来款收据核销明细账',
			columns: JSON.stringify(grid.getPrintColumns()),//表头
			class_name: "com.chd.hrp.acc.service.paper.AccPaperJournalinglService",
			method_name: "queryAccPaperIntercourseFundsDetailPrint",
			bean_name: "accPaperJournalinglService",
			heads: JSON.stringify(heads)//表头需要打印的查询条件,可以为空
			};
	
		//执行方法的查询条件
		$.each(grid.options.parms,function(i,obj){
			printPara[obj.name]=obj.value;
	});
		
	officeGridPrint(printPara); 
	
           	
        	/*  var selPara={usePager : false};
        	$.each(grid.options.parms,function(i,obj){
        		selPara[obj.name]=obj.value;
        	});
     
       		//console.log(grid)
       		var printPara={
       			headCount:2,
       			title:'往来款收据核销明细账',
       			type:3,
       			columns:grid.getColumns(1),
       			autoFile:false
       		};
       		
       		ajaxJsonObjectByUrl("queryAccPaperIntercourseFundsDetail.do?isCheck=false", selPara, function(responseData) {
       			printGridView(responseData,printPara);
        	});  */

    }
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
         <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">会计年度：</td>
            <td align="left" class="l-table-edit-td"><input name="acc_year" class="Wdate" id="acc_year" ltype="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMMdd'})" /></td>
            <td>至</td>
            <td align="left" class="l-table-edit-td"><input name="acc_month" class="Wdate" id="acc_month" ltype="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMMdd'})" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">票据号：</td>
            <td align="left" class="l-table-edit-td"><input name="begin_no" id="begin_no" ltype="text" /></td>
            <td>至</td>
            <td align="left" class="l-table-edit-td"><input name="end_no"  id="end_no" ltype="text" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">票据类型：</td>
            <td align="left" class="l-table-edit-td"><input name="type_code" type="text" id="type_code" ltype="text"  /></td>
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
