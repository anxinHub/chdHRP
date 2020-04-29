<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
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
        
        $("#type_code").ligerTextBox({width:160});
        $("#type_name").ligerTextBox({width:160});
		
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

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '证件类型编码', name: 'type_code', align: 'left',
                    	 render: function(rowdata, index, value){
                    		 return "<a href=javascript:openUpdate('"+rowdata.group_id+"|"+ rowdata.hos_id+"|"+rowdata.copy_code+"|"+rowdata.type_id+"')>"+rowdata.type_code+"</a>";
                    	 }
					 		},
                     { display: '证件类型名称', name: 'type_name', align: 'left'
					 		},
					{ display: '效期类型', name: 'validity_type', align: 'left', 
					 			render: function(rowdata, index, value){
					 				if(value == 1 ){
					 					return "永久有效" ;
					 				}else{
					 					return "限定有效" ;
					 				}
					 			}
						 	},
					 { display: '预警天数', name: 'war_days', align: 'left'
					 		},
                     { display: '是否预警', name: 'is_alarm', align: 'left',
					 			render: function(rowdata, index, value){
					 				if(rowdata.is_alarm == 0 ){
					 					return "否" ;
					 				}else{
					 					return "是" ;
					 				}
					 			}
					 		}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:false,url:'queryMedInvCertType.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,heightDiff: -20,
                     toolbar: { items: [
                     	{ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' },
                     	{ line:true },
    					{ text: '添加（<u>A</u>）', id:'add', click: add_open, icon:'add' },
    	                { line:true },
    	                { text: '删除（<u>D</u>）', id:'delete', click: remove,icon:'delete' },
						{ line:true }, 
    	                /* { text: '导出Excel（<u>E</u>）', id:'export', click: exportExcel,icon:'pager' },
		                { line:true },
		                { text: '打印（<u>P</u>）', id:'print', click: printDate,icon:'print' },
		                { line:true }, */
		                { text: '下载导入模板（<u>T</u>）', id:'downTemplate', click: downTemplate,icon:'down' },
		                { line:true },
		                { text: '导入（<u>I</u>）', id:'import', click: imp,icon:'up' }
    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code   + "|" + 
								rowdata.type_id 
							);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
	function add_open(){
		$.ligerDialog.open({url: 'medInvCertTypeAddPage.do?isCheck=false', height: 280,width: 400, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveMedInvCertType(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
	}
	
	function remove(){
		 var data = gridManager.getCheckedRows();
		 var ParamVo =[];
		  if (data.length == 0){
	        	$.ligerDialog.error('请选择行');
	        }else{
	            $(data).each(function (){					
	   						ParamVo.push(
	   						this.group_id   +"@"+ 
	   						this.hos_id   +"@"+ 
	   						this.copy_code   +"@"+ 
	   						this.type_id  +"@"+ 
	   						this.type_code 
	   						) });
	            $.ligerDialog.confirm('确定删除?', function (yes){
	            	if(yes){
	                	ajaxJsonObjectByUrl("deleteMedInvCertType.do?ParamVo="+ParamVo,{ParamVo : ParamVo},function (responseData){
	                		if(responseData.state=="true"){
	                			query();
	                		}
	                	});
	            	}
	            }); 
	        }
	}
	function downTemplate(){
		location.href = "downTemplate.do?isCheck=false";
		return;
	}
	
	function imp(){
		$.ligerDialog.open({url: 'medInvCertTypeImportPage.do?isCheck=false', height: 500,width: 800, title:'导入',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true });
	}
	
    function openUpdate(obj){
    	
		var vo = obj.split("|");
		var parm = "group_id="+vo[0]+"&hos_id="+ vo[1]+"&copy_code="+ vo[2]+"&type_id="+ vo[3];
		
    	$.ligerDialog.open({ url : 'medInvCertTypeUpdatePage.do?isCheck=false&' + parm,data:{}, height: 280,width: 400, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveMedInvCertType(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    }
    function loadDict(){
            //字典下拉框
            
         } 
  //键盘事件
	  function loadHotkeys() {
	 	
		hotkeys('Q', query);
	
		hotkeys('A', add_open);
		hotkeys('D', remove);
	
		hotkeys('T', downTemplate);
	
		hotkeys('E', exportExcel);
	
		hotkeys('P', printDate);
		hotkeys('I', imp);
	}
    
  //打印数据
	 function printDate(){
	  debugger;
		 //有数据直接打印
		if($("#resultPrint > table > tbody").html() !=""){
			lodopPrinterTable("resultPrint","开始打印","08601 药品证件类型字典",true);
			return;
		}  
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备打印数据,请稍候...');

		var printPara={
			usePager:false,
           type_code:$("#type_code").val(),
           type_name:$("#type_name").val(),
         };
		ajaxJsonObjectByUrl("queryMedInvCertType.do",printPara,function (responseData){
			var trHtml ='';
			$.each(responseData.Rows,function(idx,item){ 
				 	 trHtml+="<tr>";
					 trHtml+="<td>"+item.type_code+"</td>"; 
					 trHtml+="<td>"+item.type_name+"</td>";
					 if(item.is_alarm == 0){
						 trHtml+="<td>否</td>";
					 }else{
						 trHtml+="<td>是</td>";
					 }
					 trHtml+="<td>"+item.war_days+"</td>"; 
				 trHtml+="</tr>";
				$("#resultPrint > table > tbody").empty();
				$("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			//alert($("#resultPrint").html())
			lodopPrinterTable("resultPrint","开始打印","08601 药品证件类型字典",true);
	    },true,manager);
		return;
	 }
	
	 
	 //导出数据
	 function exportExcel(){
		 //有数据直接导出
		if($("#resultPrint > table > tbody").html()!=""){
			lodopExportExcel("resultPrint","导出Excel","08601 药品证件类型字典.xls",true);
			return;
		} 
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');

		var exportPara={
			usePager:false,
           type_code:$("#type_code").val(),
           type_name:$("#type_name").val(),
         };
		ajaxJsonObjectByUrl("queryMedInvCertType.do",exportPara,function (responseData){
			var trHtml = '';
			$.each(responseData.Rows,function(idx,item){ 
				 	 trHtml+="<tr>";
					 trHtml+="<td>"+item.type_code+"</td>"; 
					 trHtml+="<td>"+item.type_name+"</td>"; 
					 if(item.is_alarm == 0){
						 trHtml+="<td>否</td>";
					 }else{
						 trHtml+="<td>是</td>";
					 } 
					 trHtml+="<td>"+item.war_days+"</td>"; 
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").empty();
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			lodopExportExcel("resultPrint","导出Excel","08601 药品证件类型字典.xls",true);
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
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">证件类型编码：</td>
            <td align="left" class="l-table-edit-td"><input name="type_code" type="text" id="type_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">证件类型名称：</td>
            <td align="left" class="l-table-edit-td"><input name="type_name" type="text" id="type_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
    </table>

	<div id="maingrid"></div>
	<div id="resultPrint" style="display:none">
	   	<table width="100%">
			<thead>
			<tr>
                <th width="200">证件类型编码</th>	
                <th width="200">证件类型名称</th>	
                <th width="200">是否预警</th>	
                <th width="200">预警天数</th>	
			</tr>
		   	</thead>
		   	<tbody></tbody>
	   	</table>
   	</div>
</body>
</html>
