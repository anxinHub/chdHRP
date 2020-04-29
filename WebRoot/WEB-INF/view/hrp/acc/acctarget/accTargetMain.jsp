<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
    <script type="text/javascript">
	var grid;

	var gridManager = null;

	var userUpdateStr;
	
	$(function() {
		loadDict();

		loadHead(null); //加载数据

	});
	//查询
	function query() {
	
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({name : 'target_code',value : $("#target_code").val()});
		grid.options.parms.push({name : 'target_name',value : $("#target_name").val()});
		//加载查询条件
		grid.loadData(grid.where);
	}
	
	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [  
			    {display : '基本指标编码',name : 'target_code',align:'left',
					  render : function(rowdata, rowindex,value) {
						  return "<a href=javascript:openUpdate('"+rowdata.group_id  + "|" + 
							rowdata.hos_id   + "|" + 
							rowdata.copy_code   + "|" + 
							rowdata.target_code  +"')>"+rowdata.target_code+"</a>";
	  				  }	
			    	},
				{display : '基本指标名称',name : 'target_name',align:'left'},
				{display : '单位',name:'unit_name',align:'left'},
				{display : '排序号',name:'sort_code',align:'left'},
				{display : '是否停用',name:'is_stop',align:'left',
					render : function(rowdata, rowindex,value) {
	  					if(rowdata.is_stop == 0){
	  						return "否"
	  					}else {
	  						return "是"
	  					}
	  				}	
				},
				{display : '备注',name : 'note',align : 'right',width:180},
			],
			dataAction : 'server',dataType : 'server',usePager : false,isAddRow:false,
			url : 'queryAccTarget.do?isCheck=false',width : '100%',height : '100%',
			checkbox : true,rownumbers : true, delayLoad:true,frozen : false ,
			selectRowButtonOnly : false ,heightDiff:0,
			onDblClickRow : function (rowdata, rowindex, value)
			{
				openUpdate(
						rowdata.group_id   + "|" + 
						rowdata.hos_id   + "|" + 
						rowdata.copy_code + "|" + 
						rowdata.target_code 
					);
			} 
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	function add_open(){
		$.ligerDialog.open({url: 'addAccTargetPage.do?isCheck=false', height: 300,width: 500, title:'添加',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.save(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
	}
	
	function del_open(){
		
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
					this.copy_code +"@"+
					this.target_code   
					)
             });
             $.ligerDialog.confirm('确定删除?', function (yes){
             	if(yes){
                 	ajaxJsonObjectByUrl("deleteBatchAccTarget.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
                 		if(responseData.state=="true"){
                 			query();
                 		}
                 	});
             	}
             }); 
         }
	}
	
	function downTemplate(){
		
		location.href = "downTemplate.do?isCheck=false";
	}
	
	function imp_open(){
	
		$.ligerDialog.open({url: 'accTargetImportPage.do?isCheck=false', height: 500,width: 790, title:'导入',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true });
	}
	
	function openUpdate(obj){
		
		var vo = obj.split("|");
		var parm = "group_id="+
			vo[0]   +"&hos_id="+ 
			vo[1]   +"&copy_code="+ 
			vo[2]   +"&target_code="+ 
			vo[3];
		
		$.ligerDialog.open({ url : 'updateAccTargetPage.do?isCheck=false' + parm, height: 300,width: 500, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.save(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
	}
	
	function loadDict() {
		//字典下拉框
		$(':button').ligerButton({width:80});
		
        $("#target_code").ligerTextBox({width:160});
        
        $("#target_name").ligerTextBox({width:160});


	}
	
	function print_but(){
		
		if(grid.getData().length==0){
			$.ligerDialog.error("请先查询数据！");
			return;
		}
           	
        	var selPara={};
        	$.each(grid.options.parms,function(i,obj){
        		selPara[obj.name]=obj.value;
        	});
     
       		//console.log(grid)
       		var printPara={
       			headCount:2,
       			title:'基本指标',
       			type:3,
       			columns:grid.getColumns(1),
       			autoFile:true
       		};
       		
       		ajaxJsonObjectByUrl("queryAccTarget.do?isCheck=false", selPara, function(responseData) {
       			printGridView(responseData,printPara);
        	});

	}
	
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">基本指标编码：</td>
			<td align="left" class="l-table-edit-td"><input  name="target_code" type="text" id="target_code" ltype="text"  /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">基本指标名称：</td>
			<td align="left" class="l-table-edit-td"><input  name="target_name" type="text" id="target_name" ltype="text" /></td>
			<td align="left"></td>
		</tr>
	</table>
    <div style="border:1px">
    <input  type="button" value=" 查询" onclick="query();"/>
	<input  type="button" value=" 添加" onclick="add_open();"/>
	<input  type="button" value=" 删除" onclick="del_open();"/>
    <input  type="button" value=" 下载导入模板" onclick="downTemplate();"/>
	<input  type="button" value=" 导入" onclick="imp_open();"/>
    <input  type="button" value=" 打印" onclick="print_but();"/>
	</div>
    <div id="maingrid"></div>
</body>
</html>
