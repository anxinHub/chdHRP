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
    	$("#bank_number").ligerTextBox({width:160});
    	$("#bank_name").ligerTextBox({width:160});
    	
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'bank_number',value:$("#bank_number").val()}); 
    	grid.options.parms.push({name:'bank_name',value:$("#bank_name").val()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '银行编码', name: 'bank_number', align: 'left',
                    	 render:function(rowdata){
                    		 return "<a href=javascript:openUpdate('"+rowdata.bank_number+"|"+rowdata.group_id+"|"+rowdata.hos_id+"')>"+rowdata.bank_number+"</a>";
                    	 }
					 },
                     { display: '银行名称', name: 'bank_name', align: 'left'
					 },
					 { display: '简称', name: 'simple_name', align: 'left'
					 },
					 { display: '银行账号', name: 'bank_zh', align: 'left'
					 },
					 { display: '开户行', name: 'bank_address', align: 'left'
					 },
					 { display: '开户人', name: 'bank_account', align: 'left'
					 },
					 { display: '联系电话', name: 'phone', align: 'left'
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
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAccBank.do',
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
        
       
    }
    
    function itemclick(item){ 
        if(item.id)
        {
            switch (item.id)
            {
                case "add":
              		$.ligerDialog.open({url: 'accBankAddPage.do?isCheck=false', height: 500,width: 500, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAccBank(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
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
							this.bank_number+"@"+
							this.group_id+"@"+
							this.hos_id
							)
                        });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteAccBank.do",{ParamVo : ParamVo.toString()},function (responseData){
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
    
    function toobarclick(item){ 
        if(item.id)
        {
            switch (item.id)
            {
                case "delete":
                	var ParamVo =[];
                    $(data).each(function (){					
						ParamVo.push(
						//表的主键
						this.group_id   +"@"+ 
						this.hos_id   +"@"+ 
						this.copy_code 
						)
                    });
                	ajaxJsonObjectByUrl("deleteAccWageEmp.do",{ParamVo : ParamVo.toString()},function (responseData){
                		if(responseData.state=="true"){
                			query();
                		}
                	});
                	return;
                case "del":
                    var data = gridManager.getCheckedRows();
                    if (data.length == 0){
                    	$.ligerDialog.error('请选择行');
                    }else{
                        var ParamVo =[];
                        $(data).each(function (){					
							ParamVo.push(
							this.wage_code   +"@"+ 
							this.emp_id   +"@"+ 
							this.group_id   +"@"+ 
							this.hos_id   +"@"+ 
							this.copy_code 
							)
                        });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteBatchAccWageEmp.do",{ParamVo : ParamVo},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
                    return;
                case "collect":
              		$.ligerDialog.open({url: 'accWageEmpCollectPage.do?isCheck=false', height: 500,width: 600, title:'选择职工',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '勾选导入', onclick: function (item, dialog) { dialog.frame.save(); },cls:'l-dialog-btn-highlight' },{ text: '部分导入', onclick: function (item, dialog) { dialog.frame.saveAll(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
              		return;
            }   
        }
        
    }
    
    function openUpdate(obj,group_id,hos_id){
    	
    	var vo = obj.split("|");
		var parm = "bank_number="+
			vo[0]   +"&group_id="+ 
			vo[1]   +"&hos_id="+ 
			vo[2];

    	$.ligerDialog.open({ url : 'accBankUpdatePage.do?isCheck=false&' + parm,data:{}, height: 500,width: 500, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAccBank(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

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
				title: "银行信息",//标题
				columns: JSON.stringify(grid.getPrintColumns()),//表头
				class_name: "com.chd.hrp.acc.service.emp.AccBankService",
				method_name: "queryAccBankPrint",
				bean_name: "accBankService"/* ,
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
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">银行编码：</td>
            <td align="left" class="l-table-edit-td"><input name="bank_number" type="text" id="bank_number" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">银行名称：</td>
            <td align="left" class="l-table-edit-td"><input name="bank_name" type="text" id="bank_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
	
    </table>

	<div id="maingrid"></div>
</body>
</html>
