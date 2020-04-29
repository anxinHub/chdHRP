<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<link href="<%=path %>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="<%=path %>/lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<script src="<%=path %>/lib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script>
<script src="<%=path %>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path %>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>
<script src="<%=path%>/lib/My97DatePicker/WdatePicker.js"	type="text/javascript"></script>
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
        grid.options.parms.push({name:'proj_name',value:$('#proj_name').val()}); 
    	//grid.options.parms.push({name:'proj_id',value:liger.get("proj_id").getValue()}); 
    	grid.options.parms.push({name:'user_code',value:$('#user_code').val()}); 
    	//grid.options.parms.push({name:'user_id',value:liger.get("user_id").getValue()}); 
    	
    	grid.options.parms.push({
			name : 'begin_app_date',
			value : $("#begin_app_date").val()
		});
		grid.options.parms.push({
			name : 'end_app_date',
			value : $("#end_app_date").val()
		}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }
 
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '项目编码', name: 'proj_code', align: 'left',
                    	 render: function (rowdata, rowindex, value){
                             return "<a href='#' onclick=\"openUpdate('"+rowdata.group_id+"|"+ rowdata.hos_id+"|"+rowdata.proj_id+"|"+rowdata.proj_code+"');\" >"+rowdata.proj_code+"</a>";
 			           }
					 },
                     { display: '项目名称', name: 'proj_name', align: 'left'
					 },
                     { display: '项目简称', name: 'proj_simple', align: 'left'
					 },
					 { display: '项目类型', name: 'type_name', align: 'left'
					 },
					 { display: '项目负责人', name: 'con_emp_name', align: 'left'
					 },
					 { display: '财务负责人', name: 'acc_emp_name', align: 'left'
					 },
					 { display: '填报人', name: 'app_emp_name', align: 'left'
					 },
					 { display: '填报部门', name: 'dept_name', align: 'left'
					 },
					 { display: '填报日期', name: 'app_date', align: 'left'
					 },
					 { display: '项目用途', name: 'use_name', align: 'left'
					 },
					 { display: '项目级别', name: 'level_name', align: 'left'
					 },
					 { display: '备注', name: 'type_name', align: 'left'
					 },
                     { display: '是否停用', name: 'is_disable', align: 'left',
							render : function(rowdata, rowindex,
									value) {
								if(rowdata.is_disable == 0){
									return "启用";
								}else{ 
									return "停用"
								}
							}
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'../projdict/queryProjDictList.do?isCheck=false',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,enabledSort:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
    					{ text: '添加', id:'add', click: itemclick, icon:'add' },
    	                { line:true },
    	                { text: '删除', id:'delete', click: itemclick,icon:'delete' },
    	                { line:true },
    	                
    	                { text: '打印', id:'print', click: print,icon:'print' },
    	                { line:true },
    	                /* { text: '转换', id:'save', click: itemclick,icon:'save' },
    	                { line:true }, */
    	                { text: '下载导入模板', id:'downTemplate', click:downTemplate,icon:'down' },
		                { line:true },
		                { text: '导入', id:'import', click: imp,icon:'up' },
    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.proj_id   + "|" + 
								rowdata.proj_code 
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

              		$.ligerDialog.open({url: 'projAddPage.do?isCheck=false', height:$(window).height()*0.9,width: $(window).width()-400, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,
              		buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveProj(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
              		return;
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
							this.proj_id   +"@"+ 
							this.proj_code 
							)
                        });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteProj.do",{ParamVo : ParamVo},function (responseData){
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
    
	function imp(){
		$.ligerDialog.open({url: 'projImportPage.do?isCheck=false', height: 500,width: 800, title:'导入',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true });

    	}	
    function downTemplate(){
    	
    	location.href = "downTemplate.do?isCheck=false";
    	}	
    
    function openUpdate(obj){
    	
    	var vo = obj.split("|");
		var parm = "group_id="+vo[0]+"&hos_id="+ vo[1]+"&proj_id="+ vo[2]+"&proj_code="+ vo[3];
    	$.ligerDialog.open({ url : 'projUpdatePage.do?isCheck=false&' + parm,data:{},  height:$(window).height()*0.9,width: $(window).width()-400, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
    		buttons: [ { text: '保存', onclick: function (item, dialog) {dialog.frame.saveProj(); },cls:'l-dialog-btn-highlight' },
					{ text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
					]
	});

    }
	//打印
	function print(){
    	
    	if(grid.getData().length==0){
    		
			$.ligerDialog.error("请先查询数据！");
			
			return;
		}
    	
    	var selPara={};
    	
    	$.each(grid.options.parms,function(i,obj){
    		
    		selPara[obj.name]=obj.value;
    		
    	});
   		
		var dates = getCurrentDate();
    	
    	var cur_date = dates.split(";")[2];
    	//跨所有列:计算列数
    	var colspan_num = grid.getColumns(1).length-1;
    	
   		var printPara={
   			title:'项目维护',
   			head:[
				{"cell":0,"value":"单位: ${sessionScope.hos_name}","colspan":colspan_num,"br":true},
				/* {"cell":0,"value":"统计日期: " + $("#begin_in_date").val() +" 至  "+ $("#end_in_date").val(),"colspan":colspan_num,"br":true} */
   			],
   			foot:[
				{"cell":0,"value":"主管:","colspan":3,"br":false} ,
				{"cell":3,"value":"复核人:","colspan":colspan_num-5,"br":false},
				{"cell":colspan_num-2,"value":"制单人： ${sessionScope.user_name}","colspan":2,"br":true},
				{"cell":0,"value":"打印日期: " + cur_date,"colspan":colspan_num,"br":true}
   			],
   			columns:grid.getColumns(1),
   			headCount:1,//列头行数
   			autoFile:true,
   			type:3
   		};
   		ajaxJsonObjectByUrl("../projdict/queryProjDictList.do?isCheck=false", selPara, function (responseData) {
   			printGridView(responseData,printPara);
		});

   		
    }
    function loadDict(){
            //字典下拉框
    	/* autocomplete("#proj_id","../queryProjDict.do?isCheck=false","id","text",true,true); */
    	autocomplete("#user_id","../queryUserDict.do?isCheck=false","id","text",true,true);
    	autocomplete("#user_code","../queryUserDict.do?isCheck=false","id","text",true,true);
     	$("#proj_name").ligerTextBox({width:160});
     	$("#begin_app_date").ligerTextBox({width:100}); 
        $("#end_app_date").ligerTextBox({width:100});
       /*  autodate("#begin_in_date", "yyyy-mm-dd", "month_first");
        autodate("#end_in_date", "yyyy-mm-dd", "month_last"); */
    }   
    
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
          <!--   <td align="right" class="l-table-edit-td"  style="padding-left:20px;">项目：</td>
            <td align="left" class="l-table-edit-td"><input name="proj_id" type="text" id="proj_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td> -->
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">项目：</td>
            <td align="left" class="l-table-edit-td"><input name="proj_name" type="text" id="proj_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
          <!--   <td align="right" class="l-table-edit-td"  style="padding-left:20px;">填报人：</td>
            <td align="left" class="l-table-edit-td"><input name="user_id" type="text" id="user_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td> -->
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">项目负责人：</td>
            <td align="left" class="l-table-edit-td"><input name="user_code" type="text" id="user_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="right" class="l-table-edit-td"  width="10%">
            	编制日期：
            </td>
            <td align="left" class="l-table-edit-td"  width="20%">
				<table>
					<tr>
						<td align="left" >
							<input class="Wdate" name="begin_app_date" id="begin_app_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
						<td align="right" class="l-table-edit-td"  >
							至：
						</td>
						<td align="left" class="l-table-edit-td">
							<input class="Wdate" name="end_app_date" id="end_app_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
            		</tr>
				</table>
	        </td>
        </tr> 
    </table>

	<div id="maingrid"></div>

</body>
</html>
