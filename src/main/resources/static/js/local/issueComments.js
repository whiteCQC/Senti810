new Vue({
    el: "#Senti",
    data: {
        issueName:"",
        name:[],
        datetime:[],
        comments:[]
    },
    mounted:function(){
        this.showComments();//页面渲染前先触发这个函数获取数据
    },
    methods:{
        showComments:function(){
            self = this;
            this.$http.get("http://localhost:8080/search/getComments").then(function (response) {
                var res=response.data;
                var issuename=res[0][1];//issue的名字
                var names=[];           //人名
                var date=[];            //评论日期
                var comment=[];         //评论
                var posData=[];         //积极得分
                var negData=[];         //消极得分
                var count=[];           //评论数量
                var count_p1,count_p2,count_p3,count_p4,count_p5,count_n1,count_n2,count_n3,count_n4,count_n5;
                count_p1=count_p2=count_p3=count_p4=count_p5=count_n1=count_n2=count_n3=count_n4=count_n5=0;
                for(var i=0;i<res.length;i++){
                    names[i]=res[i][2];
                    date[i]=res[i][3];
                    comment[i]=res[i][4];
                    posData[i]=res[i][7];
                    negData[i]=res[i][8];
                    count[i]=i+1;
                    if(posData[i]==1){
                        count_p1++;
                    }else if(posData[i]==2){
                        count_p2++;
                    }else if(posData[i]==3){
                        count_p3++;
                    }else if(posData[i]==4){
                        count_p4++;
                    }else if(posData[i]==5){
                        count_p5++;
                    }
                    if(negData[i]==-1){
                        count_n1++;
                    }else if(negData[i]==-2){
                        count_n2++;
                    }else if(negData[i]==-3){
                        count_n3++;
                    }else if(negData[i]==-4){
                        count_n4++;
                    }else if(negData[i]==-5){
                        count_n5++;
                    }
                    console.log(posData[i]);
                    console.log(negData[i]);
                }
                this.issueName=issuename;
                this.name=names;
                this.datetime=date;
                this.comments=comment;

                var myChart = echarts.init(document.getElementById('container'));
                var myChart2 = echarts.init(document.getElementById('chartdiv1'));
                var option = {
                    title : {
                        text : '',
                    },
                    tooltip: {
                        trigger: 'axis'
                    },
                    legend : {
                        data : ['positive','negative']
                    },
                    toolbox: {
                        show : true,
                        feature : {
                            mark : {show: true},
                            dataView : {show: true, readOnly: false},
                            magicType : {show: true, type: ['line', 'bar']},
                            restore : {show: true},
                            saveAsImage : {show: true}
                        }
                    },
                    calculable : true,
                    dataZoom: {
                        show: true,
                        realtime: true,
                        start: 0,
                        end: 100
                    },
                    grid: {
                        y2: 80
                    },
                    xAxis: {
                        data:date

                    },
                    yAxis: {
                        type: 'value'
                    },
                    series: [{
                        name: 'positive',
                        type: 'line',
                        smooth:'true',
                        showAllSymbol: true,
                        symbolSize: 10,
                        data:posData,
                        markPoint : {
                            data : [
                                {type : 'max', name: '最大值'},
                                {type : 'min', name: '最小值'}
                            ]
                        },
                        markLine : {
                            data : [
                                {type : 'average', name: '平均值'}
                            ]
                        }

                    },{
                        name: 'negative',
                        type: 'line',
                        showAllSymbol: true,
                        symbolSize: 10,
                        data:negData,
                        markPoint : {
                            data : [
                                {type : 'max', name: '最大值'},
                                {type : 'min', name: '最小值'}
                            ]
                        },
                        markLine : {
                            data : [
                                {type : 'average', name: '平均值'}
                            ]
                        }
                    },

                    ]
                };
                var option2 = {
                    timeline : {
                        data :['positive','negative'],
                        label : {

                        }
                    },
                    options : [
                        {
                            title : {
                                text: '',
                            },
                            tooltip : {
                                trigger: 'item',
                                formatter: "{a} <br/>{b} : {c} ({d}%)"
                            },
                            legend: {
                                show:true,
                                data:['1','2','3','4','5']
                            },
                            toolbox: {
                                show : true,
                                feature : {
                                    mark : {show: true},
                                    dataView : {show: true, readOnly: false},
                                    magicType : {
                                        show: true,
                                        type: ['funnel','pie'],
                                        option: {
                                            funnel: {
                                                x: '25%',
                                                width: '50%',
                                                funnelAlign: 'left',
                                                max: 1700
                                            }
                                        }
                                    },
                                    restore : {show: true},
                                    saveAsImage : {show: true}
                                }
                            },
                            series : [
                                {
                                    name:'积极等级',
                                    type:'pie',
                                    center: ['50%', '45%'],
                                    radius: '50%',
                                    data:[
                                        {value:count_p1, name:'等级1'},
                                        {value:count_p2, name:'等级2'},
                                        {value:count_p3, name:'等级3'},
                                        {value:count_p4, name:'等级4'},
                                        {value:count_p5, name:'等级5'},
                                    ]
                                }
                            ]
                        },
                        {
                            series : [
                                {
                                    name:'消极等级',
                                    type:'pie',
                                    data:[
                                        {value:count_n1, name:'等级-1'},
                                        {value:count_n2, name:'等级-2'},
                                        {value:count_n3, name:'等级-3'},
                                        {value:count_n4, name:'等级-4'},
                                        {value:count_n5, name:'等级-5'},
                                    ]
                                }
                            ]
                        }
                    ]
                };
                myChart.setOption(option2);
                myChart2.setOption(option);
            })
        }


    }
});