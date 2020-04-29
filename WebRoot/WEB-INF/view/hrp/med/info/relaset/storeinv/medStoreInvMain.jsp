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
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
		 loadHotkeys();
        $("#store_id").ligerTextBox({width:160});
        $("#inv_id").ligerTextBox({width:160});
        $("#low_limit").ligerTextBox({width:160});
        $("#stock_secu").ligerTextBox({width:160});
        $("#high_limit").ligerTextBox({width:160});
        $("#is_auto_supply").ligerTextBox({width:160});
		
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	  grid.options.parms.push({name:'store_id',value:$("#store_id").val()}); 
    	  grid.options.parms.push({name:'inv_id',value:$("#inv_id").val()}); 
    	  grid.options.parms.push({name:'low_limit',value:$("#low_limit").val()}); 
    	  grid.options.parms.push({name:'stock_secu',value:$("#stock_secu").val()}); 
    	  grid.options.parms.push({name:'high_limit',value:$("#high_limit").val()}); 
    	  grid.options.parms.push({name:'is_auto_supply',value:$("#is_auto_supply").val()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
     }
    //获取查询条件的数值
    function f_getWhere(){
    	if (!grid) return null;
        var clause = function (rowdata, rowindex){
                	if($("#store_id").val()!=""){
                		return rowdata.store_id.indexOf($("#store_id").val()) > -1;	
                	}
                	if($("#inv_id").val()!=""){
                		return rowdata.inv_id.indexOf($("#inv_id").val()) > -1;	
                	}
                	if($("#low_limit").val()!=""){
                		return rowdata.low_limit.indexOf($("#low_limit").val()) > -1;	
                	}
                	if($("#stock_secu").val()!=""){
                		return rowdata.stock_secu.indexOf($("#stock_secu").val()) > -1;	
                	}
                	if($("#high_limit").val()!=""){
                		return rowdata.high_limit.indexOf($("#high_limit").val()) > -1;	
                	}
                	if($("#is_auto_supply").val()!=""){
                		return rowdata.is_auto_supply.indexOf($("#is_auto_supply").val()) > -1;	
                	}
        };
        return clause; 
    }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '仓库ID', name: 'store_id', align: 'left'
					 		},
                     { display: '药品ID', name: 'inv_id', align: 'left'
					 		},
                     { display: '最低库存', name: 'low_limit', align: 'left'
					 		},
                     { display: '库存基数', name: 'stock_secu', align: 'left'
					 		},
                     { display: '最高库存', name: 'high_limit', align: 'left'
					 		},
                     { display: '是否自动补货', name: 'is_auto_supply', align: 'left'
					 		}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryMedStoreInv.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询（<u>E</u>）', id:'search', click: query,icon:'search' },
                     	{ line:true },
				    					{ text: '添加（<u>A</u>）', id:'add', click: add_open, icon:'add' },
				    	                { line:true },
				    	                { text: '删除（<u>D</u>）', id:'delete', click: remove,icon:'delete' },
											{ line:true }, 
				    	                { text: '导出Excel（<u>E</u>）', id:'export', click: exportExcel,icon:'pager' },
						                { line:true },
						                { text: '打印（<u>P</u>）', id:'print', click: printDate,icon:'print' },
						                { line:true },
						                { text: '下载导入模板（<u>B</u>）', id:'downTemplate', click:downTemplate,icon:'down' },
						                { line:true },
						                { text: '导入（<u>I</u>）', id:'import', click: imp,icon:'up' }
				    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code   + "|" + 
								rowdata.store_id   + "|" + 
								rowdata.inv_id 
							);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function add_open(){
    	
    	var index = layer.open({
					type : 2,
					title : '科室申请资产明细',
					shadeClose : false,
					shade : false,
					maxmin : true, //开启最大化最小化按钮
					area : [ '893px', '500px' ],
					content : 'medStoreInvAddPage.do?isCheck=false'
				});
				layer.full(index);
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
																this.copy_code   +"@"+ 
																this.store_id   +"@"+ 
																this.inv_id 
																) });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteMedStoreInv.do",{ParamVo : ParamVo},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
    	}
    function imp(){
    	
    	var index = layer.open({
					type : 2,
					title : '科室申请资产明细',
					shadeClose : false,
					shade : false,
					maxmin : true, //开启最大化最小化按钮
					area : [ '893px', '500px' ],
					content : 'medStoreInvImportPage.do?isCheck=false'
				});
				layer.full(index);
    	}	
    function downTemplate(){
    	
    	location.href = "downTemplate.do?isCheck=false";
    	}	
   
    function openUpdate(obj){
    		
		var vo = obj.split("|");
		var parm = 
			"group_id="+vo[0]   +"&"+ 
			"hos_id="+vo[1]   +"&"+ 
			"copy_code="+vo[2]   +"&"+ 
			"store_id="+vo[3]   +"&"+ 
			"inv_id="+vo[4] 
		 
    var index = layer.open({
					type : 2,
					title : '科室申请资产明细',
					shadeClose : false,
					shade : false,
					maxmin : true, //开启最大化最小化按钮
					area : [ '893px', '500px' ],
					content : 'medStoreInvImportPage.do?isCheck=false&' + parm
				});
				layer.full(index);	
    
    }
    function loadDict(){
            //字典下拉框
            
         }  
    //键盘事件
	  function loadHotkeys() {

		hotkeys('Q', query);

		hotkeys('A', add);
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
			lodopPrinterTable("resultPrint","开始打印","08110 仓库药品信息",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备打印数据,请稍候...');

		var printPara={
			usePager:false,
           store_id:$("#store_id").val(),
           inv_id:$("#inv_id").val(),
           low_limit:$("#low_limit").val(),
           stock_secu:$("#stock_secu").val(),
           high_limit:$("#high_limit").val(),
           is_auto_supply:$("#is_auto_supply").val()
         };
		ajaxJsonObjectByUrl("queryMedStoreInv.do",printPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
					 trHtml+="<td>"+item.store_id+"</td>"; 
					 trHtml+="<td>"+item.inv_id+"</td>"; 
					 trHtml+="<td>"+item.low_limit+"</td>"; 
					 trHtml+="<td>"+item.stock_secu+"</td>"; 
					 trHtml+="<td>"+item.high_limit+"</td>"; 
					 trHtml+="<td>"+item.is_auto_supply+"</td>"; 
				 trHtml+="</tr>";
				$("#resultPrint > table > tbody").empty();
				$("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			//alert($("#resultPrint").html())
			lodopPrinterTable("resultPrint","开始打印","08110 仓库药品信息",true);
	    },true,manager);
		return;
	 }
	
	 
	 //导出数据
	 function exportExcel(){
		//有数据直接导出
		if($("#resultPrint > table > tbody").html()!=""){
			lodopExportExcel("resultPrint","导出Excel","08110 仓库药品信息.xls",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');

		var exportPara={
			usePager:false,
           store_id:$("#store_id").val(),
           inv_id:$("#inv_id").val(),
           low_limit:$("#low_limit").val(),
           stock_secu:$("#stock_secu").val(),
           high_limit:$("#high_limit").val(),
           is_auto_supply:$("#is_auto_supply").val()
         };
		ajaxJsonObjectByUrl("queryMedStoreInv.do",exportPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
					 trHtml+="<td>"+item.store_id+"</td>"; 
					 trHtml+="<td>"+item.inv_id+"</td>"; 
					 trHtml+="<td>"+item.low_limit+"</td>"; 
					 trHtml+="<td>"+item.stock_secu+"</td>"; 
					 trHtml+="<td>"+item.high_limit+"</td>"; 
					 trHtml+="<td>"+item.is_auto_supply+"</td>"; 
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").empty();
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			lodopExportExcel("resultPrint","导出Excel","08110 仓库药品信息.xls",true);
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
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">仓库ID：</td>
            <td align="left" class="l-table-edit-td"><input name="store_id" type="text" id="store_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">药品ID：</td>
            <td align="left" class="l-table-edit-td"><input name="inv_id" type="text" id="inv_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">最低库存：</td>
            <td align="left" class="l-table-edit-td"><input name="low_limit" type="text" id="low_limit" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">库存基数：</td>
            <td align="left" class="l-table-edit-td"><input name="stock_secu" type="text" id="stock_secu" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">最高库存：</td>
            <td align="left" class="l-table-edit-td"><input name="high_limit" type="text" id="high_limit" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否自动补货：</td>
            <td align="left" class="l-table-edit-td"><input name="is_auto_supply" type="text" id="is_auto_supply" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
    </table>

	<div id="maingrid"></div>
	<div id="resultPrint" style="display:none">
	   	<table width="100%">
			<thead>
		 
			<tr>
                <th width="200">仓库ID</th>	
                <th width="200">药品ID</th>	
                <th width="200">最低库存</th>	
                <th width="200">库存基数</th>	
                <th width="200">最高库存</th>	
                <th width="200">是否自动补货</th>	
			</tr>
			   	</thead>
			   	<tbody></tbody>
	   	</table>
   	</div>
</body>
</html>
