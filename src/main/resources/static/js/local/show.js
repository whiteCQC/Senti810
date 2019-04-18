$(document).ready(function(){
    $("#showCommit").click(function(){
        $("#CommitMessage").show();
        $("#CodeSentis").hide();
    });
    $("#showCode").click(function(){
        $("#CommitMessage").hide();
        $("#CodeSentis").show();
    });

});

new Vue({
    el: "#Senti",
    data: {
        Commitdates: [],
        highs: [],
        lows: [],

        selectClass:"",
        classes:"",
        dates:[],
        codeHighs:[],
        codeLows:[],

        codes:[]
    },
    methods: {
        commitSenti: function () {
            self = this;
            this.$http.get("http://localhost:8080/git/commitSenti").then(function (response) {
                var res = response.data;
                this.Commitdates = res.Commitdates;
                this.highs = res.highs;
                this.lows = res.lows;

                var myChart = echarts.init(document.getElementById('CommitMessage'));
                var option = {
                    title: {
                        text: 'Commit Message Sentiment',
                    },
                    tooltip: {
                        trigger: 'axis'
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
                    xAxis: {
                        type: 'category',
                        boundaryGap: false,
                        axisLine: {onZero: false},
                        data: this.Commitdates
                    },
                    yAxis: {
                        type: 'value'
                    },
                    series: [{
                        name: 'positive',
                        type: 'line',
                        data: this.highs,
                        markLine: {
                            data: [{
                                type: 'average',
                                name: 'average'
                            }]
                        }
                    }, {
                        name: 'negative',
                        type: 'line',
                        data: this.lows,
                        markLine: {
                            data: [{
                                type: 'average',
                                name: 'average'
                            }]
                        }
                    }]
                };
                myChart.setOption(option);
            })
        },
        codeSenti: function () {
            self = this;
            this.$http.get("http://localhost:8080/git/codeSenti").then(function (response) {
                var res = response.data;
                this.classes = res;
            })
        },

        getValue: function () {
            self = this;
            var selectClass=this.selectClass;
            selectClass=selectClass.replace("/",".");
            this.$http.get("http://localhost:8080/git/getSingleSenti/"+selectClass).then(function (response) {
                var res = response.data;
                this.dates = res.dates;
                this.codeHighs = res.highs;
                this.codeLows = res.lows;

                this.codes=res.codes;

                var code=this.codes.join("\n");

                document.getElementById("codes").innerHTML=code;

                var myChart = echarts.init(document.getElementById('CodeComment'));
                var option = {
                    title: {
                        text: 'Code Sentiment',
                        subtext:selectClass
                    },
                    tooltip: {
                        trigger: 'axis'
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
                    xAxis: {
                        type: 'category',
                        boundaryGap: false,
                        axisLine: {onZero: false},
                        data: this.dates
                    },
                    yAxis: {
                        type: 'value'
                    },
                    series: [{
                        name: 'positive',
                        type: 'line',
                        data: this.codeHighs,
                        markLine: {
                            data: [{
                                type: 'average',
                                name: 'average'
                            }]
                        }
                    }, {
                        name: 'negative',
                        type: 'line',
                        data: this.codeLows,
                        markLine: {
                            data: [{
                                type: 'average',
                                name: 'average'
                            }]
                        }
                    }]
                };
                myChart.setOption(option);
            })
        }
    }

})

