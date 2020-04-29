<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title></title>
	<jsp:include page="${path}/static_resource.jsp">
	    <jsp:param value="select,datepicker,grid,tab" name="plugins" />
	</jsp:include>
	<script type="text/javascript">
	   var grid;
	   var gridDept;
	   var userUpdateStr ;
	   var budg_year = "${year}";
	   var renderFunc = {
			   sum_month : function(value){ //本年合计
					if(value){
						return formatNumber(value,2,1) ;
					}
			   },
			   month1 : function(value){ //本年合计
					if(value){
						return formatNumber(value,2,1) ;
					}
				},
				month2 : function(value){ //本年合计
					if(value){
						return formatNumber(value,2,1) ;
					}
				},
				month3 : function(value){ //本年合计
					if(value){
						return formatNumber(value,2,1) ;
					}
				},
			 	month4 : function(value){ //本年合计
					if(value){
						return formatNumber(value,2,1) ;
					}
				},
			 	month5 : function(value){ //本年合计
					if(value){
						return formatNumber(value,2,1) ;
					}
				},
			 	month6 : function(value){ //本年合计
					if(value){
						return formatNumber(value,2,1) ;
					}
				},
			 	month7 : function(value){ //本年合计
					if(value){
						return formatNumber(value,2,1) ;
					}
				},
			 	month8 : function(value){ //本年合计
					if(value){
						return formatNumber(value,2,1) ;
					}
				},
			 	month9 : function(value){ //本年合计
					if(value){
						return formatNumber(value,2,1) ;
					}
				},
			 	month10 : function(value){ //本年合计
					if(value){
						return formatNumber(value,2,1) ;
					}
				},
			 	month11 : function(value){ //本年合计
					if(value){
						return formatNumber(value,2,1) ;
					}
				},
			 	month12 : function(value){ //本年合计
					if(value){
						return formatNumber(value,2,1) ;
					}
				}
		};
	   $(function (){
		    loadDict();
	        loadHeadHos();
	        loadHeadDept();
	        query();
	        etTab = $("#item_tab").etTab({
	            onChanged: function(activeState) {
	                if (activeState.index === 0) {
	                    $(".input_1").hide();
	                    query();
	                } else if (activeState.index === 1) {
	                    $(".input_1").show();
	                    queryDept();
	                }
	            }
	        });

	        $(".input_1").hide(); //设置首次加载页面的时候不容许加载下拉框
	    }); 
	    //查询
	    function query() {
	    	if(!budg_year){
	    		$.ligerDialog.error('预算年度不能为空');
	    		return false ;
	    	}
	    	var parms=[];
	   		//根据表字段进行添加查询条件
			parms.push({name: 'year', value: "${year}" });
			parms.push({name: 'index_code', value: index_code.getValue() }),
	        parms.push({ name: 'check_code', value: "${check_code}" });
	        //加载查询条件
	        grid.loadData(parms, "../budgeworkcheck/queryBudgWorkHosMonthCopy.do?isCheck=false");
	    }
	    //查询
	    function queryDept() {
	        if (!budg_year) {
	            $.ligerDialog.error('预算年度不能为空');
	            return false;
	        }
	        var parms = [
	            { name: 'year', value: "${year}" },
	            { name: 'index_code', value: index_code.getValue() },
	            { name: 'dept_id', value: dept_id.getValue() },
	            { name: 'check_code', value: "${check_code}" }
	        ];
	        //加载查询条件
	        gridDept.loadData(parms, "../budgeworkcheck/queryBudgWorkDeptMonthCopy.do?isCheck=false");
	    }	   
	   
		function loadHeadHos(){		
	         var columns = [  
	       	    { display: '年度', name: 'year', align: 'left',width:80},
			 	{ display: '指标编码', name: 'index_code', align: 'left',width:100},
			 	{ display: '指标名称', name: 'index_name', align: 'left',width:120},
			 	{ display: '本年合计', name: 'year_sum', align: 'right',width:120,frozen:true,
					 render:function(ui){
						 if(ui.rowData.year_sum){
							 return formatNumber(ui.rowData.year_sum,2,1);
						 }
					 }
				}
		 	 ];
	         for (var i = 1; i < 13; i++) {
	            columns.push({
	                display: i + '月',
	                name: 'month' + i,
	                align: 'right',
	                width: 120,
	                render:function(ui) {
	                    if (ui.cellData) {
	                        return formatNumber(ui.cellData, 2, 1);
	                    }
	                }
	            })
	         }

	         var paramObj = {
	            inWindowHeight: true,
	            height: '100%',
	            checkbox: true,
	            freezeCols: 4,
	            
	            columns: columns,
	            numberCell: {
	                show: true,
	                resizable: false,
	                width: 30,
	                minWidth: 30
	            },
	            toolbar: {
	                items: [
	                    { type: "button", label: '查询', icon: 'search', listeners: [{ click: query }] },
	                ]
	            }
	         };
	         grid = $("#maingrid").etGrid(paramObj);
		}
		
		function loadHeadDept(){	
	       	var columns = [ 
	       		{ display: '年度', name: 'year', align: 'left',width:80,
			 			},
			 	{ display: '科室编码', name: 'dept_code', align: 'left', width:100,
				 		},
				{ display: '科室名称', name: 'dept_name', align: 'left', width:120,
					 	},
			   	{ display: '指标编码', name: 'index_code', align: 'left', width:100,
					 	},
				{ display: '指标名称', name: 'index_name', align: 'left', width:120,
					 	},
				{ display: '本年合计', name: 'year_sum', align: 'right',width:120,frozen:true,
					 render:function(ui){
						 if(ui.rowData.year_sum){
							 return formatNumber(ui.rowData.year_sum,2,1);
						 }
					 }
				}
			];
	
	        for (var i = 1; i < 13; i++) {
	            columns.push({
	                display: i + '月',
	                name: 'month' + i,
	                align: 'right',
	                width: 120,
	                render:function(ui) {
	                   if (ui.cellData) {
	                       return formatNumber(ui.cellData, 2, 1);
	                   }
	                }
	            })
	        }
	
	        var paramObj = {
	            inWindowHeight: true,
	            height: '100%',
	            checkbox: true,
	            freezeCols: 6,
	
	            columns: columns,
	            toolbar: {
	                items: [
	                    { type: "button", label: '查询', icon: 'search', listeners: [{ click: queryDept }] },
	                ]
	            }
	        }
	        gridDept = $("#maingrid1").etGrid(paramObj);
    }
	//打印回调方法
    function lodopPrint(){
    	if($("#hosItem").css('display') == 'block'){
    		var head="";
     		grid.options.lodop.head=head; 
     		grid.options.lodop.fn=renderFunc;
     		grid.options.lodop.title="调整前预算查看(医院)";	
		}
		
		if($("#deptItem").css('display') == 'block'){
			
			var head="";
			gridDept.options.lodop.head=head; 
			gridDept.options.lodop.fn=renderFunc;
			gridDept.options.lodop.title="调整前预算查看(科室)";
			
		}
    }
	
    function loadDict() {
    	index_code = $("#index_code").etSelect({
            url: "../../queryBudgIndexDict.do?isCheck=false",
            defaultValue: "none",
            onChange: function () {
                if (etTab.activeState.index === 0) {
                    query();
                } else if (etTab.activeState.index === 1) {
                    queryDept();
                }
            }
        });

        dept_id = $("#dept_id").etSelect({
            url: "../../queryBudgDeptDict.do?isCheck=false",
            defaultValue: "none",
            onChange: function () {
                if (etTab.activeState.index === 0) {
                    query();
                } else if (etTab.activeState.index === 1) {
                    queryDept();
                }
            }
        });
    }
	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div class="mian">
        <table class="table-layout">
            <tr>
                <td class="label">预算年度：</td>
                <td class="ipt">
                    <input id="budg_year" type="text" disabled value="${year}" />
                </td>

                <td class="label">指标名称：</td>
                <td class="ipt">
                    <select id="index_code" style="width: 180px;"></select>
                </td>

                <td class="label input_1">科室名称：</td>
                <td class="ipt input_1">
                    <select id="dept_id" style="width: 180px;"></select>
                </td>
            </tr>
        </table>
    </div>

    <div id="item_tab">
        <div id="hosItem" title="医院">
            <div id="maingrid"></div>
        </div>
        <div id="deptItem" title="科室">
            <div id="maingrid1"></div>
        </div>
    </div>
</body>
</html>