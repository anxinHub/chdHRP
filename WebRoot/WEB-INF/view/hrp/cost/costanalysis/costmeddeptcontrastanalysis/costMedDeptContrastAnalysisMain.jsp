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
    	$("#acct_year").ligerTextBox({ width:160 });
    	$("#cost_hos").ligerTextBox({ width:160 });
    	$("#dept_id").ligerTextBox({ width:70 });
    	$("#server_dept_id").ligerTextBox({ width:70 });
    });
    //查询
    function  query(){
	grid.options.parms=[];
		
		grid.options.newPage=1;
    	//根据表字段进行添加查询条件
    	
    	if($("#first_year").val()==""||$("#last_year").val()==""){
    		
    		$.ligerDialog.error('期间不能为空!');
    		
    		return ;
    		
    	}
    
		grid.options.parms.push({name:'first_year',value:$("#first_year").val()}); 
		
		grid.options.parms.push({name:'last_year',value:$("#last_year").val()}); 
		
		grid.options.parms.push({name:'dept_type',value:liger.get("dept_type").getValue()}); 
		
		grid.options.parms.push({name:'dept_nature',value:liger.get("dept_nature").getValue()}); 
    
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
    		columns: [ 
                      { display: '科室编码', name: 'dept_code', align: 'left'
 					 },
 					 { display: '科室名称', name: 'dept_name', align: 'left'
 					 },
                      { display: '本期',columns:[{
                     	 display: '收入', name: 'money1', align: 'left'
                      },{
                     	 display: '成本', name: 'amount1', align: 'left'
                      },{
                     	 display: '收益', name: 'shoyi1', align: 'left',render:function(rowdata){
                     		 return rowdata.money1-rowdata.amount1>0?rowdata.money1-rowdata.amount1:'0';;
                     	 }
                      },{
                     	 display: '人均收益', name: 'server_dept_name', align: 'left',render:function(rowdata){
                     		 return (rowdata.money1-rowdata.amount1)/100>0?(rowdata.money1-rowdata.amount1)/100:'0';
                     	 }
                      }]
 					 },
 					 { display: '上期',columns:[{
                     	 display: '收入', name: 'money2', align: 'left'
                     },{
                    	 display: '成本', name: 'amount2', align: 'left'
                     },{
                    	 display: '收益', name: 'shoyi2', align: 'left',render:function(rowdata){
                     		 return rowdata.money2-rowdata.amount2>0?rowdata.money2-rowdata.amount2:'0';
                     	 }
                     },{
                    	 display: '人均收益', name: 'server_dept_name', align: 'left',render:function(rowdata){
                    		 return (rowdata.money2-rowdata.amount2)/100>0?(rowdata.money2-rowdata.amount2)/100:'0';
                     	 }
                     }]
 					 },	
 					 { display: '本期平均',columns:[{
                     	 display: '收入', name: 'money3', align: 'left'
                     },{
                    	 display: '成本', name: 'amount3', align: 'left'
                     },{
                    	 display: '收益', name: 'shoyi3', align: 'left',render:function(rowdata){
                     		 return rowdata.money3-rowdata.amount3>0?rowdata.money3-rowdata.amount3:'0';;
                     	 }
                     },{
                    	 display: '人均收益', name: 'server_dept_name', align: 'left',render:function(rowdata){
                    		 return (rowdata.money3-rowdata.amount3)/100>0?(rowdata.money3-rowdata.amount3)/100:'0';
                     	 }
                     }]
 					 }
                      ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryCostMedDeptContrastAnalysis.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
    	                { text: '导出Excel', id:'export', click: exportExcel,icon:'pager' },
		                { line:true },
		                { text: '打印', id:'print', click: printDate,icon:'print' },
		                { line:true }
    				]}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

    function itemclick(item){ 
        if(item.id)
        {
            switch (item.id)
            {
                case "add":
              		$.ligerDialog.open({url: 'costAssDetailAddPage.do?isCheck=false', height: 500,width: 500, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveCostAssDetail(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
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
							this.source 
							)
                        });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteCostAssDetail.do",{ParamVo : ParamVo},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
                    return;
				case "import":
                	$.ligerDialog.open({url: 'costAssDetailImportPage.do?isCheck=false', height: 500,width: 800, title:'导入',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true });
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
    /* function openUpdate(obj){
    	
		var vo = obj.split("|");
		var parm = 
			"group_id="+vo[0]   +"&"+ 
			"hos_id="+vo[1]   +"&"+ 
			"copy_code="+vo[2]   +"&"+ 
			"year_month="+vo[3]   +"&"+ 
			"dept_id="+vo[4]   +"&"+ 
			"dept_no="+vo[5]   +"&"+ 
			"server_dept_id="+vo[6]   +"&"+ 
			"server_dept_no="+vo[7]   +"&"+ 
			"cost_item_id="+vo[8]   +"&"+ 
			"cost_item_no="+vo[9]   +"&"+ 
			"source="+vo[10] 
    	$.ligerDialog.open({ url : 'costAssDetailUpdatePage.do?isCheck=false&' + parm,data:{}, height: 500,width: 500, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveCostAssDetail(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    } */
    function loadDict(){
            //字典下拉框
        var dept_para = {kind_code : "('03','05')"};
        autocomplete("#dept_nature","../../queryCostDeptNature.do?isCheck=false","id","text",true,true,dept_para);
        var dept_para = {kind_code : "('01','02')"};
        autocomplete("#dept_type","../../queryCostDeptKindDict.do?isCheck=false","id","text",true,true,dept_para);
        autocomplete("#dept_level","../../queryDeptLevel.do?isCheck=false","id","text",true,true); 
         }  
    
  //打印数据
	 function printDate(){
		//有数据直接打印
		if($("#resultPrint > table > tbody").html()!=""){
			lodopPrinterTable("resultPrint","开始打印","列表",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备打印数据,请稍候...');

		var cost_item = liger.get("cost_item_id").getValue();
		
		var dept_dict = liger.get("dept_id").getValue();
		
		var server_dept_dict = liger.get("server_dept_id").getValue();
		
		var printPara={
				
			usePager:false,
			
			year_month:$("#year_month").val(),
            
	           dept_id:(dept_dict !=null && dept_dict !='')?dept_dict.split(".")[0]:'',
	      	            
	           dept_no:(dept_dict !=null && dept_dict !='')?dept_dict.split(".")[1]:'',
	        		   
	           server_dept_id:(server_dept_dict !=null && server_dept_dict !='')?server_dept_dict.split(".")[0]:'',
	   	      	            
	          server_dept_no:(server_dept_dict !=null && server_dept_dict !='')?server_dept_dict.split(".")[1]:'',	   
	            
	           cost_item_id:(cost_item !=null && cost_item !='')?cost_item.split(".")[0]:'',
	   	            
	    	   cost_item_no:(cost_item !=null && cost_item !='')?cost_item.split(".")[1]:'',
	            
	           source_id:liger.get("source").getValue()
            
         };
		ajaxJsonObjectByUrl("queryCostAssDetail.do",printPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
					 trHtml+="<td>"+item.year_month+"</td>"; 
	                 trHtml+="<td>"+item.dept_code+"</td>"; 
	                 trHtml+="<td>"+item.dept_name+"</td>"; 
	                 trHtml+="<td>"+item.server_dept_code+"</td>"; 
	                 trHtml+="<td>"+item.server_dept_name+"</td>"; 
	                 trHtml+="<td>"+item.cost_item_code+"</td>"; 
	                 trHtml+="<td>"+item.cost_item_name+"</td>"; 
	                 trHtml+="<td>"+item.source_code+"</td>";
	                 trHtml+="<td>"+item.source_name+"</td>";
                     trHtml+="<td>"+item.two_amount+"</td>"; 
                     trHtml+="<td>"+item.one_amount+"</td>"; 
                     trHtml+="<td>"+item.ass_amount+"</td>";
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

		var cost_item = liger.get("cost_item_id").getValue();
		
		var dept_dict = liger.get("dept_id").getValue();
		
		var server_dept_dict = liger.get("server_dept_id").getValue();
		
		var printPara={
				
			usePager:false,
			
			year_month:$("#year_month").val(),
            
	           dept_id:(dept_dict !=null && dept_dict !='')?dept_dict.split(".")[0]:'',
	      	            
	           dept_no:(dept_dict !=null && dept_dict !='')?dept_dict.split(".")[1]:'',
	        		   
	           server_dept_id:(server_dept_dict !=null && server_dept_dict !='')?server_dept_dict.split(".")[0]:'',
	   	      	            
	          server_dept_no:(server_dept_dict !=null && server_dept_dict !='')?server_dept_dict.split(".")[1]:'',	   
	            
	           cost_item_id:(cost_item !=null && cost_item !='')?cost_item.split(".")[0]:'',
	   	            
	    	   cost_item_no:(cost_item !=null && cost_item !='')?cost_item.split(".")[1]:'',
	            
	           source_id:liger.get("source").getValue()
            
         };
		ajaxJsonObjectByUrl("queryCostAssDetail.do",exportPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
					 trHtml+="<td>"+item.year_month+"</td>"; 
	                 trHtml+="<td>"+item.dept_code+"</td>"; 
	                 trHtml+="<td>"+item.dept_name+"</td>"; 
	                 trHtml+="<td>"+item.server_dept_code+"</td>"; 
	                 trHtml+="<td>"+item.server_dept_name+"</td>"; 
	                 trHtml+="<td>"+item.cost_item_code+"</td>"; 
	                 trHtml+="<td>"+item.cost_item_name+"</td>"; 
	                 trHtml+="<td>"+item.source_code+"</td>";
	                 trHtml+="<td>"+item.source_name+"</td>";
                     trHtml+="<td>"+item.two_amount+"</td>"; 
                     trHtml+="<td>"+item.one_amount+"</td>"; 
                     trHtml+="<td>"+item.ass_amount+"</td>";
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
            </td>
            <td align="right" class="l-table-edit-td"  style="padding-left:-20px;" colspan="4"> 期间：</td>
            <td align="left"  colspan="3"><input name="dept_id" type="text" id="first_year" class="Wdate"  onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy'})"/></td>
            <td align="left" class="l-table-edit-td">至：</td>
            <td align="left" ><input name="server_dept_id" type="text" id="last_year" class="Wdate"  onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy'})"/></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室类型：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_type" type="text" id="dept_type" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室性质：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_nature" type="text" id="dept_nature" /></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室级次：</td>
            <td align="left" class="l-table-edit-td"  colspan="8"><input name="dept_level" type="text" id="dept_level" /></td>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">成本级次：</td>
            <td align="left" class="l-table-edit-td">
           <select name="cost_hos" id="cost_hos">
            <option value="1">医疗成本</option>
            <option value="2">医疗全成本</option>
            <option value="3">医院全成本</option>
            </select>
            </td>
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
					<th width="200">受益科室编码</th>
					<th width="200">受益科室名称</th>
					<th width="200">成本项目编码</th>
					<th width="200">成本项目名称</th>
					<th width="200">资金来源编码</th>
					<th width="200">资金来源名称</th>
					<th width="200">受益科室二级成本</th>
					<th width="200">受益科室一级成本</th>
					<th width="200">医辅分摊成本</th>
				   	</tr>
			   	</thead>
			   	<tbody></tbody>
	   	</table>
   	</div>
</body>
</html>
