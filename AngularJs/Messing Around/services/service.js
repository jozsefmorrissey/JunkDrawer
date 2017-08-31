app.service('changeGreetingService', [function() {
  let greetings = ['Welcome', 'Salutations', 'Hello'];
  let count = 0;

  this.modify = function(){
    console.log("in the service: ");
    count++;
    if(count > 2){
      count = 0;
      return greetings[Math.floor(Math.random()*greetings.length)];
    }
    return null;
  }
}]);

app.service('UtilsService',function(){

    this.myFunction = function(){ console.log ('you called myFunction()')};

});
