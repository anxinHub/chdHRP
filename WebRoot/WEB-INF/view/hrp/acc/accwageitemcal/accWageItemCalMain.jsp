<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<link href="<%=path %>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="<%=path %>/lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<script src="<%=path %>/lib/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
<script src="<%=path %>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path %>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>
<script src="<%=path%>/lib/My97DatePicker/WdatePicker.js"	type="text/javascript"></script>

<script src="<%=path%>/lib/et_components/et_plugins/etDatepicker.min.js" type="text/javascript"></script>
<script type="text/javascript">
	
    var grid;
    
    var rightgrid;
    
    var gridManager = null;
    
    var rightgridManager = null;
    
    var userUpdateStr;
    
    var para;
    
    var acc_years;
    
    $(function ()
    {
    	loadDict(null);
    	loadHead(null);	//加载数据
    	$("#para_code").ligerTextBox({width:160});
    	$("#para_name").ligerTextBox({width:160});
    	
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
        grid.options.parms.push({name:'wage_code',value:para}); 
    	grid.options.parms.push({name:'para_code',value:$("#para_code").val()}); 
    	grid.options.parms.push({name:'para_name',value:$("#para_name").val()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '工资套编码', name: 'wage_code', align: 'left'
					 },
                     { display: '工资套名称', name: 'wage_name', align: 'left'
					 },
                     { display: '操作', name: 'edit', align: 'center',
							render : function(rowdata, rowindex,
									value) {
									return "<a href='#'>合并日志<a/>&nbsp;&nbsp;&nbsp;&nbsp<a href=javascript:showData('"+rowdata.wage_code+"')>合并公式<a/>";
									
							}
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:false,url:'../accwage/queryAccWage.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,isSingleCheck:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '合并计算', id:'add', click: itemclick,icon:'add' },
                     	{ line:true }
    				]}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
        
        rightgrid = $("#rightgrid").ligerGrid({
            columns: [ 
                      { display: '工资套名称', name: 'wage_name', align: 'left'
 					 },
                      { display: '工资项编码', name: 'item_code', align: 'left'
 					 },
 					 { display: '工资项名称', name: 'item_name', align: 'left'
 					 },
 					 { display: '职工分类', name: 'kind_name', align: 'left'
					 },
                     { display: '操作', name: 'edit', align: 'center',
							render : function(rowdata, rowindex,
									value) {
								
									return "<a href=javascript:setWageItemCal('"+rowdata.wage_code+"|"+rowdata.kind_code+"|"+rowdata.item_code+"')>公式<a/>";
							}
					 }
                      ],
                      dataAction: 'server',dataType: 'server',usePager:true,
                      width: '100%', height: '96%', checkbox: true,rownumbers:true,delayLoad:true,
                      selectRowButtonOnly:true,//heightDiff: -10,
                      toolbar: { items: [
						{ text: '添加公式', id:'add', click: toobarclick,icon:'add' },
						{ line:true },
                      	{ text: '删除公式', id:'delete', click: toobarclick,icon:'delete' },
                      	{ line:true }
                      	
     				]}
                    });

        rightgridManager = $("#rightgrid").ligerGetGridManager();
    }
    
    function itemclick(item){ 
        if(item.id)
        {
            switch (item.id)
            {
                case "add":
                	
                	var acc_year = acc_years.getValue();
                	
                	if(acc_year == null){
                		
                		$.ligerDialog.error('请选择会计期间！');
                		
                	}
                	
                	var data = gridManager.getCheckedRows();
                	
                	if (data==null){
                		
                    	$.ligerDialog.error('请选择工资套进行合并！');
                    	
                    }else{
                    	
                    	var formPara={

              		           wage_code:data[0].wage_code,
              		           
              		           acc_year:acc_year.split(".")[0],
              		           
              		           acc_month:acc_year.split(".")[1],
              		           
              		           wage_name:data[0].wage_name
              		            
              		         };
                    	
                    	$.ligerDialog.confirm('确定合并?', function (yes){
                        	if(yes){
                        		ajaxJsonObjectByUrl("../accwagepay/combineAccWagePayDesc.do?isCheck=false",formPara,function(responseData){
                 		            
                 		            if(responseData.state=="true"){
                 		                parent.query();
                 		            }
                 		        });
                        	}
                        }); 
                    	
                        //$.ligerDialog.open({url: 'accWagePayAddMainPage.do?wage_code='+ParamVo, height: 275,width: 400, title:'合并',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.save(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
                    }
					
                	return;
                case "delete":
                    var data = gridManager.getCheckedRows();
                    if (data.length == 0){
                    	$.ligerDialog.error('请选择行');
                    }else{
                        var ParamVo =[];
                        $(data).each(function (){					
							ParamVo.push(
							//表的主键
							this.group_id+"@"+
							this.hos_id+"@"+
							this.copy_code+"@"+
							this.wage_code
							)
                        });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteAccWageCal.do",{ParamVo : ParamVo},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
                    return;
            }   
        }
        
    }
    
    function toobarclick(item){ 
        if(item.id)
        {
            switch (item.id)
            {
            case "add":
            	
            	$.ligerDialog.open({ url : 'accWageItemCalAddPage.do?isCheck=false',data:{}, height: 515,width: 800, title:'设置公式',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveWageItemCal(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

            	return;
            	
            case "delete":
            	
            	var data = rightgridManager.getCheckedRows();
            	
                if (data.length == 0){
                	
                	$.ligerDialog.error('请选择行');
                	
                }else{
                	
                    var ParamVo =[];
                    
                    $(data).each(function (){	
                    	
						ParamVo.push(
						
						this.cal_id/* +"@"+
						this.wage_code+"@"+
						this.item_code+"@"+
						this.kind_code */
						)
                    });
                    
                    $.ligerDialog.confirm('确定删除?', function (yes){
                    	
                    	if(yes){
                        	
                    		ajaxJsonObjectByUrl("../accwagecal/deleteAccWageCal.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
                        		
                    			if(responseData.state=="true"){
                        			
                    				rightgrid.reload();
                        		
                    			}
                        	
                    		});
                    	
                    	}
                   
                    }); 
               
                }
            	return;
                
            }   
        }
        
    }
    
    var kind_code;
    
    function setWageItemCal(obj){
    	
    	var acc_year=acc_years.getValue();
    	
    	var vo = obj.split("|");
    	
    	if(vo[1]=="0"){
    		
    		kind_code=null;
    		
    	}
    	
		var parm = "wage_code="+
			vo[0]   +"&kind_code="+ 
			vo[1]   +"&item_code="+ 
			vo[2]   +"&acc_year="+ 
			acc_year;
			
    	$.ligerDialog.open({ url : 'accWageItemCalUpdatePage.do?isCheck=false&' + parm,data:{}, height: 515,width: 800, title:'设置公式',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveWageItemCal(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    }
    function showData(obj){
    	
    	para=obj;
    	
    	var formPara={
        		
 	       wage_code:obj
 	            
 	    };
    	        
       ajaxJsonObjectByUrl("../accwagecal/queryAccWageCal.do?isCheck=false",formPara,function(responseData){
           
       rightgridManager.set({ data: responseData });
       
       });
    	
    }
    
    function loadDict(){
        //字典下拉框
	       //期间
	     	acc_year = $("#acc_year").etDatepicker({
	            view: "months",
	            minView: "months",
	            dateFormat: "yyyy.mm",
	            defaultDate: '${wage_year_month}'
	   		});
     }
     
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div style="width: 1140px;">
	<div  style="float: left; width: 570px;">
	<div id="toptoolbar" ></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
        <tr>
             <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>期间：</td>
            <td align="left" class="l-table-edit-td"><input name="acc_year" type="text" id="acc_year" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
    </table>
	<div id="maingrid"></div>
	</div>
	<div  style="float: left; width: 570px;">
	<div id="righttoolbar" ></div>

	 <div id="rightgrid" style="margin-left: 3px"></div>
	</div>
	</div>

</body>
</html>
