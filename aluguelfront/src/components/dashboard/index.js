import React from 'react';
import { useDispatch, useSelector } from 'react-redux';
import PropTypes from 'prop-types';
import { makeStyles } from '@material-ui/core/styles';
import { TextField } from '@material-ui/core';
import AppBar from '@material-ui/core/AppBar';
import Tabs from '@material-ui/core/Tabs';
import Tab from '@material-ui/core/Tab';
import Typography from '@material-ui/core/Typography';
import Box from '@material-ui/core/Box';

import Grafico from './Grafico';
import Reservas from './Reservas';
import Alugueis from './Alugueis';
import { getSemana } from '../../store/fetchActions';
import '../../styles/Home/index.css';
function TabPanel(props) {
  const { children, value, index, ...other } = props;

  return (
    <div
      role="tabpanel"
      hidden={value !== index}
      id={`simple-tabpanel-${index}`}
      aria-labelledby={`simple-tab-${index}`}
      {...other}
    >
      {value === index && (
        <Box p={3}>
          <Typography>{children}</Typography>
        </Box>
      )}
    </div>
  );
}

TabPanel.propTypes = {
  children: PropTypes.node,
  index: PropTypes.any.isRequired,
  value: PropTypes.any.isRequired,
};

function indice(index) {
  return {
    id: `simple-tab-${index}`,
    'aria-controls': `simple-tabpanel-${index}`,
  };
}

const useStyles = makeStyles((theme) => ({
  root: {
    flexGrow: 1,
    backgroundColor: theme.palette.background.paper,
  },
  tab_overflow: {
    height: 450,
    overflowY: "scroll"
  }
}));

export default function Dashboard() {
  const dispatch = useDispatch();
  const classes = useStyles();
  const [value, setValue] = React.useState(0);
  const [selectedDate, setSelectedDate] = React.useState(new Date(Date.now()).toISOString().split('T')[0]);
  const requestErrorMessage = useSelector(state => state.requestError);
  const handleDateChange = (date) => {
    setSelectedDate(date);
  };

  React.useEffect(() => {
    dispatch(getSemana(selectedDate));
  }, [selectedDate, requestErrorMessage]);


  const handleChange = (event, newValue) => {
    setValue(newValue);
  };

  return (
    <div className={classes.root}>
      <AppBar position="static">
        <Tabs value={value} onChange={handleChange} aria-label="graph tabs" variant="scrollable">
          <Tab label={<span className="content__tabs__text">Retiradas e devoluções</span>} {...indice(0)} />
          <Tab label={<span className="content__tabs__text">Alugueis</span>} {...indice(1)} />
          <Tab label={<span className="content__tabs__text">Reservas</span>} {...indice(2)} />
        </Tabs>
      </AppBar>
      <TextField
        id="date"
        label="Semana"
        type="date"
        value={selectedDate}
        onChange={(event) => {
          handleDateChange(event.target.value);
        }}
        className={classes.textField}
        InputLabelProps={{
          shrink: true,
        }}
        style={{ marginTop: 10, marginLeft: 10 }}
      />
      <TabPanel value={value} index={0}>
        <Grafico />
      </TabPanel>
      <TabPanel value={value} index={1} className={classes.tab_overflow}>
        <Alugueis />
      </TabPanel>
      <TabPanel value={value} index={2} className={classes.tab_overflow}>
        <Reservas />
      </TabPanel>
    </div>
  );
}