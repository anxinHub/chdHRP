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
	<script src="<%=path%>/lib/hrp/med/med.js"	type="text/javascript"></script>
    <script type="text/javascript">
    var dataFormat;
    
    var certIds ;
    $(function (){
        loadDict();
        loadForm();
		if("${medInv.is_single_ven}" == 1){
			$("#is_single_ven").prop("checked", true);
		}
		if("${medInv.is_bid}" == 1){
			$("#is_bid").prop("checked", true);
		}
		if("${medInv.is_zero_store}" == 1){
			$("#is_zero_store").prop("checked", true);
		}
		if("${medInv.is_involved}" == 1){
			$("#is_involved").prop("checked", true);
		}
		if("${medInv.is_implant}" == 1){
			$("#is_implant").prop("checked", true);
		}
		if("${medInv.is_charge}" == 1){
			$("#is_charge").prop("checked", true);
		}
		if("${medInv.is_highvalue}" == 1){
			$("#is_highvalue").prop("checked", true);
		}
		if("${medInv.is_dura}" == 1){
			$("#is_dura").prop("checked", true);
		}
		if("${medInv.is_com}" == 1){
			$("#is_com").prop("checked", true);
		}
		if("${medInv.is_bar}" == 1){
			$("#is_bar").prop("checked", true);
		}
		if("${medInv.is_per_bar}" == 1){
			$("#is_per_bar").prop("checked", true);
		}
		if("${medInv.is_inv_bar}" == 1){
			$("#is_inv_bar").prop("checked", true);
		}
		if("${medInv.is_quality}" == 1){
			$("#is_quality").prop("checked", true);
		}
		if("${medInv.is_disinfect}" == 1){
			$("#is_disinfect").prop("checked", true);
		}
		if("${medInv.is_cert}" == 1){
			$("#is_cert").prop("checked", true);
		}
		if("${medInv.is_sec_whg}" == 1){
			$("#is_sec_whg").prop("checked", true);
		}
		if("${medInv.is_shel_make}" == 1){
			$("#is_shel_make").prop("checked", true);
		}
		
        loadHead(null);//加载供应商数据
		querySup();
    });
	
	function querySup(){
		grid.options.parms=[];
		grid.options.newPage=1;
		
		
        //根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'group_id',
			value : '${medInv.group_id}'
		});
		grid.options.parms.push({
			name : 'hos_id',
			value : '${medInv.hos_id}'
		});
		grid.options.parms.push({
			name : 'copy_code',
			value : '${medInv.copy_code}'
		});
		grid.options.parms.push({
			name : 'inv_id',
			value : '${medInv.inv_id}'
		});		
		
		
		setTimeout("grid.loadData(grid.where); ",1000);
    	//加载查询条件
    	
	}
    
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
						}],
						switchPageSizeApplyComboBox : false,
						onSelectRow : f_onSelectRow_detail,
						url : 'queryMedInvSupListDisable.do?isCheck=false',
						//delayLoad:true,
						usePager: true,
						pageSize : 5
					},
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
					if(value == 1){
						return "<input name='is_default"+rowindex+"' checked='checked' type='checkbox' id='is_default"+rowindex+"' ltype='text'"
							+" style='margin-top:5px;' />";
					}
					return "<input name='is_default"+rowindex+"' type='checkbox' id='is_default"+rowindex+"' ltype='text'"
						+" style='margin-top:5px;' />";
				}
			} ],
			dataAction: 'server',dataType: 'server',usePager:false,
			url: "queryMedInvSup.do?isCheck=false",
			async:false,
			width: '98%', height: '200', checkbox: true,rownumbers:true,
			enabledEdit : true,alternatingRow : true,onBeforeEdit : f_onBeforeEdit,
			onBeforeSubmitEdit : f_onBeforeSubmitEdit,onAfterEdit : f_onAfterEdit,
			//isScroll : false,
			isAddRow:false,
			selectRowButtonOnly:true,//heightDiff: -10,
			toolbar: { items: [
				{ text: '删除', id:'delete', click: deleteRow,icon:'delete' },{ line:true },
				{ text: '添加行', id:'add', click: addCenterRow, icon:'add' },{ line:true },
				{ text: '保存', id:'save', click: saveSup, icon:'save' },{ line:true }
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
	 		
	//保存数据
	function saveSup() {
		grid.endEdit();
		
		validateGrid();
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
		
		var formPara = {
	        group_id : $("#group_id").val(),
	        hos_id : $("#hos_id").val(),
	        copy_code : $("#copy_code").val(),
			inv_id : $("#inv_id").val(),
			supData : param
	 	};
	 	ajaxJsonObjectByUrl("addMedInvSup.do?isCheck=false", formPara,function(responseData) {
			if (responseData.state == "true") {
				querySup();
	 		}
		});
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
	
    function save(){
    	grid.endEdit();	
        if($("form").valid()){
        	validateGrid();
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
    		if(d.length != 0 ){
    			
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
	        	group_id : $("#group_id").val(),
	        	hos_id : $("#hos_id").val(),
	        	copy_code : $("#copy_code").val(),
	            inv_no : $("#inv_no").val(),
	            inv_id : $("#inv_id").val(),
				inv_code : $("#inv_code").val(),
				inv_name : $("#inv_name").val(),
				alias : $("#alias").val(),
				med_type_id : liger.get("med_type_code").getValue() == null ? "" : liger.get("med_type_code").getValue().split(",")[0],
				med_type_no : liger.get("med_type_code").getValue() == null ? "" : liger.get("med_type_code").getValue().split(",")[1],
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
				abc_type : liger.get("abc_type").getValue() == null ? "" : liger.get("abc_type").getValue().split(",")[0],
				manage_type : liger.get("manage_type").getValue(),
				is_single_ven : $("#is_single_ven").prop("checked") ? 1 : 0,
				stora_tran_cond : $("#stora_tran_cond").val(),
				use_state :liger.get("use_state").getValue() == null ? "" : liger.get("use_state").getValue(),
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
				supData : param,
				certIds : certIds ,
				old_is_bar : $("#old_is_bar").val(), 
				old_is_com : $("#old_is_com").val(), 
	        	is_change : $("#is_change").prop("checked") ? 1 : 0,
	    		change_note : $("#change_note").val(),
				med_sx_id : liger.get("med_sx_id").getValue() == null ? "" : liger.get("med_sx_id").getValue(),
				med_jx_id : liger.get("med_jx_id").getValue() == null ? "" : liger.get("med_jx_id").getValue(),
				bqplxs: $("#bqplxs").val(),
				mzplxs: $("#mzplxs").val()
	    		
	    		
	        };
	        ajaxJsonObjectByUrl("updateMedInv.do",formPara,function(responseData){
	            
	            if(responseData.state=="true"){
	            	parentFrameUse().query();
	            }
	        });
        }
    }
     
    function loadForm(){
    
    $.metadata.setType("attr", "validate");
     var v = $("form").validate({
         errorPlacement: function (lable, element)
         {
             if (element.hasClass("l-textarea"))
             {
                 element.ligerTip({ content: lable.html(), target: element[0] }); 
             }
             else if (element.hasClass("l-text-field"))
             {
                 element.parent().ligerTip({ content: lable.html(), target: element[0] });
             }
             else
             {
                 lable.appendTo(element.parents("td:first").next("td"));
             }
         },
         success: function (lable)
         {
             lable.ligerHideTip();
             lable.remove();
         },
         submitHandler: function ()
         {
             $("form .l-text,.l-textarea").ligerHideTip();
         }
     });
     //$("form").ligerForm();
    }   
    
    function loadDict(){
    	//字典下拉框
		autocomplete("#med_type_code", "../../../queryMedTypeDictByWrite.do?isCheck=false", "id", "text", true, true, {is_last : '1'});
		
    	
		liger.get("med_type_code").setValue("${medInv.med_type_id},${medInv.med_type_no}");
		liger.get("med_type_code").setText("${medInv.med_type_name}");
    	
    	var paras = {
			field_code : "price_type"
		}
		autocomplete("#price_type", "../../../queryMedSysList.do?isCheck=false", "id", "text", true, true, paras, false, "${medInv.price_type}");
		paras = {
			field_code : "amortize_type"
		}
		autocomplete("#amortize_type", "../../../queryMedSysList.do?isCheck=false", "id", "text", true, true, paras, false, "${medInv.amortize_type}");
		autocomplete("#unit_code", "../../../queryHosUnit.do?isCheck=false", "id", "text", true, true, "", false);
		liger.get("unit_code").setValue("${medInv.unit_code}");
		liger.get("unit_code").setText("${medInv.unit_name}");
    	
		
		autocomplete("#fac_code", "../../../queryHosFacDict.do?isCheck=false", "id", "text", true, true, "", false);

		
		liger.get("fac_code").setValue("${medInv.fac_id},${medInv.fac_no}");
		liger.get("fac_code").setText("${medInv.fac_name}");
        $("#use_state").ligerComboBox({width:160, disabled:true, cancelable: false});
        liger.get("use_state").setValue("${medInv.use_state}");
		liger.get("use_state").setText("${medInv.use_state}" == 1 ? "是" : "否");
		//autocomplete("#use_state", "../../../queryMedYearOrNo.do?isCheck=false", "id", "text", true, true, "", false, "${medInv.use_state}");
		autoCompleteByData("#abc_type", medInv_ABCType.Rows, "id", "text", true, true, "", false, "${medInv.abc_type}");
		autocomplete("#manage_type", "../../../queryMedManageType.do?isCheck=false", "id", "text", true, true, "", false, "${medInv.manage_type}");
		autoCompleteByData("#source_plan", medInv_sourcePlan.Rows, "id", "text", true, true, "", false, "${medInv.source_plan}");
		//药品属性
		autocomplete("#med_sx_id", "../../../queryMedSx.do?isCheck=false", "id", "text", true, true, "", false);
		liger.get("med_sx_id").setValue("${medInv.med_sx_id}");
		liger.get("med_sx_id").setText("${medInv.med_sx_name}");
    	
		//药品剂型
		autocomplete("#med_jx_id", "../../../queryMedJx.do?isCheck=false", "id", "text", true, true, "", false);
		liger.get("med_jx_id").setValue("${medInv.med_jx_id}");
		liger.get("med_jx_id").setText("${medInv.med_jx_name}");
		
		$("#inv_code").ligerTextBox({width:160,disabled:true});
		$("#inv_name").ligerTextBox({width:160});
		$("#alias").ligerTextBox({width:160});
		$("#inv_model").ligerTextBox({width:160});
		$("#plan_price").ligerTextBox({width:160, number:true, precision:'${p08006 }'});
		$("#plan_price").focus(function(){this.select();});//加获取焦点选择文本事件
		$("#price_rate").ligerTextBox({width:160, number:true});
		$("#sell_price").ligerTextBox({width:160, number:true, precision:'${p08006 }'});
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
		$("#change_note").ligerTextBox({width:320});
		
		$("#bqplxs").ligerTextBox({width:160});
		$("#mzplxs").ligerTextBox({width:160});
		
		changeMedTypeCode();
	}
    
    //改变药品类别数值触发改变药品编码事件(根据最大编码号累加，而非总数累加)
	function changeMedTypeCode(){
    	var inv_code = $("#inv_code").val();
		var type_code = liger.get("med_type_code").getValue().split(",")[0];
		$("#med_type_code").bind("change",function() {
			var paramVo = {
				med_type_id:liger.get("med_type_code").getValue().split(",")[0]	
			};
			if(paramVo.med_type_id == type_code){
				liger.get("inv_code").setValue(inv_code);
			}else{
				ajaxJsonObjectByUrl("queryChangeMedTypeCode.do?isCheck=false",paramVo,function(responseData){
		            if(responseData != null ){
		            	//该类别存在药品最大数
		            	var num = responseData.Rows[0].INV_CODE;
		            	//该类别存在药品总数
		            	var sum = responseData.Rows[0].INV_COUNT;
		            	num++;
		            	liger.get("inv_code").setValue("0"+num);
		            }
		        });
			}
		});
	}
    
	function getSellPrice(){
		if($("#plan_price").val() && $("#price_rate").val()){
			$("#sell_price").val($("#plan_price").val() * $("#price_rate").val()); 
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
 			$.ligerDialog.warn("药品名称不能为空");  
 			return false;  
 		}
 		if(liger.get("med_type_code").getValue() == null || liger.get("med_type_code").getValue() == ""){
 			$.ligerDialog.warn("药品类别不能为空");  
 			return false;  
 		}  
 		if(liger.get("price_type").getValue() == null || liger.get("price_type").getValue() == ""){
 			$.ligerDialog.warn("计价方法不能为空");  
 			return false;  
 		}  
 		/* if(liger.get("amortize_type").getValue() == null || liger.get("amortize_type").getValue() == ""){
 			$.ligerDialog.warn("摊销方式不能为空");  
 			return false;  
 		}   */
 		if($("#inv_model").val() == ""){
 			$.ligerDialog.warn("规格型号不能为空");  
 			return false;  
 		}  
 		if(liger.get("unit_code").getValue() == null || liger.get("unit_code").getValue() == ""){
 			$.ligerDialog.warn("计量单位不能为空");  
 			return false;  
 		}
 		if(liger.get("med_sx_id").getValue() == null || liger.get("med_sx_id").getValue() == ""){
 			$.ligerDialog.warn("药品属性不能为空");  
 			return false;  
 		}
 		if(liger.get("med_jx_id").getValue() == null || liger.get("med_jx_id").getValue() == ""){
 			$.ligerDialog.warn("药品剂型不能为空");  
 			return false;  
 		}
 		
 		if($("#bqplxs").val() == ""){
 			$.ligerDialog.warn("病区拆零系数不能为空");  
 			return false;  
 		}
 		
 		if($("#mzplxs").val() == ""){
 			$.ligerDialog.warn("门诊拆零系数不能为空");  
 			return false;  
 		}
 		//明细
 		/* var msg="";
 		var data = gridManager.getData();
 		var targetMap = new HashMap();
 		$.each(data,function(i, v){
 			if (v.inv_id == "" || v.inv_id == null || v.inv_id == 'undefined') {
				var key=v.sup_id;
				var value="第"+(i+1)+"行";
				if(targetMap.get(key)== null || targetMap.get(key) == 'undefined' || targetMap.get(key) == ""){
					targetMap.put(key ,value);
				}else{
					msg += targetMap.get(key)+"与"+value+"供应商重复" + "<br>";
				}
 			}
 		});
 		if(msg != ""){
 			$.ligerDialog.warn(msg+"请修改！");  
			return false;  
 		}  */	 	
    }
	
	//键盘事件
	function loadHotkeys() {
		hotkeys('Esc', this_close);
	 }
	
	function change_open(){
		
		var paras = "group_id="+$("#group_id").val()
			+"&"+ "hos_id="+$("#hos_id").val()
			+"&"+ "copy_code="+$("#copy_code").val() 
			+"&"+ "inv_code="+$("#inv_code").val();
		
		$.ligerDialog.open({
			title: '药品变更记录查询',
			height: 400,
			width: 800,
			url: 'medInvDictMainPage.do?isCheck=false&' + paras.toString(),
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true, top: 1,
			buttons: [
				{ text: '取消', onclick: function(item, dialog){dialog.close();}}
			]
		});   
    }
	
	//关闭当前页面
	function this_close(){
		frameElement.dialog.close();
	} 
	
	function openUpdate(){
		parent.$.ligerDialog.open({
			url: 'hrp/med/info/basic/inv/medInvCertInfoMainPage.do?isCheck=false&inv_id='+'${medInv.inv_id}', 
			height: 300,width:950, title:'药品证件对应关系',
         	modal:true, showToggle:false, showMax:true ,showMin:false, isResize:true,
         	parentframename: window.name,
       		buttons: [ 
				{ text: '保存', onclick: function(item, dialog){dialog.frame.save();}, cls:'l-dialog-btn-highlight' },
				{ text: '关闭', onclick: function (item, dialog) { dialog.close(); } } 
			] 
		});
	}
	//鼠标悬停事件显示obj的值
	function showTip(oEvent,obj){  
		var oDiv = document.getElementById("divTip1"); 
		
		oDiv.innerHTML=obj.value; 
		oDiv.style.visibility="visible"; 
		oDiv.style.left = oEvent.clientX+15; 
		oDiv.style.top =oEvent.clientY+15; 
	}  
	//鼠标移出事件
	function hideTip(oEvent){  
		var oDiv = document.getElementById("divTip1");  
		oDiv.style.visibility = "hidden";  
	}  
	
	
	 function stopTimes(){
	    var data = gridManager.getData();
	    $.each(data,function(rowindex,item){});
	    var paramVo = {
	    	is_com : $("#is_com").prop("checked") ? 1 : 0,	
	    	inv_id : $("#inv_id").val(),
	    };
	    ajaxJsonObjectByUrl("queryStopTimeByCode.do?isCheck=false",paramVo,function(responseData){
	    	if(responseData.Rows[0] != null){
	           	var num = responseData.Rows[0].CUR_AMOUNT;
	           	if(num > 0){
	           		$.ligerDialog.warn("仍有库存，无法停用！");  
	           		$("#edate").val("");
	           	}	
            }
        });
	 }
	
	</script>
	</head>
  
	<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post"  id="form1" >
		<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" style="padding: 10px" border="0">
	        <tr>
	            <td align="right" class="l-table-edit-td"   width="10%">
	            	<span style="color:red">*</span>药品编码：
	            </td>
	            <td align="left" class="l-table-edit-td" width="20%">
	            	<input name="inv_code" type="text" id="inv_code" ltype="text" required="true" value="${medInv.inv_code}" validate="{required:true,maxlength:20}" />
	            </td>
	            <td align="right" class="l-table-edit-td"  width="10%">
	            	<span style="color:red">*</span>药品名称：
	            </td>
	            <td align="left" class="l-table-edit-td" width="20%">
	            	<input name="inv_name" type="text" id="inv_name" ltype="text" required="true" value="${medInv.inv_name}" validate="{required:true,maxlength:50}" onmouseover="showTip(event, this)" onmouseout="hideTip(event)"/>
	            </td>
	            <td align="right" class="l-table-edit-td"  width="10%">
	            	别名：
	            </td>
	            <td align="left" class="l-table-edit-td" width="20%">
	            	<input name="alias" type="text" id="alias" ltype="text"  value="${medInv.alias}"  validate="{required:false}" onmouseover="showTip(event, alias)" onmouseout="hideTip(event)"/>
	            </td>
	        </tr> 
	        <tr>
	            <td align="right" class="l-table-edit-td"  >
	            	<span style="color:red">*</span>药品类别：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="med_type_code" type="text" id="med_type_code" ltype="text" required="true" validate="{required:true}" />
	            </td>
	            <td align="right" class="l-table-edit-td" >
	            	<span style="color:red">*</span>计价方法：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="price_type" type="text" id="price_type" ltype="text" required="true" validate="{required:true}" />
	            </td>
	             <td align="right" class="l-table-edit-td"  >
	            	<span style="color:red">*</span>药品属性：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="med_sx_id" type="text" id="med_sx_id" ltype="text"  validate="{required:true}" />
	            </td>
			</tr>
			 <tr>
	            <td align="right" class="l-table-edit-td"  >
	            	<span style="color:red">*</span>药品剂型：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="med_jx_id" type="text" id="med_jx_id" ltype="text"   validate="{required:true}" />
	            </td>
	            <td align="right" class="l-table-edit-td" >
	            	<span style="color:red">*</span>病区拆零系数：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="bqplxs" type="text" id="bqplxs" ltype="text"  value="${medInv.bqplxs}" validate="{required:true}" />
	            </td>
	            <td align="right" class="l-table-edit-td"  >
	            	<span style="color:red">*</span>门诊拆零系数：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="mzplxs" type="text" id="mzplxs" ltype="text" value="${medInv.mzplxs}" validate="{required:true}" />
	            </td>
			</tr>
			<tr>
	            <td align="right" class="l-table-edit-td"  >
	            	规格型号：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="inv_model" type="text" id="inv_model" ltype="text" value="${medInv.inv_model}" validate="{required:false,maxlength:200}" onmouseover="showTip(event, inv_model)" onmouseout="hideTip(event)"/>
	            </td>
	            <td align="right" class="l-table-edit-td" >
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
	            	<input name="plan_price" type="text" id="plan_price" ltype="text" value="${medInv.plan_price}"  validate="{required:false,maxlength:20}" onchange="getSellPrice()"/>
	            </td>
	            <td align="right" class="l-table-edit-td" >
	            	加价率：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="price_rate" type="text" id="price_rate" ltype="text" value="${medInv.price_rate}" validate="{required:false,maxlength:20}" onchange="getSellPrice()"/>
	            </td>
	            <td align="right" class="l-table-edit-td" >
	            	零售价：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="sell_price" type="text" id="sell_price" ltype="text" value="${medInv.sell_price}" validate="{required:false,maxlength:20}" />
	            </td>
	        </tr> 
	        <tr>
	        	 <td align="right" class="l-table-edit-td"  >
	        		启用日期：
	        	</td>
	            <td align="left" class="l-table-edit-td">
	            	<input class="Wdate" name="sdate" id="sdate" type="text" onchange="change_eDate()" value="${medInv.sdate}" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
	            </td>
	            <td align="right" class="l-table-edit-td"  >
	            	停用日期：
	            </td>
	            <td align="left" class="l-table-edit-td">
		            <input class="Wdate" name="edate" id="edate" type="text" value="${medInv.edate}" onchange="changeDate()" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
	            </td>
	            <td align="right" class="l-table-edit-td" >
	            	代理商：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="agent_name" type="text" id="agent_name" ltype="text" value="${medInv.agent_name}" validate="{required:false,maxlength:60}" />
	            </td>
	        </tr>
	        <tr>
	            <td align="right" class="l-table-edit-td" >
	            	品牌名称：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="brand_name" type="text" id="brand_name" ltype="text" value="${medInv.brand_name}" validate="{required:false,maxlength:60}" />
	            </td>
	            <td align="right" class="l-table-edit-td" >
					药品用途：
				</td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="inv_usage" type="text" id="inv_usage" ltype="text" value="${medInv.inv_usage}" validate="{required:false,maxlength:60}" />
	            </td>
	            <td align="right" class="l-table-edit-td" >
	            	包装规格：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="inv_structure" type="text" id="inv_structure" ltype="text" value="${medInv.inv_structure}" validate="{required:false,maxlength:60}" />
	            </td>
	        </tr> 
	        <tr>
	            <td align="right" class="l-table-edit-td" >
	            	单位重量：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="per_weight" type="text" id="per_weight" ltype="text" value="${medInv.per_weight}" validate="{required:false,maxlength:20}" />
	            </td>
	            <td align="right" class="l-table-edit-td"  >
	            	单位体积：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="per_volum" type="text" id="per_volum" ltype="text" value="${medInv.per_volum}" validate="{required:false,maxlength:20}" />
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
	            	<input class="Wdate" name="bid_date" id="bid_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" value="${medInv.bid_date}"/>
	            </td> 
	            <td align="right" class="l-table-edit-td"  >
	            	项目中标编码：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="bid_code" type="text" id="bid_code" ltype="text" validate="{required:false,maxlength:50}" value="${medInv.bid_code}"/>
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
	            	<input name="memory_encoding" type="text" id="memory_encoding" ltype="text" validate="{required:false,maxlength:50}" value="${medInv.memory_encoding}"/>
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
	            	<input name="is_zero_store" type="checkbox" id="is_zero_store" ltype="text" validate="{required:false}" />
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	零库存管理标识
	            </td>

	            <td align="center" class="l-table-edit-td" colspan="6"><a href=javascript:openUpdate();>证件信息</a></td>
	        </tr>
	        <tr style="display:none">
        		 <td align="right" class="l-table-edit-td"  >
	            	摊销方式：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="amortize_type" type="text" id="amortize_type" ltype="text"  validate="{required:false}" />
	            </td>
	             <td align="right" class="l-table-edit-td"  >
	            	<input name="is_implant" type="checkbox" id="is_implant" ltype="text" validate="{required:false}" />
	            </td>
	            <td align="left" class="l-table-edit-td" >
	            	植入标识
	            </td>
	            <td align="right" class="l-table-edit-td"  >
	            	<input name="is_involved" type="checkbox" id="is_involved" ltype="text" validate="{required:false}" />介入标识
	            </td>
        		
        	</tr>
	        <tr>
	        	<td class="l-table-edit-td" colspan="6">
	        		<table cellpadding="0" cellspacing="0" style="margin-top: 20px;" class="l-table-edit" border="1px;" width="100%">
	        			<tr height="30px" style="background: url(../../../../../lib/ligerUI/skins/Aqua/images/grid/header-bg.gif) repeat;);">
	        				<td align="center" style="display:none" width="6%">收费标识</td>
	        				<td align="center" style="display:none" width="6%">高值标识</td>
	        				<td align="center" style="display:none" width="8%">耐用品标识</td>
	        				<td align="center" style="display:none" width="6%">代销标识</td>
	        				<td align="center" width="8%">条码管理标识</td>
	        				<td align="center" width="8%">个体码管理标识</td>
	        				<td align="center" width="6%">品种码标识</td>
	        				<td align="center" width="10%">保质期管理标识</td>
	        				<td align="center" style="display:none" width="10%">灭菌药品标识</td>
	        				<td align="center" width="10%">证件管理标识</td>
	        				<td align="center" width="10%">科室库管理标识</td>
	        				<td align="center" width="8%">自制品标识</td>
	        			</tr>
	        			<tr>
	        				<td align="center" class="l-table-edit-td" style="display:none">
	        					<input name="is_charge" type="checkbox" id="is_charge" ltype="text" />
	        				</td>
	        				<td align="center" class="l-table-edit-td" style="display:none">
	        					<input name="is_highvalue" type="checkbox" id="is_highvalue" ltype="text" />
	        				</td>
	        				<td align="center" class="l-table-edit-td" style="display:none">
	        					<input name="is_dura" type="checkbox" id="is_dura" ltype="text" />
	      					</td>
	        				<td align="center" class="l-table-edit-td" style="display:none">
	        					<input name="is_com" type="checkbox" id="is_com" ltype="text" />
	        				</td>
	        				<td align="center" class="l-table-edit-td">
	        					<input name="is_bar" type="checkbox" id="is_bar" ltype="text" />
	        				</td>
	        				<td align="center" class="l-table-edit-td">
	        					<input name="is_per_bar" type="checkbox" id="is_per_bar" ltype="text"/>
	        				</td>
	        				<td align="center" class="l-table-edit-td">
	        					<input name="is_inv_bar" type="checkbox" id="is_inv_bar" ltype="text"/>
	        				</td>
	        				<td align="center" class="l-table-edit-td">
	        					<input name="is_quality" type="checkbox" id="is_quality" ltype="text" />
	        				</td>
	        				<td align="center" class="l-table-edit-td" style="display:none">
	        					<input name="is_disinfect" type="checkbox" id="is_disinfect" ltype="text"/>
	        				</td>
	        				<td align="center" class="l-table-edit-td">
	        					<input name="is_cert" type="checkbox" id="is_cert" ltype="text" />
	        				</td>
	        				<td align="center" class="l-table-edit-td">
	        					<input name="is_sec_whg" type="checkbox" id="is_sec_whg" ltype="text" />
	        				</td>
	        				<td align="center" class="l-table-edit-td">
	        					<input name="is_shel_make" type="checkbox" id="is_shel_make" ltype="text" />
	        				</td>
	        			</tr>
	        		</table>
	        	</td>
	        </tr>
	        <tr>
	        	<td colspan="6" align="left" class="l-table-edit-td" >
	        		<h2><a href="#" onclick="change_open();">>>变更记录</a></h2>
	        	</td>
	        </tr>
	        <tr>
	        	<td align="right" class="l-table-edit-td" >
	        		修改说明：
	        	</td>
	        	<td align="left" class="l-table-edit-td" colspan="3">
	        		<input name="change_note" type="text" id="change_note" ltype="text"  validate="{required:false,maxlength:100}" value="更新"/>
	        	</td>
	        	<td align="left" class="l-table-edit-td" colspan="2">
	        		<input name="is_change" type="checkbox" id="is_change" ltype="text" checked />是否保存变更记录
	        	</td>
	        </tr>
	        <tr>
	        	<td colspan="6" align="left" class="l-table-edit-td" >
	        		<h3>>>药品供应商明细</h3>
	        	</td>
	        </tr>
	        <tr>
	        	<td colspan="6" class="l-table-edit-td" >
	        		<div id="maingrid"></div>
	        	</td>
	        </tr>
	        <tr>
	        	<td colspan="6" align="left" class="l-table-edit-td" >
	        		<input type="hidden" name="group_id" id="group_id" value="${medInv.group_id}">
	        		<input type="hidden" name="hos_id" id="hos_id" value="${medInv.hos_id}">
	        		<input type="hidden" name="copy_code" id="copy_code" value="${medInv.copy_code}">
	        		<input type="hidden" name="inv_id" id="inv_id" value="${medInv.inv_id}">
	        		<input type="hidden" name="inv_no" id="inv_no" value="${medInv.inv_no}">
	        		<input type="hidden" name="old_is_bar" id="old_is_bar" value="${medInv.is_bar}">
	        		<input type="hidden" name="old_is_com" id="old_is_com" value="${medInv.is_com}">
	        	</td>
	        </tr>
	    </table>
    </form>
	<div id="divTip1" style="width: 140px; border: 1px solid #ffc340; background:#fffcc7; font-size: 16px; position:absolute; visibility:hidden; padding: 5px">  
 	</div>  
 	<script type="text/javascript">
 	var stopTime = document.getElementById("edate");
	stopTime.onfocus = function(){
		WdatePicker({
			isShowClear:true,
			readOnly:false,
			dateFmt:'yyyy-MM-dd',
			onpicked:function(dp){
				date_b = dp.cal.getNewDateStr();
				stopTimes();
			}
		})
	}
 	</script>
    </body>
</html>
