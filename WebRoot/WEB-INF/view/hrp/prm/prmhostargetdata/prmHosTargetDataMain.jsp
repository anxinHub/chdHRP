<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    var clicked = 0;
    
    //页面初始化加载
    $(function (){
    	$('body').height("100%");
        loadDict()//加载下拉框
    	
    	loadHead(null);	//加载grid
    	toolbar();//加载工具栏
    	loadHotkeys();//加载快捷键
    	
    	
        $("#target_code").ligerTextBox({width:160});
        $("#is_audit").ligerComboBox({width:160 });
        $("#hos_id").ligerComboBox({width:160 });
        $("#acc_year").ligerTextBox({width:160});
    });
    
    //查询
	function  query(){
    	
        //根据表字段进行添加查询条件
		grid.options.parms=[];
    	grid.options.newPage=1;
    	
    	grid.options.parms.push({name:'acc_year',value:$("#acc_year").val()}); 
    	grid.options.parms.push({name:'target_code',value:liger.get("target_code").getValue()}); 
    	grid.options.parms.push({name:'check_hos_id',value:liger.get("check_hos_id").getValue()}); 
    	grid.options.parms.push({name:'is_audit',value:$("#is_audit").val()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
	}
  	
	//加载gird
	function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [ 
        		{ display: '单位信息', name: 'check_hos_name', align: 'left'},
        		
                { display: '指标编码', name: 'target_code', align: 'left'},
                     
                { display: '指标名称', name: 'target_name', align: 'left'},
                     
                { display: '指标值', name: 'target_value', align: 'left',editor:{type: 'float' ,precision : 4},render : 
                	function(rowdata, rowindex,value) {
                	
	                	var col = arguments[arguments.length -1];
	                	if(rowdata.is_audit == 1){
	                   		rowdata.notEidtColNames.push(col.columnname);
	                    }
	                    
	                	return rowdata.target_value == null || rowdata.target_value == "" ? "" : formatNumber(rowdata.target_value,2,1);
	                }
				},
                
				{ display: '审核状态', name: 'is_audit', align: 'left',render : 
					function(rowdata, rowindex, value) {
						if(rowdata.is_audit == 0){
							return "未审核";
						}else{
							return "审核"
						}
					}
				},
                
				{ display: '审核人', name: 'user_name', align: 'left'},
                 
				{ display: '审核时间', name: 'audit_date', align: 'left'}
			],
            dataAction: 'server',dataType: 'server',usePager:true,url:'queryPrmHosTargetPrmTargetData.do',
            width: '100%', height: '100%', checkbox: true,rownumbers:true,enabledEdit: true,delayLoad:true,
            selectRowButtonOnly:true,//heightDiff: -10,
            
		});
        
        gridManager = $("#maingrid").ligerGetGridManager();
       
    }
	
	
	//工具栏
	function toolbar(){
		var obj = [];
		
		obj.push({ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '生成（<u>C</u>）', id:'create', click: createHostarget, icon:'bookpen' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '删除（<u>D</u>）', id:'delete', click: deleteHostarget,icon:'delete' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '下载导入模板（<u>T</u>）', id:'downTemplate', click:downTemplateHostarget,icon:'down' });
       	obj.push({ line:true });
       	
       	obj.push({text : '导入（<u>O</u>）',id : 'import',click : importHostarget,icon : 'up'});
       	obj.push({ line:true });
       	
       	obj.push({ text: '审核（<u>A</u>）', id:'audit', click:auditHostarget,icon:'audit' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '反审核（<u>B</u>）', id:'reAudit', click:reAuditHostarget,icon:'back' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '保存（<u>S</u>）', id:'save', click:saveHostarget,icon:'save' });
       	obj.push({ line:true });
       	
       	$("#toptoolbar").ligerToolBar({ items: obj});
	}
	
	
	
    //键盘事件
	function loadHotkeys(){
		
		hotkeys('Q',query);
		hotkeys('D',deleteHostarget);
		hotkeys('O',importHostarget);
		hotkeys('T',downTemplateHostarget);
        hotkeys('A',auditHostarget);
        hotkeys('B',reAuditHostarget);
        hotkeys('C',createHostarget);
        hotkeys('S',saveHostarget);
		 
	}
    
    //保存
    function saveHostarget(){

    	var data = gridManager.getUpdated();
   
    	if(data.length==0){
    		$.ligerDialog.warn('沒有数据更新');
    		return ; 
    	}
		
    	var ParamVo = [];
		
    	$(data).each(function() {
			var target_value = this.target_value == ""?"0":this.target_value;
			
			ParamVo.push(
				this.group_id + "@" + 
				this.hos_id + "@" + 
				this.copy_code + "@" + 
				this.acc_year + "@" + 
				this.acc_month + "@" + 
				this.target_code + "@" + 
				this.check_hos_id + "@" + 
				target_value
			);
		});

		ajaxJsonObjectByUrl("saveBatchPrmHosTargetData.do", {ParamVo : ParamVo.toString()}, function(responseData) {
			if (responseData.state == "true") {
				query();
			}
		});
	}

	//删除
	function deleteHostarget() {
		
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.warn('请选择行');
			return ; 
		} 
		
		var ParamVo = [];
		
		$(data).each(function() {
			if(isnull(this.group_id)){
				gridManager.deleteSelectedRow();
			}else{
				ParamVo.push(
					this.group_id + "@" + 
					this.hos_id + "@"+ 
					this.copy_code + "@" + 
					this.acc_year + "@" + 
					this.acc_month + "@" + 
					this.target_code + "@" + 
					this.check_hos_id
				);
			}
		});
		
		if(ParamVo == ""){
			return;
		}
			
		$.ligerDialog.confirm('确定删除?', function(yes) {
			if (yes) {
				ajaxJsonObjectByUrl("deletePrmHosTargetData.do", {ParamVo : ParamVo.toString()}, function(responseData) {
					if (responseData.state == "true") {
						query();
					}
				});
			}
		});
		
	}
	
	//导入
	function importHostarget() {
		
		parent.$.ligerDialog.open({ url : 'hrp/prm/prmhostargetdata/prmHosTargetDataImportPage.do?isCheck=false',
			data:{columns : grid.columns, grid : grid}, height: 300,width: 450,title:'院级指标数据采集导入',modal:true,showToggle:false,showMax:true,
			showMin: false,isResize:true,parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		}); 
		
	}

	//下载导入模板
	function downTemplateHostarget() {

		location.href = "downTemplate.do?isCheck=false";

	}
	
	//审核
	function auditHostarget() {
		
		var data = gridManager.getData();
		if(data.length == 0){
			$.ligerDialog.warn('未找到审核的数据 ');
			return ; 
		}
		
		
		var year_month = $('#acc_year').val();
		var param = {
			acc_year : year_month.substring(0,4),
			acc_month : year_month.substring(4,6)
		};
		
		var checkedRows = gridManager.getCheckedRows();
		if(checkedRows.length != 0){
			param['checkedRows'] = JSON.stringify(checkedRows);
		}
		
		$.ligerDialog.confirm('确定审核?', function(yes) {
			if (yes) {
				ajaxJsonObjectByUrl("auditPrmHosTargetData.do", param, function(responseData) {
					if (responseData.state == "true") {
						query();
					}
				});
			}
		});
		
	}
	
	//反审核
	function reAuditHostarget() {
		
		var data = gridManager.getData();
		if(data.length == 0){
			$.ligerDialog.warn('未找到反审核的数据 ');
			return ; 
		}
		
		
		var year_month = $('#acc_year').val();
		var param = {
			acc_year : year_month.substring(0,4),
			acc_month : year_month.substring(4,6)
		};
		
		var checkedRows = gridManager.getCheckedRows();
		if(checkedRows.length != 0){
			param['checkedRows'] = JSON.stringify(checkedRows);
		}
		
		$.ligerDialog.confirm('确定反审核?', function(yes) {
			if (yes) {
				ajaxJsonObjectByUrl("reAuditPrmHosTargetData.do", param, function(responseData) {
					if (responseData.state == "true") {
						query();
					}
				});
			}
		});
	}
	
	//生成
	function createHostarget() {

		if ($("#acc_year").val() == "") {
			$.ligerDialog.warn("核算年月不能为空");
			return false;
		}
		
		$.ligerDialog.confirm('确定生成吗?',function(yes){
			if(yes){
				var ParamVo = [];
				ParamVo.push(
					$("#acc_year").val()
				)

				ajaxJsonObjectByUrl("createPrmHosTargetData.do", {ParamVo : ParamVo.toString()}, function(responseData) {
					if (responseData.state == "true") {
						query();
					}
				});
			}
		});

	}

	function loadDict() {

		autocomplete("#target_code", "../quertPrmTargetDict.do?isCheck=false","id", "text", true, true, "", false, "", "300");

		autocomplete("#check_hos_id","../quertSysHosInfoDict.do?isCheck=false", "id", "text", true,true, "", false);
		autodate("#acc_year","yyyymm");
	}
	
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
            <td align="right" class="l-table-edit-td" style="padding-left: 20px;">核算年月：</td>
			<td align="left" class="l-table-edit-td"><input class="Wdate" name="acc_year" type="text" id="acc_year" ltype="text"
				 onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyyMM'})" style="width: 160px;" /></td>
			<td align="left"></td>
			
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">指标名称：</td>
            <td align="left" class="l-table-edit-td"><input name="target_code" type="text" id="target_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">考核单位：</td>
            <td align="left" class="l-table-edit-td"><input name="check_hos_id" type="text" id="check_hos_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        
        <tr>
           <td align="right" class="l-table-edit-td"  style="padding-left:20px;">审核状态：</td>
            <td align="left" class="l-table-edit-td">
            	<select id="is_audit" name="is_audit" style="width: 135px;">
                	<option value="">全部</option>
			        <option value="0">未审核</option>
			        <option value="1">已审核</option>
               </select></td>
            <td align="left"></td>
        </tr>
        
    </table>
	
	<div id="toptoolbar" ></div>
	<div id="maingrid"></div>
</body>
</html>
