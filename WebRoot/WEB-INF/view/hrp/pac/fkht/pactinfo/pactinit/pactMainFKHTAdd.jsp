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
	<jsp:param value="tree,grid,select,dialog,validate,tab,datepicker,upload,checkbox" name="plugins" />
</jsp:include>
<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script type="text/javascript">
var parentFrameName = parent.$.etDialog.parentFrameName;
var parentWindow = parent.window[parentFrameName]
var etTab,subGrid,sourceGrid,docGrid,palnGrid,deposit_type,dept_no,emp_id,sup_no,proj_id,pact_type_code,subject_type,bid_code_select,organ_type,buy_type;
var deptSoues = [];   
var sourceSoues = [];   
var empSoues = [];    
var typeSoues = [];    
var stateSoues = [];    
var paycondSoues = [];    
var is_bid ;
var is_deposit;
var attr_code ;// 合同属性    用于 引用招标、引用资产购置申请  功能 能否使用的判断
var sourceGridData ; // 加载资金来源表格数据用 
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
					{el : $("#sup_no"),required : true},
					{el : $("#opp_emp"),required : true},
					{el : $("#trade_type"),required : true},
					{el : $("#curr_code"),required : true},
					{el : $("#start_date"),required : true},
					{el : $("#end_date"),required : true},
					{el : $("#pact_money"),required : true},
					{el : $("#deposit_money"),required : true},
					{el : $("#delivery_term"),required : true}
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
					{el : $("#sup_no"),required : true},
					{el : $("#opp_emp"),required : true},
					{el : $("#trade_type"),required : true},
					{el : $("#curr_code"),required : true},
					{el : $("#start_date"),required : true},
					{el : $("#end_date"),required : true},
					{el : $("#pact_money"),required : true},
					{el : $("#delivery_term"),required : true}
					]
			});
		}
		if(!formValidate.test()){
			 $.etDialog.error("必填项不可为空!");
			return;
		};
		
		delivery_term = $("#delivery_term").val();
		 if (typeof(delivery_term) != 'number' && (delivery_term)%1 != 0){
			 $.etDialog.error("请输入正确的数字格式") ;return ;
	          };
	          
	
		if(subGrid){
			var reg = /^[0-9]+.?[0-9]*$/;
		    var err = "";
			 var sub = [];
			 var data = subGrid.getData();
	         if (data != null && data.length != 0) {
	              var money = 0;
	              $(data).each(function () {
	                  var rowdata = this;
	                  if(!rowdata.subject_id){err = "标的物不能为空";return ;}
	                  if(!rowdata.amount){err = "数量不能为空";return ;}
	                  if(!rowdata.price){err ="单价不能为空";return ;}
	                  if(!rowdata.money){err = "金额不能为空";return ;}
	                  
	                  rowdata.group_id = ${group_id};
	                  rowdata.hos_id = ${hos_id};
	                  rowdata.copy_code = '${copy_code}';
	                  if((typeof(rowdata.dept_name) != "undefined")&&(rowdata.dept_name.indexOf("@") != -1)){
	                	  rowdata.dept_no =  rowdata.dept_name;
	                  }else{
	                	  rowdata.dept_no =  rowdata.dept_no;
	                  }
	                  rowdata.subject_no =  rowdata.subject_id;
	                  rowdata.subject_type =  subject_type;
	                  if(reg.test(rowdata.fac_name)){
	                	  rowdata.fac_id = rowdata.fac_name;
	                	  rowdata.fac_no = rowdata.fac_name;
	                  }else{
	                	  rowdata.fac_id = rowdata.fac_id;
		                  rowdata.fac_no = rowdata.fac_id;
	                  }
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
		
		if(palnGrid){
			var err;
			var testnum;
			 var plan = [];
		  		var data = palnGrid.getAllData();
		  		if(data != null && data.length != 0){
		  			 var money = 0;
			  		 $(data).each(function () {
			               var rowdata = this;
			               testnum=rowdata.plan_money;
			               if(!rowdata.pay_id){err = "付款期号不能为空";return ;}
			               if(!rowdata.pay_type){err = "付款类型不能为空";return ;}
			               if(!rowdata.pay_date){err = "付款期限不能为空";return ;}
			               if(!rowdata.pay_cond){err = "付款条件不能为空";return ;}
			               //if(!rowdata.source_id){err = "资金来源不能为空";return ;}
			               if(!rowdata.plan_money){err = "计划金额不能为空";return ;}
			               if(rowdata.pay_type=="全款"){
			            	   rowdata.pay_type = 1;
						   }
			               if(rowdata.pay_type=="预付款"){
			            	   rowdata.pay_type = 2;
						   }
			               if(rowdata.pay_type=="期款"){
			            	   rowdata.pay_type = 3;
						   }
			               if(rowdata.pay_type=="尾款"){
			            	   rowdata.pay_type = 4;
						   }
			               rowdata.group_id = ${group_id};
			               rowdata.hos_id = ${hos_id};
			               rowdata.copy_code = '${copy_code}';
			               rowdata.pact_code = "${entity.pact_code }";
			               money  += parseFloat(rowdata.plan_money);
			               plan.push(rowdata);
			               
			           });
			  		if(money!=parseFloat($("#pact_money").val().replace(/,/g,""))){
			  			err+="付款计划总金额与合同总金额不一致"
			  		}
			  		if(err.length != 0){$.etDialog.error(err) ;return;}
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
		             if(typeof(rowdata.dept_no) != "undefined") //Modified By QW20200403
		            	{
			             	rowdata.dept_id = rowdata.dept_no.split("@")[0];
			             	rowdata.dept_no = rowdata.dept_no.split("@")[1];
				 		}
		          
		             doc.push(rowdata);
		         });
				 if(err.length != 0){$.etDialog.error(err) ;return ;}
			}
		}
		
		ajaxPostData({
			url : 'addPactMainFKHT.do?isCheck=false',
			data : {
				pact_type_code : $("#pact_type_code").val(),
				state_code : $("#state_code").val(),
				pact_name : $("#pact_name").val(),
				original_code : $("#original_code").val(),
				master_pact_code : $("#master_pact_code").val(),
				sign_date : $("#sign_date").val(),
				dept_id : dept_no.getValue().split("@")[0],
				dept_no : dept_no.getValue().split("@")[1],
				emp_id : $("#emp_id").val(),
				sup_no : sup_no.getValue().split("@")[1],
				sup_id : sup_no.getValue().split("@")[0],
				opp_emp : $("#opp_emp").val(),
				opp_phone : $("#opp_phone").val(),
				pact_intro : $("#pact_intro").val(),
				trade_type : $("#trade_type").val(),
				curr_code : $("#curr_code").val(),
				pact_money_w : $("#pact_money_w").val().replace(/,/g,""),
				pact_money : $("#pact_money").val().replace(/,/g,""),
				start_date : $("#start_date").val(),
				end_date : $("#end_date").val(),
				is_bid : is_bid.checked ? 1 : 0,
				organ_type : $("#organ_type").val(),
				buy_type : $("#buy_type").val(),
				is_deposit : is_deposit.checked ? 1 : 0,
				deposit_type : $("#deposit_type").val(),
				deposit_money : $("#deposit_money").val().replace(/,/g,""),
				proj_id : proj_id.getValue().split("@")[0],
				proj_no : proj_id.getValue().split("@")[1],
				
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
				note:$("#note").val(),
				bid_id:bid_code_select.getValue(),
				sub : JSON.stringify(sub),
				plan : JSON.stringify(plan),
				doc : JSON.stringify(doc),
				is_init : ${is_init }
			},
			success : function(data) {
			    parentWindow.query(); 
				var curIndex = parent.$.etDialog.getFrameIndex(window.name);
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
      		url: '../../../basicset/select/queryPactTypeFKHTSelect.do?isCheck=false',
			  success: function (result) {
				 pact_type_code = $("#pact_type_code").etSelect({
					 defaultValue: 'none',
					 backEndSearch: false,
					 options:result ,
					 onItemAdd:function(value, $item){
						 var maxDate;
						 for(var i = 0;i<result.length;i++){
							 var obj = result[i];
							 if(value == obj.id){
								 subject_type = obj.subject_type;
								 attr_code = obj.attr_code ;
								 toolBarSet();//按钮启用/禁用设置
								 subGrid.getColumns()[3].editor.url = '../../../basicset/select/querySubjectSelect.do?isCheck=false&type='+subject_type;
								 var timestamp =  new Date(obj.start_date);
								 maxDate = timestamp.toLocaleDateString().replace(/\//g, "-");
							 }
						 }
						 if(${is_init} == 1){
							 signpicker = $("#sign_date").etDatepicker({todayButton: false,defaultDate: maxDate,maxDate: maxDate});
						 }else{
							 signpicker = $("#sign_date").etDatepicker({todayButton: false,defaultDate: maxDate,minDate: maxDate});
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
						  if(obj.id >= 12){stateSoues.push(obj);}
					  }
				  }else{
					  for(var i = 0;i<result.length;i++){
						  var obj = result[i];
						  if(obj.id <= 11){stateSoues.push(obj);}
					  }
				  }
				state_code = $("#state_code").etSelect({backEndSearch: false, options:stateSoues,defaultValue: 'none',});
			  },
		});
      	trade_type = $("#trade_type").etSelect({backEndSearch: false, url: '../../../basicset/select/queryDictSelect.do?isCheck=false&f_code=TRADE_TYPE', defaultValue: '01',});
        deposit_type = $("#deposit_type").etSelect({
       	 	 url: '../../../basicset/select/queryDictSelect.do?isCheck=false&f_code=DEPOSIT_TYPE',
       	 	 defaultValue: 'none',
       	 	 backEndSearch: false,
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
        organ_type = $("#organ_type").etSelect({backEndSearch: false, url: '../../../basicset/select/queryDictSelect.do?isCheck=false&f_code=ORGAN_TYPE',defaultValue: 'none'});
        buy_type = $("#buy_type").etSelect({backEndSearch: false, url: '../../../basicset/select/queryDictSelect.do?isCheck=false&f_code=BUY_TYPE', defaultValue: 'none',});
        sup_no = $("#sup_no").etSelect({backEndSearch: false, url: '../../../basicset/select/queryHosSupSelectDict.do?isCheck=false',defaultValue: 'none',});
        proj_id = $("#proj_id").etSelect({backEndSearch: false, url: '../../../basicset/select/queryHosProjSelectDict.do?isCheck=false', defaultValue: 'none'});
        
        ajaxPostData({
      		url: '../../../basicset/select/queryAssTendInfo.do?isCheck=false',
			  success: function (result) {
				  bid_code_select = $("#bid_code").etSelect({
					 defaultValue: 'none',
					 backEndSearch: false,
					 options:result ,
					 onChange:function(value, $item){
						 var maxDate;
						 for(var i = 0;i<result.length;i++){
							 var obj = result[i];
							 if(value == obj.id){
								 organ_type.setValue(obj.organ_type);
								 buy_type.setValue(obj.buy_type);
								 organ_type.disabled();
								 buy_type.disabled();
							 }
						 }
			      	 }  
				 });
			  },
		});
        curr_code = $("#curr_code").etSelect({
       	  url: '../../../basicset/select/queryAccCurDictSelect.do?isCheck=false',
       	  defaultValue: 'RMB',
       	  backEndSearch: false,
       	  onChange:function(value){
       		 if(value == "RMB"){
       			 $("#pact_money_w").val("");
       			 $("#pact_money_w").attr("disabled","disabled");
       			 $("#pact_money_w").attr("style","background-color:#EAEAEA;");
       		 }else{
       			 $("#pact_money_w").removeAttr("disabled");
       			 $("#pact_money_w").removeAttr("style");
       		 }
       	  }
         });
    	ajaxPostData({
    		 url: '../../../basicset/select/queryHosEmpDictSelect.do?isCheck=false',
			 success: function (result) {
				emp_id = $("#emp_id").etSelect({backEndSearch: false, options:result,defaultValue: 'none',});
	            empSoues = result;
			 },
		});
    	ajaxPostData({
    		 url: '../../../basicset/select/queryDeptSelectDict.do?isCheck=false',
			 success: function (result) {
				 dept_no = $("#dept_no").etSelect({backEndSearch:false,options:result,defaultValue: "none",});
				 deptSoues = result;
				 }
    		 });
      	ajaxPostData({
   		 	url: '../../../basicset/select/queryPactFKHTSelect.do?isCheck=false&is_init=${is_init}',
			success: function (result) {
			  master_pact_code = $("#master_pact_code").etSelect({
                 	options:result,
                 	backEndSearch: false,
                 	defaultValue: "none",
                 	onChange:function(value){
                 		if(!value){return;}
                 		ajaxPostData({
                		 	url: 'queryPactMainFKHTForMaster.do?isCheck=false',
                		 	data:{
                		 		pact_code:value
                		 	},
                			success: function (result) {
								signpicker.setValue(result.sign_date);
								dept_no.setValue(result.dept_id +'@'+result.dept_no);
								emp_id.setValue(result.emp_id);
								sup_no.setValue(result.sup_id +'@'+result.sup_no);
								proj_id.setValue(result.proj_id +'@'+result.proj_no);
								$("#opp_emp").val(result.opp_emp);
								$("#opp_phone").val(result.opp_phone);
                			},
                		});
                 	}
              });
			 },
		});
      	ajaxPostData({url: '../../../basicset/select/queryHosSourceDictSelect.do?isCheck=false',success: function (result) {sourceSoues = result; }});
      	ajaxPostData({url: '../../../basicset/select/queryPactPayCondSelect.do?isCheck=false',success: function (result) {paycondSoues = result;}});
      	ajaxPostData({url: '../../../basicset/select/queryPactDocTypeSelect.do?isCheck=false' ,success: function (result) {typeSoues= result;}});
      }
    
     var without_id = "";
	 var initSubGrid = function () {
         var columns = [
				{display: '来源', name: 'source', align: 'center', width: '100',editable: false,
					render:function(rowData,rowindex,value){
						if( value == 1){
							return '定标';
						}else if (value==2){
							return '资产采购申请';
						}else{
							return '手工添加';
						}
					}
				},
				{ display: '标的物编码', name: 'subject_code',width: '120'},
				{ display: '标的物名称', name: 'subject_name',width: '160',
					valueField : 'subject_id',	textField : 'label',
				  	editor: {
					   type: 'select', 
					   keySupport : true,
	 					autocomplete : true,
					   url:'../../../basicset/select/querySubjectSelect.do?isCheck=false&without_id='+without_id+'&type='+subject_type,
					   onChange: function(item){
						   if (item.selected) { 
							ajaxPostData({
								url: '../../../basicset/select/querySubjectSelect.do?isCheck=false&id='+item.selected.id+'&type='+subject_type,
								success: function (result) {
									subGrid.updateRow(item.rowindex, {
										subject_id : result[0].id,
										subject_code : result[0].item_code,
										item_name : result[0].text,
										item_spec : result[0].item_spec,
										item_model : result[0].item_model,
										fac_name : result[0].fac_name,
										fac_id : result[0].fac_id,
										item_brand : result[0].ass_brand,
										fac_no : result[0].fac_no,
										unit_code : result[0].unit_code,
										unit_name : result[0].unit_name
									});
								}
							});
							without_id += item.selected.id + ","}
						},
					},
				},
				{ display: '通用名', name: 'item_name', width: '120',editor: {type: 'text'}},
				{ display: '规格', name: 'item_spec', width: '120',editor: {type: 'text'}},
				{ display: '型号', name: 'item_model', width: '120',editor: {type: 'text'}},
				{ display: '生产厂家', name: 'fac_name', width: '120',textField : 'text',
					editor: {
						type: 'select',
						valueField:'id',
						textField : 'text',
						url : '../../../basicset/select/queryHosFacDict.do?isCheck=false',
	 					keySupport : true,
	 					autocomplete : true,
					}
				},
				{ display: '品牌', name: 'item_brand', width: '120',editor: {type: 'text'}},
				{ display: '计量单位', name: 'unit_code', width: '120',textField : 'unit_name',
					editor: {
						type: 'select',
						valueField : 'unit_code',
						textField : 'unit_name',
						url : '../../../basicset/select/queryHosUnitSelect.do?isCheck=false',
	 					keySupport : true,
	 					autocomplete : true
					}
				},
				{ display: '数量', name: 'amount', width: '120',align: "right",editor: {type: 'int'}},
				{ display: '单价', name: 'price', width: '120',align: "right", editor: { type: 'number'},
              	 render:function(rowData){
 	          		  return formatNumber(parseFloat(rowData.price),2,1)
 	          	 	}
               },
				{ display: '金额', name: 'money',width: '120',align: "right", editor: {type: "number"},
             	 render:function(rowData){
 	          		  return formatNumber(parseFloat(rowData.money),2,1)
 	          	 	}
               },              
				{ display: '交货日期', name: 'arrive_date',width: '120', align: 'center',editor: {type: 'date',}},
				{ display: '保修期(月)', name: 'repair_months',width: '120',editor: {type: 'int'}},
				{ display: '备注', name: 'note',width: '120',editor: {type: 'text'}},
				{ display: '需求科室', name: 'dept_name',width: '120',textField : 'name',
					editor : {
	 					type : 'select',
	 					valueField:'id',
	 					textField : 'name',
	 					url : '../../../basicset/select/queryDeptNameAndId.do?isCheck=false',
	 					keySupport : true,
	 					autocomplete : true,
 					}
			    }
         ];
         var paramObj = {
             height: '240',
             width:'100%',
             checkbox: true,
             usePager: false,
             isScroll: true, 
             columns: columns,
             enabledEdit : true,
             selectRowButtonOnly:true,
             isAddRow:false,
             onAfterEdit : editorEnd,
             onBeforeEdit: f_onBeforeEdit,
             onSelectRow: loadSourceGrid,//选择行时，加载 资金来源表数据
             toolbar: {
                 items: [{text : '增加行',	id : 'add',click : addSub,icon : 'add'}, 
				         {line : true},
				         {text : '引入定标',	id : 'impBid',click : impBidInfo,icon : 'add'}, 
				         {line : true},
				         {text : '引入资产购置申请',	id : 'impAssApply',click : impAssApplyInfo,icon : 'add'}, 
				         {line : true},
				         {text : '删除',	id : 'delete',click : removeSub,icon : 'delete'}, 
				         {line : true},
				         /* {text : '添加材料字典',	id : 'addMat',click : addMatInv,icon : 'add'}, 
				         {line : true},
				         {text : '添加药品字典',	id : 'addMed',click : addMedInv,icon : 'add'}, 
				         {line : true},
				         {text : '添加资产字典',	id : 'addAss',click : addAssNoDict,icon : 'add'}, 
				         {line : true}, */
				         {text : '添加其他标的物',	id : 'addElse',click : addElseSub,icon : 'add'}, 
				         
                 ]
             }
         };
         
       	subGrid = $("#subGrid").ligerGrid(paramObj);
     };
     // 明细 新增资金来源
     function loadHeadItem() {
 		gridItem = $("#sourceGrid").ligerGrid({
 			columns : [{ display : '资金来源',	name : 'source_id',textField : 'source_name',width : 100,
	 				editor : {
	 					type : 'select',
	 					url : '../../../basicset/select/queryHosSourceDictSelect.do?isCheck=false',
	 					keySupport : true,
	 					autocomplete : true,
	 				}
	 			},
	 			{	display : '金额',	name : 'money',align : 'right',width : 100,	editor : {type : 'float'},
	 				render : function(rowdata, rowindex,value) {
	 					 return formatNumber(value == null ? 0:value,2,1);
	 				},
	 			},
	 			{ display: '备注', name: 'note',width: '120',editor: { type: 'string' }} ],
 			usePager : false,
 			width : '100%',	height : '240',checkbox : true,enabledEdit : true,
 			rownumbers : true,isAddRow:false,isScroll: true, 
 			data:sourceGridData,
 			selectRowButtonOnly : true,//heightDiff: -10,
 			toolbar : {
 				items : [ {text : '添加行',	id : 'saveItem',click : addSourceData,icon : 'save'}, 
 				          {line : true},
 				          {text : '确定',	id : 'saveItem',click : saveSourceData,icon : 'save'}, 
 				          {line : true},
 				          {text : '删除',	id : 'deleteItem',click : removeSourceData,icon : 'delete'}
 				         ]
 			}
 		});

 		sourceGrid = $("#sourceGrid").ligerGetGridManager();
 	}
   //选中明细数据  加载 资金来源数据 
    function loadSourceGrid(rowData,rowindex,rowobj){
    	 if(rowData.sourceData){
    		 sourceGridData = rowData.sourceData ;
    	 }else{
    		 sourceGridData  = {"Rows":[],"Total":0} ;
    	 }
    	loadHeadItem();
    	 
     } 
   
  //动态判断单元格是否可编辑
    function f_onBeforeEdit(e) {
		if (e.record.source == 1) {
			if(e.column.columnname == "item_name"){return true;}
			if(e.column.columnname == "arrive_date"){return true;}
			if(e.column.columnname == "repair_months"){return true;}
			if(e.column.columnname == "note"){return true;}
			if(e.column.columnname == "dept_name"){return true;}
			return false;
		} else if(e.record.source == 2) {
			if(e.column.columnname == "item_name"){return true;}
			if(e.column.columnname == "item_spec"){return true;}
			if(e.column.columnname == "item_model"){return true;}
			if(e.column.columnname == "fac_name"){return true;}
			if(e.column.columnname == "item_brand"){return true;}
			if(e.column.columnname == "price"){return true;}
			if(e.column.columnname == "arrive_date"){return true;}
			if(e.column.columnname == "repair_months"){return true;}
			if(e.column.columnname == "note"){return true;}
			if(e.column.columnname == "dept_name"){return true;}
			return false;
		}else{
			if(e.column.columnname == "money"){return false;}
			return true;
		}
	 }
     //资金来源表格 添加行
     function addSourceData(){
    	 var data = subGrid.getCheckedRows();
         if (data.length != 1) {
             $.etDialog.error('请选择一行标的物明细');
         }else{
        	 sourceGrid.addRow();
         }
    	 
     }
     //保存资金来源数据 到明细数据中
     function saveSourceData(){
    	 var detailMoney = 0 ;//明细数据金额
    	 var sourceMoney = 0 ;//资金来源总金额
    	 var errStr = '';//表格数据校验错误信息
    	 var data = subGrid.getCheckedRows();
    	 if (data.length != 1) {
             $.etDialog.error('请选择一行标的物明细');
         }else{
        	 var sourceData = sourceGrid.getData();
        	 $(data).each(function () {
        		 detailMoney = this.money;
        		 $.each(sourceData,function(index,content){
        			 if(!content.source_id){errStr = "资金来源不能为空";return ;}
	                 if(!content.money){errStr = "金额不能为空";return ;}
	                 var money = parseFloat(content.money)
	                 if (!money) {money = 0;}
	                 content.money = money;
        			 sourceMoney += money;
        		 })
        		 if(errStr != ''){
        			 if(errStr.length != 0){$.etDialog.error(errStr) ;return ;}
        		 }else{
        			 if(detailMoney==sourceMoney){
            			 subGrid.updateRow(this,{sourceData:{"Rows":sourceData,"Total":sourceData.length}});
            			 $.etDialog.success('操作成功！');
            		 }else{
            			 $.etDialog.error('标的物明细数据金额与资金来源总金额不一致,请核对！');
            		 }
        		 }
        	 })
        	 
         }
        
     }
	 //资金来源表格 删除行
     function removeSourceData(){
    	 var data = subGrid.getCheckedRows();
         if (data.length != 1) {
             $.etDialog.error('请选择一行标的物明细');
         } else {
        	 var dataSource = sourceGrid.getCheckedRows();
        	 if (data.length == 0) {
                 $.etDialog.error('请选择要删除的资金来源明细数据');
        	 }else{
        		 sourceGrid.deleteSelectedRow2();
        	 }
             
         }
     }
  // 用于动态生成标的物中的金额
     function editorEnd(e){
    	 if(e.column.name == 'amount' || e.column.name == 'price'){
    		var data = e.record;
    		if(!data.price){
    			 data.price = 0;
    		}else{
    			var price = parseFloat(data.price);
    			if (!price) {price = 0;}
    			e.record.price = parseFloat(price)
    		}
    		e.record.money  = parseInt(data.amount)* parseFloat(data.price) ;
    		subGrid.updateCell( 'money',e.record.money, e.record);

    		addMoney();
    	 }
     }
  
    function addMoney(){
    		var money = 0;
     	 	var data = subGrid.getData();
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
    // 引入定标
    function impBidInfo(){
    	var bid_code = bid_code_select.getText();
    	if(bid_code){
    		 parent.$.etDialog.open({
                 url: 'hrp/pac/fkht/pactinfo/pactinit/impBidInfo.do?isCheck=false&bid_code='+bid_code,
                 width: $(window).width(),
                 height: $(window).height(),
                 title: '引入定标',
                 modal: true,
                 frameName: window.name
             });
    	}else{
    		$.etDialog.error('请勾选是否招标后选择定标编号');
    	}
    	
    }
    // 引入资产购置申请
    function impAssApplyInfo(){
    	 parent.$.etDialog.open({
             url: 'hrp/pac/fkht/pactinfo/pactinit/impAssApplyInfo.do?isCheck=false',
             width: $(window).width(),
             height: $(window).height(),
             title: '引入资产购置申请',
             modal: true,
             frameName: window.name
         });
    }
    function removeSub(){
    	 var data = subGrid.getCheckedRows();
         if (data.length == 0) {
             $.etDialog.error('请选择行');
         } else {
             addMoney();
             subGrid.deleteSelectedRow2();
             // 清除资金来源表数据
             sourceGridData  = {"Rows":[],"Total":0} ;
             loadHeadItem();
             
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
 	function addMedInv(){
 		parent.$.ligerDialog.open({
			title: '添加药品字典',
			height: 550,
			width: 1000,
			url: 'hrp/med/info/basic/inv/medInvAddPage.do?isCheck=false&',
			modal: true, showToggle: false, showMax: true, showMin:false, isResize: true, top: 10,
			parentframename: window.name,
			buttons: [
				{ text: '保存', onclick: function(item, dialog){dialog.frame.save();}, cls:'l-dialog-btn-highlight' },
				{ text: '取消', onclick: function(item, dialog){dialog.close();}}
			]
		});
 	}
 	
 	function addMatInv(){
 		parent.$.ligerDialog.open({
			title: '添加材料字典',
			height: 550,
			width: 1000,
			url: 'hrp/mat/info/basic/inv/matInvAddPage.do?isCheck=false&',
			modal: true, showToggle: false, showMax: true, showMin:false, isResize: true, top: 10,
			parentframename: window.name,
			buttons: [
				{ text: '保存', onclick: function(item, dialog){dialog.frame.save();}, cls:'l-dialog-btn-highlight' },
				{ text: '取消', onclick: function(item, dialog){dialog.close();}}
			]
			
		});
 	}
 	
 	function addAssNoDict(){
 		parent.$.ligerDialog.open({	
	 		url: 'hrp/ass/assnodict/assNoDictAddPage.do?isCheck=false', 
	 		height: 500,width: 500, 
	 		title:'添加资产字典',
	 		modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,
	 		buttons: [ 
	 			{ text: '确定', onclick: function (item, dialog) { dialog.frame.saveAssNoDict(); },cls:'l-dialog-btn-highlight' },
	 			{ text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
	 			] 
	 		});
 	}
 	
 	 var initPactPlanGrid = function () {
		 var columns = [
         	 { display: '付款期号', name: 'pay_id',width: '150px'},
             { display: '付款类型', name: 'pay_type', width: '150px',
				editor: {
					type: 'select',
					keyField: 'pay_type',
					source: [{ "id": 1, "text": "全款",label:"全款"}, 
					       { "id": 2, "text": "预付款",label:"预付款"},
					       { "id": 3, "text": "期款",label:"期款"},
					       { "id": 4, "text": "尾款",label:"尾款"}
					      ],
					keySupport: true,
					autocomplete: true,
				},
				editable: true
             },
             { display: '摘要', name: 'summary', width: '250px'},
             { display: '付款期限', name: 'pay_date', width: '150px',
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
              { display: '付款条件', name: 'pay_cond_name', width: '150px',
            	  editor: {
           		     type: 'select',  //编辑框为下拉框时
           		  	 keyField: 'pay_cond',
           		     source:paycondSoues,   //  静态数据接口  也可以是回调函数返回值
           		 }	 
              },
              /* { display: '资金来源', name: 'source_name', width: '150px',
            	  editor: {
          		     type: 'select', 
          		     keyField: 'source_id',
          		     source:sourceSoues,   //  静态数据接口  也可以是回调函数返回值
          		 }	  
              }, */
              { display: '计划付款比例(%)', name: 'rate', align: 'right', width: '150px',
            	  editor:{
            		  type:'number',
            		  change:function(ui){
            			  var data = ui.rowData;
            			  if(data.rate){
            				  data.plan_money = parseFloat($("#pact_money").val())*parseFloat(data.rate);
            				  initPactPlanGrid.refreshCell(ui.rowIndx, 'plan_money', false);
            			  }else{
            				  data.plan_money = 0 ;//计划付款比例 不填  计划付款金额暂时赋0,手动修改
            				  initPactPlanGrid.refreshCell(ui.rowIndx, 'plan_money', false);
            			  }
            			 

            		  }
            	  }
              },
              { display: '计划金额', name: 'plan_money', align: 'right', width: '150px',
            	  render:function(ui){
            		  var data = ui.rowData
            		  return formatNumber(parseFloat(data.plan_money),2,1)
            	  }
              },
              { display: '已付金额', name: 'payed_money', align: 'right', width: '150px',editable: false,
            	  render:function(ui){
            		  var data = ui.rowData
            		  return formatNumber(parseFloat(data.payed_money),2,1)
            	  }
              },
              { display: '未付金额', name: 'nopay_money', align: 'right', width: '150px',editable: false,
            	  render:function(ui){
            		  var data = ui.rowData
            		  return formatNumber(parseFloat(data.plan_money) - parseFloat(data.payed_money),2,1)
            	  }
              },
         ];
         var paramObj = {
         	editable: true,
         	height: '300',
         	width:'100%',
         	checkbox: true,
         	usePager: false,
             dataModel: {
	             url: 'queryPactPlanFKHT.do?isCheck=false&pact_code='+$("#pact_code").val(),
             },
             columns: columns,
             editorEnd : editorEndForPlan,
             toolbar: {
                 items: [
                	 { type: 'button', label: '增加', listeners: [{ click: addplan }], icon: 'add' },
                    /*  { type: 'button', label: '保存',  listeners: [{ click: saveplan }],  icon: 'save' }, */
                     { type: 'button', label: '删除',  listeners: [{ click: removeplan }], icon: 'del' }
                 ]
             }
         };
        palnGrid = $("#pactplan").etGrid(paramObj);
     };
     
     // 用于动态生成标的物中的金额
     function editorEndForPlan(e, ui){
    	 if(ui.colIndx == 1){
   			var pay_id  = parseFloat(ui.rowData.pay_id).toString();
   			if (pay_id.length > 4) {
   				$.etDialog.error('付款期号的长度为1-4位');
   				ui.rowData.pay_id = pay_id.substr(0,4);
   				return;
   			}
    	 }
    	 if(ui.colIndx == 6){
    		var data = ui.rowData;
     		if(!data.plan_money){
     			 data.plan_money = 0;
     		}else{
     			var plan_money = parseFloat(data.plan_money);
     			if (!plan_money) {plan_money = 0;}
     			ui.rowData.plan_money = parseFloat(plan_money)
     		}
     		ui.rowData.plan_money  = parseInt(data.rate)* parseFloat($("#pact_money").val().replace(/,/g,""))/100 ;
     		palnGrid.refreshCell(ui.rowIndx, 'plan_money', false);
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
                     url: 'deletePactPlanFKHT.do',
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
         	height: '300',
         	width:'100%',
         	checkbox: true,
         	usePager: false,
            dataModel: {url: '../../pactdoc/queryPactDocFKHT.do?isCheck=false&pact_code='+$("#pact_code").val()},
            columns: docColumns,
            toolbar: {
                items: [
               	 	{ type: 'button', label: '增加', listeners: [{ click: addDoc }], icon: 'add' },
                   /*  { type: 'button', label: '保存',  listeners: [{ click: saveDoc }],  icon: 'save' }, */
                    { type: 'button', label: '删除',  listeners: [{ click:  delDoc }],  icon: 'del' }
                ]
            }
         };
         docGrid =  $("#pactdoc").etGrid(paramObj);
     };
     
	function addSub() { subGrid.addRow({"source":3});}
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
                 if(typeof(rowdata.dept_no) != "undefined") { //Modified By CXD 20200423
                	 if(rowdata.dept_no.toString().indexOf("@") !== -1){
		            	 rowdata.dept_id = rowdata.dept_no.split("@")[0];
			             rowdata.dept_no = rowdata.dept_no.split("@")[1]; 
		             }
			 	 }
                 param.push(rowdata);
             });
             $.etDialog.confirm('确定删除?', function () {
                 ajaxPostData({
                     url: '../../pactdoc/deletePactDocFKHT.do',
                     data: {mapVo: JSON.stringify(param) },
                     success: function () {docGrid.deleteRows(data);}
                 })
             });
         }
	}
	
	$(function(){
		 $("#layout1").ligerLayout({ 
			 rightWidth: 400,
			 isRightCollapse: true , //默认隐藏右侧资金来源  2020-04-08
			 heightDiff:150,
			 onRightToggle : function() {
				 subGrid._onResize();
				 sourceGrid._onResize();
			 },
			 //每调整左边树宽度大小即刷新一次表格，以免结构混乱
			 onEndResize : function(a, b) {subGrid._onResize();}
		});

		initSubGrid();
		loadHeadItem();
		initfrom();
		initSelect();
		toolBarSet();

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
			defaultDate: true,
			todayButton: false,
			onChange: function (date) {
				startpicker.setValue($("#sign_date").val());//modifyByWjt合同开始日期默认等于签订日期
			}
		});
		is_bid = $('#is_bid').etCheck({
			onChange: function(state){
				toolBarSet();
				bid_code_select.enabled();
				if(is_bid.checked){
					// 情形1：是否招标未选中，招标编号下拉框禁用，组织形式和采购方式下拉框正常使用；
	      			// 情形2：是否招标选中，招标编号下拉框可用，但未选择招标编号，组织形式和采购方式下拉框可用
	      			// 情形3：是否招标选中，招标编号下拉框可用，且选择招标编号，组织形式和采购方式分别取招标信息中的组织形式和招标方式，禁止自行选择。 20200327 cxd
	      			$("#bid_code").attr("disabled",false); 
	      			organ_type.setValue("");               
	      			buy_type.setValue("");                 
				 }else{
					bid_code_select.disabled();  
	      			bid_code_select.setValue("");
	      			organ_type.setValue("");     
	      			buy_type.setValue("");       
	      			organ_type.enabled();        
	      			buy_type.enabled();          
				 }
			}
		});
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
	
	function toolBarSet(){// 引用定标 引用资产购置申请  按钮 启用/禁用设置
		if(attr_code =='01' || attr_code == '02'){
			subGrid.toolbarManager.setEnabled("impAssApply");
			if(is_bid.status == "checked"){
				subGrid.toolbarManager.setEnabled("impBid");
			}else{
				subGrid.toolbarManager.setDisabled("impBid");
			}
		}else{
			subGrid.toolbarManager.setDisabled("impAssApply");
			subGrid.toolbarManager.setDisabled("impBid");
		}
		
	}

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
			<td class="ipt"><input id="pact_code" type="text" disabled="disabled" style="background-color: #EAEAEA" placeholder="系统生成"/></td>
			<td class="label no-empty" style="width: 100px;">合同类型：</td>
			<td class="ipt"><select id="pact_type_code" style="width: 180px;"></select> </td>
			<td class="label no-empty" style="width: 100px;">合同状态：</td>
			<td class="ipt"><select id="state_code" style="width: 180px;"></select></td>
			<td class="label no-empty" style="width: 100px;">合同名称：</td>
			<td class="ipt" ><input id="pact_name" type="text" style="width: 180px;" /></td>
		</tr>
		<tr>
			<td class="label" style="width: 100px;">原始编号：</td>
			<td class="ipt"><input id="original_code" type="text"/></td>
			<td class="label" style="width: 100px;">主合同：</td>
			<td class="ipt"><select id="master_pact_code" style="width: 180px;" ></select></td>
			<td class="label no-empty" style="width: 100px;">签订日期：</td>
			<td class="ipt"><input id="sign_date" type="text"/></td>
			<td class="label no-empty" style="width: 100px;">预交货期限(月)：</td>
			<td class="ipt"><input id="delivery_term" type="text"/></td>
		</tr>
		<tr id="ht_sign_point" style="height: 16px;">
			<td></td><td></td><td></td><td></td><td></td>
			<td class="ipt" style="color: red; white-space: nowrap; position: relative;"><div style="top: -3px; position: absolute;">必须小于合同类型启用日期</div></td> 
		</tr>
		<tr id="xy_sign_point" style="height: 16px;">
			<td></td><td></td><td></td><td></td><td></td>
			<td class="ipt" style="color: red; white-space: nowrap; position: relative;"><div style="top: -3px; position: absolute;">必须大于等于合同类型启用日期</div></td> 
		</tr>
		<tr>
			<td class="label" style="width: 100px;">项目名称：</td>
			<td class="ipt"><input id="proj_id" type="text" style="width: 180px;" /></td>
			<td class="label no-empty" style="width: 100px;">签约单位：</td>
			<td class="ipt"><input id="hos_name" type="text" value="${hos_name }" disabled="disabled"/></td>
			<td class="label no-empty" style="width: 100px;">签订科室：</td>
			<td class="ipt"><select id="dept_no" style="width: 180px;"></select></td>
			<td class="label no-empty" style="width: 100px;">负责人：</td>
			<td class="ipt"><select id="emp_id" style="width: 180px;"></select> </td>
		</tr>
		<tr>
			<td class="label no-empty" style="width: 100px;">供应商：</td>
			<td class="ipt"><select id="sup_no" style="width: 180px;"></select></td>
			<td class="label no-empty" style="width: 100px;">对方负责人：</td>
			<td class="ipt"><input id="opp_emp" type="text" /></td>
			<td class="label" style="width: 120px;">对方联系电话：</td>
			<td class="ipt"><input id="opp_phone" type="text" /></td>
			<td class="label" style="width: 100px;">服务商：</td>
			<td class="ipt"><input id="server" type="text" style="width: 180px;" /></td>
		</tr>
		<tr>
			<td class="label" style="width: 100px;">服务联系人：</td>
			<td class="ipt"><input id="ser_emp" type="text" /></td>
			<td class="label" style="width: 120px;">服务电话：</td>
			<td class="ipt"><input id="ser_phone" type="text" /></td>
			<td class="label no-empty" style="width: 100px;">贸易类别：</td>
			<td class="ipt"><select id="trade_type" style="width: 180px;"></select></td>
			<td class="label no-empty" style="width: 100px;">币种：</td>
			<td class="ipt"><select id="curr_code" style="width: 180px;"></select></td>
		</tr>
		<tr>
			<td class="label" style="width: 120px;">合同金额(外币)：</td>
			<td class="ipt"><input id="pact_money_w" type="text" disabled="disabled" style="background-color: #EAEAEA" onchange="formatMoney(this.id)"/></td>
			<td class="label no-empty" style="width: 120px;">合同开始日期：</td>
			<td class="ipt"><input id="start_date" type="text" style="background-color: #EAEAEA"/></td>
			<td class="label no-empty" style="width: 120px;">合同截止日期：</td>
			<td class="ipt"><input id="end_date" type="text" /></td>
			<td class="label no-empty" style="width: 120px;">合同金额：</td>
			<td class="ipt"><input id="pact_money" type="text" disabled="disabled" value="0" style="background-color: #EAEAEA;text-align: right;"/></td>
		</tr>
		<tr>
			<td class="label"><input id="is_bid" type="checkbox" />是否招标</td>
			<td class="ipt"><select id="bid_code" disabled="disabled" style="width: 180px;"></select></td>
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
			<td class="ipt"><input id="deposit_money" type="text" disabled="disabled" style="background-color: #EAEAEA" onchange="formatMoney(this.id)"/></td>
		</tr>
		<tr>
			<td class="label" style="width: 100px;">服务条款：</td>
			<td class="ipt" ><textarea id="cont_term1" style="resize:none;width: 180px;"></textarea></td>
			<td class="label" style="width: 100px;">付款条款：</td>
			<td class="ipt" ><textarea id="cont_term2" style="resize:none;width: 180px;"></textarea></td>
			<td class="label" style="width: 100px;">验收标准：</td>
			<td class="ipt" ><textarea id="cont_term3" style="resize:none;width: 180px;"></textarea></td>
		</tr>
		<tr>
			<td class="label" style="width: 100px;">违约处理：</td>
			<td class="ipt" ><textarea id="cont_term4" style="resize:none;width: 180px;"></textarea></td>
			<td class="label" style="width: 100px;">交货条款：</td>
			<td class="ipt" ><textarea id="cont_term5" style="resize:none;width: 180px;"></textarea></td>
			<td class="label" style="width: 100px;">质保条款：</td>
			<td class="ipt"><textarea id="cont_term6" style="resize:none;width: 180px;"></textarea></td>
		</tr>
		<tr>
			<td class="label" style="width: 100px;">备注：</td>
			<td class="ipt" colspan="5"><textarea id="note" style="resize:none;width: 95.5%;"></textarea></td>
		</tr>
	</table>
		<div id="etTab">
		  <div title="标的物" tabid="0" id="layout1" >
	  		<div id="subGrid" position="center" title='明细'></div>
		 	<div id="sourceGrid" position="right" title='资金来源'></div>
		  </div>
		  <div title="付款计划" id='1'>
			 <div id="pactplan"></div>
		  </div>
		  <div id="2" title="文档管理" >
		    <div id="pactdoc"></div>
		  </div>
		</div>
	<div class="button-group">
	  <button id="save">保存</button>
	  <button id="cancle">取消</button>
	</div>
	
</body>

</html>

