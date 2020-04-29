<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">	
    
    var grid; 
    
    var gridManager = null; 
    
    var userUpdateStr;
    
    $(function(){
    	
    	loadDict();//加载字典
    	
    	loadHead(null);//加载数据
    	
    });
    
    
    //查询
    function  query(){
    	grid.options.parms=[];
    	grid.options.newPage=1;
        //根据表字段进行添加查询条件

    	//加载查询条件
    	grid.loadData(grid.where);
    }

    function loadHead(){
    	
    	
    	
    	grid = $("#maingrid").ligerGrid({
           columns: [ { display: '区间描述', name: 'note', align: 'left',
								render : function(rowdata, rowindex,
										value) {
										return "<a href=javascript:openUpdate('"+rowdata.range_id+"')>"+rowdata.note+"</a>";
								}
			 		 },
                     { display: '起始天数', name: 'begin_days', align: 'left'
					 },
                     { display: '终止天数', name: 'end_days', align: 'left',
							render : function(rowdata, rowindex,
									value) {
								if(rowdata.begin_days == 0){
									return '0';
								}
								if(value == 0){
									rowdata.end_days = "";
									return "";
								}
								return 	value;
							}
						 
					 },
                     { display: '计提比例', name: 'range_pro', align: 'left'
					 },
					 { display: '操作', name: 'iedt', align: 'left',
							render : function(rowdata, rowindex,
									value) {
									return "<div class='l-button' style='width: 60px; margin-top:1px; margin-left: 30px;' ligeruiid='Button1004'" 
									+"onclick=deleteBudgRange('"+rowdata.end_days+"','"+rowdata.range_id+"','"+rowdata.group_id+"','"+rowdata.hos_id+"','"+rowdata.copy_code+"')>"
				       					+"<span>删除</span></div>";
							}
			 		 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAccBudgRange.do?isCheck=true',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
    					{ text: '添加', id:'add', click: itemclick, icon:'add' },
                     	{ line:true },
    					{ text: '打印', id:'print', click: print, icon:'print' }
    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.range_id 
							);
    				} 
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
				title: "账龄区间设置",//标题
				columns: JSON.stringify(grid.getPrintColumns()),//表头
				class_name: "com.chd.hrp.acc.service.verification.AccBudgRangeService",
				method_name: "queryAccBudgRangePrint",
				bean_name: "accBudgRangeService"/* ,
				heads: JSON.stringify(heads) *///表头需要打印的查询条件,可以为空
				/* foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 */
				};
		
		$.each(grid.options.parms,function(i,obj){
				printPara[obj.name]=obj.value;
		});
		
		officeGridPrint(printPara);
           	
    }
    
    function itemclick(item){ 
        if(item.id)
        {
            switch (item.id)
            {
                case "add":
                	
                	$.post("getMaxDays.do?isCheck=false",null,function(data){
                		
                		if( 0 ==data){
                			
                			$.ligerDialog.error('终止天数已是无穷大，不能再添加！');
                			
                		}else{
                			
                			$.ligerDialog.open({url: 'accBudgRangeAddPage.do?isCheck=false', height: 300,width: 500, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAccBudgRange(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
                			
                		}
                	});
              		
              		return;
                case "modify":
                    return;
                case "delete":
                case "Excel":
                case "Word":
                case "PDF":
                case "TXT":
                case "XML":
            }   
        }
        
    }
    
    function deleteBudgRange(end_days,range_id,group_id,hos_id,copy_code){
    	
    	$.post("getMaxDays.do?isCheck=false",null,function(data){
    		
    		if( "" != end_days&&parseInt(end_days) != parseInt(data)){
    			$.ligerDialog.error('只能从最后一条开始删除');
    		}else{
                var ParamVo =[];
				ParamVo.push(range_id+"@"+group_id+"@"+hos_id+"@"+copy_code);
				
                $.ligerDialog.confirm('确定删除?', function (yes){
                if(yes){
                      ajaxJsonObjectByUrl("deleteAccBudgRange.do?isCheck=true",{ParamVo : ParamVo.toString()},function (responseData){
                        	if(responseData.state=="true"){
                        		query();
                        	}
                       });
                }
                }); 
    		}
    	});
    }
    
    function openUpdate(obj){
    	
		var vo = obj.split("|");
		var parm = "range_id="+vo[0]; 
		//alert(parm);
    	$.ligerDialog.open({ url : 'accBudgRangeUpdatePage.do?isCheck=false&' + parm,data:{}, height: 300,width: 500, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAccBudgRange(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    }
    function loadDict(){
            //字典下拉框
            
    }   
	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
	<center><h1>账龄区间</h1></center>
	<div id="maingrid"></div>

</body>
</html>
