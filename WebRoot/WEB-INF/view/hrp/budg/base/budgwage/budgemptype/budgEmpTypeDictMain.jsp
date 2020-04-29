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
		
		$("#type_code").change(function(){
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
	   	grid.options.parms.push({name:'type_code',value:$("#type_code").val()}); 
	   	grid.options.parms.push({name:'is_stop',value:liger.get("is_stop").getValue()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '类别编码', name: 'type_code', align: 'left',
	                    	render : function(rowdata, rowindex,value) {
								return "<a href=javascript:openUpdate('"+rowdata.group_id+"|"+ rowdata.hos_id+"|"+rowdata.copy_code+"|"+rowdata.type_code+"')>"+rowdata.type_code+"</a>";
							}
					 	},
                     { display: '类别名称', name: 'type_name', align: 'left'
					 		},
                     { display: '是否停用', name: 'stop', align: 'left'
					 		},
                     ],
                     dataAction: 'server',dataType: 'server',usePager:false,url:'queryBudgEmpTypeDict.do?isCheck=false',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询（<u>E</u>）', id:'search', click: query,icon:'search' },
                     	{ line:true },
    					{ text: '添加（<u>A</u>）', id:'add', click: add_open, icon:'add' },
    	                { line:true },
    	                { text: '删除（<u>D</u>）', id:'delete', click: remove,icon:'delete' },
		                { line:true }/* ,
		                { text: '导入（<u>I</u>）', id:'import', click: impNew,icon:'up' } */
    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code   + "|" + 
								rowdata.type_code 
							);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function add_open(){
    	
		$.ligerDialog.open({ url : 'budgEmpTypeDictAddPage.do?isCheck=false',data:{}, height: 300,width: 400, 
				title:'添加职工类别字典',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
				buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveBudgTitleDict(); },
					cls:'l-dialog-btn-highlight' },{ text: '取消', onclick: function (item, dialog) { dialog.close();}}]
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
				this.type_code 
				) });
            $.ligerDialog.confirm('确定删除?', function (yes){
          	  if(yes){
              	ajaxJsonObjectByUrl("deleteBudgEmpTypeDict.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
              		if(responseData.state=="true"){
              			query();
              		}
              	});
          	  }
           }); 
       }
   	}
    
  	//导入
    function impNew(){
	    parent.$.ligerDialog.open({ url : 'hrp/budg/base/budgwage/budgemptype/importBudgEmpTypeDictPage.do?isCheck=false',
			data:{columns : grid.columns, grid : grid}, height: 300,width: 450,title:'职工类别导入',modal:true,showToggle:false,showMax:true,
			showMin: false,isResize:true,parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
 		}); 
	}	
    function openUpdate(obj){
    		
		var vo = obj.split("|");
		var parm = "group_id="+vo[0]   +"&hos_id="+vo[1]   +"&copy_code="+vo[2] +"&type_code="+vo[3] 
		 
		 $.ligerDialog.open({ url : 'budgEmpTypeDictUpdatePage.do?isCheck=false'+ parm ,data:{}, height: 300,width: 400,
				title:'修改职工类别字典',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
				buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveBudgTitleDict(); },
					cls:'l-dialog-btn-highlight' },{ text: '取消', onclick: function (item, dialog) { dialog.close();}}]
    	}); 
    }
    
    function loadDict(){
       //字典下拉框
       autoCompleteByData("#is_stop", yes_or_no.Rows, "id", "text", true, true);
       $("#type_code").ligerTextBox({width:160});
    }  
    //键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
		hotkeys('A', add_open);
		hotkeys('D', remove);
		hotkeys('I', impNew);
	}
    
</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">职工类别:</td>
            <td align="left" class="l-table-edit-td"><input name="type_code" type="text" id="type_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否停用：</td>
            <td align="left" class="l-table-edit-td"><input name="is_stop" type="text" id="is_stop" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
        </tr> 
    </table>

	<div id="maingrid"></div>
	
</body>
</html>
