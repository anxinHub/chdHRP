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
<style type="text/css">
.search-form {
    padding: 5px;
    /* text-align: center; */
    box-sizing: border-box;
    background: #e0ecff;
    border-bottom: 1px solid #a3c0e8;
}
button {
        	margin: 0px 5px;
       		box-sizing: border-box;
       		height: 26px;
       		padding-left: 10px;
       		padding-right: 10px;
       		border: 1px solid #aecaf0;
       		background: #c1dbfa;
       		outline: none;
       		border-radius: 2px;
       		cursor: pointer;
       		-webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .1);
       		box-shadow: inset 0 1px 1px rgba(0, 0, 0, .1);
         	text-align: center
        }
       	#table {
       		width: 80%;
    		    margin: 0 auto;
    border-collapse: collapse;
 /*   margin-left: 2px;  */
		}
		 	#table2 {
       		width: 80%;
    		    margin: 0 auto;
    border-collapse: collapse;
   /*  margin-left: -13px; */
		}
		#tableth{
		position: relative;
    padding-left: 10px;
    color: #183152;
    font-weight: 700;
    height: 25px;
    line-height: 24px;
    background: #e5effe url(../images/layout/layout-header.gif?v=2017-02-22) repeat-x;
    overflow: auto;
    cursor: pointer;
		}
		#main{
	margin-left: -15px;
    position: relative;
    padding-left: 20px;
    color: #183152;
    font-weight: 700;
    height: 25px;
    width: 140%;
    line-height: 24px;
    background: #e5effe url(../images/layout/layout-header.gif?v=2017-02-22) repeat-x;
    overflow: auto;
    cursor: pointer;
		}
		#caption {
		margin-left: -120px;
    position: relative;
    padding-left: 20px;
    color: #183152;
    font-weight: 700;
    height: 25px;
    width: 140%;
    line-height: 24px;
    background: #e5effe url(../images/layout/layout-header.gif?v=2017-02-22) repeat-x;
    overflow: auto;
    cursor: pointer;
		}
		span.title{
   display: block;
    width: 100%;
    border: 1px solid #CCCCCC;
    position: relative;
    text-align: left;
    background: #e0ecff;
    font-size: 14px;
    font-weight: bold;
    text-indent : 10px;
}
</style>
<script type="text/javascript">
	var grid;
	var rightGrid;
	var gridManager = null;
	var rightManager=null;
	var is_update;
	
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
		//liger.get("station_code").setValue(dialogData.param[0].station_code);
		
		
		 $("#station_code").ligerTextBox({width:160,disabled:true});
       	$("#station_name").ligerTextBox({width:160,disabled:true});
       	$("#station_num").ligerTextBox({width:160,disabled:true});
       	$("#dept_name").ligerTextBox({width:160,disabled:true});
        $("#education").ligerTextBox({width:160,disabled:true});
       	$("#discipline").ligerTextBox({width:160,disabled:true});
       	$("#workage").ligerTextBox({width:160,disabled:true});
       	$("#academic").ligerTextBox({width:160,disabled:true});
        $("#permit").ligerTextBox({width:160,disabled:true});
       	$("#technique").ligerTextBox({width:160,disabled:true});
       	$("#age").ligerTextBox({width:160,disabled:true});
        $("#kind_code").ligerTextBox({width:160,disabled:true});
       	$("#interpersonal").ligerTextBox({width:160,disabled:true}); 
       	/*
       	   查询数据
       	*/
       	
    		initTree();
});
	  
	  function initTree() {
	        tree = $("#mainTree").etTree({
	        	async: {
	                enable: true,
	                url: '../../queryStManTree.do?isCheck=false'
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
	                	query1(node);
	                	query(node);
	                }
	            },
	            
	        });
	        
	  /*       hos_name = $("#hos_name").etSelect({
	            url: '../../queryHosInfoSelect.do?isCheck=false',
	            defaultValue: '${sessionScope.hos_id}'
	        }); */
	    };
	  
	//查询
	function query(node) {
		grid.options.parms = [];
		grid.options.newPage = 1;
		
		//根据表字段进行添加查询条件
 	grid.options.parms.push({
			name : 'station_code',
			value : node.id
		}); 
	grid.options.parms.push({
		name : 'dept_id',
		value : node.DEPT_ID
	}); 
	/*grid.options.parms.push({
		name : 'dept_no',
		value : dialogData.param[0].dept_no
	});  */
	
		
		grid.options.parms.push({
			name : 'tab_code',
			value : 'HR_STATION_DESCRIPTION'
		}); 

		//加载查询条件
		grid.loadData(grid.where);
		
	}
	function query1(node){
		 $("#station_code").val('')
			$("#station_name").val('');
			$("#station_num").val('');
			$("#dept_name").val('');
			$("#note").val('');
			$("#education").val('');
			$("#discipline").val('');
			$("#workage").val('');
			$("#academic").val('');
			$("#permit").val('');
			$("#technique").val('');
			$("#age").val('');
			$("#kind_code").val('');
			$("#interpersonal").val('');
		ajaxJsonObjectByUrl("queryStationDescription.do?isCheck=false&tab_code=HR_STATION_DESCRIPTION",{
			'tab_code' : 'HR_STATION_DESCRIPTION',
			'station_code':node.id,
			 'dept_id':node.DEPT_ID,
			 'rjt':'json'
		
		},function(data){
			
			 $.each(data.Rows,function(i,v){
				 $("#station_code").val(v.STATION_CODE)
					$("#station_name").val(v.STATION_NAME);
					$("#station_num").val(v.STATION_NUM);
					$("#dept_name").val(v.DEPT_ID_NAME);
					$("#note").val(v.NOTE);
					$("#education").val(v.EDUCATION_NAME);
		   			$("#discipline").val(v.DISCIPLINE_NAME);
		   			$("#workage").val(v.WORKAGE);
		   			$("#academic").val(v.ACADEMIC_NAME);
		   			$("#permit").val(v.PERMIT);
		   			$("#technique").val(v.TECHNIQUE);
		   			$("#age").val(v.AGE);
		   			$("#kind_code").val(v.KIND_CODE_NAME);
		   			$("#interpersonal").val(v.INTERPERSONAL);
				 
				 
			 })
			
			
		})
		//
		
	}
	function loadHead() {
		grid = $("#maingrid")
				.ligerGrid(
						{
							columns : [
				                        { display: '职责描述',name: 'obligation',width: 200,algin:'left',editor : {
				    						type : 'text'
				    					},},
				                        { display: '工作任务',name: 'tasks',width: 100,algin:'left',editor : {
				    						type : 'text'
				    					},},
				            			{ display: '工作比重',name: 'proportion',width: 100,algin:'left',editor : {
				    						type : 'text'
				    					},}
				            			
				            			],
							dataAction : 'server',
							dataType : 'server',
							usePager : false,
							url : "queryStaDesProportion.do",
							width : '100%',
							height : '100%',
							delayLoad : true,
							checkbox : true,
							rownumbers : true,
							enabledEdit : true,
							selectRowButtonOnly : true,//heightDiff: -10,
					 	toolbar : {
								items : [ /* {
									text : '添加',
									id : 'add',
									click : add,
									icon : 'add'
								}, {
									line : true
								},  */{
									text : '删除',
									id : 'delete',
									click : dele,
									icon : 'delete'
								}, {
									line : true
								}  ]
							}  
						});

		gridManager = $("#maingrid").ligerGetGridManager();
	}


 
	function add(){
		/* grid.addRow(); */
	} 


	 function dele(){
		 /*var data = grid.getCheckedRows();
	    if (data.length == 0) {
	        $.etDialog.error('请选择行');
	    } else {
	    	var param = [];
	        $(data).each(function () {
	            var rowdata = this;
	            rowdata.tab_code='HR_STATION_DUTY';
	            param.push(rowdata);
	        });
	     
	        $.ligerDialog.confirm('确定删除?' , function (yes){
	            	if(yes){
	                	ajaxJsonObjectByUrl("deleteStaDesProportionBatch.do?isCheck=false",{paramVo: JSON.stringify(param)},function (responseData){
	                		if(responseData.state=="true"){
	                			query();
	                		}
	                	});
	            	}
	        });
	    }*/
	} 
/* function saveData(){
	
	gridManager.endEdit();
	
	
	var data = gridManager.getData();
	var num = 0;
	for (var i = 0; i < data.length; i++) {

		if (data[i].emp_code) {
			num++;
		}
	}
	
	var formPara = {
			dept_no : dialogData.param[0].dept_no,
			dept_id: dialogData.param[0].dept_id,
			station_code : dialogData.param[0].station_code,
			tab_code:"HR_STATION_DESCRIPTION",
			education:$("#education").val(),
			discipline:$("#DISCIPLINE").val(),
			workage:$("#workage").val(),
			academic:$("#academic").val(),
			permit:$("#permit").val(),
			technique:$("#technique").val(),
			age:$("#age").val(),
			kind_employment:$("#kind_employment").val(),
			interpersonal:$("#interpersonal").val(),

		};
	
	if(is_update!=null){
		ajaxJsonObjectByUrl("updateStationDescription.do", formPara, function(
				responseData) {
			if (!num) {
				
			var dataPara = {
					dept_no : dialogData.param[0].dept_no,
					dept_id: dialogData.param[0].dept_id,
					station_code : dialogData.param[0].station_code,
					tab_code : dialogData.param[0].tab_code, 
				
					
				ParamVo : JSON.stringify(data)
			};
			
			
				ajaxJsonObjectByUrl("updateStaDesProportionBatch.do", dataPara, function(
						responseData) {
					if (responseData.state == "true") {
						  parent.query();
					}
				});
				
			}else{
				  parent.query();
			}
		});
	}else{
	ajaxJsonObjectByUrl("addStationDescription.do", formPara, function(
			responseData) {
		if (!num) {
			
		var dataPara = {
				dept_no : dialogData.param[0].dept_no,
				dept_id: dialogData.param[0].dept_id,
				station_code : dialogData.param[0].station_code,
				tab_code : dialogData.param[0].tab_code, 
			
				
			ParamVo : JSON.stringify(data)
		};
		
		
			ajaxJsonObjectByUrl("addStaDesProportionBatch.do", dataPara, function(
					responseData) {
				if (responseData.state == "true") {
					  parent.query();
				}
			});
			
		}else{
			  parent.query();
		}
	});
	} 
}*/
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="layout1">
		<div position="left" title="岗位">
		 <div class="left border-right">
			<!--  <div class="search-form">
                <label>快速定位</label>
                <input type="text" class="text-input">
            </div> -->
            <div id="mainTree"></div>
		</div>
		</div>
		<div position="center"> 
		<div>
		<span class="title">基础信息</span>
		 <table cellpadding="0" cellspacing="0" class="l-table-edit"  id="table">
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">标准岗位：</td>
            <td align="left" class="l-table-edit-td"><input name="station_code" ltype="text" id="station_code" ltype="text"  validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室岗位名称：</td>
            <td align="left" class="l-table-edit-td"><input name="station_name" ltype="text" id="station_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
             <td align="right" class="l-table-edit-td"  style="padding-left:20px;">定额人数：</td>
            <td align="left" class="l-table-edit-td"><input name="station_num" ltype="text" id="station_num" ltype="text"  validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
               <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室：</td>
                <td align="left" class="l-table-edit-td">
                    <input name="dept_name" ltype="text" id="dept_name" ltype="text"  validate="{required:true,maxlength:20}" />
                </td>
                 <td align="left"></td>
                 <td align="right" class="l-table-edit-td"  style="padding-left:20px;">备注：</td>
	            <td  align="left" class="l-table-edit-td" colspan="4" >
	                    <textarea id="note" rows="2" cols="80" class="wishContent" maxlength="100" disabled="disabled"></textarea>
	            </td>
	             <td align="left"></td>
			</tr>
  </table>
	
		 <table cellpadding="0" cellspacing="0" class="l-table-edit"  id="table2">
		<span class="title">所需资格信息</span>
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">学历：</td>
            <td align="left" class="l-table-edit-td"><input name="education" ltype="text" id="education" ltype="text" value="${education}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">专业要求：</td>
            <td align="left" class="l-table-edit-td"><input name="discipline" ltype="text" id="discipline" ltype="text" value="${discipline}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
             <td align="right" class="l-table-edit-td"  style="padding-left:20px;">工作经验：</td>
            <td align="left" class="l-table-edit-td"><input name="workage" ltype="text" id="workage" ltype="text" value="${workage}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
               <td align="right" class="l-table-edit-td"  style="padding-left:20px;">学位：</td>
                <td align="left" class="l-table-edit-td">
                    <input name="academic" ltype="text" id="academic" ltype="text" value="${academic}" validate="{required:true,maxlength:20}" />
                </td>
                 <td align="left"></td>
               <td align="right" class="l-table-edit-td"  style="padding-left:20px;">专业证照：</td>
                <td align="left" class="l-table-edit-td">
                    <input name="permit" ltype="text" id="permit" ltype="text" value="${permit}" validate="{required:true,maxlength:20}" />
                </td>
                 <td align="left"></td>
                 <td align="right" class="l-table-edit-td"  style="padding-left:20px;">专业知识及技能：</td>
                <td align="left" class="l-table-edit-td">
                    <input name="technique" ltype="text" id="technique" ltype="text" value="${technique}" validate="{required:true,maxlength:20}" />
                </td>
                 <td align="left"></td>
			</tr>
			      <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">年龄范围：</td>
            <td align="left" class="l-table-edit-td"><input name="age" ltype="text" id="age" ltype="text" value="${age}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">用工形式：</td>
            <td align="left" class="l-table-edit-td"><input name="kind_code" ltype="text" id="kind_code" ltype="text" value="${kind_code}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
             <td align="right" class="l-table-edit-td"  style="padding-left:20px;">人际能力：</td>
            <td align="left" class="l-table-edit-td"><input name="interpersonal" ltype="text" id="interpersonal" ltype="text" value="${interpersonal}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr>
    </table>
		</div>
			<span class="title">工作职责</span>
			<div id="maingrid" ></div>
			
		
		</div>


</body>
</html>
