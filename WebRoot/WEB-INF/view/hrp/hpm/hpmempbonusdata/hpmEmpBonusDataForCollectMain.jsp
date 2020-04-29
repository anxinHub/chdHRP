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
    	loadHead(null);	//加载grid
    	
    	toolbar();//加载工具栏
    	loadHotkeys();//加载快捷键
    });
    
    //查询
    function  query(){
    	grid.options.parms=[];
    	grid.options.newPage=1;
    	
        //根据表字段进行添加查询条件
        grid.options.parms.push({name:'acct_yearm',value:$("#acct_year_month").val()}); 
    	grid.options.parms.push({name:'dept_id',value:liger.get("dept_code").getValue() == "" ? "" :liger.get("dept_code").getValue().split(",")[0]}); 
    	grid.options.parms.push({name:'dept_no',value:liger.get("dept_code").getValue() == "" ? "" :liger.get("dept_code").getValue().split(",")[1]}); 
    	grid.options.parms.push({name:'duty_code',value:liger.get("duty_code").getValue()}); 
    	grid.options.parms.push({name:'emp_name',value:$("#emp_name").val()}); 
    	
    	//加载查询条件
    	grid.loadData(grid.where);
	}
	
    
    //加载grid
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [ 
				{ display: '核算年月', name: 'acct_year_month', align: 'left',render: 
					function (rowdata, rowindex, value){
						return rowdata.acct_year + rowdata.acct_month;
					}
				},
				
				{ display: '科室编码', name: 'dept_code', align: 'left'},
				
				{ display: '科室名称', name: 'dept_name', align: 'left'},
				{ display: '职工编码', name: 'emp_code', align: 'left'},
				{ display: '职工名称', name: 'emp_name', align: 'left'},
				{ display: '项目名称', name: 'item_name', align: 'left'},
				{ display: '奖金额', name: 'bonus_money', align: 'right'}
			],
			
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryHpmEmpBonusDataForCollect.do',
            width: '100%',height: '100%',   checkbox: true,rownumbers:true,
            selectRowButtonOnly:true,//heightDiff: -10,
 			onDblClickRow : function (rowdata, rowindex, value)
			{
					//openUpdate(rowdata.temp);//实际代码中temp替换主键
			} 
        });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    
  	//工具栏
	function toolbar(){
       	var obj = [];
       	
       	obj.push({ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '生成（<u>G</u>）', id:'create', click: create,icon:'bookpen' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '计算（<u>C</u>）', id:'calculation', click: collect,icon:'add' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '打印（<u>P</u>）', id:'print', click: print,icon:'print' });
       	obj.push({ line:true });
       	
       	$("#toptoolbar").ligerToolBar({ items: obj});
	}
  	
	//键盘事件
	function loadHotkeys(){
		
		hotkeys('Q',query);
		
		hotkeys('G',create);
		
		hotkeys('C',collect);
		
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
				title: "二次分配核算",//标题
				columns: JSON.stringify(grid.getPrintColumns()),//表头
				class_name: "com.chd.hrp.hpm.service.AphiEmpBonusDataService",
				method_name: "queryEmpBonusDataPrint",
				bean_name: "aphiEmpBonusDataService"/* ,
				heads: JSON.stringify(heads) *///表头需要打印的查询条件,可以为空
				/* foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 */
				};
		
		$.each(grid.options.parms,function(i,obj){
				printPara[obj.name]=obj.value;
		});
		
		officeGridPrint(printPara);
	}
    
    
    //生成
    function create(){
    	var year_month = $("#acct_year_month").val();
    	//var sub_scheme_seq_no = $("#sub_scheme_seq_no").val();
    	
		if(year_month == ""){
    		$.ligerDialog.warn('请选择生成年月');
    		return false;
    	}
		
		/* if(sub_scheme_seq_no == ""){
    		$.ligerDialog.warn('请选择方案序号 ');
    		return false;
    	} */
		
        var param={
				year_month:year_month
        		//sub_scheme_seq_no:sub_scheme_seq_no
		}

        $.ligerDialog.confirm('确定生成?', function (yes){
        	if(yes){
        		ajaxJsonObjectByUrl("createHpmEmpBonusDataForCollect.do",param,function (responseData){
            		if(responseData.state=="true"){
            			query();
            		}
            	});    
        	}
        });
    }
    
    
    //计算
    function collect(){
    	var year_month = $("#acct_year_month").val();
    	
		if(year_month == ""){
    		$.ligerDialog.warn('请选择核算年月');
    		return false;
    	}
		
        var param={
			year_month:year_month,
			dept_code:liger.get("dept_code").getValue(),
			duty_code:liger.get("duty_code").getValue()
		}
        
        $.ligerDialog.confirm('确定计算?', function (yes){
        	if(yes){
            	ajaxJsonObjectByUrl("collectHpmEmpBonusDataForCollect.do",param,function (responseData){
            		if(responseData.state=="true"){
            			query();
            		}
            	});
        	}
        });
    }
	
    
    //修改
    function openUpdate(obj){
    	//实际代码中&temp替换主键
    	$.ligerDialog.open({ 
    		url: 'empBonusDataUpdatePage.do?isCheck=false&temp='+obj,data:{}, 
    		height: 500,width: 500, title:'修改',modal:true,showToggle:false,
    		showMax:false,showMin: false,isResize:true,
    		buttons: [ 
    			{ text: '确定', onclick: 
    				function (item, dialog) { 
    					dialog.frame.saveEmpBonusData(); 
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
    function loadDict(){
    	autocomplete("#dept_code","../queryDeptDictByPerm.do?isCheck=false","id","text",true,true);
    	autocomplete("#duty_code","../queryEmpDutyDict.do?isCheck=false","id","text",true,true);
    	
    	$("#acct_year_month").ligerTextBox({width:160 });
    	$("#emp_name").ligerTextBox({width:160 });
    	autocomplete("#sub_scheme_seq_no","../querySubSchemeSeqDict.do?isCheck=false","id","text",true,true); 
	} 
    
    </script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<table cellpadding="0" cellspacing="0" class="l-table-edit">

		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">核算年月：</td>
			<td align="left" class="l-table-edit-td"><input name="acct_year_month" type="text" class="Wdate" id="acct_year_month" ltype="text"
				validate="{required:true,maxlength:20}" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyyMM'})" /></td>
			
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">方案序号：</td>
			<td align="left" class="l-table-edit-td"><input name="sub_scheme_seq_no" type="text" id="sub_scheme_seq_no" ltype="text" /></td>
			
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室名称：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_code" type="text" id="dept_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
			
		</tr>

		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">职务名称：</td>
			<td align="left" class="l-table-edit-td"><input name="duty_code" type="text" id="duty_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">职工名称：</td>
			<td align="left" class="l-table-edit-td"><input name="emp_name" type="text" id="emp_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
		</tr>
	</table>

	<div id="toptoolbar"></div>
	<div id="maingrid"></div>
</body>
</html>
