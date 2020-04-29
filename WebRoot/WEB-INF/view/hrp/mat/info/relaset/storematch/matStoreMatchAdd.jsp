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
	<script src="<%=path%>/lib/hrp/mat/mat.js"	type="text/javascript"></script>
    <script src="<%=path%>/lib/stringbuffer.js"></script>
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
             columns: [ { display: '交易编码', name: 'bid_code', align: 'left',width:150
		 				},{ display: '材料编码', name: 'inv_code', align: 'left',width:150
  					 	},{ display: '材料名称',
  					     name: 'inv_name',
  					     align: 'left',
  					     textField : 'inv_name',
  					     valueField : 'inv_name',width:270,
  					     editor :{
								type : 'select',
								textField : 'inv_name',
								valueField : 'inv_name',
								selectBoxWidth : '80%',
								selectBoxHeight : 240,
								grid : {
									columns : [{
										display : '交易编码',
										name : 'bid_code',
										align : 'left',width:200
									}, {
										display : '材料编码',
										name : 'inv_code',
										align : 'left',width:120
									}, {
										display : '材料名称',
										name : 'inv_name',
										align : 'left',width:240
									}, {
										display : '规格型号',
										name : 'inv_model',
										align : 'left',width:200
									}, {
										display : '计量单位',
										name : 'unit_name',
										align : 'left',width:120
									} ],
									switchPageSizeApplyComboBox : false,
									onSelectRow: function (data) {
										var e = window.event;
										if (e && e.which == 1) {
											f_onSelectRow_detail(data);
										}
									},
									url : '../../queryMatInvList.do?isCheck=false&store_id=' + liger.get("store_id").getValue().split(",")[0],
									//delayLoad:true,
									usePager:true,
									pageSize : 200,
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
  					   },{ display: '规格型号', name: 'inv_model', align: 'left',width:160
  					   },{ display: '计量单位', name: 'unit_name', align: 'left',width:100
  					   },{ display: '单价', name: 'price', align: 'right',width:100
					   },{ display: '数量', name: 'amount', align: 'right',type: 'int', editor: { type: 'int'},width:110,
	 						  render: function (data,index,value) {
									data.amount= (data.amount || data.amount === 0) ? data.amount.toString() : '0';
									var value = (data.amount || data.amount === 0) ? data.amount.toString() : '0';
									return value
							 }
					   },{ display: '生产厂商', name: 'fac_name', align: 'left',width:210
					   },{ display: '供应商', name: 'sup_name', align: 'left',width:210
					   }
                       ],
                       dataAction: 'server',dataType: 'server',usePager:true,pageSize:500,
                       width: '100%', height: '100%', checkbox: true,rownumbers:true,
                       enabledEdit : true,alternatingRow : true,onBeforeEdit : f_onBeforeEdit,
                       onBeforeSubmitEdit : f_onBeforeSubmitEdit,onAfterEdit : f_onAfterEdit,
                       isScroll : true,
                       selectRowButtonOnly:true,//heightDiff: -10,
                       toolbar: { items: [
                       			{ text: '删除', id:'delete', click: deleteRow,icon:'delete' },{ line:true },
				    	        { text: '添加行', id:'add', click: addCenterRow, icon:'add' },{ line:true },
				    	        { text: '关闭（<u>C</u>）', id:'close', click: this_close, icon:'close' },{ line:true },
					    	    { text: '选择材料', id:'choice_inv', click: choice_inv,icon:'add' }
  				       ]}
                     });

          gridManager = $("#maingrid").ligerGetGridManager();
          
     }
     
     function choice_inv(){
    	var store_id = liger.get("store_id").getValue();
		if (store_id == null || store_id == "" || store_id == "undefined") {
			$.ligerDialog.error("请先选择仓库！");
			return;
		}
		var para = "store_id=" + liger.get("store_id").getValue() +
		"&store_text=" + liger.get("store_id").getText() ;
		
		$.ligerDialog.open({
			url: "matStoreMatchChoiceInvPage.do?isCheck=false&" + para,
			height: 500,width: 900, title:'选择材料',modal:true,showToggle:false,showMax:true,
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
     
     function this_close() {
		frameElement.dialog.close();
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
						rowindex_id,{
						bid_code : data.bid_code,
						inv_code : data.inv_code,
						inv_name : data.inv_name,
						inv_model : (data.inv_model == "" || data.inv_model == undefined) ? '': data.inv_model,
						unit_name : data.unit_name,
						inv_id:data.inv_id,
						price : (data.price == "" || data.price == undefined) ? '': data.price,
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
	    var targetMap = new HashMap();   
    	var validate_detail_buf = new StringBuffer();
	    var rows=0;
	    		
		//重复数据判断
	    
 		$.each(allData, function(d_index, d_content){ 
 	      	if(d_content.inv_id){
  	      		var value="第"+(d_index+1)+"行";   	      		
  	      		var key=d_content.inv_id ;
  				if(targetMap.get(key)== null || targetMap.get(key) == 'undefined' || targetMap.get(key) == ""){   					
  					targetMap.put(key ,value);   					
  				}else{   					
  					validate_detail_buf.append(targetMap.get(key)+"与"+value+"材料编码、条形码、单价不能重复"+d_content.inv_code+" "+d_content.inv_name + "\n\r");   					
  				}
  			
  				rows = rows + 1;
				if(this.inv_name == undefined || this.inv_name == ''){
	    			$.ligerDialog.error('请选择材料名称');
	    			return flag = false;
	    		}
	    		
	    		/* if(this.amount == undefined || this.amount == ''){
	    			$.ligerDialog.error('请填写数量');
	    			return flag = false;
	    		} */
	    		rows++;
	    	}
		}); 
   		
		    	
		if(!flag){
		    return;
		}
		
		if(validate_detail_buf.toString()  != ""){  			
  			$.ligerDialog.warn(validate_detail_buf.toString());  			
  			return false;
  		}
		
		if(rows == 0){
 			$.ligerDialog.warn("请先添加材料！");  
			return false;  
 		}	
	    	
		var formPara = {
			store_match_code : $('#store_match_code').val(),//配套表编码
			store_match_name : $('#store_match_name').val(),//配套表名称
			store_id : liger.get("store_id").getValue().split(",")[0],//仓库ID
			allData : JSON.stringify(allData),//获取所有数据
		};
			
		ajaxJsonObjectByUrl("addMatStoreMatch.do", formPara,function(responseData) {
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
		 
		//保存配套表
		function saveMatStoreMatch() {
			grid.endEdit();			
			save();
		}
					
		function loadDict() {
			//字典下拉框
			autocomplete("#store_id","../../queryMatStoreDictDate.do?isCheck=false", "id","text", true, true,"","","","","",220);//仓库信息
			
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
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;" width="10%"><font color="red" size="2">*</font>配套表编码：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="store_match_code" type="text" id="store_match_code" ltype="text" value="系统生成" disabled="disabled" validate="{required:true,maxlength:20}" />
            </td>
          
          
          	<td align="right" class="l-table-edit-td"  style="padding-left:20px;" width="10%"><font color="red" size="2">*</font>配套表名称：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="store_match_name" type="text" id="store_match_name" ltype="text" validate="{required:true,maxlength:20}" />
            </td>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;" width="10%"><font color="red" size="2">*</font>仓库名称：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="store_id" type="text" id="store_id" ltype="text"  />
            </td><!-- validate="{required:true,maxlength:20}" -->
        </tr> 
    </table>
    
    <div id="maingrid"></div>
    </form>
   
    </body>
</html>
