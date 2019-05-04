$(document).ready(function () {
    new Vue({
        el: "#Senti",
        data: {
            selectClass: "",
            classes: "",
            dates: [],
            codeHighs: [],
            codeLows: [],
            codeComments: [],

            codes: [],

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
            searchProject:function () {

            }
        },

        created() {
            $("#loader").show();
            $("#CommitMessage").hide();
            var self = this;
            this.$http.get("http://localhost:8080/git/codeSenti").then(function (response) {
                var res = response.data;
                this.classes = res;


                $(function () {

                    $('#allClasses').jstree({
                        'core': {
                            'data': res
                        }
                    });


                    // 6 create an instance when the DOM is ready
                    var tree = $('#allClasses').jstree();
                    // 7 bind to events triggered on the tree
                    $('#allClasses').on('changed.jstree', function (e, data) {//点击展开
                        $('#allClasses').jstree(true).toggle_node(data.selected);
                    })

                    $('#allClasses').on("changed.jstree", function (e, data) {
                        var id = data.instance.get_node(data.selected).id;
                        if (tree.is_leaf(id)) {
                            self.selectClass = id;

                            // self.$options.methods.getValue()
                            self.$http.get("http://localhost:8080/git/getSingleSenti/" + self.selectClass).then(function (response) {
                                var res = response.data;
                                self.dates = res.dates;
                                self.codeHighs = res.codeHighs;
                                self.codeLows = res.codeLows;
                                self.codeComments = res.codeComments;

                                var posData = [];
                                var negData = [];
                                var comments = [];

                                for (var i = 0; i < this.dates.length; i++) {
                                    posData[i] = [self.dates[i], self.codeHighs[i]];
                                    negData[i] = [self.dates[i], self.codeLows[i]];
                                    comments[i] = self.codeComments[i];
                                }

                                self.codes = res.codes;
                                var code = self.codes.join("\n");

                                document.getElementById("codes").innerHTML = code;

                                var myChart = echarts.init(document.getElementById('CodeComment'), 'macarons');
                                var option = {
                                    title: {
                                        text: 'Code Sentiment',
                                        subtext: self.selectClass
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
                                        data: posData
                                    }, {
                                        name: 'negative',
                                        type: 'line',
                                        showAllSymbol: true,
                                        symbolSize: 10,
                                        data: negData
                                    }]
                                };
                                myChart.setOption(option);

                                if (myChart._$handlers.click) {
                                    myChart._$handlers.click.length = 0;
                                }
                                myChart.on('click', function (params) {
                                    $('#codeModal').modal();
                                    document.getElementById("code-body").innerHTML = comments[params.dataIndex];
                                });
                            })
                        }

                    });
                })


                $("#loader").hide();
                $("#CodeSentis").show();
            })
        }
    })

})