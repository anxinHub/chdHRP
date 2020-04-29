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
    $(function ()
    {
    	$("#is_acc").ligerTextBox({width:160 });
    	
    	autocomplete("#dept_id","../queryDeptDictByPerm.do?isCheck=false","id","text",true,true);
    	autocomplete("#work_item_code","../queryWorkItem.do?isCheck=false","id","text",true,true);
    	
    	loadHead(null);	//加载数据
    	toolbar();//加载工具栏
    	loadHotkeys();//加载快捷键
    	
    });
    
    
    //查询
    function  query(){
    	
    	grid.options.parms=[];
    	grid.options.newPage=1;
    	
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'dept_id',value:liger.get("dept_id").getValue().split(",")[0]}); 
    	grid.options.parms.push({name:'dept_no',value:liger.get("dept_id").getValue().split(",")[1]}); 
    	grid.options.parms.push({name:'work_item_code',value:liger.get("work_item_code").getValue()}); 
    	grid.options.parms.push({name:'is_acc',value:$("#is_acc").val()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
	}
	
    //加载数据
	function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [ 
				{ display: '科室编码', name: 'dept_code', align: 'left',render: 
					function (rowdata, rowindex, value){
						return "<a href='#' onclick=\"openUpdate('"
								+ rowdata.dept_id+"','"
								+ rowdata.dept_no + "','" 
								+ rowdata.work_item_code+"');\" >"
								+ rowdata.dept_code+"</a>";
					}
				},
				
				{ display: '科室名称', name: 'dept_name', align: 'left'},
				
				{ display: '工作量指标编码', name: 'work_item_code', align: 'left'},
				
				{display: '工作量指标名称', name: 'work_item_name', align: 'left'},
				
				{ display: '是否参与核算', name: 'is_acc', align: 'left',type:'formatYesNo',render : 
					function(rowdata, rowindex, value) {
						if (rowdata.is_acc == 1) {
							return "是";
						} else {
							return "否";
						}
						return "";
					}
				},
                
				{ display: '计件标准', name: 'work_standard', align: 'left'}
			],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryHpmWorkitemConf.do',
			width: '100%',height: '100%',   checkbox: true,rownumbers:true,
			selectRowButtonOnly:true,//heightDiff: -10,
			onDblClickRow : 
				function (rowdata, rowindex, value){
					openUpdate(rowdata.dept_id,rowdata.dept_no,rowdata.work_item_code);//实际代码中temp替换主键
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
       	
       	obj.push({ text: '生成（<u>G</u>）', id:'create', click: createWorkItemConf,icon:'bookpen' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '复制（<u>C</u>）', id:'copy', click: copyWorkItemConf,icon:'copy' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '添加（<u>A</u>）', id:'add', click: addWorkItemConf, icon:'add' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '删除（<u>D</u>）', id:'delete', click: deleteWorkItemConf,icon:'delete' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '下载导入模板（<u>T</u>）', id:'downTemplate', click: downTemplate,icon:'down' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '导入（<u>I</u>）', id:'import', click: importWorkItemConf,icon:'up' });
       	obj.push({ line:true });
       	
       	$("#toptoolbar").ligerToolBar({ items: obj});
	}
  	
	//键盘事件
	function loadHotkeys(){
		
		hotkeys('Q',query);
		
		hotkeys('G',createWorkItemConf);
		
		hotkeys('C',copyWorkItemConf);
		
		hotkeys('A',addWorkItemConf);
		
		hotkeys('D',deleteWorkItemConf);
		
		hotkeys('T',downTemplate);
		
		hotkeys('I',importWorkItemConf);
	}
    
    //添加
    function addWorkItemConf(){
  		$.ligerDialog.open({
  			url: 'hpmWorkitemConfAddPage.do?isCheck=false', 
  			title:'添加',height: 300,width: 400,modal:true,showToggle:false,
  			showMax:false,showMin: true,isResize:true,
  			buttons: [ 
  				{ text: '确定', onclick: 
  					function (item, dialog) { 
  						dialog.frame.saveWorkitemConf(); 
  					},cls:'l-dialog-btn-highlight' 
  				}, { text: '取消', onclick: 
  					function (item, dialog) { 
  						dialog.close(); 
  					} 
  				} 
  			] 
  		});
    }
    
    //复制
    function copyWorkItemConf(){
    	$.ligerDialog.open({
			url : 'hpmWorkitemConfCopyPage.do.do?isCheck=false',
			title : '添加',height : 300,width : 500,modal : true,showToggle : false,
			showMax : false,showMin : true,isResize : true,
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.saveIncomeitemConf();
				},
				cls : 'l-dialog-btn-highlight'
			}, {
				text : '取消',
				onclick : function(item, dialog) {
					dialog.close();
				}
			} ]
		});
    }
    
    //生成
    function createWorkItemConf(){
    	$.ligerDialog.confirm('确定生成?生成将会清空原有配置', function(yes) {
			var para={
				dept_id:liger.get("dept_id").getValue(),
				work_item_code:liger.get("work_item_code").getValue()
	    	};
			if (yes) {
				ajaxJsonObjectByUrl("createHpmWorkitemConf.do", para, function(responseData) {
					if (responseData.state == "true") {
						query();
					}
				});
			}
		});
    }
    
    //删除
    function deleteWorkItemConf(){
    	 var data = gridManager.getCheckedRows();
         if (data.length == 0){
         	$.ligerDialog.warn('请选择行');
         	return ; 
         }
         
         var checkIds =[];
         $(data).each(function (){
         	checkIds.push(this.dept_id+";" + this.dept_no+";" + this.work_item_code);//实际代码中temp替换主键
         });
         
         $.ligerDialog.confirm('确定删除?', function (yes){
         	if(yes){
             	ajaxJsonObjectByUrl("deleteHpmWorkitemConf.do",{checkIds:checkIds.toString()},function (responseData){
             		if(responseData.state=="true"){
             			query();
             		}
             	});
         	}
         }); 
    }
    
    //下载导入模板
    function downTemplate(){
    	location.href = "downTemplateHpmWorkitemConf.do?isCheck=false";
    }
    
    //导入
    function importWorkItemConf(){
    	parent.$.ligerDialog.open({ url:'hrp/hpm/hpmworkitemconf/hpmWorkitemConfImportPage.do?isCheck=false',
   			data:{columns : grid.columns, grid : grid},
   			title:'工作量指标配置导入',height: 300,width: 450,modal:true,
   			showToggle:false,showMax:true,showMin: false,
			isResize:true,parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
    }
	
    //修改页
    function openUpdate(obj1,obj2,obj3){
    	//实际代码中&temp替换主键
    	$.ligerDialog.open({ 
    		url: 'hpmWorkitemConfUpdatePage.do?isCheck=false&dept_id='+obj1 +'&dept_no='+obj2 +'&work_item_code='+obj3,data:{}, 
    		title:'修改',height: 250,width: 400, modal:true,showToggle:false,
    		showMax:false,showMin: false,isResize:true,
    		buttons: [ 
    			{ text: '确定', onclick: 
    				function (item, dialog) { 
    					dialog.frame.saveWorkitemConf(); 
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
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室编码：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">工作量指标编码：</td>
			<td align="left" class="l-table-edit-td"><input name="work_item_code" type="text" id="work_item_code" ltype="text"
				validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">是否参与核算：</td>
			<td align="left" class="l-table-edit-td"><select name="is_acc" id="is_acc" style="width: 160px;">
					<option value="">请选择</option>
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
