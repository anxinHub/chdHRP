<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="hr,dialog,select,grid,datepicker" name="plugins" />
	</jsp:include>
	<script type="text/javascript" src="<%=path%>/lib/laydate/laydate.js"></script>
	<script src="<%=path%>/lib/map.js"></script>
    <script>
        var insur_type,insur_typeSelect;
        var grid;
        var initFrom = function () {

         	//查询工资项目
         	ajaxPostData({
                 url: 'queryAreatopayInsurtypeSelect.do?isCheck=false',
                 async: false,
                  success: function (responseData) {
                	  insur_typeSelect = responseData;
                  },
            })
        	
            insur_type = $("#insur_type").etSelect({
            	url:"queryAreatopayInsurtypeSelects.do?isCheck=false",
            	onChange: function () {
            		query();
            	},
                defaultValue: "none",
            });
        };
        
        
        var initGrid = function () {
            var yearEditor = getRecentYearForSelect();
            var columns = [
                { display: '投保地区', name: 'AREA', width:150, align: "left"},
                { display: '社保险种', name: 'INSUR_TYPES', width: 150,
                	editor: {
                        type: 'select',
                        keyField: 'INSUR_TYPE',
                        source:insur_typeSelect,
                    }	
                },
                { display: '单位缴费费率', name: 'UNIT_RATE', width: 150 ,align: "right",
                	editor: {
	    				type: 'number'
	                }	
                },
                { display: '个人缴费费率', name: 'INDIVIDUAL_RATE', width: 150 ,align: "right",
                	editor: {
	    				type: 'number'
	                }
                },
                { display: '备注', name: 'REMARK', width: 150 },
            ];
            var paramObj = {
                height: '100%',
                inWindowHeight: true,
                checkbox: true,
                editable: true,
                columns: columns,
                editorEnd: editorEnd,
                cellSelect: cellSelect,
                toolbar: {
                    items: [
                        { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                        { type: 'button', label: '添加', listeners: [{ click: add }], icon: 'add' },
                        { type: 'button', label: '删除', listeners: [{ click: remove }], icon: 'delete' },
                        { type: 'button', label: '导入', listeners: [{ click: imports }], icon: 'import' },
                        { type: 'button', label: '保存', listeners: [{ click: save }], icon: 'save' },
                    ]
                }
            };
            grid = $("#mainGrid").etGrid(paramObj);
        };

        var add = function (){
        	var param = {"UNIT_RATE":"%","INDIVIDUAL_RATE":"%"}
        	grid.addRow(param);
        }
        
        var cellSelect = function(event, ui){
        	if(ui.dataIndx == "UNIT_RATE"){
        		if(ui.rowData.UNIT_RATE.toString().indexOf("%") != -1){
        			ui.rowData.UNIT_RATE = ui.rowData.UNIT_RATE.toString().replace("%","");
        			grid.updateRow(ui._rowIndx, ui.rowData)
        		}
        	}else if(ui.dataIndx == "INDIVIDUAL_RATE"){
        		if(ui.rowData.INDIVIDUAL_RATE.toString().indexOf("%") != -1){
        			ui.rowData.INDIVIDUAL_RATE = ui.rowData.INDIVIDUAL_RATE.toString().replace("%","");
        			grid.updateRow(ui._rowIndx, ui.rowData)
        		}
        	}
        };
        var editorEnd = function(event, ui){
        	if(ui.dataIndx == "UNIT_RATE"){
       			ui.rowData.UNIT_RATE = ui.rowData.UNIT_RATE + "%";
        	}else if(ui.dataIndx == "INDIVIDUAL_RATE"){
        		ui.rowData.INDIVIDUAL_RATE = ui.rowData.INDIVIDUAL_RATE + "%";
        	}
        }
        
        var query = function(){
    		
    		params = [
    	                { name: 'area', value: $("#area").val() },
    	                { name: 'insur_type', value: insur_type.getValue() },
    	               ];
    		
        	grid.loadData(params,'queryAreatopay.do');
        }
        
        var remove = function (){
        	
        	var arrgrid = grid.selectGet();
        	
        	if(arrgrid != null && arrgrid.length > 0){
        		$.etDialog.confirm('确定删除?', function (index) {
            		grid.deleteRows(arrgrid);
            		$.etDialog.close(index)
        		});
        	}else{
        		$.etDialog.error("请选择要删除的行!");
        	}
        	
        }
        
        var imports = function(){
        	
    		var para = {
        			"column" : [ {
        				"name" : "area",
        				"display" : "投保地区",
        				"width" : "200",
        				"require" : true
        			},{
        				"name" : "insur_type",
        				"display" : "社保险种",
        				"width" : "200",
        				"require" : true
        			},{
        				"name" : "unit_rate",
        				"display" : "单位缴费费率",
        				"width" : "200",
        				"require" : false
        			},{
        				"name" : "individual_rate",
        				"display" : "个人缴费费率",
        				"width" : "200",
        				"require" : false
        			},{
        				"name" : "remark",
        				"display" : "备注",
        				"width" : "200",
        				"require" : false
        			}]
        		};
        		importSpreadView("/hrp/hr/salarymanagement/socialsecuritymanagement/areatopay/importAreatopay.do?isCheck=false", para);
        		
        }
        
        var save = function (){
        	
        
        	var arrgrid = grid.getAllData();
        	
        	
        	var keyMap = new HashMap();
			var gridarr = [];
			var recode = false;
			if(arrgrid != null && arrgrid.length > 0){
				$.each(arrgrid,function(){
						if(this.AREA || this.INSUR_TYPE){
							if(this.AREA && this.INSUR_TYPE){
								if(keyMap.get(this.AREA + this.INSUR_TYPE)){
									$.etDialog.error("{投保地区} 和 {社保险种} 有重复信息!");
									recode = true;
									return false;
								}else{
									gridarr.push({"area":this.AREA
										,"insur_type":this.INSUR_TYPE
										,"unit_rate":this.UNIT_RATE.toString().replace("%","")
										,"individual_rate":this.INDIVIDUAL_RATE.toString().replace("%","")
										,"remark":this.REMARK
										});
									keyMap.put(this.AREA + this.INSUR_TYPE,true);
								}
							}else{
								$.etDialog.error("{投保地区} 和 {社保险种} 必填!");
								recode = true;
								return false;
							}
						}
				})
			}
        	
			if(recode){
				return false;
			}
        	
         	//添加缴费项目
         	ajaxPostData({
                 url: 'addAreapay.do',
                 data:{"gridarr":JSON.stringify(gridarr)},
                 async: false,
                  success: function (responseData) {
                	  query();
                  },
            })
        	
        }
        
        $(function () {
            initFrom();
            initGrid();
            query();
        })
    </script>
</head>

<body>
    <div class="main">
        <table class="table-layout">
            <tr>
               <td class="label">投保地区：</td>
                <td class="ipt">
                    <input id="area" type="text" />
                </td>
               <td class="label">社保险种：</td>
                <td class="ipt">
                    <input id="insur_type" type="text" style="width:180px;" />
                </td>
            </tr>
        </table>
    </div>
    <div id="mainGrid"></div>
</body>

</html>