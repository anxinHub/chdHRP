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
<!-- 资产月报表  （内部管理） -->
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    
    $(function (){
        loadDict();//加载下拉框
        loadHotkeys();
    	loadHead(null);	//加载数据
    });
    
    //查询
    function query(){
		grid.options.parms=[];
		grid.options.newPage=1;
		if(liger.get("ass_nature").getValue() == ""){
			$.ligerDialog.error('请选择资产性质');
			return;
		} 
        //根据表字段进行添加查询条件
		grid.options.parms.push({name:'plan_year',value:$("#plan_year").val()}); 
		grid.options.parms.push({name:'ass_nature',value:liger.get("ass_nature").getValue()});  
		// grid.options.parms.push({name:'typt_level',value:liger.get("typt_level").getValue()}); 
    	grid.options.parms.push({name:'type_level',value:$("#type_level").val()});
    	//加载查询条件
    	grid.loadData(grid.where);
    	loadHead();	
		$("#resultPrint > table > tbody").empty();
	}

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '类别编码', name: 'ass_type_code', align: 'left',width: '110',frozen: true
                     },
                     { display: '类别名称', name: 'ass_type_name', align: 'left',width: '110',frozen: true
					 		},
                     { display: '原值',  align: 'center',columns:[
                                                                {display:'期初',name:'begin_money',align:'right',width:'120',
											                    	 render : function(rowdata, rowindex,
																				value) {
																			 return formatNumber(rowdata.begin_money,'${ass_05005 }',1);
																		}
                                                                 },
																 {display:'借方',name:'add_money',align:'right',width:'100',
										                           	 render : function(rowdata, rowindex,
																				value) {
																			 return formatNumber(rowdata.add_money,'${ass_05005 }',1);
																		}
                                                                 },
										                         {display:'贷方',name:'dec_money',align:'right',width:'100',
										                           	 render : function(rowdata, rowindex,
																				value) {
																			 return formatNumber(rowdata.dec_money,'${ass_05005 }',1);
																		}
                                                                 },
										                         {display:'余额',name:'balance_money',align:'right',width:'120',
										                           	 render : function(rowdata, rowindex,
																				value) {
																			 return formatNumber(rowdata.begin_money+rowdata.add_money-rowdata.dec_money,'${ass_05005 }',1);
																		}
                                                                 }
                     												]

					 		},
					 { display: '累计折旧', align: 'center',  
					 		columns:[
                                     {display:'期初',name:'depre_begin_money',align:'right',width:'120',
					                    	 render : function(rowdata, rowindex,
														value) {
													 return formatNumber(rowdata.depre_begin_money,'${ass_05005 }',1);
												}
                                      },
										 {display:'借方',name:'depre_add_money',align:'right',width:'100',
				                           	 render : function(rowdata, rowindex,
														value) {
													 return formatNumber(rowdata.depre_add_money,'${ass_05005 }',1);
												}
                                      },
				                         {display:'贷方',name:'depre_dec_money',align:'right',width:'100',
				                           	 render : function(rowdata, rowindex,
														value) {
													 return formatNumber(rowdata.depre_dec_money,'${ass_05005 }',1);
												}
                                      },
				                         {display:'余额',name:'depre_balance_money',align:'right',width:'120',
				                           	 render : function(rowdata, rowindex,
														value) {
													 return formatNumber(rowdata.depre_begin_money+rowdata.depre_dec_money-rowdata.depre_add_money,'${ass_05005 }',1);
												}
                                      }
											]
					 		},
					 { display: '净值',align: 'center',  
						 		columns:[
	                                     {display:'期初',name:'remain_begin_money',align:'right',width:'120',
						                    	 render : function(rowdata, rowindex,
															value) {
														 return formatNumber(rowdata.begin_money-rowdata.depre_begin_money,'${ass_05005 }',1);
													}
	                                      },
											 
					                         {display:'期末',name:'remain_end_money',align:'right',width:'120',
					                           	 render : function(rowdata, rowindex,
															value) {
														 return formatNumber((rowdata.begin_money+rowdata.add_money-rowdata.dec_money)-(rowdata.depre_begin_money+rowdata.depre_dec_money-rowdata.depre_add_money),'${ass_05005 }',1);
													}
	                                      }
												]
					 		}
                     ],//url:'queryAssPropertyMonthMain.do?ass_nature='+liger.get("ass_nature").getValue(),
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAssPropertyMonthMainManage.do?ass_nature='+liger.get("ass_nature").getValue()+'&depre_month='+$("#plan_year").val(),
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad :true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                                        { text: '查   询 （<u>Q</u>）', id:'search', click: query,icon:'search' },
                                        { line:true }, 
            						     { text: '打   印（<u>P</u>）', id:'print', click: printDate,icon:'print' }
                                ]},
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
   
    function loadDict(){
         //字典下拉框
         
		$("#plan_year").ligerTextBox({width:100}); 
       	$("#type_level").ligerTextBox({width:120});
         //默认年
		autodate("#plan_year","YYYYMM"); 

		$("#ass_nature").ligerComboBox({
          	url: '../queryAssNaturs.do?isCheck=false',
          	valueField: 'id',
           	textField: 'text', 
           	selectBoxWidth: '160',
          	autocomplete: true,
          	width: '160',
          	onSelected :function(id,text){ 
			}
		});
		 
    } 
    //键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('P', printDate);
	}
    
	//打印数据
	function printDate(){
		if(grid.getData().length==0){
  			$.ligerDialog.error("请先查询数据！");
  			return;
  		}
		
		var printPara={
				title: "",//标题
				columns: JSON.stringify(grid.getPrintColumns()),//表头
				class_name: "com.chd.hrp.ass.service.",
				method_name: "",
				bean_name: ""/* ,
				heads: JSON.stringify(heads) *///表头需要打印的查询条件,可以为空
				/* foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 */
				};
		
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
     <table cellpadding="0" cellspacing="0" class="l-table-edit" >
		
        <tr >
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产性质：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_nature" type="text" id="ass_nature"  /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">会计期间：</td>
            <td align="left" class="l-table-edit-td"><input name="plan_year" type="text" id="plan_year" ltype="text" validate="{required:true,maxlength:20}" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产分类汇总到级次：</td> 
            <td align="left" class="l-table-edit-td">
            	<select name="type_level" id="type_level">
            		<option value=""></option>
            		<option value="1">1</option>
            		<option value="2">2</option>
            		<option value="3">3</option> 
            	</select>
            </td>
        </tr> 
       
    </table>

	<div id="maingrid"></div>
</body>
</html>
