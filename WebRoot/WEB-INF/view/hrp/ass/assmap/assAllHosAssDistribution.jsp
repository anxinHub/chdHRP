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
    		
		
    	autocomplete("#ass_code","../queryAssNoDict.do?isCheck=false","id","text",true,true,null,false);
    	
    	autocomplete("#ass_type_id","../queryAssTypeDict.do?isCheck=false&is_last=1","id","text",true,true,null,false);
    	
    	
    	//autocomplete("#ass_naturs","../queryAssNaturs.do?isCheck=false","id","text",true,true,null,false);
         }  
    
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_code" type="text" id="ass_code" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产分类：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_type_id" type="text" id="ass_type_id" /></td>
            <!-- 
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产性质：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_naturs" type="text" id="ass_naturs" /></td> -->
            <td>
            	<input class="l-button l-button-test" style="float: right;" type="button" value="查询图表" onclick="query()"/>
            </td>
         </tr> 
    </table>
	<div id="maingrid" style="height:700px;width: 600px;float: left;"></div>
	<script type="text/javascript">
        function query(){
        	var ass_id = liger.get("ass_code").getValue().split("@")[0];
        	var ass_no = liger.get("ass_code").getValue().split("@")[1];
        	var ass_type_id = liger.get("ass_type_id").getValue();
        	/* var ass_naturs = liger.get("ass_naturs").getValue(); */
        	if(isnull(ass_id) && isnull(ass_type_id) /* && isnull(ass_naturs) */){
            	$.ligerDialog.error('至少选择一个条件查询');
    			return;
            }
        	
        	maingridDept();
        	maingridStore();
        }
        function maingridStore(){
        	var ass_id = liger.get("ass_code").getValue().split("@")[0];
        	var ass_no = liger.get("ass_code").getValue().split("@")[1];
        	var ass_type_id = liger.get("ass_type_id").getValue();
        	//var ass_naturs = liger.get("ass_naturs").getValue();
            var param = {
            		'ass_id':ass_id,
            		'ass_no':ass_no,
            		'ass_type_id':ass_type_id
            		//'ass_naturs':ass_naturs
            };
       	$.post("queryAssStoreDistribution.do?isCheck=false",param,function(data){
       		var showName = [];
       		$.each(data.Rows,function(i,v){
       			showName.push(v.name);
       		});
                           var myChart = echarts.init(document.getElementById('maingrid'));
                           var option = {
                           	    title : {
                           	        text: '资产在仓库的分布',
                           	        subtext: '',
                           	        x:'center'
                           	    },
                           	    tooltip : {
                           	        trigger: 'item',
                           	        formatter: "{a} <br/>{b} : {c} ({d}%)"
                           	    },
                           	    //legend: {
                           	        //orient : 'vertical',
                           	        //x : 'left',
                           	       // data:showName
                           	    //},
                           	    toolbox: {
                           	        show : true,
                           	        feature : {
                           	            mark : {show: true},
                           	            dataView : {show: true, readOnly: false},
                           	            saveAsImage : {show: true}
                           	        }
                           	    },
                           	    calculable : true,
                           	    series : [
                           	        {
                           	            name:'资产在仓库的分布',
                           	            type:'pie',
                           	            radius : '55%',
                           	            center: ['50%', '60%'],
                           	            data:data.Rows
                           	        }
                           	    ]
                           	};
                           myChart.setOption(option);
       		},"json");
        }
        
        function maingridDept(){
        	var ass_id = liger.get("ass_code").getValue().split("@")[0];
        	var ass_no = liger.get("ass_code").getValue().split("@")[1];
        	var ass_type_id = liger.get("ass_type_id").getValue();
        	/* var ass_naturs = liger.get("ass_naturs").getValue(); */
            var param = {
            		'ass_id':ass_id,
            		'ass_no':ass_no,
            		'ass_type_id':ass_type_id
            		/* 'ass_naturs':ass_naturs */
            };
        	$.post("queryAssDeptDistribution.do?isCheck=false",param,function(data){
        		var showName = [];
        		$.each(data.Rows,function(i,v){
        			showName.push(v.name);
        		});
                            var myChart = echarts.init(document.getElementById('maingrid1'));
                            var option = {
                            	    title : {
                            	        text: '资产在科室的分布',
                            	        subtext: '',
                            	        x:'center'
                            	    },
                            	    tooltip : {
                            	        trigger: 'item',
                            	        formatter: "{a} <br/>{b} : {c} ({d}%)"
                            	    },
                            	    //legend: {
                            	        //orient : 'vertical',
                            	        //x : 'left',
                            	        //data:showName
                            	    //},
                            	    toolbox: {
                            	        show : true,
                            	        feature : {
                            	            mark : {show: true},
                            	            dataView : {show: true, readOnly: false},
                            	            saveAsImage : {show: true}
                            	        }
                            	    },
                            	    calculable : true,
                            	    series : [
                            	        {
                            	            name:'资产在科室的分布',
                            	            type:'pie',
                            	            radius : '55%',
                            	            center: ['50%', '60%'],
                            	            data:data.Rows
                            	        }
                            	    ]
                            	};
                            myChart.setOption(option);
        	},"json");
        }
    </script>
    <div id="maingrid1" style="height:700px;width: 900px;float: left;"></div>
</body>
</html>
