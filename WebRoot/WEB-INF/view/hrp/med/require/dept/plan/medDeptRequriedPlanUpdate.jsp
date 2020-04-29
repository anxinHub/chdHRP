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
    <script type="text/javascript">
	var grid;
	var gridManager = null;
	var userUpdateStr;
	
	var dept_no ;
	var dept_id ;
	var acc_year = null;
	var acc_month = null;
	var state = '${medDeptMain.state}';
	var store_id = '${medDeptMain.stock_id}';
	var store_no = '${medDeptMain.stock_no}';
	$(function() {
		loadDict();//加载下拉框
		//加载数据
		loadHead(null);
		loadHotkeys();
		$("#dept_code").bind("change",function(){
	    	dept_id = liger.get("dept_code").getValue().split(",")[0];
	    	dept_no = liger.get("dept_code").getValue().split(",")[1];
	    	//loadHead();
		}); 
		$("#store_code").bind("change", function () {
			grid.columns[3].editor.grid.url = '../../../queryMedInvListDept.do?isCheck=false&store_id='+liger.get("store_code").getValue().split(",")[0];

			//grid.reRender();
		});

        query();
        is_addRow();
       
	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({name : 'req_id',value : '${medDeptMain.req_id}' });
		grid.options.parms.push({name : 'req_code',value : '${medDeptMain.req_code}'});
		
		grid.options.parms.push({name : 'dept_id',value : '${medDeptMain.dept_id}'}); 
    	grid.options.parms.push({name : 'dept_no',value : '${medDeptMain.dept_no}'});
    	
    	grid.options.parms.push({name : 'store_id',value : liger.get("store_code").getValue().split(",")[0]}); 
    	grid.options.parms.push({name : 'store_no',value : liger.get("store_code").getValue().split(",")[1]});
    	
		//加载查询条件
		grid.loadData(grid.where);
		
	}
	
	//验证
    function validateGrid() { 
    	//明细
  		var msg="";
  		var rowm = "";
  		var rows = 0;
  		var data = gridManager.getData();
  		//判断grid 中的数据是否重复或者为空
  		var targetMap = new HashMap();
  		$.each(data,function(i, v){
  			rowm = "";
  			if(v.inv_id){
  				if (!v.amount) {
	 				rowm+="[数量]、";
	 			}  
	 			if (v.price == "" || v.price == null  || v.price == 'undefined') {  
	 				rowm+="[单价]、"; 
	 			} 
	 			if(rowm != ""){
	 				rowm = "第"+(i+1)+"行" + rowm.substring(0, rowm.length-1) + "不能为空并且数量不能为0" + "\n\r";
	 			}
	 			
	 			msg += rowm;
	 			var key=v.inv_id ;
	 			var value="第"+(i+1)+"行";
	 			if(targetMap.get(key)== null || targetMap.get(key) == 'undefined' || targetMap.get(key) == ""){
	 				targetMap.put(key ,value);
	 			}else{
	 				msg += targetMap.get(key)+"与"+value+"药品编码不能重复" + "\n\r";
	 			}
	 			rows = rows + 1;
  			}
  		});
  		
  		if(rows == 0){
 			$.ligerDialog.warn("请先添加药品！");  
			return false;  
 		}
  		
  		if(msg != ""){
  			$.ligerDialog.warn(msg);  
 			return false;  
  		} 	 	 
  		return true;	
	}
	//保存
	function save(){
		if(validateGrid()){
			
			if(liger.get("dept_code").getValue() == '' ){
				$.ligerDialog.error('科室为必填项！');
		    	return ; 
			}
			
			/* var dept_id = liger.get("dept_code").getValue().split(",")[0];
			var dept_no = liger.get("dept_code").getValue().split(",")[1]; */
			var dept_id = liger.get("dept_code").getValue() == "" ? "":liger.get("dept_code").getValue().split(",")[0];
			var dept_no = liger.get("dept_code").getValue() == "" ? "":liger.get("dept_code").getValue().split(",")[1];
			
			var store_id = liger.get("store_code").getValue().split(',')[0];
			var store_no = liger.get("store_code").getValue().split(',')[1];
			
			var allData = gridManager.getData();
			if(allData.length == 0){
		    	$.ligerDialog.error('请添加明细！');
		    	return ; 
		     }
			//保存主表
			var formPara = {
					//主表信息
					req_id : '${medDeptMain.req_id}',
					req_code : '${medDeptMain.req_code}',
					dept_id : dept_id ,
					dept_no : dept_no ,
					stock_id : store_id,
					stock_no : store_no,
					make_date : $("#make_date").val(),
					rdate : $("#rdate").val(),
					other_inv : $("#other_inv").val(),
					brif : $("#brif").val(),
					//从表信息  全部信息
					allData : JSON.stringify(allData)
					
				};
		        ajaxJsonObjectByUrl("updateMedDeptRequriedPlan.do?isCheck=true", formPara, function(responseData) {
					if (responseData.state == "true") {
						parentFrameUse().query();
						query();
					}
				});
		}
		
	}
	
	function loadHead() {
		grid = $("#maingrid").ligerGrid({
		       columns: [ 
						 { display: '药品编码', name: 'inv_code', align: 'left', width:'120' ,
							 totalSummary: {
			                        type: 'sum',
			                        render: function (suminf, column, cell) {
			                            return '<div>合计</div>';
			                        }
			                    }	  
						 },
						 { display: '药品名称(E)', name: 'inv_name', align: 'left',width:'150',
							 editor : {
									type : 'select',
									valueField : 'inv_name',
									textField : 'inv_name',
									selectBoxWidth : "80%",
									selectBoxHeight : 370,
									grid : {
										columns : [ {display : '药品编码',name : 'inv_code',align : 'left'}, 
										            {display : '药品名称',name : 'inv_name',align : 'left'}, 
													{display : '别名', name : 'alias', align : 'left'},
										            {display : '规格型号',name : 'inv_model',align : 'left',width:180}, 
										            {display : '计量单位',name : 'unit_name',align : 'left'},
										            {display : '包装规格',name : 'inv_structure',align : 'left'},
										            {display : '供应商',name : 'sup_name',align : 'left'}, 
										            {display : '生产厂商',name : 'fac_name',align : 'left'},
										            {display : '计划单价',name : 'plan_price',align : 'right',
										            	render:function(rowdata){
										            		return formatNumber(rowdata.plan_price ==null ? 0 : rowdata.plan_price,'${p08006}',1);
										            	}
										            },
										            {display : '包装单位',name : 'pack_name',align : 'left'}, 
										            {display : '转换量',name : 'num_exchange',align : 'left'}, 
										          ],
										switchPageSizeApplyComboBox : false,
										onSelectRow: function (data) {
											var e = window.event;
											if (e && e.which == 1) {
												f_onSelectRow_detail(data);
											}
										},
										url : '../../../queryMedInvListDept.do?isCheck=false&&store_id='+liger.get("store_code").getValue().split(",")[0],
										pageSize : 20,
										onSuccess: function (data, g) { //加载完成时默认选中
											if (grid.editor.editParm) {
												var editor = grid.editor.editParm.record;
												var item = data.Rows.map(function (v, i) {
													return v.inv_name;
												});
												var index = item.indexOf(editor.inv_name) == -1 ? 0 : item.indexOf(editor.inv_name);
												//加载完执行
												setTimeout(function () {
													g.select(data.Rows[index]);
												}, 80);
											}
										}
									},
									keySupport : true,
									rownumbers : true,
									isScroll :true ,
									autocomplete : true,
									onSuccess : function() {
										this.parent("tr").next(".l-grid-row").find("td:first").focus();
									},
									ontextBoxKeyEnter: function (data) {
										f_onSelectRow_detail(data.rowdata);
									}
								
								}
						 },
						
						 { display: '规格型号', name: 'inv_model', align: 'left',width:'180'},
						 { display: '计量单位', name: 'unit_name', align: 'left',width:'80' },
				         {display : '包装规格',name : 'inv_structure',align : 'left',width:'100' },
				         { display: '计划数量(E)', name: 'amount', align: 'right',width:'100',
							 editor : {type : 'number'},
							 render:function(rowdata){
				            		return formatNumber(rowdata.amount ==null ? 0 : rowdata.amount,2,1);
				             } ,
				             totalSummary: {
			                        type: 'sum',
			                        render: function (suminf, column, cell) {
			                            return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum,2,1)+ '</div>';
			                        }
			                    } 
						 },
						 { display: '单价(E)', name: 'price', align: 'right',width:'100',
							 editor : {
									type : 'number',
									precision : '${p08006 }'
								},
								render : function(rowdata, rowindex, value) {
									
									rowdata.price = value == null ? "" : formatNumber(value, '${p08006 }', 0);
									return value == null ? "" : formatNumber(value, '${p08006 }', 1);
								}
						 },
						 { display: '金额', name: 'sum_money', align: 'right',width:'100',
							 render:function(rowdata){
				            		return formatNumber(rowdata.sum_money ==null ? 0 : rowdata.sum_money,'${p08005}',1);
				            } ,
				            totalSummary: {
		                        type: 'sum',
		                        render: function (suminf, column, cell) {
		                            return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum,'${p08005}',1)+ '</div>';
		                        }
		                    } 
						 },{ display: '供应商', name : 'sup_id', textField : 'sup_name', width : 250, align : 'left',
							 render : function(rowdata, rowindex, value) {	
									if(rowdata.inv_id != undefined){
										inv_id = rowdata.inv_id;
									}
									//alert(inv_id);
									return rowdata.sup_name;
								},	
								editor : {
									type : 'select',
									valueField : 'id',
									textField : 'text',
									selectBoxWidth : 250,
									selectBoxHeight : 240,
									keySupport : true,
									autocomplete : true,
									onSelected : function (data){	
								    	if(typeof (data) === "string"){
								    		var formPara="";
										 	formPara = {												 			
										 		sup_id : data.split(",")[0],
										 		sup_no : data.split(",")[1]
										 	}			 	
								    	}
									}
								}
								 	
						 },
						 { display: '生产厂商', name: 'fac_name', align: 'left',width:'250' },
						 { display: '备注', name: 'memo', align: 'left',width:'120',
							 editor : {
									type : 'text'
							 }
						 }, { 
							 display : '包装单位(E)', name : 'pack_code', textField : 'pack_name', minWidth : 80, align : 'left',
								editor : {
									type : 'select',
									valueField : 'id',
									textField : 'text',
									url : '../../../queryMedHosPackage.do?isCheck=false',
									keySupport : true,
									autocomplete : true,
								},
								render : function(rowdata, rowindex, value) {
									return rowdata.pack_name;
								} 
						 }, { 
							 display: '转换量(E)', name: 'num_exchange', align: 'right',width:'120',
							 editor : {type : 'number'}
						 }, { 
							 display: '包装数量(E)', name: 'num', align: 'right',width:'100',
							 editor : {type : 'number'}
						 }, { 
							 display: '明细ID', name: 'req_detail_id', align: 'left',width:'250',hide:true 
						}
		                ],
		                
		                 dataAction: 'server',dataType: 'server',usePager : false,
						 url:'queryMedDeptRequriedDetail.do?isCheck=false',
		                 checkbox : true,
		                 enabledEdit : state == 1 ? true : false,
						 alternatingRow : true,
						 onBeforeEdit : f_onBeforeEdit,
						 onBeforeSubmitEdit : f_onBeforeSubmitEdit,
						 onAfterEdit : f_onAfterEdit,
						 onLoaded:function(){
					         	this.addRow();
					         },
					    onsuccess:function(){
								
									//is_addRow();
								},   
		                 width: '100%', height: '95%', checkbox: true,rownumbers : true ,delayLoad : true,
		                 isScroll :true ,   selectRowButtonOnly:true,
		                 toolbar: { items: 
		                	 	[	
		                	 	 	{text: '删除', id:'del', click: del ,icon:'delete' ,disabled: state == 1 ? false : true },
		                            { line:true },
		                            {text: '配套导入', id:'pimport', click: pimport ,icon:'up' ,disabled: state == 1 ? false : true },
		                            { line:true },
		                            {text : '科室消耗导入',id : 'kimport',click : kimport ,icon : 'up',disabled: state == 1 ? false : true }, 
		      						{line : true},
		      						{text : '安全库存导入',id : 'simport',click : simport ,icon : 'up', hide:'true',disabled: state == 1 ? false : true },
		      						{line : true , hide:'true'},
		                            {text: '保存', id:'save', click: save ,icon:'save' ,disabled: state == 1 ? false : true },
		                            { line:true },
		      						{text : '关闭',id : 'close',click : this_close ,icon : 'close'}
		                   		]
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
    
    function add_rows(data){
    	//先清除数据然后再添加
    	grid.deleteAllRows();
    	grid.addRows(data);
    }
    
	var rowindex_id = "";
	var column_name="";
	function f_onBeforeEdit(e) {
		rowindex_id = e.rowindex;
		clicked = 0;
		column_name=e.column.name;
		if(column_name=='sup_id'){
			var sup = grid.getColumnByName("sup_id");
			sup.editor.url='../../../queryMedSupByInvId.do?isCheck=false&inv_id='+e.record.inv_id;
		}
	}
	
	//选中回充数据
	function f_onSelectRow_detail(data, rowindex, rowobj) {
		selectData = "";
		selectData = data;
		//alert(JSON.stringify(data)); 
		if(column_name == "inv_name"){
			if (selectData != "" || selectData != null) {
				//回充数据 
				//grid.updateCell('apply_emp', 100, e.record);
				grid.updateRow(rowindex_id, {
					inv_code : data.inv_code,
					inv_name : data.inv_name,
					inv_model : data.inv_model,
					unit_name : data.unit_name,
					num_exchange : data.num_exchange==undefined?'':data.num_exchange,
					pack_code : data.pack_code==undefined?'':data.pack_code,
					pack_name : data.pack_name==undefined?'':data.pack_name,
					sup_name : data.sup_name,
					fac_name : data.fac_name,
					price : data.plan_price,
					inv_id : data.inv_id,
					inv_no : data.inv_no,
					sup_id : data.sup_id,
					sup_no : data.sup_no,
					amount : '',
					sum_money : '', 
					inv_structure : data.inv_structure ? data.inv_structure : '',
				});
			}
		}
		return true;
	}
	function f_onSelectRow(data, rowindex, rowobj) {
		return true;
	}
	
	//编辑单元格提交编辑状态之前作判断限制
	function f_onBeforeSubmitEdit(e) {
		if (e.column.name == "inv_id" && e.value == ""){
			return false;
		}else if (e.column.name == "amount" && e.value == 0){
			return false;
		}else if (e.column.name == "price" && e.value == 0){
			return false;
		}
		return true;
		
	}
	
	// 跳转到下一个单元格之前事件
	function f_onAfterEdit(e) {
		if(e.value != "" && e.value != 0){
			if (e.column.name == "amount"){
				//自动计算金额
				if(e.record.price != undefined && e.record.price != "" && e.record.price != 0){
					grid.updateCell('sum_money', e.value * e.record.price, e.rowindex);
				}
				//自动计算包装件数
				if(e.record.num_exchange != undefined && e.record.num_exchange != "" && e.record.num_exchange != 0){
					grid.updateCell('num', e.value / e.record.num_exchange, e.rowindex);
				}
			}else if (e.column.name == "price"){
				//自动计算金额
				if(e.record.amount != undefined && e.record.amount != "" && e.record.amount != 0){
					grid.updateCell('sum_money', e.value * e.record.amount, e.rowindex);
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
						grid.updateCell('sum_money', e.record.amount * e.record.price, e.rowindex);
					}
				}
			}
		}
		return true;
		
	}
	
	//删除
	function del(){
		var json = gridManager.getCheckedRows();
        if (json.length <= 0){
        	$.ligerDialog.error('请选择要删除的行！');
        	return;
        }else{
        	gridManager.deleteSelectedRow();
        }
	}
	//增加行数据
	/*function add_rows(data){
    	grid.removeRange(gridManager.getData());
    	grid.addRows(data);
    }*/
	//删除行集合
	function deleteRange(data){
		grid.deleteRange(data);
	}
	//配套导入
	function pimport(){
		var store_id ;
		var store_no ;
    	var store_name ;
		if(liger.get("store_code").getValue()=="" 
				|| liger.get("store_code").getValue() == null 
				|| liger.get("store_code").getValue() == 'undefined')
		{
			if('${p08032}' == 1){
        		$.ligerDialog.error('请选择仓库！');
        		return;
        	}else{
        		store_id = "";
        		store_no = "";
        		store_name = "";
        	}
		}else{
			store_id = liger.get("store_code").getValue().split(",")[0];
			store_no = liger.get("store_code").getValue().split(",")[1];
			store_name = '${medDeptMain.store_name}';
		}
		
		var parm = 'store_no='+store_no
		+'&store_id='+store_id
		+'&store_name='+store_name
		+'&dept_id='+liger.get("dept_code").getValue().split(',')[0]
		+'&dept_no='+liger.get("dept_code").getValue().split(',')[1]
		+'&dept_name='+liger.get("dept_code").getText();
		
		$.ligerDialog.open({
			url: 'medDeptRequriedPlanSupportImp.do?isCheck=false&'+parm, 
			height: 500,width: 950,title:'配套导入',
			modal:true, showToggle: true, showMin: false, isResize: true, isScroll :true ,showMax : true
		});
	}
	
	//科室消耗导入
	function kimport(){
		var parm = 'store_no='+store_no
		+'&store_id='+store_id
		+'&store_name='+'${medDeptMain.store_name}'
		+'&dept_id='+liger.get("dept_code").getValue().split(',')[0]
		+'&dept_no='+liger.get("dept_code").getValue().split(',')[1];
		
    	$.ligerDialog.open({
    		url: 'medDeptRequriedPlanDeptImp.do?isCheck=false&'+parm,
    		height: 500,width: 950, title:'科室消耗导入',
    		modal:true,showToggle: true, showMin: false, isResize: true, isScroll :true ,showMax : true
		});
	}
	
	//安全库存导入
	function simport(){
		var parm = 'store_no='+store_no
		+'&store_id='+store_id
		+'&store_name='+'${medDeptMain.store_name}'
		+'&dept_id='+liger.get("dept_code").getValue().split(',')[0]
		+'&dept_no='+liger.get("dept_code").getValue().split(',')[1];
		
    	$.ligerDialog.open({
    		url: 'medDeptRequriedPlanSafeImpPage.do?isCheck=false&'+parm,
    		height: 500,width: 950, title:'安全库存导入',
    		modal:true,showToggle: true, showMin: false, isResize: true, isScroll :true ,showMax : true
		});
	}
	
	//当单据未提交 默认新增一行
    function is_addRow(){
    	if(state > 1){
	    	return;
    	}
    	setTimeout(function() {
			grid.addRow();
		}, 1000);
    }
	//打印
	function printDate(){
		
	}
	
	//关闭
	function this_close(){
		frameElement.dialog.close();
	}
	
	//键盘事件
	function loadHotkeys() {
		hotkeys('S', save);//保存
		hotkeys('D', del);//删除
		hotkeys('P', printDate);//打印
		hotkeys('M', pimport);//配套导入
		hotkeys('K', kimport);//科室消耗导入
		hotkeys('A', simport);//安全库存导入
		hotkeys('L', this_close);//关闭
		
	}
	//字典加载
	function loadDict() {
		autocomplete("#store_code","../../../queryMedStoreByRead.do?isCheck=false", "id", "text", true, true, "",false,false,'180');//仓库
		liger.get("store_code").setValue('${medDeptMain.stock_id},${medDeptMain.stock_no}');
		liger.get("store_code").setText('${medDeptMain.store_code} ${medDeptMain.store_name}');
		
		var flag = '${p08032}';
		
		if(flag == 1){
			$("#store_code").ligerComboBox({disabled:true});
			$("#store_code").attr("disabled",true);
			$("#store_code_font").show();
		}else{
			$("#store_code_font").hide();
		}
		
		autocomplete("#dept_code","../../../queryMedDept.do?isCheck=false", "id", "text", true, true, {is_last : '1'},false, false,'180');//部门
		liger.get("dept_code").setValue('${medDeptMain.dept_id},${medDeptMain.dept_no}');
		liger.get("dept_code").setText('${medDeptMain.dept_code} ${medDeptMain.dept_name}');
		
		$("#req_code").ligerTextBox({width:180,disabled:true});
        $("#make_date").ligerTextBox({width:180});
        $("#rdate").ligerTextBox({width:180});
        $("#brif").ligerTextBox({width:180});
        $("#other_inv").ligerTextBox({width:680});
        
      	
		
	}
</script>

</head>

<body >
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
		<tr>
			<td align="right" class="l-table-edit-td"  style="padding-left:10px;">计划单号：</td>
	        <td align="left" class="l-table-edit-td" >
	       		<input name="req_id" type="hidden"   id="req_id" value="${medDeptMain.req_id}" disabled="disabled"/>
	            <input name="req_code" type="text"   id="req_code" value="${medDeptMain.req_code}" disabled="disabled"/>
	        </td>
	        <td align="left"></td>
	        
	        <td align="right" class="l-table-edit-td"  style="padding-left:10px;"><font color="red">*</font>编制日期：</td>
	        <td align="left" class="l-table-edit-td"  >
	            <input class="Wdate" requried="true"  name="make_date" type="text"  value="${medDeptMain.make_date}"
	            onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" id="make_date"   />
	        </td>
	        <td align="left"></td>
	        
	        <td align="right" class="l-table-edit-td"  style="padding-left:10px;"><font color="red">*</font>编制科室：</td>
	        <td align="left" class="l-table-edit-td" >
	            <input name="dept_code" type="text" requried="true"  id="dept_code"  />
	        </td>
	        <td align="left"></td>
	        
		</tr>
		<tr>
		 	
	        <td align="right" class="l-table-edit-td"  style="padding-left:10px;"><span id="store_code_font" >
	        <font color="red">*</font></span>响应库房：</td>
	        <td align="left" class="l-table-edit-td" >
	            <input name="store_code" type="text" requried="true"  id="store_code" />
	        </td>
	        <td align="left"></td>
	        	
			<td align="right" class="l-table-edit-td"  style="padding-left:10px;"><font color="red">*</font>需求日期：</td>
	        <td align="left" class="l-table-edit-td"  >
	            <input class="Wdate" requried="true"  name="rdate" type="text"  value="${medDeptMain.rdate}"
	            onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" id="rdate"   />
	        </td>
	        <td align="left"></td>
	        
	        <td align="right" class="l-table-edit-td"  style="padding-left:10px;">摘要：</td>
	        <td align="left" class="l-table-edit-td" >
	            <input name="brif" type="text" requried="false"  id="brif"  ltype="text" value="${medDeptMain.brif}"/>
	        </td>
	        <td align="left"></td> 
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td"  style="padding-left:10px;">其他需求药品：</td>
	        <td align="left" class="l-table-edit-td"  colspan="8">
	            <input name="other_inv" type="text"  id="other_inv"  value="${medDeptMain.other_inv}"  style="width:680px;"/>
	        </td>
	       <td align="left"></td>     
	        
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td"  style="padding-left:10px;"></td>
			<td align="left" class="l-table-edit-td"  colspan="8">
	            <font color="red">药品单价300元以下或总价1000元以下的个性化临购药品且在系统中找不到药品名称的情况请在此处填写</font>
	        </td>
	       <td align="left"></td>
		</tr>
		<tr>
			<input name="thisDateB" type="hidden"   id="thisDateB" />
			<input name="thisDateE" type="hidden"   id="thisDateE" />
			<input name="lastDateB" type="hidden"   id="lastDateB" />
			<input name="lastDateE" type="hidden"   id="lastDateE" />
		</tr>
	</table>

	<div id="maingrid"></div>
</body>
</html>
