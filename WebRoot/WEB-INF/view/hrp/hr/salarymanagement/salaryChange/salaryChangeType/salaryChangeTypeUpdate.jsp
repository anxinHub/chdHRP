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

	var is_stop,plan_code,tab_code,col_code,grid,grids;
    
	var formValidate;
	
        var initValidate = function () {

        	formValidate = $.etValidate({
                items: [
                    { el: $("#type_code"), required: true },
                    { el: $("#type_name"), required: true },
                    { el: $("#is_stop"), required: true }
                ],
            	defaultValue: "${vo.IS_STOP}"
            });
        	
        	//查询变动项目
        	ajaxPostData({
                url: '<%=path%>/hrp/hr/salarymanagement/standard/queryStandardTabCodeOption.do?isCheck=false',
                async: false,
                 success: function (responseData) {
               	  tab_code = responseData;
                 },
             })
         	//查询工资项目
         	ajaxPostData({
                 url: 'queryItemOption.do?isCheck=false',
                 async: false,
                  success: function (responseData) {
                	  plan_code = responseData;
                  },
              })
        	
              is_stop = $("#is_stop").etSelect({
		        options: [
		            { id: 0, text: '否' },
		            { id: 1, text: '是' },
		        ],
		        defaultValue: '${vo.IS_STOP}',
		    });
        	
        	$("#save").click(function(){
        		
        		if (!formValidate.test()) {
                    return;
              	}
        		
        		var forms = $("#changeform").serialize();
        		var arrchange = grid.getAllData();
        		var arrsalary = grids.getAllData();
        		
        		//
        		var keyMap = new HashMap();
				var gridarr = [];
				var recode = false;
				if(arrchange != null && arrchange.length > 0){
					$.each(arrchange,function(){
						if(this.tab_code || this.col_code){
							if(this.tab_code && this.col_code){
								if(keyMap.get(this.tab_code + this.col_code)){
									$.etDialog.error("变动项目下有重复信息!");
									recode = true;
									return false;
								}else{
									keyMap.put(this.tab_code + this.col_code,true);
									gridarr.push(this);
								}
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
				var gridarrs = [];
				if(arrsalary != null && arrsalary.length > 0){
					$.each(arrsalary,function(){
						if(this.plan_code || this.item_code){
							if(this.plan_code && this.item_code){
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
                    url: 'updateSalaryChangeType.do',
                    data:forms,
                    success: function (responseData) {
                    	parentWindowQuery();
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
                { display: '*数据表', name: 'tab_codes', width: 150,
                    editor: {
                        type: 'select',
                        keyField: 'tab_code',
                        source:tab_code,
                        change:function(row){
                        	grid.getColumns()[2].editor.url ='<%=path%>/hrp/hr/salarymanagement/standard/queryStandardColCodeOption.do?isCheck=false&code='+row.tab_code;
                        	row.col_codes = "";
                        	row.col_code = "";
                        	grid.updateRow(row._rowIndx,row)
                        }
                    }
                },
                { display: '*关联字段', name: 'col_codes', width: 150,
                    editor: {
                        type: 'select',
                        keyField:'col_code',
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
                title:"变动项目",
                showTitle:true,
                showBottom: false,
                pageModel: {type: 'remote',rPP:200,rPPOptions: [50,100,200,500]},
                columns: column,
                cellSelect:cellSelect,
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
                { display: '*工资方案', name: 'plan_codes', width: 150,
                    editor: {
                        type: 'select',
                        keyField: 'plan_code',
                        source:plan_code,
                        change:function(row){
                        	grids.getColumns()[2].editor.url ='queryPlancodeOption.do?isCheck=false&plan_code='+row.plan_code;
                        	row.item_codes = "";
                        	row.item_code = "";
                        	grids.updateRow(row._rowIndx,row)
                        },
                    }
                },
                { display: '*工资项', name: 'item_codes', width: 150,
                    editor: {
                        type: 'select',
                        keyField:'item_code',
                        url:'',
                    }
                },
            ];
            var paramObjs = {
                height: '90%',
                width:"49%",
                inWindowHeight: true,
                checkbox: true,
                editable: true,
                title:"工资项目",
                showTitle:true,
                showBottom: false,
                pageModel: {type: 'remote',rPP:200,rPPOptions: [50,100,200,500]},
                columns: columns,
                cellSelect:cellSelects,
                toolbar: {
                    items: [
                       { type: 'button', label: '添加', listeners: [{ click: adds }], icon: 'add' },
                       { type: 'button', label: '删除', listeners: [{ click: removes }], icon: 'delete' },
                    ]
                }
            };
            grids = $("#mainGrid2").etGrid(paramObjs);
        };

        var cellSelects = function(event, rowData){
			grids.getColumns()[2].editor.url ='queryPlancodeOption.do?isCheck=false&plan_code='+rowData.rowData.plan_code;
        };
        
        var cellSelect = function(event, rowData){
			grid.getColumns()[2].editor.url ='<%=path%>/hrp/hr/salarymanagement/standard/queryStandardColCodeOption.do?isCheck=false&code='+rowData.rowData.tab_code+'&is_pk=0';
        };
        
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
	    
	    var query = function(){
	   		//变动项目回显
	    	grid.loadData([],'querySalaryChangeTypeChangeProject.do?type_code='+'${vo.TYPE_CODE}');
	    	//工资项目回显
	    	grids.loadData([],'querySalaryChangeTypeSalaryProject.do?type_code='+'${vo.TYPE_CODE}');
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
	        query();
	    })
        
    </script>
</head>

<body>
	<div  id="btn"  class="button-group" style="overflow-y: auto">
		<form id="changeform">
		 <table class="table-layout" style="width: 100%;">
	        <tr>
	            <td class="label"><font size="2" color="red">*</font>薪资变动类型编码：</td>
	            <td class="ipt">
	            	<input type="hidden" id="type_code" name="type_code" value="${vo.TYPE_CODE}" >
	                <input value="${vo.TYPE_CODE}" disabled  type="text" style="width:150px;" />
	            </td>
	            <td class="label"><font size="2" color="red">*</font>薪资变动类型名称：</td>
	            <td class="ipt">
	                <input id="type_name" value="${vo.TYPE_NAME}" placeholder="如 ：入职定薪" name="type_name" type="text" style="width:150px;" />
	            </td>
	            <td class="label">是否停用：</td>
	            <td class="ipt">
	                <input  id="is_stop" name="is_stop" style="width:150px;" />
	            </td>
	        </tr>
	        <tr>
	            <td class="label">备注：</td>
	            <td class="ipt" colspan="3" style="width:0s">
	                <input  id="note" value="${vo.NOTE}" type="text" name="note" style="width:81%;" />
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