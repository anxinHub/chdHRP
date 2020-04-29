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
<script src="<%=path%>/lib/hrp/med/med.js"	type="text/javascript"></script>
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    var show_detail=0;
    var renderFunc = {
			state:function(value){//状态 
				if(value == ""){
					return "";
				}else 
					if(value == 0){
 						return "验收";
 					}else if(value == 1){
 						return "未审核";
 					}else if(value == 2){
 						return "审核";
 					}else if(value == 3){
 						return "入库确认";
 					}else {
 						return "财务记账";
 					}
			  
			} ,
			amount_money:function(value){//金额
				return formatNumber(value, '${p08005 }', 1);
			},
    
	}; 
    
    $(function (){
        loadDict()//加载下拉框
    	//加载数据
    	 //loadHead(null);	
		 loadHotkeys();
		 showDetail();
		 show_detail = $("#show_detail").is(":checked") ? 1 : 0 ;
		 //query();
    });
    //查询
    function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
        //根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'begin_in_date',
			value : $("#begin_in_date").val()
		});
		grid.options.parms.push({
			name : 'end_in_date',
			value : $("#end_in_date").val()
		});
		grid.options.parms.push({
			name : 'store_id',
			value : liger.get("store_id").getValue() == null ? "" : liger.get("store_id").getValue().split(",")[0]
		}); 
		grid.options.parms.push({
			name : 'state',
			value : liger.get("state").getValue() == null ? "" : liger.get("state").getValue()
		}); 
		grid.options.parms.push({
			name : 'begin_confirm_date',
			value : $("#begin_confirm_date").val()
		});
		grid.options.parms.push({
			name : 'end_confirm_date',
			value : $("#end_confirm_date").val()
		}); 
		grid.options.parms.push({
			name : 'sup_id',
			value : liger.get("sup_id").getValue() == null ? "" : liger.get("sup_id").getValue().split(",")[0]
		});
		grid.options.parms.push({
			name : 'dept_id',
			value : liger.get("dept_id").getValue() == null ? "" : liger.get("dept_id").getValue().split(",")[0]
		});
		grid.options.parms.push({
			name : 'special_no',
			value : $("#special_no").val()
		});
		grid.options.parms.push({
			name : 'bill_no',
			value : $("#bill_no").val()
		});
		grid.options.parms.push({
			name : 'brief',
			value : $("#brief").val()
		});
		grid.options.parms.push({
			name : 'come_from',
			value : liger.get("come_from").getValue() == null ? "" : liger.get("come_from").getValue()
		});
		
		/* grid.options.parms.push({
			name : 'bus_type_code',
			value : liger.get("bus_type_code").getValue() == null ? "" : liger.get("bus_type_code").getValue()
		}); */
		
		if(show_detail == 1){
	  		grid.options.parms.push({name:'inv_code',value:$("#inv_code").val()});
	  		grid.options.parms.push({name:'med_type_code',value:liger.get("med_type_code").getValue().split(",")[2]});
	  		grid.options.parms.push({name:'fac_id',value:liger.get("fac_id").getValue().split(",")[0]});
	  	}
		grid.options.parms.push({
			name : 'bill_state',
			value : liger.get("bill_state").getValue() == null ? "" : liger.get("bill_state").getValue()
		});
		
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	if(show_detail=="1"){
    		grid = $("#maingrid").ligerGrid({
    			columns: [
    			     {display: '单据号', name: 'special_no', align: 'left', width: 130,
    						render : function(rowdata, rowindex, value) {
    							if(value == '合计'){
    								return value ; 
    							}
    							return '<a href=javascript:update_open("' 
    								+ rowdata.group_id 
    								+ ',' + rowdata.hos_id 
    								+ ',' + rowdata.copy_code 
    								+ ',' + rowdata.special_id
    								+ ',' + rowdata.special_no+'")>'+rowdata.special_no+'</a>';
    						}
    					},
    				{display: '单据来源', name: 'come_from', align: 'left',width: 140,},
    				{display: '金额', name: 'amount_money', align: 'right',width: 100,
			 			render : function(rowdata, rowindex, value) {
			 				if(rowdata.amount_money == null){
			 					return "";
			 				}
							return formatNumber(rowdata.amount_money ==null ? 0 : rowdata.amount_money, '${p08005}', 1);
						}
		 			}, 
		 			{display: '单据状态', name: 'state', align: 'left',width: 140,hide:true
		 				
		 			},
					{display: '状态', name: 'state_name', align: 'left',width: 140,
		 				
		 			},
    		 		{display: '发票号', name: 'bill_no', align: 'left',width: 160 },
    				{display: '仓库', name: 'store_name', align: 'left',width: 120,},
    				{display: '药品编码', name: 'inv_code', align: 'left',width: 120,},
    				{display: '药品名称', name: 'inv_name', align: 'left',width: 120,},
    				{display: '规格型号', name: 'inv_model', align: 'left',width: 100,},
    				{display: '计量单位', name: 'unit_name', align: 'left',width: 100,},
    				{display: '注册证号', name: 'cert_code', align: 'left',width: 200,},
    				{display: '生成厂商', name: 'fac_name', align: 'left',width: 180,},
    				{display: '单价', name: 'price', align: 'right',width: 100,
    					render: function (rowdata, rowindex, value) {
							return formatNumber(value, '${p08006 }', 1);
						}	
    				},
    				{display: '数量', name: 'amount', align: 'right',width: 100,},
    		 		{display: '供应商', name: 'sup_name', align: 'left',width: 200,},
    		 		{display: '摘要', name: 'brief', align: 'left',width: 300 },
        		 	{display: '领料科室', name: 'dept_name', align: 'left',width: 150,},
        		 	{display: '药品类别', name: 'med_type_name', align: 'left',width: 120,},
        		 	{display: '采购员', name: 'stocker_name', align: 'left',width: 140,},
    				{display: '入库单号', name: 'in_no', align: 'left', width: 130,},
    				{display: '出库单号', name: 'out_no', align: 'left', width: 130,}, 
    				{display: '编制日期', name: 'make_date', align: 'left',width: 140,}, 
    		 		{display: '制单人', name: 'maker_name', align: 'left',width: 140,},
    		 		{display: '审核日期', name: 'check_date', align: 'left',width: 140,},
    		 		{display: '审核人', name: 'checker_name', align: 'left',width: 140,},
    		 		{display: '确认日期', name: 'confirm_date', align: 'left',width: 140,},
    		 		{display: '确认人', name: 'confirmer_name', align: 'left',width: 140,},
    		 		
    		 		],
    			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMedSpecial.do?isCheck=false&show_detail=1',
    			width: '100%', height: '100%', checkbox: true,rownumbers:true,
    			selectRowButtonOnly:true,//heightDiff: -10,
    			checkBoxDisplay:isCheckDisplay,
    			delayLoad : true,//初始化明细数据
    			toolbar: { items: [
    				{ text: '查询（<u>Q</u>）', id:'search', click: query, icon:'search' },
    				{ line:true },
    				{ text: '添加（<u>A</u>）', id:'add', click: add_open, icon:'add' },
    				{ line:true },
    				{ text: '冲账（<u>O</u>）', id:'offset', click: offset, icon:'offset' },
    				{ line:true },
    				{ text: '删除（<u>D</u>）', id:'delete', click: remove, icon:'delete' },
    				{ line:true }, 
    				{ text: '审核（<u>S</u>）', id:'audit', click: audit, icon:'audit' },
    				{ line:true }, 
    				{ text: '消审（<u>U</u>）', id:'unAudit', click: unAudit, icon:'candle' },
    				{ line:true },
    				{ text: '确认（<u>F</u>）', id:'confirm', click: confirm, icon:'account' },
    				{ line:true },
    				{ text: '代销使用生成（<u>C</u>）', id:'affiOut', click: affiOut, icon:'logout' },
    				{ line:true }, 
    				{ text: '模板设置', id:'printSet', click: printSet, icon:'print' },
    				{ line:true }, 
    				{ text: '批量打印（<u>P</u>）', id:'print', click: print, icon:'print' }
    			]},
    			onDblClickRow : function (rowdata, rowindex, value){
    				if(rowdata.special_id == null){
    					$.ligerDialog.warn('请选择数据 ');
    					return ;
    				}
    				update_open(
    						rowdata.group_id 
    						+ ',' + rowdata.hos_id 
    						+ ',' + rowdata.copy_code 
    						+ ',' + rowdata.special_id
    						+ ',' + rowdata.special_no
    				);
    			} 
    		});
    	}else{
    		grid = $("#maingrid").ligerGrid({
    			columns: [
    			     {display: '单据号', name: 'special_no', align: 'left', width: 130,
    						render : function(rowdata, rowindex, value) {
    							if(value == '合计'){
    								return value ; 
    							}
    							return '<a href=javascript:update_open("' 
    								+ rowdata.group_id 
    								+ ',' + rowdata.hos_id 
    								+ ',' + rowdata.copy_code 
    								+ ',' + rowdata.special_id
    								+ ',' + rowdata.special_no+'")>'+rowdata.special_no+'</a>';
    						}
    					},
    				{display: '单据类型', name: 'bus_type_code', align: 'left',width: 80,hide:true
    					
    					},
    					{display: '单据类型', name: 'bus_type_name', align: 'left',width: 80,
    					},
    				{display: '单据来源', name: 'come_from', align: 'left',width: 80,},
    				{display: '金额', name: 'amount_money', align: 'right',width: 100,
			 			render : function(rowdata, rowindex, value) {
			 				if(rowdata.amount_money == null){
			 					return "";
			 				}
							return formatNumber(rowdata.amount_money ==null ? 0 : rowdata.amount_money, '${p08005}', 1);
						}
		 			}, 
		 			{display: '状态', name: 'state', align: 'left',width: 80,hide:true
		 			},{display: '状态', name: 'state_name', align: 'left',width: 80,
		 			
		 			},
    		 		{display: '发票号', name: 'bill_no', align: 'left',width: 140 },
    		 		{
    		 			display: '是否生成发票', name: 'bill_state', align: 'left', width: '100',
    		 			render : function(rowdata, rowindex, value) {
    		 				if(value == 1){
    		 					return "是";
    		 				}else{
    		 					return "否";
    		 				}
    					}
    		 		},
    				{display: '仓库', name: 'store_name', align: 'left',width: 120,
    		 			},
    		 		{display: '供应商', name: 'sup_name', align: 'left',width: 200,
    		 			},
    		 		{display: '摘要', name: 'brief', align: 'left',width: 300 },
    		 		{display: '领料科室', name: 'dept_name', align: 'left',width: 150,
    		 			},
    		 		{display: '采购员', name: 'stocker_name', align: 'left',width: 90,
    		 			},
    				{display: '入库单号', name: 'in_no', align: 'left', width: 130,
    					},
    				{display: '出库单号', name: 'out_no', align: 'left', width: 130,
    					}, 
    				{display: '编制日期', name: 'make_date', align: 'left',width: 90,
    		 			}, 
    		 		{display: '制单人', name: 'maker_name', align: 'left',width: 90,
    		 			},
    		 		{display: '审核日期', name: 'check_date', align: 'left',width: 90,
    		 			},
    		 		{display: '审核人', name: 'checker_name', align: 'left',width: 90,
    		 			},
    		 		{display: '确认日期', name: 'confirm_date', align: 'left',width: 140,
    		 			},
    		 		{display: '确认人', name: 'confirmer_name', align: 'left',width: 90,
    		 			},
    		 		
    		 		],
    			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMedSpecial.do?isCheck=false&show_detail=0',
    			width: '100%', height: '100%', checkbox: true,rownumbers:true,
    			selectRowButtonOnly:true,//heightDiff: -10,
    			checkBoxDisplay:isCheckDisplay,
    			delayLoad : true,//初始化明细数据
    			toolbar: { items: [
    				{ text: '查询（<u>Q</u>）', id:'search', click: query, icon:'search' },
    				{ line:true },
    				{ text: '添加（<u>A</u>）', id:'add', click: add_open, icon:'add' },
    				{ line:true },
    				{ text: '冲账（<u>O</u>）', id:'offset', click: offset, icon:'offset' },
    				{ line:true },
    				{ text: '删除（<u>D</u>）', id:'delete', click: remove, icon:'delete' },
    				{ line:true }, 
    				{ text: '审核（<u>S</u>）', id:'audit', click: audit, icon:'audit' },
    				{ line:true }, 
    				{ text: '消审（<u>U</u>）', id:'unAudit', click: unAudit, icon:'candle' },
    				{ line:true },
    				{ text: '确认（<u>F</u>）', id:'confirm', click: confirm, icon:'account' },
    				{ line:true },
    				{ text: '代销使用生成（<u>C</u>）', id:'affiOut', click: affiOut, icon:'logout' },
    				{ line:true }, 
    				{ text: '模板设置', id:'printSet', click: printSet, icon:'print' },
    				{ line:true }, 
    				{ text: '批量打印（<u>P</u>）', id:'print', click: print, icon:'print' }
    			]},
    			onDblClickRow : function (rowdata, rowindex, value){
    				if(rowdata.special_id == null){
    					$.ligerDialog.warn('请选择数据 ');
    					return ;
    				}
    				update_open(
    						rowdata.group_id 
    						+ ',' + rowdata.hos_id 
    						+ ',' + rowdata.copy_code 
    						+ ',' + rowdata.special_id
    						+ ',' + rowdata.special_no
    				);
    			} 
    		});
    	}
    	

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    //打印回调方法
    function lodopPrint(){
    	var head="<table class='head' width='100%'><tr><td>单位：${sessionScope.hos_name}</td></tr>";
 		head=head+"<tr><td>制单日期："+$("#begin_in_date").val() +" 至  "+ $("#end_in_date").val()+"</td></tr>";
 		head=head+"</table>";
 		grid.options.lodop.head=head; 
 		grid.options.lodop.fn=renderFunc;
 		grid.options.lodop.title="专购品入库";
    }
    
  //是否显示复选框
    function isCheckDisplay(rowdata){
       	if(rowdata.in_id == null) return false;
         return true;
    }

    //键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
		hotkeys('A', add);
		hotkeys('O', offset);
		hotkeys('D', remove);
		hotkeys('S', audit);
		hotkeys('U', unAudit);
		hotkeys('F', confirm);
		hotkeys('P', print);
	}
    
    function add_open(){
    	
    	parent.$.ligerDialog.open({
			title: '添加专购品入库单',
			height: 550,
			width: 1150,
			url: 'hrp/med/storage/special/medSpecialAddPage.do?isCheck=false',
			modal: true, showToggle: false, showMax: true, showMin: true, isResize: true, top: 1, 
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});   
	}
    
    function update_open(obj){		
		var vo = obj.split(",");
		var paras = 
			"group_id="+vo[0] +"&"+ 
			"hos_id="+vo[1] +"&"+ 
			"copy_code="+vo[2] +"&"+ 
			"special_id="+vo[3] +"&"+ 
			"special_no="+vo[4] ;
		parent.$.ligerDialog.open({
			title: '专购品修改',
			height: 550,
			width: 1150,
			url: 'hrp/med/storage/special/updatePage.do?isCheck=false&' + paras.toString(),
			modal: true, showToggle: false, showMax: true, showMin: true, isResize: true, top : 1,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});   
    }
    
    //代销使用生成	
    function affiOut(){		
    	parent.$.ligerDialog.open({
    		title: '代销使用生成',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/med/storage/special/medAffiImpPage.do?isCheck=false',
			//url: 'hrp/med/storage/special/medAffiImpOldPage.do?isCheck=false', 
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
    	
    }
    
    //删除
    function remove(){
    	
    	var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
			var ParamVo =[];
			var in_nos = "";
			$(data).each(function (){		
				if(this.state == 1){
					ParamVo.push(
							this.group_id   +"@"+ 
							this.hos_id   +"@"+ 
							this.copy_code   +"@"+ 
							this.special_id   +"@"+ 
							this.special_no 
						) 
				}else{
					in_nos = in_nos + this.in_no + ",";
				}
				
			});
			if(in_nos != ""){
				$.ligerDialog.error("删除失败！"+in_nos+"单据不是未审核状态,不允许删除");
				return;
			}
			if(ParamVo != null && ParamVo != ''){
				$.ligerDialog.confirm('确定删除?', function (yes){
					if(yes){
						ajaxJsonObjectByUrl("deleteMedSpecial.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
							if(responseData.state=="true"){
								query();
							}
						});
					}
				}); 
			}else{
				$.ligerDialog.error("无删除数据");
			}
			
		}
	}
	//冲账
	function offset(){
		
		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
			var ParamVo =[];
			var special_nos = "";
			$(data).each(function (){		
				if(this.state != 3){
					special_nos = special_nos + this.special_no + ",";
				}
				ParamVo.push(
					this.group_id   +"@"+ 
					this.hos_id   +"@"+ 
					this.copy_code   +"@"+ 
					this.special_id  +"@"+ 
					this.special_no  +"@"+ 
					this.in_id  +"@"+ 
					this.in_no  +"@"+ 
					this.out_id  +"@"+ 
					this.out_no
				) 
			});
			if(special_nos != ""){
				$.ligerDialog.error("冲账失败！"+special_nos+"单据不是已确认状态");
				return;
			}
			$.ligerDialog.confirm('确定冲账?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("offsetMedSpecial.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.state=="true"){
							query();
						}
					});
				}
			}); 
		}
	}
	// 审核
	function audit(){
		
		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
			var ParamVo =[];
			var in_nos = "";
			$(data).each(function (){		
				if(this.state != 1){
					in_nos = in_nos + this.in_no + ",";
				}else{
					ParamVo.push(
							this.group_id   +"@"+ 
							this.hos_id   +"@"+ 
							this.copy_code   +"@"+ 
							this.special_id  +"@"+ 
							this.special_no  +"@"+ 
							this.state  +"@"+ 2
						) 
				}
			});
			if(in_nos != ""){
				$.ligerDialog.error("审核失败！"+in_nos+"单据不是未审核状态");
				return;
			}
			$.ligerDialog.confirm('确定审核?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("updateState.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.state=="true"){
							query();
						}
					});
				}
			}); 
		}
	}
	//消审
	function unAudit(){
		
		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
			var ParamVo =[];
			var in_nos = "";
			$(data).each(function (){		
				if(this.state != 2){
					in_nos = in_nos + this.in_no + ",";
				}else{
					ParamVo.push(
							this.group_id   +"@"+ 
							this.hos_id   +"@"+ 
							this.copy_code   +"@"+ 
							this.special_id  +"@"+ 
							this.special_no  +"@"+ 
							this.state  +"@"+ 1
						) 
				}
				
			});
			if(in_nos != ""){
				$.ligerDialog.error("消审失败！"+in_nos+"单据不是已审核状态");
				return;
			}
			$.ligerDialog.confirm('确定消审?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("updateState.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.state=="true"){
							query();
						}
					});
				}
			}); 
		}
	}
    //确认	
    function confirm(){
    	
    	var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
			var ParamVo =[];
			var in_nos = "";
			$(data).each(function (){		
				if(this.state != 2){
					in_nos = in_nos + this.special_no + ",";
				}
				ParamVo.push(
					this.group_id   +"@"+ 
					this.hos_id   +"@"+ 
					this.copy_code   +"@"+ 
					this.special_id  +"@"+ 
					this.special_no  +"@"+ 
					this.year +"@"+ 
					this.month +"@"+ 
					this.store_id 
				) 
			});
			if(in_nos != ""){
				$.ligerDialog.error("确定失败！"+in_nos+"单据不是已审核状态");
				return;
			}
			$.ligerDialog.confirm('您确定要进行确认操作吗?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("confirmMedSpecial.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.state=="true"){
							query();
						}
					});
				}
			}); 
		}
	}
    
    //打印模板设置
    function printSet(){
	  
    	var useId=0;//统一打印
		if('${p08017 }'==1){
			//按用户打印
			useId='${sessionScope.user_id }';
		}else if('${p08017 }'==2){
			//按仓库打印
			if(liger.get("store_id").getValue()==""){
				$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
				return;
			}
			useId=liger.get("store_id").getValue().split(",")[0];
		}
		officeFormTemplate({template_code:"08015",use_id:useId});					
    	/* parent.$.ligerDialog.open({url :'hrp/med/storage/special/medSpecialPrintSetPage.do?template_code=08017&use_id='+useId,
    		data:{}, height: $(parent).height(),width: $(parent).width(), title:'打印模板设置',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true
    		}); */
    }
	//打印数据
    function print(){
   		var useId=0;//统一打印
		if('${p08017 }'==1){
			//按用户打印
			useId='${sessionScope.user_id }';
		}else if('${p08017 }'==2){
			//按仓库打印
			if(liger.get("store_id").getValue()==""){
				$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
				return;
			}
			useId=liger.get("store_id").getValue().split(",")[0];
		}

		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
			
			var special_id ="" ;
			var special_nos = "";
			$(data).each(function (){		
				if(this.state != 2){
					special_nos = special_nos + this.special_no + "<br>";
				}
				
				special_id  += this.special_id+","
					
			});
			
//			if(special_nos != ""){
//				$.ligerDialog.error("打印失败！<br>以下单据不是已审核状态：<br>"+special_nos);
//				return;
//			} 
			
			/* var para={
				paraId :special_id.substring(0,special_id.length-1) ,
				template_code:'08017',
				isPrintCount:false,//更新打印次数
				isPreview:true,//预览窗口，传绝对路径
				use_id:useId,
				p_num:1
				//isSetPrint:flag
			}; 
		 	
			//alert(JSON.stringify(para));
	    	printTemplate("hrp/med/storage/special/queryMedSpecialByPrintTemlate.do?isCheck=false",para); */
			
			 var para={
	    				paraId :special_id.substring(0,special_id.length-1) ,
		    			template_code:'08015',
		    			class_name:"com.chd.hrp.med.serviceImpl.storage.special.MedSpecialServiceImpl",
		    			method_name:"queryMedSpecialByPrintTemlate1",
		    			isPrintCount:false,//更新打印次数
		    			isPreview:true,//预览窗口，传绝对路径
		    			use_id:useId,
		    			p_num:1
		    	}; 
			 	
			  officeFormPrint(para);
		}
    }
   
    function loadDict(){
		//字典下拉框
		autocomplete("#fac_id", "../../queryHosFacDict.do?isCheck=false", "id", "text", true, true, "", false, false, '220');
		autocomplete("#med_type_code", "../../queryMedTypeDictDate.do?isCheck=false", "id", "text", true, true, {read_or_write:'1'}, false, false, '220');
		autocomplete("#store_id", "../../queryMedStoreDictDate.do?isCheck=false", "id", "text", true, true, {read_or_write:'1'}, false, false, '220');
		autocomplete("#sup_id", "../../queryHosSupDict.do?isCheck=false", "id", "text", true, true, "", false, false, '220');
		autocomplete("#dept_id", "../../queryMedDeptDictDate.do?isCheck=false", "id", "text", true, true, {is_last:1,read_or_write:'1'}, false, false, '220');
		autoCompleteByData("#come_from", medSpecailMain_comeFrom.Rows, "id", "text", true, true, "", false, false, '220');
		//autoCompleteByData("#bus_type_code", bus_type_code_state.Rows, "id", "text", true, true, "", false, false, '220');
		autoCompleteByData("#bill_state", yes_or_no.Rows, "id", "text", true, true, "", false, false, "220");
		autodate("#begin_in_date","yyyy-mm-dd","month_first");
		autodate("#end_in_date","yyyy-mm-dd","month_last");
		
        $("#state").ligerTextBox({width:220});
        $("#begin_in_date").ligerTextBox({width:90});
        $("#end_in_date").ligerTextBox({width:90});
        $("#begin_confirm_date").ligerTextBox({width:90});
        $("#end_confirm_date").ligerTextBox({width:90});
        $("#special_no").ligerTextBox({width:220});
        $("#bill_no").ligerTextBox({width:220});
        $("#brief").ligerTextBox({width:220});
        
      //  autocomplete("#inv_code", "../../queryMedInv.do?isCheck=false", "id", "text", true, true, "", false, false, "", "", "220");
        $("#inv_code").ligerTextBox({width:220});
	}  
	
    function showDetail(){
		show_detail = $("#show_detail").is(":checked") ? 1 : 0;
		if(show_detail==0){
			//liger.get("inv_code").clear();
			$("#inv_code").val();
		}
		if (grid) {
			//由于一个对象多次绑定相同的事件，需要进行解绑在绑定
			grid.unbind(); 
		}
		
		loadHead();
		query();
	 }
    
	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" >
       <tr>
        	<td align="right" class="l-table-edit-td" width="10%">编制日期：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<table>
            		<tr>
            			<td align="right" >
            				<input class="Wdate" name="begin_in_date" id="begin_in_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
            			</td>
            			<td align="left" class="l-table-edit-td">至：</td>
			            <td align="left" class="l-table-edit-td">
			            	<input class="Wdate" name="end_in_date" id="end_in_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
			            </td>
            		</tr>
            	</table>
            </td>
        	
            <td align="right" class="l-table-edit-td" width="10%">仓库：</td>
            <td align="left" class="l-table-edit-td" width="20%"><input name="store_id" type="text" id="store_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            
            <td align="right" class="l-table-edit-td" width="10%">状态：</td>
             <td align="left" class="l-table-edit-td" width="20%">
            	<select name="state" id="state"style="width: 135px;" >
            			<option value="">请选择</option>
            			<option value="0">验收</option>
        	        	<option value="1">未审核</option>
                		<option value="2">审核</option>  
                		<option value="3">入库确认</option>
                		<option value="4">财务记账</option>
            	</select>
            </td>
        </tr> 
        <tr>
        	<td align="right" class="l-table-edit-td" width="10%">入库日期：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<table>
            		<tr>
            			<td align="right" >
            				<input class="Wdate" name="begin_confirm_date" id="begin_confirm_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
            			</td>
			            <td align="left" class="l-table-edit-td">至：</td>
			            <td align="left" class="l-table-edit-td">
			             	<input class="Wdate" name="end_confirm_date" id="end_confirm_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
			            </td>
            		</tr>
            	</table>
            </td>
            
            <td align="right" class="l-table-edit-td" width="10%">供应商：</td>
            <td align="left" class="l-table-edit-td" width="20%"><input name="sup_id" type="text" id="sup_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="right" class="l-table-edit-td" width="10%">领料科室：</td>
            <td align="left" class="l-table-edit-td" width="20%"><input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
        </tr> 
        <tr>
        	<td align="right" class="l-table-edit-td" width="10%">单据号：</td>
            <td align="left" class="l-table-edit-td" width="20%"><input name="special_no" type="text" id="special_no" ltype="text" validate="{required:true,maxlength:100}" /></td>
        	<td align="right" class="l-table-edit-td" width="10%">发票号：</td>
            <td align="left" class="l-table-edit-td" width="20%"><input name="bill_no" type="text" id="bill_no" ltype="text" validate="{required:true,maxlength:100}" /></td>
       	 	<td align="right" class="l-table-edit-td" width="10%">单据来源：</td>
            <td align="left" class="l-table-edit-td" width="20%"><input name="come_from" type="text" id="come_from" ltype="text" validate="{required:false,maxlength:20}" /></td>
        </tr>
        <tr>
        	<td align="right" class="l-table-edit-td" width="10%">摘要：</td>
            <td align="left" class="l-table-edit-td" width="20%"><input name="brief" type="text" id="brief" ltype="text" validate="{required:true,maxlength:100}" /></td>
       		
       		<td align="right" class="l-table-edit-td" width="10%">药品信息：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="inv_code" type="text" id="inv_code" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
            
            <td align="right" class="l-table-edit-td" width="10%">药品类别：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="med_type_code" type="text" id="med_type_code" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
            
        </tr>
        <tr>
			<td align="right" class="l-table-edit-td" >
				是否生成发票：
			</td>
			<td align="left" class="l-table-edit-td">
				<input name="bill_state" type="text" id="bill_state" ltype="text" validate="{required:false}" />
			</td>
        	<td align="right" class="l-table-edit-td" width="10%"></td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="show_detail" type="checkbox" id="show_detail" onclick="showDetail();"/>&nbsp;&nbsp;显示明细
            </td>
            <td align="right" class="l-table-edit-td" width="10%">生成厂商：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="fac_id" type="text" id="fac_id" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
        </tr>
        
        <!-- <tr>
        	<td align="right" class="l-table-edit-td" width="10%">业务类型：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="bus_type_code" type="text" id="bus_type_code" ltype="text" />
            </td>
        </tr> -->
    </table>
	<div id="maingrid"></div>
	<div id="resultPrint" style="display:none">
	   	<table width="100%">
			<thead>
				<tr>
	                <th width="200">单据号</th>
	                <th width="200">单据类型</th>	
	                <th width="200">仓库</th>	
	                <th width="200">供应商</th>
	                <th width="200">领料科室</th>	
	                <th width="200">采购员</th>
	                <th width="200">金额</th>
	                <th width="200">入库单号</th>
	                <th width="200">出库单号</th>
	                <th width="200">制单日期</th>
	                <th width="200">制单人</th>
	                <th width="200">审核日期</th>	
	                <th width="200">审核人</th>	
	                <th width="200">确认日期</th>	
	                <th width="200">确认人</th>
	                <th width="200">状态</th>	
				</tr>
			</thead>
			<tbody></tbody>
	   	</table>
   	</div>
</body>
</html>
