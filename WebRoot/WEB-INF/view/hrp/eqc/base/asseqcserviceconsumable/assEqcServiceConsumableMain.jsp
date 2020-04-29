<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="${path}/resource.jsp">
<jsp:param value="grid,select,dialog,checkbox" name="plugins" />
</jsp:include>
<script src="<%=path%>/lib/map.js"></script>
<script>
	var grid, charge_kindSelect , consum_codeSelect, quanlitySelect , quantity_typeSelect ; 
	var unit_codeSelect,type_nameSelect,cycle_numSelect ,cycle_nuitSelect, month_stat_flagCheck;
	$(function () {
		loadDict();
	    initGrid();
	    
	})
    function query() {
        params = [
			//{ name: 'charge_item_id', value: $("#charge_item_id").val() },
			{ name: 'charge_kind_id', value: $("#charge_kind_id").val() },
			{ name: 'consum_code', value: $("#consum_code").val() },
			{ name: 'quantity_type', value: $("#quantity_type").val() },
			{ name: 'quantity', value: $("#quantity").val() },
            { name: 'unit_code', value: unit_codeSelect.getValue() },
            { name: 'price', value: $("#price").val()  },
            { name: 'type_name', value: $("#type_name").val()  },
            { name: 'cycle_nuit', value: $("#cycle_nuit").val()  },
            { name: 'cycle_num', value: $("#cycle_num").val()  },
            { name: 'month_stat_flag', value: month_stat_flagCheck.checked?1:0 }
        ];
        grid.loadData(params);
    };
        
	function initGrid() {
            var columns = [
            	 {display: '服务项目', name: 'charge_kind_name',width: '10%', editable:setEdit ,
            		 editor: {
	           		     type: 'select',  //编辑框为下拉框时
	           		  	 keyField: 'charge_kind_id',
	           		     url:"../queryCostChargeKind.do?isCheck=false",
	           		  	 change:function (rowdata,celldata) {
							grid.updateRow(celldata.rowIndx, {
								charge_kind_name : celldata.selected.text.split(" ")[1]
							});
						}
							
	           		 }	 
            	 },
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
            	 {display: '单位', name: 'unit_name', width: '10%'},
            	 {display: '数量类型', name: 'quantity_type_name', width: '10%',align: 'right',
            		 editor: {
            			 type: 'select',  //编辑框为下拉框时
	           		  	 keyField: 'quantity_type',
	           		  	 source:[{ "id": "1", "text": "绝对量",label:"绝对量"},
	           		  	 	        { "id": "2", "text": "相对量",label:"相对量"},
	           		  	 	  		{ "id": "3", "text": "相对量已计算绝对量",label:"相对量已计算绝对量"}]
            			 
            		 }
            	 },
            	 {display: '消耗数量', name: 'quantity', width: '10%',align: 'right',editor: {type: 'float'}},
	         	 {display: '维护周期', name: 'cycle_num', width: '8%',editor: {type: 'float'}},
	         	 {display: '周期单位', name: 'cycle_nuit_name', width: '10%',
	         		editor: {
	        			 type: 'select',  //编辑框为下拉框时
	           		  	 keyField: 'cycle_nuit',
           		  	 	 source:[{ "id": "1", "text": "年",label:"年"},
           		  	 	        { "id": "2", "text": "月",label:"月"},
		           		  	 	{ "id": "3", "text": "天",label:"天"},
		           		  	 	{ "id": "4", "text": "时",label:"时"}]
	        			 
	        		 }
				 },
	         	 {display: '类型', name: 'type_name_name', width: '8%',
	         		 editor: {
	        			 type: 'select',  //编辑框为下拉框时
	           		  	 keyField: 'type_name',
	           		  	 source:[{ "id": "1", "text": "使用",label:"使用"},
           		  	 	        { "id": "2", "text": "质控",label:"质控"}]
	        			 
	        		 }
	         	 },
	         	{display: '月份统计', name: 'month_stat_flag_name', width: '8%',
	         		 editor: {
	        			 type: 'select',  //编辑框为下拉框时
	           		  	 keyField: 'month_stat_flag',
           		  	 	 source:[{ "id": "1", "text": "是",label:"是"},
           		  	 	        { "id": "0", "text": "否",label:"否"}]
	        		 }
	         	 }
	         	 
            ];
            var paramObj = {
            	height: '97%',
            	width:'100%',
            	checkbox: true,editable:true,
                dataModel: {
                    url: 'queryAssEqcServiceConsumable.do'
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
        
        // 根据 group_id 是否存在 返回 true 或 false  控制单元格可否编辑 用 
        function setEdit(ui){
       		 if(ui.rowData && ui.rowData.group_id){
       			 return false ;
       		 }else{
       			 return true ;
       		 }
        }
        
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
		                        this.charge_kind_id + "@" +
		                        this.consum_code + "@" +
		                        this.quantity + "@" +
		                        this.quantity_type + "@" +
		                        this.month_stat_flag  + "@" +
		                        (this.cycle_num ? this.cycle_num : "") + "@" +
		                        (this.cycle_nuit ? this.cycle_nuit : "") + "@" +
		                        (this.type_name ? this.type_name : "") + "@" +
		                        this._rowIndx + "@" +
		                        '1' //添加数据标识
		                    )
		                });
		            }
		            if (data.updateList.length > 0) {

		                var updateData = data.updateList;
		                $(updateData).each(function() {
		                    ParamVo.push(
	                    		this.charge_kind_id + "@" +
		                        this.consum_code + "@" +
		                        this.quantity + "@" +
		                        this.quantity_type + "@" +
		                        this.month_stat_flag  + "@" +
		                        (this.cycle_num ? this.cycle_num : "") + "@" +
		                        (this.cycle_nuit ? this.cycle_nuit : "") + "@" +
		                        (this.type_name ? this.type_name : "") + "@" +
		                        this._rowIndx + "@" +
		                        '2' //修改数据标识
		                    )
		                });
		            }
		            ajaxPostData({
		                url: "saveAssEqcServiceConsumable.do?isCheck=false",
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
	            if (!v.charge_kind_id) {
	                rowm += "[服务项目]、";
	            }
	            if (!v.consum_code) {
	                rowm += "[消耗资源]、";
	            }
	            if (!v.quantity) {
	                rowm += "[消耗数量]、";
	            }
	            if (!v.quantity_type) {
	                rowm += "[数量类型]、";
	            }
	            
	            if (rowm != "") {
	                rowm = "第" + (Number(v._rowIndx) + 1) + "行" + rowm.substring(0, rowm.length - 1) + "不能为空" + "\n\r";
	            }
	            msg += rowm;
	            var key = v.charge_kind_id + v.consum_code
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
		            		 rowdata.charge_kind_id + "@" +
		            		 rowdata.consum_code
		                 )
		         });
		         $.etDialog.confirm('确定删除?', function () {
		             ajaxPostData({
		                 url: 'deleteAssEqcServiceConsumable.do',
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
        	charge_kindSelect= $("#charge_kind_id").etSelect({
				url: "../queryCostChargeKind.do?isCheck=false",
				defaultValue: "none",
				onChange: query
			}); 
        	consum_codeSelect = $("#consum_code").etSelect({
				url: "../queryAssEqcConsumableItem.do?isCheck=false",
				defaultValue: "none",
				onChange: query
			});  
        	quantity_typeSelect = $("#quantity_type").etSelect({
        		options: [{ "id": "1", "text": "绝对量"},{ "id": "2", "text": "相对量"},{ "id": "3", "text": "相对量已计算绝对量"}],
				defaultValue: "none",
				onChange: query
			});   

			unit_codeSelect = $("#unit_code").etSelect({
				url: "../queryHosUnit.do?isCheck=false",
				defaultValue: "none",
				onChange: query
			});
        	type_nameSelect = $("#type_name").etSelect({
        		options: [{ "id": "1", "text": "使用"},
    		  	 	      { "id": "2", "text": "质控"}],
				defaultValue: "none",
				onChange: query
			}); 
        	cycle_nuitSelect= $("#cycle_nuit").etSelect({
        		options: [{ "id": "1", "text": "年"},
     		  	 	        { "id": "2", "text": "月"},
	           		  	 	{ "id": "3", "text": "天"},
	           		  	 	{ "id": "4", "text": "时"}],
				defaultValue: "none",
				onChange: query
			}); 
            month_stat_flagCheck = $("#month_stat_flag").etCheck({
     			onChange: query
            });
        }
    </script>
</head>

<body>
    <table class="table-layout">
        <tr>
        	<td class="label" style="width: 100px;">服务项目:</td>
            <td class="ipt">
                <input id="charge_kind_id" type="text" style="width: 180px" />
            </td>
            <td class="label" style="width: 100px;">消耗资源:</td>
            <td class="ipt">
                <select id="consum_code" style="width: 180px" type="text" ></select>
            </td>
            <td class="label" style="width: 100px;">数量类型:</td>
            <td class="ipt">
                <select id="quantity_type" style="width: 180px" type="text" ></select>
            </td>
       </tr>
       <tr>
         	<td class="label" style="width: 100px;">数量:</td>
            <td class="ipt">
                <input  id="quantity" style="width: 180px" type="text"/>
            </td>
            <td class="label" style="width: 100px;">单位:</td>
            <td class="ipt">
                <select  id="unit_code" style="width: 180px" type="text"></select>
            </td>
            <td class="label" style="width: 100px;">类型:</td>
            <td class="ipt">
                <select  id="type_name" style="width: 180px" type="text"></select>
            </td>
        </tr>
         <tr>
         	<td class="label" style="width: 100px;">维护周期:</td>
            <td class="ipt">
                <input  id="cycle_num" style="width: 100px" type="text"/>
                <select  id="cycle_nuit" style="width: 80px" type="text" ></select>
            </td>
            <td class="label" style="width: 100px;">月份统计:</td>
            <td class="ipt">
                <input  id="month_stat_flag" style="width: 180px" type="checkbox"/>
            </td>
        </tr>
    </table>
    <div id="mainGrid"></div>
</body>

</html>

