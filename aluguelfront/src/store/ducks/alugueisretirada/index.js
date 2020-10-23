import { createAction, createReducer } from '@reduxjs/toolkit';

const INITIAL_STATE = [];

export const getalugueisretirada = createAction('GET_ALUGUEIS_RETIRADA');

export default createReducer(INITIAL_STATE, {
    [getalugueisretirada.type]: (state, action) => action.payload,

})