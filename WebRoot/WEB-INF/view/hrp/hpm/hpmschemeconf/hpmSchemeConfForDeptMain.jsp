<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;" xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">

	var grid;
	var gridManager = null;
	
	
	//页面初始化
	$(function() {
		loadHead(null);//加载数据
		loadDict();//加载字典
		
		toolbar();//加载工具栏
		loadHotkeys();//加载快捷键
	});
	
	
	//查询
	function query() {
		
		grid.options.parms = [];
		grid.options.newPage = 1;
		
		var dept_id = liger.get("dept_id").getValue();
		if(dept_id){
			grid.options.parms.push({name : 'dept_id',value : dept_id.split(",")[0]});
			grid.options.parms.push({name : 'dept_no',value : dept_id.split(",")[1]});
		}
		
		grid.options.parms.push({name : 'year_month',value : $("#year_month").val()}); 
		grid.options.parms.push({name : 'scheme_seq_no',value :  liger.get("scheme_seq_no").getValue()});
		grid.options.parms.push({name : 'dept_kind_code',value :  liger.get("dept_kind_code").getValue()});
		grid.options.parms.push({name : 'nature_code',value :  liger.get("nature_code").getValue() });
		
		grid.loadData(grid.where);

	}
	
	function  Sorts(a,b){
		return a.Sort-b.Sort;
	}
	 
	//加载grid
	function loadHead(){
	    	var url="queryHpmSchemeConfGridForDept.do?isCheck=false";
	    	$.getJSON(url,{ page: 1,pagesize:10 ,Rnd: Math.random()},
	    		function(json){
	    		var colunmName="";
	    		var result=0;
	    		json.sort(Sorts);
	    		//console.log(json)
	    		colunmName+=",{name:'dept_code',display:'科室编码',align:'left',width:120}";
	    		colunmName+=",{name:'dept_name',display:'科室名称',align:'left',width:280}";
	    		for ( var i = 0; i < json.length; i++) {
					colunmName += ",{name:'formula_method_chs" + i
							+ "',display:'" + json[i].text
							+ "',align : 'left',width:240,render:function(rowdata, rowindex,value,col){"
							+ "if(rowdata[col.name]){"
							//+ "return \'<a href=# onclick=openFormulaUpdate(\""+json[i].id+"\");>\'+rowdata.formula_method_chs"+ i + "+'</a>\';" 
							+ "return \'<a href=# onclick=openFormulaUpdate(\' + \'\"\'" + "+rowdata.formula_code" + i + "+ \'\"\'+\');>\'+rowdata.formula_method_chs"+ i + "+'</a>\';"
							+ "}"
							+ "}}";
				};
	    		eval(	
	    				"grid=$('#grid').ligerGrid({"
	    				+"columns: ["+colunmName+"],"
	    				+"dataAction:'server',"
	    				+"dataType:'server',"
	    				+"usePager:true,"
	    				+"checkbox: true,"
	    				+"height: '100%',"
						+"url:'queryHpmSchemeConfForDept.do',"
						+"checkbox: false,rownumbers:true,enabledEdit: true,delayLoad:true"
						+"});"
	    				+"gridManager = $('#maingrid').ligerGetGridManager();"
	    			);
	    	});
	    }
	
	
	//工具栏
	function toolbar(){
		
       	var obj = [];
       	
       	obj.push({ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' });
       	obj.push({ line:true });
       	
       	$("#toptoolbar").ligerToolBar({ items: obj});
	} 
	
	//键盘事件
	function loadHotkeys(){
		hotkeys('Q',query);
	}
	
	//计算公式页跳转	
	function openFormulaUpdate(obj){
	    	//实际代码中&temp替换主键
		parent.$.ligerDialog.open({ 
			url: 'hrp/hpm/hpmformula/hpmFormulaUpdatePage.do?isCheck=false&formula_code='+obj,data:{}, 
	    	height:$(window).height(),width: $(window).width(), title:'修改',modal:true,showToggle:false,
	    	showMax:true,showMin: false,isResize:true,
	    	parentframename: window.name,
	    	buttons: [ 
	    		{text: '确定', onclick: 
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
	
	
	//字典下拉框
	function loadDict() {
		 
		autocomplete("#scheme_seq_no","../querySchemeSeq.do?isCheck=false", "id", "text", true,true,"",true);
		autocomplete("#dept_id","../queryDeptDictByPerm.do?isCheck=false","id","text",true,true);
		autocomplete("#nature_code","../queryDeptNatureDict.do?isCheck=false","id","text",true,true);
    	autocomplete("#dept_kind_code","../queryDeptKindDict.do?isCheck=false","id","text",true,true); 
    	
    	$("#year_month").ligerTextBox({width:160});autodate("#year_month","yyyymm");
    	
	}
</script>
</head>
<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">核算年月：</td>
			<td align="left" class="l-table-edit-td"><input class="Wdate" style="width: 160px;" name="year_month" type="text" id="year_month" ltype="text"
				onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyyMM'})" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室分类：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_kind_code" type="text" id="dept_kind_code" ltype="text" /></td>
			<td align="left"></td>

			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室名称：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" /></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">方案序号：</td>
			<td align="left" class="l-table-edit-td"><input name="scheme_seq_no" type="text" id="scheme_seq_no" ltype="text" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室性质：</td>
			<td align="left" class="l-table-edit-td"><input name="nature_code" type="text" id="nature_code" ltype="text" /></td>
			<td align="left"></td>

		</tr>
	</table>
	
	<div id="toptoolbar"></div>
	<div id="grid"></div>
</body>
</html>