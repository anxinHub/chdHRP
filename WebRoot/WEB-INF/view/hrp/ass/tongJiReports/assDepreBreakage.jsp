<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<!--资产报损查询页面  -->
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
     
    	  grid.options.parms.push({name:'ass_nature',value:liger.get("ass_nature").getValue()}); 
    	  grid.options.parms.push({name:'year_month',value:$("#year_month").val()});
    	  grid.options.parms.push({name:'ass_dis_no',value:$("#ass_dis_no").val()});
    	  grid.options.parms.push({name:'bus_type_code',value:liger.get("bus_type_code").getValue()}); 
    	  grid.options.parms.push({name:'dept_id',value:liger.get("dept_id").getValue().split("@")[0]}); 
    	  grid.options.parms.push({name:'store_id',value:liger.get("store_id").getValue().split("@")[0]}); 
		 
    	//加载查询条件
    	grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                   
              { display: '年月', name: 'year_month', align: 'left',width:80
				 		}, 
              { display: '处置单号', name: 'dis_r_no', align: 'left',width:120
				 		},
              { display: '业务类型', name: 'dispose_type_name', align: 'left',width:100
				 		},
              { display: '资产性质', name: 'naturs_name', align: 'left',width:100
				 		},
              { display: '卡片位置', name: 'location', align: 'left',width:150
				 		},
              { display: '仓库', name: 'store_name', align: 'left',width:150
				 		},
              
              { display: '科室', name: 'dept_name', align: 'left',width:120
				 		},
             
              { display: '制单人', name: 'create_emp_name', align: 'left',width:80
				 		},
              { display: '制单日期', name: 'create_date', align: 'left',width:100
				 		},
              { display: '审核人', name: 'audit_emp_name', align: 'left',width:80
				 		},
              { display: '审核日期', name: 'apply_date', align: 'left',width:100
				 		},
              { display: '状态', name: 'state', align: 'left',width:50,
				 			render : function(rowdata, rowindex,
									value) {
				 				if(rowdata.state==0){
									return "新建";
								}
								else if (rowdata.state==1){
									return "审核";
								}else if (rowdata.state==2){
									return "审批";
								}else if (rowdata.state==3){
									return "登记";
								}
							   }
				 		},
              { display: '记账人', name: 'confirm_emp_name', align: 'left',width:80,
				 		},
              { display: '记账日期', name: 'confirm_date', align: 'left',width:100
				 		},
			  { display: '备注', name: 'note', align: 'left',width:200
				 		},
              ],
              dataAction: 'server',dataType: 'server',usePager:true,url:'../tongJiReports/queryAssDepreBreakage.do?state=2',
             width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad :true,
                     selectRowButtonOnly:true,
                     toolbar: { items: [
		                     	{ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' },
		                     	{ line:true },
		                     	{ text: '打印（<u>P</u>）', id:'print', click: printDate,icon:'print' },
				                { line:true },
// 				    	        { text: '导出Excel（<u>E</u>）', id:'export', click: exportExcel,icon:'pager' },
// 						        { line:true },
				    		]},
    				 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    
  //键盘事件
	  function loadHotkeys() {

		hotkeys('Q', query);

		hotkeys('E', exportExcel);

		hotkeys('P', printDate);
		 
	 }

  
	//打印数据
	 	function printDate(){
	 		 if(grid.getData().length==0){
	   			$.ligerDialog.error("请先查询数据！");
	   			return;
	   		} 
	 		
	 		var time=new Date();
	    	var date=$("#year_month").val();
	    	var heads={
	        		"isAuto":true,//系统默认，页眉显示页码
	        		"rows": [
	    	          {"cell":0,"value":"资产性质："},
	    	          {"cell":1,"value":liger.get("ass_nature").getText().split(" ")[1]},
	    	          {"cell":13,"value":"报表日期:"},
	  				  {"cell":14,"value":date} ,
	    	          
	        	]}; 
	    	//表尾
	    	var foots = {
	    			rows: [
	    				{"cell":13,"value":"制表人:"},
	    				{"cell":14,"value":"${sessionScope.user_name}"},
	    			]
	    		}; 
	 		var printPara={
	 				title: "资产报损查询",//标题
	 				columns: JSON.stringify(grid.getPrintColumns()),//表头
	 				class_name: "com.chd.hrp.ass.service.tongJiReports.AssDepreBreakageService",
	 				method_name: "queryAssDepreBreakagePrint",
	 				bean_name: "assDepreBreakageService" ,
	 				heads: JSON.stringify(heads), //表头需要打印的查询条件,可以为空
	 				foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 
	 				};
	 		
	 		$.each(grid.options.parms,function(i,obj){
	 				printPara[obj.name]=obj.value;
	 		});
	 		
	 		officeGridPrint(printPara);
	 	   		
	 	}
	 
	 //导出数据
	 function exportExcel(){
		//有数据直接导出
		if($("#resultPrint > table > tbody").html()!=""){
			lodopExportExcel("resultPrint","导出Excel","051201 资产折旧到期查询.xls",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');

		var exportPara={
			usePager:false,
           
         };
		ajaxJsonObjectByUrl("../tongJiReports/queryAssDepreBreakage.do?state=2",exportPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
					 trHtml+="<td>"+item.year_month+"</td>"; 
					 trHtml+="<td>"+item.dis_id+"</td>"; 
					 trHtml+="<td>"+item.ass_dis_no+"</td>"; 
					 trHtml+="<td>"+item.bus_type_name+"</td>"; 
					 trHtml+="<td>"+item.naturs_name+"</td>"; 
					 trHtml+="<td>"+item.card+"</td>"; 
					 trHtml+="<td>"+item.store_code+"</td>"; 
					 trHtml+="<td>"+item.store_name+"</td>"; 
					 trHtml+="<td>"+item.dept_code+"</td>"; 
					 trHtml+="<td>"+item.dept_name+"</td>"; 
					 trHtml+="<td>"+item.note+"</td>"; 
					 trHtml+="<td>"+item.create_emp_name+"</td>"; 
					 trHtml+="<td>"+item.create_date+"</td>"; 
					 trHtml+="<td>"+item.audit_emp_name+"</td>"; 
					 trHtml+="<td>"+item.audit_date+"</td>"; 
						if(item.state == 0){
							trHtml += "<td>新建</td>";
						}else if(item.state == 1){
							trHtml += "<td>审核</td>";
						}else if(item.state == 2){
							trHtml += "<td>审批</td>";
						}else if(item.state == 3){
							trHtml += "<td>登记</td>";
						}
					 trHtml+="<td>"+item.confirm_emp_name+"</td>"; 
					 trHtml+="<td>"+item.confirm_date+"</td>"; 
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			lodopExportExcel("resultPrint","导出Excel","051201 资产折旧到期查询.xls",true);
	    },true,manager);
		return;
	 }	
	 
	    function loadDict(){
	    	
			var param = {
	            	query_key:''
	        };
			
            //字典下拉框 
         autocomplete("#dept_id", "../queryDeptDict.do?isCheck=false", "id", "text", true, true,param,true);
		
		autocomplete("#store_id", "../queryHosStoreDict.do?isCheck=false","id", "text",true,true,param,true);
		
		autocomplete("#bus_type_code", "../queryAssDisposeTypeDict.do?isCheck=false","id", "text",true,true,param,true);
		
		autocomplete("#ass_nature", "../queryAssNaturs.do?isCheck=false","id", "text",true,true,param,true);
		
        $("#year_month").ligerTextBox({width:160});
        
        $("#ass_dis_no").ligerTextBox({width:160});
         
        
         }  
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
     <table cellpadding="0" cellspacing="0" class="l-table-edit" >
		<tr>
		   	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">统计年月：</td>
			<td align="left" class="l-table-edit-td"><input name="year_month" type="text" id="year_month" ltype="text" validate="{required:true,maxlength:20}" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;">处置单号：</td>
		    <td align="left" class="l-table-edit-td"><input name="ass_dis_no" type="text" id="ass_dis_no"  /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产性质：</td>
		     <td align="left" class="l-table-edit-td"><input name="ass_nature" type="text" id="ass_nature"  /></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;">业务类型：</td>
			<td align="left" class="l-table-edit-td"><input name="bus_type_code" type="text" id="bus_type_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;">仓库：</td>
			<td align="left" class="l-table-edit-td"><input name="store_id" type="text" id="store_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室：</td>
		    <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
		    <td align="left"></td>
		</tr> 
       
    </table>

	<div id="maingrid"></div>
</body>
</html>
