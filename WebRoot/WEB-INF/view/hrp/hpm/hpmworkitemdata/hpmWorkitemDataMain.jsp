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
    
	var is_add_show = ${is_add_show};
    var is_update_show = ${is_update_show};
    var is_init_show = ${is_init_show};
    var is_delete_show = ${is_delete_show};
    var is_import_show = ${is_import_show};
    
    //页面初始化
    $(function (){
    	 $("#acct_yearm").ligerTextBox({ width:160 });
    	 autodate("#acct_yearm","yyyymm");
    	 
        loadDict();//加载数据
    	loadHead(null);//加载grid
    	toolbar();//加载工具栏
    	loadHotkeys();//加载快捷键
    });
    
    
    //查询
    function  query(){
   		grid.options.parms=[];
   		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'acct_yearm',value:$("#acct_yearm").val()}); 
    	grid.options.parms.push({name:'dept_id',value:liger.get("dept_id").getValue().split(",")[0]}); 
    	grid.options.parms.push({name:'work_item_code',value:liger.get("work_item_code").getValue()}); 
    	grid.options.parms.push({name:'dept_kind_code',value:liger.get("dept_kind_code").getValue()}); 
    	grid.options.parms.push({name:'dept_no',value:liger.get("dept_id").getValue().split(",")[1]}); 
    	$("#resultPrint > table > tbody").html("");
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           	columns: [ 
				{ display: '科室编码', name: 'dept_code', align: 'left',render: 
					function (rowdata, rowindex, value){
						return "<a href='#' onclick=\"openUpdate('"+rowdata.dept_id+"','"+rowdata.dept_no+"','"+rowdata.work_item_code+"','"+rowdata.acct_year+"','"+rowdata.acct_month+"');\" >"+rowdata.dept_code+"</a>";
		           	}
				},
				{ display: '科室名称', name: 'dept_name', align: 'left'},
                { display: '工作量指标编码', name: 'work_item_code', align: 'left'},
				{ display: '工作量指标名称', name: 'work_item_name', align: 'left'},
                { display: '工作量', name: 'work_amount', align: 'left'},
				{ display: '计件标准', name: 'work_standard', align: 'left'}
			],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryHpmWorkitemData.do',
			width: '100%',height: '100%',   checkbox: true,rownumbers:true,delayLoad:true,
			selectRowButtonOnly:true,//heightDiff: -10,
			onDblClickRow : 
				function (rowdata, rowindex, value){
					openUpdate(rowdata.dept_id,rowdata.dept_no,rowdata.work_item_code,rowdata.acct_year,rowdata.acct_month);//实际代码中temp替换主键
    			} 
       	});

        gridManager = $("#maingrid").ligerGetGridManager();
        formatTrueFalse();
    }
    
  	//工具栏
	function toolbar(){
       	var obj = [];
       	
       	obj.push({ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '添加（<u>A</u>）', id:'add', click: addWorkitemData, icon:'add',hide: is_add_show});
       	obj.push({ line:true,hide: is_add_show});
       	
       	obj.push({ text: '删除（<u>D</u>）', id:'delete', click: deleteWorkitemData,icon:'delete' ,hide: is_delete_show});
       	obj.push({ line:true ,hide: is_delete_show});
       	
       	obj.push({ text: '生成（<u>G</u>）', id:'create', click: createWorkitemData,icon:'bookpen' ,hide: is_init_show});
       	obj.push({ line:true,hide: is_init_show});
       	
       	obj.push({ text: '下载导入模板（<u>T</u>）', id:'downTemplate', click: downTemplate,icon:'down' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '导入（<u>I</u>）', id:'import', click: importData,icon:'up' ,hide: is_import_show});
       	obj.push({ line:true,hide: is_import_show});
       	
       	obj.push({ text: '打印（<u>P</u>）', id:'print', click: print,icon:'print'});
       	obj.push({ line:true });
       	
       	$("#toptoolbar").ligerToolBar({ items: obj});
	}
  	
  	
	//键盘事件
	function loadHotkeys(){
		
		hotkeys('Q',query);
		 
		hotkeys('G',createWorkitemData);
		
		hotkeys('A',addWorkitemData);
		
		hotkeys('D',deleteWorkitemData);
		
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
				title: "工作量数据准备",//标题
				columns: JSON.stringify(grid.getPrintColumns()),//表头
				class_name: "com.chd.hrp.hpm.service.AphiWorkitemDataService",
				method_name: "queryWorkitemDataPrint",
				bean_name: "aphiWorkitemDataService"/* ,
				heads: JSON.stringify(heads) *///表头需要打印的查询条件,可以为空
				/* foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 */
				};
		
		$.each(grid.options.parms,function(i,obj){
				printPara[obj.name]=obj.value;
		});
		
		officeGridPrint(printPara);
	}
    
    
    //添加
    function addWorkitemData(){
  		$.ligerDialog.open({
  			url: 'hpmWorkitemDataAddPage.do?isCheck=false',  height: 300,width: 500, title:'添加',
  			modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,
  			buttons: [ 
  				{ text: '确定', onclick: 
  					function (item, dialog) { 
  						dialog.frame.saveWorkitemData(); 
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
    
    
    //删除
    function deleteWorkitemData(){
    	 var data = gridManager.getCheckedRows();
         if (data.length == 0){
         	$.ligerDialog.warn('请选择行');
         	return ; 
         }
         var checkIds =[];
         $(data).each(function (){
         	checkIds.push(this.dept_id+";"+this.dept_no+";"+this.work_item_code+";"+this.acct_year+";"+this.acct_month);//实际代码中temp替换主键
         });
         $.ligerDialog.confirm('确定删除?', function (yes){
         	if(yes){
             	ajaxJsonObjectByUrl("deleteHpmWorkitemData.do",{checkIds:checkIds.toString()},function (responseData){
             		if(responseData.state=="true"){
             			query();
             		}
             	});
         	}
         }); 
    }
    
    
    //下载导入模板
    function downTemplate(){
    	location.href = "downTemplateHpmWorkitemData.do?isCheck=false";
    }
    
    
    //导入
    function importData(){
    	parent.$.ligerDialog.open({ url:'hrp/hpm/hpmworkitemdata/hpmWorkitemDataImportPage.do?isCheck=false',
   			data:{columns : grid.columns, grid : grid},
   			height: 300,
   			width: 450,
   			title:'工作量指标数据准备导入',
   			modal:true,
   			showToggle:false,
   			showMax:true,
			showMin: false,
			isResize:true,parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
    }
    
    
    //生成
    function createWorkitemData(){
    	var acct_yearm=$("#acct_yearm").val();
    	
    	var dept_id = (liger.get("dept_id").getValue().split(",")[0] === undefined ? "":liger.get("dept_id").getValue().split(",")[0]);
    	var dept_no = (liger.get("dept_id").getValue().split(",")[1] === undefined ? "":liger.get("dept_id").getValue().split(",")[1]);
    	var work_item_code = liger.get("work_item_code").getValue()? liger.get("work_item_code").getValue():'null';
    	
    	var dept_kind_code = liger.get("dept_kind_code").getValue()? liger.get("dept_kind_code").getValue():'null';
    	
    	if(acct_yearm==''){
    		$.ligerDialog.warn('请选择核算年月');
    		return false;
    	}
    	
    	/* if(dept_id === "" && dept_no === ""){
    		$.ligerDialog.warn('请选择科室名称');
    		return false;
    	} */
    	
    	var paras = acct_yearm+"@"+dept_id+"@"+dept_no+"@"+work_item_code+"@"+dept_kind_code;

    	$.ligerDialog.open({url: 'workItemDataFastPage.do?isCheck=false&paras='+paras, height: 200,width: 400, title:'生成',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveIncomeItemTargetConf(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    	
    }
	
    
    //修改页跳转
    function openUpdate(dept_id,dept_no,work_item_code,acct_year,acct_month){
    	var formPara={
    			acct_yearm:acct_year+acct_month,
    			dept_id:dept_id,
    			dept_no:dept_no,
                work_item_code:work_item_code
          };
    	/* ajaxJsonObjectByUrl("getWorkItemTargetValue.do?isCheck=false", formPara, function(responseData) {
     		if (responseData.state == "false") {
     			$.ligerDialog.open({url: 'workItemTargetDataChoosePage.do?isCheck=false&checkIds='+checkIds, height: 200,width: 400, title:'生成',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveIncomeItemTargetConf(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

     		}else{
     			$.ligerDialog.warn('该月指标已审核，不能进行生成');
     		}
     	}) */
    	$.ligerDialog.open({ url: 'hpmWorkitemDataUpdatePage.do?isCheck=false&dept_id='+dept_id+'&dept_no='+dept_no+'&work_item_code='+work_item_code+'&acct_year='+acct_year+'&acct_month='+acct_month,
    			data:{},  height: 300,width: 500,title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveWorkitemData(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    }
    //字典下拉框
    function loadDict(){
    	
    	autocomplete("#dept_kind_code","../../hpm/queryDeptKindDict.do?isCheck=false","id","text",true,true);  
    	changeAcctYear();//核算年月绑定事件
	}
    
  //核算年月绑定事件
    function changeAcctYear(){
	  
	  	var para = {
	  		acct_yearm:$("#acct_yearm").val()
	  	};
    	autocomplete("#work_item_code","../../hpm/queryHpmWorkitemSeqTime.do?isCheck=false","id","text",true,true,para);
    	autocomplete("#dept_id","../queryDeptDictByPerm.do?isCheck=false","id","text",true,true,para);
    }
  
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<table cellpadding="0" cellspacing="0" class="l-table-edit">

		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">核算年月：</td>
			<td align="left" class="l-table-edit-td"><input class="Wdate" name="acct_yearm" type="text" id="acct_yearm" ltype="text"
				validate="{required:true,maxlength:20}" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyyMM'})" onchange="changeAcctYear()" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室名称：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">工作量指标：</td>
			<td align="left" class="l-table-edit-td"><input name="work_item_code" type="text" id="work_item_code" ltype="text"
				validate="{required:true,maxlength:20}" /></td>
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
