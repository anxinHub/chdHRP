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
    <jsp:param value="select,datepicker,dialog,grid,tab" name="plugins" />
</jsp:include>
<script type="text/javascript">
   var grid;
    var gridManager = null;
    var userUpdateStr ;
    var sum_year='${sum_year}';
    var year_input,index_code_select,dept_id;
    $(function (){
 	   	loadDict();
 	    loadHeadHos();
 	    loadHeadDept();
 	    etTab = $("#item_tab").etTab({
 	        onChanged: function(activeState) {
 	            if (activeState.index === 0) {
 	                query();
 	            } else if (activeState.index === 1) {
 	                queryDept();
 	            }
 	        }
 	    });
    }); 
    
   
    //查询
    function query() {
 	   if(!year_input.getValue()){
       		$.etDialog.error('预算年度不能为空');
       		return false ;
       	}
        var parms = [
     	   { name: 'year', value: year_input.getValue() },
 		   { name: 'index_code', value: index_code_select.getValue() },
           { name: 'dept_id', value: dept_id.getValue() },
        ];
        //加载查询条件
        grid.loadData(parms, "queryDeptExecData.do?isCheck=false.do?isCheck=false");
    }	   
    
    //查询
    function queryDept() {
 	   if(!year_input.getValue()){
       		$.etDialog.error('预算年度不能为空');
       		return false ;
       	}
    	    var parms=[];
   		//根据表字段进行添加查询条件
 		parms.push({name: 'year', value: year_input.getValue()});
 		parms.push({name: 'index_code', value: index_code_select.getValue() }),
        //加载查询条件
        gridDept.loadData(parms, "queryBudgWorkCheckDeptMonthSum.do?isCheck=false&month="+"${month}");
    }
    function loadHeadHos(){		
        var columns = [ 
     	   { display: '年度', name: 'year',width:90},
     	   { display: '科室编码', name: 'dept_code',width:90},
 	       { display: '科室名称', name: 'dept_name',width:120},
 	       { display: '指标编码', name: 'index_code',width:90},	 
 	       { display: '指标名称', name: 'index_name',width:100},	 
            { display: '项目', name: 'item',width:90,frozen :true,},
 	       { display: '本年合计', name: 'sum_year', align: 'right',width:100,frozen :true,
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
 				   if(ui.rowData.b_no == 4 ){
					  return formatNumber(ui.cellData,2,1) +"%";
				   }else{
					  return formatNumber(ui.cellData,2,1);
				   }
               }
           })
        }

        var paramObj = {
           inWindowHeight: true,
           height: '100%',
           checkbox: true,
           freezeCols: 5,
           
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
       		{ display: '预算值', name: 'budg_value', align: 'right',width: 650,
 	      			render:function(ui){
 	      				var value = ui.cellData;
 	      				if (value){
 	    					return formatNumber(value,2,1)
 	      				}else{
 	      					return formatNumber(0,2,1)
 	      				}
 					}
 				 },
 		    { display: '预算下达日期', name: 'issue_data', minWidth:600 							 			 
 				 }
 		];

         var paramObj = {
            inWindowHeight: true,
            height: '100%',
            width: '100%',
            checkbox: true,

            columns: columns,
            toolbar: {
                items: [
                    { type: "button", label: '查询', icon: 'search', listeners: [{ click: queryDept }] },
                ]
            }
        }
        gridDept = $("#maingrid1").etGrid(paramObj);
     }
 	
 	function loadDict(){
    	getData("../../queryBudgYear.do?isCheck=false", function (data) {
			year_input = $("#year_input").etDatepicker({
				defaultDate: '${year}',
				view: "years",
				minView: "years",
				dateFormat: "yyyy",
				minDate: data[data.length - 1].text,
				maxDate: data[0].text,
				todayButton: false,
				onChanged: function (value) {
					if (etTab.activeState.index === 0) {
	                    query();
	                } else if (etTab.activeState.index === 1) {
	                    queryDept();
	                }
				}
			});
		});
    	
    	index_code_select = $("#index_code_select").etSelect({
            url: "../../queryBudgIndexDict.do?isCheck=false",
            defaultValue: '${index_code}',
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
      //ajax获取数据
    	function getData(url, callback) {
    		$.ajax({
    			url: url,
    			dataType: "JSON",
    			type: "post",
    			success: function (res) {
    				if (typeof callback === "function") {
    					callback(res);
    				}
    			}
    		});
    	};
    }  
	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
<div class="mian">
        <table class="table-layout">
            <tr>
                <td class="label">预算年度：</td>
                <td class="ipt">
                    <input id="year_input" type="text" />
                </td>

                <td class="label">指标名称：</td>
                <td class="ipt">
                    <select id="index_code_select" style="width: 180px;"></select>
                </td>

                <td class="label">科室名称：</td>
                <td class="ipt">
                    <select id="dept_id" style="width: 180px;"></select>
                </td>
            </tr>
        </table>
    </div>

    <div id="item_tab">
        <div id="hosItem" title="科室业务预算查询">
            <div id="maingrid"></div>
        </div>
        <div id="deptItem" title="预算调整记录">
            <div id="maingrid1"></div>
        </div>
    </div>
</body>
</html>