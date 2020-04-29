/**
 * 辅助核算	初始账
 */
 var grid;
    
    var gridManager = null;
    
    var userUpdateStr;
    
    var cusData;
    var deptData;
    var empData;
    var projData;
    var storeData;
    var sourceData;
    var supData;
    var checkItemData;

    
    $(function ()
    {
		loadDict();
    	
    	loadHead(null);	//加载数据
    	
    	loadColDictData(null);
    	
		 $("#bal_os").ligerTextBox({ disabled: true });
		 
		 $("#sum_od").ligerTextBox({ disabled: true });
		 
		 $("#sum_oc").ligerTextBox({ disabled: true });
		 
		 $("#end_os").ligerTextBox({ disabled: true });

    });
    //查询
    function  Sorts(a,b){
        return a.Sort-b.Sort;
    }
    function loadHead(){
    	var url="queryGridTitle.do?isCheck=false";
    	$.getJSON(url,{ page: 1,pagesize:10 ,Rnd: Math.random()},
    		function(json){
    		var colunmName="";
    		var result=0;
    		json.sort(Sorts);
    		for (var  i= 0; i < json.length; i++) {
    			if(json[i].id==1){
    				colunmName+=",{name:'check"+i+"',display:'"+json[i].text+"',align : 'left', editor: { " +
    						"type: 'select', " +
    						"url: '../../sys/queryDeptDict.do?isCheck=false', " +
    						"valueField: 'id', " +
    						"textField: 'text' },render:function(rowdata,index,value){" +
    						"for (var i = 0; i < deptData.length; i++)" +
    						"{if (deptData[i].id ==value)" +
    						"return deptData[i].text } " +
    						"}}"	
    			}
    			if(json[i].id==2){
    				colunmName+=",{name:'check"+i+"',display:'"+json[i].text+"',align : 'left',editor: { "+
    				"type: 'select', " +
					"url: '../../sys/queryEmpDict.do?isCheck=false', " +
					"valueField: 'id', " +
					"textField: 'text' },render:function(rowdata,index,value){" +
					"for (var i = 0; i < empData.length; i++)" +
					"{if (empData[i].id ==value)" +
					"return empData[i].text } " +
					"}}"	
    			}
    			if(json[i].id==3){
    				colunmName+=",{name:'check"+i+"',display:'"+json[i].text+"',align : 'left',editor: { "+
    				"type: 'select', " +
					"url: '../../sys/queryProjDict.do?isCheck=false', " +
					"valueField: 'id', " +
					"textField: 'text' },render:function(rowdata,index,value){" +
					"for (var i = 0; i < projData.length; i++)" +
					"{if (projData[i].id ==value)" +
					"return projData[i].text } " +
					"}}"	
    			}
    			if(json[i].id==4){
    				colunmName+=",{name:'check"+i+"',display:'"+json[i].text+"',align : 'left',editor: { "+
    				"type: 'select', " +
					"url: '../../sys/queryStoreDict.do?isCheck=false', " +
					"valueField: 'id', " +
					"textField: 'text' },render:function(rowdata,index,value){" +
					"for (var i = 0; i < storeData.length; i++)" +
					"{if (storeData[i].id ==value)" +
					"return storeData[i].text } " +
					"}}"	
    			}
    			if(json[i].id==5){
    				colunmName+=",{name:'check"+i+"',display:'"+json[i].text+"',align : 'left',editor: { "+
    				"type: 'select', " +
					"url: '../../sys/queryCusDict.do?isCheck=false', " +
					"valueField: 'id', " +
					"textField: 'text' },render:function(rowdata,index,value){" +
					"for (var i = 0; i < cusData.length; i++)" +
					"{if (cusData[i].id ==value)" +
					"return cusData[i].text } " +
					"}}"	
    			}
    			if(json[i].id==6){
    				"type: 'select', " +
					"url: '../../sys/querySupDict.do?isCheck=false', " +
					"valueField: 'id', " +
					"textField: 'text' },render:function(rowdata,index,value){" +
					"for (var i = 0; i < supData.length; i++)" +
					"{if (supData[i].id ==value)" +
					"return supData[i].text } " +
					"}}"	
    			}
    			if(json[i].id==7){
    				colunmName+=",{name:'check"+i+"',display:'"+json[i].text+"',align : 'left',editor: { "+
    				"type: 'select', " +
					"url: '../../sys/querySourceDict.do?isCheck=false', " +
					"valueField: 'id', " +
					"textField: 'text' },render:function(rowdata,index,value){" +
					"for (var i = 0; i < sourceData.length; i++)" +
					"{if (sourceData[i].id ==value)" +
					"return sourceData[i].text } " +
					"}}"	
    			}
    			if(json[i].id>7){
    				colunmName+=",{name:'check"+i+"',display:'"+json[i].text+"',align : 'left',editor: { "+
    				"type: 'select', " +
					"url: '../queryCheckItem.do?isCheck=false', " +
					"valueField: 'id', " +
					"textField: 'text' },render:function(rowdata,index,value){" +
					"for (var i = 0; i < checkItemData.length; i++)" +
					"{if (checkItemData[i].id ==value)" +
					"return checkItemData[i].text } " +
					"}}"	
    			}
     		}; 
    		colunmName+=",{name:'bal_os',display:'年初余额',align:'left',editor: { type: 'text' }}";
    		colunmName+=",{name:'sum_od',display:'累计借方',align:'left',editor: { type: 'text' }}";
    		colunmName+=",{name:'sum_oc',display:'累计贷方',align:'left',editor: { type: 'text' }}";
    		colunmName+=",{name:'end_os',display:'期初余额',align:'left',render:function(rowdata,index,value){" +
    				"return Number(rowdata.bal_os)+Number(rowdata.sum_od)-Number(rowdata.sum_oc);" +
    				"}}";
    		eval(	
    				"grid=$('#maingrid').ligerGrid({"+
    					
    				"columns: ["+colunmName+"],"+
    				
    				"dataAction:'server',"+
    				
    				"dataType:'server',"+
    				
    				"usePager:true,"+
    				
    				"checkbox: true,"+
    				
    				"height: '100%',"+
    				
    				"width: '100%',"+
    				
    				"enabledEdit: true,"+
    				
    				"checkbox: true,rownumbers:true,enabledEdit: true,"+
    				
    				"toolbar: { items: ["+
    				"{ text: '添加', id:'add', click: addNewRow,icon:'add' },"+
                  	"{ line:true },"+
                  	"{ text: '删除', id:'del', click: deleteRow,icon:'delete' },"+
                  	"{ line:true },"+
                  	"{ text: '全部保存', id:'save', click: onAfterEdit,icon:'save' }"+
    	    				"]}"+
    				"});"+
    				
    				"gridManager = $('#maingrid').ligerGetGridManager();"
    				
    			);
    	});
    }
    
    function onAfterEdit(){
    	gridManager.endEdit();
    }
    
    function deleteRow()
    { 
    	grid.deleteSelectedRow();
    }
    function addNewRow()
    {
    	grid.addEditRow();
    } 

    function itemclick(item){ 
        if(item.id)
        {
            switch (item.id)
            {
                case "delete":
                    var data = gridManager.getCheckedRows();
                    if (data.length == 0){
                    	$.ligerDialog.error('请选择行');
                    }else{
                        var ParamVo =[];
                        $(data).each(function (){					
							ParamVo.push(
							//表的主键
							this.check_id 
							)
                        });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteAccLederCheck.do",{ParamVo : ParamVo},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
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
                            $.ligerDialog.error('导出失败');
                    }, 1000);
                    return;
            }   
        }
        
    }
    
    function loadColDictData(){
		$.post("../../sys/queryCusDict.do?isCheck=false",null,function(data){cusData = data;},"json");
		$.post("../../sys/queryDeptDict.do?isCheck=false",null,function(data){deptData = data;},"json");
		$.post("../../sys/queryEmp.do?isCheck=false",null,function(data){empData = data;},"json");
		$.post("../../sys/queryProjDictDict.do?isCheck=false",null,function(data){projData = data;},"json");
		$.post("../../sys/queryStoreDictDict.do?isCheck=false",null,function(data){storeData = data;},"json");
		$.post("../../sys/querySourceDict.do?isCheck=false",null,function(data){sourceData = data;},"json");
		$.post("../../sys/querySupDictDict.do?isCheck=false",null,function(data){supData = data;},"json");
		$.post("../queryCheckItem.do?isCheck=false",null,function(data){checkItemData = data;},"json");
	}
    
    function saveAccLeder(){
    	
    	 var data = gridManager.getCheckedRows();
         if (data.length == 0){
         	$.ligerDialog.error('请选择行');
         }else{
             var ParamVo =[];
             $(data).each(function (){					
					ParamVo.push(
						this.check0+"@"+this.check1+"@"+this.check2+"@"+this.check3+"@"+this.bal_os+"@"+this.sum_od+"@"+this.sum_oc+"@"+liger.get("subj_code").getValue().split(".")[1]+"@"+liger_id
					)
             });
           ajaxJsonObjectByUrl("saveAccLederCheck.do",{ParamVo : ParamVo},function (responseData){
                 if(responseData.state=="true"){
                 	query();
                 }
            });
         }
    	
    }
    
    function openUpdate(obj){
    	
		var vo = obj.split("|");
		var parm = 
			vo[0] 
		
    	$.ligerDialog.open({ url : 'deptIncomeUpdatePage.do?isCheck=false&' + parm,data:{}, height: 500,width: 500, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAccLederCheck(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    }
    function loadDict(){
        //字典下拉框
    	autocomplete("#subj_code","../querySubj.do?isCheck=false","id","text",true,true);
     }   
    function collEndOs(){
    	var bal_os = $("#bal_os").val();
    	var sum_od = $("#sum_od").val();
    	var sum_oc = $("#sum_oc").val();
    	$("#end_os").val(Number(bal_os)+Number(sum_od)+Number(sum_oc));
    }