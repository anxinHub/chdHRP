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
var etTab,subGrid,docGrid,dept_no,emp_id,sup_no,pact_type_code,change_reason,subject_type,change_reason;
var deptSoues = [];   
var projSoues = [];   
var empSoues = [];    
var typeSoues = [];    
var stateSoues = [];    
var is_bid,is_change ;
var is_init = '${entity.is_init}';
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
	                  rowdata.item_name =  rowdata.item_name;
	                  rowdata.subject_type =  subject_type;
	                  sub.push(rowdata);
	              });
	              if(err){$.etDialog.error(err);return;}
	         }
			 else
		     {
		     	$.etDialog.error("标的物不能为空！") ;return ;
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
		             if(!rowdata.file_path){err = "文件不能为空";return ;}
		             rowdata.group_id = ${group_id};
		             rowdata.hos_id = ${hos_id};
		             rowdata.copy_code = '${copy_code}';
		             rowdata.pact_code = "${entity.pact_code }";
		             if(rowdata.dept_no.toString().indexOf("@") !== -1){
		            	 rowdata.dept_id = rowdata.dept_no.split("@")[0];
			             rowdata.dept_no = rowdata.dept_no.split("@")[1]; 
		             }else{
		            	 rowdata.dept_id = rowdata.dept_id;
			             rowdata.dept_no = rowdata.dept_no; 
		             }
		             doc.push(rowdata);
		         });
				 if(err.length != 0){$.etDialog.error(err) ;return ;}
			}
		}
		if((is_total_cont.status == "unchecked" && is_price_cont.status == "unchecked") || (is_total_cont.status == "" && is_price_cont.status == "")
				||(is_total_cont.status == "unchecked" && is_price_cont.status == "") ||(is_total_cont.status == "" && is_price_cont.status == "unchecked")){
			$.etDialog.error("请选择协议控制方式") ;
			return ;
		}
		ajaxPostData({
			url : 'updatePactFKXY.do',
			data : {
				pact_code : "${entity.pact_code}",
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
				pact_money : $("#pact_money").val().replace(/,/g,""),
				change_reason : change_reason,
				sub : JSON.stringify(sub),
				doc : JSON.stringify(doc)
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
		 if( ${entity.is_init} == 1){
	    		$("#ht_sign_point").show();
				 $("#xy_sign_point").hide();
	    	}
	    	else{
	    		$("#ht_sign_point").hide();
				 $("#xy_sign_point").show();
	    	}
	      	ajaxPostData({
	      		url: '../../../basicset/select/queryPactTypeFKXYSelect.do?isCheck=false&FKXY_Attr='+"01",
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
	   		 url: '../../../basicset/select/queryPactStateSelect.do?isCheck=false',
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
	      	trade_type = $("#trade_type").etSelect({url: '../../../basicset/select/queryDictSelect.do?isCheck=false&f_code=TRADE_TYPE',defaultValue: def, });
	        var def = "none";if("${entity.organ_type}" != ""){ def = "${entity.organ_type}";}
	        organ_type = $("#organ_type").etSelect({ url: '../../../basicset/select/queryDictSelect.do?isCheck=false&f_code=ORGAN_TYPE', defaultValue: def,});
	      	 var def = "none";if("${entity.buy_type}" != ""){ def = "${entity.buy_type}";}
	        buy_type = $("#buy_type").etSelect({ url: '../../../basicset/select/queryDictSelect.do?isCheck=false&f_code=BUY_TYPE',  defaultValue: def });
	      	 var def = "none";if("${entity.sup_no}" != ""){ def = "${entity.sup_no}";}
	        sup_no = $("#sup_no").etSelect({ url: '../../../basicset/select/queryHosSupDictSelect.do?isCheck=false', defaultValue: def});
	      	 var def = "none";if("${entity.proj_id}" != ""){ def = "${entity.proj_id}";}
	        proj_id = $("#proj_id").etSelect({ url: '../../../basicset/select/queryHosProjDictSelect.do?isCheck=false', defaultValue:def});
	        
	    	ajaxPostData({
	    		 url: '../../../basicset/select/queryHosEmpDictSelect.do?isCheck=false',
				 success: function (result) {
					emp_id = $("#emp_id").etSelect({options:result,defaultValue: "${entity.emp_id}",});
		            empSoues = result;
				 },
			});
	    	ajaxPostData({
	    		 url: '../../../basicset/select/queryDeptSelectDict.do?isCheck=false',
				 success: function (result) {
					 dept_no = $("#dept_no").etSelect({backEndSearch:false,options:result,defaultValue: ${entity.dept_id}+'@'+${entity.dept_no},});
					 deptSoues = result;
					 }
	    		 });
	      	ajaxPostData({
	   		 	url: '../../../basicset/select/queryPactFKXYSelect.do?isCheck=false&is_init=${entity.is_init}',
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
	      	ajaxPostData({url: '../../../basicset/select/queryHosProjDictSelect.do?isCheck=false',success: function (result) {projSoues = result;}});
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
          		     url:'../../../basicset/select/querySubjectSelect.do?isCheck=false&without_id='+without_id+'&type='+subject_type,
	          		 change: function(ui){
         		   		ajaxPostData({
         		   			url: '../../../basicset/select/querySubjectSelect.do?isCheck=false&id='+ui.subject_id+'&type='+subject_type,
         		   			success: function (result) {
	         		   			ui.item_spec = result[0].item_spec;
	      		   				ui.item_model = result[0].item_model;
	      		   				ui.fac_name = result[0].fac_name;
	      		   				ui.item_name = result[0].text;
	      		   				ui.fac_id = result[0].fac_id;
	      		   				ui.fac_no = result[0].fac_no;
	      		   				ui.unit_code = result[0].unit_code;
	      		   				ui.unit_name = result[0].unit_name;
								ui.subject_code=result[0].item_code;
	      		   				subGrid.refreshCell(ui._rowIndx, 'item_model', false);
	      		   				subGrid.refreshCell(ui._rowIndx, 'item_name', false);
	      		   				subGrid.refreshCell(ui._rowIndx, 'item_spec', false);
	      		   				subGrid.refreshCell(ui._rowIndx, 'fac_name', false);
	      		   				subGrid.refreshCell(ui._rowIndx, 'unit_name', false);
	      		   				subGrid.refreshCell(ui._rowIndx, 'subject_code', false);
         		   			}
         		   		});
         		   		without_id += ui.subject_id + ","
	         		  },
          		 },
              },
              { display: '标的物编码', name: 'subject_code',width: '120px',editable: false},
              { display: '通用名', name: 'item_name', width: '120px',editable: true},
              { display: '规格', name: 'item_spec', width: '120px',editable: true},
              { display: '型号', name: 'item_model', width: '120px',editable: true},
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
				{ display: '计量单位', name: 'unit_name', width: '120',textField : 'unit_name',
					editor: {
						type: 'select',
						valueField : 'unit_code',
						textField : 'unit_name',
						url : '../../../basicset/select/queryHosUnitSelect.do?isCheck=false',
	 					keySupport : true,
	 					autocomplete : true
					}
				},
              { display: '单价', name: 'price', width: '120px',align: "right",
                	 render:function(rowData){
     	          		  return formatNumber(parseFloat(rowData.rowData.price),2,1)
     	          	 	}
                   },
              { display: '备注', name: 'note',width: '120px',}
         ];
         var paramObj = {
        	 editable: true,
             height: '200',
             width:'100%',
             checkbox: true,
             usePager: false,
             dataModel: {
	             url: 'queryPactDetFKXY.do?isCheck=false&pact_code='+$("#pact_code").val()
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
    			//var price = parseFloat(data.price);
    			//if (!price) {price = 0;}
    			//ui.rowData.price = formatNumber(parseFloat(price),2,1)
    		}
    	 }
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
                 param.push(rowdata);
             });
             $.etDialog.confirm('确定删除?', function () {
                 ajaxPostData({
                     url: 'deletePactDetFKXY.do',
                     data: {mapVo: JSON.stringify(param)},
                     success: function () {subGrid.deleteRows(data);}
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
            dataModel: {url: '../../pactdoc/queryPactDocFKXY.do?isCheck=false&pact_code='+$("#pact_code").val()},
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
                      url: '../../pactdoc/deletePactDocFKXY.do',
                      data: {mapVo: JSON.stringify(param) },
                      success: function () {docGrid.deleteRows(data);}
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
		if(is_init == "1"){
			$("#change").attr("style","display:none;");
		}
    	initSelect();
   		initfrom();
   		formatMoney("pact_money");
   		setTimeout(function(){
   			initSubGrid();
   		},500);
   		
   		etTab = $("#etTab").etTab({
   			onChange: function(item){
   				if(item.tabid == '1'){
   					if(!docGrid){
	   					initPactDocGrid();
   					}
   				}
   			}
   		});
    	
   		$("#save").on("click", function () {
			if (is_change.status == "checked") {
				parent.$.etDialog.open({
	                url: 'hrp/pac/fkht/pactinfo/pactinit/pactMainFKHTChangePage.do?isCheck=false',
	                width: 500,
	                height: 200,
	                title: '变更原因',
	                modal: true,
	                frameNameObj :{"change" : window.name} 
	            });
			}else{
				save();
			}
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
		startpicker = $("#start_date").etDatepicker({
			defaultDate: '${start_date}',
		});
		endpicker = $("#end_date").etDatepicker({
			defaultDate: '${end_date}',
		});
		//signpicker.disabled();
		
		is_change = $('#is_change').etCheck();
		is_bid = $('#is_bid').etCheck();
		if(${entity.is_bid} == 1){
			is_bid.setCheck();
		}
		is_total_cont = $('#is_total_cont').etCheck();
		if(${entity.is_total_cont} == 1){
			is_total_cont.setCheck();
		}
		is_price_cont = $('#is_price_cont').etCheck();
		if(${entity.is_price_cont} == 1){
			is_price_cont.setCheck();
		}
		
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
			<td class="label no-empty" style="width: 100px;">协议编号：</td>
			<td class="ipt"><input id="pact_code" type="text" disabled="disabled" value="${entity.pact_code }"/></td>
			<td class="label no-empty" style="width: 100px;">协议类型：</td>
			<td class="ipt"><select id="pact_type_code" style="width: 180px;" disabled="disabled"></select> </td>
			<td class="label no-empty" style="width: 100px;">协议状态：</td>
			<td class="ipt"><select id="state_code" style="width: 180px;" ></select></td>
			<td class="label no-empty" style="width: 100px;">协议名称：</td>
			<td class="ipt"><input id="pact_name" type="text" style="width: 180px;" value="${entity.pact_name }"/></td>
		</tr>
		<tr>
			<td class="label" style="width: 100px;">原始编号：</td>
			<td class="ipt"><input id="original_code" type="text" value="${entity.original_code }"/></td>
			<td class="label" style="width: 100px;">关联主协议：</td>
			<td class="ipt"><select id="master_pact_code" style="width: 180px;" ></select></td>
			<td class="label no-empty" style="width: 100px;">签订日期：</td>
			<td class="ipt"><input id="sign_date" type="text"/></td>
			<td class="label no-empty" style="width: 100px;">签约单位：</td>
			<td class="ipt"><input id="hos_name" type="text" value="${hos_name}" /></td>
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
			<td class="label no-empty" style="width: 100px;">签订科室：</td>
			<td class="ipt"><select id="dept_no" style="width: 180px;"></select></td>
			<td class="label no-empty" style="width: 100px;">负责人：</td>
			<td class="ipt"><select id="emp_id" style="width: 180px;"></select> </td>
			<td class="label no-empty" style="width: 100px;">供应商：</td>
			<td class="ipt"><select id="sup_no" style="width: 180px;"></select></td>
			<td class="label no-empty" style="width: 100px;">对方负责人：</td>
			<td class="ipt"><input id="opp_emp" type="text" value="${entity.opp_emp }"/></td>
		</tr>
		<tr>
			<td class="label" style="width: 120px;">对方联系电话：</td>
			<td class="ipt"><input id="opp_phone" type="text" value="${entity.opp_phone }"/></td>
			<td class="label" style="width: 100px;">项目名称：</td>
			<td class="ipt"><select id="proj_id" type="text" style="width: 180px;" /></td>
			<td class="label no-empty" style="width: 100px;">贸易类别：</td>
			<td class="ipt"><select id="trade_type" style="width: 180px;"></select></td>
			<td class="label" style="width: 120px;">组织方式：</td>
			<td class="ipt"><select id="organ_type" style="width: 180px;"></select></td>
		</tr>
		<tr>
			<td class="label no-empty" style="width: 120px;">协议开始日期：</td>
			<td class="ipt"><input id="start_date" type="text" /></td>
			<td class="label no-empty" style="width: 120px;">协议截止日期：</td>
			<td class="ipt"><input id="end_date" type="text" /></td>
			<td class="label" style="width: 120px;">采购方式：</td>
			<td class="ipt"><select id="buy_type" style="width: 180px;"></select></td>
			<td class="label" style="width: 120px;">协议总金额：</td>
			<td class="ipt"><input id="pact_money" type="text" value="${entity.pact_money}" onchange="formatMoney(this.id);"/></td>
		</tr>
		<tr>
			<td class="label" style="width: 100px;">服务条款：</td>
			<td class="ipt" ><textarea id="cont_term1" style="resize:none;width: 180px;">${entity.cont_term1}</textarea></td>
			<td class="label" style="width: 100px;">付款条款：</td>
			<td class="ipt" ><textarea id="cont_term2" style="resize:none;width: 180px;">${entity.cont_term2}</textarea></td>
			<td class="label" style="width: 100px;">验收标准：</td>
			<td class="ipt" ><textarea id="cont_term3" style="resize:none;width: 180px;">${entity.cont_term3}</textarea></td>
			<td></td>
			<td><input id="is_bid" type="checkbox" />是否经过招标</td>
		</tr>
		<tr>
			<td class="label" style="width: 100px;">违约处理：</td>
			<td class="ipt" ><textarea id="cont_term4" style="resize:none;width: 180px;">${entity.cont_term4}</textarea></td>
			<td class="label" style="width: 100px;">交货条款：</td>
			<td class="ipt" ><textarea id="cont_term5" style="resize:none;width: 180px;">${entity.cont_term5}</textarea></td>
			<td class="label" style="width: 100px;">质保条款：</td>
			<td class="ipt"><textarea id="cont_term6" style="resize:none;width: 180px;">${entity.cont_term6}</textarea></td>
		</tr>
		<tr>
			<td class="label" style="width: 100px;">备注：</td>
			<td class="ipt" colspan="5"><textarea id="note" style="resize:none;width: 96%;">${entity.note}</textarea></td>
			<td><input id="is_total_cont" type="checkbox" />总额控制</td>
			<td><input id="is_price_cont" type="checkbox" />单价控制</td>
		</tr>
		<tr id="change">
			<td></td>
			<td><input id="is_change" type="checkbox" />是否保留变更记录</td>
		</tr>
	</table>
		<div id="etTab">
		  <div title="标的物" tabid="0">
			 <div id="subGrid"></div>
		  </div>
		  <div id="tab_2" title="文档管理" tabid='1'>
		    <div id="pactdoc"></div>
		  </div>
		</div>
	<div class="button-group">
	  <button id="save">保存</button>
	  <button id="cancle">取消</button>
	</div>
</body>

</html>

