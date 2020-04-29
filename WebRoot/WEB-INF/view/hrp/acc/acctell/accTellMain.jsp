<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc.jsp"/>
<script type="text/javascript">	
    var grid;
    
    var gridManager = null;
    
    var userUpdateStr;
    
    $(function ()
    {
		loadDict();
    	
    	loadHead(null);	//加载数据
    	
    	$("#topmenu").ligerMenuBar({
			items : [{
				text : '初始余额',
				id : 'install',
				click : itemclick
			}, {
				text : '添加',
				id : 'add',
				click : itemclick
			}, {
				text : '删除',
				id : 'delete',
				click : itemclick
			},  {
				text : '打印',
				menu : menu_print
			}, {
				text : '导出',
				id : 'export',
				click : itemclick
			} ]
		});
    	
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'subj_code',value:liger.get("subj_code").getValue()}); 
    	/* grid.options.parms.push({name:'acc_year',value:$("#acc_year").val()}); 
    	grid.options.parms.push({name:'cash_subj_code',value:$("#cash_subj_code").val()}); 
    	grid.options.parms.push({name:'other_subj_code',value:$("#other_subj_code").val()}); 
    	grid.options.parms.push({name:'summary',value:$("#summary").val()});  */
    	//加载查询条件
    	grid.loadData(grid.where);
     }
    //获取查询条件的数值
    function f_getWhere(){
    	if (!grid) return null;
        var clause = function (rowdata, rowindex){
                	if($("#subj_code").val()!=""){
                		return rowdata.tell_id.indexOf($("#subj_code").val()) > -1;	
                	}
                	if($("#acc_year").val()!=""){
                		return rowdata.acc_year.indexOf($("#acc_year").val()) > -1;	
                	}
                	if($("#cash_subj_code").val()!=""){
                		return rowdata.cash_subj_code.indexOf($("#cash_subj_code").val()) > -1;	
                	}
                	if($("#other_subj_code").val()!=""){
                		return rowdata.other_subj_code.indexOf($("#other_subj_code").val()) > -1;	
                	}
                	if($("#summary").val()!=""){
                		return rowdata.summary.indexOf($("#summary").val()) > -1;	
                	}
        };
        return clause; 
    }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '会计年度', name: 'acc_year', align: 'left'
					 },
                     { display: '摘要', name: 'summary', align: 'left'
					 },
                     { display: '借方金额', name: 'debit', align: 'left'
					 },
                     { display: '贷方金额', name: 'credit', align: 'left'
					 },
                     { display: '票据号', name: 'check_no', align: 'left'
					 },
                     { display: '发生日期', name: 'occur_date', align: 'left'
					 },
                     { display: '结算方式', name: 'pay_type_code', align: 'left'
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAccTell.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.tell_id   + "|" + 
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code 
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
              		$.ligerDialog.open({url: 'accTellAddPage.do?isCheck=false', height: 500,width: 500, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAccTell(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
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
							this.tell_id   +"@"+ 
							this.group_id   +"@"+ 
							this.hos_id   +"@"+ 
							this.copy_code 
							)
                        });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteAccTell.do",{ParamVo : ParamVo},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
                    return;
                case"install":
                	$.ligerDialog.open({url: 'accTellInstallPage.do?isCheck=false', height: 300,width: 500, title:'初始余额',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.installAccTell(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
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
    var menu_print = {
    		width : 120,
    		items : [ {
    			text : '打印',
    			id : 'print',
    			click : itemclick
    		}, {
    			text : '预览',
    			id : 'view',
    			click : itemclick
    		}, {
    			text : '设置',
    			id : 'set',
    			click : itemclick
    		} ]
    	};
    function openUpdate(obj){
    	
		var vo = obj.split("|");
		var parm = 
			vo[0]   +"&"+ 
			vo[1]   +"&"+ 
			vo[2]   +"&"+ 
			vo[3] 
		
    	$.ligerDialog.open({ url : 'deptIncomeUpdatePage.do?isCheck=false&' + parm,data:{}, height: 500,width: 500, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAccTell(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    }
    function loadDict(){
            //字典下拉框
    	autocomplete("#subj_code","../querySubj.do?isCheck=false","id","text",true,true);
         }   
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
	
		<div id="topmenu"></div>
	
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科目编码：</td>
            <td align="left" class="l-table-edit-td"><input name="subj_code" type="text" id="subj_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">日期：</td>
            <td align="left" class="l-table-edit-td"><input name="acc_year" type="text" id="acc_year" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="center" class="l-table-edit-td"  style="padding-left:20px;">至</td>
            <td align="left" class="l-table-edit-td"><input name="cash_subj_code" type="text" id="cash_subj_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">金额：</td>
            <td align="left" class="l-table-edit-td"><input name="other_subj_code" type="text" id="other_subj_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="center" class="l-table-edit-td"  style="padding-left:20px;">至</td>
            <td align="left" class="l-table-edit-td"><input name="summary" type="text" id="summary" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
	
    </table>

	<div id="maingrid"></div>

</body>
</html>
