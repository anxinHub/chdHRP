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
<script src="<%=path%>/lib/map.js"></script>
<script src="<%=path%>/lib/ligerUI/js/checkboxRender.js"></script>
<script type="text/javascript">

	var targetMap = new HashMap();//存放弹窗窗口数据
    var grid;
    var gridManager = null;
    var userUpdateStr;
    var treeManager =null;
    var kpi_code = '';
    var isHide = true;//控制保存按钮是否显示
    var edit_id = 1;//编辑模式 默认值1 1为单条模式,2为批量模式
    var colunm_id = "";//单条模式记录值医院id
    var colunm_no = "";//单条模式记录值医院变更no
    var v_colunm = "";//单条模式后台取值字段
    
    var edit_type = {
    		Rows : [ {
    			"id" : "1",
    			"text" : "单条模式"
    		}, {
    			"id" : "2",
    			"text" : "批量模式"
    		}],
    		Total : 2
    };
    
    $(function (){
    	
    	$("#layout1").ligerLayout({ 
    		leftWidth: 200,
    		heightDiff: -28,
    		//每展开左边树即刷新一次表格，以免结构混乱
    		onLeftToggle:function(){			
        		grid._onResize()
            },
        	//每调整左边树宽度大小即刷新一次表格，以免结构混乱
        	onEndResize:function(a,b){    
				grid._onResize()
            }	
    	});
    	$("#left").css("height",$("#layout1").height() - 25);
        loadDict();//加载下拉框
    	loadHead(null);//加载grid	
    	toobar();//加载工具栏
    	loadHotkeys();//键盘事件
    	loadTree();//加载树
    	
    	//编辑模式值改变事件
    	$('#edit_mode').bind('change',function(){
    		edit_id = liger.get("edit_mode").getValue();
    		if(edit_id == 1){
    			isHide = true;
    			if($(".total").hasClass("chk-icon"))
    				$(".total").removeClass("chk-icon");
    		}else{
    			isHide = false;
    			if(!$(".total").hasClass("chk-icon"))
    				$(".total").addClass("chk-icon");
    		}
    		toobar();
    	});
    });
    
    //查询
    function  query(){
    	grid.options.parms=[];
    	grid.options.newPage=1;
        //根据表字段进行添加查询条件 
        grid.options.parms.push({name:'hos_id',value:liger.get("hos_id").getValue()}); 
    	grid.options.parms.push({name:'goal_code',value:liger.get("goal_code").getValue()}); 
    	grid.options.parms.push({name:'acc_year',value:$("#acc_year").val()});
    	//加载查询条件
    	grid.loadData(grid.where);
    	loadTree();
	}
    
	
    //加载表格
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns:[],dataAction: 'server',dataType: 'server',usePager:true,url:'queryPrmHosKpiObjHos.do',
           width: '100%', height: '100%', enabledSort:false,checkbox: false,rownumbers:true,delayLoad:true,enabledEdit : true,
           selectRowButtonOnly:true,//heightDiff: -10,
    	});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
 	
    //加载工具栏
    function toobar(){
		$("#toptoolmod").ligerToolBar({ items: [
			{ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' },
			{ line:true },
			{ text: '保存（<u>S</u>）', id:'add', click: save, icon:'add',hide:isHide},
			{ line:true,hide:isHide}
			]
		});
    }
    //键盘事件
	function loadHotkeys(){
		hotkeys('Q',query);
		hotkeys('S',saveHosKpiObj);
	}
    
    //加载下拉框字典
    function loadDict(){
    	loadComboBox({id:"#goal_code",url:"../quertPrmGoalDict.do?isCheck=false&prem_data=true",value:"id",text:"text",autocomplete:true,hightLight:true,selectBoxWidth:'auto',maxWidth:'260',defaultSelect:true,async:false})
    	autocomplete("#hos_id","../quertSysHosInfoDict.do?isCheck=false","id","text",true,true,"",true);
    	autoCompleteByData("#edit_mode", edit_type.Rows, "id", "text", true, true,"",true);//编辑模式
    	liger.get('goal_code').set({width:200});
    	
    	$("#acc_year").ligerTextBox({width : 160});
    	autodate("#acc_year","yyyy");
	}
    
    //此函数用于公用js中save方法的调用
    function save(obj){
    	saveHosKpiObj(obj);
    }
    
    //保存考核对象
    function saveHosKpiObj(obj){
    	var hos_id = liger.get("hos_id").getValue();
    	if(hos_id == null || hos_id == ''){
    		$.ligerDialog.warn("请选择单位信息");
			return false;
    	}
    	
    	var acc_year = $("#acc_year").val();
		var goal_code = liger.get("goal_code").getValue();
    	
		if(goal_code == ''){
			$.ligerDialog.warn("请选择目标节点");
			return false;
		}

		
		var hos_kpi_data;//用于存储改变的数据
		
		if(edit_id == 1){//编辑模式： 1为单条模式   2为批量模式
			hos_kpi_data = obj;
		}else{
			hos_kpi_data = gridManager.getUpdated();
		}
		
		if(hos_kpi_data.length == 0){
			$.ligerDialog.warn('未更改任何数据 ');
			return ; 
		}

		var formPara = {
			edit_id : edit_id,//编辑模式
			colunm_hos_id : colunm_id,//用于单条模式查询
			hos_colunm : v_colunm,//用于单条模式保存时获取勾选的值
			goal_code : liger.get("goal_code").getValue(),
			acc_year :acc_year,
			hos_id:hos_id,
			hos_kpi_data : JSON.stringify(hos_kpi_data)
		};
		
		ajaxJsonObjectByUrl("savePrmHosKpiObj.do", formPara, function(responseData) {
			if (responseData.state == "true") {
				query();
			}
		});
    }
  
	/* 设置树形菜单 */
	function onSelect(note){
		grid.options.parms=[];
		grid.options.newPage=1;
		//根据表字段进行添加查询条件
        var rootNote = tree.getParent(note, 1);
        var rid = rootNote == null ? "": rootNote.id;
        		
        var id = note.data.id;
        var text = note.data.text;
        var pid = note.data.pid;
        
        if(pid == -1){//根节点
			grid.options.parms.push({name:'goal_code',value:id});
			liger.get('goal_code').setValue(id);
			liger.get('goal_code').setText(id+' '+text);
		}else{
	    	if(note.data.children){//如果有子节点 查当前节点下所有指标
	        	grid.options.parms.push({name:'goal_code',value:rid});
	        	grid.options.parms.push({name:'super_kpi_code',value:id.split(",")[1]});
	        	autocomplete("#goal_code","../quertPrmGoalDict.do?isCheck=false","id","text",true,true,"",false,rid);
	        }else{
	        	grid.options.parms.push({name:'goal_code',value:rid});
	        	grid.options.parms.push({name:'kpi_code',value:id.split(",")[1]});
	        	kpi_code = id.split(",")[1];
	        	autocomplete("#goal_code","../quertPrmGoalDict.do?isCheck=false","id","text",true,true,"",false,rid);
	        }
        }
        
        grid.options.parms.push({name:'hos_id',value:liger.get("hos_id").getValue()}); 
        grid.options.parms.push({name:'acc_year',value:$('#acc_year').val()});
        	   
		//加载查询条件
       	grid.loadData(grid.where);
	}

    //加载树形菜单   
	function loadTree(){
    	
		var param = {
			acc_year :$("#acc_year").val(),
	  		goal_code:liger.get("goal_code").getValue(),
	  		hos_id:liger.get("hos_id").getValue()
	  	};
    	
		ajaxJsonObjectByUrl("queryPrmHosKpiTree.do?isCheck=false",param,function(responseData) {
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
        
	//获取考核对象(医院)动态列
	function grid_setColumns() {
		ajaxJsonObjectByUrl("queryPrmHosInfoColumns.do?isCheck=false", {},function(responseData) {
			if (responseData != null) {
				grid.set('columns', responseData);
				grid.reRender();
			}
		});
	}
</script>
</head>
</head>

<body style="padding: 0px; overflow: hidden;"   onload="grid_setColumns()">

	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar">
	   <table cellpadding="0" cellspacing="0" class="l-table-edit">
	 	<tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">单位信息：</td>
            <td align="left" class="l-table-edit-td"><input name="hos_id" type="text" id="hos_id" ltype="text" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">绩效年度：</td>
            <td align="left" class="l-table-edit-td"><input name="acc_year" type="text"  id="acc_year" ltype="text"   class="Wdate" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy',minDate:'2016-01-01'})"/></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">目标名称：</td>
            <td align="left" class="l-table-edit-td"><input name="goal_code" type="text" id="goal_code" ltype="text" /></td>
            <td align="left"></td>
        </tr>
        
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">编辑模式：</td>
            <td align="left" class="l-table-edit-td"><input name="edit_mode" type="text" id="edit_mode" ltype="text"/></td>
            <td align="left"></td>
        </tr>
    </table>
	</div>
   	<div id="toptoolmod"></div>

	<div class="l-layout" id="layout1" style="height: 100%;">
		<div position="left" id="left" style="height: 100%;overflow:auto">
			<ul id="tree"></ul>
		</div>
		
		<div position="center" style="height: 100%;">
			<div id="maingrid"></div>
		</div>
	</div>

	<div id="resultPrint" style="display:none">
	   	<table width="100%">
			<thead>
				<tr>
	                <th width="200">指标编码</th>	
	                <th width="200">指标名称</th>	
	                <th width="200">指标性质</th>	
				</tr>
			</thead>
			<tbody></tbody>
		   	</table>
   	</div>
</body>
</html>
