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
		loadHotkeys();
        $("#store_id").change( function(){
	    	loadHead();
	    	grid.reRender();
		});
     });  
     
     function loadHead(){
		 var loading_onoff = true;
    	 grid = $("#maingrid").ligerGrid({
             columns: [ 
                       { display: '交易编码', name: 'bid_code', align: 'left',width:110},
                       { display: '药品编码', name: 'inv_code', align: 'left',width:150},
                       { display: '药品名称(E)',width:230,
  					     name: 'inv_name',
  					     align: 'left',
  					     textField : 'inv_name',
  					     valueField : 'inv_name',
  					     editor :{
								type : 'select',
								textField : 'inv_name',
								valueField : 'inv_name',
								selectBoxWidth : "80%",
								selectBoxHeight : 240,
								grid : {
									columns : [ {
										display : '交易编码', name : 'bid_code', align : 'left', width : '110'
									}, {
										display : '药品编码', name : 'inv_code', align : 'left', width : '110'
									}, {	
										display : '药品名称', name : 'inv_name', align : 'left', width : '240'
									}, { 
										display : '规格型号', name : 'inv_model', align : 'left', width : '200'
									}, { 
										display : '计量单位', name : 'unit_name', align : 'left', width : '60'
									} , { 
										display : '生产厂商', name : 'fac_name', align : 'left', width : '180'
									}, {
										display : '供应商', name : 'sup_name', align : 'left', width : '180'
									} ],
									switchPageSizeApplyComboBox : false,
									onSelectRow: function (data) {
										var e = window.event;
										if (e && e.which == 1) {
											f_onSelectRow_detail(data);
										}
									},
									url : '${p08032}' == 0 ? '../../queryMedInvListNotStore.do?isCheck=false'
										    : '../../queryMedInvList.do?isCheck=false&store_id=' + liger.get("store_id").getValue().split(",")[0],
									//delayLoad:true,
									usePager:true,
									pageSize : 30,
									onGridClick:function (){           // 为了 点击时不走success的条件(特意加的事件)
										loading_onoff = true;
									},
									onSuccess: function (data, g) { //加载完成时默认选中
										if (loading_onoff && grid.editor.editParm) {
												var editor = grid.editor.editParm.record;
												var item = data.Rows.map(function (v, i) {
													return v.inv_name;
												});
												var index = item.indexOf(editor.inv_name) == -1 ? 0 : item.indexOf(editor.inv_name);
												loading_onoff = false;
												//加载完执行
												setTimeout(function () {
													g.select(data.Rows[index]);
												}, 80);
										}else if(!loading_onoff){
											return false;
										}
									}
			  					    },
			  					  delayLoad : false, keySupport : true, autocomplete : true,
									onSuccess : function() {
										this.parent("tr").next(
											".l-grid-row").find(
											"td:first").focus();
									},
									onChangeValueImmediately:function (str){
										loading_onoff = true;
									},
									ontextBoxKeyEnter: function (data) {
										f_onSelectRow_detail(data.rowdata);
									}
  					     		}
  					   },
                       { display: '规格型号', name: 'inv_model', align: 'left',width:200
  					 		},
  					   { display: '计量单位', name: 'unit_name', align: 'left',width:80
					 		},
		  			   { display: '数量', name: 'amount', align: 'left',type: 'int', width:120,editor: { type: 'int'}},
		  			   { display: '生产厂商', name: 'fac_name', align: 'left',width:180},
					   { display: '供应商', name: 'sup_name', align: 'left',width:180} ,
		  			   { display: 'inv_id', name: 'inv_id', align: 'left', hide: true },
		  			   { display: 'inv_no', name: 'inv_no', align: 'left', hide: true }
  					   
                       ],
                       dataAction: 'server',dataType: 'server',usePager:false,
                       width: '100%', height: '100%', checkbox: true,rownumbers:true,
                       enabledEdit : true,alternatingRow : true,onBeforeEdit : f_onBeforeEdit,
                       onBeforeSubmitEdit : f_onBeforeSubmitEdit,onAfterEdit : f_onAfterEdit,
                       isScroll : true,
                       selectRowButtonOnly:true,//heightDiff: -10,
                       toolbar: { items: [
                       			{ text: '删除（<u>D</u>）', id:'delete', click: deleteRow,icon:'delete' },{ line:true },
				    	        { text: '保存（<u>S</u>）', id:'save', click: saveMedDeptMatch, icon:'save' },{ line:true },
				    	        { text: '关闭（<u>C</u>）', id:'close', click: this_close, icon:'close' },{ line:true },
					    	    { text: '选择药品', id:'choice_inv', click: choice_inv,icon:'add' }
  				       ]}
                     });

          gridManager = $("#maingrid").ligerGetGridManager();
          grid.toggleCol("inv_id", false);
          grid.toggleCol("inv_no", false);
          grid.addRow()
     }

	//键盘事件
	function loadHotkeys() {
		hotkeys('D', deleteRow);
		hotkeys('S', saveMedDeptMatch);
		hotkeys('C', this_close);
	}
	
	 //添加行数据
    function add_rows(data){
    	grid.getData().pop();
		var arr = grid.getData();
		arr.pop();
		data = arr.concat(data);
		var d ={
			Rows:data
		}
		grid.set('data',d);
		grid.addRow();
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
				grid.updateRow(rowindex_id,{
						inv_code : data.inv_code,
						inv_name : data.inv_name,
						inv_model : (data.inv_model == "" || data.inv_model == undefined) ? '': data.inv_model,
						unit_name : data.unit_name,
						inv_id : data.inv_id,
						inv_no : data.inv_no,
						bid_code : data.bid_code,
						fac_name : data.fac_name,
						sup_name : data.sup_name
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
		
		//保存数据
		function save() {
			
			grid.endEdit();
            if($("#dept_match_code").val() ==  ''){
	    		$.ligerDialog.error('配套表编码不能为空');
	    		return;
	    	}
			
			if($("#dept_match_name").val() ==  ''){
	    		$.ligerDialog.error('配套表名称不能为空');
	    		return;
	    	}
			
	    	if('${p08032}' == 1){
	    		if(liger.get("store_id").getValue().split(",")[0] == ''){
		    		$.ligerDialog.error('仓库名称不能为空');
		    		return;
		    	}
	    	}
	    	
			if(liger.get("dept_name").getValue().split(",")[0] == ''){
	    		$.ligerDialog.error('科室名称不能为空');
	    		return;
	    	}
	    	
	    	var allData = gridManager.getData();
	    	var flag = true;
	    	var rows = 0;
	 		var msg="";
	 		//判断grid 中的数据是否重复或者为空
	 		var targetMap = new HashMap();
	    	if(allData.length != 0){
		    	$.each(allData,function(i, v){
		    		if (v.inv_id) {
			    		
						if(v.amount == undefined || v.amount == ''){
							msg += "第"+(i+1)+"行数量不能为空" + "\n\r";
			    		}
						
						var key=v.inv_id;
						var value="第"+(i+1)+"行";
						if(targetMap.get(key)== null || targetMap.get(key) == 'undefined' || targetMap.get(key) == ""){
							targetMap.put(key ,value);
						}else{
							msg += targetMap.get(key)+"与"+value+"药品编码不能重复" + "\n\r";
						}
						
						rows++;
		    		}
		    	});
	    	}
	    	
	    	if(rows == 0){
	 			$.ligerDialog.warn("请先添加药品！");  
				return false;  
	 		}
	 		if(msg != ""){
	 			$.ligerDialog.warn(msg);  
				return false;  
	 		} 	 
	    	
			var formPara = {
				paraValue : '${p08032}',
			    dept_match_code : $('#dept_match_code').val(),//配套表编码
				dept_match_name : $('#dept_match_name').val(),//配套表名称
				store_id : liger.get("store_id").getValue() == null ? "" : liger.get("store_id").getValue().split(",")[0],//仓库ID
				dept_id : liger.get("dept_name").getValue() == null ? "" : liger.get("dept_name").getValue().split(",")[0],
				allData : JSON.stringify(allData),//获取所有数据
				
			};
			
			ajaxJsonObjectByUrl("addMedDeptMatch.do", formPara,function(responseData) {
				if (responseData.state == "true") {
						$("#dept_match_name").val('');
						liger.get("store_id").clear();
						liger.get("dept_name").clear();
   	                 	grid.deleteAllRows();
   	                 	grid.addRow();
						parent.query();
				}
			});
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
		
		function choice_inv(){
			var allData = gridManager.getData();
			$.ligerDialog.open({url: "medDeptMatchChoiceInvPage.do?isCheck=false",
					height: 500,width: 900, title:'选择药品',modal:true,showToggle:false,showMax:true,
					showMin: false,isResize:true,
					buttons: [ 
					    { text: '确定', 
						onclick: function (item, dialog) { 
								dialog.frame.addNew(); 
							},
						cls:'l-dialog-btn-highlight' }, 
						{ text: '取消', 
						onclick: function (item, dialog) { 
							dialog.close(); 
						}} 
					] 
			});
		}
		
		//保存配套表
		function saveMedDeptMatch() {
			grid.endEdit();			
			save();
		}
					
		function loadDict() {
			//字典下拉框
		    var dept_para ={is_last:1};
		    autocomplete("#store_id","../../queryMedStoreByRead.do?isCheck=false", "id","text", true,true,"",false,"","","",200);//仓库信息
			autocomplete("#dept_name","../../queryMedDept.do?isCheck=false","id","text",true,true,dept_para,"","","","",200);//科室信息
			$("#dept_match_code").ligerTextBox({width:200,disabled:true});
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

		function this_close() {
			frameElement.dialog.close();
		}

		</script>
  </head>
  
   <body >
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
	        <tr>
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>配套表编码：</td>
	            <td align="left" class="l-table-edit-td"><input name="dept_match_code" type="text" id="dept_match_code" ltype="text" value="系统生成" disabled="disabled" validate="{required:true,maxlength:20}" /></td>
	            <td align="left"></td>
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>配套表名称：</td>
	            <td align="left" class="l-table-edit-td"><input name="dept_match_name" type="text" id="dept_match_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
	            <td align="left"></td>
	        </tr> 
	        <tr>
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">仓库名称：</td>
	            <td align="left" class="l-table-edit-td"><input name="store_id" type="text" id="store_id" ltype="text"  /></td><!-- validate="{required:true,maxlength:20}" -->
	            <td align="left"></td>
	        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>科室名称：</td>
	            <td align="left" class="l-table-edit-td"><input name="dept_name" type="text" id="dept_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
	            <td align="left"></td>
	        </tr>
    </table>
    <div id="maingrid"></div>
    </form>
   
    </body>
</html>
