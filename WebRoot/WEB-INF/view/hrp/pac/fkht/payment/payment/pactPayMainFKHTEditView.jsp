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
var sup_no,cus_id,palnGrid,payGrid;
var sourceSoues = [];   
	
    var initSelect=  function(){
    	sup_no = $("#sup_no").etSelect({
    		url: '../../../basicset/select/queryHosSupSelectDict.do?isCheck=false',
    		 defaultValue: '${entity.sup_id}'+'@'+'${entity.sup_no}' != "@"? '${entity.sup_id}'+'@'+'${entity.sup_no}':"none",
    	});
    	ajaxPostData({
      		url: '../../../basicset/select/queryPactFKHTSelect.do?isCheck=false&state_code=12',
			  success: function (result) {
				  pact_code = $("#pact_code").etSelect({
					 defaultValue: '${entity.pact_code}',
					 options:result 
				 });
			  },
		});
	 	ajaxPostData({
		 	url: '../../../basicset/select/queryHosSourceDictSelect.do?isCheck=false',
			success: function (result) {sourceSoues = result; },
		});
      }
    
    var initPactPlanGrid = function () {
        var columns = [
        	 { display: '付款期号', name: 'plan_detail_id',hidden:true,editable: false},
        	 { display: '付款期号', name: 'pay_id',width: '100px',editable: false},
             { display: '摘要', name: 'summary', width: '150px',editable: false},
             { display: '付款期限', name: 'pay_date', width: '100px',editable: false},
             /* { display: '资金来源', name: 'source_name', width: '100px',editable: false}, */
             { display: '付款条件', name: 'pay_cond_name', width: '100px',editable: false},
             { display: '计划金额', name: 'plan_money', align: 'right', width: '100px',editable: false},
             { display: '已付金额', name: 'paied_money', align: 'right', width: '100px',editable: false},
             { display: '付款金额', name: 'paied_money', align: 'right', width: '100px'}
        ];
        var paramObj = {
        	editable: false,
        	height: '95%',inWindowHeight : true,
        	width:'100%',
        	checkbox: true,
        	usePager: false,
          	columns: columns,
          	dataModel:{
          		url:'queryPactPlanFKHT.do?isCheck=false&pact_code=${entity.pact_code}&pay_code=${entity.pay_code}',
          	},
          	summary :{
          		  totalColumns: ['plan_money', 'paied_money','unPaied_money','ing_money','pay_money'],
          		  keyWordCol: 'pay_id',
          	},
        	load :function(){
        		ajaxPostData({
       			 	url: 'queryPactPayPlanFKHT.do?isCheck=false&pact_code=${entity.pact_code}&pay_code=${entity.pay_code}',
       				success: function (result) {
       					var pay = palnGrid.getAllData();
       			    	$(pay).each(function () {
       			    		for (var i = 0; i < result.Rows.length; i++) {
       			    			var detail_id = result.Rows[i].plan_det_id;
       			    			if(this.plan_detail_id == detail_id){
		       			    		palnGrid.setSelection(this._rowIndx,true,false);
       			    			}
							}
       			   		 })
       				}
        		});
        	},
        };
       palnGrid = $("#pactplan").etGrid(paramObj);
    };
    
    var initPayDetailGrid = function () {
        var columns = [
             { display: '资金来源', name: 'source_name', width: '150px',editable:false,
           	  editor: {
         		     type: 'select', 
         		     keyField: 'source_id',
         		     autoFocus:true,
         		     source:sourceSoues,
         		 }	  
             },
             { display: '支付方式', name: 'pay_name', width: '150px',
            	  	editor: {
           		     type: 'select',
           		  	 keyField: 'pay_way',
           		  	 autoFocus:true, 
           		     url: '',
           		 }	 
              },
             { display: '付款金额', name: 'pay_money', align: 'right', width: '150px',editable:false,},
             { display: '支票号码', name: 'cheq_no', align: 'right', width: '200px'},
             { display: '说明', name: 'note', align: 'right', width: '200px'}
        ];
        var paramObj = {
        	editable: false,
        	height: '88%',inWindowHeight : true,
        	width:'100%',
        	checkbox: false,
        	usePager: false,
          	columns: columns,
          	addRowByKey:false,
          	dataModel: {
                url: 'queryPactPayDetFKHT.do?isCheck=false&pact_code=${entity.pact_code}&pay_code=${entity.pay_code}'
            },
          	summary :{
        		  totalColumns: ['pay_money'],
        		  keyWordCol: 'source_name',
        	}
        };
       payGrid = $("#payDetail").etGrid(paramObj);
    };
    //日期
	var initfrom = function(){
		pay_date = $("#pay_date").etDatepicker({
			defaultDate: "${pay_date}"
		});
	}
    
	$(function(){
    	initSelect();
   		initfrom();
   		
		$("#cancle").on("click", function () {
			var curIndex = parent.$.etDialog.getFrameIndex(window.name);
	        parent.$.etDialog.close(curIndex); 
		})
		
		etTab = $("#etTab").etTab({
   			onChange: function(item){
   				if(item.tabid == '1'){
   					if(!payGrid){
						initPayDetailGrid();
   					}
  				}else{
  					palnGrid.refreshView();
  				}
   			}
   		});
		initPactPlanGrid();
	});
</script>
</head>

<body style="overflow: scroll; ">
	<div>
		<table class="table-layout">
			<tr>
				<td class="label no-empty" style="width: 100px;">付款单号：</td>
				<td class="ipt"><input id="pay_code" type="text" disabled="disabled" value="${entity.pay_code }" style="background-color: #EAEAEA"/></td>
				<td class="label no-empty" style="width: 100px;">供应商：</td>
				<td class="ipt"><select id="sup_no" style="width: 180px;" disabled="disabled"></select> </td>
				<td class="label no-empty" style="width: 100px;">合同名称：</td>
				<td class="ipt"><select id="pact_code" style="width: 180px;" disabled="disabled"></select> </td>
			</tr>
			<tr>
				<td class="label no-empty" style="width: 100px;">付款日期：</td>
				<td class="ipt"><input id="pay_date" style="width: 180px;" value="${entity.pay_date }" disabled="disabled"/></td>
				<td class="label no-empty" style="width: 100px;">发票号码：</td>
				<td class="ipt"><input id="bill_code" type="text" value="${entity.bill_code }" disabled="disabled" style="background-color: #EAEAEA"/></td>
				<td class="label" style="width: 100px;">付款金额：</td>
				<td class="ipt"><input id="pay_money" type="text" disabled="disabled" value="${entity.pay_money }" style="background-color: #EAEAEA"/></td>
			</tr>
			<tr>
				<td class="label" style="width: 100px;">备注：</td>
				<td class="ipt" colspan="5"><input id="note" type="text" style="width: 836px" value="${entity.note }" disabled="disabled" style="background-color: #EAEAEA"/></td>
			</tr>
		</table>
	</div>
	<div class="button-group">
	 <!--  <button id="save">保存</button> <button id="cancle">取消</button>-->
	  
	</div>
	
	<div id="etTab">
		  <div title="付款计划" tabid='0'>
			 <div id="pactplan"></div>
		  </div>
		  <div title="付款详情" tabid="1">
			 <div id="payDetail"></div>
			 <div class="button-group">
			  <!-- <button id="save">保存</button> -->
			  <button id="cancle">取消</button>
			</div>
		  </div>
	</div>
</body>
</html>

