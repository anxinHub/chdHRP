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
     var dataFormat;
     var validity_type;  //证件效期类型
     $(function (){
         loadDict()//加载下拉框
         loadForm();
//          $("#layout1").ligerLayout({
//              centerBottomHeight:260
//          });
         loadHead();
         loadHeadSup();

 		$("#type_id").bind("change", function () {
 			if(liger.get("type_id").getValue){
	 			validity_type = liger.get("type_id").getValue().split(",")[1];
	 			if(validity_type == 1){
					$("#start_date_span").css("display", "none");
					$("#end_date_span").css("display", "none");
	 			}else{
					$("#start_date_span").css("display", "inline");
					$("#end_date_span").css("display", "inline");
	 			}
 			}
 		});
 		
 		$("#start_date").blur(function(){
 			
 			$("#cert_date").val($("#start_date").val());
 		}); 
     });
     
     function validateGrid(){
    	if(!$("#impower_code").val()){
    		$.ligerDialog.warn("授权编码不能为空！");
    		return false;
    	}
    	
    	
    	/* if(!liger.get("fac_id").getValue()){
   			$.ligerDialog.warn("生产厂商不能为空！");
   			return false;
   		} */
		//效期管理的证件需要验证起止日期
   		if(validity_type == 2){
	    	if(!$("#start_date").val()){
	    		$.ligerDialog.warn("起始日期不能为空！");
	    		return false;
	    	}
	    	if(!$("#end_date").val()){
	    		$.ligerDialog.warn("截止日期不能为空！");
	    		return false;
	    	}
   		}

    	 //明细
   		var msg="";
   		var rowm = "";
   		var rows = 0;
   		var data = gridManager.getData();
   		//判断grid 中的数据是否重复或者为空
   		var targetMap = new HashMap();
   		$.each(data,function(i, v){
   			rowm = "";
   			if(v.inv_id){

 	 			msg += rowm;
 	 			var key=v.inv_id ;
 	 			var value="第"+(i+1)+"行";
 	 			if(targetMap.get(key)== null || targetMap.get(key) == 'undefined' || targetMap.get(key) == ""){
 	 				targetMap.put(key ,value);
 	 			}else{

 	 				msg += v.inv_code +"  材料的对应关系已经存在，不能重复添加！" + "\n\r";
 	 			}
 	 			rows = rows + 1;
   			}
   		});

   		if(msg != ""){
   			$.ligerDialog.warn(msg);
  			return false;
   		}
   		return true;
     }
     function  save(){
    	 if(validateGrid()){
    		var allData = gridManager.getData();
   	        var formPara={
   	           impower_id:${impower_id},
   	           impower_code:$("#impower_code").val(),
   	           fac_id:liger.get("fac_id").getValue().split(",")[0],
   	           sup_id:liger.get("sup_id").getValue().split(",")[0],
   	           sup_id_b:liger.get("sup_id_b").getValue().split(",")[0],
   	           impower_start_date:$("#impower_start_date").val(),
   	           impower_end_date:$("impower_#end_date").val(),
   	           impower_person:$("#impower_person").val(),
   	           impower_tel:$("#impower_tel").val(),
   	           impower_mobile:$("#impower_mobile").val(),
   	           file_path:$("#file_path").val(),
   	           file_address:$("#file_address").val(),
   	           is_stop:$("#is_stop").val(),
   	           detailData: JSON.stringify(allData)
   	         };
			
   	        ajaxJsonObjectByUrl("addMatInvImpower.do",formPara,function(responseData){
   	            if(responseData.state=="true"){
   					 $("input[name='impower_code']").val('');
   					 $("input[name='fac_id']").val('');
   					 $("input[name='sup_id']").val('');
   					 $("input[name='sup_id_b']").val('');
   					 $("input[name='impower_start_date']").val('');
   					 $("input[name='impower_end_date']").val('');
   					 $("input[name='impower_person']").val('');
   					 $("input[name='impower_tel']").val('');
   					 $("input[name='impower_mobile']").val('');
   					 $("input[name='file_path']").val('');
   					 $("input[name='file_address']").val('');
   					 $("input[name='is_stop']").val('');
   					 loadDict();
   	                 grid.loadData();
   					 parentFrameUse().query();
   	            }
   	        });
    	}

    }

     function loadHead(){
       	 grid = $("#maingrid").ligerGrid({
                columns: [
							{display : '交易编码',name : 'bid_code',align : 'left'},
                          { display: '材料编码', name: 'inv_code', align: 'left'},
                          { display: '材料名称',
     					     name: 'inv_name',
     					     align: 'left',
     					     textField : 'inv_name',
     					     valueField : 'inv_name',
     					     editor :{
    								type : 'select',
    								textField : 'inv_name',
    								valueField : 'inv_name',
    								selectBoxWidth : '80%',
    								selectBoxHeight : 240,
    								grid : {
    									columns : [
    								    	{display : '交易编码',name : 'bid_code',align : 'left'},
    								    	{display : '材料编码',name : 'inv_code',align : 'left'},
    								    	{display : '材料名称',name : 'inv_name',align : 'left'},
    								    	{display : '规格型号',name : 'inv_model',align : 'left', width: 200},
    								   		{display : '计量单位',name : 'unit_name',align : 'left'},
    								   		{display : '单价',name : 'plan_price',align : 'left'},
    								    	{display : '生产厂商',name : 'fac_name',align : 'left', width: 200},
    								   		{display : '供应商',name : 'sup_name',align : 'left', width: 200}
    								    ],
    								    switchPageSizeApplyComboBox : false,
    									onSelectRow: function (data) {
    										var e = window.event;
    										if (e && e.which == 1) {
    											f_onSelectRow_detail(data);
    										}
    									},
    									url : 'queryMatImpowerInvList.do?isCheck=false',
    									usePager:true,
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
    			  					delayLoad : true,keySupport : true,autocomplete : true,
    								onSuccess: function (data, grid) {
    									this.parent("tr").next(".l-grid-row").find("td:first").focus();
    								},
    								ontextBoxKeyEnter: function (data) {
    									f_onSelectRow_detail(data.rowdata);
    								}
     					     	}
     					   },
                           { display: '规格型号', name: 'inv_model', align: 'left', width: 200},
     					   { display: '计量单位', name: 'unit_name', align: 'left'},
     					   {display : '单价',name : 'plan_price',align : 'left'},
    		  			   { display: '生产厂商', name: 'fac_name', align: 'left', width: 240},
    		  			   { display: '供应商', name: 'sup_name', align: 'left', width: 240},
    		  			   { display: 'inv_id', name: 'inv_id', align: 'left', hide: true },
    		  			   { display: 'inv_no', name: 'inv_no', align: 'left', hide: true }
                          ],
                          dataAction: 'server',dataType: 'server',usePager:true,
                          width: '100%', height: '95%', checkbox : true,isAddRow:false ,
              			  enabledEdit : true, rownumbers : true,
            			  onBeforeEdit : f_onBeforeEdit,
            			  onBeforeSubmitEdit : f_onBeforeSubmitEdit,
            			  onAfterEdit : f_onAfterEdit,
            			  delayLoad : true,//初始化明细数据
            			  selectRowButtonOnly : true,//heightDiff: -10,
                          toolbar: { items: [
                          			{ text: '删除', id:'delete', click: deleteRow,icon:'delete' },
                          			{ line:true },
                          			{ text: '添加行', id:'new', click: addNew,icon:'add' },
                          			{ line:true },
                          			{ text: '保存', id:'save', click: save,icon:'save' },
                          			{ line:true },
                          			{ text: '关闭', id:'clolse', click: this_close,icon:'close' },
                          			{ line:true },
                          			{ text: '选择材料', id:'choice_inv', click: choice_inv,icon:'add' },
                          			{ line:true },
                          			{ text: '上传文件', id:'upLodePage', click: upLodePage,icon:'add' },
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
    					bid_code : data.bid_code,
    					inv_code : data.inv_code,
    					inv_name : data.inv_name,
    					inv_model : (data.inv_model == "" || data.inv_model == undefined) ? '': data.inv_model,
    					unit_name : data.unit_name,
    					fac_name : data.fac_name,
    					inv_id : data.inv_id,
    					inv_no : data.inv_no,
    					fac_id:data.fac_id,
    					plan_price : data.plan_price,
    					sup_name:data.sup_name
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
    	//删除选中行
    	 function deleteRow(){
    	 	gridManager.deleteSelectedRow();
         }
    	
    	//添加行数据
    	function add_rows(data){
    	   grid.deleteAllRows();
    	   grid.addRows(data);
    	}
    	
		//选择材料页面跳转
    	 function choice_inv(){
    			
    		$.ligerDialog.open({url: "matInvImpowerChoiceInvPage.do?isCheck=false",
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
    	 function upLodePage(){
   	  	  var parm = "impower_id="+${impower_id};
   	    	 	 parent.$.ligerDialog.open({url:'hrp/mat/info/basic/impower/upLodePage.do?isCheck=false&'+parm,data:{},height: 500,width: 900, 
   	    	     			 title:'上传',modal:true,showToggle:false,showMax:true,showMin: false,isResize:true ,parentframename:window.name }); 
   	      }

 	function loadForm(){
	    $.metadata.setType("attr", "validate");
	     var v = $("form").validate({
	         errorPlacement: function (lable, element)
	         {
	             if (element.hasClass("l-textarea"))
	             {
	                 element.ligerTip({ content: lable.html(), target: element[0] });
	             }
	             else if (element.hasClass("l-text-field"))
	             {
	                 element.parent().ligerTip({ content: lable.html(), target: element[0] });
	             }
	             else
	             {
	                 lable.appendTo(element.parents("td:first").next("td"));
	             }
	         },
	         success: function (lable)
	         {
	             lable.ligerHideTip();
	             lable.remove();
	         },
	         submitHandler: function ()
	         {
	             $("form .l-text,.l-textarea").ligerHideTip();
	         }
	     });
	     //$("form").ligerForm();
 	}


    function loadDict(){
    	
        //字典下拉框
    	autocomplete("#fac_id", "../../../queryHosFacDict.do?isCheck=false", "id", "text", true, true,"","","",400,"",400);
    	autocomplete("#sup_id", "../../../queryHosSupDict.do?isCheck=false", "id", "text", true, true,"","","",400,"",400);
    	autocomplete("#sup_id_b", "../../../queryHosSupDict.do?isCheck=false", "id", "text", true, true,"","","",400,"",400);
    	$("#impower_code").ligerTextBox({width:400});
    	$("#impower_start_date").ligerTextBox({width:150});
    	$("#impower_end_date").ligerTextBox({width:155});
    	$("#impower_person").ligerTextBox({width:162});
    	$("#impower_tel").ligerTextBox({width:162});
    	$("#impower_mobile").ligerTextBox({width:150});
    	$("#file_address").ligerTextBox({width:400});
    	$("#file_path").ligerTextBox({width:400});
    	$("#is_stop").ligerTextBox({width:150});
     }

    function is_addRow() {
		setTimeout(function () { //当数据为空时 默认新增一行
			grid.addRow();
		}, 100);
	}

    function addNew(){
   	 	grid.addRowEdited();
    }
    
    function removeRow(){
    	gridManagerSup.deleteSelectedRow();
    }
    
    function addRow(){
   	 	gridSup.addRow();
    }
    
    function this_close(){
   	 	frameElement.dialog.close();
    }
    </script>

  </head>

  <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" border="0" width="100%">
		 	<tr>
	            <td align="right" class="l-table-edit-td" ><b><font color ="red">*</font>授权编码:</b></td>
	            <td align="left" class="l-table-edit-td" >
	            	<input name="impower_code" type="text" id="impower_code"  ltype="text" validate="{required:true,maxlength:100}" />
				</td>
				
	            <td align="right" class="l-table-edit-td"><b>生产厂商:</b></td>
	            <td align="left"  class="l-table-edit-td" >
	            	<input name="fac_id" type="text" id="fac_id" ltype="text" validate="{required:false}" />
	            </td>
	        </tr>
            <tr>
                <td align="right" class="l-table-edit-td"><b>授权单位:</b></td>
	            <td align="left"  class="l-table-edit-td" >
	            	<input name="sup_id" type="text" id="sup_id" ltype="text" validate="{required:false}" />
	            </td>
	            
	            <td align="right" class="l-table-edit-td"><b>被授权单位:</b></td>
	            <td align="left"  class="l-table-edit-td" >
	            	<input name="sup_id_b" type="text" id="sup_id_b" ltype="text" validate="{required:false}" />
	            </td>
            </tr>
	        <tr>

	            <td align="right" class="l-table-edit-td" >
	            	<b><font id="start_date_span" color ="red">*</font>起始日期:</b>
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<table>
	            		<tr>
	            			<td>
	            				<input class="Wdate" name="impower_start_date" type="text" id="impower_start_date" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" ltype="text" validate="{required:true,maxlength:20}" />
	            			</td>
				            <td align="right" class="l-table-edit-td" style="padding-left: 28px;">
				            	<b><font id="end_date_span" color ="red">*</font>截止日期:</b>
				            </td>
				            <td align="left" class="l-table-edit-td">
				            	<input class="Wdate" name="impower_end_date" type="text" id="impower_end_date"  onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" ltype="text" validate="{required:true,maxlength:20}" />
				            </td>
	            		</tr>
	            	</table>
	            </td>
	            
	            <td align="right" class="l-table-edit-td" ><b>是否停用:</b></td>
	            <td align="left" class="l-table-edit-td">
	            	<table>
	            		<tr>
	            			<td>
	            				<select id="is_stop" name="is_stop">
	            				   <option value="0">否</option>
	            		           <option value="1">是</option>
                	            </select>
	            			</td>
				            <td align="right" class="l-table-edit-td" style="padding-left: 28px;"><b>　联系人:</b></td>
				            <td align="left" class="l-table-edit-td">
				            	<input name="impower_person" type="text" id="impower_person"  ltype="text" validate="{maxlength:50}" />
				            </td>
	            		</tr>
	            	</table>
	            </td>
	        </tr>
	        <tr>
	            <td align="right" class="l-table-edit-td" ><b>存档位置:</b></td>
	            <td align="left" class="l-table-edit-td" >
	            	<input name="file_address" type="text" id="file_address"  ltype="text" validate="{maxlength:50}" />
	           	</td>
	            <td align="right" class="l-table-edit-td" ><b>手机:</b></td>
	            <td align="left" class="l-table-edit-td">
	            	<table>
	            		<tr>
	            			<td>
	            				<input name="impower_mobile" type="text" id="impower_mobile"  ltype="text" validate="{maxlength:50}" />
	            			</td>
				            <td align="right" class="l-table-edit-td" style="padding-left: 28px;"><b>联系电话:</b></td>
				            <td align="left" class="l-table-edit-td">
				            	<input name="impower_tel" type="text" id="impower_tel"  ltype="text" validate="{maxlength:50}" />
				            </td>
	            		</tr>
	            	</table>
	            </td>
	       </tr>
	       <tr>
	           	
	           	
	            <td align="right" class="l-table-edit-td" ><b>文档路径:</b></td>
	            <td align="left" class="l-table-edit-td" >
	            	<input name="file_path" type="text" id="file_path" value="${file_path}" ltype="text" validate="{maxlength:200}" />
	            </td>
	       </tr>
	       
        </table>
    </form>
	
    <div id="layout1" >
		<div id="maingrid" ></div>
<!-- 		<div  position="centerbottom" id="maingridSup"></div> -->
	</div>
</body>
</html>
