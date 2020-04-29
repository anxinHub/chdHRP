<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" /> 
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
    });
    //查询
    function  query(){
    	grid.options.parms=[];
    	grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'acc_year',value:$("#acc_year").val()}); 
    	//加载查询条件
    	grid.loadData(grid.where);
     }


    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '方案编码', name: 'plan_code', align: 'left'},
                     { display: '方案名称', name: 'plan_name', align: 'left'},
					 { display: '所属年度', name: 'acc_year', align: 'left'},
                     { display: '开始月', name: 'start_month', align: 'left'},
                     { display: '结束月', name: 'end_month', align: 'left'},
                     { display: '审核标志', name: 'is_check', align: 'left',
                    	 render: function (rowdata, rowindex, value){
                             if(rowdata.is_check == 0){
                                  return "未审核"
                              }else if(rowdata.is_check == 1){
                             	 return "审核"
                            } 
  			             }
			         },
                     { display: '核算方法', name: 'method', align: 'left',
                    	 render: function (rowdata, rowindex, value){
                    		 if(rowdata.method == "01"){
                                 return "作业成本法"
                             }else if(rowdata.method == "02"){
                            	 return "收入比法"
                             }else if(rowdata.method == "03"){
                            	 return "相对价值比率法"
                          }
                    	 }
                	  },
                     { display: '是否当前方案', name: 'is_current', align: 'left',
	                    	 render: function (rowdata, rowindex, value){
	                    		 if(rowdata.is_current == 0){
	                                 return "否"
	                             }else if(rowdata.is_current == 1){
	                            	 return "是"
	                              } 
	 			             }
			            }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryHtcRelativePlanHistory.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true }
    				]}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
	
	function loadDict() {
		
		
        autocomplete("#plan_code","../../../info/base/queryHtcPlan.do?isCheck=false", "id", "text",true, true);
        $("#acc_year").ligerTextBox({width:160});
    	autodate("#acc_year", "YYYY"); 
			
	}   
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
        <td align="right" class="l-table-edit-td" style="padding-left: 20px;">年度：</td>
			<td align="left" class="l-table-edit-td"><input class="Wdate" name="acc_year" type="text" id="acc_year" ltype="text"
				onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy'})" style="width:160px;"/></td>
			<td align="left"></td>
        </tr> 
    </table>
	<div id="maingrid"></div>
</body>
</html>
