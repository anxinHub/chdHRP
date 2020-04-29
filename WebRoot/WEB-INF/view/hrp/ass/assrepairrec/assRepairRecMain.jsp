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
    	  
    	  grid.options.parms.push({name:'fixed_dept_id',value:liger.get("fixed_dept_id").getValue().split("@")[0]}); 
    	  
    	 // grid.options.parms.push({name:'fixed_dept_no',value:$("#fixed_dept_no").val()});  
    	  
    	  grid.options.parms.push({name:'is_inner',value:liger.get("is_inner").getValue()}); 
    	  
    	  grid.options.parms.push({name:'trouble_level',value:liger.get("trouble_level").getValue()}); 
    	  
    	  grid.options.parms.push({name:'repair_level',value:liger.get("repair_level").getValue()}); 
    	  
      	  grid.options.parms.push({name:'state',value:liger.get("state").getValue()});  
    	  
    	  grid.options.parms.push({name:'create_emp',value:liger.get("create_emp").getValue()}); 
    	  
    	  grid.options.parms.push({name:'create_date_begin',value:$("#create_date_begin").val()}); 
    	  
    	  grid.options.parms.push({name:'create_date_end',value:$("#create_date_end").val()}); 
    	  
    	  grid.options.parms.push({name:'audit_emp',value:liger.get("audit_emp").getValue()});
    	  
    	  
    	  
    	//加载查询条件
    	grid.loadData(grid.where);
    	
		$("#resultPrint > table > tbody").empty();
		
     }
    

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [  
                     { display: '维修记录号', name: 'repair_rec_no', align: 'left',width:'150',frozen: true,
                    	 
                    	 render : function(rowdata, rowindex,value) {
                    		 
   							return "<a href=javascript:openUpdate('"+rowdata.group_id   + "|" + 
																	rowdata.hos_id   + "|" + 
																	rowdata.copy_code   + "|" + 
																	rowdata.repair_rec_no  +"')>"+
   									                                 rowdata.repair_rec_no+"</a>";
   										}
					 		},
                     { display: '维修部门', name: 'dept_name', align: 'left',width:'100',
					 			 
					 		},  
                     { display: '资产卡片号', name: 'ass_card_no', align: 'left',width:'150'
					 		},
                     { display: '申请单号', name: 'apply_no', align: 'left',width:'100'
					 		},
                     { display: '是否内部维修', name: 'is_inner', align: 'left',width:'100',
					 			
					 		render : function(rowdata, rowindex, value) {
					 			
								if (rowdata.is_inner == 0) {
									
										return "否";
										
									} else {
										
										return "是";
										
									}
								}
					 		},
                     { display: '故障级别', name: 'trouble_level', align: 'left',width:'100',
					 			
					 			render : function(rowdata, rowindex, value) {
					 				
									if (rowdata.trouble_level == 0) {
										
											return "一般";
											
										} else if (rowdata.trouble_level == 1) {
											
											return "严重";
											
										}
									}
					 		},
                     { display: '维修级别', name: 'repair_level', align: 'left',width:'100',
					 			
					 			render : function(rowdata, rowindex, value) {
					 				
									if (rowdata.repair_level == 0) {
										
											return "小修";
											
										} else if (rowdata.repair_level == 1){
											
											return "中修";
											
										} else if (rowdata.repair_level == 2){
											
											return "大修";
											
										}
									}
					 		},
                     { display: '状态', name: 'state_name', align: 'left',width:'100'
					 		},
                     { display: '维修日期', name: 'repair_date', align: 'left',width:'100'
					 		},
                     { display: '维修工程师', name: 'repair_engr', align: 'left',width:'100'
					 		},
                     { display: '维修工时', name: 'repair_hours', align: 'left',width:'100'
					 		},
                     { display: '是否合同', name: 'is_contract', align: 'left',width:'100',
					 			
					 			render : function(rowdata, rowindex, value) {
					 				
									if (rowdata.is_contract == 0) {
										
											return "否";
											
										} else {
											
											return "是";
											
										}
									}
					 		},
                     { display: '合同号', name: 'contract_no', align: 'left',width:'100'
					 		},
                     { display: '维修费用', name: 'repair_money', align: 'right',width:'200',
					 			render : function(rowdata, rowindex,
										value) {
									 return formatNumber(rowdata.repair_money,'${ass_05005}',1);
								}
					 		},
                     { display: '其他费用', name: 'other_money', align: 'left',width:'100',
					 			render : function(rowdata, rowindex,
										value) {
									 return formatNumber(rowdata.other_money,'${ass_05005}',1);
								}
					 		},

                     { display: '制单人', name: 'create_emp', align: 'left',width:'100',
					 			
					 			render : function(rowdata, rowindex, value) {
					 				
									return rowdata.create_name;
									
									}
					 		
					 		},
                     { display: '制单日期', name: 'create_date', align: 'left',width:'100'
					 		},
                     { display: '审核人', name: 'audit_emp', align: 'left',width:'100',
					 			
					 			render : function(rowdata, rowindex, value) {
					 				
									return rowdata.audit_name;
									
									}
					 		
					 		},
                     { display: '检修说明', name: 'repair_desc', align: 'left',width:'100'
					 		}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAssRepairRec.do',
                      width:'100%',height: '100%', checkbox: true,rownumbers:true,delayLoad :true,
                     isScroll  : true,
                     selectRowButtonOnly:true,
                     toolbar: { items: [
                     	{ text: '查询（<u>E</u>）', id:'search', click: query,icon:'search' },
                     	{ line:true },
				    	                { text: '删除（<u>D</u>）', id:'delete', click: remove,icon:'delete' }
				                     	, {
											line : true
										},{
											text : '批量打印（<u>P</u>）',
											id : 'print',
											click : printDate,
											icon : 'print'
										},{ 
											line:true 
											},
										{
												text: '模板设置',
												id:'printSet', 
												click: printSet, 
												icon:'print' }	
                     	
                     					/* ,
									    { line:true }, 
										{ text: '审核（<u>R</u>）', id:'audit', click: auditAssRepairRec, icon:'right' },
					    	            { line:true },
					    	            { text: '消审（<u>B</u>）', id:'back', click: backAssRepairRec,icon:'back' },
					    	            { line:true },
					    	            { text: '维修完成确认', id:'count', click: countAssRepairRec,icon:'right' } */
						                
				    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code   + "|" + 
								rowdata.repair_rec_no 
							);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
  //打印回调方法
    function lodopPrint(){
    	var head="<table class='head' width='100%'><tr><td>单位：${hos_name}</td></tr>";
 		head=head+"<tr><td>制单日期："+$("#create_date_start").val() +" 至  "+ $("#create_date_end").val()+"</td></tr>";
 		head=head+"</table>";
 		grid.options.lodop.head=head; 
 		grid.options.lodop.fn=renderFunc;
 		grid.options.lodop.title="资产维修记录";
    }
	
	 //打印模板设置
	  
	  function printSet(){
		  
		  var useId=0;//统一打印
			if('${ass_05080}'==1){
				//按用户打印
				useId='${user_id }';
			}
			
	//POBrowser.openWindow('../../../../PageOffice/printFormSet.do?isCheck=false&template_code=01001&userid=${user_id}', 'fullscreen=yes');
	officeFormTemplate({template_code:"05080",use_id:useId});
	}
	
	//打印
	    function printDate(){
		    	
		    	 var useId=0;//统一打印
		 		if('${ass_05080}'==1){
		 			//按用户打印
		 			useId='${user_id }';
		 		}
		 		var data = gridManager.getCheckedRows();
				if (data.length == 0){
					$.ligerDialog.error('请选择行');
				}else{
					
					var repair_rec_no ="" ;
					/* var apply_nos = ""; */
					$(data).each(function (){		
						/* if(this.state != 2){
							copy_code = apply_nos + this.apply_no + "<br>";
						}
						 */
						 repair_rec_no  += "'"+this.repair_rec_no+"',"
						 
							
					});
				}
		    	var para={
		    		
		       
		    			template_code:'05080',
		    			class_name:"com.chd.hrp.ass.serviceImpl.repair.AssRepairRecServiceImpl",
		    			method_name:"queryAssRepairRecDY",
						
		    			paraId :repair_rec_no.substring(0,repair_rec_no.length-1),
		    			isPrintCount:false,//更新打印次数
		    			isPreview:true,//预览窗口，传绝对路径
		    			use_id:useId,
		    			p_num:1
		    	};
		    	officeFormPrint(para);
	}
    function add_open(){
    	
    	var index = layer.open({
					type : 2,
					title : '资产维修记录',
					shadeClose : false,
					shade : false,
					maxmin : true, //开启最大化最小化按钮
					area : [ '893px', '500px' ],
					content : 'assRepairRecAddPage.do?isCheck=false'
				});
				layer.full(index);
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
									this.repair_rec_no +"@"+
									this.state
							) });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteAssRepairRec.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
    	}
    
    function auditAssRepairRec(){
 	   
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
 				
 					this.repair_rec_no    +"@"+ 
 					
					this.state  
 				
 				); });
            $.ligerDialog.confirm('确定审核?', function (yes){
            	if(yes){
                	ajaxJsonObjectByUrl("auditAssRepairRec.do",{ParamVo : ParamVo.toString()},function (responseData){
                		if(responseData.state=="true"){
                			query();
                		}
                	});
            	}
            });
     	   
        }
 	   
    }
    function countAssRepairRec(){
 	   
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
 				
 					this.repair_rec_no    
 				
 				); });
            $.ligerDialog.confirm('确定审核?', function (yes){
            	if(yes){
                	ajaxJsonObjectByUrl("countAssRepairRec.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
                		if(responseData.state=="true"){
                			query();
                		}
                	});
            	}
            });
     	   
        }
 	   
    }
    
    function backAssRepairRec (){
 	   
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
 				
 					this.repair_rec_no     +"@"+ 
				
					this.state   
 				
 				); });
            $.ligerDialog.confirm('确定销审?', function (yes){
            	if(yes){
                	ajaxJsonObjectByUrl("backAssRepairRec.do",{ParamVo : ParamVo.toString()},function (responseData){
                		if(responseData.state=="true"){
                			query();
                		}
                	});
            	}
            });
     	   
        }
 	   
    }
    
    
    //根据合同生成 
    function contractAssRepairRec (){
    	
    	
    	
    	
    }
    
    //根据申请单号生成
    function applyNoAssRepairRec (){
    	
    	$.ligerDialog.open({
			url : 'openAssRepairApplyPage.do?isCheck=false',
			height : 400,
			width : 700,
			title : '根据申请单号生成申请记录',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : true,
			isResize : true,
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.applyNoAssRepairRec();
				},
				cls : 'l-dialog-btn-highlight'
			}, {
				text : '取消',
				onclick : function(item, dialog) {
					dialog.close();
				}
			} ]
		});
 	 
    	
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
				"repair_rec_no="+vo[3]   
		parent.$.ligerDialog.open({
			title: '维修记录修改',
			height: $(window).height(),
			width: $(window).width(),
			 url: 'hrp/ass/assrepairrec/assRepairRecUpdatePage.do?isCheck=false&' + parm,
			 /* width: 1000,
			 height: 700,
			 allowClose: true,
			 slide: false */
			 modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
				parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		 })
/*     var index = layer.open({
					type : 2,
					title : '维修记录明细',
					shadeClose : false,
					shade : false,
					maxmin : true, //开启最大化最小化按钮
					area : [ '600px', '500px' ],
					content : 'assRepairRecUpdatePage.do?isCheck=false&' + parm
				}); */
				//layer.full(index);	
    
    }
    
    //键盘事件
	  function loadHotkeys() {

		hotkeys('E', query);

		hotkeys('A', add_open);
		
		hotkeys('D', remove);
		
		hotkeys('R', auditAssRepairRec);
		
		hotkeys('B', backAssRepairRec);
		
		hotkeys('E', exportExcel);

		hotkeys('P', printDate);
		 
		

	 }
//打印数据
// 	 function printDate(){
// 		//有数据直接打印
// 		if($("#resultPrint > table > tbody").html()!=""){
// 			lodopPrinterTable("resultPrint","开始打印","资产维修记录",true);
// 			return;
// 		}
		
// 		//重新查询数据，避免分页导致打印数据不全
// 		var manager = $.ligerDialog.waitting('系统正在准备打印数据,请稍候...');

// 		var printPara={
				
// 			usePager:false,
			 
//          fixed_dept_id:liger.get("fixed_dept_id").getValue().split("@")[0],
       
//          is_inner:liger.get("is_inner").getValue(),
         
//          trouble_level:liger.get("is_inner").getValue(),
         
//          repair_level:liger.get("is_inner").getValue(),
         
//          state:liger.get("state").getValue(),
         
//          create_emp:liger.get("create_emp").getValue(),
         
//          create_date_begin:$("#create_date_begin").val(),
         
//          create_date_end:$("#create_date_end").val(),
         
//          audit_emp:liger.get("audit_emp").getValue(),
         
//        };
		
// 		ajaxJsonObjectByUrl("queryAssRepairRec.do",printPara,function (responseData){
			
// 			$.each(responseData.Rows,function(idx,item){ 
				
// 				 var trHtml="<tr>";
					 
// 					 trHtml+="<td>"+item.ass_year+"</td>"; 
					 
// 					 trHtml+="<td>"+item.ass_month+"</td>"; 
					 
// 					 trHtml+="<td>"+item.repair_rec_no+"</td>"; 
					 
// 					 trHtml+="<td>"+item.fixed_dept_name+"</td>";  
					 
// 					 trHtml+="<td>"+item.ass_card_no+"</td>"; 
					 
// 					 trHtml+="<td>"+item.apply_no+"</td>"; 
					 
// 					 if (item.is_inner == 0  ) {
						 
// 						 trHtml+="<td>否</td>"; 
						 
// 					 }else if (item.is_inner == 1  ) {
						 
// 						 trHtml+="<td>是</td>"; 
						 
// 					 }
					 
					 
// 					 if (item.trouble_level == 0 ) {
						 
// 						 trHtml+="<td>一级故障</td>"; 
						 
// 					 }else  if (item.trouble_level == 1 ) {
						 
// 						 trHtml+="<td>二级故障</td>"; 
						 
// 					 } else  if (item.trouble_level == 2 ) {
						 
// 						 trHtml+="<td>三级故障</td>"; 
						 
// 					 } else  if (item.trouble_level == 3 ) {
						 
// 						 trHtml+="<td>四级故障</td>"; 
						 
// 					 }
					 
// 					 if (item.repair_level == 0 ) {
						 
// 						 trHtml+="<td>一级维修</td>"; 
						 
// 					 }else if (item.repair_level == 1 ) {
						 
// 						 trHtml+="<td>二级维修</td>"; 
						 
// 					 } else if (item.repair_level == 2 ) {
						 
// 						 trHtml+="<td>三级维修</td>"; 
						 
// 					 } else if (item.repair_level == 3 ) {
						 
// 						 trHtml+="<td>四级维修</td>"; 
						 
// 					 }
// 					 if (item.state == 0 ) {
						 
// 						 trHtml+="<td>新建</td>"; 
						 
// 					 }else if (item.state == 1 ) {
						 
// 						 trHtml+="<td>审核</td>"; 
						 
// 					 }
					
					 
// 					 trHtml+="<td>"+item.repair_date+"</td>"; 
					 
// 					 trHtml+="<td>"+item.receive_date+"</td>"; 
					 
// 					 trHtml+="<td>"+item.repair_engr+"</td>"; 
					 
// 					 trHtml+="<td>"+item.repair_hours+"</td>"; 
					 
// 					 if (item.is_contract == 0) {
						 
// 						 trHtml+="<td>否</td>"; 
// 					 }else if (item.is_contract == 0) {
						 
// 						 trHtml+="<td>是</td>"; 
// 					 }
					
					 
// 					 trHtml+="<td>"+item.contract_no+"</td>"; 
					 
// 					 trHtml+="<td>"+item.repair_money+"</td>"; 
					 
// 					 trHtml+="<td>"+item.other_money+"</td>"; 
					 
// 					 trHtml+="<td>"+item.create_emp_name+"</td>"; 
					 
// 					 trHtml+="<td>"+item.create_date+"</td>"; 
					 
// 					 trHtml+="<td>"+item.audit_emp_name+"</td>"; 
					 
// 					 trHtml+="<td>"+item.repair_desc+"</td>"; 
					 
// 				 trHtml+="</tr>";
				
// 				$("#resultPrint > table > tbody").append(trHtml);
				
// 			});
// 			manager.close();
			
// 			//alert($("#resultPrint").html())
			
// 			lodopPrinterTable("resultPrint","开始打印","资产维修记录",true);
			
// 	    },true,manager);
		
// 		return;
		
// 	 }
	/*    function printDate(){
	    	
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
	       			title:'维修记录',
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
	   		ajaxJsonObjectByUrl("queryAssRepairRec.do?isCheck=false", selPara, function (responseData) {
	   			printGridView(responseData,printPara);
			});

	   		
	    } */
	 
	 //导出数据
	 function exportExcel(){
		//有数据直接导出
		
		if($("#resultPrint > table > tbody").html()!=""){
			
			lodopExportExcel("resultPrint","导出Excel","资产维修记录.xls",true);
			
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');

		var exportPara={
				
			usePager:false,
			
	         fixed_dept_id:liger.get("fixed_dept_id").getValue().split("@")[0],
	         
	         is_inner:liger.get("is_inner").getValue(),
	         
	         trouble_level:liger.get("is_inner").getValue(),
	         
	         repair_level:liger.get("is_inner").getValue(),
	         
	         state:liger.get("state").getValue(),
	         
	         create_emp:liger.get("create_emp").getValue(),
	         
	         create_date_begin:$("#create_date_begin").val(),
	         
	         create_date_end:$("#create_date_end").val(),
	         
	         audit_emp:liger.get("audit_emp").getValue(),
         
       };
		
		ajaxJsonObjectByUrl("queryAssRepairRec.do",exportPara,function (responseData){
			
			$.each(responseData.Rows,function(idx,item){ 
				
				 var trHtml="<tr>";
				  
					 trHtml+="<td>"+item.ass_year+"</td>"; 
					 
					 trHtml+="<td>"+item.ass_month+"</td>"; 
					 
					 trHtml+="<td>"+item.repair_rec_no+"</td>"; 
					 
					 trHtml+="<td>"+item.fixed_dept_name+"</td>";  
					 
					 trHtml+="<td>"+item.ass_card_no+"</td>"; 
					 
					 trHtml+="<td>"+item.apply_no+"</td>"; 
					 
					 if (item.is_inner == 0  ) {
						 
						 trHtml+="<td>否</td>"; 
						 
					 }else if (item.is_inner == 1  ) {
						 
						 trHtml+="<td>是</td>"; 
						 
					 }
					 
					 
					 if (item.trouble_level == 0 ) {
						 
						 trHtml+="<td>一级故障</td>"; 
						 
					 }else  if (item.trouble_level == 1 ) {
						 
						 trHtml+="<td>二级故障</td>"; 
						 
					 } else  if (item.trouble_level == 2 ) {
						 
						 trHtml+="<td>三级故障</td>"; 
						 
					 } else  if (item.trouble_level == 3 ) {
						 
						 trHtml+="<td>四级故障</td>"; 
						 
					 }
					 
					 if (item.repair_level == 0 ) {
						 
						 trHtml+="<td>一级维修</td>"; 
						 
					 }else if (item.repair_level == 1 ) {
						 
						 trHtml+="<td>二级维修</td>"; 
						 
					 } else if (item.repair_level == 2 ) {
						 
						 trHtml+="<td>三级维修</td>"; 
						 
					 } else if (item.repair_level == 3 ) {
						 
						 trHtml+="<td>四级维修</td>"; 
						 
					 }
					 if (item.state == 0 ) {
						 
						 trHtml+="<td>新建</td>"; 
						 
					 }else if (item.state == 1 ) {
						 
						 trHtml+="<td>审核</td>"; 
						 
					 }
					
					 
					 trHtml+="<td>"+item.repair_date+"</td>"; 
					 
					 trHtml+="<td>"+item.receive_date+"</td>"; 
					 
					 trHtml+="<td>"+item.repair_engr+"</td>"; 
					 
					 trHtml+="<td>"+item.repair_hours+"</td>"; 
					 
					 if (item.is_contract == 0) {
						 
						 trHtml+="<td>否</td>"; 
					 }else if (item.is_contract == 0) {
						 
						 trHtml+="<td>是</td>"; 
					 }
					
					 
					 trHtml+="<td>"+item.contract_no+"</td>"; 
					 
					 trHtml+="<td>"+item.repair_money+"</td>"; 
					 
					 trHtml+="<td>"+item.other_money+"</td>"; 
					 
					 trHtml+="<td>"+item.create_emp_name+"</td>"; 
					 
					 trHtml+="<td>"+item.create_date+"</td>"; 
					 
					 trHtml+="<td>"+item.audit_emp_name+"</td>"; 
					 
					 trHtml+="<td>"+item.repair_desc+"</td>"; 
					 
				 trHtml+="</tr>";
				 
				 $("#resultPrint > table > tbody").append(trHtml);
				 
			});
			
			manager.close();
			
			lodopExportExcel("resultPrint","导出Excel","资产维修记录.xls",true);
			
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
        //审核人
    	autocomplete("#audit_emp","../../../hrp/sys/queryUserDict.do?isCheck=false", "id","text", true, true,param,true);
    	//制单人
    	autocomplete("#create_emp","../../../hrp/sys/queryUserDict.do?isCheck=false", "id","text", true, true,param,true);
    	//维修部门
    	autocomplete("#fixed_dept_id", "../queryDeptDict.do?isCheck=false", "id","text", true, true,param,true,null,"220");
    	//资产卡片号
    	autocomplete("#ass_card_no", "../queryAssCardNoDict.do?isCheck=false", "id","text", true, true,param,true);
    	//申请单号
    	autocomplete("#apply_no", "../queryAssApplyNoDict.do?isCheck=false", "id","text", true, true);
    	//是否内部维修
    	/* $("#is_inner").ligerComboBox({
			width : 160
		}); */
		$('#is_inner').ligerComboBox({
			data:[{id:0,text:'否'},{id:1,text:'是'}],
			valueField: 'id',
	        textField: 'text',
			cancelable:true,
			width:160
		});
    	//故障级别
    	/* $("#trouble_level").ligerComboBox({
			width : 160
		}); */
		$('#trouble_level').ligerComboBox({
			data:[{id:0,text:'一般'},{id:1,text:'严重'}],
			valueField: 'id',
	        textField: 'text',
			cancelable:true,
			width:160
		});
    	//维修级别
    	/* $("#repair_level").ligerComboBox({
			width : 160
		}); */
		$('#repair_level').ligerComboBox({
			data:[{id:0,text:'小修'},{id:1,text:'中修'},{id:2,text:'大修'}],
			valueField: 'id',
	        textField: 'text',
			cancelable:true,
			width:160
		});
    	//是否合同
    	$("#is_contract").ligerComboBox({
			width : 160
		});
    	//状态
    	/* $("#state").ligerComboBox({
			width : 160
		}); */
		 $('#state').ligerComboBox({
			data:[{id:5,text:'待维修'},{id:6,text:'维修中'},{id:7,text:'维修完成'}],
			valueField: 'id',
	        textField: 'text',
			cancelable:true,
			width:160
		}); 
		
        $("#create_date_begin").ligerTextBox({width:90});
        
        $("#create_date_end").ligerTextBox({width:90});
          
        autodate("#create_date_begin","YYYY-mm-dd","month_first");

		autodate("#create_date_end","YYYY-mm-dd","month_last");
		
		
         }  
    	  
    </script>

</head>

<body style="overflow: auto;" >
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
	
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        
         <tr>
            <td align="right" class="l-table-edit-td"   >申请日期：</td>
            <td align="left" class="l-table-edit-td"><input  name="create_date_begin" type="text" id="create_date_begin"
				 class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" />
			</td>
			<td align="left" >至：</td>
			<td align="left" ><input name="create_date_end" type="text" id="create_date_end" 
				 class="Wdate"
				onFocus="WdatePicker({minDate:'#F{$dp.$D(\'create_date_begin\');}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" />
			</td>
       	    <td align="right" class="l-table-edit-td"  >制单人：</td>
            <td align="left" class="l-table-edit-td"><input name="create_emp" type="text" id="create_emp"  /></td> 
             
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">审核人：</td>
            <td align="left" class="l-table-edit-td"><input name="audit_emp" type="text" id="audit_emp"  /></td> 
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">维修级别：</td>
            <td align="left" class="l-table-edit-td" > 
            	<!-- <select id="repair_level" name="repair_level"> 
            			<option value="">请选择</option>
                		<option value="0">一级维修</option>
                		<option value="1">二级维修</option>
                		<option value="2">三级维修</option>
                		<option value="3">四级维修</option>
                	</select> -->
                	<input name="repair_level" type="text" id="repair_level"/>
            </td>
        </tr> 
        <tr>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">维修部门：</td>
            <td align="left" class="l-table-edit-td" colspan="3">
            	<input name="fixed_dept_id" type="text" id="fixed_dept_id"  />
            </td> 
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否内部维修：</td>
            <td align="left" class="l-table-edit-td"> 
            	<!-- <select id="is_inner" name="is_inner"> 
            			<option value="">请选择</option>
                		<option value="0">否</option>
                		<option value="1">是</option>
                	</select> -->
                	<input name="is_inner" type="text" id="is_inner"/>
            </td>
           
           <td align="right" class="l-table-edit-td"  style="padding-left:20px;">故障级别：</td>
            <td align="left" class="l-table-edit-td"> 
            	<!-- <select id="trouble_level" name="trouble_level"> 
            			<option value="">请选择</option>
                		<option value="0">一级故障</option>
                		<option value="1">二级故障</option>
                		<option value="2">三级故障</option>
                		<option value="3">四级故障</option>
                	</select> -->
                	<input name="trouble_level" type="text" id="trouble_level"/>
            </td> 
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">状态：</td>
            <td align="left" class="l-table-edit-td"> 
            	<!-- <select id="state" name="state"> 
            			<option value="">请选择</option>
                		<option value="0">0 新建</option>
                		<option value="1">1 审核</option>
                	</select> -->
                <input name="state" type="text" id="state"/>	
            </td>
            <td align="left"></td>
        </tr>  
    </table>
    
	<div id="maingrid"  ></div>
	<div id="resultPrint" style="display:none">
	   	<table width="100%">
			<thead>
		 
			<tr> 
                <th width="200">统计年度</th>	
                <th width="200">统计月份</th>	
                <th width="200">维修记录号</th>	
                <th width="200">维修部门</th>	 
                <th width="200">资产卡片号</th>	
                <th width="200">申请单号</th>	
                <th width="200">是否内部维修</th>	
                <th width="200">故障级别</th>	
                <th width="200">维修级别</th>	
                <th width="200">状态</th>
                <th width="200">维修日期</th>	
                <th width="200">取件日期</th>	
                <th width="200">维修工程师</th>	
                <th width="200">维修工时</th>	
                <th width="200">是否合同</th>	
                <th width="200">合同号</th>	
                <th width="200">维修费用</th>	
                <th width="200">其他费用</th>	
                <th width="200">制单人</th>	
                <th width="200">制单日期</th>	
                <th width="200">审核人</th>	
                <th width="200">检修说明</th>	
			</tr>
			   	</thead>
			   	<tbody></tbody>
	   	</table>
   	</div>
</body>
</html>
