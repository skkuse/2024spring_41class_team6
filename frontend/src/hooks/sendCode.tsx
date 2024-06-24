import axios from 'axios';
import { ResponseType } from '../store';

async function sendCode(javaCode: string | undefined): Promise<any> {
  try {
    const response: ResponseType = (
      await axios.post('https://d35i3dsu8h9k4c.cloudfront.net/code_editor', {
        code: javaCode,
      })
    ).data;

    if (response.beforeExecutionResult.status == 'RUNTIME_ERROR') {
      return { stderr: '런타임 에러가 발생했습니다. 다시 시도해 주세요' };
    } else if (response.beforeExecutionResult.status == 'COMPILE_ERROR') {
      return { stderr: '컴파일 에러가 발생했습니다. 다시 시도해 주세요' };
    } else {
      return response;
    }
  } catch (error: any) {
    return { stderr: '문제가 생겼습니다 다시 시도해 주세요' };
  }
}

export default sendCode;
