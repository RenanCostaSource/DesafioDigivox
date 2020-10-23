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

import { getLivros, deleteLivro, editLivro } from '../../store/fetchActions';





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

export default function EditarLivro() {
    const classes = useStyles();
    const dispatch = useDispatch();
    const livros = useSelector(state => state.livros);
    const [open, setOpen] = useState(false);
    const [openEdit, setOpenEdit] = useState(false);
    const [errors, setErrors] = useState({
        nome: false,

    });

    const [values, setValues] = useState({
        id: '',
        nome: '',
    });
    useEffect(() => {
        dispatch(getLivros());
    }, []);

    const handleChange = (prop) => (event) => {
        setValues({ ...values, [prop]: event.target.value });
    };

    const sendRequest = () => {
        setValues({
            id: '',
            nome: '',
        })
    }


    const handleClickOpen = (id) => {
        setValues({ ...values, id });
        setOpen(true);
    };

    const handleClose = () => {
        setValues({ ...values, id: '' });
        setOpen(false);
    };

    const handleClickOpenEdit = (livro) => {
        setValues({ ...values, id: livro.id, nome: livro.nome });
        setOpenEdit(true);
    };

    const handleCloseEdit = () => {
        setValues({ ...values, id: '' });
        setOpenEdit(false);
    };



    const deletar = () => {
        dispatch(deleteLivro(values.id));
        setOpen(false);
    }

    const editar = () => {

        dispatch(editLivro(values));
        setOpenEdit(false);
    }

    return (<div className={classes.root}>
        <List className={classes.demo}>
            {livros.map((livro) =>
                <ListItem key={livro.id} className={classes.item}>
                    <ListItemText
                        primary={livro.nome}

                    />
                    <ListItemIcon>
                        <IconButton edge="end" aria-label="edit" onClick={() => handleClickOpenEdit(livro)}>
                            <EditIcon />
                        </IconButton>
                    </ListItemIcon>
                    <ListItemSecondaryAction>
                        <IconButton edge="end" aria-label="delete" onClick={() => handleClickOpen(livro.id)}>
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
            <DialogTitle id="alert-dialog-title">{"Editar cadastro de Livro."}</DialogTitle>
            <DialogContent>
                <TextField
                    id="nome"
                    label="Nome do Livro"

                    placeholder="Nome"
                    onChange={handleChange('nome')}
                    fullWidth
                    margin="normal"
                    InputLabelProps={{
                        shrink: true,
                    }}
                    variant="outlined"
                    value={values.nome}
                    error={errors.nome}
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