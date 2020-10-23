import { configureStore } from '@reduxjs/toolkit';
import alugueisentrega from './ducks/alugueisentrega';
import alugueisretirada from './ducks/alugueisretirada';
import clientes from './ducks/clientes';
import livros from './ducks/livros';
import reservasentrega from './ducks/reservasentrega';
import reservasretirada from './ducks/reservasretirada';
import requestError from './ducks/requestError';
export default configureStore({
    reducer: {
        alugueisentrega,
        alugueisretirada,
        clientes,
        livros,
        reservasentrega,
        reservasretirada,
        requestError

    }
});