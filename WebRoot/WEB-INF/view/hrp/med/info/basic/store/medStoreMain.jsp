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
<script src="<%=path%>/lib/hrp/med/med.js"	type="text/javascript"></script>
<script type="text/javascript">
	
    var grid;
    
    var gridManager = null;
    
    var userUpdateStr;
    
    $(function ()
    {
		loadDict();
    	
    	loadHead(null);	//加载数据
    	$("#store_code").ligerTextBox({width:160});
        $("#store_name").ligerTextBox({width:160});
       
    });
    //查询
    function  query(){
    	grid.options.parms=[];
    	grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'store_code',value:liger.get("store_code").getValue()}); 
    	grid.options.parms.push({name:'store_name',value:liger.get("store_name").getValue()}); 
    	//加载查询条件
    	grid.loadData(grid.where);
     }
    //获取查询条件的数值
    function f_getWhere(){
    	if (!grid) return null;
        var clause = function (rowdata, rowindex){
                	if($("#store_code").val()!=""){
                		return rowdata.store_code.indexOf($("#store_code").val()) > -1;	
                	}
                	if($("#store_name").val()!=""){
                		return rowdata.store_name.indexOf($("#store_name").val()) > -1;	
                	}
        };
        return clause; 
    }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ { display: '库房ID', name: 'store_id', hide : true },
                     { display: '仓库编码', name: 'store_code', align: 'left',width :'22%',
						render : function(rowdata, rowindex,value) {
								return "<a href=javascript:openUpdate('"+rowdata.group_id+"|"+ rowdata.hos_id+"|"+rowdata.store_id+"|"+rowdata.store_code+"')>"+rowdata.store_code+"</a>";
						}
					 },
                     { display: '仓库名称', name: 'store_name', align: 'left',width :'25%'},
					 { display: '主管部门', name: 'dept_name', align: 'left',width :'25%' },
                     { display: '是否停用', name: 'is_stop', align: 'left',width :'25%',
							render : function(rowdata, rowindex,value) {
								if(rowdata.is_stop == 0){
									return "否";
								}else{
									return "是"
								}
							}
					 }
                    ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryMedStore.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
    	                { text: '删除附属信息', id:'delete', click: itemclick,icon:'delete' },
    	                { line:true }
    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.store_id   + "|" + 
								rowdata.store_code 
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
                case "modify":
                    return;
                case "delete":
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
							this.store_id   
							)
                        });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteMedStore.do",{ParamVo : ParamVo.toString()},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
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
		
		var parm = 
			"store_id="+vo[2];
		
    	$.ligerDialog.open({ url : 'medStoreUpdatePage.do?isCheck=false&' + parm,data:{}, height: 500,width: 800, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
    			buttons: [ 
    			           { text: '保存', onclick: function (item, dialog) { dialog.frame.saveStore(); },cls:'l-dialog-btn-highlight' }, 
    			           { text: '关闭', onclick: function (item, dialog) { dialog.close(); } } 
    			           ] 
    	});

    }
    function loadDict(){
        //字典下拉框
    	//autocomplete("#store_code","../queryStoreDict.do?isCheck=false","id","text",true,true);
    }   
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">库房编码：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="store_code" type="text" id="store_code" ltype="text"  />
            </td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">库房名称：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="store_name" type="text" id="store_name" ltype="text"  />
            </td>
            <td align="left"></td>
        </tr> 
	
    </table>

	<div id="maingrid"></div>

</body>
</html>
