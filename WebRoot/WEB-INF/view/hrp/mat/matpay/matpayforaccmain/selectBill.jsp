<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
    var mainGrid;
    var grid;
    var gridManager = null;
    var userUpdateStr;
    var is_init ;
    var data ;
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
		 loadHotkeys();
		 query();
    });
    //查询
    function  query(){
		mainGrid.options.parms=[];
		mainGrid.options.newPage=1;
        //根据表字段进行添加查询条件
        mainGrid.options.parms.push({
			name : 'sup_id',
			value : liger.get("sup_id").getValue() == null ? "" : liger.get("sup_id").getValue().split(",")[0]
		}); 
		mainGrid.options.parms.push({
			name : 'dept_id',
			value : liger.get("dept_id").getValue() == null ? "" : liger.get("dept_id").getValue().split(",")[0]
		}); 
		
		mainGrid.options.parms.push({
			name : 'stocker',
			value : liger.get("stocker").getValue() == null ? "" : liger.get("stocker").getValue().split(",")[0]
		}); 
		mainGrid.options.parms.push({
			name : 'year',
			value : liger.get("year").getValue() == null ? "" : $("#year").val()
		}); 
		mainGrid.options.parms.push({
			name : 'month',
			value : liger.get("month").getValue() == null ? "" : $("#month").val()
		}); 
		
		mainGrid.options.parms.push({
			name : 'bill_type',
			value : liger.get("bill_type").getValue() == null ? "" : $("#bill_type").val()
		}); 
		mainGrid.options.parms.push({
			name : 'bill_no',
			value : liger.get("bill_no").getValue() == null ? "" : $("#bill_no").val()
		}); 
		mainGrid.options.parms.push({
			name : 'note',
			value : liger.get("note").getValue() == null ? "" : $("#note").val()
		}); 
		
		mainGrid.options.parms.push({name:'is_init', value: is_init });
    	//加载查询条件
    	mainGrid.loadData(mainGrid.where);
     }

    function loadHead(){
    	mainGrid = $("#maingrid").ligerGrid({
			columns: [
			      	  { display: '发票编号', name: 'bill_no', align: 'left',width:120
 					 		},
                      { display: '开票日期', name: 'bill_date', align: 'left',width:90
 					 		},
                      { display: '摘要', name: 'note', align: 'left',width:200
 					 		},
 					  { display: '供应商', name: 'sup_name', align: 'left',width:209
  					 		},
                      { display: '采购员', name: 'emp_name', align: 'left',width:90
 					 		},
 					  { display: '发票金额', name: 'payable_money', align: 'right',width:110,
 					 			 render:function(rowdata,rowindex,value){
 		 	                		return formatNumber(rowdata.payable_money ,'${p04005 }', 1);
 		 	                	 }
  					 		},
  					  { display: '已付金额', name: 'payed_money', align: 'right',width:110,
 				 	  			render: function(rowdata,rowindex,value){
 				 	  				if(rowdata.payed_money == null | rowdata.payed_money == '' | rowdata.payed_money == 'undefined'){
 				 	  					rowdata.payed_money = 0;
 				 	  					return formatNumber(rowdata.payed_money ,'${p04005 }', 1);
 				 	  				}else{
 				 	  					return formatNumber(rowdata.payed_money ,'${p04005 }', 1);
 				 	  				}
 				 	  			}
				 			},
		 	  		 { display: '本次付款金额', name: 'pay_money', align: 'right',width:110,
				 				render : function(rowdata,rowindex,value){
				 					if(rowdata.pay_money == null | rowdata.pay_money == '' | rowdata.pay_money == 'undefined'){
				 						rowdata.pay_money = 0;
				 						return formatNumber(rowdata.pay_money ,'${p04005 }', 1);
 				 	  				}else{
 				 	  					return formatNumber(rowdata.pay_money ,'${p04005 }', 1);
 				 	  				}
				 				}
		 	  			}
		 		 ],
			dataAction: 'server',dataType: 'server',usePager: true,checkbox: true,rownumbers:true,frozen:false,
			width: '100%', height:'100%', url:'queryMatBillMain_Pay.do?isCheck=false',
			delayLoad: true,//初始化不加载，默认false
			selectRowButtonOnly:true,heightDiff:0 , allowAdjustColWidth : false ,
			onAfterShowData  : updateMoney ,//  显示完数据后   更新相应的 金额
			showTitle: true,detail: { onShowDetail: inDetail,height:'auto',reload: true, single: true },//入库单明细
			toolbar: { items: [
						{ text: '查询（<u>Q</u>）', id:'search', click: query, icon:'search' },
						{ line:true },
						{ text: '确认（<u>A</u>）', id:'add', click: add_open, icon:'add' },
						{ line:true },
						{ text: '<input type="checkbox" id="initOnly" />只显示期初发票', id:'', click: setInit,icon:'' }
					]}
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    function inDetail(row, detailPanel,callback)
    {
    	data = row ;
        var detailGrid = document.createElement('div'); 
        $(detailPanel).append(detailGrid);
        grid = $(detailGrid).css('margin',10).ligerGrid({
            columns:
                     [
						{ display: '入库单号', name: 'in_no', align: 'left',width:120},
                        { display: '材料编码', name: 'inv_code',width:100},
                        { display: '材料名称', name: 'inv_name',width:100},
                        { display: '计量单位', name: 'unit_code',width:50 },
                        { display: '规格型号', name: 'inv_model',width:180},
                        { display: '批号', name: 'batch_no',width:100 },
                        { display: '条形码', name: 'sn' ,width:100},
                        { display: '单价', name: 'price' ,align: 'right',width:100,
                        	 	render:function(rowdata,rowindex,value){
		 	                		return formatNumber(rowdata.price ,'${p04006 }', 1);
		 	                	 }
                        	},
                        { display: '数量', name: 'amount',width:90 },
                        { display: '发票金额', name: 'bill_money',align: 'right',width:100,
                        	 	render:function(rowdata,rowindex,value){
		 	                		return formatNumber(rowdata.bill_money ,'${p04005 }', 1);
		 	                	}
                        	},
                        { display: '已付金额', name: 'payed_money', align: 'right',width:100,
				 	  	 	render: function(rowdata,rowindex,value){
			 	  				if(rowdata.payed_money == null | rowdata.payed_money == '' | rowdata.payed_money == 'undefined'){
			 	  					rowdata.payed_money = 0;
			 	  					return "0" ;
			 	  				}else{
			 	  					return  formatNumber(rowdata.payed_money ,'${p04005 }', 1);
			 	  				}
			 	  			}
				 		},
		 	  		 { display: '本次付款金额', name: 'pay_money', align: 'right',editor: { type: 'float'},width:100,
				 			render: function(rowdata,rowindex,value){
				 				if(rowdata.pay_money == null | rowdata.pay_money == '' | rowdata.pay_money == 'undefined'){
				 					rowdata.pay_money =  rowdata.bill_money - rowdata.payed_money ;
				 					return formatNumber(rowdata.pay_money ,'${p04005 }', 1);
				 				}else{
				 					return formatNumber(rowdata.pay_money ,'${p04005 }', 1);
				 				}
		 	  					
			 	  			}
		 	  			},{ display: '优惠金额', name: 'dis_money', align: 'right',editor: { type: 'float'},width:100,
				 			render: function(rowdata,rowindex,value){
				 				if(rowdata.dis_money == null | rowdata.dis_money == '' | rowdata.dis_money == 'undefined'){
				 					rowdata.dis_money =  0 ;
				 					return formatNumber(rowdata.dis_money ,'${p04005 }', 1);
				 				}else{
				 					return formatNumber(rowdata.dis_money ,'${p04005 }', 1);
				 				}
			 	  			}
		 	  			}
                     ], 
                    dataAction: 'server',dataType: 'server',checkbox: true, showToggleColBtn: false, width: '98%',
                    usePager:false,showTitle: false, 
                    //url:'queryMatBillDetail_Pay.do?isCheck=false&bill_id='+row.bill_id+'&is_init='+row.is_init,
					delayLoad: false,//初始化不加载，默认false
        			data:row.detail ,
        			onAfterShowData: callback,frozen:false, onSelectRow  : detailData , onUnSelectRow :removeDetail
        });  
    }
    //键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
		hotkeys('A', add_open);
	}
	 //是否只显示期初发票
    function setInit(){
    	if($("#initOnly").prop("checked") == true){
    		is_init = "1";
    	}else{
    		is_init = "0";
    	}
    	query();
    }
    //勾选明细后  更新主表格相关金额  选中主表格记录 附加明细数据
    function detailData(rowdata,rowid,rowobj){
    	var bill_money = 0; //存储  主表格 发票金额
    	var payed_money = 0;//存储  主表格 已付金额
    	var pay_money = 0;//存储  主表格 付款金额
	   	var detail = grid.getSelectedRows();
    	if(detail.length > 0){
    		 $.each(detail,function(index,content){
    			 bill_money += content.bill_money;
    			 if(content.payed_money == ''){
	   				 content.payed_money = 0 ;
	   				 payed_money += 0;
	   			 }else{
	   				 payed_money += content.payed_money;
	   			 }
	   			 if(content.pay_money == ''){
	   				 content.pay_money = 0 ;
	   				 pay_money += 0;
	   			 }else{
	   				 pay_money += content.pay_money;
	   			 }
    		 })
    	 }
    	 gridManager.updateCell('payable_money',bill_money,data);
    	 gridManager.updateCell('payed_money',payed_money,data);
    	 gridManager.updateCell('pay_money',pay_money,data);
    	data.detail={"Rows":detail,"Total":detail.length};
	   	if(!gridManager.isSelected(data)){
	   		 gridManager.select(data);
	   	}
    }
    // 取消勾选明细后  判断是否取消选中主表格记录 重新附加明细数据
    function removeDetail(rowdata,rowid,rowobj){
    	 var bill_money = 0; //存储  主表格 已消耗金额
    	 var payed_money = 0;//存储  主表格 已付金额
    	 var pay_money = 0;//存储  主表格 付款金额
	   	 var detail = grid.getSelectedRows();
	   	 if(detail.length > 0){
    		 $.each(detail,function(index,content){
    			 bill_money += content.bill_money;
    			 if(content.payed_money == ''){
	   				 content.payed_money = 0 ;
	   				 payed_money += 0;
	   			 }else{
	   				 payed_money += content.payed_money;
	   			 }
	   			 if(content.pay_money == ''){
	   				 content.pay_money = 0 ;
	   				 pay_money += 0;
	   			 }else{
	   				 pay_money += content.pay_money;
	   			 }
    		 })
    	 }
    	 gridManager.updateCell('payable_money',bill_money,data);
    	 gridManager.updateCell('payed_money',payed_money,data);
    	 gridManager.updateCell('pay_money',pay_money,data);
    	 data.detail={"Rows":detail,"Total":detail.length};
	   	 if(detail.length == 0){
	   		 gridManager.unselect(data);
	   	 }
    }
    
 	// 显示完数据 后  更新相应的 金额
    function updateMoney(){
	   	// 
	   	var dataMain =  mainGrid.getData();
	   	$.each(dataMain,function(index,content){
	 	   	var mainOut_money = 0 ; //存储 该条入库单 所有材料的  已消耗金额 总额
	 	   	var mainPayed_money = 0 ; //存储 该条入库单 所有材料的  已付金额 总额
	 	   	var mainPay_money = 0 ; //存储 该条入库单 所有材料的  本次付款金额 总额
	 	   	var detail = content.detail ;
	 	   	$(detail.Rows).each(function (){
	 	   		//mainOut_money += this.out_money ;
	 	   		mainPayed_money += this.payed_money ;
	 	   		mainPay_money += this.pay_money ;
	 		})
	 	   //	gridManager.updateCell('out_money',mainOut_money,index);
	 		if(!content.payed_money){
	 			gridManager.updateCell('payed_money',mainPayed_money,index);
	 		}
	     	gridManager.updateCell('pay_money',mainPay_money,index);
	   	})
     }
    function add_open(){
    	
    	 var rows = mainGrid.getCheckedRows(); 
         
         if(rows.length == 0){
        	 $.ligerDialog.error('请您选择发票!');
             return;
         }else{
        	 /*var bill_nos ='';
        	 var detialStr = '' ;
        	 var flag = false ; // 明细数据  本次付款金额是否为 0 标志 
         	 for (var i = 0; i < rows.length; i++){
         		 if(rows[i].detail == null || rows[i].pay_money == 0){
         			bill_nos += rows[i].bill_no +"," ;
         		 }else{
         			$(rows[i].detail.Rows).each(function (){
         				if(this.pay_money == 0){
         					flag = true ;
         				}
         			})
         			if(flag){
         				detialStr += rows[i].bill_no +"," ;
         			}
         		 }
              }
         	if(bill_nos != ""){
				$.ligerDialog.error('添加失败！发票编号:<span style="color: red">'+bill_nos+'本次付款金额为0,请您核对数据后再添加</span>');
				return;
			}else{
				 if(detialStr != ''){
					$.ligerDialog.error('添加失败！发票编号:<span style="color: red">'+detialStr+'有明细数据的本次付款金额为0,请核对明细数据后再添加</span>');
					return;
				}else{ */
					//alert(JSON.stringify(rows));
					parent.inMainGrid.addRows(rows);
		         	parent.$.ligerDialog.close();
		         	parent.$(".l-dialog,.l-window-mask").remove();
				//}
	             
			//}
         }
	}
    
    function loadDict(){
		//字典下拉框
		
		//科室：当前用户有权限的职能科室列表（HOS_DEPT_DICT 中is_stop=0的职能科室列表）
		autocomplete("#dept_id", "../../querySignedDept.do?isCheck=false", "id", "text", true, true,'',false,'',200);
		//供应商下拉框
		autocomplete("#sup_id", "../../queryHosSupDict.do?isCheck=false", "id", "text", true, true);
		liger.get("sup_id").setValue('${sup_id},${sup_no}');
		liger.get("sup_id").setText('${sup_code} ${sup_name}');
		//采购员下拉框
		autocomplete("#stocker", "../../queryMatStoctEmpDict.do?isCheck=false", "id", "text", true, true);
		//仓库下拉框
		autocomplete("#store_id", "../../queryMatStore.do?isCheck=false", "id", "text", true, true);
        $("#sup_id").ligerTextBox({width:200,disabled:true});
        $("#dept_id").ligerTextBox({width:200});
        $("#stocker").ligerTextBox({width:200});
        $("#year").ligerTextBox({width:200});
        $("#month").ligerTextBox({width:200});
        $("#bill_no").ligerTextBox({width:200});
        $("#bill_type").ligerTextBox({width:200});
        $("#note").ligerTextBox({width:500});
	}  
	
	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar" ></div>
	<form name="form1" method="post"  id="form1" >
		 <table cellpadding="0" cellspacing="0" class="l-table-edit" >
			<tr>
				<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>供应单位<font color='red'>*</font>:</b></td>
	            <td align="left" class="l-table-edit-td"><input name="sup_id" type="text" id="sup_id" ltype="text" validate="{required:true}" /></td>
	            <td align="left"></td>
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>科室<font color='red'>*</font>:</b></td>
	            <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
	            <td align="left"></td>
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>采购员<font color='red'>*</font>:</b></td>
	            <td align="left" class="l-table-edit-td"><input name="stocker" type="text" id="stocker" ltype="text" validate="{required:true,maxlength:20}" /></td>
	            <td align="left"></td>
	        </tr> 
	        <tr>
	        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>起始年月<font color="red">*</font>:</b></td>
	            <td align="left" class="l-table-edit-td">
	            	<input class="Wdate" name="year" type="text" id="year" ltype="text"  onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" validate="{required:true,maxlength:20}" />
	            </td>
	            <td align="left"></td>
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>起始年月<font color="red">*</font>:</b></td>
	            <td align="left" class="l-table-edit-td">
	            	<input class="Wdate" name="month" type="text" id="month" ltype="text"  onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" validate="{required:true,maxlength:20}" />
	            </td>
	            <td align="left"></td>
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>发票类型<font color="red">*</font>:</b></td>
	            <td align="left" class="l-table-edit-td">
	            	 <select name="bill_type" id="bill_type" style="width: 135px;" >
		                		<option value="1">普通发票</option>
		                		<option value="2">红冲发票</option>
		            		</select>
	            </td>
	            <td align="left"></td>
	        </tr> 
	        <tr>
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>发票号:</b></td>
	            <td align="left" class="l-table-edit-td"><input name="bill_no" type="text" id="bill_no" ltype="text" validate="{required:true,maxlength:20}" /></td>
	            <td align="left"></td>
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>摘要:</b></td>
	            <td align="left" class="l-table-edit-td" colspan="4"><input name="note" type="text" id="note" ltype="text" validate="{required:true,maxlength:20}" /></td>
	            <td align="left"></td>
	        </tr> 
	    </table>
    </form>
	<div id="maingrid"></div>
</body>
</html>
