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
var etTab,subGrid,docGrid,palnGrid,deposit_type,dept_no,emp_id,cus_no,pact_type_code;
var selectSoues = [];  
var deptSoues = [];   
var sourceSoues = [];   
var empSoues = [];    
var typeSoues = [];    
var stateSoues = [];    
var paycondSoues = [];    
var is_bid ;
var is_deposit;
var cus_id;

    var initSelect=  function(){
		var def = "none";if("${entity.pact_type_code}" != ""){ def = "${entity.pact_type_code}";} 
		pact_type_code = $("#pact_type_code").etSelect({ url: '../../basicset/select/queryPactTypeSKHTSelect.do?isCheck=false',defaultValue: def});
		var def = "none";if("${entity.cus_no}" != ""){ def = "${entity.cus_no}";} 
		cus_no = $("#cus_no").etSelect({ defaultValue: def,url: '../../basicset/select/queryHosCusDictSelect.do?isCheck=false'});
      	var def = "none";if("${entity.state_code}" != ""){ def = "${entity.state_code}";} 
      	state_code = $("#state_code").etSelect({url: '../../basicset/select/queryPactStateSelect.do?isCheck=false',defaultValue: def});
      	var def = "none";if("${entity.deposit_type}" != ""){ def = "${entity.deposit_type}";} 
        deposit_type = $("#deposit_type").etSelect({url: '../../basicset/select/queryDictSelect.do?isCheck=false&f_code=DEPOSIT_TYPE',defaultValue: def});
        var def = "none";if("${entity.organ_type}" != ""){ def = "${entity.organ_type}";} 
        organ_type = $("#organ_type").etSelect({ url: '../../basicset/select/queryDictSelect.do?isCheck=false&f_code=ORGAN_TYPE',defaultValue: def});
        
        var def = "none";if("${entity.buy_type}" != ""){ def = "${entity.buy_type}";} 
        buy_type = $("#buy_type").etSelect({ url: '../../basicset/select/queryDictSelect.do?isCheck=false&f_code=BUY_TYPE', defaultValue: def,});
    	ajaxPostData({
    		 url: '../../basicset/select/queryHosEmpDictSelect.do?isCheck=false',
			 success: function (result) {
        		var def = "none";if("${entity.emp_id}" != ""){ def = "${entity.emp_id}";} 
				emp_id = $("#emp_id").etSelect({options:result,defaultValue: def,});
	            empSoues = result;
			 },
		});
    	ajaxPostData({
    		 url: '../../basicset/select/queryDeptSelect.do?isCheck=false',
			 success: function (result) {
        		var def = "none";if("${entity.dept_no}" != ""){ def = "${entity.dept_no}";} 
				 dept_no = $("#dept_no").etSelect({options:result,defaultValue: def,});
				 deptSoues = result;
				 }
    		 });
       var def = "none";if("${entity.master_pact_code}" != ""){ def = "${entity.master_pact_code}";} 
	  master_pact_code = $("#master_pact_code").etSelect({
               	url:'../../basicset/select/queryPactSKHTSelect.do?isCheck=false&is_init=0',
               	defaultValue: def,
            });
       var def = "none";if("${entity.proj_id}" != ""){ def = "${entity.proj_id}";} 
	  	proj_id = $("#proj_id").etSelect({ url: '../../basicset/select/queryHosProjDictSelect.do?isCheck=false', defaultValue: def});
      	ajaxPostData({url: '../../basicset/select/queryDictSelect.do?isCheck=false',data: {f_code:'SUBJECT_TYPE'},success: function (result) { selectSoues = result;}});
      	ajaxPostData({url: '../../basicset/select/queryHosSourceDictSelect.do?isCheck=false',success: function (result) {sourceSoues = result; }});
      	ajaxPostData({url: '../../basicset/select/queryPactPayCondSelect.do?isCheck=false',success: function (result) {paycondSoues = result;}});
      	ajaxPostData({url: '../../basicset/select/queryPactDocTypeSelect.do?isCheck=false' ,success: function (result) {typeSoues= result;}});
      }
    
     var selectId ;
	 var initSubGrid = function () {
         var columns = [
         	 { display: '标的物类型', name: 'subject_type_name',width: '100px',
         		editor: {
         		     type: 'select', 
         		     keyField: 'subject_type',
         		     source:selectSoues,
         			 change: function (id) {
         				selectId = id.subject_type;
         				subGrid.getColumns()[2].editor.url ='../../basicset/select/querySubjectSelect.do?isCheck=false&type='+selectId;
         		 	 },
         		 },
        	 },
              { display: '标的物名称', name: 'subject_name',width: '160px',
            	  editor: {
          		     type: 'select', 
          		     keyField: 'subject_id',
          		     source:'../../basicset/select/querySubjectSelect.do?isCheck=false&type='+selectId,
          		 },
              },
              { display: '规格', name: 'item_spec', width: '120px'},
              { display: '型号', name: 'item_model', width: '120px',},
              { display: '数量', name: 'amount', width: '120px',align: "right", editor: {type: 'number'}},
              { display: '单价', name: 'price', width: '120px',align: "right", editor: { type: 'textbox'}},
              { display: '金额', name: 'money',width: '120px',align: "right", editor: {type: "textbox"}},              
              { display: '交货日期', name: 'arrive_date',width: '120px', align: 'center',editor: {type: 'date',}},
              { display: '保修期(月)', name: 'keep_repair_month',width: '120px',},
              { display: '备注', name: 'note',width: '120px',},
              { display: '需求科室', name: 'dept_name',width: '120px',editor: {type: 'select',keyField: 'dept_no',source:deptSoues}},
         ];
         var paramObj = {
        	 editable: false,
             height: '200',
             width:'100%',
             checkbox: true,
             usePager: false,
             dataModel: {
	             url: '../pactinfo/pactinit/queryPactDetSKHT.do?isCheck=false&change_code=${change_code}&pact_code='+$("#pact_code").val()
             },
             columns: columns
         };
         
       	subGrid = $("#subGrid").etGrid(paramObj);
     };
     
	 var initPactPlanGrid = function () {
         var columns = [
         	 { display: '收款期号', name: 'rec_id',width: '150px'},
             { display: '摘要', name: 'summary', width: '250px',
         		editor: {
         		    type: "textbox", 
         		} 
              },
              { display: '收款期限', name: 'rec_date', width: '150px',
            	    editor: {
            	      type: 'date',
            	    }
              },
              { display: '收款条件', name: 'rec_cond_name', width: '150px',editor: {type: 'select',keyField: 'rec_cond',source:paycondSoues}},
             // { display: '资金来源', name: 'source_name', width: '150px',editor: {type: 'select',keyField: 'source_id',source:sourceSoues}},
              { display: '计划金额', name: 'plan_money', align: 'right', width: '150px'}
         ];
         var paramObj = {
         	editable: false,
         	height: '200',
         	width:'100%',
         	checkbox: true,
         	usePager: false,
            dataModel: {
            	url: 'queryPactPlanSKHTForPre.do?isCheck=false&change_code=${change_code}&pact_code='+$("#pact_code").val(),
            },
           	columns: columns
         };
        palnGrid = $("#pactplan").etGrid(paramObj);
     };
     
  
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
         	editable: false,
         	height: '200',
         	width:'100%',
         	checkbox: true,
         	usePager: false,
            dataModel: {url: '../../skht/pactdoc/queryPactDocSKHT.do?isCheck=false&pact_code='+$("#pact_code").val()},
            columns: docColumns
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
		startpicker = $("#start_date").etDatepicker({
			defaultDate: '${start_date}',
		});
		endpicker = $("#end_date").etDatepicker({
			defaultDate: '${end_date}',
		});
		signpicker = $("#sign_date").etDatepicker({
			defaultDate: '${sign_date}',
		});
		is_bid = $('#is_bid').etCheck();
		is_deposit = $('#is_deposit').etCheck(); 
	};
</script>
</head>

<body style="overflow: scroll; ">
	<table class="table-layout">
	<tr>
		<td class="label no-empty" style="width: 100px;">合同编号：</td>
			<td class="ipt"><input id="pact_code" type="text" disabled="disabled" style="background-color: #EAEAEA" value="${entity.pact_code }"/></td>
			<td class="label no-empty" style="width: 100px;">合同类型：</td>
			<td class="ipt"><select id="pact_type_code" style="width: 180px;" disabled="disabled"></select> </td>
			<td class="label no-empty" style="width: 100px;">合同状态：</td>
			<td class="ipt"><select id="state_code" style="width: 180px;" disabled="disabled"></select></td>
		</tr>
		<tr>
			<td class="label no-empty" style="width: 100px;">合同名称：</td>
			<td class="ipt"><input id="pact_name" type="text" value="${entity.pact_name }"/></td>
			<td class="label" style="width: 100px;">原始编号：</td>
			<td class="ipt"><input id="original_code" type="text" value="${entity.original_code }"/></td>
		   	<td class="label" style="width: 100px;">主合同：</td>
			<td class="ipt" ><select id="master_pact_code"   style="width: 180px;"></select></td>
		</tr>
		<tr>

			<td class="label no-empty" style="width: 100px;">签订日期：</td>
			<td class="ipt"><input id="sign_date" type="text"/></td>
			<td class="label" style="width: 100px;">预交货期限(月):</td>
			<td class="ipt" ><input type="text" id="delivery_term"  value="${entity.delivery_term }"></td>
			<td class="label" style="width: 100px;">项目：</td>
			<td class="ipt" ><select id="proj_id" type="text" style="width: 180px;"></select></td>
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
			<td class="ipt"><input id="opp_emp" type="text" value="${entity.opp_emp }"/></td>
			<td class="label" style="width: 120px;">对方联系电话：</td>
			<td class="ipt"><input id="opp_phone" type="text" value="${entity.opp_phone }"/></td>
		</tr>
		<tr>
			<td class="label" style="width: 100px;">服务商：</td>
			<td class="ipt"><input id="server" style="width: 180px;" value="${entity.server }"></td>
			<td class="label" style="width: 100px;">服务联系人：</td>
			<td class="ipt"><input id="ser_emp" type="text"  value="${entity.ser_emp }"/></td>
			<td class="label" style="width: 120px;">服务电话：</td>
			<td class="ipt"><input id="ser_phone" type="text"  value="${entity.ser_phone }"/></td>
		</tr>
		
		
		<tr>
			<td class="label no-empty" style="width: 120px;">合同开始日期：</td>
			<td class="ipt"><input id="start_date" type="text" /></td>
			<td class="label no-empty" style="width: 120px;">合同截止日期：</td>
			<td class="ipt"><input id="end_date" type="text" /></td>
			<td class="label no-empty" style="width: 120px;">合同金额：</td>
			<td class="ipt"><input id="pact_money" type="text" maxlength="15" value="${entity.pact_money }" disabled="disabled" style="background-color: #EAEAEA"/></td>
		</tr>
		<tr>
			<td></td>
			<td><input id="is_bid" type="checkbox" />是否经过招标</td>
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
			<td class="ipt"><input id="deposit_money" type="text" disabled="disabled" maxlength="15" style="background-color: #EAEAEA" value="${entity.deposit_money }"/></td>
		</tr>
		
		<tr>
	       <td  class="label" style="width: 100px;">服务条款</td>
	       <td class="ipt"><textarea style="resize:none;width: 180px;" id="cont_term1">${ entity.cont_term1}</textarea></td>
	       
	       <td  class="label" style="width: 100px;">付款条款</td>
	       <td class="ipt"><textarea style="resize:none;width: 180px;"  id="cont_term2">${ entity.cont_term2}</textarea></td>
	       
	       <td  class="label" style="width: 100px;">验收条款</td>
	       <td class="ipt"><textarea style="resize:none;width: 180px;"  id="cont_term3">${ entity.cont_term3}</textarea></td>
	    </tr>
	    
	    <tr>
	       <td  class="label" style="width: 100px;">违约处理</td>
	       <td class="ipt"><textarea style="resize:none;width: 180px;"  id="cont_term4">${ entity.cont_term4}</textarea></td>
	       
	       <td  class="label" style="width: 100px;">交货条款</td>
	       <td class="ipt"><textarea style="resize:none;width: 180px;"  id="cont_term5">${ entity.cont_term5}</textarea></td>
	       
	       <td  class="label" style="width: 100px;">质保条款</td>
	       <td class="ipt"><textarea style="resize:none;width: 180px;"  id="cont_term6">${ entity.cont_term6}</textarea></td>
	    </tr>
	    <tr><td  class="label" style="width: 100px;">备注</td>
	    <td class="ipt"  colspan="4"><textarea style="resize:none;width: 70%;"  id="note">${entity.note}</textarea></td>
		   
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

