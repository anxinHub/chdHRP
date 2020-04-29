<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%-- <jsp:include page="${path}/inc.jsp"/> --%>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script src="<%=path%>/lib/hrp/mat/mat.js"	type="text/javascript"></script>
<link href='<%=path%>/lib/SpreadJS/css/gcspread.sheets.9.40.20161.0.css' rel='stylesheet' type='text/css'/>
<script src='<%=path%>/lib/SpreadJS/scripts/gcspread.sheets.all.9.40.20161.0.min.js' type='text/javascript'></script>
<script src='<%=path%>/lib/SpreadJS/scripts/pluggable/gcspread.sheets.print.9.40.20161.0.min.js' type='text/javascript'></script>
<script type="text/javascript">
     var dataFormat;
     var clicked = 0;
     var selectData = "";
     var grid;
     var gridManager = null;
     var state = "${state}";
     $(function() {
 		loadDict();//加载下拉框
 		loadHead(null);//加载数据
 		
 		if('${pur_type}'=='1'){
 			$('input:radio:first').attr('checked', 'checked');
 			$('#hos_name').hide();
 		}else{
 			$('input:radio:last').attr('checked', 'checked');
 			$('#hos_name').show();
 		}
 	});

 	function singleSel(){
    	 if($("input[type='radio']:checked").val() == '1'){
    		 $('#hos_name').hide();
    		 //统购计划中，请购单位为当前单位，并且不可编辑
    		 
    		 autoCompleteByData("#is_dir", yes_or_no.Rows, "id", "text", true, true,"${is_dir}",false,"0",'160');//是否定向
 			 changeDir();
    	 }else{
    		 $('#hos_name').show();
    		 autoCompleteByData("#is_dir", yes_or_no.Rows, "id", "text", true, true,"${is_dir}",false,"1",'160');//是否定向
    		 $("#req_hos_id").ligerComboBox({disabled:true,cancelable: false});
    		 liger.get("req_hos_id").setValue("${req_hos_id}");
   		 liger.get("req_hos_id").setText("${req_hos_code} ${req_hos_name}");
    		 changeDir();
    	 }
     }
 	//选择定向与非定向是否显示定向科室
     function changeDir(){
 		 if(liger.get("is_dir").getValue()=='1'){
 			 $(".dept").attr("style","visibility:true");
 		 }else{
 			 $(".dept").attr("style","visibility:hidden");
 		 }
 	 }
     
     function loadHead(){
    	 
    	 grid = $("#maingrid").ligerGrid({
             columns: [ 
                       { display: '材料编码', name: 'inv_code', align: 'left',minWidth : 100,
  					 		},
                       { display: '材料名称',
  					     name: 'inv_name',
  					     align: 'left',
  					     minWidth : 100,
  					     textField : 'inv_name',
  					     valueField : 'inv_name',
  					     editor :{
								type : 'select',
								textField : 'inv_name',
								valueField : 'inv_name',
								selectBoxWidth : 500,
								selectBoxHeight : 240,
								grid : {
									columns : [ {
										display : '材料编码',
										name : 'inv_code',
										align : 'left'
									}, {
										display : '材料名称',
										name : 'inv_name',
										align : 'left'
									}, {
										display : '规格型号',
										name : 'inv_model',
										align : 'left'
									}, {
										display : '计量单位',
										name : 'unit_name',
										align : 'left'
									}, {
										display : '当前库存',
										name : 'cur_amount',
										align : 'left'
									}, {
										display : '供应商',
										name : 'sup_name',
										align : 'left'
									}, {
										display : '生产厂商',
										name : 'fac_name',
										align : 'left'
									}, {
										display : '参考单价',
										name : 'price',
										align : 'left'
									}  ],
									switchPageSizeApplyComboBox : false,
									onAfterEdit : f_onAfterEdit,
									onSelectRow : f_onSelectRow_detail,
									url : '../../queryMatInvList.do?isCheck=false',
									//delayLoad:true,
									usePager:true,
									pageSize : 30
			  					    },
									keySupport : true,
									autocomplete : true,
									onSuccess : function() {
										this.parent("tr").next(
											".l-grid-row").find(
											"td:first").focus();
									}
  					     		}
  					   },
                       { display: '规格型号', name: 'inv_model', align: 'left',minWidth : 80
  					 		},
  					   { display: '计量单位', name: 'unit_name', align: 'left',minWidth : 80
					 		},
		  			   { display: '包装单位(E)', name: 'pack_code', align: 'left',minWidth : 80,
					 			textField : 'pack_unit_name',
					 			align : 'left',
								editor : {
									type : 'select',
									valueField : 'id',
									textField : 'text',
									url : '../../queryMatHosPackage.do?isCheck=false',
									keySupport : true,
									autocomplete : true,
								},
								render : function(rowdata, rowindex, value) {
									return rowdata.pack_unit_name;
								}
						    },
					   { display: '转换量(E)', name: 'num_exchange', align: 'left',minWidth : 80,type: 'int', editor: { type: 'int'}
						    },
				  	   { display: '当前库存', name: 'cur_amount', align: 'left',minWidth : 80
							},
					   { display: '计划数量', name: 'req_amount', align: 'left',minWidth : 80
							},
					   { display: '包装数量(E)', name: 'num', align: 'left',minWidth : 80,type: 'number', editor: { type: 'number'}
							},
					   { display: '采购数量(E)', name: 'amount', align: 'left',minWidth : 80,type: 'int', editor: { type: 'int'}
							},
				       { display: '单价(E)', name: 'price', align: 'left',minWidth : 80,type: 'number', editor: { type: 'number'}
							},
					   { display: '金额', name: 'amount_money', align: 'left',minWidth : 80,
							},
						{ display: '供应商变更号', name: 'sup_no', align: 'left',minWidth : 80,
						},
					   { display: '供应商(E)', name: 'sup_id', align: 'left',minWidth : 100,
						    	 textField : 'sup_name',
		  					     editor :{
										type : 'select',
										textField : 'text',
										valueField : 'id',
										selectBoxWidth : 100,
										selectBoxHeight : 240,
										url : '../../queryHosSupDict.do?isCheck=false'
		  					     	}
							},
					   { display: '生产产商', name: 'fac_name', align: 'left',minWidth : 100
							},
					   { display: '备注', name: 'memo', align: 'left',minWidth : 80,type: 'string', editor: { type: 'string'}
							}  
					
                       ],
                       dataAction: 'server',dataType: 'server',usePager:true,
                       width: '100%', height: '90%', checkbox: true,rownumbers:true,
                       enabledEdit : true,alternatingRow : true,onBeforeEdit : f_onBeforeEdit,
                       onBeforeSubmitEdit : f_onBeforeSubmitEdit,onAfterEdit : f_onAfterEdit,
                       url:'queryMatPurMainDetail.do?isCheck=false&pur_id='+'${pur_id}',
                       isScroll : true,
                       selectRowButtonOnly:true,//heightDiff: -10,
                       toolbar: { items: [
                       			{ text: '审核', id:'check', click: itemclick,icon:'' },{ line:true },
				    	        { text: '取消审核', id:'cancelCheck', click: itemclick, icon:'' },{ line:true }
  				       ]}
                     });

          gridManager = $("#maingrid").ligerGetGridManager();
          
          grid.toggleCol("sup_no", false);
          
     }
		var formPara = {
				pur_code : $('#pur_code').val(),//计划单号
				pur_id : '${pur_id}',
				dept_id : liger.get("dept_id").getValue().split(",")[0],//编制科室
				dept_no : liger.get("dept_id").getValue().split(",")[1],
				make_date : $("#make_date").val(),//编制日期
				pur_type : $("input[type='radio']:checked").val(),//计划类型
				brif : $("#brif").val(),//摘要
				pur_hos_id : liger.get("pur_hos_id").getValue().split(",")[0],//采购单位
				req_hos_id : liger.get("req_hos_id").getValue().split(",")[0],//请购单位
				pay_hos_id : liger.get("pay_hos_id").getValue().split(",")[0],//付款单位
				allData : JSON.stringify(allData),//获取所有数据
				deletedData : JSON.stringify(deletedData)
			//获取删除的数据
			};

			ajaxJsonObjectByUrl("updateMatPurMain.do", formPara, function(
					responseData) {

				if (responseData.state == "true") {
					/* $("input[name='dept_id']").val('');
					$("input[name='make_date']").val('');
					$("input[name='brif']").val('');
					$("input[name='pur_hos_id']").val('');
					$("input[name='req_hos_id']").val('');
					$("input[name='pay_hos_id']").val(''); */
					parent.query();
				}
			});

     
     function f_onAfterEdit(e) {
 		if(e.value != "" && e.value != 0){
 			if (e.column.name == "amount"){
 				//自动计算金额
 				if(e.record.price != undefined && e.record.price != "" && e.record.price != 0){
 					grid.updateCell('amount_money', e.value * e.record.price, e.rowindex);
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
 				}
 			}
 		}
 		return true;
 	}
     
     var rowindex_id = "";
 	 var column_name="";
 	 function f_onBeforeEdit(e) {
 		 rowindex_id = e.rowindex;
 		 clicked = 0;
 		 column_name=e.column.name;
 	 }
	
 	
 	 //选中回充数据
	function f_onSelectRow_detail(data, rowindex, rowobj) {

		selectData = "";
		selectData = data;
		if (selectData != "" || selectData != null) {
			if (column_name == "inv_name") {
				//回充数据 
				//grid.updateCell('apply_emp', 100, e.record);
				grid.updateRow(
						rowindex_id,
						{inv_code : data.inv_code,
						inv_name : data.inv_name,
						inv_model : (data.inv_model == "" || data.inv_model == undefined) ? '': data.inv_model,
						unit_name : data.unit_name,
						sup_id:data.sup_id,
						sup_name:data.sup_name,
						fac_name:data.fac_name,
						cur_amount:data.cur_amount,
						price:data.price,
						inv_id:data.inv_id,
						inv_no:data.inv_no,
						sup_no:data.sup_no,
						pack_code:data.pack_unit,
						pack_unit_name:data.pack_unit_name,
						num_exchange:data.num_exchange,
				});

			}else if(column_name == 'sup_name'){
				grid.updateRow(
						rowindex_id,
						{
						sup_id:data.sup_id
				});
			}
			else if(column_name == 'pack_code'){
				grid.updateRow(
						rowindex_id,
						{
						pack_code:data.id
						
				});
			}else if(column_name == 'stocker'){
				grid.updateRow(
						rowindex_id,
						{
						stockId:data.code
						
				});
			}
		}
			return true;
	}
 	 
 	 //按钮事件
	 function itemclick(item){ 
	        if(item.id)
	        {
	            switch (item.id)
	            {
	                case "check":
	                   var Param =[];
	                       Param.push(//表的主键
	 							'${group_id}'+"@"+
	 							'${hos_id}'+"@"+
	 							'${copy_code}'+"@"+
	 							'${pur_id}'+"@"+
	 							'${state}'
	 						);
	                        
	                       $.ligerDialog.confirm('确定审核?', function (yes){
	                        	if(yes){
	                            	ajaxJsonObjectByUrl("checkMatPurMain.do?isCheck=false&paramVo="+Param,{},function (responseData){
	                            		if(responseData.state=="true"){
	                            			parent.query();
	                            		}
	                            	});
	                        	}
	                        });  
	                    return;
	                    
	                case "cancelCheck":
	                    var data = gridManager.getData();
	                    var Param =[];
	                       Param.push(
	 							//表的主键
	 							'${group_id}'+"@"+
	 							'${hos_id}'+"@"+
	 							'${copy_code}'+"@"+
	 							'${pur_id}'+"@"+
	 							'${state}'
	 						);
	                        
	                       $.ligerDialog.confirm('确定取消审核?', function (yes){
	                        	if(yes){
	                            	ajaxJsonObjectByUrl("cancelCheckMatPurMain.do?isCheck=false&paramVo="+Param,{},function (responseData){
	                            		if(responseData.state=="true"){
	                            			parent.query();
	                            		}
	                            	});
	                        	}
	                        });  
	                    return;
	            }   
	        }
	        
	    }
		
 	 function f_onSelectRow(data, rowindex, rowobj) {
			
			return true;
	 }
		
		// 编辑单元格提交编辑状态之前作判断限制
		function f_onBeforeSubmitEdit(e) {
			
			return true;
		}

		function save() {
			
		
		}

		//保存采购计划
		function saveMatPurMain() {
				
				save();
		}
		
		//加载字典下拉框			
		function loadDict() {					
			//字典下拉框
			autocomplete("#dept_id", "../../queryPurDept.do?isCheck=false", "id", "text", true, true);//编制科室下拉框	
			liger.get("dept_id").setValue("${dept_id},${dept_no}");
			liger.get("dept_id").setText("${dept_code} ${dept_name}");
			
			autocomplete("#pur_hos_id","../../queryMatHosInfoDict.do?isCheck=false","id","text",true,true,"",false,false);//采购单位下拉框 
			liger.get("pur_hos_id").setValue("${pur_hos_id},${pur_hos_no}");
			liger.get("pur_hos_id").setText("${pur_hos_code} ${pur_hos_name}");
			
			autocomplete("#req_hos_id","../../queryMatHosInfoDict.do?isCheck=false","id","text",true,true,"",true,false);//请购单位下拉框 
			liger.get("req_hos_id").setValue("${req_hos_id},${req_hos_no}");
			liger.get("req_hos_id").setText("${req_hos_code} ${req_hos_name}");
			
			autocomplete("#pay_hos_id","../../queryMatHosInfoDict.do?isCheck=false","id","text",true,true,"",false,false);//付款单位下拉框 
			liger.get("pay_hos_id").setValue("${pay_hos_id},${pay_hos_no}");
			liger.get("pay_hos_id").setText("${pay_hos_code} ${pay_hos_name}");
			
			autoCompleteByData("#is_dir", yes_or_no.Rows, "id", "text", true, true,"",false,"${is_dir}",'160');//是否定向
			autocomplete("#dir_dept_id","../../queryMatDeptDict.do?isCheck=false","id","text",true,true,"",false,false);//定向科室	
			liger.get("dir_dept_id").setValue("${dir_dept_id},${dir_dept_no}");
			liger.get("dir_dept_id").setText("${dir_dept_code} ${dir_dept_name}");
			
			$("#req_hos_id").ligerComboBox({disabled:true,cancelable: false});
			
			$("#pur_code").ligerTextBox({width:160,disabled:true});
			$("#dept_id").ligerTextBox({width:160});
			$("#make_date").ligerTextBox({width:160});
			$("#brif").ligerTextBox({width:160});
			
			$("#arrive_date").ligerTextBox({width:160});
			$("#pur_hos_id").ligerTextBox({width:160});
			$("#req_hos_id").ligerTextBox({width:160});
			$("#pay_hos_id").ligerTextBox({width:160});
			
			$("#print").ligerButton({click: printDate, width:90});
			$("#printSet").ligerButton({click: printSet, width:100});
			$("#close").ligerButton({click: this_close, width:90});
		}
		
		//键盘事件
		function loadHotkeys() {
			hotkeys('P', printDate);
			hotkeys('M', printSet);
			hotkeys('C', this_close);
		}
		
		//打印
		function printDate(){
			
			var useId=0;//统一打印
			if('${p04017 }'==1){
				//按用户打印
				useId='${sessionScope.user_id }';
			}
			
			var para={
				pur_id:'${pur_id}',
				template_code:'04027',
				use_id:useId
			};
			
			printTemplate("queryMatCheckByPrintTemlate.do?isCheck=false",para);
		}
		
		//打印设置
		function printSet(){
			
			
			var useId=0;//统一打印
			if('${p04017 }'==1){
				//按用户打印
				useId='${sessionScope.user_id }';
			}
			
			parent.parent.$.ligerDialog.open({url : 'hrp/mat/purchase/check/purMainCheckPrintSetPage.do?template_code=04027&use_id='+useId,
				data:{}, height: $(parent).height(),width: $(parent).width(), title:'打印模板设置',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
			});
		}
		
		//关闭当前页面
		function this_close(){
			frameElement.dialog.close();
		}
		</script>
  </head>
  
   <body >
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><font color="red" size="2">*</font>计划单号：</td>
				<td align="left" class="l-table-edit-td">
					<input name="pur_code" type="text" id="pur_code" ltype="text" validate="{required:true,maxlength:20}" value="${pur_code}" disabled="disabled" /></td>
				<td align="left"></td>

				<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><font color="red" size="2">*</font>编制科室：</td>
				<td align="left" class="l-table-edit-td">
					<input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>

				<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><font color="red" size="2">*</font>编制日期：</td>
				<td align="left" class="l-table-edit-td">
					<input class="Wdate" name="make_date" type="text" id="make_date" ltype="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"
					validate="{required:true,maxlength:20}" value="${make_date}" /></td>
				<td align="left"></td>
			</tr>

			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><font color="red" size="2">*</font>计划类型：</td>
				<td align="left" class="l-table-edit-td">
					<input id="planA" name="planType" type="radio" value="1" onclick="singleSel()">自购计划
					<input id="planB" name="planType" type="radio" value="2" onclick="singleSel()">统购计划</td>
				<td align="left"></td>

				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">摘要：</td>
				<td align="left" class="l-table-edit-td" colspan="2">
					<input name="brif" type="text" id="brif" ltype="text" value="${brif}" />
				</td>
					
            	<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>计划到货日期：</td>
            	<td align="left" class="l-table-edit-td">
            		<input class="Wdate" name="arrive_date" type="text" id="arrive_date" ltype="text"  value="${arrive_date}"
            		onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" validate="{required:true,maxlength:20}"/>
            	</td>
            	<td align="left"></td>
			</tr>

			<tr id="hos_name">
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><font color="red" size="2">*</font>采购单位：</td>
				<td align="left" class="l-table-edit-td">
					<input name="pur_hos_id" type="text" id="pur_hos_id" ltype="text" />
				</td>
				<td align="left"></td>

				<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><font color="red" size="2">*</font>请购单位：</td>
				<td align="left" class="l-table-edit-td">
					<input name="req_hos_id" type="text" id="req_hos_id" ltype="text" readonly="readonly" />
				</td>
				<td align="left"></td>

				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;"><font color="red" size="2">*</font>付款单位：</td>
				<td align="left" class="l-table-edit-td">
					<input name="pay_hos_id" type="text" id="pay_hos_id" ltype="text" />
				</td>
				<td align="left"></td>
			</tr>
			<tr>
	        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否定向：</td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="is_dir" type="text" id="is_dir" ltype="text" onChange="changeDir();"/>
	            </td>
	            <td align="left"></td>
	            
	            <td align="right" class="l-table-edit-td dept"  style="padding-left:20px;">定向科室：</td>
	            <td align="left" class="l-table-edit-td dept" >
	            	<input name="dir_dept_id" type="text" id="dir_dept_id" ltype="text" />
	            </td>
	            <td align="left"></td>
        	</tr>
		</table>
    
    <div id="maingrid"></div>
    </form>
    <div style="width: 100%; height: 100%;">
		<div id="maingrid"></div>
		<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%"  style="margin-top: 5px;">
<!-- 			<tr>	 -->
<!-- 				<td align="center" class="l-table-edit-td" width="32%" > -->
<%-- 					制单人：${maker} --%>
<!-- 				</td> -->
<!-- 				<td align="center" class="l-table-edit-td" width="32%" > -->
<%-- 					审核人：${checker} --%>
<!-- 				</td> -->
<!-- 			</tr> -->
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
    </body>
</html>
