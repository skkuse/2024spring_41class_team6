import { configureStore, createSlice, PayloadAction } from '@reduxjs/toolkit';

export interface ResponseType {
  greenCode: string;
  beforeExecutionResult: {
    id: number;
    codeId: number;
    status: string;
    runtime: number;
    memory: number;
    emission: number;
    output: string;
  };
  afterExecutionResult: {
    id: number;
    codeId: number;
    status: string;
    runtime: number;
    memory: number;
    emission: number;
    output: string;
  };
  beforeTranslateResult: {
    train: number;
    flight: number;
    netflix: number;
    google: number;
  };
  afterTranslateResult: {
    train: number;
    flight: number;
    netflix: number;
    google: number;
  };
}
// serverResponse
let serverResponse = createSlice({
  name: 'serverResponse',
  initialState: {},
  reducers: {
    changeServerResponse(state: any, action: PayloadAction<any>): any {
      return action.payload;
    },
  },
});
export const { changeServerResponse } = serverResponse.actions;

// Original Java Code
interface CodeState {
  javaCode: string | undefined;
}
const initialCodeState: CodeState = {
  javaCode: '',
};

let originalCode = createSlice({
  name: 'originalCode',
  initialState: initialCodeState,
  reducers: {
    changeOriginalCode(
      state: CodeState,
      action: PayloadAction<string | undefined>
    ): void {
      state.javaCode = action.payload;
    },
  },
});
export const { changeOriginalCode } = originalCode.actions;

// configureStore
export default configureStore({
  reducer: {
    serverResponse: serverResponse.reducer,
    originalCode: originalCode.reducer,
  },
});

let javaCode = `public class ConvertMilliseconds {
  public static void main(String[] args) {
      // Example runtime in milliseconds
      long milliseconds = 2028834;

      // Convert milliseconds to seconds
      long seconds = milliseconds / 1000;
      long millisecondsLeft = milliseconds % 1000;

      // Convert seconds to minutes and seconds
      long minutes = seconds / 60;
      long secondsLeft = seconds % 60;

      // Convert minutes to hours and minutes
      long hours = minutes / 60;
      long minutesLeft = minutes % 60;

      // Display the result
      if(hours != -1){
          if(minutes != -1){
              if(seconds != -1){
                  System.out.println("Runtime: " + hours + " hours " + minutesLeft + " minutes " + secondsLeft + " seconds " + millisecondsLeft + " milliseconds");           
              }
          }
      }
  }
}

  `;
