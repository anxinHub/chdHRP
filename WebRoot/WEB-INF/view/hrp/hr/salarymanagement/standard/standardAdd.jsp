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

	var start_date,state,tab_code,col_code,grid;
    
	var formValidate;
	
        var initValidate = function () {

        	formValidate = $.etValidate({
                items: [
                    { el: $("#stan_code"), required: true },
                    { el: $("#stan_name"), required: true },
                    { el: $("#state"), required: true }
                ]
            });
        	
    		state = $("#state").etSelect({
		        options: [
		            { id: 1, text: '启用' },
		            { id: 2, text: '停用' }, 
		        ],
		        defaultValue: "none",
		    });

    		start_date = $("#start_date").etDatepicker({
            	range : false,
            	defaultDate:["yyyy-mm-dd"]
            })
 			
 			//数据下拉加载
 			ajaxPostData({
               url: 'queryStandardTabCodeOption.do?isCheck=false',
               async: false,
                success: function (responseData) {
              	  tab_code = responseData;
                },
            })
 			
        	  $("#save").click( function () {

        		if (!formValidate.test()) {
                      return;
                }
        		  
        		var standardfroms = $("#standardform").serialize();
				var arr = grid.getAllData();
				var gridarr = [];
				var keyMap = new HashMap();
				var recode = false;
				
				if(arr != null && arr.length > 0){
					$.each(arr,function(){
						if(this.tab_code || this.col_code){
							if(this.tab_code && this.col_code){
								if(keyMap.get(this.tab_code + this.col_code)){
									$.etDialog.error("人员限定条件有重复信息!");
									recode = true;
									return false;
								}else{
									keyMap.put(this.tab_code + this.col_code,true);
									gridarr.push(this);
								}
							}else{
								$.etDialog.error("人员限定条件有不完整的信息!");
								recode = true;
								return false;
							}
						}
					})
				}
				if(recode){
						return false;
				}
				
				if(gridarr.length > 0){
					standardfroms += "&arrVo="+JSON.stringify(gridarr);
				}else{
					$.etDialog.error("人员限定条件至少选择一条!");
					return;
				}
                  ajaxPostData({
                     url: 'addStandard.do',
                      data:standardfroms,
                      success: function (responseData) {
                          		parentWindowQuery(responseData.id)
                          		$("#close").click()
                      },
                   
                  })
              })
            $("#close").click(function () {
                          var curIndex = parent.$.etDialog.getFrameIndex(window.name);
                          parent.$.etDialog.close(curIndex);
            })
        };
  
        var initGrid = function () {
            var columns = [
                { display: '数据表', name: 'tab_codes', width: 150, editable: true,
                    editor: {
                        type: 'select',
                        keyField: 'tab_code',
                       source:tab_code,
                       //url:"queryStandardTabCodeOptionEditable.do?isCheck=false",
/*                          change:function(row){
                        	grid.getColumns()[2].editor.url ='queryStandardColCodeOption.do?isCheck=false&tab_code='+row.tab_code;
                        	row.col_codes = "";
                        	row.col_code = "";
                        	grid.updateRow(row._rowIndx,row)
                        } */
                    }
                },
                { display: '关联字段', name: 'col_codes', width: 150,
                    editor: {
                        type: 'select',
                        keyField:'col_code',
                        url:'',
                    }
                },
            ];
            var paramObj = {
                height: $(window).height()-$("#btn").height(),
                width:"99%",
                inWindowHeight: true,
                checkbox: true,
                editable: true,
                title:'<div width="100%" align="left" style="margin-left:170px">人员限定条件</div>',
                showTitle:true,
                showBottom: false,
                pageModel: {type: 'remote',rPP:200,rPPOptions: [50,100,200,500]},
                columns: columns,
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

	    var add = function () {
		    var arr = grid.getAllData();
		    if(arr != null && arr.length >= 8){
		    	$.etDialog.error("人员限定条件最多只能填写八条!");
		    	return false;
			}
/* 		    var sss = {"tab_code":tab_code.tab_code,"tab_codes":tab_code.tab_codes};
	        grid.addRow(sss); */
	        grid.addRow();
	    };

        var cellSelect = function(event, row){
        	grid.getColumns()[2].editor.url ='queryStandardColCodeOptionEditable.do?isCheck=false&code='+row.rowData.tab_code;
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
	    
	    var parentWindowQuery = function (id){
	    	var parentFrameName = parent.$.etDialog.parentFrameName;
            var parentWindow = parent.window[parentFrameName];
            parentWindow.query();
            parentWindow.maintain(id);
		}
	    
	    $(function () {
	        initValidate();
	        initGrid();
	    })
        
    </script>
</head>

<body>
	<div  id="btn"  class="button-group" style="overflow-y: auto">
		<form id="standardform">
		 <table class="table-layout" style="width: 100%;">
	        <tr>
	            <td class="label"><font size="2" color="red">*</font>薪资标准编码：</td>
	            <td class="ipt">
	                <input id="stan_code" name="stan_code" type="text" style="width:150px;" />
	            </td>
	            <td class="label"><font size="2" color="red">*</font>薪资标准名称：</td>
	            <td class="ipt">
	                <input id="stan_name" name="stan_name" type="text" style="width:150px;" />
	            </td>
	        </tr>
	        <tr>
	            <td class="label"><font size="2" color="red">*</font>状态：</td>
	            <td class="ipt">
	                <input  id="state" type="text" name="state" style="width:150px;" />
	            </td>
	            <td class="label">启用日期：</td>
	            <td class="ipt">
	                <input  id="start_date" name="start_date" type="text" style="width:150px;" />
	            </td>
	        </tr>
    	</table>
    	</form>
    	<div id="mainGrid"></div>
        <div class="button-group btn">
	        <button id="save">保存</button>
	        <button id="close">关闭</button>
	    </div>
	</div>   
</body>

</html>