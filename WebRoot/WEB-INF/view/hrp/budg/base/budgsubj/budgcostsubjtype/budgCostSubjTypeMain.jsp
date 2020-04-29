<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
    <script src="<%=path%>/lib/hrp/mat/mat.js"	type="text/javascript"></script>
<script type="text/javascript">
	
    var grid;
    
    var gridManager = null;
    
    var userUpdateStr;
    
    $(function ()
    {
		loadDict();
    	
    	loadHead(null);	//加载数据
    	
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'cost_subj_type_code',value:$("#cost_subj_type_code").val()});
        grid.options.parms.push({name:'is_stop',value:liger.get("is_stop").getValue() == null ? "" : liger.get("is_stop").getValue()});

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '科目类别编码', name: 'cost_subj_type_code', align: 'center' ,
                    	 render : function(rowdata, rowindex, value){
                    		 return "<a href=javascript:openUpdate('"+rowdata.group_id+"|"+ rowdata.hos_id+"|"+rowdata.copy_code+"|"+rowdata.cost_subj_type_code+"')>"+rowdata.cost_subj_type_code+"</a>";
                    	 }
					 },
                     { display: '科目类别名称', name: 'cost_subj_type_name', align: 'center'},
                     { display: '是否停用', name: 'is_stop', align: 'center' ,
	                    	 render : function(rowdata, rowindex, value){
	                    		 if(rowdata.is_stop == 0){
	                    			 return "否";
	                    		 }else{
	                    			 return "是";
	                    		 }
	                    	 }
                    	 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryBudgCostSubjType.do?isCheck=false',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
    					{ text: '添加', id:'add', click: itemclick, icon:'add' },
    	                { line:true },
    	                { text: '删除', id:'delete', click: itemclick,icon:'delete' },
    	                { line:true }
    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code   + "|" + 
								rowdata.cost_subj_type_code  
							);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

    function itemclick(item){ 
        if(item.id)
        {
            switch (item.id)
            {
                case "add":
              		$.ligerDialog.open({url: 'budgCostSubjTypeAddPage.do?isCheck=false', height: 260,width: 360, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveBudgAccSubjNature(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
              		return;
                case "modify":
                    return;
                case "delete":
                    var data = gridManager.getCheckedRows();
                    if (data.length == 0){
                    	$.ligerDialog.error('请选择行');
                    }else{
                        var ParamVo =[];
                        var str = "";
                        $(data).each(function (){	
                        	if(this.is_fixed == 1){
                        		str += this.cost_subj_type_code +" "+this.cost_subj_type_name +";";
                        	}else{
                        		ParamVo.push(
       							//表的主键
       							this.group_id   +"@"+ 
       							this.hos_id   +"@"+ 
       							this.copy_code +"@"+
       							this.cost_subj_type_code 
       							)
                        	}
							
                        });
                        if(str != ""){
                       		$.ligerDialog.error('科目类别:'+str+'为内置数据，不允许删除！');
                       	}else{
                    		$.ligerDialog.confirm('确定删除?', function (yes){
		                      	if(yes){
		                          	ajaxJsonObjectByUrl("deleteBudgCostSubjType.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
		                          		if(responseData.state=="true"){
		                          			query();
		                          		}
		                          	});
		                      	}
	                      	}); 
                       	}
                    }
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
    	
		var vo = obj.split("|");
		var parm = "group_id="+vo[0]+"&hos_id="+ vo[1]+"&copy_code="+ vo[2]+"&cost_subj_type_code="+ vo[3];
		
    	$.ligerDialog.open({ url :'budgCostSubjTypeUpdatePage.do?isCheck=false&' + parm,data:{}, height: 260,width: 360, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.updateBudgAccSubjNature(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    }
    function loadDict(){
            //字典下拉框
	    	autoCompleteByData("#is_stop", yes_or_no.Rows, "id", "text", true, true);
	        $("#cost_subj_type_code").ligerTextBox({width:160});
	        $("#is_stop").ligerTextBox({width:160});
         }   
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">支出科目类别：</td>
            <td align="left" class="l-table-edit-td"><input name="cost_subj_type_code" type="text" id="cost_subj_type_code" ltype="text" validate="{maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否停用：</td>
            <td align="left" class="l-table-edit-td"><input name="is_stop" type="text" id="is_stop" ltype="text" validate="{maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
    </table>

	<div id="maingrid"></div>

</body>
</html>
