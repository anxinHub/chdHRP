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
    
	$(function (){
        loadDict()//加载下拉框
    	loadHead(null);	//加载数据
    	
    	toolbar();//加载工具栏
    	loadHotkeys();//加载快捷键
    	
        $("#target_code").ligerTextBox({width:160});
        $("#is_stop").ligerComboBox({width:160});
        $("#is_method").ligerComboBox({width:160});
		
    });
	
	
    //查询
	function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
        //根据表字段进行添加查询条件
		grid.options.parms.push({name:'target_code',value:liger.get("target_code").getValue()}); 
		grid.options.parms.push({name:'is_stop',value:$("#is_stop").val()});
		grid.options.parms.push({name:'is_method',value:$("#is_method").val()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
	}
   	
    //加载grid
	function loadHead(){
		grid = $("#maingrid").ligerGrid({
			columns: [ 
				{ display: '指标编码', name: 'target_code', align: 'left',render : 
					function(rowdata, rowindex,value) {
						return "<a href=javascript:openUpdate('" +
							rowdata.target_code   + "|" + 
							rowdata.group_id   + "|" + 
							rowdata.hos_id   + "|" + 
							rowdata.copy_code   
						+ "')>"+rowdata.target_code+"</a>";
					},width: '10%'
				},
				
				{ display: '指标名称', name: 'target_name', align: 'left',width: '35%'},
				
                { display: '指标性质', name: 'target_nature', align: 'center',width: '10%'},
                
                { display: '指标描述', name: 'target_note', align: 'left',width: '30%'},
                
                { display: '是否停用', name: 'is_stop', align: 'center',render : 
                	function(rowdata, rowindex,value) {
						if(rowdata.is_stop == 0){
							return "否";
						}else{
							return "是"
						}
					},width: '10%'
				}
			],dataAction: 'server',dataType: 'server',usePager:true,url:'queryHpmTargetNature.do?isCheck=false',
			width: '100%', height: '100%', checkbox: true,rownumbers:true,
            selectRowButtonOnly:true,//heightDiff: -10,
			onDblClickRow : function (rowdata, rowindex, value){	
				openUpdate(
					rowdata.target_code + "|" + 
					rowdata.group_id   + "|" + 
					rowdata.hos_id   + "|" + 
					rowdata.copy_code 
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
       	
       	obj.push({ text: '添加（<u>A</u>）', id:'add', click: addTarget, icon:'add' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '删除（<u>D</u>）', id:'delete', click: deleteTarget,icon:'delete' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '下载导入模板（<u>T</u>）', id:'downTemplate', click: downTemplateTarget,icon:'down' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '导入（<u>I</u>）', id:'import', click: importTarget,icon:'up' });
       	obj.push({ line:true });
       	
       	$("#toptool").ligerToolBar({ items: obj});
	}
	
	
    //修改页面跳转
    function openUpdate(obj){
    
		var vo = obj.split("|");
		var parm = "&target_code="+
			vo[0]   +"&group_id"+ 
			vo[1]   +"&hos_id"+ 
			vo[2]   +"&copy_code"+ 
			vo[3] 
		
    	$.ligerDialog.open({ url : 'hpmTargetUpdatePage.do?isCheck=false&' + parm,data:{}, height: 300,width: 750, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveHpmTarget(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    }
    
  	//键盘事件
	function loadHotkeys(){
		
		hotkeys('Q',query);
		 
		hotkeys('A',addTarget);
		
		hotkeys('D',deleteTarget);
		
		hotkeys('T',downTemplateTarget);
		
		hotkeys('I',importTarget);
	}
  	
  	//添加页跳转
	function addTarget(){
        	  
		$.ligerDialog.open({url: 'hpmTargetAddPage.do?isCheck=false', height: 300,width: 750, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveHpmTarget(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
	}
    
  	//删除
	function deleteTarget(){
        	  
		var data = gridManager.getCheckedRows();
		if (data.length == 0){
        	$.ligerDialog.warn('请选择行');
        	return ; 
		}
		
		$.ligerDialog.confirm('确定删除?', function (yes){
			if(yes){
				var ParamVo =[];
				$(data).each(function (){					
					ParamVo.push(
						this.group_id   +"@"+ 
						this.hos_id   +"@"+ 
						this.copy_code   +"@"+ 
						this.target_code 
					) 
				});
				
				ajaxJsonObjectByUrl("deleteHpmTarget.do",{ParamVo : ParamVo},function (responseData){
					if(responseData.state=="true"){
						query();
					}
				});
            }
		}); 
	}
    
  	//下载导入模板
	function downTemplateTarget(){
		location.href = "downTemplateHpmTarget.do?isCheck=false";
	}
 	
  	
  	//导入
	function importTarget(){
		parent.$.ligerDialog.open({ 
			url:'hrp/hpm/hpmtarget/hpmTargetImportPage.do?isCheck=false',
			data:{columns : grid.columns, grid : grid}, 
    		height: 300,
    		width: 450,
    		title:'绩效指标导入',
    		modal:true,
    		showToggle:false,
    		showMax:true,
    		showMin: false,
    		isResize:true,
    		parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
    	});
	}
    
    //字典下拉框
    function loadDict(){
    	autocomplete("#target_code","../queryTarget.do?isCheck=false","id","text",true,true);
    }
    
  	//打印
	function print(){
    	if(grid.getData().length==0){
			$.ligerDialog.warn("请先查询数据 ");
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
       			title:'绩效指标维护',
       			head:[
    				{"cell":0,"value":"单位: ${sessionScope.hos_name}","colspan":colspan_num,"br":true},
       			],
       			foot:[
    				{"cell":0,"value":"打印日期: " + cur_date,"colspan":colspan_num,"br":true}
       			],
       			columns:grid.getColumns(1),
       			headCount:1,//列头行数
       			autoFile:true,
       			type:3
       	};
   		ajaxJsonObjectByUrl("queryHpmTargetNature.do?isCheck=false", selPara, function (responseData) {
   			printGridView(responseData,printPara);
		});
   		
    }
    
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
        <tr>
        <td style="display:none">
			<input name="target_nature" type="hidden" id="target_nature" ltype="text" validate="{required:true,maxlength:20}" /> 
			<input name="nature_code" type="hidden" id="nature_code" ltype="text" validate="{required:true,maxlength:20}" /> 
			<input name="target_name" type="hidden" id="target_name" ltype="text" validate="{required:true,maxlength:20}" /> 
			<input name="target_note" type="hidden" id="target_note" ltype="text" validate="{required:true,maxlength:20}" /> 
		  </td>
        </tr> 
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">指标名称：</td>
            <td align="left" class="l-table-edit-td"><input name="target_code" type="text" id="target_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否停用：</td>
            <td align="left" class="l-table-edit-td">
             	<select id="is_stop" name="is_stop" style="width: 135px;">
               		<option value="">请选择</option>
			    	<option value="0">否</option>
			    	<option value="1">是</option>
                </select>
            </td>
            <td align="left"></td>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否取值方法维护：</td>
            <td align="left" class="l-table-edit-td">
             	<select id="is_method" name="is_method" style="width: 135px;">
               		<option value="">请选择</option>
			    	<option value="0">否</option>
			    	<option value="1">是</option>
                </select>
            </td>
            <td align="left"></td>
        </tr> 
    </table>
	
	<div id="toptool"></div>
	<div id="maingrid"></div>
</body>
</html>
