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
        loadDict()//加载下拉框
    	//加载数据
    	loadHead();	
		 loadHotkeys();
        $("#sup_id").ligerTextBox({width:160,disabled:true});
        $("#sup_name").ligerTextBox({width:300,disabled:true});
		
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	  grid.options.parms.push({name:'sup_id',value:liger.get("sup_id").getValue().split(",")[0]}); 
    	  grid.options.parms.push({name:'sup_name',value:liger.get("sup_name").getValue()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
     }
    //获取查询条件的数值
    function f_getWhere(){
    	if (!grid) return null;
        var clause = function (rowdata, rowindex){
                	if($("#sup_id").val()!=""){
                		return rowdata.sup_id.indexOf(liger.get("sup_id").getValue().split(",")[0]) > -1;	
                	}
                	if($("#sup_name").val()!=""){
                		return rowdata.cert_state.indexOf(liger.get("sup_name").getValue()) > -1;	
                	}
        };
        return clause; 
    }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
            columns: [ 
                      { display: '证件类型', name: 'type_name', align: 'left', width:200,
                    	  render: function(rowdata,index,value){
                    		  return "<a href=javascript:openUpdate('"+rowdata.group_id+"|"+rowdata.hos_id+"|"+rowdata.copy_code+"|"+rowdata.cert_code+"|"+rowdata.sup_id+"')>"+rowdata.type_name+"</a>";
                    	  }
 					 		},
                      { display: '起始日期', name: 'start_date', align: 'left',width:100
 					 		},
                      { display: '结束日期', name: 'end_date', align: 'left',width:100
 					 		},
                      { display: '证件编码', name: 'cert_code', align: 'left', width:250
 					 		},
 					  { display: '发证机关', name: 'issuing_authority', align: 'left',width:200
 					 		},
 					  { display: '是否停用', name: 'cert_state', align: 'left',width:50,
 					 			render:function(rowdate,rowindex,value){
 					 				if(rowdate.cert_state == 1){
 					 					return "在用";
 					 				}else{
 					 					return "停用";
 					 				}
 					 			}
 					 		},
 					  { display: '备注', name: 'cert_memo', align: 'left',width:233}
 					 ],
                      dataAction: 'server',dataType: 'server',usePager:true,url:'queryMedVenCertDetailCert.do?sup_id=${sup_id}',
                      width: '100%',  height: '100%', checkbox: true,rownumbers:true, allowAdjustColWidth:false,
                      selectRowButtonOnly:true,heightDiff: 0,
                      toolbar: { items: [
                      	{ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' },
                     	{ line:true },
    					{ text: '添加（<u>A</u>）', id:'add', click: add_open, icon:'add' },
    	                { line:true },
    	                { text: '删除（<u>D</u>）', id:'delete', click: remove,icon:'delete' },
						{ line:true }, 
    	               /*  { text: '导出Excel（<u>E</u>）', id:'export', click: exportExcel,icon:'pager' },
		                { line:true },
		                { text: '打印（<u>P</u>）', id:'print', click: printDate,icon:'print' },
		                { line:true },
		                { text: '下载导入模板（<u>B</u>）', id:'downTemplate', click:downTemplate,icon:'down' },
		                { line:true },
		                { text: '导入（<u>I</u>）', id:'import', click: imp,icon:'up' }  */
     				]},
     				onDblClickRow : function (rowdata, rowindex, value)
     				{
 						openUpdate(
 								rowdata.group_id   + "|" + 
 								rowdata.hos_id   + "|" + 
 								rowdata.copy_code   + "|" + 
 								rowdata.cert_code   + "|" + 
 								rowdata.sup_id 
 							);
     				} 
                    });

         gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function add_open(){
    	
    	$.ligerDialog.open({url: 'medVenCertDetailAddPage.do?isCheck=false&sup_id=${sup_id}&sup_name=${sup_name}', height: 440,width: 800, title:'添加',
			modal:true,showToggle:false,showMax:true,showMin: false,isResize:true,
			buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveMedVenCertDetail(); },
				cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
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
								this.cert_code   +"@"+ 
								this.sup_id 
								) });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteMedVenCertDetail.do",{ParamVo : ParamVo.toString()},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            			parent.loadHead(null);
                            		}
                            	});
                        	}
                        }); 
                    }
    	}
   /*  function imp(){
    	
    	var index = layer.open({
					type : 2,
					title : '导入',
					shadeClose : false,
					shade : false,
					maxmin : true, //开启最大化最小化按钮
					area : [ '893px', '500px' ],
					content : 'medVenCertDetailImportPage.do?isCheck=false'
				});
				layer.full(index);
    	}	
    function downTemplate(){
    	
    	location.href = "downTemplate.do?isCheck=false";
    	}	 */
   
    function openUpdate(obj){
    		
		var vo = obj.split("|");
		var parm = 
			"group_id="+vo[0]   +"&"+ 
			"hos_id="+vo[1]   +"&"+ 
			"copy_code="+vo[2]   +"&"+ 
			"cert_code="+vo[3]   +"&"+ 
			"sup_id="+vo[4] 
		 
		$.ligerDialog.open({url: 'medVenCertDetailUpdatePage.do?isCheck=false&'+ parm, height: 440,width: 800, title:'供应商证件明细修改',
			modal:true,showToggle:false,showMax:true,showMin: false,isResize:true,
			buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveMedVenCertDetail(); },
				cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
	}
    
    function loadDict(){
            //字典下拉框
            //供货单位
    		autocomplete("#sup_id", "../../../../queryHosSupDict.do?isCheck=false", "id","text", true, true);
            liger.get("sup_id").setValue('${sup_id}');
            liger.get("sup_id").setText('${sup_code}');
         }  
    //键盘事件
	  function loadHotkeys() {

		hotkeys('Q', query);

		hotkeys('A', add);
		hotkeys('D', remove);

		/* hotkeys('B', downTemplate);

		hotkeys('E', exportExcel);

		hotkeys('P', printDate);
		hotkeys('I', imp); */
		

	 }
   /* //打印数据
	 function printDate(){
		//有数据直接打印
		if($("#resultPrint > table > tbody").html()!=""){
			lodopPrinterTable("resultPrint","开始打印","08605 供应商证件信息",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备打印数据,请稍候...');

		var printPara={
			usePager:false,
           	sup_id:liger.get("sup_id").getValue().split(",")[0],
         };
		ajaxJsonObjectByUrl("queryMedVenCertDetailCert.do?sup_id=${sup_id}",printPara,function (responseData){
			var trHtml='';
			$.each(responseData.Rows,function(idx,item){ 
					 trHtml+="<tr>";
					 trHtml+="<td>"+item.type_name+"</td>"; 
					 trHtml+="<td>"+item.start_date+"</td>"; 
					 trHtml+="<td>"+item.end_date+"</td>";
					 trHtml+="<td>"+item.cert_code+"</td>";
					 trHtml+="<td>"+item.issuing_authority+"</td>"; 
					 trHtml+="<td>"+item.cert_memo+"</td>";  
				 trHtml+="</tr>";
				$("#resultPrint > table > tbody").empty();
				$("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			//alert($("#resultPrint").html())
			lodopPrinterTable("resultPrint","开始打印","08605 供应商证件信息",true);
	    },true,manager);
		return;
	 }
	
	 
	 //导出数据
	 function exportExcel(){
		//有数据直接导出
		if($("#resultPrint > table > tbody").html()!=""){
			lodopExportExcel("resultPrint","导出Excel","08605 供应商证件信息.xls",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');

		var exportPara={
			usePager:false,
			sup_id:liger.get("sup_id").getValue().split(",")[0],
         };
		ajaxJsonObjectByUrl("queryMedVenCertDetailCert.do",exportPara,function (responseData){
			var trHtml ='';
			$.each(responseData.Rows,function(idx,item){ 
				 trHtml+="<tr>";
				 trHtml+="<td>"+item.type_name+"</td>"; 
				 trHtml+="<td>"+item.start_date+"</td>"; 
				 trHtml+="<td>"+item.end_date+"</td>";
				 trHtml+="<td>"+item.cert_code+"</td>";
				 trHtml+="<td>"+item.issuing_authority+"</td>"; 
				 trHtml+="<td>"+item.cert_memo+"</td>";  
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").empty();
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			lodopExportExcel("resultPrint","导出Excel","08605 供应商证件信息.xls",true);
	    },true,manager);
		return;
	 }		 */
    </script> 

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">供应商编码：</td>
            <td align="left" class="l-table-edit-td"><input name="sup_id" type="text" id="sup_id" ltype="text" /></td>
            <td align="left"></td>
             <td align="right" class="l-table-edit-td"  style="padding-left:20px;">供应商名称：</td>
            <td align="left" class="l-table-edit-td"><input name="sup_name" type="text" id="sup_name" value="${sup_name}" disabled="disabled" ltype="text" /></td>
        </tr> 
    </table>

	<div id="maingrid"></div>
	<div id="resultPrint" style="display:none">
	   	<table width="100%">
			<thead>
				<tr>
	                <th width="200">证件类型</th>	
	                <th width="200">起始日期</th>	
	                <th width="200">截止日期</th>
	                <th width="200">证件编码</th>	
	                <th width="200">发证机关</th>	
	                <th width="200">备注</th>	
				</tr>
		   	</thead>
		   	<tbody></tbody>
	   	</table>
   	</div>
</body>
</html>
