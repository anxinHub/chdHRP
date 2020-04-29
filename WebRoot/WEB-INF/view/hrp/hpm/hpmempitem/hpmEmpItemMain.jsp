<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"  xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">

    var grid;
    var gridManager = null;
    
    $(function (){
    	
    	toolbar();//加载工具栏
    	loadHotkeys();//加载快捷键
     	
		$("#item_code").ligerTextBox({ width:160 });$("#item_name").ligerTextBox({ width:160 });$("#is_stop").ligerTextBox({ width:160 });
     	loadHead(null);	//加载grid
     });
        
    //工具栏
	function toolbar(){
		var obj = [];
		
		obj.push({ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '添加（<u>A</u>）', id:'add', click: addEmpItem, icon:'add' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '删除（<u>D</u>）', id:'delete', click: deleteEmpItem,icon:'delete' });
       	obj.push({ line:true });
       	
       	$("#toptool").ligerToolBar({ items: obj});
	}
      
        
    function  query(){
    	
   		grid.options.parms=[];grid.options.newPage=1;
   		
    	grid.options.parms.push({name:'item_code',value:$("#item_code").val()});
    	
    	grid.options.parms.push({name:'item_name',value:$("#item_name").val()});
    	
    	grid.options.parms.push({name:'is_stop',value:$("#is_stop").val()});
    	
    	grid.loadData(grid.where);
    }
    
  	//键盘事件
	function loadHotkeys(){
		
		hotkeys('Q',query);
		hotkeys('A',addEmpItem);
		hotkeys('D',deleteEmpItem);
		
	}
        
        
	function loadHead(){
		grid = $("#maingrid").ligerGrid({
			columns: [ 
				{ display: '项目编码', name: 'item_code',  align: 'left',render: 
					function (rowdata, rowindex, value){
     					return "<a href='#' onclick=\"openUpdate('"+rowdata.item_code+"');\" >"+rowdata.item_code+"</a>";
     			    }
				},
                 
				{ display: '项目名称', name: 'item_name',  align: 'left' },
                
				{ display: '是否停用', name: 'is_stop',align: 'left',render: 
					function (rowdata, rowindex, value){
               			if(rowdata.is_stop == '1'){
                 			return "是";
                 		}else{
                 			return "否";
                 		}
						return "";
					} 
				},
				
				{ display: '项目说明', name: 'item_note', align: 'left' }
			],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryHpmEmpItem.do',
			width: '100%',height: '100%',   checkbox: true,rownumbers:true,
			selectRowButtonOnly:true
		});
		
		gridManager = $("#maingrid").ligerGetGridManager();
		formatYesNo();
	}
    
	//添加
    function addEmpItem(){
    	$.ligerDialog.open({
    		url: 'addEmpItemPage.do?isCheck=false', 
    		height: 350,width: 500, title:'添加奖金项目',
    		modal:true,showToggle:false,showMax:false,
    		showMin: true,isResize:true,
    		buttons: [ 
    			{ text: '确定', onclick: 
     			function (item, dialog) { 
     				dialog.frame.saveItem(); 
     			},cls:'l-dialog-btn-highlight' 
    			}, 
    			{ text: '取消', onclick: 
    				function (item, dialog) { 
    					dialog.close(); 
    				} 
    			} 
    		] 
    	});
    }
    
	//删除
   	function deleteEmpItem(){
		var data = gridManager.getCheckedRows();
	       if (data.length == 0){
	           $.ligerDialog.error('请选择行');
	       	return ; 
	       }
	       
	       var checkIds =[];
	       $(data).each(function (){
	       	checkIds.push(this.item_code);
	       });
	       $.ligerDialog.confirm('确定删除?', function (yes){
	       	if(yes){
	           	ajaxJsonObjectByUrl("deleteHpmEmpItem.do",{checkIds:checkIds.toString()},function (responseData){
	           		if(responseData.state=="true")query();
	           	});
	       	}
	       }); 
    }
    
	//修改页面跳转
	function openUpdate(obj){
		$.ligerDialog.open({ url: 'updateHpmEmpItemPage.do?isCheck=false&item_code='+obj,data:{}, height: 350,width: 500, title:'修改奖金项目',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveItem(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
	
	}
       
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div class="l-panel-search">
		<div class="l-panel-search-item">项目编码：</div>
		<div class="l-panel-search-item">
			<input type="text" id="item_code" />
		</div>
		<div class="l-panel-search-item">项目名称：</div>
		<div class="l-panel-search-item">
			<input type="text" id="item_name" />
		</div>
		<div class="l-panel-search-item">是否停用：</div>
		<div class="l-panel-search-item">
			<select name="is_stop" id="is_stop">
				<option value="">请选择</option>
				<option value="0">否</option>
				<option value="1">是</option>
			</select>
		</div>
	</div>
	<div id="toptool"></div>
	<div id="maingrid"></div>
</body>
</html>
