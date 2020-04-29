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
var etTab,subGrid,docGrid,palnGrid,deposit_type,pact_type_code;
var selectSoues = [];  
var deptSoues = [];   
var projSoues = [];   
var sourceSoues = [];   
var empSoues = [];    
var typeSoues = [];    
var stateSoues = [];    
var paycondSoues = [];    
var is_bid ;
var state_code;
var is_deposit;

    var initSelect=  function(){
      	ajaxPostData({
      		url: '../../basicset/select/queryPactTypeFKHTSelect.do?isCheck=false',
			  success: function (result) {
				  var def = "none";
				  if("${entity.pact_type_code}" != ""){
					  def = "${entity.pact_type_code}";
				  }
				 pact_type_code = $("#pact_type_code").etSelect({
					 options:result ,
					 defaultValue: def,
				 });
			  },
		});
      	trade_type = $("#state_code").etSelect({url: '../../basicset/select/queryPactStateSelect.do?isCheck=false', defaultValue: '${entity.state_code}'});
      	trade_type = $("#trade_type").etSelect({url: '../../basicset/select/queryDictSelect.do?isCheck=false&f_code=TRADE_TYPE', defaultValue: '${entity.trade_type}'});
       
        var def = "none";if("${entity.deposit_type}" != ""){ def = "${entity.deposit_type}";}
      	deposit_type = $("#deposit_type").etSelect({
       	 	 url: '../../basicset/select/queryDictSelect.do?isCheck=false&f_code=DEPOSIT_TYPE',
       	 	 defaultValue: def,
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
      	 var def = "none";if("${entity.organ_type}" != ""){ def = "${entity.organ_type}";}
        organ_type = $("#organ_type").etSelect({ url: '../../basicset/select/queryDictSelect.do?isCheck=false&f_code=ORGAN_TYPE', defaultValue: def,});
      	 var def = "none";if("${entity.buy_type}" != ""){ def = "${entity.buy_type}";}
        buy_type = $("#buy_type").etSelect({ url: '../../basicset/select/queryDictSelect.do?isCheck=false&f_code=BUY_TYPE',  defaultValue: def });
      	 var def = "none";if("${entity.sup_no}" != ""){ def = "${entity.sup_no}";}
        sup_no = $("#sup_no").etSelect({ url: '../../basicset/select/queryHosSupDictSelect.do?isCheck=false', defaultValue: def});
      	 var def = "none";if("${entity.proj_id}" != ""){ def = "${entity.proj_id}";}
        proj_id = $("#proj_id").etSelect({ url: '../../basicset/select/queryHosProjDictSelect.do?isCheck=false', defaultValue:def});
        
        curr_code = $("#curr_code").etSelect({
       	  url: '../../basicset/select/queryAccCurDictSelect.do?isCheck=false',
       	  defaultValue: '${entity.curr_code}'});
        
    	ajaxPostData({
    		 url: '../../basicset/select/queryHosEmpDictSelect.do?isCheck=false',
			 success: function (result) {
				emp_id = $("#emp_id").etSelect({options:result,defaultValue: '${entity.emp_id}',});
	            empSoues = result;
			 },
		});
    	ajaxPostData({
    		 url: '../../basicset/select/queryDeptSelect.do?isCheck=false',
			 success: function (result) {
				dept_no = $("#dept_no").etSelect({options:result,defaultValue: '${entity.dept_no}'});
	            deptSoues = result;
			 },
		});
      	ajaxPostData({
      		url: '../../basicset/select/queryPactFKHTSelect.do?isCheck=false&is_init=1',
			success: function (result) {
				var def = "none"; if("${entity.master_pact_code}" != ""){def = "${entity.master_pact_code}";}
			  	master_pact_code = $("#master_pact_code").etSelect({options:result,defaultValue: def});
			 },
		});
      	
      	ajaxPostData({
		 	url: '../../basicset/select/queryDictSelect.do?isCheck=false',
			data: {f_code:'SUBJECT_TYPE'},
			success: function (result) { selectSoues = result;},
		});
      	ajaxPostData({
		 	url: '../../basicset/select/queryHosSourceDictSelect.do?isCheck=false',
			success: function (result) {sourceSoues = result; },
		});
      	ajaxPostData({
		 	url: '../../basicset/select/queryPactPayCondSelect.do?isCheck=false',
			success: function (result) {paycondSoues = result;},
		});
      	ajaxPostData({
      		url: '../../basicset/select/queryPactDocTypeSelect.do?isCheck=false' ,
			success: function (result) {typeSoues= result;},
		});
      }
    
     var selectId ;
	 var initSubGrid = function () {
         var columns = [
         	 { display: '标的物类型', name: 'subject_type_name',width: '100px'},
             // { display: '', name: 'detail_id', align: 'center' ,hidden:true},
              { display: '标的物名称', name: 'subject_name',width: '160px'},
              { display: '规格', name: 'item_spec', width: '120px'},
              { display: '型号', name: 'item_model', width: '120px',},
              { display: '生产厂家', name: 'manufacturer', width: '120px',},
              { display: '制造国', name: 'made_nation', width: '120px',},
              { display: '数量', name: 'amount', width: '120px',align: "right"},
              { display: '单价', name: 'price', width: '120px',align: "right"},
              { display: '金额', name: 'money',width: '120px',align: "right", editor: {type: "textbox"}},              
              { display: '交货日期', name: 'arrive_date',width: '120px', align: 'center'},
              { display: '保修期(月)', name: 'repair_months',width: '120px',},
              { display: '备注', name: 'note',width: '120px',},
              { display: '需求科室', name: 'dept_name',width: '120px'}
         ];
         var paramObj = {
        	 editable: false,
             height: '200',
             width:'100%',
             checkbox: true,
             usePager: false,
             dataModel: {
	             url: '../pactinfo/pactinit/queryPactDetFKHT.do?isCheck=false&change_code=${change_code}&pact_code='+$("#pact_code").val(),
             },
             columns: columns,
         };
         
       	subGrid = $("#subGrid").etGrid(paramObj);
     };
     
	 var initPactPlanGrid = function () {
		 var columns = [
         	 { display: '付款期号', name: 'pay_id',width: '150px'},
              { display: '摘要', name: 'summary', width: '250px'},
              { display: '付款期限', name: 'pay_date', width: '150px'},
              { display: '付款条件', name: 'pay_cond_name', width: '150px'},
              { display: '资金来源', name: 'source_name', width: '150px'},
              { display: '计划金额', name: 'plan_money', align: 'right', width: '150px'}
         ];
         var paramObj = {
         	editable: false,
         	height: '200',
         	width:'100%',
         	checkbox: true,
         	usePager: false,
             dataModel: {
	             url: 'queryPactPlanFKHTForPre.do?isCheck=false&change_code=${change_code}&pact_code='+$("#pact_code").val(),
             },
             columns: columns,
         };
        palnGrid = $("#pactplan").etGrid(paramObj);
     };
     
	 var initPactDocGrid = function () {
		 var docColumns = [
         	 { display: '阶段状态', name: 'state_name',width: '100px'},
              { display: '文档类别', name: 'doc_type_name',width: '100px'},
              { display: '文档名称', name: 'doc_name', width: '250px'},
              { display: '所在科室', name: 'dept_name', width: '140px'},
              { display: '责任人', name: 'emp_name', width: '100px'},
              { display: '存放位置', name: 'location', width: '250px'},
              { display: '上传', name: 'file_path', align: 'center', width: '120px'},
         ];
         var paramObj = {
         	editable: false,
         	height: '200',
         	width:'100%',
         	checkbox: true,
         	usePager: false,
            dataModel: {
	         	url: '../../fkht/pactdoc/queryPactDocFKHT.do?isCheck=false&pact_code='+$("#pact_code").val(),
             },
             columns: docColumns,
         };
         docGrid =  $("#pactdoc").etGrid(paramObj);
     };
     
	$(function(){
    	initSelect();
   		initfrom();
   		setTimeout(function(){
   			initSubGrid();
   		},500);
   		
   		etTab = $("#etTab").etTab({
   			onChange: function(item){
   				if(item.tabid == '1'){
   					initPactPlanGrid();
   				}else if(item.tabid == '2'){
   					initPactDocGrid();
   				}
   			}
   		});
    	
		if(${entity.is_deposit} == 1){
			is_deposit.setCheck();
		}
		if(${entity.is_bid} == 1){
			is_bid.setCheck();
		}
	})
	  //日期
	var initfrom = function(){
		startpicker = $("#start_date").etDatepicker({defaultDate: '${start_date }'});
		endpicker = $("#end_date").etDatepicker({defaultDate: '${end_date }'});
		signpicker = $("#sign_date").etDatepicker({defaultDate: '${sign_date }'});
		is_bid = $('#is_bid').etCheck();
		is_deposit = $('#is_deposit').etCheck(); 
	};
</script>
</head>

<body style="overflow: scroll; ">
	<table class="table-layout">
		<tr>
			<td class="label no-empty" style="width: 100px;">合同编号：</td>
			<td class="ipt"><input id="pact_code" type="text" disabled="disabled" value="${entity.pact_code }" style="background-color: #EAEAEA"/></td>
			<td class="label no-empty" style="width: 100px;">合同类型：</td>
			<td class="ipt"><select id="pact_type_code" style="width: 180px;" disabled="disabled"></select> </td>
			<td class="label no-empty" style="width: 100px;">合同状态：</td>
			<td class="ipt"><select id="state_code" style="width: 180px;"  disabled="disabled" ></select></td>
		</tr>
		<tr>
			<td class="label no-empty" style="width: 100px;">合同名称：</td>
			<td class="ipt" colspan="3"><input id="pact_name" type="text" style="width: 93%;background-color: #EAEAEA" value="${entity.pact_name }" disabled="disabled" /></td>
			<td class="label" style="width: 100px;">原始编号：</td>
			<td class="ipt"><input id="original_code" type="text" value="${entity.original_code }" disabled="disabled" style="background-color: #EAEAEA"/></td>
		</tr>
		<tr>
			<td class="label" style="width: 100px;">主合同：</td>
			<td class="ipt" colspan="3"><select id="master_pact_code" style="width: 93%;"  disabled="disabled"></select></td>
			<td class="label no-empty" style="width: 100px;">签订日期：</td>
			<td class="ipt"><input id="sign_date" type="text" disabled="disabled" /></td>
		</tr>
		<tr>
			<td class="label no-empty" style="width: 100px;">签约单位：</td>
			<td class="ipt"><input id="hos_name" type="text" value="${hos_name }" disabled="disabled" style="background-color: #EAEAEA"></td>
			<td class="label no-empty" style="width: 100px;">签订科室：</td>
			<td class="ipt"><select id="dept_no" style="width: 180px;"  disabled="disabled"></select></td>
			<td class="label no-empty" style="width: 100px;">负责人：</td>
			<td class="ipt"><select id="emp_id" style="width: 180px;"  disabled="disabled"></select> </td>
		</tr>
		<tr>
			<td class="label no-empty" style="width: 100px;">供应商：</td>
			<td class="ipt"><select id="sup_no" style="width: 180px;"  disabled="disabled"></select></td>
			<td class="label no-empty" style="width: 100px;">对方负责人：</td>
			<td class="ipt"><input id="opp_emp" type="text" value="${entity.opp_emp }" disabled="disabled" style="background-color: #EAEAEA"/></td>
			<td class="label" style="width: 120px;">对方联系电话：</td>
			<td class="ipt"><input id="opp_phone" type="text" value="${entity.opp_phone }" disabled="disabled" style="background-color: #EAEAEA"/></td>
		</tr>
		<tr>
			<td class="label" style="width: 100px;">合同简介：</td>
			<td class="ipt" colspan="5"><textarea id="pact_intro" style="resize:none;width: 95.5%;background-color: #EAEAEA" disabled="disabled" >${entity.pact_intro }</textarea></td>
		</tr>
		<tr>
			<td class="label no-empty" style="width: 100px;">贸易类别：</td>
			<td class="ipt"><select id="trade_type" style="width: 180px;"  disabled="disabled"></select></td>
			<td class="label no-empty" style="width: 100px;">币种：</td>
			<td class="ipt"><select id="curr_code" style="width: 180px;"  disabled="disabled"></select>
			</td>
			<td class="label" style="width: 120px;">合同金额(外币)：</td>
			<td class="ipt"><input id="pact_money_w" type="text" disabled="disabled" value="${entity.pact_money_w }" style="background-color: #EAEAEA"/></td>
		</tr>
		<tr>
			<td class="label" style="width: 100px;">签订金额：</td>
			<td class="ipt"><input id="sign_money" type="text" value="${entity.sign_money }" disabled="disabled" style="background-color: #EAEAEA"/></td>
			<td class="label" style="width: 100px;">变动金额：</td>
			<td class="ipt"><input id="change_money" type="text" value="${entity.change_money }" disabled="disabled" style="background-color: #EAEAEA"/></td>
			<td class="label no-empty" style="width: 120px;">合同金额：</td>
			<td class="ipt"><input id="pact_money" type="text" value="${entity.pact_money }" disabled="disabled" style="background-color: #EAEAEA"/></td>
		</tr>
		<tr>
			<td class="label" style="width: 100px;">项目名称：</td>
			<td class="ipt" colspan="5"><select id="proj_id" type="text" style="width: 95.5%;" disabled="disabled" ></select></td>
		</tr>
		<tr>
			<td class="label no-empty" style="width: 120px;">合同开始日期：</td>
			<td class="ipt"><input id="start_date" type="text" value="${entity.start_date }" disabled="disabled" /></td>
			<td class="label no-empty" style="width: 120px;">合同截止日期：</td>
			<td class="ipt"><input id="end_date" type="text" value="${entity.end_date }" disabled="disabled" /></td>
		</tr>
		<tr>
			<td></td>
			<td><input id="is_bid" type="checkbox" disabled="disabled" />是否经过招标</td>
			<td class="label" style="width: 120px;">组织方式：</td>
			<td class="ipt"><select id="organ_type" style="width: 180px;"  disabled="disabled"></select></td>
			<td class="label" style="width: 120px;">采购方式：</td>
			<td class="ipt"><select id="buy_type" style="width: 180px;"  disabled="disabled"></select></td>
		</tr>
		<tr>
			<td></td>
			<td><input id="is_deposit" type="checkbox" disabled="disabled" />履约保证金</td>
			<td class="label" style="width: 120px;">履约担保方式：</td>
			<td class="ipt"><select id="deposit_type" disabled="disabled" style="width: 180px;"></select></td>
			<td class="label" style="width: 120px;">保证金金额：</td>
			<td class="ipt"><input id="deposit_money" type="text" disabled="disabled" value="${entity.deposit_money }" style="background-color: #EAEAEA"/></td>
		</tr>
	</table>
		<div id="etTab">
		  <div title="标的物" tabid="0">
			 <div id="subGrid"></div>
		  </div>
		  <div title="付款计划" tabid='1'>
			 <div id="pactplan"></div>
		  </div>
		  <div id="tab_3" title="文档管理" tabid='2'>
		    <div id="pactdoc"></div>
		  </div>
		</div>
</body>

</html>

