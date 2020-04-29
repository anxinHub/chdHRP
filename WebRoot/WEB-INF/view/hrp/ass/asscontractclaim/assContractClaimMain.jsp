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
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
		 loadHotkeys();
        $("#ven_id").ligerTextBox({width:160});
        $("#create_date").ligerTextBox({width:160});
        $("#first_emp").ligerTextBox({width:160});
        $("#second_emp").ligerTextBox({width:160});
        $("#begin_date").ligerTextBox({width:160});
        $("#end_date").ligerTextBox({width:160});
		
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	  grid.options.parms.push({name:'ven_id',value:liger.get("ven_id").getValue().split("@")[0]}); 
    	  grid.options.parms.push({name:'ven_no',value:liger.get("ven_id").getValue().split("@")[1]}); 
    	  grid.options.parms.push({name:'create_date',value:$("#create_date").val()}); 
    	  grid.options.parms.push({name:'state',value:$("#state").val()}); 
    	  grid.options.parms.push({name:'first_emp',value:$("#first_emp").val()}); 
    	  grid.options.parms.push({name:'second_emp',value:$("#second_emp").val()}); 
    	  grid.options.parms.push({name:'begin_date',value:$("#begin_date").val()}); 
    	  grid.options.parms.push({name:'end_date',value:$("#end_date").val()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
     }
   

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '索赔单号', name: 'claim_no', align: 'left',
						render : function(rowdata, rowindex,
								value) {
							return "<a href=javascript:openUpdate('"+rowdata.group_id   + "|" + 
							rowdata.hos_id   + "|" + 
							rowdata.copy_code   + "|" + 
							rowdata.claim_no  +"')>"+rowdata.claim_no+"</a>";
						}
					 		},
                     { display: '合同', name: 'contract_name', align: 'left'
					 		},
                     { display: '供应商', name: 'ven_name', align: 'left'
					 		},
                     { display: '登记日期', name: 'create_date', align: 'left'
					 		},
                     { display: '赔偿金额', name: 'breach_money', align: 'right',
								render: function(item)
					            {
					                    return formatNumber(item.breach_money,'${ass_05005}',1);
					            }
					 		},
                     { display: '状态', name: 'state', align: 'left',
								render : function(rowdata, rowindex,
										value) {
									if(rowdata.state == 1){
										return "审核";
									}else if(rowdata.state == 0){
										return "新建";
									}
								}
					 		},
                     { display: '索赔方', name: 'claim_flag', align: 'left',
								render : function(rowdata, rowindex,
										value) {
									if(rowdata.claim_flag == 1){
										return "乙方";
									}else if(rowdata.claim_flag == 0){
										return "甲方";
									}
								}
					 		},
                     { display: '甲方负责人', name: 'first_emp_name', align: 'left'
					 		},
                     { display: '乙方负责人', name: 'second_emp_name', align: 'left'
					 		},
                     { display: '开始日期', name: 'begin_date', align: 'left'
					 		},
                     { display: '结束日期', name: 'end_date', align: 'left'
					 		},
                     { display: '索赔依据', name: 'claim_basis', align: 'left'
					 		},
                     { display: '索赔原因', name: 'claim_reason', align: 'left'
					 		}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAssContractClaim.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad :true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询（<u>E</u>）', id:'search', click: query,icon:'search' },
                     	{ line:true },
				    					{ text: '添加（<u>A</u>）', id:'add', click: add_open, icon:'add' },
				    	                { line:true },
				    	                { text: '删除（<u>D</u>）', id:'delete', click: remove,icon:'delete' },
										{ line:true }, 
										{ text: '打印（<u>P</u>）', id:'print', click: printDate,icon:'print' },
						                { line:true },
						                //{ text: '导出Excel（<u>E</u>）', id:'export', click: exportExcel,icon:'pager' },
				    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code   + "|" + 
								rowdata.claim_no 
							);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function add_open(){
    	
    	parent.$.ligerDialog
		.open({
			title : ' 资产合同索赔添加',
			height : $(window).height(),
			width : $(window).width(),
			url : 'hrp/ass/asscontractclaim/assContractClaimAddPage.do?isCheck=false&',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : true,
			isResize : true,
			parentframename : window.name, //用于parent弹出层调用本页面的方法或变量
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
							this.claim_no 
							) });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteAssContractClaim.do",{ParamVo : ParamVo.toString()},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
    	}
    function imp(){
    	
    	var index = layer.open({
					type : 2,
					title : '导入',
					shadeClose : false,
					shade : false,
					maxmin : true, //开启最大化最小化按钮
					area : [ '893px', '500px' ],
					content : 'assContractClaimImportPage.do?isCheck=false'
				});
				layer.full(index);
    	}	
    function downTemplate(){
    	
    	location.href = "downTemplate.do?isCheck=false";
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
			"claim_no="+vo[3] ;
		
		parent.$.ligerDialog
		.open({
			title : ' 资产合同索赔修改',
			height : $(window).height(),
			width : $(window).width(),
			url : 'hrp/ass/asscontractclaim/assContractClaimUpdatePage.do?isCheck=false&',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : true,
			isResize : true,
			parentframename : window.name, //用于parent弹出层调用本页面的方法或变量
		});	
    
    }
    function loadDict(){
    	var param = {query_key:''};

		autocomplete("#first_emp", "../../../hrp/sys/queryUserDict.do?isCheck=false", "id", "text", true, true, param, true);
		
		autocomplete("#second_emp", "../../../hrp/sys/queryUserDict.do?isCheck=false", "id", "text", true, true, param, true);
    	
		autocomplete("#ven_id", "../queryHosSupDict.do?isCheck=false","id",  "text",true,true, param, true);
            
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
		

	 }
  //打印数据
// 	 function printDate(){
// 		//有数据直接打印
// 		if($("#resultPrint > table > tbody").html()!=""){
// 			lodopPrinterTable("resultPrint","开始打印","050502 资产合同索赔",true);
// 			return;
// 		}
		
// 		//重新查询数据，避免分页导致打印数据不全
// 		var manager = $.ligerDialog.waitting('系统正在准备打印数据,请稍候...');

// 		var printPara={
// 			  usePager:false,
// 			  ven_id:$("#ven_id").val(),
// 		  	  ven_no:$("#ven_no").val(),
// 		  	  create_date:$("#create_date").val(),
// 		  	  state:$("#state").val(),
// 		  	  first_emp:$("#first_emp").val(),
// 		  	  second_emp:$("#second_emp").val(), 
// 		  	  begin_date:$("#begin_date").val(),
// 		  	  end_date:$("#end_date").val()
//          };
// 		ajaxJsonObjectByUrl("queryAssContractClaim.do",printPara,function (responseData){
// 			$.each(responseData.Rows,function(idx,item){ 
// 				 var trHtml="<tr>";
// 				 trHtml+="<td>"+item.claim_no+"</td>"; 
// 				 trHtml+="<td>"+item.contract_name+"</td>"; 
// 				 trHtml+="<td>"+item.ven_name+"</td>"; 
// 				 trHtml+="<td>"+item.create_date+"</td>"; 
// 				 trHtml+="<td>"+item.breach_money+"</td>"; 
// 				 	if(item.state == 1){
// 						 trHtml+="<td>审核</td>"; 
// 					}else if(item.state == 0){
// 						trHtml+="<td>新建</td>"; 
// 					}
// 				   if(item.claim_flag == 1){
// 						trHtml+="<td>乙方</td>";
// 					}else if(item.claim_flag == 0){
// 						trHtml+="<td>甲方</td>";
// 					}
// 				 trHtml+="<td>"+item.first_emp_name+"</td>"; 
// 				 trHtml+="<td>"+item.second_emp_name+"</td>"; 
// 				 trHtml+="<td>"+item.begin_date+"</td>"; 
// 				 trHtml+="<td>"+item.end_date+"</td>"; 
// 				 trHtml+="<td>"+item.claim_basis+"</td>"; 
// 				 trHtml+="<td>"+item.claim_reason+"</td>"; 
// 				 trHtml+="</tr>";
// 				$("#resultPrint > table > tbody").append(trHtml);
// 			});
// 			manager.close();
// 			//alert($("#resultPrint").html())
// 			lodopPrinterTable("resultPrint","开始打印","050502 资产合同索赔",true);
// 	    },true,manager);
// 		return;
// 	 }
	 function printDate(){
	    	
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
	       			title:'合同索赔',
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
	   		ajaxJsonObjectByUrl("queryAssContractClaim.do?isCheck=false", selPara, function (responseData) {
	   			printGridView(responseData,printPara);
			});

	   		
	    }
	 
	 //导出数据
	 function exportExcel(){
		//有数据直接导出
		if($("#resultPrint > table > tbody").html()!=""){
			lodopExportExcel("resultPrint","导出Excel","050502 资产合同索赔.xls",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');

		var exportPara={
			usePager:false,
			ven_id:$("#ven_id").val(),
		  	  ven_no:$("#ven_no").val(),
		  	  create_date:$("#create_date").val(),
		  	  state:$("#state").val(),
		  	  first_emp:$("#first_emp").val(),
		  	  second_emp:$("#second_emp").val(), 
		  	  begin_date:$("#begin_date").val(),
		  	  end_date:$("#end_date").val()
         };
		ajaxJsonObjectByUrl("queryAssContractClaim.do",exportPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
					 trHtml+="<td>"+item.claim_no+"</td>"; 
					 trHtml+="<td>"+item.contract_name+"</td>"; 
					 trHtml+="<td>"+item.ven_name+"</td>"; 
					 trHtml+="<td>"+item.create_date+"</td>"; 
					 trHtml+="<td>"+item.breach_money+"</td>"; 
					 if(item.state == 1){
						trHtml+="<td>审核</td>"; 
					 }else if(item.state == 0){
						trHtml+="<td>新建</td>"; 
					 } 
					 if(item.claim_flag == 1){
						trHtml+="<td>乙方</td>";
					 }else if(item.claim_flag == 0){
						trHtml+="<td>甲方</td>";
					 } 
					 trHtml+="<td>"+item.first_emp_name+"</td>"; 
					 trHtml+="<td>"+item.second_emp_name+"</td>"; 
					 trHtml+="<td>"+item.begin_date+"</td>"; 
					 trHtml+="<td>"+item.end_date+"</td>"; 
					 trHtml+="<td>"+item.claim_basis+"</td>"; 
					 trHtml+="<td>"+item.claim_reason+"</td>"; 
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			lodopExportExcel("resultPrint","导出Excel","050502 资产合同索赔.xls",true);
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
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">供应商：</td>
            <td align="left" class="l-table-edit-td"><input name="ven_id" type="text" id="ven_id"  /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">登记日期：</td>
            <td align="left" class="l-table-edit-td"><input name="create_date" type="text" id="create_date" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">甲方负责人：</td>
            <td align="left" class="l-table-edit-td"><input name="first_emp" type="text" id="first_emp"  /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">乙方负责人：</td>
            <td align="left" class="l-table-edit-td"><input name="second_emp" type="text" id="second_emp"  /></td>
            <td align="left"></td>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">开始日期：</td>
            <td align="left" class="l-table-edit-td"><input name="begin_date" type="text" id="begin_date" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">结束日期：</td>
            <td align="left" class="l-table-edit-td"><input name="end_date" type="text" id="end_date" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
            <td align="left"></td>
        </tr> 
    </table>

	<div id="maingrid"></div>
	<div id="resultPrint" style="display:none">
	   	<table width="100%">
			<thead>
		 
			<tr>
                <th width="200">索赔单号</th>	
                <th width="200">合同</th>	
                <th width="200">供应商</th>	
                <th width="200">登记日期</th>	
                <th width="200">赔偿金额</th>	
                <th width="200">状态</th>	
                <th width="200">索赔方</th>	
                <th width="200">甲方负责人</th>	
                <th width="200">乙方负责人</th>	
                <th width="200">开始日期</th>	
                <th width="200">结束日期</th>	
                <th width="200">索赔依据</th>	
                <th width="200">索赔原因</th>	
			</tr>
			   	</thead>
			   	<tbody></tbody>
	   	</table>
   	</div>
</body>
</html>
