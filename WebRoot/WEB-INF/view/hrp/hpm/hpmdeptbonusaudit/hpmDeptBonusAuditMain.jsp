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
<style type="text/css">
.ccc .l-grid-row-cell{background: #F1D3F7;}
</style>
<script type="text/javascript">

	var grid;
	
	var gridManager = null;
	
	var import_hide = ${import_hide};

	//页面初始化
	$(function() {
		
		$("#acct_yearm").ligerTextBox({width : 160});autodate("#acct_yearm", "yyyymm"); 
		
		loadDict();//加载字典
		
		loadHead(null);//加载grid
		
		changeTe();
		
		$('#acct_yearm').bind('change',function(){changeDate();});

    	$('#dept_id').bind('change',function(){changeDate();});
    	
    	$('#item_code').bind('change',function(){changeDate();query();});
		
		toolbar();//加载工具栏
		
		loadHotkeys();//加载快捷键
	});
	
	
	//查询
	function query() {//根据表字段进行添加查询条件

		grid.options.parms = [];grid.options.newPage = 1;
		
		grid.options.parms.push({name : 'acct_yearm',value : $("#acct_yearm").val()});

		grid.options.parms.push({name : 'item_code',value : liger.get("item_code").getValue()});
		
		var dept_id = liger.get("dept_id").getValue();

		if (dept_id) {

			grid.options.parms.push({name : 'dept_id',value : dept_id.split(",")[0]});

			grid.options.parms.push({name : 'dept_no',value : dept_id.split(",")[1]});
		}
		
		grid.options.parms.push({name:'is_audits',value:$("#is_audits").val()});
		
		grid.options.parms.push({name:'item_note',value:$("#item_note").val()});
		
		grid.loadData(grid.where);
		
		grid_setColumns();
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
			
    		ajaxJsonObjectByUrl("querydataAudita.do?isCheck=false",param,function (responseData){
    			
    			if (responseData.is_audit==0) {
    				
					$("#is_audit").text("未审核");
					
    			}else if (responseData.is_audit==1){
    				
    				$("#is_audit").text("审核下达");
    				
    			}else{
    				
    				$("#is_audit").text(" ");
    				
    			};
    	
    			if (responseData.is_grant==0){
    				
    				$("#is_grant").text("未发放");
    				
    			}else if (responseData.is_grant==1){
    				
    				$("#is_grant").text("已发放");
    				
    			}else{
    				
    				$("#is_grant").text(" ");
    			}
    		});
		}

	}

	function changeTe(){
		
		var para = {acct_yearm:$("#acct_yearm").val()}
		
    	autocomplete("#dept_id","../queryDeptDictByPerm.do?&isCheck=false","id","text",true,true,para);
	}
	
	
	//加载grid
	function loadHead() {
		
		grid = $("#maingrid").ligerGrid({
			columns : [],
			dataAction : 'server',dataType : 'server',usePager : true,
			url : 'queryHpmDeptBonusAudit.do?isCheck=false',
			width : '100%',height : '100%',checkbox : true,rownumbers : true,
			enabledEdit : true,delayLoad : true,selectRowButtonOnly : true,//heightDiff: -10,
			rowClsRender:  function (rowdata,rowid)
            {
				
				//console.log(rowdata);
                if (rowdata.is_audit == "审核"){
                	
                    return "";
                    
                }else if(rowdata.is_audit == "新建"){
              
                	return "ccc";
                }
            }, 
			onDblClickRow : function (rowdata, rowindex, value)
			{
				openUpdate(
						$("#acct_yearm").val() + "@" + 
						rowdata.dept_id
					);
			} 
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	
	//工具栏
	function toolbar(){
       	var obj = [];
       	
       	obj.push({ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '添加（<u>A</u>）', id:'add', click: addScheme, icon:'add' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '删除（<u>D</u>）', id:'delete', click: deleteScheme,icon:'delete' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '下载导入模板（<u>T</u>）', id:'downTemplate', click: downTemplate,icon:'down',hide:import_hide });
       	obj.push({ line:true,hide:import_hide });
       	
       	obj.push({ text: '导入（<u>I</u>）', id:'import', click: importDeptBonusAudit,icon:'up',hide:import_hide});
       	obj.push({ line:true,hide:import_hide});
       	
       	obj.push({ text: '审核下达（<u>C</u>）', id:'audit', click: audit, icon:'audit' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '取消审核（<u>U</u>）', id:'reAudit', click: reAudit,icon:'back' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '打印（<u>P</u>）', id:'print', click: print,icon:'print' });
       	obj.push({ line:true });
       	
       	$("#toptoolbar").ligerToolBar({ items: obj});
	}
  	
	
	 //添加
    function addScheme(){
    	$.ligerDialog.open({
    		url: 'hpmDeptBonusauditAddPage.do?isCheck=false', 
    		height: 350,width: 500, title:'添加绩效审核工资',
    		modal:true,showToggle:false,showMax:false,
    		showMin: true,isResize:true,
    		buttons: [ 
    			{ text: '确定', onclick: 
     			function (item, dialog) { 
     				dialog.frame.save(); 
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
    function deleteScheme(){
		
    	var data = gridManager.getCheckedRows();
	       if (data.length == 0){
	           $.ligerDialog.error('请选择行');
	       	return ; 
	       }
	      
	       var checkIds =[];
	       var item_code;
	       $(data).each(function (){
	       	checkIds.push(
					this.dept_id + "@"+
					this.dept_no + "@"+
					this.acct_year + "@" +
					this.acct_month +"@"+
					liger.get("item_code").getValue()
					
	       	);
	       	
	       });
	       
	       $.ligerDialog.confirm('确定删除?', function (yes){
	       	if(yes){
	           	ajaxJsonObjectByUrl("deleteHpmDeptItem.do?isCheck=false",{checkIds:checkIds.toString()},function (responseData){
	           		if(responseData.state=="true")query();
	           	});
	       	}
	       }); 
  		
	 }
  	
  	//修改
    function openUpdate(obj){ 
  		
    	$.ligerDialog.open({ 
			url: 'updateHpmDeptItemPage.do?isCheck=false&dept_id='+obj+'&item_code='+liger.get("item_code").getValue(),data:{}, 
			height: 350,width: 500, title:'修改绩效工资审核',modal:true,
			showToggle:false,showMax:false,showMin: false,isResize:true,
			buttons: [ 
				{ text: '确定', onclick: 
					function (item, dialog) { 
						dialog.frame.save(); 
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
	 
	//键盘事件
	function loadHotkeys(){
		hotkeys('Q',query);
		hotkeys('C',audit);
		hotkeys('U',reAudit);
		hotkeys('P',print);
		hotkeys('T',downTemplate);
		hotkeys('I',importDeptBonusAudit);
	}
	
	
	function print() {

		if (grid.getData().length == 0) {
			$.ligerDialog.warn("请先查询数据！");
			return;
		}

		var printPara={
				title: "绩效工资审核",//标题
				columns: JSON.stringify(grid.getPrintColumns()),//表头
				class_name: "com.chd.hrp.hpm.service.AphiDeptBonusAuditService",
				method_name: "queryHpmDeptBonusAuditPrint",
				bean_name: "aphiDeptBonusAuditService"/* ,
				heads: JSON.stringify(heads) *///表头需要打印的查询条件,可以为空
				/* foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 */
				};
		
		$.each(grid.options.parms,function(i,obj){
				printPara[obj.name]=obj.value;
		});
		
		officeGridPrint(printPara);
	}
	
	
	//下载导入模板
	function downTemplate(){
		location.href = "downHpmDeptBonusAudit.do?isCheck=false";
	}
	
	
	//导入
	function importDeptBonusAudit() {
		
		var dept_kind_code = liger.get("dept_kind_code").getValue();
		parent.$.ligerDialog.open({ url : 'hrp/hpm/hpmdeptbonusaudit/hpmDeptBonusAuditImportPage.do?isCheck=false&dept_kind_code='+dept_kind_code,
			data:{columns : grid.columns, grid : grid}, height: 300,width: 450,title:'绩效工资审核导入',modal:true,showToggle:false,showMax:true,
			showMin: false,isResize:true,parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		}); 
		
	}
	
	
	//查询动态列
	function grid_setColumns() {

		ajaxJsonObjectByUrl("queryHpmDeptBonusAuditGrid.do?isCheck=false&item_code="+liger.get("item_code").getValue()+"&dept_id="+liger.get("dept_id").getValue(), {},function(responseData) {
			if (responseData != null) {
				grid.set('columns', responseData);
				grid.reRender();
			}
		});

	}
	
	
	//审核
	function audit() {
		
		var data = gridManager.getCheckedRows();
		
		var acct_yearm = $("#acct_yearm").val();
		
		if(!acct_yearm){$.ligerDialog.warn('请选择核算年月');return false;}
		
	    if (data.length > 0){//选复选框时
	    	
			var checkIds =[];var item_code;
	       
			$(data).each(function (){checkIds.push(this.dept_id + "@"+this.dept_no +"@"+liger.get("item_code").getValue());});
	       
	       	$.ligerDialog.confirm('确定审核?', function (yes){
		       	if(yes){
		           	ajaxJsonObjectByUrl("updateHpmDeptBonusAudit.do?isCheck=false&acct_yearm="+$("#acct_yearm").val()+"&audit_state=1",{checkIds:checkIds.toString()},function (responseData){
		           		if(responseData.state=="true"){
		           			query();
		           			changeDate();
		           		}
		           	});
		       	}
	       	});   

		} else{//不选复选框 ,全选
	    	
			var param = {
				acct_yearm : acct_yearm,
				audit_state:'1',
				item_codes:liger.get("item_code").getValue()
			};
			
			ajaxJsonObjectByUrl("updateHpmDeptBonusAudit.do", param, function(responseData) {
				if (responseData.state == "true") {
					query();
					changeDate();
				}
			});
	    	
		}

	}

	//反审核
	function reAudit() {
		
		var data = gridManager.getCheckedRows();
		
		var acct_yearm = $("#acct_yearm").val();
		
		if(!acct_yearm){$.ligerDialog.warn('请选择核算年月');return false;}
		
	    if (data.length > 0){//选复选框时
	    	
			var checkIds =[];var item_code;
	       
			$(data).each(function (){checkIds.push(this.dept_id + "@"+this.dept_no +"@"+liger.get("item_code").getValue());});
	       
	       	$.ligerDialog.confirm('确定反审核?', function (yes){
		       	if(yes){
		           	ajaxJsonObjectByUrl("updateHpmDeptBonusAudit.do?isCheck=false&acct_yearm="+$("#acct_yearm").val()+"&audit_state=0",{checkIds:checkIds.toString()},function (responseData){
		           		if(responseData.state=="true"){
		           			query();
		           			changeDate();
		           		}
		           	});
		       	}
	       	});   

		} else{//不选复选框 ,全选
		

			var acct_yearm = $("#acct_yearm").val();
			if(!acct_yearm){
				$.ligerDialog.warn('请选择核算年月');
				return false;
			}
			
			var param = {
				acct_yearm : acct_yearm,
				audit_state:'0',
				item_codes:liger.get("item_code").getValue()
			};
			
			ajaxJsonObjectByUrl("updateHpmDeptBonusAudit.do", param, function(responseData) {
				if (responseData.state == "true") {
					query();
					changeDate();
				}
			});
	
		}
	}
	
	//加载字典
	function loadDict() {

		var para = {
   			acct_yearm:$("#acct_yearm").val()
		}

    	autocomplete("#dept_id","../queryDeptDictByPerm.do?&isCheck=false","id","text",true,true,para);
		
		autocomplete("#dept_kind_code","../queryDeptKindDict.do?isCheck=false","id","text",true,true); 
		
		autocomplete("#item_code","../queryItemAllDict.do?&isCheck=false","id","text",true,true);
		
		$("#is_audits").ligerComboBox({width:160 });
		
		$("#item_note").ligerTextBox({width:160}); 
	}
	


</script>

</head>

<body style="padding: 0px; overflow: hidden;" onload="grid_setColumns();">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<table cellpadding="0" cellspacing="0" class="l-table-edit">
        <tr>
            <td align="right" class="l-table-edit-td" style="padding-left: 20px;">核算年月：</td>
            <td align="left" class="l-table-edit-td"><input class="Wdate" name="acct_yearm" type="text" id="acct_yearm" ltype="text" validate="{required:true,maxlength:20}" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyyMM',onpicked:changeDate()})" /></td>
            <td align="left"></td>
            
            <td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室名称：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            
        	<td align="right" class="l-table-edit-td" style="padding-left: 20px;">项目名称：</td>
            <td align="left" class="l-table-edit-td"><input name="item_code" type="text" id="item_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
           
        </tr>
        <tr>    
         	<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室分类：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_kind_code" type="text" id="dept_kind_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">审核状态：</td>
            <td align="left" class="l-table-edit-td">
            	<select name="is_audits" id="is_audits">
						<option value="">请选择</option>
						<option value="0">新建</option>
						<option value="1">审核</option>
				</select>
            </td>
            <td align="left"></td>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">备注：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="item_note" type="text" id="item_note" ltype="text"  />
            </td>
            <td align="left"></td>
            
        </tr>
        
        <tr>
        	<table>
        		<tr>
					 <td align="right" class="l-table-edit-td" style="padding-left: 20px;">当前科室绩效核算 审核状态:</td>
            		 <td align="left" class="l-table-edit-td"><span id="is_audit" style="color: red"></span></td>
           			 <td align="left"></td> 
           			 <td align="right" class="l-table-edit-td" style="padding-left: 20px;">发放状态：</td>
          			 <td align="left" class="l-table-edit-td"><span id="is_grant" style="color: red"></span></td>
          		     <td align="left"></td>      		
        		</tr>
        	</table>
            
        </tr>
        
    </table>

	<div id="toptoolbar"></div>
	<div id="maingrid"></div>
</body>
</html>
