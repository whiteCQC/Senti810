$(document).ready(function() {
    new Vue({
        el:"#Senti",
        data:{
            toSearch:""
        },
        methods:{
            searchProject:function () {
                self=this;
                var search=this.toSearch.split("/");

                if(search.length<2){
                    alert("Please enter the correct GitHub website!");
                }
                else{
                    var projectInfo=[search[search.length-2],search[search.length-1]];
                    this.$http.get("http://localhost:8080/git/search/"+projectInfo).then(function (response) {
                        var res=response.bodyText;
                        if(res=="not Exist"){
                            alert("project not exist or private");
                        }else if(res=="success"){
                            window.location.href="/view/showCommit.html"
                        }
                    })
                }

            },

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
            }
        }
    })


})