<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html style="overflow:hidden;">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <jsp:include page="${path}/static_resource.jsp">
  	   <jsp:param value="select,datepicker,dialog,grid,etValidate" name="plugins" />
  </jsp:include>
<script type="text/javascript">
	var budg_year, check_type, bc_state;
	var grid;
	var formValidate;
    var check_type = '01' ;
    $(function (){
        loadDict()//加载下拉框
        loadHead() ;

        formValidate = formValidate = $.etValidate({
            items: [
                { el: $("#budg_year"), required: true },
                { el: $("#check_type"), required: true }
            ]
        });
    });  
     
    //查询
    function  query(){
    	var parms = [
 			{ name: 'budg_year', value: budg_year.getValue() },
 			{ name: 'check_type', value: check_type.getValue() }
        ];
    	//加载查询条件
    	grid.loadData(parms,'queryBudgMatPur.do?isCheck=false'); 
    }
   
    function loadHead(){
    	 var columns = [
			{ display: '物资分类编码', name: 'mat_type_code', align: 'left',width:100,frozen:true,},
			{ display: '物资分类名称', name: 'mat_type_name', align: 'left',width:100,frozen:true,},
		    { display: '本年合计', name: 'year_sum', align: 'right',width:120,frozen:true,
				 render:function(ui){
					 return formatNumber(ui.rowData.year_sum,2,1);
				 }
			},
		 ];	
    	 
    	 for(var i = 1; i < 13; i++) {
			columns.push({
				display: i + '月',
                name: 'month' + i,
                align: 'right',
                width: 120,
                render:function(ui) {
                    return formatNumber(ui.cellData, 2, 1);
                }
			})
      	 }
      	 var paramObj = {
			height: '100%',
            checkbox: true,
            freezeCols: 4,
            columns: columns
      	 };
         grid = $("#maingrid").etGrid(paramObj);
	}
    
    function  save(){
        var formPara={
    		budg_year: budg_year.getValue(),
            check_code: $("#check_code").val(),
            check_type: check_type.getValue(),
            remark: $("#remark").val(),
            bc_state: bc_state.getValue()
        };

        ajaxPostData({
        	url: "addBudgMatPurCheck.do",
        	data: formPara,
        	success: function(responseData) {
                parent_query();
                this_close();
            }
        });
    }
     
    function parent_query() {
 		var parentFrameName = parent.$.etDialog.parentFrameName;
        var parentWindow = parent.window[parentFrameName];
        parentWindow.query();
 	}
    //关闭当前页面
 	function this_close(){
 		var curIndex = parent.$.etDialog.getFrameIndex(window.name);
        parent.$.etDialog.close(curIndex);
 	}
   
    function saveBudgWorkCheck(){
    	if (formValidate.test()) {
            save();
        }
   	}
    function loadDict(){
    	 ajaxPostData({
         	url: "../../../queryBudgYear.do?isCheck=false",
         	success: function (data) {
         		budg_year = $("#budg_year").etDatepicker({
 		            view: "years",
 		            minView: "years",
 		            dateFormat: "yyyy",
 		            minDate: data[data.length - 1].text,
 		            maxDate: data[0].text,
 		            defaultDate: data[0].text,
 		            onChanged: query
 		        });
         	}
         })

         check_type = $("#check_type").etSelect({
             url: "../../../queryBudgCheckType.do?isCheck=false",
             defaultValue: "none",
             onChange: query
         });
 		
 		bc_state = $("#bc_state").etSelect({
             url: "../../../queryBudgBcState.do?isCheck=false"
         });
 		
 		$("#save").on('click', save);
 		$("#close").on('click', this_close);
	} 
  </script>
</head>

<body>
  <div class="mian">
        <table class="table-layout">
            <tr>
                <td class="label">预算年度：</td>
                <td class="ipt">
                    <input id="budg_year" type="text" />
                </td>

                <td class="label">审批单号：</td>
                <td class="ipt">
                	<input id="check_code" type="text" disabled value="系统生成" />
                </td>

                <td class="label">审批类型：</td>
                <td class="ipt">
                    <select id="check_type" style="width: 180px;"></select>
                </td>

                <td class="label">状态：</td>
                <td class="ipt">
                    <select id="bc_state" style="width: 180px;" disabled></select>
                </td>
            </tr>
            <tr>
            	<td class="label">备注：</td>
                <td class="ipt">
                	<input id="remark" type="text" />
                </td>
            </tr>
        </table>
    </div>

	<div class="button-group">
		<button id="save">保存</button>
		<button id="close">关闭</button>
	</div>

    <div id="maingrid"></div>
</body>
</html>

  
   	
