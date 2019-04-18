$(document).ready(function() {
    new Vue({
        el:"#toSearch",
        data:{
            errorMsg:"error!",
            search:{
                owner:'',
                repo:''
            }
        },
        methods:{
            searchProject:function () {
                self=this;
                var search=this.search;

                if(search.owner==""){
                    alert("Please enter the owner!");
                }
                else if (search.repo==""){
                    alert("Please enter the project!");
                }
                else{
                    var projectInfo=[search.owner,search.repo];
                    this.$http.get("http://localhost:8080/git/search/"+projectInfo).then(function (response) {
                        var res=response.bodyText;
                        if(res=="not Exist"){
                            alert("project not exist or private");
                        }else if(res=="success"){
                            window.location.href="/view/show.html"
                        }
                    })
                }

            }
        }
    })


})