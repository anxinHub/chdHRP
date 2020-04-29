<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;" xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<jsp:include page="${path}/inc.jsp" />
<script type="text/javascript">


	var nature_code = '${nature_code}';
	var app_mod_code = '${app_mod_code}';

	if ("00" == app_mod_code && "56" == nature_code) {
		nature_code = "'05','06'";
		app_mod_code = "";
	} else {
		nature_code = "'" + nature_code + "'";
		app_mod_code = "'" + app_mod_code + "','99'"
	}
	
	
	var grid;
	var gridManager = null;
	
	
	//页面初始化
	$(function() {
		
		$("#acct_yearm").ligerTextBox({width : 160});autodate("#acct_yearm", "yyyymm");
		
		loadDict();//加载字典
		loadHead(null);//加载数据
		changeDate();//核算年月事件
		
		toolbar();//加载工具栏
		loadHotkeys();//加载快捷键

	});
	
	
	//查询
	function query() {//根据表字段进行添加查询条件

		grid.options.parms = [];
		grid.options.newPage = 1;
		
		//console.log(nature_code)

		grid.options.parms.push({
			name : 'nature_code',
			value : nature_code
		});
		
		grid.options.parms.push({
			name : 'app_mod_code',
			value : app_mod_code
		});
		
		grid.options.parms.push({
			name : 'acct_yearm',
			value : $("#acct_yearm").val()
		});

		var dept_id = liger.get("dept_id").getValue();

		if (dept_id) {

			grid.options.parms.push({
				name : 'dept_id',
				value : dept_id.split(",")[0]
			});

			grid.options.parms.push({
				name : 'dept_no',
				value : dept_id.split(",")[1]
			});
		}
		
    	grid.options.parms.push({name:'dept_kind_code',value:liger.get("dept_kind_code").getValue()}); 

		grid.loadData(grid.where);
	}
	
	
	//核算年月事件
	function changeDate(){
		
		var para = {
   			acct_yearm:$("#acct_yearm").val(),
   			nature_code:nature_code
		}

    	autocomplete("#dept_id","../queryDeptDictTime.do?&isCheck=false&nature_code=" + nature_code,"id","text",true,true,para);
	}
	
	
	//加载grid
	function loadHead() {

		grid = $("#maingrid").ligerGrid({
			columns : [],
			dataAction : 'server',dataType : 'server',usePager : true,
			url : 'queryHpmDeptBonusData'+'${app_mod_code}'+'${nature_code}'+'.do',
			width : '100%',height : '100%',checkbox : true,
			rownumbers : true,enabledEdit : true,delayLoad : true,
			selectRowButtonOnly : true//heightDiff: -10,
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	
	//工具栏
	function toolbar(){
       	var obj = [];
       	
       	obj.push({ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '生成（<u>G</u>）', id:'init', click: init, icon:'bookpen' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '计算（<u>C</u>）', id:'collect', click: collect,icon:'add' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '打印（<u>P</u>）', id:'print', click: print,icon:'print' });
       	obj.push({ line:true });
       	
       	$("#toptoolbar").ligerToolBar({ items: obj});
	}
  	
	
	//键盘事件
	function loadHotkeys(){
		hotkeys('Q',query);
		hotkeys('G',init);
		hotkeys('C',collect);
		hotkeys('P',print);
	}
	
	
	//打印
    function print() {

		if (grid.getData().length == 0) {
			$.ligerDialog.warn("请先查询数据！");
			return;
		}
		
		//获取标题
		var title = getTitle();

		/* var heads={
		  //"isAuto": true/false 默认true，页眉右上角默认显示页码
		  "rows": [
	      {"cell":0,"value":"科室名称："+liger.get("dept_id").getText(),"colSpan":"5"},
	      {"cell":3,"value":"项目名称："+liger.get("proj_id").getText(),"from":"right","align":"right","colSpan":"4"}
			  ]
		}; */
		var printPara={
				title: title,//标题
				columns: JSON.stringify(grid.getPrintColumns()),//表头
				class_name: "com.chd.hrp.hpm.service.AphiDeptBonusDataService",
				method_name: "queryHpmDeptBonusDataPrint",
				bean_name: "aphiDeptBonusDataService"/* ,
				heads: JSON.stringify(heads) *///表头需要打印的查询条件,可以为空
				/* foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 */
				};
		
		$.each(grid.options.parms,function(i,obj){
				printPara[obj.name]=obj.value;
		});
		
		officeGridPrint(printPara);
	}
	
	//获取页面标题
	function getTitle(){
		
		var natureCode = '${nature_code}';
		var modCode = '${app_mod_code}';
		
		var title = '';
		
		//if(modCode == '01'){//收支结余计奖核算
			
			if(natureCode == '01'){
				title = '医疗单元绩效工资核算';
			}
			
			if(natureCode == '02'){
				title = '护理单元绩效工资核算';
			}
			
			if(natureCode == '03'){
				title = '医技科室绩效工资核算';
			}
			
			if(natureCode == '04'){
				title = '药剂科室绩效工资核算';
			}
			
			if(natureCode == '05'){
				title = '医辅科室绩效工资核算';
			}
			
			if(natureCode == '06'){
				title = '管理科室绩效工资核算';
			}
			
			if(natureCode == '07'){
				title = '科室绩效工资核算';
			}
		//}
		
		
		
		/* if(modCode == '02'){//计件绩效计奖核算
			
			if(natureCode == '01'){
				title = '医疗单元绩效工资核算';
			}
			
			if(natureCode == '02'){
				title = '护理单元绩效工资核算';
			}
			
			if(natureCode == '03'){
				title = '医技科室绩效工资核算';
			}
			
			if(natureCode == '04'){
				title = '药剂科室绩效工资核算';
			}
			
			if(natureCode == '05'){
				title = '医辅科室绩效工资核算';
			}
			
			if(natureCode == '06'){
				title = '管理科室绩效工资核算';
			}
			
			if(natureCode == '07'){
				title = '科室绩效工资核算';
			}
		} */
		
		return title;
	}
	
	
	//查询动态列
	function grid_setColumns() {

		ajaxJsonObjectByUrl("queryHpmDeptBonusDataGrid"+'${app_mod_code}'+'${nature_code}'+".do?isCheck=false", {
			app_mod_code : app_mod_code,
			nature_code : nature_code
		},

		function(responseData) {

			if (responseData != null) {

				grid.set('columns', responseData);

				grid.reRender();
			}

		});

	}
	
	
	//生成
	function init() {

		var acct_yearm = $("#acct_yearm").val();
		var dept_id = liger.get("dept_id").getValue();

		if (!acct_yearm) {
			$.ligerDialog.warn('请选择年月');
			return false;
		}

		var param = {
			acct_yearm : acct_yearm,
			dept_id : dept_id ? dept_id.split(",")[0] : '',
			dept_no : dept_id ? dept_id.split(",")[1] : '',
			nature_code : nature_code,
			app_mod_code : app_mod_code,
			dept_kind_code : liger.get("dept_kind_code").getValue()
		}
		
		$.ligerDialog.confirm('是否覆盖当前重复数据', function(yes, value) {
			if (yes) {
				ajaxJsonObjectByUrl("initHpmDeptBonusData"+'${app_mod_code}'+'${nature_code}'+".do?isCheck=false", param, function(responseData) {
					if (responseData.state == "true") {
						query();
					}
				});
			}
		});

	}
	
	
	//计算
	function collect() {

		var year_month = $("#acct_yearm").val();
		var dept_id = liger.get("dept_id").getValue();
		
		
		if (year_month == '') {
			$.ligerDialog.warn('请选择年月');
			return false;
		}
		
		var param = {
			year_month : year_month,
			dept_id : dept_id ? dept_id.split(",")[0] : '',
			dept_no : dept_id ? dept_id.split(",")[1] : '',
			nature_code : nature_code,
			app_mod_code : app_mod_code,
			dept_kind_code : liger.get("dept_kind_code").getValue()
		}
		ajaxJsonObjectByUrl("collectHpmDeptBonusData"+'${app_mod_code}'+'${nature_code}'+".do", param, function(responseData) {
			if (responseData.state == "true") {
				query();
			}
		});

	}
	
	
	//加载下拉列表
	function loadDict() {

		var para = {
   			acct_yearm:$("#acct_yearm").val(),
   			nature_code:nature_code
		}
    	autocomplete("#dept_id","../queryDeptDictTime.do?&isCheck=false&nature_code=" + nature_code,"id","text",true,true,para);
		
    	autocomplete("#dept_kind_code","../queryDeptKindDict.do?isCheck=false","id","text",true,true);  
	}
	
	
	//修改
	function openFormula(obj1,obj2,obj3){

		var year_month = $("#acct_yearm").val();

		if (year_month == '') {
			$.ligerDialog.warn('请选择年月');
			return false;
		}
		
		var para = "?isCheck=false&viewFlag=1&acct_yearm="+year_month+"&dept_id="+obj1+"&dept_no="+obj2+"&item_code="+obj3;

    	parent.$.ligerDialog.open({ 
    		url: 'hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataForFormulaMainPage.do'+para,data:{}, 
    		height:$(window).height(),width: $(window).width(), title:'查看',modal:true,showToggle:false,
    		showMax:true,showMin: false,isResize:true,
    		parentframename: window.name,
    		buttons: [ 
    			{ text: '关闭', onclick: 
    				function (item, dialog) { 
    					dialog.close(); 
    				} 
    			} 
    		]
    	});
    }

</script>

</head>

<body style="padding: 0px; overflow: hidden;" onload="grid_setColumns();">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">核算年月：</td>
			<td align="left" class="l-table-edit-td"><input class="Wdate" name="acct_yearm" type="text" id="acct_yearm" ltype="text"
				validate="{required:true,maxlength:20}" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyyMM',onPicked: changeDate()})" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室名称：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室分类：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_kind_code" type="text" id="dept_kind_code" ltype="text"
				validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			
		</tr>

	</table>
	
	<div id="toptoolbar"></div>
	<div id="maingrid"></div>
</body>
</html>
