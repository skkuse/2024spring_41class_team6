import JavaDiffEditor from '../components/JavaDiffEditor';
import JavaEditor from '../components/JavaEditor';
import ExecutionResult from '../components/ExecutionResult';
import { useState } from 'react';
import { useSelector } from 'react-redux';
import styled from 'styled-components';
import { setIsDiffEditor } from '../store';

// import LoadingAirplane from '../components/LoadingAirplane';

function Main() {
  let isDiffEditor = useSelector((state: any) => {
    return state.isDiffEditor;
  });
  // const [isDiffEditor, setIsDiffEditor] = useState(false);

  if (isDiffEditor) {
    return (
      <div>
        <JavaDiffEditor setIsDiffEditor={setIsDiffEditor} />
        <ExecutionResult />
      </div>
    );
  } else {
    return (
      <div>
        <JavaEditor setIsDiffEditor={setIsDiffEditor} />
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
