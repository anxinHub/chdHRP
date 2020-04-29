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
		
        $("#pay_term_code").ligerTextBox({width:160});
        $("#pay_term_name").ligerTextBox({width:160});
        
    });
    //查询
    function  query(){
    	grid.options.parms=[];
    	grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	 
    	grid.options.parms.push({name:'pay_term_code',value:$("#pay_term_code").val()}); 
    	grid.options.parms.push({name:'pay_term_name',value:$("#pay_term_name").val()}); 
    	

    	//加载查询条件
    	grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
     }
    //获取查询条件的数值
    function f_getWhere(){
    	if (!grid) return null;
        var clause = function (rowdata, rowindex){
                	
                	if($("#pay_term_code").val()!=""){
                		return rowdata.pay_term_code.indexOf($("#pay_term_code").val()) > -1;	
                	}
                	if($("#pay_term_name").val()!=""){
                		return rowdata.pay_term_name.indexOf($("#pay_term_name").val()) > -1;	
                	}
                	
        };
        return clause; 
    }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                    
                     { display: '付款条件编码', name: 'pay_term_code', align: 'left',
                    	 render : function(rowdata, rowindex,value) {
								return "<a href=javascript:openUpdate('"+rowdata.pay_term_code   + "|" + 
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code   + "|" + 
								rowdata.acc_year  +"')>"+rowdata.pay_term_code+"</a>"
							}
                     },
                     { display: '付款条件名称', name: 'pay_term_name', align: 'left'},
                     { display: '是否停用', name: 'is_stop', align: 'left',
                    	 render : function(rowdata, rowindex,value) {
								if(rowdata.is_stop == 0){
									return "否";
								}else{
									return "是"
								}
							}
                     }
                    ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryMedPayTerm.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
				    					{ text: '添加', id:'add', click: itemclick, icon:'add' },
				    	                { line:true },
				    	                { text: '删除', id:'delete', click: itemclick,icon:'delete' },
										{ line:true }, 
				    	                
										/*{ text: '导出Excel', id:'export', click: exportExcel,icon:'pager' },
						                { line:true },
						                { text: '打印', id:'print', click: printDate,icon:'print' },
						                { line:true },
						                
						                { text: '下载导入模板', id:'downTemplate', click:itemclick,icon:'down' },
						                { line:true },
						                */
						                { text: '导入', id:'import', click: itemclick,icon:'up' },
						                { line:true }
				    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code   + "|" + 
								rowdata.pay_term_code   
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
              		$.ligerDialog.open({url: 'medPayTermAddPage.do?isCheck=false', height: 300,width: 400, 
              				title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,
              				buttons: [ 
              				           { text: '确定', onclick: function (item, dialog) { dialog.frame.saveMedPayTerm(); },cls:'l-dialog-btn-highlight' }, 
              				           { text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
              				         ] 
              		});
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
																this.pay_term_code   
																) });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteMedPayTerm.do",{ParamVo : ParamVo.toString()},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
                    return;
                case "import":
                	
                	parent.$.ligerDialog.open({
            			url : 'hrp/med/info/basic/medpayterm/medPayTermImportPage.do?isCheck=false',
            			data : {
            				columns : grid.columns,
            				grid : grid
            			},
            			height : 300,
            			width : 450,
            			title : '付款条件导入',
            			modal : true,
            			showToggle : false,
            			showMax : true,
            			showMin : false,
            			isResize : true,
            			parentframename : window.name, //用于parent弹出层调用本页面的方法或变量
            		});
                	
                	
                	//$.ligerDialog.open({url: 'medPayTermImportPage.do?isCheck=false', height: 500,width: 800, title:'导入',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true });
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
		var parm = "pay_term_code="+vo[0];
		
    	$.ligerDialog.open({ url : 'medPayTermUpdatePage.do?isCheck=false&' + parm,data:{}, height: 300,width: 400, 
    			title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
    			buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveMedPayTerm(); },cls:'l-dialog-btn-highlight' }, 
    			           { text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
    				     ] 
    	});

    }
    function loadDict(){
            //字典下拉框
            
    }  
    
  //打印数据
	 function printDate(){
		//有数据直接打印
		if($("#resultPrint > table > tbody").html()!=""){
			lodopPrinterTable("resultPrint","开始打印","MED_PAY_TERM",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备打印数据,请稍候...');

		var printPara={
			usePager:false,
           pay_term_id:$("#pay_term_id").val(),
           pay_term_code:$("#pay_term_code").val(),
           pay_term_name:$("#pay_term_name").val(),
           is_stop:$("#is_stop").val(),
           note:$("#note").val()
         };
		ajaxJsonObjectByUrl("queryMedPayTerm.do",printPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
					 trHtml+="<td>"+item.pay_term_id+"</td>"; 
					 trHtml+="<td>"+item.pay_term_code+"</td>"; 
					 trHtml+="<td>"+item.pay_term_name+"</td>"; 
					 trHtml+="<td>"+item.is_stop+"</td>"; 
					 trHtml+="<td>"+item.note+"</td>"; 
				 trHtml+="</tr>";
				$("#resultPrint > table > tbody").empty();
				$("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			//alert($("#resultPrint").html())
			lodopPrinterTable("resultPrint","开始打印","MED_PAY_TERM",true);
	    },true,manager);
		return;
	 }
	
	 
	 //导出数据
	 function exportExcel(){
		//有数据直接导出
		if($("#resultPrint > table > tbody").html()!=""){
			lodopExportExcel("resultPrint","导出Excel","MED_PAY_TERM.xls",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');

		var exportPara={
			usePager:false,
           pay_term_id:$("#pay_term_id").val(),
           pay_term_code:$("#pay_term_code").val(),
           pay_term_name:$("#pay_term_name").val(),
           is_stop:$("#is_stop").val(),
           note:$("#note").val()
         };
		ajaxJsonObjectByUrl("queryMedPayTerm.do",exportPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
					 trHtml+="<td>"+item.pay_term_id+"</td>"; 
					 trHtml+="<td>"+item.pay_term_code+"</td>"; 
					 trHtml+="<td>"+item.pay_term_name+"</td>"; 
					 trHtml+="<td>"+item.is_stop+"</td>"; 
					 trHtml+="<td>"+item.note+"</td>"; 
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").empty();
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			lodopExportExcel("resultPrint","导出Excel","MED_PAY_TERM.xls",true);
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
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">付款条件编码：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="pay_term_code" type="text" id="pay_term_code" ltype="text" validate="{required:true,maxlength:20}" />
            </td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">付款条件名称：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="pay_term_name" type="text" id="pay_term_name" ltype="text" validate="{required:true,maxlength:20}" />
            </td>
            <td align="left"></td>
        </tr> 
        
    </table>

	<div id="maingrid"></div>
	<div id="resultPrint" style="display: none">
		<table width="100%">
			<thead>

				<tr>
					<th width="200">付款条件ID</th>
					<th width="200">付款条件编码</th>
					<th width="200">付款条件名称</th>
					<th width="200">0否，1是</th>
					<th width="200">备注</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>1</td>
					<td>1</td>
					<td>1</td>
					<td>1</td>
					<td>1</td>
				</tr>
			</tbody>
		</table>
	</div>
</body>
</html>
