import styled from 'styled-components';
import PassportPage from './PassportPage';
import { useSelector } from 'react-redux';

// Define the interfaces for the data
// can be moved to a separate file or changed as needed
export interface comparisionData {
  train: number;
  flight: number;
  netflix: number;
  google: number;
}

export interface resultData {
  id: number;
  codeId: number;
  status: string;
  runtime: number;
  memory: number;
  emission: number;
  output: string;
}

function ExecutionResult() {
  let beforeResult = useSelector((state: any) => {
    return state.serverResponse.beforeExecutionResult;
  });
  let afterResult = useSelector((state: any) => {
    return state.serverResponse.afterExecutionResult;
  });

  let beforeTranslateResult = useSelector((state: any) => {
    return state.serverResponse.beforeTranslateResult;
  });
  let afterTranslateResult = useSelector((state: any) => {
    return state.serverResponse.afterTranslateResult;
  });

  return (
    <ResultWrapper>
      <PassportPage
        result={beforeResult}
        part="left-page"
        comparision={beforeTranslateResult}
      />
      <PassportPage
        result={afterResult}
        part="right-page"
        comparision={afterTranslateResult}
      />
    </ResultWrapper>
  );
}

// style
const ResultWrapper = styled.div`
  display: grid;
  width: 85vw;
  margin: 0 auto;
  grid-template-columns: 1fr 1fr;
  box-sizing: border-box;
  gap: 0;
  overflow: hidden;
  padding: 16px;

  @media (max-width: 1280px) {
    width: 95vw;
  }

  @media (max-width: 1030px) {
    grid-template-columns: 1fr;
  }
`;

export default ExecutionResult;
