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
    	loadHead(null);	//加载grid
    	toobar();//加载工具栏
    	loadHotkeys();//键盘事件
    	loadTree();//加载树形菜单
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
    
	//加载grid
	function loadHead(){
		grid = $("#maingrid").ligerGrid({
			columns: [
				{display: '指标编码', name: 'kpi_code', align: 'left',width:'30%',render : 
					function(rowdata, rowindex,value) {
						if(rowdata.goal_code == null){
							return rowdata.kpi_code;
						}
						
						return "<a href=javascript:openUpdate('"+
								rowdata.kpi_code   + "|" + 
								rowdata.goal_code   + "|" + 
								rowdata.acc_year   + "|" + 
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code  +"')>"
								+rowdata.kpi_code
							+"</a>";
		  			}
				},
				
				{ display: '指标名称', name: 'kpi_name', align: 'left',width:'50%',render:
					function(rowdata,rowindex,value){
						var nbspNum = rowdata.kpi_level * 1;
	        		 	var nbspStr = "";
	        		 	for(var i = 1 ; i < nbspNum; i ++){
	        		 		nbspStr += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
	        		 	}
	        		 	var str = nbspStr+rowdata.kpi_name;
	        		 	if(str == null || str == 'undefined'){
	        		 		str = "";
	        		 	}
							return str;
					}
				},
				
				{ display: '指标性质', name: 'nature_code', align: 'left',width:'20%',render : 
					function(rowdata, rowindex,value) {
						if(rowdata.nature_code == 01){
							return "正向";
						}else if(rowdata.nature_code == 02){
							return "反向";
						}else{
							return "";
						}
					}
				}
           	],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryPrmHosKpis.do',width: '100%', height: '100%', 
			checkbox: true,rownumbers:true,delayLoad:true,enabledEdit : true,selectRowButtonOnly:true,//heightDiff: -10,
			checkBoxDisplay:isCheckDisplay,
				tree: {
					columnId: 'kpi_code',
					parentIDField: 'super_kpi_code', 
					idField: 'kpi_code'
				},
			onDblClickRow : 
				function (rowdata, rowindex, value){
					if(rowdata.goal_code == null){
						$.ligerDialog.warn('请选择指标 ');
						return ; 
					}
					
					openUpdate(
				    	rowdata.kpi_code   + "|" + 
					    rowdata.goal_code   + "|" + 
						rowdata.acc_year   + "|" + 
						rowdata.group_id   + "|" + 
						rowdata.hos_id   + "|" + 
						rowdata.copy_code 
					);
				}
    		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
 	
	//加载工具栏
    function toobar(){
		$("#toptoolmod").ligerToolBar({ items: [
			{ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' },
			{ line:true },
			{ text: '添加（<u>A</u>）', id:'add', click: checkClick, icon:'add' },
			{ line:true },
			{ text: '选择（<u>C</u>）', id:'save', click: saveHosKpi, icon:'outbox' },
			{ line:true },
			{ text: '删除（<u>D</u>）', id:'delete', click: deleteHosKpi,icon:'delete' },
			{ line:true }
			]
		});
    }
    
    function isCheckDisplay(rowdata){
        //admin用户没有复选框
        // console.log(rowdata.checkboxDisplay ==false)
       	if(rowdata.goal_code == null) return false;
         return true;
    }
    //键盘事件
	function loadHotkeys(){
		hotkeys('Q',query);
		hotkeys('A',checkClick);
		hotkeys('C',saveHosKpi);
		hotkeys('D',deleteHosKpi);
	}
    
    //选择页跳转
    function saveHosKpi(){
    	
    	var note = treeManager.getSelected(); 
    	
		if(note == null || note.data.pid != -1){
			$.ligerDialog.warn("请选择目标节点");
			return ;
		}
		
		 var targetData = targetMap.get(note.data.id);
			$.ligerDialog.open({url: '../prmKpiLibPage.do?isCheck=false', 
				height:$(window).height(),width: $(window).width(),title:'添加',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
				data: {
	                __id: note.data.id,
	                targetData:targetData
	            },buttons: [ 
				           { text: '确定', onclick: function (item, dialog) { dialog.frame.confirmPrmKpiLib(); dialog.close(); },cls:'l-dialog-btn-highlight' }, 
				           { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
		
    }
    
    //保存
    function addRows(target_data,__id){
    	if(target_data.length == 0){
    		return;
    	}
		
    	$.ligerDialog.confirm('确定保存?',function(yes){
    		if(yes){
    			var kpiList = [];
    	    	var acc_year = $('#acc_year').val();
    	    	var goal_code = liger.get('goal_code').getValue();
    	    	var hos_id = liger.get('hos_id').getValue();
    	    	$.each(target_data, function(t_index, t_content){
    	    		var para = {
    	    			group_id:t_content.group_id,
    	    			hos_id:hos_id,
    	    			copy_code:t_content.copy_code,
    	    			acc_year:acc_year,
    	    			goal_code:goal_code,
    	    			kpi_code:t_content.dim_name == null ? t_content.dim_code : t_content.kpi_code.toString(),
    	    			kpi_name:t_content.kpi_name,
    	    			dim_code:t_content.dim_code,
    	    			nature_code:t_content.nature_code == null ? '01' : t_content.nature_code,
    	    			super_kpi_code:t_content.dim_name == null ? 'TOP' :t_content.dim_code.toString(),
    	    			order_no:'',
    	    			kpi_level:t_content.dim_name == null ? 1 : 2,
    	    			spell_code:t_content.spell_code,
    	    			wbx_code:t_content.wbx_code,
    	    			is_last:t_content.dim_name == null ? 0 : 1
    	    		};
    	    		
    	    		kpiList.push(para);
    			});
    	    	
    	    	var formPara = {
    	    		data:JSON.stringify(kpiList)
    	    	}
    	    	
    	    	ajaxJsonObjectByUrl("addBatchPrmHosKpi.do",formPara,function(responseData){
    	    		if(responseData.state == "true"){
    	    			query();
    	    		}
    	    	});
    		}
    	});
	}
    
    
    //添加页跳转
	function checkClick(){ 
		
		$.ligerDialog.open({url: 'prmHosKpiAddPage.do?isCheck=false', 
			height: 400,width: 600,title:'添加',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
			data: {
				hos_id:liger.get("hos_id").getValue()
			},
			buttons: [ 
				{ text: '确定', onclick: function (item, dialog) { dialog.frame.savePrmHosKpi(); },cls:'l-dialog-btn-highlight' }, 
			    { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
		
    }
	
    //加载下拉框
    function loadDict(){
    	loadComboBox({id:"#goal_code",url:"../quertPrmGoalDict.do?isCheck=false&prem_data=true",value:"id",text:"text",autocomplete:true,hightLight:true,selectBoxWidth:'auto',maxWidth:'260',defaultSelect:true,async:false})
    	autocomplete("#hos_id","../quertSysHosInfoDict.do?isCheck=false","id","text",true,true,"",true);
    	autodate("#acc_year","yyyy");
    	
    	liger.get('goal_code').set({width:200});
    	$("#acc_year").ligerTextBox({width : 160});
    	
    }  
    
    //删除指标
	function deleteHosKpi(){
		var data = gridManager.getCheckedRows();
	  	if (data.length == 0){
      		$.ligerDialog.warn('请选择行');
      	}else{
          	var ParamVo =[];
          	var flag = true;
          	$(data).each(function (){
          		if(this.is_audit == 1){
          			return flag = false;
          		}
          		
			  	ParamVo.push(
					this.group_id   +"@"+ 
					this.hos_id   +"@"+ 
					this.copy_code   +"@"+ 
					this.acc_year   +"@"+ 
					this.goal_code   +"@"+ 
					this.kpi_code  
				) 
			});
          	
          	if(flag == false){
          		$.ligerDialog.warn('已审核的目标不允许删除 ');
          		return ; 
          	}
			$.ligerDialog.confirm('确定删除?', function (yes){
          	if(yes){
              	ajaxJsonObjectByUrl("deletePrmHosKpi.do",{ParamVo : ParamVo},function (responseData){
              		if(responseData.state=="true"){
              			query();
              			loadTree()
              		}
              	});
          	}
          }); 
      }
    }
	 
	/* 设置树形菜单 */
	function onSelect(note){
		//根据表字段进行添加查询条件
		grid.options.parms=[];
        grid.options.newPage=1;
		
		//获取当前节点的根节点
        var rootNote = tree.getParent(note, 1);
		
		var rid = rootNote == null ? "": rootNote.id;
		
           var id = note.data.id;//当前节点id
           var text = note.data.text;//当前jiedian
           var pid = note.data.pid;//当前节点父id
           
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
           grid.options.parms.push({name:'acc_year',value:$("#acc_year").val()});
           
       		//加载查询条件
       		grid.loadData(grid.where);
       }

    //加载树   
	function loadTree(){
    	
		var param = {
  			acc_year :$("#acc_year").val(),
  			goal_code:liger.get("goal_code").getValue(),
  			hos_id:liger.get("hos_id").getValue()
  		};
		
		ajaxJsonObjectByUrl("queryPrmHosKpiTree.do?isCheck=false", param,function(responseData) {
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
    
    //修改页面跳转    
	function openUpdate(data){
		var params = data.split('|');
		var kpi_code = params[0];
		var goal_code = params[1];
		var acc_year = params[2];
		var group_id = params[3];
		var hos_id = params[4];
		var copy_code = params[5];
		
		var param = 
			'&kpi_code='+kpi_code+
			'&goal_code='+goal_code+
			'&acc_year='+acc_year+
			'&group_id='+group_id+
			'&hos_id='+hos_id+
			'&copy_code='+copy_code;
		
		$.ligerDialog.open({
			url: 'prmHosKpiUpdatePage.do?isCheck=false'+param.toString(),
			height: 400,width: 600,title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
			data: {hos_id:hos_id},
			buttons: [ 
				{ text: '确定', onclick: function (item, dialog) { dialog.frame.savePrmHosKpi(); },cls:'l-dialog-btn-highlight' }, 
				{ text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
			] 
		});
	}

</script>
</head>
</head>

<body style="padding: 0px; overflow: hidden;" >

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
    </table>
	</div>
   	<div id="toptoolmod"></div>
	<div class="l-layout" id="layout1" >
		
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
