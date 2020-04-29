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
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
		 loadHotkeys();
        $("#dept_id").ligerTextBox({width:160});
        $("#disease_code").ligerTextBox({width:160});
		
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	  grid.options.parms.push({name:'dept_id',value:liger.get("dept_id").getValue()}); 
        
    	  grid.options.parms.push({name:'disease_code',value:liger.get("disease_code").getValue()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '科室编码', name: 'dept_code', align: 'left',
                    	 render : function(rowdata, rowindex,value) {
								return "<a href=javascript:openUpdate('"+rowdata.group_id+"|"+ rowdata.hos_id+"|"+rowdata.copy_code+"|"+rowdata.dept_id+"|"+rowdata.disease_code+"')>"+rowdata.dept_code+"</a>";
						}
					 		},
					 { display: '科室名称', name: 'dept_name', align: 'left'
					 		},
					 { display: '病种编码', name: 'disease_code', align: 'left'
						 	},
					{ display: '病种名称', name: 'disease_name', align: 'left'
							 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryBudgDeptSingleDisease.do?isCheck=false&',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询（<u>E</u>）', id:'search', click: query,icon:'search' },
                     	{ line:true },
    					{ text: '添加（<u>A</u>）', id:'add', click: add_open, icon:'add' },
    	                { line:true },
    	                { text: '打印（<u>P</u>）', id:'print', click: printDate,icon:'print' },
		                { line:true },
    	                { text: '删除（<u>D</u>）', id:'delete', click: remove,icon:'delete' },
		                { line:true },
		                { text: '下载导入模板（<u>B</u>）', id:'downTemplate', click:downTemplate,icon:'down' },
		                { line:true },
		                { text: '导入（<u>I</u>）', id:'import', click: imp,icon:'up' }
    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code   + "|" + 
								rowdata.dept_id   + "|" + 
								rowdata.disease_code 
							);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function add_open(){
    	
		$.ligerDialog.open({ url : 'budgDeptSingleDiseaseAddPage.do?isCheck=false&',data:{}, height: 300,width: 450,
			title:'科室单病种维护',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
			buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.addBudgDeptSingleDisease();  },cls:'l-dialog-btn-highlight' },
			           { text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
			          ]
    	}); 
		
    }
    	
    //打印
    function printDate(){
    	if(grid.getData().length==0){
			$.ligerDialog.error("无打印数据！");
			return;
		}
	    
    	grid.options.parms=[];
    	grid.options.newPage=1;
        var selPara={};
    	$.each(grid.options.parms,function(i,obj){
    		selPara[obj.name]=obj.value;
    	});
   		var printPara={
   			headCount:2,
   			title:"医保单病种维护",
   			type:3,
   			columns:grid.getColumns(1)
   		};
   		ajaxJsonObjectByUrl("queryBudgDeptSingleDisease.do?isCheck=false", selPara, function(responseData) {
   			printGridView(responseData,printPara);
    	});
    }
    function remove(){
    	
    	var data = gridManager.getCheckedRows();
    	
                    if (data.length == 0){
                    	$.ligerDialog.error('请选择行');
                    }else{
                        var ParamVo =[];
                        $(data).each(function (){					
							ParamVo.push(
							this.group_id   +"@"+ 
							this.hos_id   +"@"+ 
							this.copy_code   +"@"+ 
							this.dept_id   +"@"+ 
							this.disease_code 
							) });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteBudgDeptSingleDisease.do",{ParamVo : ParamVo.toString()},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
    	}
    function imp(){
    	
    	var index = layer.open({
					type : 2,
					title : '导入',
					shadeClose : false,
					shade : false,
					maxmin : true, //开启最大化最小化按钮
					area : [ '893px', '500px' ],
					content : 'budgDeptSingleDiseaseImportPage.do?isCheck=false'
				});
				layer.full(index);
    	}	
    function downTemplate(){
    	
    	location.href = "downTemplate.do?isCheck=false";
    	}	
   
    function openUpdate(obj){
    		
		var vo = obj.split("|");
		var parm = 
			"group_id="+vo[0]   +"&"+ 
			"hos_id="+vo[1]   +"&"+ 
			"copy_code="+vo[2]   +"&"+ 
			"dept_id="+vo[3]   +"&"+ 
			"disease_code="+vo[4] 
		 
		
		 $.ligerDialog.open({url:'budgDeptSingleDiseaseUpdatePage.do?isCheck=false&'+parm,data:{},height:300,width: 350,
			title:'科室单病种维护',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
			buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.updateBudgDeptSingleDisease(); },cls:'l-dialog-btn-highlight' },
			           { text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
			          ]
    	}); 
		 
   
    }
    function loadDict(){
            //字典下拉框
    	 autocomplete("#dept_id","../../../queryBudgDeptDict.do?isCheck=false&","id","text",true,true);
    	 autocomplete("#disease_code","../../../queryBudgSingleDisease.do?isCheck=false&","id","text",true,true);
            
         }  
    //键盘事件
	  function loadHotkeys() {
    	
	 }
 	  
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室名称：</td>
            <td align="left" class="l-table-edit-td"><input  name="dept_id" type="text" id="dept_id"  ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">病种名称：</td>
            <td align="left" class="l-table-edit-td"><input name="disease_code" type="text" id="disease_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
    </table>

	<div id="maingrid"></div>
	
</body>
</html>
