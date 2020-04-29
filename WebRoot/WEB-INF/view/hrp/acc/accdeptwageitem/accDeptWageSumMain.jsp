<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<title></title>
<link href="<%=path %>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="<%=path %>/lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<script src="<%=path %>/lib/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
<script src="<%=path %>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path %>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>
<script src="<%=path%>/lib/My97DatePicker/WdatePicker.js"	type="text/javascript"></script>

<link rel="stylesheet" href="<%=path %>/lib/Z-tree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="<%=path %>/lib/Z-tree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=path %>/lib/Z-tree/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="<%=path %>/lib/Z-tree/js/jquery.ztree.exedit-3.5.js"></script>
<script language="javascript" src="<%=path%>/lib/Lodop/LodopFuncs.js"></script>

<script src="<%=path%>/lib/et_components/et_plugins/etDatepicker.min.js" type="text/javascript"></script>
<script type="text/javascript">
	
    var grid;
    
    var rightgrid;
    
    var gridManager = null;
    
    var rightgridManager = null;
    
    var userUpdateStr;
    
	var para ="";
    
    var sumPara = "";
    
    var group_item = "";
    
	var tree_wage_code ="";
    
    var tree_wage_name ="";
    
    var scheme_code ="";
    
    var scheme_name ="";
    
    var tree;
    
    var acc_times;
    
    $(function ()
    {
    	
    	$("#layout1").ligerLayout({ centerWidth:790,onLeftToggle: function (isColl){grid._onResize();},onRightToggle: function (isColl){grid._onResize();}});
    	
    	loadDict(null);
    	
    	loadHead(null);	//加载数据
    	
    });
    
    //查询
    function  query(){
    	
    		grid.options.parms=[];
    		
    		grid.options.newPage=1;
    		
          	var acc_time= acc_times.getValue().split(".")[0];
          	
			var acc_month= acc_times.getValue().split(".")[1];
			
			var wage_code= liger.get("wage_code").getValue();
          	
          	if(wage_code == ""||acc_time ==""){
          		
          		$.ligerDialog.error('工资套和期间为必填项！');
        		
            	return;
          		
          	}
          	
            grid.options.parms.push({name:'item_code',value:para});
            
        	grid.options.parms.push({name:'wage_code',value:wage_code}); 
        	
        	grid.options.parms.push({name:'acc_year',value:acc_time}); 
        	
        	grid.options.parms.push({name:'acc_month',value:acc_month}); 
        	
	    	grid.options.parms.push({name:'dept_code',value:liger.get("dept_code").getValue().split(".")[0]}); 
	    	
	    	grid.set("url","queryAccDeptWageSum.do");
	    	
	    	grid.loadData(grid.where);
     
    }

    function loadHead(){
    	
    	var columns = "";

   		columns = columns +"{ display: '职工类别', name: 'KIND_NAME', align: 'left',frozen: true,render:function(rowdata){"
   		
   			+"return rowdata.TYPE_NAME=='合计'?'小计':rowdata.KIND_NAME"
   			
   		+"}},"
   		
       	+"{ display: '部门属性', name: 'TYPE_NAME', align: 'left',frozen: true,render:function(rowdata){"
   		
			+"return rowdata.TYPE_NAME=='合计'?'':rowdata.TYPE_NAME"
			
		+"}}";
    		
    	para="";
        	
        var item=0;
    	
    	$.ajax({
			
			type: "POST", 
			
            url: "../accwagepay/queryAccWagePayGrid.do?isCheck=false",
            
            data: {"wage_code":liger.get("wage_code").getValue(),"acc_year":acc_times.getValue().split(".")[0],"acc_month":acc_times.getValue().split(".")[1]},
            
            dataType: "json",
            
            async: false,
            
            success: function(data){

            	if(data.Rows.length>0){
            		
            		$.each(data.Rows,function(i,v){
            			
            			columns = columns + ",{ display: '"+v.ITEM_NAME+"', name: '"+v.COLUMN_ITEM+"',formatter: '###,##0.00', align: 'right',"+
            				
            			"render:function(rowdata){ return rowdata."+v.COLUMN_ITEM+">0?formatNumber(rowdata."+v.COLUMN_ITEM+",2,1):formatNumber('0',2,1)}},";
            				
            			 para+= ",to_char(sum(round(awp."+v.COLUMN_ITEM+",2))) as "+v.COLUMN_ITEM;
                			
            		});
            		
            	}
            	
            	if(para!=""){
            		
            		columns=columns.substring(0,columns.length-1);
            	}
            	
            }
		});

    	grid = $("#maingrid").ligerGrid({
    		
           columns:  eval("["+columns+"]"),
           
           dataAction: 'server',dataType: 'server',usePager:false,url:'',
                     
           width: '100%', height: '100%', checkbox: false,rownumbers:true,
           
           selectRowButtonOnly:true,delayLoad:true,columnWidth:'15%',minColumnWidth:100,
           
           toolbar: { items: [
						
						{ text: '查询', id:'search', click: query,icon:'search' },
						
						{ line:true } ,
						
						{ text: '打印', id:'export', click: myPrint,icon:'print' }
						
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
                	
                	var wage_code = liger.get("wage_code").getValue();
                	
                	if(wage_code ==""){
                		
                		$.ligerDialog.error('请选择工资套！');
                		
                    	return;
                		
                	}
                		
              		$.ligerDialog.open({url: '../accwagepay/accWagePayAddPage.do?isCheck=false&wage_code='+wage_code, height: 400,width: 590, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.save(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
              		
              		return;
                
            }   
        }
        
    }
    
    function openUpdate(obj,group_id,hos_id,copy_code){
    	
    	var vo = obj.split("|");
    	
		var parm = "type_id="+
			vo[0]   +"&group_id="+ 
			vo[1]   +"&hos_id="+ 
			vo[2]   +"&copy_code="+ 
			vo[3];

    	$.ligerDialog.open({ url : 'accWageTypeUpdatePage.do?isCheck=false&' + parm,data:{}, height: 500,width: 500, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAccBank(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    }
    
    function loadDict(){
        //字典下拉框

       var fromData={
        		
        		wage_code:'0000'
        
        }

    	autocomplete("#scheme_name","../queryAccWageScheme.do?isCheck=false","id","text",true,true,fromData);
        
   		//autocomplete("#wage_code","../queryAccWage.do?isCheck=false","id","text",true,true,'',true);
        
   		autocompleteObj({
    		id:"#wage_code",
    		urlStr:"../queryAccWage.do?isCheck=false",
    		valueField:"id",
    		textField:"text",
    		autocomplete:true,
    		highLight:true,
    		parmsStr:null,
    		defaultSelect:true,
    		initvalue:null,
    		initWidth:"160",initHeight:null,boxwidth:null,alwayShowInDown:null,selectEvent:function (value){
    			
    			loadHead();

			}
    	});
   		
	  	acc_times = $("#acc_time").etDatepicker({
	         view: "months",
	         minView: "months",
	         dateFormat: "yyyy.mm",
	         defaultDate: true,
			});
   		
        autocomplete("#dept_code","../queryDeptDictNo.do?isCheck=false&is_stop=0","id","text",true,true);
        
		var year_Month = '${wage_year_month}';
		
		if(year_Month.toString()=="000000"){
			
			var date=new Date;
			
			 var year=date.getFullYear();
			 
			 var month=date.getMonth()+1;
			 
			 month =(month<10?"0"+month:month); 
			 
			 year_Month = (year.toString()+month.toString());
			
		}
		
	    acc_times.setValue(year_Month.substring(0,4)+"."+year_Month.substring(4,6).toString());
    	 
     } 
    
	function myPrint(){
    	
    	if(grid.getData().length==0){
    		
			$.ligerDialog.error("请先查询数据！");
			
			return;
		}
    	
       	var printPara={
         		title:  acc_times.getValue().split(".")[0]+"年"+acc_times.getValue().split(".")[1]+"月"+liger.get("wage_code").getText()+"工资部门汇总",//标题
         		columns: JSON.stringify(grid.getPrintColumns()),//表头
         		class_name: "com.chd.hrp.acc.service.wagedata.AccDeptWageItemService",
      			method_name: "queryAccDeptWageSumPrint",
      			bean_name: "accDeptWageItemService"
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
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
	<div id="layout1">
            <div position="center"  title="  ">
			    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
			       <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>期间：</td>
			            <td align="left" class="l-table-edit-td"><input name="acc_time" type="text" id="acc_time" ltype="text" validate="{required:true,maxlength:20}" /></td>
			       		<td align="left"></td>
			       		<td align="right" class="l-table-edit-td"  style="padding-left:20px;">工资套：</td>
			            <td align="left" class="l-table-edit-td"><input name="wage_code" type="text" id="wage_code" ltype="text" validate="{required:true,maxlength:20}"/></td>
			            <td align="left"></td>
			       		<td align="right" class="l-table-edit-td"  style="padding-left:20px;">部门：</td>
			       		<td align="left" class="l-table-edit-td"><input name="dept_code" type="text" id="dept_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
			       </tr>  
			    </table>
			    <div id="maingrid"></div>
			</div>
	</div>
</body>
</html>
