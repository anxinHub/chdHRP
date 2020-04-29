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
var cus_no,cus_id,palnGrid,payGrid;
var sourceSoues = [];   
	var save = function() {
		if("${entity.state}" != '01'){
			return;
		}
		formValidate = $.etValidate({
			items : [ 
				{el : $("#rec_money"),required : true,type: 'number'},
				{el : $("#rec_code"),required : true},
				{el : $("#pact_code"),required : true},
				{el : $("#cus_no"),required : true},
				{el : $("#rec_date"),required : true},
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
       		 obj.pact_code = '${entity.pact_code }';
       		 obj.group_id = ${group_id };
       		 obj.hos_id = ${hos_id };
       		 obj.copy_code = '${copy_code}';
       		 obj.rec_code = '${entity.rec_code }',
       		 obj.plan_detail_id = rowdata.plan_detail_id;
       		 obj.rec_money = rowdata.rec_money == 0?rowdata.ing_money: rowdata.rec_money;
       		 money1+=(parseFloat(rowdata.rec_money)+parseFloat(rowdata.ing_money));
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
       					if(v.pay_way_name == m.pay_way_name)
       					{
       						error+='[收款详情第'+ (i+1) + '行与第'+(j+1)+'行支付方式重复]  ';
       					}
       				}
             });
             if(!rowdata.pay_way){error = "[收款详情第"+ (i+1) + "行支付方式不能为空]  ";return ;}
             var obj = new Object();
       		 obj.pact_code = '${entity.pact_code }';
       		 obj.group_id = ${group_id };
       		 obj.hos_id = ${hos_id };
       		 obj.copy_code = '${copy_code}';
       		 obj.rec_code = '${entity.rec_code }';
       		 obj.detail_id = rowdata.detail_id;
       		 obj.rec_money = rowdata.rec_money;
       		 obj.source_id = rowdata.source_id;
       		 obj.pay_way = rowdata.pay_way;
       		 obj.cheq_no = rowdata.cheq_no;
       		 obj.note = rowdata.note;
       		 money2+=parseFloat(rowdata.rec_money);
       		 pay.push(obj);
         });
		 if(money1 != $("#rec_money").val()){
			 error+="[收款计划总金额与合同收款金额不一致]  "
		 }
		 if(money2 != $("#rec_money").val()){
			 error+="[收款详情总金额与合同收款金额不一致]  "
		 }
		 if(error){
			 $.etDialog.error(error);
			 return ;
		 }
		 
		ajaxPostData({
			url : 'updatePactRecMainSKHT.do?isCheck=false',
			data : {
				rec_code : '${entity.rec_code }',
				rec_date: rec_date.getValue(),
				rec_money: $("#rec_money").val(),
				cus_no : '${entity.cus_no }',
				cus_id :cus_id,
				pact_code : '${entity.pact_code }',
				note : $("#note").val(),
				bill_code : $("#bill_code").val(),
				emp_id : emp_id.getValue(),
				is_init : ${is_init},
				grid : JSON.stringify(param),
				pay : JSON.stringify(pay)
			},
			success : function(data) {
				var curIndex = parent.$.etDialog.getFrameIndex(window.name);
		        parent.$.etDialog.close(curIndex); 
		        var parentFrameName = parent.$.etDialog.parentFrameName;
				var parentWindow = parent.window[parentFrameName];
				parentWindow.query(); 
			},
			delayCallback : true
		});
    }

    var initSelect=  function(){
    	ajaxPostData({
      		url: '../../../basicset/select/queryHosCusDictSelect.do?isCheck=false',
			  success: function (result) {
				  cus_no = $("#cus_no").etSelect({
					 defaultValue: '${entity.cus_no }',
					 options:result ,
					 onChange:function(value, $item){
						 for(var i = 0;i<result.length;i++){
							 var obj = result[i];
							 if(value == obj.id){
								 cus_id = obj.cus_id;
							 }
						 }
						pact_code.setValue(0);
				    	pact_code.reload({url: '../../../basicset/select/queryPactSKHTSelect.do?isCheck=false&state_code=12&is_init=${is_init}&cus_no='+value,defaultValue: "none"})
			      	 }  
				 });
			  },
		});
    	ajaxPostData({
      		url: '../../../basicset/select/queryPactSKHTSelect.do?isCheck=false&state_code=12&is_init=${is_init}',
			  success: function (result) {
				  pact_code = $("#pact_code").etSelect({
					 defaultValue: '${entity.pact_code }',
					 options:result ,
					 onChange:function(value, $item){
						 if(value){
							 ajaxPostData({
	                		 	url: 'queryPactSKHTForRecMent.do?isCheck=false',
	                		 	data:{pact_code:value},
	                			success: function (result) {
	                				rec_date = $("#rec_date").etDatepicker({
	                					defaultDate: result.start_date,
	                					maxDate:result.start_date,todayButton: false
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
             				rec_date.setValue();
						 }
						 deletePay();
						params = [{ name: 'pact_code', value: value}];
		            	palnGrid.loadData(params,'queryPactPlanSKHTForEdit.do?isCheck=false');
			      	 }  
				 });
			  },
		});
	 	ajaxPostData({
		 	url: '../../../basicset/select/queryHosSourceDictSelect.do?isCheck=false',
			success: function (result) {sourceSoues = result; },
		});
	 	emp_id = $("#emp_id").etSelect({url: '../../../basicset/select/queryHosEmpDictSelect.do?isCheck=false',defaultValue: "${entity.emp_id}"});
      }
    
    var initPactPlanGrid = function () {
        var columns = [
        	 { display: '收款期号', name: 'rec_id',width: '100px',editable: false},
            { display: '摘要', name: 'summary', width: '150px',editable: false},
             { display: '收款期限', name: 'rec_date', width: '100px',editable: false},
            // { display: '资金来源', name: 'source_name', width: '100px',editable: false},
             { display: '收款条件', name: 'rec_cond_name', width: '100px',editable: false},
             { display: '计划金额', name: 'plan_money', align: 'right', width: '100px',editable: false},
             { display: '已收金额', name: 'paied_money', align: 'right', width: '100px',editable: false},
             { display: '未收金额', name: 'unPaied_money', align: 'right', width: '100px',editable: false},
             { display: '收款中', name: 'ing_money', align: 'right', width: '100px',editable: false},
             { display: '收款金额', name: 'rec_money', align: 'right', width: '100px'}
        ];
        var paramObj = {
        	editable: true,
        	height: '95%',inWindowHeight : true,
        	width:'100%',
        	checkbox: true,
        	usePager: false,
          	columns: columns,
          	dataModel: {url: 'queryPactPlanSKHTForEdit.do?isCheck=false&pact_code=${entity.pact_code}&rec_code=${entity.rec_code}'},
          	summary :{
          		  totalColumns: ['plan_money', 'paied_money','unPaied_money','ing_money','rec_money'],
          		  keyWordCol: 'rec_id',
          	},
        	load :function(){
        		 ajaxPostData({
       			 	url: 'queryPactRecPlanSKHT.do?isCheck=false&pact_code=${entity.pact_code}&rec_code=${entity.rec_code}',
       				success: function (result) {
       					var pay = palnGrid.getAllData();
       			    	$(pay).each(function () {
       			    		for (var i = 0; i < result.Rows.length; i++) {
       			    			var detail_id = result.Rows[i].plan_detail_id;
       			    			if(this.plan_detail_id == detail_id){
		       			    		palnGrid.setSelection(this._rowIndx,true,false);
       			    			}
							}
       			   		 })
       				}
        		});
        	},
        	selectChange:function(){
        		if("${entity.state}" == '01'){
	        		$("#rec_money").val(sum());
        		}
        	},
        	change:function(){
        		if("${entity.state}" == '01'){
        		$("#rec_money").val(sum());}
        	}
        };
       palnGrid = $("#pactplan").etGrid(paramObj);
    };
    
    var sum = function(){
    	var date = palnGrid.selectGet();
		var sum = 0;
		for (var i = 0; i < date.length; i++) {
			sum += parseFloat(date[i].rowData.ing_money);
			sum += parseFloat(date[i].rowData.rec_money);
		}
		return sum.toFixed(2);
    }
    
    var initPayDetailGrid = function () {
        var columns = [
             /* { display: '资金来源', name: 'source_name', width: '150px',editable:false,
           	  editor: {
         		     type: 'select', 
         		     keyField: 'source_id',
         		     autoFocus:true,
         		     source:sourceSoues,
         		 }	  
             }, */
             { display: '支付方式', name: 'pay_way_name', width: '150px',
            	  	editor: {
           		     type: 'select',
           		  	 keyField: 'pay_way',
           		  	 autoFocus:true, 
           		     url: '',
           		 }	 
              },
             { display: '收款金额', name: 'rec_money', align: 'right', width: '150px',editable:true,},
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
          	/* load : load, */
          	addRowByKey:false,
          	dataModel: {
	             url: 'queryPactRecDetSKHT.do?isCheck=false&pact_code=${entity.pact_code}&rec_code=${entity.rec_code}'
            },
            toolbar: { 
                items: [
                    { type: 'button', label: '增加', listeners: [{ click: addPay }], icon: 'add' },
                    /* { type: 'button', label: '保存', listeners: [{ click: saveSourceData }], icon: 'save' }, */
                    { type: 'button', label: '删除', listeners: [{ click: removePayDetail }], icon: 'dalete' },
                ]
            }, 
          	summary :{
        		  totalColumns: ['rec_money'],
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
	    	obj.rec_money = ui.rowData.rec_money;
	    	obj.cheq_no = ui.rowData.cheq_no;
	    	obj.note = ui.rowData.note;
	    	payGrid.updateRow(ui.rowData._rowIndx,obj); 
    	}
    }
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
    	/* if(ui.dataIndx=="source_name"){
    		payGrid.getColumns()[1].editor.url ='../../../basicset/select/queryHosSourceDictSelect.do?isCheck=false';
    	} */
    	if(ui.dataIndx=="pay_way_name"){
    		payGrid.getColumns()[1].editor.url ='../../../basicset/select/queryPayTypeDictSelect.do?isCheck=false';//queryPayTypeDictBySourceSelect.do?isCheck=false&source_id='+ui.rowData.source_id;
    	}
    	return true;
    }
    
    /* var load = function(){
    	 var data = palnGrid.selectGet();
		 var res = [];
         if (data.length != 0) {
        	 $(data).each(function () {
	             var rowdata = this;
    			 $(payGrid.getAllData()).each(function () {
    	             if(rowdata.rowData.plan_detail_id == this.detail_id || rowdata.rowData.plan_detail_id == this.plan_detail_id){
    	            	res.push(this);
    	             }
    			 });
        	 })
        	 $(payGrid.getAllData()).each(function () {
        		 payGrid.deleteRow(this._rowIndx);
    		 });
        	  $(res).each(function () {
		         payGrid.addRow(this,true);
        	  })
         }  
    } */
    
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
		rec_date = $("#rec_date").etDatepicker({
			defaultDate: "${rec_date}",todayButton: false
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
					 /* var data = palnGrid.selectGet();
					 var res = data;
			         if (data.length == 0) {
			         } else {
			        	 $(data).each(function () {
				             var rowdata = this;
			    			 $(payGrid.getAllData()).each(function () {
			    	             if(rowdata.rowData.plan_detail_id == this.detail_id || rowdata.rowData.plan_detail_id == this.plan_detail_id){
			    	            	res.splice(res.indexOf(rowdata), 1);
			    	             }
			    			 });
			        	 })
			        	  $(res).each(function () {
					        payGrid.addRow(this.rowData,true);
			        	  })
			         }    
			         load(); */
  				}else{
  					palnGrid.refreshView();
  				}
   			}
   		});
		initPactPlanGrid();
		
		
		if("${entity.state}" != '01'){
   			$("#rec_date").attr("disabled" , "disabled");
   			emp_id.disabled();
   			$("#bill_code").attr("disabled" , "disabled");
   			$("#bill_code").attr("style","background-color:#EAEAEA");
   			$("#note").attr("disabled" , "disabled");
   			$("#note").attr("style","background-color:#EAEAEA");
   		}
	});
</script>
</head>

<body style="overflow: scroll; ">
	<div>
		<table class="table-layout">
			<tr>
				<td class="label no-empty" style="width: 100px;">收款单号：</td>
				<td class="ipt"><input id="rec_code" type="text" disabled="disabled" value="${entity.rec_code }" style="background-color: #eaeaea"/></td>
				<td class="label no-empty" style="width: 100px;">客户：</td>
				<td class="ipt"><select id="cus_no" style="width: 180px;" disabled="disabled"></select> </td>
				<td class="label no-empty" style="width: 100px;">合同名称：</td>
				<td class="ipt"><select id="pact_code" style="width: 180px;" disabled="disabled"></select> </td>
			</tr>
			<tr>
				<td class="label no-empty" style="width: 100px;">收款日期：</td>
				<td class="ipt"><input id="rec_date" style="width: 180px;"/></td>
				<td class="label no-empty" style="width: 100px;">发票号码：</td>
				<td class="ipt"><input id="bill_code" type="text" value="${entity.bill_code }"/></td>
				<td class="label" style="width: 100px;">收款金额：</td>
				<td class="ipt"><input id="rec_money" type="text" disabled="disabled" value="${entity.rec_money }" style="background-color: #eaeaea"/></td>
			</tr>
			<tr>
				<td class="label no-empty" style="width: 100px;">业务员：</td>
				<td class="ipt"><select id="emp_id" style="width: 180px;"></select> </td>
				<td class="label" style="width: 100px;">备注：</td>
				<td class="ipt" colspan="3"><input id="note" type="text" style="width: 508px" value="${entity.note }"/></td>
			</tr>
		</table>
	</div>
	<div class="button-group">
	 <!--  <button id="save">保存</button> <button id="cancle">取消</button>-->
	  
	</div>
	
	<div id="etTab">
		  <div title="收款计划" tabid='0'>
			 <div id="pactplan"></div>
		  </div>
		  <div title="收款详情" tabid="1">
			 <div id="payDetail"></div>
			 <div class="button-group">
			  <button id="save">保存</button>
			</div>
		  </div>
	</div>
</body>
</html>

