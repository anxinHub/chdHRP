<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>临床科室收入分析构成表(开单科室)</title> 
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
 
		var grid;
		var gridManager = null;
		
		
      $(function(){
    	  loadHead(null);//加载数据
    	  loadDict();
      });

      //查询
      function query(){

    	  grid.options.parms=[];
    	     grid.options.newPage=1; 
    	   //根据表字段进行添加查询条件，获取年份和月份
     	 grid.options.parms.push({name:'year_month_begin',value:$("#year_month_begin").val()}); 
     	 grid.options.parms.push({name:'year_month_end',value:$("#year_month_end").val()}); 
     	 grid.options.parms.push({name:'appl_dept_code',value:liger.get("appl_dept").getValue().split(".")[2]}); 
    	 grid.options.parms.push({name:'exec_dept_code',value:liger.get("exec_dept").getValue().split(".")[2]}); 
    	 grid.options.parms.push({name:'dept_level',value:liger.get("dept_level").getValue()}); 
	       	//加载查询条件
	     grid.loadData(grid.where);
      };


           
      function loadDict(){

     	 var appl_param = {
          		type_code:"('01','02')"
          };
          var exec_param = {
          		type_code:"('01','02')"
          };
  		 autocomplete("#appl_dept","../queryDeptDictCode.do?isCheck=false","id","text",true,true,appl_param);
  		 autocomplete("#exec_dept","../queryDeptDictCode.do?isCheck=false","id","text",true,true,exec_param);  
  		 autocomplete("#dept_level","../queryHosDeptLevel.do?isCheck=false","id","text",true,true);  
           
    	  //年月的初始化
    	  autodate("#year_month_begin","yyyyMM");
    	  autodate("#year_month_end","yyyyMM");
    	  $("#year_month_begin").ligerTextBox({width:120});
          $("#year_month_end").ligerTextBox({width:120});
          $(':button').ligerButton({ width: 80 });
          
       };
      
		function loadHead(){
			initGrid();
			loadColumns();
		}
		function initGrid(){
				grid =  $("#maingrid").ligerGrid({
			           dataAction: 'server',dataType: 'server',usePager:true,url:'queryCostClinicalDeptIncomeAnalysisAppl.do',
			           height: '100%', checkbox:false,rownumbers:true,delayLoad :true,
			           selectRowButtonOnly:true,
			           });
		           gridManager = $("#maingrid").ligerGetGridManager();
			}
		
		function loadColumns(){
			
			  var columns = [];
			  columns = [{ display: '开单科室编码', name: 'appl_dept_code', align: 'left',frozen:true,minWidth:120,
				             render:function(rowdata, rowindex,value) {
					            return rowdata.appl_dept_code==9999?"":rowdata.appl_dept_code
							 }
				          },
				         {display: '开单科室名称', name: 'appl_dept_name', align: 'left',frozen:true,minWidth:120},
					         ]
              var para = {}
			  ajaxJsonObjectByUrl("queryCostClinicalDeptIncomeItemColumns.do?isCheck=false",para,function(responseData){
					//console.log(JSON.stringify(responseData))
					$.each(responseData.Rows,function(i,obj){
                    columns.push({
                    	  display:obj.income_item_name,
                    	  name:"item"+obj.income_item_code,
                    	  minWidth:'120',
                    	  align :'right',
                    	  render:function(rowdata, rowindex,value) {return formatNumber(value,2,1)}
                        })
					});	
				},false);	

			  columns.push({
            	  display:"合计",
            	  name:"item9999",
            	  minWidth:'120',
            	  align :'right',
            	  render:function(rowdata, rowindex,value) {return formatNumber(value,2,1)}
                })		
			   grid.set('columns', columns);
			}

	function print(){
		   var time = new Date(); //获得当前时间
		   var year = time.getFullYear();//获得年、月、日
		   var month = time.getMonth()+1;
		   var day = time.getDate();  
		   var date = year+"年"+month+"月"+day;	
		   
        if(grid.getData().length==0){
    		
			$.ligerDialog.error("请先查询数据！");
			
			return;
		}

    	 var heads={
 	    		"isAuto":true,//系统默认，页眉显示页码
 	    		"rows": [
                  {"cell":0,"value":"统计年月："},
                  {"cell":1,"value":""+ $("#year_month_begin").val() +"至"+$("#year_month_end").val()},
 	    	]}; 
    	 	//表尾
			var foots = {
				/* rows: [
					{"cell":0,"value":"制单日期:"} ,
					{"cell":1,"value":date} ,
				] */
			}; 
 	       var printPara={
 	      		title: "临床科室收入分析构成表(开单科室)",//标题
 	      		columns: JSON.stringify(grid.getPrintColumns()),//表头
 	      		class_name: "com.chd.hrp.cost.service.analysis.AnalysisService",
 	   			method_name: "queryCostClinicalDeptIncomeAnalysisApplPrint",
 	   			bean_name: "analysisService",
 	   		    heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
 	   		    foots: JSON.stringify(foots),//表尾需要打印的查询条件,可以为空 
 	   			
 	       	};
 	      //执行方法的查询条件
 		  $.each(grid.options.parms,function(i,obj){
 			printPara[obj.name]=obj.value;
  	      });
 		
  	     officeGridPrint(printPara);
	}
</script>
</head>
<body style="padding: 0px; overflow: hidden;">
	 <div id="toptoolbar" ></div>
	 <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	 	<tr>
	 	   <td align="right" class="l-table-edit-td"  style="padding-left:20px;">期间：</td>
           <td align="left" class="l-table-edit-td"><input name="year_month_begin" type="text" id="year_month_begin" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
	 	   <td align="right" class="l-table-edit-td"  style="padding-left:20px;">至</td>
           <td align="left" class="l-table-edit-td"><input name="year_month_end" type="text" id="year_month_end" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
           <td align="right" class="l-table-edit-td"  style="padding-left:20px;">开单科室：</td>
           <td align="left" class="l-table-edit-td"><input name="appl_dept" type="text" id="appl_dept" /></td>
           <td align="right" class="l-table-edit-td"  style="padding-left:20px;">执行科室：</td>
           <td align="left" class="l-table-edit-td"><input name="exec_dept" type="text" id="exec_dept" /></td>
           <td align="left" class="l-table-edit-td"><input type="button" value=" 查询" onclick="query();" /></td>
           <td align="left" class="l-table-edit-td"><input type="button" value=" 打印" onclick="print();" /></td>
	 	</tr>
	 	<tr>	 	           
           <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室级次：</td>
           <td align="left" class="l-table-edit-td" colspan="4"><input name="dept_level" type="text" id="dept_level" /></td>
	 	</tr>
	 </table>
	 <div id="maingrid" style="margin:0; padding:0"></div>
</body>
</html>