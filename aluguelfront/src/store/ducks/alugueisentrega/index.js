import { createAction, createReducer } from '@reduxjs/toolkit';

const INITIAL_STATE = [];

export const getalugueisentrega = createAction('GET_ALUGUEIS_ENTREGA');

export default createReducer(INITIAL_STATE, {
    [getalugueisentrega.type]: (state, action) => action.payload,

})