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
    <script src="<%=path%>/lib/stringbuffer.js"></script>
	<script src="<%=path%>/lib/hrp/mat/mat.js"	type="text/javascript"></script>
<%-- 	<link href='<%=path%>/lib/SpreadJS/css/gcspread.sheets.9.40.20161.0.css' rel='stylesheet' type='text/css'/> --%>
<%-- 	<script src='<%=path%>/lib/SpreadJS/scripts/gcspread.sheets.all.9.40.20161.0.min.js' type='text/javascript'></script> --%>
<%-- 	<script src='<%=path%>/lib/SpreadJS/scripts/pluggable/gcspread.sheets.print.9.40.20161.0.min.js' type='text/javascript'></script> --%>
	<script type="text/javascript">
    var grid;
    var detailGrid;
    var gridManager;
    var state = '${matInMain.state}';
    var in_ids = '${in_ids}';
	$(function (){
		loadDict();//加载下拉框
		loadHead();
		//queryDetail();
		
		$("#bus_type_code").bind("change", function () { 
        	changeBus(); 
        });
		
		$("#store_code").bind("change",function(){
	    	loadHead();
	    	grid.reRender();
		})
		$("#sup_code").bind("change",function(){
	    	loadHead();
	    	grid.reRender();
		})
	});  
	
    //查询明细
 	function queryDetail(){
 		grid.options.parms=[];
 		grid.options.newPage=1;
         //根据表字段进行添加查询条件
 		grid.options.parms.push({
 			name : 'group_id',
 			value : '${matInMain.group_id}'
 		});
 		grid.options.parms.push({
 			name : 'hos_id',
 			value : '${matInMain.hos_id}'
 		});
 		grid.options.parms.push({
 			name : 'copy_code',
 			value : '${matInMain.copy_code}'
 		});
 		grid.options.parms.push({
 			name : 'in_id',
 			value : '${matInMain.in_id}'
 		});
 		grid.options.parms.push({
 			name : 'store_id',
 			value : '${matInMain.store_id}'
 		});

     	//加载查询条件
     	grid.loadData(grid.where);
 	}
    
 	function changeBus() {
		if (liger.get("bus_type_code").getValue() != 12 && liger.get("bus_type_code").getValue() != 22) {
			 
			$("#stocker_span").css("display", "none");
			$("#stocker").ligerComboBox({ width: 160, disabled: true, cancelable: false });
			liger.get("stocker").setValue("");
			liger.get("stocker").setText("");

			$("#stock_type_code_span").css("display", "none");
			$("#stock_type_code").ligerComboBox({ width: 160, disabled: true, cancelable: false });
			liger.get("stock_type_code").setValue("");
			liger.get("stock_type_code").setText("");

			$("#sup_code_span").css("display", "none");
			$("#sup_code").ligerComboBox({ width: 160, disabled: true, cancelable: false });
			liger.get("sup_code").setValue("");
			liger.get("sup_code").setText("");
		} else { 	
			$("#stocker_span").css("display", "inline");
			$("#stocker").ligerComboBox({ width: 160, disabled: false });
			
			//即墨需求   2017/04/06  根据仓库有多个采购员   gaopei
			if(liger.get("store_code").getValue()){
				var store_id = liger.get("store_code").getValue().split(",")[0]; 
				autocomplete("#stocker", "../../queryMatStockEmpByStore.do?isCheck=false", "id", "text", true, true,{store_id:store_id}, false);
			}
			

			$("#stock_type_code_span").css("display", "inline");
			$("#stock_type_code").ligerComboBox({ width: 160, disabled: false });
			liger.get("stock_type_code").selectValue(liger.get("stock_type_code").getValue());

			$("#sup_code_span").css("display", "inline");
			$("#sup_code").ligerComboBox({ width: 160, disabled: false });
			liger.get("sup_code").selectValue(liger.get("sup_code").getValue());
		}
	}
 	
	function  save(){
		grid.endEdit();
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
 		
 		if (liger.get("bus_type_code").getValue() == 12 || liger.get("bus_type_code").getValue() == 22) {
 			if(liger.get("stocker").getValue() == null || liger.get("stocker").getValue() == ""){
 	 			$.ligerDialog.warn("采购员不能为空");  
 	 			return false;  
 	 		} 
 	 		if(liger.get("stock_type_code").getValue() == null || liger.get("stock_type_code").getValue() == ""){
 	 			$.ligerDialog.warn("采购类型不能为空");  
 	 			return false;  
 	 		} 
 	 		
 			if(liger.get("sup_code").getValue() == null || liger.get("sup_code").getValue() == ""){
 	 			$.ligerDialog.warn("供应商不能为空");  
 	 			return false;  
 	 		} 
 		}
 		
 		/* if(liger.get("app_dept").getValue() == null || liger.get("app_dept").getValue() == ""){
 			$.ligerDialog.warn("申请科室不能为空");  
 			return false;  
 		}  */
 		//明细
		var validate_detail_buf = new StringBuffer();
		var detail_data = gridManager.rows;
		var targetMap = new HashMap();

		if(detail_data.length > 0){
			$.each(detail_data, function(d_index, d_content){ 
				if(d_content.inv_id){
					var value="第"+(d_index+1)+"行";
					if(typeof(d_content.amount) == "undefined" || d_content.amount == "" || d_content.amount >= 0){
	       				
		      				validate_detail_buf.append(value+"对应数量不能为空且必须小于0 请输入<br>");
					}
		      		if(d_content.imme_amount + d_content.amount <0){
		      			
	      				validate_detail_buf.append(value+"即时库存不足 请修改退货数量<br>");
			  		}
	  	      		var key=d_content.inv_id +"|"+d_content.batch_no+"|"+d_content.bar_code+"|"+d_content.price;
	  				if(targetMap.get(key)== null || targetMap.get(key) == 'undefined' || targetMap.get(key) == ""){
	  					targetMap.put(key ,value);
	  				}else{
	  					validate_detail_buf.append(targetMap.get(key)+"与"+value+"材料编码、生产批号、条形码单价不能重复" + "\n\r");
	  				}
				}
 			}); 
  		}
  		if(validate_detail_buf.toString()  != ""){
  			$.ligerDialog.warn(validate_detail_buf.toString());
  			return false;
  		}
  		if(gridManager.rows.length == 0){
  			$.ligerDialog.warn('请选择材料');
  			return false;
  		}
		var formPara={
				in_id : $("#in_id").val(),
 				in_no : $("#in_no").val(),
 				in_ids : '${in_ids}',
 				bill_no : '${matInMain.bill_no}',
 				bus_type_code : liger.get("bus_type_code").getValue(),
 				store_id : liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[0],
 				store_no : liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[1],
 				store_code : liger.get("store_code").getText() == null ? "" : liger.get("store_code").getText().split(" ")[0],
 				in_date : $("#in_date").val(),
 				stocker : liger.get("stocker").getValue() == null ? "" : liger.get("stocker").getValue(),
 				sup_id : liger.get("sup_code").getValue() == null ? "" : liger.get("sup_code").getValue().split(",")[0],
 				sup_no : liger.get("sup_code").getValue() == null ? "" : liger.get("sup_code").getValue().split(",")[1],
 				stock_type_code : liger.get("stock_type_code").getValue() == null ? "" : liger.get("stock_type_code").getValue(),
 				app_dept : liger.get("app_dept").getValue() == null ? "" : liger.get("app_dept").getValue(),
 				protocol_code : liger.get("protocol_code").getValue() == null ? "" : liger.get("protocol_code").getValue(),
				proj_id : liger.get("proj_code").getValue() == null ? "" : liger.get("proj_code").getValue(),
 				brief : $("#brief").val(),
				old_money_sum : $("#money_sum").val(),
 				detailData : JSON.stringify(gridManager.getData())
		};
		//ajaxJsonObjectByUrl("offsetMatStorageIn.do", formPara, function(responseData){
		ajaxJsonObjectByUrl("../back/addMatStorageBack.do", formPara, function(responseData){
			if(responseData.state=="true"){
				 var vo = responseData.update_para.split(",");
				var paras = 
					"group_id="+vo[0] +"&"+ 
					"hos_id="+vo[1] +"&"+ 
					"copy_code="+vo[2] +"&"+ 
					"in_id="+vo[3] +"&"+ 
					"in_no="+vo[4];
				parent.$.ligerDialog.open({
					title: '退货单修改',
					height: $(window).height(),
					width: $(window).width(),
					url: 'hrp/mat/storage/back/updatePage.do?isCheck=false&' + paras.toString(),
					modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
					parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
				});  

    			this_close();
            }
		});
	}
	
	function queryDetail(){
		grid.options.parms=[];
		grid.options.newPage=1;
        //根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'in_id',
			value : '${matInMain.in_id}'
		});
		grid.options.parms.push({
			name : 'store_id',
			value : '${matInMain.store_id}'
		});

    	//加载查询条件
    	grid.loadData(grid.where);
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
    	autocompleteAsync("#bus_type_code", "../../queryMatBusType.do?isCheck=false", "id", "text", true, true, {codes : '10,12,16,22'}, false, "${matInMain.bus_type_code}");
    	$("#bus_type_code").ligerComboBox({width:160,disabled:true,cancelable: false});
    	
    	autocompleteAsync("#store_code", "../../queryMatStore.do?isCheck=false", "id", "text", true, true);//, "", false, "${matInMain.store_id},${matInMain.store_no}");
		if("${matInMain.store_code}"){
			liger.get("store_code").setValue("${matInMain.store_id},${matInMain.store_no}");
			liger.get("store_code").setText("${matInMain.store_code} ${matInMain.store_name}");
		}
		$("#store_code").ligerComboBox({width:160,disabled:true,cancelable: false});
		
		autocomplete("#stocker", "../../queryMatStockEmp.do?isCheck=false", "id", "text", true, true, "", false, "${matInMain.stocker}");
		if("${matInMain.stocker_code}"){
			liger.get("stocker").setValue("${matInMain.stocker}}");
			liger.get("stocker").setText("${matInMain.stocker_code} ${matInMain.stocker_name}");
		}
		
		autocompleteAsync("#sup_code", "../../queryHosSupDict.do?isCheck=false", "id", "text", true, true, "", false, false, 160, false, 240);
		$("#sup_code").ligerComboBox({width:160,disabled:true,cancelable: false});
		if("${matInMain.sup_code}"){
			liger.get("sup_code").setValue("${matInMain.sup_id},${matInMain.sup_no}");
			liger.get("sup_code").setText("${matInMain.sup_code} ${matInMain.sup_name}");
		}
		
		autocomplete("#stock_type_code", "../../queryMatStockType.do?isCheck=false", "id", "text", true, true, "", false, "${matInMain.stock_type_code}");
		autocomplete("#app_dept", "../../queryHosDeptByPerm.do?isCheck=false", "id", "text", true, true, {is_last : 1}, false, "${matInMain.app_dept}");
		if("${matInMain.app_dept_code}"){
			liger.get("app_dept").setValue("${matInMain.app_dept}");
			liger.get("app_dept").setText("${matInMain.dept_code} ${matInMain.dept_name}");
		}
		
		//$("#app_dept").ligerComboBox({width:160,disabled:true,cancelable: false});
		autocomplete("#protocol_code", "../../queryMatProtocolMain.do?isCheck=false", "id", "text", true, true, "", false, false, 160, false, 240);
		if("${matInMain.protocol_code}"){
			liger.get("protocol_code").setValue("${matInMain.protocol_id},${matInMain.protocol_code}");
			liger.get("protocol_code").setText("${matInMain.protocol_code} ${matInMain.protocol_name}");
		}
		
		
		autocomplete("#proj_code", "../../queryMatProj.do?isCheck=false", "id", "text", true, true, "", false, false, 160, false, 240);
		if("${matInMain.proj_code}"){
			liger.get("proj_code").setValue("${matInMain.proj_id}");
			liger.get("proj_code").setText("${matInMain.proj_code} ${matInMain.proj_name}");
		}
		autodate("#in_date");//默认当前日期
		//格式化文本框
        $("#in_no").ligerTextBox({width:160, disabled:true});
        $("#in_date").ligerTextBox({width:160});
        $("#brief").ligerTextBox({width:320});
       
    	
     } 
    
    function loadHead(){
    	
    	grid = $("#maingrid").ligerGrid({
			columns: [ { 
				display: '材料编码', name: 'inv_code', align: 'left',width:100,
				totalSummary: {
					align : 'right',
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
	         				display : '物资类别', name : 'mat_type_name', width : 80, align : 'left'
	         			}, {
	         				display : '规格型号', name : 'inv_model', width : 180, align : 'left'
	         			}, {
	         				display : '生产厂商', name : 'fac_name', width : 100, align : 'left'
	         			}, { 
	        	         	display: '批号', name: 'batch_no', align: 'left', width: 80, align : 'left'
	       	         	}, { 
	       					display: '单价', name: 'price', align: 'right', width:80,
	       					render : function(rowdata, rowindex, value) {
	       						return value == null ? "" : formatNumber(value, '${p04006 }', 1);
	       					}
	       				}, { 
	         				display : '库存', name : 'cur_amount', width : 80, align : 'right'
	         			}, { 
	         				display : '即时库存', name : 'imme_amount', width : 80, align : 'right'
	         			}, {
	         				display : '是否收费', name : 'is_charge', width : 70, align : 'left',
	         				render : function(rowdata, rowindex, value) {
	         					return value == 1 ? '是' : '否';
	         				}
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
						url : liger.get("sup_code").getValue() ? '../../queryMatStockInvListBackNew.do?isCheck=false&store_id='+liger.get("store_code").getValue().split(",")[0] + '&sup_id=' + liger.get("sup_code").getValue().split(",")[0]+'&out_id=${out_id}'
								: '../../queryMatStockInvListBackNew.do?isCheck=false&store_id='+liger.get("store_code").getValue().split(",")[0]+'&out_id=${out_id}',
							
	         			/* url : '../../queryMatStockInvList.do?isCheck=false&store_id=' 
	         					+ liger.get("store_code").getValue().split(",")[0]+'&out_id=${out_id}', */
	         			pageSize : 10,
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
	         		delayLoad : false, keySupport : true, autocomplete : true,// rownumbers : true,
	         		onSuccess : function() {
	         			this.parent("tr").next(".l-grid-row").find("td:first").focus();
	         		},
	         		ontextBoxKeyEnter: function (data) {
						f_onSelectRow_detail(data.rowdata);
					}
	         	}
	         }, { 
	         	display: '规格型号', name: 'inv_model', align: 'left',width:180
	         }, { 
	         	display: '计量单位', name: 'unit_name', align: 'left',width:80
	         }, { 
	         	display: '批号', name: 'batch_no', align: 'left',width:80
	         }, { 
	         	display: '当前库存', name: 'cur_amount', align: 'right',width:80
	         }, { 
	         	display: '即时库存', name: 'imme_amount', align: 'right',width:80
	         }, { 
	         	display: '数量(E)', name: 'amount', align: 'right', width:80, editor : {type : 'number'},
				totalSummary: {
					align : 'right',
                    render: function (suminf, column, cell) {
                        return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, 2, 1)+ '</div>';
                    }
                } 
	         }, { 
				display: '单价', name: 'price', align: 'right',width:80,
				render : function(rowdata, rowindex, value) {
					rowdata.price = value == null ? "" : formatNumber(value, '${p04006 }', 0);
					return value == null ? "" : formatNumber(value, '${p04006 }', 1);
				}
			}, { 
				display: '金额', name: 'amount_money', align: 'right',width:80,
				render : function(rowdata, rowindex, value) {
					rowdata.amount_money = value == null ? "" : formatNumber(value, '${p04005 }', 0);
					return value == null ? "" : formatNumber(value, '${p04005 }', 1);
				},
				totalSummary: {
					align : 'right',
                    render: function (suminf, column, cell) {
                        return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, '${p04005 }', 1)+ '</div>';
                    }
                } 
			}, { 
				display: '有效日期', name: 'inva_date', align: 'left',width:120
			}, { 
				display: '灭菌日期', name: 'disinfect_date', align: 'left',type: 'date',format: 'yyyy-MM-dd',width:120
			}, { 
				display: '条形码', name: 'bar_code', align: 'left',width:120
			}, { 
				display: '批发价格', name: 'sale_price', align: 'right',width:80,
				/*editor : {
					type : 'numberbox',
					precision : '${p04006 }'
        		},*/
				render : function(rowdata, rowindex, value) {
					rowdata.sale_price = value == null ? "" : formatNumber(value, '${p04006 }', 0);
					return value == null ? "" : formatNumber(value, '${p04006 }', 1);
				}
			}, { 
				display: '批发金额', name: 'sale_money', align: 'right',width:80,
				render : function(rowdata, rowindex, value) {
					rowdata.sale_money = value == null ? "" : formatNumber(value, '${p04005 }', 0);
					return value == null ? "" : formatNumber(value, '${p04005 }', 1);
				},
				totalSummary: {
					align : 'right',
                    render: function (suminf, column, cell) {
                        return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, '${p04005 }', 1)+ '</div>';
                    }
                } 
			}, { 
				display: '零售价格', name: 'sell_price', align: 'right',width:80,
				render : function(rowdata, rowindex, value) {
					rowdata.sell_price = value == null ? "" : formatNumber(value, '${p04006 }', 0);
					return value == null ? "" : formatNumber(value, '${p04006 }', 1);
				}
			}, { 
				display: '零售金额', name: 'sell_money', align: 'right',width:80,
				render : function(rowdata, rowindex, value) {
					rowdata.sell_money = value == null ? "" : formatNumber(value, '${p04005 }', 0);
					return value == null ? "" : formatNumber(value, '${p04005 }', 1);
				},
				totalSummary: {
					align : 'right',
                    render: function (suminf, column, cell) {
                        return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, '${p04005 }', 1)+ '</div>';
                    }
                } 
			}, { 
				display: '货位名称', name: 'location_name', align: 'left',width:180
			},{
				display: '生产厂商', name: 'fac_name', width: 180, align: 'left'
			},{
				display: '注册证号(E)', name : 'cert_id', textField : 'cert_code', width : 200, align : 'left',
				 render : function(rowdata, rowindex, value) {
						return rowdata.cert_code;
					},
					editor : {
						type : 'select',
						valueField : 'code',
						textField : 'name',
						selectBoxWidth : 250,
						selectBoxHeight : 240,
						keySupport : true,
						autocomplete : true,
						onSelected : function (data){	
					    	if(typeof (data) === "string"){
					    		var formPara="";
							 	formPara = {												 			
							 		cert_id : data
							 	}			 	
					    	}
						}
					}
			}, { 
				display: '备注(E)', name: 'note', align: 'left',width:180,
				editor : {
					type : 'text'
				}
			}, { 
				display: '批次数量合计', name: 'sum_amount',width: 80, hide: true 
			}, { 
				display: '批次明细', name: 'inv_detail_data',width: 80, hide: true 
			} ],
			usePager : false,width : '100%',height : '95%',enabledEdit : true,fixedCellHeight:true,
			//data : matInDetail,
			dataAction: 'server',dataType: 'server',heightDiff:-20,
			url : 'queryMatStorageMegerBackDetail.do?isCheck=false&in_ids='+'${in_ids}'+'&store_id='+'${matInMain.store_id}',
			onBeforeEdit : f_onBeforeEdit, onBeforeSubmitEdit : f_onBeforeSubmitEdit, onAfterEdit : f_onAfterEdit,
			checkbox: true, rownumbers:true, frozen:false,//这个属性有detail明细的时候必须为false,否则明细显示不出来
			detail: { onShowDetail: showBatchSn, reload: true, single: true},//材料批次明细
			toolbar: { items: [
				{ text: '保存（<u>S</u>）', id:'save', click: save,icon:'save' },
				{ line:true }, 
				{ text: '删除（<u>D</u>）', id:'delete', click: deleteRow,icon:'delete'},
				{ line:true }, 
				{ text: '关闭（<u>L</u>）', id:'close', click: this_close, icon:'close' }
			]}
    	});
        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    var rowindex_id = "";
	var column_name = "";
	function f_onBeforeEdit(e) {
		rowindex_id = e.rowindex;
		column_name = e.column.name;
		if(column_name=='cert_id'){
			var certId = grid.getColumnByName("cert_id");
			certId.editor.url='../../queryMatInvCertId.do?isCheck=false&inv_id='+e.record.inv_id;
		}
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
					price : data.price == null ? "" : data.price,
					sale_price : data.sale_price == null ? "" : data.sale_price,
					sell_price : data.sell_price == null ? "" : data.sell_price,
					inva_date : data.inva_date == null ? "" : data.inva_date,
					disinfect_date : data.disinfect_date == null ? "" : data.disinfect_date,
					location_id : data.location_id == null ? "" : data.location_id,
					location_name : data.location_name == null ? "" : data.location_name,
					fac_name : data.fac_name == null ? "" : data.fac_name,
					cert_id : data.cert_id == null ? "" : data.cert_id,	
					cert_code : data.cert_code == null ? "" : data.cert_code		
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
    	}
    }
	// 编辑单元格提交编辑状态之前作判断限制
	function f_onBeforeSubmitEdit(e) {
		
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
    
    var gridRowData;
    function showBatchSn(row, detailPanel,callback){
    	//alert(JSON.stringify(row));    	
    	gridRowData = row;
    	batchSn = document.createElement('div');
        $(detailPanel).append(batchSn);
		//detailGrid =$(detailPanel).css('margin',10).ligerGrid({
		detailGrid =$(batchSn).css({'margin-top':10, 'margin-left':60}).ligerGrid({
    		columns: [{ 
    			display: '材料编码', name: 'inv_code',width:100, align : 'left'
    		}, { 
    			display: '材料名称(E)', name: 'inv_id', textField: 'inv_name', width:240, align : 'left',
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
	       						return value == null ? "" : formatNumber(value, '${p04006 }', 1);
	       					}
	       				}, { 
	         				display : '库存', name : 'cur_amount', width : 80, align : 'right'
	         			}, { 
	         				display : '即时库存', name : 'imme_amount', width : 80, align : 'right'
	         			} ],
	         			switchPageSizeApplyComboBox : false,
	         			onSelectRow: function (data) {
							var e = window.event;
							if (e && e.which == 1) {
								f_detail_onSelectRow_detail(data);
							}
						},
	         			url : '../../queryMatStockInvDetailList.do?isCheck=false&store_id=' 
	         					+ liger.get("store_code").getValue().split(",")[0] + '&out_id=${out_id}&inv_id=' + row.inv_id
	         					+ '&batch_no=' + row.batch_no + '&bar_code=' + row.bar_code + '&price=' + row.price,
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
	         		onSuccess : function() {
	         			this.parent("tr").next(".l-grid-row").find("td:first").focus();
	         		},
	         		ontextBoxKeyEnter: function (data) {
	         			f_detail_onSelectRow_detail(data.rowdata);
					}
	         	}
    		}, { 
    			display: '批次', name: 'batch_sn', align : 'left' 
    		}, { 
     			display : '库存', name : 'cur_amount', width : 80, align : 'right'
     		}, { 
     			display : '即时库存', name : 'imme_amount', width : 80, align : 'right', 
     		}, { 
    			display: '数量(E)', name: 'amount', width: 80, align : 'right', editor : {type : 'float'}
    		}, { 
    			display: '单价', name: 'price', width: 80, align : 'right',
    			render : function(rowdata, rowindex, value) {
    				rowdata.price = value == null ? "" : formatNumber(value, '${p04006 }', 0);
    				return value == null ? "" : formatNumber(value, '${p04006 }', 1);
    			}
    		}, { 
    			display: '金额', name: 'amount_money', align: 'right', width:80,
    			render : function(rowdata, rowindex, value) {
    				rowdata.amount_money = value == null ? "" : formatNumber(value, '${p04005 }', 0);
    				return value == null ? "" : formatNumber(value, '${p04005 }', 1);
    			}
    		}, { 
    			display: '批发单价', name: 'sale_price', width: 80, align : 'right',
    			render : function(rowdata, rowindex, value) {
    				rowdata.sale_price = value == null ? "" : formatNumber(value, '${p04006 }', 0);
    				return value == null ? "" : formatNumber(value, '${p04006 }', 1);
    			}
    		}, { 
    			display: '批发金额', name: 'sale_money', align: 'right', width:80,
    			render : function(rowdata, rowindex, value) {
    				rowdata.sale_money = value == null ? "" : formatNumber(value, '${p04005 }', 0);
    				return value == null ? "" : formatNumber(value, '${p04005 }', 1);
    			}
    		}, { 
    			display: '零售单价', name: 'sell_price', width: 80, align : 'right',
    			render : function(rowdata, rowindex, value) {
    				rowdata.sell_price = value == null ? "" : formatNumber(value, '${p04006 }', 0);
    				return value == null ? "" : formatNumber(value, '${p04006 }', 1);
    			} 
    		}, { 
    			display: '零售金额', name: 'sell_money', align: 'right', width:80,
    			render : function(rowdata, rowindex, value) {
    				rowdata.sell_money = value == null ? "" : formatNumber(value, '${p04005 }', 0);
    				return value == null ? "" : formatNumber(value, '${p04005 }', 1);
    			}
    		} ], 
    		dataAction : 'server',dataType : 'server',usePager : true,checkbox: true,
    		rownumbers: true, enabledEdit : state == 1 ? true : false, fixedCellHeight: true, frozen: false,
    		onBeforeEdit : f_detail_onBeforeEdit, onBeforeSubmitEdit : f_detail_onBeforeSubmitEdit, onAfterEdit : f_detail_onAfterEdit,
    		width: '65%',height: '90%',data : f_getInvDetailData(row),columnWidth:80, 
    		toolbar: { items: [
    			{ text: '删除', id:'delete', click: deleteDetailRow,icon:'delete' },
    			{ line:true },
    			{ text: '添加行', id:'add', click: detailGridAddRow,icon:'add' },
    			{ line:true }
    		]} 
		});
    }
    
    //添加空行
    function detailGridAddRow(){
    	setTimeout(function () {detailGrid.addRow();}, 1000);
    }
    
    function f_getInvDetailData(rowdata){
    	var data = { Rows: [] };
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
    				in_id : '${matInMain.in_id}', 
            		inv_id : rowdata.inv_id, 
            		batch_no : rowdata.batch_no, 
            		bar_code : rowdata.bar_code, 
            		price : rowdata.price,
            		amount : rowdata.amount 
            	}
        		ajaxJsonObjectByUrl("../../queryMatInvByFifo.do?isCheck=false",invPara,function(responseData){
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
			return false;
		}
		if (e.column.name == "amount" && e.value == 0){
			//$.ligerDialog.warn('数量不能为0！');
			return false;
		}
		return true;
	}
    //删除明细
    function deleteDetailRow(){ 
    	
    	detailGrid.deleteSelectedRow();
    	changeDetailAmount();
    }
    
    
    
    function remove(){
    	if(state > 1){
    		$.ligerDialog.error('修改失败，单据不是未审核状态！');
    		return;
    	}
    	grid.deleteSelectedRow();
    }
	
	function getData(){
		var manager = $("#maingrid").ligerGetGridManager();
		var data = manager.getData();
		return JSON.stringify(data);
	} 

	//当单据未审核 默认新增一行
    function is_addRow(){
    	if(state > 1){
	    	return;
    	}
    	setTimeout(function() {
			grid.addRow();
		}, 1000);
    }

	//键盘事件
	function loadHotkeys() {
		hotkeys('S', save);
		hotkeys('D', remove);
		hotkeys('L', this_close);
	}
	
	
    //添加新退货单
    function add_open(){
    	
    	parentFrameUse().add_open();
    	this_close();
	}
    
	
	//关闭当前页面
	function this_close(){
		frameElement.dialog.close();
	}
	
	function validateStr(str){
		if(str == null || str == 'undefined' || str == ''){
			return false;
		}
		return true;
	}
    </script>
  
</head>
  
<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post"  id="form1" >
		<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
			<tr>
	            <td style="display: none;">
	            	<input name="group_id" type="text" id="group_id" value="${matInMain.group_id}" ltype="text" />
	            	<input name="hos_id" type="text" id="hos_id" value="${matInMain.hos_id}" ltype="text" />
	            	<input name="copy_code" type="text" id="copy_code" value="${matInMain.copy_code}" ltype="text" />
	            	<input name="in_id" type="text" id="in_id" value="${matInMain.in_id}" ltype="text" />
	            </td>
			</tr>
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
	            	<span style="color:red">*</span>入库日期：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input class="Wdate" name="in_date" id="in_date"  type="text" required="true" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" validate="{required:true}" />
	            </td>
	            <td align="right" class="l-table-edit-td"  >
	            	<span id="stocker_span" style="color:red">*</span>采购员：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="stocker" type="text" id="stocker" ltype="text" required="true" validate="{required:true}" />
	            </td>
	            <td align="right" class="l-table-edit-td"  >
	            	<span id="stock_type_code_span" style="color:red">*</span>采购类型：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="stock_type_code" type="text" id="stock_type_code" ltype="text" validate="{required:false}" />
	            </td>
	        </tr> 
			<tr>
	            <td align="right" class="l-table-edit-td"  >
	            	<span id="sup_code_span" style="color:red">*</span>供应商：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="sup_code" type="text" id="sup_code" ltype="text" required="true" validate="{required:true}" />
	            </td>
	            <td align="right" class="l-table-edit-td"  >
	            	申请科室：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="app_dept" type="text" id="app_dept" ltype="text" validate="{required:false}" />
	            </td>
	            <td align="right" class="l-table-edit-td" >
					协议编号：
	            </td>
	            <td align="left" class="l-table-edit-td" >
	            	<input name="protocol_code" type="text" id="protocol_code" ltype="text" validate="{required:false,maxlength:20}" />
	            </td>
			</tr>
	        <tr>
	            <td align="right" class="l-table-edit-td" >
					项目：
	            </td>
	            <td align="left" class="l-table-edit-td" >
	            	<input name="proj_code" type="text" id="proj_code" ltype="text" validate="{required:false,maxlength:20}" />
	            </td>
	            <td align="right" class="l-table-edit-td" >
					备&nbsp;&nbsp;&nbsp;&nbsp;注：
	            </td>
	            <td align="left" class="l-table-edit-td" colspan="5">
	            	<input name="brief" type="text" id="brief" ltype="text" value="${matInMain.brief}" validate="{required:false,maxlength:50}" />
	            </td>
			</tr>
	    </table>
    </form>
    <div id="maingrid"></div>
    
</body>
</html>
