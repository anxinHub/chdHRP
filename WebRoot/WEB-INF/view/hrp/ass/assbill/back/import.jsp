<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
        $("#ass_in_no").ligerTextBox({width:160});
        $("#in_date_beg").ligerTextBox({width:90});
        $("#in_date_end").ligerTextBox({width:90});
        $("#ven_name").ligerTextBox({
			width : 220,
			disabled : true,
			cancelable : false
		});
        $("#store_name").ligerTextBox({
			width : 160,
			disabled : true,
			cancelable : false
		});
        $("#pay_plan_money").ligerTextBox({
			width : 160,
			disabled : true,
			cancelable : false
		});
        $("#ass_amount").ligerTextBox({
			width : 235,
			disabled : true,
			cancelable : false
		});
        $("#ass_card_no").ligerTextBox({width:220});
        $("#change_no").ligerTextBox({width:160});
        $("#change_date_beg").ligerTextBox({width:90});
        $("#change_date_end").ligerTextBox({width:90});
        $("#invoice_no").ligerTextBox({width:220});
        $("#ass_back_no").ligerTextBox({width:235});
		query();
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	  grid.options.parms.push({name:'ass_in_no',value:$("#ass_in_no").val()}); 
    	  grid.options.parms.push({name:'in_date_beg',value:$("#in_date_beg").val()}); 
    	  grid.options.parms.push({name:'in_date_end',value:$("#in_date_end").val()}); 
    	  grid.options.parms.push({name:'ven_id',value:'${ven_id}'}); 
    	  grid.options.parms.push({name:'store_id',value:'${store_id}'});
    	  grid.options.parms.push({name:'change_date_beg',value:$("#change_date_beg").val()}); 
    	  grid.options.parms.push({name:'change_date_end',value:$("#change_date_end").val()}); 
    	  grid.options.parms.push({name:'ass_card_no',value:liger.get("ass_card_no").getValue()}); 
    	  grid.options.parms.push({name:'change_no',value:liger.get("change_no").getValue() }); 
    	  grid.options.parms.push({name:'invoice_no',value:$("#invoice_no").val() }); 

    	  
    	  if($("#is_dis").prop("checked") == false){
    		  grid.options.parms.push({name:'is_dis',value:'0' }); 
    	  }else{
    		  grid.options.parms.push({name:'is_dis',value:'1' }); 
    	  }
    	  if($("#is_back").prop("checked") == false){
    		  grid.options.parms.push({name:'is_back',value:'1' }); 
    	  }else{
    		  grid.options.parms.push({name:'is_back',value:'0' }); 
    	  }
    	  
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '卡片号', name: 'ass_card_no', align: 'left',
					 		},
					 { display: '卡片状态', name: 'state_name', align: 'left',
					 		},		
		             { display: '发票号', name: 'invoice_no', align: 'left',
						 	},
					 { display: '入库单号', name: 'ass_in_no', align: 'left',
							},	
					 { display: '入库日期', name: 'in_date', align: 'left',
							},		
					 { display: '资产名称', name: 'ass_name', align: 'left',
					 		},
					 { display: '付款金额', name: 'pay_plan_money', align: 'left',
								render: function(item)
					            {
					                    return formatNumber(item.pay_plan_money,'${ass_05005}',1);
					            }
					 		},		
                     { display: '付款期号', name: 'stage_no', align: 'left',
					 		},
                     { display: '摘要', name: 'stage_name', align: 'left',
					 		},
                     { display: '付款条件', name: 'pay_term_name', align: 'left',
					 		},
                     { display: '预计付款时间', name: 'pay_plan_date', align: 'right',
					 		},
                     { display: '已付金额', name: 'pay_money', align: 'left',
								render: function(item)
					            {
					                    return formatNumber(item.pay_money,'${ass_05005}',1);
					            }
					 		},
                     { display: '未付金额', name: 'unpay_money', align: 'left',
								render: function(item)
					            {
					                    return formatNumber(item.unpay_money,'${ass_05005}',1);
					            }
					 		},
                     { display: '供应商', name: 'ven_name', align: 'left',
					 		}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAssInAndAssChangeBack.do?isCheck=false',delayLoad :true,
                     width: '100%', height: '95%', checkbox: true,rownumbers:true,checkBoxDisplay : isCheckDisplay,
                     selectRowButtonOnly:true, onSelectRow  : updateBill_money ,onUnSelectRow : updateBill_money ,
                     //heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询（<u>E</u>）', id:'search', click: query,icon:'search' },
                     	{ line:true },
				    	{ text: '保存（<u>A</u>）', id:'save', click: save, icon:'add' },
				    	, {
							line : true
						}, {
							text : '关闭',
							id : 'close',
							click : this_close,
							icon : 'candle'
						} 
				    ]}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
   
    
    function isCheckDisplay(rowdata) {
		if (rowdata.note == "合计")
			return false;
		return true;
	}
    
    
    function updateBill_money(){
    	var ass_amount = 0 ;
        var pay_plan_money =0;
       
   	 var dataMain =  grid.getSelectedRows();
   	
   	 if(dataMain.length > 0){
   		 $(dataMain).each(function(){
   			pay_plan_money += this.pay_plan_money;
   			ass_amount++;
   		 })
   	 }
   	 $("#pay_plan_money").val(pay_plan_money);
   	 $("#ass_amount").val(ass_amount);
    }
    
    
    function save(){
    	var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var params = [];
			var invoice_nos = [];
			$.each(data,function(){
				params.push(this.ass_card_no+"@"+this.stage_no+"@"+this.stage_name+"@"+this.pay_money+"@"+this.naturs_code);
				invoice_nos.push(this.invoice_no);
			});
			
			var falg = true;
			var nary = invoice_nos.sort();
			for(var i = 0; i < invoice_nos.length; i++){
				if(nary[i+1] == null){
					break;
				}
				if(invoice_nos[i] != nary[i+1]){
					falg = false;
					break;
				}
			}
			
			if(!falg){
				$.ligerDialog.warn('本次所生成发票单,发票号必须一致');
				return;
			}
			
			$.post("importInAndChangeBack.do?isCheck=false",
					{
					 'invoice_no':'${invoice_no}',
					 'invoice_num':invoice_nos[0],
					 'ven_id':'${ven_id}',
					 'ven_no':'${ven_no}',
					 'store_id':'${store_id}',
					 'store_no':'${store_no}',
					 'store_code':'${store_code}',
					 'pact_code':'${pact_code}',
					 'bill_date':'${bill_date}',
					 'create_date':'${create_date}',
					 'note':'${note}',
					 'params':params.toString()
					 },
					 function(data){
						parent.parentFrameUse().query();
						parent.parentFrameUse().openUpdate(data.update_para);
						parent.this_close();
						this_close();
			},"json");
			
		}
    
    }
    	
    
    function this_close() {
		frameElement.dialog.close();
	}
    
	
   
    function loadDict(){
		
		//autodate("#in_date_beg","YYYY-mm-dd","month_first");

		//autodate("#in_date_end","YYYY-mm-dd","month_last");
		
         }  
 	  
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;" >入库日期：</td>
            <td align="left" class="l-table-edit-td" width="5%"><input name="in_date_beg" type="text" id="in_date_beg"  class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
            <td align="left" width="2%">至：</td>
            <td align="left" class="l-table-edit-td"><input name="in_date_end" type="text" id="in_date_end"  class="Wdate"
				onFocus="WdatePicker({minDate:'#F{$dp.$D(\'in_date_beg\');}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">入库单号：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_in_no" type="text" id="ass_in_no"   /></td>
            <td align="left"></td>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">供应商：</td>
            <td align="left" class="l-table-edit-td"><input name="ven_name" type="text" id="ven_name" value="${ven_name }"   disabled="disabled"/></td>
            <td align="left"></td>
            
        </tr> 
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">原值变动日期：</td>
            <td align="left" class="l-table-edit-td"><input name="change_date_beg" type="text" id="change_date_beg"  class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"  /></td>
            <td align="left">至：</td>
            <td align="left" class="l-table-edit-td"><input name="change_date_end" type="text" id="change_date_end"  class="Wdate"
				onFocus="WdatePicker({minDate:'#F{$dp.$D(\'change_date_beg\');}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">变动单号：</td>
            <td align="left" class="l-table-edit-td"><input  name="change_no" type="text" id="change_no"/></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">卡片号：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_card_no" type="text" id="ass_card_no"   /></td>
            <td align="left"></td>
        </tr>
        <tr>
        	 <td align="right" class="l-table-edit-td"  style="padding-left:20px;">数量：</td>
             <td align="left" class="l-table-edit-td" colspan="3"><input name="ass_amount" type="text" id="ass_amount"    disabled="disabled"/></td>
             <td align="right" class="l-table-edit-td"  style="padding-left:20px;">金额：</td>
             <td align="left" class="l-table-edit-td"><input name="pay_plan_money" type="text" id="pay_plan_money"    disabled="disabled" /></td>
        	 <td align="left"></td>
        	 <td align="right" class="l-table-edit-td"  style="padding-left:20px;">发票号：</td>
             <td align="left" class="l-table-edit-td"><input name="invoice_no" type="text" id="invoice_no" /></td>
        </tr> 
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">退货单号：</td>
            <td align="left" class="l-table-edit-td" colspan="3"><input name="ass_back_no" type="text" id="ass_back_no" /></td>
            <td align="right"  class="l-table-edit-td" style="padding-left: 20px;">仓库：</td>
			<td align="left"  class="l-table-edit-td"><input name="store_name" disabled="disabled" value="${store_name }"
				type="text" id="store_name" 
				 /></td>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><input name="is_dis" type="checkbox" id="is_dis" /></td>
            <td align="left" class="l-table-edit-td" >显示已处置资产</td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><input name="is_back" type="checkbox" id="is_back" /></td>
            <td align="left" class="l-table-edit-td" >只显示退货资产</td>
        </tr>
    </table>

	<div id="maingrid"></div>
	
</body>
</html>
