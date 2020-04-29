<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<jsp:include page="${path}/inc.jsp"/>
<script src="/CHD-HRP/lib/hrp/acc/internetbank/icbc/common.js" type="text/javascript"></script>
<link rel="stylesheet" href="<%=path %>/lib/Z-tree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="<%=path %>/lib/Z-tree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=path %>/lib/Z-tree/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="<%=path %>/lib/Z-tree/js/jquery.ztree.exedit-3.5.js"></script>
<script type="text/javascript">
	
    var grid;
    
    var gridManager = null;

    var year_Month = '${sessionScope.wage_year_month}';
	
	if(year_Month.toString()=="000000"){
		
		var date=new Date;
		
		var year=date.getFullYear();
		 
		var month=date.getMonth()+1;
		 
		month =(month<10 ? "0"+month:month); 
		 
		year_Month = (year.toString()+month.toString());
		
	}
    
    var wage_code;//工资套全局变量
    
    var tree;
    
    var setting = {   
    		
		data: {
			simpleData: {
				enable: true,
				idKey: "id",
				pIdKey: "pId",
				rootPId: 0
				
			},
		    key: {
				children: "nodes"
			}
		},
		check: {
			enable: false
		}, 
		treeNode:{
			open:true
		},
		callback:{
			onClick: zTreeOnClick
		}                

   }; 
    
    $(function ()
    {
    	loadDict(null);
    	
    	loadHead(null);	//加载数据
    	
    	loadTree(null);
    	
    	$("#layout1").ligerLayout({leftWidth: 200,centerWidth:750,onLeftToggle: function (isColl){grid._onResize();},onRightToggle: function (isColl){}}); 
    	
    });
    
    function zTreeOnClick(event, treeId, treeNode) {
       	
		if(wage_code !='0'){
      		
			wage_code = treeNode.id;

	      	var acc_time= liger.get("acc_time").getValue();
	
			//grid.options.parms.push({name:'wage_code',value:wage_code});
	    	
	    	//grid.options.parms.push({name:'acc_year',value:acc_time.split(".")[0]}); 
	    	
	    	//grid.options.parms.push({name:'acc_month',value:acc_time.split(".")[1]}); 
	    	
	    	//加载查询条件
	    	//grid.loadData(grid.where);
			
			var fromData={
	        		
	        		wage_code:wage_code,
	        		
	        		acc_year:year_Month.substring(0,4)
	        		
	    	} 
	    	
	        autocomplete("#item_code","../queryAccWageItem.do?isCheck=false&is_stop=0","id","text",true,true,fromData,true,false,'180');		
      	}

    };
    
    //查询
    function  query(){//根据表字段进行添加查询条件
    	
		grid.options.parms=[];grid.options.newPage=1;

      	if(!wage_code){
      		
			$.ligerDialog.warn('请选择左侧工资套！');
    		
    		return false;
      	}
      	
      	var acc_time= liger.get("acc_time").getValue();
      	
		if(!acc_time){
    		
    		$.ligerDialog.warn('请选择会计区间！');
    		
    		return false;
    		
    	}
		
		var item_code= liger.get("item_code").getValue();
      	
		if(!item_code){
    		
    		$.ligerDialog.warn('请选择工资项名称！');
    		
    		return false;
    		
    	}
      		
		grid.options.parms.push({name:'wage_code',value:wage_code});
		
		grid.options.parms.push({name:'item_code',value:item_code});
		
		grid.options.parms.push({name:'emp_kind_code',value:liger.get("kind_code").getValue()}); 
      	
      	grid.options.parms.push({name:'emp_code',value:liger.get("emp_code").getValue()}); 
    	
    	grid.options.parms.push({name:'acc_year',value:acc_time.split(".")[0]}); 
    	
    	grid.options.parms.push({name:'acc_month',value:acc_time.split(".")[1]});
    	
    	grid.options.parms.push({name:'is_city_same',value:liger.get("is_city_same").getValue()}); 
    	
    	grid.options.parms.push({name:'is_bank_same',value:liger.get("is_bank_same").getValue()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     
    }

	function myPrint(){
    	
    	if(grid.getData().length==0){
    		
			$.ligerDialog.error("请先查询数据！");
			
			return;
		}
    	
    	var printPara={
           		title: "工资支付查询",//标题
           		columns: JSON.stringify(grid.getPrintColumns()),//表头
           		class_name: "com.chd.hrp.acc.service.InternetBank.AccBankNetWageService",
        		method_name: "queryAccBankNetWagePrint",
        		bean_name: "accBankNetWageService"
          			
             };
           	
           	//执行方法的查询条件
          	$.each(grid.options.parms,function(i,obj){
          			printPara[obj.name]=obj.value;
           	});
          		
           	officeGridPrint(printPara);
    }
  	
    function pay(){
    	
    	var acc_time= liger.get("acc_time").getValue();
    	
		if(!acc_time){
    		
    		$.ligerDialog.warn('请选择会计区间！');
    		
    		return false;
    		
    	}
		
		if(!wage_code){
    		
    		$.ligerDialog.warn('请选择左侧工资套！');
    		
    		return false;
    		
    	}
		
		var item_code = liger.get("item_code").getValue();

		if(!item_code){
    		
    		$.ligerDialog.warn('请选择工资项目！');
    		
    		return false;
    		
    	}
		
		var formPara={

				wage_code:wage_code,
				
				acc_year:acc_time.split(".")[0],
				
				acc_month:acc_time.split(".")[1]
				
		};
		
		ajaxJsonObjectByUrl("../../accempaccount/queryAccEmpAccountCount.do?isCheck=false",formPara,function(responseData){
			
			if(responseData.num!="0"){
				
				var para="&wage_code="+wage_code +"&acc_year="+acc_time.split(".")[0]+"&acc_month="+acc_time.split(".")[1]; 
				
				$.ligerDialog.open({url: '../../accempaccount/updateAccEmpAccountCountMain.do?isCheck=false'+para,height: 500,width: 870,title:'维护职工账号信息',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true });
				
			}else{
				
				var para = "&payFlag=0"+"&wage_code="+wage_code+"&item_code="+item_code+"&acc_year="+acc_time.split(".")[0]+"&acc_month="+acc_time.split(".")[1];
		    	
				$.ligerDialog.open({url: 'payAccBankNetWagePage.do?isCheck=false'+para,height: 460,width: 870,title:'补录网上银行信息',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
						/* buttons: [ 
						           { text: '确定支付', onclick: function (item, dialog) {dialog.frame.saveAccBankNetWage(); },cls:'l-dialog-btn-highlight' }, 
						           { text: '关闭', onclick: function (item, dialog) { dialog.close(); } } ]  */
				});
				
			}
			
		});

		
	}
   
    function loadHead(){
    	
    	grid = $("#maingrid").ligerGrid({
			columns : [
					{display : '职工编码',name : 'emp_code',width: 100,align : 'left'}, 
					
					{display : '职工名称',name : 'emp_name',width: 150,align : 'left'}, 
					
					{display : '职工分类',name : 'kind_name',width: 150,align : 'left'}, 
					
					{display : '银行账户',name : 'recaccno',width: 180,align : 'left'},
					
					{display : '同城',name : 'is_city_same',width: 60,align : 'left',
						render: function (item)
	                    {
							for (var i = 0; i < is_city_same_data.length; i++)
	                        {
	                            if (is_city_same_data[i]['id'] == item.is_city_same)
	                                return is_city_same_data[i]['text'];
	                        }
	                    }	
					},
					
					{display : '同行',name : 'is_bank_same',width: 60,align : 'left',
						render: function (item)
	                    {
							for (var i = 0; i < is_bank_same_data.length; i++)
	                        {
	                            if (is_bank_same_data[i]['id'] == item.is_bank_same)
	                                return is_bank_same_data[i]['text'];
	                        }
	                    }	
					},
					
					{display : '实发合计',name : 'payamt',width: 120,align : 'right',
						render:function(rowdata){ return rowdata.payamt>0?formatNumber(rowdata.payamt,2,1):formatNumber('0',2,1)}
						}
					],
					dataAction : 'server',dataType : 'server',usePager : true,url : 'queryAccWagePayMain.do',pageSize:500,delayLoad:true,
					width : '100%',height : '100%',checkbox : false,selectRowButtonOnly : true,alternatingRow: false,//heightDiff: -10,
					toolbar : {
					items : [
				         {text : '查询',id : 'search',click : query,icon : 'search'},
				         {line : true},
				         /* {text : '生成支付单',id : 'extend',click : create,icon : 'extend'},
				         {line : true}, */
				         {text : '网上支付',id : 'carry',click : pay,icon : 'communication'},
				         {line : true},
				         {text : '转换',id : 'export',click : myPrint,icon : 'down'},
				         {line : true}
				         ]
			},lodop:{
         		title:"工资支付",
      			fn:{
          			debit:function(value){//借方
          				if(value == 0){return "";}
                 			else{return formatNumber(value, 2, 1);}
          			},
          			credit:function(value){//贷方
          				if(value == 0){return "";}
                			else{return formatNumber(value, 2, 1);}
         				},
         				end_os:function(value){//余额
      	   				 if(value==0){return "Q";}
      					 else{return formatNumber(value, 2, 1);}
        				}
          		}
      		}
			
		});

		gridManager = $("#maingrid").ligerGetGridManager();
    }
    
   
    //加载方案树
    function loadTree(obj){
    	    	
    	ajaxJsonObjectByUrl("queryAccBankNetWageTree.do?isCheck=false",obj,function (responseData){

    	       tree=$.fn.zTree.init($("#tree"), setting, responseData.Rows);
    	});
    }
    
    function loadDict(){
    	
        //加载方案列表
        autocomplete("#kind_code","../../../sys/queryEmpKindDict.do?isCheck=false&is_stop=0","id","text",true,true,'',false,false,'180');

        autocomplete("#emp_code","../../../sys/queryEmpDictForCode.do?isCheck=false&is_stop=0","id","text",true,true,'',false,false,'180');

        //autocomplete("#item_code","../queryAccWageItem.do?isCheck=false&is_stop=0","id","text",true,true,'',false,false,'180');
        
        $("#item_code").ligerComboBox({
	      	url: '../queryAccWageItem.do?isCheck=false&is_stop=0',
	       	selectBoxWidth: 180,
	      	autocomplete: true,
	      	width: 180,
	      	valueField:'id',
	      	textField: 'text',
	      	autocomplete:true,
			highLight: true,
			keySupport: true,
			onSelected: function (value,text){
				grid.changeHeaderText('payamt', text);
            }
		 });
        
        $("#is_bank_same").ligerComboBox({
            width : 180,
            data: is_bank_same_data, 
            value: '1',
            autocomplete: true,
            keySupport: true
        });
        
        
        $("#is_city_same").ligerComboBox({
            width : 180,
            data: is_city_same_data, 
            value: '1',
            autocomplete: true,
            keySupport: true
        });

        
        $("#acc_time").ligerComboBox({url: '../../queryYearMonth.do?isCheck=false',valueField: 'id',textField: 'text', selectBoxWidth: 180,autocomplete: true,width: 180});

	    liger.get("acc_time").setValue(year_Month.substring(0,4)+"."+year_Month.substring(4,6).toString());
		 
    	liger.get("acc_time").setText(year_Month.substring(0,4)+"."+year_Month.substring(4,6).toString());
    	 
     } 

    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	
	<div id="layout1">
            <div position="left" title="">
   			   <div style="width:100%; height:100%;overflow:auto;" >
		      		<ul class="ztree" id="tree" ></ul>
			   </div>
            </div>
            <div position="center"  title="  ">
            	<table cellpadding="0" cellspacing="0" class="l-table-edit">
			   	 	<tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:5px;">会计期间：</td>
			            <td align="left" class="l-table-edit-td" ><input style="width: 160"  name="acc_time" type="text" id="acc_time"/></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">工资项名称：</td>
			            <td align="left" class="l-table-edit-td" ><input name="item_code" type="text" id="item_code" /></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否同城：</td>
			            <td align="left" class="l-table-edit-td" ><input name="is_city_same" type="text" id="is_city_same" /></td>
			            <td align="left"></td>
			        </tr>
			        <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:5px;">职工分类：</td>
			            <td align="left" class="l-table-edit-td" ><input name="kind_code" type="text" id="kind_code" ltype="text" validate="{required:true,maxlength:18}" /></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:5px;">职工名称：</td>
			            <td align="left" class="l-table-edit-td" ><input name="emp_code" type="text" id="emp_code" ltype="text" validate="{required:true,maxlength:18}" /></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否同行：</td>
			            <td align="left" class="l-table-edit-td" ><input name="is_bank_same" type="text" id="is_bank_same" /></td>
			            <td align="left"></td>
			        <tr>
	   			  </table>
	   		<div id="maingrid"></div>
            </div>
        </div> 
	
	
</body>
</html>
