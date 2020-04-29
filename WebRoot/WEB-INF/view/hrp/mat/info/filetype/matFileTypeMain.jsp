
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
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
        $("#type_code").ligerTextBox({width:160});
        $("#type_name").ligerTextBox({width:160});
        $("#store_id").ligerTextBox({width:160});
        
       
		
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	  grid.options.parms.push({name:'type_code',value:$("#type_code").val()}); 
        
    	  grid.options.parms.push({name:'type_name',value:$("#type_name").val()});

    	//加载查询条件
    	grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
     }
    //获取查询条件的数值
    function f_getWhere(){
    	if (!grid) return null;
        var clause = function (rowdata, rowindex){
                	if($("#type_code").val()!=""){
                		return rowdata.type_code.indexOf($("#type_code").val()) > -1;	
                	}
                	if($("#type_name").val()!=""){
                		return rowdata.type_name.indexOf($("#type_name").val()) > -1;	
                	}
                	
        };
        return clause; 
    }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '文档分类编码', name: 'type_code', align: 'left',width:100,render:
                    	 function(rowdata,rowindex,value){
	                    	 return '<a href=javascript:openUpdate("' 
								+ rowdata.type_code + '")>'+rowdata.type_code+'</a>';

                     	 }
					 		},
                     { display: '文档分类名称', name: 'type_name', align: 'left',width:500
					 		},
					 { display: '是否停用', name: 'is_stop', align: 'left',render:
						function(rowdata){
						 	if(rowdata.is_stop == '0'){
						 		return '否';
						 	}else{
						 		return '是';
						 	}
					 	}
					 		},
                     { display: '备注', name: 'note', align: 'left'
					 		}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatFileType.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
                     	{ text: '添加', id:'add', click: itemclick,icon:'add' },
                     	{ line:true },
                     	 { text: '删除', id:'delete', click: itemclick,icon:'delete' },
                     	{ line:true }
                     	
                     	]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.type_code 
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
              		$.ligerDialog.open({url: 'addMatFileTypePage.do?isCheck=false', height: 220,width: 480, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '保存', onclick: function (item, dialog) { dialog.frame.saveMatFileType(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
              		return;
               
                case "delete":
                    var data = gridManager.getCheckedRows();
                    if (data.length == 0){
                    	$.ligerDialog.error('请选择行');
                    }else{
                    	 var Param =[];
                         $(data).each(function (){					
 							Param.push(
 							//表的主键
 							this.type_code
 							)
                         });
                       $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteMatFileType.do?paramVo="+Param,{},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        });  
                    }
                    return;
            }   
        }
        
    }
    	
   
    function downTemplate(){
    	
    	location.href = "downTemplate.do?isCheck=false";
    
    }
    
   
    
    function openUpdate(obj){
    	
		$.ligerDialog.open({ url : 'updateMatFileTypePage.do?isCheck=false&type_code='+obj,data:{}, height: 220,width: 480, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '保存', onclick: function (item, dialog) { dialog.frame.saveMatFileType(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
    
    }
   
    //键盘事件
	  function loadHotkeys() {

		hotkeys('Q', query);

		hotkeys('A', add);
		hotkeys('D', remove);

		hotkeys('B', downTemplate);

		hotkeys('E', exportExcel);


	 }
  //打印数据
	 function printDate(){
		//有数据直接打印
		if($("#resultPrint > table > tbody").html()!=""){
			lodopPrinterTable("resultPrint","开始打印","04113 物资仓库配套表",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备打印数据,请稍候...');

		var printPara={
			usePager:false,
           type_code:$("#type_code").val(),
           type_name:$("#type_name").val()
         };
		ajaxJsonObjectByUrl("queryMatStoreMatch.do?isCheck=false",printPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
					 trHtml+="<td>"+item.type_code+"</td>"; 
					 trHtml+="<td>"+item.type_name+"</td>"; 
				 trHtml+="</tr>";
				$("#resultPrint > table > tbody").empty();
				$("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			//alert($("#resultPrint").html())
			lodopPrinterTable("resultPrint","开始打印","04113 物资仓库配套表",true);
	    },true,manager);
		return;
	 }
	
	 
	 //导出数据
	 function exportExcel(){
		//有数据直接导出
		if($("#resultPrint > table > tbody").html()!=""){
			lodopExportExcel("resultPrint","导出Excel","04113 物资仓库配套表.xls",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');

		var exportPara={
			usePager:false,
           type_code:$("#type_code").val(),
           type_name:$("#type_name").val()
         };
		ajaxJsonObjectByUrl("queryMatStoreMatch.do?isCheck=false",exportPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
					 trHtml+="<td>"+item.type_code+"</td>"; 
					 trHtml+="<td>"+item.type_name+"</td>"; 
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").empty();
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			lodopExportExcel("resultPrint","导出Excel","04113 物资仓库配套表.xls",true);
	    },true,manager);
		return;
	 }
	 
	 function loadDict(){
         //字典下拉框
      }   
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="topmenu"></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">文档分类编码：</td>
            <td align="left" class="l-table-edit-td"><input name="type_code" type="text" id="type_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">文档分类名称：</td>
            <td align="left"  class="l-table-edit-td" ><input name="type_name" type="text"  id="type_name" ltype="text"   validate="{required:true,maxlength:40}" /></td>
            <td align="left"></td>
        </tr> 
    </table>

	<div id="maingrid"></div>
	<div id="resultPrint" style="display:none">
	   	<table width="100%">
			<thead>
		 
			<tr>
                <th width="200">配套表ID</th>	
                <th width="200">配套表名称</th>	
                <th width="200">仓库ID</th>	
                <th width="200">科室ID</th>	
			</tr>
			   	</thead>
			   	<tbody></tbody>
	   	</table>
   	</div>
</body>
</html>
