<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<script src="<%=path%>/lib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script>
<script src="<%=path%>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>
<script src="<%=path%>/lib/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script type="text/javascript">
	var grid;
	var gridManager = null;

	$(function() {
		autocomplete("#subj_level","../../querySubjLevel.do?isCheck=false","id","text",true,true,'',false,'',100);
		autocomplete("#subj","../../querySubj.do?isCheck=false","id","text",true,true,"","","",260,"",400);
		loadHead(null); //加载数据
	});
	//查询
	function query() {
		var subj_code = $("#subj").val();
		subj_code = subj_code.split(" ")[0]
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({ name : 'subj', value : subj_code});
		grid.options.parms.push({ name : 'subj_level', value : $("#subj_level").val()});
		//加载查询条件
		grid.loadData(grid.where);
	}

	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [
				{display : '科目编码',name : 'subj_code',align : 'left',width : 140}, 
				{display : '科目名称',name : 'subj_name_all',align : 'left',width:460}, 
			],
				dataAction : 'server',dataType : 'server',usePager : true,
				url : 'queryAccDifferSubjForAdd.do?isCheck=false&diff_type_code=${diff_type_code}',width : '100%',
				height : '100%',checkbox : true,rownumbers : true,selectRowButtonOnly : true,//heightDiff: -10,
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	function onUnSelectRow(rowdata, rowid, rowobj){
		var parent_code = rowdata.subj_code;
		
		var data = gridManager.getData();
		$(data).each(function(d){
			var subj_code = this.subj_code;
			if(subj_code != parent_code){
				var this_begin = subj_code.substr(0,parent_code.length);
				if(this_begin == parent_code){
					gridManager.unselect(this.ROW_ID-1)
				}
			}
		});
	}
	
	function onSelectRow(rowdata, rowid, rowobj){
		var parent_code = rowdata.subj_code;
		
		var data = gridManager.getData();
		$(data).each(function(d){
			var subj_code = this.subj_code;
			if(subj_code != parent_code){
				var this_begin = subj_code.substr(0,parent_code.length);
				if(this_begin == parent_code){
					gridManager.select(this.ROW_ID-1)
				}
			}
		});
	}
	
	
	function save(){
		var data = gridManager.getSelectedRows();
		if(data.length == 0){
			$.ligerDialog.error('请先选择科目');
			return;
		}
		
		var subj_code_list = [];
		$(data).each(function(){
			subj_code_list.push(this.subj_code);
		})
		var formPara={
           diff_item_code :'${diff_item_code}',
           diff_type_code :'${diff_type_code}',
           subj_code_list : JSON.stringify(subj_code_list)
         };
        
        ajaxJsonObjectByUrl("addAccDifferSubj.do?isCheck=false",formPara,function(responseData){
            if(responseData.state=="true"){
                parent.query();
                query();
            }
        });
	}
	
</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div>
		<table>
			<tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">会计科目：</td>
                <td align="left" class="l-table-edit-td"><input type="text" id="subj" ltype="text" /></td>
                <td align="left"></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科目级次：</td>
                <td align="left" class="l-table-edit-td"><input name="subj_level" type="text" id="subj_level" ltype="text" /></td>
                 <td align="left"></td>
                <td align="left"></td>
            	<td>
            		<input type="button" class="l-button l-button-submit" name="查询" value="查询" onclick="query()">
            	</td>
            </tr>
		</table>
	</div>
            
	<div class="l-layout" id="layout1" style="height: 100%;" ligeruiid="layout1">
		<div class="l-layout-center" style="top: 0px; width: 685px; height: 100%;">
			<div class="l-layout-header">科目</div>
			<div title="" class="l-layout-content" style="height: 100%;" position="center">
				<div id="maingrid"></div>
			</div>
		</div>
	</div>

</body>
</html>
