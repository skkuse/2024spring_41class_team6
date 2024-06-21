import axios from 'axios';
import { ResponseType } from '../store';
// import { changeServerResponse } from '../store';

async function sendCode(javaCode: string | undefined): Promise<any> {
  try {
    const response: ResponseType = (
      await axios.post('http://localhost:8080/code_editor', {
        code: javaCode,
      })
    ).data;

    if (response.beforeExecutionResult.status != 'SUCCESS') {
      return { stderr: '문제가 생겼습니다 다시 시도해 주세요' };
    } else {
      return response;
    }
  } catch (error: any) {
    return { stderr: '문제가 생겼습니다 다시 시도해 주세요' };
    // return testResponse;
  }
}

export default sendCode;
