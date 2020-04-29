<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc.jsp" />
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
        $("#pack_code").ligerTextBox({width:160});
        $("#pack_name").ligerTextBox({width:160});
        $("#is_stop").ligerTextBox({width:160});
        $("#note").ligerTextBox({width:160});
		
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	  grid.options.parms.push({name:'pack_code',value:$("#pack_code").val()}); 
    	  grid.options.parms.push({name:'pack_name',value:$("#pack_name").val()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '包装单位编码', name: 'pack_code', align: 'left',
                    	 render:function(rowdata,index,value){
                    		 //return "<a href=javascript:openUpdate('"+rowdata.group_id+"|"+rowdata.hos_id+"|"+rowdata.copy_code+"|"+rowdata.store_id+"|"+rowdata.inv_id+"')>"+rowdata.inv_code+"</a>";
                    		 return "<a href=javascript:openUpdate('"+rowdata.group_id+ "|" +rowdata.hos_id+ "|" + rowdata.pack_code+"')>"+rowdata.pack_code+"</a>";
                    	 }
					 		},
                     { display: '包装单位名称', name: 'pack_name', align: 'left'
					 		},
                     { display: '是否停用', name: 'is_stop', align: 'left',
					 			render:function(rowdata,index,value){
					 				if(rowdata.is_stop == 0){
					 					return "在用";
					 				}else{
					 					return "停用";
					 				}
					 			}
					 			
					 		},
                     { display: '备注', name: 'note', align: 'left'
					 		}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryHosPackage.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' },
                     	{ line:true },
    					{ text: '添加（<u>A</u>）', id:'add', click: add_open, icon:'add' },
    	                { line:true },
    	                { text: '删除（<u>D</u>）', id:'delete', click: remove,icon:'delete' },
						{ line:true }, 
    	                /*{ text: '导出Excel（<u>E</u>）', id:'export', click: exportExcel,icon:'pager' },
		                { line:true },
		                { text: '打印（<u>P</u>）', id:'print', click: printDate,icon:'print' },
		                { line:true },*/
		                { text: '下载导入模板（<u>B</u>）', id:'downTemplate', click:downTemplate,icon:'down' },
		                { line:true },
		                { text: '导入（<u>I</u>）', id:'import', click: imp,icon:'up' }
    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.pack_code 
							);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function add_open(){
    	
    	$.ligerDialog.open({ url : 'hosPackageAddPage.do?isCheck=false',data:{}, 
			height: 260,width: 800, title:'添加',modal:true,showToggle:false,showMax:false,
			showMin: false,isResize:true,
			buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveHosPackage(); },
			cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); }}
			]});
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
								this.pack_code 
								) });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteHosPackage.do",{ParamVo : ParamVo.toString()},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
    	}
    function imp(){
    	
	    	$.ligerDialog.open({ url : 'hosPackageImportPage.do?isCheck=false',data:{}, 
				height: 500,width: 900, title:'导入',modal:true,showToggle:false,showMax:false,
				showMin: false,isResize:true
	    	});
    	}	
    function downTemplate(){
    	
    	location.href = "downTemplate.do?isCheck=false";
    	}	
   
    function openUpdate(obj){
    		
		var vo = obj.split("|");
		var parm = 
			"group_id="+vo[0]   +"&"+ 
			"hos_id="+vo[1]   +"&"+ 
			"pack_code="+vo[2] 
		 
		$.ligerDialog.open({ url : 'hosPackageUpdatePage.do?isCheck=false'+parm,data:{}, 
			height: 370,width: 800, title:'添加',modal:true,showToggle:false,showMax:false,
			showMin: false,isResize:true,
			buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveHosPackage(); },
			cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); }}
			]});
    }
    function loadDict(){
            //字典下拉框
            
         }  
    //键盘事件
	  function loadHotkeys() {

		hotkeys('Q', query);

		hotkeys('A', add_open);
		hotkeys('D', remove);

		hotkeys('B', downTemplate);

		hotkeys('E', exportExcel);

		hotkeys('P', printDate);
		hotkeys('I', imp);
		

	 }
  //打印数据
	 function printDate(){
		//有数据直接打印
		if($("#resultPrint > table > tbody").html()!=""){
			lodopPrinterTable("resultPrint","开始打印","04117 包装单位定义",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备打印数据,请稍候...');

		var printPara={
			usePager:false,
           pack_code:$("#pack_code").val(),
           pack_name:$("#pack_name").val()
         };
		ajaxJsonObjectByUrl("queryHosPackage.do",printPara,function (responseData){
			var trHtml='';
			$.each(responseData.Rows,function(idx,item){ 
				 	 trHtml+="<tr>";
					 trHtml+="<td>"+item.pack_code+"</td>"; 
					 trHtml+="<td>"+item.pack_name+"</td>";
					 if(item.is_stop == 0){
						 trHtml+="<td>在用</td>";
					 }else{
						 trHtml+="<td>停用</td>";
					 }
					 if(item.note == null){
						 trHtml+="<td></td>";
					 }else{
						 trHtml+="<td>"+item.note+"</td>";
					 }
				 trHtml+="</tr>";
				$("#resultPrint > table > tbody").empty();
				$("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			//alert($("#resultPrint").html())
			lodopPrinterTable("resultPrint","开始打印","04117 包装单位定义",true);
	    },true,manager);
		return;
	 }
	
	 
	 //导出数据
	 function exportExcel(){
		//有数据直接导出
		if($("#resultPrint > table > tbody").html()!=""){
			lodopExportExcel("resultPrint","导出Excel","04117 包装单位定义.xls",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');

		var exportPara={
			usePager:false,
           pack_code:$("#pack_code").val(),
           pack_name:$("#pack_name").val()
         };
		ajaxJsonObjectByUrl("queryHosPackage.do",exportPara,function (responseData){
			var trHtml='';
			$.each(responseData.Rows,function(idx,item){ 
				 	 trHtml+="<tr>";
					 trHtml+="<td>"+item.pack_code+"</td>"; 
					 trHtml+="<td>"+item.pack_name+"</td>"; 
					 if(item.is_stop == 0){
						 trHtml+="<td>在用</td>";
					 }else{
						 trHtml+="<td>停用</td>";
					 }
					 if(item.note == null){
						 trHtml+="<td></td>";
					 }else{
						 trHtml+="<td>"+item.note+"</td>";
					 }
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").empty();
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			lodopExportExcel("resultPrint","导出Excel","04117 包装单位定义.xls",true);
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
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">包装单位编码：</td>
            <td align="left" class="l-table-edit-td"><input name="pack_code" type="text" id="pack_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">包装单位名称：</td>
            <td align="left" class="l-table-edit-td"><input name="pack_name" type="text" id="pack_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
    </table>
	<div id="maingrid"></div>
	<div id="resultPrint" style="display:none">
	   	<table width="100%">
			<thead>
			<tr>
                <th width="200">包装单位编码</th>	
                <th width="200">包装单位名称</th>	
                <th width="200">是否停用</th>	
                <th width="200">备注</th>	
			</tr>
			   	</thead>
			   	<tbody></tbody>
	   	</table>
   	</div>
</body>
</html>
