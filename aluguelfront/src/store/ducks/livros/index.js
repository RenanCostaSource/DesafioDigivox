import { createAction, createReducer } from '@reduxjs/toolkit';

const INITIAL_STATE = [];

export const getlivros = createAction('GET_LIVROS');

export default createReducer(INITIAL_STATE, {
    [getlivros.type]: (state, action) => action.payload,

})