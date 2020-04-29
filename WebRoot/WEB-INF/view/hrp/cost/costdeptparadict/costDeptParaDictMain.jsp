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
    	grid.options.parms.push({name:'para_code',value:$("#para_code").val()}); 
/*     	grid.options.parms.push({name:'para_name',value:$("#para_name").val()}); */
    	grid.options.parms.push({name:'is_sys',value:$("#is_sys").val()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '分摊参数编码', name: 'para_code', align: 'left',width:'120',
                    	 
                    	 render : function(rowdata, rowindex,value) {
                    		 if(rowdata.is_sys == 1){
                    			 return rowdata.para_code;
                    		 }else{
                    			 return "<a href=javascript:openUpdate('"+rowdata.group_id+ "|" +rowdata.hos_id+ "|" + rowdata.copy_code +"|"+rowdata.para_code+ "')>"+rowdata.para_code+"</a>";
                    		 }
 						}
					 },
                     { display: '分摊参数名称', name: 'para_name', align: 'left',width:'200'
					 },
                     { display: '说明', name: 'remark', align: 'left'
					 }/* ,
					 { display: '操作', name: 'edit', align: 'center',width:'180',
							render : function(rowdata, rowindex,value) {
								if(rowdata.is_sys == 1){
									return "<span style='color:#888888'>删除</span>";
								}else{
									return "<div class='l-button' style='width: 60px; margin-top:1px; margin-left: 60px;' ligeruiid='Button1004'" 
									+"onclick=del('"+rowdata.group_id+"','"+rowdata.hos_id+"','"+rowdata.copy_code+"','"+rowdata.para_code+"','"+rowdata.is_sys+"','"+rowindex+"')><span>删除</span></div>";
								}
							}
					 } */
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryCostDeptParaDict.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     unSelectedRow:[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16],
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
    					{ text: '添加', id:'add', click: itemclick, icon:'add' },
    	                { line:true },
    	                { text: '删除', id:'delete', click: itemclick,icon:'delete' },
						{ line:true }/* , 
    	                { text: '导出Excel', id:'export', click: exportExcel,icon:'pager' },
		                { line:true },
		                { text: '打印', id:'print', click: printDate,icon:'print' },
		                { line:true },
		                { text: '下载导入模板', id:'downTemplate', click:itemclick,icon:'down' },
		                { line:true },
		                { text: '导入', id:'import', click: itemclick,icon:'up' } */
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
              		$.ligerDialog.open({url: 'costDeptParaDictAddPage.do?isCheck=false', height: 200,width: 500, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveCostDeptParaDict(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
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
							this.para_code   +"@"+
							this.is_sys
							)
                        });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteCostDeptParaDict.do",{ParamVo : ParamVo.toString()},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
                    return;
				case "import":
                	$.ligerDialog.open({url: 'costDeptParaDictImportPage.do?isCheck=false', height: 500,width: 800, title:'导入',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true });
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
    
    function del(group_id,hos_id,copy_code,para_code,is_sys,rowindex){
            var ParamVo =[];
				ParamVo.push(
				group_id   +"@"+ 
				hos_id   +"@"+ 
				copy_code   +"@"+ 
				para_code +"@"+
				is_sys
				)
            $.ligerDialog.confirm('确定删除?', function (yes){
            	if(yes){
                	ajaxJsonObjectByUrl("deleteCostDeptParaDict.do",{ParamVo : ParamVo.toString()},function (responseData){
                		if(responseData.state=="true"){
                			query();
                		}
                	});
            	}
            }); 
    }
    
    function openUpdate(obj){
    	
		var vo = obj.split("|");
		var parm = 
			"group_id="+vo[0]   +"&"+ 
			"hos_id="+vo[1]   +"&"+ 
			"copy_code="+vo[2]   +"&"+ 
			"para_code="+vo[3];
    	$.ligerDialog.open({ url : 'costDeptParaDictUpdatePage.do?isCheck=false&' + parm,data:{}, height: 200,width: 500, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveCostDeptParaDict(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    }
    function loadDict(){
    	
    	    $("#is_sys").ligerComboBox({width:80 });
            //字典下拉框
			$("#para_code").ligerTextBox({width:160});
			$("#para_name").ligerTextBox({width:160});
         }  
    
  //打印数据
	 function printDate(){

		
	 	$("#resultPrint > table > tbody").empty();
		 
	  	//有数据直接打印
		/* if($("#resultPrint > table > tbody").html()!=""){
			lodopPrinterTable("resultPrint","开始打印","分摊参数",true);
			return;
		} */

		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备打印数据,请稍候...');
		
		var printPara={
			usePager:false,
			
           	para_code:$("#para_code").val(),
            
           	/* para_name:$("#para_name").val() */
            
         };
		ajaxJsonObjectByUrl("queryCostDeptParaDict.do",printPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
                     trHtml+="<td>"+item.para_code+"</td>"; 
                     trHtml+="<td>"+item.para_name+"</td>"; 
                     trHtml+="<td>"+item.remark+"</td>"; 
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			//alert($("#resultPrint").html())
			lodopPrinterTable("resultPrint","开始打印","分摊参数",true);
	    },true,manager);
		return;
	 }
	
	 
	 //导出数据
	 function exportExcel(){
		//有数据直接导出
		
		$("#resultPrint > table > tbody").empty();
		 
	  	//有数据直接打印
		/* if($("#resultPrint > table > tbody").html()!=""){
			lodopExportExcel("resultPrint","导出Excel","分摊参数.xls",true);
			return;
		} */
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');
		
		var exportPara={
				
			usePager:false,
			
           	para_code:$("#para_code").val(),
           	
          	/* para_name:$("#para_name").val() */
          	
         };
		ajaxJsonObjectByUrl("queryCostDeptParaDict.do",exportPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
                     trHtml+="<td>"+item.para_code+"</td>"; 
                     trHtml+="<td>"+item.para_name+"</td>"; 
                     trHtml+="<td>"+item.remark+"</td>"; 
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			lodopExportExcel("resultPrint","导出Excel","分摊参数.xls",true);
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
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">分摊参数：</td>
            <td align="left" class="l-table-edit-td"><input name="para_code" type="text" id="para_code" /></td>
          <!--   <td align="right" class="l-table-edit-td"  style="padding-left:20px;">分摊参数名称：</td>
            <td align="left" class="l-table-edit-td"><input name="para_name" type="text" id="para_name" /></td> -->
        
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否系统内置：</td>
            <td align="left" class="l-table-edit-td">
            <select name="is_sys" id="is_sys"  style="width:140px;">
					<option value="">请选择</option>
					<option value="0">否</option>
					<option value="1">是</option>
				</select>
            </td>
        </tr> 
	
        <tr>
        </tr> 
    </table>

	<div id="maingrid"></div>
	<div id="resultPrint" style="display:none">
	   	<table width="100%">
			   	<thead>
				<tr>
					<th width="200">分摊参数编码</th>
					<th width="200">分摊参数名称</th>
					<th width="200">说明</th>
				   	</tr>
			   	</thead>
			   	<tbody></tbody>
	   	</table>
   	</div>
</body>
</html>
