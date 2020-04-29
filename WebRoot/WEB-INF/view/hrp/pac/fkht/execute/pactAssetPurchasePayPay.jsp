<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="hr,tree,grid,select,dialog,datepicker" name="plugins" />
	</jsp:include>
    <script>
        var grid;
        var startpicker,endpicker,startpickerc,endpickerc,pact_code,sup_id,state,pay_type;
    	var updataDictDialog = frameElement.dialog;
        $(function () {
			$("#pact_name").val(updataDictDialog!=null?updataDictDialog.get('data').pact_name:"")
			$("#sup_name").val(updataDictDialog!=null?updataDictDialog.get('data').sup_name:"")
			pact_code = updataDictDialog!=null?updataDictDialog.get('data').pact_code:""
					debugger
            initfrom();
            initGrid();
            initSelect();
            query();
        })
        
        var query = function () {
            params = [
                      { name: 'start_date', value: startpicker.getValue() },
	                  { name: 'end_date', value: endpicker.getValue() },
	                  { name: 'pact_name', value: $("#pact_name").val() },
		              { name: 'sup_name', value: $("#sup_name").val() },
		              
		              { name: 'start_date_confirm', value: startpickerc.getValue() },
		              { name: 'end_date_confirm', value: endpickerc.getValue()},
		              { name: 'pay_no', value: $("#pay_no").val() },
		              { name: 'invoice_no', value: $("#invoice_no").val() },
		              
		              { name: 'pay_type', value: $("#pay_type").val() },
		              { name: 'pay_state', value: $("#pay_state").val() },
		              { name: 'pact_code', value: pact_code },
               
            ];
            grid.loadData(params);
        };
        
        //单据状态
        var initSelect=  function(){
			state = $("#pay_state").etSelect({
		          		defaultValue: "0",
		          		options: [{ id: 0, text: '新建' },
		          		          { id: 1, text: '审核' },
		          		          { id: 2, text: '确认' }]
          		  	});
        }
        
        var initGrid = function () {
            var columns = [
            	 { display: '付款单号', name: 'bill_no', align: 'center',width: '10%',
            		 render:function(ui) {
	               		var data = ui.rowData;
	               		return "<a href=javascript:openAssPay('"+data.pay_no+"|"+data.pay_no+"')>"+data.pay_no+"</a>";
                 	}
            	 },
                 { display: '备注', name: 'note', align: 'left', width: '10%'},
                 { display: '付款日期', name: 'pay_date', align: 'left', width: '10%'},
                 { display: '经办人', name: 'audit_emp', align: 'left', width: '10%'},
                 { display: '发票流水号', name: 'bill_no', align: 'left', width: '8%'},
                 { display: '发票号', name: 'invoice_no', align: 'left', width: '8%'},
                 { display: '发票金额', name: 'bill_money', align: 'left', width: '8%'},
                 { display: '付款金额', name: 'pay_money', align: 'left', width: '8%'},
                 { display: '制单日期', name: 'create_date', align: 'left', width: '10%'},
                 { display: '制单人', name: 'create_emp_name', align: 'left', width: '8%'},
                 { display: '确认日期', name: 'audit_date', align: 'left', width: '10%'},
                 { display: '确认人', name: 'audit_emp_name', align: 'left',width : '8%' },
                 { display: '状态', name: 'state_name', align: 'left',width : '8%' },
                 { display: '单据类型', name: 'bill_type', align: 'left',width : '8%' ,
                 	render:function(ui){
                 		debugger
                 		var aa = $("#pay_state").val()
                 		return aa;
                 	}
                 }
               
            ];
            var paramObj = {
            	editable: false,
            	height: '97%',
            	width:'100%',
                dataModel: {
                    url: 'queryPactAssetPurchasePayPay.do?isCheck=false'
                },
                columns: columns,
                toolbar: {
                    items: [
                        { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' }
                    ]
                }
            };
            grid = $("#mainGrid").etGrid(paramObj);
        };
        
        
        
		//入库单信息查看
    	function openAssPay(obj) {
    	/* 	var vo = obj.split("|");
    		if("null"==vo[3] || "undefined"==vo[3]){
    			return false;
    			
    		}
    		var url = '';
    		var ass_natur = vo[4]
    		if(ass_natur=='01'){
    			
    		}else if(ass_natur=='02'){
    			url = 'hrp/ass/assspecial/assin/assInMainSpecialUpdatePage.do?isCheck=false&'
    		}else if(ass_natur=='03'){
    			url= 'hrp/ass/assgeneral/assin/assInMainGeneralUpdatePage.do?isCheck=false&'
    		}else if(ass_natur=='04'){
    			url= 'hrp/ass/assother/assin/assInMainOtherUpdatePage.do?isCheck=false&'
    		}else if(ass_natur=='05'){
    			url='hrp/ass/assinassets/assin/assInMainInassetsUpdatePage.do?isCheck=false&'
    		}else if(ass_natur=='06'){
    			
    		}
    		var parm = "group_id=" + vo[0] + "&" + "hos_id=" + vo[1] + "&"
    				+ " copy_code=" + vo[2] + "&" + "ass_in_no=" + vo[3];
           	
    		parent.$.ligerDialog.open({
   				url : 'hrp/pac/fkht/pactExecAnaly/pactAssetPurchasePayBillPage.do?isCheck=false&pact_code='+data.pact_code,
   				height : $(window).height(),
   				width : $(window).width(),
   				modal : true,
   				showToggle : false,
   				showMax : true,
   				showMin : false,
   				isResize : true,
   				parentframename : window.name,
   				title : '发票记录'
   			}); */
/*     		parent.$.etDialog.open({
                url: url+parm
                width: $(window).width(),
                height: $(window).height(),
                title: '资产入库',
                modal: true,
                frameNameObj :window.name
            });  */
    	}
		

       
        //日期
        var initfrom = function(){
        	startpicker = $("#start_date").etDatepicker({
    			defaultDate: "yyyy-fm-fd",
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
    		startpickerc = $("#start_date_confirm").etDatepicker({
    			defaultDate: "yyyy-fm-fd",
    		  	onChange: function (date) {
    		  		var end = endpickerc.getValue();
    		  		if(end < date){
    		  			endpicker.setValue(end);
    		  		}
    		  	}
    		});
    		endpickerc = $("#end_date_confirm").etDatepicker({
    			defaultDate: true,
    		  	onChange: function (date) {
    		  		var start = startpickerc.getValue();
    		  		if(start > date){
    		  			endpicker.setValue(start);
    		  		}
    		  	}
    		});
		}
    </script>
</head>

<body>
    <table class="table-layout">
        <tr>
            <td class="label" style="width: 100px;">开票日期：</td>
            <td class="ipt" style="width: 220px;">
                <input id="start_date" type="text" style="width: 100px"/>至 <input id="end_date" type="text" style="width: 100px"/>
            </td>
            <td class="label" style="width: 100px;">合同名称：</td>
            <td class="ipt"><input id="pact_name" disabled="disabled" style="width: 180px"></select> </td>
            <td class="label" style="width: 100px;">供应商：</td>
            <td class="ipt"> <input id="sup_name" disabled="disabled" style="width: 180px"></select> </td>
        </tr>
        
        <tr>
        	<td class="label" style="width: 100px;">确认日期：</td>
            <td class="ipt" style="width: 220px;">
                  <input id="start_date_confirm" type="text" style="width: 100px"/>至 <input id="end_date_confirm" type="text" style="width: 100px"/>
            </td>
            <td class="label" style="width: 100px;">付款单号：</td>
            <td class="ipt"><input id="pay_no" type="text" /> </td>
        	<td class="label" style="width: 100px;">发票号：</td>
            <td class="ipt"><input id="invoice_no" type="text" style="width: 180px"/></td>
        </tr>
        <tr>
        	<td class="label" style="width: 100px;">单据类型：</td>
            <td class="ipt">
            	<select name="pay_type" id="pay_type" style="width:180px;">
					<option value="0">资产付款</option>
					<option value="1">资产退款</option>
					<option value="2">资产预付款</option>
					<option value="3">资产预退款</option>
				</select>
			</td>		
        	<td class="label" style="width: 100px;">单据状态：</td>
            <td class="ipt"><input id="pay_state" type="text" style="width: 180px" /> </td>
        </tr>
    </table>
    <div id="mainGrid"></div>
</body>

</html>