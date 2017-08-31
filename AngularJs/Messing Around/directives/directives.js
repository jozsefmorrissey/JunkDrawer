
app.directive('inlineTemplate', function() {
  return {
    scope: {
      customerInfo: '=info'
    },
    template: 'Name: {{customerInfo.name}} Address: {{customerInfo.address}}'
  };
});

app.directive('fileTemplate', function() {
  return {
    scope: {
      customerInfo: '=info'
    },
    templateUrl: 'html/my-customer.html'
  };
});
