import styled from 'styled-components';
import PassportPage from './PassportPage';
import { useSelector } from 'react-redux';
// Define the interfaces for the data
// can be moved to a separate file or changed as needed
type outputData = {
  output: string;
  // emission: number;
  memory: number;
  runtime: number;
  // cpuUsage: number;
  // cpuPower: number;
};

type comparisionData = {
  flight: number;
  train: number;
  netflix: number;
  google: number;
};

export interface resultData {
  Emission: number;
  // PowerConsumption: number;
  Output: outputData;
  Comparision: comparisionData;
}

function ExecutionResult() {
  let serverResponse = useSelector((state: any) => {
    return state.serverResponse;
  });
  let leftResult: resultData = {
    Emission: serverResponse.beforeExecutionResult.emission,
    Output: {
      output: serverResponse.beforeExecutionResult.output,
      memory: serverResponse.beforeExecutionResult.memory,
      runtime: serverResponse.beforeExecutionResult.runtime,
    },
    Comparision: serverResponse.beforeTranslateResult,
  };
  let rightResult: resultData = {
    Emission: serverResponse.afterExecutionResult.emission,
    Output: {
      output: serverResponse.afterExecutionResult.output,
      memory: serverResponse.afterExecutionResult.memory,
      runtime: serverResponse.afterExecutionResult.runtime,
    },
    Comparision: serverResponse.afterTranslateResult,
  };
  return (
    <ResultWrapper>
      <PassportPage result={leftResult} part="left-page" />
      <PassportPage result={rightResult} part="right-page" />
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
