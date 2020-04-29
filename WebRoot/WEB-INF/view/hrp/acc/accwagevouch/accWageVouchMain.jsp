<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath(); 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>   
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script src='<%=path%>/lib/hrp/acc/superReport/ktLayer.js'  type="text/javascript"></script>
<script src="<%=path%>/lib/layer-v2.3/layer/layer.js" type="text/javascript"></script>
<script type="text/javascript">	


    var grid;
    var gridManager = null;
    var userUpdateStr;
    
    var begin_dates;
    
    $(function (){
		loadDict();
    	loadHead(null);	//加载数据
    });
    
    
    //查询
    function  query(){
   		grid.options.parms=[];
   		grid.options.newPage=1;
   		
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'wage_code',value:liger.get("wage_code").getValue()}); 
    	grid.options.parms.push({name:'acc_year',value:begin_dates.getValue().substring(0,4)}); 
    	grid.options.parms.push({name:'acc_month',value:begin_dates.getValue().substring(5,7)}); 
    	
    	//加载查询条件
    	grid.loadData(grid.where);
	}
    
    
    //加载grid
	function loadHead(){
		grid = $("#maingrid").ligerGrid({
 			columns: [ 
            	{ display: '转账方案编码', name: 'scheme_code', align: 'left',width:'30%',render : 
            		function(rowdata, rowindex,value) {
						return "<a href=javascript:openUpdate('"+rowdata.scheme_code   + "|" + 
						rowdata.group_id   + "|" + 
						rowdata.hos_id   + "|" + 
						rowdata.copy_code   + "|" + 
						rowdata.acc_year   +"')>"+rowdata.scheme_code+"</a>";
					}
				},
				
				{ display: '转账方案名称', name: 'scheme_name', align: 'left',width:'30%'},
				
                     /* { display: '摘要', name: 'summary', align: 'left'
					 }, */
				{ display: '凭证号', name: 'vouch_type_name', align: 'left',width:'30%'}
			],
			
			dataAction: 'server',dataType: 'server',usePager:false,url:'queryAccWageVouch.do',width: '100%', 
			height: '100%', checkbox: true,rownumbers:true,delayLoad: true,selectRowButtonOnly:true,//heightDiff: -10,
			toolbar: { items: [
				{ text: '查询', id:'search', click: query,icon:'search' },
                { line:true },
                
                { text: '添加', id:'add', click: openAddWageSubj,icon:'add' },
                { line:true },
                
                { text: '删除', id:'delete', click: itemclick,icon:'delete' },
                { line:true },
                
                { text: '转账', id:'carry', click: itemclick,icon:'communication' },
                { line:true },
                
                { text: '继承', id:'extend', click: itemclick,icon:'extend' },
                { line:true },
                
                { text: '凭证维护', id:'vouchlist', click: showVhouchList,icon:'account' },
                { line:true }
                
			]},
			
			onDblClickRow : function (rowdata, rowindex, value){
				openUpdate(
					rowdata.scheme_code   + "|" + 
					rowdata.group_id   + "|" + 
					rowdata.hos_id   + "|" + 
					rowdata.copy_code  + "|" + 
					rowdata.acc_year 
				);
			} 
		});

        gridManager = $("#maingrid").ligerGetGridManager();
	}
    
    
    //添加页面跳转
    function openAddWageSubj(){
 		parent.openFullDialog('hrp/acc/accwagevouch/accWageVouchAddPage.do','添加',0,0,true,true);
 	}
	
    
    function itemclick(item){ 
        if(item.id){
			switch (item.id){
                case "carry":
                	 var data = gridManager.getCheckedRows();
                     if (data.length != 1){
                     	$.ligerDialog.warn('请选择一条数据进行转账操作');
                     	return ; 
                     }
                    	 
					var year_Month = begin_dates.getValue();
					var ParamVo =[];
                    
					$(data).each(function (){	
 						ParamVo.push(
 							//表的主键
 							this.scheme_code   +"@"+ 
 							this.group_id   +"@"+ 
 							this.hos_id   +"@"+ 
 							this.copy_code +"@"+ 
 							year_Month.substring(0, 4).toString() +"@"+ 
 							this.wage_code +"@"+ 
 							year_Month.substring(5, 7).toString()
						);
 					});
                         
					var loadIndex = layer.load(1);
                         
					ajaxJsonObjectBylayer("addTransferAccWageVouch.do?isCheck=false",{ParamVo:ParamVo.toString()},
							function(responseData){	
    	        				layer.close(loadIndex);
		  	        			var para={
		  	        				url:'hrp/acc/accvouch/superVouch/superVouchMainPage.do?isCheck=false&openType=autoVouch',
		  	        				title:'会计凭证',
		  	        				width:0,
		  	        				height:0,
		  	        				isShowMin:true,
		  	        				isModal:true,
		  	        				data:{auto_id:responseData.vouch_id, busi_log_table:'ACC_BUSI_LOG_ZZ', busi_type_code:'Z010',busi_no:data[0].scheme_code}
		  	        			};
  	        					parent.openDialog(para);
							},layer,loadIndex);
                   return;
                case "delete":
                    var data = gridManager.getCheckedRows();
                    if (data.length == 0){
                    	$.ligerDialog.warn('请选择行');
                    	return ; 
                    }
                    	
					var ParamVo =[];
                        
                    $(data).each(function (){	
						ParamVo.push(//表的主键
							this.scheme_code   +"@"+ 
							this.group_id   +"@"+ 
							this.hos_id   +"@"+ 
							this.copy_code +"@"+ 
							this.acc_year 
						);
					});
                    
                    
					$.ligerDialog.confirm('确定删除?', function (yes){
                       	if(yes){
                       		ajaxJsonObjectByUrl("deleteAccWageVouch.do",{ParamVo : ParamVo.toString()},function (responseData){
                       			if(responseData.state=="true"){
                       				query();
                       			}
                           	});
                       	}
					}); 
                    return;
                case "extend":
                	
                	var data;
                	var year_Month = begin_dates.getValue();
                     $.ligerDialog.confirm('确定继承吗?', function (yes){
                     	if(yes){
                     		ajaxJsonObjectByUrl("extendAccWageVouch.do?isCheck=false&acc_year="+year_Month.substring(0, 4).toString(),{},function (responseData){
                     			if(responseData.state=="true"){
                     				query();
                     			}
                         	});
                     	}
                     }); 
                    
                 return;
                case "Excel":
                case "Word":
                case "PDF":
                case "TXT":
                case "XML":
                    $.ligerDialog.waitting('导出中，请稍候...');
                    setTimeout(function ()
                    {
                        $.ligerDialog.closeWaitting();
                        if (item.id == "Excel")
                            $.ligerDialog.success('导出成功');
                        else
                            $.ligerDialog.warn('导出失败');
                    }, 1000);
                    return;
            }   
        }
        
    }
    
	//凭证维护
	function showVhouchList(){
		parent.$.ligerDialog.open({ 
			title: '凭证维护', 
			width: $(window).width() - 20, 
			height: $(window).height() - 50, 
			url: 'hrp/acc/termend/accTermendTemplateVouchPage.do?isCheck=false&template_type_code=Z010&acc_year='+begin_dates.getValue().substring(0,4)+'&acc_month='+begin_dates.getValue().substring(5,7),
			model: true, showMax: false, showToggle: false, showMin: false, isResize: true
		}); 
	}
    
    //修改页跳转
    function openUpdate(obj){
    	
		var vo = obj.split("|");
		var parm = "scheme_code="+
			vo[0]   +"&group_id="+ 
			vo[1]   +"&hos_id="+ 
			vo[2]   +"&copy_code="+ 
			vo[3]  +"&acc_year="+ 
			vo[4]; 
		
		parent.openFullDialog('hrp/acc/accwagevouch/accWageVouchUpdatePage.do?'+ parm,'修改',0,0,true,true);
		
	}
    
    
  	//字典下拉框
	function loadDict() {
		autocomplete("#wage_code", "../queryAccWage.do?isCheck=false", "id","text", true, true);
		
		begin_dates = $("#begin_date").etDatepicker({
		    view: "months",
		    minView: "months",
		    dateFormat: "yyyy.mm",
		    width:'162',
		    defaultDate: "${wage_year_month}",
		});
		
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
	
		<div id="topmenu"></div>
	
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	 	<tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">期&nbsp;&nbsp;间：</td>
            <td align="left" class="l-table-edit-td"><input class="Wdate"
				name="begin_date" type="text" id="begin_date"
				ltype="text" validate="{required:true,maxlength:20}"
				 /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">工资套：</td>
            <td align="left" class="l-table-edit-td"><input name="wage_code" type="text" id="wage_code" /></td>
            <td align="left"></td>
        </tr> 
	
    </table>

	<div id="maingrid"></div>

</body>
</html>
