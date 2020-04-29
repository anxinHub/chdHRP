<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="${path}/resource.jsp">
	<jsp:param value="hr,tree,grid,select,dialog,validate,tab,datepicker,upload,checkbox" name="plugins" />
</jsp:include>
<script src="<%=path%>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<!-- script src="<%=path%>/lib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script-->

<script type="text/javascript">
var sup_no,cus_id,palnGrid,payGrid;
var sourceSoues = [];   
	var save = function() {
		formValidate = $.etValidate({
			items : [ 
				{el : $("#pay_money"),required : true,type: 'number'},
				{el : $("#pay_code"),required : true},
				{el : $("#pact_code"),required : true},
				{el : $("#sup_no"),required : true},
				{el : $("#pay_date"),required : true},
				{el : $("#bill_code"),required : true},
				]
		});
		if(!formValidate.test()){
			return;
		};
		 var param = [];
		 var data = palnGrid.selectGet();
		 var money1 = 0;
		 $(data).each(function () {
             var rowdata = this.rowData;
       		 var obj = new Object();
       		 obj.pay_code = '${entity.pay_code }';
       		 obj.pact_code =pact_code.getValue();
       		 obj.group_id = ${group_id };
       		 obj.hos_id = ${hos_id };
       		 obj.copy_code = '${copy_code}';
       		 obj.plan_det_id = rowdata.plan_detail_id;
       		 obj.pay_money = rowdata.pay_money;
       		 money1+=(parseFloat(rowdata.pay_money)+parseFloat(rowdata.ing_money));
             param.push(obj);
         });
		 
		 var pay = [];
		 var error ="";
		 var money2 = 0;
		 $(payGrid.getAllData()).each(function (i,v) {
             var rowdata = this;
             $(payGrid.getAllData()).each(function (j,m) {
            	 if(j>=i+1)
       				{
       					if(v.source_name == m.source_name)
       					{
       						error+='[付款详情第'+ (i+1) + '行与第'+(j+1)+'行资金来源重复]  ';
       					}
       				}
             });
             if(!rowdata.source_name){error = "[付款详情第"+ (i+1) + "行资金来源不能为空]  ";return ;}
             if(!rowdata.pay_way){error = "[付款详情第"+ (i+1) + "行支付方式不能为空]  ";return ;}
             var obj = new Object();
       		 obj.pay_code = '${entity.pay_code }';
       		 obj.pact_code =pact_code.getValue();
       		 obj.group_id = ${group_id };
       		 obj.hos_id = ${hos_id };
       		 obj.copy_code = '${copy_code}';
       		 obj.pay_money = rowdata.pay_money;
       		 obj.source_id = rowdata.source_id;
       		 obj.pay_way = rowdata.pay_way;
       		 obj.cheq_no = rowdata.cheq_no;
       		 obj.note = rowdata.note;
       		 money2+=parseFloat(rowdata.pay_money);
       		 pay.push(obj);
         });
		 if(money1 != $("#pay_money").val()){
			 error+="[付款计划总金额与合同付款金额不一致]  "
		 }
		 if(money2 != $("#pay_money").val()){
			 error+="[付款详情总金额与合同付款金额不一致]  "
		 }
		 if(error){
			 $.etDialog.error(error);
			 return ;
		 }
		 
		ajaxPostData({
			url : 'updatePactPayMainFKHT.do?isCheck=false',
			data : {
				pay_date: pay_date.getValue(),
				pay_money: $("#pay_money").val(),
				sup_no : sup_no.getValue().split("@")[1],
				cus_id :cus_id,
				pact_code : pact_code.getValue(),
				note : $("#note").val(),
				bill_code : $("#bill_code").val(),
				is_init : ${is_init},
				grid : JSON.stringify(param),
				pay : JSON.stringify(pay)
			},
			success : function(data) {
		        var parentFrameName = parent.$.etDialog.parentFrameName;
				var parentWindow = parent.window[parentFrameName];
				parentWindow.query(); 
				var curIndex = parent.$.etDialog.getFrameIndex(window.name);
		        parent.$.etDialog.close(curIndex); 
			},
			delayCallback : true
		});
    }

    var initSelect=  function(){
    	sup_no = $("#sup_no").etSelect({
    		url: '../../../basicset/select/queryHosSupSelectDict.do?isCheck=false',
    		 defaultValue: '${entity.sup_id}'+'@'+'${entity.sup_no}' != "@"? '${entity.sup_id}'+'@'+'${entity.sup_no}':"none",
    		onChange:function(value){
    			pact_code.clearItem();
    			pact_code.reload({
    				url: '../../../basicset/select/queryPactFKHTSelect.do?isCheck=false&state_code=12&is_init=${is_init}&sup_no='+value.split("@")[1],
    			})
         	}	 			
    	});
    	ajaxPostData({
      		url: '../../../basicset/select/queryPactFKHTSelect.do?isCheck=false&state_code=12&is_init=${is_init}',
			  success: function (result) {
				  pact_code = $("#pact_code").etSelect({
					 defaultValue: '${entity.pact_code}',
					 options:result ,
					 onChange:function(value, $item){
						 if(value){
							 ajaxPostData({
	                		 	url: 'queryPactFKHTForPayMent.do?isCheck=false',
	                		 	data:{pact_code:value},
	                			success: function (result) {
	                				pay_date = $("#pay_date").etDatepicker({
	                					defaultDate: result.start_date,
	                					maxDate:result.start_date,
	                					todayButton: false
	                				});
									$("#money").val(result.deposit_money);
	                			},
	                			error :function(){
	                				$("#money").val("");
	                				pact_code.setValue(0);
	                			}
		                	});
						 }else{
							 $("#money").val("");
             				pact_code.setValue(0);
             				pay_date.setValue();
						 }
						 deletePay();
						params = [{ name: 'pact_code', value: value}];
		            	palnGrid.loadData(params,'queryPactPlanFKHT.do?isCheck=false');
			      	 }  
				 });
			  },
		});
	 	ajaxPostData({
		 	url: '../../../basicset/select/queryHosSourceDictSelect.do?isCheck=false',
			success: function (result) {sourceSoues = result; },
		});
      }
    
    var initPactPlanGrid = function () {
        var columns = [
        	 { display: '付款期号', name: 'plan_detail_id',hidden:true,editable: false},
        	 { display: '付款期号', name: 'pay_id',width: '100px',editable: false},
             { display: '摘要', name: 'summary', width: '150px',editable: false},
             { display: '付款期限', name: 'pay_date', width: '100px',editable: false},
             /* { display: '资金来源', name: 'source_name', width: '100px',editable: false}, */
             { display: '付款条件', name: 'pay_cond_name', width: '100px',editable: false},
             { display: '计划金额', name: 'plan_money', align: 'right', width: '100px',editable: false},
             { display: '已付金额', name: 'paied_money', align: 'right', width: '100px',editable: false},
             { display: '未付金额', name: 'unPaied_money', align: 'right', width: '100px',editable: false},
             { display: '付款中', name: 'ing_money', align: 'right', width: '100px',editable: false},
             { display: '付款金额', name: 'pay_money', align: 'right', width: '100px'}
        ];
        var paramObj = {
        	editable: true,
        	height: '95%',inWindowHeight : true,
        	width:'100%',
        	checkbox: true,
        	usePager: false,
          	columns: columns,
          	dataModel:{
          		url:'queryPactPlanFKHT.do?isCheck=false&pact_code=${entity.pact_code}&pay_code=${entity.pay_code}',
          	},
          	summary :{
          		  totalColumns: ['plan_money', 'paied_money','unPaied_money','ing_money','pay_money'],
          		  keyWordCol: 'pay_id',
          	},
        	load :function(){
        		ajaxPostData({
       			 	url: 'queryPactPayPlanFKHT.do?isCheck=false&pact_code=${entity.pact_code}&pay_code=${entity.pay_code}',
       				success: function (result) {
       					var pay = palnGrid.getAllData();
       			    	$(pay).each(function () {
       			    		for (var i = 0; i < result.Rows.length; i++) {
       			    			var detail_id = result.Rows[i].plan_det_id;
       			    			if(this.plan_detail_id == detail_id){
		       			    		palnGrid.setSelection(this._rowIndx,true,false);
       			    			}
							}
       			   		 })
       				}
        		});
        	},
        	selectChange:function(){$("#pay_money").val(sum());},
        	change:function(){$("#pay_money").val(sum());}
        };
       palnGrid = $("#pactplan").etGrid(paramObj);
    };
    
    var sum = function(){
    	var date = palnGrid.selectGet();
		var sum = 0;
		for (var i = 0; i < date.length; i++) {
			sum += parseFloat(date[i].rowData.ing_money);
			sum += parseFloat(date[i].rowData.pay_money);
		}
		return sum.toFixed(2);
    }
    
    var initPayDetailGrid = function () {
        var columns = [
             { display: '资金来源', name: 'source_name', width: '150px',editable:true,
           	  editor: {
         		     type: 'select', 
         		     keyField: 'source_id',
         		     autoFocus:true,
         		     source:sourceSoues,
         		     url: '',
         		 }	  
             },
             { display: '支付方式', name: 'pay_name', width: '150px',
            	  	editor: {
           		     type: 'select',
           		  	 keyField: 'pay_way',
           		  	 autoFocus:true, 
           		     url: '',
           		 }	 
              },
             { display: '付款金额', name: 'pay_money', align: 'right', width: '150px',editable:true,},
             { display: '支票号码', name: 'cheq_no', align: 'right', width: '200px'},
             { display: '说明', name: 'note', align: 'right', width: '200px'}
        ];
        var paramObj = {
        	editable: true,
        	height: '88%',inWindowHeight : true,
        	width:'100%',
        	checkbox: true,
        	usePager: false,
          	columns: columns,
          	cellClick: cellClick,
          	editorEnd: editorEnd,
          	addRowByKey:false,
          	dataModel: {
                url: 'queryPactPayDetFKHT.do?isCheck=false&pact_code=${entity.pact_code}&pay_code=${entity.pay_code}'
            },
            toolbar: { 
                items: [
                    { type: 'button', label: '增加', listeners: [{ click: addPay }], icon: 'add' },
                    /* { type: 'button', label: '保存', listeners: [{ click: saveSourceData }], icon: 'save' }, */
                    { type: 'button', label: '删除', listeners: [{ click: removePayDetail }], icon: 'dalete' },
                ]
            }, 
          	summary :{
        		  totalColumns: ['pay_money'],
        		  keyWordCol: 'source_name',
        	}
        };
       payGrid = $("#payDetail").etGrid(paramObj);
    };
    
    var editorEnd= function(event, ui){
    	if(ui.dataIndx=="source_name"){
    		var obj = new Object();
	    	obj.pay_name = "";
	    	obj.pay_way = "";
	    	obj.pay_money = ui.rowData.pay_money;
	    	obj.cheq_no = ui.rowData.cheq_no;
	    	obj.note = ui.rowData.note;
	    	payGrid.updateRow(ui.rowData._rowIndx,obj); 
    	}
    }
    //增加行
    var addPay = function(){
    	payGrid.addRow();
    }
    //付款详情表格 删除行
    function removePayDetail(){
   	 	var data = payGrid.selectGet();
	 	$(data).each(function(){
			payGrid.deleteRow(this.rowIndx)
	 	})
    }
    var cellClick = function(event,ui){
    	if(ui.dataIndx=="source_name"){
    		payGrid.getColumns()[1].editor.url ='../../../basicset/select/queryHosSourceDictSelect.do?isCheck=false';
    	}
    	if(ui.dataIndx=="pay_name"){
    		payGrid.getColumns()[2].editor.url ='../../../basicset/select/queryPayTypeDictBySourceSelect.do?isCheck=false&source_id='+ui.rowData.source_id;
    	}
    	return true;
    }
    
    var deletePay = function (){
   	 if (payGrid) {
			 var data = payGrid.getAllData();
	         if (data.length != 0) {
	        	 var data = payGrid.getAllData();
	        	 $(data).each(function(){
	        		 payGrid.deleteRow(this._rowIndx)
	        	 })
	         }
		}
   }
    
    //日期
	var initfrom = function(){
		pay_date = $("#pay_date").etDatepicker({
			defaultDate: "${pay_date}",todayButton: false
		});
	}
    
	$(function(){
    	initSelect();
   		initfrom();
   		
		$("#save").on("click", function () {
			save();
		})
		$("#cancle").on("click", function () {
			var curIndex = parent.$.etDialog.getFrameIndex(window.name);
	        parent.$.etDialog.close(curIndex); 
		})
		
		etTab = $("#etTab").etTab({
   			onChange: function(item){
   				if(item.tabid == '1'){
   					if(!payGrid){
						initPayDetailGrid();
   					}
					 var data = palnGrid.selectGet();
					 var res = data;
			         if (data.length == 0) {
			         } else {
			        	 $(data).each(function () {
				             var rowdata = this;
			    			 $(payGrid.getAllData()).each(function () {
			    	             if(rowdata.rowData.plan_detail_id == this.detail_id || rowdata.rowData.plan_detail_id == this.detail_id){
			    	            	res.splice(res.indexOf(rowdata), 1);
			    	             }
			    			 });
			        	 })
			        	  $(data).each(function () {
					        payGrid.addRow(this.rowData,true);
			        	  })
			         }    
  				}else{
  					palnGrid.refreshView();
  				}
   			}
   		});
		initPactPlanGrid();
	});
</script>
</head>

<body style="overflow: scroll; ">
	<div>
		<table class="table-layout">
			<tr>
				<td class="label no-empty" style="width: 100px;">付款单号：</td>
				<td class="ipt"><input id="pay_code" type="text" disabled="disabled" value="${entity.pay_code }"/></td>
				<td class="label no-empty" style="width: 100px;">供应商：</td>
				<td class="ipt"><select id="sup_no" style="width: 180px;" disabled="disabled"></select> </td>
				<td class="label no-empty" style="width: 100px;">合同名称：</td>
				<td class="ipt"><select id="pact_code" style="width: 180px;" disabled="disabled"></select> </td>
			</tr>
			<tr>
				<td class="label no-empty" style="width: 100px;">付款日期：</td>
				<td class="ipt"><input id="pay_date" style="width: 180px;" value="${entity.pay_date }"/></td>
				<td class="label no-empty" style="width: 100px;">发票号码：</td>
				<td class="ipt"><input id="bill_code" type="text" value="${entity.bill_code }"/></td>
				<td class="label" style="width: 100px;">付款金额：</td>
				<td class="ipt"><input id="pay_money" type="text" disabled="disabled" value="${entity.pay_money }"/></td>
			</tr>
			<tr>
				<td class="label" style="width: 100px;">备注：</td>
				<td class="ipt" colspan="5"><input id="note" type="text" style="width: 836px" value="${entity.note }"/></td>
			</tr>
		</table>
	</div>
	<div class="button-group">
	 <!--  <button id="save">保存</button> <button id="cancle">取消</button>-->
	  
	</div>
	
	<div id="etTab">
		  <div title="付款计划" tabid='0'>
			 <div id="pactplan"></div>
		  </div>
		  <div title="付款详情" tabid="1">
			 <div id="payDetail"></div>
			 <div class="button-group">
			  <button id="save">保存</button>
			</div>
		  </div>
	</div>
</body>
</html>

