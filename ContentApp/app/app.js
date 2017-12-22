import angular from 'angular';
import angularCookies from 'angular-cookies';

import notesCtrl from './notes/notes';
import displayTextDirective from './text/display/display-text';

import textService from './text/textService';

const routerApp = angular.module('routerApp', [angularCookies]);

routerApp.service('textService', function textSrvc() {
  return textService;
});

routerApp.controller('notesCtrl', notesCtrl);

routerApp.directive('displayTextDirective', displayTextDirective);

routerApp.directive('inlineTemplate', function inlineTemplate() {
  return {
    scope: {
      customerInfo: '=info',
    },
    template: 'Name: bill Address: jerry',
  };
});
