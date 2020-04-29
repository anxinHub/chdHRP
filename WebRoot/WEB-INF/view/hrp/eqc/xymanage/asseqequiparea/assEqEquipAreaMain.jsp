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
	var grid, analysis_codeSelect,eo_eq_groupSelect,userSelect;
	$(function () {
		loadDict();
	    initGrid();
	    
	})
    function query() {
        params = [
            { name: 'analysis_code', value: analysis_codeSelect.getValue() },
            { name: 'ass_card_no', value: userSelect.getValue() },
            { name: 'area_percent', value: $("#area_percent").val() },
            { name: 'remark', value: $("#remark").val() }
        ];
        grid.loadData(params);
    };
        
	function initGrid() {
            var columns = [
                 {display: '分析项', name: 'analysis_name',width: '15%',editable:setEdit ,
                	 editor: {
	           		     type: 'select',  //编辑框为下拉框时
	           		  	 keyField: 'analysis_code',
	           		     url:"../queryAssAnalysisObject.do?isCheck=false"
	           		 }	 
            	 },
            	 {display: '房屋', name: 'ass_name',width: '15%', editable:setEdit ,
            		 editor: {
	           		     type: 'select',  //编辑框为下拉框时
	           		  	 keyField: 'ass_card_no',
	           		     url:"../queryAssCardDict.do?isCheck=false&ass_naturs=01",
	           		  	 change: function(ui){
	           		   		ajaxPostData({
	           		   			url: 'queryAssUseArea.do?isCheck=false&ass_card_no='+ui.ass_card_no,
	           		   			success: function (result) {
	  	         		   			ui.use_area = result.use_area;
	           		   			}
	           		   		});
	  	         		  },
	           		 }	 
            	 },
            	 {display: '分析项面积', name: 'analysis_area', width: '10%',
            		 editor: {type: 'float',
            			 change: function(ui){
            				 if(ui.use_area){
            					 ui.area_percent =ui.analysis_area/ui.use_area
            				 }else{
            					 ui.area_percent = 0;
            				 }
            				 grid.updateRow(ui._rowIndx, {area_percent:ui.area_percent});
            			 }
            		 }},
	         	 {display: '占比', name: 'area_percent', width: '10%',editable:false},
	         	 {display: '开始日期', name: 'from_date', width: '10%',editor: {type: 'date',}},
	         	 {display: '结束日期', name: 'to_date', width: '10%',editor: {type: 'date'}},
	         	 {display: '备注', name: 'remark', align: 'left', width: '27%'}
            ];
            var paramObj = {
            	height: '97%',
            	width:'100%',
            	checkbox: true,editable:true,
                dataModel: {
                    url: 'queryAssEqEquipArea.do'
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
		                        this.ass_card_no + "@" +
		                        this.analysis_area + "@" +
		                        this.area_percent  + "@" +
		                        (this.from_date ? this.from_date : "") + "@" +
		                        (this.to_date ? this.to_date : "") + "@" +
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
		                        this.ass_card_no + "@" +
		                        this.analysis_area + "@" +
		                        this.area_percent  + "@" +
		                        (this.from_date ? this.from_date : "") + "@" +
		                        (this.to_date ? this.to_date : "") + "@" +
		                        (this.remark ? this.remark : "") + "@" +
		                        this._rowIndx + "@" +
		                        '2' //修改数据标识
		                    )
		                });
		            }
		            ajaxPostData({
		                url: "saveAssEqEquipArea.do?isCheck=false",
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
	        //判断grid 中的数据是否重复或者为空
	        var targetMap = new HashMap();
	        $.each(data, function(i, v) {
	            rowm = "";
	            if (!v.analysis_code) {
	                rowm += "[分析项]、";
	            }
	            if (!v.ass_card_no) {
	                rowm += "[房屋]、";
	            }
	            if (!v.area_percent) {
	                rowm += "[占比]、";
	            }else{
	            	percent = parseFloat(v.area_percent);
	            	if(percent < 0 || percent>100 ){
	            		rowm2 += "[占比]、";
	            	}
	            }

	            if (rowm != "") {
	                rowm = "第" + (Number(v._rowIndx) + 1) + "行" + rowm.substring(0, rowm.length - 1) + "不能为空;" + "\n\r";
	            }
	            if (rowm2 != "") {
	                rowm2 = "第" + (Number(v._rowIndx) + 1) + "行" + rowm2.substring(0, rowm2.length - 1) + "只能在0-100之间" + "\n\r";
	            }
	            msg += rowm + rowm2;
	            var key = v.analysis_code  + v.ass_card_no
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
		            		 rowdata.ass_card_no 
		                 )
		         });
		         $.etDialog.confirm('确定删除?', function () {
		             ajaxPostData({
		                 url: 'deleteAssEqEquipArea.do',
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
            	options: [{ "id": "01", "text": "机组",label:"机组"},{ "id": "02", "text": "设备"}],
				defaultValue: "none",
				onChange: query
			});
             eo_eq_groupSelect = $("#eo_eq_group").etSelect({
				url: "../queryAssDict.do?isCheck=false",
				defaultValue: "none",
				onChange: query
			});
            userSelect = $("#ass_card_no").etSelect({
				url: "../queryAssCardDict.do?isCheck=false&ass_naturs=01",
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
                <select id="analysis_code"  style="width: 180px" type="text" ></select>
            </td>
            <td class="label" style="width: 100px;">设备:</td>
            <td class="ipt">
                <select id="eo_eq_group" style="width: 180px" type="text" ></select>
            </td>
            <td class="label" style="width: 100px;">房屋:</td>
            <td class="ipt">
                <select id="ass_card_no" type="text" style="width: 180px" ></select>
            </td>
         </tr>
         <tr>
         	<td class="label" style="width: 100px;">占比:</td>
            <td class="ipt">
                <input id="area_percent" style="width: 180px" type="text"/>
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

