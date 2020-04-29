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
    <script type="text/javascript">
     var dataFormat;
     var inMainGrid;
     var detailGrid;
     var gridManager;
     var detailManager;
     var data;
     var grid;
     var year;
     var month;
     var init = "0" ;
     var targetMap = new HashMap();//存放key 对应关系
     
     var detailMap = new HashMap();//存放 明细数据
     
     $(function (){
    	 
         loadDict();
         
         loadHead(null);
         
         loadForm();
         
         loadHotkeys();
         
     });  

     function loadHead(){
    	 var matInMainList = ${matInMainList};
     	 inMainGrid = $("#maingrid").ligerGrid({
            columns: [ 
                      { display: '入库单号', name: 'in_no', align: 'left',width:120
 					 		},
 					  { display: '业务类型', name: 'bus_type_name', align: 'left',width:80,
 					 			render: function(rowdate,rowindex,value){
 					 				if(rowdate.bus_type_code == null || rowdate.bus_type_code == '' | rowdate.bus_type_code == "undifined"){
 					 					return "期初未付款送货单";
 					 				}else{
 					 				return rowdate.bus_type_name ;
 					 				}
 					 			}
 					 		},
 					  { display: '仓库', name: 'store_name', align: 'left',width:150
 					 		},
                      { display: '供应商', name: 'sup_name', align: 'left', width:180
 					 		},
                      { display: '摘要', name: 'brief', align: 'left',width:160
 					 		},
 					  { display: '入库日期', name: 'confirm_date', align: 'left',width:80
 					 		},
 					  { display: '确认人', name: 'confirmer_name', align: 'left',width:80
 					 		},
 					  { display: '单据金额', name: 'payable_money', align: 'right',width:100,
 					 			render:function(rowdata,rowindex,vlue){
					 				return formatNumber(rowdata.payable_money, '${p04005 }', 1);
					 			}
  					 		},
 				 	  { display: '发票金额', name: 'bill_money', align: 'right',width:100,
  					 			render:function(rowdata,rowindex,vlue){
  					 				return formatNumber(rowdata.bill_money, '${p04005 }', 1);
					 			}
 				 	  		}
                      ],
                      usePager:true,width:'100%',height:'100%',
                      data: matInMainList, allowAdjustColWidth : false ,
                      checkbox: true,rownumbers:true,enabledEdit:true, frozen : false ,
                      delayLoad: false,//初始化不加载，默认false
                      selectRowButtonOnly:true,heightDiff: 0,
                      showTitle: true,detail: { onShowDetail: showDetail , height:'auto',reload: true ,single:true },//入库单明细
                      onSelectRow  : updateBill_money ,// 勾选入库单后  自动填写 要生成的发票的  发票金额
                      onUnSelectRow : updateBill_money ,// 取消勾选入库单后  自动填写 要生成的发票的  发票金额
                      toolbar: { items: [
     	                { text: '保存（<u>S</u>）', id:'save', click: saveMatBillMain,icon:'save' },
						{ line:true }
     				]},
                  });

         gridManager = $("#maingrid").ligerGetGridManager();
     }
     function showDetail(row, detailPanel,callback)
     {
    	 data= row;
         detailGrid = document.createElement('div'); 
         $(detailPanel).append(detailGrid);
       	 grid =$(detailGrid).css('margin',10).ligerGrid({
             columns:
                    [
                    { display: '材料编码', name: 'inv_code',width:100},
                    { display: '材料名称', name: 'inv_name',width:190},
                    { display: '计量单位', name: 'unit_name',width:80 },
                    { display: '规格型号', name: 'inv_model',width:200, align:'left' },
                    { display: '批号', name: 'batch_no',width:90 },
                    { display: '条形码', name: 'sn' ,width:100},
                    { display: '单价', name: 'price',width:98 , align: 'right',
                    	render:function(rowdata,rowindex,vlue){
			 				return formatNumber(rowdata.price, '${p04006 }', 1);
			 			}
                    },
                    { display: '数量', name: 'amount',width:80, align: 'right'},
                    { display: '单据金额', name: 'payable_money', align: 'right',width:100 ,
                    	render:function(rowdata,rowindex,value){
                    		return formatNumber(rowdata.payable_money, '${p04005 }', 1);
                    	}
                    },
	  	 			{ display: '发票金额<E>', name: 'bill_money', align: 'right',editor: { type: 'float'},width:100 ,
                   	 		render: function(rowdata,rowindex,value){
                   	 			return formatNumber(rowdata.bill_money, '${p04005 }', 1);
                   	 		}
	  	 				}
                    ], 
                    dataAction : 'server',dataType : 'server', usePager : false,checkbox: true,selectRowButtonOnly:true,
                    //delayLoad: false,//初始化不加载，默认false
                    width:'98%',height:'auto',isScoll:true ,allowAdjustColWidth : false , frozen : false , isAddRow:false,
			 		url : '../../bill/matbillmain/queryMatInDetail.do?isCheck=false&in_id='+row.in_id+'&bill_id='+row.bill_id+'&init='+row.init+'&flag=0',
			 		enabledEdit : true,fixedCellHeight:true, onAfterEdit : f_onAfterEdit, 
			 		onSelectRow  : detailData , onUnSelectRow :removeDetail
         });
     }
     
     //勾选明细后  存储数据  更新 主表格发票金额
     function detailData(rowdata,rowid,rowobj){
    	 var bill_money = 0;//存储  主表格发票金额
    	 var detailDate =  grid.getSelectedRows();
    	 if(detailDate.length > 0){
    		 $.each(detailDate,function(index,content){
    			 bill_money += content.bill_money;
    		 })
    	 }
    	 gridManager.updateCell('bill_money',bill_money,data);
    	 targetMap.put(data.__id+'|'+rowid,rowdata);
    	 if(!gridManager.isSelected(data)){
    		 gridManager.select(data);
    	 }
    	 updateBill_money();
     }
     // 取消勾选明细后  移除存储的数据  更新 主表格发票金额
     function removeDetail(rowdata,rowid,rowobj){
    	 var bill_money = 0;//存储  主表格发票金额
    	 var detailDate =  grid.getSelectedRows();
    	 if(detailDate.length > 0){
    		 $.each(detailDate,function(index,content){
    			 bill_money += content.bill_money;
    		 })
    	 }
    	 gridManager.updateCell('bill_money',bill_money,data);
    	 targetMap.remove(data.__id+'|'+rowid,rowdata);
    	 var selectRow = grid.getSelectedRows();
    	 if(selectRow.length == 0){
    		 gridManager.unselect(data);
    	 }
    	 updateBill_money();
     }
      // 编辑完明细表格单元格后   选择该行数据
     function f_onAfterEdit(e){
    	 var bill_money = 0;//存储  主表格发票金额
    	 var detailDate =  grid.getData();
    	 if(detailDate.length > 0){
    		 $.each(detailDate,function(index,content){
    			 if(content.bill_money == ""){
    				 content.bill_money = 0 ;
    				 bill_money += 0;
    			 }else{
    				 bill_money += content.bill_money;
    			 }
    		 })
    	 }
    	 gridManager.updateCell('bill_money',bill_money,data);
    	 if(!gridManager.isSelected(data)){
    		 gridManager.select(data);
    	 }
    	 data.bill_money = bill_money ;
    	 data.detail={"Rows":detailDate,"Total":detailDate.length};
    	 updateBill_money();
    	 grid.updateTotalSummary();
     } 
   //勾选入库单后  自动填写 要生成的发票的  发票金额
     function updateBill_money(){
         var payable_money =0;
         var bill_money =0;
    	 var dataMain =  inMainGrid.getSelectedRows();
    	 if(dataMain.length > 0){
    		 $(dataMain).each(function(){
    			 payable_money += this.payable_money;
    			 bill_money += this.bill_money;
    		 })
    	 }
    	 $("#bill_money").val(bill_money);
    	 $("#payable_money").val(payable_money);
     }
    //生成流水号
     var curDate=new Date();
	 year = curDate.getFullYear();
	 month = curDate.getMonth();
	 day = curDate.getDate();
    function setBillCode(){
    	var bill_type = $("#bill_type").val();
    	var parm={
		 		 year:year,
		 		 month:month ,
		 		 day:day ,
		 		bill_type:bill_type
		         };
    	 ajaxJsonObjectByUrl("../../bill/matbillmain/setBillCode.do?isCheck=false",parm,function (responseData){
				$("#bill_code").val(responseData.prefixe+responseData.year+responseData.month+responseData.max_no);
    	 })
    }
    
    function setInit(){
    	if($("#initOnly").prop("checked") == true){
    		init = "1";
    	}else{
    		init = "0" ;
    	}
    	query();
    }
     //添加
     function add_open(){
    	 if( liger.get("sup_id").getValue().split(",")[0] == '' | liger.get("dept_id").getValue().split(",")[0] ==''){
    		 $.ligerDialog.error('请先选择供应商和科室后再再添加发票');
    	 }else{
    		 $.ligerDialog.open({url: '../../bill/matbillmain/inMainPage.do?isCheck=false',height: 500,width: 1000, top: 0 ,
 				title:'选择 入库单',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true
 				});
    	 }
    	
     }
  /*    //删除
    function remove(){
    	var data = inMainGrid.getCheckedRows();
   		 if(data.length == 0){
                alert('选择要删除的行!');
                return;
            }else{
           	 $.ligerDialog.confirm('确定删除?', function (yes){
                	if(yes){
                		var delData =[];
	             	 	for (var i = 0; i < data.length; i++){
	             	 		inMainGrid.remove(data[i]);
	             		 }
	             	 }
	            }); 
	         }
    } */
     //
    function  save(){
        var formPara={
        		bill_id:'',
        		bill_code:$("#bill_code").val(),
        		bill_no:$("#bill_no").val(),
        		bill_date:$("#bill_date").val(),
        		bill_type:$("#bill_type").val(),
   	            sup_id:liger.get("sup_id").getValue().split(",")[0],
   	            sup_no:liger.get("sup_id").getValue().split(",")[1],
   	            pay_code:liger.get("pay_code").getValue(),
   	        	payable_money :$("#payable_money").val() ,
   	            bill_money : $("#bill_money").val() ,
   	            note:$("#note").val(),
   	            init: init
	         };
        var data = inMainGrid.getSelectedRows();
        console.log(data);
        if (data.length == 0){
        	$.ligerDialog.error('请选择入库单后再保存');
        }else{
       		var ParamVo =[];
          	var str = '' ;
          	if(JSON.stringify(targetMap.map) != '{}'){
                $.each(data,function(index,content){	
               		var key = targetMap.keySet() ;
               		var flag = false; // 判断该条数据  是否有明细数据 标志 （false：没有   true： 有）
            		for(var i=0;i< key.length;i++){
            			var data_detail = null;
            			if(content.__id == key[i].split("|")[0]){
            				if(!flag){
            					flag =true ; // 重置标志
            				}
            				data_detail= targetMap.get(key[i]);
    	        			$(data_detail).each(function (){	
    	        				if(Math.abs(this.payable_money) < Math.abs(this.bill_money)){
    	        					str += content.in_no +","
    	        				}
    		           		   	ParamVo.push(
    		           				content.group_id   +"@"+ 
    		           				content.hos_id   +"@"+ 
    		           				content.copy_code   +"@"+ 
    		           				content.bill_id   +"@"+ 
    		           				content.in_id  +"@"+ 
    		           				this.in_detail_id  +"@"+ 
    		           				this.payable_money +"@"+ 
    		           				this.bill_money +"@"+ 
    		       					$("#bill_no").val() +"@"+ 
    		       					content.bill_detail_id
    		        			)
    	        			})
            			}
            		}
               		if(!flag){
               			var data_detail= content.detail ;
       	       			$(data_detail.Rows).each(function (){
       	       			if(Math.abs(this.payable_money) < Math.abs(this.bill_money)){
        					str += content.in_no +","
        				}
       	           		   	ParamVo.push(
       	           				content.group_id   +"@"+ 
       	           				content.hos_id   +"@"+ 
       	           				content.copy_code   +"@"+ 
       	           				content.bill_id   +"@"+ 
       	           				content.in_id  +"@"+ 
       	           				this.in_detail_id  +"@"+ 
       	           				this.payable_money +"@"+ 
       	           				(!this.bill_money?this.payable_money:this.bill_money) +"@"+ 
       	       					$("#bill_no").val() +"@"+ 
       	       					content.bill_detail_id
       	       					
       	         			)
       	        		})
               		}
            	})
            	if(str != ''){
            		$.ligerDialog.error(str+'<span style="color: red">入库单发票金额错误,请您仔细核对发票明细金额，维护好发票金额后,再保存</span>');
            	}else{
            		$.ligerDialog.confirm('<span style="color : red">您有入库单选择了明细记录,该入库单的发票明细将只保存您选择的明细.</span>确定要保存吗?', function (yes){
                    	if(yes){
                    		 ajaxJsonObjectByUrl("../../bill/matbillmain/addMatBillMain.do?isCheck=false",formPara,function(responseData){
                   	            if(responseData.state=="true"){
                   	                ajaxJsonObjectByUrl("../../bill/matbillmain/addMatBillDetail.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
                                   		if(responseData.state=="true"){
                                   			frameElement.dialog.close();
                                   		}
                                   	});
                   	            }
                   	        });
                   		}
                    }); 
            	}
        		
        	}else{
        		var ParamVo =[];
        		var str = '';
        		$.each(data,function(index,content){
        			if(content.bill_money == null | content.bill_money == ''){
       	        		str+= content.in_no+",";
       	        	}else{
       	        		var data_detail= content.detail ;
       	       			$(data_detail.Rows).each(function (){
	       	       			if(Math.abs(this.payable_money) < Math.abs(this.bill_money)){
	     						str += content.in_no +","
	     					}
       	           		   	ParamVo.push(
       	           				content.group_id   +"@"+ 
       	           				content.hos_id   +"@"+ 
       	           				content.copy_code   +"@"+ 
       	           				content.bill_id   +"@"+ 
       	           				content.in_id  +"@"+ 
       	           				this.in_detail_id  +"@"+ 
       	           				this.payable_money +"@"+ 
       	           				(!this.bill_money?this.payable_money:this.bill_money) +"@"+ 
       	       					$("#bill_no").val() +"@"+ 
       	       					content.bill_detail_id
       	       					
       	         			)
       	        		})
       	        		
       	       		}
        		})
        		if(str != ''){
            		$.ligerDialog.error(str+'<span style="color: red">入库单发票金额错误,请您仔细核对发票明细金额，维护好发票金额后,再保存</span>');
            	}else{
            		$.ligerDialog.confirm('确定要添加发票及发票明细信息吗?', function (yes){
                    	if(yes){
                    		
                    		 ajaxJsonObjectByUrl("../../bill/matbillmain/addMatBillMain.do?isCheck=false",formPara,function(responseData){
                   	            if(responseData.state=="true"){
                   	                ajaxJsonObjectByUrl("../../bill/matbillmain/addMatBillDetail.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
                                   		if(responseData.state=="true"){
                                   			frameElement.dialog.close();
                                   		}
                                   	}); 
                   	            }
                   	        });
                   		}
                    }); 
            	}
        	}
            
        };	
    }
    function loadHotkeys() {

 		hotkeys('S', saveMatBillMain);

 		hotkeys('A', add_open);
 		
 		hotkeys('D', remove);
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
	   
    function saveMatBillMain(){
        if($("form").valid()){
            save();
        }
	}
    
	//字典下拉框
    function loadDict(){
		//供应商下拉框
		$("#sup_id").ligerComboBox({width:240,disabled:true,cancelable: false});
		liger.get("sup_id").setValue("${sup_id}");
		liger.get("sup_id").setText("${sup_text}");
		//付款条件
		autocomplete("#pay_code", "../../queryMatPayTerm.do?isCheck=false", "id", "text", true, true,'',true,'',200);
		
		autodate("#bill_date","yyyy-MM-dd");

		$("#bill_code").ligerTextBox({width:200,disabled:true});
        $("#bill_no").ligerTextBox({width:240});
        $("#bill_date").ligerTextBox({width:200});
        $("#bill_type").ligerTextBox({width:200});
        $("#sup_id").ligerTextBox({width:240});
        $("#pay_code").ligerTextBox({width:200});
		$("#bill_money").ligerTextBox({width:200,disabled:true});
        $("#note").ligerTextBox({width:535});
	} 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <div class="l-layout" id="layout1" style="height: 100%;" ligeruiid="layout1">
        	<div class="l-layout-content" position="top">
        	<input type="hidden" id="curentDate" name="curentDate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" />
	      	<input type="hidden" id="payable_money" name="payable_money" type="text"  ltype="text"  />
	      	<form name="form" method="post"  id="form" >
			     <table cellpadding="0" cellspacing="0" class="l-table-edit" >
					<tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>流水号<font color="red">*</font>:</b></td>
			            <td align="left" class="l-table-edit-td"><input name="bill_code" type="text" id="bill_code" disabled="disabled" ltype="text" validate="{maxlength:20}" /></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>发票号<font color="red">*</font>:</b></td>
			            <td align="left" class="l-table-edit-td"><input name="bill_no" type="text" id="bill_no" ltype="text" onChange="setBillCode()" validate="{required:true,maxlength:20}" /></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>开票日期<font color="red">*</font>:</b></td>
			            <td align="left" class="l-table-edit-td">
			            	<input class="Wdate" name="bill_date" type="text" id="bill_date" ltype="text" style="width: 200px;" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" validate="{required:true,maxlength:20}" />
			            </td>
			            <td align="left"></td>
			        </tr> 
					<tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>发票类别<font color="red">*</font>:</b></td>
			            <td align="left" class="l-table-edit-td">
				            <select name="bill_type" id="bill_type" style="width: 135px;" >
		                		<option value="1">普通发票</option>
		                		<option value="2">红冲发票</option>
		            		</select>
			            </td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>供应单位<font color="red">*</font>:</b></td>
			            <td align="left" class="l-table-edit-td"><input name="sup_id" type="text" id="sup_id" ltype="text"  validate="{required:true,}" /></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>付款条件<font color="red">*</font>:</b></td>
			            <td align="left" class="l-table-edit-td"><input name="pay_code" type="text" id="pay_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
			            <td align="left"></td>
			        </tr> 
			        <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>摘要:</b></td>
			            <td align="left" class="l-table-edit-td" colspan="4">
			            	<input name="note" type="text" id="note" ltype="text" validate="{maxlength:100}" />
			            </td>
			            <td align="left"></td>
						<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>发票金额<font color="red">*</font>:</b></td>
			            <td align="left" class="l-table-edit-td"><input name="bill_money" type="text" id="bill_money" disabled="disabled" ltype="text" validate="{required:true,maxlength:20}" /></td>
			            <td align="left"></td>
			        </tr> 
			        
			     </table>
		    </form>
        	</div>
	        <div title="" class="l-layout-content" style="" position="center">
	            <div id="maingrid" ></div>
	        </div>
      </div>  
    </body>
</html>
        