<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc.jsp"/>
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
        
        toolbar();
    	loadHotkeys();
		
        $("#dept_kind_code").ligerTextBox({width:160});
        $("#dept_kind_name").ligerTextBox({width:160});
        $("#dept_kind_note").ligerTextBox({width:160});
        $("#is_stop").ligerComboBox({width:160 });
		
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
          
    	  grid.options.parms.push({name:'dept_kind_code',value:liger.get("dept_kind_code").getValue()}); 
    	  grid.options.parms.push({name:'is_stop',value:$("#is_stop").val()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }
    //获取查询条件的数值
    
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '科室分类编码', name: 'dept_kind_code', align: 'left',
							render : function(rowdata, rowindex,
									value) {
									return "<a href=javascript:openUpdate('"+rowdata.dept_kind_code   + "|" + 
									rowdata.group_id   + "|" + 
									rowdata.hos_id   + "|" + 
									rowdata.copy_code   + "|" + 
									rowdata.acc_year  +"')>"+rowdata.dept_kind_code+"</a>";
							}
					 		},
                     { display: '科室分类名称', name: 'dept_kind_name', align: 'left'
					 		},
                     { display: '科室分类说明', name: 'dept_kind_note', align: 'left'
					 		},
                     { display: '是否停用', name: 'is_stop', align: 'left',
                    	 render : function(rowdata, rowindex,
									value) {
								if(rowdata.is_stop == 0){
									return "否";
								}else{
									return "是"
								}
							}
					 		}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryHpmDeptKind.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,//heightDiff: -10,
                     selectRowButtonOnly:true,
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.dept_kind_code + "|" + 
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code   + "|" 
								
							);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
  	//工具栏
	function toolbar(){
		var obj = [];
		
		obj.push({ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '添加（<u>A</u>）', id:'add', click: addDeptKind, icon:'add' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '删除（<u>D</u>）', id:'delete', click: deleteDeptKind,icon:'delete' });
       	obj.push({ line:true });
       	
       	$("#toptool").ligerToolBar({ items: obj});
	}

    function openUpdate(obj){

		var vo = obj.split("|");


		var parm = "&dept_kind_code="+
			vo[0]   +"&group_id"+ 
			vo[1]   +"&hos_id"+ 
			vo[2]   +"&copy_code"+ 
			vo[3] 
		
    	$.ligerDialog.open({ url : 'hpmDeptKindUpdatePage.do?isCheck=false&' + parm,data:{}, height: 300,width: 500, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveHpmDeptKind(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    }
    
    //键盘事件
	function loadHotkeys(){
		
		hotkeys('Q',query);
		 
		hotkeys('A',addDeptKind);
		
		hotkeys('D',deleteDeptKind);
		
		hotkeys('E',exportDeptKind);
		
		hotkeys('P',printDeptKind);
		
		hotkeys('L',downTemplateDeptKind);
		
		hotkeys('I',importDeptKind);
	}
    
    function addDeptKind(){
    	
    	$.ligerDialog.open({url: 'hpmDeptKindAddPage.do?isCheck=false', height: 300,width: 500, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveHpmDeptKind(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
    	
    }
    
    function deleteDeptKind(){
    	
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
														this.dept_kind_code 
														) });
              $.ligerDialog.confirm('确定删除?', function (yes){
              	if(yes){
                  	ajaxJsonObjectByUrl("deleteHpmDeptKind.do",{ParamVo : ParamVo},function (responseData){
                  		if(responseData.state=="true"){
                  			query();
                  		}
                  	});
              	}
              }); 
          }
    	
    }
    
    function exportDeptKind(){
    	
    	exportExcel();
    }
    
    function printDeptKind(){
    	
    	printDate();
    }
    
    function downTemplateDeptKind(){
    	
    	location.href = "downTemplate.do?isCheck=false";
    }
    
    
    function importDeptKind(){
    	
    	$.ligerDialog.open({url: 'hpmDeptKindImportPage.do?isCheck=false', height: 450,width: 800, title:'导入',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true });
    	
    }
    
    function loadDict(){
            //字典下拉框
    	   autocomplete("#dept_kind_code","../queryDeptKindDict.do?isCheck=false","id","text",true,true);
    	   
         }  
    
  //打印数据
	 function printDate(){
		//有数据直接打印

		if($("#resultPrint > table > tbody").html()!=""){
	 		
			lodopPrinterTable("resultPrint","开始打印","8803 科室分类字典表",true);
			
			return;
		}  

		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备打印数据,请稍候...');

		var printPara={
				
		   usePager:false,
		 
           dept_kind_code:$("#dept_kind_code").val(),
           
           is_stop:$("#is_stop").val()
            
            
         };
		
		ajaxJsonObjectByUrl("queryHpmDeptKind.do",printPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
			
			
				 var trHtml="<tr>";
					 trHtml+="<td>"+item.dept_kind_code+"</td>"; 
					 trHtml+="<td>"+item.dept_kind_name+"</td>"; 
					 trHtml+="<td>"+item.dept_kind_note+"</td>"; 
					 trHtml+="<td>"+(item.is_stop==0?'否':'是')+"</td>";
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			lodopPrinterTable("resultPrint","开始打印","8803 科室分类字典表",true);
	    },true,manager);
		return;
	 }
	
	 
	 //导出数据
	 function exportExcel(){
		//有数据直接导出
		if($("#resultPrint > table > tbody").html()!=""){
			lodopExportExcel("resultPrint","导出Excel","8803 科室分类字典表集.xls",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');

		
		var exportPara={
				
			usePager:false,
            
           dept_kind_code:$("#dept_kind_code").val(),

           is_stop:$("#is_stop").val()
            
            
         };
		ajaxJsonObjectByUrl("queryHpmDeptKind.do",exportPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
					 trHtml+="<td>"+item.dept_kind_code+"</td>"; 
					 trHtml+="<td>"+item.dept_kind_name+"</td>"; 
					 trHtml+="<td>"+item.dept_kind_note+"</td>"; 
					 trHtml+="<td>"+(item.is_stop==0?'否':'是')+"</td>"; 
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			lodopExportExcel("resultPrint","导出Excel"," 科室分类字典表.xls",true);
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
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室分类编码：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_kind_code" type="text" id="dept_kind_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否停用：</td>
            <td align="left" class="l-table-edit-td">           
               	<select id="is_stop" name="is_stop" style="width: 135px;">
               	                    <option value=""></option>
			                		<option value="0">否</option>
			                		<option value="1">是</option>
                				</select></td>
            <td align="left"></td>
        </tr> 
    </table>
	
	<div id="toptool"></div>
	<div id="maingrid"></div>
</body>
</html>
