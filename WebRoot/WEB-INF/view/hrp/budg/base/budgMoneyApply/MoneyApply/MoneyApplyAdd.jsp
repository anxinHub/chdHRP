<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
        <jsp:param value="select,grid,dialog,datepicker,validate" name="plugins" />
    </jsp:include>
    <style type="text/css">
    .table td{
    	white-space: nowrap;
    }
    
    </style>
    <script>
        var unit_info;
        var grid;
        var apply_date;
        var apply_dept;
        var proj_dict;
        var apply_name;
        var YS_dept;
        var initFrom = function () {
        	apply_dept = $("#apply_dept").etSelect({
                url: '../../budgpayitem/expenditureItem/queryDutyDept.do?isCheck=false',
                defaultValue: '${dept_id}'
            });

        	apply_name = $("#apply_name").etSelect({
        		
        		url: '../../../queryEmpDict.do?isCheck=false',
                defaultValue: '${emp_id}'
/*         		options: [
        			    { id: '${EmpCode}', text: '${EmpName}' }
        			  ],
        	    addPrecedence : true */
            });
        	
        	proj_dict = $("#proj_dict").etSelect({
        		url: '../../../../acc/accleder/queryProjDictDict.do?isCheck=false',
        		valueField: 'proj_code',
        		labelField: 'proj_name',
                defaultValue: 'none'
            });
        	
        	YS_dept = $("#YS_dept").etSelect({
                url: '../../budgpayitem/expenditureItem/queryDutyDept.do?isCheck=false',
                defaultValue: 'none'
            });
        	
        	apply_date = $("#apply_date").etDatepicker({
        		defaultDate: true,
            });
			
        	
        	$('#maker').html('制单人：');
        	$('#make_date').html('制单日期：');
        	$('#YS_leader').html('预算归口负责人：  ');
        	$('#ToExamine').html('审核日期：${check_date}');
        	/* var state = ${state_t};
       		if(state == '01'){
       			state = '新建';
       		}else if(state == '02'){
       			state = '已提交';
       		}else if(state == '03'){
       			state = '已审核';
       		}*/
        	$('#state').html('状态：'); 
        	
        	

        };
        var query = function () {
            params = [
            	 /* { name: 'hos_id', value: hos_name.getValue() },
                 { name: 'attend_code', value: $("#attend_code").val() },
                 { name: 'attend_name', value: $("#attend_name").val() } */
             
            ];
            grid.loadData(params);
        };
        var add = function () {
        	 grid.addRow();
        	 
        };
       
        

        //验证明细每一行是否重复
        function validateGrid() {
        	var data = grid.selectGet();
        	var msg="";
     		var rowm = "";
     		//判断grid 中的数据是否重复或者为空
     		var targetMap = {};
     		
     		$.each(data,function(i, v){
     			rowm = "";
  				if (v.rowData.source_name == "" || v.rowData.source_name == null || v.rowData.source_name == 'undefined') {
  					rowm+="[资金来源]、";
  				}  
  				if (v.rowData.payment_item_name == "" || v.rowData.payment_item_name == null || v.rowData.payment_item_name == 'undefined') {
  					rowm+="[支出项目]、";
  				}
  				//console.info(v.rowData.apply_amount_det);
  				if (v.rowData.apply_amount_det == "" || v.rowData.apply_amount_det == null || v.rowData.apply_amount_det == 'undefined' || parseFloat(v.rowData.apply_amount_det) == 0) {
  					rowm+="[申请金额]、";
  				}  
  				if(rowm != ""){
  					rowm = "第"+(i+1)+"行" + rowm.substring(0, rowm.length-1) + "不能为空" + "\n\r";
  				}
  				msg += rowm;
  				var key=(v.rowData.source_name+v.rowData.payment_item_name);
  				var value="第"+(i+1)+"行";
  				if(targetMap[key]== null || targetMap[key] == 'undefined' || targetMap[key] == ""){
  					targetMap[key] = value;
  				}else{
  					msg += targetMap[key]+"与"+value+"数据重复!!" + "\n\r";
  				}
     			
     		});
     		if(msg != ""){
     			$.etDialog.warn(msg);  
    			return false;  
     		}else{
     			return true;  
     		} 	
     	}
        
        
        var save = function () {
        	var ParamVo = [];
        	
            var formValidate = $.etValidate({
        		  config: {},
        		  items: [
        		    { el: $("#apply_date"), required: true },
        		    { el: $("#apply_dept"), required: true },
        		    { el: $("#YS_dept"), required: true },
        		    { el: $("#remark"), required: true },
        		    { el: $("#phone"), type: 'number' }
        		  ]
        	});
        	//验证必填项
        	if(!formValidate.test()){
        		return;
        	}
        	
        	if(!validateGrid()){
        		return;
        	}
        	
        	
        	var data = grid.selectGet();
         	
            if (data.length == 0) {
                  $.etDialog.error('请选择行');
                  return;
            } else {
            	var sumMoney = 0.00;
            	
                $(data).each(function () {
                    var rowdata = this.rowData;
                    
                    sumMoney = parseInt(rowdata.apply_amount_det) + parseInt(sumMoney);
                    
                    ParamVo.push(
                  		  rowdata.source_id   +"@"+ 
                  		  rowdata.payment_item_id   +"@"+ 
                  		  rowdata.apply_amount_det   +"@"+ 
                  		  rowdata.remark_det
                    );
                })
                
                $("#apply_amount").val(sumMoney) ;
            }

           ajaxPostData({
                url: 'addMoneyApply.do?isCheck=false',
                data: {
                    paramVo:  ParamVo.toString(),
                    top : 
                    apply_date.getValue() + '@' +
            		apply_name.getValue() + '@' +
            		apply_dept.getValue() + '@' +
            		(proj_dict.getValue()==''?'-1':proj_dict.getValue()) + '@' +
            		YS_dept.getValue() + '@' +
            		$("#remark").val() + '@' +
            		$("#apply_amount").val() + '@' +
            		($("#phone").val()==''?'-1':$("#phone").val()) + '@' +
            		($("#apply_code").val()=='自动生成'?'-1':$("#apply_code").val())
                },
                success: function (responseData) {

                	var apply_code = responseData.apply_code;
                	$('#apply_code').val(apply_code);
                	
                	var myDate = new Date();
                	
                	$('#maker').html('制单人：${EmpName}');
                	$('#make_date').html('制单日期：'+myDate.toLocaleDateString());
                	$('#state').html('状态：新建'); 
                }
            })
        };
        
        
        //计算
        var Calculation = function(){
			var data = grid.getAllData();
           	var sumMoney = 0.00;
            	
          	$(data).each(function () {
               var det = this.apply_amount_det;
               //console.info(det);
               if(det == "" || det == null || det == 'undefined'){
            	   det = 0;
               }
               //console.info(parseFloat(det));
               sumMoney = parseFloat(det) + parseFloat(sumMoney);
               
            })
            $("#apply_amount").val(sumMoney.toFixed(2)) ;
        }
        
        
        
        var remove = function () {

            var selectData = grid.selectGet();
            if (selectData.length === 0) {
                $.etDialog.error('请选择行');
                return;
            }
        	var ParamVo = [];
        	
        	if($("#apply_code").val()=='自动生成' ){
        		
        		grid.deleteRows(selectData);
        		
        		return;
        	}
        	
        	
        	
            $(selectData).each(function (i,v) {
                var rowdata = this.rowData;
                ParamVo.push(
                	($("#apply_code").val()=='自动生成'?'-1':$("#apply_code").val()) +'@'+
                	(rowdata.source_id==''?'-1':rowdata.source_id) +'@'+
                	(rowdata.payment_item_id==''?'-1':rowdata.payment_item_id)
                );
            });
            $.etDialog.confirm('确定删除?', function () {
            ajaxPostData({
                 url: 'deleteMoneyApplyDetailed.do?isCheck=false',
                data: { paramVo: ParamVo.toString() },
                success: function () {
                    grid.deleteRows(selectData);
                }
            }) });
        };
        
        
        
 	    var close = function (){
 	    	
 	 	    // 在当前子页面中关闭自身窗口
 	    	var curIndex = parent.$.etDialog.getFrameIndex(window.name);
 	    	parent.$.etDialog.close(curIndex);
 	    	
            
 	    }
 	    
 	    
 	    
 	    
 	    var commit = function(){
            var ParamVo = [];
            if ($('#apply_code').val() == '自动生成') {
                $.etDialog.error('请保存完毕后再进行提交!');
                return;
            } else {
                  ParamVo.push($('#apply_code').val());
            }
            ajaxPostData({
                 url: 'updateMoneyApply.do?isCheck=false',
                 data: {
                     paramVo:  ParamVo.toString()
                 },
                 success: function () {
                	 $('#state').html('状态：已提交'); 
                   	 grid.setDisabledTB('add');
                	 grid.setDisabledTB("remove");
                	 grid.setDisabledTB("save");
                	 grid.setDisabledTB("commit");
                 }
             })
 	    }
 	    
 	    
 	    
 	    
 	    var brack = function(){
			var ParamVo = [];
            if ($('#apply_code').val() == '自动生成') {
                $.etDialog.error('请先保存!');
                return;
            } else {
                  ParamVo.push($('#apply_code').val());
            }
            ajaxPostData({
                 url: 'updateMoneyApplyStateRevoke.do?isCheck=false',
                 data: {
                     paramVo:  ParamVo.toString()
                 },
                 success: function () {
                	 $('#state').html('状态：新建');
                	 initGrid();
                 }
             })
 	    }
 	    
 	    
        var initGrid = function () {
            var columns = [
                { display: '资金来源', name: 'source_name', width: 120,
                	editor: {
                	     type: 'select',  //编辑框为下拉框时
                	     autoFocus:false,   //  为true时 下拉框默认选择第一个
                	     disabled:false,
                	     keyField : 'source_id',
                	     url:  'queryProjDict.do?isCheck=false'

                	 }	
                },
                { display: '支出项目', name: 'payment_item_name', width: 120,
                	editor:{
                		type: 'select',  //编辑框为下拉框时
                		autoFocus:false,   //  为true时 下拉框默认选择第一个
                		disabled:false,
                		url:  '../../budgpayitem/expenditureItem/queryPaymentItem.do?isCheck=false', 
                		keyField : 'payment_item_id'
                		
                	}	
                },
                { display: '申请金额', name: 'apply_amount_det', width: 120,
                  editor: { type: 'float',
                	  change: function(event) {
                	  Calculation();
                	  
                  } },
                  render: function(ui) {
                      var value = ui.cellData;
                      return formatNumber(value, 2, 1);
                  },
                  
                },
                { display: '说明', name: 'remark_det', width: 240}/* ,
                { display: '实际支出金额', name: 'real_money', width: 120,editable: false },
                { display: '预算额度', name: 'budg_money', width: 120,editable: false},
                { display: '已申请额度', name: 'already_apply', width: 120,editable: false},
                { display: '已执行额度', name: 'already_execute', width: 120,editable: false} */
            ];
            var paramObj = {
                height: '80%',
                inWindowHeight: true,
                checkbox: true,
                editable: true,
                usePager:false,
                dataModel: {
                     url: ''
                },
                columns: columns,
                toolbar: {
                    items: [
                        
                        { type: 'button', label: '新增行',id: 'add', listeners: [{ click: add }], icon: 'add' },
                        { type: 'button', label: '删除',id: 'remove', listeners: [{ click: remove }], icon: 'delete' },
                        { type: 'button', label: '保存',id: 'save', listeners: [{ click: save }], icon: 'save' },
                        { type: 'button', label: '提交',id: 'commit', listeners: [{ click: commit }], icon: 'commit' },
                        { type: 'button', label: '撤销',id: 'brack', listeners: [{ click: brack }], icon: '' },
                        { type: 'button', label: '关闭',id: 'close', listeners: [{ click: close }], icon: 'close' }
                    ]
                }
            };
            grid = $("#mainGrid").etGrid(paramObj);
            

/*             $("#mainGrid").on('click', '.openUpdate', function () {
                var rowIndex = $(this).attr('row-index');
                var currentRowData = grid.getAllData()[rowIndex];
                openUpdate(currentRowData);
            }) */
        };


        
        $(function () {
            initFrom();
            initGrid();
        })
    </script>
</head>

<body>
     <table class="table-layout" >
        <tr >
     	    <td class="label no-empty">申请单号 ：</td>
            <td class="ipt" style="white-space: nowrap;">
                <input id="apply_code" style="width:180px;" disabled="disabled" value="自动生成">
            </td>
            <td class="label no-empty">申请日期 ：</td>
            <td class="ipt">
            	<input id="apply_date" type="text" />
            </td>
            <td class="label no-empty">申请人 ：</td>
            <td class="ipt">
                <select id="apply_name" style="width:180px;"  /></select>
            </td>
        </tr>
        <tr>
     	    <td class="label no-empty" style="white-space: nowrap;">申请科室 ：</td>
            <td class="ipt" >
                <select id="apply_dept" style="width:180px;" /></select>
            </td>
            <td class="label">项目名称：</td>
            <td class="ipt">
                <select id="proj_dict" style="width:180px;" /></select>
            </td>
            <td class="label no-empty">预算归口科室 ：</td>
            <td class="ipt">
                <select id="YS_dept" style="width:180px;"></select>
            </td>

        </tr>
        <tr>
     	    <td class="label no-empty" style="white-space: nowrap;">申请事由 ：</td>
            <td class="ipt" colspan="5">
                <input id="remark" type="text" style="width:780px;" />
            </td>
        </tr>
        
        <tr>
     	    <td class="label no-empty" style="white-space: nowrap;">申请金额 ：</td>
            <td class="ipt" >
                <input id="apply_amount" type="text" style="width:180px;"  disabled="disabled" value="0.00"/>
            </td>
            <td class="label">联系电话：</td>
            <td class="ipt">
                <input id="phone" type="text" style="width:180px;" > 
            </td>
        </tr>
    </table>
    <div id="mainGrid"></div>
    <table class="table-layout" width="100%"  >
    	<tr>
    		<td width="20%" id = 'maker'></td>
    		<td width="20%" id = 'make_date' ></td>
    		<td width="20%" id = 'YS_leader'></td>
    		<td width="20%" id = 'ToExamine'></td>
    		<td width="20%" id = 'state'></td>
    	</tr>
    </table>
</body>

</html>