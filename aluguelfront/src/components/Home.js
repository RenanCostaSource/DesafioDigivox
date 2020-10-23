import React, { useState, useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import PropTypes from 'prop-types';
import { makeStyles } from '@material-ui/core/styles';
import Tabs from '@material-ui/core/Tabs';
import Tab from '@material-ui/core/Tab';
import Snackbar from '@material-ui/core/Snackbar';
import MuiAlert from '@material-ui/lab/Alert';
import Aluguel from './cadastro/Aluguel';
import Reserva from './cadastro/Reserva';
import CriarCliente from './cadastro/CriarCliente';
import EditarCliente from './editar/EditarCliente';
import CriarLivro from './cadastro/CriarLivro';
import EditarLivro from './editar/EditarLivro';
import Dashboard from './dashboard';
import { requestError } from '../store/ducks/requestError';
import LogoIcon from '../assets/logo-header.png';
import '../styles/Home/index.css';
import '../styles/global.css';

function Alert(props) {
    return <MuiAlert elevation={6} variant="filled" {...props} />;
}



function TabPanel(props) {
    const { children, value, index, ...other } = props;

    return (
        <div
            role="tabpanel"
            hidden={value !== index}
            id={`vertical-tabpanel-${index}`}
            aria-labelledby={`vertical-tab-${index}`}
            style={{ width: '100%' }}
            {...other}
        >
            {value === index && (
                <div >{children}</div>
            )}
        </div>
    );
}

TabPanel.propTypes = {
    children: PropTypes.node,
    index: PropTypes.any.isRequired,
    value: PropTypes.any.isRequired,
};
const useStyles = makeStyles((theme) => ({
    root: {
        flexGrow: 1,
        backgroundColor: theme.palette.background.paper,
        display: 'flex',
        height: '600px',
    },
    tabs: {
        borderRight: `1px solid ${theme.palette.divider}`,
        marginRight: '5px',
        height: '100%',
        minWidth: 80,
    },
    tab: {
        width: '100%',

    },
    tab_overflow: {
        overflowY: "scroll"
    }
}));
export default function GeneratorHome() {

    const [value, setValue] = React.useState(0);
    const dispatch = useDispatch();
    const [openMessage, setOpenMessage] = useState(false);
    const [openError, setOpenError] = useState(false);
    const requestErrorMessage = useSelector(state => state.requestError);
    const [errorMessage, setErrorMessage] = useState('');
    const handleChange = (event, newValue) => {
        setValue(newValue);
    };
    const classes = useStyles();
    useEffect(() => {
        if (requestErrorMessage == 200) {

            setOpenMessage(true);
            return;
        }
        if (requestErrorMessage.length !== 0) {
            console.log(requestErrorMessage);
            setErrorMessage(requestErrorMessage);
            setOpenError(true);
        }

    }, [requestErrorMessage]);

    const handleCloseMessage = (event, reason) => {
        if (reason === 'clickaway') {
            return;
        }

        setOpenMessage(false);
        dispatch(requestError(""));
    };
    const handleCloseError = (event, reason) => {
        if (reason === 'clickaway') {
            return;
        }
        setOpenError(false);
        setErrorMessage('');
        dispatch(requestError(""));
    };
    return (
        <div>
            <nav className="navbar">
                <div className="navcontainer">
                    <img src={LogoIcon} alt="logo-icon" style={{ width: '170px', height: '39px' }} />
                    <span className="content__tabs__text">Desafio Digivox - Renan Paiva Oliveira Costa</span>
                </div>

            </nav>
            <div className="containerMessages">
                <div className="content">
                    <div className="content__header">
                        <h3>
                            Sistema de Empréstimo
                    </h3>
                        <hr />
                        <div className={classes.root}>

                            <Tabs textColor='primary' className={classes.tabs} value={value} onChange={handleChange} orientation="vertical" variant="scrollable" >
                                <Tab label={<span className="content__tabs__text">Programação <br /> Semanal</span>} id="simple-tab-0" aria-controls="simple-tabpanel-0" className={classes.tab} />
                                <Tab label={<span className="content__tabs__text">Alugar</span>} id="simple-tab-1" aria-controls="simple-tabpanel-1" />
                                <Tab label={<span className="content__tabs__text">Reservar</span>} id="simple-tab-2" aria-controls="simple-tabpanel-2" />
                                <Tab label={<span className="content__tabs__text">Cadastrar <br /> Cliente</span>} id="simple-tab-3" aria-controls="simple-tabpanel-3" />
                                <Tab label={<span className="content__tabs__text">Ações para <br /> Cliente</span>} id="simple-tab-4" aria-controls="simple-tabpanel-4" />
                                <Tab label={<span className="content__tabs__text">Cadastrar <br /> Livro</span>} id="simple-tab-5" aria-controls="simple-tabpanel-5" />
                                <Tab label={<span className="content__tabs__text">Ações para <br /> Livro</span>} id="simple-tab-6" aria-controls="simple-tabpanel-6" />

                            </Tabs>

                            <TabPanel value={value} index={0}>
                                <Dashboard />
                            </TabPanel>
                            <TabPanel value={value} index={1}>
                                <Aluguel />
                            </TabPanel>
                            <TabPanel value={value} index={2}>
                                <Reserva />
                            </TabPanel>
                            <TabPanel value={value} index={3}>
                                <CriarCliente />
                            </TabPanel>
                            <TabPanel value={value} index={4} className={classes.tab_overflow}>
                                <EditarCliente />
                            </TabPanel>
                            <TabPanel value={value} index={5}>
                                <CriarLivro />
                            </TabPanel>
                            <TabPanel value={value} index={6} className={classes.tab_overflow}>
                                <EditarLivro />
                            </TabPanel>

                        </div>





                    </div>
                </div>
            </div>
            <Snackbar open={openMessage} autoHideDuration={3000} onClose={handleCloseMessage} anchorOrigin={{ vertical: 'top', horizontal: 'center' }}>
                <Alert onClose={handleCloseMessage} severity="success">
                    Ação executada com sucesso!
        </Alert>
            </Snackbar>
            <Snackbar open={openError} autoHideDuration={3000} onClose={handleCloseError} anchorOrigin={{ vertical: 'top', horizontal: 'center' }}>
                <Alert onClose={handleCloseError} severity="error">
                    {errorMessage}
                </Alert>
            </Snackbar>
        </div>
    );
}