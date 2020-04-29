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
         loadDict()//加载下拉框
    	//加载数据
    	 loadHead(null);	
		 loadHotkeys();
    });
    //查询
    function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
        //根据表字段进行添加查询条件
		if(""==liger.get("acc_year_month").getValue()){
        	
        	$.ligerDialog.error('请选择会计期间');
        	return;
        }
		if(""==liger.get("mod_code").getValue()){
        	
        	$.ligerDialog.error('请选择系统名称');
        	return;
        }
		if(""==liger.get("acc_busi_type").getValue()){
        	
        	$.ligerDialog.error('请选择业务类型');
        	return;
        }
        
		grid.options.parms.push({name:'acc_year',value:liger.get("acc_year_month").getValue().split(".")[0]}); 
    	grid.options.parms.push({name:'acc_month',value:liger.get("acc_year_month").getValue().split(".")[1]});
		grid.options.parms.push({
			name : 'vouch_no_b',
			value : $("#vouch_no_b").val()
		});
		grid.options.parms.push({
			name : 'vouch_no_e',
			value : $("#vouch_no_e").val()
		});
		grid.options.parms.push({
			name : 'vouch_type_code',
			value : liger.get("vouch_type_code").getValue()
		});
		grid.options.parms.push({
			name : 'create_user',
			value : liger.get("create_user").getValue()
		});
		grid.options.parms.push({
			name : 'audit_user',
			value : liger.get("audit_user").getValue()
		});
		grid.options.parms.push({
			name : 'acc_user',
			value : liger.get("acc_user").getValue()
		});
		
		var log_table = liger.get("acc_busi_type").getValue().split("@")[1];
		
		var log_busi_type_code = liger.get("acc_busi_type").getValue().split("@")[0];
		
		grid.options.parms.push({
			name : 'log_table',
			value : log_table
		});

		grid.options.parms.push({
			name : 'log_busi_type_code',
			value : log_busi_type_code
		});
		
    	//加载查询条件
    	grid.loadData(grid.where);
     }
	
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [{
					display: '凭证号', name: 'vouch_no', align: 'left',
					align : 'left',width:90,render:function(rowdata){
				          if(rowdata.state==0){
				            return "<a href=javascript:openSuperVouch('"+rowdata.vouch_id+"')><div>"+rowdata.vouch_type_short+"-"+rowdata.vouch_no+"<span style='color:red;font-weight: bold;'> 废</span></div></a>";
				            
				          }else if(rowdata.state==-1){
				            return "<a href=javascript:openSuperVouch('"+rowdata.vouch_id+"')><div>"+rowdata.vouch_type_short+"-"+rowdata.vouch_no+"<span style='color:red;font-weight: bold;'> 稿</span></div></a>";
				            
				          }else{
				            
				            if(rowdata.vouch_type_short==null || rowdata.vouch_no==null){
				              
				              return "";
				            
				            }
				            return "<a href=javascript:openSuperVouch('"+rowdata.vouch_id+"')>"+rowdata.vouch_type_short+"-"+rowdata.vouch_no+"</a>";
				            
				          }
				        }
				},{
					display: '凭证日期', name: 'vouch_date', align: 'left'
				},{
					display: '摘要' ,name: 'summary', align: 'left', 
				},{
					display: '借方金额', name: 'debit', align: 'right',
					render:function(rowdata){
						 return formatNumber(rowdata.debit ==null ? 0 : rowdata.debit,2,1);
				 	 } 
				},{
					display: '贷方金额', name: 'credit', align: 'right',
					render:function(rowdata){
						 return formatNumber(rowdata.credit ==null ? 0 : rowdata.credit,2,1);
				 	 }
				},{
					display: '制单人', name: 'create_name', align: 'left' 
				},{
					display: '出纳签字人', name: 'cash_name', align: 'left' 
				},{
					display: '审核人', name: 'audit_name', align: 'left'
				},{
					display: '记账人', name: 'acc_name', align: 'left'
				} ],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryAccAutoVouch.do?isCheck=false',
			width: '100%', height: '100%', checkbox: true,rownumbers:true,
			delayLoad: true,//初始化加载，默认false
			selectRowButtonOnly:true,pageSize:500,//heightDiff: -10,
			toolbar: { items: [
				{ text: '查询（<u>Q</u>）', id:'search', click: query, icon:'search' },
				{ line:true },
				{ text: '删除（<u>D</u>）', id:'delete', click: remove, icon:'delete' },
				{ line:true },
				{ text: '打印', id:'print', click: printDate,icon:'print'}
			]}
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    //键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
		hotkeys('D', remove);
	}
    
    
	function openSuperVouch(vouch_id){
		parent.openFullDialog('hrp/acc/accvouch/superVouch/superVouchMainPage.do?vouch_id='+vouch_id,'会计凭证',0,0,true,true);
	}
  
    	
    function remove(){
    	
    	var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
			var ParamVo =[];
			var log_table = liger.get("acc_busi_type").getValue().split("@")[1];
			var flag = true;
			var error_str = "";
			$(data).each(function (){
				if(this.state > 1 || this.state == 0){
					error_str += '凭证:'+this.vouch_no+'-只能删除草稿、新建的凭证<br/>';
					flag = false;
				}
				if('${a005}' == 0 && '${sessionScope.user_id}' != this.create_user){
					error_str += '凭证:'+this.vouch_no+'-不允许删除他人的凭证<br/>';
					flag = false;
				}
				ParamVo.push(
					this.group_id   +"@"+ 
					this.hos_id   +"@"+ 
					this.copy_code   +"@"+ 
					'${sessionScope.user_id}'+"@"+
					log_table+"@"+
					this.vouch_id
				);
			});
			if(flag){
				$.ligerDialog.confirm('确定删除?', function (yes){
					if(yes){
						ajaxJsonObjectByUrl("deleteAccAutoVouch.do",{ParamVo : ParamVo.toString()},function (responseData){
							if(responseData.state=="true"){
								query();
							}
						});
					}
				});
			}else{
				$.ligerDialog.error(error_str);
			}
		}
	}
   
    function loadDict(){
    	$("#acc_year_month").ligerComboBox({
          	url: '../../queryYearMonth.do?isCheck=false',
          	valueField: 'id',
           	textField: 'text', 
           	selectBoxWidth: 180,
          	autocomplete: true,
          	width: 180
 		  });
    	var year_Month = '${acc_year_month}';

   	    liger.get("acc_year_month").setValue(year_Month.substring(0,4)+"."+year_Month.substring(4,6).toString());
		 
   	    liger.get("acc_year_month").setText(year_Month.substring(0,4)+"."+year_Month.substring(4,6).toString());
   	    autocomplete("#vouch_type_code", "../../queryVouchType.do?isCheck=false","id", "text", true, true);
   	    autocomplete("#acc_user", "../../../sys/queryUserDict.do?isCheck=false","id", "text", true, true);
 	    autocomplete("#audit_user", "../../../sys/queryUserDict.do?isCheck=false","id", "text", true, true,null,null,null,"180");
 	    autocomplete("#create_user", "../../../sys/queryUserDict.do?isCheck=false","id", "text", true, true);
 	    
   	    $("#vouch_no_b").ligerTextBox({width:80 });
		
		$("#vouch_no_e").ligerTextBox({width:80 });
		
		$("#acc_busi_type").ligerComboBox({
			width : 160
		});  
		
		$("#mod_code").ligerComboBox({
          	url: '../../../sys/querySysBusiMod.do?isCheck=false',
          	valueField: 'id',
           	textField: 'text', 
           	selectBoxWidth: 160,
          	autocomplete: true,
          	width: 160,
          	onSelected:function(value){
          		$("#acc_busi_type").ligerComboBox({
                  	url: '../../queryBusiType.do?isCheck=false&is_vouch=1&mod_code='+value,
                  	valueField: 'id',
                   	textField: 'text', 
                   	selectBoxWidth: 160,
                  	autocomplete: true,
                  	width: 160,
					onSuccess : function(data) {
						if(data.length>0){
							this.setValue(data[0].id);
						}
						/* 	this.setValue("0102@ACC_BUSI_LOG_0102"); */
					}
         		 });
          	},
			onSuccess : function(data) {
				this.setValue("01");
			}
 		 });
		
		
	}  
	
    function printDate(){
		 if(grid.getData().length==0){
  		
			$.ligerDialog.error("请先查询数据！");
			
			return;
		}
  	var heads={
	      		  //"isAuto": true/false 默认true，页眉右上角默认显示页码
	      		  "rows": [
							{"cell":0,"value":"会计期间："+$("#acc_year_month").val(),"colSpan":"5"},
							{"cell":3,"value":"系统名称："+liger.get("mod_code").getText(),"from":"right","align":"right","colSpan":"4"}  
	      		  ]
	      	};
	   		
 		var printPara={
 			rowCount:1,
 			title:'自动凭证维护',
 			columns: JSON.stringify(grid.getPrintColumns()),//表头
 			class_name: "com.chd.hrp.acc.service.autovouch.AccAutoVouchMaintainService",
			method_name: "queryAccAutoVouchPrint",
			bean_name: "accAutoVouchMaintainService",
			heads: JSON.stringify(heads)//表头需要打印的查询条件,可以为空
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
    <table cellpadding="0" cellspacing="0" class="l-table-edit" style="margin-left: 50px">
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:10px;"><b><font color="red">*</font></b>会计期间：</td>
            <td align="left" class="l-table-edit-td" colspan="3">
            	<input id="acc_year_month" name="acc_year_month" />
            </td>
            <td align="right" class="l-table-edit-td"  style="padding-left:10px;"><b><font color="red">*</font></b>系统名称：</td>
            <td align="left" class="l-table-edit-td">
            	<input id="mod_code" name="mod_code" />
            </td>
            <td align="right" class="l-table-edit-td"  style="padding-left:10px;"><b><font color="red">*</font></b>业务类型：</td>
            <td align="left" class="l-table-edit-td">
            	<input id="acc_busi_type" name="acc_busi_type" />
            </td>
        </tr> 
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:10px;">凭证号：</td>
            <td align="left" class="l-table-edit-td">
            	<input id="vouch_no_b" name="vouch_no_b" />
            </td>
            <td>至</td>
            <td align="left" class="l-table-edit-td" >
            	<input id="vouch_no_e" name="vouch_no_e" />
            </td>
            <td align="right" class="l-table-edit-td"  style="padding-left:10px;">凭证类型：</td>
            <td align="left" class="l-table-edit-td">
            	<input id="vouch_type_code" name="vouch_type_code" />
            </td>
            <td align="right" class="l-table-edit-td"  style="padding-left:10px;"> 制单人：</td>
            <td align="left" class="l-table-edit-td">
            	<input id="create_user" name="create_user" />
            </td>
        </tr>
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:10px;">审核人：</td>
            <td align="left" class="l-table-edit-td" colspan="3">
            	<input id="audit_user" name="audit_user" />
            </td>
            <td align="right" class="l-table-edit-td"  style="padding-left:10px;">记账人：</td>
            <td align="left" class="l-table-edit-td">
            	<input id="acc_user" name="acc_user" />
            </td>
        </tr>
    </table>
	<div id="maingrid"></div>
</body>
</html>
