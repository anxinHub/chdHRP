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
<script src="<%=path%>/lib/hrp/med/med.js"	type="text/javascript"></script>
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
		 
	     $("#toptoolbar2").ligerToolBar( { items: [
				{ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' },
				{ line:true },
				{ text: '添加（<u>A</u>）', id:'add', click: add_open, icon:'add' },
				{ line:true },
				{ text: '删除（<u>D</u>）', id:'delete', click: remove,icon:'delete' },
				{ line:true }, 
// 				{ text: '导出Excel（<u>E</u>）', id:'export', click: exportExcel,icon:'pager' },
// 				{ line:true },
// 				{ text: '打印（<u>P</u>）', id:'print', click: printDate,icon:'print' },
// 				{ line:true },
				/* { text: '下载导入模板（<u>B</u>）', id:'downTemplate', click:downTemplate,icon:'down' },
				{ line:true },
				{ text: '导入（<u>I</u>）', id:'import', click: imp,icon:'up' } */
   				
               ]
	     })
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
          grid.options.parms.push({name:'beginDate',value : $("#beginDate").val()});
    	  grid.options.parms.push({name:'endDate',value : $("#endDate").val()});
    	  grid.options.parms.push({name:'type_id',value : liger.get("type_id").getValue().split(",")[0]}); 
    	  grid.options.parms.push({name:'sup_id',value : liger.get("sup_id").getValue().split(",")[0]});
    	  grid.options.parms.push({name:'protocol_name',value : $("#protocol_name").val()});
    	  grid.options.parms.push({name:'protocol_code',value : $("#protocol_code").val()}); 
    	  grid.options.parms.push({name:'dept_id',value : liger.get("dept_id").getValue().split(",")[0]});
    	  grid.options.parms.push({name:'first_man',value : liger.get("first_man").getValue().split(",")[0]}); 
    	  grid.options.parms.push({name:'second_man',value : $("#second_man").val()}); 
    	  grid.options.parms.push({name:'state',value : $("#state").val()}); 
    	  grid.options.parms.push({name:'original_no',value : $("#original_no").val()}); 
    	  grid.options.parms.push({name:'is_init',value : liger.get("is_init").getValue()}); 
    	//加载查询条件
    	grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '协议编号', name: 'protocol_code', align: 'left',width:120,
                    	 render:function(rowdata,index,value){
                    		 return "<a href=javascript:openUpdate('"+rowdata.group_id+"|"+rowdata.hos_id+"|"+rowdata.copy_code+"|"+rowdata.protocol_id+"|"+rowdata.state+"')>"+rowdata.protocol_code+"</a>";
                    	 }
					 		},
                    
                     { display: '协议名称', name: 'protocol_name', align: 'left',width:160},
                     { display: '签订日期', name: 'sign_date', align: 'left',width:80},
                     { display: '供应商', name: 'sup_name', align: 'left',width:200
					 		},
                     { display: '签订单位', name: 'dept_name', align: 'left',width:100
					 		},
                     { display: '甲方负责人', name: 'emp_name', align: 'left',width:80
					 		},
                     { display: '乙方负责人', name: 'second_man', align: 'left',width:80
					 		},
				 	 { display: '开始日期', name: 'start_date', align: 'left',width:80
					 		},
                     { display: '截止日期', name: 'end_date', align: 'left',width:80
					 		},
                    
                     { display: '状态', name: 'state', align: 'left',width:80,
					 			render:function(rowdata,index,value){
					 				if(rowdata.state == 4){
					 					return "终止";
					 				}else if(rowdata.state == 1){
					 					return "新建";
					 				}else if(rowdata.state == 2){
					 					return "审核";
					 				}else{
					 					return "确认";
					 				}
					 			}
					 		},
					 		{ display: '是否期初', name: 'is_init', align: 'left',width:60,
					 			render:function(rowdata,index,value){
					 				if(rowdata.is_init == '1'){
					 					return "是";
					 				}
									if(rowdata.is_init == '0'){
					 					return "否";
					 				}
					 			}
					 		}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryMedProtocolMain.do?isCheck=false',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,heightDiff: 0,
                     toolbar: { items: [
								{ text: '审核（<u>Z</u>）', id:'audit', click: audit, icon:'bluebook' },
								{ line:true },
								{ text: '消审（<u>U</u>）', id:'unaudit', click: unaudit,icon:'bookpen' },
								{ line:true }, 
								{ text: '确认（<u>M</u>）', id:'confirm', click: confirm,icon:'config' },
								{ line:true },
								{ text: '取消确认（<u>F</u>）', id:'unconfirm', click: unconfirm,icon:'outbox' },
								{ line:true },
								{ text: '终止（<u>O</u>）', id:'cut', click:over,icon:'cut' },
								{ line:true },
								{ text: '取消终止（<u>R</u>）', id:'candle', click: reset,icon:'edit' }
    				]},
    				
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code   + "|" + 
								rowdata.protocol_id + "|" + 
								rowdata.state
							);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function add_open(){
    	$.ligerDialog.open({ url : 'medProtocolMainAddPage.do?isCheck=false',data:{}, 
			height: 600,width: 1100, title:'添加采购协议',modal:true,showToggle:false,showMax:true, 
			showMin: true,isResize:true, top:0
    		}); 
    	}
    	
    function remove(){
    	
    	var data = gridManager.getCheckedRows();
                    if (data.length == 0){
                    	$.ligerDialog.error('请选择行');
                    	return;
                    }else{
                        var ParamVo =[];
                        var flag;
                        $(data).each(function (){		
                        	if(this.state != '1'){
                        		flag = false;
                        	}
								ParamVo.push(
								this.group_id   +"@"+ 
								this.hos_id   +"@"+ 
								this.copy_code   +"@"+ 
								this.protocol_id 
								); });
                        if(flag == false){
                       	 $.ligerDialog.error('只有新建状态的数据允许删除');
                       	 return ;
                        }
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteMedProtocolMain.do?isCheck=true",{ParamVo : ParamVo.toString()},function (responseData){
                            		if(responseData.state=="true"){
                            			loadHead(null);
                            		}
                            	});
                        	}
                        }); 
                    }
    	}
    
    function imp(){
    	var index = layer.open({
					type : 2,
					title : '科室申请资产明细',
					shadeClose : false,
					shade : false,
					maxmin : true, //开启最大化最小化按钮
					area : [ '893px', '500px' ],
					content : 'medProtocolMainImportPage.do?isCheck=false'
				});
				layer.full(index);
    }	
    
    function downTemplate(){
    	
    	location.href = "downTemplate.do?isCheck=false";
    }	
   
    function openUpdate(obj){
    		
		var vo = obj.split("|");
		var parm = 
			"group_id="+vo[0]   +"&"+ 
			"hos_id="+vo[1]   +"&"+ 
			"copy_code="+vo[2]   +"&"+ 
			"protocol_id="+vo[3] +"&"+ 
			"state="+vo[4]
		$.ligerDialog.open({ url : 'medProtocolMainUpdatePage.do?isCheck=false&'+parm,data:{}, 
			height: 600,width: 1100, title:'修改',modal:true,showToggle:false,showMax:true,
			showMin: true,isResize:true,top:0
		});
    }
    //审核
    function audit(){
    	var data = gridManager.getCheckedRows();
    	var ParamVo =[];
    	var str = '';
        if (data.length == 0){
        	$.ligerDialog.error('请选择行');
        	return;
        }else{
        	 $(data).each(function (){
        		 if(this.state != 1){
        			 str += this.protocol_code ;
         		}else{
					ParamVo.push(
						this.group_id   +"@"+ this.hos_id   +"@"+ this.copy_code   +"@"+ this.protocol_id +"@"+ 2
					)};
        	 })
        	 
        	 if( str != ''){
             	$.ligerDialog.error('协议编码:<span style="color:red">'+str+'不是新建状态不能审核(只允许新建状态的协议审核)</span>');
             	return;
             }else{
             	 $.ligerDialog.confirm('确定审核所选协议吗?', function (yes){
   	             	if(yes){
   	                 	ajaxJsonObjectByUrl("updateState.do?isCheck=true",{ParamVo : ParamVo.toString()},function (responseData){
   	                 		if(responseData.state=="true"){
   	                 			loadHead(null);
   	                 		}
   	                 	});
   	             	}
   	             }); 
             }
        }	
    }
    //消审
    function unaudit(){
    	var data = gridManager.getCheckedRows();
    	var ParamVo =[];
    	var str = '';
        if (data.length == 0){
        	$.ligerDialog.error('请选择行');
        	return;
        }else{
        	 $(data).each(function (){
        		 if(this.state != 2){
        			str += this.protocol_code + ",";
         		}else{
					ParamVo.push(
						this.group_id   +"@"+ this.hos_id   +"@"+ this.copy_code   +"@"+ this.protocol_id +"@"+ 1
					)};
        	 })
        	 
        	 if(str != ''){
             	$.ligerDialog.error('协议编码:<span style="color:red">'+str+'不是审核状态不能消审(只允许审核状态的协议消审)</span>');
             	return;
             }else{
             	 $.ligerDialog.confirm('确定消审所选协议吗?', function (yes){
   	             	if(yes){
   	                 	ajaxJsonObjectByUrl("updateState.do?isCheck=true",{ParamVo : ParamVo.toString()},function (responseData){
   	                 		if(responseData.state=="true"){
   	                 			loadHead(null);
   	                 		}
   	                 	});
   	             	}
   	             }); 
             }
        }	
    }
    //确认
    function confirm(){
    	var data = gridManager.getCheckedRows();
    	var ParamVo =[];
    	var str = '';
        if (data.length == 0){
        	$.ligerDialog.error('请选择行');
        	return;
        }else{
        	 $(data).each(function (){
        		 if(this.state != 2){
        			 str += this.protocol_code;
         		}else{
					ParamVo.push(
						this.group_id   +"@"+ this.hos_id   +"@"+ this.copy_code   +"@"+ this.protocol_id +"@"+ 3
					)};
        	 })
        	 
        	 if(str !=''){
             	$.ligerDialog.error('协议编码:<span style="color:red">'+str+'不是审核状态不能确认(只允许审核状态的协议确认)</span>');
             	return;
             }else{
             	 $.ligerDialog.confirm('确定确认所选协议吗?', function (yes){
   	             	if(yes){
   	                 	ajaxJsonObjectByUrl("updateState.do?isCheck=true",{ParamVo : ParamVo.toString()},function (responseData){
   	                 		if(responseData.state=="true"){
   	                 			loadHead(null);
   	                 		}
   	                 	});
   	             	}
   	             }); 
             }
        }	
    }
    //取消确认
    function unconfirm(){
    	var data = gridManager.getCheckedRows();
    	var ParamVo =[];
    	var str = '';
        if (data.length == 0){
        	$.ligerDialog.error('请选择行');
        	return;
        }else{
        	 $(data).each(function (){
        		 if(this.state != 3){
        			 str += this.protocol_code ;
         		}else{
					ParamVo.push(
						this.group_id   +"@"+ this.hos_id   +"@"+ this.copy_code   +"@"+ this.protocol_id +"@"+ 2
					)};
        	 })
        	 
        	 if(str != ''){
             	$.ligerDialog.error('协议编码:<span style="color:red">'+str+'不是确认状态不能取消确认(只允许确认状态的协议取消确认)</span>');
             	return;
             }else{
             	 $.ligerDialog.confirm('确定取消确认所选协议吗?', function (yes){
   	             	if(yes){
   	                 	ajaxJsonObjectByUrl("updateState.do?isCheck=true",{ParamVo : ParamVo.toString()},function (responseData){
   	                 		if(responseData.state=="true"){
   	                 			loadHead(null);
   	                 		}
   	                 	});
   	             	}
   	             }); 
             }
        }	
       
    }
    //终止
    function over(){
    	var data = gridManager.getCheckedRows();
    	var ParamVo =[];
    	var str = '';
        if (data.length == 0){
        	$.ligerDialog.error('请选择行');
        	return;
        }else{
        	 $(data).each(function (){
        		 if(this.state != 3){
        			 str += this.protocol_code ;
         		}else{
					ParamVo.push(
						this.group_id   +"@"+ this.hos_id   +"@"+ this.copy_code   +"@"+ this.protocol_id +"@"+ 4
					)};
        	 })
        	 
        	 if(str != '' ){
             	$.ligerDialog.error('协议编号:<span style="color:red">'+str+'不是确认状态不能终止(只允许确认状态的协议终止)</span>');
             	return;
             }else{
             	 $.ligerDialog.confirm('确定终止所选协议吗?', function (yes){
   	             	if(yes){
   	                 	ajaxJsonObjectByUrl("updateState.do?isCheck=true",{ParamVo : ParamVo.toString()},function (responseData){
   	                 		if(responseData.state=="true"){
   	                 			loadHead(null);
   	                 		}
   	                 	});
   	             	}
   	             }); 
             }
        }	
    }
    //取消终止
    function reset(){
    	var data = gridManager.getCheckedRows();
    	var ParamVo =[];
    	var str ='';
        if (data.length == 0){
        	$.ligerDialog.error('请选择行');
        	return;
        }else{
        	 $(data).each(function (){
        		 if(this.state != 4){
        			str += this.protocol_code ;
         		}else{
					ParamVo.push(
						this.group_id   +"@"+ this.hos_id   +"@"+ this.copy_code   +"@"+ this.protocol_id +"@"+ 3
					)};
        	 })
        	 
        	 if(str != ''){
             	$.ligerDialog.error('协议编号:<span style="color:red">'+str+'不是终止状态不能取消终止(只允许终止状态的协议取消终止)</span>');
             	return;
             }else{
             	 $.ligerDialog.confirm('确定取消终止所选协议吗?', function (yes){
   	             	if(yes){
   	                 	ajaxJsonObjectByUrl("updateState.do?isCheck=true",{ParamVo : ParamVo.toString()},function (responseData){
   	                 		if(responseData.state=="true"){
   	                 			loadHead(null);
   	                 		}
   	                 	});
   	             	}
   	             }); 
             }
        }	
    }
    function loadDict(){
    	//字典下拉框
    	//协议类别下拉框
		autocomplete("#type_id", "../../queryMedProtocolType.do?isCheck=false", "id", "text", true, true,'',true,'',160);
		//供应商下拉框
		autocomplete("#sup_id", "../../queryHosSupDict.do?isCheck=false", "id", "text", true, true,'',false,'',240);
		//签订部门：当前用户有权限的职能科室列表（HOS_DEPT_DICT 中is_stop=0的职能科室列表）
		autocomplete("#dept_id", "../../querySignedDept.do?isCheck=false", "id", "text", true, true);
		//甲方负责人（职工下拉框）
		autocomplete("#first_man", "../../queryMedEmpDict.do?isCheck=false", "id", "text", true, true,'',false,false,160);
		
		autodate("#beginDate", "yyyy-mm-dd", "month_first");
	    autodate("#endDate", "yyyy-mm-dd", "month_last");
	    autoCompleteByData("#is_init", yes_or_no.Rows, "id", "text", true, true,'',false,false,'160');//是否期初
	    
		$("#beginDate").ligerTextBox({width:100});
		$("#endDate").ligerTextBox({width:100});
		$("#sign_date").ligerTextBox({width:160});
		$("#type_id").ligerTextBox({width:160});
		$("#sup_id").ligerTextBox({width:240});
		$("#protocol_name").ligerTextBox({width:240});
		$("#protocol_code").ligerTextBox({width:240});
		$("#dept_id").ligerTextBox({width:160});
		$("#first_man").ligerTextBox({width:160});
	    $("#second_man").ligerTextBox({width:160});
	    $("#state").ligerTextBox({width:160});
	    $("#original_no").ligerTextBox({width:160});
	    
    }
    //键盘事件
	  function loadHotkeys() {

		hotkeys('Q', query);
		hotkeys('A', add);
		hotkeys('D', remove);
		hotkeys('B', downTemplate);
		hotkeys('E', exportExcel);
		hotkeys('P', printDate);
		hotkeys('I', imp);	
		
		hotkeys('Z', audit);
		hotkeys('U', unaudit);
		hotkeys('M', confirm);
		hotkeys('F', unconfirm);
		hotkeys('O', over);
		hotkeys('R', reset);

	 }
  //打印数据
	 function printDate(){
		//有数据直接打印
		if($("#resultPrint > table > tbody").html()!=""){
			lodopPrinterTable("resultPrint","开始打印","采购协议",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备打印数据,请稍候...');

		var printPara={
			usePager:false,
			sign_date:$("#sign_date").val(),
			type_id:liger.get("type_id").getValue().split(",")[0],
			sup_id:liger.get("sup_id").getValue().split(",")[0],
			protocol_name:$("#protocol_name").val(),
            protocol_code:$("#protocol_code").val(),
           	dept_id:liger.get("dept_id").getValue().split(",")[0],
           	first_man:liger.get("first_man").getValue().split(",")[0],
           	second_man:$("#second_man").val(),
           	state:$("#state").val()
         };
		ajaxJsonObjectByUrl("queryMedProtocolMain.do",printPara,function (responseData){
			 var trHtml='';
				$.each(responseData.Rows,function(idx,item){ 
					 	 trHtml+="<tr>";
						 trHtml+="<td>"+item.protocol_code+"</td>"; 
						 trHtml+="<td>"+item.type_name+"</td>"; 
						 trHtml+="<td>"+item.protocol_name+"</td>"; 
						 trHtml+="<td>"+item.sign_date+"</td>"; 
						 trHtml+="<td>"+item.sup_name+"</td>";
						 trHtml+="<td>"+item.dept_name+"</td>";
						 trHtml+="<td>"+item.emp_name+"</td>"; 
						 trHtml+="<td>"+item.second_man+"</td>";
						 trHtml+="<td>"+item.start_date+"</td>"; 
						 trHtml+="<td>"+item.end_date+"</td>"; 
						 trHtml+="<td>"+item.second_phone+"</td>";
						 if(item.state == 0){
							 trHtml+="<td>终止</td>";
						 }else if(item.state == 1){
							 trHtml+="<td>新建</td>";
						 }else if(item.state == 2){
							 trHtml+="<td>审核</td>";
						 }else{
							 trHtml+="<td>确认</td>";
						 }
				trHtml+="</tr>";
				$("#resultPrint > table > tbody").empty();
				$("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			//alert($("#resultPrint").html())
			lodopPrinterTable("resultPrint","开始打印","采购协议",true);
	    },true,manager);
		return;
	 }
	
	 
	 //导出数据
	 function exportExcel(){
		//有数据直接导出
		if($("#resultPrint > table > tbody").html()!=""){
			lodopExportExcel("resultPrint","导出Excel","采购协议.xls",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');

		var exportPara={
			usePager:false,
			sign_date:$("#sign_date").val(),
			type_id:liger.get("type_id").getValue().split(",")[0],
			sup_id:liger.get("sup_id").getValue().split(",")[0],
			protocol_name:$("#protocol_name").val(),
            protocol_code:$("#protocol_code").val(),
           	dept_id:liger.get("dept_id").getValue().split(",")[0],
           	first_man:liger.get("first_man").getValue().split(",")[0],
           	second_man:$("#second_man").val(),
           	state:$("#state").val()
         };
		ajaxJsonObjectByUrl("queryMedProtocolMain.do",exportPara,function (responseData){
			 var trHtml='';
			$.each(responseData.Rows,function(idx,item){ 
				 	 trHtml+="<tr>";
				 	trHtml+="<td>"+item.protocol_code+"</td>"; 
					 trHtml+="<td>"+item.type_name+"</td>"; 
					 trHtml+="<td>"+item.protocol_name+"</td>"; 
					 trHtml+="<td>"+item.sign_date+"</td>"; 
					 trHtml+="<td>"+item.sup_name+"</td>";
					 trHtml+="<td>"+item.dept_name+"</td>";
					 trHtml+="<td>"+item.emp_name+"</td>"; 
					 trHtml+="<td>"+item.second_man+"</td>";
					 trHtml+="<td>"+item.start_date+"</td>"; 
					 trHtml+="<td>"+item.end_date+"</td>"; 
					 trHtml+="<td>"+item.second_phone+"</td>";
					 if(item.state == 0){
						 trHtml+="<td>终止</td>";
					 }else if(item.state == 1){
						 trHtml+="<td>新建</td>";
					 }else if(item.state == 2){
						 trHtml+="<td>审核</td>";
					 }else{
						 trHtml+="<td>确认</td>";
					 }
					  
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").empty();
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			lodopExportExcel("resultPrint","导出Excel","采购协议.xls",true);
	    },true,manager);
		return;
	 }		  
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">签订日期：</td>
        	<td>
        		<table>
        			<tr>
	        			 <td align="left" class="l-table-edit-td" style="width: 100">
			            	<input class="Wdate" name="beginDate" id="beginDate" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
			            </td>
			            <td align="left" class="l-table-edit-td" style="width: 15">至：</td>
			            <td align="left" class="l-table-edit-td" style="width: 100">
			            	<input class="Wdate" name="endDate" id="endDate" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
			            </td>
        			</tr>
        		</table>
        	</td>
        	 
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"  ><font color="red">*</font>协议类别：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="type_id" type="text" id="type_id" ltype="text" validate="{required:true}" />
            </td>
              
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否期初：</td>
        	<td align="left" class="l-table-edit-td" >
				<input name="is_init" type="text" requried="true" id="is_init" />
			</td>
            
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">协议名称：</td>
            <td align="left" class="l-table-edit-td"  >
           		 <input name="protocol_name" type="text" id="protocol_name" ltype="text" validate="{required:true}" />
            </td>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"   >甲方负责人：</td>
            <td align="left" class="l-table-edit-td" >
           		 <input name="first_man" type="text" id="first_man" ltype="text" validate="{required:true}" />
            </td>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">签订部门：</td>
            <td align="left" class="l-table-edit-td">
           		 <input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:true}" />
            </td> 
        </tr> 
           
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">协议编号：</td>
            <td align="left" class="l-table-edit-td"  ><input name="protocol_code" type="text" id="protocol_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
             
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"   >乙方负责人：</td>
            <td align="left" class="l-table-edit-td"><input name="second_man" type="text" id="second_man" ltype="text" validate="{required:true,maxlength:20}" /></td>
      
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">状态：</td>
            <td align="left" class="l-table-edit-td">
            	<select name="state" id="state"style="width: 135px;" >
            			<option value="">请选择</option>
                		<option value="1">新建</option>
                		<option value="2">审核</option>
                		<option value="3">确认</option>
                		<option value="4">终止</option>
            	</select>
            </td>
            
        </tr>
        <tr>
        	
			 <td align="right" class="l-table-edit-td"  style="padding-left:20px;">供应商：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="sup_id" type="text" id="sup_id" ltype="text" validate="{required:true}" />
            </td>
             
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;" >原始编号：</td>
            <td align="left" class="l-table-edit-td"><input name="original_no" type="text" id="original_no" ltype="text" validate="{required:true,maxlength:20}" /></td>
             
        </tr> 
    </table>
	<div id="toptoolbar2" ></div>
	<div id="maingrid"></div>
	<div id="resultPrint" style="display:none">
	   	<table width="100%">
			<thead>
			<tr>
                <th width="200">协议编号</th>	
                <th width="200">类别ID</th>	
                <th width="200">协议名称</th>	
                <th width="200">签订日期</th>
                <th width="200">供应商</th>	
                <th width="200">签订科室</th>
                <th width="200">甲方负责人</th>	
                <th width="200">乙方负责人</th>
                <th width="200">开始日期</th>	
                <th width="200">截止日期</th>	
                <th width="200">乙方电话</th>	
                <th width="200">状态</th>	
			</tr>
			   	</thead>
			   	<tbody></tbody>
	   	</table>
   	</div>
</body>
</html>
