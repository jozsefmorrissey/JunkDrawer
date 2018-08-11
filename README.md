# JunkDrawer
// Here You can type your custom JavaScript...

function jqTreeObj(node) {
  let id = node.attr('id');
  let obj = $('<div id="' + id + '"></div>');
  obj[0].domElem = node;
  return obj;
}

function traverseDom(node, parent, func) {
  let currParent = parent;
  if (func(node)) {
    currParent = jqTreeObj(node);
    parent.append(currParent);
  }
  node.children().each(function () {
    traverseDom($(this), currParent, func);
    if (func($(this))) {
      currParent.append(jqTreeObj($(this)));
      // parent.append($(this));
    }
  });
}

function isDiv(elem) {
  return elem[0].nodeName === 'DIV';
}

function buildTree(elem, func) {
  let treeObj = jqTreeObj($('<root></root>'));
  traverseDom($(elem), treeObj, isDiv);
  return treeObj;
}
