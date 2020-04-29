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
var etTab,subGrid,docGrid,palnGrid,dept_no,emp_id,cus_no,pact_type_code;
var deptSoues = [];   
var empSoues = [];    
var stateSoues = [];    
var is_bid ;

    var initSelect=  function(){
		pact_type_code = $("#pact_type_code").etSelect({url: '../../basicset/select/queryPactTypeSKXYSelect.do?isCheck=false',defaultValue: "${entity.pact_type_code}"});
		state_code = $("#state_code").etSelect({url: '../../basicset/select/queryPactStateSelect.do?isCheck=false',defaultValue: "${entity.state_code}",});
        var def = "none";if("${entity.organ_type}" != ""){ def = "${entity.organ_type}";}
        organ_type = $("#organ_type").etSelect({ url: '../../basicset/select/queryDictSelect.do?isCheck=false&f_code=ORGAN_TYPE', defaultValue: def,});
      	 var def = "none";if("${entity.buy_type}" != ""){ def = "${entity.buy_type}";}
        buy_type = $("#buy_type").etSelect({ url: '../../basicset/select/queryDictSelect.do?isCheck=false&f_code=BUY_TYPE',  defaultValue: def });
      	 var def = "none";if("${entity.cus_no}" != ""){ def = "${entity.cus_no}";}
        cus_no = $("#cus_no").etSelect({ url: '../../basicset/select/queryHosCusDictSelect.do?isCheck=false', defaultValue: def});
      	 var def = "none";if("${entity.proj_id}" != ""){ def = "${entity.proj_id}";}
        proj_id = $("#proj_id").etSelect({ url: '../../basicset/select/queryHosProjDictSelect.do?isCheck=false', defaultValue:def});
        
		emp_id = $("#emp_id").etSelect({url: '../../basicset/select/queryHosEmpDictSelect.do?isCheck=false',defaultValue: "${entity.emp_id}",});
		dept_no = $("#dept_no").etSelect({url: '../../basicset/select/queryDeptSelect.do?isCheck=false',defaultValue: "${entity.dept_no}",});

		var master = "none";if("${entity.master_pact_code}" != ""){master = "${entity.master_pact_code}";}
		master_pact_code = $("#master_pact_code").etSelect({url: '../../basicset/select/queryPactSKXYSelect.do?isCheck=false&is_init=1',defaultValue: master});
      }
    
     var selectId ;
	 var initSubGrid = function () {
		 pact_code 
         var columns = [
         	 { display: '标的物类型', name: 'subject_type_name',width: '100px'},
              { display: '标的物名称', name: 'subject_name',width: '160px'},
              { display: '规格', name: 'item_spec', width: '120px'},
              { display: '型号', name: 'item_model', width: '120px',},
              { display: '单价', name: 'price', width: '120px',align: "right"},
              { display: '备注', name: 'note',width: '120px',}
         ];
         var paramObj = {
        	 editable: false,
             height: '200',
             width:'100%',
             checkbox: true,
             usePager: false,
             dataModel: {
	             url: '../pactinfo/pactinit/queryPactDetSKXY.do?isCheck=false&change_code=${change_code}&pact_code=${entity.pact_code}'
             },
             columns: columns
         };
       	subGrid = $("#subGrid").etGrid(paramObj);
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
   					initPactDocGrid();
   				}
   			}
   		});
	})
	
	  //日期
	var initfrom = function(){
		startpicker = $("#start_date").etDatepicker({
			defaultDate: "${start_date}",
		});
		endpicker = $("#end_date").etDatepicker({
			defaultDate: "${end_date}",
		});
		signpicker = $("#sign_date").etDatepicker({
			defaultDate: "${sign_date}",
		});
		
		is_bid = $('#is_bid').etCheck();
		if(${entity.is_bid} == 1){
			is_bid.setCheck();
		}
	};
	var initPactDocGrid = function () {
        var docColumns = [
        	 { display: '阶段状态', name: 'state_name',width: '100px'},
             { display: '文档类别', name: 'doc_type_name',width: '100px'},
             { display: '文档名称', name: 'doc_name', width: '250px'},
             { display: '所在科室', name: 'dept_name', width: '140px'}, 
             { display: '责任人', name: 'emp_name', width: '100px'},
             { display: '存放位置', name: 'location', width: '250px'},
             { display: '上传', name: 'file_path', align: 'center',width: '120px'},
        ];
        var paramObj = {
        	editable: true,
        	height: '200',
        	width:'100%',
        	checkbox: true,
        	usePager: false,
           dataModel: {url: '../pactdoc/queryPactDocSKXY.do?isCheck=false&pact_code='+$("#pact_code").val()},
           columns: docColumns
           
        };
        docGrid =  $("#pactdoc").etGrid(paramObj);
    };
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
			<td class="ipt"><select id="state_code" style="width: 180px;" disabled="disabled"></select></td>
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
			<td class="ipt"><input id="hos_name" type="text" value="${hos_name}" disabled="disabled"/></td>
		</tr>
		<tr>
			
			<td class="label no-empty" style="width: 100px;">签订科室：</td>
			<td class="ipt"><select id="dept_no" style="width: 180px;"></select></td>
			<td class="label no-empty" style="width: 100px;">客户：</td>
			<td class="ipt"><select id="cus_no" style="width: 180px;"></select></td>
			<td class="label no-empty" style="width: 100px;">负责人：</td>
			<td class="ipt"><select id="emp_id" style="width: 180px;"></select></td>
			<td class="label" style="width: 100px;">项目名称：</td>
			<td class="ipt"><select id="proj_id" type="text" style="width: 180px;"></select></td>
		</tr>
		<tr>
			<td></td>
			<td><input id="is_bid" type="checkbox" />是否经过招标</td>
			<td class="label no-empty" style="width: 100px;">对方负责人：</td>
			<td class="ipt"><input id="opp_emp" type="text" value="${entity.opp_emp }"/></td>
			<td class="label" style="width: 120px;">对方联系电话：</td>
			<td class="ipt"><input id="opp_phone" type="text" value="${entity.opp_phone }"/></td>
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
		</tr>
		<tr>
			<td class="label" style="width: 100px;">服务条款：</td>
			<td class="ipt" ><textarea id="cont_term1" style="resize:none;width: 180px;">${entity.cont_term1}</textarea></td>
			<td class="label" style="width: 100px;">付款条款：</td>
			<td class="ipt" ><textarea id="cont_term2" style="resize:none;width: 180px;">${entity.cont_term2}</textarea></td>
			<td class="label" style="width: 100px;">验收标准：</td>
			<td class="ipt" ><textarea id="cont_term3" style="resize:none;width: 180px;">${entity.cont_term3}</textarea></td>
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
</body>

</html>

