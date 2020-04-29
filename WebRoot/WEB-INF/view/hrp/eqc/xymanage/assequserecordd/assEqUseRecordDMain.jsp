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
	var grid, analysis_codeSelect,ur_eq_groupSelect,charge_kindSelect,charge_itemSelect,unit_codeSelect,dept_codeSelect,use_dateSelect,end_dateSelect;
	$(function () {
		loadDict();
	    initGrid();
	    
	})
    function query() {
        params = [
                  { name: 'analysis_code', value: analysis_codeSelect.getValue() },
                  { name: 'charge_item_id', value: charge_kindSelect.getValue() },
                  { name: 'price', value: $("#price").val() },
                  { name: 'work_load_num', value: $("#work_load_num").val() },
                  { name: 'unit_code', value: unit_codeSelect.getValue()},
                  { name: 'use_date', value: $("#use_date").val() },
                  { name: 'dept_code', value: dept_codeSelect.getValue() },
                  { name: 'remark', value: $("#remark").val() },
                  { name: 'patient_id', value: $("#patient_id").val()},
                  { name: 'use_date', value: use_dateSelect.getValue()},
                  { name: 'end_date', value: end_dateSelect.getValue()}
                  
                  
        ];
        grid.loadData(params);
    };
        
	function initGrid() {
            var columns = [
                 {display: '分析项', name: 'analysis_name',width: '15%',editable:setEdit ,               	
                	 editor: {
     					type: 'select',
     					keyField: 'analysis_code',
     					url:"../queryAssAnalysisObject.do?isCheck=false"    					
     				},
            	 },           	
	         	 {display: '开始日期', name: 'use_date', width: '8%',editor: {type: 'date',},editable:setEdit },
	         	 {display: '开始时间', name: 'start_time', width: '8%',editor: {type: 'date',},editable:setEdit },
	         	 {display: '结束日期', name: 'end_date', width: '8%',editor: {type: 'date'},editable:setEdit },
	         	 {display: '结束时间', name: 'end_time', width: '8%',editor: {type: 'date'},editable:setEdit },
	         	 {display: '工作量', name: 'work_load_num', width: '8%',editor: {type: 'float'},editable:setEdit },
	         	 {display: '工作量单位', name: 'unit_name',align: 'center', width: '7%',editable:setEdit ,
            		 editor: {
            			 type: 'select',  //编辑框为下拉框时
	           		  	 keyField: 'unit_code',
	           		     url:"../queryHosUnit.do?isCheck=false"
            			 
            		 }
            	 },
            	 {display: '使用科室', name: 'dept_name',width: '10%', editable:setEdit ,
					 editor: {
						     type: 'select',  //编辑框为下拉框时
						  	 keyField: 'dept_code',
						     url:"../queryDeptDict.do?isCheck=false"
						 }	 
				 },
				 {display: '患者编码', name: 'patient_id', align: 'left', width: '8%',editable:setEdit },
				 {display: '患者名称', name: 'patient_name', align: 'left', width: '8%',editable:setEdit },
				 {display: '单价', name: 'price', align: 'left', width: '10%'},
				 {display: '收取费用', name: 'total_fee', align: 'left', width: '10%'},
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
				 {display: '服务细项', name: 'charge_item_name',width: '10%', editable:setEdit ,
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
            	 {display: '系统来源', name: 'busi_data_source_name', width: '10%',editable:setEdit ,
            		 editor: {
               		     type: 'select',  //编辑框为下拉框时
               		  	 keyField: 'busi_data_source_code',
               		     url:"../queryBusiDataSource.do?isCheck=false",   //  静态数据接口  也可以是回调函数返回值
               		 }	 
            	 },
            	 {display: '来源ID', name: 'ex_id', align: 'left', width: '10%'},
            	 {display: '单独收费项数量', name: 'alone_pay_num', width: '10%',editor: {type: 'float'},editable:setEdit },
            	 {display: '曝光次数', name: 'exposure_num', width: '10%',editor: {type: 'float'},editable:setEdit },
	         	 {display: '手工录入标识', name: 'is_input_flag', align: 'center', width: '7%',editable:false,
	         		 editor: {
            			 type: 'select',  //编辑框为下拉框时
	           		 	 source:[{ "id": "N", "text": "N",label:"N"},{ "id": "Y", "text": "Y",label:"Y"}]
            			 
            		 }
	         	 },
	         	 {display: '状态', name: 'status_name',align: 'center', width: '7%',editable:false,
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
	         	 {display: '备注', name: 'remark', align: 'left', width: '10%'},
		         {display: '新增人', name: 'add_user_name', align: 'left', width: '8%',editable:false},
		         {display: '作废日期', name: 'cancel_date', align: 'left', width: '8%',editable:false},
		         {display: '作废人', name: 'cancel_user_name', align: 'left', width: '8%',editable:false},
            ];
            var paramObj = {
            	height: '97%',
            	width:'100%',
            	checkbox: true,editable:true,
                dataModel: {
                    url: 'queryAssEqUseRecordD.do'
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
       		 if(ui.rowData && ui.rowData.status!='0'){
       			 return false ;
       		 }else{
       			 return true ;
       		 }
        }
        
        //新增
		function add(){
			grid.addRow({status:"0",status_name:"新增",is_input_flag:"Y"});
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
		                        this.ex_id + "@" +
		                        this.use_date + "@" +
		                        this.start_time  + "@" +
		                        (this.end_date ? this.end_date : "") + "@" +
		                        (this.end_time ? this.end_time : "") + "@" +
		                        this.work_load_num + "@" +
		                        this.unit_code + "@" +
		                        this.dept_code + "@" +
		                        this.patient_id + "@" +
		                        this.patient_name + "@" +
		                        this.price + "@" +
		                        this.total_fee + "@" +
		                        this.year + "@" +
		                        this.month + "@" +
		                        this.charge_item_id + "@" +		                        
		                        this.busi_data_source_code + "@" +
		                        (this.alone_pay_num ? this.alone_pay_num : "") + "@" +
		                        this.is_input_flag + "@" +
		                        this.status + "@" +
		                        (this.exposure_num ? this.exposure_num : "") + "@" +
		                        (this.remark ? this.remark : "") + "@" +
		                        this._rowIndx + "@" +
		                      	//新增、修改数据标识
		                        (this.ur_rowid ? this.ur_rowid:'-1')+ "@"
		                    )
		                });
		            }
		            if (data.updateList.length > 0) {

		                var updateData = data.updateList;
		                $(updateData).each(function() {
		                    ParamVo.push(
		                    	this.analysis_code + "@" +
		                        this.ex_id + "@" +
		                        this.use_date + "@" +
		                        this.start_time  + "@" +
		                        (this.end_date ? this.end_date : "") + "@" +
		                        (this.end_time ? this.end_time : "") + "@" +
		                        this.work_load_num + "@" +
		                        this.unit_code + "@" +
		                        this.dept_code + "@" +
		                        this.patient_id + "@" +
		                        this.patient_name + "@" +
		                        this.price + "@" +
		                        this.total_fee + "@" +
		                        this.year + "@" +
		                        this.month + "@" +
		                        this.charge_item_id + "@" +		                       
		                        this.busi_data_source_code + "@" +
		                        (this.alone_pay_num ? this.alone_pay_num : "") + "@" +
		                        this.is_input_flag + "@" +
		                        this.status + "@" +
		                        (this.exposure_num ? this.exposure_num : "") + "@" +
		                        (this.remark ? this.remark : "") + "@" +
		                        this._rowIndx + "@" +
		                      	//新增、修改数据标识
		                        (this.ur_rowid ? this.ur_rowid:'-1') 	+ "@"
		                        
		                    )
		                });
		            }
		            ajaxPostData({
		                url: "saveAssEqUseRecordD.do?isCheck=false",
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
	            if (!v.use_date) {
	                rowm += "[开始日期]、";
	            }
	            if (!v.start_time) {
	                rowm += "[开始时间]、";
	            }
	            if (!v.work_load_num) {
	                rowm += "[工作量]、";
	            }
	            if (!v.unit_code) {
	                rowm += "[工作量单位]、";
	            }
	            if (!v.dept_code) {
	                rowm += "[使用科室]、";
	            }
	            if (!v.price) {
	                rowm += "[单价]、";
	            }
	            if (!v.total_fee) {
	                rowm += "[收取费用]、";
	            }
	            if (!v.year) {
	                rowm += "[年]、";
	            }
	            if (!v.month) {
	                rowm += "[月]、";
	            }
	            if (!v.charge_item_id) {
	                rowm += "[服务细项]、";
	            }
	            if (!v.ex_id) {
	                rowm += "[来源ID]、";
	            }
	            if (!v.busi_data_source_code) {
	                rowm += "[系统来源]、";
	            }
	            
	            
	            if (rowm != "") {
	                rowm = "第" + (Number(v._rowIndx) + 1) + "行" + rowm.substring(0, rowm.length - 1) + "不能为空;" + "\n\r";
	            }
	            msg += rowm ;
	            /* var key = v.analysis_code + v.ur_eq_group + v.user_id
	            var value = "第" + (Number(v._rowIndx) + 1) + "行";
	            if (targetMap.get(key) == null || targetMap.get(key) == 'undefined' || targetMap.get(key) == "") {
	                targetMap.put(key, value);
	            } else {
	                msg += targetMap.get(key) + "与" + value + "数据重复!!" + "\n\r";
	            } */
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
		             console.log(rowdata);
		             ParamVo.push(
		            		 rowdata.ur_rowid 
		                 )
		         });

		         console.log(ParamVo);
		         $.etDialog.confirm('确定删除?', function () {
		             ajaxPostData({
		                 url: 'deleteAssEqUseRecordD.do',
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
            charge_kindSelect = $("#charge_item_id").etSelect({
				url: "../queryCostChargeItem.do?isCheck=false",
				defaultValue: "none",
				onChange: query
			});           
            unit_codeSelect = $("#unit_code").etSelect({
 				url: "../queryHosUnit.do?isCheck=false",
 				defaultValue: "none",
 				onChange: query
 			});
            dept_codeSelect = $("#dept_code").etSelect({
				url: "../queryDeptDict.do?isCheck=false",
				defaultValue: "none",
				onChange: query
			});
            use_dateSelect = $("#use_date").etDatepicker({
                dateFormat: "yyyy-mm-dd",
                onChange: query
                  
            });
            end_dateSelect = $("#end_date").etDatepicker({
                dateFormat: "yyyy-mm-dd",
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
            <td class="label" style="width: 100px;">单价:</td>
            <td class="ipt">
                <input id="price" style="width: 180px" type="text"/>
            </td>
        	      
         </tr>
         <tr>
         	<td class="label" style="width: 100px;">工作量:</td>
            <td class="ipt">
                <input id="work_load_num" style="width: 90px" type="text"/>
                <select id="unit_code" type="text" style="width: 90px" ></select>
            </td>   
             <td class="label" style="width: 100px;">患者:</td>
            <td class="ipt">
                 <input id="patient_id" style="width: 180px" type="text"/>
            </td>
            <td class="label" style="width: 100px;">开始日期:</td>
             <td class="ipt">
                 <input id="use_date" style="width: 180px" type="text"/>
            </td>              
        </tr>
        <tr>
        	 <td class="label" style="width: 100px;">使用科室:</td>
             <td class="ipt">
                 <input id="dept_code" style="width: 180px" type="text"/>
            </td>
         	<td class="label" style="width: 100px;">备注:</td>
            <td class="ipt">
                <input id="remark" style="width: 180px" type="text"/>
            </td>
             <td class="label" style="width: 100px;">结束日期:</td>
            <td class="ipt">
                 <input id="end_date" style="width: 180px" type="text"/>
            </td>
           
        </tr>
    </table>
    <div id="mainGrid"></div>
</body>

</html>

