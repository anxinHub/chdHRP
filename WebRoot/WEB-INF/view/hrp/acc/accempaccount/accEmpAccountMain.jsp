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
			},
    }
    
    
    $(function ()
    {
    	loadHead(null);	//加载数据
    	$("#emp_code").ligerTextBox({width:160});
    	$("#emp_name").ligerTextBox({width:160});
    	autocomplete("#dept_code","../../sys/queryDeptDict.do?isCheck=false","id","text",true,true);
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
        grid.options.parms.push({name:'emp_code',value:$("#emp_code").val()}); 
    	grid.options.parms.push({name:'emp_name',value:$("#emp_name").val()}); 
    	
    	if(liger.get("dept_code").getValue()!= "" ){
    		
    		grid.options.parms.push({name:'dept_id',value:liger.get("dept_code").getValue().split(".")[0]}); 
        	grid.options.parms.push({name:'dept_no',value:liger.get("dept_code").getValue().split(".")[1]});
    		
    	}

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
	                     { display: '职工编码', name: 'emp_code', align: 'left',
	                    	 render:function(rowdata){
	                    		 return "<a href=javascript:openUpdate('"+rowdata.account_id+"|"+rowdata.group_id+"|"+rowdata.hos_id+"|"+rowdata.copy_code+"')>"+rowdata.emp_code+"</a>";
	                    	 }
						 },
	                     { display: '职工姓名', name: 'emp_name', align: 'left'},
						 { display: '身份证号', name: 'id_number', align: 'left'},
						 { display: '部门名称', name: 'dept_name', align: 'left'},
						 { display: '账户类别', name: 'type_name', align: 'left'},
						 { display: '账户姓名', name: 'account_name', align: 'left'},
						 { display: '工资账号', name: 'account_code', align: 'left'},	
						 { display: '备注', name: 'note', align: 'left'}
                     ],
                     dataAction: 'server',dataType: 'server', url:'queryAccEmpAccount.do',
                     width: '100%', height: '95%', checkbox: true,rownumbers:true, usePager:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
						{ text: '查询', id:'search', click: query,icon:'search' },
						{ line:true },
                     	{ text: '添加', id:'add', click: itemclick,icon:'add' },
                     	{ line:true },
                     	{ text: '删除', id:'delete', click: itemclick,icon:'delete' },
                     	{ line:true },
                     	{ text: '查询未维护账号的职工', id:'query', click: itemclick,icon:'collect' },
                     	{ line:true },
                     	{ text: '下载导入模板', id:'downTemplate', click: itemclick,icon:'down' },
                     	{ line:true },
                     	{ text: '导入', id:'import', click: itemclick,icon:'up' },
                     	{ line:true },
                     	{ text: '打印', id:'print', click: print,icon:'print' },
                     	{ line:true }
    				]}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
        
       
    }
    
    function itemclick(item){ 
        if(item.id)
        {
            switch (item.id)
            {
                case "add":
              		$.ligerDialog.open({url: 'accEmpAccountAddPage.do?isCheck=false', height: 500,width: 500, title:'添加',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAccEmpAccount(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
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
							this.account_id+"@"+
							this.group_id+"@"+
							this.hos_id+"@"+
							this.copy_code
							)
                        });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteAccEmpAccount.do",{ParamVo : ParamVo.toString()},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
                    return;
                case "query":
                	$.ligerDialog.open({url: 'accEmpAccountIndexPage.do', height: 500,width: 800, title:'',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [  { text: '关闭', onclick: function (item, dialog) { dialog.close(); } } ] });
              		return;
                case "import":
                	parent.$.ligerDialog.open({
                		url : 'hrp/acc/accempaccount/accEmpAccountImportPage.do?isCheck=false',
            			data:{
            				columns : grid.columns, 
            				grid : grid
            			}, 
            			height: 300,
            			width: 450,
            			title:'职工账号导入',
            			modal:true,
            			showToggle:false,
            			showMax:true,
            			showMin: false,
            			isResize:true,
            			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
            		}); 
                    return;
                case "downTemplate":

                    location.href = "downTemplateEmpAccount.do";

                    return;
            }   
        }
        
    }
    
    function openUpdate(obj){
    	
    	var vo = obj.split("|");
		var parm = "account_id="+
			vo[0]   +"&group_id="+ 
			vo[1]   +"&hos_id="+ 
			vo[2]   +"&copy_code="+ 
			vo[3];

    	$.ligerDialog.open({ url : 'accEmpAccountUpdatePage.do?isCheck=false&' + parm,data:{}, height: 500,width: 500, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAccBank(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

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
				title: "职工帐号",//标题
				columns: JSON.stringify(grid.getPrintColumns()),//表头
				class_name: "com.chd.hrp.acc.service.emp.AccEmpAccountService",
				method_name: "queryAccEmpAccountPrint",
				bean_name: "accEmpAccountService"/* ,
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
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">职工编码：</td>
            <td align="left" class="l-table-edit-td"><input name="emp_code" type="text" id="emp_code" ltype="text" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">职工名称：</td>
            <td align="left" class="l-table-edit-td"><input name="emp_name" type="text" id="emp_name" ltype="text" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">部门名称：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_code" type="text" id="dept_code" ltype="text" /></td>
            <td align="left"></td>
        </tr> 
	
    </table>

	<div id="maingrid"></div>
</body>
</html>
