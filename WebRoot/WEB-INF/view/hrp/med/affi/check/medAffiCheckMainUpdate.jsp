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
	var gridDetail;	
	var gridManager = null;	
	var state = "${state}";     
     $(function (){    	 
    	 $("#layout1").ligerLayout({
 			topHeight:60,
 			centerWidth:888
 		});     	 
         loadDict();                   
         loadForm();        
         loadHead(null);	        
         $("#store_id").bind("change",function(){
 	    	loadHead(null);
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
         		brif:$("#brif").val(),        		
         		create_date:$("#create_date").val(),
         		check_detail_data:JSON.stringify(check_detail_data)
          };
  		
         ajaxJsonObjectByUrl("updateMedAffiCheckMain.do?isCheck=true",formPara,function(responseData){           
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
    	$("#brif").ligerTextBox({width:180}); 
        
    	autocompleteAsync("#store_id", "../../queryMedStore.do?isCheck=false", "id", "text", true, true,{is_com : '1'},false, false,'180');
    	liger.get("store_id").setValue("${store_id},${store_no}");
		liger.get("store_id").setText("${store_code} ${store_name}");
		
    	autocompleteAsync("#dept_id", "../../queryMedDeptDict.do?isCheck=false", "id", "text", true, true,{is_last : 1},false,false,'180');
        liger.get("dept_id").setValue("${dept_id},${dept_no}");
		liger.get("dept_id").setText("${dept_code} ${dept_name}");
		
    	autocomplete("#emp_id", "../../queryMedEmp.do?isCheck=false", "id", "text", true, true,{dept_id: "${dept_id}"},false,false,'180');
    	liger.get("emp_id").setValue("${emp_id}");
		liger.get("emp_id").setText("${emp_code} ${emp_name}");
    	//加入change事件
    	$("#dept_id").bind("change",function(){
	    	var para = {
	    			dept_id: liger.get("dept_id").getValue().split(",")[0]
	    	}
	    	autocomplete("#emp_id", "../../queryMedEmpDict.do?isCheck=false", "id", "text", true, true,para,true,false,'180');
		});
    	
    	$("#print").ligerButton({click: print, width:90});
		$("#printSet").ligerButton({click: printSet, width:100});
		$("#close").ligerButton({click: this_close, width:90});
     }  

	
    function loadHead(){
    	var para = {
			check_id : '${check_id}',
			check_code : '${check_code}'
		};
    	
    	grid = $("#maingrid").ligerGrid({
           columns: [ 

                     { display: '药品编码', name: 'inv_code', align: 'left',width:140,
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
						                	columns : [ 
						                	            {display : '药品编码', name : 'inv_code', width : 100, align : 'left'}, 
						                	            {display : '药品名称', name : 'inv_name', width : 240, align : 'left'}, 
						                	            {display : '规格型号', name : 'inv_model', width : 160, align : 'left'},
						                	            {display : '计量单位', name : 'unit_name', width : 140, align : 'left'}, 
						                	            {display : '单价', name : 'price', width : 60, align : 'left', 
	 						                               	render : function(rowdata, rowindex, value) { 
	 						                  					return value == null ? "" : formatNumber(value, '${p08006 }', 1);
	 						                  				}
						                	            }, 
						                	            {display : '条码', name : 'bar_code', width : 90, align : 'left'}, 
						                	            {display : '账面数量', name : 'cur_amount', width : 140, align : 'left'}, 
						                	            {display : '账面金额', name : 'cur_money', width : 140, align : 'left', 
	 						                               	render : function(rowdata, rowindex, value) { 
	 						                  					return value == null ? "" : formatNumber(value, '${p08005 }', 1);
	 						                  				}
						                	            }, 
						                	            {display : '有效日期', name : 'inv_date', width : 140, align : 'left'}, 
						                	            {display : '生产厂商', name : 'fac_name', width : 140, align : 'left'}, 
						                	            {display : '货位编码', name : 'location_code', width : 140, align : 'left'}, 
						                	            {display : '货位名称', name : 'location_name', width : 140, align : 'left'}
						                	            ],
						                    usePager:false,
						                    onSelectRow: function (data) {
				    							var e = window.event;
				    							if (e && e.which == 1) {
				    								f_onSelectRow_detail(data);
				    							}
				    						},
						         			url : '../../queryMedAffiOutInvList.do?isCheck=false&store_id=' 
						         					+ liger.get("store_id").getValue().split(",")[0],
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
						    				},
						                    switchPageSizeApplyComboBox : false,
						                    selectRowButtonOnly:true
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
                     { display: '规格型号', name: 'inv_model', align: 'left',width:180},
                     { display: '计量单位', name: 'unit_name', align: 'left',width:140},
                     { display: '批号', name: 'batch_no', align: 'left',width:100},
                     { display: '条形码', name: 'bar_code', align: 'left',width:120},
                     { display: '有效期', name: 'inva_date', align: 'left',width:120,type: 'date',format: 'yyyy-MM-dd'},
                     { display: '灭菌日期', name: 'disinfect_date', align: 'left',width:120,type: 'date',format: 'yyyy-MM-dd'},
                      { display: '单价', name: 'price', align: 'right',width:140,
                     	 render : function(rowdata, rowindex, value) {
                     		 rowdata.price = value == null ? "" : formatNumber(value, '${p08006 }', 0);
           					return value == null ? "" : formatNumber(value, '${p08006 }', 1);
           				}
                      },
                     { display: '账面数量', name: 'cur_amount', align: 'left',width:140,
                    	  totalSummary: {
       						align: 'left',
       						render: function (suminf, column, cell) {
       							return '<div>' + formatNumber(suminf.sum == null ? 0 : suminf.sum, 2, 1) + '</div>';
       						}
       					} 
                     },
                     { display: '账面金额', name: 'cur_money', align: 'right',width:140,
                    	 render : function(rowdata, rowindex, value) {
          					return value == null ? "" : formatNumber(value, '${p08006 }', 1);
          				},
          				totalSummary: {
    						align: 'right',
    						render: function (suminf, column, cell) {
    							return '<div>' + formatNumber(suminf.sum == null ? 0 : suminf.sum, '${p08005 }', 1) + '</div>';
    						}
    					}
                     },
                     { display: '盘点数量(E)', name: 'chk_amount', align: 'left',width:140,editor : {type : 'float'},
     					totalSummary: {
     						align: 'left',
     						render: function (suminf, column, cell) {
     							return '<div>' + formatNumber(suminf.sum == null ? 0 : suminf.sum, 2, 1) + '</div>';
     						}
     					}
                     },
                     { display: '盘点金额', name: 'chk_money', align: 'right',width:140,
                    	 render : function(rowdata, rowindex, value) {
          					return value == null ? "" : formatNumber(value, '${p08006 }', 1);
          				},
          				totalSummary: {
    						align: 'right',
    						render: function (suminf, column, cell) {
    							return '<div>' + formatNumber(suminf.sum == null ? 0 : suminf.sum, '${p08005 }', 1) + '</div>';
    						}
    					}
                     },
                     { display: '盈亏数量', name: 'pl_amount', align: 'left',width:140,
                    	 totalSummary: {
      						align: 'left',
      						render: function (suminf, column, cell) {
      							return '<div>' + formatNumber(suminf.sum == null ? 0 : suminf.sum, 2, 1) + '</div>';
      						}
      					}	 
                     },
                     { display: '盈亏金额', name: 'pl_money', align: 'right',width:140,
                    	 render : function(rowdata, rowindex, value) {
          					return value == null ? "" : formatNumber(value, '${p08006 }', 1);
          				},totalSummary: {
    						align: 'right',
    						render: function (suminf, column, cell) {
    							return '<div>' + formatNumber(suminf.sum == null ? 0 : suminf.sum, '${p08005 }', 1) + '</div>';
    						}
    					}
                     },
                     { display: '货位', name: 'location_name', align: 'left',width:180},
                     { display: '盈亏说明(E)',name : 'note', align: 'left',width:180,editor : {type : 'text'}}
                     ],
                    usePager : false,width : '100%',height : '93%',enabledEdit : state == 1 ? true : false,fixedCellHeight:true,//data:medCheckDetail,
         			selectRowButtonOnly:true,checkbox: true,rownumbers:true,isScroll:true,
					url:'queryMedCheckDetail.do?isCheck=false',parms :para,
					onsuccess:function(){
						//is_addRow();
					},
					onBeforeEdit: f_onBeforeEdit, onAfterEdit: f_onAfterEdit,heightDiff:-20,
						 toolbar: { items: [
					                     	{ text: '保存', id:'add', click: saveMedOutMain,icon:'add' , disabled: state == 1 ? false : true},
					                     	{ line:true },
					                     	{ text: '删除', id:'delete', click: deleteRow,icon:'delete', disabled: state == 1 ? false : true },
					                     	{ line:true },
					                     	{ text: '引入仓库药品', id:'add', click: medCheckByStoreImport,icon:'up',disabled: state == 1 ? false : true },
		 					                { line:true },
					                     	{ text: '重新计算', id:'edit', click: reCalculation,icon:'edit',disabled: state == 1 ? false : true  },
					                     	{ line:true },
					                     	{ text: '生成出入库单', id:'createInOut', click: createInOut,icon:'create', disabled: state == 2 ? false : true  },
					                     	{ line:true },
					                     	{ text: '审核', id:'audit', click: audit,icon:'audit' , disabled: state == 1 ? false : true},
					                     	{ line:true },
					                     	{ text: '消审', id:'unaudit', click: unAudit,icon:'unaudit',disabled: state == 2 ? false : true },
					                     	{ line:true },
					                     	{ text: '复制账面数量', id:'copyNum', click: copyNum,icon:'copy',disabled: state == 1 ? false : true },
					                     	{ line:true },
					                     	{ text: '关闭', id:'close', click: this_close,icon:'close' }
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
					cur_money : data.cur_money == null ? "" : data.cur_money,
					price : data.price == null ? "" : data.price,
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
    		gridManager.updateCell('chk_money', d_content.cur_amount * d_content.price, d_content);
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
				ajaxJsonObjectByUrl("createMedAffiInOut.do?isCheck=ture", {ParamVo : ParamVo.toString()}, function(responseData) {
					if (responseData.state == "true") {
						state = 3;
						parent.query();
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
		ParamVo.push(
			$("#hos_id").val() + "@" + 
			$("#group_id").val()+ "@"+ 
			$("#copy_code").val() + "@" +
			$("#check_id").val()
		);

		$.ligerDialog.confirm('确定审核?', function(yes) {
			if (yes) {
				ajaxJsonObjectByUrl("auditMedAffiCheckMain.do?isCheck=true", {ParamVo : ParamVo.toString()}, function(responseData) {
					if (responseData.state == "true") {
						state = 2;
						parent.query();
				    	loadHead();
				    	grid.reRender();
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
		ParamVo.push(
			$("#hos_id").val() + "@" + 
			$("#group_id").val()+ "@"+ 
			$("#copy_code").val() + "@" +
			$("#check_id").val()
		);

		$.ligerDialog.confirm('消审审核?', function(yes) {
			if (yes) {
				ajaxJsonObjectByUrl("unAuditMedAffiCheckMain.do?isCheck=true", {ParamVo : ParamVo.toString()}, function(responseData) {
					if (responseData.state == "true") {
						state = 1;
						parent.query();
				    	loadHead();
				    	grid.reRender();
					}
				});
			}
		});
	}
    
    //引入仓库药品	
    function medCheckByStoreImport(){		
	    var store_id = liger.get("store_id").getValue()?liger.get("store_id").getValue():'null';			
		var paras = store_id;			
		$.ligerDialog.open({
			url: "medAffiCheckMainByStorePage.do?isCheck=false&paras="+paras, height: 450,width: 900,
					title:'引入仓库药品',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
					buttons: [ 
						{ text: '选择', onclick: function (item, dialog) { dialog.frame.checkMedOutFifo(); },cls:'l-dialog-btn-highlight' }, 
						{ text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
					] 
			});
	}

	//打印设置
	function printSet(){
		
		var useId=0;//统一打印
		
		officeFormTemplate({template_code:"08022",use_id:useId});
	}
	 
    //打印
    function print(){
    	
		var useId=0;//统一打印
		var para={
			paraId : $("#check_id").val(),
			template_code:'08022',
			class_name:"com.chd.hrp.med.serviceImpl.affi.check.MedAffiCheckMainServiceImpl",
			method_name:"queryMedAffiCheckByPrintTemlate1",
			isPrintCount:false,//更新打印次数
			isPreview:true,//预览窗口，传绝对路径
			use_id:useId,
			p_num: 0
		}; 
		 	
		officeFormPrint(para);
	}
	 
    
    //删除
	function deleteRow(){ 
		gridManager.deleteSelectedRow();
    }
	//关闭
	function this_close(){
 		frameElement.dialog.close();
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
			            <td align="left" class="l-table-edit-td"><input name="emp_id" type="text" id="emp_id" ltype="text" validate="{required:false,maxlength:20}" /></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">摘要：</td>
			            <td align="left" class="l-table-edit-td" colspan="4"><input name="brif" type="text" id="brif" ltype="text"  value="${brif}"/></td>
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
