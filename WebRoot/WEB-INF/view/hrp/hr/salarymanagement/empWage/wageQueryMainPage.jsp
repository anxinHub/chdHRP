<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String path = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<jsp:include page="${path}/resource.jsp">
	<jsp:param value="hr,grid,dialog,select,tree,form,pageOffice" name="plugins" />
</jsp:include>
<style type="text/css">
*{
	margin: 0;
	padding: 0;
}
/* .main {
	border: 1px solid #aecaf0;
    padding: 40px 0 20px 0;
} */
.radios{
	width: 4%;
	text-align: center; 
}

.radios input{
    margin-bottom: 20px;
}

.ipt {
	width: 24% !important;
}
.content {
	width: 100%;
    height: 50px;
 /*    background: #c3d9fb; */
    display: inline-block;
  /*   margin-bottom: 20px; */
}

.content .numbers{
	display: inline-block;
    width: 15%;
    height: inherit;
    text-align: center;
    line-height: 24px;
    float: left;
}

.content .numbers .nums {
    width: 25px;
    height: 25px;
    border-radius: 50%;
    text-align: center;
    background: #6b97da;
    color: #fff;
    margin-top: 10px;
    display: inline-block;
}

.content .messages{
	display: inline-block;
    width: 85%;
    height: inherit;
    float: left;
}
.content .messages p:nth-child(1){
	margin-top: 7px;
	font-weight: 700;
}

</style>
<script type="text/javascript">
	var tree, grid, wage_plan;
	$(function(){
		initSelect();
		initTree();
		initGrid();
		refreshGridHead(wage_plan.getValue());
		query();
	    initForm();
		// 给输入框绑定搜索树事件(快速定位)
		$(".text-input").on('keyup', function () {
			var $self = $(this)
			searchTree({
                tree: tree,
                value: $self.val(),
                callback: function () {
                    $self.focus();
                }
            })
        });
	});
 
	var initSelect = function(){
		wage_plan = $("#wage_plan").etSelect({
			url: '../wagePlanManage/wagePlanSelect.do?isCheck=false',
            /* defaultValue: 'none', */
            /* onChange: query */
			onChange: function(id){
            	refreshGridHead(id);
            	query()	
            }
		});
		emp_id = $("#emp_id").etSelect({
			url: '../../queryPerson.do?isCheck=false',
            defaultValue: 'none',
            onChange: query

		});
	}
	   function initForm() {
		 	var str = '';
			  ajaxPostData({url:'queryWageQueryForm.do?isCheck=false', data:{plan_code:wage_plan.getValue()}, success:function (res) {// 获取数据
				
				/* 	var mainformEle =  res
				      
			      //	var option=mainformEle.fieldItems;
			    	main_form = $('#main').etForm(mainformEle);
			          
			      	main_form.initWidget();

			          validate = main_form.initValidate();
				

		        } */
				 	if(res.data.length > 0){
	            		$.each(res.data,function(index,item){
	               	
	               			$.each(item.Row,function(i,v){
	               				str += '<tr><td class="radios"><input type="checkbox" name="checkbox" value="'+ index + '" id="'+v.id+'" checked /></td>';
	               				str += '<td class="ipt">';
	               				str += '<span class="content">';
	               				str += '<span class="messages">';
	               				str += '<p>' +v.name+ '</p>';
	               				str += '</span>';
	               				str += '</span>';
	               				str += '</td>';
		               			str += '</tr>';
	               			});
	               			
	               		});
	               		$('.main').html(str);
	            	}
	            }

			  
			  }); 
	       
	      }
	var columns_1 = [
		{ display: '部门名称', name: 'dept_name', width: 120, editable: false },
		{ display: '员工编号', name: 'emp_code', width: 120, editable: false},
		{ display: '姓名', name: 'emp_name', width: 120, 
			editor: {
				type: 'grid',
				columns: [
					{ display: '职工工号', name: 'emp_code', width: 120,align:"left" },
					{ display: '职工名称', name: 'emp_name', width: 120,align:"left" },
					{ display: '部门名称', name: 'dept_name', width: 120,align:"left" },
					{ display: '职工ID', name: 'emp_id', hidden: true }
				],
				width: '700px',
				height: '205px',
				dataModel: {
					url: '',
				}
			}
		}
	];
	var columns_2 = [
		{ display: '合计', name: 'wage_count', width: 120, align: "center", editable: false },
		{ display: '备注', name: 'note', width: 120 }      
	];
	var columns = columns_1.concat(columns_2);
	var initTree = function(){
		tree = $("#mainTree").etTree({
			async: {
                enable: true,
                url: '../../queryHosDeptDictTree.do?isCheck=false'
            },
        	data : {
    			simpleData : {
    				enable : true,
    				idKey : "id",
    				pIdKey : "pId",
    				rootPId : 0
    			},
    			keep : {
    				leaf : true
    			},
    			key : {
    				children : "nodes"
    			}
    		},
    		treeNode : {
    			open : true
    		},
            callback: {
            	onNodeCreated : function(event, treeId, node) {
    				tree.expandNode(node, true, false, false);
    				if (node.nodes && node.level === 0 && node.nodes.length === 0) {
    					treeObj.hideNode(node);
    				}
    			},
    			onClick : function(event, treeId, node) {
    				query(node.id);

                }
            }
		});
	}
	
	var initGrid = function(){
		var toolbar = {
			items: [
				{ type: 'button', label: '查询', listeners: [{click: query}], icon: 'search' },
				{ type: 'button', label: '打印', listeners: [{ click: print }], icon: 'print' },
				{ type: 'button', label: '选择显示列', listeners: [{click: chooseCol}], icon: 'select' }
			]
		};
		
		/* var columns = [
			{ display: '部门名称', name: 'dept_name', width: 120 },
			{ display: '员工编号', name: 'emp_code', width: 120 },
			{ display: '姓名', name: 'emp_name', width: 120 },
			{ display: '基础工资', name: '', width: 120 },
			{ display: '岗位工资', name: '', width: 120 },
			{ display: '应发工资', name: '', width: 120 },
			{ display: '实发工资', name: '', width: 120 }
		]; */
		
		var paramObj = {
			height: '100%',
			inWindowHeight: true,
			rowClick: function (event, ui) {},
			columns: columns,
			toolbar: toolbar
		};
		
		grid = $("#mainGrid").etGrid(paramObj);
	}
	// 刷新表头
	function refreshGridHead(plan_code){
		ajaxPostData({url:'queryWageCheckComputeHead.do?isCheck=false', data:{plan_code:plan_code}, success:function (res) {// 获取数据
			columns = columns_1.concat(res.columns, columns_2);
			columns[2].editor.type = 'grid';
			grid.option('columns', columns);
			grid.option({
				 summary: {
					 keyWordCol: 'dept_name',
					 totalColumns: res.totalColumns
				 }
			});
			
			//grid.getColumns()[3].editor.dataModel.url = 'queryEmpByWagePlanEmpKind.do?isCheck=false&plan_code=' + wage_plan.getValue();
			grid.refreshView();

        }}); 
	}
	
	var query = function(){
		var dept_id;
		var super_code;
	    var selectedNode = tree.getSelectedNodes()[0];
        if(selectedNode){
        	if(selectedNode.id == 0){
        		attend_types="";
        		attend_codes="";
        	}else{
        		if(selectedNode.pId==0){
        			super_code = selectedNode.id;
	        	}else{
	        		dept_id = selectedNode.id;
	        	}
        	}
        }

		params = [
					{ name: 'plan_code', value: wage_plan.getValue() }, 
					{ name: 'emp_id', value: emp_id.getValue() },
					{ name: 'super_code', value: super_code } ,
			        { name: 'dept_id', value: dept_id } 

				];
				grid.loadData(params,'queryWageCheckQueryGrid.do?isCheck=false');
	}
	
	var print = function(){

    	if(grid.getAllData()==null){
    		$.etDialog.error("请先查询数据！");
			return;
		}
    	var heads={
        		 /* "isAuto":true,//系统默认，页眉显示页码
        		"rows": [
    	          {"cell":0,"value":"表名："+tree.getSelectedNodes()[0].name},
        		]  */}; 
    	var printPara={
            title: " 薪资查询打印",//标题
            columns: JSON.stringify(grid.getPrintColumns()),//表头
            class_name: "com.chd.hrp.hr.service.salarymanagement.empWage.HrWageQueryService",
            method_name: "queryWageQueryByPrint",
            bean_name: "hrWageQueryService",
            heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
            foots: '',//表尾需要打印的查询条件,可以为空 
        };
        $.each(grid.getUrlParms(),function(i,obj){
            printPara[obj.name]=obj.value;
        }); 
        //console.log(printPara);
        officeGridPrint(printPara);
    	
	}
	
	var chooseCol = function(){

		$.etDialog.open({
			title: '选择显示列',
			//type: 1, 
			content: $("#itemDiv"), 
			width: 300, 
			height: 300,
			btn: ['确定', '取消'],
			btn1: function (index, el) {
				$("input[name='checkbox']:not(:checked)").each(function() { 
				  // alert(this.id);
				   grid.toggleCol(this.id, true); 
				   
				   	$.etDialog.close(index); // 关闭弹窗
				});
				$("input[name='checkbox']:checked").each(function() { 
					  // alert(this.id);
					   grid.toggleCol(this.id, false); 
					   
					   	$.etDialog.close(index); // 关闭弹窗
					});
			}
		});
	}
	
</script>
<body>
	<div class="container">
        <div class="left border-right">
            <div class="search-form">
                <label>快速定位</label>
                <input type="text" class="text-input">
            </div>
            <div id="mainTree"></div>
            <div class="container-bar"></div>
        </div>
        <div class="center">
            <table class="table-layout">
                <tr>
					<td class="label">薪资方案：</td>
					<td class="ipt">
	                	<select id="wage_plan" style="width:180px;"></select>
	                </td>
					<td class="label">职工：</td>
				<td class="ipt">
                	<select id="emp_id" style="width:180px;"></select>
                </td>
				</tr>
            </table>

            <div id="mainGrid"></div>
        </div>
   <!--      	<div id="itemDiv" style="display: none">
			      <form id='main' class="main">  </form>
	</div> -->
	<table class="table-layout main"  id="itemDiv" style="width: 100%;display: none"></table>
    </div>
</body>
</html>