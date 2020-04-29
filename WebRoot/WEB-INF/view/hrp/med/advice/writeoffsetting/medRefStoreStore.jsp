<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script src="<%=path%>/lib/hrp/med/med.js"	type="text/javascript"></script>
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    var isEdit = false;
    var isFrist = true;
    
    $(function ()
    {
        loadDict()//加载下拉框
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
		grid.options.parms.push({
			name : 'sec_store_id',
			value : liger.get("sec_store_code").getValue() == null ? "" : liger.get("sec_store_code").getValue().split(",")[0]
		});
    	//加载查询条件
    	grid.loadData(grid.where);
    	//重置变量
    	isFrist = true;
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [{
					display: 'old_id', name: 'old_id', width: 20, align: 'left', isAllowHide: false, hide: true
				}, {
					display: '二级库(E)', name: 'sec_store_id', textField: 'sec_store_name', width: 200, align: 'left', 
					editor: {
						type: 'select',
						valueField: 'id',
						textField: 'text',
						url: 'queryMedRefStoreList.do?isCheck=false',//&is_sec=1',  //is_sec是否过滤已存在的二级库
						keySupport: true,
						autocomplete: true,
					} 
				}, {
					display: '大库(E)', name: 'store_id', textField: 'store_name', width: 200, align: 'left', 
					editor: {
						type: 'select',
						valueField: 'id',
						textField: 'text',
						url: 'queryMedRefStoreList.do?isCheck=false',
						keySupport: true,
						autocomplete: true, 
					} 
				}
		 	],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMedRefStoreStore.do',
			width: '100%', height: '100%', checkbox: true,rownumbers:true, enabledEdit: isEdit, 
			onBeforeEdit: f_onBeforeEdit, onBeforeSubmitEdit: f_onBeforeSubmitEdit, 
			onChangeRow: f_onChangeRow, onAfterEdit: f_onAfterEdit,
			delayLoad: true,//初始化不加载，默认false
			selectRowButtonOnly:true,//heightDiff: -10,
			toolbar: { items: [
			   	{ text: '查询（<u>Q</u>）', id: 'query', click: query, icon: 'search' },
				{ line: true },
				{ text: '编辑（<u>E</u>）', id: 'edit', click: changeEdit, icon: 'edit' },
				{ line: true },
				{ text: '删除（<u>D</u>）', id: 'delete', click: remove, icon: 'delete' },
				{ line: true }, 
			]} 
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    var rowindex_id, column_name;
    
    function f_onBeforeEdit(e) {
		rowindex_id = e.rowindex;
		column_name = e.column.name;
		/* if (column_name == "pack_code") {
			var pack = grid.getColumnByName("pack_code");
			pack.editor.url = '../../queryMedHosPackage.do?isCheck=false&inv_id=' + e.record.inv_id;;
		} */
	}
    
	// 编辑单元格提交编辑状态之前作判断限制
	function f_onBeforeSubmitEdit(e) {
		return true;
	}
	// 跳转到下一个单元格之前事件
	function f_onAfterEdit(e) {
		return true;
	}
	//换行事件
	function f_onChangeRow(e){
		if(!e.record.sec_store_id){
			return false;
		}
		if(!e.record.store_id){
			return false;
		}
		ajaxJsonObjectByUrl("saveMedRefStoreStore.do", e.record, function(responseData) {
			if (responseData.state == "true") {
				//回写联合主键用于继续操作当前行数据
				if(!e.record.group_id){
					grid.updateRow(e.rowindex, {
						group_id: responseData.group_id,
						hos_id: responseData.hos_id,
						copy_code: responseData.copy_code,
						old_id: responseData.old_id,  //标识代表已存在
					});
				}
			}else{
				//改变当前行背景色
			}
		});
	}
    
    //键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('E', changeEdit);
		hotkeys('D', remove);
	}
    
    function changeEdit(){
    	isEdit = isEdit ? false : true;
    	grid.set("enabledEdit", isEdit);
    	if(!grid.rows.length || isFrist){
    		grid.addRow();
    		isFrist = false;
    	}
    }
    	
    //删除
    function remove(){
		var data = gridManager.getCheckedRows();
		if (data.length == 0){
        	$.ligerDialog.warn('请选择行');
        	return;
		}else{
			var ParamVo =[];
			var rows = [];
			var is_delete = false;
			var inv_names = "";
			$(data).each(function (){	
				if(this.old_id){
					ParamVo.push(
						this.group_id   +"@"+ 
						this.hos_id   +"@"+ 
						this.copy_code   +"@"+ 
						this.old_id 
					) 
					is_delete = true;
				}
				rows.push(this);
			});

			$.ligerDialog.confirm('确定删除?', function (yes){
				if(yes){
					if(is_delete){
						ajaxJsonObjectByUrl("deleteMedRefStoreStore.do",{ParamVo : ParamVo.toString()},function (responseData){
							if(responseData.state=="true"){
								grid.deleteRange(rows);
							}
						});
					}else{
						grid.deleteRange(rows);
					}
				}
			});  
		}
	}
   
    function loadDict(){
		//字典下拉框
		autocompleteAsync("#sec_store_code", "../../queryMedStoreByRead.do?isCheck=false", "id", "text", true, true); 
        
        //$("#query").ligerButton({ click: query, width: 90 });
	}  
	
	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
        	<td align="right" class="l-table-edit-td" style="padding-left: 50px;">
            	二级库房：
            </td>
            <td align="left" class="l-table-edit-td">
				<input name="sec_store_code" type="text" id="sec_store_code" ltype="text" validate="{required:false}" />
	        </td>
	        <td>&nbsp;</td>
	        <td>&nbsp;</td>
        </tr> 
    </table>
	<div id="maingrid"></div>
</body>
</html>
