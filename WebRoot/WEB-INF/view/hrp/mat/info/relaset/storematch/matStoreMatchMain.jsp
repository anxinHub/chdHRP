
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%> 
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
    var userUpdateStr;
    $(function ()
    {
        loadDict();//加载下拉框
    	//加载数据
    	loadHead(null);	
		 loadHotkeys();
        $("#store_match_id").ligerTextBox({width:160});
        $("#store_match_name").ligerTextBox({width:160});
        $("#store_id").ligerTextBox({width:160});
		
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	  grid.options.parms.push({name:'store_match_id',value:liger.get("store_match_name").getValue()}); 
        
    	  grid.options.parms.push({name:'store_id',value:liger.get("store_id").getValue().split(",")[0]}); 

    	//加载查询条件
    	grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
     }
    //获取查询条件的数值
    function f_getWhere(){
    	if (!grid) return null;
        var clause = function (rowdata, rowindex){
                	if($("#store_match_id").val()!=""){
                		return rowdata.store_match_id.indexOf($("#store_match_id").val()) > -1;	
                	}
                	if($("#store_match_name").val()!=""){
                		return rowdata.store_match_name.indexOf($("#store_match_name").val()) > -1;	
                	}
                	if($("#store_id").val()!=""){
                		return rowdata.store_id.indexOf($("#store_id").val()) > -1;	
                	}
        };
        return clause; 
    }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '配套表编码', name: 'store_match_code', align: 'left',render:
                    	 function(rowdata,rowindex,value){
	                    	 return '<a href=javascript:openUpdate("' 
								+ rowdata.store_match_id
								+','
								+rowdata.store_id
								
								+ '")>'+rowdata.store_match_code+'</a>';

                     	 }
					 		},
                     { display: '配套表名称', name: 'store_match_name', align: 'left'
					 		},
                     { display: '仓库名称', name: 'store_name', align: 'left'
					 		}
                     ], 
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatStoreMatch.do?isCheck=false',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
				    					{ text: '添加', id:'add', click: itemclick, icon:'add' },
				    	                { line:true },
				    	                { text: '删除', id:'delete', click: itemclick,icon:'delete' }
				    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.store_match_id + "," + 
								rowdata.store_id   
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
                	parent.$.ligerDialog.open({
              			url: 'hrp/mat/info/relaset/matStoreMatchAddPage.do?isCheck=false', height: 660,width: 1300, title:'添加',
              			modal:true,showToggle:false,showMax:true,showMin: true,isResize:true,
            			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
            			buttons: [ 
               		           { text: '确定', onclick: function (item, dialog) { 
               		        	   dialog.frame.saveMatStoreMatch(); },cls:'l-dialog-btn-highlight' 
               		           }, { text: '关闭', onclick: function (item, dialog) { dialog.close(); } 
               		           } 
               		          ]
              		})
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
 							this.group_id+"@"+
 							this.hos_id+"@"+
 							this.copy_code+"@"+
 							this.store_match_id
 							)
                         });
                       $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteMatStoreMatch.do?paramVo="+Param,{},function (responseData){
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
    	
    
    function imp(){
    	
    	var index = layer.open({
					type : 2,
					title : '科室申请资产明细',
					shadeClose : false,
					shade : false,
					maxmin : true, //开启最大化最小化按钮
					area : [ '893px', '500px' ],
					content : 'matStoreMatchImportPage.do?isCheck=false'
				});
				layer.full(index);
    	}	
    function downTemplate(){
    	
    	location.href = "downTemplate.do?isCheck=false";
    
    }	
   
    function openUpdate(obj){
    	parent.$.ligerDialog.open({
  			url: 'hrp/mat/info/relaset/matStoreMatchUpdatePage.do?isCheck=false&para='+obj, height: 660,width: 1300, 
  			title:'修改',
  			modal:true,showToggle:false,showMax:true,showMin: true,isResize:true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
			buttons: [ 
   		           { text: '保存', onclick: function (item, dialog) { 
   		        	   dialog.frame.save(); },cls:'l-dialog-btn-highlight' 
   		           }, { text: '关闭', onclick: function (item, dialog) { dialog.close(); } 
   		           } 
   		          ]
  		});
    
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
			lodopPrinterTable("resultPrint","开始打印","04113 物资仓库配套表",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备打印数据,请稍候...');

		var printPara={
			usePager:false,
           store_match_id:$("#store_match_id").val(),
           store_match_name:$("#store_match_name").val(),
           store_id:$("#store_id").val()
         };
		ajaxJsonObjectByUrl("queryMatStoreMatch.do",printPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
					 trHtml+="<td>"+item.store_match_id+"</td>"; 
					 trHtml+="<td>"+item.store_match_name+"</td>"; 
					 trHtml+="<td>"+item.store_id+"</td>"; 
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
           store_match_id:$("#store_match_id").val(),
           store_match_name:$("#store_match_name").val(),
           store_id:$("#store_id").val()
         };
		ajaxJsonObjectByUrl("queryMatStoreMatch.do?isCheck=false",exportPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
					 trHtml+="<td>"+item.store_match_id+"</td>"; 
					 trHtml+="<td>"+item.store_match_name+"</td>"; 
					 trHtml+="<td>"+item.store_id+"</td>"; 
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
 		autocomplete("#store_id","../../queryMatStore.do?isCheck=false","id","text",true,true);//仓库信息
         
 		autocomplete("#store_match_name","../../queryMatStoreMatchRead.do?isCheck=false","id","text",true,true);//配套表信息
 		
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
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">配套表名称：</td>
            <td align="left" class="l-table-edit-td"><input name="store_match_name" type="text" id="store_match_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">仓库信息：</td>
            <td align="left" class="l-table-edit-td"><input name="store_id" type="text" id="store_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
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
			</tr>
			   	</thead>
			   	<tbody></tbody>
	   	</table>
   	</div>
</body>
</html>
