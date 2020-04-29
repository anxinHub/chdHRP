<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    var isEdit = false;
    var isFrist = true;
    var yes_or_no = {
    		Rows : [ {
    			"id" : "0",
    			"text" : "否"
    		}, {
    			"id" : "1",
    			"text" : "是"
    		} ],
    		Total : 2
    	};
    
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
        //alert(JSON.stringify(window.parent.document.getElementById('pay_type_code')));
		loadHotkeys();
		query();
    });
    
    //查询
    function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
        //根据表字段进行添加查询条件
		
    	//加载查询条件
    	grid.loadData(grid.where);
    	//重置变量
    	isFrist = true;
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [{
				display: 'old_code', name: 'old_code', width: 20, align: 'left', isAllowHide: false, hide: true
			},{
				display : '自定义属性编码', name : 'attr_code', width : 120, align : 'left',editor : {type : 'text'}
			},{
				display : '自定义属性名称', name : 'attr_name', width : 150, align : 'left',editor : {type : 'text'}
			},{
				display : '是否停用', name : 'is_stop', width : 60, align : 'left',
				editor : {
					type : 'select',
					valueField : 'id',
					textField : 'text',
					data : yes_or_no.Rows,
					keySupport : true,
					autocomplete : true,
				},
				render : function(rowdata, rowindex, value) {
					return (rowdata.is_stop == null ? 0 : rowdata.is_stop) == 0 ? '否' : '是';
				}
			},{
				display : '备注', name : 'note', width : 150, align : 'left',editor : {type : 'text'}
			}
		 	],
			dataAction: 'server',dataType: 'server',usePager:false,url:'queryAccCheckEmpSet.do?isCheck=false',
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
			pack.editor.url = '../../queryMatHosPackage.do?isCheck=false&inv_id=' + e.record.inv_id;;
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
		if(!e.record.attr_code){
			return false;
		}
		if(!e.record.attr_name){
			return false;
		}
		ajaxJsonObjectByUrl("saveAccCheckEmpSet.do?isCheck=false", e.record, function(responseData) {
			if (responseData.state == "true") {
				//回写联合主键用于继续操作当前行数据
				if(!e.record.group_id){
					grid.updateRow(e.rowindex, {
						group_id: responseData.group_id,
						hos_id: responseData.hos_id,
						old_code: responseData.old_code,  //标识代表已存在
					});
				}
				//parent.query();
				window.parent.document.getElementById('attr_code').click();
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
				if(this.old_code){
					ParamVo.push(
						this.group_id   +"@"+ 
						this.hos_id   +"@"+ 
						this.old_code 
					) 
					is_delete = true;
				}
				rows.push(this);
			});
			
			$.ligerDialog.confirm('确定删除?', function (yes){
				if(yes){
					if(is_delete){
						ajaxJsonObjectByUrl("deleteAccCheckEmpSet.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
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
    	
	}  
	
	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

    
	<div id="maingrid"></div>
</body>
</html>
