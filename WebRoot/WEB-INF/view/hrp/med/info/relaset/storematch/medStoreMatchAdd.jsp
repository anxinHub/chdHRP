<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <!-- jsp:include page="${path}/inc.jsp"/-->
    <jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
	<script src="<%=path%>/lib/hrp/med/med.js"	type="text/javascript"></script>
    <script type="text/javascript">
     var dataFormat;
     var selectData = "";
     var grid;
     var gridManager = null;
     $(function (){
         loadDict();//加载下拉框
        loadForm();
        loadHead(null);//加载数据

		$("#store_id").bind("change",function(){
	    	loadHead();
	    	grid.reRender();
		});
     });  
     
     function loadHead(){
    	 
    	 grid = $("#maingrid").ligerGrid({
             columns: [ 
                       { display: '药品编码', name: 'inv_code', align: 'left'
  					 		},
                       { display: '药品名称',
  					     name: 'inv_name',
  					     align: 'left',
  					     textField : 'inv_name',
  					     valueField : 'inv_name',
  					     editor :{
								type : 'select',
								textField : 'inv_name',
								valueField : 'inv_name',
								selectBoxWidth : 500,
								selectBoxHeight : 240,
								grid : {
									columns : [ {
										display : '药品编码',
										name : 'inv_code',
										align : 'left'
									}, {
										display : '药品名称',
										name : 'inv_name',
										align : 'left'
									}, {
										display : '规格型号',
										name : 'inv_model',
										align : 'left'
									}, {
										display : '计量单位',
										name : 'unit_name',
										align : 'left'
									} ],
									switchPageSizeApplyComboBox : false,
									onSelectRow: function (data) {
										var e = window.event;
										if (e && e.which == 1) {
											f_onSelectRow_detail(data);
										}
									},
									url : '../../queryMedInvList.do?isCheck=false&store_id=' + liger.get("store_id").getValue().split(",")[0],
									//delayLoad:true,
									usePager:true,
									pageSize : 30,
									onSuccess: function (data, g) { //加载完成时默认选中
										if (grid.editor.editParm) {
											var editor = grid.editor.editParm.record;
											var item = data.Rows.map(function (v, i) {
												return v.inv_name;
											});
											var index = item.indexOf(editor.inv_name) == -1 ? 0 : item.indexOf(editor.inv_name);
											//加载完执行
											setTimeout(function () {
												g.select(data.Rows[index]);
											}, 80);
										}
									}
			  					    },
									keySupport : true,
									autocomplete : true,
									onSuccess : function() {
										this.parent("tr").next(
											".l-grid-row").find(
											"td:first").focus();
									},
									ontextBoxKeyEnter: function (data) {
										f_onSelectRow_detail(data.rowdata);
									}
  					     		}
  					   },
                       { display: '规格型号', name: 'inv_model', align: 'left'
  					 		},
  					   { display: '计量单位', name: 'unit_name', align: 'left'
					 		},
		  			   { display: '数量', name: 'amount', align: 'left',type: 'int', editor: { type: 'int'}
						    }
                       ],
                       dataAction: 'server',dataType: 'server',usePager:true,
                       width: '100%', height: '100%', checkbox: true,rownumbers:true,
                       enabledEdit : true,alternatingRow : true,onBeforeEdit : f_onBeforeEdit,
                       onBeforeSubmitEdit : f_onBeforeSubmitEdit,onAfterEdit : f_onAfterEdit,
                       isScroll : true,
                       selectRowButtonOnly:true,//heightDiff: -10,
                       toolbar: { items: [
                       			{ text: '删除', id:'delete', click: deleteRow,icon:'delete' },{ line:true },
				    	        { text: '添加行', id:'add', click: addCenterRow, icon:'add' },{ line:true }
  				       ]}
                     });

          gridManager = $("#maingrid").ligerGetGridManager();
          
     }
     
     var rowindex_id = "";
 	 var column_name="";
 	 function f_onBeforeEdit(e) {
 		 rowindex_id = e.rowindex;
 		 column_name=e.column.name;
 	 }
	
 	
 	 //选中回充数据
	function f_onSelectRow_detail(data, rowindex, rowobj) {

		selectData = "";
		selectData = data;
		if (column_name == "inv_name") {
			if (selectData != "" || selectData != null) {
				//回充数据 
				//grid.updateCell('apply_emp', 100, e.record);
				grid.updateRow(
						rowindex_id,
						{inv_code : data.inv_code,
						inv_name : data.inv_name,
						inv_model : (data.inv_model == "" || data.inv_model == undefined) ? '': data.inv_model,
						unit_name : data.unit_name,
						inv_id:data.inv_id
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

	function save() {
        if($("#store_match_code").val() ==  ''){
	    	$.ligerDialog.error('配套表编码不能为空');
	    	return;
	    }
			
		if($("#store_match_name").val() ==  ''){
	    	$.ligerDialog.error('配套表名称不能为空');
	    	return;
	    }
	    	
	    if(liger.get("store_id").getValue().split(",")[0] == ''){
	    	$.ligerDialog.error('仓库名称不能为空');
	    	return;
	    }
	    	
	    var allData = gridManager.getData();
	    var flag = true;
	    var rows=0;
	    		
		$(allData).each(function(){
			if(this.inv_id){
				if(this.inv_name == undefined || this.inv_name == ''){
			    	$.ligerDialog.error('请选择药品名称');
			    	return flag = false;
			    }
			    		
				if(this.amount == undefined || this.amount == ''){
			    	$.ligerDialog.error('请填写数量');
			    	return flag = false;
			    }
				rows++;
			}
			
		});
		    	
		if(!flag){
		    return;
		}
		
		if(rows == 0){
 			$.ligerDialog.warn("请先添加药品！");  
			return false;  
 		}	
	    	
		var formPara = {
			store_match_code : $('#store_match_code').val(),//配套表编码
			store_match_name : $('#store_match_name').val(),//配套表名称
			store_id : liger.get("store_id").getValue().split(",")[0],//仓库ID
			allData : JSON.stringify(allData),//获取所有数据
		};
			
		ajaxJsonObjectByUrl("addMedStoreMatch.do", formPara,function(responseData) {
			if (responseData.state == "true") {
				$("input[name='store_match_code']").val('');
				$("input[name='store_match_name']").val('');
				$("input[name='store_id']").val('');
				delete_allRows();
				is_addRow();
				parent.query();
			}
		});
	}
	
	function delete_allRows(){
		for (var i = 0, l = gridManager.rows.length; i < l; i++) {  
			var o = gridManager.getRow(i);
			if (o['__id'] in gridManager.records)
				gridManager._deleteData.ligerDefer(gridManager, 10, [ o ]); 
		}  
		gridManager.reRender.ligerDefer(gridManager, 20);
	}
	
		function loadForm() {

			$.metadata.setType("attr", "validate");
			var v = $("form").validate({errorPlacement : function(lable, element) {
					if (element.hasClass("l-textarea")) {
							element.ligerTip({
							content : lable.html(),
							target : element[0]
							});
					} else if (element.hasClass("l-text-field")) {
							element.parent().ligerTip({
							content : lable.html(),
							target : element[0]
							});
					} else {
							lable.appendTo(element.parents("td:first").next("td"));
					}
			},success : function(lable) {
							lable.ligerHideTip();
							lable.remove();
						},submitHandler : function() {
							$("form .l-text,.l-textarea").ligerHideTip();
						}
			});
			$("form").ligerForm();
		}
		
		//保存配套表
		function saveMedStoreMatch() {
			grid.endEdit();			
			save();
		}
					
		function loadDict() {
						//字典下拉框
			autocomplete("#store_id","../../queryMedStore.do?isCheck=false", "id","text", true, true,"","","","","",220);//仓库信息
			
			$("#store_match_code").ligerTextBox({width:160,disabled:true});
		}
		
		//自动添加行
		function is_addRow() {
			
			setTimeout(function() { //当数据为空时 默认新增一行
				grid.addRow();
			}, 100);

	    }
		
		//手动添加新行
		function addCenterRow(){ 
			
			gridManager.addRow();
			
	    }
		//删除选中行
		function deleteRow(){
			
			gridManager.deleteSelectedRow();
        }

		</script>
  </head>
  
   <body onload="is_addRow()">
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
		
        <tr>
        <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>配套表编码：</td>
            <td align="left" class="l-table-edit-td"><input name="store_match_code" type="text" id="store_match_code" ltype="text" value="系统生成" disabled="disabled" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
          
          
          <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>配套表名称：</td>
            <td align="left" class="l-table-edit-td"><input name="store_match_name" type="text" id="store_match_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>仓库名称：</td>
            <td align="left" class="l-table-edit-td"><input name="store_id" type="text" id="store_id" ltype="text"  /></td><!-- validate="{required:true,maxlength:20}" -->
            <td align="left"></td>
        </tr> 
    </table>
    
    <div id="maingrid"></div>
    </form>
   
    </body>
</html>
