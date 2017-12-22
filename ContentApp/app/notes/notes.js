const notesCtrl = (textService) => {
  function getDisplay(noteObj) {
    if (noteObj.type === 'TEXT') {
      const ret = textService(noteObj).getDisplay;
      return ret;
    }
    return '<p>hello directive</p>';
  }

  const obj = {};

  obj.notes = [];
  const textTest = {
    type: 'TEXT',
    header: 'Main Topic',
    message: 'main body(hello world)',
  };
  const notTextTest = {
    type: 'Not',
    garb: 'garbage',
  };
  obj.msg = 'hello world';
  obj.notes.push(textTest);
  obj.notes.push(notTextTest);

  obj.getDisplay = getDisplay;

  return obj;
};

export default notesCtrl;
