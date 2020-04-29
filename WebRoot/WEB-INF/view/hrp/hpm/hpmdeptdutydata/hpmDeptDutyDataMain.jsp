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
    	
		$("#acct_yearm").ligerTextBox({ width:160 });
		autodate("#acct_yearm","yyyymm");
    	 
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
    	grid.options.parms.push({name:'acct_yearm',value:$("#acct_yearm").val()}); 
    	grid.options.parms.push({name:'dept_id',value:liger.get("dept_id").getValue().split(",")[0]}); 
    	grid.options.parms.push({name:'dept_no',value:liger.get("dept_id").getValue().split(",")[1]}); 
    	
    	//加载查询条件
    	grid.loadData(grid.where);
	}
	
    
    //加载grid
    function loadHead(){
		grid = $("#maingrid").ligerGrid({
			columns: [ 
				{ display: '科室编码', name: 'dept_code', align: 'left',width:120,render: 
					function (rowdata, rowindex, value){
						return "<a href='#' onclick=\"openUpdate('"
								+rowdata.dept_id+"','"
								+rowdata.dept_no+"','"
								+rowdata.acct_year+"','"
								+rowdata.acct_month+"');\" >"
								+rowdata.dept_code+"</a>";
					 }
				},
			
				{ display: '科室名称', name: 'dept_name', align: 'left'},
				
				{ display: '岗位系数', name: 'dept_duty_amount', align: 'left',editor:{type : 'float'},render: 
					function(rowdata, rowindex, value) {
					
						var dept_duty_amount =rowdata.dept_duty_amount;
							
						if(dept_duty_amount == null || dept_duty_amount==''){
							dept_duty_amount = 0;
						}
	
							//return  "<a href='#' onclick=\"openEmpDutyDataPage('"+rowdata.dept_id+"','"+rowdata.dept_no+"','"+rowdata.acct_year+"','"+rowdata.acct_month+"','"+rowdata.dept_name+"');\" >"+formatNumber(dept_duty_amount, 2, 1)+"</a>";
						return  formatNumber(dept_duty_amount, 2, 1);
					}
				}],
				
				dataAction: 'server',dataType: 'server',usePager:true,url:'queryHpmDeptDutyData.do?isCheck=false',
				width: '98%',height: '100%', checkbox: true,rownumbers:true,delayLoad:true,
				enabledEdit:true,onAfterEdit : f_onAfterEdit,isAddRow:false,
 				selectRowButtonOnly:true,//heightDiff: -10,
   				onDblClickRow : function (rowdata, rowindex, value)
   				{
   					openUpdate(rowdata.dept_id,rowdata.dept_no,rowdata.acct_year,rowdata.acct_month);//实际代码中temp替换主键
   					openEmpDutyDataMainPage(rowdata.dept_id,rowdata.dept_no,rowdata.acct_year,rowdata.acct_month,rowdata.dept_name);//实际代码中temp替换主键
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
       	
       	obj.push({ text: '添加（<u>A</u>）', id:'add', click: addDeptDutyData, icon:'add' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '删除（<u>D</u>）', id:'delete', click: deleteDeptDutyData,icon:'delete' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '生成（<u>G</u>）', id:'init', click: createDeptDutyData,icon:'bookpen' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '下载导入模板（<u>T</u>）', id:'downTemplate', click: downTemplate,icon:'down' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '导入（<u>I</u>）', id:'import', click: importData,icon:'up' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '打印（<u>P</u>）', id:'print', click: print,icon:'print' });
       	obj.push({ line:true });
       	
       	$("#toptoolbar").ligerToolBar({ items: obj});
	}
  	
	//键盘事件
	function loadHotkeys(){
		
		hotkeys('Q',query);
		 
		hotkeys('G',createDeptDutyData);
		
		hotkeys('A',addDeptDutyData);
		
		hotkeys('D',deleteDeptDutyData);
		
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
				title: '科室岗位系数准备',//标题
				columns: JSON.stringify(grid.getPrintColumns()),//表头
				class_name: "com.chd.hrp.hpm.service.AphiDeptDutyDataService",
				method_name: "queryHpmDeptDutyDataPrint",
				bean_name: "aphiDeptDutyDataService"/* ,
				heads: JSON.stringify(heads) *///表头需要打印的查询条件,可以为空
				/* foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 */
				};
		
		$.each(grid.options.parms,function(i,obj){
				printPara[obj.name]=obj.value;
		});
		
		officeGridPrint(printPara);
	}
  	
  	
  	//添加
  	function addDeptDutyData(){
  		$.ligerDialog.open({
  			url: 'addHpmDeptDutyDataPage.do?isCheck=false', 
  			height: 350,width: 500, title:'添加',modal:true,showToggle:false,
  			showMax:false,showMin: true,isResize:true,
  			buttons: [ 
  				{ text: '确定', onclick: 
  					function (item, dialog) { 
  						dialog.frame.saveEmpTargetData(); 
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
  	function deleteDeptDutyData(){
  		var acct_year = $("#acct_yearm").val();
    	if(acct_year == ""){
    		$.ligerDialog.warn('请选择年月');
    		return false;
    	}
        var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.warn('请选择行');
        	return ; 
        }
        
        var checkIds =[];
        $(data).each(function (){
        	checkIds.push(this.dept_id+";"+this.dept_no+";"+acct_year);//实际代码中temp替换主键
        });
        $.ligerDialog.confirm('确定删除?', function (yes){
        	if(yes){
            	ajaxJsonObjectByUrl("deleteHpmDeptDutyData.do",{checkIds:checkIds.toString()},function (responseData){
            		if(responseData.state=="true"){
            			query();
            		}
            	});
        	}
        }); 
  	}

    
  	//下载导入模板
  	function downTemplate(){
  		location.href = "downTemplateHpmDeptDutyData.do?isCheck=false";
  	}
  	
  	//导入
  	function importData(){
  		parent.$.ligerDialog.open({ url:'hrp/hpm/hpmdeptdutydata/hpmDeptDutyDataImportPage.do?isCheck=false',
   			data:{columns : grid.columns, grid : grid},
   			height: 300,
   			width: 450,
   			title:'科室岗位系数数据准备导入',
   			modal:true,
   			showToggle:false,
   			showMax:true,
			showMin: false,
			isResize:true,parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
  	}
  	
  	
  	//生成
  	function createDeptDutyData(){
  		
  		var acct_yearm=$("#acct_yearm").val();
		var dept_id = liger.get("dept_id").getValue() ? liger.get("dept_id").getValue().split(",")[0] : "";
    	var dept_no = liger.get("dept_id").getValue() ? liger.get("dept_id").getValue().split(",")[1] : "";
    	
    	if(acct_yearm==''){
    		$.ligerDialog.warn('请选择核算年月');
    		return false;
    	}
    	 
		$.ligerDialog.open({
			url: 'initHpmDeptDutyDataPage.do?isCheck=false&acct_yearm='+acct_yearm+'&dept_id='+dept_id+'&dept_no='+dept_no,
			title:'生成',height: 200,width: 400,modal:true,
			showToggle:false,showMax:false,showMin: true,isResize:true,
			buttons: [ 
				{ text: '确定', onclick: 
					function (item, dialog) { 
						dialog.frame.saveHpmDeptDutyData(); 
					},cls:'l-dialog-btn-highlight'},
				{ text: '取消', onclick: 
					function (item, dialog) {
						dialog.close();
					} 
				} 
			] 
		});
  	}
  	
  	
  	//计算
  	function collect(){
  		var acct_yearm = $("#acct_yearm").val();
    	
    	if(acct_yearm == ""){
    		$.ligerDialog.warn('请选择年月');
    		return false;
    	}
    	
    	$.ligerDialog.confirm('确定计算?', function (yes){
        	if(yes){
        		var param={
        				acct_yearm:acct_yearm,
        		}
            	ajaxJsonObjectByUrl("collectHpmDeptDutyData.do?isCheck=false",param,function (responseData){
            		if(responseData.state=="true"){
            			query();
            		}
            	});
        	}
        }); 
  	}
  	
  	//修改页跳转
    function openUpdate(dept_id,dept_no,acct_year,acct_month){
    	
    	//实际代码中&temp替换主键
    	$.ligerDialog.open({ 
    		url: 'updateHpmDeptDutyDataPage.do?isCheck=false&dept_id='+dept_id+'&dept_no='+dept_no+'&acct_year='+acct_year+'&acct_month='+acct_month,
    		data:{}, height: 250,width: 500, title:'修改',
    		modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
    		buttons: [ 
    			{ text: '确定', onclick: 
    				function (item, dialog) { 
    					dialog.frame.saveEmpTargetData(); 
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
    
     
    function openEmpDutyDataPage(dept_id,dept_no,acct_year,acct_month,dept_name){

    	//实际代码中&temp替换主键//queryHpmEmpDutyData  updateHpmEmpDutyDataPage
    	$.ligerDialog.open({ 
    		url: 'queryHpmEmpDutyData.do?isCheck=false&dept_id='+dept_id+'&dept_no='+dept_no+'&acct_year_month='+acct_year+acct_month+'&dept_name='+dept_name,
    		data:{
    			dept_id:dept_id,
    			dept_no:dept_no,
    			acct_year_month:acct_year+acct_month,
    			dept_name:dept_name
    		}, 
    		height: 600,width: 1000, title:'职工岗位系数准备',modal:true,
    		showToggle:false,showMax:false,showMin: false,isResize:true});

    }
    function loadDict(){
            //字典下拉框
    	
    	autocomplete("#dept_id","../queryDeptDictByPerm.do?isCheck=false","id","text",true,true);   
         }   
    
	 
	 //表格编辑事件
	 function f_onAfterEdit(e){
		 if(e.column.columnname == "dept_duty_amount"){
			 if(e.value == e.oldvalue){
				return false;
			 }
			 if(e.record.dept_duty_amount == ""){
				$.ligerDialog.warn("岗位系数为空！",'',{closeWhenEnter:false});
				return false;	
			 }
			 
			 var para = {
				dept_duty_amount : e.record.dept_duty_amount,
				dept_id : e.record.dept_id,
				dept_no : e.record.dept_no,
				acct_year : e.record.acct_year,
				acct_month : e.record.acct_month
			 };
			 ajaxJsonObjectByUrl("updateHpmDeptDutyData.do?isCheck=false", para, function(responseData){
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

	<table cellpadding="0" cellspacing="0" class="l-table-edit">

		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">核算年月：</td>
			<td align="left" class="l-table-edit-td"><input name="acct_yearm" type="text" class="Wdate" id="acct_yearm" ltype="text"
				validate="{required:true,maxlength:20}" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyyMM'})" /></td>
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
