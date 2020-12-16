$(document).ready(function(){
    let app = new Vue({
            el: '#app',
            data: {
                title: '',
                name: '',
            },
            methods:{
                submit:function(){
                    $("#eingabe").hide();
                }
            }
        }
    )
})