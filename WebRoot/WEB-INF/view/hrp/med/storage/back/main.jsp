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
	var isUseAffiStore = '${p08044 }' == 1 ? true : false;
	var show_detail ;
    var renderFunc = {
		amount_money:function(value){//金额
			return formatNumber(value, '${p08005 }', 1);
		},bill_state:function(value){
    		if(value == 1){
				return "是";
			}else{
				return "否";
			}
		} 
	}; 
    
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	//loadHead(null);	
		loadHotkeys();
		showDetail();
		show_detail = $("#show_detail").is(":checked") ? 1 : 0;
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
			name : 'bus_type_code',
			value : liger.get("bus_type_code").getValue() == null ? "" : liger.get("bus_type_code").getValue()
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
			name : 'store_id',
			value : liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[0]
		}); 
		grid.options.parms.push({
			name : 'sup_id',
			value : liger.get("sup_code").getValue() == null ? "" : liger.get("sup_code").getValue().split(",")[0]
		}); 
		grid.options.parms.push({
			name : 'in_no',
			value : $("#in_no").val()
		}); 
		grid.options.parms.push({
			name : 'bill_no',
			value : $("#bill_no").val()
		}); 
		grid.options.parms.push({
			name : 'bill_state',
			value : liger.get("bill_state").getValue() == null ? "" : liger.get("bill_state").getValue()
		}); 
		
		if (show_detail == 1) {
			grid.options.parms.push({
				name : 'inv_code',
				value : $("#inv_code").val()
			});
			grid.options.parms.push({
				name : 'batch_no',
				value : $("#batch_no").val()
			});
		}
		
    	//加载查询条件
    	grid.loadData(grid.where);
     }
	
    function loadHead(){
    	if (show_detail == "1") {
    		grid = $("#maingrid").ligerGrid({
    			columns: [{
    					display: '退库单号', name: 'in_no', align: 'left', width: 130,
    					render : function(rowdata, rowindex, value) {
    						if(value == '合计'){
    							return value;
    						}
    						return '<a href=javascript:update_open("' 
    							+ rowdata.group_id 
    							+ ',' + rowdata.hos_id 
    							+ ',' + rowdata.copy_code 
    							+ ',' + rowdata.in_id
    							+ ',' + rowdata.store_id
    							+ '")>'+rowdata.in_no+'</a>';
    					}
    				}, { 
    		 			display: '摘要', name: 'brief', align: 'left', width: '200'
    		 		}, { 
    		 			display: '仓库', name: 'store_name', align: 'left', width: '150'
    		 		},{
						display : '药品编码',name : 'inv_code',align : 'left',width : '120'
					},{
						display : '药品名称',name : 'inv_name',align : 'left',width : '120'
					},{
						display : '计量单位',name : 'unit_name',align : 'left',width : '60'
					},{
						display : '规格型号',name : 'inv_model',align : 'left',width : '120'
					},{
						display : '单价',name : 'price',align : 'right',width : '80',
						render : function(rowdata,rowindex, value) {
							return formatNumber(value,'${p08006 }',1);
						}
					},{
						display : '数量',name : 'amount',align : 'right',width : '80'
					},{
						display : '金额',name : 'amount_money',align : 'right',width : '100',
						render : function(rowdata,rowindex, value) {
							return formatNumber(
									rowdata.amount_money == null ? 0: rowdata.amount_money,'${p08005 }',1);
						}
					},{
						display : '批号',name : 'batch_no',align : 'left',width : '80'
					},{
						display : '条形码',name : 'bar_code',align : 'left',width : '80'
					},{
						display : '生产厂商',name : 'fac_name',align : 'left',width : '80'
					}, { 
    		 			display: '业务类型', name: 'bus_type_name', align: 'left', width: '80'
    		 		}, { 
    		 			display: '发票号', name: 'bill_no', align: 'left', width: '100'
    		 		}, { 
    		 			display: '是否生成发票', name: 'bill_state', align: 'left', width: '100',
    		 			render : function(rowdata, rowindex, value) {
    		 				if(value == 1){
    		 					return "是";
    		 				}else{
    		 					return "否";
    		 				}
    					}
    		 		}, { 
    		 			display: '供应商', name: 'sup_name', align: 'left', width: '150'
    		 		}, { 
    		 			display: '采购员', name: 'stocker_name', align: 'left', width: '80'
    		 		/* }, { 
    		 			display: '金额', name: 'amount_money', align: 'right', width: '100',
    		 			render : function(rowdata, rowindex, value) {
    						return formatNumber(value ==null ? 0 : value, '${p08005 }', 1);
    					} */
    		 		}, { 
    		 			display: '制单日期', name: 'in_date', align: 'left', width: '80'
    		 		}, { 
    		 			display: '制单人', name: 'maker_name', align: 'left', width: '80'
    		 		}, { 
    		 			display: '退货日期', name: 'confirm_date', align: 'left', width: '80'
    		 		}, { 
    		 			display: '库管员', name: 'confirmer_name', align: 'left', width: '80'
    		 		}, { 
    		 			display: '状态', name: 'state_name', align: 'right', width: '80'
    		 		}],
    			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMedStorageBack.do?isCheck=true&show_detail=1',
    			width: '100%', height: '100%', checkbox: true,rownumbers:true,
    			delayLoad: true,//初始化不加载，默认false
    			selectRowButtonOnly:true,//heightDiff: -10,
    			checkBoxDisplay:isCheckDisplay,
    			toolbar: { items: [
    				{ text: '查询（<u>Q</u>）', id:'search', click: query, icon:'search' },
    				{ line:true },
    				{ text: '添加（<u>A</u>）', id:'add', click: add_open, icon:'add' },
    				{ line:true },
    				{ text: '复制（<u>C</u>）', id:'copy', click: copy_no, icon:'copy' },
    				{ line:true },
    				{ text: '删除（<u>D</u>）', id:'delete', click: remove, icon:'delete' },
    				{ line:true }, 
    				{ text: '审核（<u>S</u>）', id:'audit', click: audit, icon:'audit' },
    				{ line:true }, 
    				{ text: '消审（<u>U</u>）', id:'unAudit', click: unAudit, icon:'unaudit' },
    				{ line:true }, 
    				{ text: '退货确认（<u>C</u>）', id:'confirm', click: confirmData, icon:'account' },
    				{ line:true },
    				{ text: '模板设置', id:'printSet', click: printSetNew, icon:'print' },
    				{ line:true } ,
    				{ text: '批量打印（<u>P</u>）', id:'print', click: printNew, icon:'print' }
    				
    			]}, 
    			onDblClickRow : function (rowdata, rowindex, value){
    				if(rowdata.in_id == null){
    					$.ligerDialog.warn("请选择数据 ");
    					return ;
    				}
    				update_open(
    					rowdata.group_id + "," + 
    					rowdata.hos_id + "," + 
    					rowdata.copy_code + "," + 
    					rowdata.in_id + "," + 
    					rowdata.store_id 
    				);
    			} 
    		});
    	}else{
    		grid = $("#maingrid").ligerGrid({
    			columns: [{
    					display: '退库单号', name: 'in_no', align: 'left', width: 130,
    					render : function(rowdata, rowindex, value) {
    						if(value == '合计'){
    							return value;
    						}
    						return '<a href=javascript:update_open("' 
    							+ rowdata.group_id 
    							+ ',' + rowdata.hos_id 
    							+ ',' + rowdata.copy_code 
    							+ ',' + rowdata.in_id
    							+ ',' + rowdata.store_id
    							+ '")>'+rowdata.in_no+'</a>';
    					}
    				}, { 
    		 			display: '摘要', name: 'brief', align: 'left', width: '200'
    		 		}, { 
    		 			display: '仓库', name: 'store_name', align: 'left', width: '150'
    		 		}, { 
    		 			display: '业务类型', name: 'bus_type_name', align: 'left', width: '80'
    		 		}, { 
    		 			display: '发票号', name: 'bill_no', align: 'left', width: '100'
    		 		}, { 
    		 			display: '是否生成发票', name: 'bill_state', align: 'left', width: '100',
    		 			render : function(rowdata, rowindex, value) {
    		 				if(value == 1){
    		 					return "是";
    		 				}else{
    		 					return "否";
    		 				}
    					}
    		 		}, { 
    		 			display: '供应商', name: 'sup_name', align: 'left', width: '150'
    		 		}, { 
    		 			display: '采购员', name: 'stocker_name', align: 'left', width: '80'
    		 		}, { 
    		 			display: '金额', name: 'amount_money', align: 'right', width: '100',
    		 			render : function(rowdata, rowindex, value) {
    						return formatNumber(value ==null ? 0 : value, '${p08005 }', 1);
    					}
    		 		}, { 
    		 			display: '制单日期', name: 'in_date', align: 'left', width: '80'
    		 		}, { 
    		 			display: '制单人', name: 'maker_name', align: 'left', width: '80'
    		 		}, { 
    		 			display: '退货日期', name: 'confirm_date', align: 'left', width: '80'
    		 		}, { 
    		 			display: '库管员', name: 'confirmer_name', align: 'left', width: '80'
    		 		}, { 
    		 			display: '状态', name: 'state_name', align: 'right', width: '80'
    		 		}],
    			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMedStorageBack.do?isCheck=true&show_detail=0',
    			width: '100%', height: '100%', checkbox: true,rownumbers:true,
    			delayLoad: true,//初始化不加载，默认false
    			selectRowButtonOnly:true,//heightDiff: -10,
    			checkBoxDisplay:isCheckDisplay,
    			toolbar: { items: [
    				{ text: '查询（<u>Q</u>）', id:'search', click: query, icon:'search' },
    				{ line:true },
    				{ text: '添加（<u>A</u>）', id:'add', click: add_open, icon:'add' },
    				{ line:true },
    				{ text: '复制（<u>C</u>）', id:'copy', click: copy_no, icon:'copy' },
    				{ line:true },
    				{ text: '删除（<u>D</u>）', id:'delete', click: remove, icon:'delete' },
    				{ line:true }, 
    				{ text: '审核（<u>S</u>）', id:'audit', click: audit, icon:'audit' },
    				{ line:true }, 
    				{ text: '消审（<u>U</u>）', id:'unAudit', click: unAudit, icon:'unaudit' },
    				{ line:true }, 
    				{ text: '退货确认（<u>C</u>）', id:'confirm', click: confirmData, icon:'account' },
    				{ line:true },
    				{ text: '模板设置', id:'printSet', click: printSetNew, icon:'print' },
    				{ line:true } ,
    				{ text: '批量打印（<u>P</u>）', id:'print', click: printNew, icon:'print' }
    				
    			]}, 
    			onDblClickRow : function (rowdata, rowindex, value){
    				if(rowdata.in_id == null){
    					$.ligerDialog.warn("请选择数据 ");
    					return ;
    				}
    				update_open(
    					rowdata.group_id + "," + 
    					rowdata.hos_id + "," + 
    					rowdata.copy_code + "," + 
    					rowdata.in_id + "," + 
    					rowdata.store_id 
    				);
    			} 
    		});
    	}
    	

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function isCheckDisplay(rowdata){
       	if(rowdata.in_id == null) return false;
         return true;
    }
    
    //打印回调方法
    function lodopPrint(){
    	var head="<table class='head' width='100%'><tr><td>单位：${sessionScope.hos_name}</td></tr>";
 		head=head+"<tr><td>制单日期："+$("#begin_in_date").val() +" 至  "+ $("#end_in_date").val()+"</td></tr>";
 		head=head+"</table>";
 		grid.options.lodop.head=head; 
 		grid.options.lodop.fn=renderFunc;
 		grid.options.lodop.title="药品退货";
    }
    
    
    //键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
		hotkeys('A', add_open);
		hotkeys('C', copy_no);
		hotkeys('D', remove);
		hotkeys('S', audit);
		hotkeys('U', unAudit);
		hotkeys('F', confirmData);
		hotkeys('P', printNew);
	}
    
    function add_open(){
    	parent.$.ligerDialog.open({
			title: '退货单添加',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/med/storage/back/addPage.do?isCheck=false',
			modal: true, showToggle: false, showMax: true, showMin: true, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});   
	}
    
    function update_open(obj){		
		var vo = obj.split(",");
		var paras = 
			"group_id="+vo[0] +"&"+ 
			"hos_id="+vo[1] +"&"+ 
			"copy_code="+vo[2] +"&"+ 
			"in_id="+vo[3] +"&"+ 
			"store_id="+vo[4];
		parent.$.ligerDialog.open({
			title: '退货单修改',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/med/storage/back/updatePage.do?isCheck=false&' + paras.toString(),
			modal: true, showToggle: false, showMax: true, showMin: true, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});   
    }
    	
    function remove(){
    	
    	var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
			var ParamVo =[];
			var in_nos = "";
			$(data).each(function (){		
				if(this.state > 1){
					in_nos = in_nos + this.in_no + ",";
				}
				ParamVo.push(
					this.group_id   +"@"+ 
					this.hos_id   +"@"+ 
					this.copy_code   +"@"+ 
					this.in_id 
				) 
			});
			if(in_nos != ""){
				$.ligerDialog.error("删除失败！"+in_nos+"单据不是未审核状态");
				return;
			}
			$.ligerDialog.confirm('确定删除?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("deleteMedStorageBack.do",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.state=="true"){
							query();
						}
					});
				}
			}); 
		}
	}
    
	function copy_no(){
    	
    	var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
			var ParamVo =[];
			var in_nos = "";
			$(data).each(function (){		
				ParamVo.push(
					this.group_id   +"@"+ 
					this.hos_id   +"@"+ 
					this.copy_code   +"@"+ 
					this.in_id 
				) 
			});
			$.ligerDialog.confirm('确定复制?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("copyMedStorageBack.do",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.state=="true"){
							query();
						}
					});
				}
			}); 
		}
	}

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
				}
				ParamVo.push(
					this.group_id   +"@"+ 
					this.hos_id   +"@"+ 
					this.copy_code   +"@"+ 
					this.in_id 
				) 
			});
			if(in_nos != ""){
				$.ligerDialog.error("审核失败！"+in_nos+"单据不是未审核状态");
				return;
			}
			$.ligerDialog.confirm('确定审核?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("auditMedStorageBackBatch.do",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.state=="true"){
							query();
						}
					});
				}
			}); 
		}
	}

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
				}
				ParamVo.push(
					this.group_id   +"@"+ 
					this.hos_id   +"@"+ 
					this.copy_code   +"@"+ 
					this.in_id 
				) 
			});
			if(in_nos != ""){
				$.ligerDialog.error("消审失败！"+in_nos+"单据不是已审核状态");
				return;
			}
			$.ligerDialog.confirm('确定消审?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("unAuditMedStorageBackBatch.do",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.state=="true"){
							query();
						}
					});
				}
			}); 
		}
	}
    	
       function confirmData(){
    	var is_store='${p08045 }';
    	var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
			var todayDate = new Date();
			var todayYear = todayDate.getFullYear();
			var todayMonth = todayDate.getMonth() + 1;
			var todayDate = todayDate.getDate();
			todayMonth = todayMonth < 10 ? '0' + todayMonth : todayMonth;
			todayDate = todayDate < 10 ? '0' + todayDate : todayDate;
			var today = todayYear + '-' + todayMonth + '-' + todayDate;
			var confirmDate;
			if('${p08047 }'==0){
				confirmDataAll(today);
			}else{
				$.ligerDialog.open({
					content: "确认日期:<input id='confirmDate' value=" + today + " style='text-align:center; border: 1px solid blue; height: 18px;' onFocus='WdatePicker({isShowClear:true,readOnly:true,dateFmt:\"yyyy-MM-dd\"})' />",
					width: 300,
					height: 150,
					buttons: [
						{ text: '确定', onclick: function (item, dialog) {
							confirmDate = $("#confirmDate").val();
							if (confirmDate) {
								dialog.close();
								confirmDataAll(confirmDate);
								}
						}},
		                { text: '取消', onclick: function (item, dialog) { dialog.close(); } }
				]
				})
			}
			
		}
	}
    
    function confirmDataAll(confirmDate){
    	var is_store='${p08045 }';
    	var data = gridManager.getCheckedRows();
    	var ParamVo =[];
		var in_nos = "";
		$(data).each(function (){		
			if(this.state != 2){
				in_nos = in_nos + this.in_no + ",";
			}
			ParamVo.push(
				this.group_id   +"@"+ 
				this.hos_id   +"@"+ 
				this.copy_code   +"@"+ 
				this.in_id +"@"+ 
				confirmDate +"@"+ 
				is_store+ "@"+
				this.store_id+"@"+
				this.in_no
			) 
		});
		if(in_nos != ""){
			$.ligerDialog.error("入库确认失败！"+in_nos+"单据不是已审核状态");
			return;
		}
		$.ligerDialog.confirm('确定入库确认?', function (yes){
			if(yes){
				ajaxJsonObjectByUrl("/CHD-HRP/hrp/med/storage/in/verifyMedClosingDate.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
					if(responseData.state=="true"){
						ajaxJsonObjectByUrl("confirmMedStorageBackBatch.do",{ParamVo : ParamVo.toString()},function (responseData){
							if(responseData.state=="true"){
								query();
							}
						});
					}
				});
				
			}
		}); 
	
    	
    }
    //打印模板设置
    function printSet(){
	  
    	var useId=0;//统一打印
		if('${p08018 }'==1){
			//按用户打印
			useId='${sessionScope.user_id }';
		}else if('${p08018 }'==2){
			//按仓库打印
			if(liger.get("store_code").getValue()==""){
				$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
				return;
			}
			useId=liger.get("store_code").getValue().split(",")[0];
		}
    	parent.$.ligerDialog.open({url : 'hrp/med/storage/back/storageBackPrintSetPage.do?template_code=08011&use_id='+useId,
    		data:{}, height: $(parent).height(),width: $(parent).width(), title:'打印模板设置',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true
    		});
    }
	
  //打印模板设置 最新版
    function printSetNew(){
	  
    	var useId=0;//统一打印
		if('${p08018 }'==1){
			//按用户打印
			useId='${sessionScope.user_id }';
		}else if('${p08018 }'==2){
			//按仓库打印
			if(liger.get("store_code").getValue()==""){
				$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
				return;
			}
			useId=liger.get("store_code").getValue().split(",")[0];
		}
    	
		officeFormTemplate({template_code:"08009",use_id : useId})
    }
  //打印
    function print(){
    	
    	 var start_date = $("#begin_in_date").val() + " 00:00"; 
    	 var end_date = $("#end_in_date").val() + " 00:00"; 
    	 start_date = new Date(start_date.replace(/-/g, "/")); 
    	 end_date = new Date(end_date.replace(/-/g, "/")); 
    	 if(start_date.getMonth() != end_date.getMonth()) { 
    		  $.ligerDialog.error("不支持跨月打印！"); 
    	      return false;  
    	 } 
    	 
    	 var useId=0;//统一打印
 		if('${p08018 }'==1){
 			//按用户打印
 			useId='${sessionScope.user_id }';
 		}else if('${p08018 }'==2){
 			//按仓库打印
 			if(liger.get("store_code").getValue()==""){
 				$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
 				return;
 			}
 			useId=liger.get("store_code").getValue().split(",")[0];
 		}

    	//if($("#create_date_b").val())
 		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
			
			var in_id ="" ;
			var in_nos = "";
			$(data).each(function (){		
				if(this.state != 2){
					in_nos = in_nos + this.in_no + "<br>";
				}
				
				in_id  += this.in_id+","
					
			});
			
// 			if(in_nos != ""){
// 				$.ligerDialog.error("打印失败！<br>以下单据不是已审核状态：<br>"+in_nos);
// 				return;
// 			} 
			
			
			 var para={
	    			paraId :in_id.substring(0,in_id.length-1) ,
	    			
	    			template_code:'08011',
	    			isPrintCount:false,//更新打印次数
	    			isPreview:true,//预览窗口，传绝对路径
	    			use_id:useId,
	    			p_num:1
	    			//isSetPrint:flag
	    	}; 
		 	
			//alert(JSON.stringify(para));
			
	    	printTemplate("hrp/med/storage/back/queryMedBackByPrintTemlate.do?isCheck=false",para);
	    	
		}
    	
    }
  
  //打印 最新版
    function printNew(){
    	
    	 var start_date = $("#begin_in_date").val() + " 00:00"; 
    	 var end_date = $("#end_in_date").val() + " 00:00"; 
    	 start_date = new Date(start_date.replace(/-/g, "/")); 
    	 end_date = new Date(end_date.replace(/-/g, "/")); 
    	 if(start_date.getMonth() != end_date.getMonth()) { 
    		  $.ligerDialog.error("不支持跨月打印！"); 
    	      return false;  
    	 } 
    	 
    	 var useId=0;//统一打印
 		if('${p08018 }'==1){
 			//按用户打印
 			useId='${sessionScope.user_id }';
 		}else if('${p08018 }'==2){
 			//按仓库打印
 			if(liger.get("store_code").getValue()==""){
 				$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
 				return;
 			}
 			useId=liger.get("store_code").getValue().split(",")[0];
 		}

    	//if($("#create_date_b").val())
 		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
			
			var in_id ="" ;
			var in_nos = "";
			$(data).each(function (){		
				if(this.state != 2){
					in_nos = in_nos + this.in_no + "<br>";
				}
				
				in_id  += this.in_id+","
					
			});
			
// 			if(in_nos != ""){
// 				$.ligerDialog.error("打印失败！<br>以下单据不是已审核状态：<br>"+in_nos);
// 				return;
// 			} 
			
			
			 var para={
					 
					template_code:'08009',
					class_name:"com.chd.hrp.med.serviceImpl.storage.in.MedStorageInServiceImpl",
					method_name:"queryMedInByPrintTemlateNewPrint",
					isSetPrint:false,//是否套打，默认非套打
					isPreview:true,//是否预览，默认直接打印
	    			paraId :in_id.substring(0,in_id.length-1) ,
	    			isPrintCount:false,//更新打印次数
	    			use_id:useId,
	    			p_num:1
	    			//isSetPrint:flag
	    	}; 
		 	
			 officeFormPrint(para);
	    	
		}
    	
    }
    function loadDict(){
		//字典下拉框
		autoCompleteByData("#state", medInMain_state.Rows, "id", "text", true, true);
		autoCompleteByData("#bill_state", yes_or_no.Rows, "id", "text", true, true);
		//alert(liger.get("bus_type_code").getValue());
		autocompleteAsync("#bus_type_code", "../../queryMedBusType.do?isCheck=false", "id", "text", true, true, {codes : '10,12,16,22'}, false,"12");
		autocomplete("#store_code", "../../queryMedStoreDictDate.do?isCheck=false", "id", "text", true, true, isUseAffiStore ? "" : {is_com : 0,read_or_write:'1'});
		autocomplete("#sup_code", "../../queryHosSupDict.do?isCheck=false", "id", "text", true, true);
        $("#begin_in_date").ligerTextBox({width:100}); 
        $("#end_in_date").ligerTextBox({width:100});
        autodate("#begin_in_date", "yyyy-mm-dd", "month_first");
        autodate("#end_in_date", "yyyy-mm-dd", "month_last");
        $("#begin_confirm_date").ligerTextBox({width:100});
        $("#end_confirm_date").ligerTextBox({width:100});
        $("#in_no").ligerTextBox({width : 239});
        $("#bill_no").ligerTextBox({width : 160});
        $("#inv_code").ligerTextBox({width : 239});
        $("#batch_no").ligerTextBox({width : 160});
        
	}  
	
    function showDetail() {
		show_detail = $("#show_detail").is(":checked") ? 1 : 0;
		$("#batch_no").val();
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
        </tr> 
        <tr>
        	<td align="right" class="l-table-edit-td"  width="10%">
            	制单日期：
            </td>
            <td align="left" class="l-table-edit-td"  width="20%">
				<table>
					<tr>
						<td align="left" >
							<input class="Wdate" name="begin_in_date" id="begin_in_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
						<td align="right" class="l-table-edit-td"  >
							至：
						</td>
						<td align="left" class="l-table-edit-td">
							<input class="Wdate" name="end_in_date" id="end_in_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
            		</tr>
				</table>
	        </td>
           
			<td align="right" class="l-table-edit-td"  width="10%">
				业务类型：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="bus_type_code" type="text" id="bus_type_code" ltype="text" required="true" validate="{required:true}" />
            </td>
			<td align="right" class="l-table-edit-td"  width="10%">
				状态：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="state" type="text" id="state" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
        </tr> 
        <tr>
        	<td align="right" class="l-table-edit-td"  width="10%">
            	退货日期：
            </td>
            <td align="left" class="l-table-edit-td"  width="20%">
				<table>
					<tr>
						<td align="left" >
							<input class="Wdate" name="begin_confirm_date" id="begin_confirm_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
						<td align="right" class="l-table-edit-td"  >
							至：
						</td>
						<td align="left" class="l-table-edit-td">
							<input class="Wdate" name="end_confirm_date" id="end_confirm_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
            		</tr>
				</table>
	        </td>
        
			<td align="right" class="l-table-edit-td"  width="10%">
				仓库：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="store_code" type="text" id="store_code" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
			<td align="right" class="l-table-edit-td" >
				供应商：
			</td>
            <td align="left" class="l-table-edit-td">
            	<input name="sup_code" type="text" id="sup_code" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
        </tr> 
        <tr>
			<td align="right" class="l-table-edit-td" >
				单据号：
			</td>
			<td align="left" class="l-table-edit-td">
				<input name="in_no" type="text" id="in_no" ltype="text" validate="{required:false,maxlength:100}" />
			</td>
			<td align="right" class="l-table-edit-td" >
				发票号：
			</td>
			<td align="left" class="l-table-edit-td">
				<input name="bill_no" type="text" id="bill_no" ltype="text" validate="{required:false,maxlength:100}" />
			</td>
			<td align="right" class="l-table-edit-td" >
				是否生成发票：
			</td>
			<td align="left" class="l-table-edit-td">
				<input name="bill_state" type="text" id="bill_state" ltype="text" validate="{required:false}" />
			</td>
        </tr>
        <tr>
        	<td align="right" class="l-table-edit-td" width="10%">药品信息：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="inv_code" type="text" id="inv_code" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
            
            <td align="right" class="l-table-edit-td  demo" width="10%">批号：</td>
            <td align="left" class="l-table-edit-td  demo" width="20%">
            	<input name="batch_no" type="text" id="batch_no" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
            
        	<td align="right" class="l-table-edit-td" width="10%">
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="show_detail" type="checkbox" id="show_detail" onclick="showDetail();"/>&nbsp;&nbsp;显示明细
            </td>
        </tr>
    </table>
	<div id="maingrid"></div>
</body>
</html>
