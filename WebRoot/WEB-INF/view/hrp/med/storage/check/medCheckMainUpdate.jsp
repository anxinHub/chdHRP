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
<script type="text/javascript">
  
     var grid;
     var gridManager = null;
     var state = "${state}";
     
     $(function (){
    	 
		$("#layout1").ligerLayout({
			topHeight:60,
			centerWidth:888
		});
    	 
        loadDict();
        //loadForm();
        loadHead(null);	
        
        //加入change事件
        $("#store_id").bind("change",function(){
	    	//loadHead(null);
	    	grid.columns[4].editor.grid.url = '../../queryMedStockInvList.do?isCheck=false&store_id=' 
						         					+ liger.get("store_id").getValue().split(",")[0];
	    	grid.reRender();
		})
    	$("#dept_id").bind("change",function(){
	    	var para = {
	    			dept_id: liger.get("dept_id").getValue().split(",")[0]
	    	}
	    	liger.get("emp_id").clear();
	    	liger.get("emp_id").set("parms", para);
	    	liger.get("emp_id").reload();
		})
     });  
     
     function  save(){
		grid.endEdit();
 		//主表
  		if(!liger.get("dept_id").getValue()){
  			$.ligerDialog.warn("盘点科室不能为空");  
  			return false;  
  		}
  		if(!liger.get("store_id").getValue()){
  			$.ligerDialog.warn("仓库不能为空");  
  			return false;  
  		}
  		if(!$("#create_date").val()){
  			$.ligerDialog.warn("盘点日期不能为空");  
  			return false;  
  		}
    	 
		var check_detail_data = gridManager.rows;
		var targetMap = new HashMap();
		var validate_detail_buf = new StringBuffer();
		var rows = 0;
 		if(check_detail_data.length > 0){
   			$.each(check_detail_data, function(d_index, d_content){ 
   	      		if(d_content.inv_id){
	   	      		var value="第"+(d_index+1)+"行";   	      		
	  	      		 
	   	      		var key=d_content.inv_id +"|"+d_content.batch_no+"|"+d_content.bar_code+"|"+d_content.price;
	   				if(targetMap.get(key)== null || targetMap.get(key) == 'undefined' || targetMap.get(key) == ""){   					
	   					targetMap.put(key ,value);   					
	   				}else{   					
	   					validate_detail_buf.append(targetMap.get(key)+"与"+value+"药品编码、生产批号、条形码单价不能重复" + "\n\r");   					
	   				}
	   				rows = rows + 1;
   	      		}	  
   	      		
  			}); 
   		}
		
		if(validate_detail_buf.toString()  != ""){
 			$.ligerDialog.warn(validate_detail_buf.toString());
 			return false;
 		}

		if(rows == 0){ 			
  			$.ligerDialog.warn('没有明细药品，不能保存！');  			
  			return false;
  		} 

        var formPara={
        		hos_id:$("#hos_id").val(),
        		group_id:$("#group_id").val(),
        		copy_code:$("#copy_code").val(),
        		check_id:$("#check_id").val(),
        		check_code:$("#check_code").val(),
 				dept_id:liger.get("dept_id").getValue().split(",")[0],
 				dept_no:liger.get("dept_id").getValue().split(",")[1],
        		store_id:liger.get("store_id").getValue().split(",")[0],
        		store_no:liger.get("store_id").getValue().split(",")[1],
        		emp_id:liger.get("emp_id").getValue().split(",")[0],
        		brif:$("#brief").val(),
        		create_date:$("#create_date").val(),
        		check_detail_data:JSON.stringify(check_detail_data)
         };
 		
        ajaxJsonObjectByUrl("updateMedCheckMain.do",formPara,function(responseData){
            if(responseData.state=="true"){
            	grid.reload();
            	parentFrameUse().query();
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
   
    function saveMedOutMain(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
        //字典下拉框
    	$("#check_code").ligerTextBox({width:180,disabled: true }); 
    	$("#create_date").ligerTextBox({width:180}); 
    	$("#brief").ligerTextBox({width:180}); 
        
    	//autocompleteAsync("#store_id", "../../queryMedStore.do?isCheck=false", "id", "text", true, true,"",false, false,'180');
    	autocompleteAsync("#store_id", "../../queryMedStoreDictDate.do?isCheck=false", "id", "text", true, true,{is_write:1},false, false,'180');
    	liger.get("store_id").setValue("${store_id},${store_no}");
		liger.get("store_id").setText("${store_code} ${store_name}");
    	//autocompleteAsync("#dept_id", "../../queryMedDeptDict.do?isCheck=false", "id", "text", true, true,{is_last : 1},false,false,'180');
    	autocompleteAsync("#dept_id", "../../queryMedDeptDictDate.do?isCheck=false", "id", "text", true, true,{is_last : 1,is_write:1},false,false,'180');
        liger.get("dept_id").setValue("${dept_id},${dept_no}");
		liger.get("dept_id").setText("${dept_code} ${dept_name}");
		
		autocompleteAsync("#emp_id", "../../queryMedEmpDict.do?isCheck=false", "id", "text", true, true,{dept_id: "${dept_id}"},false,false,'180');
    	liger.get("emp_id").setValue("${emp_id},${emp_no}");
		liger.get("emp_id").setText("${emp_code} ${emp_name}");
		
		$("#print").ligerButton({click: printDate, width:90});
		$("#printSet").ligerButton({click: printSet, width:100});
		$("#close").ligerButton({click: this_close, width:90});
     } 
    
    function loadHead(){
    	//var medCheckDetail = ${medCheckDetail};
    	var para = {
			check_id : '${check_id}',
			check_code : '${check_code}',
			store_id : liger.get("store_id").getValue().split(",")[0]
		};
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '交易编码', name: 'bid_code', align: 'left',width:100},
                     { display: '药品编码', name: 'inv_code', align: 'left',width:100,
                    	 totalSummary: {
      	                    align : 'right',
      	                    render: function (suminf, column, cell) {
      	                    	return '<div>合计：</div>';
      	                    }
                      	}	 
                     },
                     { display: '药品名称(E)', name : 'inv_id', textField : 'inv_name',align: 'left',width:240,
                    	 editor : {
								type : 'select',
								valueField: 'inv_id', 
								textField: 'inv_name',
							    keySupport:true,
								selectBoxWidth: '80%',
								selectBoxHeight: 240,
						      	autocomplete: true,
						      	highLight : true,
							    delayLoadGrid : false,
						                grid: {
						                	columns : [ {display : '交易编码', name : 'bid_code', width : 100, align : 'left'}, 
						                	            {display : '药品编码', name : 'inv_code', width : 100, align : 'left'}, 
						                	            {display : '药品名称', name : 'inv_name', width : 240, align : 'left'}, 
						                	            {display : '规格型号', name : 'inv_model', width : 160, align : 'left'},
						                	            {display : '计量单位', name : 'unit_name', width : 60, align : 'left'}, 
						                	            {display : '单价', name : 'price', width : 100, align : 'left', 
							                               	 render : function(rowdata, rowindex, value) {
								                  					return value == null ? "" : formatNumber(value, '${p08006 }', 1);
							                  				}
						                	           	}, 
						                	            {display : '批号', name : 'batch_no', width : 100, align : 'left'}, 
						                	            {display : '条码', name : 'bar_code', width : 100, align : 'left'}, 
						                	            {display : '账面数量', name : 'cur_amount', width : 90, align : 'left'}, 
						                	            {display : '账面金额', name : 'cur_money', width : 100, align : 'left', 
							                            	render : function(rowdata, rowindex, value) {
								                  				return value == null ? "" : formatNumber(value, '${p08005 }', 1);
							                  				}
						                	            }, 
						                	            {display : '有效日期', name : 'inv_date', width : 90, align : 'left'}, 
						                	            {display : '生产厂商', name : 'fac_name', width : 140, align : 'left'}, 
						                	            {display : '货位编码', name : 'location_code', width : 120, align : 'left'}, 
						                	            {display : '货位名称', name : 'location_new_name', width : 120, align : 'left'}
						                	            ],
						                    usePager:false,
						                    onSelectRow: function (data) {
												var e = window.event;
												if (e && e.which == 1) {
													f_onSelectRow_detail(data);
												}
											},
						         			url : '../../queryMedStockInvList.do?isCheck=false&store_id=' 
						         					+ liger.get("store_id").getValue().split(",")[0],
						                    switchPageSizeApplyComboBox : false,
						                    selectRowButtonOnly:true,
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
										},
										ontextBoxKeyEnter: function (data) {
											f_onSelectRow_detail(data.rowdata);
										}
						               
                    	 } 
                     },
                     { display: '规格型号', name: 'inv_model', align: 'left',width:180},
                     { display: '计量单位', name: 'unit_name', align: 'left',width:60},
                     { display: '批号', name: 'batch_no', align: 'left',width:100},
                     { display: '条形码', name: 'bar_code', align: 'left',width:120},
                     { display: '有效期', name: 'inva_date', align: 'left',width:90,type: 'date',format: 'yyyy-MM-dd'},
                     { display: '灭菌日期', name: 'disinfect_date', align: 'left',width:90,type: 'date',format: 'yyyy-MM-dd'},
                     { display: '单价', name: 'price', align: 'right',width:100,
                    	 render : function(rowdata, rowindex, value) {
                    		 rowdata.price = value == null ? "" : formatNumber(value, '${p08006 }', 0);
          					return value == null ? "" : formatNumber(value, '${p08006 }', 1);
          				}
                     },
                     { display: '账面数量', name: 'cur_amount', align: 'left',width:90,
                    	 totalSummary: {
        						align: 'left',
        						render: function (suminf, column, cell) {
        							return '<div>' + formatNumber(suminf.sum == null ? 0 : suminf.sum, 2, 1) + '</div>';
        						}
        					} 	 
                     },
                     { display: '账面金额', name: 'cur_money', align: 'right',width:100,
                    	 render : function(rowdata, rowindex, value) {
          					return value == null ? "" : formatNumber(value, '${p08006 }', 1);
          				},totalSummary: {
    						align: 'right',
    						render: function (suminf, column, cell) {
    							return '<div>' + formatNumber(suminf.sum == null ? 0 : suminf.sum, '${p08005 }', 1) + '</div>';
    						}
    					}
                     },
                     { display: '盘点数量(E)', name: 'chk_amount', align: 'left',width:90,editor : {type : 'float'},
                    	 totalSummary: {
        						align: 'left',
        						render: function (suminf, column, cell) {
        							return '<div>' + formatNumber(suminf.sum == null ? 0 : suminf.sum, 2, 1) + '</div>';
        						}
        					} 	 
                     },
                     { display: '盘点金额', name: 'chk_money', align: 'right',width:100,
                    	 render : function(rowdata, rowindex, value) {
          					return value == null ? "" : formatNumber(value, '${p08006 }', 1);
          				},totalSummary: {
    						align: 'right',
    						render: function (suminf, column, cell) {
    							return '<div>' + formatNumber(suminf.sum == null ? 0 : suminf.sum, '${p08005 }', 1) + '</div>';
    						}
    					}
                     },
                     { display: '盈亏数量', name: 'pl_amount', align: 'left',width:90,
                    	 totalSummary: {
        						align: 'left',
        						render: function (suminf, column, cell) {
        							return '<div>' + formatNumber(suminf.sum == null ? 0 : suminf.sum, 2, 1) + '</div>';
        						}
        					} 	 
                     },
                     { display: '盈亏金额', name: 'pl_money', align: 'right',width:100,
                    	 render : function(rowdata, rowindex, value) {
          					return value == null ? "" : formatNumber(value, '${p08006 }', 1);
          				},totalSummary: {
    						align: 'right',
    						render: function (suminf, column, cell) {
    							return '<div>' + formatNumber(suminf.sum == null ? 0 : suminf.sum, '${p08005 }', 1) + '</div>';
    						}
    					}
                     },
                     { display: '生产厂商', name: 'fac_name', align: 'left',width:180},
                     { display: '货位', name: 'location_name', align: 'left',width:120,hide:true},
                     { display: '货位', name: 'location_new_name', align: 'left',width:120},
                     { display: '盈亏说明(E)',name : 'note', align: 'left',width:180,editor : {type : 'text'}}
                     ],
                     usePager : false,width : '100%',height : '93%',enabledEdit : state == 1 ? true : false,fixedCellHeight:true,//data:medCheckDetail,
					selectRowButtonOnly:true,checkbox: true,rownumbers:true,isScroll:true,
					url:'queryMedCheckDetailByCheckID.do?isCheck=false',parms :para,
					onsuccess:function(){
					
						//is_addRow();
					},
					onBeforeEdit: f_onBeforeEdit, onAfterEdit: f_onAfterEdit,heightDiff:-20,
						 toolbar: { items: [
					                     	{ text: '保存', id:'add', click: saveMedOutMain,icon:'add', disabled: state == 1 ? false : true },
					                     	{ line:true },
					                     	{ text: '删除', id:'delete', click: deleteRow,icon:'delete', disabled: state == 1 ? false : true },
					                     	{ line:true },
					                     	{ text: '引入仓库药品', id:'add', click: medCheckByStoreImport,icon:'up',disabled: state == 1 ? false : true },
		 					                { line:true },
					                     	{ text: '重新计算', id:'edit', click: reCalculation,icon:'edit', disabled: state == 1 ? false : true },
					                     	{ line:true },
					                     	{ text: '生成出入库单', id:'createInOut', click: createInOut,icon:'create', disabled: state == 2 ? false : true },
					                     	{ line:true },
					                     	{ text: '审核', id:'audit', click: audit,icon:'audit', disabled: state == 1 ? false : true },
					                     	{ line:true },
					                     	{ text: '消审', id:'unaudit', click: unAudit,icon:'unaudit', disabled: state == 2 ? false : true },
					                     	{ line:true },
					                     	{ text: '复制账面数量', id:'copyNum', click: copyNum,icon:'copy' ,disabled: state == 1 ? false : true }
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
						bid_code : data.bid_code,
						fac_name : data.fac_name,
						inv_id : data.inv_id,
						inv_no : data.inv_no,
						inv_code : data.inv_code,
						inv_name : data.inv_name,
						inv_model : data.inv_model == null ? "" : data.inv_model,
						unit_name : data.unit_name == null ? "" : data.unit_name,
						batch_no : data.batch_no == null ? "" : data.batch_no,
						bar_code : data.bar_code == null ? "" : data.bar_code,
						cur_amount : data.cur_amount == null ? "" : data.cur_amount,
						cur_money : data.cur_money == null ? "" : data.cur_money,
						price : data.price == null ? "" : data.price,
						inva_date : data.inva_date == null ? "" : data.inva_date,
						disinfect_date : data.disinfect_date == null ? "" : data.disinfect_date,
						location_id : data.location_id == null ? "" : data.location_id,
					    location_new_name : data.location_new_name == null ? "" : data.location_new_name
					});
				}
			}
			return true;
		}
	    
	    function f_onAfterEdit(e){
	    	if("chk_amount" == e.column.columnname){
	    		gridManager.updateCell('chk_money', e.record.chk_amount*e.record.price, e.record); 
	    		gridManager.updateCell('pl_amount', (e.record.chk_amount -e.record.cur_amount), e.record); 
	    		gridManager.updateCell('pl_money', (e.record.chk_amount -e.record.cur_amount)*e.record.price, e.record); 
	    	}
			grid.updateTotalSummary();
	    }
		//重新计算
	    function reCalculation(){   	
	    	var check_detail_data = gridManager.rows;
	    	$.each(check_detail_data, function(d_index, d_content){ 
	    		gridManager.updateCell('cur_amount', d_content.left_amount, d_content);
	    		gridManager.updateCell('cur_money', (d_content.left_amount * d_content.price), d_content);
	    		gridManager.updateCell('pl_amount', (d_content.chk_amount -d_content.left_amount), d_content); 
	    		gridManager.updateCell('pl_money', (d_content.chk_amount -d_content.left_amount)*d_content.price, d_content);       		  
			}); 
	    }
		
	    //复制账面数量
	    function copyNum(){
	    	var check_detail_data = gridManager.rows;
	    	$.each(check_detail_data, function(d_index, d_content){ 
	    		gridManager.updateCell('chk_amount', d_content.cur_amount, d_content);
	    		gridManager.updateCell('chk_money', d_content.cur_money, d_content); 
	    		gridManager.updateCell('pl_amount', 0, d_content);
	    		gridManager.updateCell('pl_money', 0, d_content); 
			}); 
	    	reCalculation();
	    }
	    
	    //生成出入库盘点单
	    function createInOut(){
	    	
	    	if(state != 2){
				$.ligerDialog.error("生成失败！单据不是审核状态");
				return false;
			}
	    	
	    	var ParamVo = [];
	    	ParamVo.push(
	    			$("#hos_id").val() + "@" + 
	    			$("#group_id").val() + "@"+ 
	    			$("#copy_code").val() + "@" +
	    			$("#check_id").val()
			)
			
			$.ligerDialog.confirm('确定生成出入库单?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("createInOut.do", {ParamVo : ParamVo.toString()}, function(responseData) {
						if (responseData.state == "true") {
							state = 3;
							parentFrameUse().query();
					    	loadHead();
					    	grid.reRender();
						}
					});
				}
			});
	    }
	    //审核
	    function audit(){
			if(state > 1){
				$.ligerDialog.error("审核失败！单据不是未审核状态");
				return false;
			}
	
			var ParamVo = [];
			ParamVo.push($("#hos_id").val() + "@" + $("#group_id").val()+ "@"+ $("#copy_code").val() + "@" +$("#check_id").val());
			$.ligerDialog.confirm('确定审核?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("auditMedCheckMain.do", {ParamVo : ParamVo.toString()}, function(responseData) {
						if (responseData.state == "true") {
							state = 2;
							parentFrameUse().query();
					    	loadHead();
					    	g.reRender();
						}
					});
				}
			});
		}
		//销审    
	    function unAudit(){
			if(state > 2){
				$.ligerDialog.error("消审失败！单据是已完成状态");
				return false;
			}
	
			var ParamVo = [];
			ParamVo.push($("#hos_id").val() + "@" + $("#group_id").val()+ "@"+ $("#copy_code").val() + "@" +$("#check_id").val());
			$.ligerDialog.confirm('消审审核?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("unAuditMedCheckMain.do", {ParamVo : ParamVo.toString()}, function(responseData) {
						if (responseData.state == "true") {
							state = 1;
							parentFrameUse().query();
					    	loadHead();
					    	g.reRender();
					    	is_addRow();
						}
					});
				}
			});
		}
    	//引入仓库药品
	    function medCheckByStoreImport(){//引入仓库药品
			var store_id = liger.get("store_id").getValue()?liger.get("store_id").getValue():'null';
			var paras = store_id;
	
			$.ligerDialog.open({
				title:'引入仓库药品',
				height: 500,width: 900, 
				url: "medCheckMainByStorePage.do?isCheck=false&paras="+paras, 
				modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
				buttons: [ 
					{ text: '选择', onclick: function (item, dialog) { dialog.frame.checkMedOutFifo(); },cls:'l-dialog-btn-highlight' }, 
					{ text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
				] 
			});
		}
    	//删除行
		function deleteRow(){ 
			gridManager.deleteSelectedRow();
	    }

		//当单据未审核 默认新增一行
	    function is_addRow(){
	    	if(state > 1){
		    	return;
	    	}
			setTimeout(function () {
				g.addRow();
			}, 1000);
		}
		
	  	//打印
		function printDate(){
			var useId=0;//统一打印
			if('${p08046 }'==1){
				//按用户打印
				useId='${sessionScope.user_id }';
			}else if('${p08046 }'==2){
				//按仓库打印
				if(liger.get("store_id").getValue()==""){
					$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
					return;
				}
				useId=liger.get("store_id").getValue().split(",")[0];
			}
			
			var para={
				check_id : $("#check_id").val(),
				
				
				template_code:'08014',
				class_name:"com.chd.hrp.med.serviceImpl.storage.check.MedCheckMainServiceImpl",
				method_name:"queryMedCheckByPrintDY",
				isPreview:true,//预览窗口，传绝对路径
				use_id:useId
		};
		
		officeFormPrint(para);
		
				
			/* printTemplate("queryMedCheckByPrintTemlate.do?isCheck=false",para); */
		}
	
		//打印设置
		function printSet(){
			var useId=0;//统一打印
			if('${p08046 }'==1){
				//按用户打印
				useId='${sessionScope.user_id }';
			}else if('${p08046 }'==2){
				//按仓库打印
				if(liger.get("store_id").getValue()==""){
					$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
					return;
				}
				useId=liger.get("store_id").getValue().split(",")[0];
			}
			
			officeFormTemplate({template_code:"08014",use_id:useId});
			/* parent.parent.$.ligerDialog.open({
				url : 'hrp/med/storage/check/medCheckPrintSetPage.do?template_code=08027&use_id='+useId,
				data:{}, height: $(parent).height(),width: $(parent).width(), 
				title:'打印模板设置',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
			}); */
		}
	
		//关闭当前页面
		function this_close(){
			frameElement.dialog.close();
		}
		
		function add_rows(data) {
			//先清除数据然后再添加
			grid.deleteAllRows();
			grid.addRows(data);
		}
	
    </script>

  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
		<input name="hos_id"  type="hidden" id="hos_id" value="${hos_id}" />
		<input name="group_id"  type="hidden" id="group_id" value="${group_id}" />
		<input name="copy_code"  type="hidden" id="copy_code" value="${copy_code}" />
		<input name="check_id"  type="hidden" id="check_id" value="${check_id}" />
		<div id="toptoolbar"></div>
		<form name="form1" method="post"  id="form1" >
				<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
			        <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span style="color:red">*</span>盘点单号：</td>
			            <td align="left" class="l-table-edit-td"><input name="check_code" type="text" id="check_code" ltype="text" value="${check_code}"/></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span style="color:red">*</span>盘点科室：</td>
			            <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span style="color:red">*</span>仓库：</td>
			            <td align="left" class="l-table-edit-td"><input name="store_id" type="text" id="store_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
			            <td align="left"></td>
			        </tr> 
			        <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span style="color:red">*</span>盘点日期：</td>
			            <td align="left" class="l-table-edit-td"><input name="create_date" type="text" id="create_date" value="${create_date}" ltype="text" validate="{required:true,maxlength:20}"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">经  办  人：</td>
			            <td align="left" class="l-table-edit-td"><input name="emp_id" type="text" id="emp_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">摘要：</td>
			            <td align="left" class="l-table-edit-td" colspan="4"><input name="brief" type="text" id="brief" ltype="text"  value="${brief}"/></td>
			            <td align="left"></td>
			        </tr> 
			    </table>
			</form>
		
		<div style="width: 100%; height: 100%;">
			<div id="maingrid"></div>
			<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%"  style="margin-top: 5px;">
				<tr>	
					<td align="center" class="l-table-edit-td" colspan="3">
						<button id ="print" accessKey="P"><b>打印（<u>P</u>）</b></button>
						&nbsp;&nbsp; 
						<button id ="printSet" accessKey="M"><b>打印模板（<u>M</u>）</b></button>
						&nbsp;&nbsp;
						<button id ="close" accessKey="L"><b>关闭（<u>L</u>）</b></button>
					</td>
				</tr>
			</table>
		</div>
    </body>
</html>
