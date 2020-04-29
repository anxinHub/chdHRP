<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc.jsp"/>
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
		
        $("#fun_code").ligerTextBox({width:160});
        $("#para_code").ligerTextBox({width:160});
        $("#para_name").ligerTextBox({width:160});
        $("#stack_seq_no").ligerTextBox({width:160});
        $("#com_type_code").ligerTextBox({width:160});
		
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	  grid.options.parms.push({name:'fun_code',value:$("#fun_code").val()}); 
    	  grid.options.parms.push({name:'para_code',value:$("#para_code").val()}); 
    	  grid.options.parms.push({name:'para_name',value:$("#para_name").val()}); 
    	  grid.options.parms.push({name:'stack_seq_no',value:$("#stack_seq_no").val()}); 
    	  grid.options.parms.push({name:'com_type_code',value:$("#com_type_code").val()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }
    //获取查询条件的数值
    function f_getWhere(){
    	if (!grid) return null;
        var clause = function (rowdata, rowindex){
                	if($("#fun_code").val()!=""){
                		return rowdata.fun_code.indexOf($("#fun_code").val()) > -1;	
                	}
                	if($("#para_code").val()!=""){
                		return rowdata.para_code.indexOf($("#para_code").val()) > -1;	
                	}
                	if($("#para_name").val()!=""){
                		return rowdata.para_name.indexOf($("#para_name").val()) > -1;	
                	}
                	if($("#stack_seq_no").val()!=""){
                		return rowdata.stack_seq_no.indexOf($("#stack_seq_no").val()) > -1;	
                	}
                	if($("#com_type_code").val()!=""){
                		return rowdata.com_type_code.indexOf($("#com_type_code").val()) > -1;	
                	}
        };
        return clause; 
    }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '函数编码', name: 'fun_code', align: 'left'
					 		},
                     { display: '参数代码', name: 'para_code', align: 'left'
					 		},
                     { display: '参数名称', name: 'para_name', align: 'left'
					 		},
                     { display: '参数栈序列', name: 'stack_seq_no', align: 'left'
					 		},
                     { display: '部件类型', name: 'com_type_code', align: 'left'
					 		}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'hrFunParaSetquery.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true, showBottom:false,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
				    					{ text: '添加', id:'add', click: itemclick, icon:'add' },
				    	                { line:true },
				    	                { text: '删除', id:'delete', click: itemclick,icon:'delete' },
											{ line:true }, 
				    	                { text: '导出Excel', id:'export', click: exportExcel,icon:'pager' },
						                { line:true },
						                { text: '打印', id:'print', click: printDate,icon:'print' },
						                { line:true },
						                { text: '下载导入模板', id:'downTemplate', click:itemclick,icon:'down' },
						                { line:true },
						                { text: '导入', id:'import', click: itemclick,icon:'up' }
				    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code   + "|" + 
								rowdata.fun_code   + "|" + 
								rowdata.para_code 
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
              		$.ligerDialog.open({url: 'hrFunParaAddPage.do?isCheck=false', height: 500,width: 500, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveHpmFunPara(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
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
																this.group_id   +"@"+ 
																this.hos_id   +"@"+ 
																this.copy_code   +"@"+ 
																this.fun_code   +"@"+ 
																this.para_code 
																) });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("hrFunParaSetdelete.do",{ParamVo : ParamVo},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
                    return;
                case "import":
                	$.ligerDialog.open({url: 'hpmFunParaImportPage.do?isCheck=false', height: 500,width: 800, title:'导入',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true });
                case "export":
                	return;
                case "downTemplate":
                	location.href = "downTemplate.do?isCheck=false";
                	return;
               }   
        }
        
    }
    function openUpdate(obj){
    	
		var vo = obj.split("|");
		var parm = 
			vo[column_index]   +"&"+ 
			vo[column_index]   +"&"+ 
			vo[column_index]   +"&"+ 
			vo[column_index]   +"&"+ 
			vo[column_index] 
		
    	$.ligerDialog.open({ url : 'hpmFunParaUpdatePage.do?isCheck=false&' + parm,data:{}, height: 500,width: 500, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveHpmFunPara(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    }
    function loadDict(){
            //字典下拉框
            
         }  
    
  //打印数据
	 function printDate(){
		//有数据直接打印
		if($("#resultPrint > table > tbody").html()!=""){
			lodopPrinterTable("resultPrint","开始打印","9911 绩效函数参数表",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备打印数据,请稍候...');

		var cost_item = liger.get("cost_item_id").getValue();
		
		var dept_dict = liger.get("dept_id").getValue();
		
		var printPara={
				
			usePager:false,
            
            
            
            
           fun_code:$("#fun_code").val(),
            
           para_code:$("#para_code").val(),
            
           para_name:$("#para_name").val(),
            
           stack_seq_no:$("#stack_seq_no").val(),
            
           com_type_code:$("#com_type_code").val()
            
            
         };
		ajaxJsonObjectByUrl("hrFunParaSetquery.do",printPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
					 trHtml+="<td>"+item.fun_code+"</td>"; 
					 trHtml+="<td>"+item.para_code+"</td>"; 
					 trHtml+="<td>"+item.para_name+"</td>"; 
					 trHtml+="<td>"+item.stack_seq_no+"</td>"; 
					 trHtml+="<td>"+item.com_type_code+"</td>"; 
				 trHtml+="</tr>";
				
			});
			manager.close();
			//alert($("#resultPrint").html())
			lodopPrinterTable("resultPrint","开始打印","9911 绩效函数参数表",true);
	    },true,manager);
		return;
	 }
	
	 
	 //导出数据
	 function exportExcel(){
		//有数据直接导出
		if($("#resultPrint > table > tbody").html()!=""){
			lodopExportExcel("resultPrint","导出Excel","9911 绩效函数参数表集.xls",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');

		var cost_item = liger.get("cost_item_id").getValue();
		
		var dept_dict = liger.get("dept_id").getValue();
		
		var printPara={
				
			usePager:false,
            
            
            
            
           fun_code:$("#fun_code").val(),
            
           para_code:$("#para_code").val(),
            
           para_name:$("#para_name").val(),
            
           stack_seq_no:$("#stack_seq_no").val(),
            
           com_type_code:$("#com_type_code").val()
            
            
         };
		ajaxJsonObjectByUrl("hrFunParaSetquery.do",exportPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
					 trHtml+="<td>"+item.fun_code+"</td>"; 
					 trHtml+="<td>"+item.para_code+"</td>"; 
					 trHtml+="<td>"+item.para_name+"</td>"; 
					 trHtml+="<td>"+item.stack_seq_no+"</td>"; 
					 trHtml+="<td>"+item.com_type_code+"</td>"; 
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			lodopExportExcel("resultPrint","导出Excel","9911 绩效函数参数表.xls",true);
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
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">函数编码：</td>
            <td align="left" class="l-table-edit-td"><input name="fun_code" type="text" id="fun_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">参数代码：</td>
            <td align="left" class="l-table-edit-td"><input name="para_code" type="text" id="para_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">参数名称：</td>
            <td align="left" class="l-table-edit-td"><input name="para_name" type="text" id="para_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">参数栈序列：</td>
            <td align="left" class="l-table-edit-td"><input name="stack_seq_no" type="text" id="stack_seq_no" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">部件类型：</td>
            <td align="left" class="l-table-edit-td"><input name="com_type_code" type="text" id="com_type_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
    </table>

	<div id="maingrid"></div>
	<div id="resultPrint" style="display:none">
	   	<table width="100%">
			   	<thead>
			 
				<tr>
                <th width="200">函数编码</th>	
                <th width="200">参数代码</th>	
                <th width="200">参数名称</th>	
                <th width="200">参数栈序列</th>	
                <th width="200">部件类型</th>	
          
				   	</tr>
			   	</thead>
			   	<tbody>
			   		<tr>
                <td>1</td>	
                <td>1</td>	
                <td>1</td>	
                <td>1</td>	
                <td>1</td>	
			   		</tr>
			   	</tbody>
	   	</table>
   	</div>
</body>
</html>
