<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
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
.btn2 { position: absolute; width: 30px; height: 100%; font-size: 30px; text-align: center; top: 1px; line-height: 83px; cursor: pointer; background: #b1d3ec; opacity: 0; filter:alpha(opacity=0); }
.prev2 { left: 0px; }

.next2 { right: -1px; }

.imgs { float: left; height: 100%; margin: 0 10px; }

.imgList2 { padding:0 20px; position: absolute; }

.banner-w { position: relative; }

.clear { cursor:pointer; position: absolute; border-radius: 0 0 0 40px; background: #d3d3d3 url(/CHD-HRP/lib/hrp/med/medInvSlider/del.png) no-repeat 10px center; background-size: 60%; width: 40px; height: 40px; font-size: 12px; line-height: 30px; text-align: center; right: 0; top: 0; opacity: 0; filter:alpha(opacity=0); }

.active{ border: 3px solid #7c9ed8; }
.scroll { overflow-x: visible; }

#pageloading{ display:none; position:absolute; left:0px; top:0px; background:white url('<%=path%>/lib/images/loading.gif') no-repeat center; width:100%; height:100%; z-index:99999; }
</style>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script src="<%=path%>/lib/hrp/med/med.js"	type="text/javascript"></script>
<script src="<%=path%>/lib/hrp/med/medInvSlider/cLslider.js"></script>
<script src="<%=path%>/lib/Lodop/barcode.js"	type="text/javascript"></script>
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    var flag = true;
    $(function ()
    {
        $("#layout1").ligerLayout({
            leftWidth : 255,
            onLeftToggle:function(){			//每展开左边树即刷新一次表格，以免结构混乱
                grid._onResize()
            },
            onEndResize:function(a,b){    //每调整左边树宽度大小即刷新一次表格，以免结构混乱
                // if(b.target == $(".l-layout-dragging-yline")[0])   //触发此事件时先判断一下触发目标，否则触发滚动条时也会执行
                    grid._onResize()
            }
        });
        loadTree();
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);
		loadHotkeys();
        //query();
        $("#left").height($("#layout1").height() - 25);
    });
    
    //查询要提交的数据
    function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
        //根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'med_type_code',//药品类别
			value : liger.get("med_type_code").getText() == null ? "" :liger.get("med_type_code").getText().split(" ")[0]
    	});
		grid.options.parms.push({
			name : 'inv_code',//药品编码
			value : $("#inv_code").val()
		});
		grid.options.parms.push({
			name : 'inv_name',//药品名称
			value : $("#inv_name").val()
		});
		grid.options.parms.push({
			name : 'amortize_type',//摊销方式
			value : liger.get("amortize_type").getValue() == null ? "" : liger.get("amortize_type").getValue().split(",")[0]
    	});
		grid.options.parms.push({
			name : 'sup_id',//供应商编码
			value : liger.get("sup_code").getValue() == null ? "" : liger.get("sup_code").getValue().split(",")[0]
    	});
		grid.options.parms.push({
			name : 'fac_id',//生产厂商编码
			value : liger.get("fac_code").getValue() == null ? "" : liger.get("fac_code").getValue().split(",")[0]
    	});
		grid.options.parms.push({
			name : 'is_highvalue',//是否高值
			value : liger.get("is_highvalue").getValue() == null ? "" : liger.get("is_highvalue").getValue()
    	});
    	grid.options.parms.push({
			name : 'is_charge',//是否收费
			value : liger.get("is_charge").getValue() == null ? "" : liger.get("is_charge").getValue()
    	});

    	grid.options.parms.push({
			name : 'is_involved',//是否介入
			value : liger.get("is_involved").getValue() == null ? "" : liger.get("is_involved").getValue()
    	});

    	grid.options.parms.push({
			name : 'is_implant',//是否植入
			value : liger.get("is_implant").getValue() == null ? "" : liger.get("is_implant").getValue()
    	});
    	grid.options.parms.push({
			name : 'alias',//药品名称
			value : $("#alias").val()
		});
    	grid.options.parms.push({
			name : 'inv_model',//规格型号
			value : $("#inv_model").val()
		});
    	grid.options.parms.push({
			name : 'bid_code',//项目中标编码
			value : $("#bid_code").val()
		});
    	grid.options.parms.push({
			name : 'cert_code',//注册证号
			value : $("#cert_code").val()
		});
    	grid.options.parms.push({
			name : 'start_date',//注册证起始日期
			value : $("#start_date").val()
		});
    	grid.options.parms.push({
			name : 'end_date',//注册证结束日期
			value : $("#end_date").val()
		});

    	grid.options.parms.push({
			name : 'use_state',//是否停用
			value : liger.get("use_state").getValue() == null ? "" : liger.get("use_state").getValue()
    	});
    	grid.options.parms.push({
			name : 'is_com',//是否代销
			value : liger.get("is_com").getValue() == null ? "" : liger.get("is_com").getValue()
    	});
    	grid.options.parms.push({
			name : 'fim_type_code',//财务分类
			value : liger.get("fim_type_code").getValue() == null ? "" : liger.get("fim_type_code").getValue()
    	});
    	grid.options.parms.push({
    		name : 'state',     	
    		value : liger.get("state").getValue() == null ? "" : liger.get("state").getValue().split(",")[0]}
    	);
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    //表格中显示的字段和功能键
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ {
        	   display: '药品编码', name: 'inv_code', align: 'left',width:100,frozen:true,
        	   render : function(rowdata, rowindex, value) {
					return '<a href=javascript:update_open("'
							+ rowdata.group_id
							+ ',' + rowdata.hos_id
							+ ',' + rowdata.copy_code
							+ ',' + rowdata.inv_id
							+ '")>'+rowdata.inv_code+'</a>';
				}
			}, {
				display: '药品名称', name: 'inv_name', align: 'left',width:150,frozen:true
			}, {
				display: '项目中标编码', name: 'bid_code', align: 'left',width:100
			}, {
				display: '药品类别', name: 'med_type_name', align: 'left',width:100
			}, {
				display: '规格型号', name: 'inv_model', align: 'left',width:130
			}, {
				display: '拼音码', name: 'spell_code', align: 'left',width:130
			}, {
				display: '计量单位', name: 'unit_name', align: 'left',width:50
			}, /* {
				display: '拼音码', name: 'spell_code', align: 'left'
			}, */ {
				display: '计划价', name: 'plan_price', align: 'right', width:80,
				render : function(rowdata, rowindex, value) {
					return value == null ? "" : formatNumber(value, '${p08006 }', 1);
				}
			}, {display: '管理类别', name: 'manage_type_name', align: 'left',width:150
			}, {display: '注册证号', name: 'cert_code', align: 'left',width:150
			},{
				display: '证件开始日期', name: 'start_date', align: 'left',width:100
			}, {
				display: '证件到期日期', name: 'end_date', align: 'left',width:100
			},  {
				display: '生产厂商', name: 'fac_name', align: 'left',width:200
			}, {
				display: '证件信息', name: '', align: 'center',width:100,
				render : function(rowdata, rowindex, value) {
					return '<a href=javascript:cert_open("'
						+ rowdata.group_id
						+ ',' + rowdata.hos_id
						+ ',' + rowdata.copy_code
						+ ',' + rowdata.inv_id
						+ '")>证件信息</a>';
				}
			},{
				display: '供应商', name: 'sup_name', align: 'left',width:200
			},/*  {
				display: '是否收费', name: 'is_charge', align: 'left',width:50 ,hide:true,
				render : function(rowdata, rowindex, value) {
					return rowdata.is_charge == 1 ? "是" : "否";
				}
			}, {
				display: '是否高值', name: 'is_highvalue', align: 'left', width:50 ,hide:true,
				render : function(rowdata, rowindex, value) {
					return rowdata.is_highvalue == 1 ? "是" : "否";
				}
			},  */{
				display: '是否在用', name: 'use_state', align: 'left', width:50 ,
				render : function(rowdata, rowindex, value) {
					return rowdata.use_state == 1 ? "是" : "否";
				}
			}, /* {
				display: '是否介入', name: 'is_involved', align: 'left', width:50 ,hide:true,
				render : function(rowdata, rowindex, value) {
					return rowdata.is_involved == 1 ? "是" : "否";
				}
			}, {
				display: '是否植入', name: 'is_implant', align: 'left', width:50 ,hide:true,
				render : function(rowdata, rowindex, value) {
					return rowdata.is_implant == 1 ? "是" : "否";
				}
			}, {
				display: '是否代销', name: 'is_com', align: 'left', width:50 ,hide:true,
				render : function(rowdata, rowindex, value) {
					return rowdata.is_com == 1 ? "是" : "否";
				}
			}, */{
				display: '别名', name: 'alias', align: 'left',width:100
			}, {
				display: '存储编码', name: 'memory_encoding', align: 'left',width:100
			},{
				display: '供应商', name: 'show_sup', align: 'center',width:50, isSort: false,
	        	render : function(rowdata, rowindex, value) {
					return '<a href=javascript:sup_open("'
								+ rowdata.group_id
								+ ',' + rowdata.hos_id
								+ ',' + rowdata.copy_code
								+ ',' + rowdata.inv_id
								+ ',' + rowdata.inv_code
								+ '")>查看</a>';
				}
			}, {
				display: '药品状态', name: 'state', align: 'left', width:50 ,
				render : function(rowdata, rowindex, value) {
					return rowdata.state == 1 ? "已审核" : "未审核";
				}
			},{
				display: '审核时间', name: 'check_date', align: 'left', width:50 ,
			},{
				display: '图片', name: 'picture', align: 'center', width:100 , isSort: false,
				render : function(rowdata, rowindex, value) {
						return "<a href=javascript:showImg('"+rowdata.group_id+"','"+rowdata.hos_id+"','"+rowdata.copy_code+"','"+rowdata.inv_id+"')>查看图片</a>";

				}
			} ,{
				display: '操作', name: 'edit', align: 'left', width:100 , isSort: false,
				render : function(rowdata, rowindex,
						value) {
						return "<div class='l-button' style='width: 60px; margin-top:1px; margin-left: 20px;' ligeruiid='Button10084'"
						+"onclick=upload('"+rowdata.group_id+"','"+rowdata.hos_id+"','"+rowdata.copy_code+"','"+rowdata.inv_id+"')>"
	       					+"<span>上传图片</span></div>";
				}
			} ],
	 		rowAttrRender: function(rowdata,rowid){
	 			if(rowdata.use_state == 0){
	 				return "style='color: #FF0000'"
	 			}
 			},
 			//alternatingRow: false,
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMedInv.do',
			width: '100%', height: '100%', checkbox: true,rownumbers:true,
			delayLoad: true,
			selectRowButtonOnly:true,//heightDiff: -10,
			toolbar: { items: [
				{ text: '查询(<u>Q</u>)', id:'search', click: query,icon:'search' },
				{ line:true },
				{ text: '添加(<u>A</u>)', id:'add', click: add_open, icon:'add' },
				{ line:true },
				{ text: '复制', id:'copy', click: addInvCopy, icon:'copy' },
				{ line:true },
				{ text: '删除(<u>D</u>)', id:'delete', click: remove, icon:'delete' },
				{ line:true },
				{ text: '审核(<u>C</u>)', id:'audit', click: audit, icon:'audit' },
				{ line:true },
 				{ text: '导出Excel(<u>E</u>)', id:'export', click: exportExcel, icon:'pager' },
				{ line:true },
				{ text: '打印(<u>P</u>)', id:'print', click: printDate, icon:'print' },
				{ line:true }, /* 
				{ text: '下载导入模板(<u>B</u>)', id:'downTemplate', click:downTemplate, icon:'down' },
				{ line:true }, */
				{ text: '导入(<u>I</u>)', id:'import', click: imp,icon:'up' },
	 			{ line:true },
				{ text: '批量修改(<u>P</u>)', id:'updateBatch', click: updateBatch_open,icon:'edit' },
	 			{ line:true },
				{ text: '品种码打印(<u>T</u>)', id: 'printBar', click: printBar, icon: 'print' },
				/* { line:true }, 
				{ text: '模板设置', id:'printSet', click: printSet, icon:'print' },
				{ line:true }, 
				{ text: '批量打印（<u>P</u>）', id:'print', click: print, icon:'print' } */
	 			/*
	 			{ text: '下载导入模板(<u>N</u>)', id:'downTemplateSup', click:downTemplateSup, icon:'down' },
				{ line:true },
				{ text: '导入(<u>O</u>)', id:'importSup', click: impSup,icon:'up' } */
			]}
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }

    /* 设置树形菜单 */
    function onSelect(note){
    	
    	var typeText = note.data.text;
    	var typeId;
    	if(!note.data.children){
    		typeId = note.data.id + "," + note.data.no+",1";
        	flag = true;
    	}else{
    		typeId = note.data.id + "," + note.data.no+",0";
    		flag = false;
    	}

    	liger.get("med_type_code")._changeValue(typeId, typeText, false);
        query();
    }

    function loadTree(){
        ajaxJsonObjectByUrl("../type/queryMedTypeDictByTree.do?isCheck=false", {},
            function(responseData) {
                if (responseData != null) {
                    $("#tree").ligerTree({
                        data : responseData.Rows,
                        parentIcon: null,
                        childIcon: null ,
                        checkbox : false,
                        idFieldName : 'code',
                        parentIDFieldName : 'pcode',
                        textFieldName : 'text',
                        onSelect: onSelect,
                        isExpand: false,
                        nodeWidth:400,
                    });
            //$("#tree").css({"width":"100%"});
                    treeManager = $("#tree").ligerGetTreeManager();
                    //treeManager.collapseAll(); //全部收起
                }
            }
        );
    }
    function add_open(){
    	if(flag != true){
    		$.ligerDialog.warn("请选择末级药品类别");
    		return false;
    	}

    	parent.$.ligerDialog.open({
			title: '药品添加',
			height: 550,
			width: 1000,
			url: 'hrp/med/info/basic/inv/medInvAddPage.do?isCheck=false&',
			modal: true, showToggle: false, showMax: true, showMin:false, isResize: true, top: 10,
			parentframename: window.name,
			buttons: [
				{ text: '保存', onclick: function(item, dialog){dialog.frame.save();}, cls:'l-dialog-btn-highlight' },
				{ text: '取消', onclick: function(item, dialog){dialog.close();}}
			]
		});
    }
    //上传图片
   function upload(group_id,hos_id,copy_code,inv_id){
	   var param = "&group_id="+group_id+"&hos_id="+hos_id+"&copy_code="+copy_code+"&inv_id="+inv_id;
	   var index = layer.open({
			type : 2,
			title : '药品图片',
			shadeClose : true,
			shade : false,
			maxmin : true, //开启最大化最小化按钮
			area : [ '893px', '500px' ],
			content : 'medInvPictureImportPage.do?isCheck=false'+param
		});
		layer.full(index);
   }

   var imgDialog;
   //显示图片
   function showImg(group_id,hos_id,copy_code,inv_id){
	   $.post("queryMedInvPicture.do?isCheck=false",{group_id:group_id,hos_id:hos_id,copy_code:copy_code,inv_id:inv_id},function(data){
           var sessionPath = "<%=path%>/";
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
           var nextBtn1 = $('<img class="next btn1" src="<%=path%>/lib/hrp/med/medInvSlider/next.png"  />');     //翻页键
           var prevBtn1 = $('<img class="prev btn1"  src="<%=path%>/lib/hrp/med/medInvSlider/prev.png" />');
           var nextBtn2 = $('<span class="next2 btn2">></span>');      //小图片翻页键
           var prevBtn2 = $('<span class="prev2 btn2"><</span>');
           var clearBtn = $("<a class='clear' ></a>" );           //删除键


           $(".banner").append(nextBtn1);
           $(".banner").append(prevBtn1);
           $(".banner-w").append(nextBtn2);
           $(".banner-w").append(prevBtn2);
           $(".banner").append(clearBtn);
           $.each(data.Rows,function(i,v){
               var picture_name = v.picture_name.split(".")[0];
               var picture_type = v.picture_name.split(".")[1];
               var new_picture_name = picture_name+"_out."+picture_type;
               var item2 = imgs.clone();
               var item3 = imgbig.clone();
               item2.append("<img src='"+sessionPath+v.picture_url+"/"+new_picture_name+"' />");
               item3.append("<img src='"+sessionPath+v.picture_url+"/"+picture_name+"."+picture_type+"' />");
               $(".imgList2").append(item2);
               $(".imgList").append(item3);

		   });
		   imgDialog = $.ligerDialog.open({ target: $("#light"),showMax: true, showToggle: true, showMin: true, isResize: true, slide: false,height: 550,
				width: 1000,title:'药品图片' });
           $(".wrapper").clSlide({
            wrapperWidth:600,           //最外层宽高度
            WrapperHeight:400,
            btn1Size:60,          //删除按钮 大小
            banner2Height:83,      //微缩图列表高度
            banner2ImgWidth:60,     //微缩图片宽度
            clearBtnShow:true,     //删除按钮是否显示
            onClearBtnClick:function(index,callback){           //删除按钮事件：参数：图片索引值，回调函数(可写值)

            var delImgSrc = $(".imgs").eq(index).find("img").attr('src');
            if(data.Rows.length == 0){

                imgDialog.hide();
                return;
               };
             if(data.Rows.length == 1){
                 delImg(data.Rows[0].group_id,data.Rows[0].hos_id,data.Rows[0].copy_code,data.Rows[0].inv_id,data.Rows[0].picture_name,data.Rows[0].picture_url,callback);
                  return false;
            };
            for(var i in data.Rows){
               var picture_name = data.Rows[i].picture_name.split(".")[0];
               var picture_type = data.Rows[i].picture_name.split(".")[1];
               var new_picture_name = picture_name+"_out."+picture_type;
               var delImgSrc1 = sessionPath+data.Rows[i].picture_url+"/"+new_picture_name;
               if(delImgSrc == delImgSrc1) {
                    delImg(data.Rows[i].group_id,data.Rows[i].hos_id,data.Rows[i].copy_code,data.Rows[i].inv_id,data.Rows[i].picture_name,data.Rows[i].picture_url,callback);
                    return false;
               }
            }

            }
         });

	   },"json");
   }

   //删除图片
   function delImg(group_id,hos_id,copy_code,inv_id,picture_name,picture_url,callback){
        callback = callback || "";
	   $.ligerDialog.confirm('确定删除?', function (yes){
        if (yes) {
            if( typeof callback == "function")
                      callback();
            $.ajax({
              type:'post',
              url:'deleteMedInvPicture.do?isCheck=false',
              data:{group_id:group_id,hos_id:hos_id,copy_code:copy_code,inv_id:inv_id,picture_name:picture_name,picture_url:picture_url},
              dataType:'json',
              beforeSend:function(XMLHttpRequest){
                    $("#pageloading").show();
              },
              success:function(data,textStatus){
                   if(data.state=="true"){
                    imgDialog.hide();
                    showImg(group_id,hos_id,copy_code,inv_id)
                }
              },
              complete:function(XMLHttpRequest,textStatus){
                    $("#pageloading").hide();

              },
              error:function(){
                //请求出错处理
              }
            });

        }
	   });
   }

    function update_open(obj){
		var vo = obj.split(",");
		var paras = "group_id="+vo[0]
			+"&"+ "hos_id="+vo[1]
			+"&"+ "copy_code="+vo[2]
			+"&"+ "inv_id="+vo[3];

		parent.$.ligerDialog.open({
			title: '药品修改',
			height: 550,
			width: 1150,
			url: 'hrp/med/info/basic/inv/medInvUpdatePage.do?isCheck=false&' + paras.toString(),
			modal: true, showToggle: false, showMax: true, showMin:false, isResize: true, top : 1,
			parentframename: window.name,
			buttons: [
				{ text: '保存', onclick: function(item, dialog){dialog.frame.save();}, cls:'l-dialog-btn-highlight' },
				{ text: '取消', onclick: function(item, dialog){dialog.close();}}
			]
		});
    }
    //复制药品
    function addInvCopy(){

    	var data = gridManager.getCheckedRows();
    	if (data.length == 0){
			$.ligerDialog.warn('请选择一条要复制的药品数据');
    	}else if(data.length > 1){
    		$.ligerDialog.warn('每次只能复制一条药品数据');
    	}else{
    		var paras='';
    		$(data).each(function (){
	    		paras = "&group_id="+this.group_id +"&hos_id="+this.hos_id
						+"&copy_code="+this.copy_code +"&inv_id="+this.inv_id;
	    		return ;
    		})
			parent.$.ligerDialog.open({
				title: '药品复制',height: 550,width: 1000,
				url: 'hrp/med/info/basic/inv/medInvCopyPage.do?isCheck=false'+paras,
				modal: true, showToggle: false, showMax: true, showMin: false, isResize: true, top : 0,
				parentframename: window.name,
				buttons: [
					{ text: '保存', onclick: function(item, dialog){dialog.frame.save();}, cls:'l-dialog-btn-highlight' },
					{ text: '取消', onclick: function(item, dialog){dialog.close();}}
				]
			});
    	}
    }
	//批量修改药品
    function updateBatch_open(){
		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
			var paras = "";
			$(data).each(function (){
				if(paras == ""){
					paras = "group_id="+this.group_id
						+"&"+ "hos_id="+this.hos_id
						+"&"+ "copy_code="+this.copy_code
						+"&"+ "inv_ids="+this.inv_id;
				}else{
					paras = paras + ","+this.inv_id;
				}
			});

			parent.$.ligerDialog.open({
				title: '药品批量修改',
				height: 550,
				width: 1150,
				url: 'hrp/med/info/basic/inv/medInvUpdateBatchPage.do?isCheck=false&' + paras.toString(),
				modal: true, showToggle: false, showMax: true, showMin: false, isResize: true, top : 1,
				parentframename: window.name,
				buttons: [
					{ text: '保存', onclick: function(item, dialog){dialog.frame.save();}, cls:'l-dialog-btn-highlight' },
					{ text: '取消', onclick: function(item, dialog){dialog.close();}}
				]
			});
		}
    }
    function sup_open(obj){

		var vo = obj.split(",");
		var paras = "group_id="+vo[0]
			+"&"+ "hos_id="+vo[1]
			+"&"+ "copy_code="+vo[2]
			+"&"+ "inv_id="+vo[3]
			+"&"+ "inv_code="+vo[4];

		$.ligerDialog.open({
			title: '供应商列表',
			height: 400,
			width: 950,
			url: 'medInvSupUpdatePage.do?isCheck=false&' + paras.toString(),
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true, top : 10,
			/* buttons: [
				{ text: '保存', onclick: function(item, dialog){dialog.frame.saveSup();}, cls:'l-dialog-btn-highlight' },
				{ text: '取消', onclick: function(item, dialog){dialog.close();}}
			] */
		});
    }
	
    function cert_open(obj){
    	var vo = obj.split(",");
		var paras = "group_id="+vo[0]
			+"&"+ "hos_id="+vo[1]
			+"&"+ "copy_code="+vo[2]
			+"&"+ "inv_id="+vo[3];
		parent.$.ligerDialog.open({
			url: 'hrp/med/info/basic/inv/medInvCertInfoMainPage.do?isCheck=false&is_main=1&'+paras, 
			height: 300,width:950, title:'药品证件对应关系',
         	modal:true, showToggle:false, showMax:true ,showMin:false, isResize:true,
         	parentframename: window.name,
       		buttons: [ 
				{ text: '保存', onclick: function(item, dialog){dialog.frame.save();}, cls:'l-dialog-btn-highlight' },
				{ text: '关闭', onclick: function (item, dialog) { dialog.close(); } } 
			] 
		});
	}
    
	function remove(){
		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
			var ParamVo =[];
			$(data).each(function (){
				ParamVo.push(
					this.group_id   +"@"+
					this.hos_id   +"@"+
					this.copy_code   +"@"+
					this.inv_id
				)
			});

			$.ligerDialog.confirm('确定删除?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("deleteMedInv.do",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.state=="true"){
							query();
						}
					});
				}
			});
		}
    }
    
    //导入
    function imp() {
		var para = {
			url : 'hrp/med/info/basic/inv/medInvImportNewPage.do?isCheck=false',
			title : '药品目录导入',
			width : 0,
			height : 0,
			isShowMin : false,
			isModal : true,
			data : {
				grid : grid
			}
		};
		parent.openDialog(para);
	}

    function downTemplate(){

    	location.href = "downTemplate.do?isCheck=false";
    }

     function impSup(){

    	var index = layer.open({
					type : 2,
					title : '药品供应商',
					shadeClose : false,
					shade : false,
					maxmin : true, //开启最大化最小化按钮
					area : [ '893px', '500px' ],
					content : 'medInvSupImportPage.do?isCheck=false'
				});
				layer.full(index);
    	}

    function downTemplateSup(){

    	location.href = "downTemplateSup.do?isCheck=false";
    }
    function loadDict(){
		//字典下拉框
		//autocomplete("#med_type_code", "../../../queryMedTypeDict.do?isCheck=false", "id", "text", true, true, {is_last : 1},"","",160,"",200);
	 	var paras = {
			field_code : "amortize_type"
		}
		autocomplete("#amortize_type", "../../../queryMedSysList.do?isCheck=false", "id", "text", true, true, paras); 
		autocomplete("#sup_code", "../../../queryHosSupDict.do?isCheck=false", "id", "text", true, true,"","","","","",200);
		autocomplete("#fac_code", "../../../queryHosFacInv.do?isCheck=false", "id", "text", true, true);
		//autocomplete("#fina_type_code", "../../../queryMedFinaType.do?isCheck=false", "id", "text", true, true, {is_last : 1});
	    autocomplete("#is_highvalue", "../../../queryMedYearOrNo.do?isCheck=false", "id", "text", true, true);
		autocomplete("#is_charge", "../../../queryMedYearOrNo.do?isCheck=false", "id", "text", true, true);
		autocomplete("#is_involved", "../../../queryMedYearOrNo.do?isCheck=false", "id", "text", true, true);
		autocomplete("#is_implant", "../../../queryMedYearOrNo.do?isCheck=false", "id", "text", true, true); 
		autocomplete("#use_state", "../../../queryMedYearOrNo.do?isCheck=false", "id", "text", true, true);
		autocompleteAsync("#med_type_code", "../../../queryPermMedTypeDict.do?isCheck=false", "id", "text", true, true);
		
		autocomplete("#is_com", "../../../queryMedYearOrNo.do?isCheck=false", "id", "text", true, true);
		
		autocomplete("#fim_type_code", "../../../queryMedFimTypeDict.do?isCheck=false", "id", "text", true, true);
		
		autoCompleteByData("#state", medInv_state.Rows, "id", "text", true, true);//状态
		//药品类别改变事件
        liger.get("med_type_code").set("onSelected", function(newValue, newText, rowData){
        	//flag用于判断是否添加页跳转
        	//如果不选中药品类别,flag=true,则直接跳转,如果选中,则必须是末级分类
        	if(liger.get("med_type_code").getValue() == ''){
        		flag = true;
        	}else{
        		var is_last = liger.get("med_type_code").getValue().split(",")[2];
        		if(is_last == '1'){
        			flag = true;
        		}else{
        			flag = false;
        		}
        	}
        	query();
        });
        $("#inv_code").ligerTextBox({width:160});
        $("#inv_name").ligerTextBox({width:160});
        $("#alias").ligerTextBox({width:160});
        $("#inv_model").ligerTextBox({width:160});
        $("#bid_code").ligerTextBox({width:160});
        $("#cert_code").ligerTextBox({width:160});
        $("#start_date").ligerTextBox({width:90});
        $("#end_date").ligerTextBox({width:90});
        $("#state").ligerTextBox({width:160});
        
	}
    //审核的方法
    function audit(){



    	var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
			var ParamVo =[];
			$(data).each(function (){
				ParamVo.push(
					this.group_id   +"@"+
					this.hos_id   +"@"+
					this.copy_code   +"@"+
					this.inv_id
				)
			});

			$.ligerDialog.confirm('确定审核?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("auditMedInv.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.state=="true"){
							query();
						}
					});
				}
			});
		}

    }

    //键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('A', add);
		hotkeys('D', remove);
		hotkeys('C', audit);
		hotkeys('B', downTemplate);
		hotkeys('E', exportExcel);
		hotkeys('P', printDate); 
		hotkeys('I', imp);
		hotkeys('B', downTemplateSup);
		hotkeys('I', impSup);
	 }
    
   /*  //打印模板设置
    function printSet(){
	  
    	var useId=0;//统一打印
		if('${p08017 }'==1){
			//按用户打印
			useId='${sessionScope.user_id }';
		}
    	parent.$.ligerDialog.open({url : 'hrp/med/info/basic/inv/medInvPrintSetPage?template_code=08029&use_id='+useId,
    		data:{}, height: $(parent).height(),width: $(parent).width(), title:'打印模板设置',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true
    		});
    } */
	//打印数据
    function print(){
   		var useId=0;//统一打印
		if('${p08017 }'==1){
			//按用户打印
			useId='${sessionScope.user_id }';
		}

		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
			
			var inv_id ="" ;
			$(data).each(function (){		
				
				inv_id  += this.inv_id+","
					
			});

			var para={
				paraId :inv_id.substring(0,inv_id.length-1) ,
				template_code:'08029',
				isPrintCount:false,//更新打印次数
				isPreview:true,//预览窗口，传绝对路径
				use_id:useId
				
			}; 
		 	
	    	printTemplate("hrp/med/info/basic/inv/queryMedInvByPrintTemlate.do?isCheck=false",para);
		}
    }
    
    
  //打印数据
  function printDate(){
	  if(grid.getData().length==0){
  		
			$.ligerDialog.error("请先查询数据！");
			
			return;
		}
  	
  	var selPara={};
  	
  	$.each(grid.options.parms,function(i,obj){
  		
  		selPara[obj.name]=obj.value;
  		
  	});
 		
		var dates = getCurrentDate();
  	
  	var cur_date = dates.split(";")[2];
  	//跨所有列:计算列数
  	var colspan_num = grid.getColumns(1).length-1;
  	
 		var printPara={
 			title:'药品字典',
 			head:[
				{"cell":0,"value":"单位: ${sessionScope.hos_name}","colspan":colspan_num,"br":true}
// 				{"cell":0,"value":"统计日期: " + $("#begin_out_date").val() +" 至  "+ $("#end_out_date").val(),"colspan":colspan_num,"br":true}
 			],
 			foot:[
				{"cell":0,"value":"主管:","colspan":3,"br":false} ,
				{"cell":3,"value":"复核人:","colspan":colspan_num-5,"br":false},
				{"cell":colspan_num-2,"value":"制单人： ${sessionScope.user_name}","colspan":2,"br":true},
				{"cell":0,"value":"打印日期: " + cur_date,"colspan":colspan_num,"br":true}
 			],
 			columns:grid.getColumns(1),
 			headCount:1,//列头行数
 			autoFile:true,
 			type:3
 		};
 		ajaxJsonObjectByUrl("queryMedInv.do?isCheck=false", selPara, function (responseData) {
 			printGridView(responseData,printPara);
		});
  }

	function printBar(){
		var arr = [];
		var data = grid.getCheckedRows();
		if(data==""){
			$.ligerDialog.warn("没有勾选条码打印数据.");  
			return;
		}
		
		$(data).each(function(i,v){
			var obj = {
				bar_code: v.inv_code,
				other1: {
					name: "商品信息",
					value:v.inv_name+(v.fac_name ? +"("+v.fac_name+")" : "")
				},
				other2: {
					name: "规格型号",
					value: v.inv_model
				},
				other3: {
					name: "单价",
					value: v.plan_price
				}
			}
			
			arr.push(obj);
		});
		console.log(arr);
		lodopBarCode(arr);
	}
    
 	
	
	//导出
	function exportExcel(){
		
		var params = '';
		//$('#').val();
		var inv_code = $('#inv_code').val();
		if(inv_code != ''){
			params = params + '&inv_code=' + inv_code;
		}
		
		var inv_name = $('#inv_name').val();
		if(inv_name != ''){
			params = params + '&inv_name=' + inv_name;
		}
		
		var inv_model = $('#inv_model').val();
		if(inv_model != ''){
			params = params + '&inv_model=' + inv_model;
		}
		
		var alias = $('#alias').val();
		if(inv_model != ''){
			params = params + '&alias=' + alias;
		}
		
		var sup_code = liger.get('sup_code').getValue();
		if(sup_code != ''){
			params = params + '&sup_id=' + sup_code;
		}
		
		var fac_id = liger.get('fac_code').getValue();
		if(fac_id != ''){
			params = params + '&fac_id=' + fac_id;
		}
		
		var med_type_code = liger.get('med_type_code').getText();
		if(med_type_code != ''){
			params = params + '&med_type_code=' + med_type_code.split(' ')[0];
		}
		
		var bid_code = $('#bid_code').val();
		if(bid_code != ''){
			params = params + '&bid_code=' + bid_code;
		}
		
		var cert_code = $('#cert_code').val();
		if(cert_code != ''){
			params = params + '&cert_code=' + cert_code;
		}
		
		var use_state = liger.get('use_state').getValue();
		if(use_state != ''){
			params = params + '&use_state=' + use_state;
		}
		
		var fim_type_code = liger.get('fim_type_code').getValue();
		if(fim_type_code != ''){
			params = params + '&fim_type_code=' + fim_type_code;
		}
		
		var state = liger.get('state').getValue();
		if(state != ''){
			params = params + '&state=' + state;
		}
		
		var start_date = $('#start_date').val();
		if(start_date != ''){
			params = params + '&start_date=' + start_date;
		}
		
		var end_date = $('#end_date').val();
		if(end_date != ''){
			params = params + '&end_date=' + end_date;
		}
		
		console.log(params);
		
		location.href='expMedInvToExcel.do?isCheck=false' + params;		
	}
	
	
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div class="l-layout" id="layout1" >
        <div position="left" id="left" title="药品类别"  style="height:100%;">
            <!-- <h2><font id="font1">编码规则：<font id="font2" color="red"></font></font></h2> -->
            <div class="l-scroll" style="overflow: auto;height:100%;">
                <ul id="tree" />
            </div>
        </div>
        <div position="center" >
            <div id="toptoolbar" ></div>
        	<!-- 此处为上面显示 -->
            <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
                <tr>

                    <td align="right" class="l-table-edit-td"  >
                    	药品编码：
                    </td>
                    <td align="left" class="l-table-edit-td">
                    	<input name="inv_code" type="text" id="inv_code" ltype="text" validate="{required:true,maxlength:20}" />
                    </td>

                    <td align="right" class="l-table-edit-td"  >
                    	药品名称：
                    </td>
                    <td align="left" class="l-table-edit-td">
                    	<input name="inv_name" type="text" id="inv_name" ltype="text" validate="{required:true,maxlength:20}" />
                    </td>

                     <td align="right" class="l-table-edit-td"  >
                		规格型号：
                	</td>
                    <td align="left" class="l-table-edit-td">
                    	<input name="inv_model" type="text" id="inv_model" ltype="text" validate="{required:true,maxlength:200}" />
                    </td>

                </tr>
                <tr>
                    <td align="right" class="l-table-edit-td"  >
                    	别名：
                    </td>
                    <td align="left" class="l-table-edit-td">
                    	<input name="alias" type="text" id="alias" ltype="text" validate="{required:true,maxlength:20}" />
                    </td>
                    
                    <td align="right" class="l-table-edit-td"  >
                    	供应商：
                    </td>
                    <td align="left" class="l-table-edit-td">
                    	<input name="sup_code" type="text" id="sup_code" ltype="text" validate="{required:true,maxlength:20}" />
                    </td>
                    
                    <td align="right" class="l-table-edit-td"  >
                    	生产厂商：
                    </td>
                    <td align="left" class="l-table-edit-td">
                    	<input name="fac_code" type="text" id="fac_code" ltype="text" validate="{required:true,maxlength:20}" />
                    </td>
               
                	
                </tr>
                <tr>

                    <td align="right" class="l-table-edit-td"  >
                		药品类别：
                	</td>
                    <td align="left" class="l-table-edit-td">
                    	<input name="med_type_code" type="text" id="med_type_code" ltype="text" validate="{required:true,maxlength:200}" />
                    </td>

                	 <td align="right" class="l-table-edit-td"  >
	            		项目中标编码：
	                </td>
	                <td align="left" class="l-table-edit-td">
	            	     <input name="bid_code" type="text" id="bid_code" ltype="text" validate="{required:false,maxlength:50}"  />
	                </td>
	                 <td align="right" class="l-table-edit-td"  >
	            		注册证号：
	                </td>
	                <td align="left" class="l-table-edit-td">
	            	     <input name="cert_code" type="text" id="cert_code" ltype="text" validate="{required:false,maxlength:50}"  />
	                </td>
	                
                </tr>
                <tr>
                	 <td align="right" class="l-table-edit-td"  >
	            		是否在用：
	                </td>
	                <td align="left" class="l-table-edit-td">
	            	     <input name="use_state" type="text" id="use_state" ltype="text" validate="{required:false}"  />
	                </td>
	                
	                <td align="right" class="l-table-edit-td"  >药品财务分类：</td>
	                <td align="left" class="l-table-edit-td">
	            	     <input name="fim_type_code" type="text" id="fim_type_code" ltype="text" validate="{required:false}"  />
	                </td>

                	<td align="right" class="l-table-edit-td"  >审核状态：</td>
	                <td align="left" class="l-table-edit-td">
	            	     <input name="state" type="text" id="state" ltype="text" validate="{required:false}"  />
	                </td>
                </tr>
                <tr>
                	 <td align="right" class="l-table-edit-td"  >
	            		注册证起始日期：
	                </td>
	                <td align="left" class="l-table-edit-td">
	                	<table>
	                		<tr>
	                			<td>
	                				<input name="start_date" type="text" id="start_date" required="true" class="Wdate" onFocus="WdatePicker({isShowClear:true,dateFmt:'yyyy-MM-dd'})"  />
	                			</td>
				                <td align="left" class="l-table-edit-td">至</td>
				                <td align="left" class="l-table-edit-td">
				            	     <input name="end_date" type="text" id="end_date" required="true" class="Wdate" onFocus="WdatePicker({isShowClear:true,dateFmt:'yyyy-MM-dd'})"  />
				                </td>
	                		</tr>
	                	</table>
	                </td>
                </tr>
                <tr style="display:none">
                	 <td align="right" class="l-table-edit-td"  >
                    	摊销方式：
                    </td>
                    <td align="left" class="l-table-edit-td">
                    	<input name="amortize_type" type="text" id="amortize_type" ltype="text" validate="{required:true,maxlength:20}" />
                    </td>
                     <td align="right" class="l-table-edit-td"  >
                		是否收费：
                	</td>
                    <td align="left" class="l-table-edit-td">
                    	<input name="is_charge" type="text" id="is_charge" ltype="text" validate="{required:true,maxlength:20}" />
                    </td>
                    <td align="right" class="l-table-edit-td"  >是否代销：</td>
	                <td align="left" class="l-table-edit-td">
	            	     <input name="is_com" type="text" id="is_com" ltype="text" validate="{required:false}"  />
	                </td>
                </tr>
                <tr style="display:none">
	                <td align="right" class="l-table-edit-td"  >
	                		是否高值：
                	</td>
                    <td align="left" class="l-table-edit-td">
                    	<input name="is_highvalue" type="text" id="is_highvalue" ltype="text" validate="{required:true,maxlength:20}" />
                    </td>
                     <td align="right" class="l-table-edit-td"  >
                		是否介入：
                	</td>
                    <td align="left" class="l-table-edit-td">
                    	<input name="is_involved" type="text" id="is_involved" ltype="text" validate="{required:true,maxlength:200}" />
                    </td>

                    <td align="right" class="l-table-edit-td"  >
                		是否植入：
                	</td>
                    <td align="left" class="l-table-edit-td">
                    	<input name="is_implant" type="text" id="is_implant" ltype="text" validate="{required:true,maxlength:200}" />
                    </td>
                	
                </tr>
            </table>
        	<div id="maingrid"></div>
        	<div id="resultPrint" style="display:none">
        	   	<table width="100%">
        			<thead>
        			<tr>
                        <th width="200">药品ID</th>
                        <th width="200">药品编码</th>
                        <th width="200">药品名称</th>
                        <th width="200">别名</th>
                        <th width="200">药品类别ID</th>
                        <th width="200">财务类别编码</th>
                        <th width="200">规格型号</th>
                        <th width="200">计量单位</th>
                        <th width="200">药品属性</th>
                        <!-- <th width="200">摊销方式</th> -->
                        <th width="200">计价方法</th>
                        <th width="200">供应商ID</th>
                        <th width="200">生产厂商ID</th>
                        <th width="200">计划价</th>
                        <th width="200">加价率</th>
                        <th width="200">零售价</th>
                        <th width="200">是否批次管理</th>
                        <th width="200">是否保质期管理</th>
                        <!-- <th width="200">是否为耐用品</th> -->
                        <th width="200">呆滞标准</th>
                        <th width="200">是否呆滞积压</th>
                        <th width="200">是否作二级库管理</th>
                        <th width="200">是否自制品</th>
                        <th width="200">是否为加价销售</th>
                        <th width="200">是否条码管理</th>
                        <th width="200">品种条码</th>
                        <th width="200">是否证件管理</th>
                        <!--<th width="200">是否高值</th>-->
                        <th width="200">是否专购</th>
                        <th width="200">是否收费</th>
                        <th width="200">启用日期</th>
                        <th width="200">停用日期</th>
                        <th width="200">单位重量</th>
                        <th width="200">单位体积</th>
                        <th width="200">品牌</th>
                        <!-- <th width="200">代理商</th>-->
                        <th width="200">药品结构</th>
                        <th width="200">药品用途</th>
                        <th width="200">是否停用</th>
                        <th width="200">修改备注</th>
        			</tr>
        			   	</thead>
        			   	<tbody></tbody>
        	   	</table>
           	</div>
           	<div id="light" style="display: none;">
                <div id="pageloading"></div>
                <div id="imgt" class="wrapper">
                      <div class="banner">
                          <ul class="imgList">
                         </ul>
                            <img class="next btn1" src="<%=path%>/lib/hrp/med/medInvSlider/next.png"  />
                            <img  class="prev btn1"  src="<%=path%>/lib/hrp/med/medInvSlider/prev.png" />
                            <a class=' clear' >
                                <img alt="" src="<%=path%>/lib/hrp/med/medInvSlider/del.png">
                            </a>
                      </div>
                     <div class="banner-w">
                        <div class=banner2>
                            <ul class="imgList2">
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
</body>
</html>
