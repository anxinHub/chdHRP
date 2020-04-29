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
    <script type="text/javascript">
     var grid; 
     var gridManager;
     var state = "${matApplyMain.state}";
	 var isEmpByDept = '${p04043 }' == 1 ? true : false;
     var cur_date = '';
     var is_zero = 1;
     $(function (){
    	 
    	var dates = getCurrentDate();
  		cur_date = dates.split(";")[2];
		loadDict()//加载下拉框
        //loadForm();
		loadHead();
		queryDetail();
		//绑定change事件
		$("#store_code").bind("change",function(){
	    	//loadHead();
	    	grid.columns[2].editor.grid.url= '../../../queryMatInvListApply.do?isCheck=false&store_id=' +
            liger.get("store_code").getValue().split(",")[0] +
            '&dept_id=' + liger.get("apply_dept").getValue().split(",")[0] +
            '&cur_date=' + cur_date + '&is_zero=' + is_zero ;
	    	grid.reRender();
		})
		$("#apply_dept").bind("change",function(){
	    	//loadHead();
	    	grid.columns[2].editor.grid.url= '../../../queryMatInvListApply.do?isCheck=false&store_id=' +
            liger.get("store_code").getValue().split(",")[0] +
            '&dept_id=' + liger.get("apply_dept").getValue().split(",")[0] +
            '&cur_date=' + cur_date + '&is_zero=' + is_zero ;
	    	grid.reRender();
		})
		
		
		/* $("#apply_dept").bind("change",function(){
	    	var para = {dept_id : liger.get("apply_dept").getValue() == "" ? "0" : liger.get("apply_dept").getValue().split(",")[0]};
	    	liger.get("app_emp").clear();
	    	liger.get("app_emp").set("parms", para);
	    	liger.get("app_emp").reload();
		}) */
     });  
 	
 	function queryDetail(){
 		grid.options.parms=[];
 		grid.options.newPage=1;
         //根据表字段进行添加查询条件
 		grid.options.parms.push({
 			name : 'apply_id',
 			value : '${matApplyMain.apply_id}'
 		});

     	//加载查询条件
     	grid.loadData(grid.where);
 	}
 	
 	function validateGrid() {  
 		//主表
 		if($("#app_date").val() == null || $("#app_date").val() == ""){
 			$.ligerDialog.warn("申请日期不能为空");  
 			return false;  
 		}
 		if(liger.get("store_code").getValue() == null || liger.get("store_code").getValue() == ""){
 			$.ligerDialog.warn("响应仓库不能为空");  
 			return false;  
 		}
 		if(liger.get("apply_dept").getValue() == null || liger.get("apply_dept").getValue() == ""){
 			$.ligerDialog.warn("申请科室不能为空");  
 			return false;  
 		}
 		if(liger.get("app_emp").getValue() == null || liger.get("app_emp").getValue() == ""){
 			$.ligerDialog.warn("申领人不能为空");  
 			return false;  
 		}
/*  		//台州医院要求领料人必填
 		if(liger.get("app_emp").getValue() == null || liger.get("app_emp").getValue() == ""){
 			$.ligerDialog.warn("领料人不能为空");  
 			return false;  
 		} */
 		
 		//明细
 		var msg="";
 		var rowm = "";
 		var rows = 0;
 		var data = gridManager.getData();
 		//alert(JSON.stringify(data));
 		//判断grid 中的数据是否重复或者为空
 		
 		
 		//判断grid 中的数据是否重复或者为空
 		var is_control=0; //默认不控制
 		//科室申请是否允许不按仓库库存量控制
 		var para = {
					store_id: liger.get("store_code").getValue().split(",")[0]
				}
				ajaxJsonObjectByUrl("../../../queryMatStoreByCode.do?isCheck=false", para, function (responseData) {
					if (responseData.state == "true") {
						is_control=responseData.is_control;
						
					}
				}, false);
 		
 		var targetMap = new HashMap();
 		$.each(data,function(i, v){
 			rowm = "";
			if (v.inv_id) {
				if(is_control==1){
					if (!v.app_amount || v.app_amount < 0 | v.app_amount > v.cur_amount){// || v.app_amount > v.cur_amount) {
	 					rowm+="【数量】、";
	 				} 
					if(rowm != ""){
						rowm = "第"+(i+1)+"行" + rowm.substring(0, rowm.length-1) + "需大于零,并且申领数量应小于库存数量<br>";//",并且申领数量应小于库存数量";
	 				}
				}else{
					if (!v.app_amount || v.app_amount < 0){// || v.app_amount > v.cur_amount) {
	 					rowm+="【数量】、";
	 				} 
					
					if(rowm != ""){
						rowm = "第"+(i+1)+"行" + rowm.substring(0, rowm.length-1) + "需大于零<br>";//",并且申领数量应小于库存数量";
	 				}
				}
				msg += rowm;
				var key=v.inv_id;
				var value="第"+(i+1)+"行";
				if(targetMap.get(key)== null || targetMap.get(key) == 'undefined' || targetMap.get(key) == ""){
					targetMap.put(key ,value);
				}else{
					msg += targetMap.get(key)+"与"+value+"材料编码不能重复" + "\n\r";
				}
				rows += 1;
			}  
 		});
 		if(rows == 0){
 			$.ligerDialog.warn("请添加明细数据！");  
			return false;  
 		}
 		if(msg != ""){
 			$.ligerDialog.warn(msg);  
			return false;  
 		} 	 	
 		return true;	
 	}
     
     function  save(){
		grid.endEdit();
    	 if(validateGrid()){
 	        var formPara={
 	        	apply_id : $("#apply_id").val(),
 	        	apply_no : $("#apply_no").val(),
 	        	app_date : $("#app_date").val(),
 				store_id : liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[0],
 				store_no : liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[1],
 				dept_id : liger.get("apply_dept").getValue() == null ? "" : liger.get("apply_dept").getValue().split(",")[0],
 				dept_no : liger.get("apply_dept").getValue() == null ? "" : liger.get("apply_dept").getValue().split(",")[1],
 				app_emp : liger.get("app_emp").getValue() == null ? "" : liger.get("app_emp").getValue(),
 				response_emp : liger.get("response_emp").getValue() == null ? "" : liger.get("response_emp").getValue(),
 				brief : $("#brief").val(),
 				detailData : JSON.stringify(gridManager.getData())
 			};
 	        ajaxJsonObjectByUrl("updateMatCommonOutApply.do",formPara,function(responseData){
 	            if(responseData.state=="true"){
 	            	queryDetail();
 	            	parentFrameUse().query();
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
   
    function loadDict(){
    	//字典下拉框
		autocompleteAsync("#store_code", "../../../queryMatStoreDictDate.do?isCheck=false", "id", "text", true, true, {is_read:'1'}, true);
    	liger.get("store_code").setValue("${matApplyMain.store_id},${matApplyMain.store_no}");
    	liger.get("store_code").setText("${matApplyMain.store_code} ${matApplyMain.store_name}");
    	
    	liger.get("store_code").set("onBeforeSelect",function(){
    		var manager = $("#maingrid").ligerGetGridManager();
    		var data = manager.getData();
    		var flag=true;
       		$.each(data, function(i, v) {
       			if (v.inv_code == "" || v.inv_code == null || v.inv_code == 'undefined') {
       				flag=true;
				}else{
					flag=false;
					return false;
				}
    			
       		});
    		if(!flag){
    			$.ligerDialog.warn("已经存在明细数据不允许改动");
    			return false;
    		}
    	
    	});
    	
		autocompleteAsync("#apply_dept", "../../../queryMatDeptDictDate.do?isCheck=false", "id", "text", true, true, {is_last: 1,is_write:'1'});
    	liger.get("apply_dept").setValue("${matApplyMain.dept_id},${matApplyMain.dept_no}");
    	liger.get("apply_dept").setText("${matApplyMain.dept_code} ${matApplyMain.dept_name}");
    	
		//autocompleteAsync("#app_emp", "../../../queryMatEmp.do?isCheck=false", "id", "text", true, true, {dept_id : "${matApplyMain.dept_id}"});
		autocompleteAsync("#app_emp", "../../../queryMatEmp.do?isCheck=false", "id", "text", true, true);
		liger.get("app_emp").setValue("${matApplyMain.app_emp}");
    	liger.get("app_emp").setText("${matApplyMain.emp_code} ${matApplyMain.emp_name}");
    	
		autocomplete("#response_emp", "../../../queryMatEmp.do?isCheck=false", "id", "text", true, true);
		liger.get("response_emp").setValue("${matApplyMain.response_emp}");
    	liger.get("response_emp").setText("${matApplyMain.response_code} ${matApplyMain.response_name}");

		//格式化文本框
        $("#apply_no").ligerTextBox({width:160, disabled:true});
        $("#app_date").ligerTextBox({width:160});
        $("#brief").ligerTextBox({width:320});
        //格式化按钮
        if(state == 1){
        	$("#save").ligerButton({click: save, width:90});
        }else{
        	$("#save").ligerButton({click: save, width:90, disabled:true});
        }
		$("#close").ligerButton({click: this_close, width:90});
	}
    function changeColumn() {
        is_zero = $("#is_zero").is(":checked") ? 1 : 0
        loadHead();
    }

	
    function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '材料名称(E)', name : 'inv_id', textField : 'inv_name', width : 240, align : 'left',
				editor : {
					type : 'select',
					valueField : 'inv_id',
					textField : 'inv_name',
					selectBoxWidth : "90%",
					selectBoxHeight : 240,
                    autocompleteAllowEmpty: false,
					grid : {
						columns : [{
							display : '交易编码', name : 'bid_code', width : 100, align : 'left'
						}, {
							display : '材料编码', name : 'inv_code', width : 100, align : 'left'
						}, {
							display : '材料名称', name : 'inv_name', width : 240, align : 'left'
						},{
							display : '别名', name : 'alias', width : 120, align : 'left'
						}, {
							display : '规格型号', name : 'inv_model', width : 180, align : 'left'
						}, {
							display : '计划单价', name : 'plan_price', width : 100, align : 'right',
							render : function(rowdata, rowindex, value) {
								return formatNumber(value, '${p04006 }', 1);
							}
						}, {
							display : '库存', name : 'cur_amount', width : 90, align : 'right',
							render : function(rowdata, rowindex, value) {
								return formatNumber(value, 2, 0);
							}
						}, {
							display : '计量单位', name : 'unit_name', width : 60, align : 'left'
						}, {
							display : '包装规格', name : 'inv_structure', width : 90, align : 'left'
						}, {
							display : '生产厂商', name : 'fac_name', width : 200, align : 'left'
						}, {
							display : '供应商', name : 'sup_name', width : 200, align : 'left'
						},{
							display : '是否代销材料', name : 'is_com', width : 90, align : 'left',
							render : function(rowdata, rowindex, value) {
								return value == 1 ? '是' : '否';
							}
						},{
							display : '是否灭菌材料', name : 'is_disinfect', width : 90, align : 'left',
							render : function(rowdata, rowindex, value) {
								return value == 1 ? '是' : '否';
							}
						}, {
							display : '零售价', name : 'sell_price', width : 100, align : 'right',
							render : function(rowdata, rowindex, value) {
								return formatNumber(value, '${p04072 }', 1);
							}
						}, {
							display : '物资类别', name : 'mat_type_name', width : 140, align : 'left'
						},  {
							display : '证件号', name : 'cert_code', width : 100, align : 'left'
						}, {
							display : '货位名称', name : 'location_name', width : 100, align : 'left'
						}, {
							display : '包装单位', name : 'pack_name', width : 80, align : 'left'
						}, {
							display : '转换量', name : 'num_exchange', width : 80, align : 'left'
						} ],
						switchPageSizeApplyComboBox : false,
						onSelectRow: function (data) {
							var e = window.event;
							if (e && e.which == 1) {
								f_onSelectRow_detail(data);
							}
						},
		         		gid: 'invGrid', 
    					url:  '../../../queryMatInvListApply.do?isCheck=false&store_id='+ 
    							liger.get("store_code").getValue().split(",")[0]+
    							'&dept_id='+liger.get("apply_dept").getValue().split(",")[0]+
    							'&cur_date='+cur_date + '&is_zero=' + is_zero,
						pageSize : 500,
						delayLoad:true,
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
					delayLoad : false, keySupport : true, autocomplete : true,
					spaceSearch:true,  //空格控制是否进行检索
					onSuccess : function() {
						this.parent("tr").next(".l-grid-row").find("td:first").focus();
					},
					ontextBoxKeyEnter: function (data) {
						f_onSelectRow_detail(data.rowdata);
					}
				},
				render : function(rowdata, rowindex, value) {
					return rowdata.inv_name;
				}
			},{
				display : '交易编码', name : 'bid_code', width : 100, align : 'left'
			}, {
				display : '材料编码', name : 'inv_code', width : 110, align : 'left',
				totalSummary: {
                    align : 'right',
                    render: function (suminf, column, cell) {
                    	return '<div>合计：</div>';
                    }
                } 
			}, {
				display : '材料变更号', name : 'inv_no', align : 'left', width : 90, hide: true
			}, {
				display : '规格型号', name : 'inv_model', width : 180, align : 'left'
			}, {
				display : '生成厂商', name : 'fac_name', width : 200, align : 'left'
			}, {
				display : '供应商', name : 'sup_name', width : 200, align : 'left'
			},{
				display : '计量单位', name : 'unit_name', width : 60, align : 'left'
			}, {
				display : '包装规格', name : 'inv_structure', width : 90, align : 'left'
			}, {
				display : '库存', name : 'cur_amount', width : 90, align : 'right',
				render : function(rowdata, rowindex, value) {
					return formatNumber(value, 2, 0);
				}
			},{
				display : '请领数量(E)', name : 'app_amount', width : 90, type : 'number', align : 'right',
				editor : {
					type : 'number',
				},
				render : function(rowdata, rowindex, value) {
					return value == null ? "" : formatNumber(value, 2, 1);
				},
				totalSummary: {
					align : 'left',
                    render: function (suminf, column, cell) {
                        return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, 2, 1)+ '</div>';
                    }
                }
			}, {
				display : '出库数量', name : 'out_amount', width : 90, type : 'number', align : 'right',
				render : function(rowdata, rowindex, value) {
					return value == null ? "" : formatNumber(value, 2, 1);
				}
			}, {
				display : '采购数量', name : 'pur_amount', width : 90, type : 'number', align : 'right',
				render : function(rowdata, rowindex, value) {
					return value == null ? "" : formatNumber(value, 2, 1);
				}
			}, {
				display : '单价', name : 'price', width : 100, align : 'right', 
				render : function(rowdata, rowindex, value) {
					return value == null ? "" : formatNumber(value, '${p04006 }', 1);
				}
			}, {
				display : '金额', name : 'amount_money', width : 100, align : 'right',
				render : function(rowdata, rowindex, value) {
					return value == null ? "" : formatNumber(value, '${p04005 }', 1);
				},
				totalSummary: {
					align : 'right',
                    render: function (suminf, column, cell) {
                        return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, '${p04005 }', 1)+ '</div>';
                    }
                } 
			}, {
				display : '是否代销材料', name : 'is_com', width : 90, align : 'left',
				render : function(rowdata, rowindex, value) {
					return value == 1 ? '是' : '否';
				}
			}, {
				display : '是否灭菌材料', name : 'is_disinfect', width : 90, align : 'left',
				render : function(rowdata, rowindex, value) {
					return value == 1 ? '是' : '否';
				}
			}, {
				display : '材料状态', name : 'is_closed', width : 80, align : 'left',
				render : function(rowdata, rowindex, value) {
					if(value == 1){
						return "已关闭";
					}else if(value == 2){
						return "已退回";
					}else{
						return "正常";
					}
				}
			}, {
				display : '备注(E)', name : 'note', width : 160, align : 'left',
				editor : {
					type : 'text',
				}
			} ],
	 		rowAttrRender: function(rowdata,rowid){
	 			if(rowdata.app_amount - rowdata.out_amount > 0){
	 				return "style='color: #FF0000'"
	 			}
 			},
			dataAction : 'server', dataType : 'server', usePager : false, width : '100%', height : '98%',
			url : 'queryMatCommonOutApplyDetail.do?isCheck=false&store_id='+liger.get("store_code").getValue().split(",")[0],
			checkbox : true, 
			enabledEdit : state == 1 ? true : false,  
			alternatingRow : true,
			onBeforeEdit : f_onBeforeEdit, onBeforeSubmitEdit : f_onBeforeSubmitEdit, onAfterEdit : f_onAfterEdit,
			onsuccess:function(){
			
				//is_addRow();
			},
			isScroll : true, rownumbers : true, delayLoad : true,//初始化明细数据
			selectRowButtonOnly : true,//heightDiff: -10,
			toolbar : {
				items : [ {
					text : '添加行（<u>A</u>）', id : 'add', click : is_addRow, icon : 'add', disabled: state == 1 ? false : true
				}, {
					line : true
				}, {
					text : '删除（<u>D</u>）', id : 'delete', click : remove, icon : 'delete', disabled: state == 1 ? false : true
				}, {
					line : true
				}, {
					text : '配套表导入（<u>M</u>）', id : 'match_imp', click : match_imp, icon : 'up', disabled: state == 1 ? false : true
				}, {
					line : true
				}, {
					text : '审核（<u>S</u>）', id : 'audit', click : audit, icon : 'audit', disabled: '${p04016 }' == 1 ? true : (state == 1 ? false : true)
				}, {
					line : true
				}, {
					text : '消审（<u>X</u>）', id : 'unAudit', click : unAudit, icon : 'unaudit', disabled: '${p04016 }' == 1 ? true : (state == 2 || state == 4  ? false : true)
				}, {
					line : true
				}, {
					text : '发送（<u>F</u>）', id : 'send', click : send, icon : 'logout', disabled: state == 2 ? false : true
				}, {
					line : true
				},{
					text : '选择材料（<u>C</u>）', id : 'choice_inv', click : choice_inv, icon : 'add'
				}]
			}
		});

		gridManager = $("#maingrid").ligerGetGridManager();
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
	function choice_inv(){
		
		if(liger.get("store_code").getValue() == null){
			$.ligerDialog.warn('请选择仓库');
			return ;
		}
		
		var store_id = liger.get("store_code").getValue().split(",")[0];
		var store_no = liger.get("store_code").getValue().split(",")[1];
		
		$.ligerDialog.open({url: "matCommonOutApplyChoiceInvPage.do?isCheck=false&store_id="+store_id+"&store_no="+store_no,
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
    
    //添加行数据
    function add_rows(data){
    	//grid.deleteAllRows();
    	grid.addRows(data);
    }
    //配套表导入
    function match_imp(){
    	var para = "store_id=" + liger.get("store_code").getValue() +
			"&store_text=" + liger.get("store_code").getText() + 
			"&dept_id=" + liger.get("apply_dept").getValue() +
			"&dept_text=" + liger.get("apply_dept").getText();
    	$.ligerDialog.open({
			title: '配套导入',
			height: 500,
			width: 900,
			url: 'matchImpPage.do?isCheck=false&'+para,
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true, top: 1
		});
    }
    //添加行
    function is_addRow(){
    	grid.addRow();
    }
  //发送
	function send(){
		/* if(state != 2){
			$.ligerDialog.error("发送失败！单据不是审核状态");
			return;
		} */
		var ParamVo =[];
		ParamVo.push(
			"${matApplyMain.group_id}"   +"@"+ 
			"${matApplyMain.hos_id}"  +"@"+ 
			"${matApplyMain.copy_code}"   +"@"+ 
			"${matApplyMain.apply_id}"
		) 
		$.ligerDialog.confirm('确定发送?', function (yes){
			if(yes){
				ajaxJsonObjectByUrl("sendMatCommonOutApply.do",{ParamVo : ParamVo.toString()},function (responseData){
					if(responseData.state=="true"){
						parentFrameUse().query();
						state = 3;
						changeButton();
				    	loadHead();
				    	grid.reRender();
					}
				});
			}
		}); 
	}
	
    //审核
	function audit(){
		if(state != 1){
			$.ligerDialog.error("审核失败！单据不是未审核状态");
			return;
		}
		var ParamVo =[];
		ParamVo.push(
			"${matApplyMain.group_id}"   +"@"+ 
			"${matApplyMain.hos_id}"  +"@"+ 
			"${matApplyMain.copy_code}"   +"@"+ 
			"${matApplyMain.apply_id}"
		) 
		$.ligerDialog.confirm('确定审核?', function (yes){
			if(yes){
				ajaxJsonObjectByUrl("auditMatCommonOutApply.do",{ParamVo : ParamVo.toString()},function (responseData){
					if(responseData.state=="true"){
						parentFrameUse().query();
						state = 2;
						changeButton();
				    	loadHead();
				    	grid.reRender();
					}
				});
			}
		}); 
	}

    //消审
	function unAudit(){
		if(state != 2){
			$.ligerDialog.error("消审失败！单据不是审核状态");
			return;
		}
		var ParamVo =[];
		ParamVo.push(
			"${matApplyMain.group_id}"   +"@"+ 
			"${matApplyMain.hos_id}"  +"@"+ 
			"${matApplyMain.copy_code}"   +"@"+ 
			"${matApplyMain.apply_id}"
		) 
		$.ligerDialog.confirm('确定消审?', function (yes){
			if(yes){
				ajaxJsonObjectByUrl("unAuditMatCommonOutApply.do",{ParamVo : ParamVo.toString()},function (responseData){
					if(responseData.state=="true"){
						parentFrameUse().query();
						state = 1;
						changeButton();
				    	loadHead();
				    	grid.reRender();
						is_addRow();
					}
				});
			}
		}); 
	}
    //删除
    function remove(){
    	grid.deleteSelectedRow();
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
					inv_id:data.inv_id,
					bid_code : data.bid_code,
					inv_name:data.inv_name,
					inv_code : data.inv_code,
					inv_no : data.inv_no,
					inv_model : data.inv_model == null ? "" : data.inv_model,
					unit_name : data.unit_name == null ? "" : data.unit_name,
					fac_name : data.fac_name == null ? "" : data.fac_name,
					sup_name : data.sup_name == null ? "" : data.sup_name,
					is_com : data.is_com == null ? "" : data.is_com,
					is_disinfect: data.is_disinfect == null ? "" : data.is_disinfect,
					price : data.price == null ? "" : data.price,
				    cur_amount : data.cur_amount == null ? "" : data.cur_amount, 
				    inv_structure : data.inv_structure == null ? "" : data.inv_structure, 
				});
				
				setTimeout(function (){
					grid.endEditToNext();
				},300)
			}
		}
		return true;
	}
	function f_onSelectRow(data, rowindex, rowobj) {
		return true;
	}
	// 编辑单元格提交编辑状态之前作判断限制
	function f_onBeforeSubmitEdit(e) {
		/*
		if (e.column.name == "inv_id" && e.value == ""){
			//e.column.name.focus();
			return false;
		}else if (e.column.name == "app_amount" && e.value == 0){
			return false;
		}else if (e.column.name == "price" && e.value == 0){
			return false;
		}
		*/
		return true;
	}
	// 跳转到下一个单元格之前事件
	function f_onAfterEdit(e) {
		if(e.value != ""){
			if (e.column.name == "app_amount"){
				//自动计算金额
				if(e.record.price != undefined){
					grid.updateCell('amount_money', e.value * e.record.price, e.rowindex);
				}
			}
		}
		grid.updateTotalSummary();
		return true;
	}
	//获取grid所有数据
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
		setTimeout(function() { //当数据为空时 默认新增一行
			grid.addRow();
			//grid.beginEdit(0);
		}, 500);
	}

	//键盘事件
	function loadHotkeys() {
		hotkeys('S', save);
		hotkeys('M', match_imp);
		hotkeys('S', audit);
		hotkeys('U', send);
		hotkeys('D', remove);
		hotkeys('P', printDate);
		hotkeys('A', batch_imp);
		hotkeys('C', this_close);
	}
	//打印
	function printDate(){
		
	}
	//控制按钮
	function changeButton(){
		if(state == 1){
        	$("#save").ligerButton({click: save, width:90, disabled:false});
        }else if (state == 3){
        	$("#save").ligerButton({click: save, width:90, disabled:true});
        }
	}
	//关闭当前弹出框
	function this_close(){
		frameElement.dialog.close();
	}
    </script>
  
</head>
  
<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post"  id="form1" >
		<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
			<tr>
	            <td style="display: none;">
	            	<input name="group_id" type="text" id="group_id" value="${matApplyMain.group_id}" ltype="text" />
	            	<input name="hos_id" type="text" id="hos_id" value="${matApplyMain.hos_id}" ltype="text" />
	            	<input name="copy_code" type="text" id="copy_code" value="${matApplyMain.copy_code}" ltype="text" />
	            	<input name="apply_id" type="hidden" id="apply_id" value="${matApplyMain.apply_id}" ltype="text" />
	            </td>
			</tr>
	        <tr>
	            <td align="right" class="l-table-edit-td" >
	            	<span style="color:red">*</span>申请单号：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="apply_no" type="text" id="apply_no" value="${matApplyMain.apply_no}" ltype="text" disabled="disabled" required="true" validate="{required:true}" />
	            </td>
	            <td align="right" class="l-table-edit-td" > 
	            	<span style="color:red">*</span>申请日期：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input class="Wdate" name="app_date" id="app_date" type="text" value="${matApplyMain.app_date}" required="true" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd HH:mm:ss'})" validate="{required:true}" />
	            </td>
	            <td align="right" class="l-table-edit-td"  >
	            	<span style="color:red">*</span>响应仓库：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="store_code" type="text" id="store_code" ltype="text" required="true" validate="{required:true}" />
	            </td>
	        </tr> 
	        <tr>
	            <td align="right" class="l-table-edit-td"  >
	            	<span style="color:red">*</span>申请部门：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="apply_dept" type="text" id="apply_dept" ltype="text" required="true" validate="{required:true}" />
	            </td>
	            <td align="right" class="l-table-edit-td"  >
	            	<span style="color:red">*</span>申&nbsp;领&nbsp;人：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="app_emp" type="text" id="app_emp" ltype="text" required="true" validate="{required:true}" />
	            </td>
	            
	            <td align="right" class="l-table-edit-td"  >
	            	响&nbsp;应&nbsp;人：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="response_emp" type="text" id="response_emp" ltype="text" />
	            </td>
	        </tr> 
			<tr>
			 <td align="right" class="l-table-edit-td">不显示零库存材料：</td>
                <td align="left" class="l-table-edit-td">
                    <input name="is_zero" type="checkbox" id="is_zero" ltype="text" onclick="changeColumn()" checked="checked" />
                    <span style="color:red">←申领零库存物资，请去掉勾选</span>
                </td>
	            <td align="right" class="l-table-edit-td" >
					摘&nbsp;&nbsp;&nbsp;&nbsp;要：
	            </td>
	            <td align="left" class="l-table-edit-td" colspan="5">
	            	<input name="brief" type="text" id="brief" ltype="text" value="${matApplyMain.brief}" validate="{required:false,maxlength:50}" />
	            </td>
			</tr>
	    </table>
    </form>
    <div style="width: 100%; height: 100%;">
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
</body>
</html>
