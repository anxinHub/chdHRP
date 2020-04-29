<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"><html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
    var grid;
    var gridManager = null;
    $(function ()
    {
        
    	//加载数据
    	loadHead(null);	
		
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	  grid.options.parms.push({name:'type_code',value:$("#type_code").val()}); 
        
    	  grid.options.parms.push({name:'type_name',value:$("#type_name").val()});

    	//加载查询条件
    	grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '库房名称', name: 'store_name', align: 'left',
        			    	totalSummary: {
        	                    type: 'sum',
        	                    render: function (suminf, column, cell) {
        	                        return '<div>合计</div>';
        	                    }
        	                }
					 		},
                     { display: '药品编码', name: 'inv_code', align: 'left'
					 		},
					 { display: '药品名称', name: 'inv_name', align: 'left'
					 		},
                     { display: '规格型号', name: 'inv_model', align: 'left'
					 		},
		             { display: '计量单位', name: 'unit_name', align: 'left'
						 	},
				     { display: '数量', name: 'cur_amount', align: 'left',
								 render:function(rowdata){
									  return formatNumber(rowdata.cur_amount ==null ? 0 : rowdata.cur_amount,2,1);
					             },totalSummary: {
				                      type: 'sum',
				                      render: function (suminf, column, cell) {
				                         return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum,2,1)+ '</div>';
				                      }
				                 }
							}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryMedInvCurAmountDetail.do?isCheck=false&inv_id='+'${inv_id}',
                     width: '100%', height: '100%', rownumbers:true,
                     selectRowButtonOnly:true
    				
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    
    	
   
    
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="maingrid"></div>
	   
</body>
</html>
