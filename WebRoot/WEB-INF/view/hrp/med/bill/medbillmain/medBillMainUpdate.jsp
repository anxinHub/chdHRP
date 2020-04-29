<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
    <script src="<%=path%>/lib/map.js"></script>
    <link href='<%=path%>/lib/SpreadJS/css/gcspread.sheets.9.40.20161.0.css' rel='stylesheet' type='text/css'/>
	<script src='<%=path%>/lib/SpreadJS/scripts/gcspread.sheets.all.9.40.20161.0.min.js' type='text/javascript'></script>
	<script src='<%=path%>/lib/SpreadJS/scripts/pluggable/gcspread.sheets.print.9.40.20161.0.min.js' type='text/javascript'></script>
   
    <script type="text/javascript">
    var dataFormat;
    
    var inMainGrid;
    
    var gridManager;
    
    var data;
    
    var grid;
    
    var year;
    
    var month;
    var group_id = "${group_id}";
    var hos_id = "${hos_id}" ;
    var copy_code = "${copy_code}";
    var bill_id = "${bill_id}";
    var bill_no = "${bill_no}" ;
    var state = "${state}" ;
    
    var targetMap = new HashMap();//存放key 对应关系
    
    var detailMap = new HashMap();//存放 明细数据
    
     $(function (){
         loadDict()//加载下拉框
         loadForm();
         loadHead();
     });  
     function loadHead(){
     	 inMainGrid = $("#maingrid").ligerGrid({
            columns: [ 
                      { display: '入库单号', name: 'in_no', align: 'left',width:140, 
				 			render:function(rowdata,index,value){
				 				return "<a href=javascript:openInPage('"
				 						+rowdata.group_id+","
				 						+rowdata.hos_id+","
				 						+rowdata.copy_code+","
				 						+rowdata.in_id+","
				 						+rowdata.in_no+"')>"+rowdata.in_no+"</a>";
				 			}
 					 	},
 					  { display: '供应商', name: 'sup_name', align: 'left',width:200
 					 		},
                      { display: '仓库', name: 'store_name', align: 'left',width:150
 					 		},
                      { display: '摘要', name: 'note', align: 'left',width:200
 					 		},
 					  { display: '入库时间', name: 'confirm_date', align: 'left',width:80
 					 		},
 					  { display: '确认人', name: 'confirmer_name', align: 'center',width:80
  					 		},
  					  { display: '单据金额', name: 'payable_money', align: 'right',width:100,
  					 			render:function(rowdata,rowindex,value){
  		                    		return formatNumber(rowdata.payable_money, '${p08005 }', 1);
  		                    	}
					 		},
				 	  { display: '发票金额', name: 'bill_money', align: 'right',width:100,
					 			 render: function(rowdata,rowindex,value){
	                        		
	                        		return formatNumber(rowdata.bill_money, '${p08005 }', 1);
	                        	 }
				 	  	 	}
                      ],
                      dataAction: 'server',dataType: 'server',usePager:true,url:'queryMedBillIn.do?isCheck=false&bill_id=${bill_id}&init=${is_init}', 
                      width:'100%',height:'100%',checkbox: true,rownumbers:true,enabledEdit:true,frozen : false ,delayLoad: false,//初始化不加载，默认false
                      selectRowButtonOnly:true,heightDiff:-40,
                      onsuccess:function(){
          				//is_addRow();
          			},
                      showTitle: true,detail: { onShowDetail: showDetail,height:'auto', reload: true ,single:true},//入库单明细
                      onAfterAddRow   : updateBill_money ,// 新增行后 自动填写 要生成的发票的  发票金额
                      toolbar:{items: [
                                { text: '添加（<u>A</u>）', id:'add', click: add_open, icon:'add',disabled:state ==1?false:true },
                                { line:true },
                                { text: '删除（<u>D</u>）', id:'delete', click: remove,icon:'delete',disabled:state ==1?false:true },
                   				{ line:true },
                                { text: '保存（<u>S</u>）', id:'save', click: saveMedBillMain,icon:'save',disabled:state ==1?false:true },
                   				{ line:true },
                   				{ text: '审核（<u>Z</u>）', id:'audit', click: audit, icon:'bluebook',disabled:state ==1?false:true },
                   				{ line:true },
                   				{ text: '消审（<u>U</u>）', id:'unaudit', click: unAudit,icon:'bookpen' }
                   				]
                            }
                    });

         gridManager = $("#maingrid").ligerGetGridManager();
     }
     function showDetail(row, detailPanel,callback)
     {
    	 data= row;
         detailGrid = document.createElement('div'); 
         $(detailPanel).append(detailGrid);
         grid = $(detailGrid).css('margin',10).ligerGrid({
             columns:
                         [
                         { display: '药品编码', name: 'inv_code',minWidth:90},
                         { display: '药品名称', name: 'inv_name',minWidth:190},
                         { display: '计量单位', name: 'unit_name',minWidth:75 },
                         { display: '规格型号', name: 'inv_model',minWidth:200,align: 'left'},
                         { display: '批号', name: 'batch_no',minWidth:100},
                         { display: '条形码', name: 'sn',minWidth:100  },
                         { display: '单价', name: 'price',minWidth:100 ,align: 'right', 
	                        	 render:function(rowdata,rowindex,value){
	                        		 return formatNumber(rowdata.price, '${p08006 }', 1); 
	                        	 }
                        	 },
                         { display: '数量', name: 'amount',width:90 },
                         { display: '单据金额', name: 'payable_money', align: 'right',minWidth:100 ,
                        	 render:function(rowdata,rowindex,value){
                        		 return formatNumber(rowdata.payable_money, '${p08005 }', 1); 
                        	 }
                         },
				 	  	 { display: '发票金额<E>', name: 'bill_money', align: 'right',minWidth:100 ,editor: { type: 'float'},
	                        	 render: function(rowdata,rowindex,value){
	                        		
	                        		 return formatNumber(rowdata.bill_money, '${p08005 }', 1);
	                        	 }
				 	  	 	}
                         ], 
                         dataAction: 'server',dataType: 'server', usePager :false , frozen : false ,width:'98%', isAddRow:false,
 			 			 //url : 'queryMedInDetail.do?isCheck=false&in_id='+row.in_id+'&bill_id='+row.bill_id+'&init='+row.init,
 			 			 allowAdjustColWidth : false , data:row.detail,enabledEdit : true,fixedCellHeight:true,
 			 			 onAfterEdit: getBill_money, 
 			 			 
         });
         
     }
     
   // 编辑完明细表格 发票金额后   更新 相关的发票金额
     function getBill_money(e){
    	 var bill_money = 0;//存储  主表格发票金额
    	 var detail =  grid.getData();
    	 if(detail.length > 0){
    		 $.each(detail,function(index,content){
    			 if(content.bill_money == ''){
    				 bill_money += 0;
    			 }else{
    				 bill_money += content.bill_money;
    			 }
    		 })
    	 }
    	 gridManager.updateCell('bill_money',bill_money,data);
    	 data.bill_money = bill_money ;
    	 data.detail={"Rows":detail,"Total":detail.length};
    	 updateBill_money();
     }
   //   新增行  更新 发票金额
     function updateBill_money(){
    	 var payable_money = 0;
         var bill_money =0;
    	 var dataMain =  inMainGrid.getData();
    	 if(dataMain.length > 0){
    		 $(dataMain).each(function(){
    			 payable_money += this.payable_money;
    			 if(this.bill_money == null | this.bill_money == '' | this.bill_money == 'undefined'){
    				 bill_money += 0;
    			 }else{
    				 bill_money += this.bill_money;
    			 }
    		 })
    	 }
    	 $("#payable_money").val(payable_money);
    	 $("#bill_money").val(bill_money);
     }
	//   删除行 更新 发票金额
     function updateDeletemoney(){
    	 var payable_money = 0;
         var bill_money =0;
         var t =  0;
         var s = 0;
    	 var dataMain =  inMainGrid.getCheckedRows();
    	 if(dataMain.length > 0){
    		 $(dataMain).each(function(){
    			 t += this.payable_money;
    			 if(this.bill_money == null | this.bill_money == '' | this.bill_money == 'undefined'){
    				 s += 0;
    			 }else{
    				 s += this.bill_money;
    			 }
    		 })
    	 }
    	 payable_money = $("#payable_money").val() - t;
    	 bill_money = $("#bill_money").val() - s;
    	 $("#payable_money").val(payable_money);
    	 $("#bill_money").val(bill_money);
     }
  /*  //   提示 用户维护 发票金额
   function warnIffo(){
	   var data = gridManager.getAdded()  ;
	   if(data.length > 0){
		   $.ligerDialog.warn("请您仔细核对发票金额，维护好变动的发票金额后,再保存");
	   }
   } */
   //生成流水号
     var curDate=new Date();
	 year = curDate.getFullYear();
	 month = curDate.getMonth();
    function setBillCode(){
    	var parm={
		 		 year:year,
		 		 month:month
		         };
    	 ajaxJsonObjectByUrl("setBillCode.do?isCheck=false",parm,function (responseData){
				$("#bill_code").val(responseData.prefixe+responseData.year+responseData.month+responseData.max_no);
    	 })
    }
    
     function add_open(){
    	 if( liger.get("sup_id").getValue().split(",")[0] == '' ){
    		 $.ligerDialog.error('请先选择供应商再添加入库单');
    	 }else{
    		 $.ligerDialog.open({url: 'inMainPage.do?isCheck=false&sup_id='+liger.get("sup_id").getValue().split(",")[0]
    		 	+'&sup_no='+liger.get("sup_id").getValue().split(",")[1]+
    			"&sup_code="+liger.get("sup_id").getText().split(" ")[0]+"&sup_name="+liger.get("sup_id").getText().split(" ")[1],height: 570,width: 980, 
    			 title:'选择 入库单',modal:true,showToggle:false,showMax:true,showMin: false,isResize:true , top:0 
 			}); 
    	 }
    	
     }
    function remove(){
    	var data = inMainGrid.getCheckedRows();
   		 if(data.length == 0){
   				$.ligerDialog.error('选择要删除的行!');
                return;
            }else{
	           	$.ligerDialog.confirm('确定删除?', function (yes){
	                	if(yes){
		             	 	for (var i = 0; i < data.length; i++){
		             	 		inMainGrid.remove(data[i]);
		             		 }
		             	 }
		            });
	           	//更新 发票金额
	           	updateDeletemoney()
	       }
    }
    function btn_saveColumn(){
 		
		   var path = window.location.pathname+"/maingrid";
		   var url = '../../../sys/addBatchSysTableStyle.do?isCheck=false';
		   saveColHeader({
			   grid:inMainGrid,
			   path:path,
			   url:url,
			   callback:function(data){
				   $.ligerDialog.success("保存成功");
			   }
		   });
	  
	   return false;
}
     function  save(){
    	 
    	var data = inMainGrid.getData();
    	
        var formPara={
				bill_id:'${bill_id}',
				bill_no:$("#bill_no").val(),
				bill_code:$("#bill_code").val(),
				bill_date:$("#bill_date").val(),
				bill_type:$("#bill_type").val(),
				sup_id:liger.get("sup_id").getValue().split(",")[0],
				sup_no:liger.get("sup_id").getValue().split(",")[1],
				pay_code:liger.get("pay_code").getValue(),
				payable_money :$("#payable_money").val() ,
				bill_money : $("#bill_money").val() ,
				note:$("#note").val() ,
				ori_no:'${ori_no}' ,
				stock_type_code:'${stock_type_code}' ,
				is_init:'${is_init}',
				maker:'${maker}' ,
				make_date:'${make_date}' ,
				checker:'${checker}',
   	         	chk_date:'${chk_date}',
   	         	state:'${state}',
   	         	is_init:'${is_init}',
   	           
	         };
       
     	var ParamVo =[];
     	var str = '';
     	var flag = 0 ;
   		var error = '';
   		
   		if(data.length > 0){
   			$.each(data,function(index,content){
   	   			if(content.bill_money == null || content.bill_money == '' || content.bill_money == 0 || content.bill_money < 0){
   	  	        		str+= content.in_no+",";
   	       	}else{
   	       		var data_detail= content.detail ;
   	      			$(data_detail.Rows).each(function (){
   	       			if(Math.abs(this.payable_money) < Math.abs(this.bill_money)){
   	       				error += content.in_no +","
   	 				}
   	          		   	ParamVo.push(
   	          				content.group_id   +"@"+ 
   	          				content.hos_id   +"@"+ 
   	          				content.copy_code   +"@"+ 
   	          				$("#bill_id").val()   +"@"+ 
   	          				content.in_id  +"@"+ 
   	          				this.in_detail_id  +"@"+ 
   	          				this.payable_money +"@"+ 
   	          				(!this.bill_money?this.payable_money:this.bill_money) +"@"+ 
   	      					$("#bill_no").val() +"@"+ 
   	      					$("#bill_date").val() +"@"+ 
   	      					content.bill_detail_id
   	      					
   	        			)
   	       		})
   	      		}
   	   		})
   		}else{
   			$.ligerDialog.error('<span style="color: red">没有发票明细数据，不能保存</span>');
   			return false ;
   		}
   	   		
   		if(str != ''){
	       		$.ligerDialog.error(str+'<span style="color: red">发票金额数据不对,请您仔细核对发票金额，维护好发票金额后,再保存</span>');
	       	}else{
	       		if(error != ''){
	           		$.ligerDialog.error(error+'<span style="color: red">入库单发票金额错误,请您仔细核对发票金额，维护好发票金额后,再保存</span>');
	           	}else{
	           		$.ligerDialog.confirm('确定要修改后的发票及发票明细信息吗?', function (yes){
	                   	if(yes){
	                   		 ajaxJsonObjectByUrl("updateMedBillMain.do?isCheck=false",formPara,function(responseData){
	               	            if(responseData.state=="true"){
	               	                parent.query();
	               	                if(responseData.detail == 1){
   	               	              	ajaxJsonObjectByUrl("updateMedBillDetail.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
 	                               		if(responseData.state=="true"){
 	                               		}
 	                               	}); 	
	               	                }
	               	               
	               	            }
	               	        });
	               		}
	                }); 
	           	}
	       	}
   		
   	}
    function loadHotkeys() {

  		hotkeys('S', saveMedBillMain);

  		hotkeys('A', add_open);
  		hotkeys('D', remove);
  		hotkeys('Z', audit);
  		hotkeys('U', unAudit);
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
	    // $("form").ligerForm();
	 }       
   
    function saveMedBillMain(){
        if($("form").valid()){
            save();
        }
   }
    
	// 审核
	function audit(){
		if (state != 1){
			$.ligerDialog.error('审核失败！'+bill_no+'单据不是未审核状态,不能审核');
			return;
		}else{
			var ParamVo =[];
			ParamVo.push(
					group_id   +"@"+ 
					hos_id   +"@"+ 
					copy_code    +"@"+ 
					bill_id  +"@"+ 
					bill_no   +"@"+ 
					$("bill_date").val()   +"@"+ 2
				) 
			$.ligerDialog.confirm('确定审核?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("updateBillState.do",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.state=="true"){
							self.location.reload(); //  刷新本页
							parent.query();
						}
					});
				}
			}); 
		}
	}
	//消审
	function unAudit(){
		
		if (state != 2){
			$.ligerDialog.error('消审失败！'+bill_no+'单据不是已审核状态,不能消审');
			return;
		}else{
			var ParamVo =[];
			ParamVo.push(
					group_id   +"@"+ 
					hos_id   +"@"+ 
					copy_code    +"@"+ 
					bill_id  +"@"+ 
					bill_no   +"@"+ 
					$("bill_date").val()   +"@"+ 1
				) 
			$.ligerDialog.confirm('确定消审?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("updateBillState.do",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.state=="true"){
							self.location.reload();  //刷新本页;
							parent.query();
						}
					});
				}
			}); 
		}
	}
    function loadDict(){
            //字典下拉框
	    	//供应商下拉框
			autocomplete("#sup_id", "../../queryHosSupDictDisable.do?isCheck=false", "id", "text", true, true,'',false,'',240);
            liger.get("sup_id").setValue('${sup_id},${sup_no}');
            liger.get("sup_id").setText('${sup_code} ${sup_name}');
			//付款条件
			autocomplete("#pay_code", "../../queryMedPayTerm.do?isCheck=false", "id", "text", true, true,'',false,'',200);
			liger.get("pay_code").setValue('${pay_code}');
	        liger.get("pay_code").setText('${pay_term_code} ${pay_term_name}');
	        
	        $("#bill_type").val('${bill_type}');
	        
	        $("#bill_money").ligerTextBox({width:200,disabled:true});
	        $("#bill_code").ligerTextBox({width:200,disabled:true});
	        $("#bill_no").ligerTextBox({width:240});
	        $("#bill_type").ligerTextBox({width:200});
	        $("#sup_id").ligerTextBox({width:240});
	        $("#pay_code").ligerTextBox({width:200});
	        $("#bill_date").ligerTextBox({width:200});
	        $("#note").ligerTextBox({width:535});
	        
	        $("#print").ligerButton({click: printDate, width:90});
			$("#printSet").ligerButton({click: printSet, width:100});
			
     } 
  //打印
	function printDate(){
		
		var useId=0;//统一打印
		if('${p08027 }'==1){
			//按用户打印
			useId='${sessionScope.user_id }';
		}
		
		/* var para={
			bill_id:$("#bill_id").val(),
			template_code:'08024',
			p_num:0,
			use_id:useId
		};
		
		printTemplate("queryMedBillMainByPrintTemlate.do",para); */
		var para={
    			template_code:'08024',
    			class_name:"com.chd.hrp.med.serviceImpl.bill.MedBillMainServiceImpl",
    			method_name:"queryMedBillMainByPrintPage",
    			//isSetPrint:flag,//是否套打，默认非套打
    			isPreview:true,//是否预览，默认直接打印
    			bill_id :$("#bill_id").val() ,
    			use_id:useId,
    			p_num:0
    	};
    	
    	officeFormPrint(para);
	}
	
	//打印设置
	function printSet(){
		
		
		var useId=0;//统一打印
		if('${p08027 }'==1){
			//按用户打印
			useId='${sessionScope.user_id }';
		}
		
		/* parent.parent.$.ligerDialog.open({url : 'hrp/med/bill/medbillmain/medBillMainPrintSetPage.do?template_code=08024&use_id='+useId,
			data:{}, height: $(parent).height(),width: $(parent).width(), title:'打印模板设置',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
		}); */
		officeFormTemplate({template_code:"08024",useId:useId});
	}
	function openInPage(obj){
		parent.updateInOpenForPrint(obj);
	}
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <div class="l-layout" id="layout1" style="height: 100%;" ligeruiid="layout1">
        	<div class="l-layout-content" position="top">
        	<input type="hidden" id="bill_id" name="bill_id"  value='${bill_id}'/>
        	<input type="hidden" id="curentDate" name="curentDate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" />
	      	<input type="hidden" id="payable_money" name="payable_money" type="text"  ltype="text" />
	      	<form name="form1" method="post"  id="form1" >
			     <table cellpadding="0" cellspacing="0" class="l-table-edit" >
					<tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>流水号<font color="red">*</font>:</b></td>
			            <td align="left" class="l-table-edit-td"><input name="bill_code" type="text" id="bill_code" disabled="disabled" value='${bill_code}' ltype="text" validate="{maxlength:20}" /></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>发票号<font color="red">*</font>:</b></td>
			            <td align="left" class="l-table-edit-td"><input name="bill_no" type="text" id="bill_no" value='${bill_no}' ltype="text"  validate="{required:true,maxlength:100}" /></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>开票日期<font color="red">*</font>:</b></td>
			            <td align="left" class="l-table-edit-td">
			            	<input class="Wdate" name="bill_date" type="text" id="bill_date" value='${bill_date}' ltype="text" style="width: 200px;" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" validate="{required:true,maxlength:20}" />
			            </td>
			            <td align="left"></td>
			        </tr> 
					<tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>发票类别<font color="red">*</font>:</b></td>
			            <td align="left" class="l-table-edit-td">
				            <select name="bill_type" id="bill_type" style="width: 135px;" >
		            			<option value="">请选择</option>
		                		<option value="1">普通发票</option>
		                		<option value="2">红冲发票</option>
		            		</select>
			            </td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>供货单位<font color="red">*</font>:</b></td>
			            <td align="left" class="l-table-edit-td"><input name="sup_id" type="text" id="sup_id" ltype="text" validate="{required:true}" /></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>付款条件:</b></td>
			            <td align="left" class="l-table-edit-td"><input name="pay_code" type="text" id="pay_code" ltype="text" validate="{required:false}" /></td>
			            <td align="left"></td>
			        </tr> 
			         <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>摘要:</b></td>
			            <td align="left" class="l-table-edit-td" colspan="4">
			            	<input name="note" type="text" id="note" value='${note}' ltype="text" validate="{maxlength:100}" />
			            </td>
			            <td align="left"></td>
						<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>发票金额<font color="red">*</font>:</b></td>
			            <td align="left" class="l-table-edit-td"><input name="bill_money" value='${bill_money}' type="text" id="bill_money" disabled="disabled" ltype="text" validate="{required:true,maxlength:20}" /></td>
			            <td align="left"></td>
			        </tr> 
			     </table>
		    </form>
        	</div>
        	<div class="l-layout-header" id="toptoolbar" position="center"></div>
	        <div title="" class="l-layout-content" style="" position="centerbottom">
	            <div id="maingrid" ></div>
	            
	            <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%"  style="margin-top: 5px;">
			
			<tr>	
				<td align="center" class="l-table-edit-td" colspan="3">
					
					<button id ="print" accessKey="P"><b>打印（<u>P</u>）</b></button>
					&nbsp;&nbsp; 
					<button id ="printSet" accessKey="M"><b>打印模板（<u>M</u>）</b></button>
					
				</td>
			</tr>
		</table>
	        </div>
      </div>  
    </body>
</html>
