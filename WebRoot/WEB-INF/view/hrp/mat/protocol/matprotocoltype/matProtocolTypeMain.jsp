<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc.jsp" />
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
    	  grid.options.parms.push({name:'type_code',value:$("#type_code").val()}); 
    	  grid.options.parms.push({name:'type_name',value:$("#type_name").val()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '类别代码', name: 'type_code', align: 'left',
                    	 render:function(rowdata,index,value){
                    		 return "<a href=javascript:openUpdate('"+rowdata.group_id + "|" + rowdata.hos_id + "|" + 
								rowdata.copy_code + "|" + rowdata.type_id+"')>"+rowdata.type_code+"</a>";
                    	 		}
					 		},
                     { display: '类别名称', name: 'type_name', align: 'left'
					 		},
                     { display: '协议前缀', name: 'pre', align: 'left'
					 		},
                     { display: '预警天数', name: 'war_days', align: 'left'
					 		},
                     { display: '是否停用', name: 'is_stop', align: 'left',
					 			render: function(rowdata,index,value){
					 				if(rowdata.is_stop == 0){
					 					return "在用";
					 				}else{
					 					return "停用";
					 				}
					 			}
					 		}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatProtocolType.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' },
                     	{ line:true },
    					{ text: '添加（<u>A</u>）', id:'add', click: add_open, icon:'add' },
    	                { line:true },
    	                { text: '删除（<u>D</u>）', id:'delete', click: remove,icon:'delete' },
    	                { line:true },
    	                /* { text: '导出Excel（<u>E</u>）', id:'export', click: exportExcel,icon:'pager' },
		                { line:true },
		                { text: '打印（<u>P</u>）', id:'print', click: printDate,icon:'print' }, 
		                { line:true },*/
		                { text: '下载导入模板（<u>B</u>）', id:'downTemplate', click:downTemplate,icon:'down' },
		                { line:true },
		                { text: '导入（<u>I</u>）', id:'import', click: imp,icon:'up' }, 
    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code   + "|" + 
								rowdata.type_id 
							);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function add_open(){
    	$.ligerDialog.open({ url : 'matProtocolTypeAddPage.do?isCheck=false',data:{}, 
			height: 300,width: 320, title:'添加',modal:true,showToggle:false,showMax:false,
			showMin: false,isResize:true,
			buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveMatProtocolType(); },
			cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); }}
			]});
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
								this.type_id 
								) });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteMatProtocolType.do",{ParamVo : ParamVo.toString()},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
    	}
   function imp(){
    	$.ligerDialog.open({ url : 'matProtocolTypeImportPage.do?isCheck=false',data:{}, 
			height: 480,width: 1000, title:'导入',modal:true,showToggle:false,showMax:false,
			showMin: false,isResize:true});
   }	 
   function downTemplate(){
    	
    	location.href = "downTemplate.do?isCheck=false";
    }	 
   
    function  openUpdate(obj){
    		
		var vo = obj.split("|");
		var parm = 
			"group_id="+vo[0]   +"&"+ 
			"hos_id="+vo[1]   +"&"+ 
			"copy_code="+vo[2]   +"&"+ 
			"type_id="+vo[3] ;
		$.ligerDialog.open({ url : 'matProtocolTypeUpdatePage.do?isCheck=false&'+parm,data:{}, 
			height: 300,width: 320, title:'修改',modal:true,showToggle:false,showMax:false,
			showMin: false,isResize:true,
			buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveMatProtocolType(); },
			cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); }}
			]});
    }
    function loadDict(){
          /*   //字典下拉框
	    	$("#dept_id").ligerComboBox({
	           	url: '../queryMatDeptIsManager.do?isCheck=false',
	           	valueField: 'id',
	            textField: 'text', 
	            selectBoxWidth: 160,
	           	autocomplete: true,
	           	initValue : 0,
	           	width: 160
	  		  }); */
	    	$("#type_code").ligerTextBox({width:160});
	        $("#type_name").ligerTextBox({width:160});
	        $("#dept_id").ligerTextBox({width:160});
         }  
    //键盘事件
	  function loadHotkeys() {

		hotkeys('Q', query);

		hotkeys('A', add);
		hotkeys('D', remove);

		//hotkeys('E', exportExcel);

		

	 }

    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">类别代码：</td>
            <td align="left" class="l-table-edit-td"><input name="type_code" type="text" id="type_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">类别名称：</td>
            <td align="left" class="l-table-edit-td"><input name="type_name" type="text" id="type_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
    </table>

	<div id="maingrid"></div>
</body>
</html>
