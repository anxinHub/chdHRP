<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script src="<%=path%>/pageoffice.js" type="text/javascript" id="po_js_main"></script>
<script type="text/javascript">
	var grid;
	var gridManager = null;
	$(function() {
		loadDict();
		
		
		loadHead(null); //加载数据
		/* $("#topmenu").ligerMenuBar({
			items : [{
				text : '记账',
				id : 'accounting',
				click : itemclick
			}, {
				text : '取消记账',
				id : 'unaccounting',
				click : itemclick
			} , {
				text : '打印',
				menu : menu_print
			}, {
				text : '导出',
				id : 'export',
				click : itemclick
			} ]
		}); */

	});
	
	var state ;
	var state_name;
	
	$(function(){
		ajaxJsonObjectByUrl("../queryVouchState.do?isCheck=false&state="+99,{}, function(data) {
			
			if(data.Rows.length > 0){
				state=data.Rows[0].parent_node_id;
				state_name = data.Rows[0].state_name;
			}
		});
	})
	
	
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		//加载查询条件
		grid.options.parms.push({name : 'acc_year',value : liger.get("acc_year_month").getValue().split(".")[0] });
		grid.options.parms.push({name : 'acc_month',value : liger.get("acc_year_month").getValue().split(".")[1] });
		grid.loadData(grid.where);
	}
	
	
	//加载grid
	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [
				{display : '会计期间',name : 'acc_year',align : 'left'},
				
				{display : '凭证类型',name : 'vouch_type_short',align : 'left'},
				
				{display : '已记账凭证',name : 'vouch_accounting',align : 'left',render : 
					function(rowdata, rowindex,value) {
						return "<a href=javascript:openAccounting('"
								+ rowdata.acc_year + "|"
								+ rowdata.vouch_type_code + "|"
								+ rowdata.group_id + "|"
								+ rowdata.hos_id + "|"
								+ rowdata.copy_code
								+ "')>"			
									+ rowdata.vouch_accounting
								+ "</a>";
					}
				},
				
				{display : '未记账凭证',name : 'unvouch_accounting',align : 'left',render : 
					function(rowdata, rowindex,value) {
						return "<a href=javascript:openUnAccounting('"
								+ rowdata.acc_year + "|"
								+ rowdata.vouch_type_code + "|"
								+ rowdata.group_id + "|"
								+ rowdata.hos_id + "|"
								+ rowdata.copy_code
								+ "')>"
									+ rowdata.unvouch_accounting
								+ "</a>";
					}
				} 
			],
			dataAction : 'server',dataType : 'server',usePager : false,url : 'queryAccVouchAccount.do',width : '100%',height : '100%',
			checkbox : false,delayLoad:true,rownumbers : true,selectRowButtonOnly : true,toolbar: { 
				items: [
	            	{ text: '查询', id:'search', click: query, icon:'search' },
	                { line:true },
	                { text: '记账', id:'accounting', click: itemclick, icon:'account' },
	    	        { line:true },
	    	        { text: '取消记账', id:'unaccounting', click: itemclick,icon:'unaccount' },
					{ line:true },
					{ text: '打印', id:'print', click: printDate,icon:'print'}
	             ]
			}
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	
	function itemclick(item) {
		if (item.id) {
			switch (item.id) {
			case "search":
				return;
			case "add":
				return;
			case "modify":
				return;
			case "delete":
				return;
			case "accounting":
				
				var data = gridManager.getData();
				var acc_year = liger.get("acc_year_month").getValue();
				
				var flag=true;
				$(data).each(function (){		
					if(this.unvouch_accounting > 0){
						flag=false;
					}
				});
				
				if (flag == true) {
					$.ligerDialog.error('没有数据可以进行记账操作!');
				} else {
					$.ligerDialog.confirm('确定记账?', function(yes) {
						if (yes) {
							ajaxJsonObjectByUrl("accountingAccVouch.do", {acc_year:acc_year.split(".")[0],acc_month:acc_year.split(".")[1],parent_node_id:"${parent_node_id}"}, function(responseData) {
								if (responseData.state == "true") {
									query();
								}
							});
						}
					});
				}
				return;
			case "unaccounting":
				
				var data = gridManager.getData();
				var acc_year = liger.get("acc_year_month").getValue();
				var flag=true;
				$(data).each(function (){		
					if(this.vouch_accounting > 0){
						flag=false;
					}
				});
				
				if (flag==true) {
					$.ligerDialog.error('没有数据可以进行取消记账操作!');
				} else {
					$.ligerDialog.confirm('确定取消记账?', function(yes) {
						if (yes) {
							ajaxJsonObjectByUrl("unaccountingAccVouch.do", {acc_year:acc_year.split(".")[0],acc_month:acc_year.split(".")[1],parent_node_id:"${parent_node_id}"}, function(responseData) {
								if (responseData.state == "true") {
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
				setTimeout(function() {
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
	function loadDict() {
		
		/* $("#acc_year_month").ligerComboBox({
          	url: '../../queryYearMonth.do?isCheck=false',
          	valueField: 'id',
           	textField: 'text', 
           	selectBoxWidth: 80,
          	autocomplete: true,
          	width: 80,
          	onSuccess:function(data){
          		for(var i in data){
          			if(data[i].text =='${yearMonth}'){
          				liger.get("acc_year_month").setValue(data[i].id.split(".")[0]+"."+data[i].id.split(".")[1]);
          				liger.get("acc_year_month").setText(data[i].text);
          			}
          		}
          	}
 		  }); */
     $("#acc_year_month").ligerComboBox({disabled:true});
	 
     var year_Month = '${yearMonth}';

   	 liger.get("acc_year_month").setValue(year_Month);
		 
   	 liger.get("acc_year_month").setText(year_Month);
	
	}
	//已记账凭证
	function openAccounting(obj) {

		var vo = obj.split("|");
		var acc_year_month = vo[0].split(".");
		var parm =  
			"acc_year=" + acc_year_month[0] +"&acc_month=" + acc_year_month[1]  
			+"&vouch_type_code=" + vo[1] 
			+ "&group_id=" + vo[2]+ "&hos_id=" + vo[3] + "&copy_code=" + vo[4];

		$.ligerDialog.open({
			url : 'accVouchAccountingMainPage.do?isCheck=false&' + parm,data : {},height : 530,width : 1120,title : '已记账凭证',
			modal : true,showToggle : false,showMax : false,showMin : false,isResize : true,
			buttons : [ /* {
				text : '打印',
				onclick : function(item, dialog) {
					dialog.frame.print();
				},
				cls : 'l-dialog-btn-highlight'
			}, {
				text : '导出',
				onclick : function(item, dialog) {
					dialog.frame.acc_export();
				},
				cls : 'l-dialog-btn-highlight'
			}, {
				text : '取消记账',
				onclick : function(item, dialog) {
					dialog.frame.acc_qxjz();
				},
				cls : 'l-dialog-btn-highlight'
			}, */ {
				text : '关闭',
				onclick : function(item, dialog) {
					dialog.close();
				}
			} ]
		});

	}
	//未记账凭证
	function openUnAccounting(obj) {

		var vo = obj.split("|");
		var parm =  "acc_year=" + vo[0] +"&vouch_type_code=" + vo[1] 
		+ "&group_id=" + vo[2]+ "&hos_id=" + vo[3] + "&copy_code=" + vo[4];

		$.ligerDialog.open({
			url : 'accVouchUnAccountingMainPage.do?isCheck=false&' + parm,data : {},height : 530,width : 1120,
			title : '未记账凭证',modal : true,showToggle : false,showMax : false,showMin : false,isResize : true,
			buttons : [ /* {
				text : '打印',
				onclick : function(item, dialog) {
					dialog.frame.print();
				},
				cls : 'l-dialog-btn-highlight'
			}, {
				text : '导出',
				onclick : function(item, dialog) {
					dialog.frame.acc_export();
				},
				cls : 'l-dialog-btn-highlight'
			}, {
				text : '记账',
				onclick : function(item, dialog) {
					dialog.frame.acc_jz();
				},
				cls : 'l-dialog-btn-highlight'
			},  */{
				text : '关闭',
				onclick : function(item, dialog) {
					dialog.close();
				}
			} ]
		});

	}
	
	function printDate(){
    	
    	if(grid.getData().length==0){
			$.ligerDialog.error("请先查询数据！");
			return;
		}
    	
    	var heads={
        		  //"isAuto": true/false 默认true，页眉右上角默认显示页码
       		  "rows": [
   	          {"cell":0,"value":"会计期间："+$("#acc_year_month").val(),"colSpan":"5"}
       		  ]
       	};
     		
   		var printPara={
 			rowCount:1,
 			title:'凭证记账查询',
 			columns: JSON.stringify(grid.getPrintColumns()),//表头
 			class_name: "com.chd.hrp.acc.service.AccVouchAccountingService",
			method_name: "queryAccVouchAccountPrint",
			bean_name: "accVouchAccountingService",
			heads: JSON.stringify(heads)//表头需要打印的查询条件,可以为空
   		};
    	
   		//执行方法的查询条件
   		$.each(grid.options.parms,function(i,obj){
   			printPara[obj.name]=obj.value;
      	});
     		
      	officeGridPrint(printPara);
	}
	
	/**
	 * 打印 打印 预览 设置
	 */
	var menu_print = {
		width : 120,
		items : [
			{text : '打印',id : 'print',click : itemclick}, 
			{text : '预览',id : 'view',click : itemclick}, 
			{text : '设置',id : 'set',click : itemclick} 
		]
	};
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="topmenu"></div>
	<input type="hidden" id="parent_node_id" name="parent_node_id" value="${parent_node_id}"></input>
	<form name="form1" method="post"  id="form1" >
    <table cellpadding="0" cellspacing="0" class="l-table-edit" border="0">
        <tr>
            <td align="left" style="padding-left: 50px" >会计期间：</td>
            <td align="right" class="l-table-edit-td"  style="padding-left:10px;"><input id="acc_year_month" name="acc_year_month" dis /></td>
        <!-- <td align="left"><input type="button"  class="liger-button" value=" 查询" onclick="query();"/></td> -->
        </tr> 
    </table>
	</form>
	<div id="maingrid"></div>
</body>
</html>
