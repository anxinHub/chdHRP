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
	var grid, unit_codeSelect,is_seperate_feeCheck;
	var editFlag = false ;//收费价格 能否编辑用
	$(function () {
		loadDict();
	    initGrid();
	    
	})
    function query() {
        params = [
			{ name: 'consum_code', value: $("#consum_code").val() },
            { name: 'unit_code', value: unit_codeSelect.getValue() },
            { name: 'price', value: $("#price").val()  },
            { name: 'is_seperate_fee', value: is_seperate_feeCheck.checked?1:0 }
        ];
        grid.loadData(params);
    };
        
	function initGrid() {
            var columns = [
                 {display: '名称', name: 'consum_desc',width: '20%'},
            	 {display: '编码', name: 'consum_code',width: '15%', editable:setEdit},
            	 {display: '单价', name: 'price', width: '15%',align: 'right',editor: {type: 'float'},
            		 render : function(ui){
            			 if(ui.rowData.price){
            				 return formatNumber(ui.rowData.price,2,1);
            			 }else{
            				 return 0.00 ;
            			 }
            		 }
            	 },
            	 {display: '单位', name: 'unit_name',align: 'center', width: '10%',
            		 editor: {
            			 type: 'select',  //编辑框为下拉框时
	           		  	 keyField: 'unit_code',
	           		     url:"../queryHosUnit.do?isCheck=false"
            			 
            		 }
            	 },
	         	 {display: '是否单独收费', name: 'is_seperate_fee_name', align: 'center', width: '10%',
	         		 editor: {
            			 type: 'select',  //编辑框为下拉框时
            			 keyField : "is_seperate_fee",
	           		 	 source:[{ "id": "1", "text": "是",label:"是"},{ "id": "0", "text": "否",label:"否"}],
            		 },
            		 
	         	 },
	         	 {display: '收费价格', name: 'pay_price', width: '15%',align: 'right',editor: {type: 'float'},
		         		render : function(ui){
		           			 if(ui.rowData.pay_price){
		           				 return formatNumber(ui.rowData.pay_price,2,1);
		           			 }else{
		           				 return 0.00 ;
		           			 }
		           		}
	         	 }
	         	 
            ];
            var paramObj = {
            	height: '97%',
            	width:'100%',
            	checkbox: true,editable:true,
                dataModel: {
                    url: 'queryAssEqcConsumableItem.do'
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
        
     	// 根据 group_id 是否存在 返回 true 或 false  控制  收费价格 单元格可否编辑 用  
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
		                        this.consum_code + "@" +
		                        this.consum_desc + "@" +
		                        this.price + "@" +
		                        this.unit_code + "@" +
		                        this.is_seperate_fee  + "@" +
		                        (this.pay_price ? this.pay_price : "") + "@" +
		                        this._rowIndx + "@" +
		                        '1' //添加数据标识
		                    )
		                });
		            }
		            if (data.updateList.length > 0) {

		                var updateData = data.updateList;

		                if (!validateGrid(updateData)) {
		                    return false;
		                }
		                $(updateData).each(function() {
		                    ParamVo.push(
	                    		this.consum_code + "@" +
		                        this.consum_desc + "@" +
		                        this.price + "@" +
		                        this.unit_code + "@" +
		                        this.is_seperate_fee  + "@" +
		                        (this.pay_price ? this.pay_price : "") + "@" +
		                        this._rowIndx + "@" +
		                        '2' //修改数据标识
		                    )
		                });
		            }
		            ajaxPostData({
		                url: "saveAssEqcConsumableItem.do?isCheck=false",
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
	        var rowm2 = "";
	        debugger ;
	        //判断grid 中的数据是否重复或者为空
	        var targetMap = new HashMap();
	        $.each(data, function(i, v) {
	            rowm = "";
	            if (!v.consum_code) {
	                rowm += "[编码]、";
	            }
	            if (!v.consum_desc) {
	                rowm += "[名称]、";
	            }
	            if (!v.unit_code) {
	                rowm += "[计价单位]、";
	            }
	            if (!v.price) {
	                rowm += "[单价]、";
	            }
	            if ((v.is_seperate_fee == '' || v.is_seperate_fee == 'undefined') && v.is_seperate_fee!= 0) {
	                rowm += "[是否单独收费]、";
	            }else{
	            	if(v.is_seperate_fee == 1){
	            		if (!v.pay_price) {
	    	                rowm += "[收费价格]、";
	    	            }
	            	}else{
	            		if(v.pay_price){
	            			rowm2 = "[收费价格]"
	            		}
	            	}
	            	
	            }
	            
	            if (rowm != "") {
	                rowm = "第" + (Number(v._rowIndx) + 1) + "行" + rowm.substring(0, rowm.length - 1) + "不能为空" + "\n\r";
	            }
	            if (rowm2 != "") {
	                rowm2 = "第" + (Number(v._rowIndx) + 1) + "行" + rowm2 + "不能填写(是否单独收费为是时才可填写)" + "\n\r";
	            }
	            msg += rowm + rowm2;
	            var key = v.consum_code 
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
		            		 rowdata.consum_code 
		                 )
		         });
		         $.etDialog.confirm('确定删除?', function () {
		             ajaxPostData({
		                 url: 'deleteAssEqcConsumableItem.do',
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
            unit_codeSelect = $("#unit_code").etSelect({
				url: "../queryHosUnit.do?isCheck=false",
				defaultValue: "none",
				onChange: query
			});
            
            is_seperate_feeCheck = $("#is_seperate_fee").etCheck({
     			onChange: query
            });
        }
    </script>
</head>

<body>
    <table class="table-layout">
        <tr>
        	<td class="label" style="width: 100px;">资源项:</td>
            <td class="ipt">
                <input id="consum_code" type="text" style="width: 180px" />
            </td>
            <td class="label" style="width: 100px;">计量单位:</td>
            <td class="ipt">
                <select id="unit_code" style="width: 180px" type="text" ></select>
            </td>
            <td class="label" style="width: 100px;">单价:</td>
            <td class="ipt">
                <input id="price" style="width: 180px" type="text" />
            </td>
         	<td class="label" style="width: 100px;">是否单独收费:</td>
            <td class="ipt">
                <input  id="is_seperate_fee" style="width: 180px" type="checkbox"/>
            </td>
        </tr>
    </table>
    <div id="mainGrid"></div>
</body>

</html>

