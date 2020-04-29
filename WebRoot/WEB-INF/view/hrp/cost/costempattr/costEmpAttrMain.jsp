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

    	grid.options.parms.push({name:'title_code',value:liger.get("title_code").getValue()}); 
    	grid.options.parms.push({name:'emp_kind_code',value:liger.get("emp_kind_code").getValue()}); 
    	var emp_item = liger.get("emp_code").getValue();
    	if(emp_item !=null && emp_item !=''){
    		
			grid.options.parms.push({name:'emp_id',value:emp_item.split(".")[0]}); 
    		
        	grid.options.parms.push({name:'emp_no',value:emp_item.split(".")[1]}); 
    		
    	}
    	//加载查询条件
    	grid.loadData(grid.where);
     }
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
					 { display: '职工编码', name: 'emp_code', align: 'left',render : function(rowdata, rowindex,value) {
							return "<a href=javascript:openUpdate('"+rowdata.emp_id+ "|" + rowdata.emp_no   +"|" + rowdata.emp_code   + "')>"+rowdata.emp_code+"</a>";
					}
					 },
                     { display: '职工姓名', name: 'emp_name', align: 'left'
					 },
                     { display: '职称编码', name: 'title_code', align: 'left'
					 },
					 { display: '职称', name: 'title_name', align: 'left'
					 },
                     { display: '分类编码', name: 'emp_kind_code', align: 'left'
					 },
					 { display: '分类名称', name: 'emp_kind_name', align: 'left'
					 },
                     { display: '备注', name: 'note', align: 'left'
					 },
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryCostEmpAttr.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
    					{ text: '添加', id:'add', click: itemclick, icon:'add' },
    	                { line:true },
    	                { text: '删除', id:'delete', click: itemclick,icon:'delete' },
						{ line:true }, 
//     	                { text: '导出Excel', id:'export', click: exportExcel,icon:'pager' },
// 		                { line:true },
		                { text: '打印', id:'print', click: print,icon:'print' },
		                { line:true },
		                { text: '下载导入模板', id:'downTemplate', click:itemclick,icon:'down' },
		                { line:true },
		                { text: '导入', id:'import', click: itemclick,icon:'up' }
    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.emp_id   + "|" + 
								rowdata.emp_no   + "|" + 
								rowdata.emp_code
							);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

    function itemclick(item){ 
        if(item.id)
        {
            switch (item.id)
            {
                case "add":
              		$.ligerDialog.open({url: 'costEmpAttrAddPage.do?isCheck=false',height: 324,width: 500, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveCostEmpAttr(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
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
							this.emp_id   +"@"+ 
							this.emp_no   +"@"+ 
							this.emp_code  +"@"+ 
							this.copy_code 
							)
                        });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteCostEmpAttr.do",{ParamVo : ParamVo.toString()},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
                    return;
				case "import":
                	$.ligerDialog.open({url: 'costEmpAttrImportPage.do?isCheck=false', height: 500,width: 800, title:'导入',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true });
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
			"emp_id="+vo[0]   +"&"+ 
			"emp_no="+vo[1]  +"&"+ 
			"emp_code="+vo[2];
    	$.ligerDialog.open({ url : 'costEmpAttrUpdatePage.do?isCheck=false&' + parm,data:{}, height: 324,width: 500, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveCostEmpAttr(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    }
    function loadDict(){
            //字典下拉框
    	autocomplete("#emp_kind_code","../queryEmpTypeArrt.do?isCheck=false","id","text",true,true);
    	autocomplete("#title_code","../queryEmpTitleArrt.do?isCheck=false","id","text",true,true);
    	autocomplete("#emp_code","../queryEmpArrt.do?isCheck=false","id","text",true,true);
         }  
    
  //打印数据
	 function printDate(){
		 $("#resultPrint > table > tbody").empty();
		//有数据直接打印
		if($("#resultPrint > table > tbody").html()!=""){
			lodopPrinterTable("resultPrint","开始打印","职工维护",true);
			return;
		}
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备打印数据,请稍候...');
		
		var emp_item = liger.get("emp_code").getValue();
		
		var printPara={
				
			usePager:false,
    		
    		 emp_id:(emp_item !=null && emp_item !='')?emp_item.split(".")[0]:'',
             
             emp_no:(emp_item !=null && emp_item !='')?emp_item.split(".")[1]:'',

           title_code:liger.get("title_code").getValue(),
            
           emp_kind_code:liger.get("emp_kind_code").getValue()
            
         };
		ajaxJsonObjectByUrl("queryCostEmpAttr.do",printPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
                 trHtml+="<td>"+item.emp_id+"</td>"; 
                 trHtml+="<td>"+item.emp_no+"</td>"; 
                 trHtml+="<td>"+item.title_code+"</td>"; 
                 trHtml+="<td>"+item.title_name+"</td>"; 
                 trHtml+="<td>"+item.emp_kind_code+"</td>"; 
                 trHtml+="<td>"+item.emp_kind_name+"</td>"; 
                 trHtml+="<td>"+item.emp_code+"</td>"; 
                 trHtml+="<td>"+item.emp_name+"</td>"; 
                 trHtml+="<td>"+(item.note==null?"":item.note)+"</td>"; 
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			//alert($("#resultPrint").html())
			lodopPrinterTable("resultPrint","开始打印","职工维护",true);
	    },true,manager);
		return;
	 }
	
	 
	 //导出数据
	 function exportExcel(){
		 $("#resultPrint > table > tbody").empty();
		//有数据直接导出
		if($("#resultPrint > table > tbody").html()!=""){
			lodopExportExcel("resultPrint","导出Excel","职工维护.xls",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');
		var emp_item = liger.get("emp_code").getValue();
		var exportPara={
			usePager:false,
			emp_id:(emp_item !=null && emp_item !='')?emp_item.split(".")[0]:'',
		             
		     emp_no:(emp_item !=null && emp_item !='')?emp_item.split(".")[1]:'',

		    title_code:liger.get("title_code").getValue(),
		             
		     emp_kind_code:liger.get("emp_kind_code").getValue()
         };
		ajaxJsonObjectByUrl("queryCostEmpAttr.do",exportPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
				 trHtml+="<td>"+item.emp_id+"</td>"; 
                 trHtml+="<td>"+item.emp_no+"</td>"; 
                 trHtml+="<td>"+item.title_code+"</td>"; 
                 trHtml+="<td>"+item.title_name+"</td>"; 
                 trHtml+="<td>"+item.emp_kind_code+"</td>"; 
                 trHtml+="<td>"+item.emp_kind_name+"</td>"; 
                 trHtml+="<td>"+item.emp_code+"</td>"; 
                 trHtml+="<td>"+item.emp_name+"</td>"; 
                 trHtml+="<td>"+(item.note==null?"":item.note)+"</td>"; 
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			lodopExportExcel("resultPrint","导出Excel","职工维护.xls",true);
	    },true,manager);
		return;
	 }	
// 	 function print(){
		 
// 		//设置表头
// 	   		var columnInfos = [ {
	   		 
// 	   			name : "emp_code",
// 	   			displayName : "职工编码",
// 	   			size: 100 
// 	   		}, {
// 	   			name : "emp_name",
// 	   			displayName : "职工姓名",
// 	   			formatter : "#,##0.00"
// 	   		}, {
// 	   			name : "title_code",
// 	   			displayName : "职称编码",
// 	   			formatter : "#,##0.00"
// 	   		}, {
	   		 
// 	   			name : "title_name",
// 	   			displayName : "职称",
// 	   			size: 100 
// 	   		}, {
// 	   			name : "emp_kind_code",
// 	   			displayName : "分类编码",
// 	   			formatter : "#,##0.00"
// 	   		}, {
// 	   			name : "emp_kind_name",
// 	   			displayName : "分类名称",
// 	   			formatter : "#,##0.00"
// 	   		},{
// 	   			name : "note",
// 	   			displayName : "备注",
// 	   			formatter : "#,##0.00"
// 	   		}];
// 	   		var exportPara = {
// 	   				usePager:false,
// 	   				b_year_month:$("#emp_kind_code").val()
	   				
	   			
// 	   	   		};
	   		
// 	   	   		//公用部分
// 	   	   		viewPrintOneHead("queryCostEmpAttr.do", exportPara, columnInfos,
// 	   	   				"职工维护", 2000); 	 
		 
		 
		 
// 	 }
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
	      		title: "职工维护",//标题
	      		columns: JSON.stringify(grid.getPrintColumns()),//表头
	      		class_name: "com.chd.hrp.cost.service.CostEmpAttrService",
	   			method_name: "queryCostEmpAttrPrint",
	   			bean_name: "costEmpAttrService"
	   			
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
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">职称编码：</td>
            <td align="left" class="l-table-edit-td"><input name="title_code" type="text" id="title_code" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">分类编码：</td>
            <td align="left" class="l-table-edit-td"><input name="emp_kind_code" type="text" id="emp_kind_code" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">职工编码：</td>
            <td align="left" class="l-table-edit-td"><input name="emp_code" type="text" id="emp_code" /></td>
        </tr> 
    </table>

	<div id="maingrid"></div>
	<div id="resultPrint" style="display:none">
	   	<table width="100%">
			   	<thead>
				<tr>
					<th width="200">职工ID</th>
					<th width="200">职工变更ID</th>
					<th width="200">职称编码</th>
					<th width="200">职称名称</th>
					<th width="200">分类编码</th>
					<th width="200">分类名称</th>
					<th width="200">职工编码</th>
					<th width="200">职工姓名</th>
					<th width="200">备注</th>
				   	</tr>
			   	</thead>
			   	<tbody></tbody>
	   	</table>
   	</div>
</body>
</html>
