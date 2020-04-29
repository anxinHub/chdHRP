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
var etTab,subGrid,docGrid,palnGrid,deposit_type,dept_no,emp_id,cus_no,pact_type_code,subject_type;
var deptSoues = [];   
var sourceSoues = [];   
var empSoues = [];    
var typeSoues = [];    
var stateSoues = [];    
var paycondSoues = [];    
var is_bid ;
var is_deposit;
var cus_id;
	var save = function() {
		if($("#pact_code").val() != ""){
			return;
		}
		
		var value = deposit_type.getValue();
		if(value == '01' || value == '02'){
			formValidate = $.etValidate({
				items : [ 
					{el : $("#pact_type_code"), required : true}, 
					{el : $("#state_code"),required : true},
					{el : $("#pact_name"),required : true},
					{el : $("#sign_date"),required : true},
					{el : $("#dept_no"),required : true},
					{el : $("#emp_id"),required : true},
					{el : $("#cus_no"),required : true},
					{el : $("#opp_emp"),required : true},
					{el : $("#pact_money"),required : true},
					{el : $("#start_date"),required : true},
					{el : $("#end_date"),required : true},
					{el : $("#deposit_money"),required : true}
					]
			});
		}else{
			formValidate = $.etValidate({
				items : [ 
					{el : $("#pact_type_code"), required : true}, 
					{el : $("#state_code"),required : true},
					{el : $("#pact_name"),required : true},
					{el : $("#sign_date"),required : true},
					{el : $("#dept_no"),required : true},
					{el : $("#emp_id"),required : true},
					{el : $("#cus_no"),required : true},
					{el : $("#opp_emp"),required : true},
					{el : $("#pact_money"),required : true},
					{el : $("#start_date"),required : true},
					{el : $("#end_date"),required : true}
					]
			});
		}
		if(!formValidate.test()){return;};
		
	  var sub = [];
		if(subGrid){
			var err = "";
	 		var data = subGrid.getAllData();
	 		  if (data != null && data.length != 0) {
	        	var money = 0;
	             $(data).each(function () {
	                 var rowdata = this;
	                 if(!rowdata.subject_id){err = "标的物不能为空";return ;}
	                 if(!rowdata.amount){err = "数量不能为空";return ;}
	                 if(!rowdata.price){err = "单价不能为空";return ;}
	                 if(!rowdata.money){err = "金额不能为空";return ;}
	                 if(!rowdata.dept_no){err = "服务科室不能为空";return ;}
	                 rowdata.group_id = ${group_id};
	                 rowdata.hos_id = ${hos_id};
	                 rowdata.copy_code = '${copy_code}';
	                 rowdata.pact_code =  $("#pact_code").val();
	                 rowdata.dept_id =  rowdata.dept_no.split('@')[0];
	                 rowdata.dept_no =  rowdata.dept_no.split('@')[1];
	                 rowdata.subject_type =  '05';
	                 rowdata.subject_no =  rowdata.subject_id;
	                 rowdata.proj_no =  rowdata.proj_id;
	                 money  += parseFloat(rowdata.money);
	                 sub.push(rowdata);
	             });
	             if(err.length != 0){$.etDialog.error(err) ;return ;}
	 		}
	 		 else
	         {
	        	 $.etDialog.error("标的物不能为空！") ;return ;
	         }
		}
		
		var plan = [];
		if(palnGrid){
			var err = "";
	 		var data = palnGrid.getAllData();
	 		if(data != null && data.length != 0){
		 		var money = 0;
		 		 $(data).each(function () {
		              var rowdata = this;
		              if(!rowdata.rec_id){err = "收款期号不能为空";return ;}
		              //if(!rowdata.summary){err = "摘要不能为空";return ;}
		              if(!rowdata.rec_date){err = "收款期限不能为空";return ;}
		              //if(!rowdata.source_name){err = "资金来源不能为空";return ;}
		              if(!rowdata.plan_money){err = "计划金额不能为空";return ;}
		              
		              rowdata.group_id = ${group_id};
		              rowdata.hos_id = ${hos_id};
		              rowdata.copy_code = '${copy_code}';
		              rowdata.pact_code = "${entity.pact_code }";
		              money  += parseFloat(rowdata.plan_money);
		              plan.push(rowdata);
		          });
		 		 
		 		if(err.length != 0){$.etDialog.error(err) ;return ;}
		  		if(parseFloat($("#pact_money").val().replace(/,/g,"")) != money){
	             	 $.etDialog.error("收款计划中总金额与合同金额不一致，请确定金额");
	             	 return;
	             }
	 		}
		}
		
		if(docGrid){
	  		var doc = [];
	  		var err = "";
			var data = docGrid.getAllData();
			if(data != null && data.length != 0){
				 $(data).each(function () {
		             var rowdata = this;
		             if(!rowdata.doc_type){err = "文档类别不能为空";return ;}
		             if(!rowdata.doc_name){err = "文档名称不能为空";return ;}
		             if(!rowdata.dept_no){err = "科室不能为空";return ;}
		             if(!rowdata.emp_id){err = "责任人不能为空";return ;}
		             if(!rowdata.location){err = "存放位置不能为空";return ;}
		             if(!rowdata.file){err = "文件不能为空";return ;}
		             rowdata.group_id = ${group_id};
		             rowdata.hos_id = ${hos_id};
		             rowdata.copy_code = '${copy_code}';
		             rowdata.pact_code = "${entity.pact_code }";
		             rowdata.dept_id =  rowdata.dept_no.split('@')[0];
	                 rowdata.dept_no =  rowdata.dept_no.split('@')[1];
		             doc.push(rowdata);
		         });
				 if(err.length != 0){$.etDialog.error(err) ;return ;}
			}
		}
		
		ajaxPostData({
			url : 'addPactMainSKHT.do?isCheck=false',
			data : {
				pact_type_code : $("#pact_type_code").val(),
				state_code : $("#state_code").val(),
				pact_name : $("#pact_name").val(),
				original_code : $("#original_code").val(),
				master_pact_code : $("#master_pact_code").val(),
				sign_date : $("#sign_date").val(),
				dept_id : $("#dept_no").val().split("@")[0],
				dept_no : $("#dept_no").val().split("@")[1],
				emp_id : $("#emp_id").val(),
				cus_no : $("#cus_no").val(),
				cus_id : cus_id,
				sup_id : $("#cus_no").val(),
				opp_emp : $("#opp_emp").val(),
				opp_phone : $("#opp_phone").val(),
				pact_intro : $("#pact_intro").val(),
				pact_money : $("#pact_money").val().replace(/,/g,""),
				start_date : $("#start_date").val(),
				end_date : $("#end_date").val(),
				is_bid : is_bid.status == "checked" ? 1 : 0,
				organ_type : $("#organ_type").val(),
				buy_type : $("#buy_type").val(),
				is_deposit : is_deposit.status == "checked" ? 1 : 0,
				deposit_type : $("#deposit_type").val(),
				deposit_money : $("#deposit_money").val().val().replace(/,/g,""),
				proj_id : $("#proj_id").val(),
				sub : JSON.stringify(sub),
				plan : JSON.stringify(plan),
				doc : JSON.stringify(doc),
				is_init : ${is_init},
				///增加以下
				delivery_term:$("#delivery_term").val(),
				server:$("#server").val(),
				ser_emp:$("#ser_emp").val(),
				ser_phone:$("#ser_phone").val(),
				cont_term1:$("#cont_term1").val(),
				cont_term2:$("#cont_term2").val(),
				cont_term3:$("#cont_term3").val(),
				cont_term4:$("#cont_term4").val(),
				cont_term5:$("#cont_term5").val(),
				cont_term6:$("#cont_term6").val(),
				note:$("#note").val()
			},
			success : function(data) {
				var curIndex = parent.$.etDialog.getFrameIndex(window.name);
		        var parentFrameName = parent.$.etDialog.parentFrameName;
		        var parentWindow = parent.window[parentFrameName];
			    parentWindow.query(); 
		        parent.$.etDialog.close(curIndex); 
			},
			delayCallback : true
		})
	}

    var initSelect=  function(){
    	if(${is_init} == 1){
    		$("#ht_sign_point").show();
			 $("#xy_sign_point").hide();
    	}
    	else{
    		$("#ht_sign_point").hide();
			 $("#xy_sign_point").show();
    	}
    	ajaxPostData({
      		url: '../../../basicset/select/queryPactTypeSKHTSelect.do?isCheck=false',
			  success: function (result) {
				 pact_type_code = $("#pact_type_code").etSelect({
					 defaultValue: 'none',
					 options:result ,
					 onItemAdd:function(value, $item){
						 var maxDate;
						 for(var i = 0;i<result.length;i++){
							 var obj = result[i];
							 if(value == obj.id){
								 subject_type = obj.subject_type;
								 var timestamp =  new Date(obj.start_date);
								 maxDate = timestamp.toLocaleDateString().replace(/\//g, "-")
							 }
						 }
						 if(${is_init} == 1){
							 signpicker = $("#sign_date").etDatepicker({defaultDate: maxDate,maxDate: maxDate,todayButton: false});
						 }else{
							 signpicker = $("#sign_date").etDatepicker({defaultDate: maxDate,minDate: maxDate,todayButton: false});
						 }
			      	 }  
				 });
			  },
		});
    	ajaxPostData({
      		url: '../../../basicset/select/queryHosCusDictSelect.do?isCheck=false',
			  success: function (result) {
				  cus_no = $("#cus_no").etSelect({
					 defaultValue: 'none',
					 options:result ,
					 onItemAdd:function(value, $item){
						 for(var i = 0;i<result.length;i++){
							 var obj = result[i];
							 if(value == obj.id){
								 cus_id = obj.cus_id;
							 }
						 }
			      	 }  
				 });
			  },
		});
      	ajaxPostData({
   		 url: '../../../basicset/select/queryPactStateSelect.do?isCheck=false',
			  success: function (result) {
				  if(${is_init} == 1){
					  for(var i = 0;i<result.length;i++){
						  var obj = result[i];
						  if(obj.id == 12){stateSoues.push(obj);}
					  }
				  }else{
					  for(var i = 0;i<result.length;i++){
						  var obj = result[i];
						  if(obj.id <= 11){stateSoues.push(obj);}
					  }
				  }
				state_code = $("#state_code").etSelect({options:stateSoues,defaultValue: 'none',});
			  },
		});
        deposit_type = $("#deposit_type").etSelect({
       	 	 url: '../../../basicset/select/queryDictSelect.do?isCheck=false&f_code=DEPOSIT_TYPE',
       	 	defaultValue: 'none',
       		 onChange:function(value){
       			if(value == '01' || value == '02'){
	      			$("#deposit_money").removeAttr("disabled");
	      			$("#deposit_money").removeAttr("style");
				 }else{
	      			 $("#deposit_money").attr("disabled","disabled");
	      			$("#deposit_money").attr("style","background-color:#EAEAEA");
					$("#deposit_money").val("");
				 }
      	 	 }  
         });
        organ_type = $("#organ_type").etSelect({ url: '../../../basicset/select/queryDictSelect.do?isCheck=false&f_code=ORGAN_TYPE',defaultValue: 'none'});
        buy_type = $("#buy_type").etSelect({ url: '../../../basicset/select/queryDictSelect.do?isCheck=false&f_code=BUY_TYPE', defaultValue: 'none',});
    	ajaxPostData({
    		 url: '../../../basicset/select/queryHosEmpDictSelect.do?isCheck=false',
			 success: function (result) {
				emp_id = $("#emp_id").etSelect({options:result,defaultValue: 'none',});
	            empSoues = result;
			 },
		});
    	ajaxPostData({
    		 url: '../../../basicset/select/queryDeptSelectDict.do?isCheck=false',
			 success: function (result) {
				 dept_no = $("#dept_no").etSelect({backEndSearch:false,options:result,defaultValue: 'none',});
				 deptSoues = result;
				 }
    		 });
	  master_pact_code = $("#master_pact_code").etSelect({
               	url:'../../../basicset/select/queryPactSKHTSelect.do?isCheck=false&is_init=${is_init}',
               	defaultValue: "none",
               	onChange:function(value){
               		if(!value){
               			signpicker.setValue(0);
						dept_no.setValue(0);
						emp_id.setValue(0);
						cus_no.setValue(0);
						proj_id.setValue(0);
						$("#opp_emp").val("");
						$("#opp_phone").val("");
               			return;
               		}
               		ajaxPostData({
              		 	url: 'queryPactMainSKHTForMaster.do?isCheck=false&is_init=${is_init}',
              		 	data:{
              		 		pact_code:value
              		 	},
              			success: function (result) {
							signpicker.setValue(result.sign_date);
							dept_no.setValue(result.dept_no);
							emp_id.setValue(result.emp_id);
							cus_no.setValue(result.cus_no);
							proj_id.setValue(result.proj_id);
							$("#opp_emp").val(result.opp_emp);
							$("#opp_phone").val(result.opp_phone);
              			},
              		});
               	}
            });
	  	proj_id = $("#proj_id").etSelect({ url: '../../../basicset/select/queryHosProjDictSelect.do?isCheck=false', defaultValue: 'none'});
      	ajaxPostData({url: '../../../basicset/select/queryHosSourceDictSelect.do?isCheck=false',success: function (result) {sourceSoues = result; }});
      	ajaxPostData({url: '../../../basicset/select/queryPactPayCondSelect.do?isCheck=false',success: function (result) {paycondSoues = result;}});
      	ajaxPostData({url: '../../../basicset/select/queryPactDocTypeSelect.do?isCheck=false' ,success: function (result) {typeSoues= result;}});
      }
    
     var selectId ;
     var without_id = "";
	 var initSubGrid = function () {
         var columns = [
                { display: '标的物名称', name: 'subject_name',width: '160px',
              	  editor: {
            		     type: 'select', 
            		     keyField: 'subject_id',
            		     url:'../../../basicset/select/querySubjectSelect.do?isCheck=false&without_id='+without_id+'&type=05',
	  	          		 change: function(ui){
	           		   		ajaxPostData({
	           		   			url: '../../../basicset/select/querySubjectSelect.do?isCheck=false&id='+ui.subject_id+'&type=05',
	           		   			success: function (result) {
	  	         		   			ui.item_spec = result[0].item_spec;
	  	      		   				ui.item_model = result[0].item_model;
	  	      		   				ui.fac_name = result[0].fac_name;
	  	      		   				ui.fac_id = result[0].fac_id;
	  	      		   				ui.fac_no = result[0].fac_no;
	  	      		   				ui.unit_code = result[0].unit_code;
	  	      		   				ui.unit_name = result[0].unit_name;
	  	      		   				//subGrid.refreshCell(ui._rowIndx, 'item_model', false);
	  	      		   				//subGrid.refreshCell(ui._rowIndx, 'item_spec', false);
	  	      		   				//subGrid.refreshCell(ui._rowIndx, 'fac_name', false);
	  	      		   				//subGrid.refreshCell(ui._rowIndx, 'unit_name', false);
	  	      		   				ui.subject_code=result[0].item_code;
	      		   					subGrid.refreshCell(ui._rowIndx, 'subject_code', false);
	           		   			}
	           		   		});
	           		   		without_id += ui.subject_id + ","
	  	         		  },
            		 },
                },
              { display: '标的物编码', name: 'subject_code',width: '120',editable: false},
              { display: '数量', name: 'amount', width: '120px',align: "right",editor: {type: 'number'}},
              { display: '单价', name: 'price', width: '120px',align: "right",editor: {type: 'textbox'},
             	 render:function(rowData){
	          		  return formatNumber(parseFloat(rowData.rowData.price),2,1)
	          	 	}
             }, 
              { display: '金额', name: 'money',width: '120px',align: "right", editor: {type: "textbox"},
             	 render:function(rowData){
	          		  return formatNumber(parseFloat(rowData.rowData.money),2,1)
	          	 	}
            },               
              { display: '交货日期', name: 'arrive_date',width: '120px', align: 'center',editor: {type: 'date',}},
              { display: '保修期(月)', name: 'keep_repair_month',width: '120px',},
              { display: '备注', name: 'note',width: '120px',},
              { display: '服务科室', name: 'dept_name',width: '120px',editor: {type: 'select',keyField: 'dept_no',source:deptSoues}},
         ];
         var paramObj = {
        	 editable: true,
             height: '200',
             width:'100%',
             checkbox: true,
             usePager: false,
             columns: columns,
             editorEnd :editorEnd,
             toolbar: {
                 items: [
                	 { type: 'button', label: '增加', listeners: [{ click: addSub }], icon: 'add' },
                     { type: 'button', label: '删除',  listeners: [{ click:  removeSub}],  icon: 'del' },
                     { type: 'button', label: '添加其他标的物',  listeners: [{ click: addElseSub }],  icon: 'save' }
                 ]
             }
         };
         
       	subGrid = $("#subGrid").etGrid(paramObj);
       	
     };
     
  // 用于动态生成标的物中的金额
     function editorEnd(event, ui){
    	 if(ui.colIndx == 2 || ui.colIndx == 3){
    		var data = ui.rowData;
    		if(!data.price){
    			 data.price = 0;
    		}else{
    			//var price = parseFloat(data.price);
    			//if (!price) {price = 0;}
    			//ui.rowData.price = parseFloat(price)
    		}
    		ui.rowData.money  = parseInt(data.amount)* parseFloat(data.price) ;
    		subGrid.refreshCell(ui.rowIndx, 'money', false);

    		addMoney();
    	 }
     }
  
     function addMoney(){
 		var money = 0;
  	 	var data = subGrid.getAllData();
		 if (data != null && data.length != 0) {
           $(data).each(function () {
          	 var rowdata = this;
          	 if (rowdata && rowdata.money) {
	                 money  += parseFloat(rowdata.money);
				}
           });
      }
		 $("#pact_money").val(formatNumber(parseFloat(money),2,1));
 	}
     
     function removeSub(){
    	 var data = subGrid.selectGet();
         if (data.length == 0) {
             $.etDialog.error('请选择行');
         } else {
             var param = [];
             $(data).each(function () {
            	 var rowdata = this.rowData;
                 rowdata.group_id = ${group_id};
                 rowdata.hos_id = ${hos_id};
                 rowdata.copy_code = '${copy_code}';
                 rowdata.pact_code =  $("#pact_code").val();
                 rowdata.detail_id =  rowdata._rowIndx +1;
                 rowdata.dept_id =  rowdata.dept_no;
                 rowdata.subject_no =  rowdata.subject_id;
                 rowdata.proj_no =  rowdata.proj_id;
                 param.push(rowdata);
             });
             $.etDialog.confirm('确定删除?', function () {
                 ajaxPostData({
                     url: 'deletePactDetSKHT.do?isCheck=false',
                     data: {mapVo: JSON.stringify(param)},
                     success: function () {addMoney();subGrid.deleteRows(data);}
                 })
             });
         }
     }
     
 	function addElseSub(){
 		if(subject_type!="05"){
 			return ;
 		}
 		parent.$.etDialog.open({
             url: 'hrp/pac/basicset/elsesub/pactElseSubAdd.do?isCheck=false',
             width: 320,
             height: 280,
             title: '添加',
             btn: ['保存', '取消'],
             modal: true,
             btn1: function (index, el) {
                 var iframeWindow = parent.window[el.find('iframe').get(0).name];
                 iframeWindow.save()
             }
         });
 	}
 	 var initPactPlanGrid = function () {
         var columns = [
         	 { display: '收款期号', name: 'rec_id',width: '150px'},
             { display: '摘要', name: 'summary', width: '250px'},
              { display: '收款期限', name: 'rec_date', width: '150px',
            	    editor: {
            	      type: 'date',
            	      change:function (row) {
           		    	var data = palnGrid.getAllData();
           		 		 $(data).each(function () {
           		              var rowdata = this;
           		              if(row.pay_id > rowdata.pay_id){
           		            	  if(row.pay_date <= rowdata.pay_date){
           		            		 $.etDialog.error('日期必须大于低期号的日期');
           		            	  }
           		              }
           		          });
           		    	}
            	    }
              },
              { display: '收款条件', name: 'rec_cond_name', width: '150px',editor: {type: 'select',keyField: 'rec_cond',source:paycondSoues}},
              //{ display: '资金来源', name: 'source_name', width: '150px',editor: {type: 'select',keyField: 'source_id',source:sourceSoues}},
              { display: '计划金额', name: 'plan_money', align: 'right', width: '150px',
            	  render:function(ui){
            		  var data = ui.rowData
            		  return formatNumber(parseFloat(data.plan_money),2,1)
            	  }
              },
         ];
         var paramObj = {
         	editable: true,
         	height: '200',
         	width:'100%',
         	checkbox: true,
         	usePager: false,
            dataModel: {
             //url: 'queryPactPlanSKHT.do?isCheck=false&pact_code='+$("#pact_code").val()
            },
           	columns: columns,
           	editorEnd: editorEndForPlan,
            toolbar: {
                items: [
               	 { type: 'button', label: '增加', listeners: [{ click: addplan }], icon: 'add' },
                    { type: 'button', label: '删除',  listeners: [{ click: removeplan }], icon: 'del' }
                ]
            }
         };
        palnGrid = $("#pactplan").etGrid(paramObj);
     };
     
     // 用于动态生成标的物中的金额
     function editorEndForPlan(event, ui){
    	 if(ui.colIndx == 1){
   			var rec_id  = parseFloat(ui.rowData.rec_id).toString();
   			if (rec_id.length > 4) {
   				$.etDialog.error('收款期号的长度为1-4位');
   				ui.rowData.rec_id = rec_id.substr(0,4);
   				return;
   			}
     	 }
    	 if( ui.colIndx == 6){
   			var plan_money  = parseFloat(ui.rowData.plan_money);
   			if (!plan_money) {plan_money = 0;}
   			ui.rowData.plan_money = parseFloat(plan_money);
    	 }
     }
     
     function removeplan(){
    	 var data = palnGrid.selectGet();
         if (data.length == 0) {
             $.etDialog.error('请选择行');
         } else {
             var param = [];
             $(data).each(function () {
                 var rowdata = this.rowData;
                 rowdata.group_id = ${group_id};
                 rowdata.hos_id = ${hos_id};
                 rowdata.copy_code = '${copy_code}';
                 rowdata.pact_code = $("#pact_code").val();
                 param.push(rowdata);
             });
             $.etDialog.confirm('确定删除?', function () {
                 ajaxPostData({
                     url: 'deletePactPlanSKHT.do?isCheck=false',
                     data: {mapVo: JSON.stringify(param)},
                     success: function () {palnGrid.deleteRows(data);}
                 })
             });
         }
     }
     
     
	 var initPactDocGrid = function () {
         var docColumns = [
         	 { display: '阶段状态', name: 'state_name',width: '100px',editor: {type: 'select', keyField: 'pact_state',source:stateSoues,}},
             { display: '文档类别', name: 'doc_type_name',width: '100px',editor: { type: 'select',keyField: 'doc_type',source:typeSoues}},
             { display: '文档名称', name: 'doc_name', width: '250px',editor: { type: 'textbox' }},
             { display: '所在科室', name: 'dept_name', width: '140px',editor: {type: 'select',keyField: 'dept_no',source:deptSoues}},
             { display: '责任人', name: 'emp_name', width: '100px',editor: {type: 'select',keyField: 'emp_id',source:empSoues}},
             { display: '存放位置', name: 'location', width: '250px'},
             { display: '上传', name: 'file_path', align: 'center', width: '120px',fileModel: {keyField: 'file',url: '../../pactdoc/addFile.do?isCheck=false'}},
         ];
         var paramObj = {
         	editable: true,
         	height: '200',
         	width:'100%',
         	checkbox: true,
         	usePager: false,
            columns: docColumns,
            toolbar: {
                items: [
               	 	{ type: 'button', label: '增加', listeners: [{ click: addDoc }], icon: 'add' },
                    { type: 'button', label: '删除',  listeners: [{ click:  delDoc }],  icon: 'del' }
                ]
            }
         };
         docGrid =  $("#pactdoc").etGrid(paramObj);
     };
     
	function addSub() {  subGrid.addRow();}
	function addplan() {palnGrid.addRow();}
	function addDoc() {docGrid.addRow();}
	
	function delDoc(){
		 var data = docGrid.selectGet();
         if (data.length == 0) {
             $.etDialog.error('请选择行');
         } else {
             var param = [];
             $(data).each(function () {
                 var rowdata = this.rowData;
                 rowdata.group_id = ${group_id};
                 rowdata.hos_id = ${hos_id};
                 rowdata.copy_code = '${copy_code}';
                 if(typeof(rowdata.dept_no) != "undefined") { 
                 	 if(rowdata.dept_no.toString().indexOf("@") !== -1){
 		            	 rowdata.dept_id = rowdata.dept_no.split("@")[0];
 			             rowdata.dept_no = rowdata.dept_no.split("@")[1]; 
 		             }
 			 	 } 
                 param.push(rowdata);
             });
             $.etDialog.confirm('确定删除?', function () {
                 ajaxPostData({
                     url: '../../pactdoc/deletePactDocSKHT.do?isCheck=false',
                     data: {mapVo: JSON.stringify(param) },
                     success: function () {docGrid.deleteRows(data);}
                 })
             });
         }
	}
	
	$(function(){
    	initSelect();
   		initfrom();
   		setTimeout(function(){
   			initSubGrid();
   		},500);
   		
   		etTab = $("#etTab").etTab({
   			onChange: function(item){
   				if(item.tabid == '1'){
   					if(!palnGrid){
	   					initPactPlanGrid();
   					}
   				}else if(item.tabid == '2'){
   					if(!docGrid){
	   					initPactDocGrid();
   					}
   				}
   			}
   		});
    	
		$("#save").on("click", function () {
			save();
		})
		$("#cancle").on("click", function () {
			var curIndex = parent.$.etDialog.getFrameIndex(window.name);
	        parent.$.etDialog.close(curIndex); 
		})
	})
	
	  //日期
	var initfrom = function(){
		startpicker = $("#start_date").etDatepicker({
			defaultDate: true,
		  	onChange: function (date) {
		  		var end = endpicker.getValue();
		  		if(end < date){
		  			endpicker.setValue(end);
		  		}
		  	}
		});
		endpicker = $("#end_date").etDatepicker({
			defaultDate: true,
		  	onChange: function (date) {
		  		var start = startpicker.getValue();
		  		if(start > date){
		  			endpicker.setValue(start);
		  		}
		  	}
		});
		signpicker = $("#sign_date").etDatepicker({
			defaultDate: true,todayButton: false,
			onChange: function (date) {
				startpicker.setValue($("#sign_date").val());//modifyByWjt合同开始日期默认等于签订日期
			}
		});
		is_bid = $('#is_bid').etCheck();
		is_deposit = $('#is_deposit').etCheck({
			onChange:function(state){
				if(state == 'checked'){
					deposit_type.enabled();
					var value = deposit_type.getValue();
					if(value == '01' || value == '02'){
		      			 $("#deposit_money").removeAttr("disabled");
		      			 $("#deposit_money").removeAttr("style");
					 }
				}else{
					deposit_type.disabled();
					deposit_type.setValue(0);
					$("#deposit_money").attr("disabled","disabled");
					$("#deposit_money").attr("style","background-color:#EAEAEA");
					$("#deposit_money").val("");
				}
			}
		}); 
	};
	function formatMoney(id)
	{
		$("#"+id.toString()).val(formatNumber(parseFloat($("#"+id.toString()).val()),2,1));
	}
</script>
</head>

<body style="overflow: scroll; ">
	<table class="table-layout">
		<tr>
			<td class="label no-empty" style="width: 100px;">合同编号：</td>
			<td class="ipt"><input id="pact_code" type="text" disabled="disabled" style="background-color: #EAEAEA"/></td>
			<td class="label no-empty" style="width: 100px;">合同类型：</td>
			<td class="ipt"><select id="pact_type_code" style="width: 180px;"></select> </td>
			<td class="label no-empty" style="width: 100px;">合同状态：</td>
			<td class="ipt"><select id="state_code" style="width: 180px;"></select></td>
		</tr>
		<tr>
			<td class="label no-empty" style="width: 100px;">合同名称：</td>
			<td class="ipt" ><input id="pact_name" type="text"  /></td>
			<td class="label" style="width: 100px;">原始编号：</td>
			<td class="ipt"><input id="original_code" type="text"/></td>
			<td class="label" style="width: 100px;">主合同：</td>
			<td class="ipt" ><select id="master_pact_code" style="width: 180px;"></select></td>
		</tr>
		<tr>	
			<td class="label no-empty" style="width: 100px;">签订日期：</td>
			<td class="ipt"><input id="sign_date" type="text"/></td>
			<td class="label" style="width: 100px;">预交货期限(月):</td>
			<td class="ipt" ><input type="text" id="delivery_term"  ></td>
			<td class="label" style="width: 100px;">项目：</td>
			<td class="ipt" ><select id="proj_id"  style="width: 180px;"></select></td>
		</tr>
		<tr id="ht_sign_point" style="height: 16px;">
		<td></td>
			<td class="ipt" style="color: red; white-space: nowrap; position: relative;"><div style="top: -3px; position: absolute;">必须小于合同类型启用日期</div></td> 
		</tr>
		<tr id="xy_sign_point" style="height: 16px;">
		<td></td>
			<td class="ipt" style="color: red; white-space: nowrap; position: relative;"><div style="top: -3px; position: absolute;">必须大于等于合同类型启用日期</div></td> 
		</tr>
		<tr>
			<td class="label no-empty" style="width: 100px;">签约单位：</td>
			<td class="ipt"><input id="hos_name" type="text" value="${hos_name }" disabled="disabled"/></td>
			<td class="label no-empty" style="width: 100px;">签订科室：</td>
			<td class="ipt"><select id="dept_no" style="width: 180px;"></select></td>
			<td class="label no-empty" style="width: 100px;">负责人：</td>
			<td class="ipt"><select id="emp_id" style="width: 180px;"></select> </td>
		</tr>
		<tr>
			<td class="label no-empty" style="width: 100px;">客户：</td>
			<td class="ipt"><select id="cus_no" style="width: 180px;"></select></td>
			<td class="label no-empty" style="width: 100px;">对方负责人：</td>
			<td class="ipt"><input id="opp_emp" type="text" /></td>
			<td class="label" style="width: 120px;">对方联系电话：</td>
			<td class="ipt"><input id="opp_phone" type="text" /></td>
		</tr>	
		<tr>
			<td class="label" style="width: 100px;">服务商：</td>
			<td class="ipt"><input id="server" type="text" style="width: 180px;"></td>
			<td class="label" style="width: 100px;">服务联系人：</td>
			<td class="ipt"><input id="ser_emp" type="text" /></td>
			<td class="label" style="width: 120px;">服务电话：</td>
			<td class="ipt"><input id="ser_phone" type="text" /></td>
		</tr>		
		<tr>
			<td class="label no-empty" style="width: 120px;">合同开始日期：</td>
			<td class="ipt"><input id="start_date" type="text" style="background-color: #EAEAEA"/></td>
			<td class="label no-empty" style="width: 120px;">合同截止日期：</td>
			<td class="ipt"><input id="end_date" type="text" /></td>
			<td class="label no-empty" style="width: 120px;">合同金额：</td>
			<td class="ipt"><input id="pact_money" type="text" maxlength="15" disabled="disabled" style="background-color: #EAEAEA" value="0"/></td>
		</tr>
		<tr>
			<td></td>
			<td><input id="is_bid" type="checkbox" />是否招标</td>
			<td class="label" style="width: 120px;">组织方式：</td>
			<td class="ipt"><select id="organ_type" style="width: 180px;"></select></td>
			<td class="label" style="width: 120px;">采购方式：</td>
			<td class="ipt"><select id="buy_type" style="width: 180px;"></select></td>
		</tr>
		<tr>
			<td></td>
			<td><input id="is_deposit" type="checkbox" />履约保证金</td>
			<td class="label" style="width: 120px;">履约担保方式：</td>
			<td class="ipt"><select id="deposit_type" disabled="disabled" style="width: 180px;"></select></td>
			<td class="label" style="width: 120px;">保证金金额：</td>
			<td class="ipt"><input id="deposit_money" type="text" maxlength="15" disabled="disabled" style="background-color: #EAEAEA" onchange="formatMoney(this.id);"/></td>
		</tr>
	    <tr>
	       <td  class="label" style="width: 100px;">服务条款</td>
	       <td class="ipt"><textarea style="resize:none;width: 180px;" id="cont_term1"></textarea></td>
	       
	       <td  class="label" style="width: 100px;">付款条款</td>
	       <td class="ipt"><textarea style="resize:none;width: 180px;"  id="cont_term2"></textarea></td>
	       
	       <td  class="label" style="width: 100px;">验收条款</td>
	       <td class="ipt"><textarea style="resize:none;width: 180px;"  id="cont_term3"></textarea></td>
	    </tr>
	    <tr>
	       <td  class="label" style="width: 100px;">违约处理</td>
	       <td class="ipt"><textarea style="resize:none;width: 180px;"  id="cont_term4"></textarea></td>
	       
	       <td  class="label" style="width: 100px;">交货条款</td>
	       <td class="ipt"><textarea style="resize:none;width: 180px;"  id="cont_term5"></textarea></td>
	       
	       <td  class="label" style="width: 100px;">质保条款</td>
	       <td class="ipt"><textarea style="resize:none;width: 180px;"  id="cont_term6"></textarea></td>
	    </tr>
	    <tr><td  class="label" style="width: 100px;">备注</td>
	       <td class="ipt"  colspan="5"><textarea style="resize:none;width: 95%;"  id="note"></textarea></td></tr>
	</table>
		<div id="etTab">
		  <div title="标的物" tabid="0">
			 <div id="subGrid"></div>
		  </div>
		  <div title="收款计划" tabid='1'>
			 <div id="pactplan"></div>
		  </div>
		  <div id="tab_3" title="文档管理" tabid='2'>
		    <div id="pactdoc"></div>
		  </div>
		</div>
	<div class="button-group">
	  <button id="save">保存</button>
	  <button id="cancle">取消</button>
	</div>
</body>

</html>

