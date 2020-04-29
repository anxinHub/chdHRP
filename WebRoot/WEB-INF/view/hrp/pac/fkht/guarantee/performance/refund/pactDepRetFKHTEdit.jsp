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
var sup_no,master_pact_code,pay_way;

	var save = function() {
		formValidate = $.etValidate({
			items : [ 
				{el : $("#sup_no"),required : true},
				{el : $("#pact_code"),required : true},
				{el : $("#date"),required : true},
				{el : $("#pay_way"),required : true},
				{el : $("#money"),required : true},
				{el : $("#note"),required : true}
				]
		});
		if(!formValidate.test()){
			return;
		};
		ajaxPostData({
			url : 'updatePactDepRetFKHT.do?isCheck=false',
			data : {
				ret_code : $("#ret_code").val(),
				sup_no : $("#sup_no").val(),
				sup_id : $("#sup_no").val(),
				pact_code : $("#pact_code").val(),
				date : $("#date").val(),
				pay_way : $("#pay_way").val(),
				money : $("#money").val(),
				cheq_no : $("#cheq_no").val(),
				note : $("#note").val(),
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
    		url: '../../../../basicset/select/queryHosSupDictSelect.do?isCheck=false',
			defaultValue: '${entity.sup_no}',
			onChange:function(value){
				pact_code.reload({
    				url: 'queryPactFKHTSelectForRet.do?isCheck=false&sup_no='+value,
    			})
         	}	
		});
    	pact_code = $("#pact_code").etSelect({
    		url: 'queryPactFKHTSelectForRet.do?isCheck=false',
    		defaultValue: '${entity.pact_code}',
    		onChange:function(value){
         		if(value == 0) return;
         		ajaxPostData({
        		 	url: 'queryPactFKHTForRet.do?isCheck=false',
        		 	data:{
        		 		pact_code:value
        		 	},
        			success: function (result) {
        				recDate = $("#date").etDatepicker({
        					defaultDate: result.start_date,
        					minDate:result.start_date
        				});
						$("#money").val(result.deposit_money);
        			},
        			error: function(){
        				$("#money").val("");
        				pact_code.setValue(0);
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
		recDate.setValue("${eneity.date}");
		
	})
	
	window.onload = function(){
		if("01" != "${entity.state}"){
			sup_no.disabled();
			master_pact_code.disabled();
			pay_way.disabled();
			$("#date").attr("disabled","disabled");
			$("#money").attr("disabled","disabled");
			$("#money").attr("style","background-color:#EAEAEA");
			$("#cheq_no").attr("disabled","disabled");
			$("#cheq_no").attr("style","background-color:#EAEAEA");
			$("#note").attr("disabled","disabled");
			$("#note").attr("style","background-color:#EAEAEA;width: 510px");
			$("#save").attr("disabled","disabled");
			$("#save").attr("style","background-color:#EAEAEA;");
		}
	} 
	
	  //日期
	var initfrom = function(){
		recDate = $("#date").etDatepicker({
			defaultDate: "${date}",
		});
		
	};
</script>
</head>

<body style="overflow: scroll; ">
	<table class="table-layout">
		<tr>
			<td class="label no-empty" style="width: 100px;">退款编号：</td>
			<td class="ipt"><input id="ret_code" type="text" disabled="disabled" value="${entity.ret_code }" style="background-color: #EAEAEA"/></td>
			<td class="label no-empty" style="width: 100px;">供应商：</td>
			<td class="ipt"><select id="sup_no" style="width: 180px;"></select></td>
		</tr>
		<tr>
			<td class="label no-empty" style="width: 100px;">合同名称：</td>
			<td class="ipt"><select id="pact_code" style="width: 180px;"></select></td>
			<td class="label no-empty" style="width: 100px;">退款日期：</td>
			<td class="ipt"><input id="date" type="text"/></td>
		</tr>
		<tr>
			<td class="label no-empty" style="width: 100px;">退款方式：</td>
			<td class="ipt"><select id="pay_way" style="width: 180px;"></select></td>
			<td class="label no-empty" style="width: 100px;">退款金额：</td>
			<td class="ipt"><input id="money" type="text" value="${entity.money }"></td>
		</tr>
		<tr>
			<td class="label" style="width: 100px;">支票号码：</td>
			<td class="ipt"><input id="cheq_no" type="text" value="${entity.cheq_no }"/></td>
		</tr>
		<tr>
			<td class="label no-empty" style="width: 100px;">摘要：</td>
			<td class="ipt" colspan="5"><input type="text" value="${entity.note }" id="note" style="width: 510px"> </td>
		</tr>
	</table>
	<div class="button-group">
	  <button id="save">保存</button>
	  <button id="cancle">取消</button>
	</div>
</body>
</html>

