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
    $(function ()
    { 
        loadDict();//加载下拉框
    	//加载数据
    	loadHead(null);	
		loadHotkeys();
        query();
	});
    
    //查询
    function  query(){
    	grid.options.parms=[];
    	grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'group_id', value:$("#group_id").val()}); 
    	grid.options.parms.push({name:'hos_id', value:$("#hos_id").val()}); 
    	grid.options.parms.push({name:'set_id', value:$("#set_id").val()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
	}
	
	function loadHead(){
		grid = $("#maingrid").ligerGrid({
			columns: [ { 
        	   display: '仓库编码', name: 'store_code', align: 'left'
			}, { 
				display: '仓库名称', name: 'store_name', align: 'left'
			}, {
				display: '是否结账', name: 'is_account', align: 'center', 
				render : function(rowdata, rowindex, value) {
					if(value == 1){
						return "<input name='is_account"+rowindex+"' checked='checked' type='checkbox' id='is_account"+rowindex+"' ltype='text'"
							+" style='margin-top:5px;' />";
					}
					return "<input name='is_account"+rowindex+"' type='checkbox' id='is_account"+rowindex+"' ltype='text'"
						+" style='margin-top:5px;' />";
				}
			} ],
			dataAction: 'server',dataType: 'server', 
			usePager: false, url:'queryMatStoreDetail.do?isCheck=false',
			width: '100%', height: '93%', checkbox: false, rownumbers: true, 
			selectRowButtonOnly:true,//heightDiff: -10,
			delayLoad: true,//初始化不加载，默认false
			toolbar: { items: [
			    					{ text: '保存（<u>A</u>）', id:'add', click: save, icon:'add' },
			    	                { line:true },
			    			]}
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
	
	function save(){
		var param = '';
		var targetMap = new HashMap();
		var date = gridManager.getData();
		
		if(date.length != 0){
			$.each(date,function(rowindex, item){
				var is_account = $("#is_account"+rowindex).prop("checked") ? 1 : 0;
				//var is_account = $("#is_account"+rowindex).is(":checked")? 1 : 0;
				
				if(is_account == 1){
					param+=item.store_id+','+item.store_name+"@";
				}
			});
		}
		
		if(param == ""){
			$.ligerDialog.warn("请勾选要结账的库房");
			return ; 
		}
		
		var formPara = {
	        group_id : $("#group_id").val(),
	        hos_id : $("#hos_id").val(),
			set_id : $("#set_id").val(),
			storeData : param
	 	};
	 	ajaxJsonObjectByUrl("saveMatStoreSetAccount.do?isCheck=false", formPara, function(responseData) {
			if (responseData.state == "true") {
				query();
	 		}
		});
    }
	
    function loadDict(){
        //字典下拉框
    	$("#set_code").ligerTextBox({width:160, disabled:true});
    	$("#set_name").ligerTextBox({width:160, disabled:true});
		$("#save").ligerButton({click: save, width: 90});
		$("#close").ligerButton({click: this_close, width: 90});
    }
    
    //键盘事件
	function loadHotkeys() {
		hotkeys('S', save);
		hotkeys('C', this_close);
	}
    
	function this_close() {
		frameElement.dialog.close();
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">编码：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="group_id" type="hidden" id="group_id" value='${group_id}' />
            	<input name="hos_id" type="hidden" id="hos_id" value='${hos_id}' />
            	<input name="set_id" type="hidden" id="set_id" value='${set_id}' />
            	<input name="set_code" type="text" id="set_code" value='${set_code}' disabled="disabled" ltype="text" validate="{required:true}" />
            </td>
            <td align="left"></td>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">名称：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="set_name" disabled="disabled" type="text" id="set_name" value='${set_name}' ltype="text" validate="{required:true}" />
            </td>
            <td align="right"></td>
        </tr> 
    </table>
	
	<div style="width: 100%; height: 100%;">
		<div id="maingrid"></div>
		<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" style="margin-top: 5px;">		
		</table>
	</div>
</body>
</html>
