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
    	
        loadDict();//加载下拉框
    	loadHead(null);//加载数据
    	
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

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
					{ display: '科室编码', name: 'dept_id', align: 'left',
						 render: function (rowdata, rowindex, value){
							return "<a href='#' onclick=\"openUpdate('"+rowdata.dept_id+"','"+rowdata.dept_no+"','"+rowdata.acct_year+"','"+rowdata.acct_month+"');\" >"+rowdata.dept_id+"</a>";
					 }
					},
					{ display: '科室名称', name: 'dept_name', align: 'left'
					},
					{ display: '职工人数', name: 'emp_amount', align: 'left',editor:{type : 'float'},
						render: function (rowdata, rowindex, value){
							return formatNumber(rowdata.emp_amount ==null ? 0 : rowdata.emp_amount,2,1);
					 	}
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryHpmDeptEmpData.do',
                     width: '100%',height: '100%',  checkbox: true,rownumbers:true,delayLoad:true,enabledEdit:true,
                     onAfterEdit : f_onAfterEdit,isAddRow:false,
                     selectRowButtonOnly:true,//heightDiff: -10,
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
    					openUpdate(rowdata.dept_id,rowdata.dept_no,rowdata.acct_year,rowdata.acct_month);//实际代码中temp替换主键
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
       	
       	obj.push({ text: '添加（<u>A</u>）', id:'add', click: addDeptEmpData, icon:'add' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '删除（<u>D</u>）', id:'delete', click: deleteDeptEmpData,icon:'delete' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '生成（<u>G</u>）', id:'init', click: createDeptEmpData,icon:'bookpen' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '计算（<u>F</u>）', id:'collect', click: collect,icon:'right' });
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
		 
		hotkeys('G',createDeptEmpData);
		
		hotkeys('F',collect);
		
		hotkeys('A',addDeptEmpData);
		
		hotkeys('D',deleteDeptEmpData);
		
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
				title: '科室人数数据准备',//标题
				columns: JSON.stringify(grid.getPrintColumns()),//表头
				class_name: "com.chd.hrp.hpm.service.AphiDeptEmpDataService",
				method_name: "queryHpmDeptEmpDataPrint",
				bean_name: "aphiDeptEmpDataService"/* ,
				heads: JSON.stringify(heads) *///表头需要打印的查询条件,可以为空
				/* foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 */
				};
		
		$.each(grid.options.parms,function(i,obj){
				printPara[obj.name]=obj.value;
		});
		
		officeGridPrint(printPara);
	}
    
    
    //添加
    function addDeptEmpData(){
  		$.ligerDialog.open({
  			url: 'addHpmDeptEmpDataPage.do?isCheck=false',
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
    function deleteDeptEmpData(){
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
            	ajaxJsonObjectByUrl("deleteHpmDeptEmpData.do",{checkIds:checkIds.toString()},function (responseData){
            		if(responseData.state=="true"){
            			query();
            		}
            	});
        	}
        }); 
    }
	
    
    //下载导入模板
    function downTemplate(){
    	location.href = "downTemplateHpmDeptEmpData.do?isCheck=false";
    }
    
    
    //导入
    function importData(){
    	parent.$.ligerDialog.open({ url:'hrp/hpm/hpmdeptempdata/hpmDeptEmpDataImportPage.do?isCheck=false',
   			data:{columns : grid.columns, grid : grid},
   			height: 300,
   			width: 450,
   			title:'科室人数数据准备导入',
   			modal:true,
   			showToggle:false,
   			showMax:true,
			showMin: false,
			isResize:true,parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
    }
    
    
    //生成
    function createDeptEmpData(){
    	var acct_yearm=$("#acct_yearm").val();
    	var deptId = (liger.get("dept_id").getValue().split(",")[0]==null?"":liger.get("dept_id").getValue().split(",")[0]);
    	var deptNo = (liger.get("dept_id").getValue().split(",")[1]==null?"":liger.get("dept_id").getValue().split(",")[1]);
    	if(acct_yearm==''){
    		$.ligerDialog.warn('请选择核算年月');
    		return false;
    	}
    	/* if(liger.get("dept_id").getValue() == null ||liger.get("dept_id").getValue() == ''){
    		$.ligerDialog.warn('请选择科室名称');
    		return false;
    	} */
    	 
		$.ligerDialog.open({url: 'initHpmDeptEmpDataPage.do?isCheck=false&acct_yearm='+acct_yearm+'&dept_id='+deptId+'&dept_no='+deptNo, height: 200,width: 400, title:'生成',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveDeptEmpDataInit(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
    	
    }
    
    
    //计算
    function collect(){
    	var acct_yearm = $("#acct_yearm").val();
    	var deptId = (liger.get("dept_id").getValue().split(",")[0]==null?"":liger.get("dept_id").getValue().split(",")[0]);
    	var deptNo = (liger.get("dept_id").getValue().split(",")[1]==null?"":liger.get("dept_id").getValue().split(",")[1]);
    	if(acct_yearm == ""){
    		$.ligerDialog.warn('请选择年月');
    		return false;
    	}
    	/* if(liger.get("dept_id").getValue() == null ||liger.get("dept_id").getValue() == ''){
    		$.ligerDialog.warn('请选择科室名称');
    		return false;
    	} */
    	
    	$.ligerDialog.confirm('确定计算?', function (yes){
        	if(yes){
        		var param={
        				acct_yearm:acct_yearm,
        				dept_id:deptId,
        				dept_no:deptNo,
        		}
            	ajaxJsonObjectByUrl("collectHpmDeptEmpData.do?isCheck=false",param,function (responseData){
            		if(responseData.state=="true"){
            			query();
            		}
            	});
        	}
        }); 
    }
    
    //修改
    function openUpdate(dept_id,dept_no,acct_year,acct_month){
    	//实际代码中&temp替换主键
    	$.ligerDialog.open({ url: 'updateHpmDeptEmpDataPage.do?isCheck=false&dept_id='+dept_id+'&dept_no='+dept_no+'&acct_year='+acct_year+'&acct_month='+acct_month,
    			data:{}, height: 250,width: 400, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveEmpTargetData(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
    }
    
  	//字典下拉框
    function loadDict(){
    	autocomplete("#dept_id","../queryDeptDictByPerm.do?isCheck=false","id","text",true,true);   
    }  
    
	 //表格编辑事件
	function f_onAfterEdit(e){
		if(e.column.columnname === "emp_amount"){
			if(e.value == e.oldvalue){
				return false;
			}
			if(e.record.emp_amount == "" || e.record.emp_amount == null){
				$.ligerDialog.warn("职工人数为空！",'',{closeWhenEnter:false});
				return false;
			}
			
			var para = {
					emp_amount : e.record.emp_amount,
					dept_id : e.record.dept_id,
					dept_no : e.record.dept_no,
					acct_year : e.record.acct_year,
					acct_month : e.record.acct_month
					
				};
			ajaxJsonObjectByUrl("updateHpmDeptEmpData.do?isCheck=false", para, function(responseData){
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
			<td align="left" class="l-table-edit-td"><input name="acct_yearm" class="Wdate" type="text" id="acct_yearm" ltype="text"
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
