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
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    		grid.options.parms.push({name:'b_year_month',value:$("#b_year_month").val()}); 
    	grid.options.parms.push({name:'e_year_month',value:$("#e_year_month").val()}); 
            
    		grid.options.parms.push({name:'para_code',value:liger.get("para_code").getValue()}); 
        	
        	autocomplete("#server_dept_id","../queryDeptDictNo.do?isCheck=false","id","text",true,true);
            
        	autocomplete("#cost_item_id","../queryItemDictNo.do?isCheck=false","id","text",true,true);
        	
    		var dept = liger.get("dept_id").getValue();
        	
    		if(dept !=null && dept !=''){
        		
        		grid.options.parms.push({name:'dept_id',value:dept.split(".")[0]}); 
        		
            	grid.options.parms.push({name:'dept_no',value:dept.split(".")[1]});
            }
        	
    		var server_dept = liger.get("server_dept_id").getValue();
        	
    		if(server_dept !=null && server_dept !=''){
        		
        		grid.options.parms.push({name:'server_dept_id',value:server_dept.split(".")[0]}); 
        		
            	grid.options.parms.push({name:'server_dept_id',value:server_dept.split(".")[1]});
            }
    		
    		var cost_item = liger.get("cost_item_id").getValue();
        	
    		if(cost_item !=null && cost_item !=''){
        		
        		grid.options.parms.push({name:'cost_item_id',value:cost_item.split(".")[0]}); 
        		
            	grid.options.parms.push({name:'cost_item_no',value:cost_item.split(".")[1]});
            }

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '统计年月', name: 'year_month', align: 'left',
                    	 render : function (rowdata, rowindex,value)  
							{
           		
           		 return rowdata.acc_year+rowdata.acc_month;
						}
  					 },
  					{ display: '服务科室编码', name: 'dept_code', align: 'left'
					 },
					 { display: '服务科室名称', name: 'dept_name', align: 'left'
					 },
                   { display: '受益科室编码', name: 'server_dept_code', align: 'left'
					 },
					 { display: '受益科室名称', name: 'server_dept_name', align: 'left'
					 },
                   { display: '成本项目编码', name: 'cost_item_code', align: 'left'
					 },
					 { display: '成本项目名称', name: 'cost_item_name', align: 'left'
					 },
                    { display: '分摊参数', name: 'para_code', align: 'left'
					 },
					 { display: '分摊参数名称', name: 'para_name', align: 'left'
					 },
                     { display: '受益科室参数值', name: 'para_value', align: 'left'
					 },
                     { display: '总参数值', name: 'total_value', align: 'left'
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryCostParaMedSetData.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
    					{ text: '采集', id:'add', click: itemclick, icon:'add' },
    	                { line:true },
    	                { text: '导出Excel', id:'export', click: exportExcel,icon:'pager' },
		                { line:true },
		                { text: '打印', id:'print', click: printDate,icon:'print' },
		                { line:true }
    				]}/* ,
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.year_month   + "|" + 
								rowdata.dept_id   + "|" + 
								rowdata.dept_no   + "|" + 
								rowdata.server_dept_id   + "|" + 
								rowdata.server_dept_no   + "|" + 
								rowdata.cost_item_id   + "|" + 
								rowdata.cost_item_no   + "|" + 
								rowdata.para_code 
							);
    				}  */
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

    function itemclick(item){ 
        if(item.id)
        {
            switch (item.id)
            {
                case "add":
                	 var acc_year = $("#b_year_month").val().substring(0,4);
                 	
                 	var acc_month = $("#b_year_month").val().substring(4,7);
                	if(year_month==''){
                		$.ligerDialog.error('请选择统计年月');
                	}else{
                	$.ligerDialog.confirm('确定采集数据?', function (yes){
                    	if(yes){
                        	ajaxJsonObjectByUrl("addCostParaMedSetData.do?isCheck=false",{'acc_year' : acc_year,'acc_month' : acc_month},function (responseData){
                        		if(responseData.state=="true"){
                        			query();
                        		}
                        	});
                    	}
                    }); 
                	}
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
							this.year_month   +"@"+ 
							this.dept_id   +"@"+ 
							this.dept_no   +"@"+ 
							this.server_dept_id   +"@"+ 
							this.server_dept_no   +"@"+ 
							this.cost_item_id   +"@"+ 
							this.cost_item_no   +"@"+ 
							this.para_code 
							)
                        });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteCostParaMedSetData.do",{ParamVo : ParamVo},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
                    return;
				case "import":
                	$.ligerDialog.open({url: 'costParaMedSetDataImportPage.do?isCheck=false', height: 500,width: 800, title:'导入',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true });
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
    function openUpdate(obj){
    	
		var vo = obj.split("|");
		var parm = 
				"year_month="+vo[0]   +"&"+ 
				"dept_id="+vo[1]   +"&"+ 
				"dept_no="+vo[2]   +"&"+ 
				"server_dept_id="+vo[3]   +"&"+ 
				"server_dept_no="+vo[4]   +"&"+ 
				"cost_item_id="+vo[5]   +"&"+ 
				"cost_item_no="+vo[6]   +"&"+ 
				"para_code="+vo[7] ;
    	$.ligerDialog.open({ url : 'costParaMedSetDataUpdatePage.do?isCheck=false&' + parm,data:{}, height: 500,width: 500, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveCostParaMedSetData(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    }
    function loadDict(){
    	/*
	    	2016/11/4 lxj 
	    	增加参数,过滤科室
    	*/
    	
		var dept_para = {type_code : "03"};
    	
    	var dept_param = {type_code : "03,04"};
            //字典下拉框
    	 autocomplete("#dept_id","../queryDeptDictNo.do?isCheck=false","id","text",true,true,dept_para);
         
      	autocomplete("#server_dept_id","../queryDeptDictNo.do?isCheck=false","id","text",true,true,dept_param);
              
      	autocomplete("#cost_item_id","../queryItemDictNo.do?isCheck=false","id","text",true,true);
      	
      	autocomplete("#para_code","../queryDeptParaDict.do?isCheck=false","id","text",true,true);
      	$("#b_year_month").ligerTextBox({ width:160 });
	     $("#e_year_month").ligerTextBox({ width:160});
	 		 
			 autodate("#b_year_month","yyyyMM");
			 autodate("#e_year_month","yyyyMM");
         }  
    
  //打印数据
	 function printDate(){
	  
		 $("#resultPrint > table > tbody").empty();
		//有数据直接打印
		//if($("#resultPrint > table > tbody").html()!=""){
			//lodopPrinterTable("resultPrint","开始打印","列表",true);
			//return;
		//}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备打印数据,请稍候...');
		
		var printPara={
			usePager:false,
            
			b_year_month:$("#b_year_month").val(),
			e_year_month:$("#e_year_month").val(),
            
           dept_id:$("#dept_id").val(),
            
           dept_no:$("#dept_no").val(),
            
           server_dept_id:$("#server_dept_id").val(),
            
           server_dept_no:$("#server_dept_no").val(),
            
           cost_item_id:$("#cost_item_id").val(),
            
           cost_item_no:$("#cost_item_no").val(),
            
           para_code:$("#para_code").val(),
            
           para_value:$("#para_value").val(),
            
           total_value:$("#total_value").val()
            
         };
		ajaxJsonObjectByUrl("queryCostParaMedSetData.do",printPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
                     trHtml+="<td>"+item.acc_year+item.acc_month+"</td>"; 
                     trHtml+="<td>"+item.dept_code+"</td>"; 
                     trHtml+="<td>"+item.dept_name+"</td>"; 
                     trHtml+="<td>"+item.server_dept_code+"</td>"; 
                     trHtml+="<td>"+item.server_dept_name+"</td>"; 
                     trHtml+="<td>"+item.cost_item_code+"</td>"; 
                     trHtml+="<td>"+item.cost_item_name+"</td>"; 
                     trHtml+="<td>"+item.para_code+"</td>"; 
                     trHtml+="<td>"+item.para_name+"</td>";
                     trHtml+="<td>"+item.para_value+"</td>"; 
                     trHtml+="<td>"+item.total_value+"</td>";
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			//alert($("#resultPrint").html())
			lodopPrinterTable("resultPrint","开始打印","列表",true);
	    },true,manager);
		return;
	 }
	
	 
	 //导出数据
	 function exportExcel(){
		//有数据直接导出
		if($("#resultPrint > table > tbody").html()!=""){
			lodopExportExcel("resultPrint","导出Excel","列表.xls",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');
		
		var exportPara={
			usePager:false,
			b_year_month:$("#b_year_month").val(),
			e_year_month:$("#e_year_month").val(),
           dept_id:$("#dept_id").val(),
           dept_no:$("#dept_no").val(),
           server_dept_id:$("#server_dept_id").val(),
           server_dept_no:$("#server_dept_no").val(),
           cost_item_id:$("#cost_item_id").val(),
           cost_item_no:$("#cost_item_no").val(),
           para_code:$("#para_code").val(),
           para_value:$("#para_value").val(),
           total_value:$("#total_value").val()
         };
		ajaxJsonObjectByUrl("queryCostParaMedSetData.do",exportPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
				 trHtml+="<td>"+item.acc_year+item.acc_month+"</td>"; 
                 trHtml+="<td>"+item.dept_code+"</td>"; 
                 trHtml+="<td>"+item.dept_name+"</td>"; 
                 trHtml+="<td>"+item.server_dept_code+"</td>"; 
                 trHtml+="<td>"+item.server_dept_name+"</td>"; 
                 trHtml+="<td>"+item.cost_item_code+"</td>"; 
                 trHtml+="<td>"+item.cost_item_name+"</td>"; 
                 trHtml+="<td>"+item.para_code+"</td>"; 
                 trHtml+="<td>"+item.para_name+"</td>";
                 trHtml+="<td>"+item.para_value+"</td>"; 
                 trHtml+="<td>"+item.total_value+"</td>";
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			lodopExportExcel("resultPrint","导出Excel","列表.xls",true);
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
        </tr> 
	<tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">统计年月：</td>
           <td align="left" class="l-table-edit-td"><input name="b_year_month" type="text" id="b_year_month" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
           <td align="right" class="l-table-edit-td"  style="padding-left:20px;">至&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
            <td align="left" class="l-table-edit-td"><input name="e_year_month" type="text" id="e_year_month" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">服务科室：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" /></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">受益科室：</td>
            <td align="left" class="l-table-edit-td"><input name="server_dept_id" type="text" id="server_dept_id" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">成本项目ID：</td>
            <td align="left" class="l-table-edit-td"><input name="cost_item_id" type="text" id="cost_item_id" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">分摊参数：</td>
            <td align="left" class="l-table-edit-td"><input name="para_code" type="text" id="para_code" /></td>
        </tr> 
    </table>

	<div id="maingrid"></div>
	<div id="resultPrint" style="display:none">
	   	<table width="100%">
			   	<thead>
				<tr>
					<th width="200">统计年月</th>
					<th width="200">服务科室编码</th>
					<th width="200">服务科室名称D</th>
					<th width="200">受益科室编码</th>
					<th width="200">受益科室名称</th>
					<th width="200">成本项目编码</th>
					<th width="200">成本项目名称</th>
					<th width="200">分摊参数</th>
					<th width="200">分摊参数名称</th>
					<th width="200">受益科室参数值</th>
					<th width="200">总参数值</th>
				   	</tr>
			   	</thead>
			   	<tbody></tbody>
	   	</table>
   	</div>
</body>
</html>
