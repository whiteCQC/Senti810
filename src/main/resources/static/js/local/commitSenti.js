$(document).ready(function () {
    new Vue({
        el: "#Senti",
        data: {
            Commitdates: [],
            highs: [],
            lows: [],
            commitMessage: [],
            HighCount: [],
            LowCount: [],
            ScoreCount: [],
            relatedClasses: [],
            topHigh:[],
            topLow:[],

            toSearch: ""
        },
        methods: {
            commitSenti:function () {
                self=this;
                this.$http.get("http://localhost:8080/git/projectExist").then(function (response) {
                    var res=response.bodyText;
                    if(res=="no")
                        alert("please search project first");
                    else
                        window.location.href="/view/showCommit.html"
                })
            },
            codeSenti:function () {
                self=this;
                this.$http.get("http://localhost:8080/git/projectExist").then(function (response) {
                    var res=response.bodyText;
                    if(res=="no")
                        alert("please search project first");
                    else
                        window.location.href="/view/showCode.html"
                })
            },
            returnback:function () {
                window.location.href="SearchView"
            }

        },
        created() {
            $("#CodeSentis").hide();
            $("#loader").show();
            self = this;
            this.$http.get("http://localhost:8080/git/commitSenti").then(function (response) {
                var res = response.data;
                this.Commitdates = res.Commitdates;
                this.highs = res.highs;
                this.lows = res.lows;
                this.commitMessage = res.commitMessage;
                this.Scores = res.Scores;
                this.relatedClasses = res.relatedClasses;

                this.ScoreCount = res.ScoreCount;
                this.HighCount = res.HighCount;
                this.LowCount = res.LowCount;

                this.topHigh=res.topHigh;
                this.topLow=res.topLow;


                var posData = [];
                var negData = [];
                var comments = [];
                var rclasses = []
                for (var i = 0; i < this.Commitdates.length; i++) {
                    posData[i] = [this.Commitdates[i], this.highs[i]];
                    negData[i] = [this.Commitdates[i], this.lows[i]];
                    comments[i] = this.commitMessage[i];
                    rclasses[i] = this.relatedClasses[i];
                }
                var myChart1 = echarts.init(document.getElementById('CommitMessage10'), 'macarons');
                var option1 = {
                    title: {
                        text: 'Commit情绪总体情况',
                        subtext: 'Senti-Strength',
                    },
                    tooltip: {
                        trigger: 'item'
                    },
                    legend: {
                        data: ['positive', 'negative']
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
                        data: posData
                    }, {
                        name: 'negative',
                        type: 'scatter',
                        showAllSymbol: true,
                        symbolSize: 10,
                        data: negData
                    }

                    ]
                };

                var myChart2 = echarts.init(document.getElementById('CommitMessage11'), 'macarons');
                var option2 = {
                    title: {
                        text: 'Commit情绪分布(正面)',
                        subtext: 'Senti-Strength',
                        x: 'center'
                    },
                    tooltip: {
                        trigger: 'item',
                        formatter: "{a} <br/>{b} : {c} ({d}%)"
                    },
                    legend: {
                        orient: 'vertical',
                        left: 'left',
                        data: ['情绪平稳', '略显积极', '较为积极', '非常积极']
                    },
                    series: [
                        {
                            name: '情绪程度',
                            type: 'pie',
                            radius: '55%',
                            center: ['50%', '60%'],
                            data: [
                                {value: this.HighCount[0], name: '情绪平稳'},
                                {value: this.HighCount[1], name: '略显积极'},
                                {value: this.HighCount[2], name: '较为积极'},
                                {value: this.HighCount[3], name: '非常积极'}
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
                var myChart3 = echarts.init(document.getElementById('CommitMessage12'), 'macarons');
                var option3 = {
                    title: {
                        text: 'Commit情绪分布(负面)',
                        subtext: 'Senti-Strength',
                        x: 'center'
                    },
                    tooltip: {
                        trigger: 'item',
                        formatter: "{a} <br/>{b} : {c} ({d}%)"
                    },
                    legend: {
                        orient: 'vertical',
                        left: 'left',
                        data: ['情绪平稳', '略显消极', '较为消极', '非常消极']
                    },
                    series: [
                        {
                            name: '情绪程度',
                            type: 'pie',
                            radius: '55%',
                            center: ['50%', '60%'],
                            data: [
                                {value: this.LowCount[0], name: '情绪平稳'},
                                {value: this.LowCount[1], name: '略显消极'},
                                {value: this.LowCount[2], name: '较为消极'},
                                {value: this.LowCount[3], name: '非常消极'}
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

                $("#loader").hide();
                $("#CommitMessage").show();

                myChart1.on('click', function (params) {
                    $('#commitModal').modal();

                    document.getElementById("commit-body").innerHTML = comments[params.dataIndex];
                    document.getElementById("related").innerHTML = rclasses[params.dataIndex];
                });
            })
        }

    })

})