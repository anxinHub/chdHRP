<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc.jsp" />
<script type="text/javascript">

    var grid;
    var gridManager = null;
    var userUpdateStr;
    var dialog = frameElement.dialog;
    var dialogData ;
    
    $(function (){
    	$("#layout1").ligerLayout({ 
    		leftWidth: 200,
    		heightDiff: -30,
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
    	
        loadDict()//加载下拉框
    	loadHead(null);	
    	loadTree();
    	dialogData = dialog.get('data');//获取data参数
    	
    	$('#dim_code').bind("change",function(){
    		query();
    	});
    });
    //查询
    function  query(){
		//loadTree();
		grid.options.parms=[];
		grid.options.newPage=1;
		//根据表字段进行添加查询条件 
		grid.options.parms.push({name:'hos_id',value:liger.get("hos_id").getValue()}); 
		grid.options.parms.push({name:'dim_code',value:liger.get("dim_code").getValue()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({columns: [ 
			{ display: '指标编码', name: 'kpi_code', align: 'left',width:'20%',render : 
				function(rowdata, rowindex,value) {
                	if(!rowdata.dim_name){
                    	return rowdata.kpi_code;
                    }
					return "<a href=javascript:openUpdate('"+
						rowdata.kpi_code   + "|" + 
						rowdata.group_id   + "|" + 
						rowdata.hos_id   + "|" + 
						rowdata.copy_code  +"')>"
						+rowdata.kpi_code+"</a>";
				}
			},
            { display: '指标名称', name: 'kpi_name', align: 'left',width:'20%',render:
            	function(rowdata){
                	if(rowdata.dim_name){
                    	return "&nbsp;&nbsp;&nbsp;&nbsp;"+rowdata.kpi_name;
                    }else{
                    	return rowdata.kpi_name;
                    }
                }
			},

			{ display: '指标性质', name: 'nature_code', align: 'left',width:'20%',render : 
				function(rowdata, rowindex,value) {
					if(rowdata.nature_code == 01){
						return "正向";
					}else if(rowdata.nature_code == 02) {
						return "反向"
					}else{
						return "";
					}
				}
			},
			
            { display: '指标描述', name: 'kpi_note', align: 'left',width:'40%'}
        ],
        dataAction: 'server',dataType: 'server',usePager:true,url:'<%=path%>/hrp/prm/prmkpilib/queryPrmKpiLibDimPkns.do',
        width: '100%', height: '100%', checkbox: true,rownumbers:true,isScroll:true,onCheckRow:
        	function(checked,data,rowid,rowdata){
            	var allData = grid.rows;
                    	 
                //判断是否是维度,即一级
                if(data.dim_name == null){
                	if(checked){//选中一级,子级全选
	                	$.each(allData,function(index,content){
	                    	if(content.dim_code == data.dim_code){grid.select(content.__id);}
	                    });
                    }else{//清空一级,取消全选
                    	$.each(allData,function(index,content){
 	                    	if(content.dim_code == data.dim_code){ grid.unselect(content.__id);}
 	                    });
                    }
                    return ; 
                 }
                    	 
                 if(checked){//非一级,选中子级就选中父级
                 	$.each(allData,function(index,content){
                    	if(content.dim_code == data.dim_code && content.dim_name == null){
                     		grid.select(content.__id);
                     	}
                    });
                    return ; 
                 }else{//如果是最后一个子集,取消选中父级
                 	var count = 0 ;
                 	$.each(allData,function(index,content){//判断选中状态
						if(data.dim_code == content.dim_code && content.dim_name !=null ){
							var flag = grid.isSelected(content.__id);
							if(flag){
								count++;
							}
						}	                     		 
	                });
	                     	 
		            if(count == 0){//判断是否还有选中的子级
		            	$.each(allData,function(index,content){
							if(data.dim_code == content.dim_code){
								grid.unselect(content.__id);
							}	                     		 
			            });
		            }
                 }
             },
            selectRowButtonOnly:true,//heightDiff: -10,
    		onDblClickRow : function (rowdata, rowindex, value){
    			if(rowdata.dim_name == null){
    				$.ligerDialog.warn('请选择指标 ');
    				return ; 
    			}
				
    			openUpdate(
					rowdata.kpi_code +"|"+
					rowdata.group_id   + "|" + 
					rowdata.hos_id   + "|" + 
					rowdata.copy_code   
				);
    		} ,isChecked: f_isChecked
		});
        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function openUpdate(obj){
		var vo = obj.split("|");
		var parm = 
			"&kpi_code="+ vo[0] +
			"&group_id="+ vo[1] +
			"&hos_id="+ vo[2]   +
			"&copy_code="+ vo[3] 
    	$.ligerDialog.open({ url : '<%=path%>/hrp/prm/prmkpilib/prmKpiLibUpdatePage.do?isCheck=false&' + parm,
    		data:{}, height: 400,width: 700, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.savePrmKpiLib(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
    }

    function f_isChecked(rowdata){
    	var targetData =dialogData.targetData;
    	
    	if(targetData != null){
    		var flag = 0;
    		$.each( targetData, function(index, content){
    			
      		if(rowdata.kpi_code ==  content.kpi_code){
      			flag = 1;
      			return false;
      		}
      		}); 
    		
    		if(flag == 1){
    			return true;
    		}else{
    			return false;
    		}
    	}
    }
    
	function confirmPrmKpiLib() { 

    	var target_data = gridManager.getSelectedRows(); 
    	var map = new HashMap();
    	
    	var arr = [];
    	
    	for(var i = 0,length = target_data.length;i <length; i++){
    		var key = target_data[i].__id;
    		if(map.get(key) == null){//去重
	    		arr.push(target_data[i]);
	    		map.put(key,key);
    		}
    	}
    	
    	parent.addRows(arr,dialogData.__id); 
    	
	}
      
	function onSelect(note){
		grid.options.parms=[];
		grid.options.newPage=1;
           
		//根据表字段进行添加查询条件             
        //grid.options.parms.push({name:'dim_code',value:note.data.id}); 
        if(note.data.pid == -1  ){
        	grid.options.parms.push({name:'dim_code',value:note.data.id});
        }
          
		if(note.data.pid != -1  && note.data.pid != -2){
        	grid.options.parms.push({name:'dim_code',value:note.data.pid});
        }
		  
        if(note.data.pid != -1 && note.data.pid != -2){
        	grid.options.parms.push({name:'kpi_code',value:note.data.id});
        }

       	//加载查询条件
       	grid.loadData(grid.where);
	}
       
	function loadDict(){
		//字典下拉框
		autocomplete("#dim_code","<%=path%>/hrp/prm/queryPrmKpiDim.do?isCheck=false","id","text",true,true);
        autocomplete("#hos_id","<%=path%>/hrp/prm/quertSysHosInfoDict.do?isCheck=false","id","text",true,true,"",true);
    }  
       
	function loadTree(){
		var formPara={
        	hos_id:liger.get("hos_id").getValue(),
        };
        	        
		ajaxJsonObjectByUrl("<%=path%>/hrp/prm/prmkpilib/queryPrmKpiLibByMenu.do?isCheck=false",formPara,function(responseData) {
			if (responseData != null) {
				tree = $("#tree").ligerTree({
					data : responseData.Rows,
					checkbox : false,
					idFieldName : 'id',
					parentIDFieldName : 'pid',
					onSelect : onSelect,
					isExpand : 3,
					nodeWidth :400
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
		<table cellpadding="0" cellspacing="0" class="l-table-edit">

			<tr>
			</tr>

			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">单位信息：</td>
				<td align="left" class="l-table-edit-td"><input name="hos_id"
					type="text" id="hos_id" ltype="text"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">维度名称：</td>
				<td align="left" class="l-table-edit-td"><input name="dim_code"
					type="text" id="dim_code" ltype="text"
					validate="{required:true,maxlength:20}" /></td>
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
	
	<div id="resultPrint" style="display: none">
		<table width="100%">
			<thead>

				<tr>
					<th width="200">指标编码</th>
					<th width="200">指标名称</th>
					<th width="200">指标性质</th>
					<th width="200">维度编码</th>
					<th width="200">维度描述</th>
					<th width="200">是否停用</th>

				</tr>
			</thead>
			<tbody></tbody>
		</table>
	</div>
</body>
</html>
