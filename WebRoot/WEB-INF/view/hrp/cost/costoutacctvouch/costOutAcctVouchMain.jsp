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
        
		var dept_dict = liger.get("dept_id").getValue();
        
        var cost_item = liger.get("cost_item_id").getValue();
        
		if(dept_dict !=null && dept_dict !=''){
    		
    		/* grid.options.parms.push({name:'dept_id',value:dept_dict.split(".")[0]}); 
    		
        	grid.options.parms.push({name:'dept_no',value:dept_dict.split(".")[1]});  */
        	
			grid.options.parms.push({name:'dept_code',value:dept_dict.split(".")[2]});
        	
    	}
		
		if(cost_item !=null && cost_item !=''){
    		
    		grid.options.parms.push({name:'cost_item_id',value:cost_item.split(".")[0]}); 
    		
        	grid.options.parms.push({name:'cost_item_no',value:cost_item.split(".")[1]}); 
        	
    	}
		grid.options.parms.push({name:'source_id',value:liger.get("source_id").getValue()}); 
		grid.options.parms.push({name:'dept_kind',value:liger.get("dept_kind").getValue()}); 
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '统计年月', name: 'year_month', align: 'left',render : function(rowdata, rowindex,value) {
                    	 if(rowdata.acc_year!="总"){
                    		return "<a href=javascript:openUpdate('"+rowdata.acc_year +"|"+rowdata.acc_month+"|"+ rowdata.out_id+"')>"+rowdata.acc_year+rowdata.acc_month+"</a>";
                    	 }else{
                    		 return rowdata.acc_year+rowdata.acc_month;
                    		 
                    		 
                    	 }
            			}
					 },
                     { display: '科室编码', name: 'dept_code', align: 'left',render : function(rowdata,rowindex,value) {
						 if(rowdata.is_last=1){
							 return rowdata.dept_code;
						 }
                     }
					 },
                     { display: '科室名称', name: 'dept_name', align: 'left',
						 render : function(rowdata, rowindex, value) {
								return formatSpace(rowdata.dept_name,
										rowdata.dept_level - 1);
							}
					 },
                     { display: '成本项目编码', name: 'cost_item_code', align: 'left'
					 },
                     { display: '成本项目名称', name: 'cost_item_name', align: 'left'
					 },
					 { display: '资金来源编码', name: 'source_code', align: 'left'
					 },
                     { display: '资金来源名称', name: 'source_name', align: 'left'
					 },
                     { display: '金额', name: 'amount', align: 'right',
						 render : function(rowdata, rowindex,
									value) {
								 return formatNumber(rowdata.amount,2,1);
							}
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryCostOutAcctVouch.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad:true,
                     selectRowButtonOnly:true,checkBoxDisplay:f_checkBoxDisplay,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
    					{ text: '添加', id:'add', click: itemclick, icon:'add' },
    	                { line:true },
    	                { text: '删除', id:'delete', click: itemclick,icon:'delete' },
						{ line:true }, 
						{ text: '按月删除', id:'remove', click: deleteMonthly,icon:'delete' },
						{ line:true }, 
//     	                { text: '导出Excel', id:'export', click: exportExcel,icon:'pager' },
// 		                { line:true },
		                { text: '打印', id:'print', click: print,icon:'print' },
		                { line:true },
		                /* { text: '下载导入模板', id:'downTemplate', click:itemclick,icon:'down' },
		                { line:true }, */
		                { text: '导入', id:'import', click: itemclick,icon:'up' },
		                { line:true },
		                { text: '校验会计数据', id:'check', click: check,icon:'add' },
		                { line:true },
		                { text: '采集会计数据', id:'impAcc', click: itemclick,icon:'up' }
    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{ 
    					if(rowdata.year_month == null){
    						$.ligerDialog.warn('请选择行'); 
    						return false ;
    					}  
						openUpdate(
								rowdata.acc_year   + "|" + 
								rowdata.acc_month   + "|" + 
								rowdata.out_id
							);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

    function f_checkBoxDisplay(rowdata){
      	 if (rowdata.acc_year == "总" && rowdata.acc_month == "计")
   			    return false;
   		      return true;
       }

    
    function itemclick(item){ 
        if(item.id)
        {
            switch (item.id)
            {
                case "add":
              		$.ligerDialog.open({url: 'costOutAcctVouchAddPage.do?isCheck=false', height: 300,width: 500, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveCostOutAcctVouch(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
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
							this.out_id
							)
                        });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteCostOutAcctVouch.do",{ParamVo : ParamVo.toString()},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
                    return;
                case "impAcc":
                	$.ligerDialog.open({url: 'costOutAcctVouchCollectPage.do?isCheck=false', height: 300,width: 300, title:'采集',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.save(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
                	return;
				case "import":
					//公用import.jsp页面使用
					var para={
					    "column": [
					        {
					            "name": "acc_year",
					            "display": "统计年度",
					            "width": "200",
					            "require":true
					        },
					        {
					            "name": "acc_month",
					            "display": "统计月份",
					            "width": "200",
					            "require":true
					        },
					        {
					            "name": "dept_id",
					            "display": "科室编码",
					            "width": "200",
					            "require":true
					        },
					        {
					            "name": "dept_name",
					            "display": "科室名称",
					            "width": "200",
					            "require":true
					        },{
					            "name": "cost_item_id",
					            "display": "成本项目编码",
					            "width": "200",
					            "require":true
					        },
					        {
					            "name": "cost_item",
					            "display": "成本名称",
					            "width": "200",
					            "require":true
					        },
					        {
					            "name": "source_id",
					            "display": "资金来源编码",
					            "width": "200"
					        },
					        {
					            "name": "source_name",
					            "display": "资金来源名称",
					            "width": "200"
					        },
					        {
					            "name": "money",
					            "display": "金额",
					            "width": "200",
					            "require":true
					        }
					       
					        
					    ]/* ,
					    isUpdate:true */
					};
					importSpreadView("hrp/cost/costoutacctvouch/costOutAcctVouchImportPage.do?isCheck=false",para); 

                	//$.ligerDialog.open({url: 'costOutAcctVouchImportPage.do?isCheck=false', height: 500,width: 800, title:'导入',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true });
                	return;
                case "export":
                	return;
                case "downTemplate":
                	location.href = "downTemplate.do?isCheck=false";
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

    function deleteMonthly(){
	    	if ($("#b_year_month").val() == '' || $("#e_year_month").val() == '') {
	    		$.ligerDialog.question('统计年月不能为空')
	    		return false;
	    	}
	
	    	$.ligerDialog.confirm('是否确认删除', function(yes) {
	    		if (yes) {
	    			var formPara = {
						b_year_month: $("#b_year_month").val(),
						e_year_month: $("#e_year_month").val(),
	    			};
	    			ajaxJsonObjectByUrl("deleteMonthlyCostOutAcctVouch.do", formPara, function(responseData) {
	    				if (responseData.state == "true") {
	    					query();
	    				}
	    			});
	    		}
	    	});

        }


    function check(){
    	parent.$.ligerDialog.open({
			title: '校验',
			height: 550,
			width: 1000,
			url: 'hrp/cost/costoutacctvouch/costOutAcctVouchCheckPage.do?isCheck=false',
			modal: true, showToggle: false, showMax: true, showMin:false, isResize: true, top: 10,
			parentframename: window.name,
		});
    }
    function openUpdate(obj){
    	
		var vo = obj.split("|");
		var parm = 
			"acc_year="+vo[0]   +"&"+ 
			"acc_month="+vo[1]   +"&"+ 
			"out_id="+vo[2]
    	$.ligerDialog.open({ url : 'costOutAcctVouchUpdatePage.do?isCheck=false&' + parm,data:{}, height: 500,width: 500, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveCostOutAcctVouch(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    }
    function loadDict(){
    	 $("#b_year_month").ligerTextBox({ width:160 });
	     $("#e_year_month").ligerTextBox({ width:160});
	   //字典下拉框
			
			 autodate("#b_year_month","yyyyMM");
			 autodate("#e_year_month","yyyyMM");
            //字典下拉框
    	autocomplete("#dept_id","../queryDeptDictCode.do?isCheck=false","id","text",true,true);
    	autocomplete("#cost_item_id","../queryItemDictNo.do?isCheck=false","id","text",true,true); 
    	autocomplete("#source_id","../querySourceArrt.do?isCheck=false","id","text",true,true); 
    	autocomplete("#dept_kind","../queryCostDeptKindDict.do?isCheck=false","id","text",true,true);
         }  
    
  //打印数据
	 function printDate(){
		//有数据直接打印
		if($("#resultPrint > table > tbody").html()!=""){
			lodopPrinterTable("resultPrint","开始打印","科室支出总账采集",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备打印数据,请稍候...');
		
		var cost_item = liger.get("cost_item_id").getValue();
		
		var dept_dict = liger.get("dept_id").getValue();
		
		var printPara={
				
			usePager:false,

			b_year_month:$("#b_year_month").val(),
			e_year_month:$("#e_year_month").val(),
            
           dept_id:(dept_dict !=null && dept_dict !='')?dept_dict.split(".")[0]:'',
                   
           dept_no:(dept_dict !=null && dept_dict !='')?dept_dict.split(".")[1]:'',
            
           cost_item_id:(cost_item !=null && cost_item !='')?cost_item.split(".")[0]:'',
                   
           cost_item_no:(cost_item !=null && cost_item !='')?cost_item.split(".")[1]:''
            
         };
		ajaxJsonObjectByUrl("queryCostOutAcctVouch.do",printPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
				 trHtml+="<td>"+item.acc_year+item.acc_month+"</td>"; 
                     trHtml+="<td>"+item.dept_code+"</td>"; 
                     trHtml+="<td>"+item.dept_name+"</td>"; 
                     trHtml+="<td>"+item.cost_item_code+"</td>"; 
                     trHtml+="<td>"+item.cost_item_name+"</td>"; 
                     trHtml+="<td>"+item.amount+"</td>";
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			//alert($("#resultPrint").html())
			lodopPrinterTable("resultPrint","开始打印","科室支出总账采集",true);
	    },true,manager);
		return;
	 }
	
	 
	 //导出数据
	 function exportExcel(){
		//有数据直接导出
		if($("#resultPrint > table > tbody").html()!=""){
			lodopExportExcel("resultPrint","导出Excel","科室支出总账采集.xls",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');

		var cost_item = liger.get("cost_item_id").getValue();
		
		var dept_dict = liger.get("dept_id").getValue();
		
		var exportPara={
				
			usePager:false,

			b_year_month:$("#b_year_month").val(),
			e_year_month:$("#e_year_month").val(),
            
           dept_id:(dept_dict !=null && dept_dict !='')?dept_dict.split(".")[0]:'',
                   
           dept_no:(dept_dict !=null && dept_dict !='')?dept_dict.split(".")[1]:'',
            
           cost_item_id:(cost_item !=null && cost_item !='')?cost_item.split(".")[0]:'',
                   
           cost_item_no:(cost_item !=null && cost_item !='')?cost_item.split(".")[1]:''
            
         };
		ajaxJsonObjectByUrl("queryCostOutAcctVouch.do",exportPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
				 trHtml+="<td>"+item.acc_year+item.acc_month+"</td>"; 
                  trHtml+="<td>"+item.dept_code+"</td>"; 
                  trHtml+="<td>"+item.dept_name+"</td>"; 
                  trHtml+="<td>"+item.cost_item_code+"</td>"; 
                  trHtml+="<td>"+item.cost_item_name+"</td>"; 
                  trHtml+="<td>"+item.amount+"</td>";
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			lodopExportExcel("resultPrint","导出Excel","科室支出总账采集.xls",true);
	    },true,manager);
		return;
	 }	
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
  	 	      		title: "科室支出总账采集",//标题
  	 	      		columns: JSON.stringify(grid.getPrintColumns()),//表头
  	 	      		class_name: "com.chd.hrp.cost.service.CostOutAcctVouchService",
  	 	   			method_name: "queryCostOutAcctVouchPrint",
  	 	   			bean_name: "costOutAcctVouchService",
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
        </tr> 
	
        <tr>
           <td align="right" class="l-table-edit-td"  style="padding-left:20px;">统计年月：</td>
           <td align="left" class="l-table-edit-td"><input name="b_year_month" type="text" id="b_year_month" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
           <td align="right" class="l-table-edit-td"  style="padding-left:20px;">至&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
            <td align="left" class="l-table-edit-td"><input name="e_year_month" type="text" id="e_year_month" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室编码：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" /></td>
           
        </tr> 
        <tr>
         <td align="right" class="l-table-edit-td"  style="padding-left:20px;">成本项目：</td>
            <td align="left" class="l-table-edit-td"><input name="cost_item_id" type="text" id="cost_item_id" /></td>
        <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资金来源：</td>
        <td align="left" class="l-table-edit-td"><input name="source_id" type="text" id="source_id" /></td>
        <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室类别：</td>
        <td align="left" class="l-table-edit-td"><input name="dept_kind" type="text" id="dept_kind" /></td>
        </tr>
    </table>

	<div id="maingrid"></div>
	<div id="resultPrint" style="display:none">
	   	<table width="100%">
			   	<thead>
				<tr>
					<th width="200">统计年月</th>
					<th width="200">科室编码</th>
					<th width="200">科室名称</th>
					<th width="200">成本项目编码</th>
					<th width="200">成本项目名称</th>
					<th width="200">金额</th>
				   	</tr>
			   	</thead>
			   	<tbody></tbody>
	   	</table>
   	</div>
</body>
</html>
