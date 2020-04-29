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
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    $(function ()
    {
        //loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
		 loadHotkeys();
        
		
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
    	//加载查询条件
    	grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
     }
   

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                    
                     { display: '支出项目编码', name: 'payment_item_code', align: 'left'
					 		},
                     { display: '支出项目名称', name: 'payment_item_name', align: 'left'
					 		},
                     { display: '支出项目全称', name: 'item_name_all', align: 'left'
					 		},
	 		 		{ display: '支出项目性质', name: 'payment_item_nature', align: 'left'
					 		},
                    { display: '上级项目', name: 'super_code', align: 'left'
					 		},
					{ display: '是否管理费', name: 'is_manage', align: 'left'
						    },
                     { display: '是否末级', name: 'is_last', align: 'left'
					 		},
                     { display: '控制方式', name: 'control_way', align: 'left'
					 		}
                     
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryBudgPaymentItemDict.do?isCheck=false',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询（<u>E</u>）', id:'search', click: query,icon:'search' },
                     	{ line:true },
				    					{ text: '保存（<u>A</u>）', id:'add', click: add_open, icon:'add' },
				    	                { line:true },
				    	                { text: '重置（<u>R</u>）', id:'reset', click: reset,icon:'delete' },
						                { line:true }
				    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code   + "|" + 
								rowdata.payment_item_no   + "|" + 
								rowdata.payment_item_id 
							);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function add_open(){
    	
		$.ligerDialog.open({ url : 'budgPaymentItemDictAddPage.do?isCheck=false',data:{}, height: 300,width: 450, title:'预算支付项目',modal:true,showToggle:false,showMax:true,showMin: false,isResize:true,
			buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.savebudgPaymentItemDict(); },cls:'l-dialog-btn-highlight' },
			           { text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
			          ]
    	}); 
		
    	}
    	
  /*   function remove(){
    	
    	var data = gridManager.getCheckedRows();
                    if (data.length == 0){
                    	$.ligerDialog.error('请选择行');
                    }else{
                        var ParamVo =[];
                        $(data).each(function (){					
							ParamVo.push(
							this.group_id   +"@"+ 
							this.hos_id   +"@"+ 
							this.copy_code   +"@"+ 
							this.payment_item_no   +"@"+ 
							this.payment_item_id 
							) });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteBudgPaymentItemDict.do",{ParamVo : ParamVo.toString()},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
    	} */
    function reset () {
    	
    		var data = gridManager.getCheckedRows();
            if (data.length == 0){
            	$.ligerDialog.error('请选择行');
            }else{
                var ParamVo =[];
                $(data).each(function (){					
					ParamVo.push(
					this.group_id   +"@"+ 
					this.hos_id   +"@"+ 
					this.copy_code   +"@"+ 
					this.payment_item_no   +"@"+ 
					this.payment_item_id 
					) });
                $.ligerDialog.confirm('确定要重置吗?', function (yes){
                	if(yes){
                    	ajaxJsonObjectByUrl("deleteBudgPaymentItemDict.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
                    		if(responseData.state=="true"){
                    			query();
                    		}
                    	});
                	}
                }); 
            }		
    }
   
    function openUpdate(obj){
    		
		var vo = obj.split("|");
		var parm = 
			"group_id="+vo[0]   +"&"+ 
			"hos_id="+vo[1]   +"&"+ 
			"copy_code="+vo[2]   +"&"+ 
			"payment_item_no="+vo[3]   +"&"+ 
			"payment_item_id="+vo[4] 
		 
		 
		 $.ligerDialog.open({ url : 'budgPaymentItemDictUpdatePage.do?isCheck=false&',data:{}, height: 300,width: 450, title:'控制方式（CONTROL_WAY）取自系统字典表',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
			buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.savebudgPaymentItemDict(); },cls:'l-dialog-btn-highlight' },
			           { text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
			          ]
    	}); 
		 
  
    }
    function downTemplate () {
    	
    }
	function exportExcel () {
		
	}
	function printDate () {
			
	}
	function imp () {
		
	}
    //键盘事件
	  function loadHotkeys() {

		hotkeys('Q', query);

		hotkeys('A', add);
		hotkeys('D', remove);

		hotkeys('B', downTemplate);

		hotkeys('E', exportExcel);

		hotkeys('P', printDate);
		hotkeys('I', imp);
		

	 }
 	  
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">项目名称：${proj_id}</td>
            <td align="left" class="l-table-edit-td"></td>
            <td align="left"></td>
        </tr> 
    </table>

	<div id="maingrid"></div>
</body>
</html>
