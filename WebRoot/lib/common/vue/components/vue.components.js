//======================门户配置页组件======================
//为兼容ie只能用拼接字符串写末班...... 
//栏目导航
Vue.component('module-list', {
    props: ['modules', 'emptylist', 'added'],
    data: function () {
        return { already_hover: null }
    },
    template: 
        '<div class="sys-modules clearfix" >'
            +'<ul>'
                +'<li v-for="(item,index) in modules"'
                +':class="{\'module-hover\':index == already_hover&&added.indexOf(index)==-1,\'module-added\':added.indexOf(index)!=-1}"'
                +'@mouseenter="already_hover=index" '
                +'@mouseleave="already_hover=null"'
                +'>'
                +'<span class="txt" v-if="item">{{item.title_name}}</span>'
                +'<span class="add_txt" @click="add(item);added.push(index)">'
                    +'添加'
                +'</span>'
                +'</li> '
            +'</ul>'
        +'</div>'
    ,
    methods: {
        add: function (item) {
            var sort_code = this.emptylist.length + 1;
            var configitem = {
                title_name: item.title_name,
                title_code: item.title_code,
                show_rows: 5,
                is_colspan: 1
            }
            this.emptylist.push(configitem);
        }
    },
    created: function () {
        //组件创建时触发
    }
});

//操作栏
Vue.component('tools-bar', {
    props: ['modules'],
    template:
        '<div class="tools-bar">'
            +'<a  href="javascript:;" '
                +':data="modules"'
                +'@click="save"'
            +'>保存</a>'
        +'</div>'
    ,
    methods: {
        save: function () {
            this.$emit('on-save');
        }
    }

});

//模块列表
Vue.component('show-modules', {
    props: ['modules', 'emptylist', 'added'],
    data: function () {
        return { startIndex: -1 }
    },
    template: 
        '<div class="show-modules">'
            +'<div v-for="(item,index ) in emptylist" :style="{width:item.is_colspan>1?\'100%\':\'50%\'}"'
                +'draggable="true" '
                +'ondragover="event.preventDefault()"'
                +'@drop="drop(index)"'
                +'@dragstart="startIndex=index"'
            +'> '
                +'<div :id="\'content\'+index" class="content">'
                    +'<div class="template">'
                        +'<div class="template-title">'
                            +"{{item?item.title_name:''}}"
                        +'</div>'
                        +'<div class="template-content">'
                            +'<div>'
                                +'数据条数：<input class="num" type="number" v-model="item.show_rows" max=20 min=1>'
                            +'</div>'
                            +'<div>'
                                +'注：<span style="color:red;font-size:12px">栏目中数据显示条数</span>'
                            +'</div>'
                            +'<div>'
                                +'单元格数：<input class="cell_number" type="number" v-model="item.is_colspan" autocomplete="true"  max=2 min=1>'
                            +'</div>'
                            +'<div>'
                                +'注：<span style="color:red;font-size:12px">栏目中所占单元格 (最多两个)</span>'
                            +'</div>'
                        +'</div>'
                        +'<span class="remove"  @click="remove(item)">×</span>'
                    +'</div>'
                +'</div>'
            +'</div>'
            +'<div  class="empty-modules" v-if="emptylist.length===0">'
                +'自定义栏目区域'
            +'</div>'
        +'</div>'
    ,
    methods: {
        remove: function (item) {
            //还原栏目未添加状态
            var modulesIndex = this.getIndexByItem(item);
            this.added.splice(this.added.indexOf(modulesIndex), 1);

            //删除自定义区域栏目
            var emptyIndex = this.emptylist.indexOf(item);
            this.emptylist.splice(emptyIndex, 1);
        },
        drop: function (index) {
            this.swap(this.emptylist, this.startIndex, index);
            this.emptylist.splice(-1, 0);//重新渲染
        },
        getIndexByItem: function (item) {//根据item获取在modules中的索引
            var modulesObj = _.indexBy(this.modules, 'title_code');
            var obj = modulesObj[item.title_code];
            var modulesIndex = this.modules.indexOf(obj);
            return modulesIndex;
        },
        swap: function (array, first, second) {
            var tmp = array[first];
            array[first] = array[second];
            array[second] = tmp;
        }
    },
    created: function () {
        //组件创建时触发
        var that = this;
        _.forEach(this.emptylist, function (v, i) {
            var index = that.getIndexByItem(v);
            that.added.push(index);
        });
    }
})

//========================门户页面组件================================
//主页列表
Vue.component('main-modules',{
    props: ['modulelist'],
    data: function () {
        return {  }
    },
    template: 
        '<ul>'
            +'<li class="module" v-for="item in modulelist" :style="{width:item.is_colspan>1?\'100%\':\'50%\'}">'
                +'<div>'
                    +'<div class="title">'
                        +'<span>'
                            +'{{item.title_name}}'
                        +'</span>'
                        +'<a href="javascript:" @click="more(item.click_url)">more</a>'
                    +'</div>'
                    +'<main-module :contentlist = "item[item.title_code]" >'
                +'</div>'
            +'</li>'
        +'</ul>'
    ,
    methods:{
        more:function(url){
            window.location.href = url;
        }
    },
    created:function(){
        
    }
});

//主页模块
Vue.component('main-module',{
    props:['contentlist'],
    data:function(){
        return { };
    },
    template:
        '<div class="content" :style="{height:\'100px\'}">'
            +'<table style="width:100%">'
                +'<tr v-for="item in contentlist" class="item">'
                    +'<td>{{item.obj_name}}</td>'
                    +'<td>{{item.store_name}}</td>'
                    +'<td>{{item.diff}}</td>'
                +'</tr>'
            +'</table>'
            +'<div v-if="contentlist.length==0" class="empty">'
                +'暂无数据'
            +'</div>'
        +'<div>'
        
    ,
    methods:{},
    created:function(){
        
    }
})