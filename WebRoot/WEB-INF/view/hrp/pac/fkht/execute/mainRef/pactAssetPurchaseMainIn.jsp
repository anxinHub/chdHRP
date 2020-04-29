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
        var startpicker,endpicker,startpickerc,endpickerc,pact_code,sup_no,state;
        var query = function () {
            params = [
                      { name: 'start_date', value: startpicker.getValue() },
	                  { name: 'end_date', value: endpicker.getValue() },
	                  { name: 'pact_code', value: pact_code.getValue() },
		              { name: 'sup_id', value: sup_no.getValue() },
		              { name: 'start_date_confirm', value: startpickerc.getValue() },
		              { name: 'end_date_confirm', value: endpicker.getValue()},
		              { name: 'ass_in_no', value: $("#ass_in_no").val() },
		              { name: 'state', value: state.getValue() },
		              { name: 'ass_name', value: $("#ass_name").val() },
               
            ];
            grid.loadData(params);
        };
        
        var initSelect=  function(){
        	pact_code = $("#pact_code").etSelect({url: '../../basicset/select/queryPactFKHTSelect.do?isCheck=false',defaultValue: "${pact_code}"});
          	sup_no = $("#sup_no").etSelect({url: '../../basicset/select/queryHosSupDictSelect.do?isCheck=false',defaultValue: "${sup_id}"});
          	state = $("#state").etSelect({
		          		defaultValue: "none",
		          		options: [{ id: 0, text: '新建' },
		          		          { id: 1, text: '审核' },
		          		          { id: 2, text: '确认' }]
          		  	});
        }
        
        var initGrid = function () {
            var columns = [
            	 { display: '入库单号', name: 'ass_in_no', align: 'center',width: '10%',
            		 render:function(ui) {
	               		var data = ui.rowData;
	               		return "<a href=javascript:openAssin('"+data.group_id+"|"+data.hos_id
	               				+"|"+data.copy_code+"|"+data.ass_in_no+"|"+data.ass_naturs+"')>"+data.ass_in_no+"</a>";
                 	}
            	 },
                 { display: '单据状态', name: 'state', align: 'left' ,width: '5%',
         			render:function(ui){
        				var value = ui.rowData.state
        				if(value==0){
        					return "新建";
        				}else if(value==1){
        					return "审核";
        				}else if(value==2){
        					return "确认";
        				}
        			}	 
            	 },
                 { display: '制单日期', name: 'create_date', align: 'center', width: '8%'},
                 { display: '确认日期', name: 'in_date', align: 'center', width: '8%'},
                 { display: '仓库', name: 'store_name', align: 'left', width: '10%'},
                 { display: '领用科室', name: 'dept_name', align: 'left', width: '10%'},
                 { display: '发票号', name: 'invoice_no', align: 'left', width: '12%'},
                 { display: '发票日期', name: 'invoice_date', align: 'left', width: '8%'},
                 { display: '摘要', name: 'note', align: 'left', width: '11%'},
                 { display: '资产编码', name: 'ass_code', align: 'left', width: '10%'},
                 { display: '资产名称', name: 'ass_name', align: 'left', width: '12%'},
                 { display: '规格', name: 'ass_spec', align: 'left', width: '6%'},
                 { display: '型号', name: 'ass_model', align: 'left', width: '6%'},
                 { display: '品牌', name: 'ass_brand', align: 'left', width: '12%'},
                 { display: '生产厂商', name: 'fac_name', align: 'left', width: '6%'},
                 { display: '入库数量', name: 'in_amount', align: 'left', width: '8%'},
                 { display: '单价', name: 'price', align: 'left', width: '10%',
                	 render:function(ui){
	             			var value = ui.rowData.price
	             			if(value){
	             				return formatNumber(value,2,1);
	             			}else{
	             				return 0.00;
	             			}
	             		}	 
                 },
                 { display: '金额', name: 'in_money', align: 'right', width: '12%',
	             		render:function(ui){
	             			var value = ui.rowData.in_money
	             			if(value){
	             				return formatNumber(value,2,1);
	             			}else{
	             				return 0.00;
	             			}
	             		}	 
	             },
                 { display: '资产用途', name: 'usage_code', align: 'left', width: '6%'},
                 { display: '注册证号', name: 'reg_no', align: 'left', width: '10%'},
                 { display: '备注', name: 'note2', align: 'left', width: '10%'}
            ];
            var paramObj = {
            	editable: false,
            	height: '97%',
            	width:'100%',
                dataModel: {
                    url: 'queryPactAssPurchaseFKHTMainIn.do?isCheck=false'
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
    	function openAssin(obj) {
    		var vo = obj.split("|");
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
            }); 
    	}
        $(function () {
            initfrom();
            initGrid();
            initSelect();
        })
       
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
            <td class="label" style="width: 100px;">制单日期：</td>
            <td class="ipt" style="width: 220px;">
                <input id="start_date" type="text" style="width: 100px"/>至 <input id="end_date" type="text" style="width: 100px"/>
            </td>
            <td class="label" style="width: 100px;">合同名称：</td>
            <td class="ipt"><select id="pact_code" disabled="disabled" style="width: 180px"></select> </td>
            <td class="label" style="width: 100px;">供应商：</td>
            <td class="ipt"> <select id="sup_no" disabled="disabled" style="width: 180px"></select> </td>
        </tr>
        <tr>
        	<td class="label" style="width: 100px;">确认日期：</td>
            <td class="ipt" style="width: 220px;">
                  <input id="start_date_confirm" type="text" style="width: 100px"/>至 <input id="end_date_confirm" type="text" style="width: 100px"/>
            </td>
            <td class="label" style="width: 100px;">入库单号：</td>
            <td class="ipt"><input id="ass_in_no" type="text" /> </td>
        	<td class="label" style="width: 100px;">单据状态：</td>
            <td class="ipt"><input id="state" type="text" style="width: 180px"/></td>
        </tr>
        <tr>
        	<td class="label" style="width: 100px;">资产名称：</td>
            <td class="ipt"><input id="ass_name" type="text" /> </td>
        </tr>
    </table>
    <div id="mainGrid"></div>
</body>

</html>