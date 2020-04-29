<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="${path}/resource.jsp">
<jsp:param value="grid,select,dialog,datepicker" name="plugins" />
</jsp:include>
<script src="<%=path%>/lib/map.js"></script>
<script>
	var grid, analysis_codeSelect,ur_eq_groupSelect,charge_kindSelect,charge_itemSelect,dept_codeSelect,consum_codeSelect;
	$(function () {
		loadDict();
	    initGrid();
	    
	})
    function query() {
        params = [
                  { name: 'analysis_code', value: analysis_codeSelect.getValue() },
                  { name: 'charge_kind_id', value: charge_kindSelect.getValue() },
                  { name: 'dept_code', value: dept_codeSelect.getValue() },
                  { name: 'consum_code', value: $("#consum_code").val() },
        ];
        grid.loadData(params);
    };
        
	function initGrid() {
            var columns = [
                 {display: '分析项', name: 'analysis_name',width: '5%',editable:false},  
                 {display: '服务项目', name: 'charge_kind_name',width: '10%' ,
            		 editor: {
	           		     type: 'select',  // 编辑框为下拉框时
	           		  	 keyField: 'charge_kind_id',
	           		     url:"../queryCostChargeKind.do?isCheck=false",
	           		  	 change:function (rowdata,celldata) {
							grid.updateRow(celldata.rowIndx, {
									charge_kind_name : celldata.selected.text.split(" ")[1]
								});
						}
							
	           		 }	 
            	 },
                 {display: '消耗项目', name: 'consum_desc', width: '10%',
            		 editor: {
            			type: 'select',  // 编辑框为下拉框时
	           		  	keyField: 'consum_code',
	           		    url:"../queryAssEqcConsumableItem.do?isCheck=false",
           		    	change:function (rowdata,celldata) {
							grid.updateRow(celldata.rowIndx, {
								unit_name : celldata.selected.text.split(" ")[1]
							});
						}
            		 }
            	 },
            	 {display: '使用科室', name: 'dept_name',width: '10%', editable:false},
            	 {display: '工作量', name: 'work_load_num', width: '8%',editor: {type: 'float'},editable:setEdit },
            	 {display: '单价', name: 'price', align: 'left', width: '10%'},
            	 {display: '单位', name: 'unit_name',align: 'center', width: '7%' ,
            		 editor: {
            			 type: 'select',  // 编辑框为下拉框时
	           		  	 keyField: 'unit_code',
	           		     url:"../queryHosUnit.do?isCheck=false"
            			 
            		 }
            	 },
            	 {display: '金额', name: 'amount', align: 'left', width: '10%'},
            	 {display: '患者名称', name: 'patient_name', align: 'left', width: '8%',editable:setEdit },
	         	 {display: '状态', name: 'status_name',align: 'center', width: '7%',editable:false,
            		 editor: {
            			type: 'select',
     					keyField: 'status',
     					source: [{ "id": "0", "text": "新增",label:"新增"}, 
     					         { "id": "1", "text": "提交",label:"提交"},
     					         { "id": "2", "text": "审核",label:"审核"},
     					         { "id": "3", "text": "作废",label:"作废"}
     					      ],
     					keySupport: true,
     					autocomplete: true
     				}
            	 },
	         	 {display: '备注', name: 'remark', align: 'left', width: '10%'}
            ];
            var paramObj = {
            	height: '97%',
            	width:'100%',
            	checkbox: false,
            	editable:false,
                dataModel: {
                    url: 'queryAssEqUseConsItem.do'
                },
                columns: columns,
                toolbar: {
                    items: [
                        { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                        /*{ type: 'button', label: '增加行', listeners: [{ click: add }], icon: 'add' },
                        { type: 'button', label: '保存', listeners: [{ click: save }], icon: 'save' },
                        { type: 'button', label: '删除', listeners: [{ click: remove }], icon: 'del' } */
                    ]
                }
            };
            grid = $("#mainGrid").etGrid(paramObj);
        };
        
        // 根据 group_id 是否存在 返回 true 或 false  控制单元格可否编辑 用 
        function setEdit(ui){
       		 if(ui.rowData && ui.rowData.status!='0'){
       			 return false ;
       		 }else{
       			 return true ;
       		 }
        }        
        function loadDict(){
            analysis_codeSelect = $("#analysis_code").etSelect({
            	 url: "../queryAssAnalysisObject.do?isCheck=false",
				defaultValue: "none",
				onChange: query
			});           
            charge_kindSelect = $("#charge_kind_id").etSelect({
				url: "../queryCostChargeKind.do?isCheck=false",
				defaultValue: "none",
				onChange: query
			});           
            dept_codeSelect = $("#dept_code").etSelect({
				url: "../queryDeptDict.do?isCheck=false",
				defaultValue: "none",
				onChange: query
			});
            consum_codeSelect = $("#consum_code").etSelect({
				url: "../queryAssEqcConsumableItem.do?isCheck=false",
				defaultValue: "none",
				onChange: query
			}); 
        }
    </script>
</head>

<body>
    <table class="table-layout">
        <tr>
            <td class="label" style="width: 100px;">分析项:</td>
            <td class="ipt">
                <select id="analysis_code"  style="width: 180px" type="text" ></select>
            </td>          
           	<td class="label" style="width: 100px;">使用科室:</td>
                <td class="ipt">
                    <input id="dept_code" style="width: 180px" type="text"/>
                </td>
            <td class="label" style="width: 100px;">服务项目:</td>
            <td class="ipt">
                <select id="charge_kind_id" type="text" style="width: 180px" ></select>
            </td> 
             <td class="label" style="width: 100px;">消耗项目:</td>
            <td class="ipt">
                <select id="consum_code" style="width: 180px" type="text" ></select>
            </td>
        	      
         </tr>
    </table>
    <div id="mainGrid"></div>
</body>

</html>

