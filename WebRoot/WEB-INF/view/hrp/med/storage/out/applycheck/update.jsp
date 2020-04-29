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
	<script src="<%=path%>/lib/hrp/med/med.js"	type="text/javascript"></script>
	<link href='<%=path%>/lib/SpreadJS/css/gcspread.sheets.9.40.20161.0.css' rel='stylesheet' type='text/css'/>
	<style type="text/css">
        .notEditor .l-grid-row-cell{
            background: rgba(210, 208, 208, 0.8);
			opacity: .66;
			filter: alpha(opacity=66);
        }
    </style>
	<script src='<%=path%>/lib/SpreadJS/scripts/gcspread.sheets.all.9.40.20161.0.min.js' type='text/javascript'></script>
	<script src='<%=path%>/lib/SpreadJS/scripts/pluggable/gcspread.sheets.print.9.40.20161.0.min.js' type='text/javascript'></script>
    <script type="text/javascript">
     var grid;
     var gridManager;
     var do_state = '${medApplyMain.do_state}';
     
     $(function (){
		loadDict()//加载下拉框
        //loadForm();
		loadHead();
		queryDetail();
		loadButton();
     });  
 	
 	function queryDetail(){
 		grid.options.parms=[];
 		grid.options.newPage=1;
		//根据表字段进行添加查询条件
 		grid.options.parms.push({
 			name : 'apply_id',
 			value : '${medApplyMain.apply_id}'
 		});
 		grid.options.parms.push({
 			name : 'store_id',
 			value : '${medApplyMain.store_id}'
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
    	//仓库
		$("#store_code").ligerComboBox({width:160,disabled:true,cancelable: false});
        liger.get("store_code").setValue("${medApplyMain.store_id},${medApplyMain.store_no}");
		liger.get("store_code").setText("${medApplyMain.store_code} ${medApplyMain.store_name}");
		//申请科室
		$("#apply_dept").ligerComboBox({width:160,disabled:true,cancelable: false});
        liger.get("apply_dept").setValue("${medApplyMain.dept_id},${medApplyMain.dept_no}");
		liger.get("apply_dept").setText("${medApplyMain.dept_code} ${medApplyMain.dept_name}");
		//申请人
		$("#app_emp").ligerComboBox({width:160,disabled:true,cancelable: false});
        liger.get("app_emp").setValue("${medApplyMain.app_emp}");
		liger.get("app_emp").setText("${medApplyMain.emp_name}");
		//响应人
		$("#response_emp").ligerComboBox({width:160,disabled:true,cancelable: false});
        liger.get("response_emp").setValue("${medApplyMain.response_emp}");
		liger.get("response_emp").setText("${medApplyMain.response_emp_name}");
		//格式化文本框
        $("#apply_no").ligerTextBox({width:160, disabled:true});
        $("#app_date").ligerTextBox({width:160, disabled:true});
        $("#brief").ligerTextBox({width:320, disabled:true});
		
     } 

    //格式化按钮
	function loadButton(){
    	//全部完成后生成按钮不可用
		if(do_state == 3){
	 		$("#out").ligerButton({click: out, width:110, disabled:true});
	 		$("#affiout").ligerButton({click: affiout, width:140, disabled:true});
	 		$("#tran").ligerButton({click: tran, width:110, disabled:true});
	 		$("#affitran").ligerButton({click: affitran, width:140, disabled:true});
		}else{
	 		$("#out").ligerButton({click: out, width:110, disabled:false});
	 		$("#affiout").ligerButton({click: affiout, width:140, disabled:false});
	 		$("#tran").ligerButton({click: tran, width:110, disabled:false});
	 		$("#affitran").ligerButton({click: affitran, width:140, disabled:false});
		} 
	 		
    	$("#plan").ligerButton({click: is_exists, width:150, disabled:false});
 		$("#print").ligerButton({click: print, width:70});
 		$("#printSet").ligerButton({click: printSet, width:100});
 		$("#close").ligerButton({click: this_close, width:70});
	}
    
	//改变按钮
	function changeButton(flag){
		//flag:1-完成，2-本次完成
		//完成后按钮不可用
 		if(flag == 1){
 	 		$("#out").ligerButton({click: out, width:110, disabled:false});
 	 		$("#affiout").ligerButton({click: affiout, width:140, disabled:false});
 	 		$("#tran").ligerButton({click: tran, width:110, disabled:false});
 	 		$("#affitran").ligerButton({click: affitran, width:140, disabled:false});
 		}
     	//本次完成后按钮可用
 		if(flag == 2){
 	 		$("#out").ligerButton({click: out, width:110, disabled:false});
 	 		$("#affiout").ligerButton({click: affiout, width:140, disabled:false});
 	 		$("#tran").ligerButton({click: tran, width:110, disabled:false});
 	 		$("#affitran").ligerButton({click: affitran, width:140, disabled:false});
 		}
	}
     
 	//键盘事件
 	function loadHotkeys() {
 		hotkeys('O', out);
 		hotkeys('G', affiout);
 		hotkeys('M', tran);
 		hotkeys('L', affitran);
 		hotkeys('Y', plan);
 		hotkeys('P', print);
 		hotkeys('S', printSet);
 		hotkeys('C', close);
 	}
	
    function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [  { display : '交易编码', name : 'bid_code', width : 110, align : 'left'
		     },{
				display : '药品编码', name : 'inv_code', width : 120, align : 'left',
				totalSummary: {
                    align : 'right',
                    render: function (suminf, column, cell) {
                    	return '<div>合计：</div>';
                    }
                }
			}, {
				display : '药品名称', name : 'inv_name', width : 340, align : 'left'
			}, {
				display : '规格型号', name : 'inv_model', width : 100, align : 'left'
			}, {
				display : '生成厂商', name : 'fac_name', width : 200, align : 'left'
			}, {
				display : '计量单位', name : 'unit_name', width : 60, align : 'left'
			}, {
				display : '请领数量', name : 'app_amount', width : 90, align : 'left',
				render : function(rowdata, rowindex, value) {
					return formatNumber(value == null ? 0 : value, 2, 1);
				},
				totalSummary: {
					align : 'left',
                    render: function (suminf, column, cell) {
                        return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, 2, 1)+ '</div>';
                    }
                }
			}, {
				display : '已处理数量', name : 'rela_amount', width : 90, align : 'left',
				render : function(rowdata, rowindex, value) {
					return formatNumber(value == null ? 0 : value, 2, 1);
				},
				totalSummary: {
					align : 'left',
                    render: function (suminf, column, cell) {
                        return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, 2, 1)+ '</div>';
                    }
                }
			}, {
				display : '是否代销药品', name : 'is_com', width : 90, align : 'left',
				render : function(rowdata, rowindex, value) { 
					return value == 1 ? '是' : '否';
				}
			}, {
				display : '库存量', name : 'common_amount', width : 90, align : 'left',
				render : function(rowdata, rowindex, value) {
					return formatNumber(value == null ? 0 : value, 2, 1);
				},
				totalSummary: {
					align : 'left',
                    render: function (suminf, column, cell) {
                        return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, 2, 1)+ '</div>';
                    }
                }
			}, {
				display : '即时库存量', name : 'imme_amount', width : 90, align : 'left',
				render : function(rowdata, rowindex, value) {
					return formatNumber(value == null ? 0 : value, 2, 1);
				},
				totalSummary: {
					align : 'left',
                    render: function (suminf, column, cell) {
                        return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, 2, 1)+ '</div>';
                    }
                }
			}, {
				display : '代销药品库存量', name : 'affi_amount', width : 90, align : 'left',
				render : function(rowdata, rowindex, value) {
					return formatNumber(value == null ? 0 : value, 2, 1);
				},
				totalSummary: {
					align : 'left',
                    render: function (suminf, column, cell) {
                        return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, 2, 1)+ '</div>';
                    }
                }
			}, {
				display : '代销药品即时库存量', name : 'affi_imme_amount', width : 110, align : 'left',
				render : function(rowdata, rowindex, value) {
					return formatNumber(value == null ? 0 : value, 2, 1);
				},
				totalSummary: {
					align : 'left',
                    render: function (suminf, column, cell) {
                        return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, 2, 1)+ '</div>';
                    }
                }
			}, {
				display : '单价', name : 'price', width : 100, align : 'right',
				render : function(rowdata, rowindex, value) {
					return formatNumber(value == null ? 0 : value, '${p08006 }', 1);
				}
			}, {
				display : '备注', name : 'note', width : 160, align : 'left',
			}, {
				display : '是否关闭', name : 'is_closed', width : 60, align : 'left',
				render : function(rowdata, rowindex, value) {
					return value ? "是" : "否";
				},
			} ],
			dataAction : 'server', dataType : 'server', usePager : false, width : '100%', height : '92%',
			url : 'queryMedCommonOutApplyCheckDetail.do?isCheck=false',
			checkbox : true, enabledEdit : false, alternatingRow : true,
			onsuccess:function(){
			
				//is_addRow();
			},
			rowClsRender:function (rowdata) {
				 if(rowdata.imme_amount < rowdata.app_amount) {
					return "notEditor";
				} 
			},
			isScroll : true, rownumbers : true, delayLoad : true,//初始化明细数据
			selectRowButtonOnly : true,//heightDiff: -10,
			isChecked : is_checked, 
			checkBoxDisplay : isCheckDisplay,
			toolbar : {
				items : [ {
					text : '关闭药品（<u>G</u>）', id : 'closeInv', click : closeInv, icon : 'close'
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
    function isCheckDisplay(rowdata) {
		if (rowdata.is_closed != null && rowdata.is_closed == 1){
			return false;
		}
		return true;
	}
    function is_checked(rowdata){
    	if(rowdata.is_closed){
    		return false;
    	}
    	return true;
    }
    
	//获取grid所有数据
	function getData(){
		var manager = $("#maingrid").ligerGetGridManager();
		var data = manager.getData();
		return JSON.stringify(data);
	} 
	
	//关闭药品
	function closeInv(){
		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
	 		/*校验grid---begin-----*/
	 		var targetMap = new HashMap();
			var amount = 0;
	 		$.each(data,function(i, v){
				if(v.app_amount != v.rela_amount){
					amount += v.app_amount - v.rela_amount;
				}
	 		});
	 		if(amount == 0){
	 			$.ligerDialog.warn("所选药品的申请数量已全部处理！");  
				return;
	 		}
	 		var ParamVo =[];
			$(data).each(function (){		
				ParamVo.push(
					this.group_id   +"@"+ 
					this.hos_id   +"@"+ 
					this.copy_code   +"@"+ 
					this.apply_id +"@"+ 
					this.detail_id
				) 
			});
			ajaxJsonObjectByUrl("updateMedCommonOutApplyCheckCloseInv.do?isCheck=false", {ParamVo : ParamVo.toString()}, function(responseData){
	            if(responseData.state=="true"){
					queryDetail();
	            }
			});
		}
	}
	
	//生成出库单
	function out(){
		var data = gridManager.getCheckedRows();
 		
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
	 		/*校验grid---begin-----*/
	 		var targetMap = new HashMap();
			var amount = 0;
	 		$.each(data,function(i, v){
				if(v.app_amount != v.rela_amount){
					amount += v.app_amount - v.rela_amount;
				}
	 		});
	 		if(amount == 0){
	 			$.ligerDialog.warn("所选药品的申请数量已全部处理！");  
				return;
	 		}
	 		/*校验grid---end-------*/
			var paras;
			$(data).each(function (index, vo){	
				if(index == 0){
					paras = "&group_id=" + vo.group_id
						+ "&hos_id=" + vo.hos_id
						+ "&copy_code=" + vo.copy_code
						+ "&apply_id=" + vo.apply_id
						+ "&type=out"
						+ "&is_add=1"
						+ "&detail_ids=" + vo.detail_id;
				}else{
					paras = paras + ',' + vo.detail_id;
				}
			});
			//验证库存
			ajaxJsonObjectByUrl("checkMedCommonOutApplyCheckForAdd.do?isCheck=false", paras, function(responseData){
	            if(responseData.state=="true"){
							$.ligerDialog.open({
								title: '生成出库单',
								height: $(window).height()-50,
								width: $(window).width()-100,
								url: 'addOutPage.do?isCheck=false' + paras.toString(),
								modal: true, showToggle: false, showMax: true, showMin: false, isResize: true, 
							});
	            }
			});
		}
	}
	
	//生成代销出库单
	function affiout(){
		var data = gridManager.getCheckedRows();
 		
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
	 		/*校验grid---begin-----*/
	 		var targetMap = new HashMap();
			var amount = 0;
	 		$.each(data,function(i, v){
				if(v.app_amount != v.rela_amount){
					amount += v.app_amount - v.rela_amount;
				}
	 		});
	 		if(amount == 0){
	 			$.ligerDialog.warn("所选药品的申请数量已全部处理！");  
				return;
	 		}
	 		/*校验grid---end-------*/
			var paras;
			$(data).each(function (index, vo){	
				if(index == 0){
					paras = "&group_id=" + vo.group_id
						+ "&hos_id=" + vo.hos_id
						+ "&copy_code=" + vo.copy_code
						+ "&apply_id=" + vo.apply_id
						+ "&type=affiOut"
						+ "&is_add=1"
						+ "&detail_ids=" + vo.detail_id;
				}else{
					paras = paras + ',' + vo.detail_id;
				}
			});
			//验证库存
			ajaxJsonObjectByUrl("checkMedCommonOutApplyCheckForAdd.do?isCheck=false", paras, function(responseData){
	            if(responseData.state=="true"){
					$.ligerDialog.confirm('确定生成代销出库单?', function (yes){
						if(yes){
							$.ligerDialog.open({
								title: '生成代销出库单',
								height: $(window).height()-50,
								width: $(window).width()-100,
								url: 'addAffiOutPage.do?isCheck=false' + paras.toString(),
								modal: true, showToggle: false, showMax: true, showMin: false, isResize: true, 
							});
						}
					}); 
	            }
			});
		}
	}
	//生成调拨单
	function tran(){
		var data = gridManager.getCheckedRows();
 		
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
	 		/*校验grid---begin-----*/
	 		var targetMap = new HashMap();
			var amount = 0;
	 		$.each(data,function(i, v){
				if(v.app_amount != v.rela_amount){
					amount += v.app_amount - v.rela_amount;
				}
	 		});
	 		if(amount == 0){
	 			$.ligerDialog.warn("所选药品的申请数量已全部处理！");  
				return;
	 		}
	 		/*校验grid---end-------*/
			var paras;
			$(data).each(function (index, vo){	
				if(index == 0){
					paras = "&group_id=" + vo.group_id
						+ "&hos_id=" + vo.hos_id
						+ "&copy_code=" + vo.copy_code
						+ "&apply_id=" + vo.apply_id
						+ "&dept_id=" + liger.get("apply_dept").getValue().split(",")[0]
						+ "&type=tran"
						+ "&is_add=1"
						+ "&detail_ids=" + vo.detail_id;
				}else{
					paras = paras + ',' + vo.detail_id;
				}
			});
			//验证仓库
	        ajaxJsonObjectByUrl("existsMedCommonOutApplyCheckStoreManage.do?isCheck=false", paras, function(responseData){
				if(responseData.state=="true"){
					//验证库存
					ajaxJsonObjectByUrl("checkMedCommonOutApplyCheckForAdd.do?isCheck=false", paras, function(responseData){
			            if(responseData.state=="true"){
			    			$.ligerDialog.confirm('确定生成调拨单?', function (yes){
			    				if(yes){
			    					$.ligerDialog.open({
			    						title: '生成调拨单',
										height: $(window).height()-50,
										width: $(window).width()-100,
			    						url: 'addTranPage.do?isCheck=false' + paras.toString(),
			    						modal: true, showToggle: false, showMax: true, showMin: false, isResize: true, 
			    					});
			    				}
			    			}); 
			            }
					});
				}
	        });
		}
	}
	//生成代销调拨单
	function affitran(){
		var data = gridManager.getCheckedRows();
 		
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
	 		/*校验grid---begin-----*/
	 		var targetMap = new HashMap();
			var amount = 0;
	 		$.each(data,function(i, v){
				if(v.app_amount != v.rela_amount){
					amount += v.app_amount - v.rela_amount;
				}
	 		});
	 		if(amount == 0){
	 			$.ligerDialog.warn("所选药品的申请数量已全部处理！");  
				return;
	 		}
	 		/*校验grid---end-------*/
			var paras;
			$(data).each(function (index, vo){	
				if(index == 0){
					paras = "&group_id=" + vo.group_id
						+ "&hos_id=" + vo.hos_id
						+ "&copy_code=" + vo.copy_code
						+ "&apply_id=" + vo.apply_id
						+ "&dept_id=" + liger.get("apply_dept").getValue().split(",")[0]
						+ "&type=affiTran"
						+ "&is_add=1"
						+ "&detail_ids=" + vo.detail_id;
				}else{
					paras = paras + ',' + vo.detail_id;
				}
			});
			//验证仓库
	        ajaxJsonObjectByUrl("existsMedCommonOutApplyCheckStoreManage.do?isCheck=false", paras, function(responseData){
	            if(responseData.state=="true"){
					//验证库存
					ajaxJsonObjectByUrl("checkMedCommonOutApplyCheckForAdd.do?isCheck=false", paras, function(responseData){
			            if(responseData.state=="true"){
							$.ligerDialog.confirm('确定生成代销调拨单?', function (yes){
								if(yes){
									$.ligerDialog.open({
										title: '生成代销调拨单',
										height: $(window).height()-50,
										width: $(window).width()-100,
										url: 'addAffiTranPage.do?isCheck=false' + paras.toString(),
										modal: true, showToggle: false, showMax: true, showMin: false, isResize: true, 
									});
								}
							}); 
			            }
					});
	            }
	        });
		}
	}
	//生成科室需求计划
	function plan(){
		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
				var paras;
				$(data).each(function (index, vo){	
					if(index == 0){
						paras = "&group_id=" + vo.group_id
							+ "&hos_id=" + vo.hos_id
							+ "&copy_code=" + vo.copy_code
							+ "&apply_id=" + vo.apply_id
							+ "&is_add=1"
							+ "&detail_ids=" + vo.detail_id;
					}else{
						paras = paras + ',' + vo.detail_id;
					}
				});
				
						$.ligerDialog.open({
							title: '生成科室需求计划',
							height: $(window).height()-50,
							width: $(window).width()-100,
							url: 'addReqPage.do?isCheck=false' + paras.toString(),
							modal: true, showToggle: false, showMax: true, showMin: false, isResize: true, 
						});
				
		}
	}
function is_exists(){
	
	var formPara={
			 apply_id :'${medApplyMain.apply_id}' 
	 };
	//已经生成科室需求计划的明细 暂时处理方式  按钮置灰 后期需要改造 
	ajaxJsonObjectByUrl("queryMedOutRequirelExists.do?isCheck=false", formPara, function(responseData){
	            	if(responseData.flag=="1"){
	            		$.ligerDialog.warn('已经生成科室计划不允许重复生成');
	            		return false;
	                }else{
	                	plan();
	                	return true;
	                }
	});
	
}
	//打印
	function print(){
		var useId=0;//统一打印
		//if('${sessionScope.med_para_map["08017"] }'==1){
			//按用户打印
			useId='${sessionScope.user_id }';
		/* }else if('${sessionScope.med_para_map["08017"] }'==2){
			//按仓库打印
			if(liger.get("store_code").getValue()==""){
				$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
				return;
			}
			useId=liger.get("store_code").getValue().split(",")[0];
		} */
		
		/* var para={
			apply_id:$("#apply_id").val(),
			template_code:'08013',
			p_num:0,
			use_id:useId
		};
		
		printTemplate("queryMedOutByPrintTemlate.do?isCheck=false",para); */
		var para={
    			template_code:'08013',
    			class_name:"com.chd.hrp.med.serviceImpl.storage.out.MedCommonOutApplyCheckServiceImpl",
    			method_name:"queryMedOutByPrintPage",
    			//isSetPrint:flag,//是否套打，默认非套打
    			isPreview:true,//是否预览，默认直接打印
    			apply_id :$("#apply_id").val(),
    			use_id:useId,
    			p_num:0
    	};
    	
    	officeFormPrint(para);
	}
	//打印设置
	function printSet(){
		
		
		var useId=0;//统一打印
		//if('${sessionScope.med_para_map["08017"] }'==1){
			//按用户打印
			useId='${sessionScope.user_id }';
		/* }else if('${sessionScope.med_para_map["08017"] }'==2){
			//按仓库打印
			if(liger.get("store_code").getValue()==""){
				$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
				return;
			}
			useId=liger.get("store_code").getValue().split(",")[0];
		} */
		
		/* parent.parent.$.ligerDialog.open({url : 'hrp/med/storage/out/applycheck/storageOutPrintSetPage.do?template_code=08013&use_id='+useId,
			data:{}, height: $(parent).height(),width: $(parent).width(), title:'打印模板设置',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
		}); */
		officeFormTemplate({template_code:"08013",useId:useId});
	}
	
	//关闭当前弹出框
	function this_close(){
		frameElement.dialog.close();
	}
	//刷新父页面
	function refreshParent(){
		parentFrameUse().query();
	}
    </script>
  
</head>
  
<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post"  id="form1" >
		<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
			<tr>
	            <td style="display: none;">
	            	<input name="group_id" type="text" id="group_id" value="${medApplyMain.group_id}" ltype="text" />
	            	<input name="hos_id" type="text" id="hos_id" value="${medApplyMain.hos_id}" ltype="text" />
	            	<input name="copy_code" type="text" id="copy_code" value="${medApplyMain.copy_code}" ltype="text" />
	            	<input name="apply_id" type="hidden" id="apply_id" value="${medApplyMain.apply_id}" ltype="text" />
	            </td>
			</tr>
	        <tr>
	            <td align="right" class="l-table-edit-td" >
	            	申请单号：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="apply_no" type="text" id="apply_no" value="${medApplyMain.apply_no}" ltype="text" disabled="disabled"/>
	            </td>
	            <td align="right" class="l-table-edit-td" > 
	            	申请日期：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input class="Wdate" name="app_date" id="app_date" type="text" value="${medApplyMain.app_date}" required="true" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" validate="{required:true}"  disabled="disabled"/>
	            </td>
	            <td align="right" class="l-table-edit-td"  >
	            	响应仓库：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="store_code" type="text" id="store_code" ltype="text" required="true" validate="{required:true}" />
	            </td>
	        </tr> 
	        <tr>
	            <td align="right" class="l-table-edit-td"  >
	            	申请部门：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="apply_dept" type="text" id="apply_dept" ltype="text" required="true" validate="{required:true}" />
	            </td>
	            <td align="right" class="l-table-edit-td"  >
	            	申&nbsp;领&nbsp;人：
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
	            <td></td>
	        </tr> 
			<tr>
	            <td align="right" class="l-table-edit-td" >
					摘&nbsp;&nbsp;&nbsp;&nbsp;要：
	            </td>
	            <td align="left" class="l-table-edit-td" colspan="5">
	            	<input name="brief" type="text" id="brief" ltype="text" value="${medApplyMain.brief}" validate="{required:false,maxlength:50}" disabled="disabled"/>
	            </td>
			</tr>
	    </table>
    </form>
    <div style="width: 100%; height: 100%;">
		<div id="maingrid"></div>
		<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%"  style="margin-top: 5px;">
			<tr>	
				<td align="center" class="l-table-edit-td" >
					<button id ="out" accessKey="O"><b>生成出库单（<u>O</u>）</b></button>
					&nbsp;&nbsp;
					<button id ="affiout" accessKey="G"><b>生成代销出库单（<u>G</u>）</b></button>
					&nbsp;&nbsp;
					<button id ="tran" accessKey="M"><b>生成调拨单（<u>M</u>）</b></button>
					&nbsp;&nbsp;
					<button id ="affitran" accessKey="L"><b>生成代销调拨单（<u>L</u>）</b></button>
				</td>
			</tr>
			<tr>	
				<td align="center" class="l-table-edit-td" >
					<button id ="plan" accessKey="Y"><b>生成科室需求计划（<u>Y</u>）</b></button>
					&nbsp;&nbsp;
					<button id ="print" accessKey="P"><b>打印（<u>P</u>）</b></button>
					&nbsp;&nbsp; 
					<button id ="printSet" accessKey="M"><b>打印模板（<u>S</u>）</b></button>
					&nbsp;&nbsp;
					<button id ="close" accessKey="C"><b>关闭（<u>C</u>）</b></button>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>
