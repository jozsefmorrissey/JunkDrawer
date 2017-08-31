app.filter('reverseUp', function() {
    return function(input, uppercase) {
      input = input || '';
      var out = '';
      for (var i = 0; i < input.length; i++) {
        out = input.charAt(i) + out;
      }
      // conditional based on optional argument
      if (uppercase) {
        out = out.toUpperCase();
      }
      return out;
    };
  });

  app.filter('sortAlpha', function(){
    return function (input) {
      for(let i = 1; i < input.length - 1; i++){
        if(input[i] < input[i -1]){
          let temp = input[i];
          input[i] = input[i-1];
          input[i-1] = temp;
          i -= 2;
        }
        else if(input[i] > input[i+1]) {
          let temp = input[i];
          input[i] = input[i+1];
          input[i+1] = temp;
          i -= 1;
        }
      }
      return input;
    }
  });
