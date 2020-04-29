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
	<link href='<%=path%>/lib/SpreadJS/css/gcspread.sheets.9.40.20161.0.css' rel='stylesheet' type='text/css'/>
	<script src='<%=path%>/lib/SpreadJS/scripts/gcspread.sheets.all.9.40.20161.0.min.js' type='text/javascript'></script>
	<script src='<%=path%>/lib/SpreadJS/scripts/pluggable/gcspread.sheets.print.9.40.20161.0.min.js' type='text/javascript'></script>
    <script type="text/javascript">
    var grid; 
    var gridManager = null;
    var userUpdateStr;
    var checkedCustomer = [];
    
    $(function (){
        loadDict();//加载下拉框
    	//加载数据
    	loadHead(null);	
    	$("#mat_type_code").bind("change",function(){
	    	loadHead();
	    	grid.reRender();
	    
		});
		 $("#store_code").bind("change",function(){
	    	loadHead();
	    	grid.reRender();
	    
		}) 
    });
    
    //验证
    function validate(){
    	//1、不能添加重复的材料
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
 	 				msg += targetMap.get(key) + "与" + value +"  材料的重复！" + "\n\r";
 	 			}
 	 			rows = rows + 1;
 	 			inv_ids += v.inv_id + ",";
   			}
   		});

   		//2、判断列表的材料是否已经存在于对应关系中
		var para = {
			inv_ids: inv_ids.substring(0, inv_ids.length - 1),
			cert_id: '${cert_id}'
		}
		ajaxJsonObjectByUrl("existsMatInvInCert.do?isCheck=false", para, function (responseData) {
			if (responseData.state == "false") {
				msg += "材料" + responseData.inv_ids + "与证件的对应关系已经存在！<br>";
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
    		
        	var formPara={
        		cert_id : '${cert_id}',
        		cert_code : '${cert_code}',
        		detailData: JSON.stringify(gridManager.getData())
        		
        		
        		 
        	}
        	
        	ajaxJsonObjectByUrl("addMatInvCertRelaAdd.do?isCheck=false",formPara,function(responseData){
                if(responseData.state=="true"){
                	 grid.reload();
                	 grid.addRow();
                	 parentFrameUse().query();
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
                     { display: '材料编码', name: 'inv_code', align: 'left',width:'15%'},
                     { display: '材料名称',
 					     name: 'inv_name',
 					     width: '25%',
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
								    	{display : '材料编码',name : 'inv_code',align : 'left'},
								    	{display : '材料名称',name : 'inv_name',align : 'left'},
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
									url : 'queryMatInvCertList.do?isCheck=false&cert_id='+'${cert_id}'+'&mat_type_code='+liger.get("mat_type_code").getText().split(" ")[0]+'&store_code='+liger.get("store_code").getText().split(" ")[0],
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
                     { display: '规格型号', name: 'inv_model', align: 'left',width:'20%'},
                     { display: '生产厂商', name: 'fac_name', align: 'left',width:'25%'}
                    ],
                    dataAction: 'server',dataType: 'server',usePager:true,
                    width: '100%', height: '100%', checkbox : true,
        			enabledEdit : true,url:'queryMatInvCertRela.do?cert_id='+'${cert_id}',
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
		 
		 /* var data = gridManager.getCheckedRows();
			if (data.length == 0){
				$.ligerDialog.error('请选择要删除的材料！');
				return;
			}else{
				var ParamVo =[];
				$(data).each(function (){		
					ParamVo.push(
						this.inv_id +"@"+'${cert_id}'
					) 
				});
				
				$.ligerDialog.confirm('确定删除?', function (yes){
					if(yes){
						ajaxJsonObjectByUrl("deleteMatInvCertRelaAdd.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
							if(responseData.state=="true"){
								grid.reload();
			                	grid.addRow();
			                	parentFrameUse().query();
							}
						});
					}
				}); 
			} */
			gridManager.deleteSelectedRow();
		 
	 }
	 
     function loadDict(){
    	//字典下拉框
    
        autocomplete("#cert_id","../../../../queryMatStore.do?isCheck=false","id","text",true,true,'',false,'','160');
    	liger.get("cert_id").setValue("${cert_id}");
		liger.get("cert_id").setText("${cert_code} ${cert_name} ");
		$("#cert_id").ligerComboBox({disabled:true});
		$("#cert_id").ligerTextBox({width:300});
		   autocompleteAsync("#mat_type_code", "../../../../queryPermMatTypeDict.do?isCheck=false", "id", "text", true, true); 
	        liger.get("mat_type_code").setValue("${mat_type_id}");
			liger.get("mat_type_code").setText("${mat_type_code} ${mat_type_name} ");
			autocomplete("#store_code", "../../../../queryMatStore.do?isCheck=false", "id", "text", true, true);
			 liger.get("store_code").setValue("${store_id}");
				liger.get("store_code").setText("${store_code} ${store_name} ");
			
    }
     
    </script>
</head>
<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
    	<tr>
    	 	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">证件信息：</td>
            <td align="left" class="l-table-edit-td"><input name="cert_id" type="text" id="cert_id"  ltype="text" validate="{maxlength:20}" /></td>
     
        <td align="right" class="l-table-edit-td"  >材料分类：</td>
           <td align="left" class="l-table-edit-td">
            <input name="mat_type_code" type="text" id="mat_type_code" ltype="text" validate="{required:true,maxlength:200}" />
          
         </td> 
          <td align="right" class="l-table-edit-td"  style="padding-left:20px;">仓库：</td>
            <td align="left" class="l-table-edit-td"><input name="store_code" type="text" id="store_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            
        </tr>
    </table>
	<div id="maingrid"></div>
</body>
</html>
