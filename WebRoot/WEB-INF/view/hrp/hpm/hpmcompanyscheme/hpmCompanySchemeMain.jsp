<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"  xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">

    var grid;
    
    var gridManager = null;
    
    var userUpdateStr;
    var app_mod_code = '${app_mod_code}';
    $(function ()
    {	
		autocomplete("#item_code","../../queryItemAllDict.do?isCheck=false","id","text",true,true);
    	
    	autocomplete("#formula_code","../../queryFormula.do?isCheck=false","id","text",true,true);
    	
    	loadHead(null);	
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'item_code',value:liger.get("item_code").getValue()}); 
    	grid.options.parms.push({name:'formula_code',value:liger.get("formula_code").getValue()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }
  

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '奖金项目编码', name: 'item_code', align: 'left',
                    	 render: function (rowdata, rowindex, value){
							return "<a href='#' onclick=\"openUpdate('"+rowdata.item_code+"');\" >"+rowdata.item_code+"</a>";
			           }
					 },
					 { display: '奖金项目名称', name: 'item_name', align: 'left'},                 
                     { display: '公式名称', name: 'formula_name', align: 'left',
	                    	render: function (rowdata, rowindex, value){
	 							return "<a href='#' onclick=\"openFormulaUpdate('"+rowdata.formula_code+"');\" >"+rowdata.formula_name+"</a>";
	 			           	}
					 },
                     { display: '计算公式', name: 'fun_name', align: 'left'}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryHpmCompanyScheme.do',
                     width: '100%',height: '100%',   checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
    					{ text: '添加', id:'add', click: itemclick, icon:'add' },
    	                { line:true },
    	                { text: '删除', id:'delete', click: itemclick,icon:'delete' },
    	                { line:true },
    	                { text: '下载导入模板', id:'downTemplate', click:itemclick,icon:'down' },
		                { line:true },
    	                { text: '导入', id:'import', click: itemclick,icon:'up' },
    	                { line:true }
    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
    					openUpdate(rowdata.item_code);//实际代码中temp替换主键
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
        formatTrueFalse();
    }
    
    function openFormulaUpdate(obj){
    	//实际代码中&temp替换主键
    	parent.$.ligerDialog.open({ 
    		url: 'hpm/formula/hpmFormulaUpdatePage.do?isCheck=false&formula_code='+obj,data:{}, 
    		height:$(window).height(),width: $(window).width(), title:'修改',modal:true,showToggle:false,
    		showMax:true,showMin: false,isResize:true,
    		parentframename: window.name,
    		buttons: [ { 
    			text: '确定', onclick: 
    				function (item, dialog) { 
    					dialog.frame.saveFormula(); 
    				},cls:'l-dialog-btn-highlight' 
    			}, 
    			{ text: '取消', onclick: 
    				function (item, dialog) { 
    					dialog.close(); 
    				} 
    			} 
    		] 
    	});
    }

    function itemclick(item){ 
        if(item.id)
        {
            switch (item.id)
            {
                case "add":
              		$.ligerDialog.open({url: 'hpmCompanySchemeAddPage.do?isCheck=false&app_mod_code='+app_mod_code, height: 500,width: 900,title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveCompanyScheme(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
              		return;
                case "modify":
                    return;
                case "delete":
                    var data = gridManager.getCheckedRows();
                    if (data.length == 0){
                    	$.ligerDialog.error('请选择行');
                    }else{
                        var checkIds =[];
                        $(data).each(function (){
                        	checkIds.push(this.item_code);//实际代码中temp替换主键
                        });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteHpmCompanyScheme.do",{checkIds:checkIds.toString()},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
                    return;
                case "downTemplate":location.href = "downTemplateCompanyScheme.do?isCheck=false";return;

                case "import":
                   	$.ligerDialog.open({url: 'companySchemeImportPage.do?isCheck=false', height: 430,width: 760, isResize:true, title:'导入'});
                   	return;
                case "Excel":
                case "Word":
                case "PDF":
                case "TXT":
                case "XML":
                    $.ligerDialog.waitting('导出中，请稍候...');
                    setTimeout(function ()
                    {
                        $.ligerDialog.closeWaitting();
                        if (item.id == "Excel")
                            $.ligerDialog.success('导出成功');
                        else
                            $.ligerDialog.error('导出失败');
                    }, 1000);
                    return;
            }   
        }
        
    }
    function openUpdate(obj){
    	//实际代码中&temp替换主键
    	$.ligerDialog.open({ 
    		url: 'hpmCompanySchemeUpdatePage.do?isCheck=false&item_code='+obj,data:{}, 
    		height: $(window).height(),width: $(window).width(), title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
    		buttons: [ 
    			{ text: '确定', onclick: 
    				function (item, dialog) { 
    					dialog.frame.saveCompanyScheme(); 
    				},
    				cls:'l-dialog-btn-highlight' 
    			}, 
    			{ text: '取消', onclick: 
    				function (item, dialog) { 
    					dialog.close(); 
    				} 
    			} 
    		] 
    	});
    }
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">

		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">奖金项目编码：</td>
			<td align="left" class="l-table-edit-td"><input name="item_code" type="text" id="item_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">公式名称：</td>
			<td align="left" class="l-table-edit-td"><input name="formula_code" type="text" id="formula_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
		</tr>

	</table>

	<div id="maingrid"></div>

</body>
</html>
