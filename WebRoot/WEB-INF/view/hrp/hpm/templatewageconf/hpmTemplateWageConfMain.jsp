<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script src="<%=path%>/lib/stringbuffer.js"></script>
<script type="text/javascript">

    var grid;
    var gridManager = null;
    
    $(function ()
    {
        loadDict();//加载下拉框
    	loadHead(null);//加载表格
    	toobar();
    	
    	manager1 = $("#r1").ligerRadio();manager2 = $("#r2").ligerRadio();

		var is_grant = '${is_grant}';
		
		if(is_grant !=null && is_grant !="" && is_grant=='1'){
			
			manager1.setValue(true);manager2.setValue(false);
			
			liger.get("wage_code").setValue('${wage_code}');liger.get("wage_code").setText('${wage_name}');

		}else{
			
			manager1.setValue(false);manager2.setValue(true);
			
		}
		
    	$("#wage_code").bind("change", function() {
    		
            grid.columns[3].editor.grid.url= 'queryWageItem.do?isCheck=false&is_stop=0&wage_code=' + liger.get("wage_code").getValue();
            grid.deleteAllRows();
            grid.reRender(); 
        })

    });
    //查询
    function  query(){
    	
		grid.options.parms = []; 
		grid.options.newPage = 1;
		grid.options.parms.push({name : 'wage_code',value : liger.get("wage_code").getValue() == ""?" ":liger.get("wage_code").getValue()});
		grid.loadData(grid.where);//加载查询条件
		
     }
    
    
  //模板工具栏
	function toobar(){
    	var obj = [];
    	obj.push({ text: '添加行', id:'add', click: addRow,icon:'add' });
    	obj.push({ line:true });
    	
    	obj.push({ text: '删除行', id:'delete', click: deleteRow,icon:'delete' });
    	obj.push({ line:true });
    	
    	obj.push({ text: '保存', id:'save', click: addAphiTemplateWageConf,icon:'save' });
    	obj.push({ line:true });
    	
    	obj.push({ text: '删除', id:'del', click: del,icon:'delete' });
    	obj.push({ line:true });
    	
    	$("#toptool").ligerToolBar({ items: obj});
    }



	//加载表格
	function loadHead(){
		grid = $("#mainGrid").ligerGrid({
			columns : [
				{display : '奖金项目名称',name : 'empItem_name',width: '25%',align : 'center',textField : 'item_name',
					editor :{
						type : 'select',valueField : 'item_code',textField : 'item_name',
						selectBoxWidth : 410,selectBoxHeight : 200,
						grid : {
							columns : [ {
								display : '奖金项目编码', name : 'item_code', align : 'left'
							}, {
								display : '奖金项目名称', name : 'item_name', align : 'left',width:'auto'
							} ],
							switchPageSizeApplyComboBox : false,
							onSelectRow : f_onSelectRow_empItem,
							url : '../hpmitem/queryHpmItem.do?isCheck=false',
							usePager: true,
							pageSize : 10,
						},
						width: '98%', height: '98%', 
						keySupport : true,
						autocomplete : true,
						onSuccess : function() {
							this.parent("tr").next(".l-grid-row").find("td:first").focus();
						}
					}	
				}, 
				
				{display : '奖金项目编码',name : 'emp_item',width: '25%',align : 'center',editor: { type: 'text'}}, 
				
				{display : '工资项目名称',name : 'wageItem_name',width: '25%',align : 'center',
					textField : 'wage_item_name',
					editor :{
						type : 'select',valueField : 'wage_item_code',textField : 'wage_item_name',
						selectBoxWidth : 410,selectBoxHeight : 200,
						grid : {
							columns : [ {
								display : '工资项目编码', name : 'wage_item_code', align : 'left'
							}, {
								display : '工资项目名称', name : 'wage_item_name', align : 'left',width:'auto'
							} ],
							switchPageSizeApplyComboBox : false,
							onSelectRow : f_onSelectRow_wage,
							url : 'queryWageItem.do?isCheck=false&is_stop=0',
							usePager: true,
							pageSize : 10,
						},
						width: '98%', height: '98%', 
						keySupport : true,
						autocomplete : true,
						onSuccess : function() {
							this.parent("tr").next(".l-grid-row").find("td:first").focus();
						}
					}	
				},
				
				{display : '工资项目编码',name : 'wage_item',width: '25%',align : 'center',editor: { type: 'text'}}
				
			],
			usePager : false,width : '100%',height :'100%',checkbox : true,enabledEdit : true,
			url:'queryHpmTemplateWageConf.do',
			delayLoad: false,selectRowButtonOnly : true,//heightDiff: -10,
			onBeforeEdit : f_onBeforeEdit,
			onBeforeSubmitEdit : f_onBeforeSubmitEdit,onAfterEdit : f_onAfterEdit
		});

    	gridManager = $("#mainGrid").ligerGetGridManager();
    }
    
	
  
	function f_onBeforeEdit(e) {
		
		rowindex_id = e.rowindex;
		clicked = 0;
		column_name=e.column.name;
	}
	
	//删除行
	function deleteRow(){
		
		var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.warn('请选择行');
        	return ; 
        }
		
		gridManager.deleteSelectedRow2();//删除选择的行
		
	}
	
	
	//删除
	function del(){
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var ParamVo = [];
			$(data).each(
					function() {
						ParamVo.push(this.group_id + "@" + 
									 this.hos_id + "@" + 
									 this.copy_code + "@" + 
									 this.wage_code + "@" +
									 this.emp_item)
					});
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					console.log(ParamVo);
					ajaxJsonObjectByUrl("deleteWage.do?isCheck=false", {
						ParamVo : ParamVo.toString()
					}, function(responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			});
		}
	}

	//工资项目名称:选中回充数据
	function f_onSelectRow_wage(data, rowindex, rowobj) {
		
		selectData = "";
		selectData = data;
		if (column_name == "wageItem_name") {
			if (selectData != "" || selectData != null) {
				//回充数据 
				grid.updateRow(rowindex_id, {
					wage_item : data.wage_item_code,
	 				wageItem_name: data.wage_item_name
	 			});
			}
	 	}
		
	 	return true;
	 }
	
	//奖金项目名称:选中回充数据
	function f_onSelectRow_empItem(data, rowindex, rowobj) {
		
		selectData = "";
		selectData = data;
		
		if (column_name == "empItem_name") {
			if (selectData != "" || selectData != null) {
				//回充数据 
				grid.updateRow(rowindex_id, {
					emp_item : data.item_code,
					empItem_name : data.item_name
	 			});
			}
	 	}
	 	return true;
	 }
	 		
	function f_onSelectRow(data, rowindex, rowobj) {
	 	return true;
	}
	 		
	// 编辑单元格提交编辑状态之前作判断限制
	function f_onBeforeSubmitEdit(e) {
	 	return true;
	 }
	 
	// 跳转到下一个单元格之前事件
	function f_onAfterEdit(e) {
	 			
		return true;
	} 
  
    //添加行
	function addRow(){
		grid.addRow();
	}
    
	
	//加载字典
    function loadDict(){
		//工资套名称
    	//autocomplete("#wage_code","../../acc/queryAccWage.do?isCheck=false","id","text",true,true,'');
		
    	$("#wage_code").ligerComboBox({
        	url: '../../acc/queryAccWage.do?isCheck=false',
        	valueField: 'id',
         	textField: 'text', 
         	selectBoxWidth: 160,
        	autocomplete: true,
        	width: 160,
        	onSelected :function(id,text){ 
        		query();
        	} 
		  });
    	
	}
    
    //保存
    function addAphiTemplateWageConf(){
    	
    	$.ligerDialog.confirm('确定保存吗?',
    		function(yes){
    			if(yes){
    				var is_grant = $("input[name='is_grant']:checked").val(); 
    		    	
    		    	if(is_grant == ''){
    		    		$.ligerDialog.warn('是否发放不能为空 ');
    		    		return ; 
    		    	}
    		    	
    		    	var wage_code = liger.get("wage_code").getValue();
    		    	if(wage_code == ''){
    		    		$.ligerDialog.warn('工资套名称不能为空 ');
    		    		return ; 
    		    	}
    		    	
    		    	var data = gridManager.getData();
    		    	
    		    	if(is_grant == '1'){
    		    		
    		    		if(data.length == 0){
    		    			$.ligerDialog.warn('请配置奖金项目与工资项目的对应关系 ');
    		        		return ;
    		    		}
    		    	}
    		    	 var map = new HashMap();//判断数据是否重复
    		    	 var msg = "";//重复提示
    		    	 
    		    	$.each(data,function(index,content){
    		        	if(	typeof(content.wage_item) != "undefined" && content.wage_item != "" && typeof(content.emp_item) != "undefined" && content.emp_item != ""	
    		        	){
    		        		var key = content.wage_item + "|" + content.emp_item;
    		        		var value = '第'+(index+1)+'行'; 
    		        		
    		        		//判断子目标编码是否重复
    					 	if (!map.get(key)) {
    					 		map.put(key, value);
    						}else{
    							msg += value + '与' + map.get(key) + '重复\n ';
    						}
    		        	}
    		        });
    		    	 
    		    	if(msg != ''){
    		    		$.ligerDialog.warn(msg);
    		    		return ; 
    		    	}
    		    	
    		    	
    		    	var param = {
    		    		is_grant : is_grant,
    		    		wage_code : wage_code,
    		    		detail_data : JSON.stringify(data)
    		    	};
    		    	
    		    	ajaxJsonObjectByUrl("addHpmTemplateWageConf.do", param, function(responseData) {
    					if(responseData.state == "true"){
    						query();
    					}
    				});  
    			}
    		}
    	);
    }
	 
	
</script>

</head>
</head>

<body style="padding: 0px; overflow: hidden;" >

	<div id="pageloading" class="l-loading" style="display: none"></div>
    <div id="layout1">
            
            <div position="center" >
           	<div id="toptool"></div>
           	
            <table cellpadding="0" cellspacing="0" class="l-table-edit" >
            		<tr>
            			<td align="right" class="l-table-edit-td"  style="padding-left:20px;"> 是否发放：</td>
			           	<td align="left" class="l-table-edit-td"  style="padding-left:20px;">
			           		<input name="is_grant" type="radio" id="r1" value="1" />是&nbsp;&nbsp;
			           		<input name="is_grant" type="radio" id="r2" value="0"  checked="checked"/>否
			           	</td>
			            <td align="left"></td>
            		</tr>
			   		 <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"> 工资套名称：</td>
			            <td align="left" class="l-table-edit-td"><input name="wage_code" type="text" id="wage_code" ltype="text" /></td>
			            <td align="left"></td>
			        </tr> 
			       
			    </table>
			    <div id="mainGrid"></div>
            </div>  
        </div>
</body>
</html>
