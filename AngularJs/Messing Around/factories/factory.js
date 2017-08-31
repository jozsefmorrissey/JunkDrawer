app.factory('helloFac', function() {
    return {
        sayHello: function(text){
            return 'Hello ' + text;
        }
    }
});
