import { createAction, createReducer } from '@reduxjs/toolkit';

const INITIAL_STATE = [];

export const getreservasretirada = createAction('GET_RESERVAS_RETIRADA');

export default createReducer(INITIAL_STATE, {
    [getreservasretirada.type]: (state, action) => action.payload,

})