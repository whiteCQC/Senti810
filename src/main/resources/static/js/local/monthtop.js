new Vue({
    el: "#Senti",
    data: {
        month2019dataP:[],
        month2018dataP:[],
        month2017dataP:[],
        month2019dataN:[],
        month2018dataN:[],
        month2017dataN:[],
        des2019dataP:[],
        des2018dataP:[],
        des2017dataP:[],
        des2019dataN:[],
        des2018dataN:[],
        des2017dataN:[],
        posData:[],
        negData:[],
        posDes:[],
        negDes:[],
        issueWeb:"",
        nowYear1:"",
        nwoYear2:""
    },
    mounted:function(){
        this.monthtop();

    },
    methods:{
        monthtop:function(){
            self = this;
            this.$http.get("http://localhost:8080/search/details_month").then(function (response) {
                var res = response.data;
                var m_7_p=[];
                var m_8_p=[];
                var m_9_p=[];
                var m_7_n=[];
                var m_8_n=[];
                var m_9_n=[];
                var d_7_p = [];
                var d_8_p = [];
                var d_9_p = [];
                var d_7_n = [];
                var d_8_n = [];
                var d_9_n = [];
                console.log(res[2][0]+"ok");
                var index=res[0][0].lastIndexOf("/");
                var issueWeb=res[0][0].substring(0,index+1);
                this.issueWeb=issueWeb;
                for (var j = 0; j < 24; j++) {
                    if(j<12){
                        m_7_p[j]=res[0][j].substring(res[0][j].lastIndexOf("/")+1);
                        m_8_p[j]=res[1][j].substring(res[1][j].lastIndexOf("/")+1);
                        m_9_p[j]=res[2][j].substring(res[2][j].lastIndexOf("/")+1);
                        m_7_p[j]=m_7_p[j].substring(m_7_p[j].indexOf("-")+1);
                        m_8_p[j]=m_8_p[j].substring(m_8_p[j].indexOf("-")+1);
                        m_9_p[j]=m_9_p[j].substring(m_9_p[j].indexOf("-")+1);
                        d_7_p[j] = res[3][j];
                        d_8_p[j] = res[4][j];
                        d_9_p[j] = res[5][j];
                    }
                    if(j>=12){
                        m_7_n[j-12]=res[0][j].substring(res[0][j].lastIndexOf("/")+1);
                        m_8_n[j-12]=res[1][j].substring(res[1][j].lastIndexOf("/")+1);
                        m_9_n[j-12]=res[2][j].substring(res[2][j].lastIndexOf("/")+1);
                        m_7_n[j-12]=m_7_n[j-12].substring(m_7_n[j-12].indexOf("-")+1);
                        m_8_n[j-12]=m_8_n[j-12].substring(m_8_n[j-12].indexOf("-")+1);
                        m_9_n[j-12]=m_9_n[j-12].substring(m_9_n[j-12].indexOf("-")+1);
                        d_7_n[j-12] = res[3][j];
                        d_8_n[j-12] = res[4][j];
                        d_9_n[j-12] = res[5][j];
                    }
                }
                this.month2017dataP=m_7_p;
                this.month2018dataP=m_8_p;
                this.month2019dataP=m_9_p;
                this.month2017dataN=m_7_n;
                this.month2018dataN=m_8_n;
                this.month2019dataN=m_9_n;
                this.des2017dataP =d_7_p;
                this.des2018dataP =d_8_p;
                this.des2019dataP =d_9_p;
                this.des2017dataN =d_7_n;
                this.des2018dataN =d_8_n;
                this.des2019dataN =d_9_n;
                console.log(this.month2019dataP);
                this.posData=this.month2019dataP;
                this.negData=this.month2019dataN;
                this.posDes=this.des2019dataP;
                this.negDes=this.des2019dataN;
                this.nowYear1="2019";
                this.nowYear2="2019";

            })
        },
        changeYear:function(){
            self=this;
            var inputYear=posYear.options[posYear.selectedIndex].value;
            this.nowYear1=inputYear;
            console.log(inputYear);
            if(inputYear==2019){
                this.posData=this.month2019dataP;
                this.posDes=this.des2019dataP;
            }
            if(inputYear==2018){
                this.posData=this.month2018dataP;
                this.posDes=this.des2018dataP;
            }
            if(inputYear==2017){
                this.posData=this.month2017dataP;
                this.posDes=this.des2017dataP;
            }
            console.log(this.posData);
        },
        changeYear2:function(){
            self=this;
            var inputYear=negYear.options[negYear.selectedIndex].value;
            this.nowYear2=inputYear;
            console.log(inputYear);
            if(inputYear==2019){
                this.negData=this.month2019dataN;
                this.negDes=this.des2019dataN;
            }
            if(inputYear==2018){
                this.negData=this.month2018dataN;
                this.negDes=this.des2018dataN;
            }
            if(inputYear==2017){
                this.negData=this.month2017dataN;
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