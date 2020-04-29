<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">
	
    var grid;
    
    var gridManager = null;
    
    var userUpdateStr;
    
    $(function ()
    {
		loadDict();
    	
    	loadHead(null);	//加载数据
    	
    	loadHotkeys();
    	
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
        grid.options.parms.push({name:'emp_code',value:$("#emp_code").val()}); 
    	grid.options.parms.push({name:'proj_code',value:liger.get("proj_code").getValue()}); 
    	grid.options.parms.push({name:'is_stop',value:$("#is_stop").val()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '项目编码', name: 'proj_code', align: 'left',
							render : function(rowdata, rowindex,
									value) {
									return "<a href=javascript:openUpdate('"+rowdata.group_id   + "|" + 
									rowdata.hos_id   + "|" + 
									rowdata.proj_id    +"')>"+rowdata.proj_code+"</a>";
							}
					 },
                     { display: '项目名称', name: 'proj_name', align: 'left'
					 },
					 { display: '项目简称', name: 'proj_simple', align: 'left'
					 },
                     { display: '项目级别', name: 'level_name', align: 'left'
					 },
					 { display: '项目负责人', name: 'con_emp_name', align: 'left'
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'../../sys/projdict/queryProjDict.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '添加', id:'add', click: add_btn,icon:'add' },
                     	{ line:true },
                     	{ text: '删除', id:'delete', click: del_btn,icon:'delete' },
                     	{ line:true }
    				]}, 
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.proj_id 
							);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

    function add_btn(){
    	
    	parent.$.ligerDialog.open({url: '../../sys/proj/projAddPage.do?isCheck=false', height: 480,width: 930, title:'添加',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '保存', onclick: function (item, dialog) { dialog.frame.saveProj(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
    }
    
	function del_btn(){
    	
		var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.error('请选择行');
        }else{
            var ParamVo =[];
            $(data).each(function (){					
				ParamVo.push(
				//表的主键
				this.group_id   +"@"+ 
				this.hos_id   +"@"+ 
				this.proj_id   +"@"+ 
				this.proj_code 
				)
            });
            $.ligerDialog.confirm('确定删除?', function (yes){
            	if(yes){
                	ajaxJsonObjectByUrl("../../sys/proj/deleteProj.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
                		if(responseData.state=="true"){
                			query();
                		}
                	});
            	}
            }); 
        }
    }
    
    function openUpdate(obj){
		var vo = obj.split("|");
		var parm ="group_id="+vo[0]+"&hos_id="+ vo[1]+"&proj_id="+ vo[2];
		
		parent.$.ligerDialog.open({ url : 'accCheckProjUpdatePage.do?' + parm,data:{}, height: 400,width: 930, 
				title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
				parentframename:window.name,
				buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAccProjAttr(); },
				cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    }
    
  //键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
/* 
		hotkeys('A', add);
		hotkeys('D', remove);

		hotkeys('E', exportExcel);

		hotkeys('P', printDate);
		hotkeys('K', printBarcode); */

	}
  
    function loadDict(){
            //字典下拉框
    	
    	//autocomplete("#proj_code","../../sys/queryProjDict.do?isCheck=false","id","text",true,true,'',false,'',300);
    	//autocomplete("#emp_code","../../sys/queryEmp.do?isCheck=false","id","text",true,true);
    	$("#emp_code").ligerTextBox({width:160});
    	$("#proj_code").ligerTextBox({width:300});
    	$("#is_stop").ligerComboBox({width:120});
    }
    
    function printDate(){
		 if(grid.getData().length==0){
		
			$.ligerDialog.error("请先查询数据！");
			
			return;
		}
	var heads={
	      		  //"isAuto": true/false 默认true，页眉右上角默认显示页码
	      		  "rows": [
	      		  ]
	      	};
	   		
		var printPara={
			rowCount:1,
			title:'项目',
			columns: JSON.stringify(grid.getPrintColumns()),//表头
			class_name: "com.chd.hrp.sys.service.ProjDictService",
			method_name: "queryProjDictPrint",
			bean_name: "projDictService",
			heads: JSON.stringify(heads)//表头需要打印的查询条件,可以为空
			};
	
		//执行方法的查询条件
		$.each(grid.options.parms,function(i,obj){
			printPara[obj.name]=obj.value;
	});
		
	officeGridPrint(printPara); 
	
	}
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
        <tr>
        	 <td align="right" class="l-table-edit-td"  style="padding-left:20px;">项目：</td>
            <td align="left" class="l-table-edit-td"><input name="proj_code" type="text" id="proj_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">项目负责人：</td>
            <td align="left" class="l-table-edit-td"><input name="emp_code" type="text" id="emp_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否停用：</td>
                <td align="left" class="l-table-edit-td">
              		   <select id="is_stop" name="is_stop" style="width: 135px;">
			                		<option value="0">启用</option>
			                		<option value="1">停用</option>
                	</select>
                </td>
                <td align="left"></td>
             <td>
            	<input class="l-button l-button-test"  type="button" value="查询(Q)" onclick="query();"/>
            	<input class="l-button l-button-test"  type="button" value="打 印" onclick="printDate();"/>
            </td>
        </tr> 
    </table>

	<div id="maingrid"></div>

</body>
</html>
