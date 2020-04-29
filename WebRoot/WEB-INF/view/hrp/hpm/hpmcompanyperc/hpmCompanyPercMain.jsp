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
    var userUpdateStr;
    
    //页面初始化
    $(function (){
    	
    	autocomplete("#group_id","../querySysGroupDict.do?isCheck=false","id","text",true,true);
    	autocomplete("#hos_id","../../sys/queryHosInfoDict.do?isCheck=false","id","text",true,true);

    	loadHead(null);	//加载grid
    	toolbar();//加载工具栏
    	loadHotkeys();//加载快捷键
    	
    });
    
    
    //查询
    function  query(){
    	
    	grid.options.parms=[];
    	grid.options.newPage=1;
    	
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'group_id',value:liger.get("group_id").getValue()}); 
    	grid.options.parms.push({name:'hos_id',value:liger.get("hos_id").getValue()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
	}
	
    //加载数据
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [ 
				
				{ display: '集团名称', name: 'group_name', align: 'left',render: 
					function (rowdata, rowindex, value){
		 				return "<a href='#' onclick=\"openUpdate('"+rowdata.group_id+"','"+ rowdata.hos_id + "','" +rowdata.copy_code +"');\" >"+rowdata.group_name+"</a>";
					}
				},
				
				{ display: '医院名称', name: 'hos_name', align: 'left'},
                { display: '计提比例', name: 'comp_percent', align: 'left'}
				
            ],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryHpmCompanyPerc.do',
			width: '100%',height: '100%',   checkbox: true,rownumbers:true,
			selectRowButtonOnly:true,//heightDiff: -10,
			onDblClickRow : 
				function (rowdata, rowindex, value){
					openUpdate(rowdata.comp_code,rowdata.copy_code);//实际代码中temp替换主键
				} 
		});

        gridManager = $("#maingrid").ligerGetGridManager();
        formatTrueFalse();
    }
    
    
  	//工具栏
	function toolbar(){
       	var obj = [];
       	
       	obj.push({ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '添加（<u>A</u>）', id:'add', click: addCompanyPerc, icon:'add' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '删除（<u>D</u>）', id:'delete', click: deleteCompanyPerc,icon:'delete' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '下载导入模板（<u>T</u>）', id:'downTemplate', click: downTemplate,icon:'down' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '导入（<u>I</u>）', id:'import', click: importData,icon:'up' });
       	obj.push({ line:true });
       	
       	$("#toptoolbar").ligerToolBar({ items: obj});
	}
  	
	//键盘事件
	function loadHotkeys(){
		
		hotkeys('Q',query);
		
		hotkeys('A',addCompanyPerc);
		
		hotkeys('D',deleteCompanyPerc);
		
		hotkeys('T',downTemplate);
		
		hotkeys('I',importData);
	}
    
    //添加
    function addCompanyPerc(){
  		$.ligerDialog.open({
  			url: 'hpmCompanyPercAddPage.do?isCheck=false', 
  			title:'添加',height: 250,width: 400,modal:true,showToggle:false,
  			showMax:false,showMin: true,isResize:true,
  			buttons: [ 
  				{ text: '确定', onclick: 
  					function (item, dialog) { 
  						dialog.frame.saveCompanyPerc(); 
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
    
    //生成
    function createCompanyPerc(){
	   	$.ligerDialog.confirm('确定生成?', function(yes) {
			if (yes) {
				ajaxJsonObjectByUrl("createCompanyPerc.do", {}, function(responseData) {
					if (responseData.state == "true") {
						query();
					}
				});
			}
		});
    }
    
    
    //删除
    function deleteCompanyPerc(){
    	
    	var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.warn('请选择行');
        	return ; 
        }
        var checkIds =[];
        $(data).each(function (){
        	checkIds.push(this.group_id+";"+this.hos_id +";"+this.copy_code);//实际代码中temp替换主键
        });
        
        $.ligerDialog.confirm('确定删除?', function (yes){
        	if(yes){
            	ajaxJsonObjectByUrl("deleteHpmCompanyPerc.do",{checkIds:checkIds.toString()},function (responseData){
            		if(responseData.state=="true"){
            			query();
            		}
            	});
        	}
        }); 
    }
    
    
    //下载导入模板
    function downTemplate(){
    	location.href = "downTemplateCompanyPerc.do?isCheck=false";
    }
    
    
    //导入
    function importData(){
    	$.ligerDialog.open({
    		url: 'companyPercImportPage.do?isCheck=false', 
    		title:'导入',height: 430,width: 760, isResize:true
    	});

    }
	
    //修改
    function openUpdate(obj1,obj2,obj3){
    	//实际代码中&temp替换主键
    	$.ligerDialog.open({ 
    		url: 'hpmCompanyPercUpdatePage.do?isCheck=false&group_id='+obj1+'&hos_id='+obj2 +'&copy_code='+obj3 ,data:{}, 
    		title:'修改',height: 250,width: 400,modal:true,showToggle:false,
    		showMax:false,showMin: false,isResize:true,
    		buttons: [ 
    			{ text: '确定', onclick: 
    				function (item, dialog) { 
    					dialog.frame.saveCompanyPerc(); 
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
    
    
    function loadDict(){
	}   
    
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">集团名称：</td>
			<td align="left" class="l-table-edit-td"><input name="group_id" type="text" id="group_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">医院名称：</td>
			<td align="left" class="l-table-edit-td"><input name="hos_id" type="text" id="hos_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
		</tr>
	</table>
	
	<div id="toptoolbar"></div>
	<div id="maingrid"></div>
</body>
</html>
