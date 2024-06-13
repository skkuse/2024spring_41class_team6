import axios from 'axios';
import { changeServerResponse } from '../store';

interface ResponseType {
  emission: number;
  powerConsumption: number;
  output: {
    stdout: string;
    stderr: string;
    runtime: string;
    cpuUsage: string;
    cpuPower: string;
  };
  emissionComparison: {
    flight: string;
    tv: string;
    car: string;
    elevator: string;
  };
  greenCode: string;
  greenCodeEmission: number;
  greenCodePowerConsumption: number;
  greenCodeOutput: {
    stdout: string;
    stderr: string;
    runtime: string;
    cpuUsage: string;
    cpuPower: string;
  };
  greenCodeEmissionComparison: {
    flight: string;
    tv: string;
    car: string;
    elevator: string;
  };
}

const testResponse: ResponseType = {
  emission: 1,
  powerConsumption: 2,
  output: {
    stdout: 'Success!',
    stderr: '',
    runtime: '3s',
    cpuUsage: '4',
    cpuPower: '5',
  },
  emissionComparison: {
    flight: '6',
    tv: '7',
    car: '8',
    elevator: '9',
  },
  greenCode: `import java.util.Scanner;

  public class ProductOfTwoNumbers {
      public static void main(String[] args) {
          Scanner scanner = new Scanner(System.in);
  
          // Prompt the user to enter the first number
          System.out.print("Enter the first number: ");
          int num1 = scanner.nextInt();
  
          // Prompt the user to enter the second number
          System.out.print("Enter the second number: ");
          int num2 = scanner.nextInt();
  
          // Calculate the product of the two numbers
          int product = num1 * num2;
  
          // Display the result
          System.out.println("The product of " + num1 + " and " + num2 + " is " + product);
          
          // Close the scanner
          scanner.close();
      }
  }
  `,
  greenCodeEmission: 10,
  greenCodePowerConsumption: 11,
  greenCodeOutput: {
    stdout: 'Success!',
    stderr: '',
    runtime: '12s',
    cpuUsage: '13',
    cpuPower: '14',
  },
  greenCodeEmissionComparison: {
    flight: '15',
    tv: '16',
    car: '17',
    elevator: '18',
  },
};

async function sendCode(
  javaCode: string | undefined,
  dispatch: any
): Promise<any> {
  try {
    const response = await axios.post('http://localhost:8000/execution/code', {
      code: javaCode,
    });

    if (response.data.output.stderr) {
      return { stderr: response.data.stderr };
    } else {
      return {};
    }
  } catch (error: any) {
    // return { stderr: error.message };
    return testResponse;
  }

}

export default sendCode;
