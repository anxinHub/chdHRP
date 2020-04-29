<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"  xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<jsp:include page="${path}/inc.jsp"/>
<style type="text/css">
.ccc .l-grid-row-cell{background: #F1D3F7;}
</style>
<script type="text/javascript">

	var is_grant_show = ${is_grant_show};
	var is_audit_show = ${is_audit_show};
	var is_add_show = ${is_add_show};
	var is_del_show = ${is_del_show}
	var is_create_show = ${is_create_show};
	var is_import_show = ${is_import_show};
	var is_two_audit_show = ${is_two_audit_show};

    var grid;
    var gridManager = null;
	
    
    //页面初始化
    $(function (){
    	
    	
        loadDict();//加载下拉框
        
    	loadHead(null);//加载数据
    	
    	toolbar();//加载工具栏
    	
    	loadHotkeys();//加载快捷键
    	
    	$('#acct_yearm').bind('change',function(){queryGrantMoney();changeDate();});

    	$('#dept_id').bind('change',function(){queryGrantMoney();changeDate();});
    	
    	$('#item_code').bind('change',function(){queryGrantMoney();query();changeDate();});
    	
    });
    
    
    function queryGrantMoney(){
    	
    	var dept_id_no = liger.get("dept_id").getValue();
    	
    	var item_code = liger.get("item_code").getValue();
		
    	var acct_yearm = $("#acct_yearm").val();
		
		if(dept_id_no != null && dept_id_no != '' && item_code != null && item_code != '' ){
			
			var param = {
					acct_year : acct_yearm.substring(0,4),
					acct_month : acct_yearm.substring(4,6),
					item_code : item_code,
					dept_id : dept_id_no.split(",")[0],
					dept_no : dept_id_no.split(",")[1]
			}
			
    		ajaxJsonObjectByUrl("queryHpmEmpBonusDataDeptGrantSumMoney.do?isCheck=false",param,function (responseData){
    			$("#grant_money").text(responseData);
    		});
		}
    }
    
    
    
	
    //查询
    function  query(){
    	
    	grid_setColumns(); 
    	
    	//根据表字段进行添加查询条件
    	grid.options.parms=[];
    	grid.options.newPage=1;
        
        grid.options.parms.push({name:'acct_yearm',value:$("#acct_yearm").val()}); 
    	grid.options.parms.push({name:'dept_id',value:liger.get("dept_id").getValue() ? liger.get("dept_id").getValue().split(",")[0] : '' }); 
    	grid.options.parms.push({name:'dept_no',value:liger.get("dept_id").getValue() ? liger.get("dept_id").getValue().split(",")[1] : '' }); 
    	grid.options.parms.push({name:'emp_id',value:liger.get("emp_id").getValue() ? liger.get("emp_id").getValue().split(",")[0] : '' }); 
    	grid.options.parms.push({name:'emp_no',value:liger.get("emp_id").getValue() ? liger.get("emp_id").getValue().split(",")[1] : '' });
    	grid.options.parms.push({name:'item_code',value:liger.get("item_code").getValue()}); 
    	
    	grid.options.parms.push({name:'is_audits',value:$("#is_audits").val()});
    	
    	grid.options.parms.push({name:'is_grant',value:$("#is_grant").val()});
    	
    	grid.options.parms.push({name:'is_two_audits',value:$("#is_two_audits").val()});
    	
    	//加载查询条件
    	grid.loadData(grid.where);
    	
    	
	}
	
    
    //加载grid
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           	columns: [],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryHpmEmpBonusDataForReport.do',
            width: '100%',height: '100%',   checkbox: true,rownumbers:true,delayLoad:true,
            selectRowButtonOnly:true,
            /* 编辑前事件 */onBeforeEdit: f_onBeforeEdit,enabledEdit:true,
            /* 编辑后事件 */onAfterEdit : f_onAfterEdit,isAddRow:false,
            checkBoxDisplay: isCheckDisplay,
            rowClsRender:  function (rowdata,rowid)
            {
				
				//console.log(rowdata);
                if (rowdata.is_audit == 1){
                	
                    return "";
                    
                }else if(rowdata.is_audit == 0){
              
                	return "ccc";
                }
            },
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    
  	//工具栏
	function toolbar(){
       	var obj = [];
       	
       	obj.push({ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '引入（<u>Y</u>）', id:'add', click: addData, icon:'add',hide:is_add_show });
       	obj.push({ line:true,hide:is_add_show });
       	
       	obj.push({ text: '生成（<u>G</u>）', id:'create', click: create, icon:'initwage',hide:is_create_show});
       	obj.push({ line:true,hide:is_create_show});
       	
       	obj.push({ text: '删除（<u>D</u>）', id:'del', click: del,icon:'candle',hide:is_del_show});
       	obj.push({ line:true,hide:is_del_show});

       	obj.push({ text: '审核（<u>C</u>）',id:'audit', click: audit, icon:'ok',hide:is_audit_show});
       	obj.push({ line:true,hide:is_audit_show});
       	
       	obj.push({ text: '消审（<u>U</u>）',id:'reAudit', click: reAudit, icon:'delete',hide:is_audit_show});
       	obj.push({ line:true,hide:is_audit_show});
       	
       	obj.push({ text: '领导审核（<u>A</u>）',id:'towAudit', click: twoAudit, icon:'ok',hide:is_two_audit_show});
       	obj.push({ line:true,is_two_audit_show});
       	
       	obj.push({ text: '领导消审（<u>L</u>）',id:'reTwoAudit', click: reTwoAudit, icon:'delete',hide:is_two_audit_show});
       	obj.push({ line:true,is_two_audit_show});

       	obj.push({ text: '提交（<u>S</u>）',id:'grant', click: grant, icon:'ok' ,hide:is_grant_show});
       	obj.push({ line:true,hide:is_grant_show});
       	
       	obj.push({ text: '取消提交（<u>R</u>）',id:'reGrant', click: reGrant, icon:'delete' ,hide:is_grant_show});
       	obj.push({ line:true,hide:is_grant_show});
       	
       	
       	obj.push({ text: '模板（<u>T</u>）',id:'downTemplate', click:downTemplate,icon:'down',hide:is_import_show});
       	obj.push({ line:true,hide:is_import_show});
       	
       	obj.push({ text: '导入（<u>I</u>）',id : 'importData',click : importData,icon : 'up',hide:is_import_show});
       	obj.push({ line:true,hide:is_import_show});
       	
       	obj.push({ text: '打印（<u>P</u>）',id : 'print',click : print,icon : 'print'});
       	obj.push({ line:true});
       	
       	
       	$("#toptoolbar").ligerToolBar({ items: obj});
	}
  	
  	
	//键盘事件
	function loadHotkeys(){
		
		hotkeys('Q',query);
		hotkeys('Y',addData);
		hotkeys('D',del);
		hotkeys('G',create);
		hotkeys('C',audit);
		hotkeys('U',reAudit);
		hotkeys('A',twoAudit);
		hotkeys('L',reTwoAudit);
		hotkeys('S',grant);
		hotkeys('R',reGrant);
		hotkeys('T',downTemplate);
		hotkeys('I',importData);
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
			title: "二次分配上报",//标题
			columns: JSON.stringify(grid.getPrintColumns()),//表头
			class_name: "com.chd.hrp.hpm.service.AphiEmpBonusDataService",
			method_name: "queryHpmEmpBonusDataForReportPrint",
			bean_name: "aphiEmpBonusDataService"/* ,
			heads: JSON.stringify(heads) *///表头需要打印的查询条件,可以为空
			/* foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 */
		};
		
		$.each(grid.options.parms,function(i,obj){
				printPara[obj.name]=obj.value;
		});
		
		officeGridPrint(printPara);
	}
    
	function isCheckDisplay(rowdata) {
    	
    	if(rowdata.dept_name == '总计' || rowdata.emp_id == null){
    		return false;
    	} 
    	
      /*   if (rowdata.is_audit == 1){
        	return false;
        } */
        return true;
    }
    
  	//编制之前验证
	function f_onBeforeEdit(e){
		 
	}
	 
	 //编辑之后事件
	 function f_onAfterEdit(e){
		 
		var data = gridManager.getData()[0];
		
		if(e.value == e.oldvalue){
			return false;
		}
		
		if(e.value.toString() == ""){
			gridManager.updateRow(e.record,{value:e.oldvalue});
			return ;
		}
		
		gridManager.updateRow(e.record,{value:e.value});
		
		//var splitStr = e.column.columnname.split('__');
			
		if(e.record.is_audit == 1){
			$.ligerDialog.warn('已审核的数据不能修改 ')
			return ; 
		}
		
		var item_code = e.column.columnname;
		
		var para = {
        	acct_yearm : e.record.acct_year+e.record.acct_month,
			dept_id : e.record.dept_id,
			dept_no : e.record.dept_no,
			emp_id : e.record.emp_id,
			emp_no : e.record.emp_no,
			bonus_money : e.value,
			item_code : item_code.substring(10,item_code.length)
		};
		
		
		var sum = 0 ; 
		ajaxJsonObjectByUrl("updateHpmEmpBonusDataForReport.do", para, function(responseData){
			if (responseData.state == "true") {
				//query();
				
				
				if(e.value > e.oldvalue){//新值大于旧值
					//总值 = 总值 + 新值 - 旧值
					sum = data.sum_money + e.value - e.oldvalue;
				}
				
				if(e.value < e.oldvalue){//新值小于旧值
					//总值 = 总值 - (旧值 - 新值)
					sum = data.sum_money - (e.oldvalue - e.value);
				}
				
				gridManager.updateRow(0,{sum_money:sum});
			}
		}); 
	 }
	
	 
	 
	//导入
	function importData() {
		parent.$.ligerDialog.open({
			url : 'hrp/hpm/hpmempbonusdata/hpmEmpBonusDataForReportImportPage.do?isCheck=false',
			data : {
				columns : grid.columns,
				grid : grid
			},
			title : '职工维护导入',height : 300,width : 450,modal : true,
			showToggle : false,showMax : true,showMin : false,isResize : true,
			parentframename : window.name, //用于parent弹出层调用本页面的方法或变量
		});

	}
	
	
	//生成
	function create(){
		
		var acct_yearm=$("#acct_yearm").val();
    	var dept_id = liger.get("dept_id").getValue();
    	
    	var item_code = liger.get("item_code").getValue(); 
    	//var dept_id = liger.get("dept_id").getValue()? liger.get("dept_id").getValue():'null';

    	if(acct_yearm==''){
    		$.ligerDialog.warn('请选择核算年月');
    		return false;
    	}
    	
		if(!dept_id ){
			$.ligerDialog.warn('请选择科室');
			return false;
		}
    	
    	var paras = acct_yearm+"@"+dept_id+"@"+item_code;

    	$.ligerDialog.open({
    		url: 'createHpmEmpBonusDataForReportPage.do?isCheck=false&paras='+paras, 
    		title:'生成',height: 200,width: 400,modal:true,showToggle:false,
    		showMax:false,showMin: true,isResize:true,
    		buttons: [ 
    			{ text: '确定', onclick: 
    				function (item, dialog) { 
    					dialog.frame.saveIncomeItemTargetConf(); 
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
	
	
	//添加
	function addData(){
		
		var acct_yearm=$("#acct_yearm").val();
		var dept_id = liger.get("dept_id").getValue();
		var item_code = liger.get("item_code").getValue();
		
		if(!dept_id){
			$.ligerDialog.warn('请选择科室');
			return false;
		}
    	
		var para = "?isCheck=false&acct_yearm="+acct_yearm+"&dept_id="+dept_id+"&item_code="+item_code;

    	parent.$.ligerDialog.open({ 
    		url: 'hrp/hpm/hpmempbonusdata/hpmEmpBonusDataAddPage.do'+para,data:{}, 
    		height:$(window).height()-30,width: $(window).width()-10, title:'引入',modal:true,showToggle:false,
    		showMax:true,showMin: false,isResize:true,
    		parentframename: window.name,
    		buttons: [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.saveHpmEmpBonusData();
				},
				cls : 'l-dialog-btn-highlight'
				},
    			{ text: '关闭', onclick: 
    				function (item, dialog) { 
    					dialog.close(); 
    				} 
    			} 
    		]
    	});
	}
	
	
	//审核
	function audit(){
		
		var all = gridManager.getData()[0];
		
		if(all.sum_money == 0){
			$.ligerDialog.warn('上报金额不对！ ')
			return ;
		}
				
		var data = gridManager.getCheckedRows();
		
		var acct_yearm =$("#acct_yearm").val();
		if (acct_yearm.length == 0){
			$.ligerDialog.warn('请选择核算年月');
			return false;
		}
		
		var item_code = liger.get("item_code").getValue();
		
		if (data.length > 0){//选复选框时
		
			var checkIds =[];
		
			$(data).each(function (){checkIds.push(this.dept_id+"@"+this.dept_no+"@"+item_code);});
			
			$.ligerDialog.confirm('确定审核?', function (yes){
		       	if(yes){
		           	ajaxJsonObjectByUrl("auditHpmEmpBonusDataForReport.do?isCheck=false&acct_yearm="+$("#acct_yearm").val()+"&is_audit=1&item_code="+item_code,{checkIds:checkIds.toString()},function (responseData){
		           		if(responseData.state=="true"){
		           			query();
		           			changeDate();
		           		}
		           	});
		       	}
	       	});   
			
		}else{
				
				var dept_id = liger.get("dept_id").getValue();
				
				
				if(is_grant_show){
					if (!dept_id){
						$.ligerDialog.warn('请选择科室');
						return false;
					}
		    	}
				
				var param={acct_yearm:acct_yearm,is_audit:1,dept_id:dept_id ? dept_id : '',item_code:item_code?item_code:''};
		  		
				ajaxJsonObjectByUrl("auditHpmEmpBonusDataForReport.do",param,function (responseData){
					if(responseData.state=="true"){query();changeDate();}
				});
			
			}
	}
	
	
	//反审
	function reAudit(){
		
		
		var data = gridManager.getCheckedRows();
		
		var acct_yearm =$("#acct_yearm").val();
		if (acct_yearm.length == 0){
			$.ligerDialog.warn('请选择核算年月');
			return false;
		}
		
		var item_code = liger.get("item_code").getValue();
		
		if (data.length > 0){//选复选框时
		
			var checkIds =[];
		
			$(data).each(function (){checkIds.push(this.dept_id+"@"+this.dept_no+"@"+item_code);});
			
			$.ligerDialog.confirm('确定反审核?', function (yes){
		       	if(yes){
		           	ajaxJsonObjectByUrl("reAuditHpmEmpBonusDataForReport.do?isCheck=false&acct_yearm="+$("#acct_yearm").val()+"&is_audit=0",{checkIds:checkIds.toString()},function (responseData){
		           		if(responseData.state=="true"){
		           			query();
		           			changeDate();
		           		}
		           	});
		       	}
	       	});   
			
		}else{
			var acct_yearm =$("#acct_yearm").val();
			
			if (acct_yearm.length == 0){
				$.ligerDialog.warn('请选择核算年月');
				return false;
			}
			
			var dept_id = liger.get("dept_id").getValue();
			
			if(is_grant_show){
				if (!dept_id){
					$.ligerDialog.warn('请选择科室');
					return false;
				}
	    	}
			
			var param={acct_yearm:acct_yearm,is_audit:0,dept_id:dept_id ? dept_id : '',item_code:item_code?item_code:''};
	  		
			ajaxJsonObjectByUrl("reAuditHpmEmpBonusDataForReport.do",param,function (responseData){
				if(responseData.state=="true"){query();changeDate();}
			});
		}
	}
	
	//审核
	function twoAudit(){
		
		var all = gridManager.getData()[0];
		
		if(all.sum_money == 0){
			$.ligerDialog.warn('上报金额不对！ ')
			return ;
		}
		
		var data = gridManager.getCheckedRows();
		
		var acct_yearm =$("#acct_yearm").val();
		if (acct_yearm.length == 0){
			$.ligerDialog.warn('请选择核算年月');
			return false;
		}
		
		var item_code = liger.get("item_code").getValue();
		
		if (data.length > 0){//选复选框时
		
			var checkIds =[];
		
			$(data).each(function (){checkIds.push(this.dept_id+"@"+this.dept_no+"@"+item_code);});
			
			$.ligerDialog.confirm('确定审核?', function (yes){
		       	if(yes){
		           	ajaxJsonObjectByUrl("twoAuditHpmEmpBonusDataForReport.do?isCheck=false&acct_yearm="+$("#acct_yearm").val()+"&is_two_audit=1",{checkIds:checkIds.toString()},function (responseData){
		           		if(responseData.state=="true"){
		           			query();
		           		}
		           	});
		       	}
	       	});   
			
		}else{
		
			var dept_id = liger.get("dept_id").getValue();
			
			if(is_grant_show){
				if (!dept_id){
					$.ligerDialog.warn('请选择科室');
					return false;
				}
	    	}
			
			var param={acct_yearm:acct_yearm,is_two_audit:1,dept_id:dept_id ? dept_id : '',item_code:item_code?item_code:''};
	  		
			ajaxJsonObjectByUrl("twoAuditHpmEmpBonusDataForReport.do",param,function (responseData){
				if(responseData.state=="true"){query();}
			});
		}	
	}
	
	
	//反审
	function reTwoAudit(){
		
		var data = gridManager.getCheckedRows();
		
		var acct_yearm =$("#acct_yearm").val();
		if (acct_yearm.length == 0){
			$.ligerDialog.warn('请选择核算年月');
			return false;
		}
		
		var item_code = liger.get("item_code").getValue();
		
		if (data.length > 0){//选复选框时
		
			var checkIds =[];
		
			$(data).each(function (){checkIds.push(this.dept_id+"@"+this.dept_no+"@"+item_code);});
			
			$.ligerDialog.confirm('确定审核?', function (yes){
		       	if(yes){
		           	ajaxJsonObjectByUrl("reTwoAuditHpmEmpBonusDataForReport.do?isCheck=false&acct_yearm="+$("#acct_yearm").val()+"&is_two_audit=0",{checkIds:checkIds.toString()},function (responseData){
		           		if(responseData.state=="true"){
		           			query();
		           		}
		           	});
		       	}
	       	});   
			
		}else{
		
			var acct_yearm =$("#acct_yearm").val();
			
			if (acct_yearm.length == 0){
				$.ligerDialog.warn('请选择核算年月');
				return false;
			}
			
			var dept_id = liger.get("dept_id").getValue();
			
			if(is_grant_show){
				if (!dept_id){
					$.ligerDialog.warn('请选择科室');
					return false;
				}
	    	}
			
			var param={acct_yearm:acct_yearm,is_two_audit:0,dept_id:dept_id ? dept_id : '',item_code:item_code?item_code:''};
	  		
			ajaxJsonObjectByUrl("reTwoAuditHpmEmpBonusDataForReport.do",param,function (responseData){
				if(responseData.state=="true"){query();}
			});
			
		}	
	}
	
	//发放
	function grant(){
		
		var data = gridManager.getCheckedRows();
		
		var acct_yearm =$("#acct_yearm").val();
		if (acct_yearm.length == 0){
			$.ligerDialog.warn('请选择核算年月');
			return false;
		}
		
		var item_code = liger.get("item_code").getValue();
		
		if (data.length > 0){//选复选框时
			
			if (item_code.length == 0){
				$.ligerDialog.warn('请选择项目');
				return false;
			}
		
			var checkIds =[];
		
			$(data).each(function (){checkIds.push(this.dept_id+"@"+this.dept_no+"@"+item_code);});
			
			$.ligerDialog.confirm('确定发放?', function (yes){
		       	if(yes){
		           	ajaxJsonObjectByUrl("grantHpmEmpBonusDataForReport.do?isCheck=false&acct_yearm="+$("#acct_yearm").val()+"&is_audit=1&is_grant=1",{checkIds:checkIds.toString()},function (responseData){
		           		if(responseData.state=="true"){
		           			query();
		           		}
		           	});
		       	}
	       	});   
			
		}else{
			var acct_yearm =$("#acct_yearm").val();
			if (acct_yearm.length == 0){
				$.ligerDialog.warn('请选择核算年月');
				return false;
			}
			var dept_id = liger.get("dept_id").getValue().split(",")[0];
			
			var dept_no = liger.get("dept_id").getValue().split(",")[1];
			
			var param={acct_yearm:acct_yearm,is_audit:1,is_grant:1,item_code:item_code?item_code:'',dept_id:dept_id?dept_id:'',dept_no:dept_no?dept_no:''};
	  		
			ajaxJsonObjectByUrl("grantHpmEmpBonusDataForReport.do",param,function (responseData){
				if(responseData.state=="true"){query();}
			});
		}
	}
	
	
	//取消发放
	function reGrant(){
		
	var data = gridManager.getCheckedRows();
		
		var acct_yearm =$("#acct_yearm").val();
		if (acct_yearm.length == 0){
			$.ligerDialog.warn('请选择核算年月');
			return false;
		}
		
		var item_code = liger.get("item_code").getValue();
		
		if (data.length > 0){//选复选框时
			
			if (item_code.length == 0){
				$.ligerDialog.warn('请选择项目');
				return false;
			}
		
			var checkIds =[];
		
			$(data).each(function (){checkIds.push(this.dept_id+"@"+this.dept_no+"@"+item_code);});
			
			$.ligerDialog.confirm('确定取消发放?', function (yes){
		       	if(yes){
		           	ajaxJsonObjectByUrl("grantHpmEmpBonusDataForReport.do?isCheck=false&acct_yearm="+$("#acct_yearm").val()+"&is_audit=0&is_grant=0",{checkIds:checkIds.toString()},function (responseData){
		           		if(responseData.state=="true"){
		           			query();
		           		}
		           	});
		       	}
	       	});   
			
		}else{
		
			var acct_yearm =$("#acct_yearm").val();
			
			if (acct_yearm.length == 0){
				$.ligerDialog.warn('请选择核算年月');
				return false;
			}
			
			var item_code = liger.get("item_code").getValue();
			
			var dept_id = liger.get("dept_id").getValue().split(",")[0];
			
			var dept_no = liger.get("dept_id").getValue().split(",")[1];
			
	  		var param={acct_yearm:acct_yearm,is_audit:0,is_grant:0,item_code:item_code?item_code:'',dept_id:dept_id?dept_id:'',dept_no:dept_no?dept_no:''};
	  		
			ajaxJsonObjectByUrl("grantHpmEmpBonusDataForReport.do",param,function (responseData){
				if(responseData.state=="true"){query();}
			});
		}	
		
	}
	
	
	//删除
	function del(){
		
		 var data = gridManager.getCheckedRows();
		 
         if (data.length == 0){
         	$.ligerDialog.warn('请选择行');
         	return false;
         }
         var ParamVo =[];
         var msg = '';//提示信息
         
         var item_code = liger.get("item_code").getValue() == '' ?  0 : liger.get("item_code").getValue() ;
		$(data).each(function (){
			if(this.is_audit == 1){
				msg += '职工编码:' + this.emp_code + ',科室编码:' + this.dept_code + '数据已审核<br/>';
			}
        	ParamVo.push(this.acct_year+";"+this.acct_month +";"+this.emp_id+";"+this.emp_no+";"+this.dept_id+";"+this.dept_no+";"+ item_code);
        });
         
         if(msg != ''){
        	 $.ligerDialog.warn(msg);
        	 return ; 
         }
         
     	 ajaxJsonObjectByUrl("deleteHpmEmpBonusDataForReport.do",{ParamVo : ParamVo.toString()},function (responseData){
       		if(responseData.state=="true"){query();}
       	});
	}
   	
	
	function changeDate(){
		var dept_id_no = liger.get("dept_id").getValue();
    	
    	var item_code = liger.get("item_code").getValue();
		
    	var acct_yearm = $("#acct_yearm").val();
		
		if(dept_id_no != null && dept_id_no != '' && item_code != null && item_code != '' ){
			
			var param = {
					acct_yearm : acct_yearm,
					item_code : item_code,
					dept_id : dept_id_no.split(",")[0],
					dept_no : dept_id_no.split(",")[1]
			}
			
    		ajaxJsonObjectByUrl("querydataAuditaBonus.do?isCheck=false",param,function (responseData){
    			
    			if (responseData.is_audit==0) {
    				
					$("#is_audit").text("未审核");
					
    			}else if (responseData.is_audit==1){
    				
    				$("#is_audit").text("已审核");
    				
    			}else{
    				
    				$("#is_audit").text(" ");
    				
    			};
    		});
		}

	}
	
	//字典下拉框
    function loadDict(){
    	
    	if(is_grant_show){
    		
    		autocomplete("#dept_id","../queryDeptDictByPerm.do?isCheck=false","id","text",true,true,'',true);
    		
    	}else{
    		
    		autocomplete("#dept_id","../queryDeptDictByPerm.do?isCheck=false","id","text",true,true);
    	}

    	autocomplete("#emp_id","../queryEmpDict.do?isCheck=false","id","text",true,true);
    	
    	autocomplete("#item_code","../queryItemAllDict.do?&isCheck=false","id","text",true,true,'',true);

    	$("#acct_yearm").ligerTextBox({width:160 });
    	
    	autodate("#acct_yearm","yyyymm");
    	
    	$("#is_audits,#is_grant,#is_two_audits").ligerComboBox({width:160 });

    }  
    
	
	//下载导入模板
	function downTemplate(){location.href = "downEmpBonusDataTemplateForReport.do?isCheck=false";}	
	
	//查询动态列
	function grid_setColumns() {

		ajaxJsonObjectByUrl("queryHpmEmpBonusDataForReportGrid.do?isCheck=false", {item_code:liger.get("item_code").getValue()},function(responseData) {
			if (responseData != null) {
				grid.set('columns', responseData.Rows);
				grid.reRender();
			}
		});
	}


    </script>

</head>

<body style="padding: 0px; overflow: hidden;"  onload="grid_setColumns();">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<table cellpadding="0" cellspacing="0" class="l-table-edit">

		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">核算年月：</td>
			<td align="left" class="l-table-edit-td"><input name="acct_yearm" type="text" id="acct_yearm" class="Wdate" ltype="text"
				validate="{required:true,maxlength:20}" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyyMM',onpicked:changeDate()})" onchange="queryGrantMoney();"/></td>
			<td align="left"></td>
			
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">项目名称：</td>
            <td align="left" class="l-table-edit-td"><input name="item_code" type="text" id="item_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室名称：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">职工姓名：</td>
			<td align="left" class="l-table-edit-td"><input name="emp_id" type="text" id="emp_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
		</tr>
			 <td align="right" class="l-table-edit-td"  style="padding-left:20px;">审核状态：</td>
            <td align="left" class="l-table-edit-td">
            	<select name="is_audits" id="is_audits">
						<option value="">请选择</option>
						<option value="0">新建</option>
						<option value="1">审核</option>
				</select>
            </td>
            <td align="left"></td>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">领导审核状态：</td>
            <td align="left" class="l-table-edit-td">
            	<select name="is_two_audits" id="is_two_audits">
						<option value="">请选择</option>
						<option value="0">新建</option>
						<option value="1">领导审核</option>
				</select>
            </td>
            <td align="left"></td>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">提交状态：</td>
            <td align="left" class="l-table-edit-td">
            	<select name="is_grant" id="is_grant">
						<option value="">请选择</option>
						<option value="0">新建</option>
						<option value="1">提交</option>
				</select>
            </td>
            <td align="left"></td>
		<tr>
		
		
		</tr>
		
		<tr>
			
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">项目金额：</td>
			<td align="left" class="l-table-edit-td">
                <span id="grant_money" style="color: red"></span>
            </td>
			<td align="left"></td>
		</tr>
		
		<tr>
        	<table>
        		<tr>
					 <td align="right" class="l-table-edit-td" style="padding-left: 20px;">当前科室审核状态:</td>
            		 <td align="left" class="l-table-edit-td"><span id="is_audit" style="color: red"></span></td>
           			 <td align="left"></td> 
        		</tr>
        	</table>
            
        </tr>
	</table>

	<div id="toptoolbar"></div>
	<div id="maingrid"></div>

</body>
</html>
