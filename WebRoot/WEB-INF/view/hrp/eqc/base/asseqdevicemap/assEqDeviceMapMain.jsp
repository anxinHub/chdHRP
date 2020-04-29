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
	var grid, busiDataSourceSelect,assCodeSelect;
	$(function () {
		loadDict();
	    initGrid();
	    
	})
    function query() {
        params = [
            { name: 'busi_data_source_code', value: busiDataSourceSelect.getValue() },
            { name: 'device_code', value: $("#device_code").val() },
            { name: 'ass_card_no', value: assCodeSelect.getValue() },
            { name: 'remark', value: $("#remark").val() }
        ];
        grid.loadData(params);
    };
        
	function initGrid() {
            var columns = [
            	 {display: '设备', name: 'ass_name',width: '15%', editable:setEdit ,
            		 editor: {
	           		     type: 'select',  //编辑框为下拉框时
	           		  	 keyField: 'ass_card_no',
	           		     url:"../queryAssCardDict.do?isCheck=false"
	           		 }	 
            	 },
	         	 {display: '对照系统', name: 'busi_data_source_name', width: '10%',editable:setEdit ,
            		 editor: {
               		     type: 'select',  //编辑框为下拉框时
               		  	 keyField: 'busi_data_source_code',
               		     url:"../queryBusiDataSource.do?isCheck=false",   //  静态数据接口  也可以是回调函数返回值
               		 }	 
            	 },
	         	 {display: '对照设备编码', name: 'device_code', width: '10%'},
	         	 {display: '对照设备名称', name: 'device_desc', width: '15%'},
	         	//{display: '设备描述', name: 'device_desc', width: '20%'},    20200410  郭满   多余的展示条件
	         	 {display: '备注', name: 'remark', align: 'left', width: '27%'}
            ];
            var paramObj = {
            	height: '97%',
            	width:'100%',
            	checkbox: true,editable:true,
                dataModel: {
                    url: 'queryAssEqDeviceMap.do'
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
		                        this.ass_card_no + "@" +
		                        this.busi_data_source_code + "@" +
		                        this.device_code + "@" +
		                        (this.device_desc ? this.device_desc : "") + "@" +
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
		                    	this.ass_card_no + "@" +
		                        this.busi_data_source_code + "@" +
		                        this.device_code + "@" +
		                        (this.device_desc ? this.device_desc : "") + "@" +
		                        (this.remark ? this.remark : "") + "@" +
		                        this._rowIndx + "@" +
		                        '2' //修改数据标识
		                    )
		                });
		            }
		            ajaxPostData({
		                url: "saveAssEqDeviceMap.do?isCheck=false",
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

	            if (!v.ass_card_no) {
	                rowm += "[设备]、";
	            }
	            if (!v.busi_data_source_code) {
	                rowm += "[对照系统]、";
	            }
	            if (!v.device_code) {
	                rowm += "[对照设备编码]、";
	            }

	            if (rowm != "") {
	                rowm = "第" + (Number(v._rowIndx) + 1) + "行" + rowm.substring(0, rowm.length - 1) + "不能为空" + "\n\r";
	            }
	            msg += rowm;
	            var key = v.ass_card_no + v.busi_data_source_code + v.device_code
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
		            		 rowdata.ass_card_no + "@" +
		            		 rowdata.busi_data_source_code + "@" +
		            		 rowdata.device_code 
		                    )
		         });
		         $.etDialog.confirm('确定删除?', function () {
		             ajaxPostData({
		                 url: 'deleteAssEqDeviceMap.do',
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
        	busiDataSourceSelect = $("#busi_data_source_code").etSelect({
				url: "../queryBusiDataSource.do?isCheck=false",
				defaultValue: "none",
				onChange: query
			});
        	assCodeSelect = $("#ass_card_no").etSelect({
        		url: "../queryAssCardDict.do?isCheck=false", 
				//url: "../queryAssDict.do?isCheck=false",   20200410 gm  设备查询条件有问题
				defaultValue: "none",
				onChange: query
			})
        }
    </script>
</head>

<body>
    <table class="table-layout">
        <tr>
            <td class="label" style="width: 100px;">系统来源:</td>
            <td class="ipt">
                <input id="busi_data_source_code"  style="width: 180px" type="text" />
            </td>
            <td class="label" style="width: 100px;">对照设备:</td>
            <td class="ipt">
                <input id="device_code" type="text" style="width: 180px" />
            </td>
            <td class="label" style="width: 100px;">设备:</td>
            <td class="ipt">
                <select id="ass_card_no" style="width: 180px" type="text" ></select>
            </td>
        	<td class="label" style="width: 100px;">备注:</td>
            <td class="ipt">
                <input id="remark" style="width: 180px" type="text"/>
            </td>
        </tr>
    </table>
    <div id="mainGrid"></div>
</body>

</html>

