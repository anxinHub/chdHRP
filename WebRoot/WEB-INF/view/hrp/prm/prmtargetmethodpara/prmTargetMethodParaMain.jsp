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
        $("#target_code").ligerTextBox({width:160});
        $("#target_name").ligerTextBox({width:160}); 
        $("#method_code").ligerTextBox({width:160});
        $("#method_name").ligerTextBox({width:160});
        $("#is_stop").ligerTextBox({width:160});
        loadHotkeys();
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	  grid.options.parms.push({name:'method_code',value:liger.get("method_code").getValue()});  
    	  grid.options.parms.push({name:'target_code',value:liger.get("target_name").getValue()});
    	  grid.options.parms.push({name:'nature_code',value:liger.get("nature_code").getValue()});
    	  grid.options.parms.push({name:'is_stop',value:$("#is_stop").val()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }
    //获取查询条件的数值
    function f_getWhere(){
    	if (!grid) return null;
        var clause = function (rowdata, rowindex){
                	if($("#method_code").val()!=""){
                		return rowdata.method_code.indexOf($("#method_code").val()) > -1;	
                	}
                	if($("#target_code").val()!=""){
                		return rowdata.target_code.indexOf($("#target_code").val()) > -1;	
                	}
                	if($("#nature_code").val()!=""){
                		return rowdata.nature_code.indexOf($("#nature_code").val()) > -1;	
                	}
                	if($("#is_stop").val()!=""){
                		return rowdata.is_stop.indexOf($("#is_stop").val()) > -1;	
                	}
        };
        return clause; 
    }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '指标编码', name: 'target_code', align: 'left'
				 		},
             		 { display: '指标名称', name: 'target_name', align: 'left'
				 		},
                     { display: '指标性质', name: 'nature_code', align: 'left',
                    	 render : function(rowdata, rowindex,
									value) {
								return rowdata.nature_name;
							}
					 		},
                     { display: '取值方法名称', name: 'method_code', align: 'left',
					 			render : function(rowdata, rowindex,
    									value) {
    								return rowdata.method_name;
    							}
					 		},
                     { display: '是否停用', name: 'is_stop', align: 'left',
					 			render : function(rowdata, rowindex,
    									value) {
    								if(rowdata.is_stop==0){
    									return "否";
    								}else
    									{
    									return "是";
    									}
    							}
					 		}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryPrmTargetMethodParaNature.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' },
                     	{ line:true },
				    					{ text: '添加（<u>A</u>）', id:'add', click: addTargetMethodPara, icon:'add' },
				    	                { line:true },
				    	                { text: '删除（<u>D</u>）', id:'delete', click: deleteTargetMethodPara,icon:'delete' },
											{ line:true }, 
				    	                { text: '导出Excel（<u>E</u>）', id:'export', click: exportTargetMethodPara,icon:'pager' },
						                { line:true },
						                { text: '打印（<u>P</u>）', id:'print', click: printTargetMethodPara,icon:'print' },
						                { line:true },
						                { text: '下载导入模板（<u>T</u>）', id:'downTemplate', click: templateTargetMethodPara,icon:'down' },
						                { line:true },
						                { text: '导入（<u>I</u>）', id:'import', click: importTargetMethodPara,icon:'up' }
				    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code   + "|" + 
								rowdata.method_code 
							);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    
  //键盘事件
	function loadHotkeys(){
		
		hotkeys('Q',query);
		 
		hotkeys('A',addTargetMethodPara);
		
		hotkeys('D',deleteTargetMethodPara);
		
		hotkeys('E',exportTargetMethodPara);
		
		hotkeys('T',templateTargetMethodPara);
		
		hotkeys('I',importTargetMethodPara);
		
		hotkeys('P',printTargetMethodPara);
		 
	}

  	function addTargetMethodPara (){
  		
  		$.ligerDialog.open({url: 'prmTargetMethodParaAddPage.do?isCheck=false', height: 600,width: 1200, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.savePrmTargetMethodPara(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
  		
  	}
  	
  	function deleteTargetMethodPara (){
  		
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
														this.method_code 
														) });
             $.ligerDialog.confirm('确定删除?', function (yes){
             	if(yes){
                 	ajaxJsonObjectByUrl("deletePrmTargetMethodPara.do",{ParamVo : ParamVo},function (responseData){
                 		if(responseData.state=="true"){
                 			query();
                 		}
                 	});
             	}
             }); 
         }
  	}
  	
  	function importTargetMethodPara (){
  		
  		$.ligerDialog.open({url: 'prmTargetMethodParaImportPage.do?isCheck=false', height: 500,width: 800, title:'导入',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true });
  		
  	}
  	
  	function templateTargetMethodPara (){
  		
  		location.href = "downTemplate.do?isCheck=false";
    	
  	}
   
  //打印数据
	 function printTargetMethodPara (){
		//有数据直接打印
		if($("#resultPrint > table > tbody").html()!=""){
			lodopPrinterTable("resultPrint","开始打印","9903 指标取值方法参数表",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备打印数据,请稍候...');
 
		var printPara={
				
			usePager:false, 
           
          method_code:$("#method_code").val(),
           
          method_name:$("#method_name").val(), 
           
          is_stop:$("#is_stop").val()
           
           
        };
		ajaxJsonObjectByUrl("queryPrmTargetMethodParaNature.do",printPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
					 trHtml+="<td>"+item.method_code+"</td>"; 
					 trHtml+="<td>"+item.method_name+"</td>"; 
					 trHtml+="<td>"+(item.is_stop==0?'否':'是')+"</td>"; 
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			//alert($("#resultPrint").html())
			lodopPrinterTable("resultPrint","开始打印","9903 指标取值方法参数表",true);
	    },true,manager); 
	 }
	
	 
	 //导出数据
	 function exportTargetMethodPara (){
		//有数据直接导出
		if($("#resultPrint > table > tbody").html()!=""){
			lodopExportExcel("resultPrint","导出Excel","9903 指标取值方法参数表集.xls",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');
 
		var exportPara={
				
			usePager:false,
            
          method_code:$("#method_code").val(),
           
          method_name:$("#method_name").val(),
            
          is_stop:$("#is_stop").val()
           
           
        };
		ajaxJsonObjectByUrl("queryPrmTargetMethodParaNature.do",exportPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
					 trHtml+="<td>"+item.method_code+"</td>"; 
					 trHtml+="<td>"+item.method_name+"</td>"; 
					 trHtml+="<td>"+(item.is_stop==0?'否':'是')+"</td>";
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			lodopExportExcel("resultPrint","导出Excel","指标取值方法参数表.xls",true);
	    },true,manager); 
	 }		
	 
	 
    function openUpdate(obj){
    	
		var vo = obj.split("|");
		var parm = 
			vo[column_index]   +"&"+ 
			vo[column_index]   +"&"+ 
			vo[column_index]   +"&"+ 
			vo[column_index] 
		
    	$.ligerDialog.open({ url : 'prmTargetMethodParaUpdatePage.do?isCheck=false&' + parm,data:{}, height: 500,width: 500, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.savePrmTargetMethodPara(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    }
    function loadDict(){
            //字典下拉框
         autocomplete("#target_name","../quertPrmTargetDict.do?isCheck=false","id","text",true,true);
    	 autocomplete("#method_code","../queryPrmTargetMethodPara.do?isCheck=false","id","text",true,true);
    	 autocomplete("#nature_code","../queryPrmTargetNature.do?isCheck=false","id","text",true,true);   
    	 $("#is_stop").ligerComboBox({width:160 });   
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
        	  
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">指标名称：</td>
            <td align="left" class="l-table-edit-td"><input name="target_name" type="text" id="target_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">指标性质</td>
            <td align="left" class="l-table-edit-td"><input name="nature_code" type="text" id="nature_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
           
        </tr> 
	
        <tr>
         <td align="right" class="l-table-edit-td"  style="padding-left:20px;">取值方法名称：</td>
            <td align="left" class="l-table-edit-td"><input name="method_code" type="text" id="method_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否停用:</td>
            <td align="left" class="l-table-edit-td">
            <select id="is_stop" name="is_stop">
                		<option value=""></option>
                		<option value="0">否</option>
                		<option value="1">是</option>
                	</select>
            </td>
            <td align="left"></td>
        </tr> 
    </table>

	<div id="maingrid"></div>
	<div id="resultPrint" style="display:none">
	   	<table width="100%">
			   	<thead>
			 
				<tr>
                <th width="200">取值方法编码</th>	
                <th width="200">取值方法名称</th>	
                <th width="200">是否停用</th>	
          
				   	</tr>
			   	</thead>
			   	<tbody></tbody>
	   	</table>
   	</div>
</body>
</html>
