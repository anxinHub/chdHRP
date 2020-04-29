<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
	var grid,detailGrid , es_fromtypeSelect,es_eq_groupSelect,charge_kind_idSelect;
	var detailGridData ; // 加载机组明细表格数据用 
	$(function () {
		 $("#layout1").ligerLayout({ 
			 rightWidth: 800,
			 isRightCollapse: false ,
			 allowRightCollapse:false,
			 heightDiff:-20,
			 onRightToggle : function() {grid._onResize();},
			 //每调整宽度大小即刷新一次表格，以免结构混乱
			 onEndResize : function(a, b) {grid._onResize();}
		});
	    initGrid();
	    loadDetail();
	    
	    $("#eq_unit_code").ligerTextBox({width:180});
        //$("#eo_eq_group ").ligerTextBox({width:180});
	    
	})
    function query() {
		grid.options.parms=[];
		grid.options.newPage=1;
		//根据表字段进行添加查询条件
      	grid.options.parms.push({name:'eq_unit_code',value:$("#eq_unit_code").val()});
		grid.loadData(grid.where);
    };
    
    /* function queryDetail() {
    	 var data = grid.getCheckedRows();
	     if (data.length != 1) {
	         $.ligerDialog.error('请选择一行机组数据');
	     } else {
	    	var eq_unit_code ;
	    	detailGrid.options.parms=[];
	     	detailGrid.options.newPage=1;
	 		//根据表字段进行添加查询条件
	       	detailGrid.options.parms.push({name:'eo_eq_group ',value:$("#eo_eq_group ").val()});
	        $(data).each(function() {
	        	eq_unit_code = this.eq_unit_code ;
	        })
	        detailGrid.options.parms.push({name:'eq_unit_code',value:eq_unit_code});
	       	detailGrid.loadData(grid.where);
	     }
    	
    }; */
        
	function initGrid() {
            var columns = [
                 {display: '机组编码', name: 'eq_unit_code',width: '30%', editor: {type: 'string'},
                	 render:function(rowdata,rowindex,value){
						if (rowdata.group_id) {//不是新建  不允许 编辑 供应商/定标日期
							rowdata.notEidtColNames.push("eq_unit_code");
						}
						return rowdata.eq_unit_code;
                	 }
				 },
            	 {display: '机组名称', name: 'eq_unit_name',width: '35%', editor: {type: 'string'}},
            	 {display: '开始日期', name: 'from_date', width: '15%',type: 'date',format: 'yyyy-MM-dd',editor: {type: 'date',}},
	         	 {display: '结束日期', name: 'to_date', width: '15%',type: 'date',format: 'yyyy-MM-dd',editor: {type: 'date'}},
	         	 
            ];
            var paramObj = {
                columns: columns,
                height: '95%', 	width:'100%',
            	selectRowButtonOnly:true, isAddRow:false,
            	checkbox: true,enabledEdit:true,
            	onSelectRow: loadDetailGrid,//选择行时，加载 机组明细表数据
                url: 'queryAssEqGroup.do',
                toolbar: {
                    items: [
						{text : '查询',	id : 'search',click : query,icon : 'search'}, 
						{line : true},
						{text : '增加行',	id : 'add',click : add,icon : 'add'}, 
						{line : true},
						{text : '保存',	id : 'save',click : save,icon : 'save'}, 
						{line : true},
						{text : '删除',	id : 'delete',click : remove,icon : 'delete'}
                        
                    ]
                }
            };
            grid = $("#mainGrid").ligerGrid(paramObj);
        };
        function loadDetail() {
            var columns = [
                 {display: '设备名称', name: 'eo_eq_group',textField : 'eo_eq_group_name',width: '25%',
                	 editor: {
	           		     type: 'select',  //编辑框为下拉框时
	           		     url:"../queryAssCardDict.do?isCheck=false"
	           		 },
	           		 render:function(rowdata,rowindex,value){
						if (rowdata.group_id) {//不是新建  不允许 编辑 供应商/定标日期
							rowdata.notEidtColNames.push("eo_eq_group ");
						}
						return rowdata.eo_eq_group_name;
                	 }
                 },
            	 {display: '收入分摊比例', name: 'income_rate', width: '15%',editor: {type: 'float',}},
	         	 {display: '支出分摊比例', name: 'expend_rate', width: '15%',editor: {type: 'float'}},
            	 {display: '开始日期', name: 'from_date', width: '15%',type: 'date',format: 'yyyy-MM-dd',editor: {type: 'date',}},
	         	 {display: '结束日期', name: 'to_date', width: '15%',type: 'date',format: 'yyyy-MM-dd',editor: {type: 'date'}},
	         	{display: '主设备标识', name: 'main_flag',width: '10%', 
             		render: function(rowdata, rowindex,value) {
							if(rowdata.main_flag == 1){
								return "<input id=main_flag"+rowindex+"  type ='checkbox' checked='checked' onchange=\"checkEnable("+rowindex+")\" style='margin-top:8px;'>";
							}else{
								return "<input id=main_flag"+rowindex+"  type ='checkbox' onchange=\"checkEnable("+rowindex+")\" style='margin-top:8px;'>";
							}
             		}
				 }
            ];
            var paramObj = {
            		columns: columns,
	            	height: '95%',	width:'100%',
	            	selectRowButtonOnly:true, isAddRow:false,
	            	checkbox: true,enabledEdit:true,
	            	data : detailGridData,
	                toolbar: {
	                    items: [
							/* {text : '查询',	id : 'search',click : queryDetail,icon : 'search'}, 
							{line : true}, */
							{text : '增加行',	id : 'add',click : addDetail,icon : 'add'}, 
							{line : true},
							{text : '保存',	id : 'save',click : saveDetail,icon : 'save'}, 
							{line : true},
							{text : '删除',	id : 'delete',click : removeDetail,icon : 'delete'}
	                    ]
	                }
            };
            detailGrid = $("#detailGrid").ligerGrid(paramObj);
        };
        
      //主设备标识赋值
    	function checkEnable(rowIndx){
    		var data = detailGrid.getRow(rowIndx);
    		if($("#main_flag"+rowIndx+"").prop("checked")){
    			data.main_flag=1;
    	 	}else{
    	 		data.main_flag=0;
    	 	} 
    	}
        //新增
		function add(){
			grid.addRow();
		}
		//保存
		function save () {
			
			var addData = grid.getAdded();
			var updateData = grid.getUpdated();
	        var ParamVo = [];
	        if(addData.length > 0 || updateData.length > 0){
	        	if (addData.length > 0) {
	                if (!validateGrid(addData)) {
	                    return false;
	                }
	                
	                $(addData).each(function() {
	                    ParamVo.push(
	                        this.eq_unit_code + "@" +
	                        this.eq_unit_name + "@" +
	                        this.from_date + "@" +
	                        this.to_date  + "@" +
	                        (this.detailData ? JSON.stringify(this.detailData) : "") + "@" +
	                        this.eq_unit_code + "@" +
	                        "1" + "@" //添加 数据标识(有明细数据 末尾 @符号 不能去掉，否则后台无法拆分数据)
	                    )
	                });
	            }
	        	if (updateData.length > 0) {

	                $(updateData).each(function() {
	                    ParamVo.push(
                    		this.eq_unit_code + "@" +
 	                        this.eq_unit_name + "@" +
 	                        this.from_date + "@" +
 	                        this.to_date  + "@" +
 	                        (this.detailData ? JSON.stringify(this.detailData) : "") + "@" +
 	                        this.eq_unit_code + "@" +
	                        '2'+ "@"  //修改数据标识（有明细数据 末尾 @符号 不能去掉，否则后台无法拆分数据）
	                    )
	                });
	            }
	        	ajaxJsonObjectByUrl("saveAssEqGroup.do", {ParamVo : ParamVo.toString()}, function (responseData) {
					if (responseData.state == "true") {
                        $.ligerDialog.success('保存成功');
                        query();
                        detailGrid.deleteAllRows();
                    } 
	            });
	        
	        } else {
	            $.ligerDialog.warn('没有需要保存的数据!');
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
	            if (!v.eq_unit_code) {
	                rowm += "[机组编码]、";
	            }
	            if (!v.eq_unit_name) {
	                rowm += "[机组名称]、";
	            }
	            if (!v.from_date) {
	                rowm += "[开始日期]、";
	            }
	            if (!v.to_date) {
	                rowm += "[结束日期]、";
	            }

	            if (rowm != "") {
	                rowm = "机组"+v.eq_unit_code + rowm.substring(0, rowm.length - 1) + "不能为空" + "\n\r";
	            }
	            msg += rowm;
	            var key = v.eq_unit_code
	            var value = "机组"+v.eq_unit_code;
	            if (targetMap.get(key) == null || targetMap.get(key) == 'undefined' || targetMap.get(key) == "") {
	                targetMap.put(key, value);
	            } else {
	                msg += targetMap.get(key) + "与" + value + "数据重复!!" + "\n\r";
	            }
	        });
	        if (msg != "") {
	            $.ligerDialog.warn(msg);
	            return false;
	        } else {
	            return true;
	        }
	    }
		
		//删除
		function remove() {
			 var data = grid.getCheckedRows();
		     if (data.length == 0) {
		         $.ligerDialog.error('请选择行');
		     } else {
		    	 var ParamVo = [];
		         $(data).each(function () {
		             ParamVo.push(
		            		 this.eq_unit_code 
		                 )
		         });
		         $.ligerDialog.confirm('确定删除?', function () {
		        	 ajaxJsonObjectByUrl("deleteAssEqGroup.do", {ParamVo : ParamVo.toString()}, function (responseData) {
						if (responseData.state == "true") {
		                    query();
	                        detailGrid.deleteAllRows();
		                }
		             })
		         });
		     }
		};  
		//选中机组数据  加载 机组明细数据 
	    function loadDetailGrid(rowData,rowindex,rowobj){
	    	 if(rowData.detailData){
	    		 detailGridData = rowData.detailData ;
	    	 }else{
	    		 detailGridData  = {"Rows":[],"Total":0} ;
	    	 }
	    	 loadDetail();
	     } 
		 //新增
		function addDetail(){
			var data = grid.getCheckedRows();
	         if (data.length != 1) {
	             $.ligerDialog.error('请选择一行机组数据');
	         }else{
	        	 detailGrid.addRow();
	         }
		}
		//保存
		function saveDetail() {
			
			 var data = grid.getCheckedRows();
	    	 if (data.length != 1) {
	             $.ligerDialog.error('请选择一行机组数据');
	         }else{
	        	 var detailData = detailGrid.getData();
	 		    if(detailData.length > 0 ){
	 		    	
	 		    	if (!validateDetailGrid(detailData)) {
	                    return false;
	                }else{
	                	$(data).each(function(){
	                		 grid.updateRow(this,{detailData:{"Rows":detailData,"Total":detailData.length}});
	                	})
	                	
            			 $.ligerDialog.success('操作成功！');
	                }
	 	        } else {
	 	            $.ligerDialog.warn('请添加机组明细数据!');
	 	        }
	        	 
	         }
			
		};
		// 数据校验
		function validateDetailGrid(data) {
			
	        var msg = "";
	        var rowm = "";
	        //判断grid 中的数据是否重复或者为空
	        var targetMap = new HashMap();
	        $.each(data, function(i, v) {
	            rowm = "";
	           
	            if (!v.eo_eq_group_name) {
	                rowm += "[设备]、";
	            }
	            if (!v.income_rate) {
	                rowm += "[收入分摊比例]、";
	            }
	            if (!v.expend_rate) {
	                rowm += "[支出占比]、";
	            }
	            if (!v.from_date) {
	                rowm += "[开始日期]、";
	            }

	            if (rowm != "") {
	                rowm = "设备" + (v.eo_eq_group_name) + rowm.substring(0, rowm.length - 1) + "不能为空" + "\n\r";
	            }
	            msg += rowm;
	            var key = v.eo_eq_group_name
	            var value = "设备" + (v.eo_eq_group_name);
	            if (targetMap.get(key) == null || targetMap.get(key) == 'undefined' || targetMap.get(key) == "") {
	                targetMap.put(key, value);
	            } else {
	                msg += targetMap.get(key) + "与" + value + "数据重复!!" + "\n\r";
	            }
	        });
	        if (msg != "") {
	            $.ligerDialog.warn(msg);
	            return false;
	        } else {
	            return true;
	        }
	    }
		
		//删除
		function removeDetail() {
			 var data = detailGrid.getCheckedRows();
		     if (data.length == 0) {
		         $.ligerDialog.error('请选择行！');
		     } else {
		    	 detailGrid.deleteSelectedRow2();
		    	 $.ligerDialog.success('操作成功！');
		     }
		};        
    </script>
</head>

<body>
<div id="layout1" >
        <div position="center" title='机组维护' >
        	<table cellpadding="0" cellspacing="0" class="l-table-edit" >
		        <tr>
		            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">机组:</td>
		            <td align="left" class="l-table-edit-td"><input name="eq_unit_code" type="text" id="eq_unit_code" ltype="text" /></td>
		            <td align="left"></td>
		        </tr>
		   </table>
		    <div id="mainGrid"></div>
        </div>
        <div position="right" title='机组明细维护'>
        	<!-- <table cellpadding="0" cellspacing="0" class="l-table-edit" >
		        <tr>
		            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">设备:</td>
		            <td align="left" class="l-table-edit-td"><input name="eo_eq_group " type="text" id="eo_eq_group " ltype="text" /></td>
		            <td align="left"></td>
		        </tr>
		   </table> -->
		    <div id="detailGrid"></div>
        </div>
</div>
</body>

</html>

