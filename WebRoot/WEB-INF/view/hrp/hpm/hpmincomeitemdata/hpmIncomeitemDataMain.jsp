<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;" xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    $(function ()
    {
    	 $("#acct_yearm").ligerTextBox({ width:160 });
    	 autodate("#acct_yearm","yyyymm");
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
    });
    //查询
    function  query(){
    		queryNewIntHis();
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'acct_yearm',value:$("#acct_yearm").val()}); 
    	grid.options.parms.push({name:'dept_id',value:liger.get("dept_id").getValue().split(",")[0]});
    	grid.options.parms.push({name:'dept_no',value:liger.get("dept_id").getValue().split(",")[1]});
    	grid.options.parms.push({name:'income_item_code',value:liger.get("income_item_code").getValue()}); 
    	$("#resultPrint > table > tbody").html("");
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    
    function queryNewIntHis(){
    	
    	ajaxJsonObjectByUrl("addHisHrp.do?isCheck=false&acct_yearm="+$("#acct_yearm").val(),function (responseData){
    		if(responseData.state=="true"){
    			query();
    		}
    	});
    }
    
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '科室编码', name: 'dept_code', align: 'left',
                    	 render: function (rowdata, rowindex, value){
  							return "<a href='#' onclick=\"openUpdate('"+rowdata.dept_id+"','"+rowdata.dept_no+"','"+rowdata.income_item_code+"','"+rowdata.acct_year+"','"+rowdata.acct_month+"');\" >"+rowdata.dept_code+"</a>";
  			           }},
                     { display: '科室名称', name: 'dept_name', align: 'left'},
                     { display: '收入项目编码', name: 'income_item_code', align: 'left'},
                     { display: '收入项目名称', name: 'income_item_name', align: 'left'},
                     { display: '开单收入', name: 'order_money', align: 'left'},
                     { display: '开单计提', name: 'order_ret_money', align: 'left'},
                     { display: '执行收入', name: 'perform_money', align: 'left'},
                     { display: '执行计提', name: 'perform_ret_money', align: 'left'},
                     { display: '收入计提合计', name: 'income_tot_money', align: 'left'}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryHpmIncomeitemData.do',
                     width: '100%',height: '100%',   checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
    					{ text: '添加', id:'add', click: itemclick, icon:'add' },
    	                { line:true },
    	                { text: '删除', id:'delete', click: itemclick,icon:'delete' },
    	                { line:true },
    	                { text: '生成', id:'init', click: itemclick,icon:'bookpen' },
    	                { line:true },
    	                { text: '计算', id:'calculate', click: itemclick,icon:'add' },
    	                { line:true },
    	                { text: '下载导入模板', id:'downTemplate', click:itemclick,icon:'down' },
    	                { line:true },
    	                { text: '导入', id:'import', click: itemclick,icon:'up' },
    	                { line:true },
    	                { text: '打印', id:'print', click: printDate,icon:'print' }
    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
    					openUpdate(rowdata.dept_id,rowdata.dept_no,rowdata.income_item_code,rowdata.acct_year,rowdata.acct_month);//实际代码中temp替换主键
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
        formatTrueFalse();
    }
   
    function itemclick(item){ 
        if(item.id){
            switch (item.id){
                case "add":
              		$.ligerDialog.open({url: 'hpmIncomeitemDataAddPage.do?isCheck=false', height: 350,width: 500, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveIncomeitemData(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
              		return;
                case "modify":
                    return;
                case "delete":
                    var data = gridManager.getCheckedRows();
                    if (data.length == 0){
                    	$.ligerDialog.error('请选择行');
                    }else{
                        var checkIds =[];
                        $(data).each(function (){
                        	checkIds.push(this.dept_id+";"+this.dept_no+";"+this.income_item_code+";"+this.acct_year+";"+this.acct_month);//实际代码中temp替换主键
                        });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteHpmIncomeitemData.do",{checkIds:checkIds.toString()},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
                    return;
                case "calculate":
                	var data = gridManager.getCheckedRows();
                    if (data.length == 0){
                    	$.ligerDialog.error('请选择行');
                    }else{
	                	var checkIds =[];
	                    $(data).each(function (){
	                    	checkIds.push(this.dept_id+";"+this.dept_no+";"+this.income_item_code+";"+this.acct_year+";"+this.acct_month+";"+this.order_money+";"+this.perform_money);//实际代码中temp替换主键
	                    });
	                    ajaxJsonObjectByUrl("calculate.do?isCheck=false",{checkIds:checkIds.toString()},function (responseData){
	                		if(responseData.state=="true"){
	                			query();
	                		}
	                	});
                    }    
                	return;
                case "downTemplate":location.href = "downTemplateHpmIncomeitemData.do?isCheck=false";return;

                case "import":
                	//$.ligerDialog.open({url: 'hpmIncomeitemDataImportPage.do?isCheck=false', height: 300,width: 450, isResize:true, title:'导入'});
                	parent.$.ligerDialog.open({ url:'hrp/hpm/hpmincomeitemdata/hpmIncomeitemDataImportPage.do?isCheck=false',data:{columns : grid.columns, grid : grid}, height: 300,width: 450,title:'收入数据准备导入',modal:true,showToggle:false,showMax:true,
						showMin: false,isResize:true,parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
    				});
                	return;
                case "Word":
                case "PDF":
                case "TXT":
                case "XML":
                    $.ligerDialog.waitting('导出中，请稍候...');
                    setTimeout(function (){
                        $.ligerDialog.closeWaitting();
                        if (item.id == "Excel")
                            $.ligerDialog.success('导出成功');
                        else
                            $.ligerDialog.error('导出失败');
                    }, 1000);
                    return;
                case "init":
                	var acct_yearm=$("#acct_yearm").val();
                	if(acct_yearm==''){
                		$.ligerDialog.error('请选择核算年月');
                		return ;
                	}
                	var income_item_code = liger.get("income_item_code").getValue();
                	var dept_id = '';
                	var dept_no = '';
                	
                	dept_id = liger.get("dept_id").getValue() == "" ? "" :liger.get("dept_id").getValue().split(",")[0];
                	dept_no = liger.get("dept_id").getValue() == "" ? "" :liger.get("dept_id").getValue().split(",")[1];
                	
                	/* var formPara={
                			acct_yearm:acct_yearm,
                			dept_id:dept_id,
                			dept_no:dept_no,
                			income_item_code:income_item_code
                      }; */
                	
                	 var checkIds =[];
                    /*  $(data).each(function (){
                     }); */
                    checkIds.push($("#acct_yearm").val()+","+dept_id+","+dept_no+","+income_item_code);
                 	$.ligerDialog.open({url: 'hpmIncomeitemDataChoosePage.do?isCheck=false&checkIds='+checkIds, height: 200,width: 400, title:'生成',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.save(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
                     /* ajaxJsonObjectByUrl("getIncomeItemTargetValue.do?isCheck=false", formPara, function(responseData) {
                 		if (responseData.state == "false") {
      
                 		}else{
                 			$.ligerDialog.error('该月指标已审核，不能进行生成');
                 		}
                 	}); */
                	return;   
            }   
        }
        
    }
    
    function openUpdate(dept_id,dept_no,income_item_code,acct_year,acct_month){
    	var formPara={
    			acct_yearm:acct_year+""+acct_month,
    			dept_id:dept_id,
    			income_item_code:income_item_code
          };
      	  $.ligerDialog.open({ url: 'hpmIncomeitemDataUpdatePage.do?isCheck=false&dept_id='+dept_id+'&dept_no='+dept_no+'&income_item_code='+income_item_code+'&acct_year='+acct_year+'&acct_month='+acct_month,
        			data:{}, height: 350,width: 500, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveIncomeitemData(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] 
        			});
    	 /* ajaxJsonObjectByUrl("getIncomeItemTargetValue.do?isCheck=false", formPara, function(responseData) {
      		if (responseData.state == "false") {

      		}else{
      			$.ligerDialog.error('该月指标已审核，不能进行生成');
      		}
      	}) */
    
    }
    
    //字典下拉框
    function loadDict(){
		changeAcctYear();//核算年月绑定事件
	}
    
  //核算年月绑定事件
    function changeAcctYear(){
	  
	  	var para = {
	  		acct_yearm:$("#acct_yearm").val()
	  	};
    	autocomplete("#income_item_code","../../hpm/queryIncomeItemSeqTime.do?isCheck=false","id","text",true,true,para);
    	autocomplete("#dept_id","../queryDeptDictTime.do?isCheck=false","id","text",true,true,para);
    } 
    
    
  //打印数据
	 function printDate(){
	  	var dept_id = liger.get("dept_id").getValue().split(",")[0];
		//有数据直接打印
		if($("#resultPrint > table > tbody").html()!=""){
			lodopPrinterTable("resultPrint","收入数据准备打印.开始打印","收入数据准备列表",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备打印数据,请稍候...');
		
		ajaxJsonObjectByUrl("queryHpmIncomeitemData.do",{usePager:false,acct_yearm:$("#acct_yearm").val(),dept_id:dept_id,income_item_code:liger.get("income_item_code").getValue()},function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
				 trHtml+="<td>"+item.dept_id+"</td>";
				 trHtml+="<td>"+item.dept_name+"</td>";
				 trHtml+="<td>"+item.income_item_code+"</td>";
				 trHtml+="<td>"+item.income_item_name+"</td>";
				 trHtml+="<td>"+item.order_money+"</td>";
				 trHtml+="<td>"+item.order_ret_money+"</td>";
				 trHtml+="<td>"+item.perform_money+"</td>";
				 trHtml+="<td>"+item.perform_ret_money+"</td>";
				 trHtml+="<td>"+item.income_tot_money+"</td>";
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			//alert($("#resultPrint").html())
			lodopPrinterTable("resultPrint","收入数据准备打印.开始打印","收入数据准备列表",true);
	    },true,manager);
		return;
	 }
	
	 
	 //导出数据
	 function exportExcel(){
		 var dept_id = liger.get("dept_id").getValue().split(",")[0];
		//有数据直接导出
		if($("#resultPrint > table > tbody").html()!=""){
			lodopExportExcel("resultPrint","收入数据准备导出Excel","收入数据准备列表.xls",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');
		
		
		ajaxJsonObjectByUrl("queryHpmIncomeitemData.do",{usePager:false,acct_yearm:$("#acct_yearm").val(),dept_id:dept_id,income_item_code:liger.get("income_item_code").getValue()},function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
				 trHtml+="<td>"+item.dept_id+"</td>";
				 trHtml+="<td>"+item.dept_name+"</td>";
				 trHtml+="<td>"+item.income_item_code+"</td>";
				 trHtml+="<td>"+item.income_item_name+"</td>";
				 trHtml+="<td>"+item.order_money+"</td>";
				 trHtml+="<td>"+item.order_ret_money+"</td>";
				 trHtml+="<td>"+item.perform_money+"</td>";
				 trHtml+="<td>"+item.perform_ret_money+"</td>";
				 trHtml+="<td>"+item.income_tot_money+"</td>";
				 
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			lodopExportExcel("resultPrint","收入数据准备导出Excel","收入数据准备列表.xls",true);
	    },true,manager);
		return;
	 }
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">

		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">核算年月：</td>
			<td align="left" class="l-table-edit-td"><input
				name="acct_yearm" class="Wdate" type="text" id="acct_yearm"
				ltype="text" validate="{required:true,maxlength:20}"
				onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyyMM'})" onchange="changeAcctYear()"/></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室名称：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_id"
				type="text" id="dept_id" ltype="text"
				validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">收入项目：</td>
			<td align="left" class="l-table-edit-td"><input
				name="income_item_code" type="text" id="income_item_code"
				ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
		</tr>

	</table>

	<div id="maingrid"></div>

	<div id="resultPrint" style="display: none">
		<table width="100%">
			<thead>
				<tr>
					<th width="200">科室ID</th>
					<th width="200">科室名称</th>
					<th width="200">收入项目编码</th>
					<th width="200">收入项目名称</th>
					<th width="200">开单收入</th>
					<th width="200">开单计提</th>
					<th width="200">执行收入</th>
					<th width="200">执行计提</th>
					<th width="200">收入计提合计</th>
				</tr>
			</thead>
			<tbody></tbody>
		</table>
	</div>

</body>
</html>
