<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
    var titleData;
    $(function ()
    {
		loadDict();
    	loadHead(null);	//加载数据
    	$("#year_month").ligerTextBox({ width:160 });
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'b_year_month',value:$("#b_year_month").val()}); 
    	grid.options.parms.push({name:'e_year_month',value:$("#e_year_month").val()}); 
    	grid.options.parms.push({name:'dept_id',value:liger.get("dept").getValue().split(".")[0]}); 
    	grid.options.parms.push({name:'dept_no',value:liger.get("dept").getValue().split(".")[1]}); 
    	grid.options.parms.push({name:'emp_id',value:liger.get("emp").getValue().split(".")[0]}); 
    	grid.options.parms.push({name:'emp_no',value:liger.get("emp").getValue().split(".")[1]}); 
    	grid.options.parms.push({name:'emp_kind_code',value:liger.get("emp_kind_code").getValue()}); 
    	grid.options.parms.push({name:'emp_kind_code',value:liger.get("scheme").getValue()}); 
   
    	

    	//加载查询条件
    	grid.loadData(grid.where);
     }

  /*   
	,totalSummary:{
//			type:'sum',
//			render:function(suminf,column,cell){
//				return '<div>合计</div>';
//			}
//		} */
    function loadHead(){
    	var columns = "{ display: '统计年', name: 'acc_year', align: 'left'"
   		 +"},"
            +"{ display: '统计月', name: 'acc_month', align: 'left'"
   		 +"},"
         +"{ display: '科室', name: 'dept_name', align: 'left'"
   		 +"},"
            +"{ display: '职工', name: 'emp_name', align: 'left'"
   		 +"},"
            +"{ display: '职工分类', name: 'emp_kind_name', align: 'left'"
   		 +"}";
      
   		$.post("queryBonusSchemeSet.do?isCheck=false",{'scheme_id':liger.get("scheme").getValue()},function(data){
   			 titleData = data;
			 $("#resultTable").empty();
   			 var str1 = "<tr><th width='200'>统计年</th><th width='200'>统计月</th><th width='200'>科室</th><th width='200'>职工</th><th width='200'>职工分类</th>";
			 var str2;
			 var str3 = "</tr>";
			 $.each(data.Rows,function(i,v){
				 columns = columns + ",{ display: '"+v.bonus_item_name+"', name: '"+v.bonus_column+"', align: 'left'}";
				 str2 = str2 + "<th width='200'>"+v.bonus_item_name+"</th>";
			 });
			 $("#resultTable").append(str1 + str2 + str3);
			 grid = $("#maingrid").ligerGrid({
		           columns: eval("["+columns+"]"),
		                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryCostEmpBonusDetail.do?isCheck=false',
		                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad:true,
		                     selectRowButtonOnly:true,//heightDiff: -10,
		                     toolbar: { items: [
		                     	{ text: '查询', id:'search', click: query,icon:'search' },
		                     	{ line:true },
		    					{ text: '添加', id:'add', click: itemclick, icon:'add' },
		    	                { line:true },
		    	                { text: '删除', id:'delete', click: itemclick,icon:'delete' },
								{ line:true }, 
// 		    	                { text: '导出Excel', id:'export', click: exportExcel,icon:'pager' },
// 				                { line:true },
				                { text: '打印', id:'print', click: print,icon:'print' },
				                { line:true },
				                { text: '下载导入模板', id:'downTemplate', click:itemclick,icon:'down' },
				                { line:true },
				                { text: '导入', id:'import', click: itemclick,icon:'up' }
		    				]},
		    				onDblClickRow : function (rowdata, rowindex, value)
		    				{
		    					if(rowdata.year_month == null){
		    						$.ligerDialog.warn('请选择行'); 
		    						return false ;
		    					}
		    					
								openUpdate(
										rowdata.group_id   + "|" + 
										rowdata.hos_id   + "|" + 
										rowdata.copy_code   + "|" + 
										rowdata.acc_year   + "|" + 
										rowdata.acc_month   + "|" + 
										rowdata.dept_id   + "|" + 
										rowdata.dept_no   + "|" + 
										rowdata.emp_id   + "|" + 
										rowdata.emp_no   + "|" + 
										rowdata.emp_kind_code 
									);
		    				} 
		                   });

		        gridManager = $("#maingrid").ligerGetGridManager();
		 },"json");
    }

    function itemclick(item){ 
        if(item.id)
        {
            switch (item.id)
            {
                case "add":
                	var scheme = liger.get("scheme").getValue();
              		$.ligerDialog.open({url: 'costEmpBonusDetailAddPage.do?isCheck=false&scheme_id='+scheme, height: 300,width: 500, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveCostEmpBonusDetail(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
              		return;
                case "modify":
                    return;
                case "delete":
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
							this.copy_code   +"@"+ 
							this.acc_year   +"@"+ 
							this.acc_month   +"@"+ 
							this.dept_id   +"@"+ 
							this.dept_no   +"@"+ 
							this.emp_id   +"@"+ 
							this.emp_no   +"@"+ 
							this.emp_kind_code 
							)
                        });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteCostEmpBonusDetail.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
                    return;
				case "import":
					var scheme = liger.get("scheme").getValue();
                	$.ligerDialog.open({url: 'costEmpBonusDetailImportPage.do?isCheck=false&scheme_id='+scheme, height: 500,width: 800, title:'导入',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true });
                case "export":
                	return;
                case "downTemplate":
                	var scheme = liger.get("scheme").getValue();
                	location.href = "downTemplate.do?isCheck=false&scheme_id="+scheme;
                	return;
                case "Excel":
                case "Word":
                case "PDF":
                case "TXT":
                case "XML":
                    $.ligerDialog.waitting('导出中，请稍候...');
                    setTimeout(function ()
                    {
                        $.ligerDialog.closeWaitting();
                        if (item.id == "Excel")
                            $.ligerDialog.success('导出成功');
                        else
                            $.ligerDialog.error('导出失败');
                    }, 1000);
                    return;
            }   
        }
        
    }
    function openUpdate(obj){
    	var scheme = liger.get("scheme").getValue();
		var vo = obj.split("|");
		var parm = 
			"group_id="+vo[0]   +"&"+ 
			"hos_id="+vo[1]   +"&"+ 
			"copy_code="+vo[2]   +"&"+ 
			"acc_year="+vo[3]   +"&"+ 
			"acc_month="+vo[4]   +"&"+ 
			"dept_id="+vo[5]   +"&"+ 
			"dept_no="+vo[6]   +"&"+ 
			"emp_id="+vo[7]   +"&"+ 
			"emp_no="+vo[8]   +"&"+ 
			"emp_kind_code="+vo[9] +"&"+
			"scheme_id="+scheme; 
    	$.ligerDialog.open({ url : 'costEmpBonusDetailUpdatePage.do?isCheck=false&' + parm,data:{}, height: 300,width: 500, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveCostEmpBonusDetail(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    }
    function loadDict(){
            //字典下拉框
            $("#b_year_month").ligerTextBox({ width:160 });
	     $("#e_year_month").ligerTextBox({ width:160});
	 		 
			 autodate("#b_year_month","yyyyMM"); 
			 autodate("#e_year_month","yyyyMM");
			autocomplete("#dept","../queryDeptDictNo.do?isCheck=false","id","text",true,true);
			autocomplete("#emp","../queryEmpArrt.do?isCheck=false","id","text",true,true);
			autocomplete("#emp_kind_code","../queryEmpTypeArrt.do?isCheck=false","id","text",true,true);
			
			/*
				2016/11/2 lxj
				修改:工资方案加默认值
			*/
			autocomplete("#scheme","../queryBonusSchemeSet.do?isCheck=false","id","text",true,true,'',true);
			
         }  
    
 /*  //打印数据
	 function printDate(){
	  
		 $("#resultPrint > table > tbody").empty();
		//有数据直接打印
		if($("#resultPrint > table > tbody").html()!=""){
			lodopPrinterTable("resultPrint","职工奖金明细数据采集开始打印","职工奖金明细数据采集列表",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备打印数据,请稍候...');
		
		var printPara={
			usePager:false,
			b_year_month:$("#b_year_month").val(),
			e_year_month:$("#e_year_month").val(),
	    	dept_id:liger.get("dept").getValue().split(".")[0], 
	    	dept_no:liger.get("dept").getValue().split(".")[1],
	    	emp_id:liger.get("emp").getValue().split(".")[0],
	    	emp_no:liger.get("emp").getValue().split(".")[1], 
	    	emp_kind_code:liger.get("emp_kind_code").getValue()
         };
		ajaxJsonObjectByUrl("queryCostEmpBonusDetail.do?isCheck=false",printPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
				 trHtml+="<td>"+item.acc_year+item.acc_month+"</td>"; 
                 trHtml+="<td>"+item.dept_name+"</td>"; 
                 trHtml+="<td>"+item.emp_name+"</td>"; 
                 trHtml+="<td>"+item.emp_kind_name+"</td>"; 
                 $.each(titleData.Rows,function(i,v){
                	 if(v.bonus_column == "bonus1"){
                		 trHtml+="<td>"+item.bonus1+"</td>"; 
                	 }
                	 if(v.bonus_column == "bonus2"){
                		 trHtml+="<td>"+item.bonus2+"</td>"; 
                	 }
                	 if(v.bonus_column == "bonus3"){
                		 trHtml+="<td>"+item.bonus3+"</td>"; 
                	 }
                	 if(v.bonus_column == "bonus4"){
                		 trHtml+="<td>"+item.bonus4+"</td>"; 
                	 }
                	 if(v.bonus_column == "bonus5"){
                		 trHtml+="<td>"+item.bonus5+"</td>"; 
                	 }
                	 if(v.bonus_column == "bonus6"){
                		 trHtml+="<td>"+item.bonus6+"</td>"; 
                	 }
                	 if(v.bonus_column == "bonus7"){
                		 trHtml+="<td>"+item.bonus7+"</td>"; 
                	 }
                	 if(v.bonus_column == "bonus8"){
                		 trHtml+="<td>"+item.bonus8+"</td>"; 
                	 }
                	 if(v.bonus_column == "bonus9"){
                		 trHtml+="<td>"+item.bonus9+"</td>"; 
                	 }
                	 if(v.bonus_column == "bonus10"){
                		 trHtml+="<td>"+item.bonus10+"</td>"; 
                	 }
                	 if(v.bonus_column == "bonus11"){
                		 trHtml+="<td>"+item.bonus11+"</td>"; 
                	 }
                	 if(v.bonus_column == "bonus12"){
                		 trHtml+="<td>"+item.bonus12+"</td>"; 
                	 }
                	 if(v.bonus_column == "bonus13"){
                		 trHtml+="<td>"+item.bonus13+"</td>"; 
                	 }
                	 if(v.bonus_column == "bonus14"){
                		 trHtml+="<td>"+item.bonus14+"</td>"; 
                	 }
                	 if(v.bonus_column == "bonus15"){
                		 trHtml+="<td>"+item.bonus15+"</td>"; 
                	 }
                	 if(v.bonus_column == "bonus16"){
                		 trHtml+="<td>"+item.bonus16+"</td>"; 
                	 }
                	 if(v.bonus_column == "bonus17"){
                		 trHtml+="<td>"+item.bonus17+"</td>"; 
                	 }
                	 if(v.bonus_column == "bonus18"){
                		 trHtml+="<td>"+item.bonus18+"</td>"; 
                	 }
                	 if(v.bonus_column == "bonus19"){
                		 trHtml+="<td>"+item.bonus19+"</td>"; 
                	 }
                	 if(v.bonus_column == "bonus20"){
                		 trHtml+="<td>"+item.bonus20+"</td>"; 
                	 }
                	 if(v.bonus_column == "bonus21"){
                		 trHtml+="<td>"+item.bonus21+"</td>"; 
                	 }
                	 if(v.bonus_column == "bonus22"){
                		 trHtml+="<td>"+item.bonus22+"</td>"; 
                	 }
                	 if(v.bonus_column == "bonus23"){
                		 trHtml+="<td>"+item.bonus23+"</td>"; 
                	 }
                	 if(v.bonus_column == "bonus24"){
                		 trHtml+="<td>"+item.bonus25+"</td>"; 
                	 }
                	 if(v.bonus_column == "bonus25"){
                		 trHtml+="<td>"+item.bonus25+"</td>"; 
                	 }
                	 if(v.bonus_column == "bonus26"){
                		 trHtml+="<td>"+item.bonus26+"</td>"; 
                	 }
                	 if(v.bonus_column == "bonus27"){
                		 trHtml+="<td>"+item.bonus27+"</td>"; 
                	 }
                	 if(v.bonus_column == "bonus28"){
                		 trHtml+="<td>"+item.bonus28+"</td>"; 
                	 }
                	 if(v.bonus_column == "bonus29"){
                		 trHtml+="<td>"+item.bonus29+"</td>"; 
                	 }
                	 if(v.bonus_column == "bonus30"){
                		 trHtml+="<td>"+item.bonus30+"</td>"; 
                	 }
				 });
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			//alert($("#resultPrint").html())
			lodopPrinterTable("resultPrint","职工奖金明细数据采集开始打印","职工奖金明细数据采集列表",true);
	    },true,manager);
		return;
	 }
	 */
	 
	 //导出数据
	 function exportExcel(){
		 $("#resultPrint > table > tbody").empty();
		//有数据直接导出
		if($("#resultPrint > table > tbody").html()!=""){
			lodopExportExcel("resultPrint","职工奖金明细数据采集导出Excel","职工奖金明细数据采集列表.xls",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');
		
		var exportPara={
			usePager:false,
			b_year_month:$("#b_year_month").val(),
			e_year_month:$("#e_year_month").val(),
	    	dept_id:liger.get("dept").getValue().split(".")[0], 
	    	dept_no:liger.get("dept").getValue().split(".")[1],
	    	emp_id:liger.get("emp").getValue().split(".")[0],
	    	emp_no:liger.get("emp").getValue().split(".")[1], 
	    	emp_kind_code:liger.get("emp_kind_code").getValue()
          
         };
		ajaxJsonObjectByUrl("queryCostEmpBonusDetail.do?isCheck=false",exportPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
				 if(v.bonus_column == "bonus1"){
            		 trHtml+="<td>"+item.bonus1+"</td>"; 
            	 }
            	 if(v.bonus_column == "bonus2"){
            		 trHtml+="<td>"+item.bonus2+"</td>"; 
            	 }
            	 if(v.bonus_column == "bonus3"){
            		 trHtml+="<td>"+item.bonus3+"</td>"; 
            	 }
            	 if(v.bonus_column == "bonus4"){
            		 trHtml+="<td>"+item.bonus4+"</td>"; 
            	 }
            	 if(v.bonus_column == "bonus5"){
            		 trHtml+="<td>"+item.bonus5+"</td>"; 
            	 }
            	 if(v.bonus_column == "bonus6"){
            		 trHtml+="<td>"+item.bonus6+"</td>"; 
            	 }
            	 if(v.bonus_column == "bonus7"){
            		 trHtml+="<td>"+item.bonus7+"</td>"; 
            	 }
            	 if(v.bonus_column == "bonus8"){
            		 trHtml+="<td>"+item.bonus8+"</td>"; 
            	 }
            	 if(v.bonus_column == "bonus9"){
            		 trHtml+="<td>"+item.bonus9+"</td>"; 
            	 }
            	 if(v.bonus_column == "bonus10"){
            		 trHtml+="<td>"+item.bonus10+"</td>"; 
            	 }
            	 if(v.bonus_column == "bonus11"){
            		 trHtml+="<td>"+item.bonus11+"</td>"; 
            	 }
            	 if(v.bonus_column == "bonus12"){
            		 trHtml+="<td>"+item.bonus12+"</td>"; 
            	 }
            	 if(v.bonus_column == "bonus13"){
            		 trHtml+="<td>"+item.bonus13+"</td>"; 
            	 }
            	 if(v.bonus_column == "bonus14"){
            		 trHtml+="<td>"+item.bonus14+"</td>"; 
            	 }
            	 if(v.bonus_column == "bonus15"){
            		 trHtml+="<td>"+item.bonus15+"</td>"; 
            	 }
            	 if(v.bonus_column == "bonus16"){
            		 trHtml+="<td>"+item.bonus16+"</td>"; 
            	 }
            	 if(v.bonus_column == "bonus17"){
            		 trHtml+="<td>"+item.bonus17+"</td>"; 
            	 }
            	 if(v.bonus_column == "bonus18"){
            		 trHtml+="<td>"+item.bonus18+"</td>"; 
            	 }
            	 if(v.bonus_column == "bonus19"){
            		 trHtml+="<td>"+item.bonus19+"</td>"; 
            	 }
            	 if(v.bonus_column == "bonus20"){
            		 trHtml+="<td>"+item.bonus20+"</td>"; 
            	 }
            	 if(v.bonus_column == "bonus21"){
            		 trHtml+="<td>"+item.bonus21+"</td>"; 
            	 }
            	 if(v.bonus_column == "bonus22"){
            		 trHtml+="<td>"+item.bonus22+"</td>"; 
            	 }
            	 if(v.bonus_column == "bonus23"){
            		 trHtml+="<td>"+item.bonus23+"</td>"; 
            	 }
            	 if(v.bonus_column == "bonus24"){
            		 trHtml+="<td>"+item.bonus25+"</td>"; 
            	 }
            	 if(v.bonus_column == "bonus25"){
            		 trHtml+="<td>"+item.bonus25+"</td>"; 
            	 }
            	 if(v.bonus_column == "bonus26"){
            		 trHtml+="<td>"+item.bonus26+"</td>"; 
            	 }
            	 if(v.bonus_column == "bonus27"){
            		 trHtml+="<td>"+item.bonus27+"</td>"; 
            	 }
            	 if(v.bonus_column == "bonus28"){
            		 trHtml+="<td>"+item.bonus28+"</td>"; 
            	 }
            	 if(v.bonus_column == "bonus29"){
            		 trHtml+="<td>"+item.bonus29+"</td>"; 
            	 }
            	 if(v.bonus_column == "bonus30"){
            		 trHtml+="<td>"+item.bonus30+"</td>"; 
            	 }
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			lodopExportExcel("resultPrint","职工奖金明细数据采集导出Excel","职工奖金明细数据采集列表.xls",true);
	    },true,manager);
		return;
	 }
// function print(){
// 	 var exportPara = {
// 			usePager:false,
// 		    b_year_month:$("#b_year_month").val(),
// 		    e_year_month:$("#e_year_month").val(),
// 		    dept_id:liger.get("dept").getValue().split(".")[0], 
// 	    	dept_no:liger.get("dept").getValue().split(".")[1],
// 	    	emp_id:liger.get("emp").getValue().split(".")[0],
// 	    	emp_no:liger.get("emp").getValue().split(".")[1], 
// 	    	emp_kind_code:liger.get("emp_kind_code").getValue()
// 		};
	
// 	$.ajax({
// 	   url:"queryCostEmpBonusDetail.do",
// 	   type:"post",
// 	   data:exportPara,
// 	   dataType:"JSON",
// 	   success:function(res){
		  
// 		   var data={
// 				   headers:[
// 							{ x: 0, y: 0, rowSpan: 1, colSpan: 1, displayName: "统计年", name: "acc_year",size:120},
// 							{ x: 1, y: 0, rowSpan: 1, colSpan: 1, displayName: "统计月", name: "acc_month",size:120},
// 							{ x: 2, y: 0, rowSpan: 1, colSpan: 1, displayName: "科室", name: "dept_name",size:120},
// 							{ x: 3, y: 0, rowSpan: 1, colSpan: 1, displayName: "职工", name: "emp_name" ,size:120 },
// 							{ x: 4, y: 0, rowSpan: 1, colSpan: 1, displayName: "职工分类", name: "emp_kind_name",size:120}
// 							],
// 				    rows: res.Rows
				   
// 		   }
// 		   viewPrint(data, "职工奖金明细数据采集");
// 	   },
// 	   error: function (res) {
// 				console.error(res);
// 			}
// });	 
	 
	
	 
	
	
	
// }	
        function print(){
	    	
if(grid.getData().length==0){
	    		
				$.ligerDialog.error("请先查询数据！");
				
				return;
			}
	
	    	var heads={
	    		//"isAuto":true,//系统默认，页眉显示页码
	    		"rows": [
		          {"cell":0,"value":"统计日期："+$("#b_year_month").val()+"至"+$("#e_year_month").val(),"colSpan":"5"}
	    	]};
	       var printPara={
	      		title: "职工奖金明细数据采集",//标题
	      		columns: JSON.stringify(grid.getPrintColumns()),//表头
	      		class_name: "com.chd.hrp.cost.service.CostEmpBonusDetailService",
	   			method_name: "queryCostEmpBonusDetailPrint",
	   			bean_name: "costEmpBonusDetailService"
	   			
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
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">统计年月：</td>
           <td align="left" class="l-table-edit-td"><input name="b_year_month" type="text" id="b_year_month" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
           <td align="right" class="l-table-edit-td"  style="padding-left:20px;">至&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
            <td align="left" class="l-table-edit-td"><input name="e_year_month" type="text" id="e_year_month" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
            
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">工资查询方案：</td>
            <td align="left" class="l-table-edit-td"><input name="scheme" type="text" id="scheme" ltype="text" /></td>
        </tr> 
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室：</td>
            <td align="left" class="l-table-edit-td"><input name="dept" type="text" id="dept" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">职工：</td>
            <td align="left" class="l-table-edit-td"><input name="emp" type="text" id="emp" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">职工分类：</td>
            <td align="left" class="l-table-edit-td"><input name="emp_kind_code" type="text" id="emp_kind_code" /></td>
        </tr> 
    </table>

	<div id="maingrid"></div>
	<div id="resultPrint" style="display:none">
	   	<table width="100%">
			   	<thead id="resultTable">
			   	</thead>
			   	<tbody></tbody>
	   	</table>
   	</div>
</body>
</html>
