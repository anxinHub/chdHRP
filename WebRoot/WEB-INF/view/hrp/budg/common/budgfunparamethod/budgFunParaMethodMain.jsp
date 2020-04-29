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
		
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	  grid.options.parms.push({name:'para_code',value:$("#para_code").val()}); 
    	  grid.options.parms.push({name:'para_name',value:$("#para_name").val()}); 
    	  grid.options.parms.push({name:'is_stop',value:$("#is_stop").val()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '参数代码', name: 'para_code', align: 'left',width:130,
                    	 render:function(rowdata, rowindex,value){
                    		 return "<a href=javascript:openUpdate('"+rowdata.group_id+"|"+ rowdata.hos_id+"|"+rowdata.copy_code+"|"+rowdata.para_code+"')>"+rowdata.para_code+"</a>";
                    	 }
					 		},
                     { display: '参数名称', name: 'para_name', align: 'left',width:100},
                     { display: '是否停用', name: 'stop', align: 'left',width:70},
                     { display: '取值方法', name: 'para_sql', align: 'left'}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryBudgFunParaMethod.do?isCheck=false',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询（<u>E</u>）', id:'search', click: query,icon:'search' },
                     	{ line:true },
    					{ text: '添加（<u>A</u>）', id:'add', click: add_open, icon:'add' },
    	                { line:true },
    	                { text: '删除（<u>D</u>）', id:'delete', click: remove,icon:'delete' },
						{ line:true }, 
		                { text: '打印（<u>P</u>）', id:'print', click: printDate,icon:'print' },
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
								rowdata.para_code 
							);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function add_open(){
    	 $.ligerDialog.open({url: 'budgFunParaMethodAddPage.do?isCheck=false', 
 			height: 500,width: 600,title:'函数参数取值添加',modal:true,showToggle:false,
 			showMax:false,showMin: true,isResize:true,
 			buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveFunParaMethod(); },
 				cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
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
								this.para_code 
								) 
						});
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteBudgFunParaMethod.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
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
					title : '函数参数取值',
					shadeClose : false,
					shade : false,
					maxmin : true, //开启最大化最小化按钮
					area : [ '893px', '500px' ],
					content : 'budgFunParaMethodImportPage.do?isCheck=false'
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
			"para_code="+vo[3] 
		$.ligerDialog.open({url: 'budgFunParaMethodUpdatePage.do?isCheck=false&'+parm, 
 			height: 500,width: 600,title:'函数参数取值修改',modal:true,showToggle:false,
 			showMax:false,showMin: true,isResize:true,
 			buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveFunParaMethod(); },
 				cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
    }
    function loadDict(){
            //字典下拉框
	    	$("#para_code").ligerTextBox({width:160});
	        $("#para_name").ligerTextBox({width:160});
	        $("#is_stop").ligerTextBox({width:160});
         }  
    //键盘事件
	  function loadHotkeys() {

		hotkeys('Q', query);

		hotkeys('A', add_open);
		hotkeys('D', remove);

		hotkeys('B', downTemplate);

		hotkeys('P', printDate);
		hotkeys('I', imp);
		

	 }
  //打印数据
	 function printDate(){
		 if(grid.getData().length==0){
				$.ligerDialog.error("无打印数据！");
				return;
			}
		    
	    	grid.options.parms=[];
			
	    	grid.options.parms.push({name:'para_code',value:$("#para_code").val()}); 
	    	grid.options.parms.push({name:'para_name',value:$("#para_name").val()}); 
	    	grid.options.parms.push({name:'is_stop',value:$("#is_stop").val()}); 
	        var selPara={};
	    	$.each(grid.options.parms,function(i,obj){
	    		selPara[obj.name]=obj.value;
	    	});
	   		var printPara={
	   			headCount:2,
	   			title:"函数参数取值",
	   			type:3,
	   			columns:grid.getColumns(1)
	   		};
	   		ajaxJsonObjectByUrl("queryBudgFunParaMethod.do?isCheck=false", selPara, function(responseData) {
	   			printGridView(responseData,printPara);
	    	});
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
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">参数代码：</td>
            <td align="left" class="l-table-edit-td"><input name="para_code" type="text" id="para_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">参数名称：</td>
            <td align="left" class="l-table-edit-td"><input name="para_name" type="text" id="para_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否停用：</td>
            <td align="left" class="l-table-edit-td">
            	<select id="is_stop" name="is_stop" style="width: 135px;" >
	                	<option value="">请选择</option>
	                	<option value="0">否</option>
	                	<option value="1">是</option>	
              	</select>
            </td>
            <td align="left"></td>
        </tr> 
    </table>

	<div id="maingrid"></div>
	
</body>
</html>
