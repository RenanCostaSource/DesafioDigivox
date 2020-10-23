import { createAction, createReducer } from '@reduxjs/toolkit';

const INITIAL_STATE = "";

export const requestError = createAction('REQUEST_ERROR');

export default createReducer(INITIAL_STATE, {
    [requestError.type]: (state, action) => action.payload,

})