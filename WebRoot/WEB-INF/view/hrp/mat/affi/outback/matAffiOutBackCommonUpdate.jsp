<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
    <script src="<%=path%>/lib/stringbuffer.js"></script>
     <link href='<%=path%>/lib/SpreadJS/css/gcspread.sheets.9.40.20161.0.css' rel='stylesheet' type='text/css'/>
	<script src='<%=path%>/lib/SpreadJS/scripts/gcspread.sheets.all.9.40.20161.0.min.js' type='text/javascript'></script>
	<script src='<%=path%>/lib/SpreadJS/scripts/pluggable/gcspread.sheets.print.9.40.20161.0.min.js' type='text/javascript'></script>
    
    <script type="text/javascript"> 
     var dataFormat;
     var grid;
     var gridManager;
     var state = '${state}';
     var isHideCheck = '${p04015 }' == 1 ? false : true;
     var printFlag = '${p04042 }';
     var is_apply = '${is_apply}';
     $(function (){
		loadDict()//加载下拉框
		loadHead();
		$("#store_id").bind("change",function(){ 
    		grid.columns[4].editor.grid.url = '../../queryMatAffiOutBackInvList.do?isCheck=false&store_id=' 
				+ liger.get("store_id").getValue().split(",")[0] +'&dept_id='+liger.get("dept_id").getValue().split(",")[0];
    		grid.reRender();
    	 
		});
		//科室绑定change事件
		$("#dept_id").bind("change",function(){
		//if(liger.get("bus_type_code").getValue()== 21){
    		grid.columns[4].editor.grid.url = '../../queryMatAffiOutBackInvList.do?isCheck=false&store_id=' 
				+ liger.get("store_id").getValue().split(",")[0] +'&dept_id='+liger.get("dept_id").getValue().split(",")[0];
    		grid.reRender();
    	 	emp_change();
    	 	
		});
		loadHotkeys();
		queryDetail();
		change_button();
     }); 
     
     function emp_change(){
    	var dept_emp_paras={
     		dept_id:liger.get("dept_id").getValue().split(",")[0],
 			dept_no:liger.get("dept_id").getValue().split(",")[1]
     	};	
    	
		liger.get("dept_emp").clear();        			
		autocomplete("#dept_emp", "../../queryMatEmpDict.do?isCheck=false", "id", "text", true, true,dept_emp_paras,false,false,180);
     
     }
     
   	//键盘事件
 	function loadHotkeys() {
 		hotkeys('S', save);//保存
 		hotkeys('D', remove);//删除
 		hotkeys('F', confirmOut);//出库确认
 		 
 		hotkeys('U', upOpen);//上一张
 		hotkeys('E', nextOpen);//下一张
 		hotkeys('L', this_close);//关闭
 	}
 	
 	//验证
    function validateGrid() {  
    	//主表
  		if(liger.get("bus_type_code").getValue() == null || liger.get("bus_type_code").getValue() == ""){
  			$.ligerDialog.warn("业务类型不能为空!");  
  			return false;  
  		}
  		var bus_type_code = liger.get("bus_type_code").getValue();
  		if(liger.get("store_id").getValue() == null || liger.get("store_id").getValue() == ""){
  			$.ligerDialog.warn("仓库不能为空!");  
  			return false;  
  		}
  		if($("#out_date").val() == null || $("#out_date").val() == ""){
  			$.ligerDialog.warn("出库日期不能为空!");  
  			return false;  
  		}
  		if($("#dept_id").val() == null || $("#dept_id").val() == ""){
  			$.ligerDialog.warn("领料科室不能为空！");  
  			return false;  
  		}
  		/* if($("#dept_emp").val() == null || $("#dept_emp").val() == ""){
  			$.ligerDialog.warn("领料人不能为空！");  
  			return false;  
  		} */
  		
  		var validate_detail_buf = new StringBuffer();
		var out_detail_data = gridManager.rows;	
  	    var targetMap = new HashMap();
  	    var rows = 0;
  	  	var inva_date="";
		var disinfect_date="";
		
  	 	if(out_detail_data.length > 0){
  	 		$.each(out_detail_data, function(d_index, d_content){ 
  	 	    	if(d_content.inv_id){
  	 	    		var value="第"+(d_index+1)+"行";
  					if(typeof(d_content.amount) == "undefined" || d_content.amount == ""){	       				
  	  	 	      		validate_detail_buf.append(value+"对应数量为空 请输入\n");  	       				
  	  	       		}

  	 	      		if(bus_type_code == "30"){
  		 	      		if(d_content.amount > 0){
  		 	      			validate_detail_buf.append(value+"退库数量不能大于0 请重新输入<br>");
  		   		  		}
  	 	      		}else{
  		 	      		if(d_content.amount < 0){
  		 	      			validate_detail_buf.append(value+"出库数量不能小于0 请重新输入<br>");
  		   		  		}
  		 	      		if(d_content.imme_amount - d_content.amount <0){
  		 	      			validate_detail_buf.append(value+"数量不能大于即时库存数据数量 请重新输入<br>");
  		   		  		}
  	 	      		}
  	  	 	      	
	  	 	      	if(typeof(d_content.inva_date) != "undefined" && d_content.inva_date != null && d_content.inva_date != ""){
		      			inva_date = d_content.inva_date;
		      		}else{
		      			inva_date = "";
		      		}
		      		
		      		if(typeof(d_content.disinfect_date) != "undefined" && d_content.disinfect_date != null && d_content.disinfect_date != ""){
		      			disinfect_date = d_content.disinfect_date;
		      		}else{
		      			disinfect_date = "";
		      		}
		      		
		      		var key=d_content.inv_id +"|"+d_content.batch_no+"|"+d_content.bar_code+"|"+d_content.price+"|"+inva_date+"|"+disinfect_date;
		      		if(targetMap.get(key)== null || targetMap.get(key) == 'undefined' || targetMap.get(key) == ""){
						targetMap.put(key ,value);
					}else{
						validate_detail_buf.append(targetMap.get(key)+"与"+value+"材料编码、生产批号、条形码、单价、有效日期、灭菌日期不能重复"+ "\n\r"); 
					}
		      		
  	  	 	      	/* var key=d_content.inv_id +"|"+d_content.batch_no+"|"+d_content.bar_code+"|"+d_content.price;
  	  	 			if(targetMap.get(key)== null || targetMap.get(key) == 'undefined' || targetMap.get(key) == ""){  	 					
  	  	 				targetMap.put(key ,value);  	 					
  	  	 			}else{ 	 					
  	  	 				validate_detail_buf.append(targetMap.get(key)+"与"+value+"材料编码、生产批号、条形码单价不能重复" + "\n\r"); 	 					
  	  	 			} */
  	  	 			rows = rows + 1;
  	 	    	}
  	 	      	
  			}); 
  	 	}
  	 	if(validate_detail_buf.toString()  != ""){  	 			
  	 		$.ligerDialog.warn(validate_detail_buf.toString());  	 			
  	 		return false;
  	 	}
  	 	
  	 	if(rows == 0){
  			$.ligerDialog.warn("请添加明细材料！");  
 			return false;  
  		} 
  	 	
  	 	if(gridManager.rows.length == 0){ 	 			
  	 		$.ligerDialog.warn('请选择材料'); 	 			
  	 		return false;
  	 	}
  	 	return true;
 	}
 	
 	
 	//保存
    function  save(){
		grid.endEdit();
        if(validateGrid()){
        	var out_detail_data = gridManager.rows;
        	//alert(JSON.stringify(out_detail_data));	
        	var formPara={
            		out_id : $("#out_id").val(),
            		out_no : $("#out_no").val(),
        			bus_type_code : liger.get("bus_type_code").getValue(),
        			store_id : liger.get("store_id").getValue() == null ? "" : liger.get("store_id").getValue().split(",")[0],
        			store_no : liger.get("store_id").getValue() == null ? "" : liger.get("store_id").getValue().split(",")[1],
        			year : $("#out_date").val().substr(0, 4),
        	 		month : $("#out_date").val().substr(5, 2),
        			out_date : $("#out_date").val(),
        			dept_id : liger.get("dept_id").getValue() == null ? "" : liger.get("dept_id").getValue().split(",")[0],
        			dept_no : liger.get("dept_id").getValue() == null ? "" : liger.get("dept_id").getValue().split(",")[1],
        			proj_id : liger.get("proj_id").getValue() == null ? "" : liger.get("proj_id").getValue(),
        			dept_emp : liger.get("dept_emp").getValue() == null ? "" : liger.get("dept_emp").getValue().split(",")[0],
        			state :	state,
        			is_dir : $("#is_dir").val(),
        			brief : $("#brief").val(),
        			out_detail_data : JSON.stringify(out_detail_data)
    		};
            ajaxJsonObjectByUrl("updateMatAffiOutBackCommon.do?isCheck=true",formPara,function(responseData){
                if(responseData.state=="true"){
                	parentFrameUse().query();
        			queryDetail();
                }
            });	
        }
    	
    }
	
	function queryDetail(){
		grid.options.parms=[];
		grid.options.newPage=1;
        //根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'out_id',
			value : '${out_id}'
		});

    	//加载查询条件
    	grid.loadData(grid.where);
	}
     
	function loadDict(){
    	
		//字典下拉框
    	$("#out_no").ligerTextBox({width:180,disabled: true });            
    	$("#out_date").ligerTextBox({width:180});     	   	
    	//$("#brief").ligerTextBox({width:465});   
    	//autocompleteAsync("#store_id", "../../queryMatStore.do?isCheck=false", "id", "text", true, true,{is_com : '1'},true,false,'180');   
    	autocompleteAsync("#store_id", "../../queryMatStoreDictDate.do?isCheck=false", "id", "text", true, true,{is_com : '1',is_write:'1'},true,false,'180');   
    	liger.get("store_id").setValue('${store_id}');
		liger.get("store_id").setText('${store_code}');
    	autocomplete("#bus_type_code", "../../queryMatBusType.do?isCheck=false", "id", "text", true, true,{codes:'30'},true,'${bus_type_code}','180');
    	
    	autocomplete("#dept_id", "../../queryMatDeptDictDate.do?isCheck=false", "id", "text", true, true,{is_last : 1,is_write:'1'},false,'',180);
    	liger.get("dept_id").setValue('${dept_id},${dept_no}');
		liger.get("dept_id").setText('${dept_code} ${dept_name}');
		
    	/* $("#dept_id").ligerComboBox({
    	 	parms : {is_last : 1,is_write:'1'},
          	url: '../../queryMatDeptDictDate.do?isCheck=false',
          	valueField: 'id', 
           	textField: 'text', 
           	selectBoxWidth: 180,
          	autocomplete: true,
          	width: 180,
          	highLight : true,
    		keySupport:true,
          	//initIsTriggerEvent: true,
    		onSuccess : function (data){
    			this.setValue('${dept_id},${dept_no}');
    			this.setText('${dept_code} ${dept_name}');	
           },
    		onSelected: function (selectValue){   			
    			if(selectValue !=null ){   				
    				var dept_emp_paras={
    	        			dept_id:selectValue.split(",")[0],
    	    				dept_no:selectValue.split(",")[1]
    	        	};	   				
    				liger.get("dept_emp").clear();        			
    				autocomplete("#dept_emp", "../../queryMatEmpDict.do?isCheck=false", "id", "text", true, true,dept_emp_paras,false,false,180);
    			}
			}
    	 }); */
    	
    	autocomplete("#dept_emp", "../../queryMatEmpDict.do?isCheck=false", "id", "text", true, true,"",false,'',180);
    	liger.get("dept_emp").setValue('${dept_emp}');
		liger.get("dept_emp").setText('${dept_emp_name}');
    	autocomplete("#use_code", "../../queryMatOutUse.do?isCheck=false", "id", "text", true, true, "", true, "${use_code}", '180');
    	autocomplete("#proj_id", "../../queryMatProj.do?isCheck=false", "id", "text", true, true, "", false, "${proj_id}","","",515);
    	$("#proj_id").ligerTextBox({width:515});   
    	
    	//if(state == 3){
	    	$("#print").ligerButton({click: printDate, width:90,disabled:false});
    	//}else{
	    	//$("#print").ligerButton({click: printDate, width:90,disabled:true});
    	//}
		$("#printSet").ligerButton({click: printSet, width:100});
		$("#close").ligerButton({click: this_close, width:90});
	} 
	
	function loadHead(){
		var store_id='${store_id}'.split(',')[0];
    	grid = $("#maingrid").ligerGrid({
			columns: [ { 
				display: '材料编码', name: 'inv_code', align: 'left',width:120
			}, { 
				display: '材料名称(E)', name: 'inv_id', textField: 'inv_name',align: 'left',width:240,
				editor : {
					type : 'select',
	         		valueField : 'inv_id',
	         		textField : 'inv_name',
	         		selectBoxWidth : '80%',
	         		selectBoxHeight : 240,
	         		grid : {
	         			columns : [ {
	         				display : '材料编码', name : 'inv_code', width : 100, align : 'left'
	         			}, {
	         				display : '材料名称', name : 'inv_name', width : 240, align : 'left'
	         			}, {
	         				display : '物资类别', name : 'mat_type_name', width : 140, align : 'left'
	         			}, {
	         				display : '规格型号', name : 'inv_model', width : 180, align : 'left'
	         			}, {
	         				display : '生产厂商', name : 'fac_name', width : 100, align : 'left'
	         			}, { 
	        	         	display: '批号', name: 'batch_no', align: 'left', width: 200, align : 'left'
	       	         	}, { 
	       					display: '单价', name: 'price', align: 'right', width:140,
	       					render : function(rowdata, rowindex, value) {
	       						return value == null ? "" : formatNumber(value, '${p04006 }', 1);
	       					}
	       				}, { 
	         				display : '出库数量', name : 'cur_amount', width : 100, align : 'left'
	         			},  {
	         				display : '是否收费', name : 'is_charge', width : 60, align : 'left',
	         				render : function(rowdata, rowindex, value) {
	         					return value == 1 ? '是' : '否';
	         				}
	         			}, {
	         				display : '货位', name : 'location_name', width : 100, align : 'left'
	         			} ],
	         			switchPageSizeApplyComboBox : false,
	         			onSelectRow: function (data) {
							var e = window.event;
							if (e && e.which == 1) {
								f_onSelectRow_detail(data);
							}
						},
	         			url : '../../queryMatAffiOutBackInvList.do?isCheck=false&store_id=' 
	        				+ liger.get("store_id").getValue().split(",")[0] +'&dept_id='+liger.get("dept_id").getValue().split(",")[0]
	         				+'&out_id=${out_id}',
	         			
	         			pageSize : 500,
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
	         		delayLoad : true,keySupport : true,autocomplete : true,
					onSuccess: function (data, grid) {
						this.parent("tr").next(".l-grid-row").find("td:first").focus();
					},
					ontextBoxKeyEnter: function (data) {
						f_onSelectRow_detail(data.rowdata);
					}
	         	}
	         }, {display: '规格型号', name: 'inv_model', align: 'left',width:180
	         }, {display: '计量单位', name: 'unit_name', align: 'left',width:140
	         }, { display: '批号', name: 'batch_no', align: 'left',width:140
	         }, {display: '库存', name: 'cur_amount', align: 'left',width:140
	         },  {display: '数量(E)', name: 'amount', align: 'left', width:140, editor : {type : 'number'}
	         	,totalSummary: {
					align: 'left',
					render: function (suminf, column, cell) {
						return '<div>' + formatNumber(suminf.sum == null ? 0 : suminf.sum, 2, 1) + '</div>';
					}
				}
	         }, { 
				display: '单价',name:'price',align:'right',width:140,
				render : function(rowdata, rowindex, value) {
					rowdata.price = value == null ? "" : formatNumber(value, '${p04006 }', 0);
					return value == null ? "" : formatNumber(value, '${p04006 }', 1);
				}
			}, { 
				display: '金额', name: 'amount_money', align: 'right',width:140,
				render : function(rowdata, rowindex, value) {
					rowdata.amount_money = value == null ? "" : formatNumber(value, '${p04005 }', 0);
					return value == null ? "" : formatNumber(value, '${p04005 }', 1);
				},
				totalSummary: {
					align: 'right',
					render: function (suminf, column, cell) {
						return '<div>' + formatNumber(suminf.sum == null ? 0 : suminf.sum, '${p04005 }', 1) + '</div>';
					}
				}
			}, { 
				display: '有效日期', name: 'inva_date', align: 'left',width:120
			}, { 
				display: '灭菌日期', name: 'disinfect_date', align: 'left',type: 'date',format: 'yyyy-MM-dd',width:120
			}, { 
				display: '条形码', name: 'bar_code', align: 'left',width:120
			}, { 
				display: '批发价格', name: 'sale_price', align: 'right',width:140,
				/*editor : {
					type : 'numberbox',
					precision : '${p04006 }'
        		},*/
				render : function(rowdata, rowindex, value) {
					rowdata.sale_price = value == null ? "" : formatNumber(value, '${p04006 }', 0);
					return value == null ? "" : formatNumber(value, '${p04006 }', 1);
				}
			}, { 
				display: '批发金额', name: 'sale_money', align: 'right',width:140,
				render : function(rowdata, rowindex, value) {
					rowdata.sale_money = value == null ? "" : formatNumber(value, '${p04005 }', 0);
					return value == null ? "" : formatNumber(value, '${p04005 }', 1);
				},
				totalSummary: {
					align: 'right',
					render: function (suminf, column, cell) {
						return '<div>' + formatNumber(suminf.sum == null ? 0 : suminf.sum, '${p04005 }', 1) + '</div>';
					}
				}
			}, { 
				display: '零售价格', name: 'sell_price', align: 'right',width:140,
				render : function(rowdata, rowindex, value) {
					rowdata.sell_price = value == null ? "" : formatNumber(value, '${p04072 }', 0);
					return value == null ? "" : formatNumber(value, '${p04072 }', 1);
				}
			}, { 
				display: '零售金额', name: 'sell_money', align: 'right',width:140,
				render : function(rowdata, rowindex, value) {
					rowdata.sell_money = value == null ? "" : formatNumber(value, '${p04073 }', 0);
					return value == null ? "" : formatNumber(value, '${p04073 }', 1);
				},totalSummary: {
					align: 'right',
					render: function (suminf, column, cell) {
						return '<div>' + formatNumber(suminf.sum == null ? 0 : suminf.sum, '${p04073 }', 1) + '</div>';
					}
				}
			}, { 
				display: '货位名称', name: 'location_name', align: 'left',width:180
			},{ 
				display: '供应商', name: 'sup_name', align: 'left',width:180
			}, { 
				display: '备注(E)', name: 'note', align: 'left',width:180,
				editor :{
					type : 'text'
				}
			},{ 
				display: '供应商ID', name: 'sup_id',width:140 ,hide : true 
			},{ 
				display: '供应商NO', name: 'sup_no',width:140 ,hide : true 
			},{ 
				display: '批次数量合计', name: 'sum_amount',width:140 ,hide : true
			}, { 
				display: '批次明细', name: 'inv_detail_data',width:140 ,hide : true
			} ],
			usePager : false,width : '100%',height : '100%',
			enabledEdit : state == 1 ? true : false,
			isAddRow : (is_apply == 1 ) ? false : true,
			fixedCellHeight:true,heightDiff:-20,
			onBeforeEdit : f_onBeforeEdit, 
			onBeforeSubmitEdit : f_onBeforeSubmitEdit, 
			onAfterEdit : f_onAfterEdit,
			url:'queryMatAffiOutBackDetaillByCode.do?isCheck=false&out_id=${out_id}&store_id='+store_id,
			delayLoad : true,//初始化明细数据
			onsuccess:function(){
				//is_addRow();
			},		
			checkbox: true, rownumbers:true, frozen:false,//这个属性有detail明细的时候必须为false,否则明细显示不出来
			detail: { onShowDetail: showBatchSn, reload: true, single: true},//材料批次明细
			toolbar: { items: [
				{ text: '保存', id:'save', click: save,icon:'save' , disabled: state == 1 ? false : true},
				{ line:true },
				{text : '删除（<u>D</u>）',id : 'delete',click : deleteRow,icon : 'delete', disabled: state == 1 ? false : true},
		        {line : true}, 
		        { text: '审核', id:'audit', click: audit,icon:'audit', hide: isHideCheck, disabled: state == 1 ? false : true },
				{ line:true, hide: isHideCheck },
				{ text: '消审', id:'unaudit', click: unAudit,icon:'unaudit', hide: isHideCheck, disabled: state == 2 ? false : true },
				{ line:true, hide: isHideCheck },
				{ text: '退库确认', id:'confirm', click: confirm,icon:'account' , disabled: isHideCheck && state == 1 || !isHideCheck && state == 2 ? false : true },
				{ line:true },
				 
		        {text : '上一张（<u>U</u>）',id : 'up',click : upOpen,icon : 'up'} ,
		        {line : true},
		        {text : '下一张（<u>N</u>）',id : 'next',click : nextOpen,icon : 'down'},
		        {line : true},
		        {text : '关闭（<u>L</u>）',id : 'close',click : this_close,icon : 'up'}
			]}
    	});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
  	
	var rowindex_id = "";
	var column_name = "";
	function f_onBeforeEdit(e) {
		rowindex_id = e.rowindex;
		clicked = 0;
		column_name = e.column.name;		
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
	//选中回充数据
	function f_onSelectRow_detail(data, rowindex, rowobj) {
		selectData = "";
		selectData = data;
		//alert(JSON.stringify(data)); 
		//回充数据 
		if (selectData != "" || selectData != null) {
			if (column_name == "inv_id") {
				grid.updateRow(rowindex_id, {
					inv_no : data.inv_no,
					inv_code : data.inv_code,
					inv_name : data.inv_name,
					inv_model : data.inv_model == null ? "" : data.inv_model,
					unit_name : data.unit_name == null ? "" : data.unit_name,
					batch_no : data.batch_no == null ? "" : data.batch_no,
					batch_sn : data.batch_sn == null ? "" : data.batch_sn,
					bar_code : data.bar_code == null ? "" : data.bar_code,
					cur_amount : data.cur_amount == null ? "" : data.cur_amount,
					imme_amount : data.imme_amount == null ? "" : data.imme_amount,
					price : data.price == null ? "" : data.price,
					sale_price : data.sale_price == null ? "" : data.sale_price,
					sell_price : data.sell_price == null ? "" : data.sell_price,
					inva_date : data.inva_date == null ? "" : data.inva_date,
					disinfect_date : data.disinfect_date == null ? "" : data.disinfect_date,
					location_id : data.location_id == null ? "" : data.location_id,
					location_name : data.location_name == null ? "" : data.location_name,
					sup_name : data.sup_name == null ? "" : data.sup_name,
					sup_id : data.sup_id == null ? "" : data.sup_id,
					sup_no : data.sup_no == null ? "" : data.sup_no
				});
			}
		}
		return true;
	}
	  function f_onAfterEdit(e){
	    	if("inv_id" == column_name){
	    		grid.updateCell('sum_amount', 0, e.record); 
	    		grid.updateCell('inv_detail_data', "", e.record); 
	    	}else if("amount" == column_name){
	    		grid.updateCell('amount_money', e.record.amount*e.record.price, e.record); 
	    		grid.updateCell('sale_money', e.record.amount*e.record.sale_price, e.record); 
	    		grid.updateCell('sell_money', e.record.amount*e.record.sell_price, e.record); 
	    		grid.updateCell('sum_amount', 0, e.record); 
	    		grid.updateCell('inv_detail_data', "", e.record); 
	    	}
			grid.updateTotalSummary();
	    }
	// 编辑单元格提交编辑状态之前作判断限制
	function f_onBeforeSubmitEdit(e) {
		if (e.column.name == "inv_id" && e.value == ""){
			//$.ligerDialog.warn('请选择材料！');
			//grid.setCellEditing(e.record, e.column, true);
			return false;
		}
		if (e.column.name == "amount" && e.value == 0){
			//$.ligerDialog.warn('数量不能为0！');
			//grid.setCellEditing(e.record, e.column, true);
			return false;
		}
		return true;
	} 
    //删除
    function deleteRow(){ 
    	gridManager.deleteSelectedRow();
    }
    
    var gridRowData;
    function showBatchSn(row, detailPanel,callback){   	
    	gridRowData = row;
    	batchSn = document.createElement('div');
        $(detailPanel).append(batchSn);
		//detailGrid =$(detailPanel).css('margin',10).ligerGrid({
		detailGrid =$(batchSn).css({'margin-top':10, 'margin-left':60}).ligerGrid({
    		columns: [{ 
    			display: '材料编码', name: 'inv_code',width:80, align : 'left'
    		}, { 
    			display: '材料名称(E)', name: 'inv_id', textField: 'inv_name', width:240, align : 'left',
    			editor :{
					type : 'select',
	         		valueField : 'inv_id',
	         		textField : 'inv_name',
	         		selectBoxWidth : 500,
	         		selectBoxHeight : 240,
	         		grid : {
	         			columns : [ {
	         				display : '材料编码', name : 'inv_code', width : 100, align : 'left'
	         			}, {
	         				display : '材料名称', name : 'inv_name', width : 240, align : 'left'
	         			}, {
	        	         	display: '批次', name: 'batch_sn', align: 'left', width: 80, align : 'left'
	       	         	}, { 
	       					display: '单价', name: 'price', align: 'right', width:80,
	       					render : function(rowdata, rowindex, value) {
	       						return value == null ? "" : formatNumber(value, '${p04006 }', 1);
	       					}
	       				}, { 
	         				display : '库存', name : 'cur_amount', width : 50, align : 'left'
	         			}, { 
	         				display : '即时库存', name : 'imme_amount', width : 50, align : 'left'
	         			} ],
	         			switchPageSizeApplyComboBox : false,
	         			//onSelectRow : f_detail_onSelectRow_detail,
	         			onSelectRow: function (data) {
							var e = window.event;
							if (e && e.which == 1) {
								f_detail_onSelectRow_detail(data);
							}
						},
	         			url : '../../queryMatAffiOutDetailInvList.do?isCheck=false&store_id=' 
	         					+ liger.get("store_id").getValue().split(",")[0] + '&bus_type_code=' 
	         					+ liger.get("bus_type_code").getValue() + '&out_id=${out_id}&inv_id=' + row.inv_id
	         					+ '&batch_no=' + row.batch_no + '&bar_code=' + row.bar_code + '&price=' + row.price
	         					+ '&inva_date=' + row.inva_date + '&disinfect_date=' + row.disinfect_date,
	         			pageSize : 500,
						onSuccess: function (data, g) { //加载完成时默认选中
							if (detailGrid.editor.editParm) {
								var editor = detailGrid.editor.editParm.record;
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
	         		delayLoad : false, keySupport : true, autocomplete : true,// rownumbers : true,
	         		onSuccess: function (data, grid) {
						this.parent("tr").next(".l-grid-row").find("td:first").focus();
					},
					ontextBoxKeyEnter: function (data) {
						f_detail_onSelectRow_detail(data.rowdata);
					}
	         	}
    		}, { 
    			display: '批次', name: 'batch_sn', align : 'left' 
    		}, { 
     			display : '库存', name : 'cur_amount', minWidth : 50, align : 'left'
     		}, { 
     			display : '即时库存', name : 'imme_amount', minWidth : 50, align : 'left', 
     		}, { 
    			display: '数量(E)', name: 'amount', width: 60, align : 'left', editor : {type : 'float'}
    		}, { 
    			display: '单价', name: 'price', width: 80, align : 'right',
    			render : function(rowdata, rowindex, value) {
    				rowdata.price = value == null ? "" : formatNumber(value, '${p04006 }', 0);
    				return value == null ? "" : formatNumber(value, '${p04006 }', 1);
    			}
    		}, { 
    			display: '金额', name: 'amount_money', align: 'right', width:80,
    			render : function(rowdata, rowindex, value) {
    				rowdata.amount_money = value == null ? "" : formatNumber(value, '${p04005 }', 0);
    				return value == null ? "" : formatNumber(value, '${p04005 }', 1);
    			}
    		}, { 
    			display: '批发单价', name: 'sale_price', width: 80, align : 'right',
    			render : function(rowdata, rowindex, value) {
    				rowdata.sale_price = value == null ? "" : formatNumber(value, '${p04006 }', 0);
    				return value == null ? "" : formatNumber(value, '${p04006 }', 1);
    			}
    		}, { 
    			display: '批发金额', name: 'sale_money', align: 'right', width:80,
    			render : function(rowdata, rowindex, value) {
    				rowdata.sale_money = value == null ? "" : formatNumber(value, '${p04005 }', 0);
    				return value == null ? "" : formatNumber(value, '${p04005 }', 1);
    			}
    		}, { 
    			display: '零售单价', name: 'sell_price', width: 80, align : 'right',
    			render : function(rowdata, rowindex, value) {
    				rowdata.sell_price = value == null ? "" : formatNumber(value, '${p04072 }', 0);
    				return value == null ? "" : formatNumber(value, '${p04072 }', 1);
    			} 
    		}, { 
    			display: '零售金额', name: 'sell_money', align: 'right', width:80,
    			render : function(rowdata, rowindex, value) {
    				rowdata.sell_money = value == null ? "" : formatNumber(value, '${p04073 }', 0);
    				return value == null ? "" : formatNumber(value, '${p04073 }', 1);
    			}
    		} ], 
    		dataAction : 'server',dataType : 'server',usePager : true,checkbox: true,
    		rownumbers: true, enabledEdit : state == 1 ? true : false, fixedCellHeight: true, frozen: false,
    		onBeforeEdit : f_detail_onBeforeEdit, onBeforeSubmitEdit : f_detail_onBeforeSubmitEdit, onAfterEdit : f_detail_onAfterEdit,
    		width: '65%',height: '90%',data : f_getInvDetailData(row),columnWidth:80, 
    		toolbar: { items: [
    			{ text: '删除', id:'delete', click: deleteDetailRow,icon:'delete' },
    			{ line:true },
    			{ text: '添加行', id:'add', click: detailGridAddRow,icon:'add' },
    			{ line:true }
    		]} 
		});

    }
	
	//删除明细
    function deleteDetailRow(){ 
    	detailGrid.deleteSelectedRow();
    	changeDetailAmount();
    }
  	//添加空行
    function detailGridAddRow(){
    	setTimeout(function () {detailGrid.addRow();}, 1000);
    }
    function f_getInvDetailData(rowdata){
    	var data = { Rows: [] };
		if(validateStr(rowdata.inv_id) && validateStr(rowdata.amount) && rowdata.amount != 0){
			//明细中有批次信息并且主数量和明细数量相等
			if(validateStr(rowdata.inv_detail_data) && validateStr(rowdata.sum_amount) && rowdata.amount == rowdata.sum_amount){
				var rows = jsonRowsToObject(rowdata.inv_detail_data, "out_detail_id");
				for(var i = 0; i < rows.length; i++){
					data.Rows.push(rows[i]);
				}
    		}else{
    			//明细中没有批次信息，需要根据先进先出从后台取出
        		var invPara = {
    				store_id : liger.get("store_id").getValue().split(",")[0], 
					bus_type_code : liger.get("bus_type_code").getValue(),  
    				out_id : '${out_id}', 
            		inv_id : rowdata.inv_id, 
            		batch_no : rowdata.batch_no, 
            		bar_code : rowdata.bar_code, 
            		price : rowdata.price,
            		amount : rowdata.amount,
            		inva_date : rowdata.inva_date,
            		disinfect_date : rowdata.disinfect_date
            	}
        		ajaxJsonObjectByUrl("../../queryMatAffiInvByFifo.do?isCheck=false",invPara,function(responseData){
        			data = responseData;
                }, false);
				//变更主数据中材料批次信息
        		grid.updateCell('sum_amount', gridRowData.amount, gridRowData); 
        		grid.updateCell('inv_detail_data', JSON.stringify(data.Rows), gridRowData); 
        	}
    	}
        return data;
    }
    
    //改变明细数量更新主数据数量
    function changeDetailAmount(){
    	//获取明细总数量，并更新主数据数量
		var sumAmount =0;
		var dataDetail =  detailGrid.getData();
		if(dataDetail.length > 0){
			$(dataDetail).each(function(){
				if(validateStr(this.amount)){
					sumAmount += this.amount;
				}
			})
    		grid.updateCell('amount', sumAmount, gridRowData); 
    		grid.updateCell('amount_money', sumAmount*gridRowData.price, gridRowData); 
    		grid.updateCell('sale_money', sumAmount*gridRowData.sale_price, gridRowData); 
    		grid.updateCell('sell_money', sumAmount*gridRowData.sell_price, gridRowData); 
    		grid.updateCell('sum_amount', sumAmount, gridRowData); 
    		grid.updateCell('inv_detail_data', JSON.stringify(dataDetail), gridRowData); 
		}else{
			grid.updateCell('amount', sumAmount, gridRowData); 
    		grid.updateCell('amount_money', sumAmount*gridRowData.price, gridRowData); 
    		grid.updateCell('sale_money', sumAmount*gridRowData.sale_price, gridRowData); 
    		grid.updateCell('sell_money', sumAmount*gridRowData.sell_price, gridRowData); 
    		grid.updateCell('sum_amount', sumAmount, gridRowData); 
    		grid.updateCell('inv_detail_data', "", gridRowData); 
		}
    }

	var detail_id = "";
	var detail_name = "";
	function f_detail_onBeforeEdit(e) {
		detail_id = e.rowindex;
		detailClicked = 0;
		detail_name = e.column.name;		
	}
	//选中回充数据
	function f_detail_onSelectRow_detail(data, rowindex, rowobj) {
		detailSelectData = "";
		detailSelectData = data;
		//alert(JSON.stringify(data)); 
		//回充数据 
		if (detailSelectData != "" || detailSelectData != null) {
			if (detail_name == "inv_id") {
				detailGrid.updateRow(detail_id, {
					inv_no : data.inv_no,
					inv_code : data.inv_code,
					inv_name : data.inv_name,
					batch_sn : data.batch_sn == null ? "" : data.batch_sn,
					cur_amount : data.cur_amount == null ? "" : data.cur_amount,
					imme_amount : data.imme_amount == null ? "" : data.imme_amount,
					price : data.price == null ? "" : data.price,
					sale_price : data.sale_price == null ? "" : data.sale_price,
					sell_price : data.sell_price == null ? "" : data.sell_price
				});
			}
		}
		return true;
	}
    function f_detail_onAfterEdit(e){ 
    	if("amount" == detail_name){ 
    		detailGrid.updateCell('amount_money', e.record.amount*e.record.price, e.record); 
    		detailGrid.updateCell('sale_money', e.record.amount*e.record.sale_price, e.record); 
    		detailGrid.updateCell('sell_money', e.record.amount*e.record.sell_price, e.record); 
    		changeDetailAmount();
    	} 
    } 
    // 编辑单元格提交编辑状态之前作判断限制
	function f_detail_onBeforeSubmitEdit(e) {
		if (e.column.name == "inv_id" && e.value == ""){
			//$.ligerDialog.warn('请选择材料！');
			//detailGrid.setCellEditing(e.record, e.column, true);
			return false;
		}
		if (e.column.name == "amount" && e.value == 0){
			//$.ligerDialog.warn('数量不能为0！');
			//detailGrid.setCellEditing(e.record, e.column, true);
			return false;
		}
		return true;
	}
   
	//增加行数据
	function add_rows(data){
    	//grid.removeRange(gridManager.getData());
    	grid.addRows(data);
    }
	//增加行
	function addNew(){
		is_addRow();
	}
	
	function getData(){
		var manager = $("#maingrid").ligerGetGridManager();
		var data = manager.getData();
		return JSON.stringify(data);
	} 


	//删除
 	function remove(){
     	if(state > 1){
     		$.ligerDialog.error('修改失败，单据不是未审核状态！');
     		return;
     	}
     	grid.deleteSelectedRow();
     }
	
	//审核
 	function audit(){
		if(state != 1){
			$.ligerDialog.error("审核失败！单据不是未审核状态");
			return false;
		}
		var ParamVo = [];
			ParamVo.push(
				$("#group_id").val()   +"@"+ 
 				$("#hos_id").val()   +"@"+ 
 				$("#copy_code").val()   +"@"+ 
 				$("#out_id").val()
			) 
		
		$.ligerDialog.confirm('确定审核?', function(yes) {
			if (yes) {
				ajaxJsonObjectByUrl("auditMatAffiOutBackCommon.do", {ParamVo : ParamVo.toString()}, function(responseData) {
					if (responseData.state == "true") {
						$("#state").val('2');
						parentFrameUse().query();
						state = 2;
						change_button();
				    	loadHead();
				    	grid.reRender();
					}
				});
			}
		});
	 
	}
	//销审
 	function unAudit(){
		if(state != 2){
			$.ligerDialog.error("消审失败！单据不是已审核状态");
			return false;
		}

		var ParamVo = [];
			ParamVo.push(
				$("#group_id").val()   +"@"+ 
 				$("#hos_id").val()   +"@"+ 
 				$("#copy_code").val()   +"@"+ 
 				$("#out_id").val()
			) 
		
		$.ligerDialog.confirm('确定消审?', function(yes) {
			if (yes) {
				ajaxJsonObjectByUrl("unAuditMatAffiOutBackCommon.do", {ParamVo : ParamVo.toString()}, function(responseData) {
					if (responseData.state == "true") {
						$("#state").val('1');
						parentFrameUse().query();
						state = 1;
						change_button();
				    	loadHead();
				    	grid.reRender();
					}
				});
			}
		});
	 
	}
	
 	function confirmOut() {
		var is_store='${p04045 }';
		if(isHideCheck){
			if(state != 1){
				$.ligerDialog.error("确认出库失败！单据不是未审核状态");
				return false;
			}
		}else{
			if(state != 2){
				$.ligerDialog.error("确认出库失败！单据不是已审核状态");
				return false;
			}
		}
		
		
		var todayDate = new Date();
		var todayYear = todayDate.getFullYear();
		var todayMonth = todayDate.getMonth() + 1;
		var todayDate = todayDate.getDate();
		todayMonth = todayMonth < 10 ? '0' + todayMonth : todayMonth;
		todayDate = todayDate < 10 ? '0' + todayDate : todayDate;
		var today = todayYear + '-' + todayMonth + '-' + todayDate;
		var confirmDate;
		if('${p04047 }'==0){
			confirm(today);
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
							confirm(confirmDate)
						}
					}},
	                { text: '取消', onclick: function (item, dialog) { dialog.close(); } }
			]
			})
		}
		
	}

	
	//出库确认
 	function confirm(){ 		
 		
 		var is_store='${p04045 }';
 		
 		var ParamVo =[];
 		
 		/* 1.开始   说明：后期可用于维护用户输入日期操作 */
		var confirmDate = $("#out_date").val(); 
		/* 1.结束 */
 		
		ParamVo.push(
			$("#group_id").val()   +"@"+ 
			$("#hos_id").val()   +"@"+ 
			$("#copy_code").val()   +"@"+ 
			$("#out_id").val()+"@"+
			confirmDate+"@"+
			is_store +"@"+
			liger.get("store_id").getValue().split(",")[0] +"@"+
			liger.get("out_no").getValue()
		);   
		
		$.ligerDialog.confirm('确定出库确认?', function (yes){
 			if(yes){
 				ajaxJsonObjectByUrl("/CHD-HRP/hrp/mat/storage/in/verifyMatClosingDate.do?isCheck=false", {ParamVo : ParamVo.toString()}, function(responseData){
					if (responseData.state == "true") {
		 				ajaxJsonObjectByUrl("confirmMatAffiOutBackCommon.do?isCheck=true",{ParamVo : ParamVo.toString()},function (responseData){
		 					if(responseData.state=="true"){
				            	parentFrameUse().query();
//		 						$("#state").val('3');
		 						state = 3;
		 						change_button();
						    	loadHead();
						    	grid.reRender();
		 					}
		 				});
					}
				},false);
 			}
 		});
		
 	}
 	
 	//上一张
 	function upOpen(){
 		if('${out_idUp}' == $("#out_id").val()){
 			$.ligerDialog.confirm('本单据为第一张单据,是否跳转添加单据', function (yes){
				if(yes){
					parentFrameUse().add_open();
					this_close();
				}
 			});
 		}else{
 			var obj = '${group_id}'+","+'${hos_id}'+","+'${copy_code}'+"," + '${out_idUp}'+"," + '${store_id}';
 			parentFrameUse().openUpdate(obj);
 			this_close();	
 		}
 		
 	}
 	//下一张
 	function nextOpen(){
 		if('${out_idNext}' == $("#out_id").val()){
			$.ligerDialog.confirm('本单据为最后一张单据,是否跳转添加单据', function (yes){
				if(yes){
					parentFrameUse().add_open();
					this_close();
				}
 			});
 		}else{
 			var obj = '${group_id}'+","+'${hos_id}'+","+'${copy_code}'+"," + '${out_idNext}'+"," + '${store_id}';
 			parentFrameUse().openUpdate(obj);
 			this_close();
 		}
 	}
 	
  
 	function change_button(){
		//alert("printFlag:"+printFlag+"   state:"+state);
		if(printFlag==1 && state == 3){
      		$("#print").ligerButton({click: printDate, width:80, disabled:false});
      	}else if(printFlag==1 && state != 3){
      		$("#print").ligerButton({click: printDate, width:80, disabled:true});
      	}else{
      		$("#print").ligerButton({click: printDate, width:80, disabled:false});
      	}
		
	}
 	
 	//关闭
 	function this_close(){
 		frameElement.dialog.close();
 	}
 	
	function validateStr(str){
		if(str == null || str == 'undefined' || str == ''){
			return false;
		}
		return true;
	}
	
	function is_addRow(){//当数据为空时 默认新增一行
		setTimeout(function () {
			var data = grid.getData();
			if (data.length==0)
				grid.addRow();
			}, 1000);
		 if(is_apply == 1){
	    		return;
	    	}
	}
	
	//打印
	function printDate(){
		
		var useId=0;//统一打印
		if('${p04024 }'==1){
			//按用户打印
			useId='${user_id }';
		}else if('${p04024 }'==2){
			//按仓库打印
			if(liger.get("store_id").getValue()==""){
				$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
				return;
			}
			useId=liger.get("store_id").getValue().split(",")[0];
		}
		
		/* var para={
			out_id:$("#out_id").val(),
			template_code:'04020',
			p_num : 0,
			use_id:useId
		};
		
		printTemplate("queryMatAffiOutByPrintTemlate.do",para); */
		
		var para={
				out_id:	$("#out_id").val(),
    			template_code:'04020',
    			class_name:"com.chd.hrp.mat.serviceImpl.affi.out.MatAffiOutCommonServiceImpl",
    			method_name:"matAffiOutCommonTemplate",
    			isPrintCount:false,//更新打印次数
    			isPreview:false,//预览窗口，传绝对路径
    			use_id:useId,
    			p_num:0
    	}; 
	 	
	officeFormPrint(para);
	}
	
	//打印设置
	function printSet(){
		
		
		var useId=0;//统一打印
		if('${p04024 }'==1){
			//按用户打印
			useId='${user_id }';
		}else if('${p04024 }'==2){
			//按仓库打印
			if(liger.get("store_id").getValue()==""){
				$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
				return;
			}
			useId=liger.get("store_id").getValue().split(",")[0];
		}
		officeFormTemplate({template_code:"04020",use_id:useId});
		/* parent.parent.$.ligerDialog.open({url : 'hrp/mat/affi/out/affiOutPrintSetPage.do?template_code=04020&use_id='+useId,
			data:{}, height: $(parent).height(),width: $(parent).width(), title:'打印模板设置',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
		}); */
	}
	
	var begin_bar;
	//按条码自动填充材料信息
	function paddingInv_beginBar(){
		if(event.keyCode==13){  
			begin_bar = $("#begin_bar").val();
			var store_id = liger.get("store_id").getValue().split(",")[0]; 
			if(store_id && begin_bar){
				var para={
						flag: 1, //出单独材料
						store_id: store_id, 
						bar_code: begin_bar 
		    	}
				ajaxJsonObjectByUrl("../../../queryMatOutInvListByBar.do?isCheck=false",para,function (responseData){
					if(responseData.state == "true"){
				    	grid.addRows(responseData.invData.Rows);//添加数据
				    	$("#begin_bar").val("");
					}
				});
			}else{
				$.ligerDialog.warn('请选择仓库或者输入条码');  		
	    		return false;
			}
		}
	}
	
	//按条码自动填充材料信息
	function paddingInv_endBar(){
		if(event.keyCode==13){  
			var end_bar = $("#end_bar").val();
			var store_id = liger.get("store_id").getValue().split(",")[0]; 
			if(store_id && begin_bar && end_bar){
				var para={
						flag: 2, 
						store_id: store_id, 
						bar_code: begin_bar, 
						bar_code_end: end_bar 
		    	}
				ajaxJsonObjectByUrl("../../../queryMatOutInvListByBar.do?isCheck=false",para,function (responseData){
					if(responseData.state == "true"){
				    	grid.addRows(responseData.invData.Rows);//添加数据
				    	$("#begin_bar").val("");
				    	$("#end_bar").val("");
					}
				});
			}else{
				$.ligerDialog.warn('请选择仓库或者输入条码');  		
	    		return false;
			}
		}
	}
</script>
  
</head>
  
<body onload="is_addRow()">
   <div id="pageloading" class="l-loading" style="display: none"></div>
	<input name="hos_id"  type="hidden" id="hos_id" value="${hos_id}" />
	<input name="group_id"  type="hidden" id="group_id" value="${group_id}" />
	<input name="copy_code"  type="hidden" id="copy_code" value="${copy_code}" />
	<input name="out_id"  type="hidden" id="out_id" value="${out_id}" />
  	<input name="state"  type="hidden" id="state" value="${state}" />
  	<input name="is_dir"  type="hidden" id="is_dir" value="${is_dir}" />
	<div id="layout1">
		<div position="top">
				<form name="form1" method="post"  id="form1" >
			        <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
			        <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span style="color:red">*</span>出库单号：</td>
			            <td align="left" class="l-table-edit-td"><input name="out_no" type="text" id="out_no" ltype="text" value="${out_no}" /></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span style="color:red">*</span>业务类型：</td>
			            <td align="left" class="l-table-edit-td"><input name="bus_type_code" type="text" id="bus_type_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span style="color:red">*</span>仓库：</td>
			            <td align="left" class="l-table-edit-td"><input name="store_id" type="text" id="store_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
			            <td align="left"></td>
			        </tr> 
			        
			        <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span style="color:red">*</span>出库日期：</td>
			            <td align="left" class="l-table-edit-td"><input name="out_date" type="text" id="out_date" ltype="text" value="${out_date}" validate="{required:true,maxlength:20}"  class="Wdate"  onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span style="color:red">*</span>领用科室：</td>
			            <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">领料人：</td>
			            <td align="left" class="l-table-edit-td"><input name="dept_emp" type="text" id="dept_emp" ltype="text" validate="{required:true,maxlength:20}" /></td>
			            <td align="left"></td>
			        </tr> 			        
			        <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">
			            	物资用途：
			            </td>
			            <td align="left" class="l-table-edit-td">
			            	<input name="use_code" type="text" id="use_code" ltype="text" validate="{required:false}" />
			            </td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">
			            	项目：
			            </td>
			            <td align="left" class="l-table-edit-td" colspan="4">
			            	<input name="proj_id" type="text" id="proj_id" ltype="text" validate="{required:false}" />
			            </td>
			            <td align="left"></td>
			        </tr> 
			        <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">摘要：</td>
			            <td align="left" class="l-table-edit-td" colspan="3">
			            	<textarea class="l-textarea" name="brief" id="brief" rows="3" style="width: 380px;">${brief}</textarea>
			            </td>
			            <td align="center" class="l-table-edit-td" colspan="4" ><!-- style="display: none;"> -->
							<fieldset style="border:1px solid #00CC00; width: 450px;">
								<legend style="color: #000">扫码出库(注：录入条码敲回车)</legend>
								<table cellpadding="0" cellspacing="0" class="l-table-edit">
									<tr>
										<td align="right" class="l-table-edit-td">
											条形码：
										</td>
										<td align="left" class="l-table-edit-td">
											<input name="begin_bar" type="text" id="begin_bar" ltype="text" validate="{required:false}" onkeypress="paddingInv_beginBar()"/>
										</td>
										<td align="right" class="l-table-edit-td">
											至：
										</td>
										<td align="left" class="l-table-edit-td">
											<input name="end_bar" type="text" id="end_bar" ltype="text" validate="{required:false}" onkeypress="paddingInv_endBar()"/>
										</td>
									</tr>
								</table>
							</fieldset>
						</td>
			        </tr> 	
			    </table>			    
			</form>		
		</div>
		<div position="center" >
			<div id="maingrid"></div>
			<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%"  style="margin-top: 5px;">
			
			<tr>	
				<td align="center" class="l-table-edit-td" colspan="3">
					
					<button id ="print" accessKey="P"><b>打印（<u>P</u>）</b></button>
					&nbsp;&nbsp; 
					<button id ="printSet" accessKey="M"><b>打印模板（<u>M</u>）</b></button>
					&nbsp;&nbsp;
					<button id ="close" accessKey="C"><b>关闭（<u>C</u>）</b></button>
				</td>
			</tr>
		</table>
		</div>
		
	</div>
</body>
</html>
