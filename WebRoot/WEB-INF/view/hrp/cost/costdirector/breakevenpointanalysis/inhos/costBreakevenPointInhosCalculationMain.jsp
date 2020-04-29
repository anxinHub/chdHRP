<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>科室盈亏分析</title>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">

		var grid;
		var gridManager = null;
		
		
      $(function(){
    	  loadHead(null);//加载数据
			$("#app_dept").ligerTextBox({ width: 160 });
    	  query();
      });
    
       function query(){
       	 grid.options.parms=[];
      	 grid.options.newPage=1;
      	   //根据表字段进行添加查询条件，获取年份和月份
       	    grid.options.parms.push({name:'year_month_begin',value:'${year_month_begin}'}); 
       	    grid.options.parms.push({name:'year_month_end',value:'${year_month_end}'});
       	    grid.options.parms.push({name:'dept_code',value:'${dept_code}'}); 
 	       	//加载查询条件
 	       grid.loadData(grid.where);
 	       echartsShow();
        }

		function loadHead(){
			grid =  $("#maingrid").ligerGrid({
				columns: [{
        			display: '影响收益因素',
        			align: 'left',
        			columns:[{
       				   display: '因素',
           			   name: 'item_name',
           			   align: 'left',
            	     },{
       				    display: '基础数据',
             			name: 'money',
             			align: 'left',
             			render: function(rowdata, rowindex, value) {
 	 						return formatNumber(rowdata.money, 2, 1);
 	 			     	}
                	  }]
	        	  },{
	        		 display: '变动程度%',
	        		 align: 'left',
	        		 name:'change',
	        		 value:"1",
        			  editor: {
						type: 'float',
					 },
	        		 render: function(rowdata, rowindex, value) {
		        		 if(rowdata.money != 0 && rowdata.money !=null)
	        			if(rowdata.row_id == 2||rowdata.row_id == 3||rowdata.row_id == 4||rowdata.row_id == 5)
	        				return "<div align='center' style='margin-top: 5px'><input type='text' id=change"+rowdata.row_id+" oninput='calculation(1)' value="+rowdata.change+"></div>"
	 			     }
		          },{
		        	 display: '变动量',
		        	 align: 'left',
		        	 name:"change_num",
		        	 editor: {
							type: 'float',
					 },
					 render: function(rowdata, rowindex, value) {
						    if(rowdata.money != 0 && rowdata.money !=null)
						    if(rowdata.row_id == 2||rowdata.row_id == 3||rowdata.row_id == 4||rowdata.row_id == 5)
	 						return "<div align='center' style='margin-top: 5px'><input type='text' id=change_num"+rowdata.row_id+" oninput='calculation(2)' value="+rowdata.change_num+"></div>"
	 			     }
			        },{
			          display: '测算结果',
			          name: 'calmoney',
			          align: 'left',
			      	   render: function(rowdata, rowindex, value) {
	 						return formatNumber(rowdata.calmoney, 2, 1);
	 			     	}
				     }
        	   ],
	           dataAction: 'server',dataType: 'server',usePager:true,url:'queryCostBreakevenPointInhosCalculation.do?isCheck=false',
	           width: '100%',height: '100%',checkbox:false,rownumbers:true,delayLoad :true,
	           selectRowButtonOnly:true,enabledEdit:false,onBeforeEdit: f_onBeforeEdit,
	           onAfterShowData:f_onAfterShowData,
	           });
	           gridManager = $("#maingrid").ligerGetGridManager();
		}

		  //只允许编辑前3行
        function f_onBeforeEdit(e)
        { 
            if(e.rowindex == 1 || e.rowindex == 2 || e.rowindex == 3 || e.rowindex == 4 ) return true;
            return false;
        }

			function f_onAfterShowData(){
				 var gridRowData = grid.getData();
			 for (var i = 0; i < gridRowData.length; i++) {
				   if(gridRowData[i].row_id == 1 || gridRowData[i].row_id == 2||gridRowData[i].row_id == 3||gridRowData[i].row_id == 4){
					   grid.updateCell('change','0',gridRowData[i].row_id);
					   grid.updateCell('change_num','0',gridRowData[i].row_id);
					}
			    }
		    }
		    
		    function calculation(state){
		    	//变动量=基础数据*变动程度%   
		    	//变动结果=基础数据+变动量
		    	var change;
		    	var data = gridManager.getCheckedRows();
		    	//变动量
		        if(state==1){
		       	  grid.updateCell('change_num', data[0].money * $("#change"+data[0].row_id).val(),data[0].__index);
		       	  grid.updateCell('calmoney', data[0].money + data[0].change_num,data[0].__index);
			   }
		       //变动结果
		       if(state==2){
		    	   grid.updateCell('change',$("#change_num"+data[0].row_id).val() / data[0].money,data[0].__index);
		    	   grid.updateCell('calmoney', Number($("#change_num"+data[0].row_id).val()) + data[0].money ,data[0].__index);
			    }

			}
</script>
</head>
<body style="padding: 0px; overflow: hidden;">
	 <div id="toptoolbar" ></div>
	 <div id="maingrid" style="margin:0; padding:0; border: none;"></div>
</body>
</html>