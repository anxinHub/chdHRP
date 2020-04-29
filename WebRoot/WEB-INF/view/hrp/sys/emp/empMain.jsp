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
    	grid.options.parms.push({name:'emp_code',value:$("#emp_code").val()});
    	grid.options.parms.push({name:'note',value:$("#note").val()});
    	grid.options.parms.push({name:'is_disable',value:liger.get("is_disable").getValue()});
        if(liger.get("dept_no").getValue() != null && liger.get("dept_no").getValue() != ''){
    		grid.options.parms.push({name:'dept_no',value:liger.get("dept_no").getValue().split(".")[1]}); 
        }

        if(liger.get("emp_kind").getValue() != null && liger.get("emp_kind").getValue() != ''){
    		grid.options.parms.push({name:'kind_code',value:liger.get("emp_kind").getValue()}); 
        }
    	//加载查询条件
    	grid.loadData(grid.where);
     }
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     
                     { display: '职工编码', name: 'emp_code', align: 'left',
						render : function(rowdata, rowindex,
								value) {
								return "<a href=javascript:openUpdate('"+rowdata.group_id+"|"+ rowdata.hos_id+"|"+rowdata.emp_id+"|"+rowdata.emp_code+"|"+rowdata.emp_no+"')>"+rowdata.emp_code+"</a>";
						}
					 },
					 { display: '职工工号', name: 'emp_code', align: 'left'
					 },
                     { display: '职工名称', name: 'emp_name', align: 'left'
					 },
					 { display: '所属部门', name: 'dept_name', align: 'left'
					 },
					 { display: '职工分类', name: 'kind_name', align: 'left'
					 },
					 { display: '备注', name: 'note', align: 'left'
					 },
					 { display: '是否停用', name: 'is_disable', align: 'left',
							render : function(rowdata, rowindex,
									value) {
								if(rowdata.is_disable == 0){
									return "否";
								}else{
									return "是"
								}
							}
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'../empdict/queryEmpDictList.do?isCheck=false',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
    					{ text: '添加', id:'add', click: itemclick, icon:'add' },
    	                { line:true },
    	                /*{ text: '删除', id:'delete', click: itemclick,icon:'delete' },
    	                { line:true },
    	                */
    	                { text: '打印', id:'print', click: print,icon:'print' },
    	                { line:true },
		                { text: '下载导入模板', id:'downTemplate', click:itemclick,icon:'down' },
		                { line:true },
		                { text: '导入', id:'import', click: importEmp,icon:'up' }
    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.emp_id   + "|" + 
								rowdata.emp_code + "|" + 
								rowdata.emp_no
							);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function importEmp() {
		
		parent.$.ligerDialog.open({ 
       		url : 'hrp/sys/emp/sysEmpImportPage.do?isCheck=false',
			data:{
				columns : grid.columns, 
				grid : grid
			}, height: 300,width: 450,title:'职工维护导入',modal:true,showToggle:false,showMax:true,
			showMin: false,isResize:true,parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		}); 

	}

    function itemclick(item){ 
        if(item.id)
        {
            switch (item.id)
            {
                case "add":
              		$.ligerDialog.open({url: 'empAddPage.do?isCheck=false', height: 350,width: 750, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '保存', onclick: function (item, dialog) { dialog.frame.saveEmp(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
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
							this.emp_id   +"@"+ 
							this.emp_code 
							)
                        });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteEmp.do",{ParamVo : ParamVo},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
                    return;
    			case "downTemplate":location.href = "downTemplate.do?isCheck=false";return;
            	
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
		var parm = "group_id="+vo[0]+"&hos_id="+ vo[1]+"&emp_id="+ vo[2]+"&emp_code="+ vo[3]+"&emp_no="+ vo[4];
		
    	$.ligerDialog.open({ url : 'empUpdatePage.do?isCheck=false&' + parm,data:{}, 
    			height: 470,width: 700, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
    			buttons: [{ text: '保存', onclick: function (item, dialog) {dialog.frame.saveEmp(); },cls:'l-dialog-btn-highlight' },
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
   			title:'职工维护',
   			head:[
				{"cell":0,"value":"单位: ${sessionScope.hos_name}","colspan":colspan_num,"br":true},
			/* 	{"cell":0,"value":"统计日期: " + $("#begin_in_date").val() +" 至  "+ $("#end_in_date").val(),"colspan":colspan_num,"br":true} */
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
   		ajaxJsonObjectByUrl("../empdict/queryEmpDictList.do?isCheck=false", selPara, function (responseData) {
   			printGridView(responseData,printPara);
		});

   		
    }
    function loadDict(){
            //字典下拉框
    	 autocomplete("#dept_no","../queryDeptDict.do?isCheck=false","id","text",true,true);
    	 autocomplete("#emp_kind","../queryEmpKindDict.do?isCheck=false","id","text",true,true);
    	 $("#emp_code").ligerTextBox({width:160});
    	
    	 $("#note").ligerTextBox({width:160});
    	 $("#is_disable").ligerComboBox({
    		 data:[{id:1,text:'是'},{id:0,text:'否'}],
    		 valueField: 'id',
             textField: 'text',
 			 cancelable:true
    	 ,width:160});
    }  
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">部门：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_no" type="text" id="dept_no" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;工:</td>
            <td align="left" class="l-table-edit-td"><input name="emp_code" type="text" id="emp_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">职工分类：</td>
            <td align="left" class="l-table-edit-td"><input name="emp_kind" type="text" id="emp_kind" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">备注：</td>
            <td align="left" class="l-table-edit-td"><input name="note" type="text" id="note" ltype="text" validate="{required:true,maxlength:50}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否停用:</td>
			    <td align="left" class="l-table-edit-td">
                	<!-- <select id="is_stop" name="is_stop" style="width: 135px;">
                		<option value=""></option>
                		<option value="0">否</option>
                		<option value="1">是</option>
             			</select> -->
             		<input name="is_disable" type="text" id="is_disable" ltype="text" validate="{required:true,maxlength:50}"/>
			    </td> 
        </tr>
      
    </table>

	<div id="maingrid"></div>

</body>
</html>
