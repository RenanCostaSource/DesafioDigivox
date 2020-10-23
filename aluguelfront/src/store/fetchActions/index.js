import api from '../../services/api';
import { getalugueisentrega } from '../ducks/alugueisentrega';
import { getalugueisretirada } from '../ducks/alugueisretirada';
import { getclientes } from '../ducks/clientes';
import { getlivros } from '../ducks/livros';
import { getreservasretirada } from '../ducks/reservasretirada';
import { getreservasentrega } from '../ducks/reservasentrega';
import { requestError } from '../ducks/requestError'


export const getClientes = () => {
    return (dispatch) => {

        api.get("/cliente")
            .then((res) => {

                dispatch(getclientes(res.data));
            })
            .catch((error) => {
                console.log(error);
                dispatch(requestError(error));
                alert(error);


            });
    }
}

export const saveCliente = (data) => {
    return (dispatch) => {

        api.post("/cliente", data)
            .then((res) => {
                dispatch(requestError(200));
                dispatch(getClientes());
            })
            .catch((error) => {
                console.log(error);
                dispatch(requestError(error.response.data));



            });
    }
}

export const editCliente = (data) => {
    return (dispatch) => {

        api.put("/cliente/", data)
            .then((res) => {
                dispatch(requestError(200));
                dispatch(getClientes());
            })
            .catch((error) => {
                console.log(error.response.data);
                dispatch(requestError(error.response.data));



            });
    }
}

export const deleteCliente = (id) => {
    return (dispatch) => {

        api.delete("/cliente/" + id)
            .then((res) => {
                dispatch(requestError(200));
                dispatch(getClientes());
            })
            .catch((error) => {
                console.log(error.response.data);
                dispatch(requestError(error.response.data));



            });
    }
}

export const getLivros = () => {
    return (dispatch) => {

        api.get("/livro")
            .then((res) => {

                dispatch(getlivros(res.data));
            })
            .catch((error) => {
                console.log(error);
                dispatch(requestError(error));
                alert(error);

            });
    }
}

export const saveLivro = (data) => {
    return (dispatch) => {

        api.post("/livro", data)
            .then((res) => {
                dispatch(requestError(200));
                dispatch(getLivros());
            })
            .catch((error) => {
                console.log(error);
                dispatch(requestError(error.response.data));



            });
    }
}

export const editLivro = (data) => {
    return (dispatch) => {

        api.put("/livro/", data)
            .then((res) => {
                dispatch(requestError(200));
                dispatch(getLivros());
            })
            .catch((error) => {
                console.log(error.response.data);
                dispatch(requestError(error.response.data));



            });
    }
}

export const deleteLivro = (id) => {
    return (dispatch) => {

        api.delete("/livro/" + id)
            .then((res) => {
                dispatch(requestError(200));
                dispatch(getLivros());
            })
            .catch((error) => {
                console.log(error.response.data);
                dispatch(requestError(error.response.data));



            });
    }
}

export const saveAluguel = (data) => {
    return (dispatch) => {

        api.post("/aluguel", data)
            .then((res) => {
                dispatch(requestError(200));
            })
            .catch((error) => {
                console.log(error);
                dispatch(requestError(error.response.data));



            });
    }
}

export const saveReserva = (data) => {
    return (dispatch) => {

        api.post("/reserva", data)
            .then((res) => {
                dispatch(requestError(200));
            })
            .catch((error) => {
                console.log(error);
                dispatch(requestError(error.response.data));



            });
    }
}

export const getSemana = (semana) => {
    return (dispatch) => {
        api.get(`/reserva/rentweek/${semana}`)
            .then((res) => {
                dispatch(getreservasretirada(res.data));
            })
            .catch((error) => {
                console.log(error);
                dispatch(requestError(error));
                alert(error);

            });
        api.get(`/reserva/returnweek/${semana}`)
            .then((res) => {
                dispatch(getreservasentrega(res.data));
            })
            .catch((error) => {
                console.log(error);
                dispatch(requestError(error));
                alert(error);

            });
        api.get(`/aluguel/rentweek/${semana}`)
            .then((res) => {
                dispatch(getalugueisretirada(res.data));
            })
            .catch((error) => {
                console.log(error);
                dispatch(requestError(error));
                alert(error);

            });
        api.get(`/aluguel/returnweek/${semana}`)
            .then((res) => {
                dispatch(getalugueisentrega(res.data));
            })
            .catch((error) => {
                console.log(error);
                dispatch(requestError(error));
                alert(error);

            });
    }
}

export const deleteReserva = (id) => {
    return (dispatch) => {

        api.delete("/reserva/" + id)
            .then((res) => {
                dispatch(requestError(200));
            })
            .catch((error) => {
                console.log(error.response.data);
                dispatch(requestError(error.response.data));



            });
    }
}

export const devolverAluguel = (id) => {
    return (dispatch) => {

        api.get("/aluguel/return/" + id)
            .then((res) => {
                dispatch(requestError(200));
            })
            .catch((error) => {
                console.log(error.response.data);
                dispatch(requestError(error.response.data));



            });
    }
}
