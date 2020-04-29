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
	var grid, ciCodeSelect ,dept_codeSelect ;
	$(function () {
		loadDict();
	    initGrid();
	    
	})
    function query() {
        params = [
			{ name: 'consum_code', value: ciCodeSelect.getValue() },
			{ name: 'dept_code', value: dept_codeSelect.getValue() }
        ];
        grid.loadData(params);
    };
        
	function initGrid() {
            var columns = [
				 {display: '使用科室', name: 'dept_name',width: '15%', editable:setEdit ,
					 editor: {
						     type: 'select',  //编辑框为下拉框时
						  	 keyField: 'dept_code',
						     url:"../queryDeptDict.do?isCheck=false"
						 }	 
				 },
				 {display: '对应类型', name: 'analysis_name',width: '10%',editable:setEdit ,               	 
                	 editor: {
     					type: 'select',
     					keyField: 'analysis_code',
     					url:"../queryAssAnalysisObject.do?isCheck=false"    					
     				},
            	 },           	
                 {display: '消耗资源', name: 'consum_desc',width: '10%', editable:setEdit ,
            		 editor: {
	           		     type: 'select',  //编辑框为下拉框时
	           		  	 keyField: 'consum_code',
	           		     url:"../queryAssEqcConsumableItem.do?isCheck=false"
	           		 }	 
            	 },
            	 {display: '占比', name: 'rate',align: 'center', width: '10%', editor: { type: 'float' }},
            	 {display: '起始日期', name: 'from_date', width: '10%',editor: {type: 'date',}},
            	 {display: '结束日期', name: 'to_date', width: '10%',editor: {type: 'date',}},
            	 {display: '备注', name: 'remark', align: 'left', width: '17%'}
	         	 
            ];
            var paramObj = {
            	height: '97%',
            	width:'100%',
            	checkbox: true,editable:true,
                dataModel: {
                    url: 'queryAssEqPublicConsumRate.do'
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
	                    		this.dept_code + "@" +	                    		
	                    		this.analysis_code +"@" +
	                    		this.consum_code +"@" +
			                    this.rate + "@" +
			                    this.from_date + "@" +
		                        this.to_date + "@" +
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
	                    		this.dept_code + "@" +
	                    		this.analysis_code +"@" +	                    		
	                    		this.consum_code +"@" +
			                    this.rate + "@" +
			                    this.from_date + "@" +
		                        this.to_date + "@" +
		                        (this.remark ? this.remark : "") + "@" +
		                        this._rowIndx + "@" +
		                        '2'  +'@' //修改数据标识
		                    )
		                });
		            }
		            ajaxPostData({
		                url: "saveAssEqPublicConsumRate.do?isCheck=false",
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
	            if (!v.dept_code) {
	                rowm += "[使用科室]、";
	            }
	            if (!v.analysis_code) {
	                rowm += "[分析项]、";
	            }	          
	            if (!v.consum_code) {
	                rowm += "[消耗资源]、";
	            }
	            if (!v.rate) {
	                rowm += "[占比]、";
	            }
	            if (!v.from_date) {
	                rowm += "[起始日期]、";
	            }
	            if (!v.to_date) {
	                rowm += "[结束日期]、";
	            }
	            
	            if (rowm != "") {
	                rowm = "第" + (Number(v._rowIndx) + 1) + "行" + rowm.substring(0, rowm.length - 1) + "不能为空" + "\n\r";
	            }
	            msg += rowm ;
	            var key = v.dept_code + v.analysis_code  + v.eo_eq_group + v.consum_code  
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
		            		 rowdata.dept_code + "@" +
		            		 rowdata.analysis_code +"@" +		            		
		            		 rowdata.consum_code +"@" 
		                 )
		         });
		         $.etDialog.confirm('确定删除?', function () {
		             ajaxPostData({
		                 url: 'deleteAssEqPublicConsumRate.do',
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

