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
		
    	var param={
   			target_nature:'01',
   			method_code:'01'
    	}
    	
    	autocomplete("#target_code","../queryTargetMethod.do?isCheck=false","id","text",true,true,param);
    	$("#is_audit").ligerComboBox({width:160 });
    	
    	autodate("#acct_yearm","yyyymm");
    	
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
    	grid.options.parms.push({name:'is_audit',value:$("#is_audit").val()}); 
    	$("#resultPrint > table > tbody").html("");
    	//加载查询条件
    	grid.loadData(grid.where);
	}
   
	//加载grid
	function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [ 
				{ display: '指标编码', name: 'target_code', align: 'left',render: 
					function (rowdata, rowindex, value){
						return "<a href='#' onclick=\"openUpdate('"
								+rowdata.target_code+"','"
								+rowdata.acct_year+"','"
								+rowdata.acct_month+"');\" >"
								+rowdata.target_code+"</a>";
					}
				},
				
				{ display: '指标名称', name: 'target_name', align: 'left'},
				
				{ display: '指标值', name: 'target_value', align: 'left'},
				
				{ display: '状态', name: 'is_audit', align: 'left',render: 
					function (rowdata, rowindex, value){
						if(rowdata.is_audit == 1){
                  			return "审核";
                  		}else{
                  			return "未审核";
                  		}
  						return "";
					}
				},
				
				{ display: '审核人', name: 'user_code', align: 'left'},
				
				{ display: '审核时间', name: 'audit_time', align: 'left'}
			],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryHpmHospTargetData.do',
			width: '100%',height: '100%',   checkbox: true,rownumbers:true,delayLoad:true,
			selectRowButtonOnly:true,//heightDiff: -10,
    		onDblClickRow : 
    			function (rowdata, rowindex, value){
					openUpdate(rowdata.target_code,rowdata.acct_year,rowdata.acct_month);//实际代码中temp替换主键
				} 
		});

        gridManager = $("#maingrid").ligerGetGridManager();
        formatTrueFalse();
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
				title: "院级指标数据采集",//标题
				columns: JSON.stringify(grid.getPrintColumns()),//表头
				class_name: "com.chd.hrp.hpm.service.AphiHospTargetDataService",
				method_name: "queryHospTargetDataPrint",
				bean_name: "aphiHospTargetDataService"/* ,
				heads: JSON.stringify(heads) *///表头需要打印的查询条件,可以为空
				/* foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 */
				};
		
		$.each(grid.options.parms,function(i,obj){
				printPara[obj.name]=obj.value;
		});
		
		officeGridPrint(printPara);
	}
    
  //工具栏
	function toolbar(){
       	var obj = [];
       	
       	obj.push({ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '添加（<u>A</u>）', id:'add', click: addHospTargetData, icon:'add' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '删除（<u>D</u>）', id:'delete', click: deleteHospTargetData,icon:'delete' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '生成（<u>G</u>）', id:'create', click: createHospTargetData,icon:'bookpen' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '审核（<u>C</u>）', id:'audit', click: check,icon:'audit' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '反审（<u>U</u>）', id:'unAudit', click: unCheck,icon:'back' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '打印（<u>P</u>）', id:'print', click: print,icon:'print' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '下载导入模板（<u>T</u>）', id:'downTemplate', click: downTemplate,icon:'down' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '导入（<u>I</u>）', id:'import', click: importData,icon:'up' });
       	obj.push({ line:true });
       	
       	$("#toptoolbar").ligerToolBar({ items: obj});
	}
  
	//键盘事件
	function loadHotkeys(){
		
		hotkeys('Q',query);
		hotkeys('G',createHospTargetData);
		hotkeys('A',addHospTargetData);
		
		hotkeys('C',check);
		hotkeys('U',unCheck);
		hotkeys('P',print);
		
		hotkeys('D',deleteHospTargetData);
		hotkeys('T',downTemplate);
		hotkeys('I',importData);
	}
    
	//添加
	function addHospTargetData(){
  		$.ligerDialog.open({
  			url: 'hpmHospTargetDataAddPage.do?isCheck=false', 
  			height: 350,width: 500, title:'添加',modal:true,showToggle:false,
  			showMax:false,showMin: true,isResize:true,
  			buttons: [ 
  				{ text: '确定', onclick: 
  					function (item, dialog) { 
  						dialog.frame.saveHospTargetData(); 
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
	function deleteHospTargetData(){
		
		var data = gridManager.getCheckedRows();
    	var acct_year = $("#acct_yearm").val();
    	
    	if(acct_year == ""){
    		$.ligerDialog.warn('请选择年月');
    	 	return;
    	}
        
        if (data.length == 0){
        	$.ligerDialog.warn('请选择行');
        	return;
        }

        var checkIds =[];
        
        $(data).each(function (){
        	checkIds.push(this.target_code+";"+acct_year);//实际代码中temp替换主键
        });
        
        $.ligerDialog.confirm('确定删除?', function (yes){
        	if(yes){
        		ajaxJsonObjectByUrl("deleteHpmHospTargetData.do",{checkIds:checkIds.toString()},function (responseData){
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
		var acct_yearm = $("#acct_yearm").val();
		
   	 	/* if(data.length == 0){
   		 	$.ligerDialog.warn('请选择行');
   		 	return ; 
   	 	} */
   	 
   	 	if (acct_yearm == ""){
        	$.ligerDialog.warn('请选择核算年月');
        	return false;
        }
   	 
   	 	var checkIds =[];
   	 
   	 	if(data.length == 0){
   		 	checkIds.push(""+";"+acct_yearm+";1");
   	 	}else{
   		 	$(data).each(function (){
            	checkIds.push(this.target_code+";"+acct_yearm+";1");
            });
   	 	}

        ajaxJsonObjectByUrl("shenhe.do?isCheck=false",{checkIds:checkIds.toString()},function (responseData){
           if(responseData.state=="true"){
           	 query();
           }
        });
	}
	
	
	//反审
	function unCheck(){
		var data = gridManager.getCheckedRows();
   	 
        /* if(data.length == 0){
        	$.ligerDialog.warn('请选择行');
        	return ; 
        } */
   	 
		var acct_yearm = $("#acct_yearm").val();
	   	 
		if (acct_yearm == ""){
			$.ligerDialog.warn('请选择核算年月');
        	return false;
		}
   	 
		var checkIds =[];
	   	 
		if(data.length == 0){
			checkIds.push(""+";"+acct_yearm+";0");
		}else{
			$(data).each(function (){
   				checkIds.push(this.target_code+";"+acct_yearm+";0");
			});
		}
	
        ajaxJsonObjectByUrl("shenhe.do?isCheck=false",{checkIds:checkIds.toString()},function (responseData){
           if(responseData.state=="true"){
           	 query();
           }
        });
	}
	
	
	//下载导入模板
	function downTemplate(){
		location.href = "downTemplateHospTargetData.do?isCheck=false";
	}
	
	//导入
	function importData(){
       	$.ligerDialog.open({url: 'hospTargetDataImportPage.do?isCheck=false', height: 430,width: 760, isResize:true, title:'导入'});
	}
	
	
	//生成
	function createHospTargetData(){
		
		var acct_yearm=$("#acct_yearm").val();
    	var target_code = liger.get("target_code").getValue()? liger.get("target_code").getValue():'null';
    	
    	if(acct_yearm==''){
    		$.ligerDialog.warn('请选择核算年月');
    		return false;
    	}
    	
    	var paras = acct_yearm+"@"+target_code;
        
    	$.ligerDialog.open({
    		url: 'hpmHospTargetDataChoosePage.do?isCheck=false&paras='+paras, 
    		height: 200,width: 400, title:'生成',modal:true,showToggle:false,
    		showMax:false,showMin: true,isResize:true,
    		buttons: [ 
    			{ text: '确定', onclick: 
    				function (item, dialog) { 
    					dialog.frame.saveHospTargetConf(); 
    				},cls:'l-dialog-btn-highlight' 
    			}, 
    			{ text: '取消', onclick: 
    				function (item, dialog) { 
    					dialog.close(); 
    				} 
    			} 
    		] 
    	});
        
    	/* ajaxJsonObjectByUrl("getHospTargetValue.do?isCheck=false", formPara, function(responseData) {
    		if (responseData.state == "false") {
    		}else{
    			$.ligerDialog.warn('该月指标已审核，不能进行生成');
    		}
    	}); */
	}
	
	
    function openUpdate(target_code,acct_year,acct_month){
    	
		$.ligerDialog.open({
			url: 'hpmHospTargetDataUpdatePage.do?isCheck=false&target_code='
					+target_code+'&acct_year='+acct_year+'&acct_month='+acct_month,
			data:{}, 
			height: 350,width: 500, title:'修改',modal:true,showToggle:false,
			showMax:false,showMin: false,isResize:true,
			buttons: [ 
				{ text: '确定', onclick: 
					function (item, dialog) { 
						dialog.frame.saveHospTargetData(); 
					},cls:'l-dialog-btn-highlight' 
				}, 
				{ text: '取消', onclick: 
					function (item, dialog) { 
						dialog.close(); 
					} 
				} 
			] 
		});

    	/* var formPara={
    			acct_yearm:acct_year+""+acct_month
          };
    	ajaxJsonObjectByUrl("getHospTargetValue.do?isCheck=false", formPara, function(responseData) {
    		if (responseData.state == "false") {
    			
    		}else{
    			$.ligerDialog.warn('该月指标已审核，不能进行生成');
    		}
    	}) */
    }
    
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">核算年月：</td>
			<td align="left" class="l-table-edit-td"><input class="Wdate" name="acct_yearm" type="text" id="acct_yearm" ltype="text"
				validate="{required:true,maxlength:20}" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyyMM'})" /></td>
			<td align="left"></td>
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
