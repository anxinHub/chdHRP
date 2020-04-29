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
        var startpicker,endpicker,startpickerc,endpickerc,pact_name,sup_name,state,pay_type;
    	var updataDictDialog = frameElement.dialog;
        $(function () {
			$("#pact_name").val(updataDictDialog!=null?updataDictDialog.get('data').pact_name:"");
			$("#sup_name").val(updataDictDialog!=null?updataDictDialog.get('data').sup_name:"");
            initfrom();
            initGrid();
            initSelect();
        })
        
        var query = function () {
            params = [
                      { name: 'start_date', value: startpicker.getValue() },
	                  { name: 'end_date', value: endpicker.getValue() },
	                  { name: 'pact_name', value: $("#pact_name").val() },
		              { name: 'sup_id', value: $("#sup_name").val() },
		              
		              { name: 'start_date_confirm', value: startpickerc.getValue() },
		              { name: 'end_date_confirm', value: endpickerc.getValue()},
		              { name: 'invoice_no', value: $("#invoice_no").val() },
		              { name: 'pay_state', value: $("#pay_state").val() },
		              
		              { name: 'pay_type', value: $("#pay_type").val() },
               
            ];
            grid.loadData(params);
        };
        
        var initSelect=  function(){
          	state = $("#state").etSelect({
		          		defaultValue: "none",
		          		options: [{ id: 0, text: '新建' },
		          		          { id: 1, text: '审核' },
		          		          { id: 2, text: '确认' }]
          		  	});
        }
        
        var initGrid = function () {
            var columns = [
            	{ display: '发票流水号', name: 'bill_no', align: 'center',width: '10%',
            		 render:function(ui) {
            			var data = ui.rowData;
 	               		return "<a href=javascript:openAssBill('"+data.bill_no+"|"+data.bill_no+"')>"+data.bill_no+"</a>";
                 	}
            	 },
                 { display: '开票日期', name: 'bill_date', align: 'left' ,width: '8%' },
                 { display: '发票号', name: 'invoice_no', align: 'left', width: '8%'},
                 { display: '摘要', name: 'note', align: 'left', width: '15%'},
                 { display: '发票金额', name: 'bill_name', align: 'left', width: '10%'},
                 { display: '制单日期', name: 'create_date', align: 'left', width: '10%'},
                 { display: '制单人', name: 'create_emp_name', align: 'left', width: '12%'},
                 { display: '确认日期', name: 'audit_date', align: 'left', width: '12%'},
                 { display: '确认人', name: 'audit_emp_name', align: 'left',width : 120 },
                 { display: '状态', name: 'state_name', align: 'left',width : '8%' },
                 { display: '单据类型', name: 'bill_type', align: 'left',width : '8%' }
            ];
            var paramObj = {
            	editable: false,
            	height: '97%',
            	width:'100%',
                dataModel: {
                    url: 'queryPactAssetPurchasePayBill.do?isCheck=false'
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
    	function openAssBill(obj) {
/*     		var vo = obj.split("|");
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
    		parent.$.etDialog.open({
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
            <td class="ipt"><input id="pact_name" type="text" disabled="disabled" style="width: 180px"/></td>
            <td class="label" style="width: 100px;">供应商：</td>
            <td class="ipt"><input id="sup_name" type="text" disabled="disabled" style="width: 180px"/></td>
        </tr>
        
        <tr>
        	<td class="label" style="width: 100px;">确认日期：</td>
            <td class="ipt" style="width: 220px;">
                  <input id="start_date_confirm" type="text" style="width: 100px"/>至 <input id="end_date_confirm" type="text" style="width: 100px"/>
            </td>
            <td class="label" style="width: 100px;">发票号：</td>
            <td class="ipt"><input id="invoice_no" type="text" /> </td>
        	<td class="label" style="width: 100px;">单据状态：</td>
            <td class="ipt"><input id="state" type="text" style="width: 180px"/></td>
        </tr>
        <tr>
            <td class="label" style="width: 100px;">单据类型：</td>
            <td class="ipt">
            	<select name="pay_type" id="pay_type" style="width:180px;">
					<option value="0">付款发票</option>
					<option value="1">退款发票</option>
					<option value="2">预付发票</option>
					<option value="3">预退发票</option>
				</select>
			</td>
        </tr>
    </table>
    <div id="mainGrid"></div>
</body>

</html>