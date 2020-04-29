<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    var ass_card_no = '${ass_card_no}';
    
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
        query();
		
    });
    
    function setAssCardNo(no){
    	ass_card_no = no;
    	query();
    }
    
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
    		grid.options.parms.push({
    			name : 'ass_nature',
    			value : '${ass_nature}'
    		});
    		grid.options.parms.push({
    			name : 'ass_card_no',	
    			value : ass_card_no
    		});
    		
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '变更单号', name: 'change_no', align: 'left',width:130
					 		},
                     { display: '变更日期', name: 'change_date', align: 'left',width:130
					 		},
                     { display: '业务类型', name: 'bus_type', align: 'left',width:130,
								render : function(item) {
									if(item.change_price < 0){
										return "<font color='red'>调减</font>"
									}else if(item.change_price > 0){
										return "<font color='Blue'>调增</font>";
									}
								}
					 		},
                     { display: '变动前原值', name: 'old_price', align: 'right',width:120,
								render : function(item) {
									return formatNumber(
											item.old_price, '${ass_05006}', 1);
								}
					 		},
                     { display: '变动后原值', name: 'price', align: 'right',width:120,
								render : function(item) {
									return formatNumber(
											item.price, '${ass_05006}', 1);
								}
					 		},
                     { display: '变动金额', name: 'change_price', align: 'right',width:120,
								render : function(item) {
									return formatNumber(
											item.change_price, '${ass_05005}', 1);
								}
					 		},
                     { display: '操作人', name: 'create_emp_name', align: 'left',width:130
					 		},
                     { display: '操作时间', name: 'create_date', align: 'left',width:130
					 		},
                     { display: '审核人', name: 'audit_emp_name', align: 'left',width:130
					 		},
                     { display: '审核时间', name: 'audit_date', align: 'right',width:130
					 		},
                     { display: '备注', name: 'note', align: 'left',width:160
					 		}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAssChangeCardInit.do',
                     width: '100%', height: '90%', checkbox: true,rownumbers:true,delayLoad : true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     scroll: true,
                     frozen:true,
                     alternatingRow: false,
                     scrollToPage: true,
                     scrollToAppend: true
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function loadDict(){
         }  
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>


	<div id="maingrid"></div>
	
</body>
</html>
