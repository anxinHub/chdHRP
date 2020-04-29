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
        	
        	$("#apply_code").val('${apply_code}');
        	$("#remark").val('${remark}');
        	$("#apply_amount").val(parseFloat('${apply_amount}').toFixed(2));
        	$("#phone").val('${phone}');
        	
        	
        	apply_dept = $("#apply_dept").etSelect({	
                url: '../../budgpayitem/expenditureItem/queryDutyDept.do?isCheck=false',
                defaultValue: '${dept_id}'
            });

        	apply_name = $("#apply_name").etSelect({
        		options: [
        			    { id: '${emp_id}', text: '${emp_name}' }
        			  ],
        	    addPrecedence : true
            });
        	
        	proj_dict = $("#proj_dict").etSelect({
        		url: '../../../../acc/accleder/queryProjDictDict.do?isCheck=false',
        		valueField: 'proj_code',
        		labelField: 'proj_name',
                defaultValue: '${proj_id}'
            });
        	
        	YS_dept = $("#YS_dept").etSelect({
                url: '../../budgpayitem/expenditureItem/queryDutyDept.do?isCheck=false',
                defaultValue: '${duty_dept_id}'
            });
        	
        	apply_date = $("#apply_date").etDatepicker({
        		defaultDate: '${apply_date}',
            });


        	$('#maker').html('制单人：${maker}');
        	$('#make_date').html('制单日期：${make_date}');
        	$('#YS_leader').html('预算归口负责人：  ${checker}');
        	$('#ToExamine').html('审核日期：${check_date}');
        	var state = ${state_t};
       		if(state == '01'){
       			state = '新建';
       		}else if(state == '02'){
       			state = '已提交';
       		}else if(state == '03'){
       			state = '已审核';
       		}
        	$('#state').html('状态：'+ state );
        	

        	
        };
        var query = function () {
            params = [
            	/*{ name: 'apply_code', value: $("#apply_code").val()} ,
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
                    
                    sumMoney = parseFloat(rowdata.apply_amount_det) + parseFloat(sumMoney);
                    
                    ParamVo.push(
                  		  rowdata.source_id   +"@"+ 
                  		  rowdata.payment_item_id   +"@"+ 
                  		  rowdata.apply_amount_det   +"@"+ 
                  		  rowdata.remark_det
                    );
                })
                
                $("#apply_amount").val(sumMoney.toFixed(2)) ;
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
                success: function () {
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
            $(selectData).each(function () {
                var rowdata = this.rowData;
                ParamVo.push(
                	$("#apply_code").val() +'@'+
                	rowdata.source_id +'@'+
                	rowdata.payment_item_id
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

 	    var ToExamine = function(){
            var ParamVo = [];
            

            ParamVo.push($('#apply_code').val());

            ajaxPostData({
                 url: 'updateToExamineMoneyApplyState.do?isCheck=false',
                 data: {
                     paramVo:  ParamVo.toString()
                 },
                 success: function () {
                	 $('#state').html('状态：已审核');
                 }
             })
 	    }
 	    
 	    
 	    var back = function(){
            var ParamVo = [];
            

          	ParamVo.push($('#apply_code').val());

            ajaxPostData({
                 url: 'updateToExamineMoneyApplyStateRevoke.do?isCheck=false',
                 data: {
                     paramVo:  ParamVo.toString()
                 },
                 success: function () {
                	 $('#state').html('状态：已提交');
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
                  }
                  
                },
                { display: '说明', name: 'remark_det', width: 240},
                { display: '实际支出金额', name: 'real_money', width: 120,editable: false },
                { display: '预算额度', name: 'budg_money', width: 120,editable: false},
                { display: '已申请额度', name: 'already_apply', width: 120,editable: false},
                { display: '已执行额度', name: 'already_execute', width: 120,editable: false}
            ];
            var paramObj = {
            		isChecked : true,
                height: '80%',
                inWindowHeight: true,
                //checkbox: true,
                //editable: true,
                usePager:false,
                dataModel: {//queryMoneyApplyDet  queryToExamineMoneyApplyDet
                     url: 'queryMoneyApplyDet.do?isCheck=false&apply_code=${apply_code}'
                },
                columns: columns,
                toolbar: {
                    items: [
                        
                        { type: 'button', label: '审核', listeners: [{ click: ToExamine }], icon: '' },
                        { type: 'button', label: '消审', listeners: [{ click: back }], icon: '' },
                        { type: 'button', label: '关闭', listeners: [{ click: close }], icon: 'close' }
                    ]
                }
            };
            grid = $("#mainGrid").etGrid(paramObj);
			
        };

        $(function () {
            initFrom();
            initGrid();
        	var st = ${state_t};
       		if(st == '01'){
       			//st = '新建';
       		}else if(st == '02'){
       			//st = '已提交';
              	 /* grid.setDisabledTB('add');
            	 grid.setDisabledTB("remove");
            	 grid.setDisabledTB("save");
            	 grid.setDisabledTB("commit"); */
				 
            	 $("#apply_date").attr("disabled","disabled");
            	 apply_dept.disabled();
            	 apply_name.disabled();
            	 proj_dict.disabled();
            	 YS_dept.disabled();
            	 $("#remark").attr("disabled","disabled");
            	 $("#phone").attr("disabled","disabled");
            	 
       		}else if(st == '03'){
       			//st = '已审核';
              	 /* grid.setDisabledTB('add');
            	 grid.setDisabledTB("remove");
            	 grid.setDisabledTB("save");
            	 grid.setDisabledTB("commit");
            	 grid.setDisabledTB("brack"); */
            	 
            	 $("#apply_date").attr("disabled","disabled");
            	 apply_dept.disabled();
            	 apply_name.disabled();
            	 proj_dict.disabled();
            	 YS_dept.disabled();
            	 $("#remark").attr("disabled","disabled");
            	 $("#phone").attr("disabled","disabled");
       		}
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
                <select id="apply_name" style="width:180px;" disabled="disabled" /></select>
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
                <input id="remark" type="text" disabled="disabled" style="width:780px;" />
            </td>
        </tr>
        
        <tr>
     	    <td class="label no-empty" style="white-space: nowrap;">申请金额 ：</td>
            <td class="ipt" >
                <input id="apply_amount" type="text" style="width:180px;"  disabled="disabled" value="0.00"/>
            </td>
            <td class="label">联系电话：</td>
            <td class="ipt">
                <input id="phone" type="text" disabled="disabled" style="width:180px;" > 
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