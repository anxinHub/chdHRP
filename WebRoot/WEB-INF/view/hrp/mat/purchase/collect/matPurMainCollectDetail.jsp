<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"><html style="overflow:hidden;">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <%-- <jsp:include page="${path}/inc.jsp"/> --%>
    <jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
    <script type="text/javascript">
     var dataFormat;
     var clicked = 0;
     var selectData = "";
     var grid;
     var gridManager = null;
     $(function (){
         loadDict();//加载下拉框
        //loadForm();
        loadHead(null);//加载数据
        
        liger.get("dept_id").setValue("${dept_id},${dept_no}");
        liger.get("dept_id").setText("${dept_no}"+" "+"${dept_name}");
        
        liger.get("pur_hos_id").setValue("${pur_hos_id}");
        liger.get("pur_hos_id").setText("${pur_hos_name}");
        
        liger.get("req_hos_id").setValue("${req_hos_id}");
        liger.get("req_hos_id").setText("${req_hos_name}");
        
        liger.get("pay_hos_id").setValue("${pay_hos_id}");
        liger.get("pay_hos_id").setText("${pay_hos_name}");
        
        $("#pur_code").ligerTextBox({width:160,disabled:true});
        $("#brif").ligerTextBox({width:160,disabled:true});
        $("#pur_hos_id").ligerTextBox({width:160,disabled:true});
        $("#req_hos_id").ligerTextBox({width:160,disabled:true});
        $("#pay_hos_id").ligerTextBox({width:160,disabled:true});
        $("input[name='planType'][value='${pur_type}']").attr("checked",true); 
        //默认选中自购计划,不显示采购单位、请购单位、付款单位
        var planType = $("input[type='radio']:checked").val();
        if(planType == '1'){
        	$('#hos_name').hide();
        }
     });  
     
     //隐藏或显示  采购单位、请购单位、付款单位
     function singleSel(){
    	 if($("input[type='radio']:checked").val() == '1'){
    		 $('#hos_name').hide();
    	 }else{
    		 $('#hos_name').show();
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
				  	   { display: '当前库存', name: 'cur_amount', align: 'left',minWidth : 80,render:
				  		   	function(rowdata, rowindex, value){
				  		   		if(rowdata.cur_amount == null || rowdata.cur_amount == undefined){
				  		   			return "";
				  		   		}
					  		 return '<a href=javascript:openUpdate("' 
								+ rowdata.inv_id
								+ '")>'+rowdata.cur_amount+'</a>';
				  	   		}
							},
					   { display: '请购数量', name: 'req_amount', align: 'left',minWidth : 80,render:
						   	function(rowdata, rowindex, value){
							   if(rowdata.req_amount == null || rowdata.req_amount == undefined){
				  		   			return "";
				  		   		}
							   return '<a href=javascript:openDetail(' 
								+ rowdata.pur_rela
								+ ','
								+ rowindex
								+ ')>'+rowdata.req_amount+'</a>';
					   		}
							},
					   { display: '包装数量', name: 'num', align: 'left',minWidth : 80
							},
					   { display: '采购数量', name: 'amount', align: 'left',minWidth : 80
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
										url : '../../queryHosSup.do?isCheck=false'
		  					     	}
							},
					   { display: '生产产商', name: 'fac_name', align: 'left',minWidth : 100
							},
					   { display: '备注', name: 'memo', align: 'left',minWidth : 80,type: 'string', editor: { type: 'string'}
							}  
					
                       ],
                       dataAction: 'server',dataType: 'server',usePager:true,
                       width: '100%', height: '100%', checkbox: true,rownumbers:true,
                       enabledEdit : true,alternatingRow : true,onBeforeEdit : f_onBeforeEdit,
                       onBeforeSubmitEdit : f_onBeforeSubmitEdit,onAfterEdit : f_onAfterEdit,
                       url:'queryMatPurDetail.do?pur_id='+'${pur_id}',
                       isScroll : true,
                       selectRowButtonOnly:true,//heightDiff: -10,
                       toolbar: { items: [
                       			{ text: '删除', id:'delete', click: deleteRow,icon:'delete' },{ line:true }
  				       ]}
                     });

          gridManager = $("#maingrid").ligerGetGridManager();
          
          grid.toggleCol("sup_no", false);
          
         
     }
     
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
		
 	 function f_onSelectRow(data, rowindex, rowobj) {
			
			return true;
	 }
		
		// 编辑单元格提交编辑状态之前作判断限制
		function f_onBeforeSubmitEdit(e) {
			
			return true;
		}

		function save() {
			var allData = gridManager.getData();
			
			if(liger.get("dept_id").getValue().split(",")[0] == ''){
				$.ligerDialog.error('编制科室不能为空');
	    		return;
			} 
			
			if($('#make_date').val() == ''){
				$.ligerDialog.error('编制日期不能为空');
	    		return;
			}
				
			if($("input[type='radio']:checked").val() != '1'){
				if(liger.get("pur_hos_id").getValue().split(",")[0] == '' ){
					$.ligerDialog.error('请填写采购单位');
					return;
				}
				
				if(liger.get("req_hos_id").getValue().split(",")[0] == ''){
					$.ligerDialog.error('请填写请购单位');
					return;
				}
				
				if(liger.get("pay_hos_id").getValue().split(",")[0] == ''){
					$.ligerDialog.error('请填写付款单位');
					return;
				}
			}
			
	    	var allData = gridManager.getData();
	    	
	    	var deletedData = gridManager.getDeleted(); 
	    	
	    	if(allData.length == 0){
	    		$.ligerDialog.error('请添加采购计划明细');
	    		return ; 
	    	}
	    	
	    	var flag = true;
	    	
	    	if(allData.length != 0){
	    		
		    	$(allData).each(function(){
		    		
		    		if(this.inv_name == undefined || this.inv_name == ''){
		    			
		    			$.ligerDialog.error('请选择材料名称');
		    			
		    			return flag = false;
		    		}
		    		
					 if(this.price == undefined || this.price == ''){
		    			
		    			$.ligerDialog.error('请填写单价');
		    			
		    			return flag = false;
		    		} 
		    	}); 
		    	
		    	if(!flag){
		    		return;
		    	}
	    	}
	    	
	    	
			var formPara = {
				pur_code : $('#pur_code').val(),//计划单号
				pur_id:'${pur_id}',
				dept_id : liger.get("dept_id").getValue().split(",")[0],//编制科室
				dept_no:liger.get("dept_id").getValue().split(",")[1],
				make_date:$("#make_date").val(),//编制日期
				pur_type:$("input[type='radio']:checked").val(),//计划类型
				brif:$("#brif").val(),//摘要
				pur_hos_id:liger.get("pur_hos_id").getValue().split(",")[0],//采购单位
				req_hos_id:liger.get("req_hos_id").getValue().split(",")[0],//请购单位
				pay_hos_id:liger.get("pay_hos_id").getValue().split(",")[0],//付款单位
				allData : JSON.stringify(allData),//获取所有数据
				deletedData:JSON.stringify(deletedData)//获取删除的数据
			};
			
			
			
			ajaxJsonObjectByUrl("addOrUpdateMatPurMain.do", formPara,function(responseData) {

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
		}

		function loadForm() {

			$.metadata.setType("attr", "validate");
			var v = $("form").validate({errorPlacement : function(lable, element) {
					if (element.hasClass("l-textarea")) {
							element.ligerTip({
							content : lable.html(),
							target : element[0]
							});
					} else if (element.hasClass("l-text-field")) {
							element.parent().ligerTip({
							content : lable.html(),
							target : element[0]
							});
					} else {
							lable.appendTo(element.parents("td:first").next("td"));
					}
			},success : function(lable) {
							lable.ligerHideTip();
							lable.remove();
						},submitHandler : function() {
							$("form .l-text,.l-textarea").ligerHideTip();
						}
			});
			$("form").ligerForm();
		}
		
		//保存采购计划
		function saveMatPurMain() {
				
				save();
		}
					
		function loadDict() {
						//字典下拉框
			autocomplete("#dept_id", "../../queryPurDept.do?isCheck=false", "id", "text", true, true);//编制科室下拉框
						
			autocomplete("#pur_hos_id","../../../sys/queryHosInfoDict.do?isCheck=false","id","text",true,true);//采购单位下拉框 
						
			autocomplete("#req_hos_id","../../../sys/queryHosInfoDict.do?isCheck=false","id","text",true,true);//请购单位下拉框 
			
			autocomplete("#pay_hos_id","../../../sys/queryHosInfoDict.do?isCheck=false","id","text",true,true);//付款单位下拉框 

		}
		
		//打开材料当前存明细页面
		function openUpdate(obj){
	    	
			$.ligerDialog.open({ url : 'matInvCurAmountDetailPage.do?isCheck=false&inv_id='+obj,data:{}, height: 480,width: 1100,top:1,title:'材料当前库存明细',modal:true,showToggle:false,showMax:true,showMin: false,isResize:true});
	    
	    }
		
		//打开采购计划数量明细页面
		function openDetail(obj,rowindex){
			
			var paraObj = JSON.stringify(obj).toString();
			
			$.ligerDialog.open({ url : 'matPurMainAmountDetailPage.do?isCheck=false&paraObj='+paraObj+'&rowindex='+rowindex,data:{}, height: 400,width: 1100,top:1,title:'采购计划数量明细',modal:true,showToggle:false,showMax:true,showMin: false,isResize:true,buttons: [ { text: '确认', onclick: function (item, dialog) { dialog.frame.saveMatPurMain(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ]});
	    
	    }
		
		//修改采购数量
		 function updatePurAmount(row_index,jsonObj,exec_amount){
			 grid.updateRow(
					 row_index,
						{
						amount:exec_amount,
						pur_rela:jsonObj
						}
			);
		 }
		
		//手动添加新行
		function addCenterRow(){ 
			
			gridManager.addRow();
			
	    }
		//删除选中行
		function deleteRow(){
			
			gridManager.deleteSelectedRow();
        }

		</script>
  </head>
  
   <body >
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit">
		
        <tr>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>计划单号：</td>
            <td align="left" class="l-table-edit-td"><input name="pur_code" type="text" id="pur_code" ltype="text" validate="{required:true,maxlength:20}" value="${pur_code}" disabled="disabled"/></td>
            <td align="left"></td>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>编制科室：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text"  validate="{required:true,maxlength:20}"/></td>
            <td align="left"></td>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>编制日期：</td>
            <td align="left" class="l-table-edit-td"><input class="Wdate" name="make_date" type="text" id="make_date" ltype="text"  
            onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" validate="{required:true,maxlength:20}" value="${make_date}"/></td>
            <td align="left"></td>
        </tr>
        
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>计划类型：</td>
            <td align="left" class="l-table-edit-td">
            	<input id="planA" name="planType" type="radio" value="1" onclick="singleSel()">自购计划
            	<input id="planB" name="planType" type="radio" value="2" onclick="singleSel()">统购计划
            </td>
            <td align="left"></td>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">摘要：</td>
            <td align="left" class="l-table-edit-td" colspan="4"><input name="brif" type="text" id="brif" ltype="text" value="${brif}" readonly="readonly"/></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>计划到货日期：</td>
            <td align="left" class="l-table-edit-td"><input class="Wdate" name="arrive_date" type="text" id="arrive_date" ltype="text"  
            onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" validate="{required:true,maxlength:20}"/></td>
            <td align="left"></td>
        </tr> 
        
         <tr id="hos_name">
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>采购单位：</td>
            <td align="left" class="l-table-edit-td"><input name="pur_hos_id" type="text" id="pur_hos_id" ltype="text" /></td>
            <td align="left"></td>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>请购单位：</td>
            <td align="left" class="l-table-edit-td"><input name="req_hos_id" type="text" id="req_hos_id" ltype="text"  /></td>
            <td align="left"></td>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>付款单位：</td>
	        <td align="left" class="l-table-edit-td"><input name="pay_hos_id" type="text" id="pay_hos_id" ltype="text"  /></td>  
            <td align="left"></td>
        </tr> 
    </table>
    
    <div id="maingrid"></div>
    </form>
   
    </body>
</html>
