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
    	loadHead(null);	
		loadHotkeys();
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	  grid.options.parms.push({name:'project_no',value:$("#project_no").val()}); 
    	  grid.options.parms.push({name:'bid_date1',value:$("#bid_date1").val()}); 
    	  grid.options.parms.push({name:'bid_date2',value:$("#bid_date2").val()}); 
    	//加载查询条件
    	grid.loadData(grid.where);
     }
   
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '投标立项编码', name: 'project_no', align: 'left',frozen: true,
                    	 render : function(rowdata, rowindex,value) {
                    		 
    							return "<a href=javascript:openUpdate('"+rowdata.group_id   + "|" + 
  																	rowdata.hos_id   + "|" + 
  																	rowdata.copy_code   + "|" + 
  																	rowdata.project_id +"')>"+
    									                                 rowdata.project_no+"</a>";
    										},width:100
					 		},
                     { display: '投标立项日期', name: 'bid_date', align: 'left',frozen: true,width:100
					 		}
                     
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAssBidProject.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     //pageSize:10,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     delayLoad :true,//初始化明细数据
                     toolbar: { items: [
                     	{ text: '查询（<u>E</u>）', id:'search', click: query,icon:'search' },
                     	{ line:true },
				    					{ text: '添加（<u>A</u>）', id:'add', click: add_open, icon:'add' },
				    	                { line:true },
				    	                { text: '删除（<u>D</u>）', id:'delete', click: remove,icon:'delete' },
										{ line:true }, 
						                { text: '打印（<u>P</u>）', id:'print', click: printDate,icon:'print' }
				    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code   + "|" + 
								rowdata.project_id
							);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function add_open(){
    	
    	parent.$.ligerDialog.open({
    		title: '投标立项添加',
    		height: $(window).height(),
    		width: $(window).width(),
    		url: 'hrp/ass/assbidproject/assBidProjectAddPage.do?isCheck=false&',
    		modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
    		parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
    	});
    }
    
    	
    function remove(){
    	
    	var data = gridManager.getCheckedRows();
                    if (data.length == 0){
                    	$.ligerDialog.error('请选择行');
                    }else{
                        var ParamVo =[];
                        $(data).each(function (){					
							ParamVo.push(
							this.group_id   +"@"+ 
							this.hos_id   +"@"+ 
							this.copy_code   +"@"+ 
							this.project_id  ) });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteAssBidProject.do",{ParamVo : ParamVo.toString()},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
    	}
   
    function openUpdate(obj){
    		
		var vo = obj.split("|");
		if("null"==vo[3]){
			return false;
			
		}
		var parm = 
			"group_id="+vo[0]   +"&"+ 
			"hos_id="+vo[1]   +"&"+ 
			" copy_code="+vo[2]   +"&"+ 
			"project_id="+vo[3]  ;
		
		parent.$.ligerDialog.open({
    		title: '投标立项修改',
    		height: $(window).height(),
    		width: $(window).width(),
    		url: 'hrp/ass/assbidproject/assBidProjectUpdatePage.do?isCheck=false&'+ parm,
    		modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
    		parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
    	});
    
    }
    function loadDict(){
    	
    	 
        $("#project_no").ligerTextBox({width:160});
        
        $("#bid_date1").ligerTextBox({width:100});
        
        $("#bid_date2").ligerTextBox({width:100});
           
    } 
    //键盘事件
	  function loadHotkeys() {

		hotkeys('Q', query);

		hotkeys('A', add);
		
		hotkeys('D', remove);

		hotkeys('P', printDate);
		
		
	 }
    
	   function printDate(){
	    	
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
	       			title:'招标立项',
	       			head:[
	    				{"cell":0,"value":"单位: ${hos_name}","colspan":colspan_num,"br":true}
	       			],
	       			foot:[
	    				{"cell":0,"value":"主管:","colspan":2,"br":false} ,
						{"cell":2,"value":"复核人:","colspan":colspan_num-2,"br":true},
						{"cell":0,"value":"制单人： ${user_name}","colspan":2,"br":false},
						{"cell":2,"value":"打印日期: " + cur_date,"colspan":colspan_num-2,"br":true}
	       			],
	       			columns:grid.getColumns(1),
	       			headCount:2,//列头行数
	       			autoFile:true,
	       			type:3
	       	};
	   		ajaxJsonObjectByUrl("queryAssBidProject.do?isCheck=false", selPara, function (responseData) {
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
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">投标立项日期：</td>
            <td align="left" class="l-table-edit-td"><input name="bid_date1" type="text" id="bid_date1"  class="Wdate" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'bid_date2\')}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
            <td align="left" class="l-table-edit-td">至：</td>
            <td align="left" class="l-table-edit-td"><input name="bid_date2" type="text" id="bid_date2"  class="Wdate" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'bid_date1\')}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">投标立项编码：</td>
            <td align="left" class="l-table-edit-td"><input name="project_no" type="text" id="project_no"  /></td>
            <td align="left"></td>
        </tr>
        </table>
	<div id="maingrid"></div>
</body>
</html>
