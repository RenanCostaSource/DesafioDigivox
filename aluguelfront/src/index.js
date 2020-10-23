import React from 'react';
import ReactDOM from 'react-dom';
import { Provider } from 'react-redux';
import store from './store';
import Home from './components/Home';
import { MuiThemeProvider, createMuiTheme, responsiveFontSizes } from '@material-ui/core';

let theme = createMuiTheme({
  palette: {
    primary: { main: '#4A5A9F' },
    secondary: { main: '#D9E02C' }
  }
});
theme = responsiveFontSizes(theme);


ReactDOM.render(
  <Provider store={store} >
    <React.StrictMode>
      <MuiThemeProvider theme={theme}>
        <Home />
      </ MuiThemeProvider>
    </React.StrictMode>
  </Provider>
  , document.getElementById('root')
);

