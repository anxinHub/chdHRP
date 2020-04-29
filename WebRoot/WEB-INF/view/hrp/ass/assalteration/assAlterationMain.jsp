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
        loadDict();//加载下拉框
    	//加载数据
    	loadHead(null);	
		 loadHotkeys();

    });
    //查询
    function  query() {
    		grid.options.parms=[];
    		grid.options.newPage=1;

        	console.log(grid.options.parms)
        //根据表字段进行添加查询条件
        	if(!isnull($("#plan_year").val())){
       		 grid.options.parms.push({name:'year_month',value:$("#plan_year").val()}); 
       	}
    	/*   grid.options.parms.push({name:'acc_year',value:$("#ass_year").val()}); 
    	  grid.options.parms.push({name:'acc_month',value:$("#ass_month").val()});  */
        	if(!isnull($("#type_level").val())){
          	  grid.options.parms.push({name:'type_level',value:$("#type_level").val()}); 
          	}
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
	        	     { display: '类别编码', name: 'ass_type_code', align: 'left',width:'100'
			 		 },
                     { display: '类别名称', name: 'ass_type_name', align: 'left',width:'100'
					 		},
                     { display: '期初金额', name: 'begin_money', align: 'right',width:'20%',
					 			columns:[
					 				{ display: '期初合计', name: 'begin_sum_money', align: 'right',width:'100' ,formatter:'###,##0.00'},
					 				{ display: '自筹资金', name: 'begin_self_money', align: 'right',width:'100',formatter:'###,##0.00' },
					 				{ display: '财政资金', name: 'begin_finance_money', align: 'right',width:'100',formatter:'###,##0.00' },
					 				{ display: '科研资金', name: 'begin_research_money', align: 'right',width:'100',formatter:'###,##0.00' },
					 				{ display: '教学资金', name: 'begin_teach_money', align: 'right',width:'100',formatter:'###,##0.00' }
					 			]
					 		},
                     { display: '本期增加金额', name: 'add_money', align: 'right',width:'20%',
					 			columns:[
					 				{ display: '合计', name: 'this_add_sum_money', align: 'right',width:'100',formatter:'###,##0.00'},
					 				{ display: '自筹资金', name: 'this_add_self_money', align: 'right',width:'100',formatter:'###,##0.00'},
					 				{ display: '财政资金', name: 'this_add_finance_money', align: 'right',width:'100',formatter:'###,##0.00' },
					 				{ display: '科研资金', name: 'this_add_research_money', align: 'right',width:'100',formatter:'###,##0.00' },
					 				{ display: '教学资金', name: 'this_add_teach_money', align: 'right',width:'100',formatter:'###,##0.00' }
					 			]
					 		},
                     { display: '本期减少金额', name: 'dec_money', align: 'right',width:'20%',
					 			columns:[
					 				{ display: '合计', name: 'this_decrease_sum_money', align: 'right',width:'100',formatter:'###,##0.00' },
					 				{ display: '自筹资金', name: 'this_decrease_self_money', align: 'right',width:'100',formatter:'###,##0.00' },
					 				{ display: '财政资金', name: 'this_decrease_finance_money', align: 'right',width:'100',formatter:'###,##0.00' },
					 				{ display: '科研资金', name: 'this_decrease_research_money', align: 'right',width:'100',formatter:'###,##0.00' },
					 				{ display: '教学资金', name: 'this_decrease_teach_money', align: 'right',width:'100',formatter:'###,##0.00' }
					 			]
				 		},
					 { display: '期末金额', name: 'remain_end_money', align: 'left',width:'20%',
				 			columns:[
				 				{ display: '期末合计', name: 'end_sum_money', align: 'right',width:'100',formatter:'###,##0.00' },
				 				{ display: '自筹资金', name: 'end_self_money', align: 'right',width:'100',formatter:'###,##0.00' },
				 				{ display: '财政资金', name: 'end_finance_money', align: 'right',width:'100',formatter:'###,##0.00' },
				 				{ display: '科研资金', name: 'end_research_money', align: 'right',width:'100',formatter:'###,##0.00' },
				 				{ display: '教学资金', name: 'end_teach_money', align: 'right',width:'100',formatter:'###,##0.00' }
				 			]
					 		}
                     ],
                     dataAction: 'server',
                     dataType: 'server',
                     usePager:true,
                     url:'queryAssAlteration.do',
                     delayLoad:true,
                     width: '100%', 
                     height: '100%', 
                     checkbox: true,
                     rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items : [ { text: '查   询', id:'search', click: query,icon:'search' },
              						     { line:true },
             						     { text: '打   印', id:'print', click: printDate,icon:'print' },
             						     { line:true },
				    				]}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
  
    function loadDict(){
    	
		var param = {
            	query_key:''
        };
		
            //字典下拉框
		
		autocomplete("#ass_nature", "../queryAssNaturs.do?isCheck=false","id", "text",true,true,param,true);
		
        autocomplete("#ass_type_id", "../queryAssTypeDict.do?isCheck=false","id", "text",true,true,param,true);
	      
		  $("#plan_year").ligerTextBox({width:100}); 

        if(${ass_year_month}){
			$("#plan_year").val('${ass_year_month }')
        }else{
        	autodate("#plan_year","YYYYMM"); 
        }
		$("#type_level").ligerComboBox({
	           width : 120,
	           data: [
	               { text: '1', id: '1' },
	               { text: '2', id: '2' },
	               { text: '3', id: '3' }
	           ]
	       });
		
         }  
    //键盘事件
	  function loadHotkeys() {

		hotkeys('Q', query);
		hotkeys('P', printDate);
		
	 }
  
	//打印数据
	 	function printDate(){
	 		if(grid.getData().length==0){
	   			$.ligerDialog.error("请先查询数据！");
	   			return;
	   		}
	 		
	 		var time=new Date();
	    	var date=time.getFullYear()+"年"+(time.getMonth()+1)+"月"+time.getDate()+"日";
	    	var heads={
	        		"isAuto":true,//系统默认，页眉显示页码
	        		"rows": [
	    	          {"cell":0,"value":"会计期间："},
	    	          {"cell":1,"value":$("#plan_year").val()},
	    	          {"cell":20,"value":"制表日期:"},
	  				  {"cell":21,"value":date} ,
	    	          
	        	]}; 
	    	//表尾
	    	var foots = {
	    			rows: [
	    				{"cell":20,"value":"制表人:"},
	    				{"cell":21,"value":"${user_name}"},
	    			]
	    		}; 
	 		var printPara={
	 				title: "资产变动表",//标题
	 				columns: JSON.stringify(grid.getPrintColumns()),//表头
	 				class_name: "com.chd.hrp.ass.service.assalteration.AssAlterationService",
	 				method_name: "queryAssAlterationMainPrint",
	 				bean_name: "assAlterationService" ,
	 				heads: JSON.stringify(heads), //表头需要打印的查询条件,可以为空
	 				foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 
	 				};
	 		
	 		$.each(grid.options.parms,function(i,obj){
	 				printPara[obj.name]=obj.value;
	 		});
	 		
	 		officeGridPrint(printPara);
	 	   		
	 	}
	 
	 
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        
        <tr>
             <td align="right" class="l-table-edit-td"  style="padding-left:20px;">会计期间：</td>
              <td align="left" class="l-table-edit-td"><input name="plan_year" type="text" id="plan_year" ltype="text" validate="{required:true,maxlength:20}" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
            <td align="left"></td>
           
			<td align="left" ></td>
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产分类汇总到级次：</td> 
            <td align="left" class="l-table-edit-td">
            	<!-- <select name="type_level" id="type_level">
            		<option value=""></option>
            		<option value="1">1</option>
            		<option value="2">2</option>
            		<option value="3">3</option> 
            	</select> -->
            	<input name="type_level" type="text" id="type_level"/>
            </td>
        </tr> 
       
    </table>

	<div id="maingrid"></div>
</body>
</html>
