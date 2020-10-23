import React, { useState, useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { TextField, Button } from '@material-ui/core';
import { makeStyles } from '@material-ui/core/styles';
import Autocomplete from '@material-ui/lab/Autocomplete';

import { getClientes, getLivros, saveReserva } from '../../store/fetchActions';
import { requestError } from '../../store/ducks/requestError';

const useStyles = makeStyles({
    defaultOutlined: {
        color: '#33C2C2',
        border: '1px solid #33C2C2',
    },
    inputAutoComplete: {
        width: '100%',
        margin: 8
    },
    default: {
        background: 'linear-gradient(45deg, #4A5A9F 30%, #6A7ABF 90%)',
        color: 'white',
        float: 'right'
    },
    textField: {
        margin: 8,
    }
});

export default function Reserva() {

    const dispatch = useDispatch();
    const classes = useStyles();
    const [inputLivro, setInputLivro] = React.useState('');
    const [inputCliente, setInputCliente] = React.useState('');
    const clientes = useSelector(state => state.clientes);
    const livros = useSelector(state => state.livros);


    const [values, setValues] = React.useState({
        cliente: '',
        livro: '',
        dataInicial: '',
        dataFinal: ''
    });
    useEffect(() => {
        dispatch(getClientes());
        dispatch(getLivros());
    }, []);
    const handleChange = (prop, value) => {
        setValues({ ...values, [prop]: value });
    };

    const sendRequest = () => {
        var data = { ...values, clienteId: values.cliente.id, livroId: values.livro.id }
        dispatch(saveReserva(data));
        setValues({
            ...values,
            cliente: '',
            livro: ''
        })
    }
    const checkRequest = () => {
        var houveErros = false;
        //verifica cada campo
        if (values.cliente.length === 0) {
            houveErros = true;
        }
        if (values.livro.length === 0) {
            houveErros = true;
        }
        if (values.dataInicial.length === 0) {
            houveErros = true;
        }
        if (values.dataFinal.length === 0) {
            houveErros = true;
        }
        if (houveErros) {
            dispatch(requestError('Preencha todas as informações!'));

        } else {
            sendRequest()
        }
    }


    return (

        <li style={{ marginTop: '20px' }}>
            <div>
                <Autocomplete
                    value={values.livro}
                    onChange={(event, newValue) => {
                        handleChange('livro', newValue);
                    }}
                    getOptionLabel={(option) => option.nome}
                    inputValue={inputLivro}
                    onInputChange={(event, newInputValue) => {
                        setInputLivro(newInputValue);
                    }}
                    id="livro"
                    options={livros}
                    className={classes.inputAutoComplete}
                    renderInput={(params) => <TextField {...params} label="Livro" variant="outlined" />}
                />
                <Autocomplete
                    value={values.cliente}
                    onChange={(event, newValue) => {
                        handleChange('cliente', newValue);
                    }}
                    getOptionLabel={(option) => option == '' ? '' : option.nome + " CPF:" + option.cpf}
                    inputValue={inputCliente}
                    onInputChange={(event, newInputValue) => {
                        setInputCliente(newInputValue);
                    }}
                    id="cliente"
                    options={clientes}
                    className={classes.inputAutoComplete}
                    renderInput={(params) => <TextField {...params} label="Cliente" variant="outlined" />}
                />
            </div>
            <div>
                <TextField
                    id="date"
                    label="Data de Retirada"
                    type="date"
                    onChange={(event) => {
                        handleChange('dataInicial', event.target.value);
                    }}
                    className={classes.textField}
                    InputLabelProps={{
                        shrink: true,
                    }}
                />
                <TextField
                    id="date"
                    label="Data de Devolução"
                    type="date"
                    onChange={(event) => {
                        handleChange('dataFinal', event.target.value);
                    }}
                    className={classes.textField}
                    InputLabelProps={{
                        shrink: true,
                    }}
                />
            </div>

            <div className="cont-button-h mt-5"  >
                <Button
                    variant="contained"
                    className={classes.default}
                    onClick={checkRequest}
                >
                    Enviar
                        </Button>
            </div>
        </li>

    )
}