<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"  xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc.jsp"/>
<script type="text/javascript">


	var grid;
	var gridManager = null;
	var dialog ; 
	
	
	//页面初始化
	$(function() {
		autocomplete("#dept_kind_code","../queryDeptKindDict.do?isCheck=false", "id", "text", true,true);
		autocomplete("#dept_id","../queryDeptDictByPerm.do?isCheck=false", "id","text", true, true);
		autocomplete("#nature_code", "../queryDeptNatureDict.do?isCheck=false","id", "text", true, true);

		loadHead(null);//加载grid
		toolbar();//加载工具栏
		loadHotkeys();//加载快捷键
	});
	
	
	//查询
	function query() {//根据表字段进行添加查询条件

		grid.options.parms = [];grid.options.newPage = 1;
		
		var dept_id = liger.get("dept_id").getValue();
		if(dept_id){
			grid.options.parms.push({name : 'dept_id',value : dept_id.split(",")[0]});
			grid.options.parms.push({name : 'dept_no',value : dept_id.split(",")[1]});
		}
		
		grid.options.parms.push({name : 'dept_kind_code',value : liger.get("dept_kind_code").getValue()});
		grid.options.parms.push({name : 'nature_code',value : liger.get("nature_code").getValue()});

		grid.loadData(grid.where);//加载查询条件
		
	}
	
	function Sorts(a, b) {
		return a.Sort - b.Sort;
	}
	
	//加载grid
	function loadHead() {
		$.getJSON("querySchemeSeqGrid.do?isCheck=false",{page : 1,pagesize : 10,Rnd : Math.random()},
				function(json) {
					var colunmName = "";
					var result = 0;
					json.sort(Sorts);
					
					colunmName += ",{name:'dept_code',display:'科室编码',align:'left',width:120}";
					colunmName += ",{name:'dept_name',display:'科室名称',align:'left',width:200}";
					
					for ( var i = 0; i < json.length; i++) {
						colunmName += ",{name:'formula_method_chs" + i
						+ "',display:'" + json[i].text
						+ "',align : 'left',width:240,render:function(rowdata, rowindex,value,col){"
							+ "if(rowdata[col.name]){"
								+ "return \'<a href=# onclick=openFormulaUpdate(\' + \'\"\'" 
								+ "+rowdata.formula_code" + i + "+ \'\"\'+\');>\'+rowdata.formula_method_chs"+ i + "+'</a>\';" 
							+ "}"
						+ "}}";
					};
							//console.log(colunmName)
					eval("grid=$('#maingrid').ligerGrid({"
							+"columns: [" + colunmName + "],"
							+"dataAction:'server',"
							+"dataType:'server',"
							+"usePager:true,"
							+"checkbox: true,"
							+"height: '100%',"
							+"url:'queryHpmSchemeSeq.do',"
							+"checkbox: false,rownumbers:true,enabledEdit: true"
							+ "});" 
							+"gridManager = $('#maingrid').ligerGetGridManager();"
							+ "formatYesNo();");
					}
		);
	}
	
	//计算公式修改页
	function openFormulaUpdate(obj){
		
    	//实际代码中&temp替换主键
    	parent.$.ligerDialog.open({ 
    		url: 'hrp/hpm/hpmformula/hpmFormulaUpdatePage.do?isCheck=false&formula_code='+obj,data:{}, 
    		height:$(window).height(),width: $(window).width(), title:'修改',modal:true,showToggle:false,
    		showMax:true,showMin: false,isResize:true,
    		parentframename: window.name,
    		buttons: [ { 
    			text: '确定', onclick: 
    				function (item, dialog) { 
    					dialog.frame.saveHpmFormula(); 
    				},cls:'l-dialog-btn-highlight' 
    			}, 
    			{ text: '取消', onclick: 
    				function (item, dialog) { 
    					dialog.close(); 
    				} 
    			} 
    		] 
    	});
    }
	
	//工具栏
	function toolbar(){
       	var obj = [];
       	
       	obj.push({ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '审核（<u>C</u>）', id:'audit', click: audit, icon:'audit' });
       	obj.push({ line:true });
       	
       	$("#toptoolbar").ligerToolBar({ items: obj});
	}
	
	//键盘事件
	function loadHotkeys(){
		hotkeys('Q',query);
		hotkeys('C',audit);
	}
	
	//审核
	function audit(){
		$.ligerDialog.open({
			url : 'auditHpmSchemeSeqPage.do?isCheck=false',
			title : '审核',height : 300,width : 500,modal : true,
			showToggle : false,showMax : false,showMin : true,isResize : true,
			buttons : [ 
				{text : '确定',onclick : 
					function(item, dialog) {
						dialog.frame.auditHpmSchemeSeq();
					},cls : 'l-dialog-btn-highlight'
				}, 
				{text : '取消',onclick : 
					function(item, dialog) {
						dialog.close();
					}
				} 
			]
		});
	}

	function openUpdate(obj, obj1, obj2, obj3) {
		//实际代码中&temp替换主键
		$.ligerDialog.open({
			url : 'hpmSchemeUpdatePage.do?isCheck=false&dept_kind_code='
					+ obj + '&dept_id=' + obj1 + '&item_code=' + obj2
					+ '&formula_code=' + obj3,
			data : {},
			title : '修改',height : 550,width : 1000,modal : true,
			showToggle : false,showMax : false,showMin : false,isResize : true,
		});

	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室分类：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_kind_code" type="text" id="dept_kind_code" ltype="text"
				validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>

			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室性质编码：</td>
			<td align="left" class="l-table-edit-td"><input name="nature_code" type="text" id="nature_code" ltype="text" validate="{required:true,maxlength:50}" /></td>
			<td align="left"></td>

			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室名称：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
		</tr>
	</table>
	
	<div id="toptoolbar"></div>
	<div id="maingrid"></div>
</body>
</html>
