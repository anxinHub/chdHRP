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
        var startpicker,endpicker,sup_no,dept_id,state,pact_code;
        var query = function () {
            params = [
                      { name: 'start_date', value: startpicker.getValue() },
	                  { name: 'end_date', value: endpicker.getValue() },
	                  { name: 'pact_code', value: pact_code.getValue() },
		              { name: 'sup_id', value: sup_no.getValue() },
		              { name: 'ins_no', value: $("#ins_no").val() },
		              { name: 'dept_id', value: dept_id.getValue() },
		              { name: 'state', value: state.getValue() },
		              { name: 'ass_name', value: $("#ass_name").val() },
               
            ];
            grid.loadData(params);
        };
        
        var initSelect=  function(){
        	pact_code = $("#pact_code").etSelect({url: '../../basicset/select/queryPactFKHTSelect.do?isCheck=false',defaultValue:'${pact_code}'});
          	sup_no = $("#sup_no").etSelect({url: '../../basicset/select/queryHosSupDictSelect.do?isCheck=false',defaultValue: "${sup_id}"});
          	dept_id = $("#dept_id").etSelect({url: '../../basicset/select/queryDeptSelect.do?isCheck=false',defaultValue: "none"});
          	state = $("#state").etSelect({
		          		defaultValue: "none",
		          		options: [{ id: 0, text: '新建' },
		          		          { id: 1, text: '审核' },
		          		          { id: 2, text: '确认' }]
          		  	});
        }
        
        var initGrid = function () {
            var columns = [
            	 { display: '安装单号', name: 'ins_no', align: 'center',width: '10%',
            		 render:function(ui) {
	               		var data = ui.rowData;
	               		return "<a href=javascript:openAssIns('"+data.ins_id+"|"+data.ins_no+"')>"+data.ins_no+"</a>";
                 	}
            	 },
                 { display: '单据状态', name: 'state', align: 'center' ,width: '5%',
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
                 { display: '制单日期', name: 'ins_date', align: 'center', width: '7%'},
                 { display: '安装科室', name: 'dept_name', align: 'left', width: '10%'},
                 { display: '摘要', name: 'accept_desc', align: 'left', width: '11%'},
                 { display: '资产编码', name: 'ass_code', align: 'left', width: '8%'},
                 { display: '资产名称', name: 'ass_name', align: 'left', width: '12%'},
                 { display: '规格', name: 'ass_spec', align: 'left', width: '6%'},
                 { display: '型号', name: 'ass_model', align: 'left', width: '6%'},
                 { display: '品牌', name: 'ass_brand', align: 'left', width: '8%'},
                 { display: '生产厂商', name: 'fac_name', align: 'left', width: '12%'},
                 { display: '安装数量', name: 'ins_amount', align: 'left', width: '8%'},
                 { display: '安装单价', name: 'ins_price', align: 'right', width: '8%',
                	 render:function(ui){
	             			var value = ui.rowData.ins_price
	             			if(value){
	             				return formatNumber(value,2,1);
	             			}else{
	             				return 0.00;
	             			}
	             		}	 
                 },
                 { display: '安装费用', name: 'ins_money', align: 'right', width: '8%',
	             		render:function(ui){
	             			var value = ui.rowData.ins_money
	             			if(value){
	             				return formatNumber(value,2,1);
	             			}else{
	             				return 0.00;
	             			}
	             		}	 
	             },
                 { display: '安装单位', name: 'ins_company', align: 'left', width: '10%'},
                 { display: '联系电话', name: 'ins_tele', align: 'left', width: '8%'},
                 { display: '主要安装工程师', name: 'ins_engr', align: 'left', width: '8%'},
                 { display: '安装说明', name: 'ins_desc', align: 'left', width: '10%'},
            ];
            var paramObj = {
            	editable: false,
            	height: '97%',
            	width:'100%',
                dataModel: {
                    url: 'queryPactAssPurchaseFKHTMainIns.do?isCheck=false'
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
        
		//安装信息查看
    	function openAssIns(obj) {
    		var vo = obj.split("|");
    		if("null"==vo[0] || "undefined"==vo[0]){
    			return false;
    			
    		}
    		var parm = "&ins_id=" + vo[0]+ "&ins_no=" + vo[1];
    		parent.$.etDialog.open({
                url: 'hrp/ass/assinsmain/assInsMainUpdatePage.do?isCheck=false'+parm,
                width: $(window).width(),
                height: $(window).height(),
                title: '资产安装',
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
		}
    </script>
</head>

<body>
    <table class="table-layout">
        <tr>
            <td class="label" style="width: 100px;">安装日期：</td>
            <td class="ipt" style="width: 220px;">
                <input id="start_date" type="text" style="width: 100px"/>至 <input id="end_date" type="text" style="width: 100px"/>
            </td>
            <td class="label" style="width: 100px;">合同名称：</td>
            <td class="ipt"><select id="pact_code" style="width: 180px" disabled="disabled"></select> </td>
            <td class="label" style="width: 100px;">供应商：</td>
            <td class="ipt"> <select id="sup_no" style="width: 180px" disabled="disabled"></select> </td>
        </tr>
        <tr>
        	<td class="label" style="width: 100px;">安装单号：</td>
            <td class="ipt"><input id="ins_no" type="text" /> </td>
             <td class="label" style="width: 100px;">安装科室：</td>
            <td class="ipt"><input id="dept_id" type="text" style="width: 180px"/> </td>
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