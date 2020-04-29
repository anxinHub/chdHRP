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
        loadDict();//加载字典
    	loadHead(null);	//加载数据
    	
    	toolbar();//加载工具栏
    	loadHotkeys();//加载快捷键
    });
    
    
    
    //查询
    function  query(){
    	
    	grid.options.parms=[];
    	grid.options.newPage=1;
    	
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'dept_code',value:liger.get("dept_code").getValue()}); 
    	grid.options.parms.push({name:'duty_code',value:liger.get("duty_code").getValue()}); 
    	grid.options.parms.push({name:'formula_code',value:liger.get("formula_code").getValue()});

    	//加载查询条件
    	grid.loadData(grid.where);
	}
	
    
    //加载grid
	function loadHead(){
		grid = $("#maingrid").ligerGrid({
			columns: [ 
				{ display: '科室编码', name: 'dept_code', align: 'left'},
				{ display: '科室名称', name: 'dept_name', align: 'left'},
                { display: '职务名称', name: 'duty_name', align: 'left'},
                { display: '项目名称', name: 'item_name', align: 'left'},
                { display: '公式名称', name: 'formula_name', align: 'left'},
                { display: '计算公式', name: 'formula_method_chs', align: 'left'}
            ],
            dataAction: 'server',dataType: 'server',usePager:true,url:'queryHpmEmpSchemeSeq.do',
            width: '100%',height: '100%',rownumbers:true,selectRowButtonOnly:true,//heightDiff: -10,
    		onDblClickRow : function (rowdata, rowindex, value){
    			openUpdate(rowdata.temp);//实际代码中temp替换主键
    		} 
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
	
	
	//工具栏
	function toolbar(){
       	var obj = [];
       	
       	obj.push({ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '审核（<u>C</u>）', id:'audit', click: audit,icon:'audit' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '打印（<u>P</u>）', id:'print', click: print,icon:'print' });
       	obj.push({ line:true });
       	
       	$("#toptoolbar").ligerToolBar({ items: obj});
	}
  	
	//键盘事件
	function loadHotkeys(){
		
		hotkeys('Q',query);
		
		hotkeys('C',audit);
		
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
				title: "分配方案审核",//标题
				columns: JSON.stringify(grid.getPrintColumns()),//表头
				class_name: "com.chd.hrp.hpm.service.AphiEmpSchemeService",
				method_name: "queryEmpSchemePrint",
				bean_name: "aphiEmpSchemeService"/* ,
				heads: JSON.stringify(heads) *///表头需要打印的查询条件,可以为空
				/* foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 */
				};
		
		$.each(grid.options.parms,function(i,obj){
				printPara[obj.name]=obj.value;
		});
		
		officeGridPrint(printPara);
	}
	
	
	
	//审核
	function audit(){
		$.ligerDialog.open({
			url : 'hpmEmpSchemeSeqAuditPage.do?isCheck=false',
			height : 300,width : 500,title : '审核',modal : true,
			showToggle : false,showMax : false,showMin : true,
			isResize : true,
			buttons : [
				{text : '确定',onclick : 
					function(item, dialog) {
						dialog.frame.auditAphiEmpSchemeSeq();
					},
					cls : 'l-dialog-btn-highlight'
				}, 
				{text : '取消',onclick : 
					function(item, dialog) {
						dialog.close();
					}
				} 
			]
		});
	}
	
	
	//字典下拉框
    function loadDict(){
    	
        autocomplete("#dept_code","../queryDeptDictByPerm.do?isCheck=false","id","text",true,true); 
        autocomplete("#duty_code","../queryEmpDutyDict.do?isCheck=false","id","text",true,true); 
        autocomplete("#formula_code","../queryFormula.do?isCheck=false","id","text",true,true); 
        
    }   
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<table cellpadding="0" cellspacing="0" class="l-table-edit">

		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室名称：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_code" type="text" id="dept_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">职务名称：</td>
			<td align="left" class="l-table-edit-td"><input name="duty_code" type="text" id="duty_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">公式名称：</td>
			<td align="left" class="l-table-edit-td"><input name="formula_code" type="text" id="formula_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
		</tr>
	</table>

	<div id="toptoolbar"></div>
	<div id="maingrid"></div>

</body>
</html>
