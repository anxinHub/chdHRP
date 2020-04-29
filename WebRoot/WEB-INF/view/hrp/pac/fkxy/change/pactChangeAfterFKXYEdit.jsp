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
var etTab,subGrid,docGrid,dept_no,emp_id,sup_no,pact_type_code,subject_type,checkbox;
var deptSoues = [];   
var projSoues = [];   
var empSoues = [];    
var typeSoues = [];    
var stateSoues = [];    
var is_bid,is_change ;
var is_init = '${entity.is_init}';
var pact_name = '${entity.pact_name }';
var is_total_cont;
var is_price_cont;
	var save = function() {
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
				{el : $("#start_date"),required : true},
				{el : $("#end_date"),required : true}
				]
		});
		if(!formValidate.test()){return;};
		
		if(subGrid){
	       	 var sub = [];
			 var data = subGrid.getAllData();
			  if (data != null && data.length != 0) {
	        	  var err ;
	              $(data).each(function () {
	                  var rowdata = this;
	                  if(!rowdata.subject_id){ err ="标的物不能为空";return ;}
	                  //if(!rowdata.price){ err ="单价不能为空" ;return ;}
	                  if(is_total_cont.status=="checked"){
		                	if(!$("#pact_money").val())
		    				{ err ="总金额不能为空" ;return ;}
		                }
		                //console.log(is_price_cont);
		                if(is_price_cont.status=="checked"){
		                	if(!rowdata.price)
		    				{ err ="单价不能为空" ;return ;}
		                }	
	                  rowdata.group_id = ${group_id};
	                  rowdata.hos_id = ${hos_id};
	                  rowdata.copy_code = '${copy_code}';
	                  rowdata.pact_code =  $("#pact_code").val();
	                  rowdata.subject_no =  rowdata.subject_id;
	                  rowdata.change_code =  $("#change_code").val();
	                  rowdata.item_name =  rowdata.item_name;
	                  rowdata.subject_type =  subject_type;
	                  sub.push(rowdata);
	              });
	              if(err){$.etDialog.error(err);return;}
	         }
		}
		
		ajaxPostData({
			url : 'updatePactFKXYC.do?isCheck=false',
			data : {
				pact_code : "${entity.pact_code}",
				change_code : "${entity.change_code}",
				pact_type_code : $("#pact_type_code").val(),
				state_code : $("#state_code").val(),
				pact_name : $("#pact_name").val(),
				original_code : $("#original_code").val(),
				master_pact_code : $("#master_pact_code").val(),
				sign_date : $("#sign_date").val(),
				dept_id : $("#dept_no").val().split("@")[0],
				dept_no : $("#dept_no").val().split("@")[1],
				emp_id : $("#emp_id").val(),
				sup_no : $("#sup_no").val(),
				sup_id : $("#sup_no").val(),
				opp_emp : $("#opp_emp").val(),
				opp_phone : $("#opp_phone").val(),
				pact_intro : $("#pact_intro").val(),
				trade_type : $("#trade_type").val(),
				start_date : $("#start_date").val(),
				end_date : $("#end_date").val(),
				is_bid : is_bid.status == "checked" ? 1 : 0,
				is_total_cont : is_total_cont.status == "checked" ? 1 : 0,
				is_price_cont : is_price_cont.status == "checked" ? 1 : 0,
				organ_type : $("#organ_type").val(),
				buy_type : $("#buy_type").val(),
				is_init : ${entity.is_init },
				proj_id : $("#proj_id").val(),
				
				cont_term1:$("#cont_term1").val(),
				cont_term2:$("#cont_term2").val(),
				cont_term3:$("#cont_term3").val(),
				cont_term4:$("#cont_term4").val(),
				cont_term5:$("#cont_term5").val(),
				cont_term6:$("#cont_term6").val(),
				note : $("#note").val(),
				pact_money : $("#pact_money").val(),
				sub : JSON.stringify(sub)
			},
			success : function(data) {
				var curIndex = parent.$.etDialog.getFrameIndex(window.name);
		        parent.$.etDialog.close(curIndex); 
		        var parentFrameName = parent.$.etDialog.getFrameName('edit');
		        var parentWindow = parent.window[parentFrameName];
			    parentWindow.query(); 
			},
			delayCallback : true
		})
	}

	 var initSelect=  function(){
	      	ajaxPostData({
	      		url: '../../basicset/select/queryPactTypeFKXYSelect.do?isCheck=false',
				  success: function (result) {
					 pact_type_code = $("#pact_type_code").etSelect({
						 defaultValue: "${entity.pact_type_code}",
						 options:result,
						 onItemAdd:function(value, $item){
							 for(var i = 0;i<result.length;i++){
								 if(value == result[i].id){
									 subject_type = result[i].subject_type;
								 }
							 }
				      	 }  
					 });
				  },
			});
	      	ajaxPostData({
	   		 url: '../../basicset/select/queryPactStateSelect.do?isCheck=false',
				  success: function (result) {
					  if(${entity.is_init} == 1){
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
					state_code = $("#state_code").etSelect({options: result,defaultValue: "${entity.state_code}",});
				  },
			});
	        var def = "none";if("${entity.trade_type}" != ""){ def = "${entity.trade_type}";}
	      	trade_type = $("#trade_type").etSelect({url: '../../basicset/select/queryDictSelect.do?isCheck=false&f_code=TRADE_TYPE',defaultValue: def, });
	        var def = "none";if("${entity.organ_type}" != ""){ def = "${entity.organ_type}";}
	        organ_type = $("#organ_type").etSelect({ url: '../../basicset/select/queryDictSelect.do?isCheck=false&f_code=ORGAN_TYPE', defaultValue: def,});
	      	 var def = "none";if("${entity.buy_type}" != ""){ def = "${entity.buy_type}";}
	        buy_type = $("#buy_type").etSelect({ url: '../../basicset/select/queryDictSelect.do?isCheck=false&f_code=BUY_TYPE',  defaultValue: def });
	      	 var def = "none";if("${entity.sup_no}" != ""){ def = "${entity.sup_no}";}
	        sup_no = $("#sup_no").etSelect({ url: '../../basicset/select/queryHosSupDictSelect.do?isCheck=false', defaultValue: def});
	      	 var def = "none";if("${entity.proj_id}" != ""){ def = "${entity.proj_id}";}
	        proj_id = $("#proj_id").etSelect({ url: '../../basicset/select/queryHosProjDictSelect.do?isCheck=false', defaultValue:def});
	        
	    	ajaxPostData({
	    		 url: '../../basicset/select/queryHosEmpDictSelect.do?isCheck=false',
				 success: function (result) {
					emp_id = $("#emp_id").etSelect({options:result,defaultValue: "${entity.emp_id}",});
		            empSoues = result;
				 },
			});
	    	ajaxPostData({
	    		 url: '../../basicset/select/queryDeptSelectDict.do?isCheck=false',
				 success: function (result) {
					 dept_no = $("#dept_no").etSelect({options:result,defaultValue: ${entity.dept_id}+'@'+${entity.dept_no},});
					 deptSoues = result;
					 }
	    		 });
	      	ajaxPostData({
	   		 	url: '../../basicset/select/queryPactFKXYSelect.do?isCheck=false&is_init=${entity.is_init}',
				success: function (result) {
					var master = "none";
					if("${entity.master_pact_code}" != ""){
						master = "${entity.master_pact_code}";
					}
				  master_pact_code = $("#master_pact_code").etSelect({
	                 	options:result,
	                 	defaultValue: master,
	                 	onChange:function(value){
	                 		if(!value){return;}
	                 		ajaxPostData({
	                		 	url: 'queryPactMainFKXYForMaster.do?isCheck=false&is_init=${entity.is_init}',
	                		 	data:{
	                		 		pact_code:value
	                		 	},
	                			success: function (result) {
	                				signpicker.setValue(result.sign_date);
									dept_no.setValue(result.dept_no);
									emp_id.setValue(result.emp_id);
									sup_no.setValue(result.sup_no);
									$("#opp_emp").val(result.opp_emp);
									$("#opp_phone").val(result.opp_phone);
	                			},
	                		});
	                 	}
	              });
				 },
			});
	      	ajaxPostData({url: '../../basicset/select/queryHosProjDictSelect.do?isCheck=false',success: function (result) {projSoues = result;}});
	      	ajaxPostData({url: '../../basicset/select/queryPactDocTypeSelect.do?isCheck=false' ,success: function (result) {typeSoues= result;}});
	      }
    
     var selectId ;
     var without_id = "";
	 var initSubGrid = function () {
		 pact_code 
         var columns = [
              { display: '标的物名称', name: 'subject_name',width: '160px',
            	  editor: {
          		     type: 'select', 
          		     keyField: 'subject_id',
          		     url:'../../basicset/select/querySubjectSelect.do?isCheck=false&without_id='+without_id+'&type='+subject_type,
	          		 change: function(ui){
         		   		ajaxPostData({
         		   			url: '../../basicset/select/querySubjectSelect.do?isCheck=false&id='+ui.subject_id+'&type='+subject_type,
         		   			success: function (result) {
	         		   			ui.item_spec = result[0].item_spec;
	      		   				ui.item_model = result[0].item_model;
	      		   				ui.fac_name = result[0].fac_name;
	      		   				ui.fac_id = result[0].fac_id;
	      		   				ui.item_name = pact_name;
	      		   				ui.fac_no = result[0].fac_no;
	      		   				ui.unit_code = result[0].unit_code;
	      		   				ui.unit_name = result[0].unit_name;
	      		   				subGrid.refreshCell(ui._rowIndx, 'item_model', false);
	      		   				subGrid.refreshCell(ui._rowIndx, 'item_spec', false);
	      		   				subGrid.refreshCell(ui._rowIndx, 'fac_name', false);
	      		   				subGrid.refreshCell(ui._rowIndx, 'unit_name', false);
         		   			}
         		   		});
         		   		without_id += ui.subject_id + ","
	         		  },
          		 },
              },
              { display: '通用名', name: 'item_name', width: '120px',editable: true},
              { display: '规格', name: 'item_spec', width: '120px',editable: false},
              { display: '型号', name: 'item_model', width: '120px',editable: false},
              { display: '生产厂家', name: 'fac_name', width: '120px',editable: false},
              { display: '计量单位', name: 'unit_name', width: '120px',editable: false},
              { display: '单价', name: 'price', width: '120px',align: "right"},
              { display: '备注', name: 'note',width: '120px',}
         ];
         var paramObj = {
        	 editable: true,
             height: '200',
             width:'100%',
             checkbox: true,
             usePager: false,
             dataModel: {
	             url: 'queryPactDetFKXYC.do?isCheck=false&pact_code='+$("#pact_code").val()+'&change_code='+$("#change_code").val()
             },
             columns: columns,
             editorEnd : editorEnd,
             load : function(){
          		var data = subGrid.getAllData();
          		$(data).each(function () {
                  	var rowdata = this;
                  	if(rowdata.subject_id){
                  		without_id += rowdata.subject_id + ',';
          	   		}
             	});
           	},
             toolbar: {
                 items: [
                	 { type: 'button', label: '增加', listeners: [{ click: addSub }], icon: 'add' },
                     { type: 'button', label: '删除',  listeners: [{ click:  removeSub}],  icon: 'del' },
                  //   { type: 'button', label: '添加材料字典', listeners: [{ click: addMatInv }], icon: 'save' },
                   //  { type: 'button', label: '添加药品字典',  listeners: [{ click: addMedInv }],  icon: 'save' },
                  // { type: 'button', label: '添加资产字典',  listeners: [{ click:  }], icon: 'save' },
                  //   { type: 'button', label: '添加资产卡片', /* listeners: [{ click:  }], */ icon: 'save' },
                     { type: 'button', label: '添加其他标的物',  listeners: [{ click: addElseSub }],  icon: 'save' }
                 ]
             }
         };
         
       	subGrid = $("#subGrid").etGrid(paramObj);
       	
     };
     
     // 用于动态生成标的物中的金额
     function editorEnd(event, ui){
    	 if( ui.colIndx == 7){
    		 var data = ui.rowData;
    		if(!data.price){
    			 data.price = 0;
    		}else{
    			var price = parseFloat(data.price);
    			if (!price) {price = 0;}
    			ui.rowData.price = parseFloat(price)
    		}
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
   		$("#pact_money").val(money);
	 }
     function removeSub(){
    	 if("1" != "${entity.state}"){
    		 return;
    	 }
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
                 rowdata.change_code =  "${entity.change_code}";
                 param.push(rowdata);
             });
             $.etDialog.confirm('确定删除?', function () {
                 ajaxPostData({
                     url: 'deletePactDetFKXYC.do?isCheck=false',
                     data: {mapVo: JSON.stringify(param)},
                     success: function () {subGrid.deleteRows(data);}
                 })
             });
         }
     }
 	function addElseSub(){
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
     
	function addSub() {
		if("1" == "${entity.state}"){
			subGrid.addRow();
		}
	}
	
	$(function(){
    	initSelect();
   		initfrom();
   		setTimeout(function(){
   			initSubGrid();
   		},500);
   		
   		etTab = $("#etTab").etTab({
   		});
    	
   		$("#save").on("click", function () {
			save();
		})
		
		
		$("#cancle").on("click", function () {
			var curIndex = parent.$.etDialog.getFrameIndex(window.name);
	        parent.$.etDialog.close(curIndex); 
		})
	})
	window.onload = function(){
		if("1" != "${entity.state}"){
			buy_type.disabled();
			sup_no.disabled();
			organ_type.disabled();
			proj_id.disabled();
			trade_type.disabled();
			$("input").attr("disabled" , "disabled");
			$("input").attr("style","background-color:#EAEAEA");
			$("select").attr("disabled" , "disabled");
			$("#note").attr("disabled" , "disabled");
			$("#note").attr("style","background-color:#EAEAEA");
			$("#save").attr("disabled" , "disabled");
			$("#save").attr("style","background-color:#EAEAEA");
			$("cont_term1").attr("disabled" , "disabled");
			$("#cont_term1").attr("style","background-color:#EAEAEA");
			$("#cont_term2").attr("disabled" , "disabled");
			$("#cont_term2").attr("style","background-color:#EAEAEA");
			$("#cont_term3").attr("disabled" , "disabled");
			$("#cont_term3").attr("style","background-color:#EAEAEA");
			$("#cont_term4").attr("disabled" , "disabled");
			$("#cont_term4").attr("style","background-color:#EAEAEA");
			$("#cont_term5").attr("disabled" , "disabled");
			$("#cont_term5").attr("style","background-color:#EAEAEA");
			$("#cont_term6").attr("disabled" , "disabled");
			$("#cont_term6").attr("style","background-color:#EAEAEA");
		}
	} 
	
	  //日期
	var initfrom = function(){
		startpicker = $("#start_date").etDatepicker({
			defaultDate: true,
			minDate : '${sign_date}',
		  	onChange: function (date) {
		  		var end = endpicker.getValue();
		  		if(end < date){
		  			endpicker.setValue(end);
		  		}
		  	}
		});
		endpicker = $("#end_date").etDatepicker({
			defaultDate: true,
			minDate : '${sign_date}',
		  	onChange: function (date) {
		  		var start = startpicker.getValue();
		  		if(start > date){
		  			endpicker.setValue(start);
		  		}
		  	}
		});
		signpicker = $("#sign_date").etDatepicker({
			defaultDate: '${sign_date}',
		});
		signpicker.disabled();
		
		is_change = $('#is_change').etCheck();
		is_bid = $('#is_bid').etCheck();
		if(${entity.is_bid} == 1){
			is_bid.setCheck();
		}
		
		is_total_cont = $('#is_total_cont').etCheck();
		if('${entity.is_total_cont}' == '1'){
			is_total_cont.setCheck();
		}
		is_price_cont = $('#is_price_cont').etCheck();
		if('${entity.is_price_cont}' == '1'){
			is_price_cont.setCheck();
		}
	};
</script>
</head>

<body style="overflow: scroll; ">
	<table class="table-layout">
		<tr>
			<td class="label no-empty" style="width: 100px;">协议编号：</td>
			<td class="ipt"><input id="pact_code" type="text" disabled="disabled" value="${entity.pact_code }"/></td>
			<td class="label no-empty" style="width: 100px;">变更编号：</td>
			<td class="ipt"><input id="change_code" type="text" disabled="disabled" value="${entity.change_code }"/></td>
			<td class="label no-empty" style="width: 100px;">协议类型：</td>
			<td class="ipt"><select id="pact_type_code" style="width: 180px;" disabled="disabled"></select> </td>
			<td class="label no-empty" style="width: 100px;">协议状态：</td>
			<td class="ipt"><select id="state_code" style="width: 180px;" disabled="disabled"></select></td>
		</tr>
		<tr>
			<td class="label no-empty" style="width: 100px;">协议名称：</td>
			<td class="ipt"><input id="pact_name" type="text" style="width: 180px;" value="${entity.pact_name }"/></td>
			<td class="label" style="width: 100px;">原始编号：</td>
			<td class="ipt"><input id="original_code" type="text" value="${entity.original_code }"/></td>
			<td class="label" style="width: 100px;">关联主协议：</td>
			<td class="ipt"><select id="master_pact_code" style="width: 180px;" ></select></td>
			<td class="label no-empty" style="width: 100px;">签订日期：</td>
			<td class="ipt"><input id="sign_date" type="text"/></td>
		</tr>
		<tr>
			<td class="label no-empty" style="width: 100px;">签约单位：</td>
			<td class="ipt"><input id="hos_name" type="text" value="${hos_name}" disabled="disabled"/></td>
			<td class="label no-empty" style="width: 100px;">签订科室：</td>
			<td class="ipt"><select id="dept_no" style="width: 180px;"></select></td>
			<td class="label no-empty" style="width: 100px;">负责人：</td>
			<td class="ipt"><select id="emp_id" style="width: 180px;"></select> </td>
			<td class="label no-empty" style="width: 100px;">供应商：</td>
			<td class="ipt"><select id="sup_no" style="width: 180px;"></select></td>
			
		</tr>
		<tr>
			<td class="label no-empty" style="width: 100px;">对方负责人：</td>
			<td class="ipt"><input id="opp_emp" type="text" value="${entity.opp_emp }"/></td>
			<td class="label" style="width: 120px;">对方联系电话：</td>
			<td class="ipt"><input id="opp_phone" type="text" value="${entity.opp_phone }"/></td>
			<td class="label" style="width: 100px;">项目名称：</td>
			<td class="ipt"><select id="proj_id" type="text" style="width: 180px;" /></td>
			<td class="label no-empty" style="width: 100px;">贸易类别：</td>
			<td class="ipt"><select id="trade_type" style="width: 180px;"></select></td>
			
		</tr>
		<tr>
			<td class="label" style="width: 120px;">组织方式：</td>
			<td class="ipt"><select id="organ_type" style="width: 180px;"></select></td>
			<td class="label no-empty" style="width: 120px;">协议开始日期：</td>
			<td class="ipt"><input id="start_date" type="text" /></td>
			<td class="label no-empty" style="width: 120px;">协议截止日期：</td>
			<td class="ipt"><input id="end_date" type="text" /></td>
			<td class="label" style="width: 120px;">采购方式：</td>
			<td class="ipt"><select id="buy_type" style="width: 180px;"></select></td>
		</tr>
		<tr>
			<td class="label" style="width: 100px;">服务条款：</td>
			<td class="ipt" ><textarea id="cont_term1" style="resize:none;width: 180px;">${entity.cont_term1}</textarea></td>
			<td class="label" style="width: 100px;">付款条款：</td>
			<td class="ipt" ><textarea id="cont_term2" style="resize:none;width: 180px;">${entity.cont_term2}</textarea></td>
			<td class="label" style="width: 100px;">验收标准：</td>
			<td class="ipt" ><textarea id="cont_term3" style="resize:none;width: 180px;">${entity.cont_term3}</textarea></td>
			<td class="label" style="width: 120px;">协议总金额：</td>
			<td class="ipt"><input id="pact_money" type="text" value="${entity.pact_money}"/></td>
		</tr>
		<tr>
			<td class="label" style="width: 100px;">违约处理：</td>
			<td class="ipt" ><textarea id="cont_term4" style="resize:none;width: 180px;">${entity.cont_term4}</textarea></td>
			<td class="label" style="width: 100px;">交货条款：</td>
			<td class="ipt" ><textarea id="cont_term5" style="resize:none;width: 180px;">${entity.cont_term5}</textarea></td>
			<td class="label" style="width: 100px;">质保条款：</td>
			<td class="ipt"><textarea id="cont_term6" style="resize:none;width: 180px;">${entity.cont_term6}</textarea></td>
			<td><input id="is_total_cont" type="checkbox" />总额控制</td>
			<td><input id="is_price_cont" type="checkbox" />单价控制</td>
		</tr>
		<tr>
			<td class="label" style="width: 100px;">备注：</td>
			<td class="ipt" colspan="5"><textarea id="note" style="resize:none;width: 96%;">${entity.note}</textarea></td>
			<td></td>
			<td><input id="is_bid" type="checkbox" />是否经过招标</td>
			
		</tr>
	</table>
		<div id="etTab">
		  <div title="标的物" tabid="0">
			 <div id="subGrid"></div>
		  </div>
		</div>
	<div class="button-group">
	  <button id="save">保存</button>
	  <button id="cancle">取消</button>
	</div>
</body>

</html>

