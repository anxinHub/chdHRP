<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
 <script src="<%=path%>/lib/et_components/et_plugins/etTree.min.js"></script>
    <script src="<%=path%>/lib/hrp/hr/hr.js"></script> 
<style type="text/css">
.search-form {
    padding: 5px;
    /* text-align: center; */
    box-sizing: border-box;
    background: #e0ecff;
    border-bottom: 1px solid #a3c0e8;
}
 .l-layout-center{
width: 710px !important;
    top: 0px;
    left: 240px !important;
    height: 632px;

}
.l-layout-right{
   width: 370px !important;
    top: 0px;
    height: 632px;
    left: 955px !important;

} 
.mainTree { 
	width: auto;
	/* height: 800px; */
	position: relative;
	overflow: auto;
}
</style>
<script type="text/javascript">
	var grid;
	var rightGrid;
	var gridManager = null;
	var rightManager=null;
	var tree;
	
	$(function() {
		$("#layout1").ligerLayout({
			leftWidth : 235,
			//heightDiff: -8,
			//每展开左边树即刷新一次表格，以免结构混乱
			onLeftToggle : function() {
				grid._onResize()
			},
			//每调整左边树宽度大小即刷新一次表格，以免结构混乱
			onEndResize : function(a, b) {
				grid._onResize()
			}
		});
		//加载数据
		loadHead(null);
		loadRighHead(null);
	
		initTree();
	
		/*  // 给输入框绑定搜索树事件
	        $(".text-input").on('keyup', function () {
	            var $self = $(this)
	            searchTree({
	                tree: tree,
	                value: $self.val(),
	                callback: function () {
	                    $self.focus();
	                }
	            })
	        }) */
		
  	 
});
	  function initTree() {
	        tree = $("#mainTree").etTree({
	        	async: {
	                enable: true,
	                url: 'queryDeptTreeMod.do?isCheck=false&rjt='+"tree"
	            },
	        	data : {
	    			simpleData : {
	    				enable : true,
	    				idKey : "id",
	    				pIdKey : "pid",
	    				rootPId : 0
	    			},
	    			keep : {
	    				leaf : true
	    			},
	    			key : {
	    				children : "nodes"
	    			}
	    		},
	    		/* edit : {
	    			enable : true,
	    			removeTitle : '删除',
	    			showRemoveBtn : setShowBtn,
	    			showRenameBtn : false
	    		}, */
	    		treeNode : {
	    			open : true
	    		},
	            callback: {
	            	
	    			onClick : function(event, treeId, node) {
	                	query(node.dept_id);
	                	//queryRight(node.dept_id);
	                }
	            },
	            
	        });
	        
	  /*       hos_name = $("#hos_name").etSelect({
	            url: '../../queryHosInfoSelect.do?isCheck=false',
	            defaultValue: '${sessionScope.hos_id}'
	        }); */
	    };
	    

	//查询
	function query(code) {
		grid.options.parms = [];
		grid.options.newPage = 1;
		
		var dept_id;
		var selectedNode = tree.getSelectedNodes()[0];
		//根据表字段进行添加查询条件
		  if(selectedNode){
			  dept_id=selectedNode.dept_id
		  }else{
			  dept_id=code
		  }
			rightGrid.options.parms.push({
			name : 'dept_id',
			value : dept_id
		}); 

			grid.options.parms.push({
			name : 'dept_id',
			value : dept_id
		}); 
		grid.options.parms.push({
			name : 'tab_code',
			value : 'HR_STATION_MANAGE'
		}); 

		//加载查询条件
		grid.loadData(grid.where);
		
	}
	//查询
	function queryRight(code) {
		var data = grid.getCheckedRows();
		rightGrid.options.parms = [];
		rightGrid.options.newPage = 1;
		var dept_id;
		var selectedNode = tree.getSelectedNodes()[0];
		//根据表字段进行添加查询条件
		  if(selectedNode){
			  dept_id=selectedNode.dept_id
		  }else{
			  dept_id=code
		  }
			rightGrid.options.parms.push({
			name : 'dept_id',
			value : dept_id
		}); 
			rightGrid.options.parms.push({
			name : 'tab_code',
			value : 'HR_STATION_MANNUM'
		}); 
			rightGrid.options.parms.push({
				name : 'station_kind',
				value :rowData.station_code
			}); 
		//加载查询条件
		rightGrid.loadData(rightGrid.where);
		
	}

	function loadHead() {
    	  var columns=getGridColumns({ui:'liger',group_id:${group_id},hos_id:${hos_id},gridTables:['HR_STATION_MANAGE'],design:'queryStationManage'});

		grid = $("#maingrid").ligerGrid({
			     columns:columns,
							/* columns : [
				                        { display: '科室名称',name: 'dept_id_name',width: 200,algin:'left'},
				                        { display: '岗位编码',name: 'station_code',width: 100,algin:'left',
				                        	render: function (rowdata, rowindex,
				    								value) {
				                        		return "<a href=javascript:openUpdate('" + rowdata.group_id   + "|" + rowdata.hos_id + "|" + rowdata.dept_id + "|" + rowdata.dept_no  + "|" + rowdata.station_code +"')>"+rowdata.station_code+"</a>";
				                            }	
				                        },
				            			{ display: '岗位名称',name: 'station_name',width: 100,algin:'left'},
				            			
				            			{ display: '定岗人数',name: 'station_num',width: 100,algin:'left'},
				            			{ display: '备注',name: 'note',width: 100,algin:'left'}
				            			
				            			], */
							dataAction : 'server',
							dataType : 'server',
							usePager : true,
							url : "queryStationManage.do",
							width : '100%',
							height : '100%',
							delayLoad : true,
							checkbox : true,
							rownumbers : true,
							selectRowButtonOnly : true,//heightDiff: -10,
							//onSelectRow :queryNum,
							onClickRow:queryNum,
							toolbar : {
								items : [ {
									text : '定岗',
									id : 'add',
									click : add,
									icon : 'add'
								}, {
									line : true
								}, {
									text : '删除岗位',
									id : 'delete',
									click : dele,
									icon : 'delete'
								}, {
									line : true
								}, {
									text : '定员',
									id : 'add',
									click : addEmp,
									icon : 'add'
								}, {
									line : true
								}, {
									text : '岗位说明书',
									id : 'add',
									click : addDesc,
									icon : 'add'
								}  ]
							},
							onDblClickRow : function(rowdata, rowindex, value) {
							
									openUpdate(
											rowdata)
								
							}
						});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	function loadRighHead() {
  	  var rightcolumns=getGridColumns({ui:'liger',group_id:${group_id},hos_id:${hos_id},gridTables:['HOS_EMP'],design:'queryStationManageNum.do'});

		rightGrid = $("#rightgrid")
				.ligerGrid(
						{
							columns : rightcolumns/* [
							           { display: '工号',name: 'emp_id',width: 100,algin:'left'},
			                           { display: '姓名',name: 'emp_name',width: 100,algin:'left'},
			               			{ display: '岗位名称',name: 'station_kind_name',width: 100,algin:'left'}
			               			
				            			] */,
							dataAction : 'server',
							dataType : 'server',
							usePager : true,
							url : 'queryStationManageNum.do',
							width : '100%',
							height : '100%',
							delayLoad : true,
							checkbox : true,
							rownumbers : true,
							selectRowButtonOnly : true,//heightDiff: -10,
							toolbar : {
								items : [ {
									text : '删除定员',
									id : 'deleteNum',
									click : deleNum,
									icon : 'delete'
								},  {
									line : true
								}
								]
							}
						});

		rightManager = $("#rightgrid").ligerGetGridManager();
	}

		function openUpdate(obj) {


		
			var parm ="&group_id="+ 
			obj.group_id + "&hos_id=" + 
			obj.hos_id  + "&dept_id=" + 
			obj.dept_id  + "&dept_no=" + 
			obj.dept_no  + "&station_code="+
			obj.station_code +"&tab_code="+"HR_STATION_MANAGE";
			
			parent.$.ligerDialog.open({
				url: 'hrp/hr/os/stationmanage/stationManageUpdatePage.do?isCheck=false' + parm,
				 title: '定岗修改',
				 height : $(window).height()-200,
					width : $(window).width(),
    				parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
    				   data:{
    					   obj
    	                },
			        buttons : [ {
						text : '确定',
						onclick : function(item, dialog) {
							dialog.frame.saveData();
						},
						cls : 'l-dialog-btn-highlight'
					}, {
						text : '取消',
						onclick : function(item, dialog) {
							dialog.close();
						}
					} ] });
			}
			
	
function add(){
	parent.$.ligerDialog.open({
		 url: 'hrp/hr/os/stationmanage/stationManageAddPage.do?isCheck=false',
        title: '定岗',
        height : $(window).height()-200,
		width : $(window).width(),
       // parentframename: window.name,
        buttons : [ {
			text : '确定',
			onclick : function(item, dialog) {
				dialog.frame.saveData();
			},
			cls : 'l-dialog-btn-highlight'
		}, {
			text : '取消',
			onclick : function(item, dialog) {
				dialog.close();
			}
		} ] });
}
function dele(){
	var data = grid.getCheckedRows();
    if (data.length == 0) {
    	$.ligerDialog.error('请选择行');
    } else {
    	var param = [];
        $(data).each(function () {
            var rowdata = this;
            rowdata.tab_code='HR_STATION_MANAGE';
            param.push(rowdata);
        });
     
        $.ligerDialog.confirm('确定删除?' , function (yes){
            	if(yes){
                	ajaxJsonObjectByUrl("deleteStationManage.do?isCheck=false",{paramVo: JSON.stringify(param)},function (responseData){
                		if(responseData.state=="true"){
                			query();
                		}
                	});
            	}
        });
    }
}
function deleNum(){
	var data = rightGrid.getCheckedRows();
	
    if (data.length == 0) {
    	$.ligerDialog.error('请选择行');
    } else {
    	var param = [];
        $(data).each(function () {
            var rowdata = this;
            rowdata.tab_code='HR_STATION_MANAGE';
            
            param.push(rowdata);
        });
     
        $.ligerDialog.confirm('确定删除?' , function (yes){
            	if(yes){
                	ajaxJsonObjectByUrl("deleteEmpKind.do?isCheck=false",{paramVo: JSON.stringify(param)},function (responseData){
                		if(responseData.state=="true"){
                			rightGrid.deleteSelectedRow();
                		}
                	});
            	}
        });
    }
	
	
}
function addEmp(){
	var data = grid.getCheckedRows();
   if (data.length == 0) {$.ligerDialog.error('请选择行');return;} 
       
       if(data.length > 1){$.ligerDialog.error('只能选择一行');return;}

    	var param = [];
        $(data).each(function () {
            var rowdata = this;
            rowdata.tab_code='HR_STATION_MANNUM';
            param.push(rowdata);
        });
     
      
    
	
	$.ligerDialog.open({
        url: "stationManageNumAddPage.do?isCheck=false&addURL=' +'${URL}'+'&pageUrl=' +'${pageUrl}'",
        title: '定员',
        width: 450,
        height: 500,
        data: {
        	param
        },
       parentframename: window.name,
        buttons : [ {
			text : '确定',
			onclick : function(item, dialog) {
				dialog.frame.saveData();
			},
			cls : 'l-dialog-btn-highlight'
		}, {
			text : '取消',
			onclick : function(item, dialog) {
				dialog.close();
			}
		} ] });
    
}
function addDesc(){
	var data = grid.getCheckedRows();
   if (data.length == 0) {$.ligerDialog.error('请选择行');return;} 
       
       if(data.length > 1){$.ligerDialog.error('只能选择一行');return;}

    	var param = [];
        $(data).each(function () {
            var rowdata = this;
            rowdata.tab_code='HR_STATION_MANNUM';
            param.push(rowdata);
        });
     
      
  
	
		
		parent.$.ligerDialog.open({
			url: "hrp/hr/os/stationmanage/stationManageDescAddPage.do?isCheck=false&addURL=' +'${URL}'+'&pageUrl=' +'${pageUrl}'",
        title: '岗位说明书',
        showMax:true,
        data: {
        	param
        },
       parentframename: window.name,
        });
    
}
//查询定岗人员
function queryNum(rowData) {
	
	rightGrid.options.parms = [];
	rightGrid.options.newPage = 1;
	
	//根据表字段进行添加查询条件

	rightGrid.options.parms.push({
		name : 'dept_no',
		value : rowData.dept_no
	}); 
	rightGrid.options.parms.push({
		name : 'dept_id',
		value : rowData.dept_id
	}); 
	rightGrid.options.parms.push({
		name : 'station_kind',
		value :rowData.station_code
	}); 
	/* rightGrid.options.parms.push({
		name : 'tab_code',
		value : 'HR_STATION_MANNUM'
	});  */
	rightGrid.options.parms.push({
		name : 'tab_code',
		value : 'HR_STATION_MANNUM'
	}); 

	//加载查询条件
	rightGrid.loadData(rightGrid.where);
	
}


</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="layout1">
		<div position="left" title="科室结构">
		 <div class="left border-right">
			<!--  <div class="search-form">
                <label>快速定位</label>
                <input type="text" class="text-input">
            </div> -->
            <div id="mainTree" style="height:660px;overflow:auto"></div>
		</div>
		</div>
		<div position="center"  title="科室岗位">  
			<div id="maingrid"></div>
		</div>
	
<div position="right" title="岗位人员"  class="cent">

			<div id="rightgrid"></div>
</div>

	</div>
</body>
</html>
