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
var subGrid,pact_name,cus_no;  
var is_money ;
var cus_id;

    var initSelect=  function(){
    	 pact_name = $("#pact_name").etSelect({ 
   			defaultValue: 'none',
   			onChange:queryGrid
   			//url: 'querySKHTbyCus.do?isCheck=false&cus_no='+value 
   			//选择后加载下面表格数据
   			
   		     });
 
     	ajaxPostData({
      		url: '../../basicset/select/queryHosCusDictSelect.do?isCheck=false',
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
						 pact_name.setValue('');
				    	  pact_name.reload({
				    		  url:'querySKHTbyCus.do?isCheck=false',
				    		  defaultValue: 'none',
				    		 para:{cus_no:value,
				    			 cus_id:cus_id 
				    		 }	  
				    		  
				    	  });
			      	 }  
				 });
			  },
		});
	
      };
      ///根据合同编号加载明细数据
     var queryGrid=function(pact_code){
    	  //alert(pact_code);
    	  params = [
                    { name: 'pact_code', value: pact_code }
                    //{ name: 'cus_no', value: $("#cus_no").val() },                
                   // { name:'is_exe',value:'1'}
                ];
    	  
    	    ///判断合同是否有未完成的变更单等，验证通过则继续，否则不允许继续执行	     	    
        	ajaxPostData({
     		 url: 'ifExitsPactOthers.do?isCheck=false&pact_code='+pact_code,
  			 success: function (result) {
         		 if(parseInt(result.count)>0){
         		   $.etDialog.error('有未完成的变更单，不可继续添加变更!');
         		  pact_name.setValue('');
         		   return;
         		 }else{
         			subGrid.loadData(params); 
         		 }
  		      }
     		 });
    	    
        	/*setTimeout(function(){
        	subGrid.loadData(params);
       		},500);*/
     };
    
     var selectId ;
	 var initSubGrid = function () {
         var columns = [
         	 { display: '标的物编码', name: 'subject_code',width: '100px',editable:false
        	 },
              { display: '标的物名称', name: 'subject_name',width: '160px',editable:false
              },
              { display: '变动前数量', name: 'amount', width: '120px',align: "right",editable:false},
              { display: '变动前单价', name: 'price', width: '120px',align: "right",editable:false},
              { display: '变动前金额', name: 'money',width: '120px',align: "right",editable:false},  
              { display: '数量变动', name: 'changeamount', width: '120px',align: "right", editor: {type: 'number'}},
           
              { display: '单价变动', name: 'changeprice', width: '120px',align: "right", editor: { type: 'textbox'},editable:false,
            		},
              { display: '金额变动', name: 'changemoney',width: '120px',align: "right", editor: {type: "textbox"}},              
              { display: '变动后数量', name: 'amountafter', width: '120px',align: "right",editable:false},
                     		
              { display: '变动后单价', name: 'priceafter', width: '120px',align: "right",editable:false},
              { display: '变动后金额', name: 'moneyafter',width: '120px',align: "right",editable:false}
         ];
         var paramObj = {
        	 editable: true,
        	 
             height: '260',
             width:'100%',       
             usePager: false,
             dataModel: {
	             url: 'queryPactDetSKHT.do?isCheck=false'
             },
             columns: columns,
             editorEnd:editorEnd_f
             
         };
         
       	subGrid = $("#subGrid").etGrid(paramObj);
        subGrid.disable();
     };
   var save=function(){
	  // alert(JSON.stringify(subGrid.getAllData()));
	   ajaxPostData({
	   		 url: 'ifExitsPactOthers.do?isCheck=false&pact_code='+$("#pact_name").val(),
				 success: function (result) {
	       		 if(result.count>0){
	       		   $.etDialog.error('有未完成的变更单，不可继续添加变更!');
	       		  pact_name.setValue('');
	       		   return;
	       		 }
			  }
	   		 });
	   if ($("#rec_id").val().length > 4) {
				$.etDialog.error('收款期号的长度为1-4位');
				
				return;
			}
		if(is_money.checked==true){
			formValidate = $.etValidate({
			items : [ 
			{el : $("#cus_no"), required : true}, 
			{el : $("#pact_name"),required : true},
			{el : $("#change_date"),required : true},
			{el : $("#change_reason"),required : true},
			{el : $("#rec_id"), required : true}, 
			{el : $("#rec_date"),required : true},
			{el : $("#plan_money"),required : true},
			{el : $("#summary"),required : true},
			{el : $("#rec_cond"),required : true}
			]
			});
			}else{
				formValidate = $.etValidate({
					items : [ 
						{el : $("#cus_no"), required : true}, 
						{el : $("#pact_name"),required : true},
						{el : $("#change_date"),required : true},
						{el : $("#change_reason"),required : true}
						
						]

				});	
			}
		if(!formValidate.test()){return;};
       
	    $.etDialog.confirm('确定保存?', function () {
		   ajaxPostData({
               url: 'AddChangeAfterSign.do?isCheck=false',
               data: {
            	cus_no:$("#cus_no").val(),
            	cus_id:cus_id,
           		pact_code:$("#pact_name").val(),
           		change_date:$("#change_date").val(),
           		change_reason:$("#change_reason").val(),
           		rec_id:$("#rec_id").val(),
           		summary:$("#summary").val(),
           		rec_date:$("#rec_date").val(),
           		rec_date:$("#rec_date").val(),
           		plan_money:$("#plan_money").val(),
           		rec_cond:$("#rec_cond").val(),
           		is_money:is_money.checked,
           		sub : JSON.stringify(subGrid.getAllData())
                
               },
               success: function (response) {
            		var curIndex = parent.$.etDialog.getFrameIndex(window.name);	
      			  parent.$.etDialog.close(curIndex); 
      		        var parentFrameName = parent.$.etDialog.getFrameName('add');
      		        var parentWindow = parent.window[parentFrameName];
      			    parentWindow.query(); 
   			},
   			delayCallback : true
           }) 
		   
	   });
	   
   };  
   ///编辑后事件  自动计算变动金额等 
   function  editorEnd_f(event, ui) {
	///变动金额可以为负数（减少）,但是减少的数量不能比原数量大
	if(ui.dataIndx=='changeamount'){
		var amount=parseInt(ui.rowData.amount);
		//alert(amount);
		var changeamount=parseInt(ui.rowData.changeamount);
		if(parseFloat(changeamount)<0){
			if(Math.abs(changeamount)>parseFloat(amount)){
				ui.rowData.changeamount=0;
				  $.etDialog.error('输入的变动数量无效!');
				  return;
			}
		}
		ui.rowData.amountafter=amount+changeamount;
		subGrid.refreshCell(ui.rowIndx, 'amountafter', false);
		
	}else if(ui.dataIndx=='changemoney'){
		var changemoney=parseFloat(ui.rowData.changemoney);
		var money=parseFloat(ui.rowData.money);		
		if(parseFloat(changemoney)<0){
			if(Math.abs(changemoney)>money){
				ui.rowData.changemoney=0;
				  $.etDialog.error('输入的变动金额无效!');
				  return;
			}
		}
				
		var price=parseFloat(ui.rowData.price);
		var changeprice=((changemoney+money)/(parseInt(ui.rowData.changeamount)+parseInt(ui.rowData.amount)))-price;
		ui.rowData.changeprice=changeprice;
		ui.rowData.priceafter=changeprice+price;
		ui.rowData.moneyafter=changemoney+money;
		subGrid.refreshCell(ui.rowIndx, 'changeprice', false);
		subGrid.refreshCell(ui.rowIndx, 'moneyafter', false);
		subGrid.refreshCell(ui.rowIndx, 'priceafter', false);
		addMoney();
	}
   };
   function addMoney(){
		var money = 0;
 	 	var data = subGrid.getAllData();
		 if (data != null && data.length != 0) {
          $(data).each(function () {
         	 var rowdata = this;
         	 if (rowdata && rowdata.changemoney) {
         		money  += parseFloat(rowdata.changemoney);
				}
          });
     }
 		$("#plan_money").val(money);
	}
   
   function onChange_f(status){
	   //alert(status);
	   //如果选中金额变动，显示下面内容，否则隐藏
	   if(status=='unchecked'){
		  $("#rec_id").attr("disabled","disabled");
		  $("#summary").attr("disabled","disabled");
		  $("#rec_date").attr("disabled","disabled");
		 // $("#plan_money").attr("disabled","disabled");
		  $("#rec_cond").attr("disabled","disabled");
		  subGrid.disable();
	   }else{
		    $("#rec_id").removeAttr("disabled");
		    $("#summary").removeAttr("disabled");
		    $("#rec_date").removeAttr("disabled");
			//$("#plan_money").removeAttr("disabled");
			$("#rec_cond").removeAttr("disabled");
			 subGrid.enable();
	   }
   };
	$(function(){
    	initSelect();
   		initfrom();
   		setTimeout(function(){
   			initSubGrid();
   		},500);
   		
   		$("#cancle").on("click", function () {
   			//alert("22");
			var curIndex = parent.$.etDialog.getFrameIndex(window.name);
			
			  parent.$.etDialog.close(curIndex); 
		        var parentFrameName = parent.$.etDialog.getFrameName('add');
		        var parentWindow = parent.window[parentFrameName];
			    parentWindow.query(); 

	        
		});
   		
   		
        $("#save").on("click",function(){save()});
	});
	
	
	  //日期
	var initfrom = function(){
		change_date = $("#change_date").etDatepicker({
			defaultDate:true,
		});
		rec_date = $("#rec_date").etDatepicker({
			defaultDate: true,
		});
		
		is_money = $('#is_money').etCheck({
			onChange:onChange_f
		});
		 
	};
	
	
</script>
</head>

<body style="overflow: scroll; ">
	<table class="table-layout" >
	<tr>
		<td class="label" style="width: 100px;" >变更单号：</td>
			<td class="ipt"><input id="change_code" type="text" disabled="disabled" style="background-color: #EAEAEA" /></td>
			<td class="label no-empty" style="width: 100px;">客户：</td>
			<td class="ipt"><select id="cus_no" style="width: 180px;"></select> </td>
			<td class="label no-empty" style="width: 100px;">合同名称：</td>
			<td class="ipt"><select id="pact_name" style="width: 180px;"></select></td>
		</tr>
		<tr>
			<td class="label no-empty" style="width: 100px;">变更日期：</td>
			<td class="ipt"><input id="change_date" type="text" style="width: 180px;"/></td>			
		</tr>
		<tr>
			<td class="label no-empty" style="width: 100px;">变更原因：</td>
			<td class="ipt" colspan="5" ><textarea id="change_reason" style="width:500px"></textarea></td>		
		</tr>
		<tr>
			<td  class="label" style="width: 100px;" id="change_box" ><input id="is_money" type="checkbox" onclick="is_money();"/>金额变动</td>
		</tr>
		<tr>
			<td class="label no-empty" style="width: 100px;">收款期号：</td>
			<td class="ipt"><input id="rec_id" style="width: 180px;" type="text" disabled="disabled"></td>
			<td class="label" style="width: 100px;">收款摘要：</td>
			<td class="ipt"><input id="summary" type="text" style="width: 180px;" disabled="disabled"/></td>
			<td class="label no-empty" style="width: 100px;">收款期限：</td>
			<td class="ipt"><input id="rec_date" type="text" style="width: 180px;" disabled="disabled"/></td>
		</tr>
		<tr>
			<td class="label" style="width: 100px;">金额：</td>
			<td class="ipt"><input id="plan_money" type="text" style="width: 180px;" disabled="disabled"></td>			
		</tr>
		<tr>
			<td class="label" style="width: 120px;">收款条件：</td>
			<td class="ipt"><input id="rec_cond" type="text" style="width: 180px;" disabled="disabled"/></td>			
		</tr>
			   
	</table>
	
		  <div title="标的物" id="is_moneygrid" >
			 <div id="subGrid"></div>
		  </div>
   	<div class="button-group">
	  <button id="save" >保存</button>
	  <button id="cancle">取消</button>
	</div>
</body>

</html>

