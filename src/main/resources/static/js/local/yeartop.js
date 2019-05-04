new Vue({
    el: "#Senti",
    data: {
        year2019dataP:[],
        year2018dataP:[],
        year2017dataP:[],
        des2019dataP:[],
        des2018dataP:[],
        des2017dataP:[],
        year2019dataN:[],
        year2018dataN:[],
        year2017dataN:[],
        des2019dataN:[],
        des2018dataN:[],
        des2017dataN:[],
        posData:[],
        negData:[],
        posDes:[],
        negDes:[],
        issueWeb:""
    },
    mounted:function(){
        this.yeartop();

    },
    methods:{
        yeartop: function () {
            self = this;
            this.$http.get("http://localhost:8080/search/details").then(function (response) {
                var res = response.data;
                var y_7_p = [];
                var y_8_p = [];
                var y_9_p = [];
                var y_7_n = [];
                var y_8_n = [];
                var y_9_n = [];
                var d_7_p = [];
                var d_8_p = [];
                var d_9_p = [];
                var d_7_n = [];
                var d_8_n = [];
                var d_9_n = [];
                var index=res[0][0].lastIndexOf("/");
                var issueWeb=res[0][0].substring(0,index+1);
                this.issueWeb=issueWeb;
                for (var j = 0; j < 10; j++) {
                    if (j < 5) {
                        y_7_p[j] = res[0][j].substring(res[0][j].lastIndexOf("/")+1);
                        y_8_p[j] = res[1][j].substring(res[1][j].lastIndexOf("/")+1);
                        y_9_p[j] = res[2][j].substring(res[2][j].lastIndexOf("/")+1);
                        y_7_p[j] = y_7_p[j].substring(y_7_p[j].indexOf("-")+1);
                        y_8_p[j] = y_8_p[j].substring(y_8_p[j].indexOf("-")+1);
                        y_9_p[j] = y_9_p[j].substring(y_9_p[j].indexOf("-")+1);
                        d_7_p[j] = res[3][j];
                        d_8_p[j] = res[4][j];
                        d_9_p[j] = res[5][j];
                    }
                    if (j >= 5) {
                        y_7_n[j - 5] = res[0][j].substring(res[0][j].lastIndexOf("/")+1);
                        y_8_n[j - 5] = res[1][j].substring(res[1][j].lastIndexOf("/")+1);
                        y_9_n[j - 5] = res[2][j].substring(res[2][j].lastIndexOf("/")+1);
                        y_7_n[j - 5] = y_7_n[j - 5].substring(y_7_n[j - 5].indexOf("-")+1);
                        y_8_n[j - 5] = y_8_n[j - 5].substring(y_8_n[j - 5].indexOf("-")+1);
                        y_9_n[j - 5] = y_9_n[j - 5].substring(y_9_n[j - 5].indexOf("-")+1);
                        d_7_n[j - 5] = res[3][j];
                        d_8_n[j - 5] = res[4][j];
                        d_9_n[j - 5] = res[5][j];
                    }
                }
                this.year2017dataP = y_7_p;
                this.year2018dataP = y_8_p;
                this.year2019dataP = y_9_p;
                this.year2017dataN = y_7_n;
                this.year2018dataN = y_8_n;
                this.year2019dataN = y_9_n;
                this.des2017dataP =d_7_p;
                this.des2018dataP =d_8_p;
                this.des2019dataP =d_9_p;
                this.des2017dataN =d_7_n;
                this.des2018dataN =d_8_n;
                this.des2019dataN =d_9_n;
                this.posData=this.year2019dataP;
                this.negData=this.year2019dataN;
                this.posDes=this.des2019dataP;
                this.negDes=this.des2019dataN;
                console.log(this.des2019dataP);

            })
        },
        changeYear:function(){
            self=this;
            var inputYear=posYear.options[posYear.selectedIndex].value;
            console.log(inputYear);
            if(inputYear==2019){
                this.posData=this.year2019dataP;
                this.posDes=this.des2019dataP;
            }
            if(inputYear==2018){
                this.posData=this.year2018dataP;
                this.posDes=this.des2018dataP;
            }
            if(inputYear==2017){
                this.posData=this.year2017dataP;
                this.posDes=this.des2017dataP;
            }
            console.log(this.posData);
        },
        changeYear2:function(){
            self=this;
            var inputYear=negYear.options[negYear.selectedIndex].value;
            console.log(inputYear);
            if(inputYear==2019){
                this.negData=this.year2019dataN;
                this.negDes=this.des2019dataN;
            }
            if(inputYear==2018){
                this.negData=this.year2018dataN;
                this.negDes=this.des2018dataN;
            }
            if(inputYear==2017){
                this.negData=this.year2017dataN;
                this.negDes=this.des2017dataN;
            }
            console.log(this.negData);
        },
        sendurl:function(e){
            var issueweb=this.issueWeb+e;
            issueweb=issueweb.substring(19);
            console.log(issueweb);
            window.location.href="/search/issueComments?issueweb="+issueweb;
        }


    }
});