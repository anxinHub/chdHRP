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
    
    var gridManager = null;
    
    $(function ()
    {
    	loadHead(null);	//加载数据
    	loadDict();
    	
    });
    
  //查询
    function  query(){
    	
    	grid.options.parms=[];
    	
    	grid.options.newPage=1;
    
    	grid.options.parms.push({name:'wage_code',value:liger.get("wage_code").getValue()}); 
    	
    	grid.options.parms.push({name:'kind_code',value:liger.get("kind_code").getValue()}); 
    	//加载查询条件
    	grid.loadData(grid.where);
    	
     }

    function loadHead(){
    	 
        
    	grid = $("#maingrid").ligerGrid({
            columns: [ 
                      { display: '工资套编码', name: 'wage_code', align: 'left',
                    	  render:function(rowdata){
                     		 return "<a href=javascript:openUpdate('"+rowdata.wage_code+"|"+rowdata.kind_code+"|"+rowdata.group_id+"|"+rowdata.hos_id+"|"+rowdata.copy_code+"')>"+rowdata.wage_code+"</a>";
                     	 }
 					 },
                      { display: '工资套名称', name: 'wage_name', align: 'left'
 					 },
 					 { display: '职工分类编码', name: 'kind_code', align: 'left'
 					 },
                     { display: '职工分类名称', name: 'kind_name', align: 'left',width:'30%'
					 }
                      ],
                      dataAction: 'server',dataType: 'server',usePager:true,url:'queryAccWageEmpKind.do',
                      width: '100%', height: '100%', checkbox:true,rownumbers:true,delayLoad:false,
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
              		$.ligerDialog.open({url: 'accWageEmpKindAddPage.do?isCheck=false', height: 

											300,width: 500, title:'添加',modal:true,showToggle:false,showMax:false,showMin: 
											
											true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.save
											
											(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { 
											
											dialog.close(); } } ] });
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
							this.group_id+"@"+
							this.hos_id+"@"+
							this.copy_code+"@"+
							this.wage_code+"@"+
							this.kind_code

							)
                        });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteAccWageEmpKind.do",{ParamVo :  ParamVo.toString()},function (responseData){
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
    
     
    
    function openUpdate(obj){
    	
    	var vo = obj.split("|");
		var parm = "wage_code="+ vo[0] +
				   "&kind_code="+ vo[1]   +
				   "&group_id="+ vo[2]   +
				   "&hos_id="+ vo[3]   +
				   "&copy_code="+  vo[4]  ;
    	$.ligerDialog.open({ url : 'accWageEmpKindUpdatePage.do?isCheck=false&' + parm,data:{}, 

									height: 300,width: 500, title:'修改',modal:true,showToggle:false,showMax:false,showMin: 
									
									false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { 
									
									dialog.frame.save(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, 
									
									dialog) { dialog.close(); } } ] });

    }
    
    function print(){
    	
    	if(grid.getData().length==0){
			$.ligerDialog.error("请先查询数据！");
			return;
		} 
		var printPara={
				title: "工资套与人员类别对应关系",//标题
				columns: JSON.stringify(grid.getPrintColumns()),//表头
				class_name: "com.chd.hrp.acc.service.wage.AccWageEmpKindService",
				method_name: "queryAccWageEmpKindListPrint",
				bean_name: "accWageEmpKindService"  
				};
				$.each(grid.options.parms,function(i,obj){
					
					printPara[obj.name]=obj.value;
			});
			 
	officeGridPrint(printPara);
    }
    function loadDict(){
   		 autocomplete("#wage_code","../queryAccWage.do?isCheck=false","id","text",true,true);
   		 autocomplete("#kind_code", "../../sys/queryEmpKindDict.do?isCheck=false&is_stop=0", "id", "text", true, true);
		 $("#kind_code").ligerTextBox({width:160});
    	 $("#wage_code").ligerTextBox({width:160});
    }
    
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">工资套:</td>
       		<td align="left">
       			<input type="text" id="wage_code" name="wage_code"  >
       		</td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">职工分类:</td>
       		<td align="left">
       			<input type="text" id="kind_code" name="kind_code"  >
       		</td>
            <td align="left"></td>
        </tr> 
	
    </table>

	<div id="maingrid"></div>
</body>
</html>
