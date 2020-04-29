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
    		
        //根据表字段进行添加查询条件 
    	  
    	  grid.options.parms.push({name:'plan_id',value:liger.get("plan_id").getValue()}); 
    	  
    	  grid.options.parms.push({name:'ass_table',value:$("#ass_table").val()}); 
        
    	  grid.options.parms.push({name:'measure_kind',value:liger.get("measure_kind").getValue(),}); 
    	  
    	  grid.options.parms.push({name:'inner_measure_dept_id',value:liger.get("inner_measure_dept_id").getValue().split("@")[0]}); 
    	  
    	  grid.options.parms.push({name:'state',value:liger.get("state").getValue(),}); 
    	  
    	  grid.options.parms.push({name:'measure_result',value:liger.get("measure_result").getValue(),}); 

    	//加载查询条件
    	grid.loadData(grid.where);
    	
		
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [  
                     { display: '计量记录编号', name: 'seq_no', align: 'left',width:'100',frozen: true,
                    	 
                    	 render : function(rowdata, rowindex,value) {
                    		 
 							return "<a href=javascript:openUpdate('"+rowdata.group_id   + "|" + 
																	rowdata.hos_id   + "|" + 
																	rowdata.copy_code   + "|" + 
																	rowdata.rec_id  +"')>"+
 									                                 rowdata.seq_no+"</a>";
 										}
					 		},
                     { display: '计量计划', name: 'plan_id', align: 'left',width:'100',
					 			
							render : function(rowdata, rowindex, value) {
					 				
									return rowdata.plan_name;
									
									}
					 		},
                     { display: '计量证书号', name: 'cert_no', align: 'left',width:'100'
					 		},
                     { display: '资产性质', name: 'ass_nature', align: 'left',width:'100',
					 			
							render : function(rowdata, rowindex, value) {
								if (rowdata.ass_nature == 1) {// 2018-2-8 年后会删除掉  只是应急使用 
									
									return "房屋及建筑";
									
								} if (rowdata.ass_nature == 2){
									
									return "专业设备";
									
								} if (rowdata.ass_nature == 3){
									
									return "一般设备";
								} if (rowdata.ass_nature == 4){
									
									return "其他固定资产设备";
									
								} if (rowdata.ass_nature == 5){
									
									return "无形资产设备";
									
								} if (rowdata.ass_nature == 6){
									
									return "土地设备";
								}else{
									return rowdata.naturs_name;
									
								}
								}
					 		},
			        { display: '检测单位', name: 'outer_measure_org', align: 'left',width:'100'
							 },
                     { display: '外部计量员', name: 'outer_measure_engr', align: 'left',width:'100',
					 			
							render : function(rowdata, rowindex, value) {
					 				
									return rowdata.outer_measure_engr;
									
									}
					 		},
                     { display: '内部计量员', name: 'inner_measure_emp', align: 'left',width:'100',
					 			
							render : function(rowdata, rowindex, value) {
					 				
									return rowdata.inner_measure_emp_name;
									
									}
					 		},
                     { display: '计划计量日期', name: 'plan_measure_date', align: 'left',width:'100'
					 		},
                     { display: '实际计量日期', name: 'fact_measure_date', align: 'left',width:'100'
					 		},
                     { display: '预计下次日期', name: 'pre_next_date', align: 'left',width:'100'
					 		},
		             { display: '制单人', name: 'create_emp', align: 'left',width:'100',
					 			
								render : function(rowdata, rowindex, value) {
						 				
										return rowdata.create_emp_name;
										
										}
						      },
	                 { display: '制单时间', name: 'create_date', align: 'left',width:'100'
						 	 },
	                 { display: '审核人', name: 'audit_emp', align: 'left',width:'100',
						 			
								render : function(rowdata, rowindex, value) {
						 				
										return rowdata.audit_emp_name;
										
										}
						 	  },
	                 { display: '审核时间', name: 'audit_date', align: 'left',width:'100'
						 	  },
 					 { display: '状态', name: 'state', align: 'left',width:'100',
					 			
								render : function(rowdata, rowindex, value) {
					 				
									if (rowdata.state == 0) {
										
										return "新建";
										
									} else {
										
										return "审核";
										
									}
								
								}
					 		
					 		},
                     { display: '计量工时', name: 'measure_hours', align: 'left',width:'100'
					 		},
					 { display: '计量费用', name: 'measure_money', align: 'right',width:'100',
					 			render : function(rowdata, rowindex,
										value) {
									 return formatNumber(rowdata.measure_money,'${ass_05005}',1);
								}
					 		},
                     { display: '其他费用', name: 'other_money', align: 'right',width:'100',
					 			render : function(rowdata, rowindex,
										value) {
									 return formatNumber(rowdata.other_money,'${ass_05005}',1);
								}
					 		},
                     
                     { display: '计量说明', name: 'measure_memo', align: 'left',width:'100'
					 		},
                   
                     { display: '计量结果', name: 'measure_result', align: 'left',width:'100',
					 			
								render : function(rowdata, rowindex, value) {
					 				
									if (rowdata.measure_result == 0) {
										
										return "完成";
										
									} else {
										
										return "未完成";
										
									}
								
								}
					 		
					 		},
                     { display: '检测方式', name: 'measure_kind', align: 'left',width:'100',
					 			
								render : function(rowdata, rowindex, value) {
					 				
									if (rowdata.measure_kind == 1) {
										
										return "自检";
										
									} else if (rowdata.measure_kind == 2) {
										
										return "送检";
										
									} else if (rowdata.measure_kind == 3) {
										
										return "抽检";
										
									} else if(rowdata.measure_kind == 4){
										return "现检";
									}else{
										return "";
									}
								
								}
					 		
					 		},
                     { display: '经办人', name: 'deal_emp', align: 'left',width:'100',
					 			
							render : function(rowdata, rowindex, value) {
					 				
									return rowdata.deal_emp_name;
									
									}
					 		
					 		},
                     { display: '委托单号', name: 'entrust_no', align: 'left',width:'100'
					 		},
		             { display: '鉴定依据', name: 'firm_basis', align: 'left',width:'100'
						 		},
		             { display: '检验说明', name: 'measure_idea', align: 'left',width:'100'
							 		}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAssMeasureRec.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,
                     toolbar: { items: [
                     	{ text: '查询（<u>E</u>）', id:'search', click: query,icon:'search' },
                     	{ line:true },
				    					{ text: '添加（<u>A</u>）', id:'add', click: add_open, icon:'add' },
				    	                { line:true },
				    	                { text: '删除（<u>D</u>）', id:'delete', click: remove,icon:'delete' },
										{ line:true }, 
										{ text: '审核（<u>R</u>）', id:'audit', click: auditMeasureRec, icon:'right' },
					    	            { line:true },
					    	            { text: '销审（<u>B</u>）', id:'back', click: backMeasureRec,icon:'back' },
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

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function add_open(){
    	
    /* 	var index = layer.open({
					type : 2,
					title : '051204 检测计量记录',
					shadeClose : false,
					shade : false,
					maxmin : true, //开启最大化最小化按钮
					area : [ '893px', '500px' ],
					content : 'assMeasureRecAddPage.do?isCheck=false'
				}); */
			
				parent.$.ligerDialog.open({
					title : '检测计量记录添加',
					height : $(window).height(),
					width : $(window).width(),
					url : 'hrp/ass/assmeasurerec/assMeasureRecAddPage.do?isCheck=false',
					modal : true,
					showToggle : false,
					showMax : true,
					showMin : false,
					isResize : true,
					parentframename : window.name, //用于parent弹出层调用本页面的方法或变量
				});	/* layer.full(index); */
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
							this.rec_id +"@"+ 
							this.state 
							) });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteAssMeasureRec.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
    	}
    
    function auditMeasureRec(){
   	   
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
                  	ajaxJsonObjectByUrl("auditMeasureRec.do",{ParamVo : ParamVo.toString()},function (responseData){
                  		if(responseData.state=="true"){
                  			query();
                  		}
                  	});
              	}
              });
       	   
          }
   	   
      }
      
      function backMeasureRec (){
   	   
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
   				
   				); });
              $.ligerDialog.confirm('确定销审?', function (yes){
              	if(yes){
                  	ajaxJsonObjectByUrl("backMeasureRec.do",{ParamVo : ParamVo.toString()},function (responseData){
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
		 
    /* var index = layer.open({
					type : 2,
					title : '科室申请资产明细',
					shadeClose : false,
					shade : false,
					maxmin : true, //开启最大化最小化按钮
					area : [ '893px', '500px' ],
					content : 'assMeasureRecUpdatePage.do?isCheck=false&' + parm
				});
				layer.full(index);	 */
		parent.$.ligerDialog.open({
			title : '检测计量记录修改',			
			height : $(window).height(),
			width : $(window).width(),
			url : 'hrp/ass/assmeasurerec/assMeasureRecUpdatePage.do?isCheck=false&'+parm,
			modal : true,
			showToggle : false,
			showMax : true,
			showMin : false,
			isResize : true,
			parentframename : window.name, //用于parent弹出层调用本页面的方法或变量
		});
				//layer.full(index);	
    }
 
    //键盘事件
	  function loadHotkeys() {

		hotkeys('E', query);

		hotkeys('A', add_open);
		
		hotkeys('D', remove);
		
		hotkeys('R', auditMeasureRec);
		
		hotkeys('B', backMeasureRec);
		
		hotkeys('E', exportExcel);

		hotkeys('P', printDate);
		 
	 }
  //打印数据
// 	 function printDate(){
// 		//有数据直接打印
// 		if($("#resultPrint > table > tbody").html()!=""){
// 			lodopPrinterTable("resultPrint","开始打印","检测计量记录",true);
// 			return;
// 		}
		
// 		//重新查询数据，避免分页导致打印数据不全
// 		var manager = $.ligerDialog.waitting('系统正在准备打印数据,请稍候...');

// 		var printPara={
				
// 			usePager:false, 
			
//             plan_id :liger.get("plan_id").getValue(),
            
//             measure_kind:liger.get("measure_kind").getValue(),
            
//             inner_measure_dept_id:liger.get("inner_measure_dept_id").getValue().split("@")[0],
            
//             measure_type :liger.get("measure_type").getValue(),
            
//             state :liger.get("state").getValue(),
            
//             measure_result :liger.get("measure_result").getValue()
            
//          };
// 		ajaxJsonObjectByUrl("queryAssMeasureRec.do",printPara,function (responseData){
// 			$.each(responseData.Rows,function(idx,item){ 
// 				 var trHtml="<tr>"; 
// 					 trHtml+="<td>"+item.seq_no+"</td>"; 
// 					 trHtml+="<td>"+item.plan_name+"</td>"; 
// 					 trHtml+="<td>"+item.cert_no+"</td>"; 
// 					 trHtml+="<td>"+item.naturs_name+"</td>"; 
// 					 trHtml+="<td>"+item.outer_measure_engr_name+"</td>"; 
// 					 trHtml+="<td>"+item.inner_measure_emp_name+"</td>"; 
// 					 trHtml+="<td>"+item.plan_measure_date+"</td>"; 
// 					 trHtml+="<td>"+item.fact_measure_date+"</td>"; 
// 					 trHtml+="<td>"+item.pre_next_date+"</td>"; 
// 					 trHtml+="<td>"+item.measure_hours+"</td>"; 
// 					 trHtml+="<td>"+item.other_money+"</td>"; 
// 					 trHtml+="<td>"+item.measure_money+"</td>"; 
// 					 trHtml+="<td>"+item.measure_memo+"</td>";  
// 					 trHtml += "<td>" + (item.measure_kind == 0 ? '机器检测' : '手工检测') + "</td>";
// 					 trHtml+="<td>"+item.inner_measure_dept_name+"</td>";  
// 					 trHtml+="<td>"+item.outer_measure_org+"</td>"; 
// 					 trHtml+="<td>"+item.create_emp_name+"</td>"; 
// 					 trHtml+="<td>"+item.create_date+"</td>"; 
// 					 trHtml+="<td>"+item.audit_emp_name+"</td>"; 
// 					 trHtml+="<td>"+item.audit_date+"</td>"; 
// 					 trHtml+="<td>"+item.deal_emp_name+"</td>";  
// 					 trHtml += "<td>" + (item.measure_type == 0 ? '一级级别' : '二级级别') + "</td>";
// 					 trHtml += "<td>" + (item.state == 0 ? '新建' : '审核') + "</td>";
// 					 trHtml+="<td>"+item.measure_result+"</td>"; 
// 					 trHtml += "<td>" + (item.measure_result == 0 ? '完成' : '未完成') + "</td>";
// 					 trHtml+="<td>"+item.measure_idea+"</td>"; 
// 				 trHtml+="</tr>"; 
// 				$("#resultPrint > table > tbody").append(trHtml);
// 			});
// 			manager.close();
// 			//alert($("#resultPrint").html())
// 			lodopPrinterTable("resultPrint","开始打印","检测计量记录",true);
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
	       			title:'计量记录',
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
	   		ajaxJsonObjectByUrl("queryAssMeasureRec.do?isCheck=false", selPara, function (responseData) {
	   			printGridView(responseData,printPara);
			});

	   		
	    } */
	 
	    
	  //打印模板设置 最新版
	    function printSet(){
		  
	    	var useId=0;//统一打印
			if('${ass_05106}'==1){
				//按用户打印
				useId='${user_id }';
			}
	    	
			officeFormTemplate({template_code:"05106",use_id : useId})
	    }
	 
	    //打印 最新版
	    function printDate(){
	    	
	    	 var useId=0;//统一打印
	 		if('${ass_05106}'==1){
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
						 
						template_code:'05106',
						class_name:"com.chd.hrp.ass.serviceImpl.measure.AssMeasureRecServiceImpl", 
						method_name:"queryAssMeasureRecPrint",
						isSetPrint:false,//是否套打，默认非套打
						isPreview:true,//是否预览，默认直接打印
		    			paraId :rec_id.substring(0,rec_id.length-1) ,
		    			isPrintCount:false,//更新打印次数
		    			use_id:useId,
		    			p_num:1
		    			//isSetPrint:flag
		    	}; 
				ajaxJsonObjectByUrl("queryAssMeasureRecState.do?isCheck=false",{paraId:rec_id.substring(0,rec_id.length-1),state:1},function(responseData){
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
			lodopExportExcel("resultPrint","导出Excel","检测计量记录.xls",true);
			return;
		}
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');

		var exportPara={
				usePager:false, 
	            plan_id :liger.get("plan_id").getValue(),
	            measure_kind:$("#measure_kind").val(),
	            inner_measure_dept_id:liger.get("inner_measure_dept_id").getValue().split("@")[0],
	            measure_type :liger.get("measure_type").getValue(),
	            state :liger.get("state").getValue(),
	            measure_result :liger.get("measure_result").getValue()
         };
		ajaxJsonObjectByUrl("queryAssMeasureRec.do",exportPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>"; 
					 trHtml+="<td>"+item.seq_no+"</td>"; 
					 trHtml+="<td>"+item.plan_name+"</td>"; 
					 trHtml+="<td>"+item.cert_no+"</td>"; 
					 trHtml+="<td>"+item.naturs_name+"</td>"; 
					 trHtml+="<td>"+item.outer_measure_engr+"</td>"; 
					 trHtml+="<td>"+item.inner_measure_emp+"</td>"; 
					 trHtml+="<td>"+item.plan_measure_date+"</td>"; 
					 trHtml+="<td>"+item.fact_measure_date+"</td>"; 
					 trHtml+="<td>"+item.pre_next_date+"</td>"; 
					 trHtml+="<td>"+item.measure_hours+"</td>"; 
					 trHtml+="<td>"+item.other_money+"</td>"; 
					 trHtml+="<td>"+item.measure_money+"</td>"; 
					 trHtml+="<td>"+item.measure_memo+"</td>"; 
					 trHtml += "<td>" + (item.measure_kind == 0 ? '机器检测' : '手工检测') + "</td>";
					 trHtml+="<td>"+item.inner_measure_dept_id+"</td>"; 
					 trHtml+="<td>"+item.inner_measure_dept_no+"</td>"; 
					 trHtml+="<td>"+item.outer_measure_org+"</td>"; 
					 trHtml+="<td>"+item.create_emp+"</td>"; 
					 trHtml+="<td>"+item.create_date+"</td>"; 
					 trHtml+="<td>"+item.audit_emp+"</td>"; 
					 trHtml+="<td>"+item.audit_date+"</td>"; 
					 trHtml+="<td>"+item.deal_emp+"</td>"; 
					 trHtml += "<td>" + (item.measure_type == 0 ? '一级级别' : '二级级别') + "</td>";
					 trHtml += "<td>" + (item.state == 0 ? '新建' : '审核') + "</td>";
					 trHtml += "<td>" + (item.measure_result == 0 ? '完成' : '未完成') + "</td>";
					 trHtml+="<td>"+item.measure_idea+"</td>"; 
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			lodopExportExcel("resultPrint","导出Excel","检测计量记录.xls",true);
	    },true,manager);
		return;
	 }		 
	 
	 
	 
	    function loadDict(){
	    	
			var param = {
	            	query_key:''
	        };
			
            //字典下拉框
        //内部计量部门
        autocomplete("#inner_measure_dept_id", "../queryDeptDict.do?isCheck=false", "id","text", true, true,param,true);
        	
     	//资产性质
      	//autocomplete("#ass_nature", "../queryAssNaturs.do?isCheck=false", "id","text", true, true);
     	
        //计量计划ID
      	autocomplete("#plan_id", "../queryAssMeasurePlanDict.do?isCheck=false", "id","text", true, true,param,true);
     	
    	//状态
    	/* $("#state").ligerComboBox({
    		
    		width : 160
    		
    	}); */
    	 $('#state').ligerComboBox({
 			data:[{id:1,text:'审核'},{id:0,text:'新建'}],
 			valueField: 'id',
             textField: 'text',
 			cancelable:true,
 			width:160
 		});
    	
    	//计量类别
    	/* $("#measure_type").ligerComboBox({
    		
    		width : 160
    		
    	}); */
 		
    	//检测方式
    	/* $("#measure_kind").ligerComboBox({
    		
    		width : 160
    		
    	}); */
    	 $('#measure_kind').ligerComboBox({
 			data:[{id:1,text:'手工检测'},{id:0,text:'机器检测'}],
 			valueField: 'id',
             textField: 'text',
 			cancelable:true,
 			width:160
 		});
 		
    	//检测结果
    	/* $("#measure_result").ligerComboBox({
    		
    		width : 160
    		
    	}); */
    	 $('#measure_result').ligerComboBox({
 			data:[{id:1,text:'未完成'},{id:0,text:'完成'}],
 			valueField: 'id',
             textField: 'text',
 			cancelable:true,
 			width:160
 		})
 		
 		$("#ass_table").ligerTextBox({width:160});
	    }
	    
	    
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
        <tr>  
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">计划：</td>
            <td align="left" class="l-table-edit-td"><input name="plan_id" type="text" id="plan_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>  
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">检测方式：</td>
            <td align="left" class="l-table-edit-td">
            			<!-- <select id="measure_kind" name="measure_kind"> 
			            			<option value="">请选择</option>
			                		<option value="0">机器检测</option>
			                		<option value="1">手工检测</option>
			                	</select> -->
	                	 <input name="measure_kind"  type="text" id="measure_kind" />  
            </td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">内部计量部门：</td>
            <td align="left" class="l-table-edit-td"><input name="inner_measure_dept_id" type="text" id="inner_measure_dept_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>  
        </tr> 
        <tr>
             
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">状态：</td>
            <td align="left" class="l-table-edit-td">
            		<!-- <select id="state" name="state"> 
			            			<option value="">请选择</option>
			                		<option value="0">新建</option>
			                		<option value="1">审核</option>
			                	</select> -->
	                	 <input name="state"  type="text" id="state" />  
            </td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">计量结果：</td>
            <td align="left" class="l-table-edit-td">
            			<!-- <select id="measure_result" name="measure_result"> 
			            			<option value="">请选择</option>
			                		<option value="0">完成</option>
			                		<option value="1">未完成</option>
			                	</select> -->
			           <input name="measure_result"  type="text" id="measure_result" />     	
            </td>
            <td align="left"></td> 
        </tr> 
        <tr>
        	<td align="right" class="l-table-edit-td"  >资产信息：</td>
            <td align="left" class="l-table-edit-td" colspan="3">
           		<input name="ass_table" type="text" id="ass_table"  />
            </td>
        </tr>
    </table>
	<div id="maingrid"></div>
</body>
</html>
