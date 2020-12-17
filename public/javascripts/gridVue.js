$(document).ready(function(){
    // noinspection JSUnusedLocalSymbols
    let app = new Vue({
            el: '#app',
            data: {
                name: '',
                welcome: 'Wilkommen, bitte gib deinen Namen ein: '
            },
            methods:{
                submit:function(){
                    $("#eingabe").hide();
                    this.welcome= 'Wilkommen ' + this.name;
                }
            }

        }
    )
})