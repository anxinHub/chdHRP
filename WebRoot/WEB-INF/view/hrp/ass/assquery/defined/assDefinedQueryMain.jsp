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
<!-- 综合查询-自定义报表 -->
<script type="text/javascript">

    var grid;
    var gridManager = null;
    
    $(function (){
        loadDict();//加载下拉框
        loadHotkeys();
    	loadHead(null);	//加载数据
    	
    });
    
    //查询
    function query(){
    	
   		grid.options.parms=[];
   		grid.options.newPage=1;
   		
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'rhead_name',value:liger.get("rhead_name").getValue()});
    	grid.options.parms.push({name:'rhead_code',value:liger.get("rhead_code").getValue()});
    	
    	//加载查询条件
    	grid.loadData(grid.where);
    	loadHead();	
		$("#resultPrint > table > tbody").empty();
		
	}
    
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
	        	     { display: '报表名称', name: 'rhead_name', align: 'left',width: '120',frozen: true,
	        	    	 render : function(rowdata, rowindex,value) {
								return "<a href=javascript:openUpdate('"+rowdata.group_id + "|" + rowdata.hos_id
								+ "|" + rowdata.copy_code + "|"
								+ rowdata.rhead_id +"')>"+value+"</a>";
							}
	                 },
                     { display: '资产编码', name: 'rhead_code', align: 'left',width: '120',frozen: true
                     },
                     { display: '查询sql', name: 'r_sql', align: 'left'
					 },
					 { display: '操作', isSort: false, width: 120, render: function (rowdata, rowindex, value){
						 	return "<a href=javascript:run('" + rowdata.rhead_id + "','"+rowdata.rhead_name+"')>执行SQL</a> ";
			         	}
			         }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAssDefinedQuery.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad :true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     tree:{columnId:'ass_type_code'},
                     onDblClickRow : function(rowdata, rowindex, value) {
 						openUpdate(rowdata.group_id + "|" + rowdata.hos_id
 								+ "|" + rowdata.copy_code + "|"
 								+ rowdata.rhead_id);
 					},
                     toolbar: { 
                    	 items: [
                            { text: '查询', id:'query', click: query,icon:'search' },
                            { line:true },
                     		{ text : '添加',	id : 'add',click : itemclick,icon : 'add'}, 
                     		{line : true},
                     		{ text : '删除',	id : 'delete',click : itemclick,icon : 'delete'}
                         ]},
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function run(id,title){
    	//console.log()
    	//location.href = 'assDefinedQueryRunMainPage.do?isCheck=false&rhead_id='+id;
    	$.ligerDialog.open({
			url : 'assDefinedQueryRunMainPage.do?isCheck=false&rhead_id='+id,
			title : title,
			modal : true,
			showToggle : false,
			showMax : true,
			showMin : true,
			isResize : true
		}); 
    }
    
    function itemclick(item) {
		if (item.id) {
			switch (item.id) {
			case "add":
				$.ligerDialog.open({
					url : 'assDefinedQueryAddPage.do?isCheck=false',
					height: $(window).height() / 1.4,
					width: $(window).width()  / 1.4,
					title : '添加',
					modal : true,
					showToggle : false,
					showMax : true,
					showMin : true,
					isResize : true,
					buttons : [ {
						text : '确定',
						onclick : function(item, dialog) {
							dialog.frame.saveAssDefinedQuery();
						},
						cls : 'l-dialog-btn-highlight'
					}, {
						text : '关闭',
						onclick : function(item, dialog) {
							dialog.close();
						}
					} ]
				});
				return;
			case "modify":
				return;
			case "delete":
				var data = gridManager.getCheckedRows();
				if (data.length == 0) {
					$.ligerDialog.error('请选择行');
				} else {
					$.ligerDialog.confirm('确定删除?', function(yes) {
						if (yes) {
							ajaxJsonObjectByUrl("deleteAssDefinedQuery.do?isCheck=false", {
								param : JSON.stringify(data)
							}, function(responseData) {
								if (responseData.state == "true") {
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
    
    function openUpdate(obj) {

		var vo = obj.split("|");
		if("null"==vo[3]){
			return false;
			
		}
		var parm = "group_id=" + vo[0] + "&" + "hos_id=" + vo[1] + "&"
				+ " copy_code=" + vo[2] + "&" + "rhead_id=" + vo[3];

		$.ligerDialog.open({
			title: '修改',
			url: 'assDefinedQueryUpdatePage.do?isCheck=false&'+parm,
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.saveAssDefinedQuery();
				},
				cls : 'l-dialog-btn-highlight'
			}, {
				text : '关闭',
				onclick : function(item, dialog) {
					dialog.close();
				}
			} ]
		});
	}
    
    //字典下拉框
    function loadDict(){
    	$("#rhead_name,#rhead_code").ligerTextBox({width:180});
    } 
    //键盘事件
	function loadHotkeys() {
	}
    
	function printDate(){
   		
    }
	  
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
     <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
		    <td align="right" class="l-table-edit-td"  style="padding-left:20px;">名称：</td>
            <td align="left" class="l-table-edit-td"><input name="rhead_name" type="text" id="rhead_name"  /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">编码：</td>
            <td align="left" class="l-table-edit-td"><input name="rhead_code" type="text" id="rhead_code"  /></td>
            <td align="left"></td>
        </tr>
       
    </table>

	<div id="maingrid"></div>
</body>
</html>
