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
     var dataFormat;
     var grid; 
     var gridManager;
     
     $(function (){
		loadDict()//加载下拉框
       
		loadHead();
     });  
     //验证
     function validateGrid() {  
  		//主表
  		/* if(liger.get("bus_type_code").getValue() == null || liger.get("bus_type_code").getValue() == ""){
  			$.ligerDialog.warn("业务类型不能为空");  
  			return false;  
  		}
  		if(liger.get("store_code").getValue() == null || liger.get("store_code").getValue() == ""){
  			$.ligerDialog.warn("仓库不能为空");  
  			return false;  
  		}
  		if($("#out_date").val() == null || $("#out_date").val() == ""){
  			$.ligerDialog.warn("出库日期不能为空");  
  			return false;  
  		}
  		 */
  		//明细
  		var msg="";
  		var rowm = "";
  		var data = gridManager.getData();
  		alert(JSON.stringify(data));
  		//判断grid 中的数据是否重复或者为空
  		var targetMap = new HashMap();
  		$.each(data,function(i, v){
  			rowm = "";
 			if (v.inv_id == "" || v.inv_id == null || v.inv_id == 'undefined') {
 				rowm+="[材料]、";
 				//$.ligerDialog.warn("第"+(i+1)+"行[材料]不能为空");  
 				//return false;  
 			}  
 			if (v.amount == "" || v.amount == null || v.amount == 'undefined') {
 				rowm+="[数量]、";
 			}  
 			if (v.price == "" || v.price == null  || v.price == 'undefined') {  
 				rowm+="[单价]、"; 
 			} 
 			if(rowm != ""){
 				rowm = "第"+(i+1)+"行" + rowm.substring(0, rowm.length-1) + "不能为空" + "\n\r";
 			}
 			msg += rowm;
 			var key=v.inv_id +"|"+v.batch_no+"|"+v.sn;
 			var value="第"+(i+1)+"行";
 			if(targetMap.get(key)== null || targetMap.get(key) == 'undefined' || targetMap.get(key) == ""){
 				targetMap.put(key ,value);
 			}else{
 				msg += targetMap.get(key)+"与"+value+"材料编码、生成批号、条形码不能重复" + "\n\r";
 			}
  		});
  		if(msg != ""){
  			$.ligerDialog.warn(msg);  
 			return false;  
  		} 	 	
  		alert("校验通过");
  		return true;	
  	}
     
     function  save(){
    	if(validateGrid()){
	    	 //alert(JSON.stringify(gridManager.getData()));
	        var formPara={
				out_no : $("#out_no").val(),
				bus_type_code : liger.get("bus_type_code").getValue(),
				store_id : liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[0],
				store_no : liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[1],
				//store_alias : liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[2],
				//store_code : liger.get("store_code").getText() == null ? "" : liger.get("store_code").getText().split(" ")[0],
				out_date : $("#out_date").val(),
				dept_id : liger.get("dept_code").getValue() == null ? "" : liger.get("dept_code").getValue().split(",")[0],
				dept_no : liger.get("dept_code").getValue() == null ? "" : liger.get("dept_code").getValue().split(",")[1],
				dept_emp : liger.get("dept_emp").getValue() == null ? "" : liger.get("dept_emp").getValue(),
					
				brief : $("#brief").val(),
				is_dir : $("#is_dir").val(),
				detailData : JSON.stringify(gridManager.getData())
			};
	        ajaxJsonObjectByUrl("addMatAffiOutCommon.do",formPara,function(responseData){
	            if(responseData.state=="true"){
	                parent.doAmount(gridManager.getData());
	            }
	        });
    	}
    }
     
   
    function loadDict(){
    	//字典下拉框
    	autocomplete("#bus_type_code", "../../queryMatBusType.do?isCheck=false", "id", "text", true, true, {codes:'9,28,30,33,34'},false, "${matAffiOut.bus_type_code}");
		autocomplete("#store_code", "../../queryMatStore.do?isCheck=false", "id", "text", true, true,"", false, "${matAffiOut.store_id},${matAffiOut.store_no}");
		autocomplete("#dept_code", "../../queryMatDept.do?isCheck=false", "id", "text", true, true,"", false, "${matAffiOut.dept_id},${matAffiOut.dept_no}");//领料科室
		autocomplete("#dept_emp", "../../queryMatEmp.do?isCheck=false", "id", "text", true, true,
				"{dept_id : "+liger.get("dept_code").getValue() == null ? "" : liger.get("dept_code").getValue().split(",")[0]+"}"
				, false, "${matAffiOut.dept_emp}");//领料人
		
		//格式化文本框
        $("#out_no").ligerTextBox({width:160, disabled:true});
        $("#bus_type_code").ligerTextBox({width:160});
        $("#store_code").ligerTextBox({width:160});
        $("#out_date").ligerTextBox({width:160});
        $("#dept_code").ligerTextBox({width:160});
        $("#dept_emp").ligerTextBox({width:160});
      
        $("#brief").ligerTextBox({width:400});
        //格式化按钮
		$("#save").ligerButton({click: save, width:90});
		$("#print").ligerButton({click: printDate, width:90});
		$("#close").ligerButton({click: this_close, width:90});
     } 
	
    function loadHead() {
    	var detail_data = ${matAffiOutDetail};
		grid = $("#maingrid").ligerGrid({
			columns : [{
				display : '材料编码',name : 'inv_code',minWidth : 100,align : 'left',
				
				},{display: '材料名称', name: 'inv_name', align: 'left',width:180,
					editor : {
						type : 'select',
						valueField: 'id', 
						textField: 'text',
						url : '../../queryMatInvDict.do?isCheck=false&is_com=1',
					    keySupport:true,
						selectBoxWidth: 660,
						selectBoxHeight: 240,
				      	autocomplete: true,
				      	highLight : true
            	    }
				}, {display : '规格型号', name : 'inv_model', minWidth : 80, align : 'left'
				}, {display : '计量单位', name : 'unit_name', minWidth : 80, align : 'left'
				}, {display : '批号', name : 'batch_no', minWidth : 80, align : 'left'	
				}, {display : '当前库存', name : 'cur_amount', minWidth : 80, align : 'right'	
				}, {display : '数量(E)', name : 'amount',    minWidth : 80, type : 'number',
					align : 'right',
					editor : { type : 'number', },
					render : function(rowdata, rowindex, value) {
						return rowdata.amount == null ? "" : formatNumber(rowdata.amount, 2, 1);
					}
				}, {display : '单价(E)', name : 'price', minWidth : 80, align : 'right',
					type : 'number',
					editor : {
						type : 'number',
					},
					render : function(rowdata, rowindex, value) {
						return rowdata.price == null ? "" : formatNumber(rowdata.price, '${p04006}', 1);
					}
				}, {display : '金额', name : 'amount_money', minWidth : 80, type : 'number', align : 'right',
					render : function(rowdata, rowindex, value) {
						return rowdata.amount_money == null ? "" : formatNumber(rowdata.amount_money, '${p04005}', 1);
					}
				}, {display : '有效日期(E)', name : 'inva_date', type: 'date', format: 'yyyy-MM-dd', width : 100,
					editor : {
						type : 'date',
					}
				}, {display : '灭菌日期(E)', name : 'disinfect_date', type: 'date', format: 'yyyy-MM-dd', width : 100,
					editor : {
						type : 'date',
					}
				},{display : '条形码(E)', name : 'sn', minWidth : 80,
					editor : {
						type : 'text',
					},
					align : 'left'
				},{display : '零售单价(E)', name : 'sell_price', type : 'number', minWidth : 80, align : 'right',
					render : function(rowdata, rowindex, value) {
						return rowdata.sell_price == null ? "" : formatNumber(rowdata.sell_price, '${p04072}', 1);
					}
				},{display : '零售金额', name : 'sell_money', type : 'number', minWidth : 80, align : 'right',
					render : function(rowdata, rowindex, value) {
						return rowdata.sell_money == null ? "" : formatNumber(rowdata.sell_money, '${p04073}', 1);
					}
				},{
					display : '货位名称(E)',
					name : 'location_id',
					textField : 'text',
					minWidth : 80,
					editor : {
						type : 'select',
						valueField : 'id',
						textField : 'text',
						url : '../../queryMatLocationDict.do?isCheck=false',
						keySupport : true,
						autocomplete : true,
					},
					align : 'left',
					render : function(rowdata, rowindex, value) {
						return rowdata.text;
					}
				},{ display : '备注', name : 'note', align : 'left'
				},{ display : '材料变更号', name : 'inv_no', align : 'left'
				},{ display: '申请单明细ID', name: 'detail_id', align: 'left'}
			],
			usePager : false,width : '100%',height : '95%',enabledEdit : true,fixedCellHeight:true,onAfterEdit: f_onAfterEdit,
			selectRowButtonOnly:true,isSingleCheck:true,checkbox: true,rownumbers:true,isScroll:true,
			data : detail_data,//设置为申请单数据源 
			toolbar : {
				items : [ {text : '删除（<u>D</u>）',id : 'delete',click : remove,icon : 'delete'}, 
				          {line : true}, 
				          {text : '整单出库（<u>W</u>）',id : 'wholeOut',click : wholeOut,icon : ''},
				          {line : true}, 
				          {text : '配套导入（<u>M</u>）',id : 'matchImp',click : matchImp,icon : ''},
				          {line : true}, 
				          {text : '增加行（<u>N</u>）',id : 'addNew',click : addNew,icon : ''}
				        ]
			}
		});

		gridManager = $("#maingrid").ligerGetGridManager();
		
		grid.toggleCol("detail_id", false);
        
        $(document).bind('keydown.grid', function(event) {
    		if (event.keyCode == 13) {// enter,也可以改成9:tab
    			
    			grid.endEditToMatNext();
    			
    		}
    	});
    	$("#maingrid").on('focus', 'input', function() {
    		if (clicked != 0)
    			return;
    		var curdom = $(this).parent();
    		
    		if (curdom.hasClass('l-text-combobox') && !$(this).attr('readonly')) {
    			var clkbutton = curdom.find('.l-trigger-icon');
    			clicked = 2;
    			clkbutton[0].click();
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
		clicked = 0;
		column_name = e.column.name;		
	}
	//选中回充数据
	function f_onSelectRow_detail(data, rowindex, rowobj) {
		selectData = "";
		selectData = data;
		//alert(JSON.stringify(data)); 
		//回充数据 
		if (selectData != "" || selectData != null) {
			if(column_name == "location_id"){
				grid.updateRow(rowindex_id, {
					location_id : data.location_id
				});
			
			}
		}
		return true;
	}
	
	function f_onSelectRow(data, rowindex, rowobj) {
		return true;
	}
	
	// 编辑后事件 当编辑材料名称时候弹出材料选择框
    function f_onAfterEdit(e) {
		if(e.column.name=='inv_name' && e.value != ''){	
			var store_id = liger.get("store_code").getValue()?liger.get("store_code").getValue():'null';
			var paras = e.value+"@"+store_id;
			//alert(paras);
			$.ligerDialog.open({
				url: "matMatAffiOutInvFifoPage.do?isCheck=false&paras="+paras, height: 450,width: 1000, 
				title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,
				buttons: [ 
					{ text: '选择', onclick: function (item, dialog) { dialog.frame.checkMatOutFifo(); },cls:'l-dialog-btn-highlight' }, 
					{ text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
				] 
			});

		}else if (e.column.name == "amount"){
			//自动计算金额
			if(e.record.price != undefined && e.record.price != "" && e.record.price != 0){
				grid.updateCell('amount_money', e.value * e.record.price, e.rowindex);
			}
			//自动计算零售金额
			if(e.record.sell_price != undefined && e.record.sell_price != "" && e.record.sell_price != 0){
				grid.updateCell('sell_money', e.value * e.record.sell_price, e.rowindex);
			}
			//自动计算包装件数
			if(e.record.num_exchange != undefined && e.record.num_exchange != "" && e.record.num_exchange != 0){
				grid.updateCell('num', e.value / e.record.num_exchange, e.rowindex);
			}
		}else if (e.column.name == "price"){
			//自动计算金额
			if(e.record.amount != undefined && e.record.amount != "" && e.record.amount != 0){
				grid.updateCell('amount_money', e.value * e.record.amount, e.rowindex);
			}
			//计算包装单价
			if(e.record.num != undefined && e.record.num != "" && e.record.num != 0){
				grid.updateCell('pack_price', e.value * e.record.num, e.rowindex);
			}
		}else if (e.column.name == "num_exchange"){
			//自动计算包装件数
			if(e.record.amount != undefined && e.record.amount != "" && e.record.amount != 0){
				grid.updateCell('num', e.record.amount / e.value, e.rowindex);
			}
		}else if (e.column.name == "num"){
			//自动计算数量与金额
			if(e.record.num_exchange != undefined && e.record.num_exchange != "" && e.record.num_exchange != 0){
				grid.updateCell('amount', e.value * e.record.num_exchange, e.rowindex);
				if(e.record.price != undefined && e.record.price != "" && e.record.price != 0){
					grid.updateCell('amount_money', e.record.amount * e.record.price, e.rowindex);
				}
				if(e.record.sell_price != undefined && e.record.sell_price != "" && e.record.sell_price != 0){
					grid.updateCell('sell_money', e.record.amount * e.record.sell_price, e.rowindex);
				}
			}
			//自动包装单价
			if(e.record.price != undefined && e.record.price != "" && e.record.price != 0){
				grid.updateCell('pack_price', e.record.price * e.value, e.rowindex);
			}
		}else if (e.column.name == "sell_price"){
			//自动计算零售金额
			if(e.record.amount != undefined && e.record.amount != "" && e.record.amount != 0){
				grid.updateCell('sell_money', e.value * e.record.amount, e.rowindex);
			}
		}
		return true;
    }
	
	
	
	function getData(){
		var manager = $("#maingrid").ligerGetGridManager();
		var data = manager.getData();
		return JSON.stringify(data);
	}
	
	//增加行
	function addNew(){
		is_addRow();
	}
	
	//增加行
	function is_addRow() {
		setTimeout(function() { //当数据为空时 默认新增一行
			grid.addRow();
			//grid.beginEdit(0);
		}, 1000);
	}
	
	//增加行数据
	function add_rows(data){
    	//grid.removeRange(gridManager.getData());
    	grid.addRows(data);
    }
	
	//删除行集合
	function deleteRange(data){
		grid.deleteRange(data);
	}
	
	//配套导入
	function matchImp(){
		var para = "store_id=" + liger.get("store_code").getValue() +
		"&store_text=" + liger.get("store_code").getText();
		
		$.ligerDialog.open({
			title: '配套导入',
			height: 450,
			width: 1000,
			url: 'matAffiOutCommonMatchImpPage.do?isCheck=false&'+para,
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true, top: 1
		});  
	}
	//整单出库页面
	function wholeOut(){
		var para = "store_id=" + liger.get("store_code").getValue() +
		"&store_text=" + liger.get("store_code").getText();
		
		$.ligerDialog.open({
			title: '整单出库',
			height: 450,
			width: 1000,
			url: 'matAffiOutCommonWholeOutPage.do?isCheck=false&'+para,
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true, top: 1
		});
		
	}
	
	
	//键盘事件
	function loadHotkeys() {
		hotkeys('S', save);//保存
		hotkeys('D', remove);//删除
		hotkeys('P', printDate);//打印
		hotkeys('W', wholeOut);//整单出库
		hotkeys('M', matchImp);//配套导入
		hotkeys('N', addNew);//增加行
		
		hotkeys('C', this_close);//关闭
	}
	
	//打印
	function printDate(){
	
	}

	//关闭
	function this_close(){
		frameElement.dialog.close();
	}
	
	//领料科室改变，领料人改变
	function change(){
		$("#dept_emp").val("");
		var dept_code = liger.get("dept_code").getValue();
		
		if(dept_code =='' || dept_code == null){
			
    		$("#dept_emp").ligerComboBox({
    		 	parms :"",
    		 	url: '../../queryMatEmp.do?isCheck=false',
    	      	valueField: 'id',
    	       	textField: 'text', 
    	      	autocomplete: true,
    	      	selectBoxWidth: 160,
    	      	width: 160
    		 });
			
		}else{
			var dept_id = dept_code.split(",")[0];
			$("#dept_emp").ligerComboBox({
			 	parms : {'dept_id':dept_id},
			 	url: '../../queryMatEmp.do?isCheck=false',
		      	valueField: 'id',
		       	textField: 'text', 
		      	autocomplete: true,
		      	selectBoxWidth: 160,
		      	width: 160
			 });
		}
	}
    </script>
  
</head>
  
<body onload="is_addRow()">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post"  id="form1" >
		<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
	        <tr>
	        	
	            <td align="right" class="l-table-edit-td" ><font color="red">*</font>出库单号：</td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="out_no" type="text" id="out_no" value="自动生成" ltype="text" />
	            </td>
	            <td align="right" class="l-table-edit-td" ><font color="red">*</font>业务类型：</td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="bus_type_code" type="text" id="bus_type_code" required="true" ltype="text" validate="{required:true}" />
	            </td>
	            <td align="right" class="l-table-edit-td"  ><font color="red">*</font>仓库：</td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="store_code" type="text" id="store_code" required="true" ltype="text" validate="{required:true}" />
	            </td>
	        </tr> 
	        <tr>
	            <td align="right" class="l-table-edit-td" ><font color="red">*</font>编制日期：</td>
	            <td align="left" class="l-table-edit-td">
	            	<input class="Wdate" name="out_date" id="out_date" required="true" type="text" value=""${matAffiOut.out_date}"" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
	            </td>
	            <td align="right" class="l-table-edit-td" ><font color="red">*</font>领料科室：</td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="dept_code" type="text" id="dept_code" required="true" onchange="change();" ltype="text" validate="{required:true}" />
	            </td>
	            <td align="right" class="l-table-edit-td" ><font color="red">*</font>领料人：</td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="dept_emp" type="text" id="dept_emp"  ltype="text" required="true" validate="{required:true,maxlength:20}"/>
	            </td>
	        </tr> 
	        <tr>
	            <td align="right" class="l-table-edit-td" >
					备&nbsp;&nbsp;&nbsp;&nbsp;注：
	            </td>
	            <td align="left" class="l-table-edit-td" colspan="5">
	            	<input name="brief" type="text" id="brief" ltype="text"  value="${matAffiOut.brief}" validate="{required:true,maxlength:50}" />
	            </td>
			</tr>
			<tr>
				<td>
					<input name="is_dir" type="hidden" id="is_dir" />
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
					&nbsp;&nbsp;<!-- 
					<button id ="print" accessKey="P"><b>打印（<u>P</u>）</b></button>
					&nbsp;&nbsp; -->
					<button id ="close" accessKey="C"><b>关闭（<u>C</u>）</b></button>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>
