import React, { useState, useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { makeStyles } from '@material-ui/core/styles';
import { TextField, Button } from '@material-ui/core';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemSecondaryAction from '@material-ui/core/ListItemSecondaryAction';
import ListItemText from '@material-ui/core/ListItemText';
import IconButton from '@material-ui/core/IconButton';
import EventBusyIcon from '@material-ui/icons/EventBusy';
import DeleteIcon from '@material-ui/icons/Delete';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';

import { deleteReserva } from '../../store/fetchActions';


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
    const reservasentrega = useSelector(state => state.reservasentrega);
    const reservasretirada = useSelector(state => state.reservasretirada);
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
        dispatch(deleteReserva(id));
        setOpen(false);
    }




    return (<div className={classes.root}>
        <List className={classes.demo}>
            <span className={classes.subtitle}>Retiradas:</span>
            {reservasretirada.length > 0 ? reservasretirada.map((reserva) =>
                <ListItem key={reserva.id} className={classes.item}>
                    <ListItemText
                        primary={`Cliente: ${reserva.cliente.nome} CPF:${reserva.cliente.cpf} Livro: ${reserva.livro.nome}`}
                        secondary={`Data de Retirada: ${new Intl.DateTimeFormat('en-GB').format(Date.parse(reserva.data_inicial))} Data de Devolução: ${new Intl.DateTimeFormat('en-GB').format(Date.parse(reserva.data_final))}`}
                    />
                    <ListItemSecondaryAction>
                        <IconButton edge="end" aria-label="cancelar" onClick={() => handleClickOpen(reserva.id)}>
                            <EventBusyIcon />
                        </IconButton>
                    </ListItemSecondaryAction>
                </ListItem>,
            ) : <span><br />Nenhuma nesta semana</span>}
        </List>
        <span className={classes.subtitle}>Devoluções:</span>
        <List className={classes.demo}>
            {reservasentrega.length > 0 ? reservasentrega.map((reserva) =>
                <ListItem key={reserva.id} className={classes.item}>
                    <ListItemText
                        primary={`Cliente: ${reserva.cliente.nome} CPF:${reserva.cliente.cpf} Livro: ${reserva.livro.nome}`}
                        secondary={`Data de Retirada: ${new Intl.DateTimeFormat('en-GB').format(Date.parse(reserva.data_inicial))} Data de Devolução: ${new Intl.DateTimeFormat('en-GB').format(Date.parse(reserva.data_final))}`}
                    />
                    <ListItemSecondaryAction>
                        <IconButton edge="end" aria-label="cancelar" onClick={() => handleClickOpen(reserva.id)}>
                            <EventBusyIcon />
                        </IconButton>
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
            <DialogTitle id="alert-dialog-title">{"Confirmação de Cancelamento."}</DialogTitle>
            <DialogContent>
                <DialogContentText id="alert-dialog-description">
                    Tem certeza que quer cancelar essa reserva?<br />
                    <br />(Não pode ser revertido)
          </DialogContentText>

            </DialogContent>
            <DialogActions>
                <Button onClick={handleClose} className={classes.revoke}>
                    Sair
          </Button>
                <Button onClick={deletar} className={classes.default} autoFocus>
                    Cancelar
          </Button>
            </DialogActions>
        </Dialog>


    </div>)
}