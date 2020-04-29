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
	var grid, year_input, month_input ,analysis_codeSelect , unit_codeSelect,oresource_codeSelect;
	$(function () {
		loadDict();
	    initGrid();
	    
	})
    function query() {
        params = [
			{ name: 'year', value: year_input.getValue() },
			{ name: 'month', value: month_input.getValue() }, 
			{ name: 'analysis_code', value: analysis_codeSelect.getValue() },
			{ name: 'oresource_code', value: oresource_codeSelect.getValue() },
            { name: 'unit_code', value: unit_codeSelect.getValue() },
            { name: 'price', value: $("#price").val()  },
            { name: 'quantity', value: $("#quantity").val() },
            { name: 'amount', value: $("#amount").val() },
            { name: 'remark', value: $("#remark").val() }
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
				 {display: '月', name: 'month_name',width: '7%',editable:setEdit ,
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
            	 {display: '分析项', name: 'analysis_name',width: '12%', editable:setEdit ,
            		 editor: {
	           		     type: 'select',  //编辑框为下拉框时
	           		  	 keyField: 'analysis_code',
	           		     url:"../queryAssAnalysisObject.do?isCheck=false"
	           		 }	 
            	 },
            	 {display: '资源类型', name: 'oresource_code_name',width: '8%', editable:setEdit ,
            		 editor: {
	           		     type: 'select',  //编辑框为下拉框时
	           		  	 keyField: 'oresource_code',
	           		     url:"../queryAssEqcResourceType.do?isCheck=false"
	           		 }	 
            	 },
            	 {display: '单价', name: 'price', width: '8%',align: 'right',editor: {type: 'float'},
            		 render : function(ui){
            			 if(ui.rowData.price){
            				 return formatNumber(ui.rowData.price,2,1);
            			 }else{
            				 return 0.00 ;
            			 }
            		 }
            	 },
            	 {display: '单位', name: 'unit_name',align: 'center', width: '9%',
            		 editor: {
            			 type: 'select',  //编辑框为下拉框时
	           		  	 keyField: 'unit_code',
	           		     url:"../queryHosUnit.do?isCheck=false"
            			 
            		 }
            	 },
            	 {display: '数量', name: 'quantity',align: 'center', width: '9%', editor: { type: 'float' }},
            	 {display: '金额', name: 'amount',align: 'right', width: '10%',editor: {type: 'float'},
            		 render : function(ui){
            			 if(ui.rowData.amount){
            				 return formatNumber(ui.rowData.amount,2,1);
            			 }else{
            				 return 0.00 ;
            			 }
            		 }
            	 },
            	 {display: '状态', name: 'status_name',align: 'center', width: '7%',
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
     				},
            		 
            	 },
            	 {display: '手工录入标识', name: 'is_input_flag', align: 'center', width: '7%',
	         		 editor: {
            			 type: 'select',  //编辑框为下拉框时
	           		 	 source:[{ "id": "N", "text": "N",label:"N"},{ "id": "Y", "text": "Y",label:"Y"}]
            			 
            		 }
	         	 },
            	 {display: '备注', name: 'remark', align: 'left', width: '14%'}
	         	 
            ];
            var paramObj = {
            	height: '97%',
            	width:'100%',
            	checkbox: true,editable:true,
                dataModel: {
                    url: 'queryAssEqUsedResource.do'
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
			grid.addRow({status:'0',status_name:'新增',is_input_flag:'Y'});
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
		                    	this.year  + "@" +
		                    	this.month  + "@" +
	                    		this.analysis_code + "@" +
		                        this.oresource_code + "@" +
		                        this.price + "@" +
		                        this.unit_code + "@" +
		                        this.quantity + "@" +
		                        this.amount + "@" +
		                        this.is_input_flag + "@" +
		                        this.status + "@" +
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
	                    		this.year  + "@" +
		                    	this.month  + "@" +
	                    		this.analysis_code + "@" +
		                        this.oresource_code + "@" +
		                        this.price + "@" +
		                        this.unit_code + "@" +
		                        this.quantity + "@" +
		                        this.amount + "@" +
		                        this.is_input_flag + "@" +
		                        this.status + "@" +
		                        (this.remark ? this.remark : "") + "@" +
		                        this._rowIndx + "@" +
		                        '2' //修改数据标识
		                    )
		                });
		            }
		            ajaxPostData({
		                url: "saveAssEqUsedResource.do?isCheck=false",
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
                if (!v.analysis_code) {
	                rowm += "[分析项]、";
	            }
	            if (!v.oresource_code) {
	                rowm += "[资源类型]、";
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
	            if (!v.is_input_flag) {
	                rowm += "[手工录入标识]、";
	            }
	            if (rowm != "") {
	                rowm = "第" + (Number(v._rowIndx) + 1) + "行" + rowm.substring(0, rowm.length - 1) + "不能为空" + "\n\r";
	            }
	            msg += rowm ;
	            var key = v.year + v.month + v.analysis_code  + v.oresource_code
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
		            		 rowdata.year  + "@" +
		            		 rowdata.month  + "@" +
		            		 rowdata.analysis_code + "@" +
		            		 rowdata.oresource_code
		                 )
		         });
		         $.etDialog.confirm('确定删除?', function () {
		             ajaxPostData({
		                 url: 'deleteAssEqUsedResource.do',
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
        	analysis_codeSelect = $("#analysis_code").etSelect({
 				url: "../queryAssAnalysisObject.do?isCheck=false",
 				defaultValue: "none",
 				onChange: query
 			});
            oresource_codeSelect = $("#oresource_code").etSelect({
            	defaultValue: "none",
            	url:"../queryAssEqcResourceType.do?isCheck=false",
     			onChange: query
            });
            unit_codeSelect = $("#unit_code").etSelect({
				url: "../queryHosUnit.do?isCheck=false",
				defaultValue: "none",
				onChange: query
			});
            
          
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
        	<td class="label" style="width: 100px;">分析项:</td>
            <td class="ipt">
                <select id="analysis_code"  style="width: 180px" type="text" ></select>
            </td>
        </tr>
        <tr>
        	<td class="label" style="width: 100px;">资源类型:</td>
            <td class="ipt">
                <input id="oresource_code" type="text" style="width: 180px" />
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
         <tr>
            <td class="label" style="width: 100px;">数量:</td>
            <td class="ipt">
                <input id="quantity" style="width: 180px" type="text" />
            </td>
            <td class="label" style="width: 100px;">备注:</td>
            <td class="ipt">
                <input id="remark" style="width: 180px" type="text" />
            </td>
        </tr>
    </table>
    <div id="mainGrid"></div>
</body>

</html>

