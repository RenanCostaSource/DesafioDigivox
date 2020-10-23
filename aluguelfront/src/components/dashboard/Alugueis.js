import React, { useState, useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { makeStyles } from '@material-ui/core/styles';
import { Button } from '@material-ui/core';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemSecondaryAction from '@material-ui/core/ListItemSecondaryAction';
import ListItemText from '@material-ui/core/ListItemText';
import IconButton from '@material-ui/core/IconButton';
import EventAvailableIcon from '@material-ui/icons/EventAvailable';
import CheckIcon from '@material-ui/icons/Check';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';

import { devolverAluguel } from '../../store/fetchActions';


const useStyles = makeStyles((theme) => ({
    root: {
        flexGrow: 1,

    },
    demo: {
        backgroundColor: theme.palette.background.paper,
        width: '100%',
        height: '45%'
    },
    title: {
        margin: theme.spacing(4, 0, 2),
    },
    item: {
        borderBottom: `1px solid ${theme.palette.divider}`,
    },
    revoke: {
        background: 'red',
        color: 'white',
    },
    default: {
        background: 'linear-gradient(45deg, #4A5A9F 30%, #6A7ABF 90%)',
        color: 'white',
        float: 'right'
    },
    subtitle: {
        fontWeight: 'bold'
    }

}));



export default function Reservas() {
    const classes = useStyles();
    const dispatch = useDispatch();
    const alugueisentrega = useSelector(state => state.alugueisentrega);
    const alugueisretirada = useSelector(state => state.alugueisretirada);
    const [open, setOpen] = useState(false);


    const [id, setId] = useState('');
    useEffect(() => {

    }, []);




    const handleClickOpen = (id) => {
        setId(id);
        setOpen(true);
    };

    const handleClose = () => {
        setId('');
        setOpen(false);
    };






    const deletar = () => {
        dispatch(devolverAluguel(id));
        setOpen(false);
    }




    return (<div className={classes.root}>
        <List className={classes.demo}>
            <span className={classes.subtitle}>Retiradas:</span>
            {alugueisretirada.length > 0 ? alugueisretirada.map((aluguel) =>
                <ListItem key={aluguel.id} className={classes.item}>
                    <ListItemText
                        primary={`Cliente: ${aluguel.cliente.nome} CPF:${aluguel.cliente.cpf} Livro: ${aluguel.livro.nome}`}
                        secondary={`Data de Retirada: ${aluguel.data_inicial} | 
                        Data Marcada para Devolução: ${aluguel.data_final} 
                        ${aluguel.devolvido == null ? '' : `| Data da Devolução ${aluguel.devolvido}`}`}
                    />
                    <ListItemSecondaryAction>
                        {aluguel.devolvido == null ? <IconButton edge="end" aria-label="cancelar" onClick={() => handleClickOpen(aluguel.id)}>
                            <EventAvailableIcon />
                        </IconButton> : <CheckIcon color="primary" />}
                    </ListItemSecondaryAction>
                </ListItem>,
            ) : <span><br />Nenhuma nesta semana</span>}
        </List>
        <span className={classes.subtitle}>Devoluções:</span>
        <List className={classes.demo}>
            {alugueisentrega.length > 0 ? alugueisentrega.map((aluguel) =>
                <ListItem key={aluguel.id} className={classes.item}>
                    <ListItemText
                        primary={`Cliente: ${aluguel.cliente.nome} CPF:${aluguel.cliente.cpf} Livro: ${aluguel.livro.nome}`}
                        secondary={`Data de Retirada: ${aluguel.data_inicial} | 
                        Data Marcada para Devolução: ${aluguel.data_final} 
                        ${aluguel.devolvido == null ? '' : `| Data da Devolução ${aluguel.devolvido}`}`}
                    />
                    <ListItemSecondaryAction>
                        {aluguel.devolvido == null ? <IconButton edge="end" aria-label="cancelar" onClick={() => handleClickOpen(aluguel.id)}>
                            <EventAvailableIcon />
                        </IconButton> : <CheckIcon color="primary" />}
                    </ListItemSecondaryAction>
                </ListItem>,
            ) : <span>Nenhuma nesta semana</span>}
        </List>

        <Dialog
            open={open}

            onClose={handleClose}
            aria-labelledby="alert-dialog-title"
            aria-describedby="alert-dialog-description"
        >
            <DialogTitle id="alert-dialog-title">{"Confirmação de Devolução."}</DialogTitle>
            <DialogContent>
                <DialogContentText id="alert-dialog-description">
                    Confirma que o livro foi devolvido?
          </DialogContentText>

            </DialogContent>
            <DialogActions>
                <Button onClick={handleClose} className={classes.revoke}>
                    Voltar
          </Button>
                <Button onClick={deletar} className={classes.default} autoFocus>
                    Confirmar
          </Button>
            </DialogActions>
        </Dialog>


    </div>)
}