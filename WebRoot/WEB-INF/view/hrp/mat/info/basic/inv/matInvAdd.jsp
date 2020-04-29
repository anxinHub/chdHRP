<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%  
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:auto;">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
    <script src="<%=path%>/lib/hrp/mat/mat.js"	type="text/javascript"></script>
    <script type="text/javascript">
     var dataFormat; 
     var grid;
     var gridManager = null; 
     var barcode='${p04070}';
     //记录 证件信息
    
     var certIds ;
     $(function (){
		loadDict();//加载下拉框
        loadForm();
		loadButton();
		loadHotkeys();
		loadHead(null);//加载供应商数据
		if('${p04001 }' == '1'){
			$("#inv_code").attr("disabled", true);
			$("#inv_code").val("自动生成");
		}
		 if(barcode!=1){
	        
	        	$("#bar_code_new").ligerTextBox({width:160});
	        }else{
	        	$("#bar_code_new").ligerTextBox({width:160,disabled:true});
	        }
     });
		
  
	function loadHead(){
		grid = $("#maingrid").ligerGrid({
			columns: [ { 
				display: '供应商编码', name: 'sup_code', align: 'left'
			}, { 
				display: '供应商名称(E)',
				name: 'sup_id',
				align: 'left',
				textField : 'sup_name',
				editor :{
					type : 'select',
					valueField : 'sup_id',
					textField : 'sup_name',
					selectBoxWidth : 410,
					selectBoxHeight : 200,
					grid : {
						columns : [ {
							display : '供应商编码', name : 'sup_code', align : 'left'
						}, {
							display : '供应商名称', name : 'sup_name', align : 'left',width:'auto'
						/*}, {
							display : '供应商证件信息', name : 'sup_cert', align : 'left'
						}, {
							display : '联系人', name : 'contact', align : 'left'
						}, {
							display : '电话', name : 'phone', align : 'left'*/
						} ],
						switchPageSizeApplyComboBox : false,
						onSelectRow : f_onSelectRow_detail,
						url : 'queryMatInvSupListDisable.do?isCheck=false',
						//delayLoad:true,
						usePager: true,
						pageSize : 5,
					},
					width: '98%', height: '98%', 
					keySupport : true,
					autocomplete : true,
					onSuccess : function() {
						this.parent("tr").next(".l-grid-row").find("td:first").focus();
					}
				}
			/*}, { 
				display: '供应商证件信息', name: 'sup_cert', align: 'left'
			}, { 
				display: '联系人', name: 'contact', align: 'left'
			}, { 
				display: '电话', name: 'phone', align: 'left',type: 'int'*/
			},{
				display : '是否默认', name : 'is_default', align : 'center',
				render : function(rowdata, rowindex,value) {
					
					return "<input name='is_default"+rowindex+"'  type='checkbox' id='is_default"+rowindex+"' ltype='text'"
					+" style='margin-top:5px;' />";
				}
			}  ],
			dataAction: 'server',dataType: 'server',usePager:false,
			width: '98%', height: '200', checkbox: true,rownumbers:true,
			enabledEdit : true,alternatingRow : true,onBeforeEdit : f_onBeforeEdit,
			onBeforeSubmitEdit : f_onBeforeSubmitEdit,onAfterEdit : f_onAfterEdit,
			//isScroll : false,
			isAddRow:false,
			selectRowButtonOnly:true,//heightDiff: -10,
			toolbar: { items: [
				{ text: '删除', id:'delete', click: deleteRow,icon:'delete' },{ line:true },
				{ text: '添加行', id:'add', click: addCenterRow, icon:'add' },{ line:true }
			]}
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	          
	}		
	
	var rowindex_id = "";
	var column_name="";
	function f_onBeforeEdit(e) {
		
		if(($("#is_single_ven").prop("checked") ? 1 : 0)==1 &&  e.rowindex>=1)
		{
			
			alert("当选着唯一供应商时只可以添加一个供应商！");
			return false;
			
		}
		
		rowindex_id = e.rowindex;
		clicked = 0;
		column_name=e.column.name;
	}
	
	

	//选中回充数据
	function f_onSelectRow_detail(data, rowindex, rowobj) {
		selectData = "";
		selectData = data;
		if (column_name == "sup_id") {
			if (selectData != "" || selectData != null) {
				//回充数据 
				grid.updateRow(rowindex_id, {
					sup_code : data.sup_code,
	 				sup_name : data.sup_name/*, 
	 				sup_cert : data.sup_cert, 
	 				contact : data.contact, 
	 				phone : data.phone */
	 			});
			}
	 	}
	 	return true;
	 }
	 		
	function f_onSelectRow(data, rowindex, rowobj) {
	 	return true;
	}
	 		
	// 编辑单元格提交编辑状态之前作判断限制
	function f_onBeforeSubmitEdit(e) {
	 	return true;
	 }
	 
	// 跳转到下一个单元格之前事件
	function f_onAfterEdit(e) {
	 			
		return true;
	}
	
	//自动添加行
	function is_addRow() {
		setTimeout(function() { //当数据为空时 默认新增一行
			grid.addRow();
		}, 100);
	}
	//手动添加行
	function addCenterRow() {
		grid.addRow();
	}
	
	//删除选中行
	function deleteRow(){
		gridManager.deleteSelectedRow();
	}
	   //表单提交的全部弹出数据//桐庐县中医院 添加新材料时  如果系统已存在相同名称的材料 规格型号 则会跳出提示框 提示已存在相同材料 规格型号 （是否继续添加）
	function  save(){
    	 
		grid.endEdit();
		
        if($("form").valid()){
        	validateGrid();
        	
        	var param = '';
        	var rows = 0;
			var count = 0 ;
			var targetMap = new HashMap();
			var d = gridManager.getData();
			if(d.length != 0){	
				$.each(d,function(rowindex,item){
					
	 				if(typeof(item.sup_id) != "undefined" && item.sup_id != null && item.sup_id != ''){
	 					var sup_id = item.sup_id;
						var is_default = $("#is_default"+rowindex).is(":checked")? 1 : 0;
						
						if(is_default == 1){
							count++;
						}
						
						param+=sup_id+","+is_default+"@";
						var key = item.sup_id;
						var value="第"+(rowindex+1)+"行";
						
			 			if(targetMap.get(key)== null || targetMap.get(key) == 'undefined' || targetMap.get(key) == ""){
			 				targetMap.put(key ,value);
			 			}else{
			 				msg += value + targetMap.get(key) +'供应商重复.<br> '
			 			}
			 			
			 			rows +=1;
	 				}
				});
			}
			
			if(rows != 0){
				if(count != 1){
					$.ligerDialog.warn('是否默认必选且只能选中一个');
					return ; 
				}
			}
			 
        	var para = {
        		inv_name:$("#inv_name").val() ,
        		inv_model:$("#inv_model").val(),
        		mat_type_id : liger.get("mat_type_code").getValue() == null ? "" : liger.get("mat_type_code").getValue().split(",")[0]
        	};
        	
        	ajaxJsonObjectByUrl("queryMatInvExist.do?isCheck=false",para,function(responseData){
    	            if(responseData.state=="false"){
    	            	$.ligerDialog.error(responseData.info);
    	            }else{
    	            	if(!dateValid("sdate", "edate")){
    	            		$.ligerDialog.warn("启用日期不能大于停用日期");  
    	        			return false;
    	        		}
    	            	
    	    			var formPara={
    	    				supData : param,
    	    				certIds : certIds ,
    	    				inv_code : $("#inv_code").val(),
    	    				inv_name : $("#inv_name").val(),
    	    				alias : $("#alias").val(),
    	    				mat_type_id : liger.get("mat_type_code").getValue() == null ? "" : liger.get("mat_type_code").getValue().split(",")[0],
    	    				mat_type_no : liger.get("mat_type_code").getValue() == null ? "" : liger.get("mat_type_code").getValue().split(",")[1],
    	    				instru_type_id : liger.get("instru_type_id").getValue() == null ? "" : liger.get("instru_type_id").getValue(),
    	    				price_type : liger.get("price_type").getValue() == null ? "" : liger.get("price_type").getValue(),
    	    				amortize_type : liger.get("amortize_type").getValue() == null ? "" : liger.get("amortize_type").getValue(),
    	    				inv_model : $("#inv_model").val(),
    	    				charge_code : $("#charge_code").val(),
    	    				unit_code : liger.get("unit_code").getValue() == null ? "" : liger.get("unit_code").getValue(),
    	    				fac_id : liger.get("fac_code").getValue() == null ? "" : liger.get("fac_code").getValue().split(",")[0],
    	    				fac_no : liger.get("fac_code").getValue() == null ? "" : liger.get("fac_code").getValue().split(",")[1],
    	    				plan_price : $("#plan_price").val(),
    	    				price_rate : $("#price_rate").val(),  	
    	    				sell_price : $("#sell_price").val(),
    	    				sdate : $("#sdate").val(),
    	    				edate : $("#edate").val(),
    	    				agent_name : $("#agent_name").val(),
    	    				brand_name : $("#brand_name").val(),
    	    				inv_usage : $("#inv_usage").val(),
    	    				inv_structure : $("#inv_structure").val(),
    	    				per_weight : $("#per_weight").val(),   
    	    				bar_code_new : $("#bar_code_new").val(),
    	    		  	   // bar_code_new :	barcode == 1 ? liger.get("inv_code").getValue() :" " ,	
    	    				per_volum : $("#per_volum").val(),
    	    				abc_type : liger.get("abc_type").getValue() == null ? "" : liger.get("abc_type").getValue(),
    	    				is_single_ven : $("#is_single_ven").prop("checked") ? 1 : 0,
    	    				stora_tran_cond : $("#stora_tran_cond").val(),
    	    				use_state : liger.get("use_state").getValue() == null ? "" : liger.get("use_state").getValue(),
    	    				is_bid : $("#is_bid").prop("checked") ? 1 : 0,
    	    				bid_date : $("#bid_date").val(),
    	    				bid_code : $("#bid_code").val(),
    	    				memory_encoding : $("#memory_encoding").val(),
    	    				source_plan : liger.get("source_plan").getValue() == null ? "" : liger.get("source_plan").getValue(),
    	    			
    	    				is_zero_store : $("#is_zero_store").prop("checked") ? 1 : 0,
    	    				is_involved : $("#is_involved").prop("checked") ? 1 : 0,
    	    				is_implant : $("#is_implant").prop("checked") ? 1 : 0,
    	    								
    	    				is_charge : $("#is_charge").prop("checked") ? 1 : 0,
    	    				is_highvalue : $("#is_highvalue").prop("checked") ? 1 : 0,
    	    				is_dura : $("#is_dura").prop("checked") ? 1 : 0,
    	    				is_com : $("#is_com").prop("checked") ? 1 : 0,
    	    				is_bar : $("#is_bar").prop("checked") ? 1 : 0,
    	    				is_per_bar : $("#is_per_bar").prop("checked") ? 1 : 0,
    	    				is_quality : $("#is_quality").prop("checked") ? 1 : 0,
    	    				is_disinfect : $("#is_disinfect").prop("checked") ? 1 : 0,
    	    				is_cert : $("#is_cert").prop("checked") ? 1 : 0,
    	    				is_sec_whg : $("#is_sec_whg").prop("checked") ? 1 : 0,
    	    				is_shel_make : $("#is_shel_make").prop("checked") ? 1 : 0, 
    	    				is_inv_bar : $("#is_inv_bar").prop("checked") ? 1 : 0, 
    	    				supData : param   
    	    			};
    	    	        ajaxJsonObjectByUrl("addMatInv.do",formPara,function(responseData){
    	    	            if(responseData.state=="true"){
    	    	            	if(parentFrameUse().query){
	    	    	            	parentFrameUse().query();
    	    	            	}
	    	                   	 $.ligerDialog.confirm('是否继续添加?', function (yes){
	    	                 		if(yes){
	    	                 			if('${p04001 }' == 1){
	    	        	        			$("#inv_code").val("自动生成");
	    	        	        		}else{
	    	        		            	$("#inv_code").val("");
	    	        	        		}
	    	                 			
	    	        	    			$("#inv_name").val("");
	    	        	    			$("#alias").val("");
	    	        	    			$("#inv_model").val("");
	    	        	    			$("#plan_price").val("");
	    	        	    			$("#price_rate").val("");
	    	        	    			$("#sell_price").val("");
	    	        	    			$("#agent_name").val("");
	    	        	    			$("#brand_name").val("");
	    	        	    			$("#inv_usage").val("");
	    	        	    			$("#inv_structure").val("");
	    	        	    			$("#bar_code_new").val("");
	    	        	    			$("#is_single_ven").prop("checked", false);
	    	        	    			$("#is_charge").prop("checked", false);
	    	        	    			$("#is_highvalue").prop("checked", false);
	    	        	    			$("#is_dura").prop("checked", false);
	    	        	    			$("#is_com").prop("checked", false);
	    	        	    			$("#is_bar").prop("checked", false);
	    	        	    			$("#is_per_bar").prop("checked", false);
	    	        	    			$("#is_quality").prop("checked", false);
	    	        	    			$("#is_disinfect").prop("checked", false);
	    	        	    			$("#is_sec_whg").prop("checked", false);
	    	        	    			$("#is_shel_make").prop("checked", false);
	    	        	    			$("#is_inv_bar").prop("checked", false);
	    	        	    			loadDict();
	    	        	    			grid.loadData();
	    	        	    			is_addRow();
	    	                 		}else{
	    	                 			parentFrameUse().update_open(responseData.update_para);
	    	                 			this_close();
	    	                 		}
	    	                 	});
    	    	            }
    	    	        });
    	            }
        	})
        }
    }
     
    /*  //表单提交的全部弹出数据
	function  save(){
    	 
		grid.endEdit();
    	 
        if($("form").valid()){
        	var flag = validateGrid();
        	
        	if(flag == false){
        		return ; 
        	}
        	if(!dateValid("sdate", "edate")){
        		$.ligerDialog.warn("启用日期不能大于停用日期");  
    			return false;
    		}
        	
        	var param = '';
			var count = 0 ;
			var rows = 0;
			var msg="";
			var targetMap = new HashMap();
			var d = gridManager.getData();
			
			//if(JSON.stringify(d) != '[{}]'){
			if(d.length != 0){	
				$.each(d,function(rowindex,item){
					
	 				if(typeof(item.sup_id) != "undefined" && item.sup_id != null && item.sup_id != ''){
	 					var sup_id = item.sup_id;
						var is_default = $("#is_default"+rowindex).is(":checked")? 1 : 0;
						
						if(is_default == 1){
							count++;
						}
						
						param+=sup_id+","+is_default+"@";
						var key = item.sup_id;
						var value="第"+(rowindex+1)+"行";
						
			 			if(targetMap.get(key)== null || targetMap.get(key) == 'undefined' || targetMap.get(key) == ""){
			 				targetMap.put(key ,value);
			 			}else{
			 				msg += value + targetMap.get(key) +'供应商重复.<br> '
			 			}
			 			
			 			rows +=1;
	 				}
				});
			}
			
			if(rows != 0){
				if(count != 1){
					$.ligerDialog.warn('是否默认必选且只能选中一个');
					return ; 
				}
			}
			
			if(msg != ""){
				$.ligerDialog.warn(msg);
				return ; 
			}
			
			var formPara={
			    supData : param,
			    certIds : certIds ,
				inv_code : $("#inv_code").val(),
				inv_name : $("#inv_name").val(),
				alias : $("#alias").val(),
				mat_type_id : liger.get("mat_type_code").getValue() == null ? "" : liger.get("mat_type_code").getValue().split(",")[0],
				mat_type_no : liger.get("mat_type_code").getValue() == null ? "" : liger.get("mat_type_code").getValue().split(",")[1],
				price_type : liger.get("price_type").getValue() == null ? "" : liger.get("price_type").getValue(),
				amortize_type : liger.get("amortize_type").getValue() == null ? "" : liger.get("amortize_type").getValue(),
				inv_model : $("#inv_model").val(),
				unit_code : liger.get("unit_code").getValue() == null ? "" : liger.get("unit_code").getValue(),
				fac_id : liger.get("fac_code").getValue() == null ? "" : liger.get("fac_code").getValue().split(",")[0],
				fac_no : liger.get("fac_code").getValue() == null ? "" : liger.get("fac_code").getValue().split(",")[1],
				plan_price : $("#plan_price").val(),
				price_rate : $("#price_rate").val(),  	
				sell_price : $("#sell_price").val(),
				sdate : $("#sdate").val(),
				edate : $("#edate").val(),
				agent_name : $("#agent_name").val(),
				brand_name : $("#brand_name").val(),
				inv_usage : $("#inv_usage").val(),
				inv_structure : $("#inv_structure").val(),
				per_weight : $("#per_weight").val(),
				per_volum : $("#per_volum").val(),
				abc_type : liger.get("abc_type").getValue() == null ? "" : liger.get("abc_type").getValue(),
				manage_type : liger.get("manage_type").getValue(),
				is_single_ven : $("#is_single_ven").prop("checked") ? 1 : 0,
				stora_tran_cond : $("#stora_tran_cond").val(),
				use_state : liger.get("use_state").getValue() == null ? "" : liger.get("use_state").getValue(),
				is_bid : $("#is_bid").prop("checked") ? 1 : 0,
				bid_date : $("#bid_date").val(),
				bid_code : $("#bid_code").val(),
				memory_encoding : $("#memory_encoding").val(),
				source_plan : liger.get("source_plan").getValue() == null ? "" : liger.get("source_plan").getValue(),
				is_zero_store : $("#is_zero_store").prop("checked") ? 1 : 0,
				is_involved : $("#is_involved").prop("checked") ? 1 : 0,
				is_implant : $("#is_implant").prop("checked") ? 1 : 0,
								
				is_charge : $("#is_charge").prop("checked") ? 1 : 0,
				is_highvalue : $("#is_highvalue").prop("checked") ? 1 : 0,
				is_dura : $("#is_dura").prop("checked") ? 1 : 0,
				is_com : $("#is_com").prop("checked") ? 1 : 0,
				is_bar : $("#is_bar").prop("checked") ? 1 : 0,
				is_per_bar : $("#is_per_bar").prop("checked") ? 1 : 0,
				is_inv_bar : $("#is_inv_bar").prop("checked") ? 1 : 0,
				is_quality : $("#is_quality").prop("checked") ? 1 : 0,
				is_disinfect : $("#is_disinfect").prop("checked") ? 1 : 0,
				is_cert : $("#is_cert").prop("checked") ? 1 : 0,
				is_sec_whg : $("#is_sec_whg").prop("checked") ? 1 : 0,
				is_shel_make : $("#is_shel_make").prop("checked") ? 1 : 0, 
				//supData : JSON.stringify(gridManager.getData())
			};
			
			ajaxJsonObjectByUrl("addMatInv.do",formPara,function(responseData){
     	            if(responseData.state=="true"){
     	            parentFrameUse().query();
                    	 /* $.ligerDialog.confirm('是否继续添加?', function (yes){
                  		if(yes){
                  			if('${p04001 }' == 1){
         	        			$("#inv_code").val("自动生成");
         	        		}else{
         		            	$("#inv_code").val("");
         	        		}
         	    			$("#inv_name").val("");
         	    			$("#alias").val("");
         	    			$("#inv_model").val("");
         	    			$("#plan_price").val("");
         	    			$("#price_rate").val("");
         	    			$("#sell_price").val("");
         	    			$("#agent_name").val("");
         	    			$("#brand_name").val("");
         	    			$("#inv_usage").val("");
         	    			$("#inv_structure").val("");
         	    			$("#is_single_ven").prop("checked", false);
         	    			$("#is_charge").prop("checked", false);
         	    			$("#is_highvalue").prop("checked", false);
         	    			$("#is_dura").prop("checked", false);
         	    			$("#is_com").prop("checked", false);
         	    			$("#is_bar").prop("checked", false);
         	    			$("#is_per_bar").prop("checked", false);
         	    			$("#is_quality").prop("checked", false);
         	    			$("#is_disinfect").prop("checked", false);
         	    			$("#is_sec_whg").prop("checked", false);
         	    			$("#is_shel_make").prop("checked", false);
         	    			loadDict();
         	    			grid.loadData();
         	    			is_addRow();
                  		}else{ 
                  			parentFrameUse().update_open(responseData.update_para);
                  			this_close();
                  	 	}
                  	}); 
     	            }
     	        });*/
			
			
			
			//判断材料名称是否存在
			/*  ajaxJsonObjectByUrl("queryByUniqueness.do?isCheck=false",formPara,function(responseData){
  	            	if(responseData.info=="1"){
  	            		
  	            		$.ligerDialog.confirm('材料名称已经存在是否继续添加?', function (yes){
  	    	           		if(yes){
  	    	           			
  	    	           			 ajaxJsonObjectByUrl("addMatInv.do",formPara,function(responseData){
  	    	           	            if(responseData.state=="true"){
  	    	           	            parentFrameUse().query();
  	    	                          	  $.ligerDialog.confirm('是否继续添加?', function (yes){
  	    	                        		if(yes){
  	    	                        			if('${p04001 }' == 1){
  	    	               	        			$("#inv_code").val("自动生成");
  	    	               	        		}else{
  	    	               		            	$("#inv_code").val("");
  	    	               	        		}
  	    	               	    			$("#inv_name").val("");
  	    	               	    			$("#alias").val("");
  	    	               	    			$("#inv_model").val("");
  	    	               	    			$("#plan_price").val("");
  	    	               	    			$("#price_rate").val("");
  	    	               	    			$("#sell_price").val("");
  	    	               	    			$("#agent_name").val("");
  	    	               	    			$("#brand_name").val("");
  	    	               	    			$("#inv_usage").val("");
  	    	               	    			$("#inv_structure").val("");
  	    	               	    			$("#is_single_ven").prop("checked", false);
  	    	               	    			$("#is_charge").prop("checked", false);
  	    	               	    			$("#is_highvalue").prop("checked", false);
  	    	               	    			$("#is_dura").prop("checked", false);
  	    	               	    			$("#is_com").prop("checked", false);
  	    	               	    			$("#is_bar").prop("checked", false);
  	    	               	    			$("#is_per_bar").prop("checked", false);
  	    	               	    			$("#is_quality").prop("checked", false);
  	    	               	    			$("#is_disinfect").prop("checked", false);
  	    	               	    			$("#is_sec_whg").prop("checked", false);
  	    	               	    			$("#is_shel_make").prop("checked", false);
  	    	               	    			loadDict();
  	    	               	    			grid.loadData();
  	    	               	    			is_addRow();
  	    	                        		}else{ 
  	    	                        			parentFrameUse().update_open(responseData.update_para);
  	    	                        			this_close();
  	    	                        	 	}
  	    	                        	}); 
  	    	           	            }
  	    	           	        });
  	    	           		
  	    	           		}
  	    	  			});
  	            		
  	            	}else{
  	            		ajaxJsonObjectByUrl("addMatInv.do",formPara,function(responseData){
  	           	            if(responseData.state=="true"){
  	           	            parentFrameUse().query();
  	                          	  $.ligerDialog.confirm('是否继续添加?', function (yes){
  	                        		if(yes){
  	                        			if('${p04001 }' == 1){
  	               	        			$("#inv_code").val("自动生成");
  	               	        		}else{
  	               		            	$("#inv_code").val("");
  	               	        		}
  	               	    			$("#inv_name").val("");
  	               	    			$("#alias").val("");
  	               	    			$("#inv_model").val("");
  	               	    			$("#plan_price").val("");
  	               	    			$("#price_rate").val("");
  	               	    			$("#sell_price").val("");
  	               	    			$("#agent_name").val("");
  	               	    			$("#brand_name").val("");
  	               	    			$("#inv_usage").val("");
  	               	    			$("#inv_structure").val("");
  	               	    			$("#is_single_ven").prop("checked", false);
  	               	    			$("#is_charge").prop("checked", false);
  	               	    			$("#is_highvalue").prop("checked", false);
  	               	    			$("#is_dura").prop("checked", false);
  	               	    			$("#is_com").prop("checked", false);
  	               	    			$("#is_bar").prop("checked", false);
  	               	    			$("#is_per_bar").prop("checked", false);
  	               	    			$("#is_quality").prop("checked", false);
  	               	    			$("#is_disinfect").prop("checked", false);
  	               	    			$("#is_sec_whg").prop("checked", false);
  	               	    			$("#is_shel_make").prop("checked", false);
  	               	    			loadDict();
  	               	    			grid.loadData();
  	               	    			is_addRow();
  	                        		}else{ 
  	                        			parentFrameUse().update_open(responseData.update_para);
  	                        			this_close();
  	                        	 	}
  	                        	}); 
  	           	            }
  	           	        });
  	            	}
  	            }); 
        }
    }
      */
     //加载表单
	function loadForm(){
	    
		$.metadata.setType("attr", "validate");
		var v = $("form").validate({
			errorPlacement: function (lable, element){
				if (element.hasClass("l-textarea")){
	                 element.ligerTip({ content: lable.html(), target: element[0] }); 
	             }else if (element.hasClass("l-text-field")){
	                 element.parent().ligerTip({ content: lable.html(), target: element[0] });
	             }else{
	                 lable.appendTo(element.parents("td:first").next("td"));
	             }
	         },
	         success: function (lable){
	             lable.ligerHideTip();
	             lable.remove();
	         },
	         submitHandler: function (){
	             $("form .l-text,.l-textarea").ligerHideTip();
	         }
	     });
	     //$("form").ligerForm();
	}       
	
	function loadDict(){
		//字典下拉框
		//autocomplete("#mat_type_code", "../../../queryMatTypeDictByWrite.do?isCheck=false", "id", "text", true, true, {is_last : '1'});
		autocomplete("#mat_type_code", "../../../queryMatTypeDictDate.do?isCheck=false", "id", "text", true, true, {is_last : '1',is_write:'1'},false,'','300');
		autocomplete("#instru_type_id", "../../../queryMatInstruType.do?isCheck=false", "id", "text", true, true, false,false,'','160');

		var mat_type_id = '';
		var mat_type_text = '';
		var selectObj = parentFrameUse().liger.get('tree') ? parentFrameUse().liger.get('tree').getSelected() : "";
		if(selectObj){
			//var selectObj = parentFrameUse().liger.get('tree').getSelected();
			//var mat_type_ids = parentFrameUse().mat_type_id.split(",");
			//mat_type_id = mat_type_ids[0] +","+ mat_type_ids[1]; 
			mat_type_text = selectObj.data.text;
			mat_type_id = selectObj.data.id+','+selectObj.data.no;
			
		}
		
		
		liger.get("mat_type_code").setValue(mat_type_id);
		liger.get("mat_type_code").setText(mat_type_text);
		var paras = {
			field_code : "price_type"
		}
		autocomplete("#price_type", "../../../queryMatSysList.do?isCheck=false", "id", "text", true, true, paras, true);
		paras = {
			field_code : "amortize_type"
		}
		autocomplete("#amortize_type", "../../../queryMatSysList.do?isCheck=false", "id", "text", true, true, paras, true);
		autocomplete("#unit_code", "../../../queryHosUnit.do?isCheck=false", "id", "text", true, true, "", false);
		autocomplete("#fac_code", "../../../queryHosFacDict.do?isCheck=false", "id", "text", true, true);
		autocomplete("#manage_type", "../../../queryMatManageType.do?isCheck=false", "id", "text", true, true);
        $("#use_state").ligerComboBox({width:160, disabled:true, cancelable: false});
        
        if('${p04080 }' == '0'){
	        liger.get("use_state").setValue("1");
			liger.get("use_state").setText("是");
        }
        if('${p04080 }' == '1'){
        	liger.get("use_state").setValue("0");
    		liger.get("use_state").setText("否");
        }
		/*
        
		autocomplete("#use_state", "../../../queryMatYearOrNo.do?isCheck=false", "id", "text", true, true, "", false, "2");
		$("#use_state").ligerComboBox({disabled:true,cancelable: false});*/
		autoCompleteByData("#abc_type", matInv_ABCType.Rows, "id", "text", true, true, "", true);
		autoCompleteByData("#source_plan", matInv_sourcePlan.Rows, "id", "text", true, true, "", true);
	
		//渲染效果
		$("#inv_code").ligerTextBox({width:160}); 
		$("#mat_type_code").ligerTextBox({width:160}); 
		$("#inv_name").ligerTextBox({width:160});
		$("#bar_code_new").ligerTextBox({width:160});
		$("#alias").ligerTextBox({width:160});
		$("#inv_model").ligerTextBox({width:160});
		$("#plan_price").ligerTextBox({width:160, number:true, precision:'${p04006 }'});
		$("#plan_price").focus(function(){this.select();});//加获取焦点选择文本事件
		$("#price_rate").ligerTextBox({width:160, number:true});
		$("#sell_price").ligerTextBox({width:160, number:true, precision:'${p04072 }'});
		$("#sell_price").focus(function(){this.select();});//加获取焦点选择文本事件
		$("#agent_name").ligerTextBox({width:160});
		$("#brand_name").ligerTextBox({width:160});
		$("#inv_usage").ligerTextBox({width:160});
		$("#inv_structure").ligerTextBox({width:160});
		$("#sdate").ligerTextBox({width:160});
		$("#edate").ligerTextBox({width:160});
		$("#per_weight").ligerTextBox({width:160});
		$("#per_volum").ligerTextBox({width:160});
		$("#bid_date").ligerTextBox({width:160});
		$("#bid_code").ligerTextBox({width:160});
		$("#memory_encoding").ligerTextBox({width:160});
		$("#stora_tran_cond").ligerTextBox({width:160});
		$("#charge_code").ligerTextBox({width:160});

	}
	
	function loadButton(){

    	$("#save").ligerButton({click: save, width:90});
		$("#close").ligerButton({click: this_close, width:90});
	}
	
	function getSellPrice(){
		if($("#plan_price").val() && $("#price_rate").val()){
			var sell_price =$("#plan_price").val() * (1+parseFloat($("#price_rate").val()));
			var sellPrice = sell_price.toFixed('${p04072 }');
			$("#sell_price").val(sellPrice); 
		}
	}
	
	//改变启用日期事件
	function change_eDate(){
		
		var v1 = new Date($("#edate").val().replace(/-/g, "/"));//停用日期
	    var v2 = new Date(getDateAddDay(new Date(), 0).replace(/-/g, "/"));//当前日期
 		var v3 = new Date($("#sdate").val().replace(/-/g, "/"));//启用日期
 		
 		//1.启用、停用为空
 		if(v1 == 'Invalid Date' && v3 == 'Invalid Date'){
 			liger.get("use_state").setValue("1");
	    	liger.get("use_state").setText("是");
	    	return ; 
 		}
 		
 		//2.启用不为空、停用为空
 		if(v3 != 'Invalid Date' && v1 == 'Invalid Date'){
 			
 			if(v3 <= v2){//如果启用日期小于当前日期
 				liger.get("use_state").setValue("1");
 		    	liger.get("use_state").setText("是");
 		    	return ;
 			}else{//大于
 				liger.get("use_state").setValue("0");
 				liger.get("use_state").setText("否");
 				return ; 
 			}
 		}
 		
 		//3.停用不为空、启用为空
		if(v1 != 'Invalid Date' && v3 == 'Invalid Date'){
 			
 			if(v1 > v2){//如果停用大于当前
 				liger.get("use_state").setValue("1");
 		    	liger.get("use_state").setText("是");
 		    	return ;
 			}else{//小于等于当前
 				liger.get("use_state").setValue("0");
 				liger.get("use_state").setText("否");
 				return ; 
 			}
 		}
	    
		//4.启用停用都不为空
	    if(v1 != 'Invalid Date' && v3 != 'Invalid Date'){
	    	
	    	if(v3 >= v1){ //启用大于等于停用
		    	$.ligerDialog.warn('启用日期不能大于等于停用日期');
		    	return ;
		    }
		    
		    if(v3 > v2 || v1 <= v2){
		    	liger.get("use_state").setValue("0");
 				liger.get("use_state").setText("否");
 				return ; 
		    }else{
		    	liger.get("use_state").setValue("1");
 				liger.get("use_state").setText("是");
 				return ; 
		    }
	    }
	}
	
	//改变停用日期事件
	function changeDate(){
		
		var v1 = new Date($("#edate").val().replace(/-/g, "/"));//停用日期
	    var v2 = new Date(getDateAddDay(new Date(), 0).replace(/-/g, "/"));//当前日期
 		var v3 = new Date($("#sdate").val().replace(/-/g, "/"));//启用日期
 		
 		//1.启用、停用为空
 		if(v1 == 'Invalid Date' && v3 == 'Invalid Date'){
 			liger.get("use_state").setValue("1");
	    	liger.get("use_state").setText("是");
	    	return ; 
 		}
 		
 		//2.启用不为空、停用为空
 		if(v3 != 'Invalid Date' && v1 == 'Invalid Date'){
 			
 			if(v3 <= v2){//如果启用日期小于当前日期
 				liger.get("use_state").setValue("1");
 		    	liger.get("use_state").setText("是");
 		    	return ;
 			}else{//大于
 				liger.get("use_state").setValue("0");
 				liger.get("use_state").setText("否");
 				return ; 
 			}
 		}
 		
 		//3.停用不为空、启用为空
		if(v1 != 'Invalid Date' && v3 == 'Invalid Date'){
 			
 			if(v1 > v2){//如果停用大于当前
 				liger.get("use_state").setValue("1");
 		    	liger.get("use_state").setText("是");
 		    	return ;
 			}else{//小于等于当前
 				liger.get("use_state").setValue("0");
 				liger.get("use_state").setText("否");
 				return ; 
 			}
 		}
	    
		//4.启用停用都不为空
	    if(v1 != 'Invalid Date' && v3 != 'Invalid Date'){
	    	
	    	if(v1 <= v3){ //启用大于等于停用
		    	$.ligerDialog.warn('停用日期不能小于等于启用日期');
		    	return ;
		    }
		    
		    if(v3 > v2 || v1 <= v2){
		    	liger.get("use_state").setValue("0");
 				liger.get("use_state").setText("否");
 				return ; 
		    }else{
		    	liger.get("use_state").setValue("1");
 				liger.get("use_state").setText("是");
 				return ; 
		    }
	    }
	}
	//手动校验数据
    function validateGrid() {  
 		//主表
 		if($("#inv_name").val() == ""){
 			$.ligerDialog.warn("材料名称不能为空");  
 			return false;  
 		}
 		if(liger.get("mat_type_code").getValue() == null || liger.get("mat_type_code").getValue() == ""){
 			$.ligerDialog.warn("材料类别不能为空");  
 			return false;  
 		}  
 		if(liger.get("price_type").getValue() == null || liger.get("price_type").getValue() == ""){
 			$.ligerDialog.warn("计价方法不能为空");  
 			return false;  
 		}  
 		if(liger.get("amortize_type").getValue() == null || liger.get("amortize_type").getValue() == ""){
 			$.ligerDialog.warn("摊销方式不能为空");  
 			return false;  
 		}  
 		if($("#inv_model").val() == ""){
 			$.ligerDialog.warn("规格型号不能为空");  
 			return false;  
 		}  
 		if(liger.get("unit_code").getValue() == null || liger.get("unit_code").getValue() == ""){
 			$.ligerDialog.warn("计量单位不能为空");  
 			return false;  
 		}  
 		 	
    }
	
	//键盘事件
	function loadHotkeys() {
		hotkeys('Esc', this_close);
	 }
	
	//关闭当前页面
	function this_close(){
		frameElement.dialog.close();
	}
	
	function openUpdate(){
		parent.$.ligerDialog.open({
			url: 'hrp/mat/info/basic/inv/matInvCertInfoAddPage.do?isCheck=false', 
			height: 300,width:950, title:'材料证件对应关系',
         	modal:true, showToggle:false, showMax:true ,showMin:false, isResize:true,
         	parentframename: window.name,
       		buttons: [ 
				{ text: '保存', onclick: function(item, dialog){dialog.frame.save();}, cls:'l-dialog-btn-highlight' },
				{ text: '关闭', onclick: function (item, dialog) { dialog.close(); } } 
			] 
		});
	}
</script>
</head>
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" style="padding: 10px"  >
	        <tr>
	            <td align="right" class="l-table-edit-td" width="10%">
	            	<span style="color:red">*</span>材料编码：
	            </td>
	            <td align="left" class="l-table-edit-td" width="20%">
	            	<input name="inv_code" type="text" id="inv_code" ltype="text" onkeyup="this.value=this.value.replace(/^ +| +$/g,'')" validate="{required:true,maxlength:20}" />
	            </td>
	            <td align="right" class="l-table-edit-td"  width="10%">
	            	<span style="color:red">*</span>材料名称：
	            </td>
	            <td align="left" class="l-table-edit-td" width="20%">
	            	<input name="inv_name" type="text" id="inv_name" ltype="text"  validate="{required:true,maxlength:50}" />
	            </td>
	            <td align="right" class="l-table-edit-td" width="10%">
	            	别名：
	            </td>
	            <td align="left" class="l-table-edit-td" width="20%">
	            	<input name="alias" type="text"  id="alias" ltype="text"  validate="{required:false,maxlength:50}" />
	            </td>
	        </tr> 
	        <tr>
	            <td align="right" class="l-table-edit-td"  >
	            	<span style="color:red">*</span>物资类别：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="mat_type_code" type="text" id="mat_type_code" ltype="text"   validate="{required:true}" />
	            </td>
	            <td align="right" class="l-table-edit-td" >
	            	<span style="color:red">*</span>计价方法：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="price_type" type="text" id="price_type" ltype="text"  validate="{required:true}" />
	            </td>
	            <td align="right" class="l-table-edit-td"  >
	            	<span style="color:red">*</span>摊销方式：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="amortize_type" type="text" id="amortize_type" ltype="text"  validate="{required:true}" />
	            </td>
			</tr>
			<tr>
	            <td align="right" class="l-table-edit-td"  >
	            	<span style="color:red">*</span>规格型号：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="inv_model" type="text" id="inv_model" ltype="text"  validate="{required:true,maxlength:200}" />
	            </td>
	            <td align="right" class="l-table-edit-td"  >
	            	<span style="color:red">*</span>计量单位：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="unit_code" type="text" id="unit_code" ltype="text" validate="{required:true}" />
	            </td>
	             <td align="right" class="l-table-edit-td"  >
	            	生产厂商：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="fac_code" type="text" id="fac_code" ltype="text"  validate="{required:false}" />
	            </td>
	        </tr> 
	        <tr>
	            <td align="right" class="l-table-edit-td"  >
	            	计划价：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="plan_price" type="text" id="plan_price"  value="0" ltype="text" validate="{required:false,maxlength:20}" onchange="getSellPrice()"/>
	            </td>
	            <td align="right" class="l-table-edit-td"  >
	            	加价率：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="price_rate" type="text" id="price_rate" ltype="text" validate="{required:false,maxlength:20}" onchange="getSellPrice()"/>
	            </td>
	            <td align="right" class="l-table-edit-td"  >
	            	零售价：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="sell_price" type="text" id="sell_price"  value="0" ltype="text" validate="{required:false,maxlength:20}" />
	            </td>
	        </tr> 
	        <tr>
	            <td align="right" class="l-table-edit-td"  >
	        		启用日期：
	        	</td>
	            <td align="left" class="l-table-edit-td">
	            	<input class="Wdate" name="sdate" id="sdate" type="text" onchange="change_eDate()" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
	            </td>
	            <td align="right" class="l-table-edit-td"  >
	            	停用日期：
	            </td>
	            <td align="left" class="l-table-edit-td">
		            <input class="Wdate" name="edate" id="edate" type="text" onchange="changeDate()" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
	            </td>
	            <td align="right" class="l-table-edit-td"  >
	            	代理商：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="agent_name" type="text" id="agent_name" ltype="text" validate="{required:false,maxlength:60}" />
	            </td>
	        </tr>
	        <tr>
	            <td align="right" class="l-table-edit-td"  >
	            	品牌名称：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="brand_name" type="text" id="brand_name" ltype="text" validate="{required:false,maxlength:60}" />
	            </td>
	            <td align="right" class="l-table-edit-td"  >
					材料用途：
				</td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="inv_usage" type="text" id="inv_usage" ltype="text" validate="{required:false,maxlength:60}" />
	            </td>
	            <td align="right" class="l-table-edit-td"  >
	            	包装规格：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="inv_structure" type="text" id="inv_structure" ltype="text" validate="{required:false,maxlength:60}" />
	            </td>
	        </tr> 
	        <tr>
	            <td align="right" class="l-table-edit-td"  >
	            	单位重量：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="per_weight" type="text" id="per_weight" ltype="text" validate="{required:false,maxlength:20}" />
	            </td>
	             <td align="right" class="l-table-edit-td"  >
	            	单位体积：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="per_volum" type="text" id="per_volum" ltype="text" validate="{required:false,maxlength:20}" />
	            </td>
	             <td align="right" class="l-table-edit-td"  >
	            	ABC分类：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="abc_type" type="text" id="abc_type" ltype="text" validate="{required:false}" />
	            </td>
	        </tr> 
	        <tr>
				<td align="right" class="l-table-edit-td"  >
	            	管理类别：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="manage_type" type="text" id="manage_type" ltype="text" validate="{required:false}" />
	            </td>
	            <td align="right" class="l-table-edit-td"  >
	            	在用状态：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="use_state" type="text" id="use_state" ltype="text" validate="{required:false}" />
	            </td> 
	            <td align="right" class="l-table-edit-td"  >
	            	储运条件：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="stora_tran_cond" type="text" id="stora_tran_cond" ltype="text" validate="{required:false,maxlength:50}" />
	            </td>
	        </tr>
	        <tr>
	           <td align="right" class="l-table-edit-td"  >
	            	<input name="is_bid" type="checkbox" id="is_bid" ltype="text" validate="{required:false}" />
	            </td>
	            <td align="left" class="l-table-edit-td" >
	            	是否中标
	            </td>
	            <td align="right" class="l-table-edit-td"  >
	            	中标日期：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input class="Wdate" name="bid_date" id="bid_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
	            </td> 
	            <td align="right" class="l-table-edit-td"  >
	            	项目中标编码：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="bid_code" type="text" id="bid_code" ltype="text" validate="{required:false,maxlength:50}" />
	            </td>
	        </tr>
    
    
	        <tr>
	            <td align="right" class="l-table-edit-td"  >
	            	<input name="is_single_ven" type="checkbox" id="is_single_ven" ltype="text" validate="{required:false}" />
	            </td>
	            <td align="left" class="l-table-edit-td" >
	            	是否唯一供应商
	            </td>
            	<td align="right" class="l-table-edit-td"  >
	            	存储编码：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="memory_encoding" type="text" id="memory_encoding" ltype="text" validate="{required:false,maxlength:50}" />
	            </td>
                <td align="right" class="l-table-edit-td"  >
	            	计划来源：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="source_plan" type="text" id="source_plan" ltype="text"  validate="{required:false}" />
	            </td>
	        </tr>
	        <tr>
	           <td align="right" class="l-table-edit-td"  >
	            	<input name="is_implant" type="checkbox" id="is_implant" ltype="text" validate="{required:false}" />
	            </td>
	            <td align="left" class="l-table-edit-td" >
	            	植入标识
	            </td>
	            <td align="right" class="l-table-edit-td"  >
	            	<input name="is_zero_store" type="checkbox" id="is_zero_store" ltype="text" validate="{required:false}" />
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	零库存管理标识
	            </td>
	            <td align="right" class="l-table-edit-td"  >
	            	<input name="is_involved" type="checkbox" id="is_involved" ltype="text" validate="{required:false}" />介入标识
	            </td>
<!-- 	            <td align="center" class="l-table-edit-td" ><a href=javascript:openUpdate();>证件信息</a></td> -->
	            
			</tr>
			<tr>
			   	<td align="right" class="l-table-edit-td"  width="10%">
	            	<span style="color:red"></span>品种码：
	            </td>
	            <td align="left" class="l-table-edit-td" width="20%">
	            	<input name="bar_code_new" type="text" id="bar_code_new" ltype="text" />
	            </td>
	            <td align="right" class="l-table-edit-td"  width="10%">
	            	<span style="color:red"></span>医疗器械分类：
	            </td>
	            <td align="left" class="l-table-edit-td" width="20%">
	            	<input name="instru_type_id" type="text" id="instru_type_id" ltype="text" />
	            </td>
			   	<td align="right" class="l-table-edit-td"  width="10%">
	            	<span style="color:red"></span>医保收费代码：
	            </td>
	            <td align="left" class="l-table-edit-td" width="20%">
	            	<input name="charge_code" type="text" id="charge_code" ltype="text" />
	            </td>
			</tr>
	        <tr>
	        	<td class="l-table-edit-td" colspan="6">
	        	<!-- 长表格显示 -->
	        		<table cellpadding="0" cellspacing="0" style="margin-top: 20px;" class="l-table-edit"  width="100%"  border="1px">
	        			<tr height="30px" style="background: url(../../../../../lib/ligerUI/skins/Aqua/images/grid/header-bg.gif) repeat;);">
	        				<td align="center" width="6%">收费标识</td>
	        				<td align="center" width="6%">高值标识</td>
	        				<td align="center" width="8%">耐用品标识</td>
	        				<td align="center" width="6%">代销标识</td>
	        				<td align="center" width="8%">条码管理标识</td>
	        				<td align="center" width="8%">个体码管理标识</td>
	        				<td align="center" width="6%">品种码标识</td>
	        				<td align="center" width="10%">保质期管理标识</td>
	        				<td align="center" width="10%">灭菌材料标识</td>
	        				<td align="center" width="10%">证件管理标识</td>
	        				<td align="center" width="10%">冷链管理标识</td>
	        				<td align="center" width="8%">自制品标识</td>
	        			</tr>
	        			<tr>
	        				<td align="center" class="l-table-edit-td">
	        					<input name="is_charge" type="checkbox" id="is_charge" ltype="text" />
	        				</td>
	        				<td align="center" class="l-table-edit-td">
	        					<input name="is_highvalue" type="checkbox" id="is_highvalue" ltype="text" />
	        				</td>
	        				<td align="center" class="l-table-edit-td">
	        					<input name="is_dura" type="checkbox" id="is_dura" ltype="text" />
	      					</td>
	        				<td align="center" class="l-table-edit-td">
	        					<input name="is_com" type="checkbox" id="is_com" ltype="text" />
	        				</td>
	        				<td align="center" class="l-table-edit-td">
	        					<input name="is_bar" type="checkbox" id="is_bar" ltype="text" value="1" />
	        				</td>
	        				<td align="center" class="l-table-edit-td">
	        					<input name="is_per_bar" type="checkbox" id="is_per_bar" ltype="text" value="1" />
	        				</td>
	        				<td align="center" class="l-table-edit-td">
	        					<input name="is_inv_bar" type="checkbox" id="is_inv_bar" ltype="text" />
	        				</td>
	        				<td align="center" class="l-table-edit-td">
	        					<input name="is_quality" type="checkbox" id="is_quality" ltype="text" value="1" />
	        				</td>
	        					<td align="center" class="l-table-edit-td">
	        					<input name="is_disinfect" type="checkbox" id="is_disinfect" ltype="text" value="1" />
	        				</td>
	        				<td align="center" class="l-table-edit-td">
	        					<input name="is_cert" type="checkbox" id="is_cert" ltype="text" />
	        				</td>
	        				<td align="center" class="l-table-edit-td">
	        					<input name="is_sec_whg" type="checkbox" id="is_sec_whg" ltype="text" value="1" />
	        				</td>
	        				<td align="center" class="l-table-edit-td">
	        					<input name="is_shel_make" type="checkbox" id="is_shel_make" ltype="text" value="1" />
	        				</td>
	        			</tr>
	        		</table>
	        	</td>
	        </tr>
	        <tr>
                <td align="center" class="l-table-edit-td" colspan="6">
                    <button id="save" accessKey="B"><b>保存（<u>B</u>）</b></button>
                    &nbsp;&nbsp;
                    <button id="close" accessKey="C"><b>关闭（<u>C</u>）</b></button>
                </td>
            </tr>
	        <tr>
	        	<td colspan="6" align="left" class="l-table-edit-td" >
	        		>>材料供应商明细
	        	</td>
	        </tr>
	        <tr>
	        	<td colspan="6" class="l-table-edit-td" >
	        	
	        		<div id="maingrid"></div>
	        	</td>
	        </tr>
	    </table>
    </form>
    </body>
</html>
