<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="hr,tree,grid,select,dialog,datepicker,validate,checkbox" name="plugins" />
	</jsp:include>
    <script>
        var grid;
        var startpicker;
        var endpicker;
        var is_total_cont;
        var is_price_cont;
        
        var query = function () {
            params = [
                { name: 'pact_type_code', value: $("#pact_type_code").val() },
                { name: 'sup_no', value: $("#sup_no").val() },
                { name: 'pact_code', value: $("#pact_code").val() },
                { name: 'trade_type', value: $("#trade_type").val() },
                { name: 'state', value: $("#state").val() },
                { name: 'state_code', value: $("#state_code").val() },
                { name: 'start_date', value: startpicker.getValue() },
                { name: 'end_date', value: endpicker.getValue() },
                { name: 'is_total_cont', value:is_total_cont.checked ? 1 : 0},
                { name: 'is_price_cont', value:is_price_cont.checked ? 1 : 0},
            ];
            grid.loadData(params);
        };
        
        var initSelect=  function(){
          	pact_type_code = $("#pact_type_code").etSelect({url: '../../../basicset/select/queryPactTypeFKXYSelect.do?isCheck=false&FKXY_Attr='+"01",defaultValue: "none"});
            state = $("#state").etSelect({url: '../../../basicset/select/queryDictSelect.do?isCheck=false&f_code=STATE',defaultValue: "none"});
            trade_type = $("#trade_type").etSelect({url: '../../../basicset/select/queryDictSelect.do?isCheck=false&f_code=TRADE_TYPE',defaultValue: "none"});
            state_code = $("#state_code").etSelect({ url: '../../../basicset/select/queryPactStateSelect.do?isCheck=false', defaultValue: "none"});
            sup_no = $("#sup_no").etSelect({  url: '../../../basicset/select/queryHosSupDictSelect.do?isCheck=false', defaultValue: "none"});
          }
        
        var initGrid = function () {
            var columns = [
            	 { display: '协议编号', name: 'pact_code', width: '150px',
            		 render : function(ui) {
         			    var rowdata = ui.rowData.pact_code;
         			    var state = ui.rowData.state_name;
         			    return "<a href=javascript:edit('"+rowdata+"')><b>"+ui.rowData.pact_code+"</b></font></a>";
         		 	 }
            	 },
                 { display: '协议名称', name: 'pact_name', width: '190px'},
                 { display: '供应商', name: 'sup_name',  width: '190px'},
                 { display: '签订日期', name: 'sign_date', align: 'center', width: '90px'},
                 { display: '签订科室', name: 'dept_name',  width: '120px'},
                 { display: '协议总金额', name: 'pact_money', align: 'right', width: '120px',
                   	 render:function(ui){
     	          		  var data = ui.rowData
     	          		  return formatNumber(parseFloat(data.pact_money),2,1)
     	          	 	}
                   },
                 { display: '贸易类别', name: 'trade_name', align: 'center', width: '80px'},
                 { display: '协议状态', name: 'state_code_name',  align: 'center', width: '80px'},
                 { display: '审核状态', name: 'state_name', align: 'center', width: '80px'},
            ];
            var paramObj = {
            	editable: false,
            	height: '99%',
            	width:'99%',
            	checkbox: true,
                dataModel: {
                   url: '../pactinit/queryPactFKXY.do?isCheck=false&is_init='+0+'&FKXY_Attr='+"01"
                },
                rowDblClick: function (event, ui) {
                    var rowData = ui.rowData;
                    var pact_code = ui.rowData.pact_code;
                    edit(pact_code);
                },
                columns: columns,
                toolbar: {
                    items: [
                        { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                        { type: 'button', label: '添加', listeners: [{ click: save }], icon: 'add' },
                        { type: 'button', label: '删除', listeners: [{ click: remove }], icon: 'del' },
                        { type: 'button', label: '审核', listeners: [{ click: check }], icon: 'audit' },
                        { type: 'button', label: '消审', listeners: [{ click: uncheck }], icon: 'back' },
                        { type: 'button', label: '确认', listeners: [{ click: confir }], icon: 'audit' },
                        { type: 'button', label: '取消确认', listeners: [{ click: unconfir }], icon: 'back' },
                        
                    ]
                }
            };
            grid = $("#mainGrid").etGrid(paramObj);
        };
        
        var check = function(data){
             var data = grid.selectGet();
             if (data.length == 0) {
                 $.etDialog.error('请选择行');
             } else {
                 var param = [];
                 $(data).each(function () {
	                 var rowdata = this.rowData;
                	 if(rowdata.state == 1 && rowdata.state_code == '11'){
	                     param.push(rowdata.pact_code);
                	 }
                 });
                 if(param.length == 0){
               		 $.etDialog.error('当前状态不可审核');
               	 	return;
                }
                   ajaxPostData({
                       url: '../pactinit/checkPactMainFKXY.do',
                       data: {
                      	 mapVo: JSON.stringify(param),
                      	 check: 'check',
                      	is_init:0
                       },
                       success:function(){
                    	   query();
                       }
                   })
             }
        }
        var uncheck = function(data){
             var data = grid.selectGet();
             if (data.length == 0) {
                 $.etDialog.error('请选择行');
             } else {
                 var param = [];
                 $(data).each(function () {
                     var rowdata = this.rowData;
                     if(rowdata.state == 2 && rowdata.state_code == '11'){
                    	  param.push(rowdata.pact_code);
                     }
                 });
                 if(param.length == 0){
                	 $.etDialog.error('当前状态不可消审');
                	 return;
                 }
                   ajaxPostData({
                       url: '../pactinit/checkPactMainFKXY.do',
                       data: {
                      	 mapVo: JSON.stringify(param),
                      	 check: 'uncheck',
                      	is_init:0
                       },
                       success:function(){
                    	   query();
                       }
                   })
             }
        }
        var confir = function(data){
            var data = grid.selectGet();
            if (data.length == 0) {
                $.etDialog.error('请选择行');
            } else {
                var param = [];
                $(data).each(function () {
                    var rowdata = this.rowData;
                    if(rowdata.state == 2 && rowdata.state_code == '11'){
                    	param.push(rowdata.pact_code);
                    }
                });
                if(param.length == 0){
               		 $.etDialog.error('当前状态不可确认');
               	 	return;
                }
                  ajaxPostData({
                      url: '../pactinit/checkPactMainFKXY.do',
                      data: {
                     	 mapVo: JSON.stringify(param),
                     	 check: 'confir',
                     	is_init:0
                      },
                      success:function(){
                    	  query();
                      }
                  })
            }
       }
        var unconfir = function(data){
            var data = grid.selectGet();
            if (data.length == 0) {
                $.etDialog.error('请选择行');
            } else {
                var param = [];
                $(data).each(function () {
                    var rowdata = this.rowData;
                    if(rowdata.state == 3 && rowdata.state_code=='12'){
                    	param.push(rowdata.pact_code);
                    }
                });
                if(param.length == 0){
               		 $.etDialog.error('当前状态不可取消确认');
               	 	return;
                }
                  ajaxPostData({
                      url: '../pactinit/checkPactMainFKXY.do',
                      data: {
                     	 mapVo: JSON.stringify(param),
                     	 check: 'unconfir',
                     	is_init:0
                      },
                      success:function(){
                    	  query();
                      }
                  })
            }
       }
        //跳转保存页面
        var save = function (data) {
        	 parent.$.etDialog.open({
                 url: 'hrp/pac/fkxy/pactinfo/pactinit/pactInitFKXYAddPage.do?isCheck=false&is_init=0',
                 //width: $(window).width(),
                 //height: $(window).height(),
                 isMax:true,
                 title: '添加',
                 modal: true,
                 zIndex: 10,
                 frameName: window.name
             });
        };
        //跳转保存页面
        var edit = function (pact_code) {
        	parent.$.etDialog.open({
                 url: 'hrp/pac/fkxy/pactinfo/pactinit/pactFKXYEditPage.do?isCheck=false&is_init=0&pact_code='+pact_code,
                 //width: $(window).width(),
                 //height: $(window).height(),
                 isMax:true,
                 title: '修改',
                 modal: true,
                 frameName: window.name
             });
        };
        
        //删除
        var remove = function () {
        	 var data = grid.selectGet();
             if (data.length == 0) {
                 $.etDialog.error('请选择行');
             } else {
                 var param = [];
                 $(data).each(function () {
                     var rowdata = this.rowData;
                	 if(rowdata.state == 1){
	                     rowdata.group_id = ${group_id};
	                     rowdata.hos_id = ${hos_id};
	                     rowdata.copy_code = '${copy_code}';
	                     param.push(rowdata);
                	 }
                 });
                 if(param.length == 0){$.etDialog.error('暂无数据可删除');return;}
                 $.etDialog.confirm('确定删除?', function () {
                     ajaxPostData({
                         url: '../pactinit/deletePactFKXY.do?isCheck=false',
                         data: {
                        	 mapVo: JSON.stringify(param)
                         },
                         success: function () {
                             grid.deleteRows(data);
                         }
                     })
                 });
             }
        };        
        
        $(function () {
            initSelect();
            initfrom();
            initGrid();
        })
        
        //日期
        var initfrom = function(){
        	startpicker = $("#start_date").etDatepicker({
    			defaultDate: "yyyy-fM-fd",
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
    		
    		is_total_cont = $('#is_total_cont').etCheck();
    		is_price_cont = $('#is_price_cont').etCheck();
    		
	}
    </script>
</head>

<body>
    <table class="table-layout">
        <tr>
            <td class="label" style="width: 100px;">签订日期：</td>
            <td class="ipt" style="width: 220px;">
                <input id="start_date" type="text" style="width: 100px"/>至 <input id="end_date" type="text" style="width: 100px"/>
            </td>
            <td class="label" style="width: 100px;">协议类型：</td>
            <td class="ipt"><select id="pact_type_code" style="width: 180px"></select> </td>
            <td class="label" style="width: 100px;">供应商：</td>
            <td class="ipt"> <select id="sup_no" style="width: 180px"></select> </td>
        </tr>
        <tr>
        	<td class="label" style="width: 100px;">贸易类别：</td>
            <td class="ipt"> <select id="trade_type" style="width: 180px"></select> </td>
        	<td class="label" style="width: 100px;">审核状态：</td>
            <td class="ipt"> <select id="state" style="width: 180px"></select> </td>
        	<td class="label" style="width: 100px;">协议状态：</td>
            <td class="ipt"> <select id="state_code" style="width: 180px"></select> </td>
        </tr>
        <tr>
            <td class="label" style="width: 100px;">协议编号：</td>
            <td class="ipt"><input id="pact_code" type="text" /> </td>
            <td><input id="is_total_cont" type="checkbox" />总额控制</td>
			<td><input id="is_price_cont" type="checkbox" />单价控制</td>
        </tr>
    </table>
    <div id="mainGrid"></div>
</body>

</html>

