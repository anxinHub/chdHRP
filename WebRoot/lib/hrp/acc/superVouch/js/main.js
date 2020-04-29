//摘要获取焦点
function summaryOnFocus(){
    var $row = frameObj.grid.getRowByIndex(0);//第一行摘要获取焦点
	frameObj.grid.setActiveCell($($row[0].cells[3]));//单元格获取焦点
	frameObj.grid.editCell(); 
}
//删除分录
function myDelDetail(){
	if($(document).find(".l-window-mask").css("display") == "block"){
		return;
	}
	
	if($("input[name='controllerButton']").attr("disabled")=="disabled"){		
		//按钮置灰状态下返回
		return;
	}
	
	
	if(frameObj.grid.getSelectedRows().length>1){
		$.ligerDialog.confirm("确定要删除吗？", function(yes) {
			if(yes) {
				$.each(frameObj.grid.getSelectedRows(),function (n,obj){
					frameObj.grid.assureEmptyRow();//插入空行
				});
				frameObj.grid.removeActiveRow();
				frameObj.sumDebitCredit();//更新合计
			}
		});
	}else{
		frameObj.grid.removeActiveRow();
		frameObj.grid.assureEmptyRow();//插入空行
		frameObj.sumDebitCredit();//更新合计
	}
	
	

}

//删除凭证
function myDelVouch(){
	if($(document).find(".l-window-mask").css("display") == "block"){
		return;
	}
	
	if($("#vouchId").val()==""){
		return;
	}
	
	if($("input[name='controllerButton']").attr("disabled")=="disabled"){		
		//按钮置灰状态下返回
		return;
	}
	Local.set("hrp[repeat[commit",true);
	$.ligerDialog.confirm('确定要删除凭证吗？', function(yes) {
		if (yes) {
			var index = layer.load(1);
			ajaxJsonObjectBylayer("deleteSuperVouchByVouchId.do",{vouch_id:$("#vouchId").val()},function (responseData){
				//console.log(responseData.state);
				if(typeof (responseData.state) != 'undefined' && responseData.state=="true"){
					
					var param={next_no:"true",vouch_date:$("#vouch_date").val(),vouch_type_code:liger.get("vouch_type_code").getValue(),p001:paraList["001"],is_budg:liger.get("is_budg").getValue()};
					querySuperVouchByJump(param,index);
				}
			},layer,index);
		}else{
			Local._remove("hrp[repeat[commit");
		}
	});
	
}


//保存凭证
function mySave(state,isPrint,printFlag){
	if($(document).find(".l-window-mask").css("display") == "block"){
		return;
	}
	
	var p008=paraList["008"];//凭证保存提示成功信息
	if(isPrint=="true"){
		p008=0;
	}
	
	if($("input[name='controllerButton']").attr("disabled")=="disabled"){		
		//按钮置灰状态下返回
		if(isPrint=="true"){
			myPrint($("#vouchId").val(),printFlag);//打印
		}
		return;
	}
	
	if(liger.get("vouch_type_code").getValue()==""){
		$.ligerDialog.error("凭证类型不能为空.");
		return;
	}
	
	if(state==1 && liger.get("is_budg").getValue()==1 && (frameObj.debitSum!=frameObj.creditSum || frameObj.budgDebitSum!=frameObj.budgCreditSum)){
		$.ligerDialog.error("借贷不相等.");
		return;
	}else if(state==1 && liger.get("is_budg").getValue()==2 && frameObj.debitSum!=frameObj.creditSum){
		$.ligerDialog.error("借贷不相等.");
		return;
	}
	
	var r1 =/^[0-9]*[1-9][0-9]*$/;//正整数
	if($("#vouch_no").val()=="" || (!r1.test($("#vouch_no").val()) && parseInt($("#vouch_no").val())!=0)){
		$.ligerDialog.error("凭证号只能输入整数！");
		return false;
	}
	
	if($("#file_num").val()=="" || (!r1.test($("#file_num").val()) && parseInt($("#file_num").val())<0)){
		$.ligerDialog.error("附件只能输入整数！");
		return false;
	}

	if(state==-1 && vouchJson["busi_type_code"]!=""){
		$.ligerDialog.error("自动凭证不能存草稿！");
		return false;
	}
	
	var saveIndex = layer.load(1);
	var saveJson=[];
	var isSubmit=true;
	var frameGrid=frameObj.grid;
	//frameGrid.editorBlur(frameGrid.getActiveCell());//光标离开保存数据
	
	getSaveJson(false);//取财务会计科目
	
	if(isSubmit && liger.get("is_budg").getValue()==1){
		//取预算会计科目
		getSaveJson(true);
	}
	
	if(!isSubmit){
		return;
	}
	
	//console.log("vouchJson",JSON.stringify(vouchJson));
	//正常保存凭证，分录不能为空
	if(state==1 && saveJson.length==0){
		layer.close(saveIndex);
		$.ligerDialog.error("分录不能为空.");
		return;
	}
	
	//是否生成新的凭证号
	var isGetNewVNo=0;
	if($("#vouchId").val()=="" && (vouchJson["vouch_no"]==$("#vouch_no").val() || $("#vouch_no").is(":disabled"))){
		isGetNewVNo=1;
	}
	
	var param={
			detail:JSON.stringify(saveJson),
			diff:JSON.stringify(diffJson),
			vouchType:liger.get("vouch_type_code").getValue(),
			vouchDate:$("#vouch_date").val(),
			fileNum:$("#file_num").val(),
			state:state,
			vouchId:$("#vouchId").val(),
			auto_id: vouchJson["auto_id"],
			createUser:$("#createUserId").val(),
			vouch_no:$("#vouch_no").val(),
			p001:paraList["001"],//根据凭证号的位数，将会在凭证号前面补0
			p002:paraList["002"],//草稿凭证是否占凭证号
			p005:paraList["005"],//允许修改他人凭证
			isGetNewVNo:isGetNewVNo,//是否生成新的凭证号
			p007:paraList["007"],//是否修改凭证号
			p008:p008,//保存提示成功
			p026:paraList["026"],//保存必须要标注现金流量
			p027:paraList["027"],//到期日期默认方式
			oldVouchType:vouchJson["vouch_type_code"],//记录凭证类型初始值，值改变需要变更凭证号
			oldVouchDate:vouchJson["vouch_date"],//记录凭证日期初始值，值改变需要变更凭证号
			busi_log_table:vouchJson["busi_log_table"],//生成凭证传参，日志表
			busi_type_code:vouchJson["busi_type_code"],//生成凭证传参，业务类型编码
			busi_no:vouchJson["busi_no"],//生成凭证传参，单据编码
			template_code:vouchJson["template_code"],//生成凭证传参，模板编码
			is_auto_diff: $("#is_diff_check").prop("checked"),
			isPrint: isPrint
	
	}
	//console.log("param",param);//JSON.stringify(param)
	Local.set("hrp[repeat[commit",true);
	ajaxJsonObjectBylayer("saveSuperVouch.do",param,function (responseData){
		if(typeof (responseData.state) != 'undefined' && responseData.state=="true"){
			$("#vouchId").val(responseData.vouchId);
			
			if(isPrint=="true"){
				myPrint(responseData.vouchId,printFlag);
				if(responseData.isRe){
					$("#stateDiv").show();
					$("#stateDiv").text(responseData.tip);
					return;//遇到判断不能保存，返回不刷新
				}
			}
			
			if(isParentQuery==1){parentFrameUse().query();}
			
			if(isPrint=="false" && state==1){
				if(paraList["008"]==2){
					$('.l-dialog-btn-inner').attr('onClick', 'myNewVouch("save");');
					return;
				}else if(paraList["008"]==0){
					myNewVouch('save');
					return;
				}
			}
			
//			if(paraList["008"]==0 && isPrint=="false" && state==1){
//				//凭证保存不提示成功信息,直接取新凭证号
//				param={next_no:"true",vouch_date:$("#vouch_date").val(),vouch_type_code:liger.get("vouch_type_code").getValue(),p001:paraList["001"],is_budg:liger.get("is_budg").getValue()};	
//			}
			var param={next_no:"false",vouch_id:responseData.vouchId,vouch_date:$("#vouch_date").val(),vouch_type_code:liger.get("vouch_type_code").getValue(),p001:paraList["001"],is_budg:liger.get("is_budg").getValue()};
			querySuperVouchByJump(param,saveIndex);
			
		}
	},layer,saveIndex);
	
	
	//拼保存json
	function getSaveJson(isBudg){
		var vouchRow=0;
		$.each(frameGrid.getGridData(),function(i,obj){
			vouchRow=vouchRow+1;
			//借贷金额为空不处理
			if(obj.debit=="")obj.debit=0;
			if(obj.credit=="")obj.credit=0;
			if(obj.budg_debit=="")obj.budg_debit=0;
			if(obj.budg_credit=="")obj.budg_credit=0;
			
			
			if(isBudg){
				//只取预算会计科目
				if(state==1 && obj.budg_debit==0 && obj.budg_credit==0){
					return true;
				}
				
				if(obj.budg_subj_code=="" && parseFloat(obj.budg_debit)==0 && parseFloat(obj.budg_credit)==0 ){
					return true;
				}
				
				if(state==1 && (obj.budg_subj_code=="" || (parseFloat(obj.budg_debit)==0 && parseFloat(obj.budg_credit)==0)) ){
					return true;
				}
				
				if(state==1 && obj.budg_subj_code!="" && parseFloat(obj.budg_debit)==0 && parseFloat(obj.budg_credit)==0 ){
					isSubmit=false;
					layer.close(saveIndex);
					$.ligerDialog.error(vouchRow+"："+obj.budg_subj_code+"，没有借贷金额.");
					return false;
				}
				
				if(vouchJson["busi_type_code"]!="Z006" && paraList["047"]==1){
					var subjCode=obj.budg_subj_code.substring(0,1);
					if(subjCode=="6" && parseFloat(obj.budg_debit)!=0){
						isSubmit=false;
						layer.close(saveIndex);
						$.ligerDialog.error(vouchRow+"："+obj.budg_subj_code+"，收支科目必须与余额方向保持一致.");
						return false;
					}else if(subjCode=="7" && parseFloat(obj.budg_credit)!=0){
						isSubmit=false;
						layer.close(saveIndex);
						$.ligerDialog.error(vouchRow+"："+obj.budg_subj_code+"，收支科目必须与余额方向保持一致.");
						return false;
					}
				}
				
			}else{
				//只取财务会计科目
				if(state==1 && obj.debit==0 && obj.credit==0){
					return true;
				}
				
				if(obj.subj_code=="" && parseFloat(obj.debit)==0 && parseFloat(obj.credit)==0){
					return true;
				}
				
				if(state==1 && obj.subj_code=="" && parseFloat(obj.debit)==0 && parseFloat(obj.credit)==0 ){
					return true;
				}
				
				if(state==1 && obj.subj_code!="" && parseFloat(obj.debit)==0 && parseFloat(obj.credit)==0 ){
					isSubmit=false;
					layer.close(saveIndex);
					$.ligerDialog.error(vouchRow+"："+obj.subj_code+"，没有借贷金额.");
					return false;
				}
				
				if(vouchJson["busi_type_code"]!="Z006" && paraList["047"]==1){
					var subjCode=obj.subj_code.substring(0,1);
					if(subjCode=="4" && parseFloat(obj.debit)!=0){
						isSubmit=false;
						layer.close(saveIndex);
						$.ligerDialog.error(vouchRow+"："+obj.subj_code+"，收支科目必须与余额方向保持一致.");
						return false;
					}else if(subjCode=="5" && parseFloat(obj.credit)!=0){
						isSubmit=false;
						layer.close(saveIndex);
						$.ligerDialog.error(vouchRow+"："+obj.subj_code+"，收支科目必须与余额方向保持一致.");
						return false;
					}
				}
			}
			
			if(obj.summary=="" && (obj.subj_code!="" || obj.budg_subj_code!="")){
				isSubmit=false;
				layer.close(saveIndex);
				$.ligerDialog.error(vouchRow+"："+(obj.subj_code?obj.subj_code:obj.budg_subj_code)+"，摘要不能为空.");
				return false;
			}else if(obj.summary!="" && obj.subj_code=="" && obj.budg_subj_code==""){
				isSubmit=false;
				layer.close(saveIndex);
				$.ligerDialog.error(vouchRow+"："+obj.summary+"，科目不能为空.");
				return false;
			}else if(obj.summary=="" && obj.subj_code=="" && obj.budg_subj_code==""){
				isSubmit=false;
				layer.close(saveIndex);
				$.ligerDialog.error(vouchRow+"：摘要、科目不能为空.");
				return false;
			}
			
			var subjAttr;
			var jsonKey;
			var subjCode;
			if(isBudg){
				//取预算会计
				jsonKey=obj.budg_did;
				if(!jsonKey){
					jsonKey=obj.budg_id;
				}
				subjAttr=frameObj.getSubjAttr(obj.budg_subj_code);//根据科目编码获取科目属性
				subjCode=obj.budg_subj_code;
			}else{
				jsonKey=obj.did;
				if(!jsonKey){
					jsonKey=obj.id;
				}
				subjAttr=frameObj.getSubjAttr(obj.subj_code);//根据科目编码获取科目属性
				subjCode=obj.subj_code;
			}
			
			if(!subjAttr.id){
				isSubmit=false;
				layer.close(saveIndex);
				$.ligerDialog.error(subjCode+"，非法科目[本年度不是末级或者不存在].");
				return false;
			}
			
			var checkData=vouchCheckJson[jsonKey];
			var checkJson="";
			var isCash=false;
			//console.log(jsonKey,checkData)
			//辅助核算和现金流量一起保存
			if(checkData!=null && checkData.length>0){
				
				//为了缩短json格式，所以拼json串，其实可以直接使用checkData==null?"":checkData[0].Rows
				$.each(checkData,function(k,check){
					var checkStr="{";
					for (var key in check){
						 if(key=="cash_item_id"){
							 /*//后台判断
							  * $.each(checkDict["cash"],function(c,cash){
								 if(cash.cash_item_id==check[key]){
									 if(cash.cash_dire==0 && parseFloat(obj.credit)!=0){
										 isSubmit=false;
										 layer.close(saveIndex);
										 $.ligerDialog.error(vouchRow+"："+subjAttr.id+"的金额与现金流量标注方向不一致.");
									 }else if(cash.cash_dire==1 && parseFloat(obj.debit)!=0){
										 isSubmit=false;
										 layer.close(saveIndex);
										 $.ligerDialog.error(vouchRow+"："+subjAttr.id+"的金额与现金流量标注方向不一致.");
									 }
									 return false;
								 }
							 })*/
							 isCash=true;
						 }
						 if(key.indexOf("_name")==-1 && key!="id"){
							 checkStr=checkStr+"'"+key+"':'"+check[key]+"',";
						 }
					 }
					checkJson=checkJson+checkStr.substring(0,checkStr.length-1)+"},";
				});
				
			}else if(state==1 && subjAttr.is_check==1){
				isSubmit=false;
				layer.close(saveIndex);
				$.ligerDialog.error(vouchRow+"："+subjAttr.id+"，没有辅助核算.");
				return false;
			}else if(state==1 && subjAttr.is_check==1 && subjAttr.is_cash==1 && paraList["004"]==1){
				isSubmit=false;
				layer.close(saveIndex);
				$.ligerDialog.error(vouchRow+"："+subjAttr.id+"，没有现金流量.");
				return false;
			}else if(state==1 && parseFloat(obj.debit)==0 && parseFloat(obj.credit)==0 && parseFloat(obj.budg_debit)==0 && parseFloat(obj.budg_credit)==0){
				isSubmit=false;
				layer.close(saveIndex);
				$.ligerDialog.error(vouchRow+"："+subjAttr.id+"，没有借贷金额.");
				return false;
			}
			
			if(checkJson!=""){
				checkJson="["+checkJson.substring(0,checkJson.length-1)+"]";
			}
			
			//现金流量
			var cashData=vouchCashJson[jsonKey];
			var cashJson="";
			//console.log(jsonKey,cashData)
			if(cashData!=null && cashData.length>0){
				//为了缩短json格式，所以拼json串，其实可以直接使用checkData==null?"":checkData[0].Rows
				$.each(cashData,function(k,check){
					/*//后台判断
					 * $.each(checkDict["cash"],function(c,cash){
						 if(cash.cash_item_id==check["cash_item_id"]){
							 if(cash.cash_dire==0 && parseFloat(obj.credit)!=0){
								 isSubmit=false;
								 layer.close(saveIndex);
								 $.ligerDialog.error(vouchRow+"："+subjAttr.id+"的金额与现金流量标注方向不一致.");
							 }else if(cash.cash_dire==1 && parseFloat(obj.debit)!=0){
								 isSubmit=false;
								 layer.close(saveIndex);
								 $.ligerDialog.error(vouchRow+"："+subjAttr.id+"的金额与现金流量标注方向不一致.");
							 }
							 return false;
						 }
					 })*/
					var checkStr="{";
					for (var key in check){
						 if(key.indexOf("_name")==-1 && key!="id"){
							 checkStr=checkStr+"'"+key+"':'"+check[key]+"',";
						 }
					 }
					cashJson=cashJson+checkStr.substring(0,checkStr.length-1)+"},";
				});
			}else if(!isCash && state==1 && subjAttr.is_cash==1 && paraList["026"]==1){
				isSubmit=false;
				layer.close(saveIndex);
				$.ligerDialog.error(vouchRow+"："+subjAttr.id+"，没有现金流量.");
				return false;
			}
		
			if(cashJson!=""){
				cashJson="["+cashJson.substring(0,cashJson.length-1)+"]";
			}
			
			//saveJson.push({id:obj.id,summary:obj.summary,sid:obj.subj_code,debit:obj.debit,credit:obj.credit,check:checkData==null?"":checkData[0].Rows});
			if(isBudg){
				//分栏式，取预算会计科目
				saveJson.push({vouch_row:vouchRow,id:obj.id,did:obj.budg_did,summary:obj.summary,budg_summary:obj.budg_summary,sid:obj.budg_subj_code,debit:obj.budg_debit,credit:obj.budg_credit,nature_code:subjAttr.subj_nature_code,is_check: subjAttr.is_check,is_cash: subjAttr.is_cash,is_bill: subjAttr.is_bill,check:checkJson,cash:cashJson});
			}else{
				//取财务会计科目
				saveJson.push({vouch_row:vouchRow,id:obj.id,did:obj.did,summary:obj.summary,sid:obj.subj_code,debit:obj.debit,credit:obj.credit,nature_code:subjAttr.subj_nature_code,is_check: subjAttr.is_check,is_cash: subjAttr.is_cash,is_bill: subjAttr.is_bill,check:checkJson,cash:cashJson});
			}
			
		});
	}

}



//打印
function myPrint(vouchId,printFlag){
	if($(document).find(".l-window-mask").css("display") == "block"){
		return;
	}
	
	if(printFlag==1){
		//模板打印
		var para={
			template_code:'01001',
			class_name:"com.chd.hrp.acc.service.vouch.SuperVouchService",
			method_name:"querySuperVouchPrintPage",
			bean_name:"superVouchService",
			vouch_id:vouchId,
			vouch_type_code:liger.get("vouch_type_code").getValue(),
			vouch_date_b:$("#vouch_date").val(),
			vouch_date_e:$("#vouch_date").val()
		};
		
		officeFormPrint(para);
	}else{
		//表格打印	
		var para={
			template_code:'01002',
			class_name:"com.chd.hrp.acc.service.vouch.SuperVouchService",
			method_name:"querySuperVouchPrintPage",
			bean_name:"superVouchService",
			vouch_id:vouchId,
			isPreview:true,
			main_query:true//主表数据从数据库查询
		};
		
		officeTablePrint(para);
		
	}
	
	
	/* var para={
			vouch_id:vouchId,
			template_code:'01001',
			isPrintCount:false
	};
	
	printTemplate("querySuperVouchPrint.do",para); */
	//templatePrint("querySuperVouchPrint.do",{vouch_id:vouchId},{print_para_code:"",template_code:"01001"},"财务系统_凭证打印");
}


//凭证跳转（上张、下张）
function myVouchJump(flag){
	
	if(($("#vouch_date").val()=="" || liger.get("vouch_type_code").getValue()=="") ||  (!flag && $("#vouch_no_q").val().replace(/\s+/g,"")=="")){
		return;
	}
		
	var frameGrid=frameObj.grid;
	if(vouchJson["row_size"]!=frameGrid.getRows().length || vouchJson["debit_sum"]!=$("#debit_nameSum").find("div").text() || vouchJson["credit_sum"]!=$("#credit_nameSum").find("div").text()
			|| vouchJson["budg_debit_sum"]!=$("#budg_debit_nameSum").find("div").text() || vouchJson["budg_credit_sum"]!=$("#budg_credit_nameSum").find("div").text()){
		if(!confirm("是否离开？")){
			return;
		}
	}
	
	var vouch_no=$("#vouch_no").val();
	var isNextNo=false;
	if(flag=="P"){
		//根据上张查询
		if(parseInt($("#vouch_no").val())==1){
			//$.ligerDialog.success("已经是第一张凭证了.");
			//return;
			isNextNo=true;
		}else if(parseInt($("#vouch_no").val())==0){
			//$.ligerDialog.success("已经是第一张凭证了.");
			//return;
			isNextNo=true;
		}
	
	}else if(flag=="N"){
		if($("#vouchId").val()==""){
			return;
		}
		
	}else{
		//凭证号查询
		if($("#vouch_no_q").val()==""){
			return;
		}
		if(isNaN($("#vouch_no_q").val())){
			return;
		}
		if(parseInt($("#vouch_no_q").val())==0){
			//草稿凭证不占号，为0会有多张			
			return;
		}
		if(parseInt($("#vouch_no_q").val())==parseInt($("#vouch_no").val())){
			return;
		}
		vouch_no=parseInt($("#vouch_no_q").val());
	}
		
	var jumpIndex = layer.load(1);
	var param={next_no:isNextNo,vouch_no:vouch_no,vouch_date:$("#vouch_date").val(),vouch_type_code:liger.get("vouch_type_code").getValue(),p001:paraList["001"],jump_flag:flag,is_budg:liger.get("is_budg").getValue()};
	querySuperVouchByJump(param,jumpIndex);
}


//查询凭证，返回主从表
function querySuperVouchByJump(param,jumpIndex){
	
	$('#vouchCheckCashFrame').css("display","none");
	ajaxJsonObjectBylayer("querySuperVouchByJump.do",param,function (responseData){
		//vouchCheckMap = new HashMap();//按凭证分录的ID存放存放辅助核算
		//vouchCashMap = new HashMap();//按凭证分录的ID存放存放现金流量
		$('#subjActiveCell').text("");
		$('#subjBalanceDIv').text("");
		$('#subjEndOsDIv').text("");
		
		openType="newVouch";
		vouchJson.Rows=responseData.Rows;
		vouchCheckJson=responseData.check==undefined?{}:responseData.check;
		vouchCashJson=responseData.cash==undefined?{}:responseData.cash;
		diffJson=responseData.diff==undefined?[]:responseData.diff;//差异标注
		setDiffCheck(diffJson);
		
		//凭证主表
		vouchJson["vouch_id"]=responseData.vouch_id;
		vouchJson["vouch_date"]=responseData.vouch_date;
		vouchJson["vouch_type_code"]=responseData.vouch_type_code;
		vouchJson["att_num"]=responseData.att_num;
		vouchJson["vouch_no"]=responseData.vouch_no;
		vouchJson["state"]=responseData.state;
		vouchJson["create_user"]=responseData.create_user;
		vouchJson["create_name"]=responseData.create_name;
		vouchJson["cash_user"]=responseData.cash_user;
		vouchJson["cash_name"]=responseData.cash_name;
		vouchJson["audit_user"]=responseData.audit_user;
		vouchJson["audit_name"]=responseData.audit_name;
		vouchJson["acc_user"]=responseData.acc_user;
		vouchJson["acc_name"]=responseData.acc_name;
		vouchJson["busi_log_table"]="";
		vouchJson["busi_type_code"]=responseData.busi_type_code;
		vouchJson["busi_no"]="";
		vouchJson["template_code"]=responseData.template_code;
		vouchJson["auto_id"]="";
		vouchJson["px_tp_code"]="";
		vouchJson["px_tp_name"]="";
		vouchJson["px_tp_note"]="";
		
		$("#vouchId").val(vouchJson.vouch_id);
		$("#state").val(vouchJson.state);
		/*if(responseData.vouch_type_code!=""){
			liger.get("vouch_type_code").setValue(responseData.vouch_type_code);
			liger.get("vouch_type_code").setText(responseData.vouch_type_name);	
		}*/
		$("#vouch_no").val(vouchJson.vouch_no);
		$("#vouch_date").val(vouchJson.vouch_date);
		$("#file_num").val(vouchJson.att_num);
		$("#createUserId").val(vouchJson.create_user);
		$("#create_name").text("制单："+vouchJson.create_name);
		$("#audit_name").text("审核："+vouchJson.audit_name);
		$("#cash_name").text("出纳："+vouchJson.cash_name);
		$("#acc_name").text("记账："+vouchJson.acc_name);
		
		//凭证明细表
		if (frameObj.grid.isEditing) {
			frameObj.grid.exitEditor(true);
		}
		
		//表格渲染
		frameObj.rowIndex=0;
		frameObj.grid.renderData(vouchJson.Rows);
		//重置凭证状态，并且控制关闭窗口提示
		frameObj.resetVouchStatus();
		loadVouchDict();//加载凭证科目、摘要下拉列表
		
		frameObj.grid.bindEvents();
		frameObj.window.scrollTo(0,0);
		//console.log(vouchJson)
		layer.close(jumpIndex);
		
	},layer,jumpIndex);
}

//新建凭证
function myNewVouch(page){
	
	if(page=="main" && $(document).find(".l-window-mask").css("display") == "block"){
		return;
	}
	
	var frameGrid=frameObj.grid;
	
	if(page!="save"){
		if(vouchJson["row_size"]!=frameGrid.getRows().length || vouchJson["debit_sum"]!=$("#debit_nameSum").find("div").text() || vouchJson["credit_sum"]!=$("#credit_nameSum").find("div").text()
				|| vouchJson["budg_debit_sum"]!=$("#budg_debit_nameSum").find("div").text() || vouchJson["budg_credit_sum"]!=$("#budg_credit_nameSum").find("div").text()){
			if(!confirm("是否离开？")){
				return;
			}
		}	
	}
	

	var jumpIndex = layer.load(1);
	Local.set("hrp[repeat[commit",true);
	ajaxJsonObjectByUrl("queryMaxVouchDate.do?isCheck=false",{p029:paraList["029"]},function (responseData){
		$("#vouch_date").val(responseData.vouch_date);//凭证日期
	},false);
	
	var param={next_no:true,vouch_no:999999,vouch_date:$("#vouch_date").val(),vouch_type_code:liger.get("vouch_type_code").getValue(),p001:paraList["001"],is_budg:liger.get("is_budg").getValue()};
	querySuperVouchByJump(param,jumpIndex);
}

//上传附件
function myUpload(){
	var vouch_id=$("#vouchId").val();
	if(vouch_id==null||vouch_id==""){  
    	parent.$.ligerDialog.warn("请先保存凭证！");
        return false;  
      }
	 $.ligerDialog.open({
		title : '上传附件',
		height : $(window).height()-100,
		width : $(window).width()-200,
		url : 'upLoadPage.do?isCheck=false&vouch_id='+vouch_id,
		modal : true,
		showToggle : false,
		showMax : false,
		showMin : false,
		isResize : false
	}); 
	
}

//关闭窗口
function closeDialog(){
	if($(document).find(".l-window-mask").css("display") == "block"){
		return;
	}
	
	var frameGrid=frameObj.grid;
	if(vouchJson["row_size"]!=frameGrid.getRows().length || vouchJson["debit_sum"]!=$("#debit_nameSum").find("div").text() || vouchJson["credit_sum"]!=$("#credit_nameSum").find("div").text()
			 || vouchJson["budg_debit_sum"]!=$("#budg_debit_nameSum").find("div").text() || vouchJson["budg_credit_sum"]!=$("#budg_credit_nameSum").find("div").text()){
		if(!confirm("是否关闭？")){
			return;
		}
	}
	frameElement.dialog.close();
}



//取最大凭证号
function getMaxVouchNo(para){
	ajaxJsonObjectByUrl("queryMaxVouchNo.do?isCheck=false",para,function (responseData){
		vouchJson["vouch_no"]=responseData.vn;
		$("#vouch_no").val(responseData.vn);//凭证号
		isGetMaxVouchNo=false;//控制凭证日期
	});
}

var isGetMaxVouchNo=false;
//年发生改变
function cYearFunc(dp){
	
	isGetMaxVouchNo=true;
	var frameGrid=frameObj.grid;
	var haveData=false;
	
	$.each(frameGrid.getGridData(), function (i, obj) {
		
		if(liger.get("is_budg").getValue()==1){
			if(obj.subj_code!="" || obj.budg_subj_code!=""){
				haveData=true;
				return false;
			}
			
		}else{
			if(obj.subj_code!=""){
				haveData=true;
				return false;
			}
		}
	}); 
	//如果有会计科目则不允许修改年度，否则根据年度重新加载会计科目
	if(haveData){
		
		$.ligerDialog.error("凭证中已存在会计科目,不允许切换年度.");
		dp.hide();
		return;
	}else{
	
		ajaxJsonObjectByUrl("accVouchDictList.do?isCheck=false&acc_year="+$dp.cal.newdate.y,{flag:1},function (responseData){
			subjDict=responseData;
			 //表格下拉框渲染
		    if(liger.get("is_budg").getValue()==1){
		    	frameGrid.editorProps["subj_name"] = responseData;
		    	frameGrid.editorProps["budg_subj_name"] = responseData;
			}else{
				frameGrid.editorProps["subj_name"] = responseData;
			}
			
	    });
	}
}
//月份发生改变
function cMonthFunc(){
	isGetMaxVouchNo=true;
}

//凭证日期触发凭证号
function vouchDtateFunc(dp){
	//033凭证号取值方式:0按会计期间取值,1按凭证日期取值(调用时传入凭证日期)
	if((paraList["033"]==1 && dp.cal.getDateStr()!=dp.cal.getNewDateStr() ) || (isGetMaxVouchNo && liger.get("vouch_type_code").getValue()!="" && dp.cal.getNewDateStr()!="")){
		getMaxVouchNo({"p001":paraList["001"],"vouch_type_code":liger.get("vouch_type_code").getValue(),"vouch_date":dp.cal.getNewDateStr()});
		isGetMaxVouchNo=false;
	}
}

//辅助核算
function myCheck(){
	if($(document).find(".l-window-mask").css("display") == "block"){
		return;
	}
	$("#checkDiv").hide();
	var $activeCell =frameObj.grid.getActiveCell();
	frameObj.openVouchCheck($activeCell,2);
}

//现金流量
function myCash(){
	if($(document).find(".l-window-mask").css("display") == "block"){
		return;
	}
	$("#checkDiv").hide();
	var $activeCell =frameObj.grid.getActiveCell();
	if(paraList["004"]==1){
		//现金流量与辅助核算一起保存
		frameObj.openVouchCheck($activeCell,2);
	}else{
		frameObj.openVouchCash($activeCell,2);	
	}
	
}

//弹出辅助核算、现金流量窗口，保存后回调事件
function callSaveVouchCheck(parm){
	
	var $row=frameObj.grid.getRowByIndex(parm.rowNumber);
	var $activeCell=$($row[0].cells[parm.cellNumber]);
	if(parm.flag=="save"){
    	
    	if(parm.cellNumber==4 || parm.cellNumber==5 || parm.cellNumber==6){
    		//全屏式或者财务会计科目列
    		$($row[0].cells[5]).find("div").text(parseFloat(parm.debit)==0?"":parm.debit);//借方金额
    		$($row[0].cells[6]).find("div").text(parseFloat(parm.credit)==0?"":parm.credit);//贷方金额
    		$($row[0].cells[13]).find("div").text(parm.debit);//借方金额
    		$($row[0].cells[14]).find("div").text(parm.credit);//贷方金额
    		frameObj.customStyle($($row[0].cells[5]),parm.debit);//借方金额样式
    		frameObj.customStyle($($row[0].cells[6]),parm.credit);//贷方金额样式
    		/* frameObj.grid.move("right");
    		frameObj.grid.move("right");
    		frameObj.grid.move("right");
    		frameObj.grid.hideMove("right"); */
    	}else{
    		$($row[0].cells[19]).find("div").text(parseFloat(parm.debit)==0?"":parm.debit);//借方金额
    		$($row[0].cells[20]).find("div").text(parseFloat(parm.credit)==0?"":parm.credit);//贷方金额
    		$($row[0].cells[21]).find("div").text(parm.debit);//借方金额
    		$($row[0].cells[22]).find("div").text(parm.credit);//贷方金额
    		frameObj.customStyle($($row[0].cells[19]),parm.debit);//借方金额样式
    		frameObj.customStyle($($row[0].cells[20]),parm.credit);//贷方金额样式
    	}
    	
    	//计算合计
    	frameObj.sumDebitCredit();
    	var cellsText3=$($row[0].cells[3]).find("div").text()
    	if(cellsText3!="" && cellsText3.indexOf("[")!=-1 && cellsText3.indexOf("]")!=-1){
    		cellsText3=cellsText3.substring(cellsText3.indexOf("]")+1,cellsText3.length);
		}
		if(paraList["040"]==1 || (parm.summary && cellsText3=="")){
        	//040：分录辅助核算摘要同步，或者分录没有摘要，辅助核算的摘要赋给分录
        	$($row[0].cells[3]).find("div").text(parm.summary);
	    }
    	 
        //拼接分录的摘要：[辅助核算内容]摘要
    	if(paraList["036"]==1 && parm.checkItemVal!=undefined && parm.checkItemVal!=""){
    		parm.checkItemVal=parm.checkItemVal.split(" ")[1];
    		cellsText3=$($row[0].cells[3]).find("div").text();
    		if(cellsText3!="" && cellsText3.indexOf("[")!=-1 && cellsText3.indexOf("]")!=-1){
    			cellsText3=cellsText3.substring(cellsText3.indexOf("]")+1,cellsText3.length);
    		}
    		$($row[0].cells[3]).find("div").text("["+parm.checkItemVal+"]"+cellsText3);//摘要+辅助核算页面传过来的摘要	
    	}
    	
    	var $nextRow = $activeCell.parent("tr").next();//下一行
    	var $nextCell=$($nextRow[0].cells[3]);
       	if(parm.cellNumber==4 || parm.cellNumber==5 || parm.cellNumber==6){
	    	if(liger.get("is_budg").getValue()==1 && $("#is_budg_check").prop("checked")){
	    		//生成预算会计
	    		updateBudgData(parm);
	    	}
    	}else if($($nextRow[0].cells[4]).find("div").text()!="" && $($nextRow[0].cells[18]).find("div").text()==""){
    		//预算会计，贷方金额列，如果下一行财务会计有数据，预算会计没有数据，直接跳到预算会计
			$nextCell=$($nextRow[0].cells[18]);
    	}
       	
       	frameObj.grid.setActiveCell($nextCell);//下一行焦点
       	
    	if(window.navigator.userAgent.indexOf("Chrome") >= 0){
    	    frameObj.grid.$el.focus();
    	}else{
    		$($nextRow[0].cells[3]).focus();
    	}
    	
     /* if ($nextRow.length > 0) {
	    	if($($nextRow[0].cells[3]).find("div").text()==""){
	    		var cellsText3=$($row[0].cells[3]).find("div").text();
	    		if(cellsText3!="" && summaryText.substring(0,1)=="[" && cellsText3.indexOf("]")!=-1){
	    			cellsText3=cellsText3.substring(cellsText3.indexOf("]")+1,cellsText3.length);
	    		}
	    		$($nextRow[0].cells[3]).find("div").text(cellsText3);//将摘要复制到下一行，不需要手动获取，表格支持焦点自动获取
	    	}
    	} */
    	
    	//刷新辅助核算页面
    	frameObj.clickVouchDetailFun("enter",$activeCell);
      	
    }else{
    	frameObj.grid.setActiveCell($activeCell);//单元格获取焦点
		if(window.navigator.userAgent.indexOf("Chrome") >= 0){
			frameObj.grid.$el.focus();
    	}else{
    		$activeCell.focus();
    	}
		
    }
	
	//frameObj.grid.exitEditor(true);
	setTimeout(function (){
    	frameObj.grid.editCell(); 
	},100);
	
}

//预算会计页面弹出辅助核算、现金流量窗口，保存后回调事件
function callSaveKindCheck(parm){
	if(!budgDialog.frame){
		return;
	}
	
	var $row=budgDialog.frame.grid.getRowByIndex(parm.rowNumber);
	var $activeCell=$($row[0].cells[parm.cellNumber]);
	if(parm.flag=="save"){
    	
		var summmaryIndex=3;//财务会计摘要
    	if(parm.cellNumber==4 || parm.cellNumber==5 || parm.cellNumber==6){
    		//全屏式或者财务会计科目列
    		$($row[0].cells[5]).find("div").text(parseFloat(parm.debit)==0?"":parm.debit);//借方金额
    		$($row[0].cells[6]).find("div").text(parseFloat(parm.credit)==0?"":parm.credit);//贷方金额
    		$($row[0].cells[13]).find("div").text(parm.debit);//借方金额
    		$($row[0].cells[14]).find("div").text(parm.credit);//贷方金额
    		budgDialog.frame.customStyle($($row[0].cells[5]),parm.debit);//借方金额样式
    		budgDialog.frame.customStyle($($row[0].cells[6]),parm.credit);//贷方金额样式
    		/* frameObj.grid.move("right");
    		frameObj.grid.move("right");
    		frameObj.grid.move("right");
    		frameObj.grid.hideMove("right"); */
    	}else{
    		summmaryIndex=17;//预算会计摘要
    		$($row[0].cells[19]).find("div").text(parseFloat(parm.debit)==0?"":parm.debit);//借方金额
    		$($row[0].cells[20]).find("div").text(parseFloat(parm.credit)==0?"":parm.credit);//贷方金额
    		$($row[0].cells[21]).find("div").text(parm.debit);//借方金额
    		$($row[0].cells[22]).find("div").text(parm.credit);//贷方金额
    		budgDialog.frame.customStyle($($row[0].cells[19]),parm.debit);//借方金额样式
    		budgDialog.frame.customStyle($($row[0].cells[20]),parm.credit);//贷方金额样式
    	}
    	
    	//计算合计
    	budgDialog.frame.sumDebitCredit();
        if(paraList["040"]==1 || parm.summary && $($row[0].cells[summmaryIndex]).find("div").text()==""){
        	//040：分录辅助核算摘要同步，或者分录没有摘要，辅助核算的摘要赋给分录
        	
        	$($row[0].cells[summmaryIndex]).find("div").text(parm.summary);
        }
        
        //拼接分录的摘要：[辅助核算内容]摘要
    	if(paraList["036"]==1 && parm.checkItemVal!=undefined && parm.checkItemVal!=""){
    		parm.checkItemVal=parm.checkItemVal.split(" ")[1];
    		var cellsText3=$($row[0].cells[summmaryIndex]).find("div").text();
    		if(cellsText3!="" && cellsText3.indexOf("[")!=-1 && cellsText3.indexOf("]")!=-1){
    			cellsText3=cellsText3.substring(cellsText3.indexOf("]")+1,cellsText3.length);
    		}
    		$($row[0].cells[summmaryIndex]).find("div").text("["+parm.checkItemVal+"]"+cellsText3);//摘要+辅助核算页面传过来的摘要	
    	}
    	
    	var $nextRow = $activeCell.parent("tr").next();//下一行
    	var $nextCell=$($nextRow[0].cells[summmaryIndex]);
       /*	if(parm.cellNumber==4 || parm.cellNumber==5 || parm.cellNumber==6){
	    	if(liger.get("is_budg").getValue()==1 && $("#is_budg_check").prop("checked")){
	    		//生成预算会计
	    		updateBudgData(parm);
	    	}
    	}*/
       	
       	budgDialog.frame.grid.setActiveCell($nextCell);//下一行焦点
       	
    	if(window.navigator.userAgent.indexOf("Chrome") >= 0){
    		budgDialog.frame.grid.$el.focus();
    	}else{
    		$($nextRow[0].cells[summmaryIndex]).focus();
    	}
      	
    }else{
    	budgDialog.frame.grid.setActiveCell($activeCell);//单元格获取焦点
		if(window.navigator.userAgent.indexOf("Chrome") >= 0){
			budgDialog.frame.grid.$el.focus();
    	}else{
    		$activeCell.focus();
    	}
		
    }
	
	//frameObj.grid.exitEditor(true);
	setTimeout(function (){
		budgDialog.frame.grid.editCell(); 
	},100);
	
}

//生成预算会计,单条生成入口
function updateBudgData(parm){
	
	var $row=frameObj.grid.getRowByIndex(parm.rowNumber);
	var $rowData=frameObj.grid.getRowData($row);//新行
	var jsonKeyAcc=$rowData.did;
	if(!jsonKeyAcc){
		jsonKeyAcc=$rowData.id;
	}
	var jsonKeyBudg=$rowData.budg_did;
	if(!jsonKeyBudg){
		jsonKeyBudg=$rowData.budg_id;
	}
	
	if($($row[0].cells[23]).find("div").text()!=""){
		//有数据返回
		return;
		/*var subjAttr=frameObj.getSubjAttr($($row[0].cells[23]).find("div").text());//根据科目编码获取科目属性
		if(subjAttr.is_bill==1){
			subjAttr.is_check=1;
		}
		
		if(subjAttr.is_cash==0 || !vouchCashJson[jsonKeyAcc] || vouchCashJson[jsonKeyAcc].length==0){
			return;
		}else if(vouchCashJson[jsonKeyBudg] && vouchCashJson[jsonKeyBudg].length>0){
			return;
		}
		
		if(subjAttr.is_check==0 || !vouchCheckJson[jsonKeyAcc] || vouchCheckJson[jsonKeyAcc].length==0){
			return;
		}else if(vouchCheckJson[jsonKeyBudg] && vouchCheckJson[jsonKeyBudg].length>0){
			return;
		}*/
	}
	
	updateBudgDataCore(parm);
	
}
//按科目对照，单条/批量生成预算会计
function updateBudgDataCore(parm){
	
	var parmBudg={
		acc_year: $("#vouch_date").val().substring(0,4),
		subj_code:parm.subj_code
	};
	var msg="";
	var jsonKeyAcc,jsonKeyBudg;
	var beginRowIndex=0;
	ajaxJsonObjectByUrl("queryBudgSubjByAcc.do?isCheck=false",parmBudg,function(resJson) {
		if(resJson.Total==0){
			return;
		}
		
		var isSourceMore=false;
		$.each(frameObj.grid.getGridData(),function(index,accObj){
			
			if(accObj.subj_code==null || accObj.subj_code==""){
				return true;
			}
			
			if(!parm.isAllBudg && parm.rowNumber!=index){
				//单条生成
				return true;
			} 
			
			var subjAttr=frameObj.getSubjAttr(accObj.subj_code);
			if(!subjAttr){
				return true;
			}
			
			//往来科目反方向取对方科目
			if(subjAttr.subj_nature_code=="04" || subjAttr.subj_nature_code=="05"){
				if(subjAttr.subj_dire==0 && parseFloat(accObj.credit)==0){
					return true;
				}else if(subjAttr.subj_dire==1 && parseFloat(accObj.debit)==0){
					return true;
				}
			}
			
			if(liger.get("is_budg").getValue()==1){
				jsonKeyAcc=accObj.did;
				if(!jsonKeyAcc){
					jsonKeyAcc=accObj.id;
				}
			}else{
				jsonKeyAcc=accObj.did;
				if(!jsonKeyAcc){
					jsonKeyAcc=accObj.id;
				}
			}
			
			//根据财务会计科目、资金来源匹配预算会计科目
			var resObj=null;
			var count=0;
			if(vouchCheckJson[jsonKeyAcc] && vouchCheckJson[jsonKeyAcc].length>0){
				$.each(vouchCheckJson[jsonKeyAcc],function(checkIndex,checkObj){
					if(checkObj["check7"] && checkObj["check7"]!=""){
						if(!parm.isAllBudg && vouchCheckJson[jsonKeyAcc].length>1){
							//单条生成，有多条辅助核算，不生成
							return false;
						}
						
						resObj=getBudgSubjByAcc(resJson.Rows,accObj.subj_code,checkObj["check7"]);
						if(resObj["source"]){
							count++;
							if(parm.isAllBudg)parm.beginRowIndex++;
							
							if(count>1){
								//只要有多条资金来源对应预算会计科目,后面按照顺序生成
								isSourceMore=true;
							}
							
							if(isSourceMore || liger.get("is_budg").getValue()==2){
								beginRowIndex=parm.beginRowIndex;
							}else{
								beginRowIndex=index;
							}
							
							updateBudgAccBySubjSource(accObj,resObj["source"],checkIndex,checkObj,jsonKeyAcc);
						}
					}
				});
			}
			
			if(count==0){
				//根据财务会计科目匹配预算会计科目
				resObj=getBudgSubjByAcc(resJson.Rows,accObj.subj_code,null);
				if(!resObj["subj"]){
					return true;
				}
				
				if(parm.isAllBudg)parm.beginRowIndex++;
				if(isSourceMore || liger.get("is_budg").getValue()==2){
					beginRowIndex=parm.beginRowIndex;
				}else{
					beginRowIndex=index;
				}
				
				updateBudgAccBySubj(accObj,resObj["subj"]);
			}
			
		});
		
		if(msg!=""){
			$.ligerDialog.error(msg+"非法科目[本年度不是末级或者不存在].");
		}
		
		frameObj.sumDebitCredit();//更新合计
	});
	
	//财务会计科目
	function updateBudgAccBySubj(accObj,res){
		
		//非法科目[本年度不是末级或者不存在]
		if(!frameObj.getSubjAttr(res.budg_subj_code).id && msg.indexOf(res.budg_subj_code+"，")==-1){
			msg=msg+res.budg_subj_code+"，";
		}
		
		var $row=frameObj.grid.getRowByIndex(beginRowIndex);
		
		//分录数据
		if(liger.get("is_budg").getValue()==1){
    		//分栏式
			$($row[0].cells[18]).find("div").text(res.budg_subj_name);//科目名称
			$($row[0].cells[19]).find("div").text(accObj.debit_name);//借方金额
			$($row[0].cells[20]).find("div").text(accObj.credit_name);//贷方金额
			$($row[0].cells[21]).find("div").text(accObj.debit);//借方金额
			$($row[0].cells[22]).find("div").text(accObj.credit);//贷方金额
			$($row[0].cells[23]).find("div").text(res.budg_subj_code);//科目编码
			frameObj.customStyle($($row[0].cells[19]),accObj.debit);//借方金额样式
			frameObj.customStyle($($row[0].cells[20]),accObj.credit);//贷方金额样式
			
			jsonKeyBudg=$($row[0].cells[24]).find("div").text();
			if(!jsonKeyBudg){
				jsonKeyBudg=$($row[0].cells[16]).find("div").text();
			}
			
    	}else{
    		//分屏式
    		$($row[0].cells[3]).find("div").text(accObj.summary);//摘要
    		$($row[0].cells[4]).find("div").text(res.budg_subj_name);//科目名称
			$($row[0].cells[5]).find("div").text(accObj.debit_name);//借方金额
			$($row[0].cells[6]).find("div").text(accObj.credit_name);//贷方金额
			$($row[0].cells[13]).find("div").text(accObj.debit);//借方金额
			$($row[0].cells[14]).find("div").text(accObj.credit);//贷方金额
			$($row[0].cells[7]).find("div").text(res.budg_subj_code);//科目编码
			frameObj.customStyle($($row[0].cells[5]),accObj.debit);//借方金额样式
			frameObj.customStyle($($row[0].cells[6]),accObj.credit);//贷方金额样式
			frameObj.setSubjFlag($row[0].cells[4],res.budg_subj_code);//标记
			
			jsonKeyBudg=$($row[0].cells[1]).find("div").text();
			
    	}
		if($row.find(":checkbox").length==0){
			$row.find(">td.selectable").html($("<input type=checkbox>"));
		}
		
		//辅助核算
		if(vouchCheckJson[jsonKeyAcc]){
			var jsonArry=[];
			var isCashJson=false;
			$.each(vouchCheckJson[jsonKeyAcc],function(i,obj){
				var json={};
				
				for (var key in obj){
					
					 if(key!="check_no" && key.indexOf("check")!=-1 && (res.b_check1==key || res.b_check2==key || res.b_check3==key || res.b_check4==key)){
						//辅助核算
						json[key]=obj[key];
						json[key+"_name"]=obj[key+"_name"];
					 }else if(res.b_is_cash==1 && key=="cash_name"){
						//现金流量
						json["cash_name"]=obj.cash_name;
						json["cash_item_id"]=obj.cash_item_id;
						isCashJson=true;
					 }else if(res.b_is_bill==1 && key=="paper_type_code"){
						//票据核销
						json["paper_type_code"]=obj.paper_type_code;
						json["check_no"]=obj.check_no;
					}else if(res.b_nature_code=='03' && key=="pay_name"){
						//结算方式
						json["pay_name"]=obj.pay_name;
						json["pay_type_code"]=obj.pay_type_code;
					}
					 
				 }
				
				if(JSON.stringify(json)!="{}"){
					json["id"]=i+1;
					json["summary"]=obj.summary;
					json["money"]=obj.money;
					jsonArry.push(json);
				}
			});
			
			if(jsonArry.length>0){
				if((res.b_nature_code=='03' &&  paraList["032"]==1) || res.b_is_bill==1 || res.b_check1){
					//032：凭证制单，银行科目默认弹出辅助核算窗口、票据号核销、辅助核算
					vouchCheckJson[jsonKeyBudg]=jsonArry;
				}else if(isCashJson && res.b_is_cash==1){
					//没有辅助核算，只有现金流量
					vouchCashJson[jsonKeyBudg]=jsonArry;
				}
			}
		}
		//现金流量
		if(vouchCashJson[jsonKeyAcc]){
			var jsonArry=[];
			$.each(vouchCashJson[jsonKeyAcc],function(i,obj){
				var json={};
				if(obj.cash_name){
					json["cash_name"]=obj.cash_name;
					json["cash_item_id"]=obj.cash_item_id;
					json["id"]=i+1;
					json["summary"]=obj.summary;
					json["money"]=obj.money;
					jsonArry.push(json);
				}
			});
			
			if((res.b_is_cash==1 &&  paraList["004"]==1) && ((res.b_nature_code=='03' &&  paraList["032"]==1) || res.b_is_bill==1 || res.b_check1)){
				/* 004：凭证制单，辅助核算窗口显示现金流量
				 * 032：凭证制单，银行科目默认弹出辅助核算窗口、票据号核销、辅助核算
				 */
				if(!vouchCheckJson[jsonKeyBudg] || vouchCheckJson[jsonKeyBudg].length==0){
					//辅助核算
					vouchCheckJson[jsonKeyBudg]=jsonArry;
				}
			}else if(res.b_is_cash==1){
				//现金流量
				vouchCashJson[jsonKeyBudg]=jsonArry;
			}
		}
	}
	
	//财务会计科目、资金来源
	function updateBudgAccBySubjSource(accObj,res,checkIndex,checkObj,jsonKeyAcc){
		
		//非法科目[本年度不是末级或者不存在]
		if(!frameObj.getSubjAttr(res.budg_subj_code).id && msg.indexOf(res.budg_subj_code+"，")==-1){
			msg=msg+res.budg_subj_code+"，";
		}
		
		var debit=0;
		var credit=0;
		if(parseFloat(accObj.debit)!=0){
			debit=checkObj.money;
		}else{
			credit=checkObj.money;
		}
		
		var $row=frameObj.grid.getRowByIndex(beginRowIndex);
		
		//分录数据
		if(liger.get("is_budg").getValue()==1){
    		//分栏式
			$($row[0].cells[18]).find("div").text(res.budg_subj_name);//科目名称
			$($row[0].cells[19]).find("div").text(frameObj.customStyle($($row[0].cells[19]),debit));//借方金额
			$($row[0].cells[20]).find("div").text(frameObj.customStyle($($row[0].cells[20]),credit));//贷方金额
			$($row[0].cells[21]).find("div").text(debit);//借方金额
			$($row[0].cells[22]).find("div").text(credit);//贷方金额
			$($row[0].cells[23]).find("div").text(res.budg_subj_code);//科目编码
			frameObj.customStyle($($row[0].cells[19]),debit);//借方金额样式
			frameObj.customStyle($($row[0].cells[20]),credit);//贷方金额样式
			
			jsonKeyBudg=$($row[0].cells[24]).find("div").text();
			if(!jsonKeyBudg){
				jsonKeyBudg=$($row[0].cells[16]).find("div").text();
			}
			
    	}else{
    		//分屏式
    		$($row[0].cells[3]).find("div").text(accObj.summary);//摘要
    		$($row[0].cells[4]).find("div").text(res.budg_subj_name);//科目名称
			$($row[0].cells[5]).find("div").text(frameObj.customStyle($($row[0].cells[5]),debit));//借方金额
			$($row[0].cells[6]).find("div").text(frameObj.customStyle($($row[0].cells[6]),credit));//贷方金额
			$($row[0].cells[13]).find("div").text(debit);//借方金额
			$($row[0].cells[14]).find("div").text(credit);//贷方金额
			$($row[0].cells[7]).find("div").text(res.budg_subj_code);//科目编码
			frameObj.customStyle($($row[0].cells[5]),debit);//借方金额样式
			frameObj.customStyle($($row[0].cells[6]),credit);//贷方金额样式
			frameObj.setSubjFlag($row[0].cells[4],res.budg_subj_code);//标记
			
			jsonKeyBudg=$($row[0].cells[1]).find("div").text();
			
    	}
		if($row.find(":checkbox").length==0){
			$row.find(">td.selectable").html($("<input type=checkbox>"));
		}
		
		//辅助核算
		var jsonArry=[];
		var isCashJson=false;
		var json={};
		
		for (var key in checkObj){
			
			 if(key!="check_no" && key.indexOf("check")!=-1 && (res.b_check1==key || res.b_check2==key || res.b_check3==key || res.b_check4==key)){
				//辅助核算
				json[key]=checkObj[key];
				json[key+"_name"]=checkObj[key+"_name"];
			 }else if(res.b_is_cash==1 && key=="cash_name"){
				//现金流量
				json["cash_name"]=checkObj.cash_name;
				json["cash_item_id"]=checkObj.cash_item_id;
				isCashJson=true;
			 }else if(res.b_is_bill==1 && key=="paper_type_code"){
				//票据核销
				json["paper_type_code"]=checkObj.paper_type_code;
				json["check_no"]=checkObj.check_no;
			}else if(res.b_nature_code=='03' && key=="pay_name"){
				//结算方式
				json["pay_name"]=checkObj.pay_name;
				json["pay_type_code"]=checkObj.pay_type_code;
			}
			 
		 }
		
		if(JSON.stringify(json)!="{}"){
			json["id"]=checkIndex+1;
			json["summary"]=checkObj.summary;
			json["money"]=checkObj.money;
			jsonArry.push(json);
		}
		
		
		if(jsonArry.length>0){
			if((res.b_nature_code=='03' &&  paraList["032"]==1) || res.b_is_bill==1 || res.b_check1){
				//032：凭证制单，银行科目默认弹出辅助核算窗口、票据号核销、辅助核算
				vouchCheckJson[jsonKeyBudg]=jsonArry;
			}
		}
		
	}
	
	//根据会计科目找对照的预算科目
	function getBudgSubjByAcc(rows,subj_code,source_id){
		var res={};
		$.each(rows,function(i,r){
			
			if(source_id && source_id!=null && r.source_id!=null){
				if(r.acc_subj_code==subj_code && source_id==r.source_id){
					res["source"]=r;
					return false;
				}
			}else if(r.acc_subj_code==subj_code){
				res["subj"]=r;
				return false;
			}
			
		});
		
		return res;
	}
}


//批量生成预算会计
function updateAllBudgData(){
	
	if($("#is_budg_val").val()==1 && $("#budg_subj_nameSum").text().replace(/\s+/g,"")!="零元整"){
		//分栏式
		if(!confirm("是否按科目对照重新生成预算会计？")){
			return;
		}
	}else if($("#is_budg_val").val()==2){
		var isConfirm=false
		$.each(frameObj.grid.getGridData(),function(i,obj){
    		if(obj.subj_code!=""){
    			if(frameObj.getSubjAttr(obj.subj_code).kind_code=="02"){
    				isConfirm=true;
    				return false;
    			}
    		}
    	});
		if(isConfirm && !confirm("是否按科目对照重新生成预算会计？")){
			return;
		}
	}
	
	var subj_code="";
	$.each(frameObj.grid.getGridData(),function(i,obj){
		if(obj.subj_code!=""){
			subj_code=subj_code+obj.subj_code+",";
		}
	});
	if(subj_code==""){
		return;
	}else{
		subj_code=subj_code.substring(0,subj_code.length-1);
	}
	
	//清除预算会计数据
	var beginRowIndex=removeBudgData();
	
	var parm={
		subj_code:subj_code,
		beginRowIndex:beginRowIndex,
		isAllBudg:true
	};
	updateBudgDataCore(parm);
	
}

//根据凭证流程操作签字、审核、记账
function updateVouchStateByFlow(flag){
		
	if($("input[name='flowButton']").attr("disabled")=="disabled"){		
		//按钮置灰状态下返回
		return;
	}
	
	if(flag=="pre" && $("#preFlowButton").is(":hidden")){
		return;
	}
	
	if(flag=="next" && $("#nextFlowButton").is(":hidden")){
		return;
	}

	if($("input[name='flowButton']").attr("disabled")=="disabled"){		
		//按钮置灰状态下返回
		return;
	}
	var index = layer.load(1);
	var vouchFlowNew=frameObj.getVouchFlowByData();
	var nex_state=getVouchFlowByState(flag,$("#state").val(),vouchFlowNew);
	if(nex_state==null){
		nex_state=1;
		//layer.close(index);
		//$.ligerDialog.error("凭证状态异常.");
		//return;
	}
	var cur_state=$("#state").val();
	Local.set("hrp[repeat[commit",true);
	var param={vouch_id:$("#vouchId").val(),cur_state:cur_state,nex_state:nex_state,flag:flag};
	ajaxJsonObjectBylayer("updateVouchStateByFlow.do?isCheck=false",param,function (responseData){
		//console.log(responseData.state);
		if(typeof (responseData.state) != 'undefined' && responseData.state=="true"){
			$("#state").val(responseData.vouchState);
			
			//审批操作
			if(flag=="next"){
				//出纳签字
				if(nex_state=="2"){
					$("#cash_name").text("出纳："+responseData.user_name);
					
				//凭证审核
				}else if(nex_state=="3"){
					$("#audit_name").text("审核："+responseData.user_name);
				//凭证记账
				}else if(nex_state=="99"){
					$("#acc_name").text("记账："+responseData.user_name);
				}
			//取消操作
			}else{
				//出纳签字
				if(cur_state=="2"){
					$("#cash_name").text("出纳：");
				//凭证审核
				}else if(cur_state=="3"){
					$("#audit_name").text("审核：");
				//凭证记账
				}else if(cur_state=="99"){
					$("#acc_name").text("记账：");
				}
			}
			
			setMyDisabled(vouchFlowNew);
			frameObj.grid.config["readonly"]=isReadOnly;
		}
		layer.close(index);
	},layer,index);
}

//根据状态禁用
function setMyDisabled(vouchFlowNew){

	var isCash=false;
	var isAuti=false;
	$.each(vouchFlow, function (i, flow) {
		if(flow.NODE_ID==2){
			isCash=true;
		}
		if(flow.NODE_ID==3){
			isAuti=true;
		}
	});
	if(!isCash)$("#cash_name").css("display","none");//出纳签字
	if(!isAuti)$("#audit_name").css("display","none");//审核
	$("#copyButton").ligerButton({disabled: false});
	$("#copyButton").attr("disabled",false);
	
	var stateStr="";
	if(isOtherSys=="1"){
		stateStr="[其他账套凭证]";
	}
	
	if($("#state").val()==""){
		$("#stateDiv").show();
		$("#stateDiv").text("没有找到凭证");
		$("input[name='flowButton']").hide();
	}else if($("#state").val()=="-1"){
		$("#stateDiv").show();
		$("#stateDiv").text("草 稿 凭 证 "+stateStr);
		$("input[name='flowButton']").hide();
		$("#copyButton").ligerButton({disabled: true});
		$("#copyButton").attr("disabled",true);
	}else if($("#state").val()=="0"){
		$("#stateDiv").show();
		$("#stateDiv").text("作 废 凭 证 "+stateStr);
		$("input[name='flowButton']").hide();
	}else{
		
		if(stateStr!=""){
			$("#stateDiv").show();
			$("#stateDiv").text(stateStr);
		}else{
			$("#stateDiv").hide();
		
			//根据当前凭证的状态返回子节点控制下一操作
			$("#nextFlowButton").show();
			if($("#vouchId").val()=="" || $("#state").val()=="0" || $("#state").val()=="-1"){
				$("#nextFlowButton").hide();	
			
			//子节点为出纳签字
			}else if(getVouchFlowByState("next",$("#state").val(),vouchFlowNew)=="2"){
				$("#nextFlowButton").val("出纳签字 S");
			
			//子节点为凭证审核
			}else if(getVouchFlowByState("next",$("#state").val(),vouchFlowNew)=="3"){
				$("#nextFlowButton").val("凭证审核 S");
			
			//子节点为凭证记账
			}else if(getVouchFlowByState("next",$("#state").val(),vouchFlowNew)=="99"){
				$("#nextFlowButton").val("凭证记账 S");
				$("#nextFlowButton").hide();//先不考虑单张凭证记账的功能
				
			//找不到子节点
			}else{
				$("#nextFlowButton").hide();
			}
			
			//控制取消操作
			//当前凭证状态为新建状态
			$("#preFlowButton").show();
			if($("#state").val()=="1" || $("#state").val()=="0" || $("#state").val()=="-1"){
				$("#preFlowButton").hide();	
			
			//当前凭证状态为出纳签字
			}else if($("#state").val()=="2"){
				$("#preFlowButton").val("取消签字 Q");
			
			//当前凭证状态为凭证审核
			}else if($("#state").val()=="3"){
				$("#preFlowButton").val("取消审核 Q");
			
			//当前凭证状态为凭证记账
			}else if($("#state").val()=="99"){
				$("#preFlowButton").val("取消记账 Q");
				$("#preFlowButton").hide();//先不考虑单张凭证记账的功能
			} 
		}
	}
	
	var isDisabled=true;
	if($("#vouchId").val()==""){
		isDisabled=false;
	}else if(($("#state").val()=="-1" || $("#state").val()=="1")){
		//草稿凭证、新建凭证 && 自己的凭证、005=1参数允许修改他人凭证
		if(parent.sessionJson["user_id"]==$("#createUserId").val() || paraList["005"]==1){
			isDisabled=false;
		}
	}
	
	if(stateStr!=""){
		isDisabled=true;
	}
	
	/* if(isDisabled)$("#saveAndPrintButton").val("打印（P）");
	else $("#saveAndPrintButton").val("保存&打印（P）"); */
	
	
	if(paraList["007"]==1 && !isDisabled){
		$("#vouch_no").ligerTextBox({disabled: false});
		$("#vouch_no").attr("disabled",false);
	}else{
		$("#vouch_no").ligerTextBox({disabled: true});
		$("#vouch_no").attr("disabled",true);
	}
	
	$("#vouch_type_code").ligerComboBox({ disabled: isDisabled });
	$("#vouch_date").ligerTextBox({ disabled: isDisabled });
	$("#vouch_date").attr("disabled",isDisabled);
	$("#file_num").ligerTextBox({ disabled: isDisabled });
	$("#file_num").attr("disabled",isDisabled);
	$("input[name='controllerButton']").ligerButton({disabled: isDisabled});
	$("input[name='controllerButton']").attr("disabled",isDisabled);
	
	if(parent.sessionJson["user_id"]==$("#createUserId").val()){
		//不允许审核自己的凭证
		$("input[name='flowButton']").ligerButton({disabled: true});
		$("input[name='flowButton']").attr("disabled",true);
		
	}else{
		$("input[name='flowButton']").ligerButton({disabled: false});
		$("input[name='flowButton']").attr("disabled",false);
	}
	
	isReadOnly=isDisabled;
	frameObj.grid.config["readonly"]=isReadOnly;
}

//查询凭证审核流程
var vouchFlow=[];
function queryVouchFlow(){
	ajaxJsonObjectByUrl("queryVouchFlow.do?isCheck=false",{},function (responseData){
		vouchFlow=responseData;
	},false);
}

//根据凭证状态获取凭证流程
function getVouchFlowByState(flag,state,vouchFlowJson){
	var stateValue=null;
	$.each(vouchFlowJson, function (i, flow) {
		if(flag=="next"){
			if(flow.PARENT_NODE_ID==state){
				stateValue=flow.NODE_ID;
				return;//返回子节点
			}	
		}else{
			if(flow.NODE_ID==state){
				stateValue=flow.PARENT_NODE_ID;
				return;//返回父节点
			}
		}
		
	});
	return stateValue;
}

//复制凭证
function copyVouch(){
	if($(document).find(".l-window-mask").css("display") == "block"){
		return;
	}
	
	if($("#vouchId").val()==""){
		return;
	}
	
	if($("#copyButton").attr("disabled")=="disabled"){		
		//按钮置灰状态下返回
		return;
	}
	
	var fromData={
		copy_type:"2",//正常复制
		vouch_id:$("#vouchId").val(),
		vouch_date:$("#vouch_date").val(),
		vouch_type_code:liger.get("vouch_type_code").getValue() 
	}
	
	Local.set("hrp[repeat[commit",true);
	$.ligerDialog.confirm('确定复制？', function(yes) {
		if (yes) {
			var jumpIndex = layer.load(1);
			ajaxJsonObjectBylayer("copySuperVouch.do",fromData, function(responseData) {
				
				if (responseData.state == "true") {
					var vouch_no=parseInt(responseData.vn);
					var param={next_no:false,vouch_id:responseData.vouchId,vouch_date:$("#vouch_date").val(),is_budg:liger.get("is_budg").getValue()};
					querySuperVouchByJump(param,jumpIndex);
					
				}
			},layer,jumpIndex);	
		}else{
			Local._remove("hrp[repeat[commit");
		}
	});
	
}

//弹出科目窗口，赋值
function setActiveCellSubj(node,rowNumber,cellNumber,oldSubjCode){
	
	var $row=frameObj.grid.getRowByIndex(rowNumber);
	//var $activeCell=$($row[0].cells[cellNumber]);
	
	if(cellNumber==4){
		//财务会计科目
		//$($row[0].cells[4]).find("div").text(node.subj_name_all);//科目全称
		frameObj.subjDivView(node,$($row[0].cells[4]));//科目全称
		$($row[0].cells[7]).find("div").text(node.subj_code);//科目编码
	/*	$($row[0].cells[8]).find("div").text(node.is_check);//是否辅助核算
		$($row[0].cells[9]).find("div").text(node.is_cash);//是否现金流标注
		$($row[0].cells[10]).find("div").text(node.subj_nature_code);//科目性质
		$($row[0].cells[11]).find("div").text(node.subj_type_code);//科目分类
		$($row[0].cells[12]).find("div").text(node.subj_dire);//余额方向
		$($row[0].cells[16]).find("div").text(node.is_bill);//是否票据核销 
	*/	
	}else{
		//$($row[0].cells[18]).find("div").text(node.subj_name_all);//科目全称
		frameObj.subjDivView(node,$($row[0].cells[18]));//科目全称
		$($row[0].cells[23]).find("div").text(node.subj_code);//科目编码
		
	}
	/*if(oldSubjCode!=node.subj_code){
   		if($($row[0].cells[2]).find("div").text()!=""){
   	   		//修改凭证，如果科目发生改变，分录ID清空，重新保存辅助核算
   	   	 	$($row[0].cells[15]).find("div").text($($row[0].cells[2]).find("div").text());//保存初始化分录ID
   	   		$($row[0].cells[2]).find("div").text("");//分录ID
   	   	}
   	}*/
}

//保存凭证模板
function mySaveTemplate(){
	
	var r1 =/^[0-9]*[1-9][0-9]*$/;//正整数
	if($("#file_num").val()=="" || (!r1.test($("#file_num").val()) && parseInt($("#file_num").val())<0)){
		$.ligerDialog.error("附件只能输入整数！");
		return false;
	}
	var templateCode="";
	//不是自動凭证
	if(vouchJson["busi_type_code"]==""  && vouchJson["template_code"]!=""){
		templateCode=vouchJson["template_code"];
	}
	
	var para={
			vouch_id:$("#vouchId").val(),
			vouch_type_code:liger.get("vouch_type_code").getValue(),
			file_num:$("#file_num").val(),
			templateCode:templateCode,
			templateName:vouchJson["template_name"],
			templateNote:vouchJson["template_note"]
	}
	$.ligerDialog.open({url : 'saveTemplatePage.do?isCheck=false',
		data:para, height: 300,width: 400, title:'保存模板',modal:true,showToggle:false,showMax:false,showMin: false,isResize:false});
}

//保存平行记账模板
function mySavePxTemplate(){
		
	var para={
			vouch_type_code:liger.get("vouch_type_code").getValue(),
			pxTpCode:vouchJson["px_tp_code"],
			pxTpName:vouchJson["px_tp_name"],
			pxTpNote:vouchJson["px_tp_note"]
	}
	$.ligerDialog.open({url : '../../autovouch/accbudgtp/saveAccBudgTpByVouchPage.do?isCheck=false',
		data:para, height: 300,width: 400, title:'保存平行记账模板',modal:true,showToggle:false,showMax:false,showMin: false,isResize:false});
}

//取模板
function myGetTemplate(){
	
	$.ligerDialog.open({url : 'getTemplatePage.do?isCheck=false',
		data:{grid:frameObj.grid,vouchTypeData:vouchTypeData,vouch_date:$("#vouch_date").val()}, height: 600,width: 945, title:'取模板',modal:true,showToggle:false,showMax:false,showMin: false,isResize:false});
}

//取模板，渲染凭证json数据
function myGetTemplateVouchJson(jumpIndex,responseData,temp_name,note){
	
	openType="newVouch";
	vouchJson.Rows=responseData.Rows;
	vouchCheckJson=responseData.check==undefined?{}:responseData.check;
	vouchCashJson=responseData.cash==undefined?{}:responseData.cash;
	//凭证主表
	if($("#vouchId").val()==""){
		//新凭证，取模板
		$("#vouchId").val("");
		$("#state").val("1");
		$("#vouch_no").val(responseData.vouch_no);
		$("#vouch_date").val(responseData.vouch_date);
		$("#file_num").val(responseData.att_num);
		$("#createUserId").val(responseData.create_user);
		$("#create_name").text("制单："+responseData.create_name);
		$("#audit_name").text("审核："+responseData.audit_name);
		$("#cash_name").text("出纳："+responseData.cash_name);
		$("#acc_name").text("记账："+responseData.acc_name);
	}
	
	vouchJson["busi_type_code"]=responseData.busi_type_code;
	vouchJson["template_code"]=responseData.template_code;
	vouchJson["template_name"]=temp_name;
	vouchJson["template_note"]=note;
	vouchJson["auto_id"]=responseData.auto_id;
	vouchJson["busi_no"]=responseData.busi_no;
	vouchJson["busi_log_table"]=responseData.busi_log_table;
	
	//凭证明细表
	if (frameObj.grid.isEditing) {
		frameObj.grid.exitEditor(true);
	}
	
	//表格渲染
	frameObj.rowIndex=0;
	frameObj.grid.renderData(vouchJson.Rows);
	//重置凭证状态，并且关闭窗口提示
	frameObj.resetVouchStatus();
	loadVouchDict();//加载凭证科目、摘要下拉列表
	
	vouchJson["row_size"]=0;
	frameObj.grid.bindEvents();
	frameObj.window.scrollTo(0,0);
	//console.log(vouchJson)
	if(jumpIndex!=null){
		layer.close(jumpIndex);
	}
	
}

//存摘要
function mySaveSummary(summary,accBudgObj){
	
	//当摘要编辑时，取编辑框的值，否则取表格里面的值
	/*if(frameObj.grid.activeEditor && $activeCell.data("column")=="summary"){
		summary=$(frameObj.grid.activeEditor.editor).find(".tt-input").val();
	}else{
		summary=frameObj.grid.getRowDataByIndex(rownum).summary; 
	}*/
	
	if(!summary || summary==""){
		return;
	}
	
	if(summary.indexOf("[")!=-1 && summary.indexOf("]")!=-1){
		summary=summary.substring(summary.indexOf("]")+1,summary.length);
	}
	
	if(summary==""){
		return;
	}
	
	var jumpIndex = layer.load(1);
	ajaxJsonObjectBylayer("saveAccVouchSummary.do?isCheck=false",{summary:summary}, function(responseData) {
		
		if(!isReadOnly){
			ajaxJsonObjectByUrl("accVouchDictList.do?isCheck=false",{flag:2},function (responseData){
				summaryDict=responseData;
				frameObj.grid.editorProps["summary"] = responseData;
				if(accBudgObj){
					 if(accBudgObj.kindCode=="01"){
					 	accBudgObj.grid.editorProps["summary"]=responseData;
		             }else{
		            	 accBudgObj.grid.editorProps["budg_summary"]=responseData;
		             }
				}
			 });
		}
		layer.close(jumpIndex);
	},layer,jumpIndex);	
	
}

//摘要管理
function myGetSummary(){
	
	$.ligerDialog.open({url : 'getSummaryPage.do?isCheck=false',
		data:{grid:frameObj.grid}, height: 600,width: 600, title:'摘要管理',modal:true,showToggle:false,showMax:false,showMin: false,isResize:false});
}

//快捷键说明
function myHelp(){
	
	$.ligerDialog.open({ target: $("#target1"),height: 400,width: 600, title:'凭证制单快捷键说明',
		buttons: [{ text: '取消', onclick: function (item, dialog) { dialog.hide(); } } ]
	});

}

//维护辅助核算
function checkGrid(){
	parent.parent.openDialog(
		{
			url:'hrp/acc/acccheckitem/accCheckItemIndexPage.do?isCheck=false',
			title:'辅助核算',
			showToggle:true,
			width:0,
			height:0
		}
	);

}


//平行记账模板
function openVouchBudg(){
	//$.ligerDialog.open({ url: 'vouchBudgPage.do?isCheck=false',width:700,height: 300, isResize:true,modal:false });
	$.etDialog.open({
		id: "openVouchBudg",
		url: 'vouchBudgPage.do?isCheck=false',
		frameName : window.name,
		width: 800,
		height: 350,
		shade: 0,
		zIndex:0,
		offset: [$(window).height()-350-10,$(window).width()-800-90],
		maxmin: true,
		title: '平行记账模板',
		success: function(layero){
		   	 layer.setTop(layero);
		 },resizing: function(layero){
			  
		 },full: function(layero){
			  //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
			  var iframeWin = window[layero.find('iframe')[0]['name']];	  
			  setTimeout(function() {
				  iframeWin.$("#treeModDiv").css("height", $(window).height()-30);
				  iframeWin.budgGrid.refreshView();
				  iframeWin.accGrid.refreshView();
			  }, 100);
			 
		  },min: function(layero){
			  
		  },restore: function(layero){
			  //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
			  var iframeWin = window[layero.find('iframe')[0]['name']];
			  setTimeout(function() {
				  iframeWin.$("#treeModDiv").css("height", 350-30-30);
				  iframeWin.budgGrid.refreshView();
				  iframeWin.accGrid.refreshView();
			  }, 100);
		  }
	});

	
	/*layer.open({
		  id: 'budgDialog' //防止重复弹出
		  ,type: 2 //iframe
		  ,title: '平行记账模板'
		  ,area: ['750px', '300px']
		  ,shade: 0
		  ,zIndex:0
		  //,skin: 'layui-layer-molv' //样式类名
		  ,offset: [$(window).height()-300-10,$(window).width()-750-90]
		  ,maxmin: true
		  ,content: 'vouchBudgPage.do?isCheck=false'
		  ,zIndex: layer.zIndex 
		  ,success: function(layero){
		   	  layer.setTop(layero);
		  },resizing: function(layero){
				//console.log(layero);
		  },full: function(layero){
			  //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
			  var iframeWin = window[layero.find('iframe')[0]['name']];
			  setTimeout(function() {
				  iframeWin.budgGrid.refreshView();
				  iframeWin.accGrid.refreshView();
			  }, 100);
			 
		  },min: function(layero){
			 
		  },restore: function(layero){
			  //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
			  var iframeWin = window[layero.find('iframe')[0]['name']];
			  setTimeout(function() {
				  iframeWin.budgGrid.refreshView();
				  iframeWin.accGrid.refreshView();
			  }, 100);
		  }
		});*/
}

//失去焦点，退出编辑
/*function myExistsEdit(){
	if (frameObj.grid.isEditing) {
		frameObj.grid.exitEditor(true);
	} 
	
}*/

//获取空行的行号
function getGridRowIndex(){
	var beginRowIndex=-1;
	$.each(frameObj.grid.getGridData(),function(i,obj){
		var $row=frameObj.grid.getRowByIndex(i);
		if(liger.get("is_budg").getValue()==1 ){
			//分栏式
			if($($row[0].cells[18]).find("div").text()!="" || $($row[0].cells[19]).find("div").text()!="" || $($row[0].cells[20]).find("div").text()!=""){
				//最后一行数据
				beginRowIndex=i;
			}
			
		}else if(liger.get("is_budg").getValue()==2){
			//分屏式
			if($($row[0].cells[4]).find("div").text()!="" || $($row[0].cells[5]).find("div").text()!="" || $($row[0].cells[6]).find("div").text()!=""){
				//最后一行数据
				beginRowIndex=i;
			}
		}
	});
	
	return beginRowIndex;
}

//清除预算会计，返回最后一条数据的行号，分屏需要用到
function removeBudgData(){
	var beginRowIndex=-1;
	$.each(frameObj.grid.getGridData(),function(i,obj){
		var $row=frameObj.grid.getRowByIndex(i);
		if(liger.get("is_budg").getValue()==1 ){
			//分栏式
			$($row[0].cells[18]).find("div").text("");//预算科目名称
			$($row[0].cells[19]).find("div").text("");//借方
			$($row[0].cells[20]).find("div").text("");//贷方
			$($row[0].cells[21]).find("div").text("");//借方
			$($row[0].cells[22]).find("div").text("");//贷方
			$($row[0].cells[23]).find("div").text("");//预算科目编码
			$($row[0].cells[24]).find("div").text("");//ID
			$($row[0].cells[17]).find("div").text("");//预算会计摘要
		}else if(liger.get("is_budg").getValue()==2){
			//分屏式
			if(obj.subj_code!=""){
				var subjAttr=frameObj.getSubjAttr(obj.subj_code);//根据科目编码获取科目属性
				if(subjAttr.kind_code=="02"){
					$($row[0].cells[2]).find("div").text("");//ID
					$($row[0].cells[3]).find("div").text("");//摘要
					$($row[0].cells[4]).find("div").text("");//预算科目名称
					$($row[0].cells[5]).find("div").text("");//借方
					$($row[0].cells[6]).find("div").text("");//贷方
					$($row[0].cells[7]).find("div").text("");//预算科目编码
					$($row[0].cells[13]).find("div").text("");//借方
					$($row[0].cells[14]).find("div").text("");//贷方
					frameObj.setSubjFlag($row[0].cells[4]);//清除标记
				}
			}
		
			if($($row[0].cells[4]).find("div").text()!="" || $($row[0].cells[5]).find("div").text()!="" || $($row[0].cells[6]).find("div").text()!=""){
				//最后一行数据
				beginRowIndex=i;
			}
		}
	});
	frameObj.sumDebitCredit();//更新合计
	return beginRowIndex;
}

//根据平行记账模板生成预算
function saveBudgByAccTp(budgData,accData,is_dire){
	
	var beginRowIndex=removeBudgData();
	beginRowIndex=beginRowIndex+1;
	
	$.each(budgData,function(i,budg){
		
		var o=budg.rowData?budg.rowData:budg;
		var jsonKeyAcc,jsonKeyBudg;
		var debit_name="",debit=0.00,credit_name="",credit=0.00;
		
		var isCal=false;
		var accRow=null;
		if(o.cal.indexOf("+")!=-1 || o.cal.indexOf("-")!=-1 || o.cal.indexOf("*")!=-1 || o.cal.indexOf("/")!=-1){
			isCal=true;//有公式
			is_dire=1;//按模板方向
			accRow=calculate(o.cal);
		}else{
			accRow=getAccDataByBudg(o.cal);
		}
		
		if(accRow){
			debit_name=accRow.debit_name;
			debit=accRow.debit;
			credit_name=accRow.credit_name;
			credit=accRow.credit;
			if(is_dire==1){
				//按平行记账模板方向
				if(o.dire==0){
					//借方
					debit_name=parseFloat(accRow.debit)==0?accRow.credit_name:accRow.debit_name;
					debit=parseFloat(accRow.debit)==0?accRow.credit:accRow.debit;
					credit_name="";
					credit=0.00;
				}else{
					//贷方
					debit_name="";
					debit=0.00;
					credit_name=parseFloat(accRow.debit)==0?accRow.credit_name:accRow.debit_name;
					credit=parseFloat(accRow.debit)==0?accRow.credit:accRow.debit;
				}
			}
		}
		
		if(liger.get("is_budg").getValue()==1){
			//分栏式
			
			var $row=frameObj.grid.getRowByIndex(i);
			if(!$row || !$row[0]){
				//没有足够的空行
				frameObj.grid.assureEmptyRow();
				$row=frameObj.grid.getRowByIndex(i);
				$($row[0].cells[1]).html($("<div>"));
				$($row[0].cells[1]).find("div").text(frameObj.grid.getRowIndex());
				$($row[0].cells[16]).html($("<div>"));
				$($row[0].cells[16]).find("div").text(frameObj.grid.getRowIndex());
			}
			if($($row[0].cells[3]).find("div").text()==""){
				$($row[0].cells[3]).find("div").text(o.summary);//摘要
			}
			$($row[0].cells[18]).find("div").text(o.subj_name_all);//预算科目名称
			$($row[0].cells[23]).find("div").text(o.subj_code);//预算科目编码
			
			//根据对方编号填充数据	
			if(accRow){
				if(accRow.summary!="" && $($row[0].cells[3]).find("div").text()==""){
					//如果有财务会计的摘要，以实际业务的摘要为准
					$($row[0].cells[3]).find("div").text(accRow.summary);//摘要
				}
				$($row[0].cells[19]).find("div").text(debit_name);//借方
				$($row[0].cells[20]).find("div").text(credit_name);//贷方
				$($row[0].cells[21]).find("div").text(debit);//借方
				$($row[0].cells[22]).find("div").text(credit);//贷方
				frameObj.customStyle($($row[0].cells[19]),debit);//借方金额样式
				frameObj.customStyle($($row[0].cells[20]),credit);//贷方金额样式
				frameObj.sumDebitCredit();//更新合计
				
				jsonKeyAcc=accRow.did;
				if(!jsonKeyAcc){
					jsonKeyAcc=accRow.id;
				}
				
				jsonKeyBudg=$($row[0].cells[24]).find("div").text();
				if(!jsonKeyBudg){
					jsonKeyBudg=$($row[0].cells[16]).find("div").text();
				}
			}
			frameObj.grid.setRowSaved($row);
			
		}else if(liger.get("is_budg").getValue()==2){
			//分屏式
			
			var $row=frameObj.grid.getRowByIndex(beginRowIndex+i);
			if(!$row || !$row[0]){
				//没有足够的空行
				frameObj.grid.assureEmptyRow();
				$row=frameObj.grid.getRowByIndex(beginRowIndex+i);
				$($row[0].cells[1]).html($("<div>"));
				$($row[0].cells[1]).find("div").text(frameObj.grid.getRowIndex());
			}
			$($row[0].cells[3]).find("div").text(o.summary);//摘要
			$($row[0].cells[4]).find("div").text(o.subj_name_all);//预算科目名称
			$($row[0].cells[7]).find("div").text(o.subj_code);//预算科目编码
			frameObj.setSubjFlag($row[0].cells[4],o.subj_code);//更新标记
			
			//根据对方编号填充数据
			if(accRow){
				if(accRow.summary!="" && $($row[0].cells[3]).find("div").text()==""){
					//如果有财务会计的摘要，以实际业务的摘要为准
					$($row[0].cells[3]).find("div").text(accRow.summary);//摘要
				}
				$($row[0].cells[5]).find("div").text(debit_name);//借方
				$($row[0].cells[6]).find("div").text(credit_name);//贷方
				$($row[0].cells[13]).find("div").text(debit);//借方
				$($row[0].cells[14]).find("div").text(credit);//贷方
				frameObj.customStyle($($row[0].cells[5]),debit);//借方金额样式
				frameObj.customStyle($($row[0].cells[6]),credit);//贷方金额样式
				frameObj.sumDebitCredit();//更新合计
				
				jsonKeyAcc=accRow.did;
				if(!jsonKeyAcc){
					jsonKeyAcc=accRow.id;
				}
				jsonKeyBudg=$($row[0].cells[1]).find("div").text();
			}
			frameObj.grid.setRowSaved($row);
		}
		
		//辅助核算
		if(!isCal && vouchCheckJson[jsonKeyAcc]){
			var jsonArry=[];
			var isCashJson=false;
			$.each(vouchCheckJson[jsonKeyAcc],function(i,obj){
				var json={};
				
				for (var key in obj){
					 if(key!="check_no" && key.indexOf("check")!=-1 && (budg.check1==key || budg.check2==key || budg.check3==key || budg.check4==key)){
						//辅助核算
						json[key]=obj[key];
						json[key+"_name"]=obj[key+"_name"];
					 }else if(budg.is_cash==1 && key=="cash_name"){
						//现金流量
						json["cash_name"]=obj.cash_name;
						json["cash_item_id"]=obj.cash_item_id;
						isCashJson=true;
					 }else if(budg.is_bill==1 && key=="paper_type_code"){
						//票据核销
						json["paper_type_code"]=obj.paper_type_code;
						json["check_no"]=obj.check_no;
					}else if(budg.nature_code=='03' && key=="pay_name"){
						//结算方式
						json["pay_name"]=obj.pay_name;
						json["pay_type_code"]=obj.pay_type_code;
					}
					 
				 }
				
				/*if(accRow.check1==budg.check1 && obj.check1){
					//辅助核算1
					json["check1"]=obj.check1;
					json["check1_name"]=obj.check1_name;
				}
				if(accRow.check2==budg.check2 && obj.check2){
					//辅助核算2
					json["check2"]=obj.check2;
					json["check2_name"]=obj.check2_name;
				}
				if(accRow.check3==budg.check3 && obj.check3){
					//辅助核算3
					json["check3"]=obj.check3;
					json["check3_name"]=obj.check3_name;
				}
				if(accRow.check4==budg.check4 && obj.check4){
					//辅助核算4
					json["check4"]=obj.check4;
					json["check4_name"]=obj.check4_name;
				}
				if(accRow.is_cash==budg.is_cash && obj.cash_name && obj.cash_name.replace(/\s+/g,"")!=""){
					//现金流量
					json["cash_name"]=obj.cash_name;
					json["cash_item_id"]=obj.cash_item_id;
					isCashJson=true;
				}
				if(accRow.is_bill==budg.is_bill && obj.paper_type_code && obj.paper_type_code.replace(/\s+/g,"")!=""){
					//票据核销
					json["paper_type_code"]=obj.paper_type_code;
					json["check_no"]=obj.check_no;
				}
				if(budg.nature_code=='03' && obj.pay_name && obj.pay_name.replace(/\s+/g,"")!=""){
					//结算方式
					json["pay_name"]=obj.pay_name;
					json["pay_type_code"]=obj.pay_type_code;
				}*/
				
				if(JSON.stringify(json)!="{}"){
					json["id"]=i+1;
					json["summary"]=obj.summary;
					json["money"]=obj.money;
					jsonArry.push(json);
				}
			});
			
			if(jsonArry.length>0){
				if((budg.nature_code=='03' &&  paraList["032"]==1) || budg.is_bill==1 || budg.check1){
					//032：凭证制单，银行科目默认弹出辅助核算窗口、票据号核销、辅助核算
					vouchCheckJson[jsonKeyBudg]=jsonArry;
				}else if(isCashJson && budg.is_cash==1){
					//没有辅助核算，只有现金流量
					vouchCashJson[jsonKeyBudg]=jsonArry;
				}
			}
		}
		
		//现金流量
		if(!isCal && vouchCashJson[jsonKeyAcc]){
			var jsonArry=[];
			$.each(vouchCashJson[jsonKeyAcc],function(i,obj){
				var json={};
				if(obj.cash_name){
					json["cash_name"]=obj.cash_name;
					json["cash_item_id"]=obj.cash_item_id;
					json["id"]=i+1;
					json["summary"]=obj.summary;
					json["money"]=obj.money;
					jsonArry.push(json);
				}
			});
			
			if((budg.is_cash==1 &&  paraList["004"]==1) && ((budg.nature_code=='03' &&  paraList["032"]==1) || budg.is_bill==1 || budg.check1)){
				/* 004：凭证制单，辅助核算窗口显示现金流量
				 * 032：凭证制单，银行科目默认弹出辅助核算窗口、票据号核销、辅助核算
				 */
				if(!vouchCheckJson[jsonKeyBudg] || vouchCheckJson[jsonKeyBudg].length==0){
					//辅助核算
					vouchCheckJson[jsonKeyBudg]=jsonArry;
				}
			}else if(budg.is_cash==1){
				//现金流量
				vouchCashJson[jsonKeyBudg]=jsonArry;
			}
		}
		
	});
	
	//平行记账，根据预算会计的对方编号查找财务会计的金额和摘取
	function getAccDataByBudg(df_code){
		if(accData.length==0){
			return null;
		}
		
		var accRow=null;
		$.each(accData,function(i,obj){
			if(obj.sort_code.toUpperCase()==df_code.toUpperCase()){
				accRow=obj;
				return false;
			}
		});
		
		if(!accRow){
			return null;
		}
		
		var isVouch=false;
		$.each(frameObj.grid.getGridData(),function(i,obj){
			if(obj.subj_code==accRow.subj_code){
				accRow["id"]=obj.id;
				accRow["did"]=obj.did;
				accRow["summary"]=obj.summary;
				accRow["debit"]=obj.debit;
				accRow["credit"]=obj.credit;
				accRow["debit_name"]=obj.debit_name;
				accRow["credit_name"]=obj.credit_name;
				isVouch=true;
				return false;
			}
		});
		
		if(!isVouch){
			return null;
		}
		
		return accRow;
	}
	
	//公式计算
	function calculate(cal){
		var reRow={};
		/* var ret = cal.replace(/[0-9]*(\.[0-9]*)?/g,function(e){
			if(e!=""){
				console.log(e)	
			}
		}); */
		//正则表达式/\d+/g： 匹配至少一个数字，按数字分组，根据编号返回数据
		//正则表达式/[a-zA-Z]+/g： 匹配至少一个字母，按字母分组，根据编号返回数据
		 var ret=cal.replace(/[a-zA-Z]+/g, function () {
	        //调用方法时内部会产生 this 和 arguments
	        //console.log(arguments[0]);
			var accRow=getAccDataByBudg(arguments[0]);
			if(!accRow){
				return 0.00;
			}
	        return parseFloat(accRow.debit)==0?accRow.credit:accRow.debit;
		 });
		 
		try{
			var reMoney=eval(ret);
			reMoney=formatNumber(reMoney,2,0);
			reRow["debit"]=reMoney;
			reRow["debit_name"]=reMoney.replace(".","").replace("-","");
			reRow["credit"]=0.00;
			reRow["credit_name"]="";
		}catch(err){
			$.ligerDialog.error("公式不合法，不能取金额");
	    }
		
		return reRow;
	}
}


//删除单元格数据
function myRemoveCell(){

	var $activeCell =frameObj.grid.getActiveCell();
	if(!$activeCell){
		return;
	}
	
    var $row=frameObj.grid.getCellRow($activeCell);
	var $rowData=frameObj.grid.getRowData($row);
	var columnName=$activeCell.data("column");
	
	if(columnName=="subj_name" || columnName=="debit_name"  || columnName=="credit_name"){
		//财务会计
		var jsonKey=$rowData.did;
		if(!jsonKey){
			jsonKey=$rowData.id;
		}
		$($row[0].cells[2]).find("div").text("");//did
		$($row[0].cells[4]).find("div").text("");//科目名称
		$($row[0].cells[5]).find("div").text("");//借
		$($row[0].cells[6]).find("div").text("");//贷
		$($row[0].cells[7]).find("div").text("");//科目编码
		$($row[0].cells[13]).find("div").text("0.00");//借
		$($row[0].cells[14]).find("div").text("0.00");//贷
		
		//删除辅助核算
		if(vouchCheckJson[jsonKey]){
			delete vouchCheckJson[jsonKey];
		}
		
		//删除现金流量
		if(vouchCashJson[jsonKey]){
			delete vouchCashJson[jsonKey];
		}
		
		frameObj.sumDebitCredit();//更新合计
	}else if(columnName=="budg_subj_name" || columnName=="budg_debit_name"  || columnName=="budg_credit_name"){
		//预算会计
		var jsonKey=$rowData.budg_did;
		if(!jsonKey){
			jsonKey=$rowData.budg_id;
		}
		//删除辅助核算
		if(vouchCheckJson[jsonKey]){
			delete vouchCheckJson[jsonKey];
		}
		
		//删除现金流量
		if(vouchCashJson[jsonKey]){
			delete vouchCashJson[jsonKey];
		}
		$($row[0].cells[18]).find("div").text("");//科目名称
		$($row[0].cells[19]).find("div").text("");//借
		$($row[0].cells[20]).find("div").text("");//贷
		$($row[0].cells[21]).find("div").text("0.00");//借
		$($row[0].cells[22]).find("div").text("0.00");//贷
		$($row[0].cells[23]).find("div").text("");//科目编码
		$($row[0].cells[24]).find("div").text("");//budg_did
		$($row[0].cells[17]).find("div").text("");//预算会计摘要
		
		//删除辅助核算
		if(vouchCheckJson[jsonKey]){
			delete vouchCheckJson[jsonKey];
		}
		
		//删除现金流量
		if(vouchCashJson[jsonKey]){
			delete vouchCashJson[jsonKey];
		}
		
		frameObj.sumDebitCredit();//更新合计
	}else if(columnName=="summary"){
		$($row[0].cells[3]).find("div").text("");//摘要
	}
		
}

//删除行数据
function myRemoveRow(){
	var $activeCell =frameObj.grid.getActiveCell();
	if(!$activeCell){
		return;
	}
	
    var $row=frameObj.grid.getCellRow($activeCell);
	var $rowData=frameObj.grid.getRowData($row);
	
	/**财务会计**/
	var jsonKey=$rowData.did;
	if(!jsonKey){
		jsonKey=$rowData.id;
	}
	$($row[0].cells[2]).find("div").text("");//did
	$($row[0].cells[3]).find("div").text("");//摘要
	$($row[0].cells[4]).find("div").text("");//科目名称
	$($row[0].cells[5]).find("div").text("");//借
	$($row[0].cells[6]).find("div").text("");//贷
	$($row[0].cells[7]).find("div").text("");//科目编码
	$($row[0].cells[13]).find("div").text("0.00");//借
	$($row[0].cells[14]).find("div").text("0.00");//贷
	
	//删除辅助核算
	if(vouchCheckJson[jsonKey]){
		delete vouchCheckJson[jsonKey];
	}
	
	//删除现金流量
	if(vouchCashJson[jsonKey]){
		delete vouchCashJson[jsonKey];
	}
	
	if(liger.get("is_budg").getValue()==1){
		/**分栏式-预算会计**/
		jsonKey=$rowData.budg_did;
		if(!jsonKey){
			jsonKey=$rowData.budg_id;
		}
		$($row[0].cells[17]).find("div").text("");//预算会计摘要
		$($row[0].cells[18]).find("div").text("");//科目名称
		$($row[0].cells[19]).find("div").text("");//借
		$($row[0].cells[20]).find("div").text("");//贷
		$($row[0].cells[21]).find("div").text("0.00");//借
		$($row[0].cells[22]).find("div").text("0.00");//贷
		$($row[0].cells[23]).find("div").text("");//科目编码
		$($row[0].cells[24]).find("div").text("");//budg_did
		
		//删除辅助核算
		if(vouchCheckJson[jsonKey]){
			delete vouchCheckJson[jsonKey];
		}
		
		//删除现金流量
		if(vouchCashJson[jsonKey]){
			delete vouchCashJson[jsonKey];
		}
		
	}else{
		frameObj.setSubjFlag($row[0].cells[4]);//清除标记
	}
	
	frameObj.sumDebitCredit();//更新合计
	$($row[0].cells[3]).find("div").text("");//摘要
		
}

//复制
var copyRowIndex;
var copyColumnName="";
var pasteFlag=0;//1复制行、2复制单元格、3复制到剪贴板
function copyData(flag){
	
	copyRowIndex=0;
	
	//复制多行
	if(pasteFlag==1 && frameObj.grid.getSelectedRows().length>0){
		return;
	}
	
	var $activeCell = frameObj.grid.getActiveCell();	
	if(!$activeCell){
		return;
	}
	
	//3复制到剪贴板
	/*if(flag==3){
		var curText=$activeCell.find("div").text();
		//console.log( curText.substring(curText.selectionStart,curText.selectionEnd));
		var flag=copyText(curText);
		if(flag){
			Local.set("acc[vouch[copy_paste",curText);
			pasteFlag=3;
		}
		return;
	}
	Local.set("acc[vouch[copy_paste","");
	*/
	var $row=frameObj.grid.getCellRow($activeCell);
	copyRowIndex=$row.index();
	copyColumnName=$activeCell.data("column");
	pasteFlag=flag;
	
}

//复制到剪贴板
function copyText(text) {
	var textarea = document.createElement("input");//创建input对象
    var currentFocus = document.activeElement;//当前获得焦点的元素
    document.body.appendChild(textarea);//添加元素
    textarea.value = text;
    textarea.focus();
	
    if(textarea.setSelectionRange)
        textarea.setSelectionRange(0, textarea.value.length);//获取光标起始位置到结束位置
    else
        textarea.select();
    try {
        var flag = document.execCommand("copy");//执行复制
    } catch(eo) {
        var flag = false;
    }
	
    document.body.removeChild(textarea);//删除元素
    currentFocus.focus();
    return flag;
}

/**
 * 粘贴
 */
function pasteData(){
	
	var $activeCell = frameObj.grid.getActiveCell();
	if(!$activeCell || copyColumnName==""){
		return;
	}
	
	var $row,$rowDataNew,$rowDataOld,rowIndex;
	if(pasteFlag==1 && frameObj.grid.getSelectedRows().length>0){
		$.each(frameObj.grid.getSelectedRows(),function (n,obj){
			if(n==0){
				rowIndex=parseInt($($(".smart_menu_a")[0]).text().replace("行号：",""))-1;
			}else{
				rowIndex++;
			}
			$row=frameObj.grid.getRowByIndex(rowIndex);
			$rowDataNew=frameObj.grid.getRowDataByIndex(rowIndex);//新行
			$rowDataOld=frameObj.grid.getRowData($(obj));//旧行
			pasteDataCore();
			
		});
	}else{
		
		$row=frameObj.grid.getCellRow($activeCell);
		$rowDataNew=frameObj.grid.getRowData($row);//新行
		$rowDataOld=frameObj.grid.getRowDataByIndex(copyRowIndex);//旧行
		if($row.index()==copyRowIndex){
			return;
		}
		pasteDataCore();
	}
	frameObj.sumDebitCredit();//更新合计
	
	function pasteDataCore(){
		
		
		if(pasteFlag==1 || (pasteFlag ==2 && (copyColumnName=="subj_name" || copyColumnName=="debit_name"  || copyColumnName=="credit_name"))){
			/**
			 * 财务会计
			 */
			var subj_name=$rowDataOld.subj_name;
			if(subj_name!="" && liger.get("is_budg").getValue()==2){
				subj_name=subj_name.substring(0,subj_name.length-1);
				if($rowDataOld.subj_code!=""){
					//分屏式有预、财标记，需要加载样式
					frameObj.setSubjFlag($row[0].cells[4],$rowDataOld.subj_code);
				}
			}
			$($row[0].cells[4]).find("div").text(subj_name);//科目名称
			$($row[0].cells[5]).find("div").text($rowDataOld.debit_name);//借
			$($row[0].cells[6]).find("div").text($rowDataOld.credit_name);//贷
			$($row[0].cells[7]).find("div").text($rowDataOld.subj_code);//科目编码
			$($row[0].cells[13]).find("div").text($rowDataOld.debit);//借
			$($row[0].cells[14]).find("div").text($rowDataOld.credit);//贷
			frameObj.customStyle($($row[0].cells[5]),$rowDataOld.debit);//借方金额样式
			frameObj.customStyle($($row[0].cells[6]),$rowDataOld.credit);//贷方金额样式
					
			var jsonKey=$rowDataOld.did;
			if(!jsonKey){
				jsonKey=$rowDataOld.id;
			}
			//辅助核算
			if(vouchCheckJson[jsonKey]){
				//复制数据需要转换，否则会影响vouch.js这个方法setCheckCashMoney
				vouchCheckJson[$rowDataNew.id] = JSON.parse(JSON.stringify(vouchCheckJson[jsonKey]));
				for(var i=0;i<vouchCheckJson[$rowDataNew.id].length;i++){
					vouchCheckJson[$rowDataNew.id][i].cid="";
				}
			}else{
				delete vouchCheckJson[$rowDataNew.id];
			}

			//现金流量
			if(vouchCashJson[jsonKey]){
				vouchCashJson[$rowDataNew.id]= JSON.parse(JSON.stringify(vouchCashJson[jsonKey]));
				for(var i=0;i<vouchCashJson[$rowDataNew.id].length;i++){
					vouchCashJson[$rowDataNew.id][i].cid="";
				}
			}else{
				delete vouchCashJson[$rowDataNew.id];
			}
			
		}
		
		if(pasteFlag==1 || (pasteFlag ==2 && (copyColumnName=="budg_subj_name" || copyColumnName=="budg_debit_name"  || copyColumnName=="budg_credit_name"))){
			/**
			 * 分栏式-预算会计
			 */
			$($row[0].cells[17]).find("div").text($rowDataOld.budg_summary);//摘要
			$($row[0].cells[18]).find("div").text($rowDataOld.budg_subj_name);//科目名称
			$($row[0].cells[19]).find("div").text($rowDataOld.budg_debit_name);//借
			$($row[0].cells[20]).find("div").text($rowDataOld.budg_credit_name);//贷
			$($row[0].cells[21]).find("div").text($rowDataOld.budg_debit);//借
			$($row[0].cells[22]).find("div").text($rowDataOld.budg_credit);//贷
			$($row[0].cells[23]).find("div").text($rowDataOld.budg_subj_code);//科目编码
			frameObj.customStyle($($row[0].cells[19]),$rowDataOld.budg_debit);//借方金额样式
			frameObj.customStyle($($row[0].cells[20]),$rowDataOld.budg_credit);//贷方金额样式
			
			
			jsonKey=$rowDataOld.budg_did;
			if(!jsonKey){
				jsonKey=$rowDataOld.budg_id;
			}
			//辅助核算
			if(vouchCheckJson[jsonKey]){
				vouchCheckJson[$rowDataNew.budg_id] = JSON.parse(JSON.stringify(vouchCheckJson[jsonKey]));
				for(var i=0;i<vouchCheckJson[$rowDataNew.budg_id].length;i++){
					vouchCheckJson[$rowDataNew.budg_id][i].cid="";
				}
			}else{
				delete vouchCheckJson[$rowDataNew.budg_id];
			}
		
			//现金流量
			if(vouchCashJson[jsonKey]){
				vouchCashJson[$rowDataNew.budg_id]= JSON.parse(JSON.stringify(vouchCashJson[jsonKey]));
				for(var i=0;i<vouchCashJson[$rowDataNew.budg_id].length;i++){
					vouchCashJson[$rowDataNew.budg_id][i].cid="";
				}
			}else{
				delete vouchCashJson[$rowDataNew.budg_id];
			}
			
		}
		
		if(pasteFlag==1 || (pasteFlag ==2 && copyColumnName=="summary")){
			var summary=$rowDataOld.summary;
			if(summary!=""){
				
				if(pasteFlag ==2 && copyColumnName=="summary" && summary.indexOf("[")!=-1 && summary.indexOf("]")!=-1 && summary.substring(0,1)=="["){
					summary=summary.substring(summary.indexOf("]")+1,summary.length);
				}
				$($row[0].cells[3]).find("div").text(summary);//摘要
			}
		}
		
		$row.find(">td.selectable").html($("<input type=checkbox>"));
		$row.removeClass("sensei-grid-empty-row").addClass("sensei-grid-dirty-row");
		frameObj.grid.assureEmptyRow();//插入空行
	}
	
	
}

//更新科目对照
function updateBudgSubj(flag){
	
	//根据财务会计科目、资金来源匹配预算会计科目
	/*var isSourceMore=false;
	for(var key in vouchCheckJson){
		var count=0;
		$.each(vouchCheckJson[key],function(i,obj){
			if(obj["check7"] && obj["check7"]!=""){
				count++;
			}
			if(count>1){
				isSourceMore=true;
				return false;
			}
		});
	}
	if(isSourceMore){
		$.ligerDialog.error("按资金来源对应预算会计科目，有多对多的关系不能保存");
		return;
	}*/
	
	var $activeCell =frameObj.grid.getActiveCell();
	var subjCodeArray=[];
	//更新指定科目
	if(flag==1){
		
		if(!$activeCell){
			return;
		}
		
		var $row=frameObj.grid.getCellRow($activeCell);
		var accSubjCode=$($row[0].cells[7]).find("div").text();
		var budgSubjCode=$($row[0].cells[23]).find("div").text();
		if(!accSubjCode || accSubjCode==""){
			$.ligerDialog.error("财务会计科目不能为空");
			return;
		}
		
		if(!budgSubjCode || budgSubjCode==""){
			$.ligerDialog.error("预算会计科目不能为空");
			return;
		}
		subjCodeArray.push(accSubjCode+"-"+budgSubjCode);
		
	}else{
		
		$.each(frameObj.grid.getGridData(),function(index,obj){
			if(obj.subj_code!="" && obj.budg_subj_code!=""){
				subjCodeArray.push(obj.subj_code+"-"+obj.budg_subj_code);
			}
		});
	}
	
	if(subjCodeArray.length==0){
		$.ligerDialog.error("没有要更新的科目");
		return;
	}
	
	ajaxJsonObjectByUrl("updateBudgSubj.do?isCheck=false",{subj_code_str:subjCodeArray.toString()}, function(responseData) {
		
	});
	
}



//财务&预算
var budgDialog;
function myOpenBudg(){
	if($(document).find(".l-window-mask").css("display") == "block"){
		return;
	}
	
	if(liger.get("is_budg").getValue()==2){
		$.ligerDialog.error("分屏模式下不能显示财务&预算会计窗口.");
		return;
	}
	budgDialog=$.ligerDialog.open({id: 'budgDialog',url : 'vouchKindPage.do?isCheck=false',
	data:{grid:frameObj.grid},top:0, height: $(window).height()+40,width: $(window).width()+10, title:'',
	modal:false,showToggle:false,showMax:false,showMin: false,isResize:false,isDrag: false});
		
}


//差异标注
function myOpenDiff(){
	if($(document).find(".l-window-mask").css("display") == "block"){
		return;
	}
	
	/*$.ligerDialog.open({url : 'vouchDiffPage.do?isCheck=false',
	data:{
		grid: frameObj.grid
	}, height: 500,width: 900, title:'差异标注',
	modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,isDrag: true});*/
	
	$.etDialog.open({
		id: "myOpenDiff",
		url: 'vouchDiffPage.do?isCheck=false',
		frameName : window.name,
		width: 800,
		height: 350,
		shade: 0,
		zIndex:0,
		//offset: [$(window).height()-350-10,$(window).width()-800-90],
		maxmin: true,
		title: '差异标注',
		success: function(layero){
		   	 layer.setTop(layero);
		 },resizing: function(layero){
			  
		 },full: function(layero){
			 
			 
		  },min: function(layero){
			  
		  },restore: function(layero){
			 
		  }
	});
	
	
}

//加载凭证科目、摘要下拉列表
function loadVouchDict(){
	
	//加载科目
	if(subjDict.length==0){
		var vouchDate=$("#vouch_date").val();
		vouchDate=vouchDate.substring(0,4);//获取凭证日期的年度
		if(vouchDate!=""){
			ajaxJsonObjectByUrl("accVouchDictList.do?isCheck=false",{flag:1,acc_year: vouchDate},function (responseData){
				subjDict=responseData;
				//表格下拉框渲染
			    if(liger.get("is_budg").getValue()==1){
			    	frameObj.grid.editorProps["subj_name"] = subjDict;
			    	frameObj.grid.editorProps["budg_subj_name"] = subjDict;
				}else{
					frameObj.grid.editorProps["subj_name"] = subjDict;
				}
		    },false);
		}
	}else{
		//表格下拉框渲染
	    if(liger.get("is_budg").getValue()==1){
	    	frameObj.grid.editorProps["subj_name"] = subjDict;
	    	frameObj.grid.editorProps["budg_subj_name"] = subjDict;
		}else{
			frameObj.grid.editorProps["subj_name"] = subjDict;
		}
	}
	
	if(isReadOnly){
		return;
	}
	
	//加载摘要
	if(summaryDict.length==0){
		ajaxJsonObjectByUrl("accVouchDictList.do?isCheck=false",{flag:2},function (responseData){
			summaryDict=responseData;
			frameObj.grid.editorProps["summary"] = summaryDict;
		 },false);
	}else{
		frameObj.grid.editorProps["summary"] = summaryDict;
	}
	
}


/**********************右键菜单处理********begin*****************************/
function initMenu(){
	
	var menuData=[];
	menuData.push([{
        text: "render",
        func: function () {}
    }]);
	
	
	
	if(liger.get("is_budg").getValue()==1){
		//分栏式
		menuData.push([{
	        text: "复制",
	        data: [[{
		        text: "复制行",
		        func: function (event) {
		        	copyData(1);
		       }
		    },{
		        text: "复制单元格",
		        func: function (event) {
		        	copyData(2);
		       }
		    }]]
	    },
	    {
	        text: "粘贴",
	        func: function (event) {
	        	pasteData();
	        }
	    }]);
		
		
		menuData.push([{
	        text: "插入",
	        func: function (event) {
	       	 frameObj.grid.insertActiveRow();
	       }
	    },{
	        text: "删除",
	        data: [[{
		        text: "删除行",
		        func: function (event) {
		        	myDelDetail();
		       }
		    },{
		        text: "清除行数据",
		        func: function (event) {
		        	myRemoveRow();//删除数据不删除行
		       }
		    },{
		        text: "清除单元格",
		        func: function (event) {
		        	myRemoveCell();
		        }
		    },{
		        text: "删除所有预算会计",
		        func: function (event) {
		        	removeBudgData();
		       }
		    }]]
	    }]);
		
		menuData.push([{
	        text: "保存科目对照",
	        data: [[{
	        	text: "保存指定科目",
		        func: function (event) {
		        	updateBudgSubj(1);
		       }
	        },{
		        text: "保存所有科目",
		        func: function (event) {
		        	updateBudgSubj(2);
		       }
	        }]]
	    },{
	        text: "存摘要",
	        func: function (event) {
	       	 //var rownum=$(event.path[2]).find('li:first > a').text().replace("行号：","");
		       	var $activeCell =frameObj.grid.getActiveCell();
		    	if(!$activeCell){
		    		return;
		    	}
		    	var $row=frameObj.grid.getCellRow($activeCell);
		    	var summary=$($row[0].cells[3]).find("div").text();
		       	mySaveSummary(summary);
	       }
	    },{
	        text: "存模板",
	        func: function () {mySaveTemplate();}
	    },{
	        text: "存平行记账模板",
	        func: function () {mySavePxTemplate();}
	    },{
	        text: "取模板",
	        func: function () {myGetTemplate();}
	    }]);
		
	}else{
		//分屏式
		menuData.push([{
	        text: "复制",
	        data: [[{
		        text: "复制行",
		        func: function (event) {
		        	copyData(1);
		       }
		    },{
		        text: "复制单元格",
		        func: function (event) {
		        	copyData(2);
		       }
		    }]]
	    },
	    {
	        text: "粘贴",
	        func: function (event) {
	        	pasteData();
	        }
	    }]);
		
		menuData.push([{
	        text: "插入",
	        func: function (event) {
	       	 frameObj.grid.insertActiveRow();
	       }
	    }]);
		
		if(copyNature=="02"){
			//医院企业单位
			menuData.push([{
		        text: "删除",
		        data: [[{
			        text: "删除行",
			        func: function (event) {
			        	myDelDetail();
			       }
			    },{
			        text: "清除行数据",
			        func: function (event) {
			        	myRemoveRow();//删除数据不删除行
			       }
			    }]]
		    }]);
			
			menuData.push([{
		        text: "存摘要",
		        func: function (event) {
		       	 //var rownum=$(event.path[2]).find('li:first > a').text().replace("行号：","");
			       	var $activeCell =frameObj.grid.getActiveCell();
			    	if(!$activeCell){
			    		return;
			    	}
			    	var $row=frameObj.grid.getCellRow($activeCell);
			    	var summary=$($row[0].cells[3]).find("div").text();
			       	mySaveSummary(summary);
		       }
		    },{
		        text: "存模板",
		        func: function () {mySaveTemplate();}
		    },{
		        text: "取模板",
		        func: function () {myGetTemplate();}
		    }]);
			
		}else{
			menuData.push([{
		        text: "删除",
		        data: [[{
			        text: "删除行",
			        func: function (event) {
			        	myDelDetail();
			       }
			    },{
			        text: "清除行数据",
			        func: function (event) {
			        	myRemoveRow();//删除数据不删除行
			       }
			    },{
			        text: "删除所有预算会计",
			        func: function (event) {
			        	removeBudgData();
			       }
			    }]]
		    }]);
			
			menuData.push([{
		        text: "存摘要",
		        func: function (event) {
		       	 //var rownum=$(event.path[2]).find('li:first > a').text().replace("行号：","");
			       	var $activeCell =frameObj.grid.getActiveCell();
			    	if(!$activeCell){
			    		return;
			    	}
			    	var $row=frameObj.grid.getCellRow($activeCell);
			    	var summary=$($row[0].cells[3]).find("div").text();
			       	mySaveSummary(summary);
		       }
		    },{
		        text: "存模板",
		        func: function () {mySaveTemplate();}
		    },{
		        text: "存平行记账模板",
		        func: function () {mySavePxTemplate();}
		    },{
		        text: "取模板",
		        func: function () {myGetTemplate();}
		    }]);
		}
		
		
	}
	
	if(copyNature!="02"){
		menuData.push([{
	        text: "平行记账",
	        data: [[{
		        text: "按对照生成",
		        func: function (event) {
		        	updateAllBudgData();
		       }
		    },{
		        text: "平行记账模板",
		        func: function (event) {
		        	openVouchBudg();
		       }
		    }]]
	    }]);
	}
	
	menuData.push([{
        text: "其他",
        data: [[{
            text: "摘要管理",
            func: function () {myGetSummary();}
        },{
            text: "辅助核算",
            func: function () {checkGrid();}
        },{
	        text: "快捷键说明",
	        func: function (event) {
	        	myHelp();
	       }
	    }]]
    }]);
	
	$("#menuDiv").smartMenu(menuData,{
        name:"rightMenu",
        offsetX:2,
        offsetY:2,
        textLimit:6,
        windowsObj: 'frameObj',
        topsetX:115,
        beforeShow:function(e) {
        	$("#checkDiv").hide();
        	return readRowNum(e);
        },
        afterShow:function() {

        }
    });
	
}

//加载行号
function readRowNum(e){
	var textNew = "";
	var event = window.event||frameObj.window.event;//iframe对象
	//  根据鼠标所点击的元素的父元素是否为thead来判断是否为表头
	if($(event.srcElement).parents('thead')[0]){   
		textNew = "行号：0";
	}else if($(event.srcElement).parents('tr','.sensei-grid-tbody')[0]){
		//   取出父元素tr的索引值
		var RowNum = $(event.srcElement).parents('tr','.sensei-grid-tbody').index() + 1;
		textNew = "行号：" + RowNum;
	}else if(frameObj.grid.activeEditor){  //  若点击是编辑框，则取出.activeCell类td的父元素tr索引值
		var $activeCell =frameObj.grid.getActiveCell();
		var RowNum = $activeCell.parents('tr','.sensei-grid-tbody').index() + 1;
		textNew = "行号：" + RowNum;
	}
	
	return textNew;
}

/**********************右键菜单处理********end*****************************/

/**********************参数044默认弹出辅助核算层********begin*****************************/
var columnCheckSel={};
function showCheckDiv(cellObj){
	
	var scrollTop = frameObj.document.documentElement.scrollTop || frameObj.window.pageYOffset || frameObj.document.body.scrollTop;
	//console.log(cellObj.scrollTop+"："+cellObj.activeCell.offset().top+"："+cellObj.activeCell.position().top)
	$("#checkTable").html("");
	$("#checkDiv").css({
		display: "block",
		left: cellObj.activeCell.offset().left-5,
		top: $("#topDiv").height()+cellObj.activeCell.offset().top+15-scrollTop
	});
	
	var defaultDate="";
	var defaultCash = "";
	var defaultMoney = 0.00;
	var subjAttr=cellObj.subjAttr;
	columnCheckSel={};
	var defaultCheckNo="";
	var $row=frameObj.grid.getCellRow(cellObj.activeCell);
	var $rowData=frameObj.grid.getRowData($row);
	var columnName=cellObj.activeCell.data("column");
	
	if(columnName=="subj_name"){
		if(parseFloat($rowData.debit)!=0){
			defaultMoney=$rowData.debit;
		}else if(parseFloat($rowData.credit)!=0){
			defaultMoney=$rowData.credit;
		}
	}else if(columnName=="budg_subj_name"){
		if(parseFloat($rowData.budg_debit)!=0){
			defaultMoney=$rowData.budg_debit;
		}else if(parseFloat($rowData.budg_credit)!=0){
			defaultMoney=$rowData.budg_credit;
		}
	}
	
	if(cellObj.type=="cash"){
		//现金流量
		$("#checkTable").append('<tr><td>现金流量：</td><td><input id="check_cash" style="width: 320px;"/></td><tr>');
		
		if(vouchCashJson[cellObj.jsonKey]!=undefined && vouchCashJson[cellObj.jsonKey].length==1){
			defaultCash=vouchCashJson[cellObj.jsonKey][0].cash_item_id;
			if(parseFloat(defaultMoney)==0)defaultMoney=vouchCashJson[cellObj.jsonKey][0].money;
			
		}
		
		columnCheckSel["check_cash"]=$("#check_cash").ligerComboBox({
			url: "accCheckDictList.do?isCheck=false",
			parms: {column_id: 'cash_item_id'},
			data: checkDict["cash"],
			valueField: 'id',
	     	textField: 'name',
	     	initValue: defaultCash,
		    cancelable: true,
		    autocomplete: true,
		    autocompletelocal: true,
		    width: 320,
		    selectBoxWidth: 430,
		    selectBoxHeight: 200,
		    setTextBySource: true,
		    keySupport: true,
		    alwayShowInDown: true,
		    highLight: true,
		    async: false,
		    initIsTriggerEvent: false,
		    autocompleteAllowEmpty : true,
		    onSuccess :function (data){
		    	checkDict["cash"]=data;
		    },
		    onBeforeSelect: function (newvalue){
		    	//console.log("onBeforeSelect")
		    	if(newvalue==liger.get("check_cash").getValue()){
		    		return false;
		    	}
		    },
		    onSelected: function (selectValue,selectText){
		    	//console.log("onSelected",selectValue)
		    	if(selectValue==null || selectValue==""){
		    		return;
		    	}
		    	updateVouchCashJson(selectValue,selectText);
	    	}
		});
		
		//加载默认值
		/*if(defaultCash && defaultCash!=""){
			$.each(columnCheckSel["check_cash"].data,function(i,o){
				if(o.id==defaultCash){
					liger.get("check_cash").setValue(defaultCash);
					return false;
				}
			});
		}*/
		
	}else{
		//辅助核算
		if(vouchCheckJson[cellObj.jsonKey]!=undefined && vouchCheckJson[cellObj.jsonKey].length==1){
			if(parseFloat(defaultMoney)==0)defaultMoney=vouchCheckJson[cellObj.jsonKey][0].money;
		}
		
		//现金流量
		if(subjAttr.is_cash=="1" && paraList["004"]=="1" && paraList["026"]==1){
			
			$("#checkTable").append('<tr><td>现金流量：</td><td><input id="check_cash" style="width: 320px;"/></td></tr>');
		
			if(vouchCheckJson[cellObj.jsonKey]!=undefined && vouchCheckJson[cellObj.jsonKey].length==1){
				defaultCash=vouchCheckJson[cellObj.jsonKey][0].cash_item_id;
			}
			
			columnCheckSel["check_cash"]=$("#check_cash").ligerComboBox({
				url: "accCheckDictList.do?isCheck=false",
				parms: {column_id: 'cash_item_id'},
				data: checkDict["cash"],
				valueField: 'id',
		     	textField: 'name',
		     	initValue: defaultCash,
			    cancelable: true,
			    autocomplete: true,
			    autocompletelocal: true,
			    width: 320,
			    selectBoxWidth: 430,
			    selectBoxHeight: 200,
			    setTextBySource: true,
			    keySupport: true,
			    alwayShowInDown: true,
			    highLight: true,
			    async: true,
			    initIsTriggerEvent: false,
			    autocompleteAllowEmpty : false,
			    onSuccess :function (data){
			    	checkDict["cash"]=data;
			    },
			    onBeforeSelect: function (newvalue){
			    	//console.log("onBeforeSelect")
			    	if(newvalue==liger.get("check_cash").getValue()){
			    		return false;
			    	}
			    },
			    onSelected: function (selectValue,selectText){
			    	//console.log("onSelected",selectValue)
			    	if(selectValue==null || selectValue==""){
			    		return;
			    	}
			    	updateVouchCheckJson({
						kv: "cash_item_id",
						value: selectValue,
						kt: "cash_name",
						text: selectText
					});
		    	}
			});
			
			//加载默认值
			/*if(defaultCash && defaultCash!=""){
				$.each(columnCheckSel["check_cash"].data,function(i,o){
					if(o.id==defaultCash){
						liger.get("check_cash").setValue(defaultCash);
						return false;
					}
				});
			}*/
		}
		
		//科目性质=03银行科目
		if(subjAttr.subj_nature_code=="03"){
			
			$("#checkTable").append('<tr><td>结算方式：</td><td><input id="pay_type_code" style="width: 320px;"/><td></tr>');
		
			var defaultPayTypeCode="";
			if(vouchCheckJson[cellObj.jsonKey]!=undefined && vouchCheckJson[cellObj.jsonKey].length==1){
				defaultPayTypeCode=vouchCheckJson[cellObj.jsonKey][0].pay_type_code;
			}
						
			columnCheckSel["pay_type_code"]=$("#pay_type_code").ligerComboBox({
				url: "accCheckDictList.do?isCheck=false",
				parms: {column_id: 'pay_code'},
				data: checkDict["payType"],
				valueField: 'id',
		     	textField: 'name',
		     	initValue: defaultPayTypeCode,
			    cancelable: true,
			    autocomplete: true,
			    autocompletelocal: true,
			    width: 320,
			    selectBoxWidth: 430,
			    selectBoxHeight: 200,
			    setTextBySource: true,
			    keySupport: true,
			    alwayShowInDown: true,
			    highLight: true,
			    async: true,
			    initIsTriggerEvent: false,
			    autocompleteAllowEmpty : false,
			    onSuccess :function (data){
			    	checkDict["payType"]=data;
			    },
			    onBeforeSelect: function (newvalue){
			    	//console.log("onBeforeSelect")
			    	if(newvalue==liger.get("pay_type_code").getValue()){
			    		return false;
			    	}
			    },
			    onSelected: function (selectValue,selectText){
			    	//console.log("onSelected",selectValue)
			    	if(selectValue==null || selectValue==""){
			    		return;
			    	}
			    	updateVouchCheckJson({
						kv: "pay_type_code",
						value: selectValue,
						kt: "pay_name",
						text: selectText
					});
		    	}
			});
			
			//加载默认值
			/*if(defaultPayTypeCode && defaultPayTypeCode!=""){
				$.each(columnCheckSel["pay_type_code"].data,function(i,o){
					if(o.id==defaultPayTypeCode){
						liger.get("pay_type_code").setValue(defaultPayTypeCode);
						return false;
					}
				});
			}*/
		}
		
		//票据
		if(subjAttr.is_bill=="1"){
			
			$("#checkTable").append('<tr><td>票据类型：</td><td><input id="paper_type_code" style="width: 320px;"/></td></tr>');
			
			var defaultPaperTypeCode="";
			if(vouchCheckJson[cellObj.jsonKey]!=undefined && vouchCheckJson[cellObj.jsonKey].length==1){
				defaultPaperTypeCode=vouchCheckJson[cellObj.jsonKey][0].paper_type_code;
			}
			
			$("#checkTable").append('<tr><td>票&nbsp;据&nbsp;号&nbsp;：</td><td><input id="check_no" style="width: 320px;"/></td></tr>');
			
			if(vouchCheckJson[cellObj.jsonKey]!=undefined && vouchCheckJson[cellObj.jsonKey].length==1){
				defaultCheckNo=vouchCheckJson[cellObj.jsonKey][0].check_no;
			}
						
			//票据类型
			columnCheckSel["paper_type_code"]=$("#paper_type_code").ligerComboBox({
				url: "accCheckDictList.do?isCheck=false",
				parms: {column_id: 'paper_type_code'},
				data: checkDict["paperType"],
				valueField: 'id',
		     	textField: 'name',
		     	initValue: defaultPaperTypeCode,
			    cancelable: true,
			    autocomplete: true,
			    autocompletelocal: true,
			    width: 320,
			    selectBoxWidth: 430,
			    selectBoxHeight: 200,
			    setTextBySource: true,
			    keySupport: true,
			    alwayShowInDown: true,
			    highLight: true,
			    async: true,
			    initIsTriggerEvent: true,
			    autocompleteAllowEmpty : false,
			    onSuccess :function (data){
			    	checkDict["paperType"]=data;
			    	//加载票据号默认值
					setDefaultCheckNO(defaultCheckNo);
			    },
			    onBeforeSelect: function (newvalue){
			    	//console.log("onBeforeSelect")
			    	//reloadCheckNoSelect(newvalue);
			    	if(newvalue==liger.get("paper_type_code").getValue()){
			    		return false;
			    	}
			    },
			    onSelected: function (selectValue,selectText){
			    	//console.log("onSelected",selectValue)
		        	//票据号
					reloadCheckNoSelect(selectValue);
		        	
			    	if(selectValue==null || selectValue==""){
			    		return;
			    	}
			    	updateVouchCheckJson({
						kv: "paper_type_code",
						value: selectText
					});
		    	}
			});
			
			//加载默认值
			/*if(defaultPaperTypeCode && defaultPaperTypeCode!=""){
				$.each(columnCheckSel["paper_type_code"].data,function(i,o){
					if(o.name==defaultPaperTypeCode){
						liger.get("paper_type_code").setValue(defaultPaperTypeCode.split(" ")[0]);
						return false;
					}
				});
			}*/
			
	    }
		
		//处理辅助核算
		if(subjAttr.is_check=="1"){
			
			var acc_year=$("#vouch_date").val().substring(0,4);
			ajaxJsonObjectByUrl("queryVouchCheckType.do?isCheck=false",{subj_code: subjAttr.id,acc_year: acc_year},function (resData){
				var typeData=JSON.parse(resData.data);
				$.each(typeData,function(i,obj){
					if(!obj.column_no){
						obj.column_no="";
					}
					
					$("#checkTable").append('<tr><td>'+obj.check_type_name+'：</td><td><input id='+obj.column_check+' style="width: 320px;"/></td></tr>');
					
					var defaultCheck="";
					var defaultCheckText="";
					if(vouchCheckJson[cellObj.jsonKey]!=undefined && vouchCheckJson[cellObj.jsonKey].length==1){
						defaultCheck=vouchCheckJson[cellObj.jsonKey][0][obj.column_check];
						defaultCheckText=vouchCheckJson[cellObj.jsonKey][0][obj.column_check+"_name"];
					}
					
					//核算分类编码字段
					var check_type_code="";
					//console.log(JSON.stringify(checkJson[i]))
					if(obj.check_type_code!=undefined && obj.check_type_code!=""){
						//替换条件1;2;3--> '1','2','3'
						check_type_code="'"+obj.check_type_code.replace(/\;/g, "','")+"'";
					}
					
					//部门类型字段
					if(check_type_code!="" && obj.check_table=="hos_dept_dict"){
						check_type_code=" and a.dept_id in (select attr.dept_id from ACC_DEPT_ATTR attr where attr.group_id=a.group_id and " +
						" attr.hos_id=a.hos_id and attr.type_code in ("+check_type_code+")) ";
					}
					//职工分类字段
					else if(check_type_code!="" && obj.check_table=="hos_emp_dict"){
						check_type_code=" and a.kind_code in("+check_type_code+") ";
					}
//					//项目分类字段
//					else if(check_type_code!="" && checkJson[i].check_table=="hos_proj_dict"){
//						check_type_code_filed="type_code";
//					}
//					//库房分类字段
//					else if(check_type_code!="" && checkJson[i].check_table=="hos_store_dict"){
//						check_type_code_filed="type_code";
//					}
//					//客户分类字段
//					else if(check_type_code!="" && checkJson[i].check_table=="hos_cus_dict"){
//						check_type_code_filed="type_code";
//					}
//					//供应商分类字段
//					else if(check_type_code!="" && checkJson[i].check_table=="hos_sup_dict"){
//						check_type_code_filed="type_code";
//					}
					else if(check_type_code!="" && obj.check_table=="hos_source"){
						check_type_code=" and a.source_attr in("+check_type_code+") ";
					}else if(check_type_code!=""){
						//默认条件
						check_type_code=" and a.type_code in("+check_type_code+") ";
					}
					
					
					var para={						
							check_id: obj.check_id,
							column_id: obj.column_id,
							column_no: obj.column_no,
							column_code: obj.column_code,
							column_name: obj.column_name,
							check_table: obj.check_table,
							column_check: obj.column_check,
							check_type_code: check_type_code,
							//key: comInitId,
							out_code: subjAttr.out_code//支出性质
					};
					
					columnCheckSel[obj.column_check]=$("#"+obj.column_check).ligerComboBox({
						url: "accCheckDictList.do?isCheck=false",
						parms: para,
						data: checkDict[obj.column_check],
						valueField: 'idno',
				     	textField: 'name',
				     	initValue: defaultCheck,
					    cancelable: true,
					    autocomplete: true,
					    autocompletelocal: true,
					    width: 320,
					    selectBoxWidth: 430,
					    selectBoxHeight: 200,
					    setTextBySource: true,
					    keySupport: true,
					    alwayShowInDown: true,
					    highLight: true,
					    async: true,
					    initIsTriggerEvent: false,
					    autocompleteAllowEmpty : false,
					    onSuccess: function (data){
					    	if(obj.column_id!="dept_id" && check_type_code==""){
					    		checkDict[obj.column_check]=data;
					    	}
					    },
					    onBeforeSelect: function (newvalue){
					    	//console.log("onBeforeSelect")
					    	if(newvalue==liger.get(obj.column_check).getValue()){
					    		return false;
					    	}
					    },
					    onSelected: function (selectValue,selectText){
					    	//console.log("onSelected",selectValue)
					    	if(selectValue==null || selectValue==""){
					    		return;
					    	}
					    	var checkItemVal="";
					    	if(i==0){
					    		//第一个辅助做为摘要
								checkItemVal= selectText;
							}
				        	updateVouchCheckJson({
								kv: obj.column_check,
								value: selectValue,
								kt: obj.column_check+"_name",
								text: selectText,
								checkItemVal: checkItemVal
							});
				    	}
					});
										
					//加载默认值
					/*	if(defaultCheck && defaultCheck!=""){
						var isItem=false;
						$.each(columnCheckSel[obj.column_check].data,function(i,o){
							if(o.idno==defaultCheck){
								isItem=true;
								return false;
							}
						});
						if(!isItem){
							columnCheckSel[obj.column_check].addItem({idno: defaultCheck,name: defaultCheckText});
						}
						columnCheckSel[obj.column_check].setValue(defaultCheck);
						//columnCheckSel[obj.column_check].setText(defaultCheckText);
					}
					*/
					
					
				});
				
		    },false);
		}
		
		//科目性质=04、05应收、应付科目 
	    if(subjAttr.subj_nature_code=="04" || subjAttr.subj_nature_code=="05"){
	    	
	    	if(paraList["049"]==1){
	    		var defaultBusinessNo="";
				if(vouchCheckJson[cellObj.jsonKey]!=undefined && vouchCheckJson[cellObj.jsonKey].length==1){
					if(vouchCheckJson[cellObj.jsonKey][0].business_no && vouchCheckJson[cellObj.jsonKey][0].business_no!=""){
						defaultBusinessNo=vouchCheckJson[cellObj.jsonKey][0].business_no;
					}
				}
		    	$("#checkTable").append('<tr><td>单&nbsp;据&nbsp;号&nbsp;：</td><td><input id="business_no" value="'+defaultBusinessNo+'" style="width: 318px;height:24px;border:1.3px solid #AECAF0"/></td></tr>');
		    	
		    	$("#business_no").on('blur', function() {
		    		updateVouchCheckJson({
		    			kv: "business_no",
		    			value: $("#business_no").val()
		    		});
		    	});
	    	}
	    	
	    	if(paraList["035"]==1){
	    		var defaultConNo="";
				if(vouchCheckJson[cellObj.jsonKey]!=undefined && vouchCheckJson[cellObj.jsonKey].length==1){
					if(vouchCheckJson[cellObj.jsonKey][0].con_no && vouchCheckJson[cellObj.jsonKey][0].con_no!=""){
						defaultConNo=vouchCheckJson[cellObj.jsonKey][0].con_no;
					}
				}
		    	$("#checkTable").append('<tr><td>合同编号：</td><td><input id="con_no" value="'+defaultConNo+'" style="width: 318px;height:24px;border:1.3px solid #AECAF0"/></td></tr>');
		    	
		    	$("#checkTable").append('<tr><td>发生日期：</td><td><input id="occur_date" style="width: 120px;"/></td></tr>');
		    	
		    	var defaultOccurDate=$("#vouch_date").val();
				if(vouchCheckJson[cellObj.jsonKey]!=undefined && vouchCheckJson[cellObj.jsonKey].length==1){
					if(vouchCheckJson[cellObj.jsonKey][0].occur_date && vouchCheckJson[cellObj.jsonKey][0].occur_date!=""){
						defaultOccurDate=vouchCheckJson[cellObj.jsonKey][0].occur_date;
					}
				}
		    	
		    	$("#checkTable").append('<tr><td>到期日期：</td><td><input id="due_date" style="width: 120px;"/></td></tr>');
		    	
		    	$("#con_no").on('blur', function() {
		    		updateVouchCheckJson({
		    			kv: "con_no",
		    			value: $("#con_no").val()
		    		});
		    	});
		    	$("#occur_date").on('blur', function() {
		    		updateVouchCheckJson({
		    			kv: "occur_date",
		    			value: $("#occur_date").val()
		    		});
		    	});
		    	$("#due_date").on('blur', function() {
		    		updateVouchCheckJson({
		    			kv: "due_date",
		    			value: $("#due_date").val()
		    		});
		    	});
		    	
		    	var occurDate = $("#occur_date").etDatepicker({
					range: false,
					readonly: false,
					dateFormat: "yyyy-mm-dd",
					defaultDate: [defaultOccurDate],
					onChange: function (value) {
						updateVouchCheckJson({
			    			kv: "occur_date",
			    			value: value
			    		});
					}
				});
		    	
		    	var date = new Date();
		    	if(paraList["027"]==0){
			    	//到期日期默认月底
			    	var lastDay=getLastDay(date.getFullYear(),date.getMonth() + 1);
			    	defaultDate=date.getFullYear()+"-"+(date.getMonth() + 1)+"-"+lastDay;
			    }else{
			    	//到期日期默认年底
			    	defaultDate=date.getFullYear()+"-12-31";
			    }
		    	
		    	var defaultDueDate=defaultDate;
				if(vouchCheckJson[cellObj.jsonKey]!=undefined && vouchCheckJson[cellObj.jsonKey].length==1){
					if(vouchCheckJson[cellObj.jsonKey][0].due_date && vouchCheckJson[cellObj.jsonKey][0].due_date!=""){
						defaultDueDate=vouchCheckJson[cellObj.jsonKey][0].due_date;
					}
				}
		    	
		    	var dueDate = $("#due_date").etDatepicker({
					range: false,
					readonly: false,
					dateFormat: "yyyy-mm-dd",
					defaultDate: [defaultDueDate],
					onChange: function (value) {
						updateVouchCheckJson({
			    			kv: "due_date",
			    			value: value
			    		});
					}
				});
	    	}
	    }
	}
	
	$("#checkTable :input")[0].focus();
	if($($("#checkTable :input")[0]).val()!=""){
		$($("#checkTable :input")[0]).select();
	}
	$('#checkTable').off('keydown', 'input');
	$('#checkTable').on('keydown', 'input', function(e) {
	    var self = $(this)
	      , table = self.parents('table:eq(0)')
	      , focusable
	      , next;
	    if (e.keyCode == 13) {
	        focusable = table.find('input').filter(':visible');
	        //console.log(focusable.eq(focusable.index(this)))
	        next = focusable.eq(focusable.index(this)+1);
	        var comboboxid=$(this).attr("data-comboboxid");
	        if (next.length) {
	        	//下拉框
	        	if(comboboxid){
	        		var combobox=columnCheckSel[comboboxid];
	        		ligerToSelect(combobox);
	        		if(!ligerToDefault($(this),combobox)){
	        			return false;
	        		}
	        		
	        		if(comboboxid=="paper_type_code"){
	        			if($(this).val()==""){
	        				updateVouchCheckJson({
								kv: "paper_type_code",
								value: ''
							});
	        			}
	        		}else if(comboboxid=="pay_type_code"){
	        			if($(this).val()==""){
	        				updateVouchCheckJson({
								kv: "pay_type_code",
								value: '',
								kt: "pay_name",
								text: ''
							});
	        			}
	        		}else if(comboboxid=="check_no"){	        			
	        			if($("#paper_type_code").val()!="" && $(this).val()==""){
	        				return false;
	        			}
	        			
        				setDefaultCheckNO($(this).val());
        				updateVouchCheckJson({
        		    		kv: "check_no",
        					value: $(this).val(),
        				});
	        			
	        		}else if($(this).val()==""){
		        		return false;
		        	}
	        		
	        	}
	        	
	        	if($(next).val()!=""){
	        		$(next).select();
	        	}
	            next.focus();
	        } else {
	        	
	        	//下拉框
	        	if(comboboxid){
	        		var combobox=columnCheckSel[comboboxid];
	        		ligerToSelect(combobox);
	        		if(!ligerToDefault($(this),combobox)){
	        			return false;
	        		}
	        		
	        		if(comboboxid=="paper_type_code"){
	        			if($(this).val()==""){
	        				updateVouchCheckJson({
								kv: "paper_type_code",
								value: ''
							});
	        			}
	        		}else if(comboboxid=="pay_type_code"){
	        			if($(this).val()==""){
	        				updateVouchCheckJson({
								kv: "pay_type_code",
								value: '',
								kt: "pay_name",
								text: ''
							});
	        			}
	        		}else if(comboboxid=="check_no"){
	        			if($("#paper_type_code").val()!="" && $(this).val()==""){
	        				return false;
	        			}
	        			
        				setDefaultCheckNO($(this).val());
        				updateVouchCheckJson({
        		    		kv: "check_no",
        					value: $(this).val()
        				});
	        			
	        			//checkDivCallBack({activeCell: cellObj.activeCell,rowData: cellObj.rowData});
	        		}else if($(this).val()==""){
		        		return false;
		        	}
	        	}
	        	
	        	if($(next).val()!=""){
	        		$(next).select();
	        	}
	        
	        	checkDivCallBack({activeCell: cellObj.activeCell,rowData: cellObj.rowData});
	        }
	        return false;
         }
    });
	
	function ligerToSelect(combobox){
		
    	if (!combobox.selectBox.is(":visible")) return;
        var curTd = combobox.selectBox.table.find("td.l-over");
       
        if (curTd.length){
            var value = curTd.attr("value");
            if (combobox.enabledLoadDetail()){
                combobox.loadDetail(value, function (data){
                    var index = combobox.getRowIndex(value);
                    if (index == -1) return;
                    combobox.data = combobox.data || [];
                    combobox.data[index] = combobox.selected = data;
                    
                    combobox._changeValue(value, curTd.attr("text"), true);
                    combobox.selectBox.hide();
                    combobox.trigger('textBoxKeyEnter', [{
                        element: curTd.get(0)
                    }]);
                    
                });
            } else{
            	combobox._changeValue(value, curTd.attr("text"), true);
                combobox.selectBox.hide();
                combobox.trigger('textBoxKeyEnter', [{
                    element: curTd.get(0)
                }]);
            }
        }
	}
	
	
	function ligerToDefault(_this,combobox){
		
		if(_this.val()==""){
			combobox.selectBox.hide();
    		return true;
    	}
		
		var isItem=false;
		if(combobox && combobox.data.length>0){
			
			$.each(combobox.data,function(i,o){
				if(o.name==combobox.getText()){
					combobox.setValue(o.idno===undefined?o.id:o.idno);
					isItem=true;
					return false;
				}
			});
			/*if(!isItem && combobox.id!="check_no"){
				combobox.setValue(combobox.data[0].idno===undefined?combobox.data[0].id:combobox.data[0].idno);
			}*/
		}
		
		if(combobox.id=="check_no"){
			isItem=true;
		}
		combobox.selectBox.hide();
		return isItem;
		
	}
	
	function setDefaultCheckNO(checkNo){
		
		if(checkNo && checkNo!=""){
			var isItem=false;
			$.each(columnCheckSel["check_no"].data,function(i,o){
				if(o.id==checkNo){
					isItem=true;
					return false;
				}
			});
			if(!isItem){
				columnCheckSel["check_no"].addItem({id: checkNo,name: checkNo});
			}
			columnCheckSel["check_no"].setValue(checkNo);
		}
		
	}
	

	//更新VouchCashJson
	function updateVouchCashJson(selectValue,selectText){
		
		var rowData=[];
		if(vouchCashJson[cellObj.jsonKey]!=undefined && vouchCashJson[cellObj.jsonKey].length==1){
			rowData=vouchCashJson[cellObj.jsonKey];
			rowData[0]["cash_item_id"]=selectValue;
			rowData[0]["cash_name"]=selectText;
		}else{
			rowData=[{"id":1,"cid":"","summary":""+cellObj.rowData.summary+"","cash_name":""+selectText+"","cash_item_id":""+selectValue+"","money":""+defaultMoney+""}];
		}
		
		
		vouchCashJson[cellObj.jsonKey] = rowData;
		
		//拼接分录的摘要：[辅助核算内容]摘要
		if(paraList["050"]==1 && selectText!=undefined && selectText!=""){
			var $row=frameObj.grid.getCellRow(cellObj.activeCell);
			var checkItemVal=selectText.split(" ")[1];
			var cellsText3=$($row[0].cells[3]).find("div").text();
			if(cellsText3!="" && cellsText3.indexOf("[")!=-1 && cellsText3.indexOf("]")!=-1){
				cellsText3=cellsText3.substring(cellsText3.indexOf("]")+1,cellsText3.length);
			}
			$($row[0].cells[3]).find("div").text("["+checkItemVal+"]"+cellsText3);//摘要+现金流量
		}
	}
	
	//更新VouchCheckJson
	function updateVouchCheckJson(ele){
		
		var rowData=[];
		if(vouchCheckJson[cellObj.jsonKey]!=undefined && vouchCheckJson[cellObj.jsonKey].length==1){
			rowData=vouchCheckJson[cellObj.jsonKey];
		}else{
			rowData=[{"id":1,"cid":"","summary":""+cellObj.rowData.summary+"","money":""+defaultMoney+""}];
		}
		
		rowData[0][ele.kv]=ele.value;
		if(ele.kt){
			rowData[0][ele.kt]=ele.text;
		}
		
		
		vouchCheckJson[cellObj.jsonKey] = rowData;
		
		//拼接分录的摘要：[辅助核算内容]摘要
		if(paraList["036"]==1 && ele.checkItemVal!=undefined && ele.checkItemVal!=""){
			var $row=frameObj.grid.getCellRow(cellObj.activeCell);
			ele.checkItemVal=ele.checkItemVal.split(" ")[1];
			var cellsText3=$($row[0].cells[3]).find("div").text();
			if(cellsText3!="" && cellsText3.indexOf("[")!=-1 && cellsText3.indexOf("]")!=-1){
				cellsText3=cellsText3.substring(cellsText3.indexOf("]")+1,cellsText3.length);
			}
			$($row[0].cells[3]).find("div").text("["+ele.checkItemVal+"]"+cellsText3);//摘要+辅助核算	
		}else if(paraList["050"]==1 && ele.kv=="cash_item_id" && ele.selectText!=undefined && ele.selectText!=""){
			var $row=frameObj.grid.getCellRow(cellObj.activeCell);
			ele.selectText=ele.selectText.split(" ")[1];
			var cellsText3=$($row[0].cells[3]).find("div").text();
			if(cellsText3!="" && cellsText3.indexOf("[")!=-1 && cellsText3.indexOf("]")!=-1){
				cellsText3=cellsText3.substring(cellsText3.indexOf("]")+1,cellsText3.length);
			}
			$($row[0].cells[3]).find("div").text("["+ele.selectText+"]"+cellsText3);//摘要+现金流量
		}
	
	}
	
	//级联票据号
	function reloadCheckNoSelect(value) {
		/*if(value!=""){
			checkNoSel.setValue("");
			checkNoSel.clearOptions();
			checkNoSel.reload({
				url: "accCheckDictList.do?isCheck=false",
				para: {column_id: 'check_no',paper_type_code: value},
			});
		}*/
		/*liger.get("check_no").clear();
		liger.get("check_no").set("parms", {column_id: 'check_no',paper_type_code: value});
		liger.get("check_no").reload();*/
		if(liger.get("check_no")){
			liger.get("check_no").clear();
    		liger.get("check_no").setValue("");
        	liger.get("check_no").setText("");
    	}
			
		//票据号
    	columnCheckSel["check_no"]=$("#check_no").ligerComboBox({
			url: "accCheckDictList.do?isCheck=false",
			parms: {column_id: 'check_no',paper_type_code: value},
			valueField: 'id',
	     	textField: 'name',
		    cancelable: true,
		    autocomplete: true,
		    autocompletelocal: true,
		    width: 320,
		    selectBoxWidth: 430,
		    selectBoxHeight: 200,
		    setTextBySource: true,
		    keySupport: true,
		    alwayShowInDown: true,
		    highLight: true,
		    async: true,
		    initIsTriggerEvent: false,
		    autocompleteAllowEmpty : false,
		    onSuccess :function (checkData){
		    	
		    },
		    onSelected: function (checkValue,checkText){
		    	//console.log("onSelected",selectValue)
		    	if(checkValue==null || checkValue==""){
		    		return;
		    	}
		    
		    	updateVouchCheckJson({
		    		kv: "check_no",
					value: checkValue,
				});
	    	}
		});
    	
	}
}

//控制焦点
function checkDivCallBack(parm){

	$("#checkDiv").hide();
	if(columnCheckSel){
		for(var a in columnCheckSel){
			if(columnCheckSel[a])columnCheckSel[a].selectBox.hide();
		}
	}
	
	var $activeCell=parm.activeCell;
	var $row=frameObj.grid.getCellRow($activeCell);
	var $rowData=parm.rowData;
	//frameObj.grid.move("right");
	//frameObj.grid.hideMove("right");
	
	if(paraList["047"]==1){
    	
    	var subjCode=$rowData.subj_code;
    	if($activeCell.index()==18){
    		subjCode=$rowData.budg_subj_code;
    	}
    	if(subjCode!=""){
    		subjCode=subjCode.substring(0,1);
    	}
    	
    	if(subjCode=="4" && $rowData.debit_name==""){
    		frameObj.grid.setActiveCell($($row[0].cells[$activeCell.index()+2]));
	    	frameObj.grid.editCell(); 
    	}else if(subjCode=="6" && $rowData.budg_debit_name==""){
    		frameObj.grid.setActiveCell($($row[0].cells[$activeCell.index()+2]));
	    	frameObj.grid.editCell(); 
    	}else{
    		frameObj.grid.setActiveCell($activeCell.next());//下一单元格焦点
	    	frameObj.grid.editCell(); 
    	}
	}else{
		frameObj.grid.setActiveCell($activeCell.next());
		frameObj.grid.editCell(); 
	}
	
	setTimeout(function (){
		if(columnCheckSel){
			for(var a in columnCheckSel){
				if(columnCheckSel[a])columnCheckSel[a].selectBox.hide();
			}
		}
	},1000);
	
/*	frameObj.grid.setActiveCell($activeCell.next());//下一单元格焦点
   	setTimeout(function (){
    	frameObj.grid.editCell(); 
	},100);
   	
	if(window.navigator.userAgent.indexOf("Chrome") >= 0){
	   frameObj.grid.$el.focus();
	}else{
		$($row[0].cells[5]).focus();
	}*/
	
	//刷新辅助核算页面
	frameObj.clickVouchDetailFun("enter",$activeCell);
}

/**********************参数044默认弹出辅助核算层********end*****************************/

function setDiffCheck(json){
	
	if(json.length>0){
		if(json[0].is_auto==1){
			is_diff_check.setCheck(true);
		}else{
			is_diff_check.setUncheck(false);
		}
	}else{
		var isDiffCheck=false;
		if(paraList["045"]==1){
			isDiffCheck=true;
		}
		
		/*if(Local.get("acc[vouch[is_diff_check")=="true"){
			isDiffCheck=true;
		}else if(Local.get("acc[vouch[is_diff_check")=="false"){
			isDiffCheck=false;
		}*/
	
		if(isDiffCheck){
			is_diff_check.setCheck(true);
		}else{
			is_diff_check.setUncheck(false);
		}
	}
}