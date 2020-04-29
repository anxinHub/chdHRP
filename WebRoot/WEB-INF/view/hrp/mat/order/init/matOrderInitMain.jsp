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
    var show_detail=0;   
	var grid; 
	var gridManager = null;
	var userUpdateStr;
	var by_sum_sup = '${p04040 }';
	var isHideCheck = '${p04033 }' == 1 ? false : true;
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
					return "部分执行";
				}else if(value == 4){
					return "执行完毕";
				}else if(value == 8){
					return "已合并";
				}else if(value == 9){
					return "已撤销";
				}
			}
	};
	$(function() {
		
		loadDict()//加载下拉框
		//加载数据
		//loadHead(null);
		loadHotkeys();
		
		//query();
		showDetail();
		//change();
	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'is_sup',
			value : liger.get("is_sup").getValue()
		});
		
		grid.options.parms.push({
			name : 'inv_code',
			value : $("#inv_code").val()
		});
    	grid.options.parms.push({name : 'begin_date',	value : $("#begin_date").val()}); 
    	grid.options.parms.push({name : 'end_date',		value : $("#end_date").val() }); 
    	grid.options.parms.push({name : 'order_code',	value : $("#order_code").val()}); 
    	grid.options.parms.push({name : 'order_type',	value : liger.get("order_type").getValue()});
    	grid.options.parms.push({name : 'sup_id',		value : liger.get("sup_code").getValue() == null ? "" : liger.get("sup_code").getValue().split(",")[0]});
    	grid.options.parms.push({name : 'store_id',		value : liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[0]});
    	grid.options.parms.push({name : 'stocker',		value : liger.get("stocker").getValue().split(",")[0]});
    	if(liger.get("state").getValue() == '' ){
    		grid.options.parms.push({name : 'state_id',	value : "9"});
    	}else{
    		grid.options.parms.push({name : 'state',	value : liger.get("state").getValue()});
    	}
    	grid.options.parms.push({name:'pur_type',value:liger.get("pur_type").getValue()});//计划类型
  	  	if(liger.get("pur_type").getValue()=='2'){
  		  grid.options.parms.push({name:'take_hos_id',value:liger.get("take_hos_id").getValue().split(",")[0]});//请购单位
      	  grid.options.parms.push({name:'pay_hos_id',value:liger.get("pay_hos_id").getValue().split(",")[0]});//请购单位
  	  	}
  	  	grid.options.parms.push({name:'is_dir',value:liger.get("is_dir").getValue()});//是否定向
  	  	if(liger.get("is_dir").getValue()=='1'){//定向部门
  		  grid.options.parms.push({name:'dept_id',value:liger.get("dir_dept_id").getValue().split(",")[0]});//请购单位
  	  	}
  	  	grid.options.parms.push({name:'is_notice',value:liger.get("is_notice").getValue()});//是否已通知
  	  	
  	    grid.options.parms.push({name:'is_good',value:liger.get("is_good").getValue()});
    	
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
    				{display: '规格型号', name: 'inv_model', align: 'left',width: '90'},
    				{display: '单位', name: 'unit_name', align: 'left',width: '90'},
    				{ display: '数量', name: 'amount', align: 'right', width: '100'}, 
      				{ display: '金额', name: 'amount_money', align: 'right', width: '100',
      		 			render : function(rowdata, rowindex, value) {
      						return formatNumber(rowdata.amount_money ==null ? 0 : rowdata.amount_money, '${p04005 }', 1);
      					}
      		 		}, 
    				{display: '订单日期', name: 'order_date', align: 'left',width: '90'},
    				{display: '订单来源', name: 'come_from', align: 'left',width: '90'}, 
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
    				{display: '审核日期', name: 'check_date', align: 'left',width: '80',hide:isHideCheck}, 
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
    			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatOrderInit.do?isCheck=true&show_detail=1',
    			width: '100%', height: '100%', checkbox: true, rownumbers:true,
    			gid: 'detailGrid', 
    			delayLoad : false,//初始化不加载，默认false
    			selectRowButtonOnly:true,//heightDiff: -10,
    			toolbar: { items: [
						{ text: '查询（<u>Q</u>）', id:'search', click: query, icon:'search' },
						{ line:true },
						{ text: '添加（<u>A</u>）', id:'add', click: add_open, icon:'add' },
						{ line:true },
						{ text: '删除（<u>D</u>）', id:'del', click: del, icon:'delete' },
						{ line:true },
    			]}
    		});
            gridManager = $("#maingrid").ligerGetGridManager();
        }else{
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
    				{display: '订单日期', name: 'order_date', align: 'left',width: '90'},
    				{display: '订单来源', name: 'come_from', align: 'left',width: '90'}, 
    				{display: '采购方式', name: 'pur_type', align: 'left',width: '100',hide:true
    				}, 
    				{display: '采购方式', name: 'pur_name', align: 'left',width: '100'
    				}, 
    				{display: '需求库房', name: 'store_name', align: 'left',width: '150'},  
    				{display: '供应商', name: 'sup_name', align: 'left',width: '180'},
    				{display: '手机号', name: 'mobile', align: 'left',width: '100'},
    				{display: '是否通知', name: 'is_notice', align: 'left',width: '120',
    					render : function(rowdata, rowindex,value) {
    						if(rowdata.is_notice == 0){
    							return "未通知";
    						}else if(rowdata.is_notice == 2 || rowdata.is_notice == 3  ){// 
    							return '<a href=javascript:get_delivery("' 
    							+ rowdata.group_id 
    							+ ',' + rowdata.hos_id 
    							+ ',' + rowdata.copy_code 
    							+ ',' + rowdata.order_id
    							+ ',' + rowdata.is_notice  
    							+ '")>获取省平台配送信息</a>';
    						}else if( rowdata.is_notice == 5 ){
    							return '<a href=javascript:get_delivery("'      
    							+ rowdata.group_id 
    							+ ',' + rowdata.hos_id 
    							+ ',' + rowdata.copy_code 
    							+ ',' + rowdata.order_id
    							+ ',' + rowdata.is_notice
    							+ '")>省平台和供应商平台都已发送</a>';
    						}else{
    							return "已通知";
    						}
    	
    					}		
    				},
    				{display: '订单类型編碼', name: 'order_type', align: 'left',width: '100',hide:true
    					
    				}, 
    				{display: '订单类型', name: 'order_type_name', align: 'left',width: '100'
    					
    				}, 
    				{display: '采购单位', name: 'pur_hos_name', align: 'left',width: '120'}, 
    				{display: '收货单位', name: 'take_hos_name', align: 'left',width: '120'},
    				{display: '付款单位', name: 'pay_hos_name', align: 'left',width: '120'}, 
    				{display: '采购员', name: 'stocker_name', align: 'left',width: '80'}, 
    				{display: '计划到货日期', name: 'arrive_date', align: 'left',width: '80'}, 
    				
    				{ display: '金额', name: 'amount_money', align: 'right', width: '100',
    		 			render : function(rowdata, rowindex, value) {
    						return formatNumber(rowdata.amount_money ==null ? 0 : rowdata.amount_money, '${p04005 }', 1);
    					}
    		 		}, 
    				{display: '通知日期', name: 'notice_date', align: 'left',width: '80'}, 
    				{display: '审核日期', name: 'check_date', align: 'left',width: '80',hide:isHideCheck}, 
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
    			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatOrderInit.do?isCheck=true&show_detail=0',
    			width: '100%', height: '100%', checkbox: true, rownumbers:true,
    			delayLoad : true,//初始化不加载，默认false
    			selectRowButtonOnly:true,//heightDiff: -10,
    			toolbar: { items: [
    				{ text: '查询（<u>Q</u>）', id:'search', click: query, icon:'search' },
    				{ line:true },
    				{ text: '添加（<u>A</u>）', id:'add', click: add_open, icon:'add' },
    				{ line:true },
    				{ text: '删除（<u>D</u>）', id:'del', click: del, icon:'delete' },
    				{ line:true },
    				{ text: '审核（<u>A</u>）', id:'audit', click: audit, icon:'audit',hide:isHideCheck},
    				{ line:true,hide:isHideCheck},
    				{ text: '销审（<u>U</u>）', id:'unAudit', click: unAudit, icon:'unaudit',hide:isHideCheck},
    				{ line:true,hide:isHideCheck},
    				/* { text: '据采购计划生成（<u>G</u>）', id:'gen_pur', click: gen_pur, icon:'settle' },
    				{ line:true }, */
    				{ text: '发送（<u>S</u>）', id:'send', click: send, icon:'logout' },
    				{ line:true },
    				{ text: '撤销', id:'revoke', click: revoke, icon:'revoke' },
    				{ line:true },
    				/*{ text: '省平台发送（<u>S</u>）', id:'provincePlatformSend', click: provincePlatformSend, icon:'logout' },
    				{ line:true },
    				{ text: '省平台撤销', id:'provincePlatformRevoke', click: provincePlatformRevoke, icon:'logout' },
    				{ line:true }, */
    				{ text: '采购计划导入（<u>I</u>）', id:'purImp', click: purImp, icon:'settle' ,hide: by_sum_sup == 1 ? true : false},
    				{ line:true ,hide: by_sum_sup == 1 ? true : false}, 
    				 { text: '采购计划导入（<u>M</u>）', id:'purImpS', click: purImpS, icon:'settle' ,hide: by_sum_sup == 0 ? true : false},
    				{ line:true ,hide: by_sum_sup == 0 ? true : false}, 
    				{ text: '代销使用生成', id:'out_no', click: out_no, icon:'settle' },
    				{ line:true } ,
    				{ text: '批量打印（<u>P</u>）', id:'print', click: print, icon:'print' },
    				{ line:true }, 	
    				{ text: '模板设置', id:'printSet1', click: printSet1, icon:'settings' },
    				{ line:true },
    				{ text: '合并（<u>M</u>）', id:'merge', click: merge, icon:'copy' },
    				{ line:true },
    				{ text: '终止（<u>T</u>）', id:'abort', click: abort, icon:'cut' },
    				{ line:true },
    				{ text: '评价', id:'add', click: eva, icon:'add' },
    			]}
    		});
            gridManager = $("#maingrid").ligerGetGridManager();
        }
    	
        
    }
	//键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('A', add_open);
		hotkeys('G', gen_pur);
		hotkeys('I', purImp);
		hotkeys('M',merge );
		hotkeys('T', abort);
		hotkeys('D', del);
		//hotkeys('P', print);
	}
	//添加	
	function add_open(){
		parent.$.ligerDialog.open({
			title: '订单添加',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/mat/order/init/matOrderInitAddPage.do?isCheck=false', 
			modal: true, showToggle: false, showMax: true, showMin: true, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
	}
	
	//修改
	function update_open(obj){		
		var vo = obj.split(",");
		var parm = "group_id="+vo[0]+"&hos_id="+vo[1]+"&copy_code="+vo[2]+"&order_id="+vo[3]; 
		parent.$.ligerDialog.open({ 
			title: '订单修改',
			height: $(window).height(),
			width: $(window).width(),
			url : 'hrp/mat/order/init/matOrderInitUpdatePage.do?isCheck=false&' + parm, 
			modal:true,showToggle:false,showMax:true ,showMin: true,isResize:true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
		
    }
	
	
	function update_mat_in(obj) {
		console.log(obj);
		var vo = obj.split(",");
		var paras = "group_id=" + vo[0] + "&" + "hos_id=" + vo[1] + "&"
				+ "copy_code=" + vo[2] + "&" + "in_id=" + vo[3] + "&"
				+ "in_no=" + vo[4];
		parent.$.ligerDialog.open({
			title : '入库单修改',
			height : $(window).height(),
			width : $(window).width(),
			url : 'hrp/mat/storage/in/updatePage.do?isCheck=false&'
					+ paras.toString(),
			modal : true,
			showToggle : false,
			showMax : true,
			showMin : true,
			isResize : true,
			parentframename : window.name, //用于parent弹出层调用本页面的方法或变量
		});
	}
	
	//获取省平台配送信息
	function get_delivery(obj){		
		var vo = obj.split(",");
		var parm = "group_id="+vo[0]+"&hos_id="+vo[1]+"&copy_code="+vo[2]+"&order_id="+vo[3]+"&is_notice="+vo[4]; 
		parent.$.ligerDialog.open({ 
			title: '省平台配送单获取',
			height: $(window).height(),
			width: $(window).width(),
			url : 'hrp/mat/order/init/matOrderPlatformDeliveryMainPage.do?isCheck=false&' + parm, 
			modal:true,showToggle:false,showMax:true ,showMin: true,isResize:true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
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
			$(data).each(function (){
				
				if(this.is_notice == 1){
					notice_nos = notice_nos + this.order_code +",";
				}else{
					if(this.state == 0){
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
			$.ligerDialog.confirm('确定要审核?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("../audit/auditMatOrderAudit.do?isCheck=true",{ParamVo : ParamVo.toString()},function (responseData){
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
					ajaxJsonObjectByUrl("../audit/unAuditMatOrderAudit.do?isCheck=true",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.state=="true"){
							query();
						}
					});
				}
			}); 
		}
	}
	
	//删除
	function del(){
		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择要删除的订单！');
			return;
		}else{
			var ParamVo =[];
			var order_nos = "";
			$(data).each(function (){		
				if(this.state != 1){
					order_nos = order_nos + this.order_code + ",";
				}
				ParamVo.push(
					this.group_id   +"@"+ 
					this.hos_id   +"@"+ 
					this.copy_code   +"@"+ 
					this.order_id 
				) 
			});
			if(order_nos != ""){
				$.ligerDialog.error("删除失败！"+order_nos+"单据不是新建状态");
				return;
			}
			$.ligerDialog.confirm('确定要删除?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("deleteMatOrderInit.do?isCheck=true",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.state=="true"){
							query();
						}
					});
				}
			}); 
		}
	}
	
	function eva(){
		
		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.warn('请选择要评价的订单！');
			return;
		}else{
			var ParamVo =[];
			var order_nos = "";
			var sup_id = data[0].sup_id;
			var sup_no = data[0].sup_no;
			var sup_code = data[0].sup_code;
			var sup_name = data[0].sup_name;
			var order_ids = "";
			$(data).each(function (){
				
				if(sup_id != this.sup_id){
					$.ligerDialog.warn("当前所选单据不是同一供应商！");
					return;
				}
				if(!isHideCheck){
					if(this.state != 2){
						order_nos = order_nos + this.order_code + ",";
					}
				}
				order_ids += this.order_id + ",";
			});
			
			if(order_nos != ""){
				$.ligerDialog.warn("评价失败！"+order_nos+"单据不是审核状态");
				return;
			}
			
			var paras = "order_ids=" + order_ids.substring(0, order_ids.length-1) + 
			  "&" + "sup_id=" + sup_id + 
			  "&" + "sup_no=" + sup_no + 
			  "&" + "sup_code=" + sup_code + 
			  "&" + "sup_name=" + sup_name + 
			  "&" + "isAdd=1";

			parent.$.ligerDialog.open({
				title : '评价维护页面',
				height : $(window).height(),
				width : $(window).width(),
				url : 'hrp/mat/eva/sup/matEvaSupUpdatePage.do?isCheck=false&' + paras.toString(),
				modal : true,
				showToggle : false,
				showMax : true,
				showMin : true,
				isResize : true,
				parentframename : window.name, //用于parent弹出层调用本页面的方法或变量
			});
		}
	}
	
	//打印回调方法
	function lodopPrint() {
		var head = "<table class='head' width='100%'><tr><td>单位：${sessionScope.hos_name}</td></tr>";
		head = head + "<tr><td>制单日期：" + $("#begin_date").val() + " 至  "
				+ $("#end_date").val() + "</td></tr>";
		head = head + "</table>";
		grid.options.lodop.head = head;
		grid.options.lodop.fn = renderFunc;
		

	}
	//据采购计划生成
	function  gen_pur(){
		parent.$.ligerDialog.open({
			title: '采购计划生成',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/mat/order/init/matOrderInitGenPurPage.do?isCheck=false', 
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
		
	}
	
	//导入采购计划new
	function purImpS(){
		parent.$.ligerDialog.open({
			title: '采购计划导入',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/mat/order/init/matOrderPurImpPage.do?isCheck=false',
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true, top: 1, 
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
	}
	
	//导入采购计划
	function  purImp(){
		parent.$.ligerDialog.open({
			title: '采购计划导入',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/mat/order/init/matOrderInitPurImpPage.do?isCheck=false',
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true, top: 1, 
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
	}
	
	//代销使用生成
	function  out_no(){
		$.ligerDialog.open({
			title: '代销使用生成',
			height: 550,
			width: 1000,
			url: 'matOrderInitAffiPage.do?isCheck=false',
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true, top: 1
		});
	}
	
	//合并订单
	function merge(){
		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.warn('请选择要合并的订单！');
			return;
		}else{
			var orderNo = "";
			var ParamVo =[];
			var order_nos = "";
			$(data).each(function (){	
				
				if(this.state == 8){
					orderNo += this.order_code + ","
				}else{
					ParamVo.push(
						this.group_id   +"@"+ 
						this.hos_id   +"@"+ 
						this.copy_code   +"@"+ 
						this.order_id 
					); 
				}	
			});
			
			if(orderNo != ""){
				$.ligerDialog.warn("单据["+ orderNo.substring(0, orderNo.length - 1) +"]已经被合并！");
				return;
			}
			
			$.ligerDialog.confirm('确定要合并吗?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("mergeMatOrderInit.do?isCheck=true",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.state=="true"){
							query();
						}
					});
				}
			}); 
		}
	}
	
	//中止订单
	function abort(){
		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择要中止的单据！');
			return;
		}else{
			var ParamVo =[];
			var order_nos = "";
			$(data).each(function (){		
				if(this.state != 2){
					order_nos = order_nos + this.order_code + ",";
				}
				ParamVo.push(
					this.group_id   +"@"+ 
					this.hos_id   +"@"+ 
					this.copy_code   +"@"+ 
					this.order_id 
				) 
			});
			/* if(order_nos != ""){
				$.ligerDialog.error("中止失败！"+order_nos+"单据不是审核状态");
				return;
			} */
			$.ligerDialog.confirm('确定要中止?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("abortMatOrderInit.do?isCheck=true",{ParamVo : ParamVo.toString()},function (responseData){
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
				 if(this.is_notice == 1 &&  this.is_notice != 2){
					 
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
					}
				}); 
			}
			
		}
	}
	
	
	function revoke(){
		var data = gridManager.getCheckedRows();
		
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
			return;
		}else{
			var ParamVo =[];
			var notice_nos = "";
			
			$(data).each(function(){
				if( this.is_notice == 0 ){
					notice_nos = notice_nos + this.order_code +",";
				}else{
					ParamVo.push(
						this.order_id +"@"+
						this.order_code
					) 
				}
			});
			
			//发送过的单据不允许再次发送
			if(notice_nos != "" ){
				$.ligerDialog.error("撤销失败，"+notice_nos+"单据处于未通知状态，无法撤销");
				return;
			}
			
			$.ligerDialog.confirm('确定要撤销吗?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("../audit/revokeOutMatOrder.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.state=="true"){
							query();
						}else{
							$.ligerDialog.error(responseData.errMsg+",撤销失败!");
							query();
						}
					})
					
				}
			}); 
		}
	}
	
	//浙江省平台发送
	function provincePlatformSend(){
		
		var data = gridManager.getCheckedRows();
		
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
			return;
		}else if(data.length >1){
			$.ligerDialog.warn('省平台发送功能暂时只支持单笔订单发送');
		}else{
			var orderStr ="";
			var order_nos = "";
			var notice_nos = "";
			$(data).each(function (){
				 if(this.is_notice > 1){
					notice_nos = notice_nos + this.order_code +",";
				}else{
					if(this.state != 2){
						order_nos = order_nos + this.order_code + ",";
					}
					orderStr=this.order_id ;
					
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
			
			
			$.ligerDialog.confirm('确定要发送吗?', function (yes){
				if(yes){
					var param={
							orderId :orderStr,
					}
					
					ajaxJsonObjectByUrl("../audit/sendOrderToProvincePlatfrom.do?isCheck=false",param,function (responseData){
						if(responseData.state=="true"){
							//HRP云平台推送订单，材料与供应商产品没有做对应关系，弹出窗口对供应商产品做审核操作并绑定对应关系
							//alert(1);
							query();
						}else{
							$.ligerDialog.error(responseData.errMsg+",发送失败!");
							query();
						}
					})
					
				}
			}); 
			
			
		}
	}
	
	
	
	//省平台撤销
	function provincePlatformRevoke(){
		var data = gridManager.getCheckedRows();
		
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
			return;
		}else if(data.length >1){
			$.ligerDialog.warn('省平台撤销功能暂时只支持单笔订单撤销');
		}else{
			var orderStr ="";
			var order_nos = "";
			var notice_nos = "";
			var states = "";
			var is_notices = "";
			$(data).each(function (){
				 if(this.is_notice == 0){
					notice_nos = notice_nos + this.order_code +",";
				}else{
					if(this.state != 2){
						order_nos = order_nos + this.order_code + ",";
					}
					orderStr=this.order_id ;
					
				}
				
			});
			//未通知不许撤销
			if(notice_nos != "" ){
				$.ligerDialog.error("撤销失败，"+order_nos+"未通知");
				return;
			}
			//未审核装填单据不允许撤销
			if(order_nos != ""){
				$.ligerDialog.error("撤销失败，"+order_nos+"单据不是审核状态");
				return;
			}
			
			
			$.ligerDialog.confirm('确定要撤销吗?', function (yes){
				if(yes){
					var param={
							orderId :orderStr,
					}
					
					ajaxJsonObjectByUrl("../audit/revokeOrderToProvincePlatfrom.do?isCheck=false",param,function (responseData){
						if(responseData.state=="true"){
							//HRP云平台推送订单，材料与供应商产品没有做对应关系，弹出窗口对供应商产品做审核操作并绑定对应关系
							//alert(1);
							query();
						}else{
							$.ligerDialog.error(responseData.errMsg+",撤销失败!");
							query();
						}
					})
					
				}
			}); 
			
			
		}
		
		
	}
	
	
	function mySend(param,dialog){
		ajaxJsonObjectByUrl("../audit/sendOutMatOrderAudit.do?isCheck=true",param,function (responseData){
			if(responseData.state=="true"){
				//HRP云平台推送订单，材料与供应商产品没有做对应关系，弹出窗口对供应商产品做审核操作并绑定对应关系
				if(responseData.errSupMsgs){
					$.ligerDialog.error("发送成功，但"+responseData.errSupMsgs);
				} 
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
					query();
				}
			}
		});
	}
	
	//打印设置
	function printSet(){
		
		var useId=0;//统一打印
		
		var useId=0;//统一打印
		if('${p04023 }'==1){
			//按用户打印
			useId='${sessionScope.user_id }';
		}
			
		
		parent.parent.$.ligerDialog.open({url : 'hrp/mat/order/init/matOrderInitPrintSetPage.do?template_code=04006&use_id='+useId,
			data:{}, height: $(parent).height(),width: $(parent).width(), title:'打印模板设置',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
		});
	}
	
	function printSet1(){
		officeFormTemplate({template_code:"04006"});
	}
	
	
    //打印
    function print(){
    	
    	 /**var start_date = $("#begin_date").val() + " 00:00"; 
    	 var end_date = $("#end_date").val() + " 00:00"; 
    	 start_date = new Date(start_date.replace(/-/g, "/")); 
    	 end_date = new Date(end_date.replace(/-/g, "/")); 
    	  if(start_date.getMonth() != end_date.getMonth()) { 
    		  $.ligerDialog.error("不支持跨月打印！"); 
    	      return false;  
    	 } */
    	  
    	 var dateData = gridManager.getCheckedRows();
    	 $(dateData).each(function(){
    		 var start_date = $("#begin_date").val() + " 00:00"; 
    		 var end_date = $("#end_date").val() + " 00:00"; 
         	  if(this.order_date.substring(5,7) != start_date.substring(5,7) || this.order_date.substring(5,7) != end_date.substring(5,7)) { 
         		  $.ligerDialog.error("不支持跨月打印！"); 
         	      return false;  
         	 }
    	 });
     	  
    	   
    	 var useId=0;//统一打印
 		if('${p04023 }'==1){
 			//按用户打印
 			useId='${sessionScope.user_id }';
 		}else if('${p04023 }'==2){
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
			
			var order_id ="" ;
			var in_nos = "";
			$(data).each(function (){		
				if(this.state != 2){
					in_nos = in_nos + this.in_no + "<br>";
				}
				
				order_id  += this.order_id+","
					
			});
			
// 			if(in_nos != ""){
// 				$.ligerDialog.error("打印失败！<br>以下单据不是已审核状态：<br>"+in_nos);
// 				return;
// 			} 
			
			
// 			 var para={
// 	    			paraId :order_id.substring(0,order_id.length-1) ,
	    			
// 	    			template_code:'04006',
// 	    			isPrintCount:false,//更新打印次数
// 	    			isPreview:true,//预览窗口，传绝对路径
// 	    			use_id:useId,
// 	    			p_num:1
// 	    			//isSetPrint:flag
// 	    	}; 
		 	
			//alert(JSON.stringify(para));
			
	    	//printTemplate("hrp/mat/order/init/queryMatOrderInitByPrintTemlate.do?isCheck=false",para);
			var para={
					
					template_code:'04006',
					class_name:"com.chd.hrp.mat.serviceImpl.order.init.MatOrderInitServiceImpl",
					method_name:"queryMatOrderInitByPrintTemlatePage",
					isSetPrint:false,//是否套打，默认非套打
					isPreview:true,//是否预览，默认直接打印
					paraId :order_id.substring(0,order_id.length-1) ,
					//order_id:$("#order_id").val(),
					p_num:1,
	 				use_id:useId
			};
			
			officeFormPrint(para);
	    	
		}
    	
    }
	
	function loadDict() {
		//返回当前年,当前月,当前日期,当前月第一天,当前月最后一天,上个月,上月第一天，上月最后一天
		var date = getCurrentDate();
        var aa = date.split(';');
       
		$("#begin_date").val(aa[3]);
   		$("#end_date").val(aa[4]);
   	
		//订单类型
		autoCompleteByData("#order_type", matOrderMain_orderType.Rows, "id", "text", true, true,"",false);
		//供应商
		autocomplete("#sup_code","../../queryHosSupDict.do?isCheck=false","id","text",true,true,'',false,'',240);
		//仓库信息
		autocompleteAsync("#store_code","../../queryMatStoreDictDate.do?isCheck=false","id","text",true,true);
		//采购员
		autocomplete("#stocker","../../queryMatPurStockEmp.do?isCheck=false","id","text",true,true);
		//采购方式
		autoCompleteByData("#pur_type", matOrderMain_purType.Rows, "id", "text", true, true,{sel_flag : 'init'},true);
		//收货单位
		autocomplete("#take_hos_id","../../queryMatHosInfoDict.do?isCheck=false","id","text",true,true);
		//付款单位
		autocomplete("#pay_hos_id","../../queryMatHosInfoDict.do?isCheck=false","id","text",true,true);//付款单位下拉框 
		//状态
		if(isHideCheck == false){
			autoCompleteByData("#state", matOrderMain_state.Rows, "id", "text", true, true);
		}else{
			autoCompleteByData("#state", matOrderMain_state2.Rows, "id", "text", true, true);
		}
		autoCompleteByData("#is_dir", yes_or_no.Rows, "id", "text", true, true,'',false,false,'160');//是否定向
		autoCompleteByData("#is_notice", yes_or_no.Rows, "id", "text", true, true,'',false,false,'160');//是否已通知
		autocomplete("#dir_dept_id","../../queryMatDeptDictDate.do?isCheck=false","id","text",true,true,{is_last:'1',read_or_write:'1'},false);//定向科室	
		 
		$("#is_dir").bind("change", function(){
			changeDir();
		})
		$("#pur_type").bind("change", function(){
			change();
		})
		
   		$("#begin_date").ligerTextBox({width:100});
		autodate("#begin_date", "yyyy-mm-dd", "month_first");
		$("#end_date").ligerTextBox({width:100});
		   autodate("#end_date", "yyyy-mm-dd", "month_last");
		$("#order_code").ligerTextBox({width:240}); 
		$("#sup_code").ligerTextBox({width:240});
		$("#dir_dept_id").ligerTextBox({width:160});
        $("#stocker").ligerTextBox({width:160});
        $("#pur_type").ligerTextBox({width:240});
        $("#take_hos_id").ligerTextBox({width:160});
        $("#pay_hos_id").ligerTextBox({width:160});
        $("#state").ligerTextBox({width:160});
        $('#is_sup').ligerComboBox({
			data:[{id:1,text:'是'},{id:0,text:'否'}],
			valueField: 'id',
            textField: 'text',
			cancelable:true
		});
        $("#inv_code").ligerTextBox({width : 160});
        $("#is_sup").ligerTextBox({width:160});
        
        $('#is_good').ligerComboBox({
			data:[{id:1,text:'是'},{id:0,text:'否'}],
			valueField: 'id',
            textField: 'text',
			cancelable:true
		})
		$("#is_good").ligerComboBox({width:160});
	}
	
	function change(){	//采购类型变化,是否定向改变，显示定向部门，显示采购单位、请购单位
		 var para = "";
		 if(liger.get("pur_type").getValue()=='2'){
			 //$(".demo").attr("style","visibility:true");
			 $(".demo").css("display", "");
			 para = 1;
		 }else if(liger.get("pur_type").getValue()=='1'){		
			 //$(".demo").attr("style","visibility:hidden");
			 $(".demo").css("display", "none");
			 para = 0;
		 }else{
			 //$(".demo").attr("style","visibility:hidden");
			 $(".demo").css("display", "none");
		 } 
		 
		 autoCompleteByData("#is_dir", yes_or_no.Rows, "id", "text", true, true,"",false,para,'160');//是否定向
		 changeDir();
	 }
	//是否显示明细
	function showDetail() {
		show_detail = $("#show_detail").is(":checked") ? 1 : 0;
		if (grid) {
			//由于一个对象多次绑定相同的事件，需要进行解绑在绑定
			grid.unbind(); 
			grid.bind('contextmenu', grid.options.onContextmenu);
		}
		loadHead();
	}
	//定向改变实现定向科室
	 function changeDir(){
		 if(liger.get("is_dir").getValue()=='1'){ 
			 $("#dir_dept_id").attr("type","hidden");
			 $(".dept").css("display", "");
			 //loadHead();
		 }else{
			 $(".dept").css("display", "none");
			 //loadHead();
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
			<td align="left" class="l-table-edit-td" style="width: 100px;">
				<table>
					<tr>
						<td align="right" >
							<input class="Wdate" name="begin_date" id="begin_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
						<td align="left" class="l-table-edit-td">
							至：
						</td>
						<td align="left" class="l-table-edit-td">
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
				<input name="order_type" type="text" requried="false"  id="order_type" />
			</td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">供应商：</td>
			<td align="left" class="l-table-edit-td" >
				<input name="sup_code" type="text" requried="false" id="sup_code" />
			</td>
			
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">采购员：</td>
			<td align="left" class="l-table-edit-td">
				<input name="stocker" type="text" requried="false" id="stocker" />
			</td>
			
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">状态：</td>
			<td align="left" class="l-table-edit-td" >
				<input name="state" type="text" requried="false" id="state" />
			</td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">采购方式：</td>
			<td align="left" class="l-table-edit-td" >
				<input name="pur_type" type="text" requried="false" id="pur_type" />
			</td>
			
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否定向：</td>
            <td align="left" class="l-table-edit-td" >
            	<input name="is_dir" type="text" id="is_dir" ltype="text"/>
            </td>
            
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否已通知：</td>
            <td align="left" class="l-table-edit-td" >
            	<input name="is_notice" type="text" id="is_notice" ltype="text"/>
            </td>
		</tr>
		<tr >
        	<td align="right" class="l-table-edit-td dept"  style="padding-left:20px;">定向科室：</td>
            <td align="left" class="l-table-edit-td dept" >
            	<input name="dir_dept_id" type="text" id="dir_dept_id" ltype="text"  />
            </td>
			
			<td align="right" class="l-table-edit-td demo" style="padding-left: 10px;">收货单位：</td>
			<td align="left" class="l-table-edit-td demo">
				<input name="take_hos_id" type="text" requried="false" id="take_hos_id" />
			</td>
			
			<td align="right" class="l-table-edit-td demo" style="padding-left: 10px;">付款单位：</td>
			<td align="left" class="l-table-edit-td demo" >
				<input name="pay_hos_id" type="text" requried="false" id="pay_hos_id" />
			</td>
        </tr>
        <tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">订单编号：</td>
			<td align="left" class="l-table-edit-td">
				<input name="order_code" type="text" requried="false" id="order_code" />
			</td>
				<td align="right" class="l-table-edit-td"  style="padding-left:20px;">供应商平台：</td>
            <td align="left" class="l-table-edit-td">
                	<input name="is_sup" type="text" id="is_sup"  />
            
            <td align="right" class="l-table-edit-td" width="10%">材料信息：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="inv_code" type="text" id="inv_code" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
        </tr>
        <tr>
	        <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否线上：</td>
	            <td align="left" class="l-table-edit-td" >
	            <input name="is_good" type="text" id="is_good" ltype="text"/>
            </td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" width="10%"></td>
			<td align="left" class="l-table-edit-td">
			       <input name="show_detail" type="checkbox" id="show_detail" onclick="showDetail();"/>&nbsp;&nbsp;显示明细
			</td>
		</tr>	
	</table>
	<div id="maingrid"></div>
	
</body>
</html>
