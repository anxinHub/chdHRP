<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<script src="<%=path%>/lib/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js" type="text/javascript"></script>
<script src="<%=path%>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/core/base.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path%>/lib/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<link href="<%=path %>/lib/Z-tree/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css"/>
<script src="<%=path %>/lib/Z-tree/js/jquery.ztree.core-3.5.js" type="text/javascript" ></script>
<script src="<%=path %>/lib/Z-tree/js/jquery.ztree.excheck-3.5.js" type="text/javascript" ></script>
<script src="<%=path %>/lib/Z-tree/js/jquery.ztree.exedit-3.5.js" type="text/javascript" ></script>
<script src="<%=path%>/lib/layer-v2.3/layer/layer.js" type="text/javascript"></script>
<script src='<%=path%>/lib/SpreadJS10/Spread.Sheets.All.10.1.0/scripts/gc.spread.sheets.all.10.1.0.min.js' type='text/javascript'></script>
<script src='<%=path%>/lib/SpreadJS10/Spread.Sheets.All.10.1.0/scripts/interop/gc.spread.excelio.10.1.0.min.js' type='text/javascript'></script>
<script src="<%=path%>/lib/SpreadJS10/license.js" type="text/javascript"></script>
<script src="<%=path%>/pageoffice.js" type="text/javascript" id="po_js_main"></script>

<script type="text/javascript">
var tree;
var setting;
var grid;
var gridManager;

    $(function (){
		
		$("#layout1").ligerLayout({ leftWidth: 210,allowLeftResize: true
			,onLeftToggle: function (isColl){
            	//alert(isColl ? "收缩" : "显示");
				grid._onResize();
        	}
			,onEndResize: function(isColl) {
				grid._onResize();
	        }	
		});
    	setting = {    
            	check: {enable: true},
 		   		data: {simpleData: {enable: true},key: {title: "title"}},
 		   		treeNode:{open:true}
 	 	};
    	loadHead('');
    	$("#treeDiv").css("height", $(window).height()-28);
    	$("#period_td").css("display","none");
    });
    
    function loadDict(rtype){
    	//查询报表条件
		var para={
			report_type:rtype   			
    	};
		ajaxJsonObjectByUrl("../queryAccSuperReportByWhere.do?isCheck=false",para,function(responseData){
			var curMonth;
			if(responseData.minDate=="0"){
				$("#acc_year").attr("disabled","disabled");
				$.ligerDialog.error("请维护年度！");
				return false;
			}
			
			$("#acc_year").val(responseData.curDate.substring(0,5));
			$("#minDate").val(responseData.minDate);
			$("#maxDate").val(responseData.maxDate);
			curMonth=parseInt(responseData.curDate.substring(5,7));

	    	if(rtype==2 || rtype==3 || rtype==4 || rtype==5){
				//月报、季报、半年报、年报
				$("#acc_report_tab").css("display","block");
				if(rtype==2){
					//月报
					$("#period_td").css("display","block");
					accYearFunc($("#acc_year").val(),responseData.curDate.substring(5,7));
	 			}else if(rtype==3){
					//季报
					var selVal=14;
					if(curMonth>=4 && curMonth<=6)selVal=15;
					else if(curMonth>=7 && curMonth<=9)selVal=16;
					else if(curMonth>=10 && curMonth<=12)selVal=17;
					
					$("#period_td").css("display","block");
					$("#period").ligerComboBox({
		                width : 150,
		                data: [
		                    { text: '第一季度', id: '14' },
		                    { text: '第二季度', id: '15' },
		                    { text: '第三季度', id: '16' },
		                    { text: '第四季度', id: '17' }
		                ],
		                value: selVal,cancelable: false
		            });
					
				}else if(rtype==4){
					//半年报
					var selVal=18;
					if(curMonth>=7 && curMonth<=12)selVal=19;
					
					$("#period_td").css("display","block");
					$("#period").ligerComboBox({
		                width : 150,
		                data: [
		                    { text: '上半年', id: '18' },
		                    { text: '下半年', id: '19' }
		                ],  
		                value: selVal,cancelable: false
		            });
					
				}else if(rtype==5){
					//年报
					$("#period_td").css("display","none");
					
				}
	    	}
			
		});
    }
    function loadTree(){
    	loadDict($('#report_type').val());
    	var para={
				mod_code:'${mod_code}',
				report_type:$('#report_type').val()
    	};
		ajaxJsonObjectByUrl("../queryAccSuperReportByPerm.do?isCheck=false",para,function(responseData){	
			  tree=$.fn.zTree.init($("#tree"), setting, responseData.Rows);
			  var nodes=tree.getCheckedNodes();
      		$.each(nodes, function(index, item){ 
  				tree.checkNode(item, true, true);
  			})
		});
		
    }
  //年度发生改变，查询月份
	function accYearFunc(newDateStr,curDate){	 
		
		$("#period").ligerComboBox({
			parms : {acc_year:newDateStr.substring(0,4)},
		   	url: '../queryYearMonthByAccYearSelect.do?isCheck=false',
	        width : 150,
	        valueField: 'id',
	      	textField: 'text', 
	        cancelable: false,
	        async:false,
	        onSuccess: function (data) {
				if (data.length > 0) {
					if(curDate!=""){
						this.setValue(curDate);
					}else if (data[0].id != undefined && data[0].id != "" && data[0].id != null) {
						this.setValue(data[0].id);
					}
				}
			}
	       });
	}
    function loadHead(dataMap){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
					 { display: '报表编码', name: 'report_code', align: 'left',width:100},
                     { display: '报表名称', name: 'report_name', align: 'left',width:200},
                     { display: '状态', name: 'state', align: 'left',width:80},
					 { display: '信息',name: 'msg', align: 'left',
							render : function(rowdata, rowindex,value) {
								if(rowdata.state=='失败')
									return "<p title="+rowdata.msg+"><font color=red>"+rowdata.msg+"</font></p>";
								else
									return rowdata.msg;
							}}
    				],
    				usePager : false,
    				data: dataMap,width : '99%',height : '100%',checkbox : false,
    				rownumbers : true,delayLoad:true,selectRowButtonOnly : true ,heightDiff: 0
    				 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
	

    
    function save(){
    	var zTree= $.fn.zTree.getZTreeObj("tree");
    	 if(zTree==null){
         		$.ligerDialog.error("请选择要生成的报表！");
         		return;
         }
        var notes =zTree.getCheckedNodes(true);
        
        var checkedIds=[];
        for (var i = 0; i < notes.length; i++){
            if(notes[i].pId!=null&&notes[i].pId!='~!@#1'&&notes[i].pId!='~!@#2'){
            	checkedIds.push(notes[i].id+"@"+notes[i].name);
            }
        }
        if(checkedIds.length==0){
        	$.ligerDialog.error("未选择有效的报表,或者您选择的报表没有模板无法生成！");
        	return;
        }
        var loadIndex = layer.load(1);
		ajaxJsonObjectBylayer(
			"batchGenerate.do?isCheck=false",
			{
    			listVo:checkedIds.toString(),
    			rtype:$("#report_type").val(),
    			acc_year:$("#acc_year").val().substring(0,4),
    			acc_month:liger.get("period")==undefined?'':liger.get("period").getValue()
    		},
			function (responseData){
    			loadHead(responseData);
			},
			layer,
			loadIndex
		);
			
			
       
        //alert('选择的节点数：' + text);
    }
   
	

	</script>

</head>

<body style="padding: 0px; overflow: hidden;">
<div id="pageloading" class="l-loading" style="display: none"></div>
<input type="text" id="minDate" style="display:none"/>
<input type="text" id="maxDate" style="display:none"/>	
	<table cellpadding="0" cellspacing="0" class="l-table-edit" id="acc_report_tab">
			<tr>
			   <td>
			      	<table>
			       		<tr>
			       		<td align="left" class="l-table-edit-td">
					     <select name="" id="report_type" ltype="text" style="width:180px;" onChange="loadTree();">
								<option value="-1">选择报表类型</option>
								<option value="2">月报</option>
								<option value="3">季报</option>
								<option value="4">半年报</option>
								<option value="5">年报</option>
							</select>
						</td>
			       		 <td align="right" class="l-table-edit-td"  style="padding-left:20px;">年度：</td>
			             <td align="left" class="l-table-edit-td">
							<input class="Wdate" name="acc_year" type="text" id="acc_year" ltype="text" 
										onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy年',minDate:'#F{$dp.$D(\'minDate\')}',maxDate:'#F{$dp.$D(\'maxDate\')}',ychanging:function(dp){accYearFunc(dp.cal.getNewDateStr());}})"/>
									 </td>
			       		 </tr>
				    </table>
			   </td>
				<td id="period_td">
				    <table>
					    <tr>
					    	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">会计期间：</td>
							<td align="left" class="l-table-edit-td">
								<div id="period" style="display:none"></div> 
							</td>
					     </tr>
					</table>
				</td>   
			 </tr>
	</table>
	<div class="l-layout" id="layout1"  >
		<div position="left" title="报表列表">
		    <div  style="overflow:auto;" id="treeDiv">
		       	<ul class="ztree"  id="tree"></ul>
		    </div>
	    </div>
		
		<div position="center" id="centerReport">
			         
		    
			
		    <div id="maingrid"></div>
		</div>
		
	</div>
	
</body>
</html>
