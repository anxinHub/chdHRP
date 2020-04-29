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
        var startpicker,endpicker,pact_type_code ,sup_no ,state;
        var query = function () {
            params = [
				{ name: 'start_date', value: startpicker.getValue() },
				{ name: 'end_date', value: endpicker.getValue() },
                { name: 'pact_type_code', value: pact_type_code.getValue() },
                { name: 'sup_no', value: sup_no.getValue() },
                { name: 'pact_code', value: $("#pact_code").val() },
                { name: 'pact_name', value: $("#pact_name").val() },
                { name: 'change_reason', value: $("#change_reason").val() },
                { name: 'ass_card_no', value: $("#ass_card_no").val() },
                { name: 'value_c_code', value: $("#value_c_code").val() },
                { name: 'state', value: state.getValue() },
                
            ];
            grid.loadData(params);
        };
        
        var initSelect=  function(){
          	pact_type_code = $("#pact_type_code").etSelect({url: '../../basicset/select/queryPactTypeFKHTSelect.do?isCheck=false',defaultValue: "none"});
            sup_no = $("#sup_no").etSelect({  url: '../../basicset/select/queryHosSupDictSelect.do?isCheck=false', defaultValue: "none"});
            state = $("#state").etSelect({
          		defaultValue: "none",
          		options: [{ id: 0, text: '新建' },
          		          { id: 1, text: '提交' },
          		          { id: 2, text: '确认' }]
  		  	});
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
                 { display: '变动金额', name: 'plan_money', align: 'right',width: '9%',
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
                  },
                  { display: '关联卡片', name: 'card_amount', align: 'center', width: '5%',
	                	render:function(ui) {
	   	               		var data = ui.rowData;
	   	               		if(data.card_amount){
	   	               			return "<a href=javascript:openAssCard('"+data.group_id+"|"+data.hos_id
	               					+"|"+data.copy_code+"|"+data.pact_code+"|"+data.change_code+"')>"+data.card_amount+"</a>";
	   	               		}else{
	   	               			return 0;
	   	               		}
	   	               		
                 		}  	 
               	  },
	              { display: '原值变动单', name: 'value_c_code', align: 'center', width: '10%',
	                	render:function(ui) {
	   	               		var data = ui.rowData;
	   	               		return "<a href=javascript:openAssValue('"+data.group_id+"|"+data.hos_id
	   	               				+"|"+data.copy_code+"|"+data.change_code+"|"+data.pact_code+"')>"+data.value_c_code+"</a>";
              			}  	 
            	  },
                  { display: '确认人', name: 'confirm_name',  width: '8%'},
                  { display: '确认日期', name: 'confirm_date',  width: '8%'},
            ];
            var paramObj = {
            	editable: false,
            	checkbox: true,
            	height: '97%',
            	width:'100%',
                dataModel: {
                    url: 'queryPactMainChangeFKHTAfter.do?isCheck=false'
                },
                rowDblClick: function (event, ui) {
                    var rowData = ui.rowData;
                    edit(rowData);
                },
                columns: columns,
                toolbar: {
                    items: [
                        { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                        { type: 'button', label: '添加', listeners: [{ click: addChange }], icon: 'save' },
                        { type: 'button', label: '删除', listeners: [{ click: deleteChange }], icon: 'delete' },
                        { type: 'button', label: '提交', listeners: [{ click: submit }], icon: 'check' },
                        { type: 'button', label: '撤回', listeners: [{ click: unSubmit }], icon: 'back' },
                        { type: 'button', label: '确认', listeners: [{ click: confirm }], icon: 'check' },
                    ]
                }
            };
            grid = $("#mainGrid").etGrid(paramObj);
            
        };
        //添加
        function addChange(){
        	parent.$.etDialog.open({
          	 	 url: 'hrp/pac/fkht/change/pactChangeFKHTAfterAddPage.do?isCheck=false',
                   width: $(window).width(),
                   height: $(window).height(),
                   title: '添加',
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
        // 删除
        function deleteChange(){
        	var data = grid.selectGet();
            var ParamVo = [];
    		var str = '';
            if (data.length == 0) {
                $.etDialog.error('请选择行');
            } else {
            	$(data).each(function () {
	                 var rowdata = this.rowData;
	                 if(rowdata.state != 0){
	     				str += rowdata.change_code +",";
	     			 }else{
	     				$(data).each(function (){					
	    					ParamVo.push(
	    							rowdata.group_id   +"@"+ 
	    							rowdata.hos_id   +"@"+ 
	    							rowdata.copy_code   +"@"+ 
	    							rowdata.pact_code  +"@"+ 
	    							rowdata.change_code     
	    					) 
	    				});
	     			 }
               });
               if(str != '' ){
             		 $.etDialog.error("【"+str.substr(0, str.length - 1)+"】不是新建状态不能删除！");
             	 	return false;
              }else{
            	  ajaxPostData({
                      url: 'deletePactMainFKHTAfter.do?isCheck=false',
                      data: {
                    	  ParamVo: ParamVo.toString()
                      },
                      success: function () {
                    	  query();
                      }
                  })
              }
            }
            
        }
        //提交
        function submit (){
        	var data = grid.selectGet();
            var ids = "";
    		var str = '';
            if (data.length == 0) {
                $.etDialog.error('请选择行');
            } else {
                var param = [];
                $(data).each(function () {
	                 var rowdata = this.rowData;
	                 if(rowdata.state != 0){
	     				str += rowdata.change_code +",";
	     			 }
	     			 ids += rowdata.change_code +'@1'+ ",";
                });
                if(str != '' ){
              		 $.etDialog.error("【"+str.substr(0, str.length - 1)+"】不是新建状态不能提交！");
              	 	return false;
               }
               ajaxPostData({
                   url: 'submitPactChangeFKHTAfter.do?isCheck=false',
                   data: {
                	   ParamVo: ids.substr(0, ids.length-1)
                   },
                   success:function(){
                	   query();
                   }
               })
            }
       }
        //撤回
       function unSubmit (){
            var data = grid.selectGet();
            var ids = "";
    		var str = '';
            if (data.length == 0) {
                $.etDialog.error('请选择行');
            } else {
                var param = [];
                $(data).each(function () {
	                 var rowdata = this.rowData;
	                 if(rowdata.state != 1){
	     				str += rowdata.change_code +",";
	     			 }
	     			 ids += rowdata.change_code +'@0'+ ",";
                });
                if(str != '' ){
              		 $.etDialog.error("【"+str.substr(0, str.length - 1)+"】不是提交状态不能撤回！");
              	 	return false;
               }
               ajaxPostData({
                   url: 'unSubmitPactChangeFKHTAfter.do?isCheck=false',
                   data: {
                	   ParamVo: ids.substr(0, ids.length-1)
                   },
                   success:function(){
                	   query();
                   }
               })
            }
       }
     	//确认
		function confirm (){
			var data = grid.selectGet();
			var ids = "";
			var str = '';
		    if (data.length == 0) {
		        $.etDialog.error('请选择行');
		    } else {
		        var param = [];
		        $(data).each(function () {
		           var rowdata = this.rowData;
		           if(rowdata.state != 1){
					str += rowdata.change_code +",";
				 }
				 ids += rowdata.pact_code +'@'+rowdata.change_code +'@2'+ ",";
		        });
		        if(str != '' ){
		      		 $.etDialog.error("【"+str.substr(0, str.length - 1)+"】不是提交状态不能确认！");
		      	 	return false;
		       }
		       ajaxPostData({
		           url: 'confirmPactChangeFKHTAfter.do?isCheck=false',
		           data: {
		        	   ParamVo: ids.substr(0, ids.length-1)
		           },
		           success:function(){
		        	   query();
		           }
		       })
		    }
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
        
    	//关联卡片 页面跳转
        function openAssCard(obj){
        	var vo = obj.split("|");
       	 	parent.$.etDialog.open({
       	 	 url: 'hrp/pac/fkht/pactExecAnaly/pactAssetPurchaseDetailMainPage.do?isCheck=false&pact_code='+vo[3],
                width: $(window).width(),
                height: $(window).height(),
                title: '关联卡片信息',
                frameName: window.name
            });
        }
    	
    	//原值变动页面跳转
        function openAssValue(obj){
        	var vo = obj.split("|");
       	 	parent.$.etDialog.open({
       	 	 url: 'hrp/pac/fkht/change/pactMainFKHTEdit.do?isCheck=false&pact_code='+vo[3],
                width: $(window).width(),
                height: $(window).height(),
                title: '原值变动信息',
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
         <tr>
            <td class="label" style="width: 100px;">卡片编号：</td>
            <td class="ipt"><input id="ASS_CARD_NO" type="text" /> </td>
            <td class="label" style="width: 100px;">原值变动单：</td>
            <td class="ipt"><input id="value_c_code" type="text" /> </td>
            <td class="label" style="width: 100px;">状态：</td>
            <td class="ipt"><select id="state" style="width: 180px" ></select> </td>
        </tr>
    </table>
    <div id="mainGrid"></div>
</body>

</html>

