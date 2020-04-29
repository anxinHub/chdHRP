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
<script type="text/javascript" src="<%=path%>/lib/hrp/acc/superReport/ktLayer.js"></script>
<script type="text/javascript" src="<%=path%>/lib/hrp/mat/mat.js?v=2020032601"></script>
<script type="text/javascript" src="<%=path%>/lib/hrp/mat/eva/matEvaTargetType.js?v=2020032601"></script>
<script type="text/javascript" src="<%=path%>/lib/hrp/mat/eva/matEvaTarget.js?v=2020032603"></script>
<script type="text/javascript" src="<%=path%>/lib/hrp/mat/eva/matEvaTargetDeduct.js?v=2020032601"></script>
<style>
	.info {
		padding: 5px 0px;
		position:relative;
		/*height: 500px;*/
		display: none;/*避免闪动初始规定不显示*/
	}
</style>
<script type="text/javascript">
	var mGrid, dGrid, tree;
	
	$(function (){
		$("#layout1").ligerLayout({ leftWidth : 255});
		$("#treeDiv").css("height", $(window).height() - 28);
		
		$("#toptoolbar").ligerToolBar({
			items: [{
				text: '新增', id: 'add_type', click: add_type, icon: 'add'
			},{ line:true },{
				text: '修改', id: 'edit_type', click: edit_type, icon: 'edit'
			},{ line:true },{
				text: '删除', id: 'remove_type', click: remove_type, icon: 'delete'
			},{ line:true },{ 
				text: '标准标度', id: 'set_scale', click: set_scale, icon: 'edit'
			}]
		});
		$(".l-toolbar-item").css({"padding-left": "18px", "padding-right": "5px"});
		loadTree();
		loadDict()
		loadMGrid();
		loadLayer();
	});
	
	//设置标准标度
	function set_scale(){
		parent.$.ligerDialog.open({ 
			title: '设置标准标度',
			url : 'hrp/mat/eva/target/matEvaScaleListPage.do?isCheck=false',
			height: $(window).height(),
			width: $(window).width() - 100,
			data:{}, 
			modal:true,showToggle:false,showMax:false,
			isResize:true, showMin: true,showMax: false, //开启最大化最小化按钮
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		}); 
	}
	
	//表单样式
	function loadDict(){
		$("#target_name").ligerTextBox({width:160});
		//autocomplete("#is_stop", "../../queryMatYearOrNo.do?isCheck=false", "id", "text", true, true);
		autoCompleteByData("#is_stop", yes_or_no.Rows, "id", "text", true, true);
	}
</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div class="l-layout" id="layout1" >
		<div position="left" >
			<div id="toptoolbar" ></div>
			<div id="treeDiv" class="l-scroll" style="width: 100%">
				<ul id="tree" style="overflow: auto;margin-top:3px;"/>
			</div>
		</div>
		<div position="center" >
			<div>
				<table cellpadding="0" cellspacing="0" class="l-table-edit">
					<tr>
						<td align="right" class="l-table-edit-td">
							指标信息：
						</td>
						<td align="left" class="l-table-edit-td">
							<input type="text" id="target_name" />
						</td>
						<td align="right" class="l-table-edit-td">
							是否停用：
						</td>
						<td align="left" class="l-table-edit-td">
							<input type="text" id="is_stop" />
						</td>
					</tr>
				</table>
				<div id="maingrid"></div>
			</div>
			<div id="floatDiv" style="height:0px;">
				<div id="floatQuery" class="info">
					<div id="detailgrid"></div>
				</div>
			</div>
		</div>
    </div>
</body>
</html>
