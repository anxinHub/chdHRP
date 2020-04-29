<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="${path}/resource.jsp">
<jsp:param value="grid,select,dialog,checkbox,datepicker" name="plugins" />
</jsp:include>
<script src="<%=path%>/lib/map.js"></script>
<script>
	var grid, year_input , month_input , ciCodeSelect ,dept_codeSelect ;
	$(function () {
		loadDict();
	    initGrid();
	    
	})
    function query() {
        params = [
			{ name: 'year', value: year_input.getValue() },
			{ name: 'month', value: month_input.getValue() },
			{ name: 'consum_code', value: ciCodeSelect.getValue() },
			{ name: 'dept_code', value: dept_codeSelect.getValue() }
        ];
        grid.loadData(params);
    };
        
	function initGrid() {
            var columns = [
                 {display: '年', name: 'year',width: '5%',editable:setEdit ,
   						 editor: {
   							type: 'select',
   							keyField:'year',
   							url:"../queryAssYear.do?isCheck=false" ,
   							keySupport: true,
   							autocomplete: true,
   						},
   				 },
   				 {display: '月', name: 'month_name',width: '5%',editable:setEdit ,
   						 valueField: 'id', textField: 'text',
   						 editor: {
   							type: 'select',
   							keyField: 'month',
   							source: [{ "id": "01", "text": "01月",label:"01月"}, 
   							         { "id": "02", "text": "02月",label:"02月"}, 
   							         { "id": "03", "text": "03月",label:"03月"}, 
   							         { "id": "04", "text": "04月",label:"04月"}, 
   							         { "id": "05", "text": "05月",label:"05月"}, 
   							         { "id": "06", "text": "06月",label:"06月"}, 
   							         { "id": "07", "text": "07月",label:"07月"}, 
   							         { "id": "08", "text": "08月",label:"08月"}, 
   							         { "id": "09", "text": "09月",label:"09月"}, 
   							         { "id": "10", "text": "10月",label:"10月"}, 
   							         { "id": "11", "text": "11月",label:"11月"}, 
   							         { "id": "12", "text": "12月",label:"12月"}, 
   							      ],
   							keySupport: true,
   							autocomplete: true,
   						},
   				 },        
                 {display: '消耗资源', name: 'consum_desc',width: '10%', editable:setEdit ,
            		 editor: {
	           		     type: 'select',  //编辑框为下拉框时
	           		  	 keyField: 'consum_code',
	           		     url:"../queryAssEqcConsumableItem.do?isCheck=false"
	           		 }	 
            	 },
            	 {display: '使用科室', name: 'dept_name',width: '10%', editable:setEdit ,
            		 editor: {
	           		     type: 'select',  //编辑框为下拉框时
	           		  	 keyField: 'dept_code',
	           		     url:"../queryDeptDict.do?isCheck=false"
	           		 }	 
            	 },
            	 {display: '单位', name: 'unit_name',align: 'center', width: '7%',
            		 editor: {
            			 type: 'select',  //编辑框为下拉框时
	           		  	 keyField: 'unit_code',
	           		     url:"../queryHosUnit.do?isCheck=false"
            			 
            		 }
            	 },
            	 {display: '数量类型', name: 'quantity_type_name', width: '10%',align: 'right',
            		 editor: {
            			 type: 'select',  //编辑框为下拉框时
	           		  	 keyField: 'quantity_type',
	           		  	 source:[{ "id": "1", "text": "绝对量",label:"绝对量"},
	           		  	 	        { "id": "2", "text": "相对量",label:"相对量"},
	           		  	 	  		{ "id": "3", "text": "相对量已计算绝对量",label:"相对量已计算绝对量"}]
            			 
            		 }
            	 },
            	 {display: '数量', name: 'quantity',align: 'center', width: '8%', editor: { type: 'float' }},
            	 {display: '单价', name: 'price', width: '10%',align: 'right',editor: {type: 'float'},
            		 render : function(ui){
            			 if(ui.rowData.price){
            				 return formatNumber(ui.rowData.price,2,1);
            			 }else{
            				 return 0.00 ;
            			 }
            		 }
            	 },
            	 {display: '金额', name: 'amount',align: 'right', width: '10%',editor: {type: 'float'},
            		 render : function(ui){
            			 if(ui.rowData.amount){
            				 return formatNumber(ui.rowData.amount,2,1);
            			 }else{
            				 return 0.00 ;
            			 }
            		 }
            	 },
            	 {display: '领用日期', name: 'req_date', width: '8%',editor: {type: 'date',}},
            	 {display: '备注', name: 'remark', align: 'left', width: '14%'}
	         	 
            ];
            var paramObj = {
            	height: '97%',
            	width:'100%',
            	checkbox: true,editable:true,
                dataModel: {
                    url: 'queryAssEqPublicConsumRequisite.do'
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
	                    		this.year + "@" +
			                    this.month  + "@" +
	                    		this.consum_code + "@" +
			                    this.dept_code  + "@" +
			                    this.unit_code + "@" +
			                    this.quantity_type + "@" +
			                    this.quantity + "@" +
		                        this.price + "@" +
		                        this.amount + "@" +
		                        this.req_date + "@" +
		                        (this.remark ? this.remark : "") + "@" +
		                        this._rowIndx + "@" +
		                        '1' +'@' //添加数据标识
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
	                    		this.year + "@" +
			                    this.month  + "@" +
	                    		this.consum_code + "@" +
			                    this.dept_code  + "@" +
			                    this.unit_code + "@" +
			                    this.quantity_type + "@" +
			                    this.quantity + "@" +
		                        this.price + "@" +
		                        this.amount + "@" +
		                        this.req_date + "@" +
		                        (this.remark ? this.remark : "") + "@" +
		                        this._rowIndx + "@" +
		                        '2'  +'@' //修改数据标识
		                    )
		                });
		            }
		            ajaxPostData({
		                url: "saveAssEqPublicConsumRequisite.do?isCheck=false",
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
	            if (!v.year) {
	                rowm += "[年]、";
	            }
	            if (!v.month) {
	                rowm += "[月]、";
	            }
	            if (!v.consum_code) {
	                rowm += "[消耗资源]、";
	            }
	            if (!v.dept_code) {
	                rowm += "[使用科室]、";
	            }
	            if (!v.unit_code) {
	                rowm += "[单位]、";
	            }
	            if (!v.price) {
	                rowm += "[单价]、";
	            }
	            if (!v.quantity) {
	                rowm += "[数量]、";
	            }
	            if (!v.amount) {
	                rowm += "[金额]、";
	            }
	            if (!v.req_date) {
	                rowm += "[领用日期]、";
	            }
	            
	            if (rowm != "") {
	                rowm = "第" + (Number(v._rowIndx) + 1) + "行" + rowm.substring(0, rowm.length - 1) + "不能为空" + "\n\r";
	            }
	            msg += rowm ;
	            var key = v.consum_code + v.dept_code  
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
		            		 rowdata.year + "@" +
		            		 rowdata.month +"@" +
		            		 rowdata.consum_code + "@" +
		            		 rowdata.dept_code +"@" 
		                 )
		         });
		         $.etDialog.confirm('确定删除?', function () {
		             ajaxPostData({
		                 url: 'deleteAssEqPublicConsumRequisite.do',
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
        	year_input = $("#year").etDatepicker({
                view: "years",
                minView: "years",
                dateFormat: "yyyy",
                clearButton: false,
                onChange: query
                  
            });
        	month_input = $("#month").etDatepicker({
                view: "months",
                minView: "months",
                dateFormat: "mm",
                clearButton: false,
                onChange: query
                  
            });
        	ciCodeSelect = $("#consum_code").etSelect({
				url: "../queryAssEqcConsumableItem.do?isCheck=false",
				defaultValue: "none",
				onChange: query
			})
			dept_codeSelect =  $("#dept_code").etSelect({
				url: "../queryDeptDict.do?isCheck=false",
				defaultValue: "none",
				onChange: query
			})
          
        }
    </script>
</head>

<body>
    <table class="table-layout">
        <tr>
       	   <td class="label">年度：</td>
            <td class="ipt">
                <input type="text" id="year" />
            </td>
             <td class="label">月份：</td>
            <td class="ipt">
                <input type="text" id="month" />
            </td>
        	<td class="label" style="width: 100px;">消耗资源:</td>
            <td class="ipt">
                <input id="consum_code"  style="width: 180px" type="text" />
            </td>
            <td class="label" style="width: 100px;">使用科室:</td>
            <td class="ipt">
                <input id="dept_code" type="text" style="width: 180px" />
            </td>
         </tr>
    </table>
    <div id="mainGrid"></div>
</body>

</html>

