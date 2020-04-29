<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
	
    var grid;
    
    var gridManager = null;
    
    var userUpdateStr;
    
    $(function ()
    {
		loadDict();
    	
    	loadHead(null);	//加载数据
    	
    	loadHotkeys();
    	
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
        grid.options.parms.push({name:'begin_date',value:$("#begin_date").val()}); 
        grid.options.parms.push({name:'end_date',value:$("#end_date").val()}); 
        grid.options.parms.push({name:'begin_paper_num',value:$("#begin_paper_num").val()}); 
        grid.options.parms.push({name:'end_paper_num',value:$("#end_paper_num").val()}); 
    	grid.options.parms.push({name:'type_code',value:liger.get("type_code").getValue()}); 
    	//加载查询条件
    	grid.loadData(grid.where);
     }
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '票据类型编码', name: 'type_code', align: 'left',minWidth:90},
   	  				 { display: '票据类型名称', name: 'type_name', align: 'left',minWidth:130},
                     { display: '管理方式', name: 'paper_way_type', align: 'left',
   					     render : function(rowdata, rowindex,value) {
     						  if(rowdata.paper_way_type == 1){
     							  return "单张管理"
     						  }else if(rowdata.paper_way_type == 2){
     							  return "多张管理"
     						  }
     	  				  }	
   	  				 },
                     { display: '领用方式', name: 'paper_use_type', align: 'left',
   					     render : function(rowdata, rowindex,value) {
    						  if(rowdata.paper_use_type == 1){
    							  return "一次领用"
    						  }else if(rowdata.paper_use_type == 2){
    							  return "二次领用"
    						  }
    	  				  }
   	  				 },
                     { display: '票据号', name: 'paper_num', align: 'left',minWidth:130},
                     { display: '状态', name: 'state1', align: 'left',
   					     render : function(rowdata, rowindex,value) {
      						  if(rowdata.state1 == 1){
      							  return "购置"
      						  }else if(rowdata.state1 == 2){
      							  return "领用生效"
      						  }else if(rowdata.state1 == 3){
      							  return "作废"
      						  }
      	  				  }	},
                     { display: '领用人', name: 'sys_user_id1_name', align: 'left' },
                     { display: '领用日期', name: 'out_date1', align: 'left',width:100},
                     { display: '作废人', name: 'inva_user_id_name', align: 'left' },
                     { display: '作废日期', name: 'inva_date', align: 'left',width:100},
                     { display: '每本张数', name: 'paper_zlen', align: 'left'},
                     { display: '购置人', name: 'user_name', align: 'left'},
                     { display: '购置日期', name: 'opt_date', align: 'left',width:100},
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAccPaperDetailUseOne.do',
                     width: '100%', height: '97%', checkbox: true,rownumbers:true,delayLoad:true,
                     selectRowButtonOnly:true,minColumnWidth:200,columnWidth:'10%'//heightDiff: -10,
                     ,
                 	lodop:{
    	         		title:"票据领用",
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
   

		    function Receive(){
		    	
		    	 var data = gridManager.getCheckedRows();
				 if (data.length == 0){
		         	$.ligerDialog.error('请选择行');
		         }else{
		        	if(data.length == 1){
		        		if(data[0].state1 == '3'){
			        		$.ligerDialog.error('已作废的票据不能领用');
			        		return false;
			        	}
		        	}
		             var ParamVo =[];
		             $(data).each(function (){	
							ParamVo.push(
							//表的主键
							this.group_id   +"@"+ 
							this.hos_id   +"@"+ 
							this.copy_code +"@"+
							this.pid   +"@"+
							this.paper_num +"@"+
							"2" +"@"+
							this.state1
							)
		             });
		             
		            	 $.ligerDialog.open({ 
		         			title: '设置领用人', 
		         			width: 435, 
		         			height: 260, 
		         			url: 'accAccPaperDetailSetUser.do?isCheck=false&ParamVo='+ParamVo.toString(), 
		         			modal: true, showToggle: false, showMax: false, showMin: false, isResize: true,
		         			buttons: [
		         						{ text: '确定',onclick: function (item, dialog) { dialog.frame.saveUser(); }, cls:'l-dialog-btn-highlight' },
		         						{ text: '取消', onclick: closeDialog }
		         					]
		         		}); 
		            	 
		             /* $.ligerDialog.confirm('是否确认领用?', function (yes){
		             	if(yes){
		                 	ajaxJsonObjectByUrl("updateAccPaperDetailUseOneReceive.do",{ParamVo : ParamVo.toString()},function (responseData){
		                 		if(responseData.state=="true"){
		                 			query();
		                 		}
		                 	});
		             	}
		             }); */ 
		         }
		    	
		    }
		    
		    function closeDialog(item, dialog){ 
		        dialog.close();
		    }
		    
		    function CancelReceive(){
		    	 var data = gridManager.getCheckedRows();
				 if (data.length == 0){
		         	$.ligerDialog.error('请选择行');
		         }else{
		             var ParamVo =[];
		             $(data).each(function (){					
							ParamVo.push(
							//表的主键
							this.group_id   +"@"+ 
							this.hos_id   +"@"+ 
							this.copy_code +"@"+
							this.pid   +"@"+
							this.paper_num +"@"+
							"1" +"@"+
							this.state1
							)
		             });
		             $.ligerDialog.confirm('是否确认取消领用?', function (yes){
		             	if(yes){
		                 	ajaxJsonObjectByUrl("updateAccPaperDetailUseOneCancelReceive.do",{ParamVo : ParamVo.toString()},function (responseData){
		                 		if(responseData.state=="true"){
		                 			query();
		                 		}
		                 	});
		             	}
		             }); 
		         }
		    }
		    
		   function Nullify(){
			   var data = gridManager.getCheckedRows();
				 if (data.length == 0){
		         	$.ligerDialog.error('请选择行');
		         }else{
		             var ParamVo =[];
		             $(data).each(function (){					
							ParamVo.push(
							//表的主键
							this.group_id   +"@"+ 
							this.hos_id   +"@"+ 
							this.copy_code +"@"+
							this.pid   +"@"+
							this.paper_num +"@"+
							"3" +"@"+
							this.state1
							)
		             });
		             $.ligerDialog.confirm('是否确认作废?', function (yes){
		             	if(yes){
		                 	ajaxJsonObjectByUrl("updateAccPaperDetailUseOneNullify.do",{ParamVo : ParamVo.toString()},function (responseData){
		                 		if(responseData.state=="true"){
		                 			query();
		                 		}
		                 	});
		             	}
		             }); 
		         }
		   }
		 
		   function CancelNullify(){
			   var data = gridManager.getCheckedRows();
				 if (data.length == 0){
		         	$.ligerDialog.error('请选择行');
		         }else{
		             var ParamVo =[];
		             $(data).each(function (){					
							ParamVo.push(
							//表的主键
							this.group_id   +"@"+ 
							this.hos_id   +"@"+ 
							this.copy_code +"@"+
							this.pid   +"@"+
							this.paper_num+"@"+
							"1" +"@"+
							this.state1
							)
		             });
		             $.ligerDialog.confirm('是否确认取消作废?', function (yes){
		             	if(yes){
		                 	ajaxJsonObjectByUrl("updateAccPaperDetailUseOneCancelNullify.do",{ParamVo : ParamVo.toString()},function (responseData){
		                 		if(responseData.state=="true"){
		                 			query();
		                 		}
		                 	});
		             	}
		             }); 
		         }
		 	} 
		   //打印回调方法
		    function lodopPrint(){
		   
		    	var head="<table class='head' width='100%'><tr><td>日期："+$("#begin_date").val()+"至"+$("#end_date").val()+"</td>";
		 		grid.options.lodop.head=head;
		    }
    //键盘事件
	 function loadHotkeys() {

		hotkeys('Q', query);
		
		hotkeys('A', Receive);
		
		hotkeys('S', CancelReceive);
		
		hotkeys('D', Nullify);
		
		hotkeys('F', CancelNullify);
				

	}	   
								  
		   
    function loadDict(){
            //字典下拉框
            
         autocomplete("#type_code","../../queryAccPaperType.do?isCheck=false","id","text",true,true);
            
    	 $(':button').ligerButton({width:100});
    	 
    	 autodate("#begin_date","yyyy-MM-dd");
    	 
    	 var date = $("#begin_date").val();
    	 $("#begin_date").val(date.substr(0,date.length-2) + "01");

       	autodate("#end_date","yyyy-MM-dd"); 	
       	
    	 
    	 $("#begin_paper_num").ligerTextBox({width:120});
    	 
    	 $("#end_paper_num").ligerTextBox({width:120});
    	 
         $("#begin_date").ligerTextBox({width:120});
    	 
    	 $("#end_date").ligerTextBox({width:120});
    		
         }  
    
    function printDate(){
		 if(grid.getData().length==0){
		
			$.ligerDialog.error("请先查询数据！");
			
			return;
		}
	var heads={
	      		  //"isAuto": true/false 默认true，页眉右上角默认显示页码
	      		  "rows": [
							{"cell":0,"value":"购置日期："+$("#begin_date").val()+"至"+$("#end_date").val(),"colSpan":"5"},
							//{"cell":3,"value":"系统名称："+liger.get("mod_code").getText(),"from":"right","align":"right","colSpan":"4"}  
	      		  ]
	      	};
	   		
		var printPara={
			rowCount:1,
			title:'票据领用',
			columns: JSON.stringify(grid.getPrintColumns()),//表头
			class_name: "com.chd.hrp.acc.service.paper.AccPaperDetailService",
			method_name: "queryAccPaperDetailUseOnePrint",
			bean_name: "accPaperDetailService",
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
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">购置日期：</td>
            <td align="left" class="l-table-edit-td"><input name="begin_date" class="Wdate" id="begin_date" ltype="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"  /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td">至</td>
            <td align="left" class="l-table-edit-td"><input name="end_date" class="Wdate" id="end_date" ltype="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"  /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">票据类型：</td>
            <td align="left" class="l-table-edit-td"><input name="type_code" type="text" id="type_code" ltype="text"  /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">票据号：</td>
            <td align="left" class="l-table-edit-td"><input name="begin_paper_num" type="text" id="begin_paper_num" ltype="text"  /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td">至</td>
            <td align="left" class="l-table-edit-td"><input name="end_paper_num" type="text" id="end_paper_num" ltype="text"  /></td>
            <td align="left"></td>
        </tr> 
    </table>
    <div style="border:1px">
    <input  type="button" value=" 查询(Q)" onclick="query();"/>
    <input  type="button" value=" 领用(A)" onclick="Receive();"/>
    <input  type="button" value=" 取消领用(S)" onclick="CancelReceive();"/>
    <input  type="button" value=" 作废(D)" onclick="Nullify();"/>
    <input  type="button" value=" 取消作废(F)" onclick="CancelNullify();"/> 
    <input  type="button" value=" 打 印" onclick="printDate();"/>     
	</div>
	<div id="maingrid"></div>

</body>
</html>
