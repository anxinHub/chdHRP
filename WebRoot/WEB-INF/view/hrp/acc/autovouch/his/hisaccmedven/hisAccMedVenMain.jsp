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
    	
		$("#ven_code").ligerTextBox({width:180});
    	
    	$("#ven_name").ligerTextBox({width:180});
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
        
    	grid.options.parms.push({name:'ven_code',value:$("#ven_code").val()}); 
    	grid.options.parms.push({name:'ven_name',value:$("#ven_name").val()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '供应商编码', name: 'ven_code', align: 'left',
						render : function(rowdata, rowindex,
								value) {
								return "<a href=javascript:openUpdate('"+rowdata.group_id   + "|" + rowdata.hos_id   + "|" + rowdata.copy_code   + "|" + rowdata.ven_code +"')>"+rowdata.ven_code+"</a>";
						}
					 },
                     { display: '供应商名称', name: 'ven_name', align: 'left'
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryHisAccMedVen.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
    					{ text: '添加', id:'add', click: itemclick, icon:'add' },
    	                { line:true },
    	                { text: '删除', id:'delete', click: itemclick,icon:'delete' },
						/* { line:true }, 
    	                { text: '导出Excel', id:'export', click: exportExcel,icon:'pager' }, */
		                { line:true },
		                { text: '打印', id:'print', click: print,icon:'print' },
		                { line:true }/* ,
		                { text: '下载导入模板', id:'downTemplate', click:itemclick,icon:'down' },
		                { line:true },
		                { text: '导入', id:'import', click: itemclick,icon:'up' } */
    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code   + "|" + 
								rowdata.ven_code 
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
              		$.ligerDialog.open({url: 'hisAccMedVenAddPage.do?isCheck=false', height: 400,width: 500, title:'添加',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.save(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
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
							this.ven_code 
							)
                        });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	$.post("deleteHisAccMedVen.do",{ParamVo : ParamVo.toString()},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}else{
                            			$.ligerDialog.warn(responseData.error);
                            		}
                            	},"json");
                        	}
                        }); 
                    }
                    return;
				case "import":
                	$.ligerDialog.open({url: 'costChargeKindArrtImportPage.do?isCheck=false', height: 500,width: 800, title:'导入',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true });
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
			"group_id="+vo[0]   +"&"+ 
			"hos_id="+vo[1]   +"&"+ 
			"copy_code="+vo[2]   +"&"+ 
			"ven_code="+vo[3]; 
    	$.ligerDialog.open({ url : 'updateHisAccMedVenPage.do?isCheck=false&' + parm,data:{}, height:400,width: 500, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.save(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    }
    function loadDict(){
            //字典下拉框
    }  
    
 //导出数据
 function exportExcel(){
	//有数据直接导出
	if($("#resultPrint > table > tbody").html()!=""){
		lodopExportExcel("resultPrint","收费类别导出Excel","收费类别列表.xls",true);
		return;
	}
	
	//重新查询数据，避免分页导致打印数据不全
	var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');
	
	var exportPara={
		usePager:false,
		charge_kind_code:$("#charge_kind_code").val(),
    	income_type_id:liger.get("income_type_id").getValue(),
    	//charge_kind_name:$("#charge_kind_name").val(),
    	income_item_id_in:liger.get("income_item_id_in").getValue(),
    	income_item_id_out:liger.get("income_item_id_out").getValue()
        };
	ajaxJsonObjectByUrl("queryCostChargeKindArrt.do",exportPara,function (responseData){
		$.each(responseData.Rows,function(idx,item){ 
			 var trHtml="<tr>";
                    trHtml+="<td>"+item.charge_kind_code+"</td>"; 
                    trHtml+="<td>"+item.income_type_name+"</td>"; 
                    trHtml+="<td>"+item.charge_kind_name+"</td>"; 
                    trHtml+="<td>"+item.income_item_name_in+"</td>"; 
                    trHtml+="<td>"+item.income_item_name_out+"</td>"; 
			 trHtml+="</tr>";
			 $("#resultPrint > table > tbody").append(trHtml);
		});
		manager.close();
		lodopExportExcel("resultPrint","收费类别导出Excel","收费类别列表.xls",true);
    },true,manager);
	return;
 }	

	//通过Spread组件进行打印
		function print() {
			//设置表头
			if(grid.getData().length==0){
	    		
				$.ligerDialog.error("请先查询数据！");
				
				return;
			}
	    	var heads={
		      		  //"isAuto": true/false 默认true，页眉右上角默认显示页码
		      		  "rows": [
		      	  	         
		      		  ]
		      	};
		   		
	   		var printPara={
	   			rowCount:1,
	   			title:'HIS药品供应商',
	   			columns: JSON.stringify(grid.getPrintColumns()),//表头
	   			class_name: "com.chd.hrp.acc.service.autovouch.his.HisAccMedVenService",
				method_name: "queryHisAccMedVenPrint",
				bean_name: "hisAccMedVenService",
				heads: JSON.stringify(heads)//表头需要打印的查询条件,可以为空
	   			};
	    	
	   		//执行方法的查询条件
	   		$.each(grid.options.parms,function(i,obj){
	   			printPara[obj.name]=obj.value;
	    	});
	   		
	    	officeGridPrint(printPara);
	 
	 
	};
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">供应商编码：</td>
            <td align="left" class="l-table-edit-td"><input name="ven_code" type="text" id="ven_code" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">供应商名称：</td>
            <td align="left" class="l-table-edit-td"><input name="ven_name" type="text" id="ven_name" /></td>
        </tr> 
	
        <tr>
        </tr> 
    </table>

	<div id="maingrid"></div>
	
</body>
</html>
