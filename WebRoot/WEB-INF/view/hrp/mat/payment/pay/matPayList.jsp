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
<script type="text/javascript">
	var grid;
	var gridManager = null; 

	$(function ()
	{
		loadDict()//加载下拉框
		//加载数据
		loadHead(null);	
		//query();
		loadHotkeys();
		//默认隐藏
		$(".set_td").hide();
		
		//绑定监听事件
		$("input[name='store_type']").bind("click", function(){
			if(this.value == 2){
				$(".store_td").hide();
				$(".set_td").show();
			}else{
				$(".store_td").show();
				$(".set_td").hide();
			}
		});
	});

	//查询
	function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({name:'make_date_begin',value:$("#make_date_begin").val()});
		grid.options.parms.push({name:'make_date_end',value:$("#make_date_end").val()});
		var store_type = $("input[name='store_type']:checked").val();
		if(store_type == 2){
			grid.options.parms.push({
				name: 'set_id',
				value: liger.get("set_code").getValue() == null ? "" : liger.get("set_code").getValue()}
			);
		}else{
			grid.options.parms.push({
				name : 'store_id',
				value : liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[0]
			}); 
		}
		grid.options.parms.push({name: 'sup_id', value: liger.get("sup_id").getValue().split(",")[0]});
		grid.options.parms.push({name: 'pay_bill_type', value: liger.get("pay_bill_type").getValue()}); 
		grid.options.parms.push({name: 'state', value: liger.get("state").getValue()}); 
		grid.options.parms.push({name: 'pay_date_begin', value: $("#pay_date_begin").val()});
		grid.options.parms.push({name: 'pay_date_end', value: $("#pay_date_end").val()});
		grid.options.parms.push({name: 'pay_bill_no', value: $("#pay_bill_no").val()}); 
		grid.options.parms.push({name: 'maker', value: liger.get("maker").getValue()});
		grid.options.parms.push({name: 'checker', value: liger.get("checker").getValue()}); 
		grid.options.parms.push({name : 'pay_type_code', value : liger.get("pay_type_code").getValue()});
		//加载查询条件
		grid.loadData(grid.where);
	}

	function loadHead(){
		grid = $("#maingrid").ligerGrid({
			columns: [ { 
				display: '付款单号', name: 'pay_bill_no', align: 'left', width: 120,
				render:function(rowdata, index, value){
					if (value == '合计') {
						return value;
					}
					if(rowdata.pay_bill_type == 0){
						return "<a href=javascript:openUpdate('"+rowdata.pay_id+"','"+index+"')>"+value+"</a>";
					}else{
						return "<a href=javascript:openUpdate('"+rowdata.pay_id+"','"+index+"')>"+value+"<font color='red'>(退)</font>"+"</a>";
					}
				}
			}, { 
				display: '单据类型', name: 'pay_bill_type_name', align: 'left', width: 70,
			}, { 
				display: '付款日期', name: 'pay_date', align: 'left', width: 70
			}, { 
				display: '库房', name: 'store_name', align: 'left', width: 120
			}, { 
				display: '供应商', name: 'sup_name', align: 'left', width: 200
			}, { 
				display: '付款金额', name: 'pay_money', align: 'right', width: 90, 
				render: function(rowdata, rowindex, value){
					return formatNumber(value, '${p04005 }', 1);
				}
			}, { 
				display: '付款方式', name: 'pay_type_name', align: 'left', width: 80
			}, {  
				display: '摘要', name: 'note', align: 'left', width: 180
			}, { 
				display: '制单人', name: 'maker_name', align: 'left', width: 60
			}, { 
				display: '制单日期', name: 'make_date', align: 'left', width: 70
			}, { 
				display: '审核人', name: 'checker_name', align: 'left', width: 60
			}, { 
				display: '审核日期', name: 'chk_date', align: 'left', width: 70
			}, { 
				display: '财务审核状态', name: 'acc_check_state', align: 'left', width: 100
			}, { 
				display: '单据状态', name: 'state', align: 'left',hide:true, width: 70
			}, { 
				display: '状态', name: 'state_name', align: 'left', width: 60
			} ],
			dataAction: 'server', dataType: 'server', usePager: true, url: 'queryMatPayList.do?isCheck=false',
			width: '100%', height: '100%', checkbox: true, rownumbers: true,
			checkBoxDisplay : isCheckDisplay,
			delayLoad: true,//初始化不加载，默认false
			selectRowButtonOnly: true,//heightDiff: -10,
			toolbar: { items: [ { 
				text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' 
			}, { 
				line:true 
			}, { 
				text: '添加（<u>A</u>）', id:'add', click: add_open, icon:'add' 
			}, { 
				line:true 
			}, { 
				text: '删除（<u>D</u>）', id:'delete', click: remove,icon:'delete' 
			}, { 
				line:true 
			}, {
				text: '审核（<u>Z</u>）', id:'audit', click: audit, icon:'bluebook' 
			}, { 
				line:true 
			}, { 
				text: '消审（<u>U</u>）', id:'unAudit', click: unAudit,icon:'bookpen' 
			}, { 
				line:true 
			}, { 
				text: '省平台发送（<u>S</u>）', id:'paySend', click: paySend,icon:'logout' 
			}, { 
				line:true 
			}, { 
				text: '打印', id:'pagePrint', click: pagePrint,icon:'print' 
			}, { 
				line:true 
			}, { 
				text: '模板设置', id:'printSet', click: printSet, icon:'settings' 
			}, { 
				line:true 
			}, { 
				text: '批量打印（<u>P</u>）', id:'print', click: print, icon:'print' 
			} ] },
			onDblClickRow : function (rowdata, rowindex, value){
				if (rowdata.pay_id == null) {
					//$.ligerDialog.warn('请选择数据 ');
					return;
				}
				openUpdate(rowdata.pay_id, rowindex);
			} 
		} );
		
		gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	//复选框是否可用
	function isCheckDisplay(rowdata) {
		if (rowdata.pay_id == null)
			return false;
		return true;
	}
	
	function add_open(){
		parent.$.ligerDialog.open({ 
			title:'添加付款单',
			url : 'hrp/mat/payment/pay/matPayAddPage.do?isCheck=false',
			height: $(window).height(),
			width: $(window).width(),
			data:{}, 
			modal:true,showToggle:false,showMax:false,
			isResize:true, showMin: true,showMax: true, //开启最大化最小化按钮
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		}); 
	}
	
	function remove(){
		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
			return false;
		}
		var ids = "";
		var msg = "";
		var sum_money = 0;
		$.each(data, function (){
			if(this.state ==1){
				ids += this.pay_id+",";
				sum_money += parseFloat(this.pay_money);
			}else{
				msg = '只有未审核状态的付款单允许删除';
				return false;
			}
		})
		
		if(msg.length > 0){
			$.ligerDialog.error(msg);
			return false;
		}
		
		if(ids.length > 0){
			$.ligerDialog.confirm('确定删除?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("deleteMatPay.do",{ids: ids.substr(0, ids.length - 1)},function (responseData){
						if(responseData.state=="true"){
					    	gridManager.deleteSelectedRow();
					    	if(grid.getRow(0).pay_bill_no == "合计"){
					    		grid.updateCell("pay_money", parseFloat(grid.getRow(0).pay_money) - sum_money, 0);
					    	}
						}
					});
				}
			}); 
		}
	}	

	var gridRowIdByOpen;
	function openUpdate(pay_id, row_id){
		gridRowIdByOpen = row_id;
		parent.$.ligerDialog.open({ 
			title:'付款单修改',
			url : 'hrp/mat/payment/pay/matPayUpdatePage.do?isCheck=false&pay_id='+pay_id,
			height: $(window).height(),
			width: $(window).width(),
			data:{}, 
			modal:true, showToggle:false,showMax:false,
			isResize:true, showMin: true,showMax: true, //开启最大化最小化按钮
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
	}
	
	//审核
	function audit(){
		var data = gridManager.getCheckedRows();
		var ids = "";
		var str = '';
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
			return false;
		}
		
		$.each(data, function (){
			if(this.state != 1){
				str += this.pay_bill_no +",";
			}else{
				ids += this.pay_id + ",";
			};
		})
		
		if(str != ''){
			$.ligerDialog.error('<span style="color:red">审核失败，单据：'+str+'不是未审核状态！</span>(只有未审核状态的单据允许审核)');
		}else{
			$.ligerDialog.confirm('确定审核所选单据吗?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("auditMatPay.do",{ids: ids.substr(0, ids.length-1)},function (responseData){
						if(responseData.state=="true"){
							$.each(data, function (){
								grid.updateRow(this, {state: 2, state_name: '已审核'});
							})
						}
					});
				}
			}); 
		}
	}
	
	//消审
	function unAudit(){
		var data = gridManager.getCheckedRows();
		var ids = "";
		var str = '';
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
			return false;
		}
		
		$.each(data, function (){
			if(this.state != 2){
				str += this.pay_bill_no +",";
			}else{
				ids += this.pay_id + ",";
			};
		})
		
		if(str != ''){
			$.ligerDialog.error('<span style="color:red">消审失败，单据：'+str+'不是已审核状态！</span>只有已审核状态的单据允许消审');
		}else{
			$.ligerDialog.confirm('确定消审所选单据吗?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("unAuditMatPay.do",{ids: ids.substr(0, ids.length-1)},function (responseData){
						if(responseData.state=="true"){
							$.each(data, function (){
								grid.updateRow(this, {state: 1, state_name: '未审核'});
							})
						}
					});
				}
			}); 
		}
	}
	
	//字典下拉框
	function loadDict(){ 
		//供应商下拉框
		autocomplete("#sup_id", "../../queryHosSupDictMethod.do?isCheck=false", "id", "text", true, true, '', false, '', 202);
		autocomplete("#checker", "../../../sys/queryUserDict.do?isCheck=false", "id", "text", true, true);
		autocomplete("#maker", "../../../sys/queryUserDict.do?isCheck=false",  "id", "text", true, true);
		autocomplete("#set_code", "../../queryMatVirStore.do?isCheck=false", "id", "text", true, true);
		autocomplete("#store_code", "../../queryMatStoreDictDate.do?isCheck=false", "id", "text", true, true, {read_or_write:1},true);
		autoCompleteByData("#state", [{id: "1", text: "未审核"}, {id: "2", text: "审核"}, {id: "3", text: "已确认"}], "id", "text", true, true);
		autoCompleteByData("#pay_bill_type", [{id: "0", text: "付款单"}, {id: "1", text: "退款单"}], "id", "text", true, true);
		autocomplete("#pay_type_code", "../../queryMatPayType.do?isCheck=false", "id", "text", true, true);
		
		autodate("#make_date_begin", "yyyy-mm-dd", "month_first");
		autodate("#make_date_end", "yyyy-mm-dd", "month_last");
		$("#make_date_begin").ligerTextBox({width: 90});
		$("#make_date_end").ligerTextBox({width: 90});
		
		$("#pay_date_begin").ligerTextBox({width: 90});
		$("#pay_date_end").ligerTextBox({width: 90});
		
		$("#pay_bill_no").ligerTextBox({width: 160});
	}  
	
	//键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('A', add);
		hotkeys('D', remove);
		//hotkeys('P', printDate);
		hotkeys('Z', audit);
		hotkeys('U', unAudit);
	}
	
	//页面打印
	function pagePrint(){
		if(grid.getData().length==0){
			$.ligerDialog.error("请先查询数据!");
			return false;
		}
		//表头
		var heads={
			"isAuto":true,//系统默认,页眉显示页码
			"rows":[ {
				"cell":0,"value":"制单日期:"
			}, {
				"cell":1,"value":$("#make_date_begin").val()+"至"+$("#make_date_end").val()
			}, {
				"cell":3,"value":"仓库:"
			}, {
				"cell":4,"value":liger.get("store_code").getText()?liger.get("store_code").getText():"空"
			}, {
				"cell":5,"value":"虚仓:"
			}, {
				"cell":6,"value":liger.get("set_code").getText()?liger.get("set_code").getText():"空"
			} ]
		};
		//表尾
		//获取日期
		var time=new Date();
		var date=time.getFullYear()+"年"+(time.getMonth()+1)+"月"+time.getDate()+"日";
		var foots={
			"rows":[ {
				"cell":0,"value":"制表日期:"
			}, {
				"cell":1,"value":date
			}, {
				"cell":0,"value":'分管院领导：',"br":"true"
			}, {
				"cell":3,"value":"部门主管:"
			}, {
				"cell":11,"value":"会计:"
			} ]
		};
		
		var printPara={
			title:"付款单",
			columns:JSON.stringify(grid.getPrintColumns()),
			class_name:"com.chd.hrp.mat.serviceImpl.payment.MatPayServiceImpl",
			method_name:"queryMatPayMainForPrint",
			bean_name:"matPayService",
			heads:JSON.stringify(heads),
			foots:JSON.stringify(foots),
		};
		
		$.each(grid.options.parms,function(i,obj){
			printPara[obj.name]=obj.value;
		});
		
		officeGridPrint(printPara);
	}
	
	//打印设置
	function printSet(){
		var useId=0;//统一打印
		if('${p04027 }'==1){
			//按用户打印
			useId='${user_id}';
		}
		
		officeFormTemplate({template_code:"04026",use_id:useId});
	}
	
	//打印
	function print(){
		var useId=0;//统一打印
		if('${p04027 }'==1){
			//按用户打印
			useId='${user_id}';
		}
		
		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
			var pay_id ="" ;
			var in_nos = "";
			$.each(data, function (){
				if(this.state != 2){
					in_nos = in_nos + this.in_no + "<br>";
				}
				pay_id  += this.pay_id+","
			});
			
			var para={
					template_code: '04026',
					class_name: "com.chd.hrp.mat.serviceImpl.payment.MatPayServiceImpl",
					bean_name: "matPayService",
					method_name: "queryMatPayByPrintTemlate",
					//isSetPrint: flag,//是否套打，默认非套打
					isPreview: true,//是否预览，默认直接打印
					paraId: pay_id.substring(0, pay_id.length-1) ,
					use_id: useId,
					p_num: 1
			};
			
			officeFormPrint(para);
		}
	}
	
	//浙江省平台发送
	function paySend(){
		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
			return;
		}else if(data.length >1){
			$.ligerDialog.warn('省平台发送功能暂时只支持单笔发送');
		}else{
			var payStr ="";
			var pay_nos = "";
			var notice_nos = "";
			$(data).each(function (){
				 //if(this.is_notice > 1){
					//notice_nos = notice_nos + this.order_code +",";
				//}else{
					if(this.state_name == "未审核"){
						pay_nos = pay_nos + this.pay_bill_no + ",";
					}
					payStr=this.pay_id ;
				//}
			});
			//发送过的单据不允许再次发送
			//if(notice_nos != "" ){
				//$.ligerDialog.error("发送失败，"+order_nos+"单据不允许再次发送");
				//return;
			//}
			//未审核装填单据不允许发送
			if(pay_nos != ""){
				$.ligerDialog.error("发送失败，"+pay_nos+"单据不是审核状态");
				return;
			}
			
			$.ligerDialog.confirm('确定要发送吗?', function (yes){
				if(yes){
					var param={
							payid: payStr,
					}
					
					ajaxJsonObjectByUrl("matPaySend.do?isCheck=false", param, function (responseData){
						if(responseData.state=="true"){
							//HRP云平台推送订单，材料与供应商产品没有做对应关系，弹出窗口对供应商产品做审核操作并绑定对应关系
							if(dialog){
								dialog.close();
							}
							query();
						}else{
							$.ligerDialog.error(responseData.errMsg+"，发送失败！");
						}
					})
				}
			}); 
		}
	}
</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
		<tr>
			<td align="right" class="l-table-edit-td" width="10%">
				制单日期：
			</td>
			<td align="left" class="l-table-edit-td"  width="20%">
				<table>
					<tr>
						<td>
							<input class="Wdate" id="make_date_begin" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
						</td>
						<td align="right" class="l-table-edit-td"  > 至 </td>
						<td>
							<input class="Wdate" id="make_date_end" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
					</tr>
				</table>
			</td>
			
			<td align="right" class="l-table-edit-td" width="10%">
				查询方式：
			</td>
			<td align="left" class="l-table-edit-td" width="20%">
				<input name="store_type" type="radio" value="1" checked/>&nbsp;&nbsp;按仓库
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input name="store_type" type="radio" value="2"/>&nbsp;&nbsp;按虚仓
			</td>
			
			<td align="right" class="l-table-edit-td store_td" width="10%">
				仓库：
			</td>
			<td align="left" class="l-table-edit-td store_td" width="20%">
				<input type="text" id="store_code" />
			</td>
			
			<td align="right" class="l-table-edit-td set_td" width="10%">
				虚仓：
			</td>
			<td align="left" class="l-table-edit-td set_td" width="20%">
				<input type="text" id="set_code" />
			</td>
		</tr> 
		<tr>
			<td align="right" class="l-table-edit-td">
				供应商：
			</td>
			<td align="left" class="l-table-edit-td" >
				<input type="text" id="sup_id" />
			</td>
			
			<td align="right" class="l-table-edit-td">
				单据类型：
			</td>
			<td align="left" class="l-table-edit-td">
				<input type="text" id="pay_bill_type" />
			</td> 
			
			<td align="right" class="l-table-edit-td">
				状态：
			</td>
			<td align="left" class="l-table-edit-td">
				<input id="state" type="text"/>
			</td> 
		</tr> 
		<tr>
			<td align="right" class="l-table-edit-td" >
				付款日期：
			</td>
			<td align="left" class="l-table-edit-td" >
				<table>
					<tr>
						<td>
							<input class="Wdate" name="pay_date_begin" id="pay_date_begin" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
						<td align="right" class="l-table-edit-td"  > 至 </td>
						<td>
							<input class="Wdate" name="pay_date_end" id="pay_date_end" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
					</tr>
				</table>
			</td>
			
			<td align="right" class="l-table-edit-td" >
				付款单号：
			</td>
			<td align="left" class="l-table-edit-td">
				<input type="text" id="pay_bill_no" />
			</td>
			
			<td align="right" class="l-table-edit-td" >
				付款方式：
			</td>
			<td align="left" class="l-table-edit-td" >
				<input id="pay_type_code" type="text"/>
			</td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td">
				制单人：
			</td>
			<td align="left" class="l-table-edit-td">
				<input type="text" id="maker" />
			</td>
			
			<td align="right" class="l-table-edit-td">
				审核人：
			</td>
			<td align="left" class="l-table-edit-td">
				<input type="text" id="checker"/>
			</td>
		</tr>
	</table>
	
	<div id="maingrid"></div>
</body>
</html>
