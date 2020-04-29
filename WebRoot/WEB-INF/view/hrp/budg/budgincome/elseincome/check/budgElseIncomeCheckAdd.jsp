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
	    <jsp:param value="select,datepicker,dialog,grid,etValidate,tab" name="plugins" />
	</jsp:include>
    <script type="text/javascript">
	 var budg_year, check_type, bc_state;
	 var formValidate;
     var grid;
     var check_type = '01' ;
     $(function (){
        loadDict()//加载下拉框
        loadHead() ;
        formValidate = $.etValidate({
            config: {},
            items: [
                { el: $("#budg_year"), required: true },
                { el: $("#check_type"), required: true },
            ]
        });
    });  
     
   //查询
    function  query(){
    	var parms=[];
        //根据表字段进行添加查询条件
    	parms.push({name:'budg_year',value: budg_year.getValue()}); 
        
  		parms.push({name: 'check_type', value: check_type.getValue() });
  		
    	//加载查询条件
    	grid.loadData(parms,''); 
    	
     }
   
     function loadHead(){
			grid = $("#maingrid").etGrid({
	        	 columns: [ 
					{ display: '科目编码', name: 'subj_code', align: 'left',width:100,frozen:true,
						 
					},
					{ display: '科目名称', name: 'subj_name', align: 'left',width:100,frozen:true,
						 
						 },
				    { display: '本年合计', name: 'year_sum', align: 'right',width:120,frozen:true,
							 render:function(ui){
								 
								 return formatNumber(ui.rowData.year_sum,2,1);
							 }
						 			 
						 },
                 	{ display: '1月', name: 'month1', align: 'right',width:120,
							 render:function(ui){
								 
                 				return formatNumber(ui.rowData.month1,2,1);
							 }
					 	},
					{ display: '2月', name: 'month2', align: 'right',width:120,
								 render:function(ui){
									return formatNumber(ui.rowData.month2,2,1);
								 }
						 },
					{ display: '3月', name: 'month3', align: 'right',width:120,
							 render:function(ui){
								 
								return formatNumber(ui.rowData.month3,2,1);
							 }
						 },
					{ display: '4月', name: 'month4', align: 'right',width:120,
							 render:function(ui){
								 
								return formatNumber(ui.rowData.month4,2,1);
							 }
						},
				 	{ display: '5月', name: 'month5', align: 'right',width:120,
							 render:function(ui){
								 
								return formatNumber(ui.rowData.month5,2,1);
							 }
						 },
					{ display: '6月', name: 'month6', align: 'right',width:120,
							 render:function(ui){
						 
								return formatNumber(ui.rowData.month6,2,1);
							 }
						},
					{ display: '7月', name: 'month7', align: 'right',width:120,
							 render:function(ui){
								 
								return formatNumber(ui.rowData.month7,2,1);
							 }
						},
					{ display: '8月', name: 'month8', align: 'right',width:120,
							 render:function(ui){
								 
								return formatNumber(ui.rowData.month8,2,1);
							 }
						},
				 	{ display: '9月', name: 'month9', align: 'right',width:120,
							 render:function(ui){
								 
				 				return formatNumber(ui.rowData.month9,2,1);
							 }
						},
					{ display: '10月', name: 'month10', align: 'left',width:120,
							 render:function(ui){
								 
								return formatNumber(ui.rowData.month10,2,1);
							 }
						},
					{ display: '11月', name: 'month11', align: 'right',width:120,
							 render:function(ui){
								 
								return formatNumber(ui.rowData.month11,2,1);
							 }
				 			
				 		},
					{ display: '12月', name: 'month12', align: 'right',width:120,
							 render:function(ui){
								 
								return formatNumber(ui.rowData.month12,2,1);
							 }
						}
                  ],  	
                  dataModel:{
                    	 method:'POST',
                    	 location:'remote',
                    	 url:'queryBudgElseIncome.do?isCheck=false',
                    	 recIndx: 'subj_code' //必填 且该列不能为空  
                  },
                  usePager:true,width: '100%', height: '100%',checkbox: true,
                  freezeCols: 4
              });
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
            url: "addBudgElseIncomeCheck.do",
            data: formPara,
            success: function(responseData) {
                var parentFrameName = parent.$.etDialog.parentFrameName;
                var parentWindow = parent.window[parentFrameName];
                parentWindow.query();

                close_page();
            },
            delayCallback: true
        });
    }
     
   //关闭当前页面
 	function close_page(){
 		var curIndex = parent.$.etDialog.getFrameIndex(window.name);
        parent.$.etDialog.close(curIndex);
 	}
    function loadDict() {
        bc_state = $("#bc_state").etSelect({
            url: "../../../queryBudgBcState.do?isCheck=false"
        });
        
        /**
         * 这里声明了一个wait等待方法，并放到when中执行
         * 里面Deferred是 jq的延迟对象 。resolve表示成功。并可执行done
         */
        var wait_check_type = function(dtd) {
            var dtd = $.Deferred();

            check_type = $("#check_type").etSelect({
                url: "../../../queryBudgCheckType.do?isCheck=false",
                onInit: function () {
                    dtd.resolve();
                },
            });
	    　　　　return dtd.promise();
	    　　};

        $.when(wait_check_type())
        .done(function() {
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
                    })
                }
            })
        })

        $("#save").on("click", save);
        $("#close").on("click", close_page);
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
            </tr>
            <tr>
                <td class="label">备注：</td>
                <td class="ipt">
                    <input id="remark" type="text" />
                </td>

                <td class="label">状态：</td>
                <td class="ipt">
                    <select id="bc_state" style="width: 180px;" disabled></select>
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

  
   	
