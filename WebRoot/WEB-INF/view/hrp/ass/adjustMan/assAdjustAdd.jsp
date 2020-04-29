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
     var clicked = 0;
     var selectData = "";   
     var grid;
     var gridManager = null;
     $(function (){
    	loadDict();
        loadHead(null);//加载数据
    	
     });  
     
     
     function loadHead(){
    	 
    	 grid = $("#maingrid").ligerGrid({   
             columns: [ 
                       { display: '资产编码', name: 'ass_code', align: 'left',minWidth : 120},
                       { display: '资产名称(E)',
  					     name: 'ass_name',
  					     align: 'left',
  					     minWidth : 240,
  					     textField : 'ass_name',
  					     valueField : 'ass_name',
  					     editor :{
								type : 'select',
								textField : 'ass_name',
								valueField : 'ass_name',
								selectBoxWidth : '80%',
								selectBoxHeight : 240,
								grid : {
									columns : [ {
										display : '资产编码',
										name : 'ass_code',
										align : 'left', 
										width : 120
									}, {
										display : '资产名称',
										name : 'ass_name',
										align : 'left', 
										width : 240
									}, {
										display : '规格型号',
										name : 'ass_model',
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
									}, {
										display : '零售价',
										name : 'price',
										align : 'left'
									}  ],
									switchPageSizeApplyComboBox : false,
									onSelectRow: function (data) {
										var e = window.event;
										if (e && e.which == 1) {
											f_onSelectRow_detail(data);
										}
									},
									url : 'queryAssList.do?isCheck=false',
									//delayLoad:true,
									usePager:true,
									pageSize : 30,
									onSuccess: function (data, g) { //加载完成时默认选中
										if (grid.editor.editParm) {
											var editor = grid.editor.editParm.record;
											var item = data.Rows.map(function (v, i) {
												return v.ass_name;
											});
											var index = item.indexOf(editor.ass_name) == -1 ? 0 : item.indexOf(editor.ass_name);
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
                       { display: '规格型号', name: 'ass_model', align: 'left',minWidth : 80
  					 		},
  					   { display: '计量单位', name: 'unit_name', align: 'left',minWidth : 80
					 		},
					   { display: '生产厂商', name: 'fac_name', align: 'left',minWidth : 100
							},
					   { display: '供应商', name: 'sup_name', align: 'left',minWidth : 100
							}, 
					   { display: '原计划价', name: 'old_price', align: 'right',minWidth : 80,render:
							function(rowdata){
								return formatNumber(rowdata.old_price,'${para_value}',1);
							}
					   },
					   { display: '新计划价(E)', name: 'new_price', align: 'right',minWidth : 80,
							editor : {
								type : 'numberbox',
								precision : '${ass_05006}'
					   },
							render : function(rowdata, rowindex, value) {
								rowdata.new_price = value == null ? "" : formatNumber(value, '${ass_05006}', 0);
								return value == null ? "" : formatNumber(value, '${ass_05006}', 1);
							}
						},
					   { display: '调价原因(E)', name: 'adjust_reason', align: 'left',minWidth : 80,type: 'string', editor: { type: 'string'}
							}    
                       ],
                       usePager:true,
                       width: '100%', height: '100%', checkbox: true,rownumbers:true,
                       enabledEdit : true,alternatingRow : true,onBeforeEdit : f_onBeforeEdit,
                       onBeforeSubmitEdit : f_onBeforeSubmitEdit,
                       isScroll : true,
                       selectRowButtonOnly:true,//heightDiff: -10,
                       toolbar: { items: [
                       			{ text: '删除', id:'delete', click: deleteRow,icon:'delete' },{ line:true },
				    	        { text: '添加行', id:'add', click: addCenterRow, icon:'add' },{ line:true }
				    	       
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
          
     	function add_rows(data) {
			//先清除数据然后再添加
			grid.deleteAllRows();
			grid.addRows(data);
		}
     var rowindex_id = "";
 	 var column_name="";
 	 function f_onBeforeEdit(e) {
 		 rowindex_id = e.rowindex;
 		 clicked = 0;
 		 column_name=e.column.name;
 	 }
	
 	
 	 //选中回充数据
	function f_onSelectRow_detail(data, rowindex, rowobj) {

		selectData = "";
		selectData = data;
		if (selectData != "" || selectData != null) {
			if (column_name == "ass_name") {
				//回充数据 
				//grid.updateCell('apply_emp', 100, e.record);
				grid.updateRow(
						rowindex_id,
						{ass_code : data.ass_code,
						ass_name : data.ass_name,
						ass_model : (data.ass_model == "" || data.ass_model == undefined) ? '': data.ass_model,
						unit_name : data.unit_name,
						fac_name:data.fac_name,
						sup_name:data.sup_name,
						ass_id:data.ass_id,
						ass_no:data.ass_no,
						old_price:(data.price == "" || data.price == undefined) ? 0 :data.price,
						new_price:(data.new_price == "" || data.new_price == undefined) ? 0 :data.new_price
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
			
			if($('#create_date').val() == ''){
				$.ligerDialog.error('编制日期不能为空');
	    		return;
			} 
			
			
			
	    	var allData = gridManager.getData();
	    	
	    	if(allData.length == 0){
	    		$.ligerDialog.error('请添加调价单明细');
	    		return ; 
	    	}
	    	
	    	var targetMap = new HashMap();//用于判断grid
	    	
	    	var msg = '';
	    	
	    	var rows = 0;

	    	if(allData.length != 0){

		    	$(allData).each(function(i,v){
		    		
		    		if(typeof(v.ass_id) != "undefined" && v.ass_id != null && v.ass_id != ''){
		    			
		    			if(v.ass_name == undefined || v.ass_name == ''){

			    			$.ligerDialog.error('请选择资产名称');

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
			    		var key = v.ass_id;

						var value="第"+(i+1)+"行";
						
			 			if(targetMap.get(key)== null || targetMap.get(key) == 'undefined' || targetMap.get(key) == ""){

			 				targetMap.put(key ,value);
			 			}else{
			 			
			 				msg += value + targetMap.get(key) +'资产重复.<br> '
			 			}
			 			
			 			rows +=1;
		    		}

		    	});
	    	}
	    	
	    	if(rows == 0){
	    		
	    		$.ligerDialog.warn('请选择资产');
	 			return false;
	    	}
	    	
	    	if(msg != ''){
	    		
	    		$.ligerDialog.warn(msg);
	    		
	    		return ;
	    	}
	    	
	    	
			var formPara = {
				create_date:$('#create_date').val(),
				note:$('#note').val(),
				allData : JSON.stringify(allData)//获取所有数据
				
			};
			
			ajaxJsonObjectByUrl("addAssAdjust.do", formPara,function(responseData) {
				if (responseData.state == "true") {
							parent.query();
							
							parent.openUpdate(responseData.update_para);
							
							this_close();
						
						
			           /*  $.ligerDialog.confirm('是否继续添加调价单?', function (yes){
			            	if(yes){
			            		this_refresh();
			            	}else{
			            		
			            		parent.openUpdate(responseData.update_para);
								
								this_close();
			            	}
			            }); */
				}
			});
		}
	    
	    function this_refresh(){
	        grid.reload();
	        is_addRow();
	    }
		function this_close(){
			frameElement.dialog.close();
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
		
		//保存采购计划
		function saveMatAdjust() {
				
				save();
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
		
 		
		function loadDict(){
	         //字典下拉框
			// autoCompleteByData("#state", matAdjust_state.Rows, "id", "text", true, true);//状态下拉框
			
			//返回当前年,当前月,当前日期,当前月第一天,当前月最后一天,上个月,上月第一天，上月最后一天
			var date = getCurrentDate();
	        var aa = date.split(';');
	   		$("#create_date").val(aa[2]);
	   		$("#adjsut_code").ligerTextBox({width:160,disabled:true});
			$("#create_date").ligerTextBox({width:160});
			$("#note").ligerTextBox({width:160});	
	      }   
		
		</script>
  </head>
  
   <body onload="is_addRow()">
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit">
		
        <tr>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>调价单号：</td>
            <td align="left" class="l-table-edit-td"><input name="adjsut_code" type="text" id="adjsut_code" ltype="text" value="自动生成" disabled="disabled" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>编制日期：</td>
            <td align="left" class="l-table-edit-td"><input class="Wdate" name="create_date" type="text" id="create_date" ltype="text"  
            onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" validate="{required:true,maxlength:20}"/></td>
            <td align="left"></td>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">备注：</td>
            <td align="left" class="l-table-edit-td" colspan="5"><input name="note" type="text" id="note" ltype="text" /></td>
            <td align="left"></td>
        </tr>
        
    	</table>
    
    <div id="maingrid"></div>
    </form>
   
    </body>
</html>
