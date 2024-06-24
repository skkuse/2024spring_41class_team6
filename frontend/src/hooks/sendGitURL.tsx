import axios from 'axios';

// gitURL을 보내기 위한 인터페이스
interface GitURL {
  prurl: string;
}

export async function sendGitURL(code: string, gitURL: string): Promise<any> {
  // post로 서버에 code랑 gitURL 보내기
  try {
    const response = await axios.post('http://localhost:8000/?  ', {
      code: code,
      gitURL: gitURL,
    });

    // reqeust 실패
    if (response.status === 200 || response.status === 500) {
      return response.data;
    }

    // reqeust 성공
    return response.data;
  } catch (error) {
    console.error(error);
  }
}
