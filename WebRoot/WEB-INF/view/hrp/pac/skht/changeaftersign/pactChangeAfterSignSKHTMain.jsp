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
        var state;
        var query = function () {
            params = [
                { name: 'pact_type_code', value: $("#pact_type_code").val() },
                { name: 'cus_no', value: $("#cus_no").val() },
                { name: 'pact_code', value: $("#pact_code").val() },
                { name: 'pact_name', value: $("#pact_name").val() },
                { name: 'change_reason', value: $("#change_reason").val() },
                { name: 'start_date', value: startpicker.getValue() },
                { name: 'end_date', value: endpicker.getValue() },
                {name:'state',value:$("#state").val()},
                { name:'is_exe',value:'1'}
            ];
            grid.loadData(params);
        };
        
        var initSelect=  function(){
          	pact_type_code = $("#pact_type_code").etSelect({url: '../../basicset/select/queryPactTypeSKHTSelect.do?isCheck=false',defaultValue: "none"});
          	cus_no = $("#cus_no").etSelect({url: '../../basicset/select/queryHosCusDictSelect.do?isCheck=false',defaultValue: "none"});
         
          state=$("#state").etSelect({
        	 options:[{id:'1',text:'新建'},
        	          {id:'2',text:'提交'},
        	          {id:'3',text:'确认'}],
        	  valueField:'id',
        	  lableField:'text',
        	  defaultValue: '' 
        	          
         });
        }
        
        var initGrid = function () {
            var columns = [
                 { display: '变更编号', name: 'change_code', width: '14%'},
                 { display: '变更日期', name: 'change_date',align: 'center',  width: '8%'},
            	 { display: '合同编号', name: 'pact_code', width: '13%',
                	render : function (data){
                		return '<a class="toPact" rowIndex = "'+data.rowIndx+'">'+data.rowData.pact_code+'</a>'
                	}	 
            	 },
                 { display: '合同名称', name: 'pact_name', width: '15%'},
                 { display: '客户', name: 'cus_name',  width: '15%'},
                 { display: '变更原因', name: 'change_reason', width: '18%'},
                 { display: '操作员', name: 'user_name',  width: '10%'},
                 { display: '变更前合同查看', name: '', align: 'center', width: '15%',
                	render :function(data){
                		return '<a class="toPreBefor" rowIndex = "'+data.rowIndx+'">'+data.rowData.change_code.split("-")[0]+"-"+(parseInt(data.rowData.change_code.split("-")[1])-1)+'</a>'
                	}	 
                 }, { display: '变更后合同查看', name: '', align: 'center', width: '15%',
                	render :function(data){
                		return '<a class="toPre" rowIndex = "'+data.rowIndx+'">'+data.rowData.change_code+'</a>'
                	}	 
                 },
                 {display:'确认人',name:'confirmer',align:'center',width:'10%'},
                 {display:'确认时间',name:'confirm_date',align:'center',width:'10%'},
                 {display:'状态',name:'state_name',align:'center',width:'10%',
                  render:function(data){
                	  if(data.rowData.state==1){
                		  return "新建";
                	  }else if(data.rowData.state==2){
                		  return "提交";
                	  }else{
                		  return "审核";
                	  }
                  }	 
                 },
                 {display:'state',name:'state',align:'center',width:'10%',hidden:true}
            ];
            var paramObj = {
            	editable: false,
            	height: '97%',
            	width:'100%',
            	checkbox:true,
                dataModel: {
                    url: '../change/queryPactMainChangeSKHT.do?isCheck=false&is_exe=1'
                },
                rowDblClick: function (event, ui) {
                    var rowData = ui.rowData;
                    edit(rowData);
                },
                columns: columns,
                toolbar: {
                    items: [
                        { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                        { type: 'button', label: '添加', listeners: [{ click: add }], icon: 'save' },
                        { type: 'button', label: '删除', listeners: [{ click: del }], icon: 'del' },
                        { type: 'button', label: '提交', listeners: [{ click: submit }], icon: 'audit' },
                        { type: 'button', label: '撤销', listeners: [{ click: cancel }], icon: 'back' },
                        { type: 'button', label: '确认', listeners: [{ click: confirm }], icon: 'audit' },
                    ]
                }
            };
            grid = $("#mainGrid").etGrid(paramObj);
            
            $("#mainGrid").on('click','.toPre',function(){
             	 var rowIndex = $(this).attr('rowIndex');
                  var currentRowData = grid.getAllData()[rowIndex];
                  toPre(currentRowData);
       		})
       		
       		   $("#mainGrid").on('click','.toPreBefor',function(){
             	 var rowIndex = $(this).attr('rowIndex');
                  var currentRowData = grid.getAllData()[rowIndex];
                  toPreBefor(currentRowData);
       		})
       		
            $("#mainGrid").on('click','.toPact',function(){
             	 var rowIndex = $(this).attr('rowIndex');
                  var currentRowData = grid.getAllData()[rowIndex];
                  toPact(currentRowData);
       		})
        };
        
        //跳转备份合同
        function toPre(rowData){
       	 	parent.$.etDialog.open({
       	 	 url: 'hrp/pac/skht/change/pactMainChangeSKHTPrePage.do?isCheck=false&change_code='+rowData.change_code +'&pact_code='+rowData.pact_code,
                width: $(window).width(),
                height: $(window).height(),
                title: '查看',
                modal: true,
            });
        };
        
        //跳转备份合同
        function toPreBefor(rowData){
       	 	parent.$.etDialog.open({
       	 	 url: 'hrp/pac/skht/change/pactMainChangeSKHTPrePage.do?isCheck=false&change_code='+rowData.change_code.split("-")[0]+"-"+(parseInt(rowData.change_code.split("-")[1])-1) +'&pact_code='+rowData.pact_code,
                width: $(window).width(),
                height: $(window).height(),
                title: '查看',
                modal: true,
            });
        }
        
        function toPact(rowData){
        	parent.$.etDialog.open({
                url: 'hrp/pac/skht/pactinfo/pactexec/pactExecSKHTEdit.do?isCheck=false&pact_code='+rowData.pact_code,
                width: $(window).width(),
                height: $(window).height(),
                title: '查看',
                modal: true,
                //zIndex: 10,
                frameName: window.name
            });
        }
       function edit(rowData){
    	   parent.$.etDialog.open({
        	 	 url: 'hrp/pac/skht/changeaftersign/updatePactAfterChangeSKHT.do?isCheck=false&change_code='+rowData.change_code+"&pact_code="+rowData.pact_code+"&state="+rowData.state,
                 width: $(window).width(),
                 height: $(window).height(),
                 title: '修改',
                 modal: true,
                 frameNameObj :{'editmain' : window.name} 
                
             }); 
       }  
        //添加（新增变更记录）
        function add(){
       	 	parent.$.etDialog.open({
          	 	 url: 'hrp/pac/skht/changeaftersign/addPactAfterChangeSKHT.do',
                   width: $(window).width(),
                   height: $(window).height(),
                   title: '添加',
                   modal: true,
                  // zIndex: 10,
                   frameNameObj :{'add' : window.name} 
               });
        };
        
        function del(){
        	 var data = grid.selectGet();
             if (data.length == 0) {
                 $.etDialog.error('请选择行');
             } else {
                 var param = [];
                 $(data).each(function () {
                     var rowdata = this.rowData;
                     if(rowdata.state == 1){
	                     param.push(rowdata.pact_code+"@"+rowdata.change_code);
                     }
                 });
                 
            
                 if(param.length == 0){$.etDialog.error('当前状态不可删除');return;}
                 $.etDialog.confirm('确定删除?', function () {
                     ajaxPostData({
                         url: '../changeaftersign/deletePactMainSKHTC.do?isCheck=false',
                         data: {
                        	 param: param.toString()
                         },
                         success: function () {
                             grid.deleteRows(data);
                         }
                     })
                 });
             }
        	
        };
        
        function submit(){

            var data = grid.selectGet();
            if (data.length == 0) {
                $.etDialog.error('请选择行');
            } else {
                var param = [];
                $(data).each(function () {
	                 var rowdata = this.rowData;
               	 if(rowdata.state == 1){
               	  param.push(rowdata.pact_code+"@"+rowdata.change_code);
               	 }
                });
                if(param.length == 0){
              		 $.etDialog.error('当前状态不可提交');
              	 	return;
               }
                  ajaxPostData({
                      url: '../changeaftersign/submitPactMainSKHTC.do?isCheck=false',
                      data: {
                    	  param: param.toString()
                      },
                      success:function(){
                   	   query();
                      }
                  })
            }
        };
        
        function cancel(){

            var data = grid.selectGet();
            if (data.length == 0) {
                $.etDialog.error('请选择行');
            } else {
                var param = [];
                $(data).each(function () {
	                 var rowdata = this.rowData;
               	 if(rowdata.state == 2){
               	  param.push(rowdata.pact_code+"@"+rowdata.change_code);
               	 }
                });
                if(param.length == 0){
              		 $.etDialog.error('当前状态不可撤销');
              	 	return;
               }
                  ajaxPostData({
                      url: '../changeaftersign/cancelPactMainSKHTC.do?isCheck=false',
                      data: {
                    	  param: param.toString()
                      },
                      success:function(){
                   	   query();
                      }
                  })
            }
        };
        
        function confirm(){

            var data = grid.selectGet();
            if (data.length == 0) {
                $.etDialog.error('请选择行');
            } else {
                var param = [];
                $(data).each(function () {
	                 var rowdata = this.rowData;
               	 if(rowdata.state == 2){
               	  param.push(rowdata.pact_code+"@"+rowdata.change_code);
               	 }
                });
                if(param.length == 0){
              		 $.etDialog.error('当前状态不可确认');
              	 	return;
               }
                $.etDialog.confirm('确定确认?', function () {
                  ajaxPostData({
                      url: '../changeaftersign/confirmPactMainSKHTC.do?isCheck=false',
                      data: {
                    	  param: param.toString()
                      },
                      success:function(){
                   	   query();
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
            <td class="label" style="width: 100px;">客户：</td>
            <td class="ipt"> <select id="cus_no" style="width: 180px"></select> </td>
        </tr>
        <tr>
            <td class="label" style="width: 100px;">合同编号：</td>
            <td class="ipt"><input id="pact_code" type="text" /> </td>
            <td class="label" style="width: 100px;">合同名称：</td>
            <td class="ipt"><input id="pact_name" type="text" /> </td>
            <td class="label" style="width: 100px;">变更原因：</td>
            <td class="ipt"><input id="change_reason" type="text" /> </td>
            <td class="label" style="width: 100px;">状态：</td>
            <td class="ipt"><select id="state"  style="width: 180px"></select> </td>
        </tr>
    </table>
    <div id="mainGrid"></div>
</body>

</html>

