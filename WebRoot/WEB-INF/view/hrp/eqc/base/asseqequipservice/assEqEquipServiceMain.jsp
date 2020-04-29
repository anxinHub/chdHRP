<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="${path}/resource.jsp">
<jsp:param value="grid,select,dialog" name="plugins" />
</jsp:include>
<script src="<%=path%>/lib/map.js"></script>
<script>
	var grid, analysis_codeSelect,charge_kind_idSelect;
	$(function () {
		loadDict();
	    initGrid();
	    
	})
    function query() {
        params = [
            { name: 'analysis_code', value: analysis_codeSelect.getValue() }, 
            { name: 'charge_kind_id', value: charge_kind_idSelect.getValue() },
            { name: 'minutes_per_times', value: $("#minutes_per_times").val() },
            { name: 'remark', value: $("#remark").val() }
        ];
        grid.loadData(params);
    };
        
	function initGrid() {
            var columns = [
                 {display: '分析项', name: 'analysis_name',width: '10%', editable:setEdit ,
            		 editor: {
	           		     type: 'select',  //编辑框为下拉框时
	           		  	 keyField: 'analysis_code',
                 		 url:"../queryAssAnalysisObject.do?isCheck=false"
	           		 }	 
            	 },
            	 {display: '服务项', name: 'charge_kind_name',width: '10%', editable:setEdit ,
            		 editor: {
	           		     type: 'select',  //编辑框为下拉框时
	           		  	 keyField: 'charge_kind_id',
	           		     url:"../queryCostChargeKind.do?isCheck=false"
	           		 }	 
            	 },
            	 {display: '最小分钟数', name: 'min_minutes', width: '10%',editor: {type: 'float'}},
	         	 {display: '每次分钟数', name: 'minutes_per_times', width: '10%',editor: {type: 'float'}},
	         	 {display: '最大分钟数', name: 'max_minutes', width: '10%',editor: {type: 'float'}},
	         	 {display: '备注', name: 'remark', align: 'left', width: '27%'},  	 
            ];
            var paramObj = {
            	height: '97%',
            	width:'100%',
            	checkbox: true,editable:true,
                dataModel: {
                    url: 'queryAssEqEquipService.do'
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
		                        this.analysis_code + "@" +
		                        this.charge_kind_id + "@" +
		                        this.minutes_per_times  + "@" +
		                        (this.min_minutes ? this.min_minutes : "") + "@" +
		                        (this.max_minutes ? this.max_minutes : "") + "@" +
		                        (this.remark ? this.remark : "") + "@" +
		                        this._rowIndx + "@" +
		                        '1' //添加数据标识
		                    )
		                });
		            }
		            if (data.updateList.length > 0) {

		                var updateData = data.updateList;
		                $(updateData).each(function() {
		                    ParamVo.push(
                    		 	this.analysis_code + "@" +
		                        this.charge_kind_id + "@" +
		                        this.minutes_per_times  + "@" +
		                        (this.min_minutes ? this.min_minutes : "") + "@" +
		                        (this.max_minutes ? this.max_minutes : "") + "@" +
		                        (this.remark ? this.remark : "") + "@" +
		                        this._rowIndx + "@" +
		                        '2' //修改数据标识
		                    )
		                });
		            }
		            ajaxPostData({
		                url: "saveAssEqEquipService.do?isCheck=false",
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
	                rowm += "[对应类型]、";
	            }
	            if (!v.charge_kind_id) {
	                rowm += "[服务项]、";
	            }
	            if (!v.minutes_per_times) {
	                rowm += "[每次分钟数]、";
	            }

	            if (rowm != "") {
	                rowm = "第" + (Number(v._rowIndx) + 1) + "行" + rowm.substring(0, rowm.length - 1) + "不能为空" + "\n\r";
	            }
	            msg += rowm;
	            var key = v.analysis_code  + v.charge_kind_id
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
		            		 rowdata.analysis_code + "@" +
		            		 rowdata.charge_kind_id 
		                 )
		         });
		         $.etDialog.confirm('确定删除?', function () {
		             ajaxPostData({
		                 url: 'deleteAssEqEquipService.do',
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
             charge_kind_idSelect = $("#charge_kind_id").etSelect({
				url: "../queryCostChargeKind.do?isCheck=false",
				defaultValue: "none",
				onChange: query
			});
        }
    </script>
</head>

<body>
    <table class="table-layout">
        <tr>
            <td class="label" style="width: 100px;">对应类型:</td>
            <td class="ipt">
                <input id="analysis_code"  style="width: 180px" type="text" />
            </td>
            <td class="label" style="width: 100px;">服务项:</td>
            <td class="ipt">
                <select id="charge_kind_id" type="text" style="width: 180px" ></select>
            </td>
         	<td class="label" style="width: 100px;">每次分钟数:</td>
            <td class="ipt">
                <input id="minutes_per_times" style="width: 180px" type="text"/>
            </td>
        	<td class="label" style="width: 100px;">备注:</td>
            <td class="ipt">
                <input id="eo_remark" style="width: 180px" type="text"/>
            </td>
        </tr>
    </table>
    <div id="mainGrid"></div>
</body>

</html>

