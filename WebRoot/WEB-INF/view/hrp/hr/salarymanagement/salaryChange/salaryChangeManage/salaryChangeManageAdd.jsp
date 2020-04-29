<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="hr,dialog,select,grid,datepicker,validate" name="plugins" />
	</jsp:include>
	<script type="text/javascript" src="<%=path%>/lib/laydate/laydate.js"></script>
	<script src="<%=path%>/lib/map.js"></script>
    <script>

	var plan_code,tab_code,grid,grids,make_date,effective_date,change_type,emp_id;
    var type_code;
	var formValidate;
	
        var initValidate = function () {

        	make_date = $("#make_date").etDatepicker({
        		defaultDate:["yyyy-mm-dd"]
    		});
    		
    		effective_date = $("#effective_date").etDatepicker({
    		});
    		emp_id= $("#emp_id").etSelect({
    			url: 'querySalaryManageEmpOption.do?isCheck=false',
    			defaultValue: 'none',
    			checkboxMode: true,
    		});
    		
    		change_type = $("#change_type").etSelect({
    			url: 'querySalaryChangeTypeOption.do?isCheck=false',
    			defaultValue: 'none',
    			onChange:function(id){
    				type_code = id;
    		    	grids.loadData([],'querySalaryChangeTypeSalaryProject.do?isCheck=false&type_code='+id);
    			}
    		});
    		
        	formValidate = $.etValidate({
                items: [
                    { el: $("#change_type"), required: true },
                    { el: $("#make_date"), required: true },
                    { el: $("#emp_id"), required: true },
                    { el: $("#effective_date"), required: true },
                    { el: $("#remark"), required: true },
                ]
            });
        	
         	//查询工资项目
         	ajaxPostData({
                 url: 'queryItemOption.do?isCheck=false',
                 async: false,
                  success: function (responseData) {
                	  plan_code = responseData;
                  },
            })
        	
        	$("#save").click(function(){
        		
         		if (!formValidate.test()) {
                    return;
              	}
        		
        		var forms = $("#manageform").serialize();
              	
              	forms += "&emp_id="+emp_id.getValue()
        		var arrchange = grid.getAllData();
        		var arrsalary = grids.getAllData();
        		//
        		var keyMap = new HashMap();
				var gridarr = [];
				var recode = false;
	         	if (!grid.checkRepeat(arrchange, ['tab_codes'])){
	                return;
	            }
				if(arrchange != null && arrchange.length > 0){
					$.each(arrchange,function(){
							if(this.tab_code || this.value_aft){
								if(this.tab_code && this.value_aft){
									gridarr.push({"tab_code":this.tab_code.toString().split("@")[0]
												,"col_code":this.tab_code.toString().split("@")[1]
												,"value_aft":this.value_aft});
								}else{
									$.etDialog.error("变动项目下有不完整的信息!");
									recode = true;
									return false;
								}
							}
					})
				}
				if(recode){
					return false;
				}
				var gridarrs =[];
				if(arrsalary != null && arrsalary.length > 0){
					$.each(arrsalary,function(){
						if(this.plan_code || this.item_code || this.value_aft){
							if(this.plan_code && this.item_code && this.value_aft){
								if(keyMap.get(this.plan_code + this.item_code)){
									$.etDialog.error("工资项目下有重复信息!");
									recode = true;
									return false;
								}else{
									keyMap.put(this.plan_code + this.item_code,true);
									gridarrs.push(this);
								}
							}else{
								$.etDialog.error("工资项目下有不完整的信息!");
								recode = true;
								return false;
							}
						}
					})
				}
				if(recode){
					return false;
				}
				forms += "&arrchange="+JSON.stringify(gridarr);
				forms += "&arrsalary="+JSON.stringify(gridarrs);
             	ajaxPostData({
                    url: 'addSalaryManage.do',
                    data:forms,
                    success: function (responseData) {
                    	  var curIndex = parent.$.etDialog.getFrameIndex(window.name);
                            
                            var parentFrameName = parent.$.etDialog.parentFrameName;
                            var parentWindow = parent.window[parentFrameName];
                            parentWindow.query(); 
                            parent.$.etDialog.close(curIndex);
                    	//parentWindowQuery();
                    },
                })
        	})
        	
            $("#close").click(function () {
                          var curIndex = parent.$.etDialog.getFrameIndex(window.name);
                          parent.$.etDialog.close(curIndex);
            })
        };
  
        var initGrid = function () {
            var column = [
                { display: '变动项目', name: 'tab_codes', width: 150,
                    editor: {
                        type: 'select',
                        keyField: 'tab_code',
                        url:"",
                        change:function(row){
                        	//grid.getColumns()[2].editor.url ='queryValueaftOption.do?isCheck=false&tab_code='+row.tab_code;
                        	row.value_afts = "";
                        	row.value_aft = "";
                        	grid.updateRow(row._rowIndx,row);
                        	//console.log(grid.getAllData())
                        },
                    
                    }
                },
                { display: '变动后', name: 'value_afts', width: 150,
                    editor: {
                        type: 'select',
                        keyField:'value_aft',
                        url:'',
                    }
                },
            ];
            var paramObj = {
                height: '90%',
                width:"49%",
                inWindowHeight: true,
                checkbox: true,
                editable: true,
                cellSelect: cellSelect,
                showBottom: false,
                pageModel: {type: 'remote',rPP:200,rPPOptions: [50,100,200,500]},
                columns: column,
                toolbar: {
                    items: [
                       { type: 'button', label: '添加', listeners: [{ click: add }], icon: 'add' },
                       { type: 'button', label: '删除', listeners: [{ click: remove }], icon: 'delete' },
                    ]
                }
            };
            grid = $("#mainGrid").etGrid(paramObj);
        };
        
        var initGrids = function () {
            var columns = [
                { display: '薪资方案', name: 'plan_codes', width: 150,
                    editor: {
                        type: 'select',
                        keyField: 'plan_code',
                        source:plan_code,
                        change:function(row){
                        	//grids.getColumns()[2].editor.url ='queryPlancodeOption.do?isCheck=false&plan_code='+row.plan_code;
                        	row.item_codes = "";
                        	row.item_code = "";
                        	grids.updateRow(row._rowIndx,row)
                        },
                    }
                },
                { display: '工资项目', name: 'item_codes', width: 150,
                    editor: {
                        type: 'select',
                        keyField:'item_code',
                        url:'',
                    }
                },
                { display: '变动后', name: 'value_aft', width: 150,
                    editor: {
                        type: 'number'
                    }
                }
            ];
            var paramObjs = {
                height: '90%',
                width:"49%",
                inWindowHeight: true,
                checkbox: true,
                editable: true,
                cellSelect: cellSelects,
                showBottom: false,
                pageModel: {type: 'remote',rPP:200,rPPOptions: [50,100,200,500]},
                columns: columns,
                toolbar: {
                    items: [
                       { type: 'button', label: '添加', listeners: [{ click: adds }], icon: 'add' },
                       { type: 'button', label: '删除', listeners: [{ click: removes }], icon: 'delete' },
                    ]
                }
            };
            grids = $("#mainGrid2").etGrid(paramObjs);
        };

	    var cellSelects = function(event,row){
	    	grids.getColumns()[2].editor.url ='queryPlancodeOption.do?isCheck=false&plan_code='+row.rowData.plan_code;
	    }
        
	    var cellSelect = function(event,row){
	    	if(row.dataIndx == "tab_codes"){
	    		grid.getColumns()[1].editor.url ='querySalaryChangeTypeChangeProjectOption.do?isCheck=false&type_code='+type_code;
	    	}else{
	    		grid.getColumns()[2].editor.url ='queryValueaftOption.do?isCheck=false&tab_code='+row.rowData.tab_code;
	    	}
	    }

	    var add = function () {
	        grid.addRow();
	    };
	    
	    var adds = function () {
	        grids.addRow();
	    };

	    var remove = function (){
			var selectData = grid.selectGet();
            if (selectData.length === 0) {
                $.etDialog.error('请选择行');
                return;
            }
            $.etDialog.confirm('确定删除?', function (index) {
            	grid.deleteRows(selectData);
            	$.etDialog.close(index)
    		});
		}
	    
	    var removes = function (){
			var selectDatas = grids.selectGet();
            if (selectDatas.length === 0) {
                $.etDialog.error('请选择行');
                return;
            }
            $.etDialog.confirm('确定删除?', function (index) {
            	grids.deleteRows(selectDatas);
            	$.etDialog.close(index)
    		});
		}
	    
	    var parentWindowQuery = function (){
	    	var parentFrameName = parent.$.etDialog.parentFrameName;
            var parentWindow = parent.window[parentFrameName];
            parentWindow.query();
		}
	    
	    $(function () {
	        initValidate();
	        initGrid();
	        initGrids();
	    })

    </script>
</head>

<body>
	<div  id="btn"  class="button-group" style="overflow-y: auto">
		<form id="manageform">
		 <table class="table-layout" style="width: 100%;">
	        <tr>
	            <td class="label">变动编号：</td>
	            <td class="ipt">
	                <input value="自动生成" disabled type="text" style="width:150px;" />
	            </td>
	            <td class="label">变动类型<font size="2" color="red">*</font>：</td>
	            <td class="ipt">
	                <input id="change_type" name="change_type" style="width:150px;" />
	            </td>
	            <td class="label">制单日期<font size="2" color="red">*</font>：</td>
	            <td class="ipt">
	                <input  id="make_date" name="make_date" style="width:150px;" />
	            </td>
	        </tr>
	        <tr>
	            <td class="label">职工名称<font size="2" color="red">*</font>：</td>
	            <td class="ipt" colspan="5">
	                <select id="emp_id" style="width: 872px;"></select>
	            </td>
	        </tr>
	        <tr>
	            <td class="label">变动说明<font size="2" color="red">*</font>：</td>
	            <td class="ipt" colspan="5">
	                <input  id="remark" type="text" name="remark" style="width:872px;" />
	            </td>
	        </tr>
	        <tr>
		        <td class="label">生效日期<font size="2" color="red">*</font>：</td>
				<td class="ipt">
	                	<input type="text" id="effective_date" name="effective_date" style="width:180px;"/>
	            </td>
            </tr>
    	</table>
    	</form>
    	<div id="mainGrid" style="float: left;"></div>
    	<div id="mainGrid2" style="float: right;"></div>
        <div id="btn2" class="button-group btn" style="float: inherit;">
	        <button id="save">保存</button>
	        <button id="close">关闭</button>
	    </div>
	</div>   
</body>
</html>