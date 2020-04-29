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
	<jsp:param value="hr,grid,select,dialog,validate,datepicker" name="plugins" />
</jsp:include>
<script src="<%=path%>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<!-- script src="<%=path%>/lib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script-->

<script type="text/javascript">
var sup_no,pact_code,pay_way,pact_type_code;

	var save = function() {
		if("01" != "${entity.state}"){
			var curIndex = parent.$.etDialog.getFrameIndex(window.name);
	        parent.$.etDialog.close(curIndex); 
			return;
		}
		formValidate = $.etValidate({
			items : [ 
				{el : $("#sup_no"),required : true},
				{el : $("#pact_code"),required : true},
				{el : $("#date"),required : true},
				{el : $("#pay_way"),required : true},
				{el : $("#money"),required : true},
				{el : $("#ret_plan_date"),required : true},
				{el : $("#note"),required : true}
				]
		});
		if(!formValidate.test()){return;};
		
		ajaxPostData({
			url : 'updatePactDepRecFKHT.do?isCheck=false',
			data : {
				rec_code : $("#rec_code").val(),
				sup_no : $("#sup_no").val().split("@")[1],
				sup_id : $("#sup_no").val().split("@")[0],
				pact_code : $("#pact_code").val(),
				date : $("#date").val(),
				pay_way : $("#pay_way").val(),
				money : $("#money").val(),
				cheq_no : $("#cheq_no").val(),
				ret_plan_date : $("#ret_plan_date").val(),
				note : $("#note").val(),
				pact_type_code : pact_type_code
			},
			success : function(data) {
				var parentFrameName = parent.$.etDialog.parentFrameName;
		        var parentWindow = parent.window[parentFrameName];
			    parentWindow.query(); 
		        var curIndex = parent.$.etDialog.getFrameIndex(window.name);
		        parent.$.etDialog.close(curIndex); 
			},
			delayCallback : true
		})
	}

    var initSelect=  function(){
    	sup_no = $("#sup_no").etSelect({
    		url: '../../../../basicset/select/queryHosSupSelectDict.do?isCheck=false',
    		defaultValue: '${entity.sup_no}',
    		onChange:function(value){
    			pact_code.reload({
    				url: '../../../../basicset/select/queryPactFKHTSelect.do?isCheck=false&is_init=${entity.is_init}&state_code=12&is_deposit=1&deposit_type=01&state=3&sup_no='+value.split("@")[1],
    			})
         	}			
    	});
      	ajaxPostData({
      		url: '../../../../basicset/select/queryPactFKHTSelect.do?isCheck=false',
 			data:{
 				state_code:12,
 				deposit_type:'01',
 				is_deposit: 1,
 				is_init:${entity.is_init},
 				sup_no:sup_no.getValue()
 			},
			success: function (result) {
				pact_code = $("#pact_code").etSelect({
                 	options:result,
                 	defaultValue: "${entity.pact_code}",
                 	onChange:function(value){
                 		ajaxPostData({
                		 	url: 'queryPactFKHT.do?isCheck=false&is_init=${entity.is_init}',
                		 	data:{
                		 		pact_code:value
                		 	},
                			success: function (result) {
                				pact_type_code = result.pact_type_code;
                				recDate = $("#date").etDatepicker({
                					defaultDate: result.start_date,
                					maxDate:result.start_date,
                					todayButton: false
                				});
                				ret_plan_date = $("#ret_plan_date").etDatepicker({
                					defaultDate: result.end_date,
                					minDate:result.start_date
                				});
								$("#money").val(result.deposit_money);
                			}
                		});
                 	}
              });
			 }
		});
      	pay_way = $("#pay_way").etSelect({url: '../../../../basicset/select/queryPayTypeDictSelect.do?isCheck=false',defaultValue: "${entity.pay_way}"});
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
		ret_plan_date.setValue("${eneity.ret_plan_date}");
		recDate.setValue("${eneity.date}");
		
		if("01" != "${entity.state}"){
			sup_no.disabled();
			pay_way.disabled();
			$("#date").attr("disabled","disabled");
			$("#ret_plan_date").attr("disabled","disabled");
			$("#money").attr("disabled","disabled");
			$("#money").attr("style","background-color:#EAEAEA");
			$("#cheq_no").attr("disabled","disabled");
			$("#cheq_no").attr("style","background-color:#EAEAEA");
			$("#note").attr("disabled","disabled");
			$("#note").attr("style","width: 550px;background-color:#EAEAEA");
			$("#pact_code").attr("disabled","disabled");
		}
	})
	
	  //日期
	var initfrom = function(){
		ret_plan_date = $("#ret_plan_date").etDatepicker({
			defaultDate: "${ret_plan_date}",
			onChange: function (date) {
		  		var start = recDate.getValue();
		  		if(start > date){
		  			ret_plan_date.setValue(start);
		  		}
		  	}
		});
		recDate = $("#date").etDatepicker({
			defaultDate: "${date}",todayButton: false
		});
		
	};
</script>
</head>

<body style="overflow: scroll; ">
	<table class="table-layout">
		<tr>
			<td class="label no-empty" style="width: 100px;">收款编号：</td>
			<td class="ipt"><input id="rec_code" type="text" disabled="disabled" value="${entity.rec_code }" style="background-color: #EAEAEA"/></td>
			<td class="label no-empty" style="width: 100px;">供应商：</td>
			<td class="ipt"><select id="sup_no" style="width: 180px;" disabled="disabled"></select></td>
		</tr>
		<tr>
			<td class="label no-empty" style="width: 100px;">合同名称：</td>
			<td class="ipt"><select id="pact_code" style="width: 180px;" disabled="disabled"></select></td>
			<td class="label no-empty" style="width: 100px;">收款日期：</td>
			<td class="ipt"><input id="date" type="text"/></td>
		</tr>
		<tr>
			<td class="label no-empty" style="width: 100px;">收款方式：</td>
			<td class="ipt"><select id="pay_way" style="width: 180px;"></select></td>
			<td class="label no-empty" style="width: 100px;">收款金额：</td>
			<td class="ipt"><input id="money" type="text" value="${entity.money }"></td>
		</tr>
		<tr>
			<td class="label" style="width: 100px;">支票号码：</td>
			<td class="ipt"><input id="cheq_no" type="text" value="${entity.cheq_no }"/></td>
			<td class="label  no-empty" style="width: 140px;">计划归还日期：</td>
			<td class="ipt"><input id="ret_plan_date" type="text" /></td>
		</tr>
		<tr>
			<td class="label no-empty" style="width: 100px;">摘要：</td>
			<td class="ipt" colspan="5"><input type="text" value="${entity.note }" id="note" style="width: 550px"> </td>
		</tr>
	</table>
	<div class="button-group">
	  <button id="save">保存</button>
	  <button id="cancle">取消</button>
	</div>
	<div>
	 <table class="table-layout">
	  	<tr>
	  		<td class="label" style="width: 100px;">制单人：</td>
	  		<td class="ipt">${entity.maker_name }</td>
	  		<td class="label" style="width: 100px;">确认人：</td>
	  		<td class="ipt">${entity.confirmer_name }</td>
	  	</tr>
	  	<tr>
	  		<td class="label" style="width: 100px;">制单日期：</td>
	  		<td class="ipt"><span>${make_date }</span></td>
	  		<td class="label" style="width: 100px;">确认日期：</td>
	  		<td class="ipt"><span>${confirm_date }</span></td>
	  	</tr>
	  </table>
	</div>
</body>
</html>

