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
        
        $("#impower_code").ligerTextBox({width:160});
        $("#impower_name").ligerTextBox({width:160});
		
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	  grid.options.parms.push({name:'impower_code',value:$("#impower_code").val()}); 
    	  grid.options.parms.push({name:'sup_id',value:liger.get("sup_id").getValue()}); 
    	  grid.options.parms.push({name:'sup_id',value:liger.get("sup_id_b").getValue()}); 
    	  grid.options.parms.push({name:'fac_id',value:liger.get("fac_id").getValue()}); 
    	  grid.options.parms.push({name:'impower_start_date',value:$("#impower_start_date").val()});
    	  grid.options.parms.push({name:'impower_end_date',value : $("#impower_end_date").val()});

    	//加载查询条件
    	grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '授权编码', name: 'impower_code', align: 'left',
                    	 render: function(rowdata, index, value){
                    		 return "<a href=javascript:openUpdate('"+rowdata.group_id+"|"+ rowdata.hos_id+"|"+ rowdata.copy_code+"|"+rowdata.impower_id+"')>"+rowdata.impower_code+"</a>";
                    	 }
					 		},
                     { display: '授权单位', name: 'sup_name', align: 'left',width: '200'
					 		},
					 { display: '被授权单位', name: 'sup_name_b', align: 'left',width: '200'
					 		},
					 { display: '起始日期', name: 'impower_start_date', align: 'left'
					 		},
					 { display: '截止日期', name: 'impower_end_date', align: 'left'
					 		},
					 { display: '生产厂商', name: 'fac_name', align: 'left',width: '200'
						 	},
					 { display: '联系人', name: 'impower_person', align: 'left'
					 		},
					 { display: '联系电话', name: 'impower_tel', align: 'left'
					 		},
					 { display: '手机', name: 'impower_mobile', align: 'left'
					 		},
					 { display: '存档位置', name: 'file_address', align: 'left'
					 		},
					 { display: '文档路径', name: 'file_path', align: 'left'
					 		},
                     { display: '是否停用', name: 'is_stop', align: 'left',
					 			render: function(rowdata, index, value){
					 				if(rowdata.is_stop == 0 ){
					 					return "否" ;
					 				}else{
					 					return "是" ;
					 				}
					 			}
					 		}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatInvImpower.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,heightDiff: -20,
                     toolbar: { items: [
                     	{ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' },
                     	{ line:true },
    					{ text: '添加（<u>A</u>）', id:'add', click: add_open, icon:'add' },
    	                { line:true },
    	                { text: '删除（<u>D</u>）', id:'delete', click: remove,icon:'delete' },
						{ line:true }, 
    	               
    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code   + "|" + 
								rowdata.impower_id 
							);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
	function add_open(){
		//$.ligerDialog.open({url: 'matInvImpowerAddPage.do?isCheck=false', height: 280,width: 400, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveMatInvImpower(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
		parent.$.ligerDialog.open({
			title: '添加',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/mat/info/basic/impower/matInvImpowerAddPage.do?isCheck=false',
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});  
	
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
	   						this.impower_id  +"@"+ 
	   						this.impower_code 
	   						) });
	            $.ligerDialog.confirm('确定删除?', function (yes){
	            	if(yes){
	                	ajaxJsonObjectByUrl("deleteMatInvImpower.do?ParamVo="+ParamVo,{ParamVo : ParamVo},function (responseData){
	                		if(responseData.state=="true"){
	                			query();
	                		}
	                	});
	            	}
	            }); 
	        }
	}
    function openUpdate(obj){
    	
    	var vo = obj.split("|");
		var parm = "group_id="+vo[0]+"&hos_id="+ vo[1] +"&copy_code="+vo[2] +"&impower_id="+ vo[3];
		parent.$.ligerDialog.open({
			title: '修改',
			height: $(window).height(),
			width: $(window).width(),
			selectedRow: vo[4],
			url: 'hrp/mat/info/basic/impower/matInvImpowerUpdatePage.do?isCheck=false&' + parm.toString(),
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});  
    }
    function loadDict(){
            //字典下拉框
    	autocomplete("#sup_id","../../../queryHosSup.do?isCheck=false","id","text",true,true);
    	autocomplete("#sup_id_b","../../../queryHosSup.do?isCheck=false","id","text",true,true);
    	autocomplete("#fac_id","../../../queryHosFac.do?isCheck=false","id","text",true,true);
         } 
  //键盘事件
	  function loadHotkeys() {
	 	
		hotkeys('Q', query);
	
		hotkeys('A', add_open);
		hotkeys('D', remove);
	
/* 		hotkeys('T', downTemplate); */
	
	/* 	hotkeys('E', exportExcel); */
	
	/* 	hotkeys('P', printDate); */
	/* 	hotkeys('I', imp); */
	}
    
//   //打印数据
// 	 function printDate(){
// 	  debugger;
// 		 //有数据直接打印
// 		if($("#resultPrint > table > tbody").html() !=""){
// 			lodopPrinterTable("resultPrint","开始打印","04601 材料证件类型字典",true);
// 			return;
// 		}  
		
// 		//重新查询数据，避免分页导致打印数据不全
// 		var manager = $.ligerDialog.waitting('系统正在准备打印数据,请稍候...');

// 		var printPara={
// 			usePager:false,
//            type_code:$("#type_code").val(),
//            type_name:$("#type_name").val(),
//          };
// 		ajaxJsonObjectByUrl("queryMatInvCertType.do",printPara,function (responseData){
// 			var trHtml ='';
// 			$.each(responseData.Rows,function(idx,item){ 
// 				 	 trHtml+="<tr>";
// 					 trHtml+="<td>"+item.type_code+"</td>"; 
// 					 trHtml+="<td>"+item.type_name+"</td>";
// 					 if(item.is_alarm == 0){
// 						 trHtml+="<td>否</td>";
// 					 }else{
// 						 trHtml+="<td>是</td>";
// 					 }
// 					 trHtml+="<td>"+item.war_days+"</td>"; 
// 				 trHtml+="</tr>";
// 				$("#resultPrint > table > tbody").empty();
// 				$("#resultPrint > table > tbody").append(trHtml);
// 			});
// 			manager.close();
// 			//alert($("#resultPrint").html())
// 			lodopPrinterTable("resultPrint","开始打印","04601 材料证件类型字典",true);
// 	    },true,manager);
// 		return;
// 	 }
	
	 
// 	 //导出数据
// 	 function exportExcel(){
// 		 //有数据直接导出
// 		if($("#resultPrint > table > tbody").html()!=""){
// 			lodopExportExcel("resultPrint","导出Excel","04601 材料证件类型字典.xls",true);
// 			return;
// 		} 
		
// 		//重新查询数据，避免分页导致打印数据不全
// 		var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');

// 		var exportPara={
// 			usePager:false,
//            type_code:$("#type_code").val(),
//            type_name:$("#type_name").val(),
//          };
// 		ajaxJsonObjectByUrl("queryMatInvCertType.do",exportPara,function (responseData){
// 			var trHtml = '';
// 			$.each(responseData.Rows,function(idx,item){ 
// 				 	 trHtml+="<tr>";
// 					 trHtml+="<td>"+item.type_code+"</td>"; 
// 					 trHtml+="<td>"+item.type_name+"</td>"; 
// 					 if(item.is_alarm == 0){
// 						 trHtml+="<td>否</td>";
// 					 }else{
// 						 trHtml+="<td>是</td>";
// 					 } 
// 					 trHtml+="<td>"+item.war_days+"</td>"; 
// 				 trHtml+="</tr>";
// 				 $("#resultPrint > table > tbody").empty();
// 				 $("#resultPrint > table > tbody").append(trHtml);
// 			});
// 			manager.close();
// 			lodopExportExcel("resultPrint","导出Excel","04601 材料证件类型字典.xls",true);
// 	    },true,manager);
// 		return;
// 	 }		  
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">授权编码：</td>
            <td align="left" class="l-table-edit-td"><input name="impower_code" type="text" id="impower_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">起始日期：</td>
            <td align="left" class="l-table-edit-td"><input class="Wdate" name="impower_start_date" type="text" id="impower_start_date"  onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">截止日期：</td>
            <td align="left" class="l-table-edit-td"><input class="Wdate" name="impower_end_date" type="text" id="impower_end_date"  onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
           
            
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">生产厂商：</td>
            <td align="left" class="l-table-edit-td"><input name="fac_id" type="text" id="fac_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
             <td align="right" class="l-table-edit-td"  style="padding-left:20px;">授权单位：</td>
            <td align="left" class="l-table-edit-td"><input name="sup_id" type="text" id="sup_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">被授权单位：</td>
            <td align="left" class="l-table-edit-td"><input name="sup_id_b" type="text" id="sup_id_b" ltype="text" validate="{required:true,maxlength:20}" /></td>
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
