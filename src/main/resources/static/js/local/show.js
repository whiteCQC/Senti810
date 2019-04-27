new Vue({
    el: "#Senti",
    data: {
        Commitdates: [],
        highs: [],
        lows: [],
        commitMessage:[],
        HighCount:[],
        LowCount:[],
        Scores:[],
        ScoreCount:[],

        selectClass:"",
        classes:"",
        dates:[],
        codeHighs:[],
        codeLows:[],
        codeComments:[],

        codes:[],

        toSearch:""
    },
    methods: {
        commitSenti: function () {
            $("#CodeSentis").hide();
            $("#loader").show();
            self = this;
            this.$http.get("http://localhost:8080/git/commitSenti").then(function (response) {
                var res = response.data;
                this.Commitdates = res.Commitdates;
                this.highs = res.highs;
                this.lows = res.lows;
                this.commitMessage=res.commitMessage;
                this.Scores=res.Scores;

                this.ScoreCount=res.ScoreCount;
                this.HighCount=res.HighCount;
                this.LowCount=res.LowCount;

                var posData=[];
                var negData=[];
                var comments=[];
                var scoreData=[];
                for(var i=0;i<this.Commitdates.length;i++){
                    posData[i]=[this.Commitdates[i],this.highs[i]];
                    negData[i]=[this.Commitdates[i],this.lows[i]];
                    comments[i]=this.commitMessage[i];
                    scoreData[i]=[this.Commitdates[i],this.Scores[i]];
                }
                var myChart1 = echarts.init(document.getElementById('CommitMessage10'),'macarons');
                var option1 = {
                    title : {
                        text : 'Commit情绪总体情况',
                        subtext: 'Senti-Strength',
                    },
                    tooltip: {
                        trigger: 'item'
                    },
                    legend : {
                        data : ['positive','negative']
                    },
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
                        type: 'time',
                        name: 'time',

                    },
                    yAxis: {
                        type: 'value'
                    },
                    series: [{
                        name: 'positive',
                        type: 'scatter',
                        showAllSymbol: true,
                        symbolSize: 10,
                        data:posData
                    },{
                        name: 'negative',
                        type: 'scatter',
                        showAllSymbol: true,
                        symbolSize: 10,
                        data:negData
                    }

                    ]
                };

                var myChart2 = echarts.init(document.getElementById('CommitMessage11'),'macarons');
                var option2 = {
                    title : {
                        text: 'Commit情绪分布(正面)',
                        subtext: 'Senti-Strength',
                        x:'center'
                    },
                    tooltip : {
                        trigger: 'item',
                        formatter: "{a} <br/>{b} : {c} ({d}%)"
                    },
                    legend: {
                        orient: 'vertical',
                        left: 'left',
                        data: ['情绪平稳','略显积极','较为积极','非常积极']
                    },
                    series : [
                        {
                            name: '情绪程度',
                            type: 'pie',
                            radius : '55%',
                            center: ['50%', '60%'],
                            data:[
                                {value:this.HighCount[0], name:'情绪平稳'},
                                {value:this.HighCount[1], name:'略显积极'},
                                {value:this.HighCount[2], name:'较为积极'},
                                {value:this.HighCount[3], name:'非常积极'}
                            ],
                            itemStyle: {
                                emphasis: {
                                    shadowBlur: 10,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                }
                            }
                        }
                    ]
                };
                var myChart3 = echarts.init(document.getElementById('CommitMessage12'),'macarons');
                var option3 = {
                    title : {
                        text: 'Commit情绪分布(负面)',
                        subtext: 'Senti-Strength',
                        x:'center'
                    },
                    tooltip : {
                        trigger: 'item',
                        formatter: "{a} <br/>{b} : {c} ({d}%)"
                    },
                    legend: {
                        orient: 'vertical',
                        left: 'left',
                        data: ['情绪平稳','略显消极','较为消极','非常消极']
                    },
                    series : [
                        {
                            name: '情绪程度',
                            type: 'pie',
                            radius : '55%',
                            center: ['50%', '60%'],
                            data:[
                                {value:this.LowCount[0], name:'情绪平稳'},
                                {value:this.LowCount[1], name:'略显消极'},
                                {value:this.LowCount[2], name:'较为消极'},
                                {value:this.LowCount[3], name:'非常消极'}
                            ],
                            itemStyle: {
                                emphasis: {
                                    shadowBlur: 10,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                }
                            }
                        }
                    ]
                };


                var myChart4 = echarts.init(document.getElementById('CommitMessage20'),'macarons');
                var option4 = {
                    title : {
                        text : 'Commit情绪总体情况',
                        subtext: 'Sanford-corenlp',
                    },
                    tooltip: {
                        trigger: 'item'
                    },
                    legend : {
                        data : ['Sentiment']
                    },
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
                        type: 'time',
                        name: 'time',

                    },
                    yAxis: {
                        type: 'value'
                    },
                    series: [{
                        name: 'Sentiment',
                        type: 'scatter',
                        showAllSymbol: true,
                        symbolSize: 10,
                        data:scoreData
                    }
                    ]
                };

                var myChart5 = echarts.init(document.getElementById('CommitMessage21'),'macarons');
                var option5 = {
                    title : {
                        text: 'Commit情绪分布',
                        subtext: 'Sanford-corenlp',
                        x:'center'
                    },
                    tooltip : {
                        trigger: 'item',
                        formatter: "{a} <br/>{b} : {c} ({d}%)"
                    },
                    legend: {
                        orient: 'vertical',
                        left: 'left',
                        data: ['非常消极','较为消极','情绪平稳','较为积极','非常积极']
                    },
                    series : [
                        {
                            name: '情绪程度',
                            type: 'pie',
                            radius : '55%',
                            center: ['50%', '60%'],
                            data:[
                                {value:this.ScoreCount[0], name:'非常消极'},
                                {value:this.ScoreCount[1], name:'较为消极'},
                                {value:this.ScoreCount[2], name:'情绪平稳'},
                                {value:this.ScoreCount[3], name:'较为积极'},
                                {value:this.ScoreCount[4], name:'非常积极'}
                            ],
                            itemStyle: {
                                emphasis: {
                                    shadowBlur: 10,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                }
                            }
                        }
                    ]
                };

                myChart1.setOption(option1);
                myChart2.setOption(option2);
                myChart3.setOption(option3);
                myChart4.setOption(option4);
                myChart5.setOption(option5);

                $("#loader").hide();
                $("#CommitMessage").show();

                myChart1.on('click',function(params){
                    $('#commitModal').modal();
                    document.getElementById("commit-body").innerHTML=comments[params.dataIndex];
                });
                myChart4.on('click',function(params){
                    $('#commitModal1').modal();
                    document.getElementById("commit-body1").innerHTML=comments[params.dataIndex];
                });
            })
        },
        codeSenti: function () {
            $("#loader").show();
            $("#CommitMessage").hide();
            self = this;
            this.$http.get("http://localhost:8080/git/codeSenti").then(function (response) {
                var res = response.data;
                this.classes = res;
                $("#loader").hide();
                $("#CodeSentis").show();
            })
        },

        getValue: function () {

            String.prototype.replaceAll  = function(s1,s2){
                return this.replace(new RegExp(s1,"gm"),s2);
            }

            self = this;
            var selectClass=this.selectClass;
            selectClass=selectClass.replaceAll("/",".");
            this.$http.get("http://localhost:8080/git/getSingleSenti/"+selectClass).then(function (response) {
                var res = response.data;
                this.dates = res.dates;
                this.codeHighs = res.codeHighs;
                this.codeLows = res.codeLows;
                this.codeComments=res.codeComments;

                var posData=[];
                var negData=[];
                var comments=[];

                for(var i=0;i<this.dates.length;i++){
                    posData[i]=[this.dates[i],this.codeHighs[i]];
                    negData[i]=[this.dates[i],this.codeLows[i]];
                    comments[i]=this.codeComments[i];
                }

                this.codes=res.codes;
                var code=this.codes.join("\n");

                document.getElementById("codes").innerHTML=code;

                var myChart = echarts.init(document.getElementById('CodeComment'),'macarons');
                var option = {
                    title: {
                        text: 'Code Sentiment',
                        subtext:selectClass
                    },
                    tooltip: {
                        trigger: 'item'
                    },
                    legend : {
                        data : ['positive','negative']
                    },
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
                        type: 'time',
                        name: 'time'
                    },
                    yAxis: {
                        type: 'value'
                    },
                    series: [{
                        name: 'positive',
                        type: 'line',
                        showAllSymbol: true,
                        symbolSize: 10,
                        data:posData
                    }, {
                        name: 'negative',
                        type: 'line',
                        showAllSymbol: true,
                        symbolSize: 10,
                        data:negData
                    }]
                };
                myChart.setOption(option);

                if(myChart._$handlers.click){
                    myChart._$handlers.click.length = 0;
                }
                myChart.on('click',function(params){
                    $('#codeModal').modal();
                    document.getElementById("code-body").innerHTML=comments[params.dataIndex];
                });
            })
        }
    }

})

