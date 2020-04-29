<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script src="<%=path%>/lib/echarts/dist/echarts.js" type="text/javascript"></script>
<script type="text/javascript">	
    $(function ()
    {
		loadDict();
    });


    function loadDict(){
    		
		var param = {
            	query_key:''
        };
		
    	autocomplete("#ass_code","../queryAssNoDict.do?isCheck=false","id","text",true,true,null,true);
         }  
    
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_code" type="text" id="ass_code" /></td>
            <td>
            	<input class="l-button l-button-test" style="float: right;" type="button" value="查询图表" onclick="query()"/>
            </td>
         </tr> 
    </table>
	<div id="maingrid" style="height:400px;"></div>
	<script type="text/javascript">
        
        
        function query(){
        	maingrid();
        }
        function maingrid(){
        	var ass_id = liger.get("ass_code").getValue().split("@")[0];
        	var ass_no = liger.get("ass_code").getValue().split("@")[1];
            var param = {
            		'ass_id':ass_id,
            		'ass_no':ass_no
            };
	       	$.post("queryAssCirculationView.do?isCheck=false",param,function(data){
	                     var myChart = echarts.init(document.getElementById('maingrid'));
	                     var option = {
	                     	    title : {
	                     	        text: '资产流转查看',
	                     	        subtext: ''
	                     	    },
	                     	    tooltip : {
	                     	        trigger: 'axis'
	                     	    },
	                     	    legend: {
	                     	        data:['入库','科室领用','科室退库','仓库转移仓库','科室转移科室']
	                     	    },
	                     	    toolbox: {
	                     	        show : true,
	                     	        feature : {
	                     	            mark : {show: true},
	                     	            dataView : {show: true, readOnly: false},
	                     	            magicType : {show: true, type: ['line', 'bar']},
	                     	            restore : {show: true},
	                     	            saveAsImage : {show: true}
	                     	        }
	                     	    },
	                     	    calculable : true,
	                     	    xAxis : [
	                     	        {
	                     	            type : 'category',
	                     	            data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
	                     	        }
	                     	    ],
	                     	    yAxis : [
	                     	        {
	                     	            type : 'value'
	                     	        }
	                     	    ],
	                     	    series : [
	                     	        {
	                     	            name:'入库',
	                     	            type:'bar',
	                     	            data:data.Rows[0].ruku,
	                     	            markPoint : {
	                     	                data : [
	                     	                    {type : 'max', name: '年最高'},
	                     	                    {type : 'min', name: '年最低'}
	                     	                ]
	                     	            }
	                     	        },
	                     	        {
	                     	            name:'科室领用',
	                     	            type:'bar',
	                     	            data:data.Rows[0].deptInDept,
	                     	            markPoint : {
	                     	                data : [
	                     	                    {type : 'max',name : '年最高'},
	                     	                    {type : 'min',name : '年最低'}
	                     	                ]
	                     	            }
	                     	        },
	                     	        {
	                     	            name:'科室退库',
	                     	            type:'bar',
	                     	            data:data.Rows[0].deptBack,
	                     	            markPoint : {
	                     	                data : [
	                     	                    {type : 'max',name : '年最高'},
	                     	                    {type : 'min',name : '年最低'}
	                     	                ]
	                     	            }
	                     	        },
	                     	        {
	                     	            name:'仓库转移仓库',
	                     	            type:'bar',
	                     	            data:data.Rows[0].storeInStore,
	                     	            markPoint : {
	                     	                data : [
	                     	                    {type : 'max',name : '年最高'},
	                     	                    {type : 'min',name : '年最低'}
	                     	                ]
	                     	            }
	                     	        },
	                     	        {
	                     	            name:'科室转移科室',
	                     	            type:'bar',
	                     	            data:data.Rows[0].deptInDept,
	                     	            markPoint : {
	                     	                data : [
	                     	                    {type : 'max',name : '年最高'},
	                     	                    {type : 'min',name : '年最低'}
	                     	                ]
	                     	            }
	                     	        }
	                     	    ]
	                     	};
	                     myChart.setOption(option);
	       	},"json");
        }
    </script>
</body>
</html>
