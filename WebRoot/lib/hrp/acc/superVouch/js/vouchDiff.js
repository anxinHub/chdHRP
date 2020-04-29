/**
 * 差异标注
 */ 
//var oldValue ='流入';
var page="vouchDiff";
var grid;
var initRow=0;
var initMoneyText="";
//初始化辅助核算表格
function loadVouchCheckTable(){
	
    var columns=[];
    columns.push({name: "id",type: "int",editor:"DisabledEditor",hide:true,display: "id",align : 'left'});
    columns.push({name: "summary",type: "string",editor: "AutocompleteEditor",editorProps: parent.frames["vouchFrame"].grid.editorProps["summary"],wrap:true,display:"摘要",width:'300px',align : 'left'});
   
    //现金流量标注    
    columns.push({name: "diff_item_name",display:"标注项目",wrap:true,width:'350px',type: "string",editor: "AutocompleteEditor",editorProps: [],align : 'left'});
    columns.push({name: "diff_item_code",display:"diff_item_code",hide:true,type: "string",editor:"DisabledEditor",align : 'left'});
    columns.push({name: "money",type: "number",display:'金额',width : '100px',align : 'right'});
   
	
/*	if((!parent.diffJson || parent.diffJson.length==0) && $("#vouchId",parent.document).val()!=""){	
		var param={vouch_id:$("#vouchId",parent.document).val()};
		//console.log("param:",param);
		ajaxJsonObjectByUrl("querySuperVouchByDiff.do?isCheck=false",param,function (responseData){
			parent.diffJson=responseData.diff;
			initRow=parseInt(responseData.diff.length);
		},false);
	 }*/
    
	var isSelectable=true;//复选框
	var isSortable=false;//排序
	var isEmptyRow=true;
	
	//右键菜单
	initMenu();
	
 	if(parent.paraList["046"]==1){
     	$("input[name='saveDiffButton']").hide();
    }
	 
	if(isReadonly){
		$("input[name='controllerButton']").ligerButton({disabled: true});
    	$("input[name='controllerButton']").attr("disabled",true);
		isSelectable=false;
		isSortable=true;
		isEmptyRow=false;
		
		if(parent.paraList["046"]==0){
			//允许手工调整
			isSelectable=true;
			isSortable=false;
			isEmptyRow=true;
			isReadonly=false;
		}
	}else{
		$("input[name='saveDiffButton']").hide();
	}
	
    var options = {
        // add an empty row at the end of grid
        emptyRow: isEmptyRow,
        // enable sortable callbacks
        sortable: isSortable,
        // disable specific keys
        disableKeys: [],
        // move active cell when a row is removed
        moveOnRowRemove: true,
        // skip these cells on duplicate action
        skipOnDuplicate: ["id"],
        // set the initial order of table
        initialSort: {col: "id", order: "asc"},
        selectable: isSelectable,
        readonly : isReadonly 
    };

    //console.log("columns",columns);
    // initialize grid with data, column mapping and options
    grid = $(".sensei-grid-default").grid(parent.diffJson, columns, options);
    
    // register editors that are bundled with sensei grid
    grid.registerEditor(BasicEditor);
   // grid.registerEditor(BooleanEditor);
   // grid.registerEditor(TextareaEditor);
  //  grid.registerEditor(SelectEditor);    
    grid.registerEditor(AutocompleteEditor);
   // grid.registerEditor(DisabledEditor);
    grid.registerEditor(DateEditor);

    // register row actions
    // grid.registerRowAction(BasicRowActions);

    // example listeners on grid events
    grid.events.on("editor:save", function (data, $cell) {
    
    	var $row=grid.getCellRow($cell);
    	var isCheckVal=true;
    	//检查空字符串
         var regu = "^[ ]+$";
         var re = new RegExp(regu);
         if(re.test($cell.find("div").text())){
        	 isCheckVal=false;
             $cell.find("div").text("");
         }
        
         var columnName=$cell.data("column");
               
         if(isCheckVal && $cell.find("div").text()!="" && columnName=="money"){
        	 
        		//金额运算
        		reMoney=evelMoney($cell.find("div").text());
         	    if(reMoney!=null){
         	    	reMoney=toDecimal(reMoney);//格式化两位小数
              		
         	    }else{
         	    	isCheckVal=false;
         	    	reMoney="";
//                  console.info("isNaN","不是数字");
         	    }
        	
        	 $cell.find("div").text(formatNumber(reMoney,2,1));
     	     sumMoney();//合计金额
         }
         
         //字典下拉框保存事件
         if(columnName=="diff_item_name"){
        	// console.log(this.grid.editorProps[columnName]);
        	 saveCheckId(isCheckVal,$row,$cell,this.grid.editorProps[columnName],columnName);
         }
    	
    });
    grid.events.on("editor:load", function (data, $cell) {
        //console.info("set value in editor:", data, $cell);
    });
    grid.events.on("cell:select", function ($cell) {
       // console.info("active cell:", $cell);
    });
    grid.events.on("cell:clear", function (oldValue, $cell) {
       // console.info("clear cell:", oldValue, $cell);
    });
    grid.events.on("cell:deactivate", function ($cell) {
      //  console.info("cell deactivate:", $cell);
    });
    grid.events.on("row:select", function ($row) {
      //  console.info("row select:", $row);
    });
    grid.events.on("row:remove", function (data, row, $row) {
       // console.info("row remove:", data, row, $row);
    });
    grid.events.on("row:mark", function ($row) {
      //  console.info("row mark:", $row);
    });
    grid.events.on("row:unmark", function ($row) {
      //  console.info("row unmark:", $row);
    });
    grid.events.on("row:save", function (data, $row, source) {
     //   console.info("row save:", source, data);
        // save row via ajax or any other way
        // simulate delay caused by ajax and set row as saved
       setTimeout(function () {
            grid.setRowSaved($row);
          //  $('#vouchCheckDiv').scrollTop( $('#vouchCheckDiv')[0].scrollHeight );
        });
    });

    // implement basic sorting
    grid.events.on("column:sort", function (col, order, $el) {
      //  console.info("column sort:", col, order, $el);
        var sorted = _.sortBy(parent.diffJson, col);
        if (order === "desc") {
            sorted = sorted.reverse();
        }
        grid.updateData(sorted);
    });

    // render grid
    
    grid.render();
    if(!isReadonly){
    	loadCheckDict();//加载下拉列表
    	initCheckGridValue(isReadonly);
    }
   
    initRow=grid.getRows().length;
    sumMoney();//合计金额
    initMoneyText=$("#sum_money").text();

    
}


//合计金额
function sumMoney(){
	var sumMoney01=0.00;
	var sumMoney02=0.00;
	var sumMoney03=0.00;
	var sumMoney04=0.00;
	/*$.each(grid.getRows(),function (n,value) {		
   	     if(grid.getCellDataByKey(n, "money")!="" && !isNaN(grid.getCellDataByKey(n, "money"))){
   	    	sumMoney+=parseFloat(grid.getCellDataByKey(n, "money"));
   	     }
	});*/
	$.each(grid.getGridData(),function (n,obj) {		
  	     if(obj.money=="")obj.money=0;
  	     if(obj.diff_item_code.substring(0,2)=="01"){
  	    	sumMoney01+=parseFloat(obj.money.replace(/,/g,""));
  	     }else if(obj.diff_item_code.substring(0,2)=="02"){
  	    	sumMoney02+=parseFloat(obj.money.replace(/,/g,""));
  	     }else if(obj.diff_item_code.substring(0,2)=="03"){
  	    	sumMoney03+=parseFloat(obj.money.replace(/,/g,""));
  	     }else if(obj.diff_item_code.substring(0,2)=="04"){
  	    	sumMoney04+=parseFloat(obj.money.replace(/,/g,""));
  	     }
	});

	$("#sum_money").text(""); 
	if(sumMoney01!=0){
		$("#sum_money").append("01："+formatNumber(sumMoney01,2,1)+"，");
	}
	
	if(sumMoney02!=0){
		$("#sum_money").append("02："+formatNumber(sumMoney02,2,1)+"，");
	}
	
	if(sumMoney03!=0){
		$("#sum_money").append("03："+formatNumber(sumMoney03,2,1)+"，");
	}
	
	if(sumMoney04!=0){
		$("#sum_money").append("04："+formatNumber(sumMoney04,2,1)+"，");
	}
	
	var sumMoneyText=$("#sum_money").text();
	if(sumMoneyText!=""){
		$("#sum_money").text(sumMoneyText.substring(0,sumMoneyText.length-1));
	}
	//$("#sum_money").text(toDecimal(sumMoney));
	//$("#sum_money").text(formatNumber(_.sum(grid.getGridData(),"money"),2,1));
}

//金额运算
var verlis = /^[\d\+\-\*\/\.{0,1}\,{0,1}\={0,1}]+$/;
function evelMoney(a){
	
	if(verlis.test(a)){
		var sum = a.replace(/,/g,"");//替换千分符号
		try{
			return eval(sum);	
		}catch(e){
			
		}
		
	}
	return null;
};

//选择下拉框，保存ID列
function saveCheckId(isCheckVal,$row,$cell,checkist,columnName){
	var isSelCheck=false;
	if(isCheckVal){
		$.each(checkist, function (i, str) {
			 
	 		//科目：如果输入编码匹配的话，自动选中
	 		if($cell.find("div").text()==str.name.split(" ")[0]){
	 			$cell.find("div").text(str.name);
	 		}
	 		
	        if ($cell.find("div").text()==str.name) {
	         	isSelCheck=true;
	         	if(str.no!=undefined){
	         		$($row[0].cells[$cell.index()+1]).find("div").text(str.id+"@"+str.no);//保存ID,no
	         	}else{
	         		$($row[0].cells[$cell.index()+1]).find("div").text(str.id);//保存ID
	         	}
	         	
	         	
	         	
	            return false;
	        }
	        
	        
		});
	}
	
	//输入的值在下拉框里面不存在，清空
	if(!isSelCheck){
	 	$cell.find("div").text("");
	 	$($row[0].cells[$cell.index()+1]).find("div").text("");//ID,no
	}
	
}


//表格加载完，初始化值
function initCheckGridValue(isReadonly){
	//第一行摘要获取焦点
    var $row = grid.getRowByIndex(0);
    //$row[0].cells[3].innerText=$("#summary").val();
    grid.setActiveCell($($row[0].cells[2]));
    grid.$el.focus();
    //grid.exitEditor(true);
    grid.editCell(); 
    
}

function loadCheckDict(){
	/*var value = liger.get("subj_dire").getValue();
	if(!grid){
		return;
	}*/
	
	//加载现金流量下拉字典
	if(grid.editorProps["diff_item_name"].length==0){
		var para={						
			column_id:"diff_item_code" 
			//cash_dire: value
		};
		ajaxJsonObjectByUrl("accCheckDictList.do?isCheck=false",para,function (responseData){
	    	grid.editorProps["diff_item_name"] = responseData;
	    });
	}
}

/**********************右键菜单处理********begin*****************************/
function initMenu(){
	var menuData=[
         [{
             text: "render",
             func: function () {}
         }],
         [{
             text: "插入",
             func: function (event) {
            	 grid.insertActiveRow();
            }
         }],
         [{
             text: "快捷键说明",
             func: function () {
            	parent.myHelp();
             }
         }]
	 ];
 	$("#vouchCheckDiv").smartMenu(menuData,{
        name:"rightMenu",
        offsetX:2,
        offsetY:2,
        textLimit:6,
        topsetX:0,
        beforeShow:function(e) {
        	return readRowNum(e);
        },
        afterShow:function() {

        }
    });
	
}

//加载行号
function readRowNum(e){
	var textNew = "";
	var event = event || window.event;
	//  根据鼠标所点击的元素的父元素是否为thead来判断是否为表头
	if($(event.srcElement).parents('thead')[0]){   
		textNew = "行号：0";
	} else if($(event.srcElement).parents('tr','.sensei-grid-tbody')[0]){
		//   取出父元素tr的索引值
		var RowNum = $(event.srcElement).parents('tr','.sensei-grid-tbody').index() + 1;
		textNew = "行号：" + RowNum;
	}else if(grid.activeEditor){  //  若点击是编辑框，则取出.activeCell类td的父元素tr索引值
		var RowNum = $('.activeCell').parents('tr','.sensei-grid-tbody').index() + 1;
		textNew = "行号：" + RowNum;
	}

	return textNew;
}
/**********************右键菜单处理********end*****************************/

//取现金流量属性
function getCashAttr(cash_item_id){
	var attr={};
	$.each(grid.editorProps["cash_name"], function (i, obj) {
		 if(cash_item_id==obj.id){
			 attr=obj;
			 return false;
		 }
	 });
	return attr
}

