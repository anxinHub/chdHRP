<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
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
		loadDict();
    	
    	loadHead(null);	//加载数据
    
    });
    //查询
    function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	 grid.options.parms.push({name:'year_month_begin',value:$("#year_month_begin").val()}); 
 	      grid.options.parms.push({name:'year_month_end',value:$("#year_month_end").val()});
 	     /* grid.options.parms.push({
				name: 'source_attr_2',
				value: $("#source_attr_2").prop("checked") ? 1 : 0
			});
			grid.options.parms.push({
				name: 'source_attr_3',
				value: $("#source_attr_3").prop("checked") ? 1 : 0
			});
			grid.options.parms.push({
				name: 'source_attr_4',
				value: $("#source_attr_4").prop("checked") ? 1 : 0
			}); */
 	      //加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '排序', name: 'id', align: 'center',width:'10%',
                    	 render : function(rowdata, rowindex,value) {
            	        		if(rowdata.id == null){
            	        			return "合计";
            	        		}else {
            	        			return rowdata.id;
            	        		}
                    	 }
                     },
                     
					 {display:'科室名称',name:'dept_name_hb',align:'left'},
					 { display: '本期', name: '', align: 'center',
                    	 columns:[
									{display:'医疗收入',name:'t_1',align:'right',
	                    	        	  render : function(rowdata, rowindex,
													value) {
										 	return formatNumber(rowdata.t_1,2,1);
										}
                      				},
                      				{display:'医疗成本',name:'t_2',align:'right',
                      	        	  render : function(rowdata, rowindex,
  												value) {
  									 	return formatNumber(rowdata.t_2,2,1);
  									}},
  									{display:'医疗收益',name:'t_3',align:'right',
                        	        	  render : function(rowdata, rowindex,
    												value) {
    									 	return formatNumber(rowdata.t_3,2,1);
    									}}
                    	         ]
					 },
					 {display:'科室名称',name:'dept_name',align:'left'},
                     { display: '累计', name: '', align: 'center',
                    	 columns:[
									{display:'医疗收入',name:'t_4',align:'right',
	                    	        	  render : function(rowdata, rowindex,
													value) {
										 	return formatNumber(rowdata.t_4,2,1);
										}
									},
                      				{display:'医疗成本',name:'t_5',align:'right',
                      	        	  render : function(rowdata, rowindex,
  												value) {
  									 	return formatNumber(rowdata.t_5,2,1);
  									}},
  									{display:'医疗收益',name:'t_6',align:'right',
                        	        	  render : function(rowdata, rowindex,
    												value) {
    									 	return formatNumber(rowdata.t_6,2,1);
    									}}
                    	         ]
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAnalysisC0622.do?isCheck=false',
                     width: '100%', height: '100%', checkbox: false,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
                     	{ text: '打印', id:'print', click: print,icon:'print' },
                     	{ line:true }
    				]}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function createData(){
  	   
    }
    function printData(){
 	   
    }
    
     function loadDict(){
    	 $("#year_month_begin").ligerTextBox({ width:120 });
    	 $("#year_month_end").ligerTextBox({ width:120 });  	
   	    autodate("#year_month_begin","yyyyMM");
   	    autodate("#year_month_end","yyyyMM");
 	}   
     
     function print(){
      	
      	if(grid.getData().length==0){
      		
  			$.ligerDialog.error("请先查询数据！");
  			
  			return;
  		}
      	
      	var selPara={};
      	
      	$.each(grid.options.parms,function(i,obj){
      		
      		selPara[obj.name]=obj.value;
      		
      	});
     		
  		var dates = getCurrentDate();
      	
      	var cur_date = dates.split(";")[2];
      	//跨所有列:计算列数
      	var colspan_num = grid.getColumns(1).length-1;
     		
      	var printPara={
         			title:'临床医疗收益排序表',
         			head:[
      				{"cell":0,"value":"单位: ${sessionScope.hos_name}","colspan":colspan_num,"br":true},
      				{"cell":0,"value":
      					"期间: " + $("#year_month_begin").val()+
      					" 至  "+ $("#year_month_end").val(),
      				"colspan":colspan_num,"br":true}
         			],
         			foot:[
      				{"cell":0,"value":"主管:","colspan":2,"br":false} ,
  					{"cell":2,"value":"复核人:","colspan":colspan_num-2,"br":true},
  					{"cell":0,"value":"制单人： ${sessionScope.user_name}","colspan":2,"br":false},
  					{"cell":2,"value":"打印日期: " + cur_date,"colspan":colspan_num-2,"br":true}
         			],
         			columns:grid.getColumns(1),
         			headCount:2,//列头行数
         			autoFile:true,
         			type:3
         	};
     		ajaxJsonObjectByUrl("queryAnalysisC0622	.do?isCheck=false", selPara, function (responseData) {
     			printGridView(responseData,printPara);
  		});

     		
      }
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">期间：</td>
            <td align="left" class="l-table-edit-td"><input class="Wdate" name="year_month_begin" type="text" id="year_month_begin" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})" ltype="text"/></td>
            <td align="left" class="l-table-edit-td">至：</td>
            <td align="left" class="l-table-edit-td"><input class="Wdate" name="year_month_end" type="text" id="year_month_end" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})" ltype="text"/></td>
<!-- <td align="center" class="l-table-edit-td"><input name="source_attr_2" type="checkbox" id="source_attr_2" ltype="text" /> 包含财政
	<td align="center" class="l-table-edit-td"><input name="source_attr_3" type="checkbox" id="source_attr_3" ltype="text" /> 包含科研
		<td align="center" class="l-table-edit-td"><input name="source_attr_4" type="checkbox" id="source_attr_4" ltype="text" /> 包含教学
		 -->	
        </tr> 
    </table>

	<div id="maingrid"></div>
</body>
</html>
