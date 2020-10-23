import { createAction, createReducer } from '@reduxjs/toolkit';

const INITIAL_STATE = [];

export const getclientes = createAction('GET_CLIENTES');

export default createReducer(INITIAL_STATE, {
    [getclientes.type]: (state, action) => action.payload,

})