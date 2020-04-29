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
    var show_detail=0;
	var gridManager = null;
	var userUpdateStr;
	var is_qzj='${is_qzj}';
    var renderFunc = {
    		pur_type:function(value){
    			if(value == 1){
					return "自购订单";
				}else if(value == 2){
					return "统购订单";
				}else{
					return "";
				}
			} , 
			order_type:function(value){
				if(value == 1){
					return "普通订单";
				}else{
					return "代销备货订单";
				}
			} ,
			is_notice:function(value){
				if(value == 0){
					return "未通知";
				}else{
					return "已通知";
				}
			} ,
			state:function(value){
				if(value == 0){
					return "已中止";
				}else if(value == 1){
					return "未审核";
				}else if(value == 2){
					return "已审核";
				}else if(value == 3){
					return "执行完毕";
				}else{
					return "已合并";
				}
				
			}
    		
    };
	
	$(function() {
		
		loadDict()//加载下拉框
		//加载数据
		//loadHead(null);
		loadHotkeys();
		//showDetail();
	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		grid.options.parms.push({
			name : 'inv_code',
			value : $("#inv_code").val()
		});
		//根据表字段进行添加查询条件
		
    	grid.options.parms.push({name : 'begin_date',value : $("#begin_date").val()}); 
    	grid.options.parms.push({name : 'end_date',value : $("#end_date").val() }); 
    	grid.options.parms.push({name : 'arrive_date',value : $("#arrive_date").val()}); 
    	grid.options.parms.push({name : 'order_code',value : $("#order_code").val()}); 
    	grid.options.parms.push({name : 'store_id',value : liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[0]}); 
    	grid.options.parms.push({name : 'order_type',value : liger.get("order_type").getValue()});
    	grid.options.parms.push({name : 'sup_id',value : liger.get("sup_code").getValue().split(',')[0]});
    	grid.options.parms.push({name : 'sup_no',value : liger.get("sup_code").getValue().split(',')[1]});
    	grid.options.parms.push({name : 'dept_id',value : liger.get("dept_code").getValue().split(',')[0]}); 
    	grid.options.parms.push({name : 'dept_no',value : liger.get("dept_code").getValue().split(',')[1]});
    	grid.options.parms.push({name : 'stocker',value : liger.get("stocker").getValue().split(',')[0]});
    	grid.options.parms.push({name : 'pur_type',value : liger.get("pur_type").getValue()});
    	grid.options.parms.push({name : 'take_hos_id',value : liger.get("take_hos_id").getValue()});
    	grid.options.parms.push({name : 'pay_hos_id',value : liger.get("pay_hos_id").getValue()});
    	
    	grid.options.parms.push({name : 'state',value : liger.get("state").getValue()});
    	grid.options.parms.push({name:'is_dir',value:liger.get("is_dir").getValue()});//是否定向
  	  	if(liger.get("is_dir").getValue()=='1'){//定向部门
  		  grid.options.parms.push({name:'dir_dept_id',value:liger.get("dir_dept_id").getValue().split(",")[0]});//请购单位
  	  	}
		//加载查询条件
		grid.loadData(grid.where);
		
	}
	

	function loadHead(){
		var date = getCurrentDate();
        var aa = date.split(';');
        if(show_detail==1){
        	grid = $("#maingrid").ligerGrid({
    			columns: [{
    					display: '订单编号', name: 'order_code', align: 'left', width: '120',
    					render : function(rowdata, rowindex, value) {
    						return '<a href=javascript:update_open("' 
    							+ rowdata.group_id 
    							+ ',' + rowdata.hos_id 
    							+ ',' + rowdata.copy_code 
    							+ ',' + rowdata.order_id
    							+ '")>'+rowdata.order_code+'</a>';
    					}}, 
    				{display: '材料编码', name: 'inv_code', align: 'left',width: '90'},
    				{display: '材料名称', name: 'inv_name', align: 'left',width: '90'},
    				
    				
      				
    				{display: '订单日期', name: 'order_date', align: 'left',width: '90'},
    			
    				{display: '采购方式', name: 'pur_type', align: 'left',width: '100',hide:true
    				}, 
    				{display: '采购方式', name: 'pur_name', align: 'left',width: '100'
    				}, 
    				{display: '需求库房', name: 'store_name', align: 'left',width: '150'},  
    				{display: '供应商', name: 'sup_name', align: 'left',width: '180'},
    				{display: '手机号', name: 'mobile', align: 'left',width: '100'},
    				{display: '订单类型編碼', name: 'order_type', align: 'left',width: '100',hide:true
    					
    				}, 
    				{display: '订单类型', name: 'order_type_name', align: 'left',width: '100'
    					
    				}, 
    				{display: '采购单位', name: 'pur_hos_name', align: 'left',width: '120'}, 
    				{display: '收货单位', name: 'take_hos_name', align: 'left',width: '120'},
    				{display: '付款单位', name: 'pay_hos_name', align: 'left',width: '120'}, 
    				{display: '采购员', name: 'stocker_name', align: 'left',width: '80'}, 
    				{display: '是否通知', name: 'is_notice', align: 'left',width: '80',
    					render : function(rowdata, rowindex,value) {
    						if(rowdata.is_notice == 0){
    							return "未通知";
    						}else{
    							return "已通知";
    						}
    					}		
    				},{display: '计划到货日期', name: 'arrive_date', align: 'left',width: '80'}, 
    				{display: '通知日期', name: 'notice_date', align: 'left',width: '80'}, 
    				{display: '审核日期', name: 'check_date', align: 'left',width: '80',/* hide:isHideCheck */}, 
    				{display: '状态', name: 'state', align: 'left',width: '60',hide:true
    					
    				},
    				{display: '状态', name: 'state_name', align: 'left',width: '60'
    					
    				},
    				{ display: '是否定向', name: 'is_dir', align: 'left',width:100,
    					 render:function(rowdata){
    						 if(rowdata.is_dir == '1'){
    						 	return "是";
    						 }
    						 if(rowdata.is_dir =='0'){
    							 return "否";
    						 }
    					
    					 }
    				 },
    				 { display: '定向科室', name: 'dir_dept_name', align: 'left',width:100 },
    				{display: '备注', name: 'note', align: 'left',width: '100'}
    			],
    			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatOrderAudit.do?isCheck=true&show_detail=1',
    			width: '100%', height: '100%', checkbox: true, rownumbers:true,
    			delayLoad : true,//初始化不加载，默认false
    			selectRowButtonOnly:true,//heightDiff: -10,
    			toolbar: { items: [
    			   				{ text: '查询（<u>Q</u>）', id:'search', click: query, icon:'search' },
    			   				{ line:true },
    			   				{ text: '审核（<u>A</u>）', id:'audit', click: audit, icon:'audit' },
    			   				{ line:true },
    			   				{ text: '取消审核（<u>U</u>）', id:'unAudit', click: unAudit, icon:'unaudit' },
    			   				{ line:true },
    			   				{ text: '发送（<u>S</u>）', id:'send', click: send, icon:'logout' }, 
    			   				{ line:true }/* , 	
    			   				{ text: '模板打印（<u>P</u>）', id:'print', click: print, icon:'print' } */
    			]}
    		});
            gridManager = $("#maingrid").ligerGetGridManager();
        }else{
    	grid = $("#maingrid").ligerGrid({
			columns: [{
					display: '订单编号', name: 'order_code', align: 'left', width: 120,
					render : function(rowdata, rowindex, value) {
						return '<a href=javascript:update_open("' 
							+ rowdata.group_id 
							+ ',' + rowdata.hos_id 
							+ ',' + rowdata.copy_code 
							+ ',' + rowdata.order_id
							+ '")>'+rowdata.order_code+'</a>';
					}}, 
				{display: '订单日期', name: 'order_date', align: 'left', width: 120},
				{display: '需求库房', name: 'store_name', align: 'left', width: 150},
				{display: '采购方式', name: 'pur_type', align: 'left', width: 120,hide:true
					
				}, 
				{display: '采购方式', name: 'pur_name', align: 'left', width: 120,
					
				}, 
				{display: '供应商', name: 'sup_name', align: 'left', width: 200}, 
				{display: '派工手机号', name: 'mobile', align: 'left', width: 100}, 
				{display: '订单类型', name: 'order_type', align: 'left', width: 120,hide:true
					
				}, 
				{display: '订单类型', name: 'order_type_name', align: 'left', width: 120,
					
				}, 
				{display: '采购单位', name: 'pur_hos_name', align: 'left', width: 120}, 
				{display: '收货单位', name: 'take_hos_name', align: 'left', width: 120},
				{display: '付款单位', name: 'pay_hos_name', align: 'left', width: 120}, 
				{display: '采购部门', name: 'dept_name', align: 'left', width: 120}, 
				{display: '采购员', name: 'stocker_name', align: 'left', width: 120}, 
				{display: '是否通知', name: 'is_notice', align: 'left', width: 100,
					render : function(rowdata, rowindex,value) {
						if(rowdata.is_notice == 0){
							return "未通知";
						}else{
							return "已通知";
						}
					}		
				},
				{display: '通知日期', name: 'notice_date', align: 'left', width: 100}, 
				{display: '审核日期', name: 'check_date', align: 'left', width: 100}, 
				
				{display: '状态', name: 'state_name', align: 'left', width: 120,
					
				},
				{ display: '是否定向', name: 'is_dir', align: 'left',width:100,
					 render:function(rowdata){
						 if(rowdata.is_dir == '1'){
						 	return "是";
						 }
						 if(rowdata.is_dir =='0'){
							 return "否";
						 }
					
					 }
				 },
				 { display: '定向科室', name: 'dir_dept_name', align: 'left',width:100 },
				{display: '备注', name: 'note', align: 'left', width: 120}
			],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatOrderAudit.do?isCheck=true&show_detail=0',
			width: '100%', height: '100%', checkbox: true, rownumbers:true,
			delayLoad: true,//初始化不加载，默认false
			selectRowButtonOnly:true,//heightDiff: -10,
			toolbar: { items: [
				{ text: '查询（<u>Q</u>）', id:'search', click: query, icon:'search' },
				{ line:true },
				{ text: '审核（<u>A</u>）', id:'audit', click: audit, icon:'audit' },
				{ line:true },
				{ text: '取消审核（<u>U</u>）', id:'unAudit', click: unAudit, icon:'unaudit' },
				{ line:true },
				{ text: '发送（<u>S</u>）', id:'send', click: send, icon:'logout' }, 
				{ line:true }/* , 	
				{ text: '模板打印（<u>P</u>）', id:'print', click: print, icon:'print' } */
			]}
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }}
	//键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('A', audit);
		hotkeys('U', unAudit);
		hotkeys('N', send);
		//hotkeys('P', print);
	}
	//是否显示明细
	function showDetail() {
		show_detail = $("#show_detail").is(":checked") ? 1 : 0;
		console.log(show_detail);
		if (grid) {
			//由于一个对象多次绑定相同的事件，需要进行解绑在绑定
			grid.unbind(); 
			grid.bind('contextmenu', grid.options.onContextmenu);
		}
		loadHead();
	}
	
	//订单查看
	function update_open(obj){		
		var vo = obj.split(",");
		var paras = 
			"group_id="+vo[0] +"&"+ 
			"hos_id="+vo[1] +"&"+ 
			"copy_code="+vo[2] +"&"+ 
			"order_id="+vo[3] ;
		//alert(paras);
		$.ligerDialog.open({
			title: '订单查看',
			height: 500,
			width: 1000,
			url: 'matOrderAuditDetailPage.do?isCheck=false&' + paras.toString(),
			modal: true, showToggle: false, showMax: true, showMin: true, isResize: true, top : 1
		});   
    }
	
	//审核
	function audit(){
		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
			return;
		}else{
		
			var ParamVo =[];
			var order_nos = "";
			var notice_nos = "";
			var state = "";
			var data_num=0;
			var state_msg = "";
			$(data).each(function (){

				if(this.is_notice == 1){
					
					notice_nos = notice_nos + this.order_code +",";
					
				}else if(this.is_notice == 0 && this.state == 2){
					
					state_msg = state_msg + this.order_code +",";
					
				}else{
					
					if(this.state == 0){
						order_nos = order_nos + this.order_code + ",";
					}else if(this.state == 2){
						state =  this.state + ",";
					}
					ParamVo.push(
						this.group_id   +"@"+ 
						this.hos_id   +"@"+ 
						this.copy_code   +"@"+ 
						this.order_id 
					) 
					
					data_num=data_num+1;
				}
				
			});
			
			//发送状态单据不允许再次审核
			if(state_msg != ""&&data_num==0){
				$.ligerDialog.error(state_msg+"单据已审核，不允许再次审核！");
				return;
			}
			
			//发送状态单据不允许再次审核
			if(notice_nos != ""){
				$.ligerDialog.error("审核失败！"+order_nos+"单据已发送，不允许再次审核！");
				return;
			}
			//不是新建状态单据不允许审核
			if(order_nos != ""){
				$.ligerDialog.error("审核失败！"+order_nos+"单据是中止状态，不能审核！");
				return;
			}
			//审核状态单据不允许审核
			if(state != ""){
				$.ligerDialog.error(order_nos+"单据是审核状态，不能重复审核！");
				return;
			}
			$.ligerDialog.confirm('确定要审核?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("auditMatOrderAudit.do?isCheck=true",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.state=="true"){
							query();
						}
					});
				}
			}); 
		}
	}
	//取消审核
	function unAudit(){
		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
			return;
		}else{
			var ParamVo =[];
			var order_nos = "";
			var notice_nos = "";
			$(data).each(function (){
				
				if(this.is_notice == 1){
					notice_nos = notice_nos + this.order_code +",";
				}else{
					if(this.state != 2){
						order_nos = order_nos + this.order_code + ",";
					}
					ParamVo.push(
						this.group_id   +"@"+ 
						this.hos_id   +"@"+ 
						this.copy_code   +"@"+ 
						this.order_id 
					) 
				}
			});
			//发送的单据不允许销审
			if(notice_nos != ""){
				$.ligerDialog.error("销审失败！"+notice_nos+"单据已发送，不允许销审！");
				return;
			}
			//未审核的单据不允许销审
			if(order_nos != ""){
				$.ligerDialog.error("销审失败！"+order_nos+"单据不是审核状态");
				return;
			}
			$.ligerDialog.confirm('确定要销审?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("unAuditMatOrderAudit.do?isCheck=true",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.state=="true"){
							query();
						}
					});
				}
			}); 
		}
	}
	
	//发送
	function send(){
		var data = gridManager.getCheckedRows();
		
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
			return;
		}else{
			var ParamVo =[];
			var order_nos = "";
			var notice_nos = "";
			$(data).each(function (){
				 if(this.is_notice == 1){
					notice_nos = notice_nos + this.order_code +",";
				}else{
					if(this.state != 2){
						order_nos = order_nos + this.order_code + ",";
					}
					ParamVo.push(
						this.group_id   +"@"+ 
						this.hos_id   +"@"+ 
						this.copy_code   +"@"+ 
						this.order_id 
					) 
				}
				
			});
			
			//发送过的单据不允许再次发送
			if(notice_nos != "" ){
				$.ligerDialog.error("发送失败，"+order_nos+"单据不允许再次发送");
				return;
			}
			//未审核装填单据不允许发送
			if(order_nos != ""){
				$.ligerDialog.error("发送失败，"+order_nos+"单据不是审核状态");
				return;
			}
			
			if(is_qzj=="true"){
				
				//配置hrp云平台前置机，走弹出窗口确认是否发送平台（因为有的订单不需要发平台），是否发送短信，（接口不需要控制是否发送，都可以重复发送，后台有判断）
				var content='<input type="checkbox" id="is_sms" checked><label>发送短信</label><br/><br/>';
				content=content+'<input type="checkbox" id="is_hrp" checked><label>发送平台</label><br/><br/>';
				content=content+"供应商平台流程：<br/>";
				content=content+"1、供应商发送产品到HRP系统审核。<br/>";
				content=content+"2、HRP系统审核供应商产品，并对应材料。<br/>";
				content=content+"3、HRP系统发送订单给供应商。<br/>";
				content=content+"4、供应商接到订单进行配送，将配送单发送到HRP系统。<br/>";
				$.ligerDialog.open({
					title: '确定发送吗？',
					height: 250,
					width: 350,
					content: content,
					modal: true, showToggle: false, showMax: false, showMin: false, isResize: false,
					buttons: [ { text: '确定',cls:'l-dialog-btn-highlight', onclick: function (item, dialog) {
							var param={
									ParamVo : ParamVo.toString(),
									is_sms:$("#is_sms").prop("checked"),
									is_hrp:$("#is_hrp").prop("checked")
							}
							query();
							mySend(param,dialog);
						
						}},
		    		    { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] 
				})
			}else{
				$.ligerDialog.confirm('确定要发送吗?', function (yes){
					if(yes){
						var param={
								ParamVo : ParamVo.toString(),
								is_sms:true,
								is_hrp:false
						}
						mySend(param);
						query();
					}
				}); 
				query();
			}
			query();
			
		}
	}
	
	function mySend(param,dialog){
		ajaxJsonObjectByUrl("../audit/sendOutMatOrderAudit.do?isCheck=true",param,function (responseData){
			if(responseData.state=="true"){
				
				//HRP云平台推送订单，材料与供应商产品没有做对应关系，弹出窗口对供应商产品做审核操作并绑定对应关系
				if(responseData.is_open){
					parent.$.ligerDialog.open({
						title: '供应商产品审核',
						height: $(window).height(),
						width: $(window).width(),
						url: 'hrp/sys/ecs/sup/querySupProdPage.do?isCheck=false',
						data:{inv_no:responseData.inv_no,check_state:1},
						modal: true, showToggle: false, showMax: true, showMin: true, isResize: true,
						parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
					});
				}else{
					if(dialog){
						dialog.close();
					}
				}
			}
		});
	}
	
	//模板打印
	function print(){
		
	}
	
	function loadDict() {
   	
		//订单类型
		autoCompleteByData("#order_type", matOrderMain_orderType.Rows, "id", "text", true, true);
		//仓库信息
		autocomplete("#store_code","../../queryMatStoreDictDate.do?isCheck=false","id","text",true,true);
		//供应商
		autocomplete("#sup_code","../../queryHosSupDict.do?isCheck=false","id","text",true,true,'',false,'',300);
		//采购部门
		autocomplete("#dept_code","../../queryPurDept.do?isCheck=false","id","text",true,true);
		//采购员
		autocomplete("#stocker","../../queryMatPurStockEmp.do?isCheck=false","id","text",true,true);
		//采购方式
		autoCompleteByData("#pur_type", matOrderMain_purType.Rows, "id", "text", true, true);
		//收货单位
		autocomplete("#take_hos_id","../../../sys/queryHosInfoDictPerm.do?isCheck=false","id","text",true,true,'',false,'',230);
		//付款单位
		autocomplete("#pay_hos_id","../../../sys/queryHosInfoDictPerm.do?isCheck=false","id","text",true,true,'',false,'',230);//付款单位下拉框 
		//状态
		autoCompleteByData("#state", matOrderMain_state.Rows, "id", "text", true, true);
		autocomplete("#dir_dept_id","../../queryMatDeptDictDate.do?isCheck=false","id","text",true,true,{read_or_write:'1'},false, "", 230);//定向科室
		
		
		autodate("#begin_date", "yyyy-MM-dd", "month_first");
        autodate("#end_date", "yyyy-MM-dd", "month_last");
   		
		$("#order_code").ligerTextBox({width:160});
		$("#order_type").ligerTextBox({width:160});
		$("#sup_code").ligerTextBox({width:230});
		$("#dept_code").ligerTextBox({width:160});
        $("#stocker").ligerTextBox({width:160});
        $("#pur_type").ligerTextBox({width:160});
        $("#take_hos_id").ligerTextBox({width:230});
        $("#pay_hos_id").ligerTextBox({width:230});
        $("#state").ligerTextBox({width:160});
        $("#dir_dept_id").ligerTextBox({width:160});
        $("#begin_date").ligerTextBox({width:100});
        $("#end_date").ligerTextBox({width:100});
        $("#inv_code").ligerTextBox({width : 160});
	}
	//打印回调方法
	function lodopPrint() {
		var head = "<table class='head' width='100%'><tr><td>单位：${sessionScope.hos_name}</td></tr>";
		head = head + "<tr><td>制单日期：" + $("#begin_date").val() + " 至  "
				+ $("#end_date").val() + "</td></tr>";
		head = head + "</table>";
		grid.options.lodop.head = head;
		grid.options.lodop.fn = renderFunc;
	/* 	if (liger.get("bus_type_code").getValue() == "") {
			grid.options.lodop.title = "材料入库";
		} else {
			grid.options.lodop.title = liger.get("bus_type_code").getText()
					+ "材料入库";
		} */

	}
	
	function change(){	//采购类型变化,是否定向改变，显示定向部门，显示采购单位、请购单位
		 var para = "";
		 if(liger.get("pur_type").getValue()=='2'){
			 $(".demo").attr("style","visibility:true");
			 para = 1;
		 }else if(liger.get("pur_type").getValue()=='1'){		
			 $(".demo").attr("style","visibility:hidden");
			 para = 0;
		 }else{
			 $(".demo").attr("style","visibility:hidden");
		 }
		 
		 autoCompleteByData("#is_dir", yes_or_no.Rows, "id", "text", true, true,"",false,para,'160');//是否定向
		 changeDir();
	 }
	//定向改变实现定向科室
	 function changeDir(){
		 if(liger.get("is_dir").getValue()=='1'){ 
			 $("#dir_dept_id").attr("type","hidden");
			 $(".dept").attr("style","display:table-cell");
			 loadHead();
		 }else{
			 $(".dept").attr("style","display:none");
			 loadHead();
		 }
	 }

</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" border="0" width="100%">

		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">日期范围：</td>
			<td>
				<table>
				    <tr>
				    	<td >
							<input class="Wdate" name="begin_date" id="begin_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
						<td align="right" class="l-table-edit-td"  style="width: 10px;">至：</td>
						<td  >
							<input class="Wdate" name="end_date" id="end_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
				    </tr>
				</table>
			</td>
			
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">需求库房：</td>
			<td align="left" class="l-table-edit-td">
				<input name="store_code" type="text" requried="false" id="store_code" />
			</td>
			
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">订单类型：</td>
			<td align="left" class="l-table-edit-td">
				<input name="order_type" type="text" requried="false" id="order_type" />
			</td>

		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">供应商：</td>
			<td align="left" class="l-table-edit-td"  >
				<input name="sup_code" type="text" requried="false" id="sup_code" />
			</td>
			

			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">采购部门：</td>
			<td align="left" class="l-table-edit-td">
				<input name="dept_code" type="text" requried="false" id="dept_code" />
			</td>
		

			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">采购员：</td>
			<td align="left" class="l-table-edit-td">
				<input name="stocker" type="text" requried="false" id="stocker" />
			</td>
			
		</tr>
		<tr>
			
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">付款单位：</td>
			<td align="left" class="l-table-edit-td" >
				<input name="pay_hos_id" type="text" requried="false" id="pay_hos_id" />
			</td>
		    <td align="right" class="l-table-edit-td" style="padding-left: 10px;">状态：</td>
			<td align="left" class="l-table-edit-td" >
				<input name="state" type="text" requried="false" id="state" />
			</td>
			 
			
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">采购方式：</td>
			<td align="left" class="l-table-edit-td"  >
				<input name="pur_type" type="text" requried="false" id="pur_type" onChange="change();"/>
			</td>
			
		</tr>
		<tr>
			
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">收货单位：</td>
			<td align="left" class="l-table-edit-td">
				<input name="take_hos_id" type="text" requried="false" id="take_hos_id" />
			</td>
			
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">订单编号：</td>
			<td align="left" class="l-table-edit-td">
				<input name="order_code" type="text" requried="false" id="order_code" />
			</td>
			
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否定向：</td>
            <td align="left" class="l-table-edit-td" >
            	<input name="is_dir" type="text" id="is_dir" ltype="text" onChange="changeDir();"/>
            </td>
		</tr>
		<tr>
        	<td align="right" class="l-table-edit-td dept"  style="padding-left:20px;">定向科室：</td>
            <td align="left" class="l-table-edit-td dept" colspan = '3'>
            	<input name="dir_dept_id" type="text" id="dir_dept_id" ltype="text"  />
            </td>
		</tr>
		<tr>
		  <td align="right" class="l-table-edit-td" width="10%">材料信息：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="inv_code" type="text" id="inv_code" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
              <td align="right" class="l-table-edit-td" width="10%"></td>
			<td align="left" class="l-table-edit-td">
			        <input name="show_detail" type="checkbox" id="show_detail" onclick="showDetail();"/>&nbsp;&nbsp;显示明细
			</td>
            
		</tr>
	</table>

	<div id="maingrid"></div>
	
</body>
</html>
