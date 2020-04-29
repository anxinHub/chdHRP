<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html style="overflow:hidden;" xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
    <script src="<%=path%>/lib/stringbuffer.js"></script> 
<script type="text/javascript"> 
     var grid; 
     var detailGrid;
     var gridManager = null;
     var is_total_cont =0;
	 var is_price_cont =0;
     
     $(function (){
    	 
		/* $("#layout1").ligerLayout({
			topHeight:140,
			centerWidth:888
		}); */
        loadDict();
        //loadForm();
        loadHead(null);	 

		$("#store_code").bind("change",function(){
	    	grid.columns[4].editor.grid.url =  f_get_url;
	    	grid.reRender();
		})
		$("#sup_code").bind("change",function(){
			var sup_id = liger.get("sup_code").getValue().split(",")[0];
			var in_date = liger.get("in_date").getValue();
			if(sup_id && in_date){
				$.ajax({
					url:"../../getMatPactFkxyInfo.do?isCheck=false",
					data:{
						sup_id:sup_id,
						in_date :in_date	
					},
					type:"post",
					dataType:"json",
					success:function(msg){
						var pactFkxyInfo=msg.pactFkxyInfo?msg.pactFkxyInfo:"";
						
						liger.get("protocol_code").setValue("");
						liger.get("protocol_code").setValue(pactFkxyInfo);
						is_total_cont =msg.is_total_cont?msg.is_total_cont:0;
						is_price_cont =msg.is_price_cont?msg.is_price_cont:0;
					},
					async:false
				})
			}
			grid.columns[4].editor.grid.url =  f_get_url;
			grid.reRender();
		})
	
     });  
      
     //已选中的材料,下拉框中不再显示,不可重复选择
  	//传递已有的材料的 id batch_no bar_code 过滤掉信息
  	function f_get_url(){
  	  	
  	  	// 04021 系统参数 材料入库时是否采用供应商过滤材料 不能用于代销退货暂时屏蔽掉
  	  	
  		/* return '${p04021}' == 0 ? '../../queryMatAffiInvList.do?isCheck=false&is_com=1&store_id='+liger.get("store_code").getValue().split(",")[0]
		: '../../queryMatAffiInvList.do?isCheck=false&is_com=1&store_id='+liger.get("store_code").getValue().split(",")[0]+'&sup_id='+liger.get("sup_code").getValue().split(",")[0]; */

		return '../../queryMatAffiInvList.do?isCheck=false&is_com=1&store_id='+liger.get("store_code").getValue().split(",")[0]+'&sup_id='+liger.get("sup_code").getValue().split(",")[0];		
  		 
  	}

     
     
     
     function  save(){
		grid.endEdit();

		//if($("form").valid()){
			//主表
	 		if(liger.get("bus_type_code").getValue() == null || liger.get("bus_type_code").getValue() == ""){
	 			$.ligerDialog.warn("业务类型不能为空");  
	 			return false;  
	 		}
	 		if(liger.get("store_code").getValue() == null || liger.get("store_code").getValue() == ""){
	 			$.ligerDialog.warn("仓库不能为空");  
	 			return false;  
	 		}
	 		if($("#in_date").val() == null || $("#in_date").val() == ""){
	 			$.ligerDialog.warn("编制日期不能为空");  
	 			return false;  
	 		}
	 		if(liger.get("stocker").getValue() == null || liger.get("stocker").getValue() == ""){
	 			$.ligerDialog.warn("采购员不能为空");  
	 			return false;  
	 		} 
	 		if(liger.get("stock_type_code").getValue() == null || liger.get("stock_type_code").getValue() == ""){
	 			$.ligerDialog.warn("采购类型不能为空");  
	 			return false;  
	 		} 
	 		/* if(liger.get("sup_code").getValue() == null || liger.get("sup_code").getValue() == ""){
	 			$.ligerDialog.warn("供应商不能为空");  
	 			return false;  
	 		}  */
	 		
	 		//明细
	 		var out_detail_data = gridManager.rows;
	    	var targetMap = new HashMap();
	    	var validate_detail_buf = new StringBuffer();
	 		var store_inv = "";  //用于判断是否属于该仓库
	 		var rows = 0;
	 		var msg="";
	 		var inva_date="";
			var disinfect_date="";
			
	 		if(out_detail_data.length > 0){
	 			$.each(out_detail_data, function(d_index, d_content){ 
					if(typeof(d_content.inv_id) != "undefined" && d_content.inv_id != null && d_content.inv_id != ""){  		  
		 	      		var value="第"+(d_index+1)+"行";
		 	      		//d_content.amount = d_content.amount*-1
		 	      		if(typeof(d_content.amount) == "undefined" || d_content.amount == "" || d_content.amount >= 0){
		 	      				validate_detail_buf.append(value+"对应数量不能为空且必须小于0 请输入<br>");
		       		  	}
		 	      		
		 	      		/* 	if(d_content.imme_amount + d_content.amount <0){
		 	      			
			      				validate_detail_buf.append(value+"即时库存不足 请修改退货数量<br>");
		   		  		} */
		   		  		
		 	      		if(typeof(d_content.inva_date) != "undefined" && d_content.inva_date != null && d_content.inva_date != ""){
			      			inva_date = d_content.inva_date;
			      		}else{
			      			inva_date = "";
			      		}
			      		
			      		if(typeof(d_content.disinfect_date) != "undefined" && d_content.disinfect_date != null && d_content.disinfect_date != ""){
			      			disinfect_date = d_content.disinfect_date;
			      		}else{
			      			disinfect_date = "";
			      		}
			      		
		 	      		var key=d_content.inv_id +"|"+d_content.batch_no+"|"+d_content.bar_code+"|"+d_content.price+"|"+inva_date+"|"+disinfect_date;
						if(targetMap.get(key)== null || targetMap.get(key) == 'undefined' || targetMap.get(key) == ""){
			 				targetMap.put(key ,value);
			 			}else{
							validate_detail_buf.append(targetMap.get(key)+"与"+value+"材料编码、生产批号、条形码、单价、有效日期、灭菌日期不能重复" + "<br>");
						}
						
		 	      		/* var key=d_content.inv_id +"|"+d_content.batch_no+"|"+d_content.bar_code+"|"+d_content.price;
		 				if(targetMap.get(key)== null || targetMap.get(key) == 'undefined' || targetMap.get(key) == ""){
		 					targetMap.put(key ,value);
		 				}else{
		 					validate_detail_buf.append(targetMap.get(key)+"与"+value+"材料编码、生产批号、条形码单价不能重复" + "<br>");
		 				} */
		 				store_inv += d_content.inv_id + ",";
		 				rows += 1;
					}
				}); 
	 		}

	 		if(rows == 0){
	 			$.ligerDialog.warn('请选择材料');
	 			return false;
	 		}
	 		
	 		//判断仓库材料关系
	 		if(store_inv.length > 0){
				var para = {
					inv_ids: store_inv.substring(0, store_inv.length-1), 
					store_id: liger.get("store_code").getValue().split(",")[0]
				}
				
				ajaxJsonObjectByUrl("../../existsMatStoreIncludeInv.do?isCheck=false", para, function (responseData){
					if(responseData.state=="false"){
						//$.ligerDialog.warn("材料"+responseData.inv_ids+"不在该仓库中！");  
						//return false;
						//msg += "材料"+responseData.inv_ids+"不在该仓库中！<br>";
						validate_detail_buf.append("材料"+responseData.inv_ids+"不在该仓库中！<br>");
					}
				}, false);
				
				
				//供应商材料对应关系
				 var para = {
					inv_ids: store_inv.substring(0, store_inv.length-1), 
					sup_id: liger.get("sup_code").getValue().split(",")[0]
				}
				ajaxJsonObjectByUrl("../../existsMatAffiSupIsInv.do?isCheck=false", para, function (responseData){
					//alert(responseData.state)
					// if(responseData.state==false ){
						//msg += "材料"+responseData.inv_ids+"不属于该供应商！<br>";
						//validate_detail_buf.append("材料"+responseData.inv_ids+"不属于该供应商！<br>");
					//} 
					if(responseData.state=="false"){
						validate_detail_buf.append("材料"+responseData.inv_ids+"不属于该供应商！<br>");
					}
				}, false); 
				
				
	 		}
	 		/* if(msg != ""){
	 			$.ligerDialog.warn(msg);  
				return false;  
	 		} */
	 		
	 		
	 		if(validate_detail_buf.toString()  != ""){
	 			$.ligerDialog.warn(validate_detail_buf.toString());
	 			return false;
	 		}
			
	 		if(gridManager.rows.length == 0){
	 			$.ligerDialog.warn('请选择材料');
	 			return false;
	 		}
			
	        var formPara={
				in_no : $("#in_no").val(),
				bus_type_code : liger.get("bus_type_code").getValue(),
				store_id : liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[0],
				store_no : liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[1],
				in_date : $("#in_date").val(),
				stocker : liger.get("stocker").getValue() == null ? "" : liger.get("stocker").getValue(),
				sup_id : liger.get("sup_code").getValue() == null ? "" : liger.get("sup_code").getValue().split(",")[0],
				sup_no : liger.get("sup_code").getValue() == null ? "" : liger.get("sup_code").getValue().split(",")[1],
				stock_type_code : liger.get("stock_type_code").getValue() == null ? "" : liger.get("stock_type_code").getValue(),
				app_dept : liger.get("app_dept").getValue() == null ? "" : liger.get("app_dept").getValue().split(",")[1],
				protocol_code : liger.get("protocol_code").getValue() == null ? "" : liger.get("protocol_code").getValue(),
				proj_id : liger.get("proj_code").getValue() == null ? "" : liger.get("proj_code").getValue(),
				brief : $("#brief").val(),
				detailData : JSON.stringify(gridManager.getData())
			};
	        
	        ajaxJsonObjectByUrl("addMatAffiBack.do",formPara,function(responseData){
	        	if(responseData.state=="true"){
	        		parentFrameUse().query();
	            	$.ligerDialog.confirm('是否继续添加退货单?', function (yes){
	            		if(yes){
	            			this_refresh();
	            		}else{
	            			parentFrameUse().update_open(responseData.update_para);
	            			this_close();
	            		}
	            	});
	            }
	        
	        });
		//}
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
    	autocomplete("#bus_type_code", "../../queryMatBusType.do?isCheck=false", "id", "text", true, true, {codes : '29'}, true);
    	//autocomplete("#store_code", "../../queryMatStore.do?isCheck=false", "id", "text", true, true, {is_com : '1'}, true,"","","",200);
    	autocomplete("#store_code", "../../queryMatStoreDictDate.do?isCheck=false", "id", "text", true, true, {is_com : '1',is_write:'1'}, true,"","","",200);
		autocomplete("#stocker", "../../queryMatStockEmp.do?isCheck=false", "id", "text", true, true, "", true);
		autocomplete("#sup_code", "../../queryHosSupDictDisable.do?isCheck=false", "id", "text", true, true,"","","","","",300);
		autocomplete("#stock_type_code", "../../queryMatStockType.do?isCheck=false", "id", "text", true, true,'',true);
//		autocomplete("#app_dept", "../../queryMatDept.do?isCheck=false", "id", "text", true, true, {is_last : '1'},true,"","","",300);
		autocomplete("#app_dept", "../../queryMatDeptDictDate.do?isCheck=false", "id", "text", true, true, {is_last : '1',is_write:'1'},true,"","","",300);
		
		autocomplete("#proj_code", "../../queryMatProj.do?isCheck=false", "id", "text", true, true,"","","","","",490);
		//格式化文本框
		$("#protocol_code").ligerTextBox({width:320,disabled:true,cancelable: false});
        $("#in_no").ligerTextBox({width:160, disabled:true});
        $("#in_date").ligerTextBox({width:160});
        autodate("#in_date");//默认当前日期
        $("#brief").ligerTextBox({width:490});
        $("#proj_code").ligerTextBox({width:490});
        //格式化按钮
		$("#save").ligerButton({click: save, width:90});
		$("#close").ligerButton({click: this_close, width:90});
     } 
    
	function loadHead(){
		grid = $("#maingrid").ligerGrid({
			columns: [ { 
				display: '材料编码', name: 'inv_code', align: 'left',width:120,
				totalSummary: {
					align: 'right',
					render: function (suminf, column, cell) {
						return '<div>合计：</div>';
					}
				}
			}, { 
				display: '材料名称(E)', name: 'inv_id', textField: 'inv_name',align: 'left',width:240,
				editor : {
					type : 'select',
	         		valueField : 'inv_id',
	         		textField : 'inv_name',
	         		selectBoxWidth : '80%',
	         		selectBoxHeight : 240,
	         		grid : {
	         			columns : [ {
	         				display : '材料编码', name : 'inv_code', width : 100, align : 'left'
	         			}, {
	         				display : '材料名称', name : 'inv_name', width : 240, align : 'left'
	         			}, {
	         				display : '物资类别', name : 'mat_type_name', width : 140, align : 'left'
	         			}, {
	         				display : '规格型号', name : 'inv_model', width : 140, align : 'left'
	         			}, {
	         				display : '生产厂商', name : 'fac_name', width : 100, align : 'left'
	         			}, { 
	        	         	display: '批号', name: 'batch_no', align: 'left', width: 140, align : 'left'
	       	         	}, { 
	        	         	display: '条形码', name: 'bar_code', align: 'left', width: 100, align : 'left'
	       	         	}, { 
	       					display: '单价', name: 'price', align: 'right', width:140,
	       					render : function(rowdata, rowindex, value) {
	       						return value == null ? "" : formatNumber(value, '${p04006}', 1);
	       					}
	       				}, { 
	         				display : '库存', name : 'cur_amount', width : 100, align : 'left'
	         			}, { 
	         				display : '即时库存', name : 'imme_amount', width : 100, align : 'left'
	         			}, {
	         				display : '是否收费', name : 'is_charge', width : 140, align : 'left',
	         				render : function(rowdata, rowindex, value) {
	         					return value == 1 ? '是' : '否';
	         				}
	         			}, { 
         					display : '有效日期', name : 'inva_date', width : 100, align : 'left'
         				}, { 
         					display : '灭菌日期', name : 'disinfect_date', width : 100, align : 'left'
	         			}, {
	         				display : '货位', name : 'location_name', width : 100, align : 'left'
	         			} ],
	         			switchPageSizeApplyComboBox : false,
	         			onSelectRow: function (data) {
							var e = window.event;
							if (e && e.which == 1) {
								f_onSelectRow_detail(data);
							}
						},
	         			url :f_get_url,
	         			pageSize : 500,
						onSuccess: function (data, g) { //加载完成时默认选中
							if (grid.editor.editParm) {
								var editor = grid.editor.editParm.record;
								var item = data.Rows.map(function (v, i) {
									return v.inv_name;
								});
								var index = item.indexOf(editor.inv_name) == -1 ? 0 : item.indexOf(editor.inv_name);
								//alert(index);
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
	         }, { 
	         	display: '规格型号', name: 'inv_model', align: 'left',width:140
	         }, { 
	         	display: '计量单位', name: 'unit_name', align: 'left',width:140
	         }, { 
	         	display: '批号', name: 'batch_no', align: 'left',width:250
	         }, { 
	         	display: '当前库存', name: 'cur_amount', align: 'left',width:140
	         }, { 
	         	display: '即时库存', name: 'imme_amount', align: 'left',width:140
	         }, { 
	         	display: '数量(E)', name: 'amount', align: 'left', width:140, editor : {type : 'number'},
				totalSummary: {
					align: 'left',
					render: function (suminf, column, cell) {
						return '<div>' + formatNumber(suminf.sum == null ? 0 : suminf.sum, 2, 1) + '</div>';
					}
				}
	         }, { 
				display: '单价', name: 'price', align: 'right',width:140,
				render : function(rowdata, rowindex, value) {
					return value == null ? "" : formatNumber(value, '${p04006}', 1);
				}
			}, { 
				display: '金额', name: 'amount_money', align: 'right',width:140,
				render : function(rowdata, rowindex, value) {
					rowdata.amount_money = value == null ? "" : formatNumber(value, '${p04005}', 0);
					return value == null ? "" : formatNumber(value, '${p04005}', 1);
				},
				totalSummary: {
					align: 'right',
					render: function (suminf, column, cell) {
						return '<div>' + formatNumber(suminf.sum == null ? 0 : suminf.sum, '${p04005}', 1) + '</div>';
					}
				}
			}, { 
				display: '有效日期', name: 'inva_date', align: 'left',width:120
			}, { 
				display: '灭菌日期', name: 'disinfect_date', align: 'left',type: 'date',format: 'yyyy-MM-dd',width:120
			}, { 
				display: '条形码', name: 'bar_code', align: 'left',width:120
			}, { 
				display: '批发价格', name: 'sale_price', align: 'right',width:140,
				/*editor : {
					type : 'numberbox',
					precision : '${p04006}'
        		},*/
				render : function(rowdata, rowindex, value) {
					rowdata.sale_price = value == null ? "" : formatNumber(value, '${p04006}', 0);
					return value == null ? "" : formatNumber(value, '${p04006}', 1);
				}
			}, { 
				display: '批发金额', name: 'sale_money', align: 'right',width:140,
				render : function(rowdata, rowindex, value) {
					rowdata.sale_money = value == null ? "" : formatNumber(value, '${p04005}', 0);
					return value == null ? "" : formatNumber(value, '${p04005}', 1);
				},
				totalSummary: {
					align: 'right',
					render: function (suminf, column, cell) {
						return '<div>' + formatNumber(suminf.sum == null ? 0 : suminf.sum, '${p04005}', 1) + '</div>';
					}
				}
			}, { 
				display: '零售价格', name: 'sell_price', align: 'right',width:140,
				render : function(rowdata, rowindex, value) {
					rowdata.sell_price = value == null ? "" : formatNumber(value, '${p04072}', 0);
					return value == null ? "" : formatNumber(value, '${p04072}', 1);
				}
			}, { 
				display: '零售金额', name: 'sell_money', align: 'right',width:140,
				render : function(rowdata, rowindex, value) {
					rowdata.sell_money = value == null ? "" : formatNumber(value, '${p04073}', 0);
					return value == null ? "" : formatNumber(value, '${p04073}', 1);
				},
				totalSummary: {
					align: 'right',
					render: function (suminf, column, cell) {
						return '<div>' + formatNumber(suminf.sum == null ? 0 : suminf.sum, '${p04073}', 1) + '</div>';
					}
				}
			}, { 
				display: '货位名称', name: 'location_name', align: 'left',width:180
			}, { 
				display: '备注(E)', name: 'note', align: 'left',width:180,
				editor : {
					type : 'text'
				}
			}, { 
				display: '批次数量合计', name: 'sum_amount',width:140,hide:true 
			}, { 
				display: '批次明细', name: 'inv_detail_data',width:140,hide:true
			} ],
			usePager : false,width : '100%',height : '100%',enabledEdit : true,fixedCellHeight:true,heightDiff:-20,
			onBeforeEdit : f_onBeforeEdit, onBeforeSubmitEdit : f_onBeforeSubmitEdit, onAfterEdit : f_onAfterEdit,
			checkbox: true, rownumbers:true, frozen:false,//这个属性有detail明细的时候必须为false,否则明细显示不出来
			detail: { onShowDetail: showBatchSn, reload: true, single: true},//材料批次明细
			toolbar: { items: [
				//{ text: '保存', id:'save', click: save,icon:'save' },
				//{ line:true },
				{ text: '删除', id:'delete', click: deleteRow,icon:'delete' },
				{ line:true },
				{ text: '入库单导入', id:'in_imp', click: in_imp,icon:'up' },
				{ line:true },
				{ text: '显示供应商全部材料', id:'aaa', click: queryallMaterials,icon:'query' }
			]}
		});

        gridManager = $("#maingrid").ligerGetGridManager();
        
    }  
	
	var rowindex_id = "";
	var column_name = "";
	function f_onBeforeEdit(e) {
		rowindex_id = e.rowindex;
		column_name = e.column.name;		
	}
	 function btn_saveColumn(){
 		
		   var path = window.location.pathname+"/maingrid";
		   var url = '../../../sys/addBatchSysTableStyle.do?isCheck=false';
		   saveColHeader({
			   grid:grid,
			   path:path,
			   url:url,
			   callback:function(data){
				   $.ligerDialog.success("保存成功");
			   }
		   });
	  
	   return false;
} 
	//选中回充数据
	function f_onSelectRow_detail(data, rowindex, rowobj) {
		selectData = "";
		selectData = data;
		//alert(JSON.stringify(data)); 
		//回充数据 
		if (selectData != "" || selectData != null) {
			if (column_name == "inv_id") {
				grid.updateRow(rowindex_id, {
					inv_no : data.inv_no,
					inv_code : data.inv_code,
					inv_name : data.inv_name,
					inv_model : data.inv_model == null ? "" : data.inv_model,
					unit_name : data.unit_name == null ? "" : data.unit_name,
					batch_no : data.batch_no == null ? "" : data.batch_no,
					bar_code : data.bar_code == null ? "" : data.bar_code,
					cur_amount : data.cur_amount == null ? "" : data.cur_amount,
					imme_amount : data.imme_amount == null ? "" : data.imme_amount,
					sale_price : data.sale_price == null ? "" : data.sale_price,
					sell_price : data.sell_price == null ? "" : data.sell_price,
					inva_date : data.inva_date == null ? "" : data.inva_date,
					disinfect_date : data.disinfect_date == null ? "" : data.disinfect_date,
					location_id : data.location_id == null ? "" : data.location_id,
					location_name : data.location_name == null ? "" : data.location_name,
					amount : data.is_per_bar==1 || data.is_highvalue == 1 ? "-1" : "",
					price : data.price == null ? "" : data.price,
					amount_money : data.is_per_bar==1 || data.is_highvalue == 1 ? data.price*(-1) : ""
				});
			}
		}
		return true;
	}
    function f_onAfterEdit(e){
    	if("inv_id" == column_name){
    		grid.updateCell('sum_amount', 0, e.record); 
    		grid.updateCell('inv_detail_data', "", e.record); 
    	}else if("amount" == column_name){
    		grid.updateCell('amount_money', e.record.amount*e.record.price, e.record); 
    		grid.updateCell('sale_money', e.record.amount*e.record.sale_price, e.record); 
    		grid.updateCell('sell_money', e.record.amount*e.record.sell_price, e.record);
    		grid.updateCell('sum_amount', 0, e.record); 
    		grid.updateCell('inv_detail_data', "", e.record); 
    		grid.updateTotalSummary();
    	}
    }
	// 编辑单元格提交编辑状态之前作判断限制
	function f_onBeforeSubmitEdit(e) {
		if (e.column.name == "inv_id" && !e.value){
			//$.ligerDialog.warn('请选择材料！');
			return false;
		}
		if (e.column.name == "amount" && !e.value){
			//$.ligerDialog.warn('数量不能为0！');
			return false;
		}
		return true;
	} 
    
    //删除
    function deleteRow(){ 
    	
    	gridManager.deleteSelectedRow();
    }
    
	//批量添加明细数据
    function add_Rows(data){
    	grid.deleteAllRows();
    	grid.addRows(data);
    }
    //清空表格
    function delete_allRows(){
		for (var i = 0, l = gridManager.rows.length; i < l; i++) {  
			var o = gridManager.getRow(i);
			if (o['__id'] in gridManager.records)
				gridManager._deleteData.ligerDefer(gridManager, 10, [ o ]); 
		}  
		gridManager.reRender.ligerDefer(gridManager, 20);
	}
    
    var gridRowData;
    function showBatchSn(row, detailPanel,callback){
    	gridRowData = row;
    	batchSn = document.createElement('div');
        $(detailPanel).append(batchSn);
		//detailGrid =$(detailPanel).css('margin',10).ligerGrid({
		detailGrid =$(batchSn).css({'margin-top':10, 'margin-left':60}).ligerGrid({
    		columns: [{ 
    			display: '材料编码', name: 'inv_code', width:100
    		}, { 
    			display: '材料名称(E)', name: 'inv_id', textField: 'inv_name', width:240,
				editor : {
					type : 'select',
	         		valueField : 'inv_id',
	         		textField : 'inv_name',
	         		selectBoxWidth : 500,
	         		selectBoxHeight : 240,
	         		grid : {
	         			columns : [ {
	         				display : '材料编码', name : 'inv_code', width : 100, align : 'left'
	         			}, {
	         				display : '材料名称', name : 'inv_name', width : 240, align : 'left'
	         			}, {
	        	         	display: '批次', name: 'batch_sn', align: 'left', width: 80, align : 'left'
	       	         	}, { 
	       					display: '单价', name: 'price', align: 'right', width:80,
	       					render : function(rowdata, rowindex, value) {
	       						return value == null ? "" : formatNumber(value, '${p04006}', 1);
	       					}
	       				}, { 
	         				display : '库存', name : 'cur_amount', width : 50, align : 'left'
	         			}, { 
	         				display : '即时库存', name : 'imme_amount', width : 50, align : 'left'
	         			} ],
	         			switchPageSizeApplyComboBox : false,
	         			//onSelectRow: f_detail_onSelectRow_detail,
	         			onSelectRow: function (data) {
							var e = window.event;
							if (e && e.which == 1) {
								f_detail_onSelectRow_detail(data);
							}
						},
	         			url : '../../queryMatAffiOutDetailInvList.do?isCheck=false&is_com=1&store_id=' 
	         					+ liger.get("store_code").getValue().split(",")[0] + '&inv_id=' + row.inv_id
	         					+ '&batch_no=' + row.batch_no + '&bar_code=' + row.bar_code + '&price=' + row.price
	         					+ '&inva_date=' + row.inva_date + '&disinfect_date=' + row.disinfect_date,
	         			pageSize : 10,
						onSuccess: function (data, g) { //加载完成时默认选中
							if (detailGrid.editor.editParm) {
								var editor = detailGrid.editor.editParm.record;
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
	         		delayLoad : false, keySupport : true, autocomplete : true,// rownumbers : true,
	         		onSuccess: function (data, grid) {
						this.parent("tr").next(".l-grid-row").find("td:first").focus();
					},
					ontextBoxKeyEnter: function (data) {
						f_detail_onSelectRow_detail(data.rowdata);
					}
	         	}
    		}, { 
    			display: '批次', name: 'batch_sn', align : 'left' 
    		}, { 
     			display : '库存', name : 'cur_amount', minWidth : 50, align : 'left'
     		}, { 
     			display : '即时库存', name : 'imme_amount', minWidth : 50, align : 'left', 
     		}, { 
    			display: '数量(E)', name: 'amount', width: 60, align : 'left', editor : {type : 'float'}
    		}, { 
    			display: '单价', name: 'price', width: 80, align : 'right',
    			render : function(rowdata, rowindex, value) {
    				rowdata.price = value == null ? "" : formatNumber(value, '${p04006}', 0);
    				return value == null ? "" : formatNumber(value, '${p04006}', 1);
    			}
    		}, { 
    			display: '金额', name: 'amount_money', align: 'right', width:80,
    			render : function(rowdata, rowindex, value) {
    				rowdata.amount_money = value == null ? "" : formatNumber(value, '${p04005}', 0);
    				return value == null ? "" : formatNumber(value, '${p04005}', 1);
    			}
    		}, { 
    			display: '批发单价', name: 'sale_price', width: 80, align : 'right',
    			render : function(rowdata, rowindex, value) {
    				rowdata.sale_price = value == null ? "" : formatNumber(value, '${p04006}', 0);
    				return value == null ? "" : formatNumber(value, '${p04006}', 1);
    			}
    		}, { 
    			display: '批发金额', name: 'sale_money', align: 'right', width:80,
    			render : function(rowdata, rowindex, value) {
    				rowdata.sale_money = value == null ? "" : formatNumber(value, '${p04005}', 0);
    				return value == null ? "" : formatNumber(value, '${p04005}', 1);
    			}
    		}, { 
    			display: '零售单价', name: 'sell_price', width: 80, align : 'right',
    			render : function(rowdata, rowindex, value) {
    				rowdata.sell_price = value == null ? "" : formatNumber(value, '${p04072}', 0);
    				return value == null ? "" : formatNumber(value, '${p04072}', 1);
    			} 
    		}, { 
    			display: '零售金额', name: 'sell_money', align: 'right', width:80,
    			render : function(rowdata, rowindex, value) {
    				rowdata.sell_money = value == null ? "" : formatNumber(value, '${p04073}', 0);
    				return value == null ? "" : formatNumber(value, '${p04073}', 1);
    			}
    		} ], 
    		usePager : true,checkbox: true,
    		rownumbers: true, enabledEdit : true, fixedCellHeight: true, frozen: false,
    		onBeforeEdit : f_detail_onBeforeEdit, onBeforeSubmitEdit : f_detail_onBeforeSubmitEdit, onAfterEdit : f_detail_onAfterEdit,
    		width: '65%',height: '90%',columnWidth:80, data : f_getInvDetailData(row),
    		toolbar: { items: [
    			{ text: '删除', id:'delete', click: deleteDetailRow,icon:'delete' },
    			{ line:true },
    			{ text: '添加行', id:'add', click: detailGridAddRow,icon:'add' },
    			{ line:true }
    		]} 
		});

		//默认添加行
		detailGridAddRow();
    }
    
    //添加空行
    function detailGridAddRow(){
    	setTimeout(function () {detailGrid.addRow();}, 1000);
    }
    
    function f_getInvDetailData(rowdata){
    	var data = { Rows: [] };
    	//alert("是否存在数据："+JSON.stringify(rowdata.inv_detail_data));
		if(validateStr(rowdata.inv_id) && validateStr(rowdata.amount) && rowdata.amount != 0){
			//明细中有批次信息并且主数量和明细数量相等
			if(validateStr(rowdata.inv_detail_data) && validateStr(rowdata.sum_amount) && rowdata.amount == rowdata.sum_amount){
				var rows = jsonRowsToObject(rowdata.inv_detail_data, "in_detail_id"); 
				for(var i = 0; i < rows.length; i++){
					data.Rows.push(rows[i]);
				}
    		}else{
    			//明细中没有批次信息，需要根据先进先出从后台取出
        		var invPara = {
    				store_id : liger.get("store_code").getValue().split(",")[0],
            		inv_id : rowdata.inv_id,
            		batch_no : rowdata.batch_no,
            		bar_code : rowdata.bar_code,
            		price : rowdata.price,
            		amount : rowdata.amount,
            		inva_date : rowdata.inva_date,
            		disinfect_date : rowdata.disinfect_date
            	}
        		ajaxJsonObjectByUrl("../../queryMatAffiInvByFifo.do?isCheck=false",invPara,function(responseData){
        			data = responseData;
                }, false);
				//变更主数据中材料批次信息
        		grid.updateCell('sum_amount', gridRowData.amount, gridRowData); 
        		grid.updateCell('inv_detail_data', JSON.stringify(data.Rows), gridRowData); 
        	}
    	}
        return data;
    }
    
    //改变明细数量更新主数据数量
    function changeDetailAmount(){
    	//获取明细总数量，并更新主数据数量
		var sumAmount =0;
		var dataDetail =  detailGrid.getData();
		if(dataDetail.length > 0){
			$(dataDetail).each(function(){
				if(validateStr(this.amount)){
					sumAmount += this.amount;
				}
			})
    		grid.updateCell('amount', sumAmount, gridRowData); 
    		grid.updateCell('amount_money', sumAmount*gridRowData.price, gridRowData); 
    		grid.updateCell('sale_money', sumAmount*gridRowData.sale_price, gridRowData); 
    		grid.updateCell('sell_money', sumAmount*gridRowData.sell_price, gridRowData); 
    		grid.updateCell('sum_amount', sumAmount, gridRowData); 
    		grid.updateCell('inv_detail_data', JSON.stringify(dataDetail), gridRowData); 
		}else{
			grid.updateCell('amount', sumAmount, gridRowData); 
    		grid.updateCell('amount_money', sumAmount*gridRowData.price, gridRowData); 
    		grid.updateCell('sale_money', sumAmount*gridRowData.sale_price, gridRowData); 
    		grid.updateCell('sell_money', sumAmount*gridRowData.sell_price, gridRowData); 
    		grid.updateCell('sum_amount', sumAmount, gridRowData); 
    		grid.updateCell('inv_detail_data', "", gridRowData); 
		}
    }

	var detail_id = "";
	var detail_name = "";
	function f_detail_onBeforeEdit(e) {
		detail_id = e.rowindex;
		detailClicked = 0;
		detail_name = e.column.name;		
	}
	//选中回充数据
	function f_detail_onSelectRow_detail(data, rowindex, rowobj) {
		detailSelectData = "";
		detailSelectData = data;
		//alert(JSON.stringify(data)); 
		//回充数据 
		if (detailSelectData != "" || detailSelectData != null) {
			if (detail_name == "inv_id") {
				detailGrid.updateRow(detail_id, {
					inv_no : data.inv_no,
					inv_code : data.inv_code,
					inv_name : data.inv_name,
					batch_sn : data.batch_sn == null ? "" : data.batch_sn,
					cur_amount : data.cur_amount == null ? "" : data.cur_amount,
					imme_amount : data.imme_amount == null ? "" : data.imme_amount,
					price : data.price == null ? "" : data.price,
					sale_price : data.sale_price == null ? "" : data.sale_price,
					sell_price : data.sell_price == null ? "" : data.sell_price
				});
			}
		}
		return true;
	}
    function f_detail_onAfterEdit(e){ 
    	if("amount" == detail_name){ 
    		detailGrid.updateCell('amount_money', e.record.amount*e.record.price, e.record); 
    		detailGrid.updateCell('sale_money', e.record.amount*e.record.sale_price, e.record); 
    		detailGrid.updateCell('sell_money', e.record.amount*e.record.sell_price, e.record); 
    		changeDetailAmount();
    	} 
    } 
    // 编辑单元格提交编辑状态之前作判断限制
	function f_detail_onBeforeSubmitEdit(e) {
		if (e.column.name == "inv_id" && e.value == ""){
			//$.ligerDialog.warn('请选择材料！');
			//detailGrid.setCellEditing(e.record, e.column, true);
			return false;
		}
		if (e.column.name == "amount" && e.value == 0){
			//$.ligerDialog.warn('数量不能为0！');
			//detailGrid.setCellEditing(e.record, e.column, true);
			return false;
		}
		return true;
	}
    //删除明细
    function deleteDetailRow(){ 
    	
    	detailGrid.deleteSelectedRow();
    	changeDetailAmount();
    }
    
    function in_imp(){
    	var store_id = liger.get("store_code").getValue();
    	if(store_id == null || store_id == "" || store_id == "defined"){
    		$.ligerDialog.error("请先选择库房！");
    		return;
    	}
    	var sup_id = liger.get("sup_code").getValue();
    	if(sup_id == null || sup_id == "" || sup_id == "defined"){
    		$.ligerDialog.error("请先选择供应商！");
    		return;
    	}
    	var para = "store_id=" + liger.get("store_code").getValue() +
    		"&store_text=" + liger.get("store_code").getText() + 
    		"&sup_id=" + liger.get("sup_code").getValue() +
    		"&sup_text=" + liger.get("sup_code").getText();
    	$.ligerDialog.open({
			title: '入库单导入',
			height: 500,
			width: 900,
			url: 'matAffiBackInImpPage.do?isCheck=false&'+para,
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true, top: 1
		});
    }
 
	function is_addRow(){//当数据为空时 默认新增一行
		setTimeout(function () {
			var data = grid.getData();
			if (data.length==0)
				grid.addRow();
			}, 1000);
	} 
	
	function this_close(){
		frameElement.dialog.close();
	}
	
	function validateStr(str){
		if(str == null || str == 'undefined' || str == ''){
			return false;
		}
		return true;
	}
	//显示该库房下全部材料
	//by 2017年3月21日16:32:44  @denhui
	function queryallMaterials(){
	
		var para = "store_id=" + liger.get("store_code").getValue() +
		"&store_text=" + liger.get("store_code").getText() + 
		"&sup_id=" + liger.get("sup_code").getValue() +
		"&sup_text=" + liger.get("sup_code").getText();
		
		var store_id=liger.get("store_code").getValue().split(",")[0];
    	if(liger.get("sup_code").getValue() == null || liger.get("sup_code").getValue() == "" || liger.get("sup_code").getValue() == "defined"){
    		$.ligerDialog.error("请先选择供应商！");
    		return;
    	}
    	var sup_id=liger.get("sup_code").getValue().split(",")[0];
    	   $.ligerDialog.open({
	            url: "matAffiBackAddSupPage.do?isCheck=false&"+para,
	            height: 500,width: 900,title: '选择',modal: true,showToggle: false,
	            showMax: true, showMin: false, isResize: true,
	            buttons: [{text: '确定',onclick: function(item, dialog) {dialog.frame.checkMatBar();},
	                    cls: 'l-dialog-btn-highlight' },
	                {text: '取消',onclick: function(item, dialog) {dialog.close();}
	                }
	            ]
	        });
/* 		
		ajaxJsonObjectByUrl("queryAllMatbySupId.do?isCheck=false&store_id="+store_id+"&sup_id="+sup_id,null,function(responseData){
			var data = responseData;
			grid.data = data.Rows;
			grid.deleteAllRows();
			grid.addRows(data.Rows);
			//lodeData();
        }, false); */
		is_addRow();
		
	}
	function changeIndate(){
		var sup_id = liger.get("sup_code").getValue().split(",")[0];
		var in_date = liger.get("in_date").getValue();
		if(sup_id && in_date){
			$.ajax({
				url:"../../getMatPactFkxyInfo.do?isCheck=false",
				data:{
					sup_id:sup_id,
					in_date :in_date	
				},
				type:"post",
				dataType:"json",
				success:function(msg){
					var pactFkxyInfo=msg.pactFkxyInfo?msg.pactFkxyInfo:"";
					
					liger.get("protocol_code").setValue("");
					liger.get("protocol_code").setValue(pactFkxyInfo);
					is_total_cont =msg.is_total_cont?msg.is_total_cont:0;
					is_price_cont =msg.is_price_cont?msg.is_price_cont:0;
				},
				async:false
			})
		}
		
	}
    </script>

	</head>
  
	<body onload="is_addRow()">
		<div id="pageloading" class="l-loading" style="display: none"></div>
		<input name="is_dir" type="hidden" id="is_dir" />
		<div id="layout1">
			<div position="top">
				<form name="form1" method="post"  id="form1" >
					<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
				        <tr>
				            <td align="right" class="l-table-edit-td" >
				            	<span style="color:red">*</span>退货单号：
				            </td>
				            <td align="left" class="l-table-edit-td">
				            	<input name="in_no" type="text" id="in_no" value="自动生成" ltype="text" disabled="disabled" required="true" validate="{required:true}" />
				            </td>
				            <td align="right" class="l-table-edit-td" >
				            	<span style="color:red">*</span>业务类型：
				            </td>
				            <td align="left" class="l-table-edit-td">
				            	<input name="bus_type_code" type="text" id="bus_type_code" ltype="text" required="true" validate="{required:true}" />
				            </td>
				            <td align="right" class="l-table-edit-td"  >
				            	<span style="color:red">*</span>仓库：
				            </td>
				            <td align="left" class="l-table-edit-td">
				            	<input name="store_code" type="text" id="store_code" ltype="text" required="true" validate="{required:true}" />
				            </td>
				        </tr> 
				        <tr>
				            <td align="right" class="l-table-edit-td" >
				            	<span style="color:red">*</span>制单日期：
				            </td>
				            <td align="left" class="l-table-edit-td">
				            	<input class="Wdate" name="in_date" id="in_date" type="text" required="true" onchange="changeIndate()" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" validate="{required:true}" />
				            </td>
				            <td align="right" class="l-table-edit-td"  >
				            	<span style="color:red">*</span>采购员：
				            </td>
				            <td align="left" class="l-table-edit-td">
				            	<input name="stocker" type="text" id="stocker" ltype="text" required="true" validate="{required:true}" />
				            </td>
				            <td align="right" class="l-table-edit-td"  >
				            	<span style="color:red">*</span>采购类型：
				            </td>
				            <td align="left" class="l-table-edit-td">
				            	<input name="stock_type_code" type="text" id="stock_type_code" ltype="text" validate="{required:true}" />
				            </td>
				        </tr> 
						<tr>
				            <td align="right" class="l-table-edit-td"  >
				            	供应商：
				            </td>
				            <td align="left" class="l-table-edit-td">
				            	<input name="sup_code" type="text" id="sup_code" ltype="text" required="true" validate="{required:true}" />
				            </td>
				            <td align="right" class="l-table-edit-td"  >申请科室：</td>
				            <td align="left" class="l-table-edit-td">
				            	<input name="app_dept" type="text" id="app_dept" ltype="text" validate="{required:false}" />
				            </td>
				            <td align="right" class="l-table-edit-td" >
								协议编号：
				            </td>
				            <td align="left" class="l-table-edit-td" >
				            	<input name="protocol_code" type="text" id="protocol_code" ltype="text" validate="{required:false}" />
				            </td>
						</tr>
				        <tr>
				            <td align="right" class="l-table-edit-td" >
								项&nbsp;&nbsp;&nbsp;&nbsp;目：
				            </td>
				            <td align="left" class="l-table-edit-td" colspan="5">
				            	<input name="proj_code" type="text" id="proj_code" ltype="text" validate="{required:false,maxlength:20}" />
				            </td>
				        </tr>
				        <tr>
				            <td align="right" class="l-table-edit-td" >
								备&nbsp;&nbsp;&nbsp;&nbsp;注：
				            </td>
				            <td align="left" class="l-table-edit-td" colspan="5">
				            	<input name="brief" type="text" id="brief" ltype="text" validate="{required:false,maxlength:50}" />
				            </td>
						</tr>
				    </table>
			    </form>
		    </div>
		    <div position="center" style="width: 100%; height: 100%;">
				<div id="maingrid"></div>
				<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%"  style="margin-top: 5px;">
					<tr>	
						<td align="center" class="l-table-edit-td" >
							<button id ="save" accessKey="B"><b>保存（<u>B</u>）</b></button>
							&nbsp;&nbsp;
							<button id ="close" accessKey="C"><b>关闭（<u>C</u>）</b></button>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</body>
</html>
