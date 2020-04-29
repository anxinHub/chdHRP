<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"  xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">


    var grid;
    var gridManager = null;
    var userUpdateStr;
    
    //页面初始化
    $(function (){
        loadDict()//加载下拉框
    	loadHead(null);	//加载数据
    	
    	toolbar()//加载工具栏
    	loadHotkeys();//加载快捷键
    });
    
    
    //查询
    function  query(){
    	
    	grid.options.parms=[];
    	grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'dept_id',value:liger.get("dept_code").getValue() ? liger.get("dept_code").getValue().split(",")[0] : ''}); 
    	grid.options.parms.push({name:'dept_no',value:liger.get("dept_code").getValue() ? liger.get("dept_code").getValue().split(",")[1] : ''}); 
    	grid.options.parms.push({name:'duty_code',value:liger.get("duty_code").getValue()}); 
    	grid.options.parms.push({name:'formula_code',value:liger.get("formula_code").getValue()}); 
    	grid.options.parms.push({name:'acct_year_month',value:$("#acct_year_month").val()}); 
    	//加载查询条件
    	grid.loadData(grid.where);
	}
	
    
    //加载grid
	function loadHead(){
		grid = $("#maingrid").ligerGrid({
			columns: [ 
				{ display: '核算年度', name: 'acct_year', align: 'left'},
				{ display: '核算月份', name: 'acct_month', align: 'left'},
                { display: '科室编码', name: 'dept_code', align: 'left'},
				{ display: '科室名称', name: 'dept_name', align: 'left'},
                { display: '职务名称', name: 'duty_name', align: 'left'},
                { display: '项目名称', name: 'item_name', align: 'left'},
                { display: '公式名称', name: 'formula_name', align: 'left',
					render : function(rowdata, rowindex, value) {
						return "<a href=javascript:openUpdate('"
							+ rowdata.formula_code + "|"
							+ rowdata.group_id + "|"
							+ rowdata.hos_id + "|"
							+ rowdata.copy_code + "')>"
							+ rowdata.formula_name + "</a>";
					}
				},
                { display: '计算公式', name: 'formula_method_chs', align: 'left'},
                { display: '方案序号', name: 'sub_scheme_seq_no', align: 'left'}
			],
            dataAction: 'server',dataType: 'server',usePager:true,url:'queryHpmSubSchemeConfForEmp.do',
            width: '100%',height: '100%',   checkbox: true,rownumbers:true,selectRowButtonOnly:true,//heightDiff: -10,
            delayLoad:true
		});
        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    
  	//工具栏
	function toolbar(){
       	var obj = [];
       	
       	obj.push({ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '打印（<u>P</u>）', id:'print', click: print,icon:'print' });
       	obj.push({ line:true });
       	
       	$("#toptoolbar").ligerToolBar({ items: obj});
	}
  	
	//键盘事件
	function loadHotkeys(){
		hotkeys('Q',query);
		hotkeys('P',print);
	}
    
	
	//打印
    function print() {

		if (grid.getData().length == 0) {
			$.ligerDialog.warn("请先查询数据！");
			return;
		}

		/* var heads={
		  //"isAuto": true/false 默认true，页眉右上角默认显示页码
		  "rows": [
	      {"cell":0,"value":"科室名称："+liger.get("dept_id").getText(),"colSpan":"5"},
	      {"cell":3,"value":"项目名称："+liger.get("proj_id").getText(),"from":"right","align":"right","colSpan":"4"}
			  ]
		}; */
	
		var printPara={
				title: "分配方案查询",//标题
				columns: JSON.stringify(grid.getPrintColumns()),//表头
				class_name: "com.chd.hrp.hpm.service.AphiSubSchemeConfService",
				method_name: "queryHpmSubSchemeConfForEmpPrint",
				bean_name: "aphiSubSchemeConfService"/* ,
				heads: JSON.stringify(heads) *///表头需要打印的查询条件,可以为空
				/* foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 */
				};
		
		$.each(grid.options.parms,function(i,obj){
				printPara[obj.name]=obj.value;
		});
		
		officeGridPrint(printPara);
	}

    
    /* function openUpdate(obj){
    	//实际代码中&temp替换主键
    	$.ligerDialog.open({ url: 'empSchemeUpdatePage.do?isCheck=false&temp='+obj,data:{}, height: 500,width: 500, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveEmpScheme(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    } */
    
    //计算公式修改页跳转
    function openUpdate(obj) {

		var vo = obj.split("|");
		var parm = 
			"&formula_code=" + vo[0] + 
			"&group_id" + vo[1] + 
			"&hos_id" + vo[2] + 
			"&copy_code" + vo[3]

		parent.$.ligerDialog.open({
			url : 'hrp/hpm/hpmformula/hpmFormulaUpdatePage.do?isCheck=false&' + parm,
			data : {},
			height: $(window).height(),
			width: $(window).width(),
			title : '修改',
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name,
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.saveHpmFormula();
				},
				cls : 'l-dialog-btn-highlight'
			}, {
				text : '取消',
				onclick : function(item, dialog) {
					dialog.close();
				}
			} ]
		});
	}

	//字典下拉框
    function loadDict(){
		autocomplete("#dept_code","../queryDeptDictByPerm.do?isCheck=false","id","text",true,true); 
        autocomplete("#duty_code","../queryEmpDutyDict.do?isCheck=false","id","text",true,true); 
        autocomplete("#formula_code","../queryFormula.do?isCheck=false","id","text",true,true); 
            
        $("#acct_year_month").ligerTextBox({width:160 });
        autodate("#acct_year_month",'yyyyMM');
	}   
	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">核算年月：</td>
			<td align="left" class="l-table-edit-td"><input name="acct_year_month" type="text" id="acct_year_month" ltype="text" 
				 class="Wdate" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyyMM'})"/>
			</td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室名称：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_code" type="text" id="dept_code" ltype="text" /></td>
			<td align="left"></td>
		</tr>
		
		<tr>
			
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">职务名称：</td>
			<td align="left" class="l-table-edit-td"><input name="duty_code" type="text" id="duty_code" ltype="text" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">公式名称：</td>
			<td align="left" class="l-table-edit-td"><input name="formula_code" type="text" id="formula_code" ltype="text" /></td>
			<td align="left"></td>
		</tr>
	</table>

	<div id="toptoolbar"></div>
	<div id="maingrid"></div>
</body>
</html>
