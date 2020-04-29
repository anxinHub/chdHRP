
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
    var userUpdateStr;
    $(function ()
    {
    	//加载数据
    	loadHead(null);	
		
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        /* //根据表字段进行添加查询条件
    	  grid.options.parms.push({name:'make_date',value:$("#make_date").val()});//日期范围：开始时间 
    	  grid.options.parms.push({name:'pur_hos_id',value:liger.get("pur_hos_id").getValue()});//状态
    	  grid.options.parms.push({name:'req_hos_id',value:liger.get("req_hos_id").getValue()});//计划类型 */
    	//加载查询条件
    	grid.loadData(grid.where);
     }
 
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '采购计划单号', name: 'pur_code', align: 'left'
					 		},
                     { display: '计划单位', name: 'req_hos_name', align: 'left'
					 		},
					 { display: '材料编码', name: 'inv_code', align: 'left'
					 		},
					 { display: '材料名称', name: 'inv_name', align: 'left'
						 	},
                     { display: '规格型号', name: 'inv_model', align: 'left'
					 		},
					 { display: '计量单位', name: 'unit_name', align: 'left'
					 },
					 { display: '单价', name: 'price', align: 'left'
					 },
					 { display: '计划数量', name: 'pur_amount', align: 'left'
					 },
					 { display: '执行数量', name: 'exec_amount', align: 'left',type:'number',editor: { type: 'number'}
					 },
					 { display: '执行金额', name: 'exec_money', align: 'left',render:
						 function(rowdata){
						 	return rowdata.price*rowdata.exec_amount;
					 	}
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatPurMainAmountDetail.do?isCheck=false&inv_id=${inv_id}&total_id=${total_id}&pur_id=${pur_id}',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true, enabledEdit : true,
                     selectRowButtonOnly:true
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
   function saveMatPurMain(){
	   
       var allData = gridManager.getData();
	   
	   var pur_amount = null;
	   
	   var exec_amount = 0;
	   
	   var flag ;
	   
	   $(allData).each(function(){
		   
		   if(this.exec_amount == undefined || this.exec_amount == ''){
   			
   			$.ligerDialog.error('执行数量不能为零');
   			
   			return flag = false;
   		}
		   pur_amount = this.pur_amount;
		   
		   exec_amount += parseInt(this.exec_amount);
	   });
	   
	   if(flag == false){
		   
		   return;
	   }
	   
	   var inv_id = '${inv_id}';
	   
	   var pur_id = '${pur_id}';
	   
	   var total_id = '${total_id}';
	   
	   var rowindex = '${rowindex}';
	   
	   var pur_rela = '['+'{"pur_id":' + pur_id + ',"total_id":' + total_id + ',"exec_amount":' + exec_amount + ',"pur_amount":' + pur_amount +',"inv_id":' + inv_id + '}'+']';
	   
 	   parent.updatePurAmount(rowindex,pur_rela,exec_amount);
	   
	   this_close();
   }
   
   function this_close(){
		
		 frameElement.dialog.close();
	}
    
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
        	<td align="right" class="l-table-edit-td"  >
				材料信息：
	        </td>
			<td align="left" class="l-table-edit-td">
				<input name="inv_code" id="inv_code" type="text" />
			</td>
        </tr>
        
    </table>

	<div id="maingrid"></div>
	<div id="resultPrint" style="display:none">
	   	<table width="100%">
			<thead>
				<tr>
	                <th width="200">配套表ID</th>	
	                <th width="200">配套表名称</th>	
	                <th width="200">仓库ID</th>	
	                <th width="200">科室ID</th>	
				</tr>
			 </thead>
			<tbody></tbody>
	   	</table>
   	</div>
</body>
</html>
