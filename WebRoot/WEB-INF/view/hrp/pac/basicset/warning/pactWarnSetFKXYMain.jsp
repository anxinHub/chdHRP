<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="hr,tree,grid,select,dialog" name="plugins" />
	</jsp:include>
    <script>
        var grid;
        var query = function () {
            params = [
                { name: 'type_name', value: $("#type_name").val() },
            ];
            grid.loadData(params);
        };
        var save = function (data) {
        	//var pactexew=null;
        	if (data.pact_exe_w.indexOf("%") >= 0) {
                alert("执行预警填写规则0.00");
                //data.pact_exe_w = parseFloat(data.pact_exe_w)/100.00;
                return;
            }
        	if (data.pact_exe_w > 1) {
                alert("执行预警值不允许超过100%");
                return;
            }
        	ajaxPostData({
                url: 'updatePactWarnSetFKXY.do',
                data: {
                	pact_type_code:data.pact_type_code,
    				pact_end_w:data.pact_end_w,
    				pact_exe_w:data.pact_exe_w,
                },
                delayCallback:true,
                success: function (responseData) {
                	if(responseData.state=="true"){
    	                query();
    	            }
                }
            })
        };
        var initGrid = function () {
            var columns = [
            	 { display: '协议类型编码', name: 'pact_type_code',width: '15%',editable: false},
                 { display: '协议类型名称', name: 'type_name',width: '19%',editable: false},
                 { display: '协议到期预警(天)', name: 'pact_end_w', align: 'center', width: '15%',editable: true},
                 { display: '协议执行预警(%)', name: 'pact_exe_w', align: 'center', width: '15%',editable: true,render:toPercent,type: 'float',editor: { type: 'float' }},
                 { display: '操作', name: 'edit', width: '20%', align:'center',
                	render: function (ui) {
                		return  '<button class="savebtn" row-index="' + ui.rowIndx + '">保存</button>'
                    }
                }

            ];
            var paramObj = {
            	editable: true,
            	height: '97%',
            	width:'100%',
                dataModel: {
                    url: 'queryPactWarnSetFKXY.do?isCheck=false&FKXY_Attr='+"01"
                },
                rowDblClick: function (event, ui) {
                    var rowData = ui.rowData;
                },
                columns: columns,
                toolbar: {
                    items: [
                        { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                        /* { type: 'button', label: '保存', listeners: [{ click: save }], icon: 'save' } */
                    ]
                }
            };
            grid = $("#mainGrid").etGrid(paramObj);
			
            $("#mainGrid").on('click', '.savebtn', function () {
                var rowIndex = $(this).attr('row-index');
                var currentRowData = grid.getAllData()[rowIndex];
                save(currentRowData);
            })
        };
		
        function toPercent(rowData,index,value) { 
        	//console.log(rowData);
        	var pactexew = rowData.rowData.pact_exe_w;
            if (pactexew==null){
            	//$("#pact_exe_w").attr("value",'请输入');
            	return null;
            }else{
             	//alert(pactexew);
           	 	return '<span style="color: red">'+(Math.round(pactexew * 10000).toFixed(2) / 100.00) + "%" +'</span>';// 小数点后两位百分比
            } 
       	}
        
        $(function () {
            initGrid();
        })
    </script>
</head>

<body>
    <table class="table-layout">
        <tr>
            <td class="label" style="width: 100px;">协议类型：</td>
            <td class="ipt">
                <input id="type_name" type="text" />
            </td>
        </tr>
    </table>
    <div id="mainGrid"></div>
</body>

</html>

