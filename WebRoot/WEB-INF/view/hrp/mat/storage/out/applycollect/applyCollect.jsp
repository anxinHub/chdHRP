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
<script src="<%=path%>/lib/hrp/mat/mat.js"	type="text/javascript"></script>
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    var is_com;
	var store_dept = "";
    var renderFunc = {
			price:function(value){//单价
				return formatNumber(value, '${p04005 }', 1);
			},
			amount_money:function(value){//金额
				return formatNumber(value, '${p04005 }', 1);
			},
			app_amount:function(value){//请领数量
				return formatNumber(value==null?0:value,2,1);
			},
			rela_amount:function(value){//已处理数量
				return formatNumber(value==null?0:value,2,1);
			},
			cur_amount:function(value){//库存数量
				return formatNumber(value==null?0:value,2,1);
			},
			imme_amount:function(value){//即时库存数量
				return formatNumber(value==null?0:value,2,1);
			},
			is_com:function(value){//是否代销
				if(value == 1){
 					return "是";	
 				}else if(value == 0){
 					return "否";
 				} 
			} ,
			is_sec_whg:function(value){//是否转库
				if(value == 1){
 					return "是";	
 				}else if(value == 0){
 					return "否";
 				} 
			}  ,
			do_state:function(value){//处理状态
				if(value == 1){
 					return "未完成";	
 				}else if(value == 2){
 					return "已完成";
 				}
			} 
	}; 
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
		 loadHotkeys();
		 //query();
    });
    //查询
    function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
        //根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'begin_app_date',
			value : $("#begin_app_date").val()
		});
		grid.options.parms.push({
			name : 'end_app_date',
			value : $("#end_app_date").val()
		}); 
		grid.options.parms.push({
			name : 'dept_id',
			value : liger.get("apply_dept").getValue() == null ? "" : liger.get("apply_dept").getValue().split(",")[0]
		}); 
		grid.options.parms.push({
			name : 'brief',
			value : $("#brief").val()
		}); 
		grid.options.parms.push({
			name : 'store_id',
			value : liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[0]
		});
		grid.options.parms.push({
			name : 'do_state',
			value : liger.get("do_state").getValue()
		});
		grid.options.parms.push({
			name : 'apply_no',
			value : $("#apply_no").val()
		}); 
		grid.options.parms.push({
			name : 'inv_code',
			value : $("#inv_code").val()
		}); 
    	is_com = $("input[name='is_com']:checked").val();
		grid.options.parms.push({
			name : 'is_com',
			value : is_com
		}); 
		grid.options.parms.push({
			name : 'is_bar',
			value : $("#is_bar").prop("checked") ? 1 : 0
		}); 
		grid.options.parms.push({
			name : 'is_enough',
			value : $("input[name='is_enough']:checked").val()
		}); 
		grid.options.parms.push({
			name : 'response_emp',
			value : liger.get("response_emp").getValue() == null ? "" : liger.get("response_emp").getValue()
		}); 
		
 		
    	//加载查询条件
    	grid.loadData(grid.where);
    	store_dept = "";
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [{
					display: '申请单号', name: 'apply_no', align: 'left', width: 110,
					render : function(rowdata, rowindex, value) {
						if(value == '合计'){
							return value;
						}
						return '<a href=javascript:update_open("' 
							+ rowdata.group_id 
							+ ',' + rowdata.hos_id 
							+ ',' + rowdata.copy_code 
							+ ',' + rowdata.apply_id
							+ '")>'+rowdata.apply_no+'</a>';
					}
				}, { 
		 			display: '申请日期', name: 'app_date', align: 'left', width: 130,
		 		}, { 
		 			display: '响应库房', name: 'store_name', align: 'left', width: 80,
		 		},{ 
		 			display: '部门', name: 'dept_name', align: 'left', width: 110,
		 		},{ 
		 			display: '申领人', name: 'emp_name', align: 'left', width: 80,
		 		} ,{ 
		 			display: '响应人', name: 'response_name', align: 'left', width: 80,
		 		} ,{ 
		 			display: '摘要', name: 'brief', align: 'left', width: 200,
		 		},{
					display : '是否转库', name : 'is_sec_whg', width : 50, align : 'left',
					render : function(rowdata, rowindex, value) {
						if(value == null){
							return "";
						}
						return value == 1 ? '是' : '否';
					}
				},{ 
		 			display: '材料编码', name: 'inv_code', align: 'left', width: 100,
		 		},{ 
		 			display: '交易编码', name: 'bid_code', align: 'left', width: 100,
		 		},{ 
		 			display: '材料名称', name: 'inv_name', align: 'left', width: 140,
		 		},{ 
		 			display: '规格型号', name: 'inv_model', align: 'left', width: 140,
		 		},{ 
		 			display: '单位', name: 'unit_name', align: 'left', width: 50,
		 		},{ 
		 			display : '请领数量', name : 'app_amount', width : 80,  align : 'right',
					
					render : function(rowdata, rowindex, value) { 
						return value == null ? "" : formatNumber(value, 2, 1);
					},
		 		},{ 
		 			display : '已处理', name : 'rela_amount', width : 80,  align : 'right',
					render : function(rowdata, rowindex, value) {
						return value == null ? "" : formatNumber(value, 2, 1);
					},
		 		},{ 
		 			display : '采购数量', name : 'pur_amount', width : 80,  align : 'right',
					render : function(rowdata, rowindex, value) {
						return value == null ? "" : formatNumber(value, 2, 1);
					},
		 		},{ 
		 			display : '需求数量', name : 'req_amount', width : 80,  align : 'right',
					render : function(rowdata, rowindex, value) {
						return value == null ? "" : formatNumber(value, 2, 1);
					},
		 		},{ 
		 			display : '库存', name : 'cur_amount', width : 80,  align : 'right',
					render : function(rowdata, rowindex, value) {
						return value == null ? "" : formatNumber(value, 2, 1);
					},
		 		},{ 
		 			display : '即时库存', name : 'imme_amount', width : 80,  align : 'right',
					render : function(rowdata, rowindex, value) {
						return value == null ? "" : formatNumber(value, 2, 1);
					},
		 		},{ 
		 			display : '单价', name : 'price', width : 90, align : 'right', 
					render : function(rowdata, rowindex, value) {
						return value == null ? "" : formatNumber(value, '${p04006 }', 1);
					}
		 		},{ 
		 			display : '金额', name : 'amount_money', width : 120, align : 'right',
					render : function(rowdata, rowindex, value) {
						return value == null ? "" : formatNumber(value, '${p04005 }', 1);
					},
		 		},{ 
		 			display : '是否代销', name : 'is_com', width : 80, align : 'left',
					render : function(rowdata, rowindex, value) {
						if(value == null){
							return "";
						}
						return value == 1 ? '是' : '否';
					}
		 		}, {
					display : '备注(E)', name : 'note', width : 80, align : 'left',
		 		}, { 
		 			display: '供应商', name: 'sup_name', align: 'left', width: 150,
		 		},{ 
		 			display: '生产厂商', name: 'fac_name', align: 'left', width: 150,
		 		},{ 
		 			display: '处理状态', name: 'do_state', align: 'left', width: 80,
		 			render: function(rowdata, rowindex, value){
		 				if(value == 1){
		 					return "未完成";	
		 				}else if(value == 2){
		 					return "已完成";
		 				}
		 			}
		 		}],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatCommonOutApplyCollect.do?isCheck=false',
			width: '100%', height: '100%', checkbox: true,rownumbers:true,pageSize:200,
			delayLoad: true,//初始化不加载，默认false
			selectRowButtonOnly:true,//heightDiff: -10,
			checkBoxDisplay:isCheckDisplay,
			toolbar: { items: [
				{ text: '查询（<u>Q</u>）', id:'search', click: query, icon:'search' },
				{ line:true },
				{ text: '退回（<u>B</u>）', id:'back', click: back, icon:'back' },
				{ line:true }, 
				{ text: '生成出库单（<u>O</u>）', id:'out', click: out, icon:'settle' },
				{ line:true },
				{ text: '生成调拨单（<u>T</u>）', id:'tran', click: tran, icon:'settle' },
				{ line:true },
				{ text: '生成请购单（<u>P</u>）', id:'batchPur', click: batchPur, icon:'settle' },
				{ line:true },
				{ text: '请购单处理（<u>M</u>）', id:'batchPurPage', click: batchPurPage, icon:'settle' }, 
				{ line:true },
				{ text: '生成科室需求（<u>R</u>）', id:'req', click: req, icon:'settle' },
				{ line:true },
				{ text: '批量生成科室需求（<u>H</u>）', id:'batchReq', click: batchReq, icon:'settle' },
				{ line:true },
				{ text: '关闭行（<u>D</u>）', id:'closeInv', click: closeInv, icon:'close' },
				{ line:true },
				{ text: '查看关闭材料（<u>V</u>）', id:'queryColseInv', click: queryColseInv, icon:'query' }, 
			]},  
			onDblClickRow : function (rowdata, rowindex, value){
				if(rowdata.apply_id == null){
					$.ligerDialog.warn('请选择数据 ');
					return ; 
				}
				changeDept(rowdata);
				/* update_open(
					rowdata.group_id + "," + 
					rowdata.hos_id + "," + 
					rowdata.copy_code + "," + 
					rowdata.apply_id 
				); */
			}, 
			onBeforeCheckRow : function(checked, rowdata, rowindex, rowobj){
				if(checked){
					/* if(rowdata.store_id == '0'){
						$.ligerDialog.warn("材料【"+rowdata.inv_name+"】没有维护默认申领库房");
						return false;
					} */
					/* var para = rowdata.store_id+","+rowdata.dept_id;
					if(store_dept != ""){
						if(store_dept != para){
							$.ligerDialog.warn("所选材料响应库房与领用科室需一致");
							return false;
						}
					}else{
						store_dept = para;
					} */
				}
				return true;
			},
			/* onCheckRow : function(checked, rowdata, rowindex, rowobj){
				if(checked){//选中
					
				}else{//取消选中
					//如果主表数据未选中任何行，则清空判断仓库科室一致的参数'store_dept'
					if(gridManager.getSelectedRows().length == 0){
						store_dept = "";
					}
				}
			}, */
		});

        gridManager = $("#maingrid").ligerGetGridManager();
        grid.options.lodop.fn=renderFunc;
    }
    
    //打印回调方法
    function lodopPrint(){
    	var head="<table class='head' width='100%'><tr><td>单位：${sessionScope.hos_name}</td></tr>";
 		head=head+"<tr><td>申请日期："+$("#begin_app_date").val() +" 至  "+ $("#end_app_date").val()+"</td></tr>";
 		head=head+"</table>";
 		grid.options.lodop.head=head; 
 		grid.options.lodop.fn=renderFunc;
 		grid.options.lodop.title="科室申领汇总";
    }
    
    function isCheckDisplay(rowdata){
       	if(rowdata.app_date == null) return false;
         return true;
    }
    
    function changeDept(rowdata){		

		liger.get("store_code").setValue(rowdata.store_id+","+rowdata.store_no);
		liger.get("store_code").setText(rowdata.store_code+" "+rowdata.store_name);
		
		liger.get("apply_dept").setValue(rowdata.dept_id+","+rowdata.dept_no);
		liger.get("apply_dept").setText(rowdata.dept_code+" "+rowdata.dept_name);
		
		query();
    }
    
    //键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('B', back);
		hotkeys('D', closeInv);
		hotkeys('O', out);
		hotkeys('T', tran);
		hotkeys('R', req);
		hotkeys('H', batchReq);
		hotkeys('P', batchPur);
		hotkeys('V',queryColseInv);
	}
    //查看关闭的材料
    function queryColseInv(){
    	parent.$.ligerDialog.open({
			title: '查看关闭的材料',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/mat/storage/out/applycollect/colseInvPage.do?isCheck=false',
			modal: true, showToggle: false, showMax: true, showMin: true, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});   
    }
    
    //修改
    function update_open(obj){		
		var vo = obj.split(",");
		var paras = 
			"group_id="+vo[0] +"&"+ 
			"hos_id="+vo[1] +"&"+ 
			"copy_code="+vo[2] +"&"+ 
			"apply_id="+vo[3] ;
		
		parent.$.ligerDialog.open({
			title: '申请单修改',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/mat/storage/out/applynstore/updatePage.do?isCheck=false&' + paras.toString(),
			modal: true, showToggle: false, showMax: true, showMin: true, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});   
    }
	
    //退回
	function back(){
		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
			var ParamVo =[];
			var apply_nos = "";
			$(data).each(function (){		
				if(this.rela_state > 1){
					apply_nos = apply_nos + this.apply_no + "<br>";
				}
				ParamVo.push(
					this.group_id   +"@"+ 
					this.hos_id   +"@"+ 
					this.copy_code   +"@"+ 
					this.apply_id   +"@"+ 
					this.detail_id 
				) 
			});
			if(apply_nos != ""){
				$.ligerDialog.error("操作失败！<br>以下单据存在已处理数据不允许退回：<br>"+apply_nos);
				return;
			}
			
			$.ligerDialog.prompt('退回原因', true, function (yes, value){
				if(yes){
					if(!value){
						$.ligerDialog.error("请填写退回原因！");
						return false;
					}
					var paras = {
						back_reason : value,
						ids : ParamVo.toString()
					}
					ajaxJsonObjectByUrl("backMatCommonOutApplyCollect.do?",paras,function (responseData){
						if(responseData.state=="true"){
							query();
						}
					});
				}
			}); 
		}
	}
    
	//获取grid所有数据
	function getData(){
		var manager = $("#maingrid").ligerGetGridManager();
		var data = manager.getData();
		return JSON.stringify(data);
	} 
	
	//关闭材料
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
	 			$.ligerDialog.warn("所选材料的申请数量已全部处理！");  
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
			$.ligerDialog.confirm('确定要关闭单据中的材料？', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("../applycheck/updateMatCommonOutApplyCheckCloseInv.do?isCheck=false", {ParamVo : ParamVo.toString()}, function(responseData){
			            if(responseData.state=="true"){
							query();
			            }
					});
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
			var flag = false;
			var store_dept = "";
	 		$.each(data,function(i, v){
				if(v.app_amount != v.rela_amount){
					amount += v.app_amount - v.rela_amount;
				}
				if(store_dept != "" && store_dept != v.store_id + "," + v.dept_id){
					flag = true;
					return;
				}
				store_dept = v.store_id + "," + v.dept_id;
	 		});
	 		if(flag){
	 			$.ligerDialog.warn("所选材料响应库房与领用科室需一致！");  
				return;
	 		}
	 		if(amount == 0){
	 			$.ligerDialog.warn("所选材料的申请数量已全部处理！");  
				return;
	 		}
	 		/*校验grid---end-------*/
			var paras;
			var apply_ids;
			var detail_ids;
			$(data).each(function (index, vo){	
				if(index == 0){
					paras = "&group_id=" + vo.group_id
						+ "&hos_id=" + vo.hos_id
						+ "&copy_code=" + vo.copy_code
						+ "&store_id=" + vo.store_id
						+ "&type=" + (is_com == '1' ? "affiOut" : "out");
					apply_ids = vo.apply_id;
					detail_ids = vo.detail_id;
				}else{
					apply_ids += "," + vo.apply_id;
					detail_ids += "," + vo.detail_id;
				}
			});
			paras += "&apply_ids=" + apply_ids + "&detail_ids=" + detail_ids;
			//验证库存
			ajaxJsonObjectByUrl("checkMatCommonOutApplyCollectForAdd.do?isCheck=false", paras, function(responseData){
	            if(responseData.state=="true"){
					$.ligerDialog.confirm(is_com == '1' ? '确定生成代销出库单？' : '确定生成出库单？', function (yes){
						if(yes){
							parent.$.ligerDialog.open({
								title: is_com == '1' ? '生成代销出库单' : '生成出库单',
								height: $(window).height(),
								width: $(window).width(),
								url: 'hrp/mat/storage/out/applycollect/addOutPage.do?isCheck=false' + paras.toString(),
								modal: true, showToggle: false, showMax: true, showMin: false, isResize: true, 
								parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
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
			var flag = false;
			var store_dept = "";
	 		$.each(data,function(i, v){
				if(v.app_amount != v.rela_amount){
					amount += v.app_amount - v.rela_amount;
				}
				if(store_dept != "" && store_dept != v.store_id + "," + v.dept_id){
					flag = true;
					return;
				}
				store_dept = v.store_id + "," + v.dept_id;
	 		});
	 		if(flag){
	 			$.ligerDialog.warn("所选材料响应库房与领用科室需一致！");  
				return;
	 		}
	 		if(amount == 0){
	 			$.ligerDialog.warn("所选材料的申请数量已全部处理！");  
				return;
	 		}
	 		/*校验grid---end-------*/
			var paras;
			var apply_ids;
			var detail_ids;
			$(data).each(function (index, vo){	
				if(index == 0){
					paras = "&group_id=" + vo.group_id
						+ "&hos_id=" + vo.hos_id
						+ "&copy_code=" + vo.copy_code
						+ "&store_id=" + vo.store_id
						+ "&dept_id=" + vo.dept_id//liger.get("apply_dept").getValue().split(",")[0]
						+ "&type=" + (is_com == '1' ? "affiTran" : "tran");
					apply_ids = vo.apply_id;
					detail_ids = vo.detail_id;
				}else{
					apply_ids += "," + vo.apply_id;
					detail_ids += "," + vo.detail_id;
				}
			});
			paras += "&apply_ids=" + apply_ids + "&detail_ids=" + detail_ids;
			//验证仓库
	        ajaxJsonObjectByUrl("existsMatCommonOutApplyCollectStoreManage.do?isCheck=false", paras, function(responseData){
				if(responseData.state=="true"){
					//验证库存
					ajaxJsonObjectByUrl("checkMatCommonOutApplyCollectForAdd.do?isCheck=false", paras, function(responseData){
			            if(responseData.state=="true"){
			    			$.ligerDialog.confirm(is_com == '1' ? '确定生成代销调拨单？' : '确定生成调拨单？', function (yes){
			    				if(yes){
			    					parent.$.ligerDialog.open({
			    						title: is_com == '1' ? '生成代销调拨单' : '生成调拨单',
										height: $(window).height()-50,
										width: $(window).width()-100,
			    						url: 'hrp/mat/storage/out/applycollect/addTranPage.do?isCheck=false' + paras.toString(),
			    						modal: true, showToggle: false, showMax: true, showMin: false, isResize: true, 
				    					parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
			    					});
			    				}
			    			}); 
			            }
					});
				}else if(responseData.state=="false"){
					$.ligerDialog.confirm("该科室无对应的仓库是否继续生成调拨单据？", function (yes){
						if(yes){	
							//验证库存
							ajaxJsonObjectByUrl("checkMatCommonOutApplyCollectForAdd.do?isCheck=false", paras, function(responseData){
					            if(responseData.state=="true"){
					    			$.ligerDialog.confirm(is_com == '1' ? '确定生成代销调拨单？' : '确定生成调拨单？', function (yes){
					    				if(yes){
					    					parent.$.ligerDialog.open({
					    						title: is_com == '1' ? '生成代销调拨单' : '生成调拨单',
												height: $(window).height()-50,
												width: $(window).width()-100,
					    						url: 'hrp/mat/storage/out/applycollect/addTranPage.do?isCheck=false' + paras.toString(),
					    						modal: true, showToggle: false, showMax: true, showMin: false, isResize: true, 
						    					parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
					    					});
					    				}
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
	function req(){
		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
				var paras;
				var apply_ids;
				var detail_ids;
				var flag = false;
				var store_dept = "";
				
				$(data).each(function (index, vo){
					if(store_dept != "" && store_dept != vo.store_id + "," + vo.dept_id){
						flag = true;
						return;
					}
					store_dept = vo.store_id + "," + vo.dept_id;
					
					if(index == 0){
						paras = "&group_id=" + vo.group_id
							+ "&hos_id=" + vo.hos_id
							+ "&copy_code=" + vo.copy_code
							+ "&store_id=" + vo.store_id;
						apply_ids = vo.apply_id;
						detail_ids = vo.detail_id;
					}else{
						apply_ids += "," + vo.apply_id;
						detail_ids += "," + vo.detail_id;
					}
				});
				if(flag){
		 			$.ligerDialog.warn("所选材料响应库房与领用科室需一致！");  
					return;
				}
				paras += "&apply_ids=" + apply_ids + "&detail_ids=" + detail_ids;
				
						parent.$.ligerDialog.open({
							title: '生成科室需求计划',
							height: $(window).height()-50,
							width: $(window).width()-100,
							url: 'hrp/mat/storage/out/applycollect/addReqPage.do?isCheck=false' + paras.toString(),
							modal: true, showToggle: false, showMax: true, showMin: false, isResize: true, 
	    					parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
						});
				
		}
	}
	
	//汇总生成科室需求计划
	function batchReq(){
		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
			var paras;
			var apply_ids;
			var detail_ids;
			$(data).each(function (index, vo){	
				if(index == 0){
					paras = "&group_id=" + vo.group_id
						+ "&hos_id=" + vo.hos_id
						+ "&copy_code=" + vo.copy_code
						+ "&store_id=" + vo.store_id;
					apply_ids = vo.apply_id;
					detail_ids = vo.detail_id;
				}else{
					apply_ids += "," + vo.apply_id;
					detail_ids += "," + vo.detail_id;
				}
			});
			paras += "&apply_ids=" + apply_ids + "&detail_ids=" + detail_ids;
			$.ligerDialog.confirm('确定汇总生成科室需求计划？', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("addMatReqByApplyCollect.do?isCheck=false", paras, function(responseData){
			            if(responseData.state=="true"){
							query();
			            }
					});
				}
			});	
		}
	}
	
	//汇总生成采购计划
	function batchPur(){
		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
			var paras;
			var apply_ids;
			var detail_ids;
			var type_code;
			$(data).each(function (index, vo){	
				if(index == 0){
					paras = "&group_id=" + vo.group_id
						+ "&hos_id=" + vo.hos_id
						+ "&copy_code=" + vo.copy_code
						+ "&store_id=" + vo.store_id;
					apply_ids = vo.apply_id;
					detail_ids = vo.detail_id;
				}else{
					apply_ids += "," + vo.apply_id;
					detail_ids += "," + vo.detail_id;
				}
				
				if(vo.type_code!='01'){
					type_code='1';
				}else{
					type_code='0';
				}
				
			});
			paras += "&apply_ids=" + apply_ids + "&detail_ids=" + detail_ids;
			
			if(type_code=='0'){
				$.ligerDialog.confirm('确定汇总生成采购计划？', function (yes){
					if(yes){
						ajaxJsonObjectByUrl("addMatPurByApplyCollect.do?isCheck=false", paras, function(responseData){
				            if(responseData.state=="true"){
								query();
				            }
						});
					}
				});
			}else{
				$.ligerDialog.open({
					url: 'matPurByApplyCollectStorePage.do?isCheck=false&'+ paras.toString(), height: 250,width:450, 
					title:'采购库房',modal: true, showToggle: false,initShowMax:true, showMin: false, isResize: true,
					buttons: [ 
					           { text: '确定', onclick: function (item, dialog) { 
	  				        	   	dialog.frame.savePurCollect();
	  				        	   		//dialog.close(); 
					        	    },cls:'l-dialog-btn-highlight' }, 
					           { text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
					         ] 
				});
			}
			
			
				
		}
	}
	
    //处理请购单
    function batchPurPage(){
    	parent.$.ligerDialog.open({
			title: '处理请购单',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/mat/storage/out/applycollect/applyInvCollectPage.do?isCheck=false',
			modal: true, showToggle: false, showMax: true, showMin: true, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});   
    }
    
    function loadDict(){
		//字典下拉框
		autocomplete("#apply_dept", "../../../queryMatDeptDictDate.do?isCheck=false", "id", "text", true, true, {is_last: 1,read_or_write:'1'});
		autoCompleteByData("#do_state", matApplyDetail_doState.Rows, "id", "text", true, true,null,null,'1');
		autocomplete("#store_code", "../../../queryMatStoreDictDate.do?isCheck=false", "id", "text", true, true,{is_write:'1'},false);
		autocomplete("#response_emp", "../../../queryMatEmp.do?isCheck=false", "id", "text", true, true,"",false);
		$("#begin_app_date").ligerTextBox({width:100});
    	autodate("#begin_app_date", "yyyy-mm-dd", "before_month");
        $("#end_app_date").ligerTextBox({width:100});
        autodate("#end_app_date", "yyyy-mm-dd", "new");
        $("#brief").ligerTextBox({width:238});
        $("#apply_no").ligerTextBox({width : 160});
        $("#inv_code").ligerTextBox({width : 238});
        $("#is_com input:radio").ligerRadio();
        $("#is_enough input:radio").ligerRadio();
	} 
	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" >
        <tr>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  width="10%">
            	申请日期：
            </td>
            <td align="left" class="l-table-edit-td"  width="20%">
				<table>
					<tr>
						<td align="left" >
							<input class="Wdate" name="begin_app_date" id="begin_app_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
						<td align="right" class="l-table-edit-td"  >
							至：
						</td>
						<td align="left" class="l-table-edit-td">
							<input class="Wdate" name="end_app_date" id="end_app_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
            		</tr>
				</table>
	        </td>
			<td align="right" class="l-table-edit-td"  width="14%">
				申请科室：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="apply_dept" type="text" id="apply_dept" ltype="text" validate="{required:false}" />
            </td>
        	<td align="right" class="l-table-edit-td" width="14%">
            	响应库房：
            </td>
            <td align="left" class="l-table-edit-td" width="20%">
				<input name="store_code" type="text" id="store_code" ltype="text" validate="{required:false}" />
	        </td>
        </tr> 
        <tr>
			<td align="right" class="l-table-edit-td" >
				摘要：
			</td>
            <td align="left" class="l-table-edit-td" >
            	<input name="brief" type="text" id="brief" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
			<td align="right" class="l-table-edit-td" >
				状态：
			</td>
            <td align="left" class="l-table-edit-td">
            	<input name="do_state" type="text" id="do_state" ltype="text" validate="{required:false}" />
            </td>
			<td align="right" class="l-table-edit-td" >
				单据号：
			</td>
			<td align="left" class="l-table-edit-td">
				<input name="apply_no" type="text" id="apply_no" ltype="text" validate="{required:false,maxlength:100}" />
			</td>
        </tr> 
        <tr>
        	<td align="right" class="l-table-edit-td" >
				材料信息：
			</td>
			<td align="left" class="l-table-edit-td">
				<input name="inv_code" type="text" id="inv_code" ltype="text" validate="{required:false,maxlength:100}" />
			</td>
        	<td align="right" class="l-table-edit-td" >
				处理类别：
			</td>
			<td id="is_com" align="left" class="l-table-edit-td">
				<input type="radio" name="is_com" value="0" checked="checked" />常备
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    			<input type="radio" name="is_com" value="1" />代销
    			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    			<input name="is_bar" type="checkbox" id="is_bar" ltype="text" />只显示条码材料
			</td>
        	<td align="right" class="l-table-edit-td" >
				库存状态：
			</td>
			<td id="is_enough" align="left" class="l-table-edit-td">
				<input type="radio" name="is_enough" value="1" checked="checked" />充足
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    			<input type="radio" name="is_enough" value="0" />零库存
			</td>
        </tr>
        <tr>
        	<td align="right" class="l-table-edit-td" >
				响应人：
			</td>
			<td align="left" class="l-table-edit-td">
				<input name="response_emp" type="text" id="response_emp" ltype="text" validate="{required:false,maxlength:100}" />
			</td>
	    </tr>
    </table>
	<div id="maingrid"></div>
</body>
</html>
