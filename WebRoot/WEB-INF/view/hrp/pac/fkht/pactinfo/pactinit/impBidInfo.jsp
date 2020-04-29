<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="hr,tree,grid,select,dialog,datepicker,validate" name="plugins" />
	</jsp:include>
    <script>
        var grid;
        var sup_select ;
        var query = function () {
            params = [
                { name: 'sup_id', value: sup_select.getValue()},
                { name: 'bid_code', value: $("#bid_code").val()},
                { name: 'ass_id', value: $("#ass_id").val()},
            ];
            grid.loadData(params,'queryPactBidFKHT.do?isCheck=false');
        };
        
        
        var initGrid = function () {
            var columns = [
                 { display: '资产编号', name: 'subject_code', align: 'left', width: '100px'},
                 { display: '资产名称', name: 'subject_name',  align: 'left',width: '150px'},
                 { display: '规格', name: 'item_spec', align: 'left', width: '100px'},
                 { display: '型号', name: 'item_model', align: 'left', width: '100px'},
                 { display: '生成厂家', name: 'fac_name',  align: 'left', width: '180px'},
                 { display: '品牌', name: 'item_brand', align: 'left', width: '90px'},
                 { display: '计量单位', name: 'unit_name', align: 'left', width: '100px'},
                 { display: '招标数量', name: 'apply_amount', align: 'center', width: '100px'},
                 { display: '招标单价', name: 'price', align: 'center', width: '100px'},
                 { display: '已生成数量', name: 'pact_amount', align: 'center', width: '100px'},
                 { display: '剩余数量', name: 'left_amount', align: 'center', width: '100px'},
                 { display: '合同数量', name: 'amount', align: 'center', width: '100px'},
                 { display: '预计到货日期', name: 'arrive_date', align: 'center', width: '100px'}
            ];
            var paramObj = {
            	editable: false,
            	height: '97%',
            	width:'100%',
            	checkbox: true,
                dataModel: {
                    url: ''
                },
                columns: columns,
                toolbar: {
                    items: [
                        { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                        { type: 'button', label: '生成', listeners: [{ click: save }], icon: 'save' },
                        { type: 'button', label: '关闭', listeners: [{ click: this_close }], icon: 'del' },
                    ]
                }
            };
            grid = $("#mainGrid").etGrid(paramObj);
        };
        
       
        //跳转保存页面
        var save = function (data) {
        	var data = grid.selectGet();
        	if (data.length == 0) {
                $.etDialog.error('请选择行');
                return false ;
            }else{
            	$(data).each(function () {
            		var parentFrameName = parent.$.etDialog.parentFrameName;
    		        var parentWindow = parent.window[parentFrameName];
    			    parentWindow.subGrid.addRow(this.rowData);
    			    parentWindow.addMoney();//更新 合同主表总金额
                })
            	
            }
        	
        	this_close();
        };
        // 关闭
        function this_close() {
        	var curIndex = parent.$.etDialog.getFrameIndex(window.name);
	        parent.$.etDialog.close(curIndex); 
    	}
        $(function () {
            initSelect();
            initGrid();
            query();
        })
        
         var initSelect=  function(){
        	sup_select = $("#sup_id").etSelect(
            		{url: '../../../basicset/select/queryHosSupDictSelect.do?isCheck=false', 
            			defaultValue: "none"}
            		);
          }
        
    </script>
</head>

<body>
    <table class="table-layout">
        <tr>
        	<td class="label" style="width: 100px;">招标编码：</td>
            <td class="ipt"> <input id="bid_code" type='text' disabled="disabled" value="${bid_code}" style="width: 180px"/></td>
        	<td class="label" style="width: 100px;">供应商：</td>
            <td class="ipt"> <select id="sup_id" style="width: 240px"></select> </td>
        	<td class="label" style="width: 100px;">资产名称：</td>
            <td class="ipt"> <input id="ass_id" type='text' style="width: 180px"/> </td>
        </tr>
    </table>
    <div id="mainGrid"></div>
</body>

</html>

