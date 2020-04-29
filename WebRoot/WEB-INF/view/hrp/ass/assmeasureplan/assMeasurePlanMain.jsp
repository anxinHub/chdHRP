<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
    	  
	    	  grid.options.parms.push({name:'plan_name',value:$("#plan_name").val()}); 
	    	  
	    	  grid.options.parms.push({name:'ass_table',value:$("#ass_table").val()}); 
	    	  
	    	  grid.options.parms.push({name:'ass_nature',value:liger.get("ass_nature").getValue()}); 
	    	  
	    	  grid.options.parms.push({name:'create_emp',value:liger.get("create_emp").getValue()}); 
	    	  
	    	  grid.options.parms.push({name:'create_date_begin',value:$("#create_date_begin").val()}); 
	    	  
	    	  grid.options.parms.push({name:'create_date_end',value:$("#create_date_end").val()}); 
	    	  
	    	  grid.options.parms.push({name:'audit_emp',value:liger.get("audit_emp").getValue()}); 
	    	  
	    	  grid.options.parms.push({name:'audit_date_begin',value:$("#audit_date_begin").val()}); 
	    	  
	    	  grid.options.parms.push({name:'audit_date_end',value:$("#audit_date_end").val()});
	    	  
	    	  grid.options.parms.push({name:'state',value:liger.get("state").getValue()}); 

    		  grid.loadData(grid.where);
    	
			  $("#resultPrint > table > tbody").empty();
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [  
                     { display: '计量计划年份', name: 'plan_year', align: 'left',frozen: true
				 		       }, 
                     { display: '计量计划编号', name: 'plan_no', align: 'left',frozen: true,
                    	 
                    	 render : function(rowdata, rowindex,value) {
                    		 
    							return "<a href=javascript:openUpdate('"+rowdata.group_id   + "|" + 
																		rowdata.hos_id   + "|" + 
																		rowdata.copy_code   + "|" + 
																		rowdata.plan_id +"')>"+
    									                                 rowdata.plan_no+"</a>";
    										}
					 		},
                     { display: '计量计划名称', name: 'plan_name', align: 'left'
					 		},

                     { display: '资产性质', name: 'ass_nature', align: 'left',
					 			
							render : function(rowdata, rowindex, value) {
					 				
									return rowdata.naturs_name;
									
									}
					 		},
                     { display: '制单人', name: 'create_emp', align: 'left',
					 			
							render : function(rowdata, rowindex, value) {
					 				
									return rowdata.create_emp_name;
									
									}
					 		},
                     { display: '制单日期', name: 'create_date', align: 'left'
					 		},
                     { display: '审核人', name: 'audit_emp', align: 'left',
					 			
							render : function(rowdata, rowindex, value) {
					 				
									return rowdata.audit_emp_name;
									
									}
					 		},
                     { display: '审核日期', name: 'audit_date', align: 'left'
					 		},
                     { display: '状态', name: 'state', align: 'left',
					 			
								render : function(rowdata, rowindex, value) {
					 				
									if (rowdata.state == 0) {
										
										return "新建";
										
									} else if (rowdata.state == 1){
										
										return "审核";
										
									}else if (rowdata.state == 2){
										
										return "终止";
										
									}
									
								
								}
					 		},
                     { display: '检查方式', name: 'check_way', align: 'left'
					 		},
                     { display: '备注', name: 'note', align: 'left'
					 		}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAssMeasurePlan.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,
                     toolbar: { items: [
                     	{ text: '查询（<u>E</u>）', id:'search', click: query,icon:'search' },
                     	{ line:true },
				    					{ text: '添加（<u>A</u>）', id:'add', click: add_open, icon:'add' },
				    	                { line:true },
				    	                { text: '删除（<u>D</u>）', id:'delete', click: remove,icon:'delete' },
										{ line:true }, 
										{ text: '审核（<u>R</u>）', id:'audit', click: auditMeasurePlan, icon:'right' },
						    	        { line:true },
						    	        { text: '销审（<u>B</u>）', id:'back', click: backMeasurePlan,icon:'back' },
										{ line:true },
										{ text: '终止计划（<u>S</u>）', id:'stop', click: stopMeasurePlan,icon:'lock' },
										{ line:true },
										{ text : '打印模板设置',id : 'printSet',click : printSet,icon : 'settings'},
										{ line:true }, 
									    { text : '批量打印',id : 'print',click : printDate,icon : 'print'}
						                
				    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code   + "|" + 
								rowdata.plan_id 
							);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function add_open(){


		parent.$.ligerDialog.open({
			title : '检查计量计划',
			height : $(window).height(),
			width : $(window).width(),
			url : 'hrp/ass/assmeasureplan/assMeasurePlanAddPage.do?isCheck=false',
			modal : true,
			showToggle : false,
			showMax : true,
			showMin : false,
			isResize : true,
			parentframename : window.name, //用于parent弹出层调用本页面的方法或变量
		});}
    /* 	var index = layer.open({
					type : 2,
					title : '051204 检查计量计划',
					shadeClose : false,
					shade : false,
					maxmin : true, //开启最大化最小化按钮
					 area : [ '893px', '500px' ], 
					content : 'assMeasurePlanAddPage.do?isCheck=false'
				});
				layer.full(index);
    	} */
    	
    function remove(){
    	
    	var data = gridManager.getCheckedRows();
                    if (data.length == 0){
                    	$.ligerDialog.error('请选择行');
                    }else{
                        var ParamVo =[];
                        $(data).each(function (){					
							ParamVo.push(
							this.group_id   +"@"+ 
							this.hos_id   +"@"+ 
							this.copy_code   +"@"+ 
							this.plan_id  +"@"+ 
							this.state 
							
							) });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteAssMeasurePlan.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
    	}
 	
    function auditMeasurePlan(){
   	   
   	   var data = gridManager.getCheckedRows();
   	   
          if (data.length == 0){
       	   
          	$.ligerDialog.error('请选择行');
          	
          }else{
       	   
              var ParamVo =[];
              
              $(data).each(function (){		
           	   
   				ParamVo.push(
   						
   					this.group_id   +"@"+
   				
   					this.hos_id   +"@"+ 
   				
   					this.copy_code   +"@"+ 
   				
   					this.plan_id     +"@"+ 
   				
   					this.state   
   				
   				); });
              $.ligerDialog.confirm('确定审核?', function (yes){
              	if(yes){
                  	ajaxJsonObjectByUrl("auditMeasurePlan.do",{ParamVo : ParamVo.toString()},function (responseData){
                  		if(responseData.state=="true"){
                  			query();
                  		}
                  	});
              	}
              });
       	   
          }
   	   
      }
      
      function backMeasurePlan (){
   	   
   	   var data = gridManager.getCheckedRows();
   	   
          if (data.length == 0){
       	   
          	$.ligerDialog.error('请选择行');
          	
          }else{
       	   
              var ParamVo =[];
              
              $(data).each(function (){		
           	   
   				ParamVo.push(
   						
   					this.group_id   +"@"+
   				
   					this.hos_id   +"@"+ 
   				
   					this.copy_code   +"@"+ 
   				
   					this.plan_id     +"@"+ 
   				
   					this.state   
   				
   				); });
              $.ligerDialog.confirm('确定销审?', function (yes){
              	if(yes){
                  	ajaxJsonObjectByUrl("backMeasurePlan.do",{ParamVo : ParamVo.toString()},function (responseData){
                  		if(responseData.state=="true"){
                  			query();
                  		}
                  	});
              	}
              });
       	   
          }
   	   
      }
      
      
      function stopMeasurePlan (){
      	
    	   
    	   var data = gridManager.getCheckedRows();
    	   
           if (data.length == 0){
        	   
           	$.ligerDialog.error('请选择行');
           	
           }else{
        	   
               var ParamVo =[];
               
               $(data).each(function (){		
            	   
    				ParamVo.push(
    						
    					this.group_id   +"@"+
    				
    					this.hos_id   +"@"+ 
    				
    					this.copy_code   +"@"+ 
    				
    					this.plan_id     
    				
    				); });
               $.ligerDialog.confirm('确定终止计划?', function (yes){
               	if(yes){
                   	ajaxJsonObjectByUrl("stopMeasurePlan.do",{ParamVo : ParamVo.toString()},function (responseData){
                   		if(responseData.state=="true"){
                   			query();
                   		}
                   	});
               	}
               });
        	   
           }
      	
      }
    function openUpdate(obj){
    		
		var vo = obj.split("|");
		if("null"==vo[3]){
			return false;
			
		}
		var parm = 
			"group_id="+vo[0]   +"&"+ 
			"hos_id="+vo[1]   +"&"+ 
			" copy_code="+vo[2]   +"&"+ 
			"plan_id="+vo[3] ;
		
   /*  var index = layer.open({
					type : 2,
					title : '科室申请资产明细',
					shadeClose : false,
					shade : false,
					maxmin : true, //开启最大化最小化按钮
					area : [ '893px', '500px' ],
					content : 'assMeasurePlanUpdatePage.do?isCheck=false&' + parm
				}); */
		parent.$.ligerDialog.open({
			title : '检查计量计划修改',			
			height : $(window).height(),
			width : $(window).width(),
			url : 'hrp/ass/assmeasureplan/assMeasurePlanUpdatePage.do?isCheck=false&'+parm,
			modal : true,
			showToggle : false,
			showMax : true,
			showMin : false,
			isResize : true,
			parentframename : window.name, //用于parent弹出层调用本页面的方法或变量
		});
			/* 	layer.full(index);	 */
    
    }

    //键盘事件
	  function loadHotkeys() {

		hotkeys('E', query);

		hotkeys('A', add_open);
		
		hotkeys('D', remove);
		
		hotkeys('R', auditMeasurePlan);
		
		hotkeys('B', backMeasurePlan);
		
		hotkeys('S', stopMeasurePlan);
		
		hotkeys('E', exportExcel);

		hotkeys('P', printDate);
		 
	 }
  //打印数据
// 	 function printDate(){
// 		//有数据直接打印
// 		if($("#resultPrint > table > tbody").html()!=""){
// 			lodopPrinterTable("resultPrint","开始打印","检查计量计划",true);
// 			return;
// 		}
		
// 		//重新查询数据，避免分页导致打印数据不全
// 		var manager = $.ligerDialog.waitting('系统正在准备打印数据,请稍候...');

// 		var printPara={
				
// 			usePager:false,
	           
// 	           plan_name:$("#plan_name").val(),
	           
// 	           ass_nature: liger.get("ass_nature").getValue(),
	           
// 	           create_emp: liger.get("create_emp").getValue(),
	           
// 	           create_date_begin:$("#create_date_begin").val(),
	           
// 	           create_date_end:$("#create_date_end").val(),
	           
// 	           audit_emp: liger.get("audit_emp").getValue(),
	           
// 	           audit_date_begin:$("#audit_date_begin").val(),
	           
// 	           audit_date_end:$("#audit_date_end").val(),
	           
// 	           state: liger.get("state").getValue()
           
//          };
		
// 		ajaxJsonObjectByUrl("queryAssMeasurePlan.do",printPara,function (responseData){
			
// 			$.each(responseData.Rows,function(idx,item){ 
				
// 				 var trHtml="<tr>";
					 
// 					 trHtml+="<td>"+item.plan_no+"</td>"; 
					 
// 					 trHtml+="<td>"+item.plan_name+"</td>"; 
					 
// 					 trHtml+="<td>"+item.plan_year+"</td>"; 
					 
// 					 trHtml+="<td>"+item.naturs_name+"</td>"; 
					 
// 					 trHtml+="<td>"+item.create_emp_name+"</td>"; 
					 
// 					 trHtml+="<td>"+item.create_date+"</td>"; 
					 
// 					 trHtml+="<td>"+item.audit_emp_name+"</td>"; 
					 
// 					 trHtml+="<td>"+item.audit_date+"</td>"; 
					 
// 					 if (item.state == 0) {
						 
// 						 trHtml += "<td>新建</td>";
						 
// 					 }else if (item.state == 1) {
						 
// 						 trHtml += "<td>审核</td>";
						 
// 					 }else if (item.state == 2) {
						 
// 						 trHtml += "<td>终止</td>";
						 
// 					 }
					 
// 					 trHtml+="<td>"+item.check_way+"</td>"; 
					 
// 					 trHtml+="<td>"+item.note+"</td>"; 
					 
// 				 trHtml+="</tr>";
				
// 				$("#resultPrint > table > tbody").append(trHtml);
				
// 			});
			
// 			manager.close();
			
// 			//alert($("#resultPrint").html())
			
// 			lodopPrinterTable("resultPrint","开始打印","检查计量计划",true);
			
// 	    },true,manager);
		
// 		return;
// 	 }
	  /*  function printDate(){
	    	
	    	if(grid.getData().length==0){
	    		
				$.ligerDialog.error("请先查询数据！");
				
				return;
			}
	    	
	    	var selPara={};
	    	
	    	$.each(grid.options.parms,function(i,obj){
	    		
	    		selPara[obj.name]=obj.value;
	    		
	    	});
	   		
			var dates = getCurrentDate();
	    	
	    	var cur_date = dates.split(";")[2];
	    	//跨所有列:计算列数
	    	var colspan_num = grid.getColumns(1).length-1;
	   		
	    	var printPara={
	       			title:'计量计划',
	       			head:[
	    				{"cell":0,"value":"单位: ${hos_name}","colspan":colspan_num,"br":true}
	       			],
	       			foot:[
	    				{"cell":0,"value":"主管:","colspan":2,"br":false} ,
						{"cell":2,"value":"复核人:","colspan":colspan_num-2,"br":true},
						{"cell":0,"value":"制单人： ${user_name}","colspan":2,"br":false},
						{"cell":2,"value":"打印日期: " + cur_date,"colspan":colspan_num-2,"br":true}
	       			],
	       			columns:grid.getColumns(1),
	       			headCount:2,//列头行数
	       			autoFile:true,
	       			type:3
	       	};
	   		ajaxJsonObjectByUrl("queryAssMeasurePlan.do?isCheck=false", selPara, function (responseData) {
	   			printGridView(responseData,printPara);
			});

	   		
	    } */
	 
	  //打印模板设置 最新版
	    function printSet(){
		  
	    	var useId=0;//统一打印
			if('${ass_05105}'==1){
				//按用户打印
				useId='${user_id }';
			}
	    	
			officeFormTemplate({template_code:"05105",use_id : useId})
	    }
	 
	    //打印 最新版
	    function printDate(){
	    	
	    	 var useId=0;//统一打印
	 		if('${ass_05105}'==1){
	 			//按用户打印
	 			useId='${user_id }';
	 		}

	 		var data = gridManager.getCheckedRows();
			if (data.length == 0){
				$.ligerDialog.error('请选择行');
			}else{
				
				var plan_id ="" ;
				
				$(data).each(function (){	
					
					plan_id  += "'"+this.plan_id+"',"
						
				});
				
				 var para={
						 
						template_code:'05105',
						class_name:"com.chd.hrp.ass.serviceImpl.measure.AssMeasurePlanServiceImpl", 
						method_name:"queryAssMeasurePlanPrint",
						isSetPrint:false,//是否套打，默认非套打
						isPreview:true,//是否预览，默认直接打印
		    			paraId :plan_id.substring(0,plan_id.length-1) ,
		    			isPrintCount:false,//更新打印次数
		    			use_id:useId,
		    			p_num:1
		    			//isSetPrint:flag
		    	}; 
				ajaxJsonObjectByUrl("queryAssMeasurePlanState.do?isCheck=false",{paraId:plan_id.substring(0,plan_id.length-1),state:1},function(responseData){
					if (responseData.state == "true") {
						officeFormPrint(para);
					}
				});
			}
	    	
	    }   
	    
	 //导出数据
	 function exportExcel(){
		//有数据直接导出
		if($("#resultPrint > table > tbody").html()!=""){
			lodopExportExcel("resultPrint","导出Excel","检查计量计划.xls",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');

		var exportPara={
				
			usePager:false,
			
			plan_name:$("#plan_name").val(),
	           
	           ass_nature: liger.get("ass_nature").getValue(),
	           
	           create_emp: liger.get("create_emp").getValue(),
	           
	           create_date_begin:$("#create_date_begin").val(),
	           
	           create_date_end:$("#create_date_end").val(),
	           
	           audit_emp: liger.get("audit_emp").getValue(),
	           
	           audit_date_begin:$("#audit_date_begin").val(),
	           
	           audit_date_end:$("#audit_date_end").val(),
	           
	           state: liger.get("state").getValue()
           
         };
		
		ajaxJsonObjectByUrl("queryAssMeasurePlan.do",exportPara,function (responseData){
			
			$.each(responseData.Rows,function(idx,item){ 
				
				 var trHtml="<tr>";
					 
					 trHtml+="<td>"+item.plan_no+"</td>"; 
					 
					 trHtml+="<td>"+item.plan_name+"</td>"; 
					 
					 trHtml+="<td>"+item.plan_year+"</td>"; 
					 
					 trHtml+="<td>"+item.naturs_name+"</td>"; 
					 
					 trHtml+="<td>"+item.create_emp_name+"</td>"; 
					 
					 trHtml+="<td>"+item.create_date+"</td>"; 
					 
					 trHtml+="<td>"+item.audit_emp_name+"</td>"; 
					 
					 trHtml+="<td>"+item.audit_date+"</td>"; 
					 
 					 if (item.state == 0) {
						 
						 trHtml += "<td>新建</td>";
						 
					 }else if (item.state == 1) {
						 
						 trHtml += "<td>审核</td>";
						 
					 }else if (item.state == 2) {
						 
						 trHtml += "<td>终止</td>";
						 
					 }
					 
					 trHtml+="<td>"+item.check_way+"</td>"; 
					 
					 trHtml+="<td>"+item.note+"</td>"; 
					 
				 trHtml+="</tr>"; 
				 
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			lodopExportExcel("resultPrint","导出Excel","检查计量计划.xls",true);
	    },true,manager);
		return;
	 }		
	 
	 
	    function loadDict(){
	    	
			var param = {
	            	query_key:''
	        };
			
            //字典下拉框
    	//资产性质
      	autocomplete("#ass_nature", "../queryAssNaturs.do?isCheck=false", "id","text", true, true,param,true);
     	
    	//制单人
    	autocomplete("#create_emp","../../../hrp/sys/queryUserDict.do?isCheck=false", "id","text", true, true,param,true);
    	
    	//审核人
    	autocomplete("#audit_emp","../../../hrp/sys/queryUserDict.do?isCheck=false", "id","text", true, true,param,true);
    	
        $("#plan_name,#ass_table").ligerTextBox({width:160});
        
        $("#create_date_begin").ligerTextBox({width:90});
        
        $("#create_date_end").ligerTextBox({width:90});
        
        $("#audit_date_begin").ligerTextBox({width:90});
        
        $("#audit_date_end").ligerTextBox({width:90});
        autodate("#create_date_begin","YYYY-mm-dd","month_first");

		autodate("#create_date_end","YYYY-mm-dd","month_last");
		
	
    	//状态
    	$('#state').ligerComboBox({
			data:[{id:2,text:'终止'},{id:1,text:'审核'},{id:0,text:'新建'}],
			valueField: 'id',
            textField: 'text',
			cancelable:true,
			width:160
		})
         }  
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
        <tr> 
       		<td align="right" class="l-table-edit-td" style="padding-left: 20px;">制单日期：</td>
			<td align="left" class="l-table-edit-td" width="5%"><input
				name="create_date_begin" type="text" id="create_date_begin"
				  class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="left" >至：</td>
			<td align="left"><input name="create_date_end" type="text"
				id="create_date_end" 
				 class="Wdate"
				onFocus="WdatePicker({minDate:'#F{$dp.$D(\'create_date_begin\');}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">制单人：</td>
            <td align="left" class="l-table-edit-td"><input name="create_emp" type="text" id="create_emp"   /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">计量计划名称：</td>
            <td align="left" class="l-table-edit-td"><input name="plan_name" type="text" id="plan_name"   /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产性质：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_nature" type="text" id="ass_nature"   /></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td" style="padding-left: 20px;">审核日期：</td>
			<td align="left" class="l-table-edit-td"><input name="audit_date_begin" type="text" id="audit_date_begin"
				  class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="left"  >至：</td>
			<td align="left"><input name="audit_date_end" type="text" id="audit_date_end" 
				 class="Wdate"
				onFocus="WdatePicker({minDate:'#F{$dp.$D(\'audit_date_begin\');}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
             <td align="right" class="l-table-edit-td"  style="padding-left:20px;">审核人：</td>
            <td align="left" class="l-table-edit-td"><input name="audit_emp" type="text" id="audit_emp"   /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">状态：</td>
            <td align="left" class="l-table-edit-td">
            				<!-- <select id="state" name="state"> 
			            			<option value="">请选择</option>
			                		<option value="0">新建</option>
			                		<option value="1">审核</option>
			                		<option value="2">终止</option>
			                	</select> -->
			          <input name="state" type="text" id="state" />      	
            </td>
        </tr>  
        <tr>
        	<td align="right" class="l-table-edit-td"  >资产信息：</td>
            <td align="left" class="l-table-edit-td" colspan="3">
           		<input name="ass_table" type="text" id="ass_table"  />
            </td>
        </tr>
    </table>
	<div id="maingrid"></div>
	<div id="resultPrint" style="display:none">
	   	<table width="100%">
			<thead>
		 
			<tr> 
                <th width="200">计量计划编号</th>	
                <th width="200">计量计划名称</th>	
                <th width="200">计量计划年份</th>	
                <th width="200">资产性质</th>	
                <th width="200">制单人</th>	
                <th width="200">制单日期</th>	
                <th width="200">审核人</th>	
                <th width="200">审核日期</th>	
                <th width="200">状态</th>	
                <th width="200">检查方式</th>	
                <th width="200">备注</th>	
			</tr>
			   	</thead>
			   	<tbody></tbody>
	   	</table>
   	</div>
</body>
</html>
