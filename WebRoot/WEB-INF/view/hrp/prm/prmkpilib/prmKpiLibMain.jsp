<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<jsp:include page="${path}/inc.jsp"/>
<script type="text/javascript">


    var grid;
    var gridManager = null;
    var userUpdateStr;
    
    
    //页面初始化
    $(function (){
    	
		loadDict();//加载下拉框
    	loadHead(null);	//加载数据
    	
    	loadHotkeys();//快捷键
    	loadTree();//加载树菜单
    	
    	$("#layout1").ligerLayout({ leftWidth: 200,allowLeftResize:true }); 
    });
    
    
    
    //查询
    function  query(){
		loadTree();
		grid.options.parms=[];
		grid.options.newPage=1;
		
		//根据表字段进行添加查询条件 
		grid.options.parms.push({name:'hos_id',value:liger.get("hos_id").getValue()}); 
		grid.options.parms.push({name:'dim_code',value:liger.get("dim_code").getValue()}); 
		
    	//加载查询条件
    	grid.loadData(grid.where);
	}
    
    
    //加载grid
    function loadHead(){
		grid = $("#maingrid").ligerGrid({
		columns: [
			{ display: '指标编码', name: 'kpi_code', align: 'left',width:180,render : 
				function(rowdata, rowindex,value) {
					return "<a href=javascript:openUpdate('"+
						rowdata.kpi_code   + "|" + 
						rowdata.group_id   + "|" + 
						rowdata.hos_id   + "|" + 
						rowdata.copy_code  +"')>"
						+rowdata.kpi_code
					+"</a>";
				}
			},
			
	        { display: '指标名称', name: 'kpi_name', align: 'left',width:180},
	        { display: '指标性质', name: 'nature_code', align: 'left',width:180,render : 
	        	function(rowdata, rowindex,value) {
					if(rowdata.nature_code == 01){
						return "正向";
					}else{
						return "反向"
					}
				}
			},
			
        	{ display: '指标描述', name: 'kpi_note', align: 'left',width:580}
        ],
        dataAction: 'server',dataType: 'server',usePager:true,url:'queryPrmKpiLibDimPkn.do',width: '100%', 
        height: '100%',checkbox: true,rownumbers:true,delayLoad:true,selectRowButtonOnly:true,
		onDblClickRow : 
			function (rowdata, rowindex, value){
				openUpdate(
					rowdata.kpi_code +"|"+
					rowdata.group_id   + "|" + 
					rowdata.hos_id   + "|" + 
					rowdata.copy_code   
				);
    		} 
       });
        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    
    //打开修改页
    function openUpdate(obj){
		var vo = obj.split("|");
		var parm = 
			"&kpi_code=" + vo[0] +
			"&group_id=" + vo[1] +
			"&hos_id=" + vo[2] +
			"&copy_code="+ vo[3] ;

    	$.ligerDialog.open({ 
    		url : 'prmKpiLibUpdatePage.do?isCheck=false&' + parm,
    		data:{},height: 400,width: 700,title:'修改',
    		modal:true,showToggle:false,showMax:false,showMin: false,
    		isResize:true,buttons: [
	    		{ text: '确定', onclick: 
	    			function (item, dialog) { 
	    				dialog.frame.savePrmKpiLib(); 
	    			},cls:'l-dialog-btn-highlight' }, 
	    			
	    		{ text: '取消', onclick: 
	    			function (item, dialog) { 
	    				dialog.close(); 
	    			} 
	    		} 
    		] 
    	});

    }
    //键盘事件
	function loadHotkeys(){
		hotkeys('Q',query);
		hotkeys('A',addKpiLib);
		hotkeys('D',deleteKpiLib);
		hotkeys('P',print);
		hotkeys('T',downTemplateKpiLib);
		hotkeys('I',importKpiLib);
	}
    
    
	//字典下拉框
    function loadDict(){
    	autocomplete("#dim_code","../queryPrmKpiDim.do?isCheck=false","id","text",true,true);
    	autocomplete("#hos_id","../quertSysHosInfoDict.do?isCheck=false","id","text",true,true,"",true);
    }
    
    function addKpiLib(){
    	$.ligerDialog.open({
    		url: 'prmKpiLibAddPage.do?isCheck=false',height: 400,width: 750, title:'添加',modal:true,
    		showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ 
    			{ text: '确定', onclick: 
    				function (item, dialog) { 
    					dialog.frame.savePrmKpiLib(); 
    				},
    				cls:'l-dialog-btn-highlight' 
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
	function deleteKpiLib(){
		
	  	var data = gridManager.getCheckedRows();
      	if (data.length == 0){
      		$.ligerDialog.warn('请选择行');
      		return ;
     	}
      	
		var ParamVo =[];
		$(data).each(function (){					
			ParamVo.push(
				this.group_id   +"@"+ 
				this.hos_id   +"@"+ 
				this.copy_code   +"@"+ 
				this.kpi_code 
			) 
		});
          	
		$.ligerDialog.confirm('确定删除?', function (yes){
			if(yes){
              	ajaxJsonObjectByUrl("deletePrmKpiLib.do",{ParamVo : ParamVo},function (responseData){
              		if(responseData.state=="true"){
              			query();
              			loadTree()
              		}
              	});
          	}
		}); 
	}
	
    
	//打印
	function print(){
  		
		if (grid.getData().length == 0) {
			$.ligerDialog.warn("请先查询数据！");
			return;
		}

		/* var heads={
		  //"isAuto": true/false 默认true，页眉右上角默认显示页码
		  "rows": [
	      {"cell":0,"value":"科室名称："+liger.get("dept_id").getText(),"colSpan":"5"},
	      {"cell":3,"value":"项目名称："+liger.get("proj_id").getText(),"from":"right","align":"right","colSpan":"4"}
			  ]
		}; */
	
		var printPara={
				title: "指标库维护",//标题
				columns: JSON.stringify(grid.getPrintColumns()),//表头
				class_name: "com.chd.hrp.prm.service.PrmKpiLibService",
				method_name: "queryPrmKpiLibPrint",
				bean_name: "prmKpiLibService"/* ,
				heads: JSON.stringify(heads) *///表头需要打印的查询条件,可以为空
				/* foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 */
				};
		
		$.each(grid.options.parms,function(i,obj){
				printPara[obj.name]=obj.value;
		});
		
		officeGridPrint(printPara);
  		
    }
	
	
	//下载导入模板
  	function downTemplateKpiLib(){
		location.href = "downTemplate.do?isCheck=false";
  	}
  	
	
	//导入
  	function importKpiLib(){
	  	$.ligerDialog.open({url: 'prmKpiLibImportPage.do?isCheck=false', height: 500,width: 800, title:'导入',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true });
  	}
  	
</script>

	<script type="text/javascript">
		$(function (){
			$("#toptoolmod").ligerToolBar({ 
				items: [
					{ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' },
		            { line:true },
					{ text: '添加（<u>A</u>）', id:'add', click: addKpiLib, icon:'add' },
	                { line:true },
	                { text: '删除（<u>D</u>）', id:'delete', click: deleteKpiLib,icon:'delete' },
					{ line:true }, 
	                { text: '打印（<u>P</u>）', id:'print', click: print,icon:'print' },
	                { line:true },
	                { text: '下载导入模板（<u>T</u>）', id:'downTemplate', click:downTemplateKpiLib,icon:'down' },
	                { line:true },
	                { text: '导入（<u>I</u>）', id:'import', click: importKpiLib,icon:'up' }
           		]
           	});
       	});
      	
		
		//节点选中事件
		function onSelect(note){
			grid.options.parms=[];
	        grid.options.newPage=1;
	        
	        //根据表字段进行添加查询条件             
	        //grid.options.parms.push({name:'dim_code',value:note.data.id}); 
	        if(note.data.pid== -1){
	        	grid.options.parms.push({name:'dim_code',value:note.data.id});
	        }
	        
	        if(note.data.pid != -1 && note.data.pid != -2){
	        	grid.options.parms.push({name:'kpi_code',value:note.data.id});
	        }
	       	//加载查询条件
	       	grid.loadData(grid.where);
		}
		
		
		//加载树
		function loadTree(){
			var formPara={
				hos_id:liger.get("hos_id").getValue(),
        	};
        	        
			ajaxJsonObjectByUrl("../prmkpilib/queryPrmKpiLibByMenu.do?isCheck=false", formPara,function(responseData) {
				if (responseData != null) {
					tree = $("#tree").ligerTree({
						data : responseData.Rows,
						checkbox : false,
						idFieldName : 'id',
						parentIDFieldName : 'pid',
						onSelect: onSelect,
						isExpand: 3,
						nodeWidth:400
					});
					
					treeManager = $("#tree").ligerGetTreeManager();
					treeManager.collapseAll();
				}
			});
		}
   </script>
</head>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar">
	   <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	        <tr>
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">单位信息：</td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="hos_id" type="text" id="hos_id" ltype="text"/>
	            </td>
	            <td align="left"></td>
	            
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">维度名称：</td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="dim_code" type="text" id="dim_code" ltype="text" />
	            </td>
	            <td align="left"></td>
	        </tr> 
    	</table>
	</div>
	
   	<div id="toptoolmod"></div>
   <div class="l-layout" id="layout1" style="height: 100%;" >
          <div  position="left" style="left: 0px; top: 0px;  height: 99%;">
            	<div class="l-layout-content" position="left" style="height:100%;overflow: auto;">
					<div class="ztree" style="float: left">
						<ul id="tree"></ul>
					</div>
				</div>
            </div>
            
          <div position="center" style="left:width: 975px; height: 100%;">
	           <div title="" class="l-layout-content" style="height: 100%;" position="center">
	            	<div id="maingrid"></div>
	           </div>
         </div>
	</div>  
</body>
</html>
