import styled from 'styled-components';
import GitURLForm from '../components/GitURLForm';
import LoadingAirplane from '../components/LoadingAirplane';
import GitPRDone from '../components/GitPRDone';
import { useEffect, useState } from 'react';

function GitRepo() {
  const [isPending, setIsPending] = useState(false);
  const [isDone, setIsDone] = useState(false);

  useEffect(() => {
    const code = new URLSearchParams(window.location.search).get('code');
    const gitURL = sessionStorage.getItem('gitURL');

    // sessionStorage에 저장된 URL 삭제
    sessionStorage.removeItem('gitURL');

    if (code) {
      console.log(code);
      console.log(gitURL);
      // 서버로 code와 gitURL을 보내고, PR 생성, response로 PR URL을 받아옴

      // temp
      setIsPending(true);
      // 서버로 code와 gitURL을 보내고, PR 생성, response로 PR URL을 받아옴
      setTimeout(() => {
        setIsPending(false);
        setIsDone(true);
      }, 2000);
    }
  }, []);

  return (
    <Wrapper>
      {isDone ? <GitPRDone url="127.0.0.1" /> : !isPending && <GitURLForm />}
      {isPending && <LoadingAirplane />}
    </Wrapper>
  );
}

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 16px;
  margin-top: 128px;
`;

export default GitRepo;
