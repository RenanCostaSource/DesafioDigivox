import { createAction, createReducer } from '@reduxjs/toolkit';

const INITIAL_STATE = [];

export const getreservasentrega = createAction('GET_RESERVAS_ENTREGA');

export default createReducer(INITIAL_STATE, {
    [getreservasentrega.type]: (state, action) => action.payload,

})