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
	<script src="<%=path%>/lib/Lodop/barcode.js"	type="text/javascript"></script>
	<script src="<%=path%>/lib/Lodop/LodopFuncs.js"	type="text/javascript"></script>
    <script type="text/javascript">
	var grid;   
	var gridManager;
	var state = '${matInMain.state}';
	var by_sup_inv = '${p04021 }';    
	var by_batch_price = '${p04030 }';
    var flag = 0;//'${flag}';//是否是订单或者送货单生成   0：不是  ；1：是
    var printFlag = '${p04042 }';
	var isUseAffiStore = '${p04044 }' == 1 ? true : false;
	var by_cont_prot = '${p04082 }';
	var is_total_cont =0;
	var is_price_cont =0;
	$(function (){
		loadDict();//加载下拉框 
		initBus();
        //loadForm(); 
		loadHead();
		queryDetail();
		
		$("#bus_type_code").ligerComboBox({cancelable: false });
		
		$("#bus_type_code").bind("change", function () { changeBus(); });
		$("#store_code").bind("change", function () {
			charge_store();
// 			loadHead();
// 			grid.reRender();
			if(!$("#examiner").val()){
				change_Examiner();
			} 
			if(isUseAffiStore){
				grid.columns[6].editor.grid.url = '../../queryMatInvListIn.do?isCheck=false&store_id=' + liger.get("store_code").getValue().split(",")[0] + '&sup_id=' + liger.get("sup_code").getValue().split(",")[0];
			}else{
				grid.columns[6].editor.grid.url = '../../queryMatInvListIn.do?isCheck=false&is_com=0&store_id=' + liger.get("store_code").getValue().split(",")[0] + '&sup_id=' + liger.get("sup_code").getValue().split(",")[0];
			}
			
			grid.reRender();
		});
		$("#sup_code").bind("change", function () {
			var sup_id = liger.get("sup_code").getValue().split(",")[0];
			var in_date = liger.get("in_date").getValue();
			if(sup_id && in_date){
				$.ajax({
					url:"../../getMatPactFkxyInfo.do?isCheck=false",
					data:{
						sup_id:sup_id,
						in_date :in_date	
					},
					type:"post",
					dataType:"json",
					success:function(msg){
						var pactFkxyInfo=msg.pactFkxyInfo?msg.pactFkxyInfo:"";
						
						liger.get("protocol_code").setValue("");
						liger.get("protocol_code").setValue(pactFkxyInfo);
						is_total_cont =msg.is_total_cont?msg.is_total_cont:0;
						is_price_cont =msg.is_price_cont?msg.is_price_cont:0;
					},
					async:false
				})
			}	
			
		});
		change_button();


		$("#barcodePrint").click(function(){
			var arr = [];
			var data = grid.getCheckedRows();
			if(data=="")
			{
			
				$.ligerDialog.warn("没有勾选条码打印数据.");  
				return;
			}
			$(data).each(function(i,v){
				if(v.inv_code){
					var obj = {
							bar_code:v.bar_code,
							other1:{
								name:"商品信息",
								value:v.inv_name//+"("+v.fac_name+")"
							},
							other2:{
								name:"单价",
								value:v.sell_price +" 有效期:"+(v.inva_date==null?" ":v.inva_date)
							},
							other3:{
								name:"采购日期",
								value:$("#in_date").val()==null?" ":$("#in_date").val()
							}
						}

						arr.push(obj); 
				}
				
			});
			lodopBarCode(arr);
		}); 

		 $("#newBarcodePrint").click(function(){
			var arr = [];
			var data = grid.getCheckedRows();
			if(data=="")
			{
				$.ligerDialog.warn("没有勾选条码打印数据.");  
				return;
			}
				
			$(data).each(function(i,v){
				var obj = {
					hospital:"${sessionScope.copy_name}",
					bar_code:v.bar_code,
					info:{
						mat_name:v.inv_name||"",
						spec_model:v.inv_model||"",
						batch:v.batch_no||"",
						date:v.inva_date||"",
					}
				}
				arr.push(obj);
			});
			newLodopBarCode(arr);
		}); 
	});
	

	//获取仓库采购员
	function charge_store(){

    	//liger.get("store_code").clear();
    	liger.get("store_code").set("parms", {store_id: liger.get("store_code").getValue().split(",")[0]});
    	liger.get("store_code").reload();
    	var store_id = liger.get("store_code").getValue().split(",")[0];  
		autocomplete("#stocker", "../../queryMatStockEmpByStore.do?isCheck=false", "id", "text", true, true,{store_id:store_id}, true);
		var manager = liger.get("store_code").getValue().split(",")[4];
		if(manager){
			autocomplete("#examiner", "../../queryMatStockEmp.do?isCheck=false", "id", "text", true, true,{manager : manager}, true);
		}else{
			autocomplete("#examiner", "../../queryMatStockEmp.do?isCheck=false", "id", "text", true, true,{manager : manager}, false);
			liger.get("examiner").setValue("");
			liger.get("examiner").setText("");
		}
		
		/* var emp_id=	liger.get("store_code").getValue().split(",")[2];
		var emp_name=liger.get("store_code").getValue().split(",")[3];
		liger.get("stocker").setValue(emp_id);
		liger.get("stocker").setText(emp_name); */
		
	}
	
	//验收员下拉框
	function change_Examiner(){
		autocomplete("#examiner", "../../queryMatStockEmp.do?isCheck=false", "id", "text", true, true,"", false);
	}
	
	function validateGrid() {  
		//主表
  		if(!liger.get("bus_type_code").getValue()){
  			$.ligerDialog.warn("业务类型不能为空");  
  			return false;  
  		}
  		if(!liger.get("store_code").getValue()){
  			$.ligerDialog.warn("仓库不能为空");  
  			return false;  
  		}
  		if(!$("#in_date").val()){
  			$.ligerDialog.warn("制单日期不能为空");  
  			return false;  
  		}
 		if(liger.get("bus_type_code").getValue() && liger.get("bus_type_code").getValue() == 2){
	 		if(!liger.get("stocker").getValue()){
	 			$.ligerDialog.warn("采购员不能为空");  
	 			return false;  
	 		} 
	 		if(!liger.get("stock_type_code").getValue()){
	 			$.ligerDialog.warn("采购类型不能为空");  
	 			return false;  
	 		} 
	 		if(!liger.get("sup_code").getValue()){
	 			$.ligerDialog.warn("供应商不能为空");  
	 			return false;  
	 		} 
 		}
  		//明细
 		var rowm = "";
  		var msg="";
		var priceMsg = "";
 		var rows = 0;
 		var store_inv = "";  //用于判断是否属于该仓库
 		var sup_inv = ""; //用于判断唯一供应商
 		var certMsg = "";
  		var data = gridManager.getData();
  		var mydate=new Date();
 		//alert(JSON.stringify(data));
  		//判断grid 中的数据是否重复或者为空
  		var targetMap = new HashMap();
  		$.each(data,function(i, v){
  			rowm = "";
 			if (v.inv_id) {
 				
                if(v.inva_date)
               {
				  if(v.inva_date < mydate){
					  msg += "第" + (i + 1) + "行" + "[有效日期]不能小于当前日期。<br>";
				  }
               }
                if(v.fac_date)
                {
				  if(v.fac_date > mydate){
					  msg += "第" + (i + 1) + "行" + "[生产日期]不能大于当前日期。<br>";
				  }
                }
            	
 				
 				//如果批号为空给默认批号用于判断
				if(!v.batch_no){
					v.batch_no = '-';
				}
	 			if (!v.amount) {
	 				rowm+="[数量]、";
	 			}  
	 			if(v.amount<0){
	 				rowm+="[数量]、";
	 			}
	 			if (v.price == "" || v.price == null  || v.price == 'undefined') {  
	 				rowm+="[单价]、"; 
	 			} 
	 			if(rowm != ""){
	 				rowm = "第"+(i+1)+"行" + rowm.substring(0, rowm.length-1) + "不能为空并且数量不能小于或等于0" + "\n\r";
	 			}
	 			msg += rowm;
				if(v.is_bar == 1 && !v.sn){
					msg += "第"+(i+1)+"行按条码管理的材料必须输入条形码。<br>";
				}
		 		if(v.is_quality == 1){
					if(!v.inva_date){
						msg += "第"+(i+1)+"行按保质期管理的材料必须输入有效日期。<br>";
					}/* else{
						if(v.batch_no != "-"){
							//如果材料按保质期管理，则判断有效日期是否与上一批号一致
							var para = {
								inv_id: v.inv_id, 
								batch_no: v.batch_no,  
								inva_date: v.inva_date 
							}
							ajaxJsonObjectByUrl("../../queryMatInvBatchInva.do?isCheck=false", para, function (responseData){
								if(responseData.state=="false"){
									msg += "第"+(i+1)+"行批号"+v.batch_no+"对应的有效日期应为"+responseData.inva_date+"<br>";
								}
							}, false);
						}
					} */
				} 
				if(v.is_disinfect == 1){
					if(!v.disinfect_date){
						msg += "第"+(i+1)+"行灭菌材料必须输入灭菌日期。<br>";
					}/* else{
						if(v.batch_no != "-"){
							//如果材料是灭菌材料，则判断灭菌日期是否与上一批号一致
							var para = {
								inv_id: v.inv_id, 
								batch_no: v.batch_no,  
								disinfect_date: v.disinfect_date 
							}
							ajaxJsonObjectByUrl("../../queryMatInvBatchDisinfect.do?isCheck=false", para, function (responseData){
								if(responseData.state=="false"){
									msg += "第"+(i+1)+"行批号"+v.batch_no+"对应的灭菌日期应为"+responseData.disinfect_date+"<br>";
								}
							}, false);
						}
					} */
				}
				/* 20170519申总要求去掉这个限制~~~~~by hsy~~~
				if(v.batch_no != "-"){
					//同一批号的单价必须一致
					var para = {
						inv_id: v.inv_id, 
						batch_no: v.batch_no,   
						price: v.price 
					}
					ajaxJsonObjectByUrl("../../queryMatInvBatchPrice.do?isCheck=false", para, function (responseData){
						if(responseData.state=="false"){
							priceMsg += "第"+(i+1)+"行批号"+v.batch_no+"对应的单价应为"+responseData.price + ", ";// + ", <br>";
						}
					}, false);
				}
				*/
				if(v.cert_id != null || v.cert_id != ''){
					
					//如果所选材料带有注册证 判断注册证是否过期并提示
					var para = {
						cert_id: v.cert_id
						
					}
					ajaxJsonObjectByUrl("../../queryMatCertDate.do?isCheck=false", para, function (responseData){
						if(responseData.state=="false"){
							certMsg += "第"+(i+1)+"行【证件号："+v.cert_code+"到期日期为"+responseData.end_date+",已过期"+Math.abs(responseData.days)+"天】;";
						}
					}, false);
				}
				//如果条码为空给默认条码用于判断
				if(!v.sn){
					v.sn = '-';
					v.bar_code = '-';
				}
				var key= v.batch_no == '-' ? v.inv_id+"|"+v.price+"|"
						+v.batch_no+"|"+v.sn+"|"+v.location_id+"|"
						+v.bar_code : v.inv_id+"|"+v.batch_no+"|"
						+v.sn+"|"+v.location_id+"|"+v.bar_code+ "|" + v.serial_no;//序列号 serial_no
	 			var value="第"+(i+1)+"行";
	 			if (targetMap.get(key)== null || targetMap.get(key) == 'undefined' || targetMap.get(key) == "") {
					targetMap.put(key, value+v.inv_code+" "+v.inv_name+" "+v.batch_no+" "+v.price);
				} else {
					msg += targetMap.get(key) + "与" + value + (v.batch_no == '-' ? "材料编码、单价、生产批号、条形码、货位不能重复" : "材料编码、生产批号、条形码、货位不能重复") + "<br>";
				}
				rows = rows + 1;
				store_inv += v.inv_id + ",";
				if(v.is_single_ven == 1){
					sup_inv += v.inv_id + ","; 
				}
 			}
  		});
 		if(rows == 0){
 			$.ligerDialog.warn("请先添加材料！");  
			return false;  
 		}
 		if(store_inv.length > 0){
	 		//判断仓库材料关系
			var para = {
				inv_ids: store_inv.substring(0, store_inv.length-1), 
				store_id: liger.get("store_code").getValue().split(",")[0]
			}
			ajaxJsonObjectByUrl("../../existsMatStoreIncludeInv.do?isCheck=false", para, function (responseData){
				if(responseData.state=="false"){
					//$.ligerDialog.warn("材料"+responseData.inv_ids+"不在该仓库中！");  
					//return false;
					msg += "材料"+responseData.inv_ids+"不在该仓库中！<br>";
				}
			}, false);
	 		
			if(by_sup_inv == 1){
				//供应商材料对应关系
				para = {
					inv_ids: store_inv.substring(0, store_inv.length-1), 
					sup_id: liger.get("sup_code").getValue().split(",")[0]
				}
				ajaxJsonObjectByUrl("../../existsMatSupIncludeInv.do?isCheck=false", para, function (responseData){
					if(responseData.state=="false"){
						//$.ligerDialog.warn("材料"+responseData.inv_ids+"不属于该供应商！");  
						//return false;
						msg += "材料"+responseData.inv_ids+"不属于该供应商！<br>";
					}
				}, false);
			}
 		}
		//如果存在唯一供应商的材料则判断是否是唯一供应商
		//东阳  常备材料不需要唯一供应商   gaopei 2017-08-22 
		/* if(sup_inv.length > 0){
			var para = {
				inv_ids: sup_inv.substring(0, sup_inv.length-1), 
				sup_id: liger.get("sup_code").getValue().split(",")[0]
			}
			ajaxJsonObjectByUrl("../../existsMatInvOnlySup.do?isCheck=false", para, function (responseData){
				if(responseData.state=="false"){
					//$.ligerDialog.warn("材料"+responseData.inv_ids+"不符合唯一供应商要求！");  
					//return false;
					msg += "材料"+responseData.inv_ids+"不符合唯一供应商要求！<br>";
				}
			}, false);
		} */
		
		if(certMsg != ""){
       		if(!confirm(certMsg+'是否继续保存？')){
       			
       			return false;
       		}
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
		var protocolCode =liger.get("protocol_code").getValue() == null ? "" : liger.get("protocol_code").getValue();
		
		var work_detail_data=[];
		if(data.length > 0){
 			$.each(data, function(d_index, d_content){
 				if(typeof(d_content.inv_id) != "undefined" && d_content.inv_id != null && d_content.inv_id != ""
 					&& typeof(d_content.amount_money) != "undefined" && d_content.amount_money != null && d_content.amount_money != ""
 				    && typeof(d_content.price) != "undefined" && d_content.price != null && d_content.price != ""
 				){
 					var detailPara={
 	 		        	work_item_code:d_content.inv_id,
 	 		        	work_item_price:d_content.price,
 	 		        	work_item_money:d_content.amount_money
	 				};
 					work_detail_data.push(detailPara);
 				}
 			});
		}
		var protocol_msg = "";
		if(protocolCode != ""){
			if (by_cont_prot ==1){
				if(is_price_cont >0){
					var parm ={
							work_code:protocolCode,	
							work_note:'priceControl',
							work_select_id:'',
							work_update_id:'',	
							work_sup_id:liger.get("sup_code").getValue().split(",")[0],					
							work_detail_data:JSON.stringify(work_detail_data)
					}
					ajaxJsonObjectByUrl("../../../mate/base/queryControlExecProcess.do?isCheck=false",parm,function (responseData){
						if(responseData.work_flag =="5"){
        					$.ligerDialog.error(responseData.work_msg);
        					protocol_msg =responseData.work_msg;
        					return false;
        				}   			
					},false);
				}
				if (protocol_msg !="") return false;
				if(is_total_cont >0){
					var parm ={
							work_code:protocolCode,	
							work_note:'totalControl',
							work_select_id:'',
							work_update_id:'${matInMain.in_id}',	
							work_sup_id:liger.get("sup_code").getValue().split(",")[0],					
							work_detail_data:JSON.stringify(work_detail_data)
					}
					ajaxJsonObjectByUrl("../../../mate/base/queryControlExecProcess.do?isCheck=false",parm,function (responseData){
						if(responseData.work_flag =="5"){
        					$.ligerDialog.error(responseData.work_msg);
        					return false;
        				} 
						else if(responseData.work_flag =="4"){
							if(!confirm(responseData.work_msg)){
								protocol_msg = responseData.work_msg
								return false;
							}
						}
					},false);
				}
				if (protocol_msg!="") return false;
			}
		}
  		return true;	
  	}
    
	function  save(){
		grid.endEdit();
		var inv_datas=gridManager.getData();
		var msg_str="";
		$.each(inv_datas, function (i, v) {
			if(v.is_sec_whg==1){
				msg_str+=v.inv_code+" "+v.inv_name+" ";
			}
		})
		if(msg_str.length>0){
			if(!liger.get("temperature").getValue()){
				$.ligerDialog.warn(msg_str+"是冷链管理材料,需填写验收温度才能成功保存!");
				return;
			}
		}
		
     	if(validateGrid()){
 	        var formPara={
     			in_id : $("#in_id").val(),
 				in_no : $("#in_no").val(),
 				bus_type_code : liger.get("bus_type_code").getValue(),
 				store_id : liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[0],
 				store_no : liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[1],
 				store_code : liger.get("store_code").getText() == null ? "" : liger.get("store_code").getText().split(" ")[0],
 				year : $("#in_date").val().substr(0, 4),
 				month : $("#in_date").val().substr(5, 2),
 				in_date : $("#in_date").val(),
 				stocker : liger.get("stocker").getValue() == null ? "" : liger.get("stocker").getValue(),
 				sup_id : liger.get("sup_code").getValue() == null ? "" : liger.get("sup_code").getValue().split(",")[0],
 				sup_no : liger.get("sup_code").getValue() == null ? "" : liger.get("sup_code").getValue().split(",")[1],
 				stock_type_code : liger.get("stock_type_code").getValue() == null ? "" : liger.get("stock_type_code").getValue(),
 				app_dept : liger.get("app_dept").getValue() == null ? "" : liger.get("app_dept").getValue().split(",")[0],
 				protocol_code : liger.get("protocol_code").getValue() == null ? "" : liger.get("protocol_code").getValue(),
				proj_id : liger.get("proj_code").getValue() == null ? "" : liger.get("proj_code").getValue(),
 				brief : $("#brief").val(),
				bill_no: $("#bill_no").val(),
				bill_date: $("#bill_date").val(),
				delivery_code: $("#delivery_code").val(),
				temperature: $("#temperature").val(),
				examiner: liger.get("examiner").getValue() == null ? "" : liger.get("examiner").getValue(),
 				detailData : JSON.stringify(gridManager.getData())
 			};
 	        ajaxJsonObjectByUrl("updateMatStorageIn.do", formPara, function(responseData){
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
		grid.options.parms.push({
			name : 'in_id',
			value : '${matInMain.in_id}'
		});
		grid.options.parms.push({
			name : 'store_id',
			value : liger.get("store_code").getValue().split(",")[0]
		});

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
    	autocompleteAsync("#bus_type_code", "../../queryMatBusType.do?isCheck=false", "id", "text", true, true, {codes : '2,4,6,8,10,16,18,22,47', is_write:1}, false, "${matInMain.bus_type_code}");
		autocompleteAsync("#store_code", "../../queryMatStoreDictDate.do?isCheck=false", "id", "text", true, true, isUseAffiStore ? {is_write:'1'} : {is_com : 0,is_write:'1'});
		liger.get("store_code").setValue("${matInMain.store_id},${matInMain.store_no}");
		liger.get("store_code").setText("${matInMain.store_code} ${matInMain.store_name}");
		
		//即墨需求   2017/04/06  根据仓库有多个采购员   gaopei
		var store_id = liger.get("store_code").getValue().split(",")[0]; 
		autocomplete("#stocker", "../../queryMatStockEmpByStore.do?isCheck=false", "id", "text", true, true,{store_id:store_id}, false);
		liger.get("stocker").setValue("${matInMain.stocker}");
		liger.get("stocker").setText("${matInMain.stocker_code} ${matInMain.stocker_name}");
		//一个仓库对应一个采购员
		/* autocomplete("#stocker", "../../queryMatStockEmp.do?isCheck=false", "id", "text", true, true);
		liger.get("stocker").setValue("${matInMain.stocker}");
		liger.get("stocker").setText("${matInMain.stocker_code} ${matInMain.stocker_name}"); */
		
		autocompleteAsync("#sup_code", "../../queryHosSupDictDisable.do?isCheck=false", "id", "text", true, true, "", false, false, 300, false, 340);
		liger.get("sup_code").setValue("${matInMain.sup_id},${matInMain.sup_no}");
		liger.get("sup_code").setText("${matInMain.sup_code} ${matInMain.sup_name}");
		
		autocomplete("#stock_type_code", "../../queryMatStockType.do?isCheck=false", "id", "text", true, true, "", false, "${matInMain.stock_type_code}");
		
		autocomplete("#app_dept", "../../queryMatDeptDictDate.do?isCheck=false", "id", "text", true, true, {is_last : 1,is_write:'1'}, false, false, 280);
		liger.get("app_dept").setValue("${matInMain.app_dept}");
		liger.get("app_dept").setText("${matInMain.app_dept_code} ${matInMain.app_dept_name}");
		
		is_price_cont ='${matInMain.protocol_code}' 
		is_total_cont ='${matInMain.protocol_name}' 
		autocomplete("#proj_code", "../../queryMatProj.do?isCheck=false", "id", "text", true, true, "", false, false, 160, false, 240);
		liger.get("proj_code").setValue("${matInMain.proj_id}");
		liger.get("proj_code").setText("${matInMain.proj_code} ${matInMain.proj_name}");
		
		$("#delivery_code").ligerTextBox({width:160});
		/* autocomplete("#examiner", "../../queryMatStockEmp.do?isCheck=false", "id", "text", true, true, '', false); */
			var manager = liger.get("store_code").getValue().split(",")[4];
			if(manager){
				autocomplete("#examiner", "../../queryMatStockEmp.do?isCheck=false", "id", "text", true, true,{manager : manager}, true);
			}else{
				autocomplete("#examiner", "../../queryMatStockEmp.do?isCheck=false", "id", "text", true, true,{manager : manager}, false);
			/* 	liger.get("examiner").setValue("");
				liger.get("examiner").setText(""); */
			}
		 liger.get("examiner").setValue("${matInMain.examiner}");
		liger.get("examiner").setText("${matInMain.examiner_code} ${matInMain.examiner_name}"); 
		$("#examiner").ligerTextBox({width:160});
		
		//格式化文本框
		$("#protocol_code").ligerTextBox({width:320,disabled:true,cancelable: false});
        $("#in_no").ligerTextBox({width:160, disabled:true});
        $("#in_date").ligerTextBox({width:160});
        $("#brief").ligerTextBox({width:320});
        $("#bill_date").ligerTextBox({width:160});
        $("#temperature").ligerTextBox({ width: 160 });
        
       
        //格式化按钮
        if(state > 1){
    		$("#save").ligerButton({click: save, width:80, disabled:true});
        }else{
    		$("#save").ligerButton({click: save, width:80});
        }
      	
        $("#print").ligerButton({click: printDate, width:80});
		$("#printSet").ligerButton({click: printSet, width:90});
        $("#barCodePrintMenu").ligerButton({click: barCodePrintMenu, width:80});
		$("#barCodePrintSet").ligerButton({click: barCodePrintSet, width:90});
		$("#newBarcodePrint").ligerButton({width:90});
		$("#mergePrint").ligerButton({click: mergePrint, width:90});
		$("#barcodePrint").ligerButton({width:90});
        $("#invTypePrint").ligerButton({click: invTypePrint, width:80});
		$("#close").ligerButton({click: this_close, width:80});
		
		$("#btn_saveColumn").ligerButton( {width:80});
		$("#app_dept").ligerTextBox({ width: 160 });
		$("#sup_code").ligerTextBox({ width: 200 });
		
		if(${matInMain.count == 0}){
			$("#bill_no").ligerTextBox({width:160});
		}else{
			$("#bill_no").ligerTextBox({width:160, disabled:true});
		}   
    } 
    
    function initBus(){
    	if("${matInMain.bus_type_code}" != 2 && "${matInMain.bus_type_code}" != 22){
    		$("#stocker_span").css("display", "none");
			$("#stocker").ligerComboBox({width:160,disabled:true,cancelable: false});

    		$("#stock_type_code_span").css("display", "none");
			$("#stock_type_code").ligerComboBox({width:160,disabled:true,cancelable: false});

    		$("#sup_code_span").css("display", "none");
			$("#sup_code").ligerComboBox({width:300,disabled:true,cancelable: false});
		}else{
    		$("#stocker_span").css("display", "inline");
			$("#stocker").ligerComboBox({width:160,disabled:false});

    		$("#stock_type_code_span").css("display", "inline");
			$("#stock_type_code").ligerComboBox({width:160,disabled:false});

    		$("#sup_code_span").css("display", "inline");
			$("#sup_code").ligerComboBox({width:300,disabled:false});
		}
    }
    
    function changeBus() {
		if (liger.get("bus_type_code").getValue() != 2 && liger.get("bus_type_code").getValue() != 22) {
			$("#stocker_span").css("display", "none");
			$("#stocker").ligerComboBox({ width: 160, disabled: true, cancelable: false });
			liger.get("stocker").setValue("");
			liger.get("stocker").setText("");

			$("#stock_type_code_span").css("display", "none");
			$("#stock_type_code").ligerComboBox({ width: 160, disabled: true, cancelable: false });
			liger.get("stock_type_code").setValue("");
			liger.get("stock_type_code").setText("");

			$("#sup_code_span").css("display", "none");
			$("#sup_code").ligerComboBox({ width: 300, disabled: true, cancelable: false });
			liger.get("sup_code").setValue("");
			liger.get("sup_code").setText("");
		} else {
			$("#stocker_span").css("display", "inline");
			$("#stocker").ligerComboBox({ width: 160, disabled: false });
			var emp_id=	liger.get("store_code").getValue().split(",")[2];
			var emp_name=liger.get("store_code").getValue().split(",")[3];
			liger.get("stocker").setValue(emp_id);
			liger.get("stocker").setText(emp_name);

			$("#stock_type_code_span").css("display", "inline");
			$("#stock_type_code").ligerComboBox({ width: 160, disabled: false });
			liger.get("stock_type_code").selectValue(liger.get("stock_type_code").data[0].id);

			$("#sup_code_span").css("display", "inline");
			$("#sup_code").ligerComboBox({ width: 300, disabled: false });
			liger.get("sup_code").selectValue(liger.get("sup_code").data[0].id);
		}
	}
	
	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [{
				display : '交易编码', name : 'bid_code', width : 100, align : 'left'
			},{
				display : '明细ID', name : 'in_detail_id', width : 100, align : 'left', isAllowHide: false, hide: true
			}, {
				display : '材料变更号', name : 'inv_no', width: 100, align : 'left', isAllowHide: false, hide: true
			}, {
				display : '材料编码', name : 'inv_code', width : 100, align : 'left',
				totalSummary: {
                    align : 'right',
                    render: function (suminf, column, cell) {
                    	return '<div>合计：</div>';
                    }
                }
			}, {
				display : '材料名称(E)', name : 'inv_id', textField : 'inv_name', width : 240, align : 'left',				
				editor : flag != 0 ? {} :{
					type : 'select',
					valueField : 'inv_id',
					textField : 'inv_name',
					selectBoxWidth : '80%',
					selectBoxHeight : 240,
					grid : {
						columns : [ {
							display : '交易编码', name : 'bid_code', width : 100, align : 'left'
						}, {
							display : '材料编码', name : 'inv_code', width : 100, align : 'left'
						}, {
							display : '材料名称', name : 'inv_name', width : 240, align : 'left'
						}, { 
							display : '别名', name : 'alias', width : 120, align : 'left'
						}, {
							display : '物资类别', name : 'mat_type_name', width : 100, align : 'left'
						}, {
							display : '规格型号', name : 'inv_model', width : 180, align : 'left'
						}, {
							display : '计量单位', name : 'unit_name', width : 60, align : 'left'
						}, {
							display : '包装规格', name : 'inv_structure', width : 90, align : 'left'
						}, {
							display : '生产厂商', name : 'fac_name', width : 100, align : 'left'
						}, {
							display : '计划单价', name : 'plan_price', width : 90, align : 'left',
							render : function(rowdata, rowindex, value) {
								return formatNumber(value, '${p04006 }', 1);
							}
						}, {
							display : '是否条码', name : 'is_bar', width : 60, align : 'left',
							render : function(rowdata, rowindex, value) {
								return rowdata.is_bar == 1 ? '是' : '否';
							}
						}, {
							display : '货位', name : 'location_new_name', width : 100, align : 'left'
						}, {
							display : '零售价', name : 'sell_price', width : 90, align : 'left',
							render : function(rowdata, rowindex, value) {
								return formatNumber(value, '${p04072 }', 1);
							}
						}, {
							display : '包装单位', name : 'pack_name', width : 80, align : 'left'
						}, {
							display : '转换量', name : 'num_exchange', width : 80, align : 'left'
						}, {
							display : '存储编码', name : 'memory_encoding', width : 100, align : 'left'
						} ],
						switchPageSizeApplyComboBox : false,
						onSelectRow: function (data) {
							var e = window.event;
							if (e && e.which == 1) {
								f_onSelectRow_detail(data);
							}
						},
						gid: "invGrid", 
						url : '../../queryMatInvListIn.do?isCheck=false&store_id=' 
								+ liger.get("store_code").getValue().split(",")[0]
								+'&sup_id=' + liger.get("sup_code").getValue().split(",")[0]
								+ '&is_com=0',
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
					delayLoad : true, keySupport : true, autocomplete : true,
					onSuccess : function() {
						this.parent("tr").next(".l-grid-row").find("td:first").focus();
					},
					ontextBoxKeyEnter: function (data) {
						f_onSelectRow_detail(data.rowdata);
					}
				},
				render : function(rowdata, rowindex, value) {
					//控制如果不是条码管理材料不能编辑条码 
					
					if(rowdata.is_bar==0){
						rowdata.notEidtColNames.push("sn");
					}
					return rowdata.inv_name;
				}
			}, {
				display : '规格型号', name : 'inv_model', width : 180, align : 'left'
			}, {
				display : '计量单位', name : 'unit_name', width : 60, align : 'left'
			}, {
				display: '生产厂商', name: 'fac_name', width: 180, align: 'left'
			},{
				display : '数量(E)', name : 'amount', width : 80, type : 'float', align : 'right' ,
				
				editor :   flag != 0 ? {} :{
					type: 'float'
				} /* ,
				totalSummary: {
					align : 'right',
                    render: function (suminf, column, cell) {
                        return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, 2, 1)+ '</div>';
                    }
                } */
			}, {
				display : '单价(E)', name : 'price', width : 90, align : 'right', 
				editor : {
					type : 'numberbox',
					precision : '${p04006 }'
				},
				render : function(rowdata, rowindex, value) {
					rowdata.price = value == null ? "" : formatNumber(value, '${p04006 }', 0);
					return value == null ? "" : formatNumber(value, '${p04006 }', 1);
				}
			}, {
				display : '金额(E)', name : 'amount_money', width : 90, align : 'right',
				editor : {
					type : 'numberbox',
					precision : '${p04006 }'
				},
				render : function(rowdata, rowindex, value) {
					rowdata.amount_money = value == null ? "" : formatNumber(value, '${p04005 }', 0);
					return value == null ? "" : formatNumber(value, '${p04005 }', 1);
				},
				totalSummary: {
					align : 'right',
                    render: function (suminf, column, cell) {
                        return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, '${p04005 }', 1)+ '</div>';
                    }
                } 
			},{
				display: '注册证号(E)', name : 'cert_id',  textField : 'cert_code',width : 300, align : 'left',
				 render : function(rowdata, rowindex, value) {
						return rowdata.cert_code;
					},
				 editor : {
						type : 'select',
						valueField : 'code',
						textField : 'name',
						selectBoxWidth : 250,
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
				
			},{
				display: '生产日期(E)', name: 'fac_date', type: 'date', align: 'left', format: 'yyyy-MM-dd', width: 90,
				editor: {
					type: 'date',
				}
			},{
				display : '生产批号(E)', name : 'batch_no', width : 100, align : 'left',
				editor : {
					type : 'text',
				}
			}, {
				display : '批次', name : 'batch_sn', width : 60, align : 'left',
			}, {
				display : '有效日期(E)', name : 'inva_date', type: 'date', align : 'left', format: 'yyyy-MM-dd', width : 90,
				editor : {
					type : 'date',
				},
			}, {
				display : '灭菌日期(E)', name : 'disinfect_date', type: 'date', align : 'left', format: 'yyyy-MM-dd', width : 90,
				editor : {
					type : 'date',
				},
			}, {
				display: '灭菌批号(E)', name: 'disinfect_no', width: 100, align: 'left',
				editor: {
					type: 'text',
				}
			}, {
				display : '条形码(E)', name : 'sn', width : 120, align : 'left',
				editor : {
					type : 'text',
				}
			}, {
				display : '是否个体码', name : 'is_per_bar', width : 90, align : 'left',
				render : function(rowdata, rowindex, value) {
					if(value == 0){
						return "否";
					}else if(value == 1){
						return "是";
					}else{
						rowdata.is_per_bar = 0;
						return "否";
					}
				}
			}, {
				display: '是否冷链管理', name: 'is_sec_whg', width: 100, align: 'left',
				render: function (rowdata, rowindex, value) {
					if (value == 0) {
						return "否";
					} else if (value == 1) {
						return "是";
					} else {
						rowdata.is_sec_whg = 0;
						return "否";
					}
				}
			}, {
				display : '院内码', name : 'bar_code', width : 120, align : 'left',
			}, {
				display : '包装单位(E)', name : 'pack_code', textField : 'pack_name', width : 90, align : 'left',
				editor : {
					type : 'select',
					valueField : 'id',
					textField : 'text',
					url : '../../queryMatHosPackage.do?isCheck=false',
					keySupport : true,
					autocomplete : true,
				},
			}, {
				display : '转换量(E)', name : 'num_exchange', width : 90, type : 'float', align : 'left',
				editor : {
					type : 'float',
				}
			}, {
				display : '包装件数(E)', name : 'num', width : 90, type : 'float', align : 'left',
				editor : {
					type : 'float',
				},
			}, {
				display : '包装单价', name : 'pack_price', width : 90, align : 'right',
				render : function(rowdata, rowindex, value) {
					rowdata.pack_price = value == null ? "" : formatNumber(value, '${p04006 }', 0);
					return value == null ? "" : formatNumber(value, '${p04006 }', 1);
				}
			}, {
				display : '批发单价(E)', name : 'sale_price', width : 90, align : 'right', 
				editor : {
					type : 'numberbox',
					precision : '${p04006 }'
				},
				render : function(rowdata, rowindex, value) {
					rowdata.sale_price = value == null ? "" : formatNumber(value, '${p04006 }', 0);
					return value == null ? "" : formatNumber(value, '${p04006 }', 1);
				}
			}, {
				display : '批发金额', name : 'sale_money', width : 90, align : 'right',
				render : function(rowdata, rowindex, value) {
					rowdata.sale_money = value == null ? "" : formatNumber(value, '${p04005 }', 0);
					return value == null ? "" : formatNumber(value, '${p04005 }', 1);
				},
				totalSummary: {
					align : 'right',
                    render: function (suminf, column, cell) {
                        return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, '${p04005 }', 1)+ '</div>';
                    }
                }
			}, {
				display : '零售单价(E)', name : 'sell_price', width : 90, align : 'right', 
				editor : {
					type : 'numberbox',
					precision : '${p04072 }'
				},
				render : function(rowdata, rowindex, value) {
					rowdata.sell_price = value == null ? "" : formatNumber(value, '${p04072 }', 0);
					return value == null ? "" : formatNumber(value, '${p04072 }', 1);
				}
			}, {
				display : '零售金额', name : 'sell_money', width : 90, align : 'right',
				render : function(rowdata, rowindex, value) {
					rowdata.sell_money = value == null ? "" : formatNumber(value, '${p04073 }', 0);
					return value == null ? "" : formatNumber(value, '${p04073 }', 1);
				},
				totalSummary: {
					align : 'right',
                    render: function (suminf, column, cell) {
                        return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, '${p04073 }', 1)+ '</div>';
                    }
                }
			}, {
				display : '货位名称', name : 'location_name', width : 140, align : 'left',hide: true
				/*  textField : 'location_name', width : 80, align : 'left',
				editor : {
					type : 'select',
					valueField : 'id',
					textField : 'text',
					url : '../../queryMatLocationDict.do?isCheck=false',
					keySupport : true,
					autocomplete : true, 
				}*/
			},{
				display : '序列号', name : 'serial_no', width : 100, align : 'left',
				editor : {
					type : 'text',
				}
			}, {
				display : '备注(E)', name : 'note', width : 240, align : 'left',
				editor : {
					type : 'text',
				}
			},{
				display : '订单关系', name : 'order_rela', align : 'left', width: 100, isAllowHide: false, hide: true
			},{
				display : '送货单关系', name : 'delivery_rela', align : 'left', width: 100, isAllowHide: false, hide: true
			},
			{
				display: '货位', name: 'location_new_name', width: 240, align: 'left' 
				 
			} ],
			dataAction : 'server', dataType : 'server', usePager : false, url : 'queryMatStorageInDetail.do?isCheck=false', 
			width : '100%', height : '93%', checkbox : true, 
			enabledEdit : (state == 1 ) ? true : false, alternatingRow : true, 
			isAddRow : (flag == 1 ) ? false : true,
			onBeforeEdit : f_onBeforeEdit, onBeforeSubmitEdit : f_onBeforeSubmitEdit, onAfterEdit : f_onAfterEdit,
			onsuccess:function(){
			
				is_addRow();
			},
			isScroll : true, rownumbers : true, delayLoad : true,//初始化明细数据
			selectRowButtonOnly : true,//heightDiff: -10,isAddRow:false,
			toolbar : {
				items : [ {
					text : '删除(<u>D</u>)', id : 'delete', click : remove, icon : 'delete', disabled: (state == 1 ) ? false : true
				}, {
					line : true
				}, {
					text : '审核(<u>S</u>)', id : 'audit', click : audit, icon : 'audit', disabled: state == 1 ? false : true
				}, {
					line : true
				}, {
					text : '消审(<u>X</u>)', id : 'unAudit', click : unAudit, icon : 'unaudit', disabled: state == 2 ? false : true
				}, {
					line : true
				}, {
					text : '入库确认(<u>C</u>)', id : 'confirm', click : confirmData, icon : 'account', disabled: state == 2 ? false : true
				}, {
					line : true
				}, {
					text : '发票补登(<u>W</u>)', id : 'update_invoice', click : update_invoice, icon : 'edit',disabled: state == 3 ? false : true
				}, {
					line : true
				}, {
					text : '生成发票(<u>W</u>)', id : 'create_invoice', click : create_invoice, icon : 'edit',disabled: state == 3 ? false : true
				}, {
					line : true
				/* }, {
					text : '维护发票(<u>W</u>)', id : 'invoice', click : invoice_open, icon : 'edit',disabled: state == 3 ? false : true
				}, {
					line : true */
				}, {
					text : '上一张(<u>B</u>)', id : 'before_no', click : before_no, icon : 'before'
				}, {
					line : true
				}, {
					text : '下一张(<u>N</u>)', id : 'next_no', click : next_no, icon : 'next'
				}, {
					line : true
				}, {
					text : '条形码：<input type="text"/>', id : 'sn_imp', click : sn_imp, icon : 'up'
				}, {
					line : true
				},{ text: '复制材料（<u>M</u>）', id:'copy', click: copy, icon:'copy',disabled: (flag == 0 && state == 1) ? false : true  }
				]
			}
		});
		gridManager = $("#maingrid").ligerGetGridManager(); 
	}
	
	//复制材料
    function copy () {
		var data_copys = grid.getCheckedRows(); 
		if (data_copys.length == 0){
			$.ligerDialog.error('请选择行');
			return;
		}else{
			for(var i = 0, length = data_copys.length; i<length; i++){         // 把数组的每个data都复制一遍，即深复制
				data_copys[i] = $.extend({},data_copys[i]) ;
				data_copys[i].amount = '1';
				data_copys[i].in_detail_id='';  //新加材料明细ID应为空
				data_copys[i].batch_sn='';  //新加材料批次应为空
			}
			grid.addRows(data_copys);
		}
	}
	
	var rowindex_id = "";
	var column_name = "";
	function f_onBeforeEdit(e) {
		rowindex_id = e.rowindex;
		column_name = e.column.name;	
		if(column_name=='cert_id'){
			var certId = grid.getColumnByName("cert_id");
			certId.editor.url='../../queryMatInvCertId.do?isCheck=false&inv_id='+e.record.inv_id;
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
		//alert(JSON.stringify(data)); 
		//回充数据 
		if (selectData != "" || selectData != null) {
			if (column_name == "inv_id") {
				grid.updateRow(rowindex_id, {
					bid_code : data.bid_code,
					inv_code : data.inv_code,
					inv_no : data.inv_no,
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
					location_new_name : data.location_new_name == null ? "" : data.location_new_name,
					sell_price : data.sell_price == null ? "" : data.sell_price,
					fac_name : data.fac_name == null ? "" : data.fac_name,
					cert_id : data.cert_id == null ? "" : data.cert_id,	
					cert_code : data.cert_code == null ? "" : data.cert_code, 
					is_inv_bar : data.is_inv_bar == null ? "" : data.is_inv_bar,	
					is_sec_whg : data.is_sec_whg == null ? "" : data.is_sec_whg,	
					bar_code_new : data.bar_code_new == null ? "" : data.bar_code_new 
				});
				setTimeout(function (){
					grid.endEditToNext();
				},500)
				//
			}
		}
		
		return true;
	}
	function f_onSelectRow(data, rowindex, rowobj) {
		return true;
	}
	// 编辑单元格提交编辑状态之前作判断限制
	function f_onBeforeSubmitEdit(e) {
		/*
		if (e.column.name == "inv_id" && !e.value){
			//$.ligerDialog.warn('请选择材料！');
			return false;
		}
		if (e.column.name == "amount" && !e.value){
			//$.ligerDialog.warn('数量不能为0！');
			return false;
		}
		if (e.column.name == "price" && !e.value){
			//$.ligerDialog.warn('单价不能为0！');
			return false;
		}
		if (e.column.name == "sn"){
			if(e.record.is_bar == 1 && !e.value){
				//$.ligerDialog.warn('按条码管理的材料必须输入条形码！');
				return false;
			}
		}
		if (e.column.name == "inva_date"){
			if(e.record.is_quality == 1 && !e.value){
				//$.ligerDialog.warn(按保质期管理的材料必须输入有效日期！');
				return false;
			}
		}
		if (e.column.name == "disinfect_date"){
			if(e.record.is_disinfect == 1 && !e.value){
				//$.ligerDialog.warn('行灭菌材料必须输入灭菌日期！');
				return false;
			}
		}
		*/
		return true;
	}
	// 跳转到下一个单元格之前事件
	function f_onAfterEdit(e) {
		if(e.value != ""){
			if(e.column.name == "inv_id"){
				if(e.record.is_quality){
					//判断是否为自动有效日期
					if('${p04009 }' != 0){
						grid.updateCell('inva_date', getDateAddDay(new Date(), '${p04009 }'), e.rowindex);
					}
				}
				if(e.record.is_inv_bar){
					//条形码自动默认为材料的品种码
					grid.updateCell('sn', e.record.bar_code_new, e.rowindex);
				}
				if(e.record.amount != undefined && e.record.amount != ""){
					//数量不为空自动计算金额
					if(e.record.price != undefined && e.record.price != ""){
						grid.updateCell('amount_money', e.record.amount * e.record.price, e.rowindex);
					}
					if(e.record.sell_price != undefined && e.record.sell_price != ""){
						grid.updateCell('sell_money', e.record.amount * e.record.sell_price, e.rowindex);
					}
					if(e.record.sale_price != undefined && e.record.sale_price != ""){
						grid.updateCell('sale_money', e.record.amount * e.record.sale_price, e.rowindex);
					}
				}
				//材料更改应重置材料批次
				grid.updateCell('batch_sn', "", e.rowindex);
			}else if (e.column.name == "amount"){
				//自动计算金额
				if(e.record.price != undefined && e.record.price != ""){
					grid.updateCell('amount_money', e.value * e.record.price, e.rowindex);
				}
				//自动计算零售金额
				if(e.record.sell_price != undefined && e.record.sell_price != ""){
					grid.updateCell('sell_money', e.value * e.record.sell_price, e.rowindex);
				}
				//自动计算批发金额
				if(e.record.sale_price != undefined && e.record.sale_price != ""){
					grid.updateCell('sale_money', e.value * e.record.sale_price, e.rowindex);
				}
				//自动计算包装件数
				if(e.record.num_exchange != undefined && e.record.num_exchange != "" ){
					grid.updateCell('num', e.record.num_exchange ? e.value / e.record.num_exchange : 0, e.rowindex);
				}
			}else if (e.column.name == "price"){
				//自动计算金额
				if(e.record.amount != undefined && e.record.amount != ""){
					grid.updateCell('amount_money', e.record.amount ? e.value * e.record.amount : 0, e.rowindex);
				}
				//计算包装单价
				if(e.record.num_exchange != undefined && e.record.num_exchange != ""){
					grid.updateCell('pack_price', e.value * e.record.num_exchange, e.rowindex);
				}
			}else if (e.column.name == "num_exchange"){
				//自动计算包装件数
				if(e.record.amount != undefined && e.record.amount != ""){
					grid.updateCell('num', e.value ? e.record.amount / e.value : 0, e.rowindex);
				}
				//自动包装单价
				if(e.record.price != undefined && e.record.price != ""){
					grid.updateCell('pack_price', e.record.price * e.value, e.rowindex);
				}
			}else if (e.column.name == "num"){
				//自动计算数量与金额
				if(e.record.num_exchange != undefined && e.record.num_exchange != ""){
					grid.updateCell('amount', e.value * e.record.num_exchange, e.rowindex);
					if(e.record.price != undefined && e.record.price != ""){
						grid.updateCell('amount_money', e.record.amount * e.record.price, e.rowindex);
					}
					if(e.record.sell_price != undefined && e.record.sell_price != ""){
						grid.updateCell('sell_money', e.record.amount * e.record.sell_price, e.rowindex);
					}
					if(e.record.sale_money != undefined && e.record.sale_money != ""){
						grid.updateCell('sale_price',  e.record.amount ? e.record.sale_money / e.record.amount : 0, e.rowindex);
					}else if(e.record.sale_price != undefined && e.record.sale_price != ""){
						grid.updateCell('sale_money', e.record.amount * e.record.sale_price, e.rowindex);
					}
				}
			}else if (e.column.name == "sell_price"){
				//自动计算零售金额
				if(e.record.amount != undefined && e.record.amount != ""){
					grid.updateCell('sell_money', e.value * e.record.amount, e.rowindex);
				}
			}else if (e.column.name == "sale_price"){
				//自动计算批发金额
				if(e.record.amount != undefined && e.record.amount != ""){
					grid.updateCell('sale_money', e.value * e.record.amount, e.rowindex);
				}
			}else if (e.column.name == "sale_money"){
				//自动计算批发单价
				if(e.record.amount != undefined && e.record.amount != ""){
					grid.updateCell('sale_price', e.record.amount ? e.value / e.record.amount : 0, e.rowindex);
				}
			}else if (e.column.name == "amount_money"){
				if (e.record.price != undefined && e.record.price != "" && e.record.price != 0) {
					grid.updateCell('amount', e.record.price ? division(e.value , e.record.price,2) : 0, e.rowindex);
				} 
				//自动计算金额
				if(e.record.amount != undefined && e.record.amount != ""){
					if(formatNumber(e.record.price,2,-1)!=formatNumber(e.record.amount ? e.value / e.record.amount : 0,2,-1))
					{
					grid.updateCell('price', e.record.amount ? e.value / e.record.amount : 0, e.rowindex);
					}
				}
				//计算包装单价
				if(e.record.num_exchange != undefined && e.record.num_exchange != ""){
					grid.updateCell('pack_price', e.record.amount * e.record.num_exchange ? e.value / e.record.amount * e.record.num_exchange : 0, e.rowindex);
				}
			}else if (e.column.name == "batch_no"){
				//批号更改应重置材料批次
				grid.updateCell('batch_sn', "", e.rowindex);
			}else if (e.column.name == "sn"){
				//修改条形码需要重置院内码
				grid.updateCell('bar_code', "", e.rowindex);
			}
		}
		grid.updateTotalSummary();
		return true;
	}
	
	//获取明细数据
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

	//当单据未审核 默认新增一行
    function is_addRow(){
    	if(state > 1){
	    	return;
    	}
    	if(flag == 1){
    		return;
    	}
    	setTimeout(function() {
			grid.addRow();
		}, 1000);
    }
    
    //审核
	function audit(){

		if(state != 1){
			$.ligerDialog.error("审核失败！单据不是未审核状态");
			return;
		}
		var formPara={
 			group_id : $("#group_id").val(),
     		hos_id : $("#hos_id").val(),
     		copy_code : $("#copy_code").val(),
     		in_id : $("#in_id").val()
 		};
		$.ligerDialog.confirm('确定审核?', function (yes){
			if(yes){
				ajaxJsonObjectByUrl("auditMatStorageIn.do", formPara, function (responseData){
					if(responseData.state=="true"){
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
	
    //消审
	function unAudit(){
		
		if(state != 2){
			$.ligerDialog.error("消审失败！单据不是审核状态");
			return;
		}
		var formPara={
 			group_id : $("#group_id").val(),
     		hos_id : $("#hos_id").val(),
     		copy_code : $("#copy_code").val(),
     		in_id : $("#in_id").val()
 		};
		$.ligerDialog.confirm('确定消审?', function (yes){
			if(yes){
				ajaxJsonObjectByUrl("unAuditMatStorageIn.do", formPara, function (responseData){
					if(responseData.state=="true"){
						parentFrameUse().query();
						state = 1;
						change_button();
				    	loadHead();
				    	grid.reRender();
				    	is_addRow();
					}
				});
			}
		}); 
	}
    
    //确认
    function confirm_in(){
    	var is_store='${p04045 }';
    	if(state != 2){
			$.ligerDialog.error("入库确认失败！单据不是审核状态");
			return;
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
			confirmData(today);
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
							confirmData(confirmDate)
						}
					}},
	                { text: '取消', onclick: function (item, dialog) { dialog.close(); } }
			]
			})
		}
		
	}
    function confirmData(){
    	var is_store='${p04045 }';
    	var ParamVo = [];
    	
    	/* 1.开始   说明：后期可用于维护用户输入日期操作 */
		var confirmDate = $("#in_date").val(); 
		/* 1.结束 */
    	
		var formPara={
 			group_id : $("#group_id").val(),
     		hos_id : $("#hos_id").val(),
     		copy_code : $("#copy_code").val(),
     		in_id : $("#in_id").val(),
     		confirm_date : confirmDate
 		}; 
		ParamVo.push(
				$("#group_id").val() +"@"+
				$("#hos_id").val() +"@"+
				$("#copy_code").val() +"@"+
				$("#in_id").val() +"@"+
				confirmDate +"@"+
				is_store +"@"+
				liger.get("store_code").getValue().split(",")[0] +"@"+
				liger.get("in_no").getValue()
				)
		$.ligerDialog.confirm('确定入库确认?', function (yes){
			if(yes){
				ajaxJsonObjectByUrl("verifyMatClosingDate.do?isCheck=false", {ParamVo : ParamVo.toString()}, function(responseData){
					if (responseData.state == "true") {
						ajaxJsonObjectByUrl("confirmMatStorageIn.do", formPara, function (responseData){
							if(responseData.state=="true"){
								parentFrameUse().query();
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
    //添加材料
    function add_inv(){
    	
		if(state > 1){
			$.ligerDialog.error('单据不是未审核状态！');
			return;
		}
    	var para = "store_id=" + liger.get("store_code").getValue() +
			"&store_text=" + liger.get("store_code").getText();
		$.ligerDialog.open({
			title: '添加材料',
			height: 550,
			width: 1000,
			url: 'matchImpPage.do?isCheck=false&'+para,
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true, top: 1
		});
    }
    
	//更新发票号
    function update_invoice(){
		
    	if(state != 3){
			$.ligerDialog.error("单据未入库确认，该功能不可用");
			return;
		}
    	var para = "group_id=" + $("#group_id").val() + "&hos_id=" + $("#hos_id").val() + 
    		"&copy_code=" + $("#copy_code").val() + "&in_id=" + $("#in_id").val() + 
    		"&in_date=" + $("#in_date").val() + "&show_history=" + ${p03001 };
    	$.ligerDialog.open({
			title: '更新发票号',
			height: 300,
			width: 500,
			url: 'updateInvoicePage.do?isCheck=false&'+para,
			modal: true, showToggle: false, showMax: false, showMin: false, isResize: true, top: "20%",
		});
    }
	
	function updateBill(bill_no, bill_date){
		$("#bill_no").val(bill_no);
		$("#bill_date").val(bill_date);
	}
	
	//生成发票
    function create_invoice(){
    	if(state != 3){
			$.ligerDialog.error("单据未入库确认，该功能不可用");
			return;
		}
    	var para = "group_id=" + $("#group_id").val() + 
			"&hos_id=" + $("#hos_id").val() + 
			"&copy_code=" + $("#copy_code").val() + 
			"&in_id=" + $("#in_id").val();
    	
    	$.ligerDialog.confirm('确定生成发票?', function (yes){
			if(yes){
				ajaxJsonObjectByUrl("addMatBillByInvoice.do?isCheck=false&"+para, "", function (responseData){});
			}
		}); 
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
    		"&money_sum=${matInMain.amount_money}";
    	
    	$.ligerDialog.confirm('确定生成发票?', function (yes){
			if(yes){
				ajaxJsonObjectByUrl("existsMatInDetailByInvoice.do?isCheck=false&"+para, "", function (responseData){
					if(responseData.state=="true"){
						$.ligerDialog.open({
							title: '维护发票',
							height: 550,
							width: 1000,
							url: 'invoicePage.do?isCheck=false&'+para,
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
    	
		var formPara={
			group_id : $("#group_id").val(),
			hos_id : $("#hos_id").val(),
			copy_code : $("#copy_code").val(),
			in_id : $("#in_id").val(),
			store_id : liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[0],
			bus_type_code : liger.get("bus_type_code").getValue(),
		};
		ajaxJsonObjectByUrl("../../queryMatInBeforeNo.do?isCheck=false",formPara,function(responseData){
			if(responseData.state=="true"){
				parentFrameUse().update_open(responseData.update_para);
				this_close();
			}
		}); 	
    }
	//下一张
	function next_no(){
		var formPara={
			group_id : $("#group_id").val(),
			hos_id : $("#hos_id").val(),
			copy_code : $("#copy_code").val(),
			in_id : $("#in_id").val(),
			store_id : liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[0],
			bus_type_code : liger.get("bus_type_code").getValue(),
		};
		ajaxJsonObjectByUrl("../../queryMatInNextNo.do?isCheck=false",formPara,function(responseData){
			if(responseData.state=="true"){
				parentFrameUse().update_open(responseData.update_para);
				this_close();
			}else{
				$.ligerDialog.confirm('已经是最后一张单据了,是否打开新单据?', function (yes){
					if(yes){
						parentFrameUse().add_open();
				    	this_close();
					}
				}); 
			}
		}); 	
    }
    
    //条码导入
    function sn_imp(){
    	
		if(state > 1){
			$.ligerDialog.error('单据不是未审核状态！');
			return;
		}
    }

	//键盘事件
	function loadHotkeys() {
		hotkeys('S', save);
		hotkeys('D', remove);
		hotkeys('P', printDate);
		hotkeys('M', printSet);
		hotkeys('A', barCodePrintMenu);
		hotkeys('T', barCodePrintSet);
		hotkeys('I', imp);
		hotkeys('C', this_close);
	}
	
	//打印
	function printDate(){
		
		var useId=0;//统一打印
		if('${p04017 }'==1){
			//按用户打印
			useId='${sessionScope.user_id }';
		}else if('${p04017 }'==2){
			//按仓库打印
			if(liger.get("store_code").getValue()==""){
				$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
				return;
			}
			useId=liger.get("store_code").getValue().split(",")[0];
		}
		
	/* 	var para={
			in_id:$("#in_id").val(),
			store_id:liger.get("store_code").getValue().split(",")[0],
			template_code:'04010',
			p_num:0,
			use_id:useId
		}; */
		
	/* 	var isPreview=true;
		if(liger.get("isPreview").getValue()){
			isPreview=false;
		} */
		
		var para={
				template_code: '04010',
				class_name: "com.chd.hrp.mat.serviceImpl.storage.in.MatStorageInServiceImpl",
				method_name: "queryMatInByPrintTemlateNewPrint",
				isSetPrint: false,//是否套打，默认非套打 
				isPreview: '${p04076 }' == 1 ? true : false,//是否预览，默认直接打印
				in_id: $("#in_id").val(),
				store_id: liger.get("store_code").getValue().split(",")[0], 
				print_title: "材料入库单",
				p_num: 0,
				use_id: useId
		}; 
		officeFormPrint(para);
		//printTemplate("queryMatInByPrintTemlate.do?isCheck=false",para);
	}
	//条码打印模板设置
	function barCodePrintSet(){
		var useId=0;//统一打印
		if('${p04017 }'==1){
			//按用户打印
			useId='${sessionScope.user_id }';
		}else if('${p04017 }'==2){
			//按仓库打印
			if(liger.get("store_code").getValue()==""){
				$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
				return;
			}
			useId=liger.get("store_code").getValue().split(",")[0];
		}
		officeFormTemplate({template_code:"041327",use_id:useId});
	}
	//模板条码打印
	function barCodePrintMenu(){
		var useId=0;//统一打印
		if('${p04017 }'==1){
			//按用户打印
			useId='${sessionScope.user_id }';
		}else if('${p04017 }'==2){
			//按仓库打印
			if(liger.get("store_code").getValue()==""){
				$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
				return;
			}
			useId=liger.get("store_code").getValue().split(",")[0];
		}
		var barData=grid.getSelecteds();//选中数据
		var inDetailIdStr="";//明细id主键字符串
		var errDetailIdStr="";//非个体码物资材料信息
		if(barData.length<1){
			$.ligerDialog.error('请选择待生成条码的材料!');
			return;
		}
		for(var i=0;i<barData.length;i++){
			if(barData[i].bar_code==='-'){//如果is_per_bar为1说明为个体码
				if(errDetailIdStr.length!=0){
					errDetailIdStr+=',';
				}
				errDetailIdStr+=barData[i].inv_code+barData[i].inv_name;
			}else{
				if(inDetailIdStr.length!=0){
					inDetailIdStr+=',';
				}
				inDetailIdStr+=barData[i].in_detail_id;
			}
		}
		if(errDetailIdStr.length!=0){//含有非个体码材料
			$.ligerDialog.error('含非个体码材料('+errDetailIdStr+'),无法生成条形码,清重新选择!');
			return;
		}
		var para={
				template_code:'041327',
				class_name:"com.chd.hrp.mat.serviceImpl.storage.in.MatStorageInServiceImpl",
				method_name:"queryMatInBarcodeByPrintTemlateNewPrint",
				isSetPrint:false,//是否套打，默认非套打
				isPreview:false,//是否预览，默认直接打印
				in_id:$("#in_id").val(),
				store_id:liger.get("store_code").getValue().split(",")[0], 
				"inDetailIdStr":inDetailIdStr,
				p_num:0,
				use_id:useId
		}; 
		officeFormPrint(para); 
	}
	
	//打印设置
	function printSet(){
		
		
		var useId=0;//统一打印
		if('${p04017 }'==1){
			//按用户打印
			useId='${sessionScope.user_id }';
		}else if('${p04017 }'==2){
			//按仓库打印
			if(liger.get("store_code").getValue()==""){
				$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
				return;
			}
			useId=liger.get("store_code").getValue().split(",")[0];
		}
		officeFormTemplate({template_code:"04010",use_id:useId});
		/* parent.parent.$.ligerDialog.open({url : 'hrp/mat/storage/in/storageInPrintSetPage.do?template_code=04010&use_id='+useId,
			data:{}, height: $(parent).height(),width: $(parent).width(), title:'打印模板设置',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
		}); */
	}
	
	//合并打印
	function mergePrint(){
    	/* var selPara={};
    	
    	$.each(grid.options.parms,function(i,obj){
    		selPara[obj.name]=obj.value;
    	});
    	 
		var dates = getCurrentDate();
		
    	var cur_date = dates.split(";")[2];
    	//跨所有列:计算列数
    	var colspan_num = grid.getColumns(1).length-1;
    	 
   		var printPara={
   			title:'材料入库合并打印',
   			head:[
				{"cell":0,"value":"单位: ${sessionScope.hos_name}","colspan":colspan_num,"br":true},
   			],
   			foot:[
				{"cell":colspan_num-2,"value":"制单人： ${sessionScope.user_name}","colspan":2,"br":true},
				{"cell":0,"value":"打印日期: " + cur_date,"colspan":colspan_num,"br":true}
   			],
   			columns:grid.getColumns(1),
   			headCount:1,//列头行数
   			autoFile:true,
   			type:3
   		};
   		ajaxJsonObjectByUrl("querymergePrintDetail.do?isCheck=false", selPara, function (responseData) {
   			printGridView(responseData,printPara);
		});
   		  */
		var useId=0;//统一打印
		if('${p04017 }'==1){
			//按用户打印
			useId='${sessionScope.user_id }';
		}else if('${p04017 }'==2){
			//按仓库打印
			if(liger.get("store_code").getValue()==""){
				$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
				return;
			}
			useId=liger.get("store_code").getValue().split(",")[0];
		}
		
		var para={
				template_code:'04010',
				class_name:"com.chd.hrp.mat.service.storage.in.MatStorageInService",
				method_name:"queryMatInByNewCombinePrint",
				bean_name:"matStorageInService",
				isSetPrint:false,//是否套打，默认非套打
				isPreview:true,//是否预览，默认直接打印
				in_id:$("#in_id").val(),
				store_id:liger.get("store_code").getValue().split(",")[0], 
				p_num:0,
				use_id:useId
		}; 
		officeFormPrint(para);
    }
	
	//类别打印
	function invTypePrint(){
		
		var useId=0;//统一打印
		if('${p04017 }'==1){
			//按用户打印
			useId='${sessionScope.user_id }';
		}else if('${p04017 }'==2){
			//按仓库打印
			if(liger.get("store_code").getValue()==""){
				$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
				return;
			}
			useId=liger.get("store_code").getValue().split(",")[0];
		}
		
		var para={
				template_code:'04010',
				class_name:"com.chd.hrp.mat.serviceImpl.storage.in.MatStorageInServiceImpl",
				method_name:"queryMatInByPrintTemlateNewPrint",
				isSetPrint:false,//是否套打，默认非套打
				isPreview:true,//是否预览，默认直接打印
				in_id:$("#in_id").val(),
				store_id:liger.get("store_code").getValue().split(",")[0], 
				p_num:0,
				is_type:1,
				use_id:useId
		}; 
		officeFormPrint(para);
		//printTemplate("queryMatInByPrintTemlate.do?isCheck=false",para);
	}

	//关闭当前页面
	function this_close(){
		frameElement.dialog.close();
	}
	function changeIndate(){
		var sup_id = liger.get("sup_code").getValue().split(",")[0];
		var in_date = liger.get("in_date").getValue();
		if(sup_id && in_date){
			$.ajax({
				url:"../../getMatPactFkxyInfo.do?isCheck=false",
				data:{
					sup_id:sup_id,
					in_date :in_date	
				},
				type:"post",
				dataType:"json",
				success:function(msg){
					var pactFkxyInfo=msg.pactFkxyInfo?msg.pactFkxyInfo:"";
					
					liger.get("protocol_code").setValue("");
					liger.get("protocol_code").setValue(pactFkxyInfo);
					is_total_cont =msg.is_total_cont?msg.is_total_cont:0;
					is_price_cont =msg.is_price_cont?msg.is_price_cont:0;
				},
				async:false
			})
		}
	}
	function change_button(){
		if(state == 1){
			$("#save").ligerButton({click: save, width:90, disabled:false});
		}else{
			$("#save").ligerButton({click: save, width:90, disabled:true});
		}
		//alert("printFlag:"+printFlag+"   state:"+state);
		
		if(printFlag==1 && state == 3){
      		$("#print").ligerButton({click: printDate, width:80, disabled:false});
      	}else if(printFlag==1 && state != 3){
      		$("#print").ligerButton({click: printDate, width:80, disabled:true});
      	}else{
      		$("#print").ligerButton({click: printDate, width:80, disabled:false});
      	}
		
	}
    </script>
  
</head>
  
<body> 
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post"  id="form1" >
		<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
			<tr>
	            <td style="display: none;">
	            	<input name="group_id" type="text" id="group_id" value="${matInMain.group_id}" ltype="text" />
	            	<input name="hos_id" type="text" id="hos_id" value="${matInMain.hos_id}" ltype="text" />
	            	<input name="copy_code" type="text" id="copy_code" value="${matInMain.copy_code}" ltype="text" />
	            	<input name="in_id" type="text" id="in_id" value="${matInMain.in_id}" ltype="text" />
	            </td>
			</tr>
	        <tr>
	            <td align="right" class="l-table-edit-td" >
	            	<span style="color:red">*</span>入库单号：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="in_no" type="text" id="in_no" value="${matInMain.in_no}" ltype="text" disabled="disabled" required="true" validate="{required:true}" />
	            </td>
	            <td align="right" class="l-table-edit-td" >
	            	<span style="color:red">*</span>业务类型：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="bus_type_code" type="text" id="bus_type_code" ltype="text" required="true" validate="{required:true}" />
	            </td>
	            <td align="right" class="l-table-edit-td"  >
	            	<span style="color:red">*</span>仓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;库：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="store_code" type="text" id="store_code" ltype="text" required="true" validate="{required:true}" />
	            </td>
	        </tr> 
	        <tr>
	            <td align="right" class="l-table-edit-td" >
	            	<span style="color:red">*</span>制单日期：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input class="Wdate" name="in_date" id="in_date" value="${matInMain.in_date}" type="text" required="true" onchange="changeIndate()" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" validate="{required:true}" />
	            </td>
	            <td align="right" class="l-table-edit-td"  >
	            	<span id="stocker_span" style="color:red">*</span>采&nbsp;&nbsp;购&nbsp;&nbsp;员：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="stocker" type="text" id="stocker" ltype="text" required="true" validate="{required:true}" />
	            </td>
	            <td align="right" class="l-table-edit-td"  >
	            	<span id="stock_type_code_span" style="color:red">*</span>采购类型：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="stock_type_code" type="text" id="stock_type_code" ltype="text" validate="{required:false}" />
	            </td>
	        </tr> 
			<tr>
	            <td align="right" class="l-table-edit-td"  >
	            	<span id="sup_code_span" style="color:red">*</span>供&nbsp;&nbsp;应&nbsp;&nbsp;商：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="sup_code" type="text" id="sup_code" ltype="text" required="true" validate="{required:true}" />
	            </td>
	            <td align="right" class="l-table-edit-td"  >
	            	申请科室：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="app_dept" type="text" id="app_dept" ltype="text" validate="{required:false}" />
	            </td>
	            <td align="right" class="l-table-edit-td" >
					协议编号：
	            </td>
	            <td align="left" class="l-table-edit-td" >
	            	<input name="protocol_code" type="text" id="protocol_code" value="${matInMain.protocol_id}" ltype="text" validate="{required:false}" />
	            	
	            </td>
			</tr>
	        <tr>
	            <td align="right" class="l-table-edit-td" >
					项&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;目：
	            </td>
	            <td align="left" class="l-table-edit-td" >
	            	<input name="proj_code" type="text" id="proj_code" ltype="text" validate="{required:false,maxlength:20}" />
	            </td>
	           
	            <td align="right" class="l-table-edit-td" >验&nbsp;&nbsp;收&nbsp;&nbsp;员：</td>
				<td align="left" class="l-table-edit-td" >
					<input name="examiner" type="text" id="examiner" ltype="text" validate="{required:false,maxlength:20}" />
				</td>
	            <td align="right" class="l-table-edit-td" >货&nbsp;&nbsp;单&nbsp;&nbsp;号：</td>
				<td align="left" class="l-table-edit-td"  >
					<input name="delivery_code"  type="text" id="delivery_code" value="${matInMain.delivery_code}" ltype="text" validate="{required:false,maxlength:20}" />
				</td>
	
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td">发票日期：</td>
				<td align="left" class="l-table-edit-td">
					<input class="Wdate" name="bill_date" id="bill_date" type="text" value="${matInMain.bill_date}"
					onFocus="WdatePicker({isShowClear:true,dateFmt:'yyyy-MM-dd'})" />
			    </td>
				
				<td align="right" class="l-table-edit-td">发&nbsp;&nbsp;票&nbsp;&nbsp;号：</td>
				<td align="left" class="l-table-edit-td">
					<input name="bill_no" type="text" id="bill_no"  ltype="text" value="${matInMain.bill_no}" required="true"  />
				</td>
				
				
				 <td align="right" class="l-table-edit-td" >
					摘&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;要：
	            </td>
	            <td align="left" class="l-table-edit-td" >
	            	<input name="brief" type="text" id="brief" ltype="text" value="${matInMain.brief}" validate="{required:false,maxlength:100}" />
	            </td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td">验收温度：</td>
				<td align="left" class="l-table-edit-td">
					<input name="temperature" type="text" id="temperature"  ltype="text" value="${matInMain.temperature}"/>
				</td>
			</tr>
	    </table>
    </form>
    <div style="width: 100%; height: 100%;">
		<div id="maingrid"></div>
		<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%"  style="margin-top: 5px;">
			<tr>	
				<td align="center" class="l-table-edit-td" width="32%" >
					制单人：${matInMain.maker_name}
				</td>
				<td align="center" class="l-table-edit-td" width="32%" >
					审核人：${matInMain.checker_name}
				</td>
				<td align="center" class="l-table-edit-td" width="32%" >
					确认人：${matInMain.confirmer_name}
				</td>
			</tr>
			<tr>	
				<td align="center" class="l-table-edit-td" colspan="4">
				   
					<button id ="save" accessKey="B"><b>修改(<u>B</u>)</b></button>
					&nbsp;&nbsp;
					<button id ="print" accessKey="P"><b>打印(<u>P</u>)</b></button>
					&nbsp;&nbsp; 
					<button id ="printSet" accessKey="M"><b>打印模板(<u>M</u>)</b></button>
					&nbsp;&nbsp;
					<button id ="mergePrint" accessKey="H"><b>合并打印(<u>H</u>)</b></button>
					&nbsp;&nbsp;
					<button id ="barCodePrintMenu" accessKey="A"><b>条码模板打印(<u>A</u>)</b></button>
					&nbsp;&nbsp; 
					<button id ="barCodePrintSet" accessKey="T"><b>条码模板(<u>T</u>)</b></button>
					&nbsp;&nbsp; 
					<button id ="newBarcodePrint" accessKey="J"><b>条码打印(<u>J</u>)</b></button>
					&nbsp;&nbsp;  
					<button id ="invTypePrint" accessKey="L"><b>类别打印(<u>J</u>)</b></button>
					&nbsp;&nbsp;  
					<button id ="close" accessKey="C"><b>关闭(<u>C</u>)</b></button>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>
