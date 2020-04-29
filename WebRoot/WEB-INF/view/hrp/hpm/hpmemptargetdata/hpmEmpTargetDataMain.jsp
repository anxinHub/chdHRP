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
        loadDict();//加载下拉框
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
    	grid.options.parms.push({name:'target_code',value:liger.get("target_code").getValue()}); 
    	grid.options.parms.push({name:'dept_id',value:liger.get("dept_code").getValue() ? liger.get("dept_code").getValue().split(',')[0] : ''}); 
    	grid.options.parms.push({name:'dept_no',value:liger.get("dept_code").getValue() ? liger.get("dept_code").getValue().split(',')[1] : ''}); 
    	
    	grid.options.parms.push({name:'emp_id',value:liger.get("emp_code").getValue() ? liger.get("emp_code").getValue().split(',')[0] : ''}); 
    	grid.options.parms.push({name:'emp_no',value:liger.get("emp_code").getValue() ? liger.get("emp_code").getValue().split(',')[1] : ''}); 
    	grid.options.parms.push({name:'is_audit',value:$("#is_audit").val()}); 
    	//加载查询条件
    	grid.loadData(grid.where);
    }
	
    
    //加载grid
	function loadHead(){
    	grid = $("#maingrid").ligerGrid({
        	columns: [ 
				{display: '科室名称', name: 'dept_name', align: 'left',
					render: function (rowdata, rowindex, value){
						return "<a href='#' onclick=\"openUpdate('"+
							rowdata.target_code+"','"+
							rowdata.emp_id+"','"+
							rowdata.emp_no+"','"+
							rowdata.acct_year+"','"+
							rowdata.acct_month +"','"+
							rowdata.target_value 
						+"');\" >"+rowdata.dept_name+"</a>";
					  }
				},
				{ display: '职工名称', name: 'emp_name', align: 'left'},
               	{ display: '指标编码', name: 'target_code', align: 'left'},
                { display: '指标名称', name: 'target_name', align: 'left'},
                { display: '指标值', name: 'target_value', align: 'left'},
                { display: '状态', name: 'is_audit', align: 'left',render: 
                	function (rowdata, rowindex, value){
                    	if(rowdata.is_audit == 1){
                      		return "审核";
                      	}else{
                      		return "未审核";
                      	}
  			    	}
				},
                { display: '审核人', name: 'check_name', align: 'left'},
                { display: '审核时间', name: 'audit_time', align: 'left'}
            ],
            dataAction: 'server',dataType: 'server',usePager:true,url:'queryHpmEmpTargetData.do',
            width: '100%',height: '100%',   checkbox: true,rownumbers:true,delayLoad:true,
            selectRowButtonOnly:true,//heightDiff: -10,
    		onDblClickRow : function (rowdata, rowindex, value){
    			openUpdate(
    				rowdata.target_code,
    				rowdata.emp_id,
    				rowdata.emp_no,
    				rowdata.acct_year,
    				rowdata.acct_month,
    				rowdata.target_value
    			);//实际代码中temp替换主键
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
       	
       	obj.push({ text: '添加（<u>A</u>）', id:'add', click: addEmpTargetData, icon:'add'});
       	obj.push({ line:true});
       	
       	obj.push({ text: '删除（<u>D</u>）', id:'delete', click: deleteEmpTargetData,icon:'delete'});
       	obj.push({ line:true});
       	
       	obj.push({ text: '生成（<u>G</u>）', id:'init', click: createEmpTargetData,icon:'bookpen'});
       	obj.push({ line:true});
       	
       	/* obj.push({ text: '指标值查看（<u>V</u>）', id:'targetView', click: targetView,icon:'bookpen' });
       	obj.push({ line:true }); */
       	
       	obj.push({ text: '审核（<u>C</u>）', id:'shenhe', click: check,icon:'right'});
       	obj.push({ line:true});
       	
       	obj.push({ text: '反审（<u>U</u>）', id:'noshenhe', click: unCheck,icon:'back'});
       	obj.push({ line:true});
       	
       	obj.push({ text: '下载导入模板（<u>T</u>）', id:'downTemplate', click: downTemplate,icon:'down'});
       	obj.push({ line:true});
       	
       	obj.push({ text: '导入（<u>I</u>）', id:'import', click: importData,icon:'up'});
       	obj.push({ line:true});
       	
       	obj.push({ text: '打印（<u>P</u>）', id:'print', click: print,icon:'print'});
       	obj.push({ line:true });
       	
       	$("#toptoolbar").ligerToolBar({ items: obj});
	}
	
	//键盘事件
	function loadHotkeys(){
		
		hotkeys('Q',query);
		hotkeys('G',createEmpTargetData);
		hotkeys('A',addEmpTargetData);
		hotkeys('D',deleteEmpTargetData);
		hotkeys('C',check);
		hotkeys('U',unCheck);
		hotkeys('P',print);
		hotkeys('T',downTemplate);
		hotkeys('I',importData);
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
				title: "职工指标数据采集",//标题
				columns: JSON.stringify(grid.getPrintColumns()),//表头
				class_name: "com.chd.hrp.hpm.service.AphiEmpTargetDataService",
				method_name: "queryEmpTargetDataPrint",
				bean_name: "aphiEmpTargetDataService"/* ,
				heads: JSON.stringify(heads) *///表头需要打印的查询条件,可以为空
				/* foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 */
				};
		
		$.each(grid.options.parms,function(i,obj){
				printPara[obj.name]=obj.value;
		});
		
		officeGridPrint(printPara);
	}
	
	
	//添加
	function addEmpTargetData(){
  		$.ligerDialog.open({
  			url: 'hpmEmpTargetDataAddPage.do?isCheck=false', 
  			height: 450,width: 500, title:'添加',modal:true,showToggle:false,
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
	function deleteEmpTargetData(){
		
		var acct_year = $("#acct_yearm").val();
    	if(acct_year == ""){
    		$.ligerDialog.warn('请选择年月');
    		return false;
    	}
        var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.warn('请选择行');
        	return false;
        }
        

        var checkIds =[];
        $(data).each(function (){
        	checkIds.push(this.target_code+";"+this.emp_id +";"+this.emp_no +";"+acct_year);//实际代码中temp替换主键
        });
        $.ligerDialog.confirm('确定删除?', function (yes){
        	if(yes){
        		ajaxJsonObjectByUrl("deleteHpmEmpTargetData.do",{checkIds:checkIds.toString()},function (responseData){
            		if(responseData.state=="true"){
            			query();
            		}
            	});
        	}
        }); 
	}
	
	//审核
	function check(){
		var data = gridManager.getCheckedRows();
    	if(data.length == 0){
    		$.ligerDialog.warn('请选择行 ');
    		return ; 
    	}
    	$.ligerDialog.confirm('确定审核吗?',function(yes){
    		
    		if(yes){
              	var checkIds =[];
              	$(data).each(function (){
                	checkIds.push(
                		this.group_id+";"+
                		this.hos_id+";"+
                		this.copy_code+";"+
                		this.acct_year+";"+
                		this.acct_month+";"+
                		this.target_code+";"+
                		this.emp_id+";"+
                		this.emp_no+";"+"1"
                	);
                });
              	 
				ajaxJsonObjectByUrl("shenhe.do?isCheck=false",{checkIds:checkIds.toString()},function (responseData){
                    if(responseData.state=="true"){
                    	 query();
                    }
                });
    		}
    	});
	}
	
	//反审
	function unCheck(){
		var data = gridManager.getCheckedRows();
       	if(data.length == 0){
       		$.ligerDialog.warn('请选择行 ');
       		return ; 
       	}
       	$.ligerDialog.confirm('确定取消审核?',function(yes){
       		
       		if(yes){
            	var checkIds =[];
              	$(data).each(function (){
                 	checkIds.push(
                 		this.group_id+";"+
                 		this.hos_id+";"+
                 		this.copy_code+";"+
                 		this.acct_year+";"+
                 		this.acct_month+";"+
                 		this.target_code+";"+
                 		this.emp_id+";"+
              			this.emp_no+";"+"0"
                 	);
                });
                 	 
				ajaxJsonObjectByUrl("shenhe.do?isCheck=false",{checkIds:checkIds.toString()},function (responseData){
                	if(responseData.state=="true"){
                    	query();
                	}
                });
       		}
       	});
	}
	
	
	//下载导入模板
	function downTemplate(){
		location.href = "downTemplateEmpTargetData.do?isCheck=false";
	}
	
	
	//导入
	function importData(){
		parent.$.ligerDialog.open({ url : 'hrp/hpm/hpmemptargetdata/hpmEmpTargetDataImportPage.do?isCheck=false',
			data:{columns : grid.columns, grid : grid}, height: 300,width: 450,title:'职工指标数据采集导入',modal:true,showToggle:false,showMax:true,
			showMin: false,isResize:true,parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
	}
	
	//生成
	function createEmpTargetData(){
		var acct_yearm=$("#acct_yearm").val();
    	
    	var target_code = liger.get("target_code").getValue()? liger.get("target_code").getValue():'null';
    	
    	var emp_id = liger.get("emp_code").getValue()? liger.get("emp_code").getValue().split(",")[0]:'null';
    	var emp_no = liger.get("emp_code").getValue()? liger.get("emp_code").getValue().split(",")[1]:'null';
    	
    	if(acct_yearm==''){
    		
    		$.ligerDialog.warn('请选择核算年月');
    		
    		return false;
    	}
    	
    	var paras = acct_yearm+"@"+target_code+"@"+emp_id + "@" + emp_no;

    	$.ligerDialog.open({url: 'hpmEmpTargetDataChoosePage.do?isCheck=false&paras='+paras, height: 200,width: 400, title:'生成',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveHospTargetConf(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
        
    	
	}
	
	
	//修改
    function openUpdate(target_code,emp_id,emp_no,acct_year,acct_month,target_value){
    	
    	$.ligerDialog.open({ 
    		url: 'hpmEmpTargetDataUpdatePage.do?isCheck=false'+
    				'&target_code='+target_code+
    				'&emp_id='+emp_id+
    				'&emp_no='+emp_no+
    				'&acct_year='+acct_year+
    				'&acct_month='+acct_month +
    				'&target_value='+target_value
    				,data:{}, height: 350,width: 500, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveEmpTargetData(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

   
    }
	
	//加载下拉框
    function loadDict(){
            //字典下拉框
    	var param={
    			target_nature:"04",
    			method_code:"01"
    	}
            
    	autocomplete("#target_code","../queryTargetMethod.do?isCheck=false","id","text",true,true,param);
    	autocomplete("#emp_code","../queryEmpDict.do?isCheck=false","id","text",true,true);
    	$("#acct_yearm").ligerTextBox({width:160 });
    	$("#is_audit").ligerComboBox({width:160 });
    	autodate("#acct_yearm","yyyymm");
    	changeAcctYear();//加载科室下拉框
    	
	}
    
  	//核算年月绑定事件
    function changeAcctYear(){
	  
	  	var para = {
	  		acct_yearm:$("#acct_yearm").val()
	  	};
    	autocomplete("#dept_code","../queryDeptDictTime.do?isCheck=false","id","text",true,true,para);
    }
</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<table cellpadding="0" cellspacing="0" class="l-table-edit">

		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">核算年月：</td>
			<td align="left" class="l-table-edit-td"><input name="acct_yearm" type="text" class="Wdate" id="acct_yearm" ltype="text"
				validate="{required:true,maxlength:20}" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyyMM'})" onchange="changeAcctYear()"/></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室名称：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_code" type="text" id="dept_code" ltype="text" validate="{required:true,maxlength:20}"/></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">职工名称：</td>
			<td align="left" class="l-table-edit-td"><input name="emp_code" type="text" id="emp_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
		</tr>

		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">指标名称：</td>
			<td align="left" class="l-table-edit-td"><input name="target_code" type="text" id="target_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">审核状态：</td>
			<td align="left" class="l-table-edit-td"><select name="is_audit" id="is_audit">
					<option value="">请选择</option>
					<option value="0">否</option>
					<option value="1">是</option>
			</select></td>
			<td align="left"></td>
		</tr>
	</table>
	
	<div id="toptoolbar"></div>
	<div id="maingrid"></div>
</body>
</html>
