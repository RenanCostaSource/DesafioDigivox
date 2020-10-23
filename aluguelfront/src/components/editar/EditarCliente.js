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
import EditIcon from '@material-ui/icons/Edit';
import DeleteIcon from '@material-ui/icons/Delete';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';

import { getClientes, deleteCliente, editCliente } from '../../store/fetchActions';


const useStyles = makeStyles((theme) => ({
    root: {
        flexGrow: 1,

    },
    demo: {
        backgroundColor: theme.palette.background.paper,
        width: '100%',
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
    }

}));



export default function EditarCliente() {
    const classes = useStyles();
    const dispatch = useDispatch();
    const clientes = useSelector(state => state.clientes);
    const [open, setOpen] = useState(false);
    const [openEdit, setOpenEdit] = useState(false);

    const [values, setValues] = useState({
        id: '',
        nome: '',
        cpf: ''
    });
    useEffect(() => {
        dispatch(getClientes());
    }, []);

    const handleChange = (prop) => (event) => {
        setValues({ ...values, [prop]: event.target.value });
    };


    const handleClickOpen = (id) => {
        setValues({ ...values, id });
        setOpen(true);
    };

    const handleClose = () => {
        setValues({ ...values, id: '' });
        setOpen(false);
    };

    const handleClickOpenEdit = (cliente) => {
        setValues({ ...values, id: cliente.id, nome: cliente.nome, cpf: cliente.cpf });
        setOpenEdit(true);
    };

    const handleCloseEdit = () => {
        setValues({ ...values, id: '' });
        setOpenEdit(false);
    };



    const deletar = () => {
        dispatch(deleteCliente(values.id));
        setOpen(false);
    }

    const editar = () => {

        dispatch(editCliente(values));
        setOpenEdit(false);
    }


    return (<div className={classes.root}>
        <List className={classes.demo}>
            {clientes.map((cliente) =>
                <ListItem key={cliente.id} className={classes.item}>
                    <ListItemText
                        primary={cliente.nome}
                        secondary={"CPF: " + cliente.cpf}
                    />
                    <ListItemIcon>
                        <IconButton edge="end" aria-label="edit" onClick={() => handleClickOpenEdit(cliente)}>
                            <EditIcon />
                        </IconButton>
                    </ListItemIcon>
                    <ListItemSecondaryAction>
                        <IconButton edge="end" aria-label="delete" onClick={() => handleClickOpen(cliente.id)}>
                            <DeleteIcon />
                        </IconButton>
                    </ListItemSecondaryAction>
                </ListItem>,
            )}
        </List>

        <Dialog
            open={open}

            onClose={handleClose}
            aria-labelledby="alert-dialog-title"
            aria-describedby="alert-dialog-description"
        >
            <DialogTitle id="alert-dialog-title">{"Confirmação de Exclusão."}</DialogTitle>
            <DialogContent>
                <DialogContentText id="alert-dialog-description">
                    Tem certeza que quer excluir esse cliente?<br />
                    <br />(Não pode ser revertido)
          </DialogContentText>

            </DialogContent>
            <DialogActions>
                <Button onClick={handleClose} className={classes.revoke}>
                    Cancelar
          </Button>
                <Button onClick={deletar} className={classes.default} autoFocus>
                    Excluir
          </Button>
            </DialogActions>
        </Dialog>

        <Dialog
            open={openEdit}
            onClose={handleCloseEdit}
            aria-labelledby="alert-dialog-title"
            aria-describedby="alert-dialog-description"
            maxWidth={false}
        >
            <DialogTitle id="alert-dialog-title">{"Editar cadastro de cliente."}</DialogTitle>
            <DialogContent>
                <TextField
                    id="nome"
                    label="Nome do Cliente"
                    placeholder="Nome"
                    onChange={handleChange('nome')}
                    fullWidth
                    margin="normal"
                    InputLabelProps={{
                        shrink: true,
                    }}
                    variant="outlined"
                    value={values.nome}
                />
                <TextField
                    id="CPF"
                    label="CPF do Cliente"
                    style={{ marginTop: 8 }}
                    placeholder="CPF"
                    onChange={handleChange('cpf')}
                    fullWidth
                    margin="normal"
                    InputLabelProps={{
                        shrink: true,
                    }}
                    variant="outlined"
                    value={values.cpf}
                />
            </DialogContent>
            <DialogActions>
                <Button onClick={handleCloseEdit} className={classes.revoke}>
                    Cancelar
          </Button>
                <Button onClick={editar} className={classes.default} autoFocus>
                    Editar
          </Button>
            </DialogActions>
        </Dialog>
    </div>)
}