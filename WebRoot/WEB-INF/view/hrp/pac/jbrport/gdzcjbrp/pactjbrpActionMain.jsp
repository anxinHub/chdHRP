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
    $(function(){
    	
    	 loadDict();//加载下拉框
     	//加载数据
     	loadHead(null);	
 		 //loadHotkeys();
    	
    });
    //查询
    function query(){
    	
    	grid.options.parms=[];
    	grid.options.newPage=1;
    	
    	
    	//根据表字段进行添加查询条件
    	if(!isnull($("#plan_year").val())){
   			grid.options.parms.push({name:'plan_year',value:$("#plan_year").val()}); 
    	}else{
    		
    	}
    	//加载查询条件
    	grid.loadData(grid.where);
    };
       function loadHead(){
    	   grid=$("#maingrid").ligerGrid({
    		   
    		   columns:[{display: 'ID', name: 'rs_rowid', align: 'right',width:'150',hide:true}, 
    		            {
    			            
    		            	display: '本年累计', name: 'add_money', align: 'right',width:'20%',
    		            	columns:[
    		            	         { display: '本年累计采购/合同金额', name: 'htmoney', align: 'right',width:'150',render:changeValue
    		            , editor: { type: 'float' }},
    		            	         { display: '本年累计降本金额', name: 'ljjbmoney', align: 'right',width:'100',render:changeValue,type: 'float', editor: { type: 'float' }},
    		            	         { display: '降本比率%', name: 'jbbl', align: 'right',width:'100',render:toPercent,type: 'float', editor: { type: 'float' }},
    		            	         { display: '本年累计集采金额', name: 'planmoney', align: 'right',width:'100',render:changeValue,type: 'float', editor: { type: 'float' }},
    		            	         { display: '集采占比%', name: 'cjzb', align: 'right',width:'100',render:toPercent,type: 'float', editor: { type: 'float' }},
    		            	         ]
    		            },{
    		            	display: '全年预算', name: 'planadd_money', align: 'right',width:'20%',
    		            	columns:[
                                     { display: '降本金额', name: 'budgmoney', align: 'right',width:'100',render:changeValue,type: 'float', editor: { type: 'float' }},
                                     { display: '完成%', name: 'wczb', align: 'right',width:'100',render:toPercent,type: 'float'},
    		            	         ]
    		            }
    		            ],
    		            dataAction:'server',
    		            dataType:'server',
    		            usePager:true,
    		            url:'queryPactAlteration.do',  //查询调用方法
    		            delayLoad:true,
                        width: '100%', 
                        height: '100%', 
                        checkbox: true,
                        rownumbers:true,
                        //selectRowButtonOnly:true,//heightDiff: -10,
                        enabledEdit: true,
                        isAddRow:false ,
                        onAfterEdit : f_onAfterEdit,
                        toolbar: { items : [ { text: '查   询', id:'search', click: query,icon:'search' },
                                             { line:true },
                         					{ text: '保 存', id:'add', click: save, icon:'add' }
   				    				
   				    				]}
    		   
    	   });
    	   gridManager = $("#maingrid").ligerGetGridManager();
       }
       function changeValue(rowData,index,value){
    	   return formatNumber(value,2,1);
       }
       function toPercent(rowData,index,value) { 
         if (value==null){
        	 
        	 return (Math.round(0 * 10000) / 100.00 + "%");// 小数点后两位百分比
        	 
         }else{
        	 return (Math.round(value * 10000) / 100.00 + "%");// 小数点后两位百分比
        	 
         }
    	   
    	   
    	}
    // 跳转到下一个单元格之前事件
   	function f_onAfterEdit(e) {
    	
   		if(e.value != "" && e.value != 0){
   			
   			if(e.column.name == "budgmoney"){
   				if(e.record.ljjbmoney != undefined && e.record.ljjbmoney != "" && e.record.ljjbmoney != 0){
					grid.updateCell('jbbl',  e.record.ljjbmoney /e.value, e.rowindex);
					grid.updateCell('wczb',  e.value /e.value, e.rowindex);
				}
				}
   			if(e.column.name == "planmoney"){
   				if(e.record.htmoney != undefined && e.record.htmoney != "" && e.record.htmoney != 0){
					grid.updateCell('cjzb', e.value / e.record.htmoney , e.rowindex);
				}
				}
   		}
    	
    	
    }
       function  save(){
   	        
    	   //var param = [];
    	   var allData = gridManager.getData();
   	       if(allData.length == 0){
   	    	   
   			    $.ligerDialog.error('请添加数据');
   			        return ;
   		    }
            var rs_rowid=allData[0].rs_rowid;
		    var formPara = {
		       plan_year:$('#plan_year').val(),
			   //allData : JSON.stringify(allData)//获取所有数据
			   htmoney : allData[0].htmoney,
			   ljjbmoney : allData[0].ljjbmoney,
			   planmoney : allData[0].planmoney,
			   cjzb : allData[0].cjzb,
			   budgmoney : allData[0].budgmoney,
			   jbbl : allData[0].jbbl,
			   wczb : allData[0].wczb,
			   rs_rowid:rs_rowid
		      };
         
		     if (rs_rowid>0){
		    	 ajaxJsonObjectByUrl("updataJbRtMain.do?isCheck=false", formPara,function(responseData) {
						if (responseData.state == "true") {
									query();
									
						}
					});
		    	 
		     }
		     else {
		    ajaxJsonObjectByUrl("addJbRtMain.do?isCheck=false", formPara,function(responseData) {
				if (responseData.state == "true") {
							query();
							
				}
			})
		     }
       }
        
       function loadDict(){
       		autodate("#plan_year","yyyy","new")
   		
       }
    </script>
</head>
<body style="padding: 0px; overflow: hidden;">
<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table class="table-layout">
    <tr>
    <td align="right" class="l-table-edit-td"  style="padding-left:20px;">年度：</td>
              <td align="left" class="l-table-edit-td"><input name="plan_year" type="text" id="plan_year" ltype="text" validate="{required:true,maxlength:20}" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy'})"/></td>
   </tr>
    </table>
    <div id="maingrid"></div>
</body>
</html>