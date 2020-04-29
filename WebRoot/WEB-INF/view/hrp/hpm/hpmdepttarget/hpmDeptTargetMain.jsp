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
    	
    	autocomplete("#target_code","../../queryTarget.do?isCheck=false","id","text",true,true);
    	autocomplete("#dept_kind_code","../../queryDeptKindDict.do?isCheck=false","id","text",true,true);
    	$("#is_acc").ligerComboBox({width:160 });

    	loadHead(null);	//加载grid
    	toolbar();//加载工具栏
    	loadHotkeys();//加载快捷键
    	
    });
    
    
    //查询
    function  query(){
    	
    	grid.options.parms=[];
    	grid.options.newPage=1;
    	
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'target_code',value:liger.get("target_code").getValue()}); 
    	grid.options.parms.push({name:'dept_kind_code',value:liger.get("dept_kind_code").getValue()}); 
    	grid.options.parms.push({name:'is_acc',value:$("#is_acc").val()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
	}

	//加载grid
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [ 
				{ display: '科室分类', name: 'dept_kind_code', align: 'left',render: 
					function (rowdata, rowindex, value){
						return "<a href='#' onclick=\"openUpdate('"
								+rowdata.target_code+"','"
								+rowdata.dept_kind_code+"');\" >"
								+rowdata.dept_kind_name+"</a>";
			           }
				},
				
				{ display: '指标编码', name: 'target_code', align: 'left',render: 
					function (rowdata, rowindex, value){
						return "<a href='#' onclick=\"openUpdate('"
							+rowdata.target_code+"','"
							+rowdata.dept_kind_code+"');\" >"
							+rowdata.target_code+"</a>";
			           }
				},
					 
				{ display: '指标名称', name: 'target_name', align: 'left'},
                { display: '指标描述', name: 'target_name', align: 'left'},
                { display: '分配动因', name: 'is_acc', align: 'left',type:'formatYesNo'}
			],
            dataAction: 'server',dataType: 'server',usePager:true,url:'queryHpmDeptTarget.do',
            width: '100%',height: '100%',   checkbox: true,rownumbers:true,
            selectRowButtonOnly:true,//heightDiff: -10,
			onDblClickRow : 
				function (rowdata, rowindex, value){
					openUpdate(rowdata.target_code,rowdata.dept_kind_code);//实际代码中temp替换主键
				} 
		});

        gridManager = $("#maingrid").ligerGetGridManager();
        formatYesNo();
    }
    
    
  	//工具栏
	function toolbar(){
       	var obj = [];
       	
       	obj.push({ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '添加（<u>A</u>）', id:'add', click: addDeptTarget, icon:'add' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '删除（<u>D</u>）', id:'delete', click: deleteDeptTarget,icon:'delete' });
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
		 
		hotkeys('A',addDeptTarget);
		
		hotkeys('D',deleteDeptTarget);
		
		hotkeys('T',downTemplate);
		
		hotkeys('I',importData);
	}
  	
  	
  	//添加
  	function addDeptTarget(){
  		$.ligerDialog.open({
  			url: 'hpmDeptTargetAddPage.do?isCheck=false', 
  			title:'添加',height: 250,width: 400,modal:true,showToggle:false,
  			showMax:false,showMin: true,isResize:true,
  			buttons: [ 
  				{ text: '确定', onclick: 
  					function (item, dialog) { 
  						dialog.frame.saveDeptTarget(); 
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
  	function deleteDeptTarget(){
  		
  		var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.warn('请选择行');
        	return ; 
        }
        var checkIds =[];
        $(data).each(function (){
        	checkIds.push(this.target_code+";"+this.dept_kind_code);//实际代码中temp替换主键
        });
        
        $.ligerDialog.confirm('确定删除?', function (yes){
        	if(yes){
            	ajaxJsonObjectByUrl("deleteHpmDeptTarget.do",{checkIds:checkIds.toString()},function (responseData){
            		if(responseData.state=="true"){
            			query();
            		}
            	});
        	}
        }); 
  	}
  	
  	
  	//下载导入模板
  	function downTemplate(){
  		location.href = "downTemplateDeptTarget.do?isCheck=false";
  	}
  	
  	//导入
  	function importData(){
       	$.ligerDialog.open({url: 'deptTargetImportPage.do?isCheck=false', height: 430,width: 760, isResize:true, title:'导入'});
  	}
	
  	//修改
    function openUpdate(obj1,obj2){
    	//实际代码中&temp替换主键
    	$.ligerDialog.open({ 
    		url: 'hpmDeptTargetUpdatePage.do?isCheck=false&target_code='+obj1+'&dept_kind_code='+obj2,data:{}, 
    		height: 250,width: 400, title:'修改',modal:true,showToggle:false,
    		showMax:false,showMin: false,isResize:true,
    		buttons: [ 
    			{ text: '确定', onclick: 
    				function (item, dialog) { 
    					dialog.frame.saveDeptTarget(); 
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
  	
    </script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">指标编码：</td>
			<td align="left" class="l-table-edit-td"><input name="target_code" type="text" id="target_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>

			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室类别：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_kind_code" type="text" id="dept_kind_code" ltype="text"
				validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">分配动因：</td>
			<td align="left" class="l-table-edit-td"><select name="is_acc" id="is_acc" style="width: 160px;">
					<option value="0">否</option>
					<option value="1">是</option>
			</select></td>
			<td align="left"></td>
		</tr>
	</table>
	
	<div id="toptoolbar"></div>
	<div id="maingrid"></div>
</body>
</html>
