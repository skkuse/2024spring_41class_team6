import styled from 'styled-components';
import GitURLForm from '../components/GitURLForm';
import LoadingAirplane from '../components/LoadingAirplane';
import GitPRDone from '../components/GitPRDone';
import { sendGitURL } from '../hooks/sendGitURL';
import { useEffect, useState } from 'react';

function GitRepo() {
  const [isPending, setIsPending] = useState(false);
  const [isDone, setIsDone] = useState(false);
  const [isError, setIsError] = useState(false);
  const [prUrl, setPrUrl] = useState('');

  // PR 실패시 컴포넌트
  function GitPRFail() {
    return (
      <div>
        <h1>PR 생성에 실패했습니다.</h1>
        <p>다시 시도해주세요.</p>
        <button
          onClick={() => {
            setIsDone(false);
            setIsError(false);
          }}
        >
          다시 시도
        </button>
      </div>
    );
  }

  useEffect(() => {
    const code = new URLSearchParams(window.location.search).get('code');
    const gitURL = sessionStorage.getItem('gitURL');

    // sessionStorage에 저장된 URL 삭제
    sessionStorage.removeItem('gitURL');

    if (code && gitURL) {
      // 서버로 code와 gitURL을 보내고, PR 생성, response로 PR URL을 받아옴
      setIsPending(true);
      sendGitURL(code, gitURL)
        .then((res) => {
          if (res.url) {
            setPrUrl(res.url);
            setIsDone(true);
          } else {
            setIsError(true);
          }

          setIsPending(false);
        })
        .catch((err) => {
          setIsError(true);
          setIsPending(false);

          console.error(err);
        });
    }
  }, []);

  return (
    <Wrapper>
      {isError && <GitPRFail />}
      {isDone ? (
        <GitPRDone url={prUrl} />
      ) : (
        !isPending && !isError && <GitURLForm />
      )}
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
