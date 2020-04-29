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
    	  grid.options.parms.push({name:'year_month_end',value:$("#year_month_end").val()}); 
    	  grid.options.parms.push({name:'year_month_begin',value:$("#year_month_begin").val()});   
    	  grid.options.parms.push({name:'dept_code',value:liger.get("dept_code").getValue()}); 
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                    {display:'科室编码',name:'dept_code',align:'left'},
					{display:'科室名称',name:'dept_name',align:'left'},
                     { display: '医疗收入', name: 't_1', align: 'center',width:'20%',
                         			 render : function(rowdata, rowindex,
	     										value) {
	     									 return formatNumber(rowdata.t_1,2,1);
	     									}
					 },
					 { display: '医疗全成本', name: 't_2', align: 'center',width:'20%',
             			 render : function(rowdata, rowindex,
									value) {
								 return formatNumber(rowdata.t_2,2,1);
								}
					 },
					 { display: '医疗收益', name: 't_3', align: 'center',width:'20%',
             			 render : function(rowdata, rowindex,
									value) {
								 return formatNumber(rowdata.t_3,2,1);
								}
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAnalysisC1001.do',
                     width: '100%', height: '100%', checkbox: false,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
                     	/* { text: '生成', id:'add', click: itemclick,icon:'add' },
                     	{ line:true }, */
                     	{ text: '打印', id:'print', click: print,icon:'print' },
                     	{ line:true }
    				]}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function itemclick(item){
    	if(item.id)
        {
            switch (item.id)
            {
                case "add":
                	
    				$.ligerDialog.open({url: 'analysisC1001AddPage.do?isCheck=false', height: 300,width: 400, title:'生成',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveCostCreateData(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
              		return;
            }   
        }
    }
function print(){
      	
      	if(grid.getData().length==0){
      		
  			$.ligerDialog.error("请先查询数据！");
  			
  			return;
  		}
      	
      	var heads={
 	    		//"isAuto":true,//系统默认，页眉显示页码
 	    		"rows": [
 		          {"cell":0,"value":"统计日期："+$("#year_month_begin").val()+"至"+$("#year_month_end").val(),"colSpan":"5"}
 	    	]};
 	       var printPara={
 	      		title: "医院成本绩效分析",//标题
 	      		columns: JSON.stringify(grid.getPrintColumns()),//表头
 	      		class_name: "com.chd.hrp.cost.service.analysis.c10.C10Service",
 	   			method_name: "queryC1001Print",
 	   			bean_name: "c10Service",
 	   		    heads: JSON.stringify(heads)//表头需要打印的查询条件,可以为空
 	   			
 	       	};
 	      //执行方法的查询条件
 		  $.each(grid.options.parms,function(i,obj){
 			printPara[obj.name]=obj.value;
  	      });
 		
  	     officeGridPrint(printPara);
     		
      }
    
    
     function loadDict(){
    	 autocomplete("#dept_code","../../queryDeptDictNo.do?isCheck=false","id","text",true,true);
    	 $("#year_month_begin").ligerTextBox({width:120});
      	$("#year_month_end").ligerTextBox({width:90});
      /* 	$("#acc_month2").ligerTextBox({width:90}); */
      	autodate("#year_month_begin","yyyyMM");
      	autodate("#year_month_end","yyyyMM");
 	}     
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
           <!-- <td align="right" class="l-table-edit-td"  style="padding-left:20px;">期间：</td>
            <td align="left" class="l-table-edit-td" ><input name="begin_date" type="text" id="begin_date" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
            <td align="right" class="l-table-edit-td" style="padding-left:5px;">至：</td>
            <td align="left" class="l-table-edit-td"><input name="end_date" type="text" id="end_date" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
            <td align="left"></td> -->
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">期间：</td>
           <td align="left" class="l-table-edit-td"><input name="year_month_begin" type="text" id="year_month_begin" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
	 	   <td align="right" class="l-table-edit-td"  style="padding-left:20px;">至</td>
           <td align="left" class="l-table-edit-td"><input name="year_month_end" type="text" id="year_month_end" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>

          <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_code" type="text" id="dept_code" /></td>
            <td align="left"></td>
        </tr> 
    </table>

	<div id="maingrid"></div>
</body>
</html>
