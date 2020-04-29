<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="hr,tree,grid,select,dialog,datepicker,validate" name="plugins" />
	</jsp:include>
    <script>
        var grid;
        var startpicker;
        var endpicker;
        var query = function () {
            params = [
                { name: 'pact_type_code', value: $("#pact_type_code").val() },
                { name: 'sup_no', value: $("#sup_no").val() },
                { name: 'pact_code', value: $("#pact_code").val() },
                { name: 'pact_name', value: $("#pact_name").val() },
                { name: 'change_reason', value: $("#change_reason").val() },
                { name: 'start_date', value: startpicker.getValue() },
                { name: 'end_date', value: endpicker.getValue() },
            ];
            grid.loadData(params);
        };
        
        var initSelect=  function(){
          	pact_type_code = $("#pact_type_code").etSelect({url: '../../basicset/select/queryPactTypeFKHTSelect.do?isCheck=false',defaultValue: "none"});
            sup_no = $("#sup_no").etSelect({  url: '../../basicset/select/queryHosSupDictSelect.do?isCheck=false', defaultValue: "none"});
          }
        
        var initGrid = function () {
            var columns = [
					{ display: '变更编号', name: 'change_code', width: '12%',
						 render:function(ui) {
					        		var data = ui.rowData;
					        		return "<a href=javascript:openPactChange('"+data.group_id+"|"+data.hos_id
					        				+"|"+data.copy_code+"|"+data.change_code+"|"+data.pact_code+"')>"+data.change_code+"</a>";
					      	}  
					},
					{ display: '变更日期', name: 'change_date',align: 'center',  width: '7%'},
					{ display: '合同编号', name: 'pact_code', width: '10%',
						render:function(ui) {
	 	               		var data = ui.rowData;
	 	               		return "<a href=javascript:openPact('"+data.group_id+"|"+data.hos_id
	 	               				+"|"+data.copy_code+"|"+data.pact_code+"')>"+data.pact_code+"</a>";
	                  	} 
					},
					{ display: '合同名称', name: 'pact_name', width: '12%'},
					{ display: '供应商', name: 'sup_name',  width: '12%'},
					{ display: '变更原因', name: 'change_reason', width: '15%'},
					{ display: '操作员', name: 'user_name', align: 'right',width: '9%',
							render:function(ui){
								var value = ui.rowData.plan_money
								if(value){
									return formatNumber(value,2,1);
								}else{
									return 0.00 ;
								}
							}	 
					},
					{ display: '变更前合同', name: 'change_before', align: 'center', width: '12%',
					   	render:function(ui) {
					        		var data = ui.rowData;
					        		return "<a href=javascript:openPactBefore('"+data.group_id+"|"+data.hos_id
					        				+"|"+data.copy_code+"|"+data.pact_code+"|"+data.change_before+"')>"+data.change_before+"</a>";
					      	}  
					},
					{ display: '变更后合同', name: 'change_after', align: 'center', width: '12%',
					   	render:function(ui) {
					         		var data = ui.rowData;
					         		return "<a href=javascript:openPactAfter('"+data.group_id+"|"+data.hos_id
					         				+"|"+data.copy_code+"|"+data.pact_code+"|"+data.change_after+"')>"+data.change_after+"</a>";
					   	}  	 
					 }
            ];
            var paramObj = {
            	editable: false,
            	height: '97%',
            	width:'100%',
                dataModel: {
                    url: 'queryPactMainChangeFKHT.do?isCheck=false'
                },
                rowDblClick: function (event, ui) {
                    var rowData = ui.rowData;
                    edit(rowData);
                },
                columns: columns,
                toolbar: {
                    items: [
                        { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                    ]
                }
            };
            grid = $("#mainGrid").etGrid(paramObj);
            
            $("#mainGrid").on('click','.toPre',function(){
             	 var rowIndex = $(this).attr('rowIndex');
                  var currentRowData = grid.getAllData()[rowIndex];
                  toPre(currentRowData);
       		})
            $("#mainGrid").on('click','.toPact',function(){
             	 var rowIndex = $(this).attr('rowIndex');
                  var currentRowData = grid.getAllData()[rowIndex];
                  toPact(currentRowData);
       		})
        };
        
        //跳转资金来源页面
        function toPre(rowData){
       	 	parent.$.etDialog.open({
       	 	 url: 'hrp/pac/fkht/change/pactMainChangeFKHTPrePage.do?isCheck=false&change_code='+rowData.change_code +'&pact_code='+rowData.pact_code,
                width: $(window).width(),
                height: $(window).height(),
                title: '查看',
                modal: true,
            });
        }
        
        //合同页面查看
        function openPact(obj){
        	var vo = obj.split("|");
       	 	parent.$.etDialog.open({
       	 	 url: 'hrp/pac/fkht/pactinfo/pactinit/pactMainFKHTEdit.do?isCheck=false&pact_code='+vo[3],
                width: $(window).width(),
                height: $(window).height(),
                title: '合同信息',
                frameName: window.name
            });
        }
        
      //修改
        function openPactChange(obj){
        	var vo = obj.split("|");
        	var parm = "&group_id="+vo[0] + "&hos_id="+vo[1] + "&copy_code="+vo[2] + "&change_code="+vo[3]+ "&pact_code="+vo[4] 
       	 	parent.$.etDialog.open({
       	 	 url: 'hrp/pac/fkht/change/pactChangeFKHTAfterUpdatePage.do?isCheck=false'+parm,
                width: $(window).width(),
                height: $(window).height(),
                title: '修改',
                frameName: window.name
            });
        }
      //变更前合同页面查看
        function openPactBefore(obj){
        	var vo = obj.split("|");
       	 	parent.$.etDialog.open({
       	 	 url: 'hrp/pac/fkht/pactinfo/pactinit/pactMainFKHTEdit.do?isCheck=false&pact_code='+vo[3]+'&change_code='+vo[4],
                width: $(window).width(),
                height: $(window).height(),
                title: '变更前合同信息',
                frameName: window.name
            });
        }
      
      	//变更后合同页面查看
        function openPactAfter(obj){
        	var vo = obj.split("|");
       	 	parent.$.etDialog.open({
       	 	 url: 'hrp/pac/fkht/pactinfo/pactinit/pactMainFKHTEdit.do?isCheck=false&pact_code='+vo[3]+'&change_code='+vo[4],
                width: $(window).width(),
                height: $(window).height(),
                title: '变更后合同信息',
                frameName: window.name
            });
        }
        
        $(function () {
            initSelect();
            initfrom();
            initGrid();
        })
        
        //日期
        var initfrom = function(){
        	startpicker = $("#start_date").etDatepicker({
    			defaultDate: 'yyyy-fm-fd',
    		  	onChange: function (date) {
    		  		var end = endpicker.getValue();
    		  		if(end < date){
    		  			endpicker.setValue(end);
    		  		}
    		  	}
    		});
    		endpicker = $("#end_date").etDatepicker({
    			defaultDate: true,
    		  	onChange: function (date) {
    		  		var start = startpicker.getValue();
    		  		if(start > date){
    		  			endpicker.setValue(start);
    		  		}
    		  	}
    		});
	}
    </script>
</head>

<body>
    <table class="table-layout">
        <tr>
            <td class="label" style="width: 100px;">变更日期：</td>
            <td class="ipt" style="width: 220px;">
                <input id="start_date" type="text" style="width: 100px"/>至 <input id="end_date" type="text" style="width: 100px"/>
            </td>
            <td class="label" style="width: 100px;">合同类型：</td>
            <td class="ipt"><select id="pact_type_code" style="width: 180px"></select> </td>
            <td class="label" style="width: 100px;">供应商：</td>
            <td class="ipt"> <select id="sup_no" style="width: 180px"></select> </td>
        </tr>
        <tr>
            <td class="label" style="width: 100px;">合同编号：</td>
            <td class="ipt"><input id="pact_code" type="text" /> </td>
            <td class="label" style="width: 100px;">合同名称：</td>
            <td class="ipt"><input id="pact_name" type="text" /> </td>
            <td class="label" style="width: 100px;">变更原因：</td>
            <td class="ipt"><input id="change_reason" type="text" /> </td>
        </tr>
    </table>
    <div id="mainGrid"></div>
</body>

</html>

