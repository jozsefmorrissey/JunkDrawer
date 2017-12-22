const displayTextDirective = () => {
  const directive = {
    scope: {
      header: '=header',
      message: '=message',
    },
    templateUrl: 'text/display/display-text.html',
  };

  return directive;
};

export default displayTextDirective;
