<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<script src="<%=path%>/lib/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>
<script src="<%=path%>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/core/base.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path%>/lib/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ckeditor/ckeditor.js"></script>
<script language="javascript" src="<%=path%>/lib/Lodop/LodopFuncs.js"></script>
<style type="text/css">
       
        body {
			-moz-user-select: none;
			-webkit-user-select: none;
			-ms-user-select: none;
			-khtml-user-select: none;
			user-select: none;
		}
		.inputMar{
			margin-top:5px;width:95px;
		}
</style>

<script type="text/javascript">
var grid;
var gridManager = null;
var editor=null;
var editorElement=null;
$(function() {
	$("#layout1").ligerLayout({ leftWidth: 350});
	initCkeditor();
	loadHead();	//加载数据
	
});


function initCkeditor(){
	editorElement = CKEDITOR.document.getById( 'editor' );
	editor=CKEDITOR.replace( 'editor' );
//	$("#editor").css("height", $(window).height());

	//加载打印模板内容
	var para={
			mod_code:'${sysPrintTemplate.mod_code}',
			template_code:'${sysPrintTemplate.template_code}',
			user_id:'${sysPrintTemplate.user_id}'
	}
	ajaxTextByUrl("querySuperPrintContentByCode.do?isCheck=false",para,function(responseData){	
		//console.log(responseData)
		editorElement.setHtml(responseData);
	},false);
	
	
}



function loadHead(){
	var para={
			mod_code:'${sysPrintTemplate.mod_code}',
			template_code:'${sysPrintTemplate.template_code}',
			user_id:'${sysPrintTemplate.user_id}'
	}
	grid = $("#maingrid").ligerGrid({
       columns: [ 
                /* { display: '参数编码', name: 'para_code', align: 'left'
				 },*/
                 { display: '参数名称', name: 'para_name', align: 'left',width:130,
					 render : function(rowdata, rowindex,value) {
							var str="";
							if(rowdata.flag<7){
								str="<a href=javascript:void(0); ondragstart='return false' onclick=myDrag("+rowdata.flag+",'"+rowdata.para_code+"','"+rowdata.para_name+"'); title='点击填充到指定位置' style='cursor:pointer;'>"+rowdata.para_name+"</a>";
								
							}else{
								str=rowdata.para_name;
							}
							
							return str;
						}
					 
				 }, 
				 { display: '参数类型', name: 'flag_value', align: 'left',width:70
				 },
                 { display: '参数值', name: 'para_value', align: 'left',width:100,
						render : function(rowdata, rowindex,value) {
							var str = "";
								
							if(rowdata.flag == 7){
								
								str = "<select id = 'p_"+rowdata.para_code+"' class='inputMar'>";
									if(rowdata.para_value == "是"){
										str = str + "<option value='否'>否</option>";
										str = str + "<option value='是' selected='selected'>是</option>";
									}else{
										str = str + "<option value='否'selected='selected'>否</option>";
										str = str + "<option value='是' >是</option>";
									}
								str = str + "</option></select>";
								
							}else if(rowdata.flag == 8){
								
								str = "<input id='p_"+rowdata.para_code+"' value='"+(rowdata.para_value==null?"":rowdata.para_value)+"' class='inputMar'>";
								
							}else if(rowdata.flag == 9 && rowdata.para_code == '008'){
								
								str = "<select id = 'p_"+rowdata.para_code+"' class='inputMar'>";
									if(rowdata.para_value == "0"){
										str = str + "<option value='0' selected='selected'>缺省设置</option>";
										str = str + "<option value='1'>纵向打印</option>";
										str = str + "<option value='2'>横向打印</option>";
										str = str + "<option value='3'>纵向打印(宽固)</option>";
									}else if(rowdata.para_value == "1"){
										str = str + "<option value='0'>缺省设置</option>";
										str = str + "<option value='1' selected='selected'>纵向打印</option>";
										str = str + "<option value='2'>横向打印</option>";
										str = str + "<option value='3'>纵向打印(宽固)</option>";
									}else if(rowdata.para_value == "2"){
										str = str + "<option value='0'>缺省设置</option>";
										str = str + "<option value='1'>纵向打印</option>";
										str = str + "<option value='2' selected='selected'>横向打印</option>";
										str = str + "<option value='3'>纵向打印(宽固)</option>";
									}else if(rowdata.para_value == "3"){
										str = str + "<option value='0'>缺省设置</option>";
										str = str + "<option value='1'>纵向打印</option>";
										str = str + "<option value='2'>横向打印</option>";
										str = str + "<option value='3' selected='selected'>纵向打印(宽固)</option>";
									}
									
								str = str + "</option></select>";
								
							}else{
								//str="<button style='margin-top:5px;' onclick=myDrag('"+rowdata.para_code+"','"+rowdata.para_name+"')>填充</button>";
							}
							return str;
						}
				 }
                 ],
                 dataAction: 'server',dataType: 'server',usePager:false,url:'querySuperPrintParaByListJosn.do?isCheck=false',
                 width: '99%', height:'100%', checkbox: false,rownumbers:true,urlParms:para,
                 selectRowButtonOnly:true
               });

    gridManager = $("#maingrid").ligerGetGridManager();
}

function myPrint(){
	if(editor.document==undefined)return;
	//console.log(editor.document.getBody().getHtml()); //取得html文本);
	LODOP=getLodop();
	LODOP.PRINT_INIT("财务系统_凭证打印模板设置");
	LODOP.SET_PREVIEW_WINDOW(1,0,1,-1,-1,"凭证打印模板设置");
	var widthStr="0";
	var heightStr="0";
	if($("#p_006").val()!="" && $("#p_006").val().split("*").length==2){
		widthStr=parseFloat($("#p_006").val().split("*")[0])*10;
		heightStr=parseFloat($("#p_006").val().split("*")[1])*10;
	}
	//console.log(widthStr+"--"+heightStr)
	//console.log($("#p_008").val())
	LODOP.SET_PRINT_PAGESIZE($("#p_008").val(),widthStr,heightStr,$("#p_007").val());
	
//	LODOP.ADD_PRINT_HTM(10,300,151,50,"<b><u><font size=6>记账凭证 </font></u></b>");//凭证名称
//	LODOP.ADD_PRINT_HTM(60,10,200,50,"<b>核算单位</b>");//核算单位
	
	var p009=0;//上边距
	var p010=0;//左边距
	if($("#p_009").val()!="")p009=$("#p_009").val();
	if($("#p_010").val()!="")p010=$("#p_010").val();	
	
	
	//套打
	if($("#p_003").val()=="是"){	
		LODOP.ADD_PRINT_HTM(p009,p010,"100%","100%",editor.document.getBody().getHtml().replace(new RegExp('border="1"',"gm"), 'border="0"'));//editor.document.getBody().getHtml()	
	}else{
		LODOP.ADD_PRINT_HTM(p009,p010,"100%","100%",editor.document.getBody().getHtml());//editor.document.getBody().getHtml()
	}
	
	//LODOP.SET_PRINT_STYLEA(0,"AngleOfPageInside",180);
	//LODOP.SET_SHOW_MODE("PREVIEW_NO_MINIMIZE",true);//预览窗口禁止最小化，并始终最前
	LODOP.SET_SHOW_MODE('NP_NO_RESULT',true);
	LODOP.PREVIEW();
}

function mySave(){
	if(editor.document==undefined)return;
	
	var para_value="";
	var isCheck=true;
	var beginNum=0;
	var endNum=0;
	var re = /^[0-9]*[1-9][0-9]*$/ ; 
	$.each($(".inputMar"),function(i){
		var thisVal=$(this).val();
		
		if($(this).attr("id").replace("p_","")=="004"){
			beginNum=thisVal;
			if(!re.test(beginNum)){
				$.ligerDialog.error("起始行只能为正整数！");
				isCheck=false;
				return;
			}
		}
		if($(this).attr("id").replace("p_","")=="005"){
			endNum=thisVal;
			if(!re.test(endNum)){
				$.ligerDialog.error("结束行只能为正整数！");
				isCheck=false;
				return;
			}
		}
		if($(this).attr("id").replace("p_","")=="006"){
			if(thisVal!="" && thisVal.split("*").length!=2){
				$.ligerDialog.error("纸张大小的格式不正确，宽*高！");
				isCheck=false;
				return;
			}
			if(thisVal.split("*")[0]=="" || thisVal.split("*")[1]==""){
				$.ligerDialog.error("纸张大小的格式不正确，宽*高！");
				isCheck=false;
				return;
			}
		}
		if(thisVal=="")thisVal=" ";
		para_value=para_value+$(this).attr("id").replace("p_","")+"@"+thisVal+"#";
	});
	if(!isCheck){
		return;
	}
	if(parseInt(beginNum)>parseInt(endNum)){
		$.ligerDialog.error("起始行不能大于结束行！");
		return;
	}
	
	var para={
			content:editor.document.getBody().getHtml(),
			para_value:para_value,
			mod_code:'${sysPrintTemplate.mod_code}',
			template_code:'${sysPrintTemplate.template_code}',
			user_id:'${sysPrintTemplate.user_id}'
			};
	ajaxJsonObjectByUrl("saveSuperPrintTemplate.do?isCheck=false",para,function (responseData){
		
	},false);
	
}

function myDrag(flag,para_code,para_name){
	
	 if (editor.mode == 'wysiwyg' ){
	      
	       //从表
	       if(flag==2){
	    	   editor.insertHtml("<span dir='"+para_code+".detail' style='color:blue;'>"+para_name+"</span>");
	       }
	       //小计
	       else if(flag==3){
	    	   editor.insertHtml("<span dir='"+para_code+".total' style='color:blue;'>"+para_name+"</span>");
	       }
	       //合计
	       else if(flag==4){
	    	   editor.insertHtml("<span dir='"+para_code+".sum' style='color:blue;'>"+para_name+"</span>");
	       }
	       //合计大写
	       else if(flag==5){
	    	   editor.insertHtml("<span dir='"+para_code+".capital' style='color:blue;'>"+para_name+"</span>");
	       }
	       else{
	    	   editor.insertHtml("<span dir='"+para_code+".main' style='color:blue;'>"+para_name+"</span>");
	       }
	       
	  }else{
		  alert( 'You must be in WYSIWYG mode!' );
	        
	}
}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="layout1" style="width:100%;margin:0; padding:0;">
	
		<div id="maingrid" position="left" title="打印参数"></div>
		
		<div class="adjoined-bottom" position="center" title="打印模板">
			<div class="grid-container">
				<div class="grid-width-100">
					<div id="editor"></div>
				</div>
			</div>
		</div>

	</div>
</body>
</html>
