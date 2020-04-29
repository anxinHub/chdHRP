<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
    <script src="<%=path%>/lib/stringbuffer.js"></script>
    <style>
    	.l-loading { 
		    display: block;
		    width: 100%;
		    height: 100%; 
		    top: 0;
		    left: 0; 
		    bottom: 0;
		    right: 0;
		    background-position: center center;
    	}
    </style>
<script type="text/javascript"> 
     var grid;
     var detailGrid;
     var gridManager = null;
	 var isEmpByDept = '${p04043 }' == 1 ? true : false;
	 var isUseAffiStore = '${p04044 }' == 1 ? true : false;
		
     $(function (){
    	
		$("#layout1").ligerLayout({
			topHeight:145,
			centerWidth:888
		});
        loadDict();
        //loadForm();
        loadHead(null);	
      	//仓库绑定change事件
		$("#bus_type_code").bind("change",function(){
	    	//loadHead();
	    	console.log(grid);
	    	grid.columns[5].editor.grid.url =f_get_url;  //已选材料,弹窗不再显示
	    		/* '../../../queryMatStockInvListOut.do?isCheck=false&is_com=0&store_id=' 
					+ liger.get("store_id").getValue().split(",")[0] + '&bus_type_code=' 
 					+ liger.get("bus_type_code").getValue(); */
	    	grid.reRender();
		});
		$("#store_id").bind("change",function(){
	    	//loadHead();
	    	//console.log(grid);
	    	grid.columns[5].editor.grid.url = f_get_url;  //已选材料,弹窗不再显示
	    		/* '../../../queryMatStockInvListOut.do?isCheck=false&is_com=0&store_id=' 
					+ liger.get("store_id").getValue().split(",")[0] + '&bus_type_code=' 
 					+ liger.get("bus_type_code").getValue(); */
	    	grid.reRender();
	    	
		});
		//科室绑定change事件
		$("#dept_id").bind("change",function(){
			if(isEmpByDept && liger.get("dept_id").getValue()){
		    	var para = {dept_id : liger.get("dept_id").getValue() == "" ? "0" : liger.get("dept_id").getValue().split(",")[0]};
		    	liger.get("dept_emp").setValue("");
		    	liger.get("dept_emp").setText("");
		    	autocomplete("#dept_emp", "../../../queryMatEmpDict.do?isCheck=false", "id", "text", true, true, para, false);
			}
		});
		

     });  
     
     function  save(){
    	 
		grid.endEdit();
    	 
    	var validate_detail_buf = new StringBuffer();
    	
		//弹出框验证 
    	var bus_type_code = liger.get("bus_type_code").getValue();if(!bus_type_code){validate_detail_buf.append("请选择业务类型<br>");}
     	
    	var store_id = liger.get("store_id").getValue();if(!store_id){validate_detail_buf.append("请选择业务仓库<br>");}
    	
    	var out_date = $("#out_date").val();if(!out_date){validate_detail_buf.append("请选择出库日期<br>");}
    	
    	var dept_id = liger.get("dept_id").getValue();if(!dept_id){validate_detail_buf.append("请选择领用科室<br>");}
    	
    	var dept_emp = liger.get("dept_emp").getValue();//if(!dept_emp){validate_detail_buf.append("请选择领料人<br>");}

    	if($("#brief").val() && $("#brief").val().length > 100){
    		validate_detail_buf.append("摘要长度不能大于100\n");
    	}
    	
    	var out_detail_data = gridManager.rows;
    	var targetMap = new HashMap();
 		var store_inv = "";  //用于判断是否属于该仓库
 		var rows = 0;
		var inva_date="";
		var disinfect_date="";
 		if(out_detail_data.length > 0){

 			$.each(out_detail_data, function(d_index, d_content){ 

				if(typeof(d_content.inv_id) != "undefined" && d_content.inv_id != null && d_content.inv_id != ""){  		  
	 	      		var value="第"+(d_index+1)+"行";
	
	 	      		if(typeof(d_content.amount) == "undefined" || d_content.amount == ""){
						validate_detail_buf.append(value+"对应数量为空 请输入<br>");
	       		  	}
	 	      		
	 	      		if(bus_type_code == "21"){
		 	      		if(d_content.amount > 0){
		 	      			validate_detail_buf.append(value+"退库数量不能大于0 请重新输入<br>");
		   		  		}
	 	      		}else if(bus_type_code == '11' || bus_type_code == '17' || bus_type_code == '19' || bus_type_code == '23'){
	 	      			if(d_content.amount > 0){
	 	      				if(d_content.imme_amount - d_content.amount <0){
			 	      			validate_detail_buf.append(value+"数量不能大于即时库存数据数量 请重新输入<br>");
			   		    	}
		   		  		}
	 	      		}else{
		 	      		if(d_content.amount < 0){
		 	      			validate_detail_buf.append(value+"出库数量不能小于0 请重新输入<br>");
		   		  		}
		 	      		if(d_content.imme_amount - d_content.amount <0){
		 	      			validate_detail_buf.append(value+"数量不能大于即时库存数据数量 请重新输入<br>");
		   		  		}
	 	      		}
	 	      		
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
	 					validate_detail_buf.append(targetMap.get(key)+"与"+value+"材料编码、生产批号、条形码、单价、有效日期、灭菌日期不能重复<br>");
	 				}
	 				
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
				store_id: liger.get("store_id").getValue().split(",")[0]
			}
			ajaxJsonObjectByUrl("../../../existsMatStoreIncludeInv.do?isCheck=false", para, function (responseData){
				if(responseData.state=="false"){
					//$.ligerDialog.warn("以下材料不在该仓库中：<br>"+responseData.inv_ids);  
					//return false;
					validate_detail_buf.append("以下材料不在该仓库中：<br>"+responseData.inv_ids);
				}
			}, false);
 		}

 		if(validate_detail_buf.toString()  != ""){
 			$.ligerDialog.warn(validate_detail_buf.toString());
 			return false;
 		}
    	 
        var formPara={
        		bus_type_code:liger.get("bus_type_code").getValue(),
        		store_id:liger.get("store_id").getValue().split(",")[0],
        		store_no:liger.get("store_id").getValue().split(",")[1],
        		brief:$("#brief").val(),
        		out_date:$("#out_date").val(),
        		dept_id:liger.get("dept_id").getValue().split(",")[0],
        		dept_no:liger.get("dept_id").getValue().split(",")[1],
        		dept_emp:liger.get("dept_emp").getValue() == null ? "" : liger.get("dept_emp").getValue().split(",")[0],
        		use_code : liger.get("use_code").getValue() == null ? "" : liger.get("use_code").getValue(),
        		proj_id : liger.get("proj_code").getValue() == null ? "" : liger.get("proj_code").getValue(),
        		is_dir:$("#is_dir").val(),
        		app_date : $("#app_date").val() == null ? "" : $("#app_date").val() ,//2017/04/10  即墨  申领日期
        		out_detail_data:JSON.stringify(out_detail_data)
		};
        ajaxJsonObjectByUrl("addMatOutMain.do",formPara,function(responseData){
            if(responseData.state=="true"){
            	parentFrameUse().query();
            	$.ligerDialog.confirm('是否继续添加出库单?', function (yes){
            		if(yes){
            			$("#brief").val("");
            			loadDict();
                        grid.reload();
                        is_addRow();
            		}else{ 
            			parentFrameUse().openUpdate(responseData.update_para);
            			this_close();
            		 }
            	}); 
            }
        });
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
   
    function saveMatOutMain(){
        if($("form").valid()){
        	 
            save();
        }
   }
    function loadDict(){
        //字典下拉框
         $("#app_date").ligerTextBox({width:160}); 
        
    	$("#out_no").ligerTextBox({width:160,disabled: true }); 
    	$("#out_date").ligerTextBox({width:160}); 
    	
    	autodate("#out_date");
    	//$("#brief").ligerTextBox({width:465}); 
            
   		var bus_type_code_paras={codes : "3,5,7,9,11,17,19,20,23,24,25,49", read_or_write:1}; 
    	autocompleteAsync("#bus_type_code", "../../../queryMatBusType.do?isCheck=false", "id", "text", true, true,bus_type_code_paras,true,false);
    	
    	autocompleteAsync("#store_id", "../../../queryMatStoreDictDate.do?isCheck=false", "id", "text", true, true, isUseAffiStore ? {is_write:'1'} : {is_com : 0,is_write:'1'}, true);
    	
    	loadDictDeptId();

    	autocomplete("#dept_emp", "../../../queryMatEmpDict.do?isCheck=false", "id", "text", true, true, isEmpByDept ? {dept_id: liger.get("dept_id").getValue().split(",")[0]} : "", false); 
		
    	autocomplete("#use_code", "../../../queryMatOutUse.do?isCheck=false", "id", "text", true, true);
    	
    	autocomplete("#proj_code", "../../../queryMatProj.do?isCheck=false", "id", "text", true, true);

    	$("#begin_bar").ligerTextBox({width: 160}); 
    	
    	$("#end_bar").ligerTextBox({width: 160}); 
     } 
    
    // 加载科室下拉框，并会在子页面执行
    function loadDictDeptId (value) {
    	autocompleteAsync("#dept_id", "../../../queryMatDeptDictDate.do?isCheck=false", "id", "text", true, true, {is_last: 1,is_write:"1"}, false, value);
    }
    
	function loadHead(){
		grid = $("#maingrid").ligerGrid({
			columns: [ { 
	         	display: '交易编码', name: 'bid_code', align: 'left', width: 100
	         }, { 
				display: '材料编码', name: 'inv_code', align: 'left', width: 100,
				totalSummary: {
					align : 'right',
                    render: function (suminf, column, cell) {
                        return '<div>合计：</div>';
                    }
                }
			}, { 
				display: '材料名称(E)', name: 'inv_id', textField: 'inv_name',align: 'left', width:240,
				editor : {
					type : 'select',
	         		valueField : 'inv_id',
	         		textField : 'inv_name',
	         		selectBoxWidth : '80%',
	         		selectBoxHeight : 240,
	         		grid : {
	         			columns : [{
	         				display : '交易编码', name : 'bid_code', width : 100, align : 'left'
	         			},  {
	         				display : '材料编码', name : 'inv_code', width : 100, align : 'left'
	         			}, {
	         				display : '材料名称', name : 'inv_name', width : 240, align : 'left'
	         			}, {
	         				display : '有效日期', name : 'inva_date', width : 90, align : 'left'
	         			}, {
							display : '别名', name : 'alias', width : 120, align : 'left'
						}, {
	         				display : '物资类别', name : 'mat_type_name', width : 120, align : 'left'
	         			}, { 
	         				display : '规格型号', name : 'inv_model', width : 180, align : 'left'
	         			}, { 
							display : '计量单位', name : 'unit_name', width : 70, align : 'left'
						}, {
							display : '包装规格', name : 'inv_structure', width : 70, align : 'left'
						}, {
	       					display: '单价', name: 'price', align: 'right', width:90,
	       					render : function(rowdata, rowindex, value) {
	       						return value == null ? "" : formatNumber(value, '${p04006 }', 1);
	       					}
	       				}, { 
	         				display : '库存', name : 'cur_amount', width : 80, align : 'right'
	         			}, { 
	         				display : '即时库存', name : 'imme_amount', width : 80, align : 'right'
	         			}, {
	         				display : '生产厂商', name : 'fac_name', width : 100, align : 'left'
	         			}, { 
	        	         	display: '批号', name: 'batch_no', align: 'left', width: 140, align : 'left'
	       	         	}, { 
	        	         	display: '条形码', name: 'bar_code', align: 'left', width: 100, align : 'left'
	       	         	}, {
	         				display : '是否收费', name : 'is_charge', width : 140, align : 'left',
	         				render : function(rowdata, rowindex, value) {
	         					return value == 1 ? '是' : '否';
	         				}
	         			}, {
	         				display : '货位', name : 'location_new_name', width : 100, align : 'left'
	         			} /* , {
	         				display : '注册证号', name : 'cert_code', width : 100, align : 'left',hide:true
	         			} */ ],
	         			switchPageSizeApplyComboBox : false,
	         			onSelectRow: function (data) {
							var e = window.event;
							if (e && e.which == 1) {
								f_onSelectRow_detail(data);
							}
						},
						gid: 'invGrid', 
	         			url : f_get_url,
	         			//pageSize : 50,
	         			usePager : false,
	         			delayLoad:false,
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
	         		delayLoad : true, keySupport : true, autocomplete : true,// rownumbers : true,
	         		onSuccess : function() {
	         			this.parent("tr").next(".l-grid-row").find("td:first").focus();
	         		},
	         		ontextBoxKeyEnter: function (data) {
						f_onSelectRow_detail(data.rowdata);
					}
	         	}
	         }, { 
	         	display: '规格型号', name: 'inv_model', align: 'left', width: 180
	         }, { 
	         	display: '计量单位', name: 'unit_name', align: 'left', width: 60
	         }, { 
					display: '单价', name: 'price', align: 'right', width: 90,
					render : function(rowdata, rowindex, value) {
						return value == null ? "" : formatNumber(value, '${p04006 }', 1);
					}
			}, { 
	         	display: '数量(E)', name: 'amount', align: 'right', width: 90, editor : {type : 'float'} ,
				totalSummary: {
					align : 'right',
                    render: function (suminf, column, cell) {
                        return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, 2, 1)+ '</div>';
                    }
                } 
	         }, { 
				display: '金额', name: 'amount_money', align: 'right', width: 90,
				render : function(rowdata, rowindex, value) {
					rowdata.amount_money = value == null ? "" : formatNumber(value, '${p04005 }', 0);
					return value == null ? "" : formatNumber(value, '${p04005 }', 1);
				},editor: {
					type: 'numberbox',
					precision: '${p04005 }'
				},
				totalSummary: {
					align : 'right',
                    render: function (suminf, column, cell) {
                        return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, '${p04005 }', 1)+ '</div>';
                    }
                }
			/* },  { 
				display: '注册证号', name: 'cert_code', align: 'left', width: 180 */
			},{ 
				display: '生产厂商', name: 'fac_name', align: 'left', width: 120
			}, { 
	         	display: '批号', name: 'batch_no', align: 'left', width: 140
	         }, { 
	         	display: '当前库存', name: 'cur_amount', align: 'right', width: 80
	         }, { 
	         	display: '即时库存', name: 'imme_amount', align: 'right', width: 80
	         },{ 
				display: '有效日期', name: 'inva_date', align: 'left', width: 90
			}, { 
				display: '灭菌日期', name: 'disinfect_date', align: 'left', type: 'date', format: 'yyyy-MM-dd', width: 90
			}, { 
				display: '条形码', name: 'bar_code', align: 'left', width: 120
			}, { 
				display: '批发价格', name: 'sale_price', align: 'right', width: 90,
				/*editor : {
					type : 'numberbox',
					precision : '${p04006 }'
        		},*/
				render : function(rowdata, rowindex, value) {
					rowdata.sale_price = value == null ? "" : formatNumber(value, '${p04006 }', 0);
					return value == null ? "" : formatNumber(value, '${p04006 }', 1);
				}
			}, { 
				display: '批发金额', name: 'sale_money', align: 'right', width: 90,
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
				display: '零售价格', name: 'sell_price', align: 'right', width: 90,
				render : function(rowdata, rowindex, value) {
					rowdata.sell_price = value == null ? "" : formatNumber(value, '${p04072 }', 0);
					return value == null ? "" : formatNumber(value, '${p04072 }', 1);
				}
			}, { 
				display: '零售金额', name: 'sell_money', align: 'right', width: 90,
				render : function(rowdata, rowindex, value) {
					rowdata.sell_money = value == null ? "" : formatNumber(value, '${p04073 }', 0);
					return value == null ? "" : formatNumber(value, '${p04073 }', 1);
				},
				totalSummary: {
					align : 'right',
                    render: function (suminf, column, cell) {
                        return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, '${p04073 }', 1)+ '</div>';
                    }
                }
			}, { 
				display: '货位名称', name: 'location_name', align: 'left', width: 120,hide:true
			},  { 
				display: '货位', name: 'location_new_name', align: 'left', width: 120
			}, { 
				display: '备注(E)', name: 'note', align: 'left', width: 180,
				editor : {
					type : 'text'
				}
			}, { 
				display: '批次数量合计', name: 'sum_amount', width:140, hide: true 
			}, { 
				display: '批次明细', name: 'inv_detail_data', width:140, hide: true 
			} ],
			usePager : false,width : '100%',height : '100%',enabledEdit : true,fixedCellHeight:true,heightDiff:-20,
			onBeforeEdit : f_onBeforeEdit, onBeforeSubmitEdit : f_onBeforeSubmitEdit, onAfterEdit : f_onAfterEdit,
			checkbox: true, rownumbers:true, frozen:false,//这个属性有detail明细的时候必须为false,否则明细显示不出来
			detail: { onShowDetail: showBatchSn, reload: true, single: true},//材料批次明细
			toolbar: { items: [
				{ text: '保存', id:'save', click: saveMatOutMain,icon:'save' },
				{ line:true },
				{ text: '删除', id:'delete', click: deleteRow,icon:'delete' },
				{ line:true },
				{ text: '定向出库', id:'dirImp', click: matOutDirImport,icon:'up' },
				{ line:true },
				{ text: '配套导入', id:'matchImp', click: matOutMatchedImport,icon:'up' },
				{ line:true },
				{ text: '历史使用导入', id:'hisImp', click: matOutHistoryImport,icon:'up' },
				{ line:true },
				{ text: '选择材料', id:'add', click: choice_inv,icon:'add' },
				{ line:true },
				{ text: '复制材料（<u>M</u>）', id:'copy', click: copy, icon:'copy' },
				{ line:true },
				{ text: '关闭（<u>L</u>）', id:'close', click: this_close, icon:'close' }
				/* { text: '选择材料', id:'add', click: choice_invOld,icon:'add' },
				{ line:true } */
				
			]}
		});

        gridManager = $("#maingrid").ligerGetGridManager();

	
    }
	
	//获取材料名称编辑时弹窗数据的获取路径--拼接上已有材料信息,不再显示
	function f_get_url(){
		var params="";
		/* $(grid.rows).each(function(i,obj){
			if(obj.inv_id){
				if(i==0){
					params="&invMsg=";
				}
				if(i!=0){
					params+="@";
				}
				
				params+=obj.inv_id+","+obj.batch_no+","+obj.bar_code+","+parseFloat(obj.price);
			}
		}); */
		//alert(params);
		return '../../../queryMatStockInvListOut.do?isCheck=false&is_com=0&store_id=' 
			+ liger.get("store_id").getValue().split(",")[0] + '&bus_type_code=' 
				+ liger.get("bus_type_code").getValue()+params;
	}
	
	//复制材料
    function copy () {
		var data_copys = grid.getCheckedRows();
		if (data_copys.length == 0){
			$.ligerDialog.error('请选择行');
			return;
		}else{
			for(var i = 0, length = data_copys.length; i<length; i++){         // 把数组的每个data都复制一遍，即深复制
				data_copys[i] = $.extend({},data_copys[i]) ;
				data_copys[i].amount = '1';
				data_copys[i].amount_money = data_copys[i].price;
			}
			var girdData = grid.getData();
			grid.addRows(data_copys, girdData, -1);
		}
	}
	
	function btn_saveColumn(){
		
			   var path = window.location.pathname+"/maingrid";
			   var url = '../../../../sys/addBatchSysTableStyle.do?isCheck=false';
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
	
	//选择材料
	function choice_inv(){
		
		if(liger.get("store_id").getValue() == null){
			$.ligerDialog.warn('请选择仓库');
			return ;
		}
		
		var store_id = liger.get("store_id").getValue();
		var store_text = liger.get("store_id").getText();
		
		$.ligerDialog.open({url: "matOutMainChoiceInvPage.do?isCheck=false&store_id="+store_id+"&store_text="+store_text,
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
	
	function choice_invOld(){
		//获得明细数据为几行
		var rows = 0;
		var data = gridManager.getData();
		$.each(data, function (i, v) {
			if (v.inv_id) {
				rows = rows + 1;
			}
		});
		
		if(liger.get("store_id").getValue() == null){
			$.ligerDialog.warn('请选择仓库');
			return ;
		}
		
		var store_id = liger.get("store_id").getValue().split(",")[0];
		var store_no = liger.get("store_id").getValue().split(",")[1];
		var store_text = liger.get("store_id").getText();
		
		var paras = store_id + '@'+ '' +'@'+store_text+'@'+rows;
		
		$.ligerDialog.open({url: "matOutMainFifoPage.do?isCheck=false&paras="+paras,
				height: 500,width: 900, title:'选择材料',modal:true,showToggle:false,showMax:true,
				showMin: false,isResize:true,
				buttons: [ 
				    { text: '确定',onclick: function (item, dialog) { 
							dialog.frame.checkMatOutFifo(); 
						},cls:'l-dialog-btn-highlight' }, 
					{ text: '取消', onclick: function (item, dialog) { 
						dialog.close(); 
					}} 
				] 
		});
	}
	
	var rowindex_id = "";
	var column_name = "";
	function f_onBeforeEdit(e) {
		rowindex_id = e.rowindex;
		column_name = e.column.name;		
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
					fac_name : data.fac_name,
					bid_code : data.bid_code,
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
					location_new_name : data.location_new_name == null ? "" : data.location_new_name,
					cur_amount : data.cur_amount == null ? "" : data.cur_amount/* ,
					cert_code : data.cert_code == null ? "" : data.cert_code */
				});
				
				setTimeout(function (){
					grid.endEditToNext();
				},300)
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
    	}/* else if (e.column.name == "amount_money") {
			//根据金额反算的数量要保留6位小数
    		if (e.record.price != undefined && e.record.price != "") {
    			var price = e.record.price;
				grid.updateCell('amount', Number(price ? e.value / price : 0).toFixed(6), e.rowindex);
			}
    	} */
    	grid.updateTotalSummary();
    }
	// 编辑单元格提交编辑状态之前作判断限制
	function f_onBeforeSubmitEdit(e) {
		/*
		if (e.column.name == "inv_id" && e.value == ""){
			//$.ligerDialog.warn('请选择材料！');
			return false;
		}
		if (e.column.name == "amount" && e.value == 0){
			//$.ligerDialog.warn('数量不能为0！');
			return false;
		}
		*/
		return true;
	} 
    
    //删除
    function deleteRow(){ 
    	gridManager.deleteSelectedRow();
    }
    
	//批量添加明细数据
    function add_Rows(data){
    	//delete_allRows();
    	grid.addRows(data);
    }
	
  //删除行集合
	function deleteRange(data){
		grid.deleteRange(data);
	}
    //清空表格
    function delete_allRows(){
		grid.deleteAllRows();
	}
    
    var gridRowData;
    function showBatchSn(row, detailPanel,callback){
    	gridRowData = row;
    	batchSn = document.createElement('div');
        $(detailPanel).append(batchSn);
		//detailGrid =$(detailPanel).css('margin',10).ligerGrid({
		detailGrid =$(batchSn).css({'margin-top':10, 'margin-left':60}).ligerGrid({
    		columns: [{ 
    			display: '材料编码', name: 'inv_code',width:100,
				totalSummary: {
					align : 'right',
                    render: function (suminf, column, cell) {
                        return '<div>合计：</div>';
                    }
                }
    		}, { 
    			display: '材料名称(E)', name: 'inv_id', textField: 'inv_name',width:240,
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
	        	         	display: '批次', name: 'batch_sn', align: 'left', width: 60, align : 'left'
	       	         	}, { 
	       					display: '单价', name: 'price', align: 'right', width: 90,
	       					render : function(rowdata, rowindex, value) {
	       						return value == null ? "" : formatNumber(value, '${p04006 }', 1);
	       					}
	       				}, { 
	         				display : '库存', name : 'cur_amount', width : 80, align : 'right'
	         			}, { 
	         				display : '即时库存', name : 'imme_amount', width : 80, align : 'right'
	         			} ],
	         			switchPageSizeApplyComboBox : false,
	         			onSelectRow : f_detail_onSelectRow_detail,
	         			gid: 'detailInvGrid', 
	         			url : '../../../queryMatStockInvDetailList.do?isCheck=false&is_com=0&store_id=' 
	         					+ liger.get("store_id").getValue().split(",")[0]  + '&bus_type_code=' 
	         					+ liger.get("bus_type_code").getValue() + '&inv_id=' + row.inv_id
	         					+ '&batch_no=' + row.batch_no + '&bar_code=' + row.bar_code + '&price=' + row.price
	         					+ '&amount=' + row.amount+ '&inva_date=' + row.inva_date + '&disinfect_date=' + row.disinfect_date,
	         			pageSize : 500
	         		},
	         		delayLoad : false, keySupport : true, autocomplete : true,// rownumbers : true,
	         		onSuccess : function() {
	         			this.parent("tr").next(".l-grid-row").find("td:first").focus();
	         		}
	         	}
    		}, { 
    			display: '批次', name: 'batch_sn', width: 60, align : 'left' 
    		}, { 
     			display : '库存', name : 'cur_amount', width : 80, align : 'right'
     		}, { 
     			display : '即时库存', name : 'imme_amount', width : 80, align : 'right', 
     		}, { 
    			display: '数量(E)', name: 'amount', width: 80, align : 'right', editor : {type : 'float'},
				totalSummary: {
					align : 'right',
                    render: function (suminf, column, cell) {
                        return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, 2, 1)+ '</div>';
                    }
                }
    		}, { 
    			display: '单价', name: 'price', width: 90, align : 'right',
    			render : function(rowdata, rowindex, value) {
    				rowdata.price = value == null ? "" : formatNumber(value, '${p04006 }', 0);
    				return value == null ? "" : formatNumber(value, '${p04006 }', 1);
    			}
    		}, { 
    			display: '金额', name: 'amount_money', align: 'right', width: 90,
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
    			display: '批发单价', name: 'sale_price', width: 90, align : 'right',
    			render : function(rowdata, rowindex, value) {
    				rowdata.sale_price = value == null ? "" : formatNumber(value, '${p04006 }', 0);
    				return value == null ? "" : formatNumber(value, '${p04006 }', 1);
    			}
    		}, { 
    			display: '批发金额', name: 'sale_money', align: 'right', width: 90,
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
    			display: '零售单价', name: 'sell_price', width: 90, align : 'right',
    			render : function(rowdata, rowindex, value) {
    				rowdata.sell_price = value == null ? "" : formatNumber(value, '${p04072 }', 0);
    				return value == null ? "" : formatNumber(value, '${p04072 }', 1);
    			} 
    		}, { 
    			display: '零售金额', name: 'sell_money', align: 'right', width: 90,
    			render : function(rowdata, rowindex, value) {
    				rowdata.sell_money = value == null ? "" : formatNumber(value, '${p04073 }', 0);
    				return value == null ? "" : formatNumber(value, '${p04073 }', 1);
    			},
				totalSummary: {
					align : 'right',
                    render: function (suminf, column, cell) {
                        return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, '${p04073 }', 1)+ '</div>';
                    }
                }
    		} ], 
    		dataAction : 'server',dataType : 'server',usePager : true, checkbox: true,
    		rownumbers: true, enabledEdit : true, fixedCellHeight: true, frozen: false, gid: 'detailGrid', 
    		onBeforeEdit : f_detail_onBeforeEdit, onBeforeSubmitEdit : f_detail_onBeforeSubmitEdit, onAfterEdit : f_detail_onAfterEdit,
    		width: '65%',height: '80%', data : f_getInvDetailData(row),
    		toolbar: { items: [
    			{ text: '删除', id:'delete', click: deleteDetailRow,icon:'delete' },
    			{ line:true },
    		]} 
		});

		//默认添加行
		detailGridAddRow();
    }
    
    //添加空行
    function detailGridAddRow(){
    	setTimeout(function () {detailGrid.addRow();}, 500);
    }
    
    function f_getInvDetailData(rowdata){
    	var data = { Rows: [] };
    	//alert("是否存在数据："+JSON.stringify(rowdata.inv_detail_data));
		if(validateStr(rowdata.inv_id) && validateStr(rowdata.amount) && rowdata.amount != 0){
			//明细中有批次信息并且主数量和明细数量相等
			if(validateStr(rowdata.inv_detail_data) && validateStr(rowdata.sum_amount) && rowdata.amount == rowdata.sum_amount){
				var rows = jsonRowsToObject(rowdata.inv_detail_data, "out_detail_id"); 
				for(var i = 0; i < rows.length; i++){
					data.Rows.push(rows[i]);
				}
    		}else{
    			//明细中没有批次信息，需要根据先进先出从后台取出
        		var invPara = {
    				store_id : liger.get("store_id").getValue().split(",")[0], 
					bus_type_code : liger.get("bus_type_code").getValue(),
            		inv_id : rowdata.inv_id,
            		batch_no : rowdata.batch_no,
            		bar_code : rowdata.bar_code,
            		price : rowdata.price,
            		amount : rowdata.amount,
            		inva_date : rowdata.inva_date,
            		disinfect_date : rowdata.disinfect_date
            	}
        		ajaxJsonObjectByUrl("../../../queryMatInvByFifo.do?isCheck=false",invPara,function(responseData){
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
		//alert(dataDetail.length);
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
		/* if (e.column.name == "inv_id" && e.value == ""){
			//$.ligerDialog.warn('请选择材料！');
			return false;
		}
		if (e.column.name == "amount" && e.value == 0){
			//$.ligerDialog.warn('数量不能为0！');
			return false;
		} */
		return true;
	}
    
    //删除明细
    function deleteDetailRow(){ 
    	
    	detailGrid.deleteSelectedRow();
    	changeDetailAmount();
    }
    
    
 	
	function matOutFifoImport(){//选择材料

		var store_id = liger.get("store_id").getValue();
		var store_text = liger.get("store_id").getText();
    	
    	if(!store_id){
    		
    		$.ligerDialog.warn('请选择仓库');
    		
    		return false;
    	}
		
    	//var dept_id = liger.get("dept_id").getValue()?liger.get("dept_id").getValue():'null';
    	
    	//var store_id = liger.get("store_id").getValue()?liger.get("store_id").getValue():'null';
		var out_id = "null";
		var paras = store_id+"@"+out_id+"@"+store_text;
		
		$.ligerDialog.open({url: "matOutMainFifoPage.do?isCheck=false&paras="+paras, height: 500,width: 900, title:'选择材料',modal:true,showToggle:false,showMax:true,showMin: false,isResize:true,buttons: [ { text: '选择', onclick: function (item, dialog) { dialog.frame.checkMatOutFifo(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    }
    
    function matOutMatchedImport(){//配套导入
    	
    	var dept_id = liger.get("dept_id").getValue();
    	var dept_text = liger.get("dept_id").getText();
    	if(!dept_id){
    		
    		$.ligerDialog.warn('请选择领用科室');
    		
    		return false;
    	}
    	
		var store_id = liger.get("store_id").getValue();
		var store_text = liger.get("store_id").getText();
    	if(!store_id){
    		
    		$.ligerDialog.warn('请选择仓库');
    		
    		return false;
    	}
		
    	//var dept_id = liger.get("dept_id").getValue()?liger.get("dept_id").getValue():'null';
    	
    	//var store_id = liger.get("store_id").getValue()?liger.get("store_id").getValue():'null';
		
		var paras = dept_id+"@"+dept_text+"@"+store_id+"@"+store_text;
		
		$.ligerDialog.open({
			url: "matOutMainMatchedPage.do?isCheck=false&paras="+paras, 
			height: 500,width: 900, title:'配套导入',
			modal:true,showToggle:false,showMax:true,showMin: false,isResize:true
			//,buttons: [ { text: '选择', onclick: function (item, dialog) { dialog.frame.checkMatOutFifo(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] 
		});

    }
    function matOutDirImport(){//定向导入
		
    	 var dept_id = liger.get("dept_id").getValue();
    	/* 
    	if(!dept_id){
    		
    		$.ligerDialog.warn('请选择领用科室');
    		
    		return false;
    	} */
    	
		var store_id = liger.get("store_id").getValue();
    	
    	if(!store_id){
    		
    		$.ligerDialog.warn('请选择仓库');
    		
    		return false;
    	}
		 
		var paras = dept_id+"@"+store_id;
		
		$.ligerDialog.open({
			title:'定向导入',
			url: "matOutMainDirPage.do?isCheck=false&paras="+paras, 
			height: $(window).height()-100,
			width: $(window).width()-200,
			//height: 500,
			//width: 900, 
			modal:true,showToggle:false,showMax: true,showMin: false,isResize:true,
			/* buttons: [ 
			           { text: '选择', onclick: function (item, dialog) { dialog.frame.checkMatOutFifo(); },cls:'l-dialog-btn-highlight' }, 
			           { text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
			]  */
		});
	}

    function matOutHistoryImport(){//历史导入
		
		var dept_id = liger.get("dept_id").getValue();
    	if(!dept_id){
    		$.ligerDialog.warn('请选择领用科室');
    		return false;
    	}
    	
		var store_id = liger.get("store_id").getValue();
    	if(!store_id){
    		$.ligerDialog.warn('请选择仓库');
    		return false;
    	}

    	var para = "store_id=" + liger.get("store_id").getValue() +
    		"&store_text=" + liger.get("store_id").getText() +
    		"&dept_id=" + liger.get("dept_id").getValue() +
    		"&dept_text=" + liger.get("dept_id").getText();;
		
		$.ligerDialog.open({
			title:'历史导入',
			height: $(window).height()-100,
			width: $(window).width()-200,
			url: "matOutMainHistoryPage.do?isCheck=false&"+para, 
			modal:true,showToggle:false,showMax: true,showMin: false,isResize:true,
			buttons: [ 
				{ text: '选择', onclick: function (item, dialog) { dialog.frame.checkMatOutFifo(); },cls:'l-dialog-btn-highlight' }, 
				{ text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
			] 
		});
	}
 
	function is_addRow(){//默认新增一行
		setTimeout(function () {
			grid.addRow();
		}, 100);
	}
	
	function validateStr(str){
		if(str == null || str == 'undefined' || str == ''){
			return false;
		}
		return true;
	}

	//关闭当前弹出框
	function this_close(){
		frameElement.dialog.close();
	}
	
	var begin_bar;
	//按条码自动填充材料信息
	function paddingInv_beginBar(){
		if(event.keyCode==13){  
			begin_bar = $("#begin_bar").val();
			var store_id = liger.get("store_id").getValue().split(",")[0]; 
			if(store_id && begin_bar){
				var para={
						flag: 1, //出单独材料
						store_id: store_id, 
						bar_code: begin_bar 
		    	}
				ajaxJsonObjectByUrl("../../../queryMatOutInvListByBar.do?isCheck=false",para,function (responseData){
					if(responseData.state == "true"){
				    	grid.addRows(responseData.invData.Rows);//添加数据
				    	$("#begin_bar").val("");
					}
				});
			}
		}
	}
	
	//按条码自动填充材料信息
	function paddingInv_endBar(){
		if(event.keyCode==13){  
			var end_bar = $("#end_bar").val();
			var store_id = liger.get("store_id").getValue().split(",")[0]; 
			if(store_id && begin_bar && end_bar){
				var para={
						flag: 2, 
						store_id: store_id, 
						bar_code: begin_bar, 
						bar_code_end: end_bar 
		    	}
				ajaxJsonObjectByUrl("../../../queryMatOutInvListByBar.do?isCheck=false",para,function (responseData){
					if(responseData.state == "true"){
				    	grid.addRows(responseData.invData.Rows);//添加数据
				    	$("#begin_bar").val("");
				    	$("#end_bar").val("");
					}
				});
			}
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
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span style="color:red">*</span>出库单号：</td>
			            <td align="left" class="l-table-edit-td"><input name="out_no" type="text" id="out_no" ltype="text" value="自动生成"/></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span style="color:red">*</span>业务类型：</td>
			            <td align="left" class="l-table-edit-td"><input name="bus_type_code" type="text" id="bus_type_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span style="color:red">*</span>仓库：</td>
			            <td align="left" class="l-table-edit-td"><input name="store_id" type="text" id="store_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
			            <td align="left"></td>
			        </tr> 
			        <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span style="color:red">*</span>制单日期：</td>
			            <td align="left" class="l-table-edit-td"><input name="out_date" type="text" id="out_date" ltype="text" validate="{required:true,maxlength:20}"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span style="color:red">*</span>领用科室：</td>
			            <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><!-- <span style="color:red">*</span> -->领料人：</td>
			            <td align="left" class="l-table-edit-td"><input name="dept_emp" type="text" id="dept_emp" ltype="text" validate="{maxlength:20}" /></td>
			            <td align="left"></td>
			        </tr> 
			        <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">
			            	物资用途：
			            </td>
			            <td align="left" class="l-table-edit-td">
			            	<input name="use_code" type="text" id="use_code" ltype="text" validate="{required:false}" />
			            </td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">
			            	项目：
			            </td>
			            <td align="left" class="l-table-edit-td">
			            	<input name="proj_code" type="text" id="proj_code" ltype="text" validate="{required:false}" />
			            </td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td" >申领日期： </td>
	                    <td align="left" class="l-table-edit-td">
	            			<input class="Wdate" name="app_date" id="app_date" type="text"  onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd HH:mm:ss'})" validate="{required:false}" />
	           		    </td>  
			        </tr> 
			        <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">摘要：</td>
			            <td align="left" class="l-table-edit-td" colspan="3">
			            	<textarea class="l-textarea" name="brief" id="brief" rows="3" style="width: 380px;"></textarea>
			            </td> 
						<td align="center" class="l-table-edit-td" colspan="4" ><!-- style="display: none;"> -->
							<fieldset style="border:1px solid #00CC00; width: 450px;">
								<legend style="color: #000">扫码出库(注：录入条码敲回车)</legend>
								<table cellpadding="0" cellspacing="0" class="l-table-edit">
									<tr>
										<td align="right" class="l-table-edit-td">
											条形码：
										</td>
										<td align="left" class="l-table-edit-td">
											<input name="begin_bar" type="text" id="begin_bar" ltype="text" validate="{required:false}" onkeypress="paddingInv_beginBar()"/>
										</td>
										<td align="right" class="l-table-edit-td">
											至：
										</td>
										<td align="left" class="l-table-edit-td">
											<input name="end_bar" type="text" id="end_bar" ltype="text" validate="{required:false}" onkeypress="paddingInv_endBar()"/>
										</td>
									</tr>
								</table>
							</fieldset>
						</td>
			        </tr> 
			    </table>
			</form>
		</div>
		<div position="center" >
			<div id="maingrid"></div>
		</div>
		
	</div>

    </body>
</html>
