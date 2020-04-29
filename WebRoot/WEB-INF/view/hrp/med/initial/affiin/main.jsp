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
    var renderFunc = {
			 
			amount_money:function(value){//金额
				return formatNumber(value, '${p08005 }', 1);
			} 
	}; 
    
    $(function ()
    {
        loadDict();//加载下拉框
    	//加载数据
    	loadHead(null);	
		loadHotkeys();
		query();
    });
    //查询
    function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
        //根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'begin_date',
			value : $("#begin_date").val()
		});
		grid.options.parms.push({
			name : 'end_date',
			value : $("#end_date").val()
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
			name : 'state',
			value : liger.get("state").getValue() == null ? "" : liger.get("state").getValue().split(",")[0]
		}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [{
					display: '入库单号', name: 'in_no', align: 'left', width: 130,
					render : function(rowdata, rowindex, value) {
						return '<a href=javascript:update_open("' 
							+ rowdata.group_id 
							+ ',' + rowdata.hos_id 
							+ ',' + rowdata.copy_code 
							+ ',' + rowdata.in_id
							+ '")>'+rowdata.in_no+'</a>';
					}
				}, 
				{display: '编制日期', name: 'in_date', align: 'left', width: 90},
				{display: '摘要', name: 'brief', align: 'left', width: 150}, 
				{display: '仓库', name: 'store_name', align: 'left',width: 100}, 
				{display: '供应商', name: 'sup_name', align: 'left', width: 200},
		 		{display: '业务类型', name: 'bus_type_name', align: 'left',width: 100}, 
		 		{display: '制单人', name: 'maker_name', align: 'left',width: 100}, 
		 		{display: '确认日期', name: 'confirm_date', align: 'left',width: 100}, 
		 		{display: '确认人', name: 'confirmer_name', align: 'left',width: 100},
		 		{ display: '金额', name: 'amount_money', align: 'right',width: 100,
		 		  render : function(rowdata, rowindex, value) {
						return formatNumber(rowdata.amount_money ==null ? 0 : rowdata.amount_money, '${p08005 }', 1);
					}
		 		}, 
		 		{display: '状态', name: 'state_name', align: 'right',width: 100}
		 	],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMedInitAffiIn.do?isCheck=true',
			width: '100%', height: '100%', checkbox: true,rownumbers:true,
			delayLoad: true,//初始化不加载，默认false
			selectRowButtonOnly:true,//heightDiff: -10,
			toolbar: { items: [
				{ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' },
				{ line:true },
				{ text: '添加（<u>A</u>）', id:'add', click: add_open, icon:'add' },
				{ line:true },
				{ text: '删除（<u>D</u>）', id:'delete', click: remove,icon:'delete' },
				{ line:true }, 
				{ text: '入库确认（<u>F</u>）', id:'confirm', click: confirm,icon:'account' },
				{ line:true },
				{ text: '模板设置', id:'printSet', click: printSet, icon:'print' },
				{ line:true } ,
				{ text: '批量打印（<u>P</u>）', id:'print', click: print, icon:'print' }
				
			]}
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    //打印回调方法
    function lodopPrint(){
    	var head="<table class='head' width='100%'><tr><td>单位：${sessionScope.hos_name}</td></tr>";
 		head=head+"<tr><td>编制日期："+$("#begin_date").val() +" 至  "+ $("#end_date").val()+"</td></tr>";
 		head=head+"</table>";
 		grid.options.lodop.head=head; 
 		grid.options.lodop.fn=renderFunc;
 		grid.options.lodop.title="代销药品期初入库";
    }
    
    //键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
		hotkeys('A', add_open);
		hotkeys('D', remove);
		hotkeys('F', confirm);
		hotkeys('E', exportExcel);
		hotkeys('P', printDate);
	}
    
    //添加
    function add_open(){
    	parent.$.ligerDialog.open({
			title: '代销药品期初入库单添加',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/med/initial/affiin/addPage.do?isCheck=false',
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
			"in_id="+vo[3] ;
		parent.$.ligerDialog.open({
			title: '代销药品期初入库单修改',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/med/initial/affiin/updatePage.do?isCheck=false&' + paras.toString(),
			modal: true, showToggle: false, showMax: true, showMin: true, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});  
		
    }
    
    //删除	
    function remove(){
    	
    	var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
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
				$.ligerDialog.error("删除失败！"+in_nos+"单据不是未审核状态");
				return;
			}
			$.ligerDialog.confirm('确定删除?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("deleteMedInitAffiIn.do?isCheck=true",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.state=="true"){
							query();
						}
					});
				}
			}); 
		}
	}
    
    //入库确认	
    function confirm(){
    	
    	var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
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
				$.ligerDialog.error("入库确认失败！"+in_nos+"单据不是未审核状态");
				return;
			}
			$.ligerDialog.confirm('确定入库确认?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("confirmMedInitAffiIn.do?isCheck=true",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.state=="true"){
							query();
						}
					});
				}
			}); 
		}
	}
   
    //加载字典
    function loadDict(){
		//字典下拉框
		autocomplete("#store_code", "../../queryMedStore.do?isCheck=false", "id", "text", true, true,{is_com : '1'},true,false);
		autocomplete("#sup_code", "../../queryHosSupDict.do?isCheck=false", "id", "text", true, true);
		var paras = {
			table_code : "med_affi_in",
			field_code : "state",
			notValues : "2"
		}
		autocomplete("#state", "../../queryMedSysList.do?isCheck=false", "id", "text", true, true, paras);
        $("#begin_date").ligerTextBox({width:120});
        $("#end_date").ligerTextBox({width:120});
        autodate("#begin_date", "yyyy-mm-dd", "month_first");
        autodate("#end_date", "yyyy-mm-dd", "month_last");
	}  
	 //打印模板设置
    function printSet(){
	  
    	var useId=0;//统一打印
		if('${p08017 }'==1){
			//按用户打印
			useId='${sessionScope.user_id }';
		}else if('${p08017 }'==2){
			//按仓库打印
			if(liger.get("store_code").getValue()==""){
				$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
				return;
			}
			useId=liger.get("store_code").getValue().split(",")[0];
		}
    	parent.$.ligerDialog.open({url : 'hrp/med/initial/in/storageInPrintSetPageQc.do?template_code=08007&use_id='+useId,
    		data:{}, height: $(parent).height(),width: $(parent).width(), title:'打印模板设置',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true
    		});
    }

  //打印
    function print(){
    	 var useId=0;//统一打印
 		if('${p08017 }'==1){
 			//按用户打印
 			useId='${sessionScope.user_id }';
 		}else if('${p08017 }'==2){
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
	    			
	    			template_code:'08007',
	    			isPrintCount:false,//更新打印次数
	    			isPreview:true,//预览窗口，传绝对路径
	    			use_id:useId,
	    			p_num:1
	    			//isSetPrint:flag
	    	}; 
		 	
			//alert(JSON.stringify(para));
			
	    	printTemplate("hrp/med/initial/affiin/queryMedInByPrintTemlateQm.do?isCheck=false",para);
	    	
		}
    	
    }
	 //导出数据
	 function exportExcel(){
		//有数据直接导出
		if($("#resultPrint > table > tbody").html()!=""){
			lodopExportExcel("resultPrint","导出Excel","MED_IN_MAIN.xls",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');

		var exportPara={
			usePager:false,
           in_id:$("#in_id").val(),
           in_no:$("#in_no").val(),
           year:$("#year").val(),
           month:$("#month").val(),
           bus_type_code:$("#bus_type_code").val(),
           sup_no:$("#sup_no").val(),
           sup_id:$("#sup_id").val(),
           store_id:$("#store_id").val(),
           store_no:$("#store_no").val(),
           location_code:$("#location_code").val(),
           brief:$("#brief").val(),
           stocker:$("#stocker").val(),
           stock_type_code:$("#stock_type_code").val(),
           is_org:$("#is_org").val(),
           maker:$("#maker").val(),
           in_date:$("#in_date").val(),
           checker:$("#checker").val(),
           check_date:$("#check_date").val(),
           confirmer:$("#confirmer").val(),
           confirm_date:$("#confirm_date").val(),
           state:$("#state").val(),
           is_pay:$("#is_pay").val(),
           pay_date:$("#pay_date").val(),
           invoice_no:$("#invoice_no").val(),
           pay_money:$("#pay_money").val(),
           is_pay_all:$("#is_pay_all").val(),
           company:$("#company").val(),
           ctrl_type_code:$("#ctrl_type_code").val(),
           check_code:$("#check_code").val(),
           protocol_code:$("#protocol_code").val(),
           dept_id:$("#dept_id").val()
         };
		ajaxJsonObjectByUrl("queryMedAffiMain.do",exportPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
					 trHtml+="<td>"+item.in_id+"</td>"; 
					 trHtml+="<td>"+item.in_no+"</td>"; 
					 trHtml+="<td>"+item.year+"</td>"; 
					 trHtml+="<td>"+item.month+"</td>"; 
					 trHtml+="<td>"+item.bus_type_code+"</td>"; 
					 trHtml+="<td>"+item.sup_no+"</td>"; 
					 trHtml+="<td>"+item.sup_id+"</td>"; 
					 trHtml+="<td>"+item.store_id+"</td>"; 
					 trHtml+="<td>"+item.store_no+"</td>"; 
					 trHtml+="<td>"+item.location_code+"</td>"; 
					 trHtml+="<td>"+item.brief+"</td>"; 
					 trHtml+="<td>"+item.stocker+"</td>"; 
					 trHtml+="<td>"+item.stock_type_code+"</td>"; 
					 trHtml+="<td>"+item.is_org+"</td>"; 
					 trHtml+="<td>"+item.maker+"</td>"; 
					 trHtml+="<td>"+item.in_date+"</td>"; 
					 trHtml+="<td>"+item.checker+"</td>"; 
					 trHtml+="<td>"+item.check_date+"</td>"; 
					 trHtml+="<td>"+item.confirmer+"</td>"; 
					 trHtml+="<td>"+item.confirm_date+"</td>"; 
					 trHtml+="<td>"+item.state+"</td>"; 
					 trHtml+="<td>"+item.is_pay+"</td>"; 
					 trHtml+="<td>"+item.pay_date+"</td>"; 
					 trHtml+="<td>"+item.invoice_no+"</td>"; 
					 trHtml+="<td>"+item.pay_money+"</td>"; 
					 trHtml+="<td>"+item.is_pay_all+"</td>"; 
					 trHtml+="<td>"+item.company+"</td>"; 
					 trHtml+="<td>"+item.ctrl_type_code+"</td>"; 
					 trHtml+="<td>"+item.check_code+"</td>"; 
					 trHtml+="<td>"+item.protocol_code+"</td>"; 
					 trHtml+="<td>"+item.dept_id+"</td>"; 
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").empty();
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			lodopExportExcel("resultPrint","导出Excel","MED_IN_MAIN.xls",true);
	    },true,manager);
		return;
	 }		  
    </script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
		<tr>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" width="10%">编制日期：</td>
			<td align="left" class="l-table-edit-td" width="20%">
				<table>
					<tr>
						<td align="left" class="l-table-edit-td">
							<input class="Wdate" name="begin_date" id="begin_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" />
						</td>
						<td align="right" class="l-table-edit-td">至：</td>
						<td align="left" class="l-table-edit-td">
							<input class="Wdate" name="end_date" id="end_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" />
						</td>
					</tr>
				</table>
			</td>
			<td align="right" class="l-table-edit-td" width="10%">仓库：</td>
			<td align="left" class="l-table-edit-td" width="20%">
				<input	name="store_code" type="text" id="store_code" ltype="text"	validate="{required:false,maxlength:100}" />
			</td>
			<td align="right" class="l-table-edit-td" width="10%">状态：</td>
			<td align="left" class="l-table-edit-td" width="20%">
				<input	name="state" type="text" id="state" ltype="text" validate="{required:false,maxlength:100}" />
			</td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td">供应商：</td>
			<td align="left" class="l-table-edit-td">
				<input name="sup_code"	type="text" id="sup_code" ltype="text" validate="{required:false,maxlength:100}" />
			</td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
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
