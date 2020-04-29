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
    <script src="<%=path%>/lib/hrp/med/med.js"	type="text/javascript"></script>
    <script type="text/javascript">
    
     var dialog = frameElement.dialog; //调用页面的dialog对象(ligerui对象)
	 var detailData = dialog!=null?dialog.get("data"):"";

	
     var dataFormat;
     var clicked = 0;
     var selectData = "";
     var grid;
     var gridManager = null;
     var medPurMap = new HashMap(); 
     var hideTrue = '${is_dept}'== 0 ? true : false ;
     var come_from = '${is_dept}'== 0 ? 2 : 3 ;
     $(function (){
    	
        loadDict();//加载下拉框
        loadHead(null);//加载数据       
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
                       { display: '药品编码', name: 'inv_code', align: 'left',minWidth : 100,
       			    	totalSummary: {
    	                    type: 'sum',
    	                    render: function (suminf, column, cell) {
    	                        return '<div>合计</div>';
    	                    }
    	                }},
                       { display: '药品名称',
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
										display : '药品编码',
										name : 'inv_code',
										align : 'left'
									}, {
										display : '药品名称',
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
									onSelectRow : function (data) {
										var e = window.event;
										if (e && e.which == 1) {
											f_onSelectRow_detail(data);
										}
									},
									url : '../../queryMedInvListByPur.do?isCheck=false',
									//delayLoad:true,
									usePager:true,
									pageSize : 30,
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
									autocomplete : true,
									onSuccess : function() {
										this.parent("tr").next(
											".l-grid-row").find(
											"td:first").focus();
									},
									ontextBoxKeyEnter: function (data) {
										f_onSelectRow_detail(data.rowdata);
									}
  					     		}
  					   },
                       { display: '规格型号', name: 'inv_model', align: 'left',minWidth : 80},
  					   { display: '计量单位', name: 'unit_name', align: 'left',minWidth : 80},
		  			   { display: '包装单位(E)', name: 'pack_code', align: 'left',minWidth : 80,
					 			textField : 'pack_name',
					 			align : 'left',
								editor : {
									type : 'select',
									valueField : 'id',
									textField : 'text',
									url : '../../queryMedHosPackage.do?isCheck=false',
									keySupport : true,
									autocomplete : true,
								}
						    },
					   { display: '转换量(E)', name: 'num_exchange', align: 'right',minWidth : 80,type: 'int', editor: { type: 'int'} },
				  	   { display: '当前库存', name: 'cur_amount', align: 'right',minWidth : 80
						   /* ,render:
	                    	 function(rowdata,rowindex,value){
		                    	 return '<a href=javascript:openUpdate("'+ rowdata.inv_id + '")>'+rowdata.cur_amount+'</a>';
                   			 } */
					  },{ display: '计划数量', name: 'req_amount', align: 'right',minWidth : 80,
						  /* render:
						  function(rowdata,rowindex,value){
		                    	 return '<a href=javascript:openDetail(' 
									+ rowdata.req_rela + ','+ rowindex
									+ ')>'+rowdata.req_amount+'</a>';
             			 	} , */totalSummary: {
								align: 'right',
								render: function (suminf, column, cell) {
									return '<div>' + formatNumber(suminf.sum == null ? 0 : suminf.sum, 2, 1) + '</div>';
								}
							} 
					   },
					   { display: '包装数量', name: 'num', align: 'right',minWidth : 80},
					   { display: '采购数量', name: 'amount', align: 'right',minWidth : 80,
						   totalSummary: {
								align: 'right',
								render: function (suminf, column, cell) {
									return '<div>' + formatNumber(suminf.sum == null ? 0 : suminf.sum, 2, 1) + '</div>';
								}
							}   
					   },
				       { display: '单价', name: 'price', align: 'right',minWidth : 80,type: 'number',
								editor : {
									type : 'numberbox',
									precision : '${p08006 }'
								},
								render : function(rowdata, rowindex, value) {
									rowdata.price = value == null ? "" : formatNumber(value, '${p08006 }', 0);
									return value == null ? "" : formatNumber(value, '${p08006 }', 1);
								}
							},
					   { display: '金额', name: 'amount_money', align: 'right',minWidth : 80,
								 render:function(rowdata){
									  return formatNumber(rowdata.amount_money ==null ? 0 : rowdata.amount_money,2,1);
					             },totalSummary: {
				                      type: 'sum',
				                      render: function (suminf, column, cell) {
				                         return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum,2,1)+ '</div>';
				                      }
				                 }},
				       { display: '申请科室', name: 'app_dept_id', align: 'left',textField : 'app_dept_name',minWidth : 200,textField:'text',
				            render :function(rowdata, rowindex, value) {
 								return rowdata.app_dept_name;
 							},hide : hideTrue
					   },
					   { display: '申请日期', name: 'app_date', align: 'left',type: 'date', align: 'left', 
						    format: 'yyyy-MM-dd', minWidth: 100,hide : hideTrue
					   },
					   { display: '供应商(E)', name: 'sup_id', align: 'left',textField : 'sup_name',width : 200,render : 
							   	 function(rowdata, rowindex, value) {
			  						return rowdata.sup_name;
			  					 },
							     editor : {
										type : 'select',
										valueField : 'id',
										textField : 'text',
										selectBoxWidth : 250,
										selectBoxHeight : 240,
										keySupport : true,
										autocomplete : true
								  }
					   },
					   { display: '生产产商', name: 'fac_name', align: 'left',width : 200},
					   { display: '备注', name: 'memo', align: 'left',minWidth : 80,type: 'string', editor: { type: 'string'}} ,
					   { display: '关系', name: 'req_rela', align: 'left',minWidth : 80,hide:true} ,
					   { display: '申请科室ID', name: 'app_dept_id', align: 'left',minWidth : 80,hide:true} ,
					   { display: '申请科室NO', name: 'app_dept_no', align: 'left',minWidth : 80,hide:true} 
                       ],
                       dataAction: 'server',dataType: 'server',usePager:true,
                       width: '100%', height: '100%', checkbox: true,rownumbers:true,
                       enabledEdit : true,
                       alternatingRow : true,onBeforeEdit : f_onBeforeEdit,
                       onBeforeSubmitEdit : f_onBeforeSubmitEdit,
                       onAfterEdit : f_onAfterEdit,
                       //onAfterShowData : initValue,
                       isScroll : true,
                       delayLoad : false,
                       onLoaded:function(){
	                   		this.addRow();
	                   },
	                   data : detailData,
                       selectRowButtonOnly:true,//heightDiff: -10,
                       toolbar: { items: [
                       			{ text: '删除', id:'delete', click: deleteRow,icon:'delete' },
                       			{ line:true },
                       			{text: '保存', id:'save', click: save ,icon:'save' },
                       			{ line:true },
	      						{text : '关闭',id : 'close',click : this_close ,icon : 'close'}
  				       ]}
                     });

          gridManager = $("#maingrid").ligerGetGridManager();
          
        
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
 		 if(column_name=='sup_id'){
			var sup = grid.getColumnByName("sup_id");
			sup.editor.url='../../queryMedSupByInvId.do?isCheck=false&inv_id='+e.record.inv_id;
		 }
 	 }
	
 	
 	 //选中回充数据
	function f_onSelectRow_detail(data, rowindex, rowobj) {
		selectData = "";
		selectData = data;
		
		if (selectData != "" || selectData != null) {
			if (column_name == "inv_name") {
				//回充数据 
				grid.updateRow(rowindex_id,{
						inv_code : data.inv_code,
						inv_name : data.inv_name,
						inv_model : (data.inv_model == "" || data.inv_model == undefined) ? '': data.inv_model,
						unit_name : data.unit_name,						
						fac_name : data.fac_name,
						cur_amount : data.cur_amount,
						price : data.price,
						inv_id : data.inv_id,
						inv_no : data.inv_no,
						sup_id : data.is_default == undefined?'':data.sup_id,
						sup_no : data.is_default == undefined?'': data.sup_no,
						sup_name : data.is_default == undefined?'':data.sup_name,
						pack_name : data.pack_name,
						pack_code : data.pack_code,
						num_exchange : data.num_exchange,
				});
				var formPara="";
			 	formPara = {
			 			sup_id:data.sup_id,
			 			sup_no:data.sup_no
		 	    }		
				ajaxJsonObjectByUrl("../../protocol/medprotocolmain/queryMedProtocolMainPur.do?isCheck=false",formPara,function (responseData){
				if(responseData.state=="true"){
        			var a_price = responseData.price;
        			medPurMap.put(rowindex_id,a_price);
        		}else {
        			medPurMap.put(rowindex_id,0);
        		}
        	});
			}else if(column_name == 'pack_code'){
				grid.updateRow(rowindex_id,{
					pack_code : data.text,
				});
			}
		}
			return true;
	}
 	
 	  //修改供应商
	 /* function f_onSelectRow_updateSup(data, rowindex, rowobj){		 
 		grid.updateRow(rowindex_id,{sup_id:data.value});
 	 }*/
 		
 	 function f_onSelectRow(data, rowindex, rowobj) {			
			return true;
	 }
		
	 // 编辑单元格提交编辑状态之前作判断限制
	 function f_onBeforeSubmitEdit(e) {			
			return true;
	 }
	 
	 /* function initValue(){
		
		 var data =  gridManager.getData();
	        $(data).each(function (){					
	        	var index  = this.ROW_ID - 1;
	            var formPara="";
				formPara = {
					sup_id:this.sup_id,
					sup_no:this.sup_no
				}		
	            
				ajaxJsonObjectByUrl("../../protocol/medprotocolmain/queryMedProtocolMainPur.do?isCheck=false",formPara,function (responseData){
					if(responseData.state=="true"){
		        		var a_price = responseData.price;
		        		medPurMap.put(index,a_price);
		        	}else {
		        		medPurMap.put(index,0);
		        	}
		        });
			});
	} */
	 
	 //保存
	 function save() {
		if(liger.get("dept_id").getValue().split(",")[0] == ''){
			$.ligerDialog.error('编制科室不能为空');
	    	return false;
		} 
		if($('#make_date').val() == ''){
			$.ligerDialog.error('编制日期不能为空');
	    	return false;
		}
		if($("input[type='radio']:checked").val() != '1'){
			if(liger.get("pur_hos_id").getValue().split(",")[0] == '' ){
				$.ligerDialog.error('请填写采购单位');
				return false;
			}
				
			if(liger.get("req_hos_id").getValue().split(",")[0] == ''){
				$.ligerDialog.error('请填写请购单位');
				return false;
			}
					
			if(liger.get("pay_hos_id").getValue().split(",")[0] == ''){
				$.ligerDialog.error('请填写付款单位');
				return false;
			}
		}
		
		//alert(JSON.stringify(gridManager.getData()));	
		var allData =  gridManager.getData(); 	
	    if(allData.length == 0){
	    	$.ligerDialog.error('请添加采购计划明细');
	    	return false; 
	    }
	    	
	    var flag = true;	    	
	    if(allData.length != 0){	    		
		    $(allData).each(function(d_index, d_content){	
		    	if(this.inv_id){
		    		var row_index = d_index + 1;
					if(this.price == undefined || this.price == ''){	    			
			    		$.ligerDialog.error('请填写单价');	    			
			    		return flag = false;
			    	}
						 
					if (this.amount == "" && this.amount == null) {					
						$.ligerDialog.error('请填写数量');					
						return flag = false;
					} 
					
					if (parseFloat(this.amount) <= 0 ) {						
						$.ligerDialog.error('数量必须大于零');						
						return flag = false;
					}  
						
					if (this.price == "" || this.price == null  || this.price == 'undefined') {  				    
						$.ligerDialog.error('请填写单价');					
						return flag = false;
					}

					var med_pare = '${p08013 }';
					if(med_pare == 1){
				    	if(medPurMap.get(d_index)!= 0){ 
							var price = medPurMap.get(d_index);
							if(this.price > price){
								$.ligerDialog.error('第' + row_index +'行采购药品单价大于协议中供应商设定的单价');	
								return flag = false;
							}
				 		}  
			    	}
		    	}
		    	
		    }); 
		    if(!flag){
		    	return;
		    }
	    }
	    
	    var formPara = {
				dept_id : liger.get("dept_id").getValue().split(",")[0],//编制科室
				dept_no : liger.get("dept_id").getValue().split(",")[1],
				make_date : $("#make_date").val(),//编制日期
				arrive_date : $("#arrive_date").val(),//编制日期
				pur_type : $("input[type='radio']:checked").val(),//计划类型
				is_dir : '${is_dir}',//是否定向
				brif : $("#brif").val(),//摘要
				pur_hos_id : liger.get("pur_hos_id").getValue().split(",")[0],//采购单位
				req_hos_id : liger.get("req_hos_id").getValue().split(",")[0],//请购单位
				pay_hos_id : liger.get("pay_hos_id").getValue().split(",")[0],//付款单位
				come_from : come_from,
				is_dept : '${is_dept}',
				allData : JSON.stringify(allData)//获取所有数据
		};
			
		ajaxJsonObjectByUrl("addProdMedPurMain.do?isCheck=false", formPara,function(responseData) {
				if (responseData.state == "true") {
						$("input[name='dept_id']").val('');
						$("input[name='make_date']").val('');
						$("input[name='brif']").val('');
						$("input[name='pur_hos_id']").val('');
						$("input[name='req_hos_id']").val('');
						$("input[name='pay_hos_id']").val('');
						
						parentFrameUse().query();
						this_close();
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
		function saveMedPurMain() {				
				save();
		}
		
		//加载字典下拉框
		function loadDict() {
						
			autocomplete("#dept_id", "../../queryPurDept.do?isCheck=false", "id", "text", true, true,'',true);//编制科室下拉框				
			autocomplete("#pur_hos_id","../../../sys/queryHosInfoDict.do?isCheck=false","id","text",true,true);//采购单位下拉框 						
			autocomplete("#req_hos_id","../../../sys/queryHosInfoDict.do?isCheck=false","id","text",true,true,"",true);//请购单位下拉框 			
			autocomplete("#pay_hos_id","../../../sys/queryHosInfoDict.do?isCheck=false","id","text",true,true);//付款单位下拉框 
			autoCompleteByData("#is_dir", yes_or_no.Rows, "id", "text", true, true,"",false,"${is_dir}",'160');//是否定向 
			$("#req_hos_id").ligerComboBox({disabled:true,cancelable: false});
			
			$("#pur_code").ligerTextBox({width:160,disabled:true});
			$("#dept_id").ligerTextBox({width:160});
			$("#make_date").ligerTextBox({width:160});
			autodate("#make_date");
			$("#brif").ligerTextBox({width:160});
			
			$("#arrive_date").ligerTextBox({width:160});
			$("#pur_hos_id").ligerTextBox({width:160});
			$("#req_hos_id").ligerTextBox({width:160});
			$("#pay_hos_id").ligerTextBox({width:160});
		}
		
		//打开药品当前库存明细页面
		function openUpdate(obj){		    	
			$.ligerDialog.open({ 
				url : 'medInvCurAmountDetailPage.do?isCheck=false&inv_id='+obj,data:{}, 
				height: 480,width: 980,top:1, title:'药品当前库存明细',modal:true,showToggle:false,showMax:true,showMin: false,isResize:true
			});		    
		}
		
		//打开计划数量明细页面
		function openDetail(obj,rowindex){		
			 var req_rela = JSON.stringify(obj).toString();			 
			 $.ligerDialog.open({ 
				url : 'medRequireAmountDetailPage.do?isCheck=false&req_rela='+req_rela+'&rowindex='+rowindex,data:{}, 
				height: 400,width: 1100, top:1,title:'计划数量明细',modal:true,showToggle:false,showMax:true,showMin: false,isResize:true,
				buttons: [ 
					{ text: '确认', onclick: function (item, dialog) { dialog.frame.saveReqPurRela(); },cls:'l-dialog-btn-highlight' }, 
					{ text: '关闭', onclick: function (item, dialog) { dialog.close(); } } 
				]});
		 }
		
		//关闭窗口 
		function this_close(){	
			 frameElement.dialog.close();
		}
		
		//修改 从子页面传回的采购数量和对应关系
		function updatePurAmount(row_index,jsonObj,pur_amount){
			 grid.updateRow(row_index,{amount:pur_amount,req_rela:jsonObj});
		 }
			
		//删除选中行
		function deleteRow(){			
			gridManager.deleteSelectedRow();
        }
	</script>
  </head> 
  <body >
  <div id="pageloading" class="l-loading" style="display:none"></div>
   		<form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">		
	        <tr>
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>计划单号：</td>
	            <td align="left" class="l-table-edit-td"><input name="pur_code" type="text" id="pur_code" ltype="text" validate="{required:true,maxlength:20}" value="自动生成" disabled="disabled"/></td>
	            <td align="left"></td>
	            
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>编制科室：</td>
	            <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text"  validate="{required:true,maxlength:20}"/></td>
	            <td align="left"></td>
	            
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>编制日期：</td>
	            <td align="left" class="l-table-edit-td"><input class="Wdate" name="make_date" type="text" id="make_date" ltype="text"  
	            onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" validate="{required:true,maxlength:20}"/></td>
	            <td align="left"></td>
	        </tr>       
        	<tr>
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>计划类型：</td>
	            <td align="left" class="l-table-edit-td">
	            	<input id="planA" name="planType" type="radio" value="1" checked="checked" onclick="singleSel()" />自购计划
	            	<input id="planB" name="planType" type="radio" value="2" onclick="singleSel()" />统购计划
	            </td>
	            <td align="left"></td>
	            
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">摘要：</td>
	            <td align="left" class="l-table-edit-td" colspan="2">
	            	<input name="brif" type="text" id="brif" ltype="text" />
	            </td>
	            
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">计划到货日期：</td>
	            <td align="left" class="l-table-edit-td">
	            	<input class="Wdate" name="arrive_date" type="text" id="arrive_date" ltype="text"  
	           		 onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" validate="{required:true,maxlength:20}"/>
	             </td>
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
        	<tr>
	        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否定向：</td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="is_dir" type="text" id="is_dir" ltype="text" />
	            </td>
	            <td align="left"></td>
        	</tr> 
    	</table>   
    	<div id="maingrid"></div>
    </form>
</body>
</html>
