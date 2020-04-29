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
    });

    
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
<table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
            <td>
            	<input class="l-button l-button-test" style="float: right;" type="button" value="查询图表" onclick="query()"/>
            </td>
         </tr> 
    </table>
	<div id="maingrid" style="height:500px;"></div>
	<script type="text/javascript">
	
	function query(){
    	$.post("queryDept.do?isCheck=false",null,function(resdata){
    			                     var myChart = echarts.init(document.getElementById('maingrid'));
    			                     var option = {
    			                    		    title : {
    			                    		        text: '全院资产分布',
    			                    		        subtext: '',
    			                    	            left: 'leafDepth'
    			                    		    },
    			                    		    tooltip : {
    			                    		    },
    			                    		    toolbox: {
    			                    		        show : true,
    			                    		        feature : {
    			                    		            mark : {show: true},
    			                    		            dataView : {show: true, readOnly: false},
    			                    		            restore : {show: true},
    			                    		            saveAsImage : {show: true}
    			                    		        }
    			                    		    },
    			                    		    calculable : false,
    			                    		    series : [
    			                    		        {
    			                    		            name:'全院资产分布',
    			                    		            type:'treemap',
    			                    		            visibleMin: 300,
    			                    		            data:resdata.data,
    			                    		            leafDepth: 2,
    			                    		            levels: [
    			                    		                     {
    			                    		                         itemStyle: {
    			                    		                             normal: {
    			                    		                                 borderColor: '#555',
    			                    		                                 borderWidth: 4,
    			                    		                                 gapWidth: 4
    			                    		                             }
    			                    		                         }
    			                    		                     },
    			                    		                     {
    			                    		                         colorSaturation: [0.3, 0.6],
    			                    		                         itemStyle: {
    			                    		                             normal: {
    			                    		                                 borderColorSaturation: 0.7,
    			                    		                                 gapWidth: 2,
    			                    		                                 borderWidth: 2
    			                    		                             }
    			                    		                         }
    			                    		                     },
    			                    		                     {
    			                    		                         colorSaturation: [0.3, 0.5],
    			                    		                         itemStyle: {
    			                    		                             normal: {
    			                    		                                 borderColorSaturation: 0.6,
    			                    		                                 gapWidth: 1
    			                    		                             }
    			                    		                         }
    			                    		                     },
    			                    		                     {
    			                    		                         colorSaturation: [0.3, 0.5]
    			                    		                     }
    			                    		                 ]
    			                    		        }
    			                    		    ]
    			                    		};
    			                     myChart.setOption(option);
    	},"json");
	}
	
    </script>
   
</body>
</html>
