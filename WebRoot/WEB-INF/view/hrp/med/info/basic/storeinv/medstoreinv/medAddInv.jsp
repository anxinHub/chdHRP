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
	<link href='<%=path%>/lib/SpreadJS/css/gcspread.sheets.9.40.20161.0.css' rel='stylesheet' type='text/css'/>
	<script src='<%=path%>/lib/SpreadJS/scripts/gcspread.sheets.all.9.40.20161.0.min.js' type='text/javascript'></script>
	<script src='<%=path%>/lib/SpreadJS/scripts/pluggable/gcspread.sheets.print.9.40.20161.0.min.js' type='text/javascript'></script>
    <script type="text/javascript">
    var grid; 
    var gridManager = null;
    var userUpdateStr;
    var checkedCustomer = [];
    var isHide = '${p08035 }' == 1 ? false : true;
    $(function ()
    {
        loadDict();//加载下拉框
    	//加载数据
    	loadHead(null);	
    });
    
    //验证
    function validate(){
    	//1、不能添加重复的药品
    	var msg = "";
   		var rowm = "";
   		var rows = 0;
   		var inv_ids = "";
   		var data = gridManager.getData();
   		//判断grid 中的数据是否重复或者为空
   		var targetMap = new HashMap();
   		$.each(data,function(i, v){
   			rowm = "";
   			if(v.inv_id){
 	 			msg += rowm;
 	 			var key = v.inv_id ;
 	 			var value = "第"+(i+1)+"行";
 	 			if(targetMap.get(key)== null || targetMap.get(key) == 'undefined' || targetMap.get(key) == ""){
 	 				targetMap.put(key ,value);
 	 			}else{
 	 				msg += targetMap.get(key) + "与" + value +"  药品的重复！" + "\n\r";
 	 			}
 	 			rows = rows + 1;
 	 			inv_ids += v.inv_id + ",";
   			}
   		});

   		//2、判断列表的药品是否已经存在于对应关系中
		var para = {
			inv_ids: inv_ids.substring(0, inv_ids.length - 1),
			store_id: '${store_id}'
		}
		ajaxJsonObjectByUrl("existsMedInvInStore.do?isCheck=false", para, function (responseData) {
			if (responseData.state == "false") {
				msg += "药品" + responseData.inv_ids + "已经存在于该仓库中！<br>";
			}
		}, false);
   		
   		
   		if(msg != ""){
   			$.ligerDialog.warn(msg);
  			return false;
   		}
   		return true;
    	
    }
    //保存
    function save(){
    	if(validate()){
    		var param = '';
    		var allData = gridManager.getData();
    		
    		$.each(allData,function(i,v){
    			if(typeof(v.inv_id) != 'undefined' && v.inv_id != null && v.inv_id != ''){
	    			var inv_id = v.inv_id;
	    			var location_id=v.location_id;
	    			var is_apply = $("#is_apply"+i).is(":checked") ? '1' : '0';
	    			param = param + inv_id + ','+ is_apply ;
	    			if(location_id != '' && location_id != undefined){
	    				param = param + ','+ location_id ;
	    			}
	    			param += '@';
    			}
    		});
    		
        	var formPara={
        		store_id : '${store_id}',
        		detailData: param
        	}
        	
        	ajaxJsonObjectByUrl("addMedStoreInvCert.do?isCheck=false",formPara,function(responseData){
                if(responseData.state=="true"){
                	 grid.reload();
                	 grid.addRow();
    				 parent.query();
                }
            });
    	}
    	
    }
    
    function loadHead(){
    	var loading_onoff=true;
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: 'inv_id', name: 'inv_id', align: 'left', hide: true },
		  			 { display: 'inv_no', name: 'inv_no', align: 'left', hide: true },
                     { display: '药品编码', name: 'inv_code', align: 'left',width:100},
                     { display: '药品名称',
 					     name: 'inv_name',
 					     width: 220,
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
									columns : [
								    	{display : '药品编码',name : 'inv_code',align : 'left'},
								    	{display : '药品名称',name : 'inv_name',align : 'left'},
								    	{display : '规格型号',name : 'inv_model',align : 'left'},
								   		{display : '计量单位',name : 'unit_name',align : 'left'}
								    ],
								    switchPageSizeApplyComboBox : false,
									onSelectRow: function (data) {
										var e = window.event;
										if (e && e.which == 1) {
											f_onSelectRow_detail(data);
										}
									},
									//url : 'queryMedInvAllList.do?isCheck=false&store_id='+'${store_id}',
									url : 'queryMedInvAllList.do?isCheck=false&store_id='+'${store_id}',
									usePager:true,
									onGridClick:function(){
										loading_onoff=true;
									},
									onSuccess: function (data, g) { //加载完成时默认选中
										if (loading_onoff && grid.editor.editParm) {
											var editor = grid.editor.editParm.record;
											var item = data.Rows.map(function (v, i) {
												return v.inv_name;
											});
											var index = item.indexOf(editor.inv_name) == -1 ? 0 : item.indexOf(editor.inv_name);
											loading_onoff=false;
											//加载完执行
											setTimeout(function () {
												g.select(data.Rows[index]);
											}, 80);
										}else if(!loading_onoff){
											return false;
										}
									}
			  					 },
			  					delayLoad : true,keySupport : true,autocomplete : true,
								onSuccess: function (data, grid) {
									this.parent("tr").next(".l-grid-row").find("td:first").focus();
								},
								onChangeValueImmediately : function(){
									loading_onoff = true;
								},
								ontextBoxKeyEnter: function (data) {
									f_onSelectRow_detail(data.rowdata);
								}
 					     	}
 					   },
                     { display: '规格型号', name: 'inv_model', align: 'left',width:180},
                     { display: '生产厂商', name: 'fac_name', align: 'left',width:180},
                     { display: '货位', name: 'location_id',textField : 'location_name', align: 'left',width:160,hide:isHide,
                    	 editor : {
     						type : 'select',
     						valueField : 'id',
     						textField : 'text',
     						url : '../../../../queryMedLocationType.do?isCheck=false&store_id='+liger.get("store_id").getValue().split(",")[0],
     						keySupport : true,
     						autocomplete : true,
     					},
     					render : function(rowdata, rowindex, value) {
     						return rowdata.location_name;
     					} 
     				 },
                     {
         				display : '是否申领', name : 'is_apply',width:60, align : 'center',
         				render : function(rowdata, rowindex,value) {
         					if(rowdata.is_apply == '1'){
	         					return "<input name='is_apply"+rowindex+"' disabled='disabled' type='checkbox' id='is_apply"+rowindex+"' ltype='text'"
	         					+" style='margin-top:5px;' />";
         					}else{
         						return "<input name='is_apply"+rowindex+"' type='checkbox' id='is_apply"+rowindex+"' ltype='text'"
	         					+" style='margin-top:5px;' />";
         					}
         				}
         			  } 
                    ],
                    dataAction: 'server',dataType: 'server',usePager:true,
                    width: '100%', height: '100%', checkbox : true,
        			enabledEdit : true,
      			  	alternatingRow : true,
      			  	onBeforeEdit : f_onBeforeEdit,
      			  	onBeforeSubmitEdit : f_onBeforeSubmitEdit,
      			  	onAfterEdit : f_onAfterEdit,
      			  	isScroll : true,
      			  	rownumbers : true,
      			  	delayLoad : true,//初始化明细数据
      			  	selectRowButtonOnly : true,//heightDiff: -10,
                     toolbar: { items: [
						{ text: '删除（<u>S</u>）', id:'delete', click: deleteRow, icon:'delete' },
						{ line:true },
    					{ text: '保存（<u>S</u>）', id:'add', click: save, icon:'add' },
    					{ line:true },
    					{ text: '关闭', id:'close', click: this_close,icon:'close' },
              			{ line:true }
    				]}
                   });
        gridManager = $("#maingrid").ligerGetGridManager();
        grid.toggleCol("inv_id", false);
        grid.toggleCol("inv_no", false);
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
				grid.updateRow(rowindex_id,{
					inv_code : data.inv_code,
					inv_name : data.inv_name,
					inv_model : (data.inv_model == "" || data.inv_model == undefined) ? '': data.inv_model,
					unit_name : data.unit_name,
					fac_name : data.fac_name,
					inv_id : data.inv_id,
					inv_no : data.inv_no,
					is_apply:data.is_apply
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
	 
	 //关闭
	 function this_close(){
	  	frameElement.dialog.close();
	 }
	 //删除
	 function deleteRow(){
		 gridManager.deleteSelectedRow();
	 }
	 
     function loadDict(){
    	//字典下拉框
       
        autocomplete("#store_id","../../../../queryMedStore.do?isCheck=false","id","text",true,true,'',false,'','160');
    	liger.get("store_id").setValue("${store_id}");
		liger.get("store_id").setText("${store_name}");
		$("#store_id").ligerComboBox({disabled:true});
		
    }
    </script>
</head>
<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
    	<tr>
    	 	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">仓库信息：</td>
            <td align="left" class="l-table-edit-td"><input name="store_id" type="text" id="store_id"  ltype="text" validate="{maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">注：已设置申领仓库的药品不能再次勾选!</font></td>
        </tr>
    </table>
	<div id="maingrid"></div>
</body>
</html>
