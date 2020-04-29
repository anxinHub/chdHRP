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
        loadDict();//加载下拉框
    	//加载数据 
    	loadHead(null);	
		
        $("#type_code").ligerTextBox({width:160});
      
   
        $("#is_stop").ligerComboBox({width:160});
        
        $("#is_last").ligerComboBox({width:160});
		
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	  grid.options.parms.push({name:'type_code',value:$("#type_code").val()}); 
    	
    	  grid.options.parms.push({
  			name : 'is_stop',
  			value : liger.get("is_stop").getValue()
  		}); 
    	  
    	  grid.options.parms.push({
    			name : 'is_last',
    			value : liger.get("is_last").getValue()
    		}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '分类编码', name: 'type_code', align: 'left',
                    	 render : function(rowdata, rowindex,
  								value) {
 								
 								return "<a href=javascript:openUpdate('" + rowdata.group_id   + "|" + rowdata.hos_id   + "|" + rowdata.copy_code   + "|"  + rowdata.type_code +"')>"+rowdata.type_code+"</a>";

                     	 }
					 		},
					 		
                     { display: '分类名称', name: 'type_name', align: 'left'
					 		},
                   
					 		{ display: '是否停用', name: 'is_stop', align: 'left',
								render : function(rowdata, rowindex,
										value) {
									if(rowdata.is_stop == 0){
										return "否";
									}else{
										return "是";
									}
								}
						 },
				 		 { display: '上级编码', name: 'supper_code', align: 'left'
					 		},
					 		{ display: '是否末级', name: 'is_last', align: 'left',
					 			render : function(rowdata, rowindex,
										value) {
									if(rowdata.is_last == 0){
										return "否";
									}else{
										return "是";
									}
								}
					 		
					 		}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAssTypeSixEight.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad :false,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
				    					{ text: '添加', id:'add', click: itemclick, icon:'add' },
				    	                { line:true },
				    	                { text: '删除', id:'delete', click: itemclick,icon:'delete' },
										{ line:true },
						                { text: '导入', id:'import', click: itemclick,icon:'up' },
						                { line:true },	
										{ text: '打印', id:'print', click: printDate,icon:'print' },
							            { line:true },	
// 				    	                { text: '导出Excel', id:'export', click: exportExcel,icon:'pager' },
				    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code   + "|" + 
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
              		$.ligerDialog.open({url: 'assTypeSixEightAddPage.do?isCheck=false', height: 350,width: 450, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAssUsageDict(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
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
																this.type_code  +"@"+ 
																this.supper_code  
																); });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteAssTypeSixEight.do",{ParamVo : ParamVo.toString()},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
                    return;
                case "import":
                	 /*	importSpreadView("assUsageDictImportPage.do?isCheck=false");
               		$.ligerDialog.open({
                		url: 'assUsageDictImportPage.do?isCheck=false', 
                		height: 500,width: 800, title:'导入',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true });
               		*/
                	 var para = {
        					"column" : [ {
        						"name" : "type_code",
        						"display" : "用途编码",
        						"width" : "200",
        						"require" : true
        					}, {
        						"name" : "type_name",
        						"display" : "用途名称",
        						"width" : "200",
        						"require" : true
        					}, 
        					 {
        						"name" : "supper_code",
        						"display" : "上级编码",
        						"width" : "200",
        						"require" : true
        					}, 
        					{
        						"name" : "is_last",
        						"display" : "是否末级",
        						"width" : "200",
        						"require" : true
        					}, {
        						"name" : "is_stop",
        						"display" : "是否停用",
        						"width" : "200",
        						"require" : true
        					}

        					]
        				};
        				importSpreadView("hrp/ass/asstypesixeight/assTypeSixEightImport.do?isCheck=false",
        						para);
        				
                
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
		if("null"==vo[3]){
			return false;
			
		}
		var parm ="&group_id="+ 
		   vo[0]   +"&hos_id="+ 
		   vo[1]   +"&copy_code="+ 
		   vo[2]   +"&type_code="+ 
		   vo[3]; 	
		
    	$.ligerDialog.open({ url : 'assTypeSixEightUpdatePage.do?isCheck=false&' + parm,data:{}, height: 350,width: 450, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAssUsageDict(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    }
    function loadDict(){
            //字典下拉框
              $('#is_stop').ligerComboBox({
				data:[{id:1,text:'是'},{id:0,text:'否'}],
				valueField: 'id',
	            textField: 'text',
				cancelable:true,
				width : 160
				})
         }  
    
  //打印数据
// 	 function printDate(){
// 		//有数据直接打印
// 		if($("#resultPrint > table > tbody").html()!=""){
// 			lodopPrinterTable("resultPrint","开始打印","资产用途",true);
// 			return;
// 		}
		
// 		//重新查询数据，避免分页导致打印数据不全
// 		var manager = $.ligerDialog.waitting('系统正在准备打印数据,请稍候...');

// 		var printPara={
				
// 			usePager:false,
// 	        type_code : $("#type_code").val(),
//   	        is_stop : $("#is_stop").val() 
//          };
// 		ajaxJsonObjectByUrl("queryAssUsageDict.do",printPara,function (responseData){
// 			$.each(responseData.Rows,function(idx,item){ 
// 				 var trHtml="<tr>";
// 					 trHtml+="<td>"+item.type_code+"</td>"; 
// 					 trHtml+="<td>"+item.type_name+"</td>"; 
// 					 if (item.is_stop==0){
//                     	 trHtml+="<td>否</td>"; 
					 
// 				 }else{
// 					 trHtml+="<td>是</td>"; 
					 
// 				 }
// 				 trHtml+="</tr>";
// 				 $("#resultPrint > table > tbody").append(trHtml);
// 			});
// 			manager.close();
// 			//alert($("#resultPrint").html())
// 			lodopPrinterTable("resultPrint","开始打印","资产用途",true);
// 	    },true,manager);
// 		return;
// 	 }
	    function printDate(){
	    	
	    	if(grid.getData().length==0){
	    		
				$.ligerDialog.error("请先查询数据！");
				
				return;
			}
	    	
	    	var selPara={};
	    	
	    	$.each(grid.options.parms,function(i,obj){
	    		
	    		selPara[obj.name]=obj.value;
	    		
	    	});
	   		
			var dates = getCurrentDate();
	    	
	    	var cur_date = dates.split(";")[2];
	    	//跨所有列:计算列数
	    	var colspan_num = grid.getColumns(1).length-1;
	   		
	    	var printPara={
	       			title:'资产用途字典',
	       			head:[
	    				{"cell":0,"value":"单位: ${sessionScope.hos_name}","colspan":colspan_num,"br":true}
	       			],
	       			foot:[
	    				{"cell":0,"value":"主管:","colspan":2,"br":false} ,
						{"cell":2,"value":"复核人:","colspan":colspan_num-2,"br":true},
						{"cell":0,"value":"制单人： ${sessionScope.user_name}","colspan":2,"br":false},
						{"cell":2,"value":"打印日期: " + cur_date,"colspan":colspan_num-2,"br":true}
	       			],
	       			columns:grid.getColumns(1),
	       			headCount:2,//列头行数
	       			autoFile:true,
	       			type:3
	       	};
	   		ajaxJsonObjectByUrl("queryAssTypeSixEight.do?isCheck=false", selPara, function (responseData) {
	   			printGridView(responseData,printPara);
			});

	   		
	    }
	 
	 //导出数据
	 function exportExcel(){
		//有数据直接导出
		if($("#resultPrint > table > tbody").html()!=""){
			lodopExportExcel("resultPrint","导出Excel","资产用途集.xls",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');
		var exportPara={
			usePager:false,
	        type_code : $("#type_code").val(),
  	        is_stop : $("#is_stop").val() 
         };
		ajaxJsonObjectByUrl("queryAssTypeSixEight.do",exportPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
					 trHtml+="<td>"+item.type_code+"</td>"; 
					 trHtml+="<td>"+item.type_name+"</td>"; 
					 trHtml+="<td>"+item.is_stop+"</td>"; 
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			lodopExportExcel("resultPrint","导出Excel","资产用途.xls",true);
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
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">68分类编码：</td>
            <td align="left" class="l-table-edit-td"><input name="type_code" type="text" id="type_code" ltype="text"/></td>
            <td align="left"></td>
             <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否停用<span style="color:red">*</span>：</td>
                <td align="left" class="l-table-edit-td">
                	<!-- <select id="is_stop" name="is_stop" style="width: 135px;">
			                <option value="">请选择</option>
			               <option value="0">否</option>
			               <option value="1">是</option>
                	</select> -->
                	<input name="is_stop" type="text" id="is_stop"  />
                </td>
                <td align="left"></td>
                <td align="left"></td>
                  <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否末级：</td>
                <td align="left" class="l-table-edit-td">
                	<!-- <select id="is_stop" name="is_stop" style="width: 135px;">
			                <option value="">请选择</option>
			               <option value="0">否</option>
			               <option value="1">是</option>
                	</select> -->
                	<input name="is_last" type="text" id="is_last"  />
                </td>
                <td align="left"></td>
            </tr> 
    </table>
	<div id="maingrid"></div>
	<div id="resultPrint" style="display:none">
	   	<table width="100%">
			   	<thead>
				<tr>
                <th width="200">68分类编码</th>	
                <th width="200">68分类名称</th>	
                <th width="200">是否停用</th>	
				   	</tr>
			   	</thead>
			   	<tbody></tbody>
	   	</table>
   	</div>
</body>
</html>
