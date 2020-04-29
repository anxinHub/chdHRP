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
	var grid, analysis_codeSelect,ur_eq_groupSelect,charge_itemSelect,unit_codeSelect,dept_codeSelect,ur_usedateSelect,ur_enddateSelect;
	$(function () {
		loadDict();
	    initGrid();
	    
	})
    function query() {
        params = [
                  { name: 'analysis_code', value: analysis_codeSelect.getValue() },                 
                  { name: 'charge_item_id', value: charge_itemSelect.getValue() }
        ];
        grid.loadData(params);
    };
        
	function initGrid() {
            var columns = [
                 {display: '分析项', name: 'analysis_name',width: '15%' ,
                	 valueField: 'id', textField: 'text',
                	 editor: {
     					type: 'select',
     					keyField: 'analysis_code',
     					url:"../queryAssAnalysisObject.do?isCheck=false"
     				},
            	 },           	
            	 {display: '服务细项', name: 'charge_item_name',width: '10%' ,
            		 editor: {
	           		     type: 'select',  //编辑框为下拉框时
	           		  	 keyField: 'charge_item_id',
	           		     url:"../queryCostChargeItem.do?isCheck=false",
	           		  	 change:function (rowdata,celldata) {
							grid.updateRow(celldata.rowIndx, {
									charge_item_name : celldata.selected.text.split(" ")[1]
								});
						}
							
	           		 }	 
            	 },
				
            	<!-- {display: '细项数量', name: 'uci_servdetitemqty', width: '10%',editor: {type: 'float'} },-->
            	 {display: '消耗资源', name: 'consum_desc', width: '10%',
            		 editor: {
            			type: 'select',  //编辑框为下拉框时
	           		  	keyField: 'consum_code',
	           		    url:"../queryAssEqcConsumableItem.do?isCheck=false",
           		    	change:function (rowdata,celldata) {
							grid.updateRow(celldata.rowIndx, {
								unit_name : celldata.selected.text.split(" ")[1]
							});
						}
            		 }
            	 },
            	 {display: '单位', name: 'unit_name',align: 'center', width: '7%' ,
            		 editor: {
            			 type: 'select',  //编辑框为下拉框时
	           		  	 keyField: 'unit_code',
	           		     url:"../queryHosUnit.do?isCheck=false"
            			 
            		 }
            	 },
            	 {display: '数量', name: 'quantity', width: '10%',editor: {type: 'float'} },
            	 {display: '单价', name: 'price', align: 'left', width: '10%'},
            	 {display: '金额', name: 'amount', align: 'left', width: '10%'},
            	 {display: '数量类型', name: 'quantity_type_name', width: '10%',align: 'right',
            		 editor: {
            			 type: 'select',  //编辑框为下拉框时
	           		  	 keyField: 'quantity_type',
	           		  	 source:[{ "id": "1", "text": "绝对量",label:"绝对量"},
	           		  	 	        { "id": "2", "text": "相对量",label:"相对量"},
	           		  	 	  		{ "id": "3", "text": "相对量已计算绝对量",label:"相对量已计算绝对量"}]
            			 
            		 }
            	 },
	         	 {display: '相对量', name: 'relative_qty', width: '8%',editor: {type: 'float'} },
           	 	 {display: '单独收费消耗项单价', name: 'pay_price', align: 'left', width: '20%'},
           	 	 {display: '数量', name: 'quantity', width: '10%',editor: {type: 'float'} },
           	 	 {display: '单独收费消耗项总金额', name: 'pay_amount', align: 'left', width: '20%'},
            	 {display: '使用科室', name: 'dept_name',width: '10%' ,
					 editor: {
						     type: 'select',  //编辑框为下拉框时
						  	 keyField: 'dept_code',
						     url:"../queryDeptDict.do?isCheck=false"
						 }	 
				 },
		         {display: '使用日期', name: 'use_date', align: 'left', width: '8%',editor: {type: 'date'}}
            ];
            var paramObj = {
            	height: '97%',
            	width:'100%',
            	checkbox: true,editable:true,
                dataModel: {
                    url: 'queryAssEqUseConsumableItemM.do'
                },
                /* rowDblClick: function (event, ui) {
                    var rowData = ui.rowData;
                    update(rowData);
                }, */
                columns: columns,
                toolbar: {
                    items: [
                        { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                        { type: 'button', label: '增加行', listeners: [{ click: add }], icon: 'add' },
                        { type: 'button', label: '保存', listeners: [{ click: save }], icon: 'save' },
                        { type: 'button', label: '删除', listeners: [{ click: remove }], icon: 'del' }
                        
                    ]
                }
            };
            grid = $("#mainGrid").etGrid(paramObj);
        };
        
        
        //新增
		function add(){
			grid.addRow();
		}
		//保存
		function save () {
			 var data = grid.getChanges();
		        var ParamVo = [];

		        if (data.addList.length > 0 || data.updateList.length > 0) {

		            if (data.addList.length > 0) {

		                var addData = data.addList;

		                if (!validateGrid(addData)) {
		                    return false;
		                }
		                $(addData).each(function() {
		                	
		                    ParamVo.push(
		                        this.analysis_code + "@" +		                       
		                        this.charge_item_id + "@" +
		                        this.consum_code + "@" +
		                        this.unit_code + "@" +
		                        this.quantity + "@" +
		                        this.price + "@" +
		                        this.amount + "@" +
		                        (this.quantity_type? this.quantity_type : "") + "@" +
		                        (this.relative_qty? this.relative_qty : "") + "@" +
		                        (this.pay_price? this.pay_price : "") + "@" +
		                        (this.pay_amount? this.pay_amount : "") + "@" +
		                        this.dept_code + "@" +
		                        (this.use_date? this.use_date : "") + "@" +
		                        this._rowIndx + "@" +
		                      	//新增、修改数据标识
		                        (this.uci_userecorddr ? this.uci_userecorddr:'-1')+ "@"
		                    )
		                });
		            }
		            if (data.updateList.length > 0) {

		                var updateData = data.updateList;
		                $(updateData).each(function() {
		                    ParamVo.push(
                    			this.analysis_code + "@" +		                      
		                        this.charge_item_id + "@" +
		                        this.consum_code + "@" +
		                        this.unit_code + "@" +
		                        this.quantity + "@" +
		                        this.price + "@" +
		                        this.amount + "@" +
		                        (this.quantity_type? this.quantity_type : "") + "@" +
		                        (this.relative_qty? this.relative_qty : "") + "@" +
		                        (this.pay_price? this.pay_price : "") + "@" +
		                        (this.pay_amount? this.pay_amount : "") + "@" +
		                        this.dept_code + "@" +
		                        (this.use_date? this.use_date : "") + "@" +
		                        this._rowIndx + "@" +
		                      	//新增、修改数据标识
		                        (this.uci_userecorddr ? this.uci_userecorddr:'-1') 	+ "@"
		                        
		                    )
		                });
		            }
		            ajaxPostData({
		                url: "saveAssEqUseConsumableItemM.do?isCheck=false",
		                data: { ParamVo: ParamVo.toString() },
		                success: function(responseData) {
		                    if (responseData.state == "true") {
		                        $.etDialog.success('保存成功');
		                        query();
		                    } else {
		                        $.etDialog.error(responseData.message)
		                    }
		                }
		            });
		        } else {
		            $.etDialog.warn('没有需要保存的数据!');
		        }
		};
		// 数据校验
		function validateGrid(data) {
	        var msg = "";
	        var rowm = "";
	        //判断grid 中的数据是否重复或者为空
	        var targetMap = new HashMap();
	        $.each(data, function(i, v) {
	            rowm = "";
	            if (!v.analysis_code) {
	                rowm += "[分析项]、";
	            }	            
	            if (!v.charge_item_id) {
	                rowm += "[服务细项]、";
	            }	            
	            if (!v.consum_code) {
	                rowm += "[消耗资源]、";
	            }
	            if (!v.unit_code) {
	                rowm += "[单位]、";
	            }
	            if (!v.quantity) {
	                rowm += "[数量]、";
	            }
	            if (!v.price) {
	                rowm += "[单价]、";
	            }
	            if (!v.amount) {
	                rowm += "[金额]、";
	            }
	            if (!v.dept_code) {
	                rowm += "[使用科室]、";
	            }
	            
	            
	            if (rowm != "") {
	                rowm = "第" + (Number(v._rowIndx) + 1) + "行" + rowm.substring(0, rowm.length - 1) + "不能为空;" + "\n\r";
	            }
	            msg += rowm ;
	            var key = v.analysis_code + v.ur_eq_group + v.user_id
	            var value = "第" + (Number(v._rowIndx) + 1) + "行";
	            if (targetMap.get(key) == null || targetMap.get(key) == 'undefined' || targetMap.get(key) == "") {
	                targetMap.put(key, value);
	            } else {
	                msg += targetMap.get(key) + "与" + value + "数据重复!!" + "\n\r";
	            }
	        });
	        if (msg != "") {
	            $.etDialog.warn(msg);
	            return false;
	        } else {
	            return true;
	        }
	    }
		
		//删除
		function remove() {
			 var data = grid.selectGet();
		     if (data.length == 0) {
		         $.etDialog.error('请选择行');
		     } else {
		    	 var ParamVo = [];
		         $(data).each(function () {
		             var rowdata = this.rowData;
		             ParamVo.push(
		            		 rowdata.uci_userecorddr 
		                 )
		         });
		         $.etDialog.confirm('确定删除?', function () {
		             ajaxPostData({
		                 url: 'deleteAssEqUseConsumableItemM.do',
		                 data: {
		                	 ParamVo: ParamVo.toString()
		                 },
		                 success: function () {
		                     grid.deleteRows(data);
		                 }
		             })
		         });
		     }
		};        
        function loadDict(){
             analysis_codeSelect = $("#analysis_code").etSelect({
            	 url: "../queryAssAnalysisObject.do?isCheck=false",
				defaultValue: "none",
				onChange: query
			});            
            charge_itemSelect = $("#charge_item_id").etSelect({
				url: "../queryCostChargeItem.do?isCheck=false",
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
           
            <td class="label" style="width: 100px;">服务细项:</td>
            <td class="ipt">
                <select id="charge_item_id" type="text" style="width: 180px" ></select>
            </td>
           
         </tr>
    </table>
    <div id="mainGrid"></div>
</body>

</html>

