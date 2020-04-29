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
    	
    });
    //查询
    function  query(){
    	grid.options.parms=[];
    	grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'pay_code',value:$("#pay_code").val()}); 
    	grid.options.parms.push({name:'pay_name',value:$("#pay_name").val()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }
    //获取查询条件的数值
    function f_getWhere(){
    	if (!grid) return null;
        var clause = function (rowdata, rowindex){
                	if($("#pay_code").val()!=""){
                		return rowdata.pay_code.indexOf($("#pay_code").val()) > -1;	
                	}
                	if($("#pay_name").val()!=""){
                		return rowdata.pay_name.indexOf($("#pay_name").val()) > -1;	
                	}
        };
        return clause; 
    }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '支付方式编码', name: 'pay_code', align: 'left',
							render : function(rowdata, rowindex,
									value) {
									return "<a href=javascript:openUpdate('"+rowdata.pay_code   + "|" + 
									rowdata.group_id   + "|" + 
									rowdata.hos_id   + "|" + 
									rowdata.copy_code   + "|" + 
									rowdata.acc_year  +"')>"+rowdata.pay_code+"</a>";
							}
					 },
                     { display: '支付方式名称', name: 'pay_name', align: 'left'
					 },
                     { display: '支付属性', name: 'pay_type', align: 'left',
							render : function(rowdata, rowindex,
									value) {
								if(value == 0){
									return "现金";
								}else if(value == 1){
									return "银行";
								}else if(value == 2){
									return "应付";
								}
							}
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:false,url:'queryMedPayType.do',
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
								rowdata.pay_code   + "|" + 
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code   + "|" + 
								rowdata.acc_year 
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
              		$.ligerDialog.open({url: 'medPayTypeAddPage.do?isCheck=false', 
              				height: 300,width: 500, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,
              				buttons: [ 
              				           { text: '确定', onclick: function (item, dialog) { dialog.frame.saveMedPayType(); },cls:'l-dialog-btn-highlight' }, 
              				           { text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
              				         ] 
              		});
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
							this.pay_code   +"@"+ 
							this.group_id   +"@"+ 
							this.hos_id   +"@"+ 
							this.copy_code   
							)
                        });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteMedPayType.do",{ParamVo : ParamVo.toString()},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
                    return;
                case "extend":
              		$.ligerDialog.open({url: 'medPayTypeExtendPage.do?isCheck=false', 
              				height: 240,width: 500, title:'继承数据',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,
              				buttons: [ 
              				           { text: '确定', onclick: function (item, dialog) { dialog.frame.extendMedPayType(); },cls:'l-dialog-btn-highlight' }, 
              				           { text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
              				         ] 
              		});
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
		var parm = "pay_code="+
			vo[0]   +"&group_id="+ 
			vo[1]   +"&hos_id="+ 
			vo[2]   +"&copy_code="+ 
			vo[3]   +"&acc_year="+ 
			vo[4]; 
		
    	$.ligerDialog.open({ url : 'medPayTypeUpdatePage.do?isCheck=false&' + parm,data:{}, 
    			height: 300,width: 500, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
    			buttons: [ 
    			           { text: '确定', onclick: function (item, dialog) { dialog.frame.saveMedPayType(); },cls:'l-dialog-btn-highlight' }, 
    			           { text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
    			         ] 
    	});

    }
    function loadDict(){
        //字典下拉框
    	$("#pay_code").ligerTextBox({width:160});
    	$("#pay_name").ligerTextBox({width:160}); 
     }   
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">支付方式编码：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="pay_code" type="text" id="pay_code" ltype="text" validate="{required:true,maxlength:20}" />
            </td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">支付方式名称：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="pay_name" type="text" id="pay_name" ltype="text" validate="{required:true,maxlength:20}" />
            </td>
            <td align="left"></td>
        </tr> 
	
    </table>

	<div id="maingrid"></div>

</body>
</html>
