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
    $(function ()
    {
        loadDict();//加载下拉框
    	//加载数据
    	loadHead(null);	
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
		grid.options.parms.push({name : 'confirm_dateB',	value : $("#confirm_dateB").val()});
		grid.options.parms.push({name : 'confirm_dateE',  	value : $("#confirm_dateE").val()}); 
		grid.options.parms.push({name : 'in_dateB',	value : $("#in_dateB").val()});
		grid.options.parms.push({name : 'in_dateE',   value : $("#in_dateE").val()}); 
		grid.options.parms.push({name : 'store_id',  	value : liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[0]}); 
		grid.options.parms.push({name : 'bus_type_code',  	value : liger.get("bus_type_code").getValue() == null ? "" : liger.get("bus_type_code").getValue() == "" ? "27" : liger.get("bus_type_code").getValue()}); 
		grid.options.parms.push({name : 'sup_id',  		value : liger.get("sup_code").getValue() == null ? "" : liger.get("sup_code").getValue().split(",")[0]}); 
		grid.options.parms.push({name : 'state',     	value : liger.get("state").getValue() == null ? "" : liger.get("state").getValue().split(",")[0]}); 
		grid.options.parms.push({name : 'in_no',	value : $("#in_no").val()});
		if(show_detail == 1){
			
	  		/* grid.options.parms.push({name:'inv_code',value:liger.get("inv_code").getText().split(" ")[0]}); */
	  		grid.options.parms.push({name:'inv_code',value:$("#inv_code").val()});
	  		grid.options.parms.push({name:'batch_no',value:$("#batch_no").val()});
	  		grid.options.parms.push({name:'bar_code',value:$("#bar_code").val()});
		}
		//alert(liger.get("store_code").getValue());
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	if(show_detail=="1"){
    		grid = $("#maingrid").ligerGrid({
				columns: [{
						display: '入库单号', name: 'in_no', align: 'left', width: 130,
						render : function(rowdata, rowindex, value) {
							if(value == '合计'){
								return value ; 
							}
							return '<a href=javascript:update_open("' 
								+ rowdata.group_id 
								+ ',' + rowdata.hos_id 
								+ ',' + rowdata.copy_code 
								+ ',' + rowdata.in_id
								+ '")>'+rowdata.in_no+'</a>';
						}
					},{display: '单据来源', name: 'field_desc', align: 'left', width: '120' 
					},{display: '仓库', name: 'store_name', align: 'left',width: '100' 
		    		},{ display: '药品编码', name: 'inv_code', align: 'left', width: '120'
			 		},{ display: '药品名称', name: 'inv_name', align: 'left', width: '120'
			 		},{ display: '计量单位', name: 'unit_name', align: 'left', width: '60'
			 		},{ display: '规格型号', name: 'inv_model', align: 'left', width: '120'
			 		},{ display: '单价', name: 'price', align: 'right', width: '80',
			 			render: function (rowdata, rowindex, value) {
							return formatNumber(value, '${p08006 }', 1);
						}
			 		},{ display: '数量', name: 'amount', align: 'right', width: '80'
			 		},{ 
			 			display: '金额', name: 'amount_money', align: 'right', width: '100',
			 			render : function(rowdata, rowindex, value) {
							return formatNumber(rowdata.amount_money ==null ? 0 : rowdata.amount_money, '${p08005 }', 1);
						}
			 		},{ display: '批号', name: 'batch_no', align: 'left', width: '80'
			 		},{ display: '条形码', name: 'bar_code', align: 'left', width: '80'
			 		},{ display: '生产厂商', name: 'fac_name', align: 'left', width: '80'
			 		},{display: '货单号', name: 'delivery_code', align: 'left',width: '90'
			 		},{display: '验收员', name: 'examiner_name', align: 'left',width: '90'
			 		},{display: '编制日期', name: 'in_date', align: 'left',width: '90'
			 		},{display: '制单人', name: 'maker_name', align: 'left',width: '90'
			 		},{display: '制单人', name: 'maker', align: 'left'
			 		},{display: '审核人', name: 'checker_name', align: 'left',width: '90'
			 		},{display: '确认日期', name: 'confirm_date', align: 'left',width: '90'
			 		},{display: '确认人', name: 'confirmer_name', align: 'left',width: '90'
			 		},{display: '单据状态', name: 'state', align: 'left',width: '90',hide:true
			 			/* render : function(rowdata, rowindex,value) {
			 				if(value == null){
			 					return "";
			 				}
							if(rowdata.state == 1){
								return "未审核";
							}else if(rowdata.state == 2){
								return "已审核";
							}else{
								return "已入库";
							}
						} */	
			 		},{display: '状态', name: 'state_name', align: 'left',width: '90',
			 			
			 		}
			 	],
				dataAction: 'server',dataType: 'server',usePager:true,url:'queryMedAffiInCommon.do?isCheck=true&show_detail=1',
				width: '100%', height: '100%', checkbox: false,rownumbers:true,
				delayLoad: true,//初始化不加载，默认false
				selectRowButtonOnly:true,//heightDiff: -10,
				checkBoxDisplay:isCheckDisplay,
				onsuccess:function(){
					//is_addRow();
				},
				toolbar: { items: [
					{ text: '查询（<u>Q</u>）', id:'query', click: query,icon:'search' },
					{ line:true },
					{ text: '添加（<u>I</u>）', id:'add', click: add_open, icon:'add' },
					{ line:true },
					{ text: '删除（<u>D</u>）', id:'delete', click: remove,icon:'delete' },
					{ line:true }, 
					{ text: '审核（<u>A</u>）', id:'audit', click: audit, icon:'audit' },
					{ line:true },
					{ text: '销审（<u>U</u>）', id:'unAudit', click: unAudit, icon:'unaudit' },
					{ line:true },
					{ text: '复制（<u>C</u>）', id:'copy', click: copy, icon:'copy' },
					{ line:true },
					{ text: '冲销（<u>V</u>）', id:'offSet', click: offSet, icon:'offset' },
					{ line:true },
					{ text: '入库确认（<u>R</u>）', id:'confirm', click: confirm,icon:'account' },
					{ line:true },
					{ text: '根据订单生成（<u>G</u>）', id:'orderImp', click: orderImp,icon:'account' },
					{ line:true },
					{ text: '根据送货单生成（<u>G</u>）', id:'generateMain', click: generateMain,icon:'add' },
					{ line:true },
					{ text: '模板设置', id:'printSet', click: printSet, icon:'print' },
					{ line:true } ,
					{ text: '批量打印（<u>P</u>）', id:'print', click: print, icon:'print' }
				]}
			});
    	}else{
	    	grid = $("#maingrid").ligerGrid({
				columns: [{
						display: '入库单号', name: 'in_no', align: 'left', width: 130,
						render : function(rowdata, rowindex, value) {
							if(value == '合计'){
								return value ; 
							}
							return '<a href=javascript:update_open("' 
								+ rowdata.group_id 
								+ ',' + rowdata.hos_id 
								+ ',' + rowdata.copy_code 
								+ ',' + rowdata.in_id
								+ '")>'+rowdata.in_no+'</a>';
						}
					}, {display: '单据来源', name: 'field_desc', align: 'left', width: '120'}, 
					{display: '摘要', name: 'brief', align: 'left', width: '210'}, 
					{display: '仓库', name: 'store_name', align: 'left',width: '100'}, 
					{display: '业务类型', name: 'bus_type_name', align: 'left',width: '90'},
					{display: '业务编码', name: 'bus_type_code'}, 
					{display: '供应商', name: 'sup_name', align: 'left',width: '170'},
					{display: '采购员', name: 'stocker', align: 'left',width: '90'},
					{display: '货单号', name: 'delivery_code', align: 'left',width: '90'},
					{display: '验收员', name: 'examiner_name', align: 'left',width: '90'},
					{ 
			 			display: '金额', name: 'amount_money', align: 'right',width: '90',
			 			render : function(rowdata, rowindex, value) {
							return formatNumber(rowdata.amount_money ==null ? 0 : rowdata.amount_money, '${p08005}', 1);
						}
			 		}, 
			 		{display: '编制日期', name: 'in_date', align: 'left',width: '90'},
			 		{display: '制单人', name: 'maker_name', align: 'left',width: '90'},
			 		{display: '制单人', name: 'maker', align: 'left'},
			 		{display: '审核人', name: 'checker_name', align: 'left',width: '90'},
			 		{display: '确认日期', name: 'confirm_date', align: 'left',width: '90'},
			 		{display: '确认人', name: 'confirmer_name', align: 'left',width: '90'},
			 		{display: '单据状态', name: 'state', align: 'left',width: '90',hide:true
			 			
			 		},{display: '状态', name: 'state_name', align: 'left',width: '90',hide:true
			 			
			 		}
			 	],
				dataAction: 'server',dataType: 'server',usePager:true,url:'queryMedAffiInCommon.do?isCheck=true&show_detail=0',
				width: '100%', height: '100%', checkbox: true,rownumbers:true,
				delayLoad: true,//初始化不加载，默认false
				selectRowButtonOnly:true,//heightDiff: -10,
				checkBoxDisplay:isCheckDisplay,
				onsuccess:function(){
					//is_addRow();
				},
				toolbar: { items: [
					{ text: '查询（<u>Q</u>）', id:'query', click: query,icon:'search' },
					{ line:true },
					{ text: '添加（<u>I</u>）', id:'add', click: add_open, icon:'add' },
					{ line:true },
					{ text: '删除（<u>D</u>）', id:'delete', click: remove,icon:'delete' },
					{ line:true }, 
					{ text: '审核（<u>A</u>）', id:'audit', click: audit, icon:'audit' },
					{ line:true },
					{ text: '销审（<u>U</u>）', id:'unAudit', click: unAudit, icon:'unaudit' },
					{ line:true },
					{ text: '复制（<u>C</u>）', id:'copy', click: copy, icon:'copy' },
					{ line:true },
					{ text: '冲销（<u>V</u>）', id:'offSet', click: offSet, icon:'offset' },
					{ line:true },
					{ text: '入库确认（<u>R</u>）', id:'confirm', click: confirm,icon:'account' },
					{ line:true },
					{ text: '根据订单生成（<u>G</u>）', id:'orderImp', click: orderImp,icon:'account' },
					{ line:true },
					{ text: '根据送货单生成（<u>G</u>）', id:'generateMain', click: generateMain,icon:'add' },
					{ line:true },
					{ text: '模板设置', id:'printSet', click: printSet, icon:'print' },
					{ line:true } ,
					{ text: '批量打印（<u>P</u>）', id:'print', click: print, icon:'print' }
				]}
			});
    	}
    	grid.toggleCol("bus_type_code", false);
    	grid.toggleCol("maker", false);
	    gridManager = $("#maingrid").ligerGetGridManager();
    }
    
  	//是否显示复选框
    function isCheckDisplay(rowdata){
       	if(rowdata.in_id == null) return false;
         return true;
    }
  	//根据送货单生成
  	function generateMain(){
  		parent.$.ligerDialog.open({
			title : '送货单入库',
			height : $(window).height(),
			width : $(window).width(),
			url : 'hrp/med/affi/in/medAffiDeliveryOrderImpMainPage.do?isCheck=false',
			modal : true,
			showToggle : false,
			showMax : true,
			showMin : false,
			isResize : true,
			parentframename : window.name
		});
  	}
  	
  	
    //根据订单生成
    function orderImp(){
    	parent.$.ligerDialog.open({
			title : '根据订单生成',
			height : $(window).height(),
			width : $(window).width(),
			url : 'hrp/med/affi/in/medAffiOrderImpPage.do?isCheck=false&',
			modal : true,
			showToggle : false,
			showMax : true,
			showMin : false,
			isResize : true,
			parentframename : window.name
		});
    }

  	//键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);//查询
		hotkeys('I', add_open);//添加
		hotkeys('D', remove);//删除
		hotkeys('A', audit);//审核
		hotkeys('U', unAudit);//取消审核
		hotkeys('C', copy);//复制
		hotkeys('V', offSet);//冲销
		hotkeys('R', confirm);//入库确认
		//hotkeys('T', printSelect);//批量打印
		//hotkeys('P', printDate);//模板打印
	}
    function loadDict(){
		//字典下拉框
		autocomplete("#store_code", "../../queryMedStore.do?isCheck=false", "id", "text", true, true,{is_com : '1'},false);//仓库
		autocomplete("#sup_code", "../../queryHosSupDict.do?isCheck=false", "id", "text", true, true);//供应商
		autoCompleteByData("#state", medInMain_state.Rows, "id", "text", true, true);//状态
		autocompleteAsync("#bus_type_code", "../../queryMedBusType.do?isCheck=false", "id", "text", true, true,{codes:'4,6,8,27,31'},false,"27");//业务类型
		
		autodate("#in_dateB", "yyyy-mm-dd", "month_first");
        autodate("#in_dateE", "yyyy-mm-dd", "month_last");
        //autodate("#confirm_dateB", "yyyy-mm-dd", "month_first");
        //autodate("#confirm_dateE", "yyyy-mm-dd", "month_last");
        $("#confirm_dateB").ligerTextBox({width:100});
        $("#confirm_dateE").ligerTextBox({width:100});
        $("#in_dateB").ligerTextBox({width:100});
        $("#in_dateE").ligerTextBox({width:100});
        $("#store_code").ligerTextBox({width:160});
        $("#sup_code").ligerTextBox({width:160});
        $("#state").ligerTextBox({width:160});
        $("#in_no").ligerTextBox({width:238});
        $("#batch_no").ligerTextBox({width:238});
       /*  autocomplete("#inv_code", "../../queryMedInv.do?isCheck=false", "id", "text", true, true, "", false, false, "", "", "160"); */
        $("#inv_code").ligerTextBox({width:160});
        $("#bar_code").ligerTextBox({width:220});
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
    //添加
    function add_open(){
    	parent.$.ligerDialog.open({
			title: '代销药品入库单添加',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/med/affi/in/medAffiInCommonAddPage.do?isCheck=false',
			modal: true, showToggle: false, showMax: true, showMin: true,initShowMax:true, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		}); 
	}
    //修改
    function update_open(obj){		
		var vo = obj.split(",");
		var paras ="group_id="+vo[0] +"&hos_id="+vo[1] +"&copy_code="+vo[2] +"&in_id="+vo[3] ;
		parent.$.ligerDialog.open({
			title: '代销药品入库单修改',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/med/affi/in/medAffiInCommonUpdatePage.do?isCheck=false&' + paras.toString(),
			modal: true, showToggle: false, showMax: true, showMin: true, initShowMax:true,isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		}); 
    }
    
    //删除	
    function remove(){
    	//根据参数08011判断是否倒序删除，如果是，则只能删除单据号最大的入库单。否则，医院人为控制单据连续性。
    	//判断当前登录用户是否为制单人，非制单人不允许删除
    	var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择要删除的单据！');
			return;
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
				$.ligerDialog.error("删除失败！请选择未审核的单据！"); //"+in_nos+"单据不是未审核状态");
				return;
			}
			$.ligerDialog.confirm('确定删除?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("deleteMedAffiInCommon.do?isCheck=true",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.state=="true"){
							query();
						}
					});
				}
			}); 
		}
	}
    //审核
    function audit(){
    	var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择要审核的单据！');
			return;
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
				$.ligerDialog.error("审核失败！请选择未审核的单据！");//+in_nos+"单据不是未审核状态");
				return;
			}
			$.ligerDialog.confirm('确定审核?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("auditMedAffiInCommon.do?isCheck=true",{ParamVo : ParamVo.toString()},function (responseData){
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
			$.ligerDialog.error('请选择取消审核的单据！');
			return;
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
				$.ligerDialog.error("销审失败！请选择已经审核的单据！");//+in_nos+"单据不是审核状态");
				return;
			}
			$.ligerDialog.confirm('确定要取消审核?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("unAuditMedAffiInCommon.do?isCheck=true",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.state=="true"){
							query();
						}
					});
				}
			}); 
		}
    }
    //复制
    function copy(){
    	var data = gridManager.getCheckedRows();
    	if (data.length == 0){
			$.ligerDialog.error('请选择要复制的单据！');
			return ;
		}else{
				var ParamVo = [];
				var in_nos = "";
				$(data).each(function(){
					
					/* if(this.state != 3){
						in_nos = in_nos + this.in_no +",";
					} */
					ParamVo.push(
						this.group_id   +"@"+ 
						this.hos_id   +"@"+ 
						this.copy_code   +"@"+ 
						this.in_id 
					) 
				});
				/* if(in_nos != ""){
					$.ligerDialog.error("复制失败！请选择入库确认的单据！");//+in_nos+"单据不是入库确认状态");
					return;
				} */
				$.ligerDialog.confirm('确定要复制吗?', function (yes){
					if(yes){
						ajaxJsonObjectByUrl("copyMedAffiInCommon.do?isCheck=true",{ParamVo : ParamVo.toString()},function (responseData){
							if(responseData.state=="true"){
								query();
							}
						});
					}
				}); 
				
			}
    }
    //冲销
    function offSet(){
    	var data = gridManager.getCheckedRows();
    	if (data.length == 0){
			$.ligerDialog.error('请选择要冲销的单据！');
			return ;
		}else{
			if(data.length > 1){
				$.ligerDialog.error('只能单张冲账,请重新选择！');
				return ;
			}else{
				var ParamVo = [];
				var in_nos = "";
				var in_noM = "";
				$(data).each(function(){	
					if(this.state != 3){
						in_nos = this.in_no +",";
					}
					
					if(this.bus_type_code != 27){
						in_noM = this.in_no +",";
					}
					ParamVo.push(
						this.group_id   +"@"+ 
						this.hos_id   +"@"+ 
						this.copy_code   +"@"+ 
						this.in_id 
					) 
				});
				//判断是否是入库单据
				if(in_nos != ""){
					$.ligerDialog.error("冲销失败！请选择入库确认的单据！");//+in_nos+"单据不是入库确认状态");
					return;
				}
				//判断单据类型是否是代销入库
				if(in_noM !="" ){
					$.ligerDialog.error("冲销失败！请选择业务类型为代销入库的单据！");//+in_nos+"单据的业务类型不是代销入库！");
					return;
				}
				$.ligerDialog.confirm('确定要冲销吗?', function (yes){
					if(yes){
						ajaxJsonObjectByUrl("offsetMedAffiInCommon.do?isCheck=true",{ParamVo : ParamVo.toString()},function (responseData){
							if(responseData.state=="true"){
								query();
							}
						});
					}
				});
			}
				
		}
    }
    //入库确认	
    function confirm(){
    	
    	var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择要入库确认的单据！');
			return;
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
				$.ligerDialog.error("入库确认失败！请选择审核状态的单据！");//+in_nos+"单据不是审核状态");
				return;
			}
			$.ligerDialog.confirm('确定入库确认吗?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("confirmMedAffiInCommon.do?isCheck=true",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.state=="true"){
							query();
						}
					});
				}
			}); 
		}
	}
  //打印设置
	function printSet(){
		
		var useId=0;//统一打印
		if('${p08022 }'==1){
			//按用户打印
			useId='${sessionScope.user_id }';
		}else if('${p08022 }'==2){
			//按库房打印
 			if(liger.get("store_code").getValue()==""){
 				$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
 				return;
 			}
 			useId=liger.get("store_code").getValue().split(",")[0];
		}
		
		officeFormTemplate({template_code:"08017",use_id:useId});
		/* parent.parent.$.ligerDialog.open({url : 'hrp/med/affi/in/affiInPrintSetPage.do?template_code=08018&use_id='+useId,
			data:{}, height: $(parent).height(),width: $(parent).width(), title:'打印模板设置',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
		});
		 */
	}
	 
    //打印
    function print(){
    	
    	 var useId=0;//统一打印
 		if('${p08022 }'==1){
 			//按用户打印
 			useId='${sessionScope.user_id }';
 		}else if('${p08022 }'==2){
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
			 var para={
	    			paraId :in_id.substring(0,in_id.length-1) ,
	    			template_code:'08017',
	    			class_name:"com.chd.hrp.med.serviceImpl.affi.in.MedAffiInCommonServiceImpl",
	    			method_name:"queryMedAffiInByPrintTemlate1",
	    			isPrintCount:false,//更新打印次数
	    			isPreview:true,//预览窗口，传绝对路径
	    			use_id:useId,
	    			p_num:1
	    	}; 
		 	
				officeFormPrint(para);
			
	    	//printTemplate("hrp/med/affi/in/queryMedAffiInByPrintTemlate.do?isCheck=false",para);
	    	
		}
    	
    }
    
    function showDetail(){
		show_detail = $("#show_detail").is(":checked") ? 1 : 0;
		if(show_detail==0){
			liger.get("inv_code").clear();
			//$(".demo").attr("style","display:none");
			$("#batch_no").val();
		}/* else{
			$(".demo").attr("style","display:table-cell");
		} */
		loadHead();
		//query();
	 }
    </script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
		 
		<tr>
			<td align="right" class="l-table-edit-td" width="10%">制单日期：</td>
			<td align="left" class="l-table-edit-td"  width="20%">
				<table>
					<tr>
						<td>
							<input class="Wdate" name="in_dateB" id="in_dateB" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
						<td align="right" class="l-table-edit-td"  >
							至：
						</td>
						<td align="left" class="l-table-edit-td">
							<input class="Wdate" name="in_dateE" id="in_dateE" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
            		</tr>
				</table>
	        </td>
			
			
			<td align="right" class="l-table-edit-td" width="10%" ><font color="red">*</font>业务类型：</td>
			<td align="left" class="l-table-edit-td" width="20%">
				<input	name="bus_type_code" type="text" id="bus_type_code" required="true" ltype="text"	validate="{required:false,maxlength:100}" />
			</td>
			
			<td align="right" class="l-table-edit-td" width="10%" style="padding-left: 10px;">状态：</td>
			<td align="left" class="l-table-edit-td" width="20%">
				<input	name="state" type="text" id="state" ltype="text" validate="{required:false,maxlength:100}" />
			</td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" width="10%">确认日期：</td>
			<td align="left" class="l-table-edit-td"  width="20%">
				<table>
					<tr>
						<td>
							<input class="Wdate" name="confirm_dateB" id="confirm_dateB" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
						<td align="right" class="l-table-edit-td"  >
							至：
						</td>
						<td align="left" class="l-table-edit-td">
							<input class="Wdate" name="confirm_dateE" id="confirm_dateE" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
            		</tr>
				</table>
	        </td>
			
			<td align="right" class="l-table-edit-td" width="10%" style="padding-left: 10px;"><font color="red">*</font>仓库：</td>
			<td align="left" class="l-table-edit-td" width="20%">
				<input	name="store_code" type="text" id="store_code" required="true" ltype="text"	validate="{required:false,maxlength:100}" />
			</td>
			
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;"> 供应商：</td>
			<td align="left" class="l-table-edit-td">
				<input name="sup_code"	type="text" id="sup_code" ltype="text" validate="{required:false,maxlength:100}" />
			</td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" width="10%"> 入库单号：</td>
			<td class="l-table-edit-td" width="20%">
				<input name="in_no" type="text" id="in_no" ltype="text" validate="{required:false,maxlength:100}" />
			</td>
			
			<td align="right" class="l-table-edit-td" width="10%">药品信息：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="inv_code" type="text" id="inv_code" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
            
			<td align="right" class="l-table-edit-td" width="10%">
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="show_detail" type="checkbox" id="show_detail" onclick="showDetail();"/>&nbsp;&nbsp;显示明细
            </td>
		</tr>
		<tr>
        	<td align="right" class="l-table-edit-td  demo" width="10%">批号：</td>
            <td align="left" class="l-table-edit-td  demo" width="20%">
            	<input name="batch_no" type="text" id="batch_no" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
            <td align="right" class="l-table-edit-td  demo" width="10%">条形码：</td>
            <td align="left" class="l-table-edit-td  demo" width="20%">
            	<input name="bar_code" type="text" id="bar_code" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
        </tr>
	</table>
	<div id="maingrid"></div>
	<div id="resultPrint" style="display: none">
		<table width="100%">
			<thead>

				<tr>
					<th width="200">单据ID</th>
					<th width="200">入库单号</th>
					<th width="200">年份</th>
					<th width="200">月份</th>
					<th width="200">业务类型</th>
					<th width="200">供应商变更ID</th>
					<th width="200">供应商ID</th>
					<th width="200">仓库ID</th>
					<th width="200">仓库变更ID</th>
					<th width="200">货位</th>
					<th width="200">摘要</th>
					<th width="200">采购员</th>
					<th width="200">采购类型编码</th>
					<th width="200">是否为初始化单据</th>
					<th width="200">制单人</th>
					<th width="200">入库日期</th>
					<th width="200">审核人</th>
					<th width="200">审核日期</th>
					<th width="200">入库确认人</th>
					<th width="200">入库确认日期</th>
					<th width="200">状态</th>
					<th width="200">是否已经付款</th>
					<th width="200">付款日期</th>
					<th width="200">发票号码</th>
					<th width="200">开发票金额</th>
					<th width="200">是否全部开发票</th>
					<th width="200">调拨单位</th>
					<th width="200">结算方式</th>
					<th width="200">盘点单号</th>
					<th width="200">协议编号</th>
					<th width="200">申请科室</th>
				</tr>
			</thead>
			<tbody></tbody>
		</table>
	</div>
</body>
</html>
