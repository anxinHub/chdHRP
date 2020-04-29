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
	var grid, unit_codeSelect,price_typeSelect;
	$(function () {
		loadDict();
	    initGrid();
	    
	})
    function query() {
        params = [
			{ name: 'oresource_code', value: $("#oresource_code").val() },
            { name: 'unit_code', value: unit_codeSelect.getValue() },
            { name: 'price', value: $("#price").val()  },
            { name: 'price_type', value: price_typeSelect.getValue() }
        ];
        grid.loadData(params);
    };
        
	function initGrid() {
            var columns = [
                 {display: '名称', name: 'oresource_desc',width: '20%'},
            	 {display: '编码', name: 'oresource_code',width: '15%', editable:setEdit},
            	 {display: '单价类型', name: 'price_type_name',align: 'center', width: '10%',
            		 editor: {
            			 type: 'select',  //编辑框为下拉框时
	           		  	 keyField: 'price_type',
	           		  	 source:[{ "id": "1", "text": "固定单价",label:"固定单价"},{ "id": "2", "text": "无固定单价",label:"无固定单价"}]
            			 
            		 }
            	 },
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
            	 {display: '备注', name: 'remark', align: 'left', width: '27%'}
	         	 
            ];
            var paramObj = {
            	height: '97%',
            	width:'100%',
            	checkbox: true,editable:true,
                dataModel: {
                    url: 'queryAssEqcResourceType.do'
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
		                        this.oresource_code + "@" +
		                        this.oresource_desc + "@" +
		                        this.price_type  + "@" +
		                        this.price + "@" +
		                        this.unit_code + "@" +
		                        (this.remark ? this.remark : "") + "@" +
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
	                    		this.oresource_code + "@" +
			                    this.oresource_desc + "@" +
			                    this.price_type  + "@" +
			                    this.price + "@" +
			                    this.unit_code + "@" +
			                    (this.remark ? this.remark : "") + "@" +
		                        this._rowIndx + "@" +
		                        '2' //修改数据标识
		                    )
		                });
		            }
		            ajaxPostData({
		                url: "saveAssEqcResourceType.do?isCheck=false",
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
	        debugger ;
	        //判断grid 中的数据是否重复或者为空
	        var targetMap = new HashMap();
	        $.each(data, function(i, v) {
	            rowm = "";
	            if (!v.oresource_code) {
	                rowm += "[编码]、";
	            }
	            if (!v.oresource_desc) {
	                rowm += "[名称]、";
	            }
	            if (!v.price_type) {
	                rowm += "[单价类型]、";
	            }
	            if (!v.unit_code) {
	                rowm += "[单位]、";
	            }
	            if (!v.price) {
	                rowm += "[单价]、";
	            }
	            
	            if (rowm != "") {
	                rowm = "第" + (Number(v._rowIndx) + 1) + "行" + rowm.substring(0, rowm.length - 1) + "不能为空" + "\n\r";
	            }
	            msg += rowm ;
	            var key = v.oresource_code 
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
		            		 rowdata.oresource_code 
		                 )
		         });
		         $.etDialog.confirm('确定删除?', function () {
		             ajaxPostData({
		                 url: 'deleteAssEqcResourceType.do',
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
            
            price_typeSelect = $("#price_type").etSelect({
            	options:[{ "id": "1", "text": "固定单价",label:"固定单价"},{ "id": "2", "text": "无固定单价",label:"无固定单价"}],
            	defaultValue: "none",
     			onChange: query
            });
        }
    </script>
</head>

<body>
    <table class="table-layout">
        <tr>
        	<td class="label" style="width: 100px;">其他资源:</td>
            <td class="ipt">
                <input id="oresource_code" type="text" style="width: 180px" />
            </td> 
            <td class="label" style="width: 100px;">单价类型:</td>
            <td class="ipt">
                <select id="price_type" style="width: 180px" type="text" ></select>
            </td>
            <td class="label" style="width: 100px;">单位:</td>
            <td class="ipt">
                <select id="unit_code" style="width: 180px" type="text" ></select>
            </td>
            <td class="label" style="width: 100px;">单价:</td>
            <td class="ipt">
                <input id="price" style="width: 180px" type="text" />
            </td>
        </tr>
    </table>
    <div id="mainGrid"></div>
</body>

</html>

