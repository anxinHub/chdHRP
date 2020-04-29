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
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	  grid.options.parms.push({name:'inv_id',value:$("#inv_id").val()}); 
    	  grid.options.parms.push({name:'inv_model',value : $("#inv_model").val()});
    	//加载查询条件
    	grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '药品编码', name: 'inv_code', align: 'left'/* ,
                    	 render: function(rowdata,index,value){
                    		 return "<a href=javascript:openUpdate('"+rowdata.group_id+"|"+rowdata.hos_id+"|"+rowdata.copy_code+"|"+rowdata.inv_id+"|"+rowdata.pack_code+"')>"+rowdata.inv_code+"</a>";
                    	 } */
					 		},
				 	 { display: '药品名称', name: 'inv_name', align: 'left'
					 		},
					 { display: '规格型号', name: 'inv_model', align: 'left'
							},
                     { display: '生产厂商', name: 'fac_name', align: 'left'
					 		},
					 { display: '计量单位', name: 'unit_name', align: 'left'
					 		},
                     { display: '包装单位', name: 'pack_name', align: 'center',
					 			valueField : 'id',textField: 'text',
					 			editor: { 
					 				type: 'select', 
					 				url:'../../../queryHosPackage.do?isCheck=false',
					 				valueField : 'id',textField: 'text',
					 				keySupport: true,
					 				autocomplete : true 
					 			}

					 		},
                     { display: '转换数量', name: 'map_amount', align: 'left',editor: { type: 'int'} 
					 		}
                     ],
                     dataAction: 'server',dataType: 'server',url:'queryMedInvUnitMap.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,enabledEdit: true,
                     onAfterEdit : f_onAfterEdit,
                     usePager:false,
                     isScroll: true,
                     noInsertByEnter:false,
                     selectRowButtonOnly:true,heightDiff: 0,
                     toolbar: { items: [
                     	{ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' },
                     	{ line:true },
                     	{ text: '选择药品（<u>S</u>）', id:'select', click: selectMedInv,icon:'add' },
						{ line:true },
    					{ text: '保存（<u>A</u>）', id:'add', click: add_open, icon:'add' },
    	                { line:true },
    	                { text: '删除（<u>D</u>）', id:'delete', click: remove,icon:'delete' },
						{ line:true }
    	                /*, 
    	                { text: '导出Excel（<u>E</u>）', id:'export', click: exportExcel,icon:'pager' },
		                { line:true },
		                { text: '打印（<u>P</u>）', id:'print', click: printDate,icon:'print' },
		                { line:true },
		                 { text: '下载导入模板（<u>B</u>）', id:'downTemplate', click:downTemplate,icon:'down' },
		                { line:true },
		                { text: '导入（<u>I</u>）', id:'import', click: imp,icon:'up' } */
    				]},
    				/* onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code   + "|" + 
								rowdata.inv_id   + "|" + 
								rowdata.pack_code 
							);
    				}  */
                   });
                 
    	
    	
        gridManager = $("#maingrid").ligerGetGridManager();
    }
    function selectMedInv(){
 		$.ligerDialog.open({url: 'medInvPage.do?isCheck=false',height: 500,width: 1000, 
 				title:'选择 药品',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true
 					});
	}
    
    
    
    function f_onAfterEdit(e) {
 		
 		
 		  if(e.column.name=='map_amount' && e.rowindex==11)
     	{
 			return false;
         	
        }
 		
 		
		return true;
	}
    
    function add_open(){
    	
    	grid.endEdit(); 
	    	var addData = gridManager.getAdded();
	    	var updateData = grid.getUpdated();
	    	var ParamVo =[];
	    	msg = "";
	    	rowm = "";
	    	
    		if(updateData.length > 0){
    			
    			$.each(updateData,function(i, v){
    	    		rowm = "";
    	 				//如果批号为空给默认批号用于判断
    					if(!v.pack_name){
    						rowm+= '包装单位 ';
    					}
    					if (!v.map_amount) {
    						rowm+="[转换数量]、";
    					}  
    					if(rowm != ""){
    						rowm = "第"+(i+1)+"行" + rowm.substring(0, rowm.length-1) + "不能为空并且转换数量不能为0。" + "<br>";
    					}
    					msg += rowm;
    					
    	 		});
    	    	if(msg != ""){
    	 			$.ligerDialog.warn(msg);  
    				return false;  
    	 		}
    			
    			
	            $(updateData).each(function (){					
						ParamVo.push(
						this.group_id   +"@"+ 
						this.hos_id   +"@"+ 
						this.copy_code   +"@"+ 
						this.inv_id   +"@"+ 
						this.pack_code   +"@"+
						this.unit_code   +"@"+
						this.map_amount  +"@"+
	 					this.pack_code 
						) });
	            
	    	}
	    	if(addData.length > 0){
	    		
	    		$.each(addData,function(i, v){
		    		rowm = "";
		 				//如果批号为空给默认批号用于判断
						if(!v.pack_name){
							rowm+= '包装单位 ';
						}
						if (!v.map_amount) {
							rowm+="[转换数量]、";
						}  
						if(rowm != ""){
							rowm = "第"+(i+1)+"行" + rowm.substring(0, rowm.length-1) + "不能为空并且转换数量不能为0。" + "<br>";
						}
						msg += rowm;
						
		 		});
		    	if(msg != ""){
		 			$.ligerDialog.warn(msg);  
					return false;  
		 		}
	    		
	             $(addData).each(function (){					
	 					ParamVo.push(
	 					this.group_id   +"@"+ 
	 					this.hos_id   +"@"+ 
	 					this.copy_code   +"@"+ 
	 					this.inv_id   +"@"+ 
	 					this.pack_name   +"@"+
	 					this.unit_code   +"@"+
	 					this.map_amount +"@"+
	 					this.pack_code 
	 					) });
	             
	    	}
	    	/* alert(ParamVo)
	    	alert(ParamVo.toString().length); */
	    	if(ParamVo.toString().length >0 ){
	    		ajaxJsonObjectByUrl("saveBatchMedInvUnitMap.do",{ParamVo : ParamVo.toString()},function (responseData){
	        		if(responseData.state=="true"){
	        			query();
	        		}
	        	});
	    	}else{
	    		$.ligerDialog.error('无数据需要保存');
	    	}
        	
        }
    function remove(){
    	
    	gridManager.deleteSelectedRow();
    	
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
								this.inv_id   +"@"+ 
								this.pack_code 
								) });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteMedInvUnitMap.do",{ParamVo : ParamVo.toString()},function (responseData){
                            		if(responseData.state=="true"){
                            			
                            			
                            			//query();
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
					content : 'medInvUnitMapImportPage.do?isCheck=false'
				});
				layer.full(index);
    	}	
    function downTemplate(){
    	
    	location.href = "downTemplate.do?isCheck=false";
    	}	
   
    /* function openUpdate(obj){
    		
		var vo = obj.split("|");
		var parm = 
			"group_id="+vo[0]   +"&"+ 
			"hos_id="+vo[1]   +"&"+ 
			"copy_code="+vo[2]   +"&"+ 
			"inv_id="+vo[3]   +"&"+ 
			"pack_code="+vo[4] 
		 
    var index = layer.open({
					type : 2,
					title : '科室申请资产明细',
					shadeClose : false,
					shade : false,
					maxmin : true, //开启最大化最小化按钮
					area : [ '893px', '500px' ],
					content : 'medInvUnitMapImportPage.do?isCheck=false&' + parm
				});
				layer.full(index);
    
    } */
    function loadDict(){
            //字典下拉框
    	 	$("#inv_id").ligerTextBox({width:160});
    	 	$("#inv_model").ligerTextBox({width:160});
         }  
    //键盘事件
	  function loadHotkeys() {

		hotkeys('Q', query);

		hotkeys('A', add_open);
		//hotkeys('U', update);
		hotkeys('D', remove);
		hotkeys('S', selectMedInv);
		hotkeys('B', downTemplate);

		hotkeys('E', exportExcel);

		hotkeys('P', printDate);
		hotkeys('I', imp);
		

	 }
  //打印数据
	 function printDate(){
		//有数据直接打印
		if($("#resultPrint > table > tbody").html()!=""){
			lodopPrinterTable("resultPrint","开始打印","08116 药品包装单位关系表",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备打印数据,请稍候...');

		var printPara={
			usePager:false,
           inv_id:$("#inv_id").val(),
         };
		ajaxJsonObjectByUrl("queryMedInvUnitMap.do",printPara,function (responseData){
			var trHtml ='';
			$.each(responseData.Rows,function(idx,item){ 
				 	 trHtml+="<tr>";
					 trHtml+="<td>"+item.inv_code+"</td>";
					 trHtml+="<td>"+item.inv_name+"</td>";
					 trHtml+="<td>"+item.inv_model+"</td>";
					 trHtml+="<td>"+item.fac_name+"</td>";
					 trHtml+="<td>"+item.unit_name+"</td>";
					 trHtml+="<td>"+item.pack_name+"</td>";
					 trHtml+="<td>"+item.map_amount+"</td>"; 
				 trHtml+="</tr>";
				$("#resultPrint > table > tbody").empty();
				$("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			//alert($("#resultPrint").html())
			lodopPrinterTable("resultPrint","开始打印","08116 药品包装单位关系表",true);
	    },true,manager);
		return;
	 }
	
	 
	 //导出数据
	 function exportExcel(){
		//有数据直接导出
		if($("#resultPrint > table > tbody").html()!=""){
			lodopExportExcel("resultPrint","导出Excel","08116 药品包装单位关系表.xls",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');

		var exportPara={
			usePager:false,
           inv_id:$("#inv_id").val(),
         };
		ajaxJsonObjectByUrl("queryMedInvUnitMap.do",exportPara,function (responseData){
			var trHtml ='';
			$.each(responseData.Rows,function(idx,item){ 
				 	 trHtml+="<tr>";
				 	trHtml+="<td>"+item.inv_code+"</td>";
					 trHtml+="<td>"+item.inv_name+"</td>";
					 trHtml+="<td>"+item.inv_model+"</td>";
					 trHtml+="<td>"+item.fac_name+"</td>";
					 trHtml+="<td>"+item.unit_name+"</td>";
					 trHtml+="<td>"+item.pack_name+"</td>";
					 trHtml+="<td>"+item.map_amount+"</td>";  
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").empty();
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			lodopExportExcel("resultPrint","导出Excel","08116 药品包装单位关系表.xls",true);
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
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">药品信息：</td>
            <td align="left" class="l-table-edit-td"><input name="inv_id" type="text" id="inv_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
             <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>规格型号:</b></td>
            <td align="left" class="l-table-edit-td">
            	<input name="inv_model" type="text" id="inv_model" ltype="text" validate="{maxlength:20}" />
            </td>
            <td align="left"></td>
        </tr> 
    </table>

	<div id="maingrid"></div>
	<div id="resultPrint" style="display:none">
	   	<table width="100%">
			<thead>
		 
			<tr>
                <th width="200">药品编码</th>
                <th width="200">药品名称</th>
                <th width="200">规格型号</th>		
                <th width="200">生产厂商</th>	
                <th width="200">计量单位</th>
                <th width="200">包装单位</th>		
                <th width="200">转换数量</th>	
			</tr>
			   	</thead>
			   	<tbody></tbody>
	   	</table>
   	</div>
</body>
</html>
