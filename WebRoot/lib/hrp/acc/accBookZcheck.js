/**
 * 1. 各页面表格对象需为grid
 * 2. 浮动层DIV的id需为floatDiv
 * 3. 需存在全局变量is_zcheck, check_type_id, check_type_name, template_code
 * 4. 顶部表单DIV的ID需为"queryTab"
 * 5. 内容表格DIV的ID需为"maingrid"
 */
var year_month1, year_month2, check_type1, check_type2, check_type3, check_type4, order_by, show_by, not_group;
var is_float = 0, type_count = 1, old_order_by = 0;
var yebObj = {}, check_parms = {};

/**
 * 加载浮动层
 * 条件浮动
 */
function loadLayer(){
	
	ktLayerObj = $("#floatDiv").ktLayer({
		// 参数配置
		direction:"down",
		BtnbgImg:{open:'/CHD-HRP/lib/hrp/acc/superReport/open.png',close:'/CHD-HRP/lib/hrp/acc/superReport/close.png'},
		speed:"100",
		// bgColor:"#ccc",//背景颜色
		closeHeight:0,//关闭状态高度
		Descript:["查询条件","收起条件"],//展开收起描述
		zIndex: 2,
		open:function(){
			if(is_float == 0){
				floatDict();
				is_float = 1;
			}
		}
	});
	
	if(is_zcheck == 0){
		//控制显隐
		$(".check_type1").hide();
		$("#check_name1").text(check_type_name + "：");
		$("#add1").hide();

		grid.toggleCol("check1_name", true);
		grid.changeHeaderText("check1_name", check_type_name);
	}

	//点击tab或表格隐藏弹出层
	$("#maingrid").click(function(event){
		ktLayerObj.fn.close();
	});
	$("#queryTab").click(function(event){
		ktLayerObj.fn.close();
	});
}

//重载方法为查询条件不浮动，如果想浮动则不重载该方法（修改成别的方法名即可）
function loadLayer(){
	$("#floatDiv").css("height", "");
	$("<div align='right' style='height: 0; position: relative; top: -20px; z-index: 2; margin-right: 20px;'><a id='ashow' href='javascript:showFloat()'>收起条件</a></div>").prependTo("#floatDiv");
	$("#floatDiv").show();
	$("#floatQuery").removeClass("info");
	if(is_zcheck == 0){
		//控制显隐
		$(".check_type1").html("");
		$("#check_name1").text(check_type_name + "：");
		$("#add1").hide();

		grid.toggleCol("check1_name", true);
		grid.changeHeaderText("check1_name", check_type_name);
	}
	floatDict();
	grid._onResize();
}

//控制显隐
var is_show = true;
function showFloat(){
	if(is_show){
		$("#floatQuery").hide();
		$("#ashow").text("展开条件");
		is_show = false;
	}else{
		$("#floatQuery").show();
		$("#ashow").text("收起条件");
		is_show = true;
	}
	grid._onResize();
}

//浮动层条件
function floatDict(){
	// 科目属性
	$("#subj_kind").ligerComboBox({
		data: [
		    {id: "01", text: "账务会计科目"}, 
		    {id: "02", text: "预算会计科目"}
		], 
		value: '01', 
		width: 205, 
		cancelable: false
	});
	
	var para = {
		sch_id: liger.get("sch_id").getValue(), 
		check_type_id: is_zcheck == 1 ? '' : check_type_id
	}; 
	
	autocompleteObj({
		id:  '#begin_subj_code', 
		urlStr: 	"../../querySubjCode.do?isCheck=false&is_check="+is_check, 
		valueField:  'id', 
		textField:    'text' , 
		autocomplete: true, 
		highLight: true, 
		parmsStr: para, 
		defaultSelect:  false, 
		boxwidth: subjWidth, 
		selectEvent: function(value){
			
			liger.get("end_subj_code").clear();
			liger.get("end_subj_code").setValue(value);
			liger.get("end_subj_code").setText(liger.get("begin_subj_code").getText());
			if(is_zcheck == 1){
				check_parms = liger.get("check_item_type1") ? liger.get("check_item_type1").options.parms || {} : {};
				check_parms.begin_subj_code = liger.get("begin_subj_code").getValue(); 
				check_parms.end_subj_code = liger.get("end_subj_code").getValue();
				
				liger.get("check_item_type1").clear();
				liger.get("check_item_type1").set("parms", check_parms);
				liger.get("check_item_type1").reload();
				if(liger.get("check_item_type2")){
					liger.get("check_item_type2").clear();
					liger.get("check_item_type2").set("parms", check_parms);
					liger.get("check_item_type2").reload();
				}
				if(liger.get("check_item_type3")){
					liger.get("check_item_type3").clear();
					liger.get("check_item_type3").set("parms", check_parms);
					liger.get("check_item_type3").reload();
				}
				if(liger.get("check_item_type4")){
					liger.get("check_item_type4").clear();
					liger.get("check_item_type4").set("parms", check_parms);
					liger.get("check_item_type4").reload();
				}
			}
		}, 
		textBoxKeySpace: function(value){
			var data = {
					ligerId: "begin_subj_code", 
					acc_year: acc_year_month1.getValue().split(".")[0]
			}
			parent.$.ligerDialog.open({
				title :  '科目选择',
				width :  600,
				height :  $(window).height()-50,
				url :  'hrp/acc/books/subjTreePage.do?isCheck=false', 
				data: data, modal :  true, showToggle :  false, 
				showMax :  false, showMin :  false, isResize :  true,
				parentframename :  window.name,
			});
		}
	});

	autocompleteObj({
		id:  '#end_subj_code', 
		urlStr: 	"../../querySubjCode.do?isCheck=false&is_check="+is_check, 
		valueField:  'id', 
		textField:    'text', 
		autocomplete: true, 
		highLight: true, 
		parmsStr: para, 
		defaultSelect:  false, 
		boxwidth: subjWidth, 
		selectEvent: function(value){
			if(is_zcheck == 1){
				/*if(liger.get("check_item_type1")){
					check_parms = liger.get("check_item_type1").parms || {};
				}else{
					check_parms = {};
				}*/
				check_parms = liger.get("check_item_type1") ? liger.get("check_item_type1").options.parms || {} : {}; 
				check_parms.begin_subj_code = liger.get("begin_subj_code").getValue(); 
				check_parms.end_subj_code = liger.get("end_subj_code").getValue();
				
				liger.get("check_item_type1").clear();
				liger.get("check_item_type1").set("parms", check_parms);
				liger.get("check_item_type1").reload();
				if(liger.get("check_item_type2")){
					liger.get("check_item_type2").clear();
					liger.get("check_item_type2").set("parms", check_parms);
					liger.get("check_item_type2").reload();
				}
				if(liger.get("check_item_type3")){
					liger.get("check_item_type3").clear();
					liger.get("check_item_type3").set("parms", check_parms);
					liger.get("check_item_type3").reload();
				}
				if(liger.get("check_item_type4")){
					liger.get("check_item_type4").clear();
					liger.get("check_item_type4").set("parms", check_parms);
					liger.get("check_item_type4").reload();
				}
			}
		}, 
	    textBoxKeySpace: function(value){
	    	var data = {
				ligerId: "end_subj_code", 
				acc_year: acc_year_month1.getValue().split(".")[0]
			}
			parent.$.ligerDialog.open({
				title :  '科目选择',
				width :  600,
				height :  $(window).height()-50,
				url :  'hrp/acc/books/subjTreePage.do?isCheck=false', 
				data: data, modal :  true, showToggle :  false, 
				showMax :  false, showMin :  false, isResize :  true,
				parentframename :  window.name,
			});
	    }
	});
	
	autocompleteObj({
		id: '#check_item_type1', 
		urlStr:	"../../queryCheckTypeBySubjCode.do?isCheck=false",							
		valueField: 'id',            
		textField:   'text' ,            
		autocomplete:true,			
		highLight:true,
		defaultSelect:false,
		initWidth: 205, 
		selectEvent: function(value){

			var param = {
				check_type_id:  value
       		};
				
			liger.get("check_item_code1_b").clear();
			liger.get("check_item_code1_b").set("parms", param);
			liger.get("check_item_code1_b").reload();
			liger.get("check_item_code1_e").clear();
			liger.get("check_item_code1_e").set("parms", param);
			liger.get("check_item_code1_e").reload();
	    }
	}); 
	
	var param1 = {}
	if(check_type_id){
		param1 = {
			check_type_id:  check_type_id
   		};
	}
	
	autocompleteObj({
		id: '#check_item_code1_b', 
		urlStr:	"../../queryCheckItemByTypeFy.do?isCheck=false", 
		valueField: 'id', 
		textField: 'text', 
		parmsStr: param1, 
		autocomplete: true, 
		highLight: true, 
		defaultSelect: false, 
		boxwidth: subjWidth, 
		selectEvent: function(value){

			liger.get("check_item_code1_e").clear();
			liger.get("check_item_code1_e").setValue(value);
			liger.get("check_item_code1_e").setText(liger.get("check_item_code1_b").getText());
	    }
	}); 
	
	autocompleteObj({
		id: '#check_item_code1_e', 
		urlStr:	"../../queryCheckItemByTypeFy.do?isCheck=false", 
		valueField: 'id', 
		textField: 'text', 
		parmsStr: param1, 
		autocomplete: true, 
		highLight: true, 
		defaultSelect: false, 
		boxwidth: subjWidth, 
	}); 
	
	// 项目分类
	if(checkType == "check3"){
		$(".proj_type").show();
		autocompleteObj({
			id: '#proj_type', 
			urlStr:	"../../../sys/queryProjTypeDict.do?isCheck=false", 
			valueField: 'id', 
			textField: 'text', 
			parmsStr: {},
			autocomplete: true, 
			highLight: true, 
			defaultSelect: false, 
			boxwidth: subjWidth, 
		}); 
	}
	
	$("#order_by input:radio").ligerRadio();
};   

//查询
function query(){
	grid.options.parms=[];
	grid.options.newPage=1;
	//根据表字段进行添加查询条件
	
	year_month1 = acc_year_month1.getValue();
	year_month2 = acc_year_month2.getValue();
	if(year_month1=="" || year_month2 == ""){
	$.ligerDialog.error('起始年月为必填项');
		return ;
	};

	var sch_id = liger.get("sch_id").getValue();
	var is_flag = is_zcheck == 1;
	check_type1 = is_flag ? (liger.get("check_item_type1") == null ? "" : liger.get("check_item_type1").getValue()) : check_type_id;
	check_type2 = liger.get("check_item_type2") == null ? "" : liger.get("check_item_type2").getValue();
	check_type3 = liger.get("check_item_type3") == null ? "" : liger.get("check_item_type3").getValue();
	check_type4 = liger.get("check_item_type4") == null ? "" : liger.get("check_item_type4").getValue();
	
	if (check_type_id == '') {
		if(liger.get("begin_subj_code").getValue() == undefined || liger.get("begin_subj_code").getValue()=="" ){
				$.ligerDialog.error('科目为必填项');
				return ;
		};
	}
	
	if(is_flag && !sch_id && check_type1 =="" ){
	$.ligerDialog.error('核算类1为必填项');
		return ;
	}
	if(is_flag && type_count >= 2 && check_type2 =="" ){
	$.ligerDialog.error('核算类2为必填项');
		return ;
	}
	if(is_flag && type_count >= 3 && check_type3 =="" ){
	$.ligerDialog.error('核算类3为必填项');
		return ;
	}
	if(is_flag && type_count >= 4 && check_type4 =="" ){
	$.ligerDialog.error('核算类4为必填项');
		return ;
	}
	
	//交叉表需要改变表头
	if(page_name == "交叉表"){
		changeHead();
	}

	var check_item1_b = liger.get("check_item_code1_b") == null ? "" :  liger.get("check_item_code1_b").getValue();
	var check_item1_e = liger.get("check_item_code1_e") == null ? "" :  liger.get("check_item_code1_e").getValue();
   
	var check_item2_b = liger.get("check_item_code2_b") == null ? "" :  liger.get("check_item_code2_b").getValue();
	var check_item2_e = liger.get("check_item_code2_e") == null ? "" :  liger.get("check_item_code2_e").getValue();

	var check_item3_b = liger.get("check_item_code3_b") == null ? "" :  liger.get("check_item_code3_b").getValue();
	var check_item3_e = liger.get("check_item_code3_e") == null ? "" :  liger.get("check_item_code3_e").getValue();

	var check_item4_b = liger.get("check_item_code4_b") == null ? "" :  liger.get("check_item_code4_b").getValue();
	var check_item4_e = liger.get("check_item_code4_e") == null ? "" :  liger.get("check_item_code4_e").getValue();

	var begin_subj_code = liger.get("begin_subj_code") == null ? "" :  liger.get("begin_subj_code").getValue();
	var end_subj_code = liger.get("end_subj_code") == null ? "" :  liger.get("end_subj_code").getValue();
	
	var subjKind = liger.get("subj_kind") == null ? "01" : liger.get("subj_kind").getValue();
	grid.options.parms.push({name: 'subj_kind',value: subjKind});// 科目属性
	
	grid.options.parms.push({name:'acc_year_b',value : year_month1.split(".")[0]}); 
	grid.options.parms.push({name:'acc_month_b',value : year_month1.split(".")[1]}); 
	grid.options.parms.push({name:'acc_year_e',value : year_month2.split(".")[0]}); 
	grid.options.parms.push({name:'acc_month_e',value : year_month2.split(".")[1]}); 
	grid.options.parms.push({name: 'sch_id', value:  sch_id}); 
	grid.options.parms.push({name: 'begin_subj_code', value:  begin_subj_code}); 
	grid.options.parms.push({name: 'end_subj_code', value:  end_subj_code}); 
	grid.options.parms.push({name: 'check_item_type', value: check_type1}); //兼容老页面
	grid.options.parms.push({name: 'check_item_type1', value: check_type1}); 
	grid.options.parms.push({name: 'check_item_type2', value: check_type2}); 
	grid.options.parms.push({name: 'check_item_type3', value: check_type3}); 
	grid.options.parms.push({name: 'check_item_type4', value: check_type4});
	
	grid.options.parms.push({name: 'check_item_code1_b', value: check_item1_b}); 
	grid.options.parms.push({name: 'check_item_code1_e', value: check_item1_e}); 
	grid.options.parms.push({name: 'check_item_code2_b', value: check_item2_b}); 
	grid.options.parms.push({name: 'check_item_code2_e', value: check_item2_e});
	grid.options.parms.push({name: 'check_item_code3_b', value: check_item3_b}); 
	grid.options.parms.push({name: 'check_item_code3_e', value: check_item3_e}); 
	grid.options.parms.push({name: 'check_item_code4_b', value: check_item4_b}); 
	grid.options.parms.push({name: 'check_item_code4_e', value: check_item4_e});
	
	
	
	//关联方//机构//险种//结算方式
	grid.options.parms.push({ name: 'aff_code', value: liger.get("guanlianfang_code").getValue() });
	grid.options.parms.push({ name: 'inst_code', value: liger.get("jigou_code").getValue() });
    grid.options.parms.push({ name: 'dang_code', value: liger.get("xianzhong_code").getValue() });
    grid.options.parms.push({ name: 'tmop_code', value: liger.get("jiesuan_code").getValue() }); 
	
	if(liger.get("proj_type")){
		grid.options.parms.push({name: 'proj_type', value: liger.get("proj_type").getValue()});
	}
	
	if($("input[name='order_by']").length > 0){  //总账、明细账使用
		
		order_by = $("input[name='order_by']:checked").val();
		grid.options.parms.push({name: 'order_by', value: order_by});
	}else if($("input[name='show_by']").length > 0){  //余额表使用
		
		show_by = $("input[name='show_by']:checked").val();
		grid.options.parms.push({name: 'order_by', value: show_by});
	}

	grid.options.parms.push({name: 'only_last', value: $("#only_last").prop("checked") ? 1 : 0});
	if($("#show_zero")){
		grid.options.parms.push({name: 'show_zero', value: $("#show_zero").prop("checked") ? 1 : 0});
	}
	
	
	//改变统计（维度/方式）
	changeGroup();

	//改变列
	showCol();

	//加载查询条件
	grid.loadData(grid.where);
};

//改变统计方式
function changeGroup(){
	//order_by存在则是总账、明细账页面需判断是否改变统计方式
	if(order_by && old_order_by > 0 && old_order_by != order_by){
		var fromCol, toCol;
		fromCol = grid.getColumnByName("subj_name").columnindex;
		if(order_by == 1){
			grid.options.groupColumnName = "subj_name";
			grid.options.groupColumnDisplay = "科目";

			toCol = grid.getColumnByName("check1_name").columnindex;
			grid.changeCol(fromCol, toCol, false);
		}else if(order_by == 2){
			grid.options.groupColumnName = "check1_name";
			grid.options.groupColumnDisplay = check_type_name;

			toCol = grid.getColumnByName("check4_name").columnindex;
			grid.changeCol(fromCol, toCol, true);
		}
		
		//改变列顺序会初始化列的显示或隐藏
		grid.toggleCol("check1_name", true);
		grid.changeHeaderText("check1_name", check_type_name);
	}
	
	old_order_by = order_by;
	
	if(not_group){
		grid.options.groupColumnName = "";
		grid.options.groupColumnDisplay = "";
	}
}

//改变列
function showCol(){
	//总账、明细账、交叉表页面
	if((page_name == "总账" || page_name == "明细账" || page_name == "交叉表") && is_zcheck == 1){
		//改变列头
		changeColumnName();
	}
	//余额表页面
	if(page_name == "余额表"){
		//初始化变量
		yebObj = {
			check_type1: check_type_id, 
			check_type_name1: check_type_name 
		};
		if(show_by == 1){
			grid.toggleCol("subj_name", true);
			grid.toggleCol("check1_name", false);
			grid.toggleCol("check2_name", false);
			grid.toggleCol("check3_name", false);
			grid.toggleCol("check4_name", false);
		}else if(show_by == 2){
			grid.toggleCol("subj_name", false);
			if(is_zcheck == 1){
				//改变列头
				changeColumnName();
			}else{
				grid.toggleCol("check1_name", true);
				grid.changeHeaderText("check1_name", check_type_name);
			}
		}else if(show_by == 3){
			grid.toggleCol("subj_name", true);
			if(is_zcheck == 1){
				//改变列头
				changeColumnName();
			}else{
				grid.toggleCol("check1_name", true);
				grid.changeHeaderText("check1_name", check_type_name);
			}
		}
	}
}

//改变表格列头显示
function changeColumnName(){
	if(check_type1){
		grid.toggleCol("check1_name", true);
		grid.changeHeaderText("check1_name", liger.get("check_item_type1").getText());
		yebObj.check_type1 = liger.get("check_item_type1").getValue();
		yebObj.check_type_name1 = liger.get("check_item_type1").getText();
		if(check_type2){
			grid.toggleCol("check2_name", true);
			grid.changeHeaderText("check2_name", liger.get("check_item_type2").getText());
			yebObj.check_type2 = liger.get("check_item_type2").getValue();
			yebObj.check_type_name2 = liger.get("check_item_type2").getText();
		}else{
			grid.toggleCol("check2_name", false);
		}
		if(check_type3){
			grid.toggleCol("check3_name", true);
			grid.changeHeaderText("check3_name", liger.get("check_item_type3").getText());
			yebObj.check_type3 = liger.get("check_item_type3").getValue();
			yebObj.check_type_name3 = liger.get("check_item_type3").getText();
		}else{
			grid.toggleCol("check3_name", false);
		}
		if(check_type4){
			grid.toggleCol("check4_name", true);
			grid.changeHeaderText("check4_name", liger.get("check_item_type4").getText());
			yebObj.check_type4 = liger.get("check_item_type4").getValue();
			yebObj.check_type_name4 = liger.get("check_item_type4").getText();
		}else{
			grid.toggleCol("check4_name", false);
		}
	}else{
		if(liger.get("sch_id").getValue()){
			ajaxJsonObjectByUrl("../../accbooksch/queryAccBookSchCheckNameBySchId.do?isCheck=false", {sch_id: liger.get("sch_id").getValue()}, function (res) {
				if(res.Rows.length > 0){
		    		grid.toggleCol("check1_name", true);
		    		grid.changeHeaderText("check1_name", res.Rows[0].CHECK_TYPE_NAME);
					yebObj.check_type1 = res.Rows[0].CHECK_TYPE_ID;
					yebObj.check_type_name1 = res.Rows[0].CHECK_TYPE_NAME;
			    	if(res.Rows.length > 1){
			    		grid.toggleCol("check2_name", true);
			    		grid.changeHeaderText("check2_name", res.Rows[1].CHECK_TYPE_NAME);
						yebObj.check_type2 = res.Rows[1].CHECK_TYPE_ID;
						yebObj.check_type_name2 = res.Rows[1].CHECK_TYPE_NAME;
			    	}else{
			    		grid.toggleCol("check2_name", false);
			    	}
			    	if(res.Rows.length > 2){
			    		grid.toggleCol("check3_name", true);
			    		grid.changeHeaderText("check3_name", res.Rows[2].CHECK_TYPE_NAME);
						yebObj.check_type3 = res.Rows[2].CHECK_TYPE_ID;
						yebObj.check_type_name3 = res.Rows[2].CHECK_TYPE_NAME;
			    	}else{
			    		grid.toggleCol("check3_name", false);
			    	}
			    	if(res.Rows.length > 3){
			    		grid.toggleCol("check4_name", true);
			    		grid.changeHeaderText("check4_name", res.Rows[3].CHECK_TYPE_NAME);
						yebObj.check_type4 = res.Rows[3].CHECK_TYPE_ID;
						yebObj.check_type_name4 = res.Rows[3].CHECK_TYPE_NAME;
			    	}else{
			    		grid.toggleCol("check4_name", false);
			    	}
		    	}else{
		    		grid.toggleCol("check1_name", false);
		    	}
			}, false); 
		}else{
			grid.toggleCol("check1_name", false);
		}
	}
};

//方案设置
function subjIntercalate(){
	parent.$.ligerDialog.open({
		title :  '方案设置',
		width :  $(window).width()-20,
		height :  $(window).height()-50,
		url :  'hrp/acc/accbooksch/accBookSchMainPage.do?isCheck=false&is_check='+is_check+'&page='+template_code,
		modal :  true,
		showToggle :  false,
		showMax :  false,
		showMin :  false,
		isResize :  true,
		parentframename :  window.name,
		buttons :  [ {
			text :  '保存',
			onclick :  function(item, dialog) {
				dialog.frame.saveSch(0);
			},
			cls :  'l-dialog-btn-highlight'
		},{
			text :  '查询',
			onclick :  function(item, dialog) {
				dialog.frame.saveSch(1);
			},
			cls :  'l-dialog-btn-highlight'
		},{
			text :  '取消',
			onclick :  function(item, dialog) {
				dialog.close();
			}
		} ]
	});
};

//增加第二行调用
function addSecond(){
	autocompleteObj({
		id:  '#check_item_type2',                   
		urlStr: 	"../../queryCheckTypeBySubjCode.do?isCheck=false",							
		valueField:  'id',            
		textField:    'text' ,            
		autocomplete: true,			
		highLight: true,
		parmsStr: check_parms,
		defaultSelect:  false,
		initWidth: 205, 
		selectEvent: function(value){
			 
			var param = {
				check_type_id:  value
	 		}; 
			liger.get("check_item_code2_b").clear();
			liger.get("check_item_code2_b").set("parms", param);
			liger.get("check_item_code2_b").reload();
			liger.get("check_item_code2_e").clear();
			liger.get("check_item_code2_e").set("parms", param);
			liger.get("check_item_code2_e").reload();
	    }
	});	
	
	autocompleteObj({
		id: '#check_item_code2_b', 
		urlStr:	"../../queryCheckItemByTypeFy.do?isCheck=false", 
		valueField: 'id', 
		textField: 'text', 
		autocomplete: true, 
		highLight: true, 
		defaultSelect: false, 
		boxwidth: subjWidth, 
		selectEvent: function(value){

			liger.get("check_item_code2_e").clear();
			liger.get("check_item_code2_e").setValue(value);
			liger.get("check_item_code2_e").setText(liger.get("check_item_code2_b").getText());
	    }
	}); 
	
	autocompleteObj({
		id: '#check_item_code2_e', 
		urlStr:	"../../queryCheckItemByTypeFy.do?isCheck=false", 
		valueField: 'id', 
		textField: 'text', 
		autocomplete: true, 
		highLight: true, 
		defaultSelect: false, 
		boxwidth: subjWidth, 
	}); 
};

//增加第三行调用
function addThird(){
	autocompleteObj({
		id:  '#check_item_type3',                   
		urlStr: 	"../../queryCheckTypeBySubjCode.do?isCheck=false",							
		valueField:  'id',            
		textField:    'text' ,            
		autocomplete: true,			
		highLight: true,
		parmsStr: check_parms,
		defaultSelect:  false,
		initWidth: 205, 
		selectEvent: function(value){
			 
			var param = {
				check_type_id:  value
	 		}; 
			liger.get("check_item_code3_b").clear();
			liger.get("check_item_code3_b").set("parms", param);
			liger.get("check_item_code3_b").reload();
			liger.get("check_item_code3_e").clear();
			liger.get("check_item_code3_e").set("parms", param);
			liger.get("check_item_code3_e").reload();
	    }
	});
	
	autocompleteObj({
		id: '#check_item_code3_b', 
		urlStr:	"../../queryCheckItemByTypeFy.do?isCheck=false", 
		valueField: 'id', 
		textField: 'text', 
		autocomplete: true, 
		highLight: true, 
		defaultSelect: false, 
		boxwidth: subjWidth, 
		selectEvent: function(value){

			liger.get("check_item_code3_e").clear();
			liger.get("check_item_code3_e").setValue(value);
			liger.get("check_item_code3_e").setText(liger.get("check_item_code3_b").getText());
	    }
	}); 
	
	autocompleteObj({
		id: '#check_item_code3_e', 
		urlStr:	"../../queryCheckItemByTypeFy.do?isCheck=false", 
		valueField: 'id', 
		textField: 'text', 
		autocomplete: true, 
		highLight: true, 
		defaultSelect: false, 
		boxwidth: subjWidth, 
	}); 
};

//增加第四行调用
function addFourth(){
	autocompleteObj({
		id:  '#check_item_type4',                   
		urlStr: 	"../../queryCheckTypeBySubjCode.do?isCheck=false",							
		valueField:  'id',            
		textField:    'text' ,            
		autocomplete: true,			
		highLight: true,
		parmsStr: check_parms,
		defaultSelect: false,
		initWidth: 205, 
		selectEvent: function(value){
			 
			var param = {
				check_type_id:  value
	 		}; 
			liger.get("check_item_code4_b").clear();
			liger.get("check_item_code4_b").set("parms", param);
			liger.get("check_item_code4_b").reload();
			liger.get("check_item_code4_e").clear();
			liger.get("check_item_code4_e").set("parms", param);
			liger.get("check_item_code4_e").reload();
	    }
	});
	
	autocompleteObj({
		id: '#check_item_code4_b', 
		urlStr:	"../../queryCheckItemByTypeFy.do?isCheck=false", 
		valueField: 'id', 
		textField: 'text', 
		autocomplete: true, 
		highLight: true, 
		defaultSelect: false, 
		boxwidth: subjWidth, 
		selectEvent: function(value){

			liger.get("check_item_code4_e").clear();
			liger.get("check_item_code4_e").setValue(value);
			liger.get("check_item_code4_e").setText(liger.get("check_item_code4_b").getText());
	    }
	}); 
	
	autocompleteObj({
		id: '#check_item_code4_e', 
		urlStr:	"../../queryCheckItemByTypeFy.do?isCheck=false", 
		valueField: 'id', 
		textField: 'text', 
		autocomplete: true, 
		highLight: true, 
		defaultSelect: false, 
		boxwidth: subjWidth, 
	}); 
};

//增加1
function addLine1(){
	$('#attend2').show();
	$('#add1').hide();
	addSecond();
	type_count = 2;
	changeGridHeight();
};

//增加2
function addLine2(){
	$('#attend3').show();
	$('#add2').hide();
	addThird();
	type_count = 3;
	changeGridHeight();
};

//增加3
function addLine3(){
	$('#attend4').show();
	$('#add3').hide();
	addFourth();
	type_count = 4;
	changeGridHeight();
};

//移出2
function deleteLine1(){
	liger.get("check_item_type2").clear();
	liger.get("check_item_code2_b").clear();
	liger.get("check_item_code2_e").clear();
	
	$('#attend2').hide();
	$('#add1').show();
	type_count = 1;
	changeGridHeight();
};

//移出3
function deleteLine2(){
	liger.get("check_item_type3").clear();
	liger.get("check_item_code3_b").clear();
	liger.get("check_item_code3_e").clear();
	
	$('#attend3').hide();
	$('#add2').show();
	type_count = 2;
	changeGridHeight();
};

//移出4
function deleteLine3(){
	liger.get("check_item_type4").clear();
	liger.get("check_item_code4_b").clear();
	liger.get("check_item_code4_e").clear();
	
	$('#attend4').hide();
	$('#add3').show();
	type_count = 3;
	changeGridHeight();
};

function changeGridHeight(){
	
	if(is_float == 0){
		grid._onResize();
	}
}

//打开凭证
function openSuperVouch(vouch_id) {
	parent.openFullDialog('hrp/acc/accvouch/superVouch/superVouchMainPage.do?vouch_id='+ vouch_id, '会计凭证', 0, 0, true, true);
}

//打印维护
function printSet(){
	 officeFormTemplate({template_code: template_code});
}

//打印
function printData(){ 
	if(grid.getData().length == 0){
		$.ligerDialog.error("请先查询数据！");
		return;
	}
	
	var selPara={};
   	$.each(grid.options.parms,function(i,obj){
   		selPara[obj.name]=obj.value;
   	});
   	selPara["template_code"] = template_code;
   	selPara["class_name"] = "com.chd.hrp.acc.service.books.check.AccBooksCheckService";
   	selPara["method_name"] = method_name;
   	selPara["bean_name"] = "accBooksCheckService";
   	selPara["p_acc_year"] = year_month1[0].split(".")[0];
   	selPara["year_month_b"] = year_month1;
   	selPara["year_month_e"] = year_month2;
   	selPara["group_name"] = parent.sessionJson.group_name;
   	selPara["hos_name"] = parent.sessionJson.hospital;
   	selPara["copy_name"] = parent.sessionJson.copy_name;
   	selPara["user_name"] = parent.sessionJson.user_name;
   	
   	officeTablePrint(selPara);
}

//列表打印
function printGrid(){
	
	if(grid.getData().length==0){
		$.ligerDialog.error("请先查询数据！");
		return;
	}
     
	var printPara={
			title: check_type_name + page_name,//标题
			columns: JSON.stringify(grid.getPrintColumns()),//表头
			class_name: "com.chd.hrp.acc.service.books.check.AccBooksCheckService",
			method_name: method_name_grid,
			bean_name: "accBooksCheckService"
	};
	
	$.each(grid.options.parms,function(i,obj){
			printPara[obj.name]=obj.value;
	});
	
	officeGridPrint(printPara);
}

