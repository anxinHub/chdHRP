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
<script src="<%=path%>/lib/hrp/mat/matInvSlider/cLslider.js"></script>
<style type="text/css">

    * { margin: 0; padding: 0; }

img { border: 0; width: 100%; height: 100%; }

ol,
ul,
li { list-style: none; }

.wrapper { margin: 10px auto 0; }

.banner { margin: 10px auto 2px; width: 100%; position: relative; overflow: hidden; }
.banner:hover .next,
.banner:hover .clear,
.banner:hover .prev{ opacity:1; filter:alpha(opacity=100); }
.next,
.prev { position: absolute; cursor: pointer; opacity: .0; filter:alpha(opacity=0); }

.prev { left: 0; }

.next { right: 0; }

.imgList { height: 100%; position: absolute; }

.imgbig { float: left; height: 100%; }

.banner2 { border: 1px solid #b1d3ec; width: 100%; position: relative; overflow: scroll; overflow-x: hidden; overflow-y: hidden; }
 .banner-w:hover .btn2
 { opacity:1; filter:alpha(opacity=100); }
.btn2 { position: absolute; width: 30px; height: 100%; font-size: 30px; text-align: center; top: 1px; line-height: 209%; cursor: pointer; background: #b1d3ec; opacity: 0; filter:alpha(opacity=0); }
.prev2 { left: 0px; }

.next2 { right: -1px; }

.imgs { float: left; height: 100%; margin: 0 10px; }

.imgList2 { padding:0 20px; position: absolute; }

.banner-w { position: relative; }

.clear { cursor:pointer; position: absolute; border-radius: 0 0 0 40px; background: #d3d3d3 url(/CHD-HRP/lib/hrp/mat/matInvSlider/del.png) no-repeat 10px center; background-size: 60%; width: 40px; height: 40px; font-size: 12px; line-height: 30px; text-align: center; right: 0; top: 0; opacity: 0; filter:alpha(opacity=0); }

.active{ border: 3px solid #7c9ed8; }
.scroll { overflow-x: visible; }

#pageloading{ display:none; position:absolute; left:0px; top:0px; background:white url('<%=path%>/lib/images/loading.gif') no-repeat center; width:100%; height:100%; z-index:99999; }
</style>
<script type="text/javascript">
	var grid;
	var gridManager = null;
	var userUpdateStr;
	var ass_card_no = '${ass_card_no}';

	$(function() {
		$("#layout1").ligerLayout({ 
			rightWidth: 700,
      		//heightDiff: -8,
      		//每展开左边树即刷新一次表格，以免结构混乱
      		onRightToggle:function(){			
          		grid._onResize();
              },
          //每调整左边树宽度大小即刷新一次表格，以免结构混乱
          	onEndResize:function(a,b){    
    				grid._onResize();
              }	
    	});

		loadDict()//加载下拉框
		//加载数据
		loadHead(null);
		query();
	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		grid.options.parms.push({
			name : 'ass_nature',
			value : '${ass_nature}'
		});
		grid.options.parms.push({
			name : 'ass_card_no',
			value : ass_card_no
		});
		grid.loadData(grid.where);
	}
	function setAssCardNo(no) {
		ass_card_no = no;
		query();
	}
	function loadHead() {
		grid = $("#maingrid").ligerGrid(
				{
					columns : [ {
						display : '照片编号',
						name : 'photo_code',
						align : 'left',
						render : function(rowdata, rowindex,
								value) {
							return "<a href=javascript:openUpdate('"
									+ rowdata.group_id
									+ "|"
									+ rowdata.hos_id
									+ "|"
									+ rowdata.copy_code
									+ "|"
									+ rowdata.ass_card_no
									+ "|"
									+ rowdata.photo_code
									+ "')>"
									+ rowdata.photo_code
									+ "</a>";
						}
					}, {
						display : '照片名称',
						name : 'photo_name',
						align : 'left'
					},{
						display : '是否停用',
						name : 'is_stop',
						align : 'left',
						render : function(rowdata, rowindex,
								value) {
							if(rowdata.is_stop == 0){
								return '否';
							}else{
								return '是'
							}
						}
					} ],
					dataAction : 'server',
					dataType : 'server',
					usePager : true,
					url : 'queryAssPhoto.do',
					width : '100%',
					height : '90%',
					checkbox : true,
					rownumbers : true,
					delayLoad : true,
					selectRowButtonOnly : true,//heightDiff: -10,
					scroll : true,
					frozen : true,
					alternatingRow : false,
					scrollToPage : true,
					scrollToAppend : true,
					toolbar : {
						items : [ {
							text : '添加',
							id : 'add',
							click : add_open,
							icon : 'add'
						}, {
							line : true
						}, {
							text : '删除',
							id : 'delete',
							click : remove,
							icon : 'delete'
						} ]
					},
					onAfterShowData:function (){
						viewImg();
					},
					onDblClickRow : function(rowdata, rowindex, value) {
						openUpdate(rowdata.group_id + "|" + rowdata.hos_id
								+ "|" + rowdata.copy_code + "|"
								+ rowdata.ass_card_no + "|"
								+ rowdata.photo_code);
					}
				});

		gridManager = $("#maingrid").ligerGetGridManager();
		viewImg();
	}
	
	function viewImg(){
		
		var data = grid.getData();
		
	           $(".imgList").empty();
	           $(".imgList2").empty();

	         if($(".btn1").length > 0)
	                {
	                    $(".btn1").remove();
	                    $(".btn2").remove();
	                    $(".clear").remove();
	                }

	           var imgbig = $("<li class='imgbig'></li>");              //大图片
	           var imgs = $("<li class='imgs'></li>");              //小图片
	           var nextBtn1 = $('<img class="next btn1" src="/CHD-HRP/lib/hrp/mat/matInvSlider/next.png"  />');     //翻页键
	           var prevBtn1 = $('<img class="prev btn1"  src="/CHD-HRP/lib/hrp/mat/matInvSlider/prev.png" />');
	           var nextBtn2 = $('<span class="next2 btn2">></span>');      //小图片翻页键
	           var prevBtn2 = $('<span class="prev2 btn2"><</span>');
	           var clearBtn = $("<a class='clear' ></a>" );           //删除键


	           $(".banner").append(nextBtn1);
	           $(".banner").append(prevBtn1);
	           $(".banner-w").append(nextBtn2);
	           $(".banner-w").append(prevBtn2);
	           $(".banner").append(clearBtn);
	           $.each(data,function(i,v){
	        	   var file_url = "viewAssPhoto.do?isCheck=false&file_url="+v.file_url;
	              /*  var picture_name = v.picture_name.split(".")[0];
	               var picture_type = v.picture_name.split(".")[1];
	               var new_picture_name = picture_name+"_out."+picture_type;*/
	               var item2 = imgs.clone(); 
	               var item3 = imgbig.clone();
	               item2.append("<img src='"+file_url+"' />").height('53px'); 
	               item3.append("<img src='"+file_url+"' />");
	               $(".imgList2").append(item2); 
	               $(".imgList").append(item3);
 
			   });
			   /* imgDialog = $.ligerDialog.open({ target: $("#light"),showMax: true, showToggle: true, showMin: true, isResize: true, slide: false,height: 550,
					width: 1000,title:'物资材料图片' }); */
	           $(".wrapper").clSlide({
	            wrapperWidth:600,           //最外层宽高度
	            WrapperHeight:400,
	            btn1Size:60,          //删除按钮 大小
	            banner2Height:63,      //微缩图列表高度
	            banner2ImgWidth:60,     //微缩图片宽度
	            clearBtnShow:true,     //删除按钮是否显示
	            /* onClearBtnClick:function(index,callback){           //删除按钮事件：参数：图片索引值，回调函数(可写值)

	            var delImgSrc = $(".imgs").eq(index).find("img").attr('src');
	            if(data.Rows.length == 0){

	                imgDialog.hide();
	                return;
	               };
	            } */
	         });
		
		//$('#aa').attr('src',"viewAssPhoto.do?isCheck=false&file_url="+file_url)
		//location.href="viewAssPhoto.do?isCheck=false&file_url="+file_url;
	}

	function add_open() {
		if(ass_card_no == '0'){
			parent.$.ligerDialog.warn("请先保存卡片");
    		return;
    	}
		parent.$.ligerDialog
				.open({
					title : '资产照片添加',
					height : 350,
					width : 600,
					url : 'assPhotoAddPage.do?isCheck=false&ass_nature=${ass_nature}&ass_card_no=${ass_card_no}',
					modal : false,
					showToggle : false,
					showMax : false,
					showMin : true,
					isResize : true,
					parentframename : window.name,
					top : 90,
					buttons : [ {
						text : '确定',
						onclick : function(item, dialog) {
							dialog.frame.saveAssPhoto();
						},
						cls : 'l-dialog-btn-highlight'
					}, {
						text : '关闭',
						onclick : function(item, dialog) {
							dialog.close();
						}
					} ]
				});

	}

	function remove() {

		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			parent.$.ligerDialog.error('请选择行');
		} else {
			var ParamVo = [];
			$(data).each(
					function() {
						ParamVo.push(this.group_id + "@" + this.hos_id + "@"
								+ this.copy_code + "@" + this.ass_card_no + "@"
								+ this.photo_code + "@" + this.file_url)
					});
			parent.$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteAssPhoto.do", {
						ParamVo : ParamVo.toString(),
						ass_nature : '${ass_nature}'
					}, function(responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			});
		}
	}


	function openUpdate(obj) {

		var vo = obj.split("|");
		if("null"==vo[3]){
			return false;
			
		}
		var parm = "group_id=" + vo[0] + "&" + "hos_id=" + vo[1] + "&"
				+ "copy_code=" + vo[2] + "&" + "ass_card_no=" + vo[3] + "&"
				+ "photo_code=" + vo[4] + "&ass_nature=${ass_nature}";
		parent.$.ligerDialog.open({
			url : 'assPhotoUpdatePage.do?isCheck=false&'+parm,
			data : {},
			height : 350,
			width : 600,
			title : '资产照片修改',
			modal : false,
			showToggle : false,
			showMax : false,
			showMin : true,
			isResize : true,
			parentframename : window.name,
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.saveAssPhoto();
				},
				cls : 'l-dialog-btn-highlight'
			}, {
				text : '取消',
				onclick : function(item, dialog) {
					dialog.close();
				}
			} ]
		});

	}
	function loadDict() {
		//字典下拉框
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="layout1">
		<div position="center" title="照片列表">
			<div id="maingrid"></div>
		</div>
		<div position="right" title="资产照片">
			<div id="light">
                <div id="pageloading"></div>
                <div id="imgt" class="wrapper">
                   <div class="banner">
                       <ul class="imgList">
                      </ul>
                         <img class="next btn1" src="<%=path%>/lib/hrp/mat/matInvSlider/next.png"  />
                         <img  class="prev btn1"  src="<%=path%>/lib/hrp/mat/matInvSlider/prev.png" />
                         <a class=' clear' >
                             <img alt="" src="<%=path%>/lib/hrp/mat/matInvSlider/del.png">
                         </a>
                   </div>
                   <div class="banner-w">
                      <div class=banner2>
                          <ul class="imgList2" style='margin: 3px auto;'>
                             <!--  <li class="imgs"></li> -->

                          </ul>
                      </div>
                      <span class="next2 btn2">></span>
                      <span class="prev2 btn2"><</span>
                   </div>
                </div>
            </div>
		</div>
	</div>
	
	<div id="pageloading" class="l-loading" style="display: none"></div>
	
</body>
</html>
