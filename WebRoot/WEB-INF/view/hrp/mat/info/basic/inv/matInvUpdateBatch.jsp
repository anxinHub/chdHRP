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
     $(function (){
		loadDict()//加载下拉框
        loadForm();
		loadHotkeys();
     });
     
     //表单提交的全部弹出数据
	function  save(){
		changeDate();
        if($("form").valid()){
        	if(!dateValid("sdate", "edate")){
        		$.ligerDialog.warn("启用日期不能大于停用日期");  
    			return false;
    		}
			
			var formPara={
		        group_id : $("#group_id").val(),
		        hos_id : $("#hos_id").val(),
		        copy_code : $("#copy_code").val(),
				inv_ids : $("#inv_ids").val(),
				//暂不支持类别批量修改
				//mat_type_id : liger.get("mat_type_code").getValue() == null ? "" : liger.get("mat_type_code").getValue().split(",")[0],
				//mat_type_no : liger.get("mat_type_code").getValue() == null ? "" : liger.get("mat_type_code").getValue().split(",")[1],
				insura_type_id : liger.get("insura_type_id").getValue() == null ? "" : liger.get("insura_type_id").getValue(),
	    	    instru_type_id : liger.get("instru_type_id").getValue() == null ? "" : liger.get("instru_type_id").getValue(),			
				price_type : liger.get("price_type").getValue() == null ? "" : liger.get("price_type").getValue(),
				amortize_type : liger.get("amortize_type").getValue() == null ? "" : liger.get("amortize_type").getValue(),
				inv_model : $("#inv_model").val(),
				unit_code : liger.get("unit_code").getValue() == null ? "" : liger.get("unit_code").getValue(),
				fac_id : liger.get("fac_code").getValue() == null ? "" : liger.get("fac_code").getValue().split(",")[0],
				fac_no : liger.get("fac_code").getValue() == null ? "" : liger.get("fac_code").getValue().split(",")[1],
				sup_id : liger.get("sup_code").getValue() == null ? "" : liger.get("sup_code").getValue(),
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
				is_single_ven : liger.get("is_single_ven").getValue() == null ? "" : liger.get("is_single_ven").getValue(),
				stora_tran_cond : $("#stora_tran_cond").val(),
				use_state : liger.get("use_state").getValue() == null ? "" : liger.get("use_state").getValue(),
				is_bid : liger.get("is_bid").getValue() == null ? "" : liger.get("is_bid").getValue(),
				bid_date : $("#bid_date").val(),
				bid_code : $("#bid_code").val(),
				memory_encoding : $("#memory_encoding").val(),
				source_plan : liger.get("source_plan").getValue() == null ? "" : liger.get("source_plan").getValue(),
				is_zero_store : liger.get("is_zero_store").getValue() == null ? "" : liger.get("is_zero_store").getValue(),
				is_involved : liger.get("is_involved").getValue() == null ? "" : liger.get("is_involved").getValue(),
				is_implant : liger.get("is_implant").getValue() == null ? "" : liger.get("is_implant").getValue(),
						
				is_charge : liger.get("is_charge").getValue() == null ? "" : liger.get("is_charge").getValue(),
				is_highvalue : liger.get("is_highvalue").getValue() == null ? "" : liger.get("is_highvalue").getValue(),
				is_dura : liger.get("is_dura").getValue() == null ? "" : liger.get("is_dura").getValue(),
				is_com : liger.get("is_com").getValue() == null ? "" : liger.get("is_com").getValue(),
				is_bar : liger.get("is_bar").getValue() == null ? "" : liger.get("is_bar").getValue(),
				is_per_bar : liger.get("is_per_bar").getValue() == null ? "" : liger.get("is_per_bar").getValue(),
				is_inv_bar : liger.get("is_inv_bar").getValue() == null ? "" : liger.get("is_inv_bar").getValue(),
				is_quality : liger.get("is_quality").getValue() == null ? "" : liger.get("is_quality").getValue(),
				is_disinfect : liger.get("is_disinfect").getValue() == null ? "" : liger.get("is_disinfect").getValue(),
				is_cert : liger.get("is_cert").getValue() == null ? "" : liger.get("is_cert").getValue(),
				is_sec_whg : liger.get("is_sec_whg").getValue() == null ? "" : liger.get("is_sec_whg").getValue(),
				is_shel_make : liger.get("is_shel_make").getValue() == null ? "" : liger.get("is_shel_make").getValue(),
				bar_code_new : $("#bar_code_new").val(),
				alias: $("#alias").val(), 
				inv_name:$("#inv_name").val(),
				is_change : $("#is_change").prop("checked") ? 1 : 0,
				change_note : $("#change_note").val()
			};
			console.log( $("#bar_code_new").val())
			console.log(JSON.stringify(formPara))
			ajaxJsonObjectByUrl("updateMatInvBatch.do?isCheck=false",formPara,function(responseData){
				if(responseData.state=="true"){
					parentFrameUse().query();
					this_close();
				}
			});
        }
    }
     
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
		//暂不支持类别批量修改
		//autocomplete("#mat_type_code", "../../../queryMatTypeDict.do?isCheck=false", "id", "text", true, true, {is_last : '1'});
		autocomplete("#instru_type_id", "../../../queryMatInstruType.do?isCheck=false", "id", "text", true, true, false,false,'','160');
		autocomplete("#insura_type_id", "../../../queryMatInsuraType.do?isCheck=false", "id", "text", true, true, false,false,'','160');
    	
		var paras = {
			field_code : "price_type"
		}
		autocomplete("#price_type", "../../../queryMatSysList.do?isCheck=false", "id", "text", true, true, paras);
		paras = {
			field_code : "amortize_type"
		}
		autocomplete("#amortize_type", "../../../queryMatSysList.do?isCheck=false", "id", "text", true, true, paras);
		autocomplete("#unit_code", "../../../queryHosUnit.do?isCheck=false", "id", "text", true, true);
		autocomplete("#fac_code", "../../../queryHosFacDict.do?isCheck=false", "id", "text", true, true);
		autocomplete("#sup_code", "../../../queryHosSup.do?isCheck=false", "id", "text", true, true);
		autocomplete("#manage_type", "../../../queryMatManageType.do?isCheck=false", "id", "text", true, true);
		$("#bar_code_new").ligerTextBox({width:160});
        $("#use_state").ligerComboBox({width:160, disabled:true, cancelable: false});
        /* liger.get("use_state").setValue("1");
		liger.get("use_state").setText("是"); */
		/*
		autocomplete("#use_state", "../../../queryMatYearOrNo.do?isCheck=false", "id", "text", true, true, "", false, "1");
		*/
		autoCompleteByData("#abc_type", matInv_ABCType.Rows, "id", "text", true, true);
		autoCompleteByData("#source_plan", matInv_sourcePlan.Rows, "id", "text", true, true);
		autocomplete("#is_single_ven", "../../../queryMatYearOrNo.do?isCheck=false", "id", "text", true, true);
		autocomplete("#is_bid", "../../../queryMatYearOrNo.do?isCheck=false", "id", "text", true, true);
		autocomplete("#is_zero_store", "../../../queryMatYearOrNo.do?isCheck=false", "id", "text", true, true);
		autocomplete("#is_involved", "../../../queryMatYearOrNo.do?isCheck=false", "id", "text", true, true);
		autocomplete("#is_implant", "../../../queryMatYearOrNo.do?isCheck=false", "id", "text", true, true);
		autocomplete("#is_charge", "../../../queryMatYearOrNo.do?isCheck=false", "id", "text", true, true);
		autocomplete("#is_highvalue", "../../../queryMatYearOrNo.do?isCheck=false", "id", "text", true, true);
		autocomplete("#is_dura", "../../../queryMatYearOrNo.do?isCheck=false", "id", "text", true, true);
		autocomplete("#is_com", "../../../queryMatYearOrNo.do?isCheck=false", "id", "text", true, true);
		autocomplete("#is_bar", "../../../queryMatYearOrNo.do?isCheck=false", "id", "text", true, true);
		autocomplete("#is_per_bar", "../../../queryMatYearOrNo.do?isCheck=false", "id", "text", true, true);
		autocomplete("#is_inv_bar", "../../../queryMatYearOrNo.do?isCheck=false", "id", "text", true, true);
		autocomplete("#is_quality", "../../../queryMatYearOrNo.do?isCheck=false", "id", "text", true, true);
		autocomplete("#is_disinfect", "../../../queryMatYearOrNo.do?isCheck=false", "id", "text", true, true);
		autocomplete("#is_cert", "../../../queryMatYearOrNo.do?isCheck=false", "id", "text", true, true);
		autocomplete("#is_sec_whg", "../../../queryMatYearOrNo.do?isCheck=false", "id", "text", true, true);
		autocomplete("#is_shel_make", "../../../queryMatYearOrNo.do?isCheck=false", "id", "text", true, true);
								
		//渲染效果
		$("#inv_model").ligerTextBox({width:160});
		$("#plan_price").ligerTextBox({width:160, number:true, precision:'${p04006 }'});
		$("#price_rate").ligerTextBox({width:160, number:true});
		$("#sell_price").ligerTextBox({width:160, number:true, precision:'${p04006 }'});
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
		$("#stora_tran_cond").ligerTextBox({width:160});
		$("#memory_encoding").ligerTextBox({width:160});
		$("#alias").ligerTextBox({width:160});
		$("#inv_name").ligerTextBox({width:160});
		$("#change_note").ligerTextBox({width:320});
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
 		
 		//1.启用不为空、停用为空
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
 		
 		//2.停用不为空、启用为空
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
	
	//键盘事件
	function loadHotkeys() {
		hotkeys('Esc', this_close);
	 }
	
	//关闭当前页面
	function this_close(){
		frameElement.dialog.close();
	}
</script>
</head>
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" style="padding: 10px"  >
	        <tr>
	            <!--暂不支持类别批量修改 
	            <td align="right" class="l-table-edit-td"  >
	            	物资类别：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="mat_type_code" type="text" id="mat_type_code" ltype="text"   validate="{required:false}" />
	            </td> 
	            -->
	            <td align="right" class="l-table-edit-td" >
	            	计价方法：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="price_type" type="text" id="price_type" ltype="text"  validate="{required:false}" />
	            </td>
	            <td align="right" class="l-table-edit-td"  >
	            	摊销方式：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="amortize_type" type="text" id="amortize_type" ltype="text"  validate="{required:false}" />
	            </td>
	            <td align="right" class="l-table-edit-td"  >
	            	规格型号：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="inv_model" type="text" id="inv_model" ltype="text"  validate="{required:false,maxlength:200}" />
	            </td>
			</tr>
			<tr>
	            <td align="right" class="l-table-edit-td"  >
	            	计量单位：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="unit_code" type="text" id="unit_code" ltype="text" validate="{required:false}" />
	            </td>
	             <td align="right" class="l-table-edit-td"  >
	            	生产厂商：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="fac_code" type="text" id="fac_code" ltype="text"  validate="{required:false}" />
	            </td>
	             <td align="right" class="l-table-edit-td"  >
	            	供应商：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="sup_code" type="text" id="sup_code" ltype="text"  validate="{required:false}" />
	            </td>
	        </tr> 
	        <tr>
	            <td align="right" class="l-table-edit-td"  >
	            	计划价：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="plan_price" type="text" id="plan_price"  ltype="text" validate="{required:false,maxlength:20}"  onchange="getSellPrice()"/>
	            </td>
	            <td align="right" class="l-table-edit-td"  >
	            	加价率：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="price_rate" type="text" id="price_rate" ltype="text" validate="{required:false,maxlength:20}"  onchange="getSellPrice()"/>
	            </td>
	            <td align="right" class="l-table-edit-td"  >
	            	零售价：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="sell_price" type="text" id="sell_price"  ltype="text" validate="{required:false,maxlength:20}" />
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
	            	是否中标
	            </td>
	            <td align="left" class="l-table-edit-td" >
	            	<input name="is_bid" type="text" id="is_bid" ltype="text" validate="{required:false}" />
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
	            	是否唯一供应商
	            </td>
	            <td align="left" class="l-table-edit-td" >
	            	<input name="is_single_ven" type="text" id="is_single_ven" ltype="text" validate="{required:false}" />
	            </td>
	            <td align="right" class="l-table-edit-td"  >
	            	是否零库存管理
	            </td>
	            <td align="left" class="l-table-edit-td" >
	            	<input name="is_zero_store" type="text" id="is_zero_store" ltype="text" validate="{required:false}" />
	            </td>
	            <td align="right" class="l-table-edit-td"  >
	            	是否介入
	            </td>
	            <td align="left" class="l-table-edit-td" >
	            	<input name="is_involved" type="text" id="is_involved" ltype="text" validate="{required:false}" />
	            </td>
	        </tr>
	        <tr>
	            <td align="right" class="l-table-edit-td"  >
	            	是否植入
	            </td>
	            <td align="left" class="l-table-edit-td" >
	            	<input name="is_implant" type="text" id="is_implant" ltype="text" validate="{required:false}" />
	            </td>
	            <td align="right" class="l-table-edit-td"  >
	            	是否收费
	            </td>
	            <td align="left" class="l-table-edit-td" >
	            	<input name="is_charge" type="text" id="is_charge" ltype="text" validate="{required:false}" />
	            </td>
	            <td align="right" class="l-table-edit-td"  >
	            	是否高值
	            </td>
	            <td align="left" class="l-table-edit-td" >
	            	<input name="is_highvalue" type="text" id="is_highvalue" ltype="text" validate="{required:false}" />
	            </td>
			</tr>
	        <tr>
	            <td align="right" class="l-table-edit-td"  >
	            	是否耐用品
	            </td>
	            <td align="left" class="l-table-edit-td" >
	            	<input name="is_dura" type="text" id="is_dura" ltype="text" validate="{required:false}" />
	            </td>
	            <td align="right" class="l-table-edit-td"  >
	            	是否代销
	            </td>
	            <td align="left" class="l-table-edit-td" >
	            	<input name="is_com" type="text" id="is_com" ltype="text" validate="{required:false}" />
	            </td>
	            <td align="right" class="l-table-edit-td"  >
	            	是否条码管理
	            </td>
	            <td align="left" class="l-table-edit-td" >
	            	<input name="is_bar" type="text" id="is_bar" ltype="text" validate="{required:false}" />
	            </td>
			</tr>
	        <tr>
	            <td align="right" class="l-table-edit-td"  >
	            	是否个体码
	            </td>
	            <td align="left" class="l-table-edit-td" >
	            	<input name="is_per_bar" type="text" id="is_per_bar" ltype="text" validate="{required:false}" />
	            </td>
	            <td align="right" class="l-table-edit-td"  >
	            	是否保质期管理
	            </td>
	            <td align="left" class="l-table-edit-td" >
	            	<input name="is_quality" type="text" id="is_quality" ltype="text" validate="{required:false}" />
	            </td>
	            <td align="right" class="l-table-edit-td"  >
	            	是否灭菌材料
	            </td>
	            <td align="left" class="l-table-edit-td" >
	            	<input name="is_disinfect" type="text" id="is_disinfect" ltype="text" validate="{required:false}" />
	            </td>
			</tr>
	        <tr>
	            <td align="right" class="l-table-edit-td"  >
	            	是否证件管理
	            </td>
	            <td align="left" class="l-table-edit-td" >
	            	<input name="is_cert" type="text" id="is_cert" ltype="text" validate="{required:false}" />
	            </td>
	            <td align="right" class="l-table-edit-td"  >
	            	是否科室库管理
	            </td>
	            <td align="left" class="l-table-edit-td" >
	            	<input name="is_sec_whg" type="text" id="is_sec_whg" ltype="text" validate="{required:false}" />
	            </td>
	            <td align="right" class="l-table-edit-td"  >
	            	是否自制品
	            </td>
	            <td align="left" class="l-table-edit-td" >
	            	<input name="is_shel_make" type="text" id="is_shel_make" ltype="text" validate="{required:false}" />
	            </td>
	        </tr>
	        <tr>
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
	            <td align="right" class="l-table-edit-td"  >
	            	是否品种码
	            </td>
	            <td align="left" class="l-table-edit-td" >
	            	<input name="is_inv_bar" type="text" id="is_inv_bar" ltype="text" validate="{required:false}" />
	            </td>
			</tr>
			<tr>
	            <td align="right" class="l-table-edit-td"  >
	            	别名：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="alias" type="text" id="alias" ltype="text" validate="{required:false}" />
	            </td>
	            <td align="right" class="l-table-edit-td"  >
	            	材料名称：
	            </td>
	            <td align="left" class="l-table-edit-td" >
	            	<input name="inv_name" type="text" id="inv_name" ltype="text" validate="{required:false}" />
	            </td>
	            <td align="right" class="l-table-edit-td"  >
	            </td>
	            <td align="left" class="l-table-edit-td" >
	            </td>
	        </tr>
	        <tr>
	        	<td align="right" class="l-table-edit-td"  width="10%">
	            	<span style="color:red"></span>医疗器械分类：
	            </td>
	            <td align="left" class="l-table-edit-td" width="20%">
	            	<input name="instru_type_id" type="text" id="instru_type_id" ltype="text" />
	            </td>
	            <td align="right" class="l-table-edit-td"  width="10%">
	            	<span style="color:red"></span>医疗保险分类：
	            </td>
	            <td align="left" class="l-table-edit-td" width="20%">
	            	<input name="insura_type_id" type="text" id="insura_type_id" ltype="text" />
	            </td>
	        </tr>
	        <tr>
	        	<td align="right" class="l-table-edit-td" >
	        		修改说明：
	        	</td>
	        	<td align="left" class="l-table-edit-td" colspan="3">
	        		<input name="change_note" type="text" id="change_note" ltype="text"  validate="{required:false,maxlength:100}" value="更新"/>
	        	</td>
	        	 <td align="right" class="l-table-edit-td"  width="10%">
	            	<span style="color:red"></span>品种码：
	            </td>
	            <td align="left" class="l-table-edit-td" width="20%">
	            	<input name="bar_code_new" type="text" id="bar_code_new" ltype="text" validate="{required:false,maxlength:50}"  />
	            </td>
	        	<td align="left" class="l-table-edit-td" colspan="2">
	        		<input name="is_change" type="checkbox" id="is_change" ltype="text" checked />是否保存变更记录
	        	</td>
	        </tr>
	        <tr>
	        	<td colspan="6" align="left" class="l-table-edit-td" >
	        		<input type="hidden" name="group_id" id="group_id" value="${group_id}">
	        		<input type="hidden" name="hos_id" id="hos_id" value="${hos_id}">
	        		<input type="hidden" name="copy_code" id="copy_code" value="${copy_code}">
	        		<input type="hidden" name="inv_ids" id="inv_ids" value="${inv_ids}">
	        	</td>
	        </tr>
	    </table>
    </form>
    </body>
</html>
