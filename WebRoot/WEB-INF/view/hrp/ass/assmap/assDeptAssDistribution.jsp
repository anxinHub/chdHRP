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
    		
    	
    	 $("#dept_code").ligerComboBox({
		      	url: '../queryDeptDict.do?isCheck=false',
		      	valueField: 'id',
		       	textField: 'text', 
		       	selectBoxWidth: 180,
		      	autocomplete: true,
		      	width: 180
			 });
    	 
    	 //autocomplete("#ass_naturs","../queryAssNaturs.do?isCheck=false","id","text",true,true,null,false);
    	 
    	 autocomplete("#dept_kind_code","../queryDeptKindDict.do?isCheck=false","id","text",true,true,null,false);
    	 
    	 
    }  
    
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_code" type="text" id="dept_code" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室分类：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_kind_code" type="text" id="dept_kind_code" /></td>
            
            <!-- 
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产性质：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_naturs" type="text" id="ass_naturs" /></td>
             -->
             <td>
            	<input class="l-button l-button-test" style="float: right;" type="button" value="查询图表" onclick="query()"/>
            </td>
         </tr> 
    </table>
	<div id="maingrid" style="height:600px;"></div>
	<script type="text/javascript">
        function query(){
        	maingridDept();
        }
        
        function maingridDept(){
        	var dept_id = liger.get("dept_code").getValue().split("@")[0];
        	var dept_no = liger.get("dept_code").getValue().split("@")[1];
        	//var ass_naturs = liger.get("ass_naturs").getValue();
        	var dept_kind_code = liger.get("dept_kind_code").getValue();
            var param = {
            		'user_dept_id':dept_id,
            		'user_dept_no':dept_no,
            		'dept_kind_code':dept_kind_code
            		//'ass_naturs':ass_naturs
            };
            if(isnull(dept_id) && isnull(dept_kind_code) && isnull(ass_naturs)){
            	$.ligerDialog.error('至少选择一个条件查询');
    			return;
            }
        	$.post("queryDeptAssDistribution.do?isCheck=false",param,function(data){
        		var showName = [];
        		$.each(data.Rows,function(i,v){
        			showName.push(v.name);
        		});
                            var myChart = echarts.init(document.getElementById('maingrid'));
                            var option = {
                            	    title : {
                            	        text: '科室资产分布',
                            	        subtext: '',
                            	        x:'center'
                            	    },
                            	    tooltip : {
                            	        trigger: 'item',
                            	        formatter: "{a} <br/>{b} : {c} ({d}%)"
                            	    },
                            	    //legend: {
                            	       // orient : 'vertical',
                            	        //x : 'left',
                            	       // data:showName
                            	   // },
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
                            	            name:'科室资产分布',
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
</body>
</html>
