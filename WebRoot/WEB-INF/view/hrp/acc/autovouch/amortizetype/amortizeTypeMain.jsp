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
<link rel="stylesheet" href="<%=path%>/lib/font-awesome/css/font-awesome.min.css"/>
<script src="<%=path%>/lib/et_components/et_plugins/etDialog.min.js"></script>
<script type="text/javascript">
	var grid;
	var gridManager = null;
	$(function() {
		loadDict();//加载下拉框
		loadHead();
	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;

		grid.options.parms.push({name : 'type_code',value : $("#type_code").val()});
		grid.options.parms.push({name : 'type_name',value : $("#type_name").val()});
		grid.options.parms.push({name : 'is_stop',value : liger.get("is_stop").getValue()});
		//加载查询条件
		grid.loadData(grid.where);
	}


	function loadDict() {

		$("#type_code").ligerTextBox({width : 160});
		$("#type_name").ligerTextBox({width : 160});
		$("#is_stop").ligerComboBox({  
            data: [
                { text: '启用', id: '0' },
                { text: '停用', id: '1' },
            ]
        }); 
	}
	
	 function loadHead(){
	    	grid = $("#maingrid").ligerGrid({
				columns: [
					{display: '类别编码', name: 'type_code', align: 'left' ,
						render:function(rowData){
							return "<a href=javascript:openUpdate('"+rowData.type_code+"')>"+rowData.type_code+"</a>";
						}	
					},
					{display: '类别名称', name: 'type_name', align: 'left' },
					{display: '是否停用', name: 'is_stop', align: 'left' ,
						render:function(rowData){
							if(rowData.is_stop == 0){
								return "启用";
							}else{
								return "停用";
							}
						}
					}
				],
				dataAction: 'server',dataType: 'server',usePager:true,url:'queryAmortizeType.do',
				width: '100%', height: '100%', checkbox: true,rownumbers:true,
				delayLoad: true,//初始化加载，默认false
				selectRowButtonOnly:true,//heightDiff: -10,
				toolbar: { items: [
				   				{ text: '查询（<u>Q</u>）', id:'search', click: query, icon:'search' },
				   				{ line:true },
				   				{ text: '添加', id:'add', click: add, icon:'add' },
				   				{ line:true },
				   				{ text: '删除（<u>D</u>）', id:'delete',  click: remove,  icon:'delete' }
				   			]}

			});

	        gridManager = $("#maingrid").ligerGetGridManager();
	    }
	 
	 function add(){
			parent.$.ligerDialog.open({
				title: '添加',
				height: 300,
				width: 320,
				url: 'hrp/acc/autovouch/amortizetype/amortizeTypeAddPage.do?isCheck=false',
				modal: true, showToggle: false, isResize: true,slide:false,
				parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
			});
	}
	 
	 function openUpdate(type_code){
		 parent.$.ligerDialog.open({
				title: '修改',
				height: 300,
				width: 320,
				url: 'hrp/acc/autovouch/amortizetype/amortizeTypeUpdatePage.do?isCheck=false&type_code='+type_code,
				modal: true, showToggle: false, isResize: true,slide:false,
				parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
			});
	 }
	 
	 function remove(){
		 var data = gridManager.getCheckedRows();
		 if (data.length == 0){
         	$.ligerDialog.error('请选择行');
         }else{
             var ParamVo =[];
             $(data).each(function (){					
					ParamVo.push(
						this.type_code
						) 
					});
             $.ligerDialog.confirm('确定删除?', function (yes){
             	
             	if(yes){
                 	ajaxJsonObjectByUrl("deleteAmortizeType.do",{type_code:ParamVo.toString()},function (responseData){
                 		if(responseData.state=="true"){
                 			query();
                 		}
                 	});
             	}
             }); 
         }
	 }

</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">类别编码：</td>
			<td align="left" class="l-table-edit-td"><input id="type_code" name="type_code" /></td>	
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">类别名称：</td>
			<td align="left" class="l-table-edit-td"><input id="type_name" name="type_name" /></td>	
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">是否停用：</td>
			<td align="left" class="l-table-edit-td"><input id="is_stop" name="is_stop" /></td>	
				
		</tr>
	</table>
	<div id="maingrid" style="margin-top: 10px"></div>
</body>
</html>
