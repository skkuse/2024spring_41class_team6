import styled from 'styled-components';
import PassportPage from './PassportPage';

// Define the interfaces for the data
// can be moved to a separate file or changed as needed
type outputData = {
  stdout: string;
  stderr: string;
  runtime: number;
  cpuUsage: number;
  cpuPower: number;
};

type comparisionData = {
  flight: number;

  train: number;
  netflix: number;
  google: number;
};

export interface resultData {
  Emission: number;
  PowerConsumption: number;
  Output: outputData;
  Comparision: comparisionData;
}

// Define the hardcoded data
const hardcodedResultLeft: resultData = {
  Emission: 392.9,
  PowerConsumption: 0.2,
  Output: {
    stdout: 'Hello, World!',
    stderr: '',
    runtime: 0.1,
    cpuUsage: 0.1,
    cpuPower: 0.1,
  },
  Comparision: {
    flight: 30.1,
    train: 234.1,
    netflix: 0.1,
    google: 0.1,
  },
};
const hardcodedResultRight: resultData = {
  Emission: 392.9,
  PowerConsumption: 0.2,
  Output: {
    stdout: 'Hello, World!\nHello, World!',
    stderr: '',
    runtime: 0.1,
    cpuUsage: 0.1,
    cpuPower: 0.1,
  },
  Comparision: {
    flight: 0.1,
    train: 0.1,
    netflix: 0.1,
    google: 0.1,
  },
};

function ExecutionResult() {
  return (
    <ResultWrapper>
      <PassportPage result={hardcodedResultLeft} part="left-page" />
      <PassportPage result={hardcodedResultRight} part="right-page" />
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
