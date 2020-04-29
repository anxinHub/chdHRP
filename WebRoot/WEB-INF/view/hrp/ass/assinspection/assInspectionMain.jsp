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
<!-- 资产巡检记录  页面 -->
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    $(function ()
    {
        loadDict();//加载下拉框
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
    	  grid.options.parms.push({name:'ins_name',value:$("#ins_name").val()});  
    	  grid.options.parms.push({name:'ass_table',value:$("#ass_table").val()}); 
          grid.options.parms.push({name:'dept_id',value:liger.get("dept_id").getValue().split("@")[0]}); 
    	  //grid.options.parms.push({name:'dept_no',value:$("#dept_no").val()}); 
    	  grid.options.parms.push({name:'create_emp',value:liger.get("create_emp").getValue()}); 
    	  grid.options.parms.push({name:'create_date_begin',value:$("#create_date_begin").val()}); 
    	  grid.options.parms.push({name:'create_date_end',value:$("#create_date_end").val()});
    	  grid.options.parms.push({name:'audit_emp',value:liger.get("audit_emp").getValue()}); 
    	  grid.options.parms.push({name:'audit_date_begin',value:$("#audit_date_begin").val()}); 
    	  grid.options.parms.push({name:'audit_date_end',value:$("#audit_date_end").val()}); 
    	  grid.options.parms.push({name:'state',value:liger.get("state").getValue()});  

    	//加载查询条件
    	grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
     }
    

    function loadHead(){
    	if(show_detail == "1"){	
	    	grid = $("#maingrid").ligerGrid({
	           columns: [  
	                     { display: '巡检编号', name: 'ins_no', align: 'left',frozen: true,minWidth: 100,
	                    	 
	                    	 render : function(rowdata, rowindex,value) {
	                    		 
	 							return "<a href=javascript:openUpdate('"+rowdata.group_id   + "|" + 
																		rowdata.hos_id   + "|" + 
																		rowdata.copy_code   + "|" + 
																		rowdata.ins_id   +"')>"+
	 									                                 rowdata.ins_no+"</a>";
	 										}
						 		},
	                     { display: '巡检名称', name: 'ins_name', align: 'left',minWidth: 100
						 		},
			            { display: '巡检科室', name: 'dept_name', align: 'left',minWidth: 100
							     },
	                     { display: '资产性质', name: 'naturs_name', align: 'left',minWidth: 100
						 		},
						{ display: '卡片号', name: 'ass_card_no',minWidth: 100, align: 'left', },		
				 		{ display: '资产编码', name: 'ass_code',minWidth: 100, align: 'left', },
                        { display: '资产名称', name: 'ass_name', minWidth: 100,align: 'left', },
                        { display: '型号', name: 'ass_mondl',minWidth: 100, align: 'left',  },
                        { display: '序列号', name: 'ass_seq_no',minWidth: 100, align: 'left',  },
                        { display: '规格', name: 'ass_spec', minWidth: 100,align: 'left', },
                        { display: '品牌', name: 'ass_brand',minWidth: 100, align: 'left', },
 					    { display: '生产厂家', name: 'fac_name', minWidth: 100,align: 'left', },
 					    {
									display : '运行状态', 
									name : 'state_go',
									minWidth: 100,
				                    render: function (item)
				                    {
				                        if (parseInt(item.state_go) == 0) {
				                        	return '否';
				                        }else if(parseInt(item.state_go) == 1){
				                        	return '是';
				                        }else{
				                        	item.state_go = 1;
				                        	return 	"是";
				                        }
				                    },
									align : 'left'
								},
								{
									display : '是否报修',
									name : 'is_rep',
									minWidth: 100,
				                    render: function (item)
				                    {
				                        if (parseInt(item.is_rep) == 1) {
				                        	return '是';
				                        }else if(parseInt(item.is_rep) == 0){
				                        	return '否';
				                        }else{
				                        	item.is_rep = 0;
				                        	return "否";
				                        }
				                    },
									align : 'left'
								},
								
								{
									display : '是否保养',
									name : 'is_main',
									minWidth: 100,
				                    render: function (item)
				                    {
				                        if (parseInt(item.is_main) == 1) {
				                        	return '是';
				                        }else if(parseInt(item.is_main) == 0){
				                        	return '否';
				                        }else{
				                        	item.is_main = 0;
				                        	return "否";
				                        }
				                    },
									align : 'left'
								},
								{
									display : '巡检结果',
									name : 'ins_result',
									minWidth: 100,
				                    render: function (item)
				                    {
				                        if (parseInt(item.ins_result) == 1) {
				                        	return '已完成';
				                        }else if(parseInt(item.ins_result) == 0){
				                        	return '未完成';
				                        }else{
				                        	item.ins_result = 1
				                        	return "已完成";
				                        }
				                    },
				                    align : 'left'
								},
	                     { display: '制单人', name: 'create_emp_name', align: 'left',minWidth : 100
						 		},
	                     { display: '制单日期', name: 'create_date', align: 'left',minWidth : 100
						 		},
	                     { display: '审核人', name: 'audit_emp_name', align: 'left',minWidth : 100
						 		},
	                     { display: '审核日期', name: 'audit_date', align: 'left',minWidth : 100
						 		},
	                     { display: '状态', name: 'state', align: 'left',minWidth : 100,
									render : function(rowdata, rowindex,
											value) {
											if(rowdata.state==0){
												return "新建";
											}
											else if(rowdata.state==1){
												return "审核";
											}else if(rowdata.state==2){
												return "终止";
											}
									   }
						 			
						 		},
	                     { display: '备注', name: 'note', align: 'left',minWidth : 100
						 		}
	                     ],
	                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAssInspectionMain.do?isCheck=false&show_detail=1',
	                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
	                     selectRowButtonOnly:true,//heightDiff: -10,
	                     toolbar: { items: [
	                     	{ text: '查询（<u>E</u>）', id:'search', click: query,icon:'search' },
	                     	{ line:true },
					    					{ text: '添加（<u>A</u>）', id:'add', click: add_open, icon:'add' },
					    	                { line:true },
					    	                { text: '删除（<u>D</u>）', id:'delete', click: remove,icon:'delete' },
											{ line:true }, 
											{ text: '审核（<u>R</u>）', id:'audit', click: auditAssInspection, icon:'right' },
							    	        { line:true },
							    	        { text: '消审（<u>B</u>）', id:'back', click: backAssInspection,icon:'back' },
											{ line:true },
											{ text: '终止计划（<u>S</u>）', id:'stop', click: stopAssInspection,icon:'lock' },
											{ line:true },
											{ text : '打印模板设置',id : 'printSet',click : printSet,icon : 'settings'},
											{ line:true }, 
										    { text : '批量打印',id : 'print',click : printDate,icon : 'print'}
	// 				    	                { text: '导出Excel（<u>E</u>）', id:'export', click: exportExcel,icon:'pager' },
					    				]},
	    				onDblClickRow : function (rowdata, rowindex, value)
	    				{
							openUpdate(
									rowdata.group_id   + "|" + 
									rowdata.hos_id   + "|" + 
									rowdata.copy_code   + "|" + 
									rowdata.ins_id   
								);
	    				} 
	                   });
    	} else{
    		grid = $("#maingrid").ligerGrid({
 	           columns: [  
 	                     { display: '巡检编号', name: 'ins_no', align: 'left',frozen: true,
 	                    	 
 	                    	 render : function(rowdata, rowindex,value) {
 	                    		 
 	 							return "<a href=javascript:openUpdate('"+rowdata.group_id   + "|" + 
 																		rowdata.hos_id   + "|" + 
 																		rowdata.copy_code   + "|" + 
 																		rowdata.ins_id   +"')>"+
 	 									                                 rowdata.ins_no+"</a>";
 	 										}
 						 		},
 	                     { display: '巡检名称', name: 'ins_name', align: 'left'
 						 		},
 			            { display: '巡检科室', name: 'dept_name', align: 'left'
 							     },
 	                     { display: '资产性质', name: 'naturs_name', align: 'left'
 						 		},
 	                     { display: '制单人', name: 'create_emp_name', align: 'left'
 						 		},
 	                     { display: '制单日期', name: 'create_date', align: 'left'
 						 		},
 	                     { display: '审核人', name: 'audit_emp_name', align: 'left'
 						 		},
 	                     { display: '审核日期', name: 'audit_date', align: 'left'
 						 		},
 	                     { display: '状态', name: 'state', align: 'left',
 									render : function(rowdata, rowindex,
 											value) {
 											if(rowdata.state==0){
 												return "新建";
 											}
 											else if(rowdata.state==1){
 												return "审核";
 											}else if(rowdata.state==2){
 												return "终止";
 											}
 									   }
 						 			
 						 		},
 	                     { display: '备注', name: 'note', align: 'left'
 						 		}
 	                     ],
 	                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAssInspectionMain.do?isCheck=false&show_detail=0',
 	                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
 	                     selectRowButtonOnly:true,//heightDiff: -10,
 	                     toolbar: { items: [
 	                     	{ text: '查询（<u>E</u>）', id:'search', click: query,icon:'search' },
 	                     	{ line:true },
 					    					{ text: '添加（<u>A</u>）', id:'add', click: add_open, icon:'add' },
 					    	                { line:true },
 					    	                { text: '删除（<u>D</u>）', id:'delete', click: remove,icon:'delete' },
 											{ line:true }, 
 											{ text: '审核（<u>R</u>）', id:'audit', click: auditAssInspection, icon:'right' },
 							    	        { line:true },
 							    	        { text: '消审（<u>B</u>）', id:'back', click: backAssInspection,icon:'back' },
 											{ line:true },
 											{ text: '终止计划（<u>S</u>）', id:'stop', click: stopAssInspection,icon:'lock' },
 											{ line:true },
 											{ text : '打印模板设置',id : 'printSet',click : printSet,icon : 'settings'},
 											{ line:true }, 
 										    { text : '批量打印',id : 'print',click : printDate,icon : 'print'}
 	// 				    	                { text: '导出Excel（<u>E</u>）', id:'export', click: exportExcel,icon:'pager' },
 					    				]},
 	    				onDblClickRow : function (rowdata, rowindex, value)
 	    				{
 							openUpdate(
 									rowdata.group_id   + "|" + 
 									rowdata.hos_id   + "|" + 
 									rowdata.copy_code   + "|" + 
 									rowdata.ins_id   
 								);
 	    				} 
 	                   });
    	}

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function add_open(){
    	

    	parent.$.ligerDialog.open({
			title: '巡检记录添加',
			height: $(window).height(),
			width: $(window).width(),
			url:  'hrp/ass/assinspection/assInspectionAddPage.do?isCheck=false&',
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});	
				layer.full(index);
    	}
    	
    
  //打印模板设置 最新版
    function printSet(){
	  
    	var useId=0;//统一打印
		if('${ass_05012}'==1){
			//按用户打印
			useId='${user_id }';
		}
    	
		officeFormTemplate({template_code:"05102",use_id : useId})
    }
    
  //打印 最新版
    function printDate(){
    	
    	 var useId=0;//统一打印
 		if('${ass_05012}'==1){
 			//按用户打印
 			useId='${user_id }';
 		}

 		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
			
			var ins_id ="" ;
			
			$(data).each(function (){	
				
				ins_id  += "'"+this.ins_id+"',"
					
			});
			
			 var para={
					 
					template_code:'05102',
					class_name:"com.chd.hrp.ass.serviceImpl.inspection.AssInspectionMainServiceImpl", 
					method_name:"queryAssInSpectionMainPrint",
					isSetPrint:false,//是否套打，默认非套打
					isPreview:true,//是否预览，默认直接打印
	    			paraId :ins_id.substring(0,ins_id.length-1) ,
	    			isPrintCount:false,//更新打印次数
	    			use_id:useId,
	    			p_num:1
	    			//isSetPrint:flag
	    	}; 
			ajaxJsonObjectByUrl("queryInSpectionMainState.do?isCheck=false",{paraId:ins_id.substring(0,ins_id.length-1),state:1},function(responseData){
				if (responseData.state == "true") {
					officeFormPrint(para);
				}
			});
		}
    	
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
							this.ins_id +"@"+ 
							this.state 
							) });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteAssInspectionMain.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
    	}
     
    function auditAssInspection(){
    	   
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
    				
    					this.ins_id     +"@"+ 
    				
    					this.state   
    				
    				); });
               $.ligerDialog.confirm('确定审核?', function (yes){
               	if(yes){
                   	ajaxJsonObjectByUrl("auditAssInspection.do",{ParamVo : ParamVo.toString()},function (responseData){
                   		if(responseData.state=="true"){
                   			query();
                   		}
                   	});
               	}
               });
        	   
           }
    	   
       }
       
       function backAssInspection (){
    	   
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
    				
    					this.ins_id     +"@"+ 
    				
    					this.state   
    				
    				); });
               $.ligerDialog.confirm('确定销审?', function (yes){
               	if(yes){
                   	ajaxJsonObjectByUrl("backAssInspection.do",{ParamVo : ParamVo.toString()},function (responseData){
                   		if(responseData.state=="true"){
                   			query();
                   		}
                   	});
               	}
               });
        	   
           }
    	   
       }
       
       
       function stopAssInspection (){
       	
     	   
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
     				
     					this.ins_id     
     				
     				); });
                $.ligerDialog.confirm('确定终止计划?', function (yes){
                	if(yes){
                    	ajaxJsonObjectByUrl("stopAssInspection.do",{ParamVo : ParamVo.toString()},function (responseData){
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
		var parm = "&group_id="+ 
		    vo[0] +"&hos_id="+ 
		    vo[1] +"& copy_code="+ 
		    vo[2] +"&ins_id="+
		    vo[3] ;
		 
		parent.$.ligerDialog.open({
			title: '巡检记录修改',
			height: $(window).height(),
			width: $(window).width(),
			url:  'hrp/ass/assinspection/assInspectionUpdatePage.do?isCheck=false&' + parm,
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});	
				layer.full(index);	
    
    }
    function loadDict(){
    	
		var param = {
            	query_key:''
        };
		
            //字典下拉框
    	autocomplete("#audit_emp", "../../../hrp/sys/queryUserDict.do?isCheck=false", "id", "text", true, true,param,true);
		
		autocomplete("#dept_id", "../queryDeptDict.do?isCheck=false", "id", "text", true, true,param,true);
		
		autocomplete("#create_emp", "../../../hrp/sys/queryUserDict.do?isCheck=false","id", "text",true,true,param,true);
		
		autocomplete("#ass_nature", "../queryAssNaturs.do?isCheck=false","id", "text",true,true,param,true);
		
        $("#ins_name").ligerTextBox({width:160});
        
        $("#create_date_begin").ligerTextBox({width:90});
        
        $("#create_date_end").ligerTextBox({width:90});
        
        $("#audit_date_begin").ligerTextBox({width:90});
        
        $("#audit_date_end").ligerTextBox({width:90});
        
        /* $("#state").ligerComboBox({width:160}); */
        $('#state').ligerComboBox({
			data:[{id:2,text:'确认'},{id:1,text:'审核'},{id:0,text:'新建'}],
			valueField: 'id',
            textField: 'text',
			cancelable:true,
			width:160
		})
		autodate("#create_date_begin","YYYY-mm-dd","month_first");

		autodate("#create_date_end","YYYY-mm-dd","month_last");
		
		$("#ass_table").ligerTextBox({width:160});
	
         }  
    //键盘事件
	  function loadHotkeys() {

		hotkeys('E', query);

		hotkeys('A', add_open);
		
		hotkeys('D', remove);
		
		hotkeys('R', auditAssInspection);
		
		hotkeys('B', backAssInspection);
		
		hotkeys('S', stopAssInspection);
		
		hotkeys('E', exportExcel);

		hotkeys('P', printDate);
		 
	 }
  //打印数据
// 	 function printDate(){
// 		//有数据直接打印
// 		if($("#resultPrint > table > tbody").html()!=""){
// 			lodopPrinterTable("resultPrint","开始打印","资产巡检记录",true);
// 			return;
// 		}
		
// 		//重新查询数据，避免分页导致打印数据不全
// 		var manager = $.ligerDialog.waitting('系统正在准备打印数据,请稍候...');

// 		var printPara={
// 			usePager:false,  
// 			create_emp :liger.get("create_emp").getValue(),
// 	        create_date_begin:$("#create_date_begin").val(),
// 	        create_date_end:$("#create_date_end").val(),
// 	        ins_name:$("#ins_name").val(),
//             dept_id:liger.get("dept_id").getValue().split("@")[0], 
//             state :liger.get("state").getValue(),
//             audit_emp :liger.get("audit_emp").getValue(),
//             audit_date_begin:$("#audit_date_begin").val(),
//             audit_date_end:$("#audit_date_end").val()
            
//          };
// 		ajaxJsonObjectByUrl("queryAssInspectionMain.do",printPara,function (responseData){
// 			$.each(responseData.Rows,function(idx,item){ 
// 				 var trHtml="<tr>"; 
// 					 trHtml+="<td>"+item.ins_no+"</td>"; 
// 					 trHtml+="<td>"+item.ins_name+"</td>"; 
// 					 trHtml+="<td>"+item.ass_year+"</td>"; 
// 					 trHtml+="<td>"+item.ass_month+"</td>"; 
// 					 trHtml+="<td>"+item.naturs_name+"</td>"; 
// 					 trHtml+="<td>"+item.dept_name+"</td>"; 
// 					 trHtml+="<td>"+item.create_emp_name+"</td>"; 
// 					 trHtml+="<td>"+item.create_date+"</td>"; 
// 					 trHtml+="<td>"+item.audit_emp_name  +"</td>"; 
// 					 trHtml+="<td>"+item.audit_date +"</td>"; 
// 					 if (item.state == 0){
						  
// 						 trHtml+="<td>新建</td>";
						 
// 					 }else if (item.state == 1){
						  
// 						 trHtml+="<td>审核</td>";
						 
// 					 }if (item.state == 2){
						  
// 						 trHtml+="<td>终止</td>";
						 
// 					 }
					  
// 					 trHtml+="<td>"+item.note+"</td>"; 
// 				 trHtml+="</tr>"; 
// 				$("#resultPrint > table > tbody").append(trHtml);
// 			});
// 			manager.close();
// 			//alert($("#resultPrint").html())
// 			lodopPrinterTable("resultPrint","开始打印","资产巡检记录",true);
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
	       			title:'巡检记录',
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
	   		ajaxJsonObjectByUrl("queryAssInspectionMain.do?isCheck=false", selPara, function (responseData) {
	   			printGridView(responseData,printPara);
			});

	   		
	    } */
	 
	 //导出数据
	 function exportExcel(){
		//有数据直接导出
		if($("#resultPrint > table > tbody").html()!=""){
			lodopExportExcel("resultPrint","导出Excel","资产巡检记录.xls",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');

		var exportPara={
			usePager:false,
			create_emp :liger.get("create_emp").getValue(),
	        create_date_begin:$("#create_date_begin").val(),
	        create_date_end:$("#create_date_end").val(),
	        ins_name:$("#ins_name").val(),
            dept_id:liger.get("dept_id").getValue().split("@")[0], 
            state :liger.get("state").getValue(),
            audit_emp :liger.get("audit_emp").getValue(),
            audit_date_begin:$("#audit_date_begin").val(),
            audit_date_end:$("#audit_date_end").val()
         };
		ajaxJsonObjectByUrl("queryAssInspectionMain.do",exportPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>"; 
					 trHtml+="<td>"+item.ins_no+"</td>"; 
					 trHtml+="<td>"+item.ins_name+"</td>"; 
					 trHtml+="<td>"+item.ass_year+"</td>"; 
					 trHtml+="<td>"+item.ass_month+"</td>"; 
					 trHtml+="<td>"+item.ass_nature+"</td>"; 
					 trHtml+="<td>"+item.dept_name+"</td>";  
					 trHtml+="<td>"+item.create_emp_name+"</td>"; 
					 trHtml+="<td>"+item.create_date+"</td>"; 
					 trHtml+="<td>"+item.audit_emp_name+"</td>"; 
					 trHtml+="<td>"+item.audit_date+"</td>"; 
					 if (item.state == 0){
						  
						 trHtml+="<td>新建</td>";
						 
					 }else if (item.state == 1){
						  
						 trHtml+="<td>审核</td>";
						 
					 }if (item.state == 2){
						  
						 trHtml+="<td>终止</td>";
						 
					 }
					 trHtml+="<td>"+item.note+"</td>"; 
				 trHtml+="</tr>"; 
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			lodopExportExcel("resultPrint","导出Excel","资产巡检记录.xls",true);
	    },true,manager);
		return;
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
			<td align="left"  >至：</td>
			<td align="left"><input name="create_date_end" type="text"
				id="create_date_end" 
				 class="Wdate"
				onFocus="WdatePicker({minDate:'#F{$dp.$D(\'create_date_begin\');}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">制单人：</td>
            <td align="left" class="l-table-edit-td"><input name="create_emp" type="text" id="create_emp"  /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">巡检名称：</td>
            <td align="left" class="l-table-edit-td"><input name="ins_name" type="text" id="ins_name"  /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">巡检科室：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id"  /></td>
        </tr>
        <tr>
            <td align="right" class="l-table-edit-td" style="padding-left: 20px;">审核日期：</td>
			<td align="left" class="l-table-edit-td"><input name="audit_date_begin" type="text" id="audit_date_begin"
				 class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="left" >至：</td>
			<td align="left"><input name="audit_date_end" type="text" id="audit_date_end" 
				 class="Wdate"
				onFocus="WdatePicker({minDate:'#F{$dp.$D(\'audit_date_begin\');}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">审核人：</td>
            <td align="left" class="l-table-edit-td"><input name="audit_emp" type="text" id="audit_emp"  /></td>
         	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">状态：</td>
            <td align="left" class="l-table-edit-td">
            <!-- <select id="state"  name="state">
						<option value=""></option>
                		<option value="0">新建</option>
                		<option value="1">审核</option>
                		<option value="2">终止</option>
                	</select> -->
                	<input name="state" type="text" id="state" />
                	</td>
            <td align="left"></td>
        </tr>  
        <tr>
        	<td align="right" class="l-table-edit-td"  >资产信息：</td>
            <td align="left" class="l-table-edit-td" colspan="3">
           		<input name="ass_table" type="text" id="ass_table"  />
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
                <th width="200">巡检编号</th>	
                <th width="200">巡检名称</th>	
                <th width="200">统计年度</th>	
                <th width="200">统计月份</th>	
                <th width="200">资产性质</th>	
                <th width="200">科室</th>	
                <th width="200">制单人</th>	
                <th width="200">制单日期</th>	
                <th width="200">审核人</th>	
                <th width="200">审核日期</th>	
                <th width="200">状态</th>	
                <th width="200">备注</th>	
			</tr>
			   	</thead>
			   	<tbody></tbody>
	   	</table>
   	</div>
</body>
</html>
