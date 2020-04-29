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
                { name: 'cus_no', value: $("#cus_no").val() },
                { name: 'pact_code', value: $("#pact_code").val() },
                { name: 'pact_name', value: $("#pact_name").val() },
                { name: 'change_reason', value: $("#change_reason").val() },
                { name: 'start_date', value: startpicker.getValue() },
                { name: 'end_date', value: endpicker.getValue() },
                { name: 'state', value:  $("#state").val() }
            ];
            grid.loadData(params);
        };
        
        var initSelect=  function(){
          	pact_type_code = $("#pact_type_code").etSelect({url: '../../basicset/select/queryPactTypeSKXYSelect.do?isCheck=false',defaultValue: "none"});
          	cus_no = $("#cus_no").etSelect({  url: '../../basicset/select/queryHosCusDictSelect.do?isCheck=false', defaultValue: "none"});
            state = $("#state").etSelect({
            	defaultValue: "none",
            	options: [
            		{ id: 1, text: '新建' },
    		        { id: 2, text: '提交' },
    		        { id: 3, text: '确认' }
    		    ]
            });
        }
        
        var initGrid = function () {
            var columns = [
                 { display: '变更编号', name: 'change_code', width: '15%',
                	render : function (data){
                		return '<a class="toChangeAftEdit" rowIndex = "'+data.rowIndx+'">'+data.rowData.change_code+'</a>'
                	}
                 },
                 { display: '变更日期', name: 'change_date',align: 'center',  width: '10%'},
            	 { display: '协议编号', name: 'pact_code', width: '12%',
                	render : function (data){
                		return '<a class="toPact" rowIndex = "'+data.rowIndx+'">'+data.rowData.pact_code+'</a>'
                	}	 
            	 },
                 { display: '协议名称', name: 'pact_name', width: '10%'},
                 { display: '客户', name: 'cus_name',  width: '8%'},
                 { display: '变更原因', name: 'change_reason', width: '10%'},
                 { display: '变更前协议查看', name: '', align: 'center', width: '15%',
                	render :function(data){
                		var code = data.rowData.change_code.toString();
                		var change_code = code.match(/(\S*)-/)[1]+'-'+(parseInt(code.match(/-(\S*)/)[1])-1);
                		return '<a class="toBef" rowIndex = "'+data.rowIndx+'">'+change_code+'</a>'
                	}	 
                 },
                 { display: '变更后协议查看', name: '', align: 'center', width: '15%',
                	render : function (data){
                		return '<a class="toChangeAftEdit" rowIndex = "'+data.rowIndx+'">'+data.rowData.change_code+'</a>'
                	}	 
                 },
                 { display: '确认人', name: 'confirmer_name',  width: '10%'},
                 { display: '确认日期', name: 'confirm_date', width: '10%'},
                 { display: '状态', name: 'state',  width: '10%',
                	 render : function (data){
                  		if(data.rowData.state == "1"){
                  			return "新建";
                  		}
                  		if(data.rowData.state == "2"){
                  			return "提交";
                  		}
                  		if(data.rowData.state == "3"){
                  			return "确认";
                  		}
                  	}
                 }
            ];
            var paramObj = {
            	editable: false,
            	height: '97%',
            	width:'100%',
            	checkbox: true,
                dataModel: {
                    url: 'queryPactChangeAftSKXYMain.do?isCheck=false'
                },
                columns: columns,
                toolbar: {
                    items: [
                        { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                        { type: 'button', label: '添加', listeners: [{ click: save }], icon: 'add' },
                        { type: 'button', label: '删除', listeners: [{ click: remove }], icon: 'del' },
                        { type: 'button', label: '提交', listeners: [{ click: check }], icon: 'audit' },
                        { type: 'button', label: '撤回', listeners: [{ click: uncheck }], icon: 'back' },
                        { type: 'button', label: '确认', listeners: [{ click: confir }], icon: 'audit' }
                    ]
                }
            };
            grid = $("#mainGrid").etGrid(paramObj);
            
            $("#mainGrid").on('click','.toAft',function(){
             	 var rowIndex = $(this).attr('rowIndex');
                  var currentRowData = grid.getAllData()[rowIndex];
                  toAft(currentRowData);
       		})
       		$("#mainGrid").on('click','.toChangeAftEdit',function(){
             	 var rowIndex = $(this).attr('rowIndex');
                  var currentRowData = grid.getAllData()[rowIndex];
                  toChangeAftEdit(currentRowData);
       		})
       		$("#mainGrid").on('click','.toBef',function(){
             	 var rowIndex = $(this).attr('rowIndex');
                  var currentRowData = grid.getAllData()[rowIndex];
                  var code = currentRowData.change_code;
                  var change_code = code.match(/(\S*)-/)[1]+'-'+(parseInt(code.match(/-(\S*)/)[1])-1);
                  var pact_code = currentRowData.pact_code;
                  toBef(change_code,pact_code);
       		})
            $("#mainGrid").on('click','.toPact',function(){
             	 var rowIndex = $(this).attr('rowIndex');
                  var currentRowData = grid.getAllData()[rowIndex];
                  toPact(currentRowData);
       		})
        };
        //提交
        var check = function(data){
            var data = grid.selectGet();
            if (data.length == 0) {
                $.etDialog.error('请选择行');
            } else {
                var param = [];
                $(data).each(function () {
	                 var rowdata = this.rowData;
	               	 if(rowdata.state == 1){
	                     param.push(rowdata);
	               	 }
                });
                if(param.length == 0){
              		 $.etDialog.error('当前状态不可提交！');
              	 	return;
                }
                ajaxPostData({
                     url: 'checkPactMainSKXYC.do?isCheck=false',
                     data: {
	                      mapVo: JSON.stringify(param),
	                      check: 'check',
	                      is_init :1
                     },
                     success:function(){
                   	  query();
                     }
                })
            }
       }
       //撤回
       var uncheck = function(data){
            var data = grid.selectGet();
            if (data.length == 0) {
                $.etDialog.error('请选择行');
            } else {
                var param = [];
                $(data).each(function () {
                    var rowdata = this.rowData;
                    if(rowdata.state == 2){
                    	param.push(rowdata);
                    }
                });
                if(param.length == 0){
               	 $.etDialog.error('当前状态不可撤回');
               	 return;
                }
                  ajaxPostData({
                      url: 'checkPactMainSKXYC.do?isCheck=false',
                      data: {
                     	 mapVo: JSON.stringify(param),
                     	 check: 'uncheck',
                     	is_init :1
                      },
                      success:function(){
                   	   query();
                      }
                  })
            }
       }
       //确认
       var confir = function(data){
           var data = grid.selectGet();
           if (data.length == 0) {
               $.etDialog.error('请选择行');
           } else {
               var param = [];
               $(data).each(function () {
                   var rowdata = this.rowData;
                   if(rowdata.state == 2){
                   	param.push(rowdata);
                   }
               });
               if(param.length == 0){
              		 $.etDialog.error('当前状态不可确认');
              	 	return;
               }
                 ajaxPostData({
                     url: 'checkPactMainSKXYC.do?isCheck=false',
                     data: {
                    	 mapVo: JSON.stringify(param),
                    	 check: 'confir',
                    	is_init :1
                     },
                     success:function(){
                   	  query();
                     }
                 })
           }
      }
      //添加
      var save = function (data) {
       	 parent.$.etDialog.open({
                url: 'hrp/pac/skxy/changeafter/pactChangeAftSKXYAddPage.do?isCheck=false',
                width: $(window).width(),
                height: $(window).height(),
                title: '添加',
                modal: true,
                frameName: window.name
            });
       };
       
       //删除
       var remove = function () {
       	 var data = grid.selectGet();
       	 var msg = "";
            if (data.length == 0) {
                $.etDialog.error('请选择行');
            } else {
                var param = [];
                $(data).each(function () {
                    var rowdata = this.rowData;
                    var rowIndx = parseInt(this.rowIndx)+1;//行号
               	 if(rowdata.state != 1){
               		var state_name="";
              		 if(rowdata.state == "1"){
              			state_name="新建";
              		 }
              		 if(rowdata.state == "2"){
              			state_name="提交";
              		 }
              		 if(rowdata.state == "3"){
              			state_name="确认";
              		 }
               		 msg +="第["+rowIndx+"]行状态["+state_name+"] 不能删除！"
               	 }
               	 	rowdata.group_id = ${group_id};
                    rowdata.hos_id = ${hos_id};
                    rowdata.copy_code = '${copy_code}';
                    param.push(rowdata);
                });
                if(msg.length > 0){
               	 $.etDialog.error(msg);
               	 return;
                }
                $.etDialog.confirm('确定删除?', function () {
                    ajaxPostData({
                        url: 'deletePactSKXYC.do?isCheck=false',
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
        
        
        function toAft(rowData){
       	 	parent.$.etDialog.open({
       	 	 url: 'hrp/pac/skxy/change/pactMainChangeSKXYPrePage.do?isCheck=false&change_code='+rowData.change_code +'&pact_code='+rowData.pact_code,
                width: $(window).width(),
                height: $(window).height(),
                title: '查看',
                modal: true,
            });
        }
        
        function toBef(change_code,pact_code){
       	 	parent.$.etDialog.open({
       	 	 url: 'hrp/pac/skxy/change/pactMainChangeSKXYPrePage.do?isCheck=false&change_code='+change_code +'&pact_code='+pact_code,
                width: $(window).width(),
                height: $(window).height(),
                title: '查看',
                modal: true,
            });
        }
        
        function toPact(rowData){
        	parent.$.etDialog.open({
                url: 'hrp/pac/skxy/pactinfo/pactexec/pactExecSKXYEdit.do?isCheck=false&pact_code='+rowData.pact_code,
                width: $(window).width(),
                height: $(window).height(),
                title: '查看',
                modal: true,
            });
        }
        
        function toChangeAftEdit(rowData){
        	parent.$.etDialog.open({
                url: 'hrp/pac/skxy/changeafter/pactChangeAfterSKXYEdit.do?isCheck=false&change_code='+rowData.change_code+'&pact_code='+rowData.pact_code+'&state='+rowData.state,
                width: $(window).width(),
                height: $(window).height(),
                title: '查看',
                modal: true,
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
            <td class="label" style="width: 100px;">协议类型：</td>
            <td class="ipt"><select id="pact_type_code" style="width: 180px"></select> </td>
            <td class="label" style="width: 100px;">客户：</td>
            <td class="ipt"> <select id="cus_no" style="width: 180px"></select> </td>
            <td class="label" style="width: 100px;">审核状态：</td>
            <td class="ipt"> <select id="state" style="width: 180px"></select> </td>
        </tr>
        <tr>
            <td class="label" style="width: 100px;">协议编号：</td>
            <td class="ipt"><input id="pact_code" type="text" /> </td>
            <td class="label" style="width: 100px;">协议名称：</td>
            <td class="ipt"><input id="pact_name" type="text" /> </td>
            <td class="label" style="width: 100px;">变更原因：</td>
            <td class="ipt"><input id="change_reason" type="text" /> </td>
        </tr>
    </table>
    <div id="mainGrid"></div>
</body>

</html>

