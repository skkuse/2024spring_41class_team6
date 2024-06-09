import JavaDiffEditor from '../components/JavaDiffEditor';
import JavaEditor from '../components/JavaEditor';
import ExecutionResult from '../components/ExecutionResult';
import { useState } from 'react';
import styled from 'styled-components';
// import LoadingAirplane from '../components/LoadingAirplane';

function Main() {
  const [isDiffEditor, setIsDiffEditor] = useState(false);

  if (isDiffEditor) {
    return (
      <div>
        <JavaDiffEditor setDiffEditor={setIsDiffEditor} />
        <ExecutionResult />
      </div>
    );
  } else {
    return (
      <div>
        <JavaEditor setDiffEditor={setIsDiffEditor} />
      </div>
    );
  }
}

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 16px;
`;
export default Main;
