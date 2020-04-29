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
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
		 loadHotkeys();
		
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	  grid.options.parms.push({name:'dept_id',value:liger.get("dept_id").getValue().split(".")[0]}); 
    	  grid.options.parms.push({name:'emp_id',value:liger.get("emp_id").getValue().split(".")[0]}); 
    	  if($("#is_price").prop("checked") == true){
    		  grid.options.parms.push({name:'is_price',value:'1'}); 
    	  }
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
        	   { display: '科室', name: 'dept_name', align: 'left',
                   totalSummary:
                   {
                       render: function (suminf, column, cell)
                       {
                           return '<div>合计</div>';
                       },
                       align: 'center'
                   }
		 		},
                     { display: '借款人', name: 'emp_name', align: 'left'
					 		},
					 		{ display: '借款金额', name: 'borrow_amount', 
								align : 'right',
								render: function(item)
					            {
				                    	return formatNumber(item.borrow_amount,2,1);
					            },
			                    totalSummary:
			                    {
			                        render: function (suminf, column, cell)
			                        {
			                            return '<div>' + formatNumber(suminf.sum,2,1) + '</div>';
			                        }
			                    } 
					 		},
                     { display: '报销冲抵金额', name: 'offset_amount', align: 'right', 
								align : 'right',
								render: function(item)
					            {
				                    	return formatNumber(item.offset_amount,2,1);
					            },
			                    totalSummary:
			                    {
			                        render: function (suminf, column, cell)
			                        {
			                            return '<div>' + formatNumber(suminf.sum,2,1) + '</div>';
			                        }
			                    }
					 		},
                     { display: '退还金额', name: 'return_amount', align: 'right', 
								align : 'right',
								render: function(item)
					            {
				                    	return formatNumber(item.return_amount,2,1);
					            },
			                    totalSummary:
			                    {
			                        render: function (suminf, column, cell)
			                        {
			                            return '<div>' + formatNumber(suminf.sum,2,1) + '</div>';
			                        }
			                    }
					 		},
                     { display: '借款余额', name: 'remain_amount', align: 'right', 
								align : 'right',
								render: function(item)
					            {
				                    	return formatNumber(item.remain_amount,2,1);
					            },
			                    totalSummary:
			                    {
			                        render: function (suminf, column, cell)
			                        {
			                            return '<div>' + formatNumber(suminf.sum,2,1) + '</div>';
			                        }
			                    }
					 		}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryBudgBorrDept.do',
                     width: '100%', height: '100%', checkbox: false,rownumbers:false,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询（<u>E</u>）', id:'search', click: query,icon:'search' },
                     	{ line : true},
						{ text : '打印',id : 'print',click :print ,icon : 'print'}
				      ]}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    	
	function print(){
    	
    	if(grid.getData().length==0){
			$.ligerDialog.error("请先查询数据！");
			return;
		}
    	
    	/* var heads={
      		  //"isAuto": true/false 默认true，页眉右上角默认显示页码
      		  "rows": [
  	          {"cell":0,"value":"科室名称："+liger.get("dept_id").getText(),"colSpan":"5"},
  	          {"cell":3,"value":"项目名称："+liger.get("proj_id").getText(),"from":"right","align":"right","colSpan":"4"}
      		  ]
      	}; */
           	
    	var printPara={
       			title: "科室借款余额查询",//标题
       			columns: JSON.stringify(grid.getPrintColumns()),//表头
       			class_name: "com.chd.hrp.acc.service.payable.base.BudgBorrDeptService",
    			method_name: "queryBudgBorrDeptPrint",
    			bean_name: "budgBorrDeptService"/* ,
    			heads: JSON.stringify(heads) *///表头需要打印的查询条件,可以为空
    			/* foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 */
       		};
    	
    	$.each(grid.options.parms,function(i,obj){
   			printPara[obj.name]=obj.value;
    	});
    	
    	officeGridPrint(printPara);
   		
    }
 
    function loadDict(){
            //字典下拉框
    	autocomplete("#dept_id", "../../../../../sys/queryDeptDict.do?isCheck=false", "id","text", true, true,null,false);   
    	
    	autocomplete("#emp_id","../../../../queryEmpDict.do?isCheck=false", "id","text", true, true,null,false,null,"180");
         }  
    //键盘事件
	  function loadHotkeys() {

		hotkeys('Q', query);

	 }
 	  
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
        
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id"  /></td>
            <td align="left"></td>
             
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">借款人：</td>
            <td align="left" class="l-table-edit-td"><input name="emp_id" type="text" id="emp_id"  /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><input type="checkbox" id="is_price" name="is_price"/></td>
            <td align="left" class="l-table-edit-td">不显示余额为0的数据</td>
            <td align="left"></td>
        </tr> 
    </table>
	<div id="maingrid"></div>
	
</body>
</html>
