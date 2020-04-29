<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">
	
    var grid;
    
    var rightgrid;
    
    var gridManager = null;
    
    var rightgridManager = null;
    
    var userUpdateStr;
    
    var para;
    
    var renderFunc = {
    		is_stop:function(value){//是否停用
				 if(value==1){
					 return "是";
				 }else if (value == 0){
					 return "否";
				 }
			}
    }
    
    $(function ()
    {
    	loadHead(null);	//加载数据
    	$("#kind_code").ligerTextBox({width:160});
    	$("#kind_name").ligerTextBox({width:160});
    	$("#is_stop").ligerTextBox({width:160});
    	
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'kind_code',value:$("#kind_code").val()}); 
    	grid.options.parms.push({name:'kind_name',value:$("#kind_name").val()}); 
    	grid.options.parms.push({name:'is_stop',value:liger.get("is_stop").getValue()}); 
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '分类编码', name: 'kind_code', align: 'left',
                    	 render:function(rowdata){
                    		 return "<a href=javascript:openUpdate('"+rowdata.kind_code+"|"+rowdata.group_id+"|"+rowdata.hos_id+"')>"+rowdata.kind_code+"</a>";
                    	 }
					 },
                     { display: '分类名称', name: 'kind_name', align: 'left'
					 },
					 { display: '拼音码', name: 'spell_code', align: 'left'
					 },
					 { display: '五笔码', name: 'wbx_code', align: 'left'
					 },
					 { display: '是否停用', name: 'is_stop', align: 'left',render:function(rowdata){
						 
						 if( rowdata.is_stop == "0"){
							 return "否";
						 }
						 return "是";
					 }
					 },
					 { display: '备注', name: 'note', align: 'left'
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAccHosEmpKind.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
						{ text: '查询', id:'search', click: query,icon:'search' },
						{ line:true },
                     	{ text: '添加', id:'add', click: itemclick,icon:'add' },
                     	{ line:true },
                     	{ text: '删除', id:'delete', click: itemclick,icon:'delete' },
                     	{ line:true },
                     	{ text: '打印', id:'print', click: print,icon:'print' },
                     	{ line:true }
    				]}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
        grid.options.lodop.fn=renderFunc;
       
    }
    
    function itemclick(item){ 
        if(item.id)
        {
            switch (item.id)
            {
                case "add":
              		$.ligerDialog.open({url: 'accHosEmpKindAddPage.do?isCheck=false', height: 300,width: 400, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.save(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
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
							this.kind_code+"@"+ 
							this.group_id+"@"+ 
							this.hos_id
							)
                        });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteAccHosEmpKind.do",{ParamVo : ParamVo.toString()},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
                    return;
            }   
        }
        
    }
    
    
    function openUpdate(obj,group_id,hos_id){
    	
    	var vo = obj.split("|");
		var parm = "kind_code="+
			vo[0]   +"&group_id="+ 
			vo[1]   +"&hos_id="+ 
			vo[2];

    	$.ligerDialog.open({ url : 'accHosEmpKindUpdatePage.do?isCheck=false&' + parm,data:{}, height: 500,width: 500, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveHosEmpKind(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    }
    
    function print(){
    	
    	if(grid.getData().length==0){
			$.ligerDialog.error("请先查询数据！");
			return;
		}
           	
    	/* var heads={
		  //"isAuto": true/false 默认true，页眉右上角默认显示页码
		  "rows": [
      {"cell":0,"value":"科室名称："+liger.get("dept_id").getText(),"colSpan":"5"},
      {"cell":3,"value":"项目名称："+liger.get("proj_id").getText(),"from":"right","align":"right","colSpan":"4"}
		  ]
		}; */
 	
		var printPara={
				title: "职工分类",//标题
				columns: JSON.stringify(grid.getPrintColumns()),//表头
				class_name: "com.chd.hrp.acc.service.emp.AccHosEmpKindService",
				method_name: "queryAccHosEmpKindPrint",
				bean_name: "accHosEmpKindService"/* ,
				heads: JSON.stringify(heads) *///表头需要打印的查询条件,可以为空
				/* foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 */
				};
		
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
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">分类编码：</td>
            <td align="left" class="l-table-edit-td"><input name="kind_code" type="text" id="kind_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">分类名称：</td>
            <td align="left" class="l-table-edit-td"><input name="kind_name" type="text" id="kind_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否停用：</td>
            <td align="left" class="l-table-edit-td">
            <select id="is_stop" name="is_stop">
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
