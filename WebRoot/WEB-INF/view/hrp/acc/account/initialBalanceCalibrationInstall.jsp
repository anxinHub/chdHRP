<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
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
        
         var subj_code=liger.get("subj_code").getValue();
        
         if(subj_code==""){
         	
         	$.ligerDialog.error('科目编码不能为空！');
         	
         	return;
         	
         }
        
    	grid.options.parms.push({name:'subj_code',value:liger.get("subj_code").getValue()});
    	grid.options.parms.push({name:'subj_select_flag',value:$('#is_state').is(':checked')?1:0});
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({ 
           columns: [ 
                     { display: '单位银行账项目', name: 'unit_name', align: 'left'
					 },
                     { display: '金额', name: 'debit', align: 'right',formatter:'###,##0.00',
                    	 render : function(rowdata, rowindex, value) {
           					return formatNumber(rowdata.debit, 2, 1);
           				}
					 },
                     { display: '银行对账单项目', name: 'bank_name', align: 'left'
					 },
                     { display: '金额', name: 'credit', align: 'right',formatter:'###,##0.00',
                    	 render : function(rowdata, rowindex, value) {
            					return formatNumber(rowdata.credit, 2, 1);
            				}
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryInitialBalanceCalibration.do',
                     width: '100%', height: '100%', rownumbers:true,delayLoad:true,onAfterShowData:function (currentData){
                        		$.each(currentData.Rows, function(t_index, t_content){ 
								    if(t_content.unit_name=="单位-调节后余额" || t_content.bank_name =="银行-调节后余额"){
								    	if(t_content.debit == t_content.credit){
								    		$("#hid").show()
								    		$("#hid").html("初始余额校验平衡!")
								    		$("#hid").css({color:"green"}) 
								    	}else {
								    		$("#hid").show()
								    		$("#hid").html("初始余额校验不平衡!")
								    		$("#hid").css({color:"red"}) 
								    	}
								    }

				     			}); 
                     },
                     lodop:{
     		    		title:"初始余额效验",
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
     				},
                     selectRowButtonOnly:true,//heightDiff: -10,
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
   
    function itemclick(item){
    	
    }
    
    
    var menu_print = {
    		width : 120,
    		items : [ {
    			text : '打印',
    			id : 'print',
    			click : itemclick
    		}, {
    			text : '预览',
    			id : 'view',
    			click : itemclick
    		}, {
    			text : '设置',
    			id : 'set',
    			click : itemclick
    		} ]
    	};
    
function print_btn(){
	
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
			title: "初始余额校验",//标题
			columns: JSON.stringify(grid.getPrintColumns()),//表头
			class_name: "com.chd.hrp.acc.service.AccInitialBalanceCalibrationService",
			method_name: "collectInitialBalanceCalibrationPrint",
			bean_name: "accInitialBalanceCalibrationService"/* ,
			heads: JSON.stringify(heads) *///表头需要打印的查询条件,可以为空
			/* foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 */
			};
	
	$.each(grid.options.parms,function(i,obj){
			printPara[obj.name]=obj.value;
	});
	
	officeGridPrint(printPara);
    
    } 
    
    function loadDict(){
            //字典下拉框
    	autocomplete("#subj_code","../querySubj.do?isCheck=false&subj_nature_code=03&is_last=1","id","text",true,true,'',true);
        
    	$(':button').ligerButton({width:80});

    }   
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
	
	<div id="topmenu"></div>
	
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>科目编码：</td>
            <td align="left" class="l-table-edit-td"><input name="subj_code" type="text" id="subj_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><input name="is_state" type="checkbox" id="is_state" class="liger-checkbox"/>含对账明细</td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"></td>
            <td align="left"><input  type="button" value=" 查询" onclick="query();"/></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"></td>
            <td><input  type="button" value="打印" onclick="print_btn();"/></td>
            <td align="left">　</td>
            <td align="left">　</td>
            <td align="left">　</td>
            <td align="left">　</td>
            <td align="right" id="hid" style="display:none;"></td>
        </tr> 
    </table>

	<div id="maingrid"></div>

</body>
</html>
