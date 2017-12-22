const textService = (textData) => {
  const data = textData;
  const serviceObj = {};
  function getHeader() { return textData.header; }
  function getMessage() { return textData.message; }
  function setMessage(text) { data.message = text; }
  function setHeader(header) { data.header = header; }

  serviceObj.getDisplay = `<div display-text-directive header="new" message='"${getMessage()}"'></div>`;
  serviceObj.getEdit = `<editTextDirective setMessage='${setMessage}' setHeader='${setHeader}' />`;
  serviceObj.header = getHeader();
  serviceObj.getMessage = getMessage();
  return serviceObj;
};

export default textService;
