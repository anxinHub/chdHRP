<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"  xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
    <script src="<%=path%>/lib/stringbuffer.js"></script>
    <script src="<%=path%>/lib/hrp/mat/mat.js" type="text/javascript"></script>
<script type="text/javascript">
     var grid; 
     var detailGrid; 
     var gridManager = null;
     var is_com = "${matTranMain.is_com}";
     
     $(function (){
		$("#layout1").ligerLayout({
			topHeight:120,
        	centerBottomHeight:80
		});
        loadDict();
        //loadForm();
        loadHead(null);	
        loadHotkeys();
		
		changeTranType();
		//联动事件
		$("#tran_type").bind("change", function(){changeTranType()});
		$("#out_store_id").bind("change",function(){
	    	loadHead();
	    	grid.reRender();
		})
		//联动事件
		$("#tran_type").bind("change", function(){changeTranType()});
		//联动事件
		$("#out_hos_id").bind("change",function(){
			var para = {
				hos_id : liger.get("out_hos_id").getValue()
			}
			liger.get("out_store_id").set("parms", para);
			liger.get("out_store_id").reload();
			if(liger.get("out_store_id").data[0]){
				liger.get("out_store_id").selectValue(liger.get("out_store_id").data[0].id);
			}else{
				liger.get("out_store_id").setValue("");
				liger.get("out_store_id").setText("");
			}
		})
		$("#out_store_id").bind("change",function(){
	    	loadHead();
	    	grid.reRender();
		})
		$("#in_hos_id").bind("change",function(){
			var para = {
				hos_id : liger.get("in_hos_id").getValue()
			}
			liger.get("in_store_id").set("parms", para);
			liger.get("in_store_id").reload();
			if(liger.get("in_store_id").data[0]){
				liger.get("in_store_id").selectValue(liger.get("in_store_id").data[0].id);
			}else{
				liger.get("in_store_id").setValue("");
				liger.get("in_store_id").setText("");
			}
		})
     });  
     function validateGrid(){
    	 if(liger.get("tran_type").getValue() == 1){
    
    		 if(liger.get("out_store_id").getValue().split(",")[0]+""+liger.get("out_store_id").getValue().split(",")[1] == liger.get("in_store_id").getValue().split(",")[0]+""+liger.get("in_store_id").getValue().split(",")[1]){
    			 $.ligerDialog.warn('院内调拨调出、调入仓库不能一致！');
    			 return false;
    		 }
    	 }else if(liger.get("tran_type").getValue() == 2){
    		 if(liger.get("out_hos_id").getValue() == liger.get("in_hos_id").getValue()){
    			 $.ligerDialog.warn('院外调拨调出单位、调入单位不能一致！');
    			 return false;
    		 }
        	 if(!liger.get("bus_type").getValue()){
        		 $.ligerDialog.warn('院外调拨业务类型不能为空！');
    			 return false;
        	 }
    	 }else{
    		 $.ligerDialog.warn('调拨类型不能为空！');
			 return false;
    	 }
    	 if(!liger.get("tran_date").getValue()){
    		 $.ligerDialog.warn('编制日期不能为空！');
			 return false;
    	 }
    	 if(!liger.get("out_hos_id").getValue()){
    		 $.ligerDialog.warn('调出单位不能为空！');
			 return false;
    	 }
    	 if(!liger.get("in_hos_id").getValue()){
    		 $.ligerDialog.warn('调入单位不能为空！');
			 return false;
    	 }
    	 if(!liger.get("out_store_id").getValue()){
    		 $.ligerDialog.warn('调出仓库不能为空！');
			 return false;
    	 }
    	 if(!liger.get("in_store_id").getValue()){
    		 $.ligerDialog.warn('调入仓库不能为空！');
			 return false;
    	 }
    	 
		var tran_detail_data = gridManager.rows;
		var targetMap = new HashMap();
		var validate_detail_buf = new StringBuffer();
		var rows = 0;
		var store_inv = "";  //用于判断是否 材料与调入仓库是否存在对应关系 
		var inv_ids = "";
		var inva_date="";
  	    var disinfect_date="";
  	    
		if(tran_detail_data.length > 0){
  			$.each(tran_detail_data, function(d_index, d_content){ 
	  	      	if(d_content.inv_id){
  					store_inv += d_content.inv_id + ",";
  	      			var value="第"+(d_index+1)+"行";
	  	      		if(!d_content.amount){
						validate_detail_buf.append(value+"数量为零或空 请输入;\n");
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
						validate_detail_buf.append(targetMap.get(key)+"与"+value+"材料编码、生产批号、条形码、单价、有效日期、灭菌日期不能重复"+ "\n\r"); 
					}
	      		
	  	      		/* var key=d_content.inv_id +"|"+d_content.batch_no+"|"+d_content.bar_code;
	  				if(!targetMap.get(key)){
	  					targetMap.put(key ,value);
	  				}else{
	  					validate_detail_buf.append(targetMap.get(key)+"与"+value+"材料编码、生产批号、条形码不能重复;" + "\n\r");
	  				} */
	  				rows += 1;
	  	      	}
 			}); 
  		}
		//仓库材料对应关系
		var para = {
			inv_ids: store_inv.substring(0, store_inv.length-1), 
			store_id: liger.get("in_store_id").getValue().split(",")[0]
		}
		var is_flag = ajaxJsonObjectByUrl("../../../existsMatStoreIncludeInv.do?isCheck=false", para, function (responseData){
			if(responseData.state=="false"){
				validate_detail_buf.append("材料"+responseData.inv_ids+"不在该仓库中！<br>");
			}
		}, false);
		if(validate_detail_buf.toString()  != ""){
 			$.ligerDialog.warn(validate_detail_buf.toString(),'','',{width:450});
 			return false;
 		}

 		if(rows == 0){
 			$.ligerDialog.warn('请选择材料');
 			return false;
 		}
 		
 		return true;
     }
     
     function  save(){
		grid.endEdit();
		
		if(validateGrid()){
			$("#save").ligerButton({disabled : true});
			var formPara={
	        		bus_type : liger.get("bus_type").getValue(),
	 				tran_type : liger.get("tran_type").getValue(),
	 				tran_method : liger.get("tran_method").getValue(),
	 				tran_date : $("#tran_date").val(),
	 				out_hos_id : liger.get("out_hos_id").getValue(),
	 				in_hos_id : liger.get("in_hos_id").getValue(),
	 				out_store_id : liger.get("out_store_id").getValue().split(",")[0],
	 				out_store_no : liger.get("out_store_id").getValue().split(",")[1],
	 				in_store_id : liger.get("in_store_id").getValue().split(",")[0],
	 				in_store_no : liger.get("in_store_id").getValue().split(",")[1],
	 				brief : $("#brief").val(),
	 				detailData : JSON.stringify(gridManager.rows)
	         };
	        
	        var saveUrl;
	        var pageUrl;
	        var pageTitle;
	        if(is_com == "0"){
	        	pageTitle = "调拨单修改";
	        	saveUrl = "../applycheck/addMatTranMainByApp.do?isCheck=false";
	        	pageUrl = "hrp/mat/storage/tran/matTranMainUpdatePage.do?isCheck=false";
	        }else{
	        	pageTitle = "代销调拨单修改";
	        	saveUrl = "../applycheck/addMatAffiTranMainByApp.do?isCheck=false";
	        	pageUrl = "hrp/mat/affi/tran/matAffiTranMainUpdatePage.do?isCheck=false";
	        }

	        ajaxJsonObjectByUrl(saveUrl, formPara, function(responseData){
	            
	            if(responseData.state=="true"){
	            	parentFrameUse().query();
	            	var voStr = responseData.update_para.split(",");
	        		var paras = 
	        			"group_id=" + voStr[0].toString() + "&" 
	        			+ "hos_id=" + voStr[1].toString() + "&" 
	        			+ "copy_code=" + voStr[2].toString() + "&" 
	        			+ "tran_id=" + voStr[3].toString() + "&" 
	        			+ "store_id=" + voStr[4].toString();
	            	parent.$.ligerDialog.open({
						title: pageTitle,
						height: $(window).height(),
						width: $(window).width(),
						url: pageUrl + "&" + paras.toString(),
						modal: true, showToggle: false, showMax: true, showMin: false, isResize: true, 
					});
	            	this_close();
	            }
	        });
		}
    	 

        
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
    	$("#tran_no").ligerTextBox({width:180,disabled: true }); 
		autoCompleteByData("#tran_type", matTranMain_tranType.Rows, "id", "text",true, true,'',false, "${matTranMain.tran_type}", '180');
		
		autoCompleteByData("#bus_type", matTranMain_busType.Rows, "id", "text",true, true,'', false, "${matTranMain.bus_type}", '180');
		
		//autoCompleteByData("#tran_method", matTranMain_tranMethod.Rows, "id", "text",true, true,'',true,false,'180');
		$("#tran_method").ligerComboBox({width:180,disabled:true,cancelable: false});
		liger.get("tran_method").setValue("${matTranMain.tran_method}");
        if("${matTranMain.tran_method}" == 1){
    		liger.get("tran_method").setText("同价调拨");
        }else{
    		liger.get("tran_method").setText("异价调拨");
        }
        
    	$("#out_hos_id").ligerComboBox({disabled: true}); 
        $("#out_store_id").ligerComboBox({disabled: true }); 
        
		//字典下拉框
        autocompleteAsync("#out_hos_id", "../../../queryMatHosInfo.do?isCheck=false", "id","text", true, true, "", false, false, '180');
		if("${matTranMain.out_hos_id}"){
			liger.get("out_hos_id").setValue('${matTranMain.out_hos_id}');
			liger.get("out_hos_id").setText('${matTranMain.out_hos_code} ${matTranMain.out_hos_name}');
    	}
		autocompleteAsync("#out_store_id", "../../../queryMatStore.do?isCheck=false", "id","text", true, true, {hos_id : "${matTranMain.out_hos_id}"}, false, false, '180');
		if("${matTranMain.out_store_id}"){
			liger.get("out_store_id").setValue('${matTranMain.out_store_id},${matTranMain.out_store_no}');
			liger.get("out_store_id").setText('${matTranMain.out_store_code} ${matTranMain.out_store_name}');
		}
		autocompleteAsync("#in_hos_id", "../../../queryMatHosInfo.do?isCheck=false", "id","text", true, true, "", false, false, '180');
		if("${matTranMain.in_hos_id}"){
			liger.get("in_hos_id").setValue('${matTranMain.in_hos_id}');
			liger.get("in_hos_id").setText('${matTranMain.in_hos_code} ${matTranMain.in_hos_name}');
		}
		autocompleteAsync("#in_store_id", "../../../queryMatStore.do?isCheck=false", "id","text", true, true, {hos_id : "${matTranMain.in_hos_id}"}, false, false, '180');
		if("${matTranMain.in_store_id}"){
			liger.get("in_store_id").setValue('${matTranMain.in_store_id},${matTranMain.in_store_no}');
			liger.get("in_store_id").setText('${matTranMain.in_store_code} ${matTranMain.in_store_name}');
		}

		$("#tran_date").ligerTextBox({width : 180});
		autodate("#tran_date");
    	$("#brief").ligerTextBox({width:480}); 
    	
		$("#save").ligerButton({click: save, width:90});
		$("#close").ligerButton({click: this_close, width:90});
	} 
	
    function changeTranType(){
		if(liger.get("tran_type").getValue() == 1){
			$("#bus_type_span").css("display", "none");
			$("#bus_type").attr("readonly", "readonly").ligerComboBox({width:180,disabled:true, cancelable: false});
	        liger.get("bus_type").setValue("");
			liger.get("bus_type").setText("");
			$("#out_hos_id").attr("readonly", "readonly").ligerComboBox({width:180,disabled:true, cancelable: false});
			$("#in_hos_id").attr("readonly", "readonly").ligerComboBox({width:180,disabled:true, cancelable: false});
			liger.get("in_hos_id").setValue(liger.get("out_hos_id").getValue());
			liger.get("in_hos_id").setText(liger.get("out_hos_id").getText());
		}else{
			$("#bus_type_span").css("display", "inline");
			$("#bus_type").removeAttr("readonly").prop("disabled", false).ligerComboBox({width:180,disabled:false, cancelable: true});
			liger.get("bus_type").selectValue(liger.get("bus_type").data[0].id);
			$("#out_hos_id").removeAttr("readonly").prop("disabled", false).ligerComboBox({width:180,disabled:false, cancelable: true});
			$("#in_hos_id").removeAttr("readonly").prop("disabled", false).ligerComboBox({width:180,disabled:false, cancelable: true});
		}
    }
    
    function loadHead(){
		var matTranDetail = ${matTranDetail};
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '材料编码', name: 'inv_code', align: 'left',width:100,
         				totalSummary: {
        					align : 'right',
                            render: function (suminf, column, cell) {
                                return '<div>合计：</div>';
                            }
                        }
					},
                     { display: '材料名称(E)', name : 'inv_id', textField : 'inv_name',align: 'left',width:240,
                    	 editor : {
								type : 'select',
								valueField: 'inv_id', 
								textField: 'inv_name',
								selectBoxWidth: '80%',
								selectBoxHeight: 370,
							    keySupport:true,
						      	autocomplete: true,
						      	highLight : true,
						      	grid :{
							    	columns : [ 
			                	            {display : '材料编码', name : 'inv_code', width : 100, align : 'left'}, 
			                	            {display : '材料名称', name : 'inv_name', width : 240, align : 'left'}, 
			                	            {display : '规格型号', name : 'inv_model', width : 120, align : 'left'},
			                	            {display : '计量单位', name : 'unit_name', width : 100, align : 'left'}, 
			                	            {display : '单价', name : 'price', width : 80, align : 'right'}, 
			                	            {display : '批号', name : 'batch_no', width : 100, align : 'left'}, 
			                	            {display : '条码', name : 'bar_code', width : 100, align : 'left'}, 
			                	            {display : '库存', name : 'cur_amount', width : 80, align : 'right'},
			                	            {display : '即时库存', name : 'imme_amount', width : 80, align : 'right'},
			                	            {display : '有效日期', name : 'inva_date', width : 100, align : 'left'}, 
			    	         				{display : '是否收费', name : 'is_charge', width : 50, align : 'left',
				    	         				render : function(rowdata, rowindex, value) {
				    	         					return value == 1 ? '是' : '否';
				    	         				}
			    	         				},
			    	         				{display : '物资类别', name : 'mat_type_name', width : 120, align : 'left'},
			                	            {display : '生产厂商', name : 'fac_name', width : 180, align : 'left'}, 
			                	            {display : '零售单价', name : 'sell_price', width : 80, align : 'right'}, 
			                	            {display : '货位编码', name : 'location_code', width : 100, align : 'left'}, 
			                	            {display : '货位名称', name : 'location_name', width : 140, align : 'left'}
			                	        ],
			        	         		switchPageSizeApplyComboBox : false,
			        	         		onSelectRow: function (data) {
			    							var e = window.event;
			    							if (e && e.which == 1) {
			    								f_onSelectRow_detail(data);
			    							}
			    						},
			    						url : (is_com == "0" ? '../../../queryMatStockInvList.do?isCheck=false&is_com=0' : '../../../queryMatAffiOutInvList.do?isCheck=false&is_com=1')
			        	         				+ '&store_id=' + liger.get("out_store_id").getValue().split(",")[0],
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
						      		onSuccess : function() {
						            	this.parent("tr").next(".l-grid-row").find("td:first").focus();
						            } ,
						            ontextBoxKeyEnter: function (data) {
										f_onSelectRow_detail(data.rowdata);
									}
                    	 }
						      	 
                     },
                     { display: '规格型号', name: 'inv_model', align: 'left',width:180},
                     { display: '计量单位', name: 'unit_name', align: 'left',width:80},
                     { display: '批号', name: 'batch_no', align: 'left',width:80},
                     { display: '当前库存', name: 'cur_amount', align: 'right',width:80},
                     { display: '数量(E)', name: 'amount', align: 'right',width:90,editor : {type : 'float'},
         				totalSummary: {
        					align : 'right',
                            render: function (suminf, column, cell) {
                                return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, 2, 1)+ '</div>';
                            }
                        }
                     },
                     { display: '单价', name: 'price', align: 'right',width:90,
                    	 render : function(rowdata, rowindex, value) {
         					return value == null ? "" : formatNumber(value, '${p04006 }', 1);
         				}
                     },
                     { display: '金额', name: 'amount_money', align: 'right',width:90,
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
                     },
                     { display: '有效期', name: 'inva_date', align: 'left',width:120},
                     { display: '灭菌日期', name: 'disinfect_date', align: 'left',width:120},
                     { display: '条形码', name: 'bar_code', align: 'left',width:120},
                     { display: '移出仓库货位', name: 'location_out_name', align: 'left',width:120},
                     { display: '移入仓库货位(E)', name: 'location_in_id', textField: 'location_in_name', align: 'left',width:120,
         				editor : {
         					type : 'select',
         					valueField : 'id',
         					textField : 'text',
         					url : '../../../queryMatLocationDict.do?isCheck=false',
         					keySupport : true,
         					autocomplete : true,
         				}
                     }, 
                     { display: '备注(E)',name : 'note',align: 'left',width:180,editor : {type : 'text'}}, 
                     { display: '批次数量合计', name: 'sum_amount',width:80, hide: true }, 
                     { display: '批次明细', name: 'inv_detail_data',width:80, hide: true } 
				],
				usePager : false,width : '100%',height : '100%',enabledEdit : true,fixedCellHeight:true,heightDiff:-20,
				onBeforeEdit : f_onBeforeEdit, onBeforeSubmitEdit : f_onBeforeSubmitEdit, onAfterEdit : f_onAfterEdit,
				selectRowButtonOnly:true,checkbox: true,rownumbers:true,isScroll:true, frozen:false,isAddRow:false,//这个属性有detail明细的时候必须为false,否则明细显示不出来
				detail: { onShowDetail: showBatchSn, reload: true, single: true},//材料批次明细
				data: matTranDetail, //数据来源
				toolbar: { 
					items: [
						{ text: '删除（<u>D</u>）', id:'delete', click: deleteRow, icon:'delete' },
						{ line:true },
						/* { text: '整单调拨（<u>I</u>）', id:'import', click: matTranBySingleImport, icon:'up' },
						{ line:true } */
					]
				}
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
	
    //键盘事件
	function loadHotkeys() {
		hotkeys('D', deleteRow);
		hotkeys('I', matTranBySingleImport);
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
					location_name : data.location_name == null ? "" : data.location_name
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
    		grid.updateCell('sum_amount', 0, e.record); 
    		grid.updateCell('inv_detail_data', "", e.record); 
    	}
    }
	// 编辑单元格提交编辑状态之前作判断限制
	function f_onBeforeSubmitEdit(e) {
		/*
		if (e.column.name == "inv_id" && e.value == ""){
			//$.ligerDialog.warn('请选择材料！');
			//grid.setCellEditing(e.record, e.column, true);
			return false;
		}
		if (e.column.name == "amount" && e.value == 0){
			//$.ligerDialog.warn('数量不能为0！');
			//grid.setCellEditing(e.record, e.column, true);
			return false;
		}
		*/
		return true;
	} 

	function deleteRow(){ 
		
		gridManager.deleteSelectedRow();
    }
    
	//批量添加明细数据
    function add_Rows(data){
    	delete_allRows();
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
    			display: '材料编码', name: 'inv_code',width:80,
				totalSummary: {
					align : 'right',
                    render: function (suminf, column, cell) {
                        return '<div>合计：</div>';
                    }
                }
    		}, { 
    			display: '材料名称(E)', name: 'inv_id', textField: 'inv_name',width:100,
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
	         			onSelectRow : f_detail_onSelectRow_detail,
	         			url : (is_com == "0" ? '../../../queryMatStockInvDetailList.do?isCheck=false&is_com=0' : '../../../queryMatAffiOutDetailInvList.do?isCheck=false&is_com=1')
	         					+ '&store_id=' + liger.get("out_store_id").getValue().split(",")[0] + '&inv_id=' + row.inv_id
	         					+ '&batch_no=' + row.batch_no + '&bar_code=' + row.bar_code + '&price=' + row.price
	         					+ '&inva_date=' + row.inva_date + '&disinfect_date=' + row.disinfect_date ,
	         			pageSize : 10
	         		},
	         		delayLoad : false, keySupport : true, autocomplete : true,// rownumbers : true,
	         		onSuccess : function() {
	         			this.parent("tr").next(".l-grid-row").find("td:first").focus();
	         		}
	         	}
    		}, { 
    			display: '批次', name: 'batch_sn', align : 'left' 
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
    			},
				totalSummary: {
					align : 'right',
                    render: function (suminf, column, cell) {
                        return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, '${p04005 }', 1)+ '</div>';
                    }
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
    			},
				totalSummary: {
					align : 'right',
                    render: function (suminf, column, cell) {
                        return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, '${p04005 }', 1)+ '</div>';
                    }
                }
    		}, { 
    			display: '零售单价', name: 'sell_price', width: 80, align : 'right',
    			render : function(rowdata, rowindex, value) {
    				rowdata.sell_price = value == null ? "" : formatNumber(value, '${p04072 }', 0);
    				return value == null ? "" : formatNumber(value, '${p04072 }', 1);
    			} 
    		}, { 
    			display: '零售金额', name: 'sell_money', align: 'right', width:80,
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
    		dataAction : 'server',dataType : 'server',usePager : true,checkbox: true,
    		rownumbers: true, enabledEdit : true, fixedCellHeight: true, frozen: false,
    		onBeforeEdit : f_detail_onBeforeEdit, onBeforeSubmitEdit : f_detail_onBeforeSubmitEdit, onAfterEdit : f_detail_onAfterEdit,
    		width: '65%',height: '90%',data : f_getInvDetailData(row),columnWidth:80, 
    		toolbar: { items: [
    			{ text: '删除', id:'delete', click: deleteDetailRow,icon:'delete' },
    			{ line:true }
    		]} 
		});
		
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
				var rows = jsonRowsToObject(rowdata.inv_detail_data, "tran_detail_id"); 
				for(var i = 0; i < rows.length; i++){
					data.Rows.push(rows[i]);
				}
    		}else{
    			//明细中没有批次信息，需要根据先进先出从后台取出
        		var invPara = {
    				store_id : liger.get("out_store_id").getValue().split(",")[0],
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
    //删除明细
    function deleteDetailRow(){ 
    	
    	detailGrid.deleteSelectedRow();
    	changeDetailAmount();
    }
  	
	 function matTranBySingleImport(){//整单出库
			
	    	var store_id = liger.get("out_store_id").getValue()?liger.get("out_store_id").getValue():'null';

			var paras = store_id;
			
			$.ligerDialog.open({
				url: "matTranMainBySinglePage.do?isCheck=false&paras="+paras, 
				height: 500,width: 950, 
				title:'整单调拨',
				modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
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
	
    </script>

  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
  	<input name="is_dir" type="hidden" id="is_dir" />
	<div id="layout1">
		<div position="top">
				<form name="form1" method="post"  id="form1" >
			        <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
			       <tr>
			       		<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span style="color:red">*</span>调拨单号：</td>
			            <td align="left" class="l-table-edit-td"><input name="tran_no" type="text" id="tran_no" disabled="disabled" ltype="text" value="自动生成"/></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span style="color:red">*</span>调拨类型：</td>
			            <td align="left" class="l-table-edit-td" ><input name="tran_type" type="text" id="tran_type" ltype="text" validate="{required:true}" /></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span id="bus_type_span" style="color:red">*</span>业务类型：</td>
			            <td align="left" class="l-table-edit-td" ><input name="bus_type" type="text" id="bus_type" ltype="text" disabled="disabled" validate="{required:true}" /></td>
			            <td align="left"></td>
			        </tr>
			        <tr>
			        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span style="color:red">*</span>编制日期：</td>
			            <td align="left" class="l-table-edit-td"><input name="tran_date" type="text" id="tran_date" ltype="text"  validate="{required:true}"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span style="color:red">*</span>调出单位：</td>
			            <td align="left" class="l-table-edit-td"><input name="out_hos_id" type="text" id="out_hos_id" ltype="text" disabled="disabled" validate="{required:true}" /></td>
			            <td align="left"></td>
			           	<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span style="color:red">*</span>调出仓库：</td>
			            <td align="left" class="l-table-edit-td"><input name="out_store_id" type="text" id="out_store_id" ltype="text" validate="{required:true}" /></td>
			            <td align="left"></td>
			        </tr>  
			         <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span id="tran_method_span" style="color:red">*</span>调拨方式：</td>
			            <td align="left" class="l-table-edit-td"><input name="tran_method" type="text" id="tran_method" ltype="text" disabled="disabled"  validate="{required:true}" /></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span style="color:red">*</span>调入单位：</td>
			            <td align="left" class="l-table-edit-td"><input name="in_hos_id" type="text" id="in_hos_id" ltype="text" disabled="disabled"  validate="{required:true}" /></td>
			            <td align="left"></td>
			           <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span style="color:red">*</span>调入仓库：</td>
			            <td align="left" class="l-table-edit-td"><input name="in_store_id" type="text" id="in_store_id" ltype="text"  validate="{required:true}" /></td>
			            <td align="left"></td>
					</tr>
					<tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">摘      要：</td>
			            <td align="left" class="l-table-edit-td" colspan="7"><input name="brief" type="text" id="brief" ltype="text" value="${matTranMain.brief}" validate="{maxlength:50}" /></td>
			            <td align="left"></td>
			        </tr> 
			    </table>
			    </form>
		</div>
		<div position="center" >
			<div id="maingrid"></div>
		</div>
		<div position="bottom" >
			<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%"  style="margin-top: 5px;">
				<tr>	
					<td align="center" class="l-table-edit-td" >
						<button id ="save" accessKey="S"><b>保存（<u>S</u>）</b></button>
						&nbsp;&nbsp;
						<button id ="close" accessKey="C"><b>关闭（<u>C</u>）</b></button>
					</td>
				</tr>
			</table>
		</div>
	</div>
    </body>
</html>
