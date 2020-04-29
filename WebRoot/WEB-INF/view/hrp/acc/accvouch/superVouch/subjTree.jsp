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
<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<script src="<%=path%>/lib/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>
<script src="<%=path%>/lib/map.js"></script>
<script src="<%=path%>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/core/base.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>

<link rel="stylesheet" href="<%=path%>/lib/Z-tree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="<%=path%>/lib/Z-tree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=path%>/lib/Z-tree/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="<%=path%>/lib/Z-tree/js/jquery.ztree.exedit-3.5.js"></script>


<script type="text/javascript">
var dialog = frameElement.dialog; //调用页面的dialog对象(ligerui对象)
var grid=dialog.get("data").grid;
var activeCell=dialog.get("data").activeCell;
var kind_code=dialog.get("data").kind_code;
var activeCellVal=dialog.get("data").activeCellVal;
var vouchDate=dialog.get("data").vouch_date;
var allNodes = [];//所有节点
var setting = {
		view: {
			dblClickExpand: false
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			/*beforeClick: beforeClick,*/
			onClick: myClick,
			onDblClick: mySelect
		}
	};

$(function() {
	$(':button').ligerButton({width:80});
	loadTree();

	//注册键盘事件
	initKeyboard();
});


function loadTree(){
	
	vouchDate=vouchDate.substring(0,4);//获取凭证日期的年度
	if(vouchDate==""){
		return;
	}
	
	ajaxJsonObjectByUrl("queryAccVcouchSubjTree.do?isCheck=false",{kind_code:kind_code,acc_year: vouchDate},function (responseData){
	      tree=$.fn.zTree.init($("#tree"), setting, responseData.Rows);
	      allNodes = tree.getNodes();
		  

	      var $row=grid.getCellRow(activeCell);

		  var node_name = activeCellVal.trim();
		  
		  var node = tree.getNodeByParam("subj_name_all",node_name);
		  
		  if(!node)
		  	node = tree.getNodes()[0];
		  tree.selectNode(node);

		  var inputObj = $("#subj_code");
		  inputObj.val(node_name);
		  myQuery();
		  inputObj.focus();
		  
	});
}

function myClick(e, treeId, treeNode){
	if(treeNode.is_last==0){
		return;
	}
}

function myQuery(){
	var txt = $("#subj_code").val().trim();
	var node = tree.getNodesByParamFuzzy("id",txt);

	if(node.length===0)
		node = tree.getNodesByParamFuzzy("spell_code",txt);
	if(node.length===0)
		node = tree.getNodesByParamFuzzy("wbx_code",txt);
	if(node.length===0)
		node = tree.getNodesByParamFuzzy("subj_name_all",txt);

	tree.selectNode(node[0]);
}

//确定
function mySelect(){
	var node =tree.getSelectedNodes()[0];
	if(node==null || node.is_last==0){
		return;
	}
	var $row=grid.getCellRow(activeCell);
	var oldSubjId=$($row[0].cells[7]).find("div").text();
	if(activeCell.index()==18){
		//预算会计科目
		oldSubjId=$($row[0].cells[23]).find("div").text();
	}
	
	parent.setActiveCellSubj(node,$row.index(),activeCell.index(),oldSubjId);
///	var $rowData=grid.getRowData($row); 
	myClose();
}

function myClose(){
	
//	parent.callSaveVouchCheck("close",$("#rowNumber").val(),$("#cellNumber").val());

	setTimeout(function(){
		if(dialog!=null){
			var pGrid=dialog.get("data").grid;
			pGrid.editCell(); 
		}
		frameElement.dialog.close();
	},50);
}	


function treeSelect(node){

}

function initKeyboard(){
	$(document).keydown(function(e){
		var curNodes = tree.getSelectedNodes();
		var isInputFocus=$("#subj_code").is(":focus"); 
		var code = e.which;
		if(isInputFocus){
			//焦点在输入框
			if(code === 13 ){
				myQuery();
			}
		}else{
			//焦点不在输入框
			switch (code) {
				case 13:
					mySelect();
					break;
				case 37: //左
					parentNode(curNodes);
					break;
				case 38: //上
					prevNode(curNodes);
					return false;
					break;
				case 39: //右
					childrenNode(curNodes);
					break;
				case 40: //下
					nextNode(curNodes);
					return false;
					break;
				default:
					break;
			}
		}
		
	});

	$("#subj_code").focus(function(){
		var _this = $(this)
		setTimeout(function(){
			_this.select();
		},0)
		
	})

}

function parentNode(nodes){
	var select_node = nodes[0].getParentNode();
	if(select_node){
		tree.expandNode(select_node,false,false,false,false);
		tree.selectNode(select_node);
	}else{
		$("#subj_code").focus();
	}
}
function childrenNode(nodes){
	if(nodes[0].children){
		var select_node = nodes[0].children[0];
		tree.selectNode(select_node);
	}
}
function nextNode(nodes){
	var select_node = nodes[0].getNextNode();
	tree.selectNode(select_node);
}
function prevNode(nodes){
	var select_node = nodes[0].getPreNode();
	tree.selectNode(select_node);
}
</script>

</head>

<body style="padding: 0px;overflow:hidden;background:#FFFFDF;"  onload="">
	 <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%"  style="height:40px;border-bottom:solid 1px #333333;">
   		<tr>
            <td align="left" class="l-table-edit-td">
	            <input type="text" id="subj_code"  name="subj_code" ltype="text" style="width:312px" title="回车定位到科目节点"/>
	            <!-- input type="button" accessKey="Q" value="回车定位(Q)" onclick="myQuery();"/-->
	            <input type="button" accessKey="S" value="确定（S）" onclick="mySelect();"/>
            </td>
            <td align="right" >
	            <input type="button" accessKey="C" value="关闭（C）" onclick="myClose();"/>
	            &nbsp;
            </td>
   		</tr>
   </table>
   
    <div style="width:590px; height:490px;overflow:auto;" id="divTree" >
      <ul class="ztree" id="tree" style="font-size:15px"></ul>
	</div>
			
</body>
</html>
