<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<jsp:include page="${path}/static_resource.jsp">
		 <jsp:param value="select,datepicker,select,ligerUI,checkbox,tree" name="plugins"/>
	</jsp:include>
	<style>
		.table-layout {font-size: 13px;width:100%}
		 .l-children{
       		position: relative;
  			left: -30px;
       }
	</style>
	<script type="text/javascript">
		//spread=document.getElementById('spreadFrame').contentWindow.GcSpread.Sheets.designer.wrapper.spread;
	var grid,tree;
	var gridManager = null;
	
	/*var str  = '&nbsp;&nbsp;<div class="l-toolbar-item" toolbarid="item-1"><span style="float:left">会计科目：<input type="text" id="subj_query" class="liger-textbox"  style="float:left"></span><input class="l-button l-button-submit" type="button" onClick="query()" value="查询">'
	+'</div>';*/
	$(function() {
		loadTree();
		loadHead(null); //加载数据
		loadCss();
	});
	//查询
	function query() {
		var node = tree.getSelected();
		if(node == null){
			$.ligerDialog.error('请先选择差异标注项目');
			return;
		}
		
      	 var subj_code = $("#subj_code").val();
	     subj_code = subj_code.split(" ")[0];
	     grid.options.parms = [];
	     
		 if(node.data.pid == 'top'){
	       	grid.options.parms.push({ name : 'diff_type_code', value : node.data.id});
	       	grid.options.parms.push({ name : 'subj_code', value : subj_code});
      	 }else if(node.data.id == 'top'){
	       	grid.options.parms.push({ name : 'subj_code', value : subj_code});
      	 }else{
			grid.options.parms.push({ name : 'diff_item_code', value : node.data.id});
			grid.options.parms.push({ name : 'subj_code', value : subj_code});
      	 }
		grid.options.newPage = 1;
		grid.loadData(grid.where);
	}

	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [
				{display : '科目编码',name : 'subj_code',align : 'left',width : 160}, 
				{display : '科目名称',name : 'subj_name_all',align : 'left',width:310}, 
				{display : '差异类别',name : 'diff_type_name',align : 'left',width:310}, 
				{display : '差异方向',name : 'diff_dire',align : 'left',width : 80,
					render : function(rowdata, rowindex, value) {
						if (rowdata.diff_dire == 0) {
							return "借";
						} else {
							return "贷"
						}
					}
				}
			],
			dataAction : 'server',dataType : 'server',usePager : false,
			url : 'queryAccDifferSubj.do?isCheck=false',width : '100%',delayLoad : true,
			height : '100%',checkbox : true,rownumbers : true,selectRowButtonOnly : true,heightDiff: 28,
			toolbar : {
				items : [ 
					{text : '添加',id : 'add',click : itemclick,icon : 'add'}, 
					{line : true}, 
					{text : '删除',id : 'delete',click : itemclick,icon : 'delete'} ,
					{line : true}, 
				]
			},
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	function itemclick(item) {
		if (item.id) {
			switch (item.id) {
			case "add":
				var node = tree.getSelected();
				if (node == null || node.data.pid == 'top' || node.data.id == 'top') {
					$.ligerDialog.error('请先选择差异标注项目');
					return;
				}
				$.ligerDialog.open({
					url : 'accDifferSubjAddPage.do?isCheck=false&diff_item_code=' + node.data.id+'&diff_type_code='+node.data.pid,
					height : 500,
					width : 700,
					title : '添加',
					modal : true,
					showToggle : false,
					showMax : false,
					showMin : true,
					isResize : true,
					buttons : [ {
						text : '确定',
						onclick : function(item, dialog) {
							dialog.frame.save();
						},
						cls : 'l-dialog-btn-highlight'
					}, {
						text : '取消',
						onclick : function(item, dialog) {
							dialog.close();
						}
					} ]
				});
				return;
			case "delete":
				var data = gridManager.getCheckedRows();
				if (data.length == 0) {
					$.ligerDialog.error('请选择行');
				} else {
					var ParamVo = [];
					$(data).each(function() {
						ParamVo.push(this.subj_code+"@"+this.diff_item_code)
					});
					$.ligerDialog.confirm('确定删除?', function(yes) {
						if (yes) {
							ajaxJsonObjectByUrl("deleteAccDifferSubj.do?isCheck=false", {
								mapVo : ParamVo.toString()
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
	
	var setting = {      
	   		data: {simpleData: {enable: true}},
	   		treeNode:{open:true},
	   		callback:{onClick:groupDetail}
	}; 
	function loadTree(){
       	$.post('queryDifferItemTree.do?isCheck=false' ,null,function (responseData){
     	    tree= $("#tree").ligerTree({  
     	         data: responseData.Rows, 
     	         idFieldName :'id',
     	         parentIDFieldName :'pid',
     	         textFieldName : 'name',
     	         nodeWidth : 400,
     	         checkbox :false,
     	         single : true,
     	         onSelect : function(data){
     	        		 query();
     	         }
     	      });
       	},"json");
     }
	
	function groupDetail(){
		query();
	}
	
	function loadCss(){
		$("#layout1").ligerLayout({ leftWidth: 280, allowLeftResize: true
			,onLeftToggle: function (isColl){
        	//alert(isColl ? "收缩" : "显示");
				grid._onResize();
	    	}
			,onEndResize: function(isColl) {
				grid._onResize();
	        }	 
		});
		$("#treeDiv").css("height", $(window).height() - 28);
		autocomplete("#subj_code","../../querySubj.do?isCheck=false","id","text",true,true,"","","",200,"",subjWidth);
		$(':button').ligerButton({width:80});
	}
	</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div class="l-layout" id="layout1">
		<div position="left" title="差异项目" >
			<div style="overflow:auto;" id="treeDiv">
				<ul class="ztree" id="tree"></ul>
			</div>
		</div>
		<div position="center" title="科目" >
			<table cellpadding="0" cellspacing="0" class="l-table-edit" id="acc_report_tab">
				<tr>
				     <td align="right" class="l-table-edit-td"  style="padding-left:20px;">会计科目：</td>
				     <td align="left" class="l-table-edit-td">
						<input name="subj_code" id="subj_code" ltype="text" />
					</td>
					<td>
						<input type="button" value=" 查询（Q）" accessKey="I" onclick="query();"/>
					</td>
				</tr>
			</table>
			<div>
				<div id="maingrid"></div>
			</div>
		</div>
	</div>
</body>
</html>