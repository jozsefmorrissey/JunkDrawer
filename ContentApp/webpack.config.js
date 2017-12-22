const path = require('path');

const config = {
  context: path.join(__dirname, './app'),
  entry: './app',
  output: {
    path: path.join(__dirname, './app'),
    filename: 'index.js'
  },
  resolve: {
    extensions: ['.js', '.ts']
  },
  module: {
    loaders: [
      {
        test: /\.js$/,
        exclude: /(node_modules)/,
        loader: 'babel-loader'
      },
      {
        test: /\.js$/,
        exclude: /(node_modules)/,
        loader: 'eslint-loader'
      }
    ]
  },
  watchOptions: {
    poll: true
  },
};

module.exports = config;
