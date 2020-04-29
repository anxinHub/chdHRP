<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <%-- <jsp:include page="${path}/inc.jsp"/> --%>
    <jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
    <script type="text/javascript">
     var clicked = 0;
     var selectData = "";
     var grid;
     var gridManager = null;
     $(function (){
    	loadDict() ;
        loadHead(null);//加载数据
     });


     function loadHead(){

    	 grid = $("#maingrid").ligerGrid({
             columns: [
                       { display: '材料编码', name: 'inv_code', align: 'left',minWidth : 120},
                       { display: '材料名称(E)',
  					     name: 'inv_name',
  					     align: 'left',
  					     minWidth : 240,
  					     textField : 'inv_name',
  					     valueField : 'inv_name',
  					     editor :{
								type : 'select',
								textField : 'inv_name',
								valueField : 'inv_name',
								selectBoxWidth : '80%',
								selectBoxHeight : 240,
								grid : {
									columns : [ {
										display : '材料编码',
										name : 'inv_code',
										align : 'left', 
										width : 120
									}, {
										display : '材料名称',
										name : 'inv_name',
										align : 'left', 
										width : 240
									}, {
										display : '规格型号',
										name : 'inv_model',
										align : 'left'
									}, {
										display : '计量单位',
										name : 'unit_name',
										align : 'left'
									}, {
										display : '生产厂商',
										name : 'fac_name',
										align : 'left'
									}, {
										display : '供应商',
										name : 'sup_name',
										align : 'left'
									},{
										display : '零售价',
										name : 'sell_price',
										align : 'left'
									}  ],
									switchPageSizeApplyComboBox : false,
									onSelectRow: function (data) {
										var e = window.event;
										if (e && e.which == 1) {
											f_onSelectRow_detail(data);
										}
									},
									url : 'queryMatInvList.do?isCheck=false',
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
                       { display: '规格型号', name: 'inv_model', align: 'left',minWidth : 80
  					 		},
  					   { display: '计量单位', name: 'unit_name', align: 'left',minWidth : 80
					 		},
					   { display: '生产厂商', name: 'fac_name', align: 'left',minWidth : 100
							},
					   { display: '供应商', name: 'sup_name', align: 'left',minWidth : 100
							},
					   { display: '原计划价', name: 'old_price', align: 'right',minWidth : 80,render:
							function(rowdata){
								return formatNumber(rowdata.old_price,'${p04006}',1);
						   	}
					   },
					   { display: '新计划价(E)', name: 'new_price', align: 'right',minWidth : 80,
							editor : {
								type : 'numberbox',
								precision : '${p04006}'
							},
							render : function(rowdata, rowindex, value) {
								rowdata.new_price = value == null ? "" : formatNumber(value, '${p04006}', 0);
								return value == null ? "" : formatNumber(value, '${p04006}', 1);
							}
					   },
					   /* { display: '原零售单价', name: 'old_sell_price', align: 'right',minWidth : 80,render:
						   	function(rowdata){
						   		return formatNumber(rowdata.old_sell_price,'${p04006}',1);
					   		}
					   },
					   { display: '新零售单价(E)', name: 'new_sell_price', align: 'right',minWidth : 80,
								editor : {
									type : 'numberbox',
									precision : '${p04006}'
								},
								render : function(rowdata, rowindex, value) {
									rowdata.new_sell_price = value == null ? "" : formatNumber(value, '${p04006}', 0);
									return value == null ? "" : formatNumber(value, '${p04006}', 1);
								}
							}, */
					   
					   { display: '调价原因(E)', name: 'adjust_reason', align: 'left',minWidth : 80,type: 'string', editor: { type: 'string'}
							}
                       ],
                       dataAction: 'server',dataType: 'server',usePager:true,
                       url:'queryMatAdjustDetailByCode.do?isCheck=false&adjust_id='+'${adjust_id}',
                       width: '100%', height: '100%', checkbox: true,rownumbers:true,
                       enabledEdit : true,alternatingRow : true,onBeforeEdit : f_onBeforeEdit,
                       onBeforeSubmitEdit : f_onBeforeSubmitEdit,
                       isScroll : true,
                       selectRowButtonOnly:true,//heightDiff: -10,
                       toolbar: { items: [
                       			{ text: '删除', id:'delete', click: deleteRow,icon:'delete' },{ line:true },
				    	        { text: '添加行', id:'add', click: addCenterRow, icon:'add' },{ line:true },
    	        				{ text: '审核', id:'check', click: check,icon:'audit' }
  				       ]}
                     });

          gridManager = $("#maingrid").ligerGetGridManager();

          //grid.toggleCol("sup_no", false);
     }
          //回车添加行
     	 $(document).bind('keydown.grid', function(event) {
     		if (event.keyCode == 13) {// enter,也可以改成9:tab

     			grid.endEditToNextChd();

     	 	}
     	 });

     var rowindex_id = "";
 	 var column_name="";
 	 function f_onBeforeEdit(e) {
 		 rowindex_id = e.rowindex;
 		 clicked = 0;
 		 column_name=e.column.name;
 	 }

 	function  check(){
            $.ligerDialog.confirm('审核将修改材料零售价和计划价,确定修改吗?', function (yes){
             	if(yes){
                 	ajaxJsonObjectByUrl("updateCheckMatAdjustState.do?isCheck=fasle&adjust_id="+'${adjust_id}',{},function (responseData){
                 		if(responseData.state=="true"){
                 			query();
                 		}
                 	});
             	}
             });  
	}
 	 

 	 //选中回充数据
	function f_onSelectRow_detail(data, rowindex, rowobj) {

		selectData = "";
		selectData = data;
		if (selectData != "" || selectData != null) {
			if (column_name == "inv_name") {
				//回充数据
				//grid.updateCell('apply_emp', 100, e.record);
				grid.updateRow(
						rowindex_id,
						{inv_code : data.inv_code,
						inv_name : data.inv_name, 
						inv_model : (data.inv_model == "" || data.inv_model == undefined) ? '': data.inv_model,
						unit_name : data.unit_name,
						fac_name:data.fac_name,
						sup_name:data.sup_name,
						inv_id:data.inv_id,
						inv_no:data.inv_no,
						old_price:(data.plan_price == "" || data.plan_price == undefined) ? 0: data.plan_price,
						//old_sell_price:(data.sell_price == "" || data.sell_price == undefined) ? 0: data.sell_price,
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

		function save() {

			var state = '${state}';

			if(state != '1'){
				$.ligerDialog.error('只有未审核状态单据允许修改');
	    		return;
			}

			if($('#create_date').val() == ''){
				$.ligerDialog.error('编制日期不能为空');
	    		return;
			}



	    	var allData = gridManager.getData();

	    	if(allData.length == 0){
	    		$.ligerDialog.error('请添加调价单明细');
	    		return ;
	    	}

	    	var flag = true;
	    	
	    	var targetMap = new HashMap();//用于判断grid
	    	
	    	var msg = '';

	    	var rows = 0;

	    	if(allData.length != 0){

		    	$(allData).each(function(i,v){
		    		
		    		if(typeof(v.inv_id) != "undefined" && v.inv_id != null && v.inv_id != ''){
		    			
		    			if(v.inv_name == undefined || v.inv_name == ''){

			    			$.ligerDialog.error('请选择材料名称');

			    			return flag = false;
			    		}

						/* if(v.new_sell_price == undefined){

			    			$.ligerDialog.error('请填写新零售单价');

			    			return flag = false;
			    		} */
		    			if(v.new_price == undefined){

			    			$.ligerDialog.error('请填写新计划价');

			    			return flag = false;
			    		}
			    		
			    		var key = v.inv_id;

						var value="第"+(i+1)+"行";
						
			 			if(targetMap.get(key)== null || targetMap.get(key) == 'undefined' || targetMap.get(key) == ""){

			 				targetMap.put(key ,value);
			 			}else{
			 				
			 				msg += value + targetMap.get(key) +'材料重复.<br> '
			 			}
			 			
			 			rows +=1;
		    		}

		    	});
	    	}
	    	
	    	if(rows == 0){
	    		
	    		$.ligerDialog.warn('请选择材料');
	 			return false;
	    	}
	    	
	    	if(msg != ''){
	    		
	    		$.ligerDialog.warn(msg);
	    		
	    		return ;
	    	}
			
			var formPara = {
				adjust_id:'${adjust_id}',
				create_date:$('#create_date').val(),
				note:$('#note').val(),
				allData : JSON.stringify(allData)//获取所有数据

			};
			
			ajaxJsonObjectByUrl("updateMatAdjust.do", formPara,function(responseData) {

				if (responseData.state == "true") {

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
			//$("form").ligerForm();
		}

		//保存调价单
		function saveMatAdjust() {

				save();
		}

		//手动添加新行
		function addCenterRow(){

			gridManager.addRow();

	    }
		//删除选中行
		function deleteRow(){

			gridManager.deleteSelectedRow();
        }

		function loadDict(){
			$("#adjsut_code").ligerTextBox({width:160,disabled:true});
			$("#create_date").ligerTextBox({width:160});
			$("#note").ligerTextBox({width:160});

			if("${state}" != 1){
				// var dialog = frameElement.dialog;
                console.log( $(frameElement).parent(".l-dialog-content").next(".l-dialog-buttons"));
               $(frameElement).parent(".l-dialog-content").next(".l-dialog-buttons").hide();
			}
	    }
		</script>
  </head>

   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit">

        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>调价单号：</td>
            <td align="left" class="l-table-edit-td"><input name="adjsut_code" type="text" id="adjsut_code" ltype="text" value="${adjust_code}" disabled="disabled" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>

            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>编制日期：</td>
            <td align="left" class="l-table-edit-td"><input class="Wdate" name="create_date" type="text" id="create_date" value="${create_date}" ltype="text"
            onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" validate="{required:true,maxlength:20}"/></td>
            <td align="left"></td>

            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">备注：</td>
            <td align="left" class="l-table-edit-td" colspan="5"><input name="note" type="text" id="note" ltype="text" value="${note}"/></td>
            <td align="left"></td>
        </tr>

    	</table>

    <div id="maingrid"></div>
    </form>

    </body>
</html>
