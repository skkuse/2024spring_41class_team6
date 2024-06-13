import { configureStore, createSlice, PayloadAction } from '@reduxjs/toolkit';

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

let javaCode = `import java.util.Scanner;

  public class SumOfTwoNumbers {
      public static void main(String[] args) {
          Scanner scanner = new Scanner(System.in);
  
          // Prompt the user to enter the first number
          System.out.print("Enter the first number: ");
          int num1 = scanner.nextInt();
  
          // Prompt the user to enter the second number
          System.out.print("Enter the second number: ");
          int num2 = scanner.nextInt();
  
          // Calculate the sum of the two numbers
          int sum = num1 + num2;
  
          // Display the result
          System.out.println("The sum of " + num1 + " and " + num2 + " is " + sum);
          
          // Close the scanner
          scanner.close();
      }
  }
  `;
