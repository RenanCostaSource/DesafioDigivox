import React, { useState, useEffect } from 'react';
import { useDispatch } from 'react-redux';
import { TextField, Button } from '@material-ui/core';
import { makeStyles } from '@material-ui/core/styles';
import { saveCliente } from '../../store/fetchActions';
import { requestError } from '../../store/ducks/requestError';




const useStyles = makeStyles({
  defaultOutlined: {
    color: '#33C2C2',
    border: '1px solid #33C2C2',
  },
  default: {
    background: 'linear-gradient(45deg, #4A5A9F 30%, #6A7ABF 90%)',
    color: 'white',
    float: 'right'
  }
});
export default function CriaCliente() {

  const dispatch = useDispatch();
  const classes = useStyles();
  const [errorRender, setErrorRender] = useState(false);
  const [errors, setErrors] = useState({
    nome: false,
    cpf: false

  });

  const [values, setValues] = React.useState({
    nome: '',
    cpf: ''
  });

  useEffect(() => {
    setValues(values);
  }, [errors]);
  const handleChange = (prop) => (event) => {
    setValues({ ...values, [prop]: event.target.value });
  };

  const sendRequest = () => {
    dispatch(saveCliente(values));
    setValues({
      nome: '',
      cpf: '',
    })
  }
  const checkRequest = () => {
    var houveErros = false;
    var temperrors = errors;
    console.log(errors);
    //verifica cada campo
    if (values.nome.length === 0) {
      houveErros = true;
      temperrors.nome = true;
    } else {
      temperrors.nome = false;
    }
    if (values.cpf.length === 0) {
      houveErros = true;
      temperrors.cpf = true;
    } else {
      temperrors.cpf = false;
    }
    setErrors(temperrors);
    //força re-render do atributo de erro nos campos errados
    setErrorRender(!errorRender);
    if (houveErros) {
      dispatch(requestError('Preencha todas as informações!'));
    } else {
      sendRequest()
    }
  }

  return (

    <li style={{ marginTop: '20px' }}>
      <div>
        <TextField
          id="nome"
          label="Nome do Cliente"
          style={{ margin: 8 }}
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
      </div>
      <div>
        <TextField
          id="CPF"
          label="CPF do Cliente"
          style={{ margin: 8, width: "30%", minWidth: 200 }}
          placeholder="CPF"
          onChange={handleChange('cpf')}
          fullWidth
          margin="normal"
          InputLabelProps={{
            shrink: true,
          }}
          variant="outlined"
          value={values.cpf}
          error={errors.cpf}
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