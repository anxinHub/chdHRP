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
	<script src="<%=path%>/lib/Lodop/barcode.js"	type="text/javascript"></script>
	<script src="<%=path%>/lib/Lodop/LodopFuncs.js"	type="text/javascript"></script>
    <script type="text/javascript">
     var dataFormat;
     var grid;
     var gridManager;
     var state = '${medAffiIn.state}';
	 var by_sup_inv = '${p08021 }';
	 var by_batch_price = '${p08030 }';
	 var printFlag = '${p08042 }';
	 var flag = '${flag}';//是否是订单生成   0：不是  ；1：是
	 
     $(function (){
    	
		loadDict();//加载下拉框
		loadHead();
		loadHotkeys();//加载快捷键
		queryDetail();
		
		$("#store_code").bind("change",function(){
			change_Store();
			loadHead();
	    	grid.reRender();
	    	change_Examiner();
		})
		$("#sup_code").bind("change",function(){
	    	loadHead();
	    	grid.reRender();
		});
		change_button();

     });  
     
     //校验
     function validateGrid() {  
    	//主表
   		if(liger.get("bus_type_code").getValue() == null || liger.get("bus_type_code").getValue() == ""){
   			$.ligerDialog.warn("业务类型不能为空!");  
   			return false;  
   		}
   		if(liger.get("store_code").getValue() == null || liger.get("store_code").getValue() == ""){
   			$.ligerDialog.warn("仓库不能为空!");  
   			return false;  
   		}
   		if($("#in_date").val() == null || $("#in_date").val() == ""){
   			$.ligerDialog.warn("入库日期不能为空!");  
   			return false;  
   		}
   		if(liger.get("bus_type_code").getValue() == '27'){
  			if(liger.get("stock_type_code").getValue() == null || liger.get("stock_type_code").getValue() == ""){
  	  			$.ligerDialog.warn("采购类型不能为空！");  
  	  			return false;  
  	  		} 
  	  		if(liger.get("stocker").getValue() == null || liger.get("stocker").getValue() == ""){
  	  			$.ligerDialog.warn("采购员不能为空！");  
  	  			return false;  
  	  		} 
  		}
  		if(liger.get("sup_code").getValue() == null || liger.get("sup_code").getValue() == ""){
  			$.ligerDialog.warn("供应商不能为空！");  
  			return false;  
  		}  
  		
  		
  		//明细
 		var rowm = "";
  		var msg="";
		var priceMsg = "";
 		var rows = 0;
 		var store_inv = "";  //用于判断是否属于该仓库
 		var sup_inv = ""; //用于判断唯一供应商
  		var data = gridManager.getData();
 		
  		//判断grid 中的数据是否重复或者为空
  		var targetMap = new HashMap();
  		$.each(data,function(i, v){
  			rowm = "";
 			if (v.inv_id) {
 				//如果批号为空给默认批号用于判断
				if(!v.batch_no){
					v.batch_no = '-';
				}
	 			if (v.amount == "" || v.amount == null || v.amount == 'undefined' || v.amount == 0) {
	 				rowm+="[数量]、";
	 			}  
	 			if (v.price == "" || v.price == null  || v.price == 'undefined') {  
	 				rowm+="[单价]、"; 
	 			} 
	 			if(rowm != ""){
	 				rowm = "第"+(i+1)+"行" + rowm.substring(0, rowm.length-1) + "不能为空并且数量不能为0" + "\n\r";
	 			}
	 			msg += rowm;
	 			if(v.is_bar == 1 && !v.sn){
					msg += "第"+(i+1)+"行按条码管理的药品必须输入条形码。<br>";
				}
				if(v.is_quality == 1){
					if(v.inva_date == ""){
						msg += "第"+(i+1)+"行按保质期管理的药品必须输入有效日期。<br>";
					}else{
						if(v.batch_no != "-"){
							//如果药品按保质期管理，则判断有效日期是否与上一批号一致
							var para = {
								inv_id: v.inv_id, 
								batch_no: v.batch_no,  
								inva_date: v.inva_date 
							}
							ajaxJsonObjectByUrl("../../queryMedInvBatchDisinfect.do?isCheck=false", para, function (responseData){
								if(responseData.state=="false"){
									msg += "第"+(i+1)+"行批号"+v.batch_no+"对应的有效日期应为"+responseData.inva_date+"<br>";
								}
							}, false);
						}
					}
				}
				if(v.is_disinfect == 1){
					if(v.disinfect_date == ""){
						msg += "第"+(i+1)+"行灭菌药品必须输入灭菌日期。<br>";
					}else{
						if(v.batch_no != "-"){
							//如果药品是灭菌药品，则判断灭菌日期是否与上一批号一致
							var para = {
								inv_id: v.inv_id, 
								batch_no: v.batch_no,  
								disinfect_date: v.disinfect_date
							}
							ajaxJsonObjectByUrl("../../queryMedInvBatchDisinfect.do?isCheck=false", para, function (responseData){
								if(responseData.state=="false"){
									msg += "第"+(i+1)+"行批号"+v.batch_no+"对应的灭菌日期应为"+responseData.disinfect_date+"<br>";
								}
							}, false);
						}
					}
				}
				
				if(v.batch_no != "-"){
					//同一批号的单价必须一致
					var para = {
						inv_id: v.inv_id, 
						batch_no: v.batch_no,   
						type: 'affi',
						price: v.price 
					}
					ajaxJsonObjectByUrl("../../queryMedInvBatchPrice.do?isCheck=false", para, function (responseData){
						if(responseData.state=="false"){
							priceMsg += "第"+(i+1)+"行批号"+v.batch_no+"对应的单价应为"+responseData.price + ", ";// + ", <br>";
						}
					}, false);
				}
				//如果条码为空给默认条码用于判断
				if(!v.sn){
					v.sn = '-';
					v.bar_code = '-';
				}
                //默认批号可以存在不同单价，所以判断默认批号是否重复应加上单价
				var key= v.batch_no == '-' ? v.inv_id+"|"+v.price+"|"+v.batch_no+"|"+v.sn+"|"+v.location_id+"|"+v.bar_code : v.inv_id+"|"+v.batch_no+"|"+v.sn+"|"+v.location_id+"|"+v.bar_code;
	 		
				var value="第"+(i+1)+"行";
	 			if(targetMap.get(key)== null || targetMap.get(key) == 'undefined' || targetMap.get(key) == ""){
	 				targetMap.put(key ,value);
	 			}else{
	 				msg += targetMap.get(key)+"与"+value + v.batch_no == '-' ? "药品编码、单价、生产批号、条形码、货位不能重复" : "药品编码、生产批号、条形码、货位不能重复" + "<br>";
	 			}
	 			rows = rows + 1;
				store_inv += v.inv_id + ",";
				if(v.is_single_ven == 1){
					sup_inv += v.inv_id + ","; 
				}
 			}
  		});
 		if(rows == 0){
 			$.ligerDialog.warn("请先添加药品！");  
			return false;  
 		}
 		if(store_inv.length > 0){
	 		//判断仓库药品关系
			var para = {
				inv_ids: store_inv.substring(0, store_inv.length-1), 
				store_id: liger.get("store_code").getValue().split(",")[0]
			}
			ajaxJsonObjectByUrl("../../existsMedStoreIncludeInv.do?isCheck=false", para, function (responseData){
				if(responseData.state=="false"){
					//$.ligerDialog.warn("药品"+responseData.inv_ids+"不在该仓库中！");  
					//return false;
					msg += "药品"+responseData.inv_ids+"不在该仓库中！<br>";
				}
			}, false);
	 		
			if(by_sup_inv == 1){
				//供应商药品对应关系
				para = {
					inv_ids: store_inv.substring(0, store_inv.length-1), 
					sup_id: liger.get("sup_code").getValue().split(",")[0]
				}
				ajaxJsonObjectByUrl("../../existsMedAffiSupIncludeInv.do?isCheck=false", para, function (responseData){
					if(responseData.state== false ){
						//$.ligerDialog.warn("药品"+responseData.inv_ids+"不属于该供应商！");  
						//return false;
						msg += "药品"+responseData.inv_ids+"不属于该供应商！<br>";
					}
				}, false);
 			}
 		}
 		
 		
		//如果存在唯一供应商的药品则判断是否是唯一供应商
		if(sup_inv.length > 0){
			var para = {
				inv_ids: sup_inv.substring(0, sup_inv.length-1), 
				sup_id: liger.get("sup_code").getValue().split(",")[0]
			}
			ajaxJsonObjectByUrl("../../existsMedAffiSupIncludeInv.do?isCheck=false", para, function (responseData){
				if(responseData.state=="false"){
					//$.ligerDialog.warn("药品"+responseData.inv_ids+"不符合唯一供应商要求！");  
					//return false;
					msg += "药品"+responseData.inv_ids+"不符合唯一供应商要求！<br>";
				}
			}, false);
		}
  		if(msg != ""){
  			$.ligerDialog.warn(msg);  
 			return false;  
  		} 	 	
		if(priceMsg){
			//提示单价不同是否继续保存
			if(by_batch_price  == 0){
				/* $.ligerDialog.confirm(priceMsg+'确定继续保存单据?', function (yes){
					if(!yes){
						return false;
					}
				}); */
				if(!confirm(priceMsg+'确定继续保存单据?')){
					return false;
				}
			}else{//提示单价不同并中断保存操作
				$.ligerDialog.warn(priceMsg);
				return false;
			}
		}
  		return true;	
   	}
     
     function  save(){
		grid.endEdit();
    	if(validateGrid()){
    		var formPara={
         			in_id : $("#in_id").val(),
     				in_no : $("#in_no").val(),
     				bus_type_code : liger.get("bus_type_code").getValue(),
     				store_id : liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[0],
     				store_no : liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[1],
     				store_code : liger.get("store_code").getText() == null ? "" : liger.get("store_code").getText().split(" ")[0],
     				in_date : $("#in_date").val(),
     				stocker : liger.get("stocker").getValue() == null ? "" : liger.get("stocker").getValue(),
     				sup_id : liger.get("sup_code").getValue() == null ? "" : liger.get("sup_code").getValue().split(",")[0],
     				sup_no : liger.get("sup_code").getValue() == null ? "" : liger.get("sup_code").getValue().split(",")[1],
     				stock_type_code : liger.get("stock_type_code").getValue() == null ? "" : liger.get("stock_type_code").getValue(),
     				app_dept : liger.get("app_dept").getValue() == null ? "" : liger.get("app_dept").getValue(),
     				protocol_code : liger.get("protocol_code").getValue() == null ? "" : liger.get("protocol_code").getValue().split(",")[1],
    				proj_id : liger.get("proj_code").getValue() == null ? "" : liger.get("proj_code").getValue(),
     				brief : $("#brief").val(),
     				delivery_code: $("#delivery_code").val(),
    				examiner: liger.get("examiner").getValue() == null ? "" : liger.get("examiner").getValue(),
    				//old_money_sum : $("#money_sum").val(),
    				detailData : JSON.stringify(gridManager.getData())
     		};
    		
	        ajaxJsonObjectByUrl("updateMedAffiInCommon.do?isCheck=true",formPara,function(responseData){
	            if(responseData.state=="true"){
	            	queryDetail();
	            	parentFrameUse().query();
	            }
	        });
    	}
    }
	
	function queryDetail(){
		grid.options.parms=[];
		grid.options.newPage=1;
        //根据表字段进行添加查询条件
		grid.options.parms.push({name : 'in_id',value : '${medAffiIn.in_id}'});
    	//加载查询条件
    	grid.loadData(grid.where);
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
    	autocomplete("#bus_type_code", "../../queryMedBusType.do?isCheck=false", "id", "text", true, true, {codes:'4,6,8,27,31'}, false, "${medAffiIn.bus_type_code}");
		autocompleteAsync("#store_code", "../../queryMedStore.do?isCheck=false", "id", "text", true, true, {is_com : '1'}, false, false);
		liger.get("store_code").setValue('${medAffiIn.store_id},${medAffiIn.store_no}');
		liger.get("store_code").setText('${medAffiIn.store_code} ${medAffiIn.store_name}');
		
		autocomplete("#stocker", "../../queryMedStockEmp.do?isCheck=false", "id", "text", true, true, "", false, "${medAffiIn.stocker}");
		
		autocompleteAsync("#sup_code", "../../queryHosSupDictDisable.do?isCheck=false", "id", "text", true, true, "", false, false,"","",450);
		liger.get("sup_code").setValue('${medAffiIn.sup_id},${medAffiIn.sup_no}');
		liger.get("sup_code").setText('${medAffiIn.sup_code} ${medAffiIn.sup_name}');
		
		autocomplete("#stock_type_code", "../../queryMedStockType.do?isCheck=false", "id", "text", true, true, "", false, "${medAffiIn.stock_type_code}");
		autocomplete("#app_dept", "../../queryMedAppDept.do?isCheck=false", "id", "text", true, true, {is_last : 1}, false, false);
		liger.get("app_dept").setValue('${medAffiIn.app_dept}');
		liger.get("app_dept").setText('${medAffiIn.app_dept_code} ${medAffiIn.app_dept_name}');
		
		autocomplete("#protocol_code", "../../queryMedProtocolMain.do?isCheck=false", "id", "text", true, true, "", false, false,"","",200);
		liger.get("protocol_code").setValue('${medAffiIn.protocol_id},${medAffiIn.protocol_code}');
		liger.get("protocol_code").setText('${medAffiIn.protocol_code} ${medAffiIn.protocol_name}');
		
		autocomplete("#proj_code", "../../queryMedProj.do?isCheck=false", "id", "text", true, true, "", false, false,"","",450);
		liger.get("proj_code").setValue('${medAffiIn.proj_id}');
		liger.get("proj_code").setText('${medAffiIn.proj_code} ${medAffiIn.proj_name}');
		
		$("#delivery_code").ligerTextBox({width:160});
		autocomplete("#examiner", "../../queryMedStockEmp.do?isCheck=false", "id", "text", true, true, '', false);
		liger.get("examiner").setValue("${medAffiIn.examiner}");
		liger.get("examiner").setText("${medAffiIn.examiner_code} ${medAffiIn.examiner_name}");
		$("#examiner").ligerTextBox({width:160});
		
        $("#in_no").ligerTextBox({width:160, disabled:true});
        $("#bus_type_code").ligerTextBox({width:160});
        $("#in_date").ligerTextBox({width:160});
        $("#store_code").ligerTextBox({width:160});
        $("#stocker").ligerTextBox({width:160});
        $("#sup_code").ligerTextBox({width:450});
        $("#stock_type_code").ligerTextBox({width:160});
        $("#app_dept").ligerTextBox({width:160});
        $("#protocol_code").ligerTextBox({width:160});
        $("#proj_code").ligerTextBox({width:450});
        $("#brief").ligerTextBox({width:450});
               
        if(state > 1){
    		$("#save").ligerButton({click: save, width:90, disabled:true});
        }else{
    		$("#save").ligerButton({click: save, width:90});
        }
        
        $("#merge_print").ligerButton({click: merge_print, width:90});
		$("#bar_print").ligerButton({click: bar_print, width:90});
		$("#print").ligerButton({click: printDate, width:90,disabled:false});
		$("#printCollect").ligerButton({click: printCollect, width:90,disabled:false});
		$("#printSet").ligerButton({click: printSet, width:100});
		$("#close").ligerButton({click: this_close, width:90});
     } 
    
    
  	 //根据仓库改变set验收员
	 function change_Store(){
		//根据仓库改变采购员
	 		var store_id = liger.get("store_code").getValue().split(",")[0]; 
			 autocomplete("#stocker", "../../queryMedStockEmpByStore.do?isCheck=false", "id", "text", true, true, {store_id :store_id}, true);//采购员
			
			 
		var manager = liger.get("store_code").getValue().split(",")[4];
		autocomplete("#examiner", "../../queryMedStockEmp.do?isCheck=false", "id", "text", true, true, {manager : manager}, true);
		
	 }
	 //验收员下拉框
	 function change_Examiner(){
		 autocomplete("#examiner", "../../queryMedStockEmp.do?isCheck=false", "id", "text", true, true,"", true);
	 }
	
    function loadHead() {
    	grid = $("#maingrid").ligerGrid({
			columns : [
				{display : '交易编码',name : 'bid_code',width : 100,align : 'left'}, 
			    {display : '药品编码',name : 'inv_code',width : 150,align : 'left',
					totalSummary: {
	                    align : 'right',
	                    render: function (suminf, column, cell) {
	                    	return '<div>合计：</div>';
	                    }
                	} 
				},{display : '药品名称(E)',name : 'inv_id',textField : 'inv_name',width : 240,align : 'left',
					editor : {
						type : 'select',
						valueField : 'inv_id',
						textField : 'inv_name',
						selectBoxWidth : '80%',
						selectBoxHeight : 240,
						grid : {
							columns : [ 
								{display : '交易编码',name : 'bid_code',width : 100,align : 'left'}, 
								{display : '药品编码', name : 'inv_code', width : 100, align : 'left'}, 
								{display : '药品名称', name : 'inv_name', width : 240, align : 'left'}, 
								{display : '药品类别', name : 'med_type_name', width : 100, align : 'left'}, 
								{display : '规格型号', name : 'inv_model', width : 180, align : 'left'}, 
								{display : '计量单位', name : 'unit_name', width : 140, align : 'left'}, 
								{display : '包装单位', name : 'pack_name', width : 140, align : 'left'}, 
								{display : '转换量', name : 'num_exchange', width : 140, align : 'left'}, 
								{display : '生产厂商', name : 'fac_name', width : 200, align : 'left'}, 
								{display : '计划单价', name : 'plan_price', width : 140, align : 'left',
									render : function(rowdata, rowindex, value) {
										return formatNumber(rowdata.value, '${p08006 }', 1);
									}
								},{
									display : '是否条码', name : 'is_bar', width : 140, align : 'left',
									render : function(rowdata, rowindex, value) {
										return rowdata.is_bar == 1 ? '是' : '否';
									}
								},{
									display : '货位', name : 'location_name', width : 100, align : 'left'
								}, {
									display : '零售价', name : 'sell_price', width : 140, align : 'left',
									render : function(rowdata, rowindex, value) {
										return formatNumber(rowdata.value, '${p08006 }', 1);
									}
								}	
							],
							switchPageSizeApplyComboBox : false,
							onSelectRow: function (data) {
								var e = window.event;
								if (e && e.which == 1) {
									f_onSelectRow_detail(data);
								}
							},
							url : '${p08021}' == 0 ? '../../queryMedInvList.do?isCheck=false&is_stop=1&is_com=1&store_id='+liger.get("store_code").getValue().split(",")[0]
								: '../../queryMedInvList.do?isCheck=false&is_stop=1&is_com=1&store_id='+liger.get("store_code").getValue().split(",")[0]+'&sup_id='+liger.get("sup_code").getValue().split(",")[0],
							//url : '../../queryMedInvList.do?isCheck=false&is_com=1&store_id='+liger.get("store_code").getValue().split(",")[0]+'&sup_id=' + liger.get("sup_code").getValue().split(",")[0],
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
					},
					render : function(rowdata, rowindex, value) {
						
						//控制如果不是条码管理药品不能编辑条码 
						//if(rowdata.is_bar){
						//	rowdata.notEidtColNames.push("sn");
						//}
						return rowdata.inv_name;
					}
				}, {display : '规格型号', name : 'inv_model', width : 180, align : 'left'
				}, {display : '计量单位', name : 'unit_name', width : 80, align : 'left'
				}, {display : '数量(E)', name : 'amount',    width : 80, align : 'right',
					editor :   flag != 0 ? {} :{
						type: 'float'
					},
					totalSummary: {
						align: 'left',
						render: function (suminf, column, cell) {
							return '<div>' + formatNumber(suminf.sum == null ? 0 : suminf.sum, 2, 1) + '</div>';
						}
					}
				}, {
					display : '单价(E)', name : 'price',width : 100,align : 'right',
					editor : {
						type : 'numberbox',
						precision : '${p08006 }'
					},
					render : function(rowdata, rowindex, value) {
						rowdata.price = value == null ? "" : formatNumber(value, '${p08006 }', 0);
						return value == null ? "" : formatNumber(value, '${p08006 }', 1);
					}
				}, {display : '金额', name : 'amount_money', width : 110, type : 'number', align : 'right',
					render : function(rowdata, rowindex, value) {
						return rowdata.amount_money == null ? "" : formatNumber(rowdata.amount_money, '${p08006}', 1);
					},
					totalSummary: {
						align: 'right',
						render: function (suminf, column, cell) {
							return '<div>' + formatNumber(suminf.sum == null ? 0 : suminf.sum, '${p08005 }', 1) + '</div>';
						}
					}
				}, {display : '包装单位(E)', name : 'pack_code', textField : 'pack_name', width : 80, align : 'left',
					editor : {
						type : 'select',
						valueField : 'id',
						textField : 'text',
						url : '../../queryMedHosPackage.do?isCheck=false',
						keySupport : true,
						autocomplete : true,
					},
					render : function(rowdata, rowindex, value) {
						return rowdata.pack_name;
					}
				}, {display : '转换量(E)', name : 'num_exchange', width : 140, type : 'int',
					editor : { type : 'int'}, align : 'left',
					render : function(rowdata, rowindex, value) {
						return rowdata.num_exchange == null ? "" : formatNumber(rowdata.num_exchange, 2, 1);
					}
				}, {display : '包装件数(E)', name : 'num', width : 80, type : 'number',align : 'right',
					editor : {
						type : 'float',
					},
				}, {display : '包装单价', name : 'pack_price', type : 'number', width : 80, align : 'right',
					editor : {
						type : 'float',
					},
					render : function(rowdata, rowindex, value) {
						rowdata.pack_price = value == null ? "" : formatNumber(value, '${p08005 }', 0);
						return value == null ? "" : formatNumber(value, '${p08005 }', 1);
					}
				}, {display : '生产批号(E)', name : 'batch_no', width : 120,align : 'left',
					editor : {
						type : 'text',
					},
					
				}, {
					display: '生产日期(E)', name: 'fac_date', type: 'date', align: 'left', format: 'yyyy-MM-dd', minWidth: 100,
					editor: {
						type: 'date',
					}
				},{
					display: '序列号(E)', name: 'serial_no', width: 100, align: 'left',
					editor: {
						type: 'text',
					}
				},{display : '有效日期(E)', name : 'inva_date', type: 'date', format: 'yyyy-MM-dd', width : 100,
					editor : {
						type : 'date',showSelectBox:false
					}
				}, {display : '灭菌日期(E)', name : 'disinfect_date', type: 'date', format: 'yyyy-MM-dd', width : 100,
					editor : {
						type : 'date',showSelectBox:false
					}
				},{display : '灭菌批号(E)', name : 'disinfect_no', width : 100,align : 'left',
					editor : {
						type : 'text',
					},
				},{display : '条形码(E)', name : 'sn', width : 300, align : 'left',
					editor : {
						type : 'text',
					}
				},{
					display: '生产厂商', name: 'fac_name', width: 180, align: 'left'
				},{
					display: '注册证号(E)', name : 'cert_id',  textField : 'cert_code',minWidth : 200, align : 'left',
					 render : function(rowdata, rowindex, value) {
							return rowdata.cert_code;
						},
					 editor : {
							type : 'select',
							valueField : 'code',
							textField : 'name',
							selectBoxWidth : 200,
							selectBoxHeight : 240,
							keySupport : true,
							autocomplete : true,
							onSelected : function (data){	
						    	if(typeof (data) === "string"){
						    		var formPara="";
								 	formPara = {												 			
								 		cert_id : data
								 	}			 	
						    	}
							}
						}
					
				},{display : '是否个体码(E)', name : 'is_per_bar', textField : 'text', width : 80,
					align : 'left',
					render : function(rowdata, rowindex, value) {
						return rowdata.is_per_bar == 0 ? '否' : '是';
					}
				},{display : '院内码',    name : 'bar_code',  width : 120,  align : 'left'
				},{display : '批发单价(E)', name : 'sale_price', width : 80, align : 'right',
					editor : {
						type : 'numberbox',
						precision : '${p08006 }'
					},
					render : function(rowdata, rowindex, value) {
						rowdata.sale_price = value == null ? "" : formatNumber(value, '${p08006 }', 0);
						return value == null ? "" : formatNumber(value, '${p08006 }', 1);
					}
				},{display : '批发金额', name : 'sale_money',  width : 80, align : 'right',
					render : function(rowdata, rowindex, value) {
						rowdata.sale_money = value == null ? "" : formatNumber(value, '${p08005 }', 0);
						return value == null ? "" : formatNumber(value, '${p08005 }', 1);
					},
					totalSummary: {
						align: 'right',
						render: function (suminf, column, cell) {
							return '<div>' + formatNumber(suminf.sum == null ? 0 : suminf.sum, '${p08005 }', 1) + '</div>';
						}
					}
				},{display : '零售单价(E)', name : 'sell_price', width : 80, align : 'right',
					editor : {
						type : 'numberbox',
						precision : '${p08006 }'
					},
					render : function(rowdata, rowindex, value) {
						return rowdata.sell_price == null ? "" : formatNumber(rowdata.sell_price, '${p08006}', 1);
					}
				},{display : '零售金额', name : 'sell_money', type : 'number', width : 80, align : 'right',
					render : function(rowdata, rowindex, value) {
						return rowdata.sell_money == null ? "" : formatNumber(rowdata.sell_money, '${p08005}', 1);
					},
					totalSummary: {
						align: 'right',
						render: function (suminf, column, cell) {
							return '<div>' + formatNumber(suminf.sum == null ? 0 : suminf.sum, '${p08005 }', 1) + '</div>';
						}
					}
				},{
					display : '货位名称(E)', name : 'location_id', textField : 'location_name', width : 80, align : 'left',
					editor : {
						type : 'select',
						valueField : 'id',
						textField : 'text',
						url : '../../queryMedLocationDict.do?isCheck=false',
						keySupport : true,
						autocomplete : true,
					},
					render : function(rowdata, rowindex, value) {
						return rowdata.location_name;
					}
				},{ display : '备注', name : 'note', align : 'left',width:100,
					editor : {
						type : 'text',
					}
				},{ display : '药品变更号', name : 'inv_no', align : 'left', hide:true, width:140
				},{display : '订单关系', name : 'order_rela', align : 'left', hide:true, width:140
				},{display : '是否唯一供应商', name : 'is_single_ven', align : 'left', hide:true, width:140
				},{display : '明细ID', name : 'detail_id', align : 'left', hide:true, width:140
				},{display : '药品变更号', name : 'inv_no', align : 'left', hide:true, width:140
				}
			],
			dataAction : 'server',
			dataType : 'server',
			usePager : false,
			url:'queryMedAffiInCommonDetail.do?isCheck=false',
			width : '100%',
			height : '90%',alternatingRow : true, 
			enabledEdit :true,
			isAddRow : (flag == 1 ) ? false : true,
			onBeforeEdit : f_onBeforeEdit, onBeforeSubmitEdit : f_onBeforeSubmitEdit, onAfterEdit : f_onAfterEdit,
			onsuccess:function(){
				var path = window.location.pathname+"/maingrid";
				var url = '../../../sys/querySysTableStyle.do?isCheck=false';
				loadColHeader({
					grid:grid,
					path:path,
					url:url
				});
				//is_addRow();
			},
			isScroll : true, checkbox: true, rownumbers : true, delayLoad : true,//初始化明细数据
			selectRowButtonOnly : true,//heightDiff: -10,
			onKeyDownWhenEnter:f_onKeyDownWhenEnter,
			toolbar : {
				items : [ {text : '删除（<u>D</u>）',id : 'delete',click : remove,icon : 'delete', disabled: state == 1 ? false : true},
				          {line : true},
				          {text : '审核（<u>S</u>）',id : 'audit',click : audit,icon : 'audit', disabled: state == 1 ? false : true},
				          {line : true}, 
				          {text : '销审（<u>X</u>）',id : 'unAudit',click : unAudit,icon : 'unaudit', disabled: state == 2 ? false : true},
				          {line : true},
				          {text : '入库确认（<u>C</u>）',id : 'confirm',click : confirm_in,icon : 'account', disabled: state == 2 ? false : true},
				          {line : true},
				          {text : '维护发票（<u>W</u>）',id : 'invoice',click : invoice_open,icon : 'edit', disabled: state == 3 ? false : true},
				          {line : true},
				          {text : '上一张（<u>B</u>）',id : 'before',click : before_no,icon : 'before'}, 
				          {line : true},
				          {text : '下一张（<u>N</u>）',id : 'next',click : next_no,icon : 'after'},
				          {line : true},
				          {text : '条形码：<input type="text"/>', id : 'sn_imp', click : sn_imp, icon : 'up'},
				          {line : true},
				          { text: '复制药品（<u>M</u>）', id:'copy', click: copy, icon:'copy',disabled: (flag == 0 && state == 1) ? false : true }
				        ]
			}
		});

		gridManager = $("#maingrid").ligerGetGridManager();
		
		
	}
    function f_onKeyDownWhenEnter(e){
    	column_name = e.column.name;	
    	if(column_name=='sn'){
    		return false;
    	}
    }
    //复制药品
    function copy () {
		var data_copys = grid.getCheckedRows();
		if (data_copys.length == 0){
			$.ligerDialog.error('请选择行');
			return;
		}else{
			for(var i = 0, length = data_copys.length; i<length; i++){         // 把数组的每个data都复制一遍，即深复制
				data_copys[i] = $.extend({},data_copys[i]) ;
				data_copys[i].amount = '1';
				data_copys[i].detail_id='';
			}
			grid.addRows(data_copys);
		}
	}
    
    var rowindex_id = "";
	var column_name = "";
	function f_onBeforeEdit(e) {
		rowindex_id = e.rowindex;
		clicked = 0;
		column_name = e.column.name;	
		if(column_name=='cert_id'){
			var certId = grid.getColumnByName("cert_id");
			certId.editor.url='../../queryMedInvCertId.do?isCheck=false&inv_id='+e.record.inv_id;
		}
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
		
		if (selectData != "" || selectData != null) {
			if (column_name == "inv_id") {
				grid.updateRow(rowindex_id, {
					inv_code : data.inv_code,
					inv_no : data.inv_no,
					bid_code : data.bid_code == null ? "" : data.bid_code,
					inv_model : data.inv_model == null ? "" : data.inv_model,
					unit_name : data.unit_name == null ? "" : data.unit_name,
					pack_code : data.pack_code == null ? "" : data.pack_code,
					pack_name : data.pack_name == null ? "" : data.pack_name,
					num_exchange : data.num_exchange == null ? "" : data.num_exchange,
					is_batch : data.is_batch == null ? 0 : data.is_batch,
					is_bar : data.is_bar == null ? 0 : data.is_bar,
					is_per_bar : data.is_per_bar == null ? 0 : data.is_per_bar,
					is_quality : data.is_quality == null ? 0 : data.is_quality,
					is_disinfect : data.is_disinfect == null ? 0 : data.is_disinfect,
					is_highvalue : data.is_highvalue == null ? 0 : data.is_highvalue,
					price : data.plan_price == null ? "" : data.plan_price,
					location_id : data.location_id == null ? "" : data.location_id,
					location_name : data.location_name == null ? "" : data.location_name,
					sell_price : data.sell_price == null ? "" : data.sell_price,
					is_single_ven : data.is_single_ven == null ? "" : data.is_single_ven,
					fac_name : data.fac_name == null ? "" : data.fac_name,
					cert_id : data.cert_id == null ? "" : data.cert_id,	
					cert_code : data.cert_code == null ? "" : data.cert_code
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
		if (e.column.name == "inv_id" && e.value == ""){
			//$.ligerDialog.warn('请选择药品！');
			grid.setCellEditing(e.record, e.column, true);
			return false;
		}
		if (e.column.name == "amount" && e.value == 0){
			//$.ligerDialog.warn('数量不能为0！');
			grid.setCellEditing(e.record, e.column, true);
			return false;
		}
		if (e.column.name == "price" && e.value == 0){
			//$.ligerDialog.warn('单价不能为0！');
			grid.setCellEditing(e.record, e.column, true);
			return false;
		}
		if (e.column.name == "sn"){
			if(e.record.is_bar == 1 && e.value == ""){
				//$.ligerDialog.warn('按条码管理的药品必须输入条形码！');
				grid.setCellEditing(e.record, e.column, true);
				return false;
			}
		}
		if (e.column.name == "inva_date"){
			if(e.record.is_quality == 1 && e.value == ""){
				//$.ligerDialog.warn(按保质期管理的药品必须输入有效日期！');
				grid.setCellEditing(e.record, e.column, true);
				return false;
			}
		}
		if (e.column.name == "disinfect_date"){
			if(e.record.is_disinfect == 1 && e.value == ""){
				//$.ligerDialog.warn('行灭菌药品必须输入灭菌日期！');
				grid.setCellEditing(e.record, e.column, true);
				return false;
			}
		}
		return true;
	}
	// 跳转到下一个单元格之前事件
	function f_onAfterEdit(e) {
		if(e.value != "" ){
			if(e.column.name == "inv_id"){
				if(e.record.is_quality){
					//判断是否为自动有效日期
					if('${p08009 }' != 0){
						grid.updateCell('inva_date', getDateAddDay(new Date(), '${p08009 }'), e.rowindex);
					}
				}
			}else if (e.column.name == "amount"){
				//自动计算金额
				if(e.record.price != undefined && e.record.price != "" ){
					grid.updateCell('amount_money', e.value * e.record.price, e.rowindex);
				}
				//自动计算零售金额
				if(e.record.sell_price != undefined && e.record.sell_price != "" ){
					grid.updateCell('sell_money', e.value * e.record.sell_price, e.rowindex);
				}
				//自动计算批发金额
				if(e.record.sale_price != undefined && e.record.sale_price != "" ){
					grid.updateCell('sale_money', e.value * e.record.sale_price, e.rowindex);
				}
				//自动计算包装件数
				if(e.record.num_exchange != undefined && e.record.num_exchange != "" ){
					grid.updateCell('num', e.value / e.record.num_exchange, e.rowindex);
				}
			}else if (e.column.name == "price"){
				//自动计算金额
				if(e.record.amount != undefined && e.record.amount != "" ){
					grid.updateCell('amount_money', e.value * e.record.amount, e.rowindex);
				}
				//计算包装单价
				if(e.record.num_exchange != undefined && e.record.num_exchange != "" ){
					grid.updateCell('pack_price', e.value * e.record.num_exchange, e.rowindex);
				}
			}else if (e.column.name == "num_exchange"){
				//自动计算包装件数
				if(e.record.amount != undefined && e.record.amount != "" && e.record.amount != 0){
					grid.updateCell('num', e.record.amount / e.value, e.rowindex);
				}
				//自动包装单价
				if(e.record.price != undefined && e.record.price != "" ){
					grid.updateCell('pack_price', e.record.price * e.value, e.rowindex);
				}
			}else if (e.column.name == "num"){
				//自动计算数量与金额
				if(e.record.num_exchange != undefined && e.record.num_exchange != "" ){
					grid.updateCell('amount', e.value * e.record.num_exchange, e.rowindex);
					if(e.record.price != undefined && e.record.price != "" ){
						grid.updateCell('amount_money', e.record.amount * e.record.price, e.rowindex);
					}
					if(e.record.sell_price != undefined && e.record.sell_price != "" ){
						grid.updateCell('sell_money', e.record.amount * e.record.sell_price, e.rowindex);
					}
					if(e.record.sale_money != undefined && e.record.sale_money != "" && e.record.sale_money != 0){
						grid.updateCell('sale_price', e.record.sale_money / e.record.amount, e.rowindex);
					}else if(e.record.sale_price != undefined && e.record.sale_price != "" ){
						grid.updateCell('sale_money', e.record.amount * e.record.sale_price, e.rowindex);
					}
				}
			}else if (e.column.name == "sell_price"){
				//自动计算零售金额
				if(e.record.amount != undefined && e.record.amount != "" ){
					grid.updateCell('sell_money', e.value * e.record.amount, e.rowindex);
				}
			}else if (e.column.name == "sale_price"){
				//自动计算批发金额
				if(e.record.amount != undefined && e.record.amount != "" ){
					grid.updateCell('sale_money', e.value * e.record.amount, e.rowindex);
				}
			}else if (e.column.name == "sale_money"){
				//自动计算批发单价
				if(e.record.amount != undefined && e.record.amount != "" && e.record.amount != 0){
					grid.updateCell('sale_price', e.value / e.record.amount, e.rowindex);
				}
			}
		}
		grid.updateTotalSummary();
		return true;
	}
	//获取数据
	function getData(){
		var manager = $("#maingrid").ligerGetGridManager();
		var data = manager.getData();
		return JSON.stringify(data);
	} 
	
	//根据data添加明细数量
    function add_rows(data){
    	//grid.removeRange(gridManager.getData());
    	grid.addRows(data);
    }
	
    //移除明细行数据
    function remove(){
    	
    	if(state > 1){
    		$.ligerDialog.error('修改失败，单据不是未审核状态！');
    		return;
    	}
    	grid.deleteSelectedRow();
    }

	//添加空行--flag=init表示页面初始化时加载不应出提示
    function is_addRow(flag){
    	
    	setTimeout(function() {
			grid.addRow();
		}, 1000);
    }

	//审核
	function audit(){
		if(state > 1){
			$.ligerDialog.error("审核失败！"+$("#in_no").val()+"单据不是未审核状态");
			return;
		}else{
			var ParamVo =[];
			ParamVo.push(
					$("#group_id").val()   +"@"+ 
					$("#hos_id").val()   +"@"+ 
					$("#copy_code").val()   +"@"+ 
					$("#in_id").val() 
				) 
			
			$.ligerDialog.confirm('确定审核?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("auditMedAffiInCommon.do?isCheck=true", {ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.state=="true"){
							parentFrameUse().query();
							state  = 2;
							change_button();
					    	loadHead();
					    	grid.reRender();
						}
					});
				}
			});
		}
		
	}
	
	//销审
	function unAudit(){
	
		if(state != 2 ){
			$.ligerDialog.error("销审失败！"+$("#in_no").val()+"单据不是审核状态");
			return;
		}else{
			var ParamVo =[];
			ParamVo.push(
				$("#group_id").val()   +"@"+ 
				$("#hos_id").val()   +"@"+ 
				$("#copy_code").val()   +"@"+ 
				$("#in_id").val() 
			) 
			
			$.ligerDialog.confirm('确定销审?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("unAuditMedAffiInCommon.do?isCheck=true",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.state=="true"){
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
	}
	
	//入库确认
	function confirm_in(){
		if(state != 2 ){
			$.ligerDialog.error("入库确认失败！"+$("#in_no").val()+"单据不是审核状态");
			return;
		}else{
			var ParamVo =[];
			ParamVo.push(
				$("#group_id").val()   +"@"+ 
				$("#hos_id").val()   +"@"+ 
				$("#copy_code").val()   +"@"+ 
				$("#in_id").val() 
			) 
			
			$.ligerDialog.confirm('确定入库确认吗?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("confirmMedAffiInCommon.do?isCheck=true",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.state=="true"){
							parentFrameUse().query();
							state = 3;
							change_button();
					    	loadHead();
					    	grid.reRender();
						}
					});
				}
			});
		}
	}
	
	//维护发票
    function invoice_open(){
    	if(state != 3){
			$.ligerDialog.error("单据未入库确认，该功能不可用");
			return;
		}
    	
    	var para = "group_id=" + $("#group_id").val() + 
		"&hos_id=" + $("#hos_id").val() + 
		"&copy_code=" + $("#copy_code").val() + 
		"&in_id=" + $("#in_id").val() + 
		"&sup_id=" + liger.get("sup_code").getValue() +
		"&sup_text=" + liger.get("sup_code").getText() + 
		"&money_sum=" + $("#money_sum").val();
	
		$.ligerDialog.confirm('确定生成发票?', function (yes){
			if(yes){
				ajaxJsonObjectByUrl("existsMedInDetailByInvoice.do?isCheck=false&"+para, "", function (responseData){
					if(responseData.state=="true"){
						$.ligerDialog.open({
							title: '维护发票',
							height: 550,
							width: 1000,
							url: 'medAffiInCommonVoice.do?isCheck=false&'+para,
							modal: true, showToggle: false, showMax: true, showMin: false, isResize: true, top: 1
						});
					}else{
						$.ligerDialog.warn("该入库单已全部生成发票！");
					}
				});
			}
		}); 
	
    }
	
  	//上一张
    function before_no(){
  	
    	if('${up}' == $("#in_id").val()){
 			$.ligerDialog.confirm('本单据为第一张单据,是否跳转添加单据', function (yes){
				if(yes){
					parentFrameUse().add_open();
					this_close();
				}
 			});
 		}else{
 			var obj = '${group_id}'+","+'${hos_id}'+","+'${copy_code}'+"," + '${up}';
 			parentFrameUse().update_open(obj);
 			this_close();	
 		}
    		
    }
  
    //下一张
	function next_no(){
		if('${next}' == $("#in_id").val()){
			$.ligerDialog.confirm('本单据为最后一张单据,是否跳转添加单据', function (yes){
				if(yes){
					parentFrameUse().add_open();
					this_close();
				}
 			});
 		}else{
 			var obj = '${group_id}'+","+'${hos_id}'+","+'${copy_code}'+"," + '${next}';
 			parentFrameUse().update_open(obj);
 			this_close();
 		}
			
    }
	
	//添加入库单
	function addNew(){
		parentFrameUse().add_open();
    	this_close();
	}
	//条形码
	function sn_imp(){
		if(state > 1){
			$.ligerDialog.error('单据不是未审核状态！');
			return;
		} 	
	}

	//键盘事件
	function loadHotkeys() {
		hotkeys('S', save);//保存
		hotkeys('D', remove);//删除
		hotkeys('A', audit);//审核
		hotkeys('U', unAudit);//销审
		hotkeys('F', confirm_in);//入库确认
		//hotkeys('E', addDetail);//添加药品
		hotkeys('N', addNew);//添加入库单
		
		/* hotkeys('P', printDate);//打印
		hotkeys('M', merge_print);//合并打印
		hotkeys('R', bar_print);//条码打印 */
		hotkeys('L', this_close);//关闭
	}
	
	
	
	//合并打印
	function merge_print(){
		
	}
	
	//条码打印
	function bar_print(){

		var arr = [];
		var data = grid.getCheckedRows();
		if(data==""){

			$.ligerDialog.warn("没有勾选条码打印数据.");  
			return;
		}
		
		$(data).each(function(i,v){
			
			var obj = {
				bar_code:v.bar_code,
				other1:{
					name:"商品信息",
					value:v.inv_name//+(v.fac_name ? +"("+v.fac_name+")" : "")
				},
				other2:{
					name:"单价",
					value:v.sell_price// +" 有效期:"+(v.inva_date==null?" ":v.inva_date)
				},
				other3:{
					name:"采购日期",
					value:$("#in_date").val()==null?" ":$("#in_date").val()
				}
			}

			arr.push(obj);
		});
		lodopBarCode(arr);
	}
	
	//关闭
	function this_close(){
		frameElement.dialog.close();
	}
	
	function change_button(){
		//delete, update_invoice, create_invoice, audit, unAudit, confirm, add_inv, open_add
		if(state == 1){
			$("#save").ligerButton({click: save, width:90, disabled:false});//保存按钮
			//审核按钮
			//销审按钮
			//入库确认按钮
			//维护发票按钮
		}else{
			$("#save").ligerButton({click: save, width:90, disabled:true});
		}
		//alert("printFlag:"+printFlag+"   state:"+state);
		if(printFlag==1 && state == 3){
      		$("#print").ligerButton({click: printDate, width:80, disabled:false});
      		$("#printCollect").ligerButton({click: printCollect, width:80, disabled:false});
      	}else if(printFlag==1 && state != 3){
      		$("#print").ligerButton({click: printDate, width:80, disabled:true});
      		$("#printCollect").ligerButton({click: printCollect, width:80, disabled:true});
      	}else{
      		$("#print").ligerButton({click: printDate, width:80, disabled:false});
      		$("#printCollect").ligerButton({click: printCollect, width:80, disabled:false});
      	}
	}
	
	//业务类型不是代销入库时，采购员、采购类型不显示
	function change(){
		 if(liger.get("bus_type_code").getValue() == '8'){
    		 $(".demo").attr("style","visibility:hidden");
    		 $("#stocker").ligerComboBox({disabled:true,cancelable: false});
    		 liger.get("stocker").setValue(""); 
 			 liger.get("stocker").setText("");
    		 $("#stock_type_code").ligerComboBox({disabled:true,cancelable: false});
    		 liger.get("stock_type_code").setValue(""); 
 			 liger.get("stock_type_code").setText("");
    		 
    	 }else{
    		 $(".demo").attr("style","visibility:true");
    		 $("#stocker").ligerComboBox({disabled : false,cancelable: true});
    		 $("#stock_type_code").ligerComboBox({disabled : false,cancelable: true});
    	 }
	}
	
	//打印
	function printDate(){
		
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
		
		var para={
			in_id:$("#in_id").val(),
			template_code:'08017',
			class_name:"com.chd.hrp.med.serviceImpl.affi.in.MedAffiInCommonServiceImpl",
			method_name:"queryMedAffiInByPrintTemlate1",
			isPrintCount:false,//更新打印次数
			isPreview:true,//预览窗口，传绝对路径
			p_num : 0,
			use_id:useId
		};
		
		officeFormPrint(para);
		//printTemplate("queryMedAffiInByPrintTemlate.do",para);
	}
	
	//汇总打印
	function printCollect(){
		
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
		
		/* var para={
			in_id:$("#in_id").val(),
			template_code:'08018',
			p_num : 0,
			use_id:useId, 
			is_collect: 1
		}; */
		
		var para={
				in_id:$("#in_id").val(),
				template_code:'08017',
				class_name:"com.chd.hrp.med.serviceImpl.affi.in.MedAffiInCommonServiceImpl",
				method_name:"queryMedAffiInByPrintTemlate1",
				isPrintCount:false,//更新打印次数
				isPreview:false,//预览窗口，传绝对路径
				p_num : 0,
				use_id:useId,
				is_collect: 1
			};
		
		officeFormPrint(para);
		//printTemplate("queryMedAffiInByPrintTemlate.do",para);
	}
	
	//打印设置
	function printSet(){
		
		
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
		officeFormTemplate({template_code:"08018",use_id:useId});
		/* parent.parent.$.ligerDialog.open({url : 'hrp/med/affi/in/affiInPrintSetPage.do?template_code=08018&use_id='+useId,
			data:{}, height: $(parent).height(),width: $(parent).width(), title:'打印模板设置',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
		}); */
	}
	
   </script>
  
</head>
  
<body onload="is_addRow()">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post"  id="form1" >
		<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" border="0">
			<tr>
				<td style="display: none;">
	            	<input name="group_id" type="text" id="group_id" value="${medAffiIn.group_id}" ltype="text" />
	            	<input name="hos_id" type="text" id="hos_id" value="${medAffiIn.hos_id}" ltype="text" />
	            	<input name="copy_code" type="text" id="copy_code" value="${medAffiIn.copy_code}" ltype="text" />
	            	<input name="in_id" type="text" id="in_id" value="${medAffiIn.in_id}" ltype="text" />
	            	<input name="money_sum" type="text" id="money_sum" value="${medAffiIn.money_sum}" ltype="text" /> 
	            </td>
			</tr>
	        <tr>
	            <td align="right" class="l-table-edit-td" width="10%"><font color="red">*</font>入库单号：</td>
	            <td align="left" class="l-table-edit-td" width="20%">
	            	<input name="in_no" type="text" id="in_no" disable="disabled" value="${medAffiIn.in_no}" ltype="text" />
	            </td>
	            
	            <td align="right" class="l-table-edit-td" width="10%"><font color="red">*</font>业务类型：  </td>
	            <td align="left" class="l-table-edit-td" width="20%">
	            	<input name="bus_type_code" type="text" id="bus_type_code" ltype="text" required="true" onChange="change();" validate="{required:true}" />
	            </td>
	            
	            <td align="right" class="l-table-edit-td"  width="10%"><font color="red">*</font>仓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;库：</td>
	            <td align="left" class="l-table-edit-td" width="20%">
	            	<input name="store_code" type="text" id="store_code" ltype="text" required="true" validate="{required:true}" />
	            </td>
	        </tr> 
	        <tr>
	            <td align="right" class="l-table-edit-td" width="10%"><font color="red">*</font>入库日期：</td>
	            <td align="left" class="l-table-edit-td" width="20%">
	            	<input class="Wdate" name="in_date" id="in_date" type="text"  value="${medAffiIn.in_date}" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
	            </td>
	            
	            <td align="right" class="l-table-edit-td" width="10%"><font color="red">*</font>采&nbsp;&nbsp;购&nbsp;&nbsp;员：</td>
	            <td align="left" class="l-table-edit-td" width="20%">
	            	<input name="stocker" type="text" id="stocker"  ltype="text" required="true" validate="{required:true,maxlength:20}"/>
	            </td>
	            
	            <td align="right" class="l-table-edit-td" width="10%"><font color="red">*</font>采购类型：</td>
	            <td align="left" class="l-table-edit-td" width="20%">
	            	<input name="stock_type_code" type="text" id="stock_type_code" required="true" ltype="text" validate="{required:true}" />
	            </td>
	            
	        </tr> 
	        <tr>
	        	<td align="right" class="l-table-edit-td" width="10%"><font color="red">*</font>供&nbsp;&nbsp;应&nbsp;&nbsp;商：</td>
	            <td align="left" class="l-table-edit-td" width="20%">
	            	<input name="sup_code" type="text" id="sup_code" required="true" ltype="text" validate="{required:false}" />
	            </td>
	            
	            <td align="right" class="l-table-edit-td"  width="10%">申请科室：</td>
	            <td align="left" class="l-table-edit-td" width="20%">
	            	<input name="app_dept" type="text" id="app_dept" ltype="text" validate="{required:false}" />
	            </td>
	            
	            <td align="right" class="l-table-edit-td" width="10%">协议编号：</td>
	            <td align="left" class="l-table-edit-td" width="20%">
	            	<input name="protocol_code" type="text" id="protocol_code" ltype="text" validate="{required:false,maxlength:20}" />
	            </td>
	        </tr>
	        <tr>
	        	<td align="right" class="l-table-edit-td" width="10%">项&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;目：</td>
	            <td align="left" class="l-table-edit-td" width="20%">
	            	<input name="proj_code" type="text" id="proj_code" ltype="text" validate="{required:false,maxlength:20}" />
	            </td>
	            
	            <td align="right" class="l-table-edit-td" width="10%">货&nbsp;&nbsp;单&nbsp;&nbsp;号：</td>
	            <td align="left" class="l-table-edit-td" width="20%">
	            	<input name="delivery_code" type="text" id="delivery_code" disable="disabled" value="${medAffiIn.delivery_code}" ltype="text" />
	            </td>
	            
	            <td align="right" class="l-table-edit-td"  width="10%">验&nbsp;&nbsp;收&nbsp;&nbsp;员：</td>
	            <td align="left" class="l-table-edit-td" width="20%">
	            	<input name="examiner" type="text" id="examiner" ltype="text" validate="{required:false}" />
	            </td>
	        </tr>
	        <tr>   
	            <td align="right" class="l-table-edit-td" width="10%">
					摘&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;要：
	            </td>
	            <td align="left" class="l-table-edit-td" width="20%">
	            	<input name="brief" type="text" id="brief" ltype="text" value="${medAffiIn.brief}" validate="{required:true,maxlength:50}" />
	            </td>
	        </tr>
	    </table>
    </form>
    <div style="width: 100%; height: 100%;">
		<div id="maingrid"></div>
		<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%"  style="margin-top: 5px;">
			<tr>	
				<td align="center" class="l-table-edit-td" >
					制单人：${medAffiIn.maker_name}
				</td>
				<td align="center" class="l-table-edit-td" >
					审核人：${medAffiIn.checker_name}
				</td>
				<td align="center" class="l-table-edit-td" >
					确认人：${medAffiIn.confirmer_name}
				</td>
			</tr>
			<tr>	
				<td align="center" class="l-table-edit-td" colspan="3">
					<button id ="save" accessKey="B"><b>保存（<u>B</u>）</b></button>
					&nbsp;&nbsp;
					<!-- <button id ="print" accessKey="P"><b>打印（<u>P</u>）</b></button>
					&nbsp;&nbsp;
					<button id ="merge_print" accessKey="M"><b>合并打印（<u>M</u>）</b></button>
					&nbsp;&nbsp; -->
					<button id ="bar_print" accessKey="R"><b>条码打印（<u>R</u>）</b></button>
					&nbsp;&nbsp;
					<button id ="print" accessKey="P"><b>打印（<u>P</u>）</b></button>
					&nbsp;&nbsp;
					<button id ="printCollect" accessKey="H"><b>汇总打印（<u>H</u>）</b></button>
					&nbsp;&nbsp; 
					<button id ="printSet" accessKey="M"><b>打印模板（<u>M</u>）</b></button>
					&nbsp;&nbsp;
					<button id ="close" accessKey="L"><b>关闭（<u>L</u>）</b></button>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>
