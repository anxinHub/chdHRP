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
	var type_code='${type_code}';
	$(function() {
		loadDict()//加载下拉框
		//加载数据
		loadHead(null);
		loadHotkeys();
		$("#dept_id").ligerTextBox({
			width : 160
		});
		$("#type_code").ligerTextBox({
			width : 160
		});
		query();
	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'dept_id',
			value : $("#dept_id").val()
		});
		grid.options.parms.push({
			name : 'type_code',
			value : '${type_code}'
		});

		//加载查询条件
		grid.loadData(grid.where);
	}
	//获取查询条件的数值
	function f_getWhere() {
		if (!grid)
			return null;
		var clause = function(rowdata, rowindex) {
			
			if ($("#dept_id").val() != "") {
				return rowdata.dept_id.indexOf($("#dept_id").val()) > -1;
			}
		};
		return clause;
	}

	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '科室编码',
				name : 'dept_code',
				align : 'left'
			}, {
				display : '科室名称',
				name : 'dept_name',
				align : 'left'
			} ],
			dataAction : 'server',
			dataType : 'server',
			usePager : true,
			url : 'queryCostParaDeptDict.do?isCheck=false',
			width : '100%',
			height : '100%',
			checkbox : true,
			rownumbers : true,
			delayLoad:true,
			selectRowButtonOnly : true,//heightDiff: -10,
			toolbar : {
				items : [ {
					text : '查询（<u>E</u>）',
					id : 'search',
					click : query,
					icon : 'search'
				}, ]
			}
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}
	function loadDict() {
		//字典下拉框
		var dept_param = {
				type_code : "('"+type_code+"')"
			};
			//字典下拉框
			autocomplete("#dept_id", "../queryDeptDictCode.do?isCheck=false", "id",
					"text", true, true, dept_param);
	}
	//键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
	}
	function saveDeptDict(){
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
						'${acc_year }'   +"@"+  
						'${acc_month  }'  +"@"+  
							'${bill_code }'  +"@"+
						this.dept_id +"@"+  
						this.dept_no+"@"+
						this.type_code+"@"+
						this.natur_code+"@"+
						this.para_code
				)
            });
            ajaxJsonObjectByUrl("addCostParaDept.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
        		if(responseData.state=="true"){
        			query();
        		}
        	});
        }
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">服务科室：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_no"
				type="text" id="dept_id" ltype="text"
				validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
		</tr>
	</table>

	<div id="maingrid"></div>
</body>
</html>
