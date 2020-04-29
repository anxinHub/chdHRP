(function (window, $) {
    $.fn.etChart = function (option) {
        var self = this.get(0);
        // var default_options = {
        //     // type: '',
        //     // data: {},
        //     options: {

        //     }
        // }
        
        // options: {
            // 自适应
            // responsive: true,

            // 图例配置。
            // legend: {
            //     display: true, // 显示图例
            //     position: 'top', // 图例显示位置
            //     onClick: function (event, params) {
            //         // console.log('click', arguments)
            //     },
            //     onHover: function (event, params) { // 鼠标在图例经过时，回调，注，每次移动鼠标都会执行
            //         // console.log('hover', arguments)
            //     },
            //     reverse: false, // 图表是否返序显示
            //     // 图表样式
            //     labels: {
            //         boxWidth: 40,
            //         fontSize: 12,
            //         fontStyle: 'normal',
            //         fontColor: '#666',
            //         fontFamily: "'Helvetica Neue', 'Helvetica', 'Arial', sans-serif",
            //         padding: 10,
            //         // filter: function (item, data) { // 图例过滤，但是图表数据仍会显示
            //         //     return item
            //         // }
            //    }
            // },
            
            // 提示
            // tooltips: {
                // enabled: true, // 启用工具提示
                // custom: null,  // 自定义工具，function
                // mode: 'index', // 互动模式
                // intersect: false, // 如果为true，提示仅在鼠标位置与元素相交才显示，false，在附近就显示
                // position: 'average', // average在平局位置显示；nearest离事件位置最近的元素位置显示
                // callbacks: {......}, // 提示的回调
                // itemSort: fun // 排序工具提示项
                // filter: fun // 过滤工具
            // },

            // hover: {
                // mode: 'nearest',
                // intersect: true
            // },

            // 标题
            // title: {
                // display: true, // 是否显示辩题, 默认false
                // position: 'top', // 标题位置
                // text: 'Chart.js Bar Chart - multi axis', // 默认''
            // },

            // 轴
            // scales: {
            
            //     x轴
                 
            //     xAxes: [{
            //         // stacked: true,
            //         display: true,
            //         scaleLabel: {
            //             display: true,
            //             labelString: 'Month'
            //         }
            //     }],
                 
            //     y轴
                
            //     yAxes: [
            //         {
            //             // type: 'linear',
            //             // position: 'left',
            //             // id: 'y-axis-1',
            //             // stacked: true
            //             display: true,
            //             scaleLabel: {
            //                 display: true,
            //                 labelString: 'Value'
            //             }
            //         }
            //     ]
            // }
        // }

        /**
         * 一般
         */
        // 互动
            // 活动
            // 模式mode
                // mode: 'point' // 查找与点相交的项目
                // mode: 'nearest' // 获取最接近点的项目
                // mode: 'index' // 如果intersect为true，第一个相交项用于确定数据中的索引，为false，则使用最近的项目来确定索引
                // mode: 'dataset' // 查找同一数据集中的项目
                // mode: 'x' // 根据x轴坐标返回所有相交的项目，注：仅适用于笛卡尔图表
                // mode: 'y' // 根据y轴。同上

        /**
         * 类型
         */
        // 条形图
            // type: bar 垂直
            // type: horizontalBar 横向
        // 折线图
            // type: line
        // 雷达图
            // type: radar
        // 甜甜圈
            // type: doughnut
        // 饼图
            // type: pie
        // 极地区
            // type: polarArea
        // 气泡图
            // type: bubble
        // 散点图
            // type: scatter
        // 

        /**
         * 方法
         */
        // 直接对data操作，然后
        // myBar.update();

        var setting = $.extend({}, option);

        /////////////////////////////////////////////////////////////////////////////////
        //                                自定义配置数据                               //
        /////////////////////////////////////////////////////////////////////////////////
        var Months = [ '一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'];

        var chartColorsObj = {
            red: 'rgb(255, 99, 132)',
            orange: 'rgb(255, 159, 64)',
            yellow: 'rgb(255, 205, 86)',
            green: 'rgb(75, 192, 192)',
            purple: 'rgb(153, 102, 255)',
            blue: 'rgb(54, 162, 235)',
            grey: 'rgb(201, 203, 207)'
        };
        var chartColorsArr = ['rgb(255, 99, 132)', 'rgb(255, 159, 64)', 'rgb(255, 205, 86)', 'rgb(75, 192, 192)', 'rgb(153, 102, 255)', 'rgb(54, 162, 235)', 'rgb(201, 203, 207)', '#c23531', '#2f4554', '#61a0a8', '#d48265', '#d48265', '#749f83', '#ca8622', '#bda29a'];

        ////////////////////////////////////////////////////////////////
        //                       根据类型配置参数                     //
        ////////////////////////////////////////////////////////////////
        
        // 饼图
        if (setting.type === 'pie') {
            if (setting.data.datasets) {
                setting.data.datasets.forEach(function (item, index) {
                    // 判断是否有设置背景颜色
                    if (!item.backgroundColor) {
                        var len = item.data.length;

                        item.backgroundColor = [];
                        for (var i = 0; i < len; i++) {
                            var a = i % chartColorsArr.length;

                            item.backgroundColor.push(chartColorsArr[a]);
                        }
                    }
                })
            }

            var default_pie_options = {
                responsive: true,
                legend: {
                    position: 'left'
                },
                tooltips: {
                    callbacks: {
                        // 计算百分比，并显示在提示框中
                        afterLabel: function (tooltipItem, data) {
                            var index = tooltipItem.index;
                            var valueArray = data.datasets[0].data;
                            var sum = 0;
                            valueArray.forEach(function (item) {
                                sum += Number(item);
                            })
                            var percentage = (valueArray[index] / sum * 100).toFixed(2);
                            return '（' + percentage + '%）';
                        }
                    }
                }
            }
            Chart.defaults.global.defaultFontColor = '#333';

            setting.options = $.extend({}, default_pie_options, setting.options);
        }

        ////////////////////////////////////////////////////////////////
        //                          构建                              //
        ////////////////////////////////////////////////////////////////
        var ctx = self.getContext('2d');
        var etChart = new Chart(ctx, setting);

        return etChart;
    }
})(window, jQuery);