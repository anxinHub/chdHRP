<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="hr,tree,grid,select,dialog,datepicker" name="plugins" />
	</jsp:include>
    <script>
        var grid;
        var startpicker,endpicker,sup_no,dept_id,state,pact_code,is_well;
        var query = function () {
            params = [
                      { name: 'start_date', value: startpicker.getValue() },
	                  { name: 'end_date', value: endpicker.getValue() },
	                  { name: 'pact_code', value: pact_code.getValue() },
		              { name: 'sup_id', value: sup_no.getValue() },
		              { name: 'accept_no', value: $("#accept_no").val() },
		              { name: 'dept_id', value: dept_id.getValue() },
		              { name: 'state', value: state.getValue() },
		              { name: 'ass_name', value: $("#ass_name").val() },
		              { name: 'is_well', value: is_well.getValue() },
               
            ];
            grid.loadData(params);
        };
        
        var initSelect=  function(){
        	pact_code = $("#pact_code").etSelect({url: '../../basicset/select/queryPactFKHTSelect.do?isCheck=false',defaultValue: "${pact_code}"});
          	sup_no = $("#sup_no").etSelect({url: '../../basicset/select/queryHosSupDictSelect.do?isCheck=false',defaultValue: "${sup_id}"});
          	dept_id = $("#dept_id").etSelect({url: '../../basicset/select/queryDeptSelect.do?isCheck=false',defaultValue: "none"});
          	state = $("#state").etSelect({
		          		defaultValue: "none",
		          		options: [{ id: 0, text: '未执行' },
		          		          { id: 1, text: '通过' },
		          		          { id: 2, text: '未通过' }]
          		  	});
          	is_well = $("#state").etSelect({
          		defaultValue: "none",
          		options: [{ id: 0, text: '新建' },
          		          { id: 1, text: '审核' },
          		          { id: 2, text: '确认' }]
  		  	});
        }
        
        var initGrid = function () {
            var columns = [
            	 { display: '验收单号', name: 'accept_no', align: 'center',width: '10%',
            		 render:function(ui) {
	               		var data = ui.rowData;
	               		return "<a href=javascript:openAssAccept('"+data.group_id+"|"+data.hos_id
	               				+"|"+data.copy_code+"|"+data.accept_id+"')>"+data.accept_no+"</a>";
                 	}
            	 },
                 { display: '单据状态', name: 'state_name', align: 'left' ,width: '5%',
         			render:function(ui){
        				var value = ui.rowData.state
        				if(value==0){
        					return "新建";
        				}else if(value==1){
        					return "审核";
        				}else if(value==2){
        					return "确认";
        				}
        			}	 
            	 },
                 { display: '验收日期', name: 'accpet_date', align: 'center', width: '8%'},
                 { display: '验收科室', name: 'dept_name', align: 'left', width: '10%'},
                 { display: '摘要', name: 'accept_desc', align: 'left', width: '11%'},
                 { display: '资产编码', name: 'ass_code', align: 'left', width: '10%'},
                 { display: '资产名称', name: 'ass_name', align: 'left', width: '12%'},
                 { display: '规格', name: 'ass_spec', align: 'left', width: '6%'},
                 { display: '型号', name: 'ass_model', align: 'left', width: '6%'},
                 { display: '品牌', name: 'ass_brand', align: 'left', width: '12%'},
                 { display: '生产厂商', name: 'fac_name', align: 'left', width: '6%'},
                 { display: '验收数量', name: 'accpet_amount', align: 'left', width: '8%'},
                 { display: '验收结果', name: 'is_well', align: 'left', width: '6%',
                	 render:function(ui){
                		 var value = ui.rowData.is_well;
                		 if(value==0){
                			 return "未执行"
                		 }else if(value==1){
                			 return "通过"
                		 }else if(value==2){
                			 return "未通过"
                		 }
                	 }
                 },
                 { display: '验收说明', name: 'note', align: 'left', width: '10%'},
            ];
            var paramObj = {
            	editable: false,
            	height: '97%',
            	width:'100%',
                dataModel: {
                    url: 'queryPactAssPurchaseFKHTMainAccept.do?isCheck=false'
                },
                columns: columns,
                toolbar: {
                    items: [
                        { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' }
                    ]
                }
            };
            grid = $("#mainGrid").etGrid(paramObj);
        };
        
		//验收信息查看
    	function openAssAccept(obj) {
    		var vo = obj.split("|");
    		if("null"==vo[3] || "undefined"==vo[3]){
    			return false;
    			
    		}
    		var parm = "group_id=" + vo[0] + "&hos_id=" + vo[1] 
    			+ "&copy_code=" + vo[2] + "&accept_id=" + vo[3];
    		parent.$.etDialog.open({
                url: 'hrp/ass/assacceptmain/assAcceptMainUpdatePage.do?isCheck=false&'+parm,
                width: $(window).width(),
                height: $(window).height(),
                title: '资产验收',
                modal: true,
                frameNameObj :window.name
            }); 
    	}
        $(function () {
            initfrom();
            initGrid();
            initSelect();
        })
       
        //日期
        var initfrom = function(){
        	startpicker = $("#start_date").etDatepicker({
    			defaultDate: "yyyy-fm-fd",
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
            <td class="label" style="width: 100px;">验收日期：</td>
            <td class="ipt" style="width: 220px;">
                <input id="start_date" type="text" style="width: 100px"/>至 <input id="end_date" type="text" style="width: 100px"/>
            </td>
            <td class="label" style="width: 100px;">合同名称：</td>
            <td class="ipt"><select id="pact_code"  disabled="disabled" style="width: 180px"></select> </td>
            <td class="label" style="width: 100px;">供应商：</td>
            <td class="ipt"> <select id="sup_no" disabled="disabled" style="width: 180px"></select> </td>
        </tr>
        <tr>
        	<td class="label" style="width: 100px;">验收单号：</td>
            <td class="ipt"><input id="accept_no" type="text" /> </td>
             <td class="label" style="width: 100px;">验收科室：</td>
            <td class="ipt"><input id="dept_id" type="text" style="width: 180px"/> </td>
        	<td class="label" style="width: 100px;">单据状态：</td>
            <td class="ipt"><input id="state" type="text" style="width: 180px"/></td>
        </tr>
        <tr>
        	<td class="label" style="width: 100px;">资产名称：</td>
            <td class="ipt"><input id="ass_name" type="text" /> </td>
            <td class="label" style="width: 100px;">验收结果：</td>
            <td class="ipt"><input id="is_well" type="text" style="width: 180px"/></td>
        </tr>
    </table>
    <div id="mainGrid"></div>
</body>

</html>