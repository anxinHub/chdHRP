<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
        <jsp:param value="select,grid,dialog,datepicker" name="plugins" />
    </jsp:include>
    <style type="text/css">
    .table td{
    	white-space: nowrap;
    }
    
    </style>
    <script>
        var unit_info;
        var grid;
        var start_date;
        var end_date;
        var apply_dept;
        var money_dept;
        var apply_name;
        var proj_dict;
        var mode;
        var initFrom = function () {
        	apply_dept = $("#apply_dept").etSelect({
                url: '../../budgpayitem/expenditureItem/queryDutyDept.do?isCheck=false',
                defaultValue: 'none'
            });
        	money_dept = $("#money_dept").etSelect({
                url: '../../budgpayitem/expenditureItem/queryDutyDept.do?isCheck=false',
                defaultValue: 'none'
            });
        	apply_name = $("#apply_name").etSelect({
                url: '../../../../acc/accleder/queryEmp.do?isCheck=false',
        		valueField: 'emp_code',
        		labelField: 'emp_name',
                defaultValue: 'none'
            });
        	proj_dict = $("#proj_dict").etSelect({
        		url: '../../../../acc/accleder/queryProjDictDict.do?isCheck=false',
        		valueField: 'proj_code',
        		labelField: 'proj_name',
                defaultValue: 'none'
            });
        	mode = $("#mode").etSelect({
        		options: [
        			    { id: '01', text: '新建' },
        			    { id: '02', text: '已提交' },
        			    { id: '03', text: '已审核' }
        			  ],
        		defaultValue: "none"
            });
        	
        	start_date = $("#start_date").etDatepicker({
        		defaultDate: 'yyyy-mm-fd',
            });
            end_date = $("#end_date").etDatepicker({
            	defaultDate: true,
            });

        };
        var query = function () {
            params = [
            	 { name: 'start_date', value: start_date.getValue() },
                 { name: 'end_date', value: end_date.getValue() },
                 { name: 'apply_dept', value: apply_dept.getValue() },
                 { name: 'apply_name', value: apply_name.getValue() },
                 { name: 'proj_dict', value: proj_dict.getValue() },
                 { name: 'money_dept', value: money_dept.getValue() },
                 { name: 'mode', value: mode.getValue() }
             
            ];
            //console.info(params);
            grid.loadData(params);
        };

        
        
        var ToExamine = function () {
           var ParamVo = [];

           var data = grid.selectGet();
           if (data.length == 0) {
               $.etDialog.error('请选择行');
               return;
           } else {
                $(data).each(function () {
                    var rowdata = this.rowData;
                    ParamVo.push(rowdata.apply_code);
                }) 
            }
            ajaxPostData({
                url: 'updateToExamineMoneyApplyState.do?isCheck=false',
                data: {
                    paramVo:  ParamVo.toString()
                },
                success: function () {
                	query();
                }
            })
        };
        
        var openUpdate = function(apply_code){
        	
  		  // 普通打开的弹窗
  		  parent.$.etDialog.open({
  		    url: 'hrp/budg/base/budgMoneyApply/Apply/ToExamineMoneyApplyDetail.do?apply_code='+apply_code+'&isCheck=false',
  		    isMax : true,
  		    width: 600,
  		    height: 600,
  		    title: '用款申请审核明细'
  		    /*,
  		    //btn: ['确定', '取消'],
   		    btn1: function (index, el) {
  		      var iframeWindow = window[el.find('iframe').get(0).name];
  		      // 这里的iframeWindow表示子页面的window对象，
  		      // 然后执行子页面的方法，如保存方法，或拿取全局对象
  		      // 然后需要手动关闭
  		      iframeWindow.save()
  		      $.etDialog.close(index)
  		    } */
  		  })
  		  
        }
        
        
        
        
 	    var back = function (){
            var ParamVo = [];

            var data = grid.selectGet();
            if (data.length == 0) {
                $.etDialog.error('请选择行');
                return;
            } else {
                 $(data).each(function () {
                     var rowdata = this.rowData;
                     ParamVo.push(rowdata.apply_code);
                 }) 
             }
             ajaxPostData({
                 url: 'updateToExamineMoneyApplyStateRevoke.do?isCheck=false',
                 data: {
                     paramVo:  ParamVo.toString()
                 },
                 success: function () {
                	 query();
                 }
             })
 	    }
 	    
 	    
 	    
 	    
        var initGrid = function () {
            var columns = [
                { display: '用款申请单号', name: 'apply_code', width: 120,
	       			  render: function (ui) {
	       				var apply_code = ui.rowData.apply_code;
	       				
                        return "<a href=\"javascript:openUpdate('"+apply_code+"')\">"+apply_code+"</a>";
	                  }
                },
                { display: '申请日期', name: 'apply_date', width: 120 },
                { display: '申请科室', name: 'dept_name', width: 120 },
                { display: '申请人', name: 'emp_name', width: 120 },
                { display: '项目名称', name: 'proj_name', width: 300},
                { display: '预算归口科室', name: 'duty_dept_name', width: 120},
                { display: '申请事由', name: 'remark', width: 120},
                { display: '申请金额', name: 'apply_amount', width: 120},
                { display: '审核人', name: 'checker', width: 120},
                { display: '审核日期', name: 'check_date', width: 120},
                { display: '状态', name: 'state_t', width: 120,
	               	render: function (ui) {
	               		var state_t = ui.rowData.state_t;
	               		if(state_t == '01'){
	               			return '新建';
	               		}else if(state_t == '02'){
	               			return '已提交';
	               		}else if(state_t == '03'){
	               			return '已审核';
	               		}

                  }}
            ];
            var paramObj = {
                height: '100%',
                inWindowHeight: true,
                checkbox: true,
                editable: false,
                dataModel: {
                     url: 'queryToExamineMoneyApply.do?isCheck=false',
                     postData:{
                    	 start_date : start_date.getValue(),
                    	 end_date : end_date.getValue(),
                    	 apply_name : apply_name.getValue()
                    	 
                     }
                },
                columns: columns,
                toolbar: {
                    items: [
                        { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                        { type: 'button', label: '审核', listeners: [{ click: ToExamine }], icon: '' },
                        { type: 'button', label: '消审', listeners: [{ click: back }], icon: '' }
                    ]
                }
            };
            grid = $("#mainGrid").etGrid(paramObj);

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
     	    <td class="label">申请日期：</td>
            <td class="ipt" style="white-space: nowrap;">
                <input id="start_date" type="text" /> 至   <input id="end_date" type="text" />
            </td>
            <td class="label" align="left" >申请科室：</td>
            <td class="ipt">
                <select id="apply_dept" style="width:180px;"></select>
            </td>
            <td class="label">申请人：</td>
            <td class="ipt">
                <select id="apply_name" style="width:180px;" /></select>
            </td>
        </tr>
        <tr>
        	<td class="label">项目名称：</td>
            <td class="ipt">
                <select id="proj_dict" style="width:380px;" /></select>
            </td>
     	    <td class="label" style="white-space: nowrap;">预算归口科室：</td>
            <td class="ipt" >
                <select id="money_dept"  style="width:180px;" /></select>
            </td>
            <td class="label">状态：</td>
            <td class="ipt">
                <select id="mode" style="width:180px;"></select>
            </td>

        </tr>
    </table>
    <div id="mainGrid"></div>
</body>

</html>