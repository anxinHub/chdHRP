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
<script src='<%=path%>/lib/hrp/acc/superReport/ktLayer.js'  type="text/javascript"></script>
<script src="<%=path%>/lib/layer-v2.3/layer/layer.js" type="text/javascript"></script>
<script src="<%=path%>/pageoffice.js" type="text/javascript" id="po_js_main"></script>
<style>
     .info {
            padding: 0px;
            position:relative;
            /*height: 500px;*/
            display: none;/*避免闪动初始规定不显示*/
            border-top:solid 5px #D0E9F8;
            
      }
</style>
<script type="text/javascript">

    var grid;
    var balGrid;
    var gridManager = null;
    var radionManager=null; 
    var ktLayerObj;
    
    $(function ()
    {
        loadDict();//加载下拉框
    	loadChargeHead();	
    	f_setColumns(getSumType());
    	
    	ktLayerObj=$("#mainHead").ktLayer({
            // 参数配置
            direction:"up",
            BtnbgImg:{close:'<%=path%>/lib/hrp/acc/superReport/open.png',open:'<%=path%>/lib/hrp/acc/superReport/close.png'},
            speed:"100",
            bgColor:"#FFFFFF",//背景颜色
            closeHeight:0,//关闭状态高度
            Descript:["结算数据","结算数据"],//展开收起描述
       	 	open:function(){
       	 		//console.log(balGrid.gridheader.find(">div:first").width())
       	 		//balGrid._setWidth("100%");
       	 		balGrid._onResize();
            }
        });
    	//ktLayerObj.fn.open();
    	
    	loadBalHead();
    	$("#mainHead").css("top",$(window).height()-10);
    	//balGrid._setWidth("100%");
    	$(':button').ligerButton({width:80});
    	$("button[name='createVouchBtn']").ligerButton({width:100});
    });
    
    //查询门诊收入数据
    function  queryCharge(){
    	queryBal();
    	
    	var sumTypeId=getSumType();
    	var column_name=f_setColumns(sumTypeId);
    	if(column_name==""){
    		grid.set({ data: {}});
    		//grid._clearGrid();
    		//grid._setData({});
    		//grid.reRender();
    		return;
    	}
    	
    	var charge_date_b=$("#charge_date_b").val();var charge_date_e=$("#charge_date_e").val();
    	
    	var sArr = charge_date_b.split("-");var eArr = charge_date_e.split("-");
    	
    	var sRDate = new Date(sArr[0], sArr[1], sArr[2]);
    	
    	var eRDate = new Date(eArr[0], eArr[1], eArr[2]);
    	
		if((eRDate-sRDate)/(24*60*60*1000)>6){
			
			$.ligerDialog.error("请选择一周内的数据进行查询！");
			
			return;
			
		}
		grid.options.parms = [];
		grid.options.parms.push({name : 'column_name',value : column_name});
		grid.options.parms.push({name : 'charge_date_b',value : $("#charge_date_b").val()});
		grid.options.parms.push({name : 'charge_date_e',value : $("#charge_date_e").val()});
		grid.options.parms.push({name : 'rep_no',value : $("#rep_no").val()});
		grid.options.parms.push({name : 'charge_code',value : $("#charge_code").val()});
		grid.options.parms.push({name : 'sum_type_id',value : sumTypeId});
		grid._setUrl("queryHisChargeDataO.do");
		//grid.loadData(grid.where);//加载查询条件
     }
    
    //加载收入表头
	function loadChargeHead() {
		grid = $("#maingrid").ligerGrid({
					columns : [],
					dataAction: 'server',dataType: 'server',usePager:false,url:'queryHisChargeDataO.do',width: '100%', height: $(window).height()-125, checkbox: true,
					rownumbers:true,delayLoad:true,checkBoxDisplay : isCheckDisplay,selectRowButtonOnly:true,title:'收入数据'/*,heightDiff: -5*/
					
				});
	
		gridManager = $("#maingrid").ligerGetGridManager();
	}
    
    function isCheckDisplay(rowdata) {
		if (rowdata.charge_code == "合计")return false;
		return true;

	}

    //动态设置收入数据表头
	function f_setColumns(sumTypeId){ 
		 var column_name="";
		 var para={
				   charge_date_b:$("#charge_date_b").val(),
				   charge_date_e:$("#charge_date_e").val(),
				   charge_code:$("#charge_code").val(),
				   rep_no:$("#rep_no").val()
			};
		 ajaxJsonObjectByUrl("queryHisChargeHeadO.do?isCheck=false",para,function(responseData){
				//console.log(JSON.stringify(responseData))
			 	var columns = [
			    	{display: '日报序号',name : 'rep_no',width: 100,align : 'left', frozen: true},
			    ];
			 	
				if(sumTypeId==1){
					columns.push({display:'收款员编码',name:"charge_code",width: 100,align : 'left', frozen: true});
					columns.push({display:'收款员名称',name:"charge_name",width: 100,align : 'left', frozen: true});
					columns.push({display:'凭证编号',name:"vouch_no",width: 100,align : 'left', frozen: true,render:function(rowdata){
						if(rowdata.vouch_no=='-'){
							return rowdata.vouch_no;
						}else{
							return "<a href=javascript:openSuperVouch('"+rowdata.vouch_id+"')>"+rowdata.vouch_no+"</a>";
						}
					}});
					
				}else{
					columns.push({display:'科室编码',name:"dept_code",width: 100,align : 'left', frozen: true});
					columns.push({display:'科室名称',name:"dept_name",width: 100,align : 'left', frozen: true});
				}
				
				$.each(responseData.Rows,function(i,obj){
					//,render : function(rowdata, rowindex, value) { return formatNumber(value, 2, 1); }
					columns.push({display:obj.charge_kind_name,name:"c_"+obj.charge_kind_code.toLowerCase(),width: 100,align : 'right', totalSummary: {
						render: function (suminf, column, cell) {
                        	return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, '2', 1)+ '</div>';
                    	} 
					}});
					column_name=column_name+obj.charge_kind_code+",";
				});	
				
			    grid.set('columns', columns); 
			    //grid.reRender();
			},false);
		 
       return column_name;
     } 

    function loadDict(){

      	autocomplete("#template_code","queryAutoBusiTemplate.do?isCheck=false&busi_type_code=0102","id","text",true,true,"",true);
    	$("#charge_date_b").ligerTextBox({width:90});autodate("#charge_date_b", "yyyy-mm-dd", "month_first");
    	$("#charge_date_e").ligerTextBox({width:90});autodate("#charge_date_e", "yyyy-mm-dd", "month_first");
    	$("#rep_no").ligerTextBox({width:150});
    	$("#charge_code").ligerTextBox({width:150});
    	radionManager=$("#sum_type_id input:radio").ligerRadio();
    	$("#vouch_date").val('${vouch_date}');

	}
    
    function getSumType(){
    	var sumTypeId =1;
        $("#sum_type_id input:radio").each(function () {
        	if(this.checked){
        		sumTypeId=this.value;
        	}
        });
        return sumTypeId;
    }
    
    //加载结算表头
	function loadBalHead() {
		balGrid = $("#balgrid").ligerGrid({
			columns : [
	           	{display : '日报序号',name : 'rep_no',width: 150,align : 'left'}, 
				{display : '收款员编码',name : 'charge_code',width: 150,align : 'left'},
				{display : '收款员名称',name : 'charge_name',align : 'left',width: 150,align : 'left'},
				{display:'凭证编号',name:"vouch_no",width: 100,align : 'left',render:function(rowdata){
					if(rowdata.vouch_no==''){
						return rowdata.vouch_no;
					}else{
						return "<a href=javascript:openSuperVouch('"+rowdata.vouch_id+"')>"+rowdata.vouch_no+"</a>";
					}
				}},
				{display : '结算方式编码',name : 'pay_type_code',align : 'left',width: 100,align : 'left'},
				{display : '结算方式名称',name : 'pay_name',align : 'left',width: 100,align : 'left'},
				{display : '病人类别编码',name : 'patient_code',align : 'left',width: 100,align : 'left'},
				{display : '病人类别名称',name : 'patient_name',align : 'left',width: 150,align : 'left'},
				{display : '金额',name : 'charge_money',align : 'left',width: 150,formatter: "###,##0.00",align : 'right', totalSummary: {
					render: function (suminf, column, cell) {
                       	return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, '2', 1)+ '</div>';
                   	} 
				}},
				{display : '是否退费',name : 'is_back',align : 'left',width: 100,align : 'left'}
			],
			dataAction: 'server',dataType: 'server',usePager:false,url:'queryHisBalO.do',width: '100%', height: $(window).height()/2-50, checkbox: false,
			rownumbers:true,delayLoad:true,selectRowButtonOnly:true,title:'结算数据'
			
		});
	
	}
    
    
	 //查询门诊结算数据
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
		if(grid.getData().length==0){
			$.ligerDialog.error("请先查询数据！");
			return;
		}
		
		var sel_code="";
		var busi_no="";
		var data = gridManager.getCheckedRows();
		var isChargeCode=true;
		$.each(grid.getData(),function (i,obj){
			if(!obj.charge_code){
				isChargeCode=false;
			}
			return false;
		});
		if(!isChargeCode){
			$.ligerDialog.error("只能按收款员生成凭证！");
			return;
		}
			
			
        if (data.length>0){
            $(data).each(function (){
            	/* if(sel_code!=""){
            		var str=sel_code.split(",");
            		for(var i=0;i<str;i++){
            			if(this.rep_no+this.charge_code==str[i]){
            				return true;
            			}
            		}
            	} */
            	
            	sel_code=sel_code+(this.rep_no==undefined?"":this.rep_no)+this.charge_code+",";
            	busi_no=busi_no+(this.rep_no==undefined?"":this.rep_no)+"-"+this.charge_code+",";
			});
            
            sel_code=sel_code.substring(0,sel_code.length-1);
        }else{
        	//不勾选取所有的日报序号+收款员编码，判断是否生成凭证
        	$.each(grid.getData(),function (){
        		if(this.charge_code=="合计"){
        			return true;
        		}
        		
        		var isBuSiNo=false;
            	if(busi_no!=""){
            		var str=busi_no.split(",");
            		for(var i=0;i<str.length;i++){
            			if((this.rep_no==undefined?"":this.rep_no)+"-"+this.charge_code==str[i]){
            				isBuSiNo=true;
            				return true;
            			}
            		}
            	} 
            	if(isBuSiNo) return false;
            	busi_no=busi_no+(this.rep_no==undefined?"":this.rep_no)+"-"+this.charge_code+",";
			});
        }
        busi_no=busi_no.substring(0,busi_no.length-1);
        
		var para={
				charge_date_b:$("#charge_date_b").val(),
				charge_date_e:$("#charge_date_e").val(),
				template_code:liger.get("template_code").getValue(),
				vouch_date:$("#vouch_date").val(),
				sel_code:sel_code,
				busi_no:busi_no
		};
		
		$.ligerDialog.confirm('确定生成凭证?', function (yes){
        	if(yes){
        		var loadIndex = layer.load(1);
        		ajaxJsonObjectBylayer("queryHisChargeVouchO.do",para,function(responseData){	
        			//console.log(responseData);
        			layer.close(loadIndex);
        			
        			var para={
        				url:'hrp/acc/accvouch/superVouch/superVouchMainPage.do?isCheck=false&openType=autoVouch',
        				title:'会计凭证',
        				width:0,
        				height:0,
        				isShowMin:true,
        				isModal:true,
        				/* data:{vouch:responseData,busi_log_table:'ACC_BUSI_LOG_0102',busi_type_code:'0102',
        					busi_no:busi_no,template_code:liger.get("template_code").getValue()} */
       					data:{auto_id:responseData.auto_id,busi_log_table:'ACC_BUSI_LOG_0102',busi_type_code:'0102',}
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
		 if(grid.getData().length==0){
    		
			$.ligerDialog.error("请先查询数据！");
			
			return;
		}
    	var heads={
	      		  //"isAuto": true/false 默认true，页眉右上角默认显示页码
	      		  "rows": [
							{"cell":0,"value":"收费日期："+$("#charge_date_b").val()+"至"+$("#charge_date_e").val(),"colSpan":"5"},
	      		  ]
	      	};
	   		
   		var printPara={
   			rowCount:1,
   			title:'门诊收费收入',
   			gridIndex:1,
   			columns: JSON.stringify(grid.getPrintColumns()),//表头
   			class_name: "com.chd.hrp.acc.service.autovouch.his.HisAccChargeService",
			method_name: "queryHisChargeDataOPrint",
			bean_name: "hisAccChargeService",
			heads: JSON.stringify(heads)//表头需要打印的查询条件,可以为空
   			};
    	
   		//执行方法的查询条件
   		$.each(grid.options.parms,function(i,obj){
   			printPara[obj.name]=obj.value;
    	});
   		
    	officeGridPrint(printPara); 
	
	}
	
	function printData(){
		 if(balGrid.getData().length==0){
   		
			$.ligerDialog.error("请先查询数据！");
			
			return;
		}
   	var heads={
	      		  //"isAuto": true/false 默认true，页眉右上角默认显示页码
	      		  "rows": [
							{"cell":0,"value":"收费日期："+$("#charge_date_b").val()+"至"+$("#charge_date_e").val(),"colSpan":"5"},
	      		  ]
	      	};
	   		
  		var printPara={
  			rowCount:1,
  			title:'门诊收费结算',
  			gridIndex:2,
  			columns: JSON.stringify(balGrid.getPrintColumns()),//表头
  			class_name: "com.chd.hrp.acc.service.autovouch.his.HisAccChargeService",
			method_name: "queryHisBalOPrint",
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
	<div id="mainHead" style="height:0px;position:absolute;">
		<div class="info">
			<div id="balgrid"></div>
		</div>
	</div>
	<table width="100%" cellpadding="0" cellspacing="0" class="l-table-edit" style="font-size:15px">
   		 <tr>
            <td align="right" class="l-table-edit-td"  style=padding-left:20px;width:120px;"><b><font color="red">*</font></b>收费日期：</td>
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
            
            <td align="right" class="l-table-edit-td"  style=padding-left:20px;">日报序号：</td>
            <td align="left" class="l-table-edit-td"><input name="rep_no" type="text" id="rep_no" ltype="text" /></td>
            
            <td align="right" class="l-table-edit-td"  style=padding-left:20px;">收款员：</td>
            <td align="left" class="l-table-edit-td"><input name="charge_code" type="text" id="charge_code" ltype="text" /></td>
        </tr> 
        <tr>
            
            <td align="right" class="l-table-edit-td"  style=padding-left:20px;">汇总方式：</td>
            <td align="left" class="l-table-edit-td">
            	 <div id="sum_type_id">
	        		 <input type="radio" value="1" name="sum_type" checked/>按收款员
	        		 <input type="radio" value="2" name="sum_type" />按科室
        		 </div>
            </td>
            <td align="right" colspan="4">
            	<button accessKey="Q" onclick="queryCharge();"><b>查询（<u>Q</u>）</b></button>
            	&nbsp;&nbsp;&nbsp;&nbsp;
            	<button  onclick="printDate();"><b>打印收入</b></button>
            	&nbsp;&nbsp;
            	<button  onclick="printData();"><b>打 印结算</b></button>
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
    
    <div id="maingrid"></div>
	
</body>
</html>
