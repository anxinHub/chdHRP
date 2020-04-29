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
		 showDetail();
		 show_detail = $("#show_detail").is(":checked") ? 1 : 0;
    });
    //查询
    function  query(){
    	
    		grid.options.parms=[];
    		
    		grid.options.newPage=1;
    		
        //根据表字段进行添加查询条件
    	  
    	  grid.options.parms.push({name:'plan_id',value:liger.get("plan_id").getValue()}); 
    	  
    	  grid.options.parms.push({name:'ass_table',value:$("#ass_table").val()}); 
        
    	  grid.options.parms.push({name:'ass_nature',value:liger.get("ass_nature").getValue()}); 
    	  
    	  grid.options.parms.push({name:'state',value:liger.get("state").getValue()}); 
    	  
    	  grid.options.parms.push({name:'maintain_dept_id',value:liger.get("maintain_dept_id").getValue().split("@")[0]}); 
    	  
    	  //grid.options.parms.push({name:'maintain_dept_no',value:liger.get("maintain_dept_id").getValue().split("@")[1]}); 
    	  
    	  grid.options.parms.push({name:'create_emp',value:liger.get("create_emp").getValue()}); 
    	  
		  grid.options.parms.push({name:'create_date_begin',value:$("#create_date_begin").val()}); 
    	  
    	  grid.options.parms.push({name:'create_date_end',value:$("#create_date_end").val()});
    	  
    	  grid.options.parms.push({name:'audit_date_begin',value:$("#audit_date_begin").val()});
    	  
    	  grid.options.parms.push({name:'audit_date_end',value:$("#audit_date_end").val()});
    	  
    	  grid.options.parms.push({name:'audit_emp',value:liger.get("audit_emp").getValue()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
    	
		$("#resultPrint > table > tbody").empty();
		
     }
    

    function loadHead(){
    	if(show_detail == "1"){	
	    	grid = $("#maingrid").ligerGrid({
	           columns: [ 
	                     { display: '保养序号', name: 'rec_no', align: 'left',minWidth:'100',frozen: true,
	                    	 
	                    	 render : function(rowdata, rowindex,value) {
	                    		 
	   							return "<a href=javascript:openUpdate('"+rowdata.group_id   + "|" + 
																		rowdata.hos_id   + "|" + 
																		rowdata.copy_code   + "|" + 
																		rowdata.rec_id    +"')>"+
	   									                                 rowdata.rec_no+"</a>";
	   										}
						 		},
	                     { display: '保养计划', name: 'plan_name', align: 'left',minWidth:'100'
						 		},
	                     { display: '资产性质', name: 'naturs_name', align: 'left',minWidth:'100',
						 			
								render : function(rowdata, rowindex, value) {
						 				
										return rowdata.naturs_name;
										
										}
						 		
						 		},
				 		{ display: '资产编码', name: 'ass_code',minWidth: 100, align: 'left' },
						{ display: '资产名称', name: 'ass_name',minWidth: 100, align: 'left'},
						{ display: '型号', name: 'ass_mondl',minWidth: 100, align: 'left'},
						{ display: '规格', name: 'ass_spec', minWidth: 100,align: 'left'},
						{ display: '品牌', name: 'ass_brand', minWidth: 100,align: 'left'}, 
					    { display: '生产厂家', name: 'fac_name',minWidth: 100, align: 'left'},		
					    { display: '工时单位', name: 'maintain_unit',minWidth: 100, align: 'left',
							render : function(item) {
								if (item.maintain_unit == 0) {
									return '年';
								} else if (item.maintain_unit == 1) {
									return '月';
								} else if(item.maintain_unit == 2){
									return '日';
								}else if(item.maintain_unit == 3){
									return '时';
								}else if(item.maintain_unit == 4){
									return '分';
								}
							}	
						},		
	                     { display: '计划执行日期', name: 'plan_exec_date', align: 'left',minWidth:'100'
						 		},
	                     { display: '实际执行日期', name: 'fact_exec_date', align: 'left',minWidth:'100'
						 		},
			             { display: '执行人', name: 'fact_exec_emp', align: 'left',minWidth:'100',
						 			
									render : function(rowdata, rowindex, value) {
							 				
											return rowdata.fact_exec_emp_name;
											
											}
							 		
							    },
			             { display: '制单人', name: 'create_emp', align: 'left',minWidth:'100',
						 			
									render : function(rowdata, rowindex, value) {
							 				
											return rowdata.create_emp_name;
											
									 }
							    },
		                 { display: '制单时间', name: 'create_date', align: 'left',minWidth:'100'
							    },
		                 { display: '审核时间', name: 'audit_date', align: 'left',minWidth:'100'
							 		},
		                 { display: '审核人', name: 'audit_emp', align: 'left',minWidth:'100',
							 			
									render : function(rowdata, rowindex, value) {
							 				
											return rowdata.audit_emp_name;
									 }
							    },
			             { display: '状态', name: 'state', align: 'left',minWidth:'100',
						 			
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
			             { display: '保养部门', name: 'maintain_dept_id', align: 'left',minWidth:'100',
						 			
									render : function(rowdata, rowindex, value) {
							 				
											return rowdata.maintain_dept_name;
											
											}
							 	},
	                     { display: '保养工时', name: 'maintain_hours', align: 'left',minWidth:'100'
						 		},
	                     { display: '保养费用', name: 'maintain_money', align: 'right',minWidth:'100',
						 			render : function(rowdata, rowindex,
											value) {
										 return formatNumber(rowdata.maintain_money,'${ass_05005}',1);
									}
						 		},
	                     { display: '保养说明', name: 'maintain_desc', align: 'left',minWidth:'100'
						 		},
	
	
	
	                     ],
	                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAssMaintainRec.do?isCheck=false&show_detail=1',
	                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
	                     selectRowButtonOnly:true,
	                     toolbar: { items: [
	                     	{ text: '查询（<u>E</u>）', id:'search', click: query,icon:'search' },
	                     	{ line:true },
					    					{ text: '添加（<u>A</u>）', id:'add', click: add_open, icon:'add' },
					    	                { line:true },
					    	                { text: '删除（<u>D</u>）', id:'delete', click: remove,icon:'delete' },
										    { line:true }, 
								            { text: '审核（<u>R</u>）', id:'audit', click: auditMaintainRec, icon:'right' },
						    	            { line:true },
						    	            { text: '消审（<u>B</u>）', id:'back', click: backMaintainRec,icon:'back' },
											{ line:true },
											{ text: '终止计划（<u>S</u>）', id:'stop', click: stopMaintainRec,icon:'lock' },
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
									rowdata.rec_id 
								);
	    				} 
	                   });
    	}else{
    		grid = $("#maingrid").ligerGrid({
    	           columns: [ 
    	                     { display: '保养序号', name: 'rec_no', align: 'left',width:'100',frozen: true,
    	                    	 
    	                    	 render : function(rowdata, rowindex,value) {
    	                    		 
    	   							return "<a href=javascript:openUpdate('"+rowdata.group_id   + "|" + 
    																		rowdata.hos_id   + "|" + 
    																		rowdata.copy_code   + "|" + 
    																		rowdata.rec_id    +"')>"+
    	   									                                 rowdata.rec_no+"</a>";
    	   										}
    						 		},
    	                     { display: '保养计划', name: 'plan_name', align: 'left',width:'100'
    						 		},
    	                     { display: '资产性质', name: 'naturs_name', align: 'left',width:'100',
    						 			
    								render : function(rowdata, rowindex, value) {
    						 				
    										return rowdata.naturs_name;
    										
    										}
    						 		
    						 		},
    	                     { display: '计划执行日期', name: 'plan_exec_date', align: 'left',width:'100'
    						 		},
    	                     { display: '实际执行日期', name: 'fact_exec_date', align: 'left',width:'100'
    						 		},
    			             { display: '执行人', name: 'fact_exec_emp', align: 'left',width:'100',
    						 			
    									render : function(rowdata, rowindex, value) {
    							 				
    											return rowdata.fact_exec_emp_name;
    											
    											}
    							 		
    							    },
    			             { display: '制单人', name: 'create_emp', align: 'left',width:'100',
    						 			
    									render : function(rowdata, rowindex, value) {
    							 				
    											return rowdata.create_emp_name;
    											
    									 }
    							    },
    		                 { display: '制单时间', name: 'create_date', align: 'left',width:'100'
    							    },
    		                 { display: '审核时间', name: 'audit_date', align: 'left',width:'100'
    							 		},
    		                 { display: '审核人', name: 'audit_emp', align: 'left',width:'100',
    							 			
    									render : function(rowdata, rowindex, value) {
    							 				
    											return rowdata.audit_emp_name;
    									 }
    							    },
    			             { display: '状态', name: 'state', align: 'left',width:'100',
    						 			
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
    			             { display: '保养部门', name: 'maintain_dept_id', align: 'left',width:'100',
    						 			
    									render : function(rowdata, rowindex, value) {
    							 				
    											return rowdata.maintain_dept_name;
    											
    											}
    							 	},
    	                     { display: '保养工时', name: 'maintain_hours', align: 'left',width:'100'
    						 		},
    	                     { display: '保养费用', name: 'maintain_money', align: 'right',width:'100',
    						 			render : function(rowdata, rowindex,
    											value) {
    										 return formatNumber(rowdata.maintain_money,'${ass_05005}',1);
    									}
    						 		},
    	                     { display: '保养说明', name: 'maintain_desc', align: 'left',width:'100'
    						 		},



    	                     ],
    	                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAssMaintainRec.do?isCheck=false&show_detail=0',
    	                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
    	                     selectRowButtonOnly:true,
    	                     toolbar: { items: [
    	                     	{ text: '查询（<u>E</u>）', id:'search', click: query,icon:'search' },
    	                     	{ line:true },
    					    					{ text: '添加（<u>A</u>）', id:'add', click: add_open, icon:'add' },
    					    	                { line:true },
    					    	                { text: '删除（<u>D</u>）', id:'delete', click: remove,icon:'delete' },
    										    { line:true }, 
    								            { text: '审核（<u>R</u>）', id:'audit', click: auditMaintainRec, icon:'right' },
    						    	            { line:true },
    						    	            { text: '消审（<u>B</u>）', id:'back', click: backMaintainRec,icon:'back' },
    											{ line:true },
    											{ text: '终止计划（<u>S</u>）', id:'stop', click: stopMaintainRec,icon:'lock' },
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
    									rowdata.rec_id 
    								);
    	    				} 
    	                   });
    	}
        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function add_open(){
    	parent.$.ligerDialog.open({
			title: '保养记录',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/ass/assmaintainrec/assMaintainRecAddPage.do?isCheck=false',
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量

		});
    	
    	}
    	
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
							this.rec_id  +"@"+ 
							this.state 
							
							) });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteAssMaintainRec.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
    	}
   
    function auditMaintainRec(){
  	   
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
  				
  					this.rec_id     +"@"+ 
  				
  					this.state   
  				
  				); });
             $.ligerDialog.confirm('确定审核?', function (yes){
             	if(yes){
                 	ajaxJsonObjectByUrl("auditMaintainRec.do",{ParamVo : ParamVo.toString()},function (responseData){
                 		if(responseData.state=="true"){
                 			query();
                 		}
                 	});
             	}
             });
      	   
         }
  	   
     }
     
     function backMaintainRec (){
  	   
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
  				
  					this.rec_id     +"@"+ 
  				
  					this.state   
  				
  				); });
             $.ligerDialog.confirm('确定销审?', function (yes){
             	if(yes){
                 	ajaxJsonObjectByUrl("backMaintainRec.do",{ParamVo : ParamVo.toString()},function (responseData){
                 		if(responseData.state=="true"){
                 			query();
                 		}
                 	});
             	}
             });
      	   
         }
  	   
     }
     
     
     function stopMaintainRec (){
     	
   	   
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
   				
   					this.rec_id     
   				
   				); });
              $.ligerDialog.confirm('确定终止计划?', function (yes){
              	if(yes){
                  	ajaxJsonObjectByUrl("stopMaintainRec.do",{ParamVo : ParamVo.toString()},function (responseData){
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
			"rec_id="+vo[3] 
		parent.$.ligerDialog.open({
			title: '保养记录',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/ass/assmaintainrec/assMaintainRecUpdatePage.do?isCheck=false&' + parm,
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量

		});
  
    
    }
    
    //键盘事件
	  function loadHotkeys() {

		hotkeys('E', query);

		hotkeys('A', add_open);
		
		hotkeys('D', remove);
		
		hotkeys('R', auditMaintainRec);
		
		hotkeys('B', backMaintainRec);
		
		hotkeys('S', stopMaintainRec);
		
		hotkeys('E', exportExcel);

		hotkeys('P', printDate);
		 
	 }
 
	 
	 
	 //导出数据
	 function exportExcel(){
		 
		//有数据直接导出
		if($("#resultPrint > table > tbody").html()!=""){
			
			lodopExportExcel("resultPrint","导出Excel","保养记录.xls",true);
			
			return;
			
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');

		var exportPara={
				
				usePager:false,
				
				plan_id :liger.get("plan_id").getValue(),
	           
	           ass_nature:liger.get("ass_nature").getValue(),
	           
	           state :liger.get("state").getValue(),
	           
	           maintain_dept_id:liger.get("maintain_dept_id").getValue().split("@")[0],
	           
	           create_emp:liger.get("create_emp").getValue(),
	           
	           create_date_begin:$("#create_date").val(),
	           
	           create_date_end:$("#create_date_end").val(),
	           
	           audit_date_begin:$("#audit_date_begin").val(),
	           
	           audit_date_end:$("#audit_date_end").val(),
	           
	           audit_emp:liger.get("audit_emp").getValue(),
           
         };
		
		ajaxJsonObjectByUrl("queryAssMaintainRec.do",exportPara,function (responseData){
			
			$.each(responseData.Rows,function(idx,item){ 
				
				 var trHtml="<tr>";
					 
					 trHtml+="<td>"+item.rec_no+"</td>"; 
					 
					 trHtml+="<td>"+item.ass_year+"</td>"; 
					 
					 trHtml+="<td>"+item.ass_month+"</td>"; 
					 
					 trHtml+="<td>"+item.plan_name+"</td>"; 
					 
					 trHtml+="<td>"+item.naturs_name+"</td>"; 
					 
					 trHtml+="<td>"+item.fact_exec_emp+"</td>"; 
					 
					 trHtml+="<td>"+item.plan_exec_date+"</td>"; 
					 
					 trHtml+="<td>"+item.fact_exec_date+"</td>"; 
					 
					 trHtml+="<td>"+item.maintain_hours+"</td>"; 
					 
					 trHtml+="<td>"+item.maintain_money+"</td>"; 
					 
					 trHtml+="<td>"+item.maintain_desc+"</td>"; 
					 
 					 if (item.state == 0) {
						 
						 trHtml += "<td>新建</td>";
						 
					 }else if (item.state == 1) {
						 
						 trHtml += "<td>审核</td>";
						 
					 }else if (item.state == 2) {
						 
						 trHtml += "<td>终止</td>";
						 
					 }
					 
					 trHtml+="<td>"+item.maintain_dept_name+"</td>";  
					 
					 trHtml+="<td>"+item.create_emp_name+"</td>"; 
					 
					 trHtml+="<td>"+item.create_date+"</td>"; 
					 
					 trHtml+="<td>"+item.audit_date+"</td>"; 
					 
					 trHtml+="<td>"+item.audit_emp_name+"</td>";
					 
				 trHtml+="</tr>";
				  
				 $("#resultPrint > table > tbody").append(trHtml);
				 
			});
			
			manager.close();
			
			lodopExportExcel("resultPrint","导出Excel","保养记录.xls",true);
			
	    },true,manager);
		
		return;
	 }		
	 
	 
 //打印模板设置 最新版
    function printSet(){
	  
    	var useId=0;//统一打印
		if('${ass_05104}'==1){
			//按用户打印
			useId='${user_id }';
		}
    	
		officeFormTemplate({template_code:"05104",use_id : useId})
    }
 
    //打印 最新版
    function printDate(){
    	
    	 var useId=0;//统一打印
 		if('${ass_05104}'==1){
 			//按用户打印
 			useId='${user_id }';
 		}

 		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
			
			var rec_id ="" ;
			
			$(data).each(function (){	
				
				rec_id  += "'"+this.rec_id+"',"
					
			});
			
			 var para={
					 
					template_code:'05104',
					class_name:"com.chd.hrp.ass.serviceImpl.maintain.AssMaintainRecServiceImpl", 
					method_name:"queryAssMainRecPrint",
					isSetPrint:false,//是否套打，默认非套打
					isPreview:true,//是否预览，默认直接打印
	    			paraId :rec_id.substring(0,rec_id.length-1) ,
	    			isPrintCount:false,//更新打印次数
	    			use_id:useId,
	    			p_num:1
	    			//isSetPrint:flag
	    	}; 
			ajaxJsonObjectByUrl("queryAssMainRecState.do?isCheck=false",{paraId:rec_id.substring(0,rec_id.length-1),state:1},function(responseData){
				if (responseData.state == "true") {
					officeFormPrint(para);
				}
			});
		}
    	
    }
	 
	 
	 
	 
	 function loadDict(){
		 
			var param = {
	            	query_key:''
	        };
			
         //字典下拉框
    //保养部门
    autocomplete("#maintain_dept_id", "../queryDeptDict.do?isCheck=false", "id","text", true, true,param,true);
    	
 	//资产性质
  	autocomplete("#ass_nature", "../queryAssNaturs.do?isCheck=false", "id","text", true, true,param,true);
         
 	//计划执行人
	autocomplete("#fact_exec_emp","../../../hrp/sys/queryUserDict.do?isCheck=false", "id","text", true, true,param,true);
 	
	//制单人
	autocomplete("#create_emp","../../../hrp/sys/queryUserDict.do?isCheck=false", "id","text", true, true,param,true);
	
	//审核人
	autocomplete("#audit_emp","../../../hrp/sys/queryUserDict.do?isCheck=false", "id","text", true, true,param,true);
	
	//保养计划
	autocomplete("#plan_id","../queryAssMaintainPlanDict.do?isCheck=false", "id","text", true, true,param,true);
	
	//状态
	/* $("#state").ligerComboBox({
		
		width : 160
		
	}); */
	$('#state').ligerComboBox({
		data:[{id:0,text:'新建'},{id:1,text:'审核'}],
		valueField: 'id',
        textField: 'text',
		cancelable:true,
		width:160
	}) 
	
	$("#ass_table").ligerTextBox({width:160});
	
    $("#create_date_begin").ligerTextBox({width:90});
    
    $("#create_date_end").ligerTextBox({width:90});
    
    $("#audit_date_begin").ligerTextBox({width:90});
    
    $("#audit_date_end").ligerTextBox({width:90});
    autodate("#create_date_begin","YYYY-mm-dd","month_first");

	autodate("#create_date_end","YYYY-mm-dd","month_last");
	
      } 
	//是否显示明细
   function showDetail() {
	show_detail = $("#show_detail").is(":checked") ? 1 : 0;
	/* if (grid) {
		//由于一个对象多次绑定相同的事件，需要进行解绑在绑定
		grid.unbind(); 
	}
	loadHead(); */
	if (grid) {
		//由于一个对象多次绑定相同的事件，需要进行解绑在绑定
		grid.unbind(); 
		grid.bind('contextmenu', grid.options.onContextmenu);
	}
	loadHead();
	//console.log(grid);
}
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        
         <tr>
         	<td align="right" class="l-table-edit-td" style="padding-left: 20px;">制单日期：</td>
			<td align="left" class="l-table-edit-td"><input
				name="create_date_begin" type="text" id="create_date_begin"
				 class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="left" >至：</td>
			<td align="left"><input name="create_date_end" type="text"
				id="create_date_end" 
				 class="Wdate"
				onFocus="WdatePicker({minDate:'#F{$dp.$D(\'create_date_begin\');}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">制单人：</td>
            <td align="left" class="l-table-edit-td"><input name="create_emp" type="text" id="create_emp"  /></td>
             
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">保养部门：</td>
            <td align="left" class="l-table-edit-td"><input name="maintain_dept_id" type="text" id="maintain_dept_id"  /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产性质：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_nature" type="text" id="ass_nature"  /></td> 
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
            <td align="left" class="l-table-edit-td"><input name="audit_emp" type="text" id="audit_emp"  /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">保养计划：</td>
            <td align="left" class="l-table-edit-td"><input name="plan_id" type="text" id="plan_id"  /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">状态：</td>
            <td align="left" class="l-table-edit-td">
						<!-- <select id="state" name="state"> 
			            			<option value="">请选择</option>
			                		<option value="0">新建</option>
			                		<option value="1">审核</option>
			                	</select> -->
			         <input name="state" type="text" id="state" />       	
			</td>
        </tr> 
        <tr>
        	<td align="right" class="l-table-edit-td"  >资产信息：</td>
            <td align="left" class="l-table-edit-td" colspan="3">
           		<input name="ass_table" type="text" id="ass_table"  />
	         </td>
	         </td>
			<td align="left" class="l-table-edit-td" >
            	<input name="show_detail" type="checkbox" id="show_detail" onclick="showDetail();"/>&nbsp;&nbsp;显示明细
             </td>
        </tr>
    </table>
	<div id="maingrid"></div>
	<div id="resultPrint" style="display:none">
	   	<table width="100%">
			<thead>
		 
			<tr> 
                <th width="200">保养序号</th>	
                <th width="200">统计年度</th>	
                <th width="200">统计月份</th>	
                <th width="200">计划</th>	
                <th width="200">资产性质</th>	
                <th width="200">执行人</th>	
                <th width="200">计划执行日期</th>	
                <th width="200">实际执行日期</th>	
                <th width="200">保养工时</th>	
                <th width="200">保养费用</th>	
                <th width="200">保养说明</th>	
                <th width="200">状态</th>	
                <th width="200">保养部门</th> 	
                <th width="200">制单人</th>	
                <th width="200">制单时间</th>	
                <th width="200">审核时间</th>	
                <th width="200">审核人</th>	
			</tr>
			   	</thead>
			   	<tbody></tbody>
	   	</table>
   	</div>
</body>
</html>
