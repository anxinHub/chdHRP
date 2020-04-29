<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
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
<script src="<%=path%>/lib/layer-v2.3/layer/layer.js" type="text/javascript"></script>
<script src="<%=path%>/pageoffice.js" type="text/javascript" id="po_js_main"></script>
<script type="text/javascript">

    var balGrid;
    var gridManager = null;
    var radionManager=null; 
    
    $(function ()
    {
        loadDict();//加载下拉框
    	loadBalHead();
    	//balGrid._setWidth("100%");
    	$(':button').ligerButton({width:80});
    	$("button[name='createVouchBtn']").ligerButton({width:100});
    });
    
   

    function loadDict(){

      	autocomplete("#template_code","queryAutoBusiTemplate.do?isCheck=false&busi_type_code=0104","id","text",true,true,"",true);
      	autocomplete("#pay_type_code","../../../queryPayType.do?isCheck=false","id","text",true, true,"",false,"",210);
    	$("#charge_date_b").ligerTextBox({width:90});autodate("#charge_date_b", "yyyy-mm-dd", "month_first");
    	$("#charge_date_e").ligerTextBox({width:90});autodate("#charge_date_e", "yyyy-mm-dd", "month_last");
    	$("#rep_no").ligerTextBox({width:150});
    	$("#charge_code").ligerTextBox({width:150});
    	radionManager=$("#sum_type_id input:radio").ligerRadio();
    	$("#vouch_date").val('${vouch_date}');
    	
    	var io_type_data = [{ id: 1, text: '1 住院' }];
    	if(${type}==1){
    		//温医个性化需求
    		io_type_data.push({ id: 2, text: '2 其它' });
    	}
    	$("#io_type").ligerComboBox({data: io_type_data,width:150,valueField:'id',textField:'text',autocomplete: false,cancelable: false}); 
		liger.get("io_type").setValue(1);
		liger.get("io_type").setText('1 住院');
	}
    
    //加载结算表头
	function loadBalHead() {
		balGrid = $("#balgrid").ligerGrid({
					columns : [
					           	{display : '日报序号',name : 'rep_no',width: 150,align : 'left'}, 
								{display : '收款员编码',name : 'charge_code',width: 150,align : 'left'},
								{display : '收款员名称',name : 'charge_name',align : 'left',width: 150,align : 'left'},
								{display:'凭证编号',name:"vouch_no",width: 100,align : 'left',render:function(rowdata){
									if(rowdata.vouch_no=='-'){
										return rowdata.vouch_no;
									}else{
										return "<a href=javascript:openSuperVouch('"+rowdata.vouch_id+"')>"+rowdata.vouch_no+"</a>";
									}
								}},
								{display : '结算方式编码',name : 'pay_type_code',align : 'left',width: 100,align : 'left'},
								{display : '结算方式名称',name : 'pay_name',align : 'left',width: 100,align : 'left'},
								{display : '病人类别编码',name : 'patient_code',align : 'left',width: 100,align : 'left'},
								{display : '病人类别名称',name : 'patient_name',align : 'left',width: 150,align : 'left'},
								{display : '金额',name : 'charge_money',align : 'left',width: 100,align : 'right', totalSummary: { 
									render: function (suminf, column, cell) {
			                        	return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, '2', 1)+ '</div>';
			                    	} 
								}},
								{display : '是否退费',name : 'is_back',align : 'left',width: 100,align : 'left'}
								],
					dataAction: 'server',dataType: 'server',usePager:false,url:'queryHisBalI.do',width: '100%', height: $(window).height()-90, checkbox: true,
					rownumbers:true,delayLoad:true,selectRowButtonOnly:true,title:'结算数据'
					
				});
		gridManager = $("#balgrid").ligerGetGridManager();
	
	}
    
    
	 //查询住院结算数据
    function queryBal(){
		/* var isQuery=false;
		//条件发生改变才查询
		if(balGrid.options.parms.length>0){
			$.each(balGrid.options.parms,function(i,obj){
				if(obj.name=="charge_date_b" && obj.value!=$("#charge_date_b").val()){
					isQuery=true;
					return false;
				}else if(obj.name=="charge_date_e" && obj.value!=$("#charge_date_e").val()){
					isQuery=true;
					return false;
				}else if(obj.name=="rep_no" && obj.value!=$("#rep_no").val()){
					isQuery=true;
					return false;
				}else if(obj.name=="pay_type_code" && obj.value!=$("#pay_type_code").val()){
					isQuery=true;
					return false;
				}else if(obj.name=="charge_code" && obj.value!=$("#charge_code").val()){
					isQuery=true;
					return false;
				}
				
			});	
		}else{
			isQuery=true;
		}
		if(!isQuery)return; */
		
    	balGrid.options.parms = [];
    	balGrid.options.parms.push({name : 'charge_date_b',value : $("#charge_date_b").val()});
    	balGrid.options.parms.push({name : 'charge_date_e',value : $("#charge_date_e").val()});
    	balGrid.options.parms.push({name : 'rep_no',value : $("#rep_no").val()});
    	balGrid.options.parms.push({name : 'charge_code',value : $("#charge_code").val()});
    	balGrid.options.parms.push({name : 'pay_type_code',value : liger.get("pay_type_code").getValue()});
    	balGrid.options.parms.push({name : 'io_type',value : liger.get("io_type").getValue()});
    	balGrid.loadData(balGrid.where);//加载查询条件
    	//balGrid._setWidth("100%");
     }
	 
	//生成凭证
	function createVouch(){
		/* if($("#charge_date_b").val().substring(0,7)!=$("#charge_date_e").val().substring(0,7)){
			$.ligerDialog.error("不能跨月生成凭证！");
			return;
		} */
		if(liger.get("template_code").getValue()==""){
			$.ligerDialog.error("请选择凭证模板！");
			return;
		}
		if(balGrid.getData().length==0){
			$.ligerDialog.error("请先查询数据！");
			return;
		}
		
		var sel_code="";
		var busi_no="";
		var data = gridManager.getCheckedRows();
        if (data.length>0){
            $(data).each(function (i,obj){
            	var isBuSiNo=false;
            	if(sel_code!=""){
            		var str=sel_code.split(",");
            		for(var i=0;i<str.length;i++){
            			if(obj.rep_no+obj.charge_code==str[i]){
            				isBuSiNo=true;
            				return true;
            			}
            		}
            	} 
            	if(isBuSiNo) return false;
            	sel_code=sel_code+this.rep_no+this.charge_code+",";
            	busi_no=busi_no+this.rep_no+"-"+this.charge_code+",";
			});
            
            sel_code=sel_code.substring(0,sel_code.length-1);
        }else{
        	//不勾选取所有的日报序号+收款员编码，判断是否生成凭证
        	$.each(balGrid.getData(),function (){
        		var isBuSiNo=false;
            	if(busi_no!=""){
            		var str=busi_no.split(",");
            		for(var i=0;i<str.length;i++){
            			if(this.rep_no+"-"+this.charge_code==str[i]){
            				isBuSiNo=true;
            				return true;
            			}
            		}
            	} 
            	if(isBuSiNo) return false;
            	busi_no=busi_no+this.rep_no+"-"+this.charge_code+",";
			});
        }
      
        busi_no=busi_no.substring(0,busi_no.length-1);
		var para={
				charge_date_b:$("#charge_date_b").val(),
				charge_date_e:$("#charge_date_e").val(),
				template_code:liger.get("template_code").getValue(),
				vouch_date:$("#vouch_date").val(),
				io_type:liger.get("io_type").getValue(),
				sel_code:sel_code,
				busi_no:busi_no
		};
		
		$.ligerDialog.confirm('确定生成凭证?', function (yes){
        	if(yes){
        		var loadIndex = layer.load(1);
        		ajaxJsonObjectBylayer("queryHisBalVouchI.do",para,function(responseData){	
        			//console.log(responseData);
        			layer.close(loadIndex);
        			
        			var para={
        				url:'hrp/acc/accvouch/superVouch/superVouchMainPage.do?isCheck=false&openType=autoVouch',
        				title:'会计凭证',
        				width:0,
        				height:0,
        				isShowMin:true,
        				isModal:true,
        				data:{busi_log_table:'ACC_BUSI_LOG_0104',busi_type_code:'0104',
        					auto_id:responseData.auto_id}
        			};
        			//期末处理生成凭证格式：data:{vouch:responseData,busi_log_table:'ACC_VOUCH_SOURCE',busi_type_code:'0103'}
        			parent.openDialog(para);
          		},layer,loadIndex);
        	}
        }); 

		
	} 
	
	function openSuperVouch(vouch_id){
		parent.openFullDialog('hrp/acc/accvouch/superVouch/superVouchMainPage.do?vouch_id='+vouch_id,'会计凭证',0,0,true,true);
	}	
	
	function printDate(){
		 if(balGrid.getData().length==0){
   		
			$.ligerDialog.error("请先查询数据！");
			
			return;
		}
   	var heads={
	      		  //"isAuto": true/false 默认true，页眉右上角默认显示页码
	      		  "rows": [
							{"cell":0,"value":"结算日期："+$("#charge_date_b").val()+"至"+$("#charge_date_e").val(),"colSpan":"5"},
	      		  ]
	      	};
	   		
  		var printPara={
  			rowCount:1,
  			title:'住院结算',
  			columns: JSON.stringify(balGrid.getPrintColumns()),//表头
  			class_name: "com.chd.hrp.acc.service.autovouch.his.HisAccChargeService",
			method_name: "queryHisBalIPrint",
			bean_name: "hisAccChargeService",
			heads: JSON.stringify(heads)//表头需要打印的查询条件,可以为空
  			};
   	
  		//执行方法的查询条件
  		$.each(balGrid.options.parms,function(i,obj){
  			printPara[obj.name]=obj.value;
   	});
  		
   	officeGridPrint(printPara); 
	
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;" >

	<div id="pageloading" class="l-loading" style="display: none"></div>
	
	<table width="100%" cellpadding="0" cellspacing="0" class="l-table-edit" style="font-size:15px">
   		 <tr>
            <td align="right" class="l-table-edit-td"  style=padding-left:20px;width:120px;"><b><font color="red">*</font></b>结算日期：</td>
            <td align="left" class="l-table-edit-td">
				<table>
					<tr>
						<td>
							<input type="text" id="charge_date_b" style="font-size:12px" ltype="text"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
						<td align="right" class="l-table-edit-td"  >
							至
						</td>
						<td>
							<input type="text" id="charge_date_e" style="font-size:12px" ltype="text"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
            		</tr>
				</table>
			</td>
            
            <td align="right" class="l-table-edit-td"  style=padding-left:20px;">医嘱类别：</td>
            <td align="left" class="l-table-edit-td"><input name="io_type" type="text" id="io_type" ltype="text" /></td>
           
        </tr> 
        <tr>
	        <td align="right" class="l-table-edit-td"  style="padding-left:20px;width:120px;">结算方式：</td>
			<td align="left" class="l-table-edit-td" style="width:120px;">
			    <input type="text" id="pay_type_code" ltype="text" />
			</td>
			
			<td align="right" class="l-table-edit-td"  style=padding-left:20px;">日报序号：</td>
            <td align="left" class="l-table-edit-td"><input name="rep_no" type="text" id="rep_no" ltype="text" /></td>
            
            <td align="right" class="l-table-edit-td"  style=padding-left:20px;">收款员：</td>
            <td align="left" class="l-table-edit-td"><input name="charge_code" type="text" id="charge_code" ltype="text" /></td>
			
            <td align="right">
            	<button accessKey="Q" onclick="queryBal();"><b>查询（<u>Q</u>）</b></button>
            	&nbsp;&nbsp;&nbsp;&nbsp;
            	<button  onclick="printDate();"><b>打 印</b></button>
            	&nbsp;&nbsp;
            </td>
        </tr>
    </table>
    <hr/>
    <table width="100%" cellpadding="0" cellspacing="0" class="l-table-edit" style="font-size:15px">
    <tr>
    	<td align="right" class="l-table-edit-td"  style="padding-left:20px;width:120px;"><b><font color="red">*</font></b>凭证模板：</td>
		<td align="left" class="l-table-edit-td" style="width:120px;">
		    <input type="text" id="template_code" ltype="text" />
		</td>
		<td align="right" class="l-table-edit-td" style="width:120px;"><b><font color="red">*</font></b>凭证日期：</td>
		<td align="left" class="l-table-edit-td" style="width:120px;">
			<input class="Wdate" name="vouch_date"  style="width:105px" type="text"  id="vouch_date"  ltype="text"  onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
		</td>
    	<td algin="left">
    		<button accessKey="S" onclick="createVouch();" name="createVouchBtn" title="选择按收款员生成，不选择按日期全部生成"><b>生成凭证（<u>S</u>）</b></button>
    	</td>
    </tr>
    </table>
    
    <div id="balgrid"></div>
	
</body>
</html>
