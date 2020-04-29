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
<script src="<%=path%>/lib/hrp/budg/budg.js"	type="text/javascript"></script>
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
      
        $("#index_name").change(function(){
        	query();
        })
		
        $("#is_carried").change(function(){
        	query();
        })
        $("#is_stop").change(function(){
        	query();
        })
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	
    	  grid.options.parms.push({name:'index_name',value:$("#index_name").val()}); 
    	  grid.options.parms.push({
    			name : 'is_carried',
    			value : liger.get("is_carried").getValue() == null ? "" : liger.get("is_carried").getValue()
    		});
    	  grid.options.parms.push({
  			name : 'is_stop',
  			value : liger.get("is_stop").getValue() == null ? "" : liger.get("is_stop").getValue()
  		});
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '指标编码', name: 'index_code', align: 'left',
                      	  render:function(rowdata,rowindex,value){
                    		 return "<a href=javascript:openUpdate('"+rowdata.group_id+"|"+ rowdata.hos_id+"|"+rowdata.copy_code+"|"+rowdata.index_code+"')>"+rowdata.index_code+"</a>";
                      	}
                      	 },
                     { display: '指标名称', name: 'index_name', align: 'left'
					 		},
                     { display: '计量单位', name: 'unit', align: 'left'
					 		},
                     { display: '数据精度', name: 'data_precision', align: 'left'
					 		},
                     { display: '数据性质', name: 'data_name', align: 'left'
					 		},
                     { display: '是否结转', name: 'carried', align: 'left'
					 		},
                     { display: '是否停用', name: 'stop', align: 'left'
					 		},
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryBudgIndexDict.do?isCheck=false&',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询（<u>E</u>）', id:'search', click: query,icon:'search' },
                     	{ line:true },
    					{ text: '添加（<u>A</u>）', id:'add', click: add_open, icon:'add' },
    	                { line:true },
    	                { text: '对应科室维护', id:'dept_add ', click: dept_add, icon:'add' },
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
								rowdata.index_code 
							);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function add_open(){
   
		$.ligerDialog.open({ url : 'budgIndexDictAddPage.do?isCheck=false&',data:{}, height: 350,width: 650, 
			title : '预算指标添加',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,      
			buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.savebudgIndexDict(); },cls:'l-dialog-btn-highlight' },
			           { text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
			          ]
    	}); 
    	}  
     //对应科室维护
   	 function dept_add(){
   		 $.ligerDialog.open({ url : 'budgIndexDeptSetPage.do?isCheck=false&',data:{}, height: 400,width: 600, 
   			title : '对应科室维护',modal:true,showToggle:false,showMax:true,showMin: false,isResize:true,   });    
   			
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
							this.index_code 
							) });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteBudgIndexDict.do",{ParamVo : ParamVo.toString()},function (responseData){
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
					content : 'budgIndexDictImportPage.do?isCheck=false'
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
			"index_code="+vo[3] 
		                                                                                 
		 $.ligerDialog.open({ url : 'budgIndexDictUpdatePage.do?isCheck=false&'+parm,data:{}, height: 350,width: 650, 
			 title : '预算指标修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
			 buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.updateBudgIndexDict(); },cls:'l-dialog-btn-highlight' },
				           { text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
			          ]
    	}); 

    }
    
  //打印回调方法
    function lodopPrint(){
    	var head="";
 		grid.options.lodop.head=head; 
 		/* grid.options.lodop.fn=renderFunc; */
 		grid.options.lodop.title="预算指标字典";
    }
    
    function loadDict(){
            //字典下拉框
    	autoCompleteByData("#is_stop", yes_or_no.Rows, "id", "text", true, true);
    	autoCompleteByData("#is_carried", yes_or_no.Rows, "id", "text", true, true);
    	
    	$("#index_name").ligerTextBox({width:160});
        $("#is_carried").ligerTextBox({width:160});
        $("#is_stop").ligerTextBox({width:160});
     }  
    //键盘事件
	  function loadHotkeys() {

	/* 	hotkeys('Q', query);

		hotkeys('A', add);
		hotkeys('D', remove);

		hotkeys('B', downTemplate);

		//s l hotkeys('E', exportExcel);

		hotkeys('P', printDate);
		hotkeys('I', imp);
		 */

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
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">指标名称：</td>
            <td align="left" class="l-table-edit-td"><input name="index_name" type="text" id="index_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
         <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否停用：</td>
            <td align="left" class="l-table-edit-td"><input name="is_stop" type="text" id="is_stop" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否结转：</td>
            <td align="left" class="l-table-edit-td"><input name="is_carried" type="text" id="is_carried" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
    </table>

	<div id="maingrid"></div>
	
</body>
</html>
